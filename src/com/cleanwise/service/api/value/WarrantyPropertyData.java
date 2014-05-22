
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_WARRANTY_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WarrantyPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WarrantyPropertyData</code> is a ValueObject class wrapping of the database table CLW_WARRANTY_PROPERTY.
 */
public class WarrantyPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4907892825816600942L;
    private int mWarrantyPropertyId;// SQL type:NUMBER, not null
    private int mWarrantyId;// SQL type:NUMBER, not null
    private String mWarrantyPropertyCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public WarrantyPropertyData ()
    {
        mWarrantyPropertyCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WarrantyPropertyData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mWarrantyPropertyId = parm1;
        mWarrantyId = parm2;
        mWarrantyPropertyCd = parm3;
        mValue = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new WarrantyPropertyData
     *
     * @return
     *  Newly initialized WarrantyPropertyData object.
     */
    public static WarrantyPropertyData createValue ()
    {
        WarrantyPropertyData valueData = new WarrantyPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyPropertyData object
     */
    public String toString()
    {
        return "[" + "WarrantyPropertyId=" + mWarrantyPropertyId + ", WarrantyId=" + mWarrantyId + ", WarrantyPropertyCd=" + mWarrantyPropertyCd + ", Value=" + mValue + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WarrantyProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWarrantyPropertyId));

        node =  doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
        root.appendChild(node);

        node =  doc.createElement("WarrantyPropertyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyPropertyCd)));
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
    * creates a clone of this object, the WarrantyPropertyId field is not cloned.
    *
    * @return WarrantyPropertyData object
    */
    public Object clone(){
        WarrantyPropertyData myClone = new WarrantyPropertyData();
        
        myClone.mWarrantyId = mWarrantyId;
        
        myClone.mWarrantyPropertyCd = mWarrantyPropertyCd;
        
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

        if (WarrantyPropertyDataAccess.WARRANTY_PROPERTY_ID.equals(pFieldName)) {
            return getWarrantyPropertyId();
        } else if (WarrantyPropertyDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (WarrantyPropertyDataAccess.WARRANTY_PROPERTY_CD.equals(pFieldName)) {
            return getWarrantyPropertyCd();
        } else if (WarrantyPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (WarrantyPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WarrantyPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WarrantyPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (WarrantyPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return WarrantyPropertyDataAccess.CLW_WARRANTY_PROPERTY;
    }

    
    /**
     * Sets the WarrantyPropertyId field. This field is required to be set in the database.
     *
     * @param pWarrantyPropertyId
     *  int to use to update the field.
     */
    public void setWarrantyPropertyId(int pWarrantyPropertyId){
        this.mWarrantyPropertyId = pWarrantyPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyPropertyId field.
     *
     * @return
     *  int containing the WarrantyPropertyId field.
     */
    public int getWarrantyPropertyId(){
        return mWarrantyPropertyId;
    }

    /**
     * Sets the WarrantyId field. This field is required to be set in the database.
     *
     * @param pWarrantyId
     *  int to use to update the field.
     */
    public void setWarrantyId(int pWarrantyId){
        this.mWarrantyId = pWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyId field.
     *
     * @return
     *  int containing the WarrantyId field.
     */
    public int getWarrantyId(){
        return mWarrantyId;
    }

    /**
     * Sets the WarrantyPropertyCd field. This field is required to be set in the database.
     *
     * @param pWarrantyPropertyCd
     *  String to use to update the field.
     */
    public void setWarrantyPropertyCd(String pWarrantyPropertyCd){
        this.mWarrantyPropertyCd = pWarrantyPropertyCd;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyPropertyCd field.
     *
     * @return
     *  String containing the WarrantyPropertyCd field.
     */
    public String getWarrantyPropertyCd(){
        return mWarrantyPropertyCd;
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
