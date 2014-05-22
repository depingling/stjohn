package com.cleanwise.service.api.dto.template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;

public class OrderDto extends ValueObject {

	private static final long serialVersionUID = -6472282596438426521L;
	private SiteDto _site = null;
	private String _orderNumber = null;
	private AccountDto _account = null;
	private String _originalOrderNumber = null;
	private Date _originalOrderDate = null;
	private String _purchaseOrderNumber = null;
	private AddressDto _shippingAddress = null;
	private AddressDto _shipFromAddress = null;
	private String _contactName = null;
	private BigDecimal _freightCost = null;
	private BigDecimal _miscCost = null;
	private BigDecimal _rushOrderCost = null;
	private BigDecimal _taxCost = null;
	private BigDecimal _subTotalCost = null;
	private BigDecimal _totalCost = null;
	private String _comments = null;
	private Date _shipDate = null;
	private DistributorDto _distributor = null;
	private String _trackingNumber = null;
	private List<OrderItemDto> _items = null;
	private Date _ruleDate = null;
	private String _placedBy = null;
	private String _orderLocale = null;
	private String _userLocale = null;
	private final int CURRENCY_DECIMAL_PLACES = -1; //indicates decimal places should be looked up
	private final String CURRENCY_FILLER = "";

	public OrderDto() {
	}
	
	public OrderDto(OrderData orderData) {
		this();
		populate(orderData);
	}
	
	public void populate(OrderData orderData) {
		SiteDto site = new SiteDto();
		site.setName(orderData.getOrderSiteName());
		setSite(site);
		setOrderNumber(orderData.getOrderNum());
		setOriginalOrderNumber(orderData.getRefOrderNum());
		setOriginalOrderDate(orderData.getOriginalOrderDate());
		setPurchaseOrderNumber(orderData.getRequestPoNum());
		if (Utility.isSet(orderData.getOrderContactName())) {
			setContactName(orderData.getOrderContactName());
		}
		else {
			setContactName(orderData.getUserFirstName() + " " + orderData.getUserLastName());
		}
		setComments(orderData.getComments());
    	setFreightCost(orderData.getTotalFreightCost());
    	setMiscCost(orderData.getTotalMiscCost());
    	setRushOrderCost(orderData.getTotalRushCharge());
    	setTaxCost(orderData.getTotalTaxCost());
    	setSubTotalCost(orderData.getTotalPrice());
    	setOrderLocale(orderData.getLocaleCd());
	}
	
	public void populateShippingAddress(OrderAddressData orderAddressData) {
		setShippingAddress(new AddressDto(orderAddressData));
	}
	
	public void populateAccount(BusEntityData account) {
		setAccount(new AccountDto(account));
	}

	public SiteDto getSite() {
		if (_site == null) {
			setSite(new SiteDto());
		}
		return _site;
	}
	
	public void setSite(SiteDto site) {
		_site = site;
	}
	
	public String getOrderNumber() {
		return _orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		_orderNumber = orderNumber;
	}
	
	public AccountDto getAccount() {
		if (_account == null) {
			setAccount(new AccountDto());
		}
		return _account;
	}
	
	public void setAccount(AccountDto account) {
		_account = account;
	}
	
	public String getOriginalOrderNumber() {
		return _originalOrderNumber;
	}
	
	public void setOriginalOrderNumber(String originalOrderNumber) {
		_originalOrderNumber = originalOrderNumber;
	}
	
	public Date getOriginalOrderDate() {
		return _originalOrderDate;
	}
	
	public void setOriginalOrderDate(Date originalOrderDate) {
		_originalOrderDate = originalOrderDate;
	}
	
	public String getPurchaseOrderNumber() {
		return _purchaseOrderNumber;
	}
	
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		_purchaseOrderNumber = purchaseOrderNumber;
	}
	
	public AddressDto getShippingAddress() {
		if (_shippingAddress == null) {
			setShippingAddress(new AddressDto());
		}
		return _shippingAddress;
	}
	
	public void setShippingAddress(AddressDto shippingAddress) {
		_shippingAddress = shippingAddress;
	}
	
	public String getContactName() {
		return _contactName;
	}
	
	public void setContactName(String contactName) {
		_contactName = contactName;
	}
	
	public String getFormattedFreightCost() {
		String returnValue = "";
		if (getFreightCost() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getFreightCost(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}
	
	public BigDecimal getFreightCost() {
		return _freightCost;
	}

	public void setFreightCost(BigDecimal freightCost) {
		_freightCost = freightCost;
	}

	public String getFormattedMiscCost() {
		String returnValue = "";
		if (getMiscCost() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getMiscCost(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}
	
	public BigDecimal getMiscCost() {
		return _miscCost;
	}

	public void setMiscCost(BigDecimal miscCost) {
		_miscCost = miscCost;
	}

	public String getFormattedRushOrderCost() {
		String returnValue = "";
		if (getRushOrderCost() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getRushOrderCost(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}

	public BigDecimal getRushOrderCost() {
		return _rushOrderCost;
	}

	public void setRushOrderCost(BigDecimal rushOrderCost) {
		_rushOrderCost = rushOrderCost;
	}

	public String getFormattedTaxCost() {
		String returnValue = "";
		if (getTaxCost() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getTaxCost(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}

	public BigDecimal getTaxCost() {
		return _taxCost;
	}

	public void setTaxCost(BigDecimal taxCost) {
		_taxCost = taxCost;
	}

	public String getFormattedSubTotalCost() {
		String returnValue = "";
		if (getSubTotalCost() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getSubTotalCost(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}

	public BigDecimal getSubTotalCost() {
		return _subTotalCost;
	}

	public void setSubTotalCost(BigDecimal subTotalCost) {
		_subTotalCost = subTotalCost;
	}

	public String getFormattedTotalCost() {
		String returnValue = "";
		if (getTotalCost() != null && Utility.isSet(getOrderLocale()) && Utility.isSet(getUserLocale())) {
			returnValue = Utility.priceFormat(getUserLocale(), getTotalCost(), getOrderLocale(), 
					CURRENCY_DECIMAL_PLACES, CURRENCY_FILLER);
		}
		return returnValue;
	}
	
	public BigDecimal getTotalCost() {
		return _totalCost;
	}
	
	public void setTotalCost(BigDecimal totalCost) {
		_totalCost = totalCost;
	}

	public String getComments() {
		return _comments;
	}
	
	public void setComments(String comments) {
		_comments = comments;
	}

	public AddressDto getShipFromAddress() {
		if (_shipFromAddress == null) {
			setShipFromAddress(new AddressDto());
		}
		return _shipFromAddress;
	}

	public void setShipFromAddress(AddressDto shipFromAddress) {
		_shipFromAddress = shipFromAddress;
	}

	public Date getShipDate() {
		return _shipDate;
	}

	public void setShipDate(Date shipDate) {
		_shipDate = shipDate;
	}

	public DistributorDto getDistributor() {
		if (_distributor == null) {
			setDistributor(new DistributorDto());
		}
		return _distributor;
	}

	public void setDistributor(DistributorDto distributor) {
		_distributor = distributor;
	}

	public String getTrackingNumber() {
		return _trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		_trackingNumber = trackingNumber;
	}

	public List<OrderItemDto> getItems() {
		if (_items == null) {
			setItems(new ArrayList<OrderItemDto>());
		}
		return _items;
	}

	public void setItems(List<OrderItemDto> items) {
		_items = items;
	}

	public Date getRuleDate() {
		return _ruleDate;
	}

	public void setRuleDate(Date ruleDate) {
		_ruleDate = ruleDate;
	}

	public String getPlacedBy() {
		return _placedBy;
	}

	public void setPlacedBy(String placedBy) {
		_placedBy = placedBy;
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
