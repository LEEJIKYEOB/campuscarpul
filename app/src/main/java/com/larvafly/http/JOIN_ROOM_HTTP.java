package com.larvafly.http;

import android.util.Log;

import com.larvafly.bean.Room_bean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JOIN_ROOM_HTTP extends HTTP_Thread {

    int user_id;
    int room_id;

    public JOIN_ROOM_HTTP(HTTP_Handler handler, int user_id, int room_id, int resultcode) {
        super(handler, resultcode);

        this.user_id = user_id;
        this.room_id = room_id;

    }

    @Override
    public void run() {

        if (roomcheck()) {
            try {

                HttpClient client = new DefaultHttpClient();
                String postURL = "http://ukplio.cafe24.com/campuscarpul/RoomJoin.php";
                HttpPost post = new HttpPost(postURL);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user_id", String.valueOf(user_id)));
                params.add(new BasicNameValuePair("room_id", String.valueOf(room_id)));


                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                post.setEntity(ent);
                HttpResponse responsePOST = client.execute(post);
                HttpEntity resEntity = responsePOST.getEntity();

                if (resEntity != null) {

                    String result = EntityUtils.toString(resEntity);
                    Log.i("RESPONSE", result);

                    JSONObject jsonObject = new JSONObject(result);

                    int result1 = jsonObject.getInt("result_code");

                    if (result1 == HTTP_Handler.HTTP_RECEIVE_TRUE) {

                    } else {
                        message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
                        message.arg2 = result1;
                    }

                    http_Handler.sendMessage(message);
                }
            } catch (Exception e) {
                message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
                message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20002;
                http_Handler.sendMessage(message);

            }
        } else {
            message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
            message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20001;
            http_Handler.sendMessage(message);
        }

    }


    boolean roomcheck() {

        try {

            HttpClient client = new DefaultHttpClient();
            String postURL = "http://ukplio.cafe24.com/campuscarpul/RoomCheck.php";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", String.valueOf(user_id)));


            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();

            if (resEntity != null) {

                String result = EntityUtils.toString(resEntity);
                Log.i("RESPONSE", result);

                JSONObject jsonObject = new JSONObject(result);

                int result1 = jsonObject.getInt("result_code");

                if (result1 == HTTP_Handler.HTTP_RECEIVE_TRUE) {
                    return true;
                } else {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
