package com.example.calinraducalin.ikeaassembler.view.warnings;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollAdapter;
import com.example.calinraducalin.ikeaassembler.model.Warning;

import java.util.List;

/**
 * Created by calinraducalin on 17/06/15.
 */
public class WarningsCardScrollAdapter extends BaseCardScrollAdapter {
    private Integer itemCode;

    public WarningsCardScrollAdapter(Context context, Integer itemCode, List<Object> list) {
        super(context, list);
        this.itemCode = itemCode;
    }

    /**
     * Builds a Glass styled "Hello World!" view using the {@link com.google.android.glass.widget.CardBuilder} class.
     */
    public View buildView(int position) {
//        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.CAPTION);
        Warning warning = ((Warning) list.get(position));
//        card.addImage(warning.getImage());
        ImageView imageView = new ImageView(context);
        String relativePath = "/" + itemCode + "/" + warning.getImage();
        imageView.setImageBitmap(getBitmap(relativePath));
//        imageView.setImageDrawable(warning.getImage());

//        if (position == 0)
        return imageView;

//        return card.getView();

    }
}
