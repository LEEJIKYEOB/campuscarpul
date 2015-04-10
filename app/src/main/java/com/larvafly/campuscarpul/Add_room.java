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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.larvafly.adapter.Location_Adapter;
import com.larvafly.bean.Location_bean;
import com.larvafly.bean.Room_bean;
import com.larvafly.campuscarpul.R;
import com.larvafly.http.ADD_ROOM_HTTP;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.lib.Static_date;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Add_room extends ActionBarActivity implements View.OnClickListener,HTTP_Handler.OnHttpReceiveListener{

    private Spinner start_location_spinner;
    private Spinner end_location_spinner;
    private Button max_people_1_btn;
    private Button max_people_2_btn;
    private Button max_people_3_btn;
    private Button max_people_4_btn;
    private ButtonRectangle add_room_cancel_btr;
    private ButtonRectangle add_room_ok_btr;
    private static ButtonFlat add_room_time_btn;
    private static ButtonFlat add_room_day_btn;
    private MaterialEditText add_room_carnum_met;
//    add_room_time_btn

    private int start_location_id = 0;
    private int end_location_id = 0;
    private int max_people = 4;
    private String carnum = "";
    private static Date date;


    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_car);
        toolbar.setTitle("카풀 만들기");
//        setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon();
        this.setSupportActionBar(toolbar);

        Resources r = getResources();

        getSupportFragmentManager();

        start_location_spinner_init();

        max_people_1_btn = (Button)findViewById(R.id.max_people_1_btn);
        max_people_2_btn = (Button)findViewById(R.id.max_people_2_btn);
        max_people_3_btn = (Button)findViewById(R.id.max_people_3_btn);
        max_people_4_btn = (Button)findViewById(R.id.max_people_4_btn);

        max_people_1_btn.setOnClickListener(this);
        max_people_2_btn.setOnClickListener(this);
        max_people_3_btn.setOnClickListener(this);
        max_people_4_btn.setOnClickListener(this);

        date = new Date(System.currentTimeMillis());


        add_room_time_btn = (ButtonFlat)findViewById(R.id.add_room_time_btn);
        add_room_day_btn = (ButtonFlat)findViewById(R.id.add_room_day_btn);
        add_room_time_btn.setOnClickListener(this);
        add_room_day_btn.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년  MM월  dd일  (E)");
        add_room_day_btn.setText(simpleDateFormat.format(date));

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("a  hh : mm");
        add_room_time_btn.setText(simpleDateFormat2.format(date));


        add_room_carnum_met = (MaterialEditText)findViewById(R.id.add_room_carnum_met);
        add_room_cancel_btr = (ButtonRectangle)findViewById(R.id.add_room_cancel_btr);
        add_room_cancel_btr.setOnClickListener(this);
        add_room_ok_btr = (ButtonRectangle)findViewById(R.id.add_room_ok_btr);
        add_room_ok_btr.setOnClickListener(this);

    }


    void start_location_spinner_init(){

        start_location_spinner = (Spinner)findViewById(R.id.start_location_spinner);


        ArrayList<Location_bean> temp = new ArrayList<>();


        Location_bean item1 = new Location_bean(1,"대연동");
        Location_bean item2 = new Location_bean(2,"경성대");
        Location_bean item3 = new Location_bean(3,"동명대");

        temp.add(item1);
        temp.add(item2);
        temp.add(item3);

        Location_Adapter list_year;

        list_year = new Location_Adapter(this, android.R.layout.simple_spinner_item, temp);
        list_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        start_location_spinner.setAdapter(list_year);
        start_location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View v,int position, long id) {

                Location_bean a = (Location_bean) arg0.getItemAtPosition(position);
                Log.d("test", " a = " + a.getId());
                Log.d("test", " l = " + a.getLocation());

                if (a.getId() == 3) end_location_spinner_init(false);
                else end_location_spinner_init(true);

                start_location_id = a.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

    void end_location_spinner_init(boolean b){

        end_location_spinner = (Spinner)findViewById(R.id.end_location_spinner);

        ArrayList<Location_bean> temp = new ArrayList<>();

        if (b){
            Location_bean item3 = new Location_bean(3,"동명대");
            temp.add(item3);
            end_location_id = 3;

        }else{
            Location_bean item1 = new Location_bean(1,"대연동");
            Location_bean item2 = new Location_bean(2,"경성대");
            temp.add(item1);
            temp.add(item2);
            end_location_id = 1;
        }



        Location_Adapter list_year;

        list_year = new Location_Adapter(this, android.R.layout.simple_spinner_item, temp);
        list_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        end_location_spinner.setAdapter(list_year);
        end_location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View v,int position, long id) {

                Location_bean a = (Location_bean) arg0.getItemAtPosition(position);
                Log.d("test", " a = " + a.getId());
                Log.d("test", " l = " + a.getLocation());

                end_location_id = a.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }


    @Override
    public void onClick(View v) {

        Log.d("test","aaaa");

        switch (v.getId()){
            case R.id.max_people_1_btn:
                max_people = 1;

                max_people_1_btn.setBackgroundColor(getResources().getColor(R.color.point));
                max_people_2_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_3_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_4_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));

                break;
            case R.id.max_people_2_btn:
                max_people = 2;

                max_people_1_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_2_btn.setBackgroundColor(getResources().getColor(R.color.point));
                max_people_3_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_4_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));

                break;
            case R.id.max_people_3_btn:
                max_people = 3;

                max_people_1_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_2_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_3_btn.setBackgroundColor(getResources().getColor(R.color.point));
                max_people_4_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));

                break;
            case R.id.max_people_4_btn:
                max_people = 4;

                max_people_1_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_2_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_3_btn.setBackgroundColor(getResources().getColor(R.color.hintcolor));
                max_people_4_btn.setBackgroundColor(getResources().getColor(R.color.point));

                break;

            case R.id.add_room_time_btn:

                showTimePickerDialog(v);

                break;

            case R.id.add_room_day_btn:

                showDatePickerDialog(v);

                break;
            case R.id.add_room_cancel_btr:
                finish();
                break;

            case R.id.add_room_ok_btr:

                carnum =  add_room_carnum_met.getEditableText().toString();

                HTTP_Handler http_handler = new HTTP_Handler(this,this);

                Room_bean room_bean = new Room_bean();
                room_bean.setOrder_id(Static_date.my_user_idx);
                room_bean.setTime(date);
                room_bean.setStart_locale(start_location_id);
                room_bean.setEnd_locale(end_location_id);
                room_bean.setMax_personnerl(max_people);
                room_bean.setCar_number(carnum);

                ADD_ROOM_HTTP add_room_http = new ADD_ROOM_HTTP(http_handler,room_bean,HTTP_Handler.HTTP_RECEIVE_ADD_ROOM);
                add_room_http.start();

//            $order_id = $_POST [order_id];
//            $time = $_POST [time];
//            $start_locale = $_POST [start_locale];
//            $end_locale = $_POST [end_locale];
//            $max_personnel = $_POST [max_personnel];
//            $car_number = $_POST [car_number];

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



    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            Log.d("test","hourOfDay = " + hourOfDay);
            Log.d("test","minute = " + minute);

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
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            Log.d("test","year = " + year);
            Log.d("test","month = " + month);
            Log.d("test","day = " + day);

            date.setYear((year-1900));
            date.setMonth(month);
            date.setDate(day);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년  MM월  dd일  (E)");

            add_room_day_btn.setText(simpleDateFormat.format(date));

            Log.d("test","time = "+simpleDateFormat.format(date));


        }
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
}
