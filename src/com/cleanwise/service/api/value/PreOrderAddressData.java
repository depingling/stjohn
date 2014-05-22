
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PreOrderAddressData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRE_ORDER_ADDRESS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PreOrderAddressDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PreOrderAddressData</code> is a ValueObject class wrapping of the database table CLW_PRE_ORDER_ADDRESS.
 */
public class PreOrderAddressData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6329781027823543596L;
    private int mPreOrderAddressId;// SQL type:NUMBER, not null
    private int mPreOrderId;// SQL type:NUMBER, not null
    private String mAddressTypeCd;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mAddress1;// SQL type:VARCHAR2
    private String mAddress2;// SQL type:VARCHAR2
    private String mAddress3;// SQL type:VARCHAR2
    private String mAddress4;// SQL type:VARCHAR2
    private String mCity;// SQL type:VARCHAR2
    private String mStateProvinceCd;// SQL type:VARCHAR2
    private String mCountryCd;// SQL type:VARCHAR2
    private String mCountyCd;// SQL type:VARCHAR2
    private String mPostalCode;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PreOrderAddressData ()
    {
        mAddressTypeCd = "";
        mShortDesc = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mStateProvinceCd = "";
        mCountryCd = "";
        mCountyCd = "";
        mPostalCode = "";
    }

    /**
     * Constructor.
     */
    public PreOrderAddressData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13)
    {
        mPreOrderAddressId = parm1;
        mPreOrderId = parm2;
        mAddressTypeCd = parm3;
        mShortDesc = parm4;
        mAddress1 = parm5;
        mAddress2 = parm6;
        mAddress3 = parm7;
        mAddress4 = parm8;
        mCity = parm9;
        mStateProvinceCd = parm10;
        mCountryCd = parm11;
        mCountyCd = parm12;
        mPostalCode = parm13;
        
    }

    /**
     * Creates a new PreOrderAddressData
     *
     * @return
     *  Newly initialized PreOrderAddressData object.
     */
    public static PreOrderAddressData createValue ()
    {
        PreOrderAddressData valueData = new PreOrderAddressData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PreOrderAddressData object
     */
    public String toString()
    {
        return "[" + "PreOrderAddressId=" + mPreOrderAddressId + ", PreOrderId=" + mPreOrderId + ", AddressTypeCd=" + mAddressTypeCd + ", ShortDesc=" + mShortDesc + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", CountryCd=" + mCountryCd + ", CountyCd=" + mCountyCd + ", PostalCode=" + mPostalCode + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PreOrderAddress");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPreOrderAddressId));

        node =  doc.createElement("PreOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPreOrderId)));
        root.appendChild(node);

        node =  doc.createElement("AddressTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Address1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress1)));
        root.appendChild(node);

        node =  doc.createElement("Address2");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress2)));
        root.appendChild(node);

        node =  doc.createElement("Address3");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress3)));
        root.appendChild(node);

        node =  doc.createElement("Address4");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress4)));
        root.appendChild(node);

        node =  doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node =  doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node =  doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        node =  doc.createElement("CountyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountyCd)));
        root.appendChild(node);

        node =  doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PreOrderAddressId field is not cloned.
    *
    * @return PreOrderAddressData object
    */
    public Object clone(){
        PreOrderAddressData myClone = new PreOrderAddressData();
        
        myClone.mPreOrderId = mPreOrderId;
        
        myClone.mAddressTypeCd = mAddressTypeCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mAddress1 = mAddress1;
        
        myClone.mAddress2 = mAddress2;
        
        myClone.mAddress3 = mAddress3;
        
        myClone.mAddress4 = mAddress4;
        
        myClone.mCity = mCity;
        
        myClone.mStateProvinceCd = mStateProvinceCd;
        
        myClone.mCountryCd = mCountryCd;
        
        myClone.mCountyCd = mCountyCd;
        
        myClone.mPostalCode = mPostalCode;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PreOrderAddressDataAccess.PRE_ORDER_ADDRESS_ID.equals(pFieldName)) {
            return getPreOrderAddressId();
        } else if (PreOrderAddressDataAccess.PRE_ORDER_ID.equals(pFieldName)) {
            return getPreOrderId();
        } else if (PreOrderAddressDataAccess.ADDRESS_TYPE_CD.equals(pFieldName)) {
            return getAddressTypeCd();
        } else if (PreOrderAddressDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PreOrderAddressDataAccess.ADDRESS1.equals(pFieldName)) {
            return getAddress1();
        } else if (PreOrderAddressDataAccess.ADDRESS2.equals(pFieldName)) {
            return getAddress2();
        } else if (PreOrderAddressDataAccess.ADDRESS3.equals(pFieldName)) {
            return getAddress3();
        } else if (PreOrderAddressDataAccess.ADDRESS4.equals(pFieldName)) {
            return getAddress4();
        } else if (PreOrderAddressDataAccess.CITY.equals(pFieldName)) {
            return getCity();
        } else if (PreOrderAddressDataAccess.STATE_PROVINCE_CD.equals(pFieldName)) {
            return getStateProvinceCd();
        } else if (PreOrderAddressDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (PreOrderAddressDataAccess.COUNTY_CD.equals(pFieldName)) {
            return getCountyCd();
        } else if (PreOrderAddressDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
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
        return PreOrderAddressDataAccess.CLW_PRE_ORDER_ADDRESS;
    }

    
    /**
     * Sets the PreOrderAddressId field. This field is required to be set in the database.
     *
     * @param pPreOrderAddressId
     *  int to use to update the field.
     */
    public void setPreOrderAddressId(int pPreOrderAddressId){
        this.mPreOrderAddressId = pPreOrderAddressId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderAddressId field.
     *
     * @return
     *  int containing the PreOrderAddressId field.
     */
    public int getPreOrderAddressId(){
        return mPreOrderAddressId;
    }

    /**
     * Sets the PreOrderId field. This field is required to be set in the database.
     *
     * @param pPreOrderId
     *  int to use to update the field.
     */
    public void setPreOrderId(int pPreOrderId){
        this.mPreOrderId = pPreOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderId field.
     *
     * @return
     *  int containing the PreOrderId field.
     */
    public int getPreOrderId(){
        return mPreOrderId;
    }

    /**
     * Sets the AddressTypeCd field.
     *
     * @param pAddressTypeCd
     *  String to use to update the field.
     */
    public void setAddressTypeCd(String pAddressTypeCd){
        this.mAddressTypeCd = pAddressTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the AddressTypeCd field.
     *
     * @return
     *  String containing the AddressTypeCd field.
     */
    public String getAddressTypeCd(){
        return mAddressTypeCd;
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
     * Sets the Address1 field.
     *
     * @param pAddress1
     *  String to use to update the field.
     */
    public void setAddress1(String pAddress1){
        this.mAddress1 = pAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the Address1 field.
     *
     * @return
     *  String containing the Address1 field.
     */
    public String getAddress1(){
        return mAddress1;
    }

    /**
     * Sets the Address2 field.
     *
     * @param pAddress2
     *  String to use to update the field.
     */
    public void setAddress2(String pAddress2){
        this.mAddress2 = pAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the Address2 field.
     *
     * @return
     *  String containing the Address2 field.
     */
    public String getAddress2(){
        return mAddress2;
    }

    /**
     * Sets the Address3 field.
     *
     * @param pAddress3
     *  String to use to update the field.
     */
    public void setAddress3(String pAddress3){
        this.mAddress3 = pAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the Address3 field.
     *
     * @return
     *  String containing the Address3 field.
     */
    public String getAddress3(){
        return mAddress3;
    }

    /**
     * Sets the Address4 field.
     *
     * @param pAddress4
     *  String to use to update the field.
     */
    public void setAddress4(String pAddress4){
        this.mAddress4 = pAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the Address4 field.
     *
     * @return
     *  String containing the Address4 field.
     */
    public String getAddress4(){
        return mAddress4;
    }

    /**
     * Sets the City field.
     *
     * @param pCity
     *  String to use to update the field.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
        setDirty(true);
    }
    /**
     * Retrieves the City field.
     *
     * @return
     *  String containing the City field.
     */
    public String getCity(){
        return mCity;
    }

    /**
     * Sets the StateProvinceCd field.
     *
     * @param pStateProvinceCd
     *  String to use to update the field.
     */
    public void setStateProvinceCd(String pStateProvinceCd){
        this.mStateProvinceCd = pStateProvinceCd;
        setDirty(true);
    }
    /**
     * Retrieves the StateProvinceCd field.
     *
     * @return
     *  String containing the StateProvinceCd field.
     */
    public String getStateProvinceCd(){
        return mStateProvinceCd;
    }

    /**
     * Sets the CountryCd field.
     *
     * @param pCountryCd
     *  String to use to update the field.
     */
    public void setCountryCd(String pCountryCd){
        this.mCountryCd = pCountryCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountryCd field.
     *
     * @return
     *  String containing the CountryCd field.
     */
    public String getCountryCd(){
        return mCountryCd;
    }

    /**
     * Sets the CountyCd field.
     *
     * @param pCountyCd
     *  String to use to update the field.
     */
    public void setCountyCd(String pCountyCd){
        this.mCountyCd = pCountyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountyCd field.
     *
     * @return
     *  String containing the CountyCd field.
     */
    public String getCountyCd(){
        return mCountyCd;
    }

    /**
     * Sets the PostalCode field.
     *
     * @param pPostalCode
     *  String to use to update the field.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the PostalCode field.
     *
     * @return
     *  String containing the PostalCode field.
     */
    public String getPostalCode(){
        return mPostalCode;
    }


}
