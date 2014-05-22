
package com.cleanwise.service.api.value;

/**
 * Title: OrderGuideDescData Description:
 * This is a ValueObject class describbing an order guide.
 * Copyright (c) 2001 Company: CleanWise, Inc.
 * @author       dvieira
*/
import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>OrderGuideDescData</code> 
 */
public class OrderGuideDescData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 8851012288394568798L;
    
    private int    mOrderGuideId;
    private String mOrderGuideName;
    private String mStatus;
    private String mOrderGuideTypeCd;
    private int    mCatalogId;
    private String mCatalogName;

    /**
     * Constructor.
     */
    private OrderGuideDescData ()
    {
        mOrderGuideName = "";
        mStatus = "";
        mOrderGuideTypeCd = "";
        mCatalogName = "";
    }

    /**
     * Constructor. 
     */
    public OrderGuideDescData
        (     int    pOrderGuideId,
              String pOrderGuideName,
              String pStatus,
              String pOrderGuideTypeCd,
              int    pCatalogId,
              String pCatalogName
              )
    {
        mOrderGuideId   = pOrderGuideId;
        mOrderGuideName =  pOrderGuideName;
        mStatus = mStatus;
        mOrderGuideTypeCd = pOrderGuideTypeCd;
        mCatalogId = pCatalogId;
        mCatalogName = pCatalogName;
    }

    /**
     * Creates a new OrderGuideDescData
     *
     * @return
     *  Newly initialized OrderGuideDescData object.
     */
    public static OrderGuideDescData createValue () 
    {
        OrderGuideDescData valueData = new OrderGuideDescData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderGuideDescData object
     */
    public String toString()
    {
        return "[" + 
            "OrderGuideId=" + mOrderGuideId + 
            ", OrderGuideName=" + mOrderGuideName + 
            ", Status=" + mStatus + 
            ", OrderGuideTypeCd=" + mOrderGuideTypeCd + 
            ", CatalogId=" + mCatalogId + 
            ", CatalogName=" + mCatalogName + "]";
    }

    /**
     * Get the value of mCatalogName.
     * @return value of mCatalogName.
     */
    public String getCatalogName() {
        return mCatalogName;
    }
    
    /**
     * Set the value of mCatalogName.
     * @param v  Value to assign to mCatalogName.
     */
    public void setCatalogName(String  v) {
        this.mCatalogName = v;
    }
    
    /**
     * Get the value of mStatus.
     * @return value of mStatus.
     */
    public String getStatus() {
        return mStatus;
    }
    
    /**
     * Set the value of mStatus.
     * @param v  Value to assign to mStatus.
     */
    public void setStatus(String  v) {
        this.mStatus = v;
    }
    
    /**
     * Get the value of mOrderGuideName.
     * @return value of mOrderGuideName.
     */
    public String getOrderGuideName() {
        return mOrderGuideName;
    }
    
    /**
     * Set the value of mOrderGuideName.
     * @param v  Value to assign to mOrderGuideName.
     */
    public void setOrderGuideName(String  v) {
        this.mOrderGuideName = v;
    }
    
    /**
     * Sets the OrderGuideId field. This field is required to be set in the database.
     *
     * @param pOrderGuideId
     *  int to use to update the field.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
    }

    /**
     * Retrieves the OrderGuideId field.
     *
     * @return
     *  int containing the OrderGuideId field.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
    }


    /**
     * Sets the CatalogId field. This field is required to be set in the database.
     *
     * @param pCatalogId
     *  int to use to update the field.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }

    /**
     * Retrieves the CatalogId field.
     *
     * @return
     *  int containing the CatalogId field.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the OrderGuideTypeCd field. This field is required to be
     * set in the database.
     *
     * @param pOrderGuideTypeCd
     *  String to use to update the field.  */
    public void setOrderGuideTypeCd(String pOrderGuideTypeCd){
        this.mOrderGuideTypeCd = pOrderGuideTypeCd;
    }

    /**
     * Retrieves the OrderGuideTypeCd field.
     *
     * @return
     *  String containing the OrderGuideTypeCd field.
     */
    public String getOrderGuideTypeCd(){
        return mOrderGuideTypeCd;
    }
  
}
