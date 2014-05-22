package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SiteDto implements BusEntityDto, Serializable{

	private int busEntityId;
	private String shortDesc;
	private String statusCd;
	private AddressDto addressShipping;
	private List<PropertyDto> siteProperties;
	
	public SiteDto(){}
	
	public SiteDto(int busEntityId, String shortDesc, String statusCd, AddressDto addressShipping, List<PropertyDto> siteProperties) {
		super();
		this.busEntityId = busEntityId;
		this.shortDesc = shortDesc;
		this.statusCd = statusCd;
		this.addressShipping = addressShipping;
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

	public AddressDto getAddressShipping() {
		return addressShipping;
	}

	public void setAddressShipping(AddressDto addressShipping) {
		this.addressShipping = addressShipping;
	}

	public List<PropertyDto> getSiteProperties() {
		return siteProperties;
	}

	public void setSiteProperties(List<PropertyDto> siteProperties) {
		this.siteProperties = siteProperties;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
