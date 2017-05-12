package com.ost.jay.settings.features.language;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.ost.jay.settings.R;

/**
 * Created by Administrator on 2017/3/23.
 */
public class LanguageActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.perference_bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_bg);
//
        getFragmentManager().beginTransaction().add(R.id.prefs,new LanguageFragment()).commit();

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

}
