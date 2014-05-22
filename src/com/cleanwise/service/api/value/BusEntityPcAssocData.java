
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityPcAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUS_ENTITY_PC_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BusEntityPcAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BusEntityPcAssocData</code> is a ValueObject class wrapping of the database table CLW_BUS_ENTITY_PC_ASSOC.
 */
public class BusEntityPcAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mBusEntityPcAssocId;// SQL type:NUMBER, not null
    private int mParentBusEntityId;// SQL type:NUMBER, not null
    private int mChildBusEntityId;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BusEntityPcAssocData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BusEntityPcAssocData(int parm1, int parm2, int parm3, Date parm4, String parm5, Date parm6, String parm7)
    {
        mBusEntityPcAssocId = parm1;
        mParentBusEntityId = parm2;
        mChildBusEntityId = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        
    }

    /**
     * Creates a new BusEntityPcAssocData
     *
     * @return
     *  Newly initialized BusEntityPcAssocData object.
     */
    public static BusEntityPcAssocData createValue ()
    {
        BusEntityPcAssocData valueData = new BusEntityPcAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BusEntityPcAssocData object
     */
    public String toString()
    {
        return "[" + "BusEntityPcAssocId=" + mBusEntityPcAssocId + ", ParentBusEntityId=" + mParentBusEntityId + ", ChildBusEntityId=" + mChildBusEntityId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BusEntityPcAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBusEntityPcAssocId));

        node =  doc.createElement("ParentBusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ChildBusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mChildBusEntityId)));
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
    * creates a clone of this object, the BusEntityPcAssocId field is not cloned.
    *
    * @return BusEntityPcAssocData object
    */
    public Object clone(){
        BusEntityPcAssocData myClone = new BusEntityPcAssocData();
        
        myClone.mParentBusEntityId = mParentBusEntityId;
        
        myClone.mChildBusEntityId = mChildBusEntityId;
        
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

        if (BusEntityPcAssocDataAccess.BUS_ENTITY_PC_ASSOC_ID.equals(pFieldName)) {
            return getBusEntityPcAssocId();
        } else if (BusEntityPcAssocDataAccess.PARENT_BUS_ENTITY_ID.equals(pFieldName)) {
            return getParentBusEntityId();
        } else if (BusEntityPcAssocDataAccess.CHILD_BUS_ENTITY_ID.equals(pFieldName)) {
            return getChildBusEntityId();
        } else if (BusEntityPcAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BusEntityPcAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BusEntityPcAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BusEntityPcAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BusEntityPcAssocDataAccess.CLW_BUS_ENTITY_PC_ASSOC;
    }

    
    /**
     * Sets the BusEntityPcAssocId field. This field is required to be set in the database.
     *
     * @param pBusEntityPcAssocId
     *  int to use to update the field.
     */
    public void setBusEntityPcAssocId(int pBusEntityPcAssocId){
        this.mBusEntityPcAssocId = pBusEntityPcAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityPcAssocId field.
     *
     * @return
     *  int containing the BusEntityPcAssocId field.
     */
    public int getBusEntityPcAssocId(){
        return mBusEntityPcAssocId;
    }

    /**
     * Sets the ParentBusEntityId field. This field is required to be set in the database.
     *
     * @param pParentBusEntityId
     *  int to use to update the field.
     */
    public void setParentBusEntityId(int pParentBusEntityId){
        this.mParentBusEntityId = pParentBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the ParentBusEntityId field.
     *
     * @return
     *  int containing the ParentBusEntityId field.
     */
    public int getParentBusEntityId(){
        return mParentBusEntityId;
    }

    /**
     * Sets the ChildBusEntityId field. This field is required to be set in the database.
     *
     * @param pChildBusEntityId
     *  int to use to update the field.
     */
    public void setChildBusEntityId(int pChildBusEntityId){
        this.mChildBusEntityId = pChildBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the ChildBusEntityId field.
     *
     * @return
     *  int containing the ChildBusEntityId field.
     */
    public int getChildBusEntityId(){
        return mChildBusEntityId;
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
