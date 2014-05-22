package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>CallDescData</code> is a ValueObject 
 *  describbing a call .
 *
 *@author     liang
 *@created    Jan 03, 2002
 */
public class CallDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 1996195922401319888L;

    private CallData    mCallDetail;
    private String      mAccountName = new String("");
    private String      mSiteName    = new String("");
    private String      mSiteCity    = new String("");
    private String      mSiteState   = new String("");
    private String      mSiteZip     = new String("");
    private String      mAssignedTo  = new String("");
    
    /**
     *  Constructor.
     */
    public CallDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public CallDescData(CallData parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7) {
        mCallDetail = parm1;
        mAccountName = parm2;
        mSiteName = parm3;
        mSiteCity = parm4;
        mSiteState = parm5;
        mSiteZip = parm6;
        mAssignedTo = parm7;
    }


    /**
     *  Set the mCallDetail field.
     *
     *@param  v   The new Call value
     */
    public void setCallDetail(CallData v) {
        mCallDetail = v;
    }



    /**
     *  Get the mCallDetail field.
     *
     *@return    CallData
     */
    public CallData getCallDetail() {
        return mCallDetail;
    }


    /**
     *  Set the mAccountName field.
     *
     *@param  v   The new String value
     */
    public void setAccountName(String v) {
        mAccountName = v;
    }

    /**
     *  Get the mAccountName field.
     *
     *@return    String
     */
    public String getAccountName() {
        return mAccountName;
    }


    /**
     *  Set the mSiteName field.
     *
     *@param  v   The new String value
     */
    public void setSiteName(String v) {
        mSiteName = v;
    }

    /**
     *  Get the mSiteName field.
     *
     *@return    String
     */
    public String getSiteName() {
        return mSiteName;
    }


    /**
     *  Set the mSiteCity field.
     *
     *@param  v   The new String value
     */
    public void setSiteCity(String v) {
        mSiteCity = v;
    }

    /**
     *  Get the mSiteCity field.
     *
     *@return    String
     */
    public String getSiteCity() {
        return mSiteCity;
    }


    /**
     *  Set the mSiteState field.
     *
     *@param  v   The new String value
     */
    public void setSiteState(String v) {
        mSiteState = v;
    }

    /**
     *  Get the mSiteState field.
     *
     *@return    String
     */
    public String getSiteState() {
        return mSiteState;
    }


    /**
     *  Set the mSiteZip field.
     *
     *@param  v   The new String value
     */
    public void setSiteZip(String v) {
        mSiteZip = v;
    }

    /**
     *  Get the mSiteZip field.
     *
     *@return    String
     */
    public String getSiteZip() {
        return mSiteZip;
    }


    /**
     *  Set the mAssignedTo field.
     *
     *@param  v   The new String value
     */
    public void setAssignedTo(String v) {
        mAssignedTo = v;
    }

    /**
     *  Get the mAssignedTo field.
     *
     *@return    String
     */
    public String getAssignedTo() {
        return mAssignedTo;
    }

    
    
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this CallDescData object
     */
    public String toString() {
        
        return "[" +  "CallDetail=" +
                mCallDetail.toString() + ", AccountName=" + mAccountName + ", SiteName=" + mSiteName
                + ", SiteCity=" + mSiteName + ", SiteState=" + mSiteState + ", SiteZip=" + mSiteZip + ", AssignedTo=" + mAssignedTo
                + "]";
    }


    /**
     *  Creates a new CallDescData
     *
     *@return    Newly initialized CallDescData object.
     */
    public static CallDescData createValue() {
        CallDescData valueData = new CallDescData();
        return valueData;
    }

}

