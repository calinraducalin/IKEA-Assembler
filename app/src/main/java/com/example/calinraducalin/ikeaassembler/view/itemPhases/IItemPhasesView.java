package com.example.calinraducalin.ikeaassembler.view.itemPhases;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 08/07/15.
 */
public interface IItemPhasesView extends IBaseView {
    int MENU_SELECT = 3;

    void selectMenuPressed();
    void countItemBeforeFirstAssemblyPhase();
}
