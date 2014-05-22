
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NoteTextData
 * Description:  This is a ValueObject class wrapping the database table CLW_NOTE_TEXT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.NoteTextDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>NoteTextData</code> is a ValueObject class wrapping of the database table CLW_NOTE_TEXT.
 */
public class NoteTextData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6945824884254966020L;
    private int mNoteTextId;// SQL type:NUMBER, not null
    private String mUserFirstName;// SQL type:VARCHAR2
    private String mUserLastName;// SQL type:VARCHAR2, not null
    private int mNoteId;// SQL type:NUMBER, not null
    private int mSeqNum;// SQL type:NUMBER, not null
    private int mPageNum;// SQL type:NUMBER, not null
    private String mNoteText;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    public NoteTextData ()
    {
        mUserFirstName = "";
        mUserLastName = "";
        mNoteText = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public NoteTextData(int parm1, String parm2, String parm3, int parm4, int parm5, int parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mNoteTextId = parm1;
        mUserFirstName = parm2;
        mUserLastName = parm3;
        mNoteId = parm4;
        mSeqNum = parm5;
        mPageNum = parm6;
        mNoteText = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new NoteTextData
     *
     * @return
     *  Newly initialized NoteTextData object.
     */
    public static NoteTextData createValue ()
    {
        NoteTextData valueData = new NoteTextData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NoteTextData object
     */
    public String toString()
    {
        return "[" + "NoteTextId=" + mNoteTextId + ", UserFirstName=" + mUserFirstName + ", UserLastName=" + mUserLastName + ", NoteId=" + mNoteId + ", SeqNum=" + mSeqNum + ", PageNum=" + mPageNum + ", NoteText=" + mNoteText + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("NoteText");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mNoteTextId));

        node =  doc.createElement("UserFirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserFirstName)));
        root.appendChild(node);

        node =  doc.createElement("UserLastName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserLastName)));
        root.appendChild(node);

        node =  doc.createElement("NoteId");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteId)));
        root.appendChild(node);

        node =  doc.createElement("SeqNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSeqNum)));
        root.appendChild(node);

        node =  doc.createElement("PageNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPageNum)));
        root.appendChild(node);

        node =  doc.createElement("NoteText");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteText)));
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
    * creates a clone of this object, the NoteTextId field is not cloned.
    *
    * @return NoteTextData object
    */
    public Object clone(){
        NoteTextData myClone = new NoteTextData();
        
        myClone.mUserFirstName = mUserFirstName;
        
        myClone.mUserLastName = mUserLastName;
        
        myClone.mNoteId = mNoteId;
        
        myClone.mSeqNum = mSeqNum;
        
        myClone.mPageNum = mPageNum;
        
        myClone.mNoteText = mNoteText;
        
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

        if (NoteTextDataAccess.NOTE_TEXT_ID.equals(pFieldName)) {
            return getNoteTextId();
        } else if (NoteTextDataAccess.USER_FIRST_NAME.equals(pFieldName)) {
            return getUserFirstName();
        } else if (NoteTextDataAccess.USER_LAST_NAME.equals(pFieldName)) {
            return getUserLastName();
        } else if (NoteTextDataAccess.NOTE_ID.equals(pFieldName)) {
            return getNoteId();
        } else if (NoteTextDataAccess.SEQ_NUM.equals(pFieldName)) {
            return getSeqNum();
        } else if (NoteTextDataAccess.PAGE_NUM.equals(pFieldName)) {
            return getPageNum();
        } else if (NoteTextDataAccess.NOTE_TEXT.equals(pFieldName)) {
            return getNoteText();
        } else if (NoteTextDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (NoteTextDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (NoteTextDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (NoteTextDataAccess.MOD_BY.equals(pFieldName)) {
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
        return NoteTextDataAccess.CLW_NOTE_TEXT;
    }

    
    /**
     * Sets the NoteTextId field. This field is required to be set in the database.
     *
     * @param pNoteTextId
     *  int to use to update the field.
     */
    public void setNoteTextId(int pNoteTextId){
        this.mNoteTextId = pNoteTextId;
        setDirty(true);
    }
    /**
     * Retrieves the NoteTextId field.
     *
     * @return
     *  int containing the NoteTextId field.
     */
    public int getNoteTextId(){
        return mNoteTextId;
    }

    /**
     * Sets the UserFirstName field.
     *
     * @param pUserFirstName
     *  String to use to update the field.
     */
    public void setUserFirstName(String pUserFirstName){
        this.mUserFirstName = pUserFirstName;
        setDirty(true);
    }
    /**
     * Retrieves the UserFirstName field.
     *
     * @return
     *  String containing the UserFirstName field.
     */
    public String getUserFirstName(){
        return mUserFirstName;
    }

    /**
     * Sets the UserLastName field. This field is required to be set in the database.
     *
     * @param pUserLastName
     *  String to use to update the field.
     */
    public void setUserLastName(String pUserLastName){
        this.mUserLastName = pUserLastName;
        setDirty(true);
    }
    /**
     * Retrieves the UserLastName field.
     *
     * @return
     *  String containing the UserLastName field.
     */
    public String getUserLastName(){
        return mUserLastName;
    }

    /**
     * Sets the NoteId field. This field is required to be set in the database.
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
     * Sets the SeqNum field. This field is required to be set in the database.
     *
     * @param pSeqNum
     *  int to use to update the field.
     */
    public void setSeqNum(int pSeqNum){
        this.mSeqNum = pSeqNum;
        setDirty(true);
    }
    /**
     * Retrieves the SeqNum field.
     *
     * @return
     *  int containing the SeqNum field.
     */
    public int getSeqNum(){
        return mSeqNum;
    }

    /**
     * Sets the PageNum field. This field is required to be set in the database.
     *
     * @param pPageNum
     *  int to use to update the field.
     */
    public void setPageNum(int pPageNum){
        this.mPageNum = pPageNum;
        setDirty(true);
    }
    /**
     * Retrieves the PageNum field.
     *
     * @return
     *  int containing the PageNum field.
     */
    public int getPageNum(){
        return mPageNum;
    }

    /**
     * Sets the NoteText field.
     *
     * @param pNoteText
     *  String to use to update the field.
     */
    public void setNoteText(String pNoteText){
        this.mNoteText = pNoteText;
        setDirty(true);
    }
    /**
     * Retrieves the NoteText field.
     *
     * @return
     *  String containing the NoteText field.
     */
    public String getNoteText(){
        return mNoteText;
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
     * Sets the AddBy field. This field is required to be set in the database.
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
     * Sets the ModBy field. This field is required to be set in the database.
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
