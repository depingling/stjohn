package com.cleanwise.service.api.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ManufacturerDto implements BusEntityDto, Serializable {

	private String shortDesc, statusCd;
	private int busEntityId;
	
	public ManufacturerDto() {
		super();
	}

	public ManufacturerDto(String shortDesc, String statusCd) {
		super();
		this.shortDesc = shortDesc;
		this.statusCd = statusCd;
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
