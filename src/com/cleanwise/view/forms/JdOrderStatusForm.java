/**
 * Title:        JdOrderStatusForm
 * Description:  This is the Struts ActionForm class for 
 * order operation console page.
 * Purpose:      Strut support to search for Jd orders.      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuiry Kupershmidt
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
public final class JdOrderStatusForm extends ActionForm {

    private JdOrderStatusViewVector _resultList = new JdOrderStatusViewVector();
    private boolean _overflowFl = true;
    private String _mWebOrderConfirmationNum     = new String("");
    private String _mOrderDateRangeBegin = new String("");
    private String _mOrderDateRangeEnd   = new String("");
    private String _mCustPONum           = new String("");
    private String _mSiteCity            = new String("");
    private String _mSiteState           = new String("");
    private String _mSiteZipCode         = new String("");
    
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
     * <code>getSiteCity</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteCity() {
        return (this._mSiteCity);
    }

    /**
     * <code>setSiteCity</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteCity(String pVal) {
        this._mSiteCity = pVal;
    }

    
    /**
     * <code>getSiteState</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSiteState() {
        return (this._mSiteState);
    }

    /**
     * <code>setSiteState</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSiteState(String pVal) {
        this._mSiteState = pVal;
    }

    
    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>JdOrderStatusViewVector</code> value
     */
    public JdOrderStatusViewVector getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>JdOrderStatusViewVector</code> value
     */
    public void setResultList(JdOrderStatusViewVector pVal) {
        this._resultList = pVal;
    }

    /**
     * <code>getOverflowFl</code> method.
     *
     * @return a <code>boolean</code> value
     */
    public boolean getOverflowFl() {
        return (this._overflowFl);
    }

    /**
     * <code>setOverflowFl</code> method.
     *
     * @param pVal a <code>boolean</code> value
     */
    public void setOverflowFl(boolean pVal) {
        this._overflowFl = pVal;
    }

    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        if (null == this._resultList) {
            this._resultList = new JdOrderStatusViewVector();
        }
        return (this._resultList.size());
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
