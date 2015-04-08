package com.larvafly.campuscarpul;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.larvafly.adapter.Location_Adapter;
import com.larvafly.bean.Location_bean;
import com.larvafly.campuscarpul.R;

public class Add_room extends ActionBarActivity implements View.OnClickListener{

    private Spinner start_location_spinner;
    private Spinner end_location_spinner;
    private int start_location_id = 0;
    private int end_location_id = 0;

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
    }


    void start_location_spinner_init(){

        start_location_spinner = (Spinner)findViewById(R.id.start_location_spinner);


        ArrayList<Location_bean> temp = new ArrayList<>();


        Location_bean item0 = new Location_bean(0,"출발지");
        Location_bean item1 = new Location_bean(1,"대연동");
        Location_bean item2 = new Location_bean(2,"경성대");
        Location_bean item3 = new Location_bean(3,"동명대");

        temp.add(item0);
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

                if (a.getId() != -1)	{
                    if (a.getId() == 3) end_location_spinner_init(false);
                    else end_location_spinner_init(true);

                    start_location_id = a.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

    void end_location_spinner_init(boolean b){

        end_location_spinner = (Spinner)findViewById(R.id.end_location_spinner);
        end_location_spinner.setVisibility(View.VISIBLE);

        ArrayList<Location_bean> temp = new ArrayList<>();

        if (b){
            Location_bean item3 = new Location_bean(3,"동명대");
            temp.add(item3);
        }else{
            Location_bean item1 = new Location_bean(1,"대연동");
            Location_bean item2 = new Location_bean(2,"경성대");
            temp.add(item1);
            temp.add(item2);
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

                if (a.getId() != -1)	{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }


    @Override
    public void onClick(View v) {

        Log.d("test","aaaa");

    }
}
