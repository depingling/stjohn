package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.lang.*;
import java.math.BigDecimal;
import java.util.*;
import java.io.*;


/**
 * <code>OrderRequestData</code> is a ValueObject class
 *  representing an order request.
 * <p>
 * <pre>
 * Composition:
 * <br><br>
 *     OrderRequestData ::= site name | site id,
 *       optional account id,
 *       item id types,
 *       1*entries
 *     entries ::= item id | customer sku, quantity, price
 *     item id types ::= @see RefCodeNames
 *
 *     An OrderRequestData object <b>must</b> specify:
 *
 *        1) the site id, which corresponds to the Site
 *           business entity for the  ship to location
 *
 *        2) the account id, which corresponds to a bill to
 *           Account entity
 *
 *        3) entries, which are the order line items, each line
 *           item must specify the Cleanwise database item id
 *           and the quantity being purchased and the price
 *           calculated for that item based in the purchaser.
 *
 * </p>
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public class OrderRequestData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = 6261790545106991077L;

  /**
     * Describe class <code>ItemEntry</code> here.
     *
     */
    public class ItemEntry extends ValueObject {
        private static final long serialVersionUID = -7634968161694358572L;
        private int mmItemId = 0;
        private int mmAssetId=0;
        private String mmItemTypeCd;
        private int mmLineNumber = -1;
        private int mmQuantity = 0;
        private double mmPrice = 0;
        private String mmCustomerSku = "0";
        private String mmCustomerUom;
        private String mmCustomerProductDesc;
        private String mmCustomerPack;
        private String mmSaleTypeCd;
        private String mmDistErpNum;
        private int distributorId;
        private String mmDistSku = "0";
        private String initialOrderItemAction; //used when creating from 855
        private String isTaxExempt = "T";
        private BigDecimal lineTaxAmount = new BigDecimal(0.0);

        public String getIsTaxExempt(){
        	return isTaxExempt;
        }
        public void setIsTaxExempt(String v){
        	isTaxExempt = v;
        }

        public BigDecimal getLineTaxAmount(){
        	return lineTaxAmount;
        }
        public void setLineTaxAmount(BigDecimal v){
        	lineTaxAmount = v;
        }

        /**
         * Get the value of ItemId.
         * @return value of ItemId.
         */
        public int getItemId() {
            return mmItemId;
        }

        /**
         * Set the value of ItemId.
         * @param v  Value to assign to ItemId.
         */
        public void setItemId(int v) {
            this.mmItemId = v;
        }


        /**
         * Get the value of SaleTypeCd.
         * @return value of SaleTypeCd.
         */
        public String getSaleTypeCd() {
            return mmSaleTypeCd;
        }

        /**
         * Set the value of SaleTypeCd.
         * @param v  Value to assign to SaleTypeCd.
         */
        public void setSaleTypeCd(String v) {
            this.mmSaleTypeCd = v;
        }

        /**
         * Get the value of CustomerUom.
         * @return value of CustomerUom.
         */
        public String getCustomerUom() {
            return mmCustomerUom;
        }

        /**
         * Set the value of CustomerUom.
         * @param v  Value to assign to CustomerUom.
         */
        public void setCustomerUom(String  v) {
            this.mmCustomerUom = v;
        }

        /**
         * Get the value of LineNumber.
         * @return value of LineNumber.
         */
        public int getLineNumber() {
            return mmLineNumber;
        }

        /**
         * Set the value of LineNumber.
         * @param v  Value to assign to LineNumber.
         */
        public void setLineNumber(int  v) {
            this.mmLineNumber = v;
        }

        /**
         * Get the value of CustomerSku.
         * @return value of CustomerSku.
         */
        public String getCustomerSku() {
            return mmCustomerSku;
        }

        /**
         * Set the value of CustomerSku.
         * @param v  Value to assign to CustomerSku.
         */
        public void setCustomerSku(String  v) {
            this.mmCustomerSku = v;
        }

        /**
         * Get the value of Quantity.
         * @return value of Quantity.
         */
        public int getQuantity() {
            return mmQuantity;
        }

        /**
         * Set the value of Quantity.
         * @param v  Value to assign to Quantity.
         */
        public void setQuantity(int  v) {
            this.mmQuantity = v;
        }

        /**
         * Get the value of Price.
         * @return value of Price.
         */
        public double getPrice() {
            return mmPrice;
        }

        /**
         * Set the value of Price.
         * @param v  Value to assign to Price.
         */
        public void setPrice(double  v) {
            this.mmPrice = v;
        }

        /**
         * Get the value of CustomerProductDesc.
         * @return value of CustomerProductDesc.
         */
        public String getCustomerProductDesc() {
            return mmCustomerProductDesc;
        }

        /**
         * Set the value of CustomerProductDesc.
         * @param v  Value to assign to CustomerProductDesc.
         */
        public void setCustomerProductDesc(String  v) {
            this.mmCustomerProductDesc = v;
        }

        /**
         * Get the value of CustomerPack.
         * @return value of CustomerPack.
         */
        public String getCustomerPack() {
            return mmCustomerPack;
        }

        /**
         * Set the value of CustomerPack.
         * @param v  Value to assign to CustomerPack.
         */
        public void setCustomerPack(String  v) {
            this.mmCustomerPack = v;
        }
        boolean mmIsaInventoryItem = false;
        public void setAsInventoryItem(boolean v) {
            mmIsaInventoryItem = v;
        }
        public boolean isaInventoryItem() {
            return mmIsaInventoryItem;
        }

        private int  mmInventoryParValue = -1;
        public int getInventoryParValue() {
            return mmInventoryParValue;
        }
        public void setInventoryParValue(int v) {
            mmInventoryParValue = v;
        }
        private String mmInventoryQtyOnHand = "";
        public String getInventoryQtyOnHand() {
            return mmInventoryQtyOnHand;
        }
        public void setInventoryQtyOnHand(String v) {
            mmInventoryQtyOnHand = v;
        }


        public int getAssetId() {
            return mmAssetId;
        }

        public void setAssetId(int assetId) {
            this.mmAssetId = assetId;
        }

        public String getItemTypeCd() {
            return mmItemTypeCd;
        }

        public void setItemTypeCd(String itemTypeCd) {
            this.mmItemTypeCd = itemTypeCd;
        }

        public String getDistErpNum() {
            return mmDistErpNum;
        }

        public void setDistErpNum(String distErpNum) {
            this.mmDistErpNum = distErpNum;
        }

        public String getDistSku() {
            return this.mmDistSku;
        }

        public void setDistSku(String v) {
            this.mmDistSku = v;
        }


        private void populateBaseItem(int pLineNumber , int pQty,
                                      double pPrice,String pCustomerUom, String pProductDesc, String pPack) {

            mmQuantity = pQty;
            mmPrice = pPrice;
            mmCustomerUom = pCustomerUom;
            mmLineNumber = pLineNumber;
            mmCustomerProductDesc = pProductDesc;
            mmCustomerPack = pPack;
            mmSaleTypeCd = RefCodeNames.ITEM_SALE_TYPE_CD.END_USE;
        }

        private void populateBaseItem(int pLineNumber , int pQty,
                double pPrice,String pCustomerUom, String pProductDesc, String pPack, BigDecimal pLineTax,
                String pTaxExempt) {

        	mmQuantity = pQty;
        	mmPrice = pPrice;
        	mmCustomerUom = pCustomerUom;
        	mmLineNumber = pLineNumber;
        	mmCustomerProductDesc = pProductDesc;
        	mmCustomerPack = pPack;
        	mmSaleTypeCd = RefCodeNames.ITEM_SALE_TYPE_CD.END_USE;
        	lineTaxAmount = pLineTax;
        	isTaxExempt = pTaxExempt;
		}

        /**
         * Creates a new <code>ItemEntry</code> instance.
         *
         * @param pItemId an <code>int</code> value
         * @param pQty an <code>int</code> value
         * @param pPrice a <code>double</code> value
         */
        public ItemEntry(int pLineNumber , int pItemId, int pCwSku, int pQty, double pPrice,
                         String pCustomerUom, String pProductDesc, String pPack) {
            mmCustomerSku = String.valueOf(pCwSku);
            mmItemId = pItemId;
            populateBaseItem(pLineNumber, pQty, pPrice, pCustomerUom, pProductDesc, pPack);
        }

        public ItemEntry(int pLineNumber,int pItemId,int pAssetId,String pItemTypeCd,int pCwSku, int pQty, double pPrice, String pItemDesc) {
            mmCustomerSku = String.valueOf(pCwSku);
            mmItemId=pItemId;
            mmAssetId=pAssetId;
            mmItemTypeCd=pItemTypeCd;
            populateBaseItem(pLineNumber, pQty, pPrice, null, pItemDesc,null);
        }

        public ItemEntry(int pLineNumber, String pSku, int pQty, double pPrice,
                         String pCustomerUom, String pProductDesc, String pPack) {
            mmCustomerSku = pSku;
            populateBaseItem(pLineNumber, pQty, pPrice, pCustomerUom, pProductDesc, pPack);
        }

        public ItemEntry(int pLineNumber, String pSku, int pQty, double pPrice,
                String pCustomerUom, String pProductDesc, String pPack, BigDecimal pLineTax, String pTaxExempt) {
        	mmCustomerSku = pSku;
        	populateBaseItem(pLineNumber, pQty, pPrice, pCustomerUom, pProductDesc, pPack, pLineTax, pTaxExempt);
        }

      public ItemEntry(int pLineNumber , int pQty, String pDistSku, double pPrice,
                       String pCustomerUom, String pProductDesc, String pPack) {
          mmDistSku = String.valueOf(pDistSku);
          populateBaseItem(pLineNumber, pQty, pPrice, pCustomerUom, pProductDesc, pPack);
      }


        /**
         * Describe <code>toString</code> method here.
         *
         * @return a <code>String</code> value
         */
        public String toString() {
            return "[" + "LineNumber=" + mmLineNumber +
                ", ItemId=" + mmItemId +
                ", CustomerSku=" + mmCustomerSku +
                ", CustomerUom=" + mmCustomerUom +
                ", Quantity=" + mmQuantity +
                ", Price=" + mmPrice +
                ", CustomerProductDesc=" + mmCustomerProductDesc +
                ", CustomerPack=" + mmCustomerPack +
        		", SaleTypeCd=" + mmSaleTypeCd +
                ", DistributorId=" + distributorId +
                ", DistributorSkuNum=" + mmDistSku +
                "]";
        }

        /**
         * When set will log an initial order item action record
         */
		public String getInitialOrderItemAction() {
			return initialOrderItemAction;
		}

		/**
         * When set will log an initial order item action record
         */
		public void setInitialOrderItemAction(String initialOrderItemAction) {
			this.initialOrderItemAction = initialOrderItemAction;
		}

		public int getDistributorId() {
			return distributorId;
		}

		public void setDistributorId(int distributorId) {
			this.distributorId = distributorId;
		}
    }

    /**
     * Describe class <code>ItemEntryVector</code> here.
     *
     */
    public class ItemEntryVector extends java.util.ArrayList {
        private static final long serialVersionUID = 1835428483589630341L;
        /**
         * Creates a new <code>ItemEntryVector</code> instance.
         *
         */
        public ItemEntryVector () {}
    }

    private int mSiteId = -1;
    private int mAccountId = -1;
    // Set the Sku type to the default value of cleanwise sku,
    // which represents the CLW_ITEM.ITEM_ID column.
    private String mSkuTypeCd = RefCodeNames.SKU_TYPE_CD.CLW;
    private String mSiteName, mOtherPaymentInfo;

    private int mIncomingProfileId;
    private int mTradingPartnerId;
    private int mWorkOrderItemId = 0; //used when the Order was made for a WorkOrderItem

    /**
     * Get the value of SiteName.
     * @return value of SiteName.
     */
    public String getSiteName() {
        return mSiteName;
    }

    /**
     * Set the value of SiteName.
     * @param v  Value to assign to SiteName.
     */
    public void setSiteName(String v) {
        this.mSiteName = v;
    }

    /**
     * Get the value of IncomingProfileId.
     * @return value of IncomingProfileId.
     */
    public int getIncomingProfileId() {
        return mIncomingProfileId;
    }

    /**
     * Set the value of IncomingProfileId.
     * @param v  Value to assign to IncomingProfileId.
     */
    public void setIncomingProfileId(int v) {
        this.mIncomingProfileId = v;
    }

    /**
     * Get the value of TradingPartnerId.
     * @return value of TradingPartnerId.
     */
    public int getTradingPartnerId() {
        return mTradingPartnerId;
    }

    /**
     * Set the value of TradingPartnerId.
     * @param v  Value to assign to TradingPartnerId.
     */
    public void setTradingPartnerId(int v) {
        this.mTradingPartnerId = v;
    }


    /**
     * Get the value of SkuTypeCd.
     * @return value of SkuTypeCd.
     * @see com.cleanwise.service.api.util.RefCodeNames.SKU_TYPE_CD
     */
    public String getSkuTypeCd() {
        return mSkuTypeCd;
    }

    /**
     * Set the value of SkuTypeCd.
     * @param v  Value to assign to SkuTypeCd.
     * @see com.cleanwise.service.api.util.RefCodeNames.SKU_TYPE_CD
     */
    public void setSkuTypeCd(String  v) {
        this.mSkuTypeCd = v;
    }

    private ItemEntryVector mEntriesCollection = null;

    private String mCustomerPoNumber, mCustomerReleaseNumber;
    private String mCustomerOrderDate;
    private String mCustomerComments;
    private String mOrderRefNumber;
    private String mOrderContactName;
    private String mOrderTelephoneNumber;
    private String mOrderFaxNumber;
    private String mOrderEmail;
    private String mOrderSourceCd;
    private String mHoldUntilDate;
    private boolean historicalOrder = false;
    private boolean skipDupOrderValidation = false;

    private java.util.ArrayList mOrderNoteCollection = null;

    private Date mOrderRequestedShipDate;
    private AddressData mOrderSiteAddress;
    private boolean mBypassOrderRouting = false;
    private boolean mBypassCustomerWorkflow = false;
    private IdVector mReplacedOrderIds;
    private Map poNumberByVendor = null;
    private HashMap serviceFeeDet = null;
    private int parentEventId = 0;
    private String orderBudgetTypeCd="";

    /**
     * Get the value of OrderSourceCd.
     * @return value of OrderSourceCd.
     */
    public String getOrderSourceCd() {
        return mOrderSourceCd;
    }

    /**
     * Set the value of OrderSourceCd.
     * @param v  Value to assign to OrderSourceCd.
     */
    public void setOrderSourceCd(String  v) {
        this.mOrderSourceCd = v;
    }

    /**
     * Get the value of HoldUntilDate.
     * @return value of HoldUntilDate.
     */
    public String getHoldUntilDate() {
        return mHoldUntilDate;
    }

    /**
     * Set the value of HoldUntilDate.
     * @param v  Value to assign to HoldUntilDate.
     */
    public void setHoldUntilDate(String  v) {
        this.mHoldUntilDate = v;
    }

    private OrderAddressData mOrderShipAddress;
    private OrderAddressData mOrderBillAddress;


    /**
     * Get the value of OrderRefNumber.
     * @return value of OrderRefNumber.
     */
    public String getOrderRefNumber() {
        return mOrderRefNumber;
    }

    /**
     * Set the value of OrderRefNumber.
     * @param v  Value to assign to OrderRefNumber.
     */
    public void setOrderRefNumber(String  v) {
        this.mOrderRefNumber = v;
    }

    /**
     * Get the value of OrderSiteAddress.
     * @return value of OrderSiteAddress.
     */
    public AddressData getOrderSiteAddress() {
        return mOrderSiteAddress;
    }

    /**
     * Set the value of OrderSiteAddress.
     * @param v  Value to assign to OrderSiteAddress.
     */
    public void setOrderSiteAddress(AddressData  v) {
        this.mOrderSiteAddress = v;
    }

    /**
     * Get the value of OrderShipAddress.
     * @return value of OrderShipAddress.
     */
    public OrderAddressData getOrderShipAddress() {
        if (mOrderShipAddress == null)
            mOrderShipAddress = OrderAddressData.createValue();
        return mOrderShipAddress;
    }

    /**
     * Set the value of OrderShipAddress.
     * @param v  Value to assign to OrderShipAddress.
     */
    public void setOrderShipAddress(OrderAddressData  v) {
        this.mOrderShipAddress = v;
    }

    /**
     * Get the value of OrderBillAddress.
     * @return value of OrderBillAddress.
     */
    public OrderAddressData getOrderBillAddress() {
        if (mOrderBillAddress == null)
            mOrderBillAddress = OrderAddressData.createValue();
        return mOrderBillAddress;
    }

    /**
     * Set the value of OrderBillAddress.
     * @param v  Value to assign to OrderBillAddress.
     */
    public void setOrderBillAddress(OrderAddressData  v) {
        this.mOrderBillAddress = v;
    }

    /**
     * Get the value of OrderEmail.
     * @return value of OrderEmail.
     */
    public String getOrderEmail() {
        return mOrderEmail;
    }

    /**
     * Set the value of OrderEmail.
     * @param v  Value to assign to OrderEmail.
     */
    public void setOrderEmail(String  v) {
        this.mOrderEmail = v;
    }

    /**
     * Get the value of OrderRequestedShipDate.
     * @return value of OrderRequestedShipDate.
     */
    public Date getOrderRequestedShipDate() {
        return mOrderRequestedShipDate;
    }

    /**
     * Set the value of OrderRequestedShipDate.
     * @param v  Value to assign to OrderRequestedShipDate.
     */
    public void setOrderRequestedShipDate(Date  v) {
        this.mOrderRequestedShipDate = v;
    }

    /**
     * Get the value of OrderFaxNumber.
     * @return value of OrderFaxNumber.
     */
    public String getOrderFaxNumber() {
        return mOrderFaxNumber;
    }

    /**
     * Set the value of OrderFaxNumber.
     * @param v  Value to assign to OrderFaxNumber.
     */
    public void setOrderFaxNumber(String  v) {
        this.mOrderFaxNumber = v;
    }


    /**
     * Get the value of OrderTelephoneNumber.
     * @return value of OrderTelephoneNumber.
     */
    public String getOrderTelephoneNumber() {
        return mOrderTelephoneNumber;
    }

    /**
     * Set the value of OrderTelephoneNumber.
     * @param v  Value to assign to OrderTelephoneNumber.
     */
    public void setOrderTelephoneNumber(String  v) {
        this.mOrderTelephoneNumber = v;
    }

    /**
     * Get the value of OrderContactName.
     * @return value of OrderContactName.
     */
    public String getOrderContactName() {
        return mOrderContactName;
    }

    /**
     * Set the value of OrderContactName.
     * @param v  Value to assign to OrderContactName.
     */
    public void setOrderContactName(String  v) {
        this.mOrderContactName = v;
    }

    /**
     * Get the value of CustomerPoNumber.
     * @return value of CustomerPoNumber.
     */
    public String getCustomerPoNumber() {
        return mCustomerPoNumber;
    }

    /**
     * Set the value of CustomerPoNumber.
     * @param v  Value to assign to CustomerPoNumber.
     */
    public void setCustomerPoNumber(String  v) {
        this.mCustomerPoNumber = v;
    }

    public String getCustomerReleaseNumber() {
        return mCustomerReleaseNumber;
    }

    public void setCustomerReleaseNumber(String  v) {
        this.mCustomerReleaseNumber = v;
    }

    /**
     * Get the value of CustomerOrderDate.
     * @return value of CustomerOrderDate.
     */
    public String getCustomerOrderDate() {
        return mCustomerOrderDate;
    }

    /**
     * Set the value of CustomerOrderDate.
     * @param v  Value to assign to CustomerOrderDate.
     */
    public void setCustomerOrderDate(String  v) {
        this.mCustomerOrderDate = v;
    }
    /**
     * Get the value of CustomerComments.
     * @return value of CustomerComments.
     */
    public String getCustomerComments() {
        return mCustomerComments;
    }

    /**
     * Set the value of CustomerComments.
     * @param v  Value to assign to CustomerComments.
     */
    public void setCustomerComments(String  v) {
        this.mCustomerComments = v;
    }

    /**
     * Get the value of ReplacedOrderIds.
     * @return value of ReplacedOrderIds.
     */
    public IdVector getReplacedOrderIds() {
        return mReplacedOrderIds;
    }

    /**
     * Set the value of ReplacedOrderIds.
     * @param v  Value to assign to ReplacedOrderIds.
     */
    public void setReplacedOrderIds(IdVector  v) {
        this.mReplacedOrderIds = v;
    }

    /**
     * Describe <code>addItemEntry</code> method here.
     *
     * @param pItemId an <code>int</code> value
     * @param pQty an <code>int</code> value
     * @param pPrice a <code>double</code> value
     * @param pDesc a <code>String</code> value
     * @param pPack a <code>String</code> value
     */
    public void addItemEntry( int pLineNumber, int pItemId, int pCwSku, int pQty, double pPrice,
                              String pUom, String pDesc, String pPack) {
    	ItemEntry nitem = new ItemEntry(pLineNumber, pItemId, pCwSku,
                                        pQty, pPrice, pUom, pDesc, pPack);
        if ( null == mEntriesCollection ) {
            mEntriesCollection = new ItemEntryVector();
        }
        mEntriesCollection.add(nitem);
    }

    public ItemEntry addItemEntry
        (int pLineNumber, int pItemId, int pCwSku, int pQty, double pPrice,
          String pUom, String pDesc, String pPack,
          boolean pIsaInventoryItem,
          int pInventoryParValue,
          String pInventoryOnhandQty,
          boolean pIsaReSaleItem) {

    	ItemEntry nitem = new ItemEntry(pLineNumber, pItemId, pCwSku,
                                        pQty, pPrice, pUom, pDesc, pPack);
        nitem.setInventoryQtyOnHand(pInventoryOnhandQty);
        nitem.setInventoryParValue(pInventoryParValue);
        nitem.setAsInventoryItem(pIsaInventoryItem);
        if(pIsaReSaleItem){
            nitem.setSaleTypeCd(RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE);
        }else{
            nitem.setSaleTypeCd(RefCodeNames.ITEM_SALE_TYPE_CD.END_USE);
        }

        if ( null == mEntriesCollection ) {
            mEntriesCollection = new ItemEntryVector();
        }
        mEntriesCollection.add(nitem);
        return nitem;
    }

    public void addItemEntry
    (int pLineNumber, int pItemId, int pCwSku, int pQty, double pPrice,
      String pUom, String pDesc, String pPack,
      boolean pIsaInventoryItem,
      int pInventoryParValue,
      String pInventoryOnhandQty,
      boolean pIsaReSaleItem,
      String pDistErpNum) {

    	ItemEntry nitem = addItemEntry
        (pLineNumber, pItemId, pCwSku, pQty, pPrice,
          pUom, pDesc, pPack,
          pIsaInventoryItem,
          pInventoryParValue,
          pInventoryOnhandQty,
          pIsaReSaleItem);
    nitem.setDistErpNum(pDistErpNum);
}


    public void addItemEntry
    (int pLineNumber, int pItemId, int pCwSku, int pQty, double pPrice,
      String pUom, String pDesc, String pPack,
      boolean pIsaInventoryItem,
      int pInventoryParValue,
      String pInventoryOnhandQty,
      boolean pIsaReSaleItem,
      int pDistributorId,
      String pInitialOrderItemActionCd) {

    	ItemEntry nitem = addItemEntry
        (pLineNumber, pItemId, pCwSku, pQty, pPrice,
          pUom, pDesc, pPack,
          pIsaInventoryItem,
          pInventoryParValue,
          pInventoryOnhandQty,
          pIsaReSaleItem);
    nitem.setDistributorId(pDistributorId);
    nitem.setInitialOrderItemAction(pInitialOrderItemActionCd);
}

    public void addItemEntry(int pLineNumber,int pItemId,int mmAssetId,
                              String mmItemTypeCd,int pCwSku,
                              int pQty, double pPrice, String pItemDescm) {

    	ItemEntry nitem = new ItemEntry(pLineNumber, pItemId,mmAssetId,mmItemTypeCd, pCwSku,pQty, pPrice,pItemDescm);
        nitem.setSaleTypeCd(RefCodeNames.ITEM_SALE_TYPE_CD.END_USE);
        if ( null == mEntriesCollection ) {
            mEntriesCollection = new ItemEntryVector();
        }
        mEntriesCollection.add(nitem);
    }

    public void addItemEntry(int pLineNumber, String pSku, int pQty,
                              double pPrice, String pUom, String pDesc, String pPack) {
    	addItemEntry(pLineNumber,pSku,pQty,pPrice,pUom,pDesc,pPack,null,"T");
    }

    /**
     * Describe <code>addItemEntry</code> method here.
     *
     * @param pItemId an <code>int</code> value
     * @param pQty an <code>int</code> value
     * @param pPrice a <code>double</code> value
     * @param pDesc a <code>String</code> value
     * @param pPack a <code>String</code> value
     */
    public void addItemEntry( int pLineNumber, String pSku, int pQty,
                              double pPrice, String pUom, String pDesc, String pPack,
                              BigDecimal pTaxAmt,String pTaxExempt) {
    	ItemEntry nitem = new ItemEntry(pLineNumber, pSku,
                                        pQty, pPrice, pUom, pDesc, pPack,pTaxAmt, pTaxExempt);

        if ( null == mEntriesCollection ) {
            mEntriesCollection = new ItemEntryVector();
        }
        mEntriesCollection.add(nitem);
    }

    public void addItemEntry(int pLineNumber, int pQty, double pPrice,  String pDesc,
                             int pDistId, String pDistSku, String pUom, int pItemId) {

    	ItemEntry nitem = new ItemEntry(pLineNumber, pQty, pDistSku, pPrice, pUom, pDesc, null);
        nitem.setItemId(pItemId);
        if ( null == mEntriesCollection ) {
            mEntriesCollection = new ItemEntryVector();
        }
        mEntriesCollection.add(nitem);
    }


    /**
     * Get the value of EntriesCollection.
     * @return value of EntriesCollection.
     */
    public ItemEntryVector getEntriesCollection() {
        if ( null == mEntriesCollection ) {
            mEntriesCollection = new ItemEntryVector();
        }
        return mEntriesCollection;
    }

    /**
     * Set the value of EntriesCollection.
     * @param v  Value to assign to EntriesCollection.
     */
    public void setEntriesCollection(ItemEntryVector  v) {
        this.mEntriesCollection = v;
    }

    /**
     * Get the value of AccountId.
     * @return value of AccountId.
     */
    public int getAccountId() {
        return mAccountId;
    }

    /**
     * Set the value of AccountId.
     * @param v  Value to assign to AccountId.
     */
    public void setAccountId(int  v) {
        this.mAccountId = v;
    }


    /**
     * Creates a new <code>OrderRequestData</code> instance.
     *
     * @param pSiteId an <code>int</code> value
     * @param pAccountId an <code>int</code> value
     */
    public OrderRequestData (int pSiteId, int pAccountId) {
        mSiteId = pSiteId;
        mAccountId = pAccountId;
    }

    private OrderRequestData () {
    }

    public OrderRequestData (String pSiteName, int pAccountId) {
        mSiteName = pSiteName;
        mAccountId = pAccountId;
    }

    /**
     * Creates a new OrderRequestData
     *
     * @return
     *  Newly initialized OrderRequestData object.
     */
    public static OrderRequestData createValue ()
    {
        OrderRequestData valueData = new OrderRequestData();
        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderRequestData object
     */
    public String toString() {
        return "[" +
            "SiteId=" + mSiteId +
            ", AccountId=" + mAccountId +
            ", SkuTypeCd=" + mSkuTypeCd +
            ", SiteName=" + mSiteName +
            ", CustomerPoNumber=" + mCustomerPoNumber +
            ", CustomerOrderDate=" + mCustomerOrderDate +
            ", CustomerComments=" + mCustomerComments +
            ", BypassOrderRouting=" + mBypassOrderRouting +
            ", PaymentType= " + mPaymentType +
            ", CcNumber= XXXX" +
            ", CcExpMonth= " + mCcExpMonth +
            ", CcExpYear= " + mCcExpYear +
            ", CcBuyerName= " + mCcBuyerName +
            ", CcStreet1= " + mCcStreet1 +
            ", CcStreet2= " + mCcStreet2 +
            ", CcCity= " + mCcCity +
            ", CcState= " + mCcState +
            ", CcPostalCode= " + mCcPostalCode +
            ", EntriesCollection=" + mEntriesCollection +
            (Utility.isSet(mHoldUntilDate)?"HoldUntilDate="+mHoldUntilDate:"")+
            "]";
    }

    public String toInfoString() {
        return "[" + "SiteId=" + mSiteId +
            ", AccountId=" + mAccountId +
            ", SkuTypeCd=" + mSkuTypeCd +
            ", SiteName=" + mSiteName +
            ", CustomerPoNumber=" + mCustomerPoNumber +
            ", CustomerComments=" + mCustomerComments +
            "]";
    }


    /**
     * Sets the SiteId field. This field is required to be set in the database.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
    }

    private String mPaymentType = "";
    private String mOrderType = "";
    private String mCcNumber = "";
    private int    mCcExpMonth = -1;
    private int    mCcExpYear = -1;
    private String mCcBuyerName = "";
    private String mCcStreet1 = "";
    private String mCcStreet2 = "";
    private String mCcCity = "";
    private String mCcState = "";
    private String mCcPostalCode = "";
    private boolean mFreeFormatAddress = false;

    private String mCcPaymetricCurrency = "";
    private String mCcPaymetricAvsStatus = "";
    private String mCcPaymetricAvsAddress = "";
    private String mCcPaymetricAvsZipCode = "";
    private BigDecimal mCcPaymetricAmount = new BigDecimal(0);
    private String mCcPaymetricAuthRefCode = "";
    private String mCcPaymetricAuthCode = "";
    private String mCcPaymetricResponseCode = "";
    private Date mCcPaymetricAuthDate = null;
    private Date mCcPaymetricAuthTime = null;
    private String mCcPaymetricTransactionId = "";
    private String mCcPaymetricMessage = "";

    /**
     * Get the value of PaymentType.
     * @return value of PaymentType.
     */
    public String getPaymentType() {
        return mPaymentType;
    }

    /**
     * Set the value of PaymentType.
     * @param v  Value to assign to PaymentType.
     */
    public void setPaymentType(String  v) {
        this.mPaymentType = v;
    }

    /**
     * Get the value of OrderType.
     * @return value of OrderType.
     */
    public String getOrderType() {
        if ( null == mOrderType ) mOrderType = "";
        return mOrderType;
    }

    /**
     * Set the value of OrderType.
     * @param v  Value to assign to OrderType.
     */
    public void setOrderType(String  v) {
        this.mOrderType = v;
    }

    /**
     * Get the value of CcNumber.
     * @return value of CcNumber.
     */
    public String getCcNumber() {
        return mCcNumber;
    }

    /**
     * Set the value of CcNumber.
     * @param v  Value to assign to CcNumber.
     */
    public void setCcNumber(String  v) {
        this.mCcNumber = v;
    }


    /**
     * Get the value of CcExpMonth.
     * @return value of CcExpMonth.
     */
    public int getCcExpMonth() {
        return mCcExpMonth;
    }

    /**
     * Set the value of CcExpMonth.
     * @param v  Value to assign to CcExpMonth.
     */
    public void setCcExpMonth(int  v) {
        this.mCcExpMonth = v;
    }




    /**
     * Get the value of CcExpYear.
     * @return value of CcExpYear.
     */
    public int getCcExpYear() {
        return mCcExpYear;
    }

    /**
     * Set the value of CcExpYear.
     * @param v  Value to assign to CcExpYear.
     */
    public void setCcExpYear(int  v) {
        this.mCcExpYear = v;
    }


    /**
     * Get the value of CcBuyerName.
     * @return value of CcBuyerName.
     */
    public String getCcBuyerName() {
        return mCcBuyerName;
    }

    /**
     * Set the value of CcBuyerName.
     * @param v  Value to assign to CcBuyerName.
     */
    public void setCcBuyerName(String  v) {
        this.mCcBuyerName = v;
    }


    /**
     * Get the value of CcStreet1.
     * @return value of CcStreet1.
     */
    public String getCcStreet1() {
        return mCcStreet1;
    }

    /**
     * Set the value of CcStreet1.
     * @param v  Value to assign to CcStreet1.
     */
    public void setCcStreet1(String  v) {
        this.mCcStreet1 = v;
    }


    /**
     * Get the value of CcStreet2.
     * @return value of CcStreet2.
     */
    public String getCcStreet2() {
        return mCcStreet2;
    }

    /**
     * Set the value of CcStreet2.
     * @param v  Value to assign to CcStreet2.
     */
    public void setCcStreet2(String  v) {
        this.mCcStreet2 = v;
    }


    /**
     * Get the value of CcCity.
     * @return value of CcCity.
     */
    public String getCcCity() {
        return mCcCity;
    }

    /**
     * Set the value of CcCity.
     * @param v  Value to assign to CcCity.
     */
    public void setCcCity(String  v) {
        this.mCcCity = v;
    }

    /**
     * Get the value of CcState.
     * @return value of CcState.
     */
    public String getCcState() {
        return mCcState;
    }

    /**
     * Set the value of CcState.
     * @param v  Value to assign to CcState.
     */
    public void setCcState(String  v) {
        this.mCcState = v;
    }


    /**
     * Get the value of CcPostalCode.
     * @return value of CcPostalCode.
     */
    public String getCcPostalCode() {
        return mCcPostalCode;
    }

    /**
     * Set the value of CcPostalCode.
     * @param v  Value to assign to CcPostalCode.
     */
    public void setCcPostalCode(String  v) {
        this.mCcPostalCode = v;
    }

    public String getCcPaymetricCurrency() {
        return mCcPaymetricCurrency;
    }
    public void setCcPaymetricCurrency(String  v) {
        this.mCcPaymetricCurrency = v;
    }

    public String getCcPaymetricAvsStatus() {
        return mCcPaymetricAvsStatus;
    }
    public void setCcPaymetricAvsStatus(String  v) {
        this.mCcPaymetricAvsStatus = v;
    }

    public String getCcPaymetricAvsAddress() {
        return mCcPaymetricAvsAddress;
    }
    public void setCcPaymetricAvsAddress(String  v) {
        this.mCcPaymetricAvsAddress = v;
    }

    public String getCcPaymetricAvsZipCode() {
        return mCcPaymetricAvsZipCode;
    }
    public void setCcPaymetricAvsZipCode(String  v) {
        this.mCcPaymetricAvsZipCode = v;
    }

    public BigDecimal getCcPaymetricAmount() {
        return mCcPaymetricAmount;
    }
    public void setCcPaymetricAmount(BigDecimal  v) {
        this.mCcPaymetricAmount = v;
    }

    public String getCcPaymetricAuthRefCode() {
        return mCcPaymetricAuthRefCode;
    }
    public void setCcPaymetricAuthRefCode(String  v) {
        this.mCcPaymetricAuthRefCode = v;
    }

    public String getCcPaymetricAuthCode() {
        return mCcPaymetricAuthCode;
    }
    public void setCcPaymetricAuthCode(String  v) {
        this.mCcPaymetricAuthCode = v;
    }

    public String getCcPaymetricResponseCode() {
        return mCcPaymetricResponseCode;
    }
    public void setCcPaymetricResponseCode(String  v) {
        this.mCcPaymetricResponseCode = v;
    }

    public Date getCcPaymetricAuthDate() {
        return mCcPaymetricAuthDate;
    }
    public void setCcPaymetricAuthDate(Date  v) {
        this.mCcPaymetricAuthDate = v;
    }

    public Date getCcPaymetricAuthTime() {
        return mCcPaymetricAuthTime;
    }
    public void setCcPaymetricAuthTime(Date  v) {
        this.mCcPaymetricAuthTime = v;
    }

    public String getCcPaymetricTransactionId() {
        return mCcPaymetricTransactionId;
    }
    public void setCcPaymetricTransactionId(String  v) {
        this.mCcPaymetricTransactionId = v;
    }

    public String getCcPaymetricMessage() {
        return mCcPaymetricMessage;
    }
    public void setCcPaymetricMessage(String  v) {
        this.mCcPaymetricMessage = v;
    }


    PropertyDataVector Properties;
    OrderMetaDataVector orderMeta;

    /** Holds value of property billingOrder. */
    private boolean billingOrder;

    /** Holds value of property orderNote. */
    private String orderNote;

    /** Holds value of property bypassPreCapturePipeline. */
    private boolean bypassPreCapturePipeline;

    /** Holds value of property orderStatusCdOveride. */
    private String orderStatusCdOveride;

    /** Holds value of property accountErpNumberOveride. */
    private String accountErpNumberOveride;

    /** Holds value of property processOrderResultDataList. */
    private java.util.List processOrderResultDataList;

    /**
     * Get the value of Properties.
     * @return value of Properties.
     */
    public PropertyDataVector getProperties() {
        return Properties;
    }

    /**
     * Set the value of Properties.
     * @param v  Value to assign to Properties.
     */
    public void setProperties(PropertyDataVector  v) {
        this.Properties = v;
    }

    public OrderMetaDataVector getOrderMeta() {
        return orderMeta;
    }

    public void setOrderMeta(OrderMetaDataVector orderMeta) {
        this.orderMeta = orderMeta;
    }

    /**
     * Get the value of FreeFormatAddress.
     * @return value of FreeFormatAddress.
     */
    public boolean getFreeFormatAddress() {
        return mFreeFormatAddress;
    }

    /**
     * Set the value of FreeFormatAddress.
     * @param v  Value to assign to FreeFormatAddress.
     */
    public void setFreeFormatAddress(boolean  v) {
        this.mFreeFormatAddress = v;
    }

    /** Getter for property billingOrder.
     * @return Value of property billingOrder.
     *
     */
    public boolean isBillingOrder() {
        return this.billingOrder;
    }

    /** Setter for property billingOrder.
     * @param billingOrder New value of property billingOrder.

     *
     */
    public void setBillingOrder(boolean billingOrder) {
        this.billingOrder = billingOrder;
    }

    /** Getter for property orderNote.
     * @return Value of property orderNote.
     *
     */
    public String getOrderNote() {
        return this.orderNote;
    }

    /** Setter for property orderNote.
     * @param orderNote New value of property orderNote.
     *
     */
    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public boolean getBypassOrderRouting() {
        return this.mBypassOrderRouting;
    }
    public void setBypassOrderRouting(boolean v) {
        this.mBypassOrderRouting = v;
    }

   public boolean isBypassCustomerWorkflow() {
        return this.mBypassCustomerWorkflow;
    }
    public void setBypassCustomerWorkflow(boolean v) {
        this.mBypassCustomerWorkflow = v;
    }

    /** Getter for property bypassPreCapturePipeline.
     * @return Value of property bypassPreCapturePipeline.
     *
     */
    public boolean isBypassPreCapturePipeline() {
        return this.bypassPreCapturePipeline;
    }

    /** Setter for property bypassPreCapturePipeline.
     * @param bypassPreCapturePipeline New value of property bypassPreCapturePipeline.
     *
     */
    public void setBypassPreCapturePipeline(boolean bypassPreCapturePipeline) {
        this.bypassPreCapturePipeline = bypassPreCapturePipeline;
    }

    /** Getter for property orderStatusCdOveride.
     * @return Value of property orderStatusCdOveride.
     *
     */
    public String getOrderStatusCdOveride() {
        return this.orderStatusCdOveride;
    }

    /** Setter for property orderStatusCdOveride.
     * @param orderStatusCdOveride New value of property orderStatusCdOveride.
     *
     */
    public void setOrderStatusCdOveride(String orderStatusCdOveride) {
        this.orderStatusCdOveride = orderStatusCdOveride;
    }

    public void addOrderNote(String v) {
	if ( null == mOrderNoteCollection) {
	    mOrderNoteCollection = new java.util.ArrayList();
	}
	mOrderNoteCollection.add(v);
    }
    public java.util.ArrayList getOrderNotes() {
	return mOrderNoteCollection;
    }

    /** Getter for property accountErpNumberOveride.
     * @return Value of property accountErpNumberOveride.
     *
     */
    public String getAccountErpNumberOveride() {
        return this.accountErpNumberOveride;
    }

    /** Setter for property accountErpNumberOveride.
     * @param accountErpNumberOveride New value of property accountErpNumberOveride.
     *
     */
    public void setAccountErpNumberOveride(String accountErpNumberOveride) {
        this.accountErpNumberOveride = accountErpNumberOveride;
    }

    /**
     *Gets the chain of any ProcessOrderResultData objects that have been generated during
     *the processing of this order request
     */
    public ProcessOrderResultData getProcessOrderResultDataChain(){
        return mProcessOrderResultData;
    }

    private ProcessOrderResultData mProcessOrderResultData;

    /**
     * Holds value of property customerBillingUnit.
     */
    private String customerBillingUnit;

    /**
     * Holds value of property punchOutOrderOrigOrderNum.
     */
    private String punchOutOrderOrigOrderNum;

    /**
     * Holds value of property refOrderId.
     */
    private int refOrderId;

    /**
     * Holds value of property customerSystemIdentifier.
     */
    private String customerSystemIdentifier;

    /**
     * Holds value of property customerSystemURL.
     */
    private String customerSystemURL;

    /**
     * Holds value of property userNameKey.
     */
    private String userNameKey;

    /**
     * Holds value of property ccType.
     */
    private String ccType;

    /**
     *Adds a ProcessOrderResultData object to this request that may be retrieved later via the
     *@see getProcessOrderResultDataChain method
     */
    public void addProcessedOrderResult(ProcessOrderResultData pRes){
        if(mProcessOrderResultData == null){
            mProcessOrderResultData = pRes;
        }else{
            ProcessOrderResultData tmpRes;
            tmpRes = mProcessOrderResultData;
            while(tmpRes.getNext() != null){
                tmpRes = tmpRes.getNext();
            }
            tmpRes.setNext(pRes);
        }
    }


    public void addArbitraryOrderProperty(String propName, String value){
        if(getProperties() == null){
            setProperties(new PropertyDataVector());
        }
        PropertyData pd = PropertyData.createValue();
        pd.setPropertyTypeCd(propName);
        pd.setShortDesc(propName);
        pd.setValue(value);
        pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        getProperties().add(pd);
    }

    public void addArbitraryOrderProperty(String propType, String propName, String value){
        if(getProperties() == null){
            setProperties(new PropertyDataVector());
        }
        PropertyData pd = PropertyData.createValue();
        pd.setPropertyTypeCd(propType);
        pd.setShortDesc(propName);
        pd.setValue(value);
        pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        getProperties().add(pd);
    }

     public void addArbitraryOrderMeta(String valueName, String value,int valueNum){
        if(getOrderMeta() == null){
            setOrderMeta(new OrderMetaDataVector());
        }
        OrderMetaData omd = OrderMetaData.createValue();
        omd.setName(valueName);
        omd.setValue(value);
        omd.setValueNum(valueNum);
        getOrderMeta().add(omd);
    }
    /**
     * Getter for property customerBillingUnit.
     * @return Value of property customerBillingUnit.
     */
    public String getCustomerBillingUnit() {
        return this.customerBillingUnit;
    }

    /**
     * Setter for property customerBillingUnit.
     * @param customerBillingUnit New value of property customerBillingUnit.
     */
    public void setCustomerBillingUnit(String customerBillingUnit) {
        this.customerBillingUnit = customerBillingUnit;
    }

    /**
     * Getter for property punchOutOrderOrigOrderNum.
     * @return Value of property punchOutOrderOrigOrderNum.
     */
    public String getPunchOutOrderOrigOrderNum() {
        return this.punchOutOrderOrigOrderNum;
    }

    /**
     * Setter for property punchOutOrderOrigOrderNum.
     * @param punchOutOrderOrigOrderNum New value of property punchOutOrderOrigOrderNum.
     */
    public void setPunchOutOrderOrigOrderNum(String punchOutOrderOrigOrderNum) {
        this.punchOutOrderOrigOrderNum = punchOutOrderOrigOrderNum;
    }

    /**
     * Getter for property prevOrderId.
     * @return Value of property prevOrderId.
     */
    public int getRefOrderId() {
        return this.refOrderId;
    }

    /**
     * Setter for property prevOrderId.
     * @param prevOrderId New value of property prevOrderId.
     */
    public void setRefOrderId(int refOrderId) {
        this.refOrderId = refOrderId;
    }

    /**
     * Getter for property customerSystemIdentifier.
     * @return Value of property customerSystemIdentifier.
     */
    public String getCustomerSystemIdentifier() {
        return this.customerSystemIdentifier;
    }

    /**
     * Setter for property customerSystemIdentifier.
     * @param customerSystemIdentifier New value of property customerSystemIdentifier.
     */
    public void setCustomerSystemIdentifier(String customerSystemIdentifier) {
        this.customerSystemIdentifier = customerSystemIdentifier;
    }

    /**
     * Getter for property customerSystemURL.
     * @return Value of property customerSystemURL.
     */
    public String getCustomerSystemURL() {
        return this.customerSystemURL;
    }

    /**
     * Setter for property customerSystemURL.
     * @param customerSystemURL New value of property customerSystemURL.
     */
    public void setCustomerSystemURL(String customerSystemURL) {
        this.customerSystemURL = customerSystemURL;
    }

    /**
     * Getter for property userNameKey.
     * @return Value of property userNameKey.
     */
    public String getUserNameKey() {
        return this.userNameKey;
    }

    /**
     * Setter for property userNameKey.
     * @param userNameKey New value of property userNameKey.
     */
    public void setUserNameKey(String userNameKey) {
        this.userNameKey = userNameKey;
    }

    public String getOtherPaymentInfo() {
        return this.mOtherPaymentInfo;
    }

    public void setOtherPaymentInfo(String v) {
        this.mOtherPaymentInfo = v;
    }

    /**
     * Getter for property ccType.
     * @return Value of property ccType.
     */
    public String getCcType() {
        return this.ccType;
    }

    /**
     * Setter for property ccType.
     * @param ccType New value of property ccType.
     */
    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

	public void setPoNumberByVendor(Map poNumberByVendor) {
		this.poNumberByVendor = poNumberByVendor;
	}

	public Map getPoNumberByVendor() {
		return poNumberByVendor;
	}

	public void setServiceFeeDetail(HashMap servFeeDet) {
		this.serviceFeeDet = servFeeDet;
	}

	public HashMap getServiceFeeDetail() {
		return serviceFeeDet;
	}

	/**
	 * Special type of order that is for history only.  As little logic as possible should go into evaluating this order type
	 * @return
	 */
	public boolean isHistoricalOrder() {
		return historicalOrder;
	}

	/**
	 * Special type of order that is for history only.  As little logic as possible should go into evaluating this order type
	 * @return
	 */
	public void setHistoricalOrder(boolean historicalOrder) {
		this.historicalOrder = historicalOrder;
	}

        public void setWorkOrderItemId(int workOrderItemId) {
            this.mWorkOrderItemId = workOrderItemId;
	}

        public int getWorkOrderItemId() {
            return mWorkOrderItemId;
	}

    public boolean isSkipDupOrderValidation() {
		return skipDupOrderValidation;
	}

	public void setSkipDupOrderValidation(boolean skipDupOrderValidation) {
		this.skipDupOrderValidation = skipDupOrderValidation;
	}
	
	public void setParentEventId(int parentEventId) {
		this.parentEventId = parentEventId;
	}
	public int getParentEventId() {
		return parentEventId;
	}

	public void setOrderBudgetTypeCd(String orderBudgetTypeCd) {
		this.orderBudgetTypeCd = orderBudgetTypeCd;
	}

	public String getOrderBudgetTypeCd() {
		return orderBudgetTypeCd;
	}
}
