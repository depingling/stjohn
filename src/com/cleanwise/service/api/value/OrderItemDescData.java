package com.cleanwise.service.api.value;


import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.math.*;

/**
 *  <code>OrderItemDescData</code> is a ValueObject
 *  describbing an item tied to a contract.
 *
 *@author     liang
 *@created    December 05, 2001
 */
public class OrderItemDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -7816140406663611385L;

    private OrderItemData mOrderItem;
    private ItemSubstitutionDataVector mOrderItemSubstitutionList;
    private OrderItemActionDataVector mOrderItemActionList;
    private OrderItemActionDescDataVector mOrderItemActionDescList;
    private InvoiceDistDetailDataVector mInvoiceDistDetailList;
    private InvoiceCustDetailDataVector mInvoiceCustDetailList;
    private OrderPropertyData mDistPoNote;
    private AssetData mAssetInfo;
    private ItemData mItemInfo;

    //for the invoice page to add invoice dist details.
    private InvoiceDistDetailData mNewInvoiceDistDetail = InvoiceDistDetailData.createValue();
    private String mItemQuantityS = "";
    private String mCwCostS = "";
    private String mDistLineNumS = "";
    private String mItemSkuNumS = "";
    private String mItemIdS = "";
    private String mOrderItemIdS = "";
    private String mLineTotalS = "";
    private String mItemPriceS = "";
    private boolean mReSale = false;

    private String mDistName = "";
    private String mNewDistName = "";
    private String mDistIdS = "";
    private String mNewAssetName="";
    private String mAssetIdS="";
    private String mNewServiceName="";

    private int mDistId;
    private boolean mHasNote = false;
    private String mTargetShipDateString = "";
    private String mShipStatus = "";
    private String mQtyReturnedString = "";
    private ReturnRequestDetailData mReturnRequestDetailData = ReturnRequestDetailData.createValue();
    private Date mDeliveryDate = null;

    private String mItemStatus = "";
    private String mPoItemStatus = "";

    /** Holds value of property orderItemNotes. */
    private OrderPropertyDataVector orderItemNotes;

    /** Holds value of property purchaseOrderData. */
    private PurchaseOrderData purchaseOrderData;

    /** Holds value of property openLineStatusCd. */
    private String openLineStatusCd;

    /** Holds value of property invoiceDistDataVector. */
    private InvoiceDistDataVector invoiceDistDataVector;

    private String distRuntimeDisplayName;
    private BigDecimal calculatedSalesTax;

    private String mStandardProductList;

    private String mCatalogItemSkuNum;

    public BigDecimal getCalculatedSalesTax() {
    return calculatedSalesTax;
  }
  public void setCalculatedSalesTax(BigDecimal calculatedSalesTax) {
    this.calculatedSalesTax = calculatedSalesTax;
  }

    private boolean mTaxExempt = false;

  /**
     * @return Returns the distDisplayName.
     */
    public String getDistRuntimeDisplayName() {
        return distRuntimeDisplayName;
    }
    /**
     * @param distDisplayName The distDisplayName to set.
     */
    public void setDistRuntimeDisplayName(String distRuntimeDisplayName) {
        this.distRuntimeDisplayName = distRuntimeDisplayName;
    }
    /**
     *  Constructor.
     */
    public OrderItemDescData() { }


    /**
     *  Constructor.
     *
     *@param  parm1  Description of Parameter
     *@param  parm2  Description of Parameter
     *@param  parm3  Description of Parameter
     */
    public OrderItemDescData(OrderItemData parm1, ItemSubstitutionDataVector parm2, OrderItemActionDataVector parm3, InvoiceCustDetailDataVector parm4) {
        mOrderItem = parm1;
        mOrderItemSubstitutionList = parm2;
        mOrderItemActionList = parm3;
        mInvoiceCustDetailList = parm4;
        mDistPoNote = null;
;

    }


    /**
     *  Set the mOrderItem field.
     *
     *@param  v   The new OrderItem value
     */
    public void setOrderItem(OrderItemData v) {
        mOrderItem = v;
    }


    /**
     *  Set the mOrderItemSubstitutionList field.
     *
     *@param  v   The new OrderItemSubstitutionList value
     */
    public void setOrderItemSubstitutionList(ItemSubstitutionDataVector v) {
        mOrderItemSubstitutionList = v;
    }


    /**
     *  Set the mOrderItemActionList field.
     *
     *@param  v   The new OrderItemActionList value
     */
    public void setOrderItemActionList(OrderItemActionDataVector v) {
        mOrderItemActionList = v;
    }

    /**
     *  Set the mOrderItemActionDescList field.
     *
     *@param  v   The new OrderItemActionDescList value
     */
    public void setOrderItemActionDescList(OrderItemActionDescDataVector v) {
        mOrderItemActionDescList = v;
    }

    /**
     *  Set the mInvoiceDistDetailList field.
     *
     *@param  v   The new InvoiceDistDetailList value
     */
    public void setInvoiceDistDetailList(InvoiceDistDetailDataVector v) {
        mInvoiceDistDetailList = v;
    }


    /**
     *  Set the mInvoiceCustDetailList field.
     *
     *@param  v   The new InvoiceCustDetailList value
     */
    public void setInvoiceCustDetailList(InvoiceCustDetailDataVector v) {
        mInvoiceCustDetailList = v;
    }

    /**
     *  Set the mDistPoNote field.
     *
     *@param  v   The new DistPoNote value
     */
    public void setDistPoNote(OrderPropertyData v) {
        mDistPoNote = v;
    }

    /**
     *  Set the NewInvoiceDistDetail field.
     *
     *@param  v   The new NewInvoiceDistDetail value
     */
    public void setNewInvoiceDistDetail(InvoiceDistDetailData v) {
        mNewInvoiceDistDetail = v;
    }



    /**
     *  Get the mOrderItem field.
     *
     *@return    OrderItemData
     */
    public OrderItemData getOrderItem() {
        return mOrderItem;
    }
    public boolean getIsAnInventoryItem() {
        if ( mOrderItem.getInventoryParValue() > 0 )
        {
            return true;
        }
        return false;
    }


    /**
     *  Get the mOrderItemSubstitutionList field.
     *
     *@return    OrderItemSubstitutionDataVector
     */
    public ItemSubstitutionDataVector getOrderItemSubstitutionList() {
        return mOrderItemSubstitutionList;
    }


    /**
     *  Get the mOrderItemActionList field.
     *
     *@return    OrderItemActionDataVector
     */
    public OrderItemActionDataVector getOrderItemActionList() {
        return mOrderItemActionList;
    }


    /**
     *  Get the mOrderItemActionDescList field.
     *
     *@return    OrderItemActionDescDataVector
     */
    public OrderItemActionDescDataVector getOrderItemActionDescList() {
        return mOrderItemActionDescList;
    }


    /**
     *  Get the mInvoiceDistDetailList field.
     *
     *@return    InvoiceDistDetailDataVector
     */
    public InvoiceDistDetailDataVector getInvoiceDistDetailList() {
        return mInvoiceDistDetailList;
    }


    /**
     *  Get the mInvoiceCustDetailList field.
     *
     *@return    InvoiceCustDetailDataVector
     */
    public InvoiceCustDetailDataVector getInvoiceCustDetailList() {
        return mInvoiceCustDetailList;
    }

    /**
     *  Get the mDistPoNote field.
     *
     *@return    OrderPropertyData
     */
    public OrderPropertyData getDistPoNote() {
        return mDistPoNote;
    }


    /**
     *  Get the NewInvoiceDistDetail field.
     *
     *@return    InvoiceDistDetailData
     */
    public InvoiceDistDetailData getNewInvoiceDistDetail() {
        return mNewInvoiceDistDetail;
    }


    /**
     *  Set the ItemQuantityS field.
     *
     *@param  v   The new ItemQuantityS value
     */
    public void setItemQuantityS(String v) {
        mItemQuantityS = v;
    }

    /**
     *  Get the ItemQuantityS field.
     *
     *@return    String
     */
    public String getItemQuantityS() {
        return mItemQuantityS;
    }


    /**
     *  Set the CwCostS field.
     *
     *@param  v   The new CwCostS value
     */
    public void setCwCostS(String v) {
        mCwCostS = v;
    }

    /**
     *  Get the CwCostS field.
     *
     *@return    String
     */
    public String getCwCostS() {
        return mCwCostS;
    }


    /**
     *  Set the DistLineNumS field.
     *
     *@param  v   The new DistLineNumS value
     */
    public void setDistLineNumS(String v) {
        mDistLineNumS = v;
    }

    /**
     *  Get the DistLineNumS field.
     *
     *@return    String
     */
    public String getDistLineNumS() {
        return mDistLineNumS;
    }

    /**
     *  Set the ItemSkuNumS field.
     *
     *@param  v   The new ItemSkuNumS value
     */
    public void setItemSkuNumS(String v) {
        mItemSkuNumS = v;
    }

    /**
     *  Get the ItemSkuNumS field.
     *
     *@return    String
     */
    public String getItemSkuNumS() {
        return mItemSkuNumS;
    }


    /**
     *  Set the ItemIdS field.
     *
     *@param  v   The new ItemIdS value
     */
    public void setItemIdS(String v) {
        mItemIdS = v;
    }

    /**
     *  Get the OrderItemIdS field.
     *
     *@return    String
     */
    public String getItemIdS() {
        return mItemIdS;
    }

    /**
     *  Get the OrderItemIdS field.
     *
     *@return    String
     */
    public String getOrderItemIdS() {
        return mOrderItemIdS;
    }

    /**
     *  Set the ItemIdS field.
     *
     *@param  v   The new ItemIdS value
     */
    public void setOrderItemIdS(String v) {
        mOrderItemIdS = v;
    }




    /**
     *  Set the LineTotalS field.
     *
     *@param  v   The new LineTotalS value
     */
    public void setLineTotalS(String v) {
        mLineTotalS = v;
    }

    /**
     *  Get the LineTotalS field.
     *
     *@return    String
     */
    public String getLineTotalS() {
        return mLineTotalS;
    }


    /**
     *  Set the ItemPriceS field.
     *
     *@param  v   The new ItemPriceS value
     */
    public void setItemPriceS(String v) {
        mItemPriceS = v;
    }

    /**
     *  Get the ItemPriceS field.
     *
     *@return    String
     */
    public String getItemPriceS() {
        return mItemPriceS;
    }


    /**
     *  Set the DistName field.
     *
     *@param  v   The new DistName value
     */
    public void setDistName(String v) {
        mDistName = v;
    }

    /**
     *  Get the DistName field.
     *
     *@return    String
     */
    public String getDistName() {
        return mDistName;
    }


    /**
     *  Set the NewDistName field.
     *
     *@param  v   The new NewDistName value
     */
    public void setNewDistName(String v) {
        mNewDistName = v;
    }

    /**
     *  Get the NewDistName field.
     *
     *@return    String
     */
    public String getNewDistName() {
        return mNewDistName;
    }



    /**
     *  Set the DistIdS field.
     *
     *@param  v   The new DistIdS value
     */
    public void setDistIdS(String v) {
        mDistIdS = v;
    }

    /**
     *  Get the DistIdS field.
     *
     *@return    String
     */
    public String getDistIdS() {
        return mDistIdS;
    }


    /**
     *  Set the DistId field.
     *
     *@param  v   The new DistId value
     */
    public void setDistId(int v) {
        mDistId = v;
    }

    /**
     *  Get the DistId field.
     *
     *@return    int
     */
    public int getDistId() {
        return mDistId;
    }

    /**
     *  Set the hasNote field.
     *
     *@param  v   The new hasNote value
     */
    public void setHasNote(boolean v) {
        mHasNote = v;
    }

    /**
     *  Get the hasNote field.
     *
     *@return    boolean
     */
    public boolean getHasNote() {
        return mHasNote;
    }


    /**
     *  Set the TargetShipDateString field.
     *
     *@param  v   The new TargetShipDate value
     */
    public void setTargetShipDateString(String v) {
        mTargetShipDateString = v;
    }

    /**
     *  Get the TargetShipDateString field.
     *
     *@return    String
     */
    public String getTargetShipDateString() {
        return mTargetShipDateString;
    }


    /**
     *  Set the shipStatus field.
     *
     *@param  v   The new shipStatus value
     */
    public void setShipStatus(String v) {
        mShipStatus = v;
    }

    /**
     *  Get the ShipStatus field.
     *
     *@return    String
     */
    public String getShipStatus() {
        return mShipStatus;
    }


    /**
     *  Returns a String representation of the value object
     *
     *@return The String representation of this
     * OrderItemStatusDescData object
     */
    public String toString() {
        return "[" + "OrderItem=" + mOrderItem.toString() + "]";
    }


    /**
     *  Creates a new OrderItemDescData
     *
     *@return    Newly initialized OrderItemDescData object.
     */
    public static OrderItemDescData createValue() {
        OrderItemDescData valueData = new OrderItemDescData();
        return valueData;
    }

    /** Getter for property orderItemNotes.
     * @return Value of property orderItemNotes.
     *
     */
    public OrderPropertyDataVector getOrderItemNotes() {
        return this.orderItemNotes;
    }

    /** Setter for property orderItemNotes.
     * @param orderItemNotes New value of property orderItemNotes.
     *
     */
    public void setOrderItemNotes(OrderPropertyDataVector orderItemNotes) {
        this.orderItemNotes = orderItemNotes;
    }


    /** Getter for property qtyReturnedString.
     * @return Value of property qtyReturnedString.
     *
     */
    public String getQtyReturnedString() {
        return mQtyReturnedString;
    }

    /** Setter for property qtyReturnedString.
     * @param qtyReturnedString New value of property qtyReturnedString.
     *
     */
    public void setQtyReturnedString(String qtyReturnedString) {
        mQtyReturnedString = qtyReturnedString;
    }

    /** Getter for property returnRequestDetailData.
     * @return Value of property returnRequestDetailData.
     *
     */
    public ReturnRequestDetailData getReturnRequestDetailData() {
        return mReturnRequestDetailData;
    }

    /** Setter for property returnRequestDetailData.
     * @param ReturnRequestDetailData New value of property returnRequestDetailData.
     *
     */
    public void setReturnRequestDetailData(ReturnRequestDetailData pReturnRequestDetailData) {
        mReturnRequestDetailData = pReturnRequestDetailData;
    }

    /** Getter for property purchaseOrderData.
     * @return Value of property purchaseOrderData.
     *
     */
    public PurchaseOrderData getPurchaseOrderData() {
        return this.purchaseOrderData;
    }

    /** Setter for property purchaseOrderData.
     * @param purchaseOrderData New value of property purchaseOrderData.
     *
     */
    public void setPurchaseOrderData(PurchaseOrderData purchaseOrderData) {
        this.purchaseOrderData = purchaseOrderData;
    }

    /** Getter for property openLineStatusCd.
     * @return Value of property openLineStatusCd.
     *
     */
    public String getOpenLineStatusCd() {
        return this.openLineStatusCd;
    }

    /** Setter for property openLineStatusCd.
     * @param openLineStatusCd New value of property openLineStatusCd.
     *
     */
    public void setOpenLineStatusCd(String openLineStatusCd) {
        this.openLineStatusCd = openLineStatusCd;
    }

    /** Getter for property invoiceDistDataVector.
     * @return Value of property invoiceDistDataVector.
     *
     */
    public InvoiceDistDataVector getInvoiceDistDataVector() {
        return this.invoiceDistDataVector;
    }

    /** Setter for property invoiceDistDataVector.
     * @param invoiceDistDataVector New value of property invoiceDistDataVector.
     *
     */
    public void setInvoiceDistDataVector(InvoiceDistDataVector invoiceDistDataVector) {
        this.invoiceDistDataVector = invoiceDistDataVector;
    }

    /** Getter for property deliveryDate.
     * @return Value of property deliveryDate.
     *
     */
    public Date getDeliveryDate() {
        return this.mDeliveryDate;
    }

    /** Setter for property deliveryDate.
     * @param deliveryDate New value of property DeliveryDate.
     *
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.mDeliveryDate = deliveryDate;
    }

    private List mItemShoppingInfoList = null;

    /**
     * Holds value of property newOrderItemActionQtyS.
     */
    private String newOrderItemActionQtyS;

    /**
     * Holds value of property targetShipDate.
     */
    private Date targetShipDate;

    /**
     * Holds value of property newOrderItemActionQty.
     */
    private int newOrderItemActionQty;

    /**
     * Holds value of property workingInvoiceDistDetailData.
     */
    private com.cleanwise.service.api.value.InvoiceDistDetailData workingInvoiceDistDetailData;
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
            if ( sinfo.getItemId() == mOrderItem.getItemId() )
            {
                mItemShoppingInfoList.add(sinfo);
            }
        }
    }

    /**
     * Getter for property newOrderItemActionQty.
     * @return Value of property newOrderItemActionQty.
     */
    public String getNewOrderItemActionQtyS()  {

        return this.newOrderItemActionQtyS;
    }

    /**
     * Setter for property newOrderItemActionQty.
     * @param newOrderItemActionQty New value of property newOrderItemActionQty.
     */
    public void setNewOrderItemActionQtyS(java.lang.String newOrderItemActionQtyS)  {

        this.newOrderItemActionQtyS = newOrderItemActionQtyS;
    }

    /**
     * Getter for property targetShipDate.
     * @return Value of property targetShipDate.
     */
    public Date getTargetShipDate() {

        return this.targetShipDate;
    }

    /**
     * Setter for property targetShipDate.
     * @param targetShipDate New value of property targetShipDate.
     */
    public void setTargetShipDate(Date targetShipDate) {

        this.targetShipDate = targetShipDate;
    }

    /**
     * Getter for property newOrderItemActionQty.
     * @return Value of property newOrderItemActionQty.
     */
    public int getNewOrderItemActionQty() {

        return this.newOrderItemActionQty;
    }

    /**
     * Setter for property newOrderItemActionQty.
     * @param newOrderItemActionQty New value of property newOrderItemActionQty.
     */
    public void setNewOrderItemActionQty(int newOrderItemActionQty) {

        this.newOrderItemActionQty = newOrderItemActionQty;
    }

    /**
     * Getter for property workingInvoiceDistData.
     * @return Value of property workingInvoiceDistData.
     */
    public com.cleanwise.service.api.value.InvoiceDistDetailData getWorkingInvoiceDistDetailData()  {

        return this.workingInvoiceDistDetailData;
    }

    /**
     * Setter for property workingInvoiceDistData.
     * @param workingInvoiceDistData New value of property workingInvoiceDistData.
     */
    public void setWorkingInvoiceDistDetailData(com.cleanwise.service.api.value.InvoiceDistDetailData workingInvoiceDistDetailData)  {

        this.workingInvoiceDistDetailData = workingInvoiceDistDetailData;
    }

    /**
     * Holds value of property itemQuantityRecvdS.
     */
    private String itemQuantityRecvdS;

    /**
     * Getter for property itemQuantityRecvd.
     * @return Value of property itemQuantityRecvd.
     */
    public String getItemQuantityRecvdS()  {

        return this.itemQuantityRecvdS;
    }

    /**
     * Setter for property itemQuantityRecvd.
     * @param itemQuantityRecvd New value of property itemQuantityRecvd.
     */
    public void setItemQuantityRecvdS(String itemQuantityRecvdS)  {

        this.itemQuantityRecvdS = itemQuantityRecvdS;
    }

    /**
     * Holds value of property actualCost.
     */
    private BigDecimal actualCost;

    /**
     * Getter for property actualCost.
     * @return Value of property actualCost.
     */
    public BigDecimal getActualCost() {

        return this.actualCost;
    }

    /**
     * Setter for property actualCost.
     * @param actualCost New value of property actualCost.
     */
    public void setActualCost(BigDecimal actualCost) {

        this.actualCost = actualCost;
    }

    /**
     * Holds value of property actualQty.
     */
    private int actualQty;

    /**
     * Getter for property actualQty.
     * @return Value of property actualQty.
     */
    public int getActualQty() {

        return this.actualQty;
    }

    /**
     * Setter for property actualQty.
     * @param actualQty New value of property actualQty.
     */
    public void setActualQty(int actualQty) {

        this.actualQty = actualQty;
    }

    /**
     * Holds value of property orderFreight.
     */
    private OrderFreightData orderFreight;

    /**
     * Getter for property orderFreight.
     * @return Value of property orderFreight.
     */
    public OrderFreightData getOrderFreight() {
        return this.orderFreight;
    }

    /**
     * Setter for property orderFreight.
     * @param orderFreight New value of property orderFreight.
     */
    public void setOrderFreight(OrderFreightData orderFreight) {
        this.orderFreight = orderFreight;
    }
    
    /**
     * Holds value of property orderHandling.
     */
    private OrderFreightData orderHandling;

    /**
     * Getter for property orderHandling.
     * @return Value of property orderHandling.
     */
    public OrderFreightData getOrderHandling() {
        return this.orderHandling;
    }

    /**
     * Setter for property orderHandling.
     * @param orderFreight New value of property orderHandling.
     */
    public void setOrderHandling(OrderFreightData orderHandling) {
        this.orderHandling = orderHandling;
    }

    /**
     * Holds the value of property orderDiscount.
     */
    private OrderAddOnChargeData orderDiscount;

    /**
     * Getter for property orderDiscount.
     * @return Value of property orderDiscount.
     */
    public OrderAddOnChargeData getOrderDiscount() {
        return this.orderDiscount;
    }

    /**
     * Setter for property orderDiscount.
     * @param orderDiscount New value of property orderDiscount.
     */
    public void setOrderDiscount(OrderAddOnChargeData orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    /**
     * Holds the value of property orderFuelSurcharge.
     */
    private OrderAddOnChargeData orderFuelSurcharge;

    /**
     * Getter for property orderFuelSurcharge.
     * @return Value of property orderFuelSurcharge.
     */
    public OrderAddOnChargeData getOrderFuelSurcharge() {
        return this.orderFuelSurcharge;
    }

    /**
     * Setter for property orderFuelSurcharge.
     * @param orderDiscount New value of property orderFuelSurcharge.
     */
    public void setOrderFuelSurcharge(OrderAddOnChargeData orderFuelSurcharge) {
        this.orderFuelSurcharge = orderFuelSurcharge;
    }


    /**
     * Holds the value of property orderSmallOrderFee.
     */
    private OrderAddOnChargeData orderSmallOrderFee;

    /**
     * Getter for property orderSmallOrderFee.
     * @return Value of property orderSmallOrderFee.
     */
    public OrderAddOnChargeData getOrderSmallOrderFee() {
        return this.orderSmallOrderFee;
    }

    /**
     * Setter for property orderSmallOrderFee.
     * @param orderDiscount New value of property orderSmallOrderFee.
     */
    public void setOrderSmallOrderFee(OrderAddOnChargeData orderSmallOrderFee) {
        this.orderSmallOrderFee = orderSmallOrderFee;
    }
    /**
     *  Set the mReSale field.
     *
     *@param  v   The new CwCostS value
     */
    public void setReSale(boolean v) {
        mReSale = v;
    }

    /**
     *  Get the mReSale field.
     *
     *@return    String
     */
    public boolean getReSale() {
        return mReSale;
    }

    public void setStandardProductList(String v) {
      this.mStandardProductList = v;
    }

    public String getStandardProductList() {
      return this.mStandardProductList;
    }

    public void setItemStatus(String v) {
        mItemStatus = v;
    }

    public String getItemStatus() {
        return mItemStatus;
    }

    public void setPoItemStatus(String v) {
        mPoItemStatus = v;
    }

    public String getPoItemStatus() {
        return mPoItemStatus;
    }

    public void setAssetInfo(AssetData assetInfo) {
        this.mAssetInfo = assetInfo;
    }

    public AssetData getAssetInfo() {
        return mAssetInfo;
    }

    public void setItemInfo(ItemData itemInfo) {
        this.mItemInfo = itemInfo;
    }

    public ItemData getItemInfo() {
        return mItemInfo;
    }

    public String getNewAssetName() {
        return mNewAssetName;
    }

    public void setNewAssetName(String newAssetName) {
        this.mNewAssetName = newAssetName;
    }

    public String getNewServiceName() {
        return mNewServiceName;
    }

    public void setNewServiceName(String newServiceName) {
        this.mNewServiceName = newServiceName;
    }

    public String getAssetIdS() {
        return mAssetIdS;
    }

    public void setAssetIdS(String assetIdS) {
        this.mAssetIdS = mAssetIdS;
    }

    public boolean getTaxExempt() {
        return mTaxExempt;
    }

    public void setTaxExempt(boolean taxExempt) {
        this.mTaxExempt = taxExempt;
    }

    public String getCatalogItemSkuNum() {
        return mCatalogItemSkuNum;
    }

    public void setCatalogItemSkuNum(String catalogItemSkuNum) {
        this.mCatalogItemSkuNum = catalogItemSkuNum;
    }
}

