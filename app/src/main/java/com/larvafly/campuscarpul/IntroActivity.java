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

public class IntroActivity extends Activity {

    private int roomidx;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);


            SharedPreferences prefs_login = getSharedPreferences("HG_LOGIN", MODE_PRIVATE);
            String id = prefs_login.getString("ID", "-");
            String pw = prefs_login.getString("PW", "-");

            if (!id.equals("-") && !pw.equals("-")) {

                if (roomidx == -1) {
                    Static_date.loginpross(IntroActivity.this, id, pw, false);
                } else {
                    Static_date.loginpross(IntroActivity.this, id, pw, roomidx);
                }

            } else {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            //			Toast.makeText(getApplicationContext(), "��ŸƮ", Toast.LENGTH_SHORT).show();


        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        Intent intent = getIntent();
        roomidx = intent.getIntExtra("roomidx", -1);

        Static_date.myProfile = new UserProfile_bean();

        Static_date.myfont = Static_date.ApplyFonts(this);
        Static_date.myfont_bold = Static_date.ApplyFonts_bold(this);
        Static_date.registerGcm(getApplicationContext());

        handler.sendEmptyMessageDelayed(0, 2000);


    }

}
