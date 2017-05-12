package com.ost.jay.settings.features.displayandsounds;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/5/3.
 */
public class FontSettingPrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fontsetting_preference);
    }


}
