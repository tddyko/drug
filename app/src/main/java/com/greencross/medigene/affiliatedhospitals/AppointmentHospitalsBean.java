package com.greencross.medigene.affiliatedhospitals;

/**
 * 검강검진 제휴병원 - 병원 리스트 저장 빈
 *
 */
public class AppointmentHospitalsBean {
	private String areaCode;
	private String areaCode_name;
	private String name;
	private String address;
	private String x;
	private String y;
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaCode_name() {
		return areaCode_name;
	}
	public void setAreaCode_name(String areaCode_name) {
		this.areaCode_name = areaCode_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
}
