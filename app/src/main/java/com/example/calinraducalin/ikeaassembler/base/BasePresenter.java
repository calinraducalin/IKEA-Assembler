package com.example.calinraducalin.ikeaassembler.base;

/**
 * Created by calinraducalin on 18/06/15.
 */
public abstract class BasePresenter {
    private IBaseView view;

    public BasePresenter(IBaseView view) {
        this.view = view;
    }

    protected boolean handleOption(int option) {

        switch (option) {
            case IBaseView.MENU_BACK:
                view.dismissView();
                return true;
            case IBaseView.MENU_NEXT:
                view.nextCommand();
                return true;
            case IBaseView.MENU_PREVIOUS:
                view.previousCommand();
                return true;

            default:
                return false;
        }
    }

    public abstract int getItemsCount();

}
