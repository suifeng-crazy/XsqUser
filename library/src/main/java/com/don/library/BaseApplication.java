package com.don.library;

import android.app.Application;

public class BaseApplication extends Application {

    private static BaseApplication mApplication;

    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
