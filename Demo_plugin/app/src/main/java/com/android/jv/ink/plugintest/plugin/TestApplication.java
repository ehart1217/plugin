package com.android.jv.ink.plugintest.plugin;

import android.app.Application;
import android.util.Log;

/**
 * 测试用的Application
 * Created by wanchi on 2017/2/13.
 */

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TestApplication", "TestApplication onCreate is invoked");
    }
}
