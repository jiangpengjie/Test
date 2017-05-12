package com.ost.jay.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ost.jay.settings.R;

import java.util.List;
import java.util.Map;

public class LanguageAdapter extends BaseAdapter{


	private Context context;
	private  LayoutInflater inflater;
	private List<Map<String, String>> listData;
	private int selected;
	ViewHolder holder;
	
	public LanguageAdapter(Context context) {
		super();
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(listData != null){
			return listData.size();
		}
		
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(listData != null){
			listData.get(arg0);
		}
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			convertView =View.inflate(context,R.layout.listview_item_com, null);
//			convertView = inflater.inflate(R.layout.listview_item_com, null);
			holder = new ViewHolder();
			holder.textViewTitle = (TextView) convertView.findViewById(R.id.item_right_text);
			holder.imageView = (ImageView)convertView.findViewById(R.id.item_right_image);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textViewTitle.setText(listData.get(position).get("item_right_text"));
		holder.imageView.setVisibility(View.GONE);
		
		if(selected == position) {
			holder.imageView.setVisibility(View.VISIBLE);
		}else {
			holder.imageView.setVisibility(View.GONE);
		}
		
		return convertView;

	}
	
	public void setData(List<Map<String, String>> listData) {
		this.listData = listData;
		notifyDataSetChanged();

	}
	
	public void setDataSelectChanged(int position) {
		if (position != selected)
		this.selected = position;
		notifyDataSetChanged();

	}
	
	class ViewHolder {
		TextView textViewTitle;
		ImageView imageView;
	}
}
