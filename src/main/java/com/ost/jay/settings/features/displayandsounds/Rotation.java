package com.ost.jay.settings.features.displayandsounds;

import android.app.Activity;
import android.os.Bundle;

import com.ost.jay.settings.R;

public class Rotation extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(R.style.perference_bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bg);
        getFragmentManager().beginTransaction().add(R.id.prefs,new RotationPrefsFragment()).commit();
    }

}
