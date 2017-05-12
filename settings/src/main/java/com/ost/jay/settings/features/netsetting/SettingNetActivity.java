package com.ost.jay.settings.features.netsetting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class SettingNetActivity extends Activity implements View.OnClickListener{

    private TextView wifi;
    private TextView ethernet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_setting_net);
        initViews();
        setListener();
    }

    private void initViews() {

        wifi = (TextView) findViewById(R.id.setting_custom_wifi);
        ethernet = (TextView) findViewById(R.id.setting_custom_ethernet);

    }

    private void setListener() {

        wifi.setOnClickListener(this);
        ethernet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent();

        switch (v.getId()) {
            case R.id.setting_custom_wifi:
                i.setClass(this, WifiActivity.class);
                startActivity(i);
                break;
            case R.id.setting_custom_ethernet:
                i.setClass(this, EthernetActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

    }
}
