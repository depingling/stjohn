
package com.cleanwise.service.api.value;

/**
 * Title: ContractDescData Description:
 * This is a ValueObject class describbing an order guide.
 * Copyright (c) 2001 Company: CleanWise, Inc.
 * @author       dvieira
*/
import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>ContractDescData</code> 
 */
public class ContractDescData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5341285624044732205L;
    
    private int    mContractId;
    private String mContractName;
    private String mStatus;
    private int    mCatalogId;
    private String mCatalogName;

    /**
     * Constructor.
     */
    private ContractDescData ()
    {
        mContractName = "";
        mStatus = "";
        mCatalogName = "";
    }

    /**
     * Constructor. 
     */
    public ContractDescData
        (     int    pContractId,
              String pContractName,
              String pStatus,
              int    pCatalogId,
              String pCatalogName
              )
    {
        mContractId   = pContractId;
        mContractName =  pContractName;
        mStatus = mStatus;
        mCatalogId = pCatalogId;
        mCatalogName = pCatalogName;
    }

    /**
     * Creates a new ContractDescData
     *
     * @return
     *  Newly initialized ContractDescData object.
     */
    public static ContractDescData createValue () 
    {
        ContractDescData valueData = new ContractDescData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractDescData object
     */
    public String toString()
    {
        return "[" + 
            "ContractId=" + mContractId + 
            ", ContractName=" + mContractName + 
            ", Status=" + mStatus + 
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
     * Get the value of mContractName.
     * @return value of mContractName.
     */
    public String getContractName() {
        return mContractName;
    }
    
    /**
     * Set the value of mContractName.
     * @param v  Value to assign to mContractName.
     */
    public void setContractName(String  v) {
        this.mContractName = v;
    }
    
    /**
     * Sets the ContractId field. This field is required to be set in the database.
     *
     * @param pContractId
     *  int to use to update the field.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }

    /**
     * Retrieves the ContractId field.
     *
     * @return
     *  int containing the ContractId field.
     */
    public int getContractId(){
        return mContractId;
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

  
}
