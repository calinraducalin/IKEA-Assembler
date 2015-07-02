package com.example.calinraducalin.ikeaassembler.view.components;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.presenter.components.ComponentsPresenter;
import com.example.calinraducalin.ikeaassembler.view.items.ItemsActivity;

import java.util.List;

/**
 * Created by calinraducalin on 02/07/15.
 */
public class ComponentsActivity extends BaseCardScrollActivity implements IComponentsView {
    private int totalComponents;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new ComponentsPresenter(this);

        itemIndex = getIntent().getExtras().getInt("itemIndex");
        itemCode = getIntent().getExtras().getInt("itemCode");

    }

    @Override
    protected void setupCardsList() {
        List<Object> components = ((ComponentsPresenter) presenter).getComponentsForItem(itemIndex);
        totalComponents = components.size();

        cardScrollerView.setAdapter(new ComponentsCardScrollAdapter(context, itemCode, components));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        //default menu options
        if (lastSelectedItem == totalComponents - 1) {
            menu.add(0, MENU_BEGIN_ASSAMBLING, Menu.NONE, R.string.action_begin_assembly).setIcon(R.drawable.ic_share_50);
        } else {
            menu.add(0, MENU_SKIP_COMPONENTS, Menu.NONE, R.string.action_skip_components).setIcon(R.drawable.ic_share_50);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
//            Warning warning = ((Warning)adapterView.getItemAtPosition(i));
//            audioHelpManager.speakTheText(warning.getText());
//            ((ItemsPresenter) presenter).setCurrentItem(i);
        }
    }

    @Override
    public void navigateToInstructionsActivity() {
        dismissActivity(ItemsActivity.NAVIGATE_FIRST_INSTRUCTION);
    }
}