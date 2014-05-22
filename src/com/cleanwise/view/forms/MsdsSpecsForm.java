/**
 * Title:        MsdsSpecsForm
 * Description:  This is the Struts ActionForm class for the msds & specs page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.value.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import com.cleanwise.view.utils.Constants;
import java.math.BigDecimal;


/**
 * Form bean for the shopping page.  This form has the following fields,
 */

public final class MsdsSpecsForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
  private int _contentPage = 0;

  private ShoppingCartItemDataVector _myMsds = null;
  private ShoppingCartItemDataVector _mySpec = null;
  private ShoppingCartItemDataVector _myDed = null;
  private ItemDataDocUrlsViewVector _dedUrls = null;
  private ItemDataDocUrlsViewVector _msdsUrls = null;
  private ItemDataDocUrlsViewVector _specUrls = null;
  

  private List _productList = null;
  private List _cartItems = null;
  private int[] _myDocDescriptor = null;
  private BusEntityDataVector _manufacturers = null;
  private ArrayList _menuMfgLables = null;
  private ArrayList _menuMfgOptions = null;
  private ItemDataVector _categories = null;
  private ArrayList _menuCategoryLables = null;
  private ArrayList _menuCategoryOptions = null;

  // search
  private String _searchDesc = null;
  private String _searchDescType = "Name";
//  private String _searchSize = null;
  private String _searchCategory = null;
  private String _searchSku = null;
  private String _searchSkuType = "Cust.Sku";
  private String _searchManufacturer = null;
  private String _docType = "MSDS";

  //order
  private int _orderBy = Constants.ORDER_BY_NAME;
  //---------------------
  private int _pageSize=10;
  private int _offset=0;
  private int[] _contractCategoryIds = new int[0];
  private int[] _contractItemIds = new int[0];
  private String _requestListNumber="0";
  
  // E3 Storage 
  private String msdsStorageTypeCd;
  private String dedStorageTypeCd;
  private String specStorageTypeCd;
  // ---------------------------------------------------------------- Properties
  public int getContentPage() {return _contentPage;}
  public void setContentPage(int pValue) {_contentPage = pValue;}

  public ShoppingCartItemDataVector getMyMsds() {return _myMsds;}
  public void setMyMsds(ShoppingCartItemDataVector pValue) {_myMsds = pValue;}

  public ShoppingCartItemDataVector getMySpec() {return _mySpec;}
  public void setMySpec(ShoppingCartItemDataVector pValue) {_mySpec = pValue;}

  public ShoppingCartItemDataVector getMyDed() {return _myDed;}
  public void setMyDed(ShoppingCartItemDataVector pValue) {_myDed = pValue;}

  public ItemDataDocUrlsViewVector getDedUrls() {return _dedUrls;}
  public void setDedUrls(ItemDataDocUrlsViewVector pValue) {_dedUrls = pValue;}
  
  public ItemDataDocUrlsViewVector getMsdsUrls() {return _msdsUrls;}
  public void setMsdsUrls(ItemDataDocUrlsViewVector pValue) {_msdsUrls = pValue;}

  public ItemDataDocUrlsViewVector getSpecUrls() {return _specUrls;}
  public void setSpecUrls(ItemDataDocUrlsViewVector pValue) {_specUrls = pValue;}

  public ArrayList getMenuMfgLabels() {return _menuMfgLables;}
  public void setMenuMfgLabels(ArrayList pValue) {_menuMfgLables = pValue;}

  public ArrayList getMenuMfgOptions() {return _menuMfgOptions;}
  public void setMenuMfgOptions(ArrayList pValue) {_menuMfgOptions = pValue;}

  public BusEntityDataVector getManufacturers() {return _manufacturers;}
  public void setManufacturers(BusEntityDataVector pValue) {_manufacturers = pValue;}

  public ArrayList getMenuCategoryLabels() {return _menuCategoryLables;}
  public void setMenuCategoryLabels(ArrayList pValue) {_menuCategoryLables = pValue;}

  public ArrayList getMenuCategoryOptions() {return _menuCategoryOptions;}
  public void setMenuCategoryOptions(ArrayList pValue) {_menuCategoryOptions = pValue;}

  public ItemDataVector getCategories() {return _categories;}
  public void setCategories(ItemDataVector pValue) {_categories = pValue;}

  public String getRequestListNumber(){return _requestListNumber;}
  public void setRequestListNumber(String pValue) {_requestListNumber=pValue;}

  public int[] getContractItemIds() {return _contractItemIds;}
  public void setContractItemIds(int[] pValue) {_contractItemIds = pValue;}

  public int[] getContractCategoryIds() {return _contractCategoryIds;}
  public void setContractCategoryIds(int[] pValue) {_contractCategoryIds = pValue;}

  //Search attributes
  public String getSearchSkuType() {return _searchSkuType;}
  public void setSearchSkuType(String pValue) {_searchSkuType = pValue;}

  public String getSearchSku() {return _searchSku;}
  public void setSearchSku(String pValue) {_searchSku = pValue;}

  public String getSearchDescType() {return _searchDescType;}
  public void setSearchDescType(String pValue) {_searchDescType = pValue;}

  public String getSearchDesc() {return _searchDesc;}
  public void setSearchDesc(String pValue) {_searchDesc = pValue;}

  public String getSearchCategory() {return _searchCategory;}
  public void setSearchCategory(String pValue) {_searchCategory = pValue;}

//  public String getSearchSize() {return _searchSize;}
//  public void setSearchSize(String pValue) {_searchSize = pValue;}

  public String getSearchManufacturer() {return _searchManufacturer;}
  public void setSearchManufacturer(String pValue) {_searchManufacturer = pValue;}

  public String getDocType() {return _docType;}
  public void setDocType(String pValue) {_docType = pValue;}

  public void clearSearchFilters() {
    setSearchSku("");
    setSearchDesc("");
    setSearchCategory("");
//    setSearchSize("");
    setSearchManufacturer("0");
  }

  public int getOrderBy() {return _orderBy;}
  public void setOrderBy(int pValue) {_orderBy = pValue;}

  public List getProductList() {return _productList;}
  public void setProductList(List pValue) {_productList = pValue;}
  public int getProductListSize() {
    if(_productList==null) return 0;
    return _productList.size();
  }

  public List getCartItems() {return _cartItems;}
  public void setCartItems(List pValue) {
   _cartItems = pValue;
   setMyDocDescriptor(new int[pValue.size()]);
  }
  public int getCartItemsSize() {
    if(_cartItems==null) return 0;
    return _cartItems.size();
  }

  public int[] getMyDocDescriptor() {return _myDocDescriptor;}
  public void setMyDocDescriptor(int[] pValue) {_myDocDescriptor = pValue;}


  public boolean isCategoryChanged(int ind) {
    if(ind>=_cartItems.size()) return false;
    if(ind==0) return true;
    ShoppingCartItemData item1 = (ShoppingCartItemData) _cartItems.get(ind-1);
    ShoppingCartItemData item2 = (ShoppingCartItemData) _cartItems.get(ind);
    if(!item1.getCategoryName().equals(item2.getCategoryName())) return true;
    return false;
  }

  /////////////// Page control
  public int getPageSize() {return _pageSize;};

//  public void setQuantity(String[] pValue){_quantity=pValue;}
//  public String[] getQuantity() { return _quantity;}

  public void setOffset(int pValue){_offset=pValue;}
  public int getOffset() { return _offset;}

//  public void setQuantityElement(int pIndex, String pValue){
//  if(pIndex<0) return;
//  if(pIndex<_quantity.length) {
//    _quantity[pIndex]=pValue;
//  } else {
//    String[] qty = new String[pIndex+1];
//    int ii=0;
//    for(;ii<_quantity.length;ii++){
//      qty[ii]=_quantity[ii];
//    }
//    for(;ii<pIndex-1;ii++) {
//      qty[ii]="";
//    }
//    qty[pIndex] = pValue;
//    _quantity = qty;
//  }
//  }

//  public String getQuantityElement(int pIndex) {
//  if(pIndex<_quantity.length &&pIndex>=0) {
//    return _quantity[pIndex];
//  } else {
//    return "";
//  }
//  }

//  public void setItemIds(int[] pValue) {_itemIds = pValue;}
//  public int[] getItemIds() {return _itemIds;}

//  public void setItemIdsElement(int pIndex, int pValue){
//    if(pIndex<0) return;
//    if(pIndex<_itemIds.length) {
//      _itemIds[pIndex]=pValue;
//    } else {
//      int[] ids = new int[pIndex+1];
//      int ii=0;
//      for(;ii<_itemIds.length;ii++){
//        ids[ii]=_itemIds[ii];
//      }
//      for(;ii<pIndex-1;ii++) {
//        ids[ii]=0;
//      }
//      ids[pIndex] = pValue;
//      _itemIds = ids;
//    }
//  }

//  public int getItemIdsElement(int pIndex) {
//    if(pIndex<_itemIds.length &&pIndex>=0) {
//      return _itemIds[pIndex];
//    } else {
//      return 0;
//    }
//  }

  //{{{{ Page navigation methods (begin)
  public LinkedList getPages() {
    LinkedList pages = new LinkedList();
    int lastPage = getLastPage();
    for(int ii=0; ii<=lastPage; ii++) {
       pages.add(new Integer(ii));
    }
    return pages;
  }

  public int getCurrentPage() {
    if(_productList==null || _productList.size()==0) {
      return -1;
    }
    int currentPage = _offset/_pageSize;
    return currentPage;
  }

  public int getLastPage() {
    if(_productList==null || _productList.size()==0) {
      return -1;
    }
    int lastPage = (_productList.size()-1)/_pageSize;
    return lastPage;
  }

  public int getPrevPage() {
    int currentPage = getCurrentPage();
    return currentPage-1;
  }

  public int getNextPage() {
    int currentPage = getCurrentPage();
    int lastPage = getLastPage();
    if(currentPage>=lastPage) {
      return -1;
    }
    return currentPage+1;
  }
  //}}}} Page navigation methods (end)

  // ------------------------------------------------------------ Public Methods


  /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
  }


  /**
   * Validate the properties that have been set from this HTTP request,
   * and return an <code>ActionErrors</code> object that encapsulates any
   * validation errors that have been found.  If no errors are found, return
   * <code>null</code> or an <code>ActionErrors</code> object with no
   * recorded error messages.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    return errors;
  }
  
	// Methods for E3 Storage vs. Database (for images, docs)
	
	public String getMsdsStorageTypeCd() {
		return msdsStorageTypeCd;
	}
	
	public void setMsdsStorageTypeCd(String msdsStorageTypeCd) {
		this.msdsStorageTypeCd = msdsStorageTypeCd;
	}
	
	public String getDedStorageTypeCd() {
		return dedStorageTypeCd;
	}
	
	public void setDedStorageTypeCd(String dedStorageTypeCd) {
		this.dedStorageTypeCd = dedStorageTypeCd;
	}

	public String getSpecStorageTypeCd() {
		return specStorageTypeCd;
	}
	
	public void setSpecStorageTypeCd(String specStorageTypeCd) {
		this.specStorageTypeCd = specStorageTypeCd;
	}
}
