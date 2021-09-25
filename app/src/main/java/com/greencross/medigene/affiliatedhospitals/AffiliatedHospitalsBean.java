package com.greencross.medigene.affiliatedhospitals;

/**
 * 검강검진 제휴병원 - 병원 리스트 저장 빈
 *
 */
public class AffiliatedHospitalsBean {
	private String code;
	private String areaCode;
	private String name;
	private String special;
	private String hptCode;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getHptCode() {
		return hptCode;
	}
	public void setHptCode(String hptCode) {
		this.hptCode = hptCode;
	}
}
