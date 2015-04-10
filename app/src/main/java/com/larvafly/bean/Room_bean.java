package com.larvafly.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Room_bean implements Serializable{

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

    int idx;
    int order_id;
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


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeString(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(time);

    }
//    2015-04-23 04:17:55

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setStart_locale(int start_locale) {
        this.start_locale = start_locale;
    }

    public void setEnd_locale(int end_locale) {
        this.end_locale = end_locale;
    }

    public void setMax_personnerl(int max_personnerl) {
        this.max_personnerl = max_personnerl;
    }

    public void setNow_personnerl(int now_personnerl) {
        this.now_personnerl = now_personnerl;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public void setPeople_idx_1(int people_idx_1) {
        this.people_idx_1 = people_idx_1;
    }

    public void setPeople_idx_2(int people_idx_2) {
        this.people_idx_2 = people_idx_2;
    }

    public void setPeople_idx_3(int people_idx_3) {
        this.people_idx_3 = people_idx_3;
    }

    public void setPeople_idx_4(int people_idx_4) {
        this.people_idx_4 = people_idx_4;
    }

    public int getIdx() {
        return idx;
    }

    public int getOrder_id() {
        return order_id;
    }

    public Date getTime() {
        return time;
    }

    public int getStart_locale() {
        return start_locale;
    }

    public int getEnd_locale() {
        return end_locale;
    }

    public int getMax_personnerl() {
        return max_personnerl;
    }

    public int getNow_personnerl() {
        return now_personnerl;
    }

    public String getCar_number() {
        return car_number;
    }

    public int getPeople_idx_1() {
        return people_idx_1;
    }

    public int getPeople_idx_2() {
        return people_idx_2;
    }

    public int getPeople_idx_3() {
        return people_idx_3;
    }

    public int getPeople_idx_4() {
        return people_idx_4;
    }
}