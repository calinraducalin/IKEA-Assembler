package com.example.calinraducalin.ikeaassembler.view.start;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.presenter.start.StartPresenter;
import com.example.calinraducalin.ikeaassembler.utlis.AlertDialogActivity;
import com.example.calinraducalin.ikeaassembler.utlis.ProgressActivity;
import com.example.calinraducalin.ikeaassembler.view.download.DownloadActivity;
import com.example.calinraducalin.ikeaassembler.view.items.ItemsActivity;
import com.google.android.glass.view.WindowUtils;


public class StartActivity extends BaseActivity implements IStartView {

    private static final int QR_CODE_MODE = 100;
    private static final int LOADING_ACTIVITY = 101;
    private static final int ITEMS_ACTIVITY = 102;
    private static final int DOWNLOAD_ACTIVITY = 103;
    private static final String SCAN_RESULT = "SCAN_RESULT";

    private Intent loadingIntent;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        presenter = new StartPresenter(this);
        ((StartPresenter) presenter).setItemsManagerDelegate();
        buildView();
    }

    @Override
    protected void onStart() {
        super.onStart();

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

//        menu.add(0, MENU_CONTINUE, Menu.NONE, R.string.action_continue).setIcon(R.drawable.ic_forward_50);     }

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
            //TODO: add functionality
        }

        super.onActivityResult(requestCode, resultCode, data);
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
        startActivityForResult(intent, ITEMS_ACTIVITY);
    }

    @Override
    public void navigateToScanActivity() {
        Intent objIntent = new Intent("com.google.zxing.client.android.SCAN");
        objIntent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(objIntent, QR_CODE_MODE);
    }

    @Override
    public void showLoadingActivity(String message) {
        if (message == null) {
            message = getString(R.string.searching_item);
        }

        loadingIntent = new Intent(StartActivity.this, ProgressActivity.class);
        loadingIntent.putExtra("message", message);
        startActivityForResult(loadingIntent, LOADING_ACTIVITY);
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
    public void itemsReady() {
        dismissLoadingActivity();
        Toast.makeText(context, "Items Ready", Toast.LENGTH_LONG).show();
    }

    private void buildView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    public void dismissView() {}

    @Override
    public void nextCommand() {}

    @Override
    public void previousCommand() {}

    private void navigateToDownloadActivity(Integer code) {
        Intent objIntent = new Intent(StartActivity.this, DownloadActivity.class);
        objIntent.putExtra("itemCode", code);
        objIntent.putExtra("message", getString(R.string.preparing_item));
        startActivityForResult(objIntent, DOWNLOAD_ACTIVITY);
    }

}
