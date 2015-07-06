package com.example.calinraducalin.ikeaassembler.view.instructions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.calinraducalin.ikeaassembler.R;
import com.example.calinraducalin.ikeaassembler.base.BaseCardScrollAdapter;
import com.example.calinraducalin.ikeaassembler.model.Substep;

import java.util.List;

/**
 * Created by calinraducalin on 06/07/15.
 */
public class InstructionsCardScrollAdapter extends BaseCardScrollAdapter {
    private Integer itemCode;


    public InstructionsCardScrollAdapter(Context context, Integer itemCode, List<Object> list) {
        super(context, list);
        this.itemCode = itemCode;
    }

    /**
     * Builds a Glass styled "Hello World!" view using the {@link com.google.android.glass.widget.CardBuilder} class.
     */
    public View buildView(int position) {
        Substep substep = ((Substep) list.get(position));

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout = inflater.inflate(R.layout.activity_step, null, false);

        ImageView imageView = ((ImageView) inflatedLayout.findViewById(R.id.stepImageView));
        imageView.setImageBitmap(getBitmap("/" + itemCode + "/" + substep.getImage()));
//
//        TextView numbersTextView = ((TextView) inflatedLayout.findViewById(R.id.numberTextView));
//        numbersTextView.setText(itemComponent.getCount() + "x");
//
//        TextView codeTextView = ((TextView) inflatedLayout.findViewById(R.id.codeTextView));
//        codeTextView.setText(itemComponent.getComponent().getCode().toString());

        return inflatedLayout;
    }

}
