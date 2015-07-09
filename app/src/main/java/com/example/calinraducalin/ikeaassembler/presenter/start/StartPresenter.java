package com.example.calinraducalin.ikeaassembler.presenter.start;

import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.utils.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.start.IStartView;

/**
 * Created by calinraducalin on 17/06/15.
 */
public class StartPresenter extends BasePresenter {

    private IStartView view;
    private int continueValue;
    private int itemIndex;
    private int itemCode;

    public StartPresenter(IStartView startView) {
        super(startView);
        this.view = startView;
    }

    public boolean handleOption(int option) {
        switch (option) {
            case IStartView.MENU_CONTINUE:
                this.handleContinue();
                return true;
            case IStartView.MENU_ITEMS_LIST:
                view.navigateToItemsActivity();
                return true;
            case IStartView.MENU_QR_CODE:
                view.navigateToScanActivity();
                return true;
            case IStartView.MENU_CALL_PROVIDER:
                view.callLocalProvider();
                return true;

            default:
                return false;

        }
    }

    public void setContinueValue(int continueValue) {
        this.continueValue = continueValue;
    }

    public void setItemCode(int code) {
        this.itemCode = code;
        if (code > -1) {
            itemIndex = ItemsManager.getSharedInstance().getIndexForCode(code);
        }
    }

    public int getItemCode() {
        return itemCode;
    }

    public int getPhasesCount() {
        return ItemsManager.getSharedInstance().getPhasesCount(itemIndex);
    }

    public int  getStepsCountForPhase(int phaseIndex) {
        if (phaseIndex < 0) {
            return 0;
        }
        return ItemsManager.getSharedInstance().getStepsCountForPhase(itemIndex, phaseIndex);
    }


    public int getItemIndex() {
        return itemIndex;
    }

    public String isItemDownloaded(Integer code) {
        return ItemsManager.getSharedInstance().isItemDownloaded(code);
    }

    public int getItemsNumber() {
        return ItemsManager.getSharedInstance().getItemsCount();
    }

    @Override
    public int getItemsCount() {
        return 0;
    }

    public void handleContinue(){
        switch (continueValue) {
            case BaseActivity.WARNINGS_ID:
                this.view.navigateToWarningsActivity();
                break;
            case BaseActivity.COMPONENTS_ID:
                this.view.navigateToComponentsActivity();
                break;

            default:
                if (continueValue % BaseActivity.PHASE_MULTIPLIER == 0) {
                    this.view.navigateToPhaseOverviewActivity();
                } else {
                    int phase = (continueValue / BaseActivity.WARNINGS_ID) - 1;
                    if (phase > -1) {
                        this.view.navigateToInstructionsActivity();
                    }
                }
                break;
        }

    }
}
