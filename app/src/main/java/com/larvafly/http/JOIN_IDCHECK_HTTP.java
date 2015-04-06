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
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.larvafly.bean.UserProfile_bean;

import android.app.ProgressDialog;
import android.util.Log;

public class JOIN_IDCHECK_HTTP extends HTTP_Thread{
	
	private String user_id = null;

	
	public JOIN_IDCHECK_HTTP(HTTP_Handler handler,String user_id,int resultcode) {
		super(handler,resultcode);
		
		this.user_id = user_id;
	}

	@Override
	public void run()
	{
		
		try
		{
			HttpClient client = new DefaultHttpClient();  
			String postURL = "http://ukplio.cafe24.com/campuscarpul/IDCheck.php";
			HttpPost post = new HttpPost(postURL); 

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);  
			HttpEntity resEntity = responsePOST.getEntity();

			if (resEntity != null)
			{    

				String result = EntityUtils.toString(resEntity);
				Log.i("RESPONSE", result);

				JSONObject jsonObject = new JSONObject(result);

				int result_code = jsonObject.getInt("result_code");


				
				if (result_code == HTTP_Handler.HTTP_RECEIVE_TRUE) {
					
				}else{
					message.arg1 = http_Handler.HTTP_RECEIVE_FALSE;
					message.arg2 = http_Handler.HTTP_RECEIVE_ERO_NICK_NO_CHECK;
				}
			}
			
			
			http_Handler.sendMessage(message);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

		
	}
}
