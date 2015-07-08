package com.example.calinraducalin.ikeaassembler.view.instructions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.presenter.instructions.InstructionsPresenter;
import com.example.calinraducalin.ikeaassembler.view.components.ComponentsActivity;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;

import java.util.List;

/**
 * Created by calinraducalin on 06/07/15.
 */
public class InstructionsActivity extends BaseCardScrollActivity implements IInstructionsView {
    private static final int COMPONENTS_ACTIVITY = 200;

    private int totalSubsteps;
    private int phaseIndex;
    private int stepIndex;
    boolean isLastStep;
    boolean isLastPhase;
    boolean shouldRepeatPhase;
    boolean canDisplayComponents;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new InstructionsPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);
        phaseIndex = extras.getInt(PHASE_INDEX);
        stepIndex = extras.getInt(STEP_INDEX);

        setContinueValue(1000 * (phaseIndex + 1) + stepIndex);
        isLastPhase = ((InstructionsPresenter) presenter).isLastPhaseForItem(itemIndex, phaseIndex);
        isLastStep = ((InstructionsPresenter) presenter).isLastStepForPhase(itemIndex, phaseIndex, stepIndex - 1);
        shouldRepeatPhase = ((InstructionsPresenter) presenter).shouldRepeatPhase(itemIndex, phaseIndex);
        canDisplayComponents = ((InstructionsPresenter) presenter).canDisplayComponents(itemIndex, phaseIndex, stepIndex - 1);
        if (canDisplayComponents) {
            showToolsAndComponents();
        }
    }

    @Override
    protected void setupCardsList() {
        List<Object> substeps = ((InstructionsPresenter) presenter).getInstructions(itemIndex, phaseIndex, stepIndex - 1);
        totalSubsteps = substeps.size();

        cardScrollerView.setAdapter(new InstructionsCardScrollAdapter(context, itemCode, substeps));
        cardScrollerView.setOnItemSelectedListener(this);
        if (totalSubsteps > 0) {
            cardScrollerView.setSelection(0);
        }
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        if (canDisplayComponents && (lastSelectedItem < totalSubsteps - 1)) {
            menu.add(0, MENU_SHOW_COMPONENTS, Menu.NONE, R.string.action_show_components).setIcon(R.drawable.ic_arrow_up_50);
        }


        if (isLastPhase && isLastStep) {

        } else if (lastSelectedItem == totalSubsteps - 1) {
            if (isLastStep) {
                if (shouldRepeatPhase) {
                    menu.add(0, MENU_REPEAT_PHASE, Menu.NONE, R.string.action_repeat_phase).setIcon(R.drawable.ic_reply_50);
                }
                menu.add(0, MENU_NEXT_PHASE, Menu.NONE, R.string.action_next_phase).setIcon(R.drawable.ic_arrow_right_50);
            } else {
                menu.add(0, MENU_NEXT_STEP, Menu.NONE, R.string.action_next_step).setIcon(R.drawable.ic_arrow_right_50);
            }
            menu.add(0, MENU_SHOW_COMPONENTS, Menu.NONE, R.string.action_show_components).setIcon(R.drawable.ic_arrow_up_50);
        } else {
            if (isLastStep) {
                if (shouldRepeatPhase) {
                    menu.add(0, MENU_REPEAT_PHASE, Menu.NONE, R.string.action_repeat_phase).setIcon(R.drawable.ic_reply_50);
                }
                menu.add(0, MENU_NEXT_PHASE, Menu.NONE, R.string.action_next_phase).setIcon(R.drawable.ic_share_50);
            } else {
                menu.add(0, MENU_SKIP_STEP, Menu.NONE, R.string.action_skip_step).setIcon(R.drawable.ic_share_50);
            }
        }

        if (stepIndex == 1) {
            menu.add(0, MENU_BACK_PHASE_OVERVIEW, Menu.NONE, R.string.action_back_phase_overview).setIcon(R.drawable.ic_arrow_left_50);
        } else {
            menu.add(0, MENU_PREVIOUS_STEP, Menu.NONE, R.string.action_previos_step).setIcon(R.drawable.ic_arrow_left_50);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
        }
    }

    @Override
    public void showNextStep() {
        if (!isLastPhase) {
            if (isLastStep) {
                phaseIndex++;
                stepIndex = 0;
                setContinueValue(1000 * (phaseIndex + 1));
                dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
            } else {
                stepIndex++;
//                isLastPhase = ((InstructionsPresenter) presenter).isLastPhaseForItem(itemIndex, phaseIndex);
                isLastStep = ((InstructionsPresenter) presenter).isLastStepForPhase(itemIndex, phaseIndex, stepIndex - 1);
                canDisplayComponents = ((InstructionsPresenter) presenter).canDisplayComponents(itemIndex, phaseIndex, stepIndex - 1);
                setContinueValue(1000 * (phaseIndex + 1) + stepIndex);
                if (canDisplayComponents) {
                    showToolsAndComponents();
                }
                setupCardsList();
            }
        }
    }

    @Override
    public void showPreviousPhaseOverview() {
        setContinueValue(1000 * (phaseIndex + 1));
        dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
    }

    @Override
    public void showNextPhaseOverview() {
        phaseIndex++;
        stepIndex = 0;
        setContinueValue(1000 * (phaseIndex + 1));
        dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
    }

    @Override
    public void showPreviousStep() {
        stepIndex--;
        isLastStep = ((InstructionsPresenter) presenter).isLastStepForPhase(itemIndex, phaseIndex, stepIndex - 1);
        canDisplayComponents = ((InstructionsPresenter) presenter).canDisplayComponents(itemIndex, phaseIndex, stepIndex - 1);
        setContinueValue(1000 * (phaseIndex + 1) + stepIndex);
        if (canDisplayComponents) {
            showToolsAndComponents();
        }
        setupCardsList();
    }

    @Override
    public void repeatThisPhase() {
        stepIndex = 1;
        isLastStep = ((InstructionsPresenter) presenter).isLastStepForPhase(itemIndex, phaseIndex, stepIndex - 1);
        canDisplayComponents = ((InstructionsPresenter) presenter).canDisplayComponents(itemIndex, phaseIndex, stepIndex - 1);
        setContinueValue(1000 * (phaseIndex + 1) + stepIndex);
        if (canDisplayComponents) {
            showToolsAndComponents();
        }
        setupCardsList();
    }

    @Override
    public void showToolsAndComponents() {
        Intent componentsIntent = new Intent(InstructionsActivity.this, ComponentsActivity.class);
        componentsIntent.putExtra(ITEM_INDEX, itemIndex);
        componentsIntent.putExtra(ITEM_CODE, itemCode);
        componentsIntent.putExtra(ComponentsActivity.FOR_STEP, true);
        componentsIntent.putExtra(PHASE_INDEX, phaseIndex);
        componentsIntent.putExtra(STEP_INDEX, stepIndex - 1);

        startActivity(componentsIntent);
    }


}
