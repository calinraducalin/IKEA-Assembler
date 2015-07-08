package com.example.calinraducalin.ikeaassembler.view.components;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.presenter.components.ComponentsPresenter;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;

import java.util.List;

/**
 * Created by calinraducalin on 02/07/15.
 */
public class ComponentsActivity extends BaseCardScrollActivity implements IComponentsView {
    public static final String FOR_STEP = "forStep";
    private int totalComponents;
    private boolean forStep;
    private int phaseIndex;
    private int stepIndex;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new ComponentsPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);

        forStep = extras.getBoolean(FOR_STEP, false);
        if (forStep) {
            audioHelpManager.speakTheText("For this step, you will use.");
            phaseIndex = extras.getInt(PHASE_INDEX);
            stepIndex = extras.getInt(STEP_INDEX);
        } else {
            audioHelpManager.speakTheText("Please make sure you have everything you need for assembling this item.");
            setContinueValue(2);
        }

    }

    @Override
    protected void setupCardsList() {
        List components;
        if (forStep) {
            components = ((ComponentsPresenter) presenter).getComponentsForStep(itemIndex, phaseIndex, stepIndex);
        } else {
            components = ((ComponentsPresenter) presenter).getComponentsForItem(itemIndex);
        }
        totalComponents = components.size();

        cardScrollerView.setAdapter(new ComponentsCardScrollAdapter(context, itemCode, components));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        if (forStep) {
            menu.add(0, MENU_HIDE_COMPONENTS, Menu.NONE, R.string.action_hide_components).setIcon(R.drawable.ic_arrow_down_50);
        } else {
            if (lastSelectedItem == totalComponents - 1) {
                menu.add(0, MENU_BEGIN_ASSAMBLING, Menu.NONE, R.string.action_begin_assembly).setIcon(R.drawable.ic_arrow_right_50);
            } else {
                menu.add(0, MENU_SKIP_COMPONENTS, Menu.NONE, R.string.action_skip_components).setIcon(R.drawable.ic_share_50);
            }
            menu.add(0, MENU_BACK_WARNINGS, Menu.NONE, R.string.action_back_warnings).setIcon(R.drawable.ic_reply_50);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
        }
    }

    @Override
    public void navigateToInstructionsActivity() {
        setContinueValue(1000); //1000 (first phase) + 0 (first step)
        dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
    }

    @Override
    public void navigateBackToWarningsActivity() {
        setContinueValue(1); //1 == Warnings
        dismissActivity(StartActivity.WARNINGS_ACTIVITY);
    }

    @Override
    public void hideComponents() {
        finish();
    }

    public boolean isForStep() {
        return forStep;
    }
}
