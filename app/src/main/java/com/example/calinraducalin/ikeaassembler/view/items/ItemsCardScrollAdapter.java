package com.example.calinraducalin.ikeaassembler.view.items;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollAdapter;
import com.example.calinraducalin.ikeaassembler.model.Item;
import com.google.android.glass.widget.CardBuilder;

import java.util.List;

/**
 * Created by calinraducalin on 24/06/15.
 */
public class ItemsCardScrollAdapter extends BaseCardScrollAdapter {
    private static final int HOUR = 60;
    private static final int QUARTER = 15;

    public ItemsCardScrollAdapter(Context context, List<Object> list) {
        super(context, list);
    }

    public View buildView(int position) {
        CardBuilder card = new CardBuilder(context, CardBuilder.Layout.CAPTION);
        Item item = ((Item) list.get(position));

        //compute total time
        int totalTime = item.getTime();
        int hours = totalTime / HOUR;
        int min = totalTime % HOUR;
        if (min > 0) {
            min = min / QUARTER + 1;
            min *= QUARTER;
        }
        String timeString = "";
        if (hours > 0) {
            timeString = timeString + hours + " hr";
        }
        if (min > 0) {
            timeString = timeString + " " + min + " min";
        }


        card.setText(item.getName());
        card.setFootnote(timeString);
        String relativePath = "/" + item.getCode() + "/" + item.getImage();
        Bitmap bitmap = getBitmap(relativePath);
        card.addImage(bitmap);

        return card.getView();
    }

}
