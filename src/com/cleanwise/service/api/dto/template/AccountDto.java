package com.cleanwise.service.api.dto.template;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.value.BusEntityData;


public class AccountDto extends ValueObject {

	private static final long serialVersionUID = -1002725733131921419L;
	private String _name = null;
	private String _erpNumber = null;
	
	public AccountDto() {
	}
	
	public AccountDto(BusEntityData account) {
		this();
		populate(account);
	}
	
	public void populate(BusEntityData account) {
		setName(account.getShortDesc());
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getErpNumber() {
		return _erpNumber;
	}

	public void setErpNumber(String erpNumber) {
		_erpNumber = erpNumber;
	}
	
	
}
