package com.cleanwise.service.api.value;

/**
 * Title:        OrderShipAnalysisView
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
import java.io.*;

import org.w3c.dom.*;
/**
 * <code>OrderShipAnalysisView</code> is a value object representing aggregated
 * order shipment analysis data.
 */
public class OrderShipAnalysisView extends ValueObject
{
    int orderStatusId;
    String accountName;
    int accountId;
    int siteId;
    String state;
    String postalCode;
    String distributorName;
    int distributorId;
    Date orderDate;
    Date shipDate;
    boolean backorder;
    int numItemsOrdered;
    double stat1;
    double stat2;
    double stat3;

    private OrderShipAnalysisView() {
	accountName = "";
	state = "";
	postalCode = "";
	distributorName = "";
    }

    /**
     * Creates a new OrderShipAnalysisView
     *
     * @return
     *  Newly initialized OrderShipAnalysisView object.
     */
    public static OrderShipAnalysisView createValue () 
    {
        OrderShipAnalysisView valueData = new OrderShipAnalysisView();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderShipAnalysisView object
     */
    public String toString()
    {
	java.text.SimpleDateFormat dtf = 
	    new java.text.SimpleDateFormat("MM/dd/yyyy");

	java.text.DecimalFormat dcf =
	    new java.text.DecimalFormat("#0.00");

	java.text.NumberFormat pf = 
	    java.text.NumberFormat.getPercentInstance();

	StringBuffer sb = new StringBuffer();
	sb.append(orderStatusId);
	sb.append(",");
	sb.append(accountName);
	sb.append(",");
	sb.append(accountId);
	sb.append(",");
	sb.append(siteId);
	sb.append(",");
	sb.append(state);
	sb.append(",");
	sb.append(postalCode);
	sb.append(",");
	sb.append(distributorName);
	sb.append(",");
	sb.append(distributorId);
	sb.append(",");
	sb.append(dtf.format(orderDate));
	sb.append(",");
	if (shipDate != null) {
	    sb.append(dtf.format(shipDate));
	}
	sb.append(",");
	sb.append(backorder);
	sb.append(",");
	sb.append(numItemsOrdered);
	sb.append(",");
	sb.append(pf.format(stat1));
	sb.append(",");
	sb.append(pf.format(stat2));
	sb.append(",");
	sb.append(pf.format(stat3));

	return sb.toString();
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
	java.text.SimpleDateFormat df = 
	    new java.text.SimpleDateFormat("MM/dd/yyyy");

        Element root =
	    doc.createElement("OrderShipAnalysis");

	Element node;

	node = doc.createElement("OrderStatusId");
	node.appendChild(doc.createTextNode(String.valueOf(orderStatusId)));
	root.appendChild(node);

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

	node = doc.createElement("DistributorName");
	node.appendChild(doc.createTextNode(String.valueOf(distributorName)));
	root.appendChild(node);

	node = doc.createElement("DistributorId");
	node.appendChild(doc.createTextNode(String.valueOf(distributorId)));
	root.appendChild(node);

	node = doc.createElement("OrderDate");
	node.appendChild(doc.createTextNode(df.format(orderDate)));
	root.appendChild(node);

	node = doc.createElement("ShipDate");
	node.appendChild(doc.createTextNode(df.format(shipDate)));
	root.appendChild(node);

	node = doc.createElement("Backorder");
	node.appendChild(doc.createTextNode(String.valueOf(backorder)));
	root.appendChild(node);

	node = doc.createElement("NumItemsOrdered");
	node.appendChild(doc.createTextNode(String.valueOf(numItemsOrdered)));
	root.appendChild(node);

	node = doc.createElement("Stat1");
	node.appendChild(doc.createTextNode(String.valueOf(stat1)));
	root.appendChild(node);

	node = doc.createElement("Stat2");
	node.appendChild(doc.createTextNode(String.valueOf(stat2)));
	root.appendChild(node);

	node = doc.createElement("Stat3");
	node.appendChild(doc.createTextNode(String.valueOf(stat3)));
	root.appendChild(node);

        return root;
    }
    
    /**
     * Gets the value of orderStatusId
     *
     * @return the value of orderStatusId
     */
    public int getOrderStatusId() {
	return this.orderStatusId;
    }

    /**
     * Sets the value of orderStatusId
     *
     * @param argOrderStatusId Value to assign to this.orderStatusId
     */
    public void setOrderStatusId(int argOrderStatusId){
	this.orderStatusId = argOrderStatusId;
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
     * Gets the value of orderDate
     *
     * @return the value of orderDate
     */
    public Date getOrderDate() {
	return this.orderDate;
    }

    /**
     * Sets the value of orderDate
     *
     * @param argOrderDate Value to assign to this.orderDate
     */
    public void setOrderDate(Date argOrderDate){
	this.orderDate = argOrderDate;
    }

    /**
     * Gets the value of shipDate
     *
     * @return the value of shipDate
     */
    public Date getShipDate() {
	return this.shipDate;
    }

    /**
     * Sets the value of shipDate
     *
     * @param argShipDate Value to assign to this.shipDate
     */
    public void setShipDate(Date argShipDate){
	this.shipDate = argShipDate;
    }

    /**
     * Gets the value of backorder
     *
     * @return the value of backorder
     */
    public boolean getBackorder() {
	return this.backorder;
    }

    /**
     * Sets the value of backorder
     *
     * @param argBackorder Value to assign to this.backorder
     */
    public void setBackorder(boolean argBackorder){
	this.backorder = argBackorder;
    }

    /**
     * Gets the value of distributorName
     *
     * @return the value of distributorName
     */
    public String getDistributorName() {
	return this.distributorName;
    }

    /**
     * Sets the value of distributorName
     *
     * @param argDistributorName Value to assign to this.distributorName
     */
    public void setDistributorName(String argDistributorName){
	this.distributorName = argDistributorName;
    }

    /**
     * Gets the value of distributorId
     *
     * @return the value of distributorId
     */
    public int getDistributorId() {
	return this.distributorId;
    }

    /**
     * Sets the value of distributorId
     *
     * @param argDistributorId Value to assign to this.distributorId
     */
    public void setDistributorId(int argDistributorId){
	this.distributorId = argDistributorId;
    }

    /**
     * Gets the value of numItemsOrdered
     *
     * @return the value of numItemsOrdered
     */
    public int getNumItemsOrdered() {
	return this.numItemsOrdered;
    }

    /**
     * Sets the value of numItemsOrdered
     *
     * @param argNumItemsOrdered Value to assign to this.numItemsOrdered
     */
    public void setNumItemsOrdered(int argNumItemsOrdered){
	this.numItemsOrdered = argNumItemsOrdered;
    }

    /**
     * Gets the value of stat1
     *
     * @return the value of stat1
     */
    public double getStat1() {
	return this.stat1;
    }

    /**
     * Sets the value of stat1
     *
     * @param argStat1 Value to assign to this.stat1
     */
    public void setStat1(double argStat1){
	this.stat1 = argStat1;
    }

    /**
     * Gets the value of stat2
     *
     * @return the value of stat2
     */
    public double getStat2() {
	return this.stat2;
    }

    /**
     * Sets the value of stat2
     *
     * @param argStat2 Value to assign to this.stat2
     */
    public void setStat2(double argStat2){
	this.stat2 = argStat2;
    }

    /**
     * Gets the value of stat3
     *
     * @return the value of stat3
     */
    public double getStat3() {
	return this.stat3;
    }

    /**
     * Sets the value of stat3
     *
     * @param argStat3 Value to assign to this.stat3
     */
    public void setStat3(double argStat3){
	this.stat3 = argStat3;
    }

}








