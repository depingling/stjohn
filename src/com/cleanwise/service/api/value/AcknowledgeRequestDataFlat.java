package com.cleanwise.service.api.value;

import java.math.BigDecimal;
import java.util.Date;

public class AcknowledgeRequestDataFlat {
	private Date ackDate;
	private String erpPoNum;
	private String siteKey;
	private String vendorOrderNum;

	
	private Date actionDate;
	private String distSkuNum;
	private String itemName;
	private BigDecimal price;
	private Integer quantity;
	
	public Date getAckDate() {
		return ackDate;
	}
	public void setAckDate(Date ackDate) {
		this.ackDate = ackDate;
	}
	public String getErpPoNum() {
		return erpPoNum;
	}
	public void setErpPoNum(String erpPoNum) {
		this.erpPoNum = erpPoNum;
	}
	public String getSiteKey() {
		return siteKey;
	}
	public void setSiteKey(String siteKey) {
		this.siteKey = siteKey;
	}
	public String getVendorOrderNum() {
		return vendorOrderNum;
	}
	public void setVendorOrderNum(String vendorOrderNum) {
		this.vendorOrderNum = vendorOrderNum;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public String getDistSkuNum() {
		return distSkuNum;
	}
	public void setDistSkuNum(String distSkuNum) {
		this.distSkuNum = distSkuNum;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
