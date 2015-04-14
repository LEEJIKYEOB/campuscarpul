package com.larvafly.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.larvafly.bean.UserProfile_bean;
import com.larvafly.campuscarpul.MainActivity;
import com.larvafly.campuscarpul.RemoteActivity;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.LOGIN_HTTP;

public class Static_date {

	public static final String APPKEY = "AIzaSyCeGGyv778u5rOpV3KqGWAfAe0dUpx4qIQ";
	public static final String PROJECTNUMBER = "399829914471";
    public static Typeface myfont;
    public static Typeface myfont_bold;
    public static UserProfile_bean myProfile;

    public static boolean carpopup = false;

	public static Typeface ApplyFonts(Context ct) {
		Typeface face = Typeface.createFromAsset(ct.getAssets(), "fonts/NANUMBARUNGOTHIC.mp3");
		return face;
	}
	public static Typeface ApplyFonts_bold(Context ct) {
		Typeface face = Typeface.createFromAsset(ct.getAssets(), "fonts/NANUMBARUNGOTHICBOLD.mp3");
		return face;
	}


	//	user_pw_et.setTypeface(Static_date.myfont);

	public static boolean copyFile(File file , String save_file){
		boolean result;
		if(file!=null&&file.exists()){
			try {
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream newfos = new FileOutputStream(save_file);
				int readcount=0;
				byte[] buffer = new byte[1024];
				while((readcount = fis.read(buffer,0,1024))!= -1){
					newfos.write(buffer,0,readcount);
				}
				newfos.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = true;
		}else{
			result = false;
		}
		return result;
	}

	public static String birthdayToAge(String birthday) {

		int a = Integer.parseInt(birthday.substring(0, 2));
		Date date = new Date(System.currentTimeMillis());
		int y = date.getYear()+1900;

		if (a > 20) {
			a += 1900;
		}else {
			a += 2000;
		}
		int t = (y-a)+1;

		//		System.out.println("나이 = " + t);

		String ccc = String.valueOf(t).substring(0, 1) + "0대 ";

		if (Integer.parseInt(String.valueOf(t).substring(1, 2)) < 3) ccc += "초반";
		if ((4 <= Integer.parseInt(String.valueOf(t).substring(1, 2))) && Integer.parseInt(String.valueOf(t).substring(1, 2)) < 7) ccc += "중반";
		if ((7 <= Integer.parseInt(String.valueOf(t).substring(1, 2))) && Integer.parseInt(String.valueOf(t).substring(1, 2)) < 10) ccc += "후반";


		return ccc;
	}

	public static String registerGcm(Context context) {

		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		final String regId = GCMRegistrar.getRegistrationId(context);


		if (regId.equals("") || regId == null) {
			GCMRegistrar.register(context, PROJECTNUMBER );
		} else {
            myProfile.setDevicekey(regId);
            Log.d("id", "appkey3 = " + regId);
		}

		return regId;

	}

	public static void saveLoginSharedPreferences(Context context, String id, String pass) {

		SharedPreferences pref = context.getSharedPreferences("HG_LOGIN",context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putString("ID", id);
		editor.putString("PW", pass);
		editor.commit();

	}

    public static void loginpross(final Activity context, final String id, final String pw, boolean showdig) {

//        HTTP_Handler handler,String user_id,String user_pw,int resultcode) {

        HTTP_Handler http_handler = new HTTP_Handler(new HTTP_Handler.OnHttpReceiveListener() {
            @Override
            public void Http_Receive(Message msg) {

                switch (msg.arg1) {
                    case HTTP_Handler.HTTP_RECEIVE_LOGIN:

                        UserProfile_bean bean = (UserProfile_bean) msg.obj;

                        Static_date.myProfile = bean;

                        saveLoginSharedPreferences(context, id, pw);

                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                        context.finish();

                        break;

                    case HTTP_Handler.HTTP_RECEIVE_FALSE:

                        switch (msg.arg2) {

                            case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20003:

                                Toast.makeText(context.getApplicationContext(), "아이디 또는 페스워드가 틀립니다.", Toast.LENGTH_SHORT).show();

                                break;


                            case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20002:

                                Toast.makeText(context.getApplicationContext(), "알수없는 에러 다시시도해주세요", Toast.LENGTH_SHORT).show();

                                break;


                        }

                        break;

                    default:
                        break;
                }

            }
        }, context);

        LOGIN_HTTP login_http = new LOGIN_HTTP(http_handler, id, pw, HTTP_Handler.HTTP_RECEIVE_LOGIN);
        login_http.setShowdialog(showdig);
        login_http.start();

    }


    public static void loginpross(final Activity context, final String id, final String pw, final int roomidx) {

//        HTTP_Handler handler,String user_id,String user_pw,int resultcode) {

        HTTP_Handler http_handler = new HTTP_Handler(new HTTP_Handler.OnHttpReceiveListener() {
            @Override
            public void Http_Receive(Message msg) {

                switch (msg.arg1) {
                    case HTTP_Handler.HTTP_RECEIVE_LOGIN:

                        UserProfile_bean bean = (UserProfile_bean) msg.obj;

                        Static_date.myProfile = bean;

                        saveLoginSharedPreferences(context, id, pw);

                        Intent intent = null;
                        if (roomidx == -1) {
                            intent = new Intent(context, MainActivity.class);
                        } else {
                            intent = new Intent(context, RemoteActivity.class);
                            intent.putExtra("roomidx", roomidx);

                        }
                        context.startActivity(intent);

                        context.finish();

                        break;

                    case HTTP_Handler.HTTP_RECEIVE_FALSE:

                        switch (msg.arg2) {

                            case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20003:

                                Toast.makeText(context.getApplicationContext(), "아이디 또는 페스워드가 틀립니다.", Toast.LENGTH_SHORT).show();

                                break;


                            case HTTP_Handler.HTTP_RECEIVE_ERROR_CODE_20002:

                                Toast.makeText(context.getApplicationContext(), "알수없는 에러 다시시도해주세요", Toast.LENGTH_SHORT).show();

                                break;


                        }

                        break;

                    default:
                        break;
                }

            }
        }, context);

        LOGIN_HTTP login_http = new LOGIN_HTTP(http_handler, id, pw, HTTP_Handler.HTTP_RECEIVE_LOGIN);
        login_http.setShowdialog(false);
        login_http.start();

    }


}
