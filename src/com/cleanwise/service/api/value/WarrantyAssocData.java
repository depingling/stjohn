
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_WARRANTY_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.WarrantyAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WarrantyAssocData</code> is a ValueObject class wrapping of the database table CLW_WARRANTY_ASSOC.
 */
public class WarrantyAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2795108086449438490L;
    private int mWarrantyAssocId;// SQL type:NUMBER, not null
    private int mWarrantyId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mWarrantyAssocCd;// SQL type:VARCHAR2
    private String mWarrantyAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public WarrantyAssocData ()
    {
        mWarrantyAssocCd = "";
        mWarrantyAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public WarrantyAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mWarrantyAssocId = parm1;
        mWarrantyId = parm2;
        mBusEntityId = parm3;
        mWarrantyAssocCd = parm4;
        mWarrantyAssocStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new WarrantyAssocData
     *
     * @return
     *  Newly initialized WarrantyAssocData object.
     */
    public static WarrantyAssocData createValue ()
    {
        WarrantyAssocData valueData = new WarrantyAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyAssocData object
     */
    public String toString()
    {
        return "[" + "WarrantyAssocId=" + mWarrantyAssocId + ", WarrantyId=" + mWarrantyId + ", BusEntityId=" + mBusEntityId + ", WarrantyAssocCd=" + mWarrantyAssocCd + ", WarrantyAssocStatusCd=" + mWarrantyAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("WarrantyAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mWarrantyAssocId));

        node =  doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("WarrantyAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("WarrantyAssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyAssocStatusCd)));
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
    * creates a clone of this object, the WarrantyAssocId field is not cloned.
    *
    * @return WarrantyAssocData object
    */
    public Object clone(){
        WarrantyAssocData myClone = new WarrantyAssocData();
        
        myClone.mWarrantyId = mWarrantyId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mWarrantyAssocCd = mWarrantyAssocCd;
        
        myClone.mWarrantyAssocStatusCd = mWarrantyAssocStatusCd;
        
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

        if (WarrantyAssocDataAccess.WARRANTY_ASSOC_ID.equals(pFieldName)) {
            return getWarrantyAssocId();
        } else if (WarrantyAssocDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (WarrantyAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (WarrantyAssocDataAccess.WARRANTY_ASSOC_CD.equals(pFieldName)) {
            return getWarrantyAssocCd();
        } else if (WarrantyAssocDataAccess.WARRANTY_ASSOC_STATUS_CD.equals(pFieldName)) {
            return getWarrantyAssocStatusCd();
        } else if (WarrantyAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (WarrantyAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (WarrantyAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (WarrantyAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC;
    }

    
    /**
     * Sets the WarrantyAssocId field. This field is required to be set in the database.
     *
     * @param pWarrantyAssocId
     *  int to use to update the field.
     */
    public void setWarrantyAssocId(int pWarrantyAssocId){
        this.mWarrantyAssocId = pWarrantyAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyAssocId field.
     *
     * @return
     *  int containing the WarrantyAssocId field.
     */
    public int getWarrantyAssocId(){
        return mWarrantyAssocId;
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
     * Sets the BusEntityId field.
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
     * Sets the WarrantyAssocCd field.
     *
     * @param pWarrantyAssocCd
     *  String to use to update the field.
     */
    public void setWarrantyAssocCd(String pWarrantyAssocCd){
        this.mWarrantyAssocCd = pWarrantyAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyAssocCd field.
     *
     * @return
     *  String containing the WarrantyAssocCd field.
     */
    public String getWarrantyAssocCd(){
        return mWarrantyAssocCd;
    }

    /**
     * Sets the WarrantyAssocStatusCd field.
     *
     * @param pWarrantyAssocStatusCd
     *  String to use to update the field.
     */
    public void setWarrantyAssocStatusCd(String pWarrantyAssocStatusCd){
        this.mWarrantyAssocStatusCd = pWarrantyAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyAssocStatusCd field.
     *
     * @return
     *  String containing the WarrantyAssocStatusCd field.
     */
    public String getWarrantyAssocStatusCd(){
        return mWarrantyAssocStatusCd;
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
