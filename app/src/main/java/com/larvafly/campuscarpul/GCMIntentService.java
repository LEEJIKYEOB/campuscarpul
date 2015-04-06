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
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.androidquery.AQuery;
import com.google.android.gcm.GCMBaseIntentService;
import com.larvafly.lib.Static_date;
import com.larvafly.campuscarpul.R;

public class GCMIntentService extends GCMBaseIntentService {

//	private static void generateNotification(Context context, String message,String main_img,String you_nick_name) {
//
//		int icon = R.drawable.ic_launcher;
//		long when = System.currentTimeMillis();
//
//
//		NotificationManager notificationManager = (NotificationManager) context
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//
//		Notification notification = new Notification(icon, message, when);
//
//		//		String title = context.getString(R.string.app_name);
//
//		Intent notificationIntent = new Intent(context, IntroActivity.class);
//
//		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		PendingIntent intent = PendingIntent.getActivity(context, 0,
//				notificationIntent, 0);
//
//
//
//		notification.setLatestEventInfo(context, you_nick_name, message, intent);
//
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//		notificationManager.notify(0, notification);
//
//	}

	void notificationWithColorFont(Context context, String message) {
		
		int icon = R.drawable.ic_launcher;
		
		AQuery aQuery = new AQuery(context);
//		Bitmap bitmap = aQuery.getCachedImage(img_url);
//		aQuery.get

		Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.push);

		Intent intent = new Intent(context, IntroActivity.class);
//		intent.putExtra("gcmstate", gcmstate); //join
//		intent.putExtra("youidx", youidx); //join
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
		.setAutoCancel(true)
		.setSmallIcon(icon)
		.setLargeIcon(largeIcon)
		.setTicker(message)			
		.setContentTitle(message)
		.setContentText(message)
		.setContentIntent(pendingIntent)
		.setContent(remoteViews);

		remoteViews.setTextViewText(R.id.push_title, "bbb");
		remoteViews.setTextViewText(R.id.push_message, message);
		remoteViews.setImageViewBitmap(R.id.push_icon, null);

		builder.setDefaults(Notification.DEFAULT_VIBRATE);
		builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
	}

	@Override
	protected void onError(Context arg0, String arg1) {

	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		

		String msg = intent.getStringExtra("msg");
		Log.e("getmessage", "getmessage:" + msg);


//		notificationWithColorFont(context,"(사진)",bean.getYou_nick_name(),bean.getMain_img(),bean.getGcmstate(),bean.getYouidx());
				

	}



	@Override
	protected void onRegistered(Context context, String reg_id) {
		Log.e("키를 등록합니다.(GCM INTENTSERVICE)", reg_id);
		Static_date.devicekey = reg_id;
	}



	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.e("키를 제거합니다.(GCM INTENTSERVICE)","제거되었습니다.");
	}

}