package com.example.calinraducalin.ikeaassembler.view.items;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.presenter.items.ItemsPresenter;
import com.example.calinraducalin.ikeaassembler.utils.ProgressActivity;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;

import java.util.List;

/**
 * Created by calinraducalin on 24/06/15.
 */
public class ItemsActivity extends BaseCardScrollActivity implements IItemsView {
    private static final int DELETING_ACTIVITY = 200;
    private boolean isContinue;
    private boolean canContinueItem;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new ItemsPresenter(this);
        itemCode = getIntent().getExtras().getInt(ITEM_CODE, -1);
        isContinue = getIntent().getExtras().getBoolean(CONTINUE_KEY, false);
    }

    @Override
    protected void setupCardsList() {
        List<Object> items = ((ItemsPresenter) presenter).getList();

        cardScrollerView.setAdapter(new ItemsCardScrollAdapter(context, items));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        if (isContinue && canContinueItem) {
            menu.add(0, MENU_CONTINUE, Menu.NONE, R.string.action_continue).setIcon(R.drawable.ic_forward_50);
        }
        menu.add(0, MENU_START_BEGINNING, Menu.NONE, R.string.action_start_beginning).setIcon(R.drawable.ic_angle_50);
        menu.add(0, MENU_PHASES, Menu.NONE, R.string.action_see_phases).setIcon(R.drawable.ic_directions_50);
        menu.add(0, MENU_DELETE, Menu.NONE, R.string.action_delete).setIcon(R.drawable.ic_delete_50);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
            Item item = ((Item)adapterView.getItemAtPosition(i));
            audioHelpManager.speakTheText(item.getName());
            ((ItemsPresenter) presenter).setCurrentItem(i);
            canContinueItem = item.getCode().intValue() == itemCode;
        }
    }

    @Override
    public void itemSuccesfullyDeleted(boolean moreItems, int itemCode) {
        Log.d("SUCCESS", "ITEM DELETE");
        if (itemCode == getItemCode()) {
            setContinueValue(-1);
        }
        if (moreItems) {
            cardScrollerView.getAdapter().notifyDataSetChanged();

        } else {
            finish();
        }

    }

    @Override
    public void itemDeletionError() {
        Log.d("ERROR", "ITEM DELETE");
    }

    @Override
    public void showDeleteGrace() {
        Intent loadingIntent = new Intent(ItemsActivity.this, ProgressActivity.class);
        loadingIntent.putExtra("message", getString(R.string.deleting_item));
        loadingIntent.putExtra("isGrace", true);
        startActivityForResult(loadingIntent, DELETING_ACTIVITY);
    }

    @Override
    public void startInstructions() {
        setItemCode(((ItemsPresenter) presenter).getCurrentItemCode());
        dismissActivity(StartActivity.WARNINGS_ACTIVITY);
    }

    @Override
    public void continueThisItem() {
        dismissActivity(StartActivity.CONTINUE_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DELETING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Log.wtf("DELETE ITEM", "CONTINUE");
                this.deleteItem();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.wtf("DELETE ITEM", "CANCEL");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void navigateToItemPhases() {
        setContinueValue(-1);
        setItemCode(((ItemsPresenter) presenter).getCurrentItemCode());
        dismissActivity(StartActivity.ITEM_PHASES);
    }

    private void setItemCode(int code) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(ITEM_CODE, code);

        // Commit the edits!
        editor.commit();
    }

    private void deleteItem() {
        ((ItemsPresenter) this.presenter).deleteCurrentItem();
    }

    private int getItemCode() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(ITEM_CODE, -1);
    }


}
