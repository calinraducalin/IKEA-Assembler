package com.example.calinraducalin.ikeaassembler.view.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollAdapter;
import com.example.calinraducalin.ikeaassembler.model.ItemComponent;

import java.util.List;

/**
 * Created by calinraducalin on 02/07/15.
 */
public class ComponentsCardScrollAdapter extends BaseCardScrollAdapter {
    private Integer itemCode;


    public ComponentsCardScrollAdapter(Context context, Integer itemCode, List<Object> list) {
        super(context, list);
        this.itemCode = itemCode;
    }

    /**
     * Builds a Glass styled "Hello World!" view using the {@link com.google.android.glass.widget.CardBuilder} class.
     */
    public View buildView(int position) {
        ItemComponent itemComponent = ((ItemComponent) list.get(position));

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout = inflater.inflate(R.layout.activity_item, null, false);

        ImageView imageView = ((ImageView) inflatedLayout.findViewById(R.id.itemImageView));
        imageView.setImageBitmap(getBitmap("/" + itemCode + "/" + itemComponent.getComponent().getImage()));

        TextView numbersTextView = ((TextView) inflatedLayout.findViewById(R.id.numberTextView));
        numbersTextView.setText(itemComponent.getCount() + "x");

        TextView codeTextView = ((TextView) inflatedLayout.findViewById(R.id.codeTextView));
        codeTextView.setText(itemComponent.getComponent().getCode().toString());

        return inflatedLayout;
    }
}
