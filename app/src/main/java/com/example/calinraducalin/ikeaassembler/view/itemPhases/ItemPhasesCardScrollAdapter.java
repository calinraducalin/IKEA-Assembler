package com.example.calinraducalin.ikeaassembler.view.itemPhases;

import android.content.Context;
import android.view.View;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseActivity;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollAdapter;
import com.example.calinraducalin.ikeaassembler.model.AssemblyPhase;
import com.example.calinraducalin.ikeaassembler.model.ItemPhase;
import com.google.android.glass.widget.CardBuilder;

import java.util.List;

/**
 * Created by calinraducalin on 08/07/15.
 */
public class ItemPhasesCardScrollAdapter extends BaseCardScrollAdapter {
    private Integer itemCode;


    public ItemPhasesCardScrollAdapter(Context context, Integer itemCode, List<Object> list) {
        super(context, list);
        this.itemCode = itemCode;
    }

    /**
     * Builds a Glass styled "Hello World!" view using the {@link com.google.android.glass.widget.CardBuilder} class.
     */
    public View buildView(int position) {
        Object object = list.get(position);
        CardBuilder cardBuilder = new CardBuilder(context, CardBuilder.Layout.TEXT);

        if (object instanceof ItemPhase) {
            switch (((ItemPhase) object).getType()) {
                case BaseActivity.WARNINGS_ID:
                    cardBuilder.addImage(R.drawable.warning_3_yes);
                    cardBuilder.setText(((ItemPhase) object).getText());
                    cardBuilder.setFootnote(((ItemPhase) object).getCount() + " warnings");
                    break;
                case BaseActivity.COMPONENTS_ID:
                    cardBuilder.addImage(R.drawable.tools_all);
                    cardBuilder.setText(((ItemPhase) object).getText());
                    cardBuilder.setFootnote(((ItemPhase) object).getCount() + " components");
                    break;
                default:
                    break;
            }
        } else if (object instanceof AssemblyPhase) {
            String title = ((AssemblyPhase) object).getName();
            int repeat = ((AssemblyPhase) object).getRepeat();
            if (repeat > 1) {
                title = title + " (" + repeat + "x)";
            }

            cardBuilder.addImage(getBitmap("/" + itemCode + "/" + ((AssemblyPhase) object).getImage()));
            cardBuilder.setText(title);
            cardBuilder.setFootnote(((AssemblyPhase) object).getStepsCount() + " steps");
        }

        return cardBuilder.getView();
    }
}
