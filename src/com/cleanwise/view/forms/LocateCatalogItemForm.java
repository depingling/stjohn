/*
 * LocateCataloItemForm.java
 *
 * Created on October 12, 2005, 11:14 AM
 */
package com.cleanwise.view.forms;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.Collection;
import java.util.LinkedList;
import java.util.GregorianCalendar;
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
import com.cleanwise.service.api.util.SearchCriteria;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ykupershmidt
 */
public class LocateCatalogItemForm   extends LocateStoreBaseForm {
    private boolean _locateCatalogFl = false;
    private CatalogDataVector _catalogFilter = null;
    private StoreData _store=null;
    private StoreData _loginStore=null;
    private String _searchType="";
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
    
    private int[] _selectedItemIds=new int[0];
    private CatalogItemDescViewVector _itemsSelected = null;
    private CatalogItemDescViewVector _itemsToReturn = null;

    public void setLocateCatalogFl(boolean pValue) {_locateCatalogFl=pValue;}
    public boolean getLocateCatalogFl(){return _locateCatalogFl;}
 
    public void setCatalogFilter(CatalogDataVector pValue) {_catalogFilter=pValue;}
    public CatalogDataVector getCatalogFilter(){return _catalogFilter;}
 

    public void setStore(StoreData pValue) {_store=pValue;}
    public StoreData getStore(){return _store;}
 
    public void setLoginStore(StoreData pValue) {_loginStore=pValue;}
    public StoreData getLoginStore(){return _loginStore;}
    
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
    
    public CatalogItemDescViewVector getItemsSelected() {return _itemsSelected;}    
    public void setItemsSelected(CatalogItemDescViewVector pVal) {_itemsSelected = pVal;}
    public int getListCount() {
      if(_itemsSelected==null) return 0;
      return _itemsSelected.size();
    }
 
    public CatalogItemDescViewVector getItemsToReturn() {return _itemsToReturn;}    
    public void setItemsToReturn(CatalogItemDescViewVector pVal) {_itemsToReturn = pVal;}

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    _selectedItemIds = new int[0];  
    _distInfoRequest = false;

  }
  
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }
}
  

