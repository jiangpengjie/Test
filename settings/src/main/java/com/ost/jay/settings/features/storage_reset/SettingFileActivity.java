package com.ost.jay.settings.features.storage_reset;

import android.app.Activity;
import android.os.Bundle;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class SettingFileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.setContentView(R.layout.activity_setting_file);
        setTheme(R.style.perference_bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bg);
        getFragmentManager().beginTransaction().add(R.id.prefs,new StorageFragment()).commit();
    }



}
