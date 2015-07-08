package com.example.calinraducalin.ikeaassembler.view.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollAdapter;
import com.example.calinraducalin.ikeaassembler.model.Component;
import com.example.calinraducalin.ikeaassembler.model.ItemComponent;
import com.example.calinraducalin.ikeaassembler.model.StepComponent;
import com.example.calinraducalin.ikeaassembler.model.Tool;

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
        LayoutInflater inflater = LayoutInflater.from(context);
        Object toolOrComponent = list.get(position);

        if (toolOrComponent instanceof Tool) {
            Tool tool = ((Tool) toolOrComponent);

            View inflatedLayout = inflater.inflate(R.layout.activity_tool, null, false);

            ImageView imageView = ((ImageView) inflatedLayout.findViewById(R.id.itemImageView));
            imageView.setImageBitmap(getBitmap("/" + itemCode + "/" + tool.getImage()));

            TextView nameTextView = ((TextView) inflatedLayout.findViewById(R.id.nameTextView));
            nameTextView.setText(tool.getName());

            return inflatedLayout;

        } else if (toolOrComponent instanceof ItemComponent || toolOrComponent instanceof StepComponent) {
            Component component;
            int count = 0;
            if (toolOrComponent instanceof ItemComponent) {
                component = ((ItemComponent) toolOrComponent).getComponent();
                count = ((ItemComponent) toolOrComponent).getCount();
            } else {
                component = ((StepComponent) toolOrComponent).getComponent();
                count = ((StepComponent) toolOrComponent).getCount();
            }

            View inflatedLayout = inflater.inflate(R.layout.activity_item, null, false);

            ImageView imageView = ((ImageView) inflatedLayout.findViewById(R.id.itemImageView));
            imageView.setImageBitmap(getBitmap("/" + itemCode + "/" + component.getImage()));

            TextView numbersTextView = ((TextView) inflatedLayout.findViewById(R.id.nameTextView));
            numbersTextView.setText(count + "x");

            TextView codeTextView = ((TextView) inflatedLayout.findViewById(R.id.codeTextView));
            codeTextView.setText(component.getCode().toString());

            return inflatedLayout;
        }

        return new View(context);
    }
}
