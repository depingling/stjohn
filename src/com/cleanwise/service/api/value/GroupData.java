
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GroupData
 * Description:  This is a ValueObject class wrapping the database table CLW_GROUP.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.GroupDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>GroupData</code> is a ValueObject class wrapping of the database table CLW_GROUP.
 */
public class GroupData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1583253567190009012L;
    private int mGroupId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mGroupStatusCd;// SQL type:VARCHAR2, not null
    private String mGroupTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public GroupData ()
    {
        mShortDesc = "";
        mGroupStatusCd = "";
        mGroupTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public GroupData(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mGroupId = parm1;
        mShortDesc = parm2;
        mGroupStatusCd = parm3;
        mGroupTypeCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new GroupData
     *
     * @return
     *  Newly initialized GroupData object.
     */
    public static GroupData createValue ()
    {
        GroupData valueData = new GroupData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GroupData object
     */
    public String toString()
    {
        return "[" + "GroupId=" + mGroupId + ", ShortDesc=" + mShortDesc + ", GroupStatusCd=" + mGroupStatusCd + ", GroupTypeCd=" + mGroupTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Group");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mGroupId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("GroupStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("GroupTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupTypeCd)));
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
    * creates a clone of this object, the GroupId field is not cloned.
    *
    * @return GroupData object
    */
    public Object clone(){
        GroupData myClone = new GroupData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mGroupStatusCd = mGroupStatusCd;
        
        myClone.mGroupTypeCd = mGroupTypeCd;
        
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

        if (GroupDataAccess.GROUP_ID.equals(pFieldName)) {
            return getGroupId();
        } else if (GroupDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (GroupDataAccess.GROUP_STATUS_CD.equals(pFieldName)) {
            return getGroupStatusCd();
        } else if (GroupDataAccess.GROUP_TYPE_CD.equals(pFieldName)) {
            return getGroupTypeCd();
        } else if (GroupDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (GroupDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (GroupDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (GroupDataAccess.MOD_BY.equals(pFieldName)) {
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
        return GroupDataAccess.CLW_GROUP;
    }

    
    /**
     * Sets the GroupId field. This field is required to be set in the database.
     *
     * @param pGroupId
     *  int to use to update the field.
     */
    public void setGroupId(int pGroupId){
        this.mGroupId = pGroupId;
        setDirty(true);
    }
    /**
     * Retrieves the GroupId field.
     *
     * @return
     *  int containing the GroupId field.
     */
    public int getGroupId(){
        return mGroupId;
    }

    /**
     * Sets the ShortDesc field. This field is required to be set in the database.
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
     * Sets the GroupStatusCd field. This field is required to be set in the database.
     *
     * @param pGroupStatusCd
     *  String to use to update the field.
     */
    public void setGroupStatusCd(String pGroupStatusCd){
        this.mGroupStatusCd = pGroupStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the GroupStatusCd field.
     *
     * @return
     *  String containing the GroupStatusCd field.
     */
    public String getGroupStatusCd(){
        return mGroupStatusCd;
    }

    /**
     * Sets the GroupTypeCd field. This field is required to be set in the database.
     *
     * @param pGroupTypeCd
     *  String to use to update the field.
     */
    public void setGroupTypeCd(String pGroupTypeCd){
        this.mGroupTypeCd = pGroupTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the GroupTypeCd field.
     *
     * @return
     *  String containing the GroupTypeCd field.
     */
    public String getGroupTypeCd(){
        return mGroupTypeCd;
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


}
