
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        Fedstrip058Data
 * Description:  This is a ValueObject class wrapping the database table CLW_FEDSTRIP_058.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.Fedstrip058DataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>Fedstrip058Data</code> is a ValueObject class wrapping of the database table CLW_FEDSTRIP_058.
 */
public class Fedstrip058Data extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6624722199952820598L;
    private int mFedstrip058Id;// SQL type:NUMBER, not null
    private String mFedstrip;// SQL type:VARCHAR2, not null
    private String mFinance;// SQL type:VARCHAR2
    private String mBaCode;// SQL type:VARCHAR2
    private String mDistrictCode;// SQL type:VARCHAR2
    private String mCag;// SQL type:VARCHAR2
    private String mAddressTypeCode;// SQL type:VARCHAR2
    private String mAddressLine1;// SQL type:VARCHAR2
    private String mAddressLine2;// SQL type:VARCHAR2
    private String mAddressLine3;// SQL type:VARCHAR2
    private String mCity;// SQL type:VARCHAR2
    private String mState;// SQL type:VARCHAR2
    private String mZip;// SQL type:VARCHAR2
    private String mNmicsSiteCode;// SQL type:VARCHAR2
    private String mNmicsSubsiteCode;// SQL type:VARCHAR2
    private String mPartBuyAuthIndicator;// SQL type:VARCHAR2
    private String mPartsFinanceNumber;// SQL type:VARCHAR2
    private String mContactPhone;// SQL type:VARCHAR2
    private String mContactFax;// SQL type:VARCHAR2
    private String mPartsFacilityTypeCode;// SQL type:VARCHAR2
    private String mScfCode;// SQL type:VARCHAR2
    private String mCustomerName;// SQL type:VARCHAR2
    private String mDatedChanged;// SQL type:VARCHAR2
    private String mChangeCode;// SQL type:VARCHAR2
    private String mErrorCode;// SQL type:VARCHAR2
    private String mErrorMessage;// SQL type:VARCHAR2
    private String mFileName;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public Fedstrip058Data ()
    {
        mFedstrip = "";
        mFinance = "";
        mBaCode = "";
        mDistrictCode = "";
        mCag = "";
        mAddressTypeCode = "";
        mAddressLine1 = "";
        mAddressLine2 = "";
        mAddressLine3 = "";
        mCity = "";
        mState = "";
        mZip = "";
        mNmicsSiteCode = "";
        mNmicsSubsiteCode = "";
        mPartBuyAuthIndicator = "";
        mPartsFinanceNumber = "";
        mContactPhone = "";
        mContactFax = "";
        mPartsFacilityTypeCode = "";
        mScfCode = "";
        mCustomerName = "";
        mDatedChanged = "";
        mChangeCode = "";
        mErrorCode = "";
        mErrorMessage = "";
        mFileName = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public Fedstrip058Data(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, Date parm28, String parm29, Date parm30, String parm31)
    {
        mFedstrip058Id = parm1;
        mFedstrip = parm2;
        mFinance = parm3;
        mBaCode = parm4;
        mDistrictCode = parm5;
        mCag = parm6;
        mAddressTypeCode = parm7;
        mAddressLine1 = parm8;
        mAddressLine2 = parm9;
        mAddressLine3 = parm10;
        mCity = parm11;
        mState = parm12;
        mZip = parm13;
        mNmicsSiteCode = parm14;
        mNmicsSubsiteCode = parm15;
        mPartBuyAuthIndicator = parm16;
        mPartsFinanceNumber = parm17;
        mContactPhone = parm18;
        mContactFax = parm19;
        mPartsFacilityTypeCode = parm20;
        mScfCode = parm21;
        mCustomerName = parm22;
        mDatedChanged = parm23;
        mChangeCode = parm24;
        mErrorCode = parm25;
        mErrorMessage = parm26;
        mFileName = parm27;
        mAddDate = parm28;
        mAddBy = parm29;
        mModDate = parm30;
        mModBy = parm31;
        
    }

    /**
     * Creates a new Fedstrip058Data
     *
     * @return
     *  Newly initialized Fedstrip058Data object.
     */
    public static Fedstrip058Data createValue ()
    {
        Fedstrip058Data valueData = new Fedstrip058Data();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this Fedstrip058Data object
     */
    public String toString()
    {
        return "[" + "Fedstrip058Id=" + mFedstrip058Id + ", Fedstrip=" + mFedstrip + ", Finance=" + mFinance + ", BaCode=" + mBaCode + ", DistrictCode=" + mDistrictCode + ", Cag=" + mCag + ", AddressTypeCode=" + mAddressTypeCode + ", AddressLine1=" + mAddressLine1 + ", AddressLine2=" + mAddressLine2 + ", AddressLine3=" + mAddressLine3 + ", City=" + mCity + ", State=" + mState + ", Zip=" + mZip + ", NmicsSiteCode=" + mNmicsSiteCode + ", NmicsSubsiteCode=" + mNmicsSubsiteCode + ", PartBuyAuthIndicator=" + mPartBuyAuthIndicator + ", PartsFinanceNumber=" + mPartsFinanceNumber + ", ContactPhone=" + mContactPhone + ", ContactFax=" + mContactFax + ", PartsFacilityTypeCode=" + mPartsFacilityTypeCode + ", ScfCode=" + mScfCode + ", CustomerName=" + mCustomerName + ", DatedChanged=" + mDatedChanged + ", ChangeCode=" + mChangeCode + ", ErrorCode=" + mErrorCode + ", ErrorMessage=" + mErrorMessage + ", FileName=" + mFileName + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Fedstrip058");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mFedstrip058Id));

        node =  doc.createElement("Fedstrip");
        node.appendChild(doc.createTextNode(String.valueOf(mFedstrip)));
        root.appendChild(node);

        node =  doc.createElement("Finance");
        node.appendChild(doc.createTextNode(String.valueOf(mFinance)));
        root.appendChild(node);

        node =  doc.createElement("BaCode");
        node.appendChild(doc.createTextNode(String.valueOf(mBaCode)));
        root.appendChild(node);

        node =  doc.createElement("DistrictCode");
        node.appendChild(doc.createTextNode(String.valueOf(mDistrictCode)));
        root.appendChild(node);

        node =  doc.createElement("Cag");
        node.appendChild(doc.createTextNode(String.valueOf(mCag)));
        root.appendChild(node);

        node =  doc.createElement("AddressTypeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressTypeCode)));
        root.appendChild(node);

        node =  doc.createElement("AddressLine1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressLine1)));
        root.appendChild(node);

        node =  doc.createElement("AddressLine2");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressLine2)));
        root.appendChild(node);

        node =  doc.createElement("AddressLine3");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressLine3)));
        root.appendChild(node);

        node =  doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node =  doc.createElement("State");
        node.appendChild(doc.createTextNode(String.valueOf(mState)));
        root.appendChild(node);

        node =  doc.createElement("Zip");
        node.appendChild(doc.createTextNode(String.valueOf(mZip)));
        root.appendChild(node);

        node =  doc.createElement("NmicsSiteCode");
        node.appendChild(doc.createTextNode(String.valueOf(mNmicsSiteCode)));
        root.appendChild(node);

        node =  doc.createElement("NmicsSubsiteCode");
        node.appendChild(doc.createTextNode(String.valueOf(mNmicsSubsiteCode)));
        root.appendChild(node);

        node =  doc.createElement("PartBuyAuthIndicator");
        node.appendChild(doc.createTextNode(String.valueOf(mPartBuyAuthIndicator)));
        root.appendChild(node);

        node =  doc.createElement("PartsFinanceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPartsFinanceNumber)));
        root.appendChild(node);

        node =  doc.createElement("ContactPhone");
        node.appendChild(doc.createTextNode(String.valueOf(mContactPhone)));
        root.appendChild(node);

        node =  doc.createElement("ContactFax");
        node.appendChild(doc.createTextNode(String.valueOf(mContactFax)));
        root.appendChild(node);

        node =  doc.createElement("PartsFacilityTypeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPartsFacilityTypeCode)));
        root.appendChild(node);

        node =  doc.createElement("ScfCode");
        node.appendChild(doc.createTextNode(String.valueOf(mScfCode)));
        root.appendChild(node);

        node =  doc.createElement("CustomerName");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerName)));
        root.appendChild(node);

        node =  doc.createElement("DatedChanged");
        node.appendChild(doc.createTextNode(String.valueOf(mDatedChanged)));
        root.appendChild(node);

        node =  doc.createElement("ChangeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mChangeCode)));
        root.appendChild(node);

        node =  doc.createElement("ErrorCode");
        node.appendChild(doc.createTextNode(String.valueOf(mErrorCode)));
        root.appendChild(node);

        node =  doc.createElement("ErrorMessage");
        node.appendChild(doc.createTextNode(String.valueOf(mErrorMessage)));
        root.appendChild(node);

        node =  doc.createElement("FileName");
        node.appendChild(doc.createTextNode(String.valueOf(mFileName)));
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
    * creates a clone of this object, the Fedstrip058Id field is not cloned.
    *
    * @return Fedstrip058Data object
    */
    public Object clone(){
        Fedstrip058Data myClone = new Fedstrip058Data();
        
        myClone.mFedstrip = mFedstrip;
        
        myClone.mFinance = mFinance;
        
        myClone.mBaCode = mBaCode;
        
        myClone.mDistrictCode = mDistrictCode;
        
        myClone.mCag = mCag;
        
        myClone.mAddressTypeCode = mAddressTypeCode;
        
        myClone.mAddressLine1 = mAddressLine1;
        
        myClone.mAddressLine2 = mAddressLine2;
        
        myClone.mAddressLine3 = mAddressLine3;
        
        myClone.mCity = mCity;
        
        myClone.mState = mState;
        
        myClone.mZip = mZip;
        
        myClone.mNmicsSiteCode = mNmicsSiteCode;
        
        myClone.mNmicsSubsiteCode = mNmicsSubsiteCode;
        
        myClone.mPartBuyAuthIndicator = mPartBuyAuthIndicator;
        
        myClone.mPartsFinanceNumber = mPartsFinanceNumber;
        
        myClone.mContactPhone = mContactPhone;
        
        myClone.mContactFax = mContactFax;
        
        myClone.mPartsFacilityTypeCode = mPartsFacilityTypeCode;
        
        myClone.mScfCode = mScfCode;
        
        myClone.mCustomerName = mCustomerName;
        
        myClone.mDatedChanged = mDatedChanged;
        
        myClone.mChangeCode = mChangeCode;
        
        myClone.mErrorCode = mErrorCode;
        
        myClone.mErrorMessage = mErrorMessage;
        
        myClone.mFileName = mFileName;
        
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

        if (Fedstrip058DataAccess.FEDSTRIP_058_ID.equals(pFieldName)) {
            return getFedstrip058Id();
        } else if (Fedstrip058DataAccess.FEDSTRIP.equals(pFieldName)) {
            return getFedstrip();
        } else if (Fedstrip058DataAccess.FINANCE.equals(pFieldName)) {
            return getFinance();
        } else if (Fedstrip058DataAccess.BA_CODE.equals(pFieldName)) {
            return getBaCode();
        } else if (Fedstrip058DataAccess.DISTRICT_CODE.equals(pFieldName)) {
            return getDistrictCode();
        } else if (Fedstrip058DataAccess.CAG.equals(pFieldName)) {
            return getCag();
        } else if (Fedstrip058DataAccess.ADDRESS_TYPE_CODE.equals(pFieldName)) {
            return getAddressTypeCode();
        } else if (Fedstrip058DataAccess.ADDRESS_LINE1.equals(pFieldName)) {
            return getAddressLine1();
        } else if (Fedstrip058DataAccess.ADDRESS_LINE2.equals(pFieldName)) {
            return getAddressLine2();
        } else if (Fedstrip058DataAccess.ADDRESS_LINE3.equals(pFieldName)) {
            return getAddressLine3();
        } else if (Fedstrip058DataAccess.CITY.equals(pFieldName)) {
            return getCity();
        } else if (Fedstrip058DataAccess.STATE.equals(pFieldName)) {
            return getState();
        } else if (Fedstrip058DataAccess.ZIP.equals(pFieldName)) {
            return getZip();
        } else if (Fedstrip058DataAccess.NMICS_SITE_CODE.equals(pFieldName)) {
            return getNmicsSiteCode();
        } else if (Fedstrip058DataAccess.NMICS_SUBSITE_CODE.equals(pFieldName)) {
            return getNmicsSubsiteCode();
        } else if (Fedstrip058DataAccess.PART_BUY_AUTH_INDICATOR.equals(pFieldName)) {
            return getPartBuyAuthIndicator();
        } else if (Fedstrip058DataAccess.PARTS_FINANCE_NUMBER.equals(pFieldName)) {
            return getPartsFinanceNumber();
        } else if (Fedstrip058DataAccess.CONTACT_PHONE.equals(pFieldName)) {
            return getContactPhone();
        } else if (Fedstrip058DataAccess.CONTACT_FAX.equals(pFieldName)) {
            return getContactFax();
        } else if (Fedstrip058DataAccess.PARTS_FACILITY_TYPE_CODE.equals(pFieldName)) {
            return getPartsFacilityTypeCode();
        } else if (Fedstrip058DataAccess.SCF_CODE.equals(pFieldName)) {
            return getScfCode();
        } else if (Fedstrip058DataAccess.CUSTOMER_NAME.equals(pFieldName)) {
            return getCustomerName();
        } else if (Fedstrip058DataAccess.DATED_CHANGED.equals(pFieldName)) {
            return getDatedChanged();
        } else if (Fedstrip058DataAccess.CHANGE_CODE.equals(pFieldName)) {
            return getChangeCode();
        } else if (Fedstrip058DataAccess.ERROR_CODE.equals(pFieldName)) {
            return getErrorCode();
        } else if (Fedstrip058DataAccess.ERROR_MESSAGE.equals(pFieldName)) {
            return getErrorMessage();
        } else if (Fedstrip058DataAccess.FILE_NAME.equals(pFieldName)) {
            return getFileName();
        } else if (Fedstrip058DataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (Fedstrip058DataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (Fedstrip058DataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (Fedstrip058DataAccess.MOD_BY.equals(pFieldName)) {
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
        return Fedstrip058DataAccess.CLW_FEDSTRIP_058;
    }

    
    /**
     * Sets the Fedstrip058Id field. This field is required to be set in the database.
     *
     * @param pFedstrip058Id
     *  int to use to update the field.
     */
    public void setFedstrip058Id(int pFedstrip058Id){
        this.mFedstrip058Id = pFedstrip058Id;
        setDirty(true);
    }
    /**
     * Retrieves the Fedstrip058Id field.
     *
     * @return
     *  int containing the Fedstrip058Id field.
     */
    public int getFedstrip058Id(){
        return mFedstrip058Id;
    }

    /**
     * Sets the Fedstrip field. This field is required to be set in the database.
     *
     * @param pFedstrip
     *  String to use to update the field.
     */
    public void setFedstrip(String pFedstrip){
        this.mFedstrip = pFedstrip;
        setDirty(true);
    }
    /**
     * Retrieves the Fedstrip field.
     *
     * @return
     *  String containing the Fedstrip field.
     */
    public String getFedstrip(){
        return mFedstrip;
    }

    /**
     * Sets the Finance field.
     *
     * @param pFinance
     *  String to use to update the field.
     */
    public void setFinance(String pFinance){
        this.mFinance = pFinance;
        setDirty(true);
    }
    /**
     * Retrieves the Finance field.
     *
     * @return
     *  String containing the Finance field.
     */
    public String getFinance(){
        return mFinance;
    }

    /**
     * Sets the BaCode field.
     *
     * @param pBaCode
     *  String to use to update the field.
     */
    public void setBaCode(String pBaCode){
        this.mBaCode = pBaCode;
        setDirty(true);
    }
    /**
     * Retrieves the BaCode field.
     *
     * @return
     *  String containing the BaCode field.
     */
    public String getBaCode(){
        return mBaCode;
    }

    /**
     * Sets the DistrictCode field.
     *
     * @param pDistrictCode
     *  String to use to update the field.
     */
    public void setDistrictCode(String pDistrictCode){
        this.mDistrictCode = pDistrictCode;
        setDirty(true);
    }
    /**
     * Retrieves the DistrictCode field.
     *
     * @return
     *  String containing the DistrictCode field.
     */
    public String getDistrictCode(){
        return mDistrictCode;
    }

    /**
     * Sets the Cag field.
     *
     * @param pCag
     *  String to use to update the field.
     */
    public void setCag(String pCag){
        this.mCag = pCag;
        setDirty(true);
    }
    /**
     * Retrieves the Cag field.
     *
     * @return
     *  String containing the Cag field.
     */
    public String getCag(){
        return mCag;
    }

    /**
     * Sets the AddressTypeCode field.
     *
     * @param pAddressTypeCode
     *  String to use to update the field.
     */
    public void setAddressTypeCode(String pAddressTypeCode){
        this.mAddressTypeCode = pAddressTypeCode;
        setDirty(true);
    }
    /**
     * Retrieves the AddressTypeCode field.
     *
     * @return
     *  String containing the AddressTypeCode field.
     */
    public String getAddressTypeCode(){
        return mAddressTypeCode;
    }

    /**
     * Sets the AddressLine1 field.
     *
     * @param pAddressLine1
     *  String to use to update the field.
     */
    public void setAddressLine1(String pAddressLine1){
        this.mAddressLine1 = pAddressLine1;
        setDirty(true);
    }
    /**
     * Retrieves the AddressLine1 field.
     *
     * @return
     *  String containing the AddressLine1 field.
     */
    public String getAddressLine1(){
        return mAddressLine1;
    }

    /**
     * Sets the AddressLine2 field.
     *
     * @param pAddressLine2
     *  String to use to update the field.
     */
    public void setAddressLine2(String pAddressLine2){
        this.mAddressLine2 = pAddressLine2;
        setDirty(true);
    }
    /**
     * Retrieves the AddressLine2 field.
     *
     * @return
     *  String containing the AddressLine2 field.
     */
    public String getAddressLine2(){
        return mAddressLine2;
    }

    /**
     * Sets the AddressLine3 field.
     *
     * @param pAddressLine3
     *  String to use to update the field.
     */
    public void setAddressLine3(String pAddressLine3){
        this.mAddressLine3 = pAddressLine3;
        setDirty(true);
    }
    /**
     * Retrieves the AddressLine3 field.
     *
     * @return
     *  String containing the AddressLine3 field.
     */
    public String getAddressLine3(){
        return mAddressLine3;
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
     * Sets the State field.
     *
     * @param pState
     *  String to use to update the field.
     */
    public void setState(String pState){
        this.mState = pState;
        setDirty(true);
    }
    /**
     * Retrieves the State field.
     *
     * @return
     *  String containing the State field.
     */
    public String getState(){
        return mState;
    }

    /**
     * Sets the Zip field.
     *
     * @param pZip
     *  String to use to update the field.
     */
    public void setZip(String pZip){
        this.mZip = pZip;
        setDirty(true);
    }
    /**
     * Retrieves the Zip field.
     *
     * @return
     *  String containing the Zip field.
     */
    public String getZip(){
        return mZip;
    }

    /**
     * Sets the NmicsSiteCode field.
     *
     * @param pNmicsSiteCode
     *  String to use to update the field.
     */
    public void setNmicsSiteCode(String pNmicsSiteCode){
        this.mNmicsSiteCode = pNmicsSiteCode;
        setDirty(true);
    }
    /**
     * Retrieves the NmicsSiteCode field.
     *
     * @return
     *  String containing the NmicsSiteCode field.
     */
    public String getNmicsSiteCode(){
        return mNmicsSiteCode;
    }

    /**
     * Sets the NmicsSubsiteCode field.
     *
     * @param pNmicsSubsiteCode
     *  String to use to update the field.
     */
    public void setNmicsSubsiteCode(String pNmicsSubsiteCode){
        this.mNmicsSubsiteCode = pNmicsSubsiteCode;
        setDirty(true);
    }
    /**
     * Retrieves the NmicsSubsiteCode field.
     *
     * @return
     *  String containing the NmicsSubsiteCode field.
     */
    public String getNmicsSubsiteCode(){
        return mNmicsSubsiteCode;
    }

    /**
     * Sets the PartBuyAuthIndicator field.
     *
     * @param pPartBuyAuthIndicator
     *  String to use to update the field.
     */
    public void setPartBuyAuthIndicator(String pPartBuyAuthIndicator){
        this.mPartBuyAuthIndicator = pPartBuyAuthIndicator;
        setDirty(true);
    }
    /**
     * Retrieves the PartBuyAuthIndicator field.
     *
     * @return
     *  String containing the PartBuyAuthIndicator field.
     */
    public String getPartBuyAuthIndicator(){
        return mPartBuyAuthIndicator;
    }

    /**
     * Sets the PartsFinanceNumber field.
     *
     * @param pPartsFinanceNumber
     *  String to use to update the field.
     */
    public void setPartsFinanceNumber(String pPartsFinanceNumber){
        this.mPartsFinanceNumber = pPartsFinanceNumber;
        setDirty(true);
    }
    /**
     * Retrieves the PartsFinanceNumber field.
     *
     * @return
     *  String containing the PartsFinanceNumber field.
     */
    public String getPartsFinanceNumber(){
        return mPartsFinanceNumber;
    }

    /**
     * Sets the ContactPhone field.
     *
     * @param pContactPhone
     *  String to use to update the field.
     */
    public void setContactPhone(String pContactPhone){
        this.mContactPhone = pContactPhone;
        setDirty(true);
    }
    /**
     * Retrieves the ContactPhone field.
     *
     * @return
     *  String containing the ContactPhone field.
     */
    public String getContactPhone(){
        return mContactPhone;
    }

    /**
     * Sets the ContactFax field.
     *
     * @param pContactFax
     *  String to use to update the field.
     */
    public void setContactFax(String pContactFax){
        this.mContactFax = pContactFax;
        setDirty(true);
    }
    /**
     * Retrieves the ContactFax field.
     *
     * @return
     *  String containing the ContactFax field.
     */
    public String getContactFax(){
        return mContactFax;
    }

    /**
     * Sets the PartsFacilityTypeCode field.
     *
     * @param pPartsFacilityTypeCode
     *  String to use to update the field.
     */
    public void setPartsFacilityTypeCode(String pPartsFacilityTypeCode){
        this.mPartsFacilityTypeCode = pPartsFacilityTypeCode;
        setDirty(true);
    }
    /**
     * Retrieves the PartsFacilityTypeCode field.
     *
     * @return
     *  String containing the PartsFacilityTypeCode field.
     */
    public String getPartsFacilityTypeCode(){
        return mPartsFacilityTypeCode;
    }

    /**
     * Sets the ScfCode field.
     *
     * @param pScfCode
     *  String to use to update the field.
     */
    public void setScfCode(String pScfCode){
        this.mScfCode = pScfCode;
        setDirty(true);
    }
    /**
     * Retrieves the ScfCode field.
     *
     * @return
     *  String containing the ScfCode field.
     */
    public String getScfCode(){
        return mScfCode;
    }

    /**
     * Sets the CustomerName field.
     *
     * @param pCustomerName
     *  String to use to update the field.
     */
    public void setCustomerName(String pCustomerName){
        this.mCustomerName = pCustomerName;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerName field.
     *
     * @return
     *  String containing the CustomerName field.
     */
    public String getCustomerName(){
        return mCustomerName;
    }

    /**
     * Sets the DatedChanged field.
     *
     * @param pDatedChanged
     *  String to use to update the field.
     */
    public void setDatedChanged(String pDatedChanged){
        this.mDatedChanged = pDatedChanged;
        setDirty(true);
    }
    /**
     * Retrieves the DatedChanged field.
     *
     * @return
     *  String containing the DatedChanged field.
     */
    public String getDatedChanged(){
        return mDatedChanged;
    }

    /**
     * Sets the ChangeCode field.
     *
     * @param pChangeCode
     *  String to use to update the field.
     */
    public void setChangeCode(String pChangeCode){
        this.mChangeCode = pChangeCode;
        setDirty(true);
    }
    /**
     * Retrieves the ChangeCode field.
     *
     * @return
     *  String containing the ChangeCode field.
     */
    public String getChangeCode(){
        return mChangeCode;
    }

    /**
     * Sets the ErrorCode field.
     *
     * @param pErrorCode
     *  String to use to update the field.
     */
    public void setErrorCode(String pErrorCode){
        this.mErrorCode = pErrorCode;
        setDirty(true);
    }
    /**
     * Retrieves the ErrorCode field.
     *
     * @return
     *  String containing the ErrorCode field.
     */
    public String getErrorCode(){
        return mErrorCode;
    }

    /**
     * Sets the ErrorMessage field.
     *
     * @param pErrorMessage
     *  String to use to update the field.
     */
    public void setErrorMessage(String pErrorMessage){
        this.mErrorMessage = pErrorMessage;
        setDirty(true);
    }
    /**
     * Retrieves the ErrorMessage field.
     *
     * @return
     *  String containing the ErrorMessage field.
     */
    public String getErrorMessage(){
        return mErrorMessage;
    }

    /**
     * Sets the FileName field.
     *
     * @param pFileName
     *  String to use to update the field.
     */
    public void setFileName(String pFileName){
        this.mFileName = pFileName;
        setDirty(true);
    }
    /**
     * Retrieves the FileName field.
     *
     * @return
     *  String containing the FileName field.
     */
    public String getFileName(){
        return mFileName;
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
