
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemFacilityCareData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_FACILITY_CARE.
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
 * <code>ItemFacilityCareData</code> is a ValueObject class wrapping of the database table CLW_ITEM_FACILITY_CARE.
 */
public class ItemFacilityCareData extends ValueObject implements Cloneable
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -6691501377148219391L;
    
    private int mItemFacilityCareId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mFacilityCareCd;// SQL type:VARCHAR2, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    private ItemFacilityCareData ()
    {
        mAddBy = "";
        mFacilityCareCd = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemFacilityCareData(int parm1, int parm2, String parm3, Date parm4, String parm5, String parm6, Date parm7)
    {
        mItemFacilityCareId = parm1;
        mItemId = parm2;
        mAddBy = parm3;
        mAddDate = parm4;
        mFacilityCareCd = parm5;
        mModBy = parm6;
        mModDate = parm7;
        
    }

    /**
     * Creates a new ItemFacilityCareData
     *
     * @return
     *  Newly initialized ItemFacilityCareData object.
     */
    public static ItemFacilityCareData createValue ()
    {
        ItemFacilityCareData valueData = new ItemFacilityCareData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemFacilityCareData object
     */
    public String toString()
    {
        return "[" + "ItemFacilityCareId=" + mItemFacilityCareId + ", ItemId=" + mItemId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", FacilityCareCd=" + mFacilityCareCd + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ItemFacilityCare");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemFacilityCareId));

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("FacilityCareCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityCareCd)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ItemFacilityCareId field is not cloned.
    *
    * @return ItemFacilityCareData object
    */
    public Object clone(){
        ItemFacilityCareData myClone = new ItemFacilityCareData();
        
        myClone.mItemId = mItemId;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mFacilityCareCd = mFacilityCareCd;
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    
    /**
     * Sets the ItemFacilityCareId field. This field is required to be set in the database.
     *
     * @param pItemFacilityCareId
     *  int to use to update the field.
     */
    public void setItemFacilityCareId(int pItemFacilityCareId){
        this.mItemFacilityCareId = pItemFacilityCareId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemFacilityCareId field.
     *
     * @return
     *  int containing the ItemFacilityCareId field.
     */
    public int getItemFacilityCareId(){
        return mItemFacilityCareId;
    }

    /**
     * Sets the ItemId field. This field is required to be set in the database.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
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
     * Sets the FacilityCareCd field. This field is required to be set in the database.
     *
     * @param pFacilityCareCd
     *  String to use to update the field.
     */
    public void setFacilityCareCd(String pFacilityCareCd){
        this.mFacilityCareCd = pFacilityCareCd;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityCareCd field.
     *
     * @return
     *  String containing the FacilityCareCd field.
     */
    public String getFacilityCareCd(){
        return mFacilityCareCd;
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


}
