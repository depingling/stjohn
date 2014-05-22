
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DistShippingOptionsData
 * Description:  This is a ValueObject class wrapping the database table CLW_DIST_SHIPPING_OPTIONS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.DistShippingOptionsDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>DistShippingOptionsData</code> is a ValueObject class wrapping of the database table CLW_DIST_SHIPPING_OPTIONS.
 */
public class DistShippingOptionsData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8266238411581741180L;
    private int mDistShippingOptionsId;// SQL type:NUMBER, not null
    private int mDistributorId;// SQL type:NUMBER
    private int mFreightHandlerId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public DistShippingOptionsData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public DistShippingOptionsData(int parm1, int parm2, int parm3, Date parm4, String parm5, Date parm6, String parm7)
    {
        mDistShippingOptionsId = parm1;
        mDistributorId = parm2;
        mFreightHandlerId = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        
    }

    /**
     * Creates a new DistShippingOptionsData
     *
     * @return
     *  Newly initialized DistShippingOptionsData object.
     */
    public static DistShippingOptionsData createValue ()
    {
        DistShippingOptionsData valueData = new DistShippingOptionsData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistShippingOptionsData object
     */
    public String toString()
    {
        return "[" + "DistShippingOptionsId=" + mDistShippingOptionsId + ", DistributorId=" + mDistributorId + ", FreightHandlerId=" + mFreightHandlerId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("DistShippingOptions");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mDistShippingOptionsId));

        node =  doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node =  doc.createElement("FreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandlerId)));
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
    * creates a clone of this object, the DistShippingOptionsId field is not cloned.
    *
    * @return DistShippingOptionsData object
    */
    public Object clone(){
        DistShippingOptionsData myClone = new DistShippingOptionsData();
        
        myClone.mDistributorId = mDistributorId;
        
        myClone.mFreightHandlerId = mFreightHandlerId;
        
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

        if (DistShippingOptionsDataAccess.DIST_SHIPPING_OPTIONS_ID.equals(pFieldName)) {
            return getDistShippingOptionsId();
        } else if (DistShippingOptionsDataAccess.DISTRIBUTOR_ID.equals(pFieldName)) {
            return getDistributorId();
        } else if (DistShippingOptionsDataAccess.FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getFreightHandlerId();
        } else if (DistShippingOptionsDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (DistShippingOptionsDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (DistShippingOptionsDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (DistShippingOptionsDataAccess.MOD_BY.equals(pFieldName)) {
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
        return DistShippingOptionsDataAccess.CLW_DIST_SHIPPING_OPTIONS;
    }

    
    /**
     * Sets the DistShippingOptionsId field. This field is required to be set in the database.
     *
     * @param pDistShippingOptionsId
     *  int to use to update the field.
     */
    public void setDistShippingOptionsId(int pDistShippingOptionsId){
        this.mDistShippingOptionsId = pDistShippingOptionsId;
        setDirty(true);
    }
    /**
     * Retrieves the DistShippingOptionsId field.
     *
     * @return
     *  int containing the DistShippingOptionsId field.
     */
    public int getDistShippingOptionsId(){
        return mDistShippingOptionsId;
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
     * Sets the FreightHandlerId field.
     *
     * @param pFreightHandlerId
     *  int to use to update the field.
     */
    public void setFreightHandlerId(int pFreightHandlerId){
        this.mFreightHandlerId = pFreightHandlerId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightHandlerId field.
     *
     * @return
     *  int containing the FreightHandlerId field.
     */
    public int getFreightHandlerId(){
        return mFreightHandlerId;
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
