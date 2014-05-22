
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiControlElementData
 * Description:  This is a ValueObject class wrapping the database table CLW_UI_CONTROL_ELEMENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UiControlElementDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UiControlElementData</code> is a ValueObject class wrapping of the database table CLW_UI_CONTROL_ELEMENT.
 */
public class UiControlElementData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mUiControlElementId;// SQL type:NUMBER, not null
    private int mUiControlId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public UiControlElementData ()
    {
        mShortDesc = "";
        mTypeCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UiControlElementData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9)
    {
        mUiControlElementId = parm1;
        mUiControlId = parm2;
        mShortDesc = parm3;
        mTypeCd = parm4;
        mValue = parm5;
        mAddBy = parm6;
        mAddDate = parm7;
        mModBy = parm8;
        mModDate = parm9;
        
    }

    /**
     * Creates a new UiControlElementData
     *
     * @return
     *  Newly initialized UiControlElementData object.
     */
    public static UiControlElementData createValue ()
    {
        UiControlElementData valueData = new UiControlElementData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiControlElementData object
     */
    public String toString()
    {
        return "[" + "UiControlElementId=" + mUiControlElementId + ", UiControlId=" + mUiControlId + ", ShortDesc=" + mShortDesc + ", TypeCd=" + mTypeCd + ", Value=" + mValue + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UiControlElement");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUiControlElementId));

        node =  doc.createElement("UiControlId");
        node.appendChild(doc.createTextNode(String.valueOf(mUiControlId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
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
    * creates a clone of this object, the UiControlElementId field is not cloned.
    *
    * @return UiControlElementData object
    */
    public Object clone(){
        UiControlElementData myClone = new UiControlElementData();
        
        myClone.mUiControlId = mUiControlId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mTypeCd = mTypeCd;
        
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

        if (UiControlElementDataAccess.UI_CONTROL_ELEMENT_ID.equals(pFieldName)) {
            return getUiControlElementId();
        } else if (UiControlElementDataAccess.UI_CONTROL_ID.equals(pFieldName)) {
            return getUiControlId();
        } else if (UiControlElementDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (UiControlElementDataAccess.TYPE_CD.equals(pFieldName)) {
            return getTypeCd();
        } else if (UiControlElementDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (UiControlElementDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UiControlElementDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UiControlElementDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UiControlElementDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return UiControlElementDataAccess.CLW_UI_CONTROL_ELEMENT;
    }

    
    /**
     * Sets the UiControlElementId field. This field is required to be set in the database.
     *
     * @param pUiControlElementId
     *  int to use to update the field.
     */
    public void setUiControlElementId(int pUiControlElementId){
        this.mUiControlElementId = pUiControlElementId;
        setDirty(true);
    }
    /**
     * Retrieves the UiControlElementId field.
     *
     * @return
     *  int containing the UiControlElementId field.
     */
    public int getUiControlElementId(){
        return mUiControlElementId;
    }

    /**
     * Sets the UiControlId field. This field is required to be set in the database.
     *
     * @param pUiControlId
     *  int to use to update the field.
     */
    public void setUiControlId(int pUiControlId){
        this.mUiControlId = pUiControlId;
        setDirty(true);
    }
    /**
     * Retrieves the UiControlId field.
     *
     * @return
     *  int containing the UiControlId field.
     */
    public int getUiControlId(){
        return mUiControlId;
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
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
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
