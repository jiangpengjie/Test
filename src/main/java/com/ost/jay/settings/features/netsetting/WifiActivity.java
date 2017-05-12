package com.ost.jay.settings.features.netsetting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ost.jay.settings.R;
import com.ost.jay.settings.adapter.WAndB_WifilistAdapter;
import com.ost.jay.settings.utils.WiFiAdmin;

import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */
public class WifiActivity extends Activity implements View.OnClickListener,OnItemClickListener {


    private static final String TAG="1";
    private static final int WIFI_OPEN_FINISH=1;//开启完成
    private static final int WIFI_FOUND_FINISH=0;//查找完成
    private static final int WIFI_SCAN=2;//wifi扫描
    private static final int WIFI_CLOSE=3;//关闭wifi
    private static final int WIFI_INFO=4;
    private static final int WIFI_STATE_INIT=5;//加载页面
    private static final int WIFI_STATE_CONNECTED=6;//wifi连接状态成功
    private static final int WIFI_STATE_DISCONNECTED=7;//wifi连接状态失败


    private String connectSSID ="";
    private WiFiAdmin wiFiAdmin;
    private Switch wifiSwitch;
    private WAndB_WifilistAdapter adapter;
    private ListView wifiListView;
    private TextView wifiStateDisplay;
    private ImageView arrowTop;
    private Dialog connectDialog;
    private List<ScanResult> scanResults;
    private int netId;//WIFI连接状态

    Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WIFI_FOUND_FINISH:
                    scanResults=wiFiAdmin.GetWifilist();
                    adapter.notifyDataSetChanged();
                    break;
                case WIFI_STATE_INIT:
                    int wifiState=wiFiAdmin.GetWifiState();
                    if(wifiState==wiFiAdmin.getWifiManager().WIFI_STATE_DISABLED){  //wifi不可用啊
                        wifiStateDisplay.setText("WiFi 网卡未打开");
                    }else if(wifiState==wiFiAdmin.getWifiManager().WIFI_STATE_UNKNOWN){//wifi 状态未知
                        wifiStateDisplay.setText("WiFi 网卡状态未知");
                    }else if(wifiState==wiFiAdmin.getWifiManager().WIFI_STATE_ENABLED){//OK 可用
                        wifiSwitch.setChecked(true);
                        wiFiAdmin.StartScan();
                        scanResults =wiFiAdmin.GetWifilist();
                        handler.sendEmptyMessageDelayed(WIFI_SCAN, 1000);

                        if(wiFiAdmin.isWifiEnable()){
                            Toast.makeText(WifiActivity.this, "wifi已经打开", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(WifiActivity.this, "请 开启 wifi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                case WIFI_OPEN_FINISH:
                    scanResults=wiFiAdmin.GetWifilist();
                    adapter=new WAndB_WifilistAdapter(WifiActivity.this, scanResults);
                    wifiListView.setAdapter(adapter);
                    break;
                case  WIFI_SCAN:
                    wiFiAdmin.StartScan();
                    scanResults=wiFiAdmin.GetWifilist();
                    wifiStateDisplay.setText("正在扫描附近的WIFI...");
                    if(scanResults==null){
                        Toast.makeText(WifiActivity.this, "附近无可用wifi", Toast.LENGTH_SHORT).show();
                    }else if(scanResults.size()==0){
                        Toast.makeText(WifiActivity.this, "得不到附近wifi数据，正在重新获取", Toast.LENGTH_SHORT).show();
                        SetScanResult();
                    }else{
                        wifiStateDisplay.setText("附近WiFi");
                        adapter=new WAndB_WifilistAdapter(WifiActivity.this, scanResults);
                        wifiListView.setAdapter(adapter);

                    }
                    break;
                case WIFI_CLOSE:
                    wifiStateDisplay.setText("WIFI已成功关闭");
                    break;
                case WIFI_INFO:
                    if(wiFiAdmin.GetSSID().endsWith("<unknown ssid>")||wiFiAdmin.GetSSID().endsWith("NULL")){
                        wiFiAdmin.getWifiConnectInfo();
                        wifiStateDisplay.setText("无WIFI连接");
                    }else if(wiFiAdmin.GetSSID().equals("NULL")){
                        wiFiAdmin.getWifiConnectInfo();
                        wifiStateDisplay.setText("无连接,请选择合适的WiFi连接");
                    }else{
                        wiFiAdmin.getWifiConnectInfo();
                        if(wiFiAdmin.GetIntIp().equals("")){
                        }
                        connectDialog.dismiss();
                        wifiStateDisplay.setText("*****正在尝试连接*****请稍后");

                    }
                    break;
                //连接状态已连接上
                case WIFI_STATE_CONNECTED:
                    wifiStateDisplay.setText("");
                    wifiStateDisplay.setText("已连接到"+GetNowWifiSSID()+"若切换有线网络请连接网线");

                    break;
                //连接状态未连接
                case WIFI_STATE_DISCONNECTED:
                    wifiStateDisplay.setText("");
                    wifiStateDisplay.setText("未连接上，wifi连接已断开");


                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_wifi);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (Build.VERSION.SDK_INT >= 23 ) {
            Settings.Secure.putInt(getContentResolver(),Settings.Secure.LOCATION_MODE, 1);
        }
        initView();


    }


    public void initView(){
        wiFiAdmin=new WiFiAdmin(WifiActivity.this);
        connectDialog =new AlertDialog.Builder(WifiActivity.this).create();
        wifiListView =(ListView)findViewById(R.id.wifi_listview);
        wifiSwitch =(Switch)findViewById(R.id.wifi_switch);
        arrowTop =(ImageView)findViewById(R.id.wifi_arrowtop);
        wifiStateDisplay =(TextView)findViewById(R.id.wifi_statedispaly);
        wifiListView.setOnItemClickListener(this);
        wifiListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    arrowTop.setVisibility(View.INVISIBLE);
                }else{
                    arrowTop.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        wifiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifiSwitch.isChecked()){
                    wiFiAdmin.OpenWifi();
                    wiFiAdmin.StartScan();
                    scanResults = wiFiAdmin.GetWifilist();
                    handler.sendEmptyMessageDelayed(WIFI_SCAN, 1000);
                    Toast.makeText(WifiActivity.this, "打开 WiFi", Toast.LENGTH_SHORT).show();
                }else{
                    wiFiAdmin.CloseWifi();
                    handler.sendEmptyMessage(WIFI_CLOSE);
                    Toast.makeText(WifiActivity.this, "关闭 WiFi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        handler.sendEmptyMessageDelayed(WIFI_STATE_INIT, 1000);

        //注册广播监听
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mBroadcastReceiver, filter);


    }

    public void SetScanResult(){
        wiFiAdmin.StartScan();
        //得不到扫描列表数据
        scanResults=wiFiAdmin.GetWifilist();
        adapter=new WAndB_WifilistAdapter(WifiActivity.this, scanResults);
        wifiListView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

//        if(GetNowWifiSSID().equals("\""+scanResults.get(arg2).SSID+"\"") ){
//            Toast.makeText(WifiActivity.this, "当前已连接此网络", Toast.LENGTH_SHORT).show();
//        }else{
            final int num=arg2;
            LayoutInflater layoutInflater= LayoutInflater.from(WifiActivity.this);
            View view=layoutInflater.inflate(R.layout.connect_wifi_dialog, null);
            TextView wifiName=(TextView)view.findViewById(R.id.wifidialog_name);
            wifiName.setText(scanResults.get(arg2).SSID);
            connectDialog.show();
            connectDialog.getWindow().setContentView(view);
            Window dialogwWindow= connectDialog.getWindow();
            WindowManager.LayoutParams params=dialogwWindow.getAttributes();
            dialogwWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            params.x=0;
            params.y=0;
            params.width=750;//宽
            params.height=600;//高
            params.softInputMode=0;
            dialogwWindow.setAttributes(params);
            connectDialog.show();
            Button cancel=(Button)view.findViewById(R.id.wifi_dialog_cancel);
            Button connect=(Button)view.findViewById(R.id.wifi_dialog_connect);
             final EditText password=(EditText)view.findViewById(R.id.wifi_dialog_password);
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    connectDialog.dismiss();
                }
            });
            connect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    String wifiPassword = password.getText().toString();
                    if (wifiPassword.length()<8){
                        Toast.makeText(WifiActivity.this, "密码不能小于8位", Toast.LENGTH_SHORT).show();
                        password.setText("");

                    }else {
                        wiFiAdmin.AddNetwork(wiFiAdmin.CreatConfiguration(scanResults.get(num).SSID, wifiPassword, 3));
                        connectDialog.dismiss();


//                    if(netId ==0){
//                        Toast.makeText(WifiActivity.this, "无线网卡不可用", Toast.LENGTH_LONG).show();
//                    }else if(netId ==1){
//                        Toast.makeText(WifiActivity.this, "密码错误", Toast.LENGTH_LONG).show();
//                    }else if(netId ==2){
//                        Toast.makeText(WifiActivity.this, "正在连接", Toast.LENGTH_LONG).show();
//                        connectDialog.dismiss();
//                    }else if(netId ==-1){
//                        Toast.makeText(WifiActivity.this, "连接失败", Toast.LENGTH_LONG).show();
//                    }else{
//                        Toast.makeText(WifiActivity.this, "正在连接", Toast.LENGTH_LONG).show();
//                        connectDialog.dismiss();
//                    }
                    handler.sendEmptyMessageDelayed(WIFI_INFO, 2000);
                    }
                }
            });
            password.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View arg0, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    if(hasFocus){
                        connectDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    }else{

                    }
                }
            });
//        }
    }

    public String GetNowWifiSSID(){
        WifiManager mWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        return wifiInfo.getSSID();
    }





    private  BroadcastReceiver mBroadcastReceiver=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                Log.e("H3c", "wifiState" + wifiState);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        wifiStateDisplay.setText("WiFi 网卡未打开");
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        wifiStateDisplay.setText("WiFi 网卡状态未知");
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        break;
                    //
                }
            }
            // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
            // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent
                        .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;

                    NetworkInfo.State state = networkInfo.getState();
                    boolean isConnected = state== NetworkInfo.State.CONNECTED;//当然，这边可以更精确的确定状态
                    Log.d("H3c" ,"onReceive: isConnected"+isConnected);
                    if(isConnected){
//                        wifiStateDisplay.setText("已连接到"+GetNowWifiSSID()+"若切换有线网络请连接网线");
                    }else{

//                        wifiStateDisplay.setText("wifi网络连接已断开");
                    }

//                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)){
//                        //获取当前wifi名称
//                        wifiStateDisplay.setText("已连接到"+GetNowWifiSSID()+"若切换有线网络请连接网线");
//                    } else if(networkInfo.getState().equals(NetworkInfo.State.DISCONNECTED)){
//                        wifiStateDisplay.setText("wifi网络连接已断开");
//
//                    }
                }
            }
            // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
            // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
            // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo gprs = manager
//                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifi = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//                Log.i(TAG, "网络状态改变:" + wifi.isConnected() + " 3g:" + gprs.isConnected());
                NetworkInfo info = intent
                        .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                        Log.e("H3c", "info.getTypeName()" + info.getTypeName());
                        Log.e("H3c", "getSubtypeName()" + info.getSubtypeName());
                        Log.e("H3c", "getState()" + info.getState());
                        Log.e("H3c", "getDetailedState()"
                                + info.getDetailedState().name());
                        Log.e("H3c", "getDetailedState()" + info.getExtraInfo());
                        Log.e("H3c", "getType()" + info.getType());


                    if (NetworkInfo.State.CONNECTED == info.getState()) {
                         Log.e("2", "CONNECTED");
                        handler.sendEmptyMessageDelayed(WIFI_STATE_CONNECTED, 2500);
//                        wifiStateDisplay.setText("已连接到"+GetNowWifiSSID()+"若切换有线网络请连接网线");
//                        Log.e("H3c","进入*********wifi连接成功***********");
//
                    }else if (NetworkInfo.State.DISCONNECTED == info.getState()) {
                        Log.e("2", "DISCONNECTED");
                        handler.sendEmptyMessageDelayed(WIFI_STATE_DISCONNECTED, 2500);
//                            Log.e("H3c","进入*********wifi连接失败***********");
//                            wifiStateDisplay.setText("wifi连接失败")
                    }
                }
            }
        }

    };
}
