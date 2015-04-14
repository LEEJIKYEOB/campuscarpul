package com.larvafly.campuscarpul;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.larvafly.bean.Location_bean;
import com.larvafly.bean.Room_bean;
import com.larvafly.http.ADD_ROOM_HTTP;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.lib.Img_bitmap;
import com.larvafly.lib.Static_date;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.drakeet.materialdialog.MaterialDialog;

public class RemoteActivity extends ActionBarActivity implements View.OnClickListener, HTTP_Handler.OnHttpReceiveListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("카풀생성");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);


    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {


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
