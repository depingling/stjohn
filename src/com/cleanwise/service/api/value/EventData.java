
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EventData
 * Description:  This is a ValueObject class wrapping the database table CLW_EVENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.EventDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>EventData</code> is a ValueObject class wrapping of the database table CLW_EVENT.
 */
public class EventData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -8374655334511244457L;
    private int mEventId;// SQL type:NUMBER, not null
    private String mType;// SQL type:VARCHAR2, not null
    private String mStatus;// SQL type:VARCHAR2, not null
    private String mCd;// SQL type:VARCHAR2
    private int mAttempt;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mEventPriority;// SQL type:NUMBER
    private Date mProcessTime;// SQL type:DATE

    /**
     * Constructor.
     */
    public EventData ()
    {
        mType = "";
        mStatus = "";
        mCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public EventData(int parm1, String parm2, String parm3, String parm4, int parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10, Date parm11)
    {
        mEventId = parm1;
        mType = parm2;
        mStatus = parm3;
        mCd = parm4;
        mAttempt = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mEventPriority = parm10;
        mProcessTime = parm11;
        
    }

    /**
     * Creates a new EventData
     *
     * @return
     *  Newly initialized EventData object.
     */
    public static EventData createValue ()
    {
        EventData valueData = new EventData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EventData object
     */
    public String toString()
    {
        return "[" + "EventId=" + mEventId + ", Type=" + mType + ", Status=" + mStatus + ", Cd=" + mCd + ", Attempt=" + mAttempt + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", EventPriority=" + mEventPriority + ", ProcessTime=" + mProcessTime + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Event");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mEventId));

        node =  doc.createElement("Type");
        node.appendChild(doc.createTextNode(String.valueOf(mType)));
        root.appendChild(node);

        node =  doc.createElement("Status");
        node.appendChild(doc.createTextNode(String.valueOf(mStatus)));
        root.appendChild(node);

        node =  doc.createElement("Cd");
        node.appendChild(doc.createTextNode(String.valueOf(mCd)));
        root.appendChild(node);

        node =  doc.createElement("Attempt");
        node.appendChild(doc.createTextNode(String.valueOf(mAttempt)));
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

        node =  doc.createElement("EventPriority");
        node.appendChild(doc.createTextNode(String.valueOf(mEventPriority)));
        root.appendChild(node);

        node =  doc.createElement("ProcessTime");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessTime)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the EventId field is not cloned.
    *
    * @return EventData object
    */
    public Object clone(){
        EventData myClone = new EventData();
        
        myClone.mType = mType;
        
        myClone.mStatus = mStatus;
        
        myClone.mCd = mCd;
        
        myClone.mAttempt = mAttempt;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mEventPriority = mEventPriority;
        
        if(mProcessTime != null){
                myClone.mProcessTime = (Date) mProcessTime.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (EventDataAccess.EVENT_ID.equals(pFieldName)) {
            return getEventId();
        } else if (EventDataAccess.TYPE.equals(pFieldName)) {
            return getType();
        } else if (EventDataAccess.STATUS.equals(pFieldName)) {
            return getStatus();
        } else if (EventDataAccess.CD.equals(pFieldName)) {
            return getCd();
        } else if (EventDataAccess.ATTEMPT.equals(pFieldName)) {
            return getAttempt();
        } else if (EventDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (EventDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (EventDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (EventDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (EventDataAccess.EVENT_PRIORITY.equals(pFieldName)) {
            return getEventPriority();
        } else if (EventDataAccess.PROCESS_TIME.equals(pFieldName)) {
            return getProcessTime();
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
        return EventDataAccess.CLW_EVENT;
    }

    
    /**
     * Sets the EventId field. This field is required to be set in the database.
     *
     * @param pEventId
     *  int to use to update the field.
     */
    public void setEventId(int pEventId){
        this.mEventId = pEventId;
        setDirty(true);
    }
    /**
     * Retrieves the EventId field.
     *
     * @return
     *  int containing the EventId field.
     */
    public int getEventId(){
        return mEventId;
    }

    /**
     * Sets the Type field. This field is required to be set in the database.
     *
     * @param pType
     *  String to use to update the field.
     */
    public void setType(String pType){
        this.mType = pType;
        setDirty(true);
    }
    /**
     * Retrieves the Type field.
     *
     * @return
     *  String containing the Type field.
     */
    public String getType(){
        return mType;
    }

    /**
     * Sets the Status field. This field is required to be set in the database.
     *
     * @param pStatus
     *  String to use to update the field.
     */
    public void setStatus(String pStatus){
        this.mStatus = pStatus;
        setDirty(true);
    }
    /**
     * Retrieves the Status field.
     *
     * @return
     *  String containing the Status field.
     */
    public String getStatus(){
        return mStatus;
    }

    /**
     * Sets the Cd field.
     *
     * @param pCd
     *  String to use to update the field.
     */
    public void setCd(String pCd){
        this.mCd = pCd;
        setDirty(true);
    }
    /**
     * Retrieves the Cd field.
     *
     * @return
     *  String containing the Cd field.
     */
    public String getCd(){
        return mCd;
    }

    /**
     * Sets the Attempt field.
     *
     * @param pAttempt
     *  int to use to update the field.
     */
    public void setAttempt(int pAttempt){
        this.mAttempt = pAttempt;
        setDirty(true);
    }
    /**
     * Retrieves the Attempt field.
     *
     * @return
     *  int containing the Attempt field.
     */
    public int getAttempt(){
        return mAttempt;
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

    /**
     * Sets the EventPriority field.
     *
     * @param pEventPriority
     *  int to use to update the field.
     */
    public void setEventPriority(int pEventPriority){
        this.mEventPriority = pEventPriority;
        setDirty(true);
    }
    /**
     * Retrieves the EventPriority field.
     *
     * @return
     *  int containing the EventPriority field.
     */
    public int getEventPriority(){
        return mEventPriority;
    }

    /**
     * Sets the ProcessTime field.
     *
     * @param pProcessTime
     *  Date to use to update the field.
     */
    public void setProcessTime(Date pProcessTime){
        this.mProcessTime = pProcessTime;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessTime field.
     *
     * @return
     *  Date containing the ProcessTime field.
     */
    public Date getProcessTime(){
        return mProcessTime;
    }


}
