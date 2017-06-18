package com.android.jv.ink.plugintest.aidltest;

import com.android.jv.ink.plugintest.aidltest.CyUserCallback;

interface CyUserManager {
//    /**
//     * Demonstrates some basic types that you can use as parameters
//     * and return values in AIDL.
//     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void requestLogin();
    void registerCallback(CyUserCallback callback);
    void unregisterCallback(CyUserCallback callback);
}
