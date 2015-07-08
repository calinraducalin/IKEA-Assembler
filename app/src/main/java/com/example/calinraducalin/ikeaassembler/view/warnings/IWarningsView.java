package com.example.calinraducalin.ikeaassembler.view.warnings;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 17/06/15.
 */
public interface IWarningsView extends IBaseView{
    int MENU_SKIP = 3;
    int MENU_COMPONENTS = 4;
    int MENU_BACK_ITEMS = 5;
    int MENU_HIDE_WARNINGS = 6;

    void navigateToComponentsView();
    void navigateBackToItemsActivity();
    void hideWarnings();

}
