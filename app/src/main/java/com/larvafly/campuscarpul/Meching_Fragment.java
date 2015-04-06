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

import com.larvafly.adapter.Meching_Adapter;
import com.larvafly.bean.Meching_bean;
import com.larvafly.http.HTTP_Handler.*;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.lib.Android_Size;

@SuppressLint("ValidFragment")
public class Meching_Fragment extends Fragment implements OnHttpReceiveListener {

	private Activity activity;
    private ListView main_listview;
    private Meching_Adapter mAdapter;
    private HTTP_Handler http_Handler;
    private ArrayList<Meching_bean> arrItem;
    private LinearLayout linearLayout;

    public Meching_Fragment(Activity activity) {

        this.activity = activity;

        http_Handler = new HTTP_Handler(this, activity);

    }


    @Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_meching, null);


		main_listview = (ListView)view.findViewById(R.id.meching_lv);

		//		linearLayout = (LinearLayout)view.findViewById(R.id.Main_ScrollView_LinearLayout);

//		MAIN_GET_CARD_INFO_HTTP card_1 = new MAIN_GET_CARD_INFO_HTTP(http_Handler, Static_date.my_user_idx, HTTP_Handler.HTTP_RECEIVE_MAIN_GET_CARD);
//		card_1.start();

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


		Android_Size size = new Android_Size(activity);

		//		linearLayout.setLayoutParams(new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, size.gethight_1(105)));

		main_listview.setLayoutParams(new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, size.gethight_1(130))); 
		main_listview.setAdapter(mAdapter);
		main_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		main_listview.setDividerHeight(0);  	


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

			//			
		default:
			break;
		}

	}
	

}
