
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUDGET_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BudgetDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BudgetDetailData</code> is a ValueObject class wrapping of the database table CLW_BUDGET_DETAIL.
 */
public class BudgetDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mBudgetDetailId;// SQL type:NUMBER, not null
    private int mBudgetId;// SQL type:NUMBER, not null
    private int mPeriod;// SQL type:NUMBER, not null
    private java.math.BigDecimal mAmount;// SQL type:NUMBER
    private String mAmountStr;
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BudgetDetailData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BudgetDetailData(int parm1, int parm2, int parm3, java.math.BigDecimal parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mBudgetDetailId = parm1;
        mBudgetId = parm2;
        mPeriod = parm3;
        mAmount = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new BudgetDetailData
     *
     * @return
     *  Newly initialized BudgetDetailData object.
     */
    public static BudgetDetailData createValue ()
    {
        BudgetDetailData valueData = new BudgetDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetDetailData object
     */
    public String toString()
    {
        return "[" + "BudgetDetailId=" + mBudgetDetailId + ", BudgetId=" + mBudgetId + ", Period=" + mPeriod + ", Amount=" + mAmount + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BudgetDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBudgetDetailId));

        node =  doc.createElement("BudgetId");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetId)));
        root.appendChild(node);

        node =  doc.createElement("Period");
        node.appendChild(doc.createTextNode(String.valueOf(mPeriod)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
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
    * creates a clone of this object, the BudgetDetailId field is not cloned.
    *
    * @return BudgetDetailData object
    */
    public Object clone(){
        BudgetDetailData myClone = new BudgetDetailData();
        
        myClone.mBudgetId = mBudgetId;
        
        myClone.mPeriod = mPeriod;
        
        myClone.mAmount = mAmount;

        myClone.mAmountStr = mAmountStr;
        
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

        if (BudgetDetailDataAccess.BUDGET_DETAIL_ID.equals(pFieldName)) {
            return getBudgetDetailId();
        } else if (BudgetDetailDataAccess.BUDGET_ID.equals(pFieldName)) {
            return getBudgetId();
        } else if (BudgetDetailDataAccess.PERIOD.equals(pFieldName)) {
            return getPeriod();
        } else if (BudgetDetailDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (BudgetDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BudgetDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BudgetDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BudgetDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BudgetDetailDataAccess.CLW_BUDGET_DETAIL;
    }

    
    /**
     * Sets the BudgetDetailId field. This field is required to be set in the database.
     *
     * @param pBudgetDetailId
     *  int to use to update the field.
     */
    public void setBudgetDetailId(int pBudgetDetailId){
        this.mBudgetDetailId = pBudgetDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetDetailId field.
     *
     * @return
     *  int containing the BudgetDetailId field.
     */
    public int getBudgetDetailId(){
        return mBudgetDetailId;
    }

    /**
     * Sets the BudgetId field. This field is required to be set in the database.
     *
     * @param pBudgetId
     *  int to use to update the field.
     */
    public void setBudgetId(int pBudgetId){
        this.mBudgetId = pBudgetId;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetId field.
     *
     * @return
     *  int containing the BudgetId field.
     */
    public int getBudgetId(){
        return mBudgetId;
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
     * Sets the Amount field.
     *
     * @param pAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAmount(java.math.BigDecimal pAmount){
        this.mAmount = pAmount;
        setDirty(true);
    }
    /**
     * Retrieves the Amount field.
     *
     * @return
     *  java.math.BigDecimal containing the Amount field.
     */
    public java.math.BigDecimal getAmount(){
        return mAmount;
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

   public void setAmountStr(String val) {
       mAmountStr = val;
   }

   public String getAmountStr() {
       return mAmountStr;
   }
}
