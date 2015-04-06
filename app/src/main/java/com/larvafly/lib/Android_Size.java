package com.larvafly.lib;

import android.content.Context;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class Android_Size {

	private Context context;

	public Android_Size(Context context) {

		this.context = context;

	}


	public int gethight(int hight){	

		Display dis = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();   	
		int dishight = dis.getHeight();

		float h =(float) ((float) ((float)dishight *hight)/1200.0);

		return (int) h;
	}


	public int gethight(){	

		Display dis = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();   	
		int dishight = dis.getHeight();

		return (int) dishight;
	}


	public int getwidth(){	

		Display dis = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();   	
		int dishight = dis.getWidth();

		return (int) dishight;
	}
	
	public int gethight_1(int dp){	

		Display dis = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();   	
		int dishight = dis.getHeight();
		
		return (dishight - dpToPixel(dp));
	}

	public int dpToPixel(int dp){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
		return (int) px;
	}

}
