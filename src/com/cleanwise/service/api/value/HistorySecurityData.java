
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        HistorySecurityData
 * Description:  This is a ValueObject class wrapping the database table CLW_HISTORY_SECURITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.HistorySecurityDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>HistorySecurityData</code> is a ValueObject class wrapping of the database table CLW_HISTORY_SECURITY.
 */
public class HistorySecurityData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mHistorySecurityId;// SQL type:NUMBER, not null
    private int mHistoryId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mBusEntityTypeCd;// SQL type:VARCHAR2, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public HistorySecurityData ()
    {
        mBusEntityTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public HistorySecurityData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mHistorySecurityId = parm1;
        mHistoryId = parm2;
        mBusEntityId = parm3;
        mBusEntityTypeCd = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new HistorySecurityData
     *
     * @return
     *  Newly initialized HistorySecurityData object.
     */
    public static HistorySecurityData createValue ()
    {
        HistorySecurityData valueData = new HistorySecurityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this HistorySecurityData object
     */
    public String toString()
    {
        return "[" + "HistorySecurityId=" + mHistorySecurityId + ", HistoryId=" + mHistoryId + ", BusEntityId=" + mBusEntityId + ", BusEntityTypeCd=" + mBusEntityTypeCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("HistorySecurity");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mHistorySecurityId));

        node =  doc.createElement("HistoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mHistoryId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTypeCd)));
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

        return root;
    }

    /**
    * creates a clone of this object, the HistorySecurityId field is not cloned.
    *
    * @return HistorySecurityData object
    */
    public Object clone(){
        HistorySecurityData myClone = new HistorySecurityData();
        
        myClone.mHistoryId = mHistoryId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mBusEntityTypeCd = mBusEntityTypeCd;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (HistorySecurityDataAccess.HISTORY_SECURITY_ID.equals(pFieldName)) {
            return getHistorySecurityId();
        } else if (HistorySecurityDataAccess.HISTORY_ID.equals(pFieldName)) {
            return getHistoryId();
        } else if (HistorySecurityDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (HistorySecurityDataAccess.BUS_ENTITY_TYPE_CD.equals(pFieldName)) {
            return getBusEntityTypeCd();
        } else if (HistorySecurityDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (HistorySecurityDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (HistorySecurityDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (HistorySecurityDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return HistorySecurityDataAccess.CLW_HISTORY_SECURITY;
    }

    
    /**
     * Sets the HistorySecurityId field. This field is required to be set in the database.
     *
     * @param pHistorySecurityId
     *  int to use to update the field.
     */
    public void setHistorySecurityId(int pHistorySecurityId){
        this.mHistorySecurityId = pHistorySecurityId;
        setDirty(true);
    }
    /**
     * Retrieves the HistorySecurityId field.
     *
     * @return
     *  int containing the HistorySecurityId field.
     */
    public int getHistorySecurityId(){
        return mHistorySecurityId;
    }

    /**
     * Sets the HistoryId field. This field is required to be set in the database.
     *
     * @param pHistoryId
     *  int to use to update the field.
     */
    public void setHistoryId(int pHistoryId){
        this.mHistoryId = pHistoryId;
        setDirty(true);
    }
    /**
     * Retrieves the HistoryId field.
     *
     * @return
     *  int containing the HistoryId field.
     */
    public int getHistoryId(){
        return mHistoryId;
    }

    /**
     * Sets the BusEntityId field. This field is required to be set in the database.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }

    /**
     * Sets the BusEntityTypeCd field. This field is required to be set in the database.
     *
     * @param pBusEntityTypeCd
     *  String to use to update the field.
     */
    public void setBusEntityTypeCd(String pBusEntityTypeCd){
        this.mBusEntityTypeCd = pBusEntityTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityTypeCd field.
     *
     * @return
     *  String containing the BusEntityTypeCd field.
     */
    public String getBusEntityTypeCd(){
        return mBusEntityTypeCd;
    }

    /**
     * Sets the AddBy field. This field is required to be set in the database.
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


}
