/*
 * LocateStoreItemForm.java
 *
 * Created on May 9, 2005, 11:14 AM
 */
package com.cleanwise.view.forms;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreItemForm  extends LocateStoreBaseForm {
    private StoreData _store=null;
    private StoreData _loginStore=null;
    private String storeId = "";
    private String _searchType="";
    private String _searchNumType = "nameBegins";
    private String _skuTempl="";
    private String _skuType = SearchCriteria.STORE_SKU_NUMBER;
    private String _shortDescTempl="";
    private String _longDescTempl="";
    private String _manuId="";
    private String _manuNameTempl="";
    private String _distId="";
    private String _distNameTempl="";
    private String _categoryTempl="";
    private String _sizeTempl="";
    private String _itemPropertyName="";
    private String _itemPropertyTempl="";
    private String _whereToSearch="this";
    private String _resultSource="";
    private String _outServiceName = "";
    private DistItemViewVector _distItems = new DistItemViewVector();
    private boolean _distInfoRequest = false;
    private boolean _distInfoFlag = false;

    private boolean _showInactiveFl=false;

    private int[] _selectedItemIds=new int[0];
    private ItemViewVector _itemsSelected = null;
    private ItemViewVector _itemsToReturn = null;

    private String _selectedCatalogList = "";
    private boolean _searchInSelectedCatalogs = false;
  private String dataSourceType;

  ///////////////////////////////////////
    public void setStore(StoreData pValue) {_store=pValue;}
    public StoreData getStore(){return _store;}

    public void setLoginStore(StoreData pValue) {_loginStore=pValue;}
    public StoreData getLoginStore(){return _loginStore;}

    public void setStoreId(String pValue) {storeId=pValue;}

  public void setDataSourceType(String dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  public String getStoreId(){return storeId;}

  public String getDataSourceType() {
    return dataSourceType;
  }

  public void setSearchType(String pValue) {_searchType=pValue;}
    public String getSearchType(){return _searchType;}

    public void setSkuTempl(String pValue) {_skuTempl=pValue;}
    public String getSkuTempl(){return _skuTempl;}

    public void setSkuType(String pValue) {_skuType=pValue;}
    public String getSkuType(){return _skuType;}
    
    public void setSearchNumType(String pValue) {_searchNumType=pValue;}
    public String getSearchNumType(){return _searchNumType;}

    public void setShortDescTempl(String pValue) {_shortDescTempl=pValue;}
    public String getShortDescTempl(){return _shortDescTempl;}

    public void setLongDescTempl(String pValue) {_longDescTempl=pValue;}
    public String getLongDescTempl(){return _longDescTempl;}

    public void setCategoryTempl(String pValue) {_categoryTempl=pValue;}
    public String getCategoryTempl(){return _categoryTempl;}

    public void setSizeTempl(String pValue) {_sizeTempl=pValue;}
    public String getSizeTempl(){return _sizeTempl;}

    public void setItemPropertyName(String pValue) {_itemPropertyName=pValue;}
    public String getItemPropertyName(){return _itemPropertyName;}

    public void setItemPropertyTempl(String pValue) {_itemPropertyTempl=pValue;}
    public String getItemPropertyTempl(){return _itemPropertyTempl;}

    public void setManuId(String pValue) {
      _manuId = pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _manuNameTempl = "";
      }
    }
    public String getManuId(){return _manuId;}

    public void setManuNameTempl(String pValue) {_manuNameTempl = pValue;}
    public String getManuNameTempl(){return _manuNameTempl;}

    public void setDistId(String pValue) {
      _distId=pValue;
      if(pValue==null || pValue.trim().length()==0) {
        _distNameTempl="";
      }
    }
    public String getDistId(){return _distId;}

    public void setDistNameTempl(String pValue) {_distNameTempl = pValue;}
    public String getDistNameTempl(){return _distNameTempl;}

    public void setDistItems(DistItemViewVector pValue) {_distItems=pValue;}
    public DistItemViewVector getDistItems(){return _distItems;}

    public void setWhereToSearch(String pValue) {_whereToSearch=pValue;}
    public String getWhereToSearch(){return _whereToSearch;}

    public void setResultSource(String pValue) {_resultSource=pValue;}
    public String getResultSource(){return _resultSource;}

    public void setOutServiceName(String pValue) {_outServiceName=pValue;}
    public String getOutServiceName(){return _outServiceName;}

    public void setDistInfoFlag(boolean pValue) {_distInfoFlag=pValue;}
    public boolean getDistInfoFlag(){return _distInfoFlag;}

    public int[] getSelectedItemIds() {return _selectedItemIds;}
    public void setSelectedItemIds(int[] pVal) {_selectedItemIds = pVal;}

    public boolean getDistInfoRequest() {return _distInfoRequest;}
    public void setDistInfoRequest(boolean pVal) {_distInfoRequest = pVal;}

    public ItemViewVector getItemsSelected() {return _itemsSelected;}
    public void setItemsSelected(ItemViewVector pVal) {_itemsSelected = pVal;}
    public int getListCount() {
      if(_itemsSelected==null) return 0;
      return _itemsSelected.size();
    }

    public ItemViewVector getItemsToReturn() {return _itemsToReturn;}
    public void setItemsToReturn(ItemViewVector pVal) {_itemsToReturn = pVal;}


    public void setSelectedCatalogList(String pValue) {_selectedCatalogList=pValue;}
    public String getSelectedCatalogList(){return _selectedCatalogList;}

    public boolean getSearchInSelectedCatalogs() {return _searchInSelectedCatalogs;}
    public void setSearchInSelectedCatalogs(boolean pVal) {_searchInSelectedCatalogs = pVal;}

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        this._showInactiveFl = showInactiveFl;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    _selectedItemIds = new int[0];
    _distInfoRequest = false;
    _searchInSelectedCatalogs = false;
    _showInactiveFl=false;
    }

  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }
}


