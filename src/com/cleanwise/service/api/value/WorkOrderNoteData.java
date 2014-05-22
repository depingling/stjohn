
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WorkOrderNoteData
 * Description:  This is a ValueObject class wrapping the database table CLW_WORK_ORDER_NOTE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WorkOrderNoteDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WorkOrderNoteData</code> is a ValueObject class wrapping of the database table CLW_WORK_ORDER_NOTE.
 */
public class WorkOrderNoteData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6213508251811844317L;
    private int mWorkOrderNoteId;// SQL type:NUMBER, not null
    private int mWorkOrderId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mNote;// SQL type:VARCHAR2, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WorkOrderNoteData ()
    {
        mShortDesc = "";
        mNote = "";
        mTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WorkOrderNoteData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mWorkOrderNoteId = parm1;
        mWorkOrderId = parm2;
        mShortDesc = parm3;
        mNote = parm4;
        mTypeCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new WorkOrderNoteData
     *
     * @return
     *  Newly initialized WorkOrderNoteData object.
     */
    public static WorkOrderNoteData createValue ()
    {
        WorkOrderNoteData valueData = new WorkOrderNoteData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WorkOrderNoteData object
     */
    public String toString()
    {
        return "[" + "WorkOrderNoteId=" + mWorkOrderNoteId + ", WorkOrderId=" + mWorkOrderId + ", ShortDesc=" + mShortDesc + ", Note=" + mNote + ", TypeCd=" + mTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WorkOrderNote");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWorkOrderNoteId));

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Note");
        node.appendChild(doc.createTextNode(String.valueOf(mNote)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
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
    * creates a clone of this object, the WorkOrderNoteId field is not cloned.
    *
    * @return WorkOrderNoteData object
    */
    public Object clone(){
        WorkOrderNoteData myClone = new WorkOrderNoteData();
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mNote = mNote;
        
        myClone.mTypeCd = mTypeCd;
        
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

        if (WorkOrderNoteDataAccess.WORK_ORDER_NOTE_ID.equals(pFieldName)) {
            return getWorkOrderNoteId();
        } else if (WorkOrderNoteDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
        } else if (WorkOrderNoteDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (WorkOrderNoteDataAccess.NOTE.equals(pFieldName)) {
            return getNote();
        } else if (WorkOrderNoteDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (WorkOrderNoteDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WorkOrderNoteDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WorkOrderNoteDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WorkOrderNoteDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WorkOrderNoteDataAccess.CLW_WORK_ORDER_NOTE;
    }

    
    /**
     * Sets the WorkOrderNoteId field. This field is required to be set in the database.
     *
     * @param pWorkOrderNoteId
     *  int to use to update the field.
     */
    public void setWorkOrderNoteId(int pWorkOrderNoteId){
        this.mWorkOrderNoteId = pWorkOrderNoteId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderNoteId field.
     *
     * @return
     *  int containing the WorkOrderNoteId field.
     */
    public int getWorkOrderNoteId(){
        return mWorkOrderNoteId;
    }

    /**
     * Sets the WorkOrderId field. This field is required to be set in the database.
     *
     * @param pWorkOrderId
     *  int to use to update the field.
     */
    public void setWorkOrderId(int pWorkOrderId){
        this.mWorkOrderId = pWorkOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the WorkOrderId field.
     *
     * @return
     *  int containing the WorkOrderId field.
     */
    public int getWorkOrderId(){
        return mWorkOrderId;
    }

    /**
     * Sets the ShortDesc field. This field is required to be set in the database.
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
     * Sets the Note field. This field is required to be set in the database.
     *
     * @param pNote
     *  String to use to update the field.
     */
    public void setNote(String pNote){
        this.mNote = pNote;
        setDirty(true);
    }
    /**
     * Retrieves the Note field.
     *
     * @return
     *  String containing the Note field.
     */
    public String getNote(){
        return mNote;
    }

    /**
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
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
