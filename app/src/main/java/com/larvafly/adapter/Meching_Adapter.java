package com.larvafly.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.larvafly.bean.Location_bean;
import com.larvafly.bean.Room_bean;
import com.larvafly.campuscarpul.R;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.lib.Img_bitmap;
import com.larvafly.lib.Static_date;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Meching_Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Room_bean> arrSrc;
    private Activity activity;
    private HTTP_Handler http_Handler;
    private AQuery aQuery;

    public Meching_Adapter(final Activity activity, ArrayList<Room_bean> arrItem, HTTP_Handler http_Handler) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


        ImageView main_room_card_profileimg_iv = (ImageView) convertView.findViewById(R.id.main_room_card_profileimg_iv);

        if (!arrSrc.get(position).getOrder_img_uri().equals("-1")) {

            aQuery.id(main_room_card_profileimg_iv).image(arrSrc.get(position).getOrder_img_uri(), true, true, Img_bitmap.dptopx(activity, 92), 0, new BitmapAjaxCallback() {

                @Override
                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                    iv.setImageBitmap(Img_bitmap.getCircleBitmap(bm));
//
                }

            });
        }

        RelativeLayout main_room_card_back_rl = (RelativeLayout) convertView.findViewById(R.id.main_room_card_back_rl);
//

        ImageView main_room_card_state_iv = (ImageView) convertView.findViewById(R.id.main_room_card_state_iv);

        if (arrSrc.get(position).ismyorder()) {
            main_room_card_state_iv.setImageResource(R.drawable.my_seal);
            main_room_card_state_iv.setVisibility(View.VISIBLE);
            main_room_card_back_rl.setBackgroundResource(R.drawable.main_card_y);

        }
        if (arrSrc.get(position).ismyjoin()) {
            main_room_card_state_iv.setImageResource(R.drawable.join_seal);
            main_room_card_state_iv.setVisibility(View.VISIBLE);
            main_room_card_back_rl.setBackgroundResource(R.drawable.main_card_y);
        }
        if (arrSrc.get(position).isendroom()) {
            main_room_card_state_iv.setImageResource(R.drawable.end_seal);
            main_room_card_state_iv.setVisibility(View.VISIBLE);
        }

        if (!(arrSrc.get(position).isendroom() || arrSrc.get(position).ismyjoin() || arrSrc.get(position).ismyorder())) {
            main_room_card_state_iv.setVisibility(View.GONE);
            main_room_card_back_rl.setBackgroundResource(R.drawable.main_card_b);
        }

        ImageView main_room_card_people_1 = (ImageView) convertView.findViewById(R.id.main_room_card_people_1);
        ImageView main_room_card_people_2 = (ImageView) convertView.findViewById(R.id.main_room_card_people_2);
        ImageView main_room_card_people_3 = (ImageView) convertView.findViewById(R.id.main_room_card_people_3);
        ImageView main_room_card_people_4 = (ImageView) convertView.findViewById(R.id.main_room_card_people_4);

        TextView main_room_card_start_loc_tv = (TextView) convertView.findViewById(R.id.main_room_card_start_loc_tv);
        TextView main_room_card_end_loc_tv = (TextView) convertView.findViewById(R.id.main_room_card_end_loc_tv);

        TextView main_room_card_day_tv = (TextView) convertView.findViewById(R.id.main_room_card_day_tv);
        TextView main_room_card_time_tv = (TextView) convertView.findViewById(R.id.main_room_card_time_tv);

//        오전  08 : 50"2015 . 03 . 12"


//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        Date date = arrSrc.get(position).getTime();
        Date todate = new Date(System.currentTimeMillis());
        Date nextdate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        if (date.getDate() == todate.getDate() && date.getYear() == todate.getYear() && date.getMonth() == todate.getMonth()) {

            main_room_card_day_tv.setText("오늘");
            main_room_card_day_tv.setTextColor(activity.getResources().getColor(R.color.point));

        } else if (date.getYear() == nextdate.getYear() && date.getMonth() == nextdate.getMonth() && date.getDate() == nextdate.getDate()) {

            main_room_card_day_tv.setText("내일");
            main_room_card_day_tv.setTextColor(activity.getResources().getColor(R.color.lightblue_500));

        } else {
            SimpleDateFormat dayFormat = new SimpleDateFormat("MM월 dd일 (E)");
            main_room_card_day_tv.setText(dayFormat.format(date));

        }


        SimpleDateFormat timeFormat = new SimpleDateFormat("a  hh : mm");
        main_room_card_time_tv.setText(timeFormat.format(date));

        int start_loc = arrSrc.get(position).getStart_locale();
        switch (start_loc) {
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
        switch (end_loc) {
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


        int max_personnerl = arrSrc.get(position).getMax_personnerl();
        switch (max_personnerl) {

            case 1:
                main_room_card_people_1.setVisibility(View.VISIBLE);
                main_room_card_people_2.setVisibility(View.GONE);
                main_room_card_people_3.setVisibility(View.GONE);
                main_room_card_people_4.setVisibility(View.GONE);

                break;
            case 2:
                main_room_card_people_1.setVisibility(View.VISIBLE);
                main_room_card_people_2.setVisibility(View.VISIBLE);
                main_room_card_people_3.setVisibility(View.GONE);
                main_room_card_people_4.setVisibility(View.GONE);

                break;
            case 3:
                main_room_card_people_1.setVisibility(View.VISIBLE);
                main_room_card_people_2.setVisibility(View.VISIBLE);
                main_room_card_people_3.setVisibility(View.VISIBLE);
                main_room_card_people_4.setVisibility(View.GONE);

                break;
            case 4:
                main_room_card_people_1.setVisibility(View.VISIBLE);
                main_room_card_people_2.setVisibility(View.VISIBLE);
                main_room_card_people_3.setVisibility(View.VISIBLE);
                main_room_card_people_4.setVisibility(View.VISIBLE);
                break;


        }

        int now_personnerl = arrSrc.get(position).getNow_personnerl();
        switch (now_personnerl) {
            case 0:
                main_room_card_people_1.setImageResource(R.drawable.main_no_people);
                main_room_card_people_2.setImageResource(R.drawable.main_no_people);
                main_room_card_people_3.setImageResource(R.drawable.main_no_people);
                main_room_card_people_4.setImageResource(R.drawable.main_no_people);

                break;
            case 1:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_2.setImageResource(R.drawable.main_no_people);
                main_room_card_people_3.setImageResource(R.drawable.main_no_people);
                main_room_card_people_4.setImageResource(R.drawable.main_no_people);

                break;
            case 2:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_2.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_3.setImageResource(R.drawable.main_no_people);
                main_room_card_people_4.setImageResource(R.drawable.main_no_people);

                break;
            case 3:
                main_room_card_people_1.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_2.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_3.setImageResource(R.drawable.main_yes_people);
                main_room_card_people_4.setImageResource(R.drawable.main_no_people);

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


