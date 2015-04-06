package com.larvafly.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.androidquery.AQuery;
import com.larvafly.bean.Meching_bean;
import com.larvafly.campuscarpul.R;
import com.larvafly.http.HTTP_Handler;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Meching_Adapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Meching_bean> arrSrc;
	private Activity activity;
	private HTTP_Handler http_Handler;
	private AQuery aQuery;

	public Meching_Adapter(final Activity activity, ArrayList<Meching_bean> arrItem, HTTP_Handler http_Handler) {
		mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arrSrc = arrItem;
		this.activity = activity;
		this.http_Handler = http_Handler;
		aQuery = new AQuery(activity);

	}

	public int getCount() {
		return arrSrc.size();
	}

	public Meching_bean getItem(int position) {
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
			res = R.layout.widget_meching_card;
			convertView = mInflater.inflate(res, parent, false);
		}


		return convertView;
	}



}


