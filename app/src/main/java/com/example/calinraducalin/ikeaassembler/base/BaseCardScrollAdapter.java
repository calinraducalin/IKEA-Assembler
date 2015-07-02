package com.example.calinraducalin.ikeaassembler.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.glass.widget.CardScrollAdapter;

import java.util.List;

/**
 * Created by calinraducalin on 18/06/15.
 */
public abstract class BaseCardScrollAdapter extends CardScrollAdapter {
    protected Context context;
    protected List<Object> list;

    public BaseCardScrollAdapter (Context context, List<Object> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("CardScroll", "" + position);
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return buildView(position);
    }

    @Override
    public int getPosition(Object item) {
        return AdapterView.SCROLLBAR_POSITION_DEFAULT;
    }

    protected abstract View buildView(int position);

    protected Bitmap getBitmap(String imageRelativePath) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + imageRelativePath;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(filePath, options);
    }

}
