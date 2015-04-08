package com.larvafly.bean;

public class Location_bean {
	
	int id;
	String location;


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
