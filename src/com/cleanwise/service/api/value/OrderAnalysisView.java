package com.cleanwise.service.api.value;

/**
 * Title:        OrderAnalysisView
 * Description:  Value object extension for marshalling order analysis data.
 * Purpose:      Obtains and marshals order analysis information among 
 *               session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;

import org.w3c.dom.*;

/**
 * <code>OrderAnalysisView</code> is a value object representing aggregated
 * order analysis data.
 */
public class OrderAnalysisView extends ValueObject
{
    String accountName;
    int accountId;
    int siteId;
    String state;
    String postalCode;
    Date beginDate;
    Date endDate;
    int totalOrders;
    int totalOnlineOrders;
    int totalBackorderOrders;
    int totalFreightChargeOrders;
    java.math.BigDecimal totalOrderAmt;
    java.math.BigDecimal totalOrderAmtCategory1;
    java.math.BigDecimal totalOrderAmtCategory2;
    java.math.BigDecimal totalOrderAmtCategory3;
    java.math.BigDecimal totalOrderAmtCategory4;
    java.math.BigDecimal totalOrderFreightAmt;
    int highDaysOrderShipNoBackorder;
    int lowDaysOrderShipNoBackorder;
    double avgDaysOrderShipNoBackorder;
    int highDaysOrderShipBackorder;
    int lowDaysOrderShipBackorder;
    double avgDaysOrderShipBackorder;
    
    int countOrderType;
    String orderType;
    

    private OrderAnalysisView() {
	accountName = "";
	state = "";
	postalCode = "";
    }

    /**
     * Creates a new OrderAnalysisView
     *
     * @return
     *  Newly initialized OrderAnalysisView object.
     */
    public static OrderAnalysisView createValue () 
    {
        OrderAnalysisView valueData = new OrderAnalysisView();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderAnalysisView object
     */
    public String toString()
    {
	java.text.SimpleDateFormat dtf = 
	    new java.text.SimpleDateFormat("MM/dd/yyyy");

	java.text.DecimalFormat dcf =
	    new java.text.DecimalFormat("#0.00");
/*
	return accountName + "," 
	    + accountId + "," 
	    + siteId + ","
	    + state + ","
	    + postalCode + ","
	    + dtf.format(beginDate) + ","
	    + dtf.format(endDate) + ","
	    + totalOrders + ","
	    + totalOnlineOrders + ","
	    + totalBackorderOrders + ","
	    + totalFreightChargeOrders + ","
	    + dcf.format(totalOrderAmt.floatValue()) + ","
	    + dcf.format(totalOrderAmtCategory1.floatValue()) + ","
	    + dcf.format(totalOrderAmtCategory2.floatValue()) + ","
	    + dcf.format(totalOrderAmtCategory3.floatValue()) + ","
	    + dcf.format(totalOrderAmtCategory4.floatValue()) + ","
	    + dcf.format(totalOrderFreightAmt.floatValue()) + ","
	    + highDaysOrderShipNoBackorder + ","
	    + lowDaysOrderShipNoBackorder + ","
	    + avgDaysOrderShipNoBackorder + ","
	    + highDaysOrderShipBackorder + ","
	    + lowDaysOrderShipBackorder + ","
	    + avgDaysOrderShipBackorder + ","
            + countOrderType + ","
            + orderType + ",";
    }
    */
    return accountName + "," 
	    + accountId + "," 
	    + dtf.format(beginDate) + ","
	    + dtf.format(endDate) + ","
	    + countOrderType + ","
            + orderType;
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
	java.text.SimpleDateFormat df = 
	    new java.text.SimpleDateFormat("MM/dd/yyyy");

        Element root = doc.createElement("OrderAnalysis");

	Element node;

	node = doc.createElement("AccountName");
	node.appendChild(doc.createTextNode(String.valueOf(accountName)));
	root.appendChild(node);

	node = doc.createElement("AccountId");
	node.appendChild(doc.createTextNode(String.valueOf(accountId)));
	root.appendChild(node);

	node = doc.createElement("SiteId");
	node.appendChild(doc.createTextNode(String.valueOf(siteId)));
	root.appendChild(node);

	node = doc.createElement("State");
	node.appendChild(doc.createTextNode(String.valueOf(state)));
	root.appendChild(node);

	node = doc.createElement("PostalCode");
	node.appendChild(doc.createTextNode(String.valueOf(postalCode)));
	root.appendChild(node);

	node = doc.createElement("BeginDate");
	node.appendChild(doc.createTextNode(df.format(beginDate)));
	root.appendChild(node);

	node = doc.createElement("EndDate");
	node.appendChild(doc.createTextNode(df.format(endDate)));
	root.appendChild(node);

	node = doc.createElement("TotalOrders");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrders)));
	root.appendChild(node);

	node = doc.createElement("TotalOnlineOrders");
	node.appendChild(doc.createTextNode(String.valueOf(totalOnlineOrders)));
	root.appendChild(node);

	node = doc.createElement("TotalBackorderOrders");
	node.appendChild(doc.createTextNode(String.valueOf(totalBackorderOrders)));
	root.appendChild(node);

	node = doc.createElement("TotalFreightChargeOrders");
	node.appendChild(doc.createTextNode(String.valueOf(totalFreightChargeOrders)));
	root.appendChild(node);

	node = doc.createElement("TotalOrderAmt");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrderAmt)));
	root.appendChild(node);

	node = doc.createElement("TotalOrderAmtCategory1");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrderAmtCategory1)));
	root.appendChild(node);

	node = doc.createElement("TotalOrderAmtCategory2");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrderAmtCategory2)));
	root.appendChild(node);

	node = doc.createElement("TotalOrderAmtCategory3");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrderAmtCategory3)));
	root.appendChild(node);

	node = doc.createElement("TotalOrderAmtCategory4");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrderAmtCategory4)));
	root.appendChild(node);

	node = doc.createElement("TotalOrderFreightAmt");
	node.appendChild(doc.createTextNode(String.valueOf(totalOrderFreightAmt)));
	root.appendChild(node);

	node = doc.createElement("HighDaysOrderShipNoBackorder");
	node.appendChild(doc.createTextNode(String.valueOf(highDaysOrderShipNoBackorder)));
	root.appendChild(node);

	node = doc.createElement("LowDaysOrderShipNoBackorder");
	node.appendChild(doc.createTextNode(String.valueOf(lowDaysOrderShipNoBackorder)));
	root.appendChild(node);

	node = doc.createElement("AvgDaysOrderShipNoBackorder");
	node.appendChild(doc.createTextNode(String.valueOf(avgDaysOrderShipNoBackorder)));
	root.appendChild(node);

	node = doc.createElement("HighDaysOrderShipBackorder");
	node.appendChild(doc.createTextNode(String.valueOf(highDaysOrderShipBackorder)));
	root.appendChild(node);

	node = doc.createElement("LowDaysOrderShipBackorder");
	node.appendChild(doc.createTextNode(String.valueOf(lowDaysOrderShipBackorder)));
	root.appendChild(node);

	node = doc.createElement("AvgDaysOrderShipBackorder");
	node.appendChild(doc.createTextNode(String.valueOf(avgDaysOrderShipBackorder)));
	root.appendChild(node);
        
        node = doc.createElement("CountOrderType");
	node.appendChild(doc.createTextNode(String.valueOf(countOrderType)));
	root.appendChild(node);
        
        node = doc.createElement("OrderType");
	node.appendChild(doc.createTextNode(String.valueOf(orderType)));
	root.appendChild(node);

        return root;
    }
    
    /**
     * Gets the value of accountName
     *
     * @return the value of accountName
     */
    public String getAccountName() {
	return this.accountName;
    }

    /**
     * Sets the value of accountName
     *
     * @param argAccountName Value to assign to this.accountName
     */
    public void setAccountName(String argAccountName){
	this.accountName = argAccountName;
    }

    /**
     * Gets the value of accountId
     *
     * @return the value of accountId
     */
    public int getAccountId() {
	return this.accountId;
    }

    /**
     * Sets the value of accountId
     *
     * @param argAccountId Value to assign to this.accountId
     */
    public void setAccountId(int argAccountId){
	this.accountId = argAccountId;
    }

    /**
     * Gets the value of siteId
     *
     * @return the value of siteId
     */
    public int getSiteId() {
	return this.siteId;
    }

    /**
     * Sets the value of siteId
     *
     * @param argSiteId Value to assign to this.siteId
     */
    public void setSiteId(int argSiteId){
	this.siteId = argSiteId;
    }

    /**
     * Gets the value of state
     *
     * @return the value of state
     */
    public String getState() {
	return this.state;
    }

    /**
     * Sets the value of state
     *
     * @param argState Value to assign to this.state
     */
    public void setState(String argState){
	this.state = argState;
    }

    /**
     * Gets the value of postalCode
     *
     * @return the value of postalCode
     */
    public String getPostalCode() {
	return this.postalCode;
    }

    /**
     * Sets the value of postalCode
     *
     * @param argPostalCode Value to assign to this.postalCode
     */
    public void setPostalCode(String argPostalCode){
	this.postalCode = argPostalCode;
    }

    /**
     * Gets the value of beginDate
     *
     * @return the value of beginDate
     */
    public Date getBeginDate() {
	return this.beginDate;
    }

    /**
     * Sets the value of beginDate
     *
     * @param argBeginDate Value to assign to this.beginDate
     */
    public void setBeginDate(Date argBeginDate){
	this.beginDate = argBeginDate;
    }

    /**
     * Gets the value of endDate
     *
     * @return the value of endDate
     */
    public Date getEndDate() {
	return this.endDate;
    }

    /**
     * Sets the value of endDate
     *
     * @param argEndDate Value to assign to this.endDate
     */
    public void setEndDate(Date argEndDate){
	this.endDate = argEndDate;
    }

    /**
     * Gets the value of totalOrders
     *
     * @return the value of totalOrders
     */
    public int getTotalOrders() {
	return this.totalOrders;
    }

    /**
     * Sets the value of totalOrders
     *
     * @param argTotalOrders Value to assign to this.totalOrders
     */
    public void setTotalOrders(int argTotalOrders){
	this.totalOrders = argTotalOrders;
    }

    /**
     * Gets the value of totalOnlineOrders
     *
     * @return the value of totalOnlineOrders
     */
    public int getTotalOnlineOrders() {
	return this.totalOnlineOrders;
    }

    /**
     * Sets the value of totalOnlineOrders
     *
     * @param argTotalOnlineOrders Value to assign to this.totalOnlineOrders
     */
    public void setTotalOnlineOrders(int argTotalOnlineOrders){
	this.totalOnlineOrders = argTotalOnlineOrders;
    }

    /**
     * Gets the value of totalBackorderOrders
     *
     * @return the value of totalBackorderOrders
     */
    public int getTotalBackorderOrders() {
	return this.totalBackorderOrders;
    }

    /**
     * Sets the value of totalBackorderOrders
     *
     * @param argTotalBackorderOrders Value to assign to this.totalBackorderOrders
     */
    public void setTotalBackorderOrders(int argTotalBackorderOrders){
	this.totalBackorderOrders = argTotalBackorderOrders;
    }

    /**
     * Gets the value of totalFreightChargeOrders
     *
     * @return the value of totalFreightChargeOrders
     */
    public int getTotalFreightChargeOrders() {
	return this.totalFreightChargeOrders;
    }

    /**
     * Sets the value of totalFreightChargeOrders
     *
     * @param argTotalFreightChargeOrders Value to assign to this.totalFreightChargeOrders
     */
    public void setTotalFreightChargeOrders(int argTotalFreightChargeOrders){
	this.totalFreightChargeOrders = argTotalFreightChargeOrders;
    }

    /**
     * Gets the value of totalOrderAmt
     *
     * @return the value of totalOrderAmt
     */
    public java.math.BigDecimal getTotalOrderAmt() {
	return this.totalOrderAmt;
    }

    /**
     * Sets the value of totalOrderAmt
     *
     * @param argTotalOrderAmt Value to assign to this.totalOrderAmt
     */
    public void setTotalOrderAmt(java.math.BigDecimal argTotalOrderAmt){
	this.totalOrderAmt = argTotalOrderAmt;
    }

    /**
     * Gets the value of totalOrderAmtCategory1
     *
     * @return the value of totalOrderAmtCategory1
     */
    public java.math.BigDecimal getTotalOrderAmtCategory1() {
	return this.totalOrderAmtCategory1;
    }

    /**
     * Sets the value of totalOrderAmtCategory1
     *
     * @param argTotalOrderAmtCategory1 Value to assign to this.totalOrderAmtCategory1
     */
    public void setTotalOrderAmtCategory1(java.math.BigDecimal argTotalOrderAmtCategory1){
	this.totalOrderAmtCategory1 = argTotalOrderAmtCategory1;
    }

    /**
     * Gets the value of totalOrderAmtCategory2
     *
     * @return the value of totalOrderAmtCategory2
     */
    public java.math.BigDecimal getTotalOrderAmtCategory2() {
	return this.totalOrderAmtCategory2;
    }

    /**
     * Sets the value of totalOrderAmtCategory2
     *
     * @param argTotalOrderAmtCategory2 Value to assign to this.totalOrderAmtCategory2
     */
    public void setTotalOrderAmtCategory2(java.math.BigDecimal argTotalOrderAmtCategory2){
	this.totalOrderAmtCategory2 = argTotalOrderAmtCategory2;
    }

    /**
     * Gets the value of totalOrderAmtCategory3
     *
     * @return the value of totalOrderAmtCategory3
     */
    public java.math.BigDecimal getTotalOrderAmtCategory3() {
	return this.totalOrderAmtCategory3;
    }

    /**
     * Sets the value of totalOrderAmtCategory3
     *
     * @param argTotalOrderAmtCategory3 Value to assign to this.totalOrderAmtCategory3
     */
    public void setTotalOrderAmtCategory3(java.math.BigDecimal argTotalOrderAmtCategory3){
	this.totalOrderAmtCategory3 = argTotalOrderAmtCategory3;
    }

    /**
     * Gets the value of totalOrderAmtCategory4
     *
     * @return the value of totalOrderAmtCategory4
     */
    public java.math.BigDecimal getTotalOrderAmtCategory4() {
	return this.totalOrderAmtCategory4;
    }

    /**
     * Sets the value of totalOrderAmtCategory4
     *
     * @param argTotalOrderAmtCategory4 Value to assign to this.totalOrderAmtCategory4
     */
    public void setTotalOrderAmtCategory4(java.math.BigDecimal argTotalOrderAmtCategory4){
	this.totalOrderAmtCategory4 = argTotalOrderAmtCategory4;
    }

    /**
     * Gets the value of totalOrderFreightAmt
     *
     * @return the value of totalOrderFreightAmt
     */
    public java.math.BigDecimal getTotalOrderFreightAmt() {
	return this.totalOrderFreightAmt;
    }

    /**
     * Sets the value of totalOrderFreightAmt
     *
     * @param argTotalOrderFreightAmt Value to assign to this.totalOrderFreightAmt
     */
    public void setTotalOrderFreightAmt(java.math.BigDecimal argTotalOrderFreightAmt){
	this.totalOrderFreightAmt = argTotalOrderFreightAmt;
    }

    /**
     * Gets the value of highDaysOrderShipNoBackorder
     *
     * @return the value of highDaysOrderShipNoBackorder
     */
    public int getHighDaysOrderShipNoBackorder() {
	return this.highDaysOrderShipNoBackorder;
    }

    /**
     * Sets the value of highDaysOrderShipNoBackorder
     *
     * @param argHighDaysOrderShipNoBackorder Value to assign to this.highDaysOrderShipNoBackorder
     */
    public void setHighDaysOrderShipNoBackorder(int argHighDaysOrderShipNoBackorder){
	this.highDaysOrderShipNoBackorder = argHighDaysOrderShipNoBackorder;
    }

    /**
     * Gets the value of lowDaysOrderShipNoBackorder
     *
     * @return the value of lowDaysOrderShipNoBackorder
     */
    public int getLowDaysOrderShipNoBackorder() {
	return this.lowDaysOrderShipNoBackorder;
    }

    /**
     * Sets the value of lowDaysOrderShipNoBackorder
     *
     * @param argLowDaysOrderShipNoBackorder Value to assign to this.lowDaysOrderShipNoBackorder
     */
    public void setLowDaysOrderShipNoBackorder(int argLowDaysOrderShipNoBackorder){
	this.lowDaysOrderShipNoBackorder = argLowDaysOrderShipNoBackorder;
    }

    /**
     * Gets the value of avgDaysOrderShipNoBackorder
     *
     * @return the value of avgDaysOrderShipNoBackorder
     */
    public double getAvgDaysOrderShipNoBackorder() {
	return this.avgDaysOrderShipNoBackorder;
    }

    /**
     * Sets the value of avgDaysOrderShipNoBackorder
     *
     * @param argAvgDaysOrderShipNoBackorder Value to assign to this.avgDaysOrderShipNoBackorder
     */
    public void setAvgDaysOrderShipNoBackorder(double argAvgDaysOrderShipNoBackorder){
	this.avgDaysOrderShipNoBackorder = argAvgDaysOrderShipNoBackorder;
    }

    /**
     * Gets the value of highDaysOrderShipBackorder
     *
     * @return the value of highDaysOrderShipBackorder
     */
    public int getHighDaysOrderShipBackorder() {
	return this.highDaysOrderShipBackorder;
    }

    /**
     * Sets the value of highDaysOrderShipBackorder
     *
     * @param argHighDaysOrderShipBackorder Value to assign to this.highDaysOrderShipBackorder
     */
    public void setHighDaysOrderShipBackorder(int argHighDaysOrderShipBackorder){
	this.highDaysOrderShipBackorder = argHighDaysOrderShipBackorder;
    }

    /**
     * Gets the value of lowDaysOrderShipBackorder
     *
     * @return the value of lowDaysOrderShipBackorder
     */
    public int getLowDaysOrderShipBackorder() {
	return this.lowDaysOrderShipBackorder;
    }

    /**
     * Sets the value of lowDaysOrderShipBackorder
     *
     * @param argLowDaysOrderShipBackorder Value to assign to this.lowDaysOrderShipBackorder
     */
    public void setLowDaysOrderShipBackorder(int argLowDaysOrderShipBackorder){
	this.lowDaysOrderShipBackorder = argLowDaysOrderShipBackorder;
    }

    /**
     * Gets the value of avgDaysOrderShipBackorder
     *
     * @return the value of avgDaysOrderShipBackorder
     */
    public double getAvgDaysOrderShipBackorder() {
	return this.avgDaysOrderShipBackorder;
    }

    /**
     * Sets the value of avgDaysOrderShipBackorder
     *
     * @param argAvgDaysOrderShipBackorder Value to assign to this.avgDaysOrderShipBackorder
     */
    public void setAvgDaysOrderShipBackorder(double argAvgDaysOrderShipBackorder){
	this.avgDaysOrderShipBackorder = argAvgDaysOrderShipBackorder;
    }
    
     /**
     * Gets the value of countOrderType
     *
     * @return the value of countOrderType
     */
    public int getCountOrderType() {
	return this.countOrderType;
    }

    /**
     * Sets the value of countOrderType
     *
     * @param argCountOrderType Value to assign to this.countOrderType
     */
    public void setCountOrderType(int argCountOrderType){
	this.countOrderType = argCountOrderType;
    }
    
     /**
     * Gets the value of orderType
     *
     * @return the value of orderType
     */
    public String getOrderType() {
	return this.orderType;
    }

    /**
     * Sets the value of orderType
     *
     * @param argOrderType Value to assign to this.orderType
     */
    public void setOrderType(String argOrderType){
	this.orderType = argOrderType;
    }
}
