package com.cleanwise.service.api.value;

/**
 * Title:        OrderItemViewData
 *
 * Description:  This is a ValueObject class wrapping
 * OrderItemData and set of OrderItemMetaData objects
 * for one ordered item
 * Purpose:      Convenient usage of order item properties
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */
import com.cleanwise.service.api.framework.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class OrderItemJoinData  extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2451613843047813824L;

  private OrderItemData _orderItem = OrderItemData.createValue();
  private OrderFreightData _orderFreight;
    
  public static int _ind=0;
  public static final int SIZE_IND=_ind++;
  public static final int PACK_IND=_ind++;
  public static final int UOM_IND=_ind++;
  public static final int COLOR_IND=_ind++;
  public static final int STANDARD_PRODUCT_LIST=_ind++;
  public static final int CATEGORY_NAME=_ind++;

  public OrderItemMetaData[] _itemMetaA = new OrderItemMetaData [_ind];
  public static final String[] _propertyNames = new String[]{
     "SIZE",
     "PACK",
     "UOM",
     "COLOR",
     "STANDARD_PRODUCT_LIST",
     "CATEGORY_NAME"
     };

     private String mStandardProductList;

  ////////////////////////////////

  public OrderItemJoinData() { }
  public OrderItemJoinData(OrderItemData pOrderItemData) {
      setOrderItem(pOrderItemData);
  }
  public void setOrderItem(OrderItemData pValue) {_orderItem = pValue;}
  public OrderItemData getOrderItem() {return _orderItem;}
    public boolean getIsAnInventoryItem() {
        if ( _orderItem.getInventoryParValue() > 0 )
        {
            return true;
        }
        return false;
    }
  public void setOrderItemMeta(OrderItemMetaDataVector pItemMetav) {
    Object[] itemMeta = pItemMetav.toArray();
    _itemMetaA = new OrderItemMetaData [_ind];
    for(int ii=0; ii<_ind; ii++) {
      String name = _propertyNames[ii];
      for(int jj=0; jj<itemMeta.length; jj++) {
        OrderItemMetaData oimD = (OrderItemMetaData) itemMeta[jj];
        if(name.equalsIgnoreCase(oimD.getName())){
          _itemMetaA[ii] = oimD;
        }
      }
    }
  }

  public void setOrderItemMetaArray(OrderItemMetaData[] pValue) {
      _itemMetaA = pValue;}
  public OrderItemMetaData[] getOrderItemMetaArray() {return _itemMetaA;}

  //////////////////////////////////////////////////////
    public void setOrderId(int pOrderId){_orderItem.setOrderId(pOrderId);}
    public int getOrderId(){return _orderItem.getOrderId();}

    public void setOrderItemId(int pOrderItemId){_orderItem.setOrderItemId(pOrderItemId);}
    public int getOrderItemId(){return _orderItem.getOrderItemId();}

    public void setShortDesc(String pShortDesc){
  _orderItem.setCustItemShortDesc(pShortDesc);}
    public String getShortDesc(){
  return _orderItem.getCustItemShortDesc();}

    public void setItemId(int pItemId){_orderItem.setOrderItemId(pItemId);}
    public int getItemId(){return _orderItem.getOrderItemId();}

    public void setQty(int pQty){_orderItem.setTotalQuantityOrdered(pQty);}
    public int getQty(){return _orderItem.getTotalQuantityOrdered();}

    public void setAmount(java.math.BigDecimal pAmount){_orderItem.setCustContractPrice(pAmount);}
    public java.math.BigDecimal getAmount(){return _orderItem.getCustContractPrice();}

    public void setItemStatusCd(String pItemStatusCd){_orderItem.setOrderItemStatusCd(pItemStatusCd);}
    public String getItemStatusCd(){return _orderItem.getOrderItemStatusCd();}


    public void setContractPrice(java.math.BigDecimal pContractPrice){_orderItem.setCustContractPrice(pContractPrice);}
    public java.math.BigDecimal getContractPrice(){return _orderItem.getCustContractPrice();}

    public void setAddDate(Date pAddDate){_orderItem.setAddDate(pAddDate);}
    public Date getAddDate(){return _orderItem.getAddDate();}

    public void setAddBy(String pAddBy){_orderItem.setAddBy(pAddBy);}
    public String getAddBy(){return _orderItem.getAddBy();}

    public void setModDate(Date pModDate){_orderItem.setModDate(pModDate);}
    public Date getModDate(){return _orderItem.getModDate();}

    public void setModBy(String pModBy){_orderItem.setModBy(pModBy);}
    public String getModBy(){return _orderItem.getModBy();}

    public void setSkuNum(String pSkuNum){
  _orderItem.setCustItemSkuNum(pSkuNum); }

    public String getSkuNum(){
  return _orderItem.getCustItemSkuNum(); }

    public void setMfgSkuNum(String pMfgSkuNum){
  _orderItem.setManuItemSkuNum(pMfgSkuNum);}

    public String getMfgSkuNum(){
  return _orderItem.getManuItemSkuNum();}


    //////////////////////////////////////////////////////
    ///               Order Meta Data                  ///
    //////////////////////////////////////////////////////

    //------------------------------------------------------


    public void setSize(String pSize){setProperty(SIZE_IND,pSize);}
    public String getSize(){return getProperty(SIZE_IND);}

    public void setPack(String pPack){setProperty(PACK_IND,pPack);}
    public String getPack(){return getProperty(PACK_IND);}

    public void setUom(String pUom){setProperty(UOM_IND,pUom);}
    public String getUom(){return getProperty(UOM_IND);}

    public void setColor(String pColor){setProperty(COLOR_IND,pColor);}
    public String getColor(){return getProperty(COLOR_IND);}

    public void setMfgName(String pMfgName){
  _orderItem.setManuItemShortDesc(pMfgName); }
    public String getMfgName(){
  return _orderItem.getManuItemShortDesc(); }


   //-----------------------------------------------------
    private void setProperty(int pInd, String pValue) {
      if(_itemMetaA[pInd]==null) {
        OrderItemMetaData orderItemMetaD = OrderItemMetaData.createValue();
        orderItemMetaD.setOrderItemId(_orderItem.getOrderItemId());
        orderItemMetaD.setName(_propertyNames[pInd]);
        orderItemMetaD.setValue(pValue);
        _itemMetaA[pInd]=orderItemMetaD;
      } else {
        _itemMetaA[pInd].setValue(pValue);
      }
    }
    public String getProperty(int pInd) {
      String value = "";
      if(_itemMetaA[pInd]!=null && _itemMetaA[pInd].getValue()!=null) {
        value = _itemMetaA[pInd].getValue();
      }
      return value;
    }

    public void setIntProperty(int pInd, int pValue) {
      setProperty(pInd,""+pValue);
    }
    public int getIntProperty(int pInd) {
      int value = 0;
      String ss = getProperty(pInd);
      try{
        value = Integer.parseInt(ss);
      } catch (NumberFormatException exc) {}
      return value;
    }

    private List mItemShoppingInfoList = null;
    public List getShoppingHistory()
    {
        if ( null == mItemShoppingInfoList ) {
            mItemShoppingInfoList = new ArrayList();
        }
        return mItemShoppingInfoList;
    }
    public void setShoppingHistory(List v)
    {
        mItemShoppingInfoList = new ArrayList();
        for ( int idx = 0; v != null && idx < v.size(); idx++ )
        {
            ShoppingInfoData sinfo = (ShoppingInfoData)v.get(idx);
            if ( sinfo.getItemId() == this.getItemId() )
            {
                mItemShoppingInfoList.add(sinfo);
            }
        }
    }

    public void setStandardProductList(String v) {
      this.mStandardProductList = v;
    }

    public String getStandardProductList() {
      return this.mStandardProductList;
    }

    public OrderFreightData getOrderFreight() {
        return _orderFreight;
    }

    public void setOrderFreight(OrderFreightData freight) {
        this._orderFreight = freight;
    }
}
