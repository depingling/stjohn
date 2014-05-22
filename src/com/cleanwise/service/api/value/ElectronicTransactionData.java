
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ElectronicTransactionData
 * Description:  This is a ValueObject class wrapping the database table CLW_ELECTRONIC_TRANSACTION.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ElectronicTransactionDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ElectronicTransactionData</code> is a ValueObject class wrapping of the database table CLW_ELECTRONIC_TRANSACTION.
 */
public class ElectronicTransactionData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -70246271345876742L;
    private int mElectronicTransactionId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mInterchangeId;// SQL type:NUMBER, not null
    private String mGroupType;// SQL type:VARCHAR2
    private String mGroupSender;// SQL type:VARCHAR2
    private String mGroupReceiver;// SQL type:VARCHAR2
    private int mGroupControlNumber;// SQL type:NUMBER
    private String mSetType;// SQL type:VARCHAR2, not null
    private int mSetControlNumber;// SQL type:NUMBER
    private int mSetStatus;// SQL type:NUMBER
    private String mSetData;// SQL type:VARCHAR2
    private String mException;// SQL type:VARCHAR2
    private String mKeyString;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mReferenceId;// SQL type:NUMBER
    private String mReferenceTable;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ElectronicTransactionData ()
    {
        mGroupType = "";
        mGroupSender = "";
        mGroupReceiver = "";
        mSetType = "";
        mSetData = "";
        mException = "";
        mKeyString = "";
        mAddBy = "";
        mModBy = "";
        mReferenceTable = "";
    }

    /**
     * Constructor.
     */
    public ElectronicTransactionData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, int parm7, String parm8, int parm9, int parm10, String parm11, String parm12, String parm13, Date parm14, String parm15, Date parm16, String parm17, int parm18, String parm19)
    {
        mElectronicTransactionId = parm1;
        mOrderId = parm2;
        mInterchangeId = parm3;
        mGroupType = parm4;
        mGroupSender = parm5;
        mGroupReceiver = parm6;
        mGroupControlNumber = parm7;
        mSetType = parm8;
        mSetControlNumber = parm9;
        mSetStatus = parm10;
        mSetData = parm11;
        mException = parm12;
        mKeyString = parm13;
        mAddDate = parm14;
        mAddBy = parm15;
        mModDate = parm16;
        mModBy = parm17;
        mReferenceId = parm18;
        mReferenceTable = parm19;
        
    }

    /**
     * Creates a new ElectronicTransactionData
     *
     * @return
     *  Newly initialized ElectronicTransactionData object.
     */
    public static ElectronicTransactionData createValue ()
    {
        ElectronicTransactionData valueData = new ElectronicTransactionData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ElectronicTransactionData object
     */
    public String toString()
    {
        return "[" + "ElectronicTransactionId=" + mElectronicTransactionId + ", OrderId=" + mOrderId + ", InterchangeId=" + mInterchangeId + ", GroupType=" + mGroupType + ", GroupSender=" + mGroupSender + ", GroupReceiver=" + mGroupReceiver + ", GroupControlNumber=" + mGroupControlNumber + ", SetType=" + mSetType + ", SetControlNumber=" + mSetControlNumber + ", SetStatus=" + mSetStatus + ", SetData=" + mSetData + ", Exception=" + mException + ", KeyString=" + mKeyString + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ReferenceId=" + mReferenceId + ", ReferenceTable=" + mReferenceTable + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ElectronicTransaction");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mElectronicTransactionId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("InterchangeId");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeId)));
        root.appendChild(node);

        node =  doc.createElement("GroupType");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupType)));
        root.appendChild(node);

        node =  doc.createElement("GroupSender");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupSender)));
        root.appendChild(node);

        node =  doc.createElement("GroupReceiver");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupReceiver)));
        root.appendChild(node);

        node =  doc.createElement("GroupControlNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupControlNumber)));
        root.appendChild(node);

        node =  doc.createElement("SetType");
        node.appendChild(doc.createTextNode(String.valueOf(mSetType)));
        root.appendChild(node);

        node =  doc.createElement("SetControlNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mSetControlNumber)));
        root.appendChild(node);

        node =  doc.createElement("SetStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mSetStatus)));
        root.appendChild(node);

        node =  doc.createElement("SetData");
        node.appendChild(doc.createTextNode(String.valueOf(mSetData)));
        root.appendChild(node);

        node =  doc.createElement("Exception");
        node.appendChild(doc.createTextNode(String.valueOf(mException)));
        root.appendChild(node);

        node =  doc.createElement("KeyString");
        node.appendChild(doc.createTextNode(String.valueOf(mKeyString)));
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

        node =  doc.createElement("ReferenceId");
        node.appendChild(doc.createTextNode(String.valueOf(mReferenceId)));
        root.appendChild(node);

        node =  doc.createElement("ReferenceTable");
        node.appendChild(doc.createTextNode(String.valueOf(mReferenceTable)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ElectronicTransactionId field is not cloned.
    *
    * @return ElectronicTransactionData object
    */
    public Object clone(){
        ElectronicTransactionData myClone = new ElectronicTransactionData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mInterchangeId = mInterchangeId;
        
        myClone.mGroupType = mGroupType;
        
        myClone.mGroupSender = mGroupSender;
        
        myClone.mGroupReceiver = mGroupReceiver;
        
        myClone.mGroupControlNumber = mGroupControlNumber;
        
        myClone.mSetType = mSetType;
        
        myClone.mSetControlNumber = mSetControlNumber;
        
        myClone.mSetStatus = mSetStatus;
        
        myClone.mSetData = mSetData;
        
        myClone.mException = mException;
        
        myClone.mKeyString = mKeyString;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mReferenceId = mReferenceId;
        
        myClone.mReferenceTable = mReferenceTable;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ElectronicTransactionDataAccess.ELECTRONIC_TRANSACTION_ID.equals(pFieldName)) {
            return getElectronicTransactionId();
        } else if (ElectronicTransactionDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (ElectronicTransactionDataAccess.INTERCHANGE_ID.equals(pFieldName)) {
            return getInterchangeId();
        } else if (ElectronicTransactionDataAccess.GROUP_TYPE.equals(pFieldName)) {
            return getGroupType();
        } else if (ElectronicTransactionDataAccess.GROUP_SENDER.equals(pFieldName)) {
            return getGroupSender();
        } else if (ElectronicTransactionDataAccess.GROUP_RECEIVER.equals(pFieldName)) {
            return getGroupReceiver();
        } else if (ElectronicTransactionDataAccess.GROUP_CONTROL_NUMBER.equals(pFieldName)) {
            return getGroupControlNumber();
        } else if (ElectronicTransactionDataAccess.SET_TYPE.equals(pFieldName)) {
            return getSetType();
        } else if (ElectronicTransactionDataAccess.SET_CONTROL_NUMBER.equals(pFieldName)) {
            return getSetControlNumber();
        } else if (ElectronicTransactionDataAccess.SET_STATUS.equals(pFieldName)) {
            return getSetStatus();
        } else if (ElectronicTransactionDataAccess.SET_DATA.equals(pFieldName)) {
            return getSetData();
        } else if (ElectronicTransactionDataAccess.EXCEPTION.equals(pFieldName)) {
            return getException();
        } else if (ElectronicTransactionDataAccess.KEY_STRING.equals(pFieldName)) {
            return getKeyString();
        } else if (ElectronicTransactionDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ElectronicTransactionDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ElectronicTransactionDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ElectronicTransactionDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ElectronicTransactionDataAccess.REFERENCE_ID.equals(pFieldName)) {
            return getReferenceId();
        } else if (ElectronicTransactionDataAccess.REFERENCE_TABLE.equals(pFieldName)) {
            return getReferenceTable();
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
        return ElectronicTransactionDataAccess.CLW_ELECTRONIC_TRANSACTION;
    }

    
    /**
     * Sets the ElectronicTransactionId field. This field is required to be set in the database.
     *
     * @param pElectronicTransactionId
     *  int to use to update the field.
     */
    public void setElectronicTransactionId(int pElectronicTransactionId){
        this.mElectronicTransactionId = pElectronicTransactionId;
        setDirty(true);
    }
    /**
     * Retrieves the ElectronicTransactionId field.
     *
     * @return
     *  int containing the ElectronicTransactionId field.
     */
    public int getElectronicTransactionId(){
        return mElectronicTransactionId;
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
     * Sets the InterchangeId field. This field is required to be set in the database.
     *
     * @param pInterchangeId
     *  int to use to update the field.
     */
    public void setInterchangeId(int pInterchangeId){
        this.mInterchangeId = pInterchangeId;
        setDirty(true);
    }
    /**
     * Retrieves the InterchangeId field.
     *
     * @return
     *  int containing the InterchangeId field.
     */
    public int getInterchangeId(){
        return mInterchangeId;
    }

    /**
     * Sets the GroupType field.
     *
     * @param pGroupType
     *  String to use to update the field.
     */
    public void setGroupType(String pGroupType){
        this.mGroupType = pGroupType;
        setDirty(true);
    }
    /**
     * Retrieves the GroupType field.
     *
     * @return
     *  String containing the GroupType field.
     */
    public String getGroupType(){
        return mGroupType;
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
     * Sets the GroupControlNumber field.
     *
     * @param pGroupControlNumber
     *  int to use to update the field.
     */
    public void setGroupControlNumber(int pGroupControlNumber){
        this.mGroupControlNumber = pGroupControlNumber;
        setDirty(true);
    }
    /**
     * Retrieves the GroupControlNumber field.
     *
     * @return
     *  int containing the GroupControlNumber field.
     */
    public int getGroupControlNumber(){
        return mGroupControlNumber;
    }

    /**
     * Sets the SetType field. This field is required to be set in the database.
     *
     * @param pSetType
     *  String to use to update the field.
     */
    public void setSetType(String pSetType){
        this.mSetType = pSetType;
        setDirty(true);
    }
    /**
     * Retrieves the SetType field.
     *
     * @return
     *  String containing the SetType field.
     */
    public String getSetType(){
        return mSetType;
    }

    /**
     * Sets the SetControlNumber field.
     *
     * @param pSetControlNumber
     *  int to use to update the field.
     */
    public void setSetControlNumber(int pSetControlNumber){
        this.mSetControlNumber = pSetControlNumber;
        setDirty(true);
    }
    /**
     * Retrieves the SetControlNumber field.
     *
     * @return
     *  int containing the SetControlNumber field.
     */
    public int getSetControlNumber(){
        return mSetControlNumber;
    }

    /**
     * Sets the SetStatus field.
     *
     * @param pSetStatus
     *  int to use to update the field.
     */
    public void setSetStatus(int pSetStatus){
        this.mSetStatus = pSetStatus;
        setDirty(true);
    }
    /**
     * Retrieves the SetStatus field.
     *
     * @return
     *  int containing the SetStatus field.
     */
    public int getSetStatus(){
        return mSetStatus;
    }

    /**
     * Sets the SetData field.
     *
     * @param pSetData
     *  String to use to update the field.
     */
    public void setSetData(String pSetData){
        this.mSetData = pSetData;
        setDirty(true);
    }
    /**
     * Retrieves the SetData field.
     *
     * @return
     *  String containing the SetData field.
     */
    public String getSetData(){
        return mSetData;
    }

    /**
     * Sets the Exception field.
     *
     * @param pException
     *  String to use to update the field.
     */
    public void setException(String pException){
        this.mException = pException;
        setDirty(true);
    }
    /**
     * Retrieves the Exception field.
     *
     * @return
     *  String containing the Exception field.
     */
    public String getException(){
        return mException;
    }

    /**
     * Sets the KeyString field.
     *
     * @param pKeyString
     *  String to use to update the field.
     */
    public void setKeyString(String pKeyString){
        this.mKeyString = pKeyString;
        setDirty(true);
    }
    /**
     * Retrieves the KeyString field.
     *
     * @return
     *  String containing the KeyString field.
     */
    public String getKeyString(){
        return mKeyString;
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

    /**
     * Sets the ReferenceId field.
     *
     * @param pReferenceId
     *  int to use to update the field.
     */
    public void setReferenceId(int pReferenceId){
        this.mReferenceId = pReferenceId;
        setDirty(true);
    }
    /**
     * Retrieves the ReferenceId field.
     *
     * @return
     *  int containing the ReferenceId field.
     */
    public int getReferenceId(){
        return mReferenceId;
    }

    /**
     * Sets the ReferenceTable field.
     *
     * @param pReferenceTable
     *  String to use to update the field.
     */
    public void setReferenceTable(String pReferenceTable){
        this.mReferenceTable = pReferenceTable;
        setDirty(true);
    }
    /**
     * Retrieves the ReferenceTable field.
     *
     * @return
     *  String containing the ReferenceTable field.
     */
    public String getReferenceTable(){
        return mReferenceTable;
    }


}
