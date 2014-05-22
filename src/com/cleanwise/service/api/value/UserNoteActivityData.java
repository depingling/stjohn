
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserNoteActivityData
 * Description:  This is a ValueObject class wrapping the database table CLW_USER_NOTE_ACTIVITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UserNoteActivityDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UserNoteActivityData</code> is a ValueObject class wrapping of the database table CLW_USER_NOTE_ACTIVITY.
 */
public class UserNoteActivityData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1771642313986707347L;
    private int mUserNoteActivityId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER, not null
    private int mNoteId;// SQL type:NUMBER
    private int mNoteCounter;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public UserNoteActivityData ()
    {
    }

    /**
     * Constructor.
     */
    public UserNoteActivityData(int parm1, int parm2, int parm3, int parm4)
    {
        mUserNoteActivityId = parm1;
        mUserId = parm2;
        mNoteId = parm3;
        mNoteCounter = parm4;
        
    }

    /**
     * Creates a new UserNoteActivityData
     *
     * @return
     *  Newly initialized UserNoteActivityData object.
     */
    public static UserNoteActivityData createValue ()
    {
        UserNoteActivityData valueData = new UserNoteActivityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserNoteActivityData object
     */
    public String toString()
    {
        return "[" + "UserNoteActivityId=" + mUserNoteActivityId + ", UserId=" + mUserId + ", NoteId=" + mNoteId + ", NoteCounter=" + mNoteCounter + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UserNoteActivity");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUserNoteActivityId));

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("NoteId");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteId)));
        root.appendChild(node);

        node =  doc.createElement("NoteCounter");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteCounter)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UserNoteActivityId field is not cloned.
    *
    * @return UserNoteActivityData object
    */
    public Object clone(){
        UserNoteActivityData myClone = new UserNoteActivityData();
        
        myClone.mUserId = mUserId;
        
        myClone.mNoteId = mNoteId;
        
        myClone.mNoteCounter = mNoteCounter;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (UserNoteActivityDataAccess.USER_NOTE_ACTIVITY_ID.equals(pFieldName)) {
            return getUserNoteActivityId();
        } else if (UserNoteActivityDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (UserNoteActivityDataAccess.NOTE_ID.equals(pFieldName)) {
            return getNoteId();
        } else if (UserNoteActivityDataAccess.NOTE_COUNTER.equals(pFieldName)) {
            return getNoteCounter();
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
        return UserNoteActivityDataAccess.CLW_USER_NOTE_ACTIVITY;
    }

    
    /**
     * Sets the UserNoteActivityId field. This field is required to be set in the database.
     *
     * @param pUserNoteActivityId
     *  int to use to update the field.
     */
    public void setUserNoteActivityId(int pUserNoteActivityId){
        this.mUserNoteActivityId = pUserNoteActivityId;
        setDirty(true);
    }
    /**
     * Retrieves the UserNoteActivityId field.
     *
     * @return
     *  int containing the UserNoteActivityId field.
     */
    public int getUserNoteActivityId(){
        return mUserNoteActivityId;
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
     * Sets the NoteId field.
     *
     * @param pNoteId
     *  int to use to update the field.
     */
    public void setNoteId(int pNoteId){
        this.mNoteId = pNoteId;
        setDirty(true);
    }
    /**
     * Retrieves the NoteId field.
     *
     * @return
     *  int containing the NoteId field.
     */
    public int getNoteId(){
        return mNoteId;
    }

    /**
     * Sets the NoteCounter field.
     *
     * @param pNoteCounter
     *  int to use to update the field.
     */
    public void setNoteCounter(int pNoteCounter){
        this.mNoteCounter = pNoteCounter;
        setDirty(true);
    }
    /**
     * Retrieves the NoteCounter field.
     *
     * @return
     *  int containing the NoteCounter field.
     */
    public int getNoteCounter(){
        return mNoteCounter;
    }


}
