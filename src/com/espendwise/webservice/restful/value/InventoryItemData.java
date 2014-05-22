package com.espendwise.webservice.restful.value;

import java.io.Serializable;
import java.util.Date;

public class InventoryItemData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer itemId;
    private String itemUrl;
    private String itemDateTime;
    private boolean submitted = false;
	private Integer quantity = 0;

	public Integer getItemId() {
		return itemId;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public String getItemDateTime() {
		return itemDateTime;
	}
	public void setItemDateTime(String itemDateTime) {
		this.itemDateTime = itemDateTime;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
