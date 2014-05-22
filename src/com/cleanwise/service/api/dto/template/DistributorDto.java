package com.cleanwise.service.api.dto.template;

import com.cleanwise.service.api.framework.ValueObject;


public class DistributorDto extends ValueObject {

	private static final long serialVersionUID = 320512030757041882L;
	private String _name = null;
	
	public DistributorDto() {
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}
	
	
}
