
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProfileAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_PROFILE_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ProfileAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ProfileAssocData</code> is a ValueObject class wrapping of the database table CLW_PROFILE_ASSOC.
 */
public class ProfileAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 7359162686577575037L;
    private int mProfileAssocId;// SQL type:NUMBER, not null
    private int mProfile1Id;// SQL type:NUMBER
    private int mProfile2Id;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mProfileAssocCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2, not null
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    public ProfileAssocData ()
    {
        mProfileAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ProfileAssocData(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mProfileAssocId = parm1;
        mProfile1Id = parm2;
        mProfile2Id = parm3;
        mBusEntityId = parm4;
        mProfileAssocCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new ProfileAssocData
     *
     * @return
     *  Newly initialized ProfileAssocData object.
     */
    public static ProfileAssocData createValue ()
    {
        ProfileAssocData valueData = new ProfileAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProfileAssocData object
     */
    public String toString()
    {
        return "[" + "ProfileAssocId=" + mProfileAssocId + ", Profile1Id=" + mProfile1Id + ", Profile2Id=" + mProfile2Id + ", BusEntityId=" + mBusEntityId + ", ProfileAssocCd=" + mProfileAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ProfileAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mProfileAssocId));

        node =  doc.createElement("Profile1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mProfile1Id)));
        root.appendChild(node);

        node =  doc.createElement("Profile2Id");
        node.appendChild(doc.createTextNode(String.valueOf(mProfile2Id)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ProfileAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileAssocCd)));
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
    * creates a clone of this object, the ProfileAssocId field is not cloned.
    *
    * @return ProfileAssocData object
    */
    public Object clone(){
        ProfileAssocData myClone = new ProfileAssocData();
        
        myClone.mProfile1Id = mProfile1Id;
        
        myClone.mProfile2Id = mProfile2Id;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mProfileAssocCd = mProfileAssocCd;
        
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

        if (ProfileAssocDataAccess.PROFILE_ASSOC_ID.equals(pFieldName)) {
            return getProfileAssocId();
        } else if (ProfileAssocDataAccess.PROFILE1_ID.equals(pFieldName)) {
            return getProfile1Id();
        } else if (ProfileAssocDataAccess.PROFILE2_ID.equals(pFieldName)) {
            return getProfile2Id();
        } else if (ProfileAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ProfileAssocDataAccess.PROFILE_ASSOC_CD.equals(pFieldName)) {
            return getProfileAssocCd();
        } else if (ProfileAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ProfileAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ProfileAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ProfileAssocDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ProfileAssocDataAccess.CLW_PROFILE_ASSOC;
    }

    
    /**
     * Sets the ProfileAssocId field. This field is required to be set in the database.
     *
     * @param pProfileAssocId
     *  int to use to update the field.
     */
    public void setProfileAssocId(int pProfileAssocId){
        this.mProfileAssocId = pProfileAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileAssocId field.
     *
     * @return
     *  int containing the ProfileAssocId field.
     */
    public int getProfileAssocId(){
        return mProfileAssocId;
    }

    /**
     * Sets the Profile1Id field.
     *
     * @param pProfile1Id
     *  int to use to update the field.
     */
    public void setProfile1Id(int pProfile1Id){
        this.mProfile1Id = pProfile1Id;
        setDirty(true);
    }
    /**
     * Retrieves the Profile1Id field.
     *
     * @return
     *  int containing the Profile1Id field.
     */
    public int getProfile1Id(){
        return mProfile1Id;
    }

    /**
     * Sets the Profile2Id field.
     *
     * @param pProfile2Id
     *  int to use to update the field.
     */
    public void setProfile2Id(int pProfile2Id){
        this.mProfile2Id = pProfile2Id;
        setDirty(true);
    }
    /**
     * Retrieves the Profile2Id field.
     *
     * @return
     *  int containing the Profile2Id field.
     */
    public int getProfile2Id(){
        return mProfile2Id;
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
     * Sets the ProfileAssocCd field.
     *
     * @param pProfileAssocCd
     *  String to use to update the field.
     */
    public void setProfileAssocCd(String pProfileAssocCd){
        this.mProfileAssocCd = pProfileAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the ProfileAssocCd field.
     *
     * @return
     *  String containing the ProfileAssocCd field.
     */
    public String getProfileAssocCd(){
        return mProfileAssocCd;
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
