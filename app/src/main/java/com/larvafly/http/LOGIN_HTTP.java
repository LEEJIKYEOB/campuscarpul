package com.larvafly.http;

import java.util.ArrayList;
import java.util.List;

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

import com.larvafly.bean.UserProfile_bean;

import android.util.Log;

public class LOGIN_HTTP extends HTTP_Thread {

    private String user_id = null;
    private String user_pw = null;

    public LOGIN_HTTP(HTTP_Handler handler, String user_id, String user_pw, int resultcode) {
        super(handler, resultcode);

        this.user_id = user_id;
        this.user_pw = user_pw;
    }

    @Override
    public void run() {
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://ukplio.cafe24.com/campuscarpul/Login.php";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", user_id));
            params.add(new BasicNameValuePair("user_pw", user_pw));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();

            if (resEntity != null) {

                String result = EntityUtils.toString(resEntity);
                Log.i("RESPONSE", result);

                JSONObject jsonObject = new JSONObject(result);

                int result_code = jsonObject.getInt("result_code");


                if (result_code == HTTP_Handler.HTTP_RECEIVE_TRUE) {


                    UserProfile_bean bean = new UserProfile_bean();
                    bean.setIdx(jsonObject.getInt("idx"));
                    bean.setUser_id(jsonObject.getString("user_id"));
                    bean.setName(jsonObject.getString("name"));
                    bean.setSex(jsonObject.getInt("sex"));
                    bean.setPhone_number(jsonObject.getString("phone_number"));
                    bean.setState(jsonObject.getInt("state"));
                    bean.setDevicekey(jsonObject.getString("device_key"));
                    bean.setCar_number(jsonObject.getString("car_number"));
                    bean.setProfileimage(jsonObject.getString("img"));

                    message.obj = bean;
                } else {

                    message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
                    message.arg2 = result_code;
                }
            }


            http_Handler.sendMessage(message);


        } catch (Exception e) {
            message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
            message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20002;
            http_Handler.sendMessage(message);

        }


    }
}
