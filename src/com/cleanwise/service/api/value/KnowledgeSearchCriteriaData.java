package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;

public class KnowledgeSearchCriteriaData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5134067092990486950L;

    private String _mProductName           = new String("");
    private String _mSkuNum                = new String("");
    private String _mDescription           = new String("");
    private String _mCategoryCd            = new String("");
    private String _mDistributorId         = new String("");
    private String _mManufacturerId        = new String("");
    private String _mUserId                = new String("");
    private String _mKnowledgeStatusCd     = new String("");
    
    private String _mDateRangeBegin   = new String("");
    private String _mDateRangeEnd     = new String("");

    /**
     * Holds value of property storeId.
     */
    private int storeId;
        
      
    public KnowledgeSearchCriteriaData() {
        this._mProductName         = new String("");
        this._mSkuNum              = new String("");
        this._mDescription         = new String("");
        this._mCategoryCd          = new String("");
        this._mDistributorId       = new String("");
        this._mManufacturerId      = new String("");
        this._mKnowledgeStatusCd   = new String("");
        this._mUserId              = new String("");
    
        this._mDateRangeBegin   = new String("");
        this._mDateRangeEnd     = new String("");
          
    }

    /**
     * Creates a new KnowledgeSearchCriteriaData
     *
     * @return
     *  Newly initialized KnowledgeSearchCriteriaData object.
     */
    public static KnowledgeSearchCriteriaData createValue () 
    {
        KnowledgeSearchCriteriaData valueData = new KnowledgeSearchCriteriaData();

        return valueData;
    }
    

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this KnowledgeSearchCriteriaData object
     */
    public String toString()
    {
        
        this._mProductName        = new String("");
        this._mSkuNum             = new String("");
        this._mDescription        = new String("");
        this._mCategoryCd         = new String("");
        this._mDistributorId      = new String("");
        this._mManufacturerId     = new String("");
        this._mKnowledgeStatusCd  = new String("");
        this._mUserId             = new String("");
        
        this._mDateRangeBegin   = new String("");
        this._mDateRangeEnd     = new String("");
          
        
        return "[" + "ProductName=" + _mProductName + ", SkuNum=" + _mSkuNum +
            ", Description=" + _mDescription + ", CategoryCd=" + _mCategoryCd + ", DistributorId=" + _mDistributorId +
            ", ManufacturerId=" + _mManufacturerId + ", KnowledgeStatusCd=" + _mKnowledgeStatusCd +
            ", DateRangeBegin=" + _mDateRangeBegin + ", DateRangeEnd=" + _mDateRangeEnd + 
            "]";
    }
    
    /**
     * <code>getProductName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getProductName() {
        return (this._mProductName);
    }

    /**
     * <code>setProductName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setProductName(String pVal) {
        this._mProductName = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getSkuNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSkuNum() {
        return (this._mSkuNum);
    }

    /**
     * <code>setSkuNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSkuNum(String pVal) {
        this._mSkuNum = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getDescription</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getDescription() {
        return (this._mDescription);
    }

    /**
     * <code>setDescription</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setDescription(String pVal) {
        this._mDescription = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getCategoryCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCategoryCd() {
        return (this._mCategoryCd);
    }

    /**
     * <code>setCategoryCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCategoryCd(String pVal) {
        this._mCategoryCd = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getDistributorId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getDistributorId() {
        return (this._mDistributorId);
    }

    /**
     * <code>setDistributorId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setDistributorId(String pVal) {
        this._mDistributorId = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getManufacturerId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getManufacturerId() {
        return (this._mManufacturerId);
    }

    /**
     * <code>setManufacturerId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setManufacturerId(String pVal) {
        this._mManufacturerId = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getKnowledgeStatusCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getKnowledgeStatusCd() {
        return (this._mKnowledgeStatusCd);
    }

    /**
     * <code>setKnowledgeStatusCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setKnowledgeStatusCd(String pVal) {
        this._mKnowledgeStatusCd = pVal;
        setDirty(true);
    }


    /**
     * <code>getUserId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserId() {
        return (this._mUserId);
    }

    /**
     * <code>setUserId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserId(String pVal) {
        this._mUserId = pVal;
        setDirty(true);
    }
    
    
    
    /**
     * <code>getDateRangeBegin</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getDateRangeBegin() {
        return (this._mDateRangeBegin);
    }

    /**
     * <code>setDateRangeBegin</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setDateRangeBegin(String pVal) {
        this._mDateRangeBegin = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getDateRangeEnd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getDateRangeEnd() {
        return (this._mDateRangeEnd);
    }

    /**
     * <code>setDateRangeEnd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setDateRangeEnd(String pVal) {
        this._mDateRangeEnd = pVal;
        setDirty(true);
    }

    /**
     * Getter for property storeId.
     * @return Value of property storeId.
     */
    public int getStoreId() {

        return this.storeId;
    }

    /**
     * Setter for property storeId.
     * @param storeId New value of property storeId.
     */
    public void setStoreId(int storeId) {

        this.storeId = storeId;
    }
                

}
