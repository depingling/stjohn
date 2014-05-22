
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemLogData</code> is a ValueObject class wrapping of the database table CLW_ITEM_LOG.
 */
public class ItemLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2721146912950379202L;
    private int mItemLogId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private Date mDate;// SQL type:DATE, not null
    private Date mTime;// SQL type:DATE, not null
    private String mChange;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemLogData ()
    {
        mChange = "";
    }

    /**
     * Constructor.
     */
    public ItemLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5)
    {
        mItemLogId = parm1;
        mItemId = parm2;
        mDate = parm3;
        mTime = parm4;
        mChange = parm5;
        
    }

    /**
     * Creates a new ItemLogData
     *
     * @return
     *  Newly initialized ItemLogData object.
     */
    public static ItemLogData createValue ()
    {
        ItemLogData valueData = new ItemLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemLogData object
     */
    public String toString()
    {
        return "[" + "ItemLogId=" + mItemLogId + ", ItemId=" + mItemId + ", Date=" + mDate + ", Time=" + mTime + ", Change=" + mChange + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemLogId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("Date");
        node.appendChild(doc.createTextNode(String.valueOf(mDate)));
        root.appendChild(node);

        node =  doc.createElement("Time");
        node.appendChild(doc.createTextNode(String.valueOf(mTime)));
        root.appendChild(node);

        node =  doc.createElement("Change");
        node.appendChild(doc.createTextNode(String.valueOf(mChange)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ItemLogId field is not cloned.
    *
    * @return ItemLogData object
    */
    public Object clone(){
        ItemLogData myClone = new ItemLogData();
        
        myClone.mItemId = mItemId;
        
        if(mDate != null){
                myClone.mDate = (Date) mDate.clone();
        }
        
        if(mTime != null){
                myClone.mTime = (Date) mTime.clone();
        }
        
        myClone.mChange = mChange;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ItemLogDataAccess.ITEM_LOG_ID.equals(pFieldName)) {
            return getItemLogId();
        } else if (ItemLogDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ItemLogDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (ItemLogDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (ItemLogDataAccess.CHANGE.equals(pFieldName)) {
            return getChange();
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
        return ItemLogDataAccess.CLW_ITEM_LOG;
    }

    
    /**
     * Sets the ItemLogId field. This field is required to be set in the database.
     *
     * @param pItemLogId
     *  int to use to update the field.
     */
    public void setItemLogId(int pItemLogId){
        this.mItemLogId = pItemLogId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemLogId field.
     *
     * @return
     *  int containing the ItemLogId field.
     */
    public int getItemLogId(){
        return mItemLogId;
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
     * Sets the Date field. This field is required to be set in the database.
     *
     * @param pDate
     *  Date to use to update the field.
     */
    public void setDate(Date pDate){
        this.mDate = pDate;
        setDirty(true);
    }
    /**
     * Retrieves the Date field.
     *
     * @return
     *  Date containing the Date field.
     */
    public Date getDate(){
        return mDate;
    }

    /**
     * Sets the Time field. This field is required to be set in the database.
     *
     * @param pTime
     *  Date to use to update the field.
     */
    public void setTime(Date pTime){
        this.mTime = pTime;
        setDirty(true);
    }
    /**
     * Retrieves the Time field.
     *
     * @return
     *  Date containing the Time field.
     */
    public Date getTime(){
        return mTime;
    }

    /**
     * Sets the Change field.
     *
     * @param pChange
     *  String to use to update the field.
     */
    public void setChange(String pChange){
        this.mChange = pChange;
        setDirty(true);
    }
    /**
     * Retrieves the Change field.
     *
     * @return
     *  String containing the Change field.
     */
    public String getChange(){
        return mChange;
    }


}
