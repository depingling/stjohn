package com.espendwise.webservice.restful.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryLocationData extends LoginData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer locationId;
    private String locationUrl;
    private String locationDateTime;
    private boolean submitted = false;
    private List<InventoryItemData> items = new ArrayList<InventoryItemData>();

	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getLocationUrl() {
		return locationUrl;
	}
	public void setLocationUrl(String locationUrl) {
		this.locationUrl = locationUrl;
	}
	public String getLocationDateTime() {
		return locationDateTime;
	}
	public void setLocationDateTime(String locationDateTime) {
		this.locationDateTime = locationDateTime;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}


	public List<InventoryItemData> getItems() {
        return this.items;
    }

    public void setItemss(List<InventoryItemData> pItems) {
       	this.items = pItems;
    }

}
