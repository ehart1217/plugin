package com.android.jv.ink.plugintest.demo_as;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PLUGIN_PACKAGE_NAME = "com.android.jv.ink.plugintest.brtest2";
    private static final String PLUGIN_LAYOUT_NAME = "activity_main";

    private static final int WRITE_REQUEST_CODE = 1;

    public static final String TAG = "host";

    private ViewGroup mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View loadPluginBtn = findViewById(R.id.load_plugin_btn);
        loadPluginBtn.setOnClickListener(this);

        mContainer = (ViewGroup) findViewById(R.id.activity_main);
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.load_plugin_btn) {
            View v = null;
            try {
                Context thisContext = this;
                Log.d(TAG, "thisContext:" + thisContext.getClass());
                Context context = createPackageContext(PLUGIN_PACKAGE_NAME,
                        Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);

                Log.d(TAG, "makeContext:" + context);

                makePluginApplication(context);

                int id = context.getResources().getIdentifier(PLUGIN_LAYOUT_NAME, "layout", PLUGIN_PACKAGE_NAME);
                v = LayoutInflater.from(context).inflate(id, mContainer, false);

                setActivity(context, this);

                v.setTag(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (v != null) {
                mContainer.addView(v);
                Toast.makeText(this, "加载插件成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "加载插件失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 反射将Activity加入到context的mOuterContext中。
     * 注意：
     * 1.如果在inflate插件之前调用此方法，可能将导致插件资源文件错乱。所以等inflate完成之后，加入到宿主之前，注入Activity。
     * 2.由于第一点，导致，在插件中，构造方法中的context不包含Activity。onAttachedToWindow回调中context就包含Activity了。
     * 3.目前测试没有什么问题，测试时间较短，不知道会不会导致其他问题发生。
     *
     * @param context  这是一个ContextImpl对象，待注入Activity
     * @param activity 这是待注入的Activity。
     */
    private void setActivity(Context context, Activity activity) {
        try {
            Class contextImplClass = Class.forName("android.app.ContextImpl");
            Field outerContextField = contextImplClass.getDeclaredField("mOuterContext");
            outerContextField.setAccessible(true);
            outerContextField.set(context, activity);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 创建一个插件的application（包名在Context中），然后通过各种反射将application设置到Context中。
    private void makePluginApplication(Context context) throws ClassNotFoundException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class contextImplClass = Class.forName("android.app.ContextImpl");
        Field packageInfoField = contextImplClass.getDeclaredField("mPackageInfo");
        packageInfoField.setAccessible(true);

        Object loadedWidgetApk = packageInfoField.get(context);
        Class<?> loadedApkClass = Class.forName("android.app.LoadedApk");
        Method makeApplicationMethod = loadedApkClass.getMethod("makeApplication", boolean.class, Instrumentation.class);

        Instrumentation instrumentation = getInstrumentation();
        Application application = (Application) makeApplicationMethod.invoke(loadedWidgetApk, false, instrumentation);

        Class contextWrapperClass = Class.forName("android.content.ContextWrapper");
        Field mBaseField = contextWrapperClass.getDeclaredField("mBase");
        mBaseField.setAccessible(true);
        mBaseField.set(application, context);

    }

    /**
     * 反射得到ActivityThread的Instrument对象。（ActivityThread类不可访问）
     *
     * @return 得到的Instrument
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Instrumentation getInstrumentation() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        activityThreadField.setAccessible(true);
        Object activityThread = activityThreadField.get(activityThreadClass);

        Field instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        instrumentationField.setAccessible(true);
        return (Instrumentation) instrumentationField.get(activityThread);
    }
}
