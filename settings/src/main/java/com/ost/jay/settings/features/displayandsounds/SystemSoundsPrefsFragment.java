package com.ost.jay.settings.features.displayandsounds;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/4/19.
 */
public class SystemSoundsPrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.systemsounds_preference);
    }
}
