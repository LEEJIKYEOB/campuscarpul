package com.larvafly.http;

import android.os.Message;

public class HTTP_Thread extends Thread{
	
	HTTP_Handler http_Handler;
	Message message;
	
	boolean showdialog = true;
	
	public HTTP_Thread(HTTP_Handler handler,int resultcode) {
		// TODO Auto-generated constructor stub
		this.http_Handler = handler;
		
		Message msg = http_Handler.obtainMessage();
		
		handler.resultcode = resultcode;
		
		message = msg;
	}
	
	@Override
	public synchronized void start() {
		
		super.start();
		
		if (showdialog) {
			http_Handler.showdialog();
		}
		
	}

	public boolean isShowdialog() {
		return showdialog;
	}

	public void setShowdialog(boolean showdialog) {
		this.showdialog = showdialog;
	};
	
	
	
	
	
}
