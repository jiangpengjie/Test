package com.ost.jay.settings.adapter;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ost.jay.settings.R;

import java.util.List;

public class WAndB_WifilistAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<ScanResult> scanResults;
    private Viewholder viewholder;
    private final Activity context;

    int wifi_level [] =new int[]{R.drawable.wifi_1,R.drawable.wifi_2,R.drawable.wifi_3,R.drawable.network_state_on};

    public WAndB_WifilistAdapter(Activity context, List<ScanResult> scanResults) {
        inflater = LayoutInflater.from(context);
        this.scanResults = scanResults;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (scanResults.size() == 0) {
            return 0;
        }
        return scanResults.size();
    }

    @Override
    public Object getItem(int arg0) {
        return scanResults.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            viewholder = new Viewholder();
            convertView= inflater.inflate(R.layout.wandb_wifilist_item, null);
            viewholder.wifiName = (TextView) convertView.findViewById(R.id.wannb_wifilist_item_wifiname);
            viewholder.wifiImage= (ImageView) convertView.findViewById(R.id.item_wifi_img);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.wifiName.setText(scanResults.get(position).SSID);
        viewholder.wifiImage.setImageResource(wifi_level[WifiManager.calculateSignalLevel(scanResults.get(position).level, 4)]);
        viewholder.arrowTop = (ImageView) context.findViewById(R.id.wifi_arrowtop);
        viewholder.arrowBottom = (ImageView) context.findViewById(R.id.wifi_arrowbottom);
        if (position == scanResults.size() - 1) {
            viewholder.arrowBottom.setVisibility(View.INVISIBLE);
        } else {
            viewholder.arrowBottom.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class Viewholder {
        public TextView wifiName;
        public ImageView arrowTop;
        public ImageView arrowBottom;
        public ImageView wifiImage;
    }

}
