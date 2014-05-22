
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PdManifestData
 * Description:  This is a ValueObject class wrapping the database table CLW_PD_MANIFEST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PdManifestDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PdManifestData</code> is a ValueObject class wrapping of the database table CLW_PD_MANIFEST.
 */
public class PdManifestData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -8315677759222136801L;
    private int mPdManifestId;// SQL type:NUMBER, not null
    private String mActualWeight;// SQL type:VARCHAR2
    private String mCustManifestId;// SQL type:VARCHAR2
    private String mCustPrclId;// SQL type:VARCHAR2
    private String mDestSrcCd;// SQL type:VARCHAR2
    private String mEstWeight;// SQL type:VARCHAR2
    private String mExpectedFlag;// SQL type:VARCHAR2
    private String mManifestGrpTxt;// SQL type:VARCHAR2
    private String mPhysicalFlag;// SQL type:VARCHAR2
    private String mPrclChrg;// SQL type:VARCHAR2
    private String mPrcsCatCd;// SQL type:VARCHAR2
    private String mSblCd;// SQL type:VARCHAR2
    private String mZoneCd;// SQL type:VARCHAR2
    private Date mEstShipDate;// SQL type:DATE
    private String mEstShipDateType;// SQL type:VARCHAR2
    private String mSrcDoc;// SQL type:VARCHAR2
    private int mPurchaseOrderId;// SQL type:NUMBER
    private int mMatchingRecords;// SQL type:NUMBER
    private String mMatchType;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private int mDistributorId;// SQL type:NUMBER
    private int mPurchaseOrderIdNonPd;// SQL type:NUMBER
    private int mMatchingRecordsNonPd;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PdManifestData ()
    {
        mActualWeight = "";
        mCustManifestId = "";
        mCustPrclId = "";
        mDestSrcCd = "";
        mEstWeight = "";
        mExpectedFlag = "";
        mManifestGrpTxt = "";
        mPhysicalFlag = "";
        mPrclChrg = "";
        mPrcsCatCd = "";
        mSblCd = "";
        mZoneCd = "";
        mEstShipDateType = "";
        mSrcDoc = "";
        mMatchType = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PdManifestData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, Date parm14, String parm15, String parm16, int parm17, int parm18, String parm19, Date parm20, String parm21, Date parm22, String parm23, int parm24, int parm25, int parm26)
    {
        mPdManifestId = parm1;
        mActualWeight = parm2;
        mCustManifestId = parm3;
        mCustPrclId = parm4;
        mDestSrcCd = parm5;
        mEstWeight = parm6;
        mExpectedFlag = parm7;
        mManifestGrpTxt = parm8;
        mPhysicalFlag = parm9;
        mPrclChrg = parm10;
        mPrcsCatCd = parm11;
        mSblCd = parm12;
        mZoneCd = parm13;
        mEstShipDate = parm14;
        mEstShipDateType = parm15;
        mSrcDoc = parm16;
        mPurchaseOrderId = parm17;
        mMatchingRecords = parm18;
        mMatchType = parm19;
        mAddDate = parm20;
        mAddBy = parm21;
        mModDate = parm22;
        mModBy = parm23;
        mDistributorId = parm24;
        mPurchaseOrderIdNonPd = parm25;
        mMatchingRecordsNonPd = parm26;
        
    }

    /**
     * Creates a new PdManifestData
     *
     * @return
     *  Newly initialized PdManifestData object.
     */
    public static PdManifestData createValue ()
    {
        PdManifestData valueData = new PdManifestData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PdManifestData object
     */
    public String toString()
    {
        return "[" + "PdManifestId=" + mPdManifestId + ", ActualWeight=" + mActualWeight + ", CustManifestId=" + mCustManifestId + ", CustPrclId=" + mCustPrclId + ", DestSrcCd=" + mDestSrcCd + ", EstWeight=" + mEstWeight + ", ExpectedFlag=" + mExpectedFlag + ", ManifestGrpTxt=" + mManifestGrpTxt + ", PhysicalFlag=" + mPhysicalFlag + ", PrclChrg=" + mPrclChrg + ", PrcsCatCd=" + mPrcsCatCd + ", SblCd=" + mSblCd + ", ZoneCd=" + mZoneCd + ", EstShipDate=" + mEstShipDate + ", EstShipDateType=" + mEstShipDateType + ", SrcDoc=" + mSrcDoc + ", PurchaseOrderId=" + mPurchaseOrderId + ", MatchingRecords=" + mMatchingRecords + ", MatchType=" + mMatchType + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", DistributorId=" + mDistributorId + ", PurchaseOrderIdNonPd=" + mPurchaseOrderIdNonPd + ", MatchingRecordsNonPd=" + mMatchingRecordsNonPd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PdManifest");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPdManifestId));

        node =  doc.createElement("ActualWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mActualWeight)));
        root.appendChild(node);

        node =  doc.createElement("CustManifestId");
        node.appendChild(doc.createTextNode(String.valueOf(mCustManifestId)));
        root.appendChild(node);

        node =  doc.createElement("CustPrclId");
        node.appendChild(doc.createTextNode(String.valueOf(mCustPrclId)));
        root.appendChild(node);

        node =  doc.createElement("DestSrcCd");
        node.appendChild(doc.createTextNode(String.valueOf(mDestSrcCd)));
        root.appendChild(node);

        node =  doc.createElement("EstWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mEstWeight)));
        root.appendChild(node);

        node =  doc.createElement("ExpectedFlag");
        node.appendChild(doc.createTextNode(String.valueOf(mExpectedFlag)));
        root.appendChild(node);

        node =  doc.createElement("ManifestGrpTxt");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestGrpTxt)));
        root.appendChild(node);

        node =  doc.createElement("PhysicalFlag");
        node.appendChild(doc.createTextNode(String.valueOf(mPhysicalFlag)));
        root.appendChild(node);

        node =  doc.createElement("PrclChrg");
        node.appendChild(doc.createTextNode(String.valueOf(mPrclChrg)));
        root.appendChild(node);

        node =  doc.createElement("PrcsCatCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPrcsCatCd)));
        root.appendChild(node);

        node =  doc.createElement("SblCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSblCd)));
        root.appendChild(node);

        node =  doc.createElement("ZoneCd");
        node.appendChild(doc.createTextNode(String.valueOf(mZoneCd)));
        root.appendChild(node);

        node =  doc.createElement("EstShipDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEstShipDate)));
        root.appendChild(node);

        node =  doc.createElement("EstShipDateType");
        node.appendChild(doc.createTextNode(String.valueOf(mEstShipDateType)));
        root.appendChild(node);

        node =  doc.createElement("SrcDoc");
        node.appendChild(doc.createTextNode(String.valueOf(mSrcDoc)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderId)));
        root.appendChild(node);

        node =  doc.createElement("MatchingRecords");
        node.appendChild(doc.createTextNode(String.valueOf(mMatchingRecords)));
        root.appendChild(node);

        node =  doc.createElement("MatchType");
        node.appendChild(doc.createTextNode(String.valueOf(mMatchType)));
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

        node =  doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderIdNonPd");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderIdNonPd)));
        root.appendChild(node);

        node =  doc.createElement("MatchingRecordsNonPd");
        node.appendChild(doc.createTextNode(String.valueOf(mMatchingRecordsNonPd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PdManifestId field is not cloned.
    *
    * @return PdManifestData object
    */
    public Object clone(){
        PdManifestData myClone = new PdManifestData();
        
        myClone.mActualWeight = mActualWeight;
        
        myClone.mCustManifestId = mCustManifestId;
        
        myClone.mCustPrclId = mCustPrclId;
        
        myClone.mDestSrcCd = mDestSrcCd;
        
        myClone.mEstWeight = mEstWeight;
        
        myClone.mExpectedFlag = mExpectedFlag;
        
        myClone.mManifestGrpTxt = mManifestGrpTxt;
        
        myClone.mPhysicalFlag = mPhysicalFlag;
        
        myClone.mPrclChrg = mPrclChrg;
        
        myClone.mPrcsCatCd = mPrcsCatCd;
        
        myClone.mSblCd = mSblCd;
        
        myClone.mZoneCd = mZoneCd;
        
        if(mEstShipDate != null){
                myClone.mEstShipDate = (Date) mEstShipDate.clone();
        }
        
        myClone.mEstShipDateType = mEstShipDateType;
        
        myClone.mSrcDoc = mSrcDoc;
        
        myClone.mPurchaseOrderId = mPurchaseOrderId;
        
        myClone.mMatchingRecords = mMatchingRecords;
        
        myClone.mMatchType = mMatchType;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mDistributorId = mDistributorId;
        
        myClone.mPurchaseOrderIdNonPd = mPurchaseOrderIdNonPd;
        
        myClone.mMatchingRecordsNonPd = mMatchingRecordsNonPd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PdManifestDataAccess.PD_MANIFEST_ID.equals(pFieldName)) {
            return getPdManifestId();
        } else if (PdManifestDataAccess.ACTUAL_WEIGHT.equals(pFieldName)) {
            return getActualWeight();
        } else if (PdManifestDataAccess.CUST_MANIFEST_ID.equals(pFieldName)) {
            return getCustManifestId();
        } else if (PdManifestDataAccess.CUST_PRCL_ID.equals(pFieldName)) {
            return getCustPrclId();
        } else if (PdManifestDataAccess.DEST_SRC_CD.equals(pFieldName)) {
            return getDestSrcCd();
        } else if (PdManifestDataAccess.EST_WEIGHT.equals(pFieldName)) {
            return getEstWeight();
        } else if (PdManifestDataAccess.EXPECTED_FLAG.equals(pFieldName)) {
            return getExpectedFlag();
        } else if (PdManifestDataAccess.MANIFEST_GRP_TXT.equals(pFieldName)) {
            return getManifestGrpTxt();
        } else if (PdManifestDataAccess.PHYSICAL_FLAG.equals(pFieldName)) {
            return getPhysicalFlag();
        } else if (PdManifestDataAccess.PRCL_CHRG.equals(pFieldName)) {
            return getPrclChrg();
        } else if (PdManifestDataAccess.PRCS_CAT_CD.equals(pFieldName)) {
            return getPrcsCatCd();
        } else if (PdManifestDataAccess.SBL_CD.equals(pFieldName)) {
            return getSblCd();
        } else if (PdManifestDataAccess.ZONE_CD.equals(pFieldName)) {
            return getZoneCd();
        } else if (PdManifestDataAccess.EST_SHIP_DATE.equals(pFieldName)) {
            return getEstShipDate();
        } else if (PdManifestDataAccess.EST_SHIP_DATE_TYPE.equals(pFieldName)) {
            return getEstShipDateType();
        } else if (PdManifestDataAccess.SRC_DOC.equals(pFieldName)) {
            return getSrcDoc();
        } else if (PdManifestDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
        } else if (PdManifestDataAccess.MATCHING_RECORDS.equals(pFieldName)) {
            return getMatchingRecords();
        } else if (PdManifestDataAccess.MATCH_TYPE.equals(pFieldName)) {
            return getMatchType();
        } else if (PdManifestDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PdManifestDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PdManifestDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PdManifestDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PdManifestDataAccess.DISTRIBUTOR_ID.equals(pFieldName)) {
            return getDistributorId();
        } else if (PdManifestDataAccess.PURCHASE_ORDER_ID_NON_PD.equals(pFieldName)) {
            return getPurchaseOrderIdNonPd();
        } else if (PdManifestDataAccess.MATCHING_RECORDS_NON_PD.equals(pFieldName)) {
            return getMatchingRecordsNonPd();
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
        return PdManifestDataAccess.CLW_PD_MANIFEST;
    }

    
    /**
     * Sets the PdManifestId field. This field is required to be set in the database.
     *
     * @param pPdManifestId
     *  int to use to update the field.
     */
    public void setPdManifestId(int pPdManifestId){
        this.mPdManifestId = pPdManifestId;
        setDirty(true);
    }
    /**
     * Retrieves the PdManifestId field.
     *
     * @return
     *  int containing the PdManifestId field.
     */
    public int getPdManifestId(){
        return mPdManifestId;
    }

    /**
     * Sets the ActualWeight field.
     *
     * @param pActualWeight
     *  String to use to update the field.
     */
    public void setActualWeight(String pActualWeight){
        this.mActualWeight = pActualWeight;
        setDirty(true);
    }
    /**
     * Retrieves the ActualWeight field.
     *
     * @return
     *  String containing the ActualWeight field.
     */
    public String getActualWeight(){
        return mActualWeight;
    }

    /**
     * Sets the CustManifestId field.
     *
     * @param pCustManifestId
     *  String to use to update the field.
     */
    public void setCustManifestId(String pCustManifestId){
        this.mCustManifestId = pCustManifestId;
        setDirty(true);
    }
    /**
     * Retrieves the CustManifestId field.
     *
     * @return
     *  String containing the CustManifestId field.
     */
    public String getCustManifestId(){
        return mCustManifestId;
    }

    /**
     * Sets the CustPrclId field.
     *
     * @param pCustPrclId
     *  String to use to update the field.
     */
    public void setCustPrclId(String pCustPrclId){
        this.mCustPrclId = pCustPrclId;
        setDirty(true);
    }
    /**
     * Retrieves the CustPrclId field.
     *
     * @return
     *  String containing the CustPrclId field.
     */
    public String getCustPrclId(){
        return mCustPrclId;
    }

    /**
     * Sets the DestSrcCd field.
     *
     * @param pDestSrcCd
     *  String to use to update the field.
     */
    public void setDestSrcCd(String pDestSrcCd){
        this.mDestSrcCd = pDestSrcCd;
        setDirty(true);
    }
    /**
     * Retrieves the DestSrcCd field.
     *
     * @return
     *  String containing the DestSrcCd field.
     */
    public String getDestSrcCd(){
        return mDestSrcCd;
    }

    /**
     * Sets the EstWeight field.
     *
     * @param pEstWeight
     *  String to use to update the field.
     */
    public void setEstWeight(String pEstWeight){
        this.mEstWeight = pEstWeight;
        setDirty(true);
    }
    /**
     * Retrieves the EstWeight field.
     *
     * @return
     *  String containing the EstWeight field.
     */
    public String getEstWeight(){
        return mEstWeight;
    }

    /**
     * Sets the ExpectedFlag field.
     *
     * @param pExpectedFlag
     *  String to use to update the field.
     */
    public void setExpectedFlag(String pExpectedFlag){
        this.mExpectedFlag = pExpectedFlag;
        setDirty(true);
    }
    /**
     * Retrieves the ExpectedFlag field.
     *
     * @return
     *  String containing the ExpectedFlag field.
     */
    public String getExpectedFlag(){
        return mExpectedFlag;
    }

    /**
     * Sets the ManifestGrpTxt field.
     *
     * @param pManifestGrpTxt
     *  String to use to update the field.
     */
    public void setManifestGrpTxt(String pManifestGrpTxt){
        this.mManifestGrpTxt = pManifestGrpTxt;
        setDirty(true);
    }
    /**
     * Retrieves the ManifestGrpTxt field.
     *
     * @return
     *  String containing the ManifestGrpTxt field.
     */
    public String getManifestGrpTxt(){
        return mManifestGrpTxt;
    }

    /**
     * Sets the PhysicalFlag field.
     *
     * @param pPhysicalFlag
     *  String to use to update the field.
     */
    public void setPhysicalFlag(String pPhysicalFlag){
        this.mPhysicalFlag = pPhysicalFlag;
        setDirty(true);
    }
    /**
     * Retrieves the PhysicalFlag field.
     *
     * @return
     *  String containing the PhysicalFlag field.
     */
    public String getPhysicalFlag(){
        return mPhysicalFlag;
    }

    /**
     * Sets the PrclChrg field.
     *
     * @param pPrclChrg
     *  String to use to update the field.
     */
    public void setPrclChrg(String pPrclChrg){
        this.mPrclChrg = pPrclChrg;
        setDirty(true);
    }
    /**
     * Retrieves the PrclChrg field.
     *
     * @return
     *  String containing the PrclChrg field.
     */
    public String getPrclChrg(){
        return mPrclChrg;
    }

    /**
     * Sets the PrcsCatCd field.
     *
     * @param pPrcsCatCd
     *  String to use to update the field.
     */
    public void setPrcsCatCd(String pPrcsCatCd){
        this.mPrcsCatCd = pPrcsCatCd;
        setDirty(true);
    }
    /**
     * Retrieves the PrcsCatCd field.
     *
     * @return
     *  String containing the PrcsCatCd field.
     */
    public String getPrcsCatCd(){
        return mPrcsCatCd;
    }

    /**
     * Sets the SblCd field.
     *
     * @param pSblCd
     *  String to use to update the field.
     */
    public void setSblCd(String pSblCd){
        this.mSblCd = pSblCd;
        setDirty(true);
    }
    /**
     * Retrieves the SblCd field.
     *
     * @return
     *  String containing the SblCd field.
     */
    public String getSblCd(){
        return mSblCd;
    }

    /**
     * Sets the ZoneCd field.
     *
     * @param pZoneCd
     *  String to use to update the field.
     */
    public void setZoneCd(String pZoneCd){
        this.mZoneCd = pZoneCd;
        setDirty(true);
    }
    /**
     * Retrieves the ZoneCd field.
     *
     * @return
     *  String containing the ZoneCd field.
     */
    public String getZoneCd(){
        return mZoneCd;
    }

    /**
     * Sets the EstShipDate field.
     *
     * @param pEstShipDate
     *  Date to use to update the field.
     */
    public void setEstShipDate(Date pEstShipDate){
        this.mEstShipDate = pEstShipDate;
        setDirty(true);
    }
    /**
     * Retrieves the EstShipDate field.
     *
     * @return
     *  Date containing the EstShipDate field.
     */
    public Date getEstShipDate(){
        return mEstShipDate;
    }

    /**
     * Sets the EstShipDateType field.
     *
     * @param pEstShipDateType
     *  String to use to update the field.
     */
    public void setEstShipDateType(String pEstShipDateType){
        this.mEstShipDateType = pEstShipDateType;
        setDirty(true);
    }
    /**
     * Retrieves the EstShipDateType field.
     *
     * @return
     *  String containing the EstShipDateType field.
     */
    public String getEstShipDateType(){
        return mEstShipDateType;
    }

    /**
     * Sets the SrcDoc field.
     *
     * @param pSrcDoc
     *  String to use to update the field.
     */
    public void setSrcDoc(String pSrcDoc){
        this.mSrcDoc = pSrcDoc;
        setDirty(true);
    }
    /**
     * Retrieves the SrcDoc field.
     *
     * @return
     *  String containing the SrcDoc field.
     */
    public String getSrcDoc(){
        return mSrcDoc;
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
     * Sets the MatchingRecords field.
     *
     * @param pMatchingRecords
     *  int to use to update the field.
     */
    public void setMatchingRecords(int pMatchingRecords){
        this.mMatchingRecords = pMatchingRecords;
        setDirty(true);
    }
    /**
     * Retrieves the MatchingRecords field.
     *
     * @return
     *  int containing the MatchingRecords field.
     */
    public int getMatchingRecords(){
        return mMatchingRecords;
    }

    /**
     * Sets the MatchType field.
     *
     * @param pMatchType
     *  String to use to update the field.
     */
    public void setMatchType(String pMatchType){
        this.mMatchType = pMatchType;
        setDirty(true);
    }
    /**
     * Retrieves the MatchType field.
     *
     * @return
     *  String containing the MatchType field.
     */
    public String getMatchType(){
        return mMatchType;
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
     * Sets the DistributorId field.
     *
     * @param pDistributorId
     *  int to use to update the field.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorId field.
     *
     * @return
     *  int containing the DistributorId field.
     */
    public int getDistributorId(){
        return mDistributorId;
    }

    /**
     * Sets the PurchaseOrderIdNonPd field.
     *
     * @param pPurchaseOrderIdNonPd
     *  int to use to update the field.
     */
    public void setPurchaseOrderIdNonPd(int pPurchaseOrderIdNonPd){
        this.mPurchaseOrderIdNonPd = pPurchaseOrderIdNonPd;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderIdNonPd field.
     *
     * @return
     *  int containing the PurchaseOrderIdNonPd field.
     */
    public int getPurchaseOrderIdNonPd(){
        return mPurchaseOrderIdNonPd;
    }

    /**
     * Sets the MatchingRecordsNonPd field.
     *
     * @param pMatchingRecordsNonPd
     *  int to use to update the field.
     */
    public void setMatchingRecordsNonPd(int pMatchingRecordsNonPd){
        this.mMatchingRecordsNonPd = pMatchingRecordsNonPd;
        setDirty(true);
    }
    /**
     * Retrieves the MatchingRecordsNonPd field.
     *
     * @return
     *  int containing the MatchingRecordsNonPd field.
     */
    public int getMatchingRecordsNonPd(){
        return mMatchingRecordsNonPd;
    }


}
