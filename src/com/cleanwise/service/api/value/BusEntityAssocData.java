
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BusEntityAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_BUS_ENTITY_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BusEntityAssocData</code> is a ValueObject class wrapping of the database table CLW_BUS_ENTITY_ASSOC.
 */
public class BusEntityAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2821771090106166142L;
    private int mBusEntityAssocId;// SQL type:NUMBER, not null
    private int mBusEntity1Id;// SQL type:NUMBER, not null
    private int mBusEntity2Id;// SQL type:NUMBER, not null
    private String mBusEntityAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public BusEntityAssocData ()
    {
        mBusEntityAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public BusEntityAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mBusEntityAssocId = parm1;
        mBusEntity1Id = parm2;
        mBusEntity2Id = parm3;
        mBusEntityAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new BusEntityAssocData
     *
     * @return
     *  Newly initialized BusEntityAssocData object.
     */
    public static BusEntityAssocData createValue ()
    {
        BusEntityAssocData valueData = new BusEntityAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BusEntityAssocData object
     */
    public String toString()
    {
        return "[" + "BusEntityAssocId=" + mBusEntityAssocId + ", BusEntity1Id=" + mBusEntity1Id + ", BusEntity2Id=" + mBusEntity2Id + ", BusEntityAssocCd=" + mBusEntityAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("BusEntityAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBusEntityAssocId));

        node =  doc.createElement("BusEntity1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntity1Id)));
        root.appendChild(node);

        node =  doc.createElement("BusEntity2Id");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntity2Id)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityAssocCd)));
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
    * creates a clone of this object, the BusEntityAssocId field is not cloned.
    *
    * @return BusEntityAssocData object
    */
    public Object clone(){
        BusEntityAssocData myClone = new BusEntityAssocData();
        
        myClone.mBusEntity1Id = mBusEntity1Id;
        
        myClone.mBusEntity2Id = mBusEntity2Id;
        
        myClone.mBusEntityAssocCd = mBusEntityAssocCd;
        
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

        if (BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID.equals(pFieldName)) {
            return getBusEntityAssocId();
        } else if (BusEntityAssocDataAccess.BUS_ENTITY1_ID.equals(pFieldName)) {
            return getBusEntity1Id();
        } else if (BusEntityAssocDataAccess.BUS_ENTITY2_ID.equals(pFieldName)) {
            return getBusEntity2Id();
        } else if (BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD.equals(pFieldName)) {
            return getBusEntityAssocCd();
        } else if (BusEntityAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BusEntityAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BusEntityAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BusEntityAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC;
    }

    
    /**
     * Sets the BusEntityAssocId field. This field is required to be set in the database.
     *
     * @param pBusEntityAssocId
     *  int to use to update the field.
     */
    public void setBusEntityAssocId(int pBusEntityAssocId){
        this.mBusEntityAssocId = pBusEntityAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityAssocId field.
     *
     * @return
     *  int containing the BusEntityAssocId field.
     */
    public int getBusEntityAssocId(){
        return mBusEntityAssocId;
    }

    /**
     * Sets the BusEntity1Id field. This field is required to be set in the database.
     *
     * @param pBusEntity1Id
     *  int to use to update the field.
     */
    public void setBusEntity1Id(int pBusEntity1Id){
        this.mBusEntity1Id = pBusEntity1Id;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntity1Id field.
     *
     * @return
     *  int containing the BusEntity1Id field.
     */
    public int getBusEntity1Id(){
        return mBusEntity1Id;
    }

    /**
     * Sets the BusEntity2Id field. This field is required to be set in the database.
     *
     * @param pBusEntity2Id
     *  int to use to update the field.
     */
    public void setBusEntity2Id(int pBusEntity2Id){
        this.mBusEntity2Id = pBusEntity2Id;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntity2Id field.
     *
     * @return
     *  int containing the BusEntity2Id field.
     */
    public int getBusEntity2Id(){
        return mBusEntity2Id;
    }

    /**
     * Sets the BusEntityAssocCd field. This field is required to be set in the database.
     *
     * @param pBusEntityAssocCd
     *  String to use to update the field.
     */
    public void setBusEntityAssocCd(String pBusEntityAssocCd){
        this.mBusEntityAssocCd = pBusEntityAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityAssocCd field.
     *
     * @return
     *  String containing the BusEntityAssocCd field.
     */
    public String getBusEntityAssocCd(){
        return mBusEntityAssocCd;
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
