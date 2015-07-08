package com.example.calinraducalin.ikeaassembler.view.items;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 24/06/15.
 */
public interface IItemsView extends IBaseView{
    int MENU_START_BEGINNING = 3;
    int MENU_DELETE = 4;
    int MENU_CONTINUE = 5;
    int MENU_PHASES = 6;


    void itemSuccesfullyDeleted(boolean moreItems, int itemCode);
    void itemDeletionError();
    void showDeleteGrace();
    void startInstructions();
    void continueThisItem();
    void navigateToItemPhases();
}
