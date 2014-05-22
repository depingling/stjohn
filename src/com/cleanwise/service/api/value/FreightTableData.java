
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        FreightTableData
 * Description:  This is a ValueObject class wrapping the database table CLW_FREIGHT_TABLE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.FreightTableDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>FreightTableData</code> is a ValueObject class wrapping of the database table CLW_FREIGHT_TABLE.
 */
public class FreightTableData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1020524352006604743L;
    private int mFreightTableId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mFreightTableStatusCd;// SQL type:VARCHAR2, not null
    private String mFreightTableTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mStoreId;// SQL type:NUMBER
    private int mDistributorId;// SQL type:NUMBER
    private String mFreightTableChargeCd;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    public FreightTableData ()
    {
        mShortDesc = "";
        mFreightTableStatusCd = "";
        mFreightTableTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mFreightTableChargeCd = "";
    }

    /**
     * Constructor.
     */
    public FreightTableData(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, int parm9, int parm10, String parm11)
    {
        mFreightTableId = parm1;
        mShortDesc = parm2;
        mFreightTableStatusCd = parm3;
        mFreightTableTypeCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mStoreId = parm9;
        mDistributorId = parm10;
        mFreightTableChargeCd = parm11;
        
    }

    /**
     * Creates a new FreightTableData
     *
     * @return
     *  Newly initialized FreightTableData object.
     */
    public static FreightTableData createValue ()
    {
        FreightTableData valueData = new FreightTableData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FreightTableData object
     */
    public String toString()
    {
        return "[" + "FreightTableId=" + mFreightTableId + ", ShortDesc=" + mShortDesc + ", FreightTableStatusCd=" + mFreightTableStatusCd + ", FreightTableTypeCd=" + mFreightTableTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", StoreId=" + mStoreId + ", DistributorId=" + mDistributorId + ", FreightTableChargeCd=" + mFreightTableChargeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("FreightTable");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFreightTableId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("FreightTableStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("FreightTableTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableTypeCd)));
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

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node =  doc.createElement("FreightTableChargeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableChargeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the FreightTableId field is not cloned.
    *
    * @return FreightTableData object
    */
    public Object clone(){
        FreightTableData myClone = new FreightTableData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mFreightTableStatusCd = mFreightTableStatusCd;
        
        myClone.mFreightTableTypeCd = mFreightTableTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mStoreId = mStoreId;
        
        myClone.mDistributorId = mDistributorId;
        
        myClone.mFreightTableChargeCd = mFreightTableChargeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (FreightTableDataAccess.FREIGHT_TABLE_ID.equals(pFieldName)) {
            return getFreightTableId();
        } else if (FreightTableDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (FreightTableDataAccess.FREIGHT_TABLE_STATUS_CD.equals(pFieldName)) {
            return getFreightTableStatusCd();
        } else if (FreightTableDataAccess.FREIGHT_TABLE_TYPE_CD.equals(pFieldName)) {
            return getFreightTableTypeCd();
        } else if (FreightTableDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (FreightTableDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (FreightTableDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (FreightTableDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (FreightTableDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (FreightTableDataAccess.DISTRIBUTOR_ID.equals(pFieldName)) {
            return getDistributorId();
        } else if (FreightTableDataAccess.FREIGHT_TABLE_CHARGE_CD.equals(pFieldName)) {
            return getFreightTableChargeCd();
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
        return FreightTableDataAccess.CLW_FREIGHT_TABLE;
    }

    
    /**
     * Sets the FreightTableId field. This field is required to be set in the database.
     *
     * @param pFreightTableId
     *  int to use to update the field.
     */
    public void setFreightTableId(int pFreightTableId){
        this.mFreightTableId = pFreightTableId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTableId field.
     *
     * @return
     *  int containing the FreightTableId field.
     */
    public int getFreightTableId(){
        return mFreightTableId;
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
     * Sets the FreightTableStatusCd field. This field is required to be set in the database.
     *
     * @param pFreightTableStatusCd
     *  String to use to update the field.
     */
    public void setFreightTableStatusCd(String pFreightTableStatusCd){
        this.mFreightTableStatusCd = pFreightTableStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTableStatusCd field.
     *
     * @return
     *  String containing the FreightTableStatusCd field.
     */
    public String getFreightTableStatusCd(){
        return mFreightTableStatusCd;
    }

    /**
     * Sets the FreightTableTypeCd field. This field is required to be set in the database.
     *
     * @param pFreightTableTypeCd
     *  String to use to update the field.
     */
    public void setFreightTableTypeCd(String pFreightTableTypeCd){
        this.mFreightTableTypeCd = pFreightTableTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTableTypeCd field.
     *
     * @return
     *  String containing the FreightTableTypeCd field.
     */
    public String getFreightTableTypeCd(){
        return mFreightTableTypeCd;
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

    /**
     * Sets the StoreId field.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the DistributorId field.
     *
     * @param pDistributorId
     *  int to use to update the field.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorId field.
     *
     * @return
     *  int containing the DistributorId field.
     */
    public int getDistributorId(){
        return mDistributorId;
    }

    /**
     * Sets the FreightTableChargeCd field. This field is required to be set in the database.
     *
     * @param pFreightTableChargeCd
     *  String to use to update the field.
     */
    public void setFreightTableChargeCd(String pFreightTableChargeCd){
        this.mFreightTableChargeCd = pFreightTableChargeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTableChargeCd field.
     *
     * @return
     *  String containing the FreightTableChargeCd field.
     */
    public String getFreightTableChargeCd(){
        return mFreightTableChargeCd;
    }


}
