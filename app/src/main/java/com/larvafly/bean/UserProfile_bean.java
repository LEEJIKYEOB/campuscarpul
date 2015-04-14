package com.larvafly.bean;

import java.io.Serializable;

import com.larvafly.lib.Static_date;


//idx	int(11)			아니오		auto_increment	 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//        user_id	varchar(40)	utf8_general_ci		아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	 Fulltext
//        user_pw	varchar(20)	utf8_general_ci		아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	 Fulltext
//        name	varchar(10)	utf8_general_ci		아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	 Fulltext
//        sex	tinyint(1)			예	0		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//        phone_number	varchar(22)	utf8_general_ci		아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	 Fulltext
//        profileimage	int(4)			예	NULL		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//        carimage	int(4)			예	NULL		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//        state	int(4)			아니오			 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	Fulltext
//        device_key	varchar(200)	utf8_general_ci		예	NULL		 Browse distinct values	 변경	 삭제	 기본	 고유값	 인덱스	 Fulltext
//        car_number

public class UserProfile_bean implements Serializable {

    int idx;
    String user_id;
    String user_pw;
    String name;
    int sex;
    String phone_number;
    String profileimage;
    int profileimage_idx;
    String carimage;
    int carimage_idx;
    int state;
    String devicekey;
    String car_number;
    String car_class;

    public UserProfile_bean() {

        carimage = "-1";
        profileimage = "-1";
    }

    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public int getProfileimage_idx() {
        return profileimage_idx;
    }

    public void setProfileimage_idx(int profileimage_idx) {
        this.profileimage_idx = profileimage_idx;
    }

    public int getCarimage_idx() {
        return carimage_idx;
    }

    public void setCarimage_idx(int carimage_idx) {
        this.carimage_idx = carimage_idx;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDevicekey() {
        return devicekey;
    }

    public void setDevicekey(String devicekey) {
        this.devicekey = devicekey;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {

        String postURL2 = "http://ukplio.cafe24.com/" + profileimage.substring(3);

        this.profileimage = postURL2;

    }

    public String getCarimage() {
        return carimage;
    }

    public void setCarimage(String Carimage) {

        String postURL2 = "http://ukplio.cafe24.com/" + Carimage.substring(3);

        this.carimage = postURL2;

    }

}