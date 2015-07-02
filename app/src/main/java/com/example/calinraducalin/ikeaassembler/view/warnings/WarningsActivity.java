package com.example.calinraducalin.ikeaassembler.view.warnings;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.model.Warning;
import com.example.calinraducalin.ikeaassembler.presenter.warnings.WarningsPresenter;
import com.example.calinraducalin.ikeaassembler.view.items.ItemsActivity;

import java.util.List;

/**
 * Created by calinraducalin on 29/04/15.
 */
public class WarningsActivity extends BaseCardScrollActivity implements IWarningsView, AdapterView.OnItemSelectedListener{

//    private static final int DELETING_ACTIVITY = 200;
    private int totalWarnings;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new WarningsPresenter(this);

        itemIndex = getIntent().getExtras().getInt("itemIndex");
        itemCode = getIntent().getExtras().getInt("itemCode");

    }

    @Override
    protected void setupCardsList() {
        List<Object> warnings = ((WarningsPresenter) presenter).getWarningsForItem(itemIndex);
        totalWarnings = warnings.size();

        cardScrollerView.setAdapter(new WarningsCardScrollAdapter(context, itemCode, warnings));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        //default menu options
        if (lastSelectedItem == totalWarnings - 1) {
            menu.add(0, MENU_COMPONENTS, Menu.NONE, R.string.action_components).setIcon(R.drawable.ic_share_50);
        } else {
            menu.add(0, MENU_SKIP, Menu.NONE, R.string.action_skip_warnings).setIcon(R.drawable.ic_share_50);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
            Warning warning = ((Warning)adapterView.getItemAtPosition(i));
            audioHelpManager.speakTheText(warning.getText());
//            ((ItemsPresenter) presenter).setCurrentItem(i);
        }
    }

    @Override
    public void navigateToComponentsView() {
        dismissActivity(ItemsActivity.NAVIGATE_COMPONENTS);
    }
}
