package com.example.calinraducalin.ikeaassembler.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.view.components.ComponentsActivity;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardScrollView;

/**
 * Created by calinraducalin on 24/06/15.
 */
public abstract class BaseCardScrollActivity extends BaseActivity implements IBaseView, AdapterView.OnItemSelectedListener {
    protected CardScrollView cardScrollerView;
    protected int lastSelectedItem;
    protected Integer itemCode;
    protected int itemIndex;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        cardScrollerView = new CardScrollView(this);
        lastSelectedItem = -1; // used for audio
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupCardsList();
        setContentView(cardScrollerView);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        menu.clear();
//        Log.d("BASE CARD SCROLL", "last selected item = " + lastSelectedItem);

        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            //only for voice menu
            if (!(this instanceof ComponentsActivity) || !((ComponentsActivity) this).isForStep()) {
                menu.add(0, MENU_BACK, Menu.NONE, R.string.action_back).setIcon(R.drawable.ic_arrow_left_50);
            }

            int itemsCount = ((BasePresenter) presenter).getItemsCount();
            if (itemsCount > 1) {
                if (lastSelectedItem < itemsCount - 1) {
                    menu.add(0, MENU_NEXT, Menu.NONE, R.string.action_next).setIcon(R.drawable.ic_arrow_right_50);
                }
                if (lastSelectedItem > 0) {
                    menu.add(0, MENU_PREVIOUS, Menu.NONE, R.string.action_previous).setIcon(R.drawable.ic_arrow_left_50);
                }
            }
        }
    }

    protected abstract void setupCardsList();


    @Override
    protected void onResume() {
        super.onResume();
        cardScrollerView.activate();
    }

    @Override
    protected void onPause() {
        cardScrollerView.deactivate();
        super.onPause();
    }

    @Override
    public void dismissView() {
        finish();
    }

    @Override
    public void nextCommand() {
        int position = cardScrollerView.getSelectedItemPosition();
        if (position == cardScrollerView.getCount() - 1) {
            return;
        }

        cardScrollerView.animate(++position, CardScrollView.Animation.NAVIGATION);
    }

    @Override
    public void previousCommand() {
        int position = cardScrollerView.getSelectedItemPosition();
        if (position == 0) {
            return;
        }

        cardScrollerView.animate(--position, CardScrollView.Animation.NAVIGATION);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Log.d("BASE CARD SCROLL", "Selected item: " + i);
        lastSelectedItem = i;
        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

}
