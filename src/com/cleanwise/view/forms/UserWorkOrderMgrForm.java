package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

import java.util.HashMap;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.WorkOrderViewVector;
import com.cleanwise.service.api.value.WorkOrderDataVector;
import com.cleanwise.service.api.value.WorkOrderSiteNameViewVector;
import com.cleanwise.service.api.value.WorkOrderSearchResultViewVector;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

/**
 * Title:        UserWorkOrderMgrForm
 * Description:  Form bean for the work order management in the USERPORTAL.
 * Purpose:      Holds data for the work order management.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         10.10.2007
 * Time:         16:26:52
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderMgrForm extends StorePortalBaseForm {

    String workOrderNumber;
    String priority;
    String type;
    String dispatchNumber;
    String status;
    String startDate;
    String finishDate;
    String actualBeginDate;
    String actualEndDate;
    String estimateBeginDate;
    String estimateEndDate;
    String searchField;
    String searchType;
    HashMap queueStatistic;
    String providerIdStr;
    String dateType;
    String poNumber;
    private boolean showCurrentSiteOnly;

    public boolean init = false;

    private WorkOrderSiteNameViewVector searchResult;
    
    private boolean displayDistributorAccountReferenceNumber;
    private boolean displayDistributorSiteReferenceNumber;
    
    private String distributorAccountNumber;
    private String distributorShipToLocationNumber;

    private WorkOrderSearchResultViewVector searchResultView;
    private WorkOrderSearchResultViewVector pendingSearchResultView;

	public UserWorkOrderMgrForm() {

        workOrderNumber="";
        priority="";
        type="";
        dispatchNumber="";
        status="";
        startDate="";
        finishDate="";
        actualBeginDate="";
        actualEndDate="";
        estimateBeginDate="";
        estimateEndDate="";
        searchField="";
        poNumber="";
        searchType= RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        showCurrentSiteOnly = false;
    }
    
    public void reset(ActionMapping mapping, ServletRequest request) {
        showCurrentSiteOnly = false;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        showCurrentSiteOnly = false;
    }

    public boolean getShowCurrentSiteOnly() {
        return showCurrentSiteOnly;
    }

    public void setShowCurrentSiteOnly(boolean showCurrentSiteOnly) {
        this.showCurrentSiteOnly = showCurrentSiteOnly;
    }
    
    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
    
    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDispatchNumber() {
        return dispatchNumber;
    }

    public void setDispatchNumber(String dispatchNumber) {
        this.dispatchNumber = dispatchNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }
    
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getActualBeginDate() {
        return actualBeginDate;
    }

    public void setActualBeginDate(String actualBeginDate) {
        this.actualBeginDate = actualBeginDate;
    }

    public String getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(String actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getEstimateBeginDate() {
        return estimateBeginDate;
    }

    public void setEstimateBeginDate(String estimateBeginDate) {
        this.estimateBeginDate = estimateBeginDate;
    }

    public String getEstimateEndDate() {
        return estimateEndDate;
    }

    public void setEstimateEndDate(String estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }
    
    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }


    public HashMap getQueueStatistic() {
        return queueStatistic;
    }

    public void setQueueStatistic(HashMap statistic) {
        this.queueStatistic = statistic;
    }

    public void setSearchResult(WorkOrderSiteNameViewVector searchResult) {
        this.searchResult = searchResult;
    }

    public WorkOrderSiteNameViewVector getSearchResult() {
        return searchResult;
    }

    public String getProviderIdStr() {
        return providerIdStr;
    }

    public void setProviderIdStr(String providerIdStr) {
        this.providerIdStr = providerIdStr;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType=dateType;
    }
    

	public boolean isDisplayDistributorAccountReferenceNumber() {
    	return displayDistributorAccountReferenceNumber;
    }

	public void setDisplayDistributorAccountReferenceNumber(
            boolean displayDistributorAccountReferenceNumber) {
    	this.displayDistributorAccountReferenceNumber = displayDistributorAccountReferenceNumber;
    }

	public boolean isDisplayDistributorSiteReferenceNumber() {
    	return displayDistributorSiteReferenceNumber;
    }

	public void setDisplayDistributorSiteReferenceNumber(
            boolean displayDistributorSiteReferenceNumber) {
    	this.displayDistributorSiteReferenceNumber = displayDistributorSiteReferenceNumber;
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

    public void init() {
        init = true;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        init = false;
    }

    public WorkOrderSearchResultViewVector getSearchResultView() {
        return searchResultView;
    }
    
    public void setSearchResultView(WorkOrderSearchResultViewVector searchResultView) {
        this.searchResultView = searchResultView;
    }

    public WorkOrderSearchResultViewVector getPendingSearchResultView() {
        return pendingSearchResultView;
    }

    public void setPendingSearchResultView(WorkOrderSearchResultViewVector pendingSearchResultView) {
        this.pendingSearchResultView = pendingSearchResultView;
    }
    
}
