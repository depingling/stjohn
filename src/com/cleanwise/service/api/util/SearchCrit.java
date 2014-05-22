package com.cleanwise.service.api.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class SearchCrit {

	protected List<SearchCritCondition> searchCritConditions;

	public SearchCrit(){
		searchCritConditions = new ArrayList<SearchCritCondition>();
	}

	public SearchCrit addCondition(SearchCritCondition condition){
		searchCritConditions.add(condition);
		return this;
	}
	
	public List<SearchCritCondition> getSearchCritConditions() {
		return searchCritConditions;
	}
	
	@Deprecated
	public void setSearchCritConditions(List<SearchCritCondition> searchCritConditions) {
		this.searchCritConditions = searchCritConditions;
	}
	

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
