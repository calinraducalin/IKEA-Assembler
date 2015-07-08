package com.example.calinraducalin.ikeaassembler.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.example.calinraducalin.ikeaassembler.R;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.widget.Slider;
import com.google.android.glass.widget.Slider.GracePeriod;

/**
 * Created by calinraducalin on 22/06/15.
 */
public class ProgressActivity extends Activity {
    private static final int MAX_SLIDER_VALUE = 5;
    private static final long ANIMATION_DURATION_MILLIS = 5000;

    protected Slider mSlider;
    private Slider.GracePeriod mGracePeriod;
    private CardScrollView mCardScroller;
    private Slider.Indeterminate mIndeterminate;
    private Intent mIntent;
    private CardBuilder card;
    private CardScrollAdapter cardScrollAdapter;
    private boolean isGracePeriod;

    private final GracePeriod.Listener mGracePeriodListener =
            new GracePeriod.Listener() {

                @Override
                public void onGracePeriodEnd() {
                    // Play a SUCCESS sound to indicate the end of the grace period.
                    card.setText(R.string.success);
                    cardScrollAdapter.notifyDataSetChanged();
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.playSoundEffect(Sounds.SUCCESS);
                    mGracePeriod = null;
                    dismissActivity(Activity.RESULT_OK);
                }

                @Override
                public void onGracePeriodCancel() {
                    // Play a DIMISS sound to indicate the cancellation of the grace period.
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.playSoundEffect(Sounds.DISMISSED);
                    mGracePeriod = null;
                }
            };


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mIntent = getIntent();
        String loadingMessage = mIntent.getExtras().getString("message", "Please Wait...");
        buildView(loadingMessage);
        setupCardScroller();
        setupSlider();
        setContentView(mCardScroller);
        checkSliderType();
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (mGracePeriod != null) {
            mGracePeriod.cancel();
            return  super.onKeyDown(keycode, event);
        }
        return false;
    }

    protected void dismissActivity(int type) {
        Intent data = new Intent();
        if (getParent() == null) {
            setResult(type, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }
        finish();
    }

    private void setupSlider() {
        // Set the view for the Slider
        mSlider = Slider.from(mCardScroller);
        mCardScroller.activate();
        mIndeterminate = mSlider.startIndeterminate();
    }

    private void setupCardScroller() {
        // Create the cards for the view
        mCardScroller = new CardScrollView(this);
        cardScrollAdapter = new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int i) {
                return card.getView();
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return card.getView();
            }

            @Override
            public int getPosition(Object o) {
                return AdapterView.SCROLLBAR_POSITION_DEFAULT;
            }

        };
        mCardScroller.setAdapter(cardScrollAdapter);
        mCardScroller.setHorizontalScrollBarEnabled(false);
    }

    /**
     * Builds a Glass styled "Hello World!" view using the {@link CardBuilder} class.
     */
    private void buildView(String text) {
        card = new CardBuilder(this, CardBuilder.Layout.TEXT);
        card.setText(text);
    }

    protected void updateMessage(String text, String footnote) {
//        if (text != null) {
//            card.setText(text);
//        }
//        if (footnote != null) {
//            card.setFootnote(footnote);
//        }
//        cardScrollAdapter.notifyDataSetChanged();
    }

    private void checkSliderType() {
        isGracePeriod = mIntent.getExtras().getBoolean("isGrace");
        if (isGracePeriod) {
            mGracePeriod = mSlider.startGracePeriod(mGracePeriodListener);
        }
    }
}
