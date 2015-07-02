package com.example.calinraducalin.ikeaassembler.utlis;

import android.os.Environment;

import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.presenter.items.IItemsPresenter;
import com.example.calinraducalin.ikeaassembler.presenter.start.IStartPresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calinraducalin on 23/06/15.
 */
public class ItemsManager implements ItemsManagerDelegate {
    private static final String itemsFolderName = "/items";
    private static final String itemsFileName = "/items.ser";

    private static ItemsManager sharedInstance;
    private ArrayList<Object> items;
    private IStartPresenter startDelegate;
    private IItemsPresenter itemsDelegate;
//    private ItemsService itemsService;

    public ItemsManager(){
        super();
        loadItems();
    }

    public static ItemsManager getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ItemsManager();
        }

        return sharedInstance;
    }

    public void setStartDelegate(IStartPresenter startDelegate) {
        this.startDelegate = startDelegate;
    }

    public void setItemsDelegate(IItemsPresenter itemsDelegate) {
        this.itemsDelegate = itemsDelegate;
    }

//    public ItemsService getNewItemWithCode(Integer code) {
//        String itemName = isItemDownloaded(code);
//        if (itemName != null) {
//            startDelegate.itemExists(itemName);
//            return null;
//        }
//
//        return this.getItemFromServer(code);
//
//    }

    public void addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        saveItems();
    }

    public int getItemsCount() {
        if (items == null) {
            return 0;
        }

        return this.items.size();
    }

    public Integer getCodeForItem(int index) {
        return ((Item) this.items.get(index)).getCode();
    }

    public List<Object> getItems() {
        return items;
    }
    public List<Object> getWarningsForItem(int index) {
        return ((Item) this.items.get(index)).getWarnings();
    }
    public List<Object> getComponentsForItem(int index) {
        return ((Item) this.items.get(index)).getComponents();
    }

//    @Override
//    public void succesfullyLoadItem(Item item) {
//        this.items.add(0, item);
//        this.startDelegate.itemsSuccesfullyLoad();
//
//        Log.d("Items ready", "Number of items loaded: " + items.size());
//        saveItems();
//        itemsService = null;
//    }

    public void deleteItemFromLocalDataStore(int index) {
        Item item = ((Item) items.remove(index));
        if (item != null) {
            //delete assets folder
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + item.getCode());
            deleteRecursive(dir);
            //save items state
            saveItems();
            itemsDelegate.itemSuccesfullyDeleted();
        } else {
            itemsDelegate.itemDeletionError();
        }
    }

//    @Override
//    public void itemNotFound() {
//        startDelegate.itemNotFoundError();
//    }
//
//    @Override
//    public void networkError() {
//        startDelegate.noNetworkError();
//    }
//
//    @Override
//    public void unknownError() {
//        startDelegate.unKnownError();
//    }

    public String isItemDownloaded(Integer code) {
        if (items != null) {
            for (Object item : items) {
                if (((Item) item).getCode().intValue() == code.intValue()) {
                    return ((Item) item).getName();
                }
            }
        }
        return null;
    }

//    private ItemsService getItemFromServer(Integer code) {
//        itemsService = new ItemsService(this);
//        itemsService.getItemByID(code);
//        startDelegate.showLoadingActivity();
//
//        return itemsService;
//    }

    private void saveItems() {
        try {
            String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + itemsFolderName;
            File dir = new File(folderPath);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            SerializationUtil.serialize(items, folderPath + itemsFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadItems() {
        String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + itemsFolderName;
        File dir = new File(folderPath);
        if (!dir.exists()) {
            this.items = new ArrayList<>();
            return;
        }

        try {
            items = ((ArrayList) SerializationUtil.deserialize(folderPath + itemsFileName));
        } catch (Exception e) {
            this.items = new ArrayList<>();
        }
    }

    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }
}
