package com.android.jv.ink.plugintest.demo_as;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.jv.ink.plugintest.demo_as.adapter.MainAdapter;
import com.android.jv.ink.plugintest.demo_as.fragment.PluginFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "host";

    private ViewPager mViewPager;
    private TextView mPageIndexTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPageIndexTv = (TextView) findViewById(R.id.activity_main_page_index_tv);
        mViewPager = (ViewPager) findViewById(R.id.activity_main_vp);
        initViewPager();
    }

    private void initViewPager() {
        PluginFragment[] fragmentList = new PluginFragment[]{
                PluginFragment.newInstance("com.android.jv.ink.plugintest.plugin", "activity_main"),
                PluginFragment.newInstance("com.android.jv.ink.plugintest.plugin", "activity_main"),
                PluginFragment.newInstance("com.android.jv.ink.plugintest.plugin", "activity_main"),
        };
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), Arrays.asList(fragmentList));
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPageIndexTv.setText("第" + (position + 1) + "页");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
