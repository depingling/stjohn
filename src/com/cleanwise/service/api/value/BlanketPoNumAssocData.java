
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BlanketPoNumAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_BLANKET_PO_NUM_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BlanketPoNumAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BlanketPoNumAssocData</code> is a ValueObject class wrapping of the database table CLW_BLANKET_PO_NUM_ASSOC.
 */
public class BlanketPoNumAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7277136668719236157L;
    private int mBlanketPoNumAssocId;// SQL type:NUMBER, not null
    private int mBlanketPoNumId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BlanketPoNumAssocData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BlanketPoNumAssocData(int parm1, int parm2, int parm3, Date parm4, String parm5, Date parm6, String parm7)
    {
        mBlanketPoNumAssocId = parm1;
        mBlanketPoNumId = parm2;
        mBusEntityId = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        
    }

    /**
     * Creates a new BlanketPoNumAssocData
     *
     * @return
     *  Newly initialized BlanketPoNumAssocData object.
     */
    public static BlanketPoNumAssocData createValue ()
    {
        BlanketPoNumAssocData valueData = new BlanketPoNumAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BlanketPoNumAssocData object
     */
    public String toString()
    {
        return "[" + "BlanketPoNumAssocId=" + mBlanketPoNumAssocId + ", BlanketPoNumId=" + mBlanketPoNumId + ", BusEntityId=" + mBusEntityId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BlanketPoNumAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBlanketPoNumAssocId));

        node =  doc.createElement("BlanketPoNumId");
        node.appendChild(doc.createTextNode(String.valueOf(mBlanketPoNumId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
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
    * creates a clone of this object, the BlanketPoNumAssocId field is not cloned.
    *
    * @return BlanketPoNumAssocData object
    */
    public Object clone(){
        BlanketPoNumAssocData myClone = new BlanketPoNumAssocData();
        
        myClone.mBlanketPoNumId = mBlanketPoNumId;
        
        myClone.mBusEntityId = mBusEntityId;
        
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

        if (BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ASSOC_ID.equals(pFieldName)) {
            return getBlanketPoNumAssocId();
        } else if (BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID.equals(pFieldName)) {
            return getBlanketPoNumId();
        } else if (BlanketPoNumAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (BlanketPoNumAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BlanketPoNumAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BlanketPoNumAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BlanketPoNumAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC;
    }

    
    /**
     * Sets the BlanketPoNumAssocId field. This field is required to be set in the database.
     *
     * @param pBlanketPoNumAssocId
     *  int to use to update the field.
     */
    public void setBlanketPoNumAssocId(int pBlanketPoNumAssocId){
        this.mBlanketPoNumAssocId = pBlanketPoNumAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the BlanketPoNumAssocId field.
     *
     * @return
     *  int containing the BlanketPoNumAssocId field.
     */
    public int getBlanketPoNumAssocId(){
        return mBlanketPoNumAssocId;
    }

    /**
     * Sets the BlanketPoNumId field.
     *
     * @param pBlanketPoNumId
     *  int to use to update the field.
     */
    public void setBlanketPoNumId(int pBlanketPoNumId){
        this.mBlanketPoNumId = pBlanketPoNumId;
        setDirty(true);
    }
    /**
     * Retrieves the BlanketPoNumId field.
     *
     * @return
     *  int containing the BlanketPoNumId field.
     */
    public int getBlanketPoNumId(){
        return mBlanketPoNumId;
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
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
