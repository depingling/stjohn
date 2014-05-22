/**
 * Title:        UserShopForm
 * Description:  This is the Struts ActionForm class for the shopping page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.Pager;
import com.cleanwise.view.utils.ShopTool;


/**
 * Form bean for the shopping page.  This form has the following fields,
 */

public final class UserShopForm extends ActionForm {
    private static final Logger log = Logger.getLogger(UserShopForm.class);


  // -------------------------------------------------------- Instance Variables
  private int _requestToken = 0;

  private CleanwiseUser _appUser = null;
  private List _categoryPath = null;
  private List _categoryDataPath = null;
  private List _categoryList = null;
  private List _productList = null;
  private List _cartItems = null;
  private List _groupedItems = null;
  private String _itemsStamp = "";
  private String _itemsSubmitStamp = "";
  private List _orderGuideCartItems = null;
  private boolean _navigationFlag = true;
  private BusEntityDataVector _manufacturers = null;
  private ArrayList _menuMfgLables = null;
  private ArrayList _menuMfgOptions = null;
  private long[] _selectBox = new long[0],
  _orderSelectBox = new long[0];
  //Shopping method
  private int _shopMethod = -1;
  // search
  private String _searchName = null;
  private String _searchDesc = null;
  private String _searchSize = null;
  private String _searchCategory = null;
  private String _searchSku = null;
  private String _searchSkuType = "Cust.Sku";
  private String _searchManufacturer = null;
  private boolean _searchGreenCertifiedFl=false;
  private String _searchUPCNum = null;
    //navigate
  private int _navigateCategoryId = -1;

  //Set the default to category.
  private int _orderBy = Constants.ORDER_BY_CATEGORY;

  //order guide
  private OrderGuideDataVector _userOrderGuides = null;
  private OrderGuideDataVector _templateOrderGuides = null;
  private OrderGuideDataVector  _customOrderGuides = null;
  private String _userOrderGuideId = "";
  private String _templateOrderGuideId = "";
  private String _customOrderGuideId = "";
  private int _orderGuideId = 0;
  private String _orderGuideName = "";
  //use CostCenter as the group heading, else use category name while creating
  //pdf.
  private boolean _groupByCostCenter = true;
  //---------------------
  private int _pageSize=1;
  private String[] _quantity = new String[_pageSize],
            _onhand = new String[_pageSize];

  private int[] _itemIds = new int[_pageSize];
  private int _offset=0;
  private int[] _contractCategoryIds = new int[0];
  private int[] _contractItemIds = new int[0];
  private String _requestListNumber="0";
  private ShoppingCartItemData _itemDetail = null;
  private int _itemId = 0;
  private String _quantityDetail = "";
  //Jd begin ---
  private String _priceCode ="";
  private BigDecimal _weightThreshold = null;
  private BigDecimal _priceThreshold = null;
  private BigDecimal _contractThreshold = null;
  private MenuItemView _catalogMenu;
  private Map<Integer, ProductDataVector> _catalogMenuCategoryToItemMap = null;
  //private PairViewVector _categoryNavigatorInfo = null;
  private PairViewVector _shopNavigatorInfo = null;
  private Pager _pager = null;
  private int _pageIndex = 0;

  private String selectedShoppingListId;
  private boolean deleteAllFl;
  private String modifiedName;
  private String confirmMessage = null;
  private String warningMessage = null;

  private ShoppingCartItemData itemGroupCachedView;

  private String _categoryKey = "";
    //Jd end
  private boolean multiLevelCategoryExists = false;

  private String msdsStorageTypeCd;
  private String dedStorageTypeCd;
  private String specStorageTypeCd;
//----------------------------------------------------------------
  public int getRequestToken(){return _requestToken;}
  public void setRequestToken(int pValue) {_requestToken=pValue;}

  public void setSelectBox(long[] pValue) {_selectBox = pValue;}
  public long[] getSelectBox() {return _selectBox;}

  public void setOrderSelectBox(long[] pValue) {_orderSelectBox = pValue;}
  public long[] getOrderSelectBox() {return _orderSelectBox;}

  // ---------------------------------------------------------------- Properties
  public int getNavigateCategoryId(){return _navigateCategoryId;}
  public void setNavigateCategoryId(int pValue) {
  _navigateCategoryId=pValue;}

  // og search properties
  private String _ogSearchName = null;
  private boolean _groupedItemView = false;

  private List originalCartItems = null;
  private List originalProducts = null;
  private OrderGuideStructureDataVector templateOrderGuideItems =null;
  private List templateOrderGuideItemIds =null;
  private List templateOrderGuideCategoryIds =null;

  public double getItemsAmt() {
	List items = getCartItems();
    if(items==null) return 0;
    long cost = 0;
    for(int ii=0; ii<items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
      BigDecimal priceBD = new BigDecimal((sciD.getPrice()+.005)*100);
      long price = priceBD.longValue();
      cost+=sciD.getQuantity()*price;
    }
    double costD = cost;
    return costD/100;
  }

  public ArrayList getMenuMfgLabels() {return _menuMfgLables;}
  public void setMenuMfgLabels(ArrayList pValue) {_menuMfgLables = pValue;}

  public ArrayList getMenuMfgOptions() {return _menuMfgOptions;}
  public void setMenuMfgOptions(ArrayList pValue) {_menuMfgOptions = pValue;}

  public BusEntityDataVector getManufacturers() {return _manufacturers;}
  public void setManufacturers(BusEntityDataVector pValue) {_manufacturers = pValue;}

  public String getQuantityDetail(){
      if (_quantityDetail == null ) return "";
      return _quantityDetail;
  }
  public void setQuantityDetail(String pValue) {_quantityDetail=pValue;}

  public ShoppingCartItemData getItemDetail() {return _itemDetail;}
  public void setItemDeatail(ShoppingCartItemData pValue) {_itemDetail = pValue;}

  public int getItemId() {return _itemId;}
  public void setItemId(int pValue) {_itemId = pValue;}

  //  public String getPageType(){return _pageType;}
//  public void setPageType(String pValue) {_pageType=pValue;}

  public String getRequestListNumber(){return _requestListNumber;}
  public void setRequestListNumber(String pValue) {_requestListNumber=pValue;}

  public int[] getContractItemIds() {return _contractItemIds;}
  public void setContractItemIds(int[] pValue) {_contractItemIds = pValue;}

  public int[] getContractCategoryIds() {return _contractCategoryIds;}
  public void setContractCategoryIds(int[] pValue) {_contractCategoryIds = pValue;}

  public boolean getNavigationFlag() {return _navigationFlag;}
  public void setNavigationFlag(boolean pValue) {_navigationFlag = pValue;}

  //Shopping method
  public int getShopMethod() {return _shopMethod;}
  public void setShopMethod(int pValue) {_shopMethod = pValue;}

  //Order guides
  public int getOrderGuideId(){return _orderGuideId;}
  public void setOrderGuideId(int pValue){_orderGuideId = pValue;}

  //Order guide name
  public String getOrderGuideName(){return _orderGuideName;}
  public void setOrderGuideName(String pValue){_orderGuideName = pValue;}

  //User order quides
  public OrderGuideDataVector getUserOrderGuides() {return _userOrderGuides;}
  public void setUserOrderGuides(OrderGuideDataVector pValue) {_userOrderGuides = pValue;}

  public ArrayList getUserOrderGuideIds() {
    ArrayList al = new ArrayList();
    if(_userOrderGuides == null) return al;
    for(int ii=0; ii<_userOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _userOrderGuides.get(ii);
      al.add(""+ogD.getOrderGuideId());
    }
    return al;
  }
  public ArrayList getUserOrderGuideNames() {
    ArrayList al = new ArrayList();
    if(_userOrderGuides == null) return al;
    for(int ii=0; ii<_userOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _userOrderGuides.get(ii);
      al.add(ogD.getShortDesc());
    }
    return al;
  }

  public int getUserOrderGuideNumber() {
    if(_userOrderGuides == null) return 0;
    return _userOrderGuides.size();
  }

  public String getUserOrderGuideId() {return _userOrderGuideId;}
  public void setUserOrderGuideId(String pValue) {_userOrderGuideId = pValue;}

  public boolean isUserOrderGuide(int id){

	  ArrayList uogs = getUserOrderGuideIds();
	  if(uogs!=null && !uogs.isEmpty()){
		  if(uogs.contains(Integer.toString(id))){
			  return true;
		  }else{
			  return false;
		  }
	  }
	  return false;
  }
   //Custom order quides
  public OrderGuideDataVector getCustomOrderGuides() {return _customOrderGuides;}
  public void setCustomOrderGuides(OrderGuideDataVector pValue) {_customOrderGuides = pValue;}

  public ArrayList getCustomOrderGuideIds() {
    ArrayList al = new ArrayList();
    if(_customOrderGuides == null) return al;
    for(int ii=0; ii<_customOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _customOrderGuides.get(ii);
      al.add(""+ogD.getOrderGuideId());
    }
    return al;
  }

  public ArrayList getCustomOrderGuideNames() {
    ArrayList al = new ArrayList();
    if(_customOrderGuides == null) return al;
    for(int ii=0; ii<_customOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _customOrderGuides.get(ii);
      al.add(ogD.getShortDesc());
    }
    return al;
  }


  public int getCustomOrderGuideNumber() {
    if(_customOrderGuides == null) return 0;
    return _customOrderGuides.size();
  }

  public String getCustomOrderGuideId() {return _customOrderGuideId;}
  public void setCustomOrderGuideId(String pValue) {_customOrderGuideId = pValue;}

  public boolean isCustomOrderGuide(int id){
	  ArrayList uogs = getCustomOrderGuideIds();
      return uogs != null && !uogs.isEmpty() && uogs.contains(Integer.toString(id));
  }

  //Template orde guides
  public OrderGuideDataVector getTemplateOrderGuides() {return _templateOrderGuides;}
  public void setTemplateOrderGuides(OrderGuideDataVector pValue) {_templateOrderGuides = pValue;}

  public ArrayList getTemplateOrderGuideIds() {
    ArrayList al = new ArrayList();
    if(_templateOrderGuides == null) return al;
    for(int ii=0; ii<_templateOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
      al.add(""+ogD.getOrderGuideId());
    }
    return al;
  }
  public ArrayList getTemplateOrderGuideNames() {
    ArrayList al = new ArrayList();
    if(_templateOrderGuides == null) return al;
    for(int ii=0; ii<_templateOrderGuides.size(); ii++) {
      OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
      al.add(ogD.getShortDesc());
    }
    return al;
  }
  public int getTemplateOrderGuideNumber() {
    if(_templateOrderGuides == null) return 0;
    return _templateOrderGuides.size();
  }

  public String getTemplateOrderGuideId() {return _templateOrderGuideId;}
  public void setTemplateOrderGuideId(String pValue) {_templateOrderGuideId = pValue;}

  //Template orde guides
  public OrderGuideDataVector getTemplateOrderGuideOptions() {

      OrderGuideDataVector templateOrderGuideOptions = new OrderGuideDataVector();

      OrderGuideDataVector templateOrderGuides = getTemplateOrderGuides();
      if (templateOrderGuides != null) {
          templateOrderGuideOptions.addAll(templateOrderGuides);
      }

      OrderGuideDataVector customOrderGuides = getCustomOrderGuides();
      if (customOrderGuides != null) {
          templateOrderGuideOptions.addAll(customOrderGuides);
      }

      DisplayListSort.sort(templateOrderGuideOptions, "short_desc");

      return templateOrderGuideOptions;
  }

  //Search attributes
  public String getSearchSkuType() {return _searchSkuType;}
  public void setSearchSkuType(String pValue) {_searchSkuType = pValue;}

  public boolean getSearchGreenCertifiedFl() {  return _searchGreenCertifiedFl;}
  public void setSearchGreenCertifiedFl(boolean searchGreenCertifiedFl) { this._searchGreenCertifiedFl = searchGreenCertifiedFl; }

  public String getSearchSku() {return _searchSku;}
  public void setSearchSku(String pValue) {_searchSku = pValue;}

  public String getSearchName() {return _searchName;}
  public void setSearchName(String pValue) {_searchName = pValue;}

  public String getSearchDesc() {return _searchDesc;}
  public void setSearchDesc(String pValue) {_searchDesc = pValue;}

  public String getSearchCategory() {return _searchCategory;}
  public void setSearchCategory(String pValue) {_searchCategory = pValue;}

  public String getSearchSize() {return _searchSize;}
  public void setSearchSize(String pValue) {_searchSize = pValue;}

  public String getSearchManufacturer() {return _searchManufacturer;}
  public void setSearchManufacturer(String pValue) {_searchManufacturer = pValue;}

  public String getSearchUPCNum() {return _searchUPCNum;}
  public void setSearchUPCNum(String pValue) {_searchUPCNum = pValue;}

  public void clearSearchFilters() {
    setSearchSku("");
    setSearchName("");
    setSearchDesc("");
    setSearchCategory("");
    setSearchSize("");
    setSearchManufacturer("0");
    setSearchUPCNum("");
  }

  public int getOrderBy() {return _orderBy;}
  public void setOrderBy(int pValue) {_orderBy = pValue;}

  //
  public CleanwiseUser getAppUser() {return _appUser;}
  public void setAppUser(CleanwiseUser pValue) {_appUser = pValue;}
  public boolean getShowWholeCatalog(){
    if(_appUser==null) {
      return false;
    }
    return _appUser.getShowWholeCatalog();
  }

  public boolean getAllowPurchase() {
    if(_appUser==null) {
      return false;
    }
    String roleCd = _appUser.getUser().getUserRoleCd();
    if(roleCd==null) {
      return false;
    }
    if(roleCd.indexOf(Constants.UserRole.BROWSE_ONLY)>=0) {
      return false;
    }
    return true;
  }

  public boolean getShowPrice(){
    if(_appUser==null) {
      return false;
    }
    String roleCd = _appUser.getUser().getUserRoleCd();
    boolean showPrice = (roleCd.indexOf(Constants.UserRole.SHOW_PRICE)>=0)?true:false;
    return showPrice;
  }

  public List getCategoryPath() {return _categoryPath;}
  public void setCategoryPath(List pValue) {_categoryPath = pValue;}
  public int getCategoryPathSize() {
    if(_categoryPath==null) return 0;
    return _categoryPath.size();
  }

  public List getCategoryDataPath() {return _categoryDataPath;}
  public void setCategoryDataPath(List pValue) {_categoryDataPath = pValue;}
  public int getCategoryDataPathSize() {
    if(_categoryDataPath==null) return 0;
    return _categoryDataPath.size();
  }

  public List getCategoryList() {return _categoryList;}
  public void setCategoryList(List pValue) {_categoryList = pValue;}
  public int getCategoryListSize() {
    if(_categoryList==null) return 0;
    return _categoryList.size();
  }

  public List getProductList() {return _productList;}
  public void setProductList(List pValue) {_productList = pValue;}
  public int getProductListSize() {
    if(_productList==null) return 0;
    return _productList.size();
  }
  
  public List getCartItems() {
	  if (_groupedItemView)
		  return _groupedItems;
	  else
		  return _cartItems;
  }
  
  public void setCartItems(List pValue) {
	  if (_groupedItemView)
		  _groupedItems = pValue;
	  else
		  _cartItems = pValue;
	        
      if(pValue!=null) {
        LinkedList llsku = new LinkedList();
        LinkedList llid = new LinkedList();
        for(Iterator iter = pValue.iterator(); iter.hasNext();) {
           ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
           String asku = sciD.getActualSkuNum();
           Integer iid = new Integer(sciD.getItemId());
           llsku.add(asku);
           llid.add(iid);
        }
        _itemsStamp=llsku.hashCode()+"@"+llid.hashCode();
      } else {
        _itemsStamp = "";
      }
  }  

  public String getItemsStamp(){
      return _itemsStamp;
  }
  public int getCartItemsSize() {
    if(getCartItems()==null) return 0;
    return getCartItems().size();
  }

  private List _removedItems = null;
  public List getRemovedItems() {return _removedItems;}
  public void setRemovedItems(List pValue) {_removedItems = pValue;}
  public int getRemovedItemsSize() {
    if(_removedItems==null) return 0;
    return _removedItems.size();
  }

  public List getOrderGuideCartItems() {return _orderGuideCartItems;}
  public void setOrderGuideCartItems(List pValue) {_orderGuideCartItems = pValue;}


  public boolean isCategoryChanged(int ind) {
	List items = getCartItems();
    if(ind>=items.size()) return false;
    if(ind==0) return true;
    ShoppingCartItemData item1 = (ShoppingCartItemData) items.get(ind-1);
    ShoppingCartItemData item2 = (ShoppingCartItemData) items.get(ind);
    if(!item1.getCategoryName().equals(item2.getCategoryName())) return true;
    return false;
  }

  /////////////// Page control
  public int getPageSize() {return _pageSize;};
  public void setPageSize(int pSize) {_pageSize = pSize;};

  public void setQuantity(String[] pValue){_quantity=pValue;}
  public String[] getQuantity() { return _quantity;}

  public void setOffset(int pValue){_offset=pValue;}
  public int getOffset() { return _offset;}

  public void setQuantityElement(int pIndex, String pValue){
    if(pIndex<0) return;
    if(pIndex<_quantity.length) {
      _quantity[pIndex]=pValue;
    } else {
      String[] qty = new String[pIndex+1];
      int ii=0;
      for(;ii<_quantity.length;ii++){
        qty[ii]=_quantity[ii];
      }
      for(;ii<pIndex-1;ii++) {
        qty[ii]="";
      }
      qty[pIndex] = pValue;
      _quantity = qty;
    }
  }

  public String getQuantityElement(int pIndex) {
    if(pIndex<_quantity.length && pIndex>=0 && null != _quantity[pIndex]) {
      return _quantity[pIndex];
    }
    return "";
  }

  public void setItemIds(int[] pValue) {_itemIds = pValue;}
  public int[] getItemIds() {return _itemIds;}

  public void setItemIdsElement(int pIndex, int pValue){
    if(pIndex<0) return;
    if(pIndex<_itemIds.length) {
      _itemIds[pIndex]=pValue;
    } else {
      int[] ids = new int[pIndex+1];
      int ii=0;
      for(;ii<_itemIds.length;ii++){
        ids[ii]=_itemIds[ii];
      }
      for(;ii<pIndex-1;ii++) {
        ids[ii]=0;
      }
      ids[pIndex] = pValue;
      _itemIds = ids;
    }
  }

  public int getItemIdsElement(int pIndex) {
    if(pIndex<_itemIds.length &&pIndex>=0) {
      return _itemIds[pIndex];
    } else {
      return 0;
    }
  }

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
  //Jd begin ---
  //private String _priceCode ="";
  public String getPriceCode() {return _priceCode;};
  public void setPriceCode(String pVal) {_priceCode = pVal;};
  //private BigDecimal _weightThreshold = null;
  public BigDecimal getWeightThreshold() {return _weightThreshold;};
  public void setWeightThreshold(BigDecimal pVal) {_weightThreshold = pVal;};
  //private BigDecimal _priceThreshold = null;
  public BigDecimal getPriceThreshold() {return _priceThreshold;};
  public void setPriceThreshold(BigDecimal pVal) {_priceThreshold = pVal;};
  //private BigDecimal _contractThreshold = null;
  public BigDecimal getContractThreshold() {return _contractThreshold;};
  public void setContractThreshold(BigDecimal pVal) {_contractThreshold = pVal;};
  /*
  public boolean getJdDiscountFlag(ShoppingCartData pCart) {
    boolean discountFlag = false;
    BigDecimal sumPrice = new BigDecimal(0);
    BigDecimal sumWeight = new BigDecimal(0);
    if(pCart!=null) {
      sumPrice = pCart.getCartPrice();
      sumWeight = pCart.getCartWeight();
    }
    if(_contractThreshold!=null) {
      if(_contractThreshold.compareTo(sumPrice)<0){
        discountFlag = true;
      } else {
        sumPrice = sumPrice.add(getSumPrice());
        if(_contractThreshold.compareTo(sumPrice)<0) discountFlag = true;
      }
    } else {
      if(_priceThreshold!=null) {
        if(_priceThreshold.compareTo(sumPrice)<0){
          discountFlag = true;
        } else {
          sumPrice = sumPrice.add(getSumPrice());
          if(_priceThreshold.compareTo(sumPrice)<0) discountFlag = true;
        }
      }
      if(!discountFlag && _weightThreshold!=null) {
        if(_weightThreshold.compareTo(sumWeight)<0){
          discountFlag = true;
        } else {
          sumWeight = sumWeight.add(getSumWeight());
          if(_weightThreshold.compareTo(sumWeight)<0) discountFlag = true;
        }
      }
    }
    return discountFlag;
  }
  */
  public BigDecimal getSumPrice() {
    BigDecimal sumPrice = new BigDecimal(0);
    List items = getCartItems();
    if(items!=null) {
      for(int ii=0; ii<items.size(); ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) items.get(ii);
        int qty = item.getQuantity();
        if(qty>0) {
          double price = item.getPrice();
          sumPrice = sumPrice.add(new BigDecimal(price*qty));
        }
      }
    }
    return sumPrice;
  }
  public BigDecimal getSumWeight() {
    BigDecimal sumWeight = new BigDecimal(0);
    List items = getCartItems();
    if(items!=null) {
      for(int ii=0; ii<items.size(); ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) items.get(ii);
        int qty = item.getQuantity();
        if(qty>0) {
          String weightS = item.getProduct().getShipWeight();
          try {
            double weight = Double.parseDouble(weightS);
            sumWeight = sumWeight.add(new BigDecimal(weight*qty));
          }catch(Exception exc) {}
        }
      }
    }
    return sumWeight;
  }
  public double getItemsDiscAmt() {
	List items = getCartItems();
    if(items==null) return 0;
    long cost = 0;
    for(int ii=0; ii<items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) items.get(ii);
      BigDecimal priceBD = new BigDecimal((sciD.getDiscPrice()+.005)*100);
      long price = priceBD.longValue();
      cost+=sciD.getQuantity()*price;
    }
    double costD = cost;
    return costD/100;
  }
  //Jd end

  // ------------------------------------------------------------ Public Methods


  /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
	  String action = request.getParameter("action");
	  if (action == null || action.equals(""))
		  return;

	  _quantity = new String[_pageSize];
      _itemIds = new int[_pageSize];
      _itemsSubmitStamp = "";
      confirmMessage="";
      mInvItems = ShopTool.getInventoryItems(request);
      Date sysdate = new Date();
      _groupedItemView = false;
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


  public void sortBySku() {
      ShoppingCartData.orderBySku(getCartItems());
  }
  public void sortByName() {
      ShoppingCartData.orderByName(getCartItems());
  }
  public void sortByCategory() {
      ShoppingCartData.orderByCategory(getCartItems());
  }

    public void setOnhand(String[] pValue){_onhand=pValue;}
    public String[] getOnhand() { return _onhand;}
    public void setOnhandElement(int pIndex, String pValue){
        if(pIndex<_onhand.length) {
            _onhand[pIndex]=pValue;
        }
    }

    public String getOnhandElement(int pIndex) {
        if(pIndex<_onhand.length &&_onhand[pIndex] != null ) {
            return _onhand[pIndex];
        }
        return "";
    }


        private ArrayList mInvItems = null;

    public ArrayList getInventoryItems() {
        if ( null == mInvItems ) {
            mInvItems = new ArrayList();
        }
        return mInvItems;
    }

    public SiteInventoryInfoView getInventoryItem(int idx) {

        if (mInvItems == null) {
            mInvItems = new ArrayList();
        }
        while (idx >= mInvItems.size()) {
            mInvItems.add(SiteInventoryInfoView.createValue());
        }

        return (SiteInventoryInfoView) mInvItems.get(idx);
    }

    public void setItemsSubmitStamp(String pVal) {
    	log.info("UserShopForm SSSSSSSSS _cartItemsSubmitStamp: "+_itemsSubmitStamp);
        _itemsSubmitStamp = pVal;
    }

    public String getItemsSubmitStamp() {
        return _itemsSubmitStamp;
    }

    public ShoppingCartItemData getCartLine(int idx) {
        if(idx<10) {
        	log.info("UserShopForm FFFFFFFFFFF _itemsStamp: "+_itemsStamp+" : "+_itemsSubmitStamp);
        }
        //if(_cartItemsStamp!=_cartItemsSubmitStamp) {
        //    return new ShoppingCartItemData();
        //}
        List items = getCartItems();
        if(items==null || idx<0 || idx>=items.size()) {
            return new ShoppingCartItemData();
        }
        return (ShoppingCartItemData) items.get(idx);
    }

   public String getOgSearchName() {return _ogSearchName;}
   public void setOgSearchName(String pValue) {_ogSearchName = pValue;}


    public void setCatalogMenu(MenuItemView catalogMenu) {
        this._catalogMenu = catalogMenu;
    }

    public MenuItemView getCatalogMenu() {
        return _catalogMenu;
    }

/**
	 * @return the catalogMenuCategoryToItemMap
	 */
	public Map<Integer, ProductDataVector> getCatalogMenuCategoryToItemMap() {
		if (_catalogMenuCategoryToItemMap == null) {
			_catalogMenuCategoryToItemMap = new HashMap<Integer, ProductDataVector>();
		}
		return _catalogMenuCategoryToItemMap;
	}
	/**
	 * @param catalogMenuCategoryToItemMap the catalogMenuCategoryToItemMap to set
	 */
	public void setCatalogMenuCategoryToItemMap(
			Map<Integer, ProductDataVector> catalogMenuCategoryToItemMap) {
		_catalogMenuCategoryToItemMap = catalogMenuCategoryToItemMap;
	}
	/*
    public void setCategoryNavigatorInfo(PairViewVector categoryNavigatorInfo) {
        this._categoryNavigatorInfo = categoryNavigatorInfo;
    }


    public PairViewVector getCategoryNavigatorInfo() {
        return _categoryNavigatorInfo;
    }
*/
    public void setShopNavigatorInfo(PairViewVector shopNavigatorInfo) {
        this._shopNavigatorInfo = shopNavigatorInfo;
    }


    public PairViewVector getShopNavigatorInfo() {
        return _shopNavigatorInfo;
    }

    public void setPager(Pager pager) {
        this._pager = pager;
    }

    public Pager getPager() {
        return _pager;
    }

    public void setPageIndex(int pageIndex) {
        this._pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return this._pageIndex;
    }

    public void setSelectedShoppingListId(String selectedShoppingListId) {
        this.selectedShoppingListId = selectedShoppingListId;
      }

      public void setDeleteAllFl(boolean deleteAllFl) {
        this.deleteAllFl = deleteAllFl;
      }

      public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
      }

      public void setWarningMessage(String warningMessage) {
          this.warningMessage = warningMessage;
        }

      public String getSelectedShoppingListId() {
        return selectedShoppingListId;
      }

      public boolean isDeleteAllFl() {
        return deleteAllFl;
      }

      public String getConfirmMessage() {
        return confirmMessage;
      }

      public String getWarningMessage() {
          return warningMessage;
        }

      public String getModifiedName(){
    	  return modifiedName;
      }

      public void setModifiedName(String value){
    		  modifiedName = value;

      }

      public void setCategoryKey(String v) {
          _categoryKey = v;
      }
      public String getCategoryKey() {
          return _categoryKey;
      }
	public ShoppingCartItemData getItemGroupCachedView() {
		return itemGroupCachedView;
	}
	public void setItemGroupCachedView(ShoppingCartItemData itemGroupCachedView) {
		this.itemGroupCachedView = itemGroupCachedView;
	}
	public void setGroupedItemView(boolean isMultipleItem) {
		this._groupedItemView = isMultipleItem;
	}
	public boolean isGroupedItemView() {
		return _groupedItemView;
	}
	public boolean isGroupByCostCenter() {
		return _groupByCostCenter;
	}
	public void setGroupByCostCenter(boolean groupByCostCenter) {
		this._groupByCostCenter = groupByCostCenter;
	}
	/**
	 * @return the multiLevelCategoryExists
	 */
	public boolean isMultiLevelCategoryExists() {
		return multiLevelCategoryExists;
	}
	/**
	 * @param multiLevelCategoryExists the multiLevelCategoryExists to set
	 */
	public void setMultiLevelCategoryExists(boolean multiLevelCategoryExists) {
		this.multiLevelCategoryExists = multiLevelCategoryExists;
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
	public List getOriginalCartItems() {
		return originalCartItems;
	}
	public void setOriginalCartItems(List originalCartItems) {
		this.originalCartItems = originalCartItems;
	}
	public List getOriginalProducts() {
		return originalProducts;
	}
	public void setOriginalProducts(List originalProducts) {
		this.originalProducts = originalProducts;
	}
	public OrderGuideStructureDataVector getTemplateOrderGuideItems() {
		return templateOrderGuideItems;
	}
	public void setTemplateOrderGuideItems(
			OrderGuideStructureDataVector templateOrderGuideItems) {
		this.templateOrderGuideItems = templateOrderGuideItems;
	}
	public List getTemplateOrderGuideItemIds() {
		return templateOrderGuideItemIds;
	}
	public void setTemplateOrderGuideItemIds(List templateOrderGuideItemIds) {
		this.templateOrderGuideItemIds = templateOrderGuideItemIds;
	}
	public List getTemplateOrderGuideCategoryIds() {
		return templateOrderGuideCategoryIds;
	}
	public void setTemplateOrderGuideCategoryIds(List templateOrderGuideCategoryIds) {
		this.templateOrderGuideCategoryIds = templateOrderGuideCategoryIds;
	}
	

}
