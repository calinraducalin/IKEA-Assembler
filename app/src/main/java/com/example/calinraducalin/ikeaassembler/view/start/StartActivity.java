package com.example.calinraducalin.ikeaassembler.view.start;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.presenter.start.StartPresenter;
import com.example.calinraducalin.ikeaassembler.utils.AlertDialogActivity;
import com.example.calinraducalin.ikeaassembler.view.components.ComponentsActivity;
import com.example.calinraducalin.ikeaassembler.view.download.DownloadActivity;
import com.example.calinraducalin.ikeaassembler.view.instructions.InstructionsActivity;
import com.example.calinraducalin.ikeaassembler.view.itemPhases.ItemPhasesActivity;
import com.example.calinraducalin.ikeaassembler.view.items.ItemsActivity;
import com.example.calinraducalin.ikeaassembler.view.phaseOverview.PhaseOverviewActivity;
import com.example.calinraducalin.ikeaassembler.view.warnings.WarningsActivity;
import com.google.android.glass.view.WindowUtils;


public class StartActivity extends BaseActivity implements IStartView {
    public static final String TOTAL_PHASES = "totalPhases";
    private static final int QR_CODE_MODE = 100;
    private static final int LOADING_ACTIVITY = 101;
    public static final int ITEMS_ACTIVITY = 102;
    private static final int DOWNLOAD_ACTIVITY = 103;
    public static final int WARNINGS_ACTIVITY = 104;
    public static final int COMPONENTS_ACTIVITY = 105;
    public static final int INSTRUCTIONS_ACTIVITY = 106;
    public static final int PHASE_OVERVIEW_ACTIVITY = 107;
    public static final int CONTINUE_ACTIVITY = 108;
    public static final int ITEM_PHASES = 109;

    private static final String MESSAGE = "message";
    private static final String SCAN_RESULT = "SCAN_RESULT";

    private int continueValue;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new StartPresenter(this);
        buildView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadContinueData();
        ImageView imageView = ((ImageView) findViewById(R.id.imageView));
        if (imageView != null) {
            imageView.setImageResource(R.drawable.ikea_assembler_caption);
        }
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        menu.clear();

        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL) {
            //only for voice menu
        }

        if (continueValue != -1) {
            menu.add(0, MENU_CONTINUE, Menu.NONE, R.string.action_continue).setIcon(R.drawable.ic_forward_50);
        }

        if (((StartPresenter) presenter).getItemsNumber() > 0) {
            menu.add(0, MENU_ITEMS_LIST, Menu.NONE, R.string.action_items).setIcon(R.drawable.ic_archive_50);
        }
        //default menu item
        menu.add(0, MENU_QR_CODE, Menu.NONE, R.string.action_scan).setIcon(R.drawable.ic_search_50);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Activity result", "result code = " + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case QR_CODE_MODE:
                    String itemCode = data.getStringExtra(SCAN_RESULT);
                    handleScanResult(itemCode);
                    break;

                case AlertDialogActivity.ALERT_TYPE_EXISTING_ITEM:
                    Log.d("Alert dialog TAP", "navigate to instructions activity");
                    //TODO:navigate to instructions activity
                    break;

                case AlertDialogActivity.ALERT_TYPE_ITEM_NOT_FOUND:
                    navigateToScanActivity();
                    break;

                case AlertDialogActivity.ALERT_TYPE_NO_NETWORK:
                    // Open WiFi Settings
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    break;

                case AlertDialogActivity.ALERT_TYPE_INVALID_CODE:
                    navigateToScanActivity();
                    break;

                default:
                    break;
            }
        } else if (resultCode == RESULT_CODE_ITEM_NOT_FOUND) {
            showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_ITEM_NOT_FOUND);
        } else if (resultCode == RESULT_CODE_NETWORK_ERROR) {
            showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_NO_NETWORK);
        } else if (resultCode == RESULT_CODE_UNKNOWN_ERROR) {
            showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_DEFAULT);
        } else if (resultCode == RESULT_CODE_ITEM_DOWNLOAD) {
            navigateToWarningsActivity();
        } else if (resultCode == WARNINGS_ACTIVITY) {
            navigateToWarningsActivity();
        } else if (resultCode == COMPONENTS_ACTIVITY) {
            navigateToComponentsActivity();
        } else if (resultCode == PHASE_OVERVIEW_ACTIVITY) {
            navigateToPhaseOverviewActivity();
        } else if (resultCode == INSTRUCTIONS_ACTIVITY) {
            navigateToInstructionsActivity();
        } else if (resultCode == ITEMS_ACTIVITY) {
            navigateToItemsActivity();
        } else if (resultCode == CONTINUE_ACTIVITY) {
            ((StartPresenter) this.presenter).handleContinue();
        } else if (resultCode == ITEM_PHASES) {
            navigateToItemPhasesActivity();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleScanResult(String itemCode) {
        String itemName = null;
        Integer iid = null;
        try {
            iid = Integer.parseInt(itemCode);
            itemName = ((StartPresenter) presenter).isItemDownloaded(iid);
        } catch (Exception e) {}

        if (iid == null ) {
            showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_INVALID_CODE);
        } else if (iid < 10000000 || iid > 99999999) {

        } else if (itemName == null) {
            navigateToDownloadActivity(iid);
        } else {
            showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_EXISTING_ITEM);
        }
    }

    @Override
    public void navigateToItemsActivity() {
        Intent intent = new Intent(StartActivity.this, ItemsActivity.class);
        intent.putExtra(ITEM_CODE, ((StartPresenter) presenter).getItemCode());
        intent.putExtra(CONTINUE_KEY, getContinueValue() > 0);
        startActivityForResult(intent, ITEMS_ACTIVITY);
    }

    @Override
    public void navigateToScanActivity() {
        Intent objIntent = new Intent("com.google.zxing.client.android.SCAN");
        objIntent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(objIntent, QR_CODE_MODE);
    }

    @Override
    public void showAlertDialogForType(int type) {
        dismissLoadingActivity();
        Intent alertIntent = new Intent(StartActivity.this, AlertDialogActivity.class);
        alertIntent.putExtra(AlertDialogActivity.TYPE_KEY, type);
        startActivityForResult(alertIntent, type);
    }

    @Override
    public void dismissLoadingActivity() {
        finishActivity(LOADING_ACTIVITY);
    }

    @Override
    public void navigateToWarningsActivity() {
        Intent warningsIntent = new Intent(StartActivity.this, WarningsActivity.class);
        reloadContinueData();
        warningsIntent.putExtra(ITEM_INDEX, ((StartPresenter) presenter).getItemIndex());
        warningsIntent.putExtra(ITEM_CODE, ((StartPresenter) presenter).getItemCode());
        startActivityForResult(warningsIntent, WARNINGS_ACTIVITY);

    }

    @Override
    public void navigateToComponentsActivity() {
        Intent componentsIntent = new Intent(StartActivity.this, ComponentsActivity.class);
        reloadContinueData();
        componentsIntent.putExtra(ITEM_INDEX, ((StartPresenter) presenter).getItemIndex());
        componentsIntent.putExtra(ITEM_CODE, ((StartPresenter) presenter).getItemCode());
        startActivityForResult(componentsIntent, COMPONENTS_ACTIVITY);
    }

    @Override
    public void navigateToInstructionsActivity() {
        Intent instructionsIntent = new Intent(StartActivity.this, InstructionsActivity.class);
        reloadContinueData();
        int phase = (continueValue / PHASE_MULTIPLIER) - 1;
        int step = continueValue % PHASE_MULTIPLIER;
        instructionsIntent.putExtra(ITEM_INDEX, ((StartPresenter) presenter).getItemIndex());
        instructionsIntent.putExtra(ITEM_CODE, ((StartPresenter) presenter).getItemCode());
        instructionsIntent.putExtra(PHASE_INDEX, phase);
        instructionsIntent.putExtra(STEP_INDEX, step);
        startActivityForResult(instructionsIntent, COMPONENTS_ACTIVITY);
    }

    @Override
    public void navigateToPhaseOverviewActivity() {
        Log.d("START ACTIVITY", "Navigate to Phase Overview Activity");
        Intent phaseIntent = new Intent(StartActivity.this, PhaseOverviewActivity.class);
        reloadContinueData();
        int phaseIndex = (continueValue / PHASE_MULTIPLIER) - 1;
        phaseIntent.putExtra(ITEM_INDEX, ((StartPresenter) presenter).getItemIndex());
        phaseIntent.putExtra(ITEM_CODE, ((StartPresenter) presenter).getItemCode());
        phaseIntent.putExtra(PHASE_INDEX, phaseIndex);
        phaseIntent.putExtra(STEP_INDEX, ((StartPresenter) presenter).getStepsCountForPhase(phaseIndex - 1));
        phaseIntent.putExtra(TOTAL_PHASES, ((StartPresenter) presenter).getPhasesCount());

        startActivityForResult(phaseIntent, PHASE_OVERVIEW_ACTIVITY);
    }

    @Override
    public void dismissView() {}

    @Override
    public void nextCommand() {}

    @Override
    public void previousCommand() {}

    private void buildView() {
        setContentView(R.layout.activity_main);
    }

    private void navigateToDownloadActivity(Integer code) {
        Intent objIntent = new Intent(StartActivity.this, DownloadActivity.class);
        objIntent.putExtra(ITEM_CODE, code);
        objIntent.putExtra(MESSAGE, getString(R.string.preparing_item));
        startActivityForResult(objIntent, DOWNLOAD_ACTIVITY);
    }

    private void navigateToItemPhasesActivity() {
        Intent intent = new Intent(StartActivity.this, ItemPhasesActivity.class);
        reloadContinueData();
        intent.putExtra(ITEM_INDEX, ((StartPresenter) presenter).getItemIndex());
        intent.putExtra(ITEM_CODE, ((StartPresenter) presenter).getItemCode());
        startActivityForResult(intent, ITEM_PHASES);
    }

    private int getContinueValue() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int continueValue = settings.getInt(CONTINUE_KEY, -1);
        return continueValue;
    }

    private int getItemCode() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(ITEM_CODE, -1);
    }

    private void reloadContinueData() {
        continueValue = getContinueValue();
        ((StartPresenter) presenter).setContinueValue(continueValue);
        ((StartPresenter) presenter).setItemCode(getItemCode());
    }
}
