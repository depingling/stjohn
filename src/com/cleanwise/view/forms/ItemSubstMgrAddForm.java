/**
 * Title:        Catalog Item Search
 * Description:  This is the Struts ActionForm class for the itemSubstMgrAdd.jsp
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Collection;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Store;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.DataNotFoundException;


/**
 * Form bean for the add/edit contract page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>detail</b> - A BusEntityData object
 * </ul>
 */
public final class ItemSubstMgrAddForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
    private int _catalogId=0;
    private ProductData _sourceProduct = new ProductData();
    private String _searchType="";
    private String _skuTempl="";
    private String _skuType="System";
    private String _shortDescTempl="";
    private String _longDescTempl="";
    private String _manuId="";
    private String _manuName="";
    private String _distributorId="";
    private String _distributorName="";
    private String _categoryTempl="";
    private String _sizeTempl="";
    private String _resultSource="";
    private String _selectedItemIdS=null;
    private String _conversionFactorS="1.00";
//    private CatalogData _superCatalog = null;

    private int _pageSize=15;
    private IdVector _listIds=new IdVector();
//    private IdVector _filterIds = new IdVector();
    private ProductDataVector _products=new ProductDataVector();
    private String[] _selectorBox=new String[0];
    private IdVector _selectedProductIds=new IdVector();

    private String _feedField = "";
    private ProductDataVector _retObject = null;
    private int _offset=0;

  // ---------------------------------------------------------------- Properties
    public void setCatalogId(int pValue) {_catalogId=pValue;}
    public int getCatalogId() {return _catalogId;}

    public void setSourceProduct(ProductData pValue) {_sourceProduct=pValue;}
    public ProductData getSourceProduct() {return _sourceProduct;}

    public void setSearchType(String pValue) {_searchType=pValue;}
    public String getSearchType(){return _searchType;}

    public void setSkuTempl(String pValue) {_skuTempl=pValue;}
    public String getSkuTempl(){return _skuTempl;}

    public void setSkuType(String pValue) {_skuType=pValue;}
    public String getSkuType(){return _skuType;}

    public void setShortDescTempl(String pValue) {_shortDescTempl=pValue;}
    public String getShortDescTempl(){return _shortDescTempl;}

    public void setLongDescTempl(String pValue) {_longDescTempl=pValue;}
    public String getLongDescTempl(){return _longDescTempl;}

    public void setCategoryTempl(String pValue) {_categoryTempl=pValue;}
    public String getCategoryTempl(){return _categoryTempl;}

//    public void setCatalogTree(Vector pValue) {_catalogTree=pValue;}
//    public Vector getCatalogTree(){return _catalogTree;}

    public void setSizeTempl(String pValue) {_sizeTempl=pValue;}
    public String getSizeTempl(){return _sizeTempl;}

    public void setManuId(String pValue) {
      _manuId = pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _manuName = "";
      }
    }
    public String getManuId(){return _manuId;}

    public void setManuName(String pValue) {_manuName = pValue;}
    public String getManuName(){return _manuName;}

    public void setDistributorId(String pValue) {
      _distributorId=pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _distributorName="";
      }
    }
    public String getDistributorId(){return _distributorId;}

    public void setDistributorName(String pValue) {_distributorName = pValue;}
    public String getDistributorName(){return _distributorName;}


    public void setSelectorBox(String[] pValue) {_selectorBox=pValue;}
    public String[] getSelectorBox(){return _selectorBox;}

    public void setSelectedProductIds(IdVector pValue) {_selectedProductIds=pValue;}
    public IdVector getSelectedProductIds(){return _selectedProductIds;}

    public void setPageSize(int pValue) {_pageSize=pValue;}
    public int getPageSize(){return _pageSize;}

    public int getCurrentPage() {
      int currentPage = _offset/_pageSize;
      return currentPage;
    }

    public void setListIds(IdVector pValue) {_listIds=pValue;}
    public IdVector getListIds(){return _listIds;}

    public int getListSize(){return _listIds.size();}

//    public void setFilterIds(IdVector pValue) {_filterIds=pValue;}
//    public IdVector getFilterIds(){return _filterIds;}

    public void setProducts(ProductDataVector pValue) {_products=pValue;}
    public ProductDataVector getProducts(){return _products;}

    public void setResultSource(String pValue) {_resultSource=pValue;}
    public String getResultSource(){return _resultSource;}

    public void setRetObject(ProductDataVector pValue){_retObject=pValue;}
    public ProductDataVector getRetObject() { return _retObject;}

    public void setOffset(int pValue){_offset=pValue;}
    public int getOffset() { return _offset;}

    public void setFeedField (String pValue){_feedField = pValue;}
    public String getFeedField() {return _feedField;}

    public void setSelectedItemIdS(String pValue) {_selectedItemIdS=pValue;}
    public String getSelectedItemIdS(){return _selectedItemIdS;}

    public void setConversionFactorS(String pValue) {_conversionFactorS=pValue;}
    public String getConversionFactorS(){return _conversionFactorS;}
    
    //    public void setSuperCatalog(CatalogData pValue) {_superCatalog=pValue;}
//    public CatalogData getSuperCatalog(){return _superCatalog;}

    public int getListCount() {
      return (_listIds.size());
    }

    public LinkedList getPages() {
      LinkedList pages = new LinkedList();
      if(_listIds!=null) {
        for(int jj=0,ii=0; ii<_listIds.size(); ii+=_pageSize, jj++) {
        pages.add(new Integer(jj));
        }
      }
      return pages;
    }

/*
    public void set(String pValue) {_=pValue;}
    public String get(){return _;}
*/
  /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {

    // create a new detail object and convert nulls to empty strings
  }


  /**
   * So far nothing to validate
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {

    return null;

  }

}
