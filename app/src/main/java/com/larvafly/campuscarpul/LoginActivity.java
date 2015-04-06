package com.larvafly.campuscarpul;

import com.gc.materialdesign.views.ButtonFlat;
import com.larvafly.bean.UserProfile_bean;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.HTTP_Handler.OnHttpReceiveListener;
import com.larvafly.http.LOGIN_HTTP;
import com.larvafly.lib.Static_date;
import com.gc.materialdesign.views.ButtonRectangle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ResourceBundle;


public class LoginActivity extends Activity implements OnClickListener, OnHttpReceiveListener {

	private ButtonRectangle login_bt;
	private ButtonRectangle join_bt;
    private ButtonFlat s_bt;

	private EditText uesr_id;
	private EditText uesr_pw;

    ButtonFlat pass_search_bt;

	public static Activity Act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		this.Act = this;

        Resources r = getResources();

		pass_search_bt = (ButtonFlat)findViewById(R.id.login_src_tv);
		pass_search_bt.getTextView().setTypeface(Static_date.myfont);
        pass_search_bt.getTextView().setTextSize(14);
		login_bt = (ButtonRectangle) findViewById(R.id.login_login_btn);
		login_bt.getTextView().setTypeface(Static_date.myfont);
		login_bt.setOnClickListener(this);
        login_bt.getTextView().setTextColor(r.getColor(R.color.white));
		join_bt = (ButtonRectangle)findViewById(R.id.login_join_btn);
		join_bt.getTextView().setTypeface(Static_date.myfont);
		join_bt.setOnClickListener(this);
        join_bt.getTextView().setTextColor(r.getColor(R.color.lightblue_500));

		uesr_id = (EditText) findViewById(R.id.login_id_et);
		uesr_id.setTypeface(Static_date.myfont);
		uesr_pw = (EditText) findViewById(R.id.login_pw_et);
		uesr_pw.setTypeface(Static_date.myfont);

		
		ImageView login_Logo = (ImageView)findViewById(R.id.login_logo_iv);
		ImageView login_Logotext = (ImageView)findViewById(R.id.login_logotext_iv);
		
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.login_login_btn:

			
			

//			HTTP_Handler handler = new HTTP_Handler(this,this);
//			LOGIN_HTTP login_HTTP = new LOGIN_HTTP(handler,uesr_id.getEditableText().toString(),uesr_pw.getEditableText().toString(),HTTP_Handler.HTTP_RECEIVE_LOGIN);
//			login_HTTP.start();
			
//			Intent intent333 = new Intent(Login_Activity.this, Chat_Activity.class);
//			startActivity(intent333);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
//			Dialog_rder id = new Dialog_rder(this);
//			id.show();

			break;

		case R.id.login_join_btn:

			Intent intent2 = new Intent(LoginActivity.this, Join01Activity.class);
			startActivity(intent2);
			//			finish();

			break;

		default:
			break;
		}

	}
	

	@Override
	public void Http_Receive(Message msg) {
		// TODO Auto-generated method stub

		//		Log.d("test", (String) msg.obj);

		switch (msg.arg1) {
		case HTTP_Handler.HTTP_RECEIVE_LOGIN:

			if (msg.arg2 > 0) {
				
				UserProfile_bean bean = (UserProfile_bean)msg.obj;

				Static_date.myimg = bean.getProfileimage();
				Static_date.my_user_idx = msg.arg2;
				Static_date.my_state = bean.getState();

				Toast.makeText(getApplicationContext(), "환영합니다. "+Static_date.mynickname + "님", Toast.LENGTH_SHORT).show();
				
				Static_date.saveLoginSharedPreferences(this,uesr_id.getEditableText().toString(), uesr_pw.getEditableText().toString());
				

				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				intent.putExtra("state", bean.getState()); //join
				startActivity(intent);
				
				finish();


			}else if (msg.arg2 == -1) {

				String temp = (String)msg.obj;

				Toast.makeText(getApplicationContext(),temp, Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}




	}

}
