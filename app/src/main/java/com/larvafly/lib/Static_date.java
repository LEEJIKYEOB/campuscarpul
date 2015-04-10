package com.larvafly.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

public class Static_date {

	public static int my_user_idx = 1;
	public static int my_state;
	public static Typeface myfont;
	public static Typeface myfont_bold;


	public static final String APPKEY = "AIzaSyCeGGyv778u5rOpV3KqGWAfAe0dUpx4qIQ";
	public static final String SERVERKEY = "AIzaSyA8cJALJaGBTU8VeU4uCUjEohAtmO9xLO0";
	public static final String PROJECTNUMBER = "399829914471";

	public static String devicekey = "";
	public static String myimg;
	public static String mynickname;





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
			devicekey = regId;
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

}
