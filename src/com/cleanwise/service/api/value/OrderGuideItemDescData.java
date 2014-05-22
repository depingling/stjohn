package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>OrderGuideItemDescData</code> is a ValueObject 
 *  describbing an item tied to an order guide.
 *
 *@author     dvieira
 *@created    September 12, 2001
 */
public class OrderGuideItemDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4387646924043433256L;

    private int mOrderGuideStructureId;
    // SQL type:NUMBER, not null
    private int mOrderGuideId;
    // SQL type:NUMBER, not null
    private int mItemId;
    // SQL type:NUMBER, not null

    private int mQuantity;
    private BigDecimal mAmount = new BigDecimal(0);
    private String mCwSKU;
    private String mShortDesc;
    private String mSizeDesc;
    private String mPackDesc;
    private String mUOMDesc;
    private String mColorDesc;
    private String mManufacturerCd;
    private String mManufacturerSKU;
    private String mCategoryDesc;
    private BigDecimal mPrice = new BigDecimal(0);


    /**
     *  Constructor.
     */
    public OrderGuideItemDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     *@param  parm4  Description of Parameter
     */
    public OrderGuideItemDescData
            (int parm1, int parm2, int parm3, int parm4) {
        mOrderGuideStructureId = parm1;
        mOrderGuideId = parm2;
        mItemId = parm3;
        mQuantity = parm4;
        
        mAmount = new BigDecimal(0);
        mCwSKU = "";
        mShortDesc = "";
        mSizeDesc = "";
        mPackDesc = "";
        mUOMDesc = "";
        mColorDesc = "";
        mManufacturerCd = "";
        mManufacturerSKU = "";
        mCategoryDesc = "";
        BigDecimal mPrice = new BigDecimal(0);

    }


    /**
     *  Sets the CwSKU attribute of the OrderGuideItemDescData object
     *
     *@param  pCwSKU  The new CwSKU value
     */
    public void setCwSKU(String v) {
        mCwSKU = v;
    }


    /**
     *  Set the mShortDesc field.
     *
     *@param  v   The new ShortDesc value
     */
    public void setShortDesc(String v) {
        mShortDesc = v;
    }


    /**
     *  Set the mSizeDesc field.
     *
     *@param  v   The new SizeDesc value
     */
    public void setSizeDesc(String v) {
        mSizeDesc = v;
    }


    /**
     *  Set the mPackDesc field.
     *
     *@param  v   The new PackDesc value
     */
    public void setPackDesc(String v) {
        mPackDesc = v;
    }


    /**
     *  Set the mUOMDesc field.
     *
     *@param  v   The new UomDesc value
     */
    public void setUomDesc(String v) {
        mUOMDesc = v;
    }


    /**
     *  Set the mColorDesc field.
     *
     *@param  v   The new ColorDesc value
     */
    public void setColorDesc(String v) {
        mColorDesc = v;
    }


    /**
     *  Set the mManufacturerCd field.
     *
     *@param  v   The new ManufacturerCd value
     */
    public void setManufacturerCd(String v) {
        mManufacturerCd = v;
    }


    /**
     *  Set the mManufacturerSKU field.
     *
     *@param  v   The new ManufacturerSKU value
     */
    public void setManufacturerSKU(String v) {
        mManufacturerSKU = v;
    }


    /**
     *  Set the value of mCategoryDesc.
     *
     *@param  v  Value to assign to mCategoryDesc.
     */
    public void setCategoryDesc(String v) {
        this.mCategoryDesc = v;
    }


    /**
     *  Set the value of mPrice.
     *
     *@param  v  Value to assign to mPrice.
     */
    public void setPrice(BigDecimal v) {
        this.mPrice = v;
        this.mAmount = new BigDecimal(mQuantity * mPrice.doubleValue());
    }


    /**
     *  Sets the OrderGuideStructureId field.
     *
     *@param  pOrderGuideStructureId  int to use to update the field.
     */
    public void setOrderGuideStructureId(int v) {
        this.mOrderGuideStructureId = v;
        setDirty(true);
    }


    /**
     *  Sets the OrderGuideId field. This field is required to be set in the
     *  database.
     *
     *@param  pOrderGuideId  int to use to update the field.
     */
    public void setOrderGuideId(int v) {
        mOrderGuideId = v;
        setDirty(true);
    }


    /**
     *  Sets the ItemId field. This field is required to be set in the
     *  database.
     *
     *@param  pItemId  int to use to update the field.
     */
    public void setItemId(int v) {
        mItemId = v;
        setDirty(true);
    }


    /**
     *  Sets the Quantity field. This field is required to be set in the
     *  database.
     *
     *@param  pQuantity  int to use to update the field.
     */
    public void setQuantity(int v) {
        mQuantity = v;
        mAmount = new BigDecimal(mQuantity * mPrice.doubleValue());
        setDirty(true);
    }

    /**
     *  Sets the Amount field. This field is quantity x price
     *  database.
     *
     *@param  pAMount  int to use to update the field.
     */
    public void setAmount(BigDecimal v) {
        mAmount = v;
        setDirty(true);
    }

    
    /**
     *  Gets the CwSKU attribute of the OrderGuideItemDescData object
     *
     *@return    The CwSKU value
     */
    public String getCwSKU() {
        return mCwSKU;
    }


    /**
     *  Get the mShortDesc field.
     *
     *@return    String
     */
    public String getShortDesc() {
        return mShortDesc;
    }


    /**
     *  Get the mSizeDesc field.
     *
     *@return    String
     */
    public String getSizeDesc() {
        return mSizeDesc;
    }


    /**
     *  Get the mPackDesc field.
     *
     *@return     String
     */
    public String getPackDesc() {
        return mPackDesc;
    }


    /**
     *  Get the mUOMDesc field.
     *
     *@return    String
     */
    public String getUomDesc() {
        return mUOMDesc;
    }


    /**
     *  Get the mColorDesc field.
     *
     *@return    String
     */
    public String getColorDesc() {
        return mColorDesc;
    }


    /**
     *  Get the mManufacturerCd field.
     *
     *@return    String
     */
    public String getManufacturerCd() {
        return mManufacturerCd;
    }


    /**
     *  Get the mManufacturerSKU field.
     *
     *@return    String
     */
    public String getManufacturerSKU() {
        return mManufacturerSKU;
    }


    /**
     *  Get the value of mCategoryDesc.
     *
     *@return    value of mCategoryDesc.
     */
    public String getCategoryDesc() {
        return mCategoryDesc;
    }


    /**
     *  Get the value of mPrice.
     *
     *@return    value of mPrice.
     */
    public BigDecimal getPrice() {
        return mPrice;
    }


    /**
     *  Retrieves the OrderGuideStructureId field.
     *
     *@return    int containing the OrderGuideStructureId field.
     */
    public int getOrderGuideStructureId() {
        return mOrderGuideStructureId;
    }


    /**
     *  Retrieves the OrderGuideId field.
     *
     *@return    int containing the OrderGuideId field.
     */
    public int getOrderGuideId() {
        return mOrderGuideId;
    }


    /**
     *  Retrieves the ItemId field.
     *
     *@return    int containing the ItemId field.
     */
    public int getItemId() {
        return mItemId;
    }


    /**
     *  Retrieves the Quantity field.
     *
     *@return    int containing the Quantity field.
     */
    public int getQuantity() {
        return mQuantity;
    }


    /**
     *  Retrieves the Amount field.
     *
     *@return    BigDecimal containing the Amount field.
     */
    public BigDecimal getAmount() {
        try {
            mAmount = mAmount.setScale
		(2, BigDecimal.ROUND_HALF_UP);
        }
        catch (Exception e) {
        }
        return mAmount;
    }

    
    /**
     *  Returns a String representation of the value object
     *
     *@return The String representation of this OrderGuideItemDescData
     * object
     */
    public String toString() {
        
        String priceStr = (mPrice == null) ? 
            mPrice.toString() : "0";
            
        return "[" + "OrderGuideStructureId=" +
                mOrderGuideStructureId + ", OrderGuideId=" +
                mOrderGuideId + ", ItemId=" + mItemId +
                ", Quantity=" + mQuantity + ", CwSKU=" +  mCwSKU +
                ", ShortDesc=" + mShortDesc + ", SizeDesc=" + mSizeDesc +
                ", PackDesc=" + mPackDesc + ", UOMDesc=" + mUOMDesc +
                ", ColorDesc=" +  mColorDesc + 
                ", ManufacturerCd=" +  mManufacturerCd +
                ", ManufacturerSKU=" +  mManufacturerSKU +
                ", CategoryDesc=" +  mCategoryDesc +
                ", Price=" + mPrice.toString() + "]";
    }

    /**
     *  Creates a clone of the current object
     *
     *@return The Object copy of the current one
     * object
     */
    public Object clone() {
      OrderGuideItemDescData ogidD = new OrderGuideItemDescData();        
      ogidD.setOrderGuideStructureId(mOrderGuideStructureId);
      ogidD.setOrderGuideId(mOrderGuideId);
      ogidD.setItemId(mItemId);
      ogidD.setQuantity(mQuantity);
      ogidD.setAmount(mAmount);
      ogidD.setCwSKU(mCwSKU);
      ogidD.setShortDesc(mShortDesc);
      ogidD.setSizeDesc(mSizeDesc);
      ogidD.setPackDesc(mPackDesc);
      ogidD.setUomDesc(mUOMDesc);
      ogidD.setColorDesc(mColorDesc);
      ogidD.setManufacturerCd(mManufacturerCd);
      ogidD.setManufacturerSKU(mManufacturerSKU);
      ogidD.setCategoryDesc(mCategoryDesc);
      ogidD.setPrice(mPrice);
      return ogidD;
    }

    /**
     *  Creates a new OrderGuideItemDescData
     *
     *@return    Newly initialized OrderGuideItemDescData object.
     */
    public static OrderGuideItemDescData createValue() {
        OrderGuideItemDescData valueData = new OrderGuideItemDescData();
        return valueData;
    }

}

