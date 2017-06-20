/* *
   * Copyright (C) 2017 BaoliYota Tech. Co., Ltd, LLC - All Rights Reserved.
   *
   * Confidential and Proprietary.
   * Unauthorized copying of this file, via any medium is strictly prohibited.
   * */

package com.android.jv.ink.plugintest.demo_as;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.jv.ink.plugintest.aidltest.CyUserCallback;
import com.android.jv.ink.plugintest.aidltest.CyUserInfo;
import com.android.jv.ink.plugintest.aidltest.CyUserManager;

import static com.android.jv.ink.plugintest.demo_as.MainActivity.TAG;

/**
 * @author wanchi@coolpad.com
 * @version 1.0, 2017/5/31
 */
public class AidlService extends Service {

    private RemoteCallbackList<CyUserCallback> mListenerList = new RemoteCallbackList<>();

    private IBinder mBinder = new CyUserManager.Stub() {

        @Override
        public void requestLogin() throws RemoteException {
            Log.i(TAG, "requestLogin: ");
            int num = mListenerList.beginBroadcast();
            CyUserInfo userInfo = new CyUserInfo("haha", 111, "111", 1);
            for (int i = 0; i < num; ++i) {
                CyUserCallback listener = mListenerList.getBroadcastItem(i);
                Log.i(TAG, "onGetUserInfo: " + userInfo);
                listener.onGetUserInfo(userInfo);
            }
            mListenerList.finishBroadcast();
        }

        @Override
        public void registerCallback(CyUserCallback callback) throws RemoteException {
            mListenerList.register(callback);
        }

        @Override
        public void unregisterCallback(CyUserCallback callback) throws RemoteException {
            mListenerList.unregister(callback);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return mBinder;
    }


}

