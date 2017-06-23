package com.android.jv.ink.plugintest.demo_as;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.jv.ink.plugintest.demo_as.fragment.PluginFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "host";

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.activity_main_vp);
        initViewPager();
    }

    private void initViewPager() {
        PluginFragment[] fragmentList = new PluginFragment[]{
                PluginFragment.newInstance("com.android.jv.ink.plugintest.plugin","activity_main");
                PluginFragment.newInstance("com.android.jv.ink.plugintest.plugin","activity_main");
                PluginFragment.newInstance("com.android.jv.ink.plugintest.plugin","activity_main");
        }
    }
}
