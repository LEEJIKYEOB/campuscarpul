package com.larvafly.campuscarpul;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.games.multiplayer.realtime.Room;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.larvafly.adapter.Meching_Adapter;
import com.larvafly.bean.Room_bean;
import com.larvafly.http.GET_ROOM_HTTP;
import com.larvafly.http.HTTP_Handler.*;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.lib.Android_Size;

@SuppressLint("ValidFragment")
public class Meching_Fragment extends Fragment implements OnHttpReceiveListener,PullToRefreshBase.OnRefreshListener {

    private Activity activity;
    private PullToRefreshListView main_listview;
    private Meching_Adapter mAdapter;
    private HTTP_Handler http_Handler;
    private ArrayList<Room_bean> arrItem;
    private LinearLayout linearLayout;
    private int start_loc;

    public Meching_Fragment(Activity activity,int start_loc) {

        this.activity = activity;
        this.start_loc =start_loc;

        http_Handler = new HTTP_Handler(this, activity);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meching, null);


        main_listview = (PullToRefreshListView)view.findViewById(R.id.meching_lv);

        //		linearLayout = (LinearLayout)view.findViewById(R.id.Main_ScrollView_LinearLayout);

        arrItem = new ArrayList<>();


//        GET_ROOM_HTTP get_room_http = new GET_ROOM_HTTP(http_Handler,start_loc,HTTP_Handler.HTTP_RECEIVE_GET_ROOM);
//        get_room_http.setShowdialog(false);
//        get_room_http.start();

//		MAIN_GET_CARD_INFO_HTTP card_1 = new MAIN_GET_CARD_INFO_HTTP(http_Handler, Static_date.my_user_idx, HTTP_Handler.HTTP_RECEIVE_MAIN_GET_CARD);
//		card_1.start();

        init_cardlist();

        return view;
    }

    public void main_fragment_refresh(){

//		MAIN_GET_CARD_INFO_HTTP card_1 = new MAIN_GET_CARD_INFO_HTTP(http_Handler, Static_date.my_user_idx, HTTP_Handler.HTTP_RECEIVE_MAIN_GET_CARD);
//		card_1.setShowdialog(false);
//		card_1.start();

    }


    void init_cardlist()
    {


        mAdapter = new Meching_Adapter(activity, arrItem,http_Handler);
        main_listview.setAdapter(mAdapter);
        main_listview.setOnRefreshListener(this);


        AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        };

        main_listview.setOnItemClickListener(mItemClickListener);

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void Http_Receive(Message msg) {

        switch (msg.arg1) {

            case HTTP_Handler.HTTP_RECEIVE_FALSE:

//			Log.d("test", "ssss");

                switch (msg.arg2) {

                    default:
                        break;
                }

                break;

            case HTTP_Handler.HTTP_RECEIVE_GET_ROOM:

                int size =  arrItem.size();

                for (int i = 0; i < size; i++) {
                    arrItem.remove(0);
                }

                ArrayList<Room_bean> a = (ArrayList<Room_bean>) msg.obj;

                for (int i=0; i<a.size(); i++){
                    arrItem.add(a.get(i));
                }

                mAdapter.notifyDataSetChanged();

                main_listview.onRefreshComplete();

                break;

            //
            default:
                break;
        }

    }

    public void refresh(){

        GET_ROOM_HTTP get_room_http = new GET_ROOM_HTTP(http_Handler,start_loc,HTTP_Handler.HTTP_RECEIVE_GET_ROOM);
        get_room_http.setShowdialog(false);
        get_room_http.start();

    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        refresh();
    }
}
