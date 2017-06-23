package com.android.jv.ink.plugintest.plugin;

import android.app.Activity;
import android.app.Dialog;
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
    private Activity mHostActivity;
    private Dialog mDialog;

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
        Log.d(TAG, "context1:" + getContext().getClass()); // it's ContextImpl.class
        Log.d(TAG, "context2:" + mContext); // it's ContextImpl.class
        Activity activity = getActivity(mContext);

        Log.d(TAG, "activity1:" + activity); // it's null
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, this);

        mDescTv = (TextView) findViewById(R.id.custom_view_description_tv);

        View navigateToAidl = findViewById(R.id.custom_navigate_to_aidl_btn);

        View showDialogBtn = findViewById(R.id.custom_view_show_dialog);

        navigateToAidl.setOnClickListener(this);
        showDialogBtn.setOnClickListener(this);
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
        Log.d(TAG, "onAttachedToWindow");
        Log.d(TAG, "context3:" + getContext().getClass()); // ContextImpl.class

        Activity activity = getActivity(mContext);
        mHostActivity = activity;
        Log.d(TAG, "activity2:" + activity); //Yes! get the activity!

        if (activity != null) {
            activity.getWindowManager();
        }

        // do something
        // requestData etc..
    }

    private void showDialog() {
        if (mDialog == null) {
            // 用宿主中的Context来创建dialog。
            mDialog = new Dialog(mHostActivity);
            // 用插件中的Context来获得布局。
            LayoutInflater inflater = LayoutInflater.from(mContext);

            // 注意这里，由于没有传递Parent参数，映射的布局中，最外层的布局大小将会失效。
            View dialogView = inflater.inflate(R.layout.dialog_test, null);
            Log.d(TAG,dialogView.getClass().toString());

            mDialog.setContentView(dialogView);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        // 有activity切换时，会回调。
        super.onVisibilityChanged(changedView, visibility);
        Log.d(TAG, "onVisibilityChanged: visibility:" + visibility);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        // 有activity切换时，会回调。
        super.onWindowVisibilityChanged(visibility);
        Log.d(TAG, "onWindowVisibilityChanged: visibility:" + visibility);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        // 有activity切换时，会回调。
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "onWindowFocusChanged: hasWindowFocus:" + hasWindowFocus);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        // 锁屏会回调。
        Log.d(TAG, "onScreenStateChanged: screenState:" + screenState);
    }

    @Override
    protected void onDetachedFromWindow() {
        // do some recycling work
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.custom_navigate_to_aidl_btn) {
            AidlTestActivity.start(getContext());
        } else if (id == R.id.custom_view_show_dialog) {
            showDialog();
        }
    }
}
