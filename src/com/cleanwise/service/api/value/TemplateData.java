
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TemplateData
 * Description:  This is a ValueObject class wrapping the database table CLW_TEMPLATE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TemplateDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TemplateData</code> is a ValueObject class wrapping of the database table CLW_TEMPLATE.
 */
public class TemplateData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6856695088627155142L;
    private int mTemplateId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mName;// SQL type:VARCHAR2, not null
    private String mType;// SQL type:VARCHAR2, not null
    private String mContent;// SQL type:CLOB, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public TemplateData ()
    {
        mName = "";
        mType = "";
        mContent = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public TemplateData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9)
    {
        mTemplateId = parm1;
        mBusEntityId = parm2;
        mName = parm3;
        mType = parm4;
        mContent = parm5;
        mAddBy = parm6;
        mAddDate = parm7;
        mModBy = parm8;
        mModDate = parm9;
        
    }

    /**
     * Creates a new TemplateData
     *
     * @return
     *  Newly initialized TemplateData object.
     */
    public static TemplateData createValue ()
    {
        TemplateData valueData = new TemplateData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TemplateData object
     */
    public String toString()
    {
        return "[" + "TemplateId=" + mTemplateId + ", BusEntityId=" + mBusEntityId + ", Name=" + mName + ", Type=" + mType + ", Content=" + mContent + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Template");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTemplateId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        node =  doc.createElement("Content");
        node.appendChild(doc.createTextNode(String.valueOf(mContent)));
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
    * creates a clone of this object, the TemplateId field is not cloned.
    *
    * @return TemplateData object
    */
    public Object clone(){
        TemplateData myClone = new TemplateData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mName = mName;
        
        myClone.mType = mType;
        
        myClone.mContent = mContent;
        
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

        if (TemplateDataAccess.TEMPLATE_ID.equals(pFieldName)) {
            return getTemplateId();
        } else if (TemplateDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (TemplateDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (TemplateDataAccess.TYPE.equals(pFieldName)) {
            return getType();
        } else if (TemplateDataAccess.CONTENT.equals(pFieldName)) {
            return getContent();
        } else if (TemplateDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TemplateDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TemplateDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TemplateDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return TemplateDataAccess.CLW_TEMPLATE;
    }

    
    /**
     * Sets the TemplateId field. This field is required to be set in the database.
     *
     * @param pTemplateId
     *  int to use to update the field.
     */
    public void setTemplateId(int pTemplateId){
        this.mTemplateId = pTemplateId;
        setDirty(true);
    }
    /**
     * Retrieves the TemplateId field.
     *
     * @return
     *  int containing the TemplateId field.
     */
    public int getTemplateId(){
        return mTemplateId;
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
     * Sets the Type field. This field is required to be set in the database.
     *
     * @param pType
     *  String to use to update the field.
     */
    public void setType(String pType){
        this.mType = pType;
        setDirty(true);
    }
    /**
     * Retrieves the Type field.
     *
     * @return
     *  String containing the Type field.
     */
    public String getType(){
        return mType;
    }

    /**
     * Sets the Content field. This field is required to be set in the database.
     *
     * @param pContent
     *  String to use to update the field.
     */
    public void setContent(String pContent){
        this.mContent = pContent;
        setDirty(true);
    }
    /**
     * Retrieves the Content field.
     *
     * @return
     *  String containing the Content field.
     */
    public String getContent(){
        return mContent;
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
