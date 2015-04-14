package com.larvafly.bean;

import com.larvafly.lib.Static_date;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Room_bean implements Serializable {

// idx	int(11)			아니오		auto_increment	 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    order_id	int(11)			아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    time	datetime			아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    start_locale	int(11)			아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    end_locale	int(11)			아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    max_personnel	int(11)			아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    now_personnel	int(11)			아니오	0		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    car_number	varchar(10)	utf8_general_ci		아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	 Fulltext
//    people_idx_1	int(11)			아니오	-1		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    people_idx_2	int(11)			아니오	-1		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    people_idx_3	int(11)			아니오	-1		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//    people_idx_4	int(11)			아니오	-1		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//            state

    public static final int STATE_READY = 0;
    public static final int STATE_GREEN = 1;
    public static final int STATE_YELLOW = 2;
    public static final int STATE_RED = 3;
    public static final int STATE_END = 4;

//    * state = 0 대기상태
//                    * state = 1 출발 파란불
//            * state = 2 노란불
//                    * state = 3 빨간불
//                    * state = 4 방종료

    int idx;
    int order_id;
    String order_name;
    Date time;
    int start_locale;
    int end_locale;
    int max_personnerl;
    int now_personnerl;
    String car_number;
    int people_idx_1;
    int people_idx_2;
    int people_idx_3;
    int people_idx_4;
    int state;

    String order_devicekey;
    String people1_devicekey = null;
    String people2_devicekey = null;
    String people3_devicekey = null;
    String people4_devicekey = null;

    String order_img_uri;

    public ArrayList<String> getOrder_devicekey() {
        ArrayList<String> t = new ArrayList<>();
        t.add(order_devicekey);
        return t;
    }

    public void setOrder_devicekey(String order_devicekey) {
        this.order_devicekey = order_devicekey;
    }


    public void setPeople1_devicekey(String people1_devicekey) {
        this.people1_devicekey = people1_devicekey;
    }

    public void setPeople2_devicekey(String people2_devicekey) {
        this.people2_devicekey = people2_devicekey;
    }

    public void setPeople3_devicekey(String people3_devicekey) {
        this.people3_devicekey = people3_devicekey;
    }

    public ArrayList<String> getPeople_devicekey() {

        ArrayList<String> t = new ArrayList<>();
        if (people1_devicekey != null) t.add(people1_devicekey);
        if (people2_devicekey != null) t.add(people2_devicekey);
        if (people3_devicekey != null) t.add(people3_devicekey);
        if (people4_devicekey != null) t.add(people4_devicekey);

        return t;
    }

    public void setPeople4_devicekey(String people4_devicekey) {
        this.people4_devicekey = people4_devicekey;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_img_uri() {
        return order_img_uri;
    }

    public void setOrder_img_uri(String order_img_uri) {
        this.order_img_uri = "http://ukplio.cafe24.com/" + order_img_uri.substring(3);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeString() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);

    }
//    2015-04-23 04:17:55

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStart_locale() {
        return start_locale;
    }

    public void setStart_locale(int start_locale) {
        this.start_locale = start_locale;
    }

    public int getEnd_locale() {
        return end_locale;
    }

    public void setEnd_locale(int end_locale) {
        this.end_locale = end_locale;
    }

    public int getMax_personnerl() {
        return max_personnerl;
    }

    public void setMax_personnerl(int max_personnerl) {
        this.max_personnerl = max_personnerl;
    }

    public int getNow_personnerl() {
        return now_personnerl;
    }

    public void setNow_personnerl(int now_personnerl) {
        this.now_personnerl = now_personnerl;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public int getPeople_idx_1() {
        return people_idx_1;
    }

    public void setPeople_idx_1(int people_idx_1) {
        this.people_idx_1 = people_idx_1;
    }

    public int getPeople_idx_2() {
        return people_idx_2;
    }

    public void setPeople_idx_2(int people_idx_2) {
        this.people_idx_2 = people_idx_2;
    }

    public int getPeople_idx_3() {
        return people_idx_3;
    }

    public void setPeople_idx_3(int people_idx_3) {
        this.people_idx_3 = people_idx_3;
    }

    public int getPeople_idx_4() {
        return people_idx_4;
    }

    public void setPeople_idx_4(int people_idx_4) {
        this.people_idx_4 = people_idx_4;
    }

    public Boolean ismyorder() {

        boolean a = getOrder_id() == Static_date.myProfile.getIdx();

        return a;
    }

    public Boolean ismyjoin() {

        if (Static_date.myProfile.getIdx() == getPeople_idx_1()) return true;
        if (Static_date.myProfile.getIdx() == getPeople_idx_2()) return true;
        if (Static_date.myProfile.getIdx() == getPeople_idx_3()) return true;
        if (Static_date.myProfile.getIdx() == getPeople_idx_4()) return true;

        return false;
    }

    public Boolean isendroom() {

        return System.currentTimeMillis() >= getTime().getTime();
    }

}