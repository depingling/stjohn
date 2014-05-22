package com.cleanwise.service.api.value;

/**
 * Title:        SiteView
 * Description:  Value object extension for marshalling site data.
 * Purpose:      Liteweight value object representing a readonly view of a
 *               site.  e.g. useful for site display lists
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
 * <code>SiteView</code> is a value object that aggregates all the value
 * objects that make up a Site.
 */
public class SiteView extends ValueObject
{
    int id;
    String name;
    int accountId;
    String accountName;
    String address;
    String city;
    String state;
    String postalCode;
    String status;
    String County;
    int targetFacilityRank;
    
    /**
     * Get the value of targetFacilityRank.
     * @return value of targetFacilityRank.
     */
    public int getTargetFacilityRank() {
	return targetFacilityRank;
    }
    
    /**
     * Set the value of targetFacilityRank.
     * @param v  Value to assign to targetFacilityRank.
     */
    public void setTargetFacilityRank(int  v) {
	this.targetFacilityRank = v;
    }
    
    /**
     * Get the value of County.
     * @return value of County.
     */
    public String getCounty() {
	return County;
    }
    
    /**
     * Set the value of County.
     * @param v  Value to assign to County.
     */
    public void setCounty(String  v) {
	this.County = v;
    }
    
    /**
     * Creates a new <code>SiteView</code> instance.
     *
     */
    private SiteView() {
	name = "";
	accountName = "";
	address = "";
	city = "";
	state = "";
	County = "";
	postalCode = "";
	status = "";
        targetFacilityRank = 0;
    }

    /**
     * Creates a new SiteView
     *
     * @return
     *  Newly initialized SiteView object.
     */
    public static SiteView createValue () 
    {
        SiteView valueData = new SiteView();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteView object
     */
    public String toString()
    {
	StringBuffer sb = new StringBuffer();
	sb.append(id);
	sb.append(",");
	sb.append(name);
	sb.append(",");
	sb.append(accountId);
	sb.append(",");
	sb.append(accountName);
	sb.append(",");
	sb.append(address);
	sb.append(",");
	sb.append(city);
	sb.append(",");
	sb.append(state);
	sb.append(",");
	sb.append(postalCode);
	sb.append(",");
	sb.append(status);
        sb.append(",");
        sb.append(targetFacilityRank);

	return sb.toString();
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SiteView");
	root.setAttribute("Id", String.valueOf(id));

	Element node;

	node = doc.createElement("Name");
	node.appendChild(doc.createTextNode(String.valueOf(name)));
	node.appendChild(node);

	node = doc.createElement("AccountId");
	node.appendChild(doc.createTextNode(String.valueOf(accountId)));
	node.appendChild(node);

	node = doc.createElement("AccountName");
	node.appendChild(doc.createTextNode(String.valueOf(accountName)));
	node.appendChild(node);

	node = doc.createElement("Address");
	node.appendChild(doc.createTextNode(String.valueOf(address)));
	node.appendChild(node);

	node = doc.createElement("City");
	node.appendChild(doc.createTextNode(String.valueOf(city)));
	node.appendChild(node);

	node = doc.createElement("State");
	node.appendChild(doc.createTextNode(String.valueOf(state)));
	node.appendChild(node);

	node = doc.createElement("PostalCode");
	node.appendChild(doc.createTextNode(String.valueOf(postalCode)));
	node.appendChild(node);

	node = doc.createElement("Status");
	node.appendChild(doc.createTextNode(String.valueOf(status)));
	node.appendChild(node);

        node = doc.createElement("TargetFacilityRank");
	node.appendChild(doc.createTextNode(String.valueOf(targetFacilityRank)));
	node.appendChild(node);
        
	return root;
    }

    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public int getId() {
	return this.id;
    }

    /**
     * Sets the value of id
     *
     * @param argId Value to assign to this.id
     */
    public void setId(int argId){
	this.id = argId;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName() {
	return this.name;
    }

    /**
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public void setName(String argName){
	this.name = argName;
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
     * Gets the value of address
     *
     * @return the value of address
     */
    public String getAddress() {
	return this.address;
    }

    /**
     * Sets the value of address
     *
     * @param argAddress Value to assign to this.address
     */
    public void setAddress(String argAddress){
	this.address = argAddress;
    }

    /**
     * Gets the value of city
     *
     * @return the value of city
     */
    public String getCity() {
	return this.city;
    }

    /**
     * Sets the value of city
     *
     * @param argCity Value to assign to this.city
     */
    public void setCity(String argCity){
	this.city = argCity;
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
     * @param argPostalCode to assign to this.postalCode
     */
    public void setPostalCode(String argPostalCode){
	this.postalCode = argPostalCode;
    }

    /**
     * Gets the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
	return this.status;
    }

    /**
     * Sets the value of status
     *
     * @param argStatus Value to assign to this.status
     */
    public void setStatus(String argStatus){
	this.status = argStatus;
    }

}



