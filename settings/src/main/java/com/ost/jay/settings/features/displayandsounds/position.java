package com.ost.jay.settings.features.displayandsounds;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ost.jay.settings.R;

public class position extends Activity implements AdapterView.OnItemClickListener,
        View.OnClickListener, View.OnFocusChangeListener {


    private final int MAX_Height = 100;
    private final int MIN_Height = 80;

    private boolean isDisplayView = false;
    private int screen_rate = MIN_Height;
    ImageView img_num_hundred = null;
    ImageView img_num_ten = null;
    ImageView img_num_unit = null;
    ImageButton btn_position_zoom_out = null;
    ImageView img_progress_bg;
    ImageView  around_line;

    ScreenPositionManager mScreenPositionManager = null;

    private int Num[] = { R.drawable.ic_num0, R.drawable.ic_num1,
            R.drawable.ic_num2, R.drawable.ic_num3, R.drawable.ic_num4,
            R.drawable.ic_num5, R.drawable.ic_num6, R.drawable.ic_num7,
            R.drawable.ic_num8, R.drawable.ic_num9 };
    private int progressNum[] = { R.drawable.ic_per_81, R.drawable.ic_per_82,
            R.drawable.ic_per_83, R.drawable.ic_per_84, R.drawable.ic_per_85,
            R.drawable.ic_per_86, R.drawable.ic_per_87, R.drawable.ic_per_88,
            R.drawable.ic_per_89, R.drawable.ic_per_90, R.drawable.ic_per_91,
            R.drawable.ic_per_92, R.drawable.ic_per_93, R.drawable.ic_per_94,
            R.drawable.ic_per_95, R.drawable.ic_per_96, R.drawable.ic_per_97,
            R.drawable.ic_per_98, R.drawable.ic_per_99, R.drawable.ic_per_100 };


    ImageButton btn_position_zoom_in = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(R.style.perference_bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_position);
        openScreenAdjustView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (v instanceof ImageButton) {
            if (id == R.id.oobe_btn_position_zoom_in) {
                if (screen_rate > MIN_Height) {
                    showProgressUI(-1);
                    //mScreenPositionManager.zoomOut();
                    mScreenPositionManager.zoomByPercent(screen_rate);
                }
            }else if(id == R.id.oobe_btn_position_zoom_out){
                if(screen_rate < MAX_Height){
                    showProgressUI(1);
                    //mScreenPositionManager.zoomIn();
                    mScreenPositionManager.zoomByPercent(screen_rate);
                }
            }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    void openScreenAdjustView() {
        openScreenAdjustLayout();
        isDisplayView = true;
    }


    private void openScreenAdjustLayout() {

        img_num_hundred = (ImageView) findViewById(R.id.oobe_img_num_hundred);
        img_num_ten = (ImageView) findViewById(R.id.oobe_img_num_ten);
        img_num_unit = (ImageView) findViewById(R.id.oobe_img_num_unit);
        img_progress_bg = (ImageView) findViewById(R.id.oobe_img_progress_bg);
        around_line = (ImageView) findViewById(R.id.oobe_screen_adjust_line);

        btn_position_zoom_out = (ImageButton) findViewById(R.id.oobe_btn_position_zoom_out);
        btn_position_zoom_out.setOnClickListener(this);
        btn_position_zoom_in = (ImageButton) findViewById(R.id.oobe_btn_position_zoom_in);
        btn_position_zoom_in.setOnClickListener(this);
        mScreenPositionManager = new ScreenPositionManager(this);
        mScreenPositionManager.initPostion();
        screen_rate = mScreenPositionManager.getRateValue();

        showProgressUI(0);

        around_line.setVisibility(View.VISIBLE);

    }

    private void showProgressUI(int step) {
        screen_rate = screen_rate + step;
        if(screen_rate >MAX_Height){
            screen_rate = MAX_Height;
        }
        if(screen_rate <MIN_Height){
            screen_rate = MIN_Height ;
        }

        if (screen_rate <= MAX_Height && screen_rate >=100) {
            int hundred = Num[(int) screen_rate / 100];
            img_num_hundred.setVisibility(View.VISIBLE);
            img_num_hundred.setBackgroundResource(hundred);
            int ten = Num[(screen_rate -100)/10] ;
            img_num_ten.setBackgroundResource(ten);
            int unit = Num[(screen_rate -100)%10];
            img_num_unit.setBackgroundResource(unit);
            if (screen_rate - MIN_Height>= 0 && screen_rate - MIN_Height <= 19)
                img_progress_bg.setBackgroundResource(progressNum[screen_rate - MIN_Height]);
        } else if (screen_rate >= 10 && screen_rate <= 99) {
            img_num_hundred.setVisibility(View.GONE);
            int ten = Num[(int) (screen_rate / 10)];
            int unit = Num[(int) (screen_rate % 10)];
            img_num_ten.setBackgroundResource(ten);
            img_num_unit.setBackgroundResource(unit);
            if (screen_rate - MIN_Height >= 0 && screen_rate - MIN_Height <= 19)
                img_progress_bg.setBackgroundResource(progressNum[screen_rate - MIN_Height]);
        } else if (screen_rate >= 0 && screen_rate <= 9) {
            int unit = Num[screen_rate];
            img_num_unit.setBackgroundResource(unit);
        }

    }

    private void closeScreenAdjustLayout() {
        /*
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"===== save position now");
                    mScreenPositionManager.savePostion();
                }
            });
            t.start();
            */
        mScreenPositionManager.savePostion();
        around_line.setVisibility(View.GONE);
        ScreenPositionManager.mIsOriginWinSet = false;    //user has changed&save postion,reset this prop to default
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isDisplayView) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                btn_position_zoom_in.setBackgroundResource(R.drawable.minus_unfocus);
                btn_position_zoom_out.setBackgroundResource(R.drawable.plus_focus);
                if(screen_rate < MAX_Height){
                    showProgressUI(1);
                    //mScreenPositionManager.zoomIn();
                    mScreenPositionManager.zoomByPercent(screen_rate);
                }
                return true;

            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {

                if (screen_rate > MIN_Height) {
                    showProgressUI(-1);
                    //mScreenPositionManager.zoomOut();
                    mScreenPositionManager.zoomByPercent(screen_rate);
                }
                btn_position_zoom_in.setBackgroundResource(R.drawable.minus_focus);
                btn_position_zoom_out.setBackgroundResource(R.drawable.plus_unfocus);
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_BACK
                    || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                //Log.d(TAG, "===== closeScreenAdjustLayout() now !!!");
                closeScreenAdjustLayout();
            }
        }



        if (keyCode == KeyEvent.KEYCODE_BACK){
            closeScreenAdjustLayout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
