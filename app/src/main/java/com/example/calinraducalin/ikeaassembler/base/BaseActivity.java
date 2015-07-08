package com.example.calinraducalin.ikeaassembler.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.calinraducalin.ikeaassembler.utlis.AudioHelpManager;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;

/**
 * Created by calinraducalin on 17/06/15.
 */
public abstract class BaseActivity extends Activity {
    protected static final String STEP_INDEX = "stepIndex";
    protected static final String PHASE_INDEX = "phaseIndex";
    protected static final String ITEM_INDEX = "itemIndex";
    protected static final String ITEM_CODE = "itemCode";
    protected static final String PREFS_NAME = "MyPrefsFile";
    protected static final String CONTINUE_KEY = "continue";
    protected Context context;
    protected BasePresenter presenter;
    protected AudioHelpManager audioHelpManager;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        context = getApplicationContext();
        gestureDetector = new GestureDetector(this);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);

        audioHelpManager = AudioHelpManager.getSharedInstance();
        audioHelpManager.setContext(context);
    }

//    @Override
//    protected void onDestroy() {
//        audioHelpManager.disable();
//        super.onDestroy();
//    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (gestureDetector != null) {
            return gestureDetector.onMotionEvent(event);
        }
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS ) {
            return presenter.handleOption(item.getItemId());
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setupMenu(-1, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            setupMenu(featureId, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            setupMenu(featureId, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.handleOption(item.getItemId());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            audioManager.playSoundEffect(Sounds.TAP);
            this.openOptionsMenu();
        }

        return super.onKeyDown(keyCode, event);
    }

    protected abstract void setupMenu(int featureId, Menu menu);

    public void setContinueValue(int continueValue) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(CONTINUE_KEY, continueValue);

        // Commit the edits!
        editor.commit();
    }

    protected void dismissActivity(int resultCode) {
        Intent data = new Intent();
        if (getParent() == null) {
            setResult(resultCode, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }
        finish();
    }
}
