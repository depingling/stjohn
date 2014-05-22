
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUDGET.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BudgetDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BudgetData</code> is a ValueObject class wrapping of the database table CLW_BUDGET.
 */
public class BudgetData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7247769078194188926L;
    private int mBudgetId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mCostCenterId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mBudgetStatusCd;// SQL type:VARCHAR2, not null
    private String mBudgetTypeCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mBudgetThreshold;// SQL type:VARCHAR2
    private int mBudgetYear;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BudgetData ()
    {
        mShortDesc = "";
        mBudgetStatusCd = "";
        mBudgetTypeCd = "";
        mBudgetThreshold = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BudgetData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, Date parm8, String parm9, int parm10, Date parm11, String parm12, Date parm13, String parm14)
    {
        mBudgetId = parm1;
        mBusEntityId = parm2;
        mCostCenterId = parm3;
        mShortDesc = parm4;
        mBudgetStatusCd = parm5;
        mBudgetTypeCd = parm6;
        mEffDate = parm7;
        mExpDate = parm8;
        mBudgetThreshold = parm9;
        mBudgetYear = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;

    }

    /**
     * Creates a new BudgetData
     *
     * @return
     *  Newly initialized BudgetData object.
     */
    public static BudgetData createValue ()
    {
        BudgetData valueData = new BudgetData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetData object
     */
    public String toString()
    {
        return "[" + "BudgetId=" + mBudgetId + ", BusEntityId=" + mBusEntityId + ", CostCenterId=" + mCostCenterId + ", ShortDesc=" + mShortDesc + ", BudgetStatusCd=" + mBudgetStatusCd + ", BudgetTypeCd=" + mBudgetTypeCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", BudgetThreshold=" + mBudgetThreshold + ", BudgetYear=" + mBudgetYear + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Budget");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBudgetId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("BudgetStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("BudgetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("BudgetThreshold");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetThreshold)));
        root.appendChild(node);

        node =  doc.createElement("BudgetYear");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetYear)));
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
    * creates a clone of this object, the BudgetId field is not cloned.
    *
    * @return BudgetData object
    */
    public Object clone(){
        BudgetData myClone = new BudgetData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mBudgetStatusCd = mBudgetStatusCd;
        
        myClone.mBudgetTypeCd = mBudgetTypeCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mBudgetThreshold = mBudgetThreshold;
        
        myClone.mBudgetYear = mBudgetYear;
        
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

        if (BudgetDataAccess.BUDGET_ID.equals(pFieldName)) {
            return getBudgetId();
        } else if (BudgetDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (BudgetDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (BudgetDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (BudgetDataAccess.BUDGET_STATUS_CD.equals(pFieldName)) {
            return getBudgetStatusCd();
        } else if (BudgetDataAccess.BUDGET_TYPE_CD.equals(pFieldName)) {
            return getBudgetTypeCd();
        } else if (BudgetDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (BudgetDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (BudgetDataAccess.BUDGET_THRESHOLD.equals(pFieldName)) {
            return getBudgetThreshold();
        } else if (BudgetDataAccess.BUDGET_YEAR.equals(pFieldName)) {
            return getBudgetYear();
        } else if (BudgetDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BudgetDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BudgetDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BudgetDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BudgetDataAccess.CLW_BUDGET;
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
     * Sets the CostCenterId field. This field is required to be set in the database.
     *
     * @param pCostCenterId
     *  int to use to update the field.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterId field.
     *
     * @return
     *  int containing the CostCenterId field.
     */
    public int getCostCenterId(){
        return mCostCenterId;
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
     * Sets the BudgetStatusCd field. This field is required to be set in the database.
     *
     * @param pBudgetStatusCd
     *  String to use to update the field.
     */
    public void setBudgetStatusCd(String pBudgetStatusCd){
        this.mBudgetStatusCd = pBudgetStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetStatusCd field.
     *
     * @return
     *  String containing the BudgetStatusCd field.
     */
    public String getBudgetStatusCd(){
        return mBudgetStatusCd;
    }

    /**
     * Sets the BudgetTypeCd field. This field is required to be set in the database.
     *
     * @param pBudgetTypeCd
     *  String to use to update the field.
     */
    public void setBudgetTypeCd(String pBudgetTypeCd){
        this.mBudgetTypeCd = pBudgetTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetTypeCd field.
     *
     * @return
     *  String containing the BudgetTypeCd field.
     */
    public String getBudgetTypeCd(){
        return mBudgetTypeCd;
    }

    /**
     * Sets the EffDate field.
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
     * Sets the BudgetThreshold field.
     *
     * @param pBudgetThreshold
     *  String to use to update the field.
     */
    public void setBudgetThreshold(String pBudgetThreshold){
        this.mBudgetThreshold = pBudgetThreshold;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetThreshold field.
     *
     * @return
     *  String containing the BudgetThreshold field.
     */
    public String getBudgetThreshold(){
        return mBudgetThreshold;
    }

    /**
     * Sets the BudgetYear field. This field is required to be set in the database.
     *
     * @param pBudgetYear
     *  int to use to update the field.
     */
    public void setBudgetYear(int pBudgetYear){
        this.mBudgetYear = pBudgetYear;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetYear field.
     *
     * @return
     *  int containing the BudgetYear field.
     */
    public int getBudgetYear(){
        return mBudgetYear;
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
