package com.cleanwise.service.api.dto;

import java.io.Serializable;

public class EmailDto implements Serializable {

	private int emailId;
	private String emailAddress;

	// int busEntityId - DO NOT INCLUDE
	// int userId - DO NOT INCLUDE
	
	public EmailDto() {}
	
	public EmailDto(int emailId, String emailAddress) {
		super();
		this.emailId = emailId;
		this.emailAddress = emailAddress;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
