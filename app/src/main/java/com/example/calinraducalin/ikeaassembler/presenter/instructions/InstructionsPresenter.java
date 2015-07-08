package com.example.calinraducalin.ikeaassembler.presenter.instructions;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.model.Step;
import com.example.calinraducalin.ikeaassembler.utils.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.instructions.IInstructionsView;

import java.util.List;

/**
 * Created by calinraducalin on 06/07/15.
 */
public class InstructionsPresenter extends BasePresenter {
    private IInstructionsView view;
    private int totalSteps = 0;
    private Step currentStep;

    public InstructionsPresenter(IInstructionsView instructionsView) {
        super(instructionsView);
        this.view = instructionsView;
    }

    public boolean handleOption(int option) {
        if (super.handleOption(option)) {
            return true;
        }

        switch (option) {
            case IInstructionsView.MENU_SKIP_STEP:
                this.view.showNextStep();
                return true;
            case IInstructionsView.MENU_NEXT_STEP:
                this.view.showNextStep();
                return true;
            case IInstructionsView.MENU_PREVIOUS_STEP:
                this.view.showPreviousStep();
                return true;
            case IInstructionsView.MENU_SHOW_COMPONENTS:
                this.view.showToolsAndComponents();
                return true;
            case IInstructionsView.MENU_NEXT_PHASE:
                this.view.showNextPhaseOverview();
                return true;
            case IInstructionsView.MENU_REPEAT_PHASE:
                this.view.repeatThisPhase();
                return true;
            case IInstructionsView.MENU_BACK_PHASE_OVERVIEW:
                this.view.showPreviousPhaseOverview();
                return true;
            case IInstructionsView.MENU_SHOW_WARNINGS:
                this.view.showWarningsForStep();
                return true;
            default:
                return false;
        }
    }

    public List getInstructions(int itemIndex, int phase, int step) {
        currentStep = ItemsManager.getSharedInstance().getStep(itemIndex, phase, step);
        List substeps = currentStep.getSubsteps();
        totalSteps = substeps.size();
        return substeps;
    }

    @Override
    public int getItemsCount() {
        return totalSteps;
    }

    public boolean isLastStepForPhase(int index, int phase, int step) {
        return ItemsManager.getSharedInstance().isLastStep(index, phase, step);
    }

    public boolean isLastPhaseForItem(int index, int phase) {
        return ItemsManager.getSharedInstance().isLastPhase(index, phase);
    }

    public boolean shouldRepeatPhase(int itemIndex, int phaseIndex) {
        return ItemsManager.getSharedInstance().shouldRepeatPhase(itemIndex, phaseIndex);
    }

    public boolean canDisplayComponents(int index, int phase, int step) {
        return ItemsManager.getSharedInstance().getToolsAndComponentsForStep(index, phase, step).size() > 0;
    }

    public boolean areWarningsToDisplay(int index, int phase, int step) {
        return ItemsManager.getSharedInstance().areWarningsToDisplay(index, phase, step);
    }
}
