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

    public StartPresenter(IStartView startView) {
        super(startView);
        this.view = startView;
    }

    public boolean handleOption(int option) {
        switch (option) {
            case IStartView.MENU_CONTINUE:
                //TODO: add functionality
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

    public void setItemsManagerDelegate() {
        ItemsManager.getSharedInstance().setStartDelegate(this);
    }

//    public void getItemFromServer(Integer itemCode) {
//        ItemsManager.getSharedInstance().setStartDelegate(this);
//        ItemsManager.getSharedInstance().getNewItemWithCode(itemCode);
//    }

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

//    @Override
//    public void itemExists(String itemName) {
//        this.view.showAlertDialogForType(AlertDialogActivity.ALERT_TYPE_EXISTING_ITEM);
//    }

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
}
