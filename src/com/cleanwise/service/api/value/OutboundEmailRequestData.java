package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

import java.util.List;


public class OutboundEmailRequestData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 25208511135135488L;

    private OrderData mOrderD;
    private OrderItemDataVector mOrderItemDV;
    private PurchaseOrderData mPurchaseOrderD;
    private String accountName;
    private String storeType;
    private int incomingTradingProfileId;


    public OrderData getOrderData() {
        return mOrderD;
    }

    public void setOrderData(OrderData orderD) {
        this.mOrderD = orderD;
    }

    public OrderItemDataVector getOrderItemDV() {
        return mOrderItemDV;
    }

    public void setOrderItemDV(OrderItemDataVector orderItemDV) {
        this.mOrderItemDV = orderItemDV;
    }

    public PurchaseOrderData getPurchaseOrderD() {
        return mPurchaseOrderD;
    }

    public void setPurchaseOrderD(PurchaseOrderData purchaseOrderD) {
        this.mPurchaseOrderD = purchaseOrderD;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }


    /** Getter for property incomingTradingProfileId.
     * @return Value of property incomingTradingProfileId.
     *
     */
    public int getIncomingTradingProfileId() {
        if(this.incomingTradingProfileId == 0 && getOrderData()!= null){
            return getOrderData().getIncomingTradingProfileId();
        }else{
            return this.incomingTradingProfileId;
        }
    }

    /** Setter for property incomingTradingProfileId.
     * @param incomingTradingProfileId New value of property incomingTradingProfileId.
     *
     */
    public void setIncomingTradingProfileId(int incomingTradingProfileId) {
        this.incomingTradingProfileId = incomingTradingProfileId;
    }
}
