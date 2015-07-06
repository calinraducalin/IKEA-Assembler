package com.example.calinraducalin.ikeaassembler.presenter.start;

/**
 * Created by calinraducalin on 17/06/15.
 */
public interface IStartPresenter {
    void itemsSuccesfullyLoad();
    void showLoadingActivity();
    void noNetworkError();
    void itemNotFoundError();
    void unKnownError();
}
