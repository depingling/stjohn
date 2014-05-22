
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CostCenterAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_COST_CENTER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CostCenterAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CostCenterAssocData</code> is a ValueObject class wrapping of the database table CLW_COST_CENTER_ASSOC.
 */
public class CostCenterAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7285607474955127147L;
    private int mCostCenterAssocId;// SQL type:NUMBER, not null
    private int mCostCenterId;// SQL type:NUMBER, not null
    private int mCatalogId;// SQL type:NUMBER, not null
    private String mCostCenterAssocCd;// SQL type:VARCHAR2, not null
    private String mBudgetThreshold;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CostCenterAssocData ()
    {
        mCostCenterAssocCd = "";
        mBudgetThreshold = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CostCenterAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mCostCenterAssocId = parm1;
        mCostCenterId = parm2;
        mCatalogId = parm3;
        mCostCenterAssocCd = parm4;
        mBudgetThreshold = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new CostCenterAssocData
     *
     * @return
     *  Newly initialized CostCenterAssocData object.
     */
    public static CostCenterAssocData createValue ()
    {
        CostCenterAssocData valueData = new CostCenterAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CostCenterAssocData object
     */
    public String toString()
    {
        return "[" + "CostCenterAssocId=" + mCostCenterAssocId + ", CostCenterId=" + mCostCenterId + ", CatalogId=" + mCatalogId + ", CostCenterAssocCd=" + mCostCenterAssocCd + ", BudgetThreshold=" + mBudgetThreshold + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CostCenterAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCostCenterAssocId));

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("BudgetThreshold");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetThreshold)));
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
    * creates a clone of this object, the CostCenterAssocId field is not cloned.
    *
    * @return CostCenterAssocData object
    */
    public Object clone(){
        CostCenterAssocData myClone = new CostCenterAssocData();
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mCostCenterAssocCd = mCostCenterAssocCd;
        
        myClone.mBudgetThreshold = mBudgetThreshold;
        
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

        if (CostCenterAssocDataAccess.COST_CENTER_ASSOC_ID.equals(pFieldName)) {
            return getCostCenterAssocId();
        } else if (CostCenterAssocDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (CostCenterAssocDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD.equals(pFieldName)) {
            return getCostCenterAssocCd();
        } else if (CostCenterAssocDataAccess.BUDGET_THRESHOLD.equals(pFieldName)) {
            return getBudgetThreshold();
        } else if (CostCenterAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CostCenterAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CostCenterAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CostCenterAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC;
    }

    
    /**
     * Sets the CostCenterAssocId field. This field is required to be set in the database.
     *
     * @param pCostCenterAssocId
     *  int to use to update the field.
     */
    public void setCostCenterAssocId(int pCostCenterAssocId){
        this.mCostCenterAssocId = pCostCenterAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterAssocId field.
     *
     * @return
     *  int containing the CostCenterAssocId field.
     */
    public int getCostCenterAssocId(){
        return mCostCenterAssocId;
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
     * Sets the CatalogId field. This field is required to be set in the database.
     *
     * @param pCatalogId
     *  int to use to update the field.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogId field.
     *
     * @return
     *  int containing the CatalogId field.
     */
    public int getCatalogId(){
        return mCatalogId;
    }

    /**
     * Sets the CostCenterAssocCd field. This field is required to be set in the database.
     *
     * @param pCostCenterAssocCd
     *  String to use to update the field.
     */
    public void setCostCenterAssocCd(String pCostCenterAssocCd){
        this.mCostCenterAssocCd = pCostCenterAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterAssocCd field.
     *
     * @return
     *  String containing the CostCenterAssocCd field.
     */
    public String getCostCenterAssocCd(){
        return mCostCenterAssocCd;
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
