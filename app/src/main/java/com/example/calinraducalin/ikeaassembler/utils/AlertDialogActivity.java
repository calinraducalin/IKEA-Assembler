package com.example.calinraducalin.ikeaassembler.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.calinraducalin.ikeaassembler.R;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardBuilder;

/**
 * Created by calinraducalin on 24/06/15.
 */

public class AlertDialogActivity extends Activity {
    public static final String TYPE_KEY = "type";
    public static final int ALERT_TYPE_DEFAULT = 1000;
    public static final int ALERT_TYPE_EXISTING_ITEM = 1001;
    public static final int ALERT_TYPE_NO_NETWORK = 1002;
    public static final int ALERT_TYPE_ITEM_NOT_FOUND = 1003;
    public static final int ALERT_TYPE_INVALID_CODE = 1004;


    private GestureDetector gestureDetector;
    private AudioManager audioManager;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        gestureDetector = new GestureDetector(this);
        buildView();

        audioManager.playSoundEffect(Sounds.ERROR);
    }


    private void buildView() {
        Intent intent = getIntent();
        int type = intent.getIntExtra(TYPE_KEY, ALERT_TYPE_DEFAULT);
        int image, text, footnote;

        switch (type) {
            case ALERT_TYPE_EXISTING_ITEM:
                image = R.drawable.ic_done_150;
                text = R.string.alert_text_existing_item;
                footnote = R.string.alert_footnote_existing_item;
                break;
            case ALERT_TYPE_ITEM_NOT_FOUND:
                image = R.drawable.ic_warning_150;
                text = R.string.alert_text_item_not_found;
                footnote = R.string.alert_footnote_item_not_found;
                break;
            case ALERT_TYPE_NO_NETWORK:
                image = R.drawable.ic_cloud_sad_150;
                text = R.string.alert_text_no_network;
                footnote = R.string.alert_footnote_no_network;
                break;
            case ALERT_TYPE_INVALID_CODE:
                image = R.drawable.ic_warning_150;
                text = R.string.alert_text_invalid_code;
                footnote = R.string.alert_footnote_invalid_code;
                break;

            default:
                image = R.drawable.ic_warning_150;
                text = R.string.alert_text_default;
                footnote = R.string.alert_footnote_default;
                break;
        }

        setContentView(new CardBuilder(getApplicationContext(), CardBuilder.Layout.ALERT)
                .setIcon(image)
                .setText(text)
                .setFootnote(footnote)
                .getView());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            handleTap();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void handleTap() {
        audioManager.playSoundEffect(Sounds.TAP);

        Intent data = new Intent();
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }

        finish();

    }
}
