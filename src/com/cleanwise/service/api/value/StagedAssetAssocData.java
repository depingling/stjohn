
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StagedAssetAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_STAGED_ASSET_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.StagedAssetAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>StagedAssetAssocData</code> is a ValueObject class wrapping of the database table CLW_STAGED_ASSET_ASSOC.
 */
public class StagedAssetAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mStagedAssetAssocId;// SQL type:NUMBER, not null
    private int mStagedAssetId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mAssocCd;// SQL type:VARCHAR2
    private String mAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public StagedAssetAssocData ()
    {
        mAssocCd = "";
        mAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public StagedAssetAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mStagedAssetAssocId = parm1;
        mStagedAssetId = parm2;
        mBusEntityId = parm3;
        mAssocCd = parm4;
        mAssocStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new StagedAssetAssocData
     *
     * @return
     *  Newly initialized StagedAssetAssocData object.
     */
    public static StagedAssetAssocData createValue ()
    {
        StagedAssetAssocData valueData = new StagedAssetAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StagedAssetAssocData object
     */
    public String toString()
    {
        return "[" + "StagedAssetAssocId=" + mStagedAssetAssocId + ", StagedAssetId=" + mStagedAssetId + ", BusEntityId=" + mBusEntityId + ", AssocCd=" + mAssocCd + ", AssocStatusCd=" + mAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("StagedAssetAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mStagedAssetAssocId));

        node =  doc.createElement("StagedAssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mStagedAssetId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("AssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("AssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssocStatusCd)));
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
    * creates a clone of this object, the StagedAssetAssocId field is not cloned.
    *
    * @return StagedAssetAssocData object
    */
    public Object clone(){
        StagedAssetAssocData myClone = new StagedAssetAssocData();
        
        myClone.mStagedAssetId = mStagedAssetId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mAssocCd = mAssocCd;
        
        myClone.mAssocStatusCd = mAssocStatusCd;
        
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

        if (StagedAssetAssocDataAccess.STAGED_ASSET_ASSOC_ID.equals(pFieldName)) {
            return getStagedAssetAssocId();
        } else if (StagedAssetAssocDataAccess.STAGED_ASSET_ID.equals(pFieldName)) {
            return getStagedAssetId();
        } else if (StagedAssetAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (StagedAssetAssocDataAccess.ASSOC_CD.equals(pFieldName)) {
            return getAssocCd();
        } else if (StagedAssetAssocDataAccess.ASSOC_STATUS_CD.equals(pFieldName)) {
            return getAssocStatusCd();
        } else if (StagedAssetAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (StagedAssetAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (StagedAssetAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (StagedAssetAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return StagedAssetAssocDataAccess.CLW_STAGED_ASSET_ASSOC;
    }

    
    /**
     * Sets the StagedAssetAssocId field. This field is required to be set in the database.
     *
     * @param pStagedAssetAssocId
     *  int to use to update the field.
     */
    public void setStagedAssetAssocId(int pStagedAssetAssocId){
        this.mStagedAssetAssocId = pStagedAssetAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the StagedAssetAssocId field.
     *
     * @return
     *  int containing the StagedAssetAssocId field.
     */
    public int getStagedAssetAssocId(){
        return mStagedAssetAssocId;
    }

    /**
     * Sets the StagedAssetId field. This field is required to be set in the database.
     *
     * @param pStagedAssetId
     *  int to use to update the field.
     */
    public void setStagedAssetId(int pStagedAssetId){
        this.mStagedAssetId = pStagedAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the StagedAssetId field.
     *
     * @return
     *  int containing the StagedAssetId field.
     */
    public int getStagedAssetId(){
        return mStagedAssetId;
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
     * Sets the AssocCd field.
     *
     * @param pAssocCd
     *  String to use to update the field.
     */
    public void setAssocCd(String pAssocCd){
        this.mAssocCd = pAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssocCd field.
     *
     * @return
     *  String containing the AssocCd field.
     */
    public String getAssocCd(){
        return mAssocCd;
    }

    /**
     * Sets the AssocStatusCd field.
     *
     * @param pAssocStatusCd
     *  String to use to update the field.
     */
    public void setAssocStatusCd(String pAssocStatusCd){
        this.mAssocStatusCd = pAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssocStatusCd field.
     *
     * @return
     *  String containing the AssocStatusCd field.
     */
    public String getAssocStatusCd(){
        return mAssocStatusCd;
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
