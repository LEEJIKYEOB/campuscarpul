package com.larvafly.campuscarpul;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.larvafly.bean.Gcm_bean;
import com.larvafly.bean.Location_bean;
import com.larvafly.bean.Room_bean;
import com.larvafly.http.ADD_ROOM_HTTP;
import com.larvafly.http.CANCEL_ROOM_HTTP;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.JOIN_ROOM_HTTP;
import com.larvafly.lib.Img_bitmap;
import com.larvafly.lib.Static_date;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.drakeet.materialdialog.MaterialDialog;

public class RoomInfoActivity extends ActionBarActivity implements View.OnClickListener, HTTP_Handler.OnHttpReceiveListener {

    private TextView room_info_date_tv;
    private TextView room_info_time_tv;
    private TextView room_info_startloc_tv;
    private TextView room_info_endloc_tv;
    private ImageView room_info_people_1_iv;
    private ImageView room_info_people_2_iv;
    private ImageView room_info_people_3_iv;
    private ImageView room_info_people_4_iv;
    private ImageView room_info_state_iv;
    //
    private TextView room_info_carnum_tv;
    private TextView room_info_price_tv;
    private ButtonRectangle room_info_btn;

    private Room_bean room_bean;

    private HTTP_Handler http_handler;

    /*
     0 = 기본
     1 = 내가만든방
     2 = 내가 참가한방
     3 = 종료된방

    */
    private int mystate = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("카풀정보");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);


        http_handler = new HTTP_Handler(this, this);

        Intent intent = getIntent();
        room_bean = (Room_bean) intent.getSerializableExtra("info");

        room_info_date_tv = (TextView) findViewById(R.id.room_info_date_tv);
        room_info_time_tv = (TextView) findViewById(R.id.room_info_time_tv);
        room_info_startloc_tv = (TextView) findViewById(R.id.room_info_startloc_tv);
        room_info_endloc_tv = (TextView) findViewById(R.id.room_info_endloc_tv);
        room_info_people_1_iv = (ImageView) findViewById(R.id.room_info_people_1_iv);
        room_info_people_2_iv = (ImageView) findViewById(R.id.room_info_people_2_iv);
        room_info_people_3_iv = (ImageView) findViewById(R.id.room_info_people_3_iv);
        room_info_people_4_iv = (ImageView) findViewById(R.id.room_info_people_4_iv);
        room_info_carnum_tv = (TextView) findViewById(R.id.room_info_carnum_tv);
        room_info_price_tv = (TextView) findViewById(R.id.room_info_price_tv);
        room_info_btn = (ButtonRectangle) findViewById(R.id.room_info_btn);
        room_info_state_iv = (ImageView) findViewById(R.id.room_info_state_iv);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        TextView room_info_order_name_tv = (TextView) findViewById(R.id.room_info_order_name_tv);
        room_info_order_name_tv.setText("  운전자 " + room_bean.getOrder_name());

        AQuery aQuery = new AQuery(this);

        if (!room_bean.getOrder_img_uri().equals("-1")) {

            aQuery.id(imageView2).image(room_bean.getOrder_img_uri(), true, true, Img_bitmap.dptopx(this, 92), 0, new BitmapAjaxCallback() {

                @Override
                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                    iv.setImageBitmap(bm);
//
                }

            });
        }

//        imageView2

        if (room_bean.ismyorder()) {
            room_info_state_iv.setImageResource(R.drawable.my_seal);
            room_info_state_iv.setVisibility(View.VISIBLE);
            mystate = 1;
            room_info_btn.setText("카풀취소");
        }
        if (room_bean.ismyjoin()) {
            room_info_state_iv.setImageResource(R.drawable.join_seal);
            room_info_state_iv.setVisibility(View.VISIBLE);
            mystate = 2;
            room_info_btn.setText("카풀나가기");
        }
        if (room_bean.isendroom()) {
            room_info_state_iv.setImageResource(R.drawable.end_seal);
            room_info_state_iv.setVisibility(View.VISIBLE);
            mystate = 3;
            room_info_btn.setText("카풀취소");
        }

        if (room_bean.getState() == Room_bean.STATE_END || room_bean.getState() == Room_bean.STATE_RED) {

            mystate = 3;

        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일 (E)");
        SimpleDateFormat timeFormat = new SimpleDateFormat("a  hh : mm");

        room_info_date_tv.setText(dateFormat.format(room_bean.getTime()));
        room_info_time_tv.setText(timeFormat.format(room_bean.getTime()));

        switch (room_bean.getStart_locale()) {
            case 1:

                room_info_startloc_tv.setText("대연동");

                break;
            case 2:

                room_info_startloc_tv.setText("경성대");

                break;
            case 3:

                room_info_startloc_tv.setText("동명대");

                break;

        }
        switch (room_bean.getEnd_locale()) {
            case 1:

                room_info_endloc_tv.setText("대연동");

                break;
            case 2:

                room_info_endloc_tv.setText("경성대");

                break;
            case 3:

                room_info_endloc_tv.setText("동명대");

                break;

        }

//        android:text="2015년  04월  09(목)"
//        android:text="오전  8 : 45"

        int max_personnerl = room_bean.getMax_personnerl();
        switch (max_personnerl) {

            case 1:
                room_info_people_1_iv.setVisibility(View.VISIBLE);
                room_info_people_2_iv.setVisibility(View.GONE);
                room_info_people_3_iv.setVisibility(View.GONE);
                room_info_people_4_iv.setVisibility(View.GONE);

                break;
            case 2:
                room_info_people_1_iv.setVisibility(View.VISIBLE);
                room_info_people_2_iv.setVisibility(View.VISIBLE);
                room_info_people_3_iv.setVisibility(View.GONE);
                room_info_people_4_iv.setVisibility(View.GONE);

                break;
            case 3:
                room_info_people_1_iv.setVisibility(View.VISIBLE);
                room_info_people_2_iv.setVisibility(View.VISIBLE);
                room_info_people_3_iv.setVisibility(View.VISIBLE);
                room_info_people_4_iv.setVisibility(View.GONE);

                break;
            case 4:
                room_info_people_1_iv.setVisibility(View.VISIBLE);
                room_info_people_2_iv.setVisibility(View.VISIBLE);
                room_info_people_3_iv.setVisibility(View.VISIBLE);
                room_info_people_4_iv.setVisibility(View.VISIBLE);
                break;


        }

        nowre();

        room_info_carnum_tv.setText(room_bean.getCar_number());

        room_info_btn.setOnClickListener(this);

    }

    void nowre() {

        int now_personnerl = room_bean.getNow_personnerl();
        switch (now_personnerl) {
            case 0:
                room_info_people_1_iv.setImageResource(R.drawable.main_no_people);
                room_info_people_2_iv.setImageResource(R.drawable.main_no_people);
                room_info_people_3_iv.setImageResource(R.drawable.main_no_people);
                room_info_people_4_iv.setImageResource(R.drawable.main_no_people);

                break;

            case 1:
                room_info_people_1_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_2_iv.setImageResource(R.drawable.main_no_people);
                room_info_people_3_iv.setImageResource(R.drawable.main_no_people);
                room_info_people_4_iv.setImageResource(R.drawable.main_no_people);

                break;
            case 2:
                room_info_people_1_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_2_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_3_iv.setImageResource(R.drawable.main_no_people);
                room_info_people_4_iv.setImageResource(R.drawable.main_no_people);

                break;
            case 3:
                room_info_people_1_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_2_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_3_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_4_iv.setImageResource(R.drawable.main_no_people);

                break;
            case 4:
                room_info_people_1_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_2_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_3_iv.setImageResource(R.drawable.main_yes_people);
                room_info_people_4_iv.setImageResource(R.drawable.main_yes_people);

                break;

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.room_info_btn:

                switch (mystate) {
                    case 0:

                        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
                        mMaterialDialog.setTitle("카풀하기");
                        mMaterialDialog.setCanceledOnTouchOutside(true);
                        mMaterialDialog.setMessage("카풀을 참가하시겠습니까?\n도착 10분전에는 카풀을 취소 못합니다.");
                        mMaterialDialog.setPositiveButton("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                JOIN_ROOM_HTTP join_room_http = new JOIN_ROOM_HTTP(http_handler, Static_date.myProfile.getIdx(), room_bean.getIdx(), HTTP_Handler.HTTP_RECEIVE_JOIN_ROOM);
                                join_room_http.start();

                                mMaterialDialog.dismiss();

                            }
                        });
                        mMaterialDialog.setNegativeButton("취소", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });

                        mMaterialDialog.show();

                        break;

                    case 1:


                        final MaterialDialog mMaterialDialog1 = new MaterialDialog(this);
                        mMaterialDialog1.setTitle("카풀취소");
                        mMaterialDialog1.setCanceledOnTouchOutside(true);
                        mMaterialDialog1.setMessage("정말 카풀방을 취소합니까?");
                        mMaterialDialog1.setPositiveButton("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                CANCEL_ROOM_HTTP cancel_room_http = new CANCEL_ROOM_HTTP(http_handler, room_bean, HTTP_Handler.HTTP_RECEIVE_CANCEL_ROOM);
                                cancel_room_http.start();

                                mMaterialDialog1.dismiss();

                            }
                        });
                        mMaterialDialog1.setNegativeButton("취소", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog1.dismiss();
                            }
                        });

                        mMaterialDialog1.show();

                        break;

                    case 2:


                        final MaterialDialog mMaterialDialog2 = new MaterialDialog(this);
                        mMaterialDialog2.setTitle("카풀나가기");
                        mMaterialDialog2.setCanceledOnTouchOutside(true);
                        mMaterialDialog2.setMessage("정말 카풀방을 나가시겠습니까?");
                        mMaterialDialog2.setPositiveButton("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CANCEL_ROOM_HTTP cancel_room_http = new CANCEL_ROOM_HTTP(http_handler, room_bean, HTTP_Handler.HTTP_RECEIVE_CANCEL_ROOM);
                                cancel_room_http.start();

                                mMaterialDialog2.dismiss();

                            }
                        });
                        mMaterialDialog2.setNegativeButton("취소", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog2.dismiss();
                            }
                        });
                        mMaterialDialog2.show();

                        break;
                    case 3:


                        final MaterialDialog mMaterialDialog3 = new MaterialDialog(this);
                        mMaterialDialog3.setTitle("카풀취소");
                        mMaterialDialog3.setCanceledOnTouchOutside(true);
                        mMaterialDialog3.setMessage("이미 운전자가 도착예정이라 방을 나가지 못합니다.");
                        mMaterialDialog3.setPositiveButton("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CANCEL_ROOM_HTTP cancel_room_http = new CANCEL_ROOM_HTTP(http_handler, room_bean, HTTP_Handler.HTTP_RECEIVE_CANCEL_ROOM);
                                cancel_room_http.start();

                                mMaterialDialog3.dismiss();

                            }
                        });

                        mMaterialDialog3.show();

                        break;
                }

                break;

        }

    }

    @Override
    public void Http_Receive(Message msg) {

        switch (msg.arg1) {

            case HTTP_Handler.HTTP_RECEIVE_CANCEL_ROOM:


                switch (mystate) {
                    case 1:

                        Toast.makeText(getApplicationContext(), "카풀 취소 완료!", Toast.LENGTH_SHORT).show();

                        Gcm_bean gcm_bean2 = new Gcm_bean();
                        gcm_bean2.setTitle("카풀 취소");
                        gcm_bean2.setState(4);
                        gcm_bean2.setRoomidx(-1);
                        gcm_bean2.setMsg(Static_date.myProfile.getName() + "님의 방이 없어졌어요.");
                        GCMServerSide gcmServerSide2 = new GCMServerSide(this, room_bean.getPeople_devicekey(), gcm_bean2);
                        gcmServerSide2.start();

                        finish();

                        break;
                    case 2:

                        Toast.makeText(getApplicationContext(), "카풀 나가기 완료!", Toast.LENGTH_SHORT).show();

                        Gcm_bean gcm_bean = new Gcm_bean();
                        gcm_bean.setTitle("카풀 탈퇴");
                        gcm_bean.setState(2);
                        gcm_bean.setRoomidx(room_bean.getIdx());
                        gcm_bean.setMsg(Static_date.myProfile.getName() + "님이 카풀을 탈퇴하였어요.");
                        GCMServerSide gcmServerSide = new GCMServerSide(this, room_bean.getOrder_devicekey(), gcm_bean);
                        gcmServerSide.start();

                        room_bean.setNow_personnerl(room_bean.getNow_personnerl() - 1);

                        nowre();

                        room_info_state_iv.setVisibility(View.GONE);
                        mystate = 0;
                        room_info_btn.setText("카풀하기");

                        break;
                }

                break;

            case HTTP_Handler.HTTP_RECEIVE_JOIN_ROOM:

                Toast.makeText(getApplicationContext(), "카풀 참가완료!", Toast.LENGTH_SHORT).show();

                Gcm_bean gcm_bean = new Gcm_bean();

                gcm_bean.setTitle("카풀 참가");
                gcm_bean.setState(1);
                gcm_bean.setRoomidx(room_bean.getIdx());
                gcm_bean.setMsg(Static_date.myProfile.getName() + "님이 카풀에 참가했어요,");

                GCMServerSide gcmServerSide = new GCMServerSide(this, room_bean.getOrder_devicekey(), gcm_bean);
                gcmServerSide.start();

                room_bean.setNow_personnerl(room_bean.getNow_personnerl() + 1);

                nowre();

                room_info_state_iv.setImageResource(R.drawable.join_seal);
                room_info_state_iv.setVisibility(View.VISIBLE);
                mystate = 2;
                room_info_btn.setText("카풀나가기");

                break;


            case HTTP_Handler.HTTP_RECEIVE_FALSE:

                switch (msg.arg2) {
                    case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20000:

                        Toast.makeText(getApplicationContext(), "차가 만석입니다. 다른방을 구해주세요.", Toast.LENGTH_SHORT).show();

                        room_bean.setNow_personnerl(room_bean.getMax_personnerl());
                        nowre();

                        break;
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
