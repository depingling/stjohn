/**
 * Title:        UserShopForm
 * Description:  This is the Struts ActionForm class for the shopping page.
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

public final class EditOrderGuideForm extends ActionForm {

  // -------------------------------------------------------- Instance Variables
    private List _items = null, _itemsAvailable = null;
  private List _removedItems = null;
  //Shopping method
  //order
  private int _orderBy = 0;
  //order guide
    private OrderGuideDataVector _userOrderGuides = null,
	_allUserOrderGuides = null;
//  private OrderGuideDataVector _templateOrderGuides = null;
  private String _shortDesc = "";
  private int _inputOrderGuideId = -1;
  private int _orderGuideId = -1;
  private boolean _changesFlag = false;
  //---------------------
  private int _pageSize=4;
  private String[] _quantity = new String[0];
  private int[] _itemIds = new int[0];
  private int[] _orderNumbers = new int[0];
  private long[] _selectBox = new long[0];
  private long[] _selectToAddBox = new long[0];
  private int[] _categoryIds = new int[0];
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
  //Jd end


  // ----------------------------------------------------------------
  public void setChangesFlag (boolean  pValue) {_changesFlag = pValue;}
  public boolean getChangesFlag() {return _changesFlag;}
  
  public void setShortDesc(String pValue) {_shortDesc = pValue;}
  public String getShortDesc() {return _shortDesc;}

  public void setSelectBox(long[] pValue) {_selectBox = pValue;}
  public long[] getSelectBox() {return _selectBox;}


  public void setSelectToAddBox(long[] pValue) {_selectToAddBox = pValue;}
  public long[] getSelectToAddBox() {return _selectToAddBox;}


  // ---------------------------------------------------------------- Properties
  public double getItemsAmt() {
    if(_items==null) return 0;
    long cost = 0;
    for(int ii=0; ii<_items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) _items.get(ii);
      if(!sciD.getDuplicateFlag()) {
        BigDecimal priceBD = new BigDecimal((sciD.getPrice()+.005)*100);
        long price = priceBD.longValue();
        cost+=sciD.getQuantity()*price;
      }
    }
    double costD = cost;
    return costD/100;
  }


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

    OrderGuideData _currentOrderGuide = null;
    public OrderGuideData getOrderGuide() {return _currentOrderGuide;}
    public void setOrderGuide(OrderGuideData pValue) {_currentOrderGuide = pValue;}

  //User order quides
  public OrderGuideDataVector getUserOrderGuides() {return _userOrderGuides;}
  public void setUserOrderGuides(OrderGuideDataVector pValue) {_userOrderGuides = pValue;}
  public OrderGuideDataVector getAllUserOrderGuides() {
      return _allUserOrderGuides;}
  public void setAllUserOrderGuides(OrderGuideDataVector pValue) {
      _allUserOrderGuides = pValue;}

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

  public int getInputOrderGuideId() {return _inputOrderGuideId;}
  public void setInputOrderGuideId(int pValue) {_inputOrderGuideId = pValue;}

  //Template orde guides
//  public OrderGuideDataVector getTemplateOrderGuides() {return _templateOrderGuides;}
//  public void setTemplateOrderGuides(OrderGuideDataVector pValue) {_templateOrderGuides = pValue;}

//  public ArrayList getTemplateOrderGuideIds() {
//    ArrayList al = new ArrayList();
//   al.add("-1");
//    if(_templateOrderGuides == null) return al;
//    for(int ii=0; ii<_templateOrderGuides.size(); ii++) {
//      OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
//      al.add(""+ogD.getOrderGuideId());
//    }
//    return al;
//  }

//  public ArrayList getTemplateOrderGuideNames() {
//    ArrayList al = new ArrayList();
//    al.add("-- Template Order Guides --");
//   if(_templateOrderGuides == null) return al;
//    for(int ii=0; ii<_templateOrderGuides.size(); ii++) {
//      OrderGuideData ogD = (OrderGuideData) _templateOrderGuides.get(ii);
//      al.add(ogD.getShortDesc());
//    }
//    return al;
//  }
//  public int getTemplateOrderGuideNumber() {
//    if(_templateOrderGuides == null) return 0;
//    return _templateOrderGuides.size();
//  }

  public int getOrderBy() {return _orderBy;}
  public void setOrderBy(int pValue) {_orderBy = pValue;}

  public List getItems() {return _items;}
  public void setItems(List pValue) {_items = pValue;}
  public int getItemsSize() {
    if(_items==null) return 0;
    return _items.size();
  }

  public List getItemsAvailable() {
      if ( null == _itemsAvailable ) {
	  _itemsAvailable = new ArrayList();
      }
      return _itemsAvailable;
  }
  public void setItemsAvailable(List pValue) {_itemsAvailable = pValue;}

  public List getRemovedItems() {return _removedItems;}
  public void setRemovedItems(List pValue) {_removedItems = pValue;}
  public int getRemovedItemsSize() {
    if(_removedItems==null) return 0;
    return _removedItems.size();
  }


  public boolean isCategoryChanged(int ind) {
    if(ind>=_items.size()) return false;
    if(ind==0) return true;
    ShoppingCartItemData item1 = (ShoppingCartItemData) _items.get(ind-1);
    ShoppingCartItemData item2 = (ShoppingCartItemData) _items.get(ind);
    if(!item1.getCategoryName().equals(item2.getCategoryName())) return true;
    return false;
  }

  public boolean isCategoryChanged2(int ind) {
    if(ind>=_itemsAvailable.size()) return false;
    if(ind==0) return true;
    ShoppingCartItemData item1 = (ShoppingCartItemData) _itemsAvailable.get(ind-1);
    ShoppingCartItemData item2 = (ShoppingCartItemData) _itemsAvailable.get(ind);
    if(!item1.getCategoryName().equals(item2.getCategoryName())) return true;
    return false;
  }


  /////////////// Page control
  public int getPageSize() {return _pageSize;};

  public void setQuantity(String[] pValue){_quantity=pValue;}
  public String[] getQuantity() { return _quantity;}

  public int[] getItemIds(){return _itemIds;}
  public void setItemIds(int[] pValue) {_itemIds = pValue;}

  public int[] getCategoryIds(){return _categoryIds;}
  public void setCategoryIds(int[] pValue) {_categoryIds = pValue;}

  public void setCategoryIdsElement(int pIndex, int pValue){
    if(pIndex<_categoryIds.length) {
      _categoryIds[pIndex]=pValue;
    }
  }

  public int getCategoryIdsElement(int pIndex) {
    if(pIndex<_categoryIds.length) {
      return _categoryIds[pIndex];
    } else {
      return -1;
    }
  }

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
  //{{{{ Page navigation methods (begin)
/*
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
*/
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
    BigDecimal sumPrice = new BigDecimal(0);
    if(_items!=null) {
      for(int ii=0; ii<_items.size(); ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) _items.get(ii);
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
    if(_items!=null) {
      for(int ii=0; ii<_items.size(); ii++) {
        ShoppingCartItemData item = (ShoppingCartItemData) _items.get(ii);
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
  public double getDiscItemsAmt() {
    if(_items==null) return 0;
    long cost = 0;
    for(int ii=0; ii<_items.size(); ii++) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) _items.get(ii);
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

}
