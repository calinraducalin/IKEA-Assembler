package com.example.calinraducalin.ikeaassembler.presenter.phaseOverview;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.model.AssemblyPhase;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.phaseOverview.IPhaseOverviewView;

/**
 * Created by calinraducalin on 07/07/15.
 */
public class PhaseOverviewPresenter extends BasePresenter {
    private IPhaseOverviewView view;

    public PhaseOverviewPresenter(IPhaseOverviewView view) {
        super(view);
        this.view = view;
    }

    public boolean handleOption(int option) {
        switch (option) {
            case IPhaseOverviewView.MENU_START_PHASE:
                this.view.navigateToFirstStep();
                return true;
            case IPhaseOverviewView.MENU_BACK:
                this.view.navigateToMainMenu();
                return true;
            case IPhaseOverviewView.MENU_PREVIOUS_STEP:
                this.view.navigateToPreviousStep();
                return true;
            case IPhaseOverviewView.MENU_NEXT_PHASE:
                this.view.navigateToNextPhase();
                return true;
            case IPhaseOverviewView.MENU_PREVIOUS_PHASE:
                this.view.navigateToPreviousPhase();
                return true;
            case IPhaseOverviewView.MENU_BACK_COMPONENTS:
                this.view.navigateToComponents();
                return true;

            default:
                return false;

        }
    }

    public AssemblyPhase getPhaseForItem(int itemIndex, int phaseIndex) {
        return ItemsManager.getSharedInstance().getPhaseForItem(itemIndex, phaseIndex);
    }

    @Override
    public int getItemsCount() {
        return 0;
    }
}
