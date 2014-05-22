
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        Edi997Data
 * Description:  This is a ValueObject class wrapping the database table CLW_EDI_997.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.Edi997DataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>Edi997Data</code> is a ValueObject class wrapping of the database table CLW_EDI_997.
 */
public class Edi997Data extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5022702683134631257L;
    private int mEdi997Id;// SQL type:NUMBER, not null
    private int mElectronicTransactionId;// SQL type:NUMBER, not null
    private String mAckGroupType;// SQL type:VARCHAR2
    private int mAckGroupControlNumber;// SQL type:NUMBER
    private String mAckSetType;// SQL type:VARCHAR2
    private int mAckSetControlNumber;// SQL type:NUMBER
    private String mAckStatus;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public Edi997Data ()
    {
        mAckGroupType = "";
        mAckSetType = "";
        mAckStatus = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public Edi997Data(int parm1, int parm2, String parm3, int parm4, String parm5, int parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mEdi997Id = parm1;
        mElectronicTransactionId = parm2;
        mAckGroupType = parm3;
        mAckGroupControlNumber = parm4;
        mAckSetType = parm5;
        mAckSetControlNumber = parm6;
        mAckStatus = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new Edi997Data
     *
     * @return
     *  Newly initialized Edi997Data object.
     */
    public static Edi997Data createValue ()
    {
        Edi997Data valueData = new Edi997Data();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this Edi997Data object
     */
    public String toString()
    {
        return "[" + "Edi997Id=" + mEdi997Id + ", ElectronicTransactionId=" + mElectronicTransactionId + ", AckGroupType=" + mAckGroupType + ", AckGroupControlNumber=" + mAckGroupControlNumber + ", AckSetType=" + mAckSetType + ", AckSetControlNumber=" + mAckSetControlNumber + ", AckStatus=" + mAckStatus + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Edi997");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mEdi997Id));

        node =  doc.createElement("ElectronicTransactionId");
        node.appendChild(doc.createTextNode(String.valueOf(mElectronicTransactionId)));
        root.appendChild(node);

        node =  doc.createElement("AckGroupType");
        node.appendChild(doc.createTextNode(String.valueOf(mAckGroupType)));
        root.appendChild(node);

        node =  doc.createElement("AckGroupControlNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mAckGroupControlNumber)));
        root.appendChild(node);

        node =  doc.createElement("AckSetType");
        node.appendChild(doc.createTextNode(String.valueOf(mAckSetType)));
        root.appendChild(node);

        node =  doc.createElement("AckSetControlNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mAckSetControlNumber)));
        root.appendChild(node);

        node =  doc.createElement("AckStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mAckStatus)));
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
    * creates a clone of this object, the Edi997Id field is not cloned.
    *
    * @return Edi997Data object
    */
    public Object clone(){
        Edi997Data myClone = new Edi997Data();
        
        myClone.mElectronicTransactionId = mElectronicTransactionId;
        
        myClone.mAckGroupType = mAckGroupType;
        
        myClone.mAckGroupControlNumber = mAckGroupControlNumber;
        
        myClone.mAckSetType = mAckSetType;
        
        myClone.mAckSetControlNumber = mAckSetControlNumber;
        
        myClone.mAckStatus = mAckStatus;
        
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

        if (Edi997DataAccess.EDI_997_ID.equals(pFieldName)) {
            return getEdi997Id();
        } else if (Edi997DataAccess.ELECTRONIC_TRANSACTION_ID.equals(pFieldName)) {
            return getElectronicTransactionId();
        } else if (Edi997DataAccess.ACK_GROUP_TYPE.equals(pFieldName)) {
            return getAckGroupType();
        } else if (Edi997DataAccess.ACK_GROUP_CONTROL_NUMBER.equals(pFieldName)) {
            return getAckGroupControlNumber();
        } else if (Edi997DataAccess.ACK_SET_TYPE.equals(pFieldName)) {
            return getAckSetType();
        } else if (Edi997DataAccess.ACK_SET_CONTROL_NUMBER.equals(pFieldName)) {
            return getAckSetControlNumber();
        } else if (Edi997DataAccess.ACK_STATUS.equals(pFieldName)) {
            return getAckStatus();
        } else if (Edi997DataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (Edi997DataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (Edi997DataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (Edi997DataAccess.MOD_BY.equals(pFieldName)) {
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
        return Edi997DataAccess.CLW_EDI_997;
    }

    
    /**
     * Sets the Edi997Id field. This field is required to be set in the database.
     *
     * @param pEdi997Id
     *  int to use to update the field.
     */
    public void setEdi997Id(int pEdi997Id){
        this.mEdi997Id = pEdi997Id;
        setDirty(true);
    }
    /**
     * Retrieves the Edi997Id field.
     *
     * @return
     *  int containing the Edi997Id field.
     */
    public int getEdi997Id(){
        return mEdi997Id;
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
     * Sets the AckGroupType field.
     *
     * @param pAckGroupType
     *  String to use to update the field.
     */
    public void setAckGroupType(String pAckGroupType){
        this.mAckGroupType = pAckGroupType;
        setDirty(true);
    }
    /**
     * Retrieves the AckGroupType field.
     *
     * @return
     *  String containing the AckGroupType field.
     */
    public String getAckGroupType(){
        return mAckGroupType;
    }

    /**
     * Sets the AckGroupControlNumber field.
     *
     * @param pAckGroupControlNumber
     *  int to use to update the field.
     */
    public void setAckGroupControlNumber(int pAckGroupControlNumber){
        this.mAckGroupControlNumber = pAckGroupControlNumber;
        setDirty(true);
    }
    /**
     * Retrieves the AckGroupControlNumber field.
     *
     * @return
     *  int containing the AckGroupControlNumber field.
     */
    public int getAckGroupControlNumber(){
        return mAckGroupControlNumber;
    }

    /**
     * Sets the AckSetType field.
     *
     * @param pAckSetType
     *  String to use to update the field.
     */
    public void setAckSetType(String pAckSetType){
        this.mAckSetType = pAckSetType;
        setDirty(true);
    }
    /**
     * Retrieves the AckSetType field.
     *
     * @return
     *  String containing the AckSetType field.
     */
    public String getAckSetType(){
        return mAckSetType;
    }

    /**
     * Sets the AckSetControlNumber field.
     *
     * @param pAckSetControlNumber
     *  int to use to update the field.
     */
    public void setAckSetControlNumber(int pAckSetControlNumber){
        this.mAckSetControlNumber = pAckSetControlNumber;
        setDirty(true);
    }
    /**
     * Retrieves the AckSetControlNumber field.
     *
     * @return
     *  int containing the AckSetControlNumber field.
     */
    public int getAckSetControlNumber(){
        return mAckSetControlNumber;
    }

    /**
     * Sets the AckStatus field.
     *
     * @param pAckStatus
     *  String to use to update the field.
     */
    public void setAckStatus(String pAckStatus){
        this.mAckStatus = pAckStatus;
        setDirty(true);
    }
    /**
     * Retrieves the AckStatus field.
     *
     * @return
     *  String containing the AckStatus field.
     */
    public String getAckStatus(){
        return mAckStatus;
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
