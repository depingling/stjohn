
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteDeliveryScheduleView
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
 * <code>SiteDeliveryScheduleView</code> is a ViewObject class for UI.
 */
public class SiteDeliveryScheduleView
extends ValueObject
{
   
    private static final long serialVersionUID = -1388782177203093828L;
    private int mSiteId;
    private String mSiteName;
    private String mSiteStatusCd;
    private String mSiteScheduleType;
    private String mCity;
    private String mState;
    private String mPostalCode;
    private String mCounty;
    private boolean mWeek1ofMonth;
    private boolean mWeek2ofMonth;
    private boolean mWeek3ofMonth;
    private boolean mWeek4ofMonth;
    private boolean mLastWeekofMonth;
    private String mIntervWeek;

    /**
     * Constructor.
     */
    public SiteDeliveryScheduleView ()
    {
        mSiteName = "";
        mSiteStatusCd = "";
        mSiteScheduleType = "";
        mCity = "";
        mState = "";
        mPostalCode = "";
        mCounty = "";
        mIntervWeek = "";
    }

    /**
     * Constructor. 
     */
    public SiteDeliveryScheduleView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, boolean parm9, boolean parm10, boolean parm11, boolean parm12, boolean parm13, String parm14)
    {
        mSiteId = parm1;
        mSiteName = parm2;
        mSiteStatusCd = parm3;
        mSiteScheduleType = parm4;
        mCity = parm5;
        mState = parm6;
        mPostalCode = parm7;
        mCounty = parm8;
        mWeek1ofMonth = parm9;
        mWeek2ofMonth = parm10;
        mWeek3ofMonth = parm11;
        mWeek4ofMonth = parm12;
        mLastWeekofMonth = parm13;
        mIntervWeek = parm14;
        
    }

    /**
     * Creates a new SiteDeliveryScheduleView
     *
     * @return
     *  Newly initialized SiteDeliveryScheduleView object.
     */
    public static SiteDeliveryScheduleView createValue () 
    {
        SiteDeliveryScheduleView valueView = new SiteDeliveryScheduleView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteDeliveryScheduleView object
     */
    public String toString()
    {
        return "[" + "SiteId=" + mSiteId + ", SiteName=" + mSiteName + ", SiteStatusCd=" + mSiteStatusCd + ", SiteScheduleType=" + mSiteScheduleType + ", City=" + mCity + ", State=" + mState + ", PostalCode=" + mPostalCode + ", County=" + mCounty + ", Week1ofMonth=" + mWeek1ofMonth + ", Week2ofMonth=" + mWeek2ofMonth + ", Week3ofMonth=" + mWeek3ofMonth + ", Week4ofMonth=" + mWeek4ofMonth + ", LastWeekofMonth=" + mLastWeekofMonth + ", IntervWeek=" + mIntervWeek + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SiteDeliverySchedule");
	root.setAttribute("Id", String.valueOf(mSiteId));

	Element node;

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node = doc.createElement("SiteStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteStatusCd)));
        root.appendChild(node);

        node = doc.createElement("SiteScheduleType");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteScheduleType)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("State");
        node.appendChild(doc.createTextNode(String.valueOf(mState)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("County");
        node.appendChild(doc.createTextNode(String.valueOf(mCounty)));
        root.appendChild(node);

        node = doc.createElement("Week1ofMonth");
        node.appendChild(doc.createTextNode(String.valueOf(mWeek1ofMonth)));
        root.appendChild(node);

        node = doc.createElement("Week2ofMonth");
        node.appendChild(doc.createTextNode(String.valueOf(mWeek2ofMonth)));
        root.appendChild(node);

        node = doc.createElement("Week3ofMonth");
        node.appendChild(doc.createTextNode(String.valueOf(mWeek3ofMonth)));
        root.appendChild(node);

        node = doc.createElement("Week4ofMonth");
        node.appendChild(doc.createTextNode(String.valueOf(mWeek4ofMonth)));
        root.appendChild(node);

        node = doc.createElement("LastWeekofMonth");
        node.appendChild(doc.createTextNode(String.valueOf(mLastWeekofMonth)));
        root.appendChild(node);

        node = doc.createElement("IntervWeek");
        node.appendChild(doc.createTextNode(String.valueOf(mIntervWeek)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SiteDeliveryScheduleView copy()  {
      SiteDeliveryScheduleView obj = new SiteDeliveryScheduleView();
      obj.setSiteId(mSiteId);
      obj.setSiteName(mSiteName);
      obj.setSiteStatusCd(mSiteStatusCd);
      obj.setSiteScheduleType(mSiteScheduleType);
      obj.setCity(mCity);
      obj.setState(mState);
      obj.setPostalCode(mPostalCode);
      obj.setCounty(mCounty);
      obj.setWeek1ofMonth(mWeek1ofMonth);
      obj.setWeek2ofMonth(mWeek2ofMonth);
      obj.setWeek3ofMonth(mWeek3ofMonth);
      obj.setWeek4ofMonth(mWeek4ofMonth);
      obj.setLastWeekofMonth(mLastWeekofMonth);
      obj.setIntervWeek(mIntervWeek);
      
      return obj;
    }

    
    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
    }


    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
    }


    /**
     * Sets the SiteStatusCd property.
     *
     * @param pSiteStatusCd
     *  String to use to update the property.
     */
    public void setSiteStatusCd(String pSiteStatusCd){
        this.mSiteStatusCd = pSiteStatusCd;
    }
    /**
     * Retrieves the SiteStatusCd property.
     *
     * @return
     *  String containing the SiteStatusCd property.
     */
    public String getSiteStatusCd(){
        return mSiteStatusCd;
    }


    /**
     * Sets the SiteScheduleType property.
     *
     * @param pSiteScheduleType
     *  String to use to update the property.
     */
    public void setSiteScheduleType(String pSiteScheduleType){
        this.mSiteScheduleType = pSiteScheduleType;
    }
    /**
     * Retrieves the SiteScheduleType property.
     *
     * @return
     *  String containing the SiteScheduleType property.
     */
    public String getSiteScheduleType(){
        return mSiteScheduleType;
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
     * Sets the State property.
     *
     * @param pState
     *  String to use to update the property.
     */
    public void setState(String pState){
        this.mState = pState;
    }
    /**
     * Retrieves the State property.
     *
     * @return
     *  String containing the State property.
     */
    public String getState(){
        return mState;
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
     * Sets the County property.
     *
     * @param pCounty
     *  String to use to update the property.
     */
    public void setCounty(String pCounty){
        this.mCounty = pCounty;
    }
    /**
     * Retrieves the County property.
     *
     * @return
     *  String containing the County property.
     */
    public String getCounty(){
        return mCounty;
    }


    /**
     * Sets the Week1ofMonth property.
     *
     * @param pWeek1ofMonth
     *  boolean to use to update the property.
     */
    public void setWeek1ofMonth(boolean pWeek1ofMonth){
        this.mWeek1ofMonth = pWeek1ofMonth;
    }
    /**
     * Retrieves the Week1ofMonth property.
     *
     * @return
     *  boolean containing the Week1ofMonth property.
     */
    public boolean getWeek1ofMonth(){
        return mWeek1ofMonth;
    }


    /**
     * Sets the Week2ofMonth property.
     *
     * @param pWeek2ofMonth
     *  boolean to use to update the property.
     */
    public void setWeek2ofMonth(boolean pWeek2ofMonth){
        this.mWeek2ofMonth = pWeek2ofMonth;
    }
    /**
     * Retrieves the Week2ofMonth property.
     *
     * @return
     *  boolean containing the Week2ofMonth property.
     */
    public boolean getWeek2ofMonth(){
        return mWeek2ofMonth;
    }


    /**
     * Sets the Week3ofMonth property.
     *
     * @param pWeek3ofMonth
     *  boolean to use to update the property.
     */
    public void setWeek3ofMonth(boolean pWeek3ofMonth){
        this.mWeek3ofMonth = pWeek3ofMonth;
    }
    /**
     * Retrieves the Week3ofMonth property.
     *
     * @return
     *  boolean containing the Week3ofMonth property.
     */
    public boolean getWeek3ofMonth(){
        return mWeek3ofMonth;
    }


    /**
     * Sets the Week4ofMonth property.
     *
     * @param pWeek4ofMonth
     *  boolean to use to update the property.
     */
    public void setWeek4ofMonth(boolean pWeek4ofMonth){
        this.mWeek4ofMonth = pWeek4ofMonth;
    }
    /**
     * Retrieves the Week4ofMonth property.
     *
     * @return
     *  boolean containing the Week4ofMonth property.
     */
    public boolean getWeek4ofMonth(){
        return mWeek4ofMonth;
    }


    /**
     * Sets the LastWeekofMonth property.
     *
     * @param pLastWeekofMonth
     *  boolean to use to update the property.
     */
    public void setLastWeekofMonth(boolean pLastWeekofMonth){
        this.mLastWeekofMonth = pLastWeekofMonth;
    }
    /**
     * Retrieves the LastWeekofMonth property.
     *
     * @return
     *  boolean containing the LastWeekofMonth property.
     */
    public boolean getLastWeekofMonth(){
        return mLastWeekofMonth;
    }


    /**
     * Sets the IntervWeek property.
     *
     * @param pIntervWeek
     *  String to use to update the property.
     */
    public void setIntervWeek(String pIntervWeek){
        this.mIntervWeek = pIntervWeek;
    }
    /**
     * Retrieves the IntervWeek property.
     *
     * @return
     *  String containing the IntervWeek property.
     */
    public String getIntervWeek(){
        return mIntervWeek;
    }


    
}
