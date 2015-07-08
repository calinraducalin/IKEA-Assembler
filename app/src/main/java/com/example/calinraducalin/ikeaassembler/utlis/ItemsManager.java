package com.example.calinraducalin.ikeaassembler.utlis;

import android.os.Environment;

import com.example.calinraducalin.ikeaassembler.model.AssemblyPhase;
import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.model.Step;
import com.example.calinraducalin.ikeaassembler.presenter.items.IItemsPresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by calinraducalin on 23/06/15.
 */
public class ItemsManager {
    private static final String itemsFolderName = "/items";
    private static final String itemsFileName = "/items.ser";

    private static ItemsManager sharedInstance;
    private ArrayList<Object> items;
    private IItemsPresenter itemsDelegate;

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

    public void setItemsDelegate(IItemsPresenter itemsDelegate) {
        this.itemsDelegate = itemsDelegate;
    }

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

    public int getIndexForCode(int itemCode) {
        int index = 0;
        for (Object item : this.items) {
            if (((Item) item).getCode().intValue() == itemCode) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public List<Object> getItems() {
        return items;
    }
    public List<Object> getWarningsForItem(int index) {
        return ((Item) this.items.get(index)).getWarnings();
    }
    public List<Object> getToolsAndComponentsForItem(int index) {
        return ((Item) this.items.get(index)).getToolsAndComponents();
    }

    public List getToolsAndComponentsForStep(int index, int phase, int step) {
        return this.getStep(index, phase, step).getToolsAndComponents();
    }

    public Step getStep(int index, int phase, int step) {
        return ((Item) this.items.get(index)).getStep(phase, step);
    }

    public AssemblyPhase getPhaseForItem(int index, int phase) {
        return ((Item) this.items.get(index)).getAssamblyPhase(phase);
    }

    public void deleteItemFromLocalDataStore(int index) {
        Item item = ((Item) items.remove(index));
        if (item != null) {
            //delete assets folder
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + item.getCode());
            deleteRecursive(dir);
            //save items state
            saveItems();
            itemsDelegate.itemSuccesfullyDeleted(item.getCode());
        } else {
            itemsDelegate.itemDeletionError();
        }
    }

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

    public boolean isLastStep(int index, int phase, int step) {
        return ((AssemblyPhase) ((Item) this.items.get(index)).getAssamblyPhase(phase)).isLastStep(step);
    }

    public boolean isLastPhase(int index, int phase) {
        return  ((Item) this.items.get(index)).isLastPhase(phase);
    }

    public boolean shouldRepeatPhase(int index, int phase) {
        return  ((Item) this.items.get(index)).shouldRepeatPhase(phase);
    }

    public int getPhasesCount(int index) {
        return  ((Item) this.items.get(index)).getPhasesCount();
    }

    public int getStepsCountForPhase(int itemIndex, int phaseIndex) {
        return  ((Item) this.items.get(itemIndex)).getStepsCountForPhase(phaseIndex);
    }

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
