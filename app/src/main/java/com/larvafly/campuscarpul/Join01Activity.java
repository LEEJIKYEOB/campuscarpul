package com.larvafly.campuscarpul;

import java.io.FileNotFoundException;

import com.larvafly.bean.UserProfile_bean;
import com.larvafly.http.FILE_UPLOAD_HTTP;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.HTTP_Handler.OnHttpReceiveListener;
import com.larvafly.http.JOIN_HTTP;
import com.larvafly.http.JOIN_IDCHECK_HTTP;
import com.larvafly.lib.Img_bitmap;
import com.larvafly.lib.Regex;
import com.larvafly.lib.Static_date;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import android.R.bool;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class Join01Activity extends ActionBarActivity implements OnClickListener, OnHttpReceiveListener{

	public static Activity Act;

	protected static final ColorStateList RED = null;


	private UserProfile_bean userProfile_bean;

//    com.rengwuxian.materialedittext.MaterialEditText
//	int idx;
//	String user_id;
//	String user_pw;
//	String name;
//	int sex;
//	String phone_number;
//	String profileimage;
//	String carimage;
//	int state;
//	String devicekey;
	
	private String id;
	private String pw;
	private String name;
	private int sex;
	private String phone;

	private EditText id_et;
	private ButtonFlat idck_bt;
	private MaterialEditText pw_et;
	private MaterialEditText pwck_et;
	private MaterialEditText name_et;
	private EditText phone_et;
	private EditText phoneck_et;
	private ButtonFlat phone_send_bt;
	private ButtonFlat phone_check_bt;


	private RelativeLayout sex_man;
	private RelativeLayout sex_woman;
	private Button sex_man_btn;
	private Button sex_woman_btn;

	private ImageView imageView;
	private Img_bitmap img_bitmap;
	private Uri imageUri;


	private ButtonRectangle next_btn;

	private boolean id_check = false;
	private boolean id_pk_check = false;
	private boolean pw_check = false;
	private boolean pw_ok_check = false;
	private boolean phone_num_check = false;
	private boolean phone_check = false;
	private boolean name_check = false;
	private boolean sex_check = false;
	
	private boolean man_check = false;
	private boolean woman_check = false;

	Resources resource;
	private HTTP_Handler handler;

	//	search_id, search_pass, join

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join01_activity);

		this.Act = this;

        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

		resource = this.getResources();

		handler = new HTTP_Handler(this, this);
		userProfile_bean = new UserProfile_bean();

		next_btn = (ButtonRectangle)findViewById(R.id.join01_next_btn);
		next_btn.setOnClickListener(this);
//		next_btn.setTypeface(Static_date.myfont);

		img_bitmap = new Img_bitmap(Act);

		imageView = (ImageView)findViewById(R.id.join01_profileimg_iv);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


                img_bitmap.showimgdialog();


			}
		});

		//		join1_title_tv = (TextView)findViewById(R.id.join1_title_tv);
		//		join1_title_tv.setTypeface(Static_date.myfont);

		id_et = (EditText)findViewById(R.id.join01_email_et);
//		id_et.setTypeface(Static_date.myfont);
		id_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				Regex reg1 = new Regex();
				if (reg1.emailcheck(s.toString())) {
//					idck_bt.setTextColor(resource.getColor(R.color.lightblue_500));
					id_check = true;
					//					idck_bt.setEnabled(true);

				}

				else{
//					idck_bt.setTextColor(resource.getColor(R.color.hintcolor));
					id_check = false;
					//					idck_bt.setEnabled(false);

				}


			}
		});
		pw_et = (MaterialEditText)findViewById(R.id.join01_pw_et);
        pw_et.setErrorColor(resource.getColor(R.color.lightblue_300));
//		pw_et.setTypeface(Static_date.myfont);
		pw_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}


			@Override
			public void afterTextChanged(Editable s) {


				Regex reg1 = new Regex();
				if (reg1.pwdcheck(s.toString())) {



					pw_check = true;

				}

				else{
					pw_check = false;

				}


			}
		});


		pwck_et = (MaterialEditText)findViewById(R.id.join01_pwck_et);
//		pwck_et.setTypeface(Static_date.myfont);
		pwck_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {


				boolean pw_check = pw_et.getEditableText().toString().equals(s.toString());


				if (pw_check) {

					pw_ok_check = true;

				}

				else{
					pw_ok_check = false;

				}


			}
		});

		phone_send_bt = (ButtonFlat)findViewById(R.id.join01_phonesend_btn);
//		phone_send_bt.setTypeface(Static_date.myfont);
		phone_send_bt.setOnClickListener(this);

		phone_et = (EditText)findViewById(R.id.join01_phone_et);
//		phone_et.setTypeface(Static_date.myfont);
		phone_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				Regex reg1 = new Regex();
				if (reg1.phonenumbercheck(s.toString())) {

					phone_num_check = true;

//					phone_send_bt.setTextColor(resource.getColor(R.color.lightblue300));

				}

				else{
					phone_num_check = false;
//					phone_send_bt.setTextColor(resource.getColor(R.color.hintcolor));
//					phone_check_bt.setTextColor(resource.getColor(R.color.hintcolor));

				}

			}
		});




		phone_check_bt = (ButtonFlat)findViewById(R.id.join01_phonecheckok_btn);
//		phone_check_bt.setTypeface(Static_date.myfont);
		phone_check_bt.setOnClickListener(this);

		idck_bt = (ButtonFlat)findViewById(R.id.join01_emailck_btn);
//		idck_bt.setTypeface(Static_date.myfont);
		idck_bt.setOnClickListener(this);



		name_et = (MaterialEditText)findViewById(R.id.join01_name_et);
//		name_et.setTypeface(Static_date.myfont);
		name_et.setOnClickListener(this);
		name_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				Regex reg1 = new Regex();
				if (reg1.namecheck(s.toString())) {

					name_check = true;


				}

				else{

					name_check = false;

				}

			}
		});


		sex_man = (RelativeLayout)findViewById(R.id.join01_man_check_rl);
		sex_man_btn = (Button)findViewById(R.id.join01_man_check_btn);

		sex_man_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				sex_man.setBackgroundResource(R.drawable.mancheck_y);
				sex_woman.setBackgroundResource(R.drawable.womancheck_n);
				man_check = true;
				woman_check = false;
				sex_check =true;

			}
		});

		sex_woman = (RelativeLayout)findViewById(R.id.join01_woman_check_rl);
		sex_woman_btn = (Button)findViewById(R.id.join01_woman_check_btn);
		sex_woman_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				sex_man.setBackgroundResource(R.drawable.mancheck_n);
				sex_woman.setBackgroundResource(R.drawable.womancheck_y);
				man_check = false;
				woman_check = true;
				sex_check =true;

			}
		});




	}
	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.join01_emailck_btn:

			if (!id_check) {
				Toast.makeText(getApplicationContext(), "이메일 형식을 확인해 주세요.", Toast.LENGTH_SHORT).show();
				return;
			}

			id = id_et.getEditableText().toString();
			JOIN_IDCHECK_HTTP http = new JOIN_IDCHECK_HTTP(handler, id, handler.HTTP_RECEIVE_JOIN_IDCHECK);
			http.start();


			break;

		case R.id.join01_phonesend_btn:

			if (!phone_num_check) {
				Toast.makeText(getApplicationContext(), "핸드폰 번호는 10자 이상이여야 합니다.", Toast.LENGTH_SHORT).show();
				return;
			}


//			phone_check_bt.setTextColor(resource.getColor(R.color.lightblue300));

			break;
		case R.id.join01_phonecheckok_btn:

			if (!phone_num_check) {
				Toast.makeText(getApplicationContext(), "전송을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
				return;
			}



			phone_check = true;

			break;
		case R.id.join01_next_btn:
			next_ok_chek(id_pk_check,id_pk_check,pw_check,pw_ok_check,phone_check,name_check,sex_check);

			break;

		default:
			break;
		}


	}
	@Override
	public void Http_Receive(Message msg) {	
		// TODO Auto-generated method stub


		switch (msg.arg1) {
		case HTTP_Handler.HTTP_RECEIVE_JOIN_IDCHECK:

			id = id_et.getEditableText().toString();
			pw = pw_et.getEditableText().toString();



			id_pk_check= true;

			Toast.makeText(getApplicationContext(), "사용가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();


			break;
			
		case HTTP_Handler.HTTP_RECEIVE_JOIN:

//			Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
			
			
			Intent intent = new Intent(getApplicationContext(), Join02Activity.class);
			intent.putExtra("userProfile_bean", userProfile_bean);
			startActivity(intent);

			break;
			
		case HTTP_Handler.HTTP_RECEIVE_FILEUPLOAD:
			
			int profileimage_idx = (Integer) msg.obj;
			
			
			userProfile_bean.setName(name);
			userProfile_bean.setPhone_number(phone);
			userProfile_bean.setSex(sex);
			userProfile_bean.setUser_id(id);
			userProfile_bean.setUser_pw(pw);
			userProfile_bean.setDevicekey(Static_date.devicekey);
			userProfile_bean.setProfileimage_idx(profileimage_idx);
			
			JOIN_HTTP join_HTTP = new JOIN_HTTP(handler, userProfile_bean, HTTP_Handler.HTTP_RECEIVE_JOIN);
			join_HTTP.start();
			
			
			

			break;
			
			
		case HTTP_Handler.HTTP_RECEIVE_FALSE:

			switch (msg.arg2) {
			case HTTP_Handler.HTTP_RECEIVE_ERO_NICK_NO_CHECK:

				Toast.makeText(getApplicationContext(), "사용 불가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();

				break;

			}


		default:
			break;
		}




	}


	public void next_ok_chek(boolean id_check,boolean id_pk_check,boolean pw_check,boolean pw_ok_check,boolean phone_check,boolean name_check,boolean sex_check) {
		// TODO Auto-generated method stub

		if (!id_check) {
			Toast.makeText(getApplicationContext(), "이메일 중복을 확인해주세요.", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!pw_check) {
			Toast.makeText(getApplicationContext(), "비밀번호는 8자 이상이여야 합니다.", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!pw_ok_check) {
			Toast.makeText(getApplicationContext(), "비밀번호 재입력이 틀립니다.", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!name_check) {
			Toast.makeText(getApplicationContext(), "이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!sex_check) {
			Toast.makeText(getApplicationContext(), "성별을 선택해 주세요.", Toast.LENGTH_SHORT).show();
			return;
		}

//		if (!phone_check) {
//			Toast.makeText(getApplicationContext(), "휴대폰 인증을 해주세요.", Toast.LENGTH_SHORT).show();
//			return;
//		}
		
		name = name_et.getEditableText().toString();
		phone = phone_et.getEditableText().toString();
		if (woman_check) sex = 1;
		if (man_check) sex = 0;
		id = id_et.getEditableText().toString();
		pw = pw_et.getEditableText().toString();
		
		
		
		
		FILE_UPLOAD_HTTP upload_HTTP = new FILE_UPLOAD_HTTP(handler, getFilesDir().getPath()+"profile.jpg", FILE_UPLOAD_HTTP.PROFILE, HTTP_Handler.HTTP_RECEIVE_FILEUPLOAD);
		upload_HTTP.start();





	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//		super.onActivityResult(requestCode, resultCode, data);

		Log.d("test", "requestCode = " +requestCode);
		Log.d("test", "resultCode = " +resultCode);
		Log.d("test", "data = " +data);

		switch(requestCode){

		case Img_bitmap.SELECT_PHOTO:

			if (resultCode == -1) {


				AssetFileDescriptor afd;
				try {

					afd = getContentResolver().openAssetFileDescriptor(imageUri, "r");

					Bitmap bitmap = img_bitmap.getPhotoBitmapOfOptions(imageUri);

					imageView.setImageBitmap(bitmap);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


			break;
		case Img_bitmap.SELECT_GALLERY:
			if (data != null) {

				Uri img_url1 = data.getData();

				if (img_url1 != null) {


					AssetFileDescriptor afd;
					try {
						afd = getContentResolver().openAssetFileDescriptor(img_url1, "r");

						Bitmap bitmap = img_bitmap.getGallaryBitmapOfOptions(afd,data);

						imageView.setImageBitmap(bitmap);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			break;

		}
	}





}
