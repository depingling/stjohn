/**
 * Title:        CallOpSearchForm
 * Description:  This is the Struts ActionForm class for 
 * call tracking operation console page.
 * Purpose:      Strut support to search for calls.      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.LinkedList;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 * Form bean for the user manager page. 
 *
 * @author Liang
 */
public final class CallOpSearchForm extends ActionForm {

      
    private CallDescDataVector _resultList = new CallDescDataVector();
    private UserDataVector _mCustomerServiceUserList = new UserDataVector();
    
    private String _mAccountName           = new String("");
    private String _mAccountIdList         = new String("");
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
    }
    
    /**
     * <code>getAccountIdList</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getAccountIdList() {
        return (this._mAccountIdList);
    }

    /**
     * <code>setAccountIdList</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setAccountIdList(String pVal) {
        this._mAccountIdList = pVal;
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
    }

    
    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>CallDescDataVector</code> value
     */
    public CallDescDataVector getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>CallDescDataVector</code> value
     */
    public void setResultList(CallDescDataVector pVal) {
        this._resultList = pVal;
    }

        
    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        if (null == this._resultList) {
            this._resultList = new CallDescDataVector();
        }
        return (this._resultList.size());
    }

    
    /**
     * <code>getCustomerServiceUserList</code> method.
     *
     * @return a <code>UserDataVector</code> value
     */
    public UserDataVector getCustomerServiceUserList() {
        return (this._mCustomerServiceUserList);
    }

    /**
     * <code>setCustomerServiceUserList</code> method.
     *
     * @param pVal a <code>UserDataVector</code> value
     */
    public void setCustomerServiceUserList(UserDataVector pVal) {
        this._mCustomerServiceUserList = pVal;
    }

    
    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

    }


    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

}
