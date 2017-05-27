package com.ost.jay.settings.features.displayandsounds;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/4/18.
 */
public class ResolutionPrefsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener,SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String KEY_SCREEN_RESOLUTION = "screen_resolution ";
    private ListPreference mScreen_resolutionPreference;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final Activity activity = getActivity();
        final ContentResolver resolver = activity.getContentResolver();

        addPreferencesFromResource(R.xml.resolution_preference);

        mScreen_resolutionPreference = (ListPreference) findPreference(KEY_SCREEN_RESOLUTION);

        mScreen_resolutionPreference.setOnPreferenceChangeListener(this);


    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
