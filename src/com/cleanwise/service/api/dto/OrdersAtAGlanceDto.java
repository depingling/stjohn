/**
 * 
 */
package com.cleanwise.service.api.dto;

import java.math.BigDecimal;
import java.util.Map;

public class OrdersAtAGlanceDto {
	private Map<String,BigDecimal> _categoryAmountMap;
	private String title;
	private boolean showCurrency;

	public boolean isShowCurrency() {
		return showCurrency;
	}

	public void setShowCurrency(boolean showCurrency) {
		this.showCurrency = showCurrency;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, BigDecimal> getCategoryAmountMap() {
		return _categoryAmountMap;
	}

	public void setCategoryAmountMap(Map<String, BigDecimal> categoryAmountMap) {
		_categoryAmountMap = categoryAmountMap;
	}
	

}

