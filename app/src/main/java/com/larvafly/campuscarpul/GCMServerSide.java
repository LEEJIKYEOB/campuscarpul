package com.larvafly.campuscarpul;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;

import com.google.android.gcm.server.*;
import com.larvafly.lib.Static_date;

public class GCMServerSide {

	public void sendMessage(Context context,ArrayList<String> devicekey) throws IOException {

		Sender sender = new Sender(Static_date.APPKEY);
		
//		bean.setSend_me_check(false);

//		devicekey = Static_date.registerGcm(context);

		Message message = new Message.Builder().addData("msg", "aaa").build();


		MulticastResult multiResult = sender.send(message, devicekey, 5);

		if (multiResult != null) {

			List<Result> resultList = multiResult.getResults();


			for (Result result : resultList) {

				System.out.println(result.getMessageId());

			}

		}

	}
}