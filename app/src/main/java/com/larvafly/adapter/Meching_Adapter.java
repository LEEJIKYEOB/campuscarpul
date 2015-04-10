package com.larvafly.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.androidquery.AQuery;
import com.larvafly.bean.Location_bean;
import com.larvafly.bean.Room_bean;
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

import org.w3c.dom.Text;

public class Meching_Adapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Room_bean> arrSrc;
	private Activity activity;
	private HTTP_Handler http_Handler;
	private AQuery aQuery;

	public Meching_Adapter(final Activity activity, ArrayList<Room_bean> arrItem, HTTP_Handler http_Handler) {
		mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arrSrc = arrItem;
		this.activity = activity;
		this.http_Handler = http_Handler;
		aQuery = new AQuery(activity);

	}

	public int getCount() {
		return arrSrc.size();
	}

	public Room_bean getItem(int position) {
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

        ImageView main_room_card_people_1 = (ImageView)convertView.findViewById(R.id.main_room_card_people_1);
        ImageView main_room_card_people_2 = (ImageView)convertView.findViewById(R.id.main_room_card_people_2);
        ImageView main_room_card_people_3 = (ImageView)convertView.findViewById(R.id.main_room_card_people_3);
        ImageView main_room_card_people_4 = (ImageView)convertView.findViewById(R.id.main_room_card_people_4);

        TextView main_room_card_start_loc_tv = (TextView)convertView.findViewById(R.id.main_room_card_start_loc_tv);
        TextView main_room_card_end_loc_tv = (TextView)convertView.findViewById(R.id.main_room_card_end_loc_tv);

        TextView main_room_card_day_tv = (TextView)convertView.findViewById(R.id.main_room_card_day_tv);
        TextView main_room_card_time_tv = (TextView)convertView.findViewById(R.id.main_room_card_time_tv);

//        오전  08 : 50"2015 . 03 . 12"



//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        Date date = arrSrc.get(position).getTime();

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년  MM월  dd일  (E)");

        SimpleDateFormat dayFormat = new SimpleDateFormat("MM월 dd일 (E)");
        main_room_card_day_tv.setText(dayFormat.format(date));
        SimpleDateFormat timeFormat = new SimpleDateFormat("a  hh : mm");
        main_room_card_time_tv.setText(timeFormat.format(date));

        int start_loc = arrSrc.get(position).getStart_locale();
        switch (start_loc){
            case 1:
                main_room_card_start_loc_tv.setText(Location_bean.idx_1);
                break;
            case 2:
                main_room_card_start_loc_tv.setText(Location_bean.idx_2);
                break;
            case 3:
                main_room_card_start_loc_tv.setText(Location_bean.idx_3);
                break;
        }

        int end_loc = arrSrc.get(position).getEnd_locale();
        switch (end_loc){
            case 1:
                main_room_card_end_loc_tv.setText(Location_bean.idx_1);
                break;
            case 2:
                main_room_card_end_loc_tv.setText(Location_bean.idx_2);
                break;
            case 3:
                main_room_card_end_loc_tv.setText(Location_bean.idx_3);
                break;
        }



        int now_personnerl = arrSrc.get(position).getNow_personnerl();
        switch (now_personnerl){
            case 0:
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);

                break;
            case 1:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);

                break;
            case 2:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);

                break;
            case 3:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);

                break;
            case 4:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);

                break;


        }


		return convertView;
	}



}


