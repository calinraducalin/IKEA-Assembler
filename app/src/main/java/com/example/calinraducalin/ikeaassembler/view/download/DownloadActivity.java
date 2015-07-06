package com.example.calinraducalin.ikeaassembler.view.download;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.calinraducalin.ikeaassembler.model.Item;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsManager;
import com.example.calinraducalin.ikeaassembler.utlis.ItemsService;
import com.example.calinraducalin.ikeaassembler.utlis.ProgressActivity;
import com.example.calinraducalin.ikeaassembler.view.start.IStartView;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.Slider;

/**
 * Created by calinraducalin on 01/07/15.
 */
public class DownloadActivity extends ProgressActivity implements IDownloadView {
    private boolean processingItemStarted;
    private int totalSteps;
    private int currentIndex;
    private ItemsService itemsService;

    public int getCurrentIndex() {
        return currentIndex;
    }

    private Slider.Determinate mDeterminate;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Integer itemCode = getIntent().getExtras().getInt("itemCode");
        downloadItem(itemCode);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK && !processingItemStarted) {
            itemsService.cancelProcessItem();
            dismissActivity(IStartView.RESULT_CODE_CANCEL_DOWNLOAD);
            return true;
        }
        return false;
    }

    private void downloadItem(Integer itemCode) {
        processingItemStarted = false;
        itemsService = new ItemsService(this);
        itemsService.getItemByID(itemCode);
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps + 3;
        currentIndex = 0;
        mDeterminate = mSlider.startDeterminate(this.totalSteps, currentIndex);
        ObjectAnimator.ofFloat(mDeterminate, "position", currentIndex, this.totalSteps).start();
    }

    @Override
    public void updateProgressActivity(String key, String value) {
        Log.wtf("DOWNLOAD ACTIVITY", key + " : " + value);

        if (key.equals("name")) {
            updateMessage(value, "Prepare resources...");
        } else if (key.equals("totalSteps")) {
            setTotalSteps(Integer.parseInt(value));
        } else if (key.equals("step")) {
            ObjectAnimator.ofFloat(mDeterminate, "position", ++currentIndex, totalSteps).start();
            updateMessage(null, "Prepare step " + value + " / " + totalSteps);
        } else if (key.equals("status")) {
            if (value.equals("warnings")) {
                updateMessage(null, "Prepare warnings...");
                ObjectAnimator.ofFloat(mDeterminate, "position", ++currentIndex, totalSteps).start();
            } else if (value.equals("components")) {
                updateMessage(null, "Prepare components...");
                ObjectAnimator.ofFloat(mDeterminate, "position", ++currentIndex, totalSteps).start();
            }
        }
    }

    @Override
    public void succesfullyLoadItem(Item item) {
        setItemCode(item.getCode());

        ObjectAnimator.ofFloat(mDeterminate, "position", ++currentIndex, totalSteps).start();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.playSoundEffect(Sounds.SUCCESS);
        ItemsManager.getSharedInstance().addItem(item);
        dismissActivity(IStartView.RESULT_CODE_ITEM_DOWNLOAD);
    }

    @Override
    public void networkError() {
        dismissActivity(IStartView.RESULT_CODE_NETWORK_ERROR);
    }

    @Override
    public void itemNotFound() {
        dismissActivity(IStartView.RESULT_CODE_ITEM_NOT_FOUND);
    }

    @Override
    public void unknownError() {
        dismissActivity(IStartView.RESULT_CODE_UNKNOWN_ERROR);
    }

    @Override
    public void processingItemStarted() {
        this.processingItemStarted = true;
    }

    private void setItemCode(int code) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("itemCode", code);

        // Commit the edits!
        editor.commit();
    }
}
