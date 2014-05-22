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
//import java.io.Serializable


public class SkuPriceFreight
{
  PriceRuleData _rule = null;
  PriceRuleDetailDataVector _ruleDetails = null;
  boolean _affectGeneralRuleFl = false;
  BigDecimal _price;
  int _accountId;
  BigDecimal _thresholdAmount;
  int[] _itemIds;
  OrderHandlingItemViewVector _orderItems;
  public SkuPriceFreight(PriceRuleData pRule, PriceRuleDetailDataVector pRuleDetails) 
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
    for(int ii=0; ii<_ruleDetails.size(); ii++) {
      PriceRuleDetailData prdD = (PriceRuleDetailData) _ruleDetails.get(ii);
      String param = prdD.getParamName();
      if(param!=null) param = param.toUpperCase();
      String val = prdD.getParamValue();
      if("AFFECT_GENERAL_RULE".equals(param)){
        if("T".equalsIgnoreCase(val) || "1".equals(val) || "Y".equalsIgnoreCase(val)) {
          _affectGeneralRuleFl = true;
        }
      } 
      else if("ITEM".equals(param)){
        try{
          Integer valI = new Integer(val);
          itemIdList.add(valI);        
        }catch(Exception exc) {
           String errorMess = "Wrong item id format for freight rule. Rule id="+
              pRule.getPriceRuleId()+" Item id="+val;
           throw new Exception(errorMess);
        }
      }
      else if("PRICE".equals(param)){
        try{
          _price = new BigDecimal(val);
          _price.setScale(2,BigDecimal.ROUND_HALF_UP);
        } catch (Exception exc) {
           String errorMess = "Wrong price format for freight rule. Rule id="+
              pRule.getPriceRuleId()+" Price="+val;
           throw new Exception(errorMess);
        }
      }
      else if("ACCOUNT".equals(param)){
        try{
          _accountId = Integer.parseInt(val);
        } catch (Exception exc) {
           String errorMess = "Wrong account number format for freight rule. Rule id="+
              pRule.getPriceRuleId()+" Account="+val;
           throw new Exception(errorMess);
        }
      }
      else if("THRESHOLD_AMOUNT".equals(param)){
        try{
          _thresholdAmount = new BigDecimal(val);
        } catch (Exception exc) {
           String errorMess = "Wrong threshold amount format for freight rule. Rule id="+
              pRule.getPriceRuleId()+" Threshold amount="+val;
           throw new Exception(errorMess);
        }
      }
    }
    _itemIds = new int[itemIdList.size()];
    for(int ii=0; ii<_itemIds.length; ii++) {
      Object ooo = itemIdList.get(ii);
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
    BigDecimal sumPrice = new BigDecimal(0);
    for(int ii=0; ii<_orderItems.size(); ii++) {
      OrderHandlingItemView oiVw = (OrderHandlingItemView) _orderItems.get(ii);
      BigDecimal itemPrice = oiVw.getPrice();
      if(itemPrice!=null) {
        int qty = oiVw.getQty();
        BigDecimal linePrice = itemPrice.multiply(new BigDecimal(qty));
        sumPrice = sumPrice.add(linePrice);
      }
    }
    BigDecimal freight = new BigDecimal(0);
    if(_orderItems.size()>0 && sumPrice.compareTo(_thresholdAmount)<0) {
      freight = _price;
    }
    freight.setScale(2,BigDecimal.ROUND_HALF_UP);
    return freight;
  }
}
