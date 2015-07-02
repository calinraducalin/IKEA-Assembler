package com.example.calinraducalin.ikeaassembler.presenter.items;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.items.IItemsView;

import java.util.List;

/**
 * Created by calinraducalin on 24/06/15.
 */
public class ItemsPresenter extends BasePresenter implements IItemsPresenter {
    private IItemsView view;
    private int currentItem;

    public ItemsPresenter(IItemsView warningsView) {
        super(warningsView);
        this.view = warningsView;
    }

    public boolean handleOption(int option) {

        Log.d("ITEMS_ACTIVITY", "option " + option);


        if (super.handleOption(option)) {
            Log.d("TRUE??", "option " + option );

            return true;
        }

        Log.d("ITEMS_ACTIVITY", "option " + option );


        switch (option) {
            case IItemsView.MENU_START_BEGINNING:
                //TODO: add functionality
                this.view.startInstructions();
                Log.d("ITEMS_ACTIVITY", "Start instructions from scratch");
                return true;
            case IItemsView.MENU_DELETE:
                this.view.showDeleteGrace();
                return true;

            default:
                return false;
        }

    }

    public List<Object>getList() {
        return ItemsManager.getSharedInstance().getItems();
    }

    public int getItemsCount() {
        return ItemsManager.getSharedInstance().getItemsCount();
    }

    public int getCurrentItemCode() {
        return ItemsManager.getSharedInstance().getCodeForItem(currentItem);
    }


    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public void deleteCurrentItem() {
        ItemsManager.getSharedInstance().setItemsDelegate(this);
        ItemsManager.getSharedInstance().deleteItemFromLocalDataStore(this.currentItem);
    }

    @Override
    public void itemSuccesfullyDeleted() {
        boolean moreItems = this.getItemsCount() > 0;
        this.view.itemSuccesfullyDeleted(moreItems);
    }

    @Override
    public void itemDeletionError() {
//        this.view.itemDeletionError();
    }
}
