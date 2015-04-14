package com.larvafly.campuscarpul;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.larvafly.adapter.Location_Adapter;
import com.larvafly.bean.Location_bean;
import com.larvafly.bean.Room_bean;
import com.larvafly.campuscarpul.R;
import com.larvafly.http.ADD_ROOM_HTTP;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.JOIN_ROOM_HTTP;
import com.larvafly.lib.Img_bitmap;
import com.larvafly.lib.Static_date;
import com.rengwuxian.materialedittext.MaterialEditText;

import me.drakeet.materialdialog.MaterialDialog;

public class Add_room extends ActionBarActivity implements View.OnClickListener, HTTP_Handler.OnHttpReceiveListener {


    private static ButtonFlat add_room_time_btn;
    private static ButtonFlat add_room_day_btn;
    private static Date date;
    private Button start_location_btn;
    private Button end_location_btn;
    private ImageButton max_people_1_btn;
    private ImageButton max_people_2_btn;
    private ImageButton max_people_3_btn;
    private ImageButton max_people_4_btn;
    private ButtonRectangle add_room_ok_btr;
    private MaterialEditText add_room_carnum_met;
    private TextView add_room_ordername_tv;
    //    add_room_time_btn
    private ImageView add_room_orderimg_iv;
    private int start_location_id = 1;
    private int end_location_id = 3;
    private int max_people = 2;
    private String carnum = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("카풀생성");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        toolbar.setLogo(R.drawable.ic_car);
//        setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon();


        Resources r = getResources();

        getSupportFragmentManager();


        add_room_orderimg_iv = (ImageView) findViewById(R.id.add_room_orderimg_iv);

        AQuery aQuery = new AQuery(this);

        if (!Static_date.myProfile.getProfileimage().equals("-1")) {

            aQuery.id(add_room_orderimg_iv).image(Static_date.myProfile.getProfileimage(), true, true, Img_bitmap.dptopx(this, 92), 0, new BitmapAjaxCallback() {

                @Override
                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                    iv.setImageBitmap(Img_bitmap.getCircleBitmap(bm));
//
                }

            });
        }


        add_room_ordername_tv = (TextView) findViewById(R.id.add_room_ordername_tv);
        add_room_ordername_tv.setText("운전자 " + Static_date.myProfile.getName());

        start_location_btn = (Button) findViewById(R.id.start_location_btn);
        start_location_btn.setOnClickListener(this);
        end_location_btn = (Button) findViewById(R.id.end_location_btn);
        end_location_btn.setOnClickListener(this);

        max_people_1_btn = (ImageButton) findViewById(R.id.max_people_1_btn);
        max_people_2_btn = (ImageButton) findViewById(R.id.max_people_2_btn);
        max_people_3_btn = (ImageButton) findViewById(R.id.max_people_3_btn);
        max_people_4_btn = (ImageButton) findViewById(R.id.max_people_4_btn);

        max_people_1_btn.setOnClickListener(this);
        max_people_2_btn.setOnClickListener(this);
        max_people_3_btn.setOnClickListener(this);
        max_people_4_btn.setOnClickListener(this);

        date = new Date(System.currentTimeMillis());


        add_room_time_btn = (ButtonFlat) findViewById(R.id.add_room_time_btn);
        add_room_day_btn = (ButtonFlat) findViewById(R.id.add_room_day_btn);
        add_room_time_btn.setOnClickListener(this);
        add_room_day_btn.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy년  MM월  dd일  (E)");
        add_room_day_btn.setText(simpleDateFormat.format(date));

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("a  hh : mm");
        add_room_time_btn.setText(simpleDateFormat2.format(date));


        add_room_carnum_met = (MaterialEditText) findViewById(R.id.add_room_carnum_met);

        add_room_carnum_met.setText(Static_date.myProfile.getCar_number());
        add_room_ok_btr = (ButtonRectangle) findViewById(R.id.add_room_ok_btr);
        add_room_ok_btr.setOnClickListener(this);

    }


    public void showimgdialog(final ArrayList<Location_bean> arrayList, final Boolean start) {
        // TODO Auto-generated method stub


//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(
//                InputMethodManager.HIDE_NOT_ALWAYS,
//                InputMethodManager.HIDE_IMPLICIT_ONLY
//        );

        ListView listView;
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item
        );

        for (int i = 0; i < arrayList.size(); i++) {

            arrayAdapter.add(arrayList.get(i).getLocation());

        }


        listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(1);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test", "position = " + position);
                Log.d("test", "view = " + view.getId());
                Log.d("test", "id = " + id);

                if (start) {

                    start_location_btn.setText(arrayList.get(position).getLocation());
                    start_location_id = arrayList.get(position).getId();

                    if (arrayList.get(position).getId() == 3) {

                        end_location_btn.setText("대연");
                        end_location_id = 1;

                    }

                } else {
                    end_location_btn.setText(arrayList.get(position).getLocation());
                    end_location_id = arrayList.get(position).getId();

                }
                mMaterialDialog.dismiss();

            }
        });


        if (start) mMaterialDialog.setTitle("출발지 선택");
        else mMaterialDialog.setTitle("도착지 선택");

        mMaterialDialog.setContentView(listView);
        mMaterialDialog.setCanceledOnTouchOutside(true);
        mMaterialDialog.setPositiveButton("취소", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });


        mMaterialDialog.show();


    }


    @Override
    public void onClick(View v) {

        Log.d("test", "aaaa");

        switch (v.getId()) {
            case R.id.max_people_1_btn:
                max_people = 1;

                max_people_1_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_2_btn.setImageResource(R.drawable.addroom_no_people);
                max_people_3_btn.setImageResource(R.drawable.addroom_no_people);
                max_people_4_btn.setImageResource(R.drawable.addroom_no_people);


                break;
            case R.id.max_people_2_btn:
                max_people = 2;

                max_people_1_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_2_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_3_btn.setImageResource(R.drawable.addroom_no_people);
                max_people_4_btn.setImageResource(R.drawable.addroom_no_people);

                break;
            case R.id.max_people_3_btn:
                max_people = 3;

                max_people_1_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_2_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_3_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_4_btn.setImageResource(R.drawable.addroom_no_people);

                break;
            case R.id.max_people_4_btn:
                max_people = 4;

                max_people_1_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_2_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_3_btn.setImageResource(R.drawable.addroom_yes_people);
                max_people_4_btn.setImageResource(R.drawable.addroom_yes_people);

                break;

            case R.id.add_room_time_btn:

                showTimePickerDialog(v);

                break;

            case R.id.add_room_day_btn:

                showDatePickerDialog(v);

                break;

            case R.id.add_room_ok_btr:

                Date today = new Date(System.currentTimeMillis());
                today.setTime(today.getTime() + (1000 * 60 * 30));

                if (date.getTime() > today.getTime()) {

                    carnum = add_room_carnum_met.getEditableText().toString();

                    HTTP_Handler http_handler = new HTTP_Handler(this, this);

                    Room_bean room_bean = new Room_bean();
                    room_bean.setOrder_id(Static_date.myProfile.getIdx());
                    room_bean.setTime(date);
                    room_bean.setStart_locale(start_location_id);
                    room_bean.setEnd_locale(end_location_id);
                    room_bean.setMax_personnerl(max_people);
                    room_bean.setCar_number(carnum);
                    room_bean.setOrder_name(Static_date.myProfile.getName());

                    ADD_ROOM_HTTP add_room_http = new ADD_ROOM_HTTP(http_handler, room_bean, HTTP_Handler.HTTP_RECEIVE_ADD_ROOM);
                    add_room_http.start();


                    break;

                } else {

                    final MaterialDialog mMaterialDialog1 = new MaterialDialog(this);
                    mMaterialDialog1.setCanceledOnTouchOutside(true);
                    mMaterialDialog1.setMessage("출발지 도착시간은 지금시간으로 30분 후부터 가능합니다.");
                    mMaterialDialog1.setPositiveButton("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mMaterialDialog1.dismiss();

                        }
                    });

                    mMaterialDialog1.show();

                }

                break;
            case R.id.start_location_btn:

                ArrayList<Location_bean> arrayList = new ArrayList<>();
                Location_bean location_bean = new Location_bean(1, "대연");
                Location_bean location_bean2 = new Location_bean(2, "경성");
                Location_bean location_bean3 = new Location_bean(3, "동명");
                arrayList.add(location_bean);
                arrayList.add(location_bean2);
                arrayList.add(location_bean3);

                showimgdialog(arrayList, true);

                break;

            case R.id.end_location_btn:

                if (start_location_id == 3) {

                    ArrayList<Location_bean> arrayList2 = new ArrayList<>();
                    Location_bean location_bean4 = new Location_bean(1, "대연");
                    Location_bean location_bean5 = new Location_bean(2, "경성");
                    arrayList2.add(location_bean4);
                    arrayList2.add(location_bean5);

                    showimgdialog(arrayList2, false);

                } else {

                }


                break;


        }


    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void Http_Receive(Message msg) {

        switch (msg.arg1) {

            case HTTP_Handler.HTTP_RECEIVE_ADD_ROOM:

                Toast.makeText(getApplicationContext(), "카풀 생성완료!", Toast.LENGTH_SHORT).show();

                finish();

                break;


            case HTTP_Handler.HTTP_RECEIVE_FALSE:

                switch (msg.arg2) {
                    case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20001:

                        Toast.makeText(getApplicationContext(), "참여한 카풀이 있거나, 만든카풀이 있습니다.", Toast.LENGTH_SHORT).show();

                        break;
                    case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20002:

                        Toast.makeText(getApplicationContext(), "알수없는 에러 다시시도해주세요", Toast.LENGTH_SHORT).show();

                        break;
                }
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = date.getHours();
            int minute = date.getMinutes();


            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            Log.d("test", "hourOfDay = " + hourOfDay);
            Log.d("test", "minute = " + minute);

            date.setHours(hourOfDay);
            date.setMinutes(minute);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a  hh : mm");

            add_room_time_btn.setText(simpleDateFormat.format(date));

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = date.getYear() + 1900;
            int month = date.getMonth();
            int day = date.getDate();

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            Log.d("test", "year = " + year);
            Log.d("test", "month = " + month);
            Log.d("test", "day = " + day);

            date.setYear((year - 1900));
            date.setMonth(month);
            date.setDate(day);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년  MM월  dd일  (E)");

            add_room_day_btn.setText(simpleDateFormat.format(date));

            Log.d("test", "time = " + simpleDateFormat.format(date));


        }
    }
}
