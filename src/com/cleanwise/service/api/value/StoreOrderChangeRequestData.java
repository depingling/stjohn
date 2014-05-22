package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;
import java.math.BigDecimal;
import java.util.Date;

public class StoreOrderChangeRequestData extends ValueObject{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4623639795282296432L;
        private OrderData orderData;
        private OrderMetaDataVector orderMeta;
        private OrderData oldOrderData;
        private OrderItemDataVector orderItems;
        private OrderItemDataVector oldOrderItems;
        private OrderItemDescDataVector orderItemsDesc;
        private IdVector delOrderItems;
        private String userName;
        private BigDecimal totalFreightCost = new BigDecimal(0);
        private BigDecimal totalMiscCost = new BigDecimal(0);
        private BigDecimal smallOrderFeeAmt = null;
        private BigDecimal fuelSurchargeAmt = null;
        private BigDecimal _discountAmt = null;
        private Date newOrderDate = null;
        private Integer newSiteId;
        private boolean rebillOrder;

        public Date getNewOrderDate() {
          return newOrderDate;
        }
        public void setNewOrderDate(Date newOrderDate) {
          this.newOrderDate = newOrderDate;
        }
        public BigDecimal getTotalFreightCost() {
          return totalFreightCost;
        }
        public void setTotalFreightCost(BigDecimal totalFreightCost) {
          this.totalFreightCost = totalFreightCost;
        }
        public BigDecimal getTotalMiscCost() {
          return totalMiscCost;
        }
        public void setTotalMiscCost(BigDecimal totalMiscCost) {
          this.totalMiscCost = totalMiscCost;
        }
        public String getUserName() {
          return userName;
        }
        public void setUserName(String userName) {
          this.userName = userName;
        }
        public IdVector getDelOrderItems(){
          return delOrderItems;
        }
        public void setDelOrderItems(IdVector delOrderItems){
          this.delOrderItems = delOrderItems;
        }
        public OrderItemDataVector getOrderItems(){
          return orderItems;
        }
        public void setOrderItems(OrderItemDataVector orderItems){
          this.orderItems = orderItems;
        }
        public OrderItemDataVector getOldOrderItems(){
          return oldOrderItems;
        }
        public void setOldOrderItems(OrderItemDataVector oldOrderItems){
          this.oldOrderItems = oldOrderItems;
        }
        public OrderItemDescDataVector getOrderItemsDesc(){
          return orderItemsDesc;
        }
        public void setOrderItemsDesc(OrderItemDescDataVector orderItemsDesc){
          this.orderItemsDesc = orderItemsDesc;
        }
        public OrderData getOrderData() {
          return orderData;
        }
        public void setOrderData(OrderData orderData) {
          this.orderData = orderData;
        }
        public OrderData getOldOrderData() {
          return oldOrderData;
        }
        public void setOldOrderData(OrderData oldOrderData) {
          this.oldOrderData = oldOrderData;
        }

    public OrderMetaDataVector getOrderMeta() {
        return orderMeta;
    }

    public void setOrderMeta(OrderMetaDataVector orderMeta) {
        this.orderMeta = orderMeta;
    }

    public BigDecimal getFuelSurchargeAmt() {
        return fuelSurchargeAmt;
    }

    public void setFuelSurchargeAmt(BigDecimal fuelSurchargeAmt) {
        this.fuelSurchargeAmt = fuelSurchargeAmt;
    }

    public BigDecimal getSmallOrderFeeAmt() {
        return smallOrderFeeAmt;
    }

    public void setSmallOrderFeeAmt(BigDecimal smallOrderFeeAmt) {
        this.smallOrderFeeAmt = smallOrderFeeAmt;
    }

    
    public Integer getNewSiteId() {
        return newSiteId;
    }

    public void setNewSiteId(Integer newSiteId) {
        this.newSiteId = newSiteId;
    }

    private StoreOrderChangeRequestData(){}
        public static StoreOrderChangeRequestData createValue (){
          return new StoreOrderChangeRequestData();
        }

    public BigDecimal getDiscountAmt() {
        return _discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        _discountAmt = discountAmt;
    }

    public boolean getRebillOrder() {
        return rebillOrder;
    }

    public void setRebillOrder(boolean v) {
        rebillOrder = v;
    }

}
