
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProfileDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROFILE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProfileDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProfileDetailData</code> is a ValueObject class wrapping of the database table CLW_PROFILE_DETAIL.
 */
public class ProfileDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8044910995943505516L;
    private int mProfileDetailId;// SQL type:NUMBER, not null
    private int mProfileId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mProfileDetailStatusCd;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null
    private int mProfileDetailParentId;// SQL type:NUMBER
    private int mLoopValue;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public ProfileDetailData ()
    {
        mProfileDetailStatusCd = "";
        mShortDesc = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ProfileDetailData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, int parm11, int parm12)
    {
        mProfileDetailId = parm1;
        mProfileId = parm2;
        mBusEntityId = parm3;
        mProfileDetailStatusCd = parm4;
        mShortDesc = parm5;
        mValue = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        mProfileDetailParentId = parm11;
        mLoopValue = parm12;
        
    }

    /**
     * Creates a new ProfileDetailData
     *
     * @return
     *  Newly initialized ProfileDetailData object.
     */
    public static ProfileDetailData createValue ()
    {
        ProfileDetailData valueData = new ProfileDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProfileDetailData object
     */
    public String toString()
    {
        return "[" + "ProfileDetailId=" + mProfileDetailId + ", ProfileId=" + mProfileId + ", BusEntityId=" + mBusEntityId + ", ProfileDetailStatusCd=" + mProfileDetailStatusCd + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ProfileDetailParentId=" + mProfileDetailParentId + ", LoopValue=" + mLoopValue + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProfileDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProfileDetailId));

        node =  doc.createElement("ProfileId");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ProfileDetailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileDetailStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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

        node =  doc.createElement("ProfileDetailParentId");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileDetailParentId)));
        root.appendChild(node);

        node =  doc.createElement("LoopValue");
        node.appendChild(doc.createTextNode(String.valueOf(mLoopValue)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ProfileDetailId field is not cloned.
    *
    * @return ProfileDetailData object
    */
    public Object clone(){
        ProfileDetailData myClone = new ProfileDetailData();
        
        myClone.mProfileId = mProfileId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mProfileDetailStatusCd = mProfileDetailStatusCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mProfileDetailParentId = mProfileDetailParentId;
        
        myClone.mLoopValue = mLoopValue;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ProfileDetailDataAccess.PROFILE_DETAIL_ID.equals(pFieldName)) {
            return getProfileDetailId();
        } else if (ProfileDetailDataAccess.PROFILE_ID.equals(pFieldName)) {
            return getProfileId();
        } else if (ProfileDetailDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ProfileDetailDataAccess.PROFILE_DETAIL_STATUS_CD.equals(pFieldName)) {
            return getProfileDetailStatusCd();
        } else if (ProfileDetailDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ProfileDetailDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ProfileDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProfileDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProfileDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProfileDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ProfileDetailDataAccess.PROFILE_DETAIL_PARENT_ID.equals(pFieldName)) {
            return getProfileDetailParentId();
        } else if (ProfileDetailDataAccess.LOOP_VALUE.equals(pFieldName)) {
            return getLoopValue();
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
        return ProfileDetailDataAccess.CLW_PROFILE_DETAIL;
    }

    
    /**
     * Sets the ProfileDetailId field. This field is required to be set in the database.
     *
     * @param pProfileDetailId
     *  int to use to update the field.
     */
    public void setProfileDetailId(int pProfileDetailId){
        this.mProfileDetailId = pProfileDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileDetailId field.
     *
     * @return
     *  int containing the ProfileDetailId field.
     */
    public int getProfileDetailId(){
        return mProfileDetailId;
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
     * Sets the ProfileDetailStatusCd field.
     *
     * @param pProfileDetailStatusCd
     *  String to use to update the field.
     */
    public void setProfileDetailStatusCd(String pProfileDetailStatusCd){
        this.mProfileDetailStatusCd = pProfileDetailStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileDetailStatusCd field.
     *
     * @return
     *  String containing the ProfileDetailStatusCd field.
     */
    public String getProfileDetailStatusCd(){
        return mProfileDetailStatusCd;
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
     * Sets the ProfileDetailParentId field.
     *
     * @param pProfileDetailParentId
     *  int to use to update the field.
     */
    public void setProfileDetailParentId(int pProfileDetailParentId){
        this.mProfileDetailParentId = pProfileDetailParentId;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileDetailParentId field.
     *
     * @return
     *  int containing the ProfileDetailParentId field.
     */
    public int getProfileDetailParentId(){
        return mProfileDetailParentId;
    }

    /**
     * Sets the LoopValue field.
     *
     * @param pLoopValue
     *  int to use to update the field.
     */
    public void setLoopValue(int pLoopValue){
        this.mLoopValue = pLoopValue;
        setDirty(true);
    }
    /**
     * Retrieves the LoopValue field.
     *
     * @return
     *  int containing the LoopValue field.
     */
    public int getLoopValue(){
        return mLoopValue;
    }


}
