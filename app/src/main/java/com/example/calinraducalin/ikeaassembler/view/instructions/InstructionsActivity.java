package com.example.calinraducalin.ikeaassembler.view.instructions;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.presenter.instructions.InstructionsPresenter;

import java.util.List;

/**
 * Created by calinraducalin on 06/07/15.
 */
public class InstructionsActivity extends BaseCardScrollActivity implements IInstructionsView {
    private int totalSubsteps;
    private int phase;
    private int step;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new InstructionsPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);
        phase = extras.getInt(PHASE_INDEX);
        step = extras.getInt(STEP_INDEX);

        setContinueValue(1000 * (phase + 1) + step);
    }

    @Override
    protected void setupCardsList() {
        List<Object> substeps = ((InstructionsPresenter) presenter).getInstructions(itemIndex, phase, step);
        totalSubsteps = substeps.size();

        cardScrollerView.setAdapter(new InstructionsCardScrollAdapter(context, itemCode, substeps));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        //default menu options
        if (lastSelectedItem == totalSubsteps - 1) {
//            menu.add(0, MENU_COMPONENTS, Menu.NONE, R.string.action_components).setIcon(R.drawable.ic_share_50);
        } else {
//            menu.add(0, MENU_SKIP, Menu.NONE, R.string.action_skip_warnings).setIcon(R.drawable.ic_share_50);
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

}
