package com.example.calinraducalin.ikeaassembler.base;

/**
 * Created by calinraducalin on 24/06/15.
 */
public interface IBaseView {
    int MENU_BACK = 0;
    int MENU_NEXT = 1;
    int MENU_PREVIOUS = 2;

    void dismissView();
    void nextCommand();
    void previousCommand();
}
