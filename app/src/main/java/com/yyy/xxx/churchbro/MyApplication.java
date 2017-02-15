package com.yyy.xxx.churchbro;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by len on 2017. 2. 14..
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
