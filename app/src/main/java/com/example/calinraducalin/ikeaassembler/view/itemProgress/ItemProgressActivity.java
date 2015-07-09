package com.example.calinraducalin.ikeaassembler.view.itemProgress;

import android.os.Bundle;
import android.view.Menu;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.presenter.itemProgress.ItemProgressPresenter;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;
import com.google.android.glass.widget.CardBuilder;

/**
 * Created by calinraducalin on 09/07/15.
 */
public class ItemProgressActivity extends BaseActivity implements IItemProgressView {
    private int itemCode;
    private int itemIndex;
    private int phaseIndex;
    private int stepIndex;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new ItemProgressPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);
        phaseIndex = extras.getInt(PHASE_INDEX);
        stepIndex = extras.getInt(StartActivity.STEP_INDEX);

        ((ItemProgressPresenter) presenter).getItemProgress(itemIndex, phaseIndex, stepIndex);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        menu.clear();

        menu.add(0, MENU_HIDE_PROGRESS, Menu.NONE, R.string.action_hide_progress).setIcon(R.drawable.ic_arrow_down_50);

    }

    @Override
    public void progressProcessed(int stepsCount, int totalPhases, String totalTime, String itemName, String timeLeft) {
        CardBuilder card = new CardBuilder(context, CardBuilder.Layout.AUTHOR);
        card.setHeading("Product: " + itemName);
        card.setSubheading(totalTime);
        String text = "Estimated time left:             " + timeLeft +
                      "\nAssembly phase:                 " + (phaseIndex + 1) + "/" + totalPhases +
                      "\nCurrent step in phase:        " + (stepIndex + 1) + "/" + stepsCount;
        card.setText(text);

        setContentView(card.getView());
    }

    @Override
    public void dismissView() {
        finish();
    }

    @Override
    public void nextCommand() {}

    @Override
    public void previousCommand() {}

}
