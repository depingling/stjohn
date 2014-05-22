
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CleaningProcData
 * Description:  This is a ValueObject class wrapping the database table CLW_CLEANING_PROC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CleaningProcDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CleaningProcData</code> is a ValueObject class wrapping of the database table CLW_CLEANING_PROC.
 */
public class CleaningProcData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1418749719118277048L;
    private int mCleaningProcId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mCleaningProcCd;// SQL type:VARCHAR2
    private String mEstimatorPageCd;// SQL type:VARCHAR2
    private int mSeqNum;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CleaningProcData ()
    {
        mShortDesc = "";
        mCleaningProcCd = "";
        mEstimatorPageCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CleaningProcData(int parm1, String parm2, String parm3, String parm4, int parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mCleaningProcId = parm1;
        mShortDesc = parm2;
        mCleaningProcCd = parm3;
        mEstimatorPageCd = parm4;
        mSeqNum = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new CleaningProcData
     *
     * @return
     *  Newly initialized CleaningProcData object.
     */
    public static CleaningProcData createValue ()
    {
        CleaningProcData valueData = new CleaningProcData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CleaningProcData object
     */
    public String toString()
    {
        return "[" + "CleaningProcId=" + mCleaningProcId + ", ShortDesc=" + mShortDesc + ", CleaningProcCd=" + mCleaningProcCd + ", EstimatorPageCd=" + mEstimatorPageCd + ", SeqNum=" + mSeqNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CleaningProc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCleaningProcId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("CleaningProcCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCleaningProcCd)));
        root.appendChild(node);

        node =  doc.createElement("EstimatorPageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorPageCd)));
        root.appendChild(node);

        node =  doc.createElement("SeqNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSeqNum)));
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
    * creates a clone of this object, the CleaningProcId field is not cloned.
    *
    * @return CleaningProcData object
    */
    public Object clone(){
        CleaningProcData myClone = new CleaningProcData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mCleaningProcCd = mCleaningProcCd;
        
        myClone.mEstimatorPageCd = mEstimatorPageCd;
        
        myClone.mSeqNum = mSeqNum;
        
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

        if (CleaningProcDataAccess.CLEANING_PROC_ID.equals(pFieldName)) {
            return getCleaningProcId();
        } else if (CleaningProcDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CleaningProcDataAccess.CLEANING_PROC_CD.equals(pFieldName)) {
            return getCleaningProcCd();
        } else if (CleaningProcDataAccess.ESTIMATOR_PAGE_CD.equals(pFieldName)) {
            return getEstimatorPageCd();
        } else if (CleaningProcDataAccess.SEQ_NUM.equals(pFieldName)) {
            return getSeqNum();
        } else if (CleaningProcDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CleaningProcDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CleaningProcDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CleaningProcDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CleaningProcDataAccess.CLW_CLEANING_PROC;
    }

    
    /**
     * Sets the CleaningProcId field. This field is required to be set in the database.
     *
     * @param pCleaningProcId
     *  int to use to update the field.
     */
    public void setCleaningProcId(int pCleaningProcId){
        this.mCleaningProcId = pCleaningProcId;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningProcId field.
     *
     * @return
     *  int containing the CleaningProcId field.
     */
    public int getCleaningProcId(){
        return mCleaningProcId;
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
     * Sets the CleaningProcCd field.
     *
     * @param pCleaningProcCd
     *  String to use to update the field.
     */
    public void setCleaningProcCd(String pCleaningProcCd){
        this.mCleaningProcCd = pCleaningProcCd;
        setDirty(true);
    }
    /**
     * Retrieves the CleaningProcCd field.
     *
     * @return
     *  String containing the CleaningProcCd field.
     */
    public String getCleaningProcCd(){
        return mCleaningProcCd;
    }

    /**
     * Sets the EstimatorPageCd field.
     *
     * @param pEstimatorPageCd
     *  String to use to update the field.
     */
    public void setEstimatorPageCd(String pEstimatorPageCd){
        this.mEstimatorPageCd = pEstimatorPageCd;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatorPageCd field.
     *
     * @return
     *  String containing the EstimatorPageCd field.
     */
    public String getEstimatorPageCd(){
        return mEstimatorPageCd;
    }

    /**
     * Sets the SeqNum field. This field is required to be set in the database.
     *
     * @param pSeqNum
     *  int to use to update the field.
     */
    public void setSeqNum(int pSeqNum){
        this.mSeqNum = pSeqNum;
        setDirty(true);
    }
    /**
     * Retrieves the SeqNum field.
     *
     * @return
     *  int containing the SeqNum field.
     */
    public int getSeqNum(){
        return mSeqNum;
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
