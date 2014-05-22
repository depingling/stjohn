
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ManifestItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_MANIFEST_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ManifestItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ManifestItemData</code> is a ValueObject class wrapping of the database table CLW_MANIFEST_ITEM.
 */
public class ManifestItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3811364391036505246L;
    private int mManifestItemId;// SQL type:NUMBER, not null
    private int mPurchaseOrderId;// SQL type:NUMBER
    private String mPackageId;// SQL type:VARCHAR2
    private String mPackageConfirmId;// SQL type:VARCHAR2
    private String mDistributionCenterId;// SQL type:VARCHAR2
    private String mManifestItemStatusCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mCubicSize;// SQL type:NUMBER
    private java.math.BigDecimal mWeight;// SQL type:NUMBER
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mRecievedPostalCd;// SQL type:VARCHAR2
    private String mRecievedZoneCd;// SQL type:VARCHAR2
    private String mRecievedActualWeight;// SQL type:VARCHAR2
    private String mRecievedPackageCategoryCd;// SQL type:VARCHAR2
    private String mRecievedPackageCost;// SQL type:VARCHAR2
    private String mManifestId;// SQL type:VARCHAR2
    private String mManifestLabelTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ManifestItemData ()
    {
        mPackageId = "";
        mPackageConfirmId = "";
        mDistributionCenterId = "";
        mManifestItemStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mRecievedPostalCd = "";
        mRecievedZoneCd = "";
        mRecievedActualWeight = "";
        mRecievedPackageCategoryCd = "";
        mRecievedPackageCost = "";
        mManifestId = "";
        mManifestLabelTypeCd = "";
    }

    /**
     * Constructor.
     */
    public ManifestItemData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, java.math.BigDecimal parm7, java.math.BigDecimal parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19)
    {
        mManifestItemId = parm1;
        mPurchaseOrderId = parm2;
        mPackageId = parm3;
        mPackageConfirmId = parm4;
        mDistributionCenterId = parm5;
        mManifestItemStatusCd = parm6;
        mCubicSize = parm7;
        mWeight = parm8;
        mAddBy = parm9;
        mAddDate = parm10;
        mModBy = parm11;
        mModDate = parm12;
        mRecievedPostalCd = parm13;
        mRecievedZoneCd = parm14;
        mRecievedActualWeight = parm15;
        mRecievedPackageCategoryCd = parm16;
        mRecievedPackageCost = parm17;
        mManifestId = parm18;
        mManifestLabelTypeCd = parm19;
        
    }

    /**
     * Creates a new ManifestItemData
     *
     * @return
     *  Newly initialized ManifestItemData object.
     */
    public static ManifestItemData createValue ()
    {
        ManifestItemData valueData = new ManifestItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ManifestItemData object
     */
    public String toString()
    {
        return "[" + "ManifestItemId=" + mManifestItemId + ", PurchaseOrderId=" + mPurchaseOrderId + ", PackageId=" + mPackageId + ", PackageConfirmId=" + mPackageConfirmId + ", DistributionCenterId=" + mDistributionCenterId + ", ManifestItemStatusCd=" + mManifestItemStatusCd + ", CubicSize=" + mCubicSize + ", Weight=" + mWeight + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", RecievedPostalCd=" + mRecievedPostalCd + ", RecievedZoneCd=" + mRecievedZoneCd + ", RecievedActualWeight=" + mRecievedActualWeight + ", RecievedPackageCategoryCd=" + mRecievedPackageCategoryCd + ", RecievedPackageCost=" + mRecievedPackageCost + ", ManifestId=" + mManifestId + ", ManifestLabelTypeCd=" + mManifestLabelTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ManifestItem");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mManifestItemId));

        node =  doc.createElement("PurchaseOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderId)));
        root.appendChild(node);

        node =  doc.createElement("PackageId");
        node.appendChild(doc.createTextNode(String.valueOf(mPackageId)));
        root.appendChild(node);

        node =  doc.createElement("PackageConfirmId");
        node.appendChild(doc.createTextNode(String.valueOf(mPackageConfirmId)));
        root.appendChild(node);

        node =  doc.createElement("DistributionCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributionCenterId)));
        root.appendChild(node);

        node =  doc.createElement("ManifestItemStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestItemStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("CubicSize");
        node.appendChild(doc.createTextNode(String.valueOf(mCubicSize)));
        root.appendChild(node);

        node =  doc.createElement("Weight");
        node.appendChild(doc.createTextNode(String.valueOf(mWeight)));
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

        node =  doc.createElement("RecievedPostalCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedPostalCd)));
        root.appendChild(node);

        node =  doc.createElement("RecievedZoneCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedZoneCd)));
        root.appendChild(node);

        node =  doc.createElement("RecievedActualWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedActualWeight)));
        root.appendChild(node);

        node =  doc.createElement("RecievedPackageCategoryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedPackageCategoryCd)));
        root.appendChild(node);

        node =  doc.createElement("RecievedPackageCost");
        node.appendChild(doc.createTextNode(String.valueOf(mRecievedPackageCost)));
        root.appendChild(node);

        node =  doc.createElement("ManifestId");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestId)));
        root.appendChild(node);

        node =  doc.createElement("ManifestLabelTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestLabelTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ManifestItemId field is not cloned.
    *
    * @return ManifestItemData object
    */
    public Object clone(){
        ManifestItemData myClone = new ManifestItemData();
        
        myClone.mPurchaseOrderId = mPurchaseOrderId;
        
        myClone.mPackageId = mPackageId;
        
        myClone.mPackageConfirmId = mPackageConfirmId;
        
        myClone.mDistributionCenterId = mDistributionCenterId;
        
        myClone.mManifestItemStatusCd = mManifestItemStatusCd;
        
        myClone.mCubicSize = mCubicSize;
        
        myClone.mWeight = mWeight;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mRecievedPostalCd = mRecievedPostalCd;
        
        myClone.mRecievedZoneCd = mRecievedZoneCd;
        
        myClone.mRecievedActualWeight = mRecievedActualWeight;
        
        myClone.mRecievedPackageCategoryCd = mRecievedPackageCategoryCd;
        
        myClone.mRecievedPackageCost = mRecievedPackageCost;
        
        myClone.mManifestId = mManifestId;
        
        myClone.mManifestLabelTypeCd = mManifestLabelTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ManifestItemDataAccess.MANIFEST_ITEM_ID.equals(pFieldName)) {
            return getManifestItemId();
        } else if (ManifestItemDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
        } else if (ManifestItemDataAccess.PACKAGE_ID.equals(pFieldName)) {
            return getPackageId();
        } else if (ManifestItemDataAccess.PACKAGE_CONFIRM_ID.equals(pFieldName)) {
            return getPackageConfirmId();
        } else if (ManifestItemDataAccess.DISTRIBUTION_CENTER_ID.equals(pFieldName)) {
            return getDistributionCenterId();
        } else if (ManifestItemDataAccess.MANIFEST_ITEM_STATUS_CD.equals(pFieldName)) {
            return getManifestItemStatusCd();
        } else if (ManifestItemDataAccess.CUBIC_SIZE.equals(pFieldName)) {
            return getCubicSize();
        } else if (ManifestItemDataAccess.WEIGHT.equals(pFieldName)) {
            return getWeight();
        } else if (ManifestItemDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ManifestItemDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ManifestItemDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ManifestItemDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ManifestItemDataAccess.RECIEVED_POSTAL_CD.equals(pFieldName)) {
            return getRecievedPostalCd();
        } else if (ManifestItemDataAccess.RECIEVED_ZONE_CD.equals(pFieldName)) {
            return getRecievedZoneCd();
        } else if (ManifestItemDataAccess.RECIEVED_ACTUAL_WEIGHT.equals(pFieldName)) {
            return getRecievedActualWeight();
        } else if (ManifestItemDataAccess.RECIEVED_PACKAGE_CATEGORY_CD.equals(pFieldName)) {
            return getRecievedPackageCategoryCd();
        } else if (ManifestItemDataAccess.RECIEVED_PACKAGE_COST.equals(pFieldName)) {
            return getRecievedPackageCost();
        } else if (ManifestItemDataAccess.MANIFEST_ID.equals(pFieldName)) {
            return getManifestId();
        } else if (ManifestItemDataAccess.MANIFEST_LABEL_TYPE_CD.equals(pFieldName)) {
            return getManifestLabelTypeCd();
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
        return ManifestItemDataAccess.CLW_MANIFEST_ITEM;
    }

    
    /**
     * Sets the ManifestItemId field. This field is required to be set in the database.
     *
     * @param pManifestItemId
     *  int to use to update the field.
     */
    public void setManifestItemId(int pManifestItemId){
        this.mManifestItemId = pManifestItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ManifestItemId field.
     *
     * @return
     *  int containing the ManifestItemId field.
     */
    public int getManifestItemId(){
        return mManifestItemId;
    }

    /**
     * Sets the PurchaseOrderId field.
     *
     * @param pPurchaseOrderId
     *  int to use to update the field.
     */
    public void setPurchaseOrderId(int pPurchaseOrderId){
        this.mPurchaseOrderId = pPurchaseOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderId field.
     *
     * @return
     *  int containing the PurchaseOrderId field.
     */
    public int getPurchaseOrderId(){
        return mPurchaseOrderId;
    }

    /**
     * Sets the PackageId field.
     *
     * @param pPackageId
     *  String to use to update the field.
     */
    public void setPackageId(String pPackageId){
        this.mPackageId = pPackageId;
        setDirty(true);
    }
    /**
     * Retrieves the PackageId field.
     *
     * @return
     *  String containing the PackageId field.
     */
    public String getPackageId(){
        return mPackageId;
    }

    /**
     * Sets the PackageConfirmId field.
     *
     * @param pPackageConfirmId
     *  String to use to update the field.
     */
    public void setPackageConfirmId(String pPackageConfirmId){
        this.mPackageConfirmId = pPackageConfirmId;
        setDirty(true);
    }
    /**
     * Retrieves the PackageConfirmId field.
     *
     * @return
     *  String containing the PackageConfirmId field.
     */
    public String getPackageConfirmId(){
        return mPackageConfirmId;
    }

    /**
     * Sets the DistributionCenterId field.
     *
     * @param pDistributionCenterId
     *  String to use to update the field.
     */
    public void setDistributionCenterId(String pDistributionCenterId){
        this.mDistributionCenterId = pDistributionCenterId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributionCenterId field.
     *
     * @return
     *  String containing the DistributionCenterId field.
     */
    public String getDistributionCenterId(){
        return mDistributionCenterId;
    }

    /**
     * Sets the ManifestItemStatusCd field.
     *
     * @param pManifestItemStatusCd
     *  String to use to update the field.
     */
    public void setManifestItemStatusCd(String pManifestItemStatusCd){
        this.mManifestItemStatusCd = pManifestItemStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ManifestItemStatusCd field.
     *
     * @return
     *  String containing the ManifestItemStatusCd field.
     */
    public String getManifestItemStatusCd(){
        return mManifestItemStatusCd;
    }

    /**
     * Sets the CubicSize field.
     *
     * @param pCubicSize
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCubicSize(java.math.BigDecimal pCubicSize){
        this.mCubicSize = pCubicSize;
        setDirty(true);
    }
    /**
     * Retrieves the CubicSize field.
     *
     * @return
     *  java.math.BigDecimal containing the CubicSize field.
     */
    public java.math.BigDecimal getCubicSize(){
        return mCubicSize;
    }

    /**
     * Sets the Weight field.
     *
     * @param pWeight
     *  java.math.BigDecimal to use to update the field.
     */
    public void setWeight(java.math.BigDecimal pWeight){
        this.mWeight = pWeight;
        setDirty(true);
    }
    /**
     * Retrieves the Weight field.
     *
     * @return
     *  java.math.BigDecimal containing the Weight field.
     */
    public java.math.BigDecimal getWeight(){
        return mWeight;
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
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
     * Sets the RecievedPostalCd field.
     *
     * @param pRecievedPostalCd
     *  String to use to update the field.
     */
    public void setRecievedPostalCd(String pRecievedPostalCd){
        this.mRecievedPostalCd = pRecievedPostalCd;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedPostalCd field.
     *
     * @return
     *  String containing the RecievedPostalCd field.
     */
    public String getRecievedPostalCd(){
        return mRecievedPostalCd;
    }

    /**
     * Sets the RecievedZoneCd field.
     *
     * @param pRecievedZoneCd
     *  String to use to update the field.
     */
    public void setRecievedZoneCd(String pRecievedZoneCd){
        this.mRecievedZoneCd = pRecievedZoneCd;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedZoneCd field.
     *
     * @return
     *  String containing the RecievedZoneCd field.
     */
    public String getRecievedZoneCd(){
        return mRecievedZoneCd;
    }

    /**
     * Sets the RecievedActualWeight field.
     *
     * @param pRecievedActualWeight
     *  String to use to update the field.
     */
    public void setRecievedActualWeight(String pRecievedActualWeight){
        this.mRecievedActualWeight = pRecievedActualWeight;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedActualWeight field.
     *
     * @return
     *  String containing the RecievedActualWeight field.
     */
    public String getRecievedActualWeight(){
        return mRecievedActualWeight;
    }

    /**
     * Sets the RecievedPackageCategoryCd field.
     *
     * @param pRecievedPackageCategoryCd
     *  String to use to update the field.
     */
    public void setRecievedPackageCategoryCd(String pRecievedPackageCategoryCd){
        this.mRecievedPackageCategoryCd = pRecievedPackageCategoryCd;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedPackageCategoryCd field.
     *
     * @return
     *  String containing the RecievedPackageCategoryCd field.
     */
    public String getRecievedPackageCategoryCd(){
        return mRecievedPackageCategoryCd;
    }

    /**
     * Sets the RecievedPackageCost field.
     *
     * @param pRecievedPackageCost
     *  String to use to update the field.
     */
    public void setRecievedPackageCost(String pRecievedPackageCost){
        this.mRecievedPackageCost = pRecievedPackageCost;
        setDirty(true);
    }
    /**
     * Retrieves the RecievedPackageCost field.
     *
     * @return
     *  String containing the RecievedPackageCost field.
     */
    public String getRecievedPackageCost(){
        return mRecievedPackageCost;
    }

    /**
     * Sets the ManifestId field.
     *
     * @param pManifestId
     *  String to use to update the field.
     */
    public void setManifestId(String pManifestId){
        this.mManifestId = pManifestId;
        setDirty(true);
    }
    /**
     * Retrieves the ManifestId field.
     *
     * @return
     *  String containing the ManifestId field.
     */
    public String getManifestId(){
        return mManifestId;
    }

    /**
     * Sets the ManifestLabelTypeCd field.
     *
     * @param pManifestLabelTypeCd
     *  String to use to update the field.
     */
    public void setManifestLabelTypeCd(String pManifestLabelTypeCd){
        this.mManifestLabelTypeCd = pManifestLabelTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ManifestLabelTypeCd field.
     *
     * @return
     *  String containing the ManifestLabelTypeCd field.
     */
    public String getManifestLabelTypeCd(){
        return mManifestLabelTypeCd;
    }


}
