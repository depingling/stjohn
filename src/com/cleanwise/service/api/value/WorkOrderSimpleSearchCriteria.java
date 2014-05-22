package com.cleanwise.service.api.value;

import java.util.Date;
import java.util.List;

import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Title:        WorkOrderSearchCriteria
 * Description:  search criteria.
 * Purpose:      specialized search of work order.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         14.10.2007
 * Time:         2:50:31
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class WorkOrderSimpleSearchCriteria  implements java.io.Serializable {

    private String workOrderName;
    private String searchType;
    private int workOrderId;
    private Date actualStartDate;
    private Date actualFinishDate;
    private Date estimateFinishDate;
    private Date estimateStartDate;
    private int dispatchId;
    private String priority;
    private String type;
    private String status;
    private IdVector siteIds;
    private IdVector providerIds;
    private int storeId = 0;
    private String workOrderNum;
    private String poNumber;
    private String histStatus;
    private boolean ignoreCase = true;
    private boolean spCancelCondition = false;
    private List excludeStatus;
    private int userId = 0;
    private String distributorAccountNumber;
	private String distributorShipToLocationNumber;
	private boolean userAuthorizedForAssetWOViewAllForStore = false;



    public boolean isUserAuthorizedForAssetWOViewAllForStore() {
		return userAuthorizedForAssetWOViewAllForStore;
	}

	public void setUserAuthorizedForAssetWOViewAllForStore(
			boolean userAuthorizedForAssetWOViewAllForStore) {
		this.userAuthorizedForAssetWOViewAllForStore = userAuthorizedForAssetWOViewAllForStore;
	}

	public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public void setActualStartDate(Date ActualStartDate) {
        this.actualStartDate = ActualStartDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualFinishDate(Date actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Date getActualFinishDate() {
        return actualFinishDate;
    }

    public void setDispatchId(int dispatchId) {
        this.dispatchId = dispatchId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    public int getDispatchId() {
        return dispatchId;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setSiteIds(IdVector siteIds) {
        this.siteIds = siteIds;
    }

    public IdVector getSiteIds() {
        return siteIds;
    }

    public void setProviderIds(IdVector providerIds) {
        this.providerIds = providerIds;
    }

    public IdVector getProviderIds() {
        return providerIds;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    } 
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setEstimateFinishDate(Date estimateFinishDate) {
        this.estimateFinishDate = estimateFinishDate;
    }

    public Date getEstimateFinishDate() {
        return estimateFinishDate;
    }

    public void setEstimateStartDate(Date estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public Date getEstimateStartDate() {
        return estimateStartDate;
    }
    
    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }
    
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public List getExcludeStatus() {
        return excludeStatus;
    }

    public void setExcludeStatus(List excludeStatus) {
        this.excludeStatus = excludeStatus;
    }

    public boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getHistStatus() {
        return histStatus;
    }

    public void setHistStatus(String histStatus) {
        this.histStatus = histStatus;
    }

    public boolean getSpCancelCondition() {
        return spCancelCondition;
    }

    public void setSpCancelCondition(boolean spCancelCondition) {
        this.spCancelCondition = spCancelCondition;
    }
    
    public String getDistributorAccountNumber() {
    	return distributorAccountNumber;
    }

	public void setDistributorAccountNumber(String distributorAccountNumber) {
    	this.distributorAccountNumber = distributorAccountNumber;
    }

	public String getDistributorShipToLocationNumber() {
    	return distributorShipToLocationNumber;
    }

	public void setDistributorShipToLocationNumber(
            String distributorShipToLocationNumber) {
    	this.distributorShipToLocationNumber = distributorShipToLocationNumber;
    }
}

