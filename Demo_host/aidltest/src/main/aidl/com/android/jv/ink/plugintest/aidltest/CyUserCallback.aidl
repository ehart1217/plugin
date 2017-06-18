// CyUserCallback.aidl
package com.android.jv.ink.plugintest.aidltest;
import com.android.jv.ink.plugintest.aidltest.CyUserInfo;

// Declare any non-default types here with import statements

interface CyUserCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void onGetUserInfo(in CyUserInfo userInfo);
}
