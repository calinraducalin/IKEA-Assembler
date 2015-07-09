package com.example.calinraducalin.ikeaassembler.presenter.itemProgress;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.utils.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.itemProgress.IItemProgressView;

/**
 * Created by calinraducalin on 09/07/15.
 */
public class ItemProgressPresenter extends BasePresenter {
    private IItemProgressView view;

    public ItemProgressPresenter(IItemProgressView view) {
        super(view);
        this.view = view;
    }

    public boolean handleOption(int option) {
        switch (option) {
            case IItemProgressView.MENU_HIDE_PROGRESS:
                this.view.dismissView();
                return true;

            default:
                return false;

        }
    }

    @Override
    public int getItemsCount() {
        return 0;
    }

    public void getItemProgress(int itemIndex, int phaseIndex, int stepIndex) {
        //compute time left
        int time = ItemsManager.getSharedInstance().computeTimeLeft(itemIndex, phaseIndex, stepIndex);
        int hours = time / 60;
        int minutes = time % 60;
        String timeString = "";
        if (hours > 0) {
            timeString = timeString + hours + " hr";
        }
        if (minutes > 0) {
            timeString = timeString + " " + minutes + " min";
        }

        //compute total estimated time
        time = ItemsManager.getSharedInstance().getItem(itemIndex).getTime();
        hours = time / 60;
        minutes = time % 60;
        if (minutes > 0) {
            minutes = ((minutes / 15) + 1) * 15;
        }
        String totalTime = "Total assembling time: ";
        if (hours > 0) {
            totalTime = totalTime + hours + " hr";
        }
        if (minutes > 0) {
            totalTime = totalTime + " " + minutes + " min";
        }

        String itemName = ItemsManager.getSharedInstance().getItem(itemIndex).getName();
        int stepsForPhase = ItemsManager.getSharedInstance().getItem(itemIndex).getStepsCountForPhase(phaseIndex);
        int totalPhases = ItemsManager.getSharedInstance().getPhasesCount(itemIndex);
        this.view.progressProcessed(stepsForPhase, totalPhases, totalTime, itemName, timeString);
    }
}
