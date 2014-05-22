
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityParameterData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUS_ENTITY_PARAMETER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BusEntityParameterData</code> is a ValueObject class wrapping of the database table CLW_BUS_ENTITY_PARAMETER.
 */
public class BusEntityParameterData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5482509857890135658L;
    private int mBusEntityParameterId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mName;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BusEntityParameterData ()
    {
        mName = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BusEntityParameterData(int parm1, int parm2, String parm3, String parm4, Date parm5, Date parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mBusEntityParameterId = parm1;
        mBusEntityId = parm2;
        mName = parm3;
        mValue = parm4;
        mEffDate = parm5;
        mExpDate = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new BusEntityParameterData
     *
     * @return
     *  Newly initialized BusEntityParameterData object.
     */
    public static BusEntityParameterData createValue ()
    {
        BusEntityParameterData valueData = new BusEntityParameterData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BusEntityParameterData object
     */
    public String toString()
    {
        return "[" + "BusEntityParameterId=" + mBusEntityParameterId + ", BusEntityId=" + mBusEntityId + ", Name=" + mName + ", Value=" + mValue + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BusEntityParameter");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBusEntityParameterId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
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
    * creates a clone of this object, the BusEntityParameterId field is not cloned.
    *
    * @return BusEntityParameterData object
    */
    public Object clone(){
        BusEntityParameterData myClone = new BusEntityParameterData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mName = mName;
        
        myClone.mValue = mValue;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
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

        if (BusEntityParameterDataAccess.BUS_ENTITY_PARAMETER_ID.equals(pFieldName)) {
            return getBusEntityParameterId();
        } else if (BusEntityParameterDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (BusEntityParameterDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (BusEntityParameterDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (BusEntityParameterDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (BusEntityParameterDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (BusEntityParameterDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BusEntityParameterDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BusEntityParameterDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BusEntityParameterDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BusEntityParameterDataAccess.CLW_BUS_ENTITY_PARAMETER;
    }

    
    /**
     * Sets the BusEntityParameterId field. This field is required to be set in the database.
     *
     * @param pBusEntityParameterId
     *  int to use to update the field.
     */
    public void setBusEntityParameterId(int pBusEntityParameterId){
        this.mBusEntityParameterId = pBusEntityParameterId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityParameterId field.
     *
     * @return
     *  int containing the BusEntityParameterId field.
     */
    public int getBusEntityParameterId(){
        return mBusEntityParameterId;
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
     * Sets the Name field. This field is required to be set in the database.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the Value field. This field is required to be set in the database.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
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
