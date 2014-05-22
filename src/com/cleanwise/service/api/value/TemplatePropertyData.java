
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TemplatePropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_TEMPLATE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.TemplatePropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>TemplatePropertyData</code> is a ValueObject class wrapping of the database table CLW_TEMPLATE_PROPERTY.
 */
public class TemplatePropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1254704127880480589L;
    private int mTemplatePropertyId;// SQL type:NUMBER, not null
    private int mTemplateId;// SQL type:NUMBER, not null
    private String mTemplatePropertyCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public TemplatePropertyData ()
    {
        mTemplatePropertyCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public TemplatePropertyData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mTemplatePropertyId = parm1;
        mTemplateId = parm2;
        mTemplatePropertyCd = parm3;
        mValue = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new TemplatePropertyData
     *
     * @return
     *  Newly initialized TemplatePropertyData object.
     */
    public static TemplatePropertyData createValue ()
    {
        TemplatePropertyData valueData = new TemplatePropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TemplatePropertyData object
     */
    public String toString()
    {
        return "[" + "TemplatePropertyId=" + mTemplatePropertyId + ", TemplateId=" + mTemplateId + ", TemplatePropertyCd=" + mTemplatePropertyCd + ", Value=" + mValue + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("TemplateProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mTemplatePropertyId));

        node =  doc.createElement("TemplateId");
        node.appendChild(doc.createTextNode(String.valueOf(mTemplateId)));
        root.appendChild(node);

        node =  doc.createElement("TemplatePropertyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTemplatePropertyCd)));
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
    * creates a clone of this object, the TemplatePropertyId field is not cloned.
    *
    * @return TemplatePropertyData object
    */
    public Object clone(){
        TemplatePropertyData myClone = new TemplatePropertyData();
        
        myClone.mTemplateId = mTemplateId;
        
        myClone.mTemplatePropertyCd = mTemplatePropertyCd;
        
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

        if (TemplatePropertyDataAccess.TEMPLATE_PROPERTY_ID.equals(pFieldName)) {
            return getTemplatePropertyId();
        } else if (TemplatePropertyDataAccess.TEMPLATE_ID.equals(pFieldName)) {
            return getTemplateId();
        } else if (TemplatePropertyDataAccess.TEMPLATE_PROPERTY_CD.equals(pFieldName)) {
            return getTemplatePropertyCd();
        } else if (TemplatePropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (TemplatePropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (TemplatePropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (TemplatePropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (TemplatePropertyDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return TemplatePropertyDataAccess.CLW_TEMPLATE_PROPERTY;
    }

    
    /**
     * Sets the TemplatePropertyId field. This field is required to be set in the database.
     *
     * @param pTemplatePropertyId
     *  int to use to update the field.
     */
    public void setTemplatePropertyId(int pTemplatePropertyId){
        this.mTemplatePropertyId = pTemplatePropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the TemplatePropertyId field.
     *
     * @return
     *  int containing the TemplatePropertyId field.
     */
    public int getTemplatePropertyId(){
        return mTemplatePropertyId;
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
     * Sets the TemplatePropertyCd field. This field is required to be set in the database.
     *
     * @param pTemplatePropertyCd
     *  String to use to update the field.
     */
    public void setTemplatePropertyCd(String pTemplatePropertyCd){
        this.mTemplatePropertyCd = pTemplatePropertyCd;
        setDirty(true);
    }
    /**
     * Retrieves the TemplatePropertyCd field.
     *
     * @return
     *  String containing the TemplatePropertyCd field.
     */
    public String getTemplatePropertyCd(){
        return mTemplatePropertyCd;
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
