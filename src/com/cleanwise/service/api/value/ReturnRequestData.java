
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReturnRequestData
 * Description:  This is a ValueObject class wrapping the database table CLW_RETURN_REQUEST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ReturnRequestDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ReturnRequestData</code> is a ValueObject class wrapping of the database table CLW_RETURN_REQUEST.
 */
public class ReturnRequestData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1587972828970622363L;
    private int mReturnRequestId;// SQL type:NUMBER, not null
    private String mSenderContactName;// SQL type:VARCHAR2
    private String mSenderContactPhone;// SQL type:VARCHAR2
    private String mReason;// SQL type:VARCHAR2
    private String mDistributorInvoiceNumber;// SQL type:VARCHAR2
    private String mDistributorRefNum;// SQL type:VARCHAR2
    private String mReturnRequestRefNum;// SQL type:VARCHAR2
    private int mPurchaseOrderId;// SQL type:NUMBER
    private String mReturnRequestStatus;// SQL type:VARCHAR2
    private String mPickupContactName;// SQL type:VARCHAR2
    private Date mDateRequestRecieved;// SQL type:DATE
    private int mInvoiceDistId;// SQL type:NUMBER
    private String mNotesToDistributor;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mReturnMethod;// SQL type:VARCHAR2
    private String mProblem;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ReturnRequestData ()
    {
        mSenderContactName = "";
        mSenderContactPhone = "";
        mReason = "";
        mDistributorInvoiceNumber = "";
        mDistributorRefNum = "";
        mReturnRequestRefNum = "";
        mReturnRequestStatus = "";
        mPickupContactName = "";
        mNotesToDistributor = "";
        mAddBy = "";
        mModBy = "";
        mReturnMethod = "";
        mProblem = "";
    }

    /**
     * Constructor.
     */
    public ReturnRequestData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, int parm8, String parm9, String parm10, Date parm11, int parm12, String parm13, String parm14, Date parm15, String parm16, Date parm17, String parm18, String parm19)
    {
        mReturnRequestId = parm1;
        mSenderContactName = parm2;
        mSenderContactPhone = parm3;
        mReason = parm4;
        mDistributorInvoiceNumber = parm5;
        mDistributorRefNum = parm6;
        mReturnRequestRefNum = parm7;
        mPurchaseOrderId = parm8;
        mReturnRequestStatus = parm9;
        mPickupContactName = parm10;
        mDateRequestRecieved = parm11;
        mInvoiceDistId = parm12;
        mNotesToDistributor = parm13;
        mAddBy = parm14;
        mAddDate = parm15;
        mModBy = parm16;
        mModDate = parm17;
        mReturnMethod = parm18;
        mProblem = parm19;
        
    }

    /**
     * Creates a new ReturnRequestData
     *
     * @return
     *  Newly initialized ReturnRequestData object.
     */
    public static ReturnRequestData createValue ()
    {
        ReturnRequestData valueData = new ReturnRequestData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReturnRequestData object
     */
    public String toString()
    {
        return "[" + "ReturnRequestId=" + mReturnRequestId + ", SenderContactName=" + mSenderContactName + ", SenderContactPhone=" + mSenderContactPhone + ", Reason=" + mReason + ", DistributorInvoiceNumber=" + mDistributorInvoiceNumber + ", DistributorRefNum=" + mDistributorRefNum + ", ReturnRequestRefNum=" + mReturnRequestRefNum + ", PurchaseOrderId=" + mPurchaseOrderId + ", ReturnRequestStatus=" + mReturnRequestStatus + ", PickupContactName=" + mPickupContactName + ", DateRequestRecieved=" + mDateRequestRecieved + ", InvoiceDistId=" + mInvoiceDistId + ", NotesToDistributor=" + mNotesToDistributor + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", ReturnMethod=" + mReturnMethod + ", Problem=" + mProblem + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ReturnRequest");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mReturnRequestId));

        node =  doc.createElement("SenderContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mSenderContactName)));
        root.appendChild(node);

        node =  doc.createElement("SenderContactPhone");
        node.appendChild(doc.createTextNode(String.valueOf(mSenderContactPhone)));
        root.appendChild(node);

        node =  doc.createElement("Reason");
        node.appendChild(doc.createTextNode(String.valueOf(mReason)));
        root.appendChild(node);

        node =  doc.createElement("DistributorInvoiceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorInvoiceNumber)));
        root.appendChild(node);

        node =  doc.createElement("DistributorRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorRefNum)));
        root.appendChild(node);

        node =  doc.createElement("ReturnRequestRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestRefNum)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ReturnRequestStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestStatus)));
        root.appendChild(node);

        node =  doc.createElement("PickupContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mPickupContactName)));
        root.appendChild(node);

        node =  doc.createElement("DateRequestRecieved");
        node.appendChild(doc.createTextNode(String.valueOf(mDateRequestRecieved)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDistId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistId)));
        root.appendChild(node);

        node =  doc.createElement("NotesToDistributor");
        node.appendChild(doc.createTextNode(String.valueOf(mNotesToDistributor)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ReturnMethod");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnMethod)));
        root.appendChild(node);

        node =  doc.createElement("Problem");
        node.appendChild(doc.createTextNode(String.valueOf(mProblem)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ReturnRequestId field is not cloned.
    *
    * @return ReturnRequestData object
    */
    public Object clone(){
        ReturnRequestData myClone = new ReturnRequestData();
        
        myClone.mSenderContactName = mSenderContactName;
        
        myClone.mSenderContactPhone = mSenderContactPhone;
        
        myClone.mReason = mReason;
        
        myClone.mDistributorInvoiceNumber = mDistributorInvoiceNumber;
        
        myClone.mDistributorRefNum = mDistributorRefNum;
        
        myClone.mReturnRequestRefNum = mReturnRequestRefNum;
        
        myClone.mPurchaseOrderId = mPurchaseOrderId;
        
        myClone.mReturnRequestStatus = mReturnRequestStatus;
        
        myClone.mPickupContactName = mPickupContactName;
        
        if(mDateRequestRecieved != null){
                myClone.mDateRequestRecieved = (Date) mDateRequestRecieved.clone();
        }
        
        myClone.mInvoiceDistId = mInvoiceDistId;
        
        myClone.mNotesToDistributor = mNotesToDistributor;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mReturnMethod = mReturnMethod;
        
        myClone.mProblem = mProblem;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ReturnRequestDataAccess.RETURN_REQUEST_ID.equals(pFieldName)) {
            return getReturnRequestId();
        } else if (ReturnRequestDataAccess.SENDER_CONTACT_NAME.equals(pFieldName)) {
            return getSenderContactName();
        } else if (ReturnRequestDataAccess.SENDER_CONTACT_PHONE.equals(pFieldName)) {
            return getSenderContactPhone();
        } else if (ReturnRequestDataAccess.REASON.equals(pFieldName)) {
            return getReason();
        } else if (ReturnRequestDataAccess.DISTRIBUTOR_INVOICE_NUMBER.equals(pFieldName)) {
            return getDistributorInvoiceNumber();
        } else if (ReturnRequestDataAccess.DISTRIBUTOR_REF_NUM.equals(pFieldName)) {
            return getDistributorRefNum();
        } else if (ReturnRequestDataAccess.RETURN_REQUEST_REF_NUM.equals(pFieldName)) {
            return getReturnRequestRefNum();
        } else if (ReturnRequestDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
        } else if (ReturnRequestDataAccess.RETURN_REQUEST_STATUS.equals(pFieldName)) {
            return getReturnRequestStatus();
        } else if (ReturnRequestDataAccess.PICKUP_CONTACT_NAME.equals(pFieldName)) {
            return getPickupContactName();
        } else if (ReturnRequestDataAccess.DATE_REQUEST_RECIEVED.equals(pFieldName)) {
            return getDateRequestRecieved();
        } else if (ReturnRequestDataAccess.INVOICE_DIST_ID.equals(pFieldName)) {
            return getInvoiceDistId();
        } else if (ReturnRequestDataAccess.NOTES_TO_DISTRIBUTOR.equals(pFieldName)) {
            return getNotesToDistributor();
        } else if (ReturnRequestDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ReturnRequestDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ReturnRequestDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ReturnRequestDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ReturnRequestDataAccess.RETURN_METHOD.equals(pFieldName)) {
            return getReturnMethod();
        } else if (ReturnRequestDataAccess.PROBLEM.equals(pFieldName)) {
            return getProblem();
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
        return ReturnRequestDataAccess.CLW_RETURN_REQUEST;
    }

    
    /**
     * Sets the ReturnRequestId field. This field is required to be set in the database.
     *
     * @param pReturnRequestId
     *  int to use to update the field.
     */
    public void setReturnRequestId(int pReturnRequestId){
        this.mReturnRequestId = pReturnRequestId;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnRequestId field.
     *
     * @return
     *  int containing the ReturnRequestId field.
     */
    public int getReturnRequestId(){
        return mReturnRequestId;
    }

    /**
     * Sets the SenderContactName field.
     *
     * @param pSenderContactName
     *  String to use to update the field.
     */
    public void setSenderContactName(String pSenderContactName){
        this.mSenderContactName = pSenderContactName;
        setDirty(true);
    }
    /**
     * Retrieves the SenderContactName field.
     *
     * @return
     *  String containing the SenderContactName field.
     */
    public String getSenderContactName(){
        return mSenderContactName;
    }

    /**
     * Sets the SenderContactPhone field.
     *
     * @param pSenderContactPhone
     *  String to use to update the field.
     */
    public void setSenderContactPhone(String pSenderContactPhone){
        this.mSenderContactPhone = pSenderContactPhone;
        setDirty(true);
    }
    /**
     * Retrieves the SenderContactPhone field.
     *
     * @return
     *  String containing the SenderContactPhone field.
     */
    public String getSenderContactPhone(){
        return mSenderContactPhone;
    }

    /**
     * Sets the Reason field.
     *
     * @param pReason
     *  String to use to update the field.
     */
    public void setReason(String pReason){
        this.mReason = pReason;
        setDirty(true);
    }
    /**
     * Retrieves the Reason field.
     *
     * @return
     *  String containing the Reason field.
     */
    public String getReason(){
        return mReason;
    }

    /**
     * Sets the DistributorInvoiceNumber field.
     *
     * @param pDistributorInvoiceNumber
     *  String to use to update the field.
     */
    public void setDistributorInvoiceNumber(String pDistributorInvoiceNumber){
        this.mDistributorInvoiceNumber = pDistributorInvoiceNumber;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorInvoiceNumber field.
     *
     * @return
     *  String containing the DistributorInvoiceNumber field.
     */
    public String getDistributorInvoiceNumber(){
        return mDistributorInvoiceNumber;
    }

    /**
     * Sets the DistributorRefNum field.
     *
     * @param pDistributorRefNum
     *  String to use to update the field.
     */
    public void setDistributorRefNum(String pDistributorRefNum){
        this.mDistributorRefNum = pDistributorRefNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorRefNum field.
     *
     * @return
     *  String containing the DistributorRefNum field.
     */
    public String getDistributorRefNum(){
        return mDistributorRefNum;
    }

    /**
     * Sets the ReturnRequestRefNum field.
     *
     * @param pReturnRequestRefNum
     *  String to use to update the field.
     */
    public void setReturnRequestRefNum(String pReturnRequestRefNum){
        this.mReturnRequestRefNum = pReturnRequestRefNum;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnRequestRefNum field.
     *
     * @return
     *  String containing the ReturnRequestRefNum field.
     */
    public String getReturnRequestRefNum(){
        return mReturnRequestRefNum;
    }

    /**
     * Sets the PurchaseOrderId field.
     *
     * @param pPurchaseOrderId
     *  int to use to update the field.
     */
    public void setPurchaseOrderId(int pPurchaseOrderId){
        this.mPurchaseOrderId = pPurchaseOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderId field.
     *
     * @return
     *  int containing the PurchaseOrderId field.
     */
    public int getPurchaseOrderId(){
        return mPurchaseOrderId;
    }

    /**
     * Sets the ReturnRequestStatus field.
     *
     * @param pReturnRequestStatus
     *  String to use to update the field.
     */
    public void setReturnRequestStatus(String pReturnRequestStatus){
        this.mReturnRequestStatus = pReturnRequestStatus;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnRequestStatus field.
     *
     * @return
     *  String containing the ReturnRequestStatus field.
     */
    public String getReturnRequestStatus(){
        return mReturnRequestStatus;
    }

    /**
     * Sets the PickupContactName field.
     *
     * @param pPickupContactName
     *  String to use to update the field.
     */
    public void setPickupContactName(String pPickupContactName){
        this.mPickupContactName = pPickupContactName;
        setDirty(true);
    }
    /**
     * Retrieves the PickupContactName field.
     *
     * @return
     *  String containing the PickupContactName field.
     */
    public String getPickupContactName(){
        return mPickupContactName;
    }

    /**
     * Sets the DateRequestRecieved field.
     *
     * @param pDateRequestRecieved
     *  Date to use to update the field.
     */
    public void setDateRequestRecieved(Date pDateRequestRecieved){
        this.mDateRequestRecieved = pDateRequestRecieved;
        setDirty(true);
    }
    /**
     * Retrieves the DateRequestRecieved field.
     *
     * @return
     *  Date containing the DateRequestRecieved field.
     */
    public Date getDateRequestRecieved(){
        return mDateRequestRecieved;
    }

    /**
     * Sets the InvoiceDistId field.
     *
     * @param pInvoiceDistId
     *  int to use to update the field.
     */
    public void setInvoiceDistId(int pInvoiceDistId){
        this.mInvoiceDistId = pInvoiceDistId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistId field.
     *
     * @return
     *  int containing the InvoiceDistId field.
     */
    public int getInvoiceDistId(){
        return mInvoiceDistId;
    }

    /**
     * Sets the NotesToDistributor field.
     *
     * @param pNotesToDistributor
     *  String to use to update the field.
     */
    public void setNotesToDistributor(String pNotesToDistributor){
        this.mNotesToDistributor = pNotesToDistributor;
        setDirty(true);
    }
    /**
     * Retrieves the NotesToDistributor field.
     *
     * @return
     *  String containing the NotesToDistributor field.
     */
    public String getNotesToDistributor(){
        return mNotesToDistributor;
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
     * Sets the AddDate field.
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

    /**
     * Sets the ModDate field.
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
     * Sets the ReturnMethod field.
     *
     * @param pReturnMethod
     *  String to use to update the field.
     */
    public void setReturnMethod(String pReturnMethod){
        this.mReturnMethod = pReturnMethod;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnMethod field.
     *
     * @return
     *  String containing the ReturnMethod field.
     */
    public String getReturnMethod(){
        return mReturnMethod;
    }

    /**
     * Sets the Problem field.
     *
     * @param pProblem
     *  String to use to update the field.
     */
    public void setProblem(String pProblem){
        this.mProblem = pProblem;
        setDirty(true);
    }
    /**
     * Retrieves the Problem field.
     *
     * @return
     *  String containing the Problem field.
     */
    public String getProblem(){
        return mProblem;
    }


}
