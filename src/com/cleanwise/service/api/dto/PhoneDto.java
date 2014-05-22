package com.cleanwise.service.api.dto;

import java.io.Serializable;

public class PhoneDto implements Serializable {

	private int phoneId;
	private String phoneNum;

	// int busEntityId - DO NOT INCLUDE
	// int userId - DO NOT INCLUDE
	

	public PhoneDto() {}
	
	public PhoneDto(int phoneId, String phoneNum) {
		super();
		this.phoneId = phoneId;
		this.phoneNum = phoneNum;
	}
	
	public int getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
