
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RemittancePropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_REMITTANCE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.RemittancePropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>RemittancePropertyData</code> is a ValueObject class wrapping of the database table CLW_REMITTANCE_PROPERTY.
 */
public class RemittancePropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3574130518719014939L;
    private int mRemittancePropertyId;// SQL type:NUMBER, not null
    private int mRemittanceDetailId;// SQL type:NUMBER
    private int mRemittanceId;// SQL type:NUMBER
    private String mValue;// SQL type:VARCHAR2
    private String mRemittancePropertyStatusCd;// SQL type:VARCHAR2
    private String mRemittancePropertyTypeCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public RemittancePropertyData ()
    {
        mValue = "";
        mRemittancePropertyStatusCd = "";
        mRemittancePropertyTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public RemittancePropertyData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mRemittancePropertyId = parm1;
        mRemittanceDetailId = parm2;
        mRemittanceId = parm3;
        mValue = parm4;
        mRemittancePropertyStatusCd = parm5;
        mRemittancePropertyTypeCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new RemittancePropertyData
     *
     * @return
     *  Newly initialized RemittancePropertyData object.
     */
    public static RemittancePropertyData createValue ()
    {
        RemittancePropertyData valueData = new RemittancePropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RemittancePropertyData object
     */
    public String toString()
    {
        return "[" + "RemittancePropertyId=" + mRemittancePropertyId + ", RemittanceDetailId=" + mRemittanceDetailId + ", RemittanceId=" + mRemittanceId + ", Value=" + mValue + ", RemittancePropertyStatusCd=" + mRemittancePropertyStatusCd + ", RemittancePropertyTypeCd=" + mRemittancePropertyTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("RemittanceProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mRemittancePropertyId));

        node =  doc.createElement("RemittanceDetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailId)));
        root.appendChild(node);

        node =  doc.createElement("RemittanceId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceId)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("RemittancePropertyStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("RemittancePropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittancePropertyTypeCd)));
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
    * creates a clone of this object, the RemittancePropertyId field is not cloned.
    *
    * @return RemittancePropertyData object
    */
    public Object clone(){
        RemittancePropertyData myClone = new RemittancePropertyData();
        
        myClone.mRemittanceDetailId = mRemittanceDetailId;
        
        myClone.mRemittanceId = mRemittanceId;
        
        myClone.mValue = mValue;
        
        myClone.mRemittancePropertyStatusCd = mRemittancePropertyStatusCd;
        
        myClone.mRemittancePropertyTypeCd = mRemittancePropertyTypeCd;
        
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

        if (RemittancePropertyDataAccess.REMITTANCE_PROPERTY_ID.equals(pFieldName)) {
            return getRemittancePropertyId();
        } else if (RemittancePropertyDataAccess.REMITTANCE_DETAIL_ID.equals(pFieldName)) {
            return getRemittanceDetailId();
        } else if (RemittancePropertyDataAccess.REMITTANCE_ID.equals(pFieldName)) {
            return getRemittanceId();
        } else if (RemittancePropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (RemittancePropertyDataAccess.REMITTANCE_PROPERTY_STATUS_CD.equals(pFieldName)) {
            return getRemittancePropertyStatusCd();
        } else if (RemittancePropertyDataAccess.REMITTANCE_PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getRemittancePropertyTypeCd();
        } else if (RemittancePropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (RemittancePropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (RemittancePropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (RemittancePropertyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return RemittancePropertyDataAccess.CLW_REMITTANCE_PROPERTY;
    }

    
    /**
     * Sets the RemittancePropertyId field. This field is required to be set in the database.
     *
     * @param pRemittancePropertyId
     *  int to use to update the field.
     */
    public void setRemittancePropertyId(int pRemittancePropertyId){
        this.mRemittancePropertyId = pRemittancePropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the RemittancePropertyId field.
     *
     * @return
     *  int containing the RemittancePropertyId field.
     */
    public int getRemittancePropertyId(){
        return mRemittancePropertyId;
    }

    /**
     * Sets the RemittanceDetailId field.
     *
     * @param pRemittanceDetailId
     *  int to use to update the field.
     */
    public void setRemittanceDetailId(int pRemittanceDetailId){
        this.mRemittanceDetailId = pRemittanceDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the RemittanceDetailId field.
     *
     * @return
     *  int containing the RemittanceDetailId field.
     */
    public int getRemittanceDetailId(){
        return mRemittanceDetailId;
    }

    /**
     * Sets the RemittanceId field.
     *
     * @param pRemittanceId
     *  int to use to update the field.
     */
    public void setRemittanceId(int pRemittanceId){
        this.mRemittanceId = pRemittanceId;
        setDirty(true);
    }
    /**
     * Retrieves the RemittanceId field.
     *
     * @return
     *  int containing the RemittanceId field.
     */
    public int getRemittanceId(){
        return mRemittanceId;
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
     * Sets the RemittancePropertyStatusCd field.
     *
     * @param pRemittancePropertyStatusCd
     *  String to use to update the field.
     */
    public void setRemittancePropertyStatusCd(String pRemittancePropertyStatusCd){
        this.mRemittancePropertyStatusCd = pRemittancePropertyStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RemittancePropertyStatusCd field.
     *
     * @return
     *  String containing the RemittancePropertyStatusCd field.
     */
    public String getRemittancePropertyStatusCd(){
        return mRemittancePropertyStatusCd;
    }

    /**
     * Sets the RemittancePropertyTypeCd field.
     *
     * @param pRemittancePropertyTypeCd
     *  String to use to update the field.
     */
    public void setRemittancePropertyTypeCd(String pRemittancePropertyTypeCd){
        this.mRemittancePropertyTypeCd = pRemittancePropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the RemittancePropertyTypeCd field.
     *
     * @return
     *  String containing the RemittancePropertyTypeCd field.
     */
    public String getRemittancePropertyTypeCd(){
        return mRemittancePropertyTypeCd;
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
