/* *
   * Copyright (C) 2017 BaoliYota Tech. Co., Ltd, LLC - All Rights Reserved.
   *
   * Confidential and Proprietary.
   * Unauthorized copying of this file, via any medium is strictly prohibited.
   * */

package com.android.jv.ink.plugintest.brtest2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.jv.ink.plugintest.aidltest.CyUserCallback;
import com.android.jv.ink.plugintest.aidltest.CyUserInfo;
import com.android.jv.ink.plugintest.aidltest.CyUserManager;

import static com.android.jv.ink.plugintest.brtest2.CustomView.TAG;

/**
 * @author wanchi@coolpad.com
 * @version 1.0, 2017/5/31
 */
public class AidlTestActivity extends Activity {
    private CyUserManager mUserManager;


    public static void start(Context context) {
        Intent starter = new Intent(context, AidlTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CyUserManager userManager = CyUserManager.Stub.asInterface(service);
            try {
                mUserManager = userManager;
                userManager.registerCallback(mUserCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mUserManager = null;
            Log.i(TAG, "绑定结束");
        }
    };

    private CyUserCallback mUserCallback = new CyUserCallback.Stub() {
        @Override
        public void onGetUserInfo(CyUserInfo cyUserInfo) throws RemoteException {
            Toast.makeText(AidlTestActivity.this, "得到UserInfo：" + cyUserInfo, Toast.LENGTH_SHORT).show();
        }
    };

    public void onRequestLoginClicked(View view) {
        try {
            mUserManager.requestLogin();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void bindService(View view) {
        String action = "com.android.baoliyota.aidlservice";
        String packageName = "com.android.jv.ink.plugintest.demo_as";
        Intent intent = new Intent(action);
        intent.setPackage(packageName);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}

