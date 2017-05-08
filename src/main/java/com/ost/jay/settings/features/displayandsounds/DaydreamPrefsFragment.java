package com.ost.jay.settings.features.displayandsounds;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/4/18.
 */
public class DaydreamPrefsFragment extends PreferenceFragment {

//    public static final String SCREEN_OFF_TIMEOUT = "screen_off_timeout";
//    private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 1800000;
    private ListPreference mScreenTimeoutPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.daydream_preference);

    }


}
