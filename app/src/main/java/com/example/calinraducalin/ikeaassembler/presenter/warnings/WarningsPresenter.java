package com.example.calinraducalin.ikeaassembler.presenter.warnings;

import android.util.Log;

import com.example.calinraducalin.ikeaassembler.base.BasePresenter;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;
import com.example.calinraducalin.ikeaassembler.view.warnings.IWarningsView;

import java.util.List;

/**
 * Created by calinraducalin on 17/06/15.
 */
public class WarningsPresenter extends BasePresenter {
    private IWarningsView view;
    private int totalWarnings = 0;

    public WarningsPresenter(IWarningsView warningsView) {
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

            case IWarningsView.MENU_SKIP:
                this.view.navigateToComponentsView();
                return true;
            case IWarningsView.MENU_COMPONENTS:
                this.view.navigateToComponentsView();
                return true;
            case IWarningsView.MENU_BACK_ITEMS:
                this.view.navigateBackToItemsActivity();
                return true;

            default:
                return false;
        }
    }

    public List getWarningsForItem(int index) {
        List warnings = ItemsManager.getSharedInstance().getWarningsForItem(index);
        totalWarnings = warnings.size();
        return warnings;
    }

    @Override
    public int getItemsCount() {
        return totalWarnings;
    }
}
