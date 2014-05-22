
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityTerrData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUS_ENTITY_TERR.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BusEntityTerrDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BusEntityTerrData</code> is a ValueObject class wrapping of the database table CLW_BUS_ENTITY_TERR.
 */
public class BusEntityTerrData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6762403059447391129L;
    private int mBusEntityTerrId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mPostalCodeId;// SQL type:NUMBER, not null
    private String mBusEntityTerrCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mPostalCode;// SQL type:VARCHAR2
    private String mBusEntityTerrFreightCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BusEntityTerrData ()
    {
        mBusEntityTerrCd = "";
        mAddBy = "";
        mModBy = "";
        mPostalCode = "";
        mBusEntityTerrFreightCd = "";
    }

    /**
     * Constructor.
     */
    public BusEntityTerrData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9, String parm10)
    {
        mBusEntityTerrId = parm1;
        mBusEntityId = parm2;
        mPostalCodeId = parm3;
        mBusEntityTerrCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mPostalCode = parm9;
        mBusEntityTerrFreightCd = parm10;
        
    }

    /**
     * Creates a new BusEntityTerrData
     *
     * @return
     *  Newly initialized BusEntityTerrData object.
     */
    public static BusEntityTerrData createValue ()
    {
        BusEntityTerrData valueData = new BusEntityTerrData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BusEntityTerrData object
     */
    public String toString()
    {
        return "[" + "BusEntityTerrId=" + mBusEntityTerrId + ", BusEntityId=" + mBusEntityId + ", PostalCodeId=" + mPostalCodeId + ", BusEntityTerrCd=" + mBusEntityTerrCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", PostalCode=" + mPostalCode + ", BusEntityTerrFreightCd=" + mBusEntityTerrFreightCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BusEntityTerr");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBusEntityTerrId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("PostalCodeId");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCodeId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityTerrCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTerrCd)));
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

        node =  doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityTerrFreightCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTerrFreightCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the BusEntityTerrId field is not cloned.
    *
    * @return BusEntityTerrData object
    */
    public Object clone(){
        BusEntityTerrData myClone = new BusEntityTerrData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mPostalCodeId = mPostalCodeId;
        
        myClone.mBusEntityTerrCd = mBusEntityTerrCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mPostalCode = mPostalCode;
        
        myClone.mBusEntityTerrFreightCd = mBusEntityTerrFreightCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (BusEntityTerrDataAccess.BUS_ENTITY_TERR_ID.equals(pFieldName)) {
            return getBusEntityTerrId();
        } else if (BusEntityTerrDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (BusEntityTerrDataAccess.POSTAL_CODE_ID.equals(pFieldName)) {
            return getPostalCodeId();
        } else if (BusEntityTerrDataAccess.BUS_ENTITY_TERR_CD.equals(pFieldName)) {
            return getBusEntityTerrCd();
        } else if (BusEntityTerrDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BusEntityTerrDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BusEntityTerrDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BusEntityTerrDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (BusEntityTerrDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
        } else if (BusEntityTerrDataAccess.BUS_ENTITY_TERR_FREIGHT_CD.equals(pFieldName)) {
            return getBusEntityTerrFreightCd();
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
        return BusEntityTerrDataAccess.CLW_BUS_ENTITY_TERR;
    }

    
    /**
     * Sets the BusEntityTerrId field. This field is required to be set in the database.
     *
     * @param pBusEntityTerrId
     *  int to use to update the field.
     */
    public void setBusEntityTerrId(int pBusEntityTerrId){
        this.mBusEntityTerrId = pBusEntityTerrId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityTerrId field.
     *
     * @return
     *  int containing the BusEntityTerrId field.
     */
    public int getBusEntityTerrId(){
        return mBusEntityTerrId;
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
     * Sets the PostalCodeId field. This field is required to be set in the database.
     *
     * @param pPostalCodeId
     *  int to use to update the field.
     */
    public void setPostalCodeId(int pPostalCodeId){
        this.mPostalCodeId = pPostalCodeId;
        setDirty(true);
    }
    /**
     * Retrieves the PostalCodeId field.
     *
     * @return
     *  int containing the PostalCodeId field.
     */
    public int getPostalCodeId(){
        return mPostalCodeId;
    }

    /**
     * Sets the BusEntityTerrCd field. This field is required to be set in the database.
     *
     * @param pBusEntityTerrCd
     *  String to use to update the field.
     */
    public void setBusEntityTerrCd(String pBusEntityTerrCd){
        this.mBusEntityTerrCd = pBusEntityTerrCd;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityTerrCd field.
     *
     * @return
     *  String containing the BusEntityTerrCd field.
     */
    public String getBusEntityTerrCd(){
        return mBusEntityTerrCd;
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
     * Sets the PostalCode field.
     *
     * @param pPostalCode
     *  String to use to update the field.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the PostalCode field.
     *
     * @return
     *  String containing the PostalCode field.
     */
    public String getPostalCode(){
        return mPostalCode;
    }

    /**
     * Sets the BusEntityTerrFreightCd field.
     *
     * @param pBusEntityTerrFreightCd
     *  String to use to update the field.
     */
    public void setBusEntityTerrFreightCd(String pBusEntityTerrFreightCd){
        this.mBusEntityTerrFreightCd = pBusEntityTerrFreightCd;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityTerrFreightCd field.
     *
     * @return
     *  String containing the BusEntityTerrFreightCd field.
     */
    public String getBusEntityTerrFreightCd(){
        return mBusEntityTerrFreightCd;
    }


}
