
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContentLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTENT_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContentLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContentLogData</code> is a ValueObject class wrapping of the database table CLW_CONTENT_LOG.
 */
public class ContentLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -9184120064795209926L;
    private int mContentLogId;// SQL type:NUMBER, not null
    private int mContentId;// SQL type:NUMBER
    private Date mDate;// SQL type:DATE
    private Date mTime;// SQL type:DATE
    private String mChange;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ContentLogData ()
    {
        mChange = "";
    }

    /**
     * Constructor.
     */
    public ContentLogData(int parm1, int parm2, Date parm3, Date parm4, String parm5)
    {
        mContentLogId = parm1;
        mContentId = parm2;
        mDate = parm3;
        mTime = parm4;
        mChange = parm5;
        
    }

    /**
     * Creates a new ContentLogData
     *
     * @return
     *  Newly initialized ContentLogData object.
     */
    public static ContentLogData createValue ()
    {
        ContentLogData valueData = new ContentLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContentLogData object
     */
    public String toString()
    {
        return "[" + "ContentLogId=" + mContentLogId + ", ContentId=" + mContentId + ", Date=" + mDate + ", Time=" + mTime + ", Change=" + mChange + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ContentLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContentLogId));

        node =  doc.createElement("ContentId");
        node.appendChild(doc.createTextNode(String.valueOf(mContentId)));
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
    * creates a clone of this object, the ContentLogId field is not cloned.
    *
    * @return ContentLogData object
    */
    public Object clone(){
        ContentLogData myClone = new ContentLogData();
        
        myClone.mContentId = mContentId;
        
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

        if (ContentLogDataAccess.CONTENT_LOG_ID.equals(pFieldName)) {
            return getContentLogId();
        } else if (ContentLogDataAccess.CONTENT_ID.equals(pFieldName)) {
            return getContentId();
        } else if (ContentLogDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (ContentLogDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (ContentLogDataAccess.CHANGE.equals(pFieldName)) {
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
        return ContentLogDataAccess.CLW_CONTENT_LOG;
    }

    
    /**
     * Sets the ContentLogId field. This field is required to be set in the database.
     *
     * @param pContentLogId
     *  int to use to update the field.
     */
    public void setContentLogId(int pContentLogId){
        this.mContentLogId = pContentLogId;
        setDirty(true);
    }
    /**
     * Retrieves the ContentLogId field.
     *
     * @return
     *  int containing the ContentLogId field.
     */
    public int getContentLogId(){
        return mContentLogId;
    }

    /**
     * Sets the ContentId field.
     *
     * @param pContentId
     *  int to use to update the field.
     */
    public void setContentId(int pContentId){
        this.mContentId = pContentId;
        setDirty(true);
    }
    /**
     * Retrieves the ContentId field.
     *
     * @return
     *  int containing the ContentId field.
     */
    public int getContentId(){
        return mContentId;
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
