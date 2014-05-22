
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CostCenterRuleData
 * Description:  This is a ValueObject class wrapping the database table CLW_COST_CENTER_RULE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CostCenterRuleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CostCenterRuleData</code> is a ValueObject class wrapping of the database table CLW_COST_CENTER_RULE.
 */
public class CostCenterRuleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mCostCenterRuleId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mCostCenterId;// SQL type:NUMBER
    private int mItemId;// SQL type:NUMBER
    private String mMatchTypeCd;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CostCenterRuleData ()
    {
        mMatchTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CostCenterRuleData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mCostCenterRuleId = parm1;
        mBusEntityId = parm2;
        mCostCenterId = parm3;
        mItemId = parm4;
        mMatchTypeCd = parm5;
        mEffDate = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;

    }

    /**
     * Creates a new CostCenterRuleData
     *
     * @return
     *  Newly initialized CostCenterRuleData object.
     */
    public static CostCenterRuleData createValue ()
    {
        CostCenterRuleData valueData = new CostCenterRuleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CostCenterRuleData object
     */
    public String toString()
    {
        return "[" + "CostCenterRuleId=" + mCostCenterRuleId + ", BusEntityId=" + mBusEntityId + ", CostCenterId=" + mCostCenterId + ", ItemId=" + mItemId + ", MatchTypeCd=" + mMatchTypeCd + ", EffDate=" + mEffDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CostCenterRule");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCostCenterRuleId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("MatchTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mMatchTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
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
    * creates a clone of this object, the CostCenterRuleId field is not cloned.
    *
    * @return CostCenterRuleData object
    */
    public Object clone(){
        CostCenterRuleData myClone = new CostCenterRuleData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mCostCenterId = mCostCenterId;
        
        myClone.mItemId = mItemId;
        
        myClone.mMatchTypeCd = mMatchTypeCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
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

        if (CostCenterRuleDataAccess.COST_CENTER_RULE_ID.equals(pFieldName)) {
            return getCostCenterRuleId();
        } else if (CostCenterRuleDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (CostCenterRuleDataAccess.COST_CENTER_ID.equals(pFieldName)) {
            return getCostCenterId();
        } else if (CostCenterRuleDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (CostCenterRuleDataAccess.MATCH_TYPE_CD.equals(pFieldName)) {
            return getMatchTypeCd();
        } else if (CostCenterRuleDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (CostCenterRuleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CostCenterRuleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CostCenterRuleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CostCenterRuleDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CostCenterRuleDataAccess.CLW_COST_CENTER_RULE;
    }

    
    /**
     * Sets the CostCenterRuleId field. This field is required to be set in the database.
     *
     * @param pCostCenterRuleId
     *  int to use to update the field.
     */
    public void setCostCenterRuleId(int pCostCenterRuleId){
        this.mCostCenterRuleId = pCostCenterRuleId;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterRuleId field.
     *
     * @return
     *  int containing the CostCenterRuleId field.
     */
    public int getCostCenterRuleId(){
        return mCostCenterRuleId;
    }

    /**
     * Sets the BusEntityId field.
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
     * Sets the CostCenterId field.
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
     * Sets the MatchTypeCd field.
     *
     * @param pMatchTypeCd
     *  String to use to update the field.
     */
    public void setMatchTypeCd(String pMatchTypeCd){
        this.mMatchTypeCd = pMatchTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the MatchTypeCd field.
     *
     * @return
     *  String containing the MatchTypeCd field.
     */
    public String getMatchTypeCd(){
        return mMatchTypeCd;
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
     * Sets the ItemId field.
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


}
