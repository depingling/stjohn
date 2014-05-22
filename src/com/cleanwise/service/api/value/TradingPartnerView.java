package com.cleanwise.service.api.value;

/**
 * Title:        TradingPartnerView
 * Description:  Value object extension for marshalling trading partner data.
 * Purpose:      Liteweight value object representing a readonly view of a
 *               trading partner
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>TradingPartnerView</code> is a value object to be used to display tranding parter list
 */
public class TradingPartnerView extends ValueObject
{
    private int _id = 0;
    private String _shortDesc = "";
    private int _busEntityId = 0;
    private String _busEntityShortDesc = "";
    private String _type = "";
    private String _status = "";
    private String _traidingTypeCD = "";

    /**
     * Creates a new <code>TradingPartnerView</code> instance.
     *
     */
    private TradingPartnerView() {
    }

    /**
     * Creates a new TradingPartnerView
     *
     * @return
     *  Newly initialized TradingPartnerView object.
     */
    public static TradingPartnerView createValue ()
    {
        TradingPartnerView valueData = new TradingPartnerView();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPartnerView object
     */
    public String toString()
    {
  	StringBuffer sb = new StringBuffer();
    sb.append("id=");
    sb.append(_id);
    sb.append(", shortDesc=");
    sb.append(_shortDesc);
    sb.append(", busEntityId=");
    sb.append(_busEntityId);
    sb.append(", busEntityShortDesc=");
    sb.append(_busEntityShortDesc);
    sb.append(", type=");
    sb.append(_type);
    sb.append(", status=");
    sb.append(_status);   
    sb.append(", traidingTypeCD=");
    sb.append(_traidingTypeCD);        
   	return sb.toString();
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("TradingPartnerView");
	root.setAttribute("Id", String.valueOf(_id));

	Element node;

	node = doc.createElement("ShortDesc");
	node.appendChild(doc.createTextNode(String.valueOf(_shortDesc)));
	node.appendChild(node);

	node = doc.createElement("BusEntityId");
	node.appendChild(doc.createTextNode(String.valueOf(_busEntityId)));
	node.appendChild(node);

	node = doc.createElement("BusEntityShortDesc");
	node.appendChild(doc.createTextNode(String.valueOf(_busEntityShortDesc)));
	node.appendChild(node);

	node = doc.createElement("Type");
	node.appendChild(doc.createTextNode(String.valueOf(_type)));
	node.appendChild(node);

	node = doc.createElement("Status");
	node.appendChild(doc.createTextNode(String.valueOf(_status)));
	node.appendChild(node);
	
	node = doc.createElement("traidingTypeCD");
	node.appendChild(doc.createTextNode(String.valueOf(_traidingTypeCD)));
	node.appendChild(node);	

	return root;
    }

    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public int getId() {
	return _id;
    }

    /**
     * Sets the value of id
     *
     * @param argId Value to assign to _id
     */
    public void setId(int argId){
	_id = argId;
    }

    /**
     * Gets the value of shortDesc
     *
     * @return the value of shortDesc
     */
    public String getShortDesc() {
	return _shortDesc;
    }

    /**
     * Sets the value of shortDesc
     *
     * @param argShortDesc Value to assign to _shortDesc
     */
    public void setShortDesc(String argShortDesc){
	_shortDesc = argShortDesc;
    }

    /**
     * Gets the value of busEntityId
     *
     * @return the value of busEntityId
     */
    public int getBusEntityId() {
	return _busEntityId;
    }

    /**
     * Sets the value of busEntityId
     *
     * @param argBusEntityId Value to assign to _busEntityId
     */
    public void setBusEntityId(int argBusEntityId){
	_busEntityId = argBusEntityId;
    }

    /**
     * Gets the value of busEntityShortDesc
     *
     * @return the value of busEntityShortDesc
     */
    public String getBusEntityShortDesc() {
	return _busEntityShortDesc;
    }
    
    /**
     * Gets the value of traidingTypeCD
     *
     * @return the value of traidingTypeCD
     */
    public String gettraidingTypeCD() {
	return _traidingTypeCD;
    }    
    
    /**
     * Sets the value of busEntityShortDesc
     *
     * @param argBusEntityShortDesc Value to assign to _busEntityShortDesc
     */
    public void settraidingTypeCD(String argtraidingTypeCD){
	_traidingTypeCD = argtraidingTypeCD;
    }    
        

    /**
     * Sets the value of busEntityShortDesc
     *
     * @param argBusEntityShortDesc Value to assign to _busEntityShortDesc
     */
    public void setBusEntityShortDesc(String argBusEntityShortDesc){
	_busEntityShortDesc = argBusEntityShortDesc;
    }

    /**
     * Gets the value of type
     *
     * @return the value of type
     */
    public String getType() {
	return _type;
    }

    /**
     * Sets the value of type
     *
     * @param argType Value to assign to _type
     */
    public void setType(String argType){
	_type = argType;
    }

    /**
     * Gets the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
	return _status;
    }

    /**
     * Sets the value of status
     *
     * @param argStatus Value to assign to _status
     */
    public void setStatus(String argStatus){
	_status = argStatus;
    }

}



