/**
 * Title:        ItemMgrDistrForm
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
import java.util.Hashtable;
import java.util.Collection;
import java.util.Vector;



/**
 * Form bean for the user manager page.
 *
 * @author durval
 */
public final class ItemMgrDistrAssignForm extends ActionForm {
  private String _searchField = "";
  private String _searchType = "";
  private int _pageSize=15;
  private String[] _skuNum = new String[_pageSize];
  private String[] _uom = new String[_pageSize];
  private String[] _productUom = new String[_pageSize];
  private String[] _pack = new String[_pageSize];
  private boolean _containsFlag = false;
  private int _offset=0;
  private String _feedField = "";
  private Hashtable _retObject = null;
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
   * <code>setPageSize</code> method.
   * @param pVal a <code>List Length</code>
   */
  public void setPageSize(int pValue) {
    _pageSize=pValue;
    _skuNum = new String[pValue];
  }
  /**
   * <code>getPageSize</code> method.
   * @return a <code>int</code> value
   */
  public int getPageSize() {return _pageSize;};


  public void setSkuNum(String[] pValue){_skuNum=pValue;}
  public String[] getSkuNum() { return _skuNum;}

  public void setUom(String[] pValue){_uom=pValue;}
  public String[] getUom() { return _uom;}
  
  public void setProductUom(String[] pValue){_productUom=pValue;}
  public String[] getProductUom() { return _productUom;}

  public void setPack(String[] pValue){_pack=pValue;}
  public String[] getPack() { return _pack;}
   
  public void setOffset(int pValue){_offset=pValue;}
  public int getOffset() { return _offset;}

  public void setRetObject(Hashtable pValue){_retObject=pValue;}
  public Hashtable getRetObject() { return _retObject;}

  public void setSkuNumElement(int pIndex, String pValue){
    if(pIndex<_pageSize) {
      _skuNum[pIndex]=pValue;
    }
  }

  public String getSkuNumElement(int pIndex) {
    if(pIndex<_pageSize) {
      return _skuNum[pIndex];
    } else {
      return null;
    }

  }

  public void setUomElement(int pIndex, String pValue){
    if(pIndex<_pageSize) {
      _uom[pIndex]=pValue;
    }
  }

  public String getUomElement(int pIndex) {
    if(pIndex<_pageSize) {
      return _uom[pIndex];
    } else {
      return null;
    }

  }

  public void setProductUomElement(int pIndex, String pValue){
    if(pIndex<_pageSize) {
      _productUom[pIndex]=pValue;
    }
  }

  public String getProductUomElement(int pIndex) {
    if(pIndex<_pageSize) {
      return _productUom[pIndex];
    } else {
      return null;
    }

  }

  public void setPackElement(int pIndex, String pValue){
    if(pIndex<_pageSize) {
      _pack[pIndex]=pValue;
    }
  }

  public String getPackElement(int pIndex) {
    if(pIndex<_pageSize) {
      return _pack[pIndex];
    } else {
      return null;
    }

  }

  
  
  public LinkedList getPages() {
    LinkedList pages = new LinkedList();
    if(_resultList!=null) {
      for(int jj=0,ii=0; ii<_resultList.size(); ii+=_pageSize, jj++) {
        pages.add(new Integer(jj));
      }
    }
    return pages;
  }

  public int getCurrentPage() {
    int currentPage = _offset/_pageSize;
    return currentPage;
  }

  public void setFeedField (String pValue){_feedField = pValue;}
  public String getFeedField() {return _feedField;}

  /**
   * <code>reset</code> method, set the search fiels to null.
   *
   */
  public void reset() {
    this._searchField = "";
    this._searchType = "";
    this._containsFlag = false;
    this._resultList = new LinkedList();
    _skuNum = new String[_pageSize];
    _offset =0;
    _retObject = new Hashtable();
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

