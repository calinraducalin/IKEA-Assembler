package com.example.calinraducalin.ikeaassembler.view.phaseOverview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.model.AssemblyPhase;
import com.example.calinraducalin.ikeaassembler.presenter.phaseOverview.PhaseOverviewPresenter;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;

/**
 * Created by calinraducalin on 07/07/15.
 */
public class PhaseOverviewActivity extends BaseActivity implements IPhaseOverviewView {
    private int itemCode;
    private int itemIndex;
    private int phaseIndex;
    private int totalPhases;
    private int lastPhaseStepsCount;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new PhaseOverviewPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);
        phaseIndex = extras.getInt(PHASE_INDEX);
        totalPhases = extras.getInt(StartActivity.TOTAL_PHASES);
        lastPhaseStepsCount = extras.getInt(StartActivity.STEP_INDEX);
//        setContentView(R.layout.activity_phase_overview);
        AssemblyPhase phase = ((PhaseOverviewPresenter) presenter).getPhaseForItem(itemIndex, phaseIndex);
        buildView(phase);

        audioHelpManager.speakTheText(phase.getName());
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        AssemblyPhase phase = ((PhaseOverviewPresenter) presenter).getPhaseForItem(itemIndex, phaseIndex);
//        buildView(phase);
//    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        menu.clear();

        menu.add(0, MENU_START_PHASE, Menu.NONE, R.string.action_start_phase).setIcon(R.drawable.ic_forward_50);

        if (phaseIndex == 0) {
            menu.add(0, MENU_BACK_COMPONENTS, Menu.NONE, R.string.action_back_components).setIcon(R.drawable.ic_arrow_left_50);
        } else {
            menu.add(0, MENU_PREVIOUS_STEP, Menu.NONE, R.string.action_previos_step).setIcon(R.drawable.ic_arrow_left_50);
            menu.add(0, MENU_PREVIOUS_PHASE, Menu.NONE, R.string.action_previos_phase).setIcon(R.drawable.ic_reply_50);
        }

        if (phaseIndex < totalPhases - 1) {
            menu.add(0, MENU_NEXT_PHASE, Menu.NONE, R.string.action_skip_phase).setIcon(R.drawable.ic_share_50);
        }


        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            menu.add(0, MENU_BACK, Menu.NONE, R.string.action_back).setIcon(null);
        }
    }

    @Override
    public void navigateToFirstStep() {
        setContinueValue(PHASE_MULTIPLIER * (phaseIndex + 1) + 1);
        dismissActivity(StartActivity.INSTRUCTIONS_ACTIVITY);
    }

    @Override
    public void navigateToMainMenu() {
        finish();
    }

    @Override
    public void navigateToPreviousStep() {
        setContinueValue(PHASE_MULTIPLIER * (phaseIndex) + lastPhaseStepsCount);
        dismissActivity(StartActivity.INSTRUCTIONS_ACTIVITY);
    }

    @Override
    public void navigateToNextPhase() {
        setContinueValue(PHASE_MULTIPLIER * (phaseIndex + 2));
        dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
    }

    @Override
    public void navigateToPreviousPhase() {
        setContinueValue(PHASE_MULTIPLIER * (phaseIndex));
        dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
    }

    @Override
    public void navigateToComponents() {
        setContinueValue(COMPONENTS_ID);
        dismissActivity(StartActivity.COMPONENTS_ACTIVITY);
    }

    @Override
    public void dismissView() {}

    @Override
    public void nextCommand() {}

    @Override
    public void previousCommand() {}

    private void buildView(AssemblyPhase phase) {
        String title = phase.getName();
        int repeat = phase.getRepeat();
        if (repeat > 1) {
            title = title + " (" + repeat + "x)";
        }

        CardBuilder cardBuilder = new CardBuilder(this, CardBuilder.Layout.CAPTION);
        cardBuilder.addImage(getBitmap("/" + itemCode + "/" + phase.getImage()));
        cardBuilder.setText(title);
        cardBuilder.setTimestamp(phase.getStepsCount() + " steps");
        cardBuilder.setFootnote("Phase " + (phaseIndex + 1) + "/" + totalPhases);

        setContentView(cardBuilder.getView());

//        ImageView imageView = ((ImageView) findViewById(R.id.imageView));
//        if (imageView != null) {
//            imageView.setImageBitmap(getBitmap("/" + itemCode + "/" + phase.getImage()));
//        }

    }

    private Bitmap getBitmap(String imageRelativePath) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + imageRelativePath;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(filePath, options);
    }
}
