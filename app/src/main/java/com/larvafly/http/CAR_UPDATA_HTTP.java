package com.larvafly.http;

import android.util.Log;

import com.larvafly.bean.Room_bean;
import com.larvafly.bean.UserProfile_bean;

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

public class CAR_UPDATA_HTTP extends HTTP_Thread {


    private UserProfile_bean userProfile_bean = null;

    public CAR_UPDATA_HTTP(HTTP_Handler handler, UserProfile_bean userProfile_bean, int resultcode) {
        super(handler, resultcode);

        this.userProfile_bean = userProfile_bean;

    }

    @Override
    public void run() {

        try {

            HttpClient client = new DefaultHttpClient();
            String postURL = "http://ukplio.cafe24.com/campuscarpul/CarUpdata.php";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("carimage", String.valueOf(userProfile_bean.getCarimage_idx())));
            params.add(new BasicNameValuePair("car_number", userProfile_bean.getCar_number()));
            params.add(new BasicNameValuePair("carclass", userProfile_bean.getCar_class()));
            params.add(new BasicNameValuePair("user_id", String.valueOf(userProfile_bean.getIdx())));


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
                    message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20002;
                }

                http_Handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
            message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20002;
            http_Handler.sendMessage(message);

        }

    }

}
