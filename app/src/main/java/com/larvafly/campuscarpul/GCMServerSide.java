package com.larvafly.campuscarpul;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;

import com.google.android.gcm.server.*;
import com.larvafly.bean.Gcm_bean;
import com.larvafly.lib.Static_date;

public class GCMServerSide extends Thread {


    private Context context;
    private ArrayList<String> devicekey;
    private Gcm_bean gcm_bean;

    public GCMServerSide(Context context, ArrayList<String> devicekey, Gcm_bean gcm_bean) {

        this.context = context;
        this.devicekey = devicekey;
        this.gcm_bean = gcm_bean;

    }

    @Override
    public void run() {

        try {
            sendMessage();
        } catch (Exception e) {

        }
    }

    public void sendMessage() throws IOException {


        ArrayList<String> devicekey1 = new ArrayList<>();
        devicekey1.add(Static_date.myProfile.getDevicekey());

        Sender sender = new Sender(Static_date.APPKEY);

        Message message = new Message.Builder().addData("msg", gcm_bean.getJSON()).build();


        MulticastResult multiResult = sender.send(message, devicekey1, 5);

        if (multiResult != null) {

            List<Result> resultList = multiResult.getResults();


            for (Result result : resultList) {

                System.out.println(result.getMessageId());

            }

        }

    }
}