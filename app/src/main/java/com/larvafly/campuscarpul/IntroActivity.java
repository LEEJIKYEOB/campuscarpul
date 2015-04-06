package com.larvafly.campuscarpul;

import android.app.Activity;
import android.os.Bundle;



import com.larvafly.bean.UserProfile_bean;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.HTTP_Handler.OnHttpReceiveListener;
import com.larvafly.http.LOGIN_HTTP;
import com.larvafly.lib.Static_date;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class IntroActivity extends Activity implements OnHttpReceiveListener{

	private int gcmstate;
	private int youidx;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);


			SharedPreferences prefs_login = getSharedPreferences("HG_LOGIN", MODE_PRIVATE);
			String id = prefs_login.getString("ID", "-");
			String pw = prefs_login.getString("PW", "-");

			if(!id.equals("-") && !pw.equals("-")){
				autoLogin(id, pw);
			}else{        
				Intent intent = new Intent(IntroActivity.this, LoginActivity.class);								
				startActivity(intent);
				finish();
			}

			//			Toast.makeText(getApplicationContext(), "��ŸƮ", Toast.LENGTH_SHORT).show();



		}

	};

	private void autoLogin(String id, String pw) {

		HTTP_Handler handler = new HTTP_Handler(this,this);
		LOGIN_HTTP login_HTTP = new LOGIN_HTTP(handler,id,pw,HTTP_Handler.HTTP_RECEIVE_LOGIN);
		login_HTTP.setShowdialog(false);
		login_HTTP.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_activity);

		Intent intent = getIntent();
		gcmstate = intent.getIntExtra("gcmstate", -1);
		youidx = intent.getIntExtra("youidx", -1);

		Static_date.myfont = Static_date.ApplyFonts(this);
		Static_date.myfont_bold = Static_date.ApplyFonts_bold(this);
		Static_date.registerGcm(getApplicationContext());

		handler.sendEmptyMessageDelayed(0, 2000);


	}

	@Override
	public void Http_Receive(Message msg) {
		// TODO Auto-generated method stub

		switch (msg.arg1) {
		case HTTP_Handler.HTTP_RECEIVE_LOGIN:

			if (msg.arg2 > 0) {

				UserProfile_bean bean = (UserProfile_bean)msg.obj;

				Static_date.myimg = bean.getProfileimage();
				Static_date.my_user_idx = msg.arg2;
				Static_date.my_state = bean.getState();

				Toast.makeText(getApplicationContext(), "환영합니다. "+Static_date.mynickname + "님", Toast.LENGTH_SHORT).show();

				Intent intent;

//				switch (gcmstate) {
//				case -1:
//
//					intent = new Intent(IntroActivity.this, Main_Activity.class);
//					intent.putExtra("state", bean.getState()); //join
//					startActivity(intent);
//
//					break;
//
//				case ChatText_bean.GCM_STATE_USERINFO_NO_RECEIVE:
//
//					intent = new Intent(IntroActivity.this, User_Profile_Activity.class);
//					System.out.println("youidx = "+youidx);
//					intent.putExtra("idx", youidx);
//					startActivity(intent);
//
//					break;
//
//				case ChatText_bean.GCM_STATE_USERINFO_OK_RECEIVE:
//
//					intent = new Intent(IntroActivity.this, Chat_Activity.class);
//					intent.putExtra("idx", youidx);
//					startActivity(intent);
//
//					break;
//
//				case ChatText_bean.GCM_STATE_USERINFO_OKSEND_RECEIVE:
//
//					intent = new Intent(IntroActivity.this, User_Profile_Activity.class);
//					System.out.println("youidx = "+youidx);
//					intent.putExtra("idx", youidx);
//					startActivity(intent);
//
//					break;
//
//				case ChatText_bean.GCM_STATE_CHATINGROOM:
//
//					intent = new Intent(IntroActivity.this, Chat_Activity.class);
//					intent.putExtra("idx", youidx);
//					startActivity(intent);
//
//					break;
//
//				default:
//					break;
//				}



				finish();


			}else if (msg.arg2 == -1) {

				Intent intent = new Intent(IntroActivity.this, LoginActivity.class);								
				startActivity(intent);
				finish();
			}

			break;

		default:
			break;
		}

	}




}
