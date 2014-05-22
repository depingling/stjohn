package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserDto implements Serializable {

	private int userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password; // Note will not be set when passed back. If a
								// value is present we will update the password
								// during save
	private long effDate;
	private long expDate;
	private String userStatusCd;
	private boolean onAccount; // This maps to the userRoleCd db field
	private boolean otherPayment; // This maps to the userRoleCd db field
	private boolean poNumberRequiered; // This maps to the userRoleCd db field
	// Note credit card defaults to false, show price defaults to true,
	// contract items only defaults to true.
	// Alternate implementation would be to make these special properties that
	// the server side would interpret.
	private String userTypeCd;
	private EmailDto emailPrimary;
	private PhoneDto phonePrimary;
	private PhoneDto phoneFax;
	private PhoneDto phoneMobile;
	private AddressDto addressPrimaryContact;
	private List<PropertyDto> userProperties;
	
	

	public UserDto() {}

	public UserDto(int userId, String firstName, String lastName, String userName, String password, long effDate, long expDate, String userStatusCd,
			boolean onAccount, boolean otherPayment, boolean poNumberRequiered, String userTypeCd, EmailDto emailPrimary, PhoneDto phonePrimary,
			PhoneDto phoneFax, PhoneDto phoneMobile, AddressDto addressPrimaryContact, List<PropertyDto> userProperties) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.effDate = effDate;
		this.expDate = expDate;
		this.userStatusCd = userStatusCd;
		this.onAccount = onAccount;
		this.otherPayment = otherPayment;
		this.poNumberRequiered = poNumberRequiered;
		this.userTypeCd = userTypeCd;
		this.emailPrimary = emailPrimary;
		this.phonePrimary = phonePrimary;
		this.phoneFax = phoneFax;
		this.phoneMobile = phoneMobile;
		this.addressPrimaryContact = addressPrimaryContact;
		this.userProperties = userProperties;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getEffDate() {
		return effDate;
	}

	public void setEffDate(long effDate) {
		this.effDate = effDate;
	}

	public long getExpDate() {
		return expDate;
	}

	public void setExpDate(long expDate) {
		this.expDate = expDate;
	}

	public String getUserStatusCd() {
		return userStatusCd;
	}

	public void setUserStatusCd(String userStatusCd) {
		this.userStatusCd = userStatusCd;
	}

	public boolean getOnAccount() {
		return onAccount;
	}

	public void setOnAccount(boolean onAccount) {
		this.onAccount = onAccount;
	}

	public boolean getOtherPayment() {
		return otherPayment;
	}

	public void setOtherPayment(boolean otherPayment) {
		this.otherPayment = otherPayment;
	}

	public boolean getPoNumberRequiered() {
		return poNumberRequiered;
	}

	public void setPoNumberRequiered(boolean poNumberRequiered) {
		this.poNumberRequiered = poNumberRequiered;
	}

	public String getUserTypeCd() {
		return userTypeCd;
	}

	public void setUserTypeCd(String userTypeCd) {
		this.userTypeCd = userTypeCd;
	}

	public EmailDto getEmailPrimary() {
		return emailPrimary;
	}

	public void setEmailPrimary(EmailDto emailPrimary) {
		this.emailPrimary = emailPrimary;
	}

	public PhoneDto getPhonePrimary() {
		return phonePrimary;
	}

	public void setPhonePrimary(PhoneDto phonePrimary) {
		this.phonePrimary = phonePrimary;
	}

	public PhoneDto getPhoneFax() {
		return phoneFax;
	}

	public void setPhoneFax(PhoneDto phoneFax) {
		this.phoneFax = phoneFax;
	}

	public PhoneDto getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(PhoneDto phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public AddressDto getAddressPrimaryContact() {
		return addressPrimaryContact;
	}

	public void setAddressPrimaryContact(AddressDto addressPrimaryContact) {
		this.addressPrimaryContact = addressPrimaryContact;
	}

	public List<PropertyDto> getUserProperties() {
		return userProperties;
	}

	public void setUserProperties(List<PropertyDto> userProperties) {
		this.userProperties = userProperties;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
