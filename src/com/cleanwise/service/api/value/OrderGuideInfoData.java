package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  Description of the Class
 *
 *@author     dvieira
 *@created    September 5, 2001
 */
public class OrderGuideInfoData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -2799696975635545819L;

    OrderGuideData mOrderGuideData;
    OrderGuideItemDescDataVector mOgItems;
    double         mTotalAmountD = 0D;  
    BigDecimal     mTotalAmount  = new BigDecimal(0);
    BigDecimal estimatedTotal = new BigDecimal(0);
    
    /**
     *  Constructor for the OrderGuideInfoData object
     *
     *@param  pOrderGuideData                
     *@param  pOrderGuideItemDescDataVector 
     */
    public OrderGuideInfoData(OrderGuideData pOrderGuideData,
            OrderGuideItemDescDataVector pOrderGuideItemDescDataVector) {
        this.mOrderGuideData = (pOrderGuideData != null)
                 ? pOrderGuideData : OrderGuideData.createValue();
        this.mOgItems = (pOrderGuideItemDescDataVector != null)
                 ? pOrderGuideItemDescDataVector : 
                 new OrderGuideItemDescDataVector();
        this.mTotalAmountD = 0D;
        if(null != pOrderGuideItemDescDataVector) {
            for(int i = 0; i < pOrderGuideItemDescDataVector.size(); i++) {
                mTotalAmountD += ((OrderGuideItemDescData)pOrderGuideItemDescDataVector.get(i)).getAmount().doubleValue();
            }
        }                 
        this.mTotalAmount = new BigDecimal(mTotalAmountD);
    }

    /**
     * The <code>createValue</code> method initializes
     * all member variables.
     *
     * @return an <code>OrderGuideInfoData</code> value
     */
    public static OrderGuideInfoData createValue() {
        return new OrderGuideInfoData(null, null);
    }

    /**
     *  Sets the OrderGuideData attribute of the OrderGuideInfoData
     *  object
     *
     *@param  pOrderGuideData  The new OrderGuideData value
     */
    public void setOrderGuideData(OrderGuideData pOrderGuideData) {
        this.mOrderGuideData = pOrderGuideData;
    }


    /**
     *  Sets the OrderGuideStructure attribute of the
     *  OrderGuideInfoData object
     *
     *@param  pOrderGuideStructure  The new OrderGuideStructure value
     */
    public void setOrderGuideItems(OrderGuideItemDescDataVector 
                                   pItems) {
        this.mOgItems = pItems;
        this.mTotalAmountD = 0D;
        for(int i = 0; i < pItems.size(); i ++) {
            mTotalAmountD += ((OrderGuideItemDescData)pItems.get(i)).getAmount().doubleValue();
        }
        this.mTotalAmount = new BigDecimal(mTotalAmountD);
    }


    /**
     *  Sets the totalAmount field. This field is the sum of quantity x price
     *  database.
     *
     *@param  pAmount  int to use to update the field.
     */
    public void setTotalAmount(BigDecimal v) {
        mTotalAmount = v;
        mTotalAmountD = mTotalAmount.doubleValue(); 
    }


    /**
     *  Sets the totalAmount field. This field is the sum of quantity x price
     *  database.
     *
     *@param  pAmount  int to use to update the field.
     */
    public void setTotalAmountD(double v) {
        mTotalAmountD = v;
        this.mTotalAmount = new BigDecimal(mTotalAmountD);
    }

    
    /**
     *  Gets the OrderGuideData attribute of the OrderGuideInfoData object
     *
     *@return    The OrderGuideData value
     */
    public OrderGuideData getOrderGuideData() {
        return mOrderGuideData;
    }


    /**
     *  Gets the OrderGuideStructureVector attribute of the
     *  OrderGuideInfoData object
     *
     *@return    The OrderGuideItemDescDataVector value
     */
    public OrderGuideItemDescDataVector getOrderGuideItems() {
        return mOgItems;
    }


    /**
     *  Retrieves the totalAmount field.
     *
     *@return    double containing the totalAmount field.
     */
    public BigDecimal getTotalAmount() {
        try {
            mTotalAmount = mTotalAmount.setScale
		(2, BigDecimal.ROUND_HALF_UP);
        }
        catch (Exception e) {
        }
        return mTotalAmount;
    }

    
    /**
     *  Retrieves the totalAmount field.
     *
     *@return    double containing the totalAmount field.
     */
    public double getTotalAmountD() {
        return mTotalAmountD;
    }
    
    
    /**
	 * @return the estimatedTotal
	 */
	public final BigDecimal getEstimatedTotal() {
		return estimatedTotal;
	}

	/**
	 * @param estimatedTotal the estimatedTotal to set
	 */
	public final void setEstimatedTotal(BigDecimal estimatedTotal) {
		this.estimatedTotal = estimatedTotal;
	}

	/**
     *  <code>toString</code>
     *
     *@return    String
     */
    public String toString() {
        return "[OrderGuideData=" + mOrderGuideData + 
            ", OrderGuideItemDescDataVector=" + mOgItems + "]";
    }
    
}

