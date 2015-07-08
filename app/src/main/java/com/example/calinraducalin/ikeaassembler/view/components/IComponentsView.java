package com.example.calinraducalin.ikeaassembler.view.components;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 02/07/15.
 */
public interface IComponentsView extends IBaseView {
    int MENU_SKIP_COMPONENTS = 3;
    int MENU_BEGIN_ASSAMBLING = 4;
    int MENU_HIDE_COMPONENTS = 5;
    int MENU_BACK_WARNINGS = 6;

    void navigateToInstructionsActivity();
    void hideComponents();
    void navigateBackToWarningsActivity();
}
