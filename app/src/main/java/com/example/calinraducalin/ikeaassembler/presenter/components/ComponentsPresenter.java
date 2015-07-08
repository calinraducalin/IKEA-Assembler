package com.example.calinraducalin.ikeaassembler.presenter.components;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.components.IComponentsView;

import java.util.List;

/**
 * Created by calinraducalin on 02/07/15.
 */
public class ComponentsPresenter extends BasePresenter {
    private IComponentsView view;
    private int totalComponents = 0;

    public ComponentsPresenter(IComponentsView componentsView) {
        super(componentsView);
        this.view = componentsView;
    }

    public boolean handleOption(int option) {
        Log.d("ITEMS_ACTIVITY", "option " + option);

        if (super.handleOption(option)) {
            Log.d("TRUE??", "option " + option );
            return true;
        }

        Log.d("ITEMS_ACTIVITY", "option " + option );

        switch (option) {
            case IComponentsView.MENU_SKIP_COMPONENTS:
                this.view.navigateToInstructionsActivity();
                return true;
            case IComponentsView.MENU_BEGIN_ASSAMBLING:
                this.view.navigateToInstructionsActivity();
                return true;
            case IComponentsView.MENU_HIDE_COMPONENTS:
                this.view.hideComponents();
                return true;
            case IComponentsView.MENU_BACK_WARNINGS:
                this.view.navigateBackToWarningsActivity();
                return true;

            default:
                return false;
        }
    }

    public List getComponentsForItem(int index) {
        List components = ItemsManager.getSharedInstance().getToolsAndComponentsForItem(index);
        totalComponents = components.size();
        return components;
    }

    public List getComponentsForStep(int index, int phase, int step) {
        List components = ItemsManager.getSharedInstance().getToolsAndComponentsForStep(index, phase, step);
        totalComponents = components.size();
        return components;
    }

    @Override
    public int getItemsCount() {
        return totalComponents;
    }
}
