
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InventoryLevelDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVENTORY_LEVEL_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InventoryLevelDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InventoryLevelDetailData</code> is a ValueObject class wrapping of the database table CLW_INVENTORY_LEVEL_DETAIL.
 */
public class InventoryLevelDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mInventoryLevelDetailId;// SQL type:NUMBER, not null
    private int mInventoryLevelId;// SQL type:NUMBER, not null
    private int mPeriod;// SQL type:NUMBER, not null
    private int mValue;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InventoryLevelDetailData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public InventoryLevelDetailData(int parm1, int parm2, int parm3, int parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mInventoryLevelDetailId = parm1;
        mInventoryLevelId = parm2;
        mPeriod = parm3;
        mValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new InventoryLevelDetailData
     *
     * @return
     *  Newly initialized InventoryLevelDetailData object.
     */
    public static InventoryLevelDetailData createValue ()
    {
        InventoryLevelDetailData valueData = new InventoryLevelDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InventoryLevelDetailData object
     */
    public String toString()
    {
        return "[" + "InventoryLevelDetailId=" + mInventoryLevelDetailId + ", InventoryLevelId=" + mInventoryLevelId + ", Period=" + mPeriod + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InventoryLevelDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInventoryLevelDetailId));

        node =  doc.createElement("InventoryLevelId");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryLevelId)));
        root.appendChild(node);

        node =  doc.createElement("Period");
        node.appendChild(doc.createTextNode(String.valueOf(mPeriod)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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
    * creates a clone of this object, the InventoryLevelDetailId field is not cloned.
    *
    * @return InventoryLevelDetailData object
    */
    public Object clone(){
        InventoryLevelDetailData myClone = new InventoryLevelDetailData();
        
        myClone.mInventoryLevelId = mInventoryLevelId;
        
        myClone.mPeriod = mPeriod;
        
        myClone.mValue = mValue;
        
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

        if (InventoryLevelDetailDataAccess.INVENTORY_LEVEL_DETAIL_ID.equals(pFieldName)) {
            return getInventoryLevelDetailId();
        } else if (InventoryLevelDetailDataAccess.INVENTORY_LEVEL_ID.equals(pFieldName)) {
            return getInventoryLevelId();
        } else if (InventoryLevelDetailDataAccess.PERIOD.equals(pFieldName)) {
            return getPeriod();
        } else if (InventoryLevelDetailDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (InventoryLevelDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InventoryLevelDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InventoryLevelDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InventoryLevelDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return InventoryLevelDetailDataAccess.CLW_INVENTORY_LEVEL_DETAIL;
    }

    
    /**
     * Sets the InventoryLevelDetailId field. This field is required to be set in the database.
     *
     * @param pInventoryLevelDetailId
     *  int to use to update the field.
     */
    public void setInventoryLevelDetailId(int pInventoryLevelDetailId){
        this.mInventoryLevelDetailId = pInventoryLevelDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryLevelDetailId field.
     *
     * @return
     *  int containing the InventoryLevelDetailId field.
     */
    public int getInventoryLevelDetailId(){
        return mInventoryLevelDetailId;
    }

    /**
     * Sets the InventoryLevelId field. This field is required to be set in the database.
     *
     * @param pInventoryLevelId
     *  int to use to update the field.
     */
    public void setInventoryLevelId(int pInventoryLevelId){
        this.mInventoryLevelId = pInventoryLevelId;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryLevelId field.
     *
     * @return
     *  int containing the InventoryLevelId field.
     */
    public int getInventoryLevelId(){
        return mInventoryLevelId;
    }

    /**
     * Sets the Period field. This field is required to be set in the database.
     *
     * @param pPeriod
     *  int to use to update the field.
     */
    public void setPeriod(int pPeriod){
        this.mPeriod = pPeriod;
        setDirty(true);
    }
    /**
     * Retrieves the Period field.
     *
     * @return
     *  int containing the Period field.
     */
    public int getPeriod(){
        return mPeriod;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  int to use to update the field.
     */
    public void setValue(int pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  int containing the Value field.
     */
    public int getValue(){
        return mValue;
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
