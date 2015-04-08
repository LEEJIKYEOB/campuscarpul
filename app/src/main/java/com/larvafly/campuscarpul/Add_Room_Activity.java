package com.larvafly.campuscarpul;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.larvafly.adapter.Location_Adapter;
import com.larvafly.bean.Location_bean;
import com.larvafly.lib.Android_Size;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressLint("DefaultLocale")
public class Add_Room_Activity extends Activity {

    private TimePicker timePicker;
    private DatePicker datePicker;
    private Spinner start_location_spinner;
    private Spinner end_location_spinner;
    private int start_location_id = 0;
    private int end_location_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker)findViewById(R.id.datePicker);

        Resources r = getResources();

        textColor(timePicker,r.getColor(R.color.material_blue_grey_950));
        textColor(datePicker,r.getColor(R.color.material_blue_grey_950));

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

    void numberPickerTextColor( NumberPicker $v, int $c ){
        setDividerColor($v);
        for(int i = 0, j = $v.getChildCount() ; i < j; i++){
            View t0 = $v.getChildAt(i);
            if( t0 instanceof EditText){
                try{
                    Field t1 = $v.getClass() .getDeclaredField( "mSelectorWheelPaint" );
                    t1.setAccessible(true);
                    ((Paint)t1.get($v)) .setColor($c);
                    ((EditText)t0) .setTextColor($c);
                    $v.invalidate();
                }catch(Exception e){}
            }
        }
    }
    private void setDividerColor(NumberPicker picker) {
        Field[] numberPickerFields = NumberPicker.class.getDeclaredFields();
        for (Field field : numberPickerFields) {
            if (field.getName().equals("mSelectionDivider")) {
                field.setAccessible(true);
                try {
                    field.set(picker, getResources().getDrawable(R.drawable.divider));
                } catch (IllegalArgumentException e) {
                    Log.v("test", "Illegal Argument Exception");
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    Log.v("test", "Resources NotFound");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    Log.v("test", "Illegal Access Exception");
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    void dateTimePickerTextColour( ViewGroup $picker, int $c ){

        for( int i = 0, j = $picker.getChildCount() ; i < j ; i++ ){
            View t0 = (View)$picker.getChildAt(i);

            //NumberPicker는 아까만든 함수로 발라내고
            if(t0 instanceof NumberPicker) numberPickerTextColor( (NumberPicker)t0, $c );

                //아니면 계속 돌아봐
            else if(t0 instanceof ViewGroup) dateTimePickerTextColour( (ViewGroup)t0, $c );
        }
    }
    void textColor( View $v, int $c ){

        if($v instanceof NumberPicker) numberPickerTextColor( (NumberPicker)$v, $c );

        else if($v instanceof TextView) ((TextView)$v).setTextColor($c);

        else if($v instanceof ViewGroup){

            ViewGroup t0 = (ViewGroup) $v;
            for( int i = 0, j = t0.getChildCount() ; i < j ; i++ )
                textColor( t0.getChildAt(i), $c );

        }
    }

}
