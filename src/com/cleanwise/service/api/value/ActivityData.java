
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ActivityData
 * Description:  This is a ValueObject class wrapping the database table CLW_ACTIVITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ActivityDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ActivityData</code> is a ValueObject class wrapping of the database table CLW_ACTIVITY.
 */
public class ActivityData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8358486493427003744L;
    private int mActivityId;// SQL type:NUMBER, not null
    private Date mDate;// SQL type:DATE, not null
    private Date mTime;// SQL type:DATE, not null
    private int mUserId;// SQL type:NUMBER, not null
    private String mActivity;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ActivityData ()
    {
        mActivity = "";
    }

    /**
     * Constructor.
     */
    public ActivityData(int parm1, Date parm2, Date parm3, int parm4, String parm5)
    {
        mActivityId = parm1;
        mDate = parm2;
        mTime = parm3;
        mUserId = parm4;
        mActivity = parm5;
        
    }

    /**
     * Creates a new ActivityData
     *
     * @return
     *  Newly initialized ActivityData object.
     */
    public static ActivityData createValue ()
    {
        ActivityData valueData = new ActivityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ActivityData object
     */
    public String toString()
    {
        return "[" + "ActivityId=" + mActivityId + ", Date=" + mDate + ", Time=" + mTime + ", UserId=" + mUserId + ", Activity=" + mActivity + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Activity");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mActivityId));

        node =  doc.createElement("Date");
        node.appendChild(doc.createTextNode(String.valueOf(mDate)));
        root.appendChild(node);

        node =  doc.createElement("Time");
        node.appendChild(doc.createTextNode(String.valueOf(mTime)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("Activity");
        node.appendChild(doc.createTextNode(String.valueOf(mActivity)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ActivityId field is not cloned.
    *
    * @return ActivityData object
    */
    public Object clone(){
        ActivityData myClone = new ActivityData();
        
        if(mDate != null){
                myClone.mDate = (Date) mDate.clone();
        }
        
        if(mTime != null){
                myClone.mTime = (Date) mTime.clone();
        }
        
        myClone.mUserId = mUserId;
        
        myClone.mActivity = mActivity;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ActivityDataAccess.ACTIVITY_ID.equals(pFieldName)) {
            return getActivityId();
        } else if (ActivityDataAccess.CLW_DATE.equals(pFieldName)) {
            return getDate();
        } else if (ActivityDataAccess.CLW_TIME.equals(pFieldName)) {
            return getTime();
        } else if (ActivityDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (ActivityDataAccess.ACTIVITY.equals(pFieldName)) {
            return getActivity();
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
        return ActivityDataAccess.CLW_ACTIVITY;
    }

    
    /**
     * Sets the ActivityId field. This field is required to be set in the database.
     *
     * @param pActivityId
     *  int to use to update the field.
     */
    public void setActivityId(int pActivityId){
        this.mActivityId = pActivityId;
        setDirty(true);
    }
    /**
     * Retrieves the ActivityId field.
     *
     * @return
     *  int containing the ActivityId field.
     */
    public int getActivityId(){
        return mActivityId;
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
     * Sets the UserId field. This field is required to be set in the database.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
    }

    /**
     * Sets the Activity field.
     *
     * @param pActivity
     *  String to use to update the field.
     */
    public void setActivity(String pActivity){
        this.mActivity = pActivity;
        setDirty(true);
    }
    /**
     * Retrieves the Activity field.
     *
     * @return
     *  String containing the Activity field.
     */
    public String getActivity(){
        return mActivity;
    }


}
