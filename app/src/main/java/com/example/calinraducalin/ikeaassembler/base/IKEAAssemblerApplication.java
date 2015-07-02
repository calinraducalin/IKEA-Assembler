package com.example.calinraducalin.ikeaassembler.base;

import android.app.Application;
import android.util.Log;

import com.example.calinraducalin.ikeaassembler.R;
import com.parse.Parse;

/**
 * Created by calinraducalin on 19/06/15.
 */
public class IKEAAssemblerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.wtf("Application", "ON CREATE");
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
    }
}
