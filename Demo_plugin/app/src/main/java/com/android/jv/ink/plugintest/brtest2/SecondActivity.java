package com.android.jv.ink.plugintest.brtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 测试用的第二页面，activity
 * Created by wanchi on 2017/2/8.
 */

public class SecondActivity extends Activity {

    public static String ACTION_TEST = "action_test";

    private final static String TAG = SecondActivity.class.getSimpleName();

    public static void navigateTo(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        // 启动activity这里注意需要新启动一个task
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        View sendMsgBtn = findViewById(R.id.activity_second_send_msg_btn);

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "SecondActivity send a message");

                Intent intent = new Intent(ACTION_TEST);
                sendBroadcast(intent);
                SecondActivity.this.finish();
            }
        });
    }
}
