
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FreightHandlerView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import com.cleanwise.service.api.value.*;


/**
 * <code>FreightHandlerView</code> is a ViewObject class for UI.
 */
public class FreightHandlerView
extends ValueObject
{
   
    private static final long serialVersionUID = -7672562289488304818L;
    private String mEdiRoutingCd;
    private String mAcceptFreightOnInvoice;
    private int mStoreId;
    private BusEntityData mBusEntityData;
    private AddressData mPrimaryAddress;

    /**
     * Constructor.
     */
    public FreightHandlerView ()
    {
        mEdiRoutingCd = "";
        mAcceptFreightOnInvoice = "";
    }

    /**
     * Constructor. 
     */
    public FreightHandlerView(String parm1, String parm2, int parm3, BusEntityData parm4, AddressData parm5)
    {
        mEdiRoutingCd = parm1;
        mAcceptFreightOnInvoice = parm2;
        mStoreId = parm3;
        mBusEntityData = parm4;
        mPrimaryAddress = parm5;
        
    }

    /**
     * Creates a new FreightHandlerView
     *
     * @return
     *  Newly initialized FreightHandlerView object.
     */
    public static FreightHandlerView createValue () 
    {
        FreightHandlerView valueView = new FreightHandlerView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FreightHandlerView object
     */
    public String toString()
    {
        return "[" + "EdiRoutingCd=" + mEdiRoutingCd + ", AcceptFreightOnInvoice=" + mAcceptFreightOnInvoice + ", StoreId=" + mStoreId + ", BusEntityData=" + mBusEntityData + ", PrimaryAddress=" + mPrimaryAddress + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("FreightHandler");
	root.setAttribute("Id", String.valueOf(mEdiRoutingCd));

	Element node;

        node = doc.createElement("AcceptFreightOnInvoice");
        node.appendChild(doc.createTextNode(String.valueOf(mAcceptFreightOnInvoice)));
        root.appendChild(node);

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("BusEntityData");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityData)));
        root.appendChild(node);

        node = doc.createElement("PrimaryAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mPrimaryAddress)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public FreightHandlerView copy()  {
      FreightHandlerView obj = new FreightHandlerView();
      obj.setEdiRoutingCd(mEdiRoutingCd);
      obj.setAcceptFreightOnInvoice(mAcceptFreightOnInvoice);
      obj.setStoreId(mStoreId);
      obj.setBusEntityData(mBusEntityData);
      obj.setPrimaryAddress(mPrimaryAddress);
      
      return obj;
    }

    
    /**
     * Sets the EdiRoutingCd property.
     *
     * @param pEdiRoutingCd
     *  String to use to update the property.
     */
    public void setEdiRoutingCd(String pEdiRoutingCd){
        this.mEdiRoutingCd = pEdiRoutingCd;
    }
    /**
     * Retrieves the EdiRoutingCd property.
     *
     * @return
     *  String containing the EdiRoutingCd property.
     */
    public String getEdiRoutingCd(){
        return mEdiRoutingCd;
    }


    /**
     * Sets the AcceptFreightOnInvoice property.
     *
     * @param pAcceptFreightOnInvoice
     *  String to use to update the property.
     */
    public void setAcceptFreightOnInvoice(String pAcceptFreightOnInvoice){
        this.mAcceptFreightOnInvoice = pAcceptFreightOnInvoice;
    }
    /**
     * Retrieves the AcceptFreightOnInvoice property.
     *
     * @return
     *  String containing the AcceptFreightOnInvoice property.
     */
    public String getAcceptFreightOnInvoice(){
        return mAcceptFreightOnInvoice;
    }


    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the BusEntityData property.
     *
     * @param pBusEntityData
     *  BusEntityData to use to update the property.
     */
    public void setBusEntityData(BusEntityData pBusEntityData){
        this.mBusEntityData = pBusEntityData;
    }
    /**
     * Retrieves the BusEntityData property.
     *
     * @return
     *  BusEntityData containing the BusEntityData property.
     */
    public BusEntityData getBusEntityData(){
        return mBusEntityData;
    }


    /**
     * Sets the PrimaryAddress property.
     *
     * @param pPrimaryAddress
     *  AddressData to use to update the property.
     */
    public void setPrimaryAddress(AddressData pPrimaryAddress){
        this.mPrimaryAddress = pPrimaryAddress;
    }
    /**
     * Retrieves the PrimaryAddress property.
     *
     * @return
     *  AddressData containing the PrimaryAddress property.
     */
    public AddressData getPrimaryAddress(){
        return mPrimaryAddress;
    }


    
}
