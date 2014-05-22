package com.cleanwise.service.api.util;

import java.util.Collection;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.sql.Connection;
//import java.io.Serializable;


public class PadSkuFreight
{
  PriceRuleData _rule = null;
  PriceRuleDetailDataVector _ruleDetails = null;
  boolean _affectGeneralRuleFl = false;
  BigDecimal _price;
  int _accountId;
  int _siteId;
  int _contractId;
  int _ruleQty;
  BigDecimal _freightRate;
  int[] _itemIds;
  OrderHandlingItemViewVector _orderItems;
  public PadSkuFreight(PriceRuleData pRule, PriceRuleDetailDataVector pRuleDetails) 
  throws Exception
  {
    if(pRule==null) {
      String errorMess = "No Price Rule data provided";
      throw new Exception(errorMess);
    }
    if(pRuleDetails==null) {
      String errorMess = "No Price Rule Details data provided";
      throw new Exception(errorMess);
    }
    _rule = pRule;
    _ruleDetails = pRuleDetails;
    ArrayList itemIdList = new ArrayList();
    for(int ii=0; ii<pRuleDetails.size(); ii++) {
        PriceRuleDetailData paramD = (PriceRuleDetailData) pRuleDetails.get(ii);
        String paramName = paramD.getParamName();
        if(paramName!=null) paramName = paramName.toUpperCase();
        String paramValue = paramD.getParamValue();
        String errorMsg = 
          "Wrong "+paramName+" parameter format for price rule. Rule Id: "+pRule.getPriceRuleId()+
            " Param: "+paramName+" Value: "+paramValue;
        if("ACCOUNT".equals(paramName)) {
          _accountId = str2int(paramValue, errorMsg);
        } else if ("CONTRACT".equals(paramName)) {
          _contractId =  str2int(paramValue, errorMsg);
        } else if ("QUANTITY".equals(paramName)) {
          _ruleQty =  str2int(paramValue, errorMsg);
        } else if ("RATE".equals(paramName)) {
          _freightRate =  str2bd(paramValue, errorMsg);
        } else if ("ITEM".equals(paramName)) {
          int itemId =  str2int(paramValue, errorMsg);
          itemIdList.add(new Integer(itemId));
        } else if ("AFFECT_GENERAL_RULE".equals(paramName)){
           if("T".equals(paramValue) || "1".equals(paramValue) || "Y".equals(paramValue)) {
           _affectGeneralRuleFl = true;
           }
        } else {
          throw new Exception("Unexpected price rule parameter: "+paramName+" Rule id: "+pRule.getPriceRuleId());
        }
      }
    
      //Check parameters
      if(_accountId<=0) {
        throw new Exception(
          "No or wrong Account parameter for price rule. Rule Id: "+pRule.getPriceRuleId());
      }
      if(_ruleQty<0) {
        throw new Exception(
          "No or wrong Quantity parameter for price rule. Rule Id: "+pRule.getPriceRuleId());
      }
      
      if(_freightRate==null) {
        throw new Exception(
          "No Rate parameter for price rule. Rule Id: "+pRule.getPriceRuleId());
      }
      _itemIds = new int[itemIdList.size()];
      for(int ii=0; ii<_itemIds.length; ii++) {
        Integer itemIdI = (Integer) itemIdList.get(ii);
        _itemIds[ii] = itemIdI.intValue();
      }
     for(int ii=0; ii<_itemIds.length-1; ii++) {
       boolean breakFl = true;
       for(int jj=0; jj<_itemIds.length-ii-1; jj++) {
         if(_itemIds[jj]>_itemIds[jj+1]) {
         int itemId = _itemIds[jj];
           _itemIds[jj] = _itemIds[jj+1];
           _itemIds[jj+1] = itemId;
           breakFl = false;
         }
       }
       if(breakFl) break;
     }
  }
  public boolean excludeFromGeneralRule() {
    return _affectGeneralRuleFl;  
  }

  public ArrayList initOrderItems(Connection pCon, OrderHandlingView pOrder) 
  throws Exception
  {
    _orderItems = new OrderHandlingItemViewVector();
    ArrayList orderItemIds = new ArrayList();
    int accountId = pOrder.getAccountId();
    if(accountId!=_accountId) {
      return orderItemIds;
    }
    int contractId = pOrder.getContractId();
    if(contractId!=_contractId) {
      return orderItemIds;
    }
    Object[] items = pOrder.getItems().toArray();
    for(int ii=0; ii<items.length-1; ii++) {
      boolean breakFl = true;
      for(int jj=0; jj<items.length-ii-1; jj++) {
        OrderHandlingItemView oi1 = (OrderHandlingItemView) items[jj];
        OrderHandlingItemView oi2 = (OrderHandlingItemView) items[jj+1];
        if(oi1.getItemId()>oi2.getItemId()) {
          items[jj] = oi2;
          items[jj+1] = oi1;
          breakFl = false;
        }
      }
      if(breakFl) break;
    }
    for(int ii=0, jj=0; ii<items.length; ii++) {
      OrderHandlingItemView oi = (OrderHandlingItemView) items[ii];
      int itemId = oi.getItemId();
      for(;jj<_itemIds.length;jj++) {
        if(itemId==_itemIds[jj]) {
          orderItemIds.add(new Integer(itemId));
          _orderItems.add(oi);
          jj++;
          break;
        }
        if(itemId<_itemIds[jj]) {
          break;
        }
      }
    }
    return orderItemIds;
  }

  public BigDecimal calcFreight() 
  throws Exception
  {
    int sumQty = 0;
    BigDecimal sumPrice = new BigDecimal(0);
    for(int ii=0; ii<_orderItems.size(); ii++) {
      OrderHandlingItemView item = (OrderHandlingItemView) _orderItems.get(ii);
      int qty = item.getQty();
      sumQty += qty;
      if(sumQty>=_ruleQty) {
        break;
      }
      BigDecimal price = item.getPrice();
      if(price==null) price = new BigDecimal(0);
      price = price.multiply(new BigDecimal(qty));
      sumPrice = sumPrice.add(price.multiply(new BigDecimal(qty)));
    }
    BigDecimal freight = new BigDecimal(0);
    if(sumQty>0 && sumQty<_ruleQty) {
      freight = sumPrice.multiply(_freightRate);
    }
    return freight;
  }
  
   private int str2int(String pValue, String ErrorMsg) 
   throws Exception
   {
      int result = 0;
      try {
        result = Integer.parseInt(pValue);
      } catch (Exception exc) {
        throw new Exception(ErrorMsg);
      }
      
      return result;
   }
      
   private BigDecimal str2bd(String pValue, String ErrorMsg) 
   throws Exception
   {
      BigDecimal result = null;
      try {
        result = new BigDecimal(pValue);
      } catch (Exception exc) {
        throw new Exception(ErrorMsg);
      }
      
      return result;
   }
}
