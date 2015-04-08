package com.larvafly.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.larvafly.bean.Location_bean;

public class Location_Adapter extends ArrayAdapter {


	LayoutInflater mInflater;
	ArrayList<Location_bean> arrSrc;

	//LinearLayout layout_card_close;
	//LinearLayout layout_card_open;	

	private static final String TAG = "";
	
	public Location_Adapter(Context context, int resource,ArrayList<Location_bean> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arrSrc = objects;
	}

//	public Location_Adapter(Context context, ArrayList<Location_bean> arrItem) {
//		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		arrSrc = arrItem;
//	}

	public int getCount() {
		return arrSrc.size();
	}

	public Location_bean getItem(int position) {
		return arrSrc.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return 1;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			int res = 0;
			res = android.R.layout.simple_spinner_item;
			convertView = mInflater.inflate(res, parent, false);				
		}

		TextView tv_nickname = (TextView)convertView.findViewById(android.R.id.text1);
		tv_nickname.setText(arrSrc.get(position).getLocation());


		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		if (convertView == null) {
			int res = 0;
			res = android.R.layout.simple_spinner_dropdown_item;
			convertView = mInflater.inflate(res, parent, false);				
		}

		TextView tv_nickname = (TextView)convertView.findViewById(android.R.id.text1);
//		tv_nickname.setTypeface(Static_date.myfont);
		tv_nickname.setText(arrSrc.get(position).getLocation());
		
		
		return convertView;
	}
	
	
	
}
