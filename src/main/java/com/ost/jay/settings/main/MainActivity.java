package com.ost.jay.settings.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.ost.jay.settings.BaseActivity;
import com.ost.jay.settings.R;
import com.ost.jay.settings.features.about.SettingAboutActivity;
import com.ost.jay.settings.features.displayandsounds.SettingDisplay_SoundsActivity;
import com.ost.jay.settings.features.language.LanguageActivity;
import com.ost.jay.settings.features.netsetting.SettingNetActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton mNetworkSetting;
    private ImageButton mOtherSetting;
    private ImageButton mNetworkSpeed;
    private ImageButton mSysUpdate;
    private ImageButton mFileManage;
    private ImageButton mAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

    }

    private void initView() {

        mNetworkSetting = (ImageButton)this.findViewById(R.id.setting_net);
        mOtherSetting = (ImageButton) this.findViewById(R.id.setting_more);
        mNetworkSpeed = (ImageButton) this.findViewById(R.id.setting_net_speed);
        mSysUpdate = (ImageButton) this.findViewById(R.id.setting_update);
        mFileManage = (ImageButton) this.findViewById(R.id.setting_file);
        mAbout = (ImageButton) this.findViewById(R.id.setting_about);


        mNetworkSetting.setOnFocusChangeListener(mFocusChangeListener);
        mOtherSetting.setOnFocusChangeListener(mFocusChangeListener);
        mNetworkSpeed.setOnFocusChangeListener(mFocusChangeListener);
        mSysUpdate.setOnFocusChangeListener(mFocusChangeListener);
        mFileManage.setOnFocusChangeListener(mFocusChangeListener);
        mAbout.setOnFocusChangeListener(mFocusChangeListener);
    }

    private void setListener() {

        mAbout.setOnClickListener(this);
        mOtherSetting.setOnClickListener(this);
        mNetworkSetting.setOnClickListener(this);
        mFileManage.setOnClickListener(this);
        mNetworkSpeed.setOnClickListener(this);
        mSysUpdate.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                return false;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                return false;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                return true;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                return false;
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                return false;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        Intent jumpIntent;
        switch (view.getId()) {
            case R.id.setting_about:
                jumpIntent = new Intent(this, SettingAboutActivity.class);
//                jumpIntent=new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS  );
                startActivity(jumpIntent);
                break;
            case R.id.setting_more:
                try {
                    jumpIntent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(jumpIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setting_file:
//                jumpIntent = new Intent(this, SettingFileActivity.class);
//                jumpIntent = new Intent(this, SettingDisplay_SoundsActivity.class);
                jumpIntent=new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                startActivity(jumpIntent);
                break;
            case R.id.setting_update:
                jumpIntent = new Intent(this, LanguageActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.setting_net:
                jumpIntent = new Intent(this, SettingNetActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.setting_net_speed:
//                jumpIntent = new Intent(this, SpeedTestActivity.class);
                jumpIntent = new Intent(this, SettingDisplay_SoundsActivity.class);
//                jumpIntent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                startActivity(jumpIntent);
                break;
            default:
                break;
        }

    }
}
