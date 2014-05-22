
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CostCenterRuleDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_COST_CENTER_RULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CostCenterRuleDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CostCenterRuleDetailData</code> is a ValueObject class wrapping of the database table CLW_COST_CENTER_RULE_DETAIL.
 */
public class CostCenterRuleDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mCostCenterRuleDetailId;// SQL type:NUMBER, not null
    private int mCostCenterRuleId;// SQL type:NUMBER, not null
    private String mParamName;// SQL type:VARCHAR2
    private String mParamValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CostCenterRuleDetailData ()
    {
        mParamName = "";
        mParamValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CostCenterRuleDetailData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mCostCenterRuleDetailId = parm1;
        mCostCenterRuleId = parm2;
        mParamName = parm3;
        mParamValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new CostCenterRuleDetailData
     *
     * @return
     *  Newly initialized CostCenterRuleDetailData object.
     */
    public static CostCenterRuleDetailData createValue ()
    {
        CostCenterRuleDetailData valueData = new CostCenterRuleDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CostCenterRuleDetailData object
     */
    public String toString()
    {
        return "[" + "CostCenterRuleDetailId=" + mCostCenterRuleDetailId + ", CostCenterRuleId=" + mCostCenterRuleId + ", ParamName=" + mParamName + ", ParamValue=" + mParamValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CostCenterRuleDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCostCenterRuleDetailId));

        node =  doc.createElement("CostCenterRuleId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterRuleId)));
        root.appendChild(node);

        node =  doc.createElement("ParamName");
        node.appendChild(doc.createTextNode(String.valueOf(mParamName)));
        root.appendChild(node);

        node =  doc.createElement("ParamValue");
        node.appendChild(doc.createTextNode(String.valueOf(mParamValue)));
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
    * creates a clone of this object, the CostCenterRuleDetailId field is not cloned.
    *
    * @return CostCenterRuleDetailData object
    */
    public Object clone(){
        CostCenterRuleDetailData myClone = new CostCenterRuleDetailData();
        
        myClone.mCostCenterRuleId = mCostCenterRuleId;
        
        myClone.mParamName = mParamName;
        
        myClone.mParamValue = mParamValue;
        
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

        if (CostCenterRuleDetailDataAccess.COST_CENTER_RULE_DETAIL_ID.equals(pFieldName)) {
            return getCostCenterRuleDetailId();
        } else if (CostCenterRuleDetailDataAccess.COST_CENTER_RULE_ID.equals(pFieldName)) {
            return getCostCenterRuleId();
        } else if (CostCenterRuleDetailDataAccess.PARAM_NAME.equals(pFieldName)) {
            return getParamName();
        } else if (CostCenterRuleDetailDataAccess.PARAM_VALUE.equals(pFieldName)) {
            return getParamValue();
        } else if (CostCenterRuleDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CostCenterRuleDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CostCenterRuleDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CostCenterRuleDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CostCenterRuleDetailDataAccess.CLW_COST_CENTER_RULE_DETAIL;
    }

    
    /**
     * Sets the CostCenterRuleDetailId field. This field is required to be set in the database.
     *
     * @param pCostCenterRuleDetailId
     *  int to use to update the field.
     */
    public void setCostCenterRuleDetailId(int pCostCenterRuleDetailId){
        this.mCostCenterRuleDetailId = pCostCenterRuleDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenterRuleDetailId field.
     *
     * @return
     *  int containing the CostCenterRuleDetailId field.
     */
    public int getCostCenterRuleDetailId(){
        return mCostCenterRuleDetailId;
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
     * Sets the ParamName field.
     *
     * @param pParamName
     *  String to use to update the field.
     */
    public void setParamName(String pParamName){
        this.mParamName = pParamName;
        setDirty(true);
    }
    /**
     * Retrieves the ParamName field.
     *
     * @return
     *  String containing the ParamName field.
     */
    public String getParamName(){
        return mParamName;
    }

    /**
     * Sets the ParamValue field.
     *
     * @param pParamValue
     *  String to use to update the field.
     */
    public void setParamValue(String pParamValue){
        this.mParamValue = pParamValue;
        setDirty(true);
    }
    /**
     * Retrieves the ParamValue field.
     *
     * @return
     *  String containing the ParamValue field.
     */
    public String getParamValue(){
        return mParamValue;
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
