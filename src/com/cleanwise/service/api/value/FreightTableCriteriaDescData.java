
package com.cleanwise.service.api.value;

/**
 * Title: FreightTableCriteriaDescData Description:
 * This is a ValueObject class describbing a FreightTableCriteria.
 * Copyright (c) 2001 Company: CleanWise, Inc.
 * @author       Liang Li
*/
import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>FreightTableCriteriaDescData</code>
 */
public class FreightTableCriteriaDescData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = 6559330884815927815L;

    private int    mFreightTableCriteriaId;
    private int    mFreightTableId;
    private String mLowerAmount;
    private String mHigherAmount;
    private String mFreightAmount;
    private String mHandlingAmount;

    private String mFreightHandlerId = "";
    private String mFreightCriteriaTypeCd = "";
    private String mRuntimeTypeCd = "";
    private String mShortDesc = "";

    private Date   mAddDate = new Date();
    private String mAddBy;

    private String mUIOrder;
    private String mChargeCd;// SQL type:VARCHAR2
    private String mDiscount;// SQL type:NUMBER


    /**
     * Constructor.
     */
    private FreightTableCriteriaDescData ()
    {
        mFreightTableCriteriaId = 0;
        mFreightTableId = 0;
        mLowerAmount = "";
        mHigherAmount = "";
        mFreightAmount = "";
        mHandlingAmount = "";
        mUIOrder = "";
        mChargeCd = "";
        mDiscount = "";
    }

    /**
     * Constructors
     */
    public FreightTableCriteriaDescData(     
              int    pFreightTableCriteriaId,
              int    pFreightTableId,
              String pLowerAmount,
              String pHigherAmount,
              String pFreightAmount,
              String pHandlingAmount,
              String pUIOrder,
              String pDiscount) {
        mFreightTableCriteriaId   = pFreightTableCriteriaId;
        mFreightTableId =  pFreightTableId;
        mLowerAmount = pLowerAmount;
        mHigherAmount = pHigherAmount;
        mFreightAmount = pFreightAmount;
        mHandlingAmount = pHandlingAmount;
        mUIOrder = pUIOrder;
        mDiscount = pDiscount;
    }

    public FreightTableCriteriaDescData(     
              int    pFreightTableCriteriaId,
              int    pFreightTableId,
              String pLowerAmount,
              String pHigherAmount,
              String pFreightAmount,
              String pHandlingAmount,
              String pUIOrder) {
        this(pFreightTableCriteriaId,
             pFreightTableId,
             pLowerAmount,
             pHigherAmount,
             pFreightAmount,
             pHandlingAmount,
             pUIOrder,
             "");
    }

    /**
     * Creates a new FreightTableCriteriaDescData
     *
     * @return
     *  Newly initialized FreightTableCriteriaDescData object.
     */
    public static FreightTableCriteriaDescData createValue ()
    {
        FreightTableCriteriaDescData valueData = new FreightTableCriteriaDescData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this FreightTableCriteriaDescData object
     */
    public String toString()
    {
        return "[" +
            "FreightTableCriteriaId=" + mFreightTableCriteriaId +
            ", FreightTableId=" + mFreightTableId +
            ", LowerAmount=" + mLowerAmount +
            ", HigherAmount=" + mHigherAmount +
            ", FreightAmount=" + mFreightAmount +
            ", HandlingAmount=" + mHandlingAmount +
            ", ChargeCd=" + mChargeCd +
            ", UIOrder=" + mUIOrder + 
            ", Discount=" + mDiscount + "]";
    }

    /**
     * Get the value of mFreightAmount.
     * @return value of mFreightAmount.
     */
    public String getFreightAmount() {
        return mFreightAmount;
    }

    /**
     * Set the value of mFreightAmount.
     * @param v  Value to assign to mFreightAmount.
     */
    public void setFreightAmount(String  v) {
        this.mFreightAmount = v.trim();
    }

    /**
     * Get the value of mHandlingAmount.
     * @return value of mHandlingAmount.
     */
    public String getHandlingAmount() {
        return mHandlingAmount;
    }

    /**
     * Set the value of mHandlingAmount.
     * @param v  Value to assign to mHandlingAmount.
     */
    public void setHandlingAmount(String  v) {
        this.mHandlingAmount = v.trim();
    }

    /**
     * Get the value of mLowerAmount.
     * @return value of mLowerAmount.
     */
    public String getLowerAmount() {
        return mLowerAmount;
    }

    /**
     * Set the value of mLowerAmount.
     * @param v  Value to assign to mLowerAmount.
     */
    public void setLowerAmount(String  v) {
        this.mLowerAmount = v.trim();
    }

    /**
     * Get the value of mFreightTableId.
     * @return value of mFreightTableId.
     */
    public int getFreightTableId() {
        return mFreightTableId;
    }

    /**
     * Set the value of mFreightTableId.
     * @param v  Value to assign to mFreightTableId.
     */
    public void setFreightTableId(int  v) {
        this.mFreightTableId = v;
    }

    /**
     * Sets the FreightTableCriteriaId field. This field is required to be set in the database.
     *
     * @param pFreightTableCriteriaId
     *  int to use to update the field.
     */
    public void setFreightTableCriteriaId(int pFreightTableCriteriaId){
        this.mFreightTableCriteriaId = pFreightTableCriteriaId;
    }

    /**
     * Retrieves the FreightTableCriteriaId field.
     *
     * @return
     *  int containing the FreightTableCriteriaId field.
     */
    public int getFreightTableCriteriaId(){
        return mFreightTableCriteriaId;
    }


    /**
     * Sets the HigherAmount field. This field is required to be set in the database.
     *
     * @param pHigherAmount
     *  int to use to update the field.
     */
    public void setHigherAmount(String pHigherAmount){
        this.mHigherAmount = pHigherAmount.trim();
    }

    /**
     * Retrieves the HigherAmount field.
     *
     * @return
     *  String containing the HigherAmount field.
     */
    public String getHigherAmount(){
        return mHigherAmount;
    }

    /**
     * Sets the AddBy field. This field is required to be set in the database.
     *
     * @param pAddBy
     *  int to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
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
     *  int to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
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
     * Sets the FreightHandlerId field.
     *
     * @param pFreightHandlerId
     *  String to use to update the field.
     */
    public void setFreightHandlerId(String pFreightHandlerId){
        this.mFreightHandlerId = pFreightHandlerId;
    }
    /**
     * Retrieves the FreightHandlerId field.
     *
     * @return
     *  String containing the FreightHandlerId field.
     */
    public String getFreightHandlerId(){
        return mFreightHandlerId;
    }

    /**
     * Sets the FreightCriteriaTypeCd field.
     *
     * @param pFreightCriteriaTypeCd
     *  String to use to update the field.
     */
    public void setFreightCriteriaTypeCd(String pFreightCriteriaTypeCd){
        this.mFreightCriteriaTypeCd = pFreightCriteriaTypeCd;
    }
    /**
     * Retrieves the FreightCriteriaTypeCd field.
     *
     * @return
     *  String containing the FreightCriteriaTypeCd field.
     */
    public String getFreightCriteriaTypeCd(){
        return mFreightCriteriaTypeCd;
    }

    /**
     * Sets the RuntimeTypeCd field.
     *
     * @param pRuntimeTypeCd
     *  String to use to update the field.
     */
    public void setRuntimeTypeCd(String pRuntimeTypeCd){
        this.mRuntimeTypeCd = pRuntimeTypeCd;
    }
    /**
     * Retrieves the RuntimeTypeCd field.
     *
     * @return
     *  String containing the RuntimeTypeCd field.
     */
    public String getRuntimeTypeCd(){
        return mRuntimeTypeCd;
    }

    /**
     * Get the value of mUIOrder.
     * @return value of mUIOrder.
     */
    public String getUIOrder() {
        return mUIOrder;
    }

    /**
     * Set the value of mUIOrder.
     * @param v  Value to assign to mUIOrder.
     */
    public void setUIOrder(String  v) {
        this.mUIOrder = v;
    }

    /**
     * Sets the ShortDesc field.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
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
  * Sets the ChargeCd field.
  *
  * @param pChargeCd
  *  String to use to update the field.
  */
 public void setChargeCd(String pChargeCd){
     this.mChargeCd = pChargeCd;
     setDirty(true);
 }
 /**
  * Retrieves the ChargeCd field.
  *
  * @return
  *  String containing the ChargeCd field.
  */
 public String getChargeCd(){
     return mChargeCd;
 }

    public void setDiscount(String discount){
        mDiscount = discount;
    }

    public String getDiscount(){
        return mDiscount;
    }

}
