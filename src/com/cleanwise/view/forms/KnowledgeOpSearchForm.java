/**
 * Title:        KnowledgeOpSearchForm
 * Description:  This is the Struts ActionForm class for 
 * knowledge tracking operation console page.
 * Purpose:      Strut support to search for knowledges.      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang
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
public final class KnowledgeOpSearchForm extends ActionForm {

      
    private KnowledgeDescDataVector _resultList = new KnowledgeDescDataVector();

    private String _mProductName            = new String("");
    private String _mSkuNum                 = new String("");
    private String _mDescription            = new String("");
    private String _mCategoryCd             = new String("");
    private String _mDistributorId          = new String("");
    private String _mManufacturerId         = new String("");
    private String _mKnowledgeStatusCd      = new String("");
    
    private String _mDateRangeBegin    = new String("");
    private String _mDateRangeEnd      = new String("");
        
  
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
    }

    
    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>KnowledgeDescDataVector</code> value
     */
    public KnowledgeDescDataVector getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>KnowledgeDescDataVector</code> value
     */
    public void setResultList(KnowledgeDescDataVector pVal) {
        this._resultList = pVal;
    }

        
    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        if (null == this._resultList) {
            this._resultList = new KnowledgeDescDataVector();
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
