
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DistShipFromAddressView
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
 * <code>DistShipFromAddressView</code> is a ViewObject class for UI.
 */
public class DistShipFromAddressView
extends ValueObject
{
   
    private static final long serialVersionUID = -5000469418365303062L;
    private int mDistributorId;
    private String mDistName;
    private int mShipFromAddressId;
    private String mShipFromName;
    private String mShipFromAddress1;
    private String mShipFromAddress2;
    private String mShipFromAddress3;
    private String mShipFromCity;
    private String mShipFromState;
    private String mShipFromPostalCode;
    private String mShipFromCountryCd;
    private String mShipFromStatus;

    /**
     * Constructor.
     */
    public DistShipFromAddressView ()
    {
        mDistName = "";
        mShipFromName = "";
        mShipFromAddress1 = "";
        mShipFromAddress2 = "";
        mShipFromAddress3 = "";
        mShipFromCity = "";
        mShipFromState = "";
        mShipFromPostalCode = "";
        mShipFromCountryCd = "";
        mShipFromStatus = "";
    }

    /**
     * Constructor. 
     */
    public DistShipFromAddressView(int parm1, String parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12)
    {
        mDistributorId = parm1;
        mDistName = parm2;
        mShipFromAddressId = parm3;
        mShipFromName = parm4;
        mShipFromAddress1 = parm5;
        mShipFromAddress2 = parm6;
        mShipFromAddress3 = parm7;
        mShipFromCity = parm8;
        mShipFromState = parm9;
        mShipFromPostalCode = parm10;
        mShipFromCountryCd = parm11;
        mShipFromStatus = parm12;
        
    }

    /**
     * Creates a new DistShipFromAddressView
     *
     * @return
     *  Newly initialized DistShipFromAddressView object.
     */
    public static DistShipFromAddressView createValue () 
    {
        DistShipFromAddressView valueView = new DistShipFromAddressView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistShipFromAddressView object
     */
    public String toString()
    {
        return "[" + "DistributorId=" + mDistributorId + ", DistName=" + mDistName + ", ShipFromAddressId=" + mShipFromAddressId + ", ShipFromName=" + mShipFromName + ", ShipFromAddress1=" + mShipFromAddress1 + ", ShipFromAddress2=" + mShipFromAddress2 + ", ShipFromAddress3=" + mShipFromAddress3 + ", ShipFromCity=" + mShipFromCity + ", ShipFromState=" + mShipFromState + ", ShipFromPostalCode=" + mShipFromPostalCode + ", ShipFromCountryCd=" + mShipFromCountryCd + ", ShipFromStatus=" + mShipFromStatus + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DistShipFromAddress");
	root.setAttribute("Id", String.valueOf(mDistributorId));

	Element node;

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("ShipFromAddressId");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddressId)));
        root.appendChild(node);

        node = doc.createElement("ShipFromName");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromName)));
        root.appendChild(node);

        node = doc.createElement("ShipFromAddress1");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress1)));
        root.appendChild(node);

        node = doc.createElement("ShipFromAddress2");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress2)));
        root.appendChild(node);

        node = doc.createElement("ShipFromAddress3");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress3)));
        root.appendChild(node);

        node = doc.createElement("ShipFromCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromCity)));
        root.appendChild(node);

        node = doc.createElement("ShipFromState");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromState)));
        root.appendChild(node);

        node = doc.createElement("ShipFromPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromPostalCode)));
        root.appendChild(node);

        node = doc.createElement("ShipFromCountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromCountryCd)));
        root.appendChild(node);

        node = doc.createElement("ShipFromStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromStatus)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DistShipFromAddressView copy()  {
      DistShipFromAddressView obj = new DistShipFromAddressView();
      obj.setDistributorId(mDistributorId);
      obj.setDistName(mDistName);
      obj.setShipFromAddressId(mShipFromAddressId);
      obj.setShipFromName(mShipFromName);
      obj.setShipFromAddress1(mShipFromAddress1);
      obj.setShipFromAddress2(mShipFromAddress2);
      obj.setShipFromAddress3(mShipFromAddress3);
      obj.setShipFromCity(mShipFromCity);
      obj.setShipFromState(mShipFromState);
      obj.setShipFromPostalCode(mShipFromPostalCode);
      obj.setShipFromCountryCd(mShipFromCountryCd);
      obj.setShipFromStatus(mShipFromStatus);
      
      return obj;
    }

    
    /**
     * Sets the DistributorId property.
     *
     * @param pDistributorId
     *  int to use to update the property.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
    }
    /**
     * Retrieves the DistributorId property.
     *
     * @return
     *  int containing the DistributorId property.
     */
    public int getDistributorId(){
        return mDistributorId;
    }


    /**
     * Sets the DistName property.
     *
     * @param pDistName
     *  String to use to update the property.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
    }
    /**
     * Retrieves the DistName property.
     *
     * @return
     *  String containing the DistName property.
     */
    public String getDistName(){
        return mDistName;
    }


    /**
     * Sets the ShipFromAddressId property.
     *
     * @param pShipFromAddressId
     *  int to use to update the property.
     */
    public void setShipFromAddressId(int pShipFromAddressId){
        this.mShipFromAddressId = pShipFromAddressId;
    }
    /**
     * Retrieves the ShipFromAddressId property.
     *
     * @return
     *  int containing the ShipFromAddressId property.
     */
    public int getShipFromAddressId(){
        return mShipFromAddressId;
    }


    /**
     * Sets the ShipFromName property.
     *
     * @param pShipFromName
     *  String to use to update the property.
     */
    public void setShipFromName(String pShipFromName){
        this.mShipFromName = pShipFromName;
    }
    /**
     * Retrieves the ShipFromName property.
     *
     * @return
     *  String containing the ShipFromName property.
     */
    public String getShipFromName(){
        return mShipFromName;
    }


    /**
     * Sets the ShipFromAddress1 property.
     *
     * @param pShipFromAddress1
     *  String to use to update the property.
     */
    public void setShipFromAddress1(String pShipFromAddress1){
        this.mShipFromAddress1 = pShipFromAddress1;
    }
    /**
     * Retrieves the ShipFromAddress1 property.
     *
     * @return
     *  String containing the ShipFromAddress1 property.
     */
    public String getShipFromAddress1(){
        return mShipFromAddress1;
    }


    /**
     * Sets the ShipFromAddress2 property.
     *
     * @param pShipFromAddress2
     *  String to use to update the property.
     */
    public void setShipFromAddress2(String pShipFromAddress2){
        this.mShipFromAddress2 = pShipFromAddress2;
    }
    /**
     * Retrieves the ShipFromAddress2 property.
     *
     * @return
     *  String containing the ShipFromAddress2 property.
     */
    public String getShipFromAddress2(){
        return mShipFromAddress2;
    }


    /**
     * Sets the ShipFromAddress3 property.
     *
     * @param pShipFromAddress3
     *  String to use to update the property.
     */
    public void setShipFromAddress3(String pShipFromAddress3){
        this.mShipFromAddress3 = pShipFromAddress3;
    }
    /**
     * Retrieves the ShipFromAddress3 property.
     *
     * @return
     *  String containing the ShipFromAddress3 property.
     */
    public String getShipFromAddress3(){
        return mShipFromAddress3;
    }


    /**
     * Sets the ShipFromCity property.
     *
     * @param pShipFromCity
     *  String to use to update the property.
     */
    public void setShipFromCity(String pShipFromCity){
        this.mShipFromCity = pShipFromCity;
    }
    /**
     * Retrieves the ShipFromCity property.
     *
     * @return
     *  String containing the ShipFromCity property.
     */
    public String getShipFromCity(){
        return mShipFromCity;
    }


    /**
     * Sets the ShipFromState property.
     *
     * @param pShipFromState
     *  String to use to update the property.
     */
    public void setShipFromState(String pShipFromState){
        this.mShipFromState = pShipFromState;
    }
    /**
     * Retrieves the ShipFromState property.
     *
     * @return
     *  String containing the ShipFromState property.
     */
    public String getShipFromState(){
        return mShipFromState;
    }


    /**
     * Sets the ShipFromPostalCode property.
     *
     * @param pShipFromPostalCode
     *  String to use to update the property.
     */
    public void setShipFromPostalCode(String pShipFromPostalCode){
        this.mShipFromPostalCode = pShipFromPostalCode;
    }
    /**
     * Retrieves the ShipFromPostalCode property.
     *
     * @return
     *  String containing the ShipFromPostalCode property.
     */
    public String getShipFromPostalCode(){
        return mShipFromPostalCode;
    }


    /**
     * Sets the ShipFromCountryCd property.
     *
     * @param pShipFromCountryCd
     *  String to use to update the property.
     */
    public void setShipFromCountryCd(String pShipFromCountryCd){
        this.mShipFromCountryCd = pShipFromCountryCd;
    }
    /**
     * Retrieves the ShipFromCountryCd property.
     *
     * @return
     *  String containing the ShipFromCountryCd property.
     */
    public String getShipFromCountryCd(){
        return mShipFromCountryCd;
    }


    /**
     * Sets the ShipFromStatus property.
     *
     * @param pShipFromStatus
     *  String to use to update the property.
     */
    public void setShipFromStatus(String pShipFromStatus){
        this.mShipFromStatus = pShipFromStatus;
    }
    /**
     * Retrieves the ShipFromStatus property.
     *
     * @return
     *  String containing the ShipFromStatus property.
     */
    public String getShipFromStatus(){
        return mShipFromStatus;
    }


    
}
