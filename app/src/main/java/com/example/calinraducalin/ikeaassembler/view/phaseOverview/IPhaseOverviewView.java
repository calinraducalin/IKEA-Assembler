package com.example.calinraducalin.ikeaassembler.view.phaseOverview;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 07/07/15.
 */
public interface IPhaseOverviewView extends IBaseView {
    int MENU_START_PHASE = 3;
    int MENU_PREVIOUS_STEP = 4;
    int MENU_NEXT_PHASE = 5;
    int MENU_PREVIOUS_PHASE = 6;
    int MENU_BACK_COMPONENTS = 7;


    void navigateToFirstStep();
    void navigateToMainMenu();
    void navigateToPreviousStep();
    void navigateToNextPhase();
    void navigateToPreviousPhase();
    void navigateToComponents();
}
