package com.cleanwise.service.api.dto.template;

import com.cleanwise.service.api.framework.ValueObject;


public class SiteDto extends ValueObject {

	private static final long serialVersionUID = -7615720351725472663L;
	private String _name = null;
	
	public SiteDto() {
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}
	
	
}
