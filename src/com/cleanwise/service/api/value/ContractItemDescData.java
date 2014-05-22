package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>ContractItemDescData</code> is a ValueObject 
 *  describbing an item tied to a contract.
 *
 *@author     liang
 *@created    September 17, 2001
 */
public class ContractItemDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5672984653979993873L;

    private int mContractItemId;
    // SQL type:NUMBER, not null
    private int mContractId;
    // SQL type:NUMBER, not null
    private int mItemId;
    // SQL type:NUMBER, not null

    private int mQuantity;
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
    private BigDecimal mDistributorCost = new BigDecimal(0);
    private BigDecimal mDistributorBaseCost = new BigDecimal(0);

    private BigDecimal  mAmount             = new BigDecimal(0);
    private String      mAmountS            = new String("");
    private BigDecimal  mQty                = new BigDecimal(0);
    private String      mQtyS               = new String("");    
    private BigDecimal  mDiscountAmount     = new BigDecimal(0);
    private String      mDiscountAmountS    = new String("");
    
    private String      mCurrencyCd         = new String("");
    private String      mBaseCurrencyCd     = new String("");
    private int mCatalogId = 0;
    private BigDecimal distUomConvMultiplier;
    private int distributorId;
    
    /**
     *  Constructor.
     */
    public ContractItemDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     *@param  parm4  Description of Parameter
     */
    public ContractItemDescData(int parm1, int parm2, int parm3, int parm4) {
        mContractItemId = parm1;
        mContractId = parm2;
        mItemId = parm3;
        mQuantity = parm4;
        
        mCwSKU = "";
        mShortDesc = "";
        mSizeDesc = "";
        mPackDesc = "";
        mUOMDesc = "";
        mColorDesc = "";
        mManufacturerCd = "";
        mManufacturerSKU = "";
        mCategoryDesc = "";
        mPrice = new BigDecimal(0);
        mDistributorCost = new BigDecimal(0);
        mDistributorBaseCost = new BigDecimal(0);
        mCatalogId = 0;

    }


    /**
     *  Sets the CwSKU attribute of the ContractItemDescData object
     *
     *@param  pCwSKU  The new CwSKU value
     */
    public void setCwSKU(String pCwSKU) {
        this.mCwSKU = pCwSKU;
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
    }


    /**
     *  Sets the ContractItemId field.
     *
     *@param  pContractItemId  int to use to update the field.
     */
    public void setContractItemId(int pContractItemId) {
        this.mContractItemId = pContractItemId;
        setDirty(true);
    }


    /**
     *  Sets the ContractId field. This field is required to be set in the
     *  database.
     *
     *@param  pContractId  int to use to update the field.
     */
    public void setContractId(int pContractId) {
        this.mContractId = pContractId;
        setDirty(true);
    }


    /**
     *  Sets the ItemId field. This field is required to be set in the database.
     *
     *@param  pItemId  int to use to update the field.
     */
    public void setItemId(int pItemId) {
        this.mItemId = pItemId;
        setDirty(true);
    }


    /**
     *  Sets the Quantity field. This field is required to be set in the
     *  database.
     *
     *@param  pQuantity  int to use to update the field.
     */
    public void setQuantity(int pQuantity) {
        this.mQuantity = pQuantity;
        setDirty(true);
    }

    public void setQuantity(String pQuantityStr) {
        this.mQuantity = Integer.parseInt(pQuantityStr);
        setDirty(true);
    }

    

    /**
     *  Set the value of mAmount.
     *
     *@param  v  Value to assign to mAmount.
     */
    public void setAmount(BigDecimal v) {
        if(null == v) {
            v = new BigDecimal(0); 
        }
        this.mAmount = v;
        this.mAmountS = v.toString();
    }

    
    /**
     *  Set the value of mAmountS.
     *
     *@param  v  Value to assign to mAmountS.
     */
    public void setAmountS(String v) {
        if( null == v || "".equals(v)) {
            v = new String("0");
        }
        this.mAmountS = v;
        this.mAmount = new BigDecimal(v);
    }

    
    /**
     *  Set the value of mQty.
     *
     *@param  v  Value to assign to mQty.
     */
    public void setQty(BigDecimal v) {
        if(null == v) {
            v = new BigDecimal(0); 
        }
        this.mQty = v;        
        this.mQtyS = v.toString();
    }

    
    /**
     *  Set the value of mQtyS.
     *
     *@param  v  Value to assign to mQtyS.
     */
    public void setQtyS(String v) {
        if( null == v || "".equals(v)) {
            v = new String("0");
        }
        this.mQtyS = v;
        this.mQty = new BigDecimal(v);
    }

    /**
     *  Set the value of mDiscountAmount.
     *
     *@param  v  Value to assign to mDiscountAmount.
     */
    public void setDiscountAmount(BigDecimal v) {
        if(null == v) {
            v = new BigDecimal(0); 
        }
        this.mDiscountAmount = v;
        this.mDiscountAmountS = v.toString();
    }

    
    /**
     *  Set the value of mDiscountAmountS.
     *
     *@param  v  Value to assign to mDiscountAmountS.
     */
    public void setDiscountAmountS(String v) {
        if( null == v || "".equals(v)) {
            v = new String("0");
        }
        this.mDiscountAmountS = v;
        this.mDiscountAmount = new BigDecimal(v);
    }

    
    /**
     *  Set the value of mCurrencyCd.
     *
     *@param  v  Value to assign to mCurrencyCd.
     */
    public void setCurrencyCd(String v) {
        this.mCurrencyCd = v;
    }


    /**
     *  Set the value of mBaseCurrencyCd.
     *
     *@param  v  Value to assign to mBaseCurrencyCd.
     */
    public void setBaseCurrencyCd(String v) {
        this.mBaseCurrencyCd = v;
    }

    
    
    /**
     *  Gets the CwSKU attribute of the ContractItemDescData object
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
     *  Retrieves the ContractItemId field.
     *
     *@return    int containing the ContractItemId field.
     */
    public int getContractItemId() {
        return mContractItemId;
    }


    /**
     *  Retrieves the ContractId field.
     *
     *@return    int containing the ContractId field.
     */
    public int getContractId() {
        return mContractId;
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
     *  Get the value of mAmount.
     *
     *@return    value of mAmount.
     */
    public BigDecimal getAmount() {
      if ( null == mAmount ) {
	mAmount = new BigDecimal(0);
      }
        return mAmount;
    }

    
    /**
     *  Get the value of mAmountS.
     *
     *@return    value of mAmountS.
     */
    public String getAmountS() {
        return mAmountS;
    }

    

    /**
     *  Get the value of mQty.
     *
     *@return    value of mQty.
     */
    public BigDecimal getQty() {
        return mQty;
    }


    /**
     *  Get the value of mQtyS.
     *
     *@return    value of mQtyS.
     */
    public String getQtyS() {
        return mQtyS;
    }

    
    
    
    /**
     *  Get the value of mDiscountAmount.
     *
     *@return    value of mDiscountAmount.
     */
    public BigDecimal getDiscountAmount() {
        return mDiscountAmount;
    }


    /**
     *  Get the value of mDiscountAmountS.
     *
     *@return    value of mDiscountAmountS.
     */
    public String getDiscountAmountS() {
        return mDiscountAmountS;
    }

    
    /**
     *  Get the value of mCurrencyCd.
     *
     *@return    value of mCurrencyCd.
     */
    public String getCurrencyCd() {
        return mCurrencyCd;
    }

    
    /**
     *  Get the value of mBaseCurrencyCd.
     *
     *@return    value of mBaseCurrencyCd.
     */
    public String getBaseCurrencyCd() {
        return mBaseCurrencyCd;
    }

    /**
     * Get the value of DistributorCost.
     * @return value of DistributorCost.
     */
    public BigDecimal getDistributorCost() {
        if ( null == mDistributorCost ) {
            mDistributorCost = new BigDecimal(0);
        } 
	return mDistributorCost;
    }
    
    /**
     * Set the value of DistributorCost.
     * @param v  Value to assign to DistributorCost.
     */
    public void setDistributorCost(BigDecimal  v) {
	this.mDistributorCost = v;
    }

    /**
     * Get the value of DistributorCost.
     * @return value of DistributorCost.
     */
    public String getDistributorCostS() {
        if (null == mDistributorCost ) {
            mDistributorCost = new BigDecimal(0);
        }
	return mDistributorCost.toString();
    }
    
    /**
     * Set the value of DistributorCost.
     * @param v  Value to assign to DistributorCost.
     */
    public void setDistributorCostS(String  v) {
	this.mDistributorCost = new BigDecimal(v);
    }

    
    
    /**
     * Get the value of DistributorBaseCost.
     * @return value of DistributorBaseCost.
     */
    public BigDecimal getDistributorBaseCost() {
        if ( null == mDistributorBaseCost ) {
            mDistributorBaseCost = new BigDecimal(0);
        } 
	return mDistributorBaseCost;
    }
    
    /**
     * Set the value of DistributorBaseCost.
     * @param v  Value to assign to DistributorBaseCost.
     */
    public void setDistributorBaseCost(BigDecimal  v) {
	this.mDistributorBaseCost = v;
    }

    /**
     * Get the value of DistributorBaseCost.
     * @return value of DistributorBaseCost.
     */
    public String getDistributorBaseCostS() {
        if (null == mDistributorBaseCost ) {
            mDistributorBaseCost = new BigDecimal(0);
        }
	return mDistributorBaseCost.toString();
    }
    
    /**
     * Set the value of DistributorBaseCost.
     * @param v  Value to assign to DistributorBaseCost.
     */
    public void setDistributorBaseCostS(String  v) {
	this.mDistributorBaseCost = new BigDecimal(v);
    }
    
    /**
     * Get the value of CatalogId.
     * @return value of CatalogId.
     */
    public int getCatalogId() {
	return mCatalogId;
    }
    
    /**
     * Set the value of CatalogId.
     * @param v  Value to assign to CatalogId.
     */
    public void setCatalogId(int  v) {
	this.mCatalogId = v;
    }
    
    /**
     *  Returns a String representation of the value object
     *
     *@return    The String representation of this ContractItemDescData object
     */
    public String toString() {
        
        String priceStr = (mPrice == null) ? 
            mPrice.toString() : "0";
            
        return "[" + "ContractItemId=" +
                mContractItemId + ", ContractId=" +
                mContractId + ", ItemId=" + mItemId +
                ", Quantity=" + mQuantity + ", mCwSKU=" +  mCwSKU +
                ", mShortDesc=" + mShortDesc + ", mSizeDesc=" + mSizeDesc +
                ", mPackDesc=" + mPackDesc + ", mUOMDesc=" + mUOMDesc +
                ", mColorDesc=" +  mColorDesc + 
                ", mManufacturerCd=" +  mManufacturerCd +
                ", mManufacturerSKU=" +  mManufacturerSKU +
                ", mCategoryDesc=" +  mCategoryDesc +
	    ", mDistributorCost=" + mDistributorCost.toString() +
            ", mDistributorBaseCost=" + mDistributorBaseCost.toString() +
                ", mPrice=" + mPrice.toString() +
                ", mCatalogId = "+mCatalogId + "]";
    }


    /**
     *  Creates a new ContractItemDescData
     *
     *@return    Newly initialized ContractItemDescData object.
     */
    public static ContractItemDescData createValue() {
        ContractItemDescData valueData = new ContractItemDescData();
        return valueData;
    }

    /** Getter for property distUomConvMultiplier.
     * @return Value of property distUomConvMultiplier.
     *
     */
    public BigDecimal getDistUomConvMultiplier() {
        return this.distUomConvMultiplier;
    }
    
    /** Setter for property distUomConvMultiplier.
     * @param distUomConvMultiplier New value of property distUomConvMultiplier.
     *
     */
    public void setDistUomConvMultiplier(BigDecimal distUomConvMultiplier) {
        this.distUomConvMultiplier = distUomConvMultiplier;
    }
    
    /** Getter for property distributorId.
     * @return Value of property distributorId.
     *
     */
    public int getDistributorId() {
        return this.distributorId;
    }
    
    /** Setter for property distributorId.
     * @param distributorId New value of property distributorId.
     *
     */
    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }
    
}

