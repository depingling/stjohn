
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EBagIn101ComponentView
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




/**
 * <code>EBagIn101ComponentView</code> is a ViewObject class for UI.
 */
public class EBagIn101ComponentView
extends ValueObject
{
   
    private static final long serialVersionUID = -75967968860430535L;
    private String mBusEntityName;
    private String mBusEntityTypeCd;
    private AddressData mPrimaryAddress;
    private String mRefNum;
    private String mArAttribute;

    /**
     * Constructor.
     */
    public EBagIn101ComponentView ()
    {
        mBusEntityName = "";
        mBusEntityTypeCd = "";
        mRefNum = "";
        mArAttribute = "";
    }

    /**
     * Constructor. 
     */
    public EBagIn101ComponentView(String parm1, String parm2, AddressData parm3, String parm4, String parm5)
    {
        mBusEntityName = parm1;
        mBusEntityTypeCd = parm2;
        mPrimaryAddress = parm3;
        mRefNum = parm4;
        mArAttribute = parm5;
        
    }

    /**
     * Creates a new EBagIn101ComponentView
     *
     * @return
     *  Newly initialized EBagIn101ComponentView object.
     */
    public static EBagIn101ComponentView createValue () 
    {
        EBagIn101ComponentView valueView = new EBagIn101ComponentView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EBagIn101ComponentView object
     */
    public String toString()
    {
        return "[" + "BusEntityName=" + mBusEntityName + ", BusEntityTypeCd=" + mBusEntityTypeCd + ", PrimaryAddress=" + mPrimaryAddress + ", RefNum=" + mRefNum + ", ArAttribute=" + mArAttribute + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EBagIn101Component");
	root.setAttribute("Id", String.valueOf(mBusEntityName));

	Element node;

        node = doc.createElement("BusEntityTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTypeCd)));
        root.appendChild(node);

        node = doc.createElement("PrimaryAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mPrimaryAddress)));
        root.appendChild(node);

        node = doc.createElement("RefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefNum)));
        root.appendChild(node);

        node = doc.createElement("ArAttribute");
        node.appendChild(doc.createTextNode(String.valueOf(mArAttribute)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EBagIn101ComponentView copy()  {
      EBagIn101ComponentView obj = new EBagIn101ComponentView();
      obj.setBusEntityName(mBusEntityName);
      obj.setBusEntityTypeCd(mBusEntityTypeCd);
      obj.setPrimaryAddress(mPrimaryAddress);
      obj.setRefNum(mRefNum);
      obj.setArAttribute(mArAttribute);
      
      return obj;
    }

    
    /**
     * Sets the BusEntityName property.
     *
     * @param pBusEntityName
     *  String to use to update the property.
     */
    public void setBusEntityName(String pBusEntityName){
        this.mBusEntityName = pBusEntityName;
    }
    /**
     * Retrieves the BusEntityName property.
     *
     * @return
     *  String containing the BusEntityName property.
     */
    public String getBusEntityName(){
        return mBusEntityName;
    }


    /**
     * Sets the BusEntityTypeCd property.
     *
     * @param pBusEntityTypeCd
     *  String to use to update the property.
     */
    public void setBusEntityTypeCd(String pBusEntityTypeCd){
        this.mBusEntityTypeCd = pBusEntityTypeCd;
    }
    /**
     * Retrieves the BusEntityTypeCd property.
     *
     * @return
     *  String containing the BusEntityTypeCd property.
     */
    public String getBusEntityTypeCd(){
        return mBusEntityTypeCd;
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


    /**
     * Sets the RefNum property.
     *
     * @param pRefNum
     *  String to use to update the property.
     */
    public void setRefNum(String pRefNum){
        this.mRefNum = pRefNum;
    }
    /**
     * Retrieves the RefNum property.
     *
     * @return
     *  String containing the RefNum property.
     */
    public String getRefNum(){
        return mRefNum;
    }


    /**
     * Sets the ArAttribute property.
     *
     * @param pArAttribute
     *  String to use to update the property.
     */
    public void setArAttribute(String pArAttribute){
        this.mArAttribute = pArAttribute;
    }
    /**
     * Retrieves the ArAttribute property.
     *
     * @return
     *  String containing the ArAttribute property.
     */
    public String getArAttribute(){
        return mArAttribute;
    }


    
}
