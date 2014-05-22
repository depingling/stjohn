package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AccountDto implements BusEntityDto, Serializable {

	private int busEntityId;
	private String shortDesc, statusCd;;
	private PhoneDto phoneOrder, phoneFax, phonePrimaryContact, phonePrimaryContactFax;
	private EmailDto emailPrimaryContact, emailDefault, emailContactUsCC, emailCustomerService;
	private AddressDto addressPrimary, addressBilling;
	private List<PropertyDto> siteProperties;
	
	public AccountDto(){
		
	}

	public AccountDto(int busEntityId, String shortDesc, String statusCd, PhoneDto phoneOrder, PhoneDto phoneFax, PhoneDto phonePrimaryContact,
			PhoneDto phonePrimaryContactFax, EmailDto emailPrimaryContact, EmailDto emailDefault, EmailDto emailContactUsCC, EmailDto emailCustomerService,
			AddressDto addressPrimary, AddressDto addressBilling, List<PropertyDto> siteProperties) {
		super();
		this.busEntityId = busEntityId;
		this.shortDesc = shortDesc;
		this.statusCd = statusCd;
		this.phoneOrder = phoneOrder;
		this.phoneFax = phoneFax;
		this.phonePrimaryContact = phonePrimaryContact;
		this.phonePrimaryContactFax = phonePrimaryContactFax;
		this.emailPrimaryContact = emailPrimaryContact;
		this.emailDefault = emailDefault;
		this.emailContactUsCC = emailContactUsCC;
		this.emailCustomerService = emailCustomerService;
		this.addressPrimary = addressPrimary;
		this.addressBilling = addressBilling;
		this.siteProperties = siteProperties;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public int getBusEntityId() {
		return busEntityId;
	}

	public void setBusEntityId(int busEntityId) {
		this.busEntityId = busEntityId;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public PhoneDto getPhoneOrder() {
		return phoneOrder;
	}

	public void setPhoneOrder(PhoneDto phoneOrder) {
		this.phoneOrder = phoneOrder;
	}

	public PhoneDto getPhoneFax() {
		return phoneFax;
	}

	public void setPhoneFax(PhoneDto phoneFax) {
		this.phoneFax = phoneFax;
	}

	public PhoneDto getPhonePrimaryContact() {
		return phonePrimaryContact;
	}

	public void setPhonePrimaryContact(PhoneDto phonePrimaryContact) {
		this.phonePrimaryContact = phonePrimaryContact;
	}

	public PhoneDto getPhonePrimaryContactFax() {
		return phonePrimaryContactFax;
	}

	public void setPhonePrimaryContactFax(PhoneDto phonePrimaryContactFax) {
		this.phonePrimaryContactFax = phonePrimaryContactFax;
	}

	public EmailDto getEmailPrimaryContact() {
		return emailPrimaryContact;
	}

	public void setEmailPrimaryContact(EmailDto emailPrimaryContact) {
		this.emailPrimaryContact = emailPrimaryContact;
	}

	public EmailDto getEmailDefault() {
		return emailDefault;
	}

	public void setEmailDefault(EmailDto emailDefault) {
		this.emailDefault = emailDefault;
	}

	public EmailDto getEmailContactUsCC() {
		return emailContactUsCC;
	}

	public void setEmailContactUsCC(EmailDto emailContactUsCC) {
		this.emailContactUsCC = emailContactUsCC;
	}

	public EmailDto getEmailCustomerService() {
		return emailCustomerService;
	}

	public void setEmailCustomerService(EmailDto emailCustomerService) {
		this.emailCustomerService = emailCustomerService;
	}

	public AddressDto getAddressPrimary() {
		return addressPrimary;
	}

	public void setAddressPrimary(AddressDto addressPrimary) {
		this.addressPrimary = addressPrimary;
	}

	public AddressDto getAddressBilling() {
		return addressBilling;
	}

	public void setAddressBilling(AddressDto addressBilling) {
		this.addressBilling = addressBilling;
	}

	public List<PropertyDto> getSiteProperties() {
		return siteProperties;
	}

	public void setSiteProperties(List<PropertyDto> siteProperties) {
		this.siteProperties = siteProperties;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
