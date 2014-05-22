package com.cleanwise.service.api.value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;



/**
 * <code>ShoppingCartData</code>
 */
public class ShoppingCartData extends ValueObject {

   private static final Logger log = Logger.getLogger(ShoppingCartData.class);

  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -5967031585222033385L;
    public String toString() {
       String s = "ShoppingCartData {  locale = "+ getLocaleCd() +	" _orderGuideId=" + _orderGuideId ;
       if ( null != _site ) {
          s+=", site id=" + _site.getSiteId();
       }
       if ( null != _shopCartItems ) {
    s+=", number of cart items=" + _shopCartItems.size() ;
       }
       s+= " }";
       return s;
    }

    private int _orderGuideId;
    public void setOrderGuideId(int v) { _orderGuideId = v; }
    public int getOrderGuideId() { return _orderGuideId; }

    //---------------------------------------------------------------------------
    UserData _user = null;
    ShoppingCartItemDataVector _shopCartItems = null;
    ShoppingCartServiceDataVector _shopCartServices=null;
    List _newItems = null;
    SiteData _site = null;
    String _sequenceNum = null;
    int _orderBy = Constants.ORDER_BY_CATEGORY;
    private List WarningMessages = null;
    OrderData _prevOrderData = null;
    ShoppingInfoDataVector customerComments;
    WorkOrderItemData _workOrderItem = null;
    private Map _prevItemsQuantityMap = null; // hold existing order item id and quantity map

    java.util.List _autoDistroShopInfoItems = null;
    java.util.List _autoDistroShopInfoItemsRemoved = null;
    private String userName2 = null;

    public OrderData getPrevOrderData() {
        return _prevOrderData;
    }
    public void setPrevOrderData(OrderData v) {
        _prevOrderData = v;
    }
    private BigDecimal _prevRushCharge;
    public void setPrevRushCharge(BigDecimal v) {
  _prevRushCharge = v;
    }
    public BigDecimal getPrevRushCharge() {
  return _prevRushCharge;
    }

    public void addWarningMessage(String pMsg)
    {
  if ( null == this.WarningMessages ) {
      this.WarningMessages = new ArrayList();
  }
  this.WarningMessages.add(pMsg);
    }

    public void addWarningMessages(List WarningMessages) {
    	if(WarningMessages!=null){

    		for(int i=0; i<WarningMessages.size(); i++){
    			//String m = (String) WarningMessages.get(i);

    			addWarningMessage(WarningMessages.get(i).toString());
    		}
    	}
    }
    public void setWarningMessages(List WarningMessages) {
        this.WarningMessages = WarningMessages;
    }

    public List getWarningMessages()
    {
        if (WarningMessages == null) {
            return new ArrayList();
        }
        List v = WarningMessages;
        return v;
    }

    //---------------------------------------------------------------
    public ShoppingCartData()    {    }

    public void setSite(SiteData pValue)
    {
        _site = pValue;
    }

    public SiteData getSite()
    {

        return _site;
    }

    public void setItems(ShoppingCartItemDataVector pValue){

        if (pValue == null)
            pValue = new ShoppingCartItemDataVector();

        if(pValue instanceof ShoppingCartItemDataVector){
            _shopCartItems = pValue;
        }else{
            _shopCartItems = new ShoppingCartItemDataVector();
            _shopCartItems.addAll(pValue);
        }

        int orderNum = 0;

        for (int ii = 0; ii < _shopCartItems.size(); ii++)
        {
            ShoppingCartItemData sciD = getItem(ii);
            sciD.setOrderNumber(++orderNum);
        }
    }

    public ShoppingCartItemData getItem(int idx)
    {
    	if (idx >= _shopCartItems.size()) {
    		return new ShoppingCartItemData();
    	}
    	else {
    		return (ShoppingCartItemData)_shopCartItems.get(idx);
    	}
    }

    public ShoppingCartItemDataVector getItems()
    {

        if (_shopCartItems == null)
        {
            _shopCartItems = new ShoppingCartItemDataVector();
        }
        if(!(_shopCartItems instanceof ShoppingCartItemDataVector)){
            ShoppingCartItemDataVector tempShopCartItems = new ShoppingCartItemDataVector();
            tempShopCartItems.addAll(_shopCartItems);
            _shopCartItems = tempShopCartItems;
        }

        return _shopCartItems;
    }
    public void setNewItems(List pValue)
    {
        _newItems = pValue;
    }
    public List getNewItems()
    {
        if (_newItems == null)
        {
            _newItems = new LinkedList();
        }
        return _newItems;
    }

    int maxNum = 0;

    public void addItem(ShoppingCartItemData pItem) {

        if (pItem == null) {
            return;
        }

        if (_newItems == null) {
            _newItems = new LinkedList();
        }

        boolean addItemFlag = addItem(this.getItems(), pItem);

        pItem.setOrderNumber(++maxNum);
        if (addItemFlag) {
            _newItems.add(pItem);
        }

    }

    public boolean addItem(ShoppingCartItemDataVector pItems, ShoppingCartItemData pNewItem) {

        ShoppingCartItemData item = findItem(pItems, pNewItem.getItemId());
        if (item != null) {

            int qty = pNewItem.getQuantity();
            int qty1 = item.getQuantity();

            int qtyNew = qty + qty1;

            item.setQuantity(qtyNew);
            item.setQuantityString("" + qtyNew);
            item.setInventoryQtyOnHand(pNewItem.getInventoryQtyOnHand());
            item.setInventoryQtyOnHandString(pNewItem.getInventoryQtyOnHandString());
            item.setInventoryQtyIsSet(pNewItem.getInventoryQtyIsSet());
            item.setInventoryParValue(pNewItem.getInventoryParValue());

            return false;
        }  else {
            pItems.add(pNewItem);
            return true;
        }

    }

    public void updateItemQuantity(ShoppingCartItemData pUpdItem)
    {

        if (pUpdItem == null) return;

        getItems();

        if (_newItems == null)
        {
            _newItems = new LinkedList();
        }


        String qtyS = pUpdItem.getQuantityString();
        if (qtyS != null && qtyS.trim().length() > 0 )
        {
            int qty = Integer.parseInt(qtyS);
            pUpdItem.setQuantity(qty);
        }

        int itemId = pUpdItem.getProduct().getProductId();

        boolean addItemFlag = true;

        for (int ii = 0; ii < _shopCartItems.size(); ii++)
        {
            ShoppingCartItemData sciD1 = (ShoppingCartItemData)
                _shopCartItems.get(ii);
            int itemId1 = sciD1.getProduct().getProductId();
            if (itemId == itemId1)
            {
                sciD1.setQuantity(pUpdItem.getQuantity());
                sciD1.setQuantityString(pUpdItem.getQuantityString());
                addItemFlag = false;
                break;
            }
        }

        if (addItemFlag)
        {
            pUpdItem.setOrderNumber(++maxNum);
            _shopCartItems.add(pUpdItem);
            _newItems.add(pUpdItem);
        }
    }

    public void calculateInvValue(ShoppingCartItemData pUpdItem) {

        if (pUpdItem == null) return;

        getItems();

        if (_newItems == null) {
            _newItems = new LinkedList();
        }

        String onHandValStr = pUpdItem.getInventoryQtyOnHandString();

        if (onHandValStr != null && onHandValStr.trim().length() > 0) {
            int onHandVal = Integer.parseInt(onHandValStr);
            pUpdItem.setInventoryQtyOnHand(onHandVal);
        }

        int itemId = pUpdItem.getProduct().getProductId();

        for (int ii = 0; ii < _shopCartItems.size(); ii++) {
            ShoppingCartItemData sciD1 = (ShoppingCartItemData) _shopCartItems.get(ii);
            int itemId1 = sciD1.getProduct().getProductId();
            if (itemId == itemId1) {
                if ((sciD1.getInventoryQtyIsSet() || pUpdItem.getInventoryQtyOnHand() > 0)) {
                    sciD1.setInventoryQtyOnHand(pUpdItem.getInventoryQtyOnHand());
                    sciD1.setInventoryQtyIsSet(true);
                    int recQty = pUpdItem.getInventoryParValue() - pUpdItem.getInventoryQtyOnHand();
                    if (recQty < 0) recQty = 0;
                    sciD1.setQuantity(recQty);
                    sciD1.setQuantityString(String.valueOf(recQty));
                }
                break;
            }
        }
    }

    public void addItems(List pValue)
    {

        if (pValue == null) return;

        for (int ii = 0; ii < pValue.size(); ii++)
        {
            ShoppingCartItemData sciD = (ShoppingCartItemData)pValue.get(ii);
            addItem(sciD);
        }
    }

    public void clearNewItems()
    {
        _newItems = new LinkedList();
    }

    public int getItemsQty()
    {

        getItems();

        int qty = _shopCartItems.size();

        return qty;
    }

    public int getItemsUom()
    {

        getItems();

        int qty = 0;

        for (int ii = 0; ii < _shopCartItems.size(); ii++)
        {

            ShoppingCartItemData sciD = (ShoppingCartItemData)
                _shopCartItems.get(ii);
            qty += sciD.getQuantity();
        }

        return qty;
    }





    public int getNewItemsQty()
    {

        if (_newItems == null)
        {
            _newItems = new LinkedList();
        }

        int qty = _newItems.size();

        return qty;
    }

    public int getNewItemsUom()
    {

        if (_newItems == null)
        {
            _newItems = new LinkedList();
        }

        int qty = 0;

        for (int ii = 0; ii < _newItems.size(); ii++)
        {
            ShoppingCartItemData sciD = (ShoppingCartItemData)_newItems.get(ii);
            qty += sciD.getQuantity();
        }

        return qty;
    }

    public double getNewItemsCost()
    {

        if (_newItems == null)
        {
            _newItems = new LinkedList();
        }

        BigDecimal cost = new BigDecimal(0);

        for (int ii = 0; ii < _newItems.size(); ii++)
        {

            ShoppingCartItemData sciD = (ShoppingCartItemData)_newItems.get(ii);
            BigDecimal priceBD = new BigDecimal(sciD.getPrice());
			priceBD = priceBD.multiply(new BigDecimal(sciD.getQuantity()));
            cost = cost.add(priceBD);
        }

        double costD = cost.doubleValue();

        return costD;
    }

    public void setUser(UserData pValue)
    {
        _user = pValue;
    }

    public UserData getUser()
    {
        return _user;
    }

    public String getLocaleCd() // read only
    {
        String localeCd = "";
        if (_site != null ) {
          ContractData contractD = _site.getContractData();
          if(contractD!=null) {
            localeCd = contractD.getLocaleCd();
          }
        }
        return localeCd;
    }

    public void setSequenceNum(String pValue)
    {
        _sequenceNum = pValue;
    }

    public String getSequenceNum()
    {

        return _sequenceNum;
    }

    public void setOrderBy(int pValue)
    {
        _orderBy = pValue;
    }

    public int getOrderBy()
    {

        return _orderBy;
    }

       static final Comparator<ShoppingCartItemData> SCART_CATCOMP = new Comparator<ShoppingCartItemData>() {
        public int compare(ShoppingCartItemData o1, ShoppingCartItemData o2) {
            String name1 = o1.getCategoryName() + " " + o1.getProduct().getCatalogProductShortDesc();
            String name2 = o2.getCategoryName() + " " + o2.getProduct().getCatalogProductShortDesc();
            return name1.compareTo(name2);
        }
    };

    static final Comparator SCART_NAMECOMP = new Comparator() {
            public int compare(Object o1, Object o2)
            {
                String name1 = ((ShoppingCartItemData)o1).getProduct().
                    getCatalogProductShortDesc();
                String name2 = ((ShoppingCartItemData)o2).getProduct().
                    getCatalogProductShortDesc();
                return name1.compareTo(name2);
            }
        };

    static final Comparator SCART_SKUCOMP = new Comparator() {
            public int compare(Object o1, Object o2)
            {
                String name1 = ((ShoppingCartItemData)o1).getActualSkuNum();
                String name2 = ((ShoppingCartItemData)o2).getActualSkuNum();
                if(name1==null){name1="";}
                if(name2==null){name2="";}
                return name1.compareTo(name2);
            }
        };

        static final Comparator SCOSTCENTER_CATCOMP = new Comparator() {
               public int compare(Object o1, Object o2)
               {
                   String name1 = ((ShoppingCartItemData)o1).getProduct().getCostCenterName()
                       + " " +
                       ((ShoppingCartItemData)o1).getProduct().getCatalogProductShortDesc();
                   String name2 = ((ShoppingCartItemData)o2).getProduct().getCostCenterName()
                       + " " +
                       ((ShoppingCartItemData)o2).getProduct().getCatalogProductShortDesc();
                   return name1.compareTo(name2);
               }
           };



    private static final Comparator<CatalogCategoryData> CATALOG_CATEGORY_SORT_ORDER_COMP = new Comparator<CatalogCategoryData>() {

        public int compare(CatalogCategoryData o1, CatalogCategoryData o2) {

            int sortOrder1 = o1.getSortOrder();
            int sortOrder2 = o2.getSortOrder();

            String name1 = o1.getCatalogCategoryShortDesc();
            String name2 = o2.getCatalogCategoryShortDesc();

            int comp;
            if (sortOrder1 != sortOrder2) {
                comp = (sortOrder1 > sortOrder2) ? 1 : -1;
            } else {
                comp = name1.compareToIgnoreCase(name2);
            }

            return comp;
        }
    };

    private static final Comparator<ProductData> PRODUCT_CATEGORY_SORT_ORDER_COMP = new Comparator<ProductData>() {

        public int compare(ProductData o1, ProductData o2) {

            ArrayList<CatalogCategoryData> path1 = new ArrayList<CatalogCategoryData>();
            CatalogCategoryDataVector ccDV1 = o1.getCatalogCategories();
            if (ccDV1 != null && ccDV1.size() > 0) {
                for (int cci = ccDV1.size() - 1; cci >= 0; cci--) {
                    CatalogCategoryData catCategData = ((CatalogCategoryData) ccDV1.get(cci));
                    path1.add(catCategData);
                }
            }

            ArrayList<CatalogCategoryData> path2 = new ArrayList<CatalogCategoryData>();
            CatalogCategoryDataVector ccDV2 = o2.getCatalogCategories();
            if (ccDV2 != null && ccDV2.size() > 0) {
                for (int cci = ccDV2.size() - 1; cci >= 0; cci--) {
                    CatalogCategoryData catCategData = ((CatalogCategoryData) ccDV2.get(cci));
                    path2.add(catCategData);
                }
            }

            if (path1.size() > path2.size()) {
                for (int i = 0; i < path2.size(); i++) {
                    int comp = CATALOG_CATEGORY_SORT_ORDER_COMP.compare(path1.get(i), path2.get(i));
                    if (comp != 0) {
                        return comp;
                    }
                }
            } else {
                for (int i = 0; i < path1.size(); i++) {
                    int comp = CATALOG_CATEGORY_SORT_ORDER_COMP.compare(path1.get(i), path2.get(i));
                    if (comp != 0) {
                        return comp;
                    }
                }
            }

            if (path1.size() > path2.size()) {
                //log.info("compare()=> path1.size() < path2.size(): " + path1.size() + " < " + path2.size() + ", comp: 1");
                return 1;
            } else if (path1.size() == path2.size()) {
                return  o1.getCatalogProductShortDesc().compareTo(o2.getCatalogProductShortDesc());
            } else {
                return -1;
            }
        }
    };

    private static final Comparator<ShoppingCartItemData> SCART_CATEGORY_SORT_ORDER_COMP = new Comparator<ShoppingCartItemData>() {
        public int compare(ShoppingCartItemData o1, ShoppingCartItemData o2) {
            int comp = PRODUCT_CATEGORY_SORT_ORDER_COMP.compare(o1.getProduct(), o2.getProduct());
            if (comp == 0) {
                comp = SCART_CATCOMP.compare(o1, o2);
            }
            return comp;
        }
    };

    private static final Comparator<ShoppingCartItemData> SCART_CUSTOM_CATEGORY_SORT_ORDER_COMP = new Comparator<ShoppingCartItemData>() {
        public int compare(ShoppingCartItemData o1, ShoppingCartItemData o2) {
            int comp = CUSTOM_CATEGORY_SORT_ORDER_COMP.compare(o1, o2);
            if (comp == 0) {
                comp = SCART_CATEGORY_SORT_ORDER_COMP.compare(o1, o2);
            }
            return comp;
        }
    };

    private static final Comparator<ShoppingCartItemData> CUSTOM_CATEGORY_SORT_ORDER_COMP = new Comparator<ShoppingCartItemData>() {
        public int compare(ShoppingCartItemData o1, ShoppingCartItemData o2) {

            int sortOrder1 = o1.getCustomSortOrder();
            int sortOrder2 = o2.getCustomSortOrder();

            String name1 = o1.getCustomCategoryName();
            String name2 = o2.getCustomCategoryName();

            int comp;
            if (sortOrder1 != sortOrder2) {
                comp = (sortOrder1 > sortOrder2) ? 1 : -1;
            } else {
                comp = name1.compareToIgnoreCase(name2);
            }

            return comp;
        }
    };

    // Shopping cart sort functions.
    public void orderByCategory() {
        if ( _shopCartItems == null ) return;
        orderByCategory(_shopCartItems);
    }
    public void orderBySku() {
        if ( _shopCartItems == null ) return;
        Collections.sort(_shopCartItems, SCART_SKUCOMP);
    }
    public void orderByName() {
        if ( _shopCartItems == null ) return;
        Collections.sort(_shopCartItems, SCART_NAMECOMP);
    }
    public void orderByCostCenter() {
        if ( _shopCartItems == null ) return;
        Collections.sort(_shopCartItems, SCOSTCENTER_CATCOMP);
    }

    public static void orderByCategory(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCART_CATEGORY_SORT_ORDER_COMP);
    }

    public static void orderByCustomCategory(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCART_CUSTOM_CATEGORY_SORT_ORDER_COMP);
    }

    public static void orderBySku(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCART_SKUCOMP);
    }
    public static void orderByName(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCART_NAMECOMP);
    }
    public static void orderByCostCenter(List l) {
        if ( l == null ) return;
        Collections.sort(l, SCOSTCENTER_CATCOMP);
    }

    int _contractId = 0, _catalogId = 0;
    public int getContractId() {        return _contractId;    }
    public void setContractId(int v) {        _contractId = v;    }
    public int getCatalogId() {        return _catalogId;    }
    public void setCatalogId(int v) {        _catalogId = v;    }
    String _storeType = "";
    public String getStoreType() { return _storeType; }
    public void setStoreType(String v) { _storeType = v; }

    public ShoppingCartItemData findItem(long pItemId) {
        return findItem(this.getItems(), pItemId);
    }

    public ShoppingCartItemData findItem(ShoppingCartItemDataVector pItems, long pItemId) {
        if (pItems != null && !pItems.isEmpty()) {
            for (Object oItem : pItems) {
                ShoppingCartItemData sciD1 = (ShoppingCartItemData) oItem;
                int itemId1 = sciD1.getProduct().getProductId();
                if (pItemId == itemId1) {
                    return sciD1;
                }
            }
        }
        return null;
    }

    public ShoppingCartItemData findItemBySku(String pSku)
    {
        boolean mfgsku = false;
        return findItemBySku(pSku, mfgsku);
    }
    public ShoppingCartItemData findItemByMfgSku(String pSku)
    {
        boolean mfgsku = true;
        return findItemBySku(pSku, mfgsku);
    }

    public ShoppingCartItemData findItemBySku(String pSku, boolean pMfgSku)
    {

        if (pSku == null || pSku.length() == 0 || _shopCartItems == null )
        {
            return null;
        }

        for (int ii = 0; ii < _shopCartItems.size(); ii++)
        {
            ShoppingCartItemData sciD = (ShoppingCartItemData) _shopCartItems.get(ii);
            if (pMfgSku)
            {
                if (pSku.equals(sciD.getProduct().getManufacturerSku())) {
                    return sciD;
                }
            }
            else
            {
                if (pSku.equals(sciD.getActualSkuNum())) {
                    return sciD;
                }
            }
        }
        return null;
    }

    public static String
        CART_ITEM_UPDATE = "CART_ITEM_UPDATE",
        CART_PO_NUM = "CART_PO_NUM",
        CART_COMMENTS = "CART_COMMENTS",
        CUSTOMER_CART_COMMENTS = "CUSTOMER_CART_COMMENTS",
        ORDER_BUDGET_TYPE_CD = "ORDER_BUDGET_TYPE_CD",
        AUTO_DISTRO_CART_ITEM_UPDATE = "AUTO_DISTRO_CART_ITEM_UPDATE";

    java.util.List mShoppingInfoVector = null,
        mShoppingItemsRemoved = null;

    public void setShoppingInfo(java.util.List v) {
        mShoppingInfoVector = v;
		_autoDistroShopInfoItems = new ShoppingInfoDataVector();
		_autoDistroShopInfoItemsRemoved = new ShoppingInfoDataVector();
        Iterator it = mShoppingInfoVector.iterator();
        customerComments = new ShoppingInfoDataVector();
        while (it.hasNext()) {
            ShoppingInfoData sid = (ShoppingInfoData) it.next();
            if (ShoppingCartData.CUSTOMER_CART_COMMENTS.equals(sid.getShortDesc())) {
                customerComments.add(sid);
                it.remove();
            }
			else if (ShoppingCartData.AUTO_DISTRO_CART_ITEM_UPDATE.equals(sid.getShortDesc())) {
                _autoDistroShopInfoItems.add(sid);
                it.remove();
            }
        }
        Collections.sort(customerComments,SHOPPING_INFO_DATA_ADD_DATE);
        mShoppingItemsRemoved = new ArrayList();

        // Initialize the history for each item.
        List scartItems = this.getItems();
        for (int idx = 0; scartItems != null &&
                 idx < scartItems.size(); idx++ )
        {
            ShoppingCartItemData scid = this.getItem(idx);
            scid.setShoppingCartHistory(mShoppingInfoVector);
			scid.setAutoDistroShoppingCartHistory(_autoDistroShopInfoItems);
        }

        // separate those items removed.
        for ( int idx2 = 0; idx2 < v.size(); idx2++ )
        {
            ShoppingInfoData sid = (ShoppingInfoData)v.get(idx2);
            if(sid.getShortDesc().equals(CART_ITEM_UPDATE) &&
               itemIsInList(sid.getItemId(), scartItems) == false )
            {
                mShoppingItemsRemoved.add(sid);
            }
			if (sid.getShortDesc().equals(AUTO_DISTRO_CART_ITEM_UPDATE) && 
				itemIsInList(sid.getItemId(), scartItems) == false )
            {
                _autoDistroShopInfoItemsRemoved.add(sid);
            }
        }
    }

    private boolean itemIsInList(int pItemId , List cartList)
    {
        if (cartList == null) return false;

        for ( int idx = 0; idx <  cartList.size(); idx++ ) {
            ShoppingCartItemData sid =
                (ShoppingCartItemData)cartList.get(idx);
            if ( sid.getProduct().getItemData().getItemId() == pItemId )
            {
                return true;
            }
        }

        return false;
    }

    public ItemChangesEntry mkItemChangesEntry(ShoppingInfoData v)
    {
        return new ItemChangesEntry(v);
    }

    public ShoppingCartItemDataVector getInventoryItemsOnly() {

        if (_shopCartItems == null) {
            _shopCartItems = new ShoppingCartItemDataVector();
        }

        ShoppingCartItemDataVector tempShopCartItems = new ShoppingCartItemDataVector();
        tempShopCartItems.addAll(_shopCartItems);
        Iterator it = tempShopCartItems.iterator();
        while (it.hasNext()) {
            ShoppingCartItemData item = (ShoppingCartItemData) it.next();
            if (!item.getIsaInventoryItem()) {
                it.remove();
            }
        }
        return tempShopCartItems;
    }


   public ShoppingCartItemDataVector getRegularItemsOnly() {

        if (_shopCartItems == null) {
            _shopCartItems = new ShoppingCartItemDataVector();
        }

        ShoppingCartItemDataVector tempShopCartItems = new ShoppingCartItemDataVector();
        tempShopCartItems.addAll(_shopCartItems);
        Iterator it = tempShopCartItems.iterator();
        while (it.hasNext()) {
            ShoppingCartItemData item = (ShoppingCartItemData) it.next();
            if (item.getIsaInventoryItem()) {
                it.remove();
            }
        }
        return tempShopCartItems;
    }


    public class ItemChangesEntry
        extends ValueObject
    {
        private ShoppingInfoData mSid;
        private String mSku, mProductDesc;

        public ItemChangesEntry(ShoppingInfoData sid) {
            mSid = sid;
            mSku = "";
            mProductDesc = "";
        }

        public void setShoppingInfoData(ShoppingInfoData v)
        { mSid = v;        }
        public ShoppingInfoData getShoppingInfoData()
        { return mSid;        }

        public void setSku(String v)
        { mSku = v;        }
        public String getSku()
        { return mSku;        }

        public void setProductDesc(String v)
        { mProductDesc = v;        }
        public String getProductDesc()
        { return mProductDesc;        }
    };

    List mRemovedProductInfo = null;
    public void setRemovedProductInfo(List productInfoList)
    {
        mRemovedProductInfo = productInfoList;
    }
    public List getRemovedProductInfo()
    {
        if (null == mRemovedProductInfo) {
            mRemovedProductInfo = new ArrayList();
        }
        return mRemovedProductInfo;
    }

    public void addRemovedProductInfo(List productInfoList) {
        if (null == mRemovedProductInfo) {
            mRemovedProductInfo = new ArrayList();
        }
        if (null == productInfoList) {
            productInfoList = new ArrayList();
        }
        mRemovedProductInfo.addAll(productInfoList);
    }

    public List getItemsRemovedHistory()
    {return mShoppingItemsRemoved;}

    public String getSavedPoNumber() {
        ShoppingInfoData sid =  getHistoryValue(CART_PO_NUM);
        if (sid != null)
        {
            return sid.getValue();
        }
        return "";
    }
    public String getSavedComments() {
        ShoppingInfoData sid = getHistoryValue(CART_COMMENTS);
        if (sid != null)
        {
            return sid.getValue();
        }
        return "";
    }

    public ShoppingInfoData getHistoryValue(String pValShortDesc)
    {
        for ( int i = 0; mShoppingInfoVector != null && i < mShoppingInfoVector.size(); i++ ){
            ShoppingInfoData sid = (ShoppingInfoData) mShoppingInfoVector.get(i);
            if ( pValShortDesc.equals(sid.getShortDesc()) ){
                return sid;
            }
        }
        return null;
    }

    public List getItemHistory()
    {   
        if(mShoppingInfoVector==null) {
            mShoppingInfoVector = new ShoppingInfoDataVector();
        }
        return mShoppingInfoVector;
    }

    public ShoppingInfoDataVector getItemHistoryValue(int pItemId)
    {
        ShoppingInfoDataVector itemHistory =
            new ShoppingInfoDataVector();

        for ( int i = 0; mShoppingInfoVector != null
                  && i < mShoppingInfoVector.size(); i++ )
        {
            ShoppingInfoData sid = (ShoppingInfoData)
                mShoppingInfoVector.get(i);
            if ( pItemId == sid.getItemId())
            {
                itemHistory.add(sid);
            }
        }
        return itemHistory;
    }

    private Date mModDate;
    public Date getModDate() { return mModDate; }
    public void setModDate(Date v) { mModDate = v; }
    private String mModBy;
    public String getModBy() { return mModBy; }
    public void setModBy(String v) { mModBy = v; }

    public void setInventoryOrderQty(ArrayList v )
    {
        List scartItems = this.getItems();
        for ( int invidx = 0; v != null && invidx < v.size(); invidx++ )
        {
            SiteInventoryInfoView invItem =
            (SiteInventoryInfoView)v.get(invidx);
            for (int idx = 0; scartItems != null &&
            idx < scartItems.size(); idx++ )
            {
                ShoppingCartItemData scid = this.getItem(idx);
                if (scid.getInventoryParValue() <= 0) continue;
                if ( invItem.getItemId() == scid.getProduct().getItemData().getItemId()) {
                    /*
                    String onhstr = invItem.getQtyOnHand();
                    int newqty = 0;
                    if(onhstr==null || onhstr.trim().length()==0) {
                      newqty = scid.getAutoOrderQty();
                    } else {
                      onhstr = onhstr.trim();
                      newqty = scid.getInventoryParValue() - Integer.parseInt(onhstr);
                    }
                    if (newqty < 0) newqty = 0;
                     */
                    int newqty = scid.getInventoryOrderQty();
                    scid.setQuantity(newqty);
                    scid.setQuantityString(String.valueOf(newqty));
                }
            }
        }
    }

    /** Getter for property customerComments.  These are the comments that a user would save with
     *their cart.  Much like order notes except they follow a shopping cart and are viewable by the
     *end user customer shopping on the site.
     */
    public ShoppingInfoDataVector getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(ShoppingInfoDataVector customerComments) {
        this.customerComments = customerComments;
    }

    private static final Comparator
  SHOPPING_INFO_DATA_ADD_DATE =
  new Comparator() 	{
      public int compare(Object o1, Object o2)
      {
    Date d1 = ((ShoppingInfoData)o1).getAddDate();
    Date d2 = ((ShoppingInfoData)o2).getAddDate();
    return d1.compareTo(d2);
      }
  };

  public ShoppingCartData reviseQuantities() {


    java.util.Hashtable ctrlv = _site.getShoppingControlsMap();

    if (null == ctrlv || ctrlv.size() <= 0) {
      return this;
    }
    List cartItems = getItems();
    for (int j = 0; j < cartItems.size(); j++) {
      ShoppingCartItemData sciD = getItem(j);
      Integer k = new Integer(sciD.getItemId());
      ShoppingControlData ctrl = (ShoppingControlData)ctrlv.get(k);

      if(ctrl == null){
    	  continue;
      }

      int reqQty = sciD.getQuantity();
      int maxQtyAllowed = sciD.getMaxOrderQty();

      int restrictDays = ctrl.getRestrictionDays();
      if(!(restrictDays < 0)){
    	  maxQtyAllowed = ctrl.getActualMaxQty();
      }
      if (maxQtyAllowed >= 0 && _prevItemsQuantityMap != null){
    	  Integer preQuantity = (Integer)_prevItemsQuantityMap.get(k);
    	  if (preQuantity != null)
    		  maxQtyAllowed += preQuantity;
      }

      if (!(maxQtyAllowed < 0) && (reqQty > maxQtyAllowed)) {
    	  sciD.setQuantity(maxQtyAllowed);
    	  sciD.setQuantityString(String.valueOf(maxQtyAllowed));
    	  String m = "";

    	  CartItemInfo cii = new CartItemInfo(sciD, ctrl);
    	  String messKey = null;
    	  Object[] messParams = null;
    	  if (maxQtyAllowed > 0 || restrictDays > 0) {

    		  if(restrictDays > 0){

    			  m += "Maximum order quantity exceeded. The request quantity of "
        			  + reqQty + " has been adjusted to " + maxQtyAllowed + ". " +
        			  ctrl.getMaxOrderQty()+" orderable every "+restrictDays+" day(s).";
        		  messKey = "shoppingMessages.text.maxOrderQuantityExceededRestrictionDays";
        		  messParams = new Object[6];
        		  messParams[0] = new Integer(reqQty);
        		  messParams[1] = new Integer(maxQtyAllowed);
        		  messParams[2] = new Integer(ctrl.getMaxOrderQty());
        		  messParams[3] = new Integer(restrictDays);
        		  messParams[4] = sciD.getActualSkuNum();
        		  messParams[5] = sciD.getProduct().getUom();

    		  }else{
    		  m += "Maximum order quantity exceeded. The request quantity of "
    			  + reqQty + " has been adjusted to " + maxQtyAllowed + ".";
    		  messKey = "shoppingMessages.text.maxOrderQuantityExceeded";
    		  messParams = new Object[5];
    		  messParams[0] = new Integer(reqQty);
    		  messParams[1] = new Integer(maxQtyAllowed);
    		  messParams[2] = new Integer(ctrl.getMaxOrderQty());
    		  messParams[3] = sciD.getActualSkuNum();
    		  messParams[4] = sciD.getProduct().getUom();
    		  }
    	  } else {
    		  messKey = "shoppingMessages.text.itemHasBeenBlocked";
    	  }

    	  cii.setMessage(m);
    	  cii.setI18nItemMessage(messKey, messParams);
    	  this.addItemMessage(cii);
      }

    }
    return this;
  }


    public class CartItemInfo extends ValueObject    {

  public  CartItemInfo( ShoppingCartItemData pShoppingCartItemData,
            ShoppingControlData pShoppingControlData )
  {
      mCartItemData = pShoppingCartItemData;
      mItemControlData = pShoppingControlData;
  }

  public ShoppingCartItemData mCartItemData;
  public ShoppingControlData  mItemControlData;
  private ArrayList I18nItemMessage = null;
  public void setI18nItemMessage(String val, Object[] params) {
    I18nItemMessage = convertToVector(val,params);
  }
  public String getI18nItemMessage(HttpServletRequest request) {
    return getI18nStr(request,I18nItemMessage);
  }
  public ArrayList getI18nItemMessageAL() {
    return I18nItemMessage;
  }

  public String mItemMessage;
  public void setMessage(String m) {
      mItemMessage = m;
  }

  public String toString() {
      String t = "";
      if ( null != mCartItemData ) {
    t += mCartItemData.description();
      }
      if ( null != mItemMessage ) {
    t += mItemMessage;
      }
      return t;
  }
    }

    private ArrayList mItemMessages;
    public void addItemMessage(CartItemInfo v)    {
       getItemMessages().add(v);
    }

    public List getItemMessages() {

  if ( null == this.mItemMessages ) {
      this.mItemMessages = new ArrayList();
  }
  return this.mItemMessages;
    }

    public void setItemMessages(List v) {
  if ( null == v ) {
      this.mItemMessages = new ArrayList();
  } else {
      this.mItemMessages = new ArrayList(v);
  }
    }

    public void addItemMessages(List v) {

        if (this.mItemMessages == null) {
            this.mItemMessages = new ArrayList();
        }
        if (null == v) {
            this.mItemMessages = new ArrayList();
        } else {
            this.mItemMessages.addAll(v);
        }
    }


    /**
     * Getter for property orderBudgetTypeCd.
     * @return Value of property orderBudgetTypeCd.
     */
    public String getOrderBudgetTypeCd() {
        ShoppingInfoData sid =  getHistoryValue(ORDER_BUDGET_TYPE_CD);
        if (sid != null){
            return sid.getValue();
        }
        return null;
    }

    /**
     * Setter for property orderBudgetTypeCd.
     * @param orderBudgetTypeCd New value of property orderBudgetTypeCd.
     */
    public void setOrderBudgetTypeCd(String orderBudgetTypeCd) {
        ShoppingInfoData sid =  getHistoryValue(ORDER_BUDGET_TYPE_CD);
        if(sid == null){
            sid = ShoppingInfoData.createValue();
            sid.setSiteId(getSite().getSiteId());
            sid.setShortDesc(ShoppingCartData.ORDER_BUDGET_TYPE_CD);
            sid.setAddBy(getUser().getUserName());
            sid.setModBy(getUser().getUserName());
            if(mShoppingInfoVector == null){
                mShoppingInfoVector = new ShoppingInfoDataVector();
            }
            this.mShoppingInfoVector.add(sid);
        }
        sid.setValue(orderBudgetTypeCd);
    }

    private boolean mIsPersistent = true;
    public void setIsPersistent(boolean pVal ) {
  mIsPersistent = pVal;
    }
    public boolean getIsPersistent() {
  return mIsPersistent;
    }

     /**
     *Returns the total cost of the shopping cart items (i.e. subtotal).
     */
    public int getTotalItemQuantity(){
        return getItems().getItemsQuantity();
    }

     /**
     *Returns the total cost of the shopping cart items (i.e. subtotal).
     */
    public double getItemsCost(){
        return getItems().getItemsCost();
    }

    /**
     *Returns the total cost of the shopping cart items (i.e. subtotal).
     */
    public double getItemsCostNonResale(){
        return getItems().getItemsCostNonResale();
    }

    /**
     *Returns true if all of the items are resale items
     */
    public boolean isAllResaleItems(){
        return getItems().isAllResaleItems();
    }

  private ArrayList convertToVector(String val, Object[] params) {
    ArrayList al = new ArrayList();
    al.add(val);
    if(params!=null ) {
      for(int ii=0; ii<params.length; ii++) {
        al.add(params[ii]);
      }
    }
    return al;
  }
  private String getI18nStr(HttpServletRequest request, ArrayList al) {
    if(al==null || al.isEmpty()) {
      return "";
    }
    String key = (String) al.get(0);
    Object[]  params = null;
    if(al.size()>1) {
      params = new Object[al.size()-1];
      for(int ii=0; ii<params.length; ii++) {
        params[ii] = al.get(ii+1);
      }
    }
    return ClwI18nUtil.getMessage(request,key,params);
  }

   public ShoppingCartServiceDataVector getServices() {
        if (_shopCartServices == null) {
            _shopCartServices = new ShoppingCartServiceDataVector();
        }
        if (_shopCartServices instanceof ShoppingCartServiceDataVector) {
            ShoppingCartServiceDataVector tempShopCartServices = new ShoppingCartServiceDataVector();
            tempShopCartServices.addAll(_shopCartServices);
            _shopCartServices = tempShopCartServices;
        }
     return _shopCartServices;
    }

    public void setServices(ShoppingCartServiceDataVector services) {
        this._shopCartServices = services;
    }

    public ShoppingCartServiceData findService(long pItemId)
    {
        if (_shopCartServices == null)
        {
            return null;
        }

        for (int ii = 0; ii < _shopCartServices.size(); ii++)
        {
            ShoppingCartServiceData sciD1 = (ShoppingCartServiceData)
                _shopCartServices.get(ii);
            int itemId1 = sciD1.getService().getItemData().getItemId();
            if (pItemId == itemId1)
            {
                return sciD1;
            }
        }
        return null;
    }

    public void addServices(ShoppingCartServiceDataVector pValue) {

        if (pValue == null) return;

        for (int ii = 0; ii < pValue.size(); ii++) {
            ShoppingCartServiceData scsD = (ShoppingCartServiceData) pValue.get(ii);
            addService(scsD);
        }
    }

    private void addService(ShoppingCartServiceData pCartService) {
        if(_shopCartServices==null)
            _shopCartServices=new  ShoppingCartServiceDataVector();
        _shopCartServices.addService(pCartService);
    }

    public void setWorkOrderItem (WorkOrderItemData workOrderItem) {
        _workOrderItem = workOrderItem;
    }

    public WorkOrderItemData getWorkOrderItem () {
        return _workOrderItem;
    }
	public void setPrevItemsQuantityMap(Map prevItemsQuantityMap) {
		this._prevItemsQuantityMap = prevItemsQuantityMap;
	}
	public Map getPrevItemsQuantityMap() {
		return _prevItemsQuantityMap;
	}

    public List getAutoDistroItemsHistory() {   
        if (_autoDistroShopInfoItems == null) {
            _autoDistroShopInfoItems = new ShoppingInfoDataVector();
        }
        return _autoDistroShopInfoItems;
    }

    public List getAutoDistroItemsHistoryRemoved() {
        if (_autoDistroShopInfoItemsRemoved == null) {
            _autoDistroShopInfoItemsRemoved = new ShoppingInfoDataVector();
        }
        return _autoDistroShopInfoItemsRemoved;
    }

    public ShoppingInfoDataVector getAutoDistroItemHistoryValue(int itemId) {
        ShoppingInfoDataVector itemHistory = new ShoppingInfoDataVector();
        if (_autoDistroShopInfoItems == null) {
            return itemHistory;
        }
        for (int i = 0; i < _autoDistroShopInfoItems.size(); i++) {
            ShoppingInfoData shopInfoData = (ShoppingInfoData)_autoDistroShopInfoItems.get(i);
            if (itemId == shopInfoData.getItemId()) {
                itemHistory.add(shopInfoData);
            }
        }
        return itemHistory;
    }
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}
	public String getUserName2() {
		return userName2;
	}
}
