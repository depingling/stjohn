
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TradingProfileConfigData
 * Description:  This is a ValueObject class wrapping the database table CLW_TRADING_PROFILE_CONFIG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TradingProfileConfigDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TradingProfileConfigData</code> is a ValueObject class wrapping of the database table CLW_TRADING_PROFILE_CONFIG.
 */
public class TradingProfileConfigData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5950342829943745191L;
    private int mTradingProfileConfigId;// SQL type:NUMBER, not null
    private int mTradingProfileId;// SQL type:NUMBER, not null
    private int mIncomingTradingProfileId;// SQL type:NUMBER
    private String mSetType;// SQL type:VARCHAR2, not null
    private String mDirection;// SQL type:VARCHAR2, not null
    private String mClassname;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mPattern;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public TradingProfileConfigData ()
    {
        mSetType = "";
        mDirection = "";
        mClassname = "";
        mAddBy = "";
        mModBy = "";
        mPattern = "";
    }

    /**
     * Constructor.
     */
    public TradingProfileConfigData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, String parm11)
    {
        mTradingProfileConfigId = parm1;
        mTradingProfileId = parm2;
        mIncomingTradingProfileId = parm3;
        mSetType = parm4;
        mDirection = parm5;
        mClassname = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mPattern = parm11;
        
    }

    /**
     * Creates a new TradingProfileConfigData
     *
     * @return
     *  Newly initialized TradingProfileConfigData object.
     */
    public static TradingProfileConfigData createValue ()
    {
        TradingProfileConfigData valueData = new TradingProfileConfigData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TradingProfileConfigData object
     */
    public String toString()
    {
        return "[" + "TradingProfileConfigId=" + mTradingProfileConfigId + ", TradingProfileId=" + mTradingProfileId + ", IncomingTradingProfileId=" + mIncomingTradingProfileId + ", SetType=" + mSetType + ", Direction=" + mDirection + ", Classname=" + mClassname + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Pattern=" + mPattern + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TradingProfileConfig");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTradingProfileConfigId));

        node =  doc.createElement("TradingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mTradingProfileId)));
        root.appendChild(node);

        node =  doc.createElement("IncomingTradingProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mIncomingTradingProfileId)));
        root.appendChild(node);

        node =  doc.createElement("SetType");
        node.appendChild(doc.createTextNode(String.valueOf(mSetType)));
        root.appendChild(node);

        node =  doc.createElement("Direction");
        node.appendChild(doc.createTextNode(String.valueOf(mDirection)));
        root.appendChild(node);

        node =  doc.createElement("Classname");
        node.appendChild(doc.createTextNode(String.valueOf(mClassname)));
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

        node =  doc.createElement("Pattern");
        node.appendChild(doc.createTextNode(String.valueOf(mPattern)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the TradingProfileConfigId field is not cloned.
    *
    * @return TradingProfileConfigData object
    */
    public Object clone(){
        TradingProfileConfigData myClone = new TradingProfileConfigData();
        
        myClone.mTradingProfileId = mTradingProfileId;
        
        myClone.mIncomingTradingProfileId = mIncomingTradingProfileId;
        
        myClone.mSetType = mSetType;
        
        myClone.mDirection = mDirection;
        
        myClone.mClassname = mClassname;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
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

        if (TradingProfileConfigDataAccess.TRADING_PROFILE_CONFIG_ID.equals(pFieldName)) {
            return getTradingProfileConfigId();
        } else if (TradingProfileConfigDataAccess.TRADING_PROFILE_ID.equals(pFieldName)) {
            return getTradingProfileId();
        } else if (TradingProfileConfigDataAccess.INCOMING_TRADING_PROFILE_ID.equals(pFieldName)) {
            return getIncomingTradingProfileId();
        } else if (TradingProfileConfigDataAccess.SET_TYPE.equals(pFieldName)) {
            return getSetType();
        } else if (TradingProfileConfigDataAccess.DIRECTION.equals(pFieldName)) {
            return getDirection();
        } else if (TradingProfileConfigDataAccess.CLASSNAME.equals(pFieldName)) {
            return getClassname();
        } else if (TradingProfileConfigDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TradingProfileConfigDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TradingProfileConfigDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (TradingProfileConfigDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TradingProfileConfigDataAccess.PATTERN.equals(pFieldName)) {
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
        return TradingProfileConfigDataAccess.CLW_TRADING_PROFILE_CONFIG;
    }

    
    /**
     * Sets the TradingProfileConfigId field. This field is required to be set in the database.
     *
     * @param pTradingProfileConfigId
     *  int to use to update the field.
     */
    public void setTradingProfileConfigId(int pTradingProfileConfigId){
        this.mTradingProfileConfigId = pTradingProfileConfigId;
        setDirty(true);
    }
    /**
     * Retrieves the TradingProfileConfigId field.
     *
     * @return
     *  int containing the TradingProfileConfigId field.
     */
    public int getTradingProfileConfigId(){
        return mTradingProfileConfigId;
    }

    /**
     * Sets the TradingProfileId field. This field is required to be set in the database.
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
     * Sets the IncomingTradingProfileId field.
     *
     * @param pIncomingTradingProfileId
     *  int to use to update the field.
     */
    public void setIncomingTradingProfileId(int pIncomingTradingProfileId){
        this.mIncomingTradingProfileId = pIncomingTradingProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the IncomingTradingProfileId field.
     *
     * @return
     *  int containing the IncomingTradingProfileId field.
     */
    public int getIncomingTradingProfileId(){
        return mIncomingTradingProfileId;
    }

    /**
     * Sets the SetType field. This field is required to be set in the database.
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
     * Sets the Direction field. This field is required to be set in the database.
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
     * Sets the Classname field. This field is required to be set in the database.
     *
     * @param pClassname
     *  String to use to update the field.
     */
    public void setClassname(String pClassname){
        this.mClassname = pClassname;
        setDirty(true);
    }
    /**
     * Retrieves the Classname field.
     *
     * @return
     *  String containing the Classname field.
     */
    public String getClassname(){
        return mClassname;
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
