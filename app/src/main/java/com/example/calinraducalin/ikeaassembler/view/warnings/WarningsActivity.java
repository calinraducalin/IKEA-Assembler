package com.example.calinraducalin.ikeaassembler.view.warnings;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.model.Warning;
import com.example.calinraducalin.ikeaassembler.presenter.warnings.WarningsPresenter;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;

import java.util.List;

/**
 * Created by calinraducalin on 29/04/15.
 */
public class WarningsActivity extends BaseCardScrollActivity implements IWarningsView, AdapterView.OnItemSelectedListener{
    public static final String FOR_STEP = "forStep";

    private int totalWarnings;
    private boolean forStep;
    private int phaseIndex;
    private int stepIndex;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new WarningsPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);
        forStep = extras.getBoolean(FOR_STEP, false);

        if (forStep) {
            phaseIndex = extras.getInt(PHASE_INDEX);
            stepIndex = extras.getInt(STEP_INDEX);
        } else {
            setContinueValue(1);
        }

    }

    @Override
    protected void setupCardsList() {
        List warnings;
        if (forStep) {
            warnings = ((WarningsPresenter) presenter).getWarningsForStep(itemIndex, phaseIndex, stepIndex);
        } else {
            warnings = ((WarningsPresenter) presenter).getWarningsForItem(itemIndex);
        }
        totalWarnings = warnings.size();

        cardScrollerView.setAdapter(new WarningsCardScrollAdapter(context, itemCode, warnings));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        if (forStep) {
            menu.add(0, MENU_HIDE_WARNINGS, Menu.NONE, R.string.action_hide_warnings).setIcon(R.drawable.ic_arrow_down_50);
        } else {
            if (lastSelectedItem == totalWarnings - 1) {
                menu.add(0, MENU_COMPONENTS, Menu.NONE, R.string.action_components).setIcon(R.drawable.ic_arrow_right_50);
            } else {
                menu.add(0, MENU_SKIP, Menu.NONE, R.string.action_skip_warnings).setIcon(R.drawable.ic_share_50);
            }
            menu.add(0, MENU_BACK_ITEMS, Menu.NONE, R.string.action_back_items).setIcon(R.drawable.ic_reply_50);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
            Warning warning = ((Warning)adapterView.getItemAtPosition(i));
            audioHelpManager.speakTheText(warning.getText());
        }
    }

    @Override
    public void navigateToComponentsView() {
        dismissActivity(StartActivity.COMPONENTS_ACTIVITY);
    }

    @Override
    public void navigateBackToItemsActivity() {
        dismissActivity(StartActivity.ITEMS_ACTIVITY);
    }

    @Override
    public void hideWarnings() {
        finish();
    }
}
