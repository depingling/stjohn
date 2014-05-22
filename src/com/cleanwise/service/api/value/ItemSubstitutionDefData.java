
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemSubstitutionDefData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_SUBSTITUTION_DEF.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemSubstitutionDefData</code> is a ValueObject class wrapping of the database table CLW_ITEM_SUBSTITUTION_DEF.
 */
public class ItemSubstitutionDefData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6248878144584861297L;
    private int mItemSubstitutionDefId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mSubstItemId;// SQL type:NUMBER, not null
    private String mSubstStatusCd;// SQL type:VARCHAR2, not null
    private String mSubstTypeCd;// SQL type:VARCHAR2, not null
    private java.math.BigDecimal mUomConversionFactor;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemSubstitutionDefData ()
    {
        mSubstStatusCd = "";
        mSubstTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemSubstitutionDefData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, java.math.BigDecimal parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mItemSubstitutionDefId = parm1;
        mBusEntityId = parm2;
        mItemId = parm3;
        mSubstItemId = parm4;
        mSubstStatusCd = parm5;
        mSubstTypeCd = parm6;
        mUomConversionFactor = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new ItemSubstitutionDefData
     *
     * @return
     *  Newly initialized ItemSubstitutionDefData object.
     */
    public static ItemSubstitutionDefData createValue ()
    {
        ItemSubstitutionDefData valueData = new ItemSubstitutionDefData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemSubstitutionDefData object
     */
    public String toString()
    {
        return "[" + "ItemSubstitutionDefId=" + mItemSubstitutionDefId + ", BusEntityId=" + mBusEntityId + ", ItemId=" + mItemId + ", SubstItemId=" + mSubstItemId + ", SubstStatusCd=" + mSubstStatusCd + ", SubstTypeCd=" + mSubstTypeCd + ", UomConversionFactor=" + mUomConversionFactor + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemSubstitutionDef");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemSubstitutionDefId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("SubstItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemId)));
        root.appendChild(node);

        node =  doc.createElement("SubstStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("SubstTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("UomConversionFactor");
        node.appendChild(doc.createTextNode(String.valueOf(mUomConversionFactor)));
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
    * creates a clone of this object, the ItemSubstitutionDefId field is not cloned.
    *
    * @return ItemSubstitutionDefData object
    */
    public Object clone(){
        ItemSubstitutionDefData myClone = new ItemSubstitutionDefData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mItemId = mItemId;
        
        myClone.mSubstItemId = mSubstItemId;
        
        myClone.mSubstStatusCd = mSubstStatusCd;
        
        myClone.mSubstTypeCd = mSubstTypeCd;
        
        myClone.mUomConversionFactor = mUomConversionFactor;
        
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

        if (ItemSubstitutionDefDataAccess.ITEM_SUBSTITUTION_DEF_ID.equals(pFieldName)) {
            return getItemSubstitutionDefId();
        } else if (ItemSubstitutionDefDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ItemSubstitutionDefDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ItemSubstitutionDefDataAccess.SUBST_ITEM_ID.equals(pFieldName)) {
            return getSubstItemId();
        } else if (ItemSubstitutionDefDataAccess.SUBST_STATUS_CD.equals(pFieldName)) {
            return getSubstStatusCd();
        } else if (ItemSubstitutionDefDataAccess.SUBST_TYPE_CD.equals(pFieldName)) {
            return getSubstTypeCd();
        } else if (ItemSubstitutionDefDataAccess.UOM_CONVERSION_FACTOR.equals(pFieldName)) {
            return getUomConversionFactor();
        } else if (ItemSubstitutionDefDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemSubstitutionDefDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemSubstitutionDefDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemSubstitutionDefDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemSubstitutionDefDataAccess.CLW_ITEM_SUBSTITUTION_DEF;
    }

    
    /**
     * Sets the ItemSubstitutionDefId field. This field is required to be set in the database.
     *
     * @param pItemSubstitutionDefId
     *  int to use to update the field.
     */
    public void setItemSubstitutionDefId(int pItemSubstitutionDefId){
        this.mItemSubstitutionDefId = pItemSubstitutionDefId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemSubstitutionDefId field.
     *
     * @return
     *  int containing the ItemSubstitutionDefId field.
     */
    public int getItemSubstitutionDefId(){
        return mItemSubstitutionDefId;
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
     * Sets the ItemId field. This field is required to be set in the database.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }

    /**
     * Sets the SubstItemId field. This field is required to be set in the database.
     *
     * @param pSubstItemId
     *  int to use to update the field.
     */
    public void setSubstItemId(int pSubstItemId){
        this.mSubstItemId = pSubstItemId;
        setDirty(true);
    }
    /**
     * Retrieves the SubstItemId field.
     *
     * @return
     *  int containing the SubstItemId field.
     */
    public int getSubstItemId(){
        return mSubstItemId;
    }

    /**
     * Sets the SubstStatusCd field. This field is required to be set in the database.
     *
     * @param pSubstStatusCd
     *  String to use to update the field.
     */
    public void setSubstStatusCd(String pSubstStatusCd){
        this.mSubstStatusCd = pSubstStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the SubstStatusCd field.
     *
     * @return
     *  String containing the SubstStatusCd field.
     */
    public String getSubstStatusCd(){
        return mSubstStatusCd;
    }

    /**
     * Sets the SubstTypeCd field. This field is required to be set in the database.
     *
     * @param pSubstTypeCd
     *  String to use to update the field.
     */
    public void setSubstTypeCd(String pSubstTypeCd){
        this.mSubstTypeCd = pSubstTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SubstTypeCd field.
     *
     * @return
     *  String containing the SubstTypeCd field.
     */
    public String getSubstTypeCd(){
        return mSubstTypeCd;
    }

    /**
     * Sets the UomConversionFactor field. This field is required to be set in the database.
     *
     * @param pUomConversionFactor
     *  java.math.BigDecimal to use to update the field.
     */
    public void setUomConversionFactor(java.math.BigDecimal pUomConversionFactor){
        this.mUomConversionFactor = pUomConversionFactor;
        setDirty(true);
    }
    /**
     * Retrieves the UomConversionFactor field.
     *
     * @return
     *  java.math.BigDecimal containing the UomConversionFactor field.
     */
    public java.math.BigDecimal getUomConversionFactor(){
        return mUomConversionFactor;
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
