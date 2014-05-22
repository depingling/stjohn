
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NoteAttachmentData
 * Description:  This is a ValueObject class wrapping the database table CLW_NOTE_ATTACHMENT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.NoteAttachmentDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>NoteAttachmentData</code> is a ValueObject class wrapping of the database table CLW_NOTE_ATTACHMENT.
 */
public class NoteAttachmentData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 3714207616991372957L;
    private int mNoteAttachmentId;// SQL type:NUMBER, not null
    private int mNoteId;// SQL type:NUMBER, not null
    private String mServerName;// SQL type:VARCHAR2
    private String mFileName;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private byte[] mBinaryData;// SQL type:BLOB

    /**
     * Constructor.
     */
    public NoteAttachmentData ()
    {
        mServerName = "";
        mFileName = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public NoteAttachmentData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, byte[] parm9)
    {
        mNoteAttachmentId = parm1;
        mNoteId = parm2;
        mServerName = parm3;
        mFileName = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mBinaryData = parm9;
        
    }

    /**
     * Creates a new NoteAttachmentData
     *
     * @return
     *  Newly initialized NoteAttachmentData object.
     */
    public static NoteAttachmentData createValue ()
    {
        NoteAttachmentData valueData = new NoteAttachmentData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NoteAttachmentData object
     */
    public String toString()
    {
        return "[" + "NoteAttachmentId=" + mNoteAttachmentId + ", NoteId=" + mNoteId + ", ServerName=" + mServerName + ", FileName=" + mFileName + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", BinaryData=" + mBinaryData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("NoteAttachment");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mNoteAttachmentId));

        node =  doc.createElement("NoteId");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteId)));
        root.appendChild(node);

        node =  doc.createElement("ServerName");
        node.appendChild(doc.createTextNode(String.valueOf(mServerName)));
        root.appendChild(node);

        node =  doc.createElement("FileName");
        node.appendChild(doc.createTextNode(String.valueOf(mFileName)));
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

        node =  doc.createElement("BinaryData");
        node.appendChild(doc.createTextNode(String.valueOf(mBinaryData)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the NoteAttachmentId field is not cloned.
    *
    * @return NoteAttachmentData object
    */
    public Object clone(){
        NoteAttachmentData myClone = new NoteAttachmentData();
        
        myClone.mNoteId = mNoteId;
        
        myClone.mServerName = mServerName;
        
        myClone.mFileName = mFileName;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mBinaryData = mBinaryData;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (NoteAttachmentDataAccess.NOTE_ATTACHMENT_ID.equals(pFieldName)) {
            return getNoteAttachmentId();
        } else if (NoteAttachmentDataAccess.NOTE_ID.equals(pFieldName)) {
            return getNoteId();
        } else if (NoteAttachmentDataAccess.SERVER_NAME.equals(pFieldName)) {
            return getServerName();
        } else if (NoteAttachmentDataAccess.FILE_NAME.equals(pFieldName)) {
            return getFileName();
        } else if (NoteAttachmentDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (NoteAttachmentDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (NoteAttachmentDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (NoteAttachmentDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (NoteAttachmentDataAccess.BINARY_DATA.equals(pFieldName)) {
            return getBinaryData();
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
        return NoteAttachmentDataAccess.CLW_NOTE_ATTACHMENT;
    }

    
    /**
     * Sets the NoteAttachmentId field. This field is required to be set in the database.
     *
     * @param pNoteAttachmentId
     *  int to use to update the field.
     */
    public void setNoteAttachmentId(int pNoteAttachmentId){
        this.mNoteAttachmentId = pNoteAttachmentId;
        setDirty(true);
    }
    /**
     * Retrieves the NoteAttachmentId field.
     *
     * @return
     *  int containing the NoteAttachmentId field.
     */
    public int getNoteAttachmentId(){
        return mNoteAttachmentId;
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
     * Sets the ServerName field.
     *
     * @param pServerName
     *  String to use to update the field.
     */
    public void setServerName(String pServerName){
        this.mServerName = pServerName;
        setDirty(true);
    }
    /**
     * Retrieves the ServerName field.
     *
     * @return
     *  String containing the ServerName field.
     */
    public String getServerName(){
        return mServerName;
    }

    /**
     * Sets the FileName field. This field is required to be set in the database.
     *
     * @param pFileName
     *  String to use to update the field.
     */
    public void setFileName(String pFileName){
        this.mFileName = pFileName;
        setDirty(true);
    }
    /**
     * Retrieves the FileName field.
     *
     * @return
     *  String containing the FileName field.
     */
    public String getFileName(){
        return mFileName;
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
     * Sets the BinaryData field.
     *
     * @param pBinaryData
     *  byte[] to use to update the field.
     */
    public void setBinaryData(byte[] pBinaryData){
        this.mBinaryData = pBinaryData;
        setDirty(true);
    }
    /**
     * Retrieves the BinaryData field.
     *
     * @return
     *  byte[] containing the BinaryData field.
     */
    public byte[] getBinaryData(){
        return mBinaryData;
    }


}
