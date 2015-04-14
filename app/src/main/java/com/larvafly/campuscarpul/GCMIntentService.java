package com.larvafly.campuscarpul;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.androidquery.AQuery;
import com.google.android.gcm.GCMBaseIntentService;
import com.larvafly.bean.Gcm_bean;
import com.larvafly.bean.Room_bean;
import com.larvafly.lib.Static_date;
import com.larvafly.campuscarpul.R;

public class GCMIntentService extends GCMBaseIntentService {

    private static void generateNotification(Context context, Gcm_bean gcm_bean) {

        int icon = R.drawable.ic_notification;
        long when = System.currentTimeMillis();


        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(icon, gcm_bean.getMsg(), when);
        //		String title = context.getString(R.string.app_name);

        Class a = IntroActivity.class;

//        gcm_bean.getState()

        if (MainActivity.MainActivity != null) {

            if (gcm_bean.getState() == 4) {
                a = MainActivity.class;
            } else {
                a = RemoteActivity.class;
            }

        }

        Intent notificationIntent = new Intent(context, a);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        notificationIntent.putExtra("roomidx", gcm_bean.getRoomidx());

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);


        notification.setLatestEventInfo(context, gcm_bean.getTitle(), gcm_bean.getMsg(), intent);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }

    @Override
    protected void onError(Context arg0, String arg1) {

    }

    @Override
    protected void onMessage(Context context, Intent intent) {


        String msg = intent.getStringExtra("msg");
        Log.e("getmessage", "getmessage:" + msg);

        vibrator();

        JSONObject jsonObject = null;
        Gcm_bean gcm_bean = null;
        try {


            jsonObject = new JSONObject(msg);
            gcm_bean = new Gcm_bean();
            gcm_bean.setState(jsonObject.getInt("state"));
            gcm_bean.setMsg(jsonObject.getString("msg"));
            gcm_bean.setTitle(jsonObject.getString("title"));
            gcm_bean.setRoomidx(jsonObject.getInt("roomidx"));

        } catch (JSONException e) {


        }

        generateNotification(context, gcm_bean);

    }


    private void vibrator() {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {200, 250, 150, 400};
        vibe.vibrate(pattern, -1);
    }


    @Override
    protected void onRegistered(Context context, String reg_id) {
        Log.e("키를 등록합니다.(GCM INTENTSERVICE)", reg_id);
        Static_date.myProfile.setDevicekey(reg_id);
    }


    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        Log.e("키를 제거합니다.(GCM INTENTSERVICE)", "제거되었습니다.");
    }

}