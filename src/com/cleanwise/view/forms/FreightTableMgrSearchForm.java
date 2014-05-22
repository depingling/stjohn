/**
 * Title:        FreightTableMgrSearchForm
 * Description:  This is the Struts ActionForm class for 
 * user management page.
 * Purpose:      Strut support to search for users.      
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
 * @author durval
 */
public final class FreightTableMgrSearchForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";
      
    private FreightTableDataVector _resultList = new FreightTableDataVector();
  
    /**
     * <code>getSearchField</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchField() {
        return (this._searchField);
    }

    /**
     * <code>setSearchField</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchField(String pVal) {
        this._searchField = pVal;
    }

    /**
     * <code>getSearchType</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchType() {
        return (this._searchType);
    }

    /**
     * <code>setSearchType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchType(String pVal) {
        this._searchType = pVal;
    }
    
    /**
     * <code>getResultList</code> method.
     *
     * @return a <code>FreightTableDataVector</code> value
     */
    public FreightTableDataVector getResultList() {
        return (this._resultList);
    }

    /**
     * <code>setResultList</code> method.
     *
     * @param pVal a <code>FreightTableDataVector</code> value
     */
    public void setResultList(FreightTableDataVector pVal) {
        this._resultList = pVal;
    }

    /**
     * <code>getListCount</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getListCount() {
        return (this._resultList.size());
    }

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this._searchField = "";
        this._searchType = "";
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
