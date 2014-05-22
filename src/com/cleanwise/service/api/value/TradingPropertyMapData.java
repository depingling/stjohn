
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingPropertyMapData
 * Description:  This is a ValueObject class wrapping the database table CLW_TRADING_PROPERTY_MAP.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TradingPropertyMapDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TradingPropertyMapData</code> is a ValueObject class wrapping of the database table CLW_TRADING_PROPERTY_MAP.
 */
public class TradingPropertyMapData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 729257399839720456L;
    private int mTradingPropertyMapId;// SQL type:NUMBER, not null
    private int mTradingProfileId;// SQL type:NUMBER
    private String mSetType;// SQL type:VARCHAR2
    private String mDirection;// SQL type:VARCHAR2
    private String mEntityProperty;// SQL type:VARCHAR2
    private String mPropertyTypeCd;// SQL type:VARCHAR2
    private String mQualifierCode;// SQL type:VARCHAR2
    private String mHardValue;// SQL type:VARCHAR2
    private String mMandatory;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mUseCode;// SQL type:VARCHAR2
    private int mOrderBy;// SQL type:NUMBER
    private String mTradingPropertyMapCode;// SQL type:VARCHAR2
    private String mPattern;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TradingPropertyMapData ()
    {
        mSetType = "";
        mDirection = "";
        mEntityProperty = "";
        mPropertyTypeCd = "";
        mQualifierCode = "";
        mHardValue = "";
        mMandatory = "";
        mAddBy = "";
        mModBy = "";
        mUseCode = "";
        mTradingPropertyMapCode = "";
        mPattern = "";
    }

    /**
     * Constructor.
     */
    public TradingPropertyMapData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, Date parm11, String parm12, Date parm13, String parm14, int parm15, String parm16, String parm17)
    {
        mTradingPropertyMapId = parm1;
        mTradingProfileId = parm2;
        mSetType = parm3;
        mDirection = parm4;
        mEntityProperty = parm5;
        mPropertyTypeCd = parm6;
        mQualifierCode = parm7;
        mHardValue = parm8;
        mMandatory = parm9;
        mAddBy = parm10;
        mAddDate = parm11;
        mModBy = parm12;
        mModDate = parm13;
        mUseCode = parm14;
        mOrderBy = parm15;
        mTradingPropertyMapCode = parm16;
        mPattern = parm17;
        
    }

    /**
     * Creates a new TradingPropertyMapData
     *
     * @return
     *  Newly initialized TradingPropertyMapData object.
     */
    public static TradingPropertyMapData createValue ()
    {
        TradingPropertyMapData valueData = new TradingPropertyMapData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingPropertyMapData object
     */
    public String toString()
    {
        return "[" + "TradingPropertyMapId=" + mTradingPropertyMapId + ", TradingProfileId=" + mTradingProfileId + ", SetType=" + mSetType + ", Direction=" + mDirection + ", EntityProperty=" + mEntityProperty + ", PropertyTypeCd=" + mPropertyTypeCd + ", QualifierCode=" + mQualifierCode + ", HardValue=" + mHardValue + ", Mandatory=" + mMandatory + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", UseCode=" + mUseCode + ", OrderBy=" + mOrderBy + ", TradingPropertyMapCode=" + mTradingPropertyMapCode + ", Pattern=" + mPattern + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TradingPropertyMap");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTradingPropertyMapId));

        node =  doc.createElement("TradingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileId)));
        root.appendChild(node);

        node =  doc.createElement("SetType");
        node.appendChild(doc.createTextNode(String.valueOf(mSetType)));
        root.appendChild(node);

        node =  doc.createElement("Direction");
        node.appendChild(doc.createTextNode(String.valueOf(mDirection)));
        root.appendChild(node);

        node =  doc.createElement("EntityProperty");
        node.appendChild(doc.createTextNode(String.valueOf(mEntityProperty)));
        root.appendChild(node);

        node =  doc.createElement("PropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("QualifierCode");
        node.appendChild(doc.createTextNode(String.valueOf(mQualifierCode)));
        root.appendChild(node);

        node =  doc.createElement("HardValue");
        node.appendChild(doc.createTextNode(String.valueOf(mHardValue)));
        root.appendChild(node);

        node =  doc.createElement("Mandatory");
        node.appendChild(doc.createTextNode(String.valueOf(mMandatory)));
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

        node =  doc.createElement("UseCode");
        node.appendChild(doc.createTextNode(String.valueOf(mUseCode)));
        root.appendChild(node);

        node =  doc.createElement("OrderBy");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBy)));
        root.appendChild(node);

        node =  doc.createElement("TradingPropertyMapCode");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingPropertyMapCode)));
        root.appendChild(node);

        node =  doc.createElement("Pattern");
        node.appendChild(doc.createTextNode(String.valueOf(mPattern)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TradingPropertyMapId field is not cloned.
    *
    * @return TradingPropertyMapData object
    */
    public Object clone(){
        TradingPropertyMapData myClone = new TradingPropertyMapData();
        
        myClone.mTradingProfileId = mTradingProfileId;
        
        myClone.mSetType = mSetType;
        
        myClone.mDirection = mDirection;
        
        myClone.mEntityProperty = mEntityProperty;
        
        myClone.mPropertyTypeCd = mPropertyTypeCd;
        
        myClone.mQualifierCode = mQualifierCode;
        
        myClone.mHardValue = mHardValue;
        
        myClone.mMandatory = mMandatory;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mUseCode = mUseCode;
        
        myClone.mOrderBy = mOrderBy;
        
        myClone.mTradingPropertyMapCode = mTradingPropertyMapCode;
        
        myClone.mPattern = mPattern;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (TradingPropertyMapDataAccess.TRADING_PROPERTY_MAP_ID.equals(pFieldName)) {
            return getTradingPropertyMapId();
        } else if (TradingPropertyMapDataAccess.TRADING_PROFILE_ID.equals(pFieldName)) {
            return getTradingProfileId();
        } else if (TradingPropertyMapDataAccess.SET_TYPE.equals(pFieldName)) {
            return getSetType();
        } else if (TradingPropertyMapDataAccess.DIRECTION.equals(pFieldName)) {
            return getDirection();
        } else if (TradingPropertyMapDataAccess.ENTITY_PROPERTY.equals(pFieldName)) {
            return getEntityProperty();
        } else if (TradingPropertyMapDataAccess.PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getPropertyTypeCd();
        } else if (TradingPropertyMapDataAccess.QUALIFIER_CODE.equals(pFieldName)) {
            return getQualifierCode();
        } else if (TradingPropertyMapDataAccess.HARD_VALUE.equals(pFieldName)) {
            return getHardValue();
        } else if (TradingPropertyMapDataAccess.MANDATORY.equals(pFieldName)) {
            return getMandatory();
        } else if (TradingPropertyMapDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TradingPropertyMapDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TradingPropertyMapDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TradingPropertyMapDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TradingPropertyMapDataAccess.USE_CODE.equals(pFieldName)) {
            return getUseCode();
        } else if (TradingPropertyMapDataAccess.ORDER_BY.equals(pFieldName)) {
            return getOrderBy();
        } else if (TradingPropertyMapDataAccess.TRADING_PROPERTY_MAP_CODE.equals(pFieldName)) {
            return getTradingPropertyMapCode();
        } else if (TradingPropertyMapDataAccess.PATTERN.equals(pFieldName)) {
            return getPattern();
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
        return TradingPropertyMapDataAccess.CLW_TRADING_PROPERTY_MAP;
    }

    
    /**
     * Sets the TradingPropertyMapId field. This field is required to be set in the database.
     *
     * @param pTradingPropertyMapId
     *  int to use to update the field.
     */
    public void setTradingPropertyMapId(int pTradingPropertyMapId){
        this.mTradingPropertyMapId = pTradingPropertyMapId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPropertyMapId field.
     *
     * @return
     *  int containing the TradingPropertyMapId field.
     */
    public int getTradingPropertyMapId(){
        return mTradingPropertyMapId;
    }

    /**
     * Sets the TradingProfileId field.
     *
     * @param pTradingProfileId
     *  int to use to update the field.
     */
    public void setTradingProfileId(int pTradingProfileId){
        this.mTradingProfileId = pTradingProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingProfileId field.
     *
     * @return
     *  int containing the TradingProfileId field.
     */
    public int getTradingProfileId(){
        return mTradingProfileId;
    }

    /**
     * Sets the SetType field.
     *
     * @param pSetType
     *  String to use to update the field.
     */
    public void setSetType(String pSetType){
        this.mSetType = pSetType;
        setDirty(true);
    }
    /**
     * Retrieves the SetType field.
     *
     * @return
     *  String containing the SetType field.
     */
    public String getSetType(){
        return mSetType;
    }

    /**
     * Sets the Direction field.
     *
     * @param pDirection
     *  String to use to update the field.
     */
    public void setDirection(String pDirection){
        this.mDirection = pDirection;
        setDirty(true);
    }
    /**
     * Retrieves the Direction field.
     *
     * @return
     *  String containing the Direction field.
     */
    public String getDirection(){
        return mDirection;
    }

    /**
     * Sets the EntityProperty field.
     *
     * @param pEntityProperty
     *  String to use to update the field.
     */
    public void setEntityProperty(String pEntityProperty){
        this.mEntityProperty = pEntityProperty;
        setDirty(true);
    }
    /**
     * Retrieves the EntityProperty field.
     *
     * @return
     *  String containing the EntityProperty field.
     */
    public String getEntityProperty(){
        return mEntityProperty;
    }

    /**
     * Sets the PropertyTypeCd field.
     *
     * @param pPropertyTypeCd
     *  String to use to update the field.
     */
    public void setPropertyTypeCd(String pPropertyTypeCd){
        this.mPropertyTypeCd = pPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyTypeCd field.
     *
     * @return
     *  String containing the PropertyTypeCd field.
     */
    public String getPropertyTypeCd(){
        return mPropertyTypeCd;
    }

    /**
     * Sets the QualifierCode field.
     *
     * @param pQualifierCode
     *  String to use to update the field.
     */
    public void setQualifierCode(String pQualifierCode){
        this.mQualifierCode = pQualifierCode;
        setDirty(true);
    }
    /**
     * Retrieves the QualifierCode field.
     *
     * @return
     *  String containing the QualifierCode field.
     */
    public String getQualifierCode(){
        return mQualifierCode;
    }

    /**
     * Sets the HardValue field.
     *
     * @param pHardValue
     *  String to use to update the field.
     */
    public void setHardValue(String pHardValue){
        this.mHardValue = pHardValue;
        setDirty(true);
    }
    /**
     * Retrieves the HardValue field.
     *
     * @return
     *  String containing the HardValue field.
     */
    public String getHardValue(){
        return mHardValue;
    }

    /**
     * Sets the Mandatory field.
     *
     * @param pMandatory
     *  String to use to update the field.
     */
    public void setMandatory(String pMandatory){
        this.mMandatory = pMandatory;
        setDirty(true);
    }
    /**
     * Retrieves the Mandatory field.
     *
     * @return
     *  String containing the Mandatory field.
     */
    public String getMandatory(){
        return mMandatory;
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
     * Sets the AddDate field.
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

    /**
     * Sets the UseCode field.
     *
     * @param pUseCode
     *  String to use to update the field.
     */
    public void setUseCode(String pUseCode){
        this.mUseCode = pUseCode;
        setDirty(true);
    }
    /**
     * Retrieves the UseCode field.
     *
     * @return
     *  String containing the UseCode field.
     */
    public String getUseCode(){
        return mUseCode;
    }

    /**
     * Sets the OrderBy field.
     *
     * @param pOrderBy
     *  int to use to update the field.
     */
    public void setOrderBy(int pOrderBy){
        this.mOrderBy = pOrderBy;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBy field.
     *
     * @return
     *  int containing the OrderBy field.
     */
    public int getOrderBy(){
        return mOrderBy;
    }

    /**
     * Sets the TradingPropertyMapCode field.
     *
     * @param pTradingPropertyMapCode
     *  String to use to update the field.
     */
    public void setTradingPropertyMapCode(String pTradingPropertyMapCode){
        this.mTradingPropertyMapCode = pTradingPropertyMapCode;
        setDirty(true);
    }
    /**
     * Retrieves the TradingPropertyMapCode field.
     *
     * @return
     *  String containing the TradingPropertyMapCode field.
     */
    public String getTradingPropertyMapCode(){
        return mTradingPropertyMapCode;
    }

    /**
     * Sets the Pattern field.
     *
     * @param pPattern
     *  String to use to update the field.
     */
    public void setPattern(String pPattern){
        this.mPattern = pPattern;
        setDirty(true);
    }
    /**
     * Retrieves the Pattern field.
     *
     * @return
     *  String containing the Pattern field.
     */
    public String getPattern(){
        return mPattern;
    }


}
