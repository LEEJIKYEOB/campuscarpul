package com.larvafly.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HTTP_Handler extends Handler {

    public static final int DIALOG_SHOW = 1;
    public static final int DIALOG_HIDE = 2;
    public static final int DIALOG_CLOSE = 3;
    public final static int HTTP_RECEIVE_FALSE = -1;
    public final static int HTTP_RECEIVE_TRUE = 0;
    public static final int HTTP_RECEIVE_LOGIN = 1001;
    public static final int HTTP_RECEIVE_JOIN_IDCHECK = 1002;
    public static final int HTTP_RECEIVE_JOIN = 1003;
    public static final int HTTP_RECEIVE_FILEUPLOAD = 1004;
    public static final int HTTP_RECEIVE_ADD_ROOM = 1005;
    public static final int HTTP_RECEIVE_GET_ROOM = 1006;
    public static final int HTTP_RECEIVE_JOIN_ROOM = 1007;
    public static final int HTTP_RECEIVE_CANCEL_ROOM = 1008;
    public static final int HTTP_RECEIVE_CAR_UPDATA = 1009;
    public static final int HTTP_RECEIVE_ERO_NICK_NO_CHECK = -1;
    public static final int HTTP_RECEIVE_ERROR_CODE_20000 = 20000; //방이 꽉찻을때
    //    $error_code_20001 = 20001; //참가하려는대 이미 참가한사람일떄
    public static final int HTTP_RECEIVE_ERROR_CODE_20001 = 20001;
    //    $error_code_20002 = 20002; //이유모름
    public static final int HTTP_RECEIVE_ERROR_CODE_20002 = 20002;
    public static final int HTTP_RECEIVE_ERROR_CODE_20003 = 20003; //아이디나 페스워드가 틀릴때;
    public int resultcode;

    /////////////////////////////////////////////////////// ERO CODE
    private OnHttpReceiveListener onHttpReceiveListener;
    private ProgressDialog dialog;
    private int dialogstate = 2;
    private int use_therad = 0;
    private Context context;

    ////////////////////////////////////////////////////////////////

    public HTTP_Handler(OnHttpReceiveListener onHttpReceiveListener, Context context) {
        // TODO Auto-generated constructor stub

        this.onHttpReceiveListener = onHttpReceiveListener;
        this.context = context;


        dialog = new ProgressDialog(context);
        dialog.setMessage("처리중입니다. 잠시만 기다려 주세요");
        dialog.setIndeterminate(true);
    }

    public synchronized void upusetherad() {

        use_therad++;

    }

    public synchronized void downusetherad() {

        use_therad--;

    }

    public synchronized boolean isdialog() {

        if (use_therad == 0) {
            return true;
        }

        return false;
    }


    @Override
    public void handleMessage(Message msg) {
        // TODO Auto-generated method stub

        Log.d("test", "message.obj = " + msg.obj);
        super.handleMessage(msg);
        Log.d("test", "message.obj = " + msg.obj);

        downusetherad();

        if (isdialog()) {
            hidedialog();
        }


        if (!(msg.arg1 == HTTP_RECEIVE_FALSE)) {
            msg.arg1 = resultcode;

            Log.d("test", "message.obj = " + msg.obj);
            onHttpReceiveListener.Http_Receive(msg);
        } else {
            onHttpReceiveListener.Http_Receive(msg);
        }
    }


    public void showdialog() {


        if (isdialog()) {
            dialogstate = DIALOG_SHOW;
            dialog.show();
        }

        upusetherad();


    }

    public void hidedialog() {


        dialogstate = DIALOG_HIDE;
        dialog.cancel();

    }


    public Context getContext() {
        return context;
    }


    public interface OnHttpReceiveListener {
        public void Http_Receive(Message msg);
    }

}
