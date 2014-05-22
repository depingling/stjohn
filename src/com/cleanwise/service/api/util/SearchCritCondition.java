package com.cleanwise.service.api.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SearchCritCondition {

	private String searchType, searchValue;
	private int searchOperator, nestedEntity;

	//search operators:
	public static final int NONE = 0;
	public static final int AND = 1;
	public static final int OR = 2;
	public static final int EQUAL = 3;
	public static final int EQUAL_IGNORE_CASE = 4;
	public static final int NOT_EQUAL = 5;
	public static final int NOT_EQUAL_IGNORE_CASE = 6;
	public static final int GREATER_THAN = 7;
	public static final int GREATER_OR_EQUAL = 8;
	public static final int LESS_THAN = 9;
	public static final int LESS_OR_EQUAL = 10;
	public static final int LIKE = 11; // ??
	public static final int LIKE_IGNORE_CASE = 12; // ??
	public static final int IS_NULL = 13;
	public static final int IS_NOT_NULL = 14;
	public static final int IN = 15;
	public static final int NOT_IN = 16;
	public static final int IN_IGNORE_CASE = 17;
	public static final int IS_NULL_OR_0 = 18;
	public static final int IS_NULL_OR_SPACE = 19;
	public static final int CONTAINS = 20;
	public static final int CONTAINS_IGNORE_CASE = 21;
	public static final int BEGINS = 22;
	public static final int BEGINS_IGNORE_CASE = 23;
	
	//nested entities:
	public static final int ADDRESS = 1,
							PHONE = 2,
							PROPERTY = 3,
							EMAIL = 4;
	
	@Deprecated
	public SearchCritCondition(){
		super();
	}

	public SearchCritCondition(String searchType, String searchValue, int searchOperator) {
		super();
		this.searchType = searchType;
		this.searchValue = searchValue;
		this.searchOperator = searchOperator;
	}
	
	public SearchCritCondition(String searchType, String searchValue, int searchOperator, int nestedEntity) {
		super();
		this.searchType = searchType;
		this.searchValue = searchValue;
		this.searchOperator = searchOperator;
		this.nestedEntity = nestedEntity;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public int getSearchOperator() {
		return searchOperator;
	}
	
	public int getNestedEntity() {
		return nestedEntity;
	}

	public void setNestedEntity(int nestedEntity) {
		this.nestedEntity = nestedEntity;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setSearchOperator(int searchOperator) {
		this.searchOperator = searchOperator;
	}
	
}

