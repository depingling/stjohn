
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NoteData
 * Description:  This is a ValueObject class wrapping the database table CLW_NOTE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.NoteDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>NoteData</code> is a ValueObject class wrapping of the database table CLW_NOTE.
 */
public class NoteData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8028320161462501131L;
    private int mNoteId;// SQL type:NUMBER, not null
    private int mPropertyId;// SQL type:NUMBER, not null
    private String mNoteTypeCd;// SQL type:VARCHAR2, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mTitle;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private int mCounter;// SQL type:NUMBER
    private String mLocaleCd;// SQL type:VARCHAR2
    private String mForcedEachLogin;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    public NoteData ()
    {
        mNoteTypeCd = "";
        mTitle = "";
        mAddBy = "";
        mModBy = "";
        mLocaleCd = "";
        mForcedEachLogin = "";
    }

    /**
     * Constructor.
     */
    public NoteData(int parm1, int parm2, String parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, Date parm10, Date parm11, int parm12, String parm13, String parm14)
    {
        mNoteId = parm1;
        mPropertyId = parm2;
        mNoteTypeCd = parm3;
        mBusEntityId = parm4;
        mTitle = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mEffDate = parm10;
        mExpDate = parm11;
        mCounter = parm12;
        mLocaleCd = parm13;
        mForcedEachLogin = parm14;
        
    }

    /**
     * Creates a new NoteData
     *
     * @return
     *  Newly initialized NoteData object.
     */
    public static NoteData createValue ()
    {
        NoteData valueData = new NoteData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NoteData object
     */
    public String toString()
    {
        return "[" + "NoteId=" + mNoteId + ", PropertyId=" + mPropertyId + ", NoteTypeCd=" + mNoteTypeCd + ", BusEntityId=" + mBusEntityId + ", Title=" + mTitle + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", Counter=" + mCounter + ", LocaleCd=" + mLocaleCd + ", ForcedEachLogin=" + mForcedEachLogin + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Note");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mNoteId));

        node =  doc.createElement("PropertyId");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyId)));
        root.appendChild(node);

        node =  doc.createElement("NoteTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("Title");
        node.appendChild(doc.createTextNode(String.valueOf(mTitle)));
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

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("Counter");
        node.appendChild(doc.createTextNode(String.valueOf(mCounter)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("ForcedEachLogin");
        node.appendChild(doc.createTextNode(String.valueOf(mForcedEachLogin)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the NoteId field is not cloned.
    *
    * @return NoteData object
    */
    public Object clone(){
        NoteData myClone = new NoteData();
        
        myClone.mPropertyId = mPropertyId;
        
        myClone.mNoteTypeCd = mNoteTypeCd;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mTitle = mTitle;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mCounter = mCounter;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mForcedEachLogin = mForcedEachLogin;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (NoteDataAccess.NOTE_ID.equals(pFieldName)) {
            return getNoteId();
        } else if (NoteDataAccess.PROPERTY_ID.equals(pFieldName)) {
            return getPropertyId();
        } else if (NoteDataAccess.NOTE_TYPE_CD.equals(pFieldName)) {
            return getNoteTypeCd();
        } else if (NoteDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (NoteDataAccess.TITLE.equals(pFieldName)) {
            return getTitle();
        } else if (NoteDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (NoteDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (NoteDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (NoteDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (NoteDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (NoteDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (NoteDataAccess.COUNTER.equals(pFieldName)) {
            return getCounter();
        } else if (NoteDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (NoteDataAccess.FORCED_EACH_LOGIN.equals(pFieldName)) {
            return getForcedEachLogin();
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
        return NoteDataAccess.CLW_NOTE;
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
     * Sets the PropertyId field. This field is required to be set in the database.
     *
     * @param pPropertyId
     *  int to use to update the field.
     */
    public void setPropertyId(int pPropertyId){
        this.mPropertyId = pPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the PropertyId field.
     *
     * @return
     *  int containing the PropertyId field.
     */
    public int getPropertyId(){
        return mPropertyId;
    }

    /**
     * Sets the NoteTypeCd field. This field is required to be set in the database.
     *
     * @param pNoteTypeCd
     *  String to use to update the field.
     */
    public void setNoteTypeCd(String pNoteTypeCd){
        this.mNoteTypeCd = pNoteTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the NoteTypeCd field.
     *
     * @return
     *  String containing the NoteTypeCd field.
     */
    public String getNoteTypeCd(){
        return mNoteTypeCd;
    }

    /**
     * Sets the BusEntityId field.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }

    /**
     * Sets the Title field. This field is required to be set in the database.
     *
     * @param pTitle
     *  String to use to update the field.
     */
    public void setTitle(String pTitle){
        this.mTitle = pTitle;
        setDirty(true);
    }
    /**
     * Retrieves the Title field.
     *
     * @return
     *  String containing the Title field.
     */
    public String getTitle(){
        return mTitle;
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
     * Sets the EffDate field.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the Counter field.
     *
     * @param pCounter
     *  int to use to update the field.
     */
    public void setCounter(int pCounter){
        this.mCounter = pCounter;
        setDirty(true);
    }
    /**
     * Retrieves the Counter field.
     *
     * @return
     *  int containing the Counter field.
     */
    public int getCounter(){
        return mCounter;
    }

    /**
     * Sets the LocaleCd field.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }

    /**
     * Sets the ForcedEachLogin field. This field is required to be set in the database.
     *
     * @param pForcedEachLogin
     *  String to use to update the field.
     */
    public void setForcedEachLogin(String pForcedEachLogin){
        this.mForcedEachLogin = pForcedEachLogin;
        setDirty(true);
    }
    /**
     * Retrieves the ForcedEachLogin field.
     *
     * @return
     *  String containing the ForcedEachLogin field.
     */
    public String getForcedEachLogin(){
        return mForcedEachLogin;
    }


}
