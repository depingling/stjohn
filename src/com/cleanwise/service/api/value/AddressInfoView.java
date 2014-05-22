
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AddressInfoView
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
 * <code>AddressInfoView</code> is a ViewObject class for UI.
 */
public class AddressInfoView
extends ValueObject
{
   
    private static final long serialVersionUID = -301223613747130491L;
    private String mAddressTypeCd;
    private String mAccountErpNum;
    private String mStreetAddress;
    private String mAddress1;
    private String mAddress2;
    private String mAddress3;
    private String mAddress4;
    private String mCity;
    private String mStateProvinceCd;
    private String mPostalCode;
    private String mCountry;

    /**
     * Constructor.
     */
    public AddressInfoView ()
    {
        mAddressTypeCd = "";
        mAccountErpNum = "";
        mStreetAddress = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mStateProvinceCd = "";
        mPostalCode = "";
        mCountry = "";
    }

    /**
     * Constructor. 
     */
    public AddressInfoView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11)
    {
        mAddressTypeCd = parm1;
        mAccountErpNum = parm2;
        mStreetAddress = parm3;
        mAddress1 = parm4;
        mAddress2 = parm5;
        mAddress3 = parm6;
        mAddress4 = parm7;
        mCity = parm8;
        mStateProvinceCd = parm9;
        mPostalCode = parm10;
        mCountry = parm11;
        
    }

    /**
     * Creates a new AddressInfoView
     *
     * @return
     *  Newly initialized AddressInfoView object.
     */
    public static AddressInfoView createValue () 
    {
        AddressInfoView valueView = new AddressInfoView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AddressInfoView object
     */
    public String toString()
    {
        return "[" + "AddressTypeCd=" + mAddressTypeCd + ", AccountErpNum=" + mAccountErpNum + ", StreetAddress=" + mStreetAddress + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", PostalCode=" + mPostalCode + ", Country=" + mCountry + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AddressInfo");
	root.setAttribute("Id", String.valueOf(mAddressTypeCd));

	Element node;

        node = doc.createElement("AccountErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountErpNum)));
        root.appendChild(node);

        node = doc.createElement("StreetAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mStreetAddress)));
        root.appendChild(node);

        node = doc.createElement("Address1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress1)));
        root.appendChild(node);

        node = doc.createElement("Address2");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress2)));
        root.appendChild(node);

        node = doc.createElement("Address3");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress3)));
        root.appendChild(node);

        node = doc.createElement("Address4");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress4)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("Country");
        node.appendChild(doc.createTextNode(String.valueOf(mCountry)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AddressInfoView copy()  {
      AddressInfoView obj = new AddressInfoView();
      obj.setAddressTypeCd(mAddressTypeCd);
      obj.setAccountErpNum(mAccountErpNum);
      obj.setStreetAddress(mStreetAddress);
      obj.setAddress1(mAddress1);
      obj.setAddress2(mAddress2);
      obj.setAddress3(mAddress3);
      obj.setAddress4(mAddress4);
      obj.setCity(mCity);
      obj.setStateProvinceCd(mStateProvinceCd);
      obj.setPostalCode(mPostalCode);
      obj.setCountry(mCountry);
      
      return obj;
    }

    
    /**
     * Sets the AddressTypeCd property.
     *
     * @param pAddressTypeCd
     *  String to use to update the property.
     */
    public void setAddressTypeCd(String pAddressTypeCd){
        this.mAddressTypeCd = pAddressTypeCd;
    }
    /**
     * Retrieves the AddressTypeCd property.
     *
     * @return
     *  String containing the AddressTypeCd property.
     */
    public String getAddressTypeCd(){
        return mAddressTypeCd;
    }


    /**
     * Sets the AccountErpNum property.
     *
     * @param pAccountErpNum
     *  String to use to update the property.
     */
    public void setAccountErpNum(String pAccountErpNum){
        this.mAccountErpNum = pAccountErpNum;
    }
    /**
     * Retrieves the AccountErpNum property.
     *
     * @return
     *  String containing the AccountErpNum property.
     */
    public String getAccountErpNum(){
        return mAccountErpNum;
    }


    /**
     * Sets the StreetAddress property.
     *
     * @param pStreetAddress
     *  String to use to update the property.
     */
    public void setStreetAddress(String pStreetAddress){
        this.mStreetAddress = pStreetAddress;
    }
    /**
     * Retrieves the StreetAddress property.
     *
     * @return
     *  String containing the StreetAddress property.
     */
    public String getStreetAddress(){
        return mStreetAddress;
    }


    /**
     * Sets the Address1 property.
     *
     * @param pAddress1
     *  String to use to update the property.
     */
    public void setAddress1(String pAddress1){
        this.mAddress1 = pAddress1;
    }
    /**
     * Retrieves the Address1 property.
     *
     * @return
     *  String containing the Address1 property.
     */
    public String getAddress1(){
        return mAddress1;
    }


    /**
     * Sets the Address2 property.
     *
     * @param pAddress2
     *  String to use to update the property.
     */
    public void setAddress2(String pAddress2){
        this.mAddress2 = pAddress2;
    }
    /**
     * Retrieves the Address2 property.
     *
     * @return
     *  String containing the Address2 property.
     */
    public String getAddress2(){
        return mAddress2;
    }


    /**
     * Sets the Address3 property.
     *
     * @param pAddress3
     *  String to use to update the property.
     */
    public void setAddress3(String pAddress3){
        this.mAddress3 = pAddress3;
    }
    /**
     * Retrieves the Address3 property.
     *
     * @return
     *  String containing the Address3 property.
     */
    public String getAddress3(){
        return mAddress3;
    }


    /**
     * Sets the Address4 property.
     *
     * @param pAddress4
     *  String to use to update the property.
     */
    public void setAddress4(String pAddress4){
        this.mAddress4 = pAddress4;
    }
    /**
     * Retrieves the Address4 property.
     *
     * @return
     *  String containing the Address4 property.
     */
    public String getAddress4(){
        return mAddress4;
    }


    /**
     * Sets the City property.
     *
     * @param pCity
     *  String to use to update the property.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
    }
    /**
     * Retrieves the City property.
     *
     * @return
     *  String containing the City property.
     */
    public String getCity(){
        return mCity;
    }


    /**
     * Sets the StateProvinceCd property.
     *
     * @param pStateProvinceCd
     *  String to use to update the property.
     */
    public void setStateProvinceCd(String pStateProvinceCd){
        this.mStateProvinceCd = pStateProvinceCd;
    }
    /**
     * Retrieves the StateProvinceCd property.
     *
     * @return
     *  String containing the StateProvinceCd property.
     */
    public String getStateProvinceCd(){
        return mStateProvinceCd;
    }


    /**
     * Sets the PostalCode property.
     *
     * @param pPostalCode
     *  String to use to update the property.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
    }
    /**
     * Retrieves the PostalCode property.
     *
     * @return
     *  String containing the PostalCode property.
     */
    public String getPostalCode(){
        return mPostalCode;
    }


    /**
     * Sets the Country property.
     *
     * @param pCountry
     *  String to use to update the property.
     */
    public void setCountry(String pCountry){
        this.mCountry = pCountry;
    }
    /**
     * Retrieves the Country property.
     *
     * @return
     *  String containing the Country property.
     */
    public String getCountry(){
        return mCountry;
    }


    
}
