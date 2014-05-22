
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        GroupAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_GROUP_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.GroupAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>GroupAssocData</code> is a ValueObject class wrapping of the database table CLW_GROUP_ASSOC.
 */
public class GroupAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 2901748262522932089L;
    private int mGroupAssocId;// SQL type:NUMBER, not null
    private String mGroupAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mUserId;// SQL type:NUMBER
    private int mGroupId;// SQL type:NUMBER, not null
    private int mGenericReportId;// SQL type:NUMBER
    private String mApplicationFunction;// SQL type:VARCHAR2
    private int mBusEntityId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public GroupAssocData ()
    {
        mGroupAssocCd = "";
        mAddBy = "";
        mModBy = "";
        mApplicationFunction = "";
    }

    /**
     * Constructor.
     */
    public GroupAssocData(int parm1, String parm2, Date parm3, String parm4, Date parm5, String parm6, int parm7, int parm8, int parm9, String parm10, int parm11)
    {
        mGroupAssocId = parm1;
        mGroupAssocCd = parm2;
        mAddDate = parm3;
        mAddBy = parm4;
        mModDate = parm5;
        mModBy = parm6;
        mUserId = parm7;
        mGroupId = parm8;
        mGenericReportId = parm9;
        mApplicationFunction = parm10;
        mBusEntityId = parm11;
        
    }

    /**
     * Creates a new GroupAssocData
     *
     * @return
     *  Newly initialized GroupAssocData object.
     */
    public static GroupAssocData createValue ()
    {
        GroupAssocData valueData = new GroupAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this GroupAssocData object
     */
    public String toString()
    {
        return "[" + "GroupAssocId=" + mGroupAssocId + ", GroupAssocCd=" + mGroupAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", UserId=" + mUserId + ", GroupId=" + mGroupId + ", GenericReportId=" + mGenericReportId + ", ApplicationFunction=" + mApplicationFunction + ", BusEntityId=" + mBusEntityId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("GroupAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mGroupAssocId));

        node =  doc.createElement("GroupAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupAssocCd)));
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

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("GroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupId)));
        root.appendChild(node);

        node =  doc.createElement("GenericReportId");
        node.appendChild(doc.createTextNode(String.valueOf(mGenericReportId)));
        root.appendChild(node);

        node =  doc.createElement("ApplicationFunction");
        node.appendChild(doc.createTextNode(String.valueOf(mApplicationFunction)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the GroupAssocId field is not cloned.
    *
    * @return GroupAssocData object
    */
    public Object clone(){
        GroupAssocData myClone = new GroupAssocData();
        
        myClone.mGroupAssocCd = mGroupAssocCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mUserId = mUserId;
        
        myClone.mGroupId = mGroupId;
        
        myClone.mGenericReportId = mGenericReportId;
        
        myClone.mApplicationFunction = mApplicationFunction;
        
        myClone.mBusEntityId = mBusEntityId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (GroupAssocDataAccess.GROUP_ASSOC_ID.equals(pFieldName)) {
            return getGroupAssocId();
        } else if (GroupAssocDataAccess.GROUP_ASSOC_CD.equals(pFieldName)) {
            return getGroupAssocCd();
        } else if (GroupAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (GroupAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (GroupAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (GroupAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (GroupAssocDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (GroupAssocDataAccess.GROUP_ID.equals(pFieldName)) {
            return getGroupId();
        } else if (GroupAssocDataAccess.GENERIC_REPORT_ID.equals(pFieldName)) {
            return getGenericReportId();
        } else if (GroupAssocDataAccess.APPLICATION_FUNCTION.equals(pFieldName)) {
            return getApplicationFunction();
        } else if (GroupAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
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
        return GroupAssocDataAccess.CLW_GROUP_ASSOC;
    }

    
    /**
     * Sets the GroupAssocId field. This field is required to be set in the database.
     *
     * @param pGroupAssocId
     *  int to use to update the field.
     */
    public void setGroupAssocId(int pGroupAssocId){
        this.mGroupAssocId = pGroupAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the GroupAssocId field.
     *
     * @return
     *  int containing the GroupAssocId field.
     */
    public int getGroupAssocId(){
        return mGroupAssocId;
    }

    /**
     * Sets the GroupAssocCd field. This field is required to be set in the database.
     *
     * @param pGroupAssocCd
     *  String to use to update the field.
     */
    public void setGroupAssocCd(String pGroupAssocCd){
        this.mGroupAssocCd = pGroupAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the GroupAssocCd field.
     *
     * @return
     *  String containing the GroupAssocCd field.
     */
    public String getGroupAssocCd(){
        return mGroupAssocCd;
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
     * Sets the UserId field.
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
     * Sets the GenericReportId field.
     *
     * @param pGenericReportId
     *  int to use to update the field.
     */
    public void setGenericReportId(int pGenericReportId){
        this.mGenericReportId = pGenericReportId;
        setDirty(true);
    }
    /**
     * Retrieves the GenericReportId field.
     *
     * @return
     *  int containing the GenericReportId field.
     */
    public int getGenericReportId(){
        return mGenericReportId;
    }

    /**
     * Sets the ApplicationFunction field.
     *
     * @param pApplicationFunction
     *  String to use to update the field.
     */
    public void setApplicationFunction(String pApplicationFunction){
        this.mApplicationFunction = pApplicationFunction;
        setDirty(true);
    }
    /**
     * Retrieves the ApplicationFunction field.
     *
     * @return
     *  String containing the ApplicationFunction field.
     */
    public String getApplicationFunction(){
        return mApplicationFunction;
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


}
