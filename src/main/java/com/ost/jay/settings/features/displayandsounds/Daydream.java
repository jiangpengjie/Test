package com.ost.jay.settings.features.displayandsounds;

import android.app.Activity;
import android.os.Bundle;

import com.ost.jay.settings.R;

public class Daydream extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.perference_bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bg);
        getFragmentManager().beginTransaction().add(R.id.prefs,new DaydreamPrefsFragment()).commit();

    }

}
