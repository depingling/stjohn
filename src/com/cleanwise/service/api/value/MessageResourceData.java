
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        MessageResourceData
 * Description:  This is a ValueObject class wrapping the database table CLW_MESSAGE_RESOURCE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.MessageResourceDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>MessageResourceData</code> is a ValueObject class wrapping of the database table CLW_MESSAGE_RESOURCE.
 */
public class MessageResourceData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7236903007094112905L;
    private int mMessageResourceId;// SQL type:NUMBER, not null
    private String mLocale;// SQL type:VARCHAR2
    private int mBusEntityId;// SQL type:NUMBER
    private String mName;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public MessageResourceData ()
    {
        mLocale = "";
        mName = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public MessageResourceData(int parm1, String parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9)
    {
        mMessageResourceId = parm1;
        mLocale = parm2;
        mBusEntityId = parm3;
        mName = parm4;
        mValue = parm5;
        mAddBy = parm6;
        mAddDate = parm7;
        mModBy = parm8;
        mModDate = parm9;
        
    }

    /**
     * Creates a new MessageResourceData
     *
     * @return
     *  Newly initialized MessageResourceData object.
     */
    public static MessageResourceData createValue ()
    {
        MessageResourceData valueData = new MessageResourceData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this MessageResourceData object
     */
    public String toString()
    {
        return "[" + "MessageResourceId=" + mMessageResourceId + ", Locale=" + mLocale + ", BusEntityId=" + mBusEntityId + ", Name=" + mName + ", Value=" + mValue + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("MessageResource");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mMessageResourceId));

        node =  doc.createElement("Locale");
        node.appendChild(doc.createTextNode(String.valueOf(mLocale)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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

        return root;
    }

    /**
    * creates a clone of this object, the MessageResourceId field is not cloned.
    *
    * @return MessageResourceData object
    */
    public Object clone(){
        MessageResourceData myClone = new MessageResourceData();
        
        myClone.mLocale = mLocale;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mName = mName;
        
        myClone.mValue = mValue;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (MessageResourceDataAccess.MESSAGE_RESOURCE_ID.equals(pFieldName)) {
            return getMessageResourceId();
        } else if (MessageResourceDataAccess.LOCALE.equals(pFieldName)) {
            return getLocale();
        } else if (MessageResourceDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (MessageResourceDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (MessageResourceDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (MessageResourceDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (MessageResourceDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (MessageResourceDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (MessageResourceDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return MessageResourceDataAccess.CLW_MESSAGE_RESOURCE;
    }

    
    /**
     * Sets the MessageResourceId field. This field is required to be set in the database.
     *
     * @param pMessageResourceId
     *  int to use to update the field.
     */
    public void setMessageResourceId(int pMessageResourceId){
        this.mMessageResourceId = pMessageResourceId;
        setDirty(true);
    }
    /**
     * Retrieves the MessageResourceId field.
     *
     * @return
     *  int containing the MessageResourceId field.
     */
    public int getMessageResourceId(){
        return mMessageResourceId;
    }

    /**
     * Sets the Locale field.
     *
     * @param pLocale
     *  String to use to update the field.
     */
    public void setLocale(String pLocale){
        this.mLocale = pLocale;
        setDirty(true);
    }
    /**
     * Retrieves the Locale field.
     *
     * @return
     *  String containing the Locale field.
     */
    public String getLocale(){
        return mLocale;
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
     * Sets the Name field.
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
     * Sets the Value field.
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


}
