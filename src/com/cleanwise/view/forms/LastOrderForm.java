/**
 * Title:        JanitorClosetForm
 * Description:  This is the Struts ActionForm class for the shopping by janitor closet page.
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

public final class LastOrderForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
  private OrderData _order = null;
  private List _cartItems = null;
  private int _shopMethod = -1;
  private int _orderBy = Constants.ORDER_BY_CATEGORY;
  private String orderPlacedBy;
  
  //order guide
  //---------------------
  private int _pageSize=4;
  private String[] _quantity = new String[0];
  private int[] _itemIds = new int[0];
  private int _offset=0;

  //Jd begin ---
  private String _priceCode ="";
  private BigDecimal _weightThreshold = null;
  private BigDecimal _priceThreshold = null;
  private BigDecimal _contractThreshold = null;
  //Jd end
  // ---------------------------------------------------------------- Properties
  public OrderData getOrder(){return _order;}
  public void setOrder(OrderData pValue) {_order = pValue;}

  public double getItemsAmt() {
    if(_cartItems==null) return 0;
    long cost = 0;
    for(int ii=0; ii<_cartItems.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) _cartItems.get(ii);
      BigDecimal priceBD = new BigDecimal((sciD.getPrice()+.005)*100);
      long price = priceBD.longValue();
      cost+=sciD.getQuantity()*price;
    }
    double costD = cost;
    return costD/100;
  }

/*
  public String getRequestNumber(){return _requestNumber;}
  public void setRequestNumber(String pValue) {_requestNumber=pValue;}
*/
  //Shopping method
  public int getShopMethod() {return _shopMethod;}
  public void setShopMethod(int pValue) {_shopMethod = pValue;}


  public int getOrderBy() {return _orderBy;}
  public void setOrderBy(int pValue) {_orderBy = pValue;}

  public List getCartItems() {return _cartItems;}
  public void setCartItems(List pValue) {_cartItems = pValue;}

  public int getCartItemsSize() {
    if(_cartItems==null) return 0;
    return _cartItems.size();
  }

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
    if(pIndex<_quantity.length &&pIndex>=0) {
      return _quantity[pIndex];
    } else {
      return "";
    }
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
    if(_cartItems==null || _cartItems.size()==0) {
      return -1;
    }
    int currentPage = _offset/_pageSize;
    return currentPage;
  }

  public int getLastPage() {
    if(_cartItems==null || _cartItems.size()==0) {
      return -1;
    }
    int lastPage = (_cartItems.size()-1)/_pageSize;
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

    /**
     * Getter for property orderPlacedBy.
     * @return Value of property orderPlacedBy.
     */
    public String getOrderPlacedBy() {
        return this.orderPlacedBy;
    }

    /**
     * Setter for property orderPlacedBy.
     * @param orderPlacedBy New value of property orderPlacedBy.
     */
    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }
}
