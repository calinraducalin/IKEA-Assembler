package com.example.calinraducalin.ikeaassembler.view.instructions;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 06/07/15.
 */
public interface IInstructionsView extends IBaseView {
    int MENU_SKIP_STEP = 3;
    int MENU_NEXT_STEP = 4;
    int MENU_PREVIOUS_STEP = 5;
    int MENU_SHOW_COMPONENTS = 6;
    int MENU_NEXT_PHASE = 7;
    int MENU_REPEAT_PHASE = 8;
    int MENU_BACK_PHASE_OVERVIEW = 9;
    int MENU_SHOW_WARNINGS = 10;


    void showNextStep();
    void repeatThisPhase();
    void showPreviousStep();
    void showToolsAndComponents();
    void showPreviousPhaseOverview();
    void showNextPhaseOverview();
    void showWarningsForStep();
}
