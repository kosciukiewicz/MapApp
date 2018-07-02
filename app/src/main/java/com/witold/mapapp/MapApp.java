package com.witold.mapapp;

import android.app.Application;

import timber.log.Timber;

public class MapApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
