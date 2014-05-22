
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContactData
 * Description:  This is a ValueObject class wrapping the database table CLW_CONTACT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ContactDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ContactData</code> is a ValueObject class wrapping of the database table CLW_CONTACT.
 */
public class ContactData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2871545506624038717L;
    private int mContactId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mFirstName;// SQL type:VARCHAR2
    private String mLastName;// SQL type:VARCHAR2
    private String mContactTypeCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mAddressId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public ContactData ()
    {
        mFirstName = "";
        mLastName = "";
        mContactTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ContactData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10)
    {
        mContactId = parm1;
        mBusEntityId = parm2;
        mFirstName = parm3;
        mLastName = parm4;
        mContactTypeCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mAddressId = parm10;
        
    }

    /**
     * Creates a new ContactData
     *
     * @return
     *  Newly initialized ContactData object.
     */
    public static ContactData createValue ()
    {
        ContactData valueData = new ContactData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContactData object
     */
    public String toString()
    {
        return "[" + "ContactId=" + mContactId + ", BusEntityId=" + mBusEntityId + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", ContactTypeCd=" + mContactTypeCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", AddressId=" + mAddressId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Contact");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mContactId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("FirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mFirstName)));
        root.appendChild(node);

        node =  doc.createElement("LastName");
        node.appendChild(doc.createTextNode(String.valueOf(mLastName)));
        root.appendChild(node);

        node =  doc.createElement("ContactTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mContactTypeCd)));
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

        node =  doc.createElement("AddressId");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ContactId field is not cloned.
    *
    * @return ContactData object
    */
    public Object clone(){
        ContactData myClone = new ContactData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mFirstName = mFirstName;
        
        myClone.mLastName = mLastName;
        
        myClone.mContactTypeCd = mContactTypeCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mAddressId = mAddressId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ContactDataAccess.CONTACT_ID.equals(pFieldName)) {
            return getContactId();
        } else if (ContactDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ContactDataAccess.FIRST_NAME.equals(pFieldName)) {
            return getFirstName();
        } else if (ContactDataAccess.LAST_NAME.equals(pFieldName)) {
            return getLastName();
        } else if (ContactDataAccess.CONTACT_TYPE_CD.equals(pFieldName)) {
            return getContactTypeCd();
        } else if (ContactDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ContactDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ContactDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ContactDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ContactDataAccess.ADDRESS_ID.equals(pFieldName)) {
            return getAddressId();
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
        return ContactDataAccess.CLW_CONTACT;
    }

    
    /**
     * Sets the ContactId field. This field is required to be set in the database.
     *
     * @param pContactId
     *  int to use to update the field.
     */
    public void setContactId(int pContactId){
        this.mContactId = pContactId;
        setDirty(true);
    }
    /**
     * Retrieves the ContactId field.
     *
     * @return
     *  int containing the ContactId field.
     */
    public int getContactId(){
        return mContactId;
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
     * Sets the FirstName field.
     *
     * @param pFirstName
     *  String to use to update the field.
     */
    public void setFirstName(String pFirstName){
        this.mFirstName = pFirstName;
        setDirty(true);
    }
    /**
     * Retrieves the FirstName field.
     *
     * @return
     *  String containing the FirstName field.
     */
    public String getFirstName(){
        return mFirstName;
    }

    /**
     * Sets the LastName field.
     *
     * @param pLastName
     *  String to use to update the field.
     */
    public void setLastName(String pLastName){
        this.mLastName = pLastName;
        setDirty(true);
    }
    /**
     * Retrieves the LastName field.
     *
     * @return
     *  String containing the LastName field.
     */
    public String getLastName(){
        return mLastName;
    }

    /**
     * Sets the ContactTypeCd field. This field is required to be set in the database.
     *
     * @param pContactTypeCd
     *  String to use to update the field.
     */
    public void setContactTypeCd(String pContactTypeCd){
        this.mContactTypeCd = pContactTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ContactTypeCd field.
     *
     * @return
     *  String containing the ContactTypeCd field.
     */
    public String getContactTypeCd(){
        return mContactTypeCd;
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
     * Sets the AddressId field.
     *
     * @param pAddressId
     *  int to use to update the field.
     */
    public void setAddressId(int pAddressId){
        this.mAddressId = pAddressId;
        setDirty(true);
    }
    /**
     * Retrieves the AddressId field.
     *
     * @return
     *  int containing the AddressId field.
     */
    public int getAddressId(){
        return mAddressId;
    }


}
