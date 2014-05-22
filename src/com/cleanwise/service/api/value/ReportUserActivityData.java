
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReportUserActivityData
 * Description:  This is a ValueObject class wrapping the database table RPT_REPORT_USER_ACTIVITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ReportUserActivityDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ReportUserActivityData</code> is a ValueObject class wrapping of the database table RPT_REPORT_USER_ACTIVITY.
 */
public class ReportUserActivityData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mReportUserActivityId;// SQL type:NUMBER, not null
    private int mStoreId;// SQL type:NUMBER
    private String mStoreName;// SQL type:VARCHAR2
    private String mSessionId;// SQL type:VARCHAR2
    private String mUserName;// SQL type:VARCHAR2
    private String mActionClass;// SQL type:VARCHAR2
    private String mAction;// SQL type:VARCHAR2
    private Date mHttpStartTime;// SQL type:DATE
    private Date mActionStartTime;// SQL type:DATE
    private Date mActionEndTime;// SQL type:DATE
    private Date mHttpEndTime;// SQL type:DATE
    private String mActionResult;// SQL type:VARCHAR2
    private String mHttpResult;// SQL type:VARCHAR2
    private java.math.BigDecimal mActionDuration;// SQL type:NUMBER
    private java.math.BigDecimal mHttpDuration;// SQL type:NUMBER
    private String mReferer;// SQL type:VARCHAR2
    private String mParams;// SQL type:VARCHAR2
    private String mFinishFile;// SQL type:VARCHAR2
    private String mServerName;// SQL type:VARCHAR2
    private String mRequestId;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ReportUserActivityData ()
    {
        mStoreName = "";
        mSessionId = "";
        mUserName = "";
        mActionClass = "";
        mAction = "";
        mActionResult = "";
        mHttpResult = "";
        mReferer = "";
        mParams = "";
        mFinishFile = "";
        mServerName = "";
        mRequestId = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ReportUserActivityData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, Date parm8, Date parm9, Date parm10, Date parm11, String parm12, String parm13, java.math.BigDecimal parm14, java.math.BigDecimal parm15, String parm16, String parm17, String parm18, String parm19, String parm20, Date parm21, String parm22, Date parm23, String parm24)
    {
        mReportUserActivityId = parm1;
        mStoreId = parm2;
        mStoreName = parm3;
        mSessionId = parm4;
        mUserName = parm5;
        mActionClass = parm6;
        mAction = parm7;
        mHttpStartTime = parm8;
        mActionStartTime = parm9;
        mActionEndTime = parm10;
        mHttpEndTime = parm11;
        mActionResult = parm12;
        mHttpResult = parm13;
        mActionDuration = parm14;
        mHttpDuration = parm15;
        mReferer = parm16;
        mParams = parm17;
        mFinishFile = parm18;
        mServerName = parm19;
        mRequestId = parm20;
        mAddDate = parm21;
        mAddBy = parm22;
        mModDate = parm23;
        mModBy = parm24;
        
    }

    /**
     * Creates a new ReportUserActivityData
     *
     * @return
     *  Newly initialized ReportUserActivityData object.
     */
    public static ReportUserActivityData createValue ()
    {
        ReportUserActivityData valueData = new ReportUserActivityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReportUserActivityData object
     */
    public String toString()
    {
        return "[" + "ReportUserActivityId=" + mReportUserActivityId + ", StoreId=" + mStoreId + ", StoreName=" + mStoreName + ", SessionId=" + mSessionId + ", UserName=" + mUserName + ", ActionClass=" + mActionClass + ", Action=" + mAction + ", HttpStartTime=" + mHttpStartTime + ", ActionStartTime=" + mActionStartTime + ", ActionEndTime=" + mActionEndTime + ", HttpEndTime=" + mHttpEndTime + ", ActionResult=" + mActionResult + ", HttpResult=" + mHttpResult + ", ActionDuration=" + mActionDuration + ", HttpDuration=" + mHttpDuration + ", Referer=" + mReferer + ", Params=" + mParams + ", FinishFile=" + mFinishFile + ", ServerName=" + mServerName + ", RequestId=" + mRequestId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ReportUserActivity");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mReportUserActivityId));

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("StoreName");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreName)));
        root.appendChild(node);

        node =  doc.createElement("SessionId");
        node.appendChild(doc.createTextNode(String.valueOf(mSessionId)));
        root.appendChild(node);

        node =  doc.createElement("UserName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserName)));
        root.appendChild(node);

        node =  doc.createElement("ActionClass");
        node.appendChild(doc.createTextNode(String.valueOf(mActionClass)));
        root.appendChild(node);

        node =  doc.createElement("Action");
        node.appendChild(doc.createTextNode(String.valueOf(mAction)));
        root.appendChild(node);

        node =  doc.createElement("HttpStartTime");
        node.appendChild(doc.createTextNode(String.valueOf(mHttpStartTime)));
        root.appendChild(node);

        node =  doc.createElement("ActionStartTime");
        node.appendChild(doc.createTextNode(String.valueOf(mActionStartTime)));
        root.appendChild(node);

        node =  doc.createElement("ActionEndTime");
        node.appendChild(doc.createTextNode(String.valueOf(mActionEndTime)));
        root.appendChild(node);

        node =  doc.createElement("HttpEndTime");
        node.appendChild(doc.createTextNode(String.valueOf(mHttpEndTime)));
        root.appendChild(node);

        node =  doc.createElement("ActionResult");
        node.appendChild(doc.createTextNode(String.valueOf(mActionResult)));
        root.appendChild(node);

        node =  doc.createElement("HttpResult");
        node.appendChild(doc.createTextNode(String.valueOf(mHttpResult)));
        root.appendChild(node);

        node =  doc.createElement("ActionDuration");
        node.appendChild(doc.createTextNode(String.valueOf(mActionDuration)));
        root.appendChild(node);

        node =  doc.createElement("HttpDuration");
        node.appendChild(doc.createTextNode(String.valueOf(mHttpDuration)));
        root.appendChild(node);

        node =  doc.createElement("Referer");
        node.appendChild(doc.createTextNode(String.valueOf(mReferer)));
        root.appendChild(node);

        node =  doc.createElement("Params");
        node.appendChild(doc.createTextNode(String.valueOf(mParams)));
        root.appendChild(node);

        node =  doc.createElement("FinishFile");
        node.appendChild(doc.createTextNode(String.valueOf(mFinishFile)));
        root.appendChild(node);

        node =  doc.createElement("ServerName");
        node.appendChild(doc.createTextNode(String.valueOf(mServerName)));
        root.appendChild(node);

        node =  doc.createElement("RequestId");
        node.appendChild(doc.createTextNode(String.valueOf(mRequestId)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ReportUserActivityId field is not cloned.
    *
    * @return ReportUserActivityData object
    */
    public Object clone(){
        ReportUserActivityData myClone = new ReportUserActivityData();
        
        myClone.mStoreId = mStoreId;
        
        myClone.mStoreName = mStoreName;
        
        myClone.mSessionId = mSessionId;
        
        myClone.mUserName = mUserName;
        
        myClone.mActionClass = mActionClass;
        
        myClone.mAction = mAction;
        
        if(mHttpStartTime != null){
                myClone.mHttpStartTime = (Date) mHttpStartTime.clone();
        }
        
        if(mActionStartTime != null){
                myClone.mActionStartTime = (Date) mActionStartTime.clone();
        }
        
        if(mActionEndTime != null){
                myClone.mActionEndTime = (Date) mActionEndTime.clone();
        }
        
        if(mHttpEndTime != null){
                myClone.mHttpEndTime = (Date) mHttpEndTime.clone();
        }
        
        myClone.mActionResult = mActionResult;
        
        myClone.mHttpResult = mHttpResult;
        
        myClone.mActionDuration = mActionDuration;
        
        myClone.mHttpDuration = mHttpDuration;
        
        myClone.mReferer = mReferer;
        
        myClone.mParams = mParams;
        
        myClone.mFinishFile = mFinishFile;
        
        myClone.mServerName = mServerName;
        
        myClone.mRequestId = mRequestId;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ReportUserActivityDataAccess.REPORT_USER_ACTIVITY_ID.equals(pFieldName)) {
            return getReportUserActivityId();
        } else if (ReportUserActivityDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (ReportUserActivityDataAccess.STORE_NAME.equals(pFieldName)) {
            return getStoreName();
        } else if (ReportUserActivityDataAccess.SESSION_ID.equals(pFieldName)) {
            return getSessionId();
        } else if (ReportUserActivityDataAccess.USER_NAME.equals(pFieldName)) {
            return getUserName();
        } else if (ReportUserActivityDataAccess.ACTION_CLASS.equals(pFieldName)) {
            return getActionClass();
        } else if (ReportUserActivityDataAccess.ACTION.equals(pFieldName)) {
            return getAction();
        } else if (ReportUserActivityDataAccess.HTTP_START_TIME.equals(pFieldName)) {
            return getHttpStartTime();
        } else if (ReportUserActivityDataAccess.ACTION_START_TIME.equals(pFieldName)) {
            return getActionStartTime();
        } else if (ReportUserActivityDataAccess.ACTION_END_TIME.equals(pFieldName)) {
            return getActionEndTime();
        } else if (ReportUserActivityDataAccess.HTTP_END_TIME.equals(pFieldName)) {
            return getHttpEndTime();
        } else if (ReportUserActivityDataAccess.ACTION_RESULT.equals(pFieldName)) {
            return getActionResult();
        } else if (ReportUserActivityDataAccess.HTTP_RESULT.equals(pFieldName)) {
            return getHttpResult();
        } else if (ReportUserActivityDataAccess.ACTION_DURATION.equals(pFieldName)) {
            return getActionDuration();
        } else if (ReportUserActivityDataAccess.HTTP_DURATION.equals(pFieldName)) {
            return getHttpDuration();
        } else if (ReportUserActivityDataAccess.REFERER.equals(pFieldName)) {
            return getReferer();
        } else if (ReportUserActivityDataAccess.PARAMS.equals(pFieldName)) {
            return getParams();
        } else if (ReportUserActivityDataAccess.FINISH_FILE.equals(pFieldName)) {
            return getFinishFile();
        } else if (ReportUserActivityDataAccess.SERVER_NAME.equals(pFieldName)) {
            return getServerName();
        } else if (ReportUserActivityDataAccess.REQUEST_ID.equals(pFieldName)) {
            return getRequestId();
        } else if (ReportUserActivityDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ReportUserActivityDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ReportUserActivityDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ReportUserActivityDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return ReportUserActivityDataAccess.RPT_REPORT_USER_ACTIVITY;
    }

    
    /**
     * Sets the ReportUserActivityId field. This field is required to be set in the database.
     *
     * @param pReportUserActivityId
     *  int to use to update the field.
     */
    public void setReportUserActivityId(int pReportUserActivityId){
        this.mReportUserActivityId = pReportUserActivityId;
        setDirty(true);
    }
    /**
     * Retrieves the ReportUserActivityId field.
     *
     * @return
     *  int containing the ReportUserActivityId field.
     */
    public int getReportUserActivityId(){
        return mReportUserActivityId;
    }

    /**
     * Sets the StoreId field.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the StoreName field.
     *
     * @param pStoreName
     *  String to use to update the field.
     */
    public void setStoreName(String pStoreName){
        this.mStoreName = pStoreName;
        setDirty(true);
    }
    /**
     * Retrieves the StoreName field.
     *
     * @return
     *  String containing the StoreName field.
     */
    public String getStoreName(){
        return mStoreName;
    }

    /**
     * Sets the SessionId field.
     *
     * @param pSessionId
     *  String to use to update the field.
     */
    public void setSessionId(String pSessionId){
        this.mSessionId = pSessionId;
        setDirty(true);
    }
    /**
     * Retrieves the SessionId field.
     *
     * @return
     *  String containing the SessionId field.
     */
    public String getSessionId(){
        return mSessionId;
    }

    /**
     * Sets the UserName field.
     *
     * @param pUserName
     *  String to use to update the field.
     */
    public void setUserName(String pUserName){
        this.mUserName = pUserName;
        setDirty(true);
    }
    /**
     * Retrieves the UserName field.
     *
     * @return
     *  String containing the UserName field.
     */
    public String getUserName(){
        return mUserName;
    }

    /**
     * Sets the ActionClass field.
     *
     * @param pActionClass
     *  String to use to update the field.
     */
    public void setActionClass(String pActionClass){
        this.mActionClass = pActionClass;
        setDirty(true);
    }
    /**
     * Retrieves the ActionClass field.
     *
     * @return
     *  String containing the ActionClass field.
     */
    public String getActionClass(){
        return mActionClass;
    }

    /**
     * Sets the Action field.
     *
     * @param pAction
     *  String to use to update the field.
     */
    public void setAction(String pAction){
        this.mAction = pAction;
        setDirty(true);
    }
    /**
     * Retrieves the Action field.
     *
     * @return
     *  String containing the Action field.
     */
    public String getAction(){
        return mAction;
    }

    /**
     * Sets the HttpStartTime field.
     *
     * @param pHttpStartTime
     *  Date to use to update the field.
     */
    public void setHttpStartTime(Date pHttpStartTime){
        this.mHttpStartTime = pHttpStartTime;
        setDirty(true);
    }
    /**
     * Retrieves the HttpStartTime field.
     *
     * @return
     *  Date containing the HttpStartTime field.
     */
    public Date getHttpStartTime(){
        return mHttpStartTime;
    }

    /**
     * Sets the ActionStartTime field.
     *
     * @param pActionStartTime
     *  Date to use to update the field.
     */
    public void setActionStartTime(Date pActionStartTime){
        this.mActionStartTime = pActionStartTime;
        setDirty(true);
    }
    /**
     * Retrieves the ActionStartTime field.
     *
     * @return
     *  Date containing the ActionStartTime field.
     */
    public Date getActionStartTime(){
        return mActionStartTime;
    }

    /**
     * Sets the ActionEndTime field.
     *
     * @param pActionEndTime
     *  Date to use to update the field.
     */
    public void setActionEndTime(Date pActionEndTime){
        this.mActionEndTime = pActionEndTime;
        setDirty(true);
    }
    /**
     * Retrieves the ActionEndTime field.
     *
     * @return
     *  Date containing the ActionEndTime field.
     */
    public Date getActionEndTime(){
        return mActionEndTime;
    }

    /**
     * Sets the HttpEndTime field.
     *
     * @param pHttpEndTime
     *  Date to use to update the field.
     */
    public void setHttpEndTime(Date pHttpEndTime){
        this.mHttpEndTime = pHttpEndTime;
        setDirty(true);
    }
    /**
     * Retrieves the HttpEndTime field.
     *
     * @return
     *  Date containing the HttpEndTime field.
     */
    public Date getHttpEndTime(){
        return mHttpEndTime;
    }

    /**
     * Sets the ActionResult field.
     *
     * @param pActionResult
     *  String to use to update the field.
     */
    public void setActionResult(String pActionResult){
        this.mActionResult = pActionResult;
        setDirty(true);
    }
    /**
     * Retrieves the ActionResult field.
     *
     * @return
     *  String containing the ActionResult field.
     */
    public String getActionResult(){
        return mActionResult;
    }

    /**
     * Sets the HttpResult field.
     *
     * @param pHttpResult
     *  String to use to update the field.
     */
    public void setHttpResult(String pHttpResult){
        this.mHttpResult = pHttpResult;
        setDirty(true);
    }
    /**
     * Retrieves the HttpResult field.
     *
     * @return
     *  String containing the HttpResult field.
     */
    public String getHttpResult(){
        return mHttpResult;
    }

    /**
     * Sets the ActionDuration field.
     *
     * @param pActionDuration
     *  java.math.BigDecimal to use to update the field.
     */
    public void setActionDuration(java.math.BigDecimal pActionDuration){
        this.mActionDuration = pActionDuration;
        setDirty(true);
    }
    /**
     * Retrieves the ActionDuration field.
     *
     * @return
     *  java.math.BigDecimal containing the ActionDuration field.
     */
    public java.math.BigDecimal getActionDuration(){
        return mActionDuration;
    }

    /**
     * Sets the HttpDuration field.
     *
     * @param pHttpDuration
     *  java.math.BigDecimal to use to update the field.
     */
    public void setHttpDuration(java.math.BigDecimal pHttpDuration){
        this.mHttpDuration = pHttpDuration;
        setDirty(true);
    }
    /**
     * Retrieves the HttpDuration field.
     *
     * @return
     *  java.math.BigDecimal containing the HttpDuration field.
     */
    public java.math.BigDecimal getHttpDuration(){
        return mHttpDuration;
    }

    /**
     * Sets the Referer field.
     *
     * @param pReferer
     *  String to use to update the field.
     */
    public void setReferer(String pReferer){
        this.mReferer = pReferer;
        setDirty(true);
    }
    /**
     * Retrieves the Referer field.
     *
     * @return
     *  String containing the Referer field.
     */
    public String getReferer(){
        return mReferer;
    }

    /**
     * Sets the Params field.
     *
     * @param pParams
     *  String to use to update the field.
     */
    public void setParams(String pParams){
        this.mParams = pParams;
        setDirty(true);
    }
    /**
     * Retrieves the Params field.
     *
     * @return
     *  String containing the Params field.
     */
    public String getParams(){
        return mParams;
    }

    /**
     * Sets the FinishFile field.
     *
     * @param pFinishFile
     *  String to use to update the field.
     */
    public void setFinishFile(String pFinishFile){
        this.mFinishFile = pFinishFile;
        setDirty(true);
    }
    /**
     * Retrieves the FinishFile field.
     *
     * @return
     *  String containing the FinishFile field.
     */
    public String getFinishFile(){
        return mFinishFile;
    }

    /**
     * Sets the ServerName field.
     *
     * @param pServerName
     *  String to use to update the field.
     */
    public void setServerName(String pServerName){
        this.mServerName = pServerName;
        setDirty(true);
    }
    /**
     * Retrieves the ServerName field.
     *
     * @return
     *  String containing the ServerName field.
     */
    public String getServerName(){
        return mServerName;
    }

    /**
     * Sets the RequestId field.
     *
     * @param pRequestId
     *  String to use to update the field.
     */
    public void setRequestId(String pRequestId){
        this.mRequestId = pRequestId;
        setDirty(true);
    }
    /**
     * Retrieves the RequestId field.
     *
     * @return
     *  String containing the RequestId field.
     */
    public String getRequestId(){
        return mRequestId;
    }

    /**
     * Sets the AddDate field. This field is required to be set in the database.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field. This field is required to be set in the database.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }


}
