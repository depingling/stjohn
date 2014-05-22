
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_UI_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UiAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UiAssocData</code> is a ValueObject class wrapping of the database table CLW_UI_ASSOC.
 */
public class UiAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mUiAssocId;// SQL type:NUMBER, not null
    private int mUiId;// SQL type:NUMBER, not null
    private int mGroupId;// SQL type:NUMBER, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public UiAssocData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UiAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7)
    {
        mUiAssocId = parm1;
        mUiId = parm2;
        mGroupId = parm3;
        mAddBy = parm4;
        mAddDate = parm5;
        mModBy = parm6;
        mModDate = parm7;
        
    }

    /**
     * Creates a new UiAssocData
     *
     * @return
     *  Newly initialized UiAssocData object.
     */
    public static UiAssocData createValue ()
    {
        UiAssocData valueData = new UiAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiAssocData object
     */
    public String toString()
    {
        return "[" + "UiAssocId=" + mUiAssocId + ", UiId=" + mUiId + ", GroupId=" + mGroupId + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UiAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUiAssocId));

        node =  doc.createElement("UiId");
        node.appendChild(doc.createTextNode(String.valueOf(mUiId)));
        root.appendChild(node);

        node =  doc.createElement("GroupId");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupId)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UiAssocId field is not cloned.
    *
    * @return UiAssocData object
    */
    public Object clone(){
        UiAssocData myClone = new UiAssocData();
        
        myClone.mUiId = mUiId;
        
        myClone.mGroupId = mGroupId;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (UiAssocDataAccess.UI_ASSOC_ID.equals(pFieldName)) {
            return getUiAssocId();
        } else if (UiAssocDataAccess.UI_ID.equals(pFieldName)) {
            return getUiId();
        } else if (UiAssocDataAccess.GROUP_ID.equals(pFieldName)) {
            return getGroupId();
        } else if (UiAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UiAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UiAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UiAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return UiAssocDataAccess.CLW_UI_ASSOC;
    }

    
    /**
     * Sets the UiAssocId field. This field is required to be set in the database.
     *
     * @param pUiAssocId
     *  int to use to update the field.
     */
    public void setUiAssocId(int pUiAssocId){
        this.mUiAssocId = pUiAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the UiAssocId field.
     *
     * @return
     *  int containing the UiAssocId field.
     */
    public int getUiAssocId(){
        return mUiAssocId;
    }

    /**
     * Sets the UiId field. This field is required to be set in the database.
     *
     * @param pUiId
     *  int to use to update the field.
     */
    public void setUiId(int pUiId){
        this.mUiId = pUiId;
        setDirty(true);
    }
    /**
     * Retrieves the UiId field.
     *
     * @return
     *  int containing the UiId field.
     */
    public int getUiId(){
        return mUiId;
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


}
