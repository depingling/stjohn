
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetMasterAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_ASSET_MASTER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AssetMasterAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AssetMasterAssocData</code> is a ValueObject class wrapping of the database table CLW_ASSET_MASTER_ASSOC.
 */
public class AssetMasterAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mAssetMasterAssocId;// SQL type:NUMBER, not null
    private int mParentMasterAssetId;// SQL type:NUMBER
    private int mChildMasterAssetId;// SQL type:NUMBER
    private String mAssetMasterAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AssetMasterAssocData ()
    {
        mAssetMasterAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AssetMasterAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mAssetMasterAssocId = parm1;
        mParentMasterAssetId = parm2;
        mChildMasterAssetId = parm3;
        mAssetMasterAssocStatusCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new AssetMasterAssocData
     *
     * @return
     *  Newly initialized AssetMasterAssocData object.
     */
    public static AssetMasterAssocData createValue ()
    {
        AssetMasterAssocData valueData = new AssetMasterAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetMasterAssocData object
     */
    public String toString()
    {
        return "[" + "AssetMasterAssocId=" + mAssetMasterAssocId + ", ParentMasterAssetId=" + mParentMasterAssetId + ", ChildMasterAssetId=" + mChildMasterAssetId + ", AssetMasterAssocStatusCd=" + mAssetMasterAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AssetMasterAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAssetMasterAssocId));

        node =  doc.createElement("ParentMasterAssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentMasterAssetId)));
        root.appendChild(node);

        node =  doc.createElement("ChildMasterAssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mChildMasterAssetId)));
        root.appendChild(node);

        node =  doc.createElement("AssetMasterAssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetMasterAssocStatusCd)));
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
    * creates a clone of this object, the AssetMasterAssocId field is not cloned.
    *
    * @return AssetMasterAssocData object
    */
    public Object clone(){
        AssetMasterAssocData myClone = new AssetMasterAssocData();
        
        myClone.mParentMasterAssetId = mParentMasterAssetId;
        
        myClone.mChildMasterAssetId = mChildMasterAssetId;
        
        myClone.mAssetMasterAssocStatusCd = mAssetMasterAssocStatusCd;
        
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

        if (AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_ID.equals(pFieldName)) {
            return getAssetMasterAssocId();
        } else if (AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID.equals(pFieldName)) {
            return getParentMasterAssetId();
        } else if (AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID.equals(pFieldName)) {
            return getChildMasterAssetId();
        } else if (AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_STATUS_CD.equals(pFieldName)) {
            return getAssetMasterAssocStatusCd();
        } else if (AssetMasterAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AssetMasterAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AssetMasterAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AssetMasterAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return AssetMasterAssocDataAccess.CLW_ASSET_MASTER_ASSOC;
    }

    
    /**
     * Sets the AssetMasterAssocId field. This field is required to be set in the database.
     *
     * @param pAssetMasterAssocId
     *  int to use to update the field.
     */
    public void setAssetMasterAssocId(int pAssetMasterAssocId){
        this.mAssetMasterAssocId = pAssetMasterAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetMasterAssocId field.
     *
     * @return
     *  int containing the AssetMasterAssocId field.
     */
    public int getAssetMasterAssocId(){
        return mAssetMasterAssocId;
    }

    /**
     * Sets the ParentMasterAssetId field.
     *
     * @param pParentMasterAssetId
     *  int to use to update the field.
     */
    public void setParentMasterAssetId(int pParentMasterAssetId){
        this.mParentMasterAssetId = pParentMasterAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the ParentMasterAssetId field.
     *
     * @return
     *  int containing the ParentMasterAssetId field.
     */
    public int getParentMasterAssetId(){
        return mParentMasterAssetId;
    }

    /**
     * Sets the ChildMasterAssetId field.
     *
     * @param pChildMasterAssetId
     *  int to use to update the field.
     */
    public void setChildMasterAssetId(int pChildMasterAssetId){
        this.mChildMasterAssetId = pChildMasterAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the ChildMasterAssetId field.
     *
     * @return
     *  int containing the ChildMasterAssetId field.
     */
    public int getChildMasterAssetId(){
        return mChildMasterAssetId;
    }

    /**
     * Sets the AssetMasterAssocStatusCd field.
     *
     * @param pAssetMasterAssocStatusCd
     *  String to use to update the field.
     */
    public void setAssetMasterAssocStatusCd(String pAssetMasterAssocStatusCd){
        this.mAssetMasterAssocStatusCd = pAssetMasterAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssetMasterAssocStatusCd field.
     *
     * @return
     *  String containing the AssetMasterAssocStatusCd field.
     */
    public String getAssetMasterAssocStatusCd(){
        return mAssetMasterAssocStatusCd;
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
