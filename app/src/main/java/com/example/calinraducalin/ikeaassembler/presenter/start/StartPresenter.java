package com.example.calinraducalin.ikeaassembler.presenter.start;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.view.start.IStartView;
import com.example.calinraducalin.ikeaassembler.utlis.AlertDialogActivity;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;

/**
 * Created by calinraducalin on 17/06/15.
 */
public class StartPresenter extends BasePresenter implements IStartPresenter {

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

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemsManagerDelegate() {
        ItemsManager.getSharedInstance().setStartDelegate(this);
    }


    public String isItemDownloaded(Integer code) {
        return ItemsManager.getSharedInstance().isItemDownloaded(code);
    }

    public int getItemsNumber() {
        return ItemsManager.getSharedInstance().getItemsCount();
    }

    @Override
    public void itemsSuccesfullyLoad() {
        Log.d("START PRESENTER", "load from local data store");
        this.view.itemsReady();
    }

    @Override
    public void showLoadingActivity() {
        this.view.showLoadingActivity(null);
    }

    @Override
    public void noNetworkError() {
        this.view.showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_NO_NETWORK);
    }

    @Override
    public void unKnownError() {
        this.view.showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_DEFAULT);
    }

    @Override
    public void itemNotFoundError() {
        this.view.showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_ITEM_NOT_FOUND);
    }

    @Override
    public int getItemsCount() {
        return 0;
    }

    private void handleContinue(){
        switch (continueValue) {
            case 1:
                this.view.navigateToWarningsActivity();
                break;
            case 2:
                this.view.navigateToComponentsActivity();
                break;

            default:
                int phase = (continueValue / 1000) - 1;
                if (phase > -1) {
                    this.view.navigateToInstructionsActivity();
                }
                break;
        }

    }
}
