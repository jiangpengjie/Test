package com.ost.jay.settings.features.displayandsounds;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.droidlogic.app.SystemControlManager;
import com.ost.jay.settings.R;
import com.ost.jay.settings.utils.Utils;

/**
 * Created by Administrator on 2017/5/25.
 */
public class ScreenResolution extends Activity implements View.OnClickListener,
        View.OnFocusChangeListener,AdapterView.OnItemClickListener{

    private String TAG = "ScreenResolution";

    private final Context mContext = this;
    private PopupWindow popupWindow = null;
    private ActivityManager mAm;
    private SystemControlManager sw = null;
    private MboxOutPutModeManager mOutPutModeManager;
    private LinearLayout screen_self_set = null;
    private TextView self_select_mode = null;
    private TextView auto_set_screen = null;
    private TextView current_mode_value = null;
    private SharedPreferences sharepreference = null;
    private LinearLayout secreen_auto = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_resolution);

        mAm = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        sw = new SystemControlManager(this);
        mOutPutModeManager = new MboxOutPutModeManager(this);

        screen_self_set = (LinearLayout) findViewById(R.id.screen_self_set);
        screen_self_set.setOnClickListener(this);
        self_select_mode = (TextView) findViewById(R.id.self_select_mode);
//        auto_set_screen = (TextView) findViewById(R.id.auto_set_screen);

        current_mode_value = (TextView) findViewById(R.id.current_mode_value);
        current_mode_value.setText(mOutPutModeManager.getCurrentOutPutModeTitle(1));

//        secreen_auto = (LinearLayout) findViewById(R.id.secreen_auto);
//        secreen_auto.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (mOutPutModeManager.ifModeIsSetting()){
            return;
        }

        switch (v.getId()){
            case R.id.screen_self_set:
                openOutPutModePopupWindow("hdmi");
                break;
//            case R.id.secreen_auto:
//                setAutoOutModeSwitch();
//                break;
            default:
                break;
        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void openOutPutModePopupWindow(final String mode) {

        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View outPutView = (View) mLayoutInflater.inflate(R.layout.out_mode_popup_window, null, true);

        ListView listview = (ListView) outPutView.findViewById(R.id.output_list);
        final MboxOutPutModeManager output = new MboxOutPutModeManager(mContext, listview, mode);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int index, long id) {
                if(index == output.getCurrentModeIndex()){
                    return ;
                }
                popupWindow.dismiss();
                output.selectItem(index);
                current_mode_value.setText((mOutPutModeManager.getCurrentOutPutModeTitle(1)));

            }
        });

        popupWindow = new PopupWindow(outPutView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(outPutView, Gravity.CENTER, 0, 0);
        popupWindow.update();

    }
    void setAutoOutModeSwitch() {
        boolean isAutoHdmiMode = getAutoHDMIMode();
        if (isAutoHdmiMode) {
            // Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.DISPLAY_OUTPUTMODE_AUTO, 0);
        } else {
            // Settings.Global.putInt(mContext.getContentResolver(), Settings.Global.DISPLAY_OUTPUTMODE_AUTO, 1);
            mOutPutModeManager.hdmiPlugged();

        }
        upDateOutModeUi();

    }

    boolean getAutoHDMIMode() {
        boolean isAutoHdmiMode = false;
       /* try {
            isAutoHdmiMode = ((0 == Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.DISPLAY_OUTPUTMODE_AUTO))?false:true) ;
        } catch (Settings.SettingNotFoundException se) {
            Log.d(TAG, "Error: "+se);
        }*/
        return isAutoHdmiMode;
    }

    void upDateOutModeUi() {
        boolean isAutoHdmiMode = getAutoHDMIMode();
        if (isAutoHdmiMode) {
            screen_self_set.setFocusable(false);
            screen_self_set.setClickable(false);
            self_select_mode.setTextColor(Color.GRAY);
            current_mode_value.setTextColor(Color.GRAY);
            auto_set_screen.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.on, 0);
            //Message msg = mHander.obtainMessage();
            //msg.what = UPDATE_OUTPUT_MODE_UI ;
            //mHander.sendMessage(msg);

        } else {
//            Drawable auto_screen=getResources().getDrawable(R.drawable.off);
//            auto_screen.setBounds(0,0,auto_screen.getMinimumWidth(),auto_screen.getMinimumHeight());
//            auto_set_screen.setCompoundDrawables(null,null,auto_screen,null);
            auto_set_screen.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.off, 0);
            screen_self_set.setFocusable(true);
            screen_self_set.setClickable(true);
            self_select_mode.setTextColor(Color.WHITE);
            current_mode_value.setTextColor(Color.WHITE);
        }

        boolean isDualOutPutMode = sw.getPropertyBoolean("ro.platform.has.cvbsmode", false);
        if(!isDualOutPutMode){
            if(mOutPutModeManager.isHDMIPlugged()){
//                secreen_auto.setFocusable(true);
//                secreen_auto.setClickable(true);
//                auto_set_screen.setTextColor(Color.WHITE);
                if (Utils.DEBUG) Log.d(TAG,"===== hdmi mode : " +  mOutPutModeManager.getCurrentOutPutModeTitle(1));
                current_mode_value.setText(mOutPutModeManager.getCurrentOutPutModeTitle(1));
            }else{
//                secreen_auto.setFocusable(false);
//                secreen_auto.setClickable(false);
                screen_self_set.setFocusable(false);
                screen_self_set.setClickable(false);
                self_select_mode.setTextColor(Color.GRAY);
                current_mode_value.setTextColor(Color.GRAY);
//                auto_set_screen.setTextColor(Color.GRAY);
            }

        }else{
            current_mode_value.setText(mOutPutModeManager.getCurrentOutPutModeTitle(1));
        }
    }

    protected void onDestroy() {

        if(popupWindow!=null){
            popupWindow.dismiss();
            popupWindow = null;
        }
        super.onDestroy();
    }
}
