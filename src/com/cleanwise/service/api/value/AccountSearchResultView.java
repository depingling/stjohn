
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AccountSearchResultView
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
import java.util.ArrayList;
import java.util.List;
import com.cleanwise.service.api.framework.ValueObject;




/**
 * <code>AccountSearchResultView</code> is a ViewObject class for UI.
 */
public class AccountSearchResultView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mAccountId;
    private String mErpNum;
    private String mShortDesc;
    private String mCity;
    private String mStateProvinceCd;
    private String mValue;
    private String mBusEntityStatusCd;
    private String mAddress1;
    private String mPostalCode;
    private String mCountryCd;

    /**
     * Constructor.
     */
    public AccountSearchResultView ()
    {
        mErpNum = "";
        mShortDesc = "";
        mCity = "";
        mStateProvinceCd = "";
        mValue = "";
        mBusEntityStatusCd = "";
        mAddress1 = "";
        mPostalCode = "";
        mCountryCd = "";
    }

    /**
     * Constructor. 
     */
    public AccountSearchResultView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10)
    {
        mAccountId = parm1;
        mErpNum = parm2;
        mShortDesc = parm3;
        mCity = parm4;
        mStateProvinceCd = parm5;
        mValue = parm6;
        mBusEntityStatusCd = parm7;
        mAddress1 = parm8;
        mPostalCode = parm9;
        mCountryCd = parm10;
        
    }

    /**
     * Creates a new AccountSearchResultView
     *
     * @return
     *  Newly initialized AccountSearchResultView object.
     */
    public static AccountSearchResultView createValue () 
    {
        AccountSearchResultView valueView = new AccountSearchResultView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AccountSearchResultView object
     */
    public String toString()
    {
        return "[" + "AccountId=" + mAccountId + ", ErpNum=" + mErpNum + ", ShortDesc=" + mShortDesc + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", Value=" + mValue + ", BusEntityStatusCd=" + mBusEntityStatusCd + ", Address1=" + mAddress1 + ", PostalCode=" + mPostalCode + ", CountryCd=" + mCountryCd + "]";
    }

    //4601 : Used to hold the fiscal year and list of fiscal year values for an account.
    private List<Integer> mFiscalYearsList = new ArrayList<Integer>();
    private int mSelectedFiscalYear;


    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AccountSearchResult");
	root.setAttribute("Id", String.valueOf(mAccountId));

	Element node;

        node = doc.createElement("ErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpNum)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node = doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node = doc.createElement("BusEntityStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityStatusCd)));
        root.appendChild(node);

        node = doc.createElement("Address1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress1)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AccountSearchResultView copy()  {
      AccountSearchResultView obj = new AccountSearchResultView();
      obj.setAccountId(mAccountId);
      obj.setErpNum(mErpNum);
      obj.setShortDesc(mShortDesc);
      obj.setCity(mCity);
      obj.setStateProvinceCd(mStateProvinceCd);
      obj.setValue(mValue);
      obj.setBusEntityStatusCd(mBusEntityStatusCd);
      obj.setAddress1(mAddress1);
      obj.setPostalCode(mPostalCode);
      obj.setCountryCd(mCountryCd);
      
      return obj;
    }

    
    /**
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the ErpNum property.
     *
     * @param pErpNum
     *  String to use to update the property.
     */
    public void setErpNum(String pErpNum){
        this.mErpNum = pErpNum;
    }
    /**
     * Retrieves the ErpNum property.
     *
     * @return
     *  String containing the ErpNum property.
     */
    public String getErpNum(){
        return mErpNum;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
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
     * Sets the Value property.
     *
     * @param pValue
     *  String to use to update the property.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
    }
    /**
     * Retrieves the Value property.
     *
     * @return
     *  String containing the Value property.
     */
    public String getValue(){
        return mValue;
    }


    /**
     * Sets the BusEntityStatusCd property.
     *
     * @param pBusEntityStatusCd
     *  String to use to update the property.
     */
    public void setBusEntityStatusCd(String pBusEntityStatusCd){
        this.mBusEntityStatusCd = pBusEntityStatusCd;
    }
    /**
     * Retrieves the BusEntityStatusCd property.
     *
     * @return
     *  String containing the BusEntityStatusCd property.
     */
    public String getBusEntityStatusCd(){
        return mBusEntityStatusCd;
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
     * Sets the CountryCd property.
     *
     * @param pCountryCd
     *  String to use to update the property.
     */
    public void setCountryCd(String pCountryCd){
        this.mCountryCd = pCountryCd;
    }
    /**
     * Retrieves the CountryCd property.
     *
     * @return
     *  String containing the CountryCd property.
     */
    public String getCountryCd(){
        return mCountryCd;
    }

    public void setFiscalYearsList(List<Integer> pFiscalYearsList) {
       	mFiscalYearsList = pFiscalYearsList;
    }

         public List<Integer> getFiscalYearsList() {
         	return mFiscalYearsList;
         }

         public void setSelectedFiscalYear(int pSelectedFiscalYear) {
         	mSelectedFiscalYear = pSelectedFiscalYear;
         }

         public int getSelectedFiscalYear() {
         	return mSelectedFiscalYear;
         }

    
}
