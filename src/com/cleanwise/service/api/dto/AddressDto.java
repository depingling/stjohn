package com.cleanwise.service.api.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AddressDto implements Serializable {

	private int addressId;
	//  int busEnityId - DO NOT INCLUDE
	//  int userId - DO NOT INCLUDE 
	private String name1, 
				   name2, 
				   address1, 
				   address2, 
				   address3, 
				   address4, 
				   city, 
				   stateProvinceCd, 
				   countryCd, 
				   postalCode, 
				   addressStatusCd;
	
	
	public AddressDto() {}

	public AddressDto(int addressId, String name1, String name2, String address1, String address2, String address3, String address4, String city,
			String stateProvinceCd, String countryCd, String postalCode, String addressStatusCd) {
		super();
		this.addressId = addressId;
		this.name1 = name1;
		this.name2 = name2;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.address4 = address4;
		this.city = city;
		this.stateProvinceCd = stateProvinceCd;
		this.countryCd = countryCd;
		this.postalCode = postalCode;
		this.addressStatusCd = addressStatusCd;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvinceCd() {
		return stateProvinceCd;
	}

	public void setStateProvinceCd(String stateProvinceCd) {
		this.stateProvinceCd = stateProvinceCd;
	}

	public String getCountryCd() {
		return countryCd;
	}

	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddressStatusCd() {
		return addressStatusCd;
	}

	public void setAddressStatusCd(String addressStatusCd) {
		this.addressStatusCd = addressStatusCd;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
