package com.cleanwise.service.api.dto.template;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.value.OrderAddressData;

public class AddressDto extends ValueObject {

	private static final long serialVersionUID = -3473183629123085091L;
	private String _street1 = null;
	private String _street2 = null;
	private String _street3 = null;
	private String _street4 = null;
	private String _city = null;
	private String _state = null;
	private String _country = null;
	private String _postalCode = null;
	
	public AddressDto() {
	}
	
	public AddressDto(OrderAddressData addressData) {
		this();
		populate(addressData);
	}
	
	public void populate(OrderAddressData addressData) {
		setStreet1(addressData.getAddress1());
		setStreet2(addressData.getAddress2());
		setStreet3(addressData.getAddress3());
		setStreet4(addressData.getAddress4());
		setCity(addressData.getCity());
		setState(addressData.getStateProvinceCd());
		setCountry(addressData.getCountryCd());
		setPostalCode(addressData.getPostalCode());
	}
	
	public String getStreet1() {
		return _street1;
	}
	
	public void setStreet1(String street1) {
		_street1 = street1;
	}
	
	public String getStreet2() {
		return _street2;
	}

	public void setStreet2(String street2) {
		_street2 = street2;
	}

	public String getStreet3() {
		return _street3;
	}

	public void setStreet3(String street3) {
		_street3 = street3;
	}

	public String getStreet4() {
		return _street4;
	}

	public void setStreet4(String street4) {
		_street4 = street4;
	}

	public String getCity() {
		return _city;
	}
	
	public void setCity(String city) {
		_city = city;
	}
	
	public String getState() {
		return _state;
	}
	
	public void setState(String state) {
		_state = state;
	}
	
	public String getCountry() {
		return _country;
	}

	public void setCountry(String country) {
		_country = country;
	}

	public String getPostalCode() {
		return _postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}
	
}
