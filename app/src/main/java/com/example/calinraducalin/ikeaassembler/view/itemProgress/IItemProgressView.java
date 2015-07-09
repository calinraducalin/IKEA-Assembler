package com.example.calinraducalin.ikeaassembler.view.itemProgress;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 09/07/15.
 */
public interface IItemProgressView extends IBaseView {
    int MENU_HIDE_PROGRESS = 3;

    void progressProcessed(int stepsCount, int totalPhases, String totalTime, String itemName, String timeLeft);
}
