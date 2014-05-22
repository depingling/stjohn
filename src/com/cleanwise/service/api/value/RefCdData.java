
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RefCdData
 * Description:  This is a ValueObject class wrapping the database table CLW_REF_CD.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.RefCdDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>RefCdData</code> is a ValueObject class wrapping of the database table CLW_REF_CD.
 */
public class RefCdData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5456367506984619030L;
    private int mRefCdId;// SQL type:NUMBER, not null
    private String mRefCd;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private String mRefCdType;// SQL type:VARCHAR2, not null
    private String mRefStatusCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public RefCdData ()
    {
        mRefCd = "";
        mShortDesc = "";
        mValue = "";
        mRefCdType = "";
        mRefStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public RefCdData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mRefCdId = parm1;
        mRefCd = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mRefCdType = parm5;
        mRefStatusCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new RefCdData
     *
     * @return
     *  Newly initialized RefCdData object.
     */
    public static RefCdData createValue ()
    {
        RefCdData valueData = new RefCdData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RefCdData object
     */
    public String toString()
    {
        return "[" + "RefCdId=" + mRefCdId + ", RefCd=" + mRefCd + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", RefCdType=" + mRefCdType + ", RefStatusCd=" + mRefStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("RefCd");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mRefCdId));

        node =  doc.createElement("RefCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRefCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("RefCdType");
        node.appendChild(doc.createTextNode(String.valueOf(mRefCdType)));
        root.appendChild(node);

        node =  doc.createElement("RefStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRefStatusCd)));
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
    * creates a clone of this object, the RefCdId field is not cloned.
    *
    * @return RefCdData object
    */
    public Object clone(){
        RefCdData myClone = new RefCdData();
        
        myClone.mRefCd = mRefCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mRefCdType = mRefCdType;
        
        myClone.mRefStatusCd = mRefStatusCd;
        
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

        if (RefCdDataAccess.REF_CD_ID.equals(pFieldName)) {
            return getRefCdId();
        } else if (RefCdDataAccess.REF_CD.equals(pFieldName)) {
            return getRefCd();
        } else if (RefCdDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (RefCdDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (RefCdDataAccess.REF_CD_TYPE.equals(pFieldName)) {
            return getRefCdType();
        } else if (RefCdDataAccess.REF_STATUS_CD.equals(pFieldName)) {
            return getRefStatusCd();
        } else if (RefCdDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (RefCdDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (RefCdDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (RefCdDataAccess.MOD_BY.equals(pFieldName)) {
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
        return RefCdDataAccess.CLW_REF_CD;
    }

    
    /**
     * Sets the RefCdId field. This field is required to be set in the database.
     *
     * @param pRefCdId
     *  int to use to update the field.
     */
    public void setRefCdId(int pRefCdId){
        this.mRefCdId = pRefCdId;
        setDirty(true);
    }
    /**
     * Retrieves the RefCdId field.
     *
     * @return
     *  int containing the RefCdId field.
     */
    public int getRefCdId(){
        return mRefCdId;
    }

    /**
     * Sets the RefCd field. This field is required to be set in the database.
     *
     * @param pRefCd
     *  String to use to update the field.
     */
    public void setRefCd(String pRefCd){
        this.mRefCd = pRefCd;
        setDirty(true);
    }
    /**
     * Retrieves the RefCd field.
     *
     * @return
     *  String containing the RefCd field.
     */
    public String getRefCd(){
        return mRefCd;
    }

    /**
     * Sets the ShortDesc field. This field is required to be set in the database.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
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
     * Sets the RefCdType field. This field is required to be set in the database.
     *
     * @param pRefCdType
     *  String to use to update the field.
     */
    public void setRefCdType(String pRefCdType){
        this.mRefCdType = pRefCdType;
        setDirty(true);
    }
    /**
     * Retrieves the RefCdType field.
     *
     * @return
     *  String containing the RefCdType field.
     */
    public String getRefCdType(){
        return mRefCdType;
    }

    /**
     * Sets the RefStatusCd field. This field is required to be set in the database.
     *
     * @param pRefStatusCd
     *  String to use to update the field.
     */
    public void setRefStatusCd(String pRefStatusCd){
        this.mRefStatusCd = pRefStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RefStatusCd field.
     *
     * @return
     *  String containing the RefStatusCd field.
     */
    public String getRefStatusCd(){
        return mRefStatusCd;
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
