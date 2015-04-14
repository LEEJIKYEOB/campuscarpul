package com.larvafly.http;

import android.util.Log;

import com.google.android.gms.games.multiplayer.realtime.Room;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GET_ROOM_HTTP extends HTTP_Thread {


    private int start_loc;

    public GET_ROOM_HTTP(HTTP_Handler handler, int start_loc, int resultcode) {
        super(handler, resultcode);
        this.start_loc = start_loc;
    }

    @Override
    public void run() {

        try {

            HttpClient client = new DefaultHttpClient();
            String postURL = "http://ukplio.cafe24.com/campuscarpul/RoomGet.php";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("start_loc", String.valueOf(start_loc)));


            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();

            if (resEntity != null) {

                String result = EntityUtils.toString(resEntity);
                Log.i("RESPONSE", result);
                Log.i("RESPONSE", "start_loc = " + start_loc);

                JSONObject jsonObject = new JSONObject(result);

                int result1 = jsonObject.getInt("result_code");

                if (result1 == HTTP_Handler.HTTP_RECEIVE_TRUE) {

                    int result_index = jsonObject.getInt("result_index");

                    if (result_index != 0) {

                        JSONArray jsonArray = jsonObject.getJSONArray("result_data");
                        ArrayList<Room_bean> array_bean = new ArrayList<Room_bean>();


                        Log.d("test", "jsonArray.length() = " + jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            Room_bean bean = new Room_bean();

                            JSONObject json_data = new JSONObject(jsonArray.getString(i));

//                            "idx" => $row [idx],
//                                    "order_id" => $row [order_id],
//                                    "time" => $row [time],
//                                    "start_locale" => $row [start_locale],
//                                    "end_locale" => $row [end_locale],
//                                    "max_personnel" => $row [max_personnel],
//                                    "now_personnel" => $row [now_personnel],
//                                    "car_number" => $row [car_number],
//                                    "people_idx_1" => $row [people_idx_1],
//                                    "people_idx_2" => $row [people_idx_2],
//                                    "people_idx_3" => $row [people_idx_3],
//                                    "people_idx_4" => $row [people_idx_4]


                            try {
//                                bean.setCreate_time(json_data.getString("create_time"));
                                bean.setIdx(json_data.getInt("idx"));
                                bean.setOrder_id(json_data.getInt("order_id"));
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                bean.setTime(simpleDateFormat.parse(json_data.getString("time")));
                                bean.setStart_locale(json_data.getInt("start_locale"));
                                bean.setEnd_locale(json_data.getInt("end_locale"));
                                bean.setMax_personnerl(json_data.getInt("max_personnel"));
                                bean.setNow_personnerl(json_data.getInt("now_personnel"));
                                bean.setCar_number(json_data.getString("car_number"));
                                bean.setPeople_idx_1(json_data.getInt("people_idx_1"));
                                bean.setPeople_idx_2(json_data.getInt("people_idx_2"));
                                bean.setPeople_idx_3(json_data.getInt("people_idx_3"));
                                bean.setPeople_idx_4(json_data.getInt("people_idx_4"));
                                bean.setState(json_data.getInt("state"));
                                bean.setOrder_img_uri(json_data.getString("order_img_uri"));
                                bean.setOrder_name(json_data.getString("order_name"));
                                bean.setOrder_devicekey(json_data.getString("order_devicekey"));
                                bean.setPeople1_devicekey(json_data.getString("people1_devicekey"));
                                bean.setPeople2_devicekey(json_data.getString("people2_devicekey"));
                                bean.setPeople3_devicekey(json_data.getString("people3_devicekey"));
                                bean.setPeople4_devicekey(json_data.getString("people4_devicekey"));


                            } catch (Exception e) {
                                // TODO: handle exception
                                Log.d("test", "error = " + e.toString());
                            }

                            array_bean.add(bean);

                        }


                        Log.d("test", "array_bean.size() = " + array_bean.size());

                        message.obj = array_bean;
                        http_Handler.sendMessage(message);

                    } else {

                        ArrayList<Room_bean> array_bean = new ArrayList<Room_bean>();
                        message.obj = array_bean;
                        http_Handler.sendMessage(message);

                    }


                } else {
                    message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
                    message.arg2 = http_Handler.HTTP_RECEIVE_ERROR_CODE_20002;
                    http_Handler.sendMessage(message);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("test", "message.obj = " + message.obj);


    }

}
