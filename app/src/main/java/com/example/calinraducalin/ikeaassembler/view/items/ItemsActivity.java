package com.example.calinraducalin.ikeaassembler.view.items;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.presenter.items.ItemsPresenter;
import com.example.calinraducalin.ikeaassembler.utlis.ProgressActivity;
import com.example.calinraducalin.ikeaassembler.view.components.ComponentsActivity;
import com.example.calinraducalin.ikeaassembler.view.warnings.WarningsActivity;

import java.util.List;

/**
 * Created by calinraducalin on 24/06/15.
 */
public class ItemsActivity extends BaseCardScrollActivity implements IItemsView {
    private static final int DELETING_ACTIVITY = 200;
    private static final int WARNINGS_ACTIVITY = 201;
    private static final int COMPONENTS_ACTIVITY = 202;
    public static final int NAVIGATE_COMPONENTS = 203;
    public static final int NAVIGATE_FIRST_INSTRUCTION = 204;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new ItemsPresenter(this);
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

        //default menu options
        menu.add(0, MENU_START_BEGINNING, Menu.NONE, R.string.action_start_beginning).setIcon(R.drawable.ic_angle_50);
        menu.add(0, MENU_DELETE, Menu.NONE, R.string.action_delete).setIcon(R.drawable.ic_delete_50);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
            Item item = ((Item)adapterView.getItemAtPosition(i));
            audioHelpManager.speakTheText(item.getName());
            ((ItemsPresenter) presenter).setCurrentItem(i);
        }
    }

    @Override
    public void itemSuccesfullyDeleted(boolean moreItems) {
        Log.d("SUCCESS", "ITEM DELETE");
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
        Intent warningsIntent = new Intent(ItemsActivity.this, WarningsActivity.class);
        warningsIntent.putExtra("itemIndex", lastSelectedItem);
        warningsIntent.putExtra("itemCode", ((ItemsPresenter) presenter).getCurrentItemCode());
        startActivityForResult(warningsIntent, WARNINGS_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DELETING_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Log.wtf("DELETE ITEM", "CONTINUE");
                ((ItemsPresenter) this.presenter).deleteCurrentItem();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.wtf("DELETE ITEM", "CANCEL");
            }
        } else if (requestCode == WARNINGS_ACTIVITY) {
            if (resultCode == NAVIGATE_COMPONENTS) {
                navigateToComponentsActivity();
            }
        } else if (requestCode == COMPONENTS_ACTIVITY) {
            if (resultCode == NAVIGATE_FIRST_INSTRUCTION) {
                navigateToInstructionsActivity();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToComponentsActivity() {
        Intent componentsIntent = new Intent(ItemsActivity.this, ComponentsActivity.class);
        componentsIntent.putExtra("itemIndex", lastSelectedItem);
        componentsIntent.putExtra("itemCode", ((ItemsPresenter) presenter).getCurrentItemCode());
        startActivityForResult(componentsIntent, COMPONENTS_ACTIVITY);
    }

    private void navigateToInstructionsActivity() {
        Log.wtf("ITEMS VIEW", "NAVIGATE INSTRUCTIONS");
    }
}
