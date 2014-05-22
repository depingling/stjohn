
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProfileMetaData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROFILE_META.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProfileMetaDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProfileMetaData</code> is a ValueObject class wrapping of the database table CLW_PROFILE_META.
 */
public class ProfileMetaData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5474981799232596510L;
    private int mProfileMetaId;// SQL type:NUMBER, not null
    private int mProfileId;// SQL type:NUMBER
    private String mProfileMetaTypeCd;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mHelpText;// SQL type:VARCHAR2
    private String mProfileMetaStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    public ProfileMetaData ()
    {
        mProfileMetaTypeCd = "";
        mValue = "";
        mHelpText = "";
        mProfileMetaStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ProfileMetaData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mProfileMetaId = parm1;
        mProfileId = parm2;
        mProfileMetaTypeCd = parm3;
        mValue = parm4;
        mHelpText = parm5;
        mProfileMetaStatusCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new ProfileMetaData
     *
     * @return
     *  Newly initialized ProfileMetaData object.
     */
    public static ProfileMetaData createValue ()
    {
        ProfileMetaData valueData = new ProfileMetaData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProfileMetaData object
     */
    public String toString()
    {
        return "[" + "ProfileMetaId=" + mProfileMetaId + ", ProfileId=" + mProfileId + ", ProfileMetaTypeCd=" + mProfileMetaTypeCd + ", Value=" + mValue + ", HelpText=" + mHelpText + ", ProfileMetaStatusCd=" + mProfileMetaStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProfileMeta");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProfileMetaId));

        node =  doc.createElement("ProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileId)));
        root.appendChild(node);

        node =  doc.createElement("ProfileMetaTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileMetaTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("HelpText");
        node.appendChild(doc.createTextNode(String.valueOf(mHelpText)));
        root.appendChild(node);

        node =  doc.createElement("ProfileMetaStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileMetaStatusCd)));
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
    * creates a clone of this object, the ProfileMetaId field is not cloned.
    *
    * @return ProfileMetaData object
    */
    public Object clone(){
        ProfileMetaData myClone = new ProfileMetaData();
        
        myClone.mProfileId = mProfileId;
        
        myClone.mProfileMetaTypeCd = mProfileMetaTypeCd;
        
        myClone.mValue = mValue;
        
        myClone.mHelpText = mHelpText;
        
        myClone.mProfileMetaStatusCd = mProfileMetaStatusCd;
        
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

        if (ProfileMetaDataAccess.PROFILE_META_ID.equals(pFieldName)) {
            return getProfileMetaId();
        } else if (ProfileMetaDataAccess.PROFILE_ID.equals(pFieldName)) {
            return getProfileId();
        } else if (ProfileMetaDataAccess.PROFILE_META_TYPE_CD.equals(pFieldName)) {
            return getProfileMetaTypeCd();
        } else if (ProfileMetaDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ProfileMetaDataAccess.HELP_TEXT.equals(pFieldName)) {
            return getHelpText();
        } else if (ProfileMetaDataAccess.PROFILE_META_STATUS_CD.equals(pFieldName)) {
            return getProfileMetaStatusCd();
        } else if (ProfileMetaDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProfileMetaDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProfileMetaDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProfileMetaDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ProfileMetaDataAccess.CLW_PROFILE_META;
    }

    
    /**
     * Sets the ProfileMetaId field. This field is required to be set in the database.
     *
     * @param pProfileMetaId
     *  int to use to update the field.
     */
    public void setProfileMetaId(int pProfileMetaId){
        this.mProfileMetaId = pProfileMetaId;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileMetaId field.
     *
     * @return
     *  int containing the ProfileMetaId field.
     */
    public int getProfileMetaId(){
        return mProfileMetaId;
    }

    /**
     * Sets the ProfileId field.
     *
     * @param pProfileId
     *  int to use to update the field.
     */
    public void setProfileId(int pProfileId){
        this.mProfileId = pProfileId;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileId field.
     *
     * @return
     *  int containing the ProfileId field.
     */
    public int getProfileId(){
        return mProfileId;
    }

    /**
     * Sets the ProfileMetaTypeCd field.
     *
     * @param pProfileMetaTypeCd
     *  String to use to update the field.
     */
    public void setProfileMetaTypeCd(String pProfileMetaTypeCd){
        this.mProfileMetaTypeCd = pProfileMetaTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileMetaTypeCd field.
     *
     * @return
     *  String containing the ProfileMetaTypeCd field.
     */
    public String getProfileMetaTypeCd(){
        return mProfileMetaTypeCd;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
    }

    /**
     * Sets the HelpText field.
     *
     * @param pHelpText
     *  String to use to update the field.
     */
    public void setHelpText(String pHelpText){
        this.mHelpText = pHelpText;
        setDirty(true);
    }
    /**
     * Retrieves the HelpText field.
     *
     * @return
     *  String containing the HelpText field.
     */
    public String getHelpText(){
        return mHelpText;
    }

    /**
     * Sets the ProfileMetaStatusCd field.
     *
     * @param pProfileMetaStatusCd
     *  String to use to update the field.
     */
    public void setProfileMetaStatusCd(String pProfileMetaStatusCd){
        this.mProfileMetaStatusCd = pProfileMetaStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileMetaStatusCd field.
     *
     * @return
     *  String containing the ProfileMetaStatusCd field.
     */
    public String getProfileMetaStatusCd(){
        return mProfileMetaStatusCd;
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
