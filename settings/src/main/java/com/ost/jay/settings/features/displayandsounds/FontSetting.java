package com.ost.jay.settings.features.displayandsounds;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ost.jay.settings.R;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2017/5/3.
 */
public class FontSetting extends Activity implements View.OnClickListener,View.OnFocusChangeListener{

    private Configuration mconfig = new Configuration();

    private RadioButton font_small_radioButton;
    private RadioButton font_normal_radioButton;
    private RadioButton font_large_radioButton;
    private RadioButton font_huge_radioButton;
    private RadioGroup font_group;

    private DisplayMetrics mDisplayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_font);
        initView();
    }

    public void initView(){
        font_small_radioButton = (RadioButton) findViewById(R.id.font_small_radioButton);
        font_normal_radioButton = (RadioButton) findViewById(R.id.font_normal_radioButton);
        font_large_radioButton = (RadioButton) findViewById(R.id.font_large_radioButton);
        font_huge_radioButton = (RadioButton) findViewById(R.id.font_huge_radioButton);
        font_group = (RadioGroup) findViewById(R.id.font_group);



        font_small_radioButton.setOnClickListener(this);
        font_normal_radioButton.setOnClickListener(this);
        font_large_radioButton.setOnClickListener(this);
        font_huge_radioButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mDisplayMetrics = new DisplayMetrics();
        mDisplayMetrics.density = metrics.density;
        mDisplayMetrics.heightPixels = metrics.heightPixels;
        mDisplayMetrics.scaledDensity = metrics.scaledDensity;
        mDisplayMetrics.widthPixels = metrics.widthPixels;
        mDisplayMetrics.xdpi = metrics.xdpi;
        mDisplayMetrics.ydpi = metrics.ydpi;

        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.font_small_radioButton:
                /**
                 * 设置字体的大小值
                 */
                mconfig.fontScale = 0.85f;
                try {
                    /**
                     * 设置系统使用的字体大小值
                     */
                    ActivityManagerNative.getDefault()
                            .updatePersistentConfiguration(mconfig);
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.font_normal_radioButton:
                mconfig.fontScale = 1.00f;
                try {
                    ActivityManagerNative.getDefault()
                            .updatePersistentConfiguration(mconfig);
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.font_large_radioButton:
                mconfig.fontScale = 1.15f;
                try {
                    ActivityManagerNative.getDefault()
                            .updatePersistentConfiguration(mconfig);
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
            case R.id.font_huge_radioButton:
                mconfig.fontScale = 1.30f;
                try {
                    ActivityManagerNative.getDefault()
                            .updatePersistentConfiguration(mconfig);
                    finish();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }


    /**
     * 展示时候设置字体大小获取焦点
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        try {
            Class<?> activityManagerNative = Class
                    .forName("android.app.ActivityManagerNative");
            try {
                Object am = activityManagerNative.getMethod("getDefault")
                        .invoke(activityManagerNative);
                Object config = am.getClass().getMethod("getConfiguration")
                        .invoke(am);
                Configuration configs = (Configuration) config;
                mconfig.updateFrom(configs);
                if (mconfig.fontScale == 0.85f) {
                    font_small_radioButton.setChecked(true);
                } else if (mconfig.fontScale == 1.00f) {
                    font_normal_radioButton.setChecked(true);
                } else if (mconfig.fontScale == 1.15f) {
                    font_large_radioButton.setChecked(true);
                } else {
                    font_huge_radioButton.setChecked(true);
                }
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
