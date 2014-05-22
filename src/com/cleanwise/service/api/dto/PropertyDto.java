package com.cleanwise.service.api.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PropertyDto implements Serializable {

	private int propertyId;
	// int busEntityId - DO NOT INCLUDE
	// int userId - DO NOT INCLUDE
	private String propertyTypeCd1; // - property type cd
	private String propertyTypeCd2; // - short desc
	private String value;
	private String propertyStatusCd;
	private String locale;

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyTypeCd1() {
		return propertyTypeCd1;
	}

	public void setPropertyTypeCd1(String propertyTypeCd1) {
		this.propertyTypeCd1 = propertyTypeCd1;
	}

	public String getPropertyTypeCd2() {
		return propertyTypeCd2;
	}

	public void setPropertyTypeCd2(String propertyTypeCd2) {
		this.propertyTypeCd2 = propertyTypeCd2;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPropertyStatusCd() {
		return propertyStatusCd;
	}

	public void setPropertyStatusCd(String propertyStatusCd) {
		this.propertyStatusCd = propertyStatusCd;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}
