/**
 * Title:        ContractMgrDetail
 * Description:  This is the Struts ActionForm class for the contract detail page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author
 */

package com.cleanwise.view.forms;

import java.util.Vector;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;


/**
 * Form bean for the add/edit contract page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>detail</b> - A BusEntityData object
 * </ul>
 */
public final class StoreItemMgrSearchForm extends StorePortalBaseForm {


  // -------------------------------------------------------- Instance Variables
    private int _catalogId=0;
    private String _searchType="";
    private Vector _catalogTree = new Vector();
    private String _skuTempl="";
    private String _skuType="SystemCustomer";
    private String _shortDescTempl="";
    private String _longDescTempl="";
    private String _manuId="";
    private String _manuName="";
    private String _distributorId="";
    private String _distributorName="";
    private String _categoryTempl="";
    private String _sizeTempl="";
    private String _itemPropertyName="";
    private String _itemPropertyTempl="";
    private String _whereToSearch="this";
    private String _resultSource="";
    private String _outServiceName = "";
    private IdVector _listIds=new IdVector();
    private ProductDataVector _products=new ProductDataVector();
    private DistItemViewVector _distItems = new DistItemViewVector();
    private String[] _selectorBox=new String[0];
    private IdVector _selectedProductIds=new IdVector();

    private String _mfg1Id = "";
    private String _mfg1Name = "";
    private String _mfg1ItemSku = "";

    private String _returnFormNum = null;
    private String _feedField = null;
    private String _fieldDesc = null;

    public String getReturnFormNum() { return _returnFormNum; }
    public void setReturnFormNum(String val) { _returnFormNum = val; }

    public String getFeedField() { return _feedField; }
    public void setFeedField(String val) { _feedField = val; }

    public String getFieldDesc() { return _fieldDesc; }
    public void setFieldDesc(String val) { _fieldDesc = val; }



  // ---------------------------------------------------------------- Properties
    LinkedList CategoryTree;
    int SelectedCategoryId = 0;
    /**
     * Holds value of property ERPEnabled.
     */
    private boolean ERPEnabled;


    private ManufacturerDataVector mManufFilter;
    private String[] mSelectedLines = new String[0];


    /**
     * Get the value of SelectedCategoryId.
     * @return value of SelectedCategoryId.
     */
    public int getSelectedCategoryId() {
  return SelectedCategoryId;
    }

    /**
     * Set the value of SelectedCategoryId.
     * @param v  Value to assign to SelectedCategoryId.
     */
    public void setSelectedCategoryId(int  v) {
  this.SelectedCategoryId = v;
    }

    /**
     * Get the value of CategoryTree.
     * @return value of CategoryTree.
     */
    public LinkedList getCategoryTree() {
  return CategoryTree;
    }

    /**
     * Set the value of CategoryTree.
     * @param v  Value to assign to CategoryTree.
     */
    public void setCategoryTree(LinkedList  v) {
  this.CategoryTree = v;
    }

    public void setCatalogId(int pValue) {_catalogId=pValue;}
    public int getCatalogId() {return _catalogId;}

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

    public void setCatalogTree(Vector pValue) {_catalogTree=pValue;}
    public Vector getCatalogTree(){return _catalogTree;}

    public void setSizeTempl(String pValue) {_sizeTempl=pValue;}
    public String getSizeTempl(){return _sizeTempl;}

    public void setItemPropertyName(String pValue) {_itemPropertyName=pValue;}
    public String getItemPropertyName(){return _itemPropertyName;}

    public void setItemPropertyTempl(String pValue) {_itemPropertyTempl=pValue;}
    public String getItemPropertyTempl(){return _itemPropertyTempl;}

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

    public int getListCount() {
      return (_listIds.size());
    }

    public void setListIds(IdVector pValue) {_listIds=pValue;}
    public IdVector getListIds(){return _listIds;}

    public int getListSize(){return _listIds.size();}

    public void setProducts(ProductDataVector pValue) {_products=pValue;}
    public ProductDataVector getProducts(){return _products;}

    public void setDistItems(DistItemViewVector pValue) {_distItems=pValue;}
    public DistItemViewVector getDistItems(){return _distItems;}

    public void setWhereToSearch(String pValue) {_whereToSearch=pValue;}
    public String getWhereToSearch(){return _whereToSearch;}

    public void setResultSource(String pValue) {_resultSource=pValue;}
    public String getResultSource(){return _resultSource;}

    public void setOutServiceName(String pValue) {_outServiceName=pValue;}
    public String getOutServiceName(){return _outServiceName;}
    public void setMfg1Id(String pValue) {_mfg1Id=pValue;}
    public String getMfg1Id(){return _mfg1Id;}
/*
    public void setMfg1IdEl(int index, String pValue)
    {
      if(_mfg1Ids==null || index<0 || index>=_mfg1Ids.length) return;
      _mfg1Ids[index] = pValue;
    }
    public String getMfg1IdEl(int index){
      if(_mfg1Ids==null || index<0 || index>=_mfg1Ids.length) return "";
      return _mfg1Ids[index];
    }
*/
    public void setMfg1Name(String pValue) {_mfg1Name=pValue;}
    public String getMfg1Name(){return _mfg1Name;}
/*
    public void setMfg1NameEl(int index, String pValue)
    {
      if(_mfg1Names==null || index<0 || index>=_mfg1Names.length) return;
      _mfg1Names[index] = pValue;
    }
    public String getMfg1NameEl(int index){
      if(_mfg1Names==null || index<0 || index>=_mfg1Names.length) return "";
      return _mfg1Names[index];
    }
*/
    public void setMfg1ItemSku(String pValue) {_mfg1ItemSku=pValue;}
    public String getMfg1ItemSku(){return _mfg1ItemSku;}
/*
    public void setMfg1SkuEl(int index, String pValue)
    {
      if(_mfg1Skus==null || index<0 || index>=_mfg1Skus.length) return;
      _mfg1Names[index] = pValue;
    }
    public String getMfg1SkuEl(int index){
      if(_mfg1Skus==null || index<0 || index>=_mfg1Skus.length) return "";
      return _mfg1Names[index];
    }
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

    /**
     * Getter for property ERPEnabled.
     * @return Value of property ERPEnabled.
     */
    public boolean isERPEnabled() {

        return this.ERPEnabled;
    }

    /**
     * Setter for property ERPEnabled.
     * @param ERPEnabled New value of property ERPEnabled.
     */
    public void setERPEnabled(boolean ERPEnabled) {

        this.ERPEnabled = ERPEnabled;
    }


    public ManufacturerDataVector getManufFilter() {return mManufFilter;}
    public void setManufFilter(ManufacturerDataVector pVal) {mManufFilter = pVal;}

    private ManufacturerDataVector mStoreManufacturers = new ManufacturerDataVector();

    public  ManufacturerDataVector getStoreManufacturers() {return mStoreManufacturers;}
    public void setStoreManufacturers( ManufacturerDataVector pVal) {mStoreManufacturers = pVal;}

    public String[] getSelectedLines() {return mSelectedLines;}
    public void setSelectedLines(String[] pVal) {mSelectedLines = pVal;}


}
