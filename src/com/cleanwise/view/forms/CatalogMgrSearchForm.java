/**
 * Title:        CatalogMgrSearchForm
 * Description:  This is the Struts ActionForm class for 
 * user management page.
 * Purpose:      Strut support to search for users.      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.util.List;
import java.util.LinkedList;


/**
 * Form bean for the user manager page. 
 *
 * @author durval
 */
public final class CatalogMgrSearchForm extends ActionForm {
  private String _searchField = "";
  private String _searchType = "catalogNameStarts";
  private boolean _containsFlag = false;

      
  private List _resultList = new LinkedList();
  
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
     * <code>isContainsFlag</code> method.
     *
     * @return a <code>boolean</code> value
     */
    public boolean isContainsFlag() {
	return (this._containsFlag);
    }

    /**
     * <code>setContainsFlag</code> method.
     *
     * @param pVal a <code>boolean</code> value
     */
    public void setContainsFlag(boolean pVal) {
	this._containsFlag = pVal;
    }

    
  /**
   * <code>getResultList</code> method.
   *
   * @return a <code>List</code> value
   */
  public List getResultList() {
    return (this._resultList);
  }

  /**
   * <code>setResultList</code> method.
   *
   * @param pVal a <code>List</code> value
   */
  public void setResultList(List pVal) {
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
    this._searchType = "catalogNameStarts";
    this._containsFlag = false;

    //    this._resultList = new LinkedList();
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

