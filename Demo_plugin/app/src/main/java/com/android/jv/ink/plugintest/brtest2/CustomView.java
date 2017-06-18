package com.android.jv.ink.plugintest.brtest2;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 将会加载到宿主app中的页面
 * Created by wanchi on 2017/2/6.
 */

public class CustomView extends FrameLayout implements View.OnClickListener {

    public static final String TAG = "tag_plugin";
    private TextView mDescTv;
    private Context mContext;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        Log.d(TAG, "context1:" + getContext().getClass()); // ContextImpl.class
        Log.d(TAG, "context2:" + mContext); // ContextImpl.class
        Activity activity = getActivity(mContext);

        Log.d(TAG, "activity1:" + activity); // null
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, this);

        mDescTv = (TextView) findViewById(R.id.custom_view_description_tv);

        View navigateToOtherBtn = findViewById(R.id.custom_navigate_to_other_page_btn);
        View navigateToAidl = findViewById(R.id.custom_navigate_to_aidl_btn);
        navigateToAidl.setOnClickListener(this);
        navigateToOtherBtn.setOnClickListener(this);
    }

    @Nullable
    private Activity getActivity(Context context) {
        try {
            Class contextImplClass = Class.forName("android.app.ContextImpl");
            Field outerContextField = contextImplClass.getDeclaredField("mOuterContext");
            outerContextField.setAccessible(true);
            Activity activity = (Activity) outerContextField.get(context);
            return activity;
        } catch (Exception e) {
            Log.d(TAG, "exception:" + e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "context3:" + getContext().getClass()); // ContextImpl.class

        Activity activity = getActivity(mContext);
        Log.d(TAG, "activity2:" + activity); // get the activity!

        if (activity != null) {
            activity.getWindowManager();
        }

        View v1 = LayoutInflater.from(getContext()).inflate(R.layout.activity_second, null, false);
        Log.d(TAG, "v1:" + v1); // get the right layout
        // do something
        // requestData etc..
    }

    @Override
    protected void onDetachedFromWindow() {
        // do some recycling work
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.custom_navigate_to_other_page_btn) {
            SecondActivity.navigateTo(getContext());
        } else if (id == R.id.custom_navigate_to_aidl_btn) {
            AidlTestActivity.start(getContext());
        }
    }

//    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d(TAG, "CustomView Receive Msg!");
//            mDescTv.setText("收到第二页面的消息以后，改变文字信息");
//            mDescTv.setTextColor(Color.RED);
//        }
//    };
//
//
//    /**
//     * 注册广播
//     */
//    private void registerReceiver() {
//        Context context = getContext();
//        if (context == null) {
//            return;
//        }
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(SecondActivity.ACTION_TEST);
//        context.registerReceiver(mBroadcastReceiver, intentFilter);
//    }
//
//    /**
//     * 注销广播
//     */
//    private void unregisterReceiver() {
//        Context context = getContext();
//        if (context == null) {
//            return;
//        }
//        context.unregisterReceiver(mBroadcastReceiver);
//    }

}
