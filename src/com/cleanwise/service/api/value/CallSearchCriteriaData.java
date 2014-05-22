package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;

public class CallSearchCriteriaData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 7781461500291599635L;

    private String _mAccountName           = new String("");
    private IdVector _mAccountIdVector     = new IdVector();
    private String _mSiteName              = new String("");
    private String _mContactName           = new String("");
    private String _mContactPhone          = new String("");
    private String _mContactEmail          = new String("");
    private String _mProductName           = new String("");
    private String _mCustomerField1        = new String("");
    private String _mSiteZipCode           = new String("");
    
    private String _mErpOrderNum           = new String("");
    private String _mWebOrderConfirmationNum     = new String("");
    private String _mCustPONum             = new String("");
    private String _mErpPONum              = new String("");
    
    private String _mCallTypeCd            = new String("");
    private String _mCallSeverityCd        = new String("");
    private String _mOpenedById            = new String("");
    private String _mAssignedToId          = new String("");
    private String _mCallStatusCd          = new String("");
    
    private String _mOrderDateRangeBegin   = new String("");
    private String _mOrderDateRangeEnd     = new String("");
    private String _mSiteData              = new String("");
        
      
    public CallSearchCriteriaData() {
        this._mAccountName           = new String("");
        this._mAccountIdVector       = new IdVector();
        this._mSiteName              = new String("");
        this._mContactName           = new String("");
        this._mContactPhone          = new String("");
        this._mContactEmail          = new String("");
        this._mProductName           = new String("");
        this._mCustomerField1        = new String("");
        this._mSiteZipCode           = new String("");
    
        this._mErpOrderNum           = new String("");
        this._mWebOrderConfirmationNum     = new String("");
        this._mCustPONum             = new String("");
        this._mErpPONum              = new String("");
    
        this._mCallTypeCd            = new String("");
        this._mCallSeverityCd        = new String("");
        this._mOpenedById            = new String("");
        this._mAssignedToId          = new String("");
        this._mCallStatusCd          = new String("");
    
        this._mOrderDateRangeBegin   = new String("");
        this._mOrderDateRangeEnd     = new String("");
        this._mSiteData              = new String("");  
    }

    /**
     * Creates a new CallSearchCriteriaData
     *
     * @return
     *  Newly initialized CallSearchCriteriaData object.
     */
    public static CallSearchCriteriaData createValue () 
    {
        CallSearchCriteriaData valueData = new CallSearchCriteriaData();

        return valueData;
    }
    

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CallSearchCriteriaData object
     */
    public String toString()
    {
        
        this._mAccountName           = new String("");
        this._mAccountIdVector       = new IdVector();
        this._mSiteName              = new String("");
        this._mContactName           = new String("");
        this._mContactPhone          = new String("");
        this._mContactEmail          = new String("");
        this._mProductName           = new String("");
        this._mCustomerField1        = new String("");
        this._mSiteZipCode           = new String("");
    
        this._mErpOrderNum           = new String("");
        this._mWebOrderConfirmationNum     = new String("");
        this._mCustPONum             = new String("");
        this._mErpPONum              = new String("");
    
        this._mCallTypeCd            = new String("");
        this._mCallSeverityCd        = new String("");
        this._mOpenedById            = new String("");
        this._mAssignedToId          = new String("");
        this._mCallStatusCd          = new String("");
    
        this._mOrderDateRangeBegin   = new String("");
        this._mOrderDateRangeEnd     = new String("");
        this._mSiteData              = new String("");
          
        
        return "[" + "AccountName=" + _mAccountName + ", AccountIdVector="+ _mAccountIdVector+
                ", SiteName=" + _mSiteName +
            ", ContactName=" + _mContactName + ", ContactPhone=" + _mContactPhone + ", ContactEmail=" + _mContactEmail +
            ", ProductName=" + _mProductName + ", CustomerField1=" + _mCustomerField1 + ", SiteZipCode=" + _mSiteZipCode +
            ", ErpOrderNum=" + _mErpOrderNum + ", ErpPONum=" + _mErpPONum + ", WebOrderConfirmationNum=" + _mWebOrderConfirmationNum + 
            ", CustPONum=" + _mCustPONum + ", CallTypeCd=" + _mCallTypeCd + ", CallSeverityCd=" + _mCallSeverityCd +
            ", OpenedById=" + _mOpenedById + ", AssignedToId=" + _mAssignedToId + ", CallStatusCd=" + _mCallStatusCd +
            ", OrderDateRangeBegin=" + _mOrderDateRangeBegin + ", OrderDateRangeEnd=" + _mOrderDateRangeEnd + ", SiteData=" + _mSiteData + 
            "]";
    }
    
    /**
     * <code>getAccountName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getAccountName() {
        return (this._mAccountName);
    }

    /**
     * <code>setAccountName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setAccountName(String pVal) {
        this._mAccountName = pVal;
        setDirty(true);
    }
    
    /**
     * <code>getAccountIdVector</code> method.
     *
     * @return a <code>IdVector</code> value
     */
    public IdVector getAccountIdVector() {
        return (this._mAccountIdVector);
    }

    /**
     * <code>setAccountIdVector</code> method.
     *
     * @param pVal a <code>IdVector</code> value
     */
    public void setAccountIdVector(IdVector pVal) {
        this._mAccountIdVector = pVal;
        setDirty(true);
    }

    /**
     * <code>getSiteName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteName() {
        return (this._mSiteName);
    }

    /**
     * <code>setSiteName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteName(String pVal) {
        this._mSiteName = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getContactName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getContactName() {
        return (this._mContactName);
    }

    /**
     * <code>setContactName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setContactName(String pVal) {
        this._mContactName = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getContactPhone</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getContactPhone() {
        return (this._mContactPhone);
    }

    /**
     * <code>setContactPhone</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setContactPhone(String pVal) {
        this._mContactPhone = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getContactEmail</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getContactEmail() {
        return (this._mContactEmail);
    }

    /**
     * <code>setContactEmail</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setContactEmail(String pVal) {
        this._mContactEmail = pVal;
        setDirty(true);
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
     * <code>getCustomerField1</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCustomerField1() {
        return (this._mCustomerField1);
    }

    /**
     * <code>setCustomerField1</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCustomerField1(String pVal) {
        this._mCustomerField1 = pVal;
        setDirty(true);
    }
    

    /**
     * <code>getSiteZipCode</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteZipCode() {
        return (this._mSiteZipCode);
    }

    /**
     * <code>setSiteZipCode</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteZipCode(String pVal) {
        this._mSiteZipCode = pVal;
        setDirty(true);
    }
    
    
    /**
     * <code>getErpOrderNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getErpOrderNum() {
        return (this._mErpOrderNum);
    }

    /**
     * <code>setErpOrderNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setErpOrderNum(String pVal) {
        this._mErpOrderNum = pVal;
        setDirty(true);
    }


    /**
     * <code>getWebOrderConfirmationNum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getWebOrderConfirmationNum() {
        return (this._mWebOrderConfirmationNum);
    }

    /**
     * <code>setWebOrderConfirmationNum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setWebOrderConfirmationNum(String pVal) {
        this._mWebOrderConfirmationNum = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getCustPONum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCustPONum() {
        return (this._mCustPONum);
    }

    /**
     * <code>setCustPONum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCustPONum(String pVal) {
        this._mCustPONum = pVal;
        setDirty(true);
    }

        
    /**
     * <code>getErpPONum</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getErpPONum() {
        return (this._mErpPONum);
    }

    /**
     * <code>setErpPONum</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setErpPONum(String pVal) {
        this._mErpPONum = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getCallTypeCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCallTypeCd() {
        return (this._mCallTypeCd);
    }

    /**
     * <code>setCallTypeCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCallTypeCd(String pVal) {
        this._mCallTypeCd = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getCallSeverityCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCallSeverityCd() {
        return (this._mCallSeverityCd);
    }

    /**
     * <code>setCallSeverityCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCallSeverityCd(String pVal) {
        this._mCallSeverityCd = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getOpenedById</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOpenedById() {
        return (this._mOpenedById);
    }

    /**
     * <code>setOpenedById</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOpenedById(String pVal) {
        this._mOpenedById = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getAssignedToId</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getAssignedToId() {
        return (this._mAssignedToId);
    }

    /**
     * <code>setAssignedToId</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setAssignedToId(String pVal) {
        this._mAssignedToId = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getCallStatusCd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCallStatusCd() {
        return (this._mCallStatusCd);
    }

    /**
     * <code>setCallStatusCd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCallStatusCd(String pVal) {
        this._mCallStatusCd = pVal;
        setDirty(true);
    }

    
    
    /**
     * <code>getOrderDateRangeBegin</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderDateRangeBegin() {
        return (this._mOrderDateRangeBegin);
    }

    /**
     * <code>setOrderDateRangeBegin</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderDateRangeBegin(String pVal) {
        this._mOrderDateRangeBegin = pVal;
        setDirty(true);
    }

    
    /**
     * <code>getOrderDateRangeEnd</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getOrderDateRangeEnd() {
        return (this._mOrderDateRangeEnd);
    }

    /**
     * <code>setOrderDateRangeEnd</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setOrderDateRangeEnd(String pVal) {
        this._mOrderDateRangeEnd = pVal;
        setDirty(true);
    }

     /**
     * <code>getSiteData</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteData() {
        return (this._mSiteData);
    }

    /**
     * <code>setSiteData</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteData(String pVal) {
        this._mSiteData = pVal;
        setDirty(true);
    }
                

}
