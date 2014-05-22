
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BaseCostData
 * Description:  This is a ValueObject class wrapping the database table CLW_BASE_COST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BaseCostDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BaseCostData</code> is a ValueObject class wrapping of the database table CLW_BASE_COST.
 */
public class BaseCostData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8208527680484535065L;
    private int mBaseCostId;// SQL type:NUMBER, not null
    private int mGroupId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private java.math.BigDecimal mBaseCost;// SQL type:NUMBER, not null
    private Date mEffDate;// SQL type:DATE, not null
    private Date mExpDate;// SQL type:DATE
    private Date mRevDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mDistributorId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public BaseCostData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BaseCostData(int parm1, int parm2, int parm3, java.math.BigDecimal parm4, Date parm5, Date parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11, int parm12)
    {
        mBaseCostId = parm1;
        mGroupId = parm2;
        mItemId = parm3;
        mBaseCost = parm4;
        mEffDate = parm5;
        mExpDate = parm6;
        mRevDate = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        mDistributorId = parm12;
        
    }

    /**
     * Creates a new BaseCostData
     *
     * @return
     *  Newly initialized BaseCostData object.
     */
    public static BaseCostData createValue ()
    {
        BaseCostData valueData = new BaseCostData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BaseCostData object
     */
    public String toString()
    {
        return "[" + "BaseCostId=" + mBaseCostId + ", GroupId=" + mGroupId + ", ItemId=" + mItemId + ", BaseCost=" + mBaseCost + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", RevDate=" + mRevDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", DistributorId=" + mDistributorId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BaseCost");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBaseCostId));

        node =  doc.createElement("GroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("BaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCost)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("RevDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRevDate)));
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

        node =  doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the BaseCostId field is not cloned.
    *
    * @return BaseCostData object
    */
    public Object clone(){
        BaseCostData myClone = new BaseCostData();
        
        myClone.mGroupId = mGroupId;
        
        myClone.mItemId = mItemId;
        
        myClone.mBaseCost = mBaseCost;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        if(mRevDate != null){
                myClone.mRevDate = (Date) mRevDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mDistributorId = mDistributorId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (BaseCostDataAccess.BASE_COST_ID.equals(pFieldName)) {
            return getBaseCostId();
        } else if (BaseCostDataAccess.GROUP_ID.equals(pFieldName)) {
            return getGroupId();
        } else if (BaseCostDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (BaseCostDataAccess.BASE_COST.equals(pFieldName)) {
            return getBaseCost();
        } else if (BaseCostDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (BaseCostDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (BaseCostDataAccess.REV_DATE.equals(pFieldName)) {
            return getRevDate();
        } else if (BaseCostDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BaseCostDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BaseCostDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BaseCostDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (BaseCostDataAccess.DISTRIBUTOR_ID.equals(pFieldName)) {
            return getDistributorId();
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
        return BaseCostDataAccess.CLW_BASE_COST;
    }

    
    /**
     * Sets the BaseCostId field. This field is required to be set in the database.
     *
     * @param pBaseCostId
     *  int to use to update the field.
     */
    public void setBaseCostId(int pBaseCostId){
        this.mBaseCostId = pBaseCostId;
        setDirty(true);
    }
    /**
     * Retrieves the BaseCostId field.
     *
     * @return
     *  int containing the BaseCostId field.
     */
    public int getBaseCostId(){
        return mBaseCostId;
    }

    /**
     * Sets the GroupId field. This field is required to be set in the database.
     *
     * @param pGroupId
     *  int to use to update the field.
     */
    public void setGroupId(int pGroupId){
        this.mGroupId = pGroupId;
        setDirty(true);
    }
    /**
     * Retrieves the GroupId field.
     *
     * @return
     *  int containing the GroupId field.
     */
    public int getGroupId(){
        return mGroupId;
    }

    /**
     * Sets the ItemId field. This field is required to be set in the database.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }

    /**
     * Sets the BaseCost field. This field is required to be set in the database.
     *
     * @param pBaseCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setBaseCost(java.math.BigDecimal pBaseCost){
        this.mBaseCost = pBaseCost;
        setDirty(true);
    }
    /**
     * Retrieves the BaseCost field.
     *
     * @return
     *  java.math.BigDecimal containing the BaseCost field.
     */
    public java.math.BigDecimal getBaseCost(){
        return mBaseCost;
    }

    /**
     * Sets the EffDate field. This field is required to be set in the database.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the RevDate field.
     *
     * @param pRevDate
     *  Date to use to update the field.
     */
    public void setRevDate(Date pRevDate){
        this.mRevDate = pRevDate;
        setDirty(true);
    }
    /**
     * Retrieves the RevDate field.
     *
     * @return
     *  Date containing the RevDate field.
     */
    public Date getRevDate(){
        return mRevDate;
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
     * Sets the DistributorId field.
     *
     * @param pDistributorId
     *  int to use to update the field.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorId field.
     *
     * @return
     *  int containing the DistributorId field.
     */
    public int getDistributorId(){
        return mDistributorId;
    }


}
