/**
 *  Title: OrderViewData Description: This is a ValueObject encapsulating order
 *  information. Purpose: Encapsulate the whole order information in one object
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Yuriy Kupershmidt
 */
package com.cleanwise.service.api.value;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;
import java.util.Date;
import java.util.Iterator;
import java.math.BigDecimal;

/**
 *  An order object is composed of: Order ::= (1) CLW_ORDER, (2..*)
 *  CLW_ORDER_ADDRESS, (0..*) CLW_ORDER_META, (1..*) CLW_ORDER_ITEM
 *  CLW_ORDER_ADDRESS - 2 entries are needed, one for the ship to address for
 *  the order and another to hold the bill to address for the order.
 *  CLW_ORDER_META - collection of name value pairs associated with this order.
 *  This may contain information shuch as a creadit card number if one was used
 *  to make the purchase.
 *
 *@author     dvieira
 *@created    June 10, 2003
 */
public class OrderJoinData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -1046843567524629844L;



    /**
     *  Constructor for the OrderJoinData object
     */
    public OrderJoinData() {
        _order = OrderData.createValue();
    }


    // Main order info, store in the CLW_ORDER table.
    private OrderData _order = OrderData.createValue();
    private OrderItemJoinDataVector _orderJoinItems = null;
    private String _message = null;
    private ReplacedOrderViewVector _replacedOrders = null;
    private OrderData _consolidatedOrder = null;
    private OrderCreditCardData _orderCC = null;



    /**
     *  Sets the order attribute of the OrderJoinData object
     *
     *@param  pValue  The new order value
     */
    public void setOrder(OrderData pValue) {
        _order = pValue;
    }


    /**
     *  Sets the orderData attribute of the OrderJoinData object
     *
     *@param  pValue  The new orderData value
     */
    public void setOrderData(OrderData pValue) {
        this.setOrder(pValue);
    }


    /**
     *  Gets the order attribute of the OrderJoinData object
     *
     *@return    The order value
     */
    public OrderData getOrder() {
        return _order;
    }


    /**
     *  Gets the orderData attribute of the OrderJoinData object
     *
     *@return    The orderData value
     */
    public OrderData getOrderData() {
        return this.getOrder();
    }


    /**
     *  Gets the order attribute of the ReplacedOrders object
     *
     *@return    The replacedOrders value
     */
    public ReplacedOrderViewVector getReplacedOrders() {
        return _replacedOrders;
    }

    /**
     *  Sets the ReplacedOrders attribute
     *
     *@param  pValue  The new ReplacedOrders value
     */
    public void setReplacedOrders(ReplacedOrderViewVector pValue) {
      _replacedOrders = pValue;
    }

    /**
     *  Gets the order attribute of the ConsolidatedOrder object
     *
     *@return    The ConsolidatedOrder value
     */
    public OrderData getConsolidatedOrder() {
        return _consolidatedOrder;
    }

    /**
     *  Sets the ConsolidatedOrder attribute
     *
     *@param  pValue  The new ConsolidatedOrder value
     */
    public void setConsolidatedOrder(OrderData pValue) {
      _consolidatedOrder = pValue;
    }

    /**
     *  Gets the order attribute of the OrderCC object
     *
     *@return    The OrderCC value
     */
    public OrderCreditCardData getOrderCC() {
        return _orderCC;
    }

    /**
     *  Sets the OrderCC attribute
     *
     *@param  pValue  The new OrderCC value
     */
    public void setOrderCC(OrderCreditCardData pValue) {
      _orderCC = pValue;
    }

    // Ship to information for this order.
    // Stored in the CLW_ORDER_ADDRESS table.
    OrderAddressData _orderShipTo =
            OrderAddressData.createValue();


    /**
     *  Sets the shipTo attribute of the OrderJoinData object
     *
     *@param  pValue  The new shipTo value
     */
    public void setShipTo(OrderAddressData pValue) {
        _orderShipTo = pValue;
    }


    /**
     *  Gets the shipTo attribute of the OrderJoinData object
     *
     *@return    The shipTo value
     */
    public OrderAddressData getShipTo() {
        return _orderShipTo;
    }


    /**
     *  Sets the shipShortDesc attribute of the OrderJoinData object
     *
     *@param  pShipShortDesc  The new shipShortDesc value
     */
    public void setShipShortDesc(String pShipShortDesc) {
        _orderShipTo.setShortDesc(pShipShortDesc);
    }


    /**
     *  Gets the shipShortDesc attribute of the OrderJoinData object
     *
     *@return    The shipShortDesc value
     */
    public String getShipShortDesc() {
        return _orderShipTo.getShortDesc();
    }


    // Bill to information for this order.
    // Stored in the CLW_ORDER_ADDRESS table.
    OrderAddressData _orderBillTo =
            OrderAddressData.createValue();


    /**
     *  Sets the billTo attribute of the OrderJoinData object
     *
     *@param  pValue  The new billTo value
     */
    public void setBillTo(OrderAddressData pValue) {
        _orderBillTo = pValue;
    }


    /**
     *  Gets the billTo attribute of the OrderJoinData object
     *
     *@return    The billTo value
     */
    public OrderAddressData getBillTo() {
        return _orderBillTo;
    }


    // Ship via info.
    // Stored in the CLW_ORDER_ADDRESS table.
    FreightHandlerView _orderShipVia = null;


    /**
     *  Sets the shipVia attribute of the OrderJoinData object
     *
     *@param  pValue  The new shipVia value
     */
    public void setShipVia(FreightHandlerView pValue) {
        _orderShipVia = pValue;
    }


    /**
     *  Gets the shipVia attribute of the OrderJoinData object
     *
     *@return    The shipVia value
     */
    public FreightHandlerView getShipVia() {
        return _orderShipVia;
    }


    private OrderMetaDataVector _orderMetaDV = null;


    /**
     *  Sets the orderMeta attribute of the OrderJoinData object
     *
     *@param  pMeta  The new orderMeta value
     */
    public void setOrderMeta(OrderMetaDataVector pMeta) {
        _orderMetaDV = pMeta;
    }

    public OrderMetaDataVector getOrderMeta() {
       return _orderMetaDV;
    }

    //returns the latest value
    public String getMetaValue(String pName) {
      if(_orderMetaDV==null || pName==null) return null;
      Date modDate = null;
      String retValue = null;
      for(int ii=0; ii<_orderMetaDV.size(); ii++) {
        OrderMetaData omD = (OrderMetaData) _orderMetaDV.get(ii);
        String name = omD.getName();
        if(pName.equals(name)) {
          if(modDate==null) {
            modDate = _order.getModDate();
            retValue = omD.getValue();
          } else {
            Date mD = _order.getModDate();
            if(mD==null) continue;
            if(modDate.before(mD)) {
              modDate = mD;
              retValue = omD.getValue();
            }
          }
        }
      }
      return retValue;
    }

    public OrderMetaData getMetaObject(String pName) {
      if(_orderMetaDV==null || pName==null) return null;
      Date modDate = null;
      OrderMetaData retObject = null;
      for(int ii=0; ii<_orderMetaDV.size(); ii++) {
        OrderMetaData omD = (OrderMetaData) _orderMetaDV.get(ii);
        String name = omD.getName();
        if(pName.equals(name)) {
          if(modDate==null) {
            modDate = _order.getModDate();
            retObject = omD;
          } else {
            Date mD = _order.getModDate();
            if(mD==null) continue;
            if(modDate.before(mD)) {
              modDate = mD;
              retObject = omD;
            }
          }
        }
      }
      return retObject;
    }

   /**
   *Returns true if this order has been reieved against
    */
    public boolean isReceived(){
        if(_orderProperties == null){
            return false;
        }
        Iterator it = _orderProperties.iterator();
        while(it.hasNext()){
            OrderPropertyData oi = (OrderPropertyData) it.next();
            if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED.equals(oi.getValue())){
                return true;
            }
        }
        return false;

    }

    private OrderPropertyDataVector _orderProperties = null;
    public void setOrderProperties(OrderPropertyDataVector pOrderProperties) {
        _orderProperties = pOrderProperties;
    }

    public OrderPropertyDataVector getOrderProperties() {
       return _orderProperties;
    }

    //returns the latest value
    public OrderPropertyDataVector getOrderProperties
	(String pShortDesc, String pOrderPropertyType, String pStatus) {

	OrderPropertyDataVector retVector = new OrderPropertyDataVector();
	if(_orderProperties==null) return retVector;
	for(int ii=0; ii<_orderProperties.size(); ii++) {
	    OrderPropertyData opD = (OrderPropertyData)
		_orderProperties.get(ii);
	    String shortDesc = opD.getShortDesc();
	    String status = opD.getOrderPropertyStatusCd();
	    String type = opD.getOrderPropertyTypeCd();
	    if(pShortDesc!=null && !pShortDesc.equals(shortDesc)) continue;
	    if(pOrderPropertyType!=null &&
	       !pOrderPropertyType.equals(type)) continue;
	    if(pStatus!=null && !pStatus.equals(status)) continue;
	    retVector.add(opD);
	}
	return retVector;
    }

    /**
     *  Sets the orderJoinItems attribute of the OrderJoinData object
     *
     *@param  pValue  The new orderJoinItems value
     */
    public void setOrderJoinItems(OrderItemJoinDataVector pValue) {
        _orderJoinItems = pValue;
    }


    /**
     *  Gets the orderJoinItems attribute of the OrderJoinData object
     *
     *@return    The orderJoinItems value
     */
    public OrderItemJoinDataVector getOrderJoinItems() {
        return _orderJoinItems;
    }

    public boolean getOrderHasInventoryItem() {

        for ( int i = 0; null != _orderJoinItems &&
                  i < _orderJoinItems.size(); i++ )
        {
            OrderItemJoinData oijd = (OrderItemJoinData)_orderJoinItems.get(i);
            if ( oijd.getIsAnInventoryItem() )
            {
                return true;
            }
        }
        return false;
    }

    /**
     *  Sets the message attribute of the OrderJoinData object
     *
     *@param  pValue  The new message value
     */
    public void setMessage(String pValue) {
        _message = pValue;
    }


    /**
     *  Gets the message attribute of the OrderJoinData object
     *
     *@return    The message value
     */
    public String getMessage() {
        return _message;
    }


    //////////////////////////////////////////////////////
    /**
     *  Sets the orderId attribute of the OrderJoinData object
     *
     *@param  pOrderId  The new orderId value
     */
    public void setOrderId(int pOrderId) {
        _order.setOrderId(pOrderId);
    }


    /**
     *  Gets the orderId attribute of the OrderJoinData object
     *
     *@return    The orderId value
     */
    public int getOrderId() {
        return _order.getOrderId();
    }

    //------------------------------------------------------

    /**
     *  Sets the orderNum attribute of the OrderJoinData object
     *
     *@param  pOrderNum  The new orderNum value
     */
    public void setOrderNum(String pOrderNum) {
        _order.setOrderNum(pOrderNum);
    }


    /**
     *  Gets the orderNum attribute of the OrderJoinData object
     *
     *@return    The orderNum value
     */
    public String getOrderNum() {
        return _order.getOrderNum();
    }

    //------------------------------------------------------

    /**
     *  Sets the userId attribute of the OrderJoinData object
     *
     *@param  pUserId  The new userId value
     */
    public void setUserId(int pUserId) {
        _order.setUserId(pUserId);
    }


    /**
     *  Gets the userId attribute of the OrderJoinData object
     *
     *@return    The userId value
     */
    public int getUserId() {
        return _order.getUserId();
    }

    //------------------------------------------------------

    /**
     *  Sets the poNum attribute of the OrderJoinData object
     *
     *@param  pPoNum  The new poNum value
     */
    public void setPoNum(String pPoNum) {
        _order.setRequestPoNum(pPoNum);
    }


    /**
     *  Gets the poNum attribute of the OrderJoinData object
     *
     *@return    The poNum value
     */
    public String getPoNum() {
        return _order.getRequestPoNum();
    }

    //------------------------------------------------------

    /**
     *  Sets the costCenterId attribute of the OrderJoinData object
     *
     *@param  pCostCenterId  The new costCenterId value
     */
    public void setCostCenterId(int pCostCenterId) {
        _order.setCostCenterId(pCostCenterId);
    }


    /**
     *  Gets the costCenterId attribute of the OrderJoinData object
     *
     *@return    The costCenterId value
     */
    public int getCostCenterId() {
        return _order.getCostCenterId();
    }

    //------------------------------------------------------

    // zzz no desc now
    /**
     *  Sets the shortDesc attribute of the OrderJoinData object
     *
     *@param  pShortDesc  The new shortDesc value
     */
    public void setShortDesc(String pShortDesc) {
        return;
    }


    /**
     *  Gets the shortDesc attribute of the OrderJoinData object
     *
     *@return    The shortDesc value
     */
    public String getShortDesc() {
        return "";
    }

    //------------------------------------------------------

    // zzz no desc now
    /**
     *  Sets the longDesc attribute of the OrderJoinData object
     *
     *@param  pLongDesc  The new longDesc value
     */
    public void setLongDesc(String pLongDesc) {
        return;
    }


    /**
     *  Gets the longDesc attribute of the OrderJoinData object
     *
     *@return    The longDesc value
     */
    public String getLongDesc() {
        return "";
    }

    //------------------------------------------------------

    /**
     *  Sets the refOrderNum attribute of the OrderJoinData object
     *
     *@param  pRefOrderNum  The new refOrderNum value
     */
    public void setRefOrderNum(String pRefOrderNum) {
        _order.setRefOrderNum(pRefOrderNum);
    }


    /**
     *  Gets the refOrderNum attribute of the OrderJoinData object
     *
     *@return    The refOrderNum value
     */
    public String getRefOrderNum() {
        return _order.getRefOrderNum();
    }

    //------------------------------------------------------

    /**
     *  Sets the accountNum attribute of the OrderJoinData object
     *
     *@param  pAccountNum  The new accountNum value
     */
    public void setAccountNum(String pAccountNum) {
        _order.setAccountErpNum(pAccountNum);
    }


    /**
     *  Gets the accountNum attribute of the OrderJoinData object
     *
     *@return    The accountNum value
     */
    public String getAccountNum() {
        return String.valueOf(_order.getAccountErpNum());
    }


    /**
     *  Sets the orderTypeCd attribute of the OrderJoinData object
     *
     *@param  pOrderTypeCd  The new orderTypeCd value
     */
    public void setOrderTypeCd(String pOrderTypeCd) {
        _order.setOrderTypeCd(pOrderTypeCd);
    }


    /**
     *  Gets the orderTypeCd attribute of the OrderJoinData object
     *
     *@return    The orderTypeCd value
     */
    public String getOrderTypeCd() {
        return _order.getOrderTypeCd();
    }

    //------------------------------------------------------

    /**
     *  Sets the orderStatusCd attribute of the OrderJoinData object
     *
     *@param  pOrderStatusCd  The new orderStatusCd value
     */
    public void setOrderStatusCd(String pOrderStatusCd) {
        _order.setOrderStatusCd(pOrderStatusCd);
    }


    /**
     *  Gets the orderStatusCd attribute of the OrderJoinData object
     *
     *@return    The orderStatusCd value
     */
    public String getOrderStatusCd() {
        return _order.getOrderStatusCd();
    }

    //------------------------------------------------------

    /**
     *  Sets the workflowRoleCd attribute of the OrderJoinData object
     *
     *@param  pWorkflowCd  The new workflowRoleCd value
     */
    public void setWorkflowRoleCd(String pWorkflowCd) {
        _order.setWorkflowStatusCd(pWorkflowCd);
    }


    /**
     *  Gets the workflowRoleCd attribute of the OrderJoinData object
     *
     *@return    The workflowRoleCd value
     */
    public String getWorkflowRoleCd() {
        return _order.getWorkflowStatusCd();
    }

    //------------------------------------------------------

    /**
     *  Sets the amount attribute of the OrderJoinData object
     *
     *@param  pAmount  The new amount value
     */
    public void setAmount(java.math.BigDecimal pAmount) {
        _order.setOriginalAmount(pAmount);
    }


    /**
     *  Gets the amount attribute of the OrderJoinData object
     *
     *@return    The amount value
     */
    public java.math.BigDecimal getAmount() {
        return _order.getOriginalAmount();
    }

    //------------------------------------------------------

    /**
     *  Sets the orderDate attribute of the OrderJoinData object
     *
     *@param  pOrderDate  The new orderDate value
     */
    public void setOrderDate(Date pOrderDate) {
        _order.setOriginalOrderDate(pOrderDate);
    }

    /**
     *  Sets the orderTime attribute of the OrderJoinData object
     *
     *@param  pOrderTime  The new orderDate value
     */
    public void setOrderTime(Date pOrderTime) {
        _order.setOriginalOrderTime(pOrderTime);
    }

    /**
     *  Gets the orderDate attribute of the OrderJoinData object
     *
     *@return    The orderDate value
     */
    public Date getOrderDate() {
        return _order.getOriginalOrderDate();
    }
    /**
      *  Gets the orderTime attribute of the OrderJoinData object
      *
      *@return    The orderTime value
      */
     public Date getOrderTime() {
         return _order.getOriginalOrderTime();
     }

    //------------------------------------------------------

    /**
     *  Sets the comments attribute of the OrderJoinData object
     *
     *@param  pComments  The new comments value
     */
    public void setComments(String pComments) {
        _order.setComments(pComments);
    }


    /**
     *  Gets the comments attribute of the OrderJoinData object
     *
     *@return    The comments value
     */
    public String getComments() {
        return _order.getComments();
    }

    //------------------------------------------------------

    /**
     *  Sets the localeCd attribute of the OrderJoinData object
     *
     *@param  pLocaleCd  The new localeCd value
     */
    public void setLocaleCd(String pLocaleCd) {
        _order.setLocaleCd(pLocaleCd);
    }


    /**
     *  Gets the localeCd attribute of the OrderJoinData object
     *
     *@return    The localeCd value
     */
    public String getLocaleCd() {
        return _order.getLocaleCd();
    }

    //------------------------------------------------------

    /**
     *  Sets the currencyCd attribute of the OrderJoinData object
     *
     *@param  pCurrencyCd  The new currencyCd value
     */
    public void setCurrencyCd(String pCurrencyCd) {
        _order.setCurrencyCd(pCurrencyCd);
    }


    /**
     *  Gets the currencyCd attribute of the OrderJoinData object
     *
     *@return    The currencyCd value
     */
    public String getCurrencyCd() {
        return _order.getCurrencyCd();
    }

    //------------------------------------------------------

    /**
     *  Sets the addDate attribute of the OrderJoinData object
     *
     *@param  pAddDate  The new addDate value
     */
    public void setAddDate(Date pAddDate) {
        _order.setAddDate(pAddDate);
    }


    /**
     *  Gets the addDate attribute of the OrderJoinData object
     *
     *@return    The addDate value
     */
    public Date getAddDate() {
        return _order.getAddDate();
    }

    //------------------------------------------------------

    /**
     *  Sets the addBy attribute of the OrderJoinData object
     *
     *@param  pAddBy  The new addBy value
     */
    public void setAddBy(String pAddBy) {
        _order.setAddBy(pAddBy);
    }


    /**
     *  Gets the addBy attribute of the OrderJoinData object
     *
     *@return    The addBy value
     */
    public String getAddBy() {
        return _order.getAddBy();
    }

    //------------------------------------------------------

    /**
     *  Sets the modDate attribute of the OrderJoinData object
     *
     *@param  pModDate  The new modDate value
     */
    public void setModDate(Date pModDate) {
        _order.setModDate(pModDate);
    }


    /**
     *  Gets the modDate attribute of the OrderJoinData object
     *
     *@return    The modDate value
     */
    public Date getModDate() {
        return _order.getModDate();
    }

    //------------------------------------------------------

    /**
     *  Sets the modBy attribute of the OrderJoinData object
     *
     *@param  pModBy  The new modBy value
     */
    public void setModBy(String pModBy) {
        _order.setModBy(pModBy);
    }


    /**
     *  Gets the modBy attribute of the OrderJoinData object
     *
     *@return    The modBy value
     */
    public String getModBy() {
        return _order.getModBy();
    }


    /**
     *  Sets the shipAddress1 attribute of the OrderJoinData object
     *
     *@param  pShipAddress1  The new shipAddress1 value
     */
    public void setShipAddress1(String pShipAddress1) {
        _orderShipTo.setAddress1(pShipAddress1);
    }


    /**
     *  Gets the shipAddress1 attribute of the OrderJoinData object
     *
     *@return    The shipAddress1 value
     */
    public String getShipAddress1() {
        return _orderShipTo.getAddress1();
    }


    /**
     *  Sets the shipAddress2 attribute of the OrderJoinData object
     *
     *@param  pShipAddress2  The new shipAddress2 value
     */
    public void setShipAddress2(String pShipAddress2) {
        _orderShipTo.setAddress2(pShipAddress2);
    }


    /**
     *  Gets the shipAddress2 attribute of the OrderJoinData object
     *
     *@return    The shipAddress2 value
     */
    public String getShipAddress2() {
        return _orderShipTo.getAddress2();
    }


    /**
     *  Sets the shipAddress3 attribute of the OrderJoinData object
     *
     *@param  pShipAddress3  The new shipAddress3 value
     */
    public void setShipAddress3(String pShipAddress3) {
        _orderShipTo.setAddress3(pShipAddress3);
    }


    /**
     *  Gets the shipAddress3 attribute of the OrderJoinData object
     *
     *@return    The shipAddress3 value
     */
    public String getShipAddress3() {
        return _orderShipTo.getAddress3();
    }


    /**
     *  Sets the shipAddress4 attribute of the OrderJoinData object
     *
     *@param  pShipAddress4  The new shipAddress4 value
     */
    public void setShipAddress4(String pShipAddress4) {
        _orderShipTo.setAddress4(pShipAddress4);
    }


    /**
     *  Gets the shipAddress4 attribute of the OrderJoinData object
     *
     *@return    The shipAddress4 value
     */
    public String getShipAddress4() {
        return _orderShipTo.getAddress4();
    }


    /**
     *  Sets the shipCity attribute of the OrderJoinData object
     *
     *@param  pShipCity  The new shipCity value
     */
    public void setShipCity(String pShipCity) {
        _orderShipTo.setCity(pShipCity);
    }


    /**
     *  Gets the shipCity attribute of the OrderJoinData object
     *
     *@return    The shipCity value
     */
    public String getShipCity() {
        return _orderShipTo.getCity();
    }


    //------------------------------------------------------
    /**
     *  Sets the shipStateProvinceCd attribute of the OrderJoinData object
     *
     *@param  v  The new shipStateProvinceCd value
     */
    public void setShipStateProvinceCd(String v) {
        _orderShipTo.setStateProvinceCd(v);
    }


    /**
     *  Gets the shipStateProvinceCd attribute of the OrderJoinData object
     *
     *@return    The shipStateProvinceCd value
     */
    public String getShipStateProvinceCd() {
        return _orderShipTo.getStateProvinceCd();
    }


    //------------------------------------------------------
    /**
     *  Sets the shipCountryCd attribute of the OrderJoinData object
     *
     *@param  v  The new shipCountryCd value
     */
    public void setShipCountryCd(String v) {
        _orderShipTo.setCountryCd(v);
    }


    /**
     *  Gets the shipCountryCd attribute of the OrderJoinData object
     *
     *@return    The shipCountryCd value
     */
    public String getShipCountryCd() {
        return _orderShipTo.getCountryCd();
    }


    //------------------------------------------------------
    /**
     *  Sets the shipPostalCode attribute of the OrderJoinData object
     *
     *@param  v  The new shipPostalCode value
     */
    public void setShipPostalCode(String v) {
        _orderShipTo.setPostalCode(v);
    }


    /**
     *  Gets the shipPostalCode attribute of the OrderJoinData object
     *
     *@return    The shipPostalCode value
     */
    public String getShipPostalCode() {
        return _orderShipTo.getPostalCode();
    }


    // Bill to information methods.
    //------------------------------------------------------
    /**
     *  Sets the billShortDesc attribute of the OrderJoinData object
     *
     *@param  v  The new billShortDesc value
     */
    public void setBillShortDesc(String v) {
        _orderBillTo.setShortDesc(v);
    }


    /**
     *  Gets the billShortDesc attribute of the OrderJoinData object
     *
     *@return    The billShortDesc value
     */
    public String getBillShortDesc() {
        return _orderBillTo.getShortDesc();
    }


    //------------------------------------------------------
    // Address fields.
    /**
     *  Sets the billAddress1 attribute of the OrderJoinData object
     *
     *@param  v  The new billAddress1 value
     */
    public void setBillAddress1(String v) {
        _orderBillTo.setAddress1(v);
    }


    /**
     *  Gets the billAddress1 attribute of the OrderJoinData object
     *
     *@return    The billAddress1 value
     */
    public String getBillAddress1() {
        return _orderBillTo.getAddress1();
    }


    /**
     *  Sets the billAddress2 attribute of the OrderJoinData object
     *
     *@param  v  The new billAddress2 value
     */
    public void setBillAddress2(String v) {
        _orderBillTo.setAddress2(v);
    }


    /**
     *  Gets the billAddress2 attribute of the OrderJoinData object
     *
     *@return    The billAddress2 value
     */
    public String getBillAddress2() {
        return _orderBillTo.getAddress2();
    }


    /**
     *  Sets the billAddress3 attribute of the OrderJoinData object
     *
     *@param  v  The new billAddress3 value
     */
    public void setBillAddress3(String v) {
        _orderBillTo.setAddress3(v);
    }


    /**
     *  Gets the billAddress3 attribute of the OrderJoinData object
     *
     *@return    The billAddress3 value
     */
    public String getBillAddress3() {
        return _orderBillTo.getAddress3();
    }


    /**
     *  Sets the billAddress4 attribute of the OrderJoinData object
     *
     *@param  v  The new billAddress4 value
     */
    public void setBillAddress4(String v) {
        _orderBillTo.setAddress4(v);
    }


    /**
     *  Gets the billAddress4 attribute of the OrderJoinData object
     *
     *@return    The billAddress4 value
     */
    public String getBillAddress4() {
        return _orderBillTo.getAddress4();
    }


    //------------------------------------------------------
    /**
     *  Sets the billCity attribute of the OrderJoinData object
     *
     *@param  v  The new billCity value
     */
    public void setBillCity(String v) {
        _orderBillTo.setCity(v);
    }


    /**
     *  Gets the billCity attribute of the OrderJoinData object
     *
     *@return    The billCity value
     */
    public String getBillCity() {
        return _orderBillTo.getCity();
    }

    //------------------------------------------------------

    /**
     *  Sets the billStateProvinceCd attribute of the OrderJoinData object
     *
     *@param  v  The new billStateProvinceCd value
     */
    public void setBillStateProvinceCd(String v) {
        _orderBillTo.setStateProvinceCd(v);
    }


    /**
     *  Gets the billStateProvinceCd attribute of the OrderJoinData object
     *
     *@return    The billStateProvinceCd value
     */
    public String getBillStateProvinceCd() {
        return _orderBillTo.getStateProvinceCd();
    }


    //------------------------------------------------------
    /**
     *  Sets the billCountryCd attribute of the OrderJoinData object
     *
     *@param  v  The new billCountryCd value
     */
    public void setBillCountryCd(String v) {
        _orderBillTo.setCountryCd(v);
    }


    /**
     *  Gets the billCountryCd attribute of the OrderJoinData object
     *
     *@return    The billCountryCd value
     */
    public String getBillCountryCd() {
        return _orderBillTo.getCountryCd();
    }


    //------------------------------------------------------
    /**
     *  Sets the billPostalCode attribute of the OrderJoinData object
     *
     *@param  v  The new billPostalCode value
     */
    public void setBillPostalCode(String v) {
        _orderBillTo.setPostalCode(v);
    }


    /**
     *  Gets the billPostalCode attribute of the OrderJoinData object
     *
     *@return    The billPostalCode value
     */
    public String getBillPostalCode() {
        return _orderBillTo.getPostalCode();
    }


    //------------------------------------------------------
    private String _ccNumber = "";


    /**
     *  Sets the ccNumber attribute of the OrderJoinData object
     *
     *@param  pCcNumber  The new ccNumber value
     */
    public void setCcNumber(String pCcNumber) {
        _ccNumber = pCcNumber;
    }


    /**
     *  Gets the ccNumber attribute of the OrderJoinData object
     *
     *@return    The ccNumber value
     */
    public String getCcNumber() {
        return _ccNumber;
    }

    //------------------------------------------------------

    private int _ccExpMonth = 0;


    /**
     *  Sets the ccExpMonth attribute of the OrderJoinData object
     *
     *@param  pCcExpMonth  The new ccExpMonth value
     */
    public void setCcExpMonth(int pCcExpMonth) {
        _ccExpMonth = pCcExpMonth;
    }


    /**
     *  Gets the ccExpMonth attribute of the OrderJoinData object
     *
     *@return    The ccExpMonth value
     */
    public int getCcExpMonth() {
        return _ccExpMonth;
    }

    //------------------------------------------------------

    private int _ccExpYear = 0;


    /**
     *  Sets the ccExpYear attribute of the OrderJoinData object
     *
     *@param  pCcExpYear  The new ccExpYear value
     */
    public void setCcExpYear(int pCcExpYear) {
        _ccExpYear = pCcExpYear;
    }


    /**
     *  Gets the ccExpYear attribute of the OrderJoinData object
     *
     *@return    The ccExpYear value
     */
    public int getCcExpYear() {
        return _ccExpYear;
    }

    //------------------------------------------------------

    private String _ccAddress1 = "";


    /**
     *  Sets the ccAddress1 attribute of the OrderJoinData object
     *
     *@param  pCcAddress1  The new ccAddress1 value
     */
    public void setCcAddress1(String pCcAddress1) {
        _ccAddress1 = pCcAddress1;
    }


    /**
     *  Gets the ccAddress1 attribute of the OrderJoinData object
     *
     *@return    The ccAddress1 value
     */
    public String getCcAddress1() {
        return _ccAddress1;
    }

    //------------------------------------------------------

    private String _ccAddress2 = "";


    /**
     *  Sets the ccAddress2 attribute of the OrderJoinData object
     *
     *@param  pCcAddress2  The new ccAddress2 value
     */
    public void setCcAddress2(String pCcAddress2) {
        _ccAddress2 = pCcAddress2;
    }


    /**
     *  Gets the ccAddress2 attribute of the OrderJoinData object
     *
     *@return    The ccAddress2 value
     */
    public String getCcAddress2() {
        return _ccAddress2;
    }

    //------------------------------------------------------

    private String _ccCity = "";


    /**
     *  Sets the ccCity attribute of the OrderJoinData object
     *
     *@param  pCcCity  The new ccCity value
     */
    public void setCcCity(String pCcCity) {
        _ccCity = pCcCity;
    }


    /**
     *  Gets the ccCity attribute of the OrderJoinData object
     *
     *@return    The ccCity value
     */
    public String getCcCity() {
        return _ccCity;
    }

    //------------------------------------------------------

    private String _ccStateProvinceCd = "";


    /**
     *  Sets the ccStateProvinceCd attribute of the OrderJoinData object
     *
     *@param  pCcStateProvinceCd  The new ccStateProvinceCd value
     */
    public void setCcStateProvinceCd(String pCcStateProvinceCd) {
        _ccStateProvinceCd = pCcStateProvinceCd;
    }


    /**
     *  Gets the ccStateProvinceCd attribute of the OrderJoinData object
     *
     *@return    The ccStateProvinceCd value
     */
    public String getCcStateProvinceCd() {
        return _ccStateProvinceCd;
    }

    //------------------------------------------------------

    private String _ccCountryCd = "";


    /**
     *  Sets the ccCountryCd attribute of the OrderJoinData object
     *
     *@param  pCcCountryCd  The new ccCountryCd value
     */
    public void setCcCountryCd(String pCcCountryCd) {
        _ccCountryCd = pCcCountryCd;
    }


    /**
     *  Gets the ccCountryCd attribute of the OrderJoinData object
     *
     *@return    The ccCountryCd value
     */
    public String getCcCountryCd() {
        return _ccCountryCd;
    }

    //------------------------------------------------------

    private String _ccPostalCode = "";

    /**
     * Holds value of property placedByFirstName.
     */
    private String placedByFirstName;

    /**
     * Holds value of property placedByLastName.
     */
    private String placedByLastName;


    /**
     *  Sets the ccPostalCode attribute of the OrderJoinData object
     *
     *@param  pCcPostalCode  The new ccPostalCode value
     */
    public void setCcPostalCode(String pCcPostalCode) {
        _ccPostalCode = pCcPostalCode;
    }


    /**
     *  Gets the ccPostalCode attribute of the OrderJoinData object
     *
     *@return    The ccPostalCode value
     */
    public String getCcPostalCode() {
        return _ccPostalCode;
    }

    //------------------------------------------------------

    /**
     *  Sets the freightAmt attribute of the OrderJoinData object
     *
     *@param  v  The new freightAmt value
     */
    public void setFreightAmt(BigDecimal v) {
        _order.setTotalFreightCost(v);
    }


    /**
     *  Gets the freightAmt attribute of the OrderJoinData object
     *
     *@return    The freightAmt value
     */
    public BigDecimal getFreightAmt() {
        return _order.getTotalFreightCost();
    }



    /**
     *  Gets the orderScheduledDelivery attribute of the OrderJoinData object
     *
     *@return    The orderScheduledDelivery value
     */
    public String getOrderScheduledDelivery() {
        // This value is store in the order meta array.
        return OrderStatusDescData.getRequestedShipDate(_orderMetaDV);
    }

    private OrderPropertyData getProp
	(String pShortDesc,
	 String pOrderPropertyType, String pStatus) {
	OrderPropertyDataVector opdv = getOrderProperties
	    (pShortDesc, pOrderPropertyType, pStatus);
	if ( null != opdv && opdv.size() > 0 ) {
	    return (OrderPropertyData)opdv.get(0);
	}
	return null;
    }

    public BigDecimal getRushOrderCharge() {
        return getOrder().getTotalRushCharge();
    }

    public BigDecimal getSumOfAllOrderCharges() {
       return OrderStatusDescData.getSumOfAllOrderCharges(getOrder());
    }

    /**
     * Getter for property placedByFirstName.
     * @return Value of property placedByFirstName.
     */
    public String getPlacedByFirstName() {

        return this.placedByFirstName;
    }

    /**
     * Setter for property placedByFirstName.
     * @param placedByFirstName New value of property placedByFirstName.
     */
    public void setPlacedByFirstName(String placedByFirstName) {

        this.placedByFirstName = placedByFirstName;
    }

    /**
     * Getter for property placedByLastName.
     * @return Value of property placedByLastName.
     */
    public String getPlacedByLastName() {

        return this.placedByLastName;
    }

    /**
     * Setter for property placedByLastName.
     * @param placedByLastName New value of property placedByLastName.
     */
    public void setPlacedByLastName(String placedByLastName) {

        this.placedByLastName = placedByLastName;
    }

}

