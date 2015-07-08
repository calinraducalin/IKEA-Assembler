package com.example.calinraducalin.ikeaassembler.view.start;

import com.example.calinraducalin.ikeaassembler.base.IBaseView;

/**
 * Created by calinraducalin on 17/06/15.
 */
public interface IStartView extends IBaseView {
    int MENU_CONTINUE = 1;
    int MENU_ITEMS_LIST = 2;
    int MENU_QR_CODE = 3;
    int RESULT_CODE_ITEM_NOT_FOUND = 1000;
    int RESULT_CODE_NETWORK_ERROR = 1001;
    int RESULT_CODE_UNKNOWN_ERROR = 1002;
    int RESULT_CODE_ITEM_DOWNLOAD = 1003;
    int RESULT_CODE_CANCEL_DOWNLOAD = 1004;

    void navigateToScanActivity();
    void navigateToItemsActivity();
    void showLoadingActivity(String message);
    void showAlertDialogForType(int type);
    void dismissLoadingActivity();
    void itemsReady();

    void navigateToWarningsActivity();
    void navigateToComponentsActivity();
    void navigateToInstructionsActivity();
    void navigateToPhaseOverviewActivity();
}
