package com.example.calinraducalin.ikeaassembler.presenter.itemPhases;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.model.ItemPhase;
import com.example.calinraducalin.ikeaassembler.utils.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.itemPhases.IItemPhasesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by calinraducalin on 08/07/15.
 */
public class ItemPhasesPresenter extends BasePresenter {
    private static final String SEE_WARNINGS = "See all warnings";
    private static final String CHECK_COMPONENTS = "Tools and components";

    private IItemPhasesView view;
    private int totalComponents = 0;

    public ItemPhasesPresenter(IItemPhasesView itemPhasesView) {
        super(itemPhasesView);
        this.view = itemPhasesView;
    }

    public boolean handleOption(int option) {
        Log.d("ITEMS_ACTIVITY", "option " + option);

        if (super.handleOption(option)) {
            Log.d("TRUE??", "option " + option );
            return true;
        }

        Log.d("ITEMS_ACTIVITY", "option " + option );

        switch (option) {
            case IItemPhasesView.MENU_SELECT:
                this.view.selectMenuPressed();
                return true;

            default:
                return false;
        }
    }

    public List getItemPhasesForItem(int index) {
        ArrayList itemPhases = new ArrayList();

        List objects = ItemsManager.getSharedInstance().getWarningsForItem(index);
        if (objects != null) {
            int count = objects.size();
            if (count > 0) {
                this.view.countItemBeforeFirstAssemblyPhase();
                itemPhases.add(new ItemPhase(BaseActivity.WARNINGS_ID, count, SEE_WARNINGS));
            }
        }
        objects = ItemsManager.getSharedInstance().getToolsAndComponentsForItem(index);
        if (objects != null) {
            int count = objects.size();
            if (count > 0) {
                this.view.countItemBeforeFirstAssemblyPhase();
                itemPhases.add(new ItemPhase(BaseActivity.COMPONENTS_ID, count, CHECK_COMPONENTS));
            }
        }
        objects = ItemsManager.getSharedInstance().getPhasesForItem(index);
        if (objects != null && objects.size() > 0) {
            itemPhases.addAll(objects);
        }

        totalComponents = itemPhases.size();
        return itemPhases;
    }

    @Override
    public int getItemsCount() {
        return totalComponents;
    }
}
