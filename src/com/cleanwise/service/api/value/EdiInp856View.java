
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EdiInp856View
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;

import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;
/**
 * <code>EdiInp856View</code> is a ViewObject class for UI.
 */
public class EdiInp856View
extends ValueObject
{
   
    private static final long serialVersionUID = -1808810488802661542L;
    private String mPurposeCode;
    private String mTransactionIdentifier;
    private Date mTransactionDate;
    private String mRefIdentQualif;
    private String mRefIdent;
    private Date mShipDate;
    private String mDistName;
    private String mDistIdentCodeQualif;
    private String mDistIdentCode;
    private String mShipFromCity;
    private String mShipFromState;
    private String mShipFromPostalCode;
    private String mPurchOrderNum;
    private String mDistOrderNum;
    private String mShipToName;
    private String mShipToCode;
    private String mTrackingNum;
    private String mCarrierCode;
    private String mCarrierName;
    private EdiInp856ItemViewVector mItems;
    private int mOrderId;
    private String mUpdateOrderItemActions;
    private int mGroupControlNum;
    private int mInterchangeControlNum;
    private String matchPoNumType = RefCodeNames.MATCH_PO_NUM_TYPE_CD.DEFAULT;    
    
    /**
     * Constructor.
     */
    public EdiInp856View ()
    {
        mPurposeCode = "";
        mTransactionIdentifier = "";
        mRefIdentQualif = "";
        mRefIdent = "";
        mDistName = "";
        mDistIdentCodeQualif = "";
        mDistIdentCode = "";
        mShipFromCity = "";
        mShipFromState = "";
        mShipFromPostalCode = "";
        mPurchOrderNum = "";
        mDistOrderNum = "";
        mShipToName = "";
        mShipToCode = "";
        mTrackingNum = "";
        mCarrierCode = "";
        mCarrierName = "";
        mUpdateOrderItemActions = "";
    }

    /**
     * Constructor. 
     */
    public EdiInp856View(String parm1, String parm2, Date parm3, String parm4, String parm5, Date parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, EdiInp856ItemViewVector parm20, int parm21, String parm22, int parm23, int parm24)
    {
        mPurposeCode = parm1;
        mTransactionIdentifier = parm2;
        mTransactionDate = parm3;
        mRefIdentQualif = parm4;
        mRefIdent = parm5;
        mShipDate = parm6;
        mDistName = parm7;
        mDistIdentCodeQualif = parm8;
        mDistIdentCode = parm9;
        mShipFromCity = parm10;
        mShipFromState = parm11;
        mShipFromPostalCode = parm12;
        mPurchOrderNum = parm13;
        mDistOrderNum = parm14;
        mShipToName = parm15;
        mShipToCode = parm16;
        mTrackingNum = parm17;
        mCarrierCode = parm18;
        mCarrierName = parm19;
        mItems = parm20;
        mOrderId = parm21;
        mUpdateOrderItemActions = parm22;
        mGroupControlNum = parm23;
        mInterchangeControlNum = parm24;
        
    }

    /**
     * Creates a new EdiInp856View
     *
     * @return
     *  Newly initialized EdiInp856View object.
     */
    public static EdiInp856View createValue () 
    {
        EdiInp856View valueView = new EdiInp856View();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EdiInp856View object
     */
    public String toString()
    {
        return "[" + "PurposeCode=" + mPurposeCode + ", TransactionIdentifier=" + mTransactionIdentifier + ", TransactionDate=" + mTransactionDate + ", RefIdentQualif=" + mRefIdentQualif + ", RefIdent=" + mRefIdent + ", ShipDate=" + mShipDate + ", DistName=" + mDistName + ", DistIdentCodeQualif=" + mDistIdentCodeQualif + ", DistIdentCode=" + mDistIdentCode + ", ShipFromCity=" + mShipFromCity + ", ShipFromState=" + mShipFromState + ", ShipFromPostalCode=" + mShipFromPostalCode + ", PurchOrderNum=" + mPurchOrderNum + ", DistOrderNum=" + mDistOrderNum + ", ShipToName=" + mShipToName + ", ShipToCode=" + mShipToCode + ", TrackingNum=" + mTrackingNum + ", CarrierCode=" + mCarrierCode + ", CarrierName=" + mCarrierName + ", Items=" + mItems + ", OrderId=" + mOrderId + ", UpdateOrderItemActions=" + mUpdateOrderItemActions + ", GroupControlNum=" + mGroupControlNum + ", InterchangeControlNum=" + mInterchangeControlNum + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EdiInp856");
	root.setAttribute("Id", String.valueOf(mPurposeCode));

	Element node;

        node = doc.createElement("TransactionIdentifier");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionIdentifier)));
        root.appendChild(node);

        node = doc.createElement("TransactionDate");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionDate)));
        root.appendChild(node);

        node = doc.createElement("RefIdentQualif");
        node.appendChild(doc.createTextNode(String.valueOf(mRefIdentQualif)));
        root.appendChild(node);

        node = doc.createElement("RefIdent");
        node.appendChild(doc.createTextNode(String.valueOf(mRefIdent)));
        root.appendChild(node);

        node = doc.createElement("ShipDate");
        node.appendChild(doc.createTextNode(String.valueOf(mShipDate)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("DistIdentCodeQualif");
        node.appendChild(doc.createTextNode(String.valueOf(mDistIdentCodeQualif)));
        root.appendChild(node);

        node = doc.createElement("DistIdentCode");
        node.appendChild(doc.createTextNode(String.valueOf(mDistIdentCode)));
        root.appendChild(node);

        node = doc.createElement("ShipFromCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromCity)));
        root.appendChild(node);

        node = doc.createElement("ShipFromState");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromState)));
        root.appendChild(node);

        node = doc.createElement("ShipFromPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromPostalCode)));
        root.appendChild(node);

        node = doc.createElement("PurchOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchOrderNum)));
        root.appendChild(node);

        node = doc.createElement("DistOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistOrderNum)));
        root.appendChild(node);

        node = doc.createElement("ShipToName");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToName)));
        root.appendChild(node);

        node = doc.createElement("ShipToCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToCode)));
        root.appendChild(node);

        node = doc.createElement("TrackingNum");
        node.appendChild(doc.createTextNode(String.valueOf(mTrackingNum)));
        root.appendChild(node);

        node = doc.createElement("CarrierCode");
        node.appendChild(doc.createTextNode(String.valueOf(mCarrierCode)));
        root.appendChild(node);

        node = doc.createElement("CarrierName");
        node.appendChild(doc.createTextNode(String.valueOf(mCarrierName)));
        root.appendChild(node);

        node = doc.createElement("Items");
        node.appendChild(doc.createTextNode(String.valueOf(mItems)));
        root.appendChild(node);

        node = doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node = doc.createElement("UpdateOrderItemActions");
        node.appendChild(doc.createTextNode(String.valueOf(mUpdateOrderItemActions)));
        root.appendChild(node);

        node = doc.createElement("GroupControlNum");
        node.appendChild(doc.createTextNode(String.valueOf(mGroupControlNum)));
        root.appendChild(node);

        node = doc.createElement("InterchangeControlNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInterchangeControlNum)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EdiInp856View copy()  {
      EdiInp856View obj = new EdiInp856View();
      obj.setPurposeCode(mPurposeCode);
      obj.setTransactionIdentifier(mTransactionIdentifier);
      obj.setTransactionDate(mTransactionDate);
      obj.setRefIdentQualif(mRefIdentQualif);
      obj.setRefIdent(mRefIdent);
      obj.setShipDate(mShipDate);
      obj.setDistName(mDistName);
      obj.setDistIdentCodeQualif(mDistIdentCodeQualif);
      obj.setDistIdentCode(mDistIdentCode);
      obj.setShipFromCity(mShipFromCity);
      obj.setShipFromState(mShipFromState);
      obj.setShipFromPostalCode(mShipFromPostalCode);
      obj.setPurchOrderNum(mPurchOrderNum);
      obj.setDistOrderNum(mDistOrderNum);
      obj.setShipToName(mShipToName);
      obj.setShipToCode(mShipToCode);
      obj.setTrackingNum(mTrackingNum);
      obj.setCarrierCode(mCarrierCode);
      obj.setCarrierName(mCarrierName);
      obj.setItems(mItems);
      obj.setOrderId(mOrderId);
      obj.setUpdateOrderItemActions(mUpdateOrderItemActions);
      obj.setGroupControlNum(mGroupControlNum);
      obj.setInterchangeControlNum(mInterchangeControlNum);
      
      return obj;
    }

    
    /**
     * Sets the PurposeCode property.
     *
     * @param pPurposeCode
     *  String to use to update the property.
     */
    public void setPurposeCode(String pPurposeCode){
        this.mPurposeCode = pPurposeCode;
    }
    /**
     * Retrieves the PurposeCode property.
     *
     * @return
     *  String containing the PurposeCode property.
     */
    public String getPurposeCode(){
        return mPurposeCode;
    }


    /**
     * Sets the TransactionIdentifier property.
     *
     * @param pTransactionIdentifier
     *  String to use to update the property.
     */
    public void setTransactionIdentifier(String pTransactionIdentifier){
        this.mTransactionIdentifier = pTransactionIdentifier;
    }
    /**
     * Retrieves the TransactionIdentifier property.
     *
     * @return
     *  String containing the TransactionIdentifier property.
     */
    public String getTransactionIdentifier(){
        return mTransactionIdentifier;
    }


    /**
     * Sets the TransactionDate property.
     *
     * @param pTransactionDate
     *  Date to use to update the property.
     */
    public void setTransactionDate(Date pTransactionDate){
        this.mTransactionDate = pTransactionDate;
    }
    /**
     * Retrieves the TransactionDate property.
     *
     * @return
     *  Date containing the TransactionDate property.
     */
    public Date getTransactionDate(){
        return mTransactionDate;
    }


    /**
     * Sets the RefIdentQualif property.
     *
     * @param pRefIdentQualif
     *  String to use to update the property.
     */
    public void setRefIdentQualif(String pRefIdentQualif){
        this.mRefIdentQualif = pRefIdentQualif;
    }
    /**
     * Retrieves the RefIdentQualif property.
     *
     * @return
     *  String containing the RefIdentQualif property.
     */
    public String getRefIdentQualif(){
        return mRefIdentQualif;
    }


    /**
     * Sets the RefIdent property.
     *
     * @param pRefIdent
     *  String to use to update the property.
     */
    public void setRefIdent(String pRefIdent){
        this.mRefIdent = pRefIdent;
    }
    /**
     * Retrieves the RefIdent property.
     *
     * @return
     *  String containing the RefIdent property.
     */
    public String getRefIdent(){
        return mRefIdent;
    }


    /**
     * Sets the ShipDate property.
     *
     * @param pShipDate
     *  Date to use to update the property.
     */
    public void setShipDate(Date pShipDate){
        this.mShipDate = pShipDate;
    }
    /**
     * Retrieves the ShipDate property.
     *
     * @return
     *  Date containing the ShipDate property.
     */
    public Date getShipDate(){
        return mShipDate;
    }


    /**
     * Sets the DistName property.
     *
     * @param pDistName
     *  String to use to update the property.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
    }
    /**
     * Retrieves the DistName property.
     *
     * @return
     *  String containing the DistName property.
     */
    public String getDistName(){
        return mDistName;
    }


    /**
     * Sets the DistIdentCodeQualif property.
     *
     * @param pDistIdentCodeQualif
     *  String to use to update the property.
     */
    public void setDistIdentCodeQualif(String pDistIdentCodeQualif){
        this.mDistIdentCodeQualif = pDistIdentCodeQualif;
    }
    /**
     * Retrieves the DistIdentCodeQualif property.
     *
     * @return
     *  String containing the DistIdentCodeQualif property.
     */
    public String getDistIdentCodeQualif(){
        return mDistIdentCodeQualif;
    }


    /**
     * Sets the DistIdentCode property.
     *
     * @param pDistIdentCode
     *  String to use to update the property.
     */
    public void setDistIdentCode(String pDistIdentCode){
        this.mDistIdentCode = pDistIdentCode;
    }
    /**
     * Retrieves the DistIdentCode property.
     *
     * @return
     *  String containing the DistIdentCode property.
     */
    public String getDistIdentCode(){
        return mDistIdentCode;
    }


    /**
     * Sets the ShipFromCity property.
     *
     * @param pShipFromCity
     *  String to use to update the property.
     */
    public void setShipFromCity(String pShipFromCity){
        this.mShipFromCity = pShipFromCity;
    }
    /**
     * Retrieves the ShipFromCity property.
     *
     * @return
     *  String containing the ShipFromCity property.
     */
    public String getShipFromCity(){
        return mShipFromCity;
    }


    /**
     * Sets the ShipFromState property.
     *
     * @param pShipFromState
     *  String to use to update the property.
     */
    public void setShipFromState(String pShipFromState){
        this.mShipFromState = pShipFromState;
    }
    /**
     * Retrieves the ShipFromState property.
     *
     * @return
     *  String containing the ShipFromState property.
     */
    public String getShipFromState(){
        return mShipFromState;
    }


    /**
     * Sets the ShipFromPostalCode property.
     *
     * @param pShipFromPostalCode
     *  String to use to update the property.
     */
    public void setShipFromPostalCode(String pShipFromPostalCode){
        this.mShipFromPostalCode = pShipFromPostalCode;
    }
    /**
     * Retrieves the ShipFromPostalCode property.
     *
     * @return
     *  String containing the ShipFromPostalCode property.
     */
    public String getShipFromPostalCode(){
        return mShipFromPostalCode;
    }


    /**
     * Sets the PurchOrderNum property.
     *
     * @param pPurchOrderNum
     *  String to use to update the property.
     */
    public void setPurchOrderNum(String pPurchOrderNum){
        this.mPurchOrderNum = pPurchOrderNum;
    }
    /**
     * Retrieves the PurchOrderNum property.
     *
     * @return
     *  String containing the PurchOrderNum property.
     */
    public String getPurchOrderNum(){
        return mPurchOrderNum;
    }


    /**
     * Sets the DistOrderNum property.
     *
     * @param pDistOrderNum
     *  String to use to update the property.
     */
    public void setDistOrderNum(String pDistOrderNum){
        this.mDistOrderNum = pDistOrderNum;
    }
    /**
     * Retrieves the DistOrderNum property.
     *
     * @return
     *  String containing the DistOrderNum property.
     */
    public String getDistOrderNum(){
        return mDistOrderNum;
    }


    /**
     * Sets the ShipToName property.
     *
     * @param pShipToName
     *  String to use to update the property.
     */
    public void setShipToName(String pShipToName){
        this.mShipToName = pShipToName;
    }
    /**
     * Retrieves the ShipToName property.
     *
     * @return
     *  String containing the ShipToName property.
     */
    public String getShipToName(){
        return mShipToName;
    }


    /**
     * Sets the ShipToCode property.
     *
     * @param pShipToCode
     *  String to use to update the property.
     */
    public void setShipToCode(String pShipToCode){
        this.mShipToCode = pShipToCode;
    }
    /**
     * Retrieves the ShipToCode property.
     *
     * @return
     *  String containing the ShipToCode property.
     */
    public String getShipToCode(){
        return mShipToCode;
    }


    /**
     * Sets the TrackingNum property.
     *
     * @param pTrackingNum
     *  String to use to update the property.
     */
    public void setTrackingNum(String pTrackingNum){
        this.mTrackingNum = pTrackingNum;
    }
    /**
     * Retrieves the TrackingNum property.
     *
     * @return
     *  String containing the TrackingNum property.
     */
    public String getTrackingNum(){
        return mTrackingNum;
    }


    /**
     * Sets the CarrierCode property.
     *
     * @param pCarrierCode
     *  String to use to update the property.
     */
    public void setCarrierCode(String pCarrierCode){
        this.mCarrierCode = pCarrierCode;
    }
    /**
     * Retrieves the CarrierCode property.
     *
     * @return
     *  String containing the CarrierCode property.
     */
    public String getCarrierCode(){
        return mCarrierCode;
    }


    /**
     * Sets the CarrierName property.
     *
     * @param pCarrierName
     *  String to use to update the property.
     */
    public void setCarrierName(String pCarrierName){
        this.mCarrierName = pCarrierName;
    }
    /**
     * Retrieves the CarrierName property.
     *
     * @return
     *  String containing the CarrierName property.
     */
    public String getCarrierName(){
        return mCarrierName;
    }


    /**
     * Sets the Items property.
     *
     * @param pItems
     *  EdiInp856ItemViewVector to use to update the property.
     */
    public void setItems(EdiInp856ItemViewVector pItems){
        this.mItems = pItems;
    }
    /**
     * Retrieves the Items property.
     *
     * @return
     *  EdiInp856ItemViewVector containing the Items property.
     */
    public EdiInp856ItemViewVector getItems(){
        return mItems;
    }


    /**
     * Sets the OrderId property.
     *
     * @param pOrderId
     *  int to use to update the property.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
    }
    /**
     * Retrieves the OrderId property.
     *
     * @return
     *  int containing the OrderId property.
     */
    public int getOrderId(){
        return mOrderId;
    }


    /**
     * Sets the UpdateOrderItemActions property.
     *
     * @param pUpdateOrderItemActions
     *  String to use to update the property.
     */
    public void setUpdateOrderItemActions(String pUpdateOrderItemActions){
        this.mUpdateOrderItemActions = pUpdateOrderItemActions;
    }
    /**
     * Retrieves the UpdateOrderItemActions property.
     *
     * @return
     *  String containing the UpdateOrderItemActions property.
     */
    public String getUpdateOrderItemActions(){
        return mUpdateOrderItemActions;
    }

    public void setMatchPoNumType(String matchPoNumType) {
		this.matchPoNumType = matchPoNumType;
	}
	
		public String getMatchPoNumType() {
		return matchPoNumType;
	}
	
    /**
     * Sets the GroupControlNum property.
     *
     * @param pGroupControlNum
     *  int to use to update the property.
     */
    public void setGroupControlNum(int pGroupControlNum){
        this.mGroupControlNum = pGroupControlNum;
    }
    /**
     * Retrieves the GroupControlNum property.
     *
     * @return
     *  int containing the GroupControlNum property.
     */
    public int getGroupControlNum(){
        return mGroupControlNum;
    }


    /**
     * Sets the InterchangeControlNum property.
     *
     * @param pInterchangeControlNum
     *  int to use to update the property.
     */
    public void setInterchangeControlNum(int pInterchangeControlNum){
        this.mInterchangeControlNum = pInterchangeControlNum;
    }
    /**
     * Retrieves the InterchangeControlNum property.
     *
     * @return
     *  int containing the InterchangeControlNum property.
     */
    public int getInterchangeControlNum(){
        return mInterchangeControlNum;
    }

    
}
