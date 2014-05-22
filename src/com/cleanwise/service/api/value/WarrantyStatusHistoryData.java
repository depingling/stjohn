
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        WarrantyStatusHistoryData
 * Description:  This is a ValueObject class wrapping the database table CLW_WARRANTY_STATUS_HISTORY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>WarrantyStatusHistoryData</code> is a ValueObject class wrapping of the database table CLW_WARRANTY_STATUS_HISTORY.
 */
public class WarrantyStatusHistoryData extends ValueObject implements Cloneable
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4064312323477228833L;
    
    private int mWarrantyId;// SQL type:NUMBER, not null
    private int mWarrantyStatusHistoryId;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private Date mStatusDate;// SQL type:DATE, not null
    private String mTypeCd;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    private WarrantyStatusHistoryData ()
    {
        mAddBy = "";
        mModBy = "";
        mStatusCd = "";
        mTypeCd = "";
    }

    /**
     * Constructor.
     */
    public WarrantyStatusHistoryData(int parm1, int parm2, String parm3, Date parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mWarrantyId = parm1;
        mWarrantyStatusHistoryId = parm2;
        mAddBy = parm3;
        mAddDate = parm4;
        mModBy = parm5;
        mModDate = parm6;
        mStatusCd = parm7;
        mStatusDate = parm8;
        mTypeCd = parm9;
        
    }

    /**
     * Creates a new WarrantyStatusHistoryData
     *
     * @return
     *  Newly initialized WarrantyStatusHistoryData object.
     */
    public static WarrantyStatusHistoryData createValue ()
    {
        WarrantyStatusHistoryData valueData = new WarrantyStatusHistoryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this WarrantyStatusHistoryData object
     */
    public String toString()
    {
        return "[" + "WarrantyId=" + mWarrantyId + ", WarrantyStatusHistoryId=" + mWarrantyStatusHistoryId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", StatusCd=" + mStatusCd + ", StatusDate=" + mStatusDate + ", TypeCd=" + mTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("WarrantyStatusHistory");
        
        Element node;

        node = doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
        root.appendChild(node);

        root.setAttribute("Id", String.valueOf(mWarrantyStatusHistoryId));

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node = doc.createElement("StatusDate");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusDate)));
        root.appendChild(node);

        node = doc.createElement("TypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the WarrantyStatusHistoryId field is not cloned.
    *
    * @return WarrantyStatusHistoryData object
    */
    public Object clone(){
        WarrantyStatusHistoryData myClone = new WarrantyStatusHistoryData();
        
        myClone.mWarrantyId = mWarrantyId;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mStatusCd = mStatusCd;
        
        if(mStatusDate != null){
                myClone.mStatusDate = (Date) mStatusDate.clone();
        }
        
        myClone.mTypeCd = mTypeCd;
        
        return myClone;
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
     * Sets the WarrantyStatusHistoryId field. This field is required to be set in the database.
     *
     * @param pWarrantyStatusHistoryId
     *  int to use to update the field.
     */
    public void setWarrantyStatusHistoryId(int pWarrantyStatusHistoryId){
        this.mWarrantyStatusHistoryId = pWarrantyStatusHistoryId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyStatusHistoryId field.
     *
     * @return
     *  int containing the WarrantyStatusHistoryId field.
     */
    public int getWarrantyStatusHistoryId(){
        return mWarrantyStatusHistoryId;
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
     * Sets the StatusCd field. This field is required to be set in the database.
     *
     * @param pStatusCd
     *  String to use to update the field.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StatusCd field.
     *
     * @return
     *  String containing the StatusCd field.
     */
    public String getStatusCd(){
        return mStatusCd;
    }

    /**
     * Sets the StatusDate field. This field is required to be set in the database.
     *
     * @param pStatusDate
     *  Date to use to update the field.
     */
    public void setStatusDate(Date pStatusDate){
        this.mStatusDate = pStatusDate;
        setDirty(true);
    }
    /**
     * Retrieves the StatusDate field.
     *
     * @return
     *  Date containing the StatusDate field.
     */
    public Date getStatusDate(){
        return mStatusDate;
    }

    /**
     * Sets the TypeCd field. This field is required to be set in the database.
     *
     * @param pTypeCd
     *  String to use to update the field.
     */
    public void setTypeCd(String pTypeCd){
        this.mTypeCd = pTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TypeCd field.
     *
     * @return
     *  String containing the TypeCd field.
     */
    public String getTypeCd(){
        return mTypeCd;
    }


}
