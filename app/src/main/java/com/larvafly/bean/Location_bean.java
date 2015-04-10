package com.larvafly.bean;

public class Location_bean {

	int id;
	String location;

    public static final String idx_1 = "대연동";
    public static final String idx_2 = "경성대";
    public static final String idx_3 = "동명대";


//    수정	삭제	1	대연동
//    수정	삭제	2	경성대
//    수정	삭제	3	동명대


    public Location_bean(int id,String location){
        this.id = id;
        this.location = location;
    }


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
