package com.example.calinraducalin.ikeaassembler.view.itemPhases;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollActivity;
import com.example.calinraducalin.ikeaassembler.model.AssemblyPhase;
import com.example.calinraducalin.ikeaassembler.model.ItemPhase;
import com.example.calinraducalin.ikeaassembler.presenter.itemPhases.ItemPhasesPresenter;
import com.example.calinraducalin.ikeaassembler.view.start.StartActivity;

import java.util.List;

/**
 * Created by calinraducalin on 08/07/15.
 */
public class ItemPhasesActivity extends BaseCardScrollActivity implements IItemPhasesView {
    private int resultCode;
    private int totalItemsBeforeFirstAssemblyPhase;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new ItemPhasesPresenter(this);

        Bundle extras = getIntent().getExtras();
        itemIndex = extras.getInt(ITEM_INDEX);
        itemCode = extras.getInt(ITEM_CODE);

        totalItemsBeforeFirstAssemblyPhase = 0;
    }

    @Override
    protected void setupCardsList() {
        List itemPhases = ((ItemPhasesPresenter) presenter).getItemPhasesForItem(itemIndex);
        cardScrollerView.setAdapter(new ItemPhasesCardScrollAdapter(context, itemCode, itemPhases));
        cardScrollerView.setOnItemSelectedListener(this);
    }

    @Override
    protected void setupMenu(int featureId, Menu menu) {
        super.setupMenu(featureId, menu);

        menu.add(0, MENU_SELECT, Menu.NONE, R.string.action_select_phase).setIcon(R.drawable.ic_select_link_50);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != lastSelectedItem) {
            super.onItemSelected(adapterView, view, i, l);
            String textToSpeech = "";
            Object object = adapterView.getItemAtPosition(i);
            if (object instanceof ItemPhase) {
                textToSpeech = ((ItemPhase) object).getText();
                resultCode = ((ItemPhase) object).getType();
            } else if (object instanceof AssemblyPhase) {
                textToSpeech = ((AssemblyPhase) object).getName();
                resultCode = (i - totalItemsBeforeFirstAssemblyPhase + 1) * PHASE_MULTIPLIER;
            }
            audioHelpManager.speakTheText(textToSpeech);
        }
    }

    @Override
    public void countItemBeforeFirstAssemblyPhase() {
        totalItemsBeforeFirstAssemblyPhase++;
    }

    @Override
    public void selectMenuPressed() {
        setContinueValue(resultCode);
        switch (resultCode) {
            case BaseActivity.WARNINGS_ID:
                dismissActivity(StartActivity.WARNINGS_ACTIVITY);
                break;
            case BaseActivity.COMPONENTS_ID:
                dismissActivity(StartActivity.COMPONENTS_ACTIVITY);
                break;

            default:
                dismissActivity(StartActivity.PHASE_OVERVIEW_ACTIVITY);
        }
    }
}
