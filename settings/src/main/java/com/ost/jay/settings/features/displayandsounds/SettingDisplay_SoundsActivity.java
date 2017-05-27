package com.ost.jay.settings.features.displayandsounds;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.ost.jay.settings.BaseActivity;
import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class SettingDisplay_SoundsActivity extends BaseActivity implements View.OnClickListener{

    private Button resolution;
	private Button rotation;
	private Button daydream;
	private Button hdr;
	private Button system_sounds;
	private Button font_setting;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_display_sounds);
        initView();
        setListener();
    }
    
    private void initView() {

    	resolution = (Button) findViewById(R.id.button_resolution);
    	rotation = (Button) findViewById(R.id.button_rotation);
    	daydream = (Button) findViewById(R.id.button_daydream);
		font_setting = (Button) findViewById(R.id.button_fontsetting);

		resolution.setOnFocusChangeListener(mFocusChangeListener);
    	rotation.setOnFocusChangeListener(mFocusChangeListener);
    	daydream.setOnFocusChangeListener(mFocusChangeListener);
		font_setting.setOnFocusChangeListener(mFocusChangeListener);

    }

    private void setListener() {
    	resolution.setOnClickListener(this);
    	rotation.setOnClickListener(this);
    	daydream.setOnClickListener(this);
		font_setting.setOnClickListener(this);

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
    	Intent jumpIntent;
    	switch (view.getId()) {
			case R.id.button_resolution:
//				jumpIntent = new Intent(SettingDisplay_SoundsActivity.this,Resolution.class);
				jumpIntent = new Intent(SettingDisplay_SoundsActivity.this,ScreenResolution.class);
				startActivity(jumpIntent);

				break;
			case R.id.button_rotation:
				jumpIntent = new Intent(SettingDisplay_SoundsActivity.this,position.class);
				startActivity(jumpIntent);
				break;
			case R.id.button_daydream:
				jumpIntent = new Intent(SettingDisplay_SoundsActivity.this,Daydream.class);
				startActivity(jumpIntent);
				break;
			case R.id.button_fontsetting:
				jumpIntent = new Intent(SettingDisplay_SoundsActivity.this,FontSetting.class);
				startActivity(jumpIntent);
				break;
			default:
				break;
		}

    }
}
