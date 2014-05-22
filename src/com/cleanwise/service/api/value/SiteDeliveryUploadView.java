
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteDeliveryUploadView
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
 * <code>SiteDeliveryUploadView</code> is a ViewObject class for UI.
 */
public class SiteDeliveryUploadView
extends ValueObject
{
   
    private static final long serialVersionUID = 1L;
    private String mCustomerMajorAccount;
    private String mCustomerMinorSite;
    private String mAccountRefNum;
    private String mSiteRefNum;
    private int mWeek;
    private int mYear;
    private int mDeliveryDay;
    private String mDeliveryFlag1;
    private int mCutoffDay1;
    private String mCutoffSystemTime1;
    private String mCutoffSiteTime1;
    private java.util.Date mDeliveryDate1;
    private java.util.Date mCutoffDate1;
    private String mDeliveryFlag2;
    private int mCutoffDay2;
    private String mCutoffSystemTime2;
    private String mCutoffSiteTime2;
    private java.util.Date mDeliveryDate2;
    private java.util.Date mCutoffDate2;
    private String mDeliveryFlag3;
    private int mCutoffDay3;
    private String mCutoffSystemTime3;
    private String mCutoffSiteTime3;
    private java.util.Date mDeliveryDate3;
    private java.util.Date mCutoffDate3;
    private String mDeliveryFlag4;
    private int mCutoffDay4;
    private String mCutoffSystemTime4;
    private String mCutoffSiteTime4;
    private java.util.Date mDeliveryDate4;
    private java.util.Date mCutoffDate4;
    private String mDeliveryFlag5;
    private int mCutoffDay5;
    private String mCutoffSystemTime5;
    private String mCutoffSiteTime5;
    private java.util.Date mDeliveryDate5;
    private java.util.Date mCutoffDate5;
    private String mDeliveryFlag6;
    private int mCutoffDay6;
    private String mCutoffSystemTime6;
    private String mCutoffSiteTime6;
    private java.util.Date mDeliveryDate6;
    private java.util.Date mCutoffDate6;
    private String mDeliveryFlag7;
    private int mCutoffDay7;
    private String mCutoffSystemTime7;
    private String mCutoffSiteTime7;
    private java.util.Date mDeliveryDate7;
    private java.util.Date mCutoffDate7;

    /**
     * Constructor.
     */
    public SiteDeliveryUploadView ()
    {
        mCustomerMajorAccount = "";
        mCustomerMinorSite = "";
        mAccountRefNum = "";
        mSiteRefNum = "";
        mDeliveryFlag1 = "";
        mCutoffSystemTime1 = "";
        mCutoffSiteTime1 = "";
        mDeliveryFlag2 = "";
        mCutoffSystemTime2 = "";
        mCutoffSiteTime2 = "";
        mDeliveryFlag3 = "";
        mCutoffSystemTime3 = "";
        mCutoffSiteTime3 = "";
        mDeliveryFlag4 = "";
        mCutoffSystemTime4 = "";
        mCutoffSiteTime4 = "";
        mDeliveryFlag5 = "";
        mCutoffSystemTime5 = "";
        mCutoffSiteTime5 = "";
        mDeliveryFlag6 = "";
        mCutoffSystemTime6 = "";
        mCutoffSiteTime6 = "";
        mDeliveryFlag7 = "";
        mCutoffSystemTime7 = "";
        mCutoffSiteTime7 = "";
    }

    /**
     * Constructor. 
     */
    public SiteDeliveryUploadView(String parm1, String parm2, String parm3, String parm4, int parm5, int parm6, int parm7, String parm8, int parm9, String parm10, String parm11, java.util.Date parm12, java.util.Date parm13, String parm14, int parm15, String parm16, String parm17, java.util.Date parm18, java.util.Date parm19, String parm20, int parm21, String parm22, String parm23, java.util.Date parm24, java.util.Date parm25, String parm26, int parm27, String parm28, String parm29, java.util.Date parm30, java.util.Date parm31, String parm32, int parm33, String parm34, String parm35, java.util.Date parm36, java.util.Date parm37, String parm38, int parm39, String parm40, String parm41, java.util.Date parm42, java.util.Date parm43, String parm44, int parm45, String parm46, String parm47, java.util.Date parm48, java.util.Date parm49)
    {
        mCustomerMajorAccount = parm1;
        mCustomerMinorSite = parm2;
        mAccountRefNum = parm3;
        mSiteRefNum = parm4;
        mWeek = parm5;
        mYear = parm6;
        mDeliveryDay = parm7;
        mDeliveryFlag1 = parm8;
        mCutoffDay1 = parm9;
        mCutoffSystemTime1 = parm10;
        mCutoffSiteTime1 = parm11;
        mDeliveryDate1 = parm12;
        mCutoffDate1 = parm13;
        mDeliveryFlag2 = parm14;
        mCutoffDay2 = parm15;
        mCutoffSystemTime2 = parm16;
        mCutoffSiteTime2 = parm17;
        mDeliveryDate2 = parm18;
        mCutoffDate2 = parm19;
        mDeliveryFlag3 = parm20;
        mCutoffDay3 = parm21;
        mCutoffSystemTime3 = parm22;
        mCutoffSiteTime3 = parm23;
        mDeliveryDate3 = parm24;
        mCutoffDate3 = parm25;
        mDeliveryFlag4 = parm26;
        mCutoffDay4 = parm27;
        mCutoffSystemTime4 = parm28;
        mCutoffSiteTime4 = parm29;
        mDeliveryDate4 = parm30;
        mCutoffDate4 = parm31;
        mDeliveryFlag5 = parm32;
        mCutoffDay5 = parm33;
        mCutoffSystemTime5 = parm34;
        mCutoffSiteTime5 = parm35;
        mDeliveryDate5 = parm36;
        mCutoffDate5 = parm37;
        mDeliveryFlag6 = parm38;
        mCutoffDay6 = parm39;
        mCutoffSystemTime6 = parm40;
        mCutoffSiteTime6 = parm41;
        mDeliveryDate6 = parm42;
        mCutoffDate6 = parm43;
        mDeliveryFlag7 = parm44;
        mCutoffDay7 = parm45;
        mCutoffSystemTime7 = parm46;
        mCutoffSiteTime7 = parm47;
        mDeliveryDate7 = parm48;
        mCutoffDate7 = parm49;
        
    }

    /**
     * Creates a new SiteDeliveryUploadView
     *
     * @return
     *  Newly initialized SiteDeliveryUploadView object.
     */
    public static SiteDeliveryUploadView createValue () 
    {
        SiteDeliveryUploadView valueView = new SiteDeliveryUploadView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteDeliveryUploadView object
     */
    public String toString()
    {
        return "[" + "CustomerMajorAccount=" + mCustomerMajorAccount + ", CustomerMinorSite=" + mCustomerMinorSite + ", AccountRefNum=" + mAccountRefNum + ", SiteRefNum=" + mSiteRefNum + ", Week=" + mWeek + ", Year=" + mYear + ", DeliveryDay=" + mDeliveryDay + ", DeliveryFlag1=" + mDeliveryFlag1 + ", CutoffDay1=" + mCutoffDay1 + ", CutoffSystemTime1=" + mCutoffSystemTime1 + ", CutoffSiteTime1=" + mCutoffSiteTime1 + ", DeliveryDate1=" + mDeliveryDate1 + ", CutoffDate1=" + mCutoffDate1 + ", DeliveryFlag2=" + mDeliveryFlag2 + ", CutoffDay2=" + mCutoffDay2 + ", CutoffSystemTime2=" + mCutoffSystemTime2 + ", CutoffSiteTime2=" + mCutoffSiteTime2 + ", DeliveryDate2=" + mDeliveryDate2 + ", CutoffDate2=" + mCutoffDate2 + ", DeliveryFlag3=" + mDeliveryFlag3 + ", CutoffDay3=" + mCutoffDay3 + ", CutoffSystemTime3=" + mCutoffSystemTime3 + ", CutoffSiteTime3=" + mCutoffSiteTime3 + ", DeliveryDate3=" + mDeliveryDate3 + ", CutoffDate3=" + mCutoffDate3 + ", DeliveryFlag4=" + mDeliveryFlag4 + ", CutoffDay4=" + mCutoffDay4 + ", CutoffSystemTime4=" + mCutoffSystemTime4 + ", CutoffSiteTime4=" + mCutoffSiteTime4 + ", DeliveryDate4=" + mDeliveryDate4 + ", CutoffDate4=" + mCutoffDate4 + ", DeliveryFlag5=" + mDeliveryFlag5 + ", CutoffDay5=" + mCutoffDay5 + ", CutoffSystemTime5=" + mCutoffSystemTime5 + ", CutoffSiteTime5=" + mCutoffSiteTime5 + ", DeliveryDate5=" + mDeliveryDate5 + ", CutoffDate5=" + mCutoffDate5 + ", DeliveryFlag6=" + mDeliveryFlag6 + ", CutoffDay6=" + mCutoffDay6 + ", CutoffSystemTime6=" + mCutoffSystemTime6 + ", CutoffSiteTime6=" + mCutoffSiteTime6 + ", DeliveryDate6=" + mDeliveryDate6 + ", CutoffDate6=" + mCutoffDate6 + ", DeliveryFlag7=" + mDeliveryFlag7 + ", CutoffDay7=" + mCutoffDay7 + ", CutoffSystemTime7=" + mCutoffSystemTime7 + ", CutoffSiteTime7=" + mCutoffSiteTime7 + ", DeliveryDate7=" + mDeliveryDate7 + ", CutoffDate7=" + mCutoffDate7 + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SiteDeliveryUpload");
	root.setAttribute("Id", String.valueOf(mCustomerMajorAccount));

	Element node;

        node = doc.createElement("CustomerMinorSite");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerMinorSite)));
        root.appendChild(node);

        node = doc.createElement("AccountRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountRefNum)));
        root.appendChild(node);

        node = doc.createElement("SiteRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteRefNum)));
        root.appendChild(node);

        node = doc.createElement("Week");
        node.appendChild(doc.createTextNode(String.valueOf(mWeek)));
        root.appendChild(node);

        node = doc.createElement("Year");
        node.appendChild(doc.createTextNode(String.valueOf(mYear)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDay");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDay)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag1");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag1)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay1");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay1)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime1");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime1)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime1");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime1)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate1");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate1)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate1");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate1)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag2");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag2)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay2");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay2)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime2");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime2)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime2");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime2)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate2");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate2)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate2");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate2)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag3");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag3)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay3");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay3)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime3");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime3)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime3");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime3)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate3");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate3)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate3");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate3)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag4");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag4)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay4");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay4)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime4");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime4)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime4");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime4)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate4");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate4)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate4");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate4)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag5");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag5)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay5");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay5)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime5");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime5)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime5");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime5)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate5");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate5)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate5");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate5)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag6");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag6)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay6");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay6)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime6");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime6)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime6");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime6)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate6");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate6)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate6");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate6)));
        root.appendChild(node);

        node = doc.createElement("DeliveryFlag7");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryFlag7)));
        root.appendChild(node);

        node = doc.createElement("CutoffDay7");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDay7)));
        root.appendChild(node);

        node = doc.createElement("CutoffSystemTime7");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSystemTime7)));
        root.appendChild(node);

        node = doc.createElement("CutoffSiteTime7");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffSiteTime7)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate7");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate7)));
        root.appendChild(node);

        node = doc.createElement("CutoffDate7");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate7)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SiteDeliveryUploadView copy()  {
      SiteDeliveryUploadView obj = new SiteDeliveryUploadView();
      obj.setCustomerMajorAccount(mCustomerMajorAccount);
      obj.setCustomerMinorSite(mCustomerMinorSite);
      obj.setAccountRefNum(mAccountRefNum);
      obj.setSiteRefNum(mSiteRefNum);
      obj.setWeek(mWeek);
      obj.setYear(mYear);
      obj.setDeliveryDay(mDeliveryDay);
      obj.setDeliveryFlag1(mDeliveryFlag1);
      obj.setCutoffDay1(mCutoffDay1);
      obj.setCutoffSystemTime1(mCutoffSystemTime1);
      obj.setCutoffSiteTime1(mCutoffSiteTime1);
      obj.setDeliveryDate1(mDeliveryDate1);
      obj.setCutoffDate1(mCutoffDate1);
      obj.setDeliveryFlag2(mDeliveryFlag2);
      obj.setCutoffDay2(mCutoffDay2);
      obj.setCutoffSystemTime2(mCutoffSystemTime2);
      obj.setCutoffSiteTime2(mCutoffSiteTime2);
      obj.setDeliveryDate2(mDeliveryDate2);
      obj.setCutoffDate2(mCutoffDate2);
      obj.setDeliveryFlag3(mDeliveryFlag3);
      obj.setCutoffDay3(mCutoffDay3);
      obj.setCutoffSystemTime3(mCutoffSystemTime3);
      obj.setCutoffSiteTime3(mCutoffSiteTime3);
      obj.setDeliveryDate3(mDeliveryDate3);
      obj.setCutoffDate3(mCutoffDate3);
      obj.setDeliveryFlag4(mDeliveryFlag4);
      obj.setCutoffDay4(mCutoffDay4);
      obj.setCutoffSystemTime4(mCutoffSystemTime4);
      obj.setCutoffSiteTime4(mCutoffSiteTime4);
      obj.setDeliveryDate4(mDeliveryDate4);
      obj.setCutoffDate4(mCutoffDate4);
      obj.setDeliveryFlag5(mDeliveryFlag5);
      obj.setCutoffDay5(mCutoffDay5);
      obj.setCutoffSystemTime5(mCutoffSystemTime5);
      obj.setCutoffSiteTime5(mCutoffSiteTime5);
      obj.setDeliveryDate5(mDeliveryDate5);
      obj.setCutoffDate5(mCutoffDate5);
      obj.setDeliveryFlag6(mDeliveryFlag6);
      obj.setCutoffDay6(mCutoffDay6);
      obj.setCutoffSystemTime6(mCutoffSystemTime6);
      obj.setCutoffSiteTime6(mCutoffSiteTime6);
      obj.setDeliveryDate6(mDeliveryDate6);
      obj.setCutoffDate6(mCutoffDate6);
      obj.setDeliveryFlag7(mDeliveryFlag7);
      obj.setCutoffDay7(mCutoffDay7);
      obj.setCutoffSystemTime7(mCutoffSystemTime7);
      obj.setCutoffSiteTime7(mCutoffSiteTime7);
      obj.setDeliveryDate7(mDeliveryDate7);
      obj.setCutoffDate7(mCutoffDate7);
      
      return obj;
    }

    
    /**
     * Sets the CustomerMajorAccount property.
     *
     * @param pCustomerMajorAccount
     *  String to use to update the property.
     */
    public void setCustomerMajorAccount(String pCustomerMajorAccount){
        this.mCustomerMajorAccount = pCustomerMajorAccount;
    }
    /**
     * Retrieves the CustomerMajorAccount property.
     *
     * @return
     *  String containing the CustomerMajorAccount property.
     */
    public String getCustomerMajorAccount(){
        return mCustomerMajorAccount;
    }


    /**
     * Sets the CustomerMinorSite property.
     *
     * @param pCustomerMinorSite
     *  String to use to update the property.
     */
    public void setCustomerMinorSite(String pCustomerMinorSite){
        this.mCustomerMinorSite = pCustomerMinorSite;
    }
    /**
     * Retrieves the CustomerMinorSite property.
     *
     * @return
     *  String containing the CustomerMinorSite property.
     */
    public String getCustomerMinorSite(){
        return mCustomerMinorSite;
    }


    /**
     * Sets the AccountRefNum property.
     *
     * @param pAccountRefNum
     *  String to use to update the property.
     */
    public void setAccountRefNum(String pAccountRefNum){
        this.mAccountRefNum = pAccountRefNum;
    }
    /**
     * Retrieves the AccountRefNum property.
     *
     * @return
     *  String containing the AccountRefNum property.
     */
    public String getAccountRefNum(){
        return mAccountRefNum;
    }


    /**
     * Sets the SiteRefNum property.
     *
     * @param pSiteRefNum
     *  String to use to update the property.
     */
    public void setSiteRefNum(String pSiteRefNum){
        this.mSiteRefNum = pSiteRefNum;
    }
    /**
     * Retrieves the SiteRefNum property.
     *
     * @return
     *  String containing the SiteRefNum property.
     */
    public String getSiteRefNum(){
        return mSiteRefNum;
    }


    /**
     * Sets the Week property.
     *
     * @param pWeek
     *  int to use to update the property.
     */
    public void setWeek(int pWeek){
        this.mWeek = pWeek;
    }
    /**
     * Retrieves the Week property.
     *
     * @return
     *  int containing the Week property.
     */
    public int getWeek(){
        return mWeek;
    }


    /**
     * Sets the Year property.
     *
     * @param pYear
     *  int to use to update the property.
     */
    public void setYear(int pYear){
        this.mYear = pYear;
    }
    /**
     * Retrieves the Year property.
     *
     * @return
     *  int containing the Year property.
     */
    public int getYear(){
        return mYear;
    }


    /**
     * Sets the DeliveryDay property.
     *
     * @param pDeliveryDay
     *  int to use to update the property.
     */
    public void setDeliveryDay(int pDeliveryDay){
        this.mDeliveryDay = pDeliveryDay;
    }
    /**
     * Retrieves the DeliveryDay property.
     *
     * @return
     *  int containing the DeliveryDay property.
     */
    public int getDeliveryDay(){
        return mDeliveryDay;
    }


    /**
     * Sets the DeliveryFlag1 property.
     *
     * @param pDeliveryFlag1
     *  String to use to update the property.
     */
    public void setDeliveryFlag1(String pDeliveryFlag1){
        this.mDeliveryFlag1 = pDeliveryFlag1;
    }
    /**
     * Retrieves the DeliveryFlag1 property.
     *
     * @return
     *  String containing the DeliveryFlag1 property.
     */
    public String getDeliveryFlag1(){
        return mDeliveryFlag1;
    }


    /**
     * Sets the CutoffDay1 property.
     *
     * @param pCutoffDay1
     *  int to use to update the property.
     */
    public void setCutoffDay1(int pCutoffDay1){
        this.mCutoffDay1 = pCutoffDay1;
    }
    /**
     * Retrieves the CutoffDay1 property.
     *
     * @return
     *  int containing the CutoffDay1 property.
     */
    public int getCutoffDay1(){
        return mCutoffDay1;
    }


    /**
     * Sets the CutoffSystemTime1 property.
     *
     * @param pCutoffSystemTime1
     *  String to use to update the property.
     */
    public void setCutoffSystemTime1(String pCutoffSystemTime1){
        this.mCutoffSystemTime1 = pCutoffSystemTime1;
    }
    /**
     * Retrieves the CutoffSystemTime1 property.
     *
     * @return
     *  String containing the CutoffSystemTime1 property.
     */
    public String getCutoffSystemTime1(){
        return mCutoffSystemTime1;
    }


    /**
     * Sets the CutoffSiteTime1 property.
     *
     * @param pCutoffSiteTime1
     *  String to use to update the property.
     */
    public void setCutoffSiteTime1(String pCutoffSiteTime1){
        this.mCutoffSiteTime1 = pCutoffSiteTime1;
    }
    /**
     * Retrieves the CutoffSiteTime1 property.
     *
     * @return
     *  String containing the CutoffSiteTime1 property.
     */
    public String getCutoffSiteTime1(){
        return mCutoffSiteTime1;
    }


    /**
     * Sets the DeliveryDate1 property.
     *
     * @param pDeliveryDate1
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate1(java.util.Date pDeliveryDate1){
        this.mDeliveryDate1 = pDeliveryDate1;
    }
    /**
     * Retrieves the DeliveryDate1 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate1 property.
     */
    public java.util.Date getDeliveryDate1(){
        return mDeliveryDate1;
    }


    /**
     * Sets the CutoffDate1 property.
     *
     * @param pCutoffDate1
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate1(java.util.Date pCutoffDate1){
        this.mCutoffDate1 = pCutoffDate1;
    }
    /**
     * Retrieves the CutoffDate1 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate1 property.
     */
    public java.util.Date getCutoffDate1(){
        return mCutoffDate1;
    }


    /**
     * Sets the DeliveryFlag2 property.
     *
     * @param pDeliveryFlag2
     *  String to use to update the property.
     */
    public void setDeliveryFlag2(String pDeliveryFlag2){
        this.mDeliveryFlag2 = pDeliveryFlag2;
    }
    /**
     * Retrieves the DeliveryFlag2 property.
     *
     * @return
     *  String containing the DeliveryFlag2 property.
     */
    public String getDeliveryFlag2(){
        return mDeliveryFlag2;
    }


    /**
     * Sets the CutoffDay2 property.
     *
     * @param pCutoffDay2
     *  int to use to update the property.
     */
    public void setCutoffDay2(int pCutoffDay2){
        this.mCutoffDay2 = pCutoffDay2;
    }
    /**
     * Retrieves the CutoffDay2 property.
     *
     * @return
     *  int containing the CutoffDay2 property.
     */
    public int getCutoffDay2(){
        return mCutoffDay2;
    }


    /**
     * Sets the CutoffSystemTime2 property.
     *
     * @param pCutoffSystemTime2
     *  String to use to update the property.
     */
    public void setCutoffSystemTime2(String pCutoffSystemTime2){
        this.mCutoffSystemTime2 = pCutoffSystemTime2;
    }
    /**
     * Retrieves the CutoffSystemTime2 property.
     *
     * @return
     *  String containing the CutoffSystemTime2 property.
     */
    public String getCutoffSystemTime2(){
        return mCutoffSystemTime2;
    }


    /**
     * Sets the CutoffSiteTime2 property.
     *
     * @param pCutoffSiteTime2
     *  String to use to update the property.
     */
    public void setCutoffSiteTime2(String pCutoffSiteTime2){
        this.mCutoffSiteTime2 = pCutoffSiteTime2;
    }
    /**
     * Retrieves the CutoffSiteTime2 property.
     *
     * @return
     *  String containing the CutoffSiteTime2 property.
     */
    public String getCutoffSiteTime2(){
        return mCutoffSiteTime2;
    }


    /**
     * Sets the DeliveryDate2 property.
     *
     * @param pDeliveryDate2
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate2(java.util.Date pDeliveryDate2){
        this.mDeliveryDate2 = pDeliveryDate2;
    }
    /**
     * Retrieves the DeliveryDate2 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate2 property.
     */
    public java.util.Date getDeliveryDate2(){
        return mDeliveryDate2;
    }


    /**
     * Sets the CutoffDate2 property.
     *
     * @param pCutoffDate2
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate2(java.util.Date pCutoffDate2){
        this.mCutoffDate2 = pCutoffDate2;
    }
    /**
     * Retrieves the CutoffDate2 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate2 property.
     */
    public java.util.Date getCutoffDate2(){
        return mCutoffDate2;
    }


    /**
     * Sets the DeliveryFlag3 property.
     *
     * @param pDeliveryFlag3
     *  String to use to update the property.
     */
    public void setDeliveryFlag3(String pDeliveryFlag3){
        this.mDeliveryFlag3 = pDeliveryFlag3;
    }
    /**
     * Retrieves the DeliveryFlag3 property.
     *
     * @return
     *  String containing the DeliveryFlag3 property.
     */
    public String getDeliveryFlag3(){
        return mDeliveryFlag3;
    }


    /**
     * Sets the CutoffDay3 property.
     *
     * @param pCutoffDay3
     *  int to use to update the property.
     */
    public void setCutoffDay3(int pCutoffDay3){
        this.mCutoffDay3 = pCutoffDay3;
    }
    /**
     * Retrieves the CutoffDay3 property.
     *
     * @return
     *  int containing the CutoffDay3 property.
     */
    public int getCutoffDay3(){
        return mCutoffDay3;
    }


    /**
     * Sets the CutoffSystemTime3 property.
     *
     * @param pCutoffSystemTime3
     *  String to use to update the property.
     */
    public void setCutoffSystemTime3(String pCutoffSystemTime3){
        this.mCutoffSystemTime3 = pCutoffSystemTime3;
    }
    /**
     * Retrieves the CutoffSystemTime3 property.
     *
     * @return
     *  String containing the CutoffSystemTime3 property.
     */
    public String getCutoffSystemTime3(){
        return mCutoffSystemTime3;
    }


    /**
     * Sets the CutoffSiteTime3 property.
     *
     * @param pCutoffSiteTime3
     *  String to use to update the property.
     */
    public void setCutoffSiteTime3(String pCutoffSiteTime3){
        this.mCutoffSiteTime3 = pCutoffSiteTime3;
    }
    /**
     * Retrieves the CutoffSiteTime3 property.
     *
     * @return
     *  String containing the CutoffSiteTime3 property.
     */
    public String getCutoffSiteTime3(){
        return mCutoffSiteTime3;
    }


    /**
     * Sets the DeliveryDate3 property.
     *
     * @param pDeliveryDate3
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate3(java.util.Date pDeliveryDate3){
        this.mDeliveryDate3 = pDeliveryDate3;
    }
    /**
     * Retrieves the DeliveryDate3 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate3 property.
     */
    public java.util.Date getDeliveryDate3(){
        return mDeliveryDate3;
    }


    /**
     * Sets the CutoffDate3 property.
     *
     * @param pCutoffDate3
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate3(java.util.Date pCutoffDate3){
        this.mCutoffDate3 = pCutoffDate3;
    }
    /**
     * Retrieves the CutoffDate3 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate3 property.
     */
    public java.util.Date getCutoffDate3(){
        return mCutoffDate3;
    }


    /**
     * Sets the DeliveryFlag4 property.
     *
     * @param pDeliveryFlag4
     *  String to use to update the property.
     */
    public void setDeliveryFlag4(String pDeliveryFlag4){
        this.mDeliveryFlag4 = pDeliveryFlag4;
    }
    /**
     * Retrieves the DeliveryFlag4 property.
     *
     * @return
     *  String containing the DeliveryFlag4 property.
     */
    public String getDeliveryFlag4(){
        return mDeliveryFlag4;
    }


    /**
     * Sets the CutoffDay4 property.
     *
     * @param pCutoffDay4
     *  int to use to update the property.
     */
    public void setCutoffDay4(int pCutoffDay4){
        this.mCutoffDay4 = pCutoffDay4;
    }
    /**
     * Retrieves the CutoffDay4 property.
     *
     * @return
     *  int containing the CutoffDay4 property.
     */
    public int getCutoffDay4(){
        return mCutoffDay4;
    }


    /**
     * Sets the CutoffSystemTime4 property.
     *
     * @param pCutoffSystemTime4
     *  String to use to update the property.
     */
    public void setCutoffSystemTime4(String pCutoffSystemTime4){
        this.mCutoffSystemTime4 = pCutoffSystemTime4;
    }
    /**
     * Retrieves the CutoffSystemTime4 property.
     *
     * @return
     *  String containing the CutoffSystemTime4 property.
     */
    public String getCutoffSystemTime4(){
        return mCutoffSystemTime4;
    }


    /**
     * Sets the CutoffSiteTime4 property.
     *
     * @param pCutoffSiteTime4
     *  String to use to update the property.
     */
    public void setCutoffSiteTime4(String pCutoffSiteTime4){
        this.mCutoffSiteTime4 = pCutoffSiteTime4;
    }
    /**
     * Retrieves the CutoffSiteTime4 property.
     *
     * @return
     *  String containing the CutoffSiteTime4 property.
     */
    public String getCutoffSiteTime4(){
        return mCutoffSiteTime4;
    }


    /**
     * Sets the DeliveryDate4 property.
     *
     * @param pDeliveryDate4
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate4(java.util.Date pDeliveryDate4){
        this.mDeliveryDate4 = pDeliveryDate4;
    }
    /**
     * Retrieves the DeliveryDate4 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate4 property.
     */
    public java.util.Date getDeliveryDate4(){
        return mDeliveryDate4;
    }


    /**
     * Sets the CutoffDate4 property.
     *
     * @param pCutoffDate4
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate4(java.util.Date pCutoffDate4){
        this.mCutoffDate4 = pCutoffDate4;
    }
    /**
     * Retrieves the CutoffDate4 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate4 property.
     */
    public java.util.Date getCutoffDate4(){
        return mCutoffDate4;
    }


    /**
     * Sets the DeliveryFlag5 property.
     *
     * @param pDeliveryFlag5
     *  String to use to update the property.
     */
    public void setDeliveryFlag5(String pDeliveryFlag5){
        this.mDeliveryFlag5 = pDeliveryFlag5;
    }
    /**
     * Retrieves the DeliveryFlag5 property.
     *
     * @return
     *  String containing the DeliveryFlag5 property.
     */
    public String getDeliveryFlag5(){
        return mDeliveryFlag5;
    }


    /**
     * Sets the CutoffDay5 property.
     *
     * @param pCutoffDay5
     *  int to use to update the property.
     */
    public void setCutoffDay5(int pCutoffDay5){
        this.mCutoffDay5 = pCutoffDay5;
    }
    /**
     * Retrieves the CutoffDay5 property.
     *
     * @return
     *  int containing the CutoffDay5 property.
     */
    public int getCutoffDay5(){
        return mCutoffDay5;
    }


    /**
     * Sets the CutoffSystemTime5 property.
     *
     * @param pCutoffSystemTime5
     *  String to use to update the property.
     */
    public void setCutoffSystemTime5(String pCutoffSystemTime5){
        this.mCutoffSystemTime5 = pCutoffSystemTime5;
    }
    /**
     * Retrieves the CutoffSystemTime5 property.
     *
     * @return
     *  String containing the CutoffSystemTime5 property.
     */
    public String getCutoffSystemTime5(){
        return mCutoffSystemTime5;
    }


    /**
     * Sets the CutoffSiteTime5 property.
     *
     * @param pCutoffSiteTime5
     *  String to use to update the property.
     */
    public void setCutoffSiteTime5(String pCutoffSiteTime5){
        this.mCutoffSiteTime5 = pCutoffSiteTime5;
    }
    /**
     * Retrieves the CutoffSiteTime5 property.
     *
     * @return
     *  String containing the CutoffSiteTime5 property.
     */
    public String getCutoffSiteTime5(){
        return mCutoffSiteTime5;
    }


    /**
     * Sets the DeliveryDate5 property.
     *
     * @param pDeliveryDate5
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate5(java.util.Date pDeliveryDate5){
        this.mDeliveryDate5 = pDeliveryDate5;
    }
    /**
     * Retrieves the DeliveryDate5 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate5 property.
     */
    public java.util.Date getDeliveryDate5(){
        return mDeliveryDate5;
    }


    /**
     * Sets the CutoffDate5 property.
     *
     * @param pCutoffDate5
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate5(java.util.Date pCutoffDate5){
        this.mCutoffDate5 = pCutoffDate5;
    }
    /**
     * Retrieves the CutoffDate5 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate5 property.
     */
    public java.util.Date getCutoffDate5(){
        return mCutoffDate5;
    }


    /**
     * Sets the DeliveryFlag6 property.
     *
     * @param pDeliveryFlag6
     *  String to use to update the property.
     */
    public void setDeliveryFlag6(String pDeliveryFlag6){
        this.mDeliveryFlag6 = pDeliveryFlag6;
    }
    /**
     * Retrieves the DeliveryFlag6 property.
     *
     * @return
     *  String containing the DeliveryFlag6 property.
     */
    public String getDeliveryFlag6(){
        return mDeliveryFlag6;
    }


    /**
     * Sets the CutoffDay6 property.
     *
     * @param pCutoffDay6
     *  int to use to update the property.
     */
    public void setCutoffDay6(int pCutoffDay6){
        this.mCutoffDay6 = pCutoffDay6;
    }
    /**
     * Retrieves the CutoffDay6 property.
     *
     * @return
     *  int containing the CutoffDay6 property.
     */
    public int getCutoffDay6(){
        return mCutoffDay6;
    }


    /**
     * Sets the CutoffSystemTime6 property.
     *
     * @param pCutoffSystemTime6
     *  String to use to update the property.
     */
    public void setCutoffSystemTime6(String pCutoffSystemTime6){
        this.mCutoffSystemTime6 = pCutoffSystemTime6;
    }
    /**
     * Retrieves the CutoffSystemTime6 property.
     *
     * @return
     *  String containing the CutoffSystemTime6 property.
     */
    public String getCutoffSystemTime6(){
        return mCutoffSystemTime6;
    }


    /**
     * Sets the CutoffSiteTime6 property.
     *
     * @param pCutoffSiteTime6
     *  String to use to update the property.
     */
    public void setCutoffSiteTime6(String pCutoffSiteTime6){
        this.mCutoffSiteTime6 = pCutoffSiteTime6;
    }
    /**
     * Retrieves the CutoffSiteTime6 property.
     *
     * @return
     *  String containing the CutoffSiteTime6 property.
     */
    public String getCutoffSiteTime6(){
        return mCutoffSiteTime6;
    }


    /**
     * Sets the DeliveryDate6 property.
     *
     * @param pDeliveryDate6
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate6(java.util.Date pDeliveryDate6){
        this.mDeliveryDate6 = pDeliveryDate6;
    }
    /**
     * Retrieves the DeliveryDate6 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate6 property.
     */
    public java.util.Date getDeliveryDate6(){
        return mDeliveryDate6;
    }


    /**
     * Sets the CutoffDate6 property.
     *
     * @param pCutoffDate6
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate6(java.util.Date pCutoffDate6){
        this.mCutoffDate6 = pCutoffDate6;
    }
    /**
     * Retrieves the CutoffDate6 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate6 property.
     */
    public java.util.Date getCutoffDate6(){
        return mCutoffDate6;
    }


    /**
     * Sets the DeliveryFlag7 property.
     *
     * @param pDeliveryFlag7
     *  String to use to update the property.
     */
    public void setDeliveryFlag7(String pDeliveryFlag7){
        this.mDeliveryFlag7 = pDeliveryFlag7;
    }
    /**
     * Retrieves the DeliveryFlag7 property.
     *
     * @return
     *  String containing the DeliveryFlag7 property.
     */
    public String getDeliveryFlag7(){
        return mDeliveryFlag7;
    }


    /**
     * Sets the CutoffDay7 property.
     *
     * @param pCutoffDay7
     *  int to use to update the property.
     */
    public void setCutoffDay7(int pCutoffDay7){
        this.mCutoffDay7 = pCutoffDay7;
    }
    /**
     * Retrieves the CutoffDay7 property.
     *
     * @return
     *  int containing the CutoffDay7 property.
     */
    public int getCutoffDay7(){
        return mCutoffDay7;
    }


    /**
     * Sets the CutoffSystemTime7 property.
     *
     * @param pCutoffSystemTime7
     *  String to use to update the property.
     */
    public void setCutoffSystemTime7(String pCutoffSystemTime7){
        this.mCutoffSystemTime7 = pCutoffSystemTime7;
    }
    /**
     * Retrieves the CutoffSystemTime7 property.
     *
     * @return
     *  String containing the CutoffSystemTime7 property.
     */
    public String getCutoffSystemTime7(){
        return mCutoffSystemTime7;
    }


    /**
     * Sets the CutoffSiteTime7 property.
     *
     * @param pCutoffSiteTime7
     *  String to use to update the property.
     */
    public void setCutoffSiteTime7(String pCutoffSiteTime7){
        this.mCutoffSiteTime7 = pCutoffSiteTime7;
    }
    /**
     * Retrieves the CutoffSiteTime7 property.
     *
     * @return
     *  String containing the CutoffSiteTime7 property.
     */
    public String getCutoffSiteTime7(){
        return mCutoffSiteTime7;
    }


    /**
     * Sets the DeliveryDate7 property.
     *
     * @param pDeliveryDate7
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate7(java.util.Date pDeliveryDate7){
        this.mDeliveryDate7 = pDeliveryDate7;
    }
    /**
     * Retrieves the DeliveryDate7 property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate7 property.
     */
    public java.util.Date getDeliveryDate7(){
        return mDeliveryDate7;
    }


    /**
     * Sets the CutoffDate7 property.
     *
     * @param pCutoffDate7
     *  java.util.Date to use to update the property.
     */
    public void setCutoffDate7(java.util.Date pCutoffDate7){
        this.mCutoffDate7 = pCutoffDate7;
    }
    /**
     * Retrieves the CutoffDate7 property.
     *
     * @return
     *  java.util.Date containing the CutoffDate7 property.
     */
    public java.util.Date getCutoffDate7(){
        return mCutoffDate7;
    }


    
}
