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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartDistDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingCartServiceDataVector;
import com.cleanwise.service.api.value.ShoppingInfoData;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;


/**
 * Form bean for the shopping page.  This form has the following fields,
 */

public class ShoppingCartForm extends ShoppingForm {

  // -------------------------------------------------------- Instance Variables
  private CleanwiseUser _appUser = null;
  private List _cartItems = null, _inventoryInfo = null;
  private List _removedItems = null;
  //Shopping method
  //order
  private int _orderBy = 0;
  //order guide
  private String _userOrderGuideName = null;
  private int _orderGuideId = 0;
  //---------------------
  private int _pageSize=4;
  private String[] _quantity = new String[_pageSize],
      _onhand = new String[_pageSize];
  private int[] _itemIds = new int[_pageSize];
  private int[] _orderNumbers = new int[_pageSize];
  private long[] _selectBox = new long[0],
      _orderSelectBox = new long[0];

  private int _offset=0;
  private int[] _contractCategoryIds = new int[0];
  private int[] _contractItemIds = new int[0];
  private String _expectedRequestNumber="0";
  private String _quantityDetail = "";
  //Jd begin ---
  private String _priceCode ="";
  private BigDecimal _weightThreshold = null;
  private BigDecimal _priceThreshold = null;
  private BigDecimal _contractThreshold = null;
  private ShoppingCartServiceDataVector _cartServices=null;
    //Jd end
  private boolean _usePhysicalInventory = false;
  private ArrayList<PhysicalInventoryPeriod> _physicalInventoryPeriods = null;

  // ----------------------------------------------------------------
  public void setSelectBox(long[] pValue) {_selectBox = pValue;}
  public long[] getSelectBox() {return _selectBox;}

  public void setOrderSelectBox(long[] pValue) {_orderSelectBox = pValue;}
  public long[] getOrderSelectBox() {return _orderSelectBox;}

  // ---------------------------------------------------------------- Properties



  public String getQuantityDetail(){return _quantityDetail;}
  public void setQuantityDetail(String pValue) {_quantityDetail = pValue;}

  public String getExpectedRequestNumber(){return _expectedRequestNumber;}
  public void setExpectedRequestNumber(String pValue) {_expectedRequestNumber=pValue;}


  public int[] getContractItemIds() {return _contractItemIds;}
  public void setContractItemIds(int[] pValue) {_contractItemIds = pValue;}

  public int[] getContractCategoryIds() {return _contractCategoryIds;}
  public void setContractCategoryIds(int[] pValue) {_contractCategoryIds = pValue;}


  //Order guides
  public int getOrderGuideId(){return _orderGuideId;}
  public void setOrderGuideId(int pValue){_orderGuideId = pValue;}

  //User order quides
  public String getUserOrderGuideName() {return _userOrderGuideName;}
  public void setUserOrderGuideName(String pValue) {_userOrderGuideName = pValue;}



  public int getOrderBy() {return _orderBy;}
  public void setOrderBy(int pValue) {_orderBy = pValue;}

  //
  public CleanwiseUser getAppUser() {return _appUser;}
  public void setAppUser(CleanwiseUser pValue) {_appUser = pValue;}
  public boolean getShowWholeCatalog(){
    if(_appUser==null) {
      return false;
    }
    String roleCd = _appUser.getUser().getUserRoleCd();
    boolean showWholeCatalog = (roleCd.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY)>=0)?false:true;
    return showWholeCatalog;
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
    boolean showPrice = (roleCd.indexOf(Constants.UserRole.SHOW_PRICE)>=0)?false:true;
    return showPrice;
  }

    public void setShoppingCart(ShoppingCartData pValue)
    {
        _formCart = pValue;
    }
    public ShoppingCartData getShoppingCart(HttpServletRequest request)
    {
        return ShopTool.getCurrentShoppingCart(request);
    }

    private ShoppingCartData _formCart = null;
  private String selectedShoppingListId;
  private boolean deleteAllFl;
  private String confirmMessage = null;
  
  	//STJ-4081 start
  	private ShoppingCartData _formCartOG = null;
  	private ShoppingCartData _formCartIL = null;  
	public ShoppingCartData getFormCartOG() {
		return _formCartOG;
	}
	public void setFormCartOG(ShoppingCartData formCartOG) {
		_formCartOG = formCartOG;
	}
	public ShoppingCartData getFormCartIL() {
		return _formCartIL;
	}
	public void setFormCartIL(ShoppingCartData formCartIL) {
		_formCartIL = formCartIL;
	}
	//STJ-4081 end

public ShoppingCartData getShoppingCart()
    {
        return _formCart;
    }

  public ShoppingCartItemData getCartLine(int idx) {
      return getShoppingCart().getItem(idx);
  }

  public ShoppingCartItemDataVector getCartItems()
    {
        if (getShoppingCart() == null) {
            return null;
        }
      return getShoppingCart().getItems();
    }

  /**
   *Returns the curent shopcart items, same as above but has the side effect of setting the
   *_cartItems variable (moved from old getItemsAmt method).
   */
  public ShoppingCartItemDataVector getItems(){
      ShoppingCartItemDataVector scratch  = getShoppingCart().getItems();
      _cartItems = scratch;
      return scratch;
  }

  public void setCartItems(ShoppingCartItemDataVector pValue) {

      if (getShoppingCart() != null) {
          getShoppingCart().setItems(pValue);
      }
      _cartItems = getCartItems();

      int csize = 0;
      if ( null != _cartItems) {
          csize = _cartItems.size();
      }
      if ( csize > 0 ) {
          _itemIds = new int[csize];
          _orderNumbers = new int[csize];
          _quantity = new String[csize];

          for(int ii=0; ii<_cartItems.size(); ii++) {
              ShoppingCartItemData sciD = (ShoppingCartItemData) _cartItems.get(ii);
              _quantity[ii] = String.valueOf(sciD.getQuantity());
              _itemIds[ii] = sciD.getProduct().getProductId();
              _orderNumbers[ii] = sciD.getOrderNumber();
          }

          _onhand = new String[csize];
      }
  }

  public int getCartItemsSize() {

      if ( null != getShoppingCart() &&
           null != getShoppingCart().getItems() ) {
          return getShoppingCart().getItems().size();
      }
      return 0;
  }

  public List getRemovedItems() {return _removedItems;}
  public void setRemovedItems(List pValue) {_removedItems = pValue;}
  public int getRemovedItemsSize() {
    if(_removedItems==null) return 0;
    return _removedItems.size();
  }


  public boolean isCategoryChanged(int ind) {
      _cartItems = getShoppingCart().getItems();

    if(ind>=_cartItems.size()) return false;
    if(ind==0) return true;
    ShoppingCartItemData item1 = (ShoppingCartItemData) _cartItems.get(ind-1);
    ShoppingCartItemData item2 = (ShoppingCartItemData) _cartItems.get(ind);
    if(!item1.getCategoryName().equals(item2.getCategoryName())) return true;
    return false;
  }

  /////////////// Page control
  public int getPageSize() {return _pageSize;};

  public void setQuantity(String[] pValue){_quantity=pValue;}
  public String[] getQuantity() { return _quantity; }

    public void setOnhand(String[] pValue){_onhand=pValue;}
    public String[] getOnhand() { return _onhand;}

  public int[] getItemIds(){return _itemIds;}
  public void setItemIds(int[] pValue) {_itemIds = pValue;}

  public int[] getOrderNumbers(){return _orderNumbers;}
  public void setOrderNumbers(int[] pValue) {_orderNumbers = pValue;}

  public void setOffset(int pValue){_offset=pValue;}
  public int getOffset() { return _offset;}

  public void setQuantityElement(int pIndex, String pValue){
    if(pIndex<_quantity.length) {
      _quantity[pIndex]=pValue;
    }
  }

  public String getQuantityElement(int pIndex) {
    if(pIndex<_quantity.length) {
      return _quantity[pIndex];
    } else {
      return null;
    }
  }

    public void setOnhandElement(int pIndex, String pValue){
    if(pIndex<_onhand.length) {
      _onhand[pIndex]=pValue;
    }
  }

  public String getOnhandElement(int pIndex) {
    if(pIndex<_onhand.length) {
      return _onhand[pIndex];
    } else {
      return null;
    }
  }

  public void setItemIdsElement(int pIndex, int pValue){
    if(pIndex<_itemIds.length) {
      _itemIds[pIndex]=pValue;
    }
  }

  public int getItemIdsElement(int pIndex) {
    if(pIndex<_itemIds.length) {
      return _itemIds[pIndex];
    } else {
      return 0;
    }
  }

  public void setOrderNumbersElement(int pIndex, int pValue){
    if(pIndex<_orderNumbers.length && pIndex>=0) {
      _orderNumbers[pIndex]=pValue;
    }
  }

  public int getOrderNumbersElement(int pIndex) {
    if(pIndex<_orderNumbers.length && pIndex>=0) {
      return _orderNumbers[pIndex];
    } else {
      return 0;
    }
  }

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

  public boolean getJdDiscountFlag() {
    boolean discountFlag = false;
    BigDecimal sumPrice = new BigDecimal(0);
    BigDecimal sumWeight = new BigDecimal(0);
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
  public BigDecimal getSumPrice() {
      _cartItems = getShoppingCart().getItems();
    BigDecimal sumPrice = new BigDecimal(0);
    if(_cartItems!=null) {
      for(int ii=0; ii<_cartItems.size(); ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) _cartItems.get(ii);
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
            _cartItems = getShoppingCart().getItems();

    BigDecimal sumWeight = new BigDecimal(0);
    if(_cartItems!=null) {
      for(int ii=0; ii<_cartItems.size(); ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) _cartItems.get(ii);
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
            _cartItems = getShoppingCart().getItems();

    if(_cartItems==null) return 0;
    long cost = 0;
    for(int ii=0; ii<_cartItems.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) _cartItems.get(ii);
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

    public void setSiteInventory(List pInventoryInfo) {
        _inventoryInfo = pInventoryInfo;
    }

    public List getSiteInventory() {
        return _inventoryInfo;
    }

    public SiteInventoryInfoView getSiteInventoryItem(int pItemId) {

        for ( int i = 0; _inventoryInfo != null &&
                  i < _inventoryInfo.size(); i++ ) {
            SiteInventoryInfoView invitem = (SiteInventoryInfoView)
                _inventoryInfo.get(i);
            if ( invitem.getItemId() == pItemId ) {
                return invitem;
            }
        }
        return SiteInventoryInfoView.createValue();
    }


    private ContractData getContract(){
    	if(this.getAppUser() != null && this.getAppUser().getSite() != null){
			return this.getAppUser().getSite().getContractData();
    	}
    	return null;
    }

    public ShoppingCartDistDataVector getCartDistributors() {
        ShoppingCartDistDataVector cartDistV= new ShoppingCartDistDataVector(this.getCartItems(),getContract());

        return cartDistV;
    }

    public void setCartServices(ShoppingCartServiceDataVector cartServices) {
        this._cartServices = cartServices;
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

  public String getSelectedShoppingListId() {
    return selectedShoppingListId;
  }

  public boolean isDeleteAllFl() {
    return deleteAllFl;
  }

  public String getConfirmMessage() {
    return confirmMessage;
  }

  //get only discretionary cart total - xpedx
  public double getDiscretionaryCartTotalAmt(){

	  ShoppingCartItemDataVector allItems = getShoppingCart().getItems();
	  if(allItems==null)return 0;
	  double discTotal = 0;

	  for(int i=0; i<allItems.size(); i++){
		  ShoppingCartItemData thisItem = (ShoppingCartItemData) allItems.get(i);
		  if(!thisItem.getIsaInventoryItem()){
			  discTotal+=thisItem.getAmount();
		  }
	  }
	  return discTotal;
  }

    public boolean getUsePhysicalInventory() {
        return _usePhysicalInventory;
    }

    public void setUsePhysicalInventory(boolean usePhysicalInventory) {
        _usePhysicalInventory = usePhysicalInventory;
    }

    public ArrayList<PhysicalInventoryPeriod> getPhysicalInventoryPeriods() {
        return _physicalInventoryPeriods;
    }

    public void setPhysicalInventoryPeriods(ArrayList<PhysicalInventoryPeriod> periods) {
        _physicalInventoryPeriods = periods;
    }
    
    /**
     * Gets the Item Changes entries for PAR Activity table.
     * @return - List : of Item Changes 
     */
    public List<ShoppingCartData.ItemChangesEntry> getParItemActivities() {
    	
    	List<ShoppingCartData.ItemChangesEntry> parActivityEntries = null;
    	
    	try {
    		ShoppingCartData shoppingCart = getShoppingCart();
    	    ShoppingCartItemDataVector scidVector = shoppingCart.getItems();
    		if (Utility.isSet(scidVector)) {
    			parActivityEntries = new ArrayList<ShoppingCartData.ItemChangesEntry>();
    		    for(int index=0 ; index<scidVector.size(); index++) {
    		    	ShoppingCartItemData scid = (ShoppingCartItemData)scidVector.get(index);
    		    	List itemHistory = null;
    		    	
    		    	if(scid.getIsaInventoryItem() && scid.getInventoryParValue() > 0) {
    		    		itemHistory = scid.getItemShoppingCartHistory();
    		    	}
    		    	
    		    	if(Utility.isSet(itemHistory)) {
    		    		
    		    		int histLastIdx = itemHistory.size() - 1;
    		    	      for (int idx = 0; idx < itemHistory.size(); idx++) { //For: Begin
    		    	    	  ShoppingInfoData sid = (ShoppingInfoData) itemHistory.get(idx);

    		    	          if (sid.getArg0() != null && sid.getArg0().equals("nothing")) {
    		    	              // get previous. If it was the same then continue
    		    	              if (idx < histLastIdx) {
    		    	                  ShoppingInfoData sidPrev = (ShoppingInfoData) itemHistory.get(idx+1);
    		    	                  if ((sidPrev.getArg0() != null &&
    		    	                       sidPrev.getArg0().equals(sid.getArg0())) ||
    		    	                      (sidPrev.getArg1() != null &&
    		    	                       sidPrev.getArg1().equals(sid.getArg0()))
    		    	                      ) {
    		    	                      continue;
    		    	                  }
    		    	              } else {
    		    	                  continue;
    		    	              }
    		    	          }
    		    	          if (sid.getArg0() != null &&
    		    	                  sid.getArg1() != null &&
    		    	                  sid.getArg0().equals(sid.getArg1())) {
    		    	              continue;
    		    	          }
    		    	          if (sid.getArg0() != null &&
    		    	                  sid.getArg0().trim().equals("0") &&
    		    	                  sid.getMessageKey().equals("shoppingMessages.text.onHandQtySet")) {
    		    	              // get previous. If it was the same then continue
    		    	              if (idx < histLastIdx) {
    		    	                  ShoppingInfoData sidPrev = (ShoppingInfoData) itemHistory.get(idx+1);
    		    	                  if ((sidPrev.getArg0() != null &&
    		    	                       sidPrev.getArg0().equals(sid.getArg0())) ||
    		    	                      (sidPrev.getArg1() != null &&
    		    	                       sidPrev.getArg1().equals(sid.getArg0()))
    		    	                      ) {
    		    	                      continue;
    		    	                  }
    		    	              }
    		    	          }
    		    	          ShoppingCartData.ItemChangesEntry iChangeEntry = shoppingCart.mkItemChangesEntry(sid);
    		    	          iChangeEntry.setProductDesc(scid.getProduct()!=null?scid.getProduct().getCatalogProductShortDesc():"");
    		    	          iChangeEntry.setSku(scid.getActualSkuNum()!=null?scid.getActualSkuNum():"");
    		    	          parActivityEntries.add(iChangeEntry);
    		    	      } // end of for
    		    	      
    		    	}
    		    }
    	      }
    	}finally {
	    }
		return parActivityEntries;
    }

}
