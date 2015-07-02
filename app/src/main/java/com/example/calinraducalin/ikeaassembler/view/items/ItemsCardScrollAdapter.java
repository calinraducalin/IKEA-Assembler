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

    public ItemsCardScrollAdapter(Context context, List<Object> list) {
        super(context, list);
    }

    public View buildView(int position) {
        CardBuilder card = new CardBuilder(context, CardBuilder.Layout.CAPTION);
        Item item = ((Item) list.get(position));
        card.setText(item.getName());
        String relativePath = "/" + item.getCode() + "/" + item.getImage();
        Bitmap bitmap = getBitmap(relativePath);
        card.addImage(bitmap);

        return card.getView();
    }

}
