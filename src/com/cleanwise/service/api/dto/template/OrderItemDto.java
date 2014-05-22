package com.cleanwise.service.api.dto.template;

import java.math.BigDecimal;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.Utility;


public class OrderItemDto extends ValueObject {

	private static final long serialVersionUID = -8973441880355436730L;
	private String _sku = null;
	private String _name = null;
	private String _lineNumber = null;
	private String _unitOfMeasure = null;
	private BigDecimal _quantity = null;
	private String _trackingNumbers = null;
	private String _pack = null;
	private BigDecimal _price = null;
	private BigDecimal _total = null;
	private String _orderLocale = null;
	private String _userLocale = null;
	private final int CURRENCY_DECIMAL_PLACES = -1; //indicates decimal places should be looked up
	private final String CURRENCY_FILLER = "";
	
	public OrderItemDto() {
	}

	public String getSku() {
		return _sku;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getLineNumber() {
		return _lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		_lineNumber = lineNumber;
	}

	public String getUnitOfMeasure() {
		return _unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		_unitOfMeasure = unitOfMeasure;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		_quantity = quantity;
	}

	public String getTrackingNumbers() {
		return _trackingNumbers;
	}

	public void setTrackingNumbers(String trackingNumbers) {
		_trackingNumbers = trackingNumbers;
	}

	public String getPack() {
		return _pack;
	}

	public void setPack(String pack) {
		_pack = pack;
	}

	public String getFormattedPrice() {
		String returnValue = "";
		if (getPrice() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getPrice(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}

	public BigDecimal getPrice() {
		return _price;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	public String getFormattedTotal() {
		String returnValue = "";
		if (getTotal() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getTotal(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}

	public BigDecimal getTotal() {
		return _total;
	}

	public void setTotal(BigDecimal total) {
		_total = total;
	}

	public String getOrderLocale() {
		return _orderLocale;
	}
	
	public void setOrderLocale(String orderLocale) {
		_orderLocale = orderLocale;
	}

	public String getUserLocale() {
		return _userLocale;
	}

	public void setUserLocale(String userLocale) {
		_userLocale = userLocale;
	}
	
}
