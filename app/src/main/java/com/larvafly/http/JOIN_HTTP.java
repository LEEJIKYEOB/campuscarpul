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

public class JOIN_HTTP extends HTTP_Thread{

	private UserProfile_bean userProfile_bean = null;
	
	public JOIN_HTTP(HTTP_Handler handler,UserProfile_bean userProfile_bean,int resultcode) {
		super(handler,resultcode);
		
		this.userProfile_bean = userProfile_bean;
		
	}
	
	@Override
	public void run()
	{
		try
		{
			
			
//			$user_id = $_POST [user_id];
//			$user_pw = $_POST [user_pw];
//			$name = $_POST [name];
//			$sex = $_POST [sex];
//			$phone_number = $_POST [phone_number]; 
//			$devicekey=$_POST[devicekey];
			
			
			HttpClient client = new DefaultHttpClient();  
			String postURL = "http://ukplio.cafe24.com/campuscarpul/Insert.php";
			HttpPost post = new HttpPost(postURL); 

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", userProfile_bean.getUser_id()));
			params.add(new BasicNameValuePair("user_pw", userProfile_bean.getUser_pw()));
			params.add(new BasicNameValuePair("name", userProfile_bean.getName()));
			params.add(new BasicNameValuePair("sex", String.valueOf(userProfile_bean.getSex())));
			params.add(new BasicNameValuePair("profileimage", String.valueOf(userProfile_bean.getProfileimage_idx())));
			params.add(new BasicNameValuePair("phone_number", String.valueOf(userProfile_bean.getPhone_number())));
			params.add(new BasicNameValuePair("devicekey",userProfile_bean.getDevicekey()));
			

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);  
			HttpEntity resEntity = responsePOST.getEntity();

			if (resEntity != null)
			{    

				String result = EntityUtils.toString(resEntity);
				Log.i("RESPONSE", result);

				JSONObject jsonObject = new JSONObject(result);
				
				int result1 = jsonObject.getInt("result_code");
				
//				Log.i("RESPONSE", jsonObject.getString("result"));
				

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		http_Handler.sendMessage(message);
		
	}
}
