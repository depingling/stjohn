package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Connection;

import org.apache.log4j.Logger;

public class TotalAmountFreight
{
  private static final Logger log = Logger.getLogger(TotalAmountFreight.class);
  
  PriceRuleData _rule = null;
  PriceRuleDetailDataVector _ruleDetails = null;
  boolean _affectGeneralRuleFl = false;
  ArrayList _zipCodes = null;
  BigDecimal _rate = null;
  IdVector _accountIdV = null;
  int _siteId;
  BigDecimal _orderPrice = null;
  public TotalAmountFreight(PriceRuleData pRule, PriceRuleDetailDataVector pRuleDetails) 
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
    _zipCodes = new ArrayList();
    _accountIdV = new IdVector();
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
      else if("ZIP_CODE".equals(param)){
        val = val.trim();
        if(val.length()>=5) {
          _zipCodes.add(val);
        } else {
           String errorMess = "Wrong zip code format = "+val;
           throw new Exception(errorMess);
        }
      }
      else if("RATE".equals(param)){
        try{
          _rate = new BigDecimal(val);
          _rate = _rate.setScale(4,BigDecimal.ROUND_HALF_UP);
        } catch (Exception exc) {
           String errorMess = "Wrong price format for freight rule. Rule id="+
              pRule.getPriceRuleId()+" Price="+val;
           throw new Exception(errorMess);
        }
      }
      else if("ACCOUNT".equals(param)){
        try{
          _accountIdV.add(Integer.decode(val));
        } catch (Exception exc) {
           String errorMess = "Wrong account number format for freight rule. Rule id="+
              pRule.getPriceRuleId()+" Account="+val;
           throw new Exception(errorMess);
        }
      }
    }
  }
  public boolean excludeFromGeneralRule() {
    return _affectGeneralRuleFl;  
  }


  public ArrayList initOrderItems(Connection pCon, OrderHandlingView pOrder) 
  throws Exception
  {
    _orderPrice = new BigDecimal(0);
    ArrayList orderItemIds = new ArrayList();
    int accountId = pOrder.getAccountId();
    //Check account
    if(_accountIdV==null) {
      return orderItemIds;
    }
    boolean foundFl = false;
    for(Iterator iter = _accountIdV.iterator(); iter.hasNext();) {
       Integer idI = (Integer) iter.next();
       int id = idI.intValue();
       if(accountId==id) {
         foundFl = true;
         break;
       }
    }
    if(!foundFl) {
      return orderItemIds;
    }
    
    //Zip code 
    int siteId = pOrder.getSiteId();
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,siteId);
    dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                                     RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
    dbc.addEqualTo(AddressDataAccess.ADDRESS_STATUS_CD,
                                     RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
    
    AddressDataVector addressDV = AddressDataAccess.select(pCon,dbc);
    if(addressDV.size()==0) {
      String errorMess = "No shipping address. Site id = "+ siteId;
      log.info("TotalAmountFreight.initOrderItems, " + errorMess);
      return orderItemIds;
    }
    if(addressDV.size()>1) {
      String errorMess = "Multiple shipping addresses. Site id = "+ siteId;
      throw new Exception(errorMess);
    }
    
    AddressData addressD = (AddressData) addressDV.get(0);
    String zipCode = addressD.getPostalCode();
    if(zipCode==null) {
      return orderItemIds;
    }
    
    zipCode = zipCode.trim();
    foundFl = false;
    for(Iterator iter = _zipCodes.iterator(); iter.hasNext();) {
      String zp = (String) iter.next();
      if(zipCode.startsWith(zp)) {
        foundFl = true;
        break;
      }
    }
    if(!foundFl) {
      return orderItemIds;
    }
    
    for(Iterator iter = pOrder.getItems().iterator(); iter.hasNext();) {
      OrderHandlingItemView ohiVw = (OrderHandlingItemView) iter.next();
      int itemId = ohiVw.getItemId();
      orderItemIds.add(new Integer(itemId));
      int qty = ohiVw.getQty();
      BigDecimal price = ohiVw.getPrice();
      BigDecimal amount = price.multiply(new BigDecimal(qty));
      amount = amount.setScale(2,BigDecimal.ROUND_HALF_UP);
      _orderPrice = _orderPrice.add(amount);
    }
    _orderPrice = _orderPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
    return orderItemIds;
  }

  public BigDecimal calcFreight() 
  throws Exception
  {
    BigDecimal freight = _orderPrice;
    if(_rate==null) {
      String errorMess = "Now freigh rate for the price rule. Rule id = "+ 
                                                        _rule.getPriceRuleId();
      throw new Exception(errorMess);
    }
    if(freight==null) {
      String errorMess = "Freigh rule was not initialized with order information "; 
      throw new Exception(errorMess);
    }
    freight = freight.multiply(_rate);
    freight = freight.setScale(2,BigDecimal.ROUND_HALF_UP);
    return freight;
  }
}
