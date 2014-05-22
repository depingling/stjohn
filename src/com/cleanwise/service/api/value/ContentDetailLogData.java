
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContentDetailLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTENT_DETAIL_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContentDetailLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContentDetailLogData</code> is a ValueObject class wrapping of the database table CLW_CONTENT_DETAIL_LOG.
 */
public class ContentDetailLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5742981364742430146L;
    private int mContentDetailLogId;// SQL type:NUMBER, not null
    private int mDetailId;// SQL type:NUMBER
    private Date mDate;// SQL type:DATE
    private Date mTime;// SQL type:DATE
    private String mChange;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContentDetailLogData ()
    {
        mChange = "";
    }

    /**
     * Constructor.
     */
    public ContentDetailLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5)
    {
        mContentDetailLogId = parm1;
        mDetailId = parm2;
        mDate = parm3;
        mTime = parm4;
        mChange = parm5;
        
    }

    /**
     * Creates a new ContentDetailLogData
     *
     * @return
     *  Newly initialized ContentDetailLogData object.
     */
    public static ContentDetailLogData createValue ()
    {
        ContentDetailLogData valueData = new ContentDetailLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContentDetailLogData object
     */
    public String toString()
    {
        return "[" + "ContentDetailLogId=" + mContentDetailLogId + ", DetailId=" + mDetailId + ", Date=" + mDate + ", Time=" + mTime + ", Change=" + mChange + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ContentDetailLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContentDetailLogId));

        node =  doc.createElement("DetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mDetailId)));
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
    * creates a clone of this object, the ContentDetailLogId field is not cloned.
    *
    * @return ContentDetailLogData object
    */
    public Object clone(){
        ContentDetailLogData myClone = new ContentDetailLogData();
        
        myClone.mDetailId = mDetailId;
        
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

        if (ContentDetailLogDataAccess.CONTENT_DETAIL_LOG_ID.equals(pFieldName)) {
            return getContentDetailLogId();
        } else if (ContentDetailLogDataAccess.DETAIL_ID.equals(pFieldName)) {
            return getDetailId();
        } else if (ContentDetailLogDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (ContentDetailLogDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (ContentDetailLogDataAccess.CHANGE.equals(pFieldName)) {
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
        return ContentDetailLogDataAccess.CLW_CONTENT_DETAIL_LOG;
    }

    
    /**
     * Sets the ContentDetailLogId field. This field is required to be set in the database.
     *
     * @param pContentDetailLogId
     *  int to use to update the field.
     */
    public void setContentDetailLogId(int pContentDetailLogId){
        this.mContentDetailLogId = pContentDetailLogId;
        setDirty(true);
    }
    /**
     * Retrieves the ContentDetailLogId field.
     *
     * @return
     *  int containing the ContentDetailLogId field.
     */
    public int getContentDetailLogId(){
        return mContentDetailLogId;
    }

    /**
     * Sets the DetailId field.
     *
     * @param pDetailId
     *  int to use to update the field.
     */
    public void setDetailId(int pDetailId){
        this.mDetailId = pDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the DetailId field.
     *
     * @return
     *  int containing the DetailId field.
     */
    public int getDetailId(){
        return mDetailId;
    }

    /**
     * Sets the Date field.
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
     * Sets the Time field.
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
