package com.glumes.hooktech;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/8.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
