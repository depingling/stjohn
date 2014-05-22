
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JanPakSiteView
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
 * <code>JanPakSiteView</code> is a ViewObject class for UI.
 */
public class JanPakSiteView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mShipNum;
    private String mShipName;
    private String mShipAddr1;
    private String mShipAddr2;
    private String mShipAddr3;
    private String mShipAddr4;
    private String mShipCity;
    private String mShipState;
    private String mShipZip;
    private String mBillNum;
    private String mBillName;
    private String mBillAddr1;
    private String mBillAddr2;
    private String mBillAddr3;
    private String mBillAddr4;
    private String mBillCity;
    private String mBillState;
    private String mBillZip;
    private String mCountry;
    private String mCustSelCode;

    /**
     * Constructor.
     */
    public JanPakSiteView ()
    {
        mShipNum = "";
        mShipName = "";
        mShipAddr1 = "";
        mShipAddr2 = "";
        mShipAddr3 = "";
        mShipAddr4 = "";
        mShipCity = "";
        mShipState = "";
        mShipZip = "";
        mBillNum = "";
        mBillName = "";
        mBillAddr1 = "";
        mBillAddr2 = "";
        mBillAddr3 = "";
        mBillAddr4 = "";
        mBillCity = "";
        mBillState = "";
        mBillZip = "";
        mCountry = "";
        mCustSelCode = "";
    }

    /**
     * Constructor. 
     */
    public JanPakSiteView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20)
    {
        mShipNum = parm1;
        mShipName = parm2;
        mShipAddr1 = parm3;
        mShipAddr2 = parm4;
        mShipAddr3 = parm5;
        mShipAddr4 = parm6;
        mShipCity = parm7;
        mShipState = parm8;
        mShipZip = parm9;
        mBillNum = parm10;
        mBillName = parm11;
        mBillAddr1 = parm12;
        mBillAddr2 = parm13;
        mBillAddr3 = parm14;
        mBillAddr4 = parm15;
        mBillCity = parm16;
        mBillState = parm17;
        mBillZip = parm18;
        mCountry = parm19;
        mCustSelCode = parm20;
        
    }

    /**
     * Creates a new JanPakSiteView
     *
     * @return
     *  Newly initialized JanPakSiteView object.
     */
    public static JanPakSiteView createValue () 
    {
        JanPakSiteView valueView = new JanPakSiteView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JanPakSiteView object
     */
    public String toString()
    {
        return "[" + "ShipNum=" + mShipNum + ", ShipName=" + mShipName + ", ShipAddr1=" + mShipAddr1 + ", ShipAddr2=" + mShipAddr2 + ", ShipAddr3=" + mShipAddr3 + ", ShipAddr4=" + mShipAddr4 + ", ShipCity=" + mShipCity + ", ShipState=" + mShipState + ", ShipZip=" + mShipZip + ", BillNum=" + mBillNum + ", BillName=" + mBillName + ", BillAddr1=" + mBillAddr1 + ", BillAddr2=" + mBillAddr2 + ", BillAddr3=" + mBillAddr3 + ", BillAddr4=" + mBillAddr4 + ", BillCity=" + mBillCity + ", BillState=" + mBillState + ", BillZip=" + mBillZip + ", Country=" + mCountry + ", CustSelCode=" + mCustSelCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JanPakSite");
	root.setAttribute("Id", String.valueOf(mShipNum));

	Element node;

        node = doc.createElement("ShipName");
        node.appendChild(doc.createTextNode(String.valueOf(mShipName)));
        root.appendChild(node);

        node = doc.createElement("ShipAddr1");
        node.appendChild(doc.createTextNode(String.valueOf(mShipAddr1)));
        root.appendChild(node);

        node = doc.createElement("ShipAddr2");
        node.appendChild(doc.createTextNode(String.valueOf(mShipAddr2)));
        root.appendChild(node);

        node = doc.createElement("ShipAddr3");
        node.appendChild(doc.createTextNode(String.valueOf(mShipAddr3)));
        root.appendChild(node);

        node = doc.createElement("ShipAddr4");
        node.appendChild(doc.createTextNode(String.valueOf(mShipAddr4)));
        root.appendChild(node);

        node = doc.createElement("ShipCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShipCity)));
        root.appendChild(node);

        node = doc.createElement("ShipState");
        node.appendChild(doc.createTextNode(String.valueOf(mShipState)));
        root.appendChild(node);

        node = doc.createElement("ShipZip");
        node.appendChild(doc.createTextNode(String.valueOf(mShipZip)));
        root.appendChild(node);

        node = doc.createElement("BillNum");
        node.appendChild(doc.createTextNode(String.valueOf(mBillNum)));
        root.appendChild(node);

        node = doc.createElement("BillName");
        node.appendChild(doc.createTextNode(String.valueOf(mBillName)));
        root.appendChild(node);

        node = doc.createElement("BillAddr1");
        node.appendChild(doc.createTextNode(String.valueOf(mBillAddr1)));
        root.appendChild(node);

        node = doc.createElement("BillAddr2");
        node.appendChild(doc.createTextNode(String.valueOf(mBillAddr2)));
        root.appendChild(node);

        node = doc.createElement("BillAddr3");
        node.appendChild(doc.createTextNode(String.valueOf(mBillAddr3)));
        root.appendChild(node);

        node = doc.createElement("BillAddr4");
        node.appendChild(doc.createTextNode(String.valueOf(mBillAddr4)));
        root.appendChild(node);

        node = doc.createElement("BillCity");
        node.appendChild(doc.createTextNode(String.valueOf(mBillCity)));
        root.appendChild(node);

        node = doc.createElement("BillState");
        node.appendChild(doc.createTextNode(String.valueOf(mBillState)));
        root.appendChild(node);

        node = doc.createElement("BillZip");
        node.appendChild(doc.createTextNode(String.valueOf(mBillZip)));
        root.appendChild(node);

        node = doc.createElement("Country");
        node.appendChild(doc.createTextNode(String.valueOf(mCountry)));
        root.appendChild(node);

        node = doc.createElement("CustSelCode");
        node.appendChild(doc.createTextNode(String.valueOf(mCustSelCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public JanPakSiteView copy()  {
      JanPakSiteView obj = new JanPakSiteView();
      obj.setShipNum(mShipNum);
      obj.setShipName(mShipName);
      obj.setShipAddr1(mShipAddr1);
      obj.setShipAddr2(mShipAddr2);
      obj.setShipAddr3(mShipAddr3);
      obj.setShipAddr4(mShipAddr4);
      obj.setShipCity(mShipCity);
      obj.setShipState(mShipState);
      obj.setShipZip(mShipZip);
      obj.setBillNum(mBillNum);
      obj.setBillName(mBillName);
      obj.setBillAddr1(mBillAddr1);
      obj.setBillAddr2(mBillAddr2);
      obj.setBillAddr3(mBillAddr3);
      obj.setBillAddr4(mBillAddr4);
      obj.setBillCity(mBillCity);
      obj.setBillState(mBillState);
      obj.setBillZip(mBillZip);
      obj.setCountry(mCountry);
      obj.setCustSelCode(mCustSelCode);
      
      return obj;
    }

    
    /**
     * Sets the ShipNum property.
     *
     * @param pShipNum
     *  String to use to update the property.
     */
    public void setShipNum(String pShipNum){
        this.mShipNum = pShipNum;
    }
    /**
     * Retrieves the ShipNum property.
     *
     * @return
     *  String containing the ShipNum property.
     */
    public String getShipNum(){
        return mShipNum;
    }


    /**
     * Sets the ShipName property.
     *
     * @param pShipName
     *  String to use to update the property.
     */
    public void setShipName(String pShipName){
        this.mShipName = pShipName;
    }
    /**
     * Retrieves the ShipName property.
     *
     * @return
     *  String containing the ShipName property.
     */
    public String getShipName(){
        return mShipName;
    }


    /**
     * Sets the ShipAddr1 property.
     *
     * @param pShipAddr1
     *  String to use to update the property.
     */
    public void setShipAddr1(String pShipAddr1){
        this.mShipAddr1 = pShipAddr1;
    }
    /**
     * Retrieves the ShipAddr1 property.
     *
     * @return
     *  String containing the ShipAddr1 property.
     */
    public String getShipAddr1(){
        return mShipAddr1;
    }


    /**
     * Sets the ShipAddr2 property.
     *
     * @param pShipAddr2
     *  String to use to update the property.
     */
    public void setShipAddr2(String pShipAddr2){
        this.mShipAddr2 = pShipAddr2;
    }
    /**
     * Retrieves the ShipAddr2 property.
     *
     * @return
     *  String containing the ShipAddr2 property.
     */
    public String getShipAddr2(){
        return mShipAddr2;
    }


    /**
     * Sets the ShipAddr3 property.
     *
     * @param pShipAddr3
     *  String to use to update the property.
     */
    public void setShipAddr3(String pShipAddr3){
        this.mShipAddr3 = pShipAddr3;
    }
    /**
     * Retrieves the ShipAddr3 property.
     *
     * @return
     *  String containing the ShipAddr3 property.
     */
    public String getShipAddr3(){
        return mShipAddr3;
    }


    /**
     * Sets the ShipAddr4 property.
     *
     * @param pShipAddr4
     *  String to use to update the property.
     */
    public void setShipAddr4(String pShipAddr4){
        this.mShipAddr4 = pShipAddr4;
    }
    /**
     * Retrieves the ShipAddr4 property.
     *
     * @return
     *  String containing the ShipAddr4 property.
     */
    public String getShipAddr4(){
        return mShipAddr4;
    }


    /**
     * Sets the ShipCity property.
     *
     * @param pShipCity
     *  String to use to update the property.
     */
    public void setShipCity(String pShipCity){
        this.mShipCity = pShipCity;
    }
    /**
     * Retrieves the ShipCity property.
     *
     * @return
     *  String containing the ShipCity property.
     */
    public String getShipCity(){
        return mShipCity;
    }


    /**
     * Sets the ShipState property.
     *
     * @param pShipState
     *  String to use to update the property.
     */
    public void setShipState(String pShipState){
        this.mShipState = pShipState;
    }
    /**
     * Retrieves the ShipState property.
     *
     * @return
     *  String containing the ShipState property.
     */
    public String getShipState(){
        return mShipState;
    }


    /**
     * Sets the ShipZip property.
     *
     * @param pShipZip
     *  String to use to update the property.
     */
    public void setShipZip(String pShipZip){
        this.mShipZip = pShipZip;
    }
    /**
     * Retrieves the ShipZip property.
     *
     * @return
     *  String containing the ShipZip property.
     */
    public String getShipZip(){
        return mShipZip;
    }


    /**
     * Sets the BillNum property.
     *
     * @param pBillNum
     *  String to use to update the property.
     */
    public void setBillNum(String pBillNum){
        this.mBillNum = pBillNum;
    }
    /**
     * Retrieves the BillNum property.
     *
     * @return
     *  String containing the BillNum property.
     */
    public String getBillNum(){
        return mBillNum;
    }


    /**
     * Sets the BillName property.
     *
     * @param pBillName
     *  String to use to update the property.
     */
    public void setBillName(String pBillName){
        this.mBillName = pBillName;
    }
    /**
     * Retrieves the BillName property.
     *
     * @return
     *  String containing the BillName property.
     */
    public String getBillName(){
        return mBillName;
    }


    /**
     * Sets the BillAddr1 property.
     *
     * @param pBillAddr1
     *  String to use to update the property.
     */
    public void setBillAddr1(String pBillAddr1){
        this.mBillAddr1 = pBillAddr1;
    }
    /**
     * Retrieves the BillAddr1 property.
     *
     * @return
     *  String containing the BillAddr1 property.
     */
    public String getBillAddr1(){
        return mBillAddr1;
    }


    /**
     * Sets the BillAddr2 property.
     *
     * @param pBillAddr2
     *  String to use to update the property.
     */
    public void setBillAddr2(String pBillAddr2){
        this.mBillAddr2 = pBillAddr2;
    }
    /**
     * Retrieves the BillAddr2 property.
     *
     * @return
     *  String containing the BillAddr2 property.
     */
    public String getBillAddr2(){
        return mBillAddr2;
    }


    /**
     * Sets the BillAddr3 property.
     *
     * @param pBillAddr3
     *  String to use to update the property.
     */
    public void setBillAddr3(String pBillAddr3){
        this.mBillAddr3 = pBillAddr3;
    }
    /**
     * Retrieves the BillAddr3 property.
     *
     * @return
     *  String containing the BillAddr3 property.
     */
    public String getBillAddr3(){
        return mBillAddr3;
    }


    /**
     * Sets the BillAddr4 property.
     *
     * @param pBillAddr4
     *  String to use to update the property.
     */
    public void setBillAddr4(String pBillAddr4){
        this.mBillAddr4 = pBillAddr4;
    }
    /**
     * Retrieves the BillAddr4 property.
     *
     * @return
     *  String containing the BillAddr4 property.
     */
    public String getBillAddr4(){
        return mBillAddr4;
    }


    /**
     * Sets the BillCity property.
     *
     * @param pBillCity
     *  String to use to update the property.
     */
    public void setBillCity(String pBillCity){
        this.mBillCity = pBillCity;
    }
    /**
     * Retrieves the BillCity property.
     *
     * @return
     *  String containing the BillCity property.
     */
    public String getBillCity(){
        return mBillCity;
    }


    /**
     * Sets the BillState property.
     *
     * @param pBillState
     *  String to use to update the property.
     */
    public void setBillState(String pBillState){
        this.mBillState = pBillState;
    }
    /**
     * Retrieves the BillState property.
     *
     * @return
     *  String containing the BillState property.
     */
    public String getBillState(){
        return mBillState;
    }


    /**
     * Sets the BillZip property.
     *
     * @param pBillZip
     *  String to use to update the property.
     */
    public void setBillZip(String pBillZip){
        this.mBillZip = pBillZip;
    }
    /**
     * Retrieves the BillZip property.
     *
     * @return
     *  String containing the BillZip property.
     */
    public String getBillZip(){
        return mBillZip;
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


    /**
     * Sets the CustSelCode property.
     *
     * @param pCustSelCode
     *  String to use to update the property.
     */
    public void setCustSelCode(String pCustSelCode){
        this.mCustSelCode = pCustSelCode;
    }
    /**
     * Retrieves the CustSelCode property.
     *
     * @return
     *  String containing the CustSelCode property.
     */
    public String getCustSelCode(){
        return mCustSelCode;
    }


    
}
