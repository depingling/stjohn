package com.cleanwise.service.api.value;

/**
 * Title:        AckItemData
 * Description:  This is a ValueObject class that contain a OrderItemData object
 *               and properties used for inbound 855
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Deping
 */

import com.cleanwise.service.api.framework.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <code>AckItemData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ITEM.
 */
public class AckItemData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -8195882793465149311L;
    private int mLineNum;
    private int mQuantity;
    private String mUom;
    private BigDecimal mPrice;
    private int mCwSkuNum;
    private String mDistSkuNum;
    private String mAction;
    private String itemName;

    private AckItemData () {
    }

    /**
     * Creates a new OrderRequestData
     *
     * @return
     *  Newly initialized OrderRequestData object.
     */
    public static AckItemData createValue ()
    {
        AckItemData valueData = new AckItemData();
        return valueData;
    }

    /**
     * Sets the LineNum field.
     *
     * @param pLineNum
     *  int to use to update the field.
     */
    public void setLineNum(int pLineNum){
        this.mLineNum = pLineNum;
    }
    /**
     * Retrieves the LineNum field.
     *
     * @return
     *  int containing the LineNum field.
     */
    public int getLineNum(){
        return mLineNum;
    }

    /**
     * Sets the Quantity field.
     *
     * @param pQuantity
     *  int to use to update the field.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the Quantity field.
     *
     * @return
     *  int containing the Quantity field.
     */
    public int getQuantity(){
        return mQuantity;
    }

    /**
     * Sets the Uom field.
     *
     * @param pUom
     *  String to use to update the field.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
        setDirty(true);
    }
    /**
     * Retrieves the Uom field.
     *
     * @return
     *  String containing the Uom field.
     */
    public String getUom(){
        return mUom;
    }

    /**
     * Sets the Price field.
     *
     * @param pPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setPrice(BigDecimal pPrice){
        this.mPrice = pPrice;
        setDirty(true);
    }
    /**
     * Retrieves the Price field.
     *
     * @return
     *  java.math.BigDecimal containing the Price field.
     */
    public BigDecimal getPrice(){
        return mPrice;
    }

    /**
     * Sets the CwSkuNum field.
     *
     * @param pCwSkuNum
     *  int to use to update the field.
     */
    public void setCwSkuNum(int pCwSkuNum){
        this.mCwSkuNum = pCwSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the CwSkuNum field.
     *
     * @return
     *  int containing the CwSkuNum field.
     */
    public int getCwSkuNum(){
        return mCwSkuNum;
    }

    /**
     * Sets the DistSkuNum field.
     *
     * @param pDistSkuNum
     *  String to use to update the field.
     */
    public void setDistSkuNum(String pDistSkuNum){
        this.mDistSkuNum = pDistSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistSkuNum field.
     *
     * @return
     *  String containing the DistSkuNum field.
     */
    public String getDistSkuNum(){
        return mDistSkuNum;
    }


    /**
     * Sets the Action field.
     *
     * @param pAction
     *  String to use to update the field.
     */
    public void setAction(String pAction){
        this.mAction = pAction;
    }
    /**
     * Retrieves the Action field.
     *
     * @return
     *  String containing the Action field.
     */
    public String getAction(){
        return mAction;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderData object
     */
    public String toString()
    {
    return "[Line number=" + mLineNum + ", Cw Sku=" + mCwSkuNum+ ", Distributor Sku=" + mDistSkuNum + ", Cw Uom=" + mUom+ ", Quantity=" + mQuantity + ", Price=" + mPrice+ ", Action=" + mAction + "]";
    }

    /**
     * Holds value of property actionDate.
     */
    private Date actionDate;

    /**
     * Getter for property actionDate.
     * @return Value of property actionDate.
     */
    public Date getActionDate() {
        return this.actionDate;
    }

    /**
     * Setter for property actionDate.
     * @param actionDate New value of property actionDate.
     */
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
