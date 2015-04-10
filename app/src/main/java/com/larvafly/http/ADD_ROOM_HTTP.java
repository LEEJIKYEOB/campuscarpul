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

public class ADD_ROOM_HTTP extends HTTP_Thread{


    private Room_bean room_bean = null;

    public ADD_ROOM_HTTP(HTTP_Handler handler, Room_bean room_bean, int resultcode) {
        super(handler,resultcode);

        this.room_bean = room_bean;

    }

    @Override
    public void run()
    {

        if (roomcheck()) {
            try {

//            $order_id = $_POST [order_id];
//            $time = $_POST [time];
//            $start_locale = $_POST [start_locale];
//            $end_locale = $_POST [end_locale];
//            $max_personnel = $_POST [max_personnel];
//            $car_number = $_POST [car_number];


//            if (result_code == HTTP_Handler.HTTP_RECEIVE_TRUE) {
//
//            }else{
//                message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
//                message.arg2 = http_Handler.HTTP_RECEIVE_ERO_NICK_NO_CHECK;
//            }


                HttpClient client = new DefaultHttpClient();
                String postURL = "http://ukplio.cafe24.com/campuscarpul/InsertRoom.php";
                HttpPost post = new HttpPost(postURL);

                Log.d("test","room_bean.getTimeString() = "+ room_bean.getTimeString());

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("order_id", String.valueOf(room_bean.getOrder_id())));
                params.add(new BasicNameValuePair("time", room_bean.getTimeString()));
                params.add(new BasicNameValuePair("start_locale", String.valueOf(room_bean.getStart_locale())));
                params.add(new BasicNameValuePair("end_locale", String.valueOf(room_bean.getEnd_locale())));
                params.add(new BasicNameValuePair("max_personnel", String.valueOf(room_bean.getMax_personnerl())));
                params.add(new BasicNameValuePair("car_number", room_bean.getCar_number()));


                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                post.setEntity(ent);
                HttpResponse responsePOST = client.execute(post);
                HttpEntity resEntity = responsePOST.getEntity();

                if (resEntity != null) {

                    String result = EntityUtils.toString(resEntity);
                    Log.i("RESPONSE", result);

                    JSONObject jsonObject = new JSONObject(result);

                    int result1 = jsonObject.getInt("result_code");

                    if (result1 == HTTP_Handler.HTTP_RECEIVE_FALSE){
                        message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
                        message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20002;
                    }

//				Log.i("RESPONSE", jsonObject.getString("result"));


                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }else{
            message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
            message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20001;
        }
        http_Handler.sendMessage(message);

    }




    boolean roomcheck(){

        try {

//            $order_id = $_POST [order_id];
//            $time = $_POST [time];
//            $start_locale = $_POST [start_locale];
//            $end_locale = $_POST [end_locale];
//            $max_personnel = $_POST [max_personnel];
//            $car_number = $_POST [car_number];


//            if (result_code == HTTP_Handler.HTTP_RECEIVE_TRUE) {
//
//            }else{
//                message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
//                message.arg2 = http_Handler.HTTP_RECEIVE_ERO_NICK_NO_CHECK;
//            }

            HttpClient client = new DefaultHttpClient();
            String postURL = "http://ukplio.cafe24.com/campuscarpul/RoomCheck.php";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_id", String.valueOf(room_bean.getOrder_id())));


            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();

            if (resEntity != null) {

                String result = EntityUtils.toString(resEntity);
                Log.i("RESPONSE", result);

                JSONObject jsonObject = new JSONObject(result);

                int result1 = jsonObject.getInt("result_code");

                if (result1 == HTTP_Handler.HTTP_RECEIVE_TRUE){
                    return true;
                }else{
                    return false;
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return  false;
        }
        return false;
    }
}
