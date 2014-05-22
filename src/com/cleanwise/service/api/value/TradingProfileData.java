
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingProfileData
 * Description:  This is a ValueObject class wrapping the database table CLW_TRADING_PROFILE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TradingProfileDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TradingProfileData</code> is a ValueObject class wrapping of the database table CLW_TRADING_PROFILE.
 */
public class TradingProfileData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -578088493060809213L;
    private int mTradingProfileId;// SQL type:NUMBER, not null
    private int mTradingPartnerId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mAuthorizationQualifier;// SQL type:VARCHAR2, not null
    private String mAuthorization;// SQL type:VARCHAR2
    private String mSecurityInfoQualifier;// SQL type:VARCHAR2, not null
    private String mSecurityInfo;// SQL type:VARCHAR2
    private String mInterchangeSenderQualifier;// SQL type:VARCHAR2, not null
    private String mInterchangeSender;// SQL type:VARCHAR2
    private String mInterchangeReceiverQualifier;// SQL type:VARCHAR2, not null
    private String mInterchangeReceiver;// SQL type:VARCHAR2
    private String mInterchangeStandardsId;// SQL type:VARCHAR2, not null
    private String mInterchangeVersionNum;// SQL type:VARCHAR2, not null
    private int mInterchangeControlNum;// SQL type:NUMBER, not null
    private String mAcknowledgmentRequested;// SQL type:VARCHAR2, not null
    private String mTestIndicator;// SQL type:VARCHAR2, not null
    private String mSegmentTerminator;// SQL type:VARCHAR2
    private String mElementTerminator;// SQL type:VARCHAR2, not null
    private String mSubElementTerminator;// SQL type:VARCHAR2, not null
    private String mGroupSender;// SQL type:VARCHAR2
    private String mGroupReceiver;// SQL type:VARCHAR2
    private int mGroupControlNum;// SQL type:NUMBER, not null
    private String mResponsibleAgencyCode;// SQL type:VARCHAR2, not null
    private String mVersionNum;// SQL type:VARCHAR2, not null
    private String mTimeZone;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TradingProfileData ()
    {
        mShortDesc = "";
        mAuthorizationQualifier = "";
        mAuthorization = "";
        mSecurityInfoQualifier = "";
        mSecurityInfo = "";
        mInterchangeSenderQualifier = "";
        mInterchangeSender = "";
        mInterchangeReceiverQualifier = "";
        mInterchangeReceiver = "";
        mInterchangeStandardsId = "";
        mInterchangeVersionNum = "";
        mAcknowledgmentRequested = "";
        mTestIndicator = "";
        mSegmentTerminator = "";
        mElementTerminator = "";
        mSubElementTerminator = "";
        mGroupSender = "";
        mGroupReceiver = "";
        mResponsibleAgencyCode = "";
        mVersionNum = "";
        mTimeZone = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public TradingProfileData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, int parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, int parm22, String parm23, String parm24, String parm25, Date parm26, String parm27, Date parm28, String parm29)
    {
        mTradingProfileId = parm1;
        mTradingPartnerId = parm2;
        mShortDesc = parm3;
        mAuthorizationQualifier = parm4;
        mAuthorization = parm5;
        mSecurityInfoQualifier = parm6;
        mSecurityInfo = parm7;
        mInterchangeSenderQualifier = parm8;
        mInterchangeSender = parm9;
        mInterchangeReceiverQualifier = parm10;
        mInterchangeReceiver = parm11;
        mInterchangeStandardsId = parm12;
        mInterchangeVersionNum = parm13;
        mInterchangeControlNum = parm14;
        mAcknowledgmentRequested = parm15;
        mTestIndicator = parm16;
        mSegmentTerminator = parm17;
        mElementTerminator = parm18;
        mSubElementTerminator = parm19;
        mGroupSender = parm20;
        mGroupReceiver = parm21;
        mGroupControlNum = parm22;
        mResponsibleAgencyCode = parm23;
        mVersionNum = parm24;
        mTimeZone = parm25;
        mAddDate = parm26;
        mAddBy = parm27;
        mModDate = parm28;
        mModBy = parm29;
        
    }

    /**
     * Creates a new TradingProfileData
     *
     * @return
     *  Newly initialized TradingProfileData object.
     */
    public static TradingProfileData createValue ()
    {
        TradingProfileData valueData = new TradingProfileData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingProfileData object
     */
    public String toString()
    {
        return "[" + "TradingProfileId=" + mTradingProfileId + ", TradingPartnerId=" + mTradingPartnerId + ", ShortDesc=" + mShortDesc + ", AuthorizationQualifier=" + mAuthorizationQualifier + ", Authorization=" + mAuthorization + ", SecurityInfoQualifier=" + mSecurityInfoQualifier + ", SecurityInfo=" + mSecurityInfo + ", InterchangeSenderQualifier=" + mInterchangeSenderQualifier + ", InterchangeSender=" + mInterchangeSender + ", InterchangeReceiverQualifier=" + mInterchangeReceiverQualifier + ", InterchangeReceiver=" + mInterchangeReceiver + ", InterchangeStandardsId=" + mInterchangeStandardsId + ", InterchangeVersionNum=" + mInterchangeVersionNum + ", InterchangeControlNum=" + mInterchangeControlNum + ", AcknowledgmentRequested=" + mAcknowledgmentRequested + ", TestIndicator=" + mTestIndicator + ", SegmentTerminator=" + mSegmentTerminator + ", ElementTerminator=" + mElementTerminator + ", SubElementTerminator=" + mSubElementTerminator + ", GroupSender=" + mGroupSender + ", GroupReceiver=" + mGroupReceiver + ", GroupControlNum=" + mGroupControlNum + ", ResponsibleAgencyCode=" + mResponsibleAgencyCode + ", VersionNum=" + mVersionNum + ", TimeZone=" + mTimeZone + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TradingProfile");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTradingProfileId));

        node =  doc.createElement("TradingPartnerId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPartnerId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("AuthorizationQualifier");
        node.appendChild(doc.createTextNode(String.valueOf(mAuthorizationQualifier)));
        root.appendChild(node);

        node =  doc.createElement("Authorization");
        node.appendChild(doc.createTextNode(String.valueOf(mAuthorization)));
        root.appendChild(node);

        node =  doc.createElement("SecurityInfoQualifier");
        node.appendChild(doc.createTextNode(String.valueOf(mSecurityInfoQualifier)));
        root.appendChild(node);

        node =  doc.createElement("SecurityInfo");
        node.appendChild(doc.createTextNode(String.valueOf(mSecurityInfo)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeSenderQualifier");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeSenderQualifier)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeSender");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeSender)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeReceiverQualifier");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeReceiverQualifier)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeReceiver");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeReceiver)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeStandardsId");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeStandardsId)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeVersionNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeVersionNum)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeControlNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeControlNum)));
        root.appendChild(node);

        node =  doc.createElement("AcknowledgmentRequested");
        node.appendChild(doc.createTextNode(String.valueOf(mAcknowledgmentRequested)));
        root.appendChild(node);

        node =  doc.createElement("TestIndicator");
        node.appendChild(doc.createTextNode(String.valueOf(mTestIndicator)));
        root.appendChild(node);

        node =  doc.createElement("SegmentTerminator");
        node.appendChild(doc.createTextNode(String.valueOf(mSegmentTerminator)));
        root.appendChild(node);

        node =  doc.createElement("ElementTerminator");
        node.appendChild(doc.createTextNode(String.valueOf(mElementTerminator)));
        root.appendChild(node);

        node =  doc.createElement("SubElementTerminator");
        node.appendChild(doc.createTextNode(String.valueOf(mSubElementTerminator)));
        root.appendChild(node);

        node =  doc.createElement("GroupSender");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupSender)));
        root.appendChild(node);

        node =  doc.createElement("GroupReceiver");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupReceiver)));
        root.appendChild(node);

        node =  doc.createElement("GroupControlNum");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupControlNum)));
        root.appendChild(node);

        node =  doc.createElement("ResponsibleAgencyCode");
        node.appendChild(doc.createTextNode(String.valueOf(mResponsibleAgencyCode)));
        root.appendChild(node);

        node =  doc.createElement("VersionNum");
        node.appendChild(doc.createTextNode(String.valueOf(mVersionNum)));
        root.appendChild(node);

        node =  doc.createElement("TimeZone");
        node.appendChild(doc.createTextNode(String.valueOf(mTimeZone)));
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
    * creates a clone of this object, the TradingProfileId field is not cloned.
    *
    * @return TradingProfileData object
    */
    public Object clone(){
        TradingProfileData myClone = new TradingProfileData();
        
        myClone.mTradingPartnerId = mTradingPartnerId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mAuthorizationQualifier = mAuthorizationQualifier;
        
        myClone.mAuthorization = mAuthorization;
        
        myClone.mSecurityInfoQualifier = mSecurityInfoQualifier;
        
        myClone.mSecurityInfo = mSecurityInfo;
        
        myClone.mInterchangeSenderQualifier = mInterchangeSenderQualifier;
        
        myClone.mInterchangeSender = mInterchangeSender;
        
        myClone.mInterchangeReceiverQualifier = mInterchangeReceiverQualifier;
        
        myClone.mInterchangeReceiver = mInterchangeReceiver;
        
        myClone.mInterchangeStandardsId = mInterchangeStandardsId;
        
        myClone.mInterchangeVersionNum = mInterchangeVersionNum;
        
        myClone.mInterchangeControlNum = mInterchangeControlNum;
        
        myClone.mAcknowledgmentRequested = mAcknowledgmentRequested;
        
        myClone.mTestIndicator = mTestIndicator;
        
        myClone.mSegmentTerminator = mSegmentTerminator;
        
        myClone.mElementTerminator = mElementTerminator;
        
        myClone.mSubElementTerminator = mSubElementTerminator;
        
        myClone.mGroupSender = mGroupSender;
        
        myClone.mGroupReceiver = mGroupReceiver;
        
        myClone.mGroupControlNum = mGroupControlNum;
        
        myClone.mResponsibleAgencyCode = mResponsibleAgencyCode;
        
        myClone.mVersionNum = mVersionNum;
        
        myClone.mTimeZone = mTimeZone;
        
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

        if (TradingProfileDataAccess.TRADING_PROFILE_ID.equals(pFieldName)) {
            return getTradingProfileId();
        } else if (TradingProfileDataAccess.TRADING_PARTNER_ID.equals(pFieldName)) {
            return getTradingPartnerId();
        } else if (TradingProfileDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (TradingProfileDataAccess.AUTHORIZATION_QUALIFIER.equals(pFieldName)) {
            return getAuthorizationQualifier();
        } else if (TradingProfileDataAccess.AUTHORIZATION_.equals(pFieldName)) {
            return getAuthorization();
        } else if (TradingProfileDataAccess.SECURITY_INFO_QUALIFIER.equals(pFieldName)) {
            return getSecurityInfoQualifier();
        } else if (TradingProfileDataAccess.SECURITY_INFO.equals(pFieldName)) {
            return getSecurityInfo();
        } else if (TradingProfileDataAccess.INTERCHANGE_SENDER_QUALIFIER.equals(pFieldName)) {
            return getInterchangeSenderQualifier();
        } else if (TradingProfileDataAccess.INTERCHANGE_SENDER.equals(pFieldName)) {
            return getInterchangeSender();
        } else if (TradingProfileDataAccess.INTERCHANGE_RECEIVER_QUALIFIER.equals(pFieldName)) {
            return getInterchangeReceiverQualifier();
        } else if (TradingProfileDataAccess.INTERCHANGE_RECEIVER.equals(pFieldName)) {
            return getInterchangeReceiver();
        } else if (TradingProfileDataAccess.INTERCHANGE_STANDARDS_ID.equals(pFieldName)) {
            return getInterchangeStandardsId();
        } else if (TradingProfileDataAccess.INTERCHANGE_VERSION_NUM.equals(pFieldName)) {
            return getInterchangeVersionNum();
        } else if (TradingProfileDataAccess.INTERCHANGE_CONTROL_NUM.equals(pFieldName)) {
            return getInterchangeControlNum();
        } else if (TradingProfileDataAccess.ACKNOWLEDGMENT_REQUESTED.equals(pFieldName)) {
            return getAcknowledgmentRequested();
        } else if (TradingProfileDataAccess.TEST_INDICATOR.equals(pFieldName)) {
            return getTestIndicator();
        } else if (TradingProfileDataAccess.SEGMENT_TERMINATOR.equals(pFieldName)) {
            return getSegmentTerminator();
        } else if (TradingProfileDataAccess.ELEMENT_TERMINATOR.equals(pFieldName)) {
            return getElementTerminator();
        } else if (TradingProfileDataAccess.SUB_ELEMENT_TERMINATOR.equals(pFieldName)) {
            return getSubElementTerminator();
        } else if (TradingProfileDataAccess.GROUP_SENDER.equals(pFieldName)) {
            return getGroupSender();
        } else if (TradingProfileDataAccess.GROUP_RECEIVER.equals(pFieldName)) {
            return getGroupReceiver();
        } else if (TradingProfileDataAccess.GROUP_CONTROL_NUM.equals(pFieldName)) {
            return getGroupControlNum();
        } else if (TradingProfileDataAccess.RESPONSIBLE_AGENCY_CODE.equals(pFieldName)) {
            return getResponsibleAgencyCode();
        } else if (TradingProfileDataAccess.VERSION_NUM.equals(pFieldName)) {
            return getVersionNum();
        } else if (TradingProfileDataAccess.TIME_ZONE.equals(pFieldName)) {
            return getTimeZone();
        } else if (TradingProfileDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TradingProfileDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TradingProfileDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TradingProfileDataAccess.MOD_BY.equals(pFieldName)) {
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
        return TradingProfileDataAccess.CLW_TRADING_PROFILE;
    }

    
    /**
     * Sets the TradingProfileId field. This field is required to be set in the database.
     *
     * @param pTradingProfileId
     *  int to use to update the field.
     */
    public void setTradingProfileId(int pTradingProfileId){
        this.mTradingProfileId = pTradingProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingProfileId field.
     *
     * @return
     *  int containing the TradingProfileId field.
     */
    public int getTradingProfileId(){
        return mTradingProfileId;
    }

    /**
     * Sets the TradingPartnerId field. This field is required to be set in the database.
     *
     * @param pTradingPartnerId
     *  int to use to update the field.
     */
    public void setTradingPartnerId(int pTradingPartnerId){
        this.mTradingPartnerId = pTradingPartnerId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPartnerId field.
     *
     * @return
     *  int containing the TradingPartnerId field.
     */
    public int getTradingPartnerId(){
        return mTradingPartnerId;
    }

    /**
     * Sets the ShortDesc field.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
    }

    /**
     * Sets the AuthorizationQualifier field. This field is required to be set in the database.
     *
     * @param pAuthorizationQualifier
     *  String to use to update the field.
     */
    public void setAuthorizationQualifier(String pAuthorizationQualifier){
        this.mAuthorizationQualifier = pAuthorizationQualifier;
        setDirty(true);
    }
    /**
     * Retrieves the AuthorizationQualifier field.
     *
     * @return
     *  String containing the AuthorizationQualifier field.
     */
    public String getAuthorizationQualifier(){
        return mAuthorizationQualifier;
    }

    /**
     * Sets the Authorization field.
     *
     * @param pAuthorization
     *  String to use to update the field.
     */
    public void setAuthorization(String pAuthorization){
        this.mAuthorization = pAuthorization;
        setDirty(true);
    }
    /**
     * Retrieves the Authorization field.
     *
     * @return
     *  String containing the Authorization field.
     */
    public String getAuthorization(){
        return mAuthorization;
    }

    /**
     * Sets the SecurityInfoQualifier field. This field is required to be set in the database.
     *
     * @param pSecurityInfoQualifier
     *  String to use to update the field.
     */
    public void setSecurityInfoQualifier(String pSecurityInfoQualifier){
        this.mSecurityInfoQualifier = pSecurityInfoQualifier;
        setDirty(true);
    }
    /**
     * Retrieves the SecurityInfoQualifier field.
     *
     * @return
     *  String containing the SecurityInfoQualifier field.
     */
    public String getSecurityInfoQualifier(){
        return mSecurityInfoQualifier;
    }

    /**
     * Sets the SecurityInfo field.
     *
     * @param pSecurityInfo
     *  String to use to update the field.
     */
    public void setSecurityInfo(String pSecurityInfo){
        this.mSecurityInfo = pSecurityInfo;
        setDirty(true);
    }
    /**
     * Retrieves the SecurityInfo field.
     *
     * @return
     *  String containing the SecurityInfo field.
     */
    public String getSecurityInfo(){
        return mSecurityInfo;
    }

    /**
     * Sets the InterchangeSenderQualifier field. This field is required to be set in the database.
     *
     * @param pInterchangeSenderQualifier
     *  String to use to update the field.
     */
    public void setInterchangeSenderQualifier(String pInterchangeSenderQualifier){
        this.mInterchangeSenderQualifier = pInterchangeSenderQualifier;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeSenderQualifier field.
     *
     * @return
     *  String containing the InterchangeSenderQualifier field.
     */
    public String getInterchangeSenderQualifier(){
        return mInterchangeSenderQualifier;
    }

    /**
     * Sets the InterchangeSender field.
     *
     * @param pInterchangeSender
     *  String to use to update the field.
     */
    public void setInterchangeSender(String pInterchangeSender){
        this.mInterchangeSender = pInterchangeSender;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeSender field.
     *
     * @return
     *  String containing the InterchangeSender field.
     */
    public String getInterchangeSender(){
        return mInterchangeSender;
    }

    /**
     * Sets the InterchangeReceiverQualifier field. This field is required to be set in the database.
     *
     * @param pInterchangeReceiverQualifier
     *  String to use to update the field.
     */
    public void setInterchangeReceiverQualifier(String pInterchangeReceiverQualifier){
        this.mInterchangeReceiverQualifier = pInterchangeReceiverQualifier;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeReceiverQualifier field.
     *
     * @return
     *  String containing the InterchangeReceiverQualifier field.
     */
    public String getInterchangeReceiverQualifier(){
        return mInterchangeReceiverQualifier;
    }

    /**
     * Sets the InterchangeReceiver field.
     *
     * @param pInterchangeReceiver
     *  String to use to update the field.
     */
    public void setInterchangeReceiver(String pInterchangeReceiver){
        this.mInterchangeReceiver = pInterchangeReceiver;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeReceiver field.
     *
     * @return
     *  String containing the InterchangeReceiver field.
     */
    public String getInterchangeReceiver(){
        return mInterchangeReceiver;
    }

    /**
     * Sets the InterchangeStandardsId field. This field is required to be set in the database.
     *
     * @param pInterchangeStandardsId
     *  String to use to update the field.
     */
    public void setInterchangeStandardsId(String pInterchangeStandardsId){
        this.mInterchangeStandardsId = pInterchangeStandardsId;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeStandardsId field.
     *
     * @return
     *  String containing the InterchangeStandardsId field.
     */
    public String getInterchangeStandardsId(){
        return mInterchangeStandardsId;
    }

    /**
     * Sets the InterchangeVersionNum field. This field is required to be set in the database.
     *
     * @param pInterchangeVersionNum
     *  String to use to update the field.
     */
    public void setInterchangeVersionNum(String pInterchangeVersionNum){
        this.mInterchangeVersionNum = pInterchangeVersionNum;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeVersionNum field.
     *
     * @return
     *  String containing the InterchangeVersionNum field.
     */
    public String getInterchangeVersionNum(){
        return mInterchangeVersionNum;
    }

    /**
     * Sets the InterchangeControlNum field. This field is required to be set in the database.
     *
     * @param pInterchangeControlNum
     *  int to use to update the field.
     */
    public void setInterchangeControlNum(int pInterchangeControlNum){
        this.mInterchangeControlNum = pInterchangeControlNum;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeControlNum field.
     *
     * @return
     *  int containing the InterchangeControlNum field.
     */
    public int getInterchangeControlNum(){
        return mInterchangeControlNum;
    }

    /**
     * Sets the AcknowledgmentRequested field. This field is required to be set in the database.
     *
     * @param pAcknowledgmentRequested
     *  String to use to update the field.
     */
    public void setAcknowledgmentRequested(String pAcknowledgmentRequested){
        this.mAcknowledgmentRequested = pAcknowledgmentRequested;
        setDirty(true);
    }
    /**
     * Retrieves the AcknowledgmentRequested field.
     *
     * @return
     *  String containing the AcknowledgmentRequested field.
     */
    public String getAcknowledgmentRequested(){
        return mAcknowledgmentRequested;
    }

    /**
     * Sets the TestIndicator field. This field is required to be set in the database.
     *
     * @param pTestIndicator
     *  String to use to update the field.
     */
    public void setTestIndicator(String pTestIndicator){
        this.mTestIndicator = pTestIndicator;
        setDirty(true);
    }
    /**
     * Retrieves the TestIndicator field.
     *
     * @return
     *  String containing the TestIndicator field.
     */
    public String getTestIndicator(){
        return mTestIndicator;
    }

    /**
     * Sets the SegmentTerminator field.
     *
     * @param pSegmentTerminator
     *  String to use to update the field.
     */
    public void setSegmentTerminator(String pSegmentTerminator){
        this.mSegmentTerminator = pSegmentTerminator;
        setDirty(true);
    }
    /**
     * Retrieves the SegmentTerminator field.
     *
     * @return
     *  String containing the SegmentTerminator field.
     */
    public String getSegmentTerminator(){
        return mSegmentTerminator;
    }

    /**
     * Sets the ElementTerminator field. This field is required to be set in the database.
     *
     * @param pElementTerminator
     *  String to use to update the field.
     */
    public void setElementTerminator(String pElementTerminator){
        this.mElementTerminator = pElementTerminator;
        setDirty(true);
    }
    /**
     * Retrieves the ElementTerminator field.
     *
     * @return
     *  String containing the ElementTerminator field.
     */
    public String getElementTerminator(){
        return mElementTerminator;
    }

    /**
     * Sets the SubElementTerminator field. This field is required to be set in the database.
     *
     * @param pSubElementTerminator
     *  String to use to update the field.
     */
    public void setSubElementTerminator(String pSubElementTerminator){
        this.mSubElementTerminator = pSubElementTerminator;
        setDirty(true);
    }
    /**
     * Retrieves the SubElementTerminator field.
     *
     * @return
     *  String containing the SubElementTerminator field.
     */
    public String getSubElementTerminator(){
        return mSubElementTerminator;
    }

    /**
     * Sets the GroupSender field.
     *
     * @param pGroupSender
     *  String to use to update the field.
     */
    public void setGroupSender(String pGroupSender){
        this.mGroupSender = pGroupSender;
        setDirty(true);
    }
    /**
     * Retrieves the GroupSender field.
     *
     * @return
     *  String containing the GroupSender field.
     */
    public String getGroupSender(){
        return mGroupSender;
    }

    /**
     * Sets the GroupReceiver field.
     *
     * @param pGroupReceiver
     *  String to use to update the field.
     */
    public void setGroupReceiver(String pGroupReceiver){
        this.mGroupReceiver = pGroupReceiver;
        setDirty(true);
    }
    /**
     * Retrieves the GroupReceiver field.
     *
     * @return
     *  String containing the GroupReceiver field.
     */
    public String getGroupReceiver(){
        return mGroupReceiver;
    }

    /**
     * Sets the GroupControlNum field. This field is required to be set in the database.
     *
     * @param pGroupControlNum
     *  int to use to update the field.
     */
    public void setGroupControlNum(int pGroupControlNum){
        this.mGroupControlNum = pGroupControlNum;
        setDirty(true);
    }
    /**
     * Retrieves the GroupControlNum field.
     *
     * @return
     *  int containing the GroupControlNum field.
     */
    public int getGroupControlNum(){
        return mGroupControlNum;
    }

    /**
     * Sets the ResponsibleAgencyCode field. This field is required to be set in the database.
     *
     * @param pResponsibleAgencyCode
     *  String to use to update the field.
     */
    public void setResponsibleAgencyCode(String pResponsibleAgencyCode){
        this.mResponsibleAgencyCode = pResponsibleAgencyCode;
        setDirty(true);
    }
    /**
     * Retrieves the ResponsibleAgencyCode field.
     *
     * @return
     *  String containing the ResponsibleAgencyCode field.
     */
    public String getResponsibleAgencyCode(){
        return mResponsibleAgencyCode;
    }

    /**
     * Sets the VersionNum field. This field is required to be set in the database.
     *
     * @param pVersionNum
     *  String to use to update the field.
     */
    public void setVersionNum(String pVersionNum){
        this.mVersionNum = pVersionNum;
        setDirty(true);
    }
    /**
     * Retrieves the VersionNum field.
     *
     * @return
     *  String containing the VersionNum field.
     */
    public String getVersionNum(){
        return mVersionNum;
    }

    /**
     * Sets the TimeZone field. This field is required to be set in the database.
     *
     * @param pTimeZone
     *  String to use to update the field.
     */
    public void setTimeZone(String pTimeZone){
        this.mTimeZone = pTimeZone;
        setDirty(true);
    }
    /**
     * Retrieves the TimeZone field.
     *
     * @return
     *  String containing the TimeZone field.
     */
    public String getTimeZone(){
        return mTimeZone;
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
