package com.larvafly.bean;

import com.larvafly.lib.Static_date;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gcm_bean implements Serializable {


//    GCM =
//    state
//    1  = 방에 참가했을떄 (참가자 -> 운전자) start -> 방정보
//    2  = 참가자가 방을 나갔을떄 (참가자 -> 운전자) start -> 방정보
//    3  = 참가자가 목적지에 도착했을때 (참가자 -> 운전자) start -> 리모컨
//    4  = 방이 없어졌을때 (운전자 -> 참가자) start -> 메인
//    5  = 운전자의 차가 출발할떄 (운전자 -> 참가자) start -> 리모컨 (초록불)
//    6  = 운전자의 차가 도착해갈때 (운전자 -> 참가자) start -> 리모컨 (노랑불)
//    7  = 운전자의 차가 도착했을때 (운전자 -> 참가자) start -> 리모컨 (빨강불)

//
//    public static final int STATE_READY = 0;
//    public static final int STATE_GREEN = 1;
//    public static final int STATE_YELLOW = 2;
//    public static final int STATE_RED = 3;
//    public static final int STATE_END = 4;


    public static final int JOIN_ROOM = 1;
    public static final int OUT_ROOM = 2;
    public static final int READY_ROOM = 3;
    public static final int END_ROOM = 4;
    public static final int STATE_GREEN = 5;
    public static final int STATE_YELLOW = 6;
    public static final int STATE_RED = 7;

    String title;
    String msg;
    int state;
    int roomidx;


    public int getRoomidx() {
        return roomidx;
    }

    public void setRoomidx(int roomidx) {
        this.roomidx = roomidx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", getTitle());
            jsonObject.put("msg", getMsg());
            jsonObject.put("state", getState());
            jsonObject.put("roomidx", getRoomidx());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

}
