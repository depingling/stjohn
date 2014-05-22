
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PriceRuleDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRICE_RULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PriceRuleDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PriceRuleDetailData</code> is a ValueObject class wrapping of the database table CLW_PRICE_RULE_DETAIL.
 */
public class PriceRuleDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4842406048459026725L;
    private int mPriceRuleDetailId;// SQL type:NUMBER, not null
    private int mPriceRuleId;// SQL type:NUMBER, not null
    private String mParamName;// SQL type:VARCHAR2
    private String mParamValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mBusEntityId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PriceRuleDetailData ()
    {
        mParamName = "";
        mParamValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PriceRuleDetailData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, int parm9)
    {
        mPriceRuleDetailId = parm1;
        mPriceRuleId = parm2;
        mParamName = parm3;
        mParamValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mBusEntityId = parm9;
        
    }

    /**
     * Creates a new PriceRuleDetailData
     *
     * @return
     *  Newly initialized PriceRuleDetailData object.
     */
    public static PriceRuleDetailData createValue ()
    {
        PriceRuleDetailData valueData = new PriceRuleDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PriceRuleDetailData object
     */
    public String toString()
    {
        return "[" + "PriceRuleDetailId=" + mPriceRuleDetailId + ", PriceRuleId=" + mPriceRuleId + ", ParamName=" + mParamName + ", ParamValue=" + mParamValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", BusEntityId=" + mBusEntityId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PriceRuleDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPriceRuleDetailId));

        node =  doc.createElement("PriceRuleId");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceRuleId)));
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

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PriceRuleDetailId field is not cloned.
    *
    * @return PriceRuleDetailData object
    */
    public Object clone(){
        PriceRuleDetailData myClone = new PriceRuleDetailData();
        
        myClone.mPriceRuleId = mPriceRuleId;
        
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
        
        myClone.mBusEntityId = mBusEntityId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PriceRuleDetailDataAccess.PRICE_RULE_DETAIL_ID.equals(pFieldName)) {
            return getPriceRuleDetailId();
        } else if (PriceRuleDetailDataAccess.PRICE_RULE_ID.equals(pFieldName)) {
            return getPriceRuleId();
        } else if (PriceRuleDetailDataAccess.PARAM_NAME.equals(pFieldName)) {
            return getParamName();
        } else if (PriceRuleDetailDataAccess.PARAM_VALUE.equals(pFieldName)) {
            return getParamValue();
        } else if (PriceRuleDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PriceRuleDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PriceRuleDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PriceRuleDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PriceRuleDetailDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
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
        return PriceRuleDetailDataAccess.CLW_PRICE_RULE_DETAIL;
    }

    
    /**
     * Sets the PriceRuleDetailId field. This field is required to be set in the database.
     *
     * @param pPriceRuleDetailId
     *  int to use to update the field.
     */
    public void setPriceRuleDetailId(int pPriceRuleDetailId){
        this.mPriceRuleDetailId = pPriceRuleDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceRuleDetailId field.
     *
     * @return
     *  int containing the PriceRuleDetailId field.
     */
    public int getPriceRuleDetailId(){
        return mPriceRuleDetailId;
    }

    /**
     * Sets the PriceRuleId field. This field is required to be set in the database.
     *
     * @param pPriceRuleId
     *  int to use to update the field.
     */
    public void setPriceRuleId(int pPriceRuleId){
        this.mPriceRuleId = pPriceRuleId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceRuleId field.
     *
     * @return
     *  int containing the PriceRuleId field.
     */
    public int getPriceRuleId(){
        return mPriceRuleId;
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


}
