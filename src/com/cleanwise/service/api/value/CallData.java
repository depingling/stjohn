
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CallData
 * Description:  This is a ValueObject class wrapping the database table CLW_CALL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CallDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CallData</code> is a ValueObject class wrapping of the database table CLW_CALL.
 */
public class CallData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 7521147909132243044L;
    private int mCallId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mAccountId;// SQL type:NUMBER
    private int mSiteId;// SQL type:NUMBER
    private String mContactName;// SQL type:VARCHAR2
    private String mContactEmailAddress;// SQL type:VARCHAR2
    private String mContactPhoneNumber;// SQL type:VARCHAR2
    private String mCustomerField1;// SQL type:VARCHAR2
    private String mProductName;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private String mComments;// SQL type:VARCHAR2
    private String mCallStatusCd;// SQL type:VARCHAR2
    private String mCallTypeCd;// SQL type:VARCHAR2
    private String mCallSeverityCd;// SQL type:VARCHAR2
    private int mOpenedById;// SQL type:NUMBER, not null
    private int mAssignedToId;// SQL type:NUMBER
    private Date mClosedDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private Date mAddTime;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CallData ()
    {
        mContactName = "";
        mContactEmailAddress = "";
        mContactPhoneNumber = "";
        mCustomerField1 = "";
        mProductName = "";
        mShortDesc = "";
        mLongDesc = "";
        mComments = "";
        mCallStatusCd = "";
        mCallTypeCd = "";
        mCallSeverityCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CallData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, int parm16, int parm17, Date parm18, Date parm19, Date parm20, String parm21, Date parm22, String parm23)
    {
        mCallId = parm1;
        mOrderId = parm2;
        mAccountId = parm3;
        mSiteId = parm4;
        mContactName = parm5;
        mContactEmailAddress = parm6;
        mContactPhoneNumber = parm7;
        mCustomerField1 = parm8;
        mProductName = parm9;
        mShortDesc = parm10;
        mLongDesc = parm11;
        mComments = parm12;
        mCallStatusCd = parm13;
        mCallTypeCd = parm14;
        mCallSeverityCd = parm15;
        mOpenedById = parm16;
        mAssignedToId = parm17;
        mClosedDate = parm18;
        mAddDate = parm19;
        mAddTime = parm20;
        mAddBy = parm21;
        mModDate = parm22;
        mModBy = parm23;
        
    }

    /**
     * Creates a new CallData
     *
     * @return
     *  Newly initialized CallData object.
     */
    public static CallData createValue ()
    {
        CallData valueData = new CallData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CallData object
     */
    public String toString()
    {
        return "[" + "CallId=" + mCallId + ", OrderId=" + mOrderId + ", AccountId=" + mAccountId + ", SiteId=" + mSiteId + ", ContactName=" + mContactName + ", ContactEmailAddress=" + mContactEmailAddress + ", ContactPhoneNumber=" + mContactPhoneNumber + ", CustomerField1=" + mCustomerField1 + ", ProductName=" + mProductName + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", Comments=" + mComments + ", CallStatusCd=" + mCallStatusCd + ", CallTypeCd=" + mCallTypeCd + ", CallSeverityCd=" + mCallSeverityCd + ", OpenedById=" + mOpenedById + ", AssignedToId=" + mAssignedToId + ", ClosedDate=" + mClosedDate + ", AddDate=" + mAddDate + ", AddTime=" + mAddTime + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Call");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCallId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("ContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mContactName)));
        root.appendChild(node);

        node =  doc.createElement("ContactEmailAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mContactEmailAddress)));
        root.appendChild(node);

        node =  doc.createElement("ContactPhoneNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mContactPhoneNumber)));
        root.appendChild(node);

        node =  doc.createElement("CustomerField1");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerField1)));
        root.appendChild(node);

        node =  doc.createElement("ProductName");
        node.appendChild(doc.createTextNode(String.valueOf(mProductName)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
        root.appendChild(node);

        node =  doc.createElement("CallStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCallStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("CallTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCallTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("CallSeverityCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCallSeverityCd)));
        root.appendChild(node);

        node =  doc.createElement("OpenedById");
        node.appendChild(doc.createTextNode(String.valueOf(mOpenedById)));
        root.appendChild(node);

        node =  doc.createElement("AssignedToId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssignedToId)));
        root.appendChild(node);

        node =  doc.createElement("ClosedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mClosedDate)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddTime");
        node.appendChild(doc.createTextNode(String.valueOf(mAddTime)));
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
    * creates a clone of this object, the CallId field is not cloned.
    *
    * @return CallData object
    */
    public Object clone(){
        CallData myClone = new CallData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mAccountId = mAccountId;
        
        myClone.mSiteId = mSiteId;
        
        myClone.mContactName = mContactName;
        
        myClone.mContactEmailAddress = mContactEmailAddress;
        
        myClone.mContactPhoneNumber = mContactPhoneNumber;
        
        myClone.mCustomerField1 = mCustomerField1;
        
        myClone.mProductName = mProductName;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mComments = mComments;
        
        myClone.mCallStatusCd = mCallStatusCd;
        
        myClone.mCallTypeCd = mCallTypeCd;
        
        myClone.mCallSeverityCd = mCallSeverityCd;
        
        myClone.mOpenedById = mOpenedById;
        
        myClone.mAssignedToId = mAssignedToId;
        
        if(mClosedDate != null){
                myClone.mClosedDate = (Date) mClosedDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        if(mAddTime != null){
                myClone.mAddTime = (Date) mAddTime.clone();
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

        if (CallDataAccess.CALL_ID.equals(pFieldName)) {
            return getCallId();
        } else if (CallDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (CallDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (CallDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (CallDataAccess.CONTACT_NAME.equals(pFieldName)) {
            return getContactName();
        } else if (CallDataAccess.CONTACT_EMAIL_ADDRESS.equals(pFieldName)) {
            return getContactEmailAddress();
        } else if (CallDataAccess.CONTACT_PHONE_NUMBER.equals(pFieldName)) {
            return getContactPhoneNumber();
        } else if (CallDataAccess.CUSTOMER_FIELD_1.equals(pFieldName)) {
            return getCustomerField1();
        } else if (CallDataAccess.PRODUCT_NAME.equals(pFieldName)) {
            return getProductName();
        } else if (CallDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CallDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (CallDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (CallDataAccess.CALL_STATUS_CD.equals(pFieldName)) {
            return getCallStatusCd();
        } else if (CallDataAccess.CALL_TYPE_CD.equals(pFieldName)) {
            return getCallTypeCd();
        } else if (CallDataAccess.CALL_SEVERITY_CD.equals(pFieldName)) {
            return getCallSeverityCd();
        } else if (CallDataAccess.OPENED_BY_ID.equals(pFieldName)) {
            return getOpenedById();
        } else if (CallDataAccess.ASSIGNED_TO_ID.equals(pFieldName)) {
            return getAssignedToId();
        } else if (CallDataAccess.CLOSED_DATE.equals(pFieldName)) {
            return getClosedDate();
        } else if (CallDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CallDataAccess.ADD_TIME.equals(pFieldName)) {
            return getAddTime();
        } else if (CallDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CallDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CallDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CallDataAccess.CLW_CALL;
    }

    
    /**
     * Sets the CallId field. This field is required to be set in the database.
     *
     * @param pCallId
     *  int to use to update the field.
     */
    public void setCallId(int pCallId){
        this.mCallId = pCallId;
        setDirty(true);
    }
    /**
     * Retrieves the CallId field.
     *
     * @return
     *  int containing the CallId field.
     */
    public int getCallId(){
        return mCallId;
    }

    /**
     * Sets the OrderId field.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    /**
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the SiteId field.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
    }

    /**
     * Sets the ContactName field.
     *
     * @param pContactName
     *  String to use to update the field.
     */
    public void setContactName(String pContactName){
        this.mContactName = pContactName;
        setDirty(true);
    }
    /**
     * Retrieves the ContactName field.
     *
     * @return
     *  String containing the ContactName field.
     */
    public String getContactName(){
        return mContactName;
    }

    /**
     * Sets the ContactEmailAddress field.
     *
     * @param pContactEmailAddress
     *  String to use to update the field.
     */
    public void setContactEmailAddress(String pContactEmailAddress){
        this.mContactEmailAddress = pContactEmailAddress;
        setDirty(true);
    }
    /**
     * Retrieves the ContactEmailAddress field.
     *
     * @return
     *  String containing the ContactEmailAddress field.
     */
    public String getContactEmailAddress(){
        return mContactEmailAddress;
    }

    /**
     * Sets the ContactPhoneNumber field.
     *
     * @param pContactPhoneNumber
     *  String to use to update the field.
     */
    public void setContactPhoneNumber(String pContactPhoneNumber){
        this.mContactPhoneNumber = pContactPhoneNumber;
        setDirty(true);
    }
    /**
     * Retrieves the ContactPhoneNumber field.
     *
     * @return
     *  String containing the ContactPhoneNumber field.
     */
    public String getContactPhoneNumber(){
        return mContactPhoneNumber;
    }

    /**
     * Sets the CustomerField1 field.
     *
     * @param pCustomerField1
     *  String to use to update the field.
     */
    public void setCustomerField1(String pCustomerField1){
        this.mCustomerField1 = pCustomerField1;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerField1 field.
     *
     * @return
     *  String containing the CustomerField1 field.
     */
    public String getCustomerField1(){
        return mCustomerField1;
    }

    /**
     * Sets the ProductName field.
     *
     * @param pProductName
     *  String to use to update the field.
     */
    public void setProductName(String pProductName){
        this.mProductName = pProductName;
        setDirty(true);
    }
    /**
     * Retrieves the ProductName field.
     *
     * @return
     *  String containing the ProductName field.
     */
    public String getProductName(){
        return mProductName;
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
     * Sets the LongDesc field.
     *
     * @param pLongDesc
     *  String to use to update the field.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
        setDirty(true);
    }
    /**
     * Retrieves the LongDesc field.
     *
     * @return
     *  String containing the LongDesc field.
     */
    public String getLongDesc(){
        return mLongDesc;
    }

    /**
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
        setDirty(true);
    }
    /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
    }

    /**
     * Sets the CallStatusCd field.
     *
     * @param pCallStatusCd
     *  String to use to update the field.
     */
    public void setCallStatusCd(String pCallStatusCd){
        this.mCallStatusCd = pCallStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the CallStatusCd field.
     *
     * @return
     *  String containing the CallStatusCd field.
     */
    public String getCallStatusCd(){
        return mCallStatusCd;
    }

    /**
     * Sets the CallTypeCd field.
     *
     * @param pCallTypeCd
     *  String to use to update the field.
     */
    public void setCallTypeCd(String pCallTypeCd){
        this.mCallTypeCd = pCallTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the CallTypeCd field.
     *
     * @return
     *  String containing the CallTypeCd field.
     */
    public String getCallTypeCd(){
        return mCallTypeCd;
    }

    /**
     * Sets the CallSeverityCd field.
     *
     * @param pCallSeverityCd
     *  String to use to update the field.
     */
    public void setCallSeverityCd(String pCallSeverityCd){
        this.mCallSeverityCd = pCallSeverityCd;
        setDirty(true);
    }
    /**
     * Retrieves the CallSeverityCd field.
     *
     * @return
     *  String containing the CallSeverityCd field.
     */
    public String getCallSeverityCd(){
        return mCallSeverityCd;
    }

    /**
     * Sets the OpenedById field. This field is required to be set in the database.
     *
     * @param pOpenedById
     *  int to use to update the field.
     */
    public void setOpenedById(int pOpenedById){
        this.mOpenedById = pOpenedById;
        setDirty(true);
    }
    /**
     * Retrieves the OpenedById field.
     *
     * @return
     *  int containing the OpenedById field.
     */
    public int getOpenedById(){
        return mOpenedById;
    }

    /**
     * Sets the AssignedToId field.
     *
     * @param pAssignedToId
     *  int to use to update the field.
     */
    public void setAssignedToId(int pAssignedToId){
        this.mAssignedToId = pAssignedToId;
        setDirty(true);
    }
    /**
     * Retrieves the AssignedToId field.
     *
     * @return
     *  int containing the AssignedToId field.
     */
    public int getAssignedToId(){
        return mAssignedToId;
    }

    /**
     * Sets the ClosedDate field.
     *
     * @param pClosedDate
     *  Date to use to update the field.
     */
    public void setClosedDate(Date pClosedDate){
        this.mClosedDate = pClosedDate;
        setDirty(true);
    }
    /**
     * Retrieves the ClosedDate field.
     *
     * @return
     *  Date containing the ClosedDate field.
     */
    public Date getClosedDate(){
        return mClosedDate;
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
     * Sets the AddTime field. This field is required to be set in the database.
     *
     * @param pAddTime
     *  Date to use to update the field.
     */
    public void setAddTime(Date pAddTime){
        this.mAddTime = pAddTime;
        setDirty(true);
    }
    /**
     * Retrieves the AddTime field.
     *
     * @return
     *  Date containing the AddTime field.
     */
    public Date getAddTime(){
        return mAddTime;
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
