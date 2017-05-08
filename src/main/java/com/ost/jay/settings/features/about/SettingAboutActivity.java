package com.ost.jay.settings.features.about;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class SettingAboutActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_setting_about);

        initViews();
    }

    private void initViews() {
        String temp = null;
        TextView model_number_value = (TextView) findViewById(R.id.model_number_value);
        String productModel = SystemInfoManager.getModelNumber();
        model_number_value.setText(productModel);


        TextView firmware_version_value = (TextView) findViewById(R.id.firmware_version_value);
        temp =  SystemInfoManager.getAndroidVersion();
        firmware_version_value.setText(temp);


        TextView build_number_value = (TextView) findViewById(R.id.build_number_value);
            build_number_value.setText( SystemInfoManager.getBuildNumber());

        TextView kernel_version_value = (TextView) findViewById(R.id.kernel_version_value);
        temp = SystemInfoManager.getKernelVersion();
        kernel_version_value.setText(temp);


    }

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

    }
}
