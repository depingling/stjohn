
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteLedgerData
 * Description:  This is a ValueObject class wrapping the database table CLW_SITE_LEDGER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.SiteLedgerDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>SiteLedgerData</code> is a ValueObject class wrapping of the database table CLW_SITE_LEDGER.
 */
public class SiteLedgerData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4947956401074184104L;
    private int mSiteLedgerId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mSiteId;// SQL type:NUMBER, not null
    private int mCostCenterId;// SQL type:NUMBER, not null
    private java.math.BigDecimal mAmount;// SQL type:NUMBER, not null
    private String mEntryTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mBudgetYear;// SQL type:NUMBER
    private int mBudgetPeriod;// SQL type:NUMBER
    private int mFiscalCalenderId;// SQL type:NUMBER
    private String mComments;// SQL type:VARCHAR2
    private int mWorkOrderId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public SiteLedgerData ()
    {
        mEntryTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mComments = "";
    }

    /**
     * Constructor.
     */
    public SiteLedgerData(int parm1, int parm2, int parm3, int parm4, java.math.BigDecimal parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, int parm11, int parm12, int parm13, String parm14, int parm15)
    {
        mSiteLedgerId = parm1;
        mOrderId = parm2;
        mSiteId = parm3;
        mCostCenterId = parm4;
        mAmount = parm5;
        mEntryTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mBudgetYear = parm11;
        mBudgetPeriod = parm12;
        mFiscalCalenderId = parm13;
        mComments = parm14;
        mWorkOrderId = parm15;
        
    }

    /**
     * Creates a new SiteLedgerData
     *
     * @return
     *  Newly initialized SiteLedgerData object.
     */
    public static SiteLedgerData createValue ()
    {
        SiteLedgerData valueData = new SiteLedgerData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteLedgerData object
     */
    public String toString()
    {
        return "[" + "SiteLedgerId=" + mSiteLedgerId + ", OrderId=" + mOrderId + ", SiteId=" + mSiteId + ", CostCenterId=" + mCostCenterId + ", Amount=" + mAmount + ", EntryTypeCd=" + mEntryTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", BudgetYear=" + mBudgetYear + ", BudgetPeriod=" + mBudgetPeriod + ", FiscalCalenderId=" + mFiscalCalenderId + ", Comments=" + mComments + ", WorkOrderId=" + mWorkOrderId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("SiteLedger");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mSiteLedgerId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node =  doc.createElement("EntryTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEntryTypeCd)));
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

        node =  doc.createElement("BudgetYear");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetYear)));
        root.appendChild(node);

        node =  doc.createElement("BudgetPeriod");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetPeriod)));
        root.appendChild(node);

        node =  doc.createElement("FiscalCalenderId");
        node.appendChild(doc.createTextNode(String.valueOf(mFiscalCalenderId)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
        root.appendChild(node);

        node =  doc.createElement("WorkOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkOrderId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the SiteLedgerId field is not cloned.
    *
    * @return SiteLedgerData object
    */
    public Object clone(){
        SiteLedgerData myClone = new SiteLedgerData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mSiteId = mSiteId;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mAmount = mAmount;
        
        myClone.mEntryTypeCd = mEntryTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mBudgetYear = mBudgetYear;
        
        myClone.mBudgetPeriod = mBudgetPeriod;
        
        myClone.mFiscalCalenderId = mFiscalCalenderId;
        
        myClone.mComments = mComments;
        
        myClone.mWorkOrderId = mWorkOrderId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (SiteLedgerDataAccess.SITE_LEDGER_ID.equals(pFieldName)) {
            return getSiteLedgerId();
        } else if (SiteLedgerDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (SiteLedgerDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (SiteLedgerDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (SiteLedgerDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (SiteLedgerDataAccess.ENTRY_TYPE_CD.equals(pFieldName)) {
            return getEntryTypeCd();
        } else if (SiteLedgerDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (SiteLedgerDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (SiteLedgerDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (SiteLedgerDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (SiteLedgerDataAccess.BUDGET_YEAR.equals(pFieldName)) {
            return getBudgetYear();
        } else if (SiteLedgerDataAccess.BUDGET_PERIOD.equals(pFieldName)) {
            return getBudgetPeriod();
        } else if (SiteLedgerDataAccess.FISCAL_CALENDER_ID.equals(pFieldName)) {
            return getFiscalCalenderId();
        } else if (SiteLedgerDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (SiteLedgerDataAccess.WORK_ORDER_ID.equals(pFieldName)) {
            return getWorkOrderId();
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
        return SiteLedgerDataAccess.CLW_SITE_LEDGER;
    }

    
    /**
     * Sets the SiteLedgerId field. This field is required to be set in the database.
     *
     * @param pSiteLedgerId
     *  int to use to update the field.
     */
    public void setSiteLedgerId(int pSiteLedgerId){
        this.mSiteLedgerId = pSiteLedgerId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteLedgerId field.
     *
     * @return
     *  int containing the SiteLedgerId field.
     */
    public int getSiteLedgerId(){
        return mSiteLedgerId;
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
     * Sets the SiteId field. This field is required to be set in the database.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
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
     * Sets the Amount field. This field is required to be set in the database.
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
     * Sets the EntryTypeCd field. This field is required to be set in the database.
     *
     * @param pEntryTypeCd
     *  String to use to update the field.
     */
    public void setEntryTypeCd(String pEntryTypeCd){
        this.mEntryTypeCd = pEntryTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the EntryTypeCd field.
     *
     * @return
     *  String containing the EntryTypeCd field.
     */
    public String getEntryTypeCd(){
        return mEntryTypeCd;
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
     * Sets the BudgetYear field.
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
     * Sets the BudgetPeriod field.
     *
     * @param pBudgetPeriod
     *  int to use to update the field.
     */
    public void setBudgetPeriod(int pBudgetPeriod){
        this.mBudgetPeriod = pBudgetPeriod;
        setDirty(true);
    }
    /**
     * Retrieves the BudgetPeriod field.
     *
     * @return
     *  int containing the BudgetPeriod field.
     */
    public int getBudgetPeriod(){
        return mBudgetPeriod;
    }

    /**
     * Sets the FiscalCalenderId field.
     *
     * @param pFiscalCalenderId
     *  int to use to update the field.
     */
    public void setFiscalCalenderId(int pFiscalCalenderId){
        this.mFiscalCalenderId = pFiscalCalenderId;
        setDirty(true);
    }
    /**
     * Retrieves the FiscalCalenderId field.
     *
     * @return
     *  int containing the FiscalCalenderId field.
     */
    public int getFiscalCalenderId(){
        return mFiscalCalenderId;
    }

    /**
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
        setDirty(true);
    }
    /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
    }

    /**
     * Sets the WorkOrderId field.
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


}
