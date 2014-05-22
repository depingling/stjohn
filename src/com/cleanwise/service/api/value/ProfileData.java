
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProfileData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROFILE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProfileDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProfileData</code> is a ValueObject class wrapping of the database table CLW_PROFILE.
 */
public class ProfileData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -4675960894733720566L;
    private int mProfileId;// SQL type:NUMBER, not null
    private int mProfileOrder;// SQL type:NUMBER
    private String mProfileTypeCd;// SQL type:VARCHAR2
    private String mProfileStatusCd;// SQL type:VARCHAR2
    private String mProfileQuestionTypeCd;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mHelpText;// SQL type:VARCHAR2
    private String mReadOnly;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null
    private String mImage;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ProfileData ()
    {
        mProfileTypeCd = "";
        mProfileStatusCd = "";
        mProfileQuestionTypeCd = "";
        mShortDesc = "";
        mHelpText = "";
        mReadOnly = "";
        mAddBy = "";
        mModBy = "";
        mImage = "";
    }

    /**
     * Constructor.
     */
    public ProfileData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, Date parm9, String parm10, Date parm11, String parm12, String parm13)
    {
        mProfileId = parm1;
        mProfileOrder = parm2;
        mProfileTypeCd = parm3;
        mProfileStatusCd = parm4;
        mProfileQuestionTypeCd = parm5;
        mShortDesc = parm6;
        mHelpText = parm7;
        mReadOnly = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mImage = parm13;
        
    }

    /**
     * Creates a new ProfileData
     *
     * @return
     *  Newly initialized ProfileData object.
     */
    public static ProfileData createValue ()
    {
        ProfileData valueData = new ProfileData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProfileData object
     */
    public String toString()
    {
        return "[" + "ProfileId=" + mProfileId + ", ProfileOrder=" + mProfileOrder + ", ProfileTypeCd=" + mProfileTypeCd + ", ProfileStatusCd=" + mProfileStatusCd + ", ProfileQuestionTypeCd=" + mProfileQuestionTypeCd + ", ShortDesc=" + mShortDesc + ", HelpText=" + mHelpText + ", ReadOnly=" + mReadOnly + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Image=" + mImage + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Profile");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProfileId));

        node =  doc.createElement("ProfileOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileOrder)));
        root.appendChild(node);

        node =  doc.createElement("ProfileTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ProfileStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ProfileQuestionTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileQuestionTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("HelpText");
        node.appendChild(doc.createTextNode(String.valueOf(mHelpText)));
        root.appendChild(node);

        node =  doc.createElement("ReadOnly");
        node.appendChild(doc.createTextNode(String.valueOf(mReadOnly)));
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

        node =  doc.createElement("Image");
        node.appendChild(doc.createTextNode(String.valueOf(mImage)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ProfileId field is not cloned.
    *
    * @return ProfileData object
    */
    public Object clone(){
        ProfileData myClone = new ProfileData();
        
        myClone.mProfileOrder = mProfileOrder;
        
        myClone.mProfileTypeCd = mProfileTypeCd;
        
        myClone.mProfileStatusCd = mProfileStatusCd;
        
        myClone.mProfileQuestionTypeCd = mProfileQuestionTypeCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mHelpText = mHelpText;
        
        myClone.mReadOnly = mReadOnly;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mImage = mImage;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ProfileDataAccess.PROFILE_ID.equals(pFieldName)) {
            return getProfileId();
        } else if (ProfileDataAccess.PROFILE_ORDER.equals(pFieldName)) {
            return getProfileOrder();
        } else if (ProfileDataAccess.PROFILE_TYPE_CD.equals(pFieldName)) {
            return getProfileTypeCd();
        } else if (ProfileDataAccess.PROFILE_STATUS_CD.equals(pFieldName)) {
            return getProfileStatusCd();
        } else if (ProfileDataAccess.PROFILE_QUESTION_TYPE_CD.equals(pFieldName)) {
            return getProfileQuestionTypeCd();
        } else if (ProfileDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ProfileDataAccess.HELP_TEXT.equals(pFieldName)) {
            return getHelpText();
        } else if (ProfileDataAccess.READ_ONLY.equals(pFieldName)) {
            return getReadOnly();
        } else if (ProfileDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProfileDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProfileDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProfileDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ProfileDataAccess.IMAGE.equals(pFieldName)) {
            return getImage();
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
        return ProfileDataAccess.CLW_PROFILE;
    }

    
    /**
     * Sets the ProfileId field. This field is required to be set in the database.
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
     * Sets the ProfileOrder field.
     *
     * @param pProfileOrder
     *  int to use to update the field.
     */
    public void setProfileOrder(int pProfileOrder){
        this.mProfileOrder = pProfileOrder;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileOrder field.
     *
     * @return
     *  int containing the ProfileOrder field.
     */
    public int getProfileOrder(){
        return mProfileOrder;
    }

    /**
     * Sets the ProfileTypeCd field.
     *
     * @param pProfileTypeCd
     *  String to use to update the field.
     */
    public void setProfileTypeCd(String pProfileTypeCd){
        this.mProfileTypeCd = pProfileTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileTypeCd field.
     *
     * @return
     *  String containing the ProfileTypeCd field.
     */
    public String getProfileTypeCd(){
        return mProfileTypeCd;
    }

    /**
     * Sets the ProfileStatusCd field.
     *
     * @param pProfileStatusCd
     *  String to use to update the field.
     */
    public void setProfileStatusCd(String pProfileStatusCd){
        this.mProfileStatusCd = pProfileStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileStatusCd field.
     *
     * @return
     *  String containing the ProfileStatusCd field.
     */
    public String getProfileStatusCd(){
        return mProfileStatusCd;
    }

    /**
     * Sets the ProfileQuestionTypeCd field.
     *
     * @param pProfileQuestionTypeCd
     *  String to use to update the field.
     */
    public void setProfileQuestionTypeCd(String pProfileQuestionTypeCd){
        this.mProfileQuestionTypeCd = pProfileQuestionTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileQuestionTypeCd field.
     *
     * @return
     *  String containing the ProfileQuestionTypeCd field.
     */
    public String getProfileQuestionTypeCd(){
        return mProfileQuestionTypeCd;
    }

    /**
     * Sets the ShortDesc field.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
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
     * Sets the ReadOnly field.
     *
     * @param pReadOnly
     *  String to use to update the field.
     */
    public void setReadOnly(String pReadOnly){
        this.mReadOnly = pReadOnly;
        setDirty(true);
    }
    /**
     * Retrieves the ReadOnly field.
     *
     * @return
     *  String containing the ReadOnly field.
     */
    public String getReadOnly(){
        return mReadOnly;
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

    /**
     * Sets the Image field.
     *
     * @param pImage
     *  String to use to update the field.
     */
    public void setImage(String pImage){
        this.mImage = pImage;
        setDirty(true);
    }
    /**
     * Retrieves the Image field.
     *
     * @return
     *  String containing the Image field.
     */
    public String getImage(){
        return mImage;
    }


}
