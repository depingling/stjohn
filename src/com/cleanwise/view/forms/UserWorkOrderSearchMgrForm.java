package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.util.RefCodeNames;

import java.util.HashMap;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         14.10.2007
 * Time:         0:49:57
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderSearchMgrForm extends ActionForm {

    String workOrderNumber;
    String priority;
    String type;
    String dispatchNumber;
    String status;
    String startDate;
    String expDate;
    String searchField;
    String searchType;
    HashMap queueStatistic;

    public UserWorkOrderSearchMgrForm() {

        workOrderNumber="";
        priority="";
        type="";
        dispatchNumber="";
        status="";
        startDate="";
        expDate="";
        searchField="";
        searchType= RefCodeNames.SEARCH_TYPE_CD.BEGINS;
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

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
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
}
