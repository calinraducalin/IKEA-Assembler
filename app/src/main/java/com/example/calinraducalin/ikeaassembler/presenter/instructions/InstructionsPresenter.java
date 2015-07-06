package com.example.calinraducalin.ikeaassembler.presenter.instructions;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.model.Step;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;
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
            Log.d("TRUE??", "option " + option );
            return true;
        }

        switch (option) {


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
}
