package com.cleanwise.service.api.session;

/**
 * Title:        ShoppingServices
 * Description:  Remote Interface for ShoppingServices Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with the shopping cart.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cleanwise.service.api.dto.CostCenterCartData;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.ProductBundle;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemPriceRuleData;
import com.cleanwise.service.api.value.ItemPriceRuleDataVector;
import com.cleanwise.service.api.value.JanitorClosetItemData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.PriceRuleDescView;
import com.cleanwise.service.api.value.PriceRuleDescViewVector;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ServiceDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingCartServiceDataVector;
import com.cleanwise.service.api.value.ShoppingInfoDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;

public interface ShoppingServices extends javax.ejb.EJBObject {

  /**
   * Adds the item information values into shopping cart.
   * @param pCart  the shopping cart data.
   * @param pItemId  the item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void addItem(ShoppingCartData pCart, int pItemId) throws RemoteException;

  /**
   * buildss the janitor closet item information values.
   * @param pJanitorClosetId  the janitor closet identifier.
   * @param pBusEntityId  the customer identifier.
   * @param pItemId  the item identifier.
   * @return JanitorClosetItemData
   * @throws            RemoteException Required by EJB 1.0
   */
  public JanitorClosetItemData buildJanitorClosetItem(int pJanitorClosetId,
    int pBusEntityId, int pItemId) throws RemoteException;

  /**
   * Calculates the shopping cart total values.
   * @param pCart  the shopping cart data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void calculateCartTotal(ShoppingCartData pCart) throws RemoteException;

  /**
   * Clears the shopping cart.
   * @param pCart  the shopping cart data.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void clearCart(ShoppingCartData pCart) throws RemoteException;

  /**
   * Gets the item price rule vector information.
   * @param pItemId  the item identifier.
   * @return ItemPriceRuleDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ItemPriceRuleDataVector getItemPriceRulesCollection(int pItemId) throws RemoteException;

  /**
   * Gets the item price rule information.
   * @param pItemId  the item identifier.
   * @param pPriceRuleId  the price rule identifier.
   * @return ItemPriceRuleData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ItemPriceRuleData getItemPriceRule(int pItemId,
                                            int pPriceRuleId) throws RemoteException;

  /**
   * Removes the item information values from the shopping cart.
   * @param pCart  the shopping cart data.
   * @param pItemId  the item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void removeItem(ShoppingCartData pCart, int pItemId) throws RemoteException;

  /**
   * Calculates the shopping cart locale total values.
   * @param pCart  the shopping cart data.
   * @param pLocaleCd the locale code.
   * @param pCurrencyCd the currency code.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void calculateCartLocaleTotal(ShoppingCartData pCart,
                                       String pLocaleCd, String pCurrencyCd) throws RemoteException;

  /**
   * Saves the shopping cart .
   * @param pCart  the shopping cart data.
   * @param pRequestShortDesc  the request short description.
   * @param pRefRequestNum  the ref request number.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void saveCart(ShoppingCartData pCart,
                       String pRequestShortDesc, String pRefRequestNum) throws RemoteException;

    /**
     * Picks up catalog item ids, which match to condition. The result is ordered by short description
     *
     * @param pPattern             the substring of item name
     * @param pMatchType           six different types of matching could be applied (see SearchCreteria)
     * @param pShoppingItemRequest specific user criteria
     * @return collection of item ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector searchCustormerItemsByName( String pPattern,
                                               int pMatchType,
                                               ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    /**
     * Picks up catalog item ids, which match to condition. The result is ordered by short description
     *
     * @param pPattern             the substring of item name
     * @param pMatchType           six different types of matching could be applied (see SearchCreteria)
     * @param pShoppingItemRequest specific user criteria
     * @return collection of item ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector searchCustormerItemsBySkuNum(String pPattern,
                                                 int pMatchType,
                                                 ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    /**
     * Picks up all catalog item ids
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pSortBy              requested item sorting (possible values are in Constants class)
     * @param pShoppingItemRequest shopping user right
     * @return collection of item ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector getAllCatalogItems(String pStoreTypeCd, ShoppingItemRequest pShoppingItemRequest,  int pSortBy) throws RemoteException;

    /**
     * Prepares collection of ShoppingCartItemDataVector. Assigns contract price if exists, othewise assigns list price.
     * If product blongs to more than one category, takes the first one
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pSiteData            site data
     * @param pAccountCaralogId    the catalog identificator
     * @param pShoppingCatalogId   the catalog identificator
     * @param pContractId          the contract identificator or 0 if doesn't appliy
     * @param pSkuNums             the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getShoppingItemsBySku(String pStoreTypeCd,
                                                            SiteData pSiteData,
                                                            List pSkuNums,
                                                            ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException;
    public ShoppingCartItemDataVector getShoppingItemsBySku(String pStoreTypeCd,
                                                            SiteData pSiteData,
                                                            List pSkuNums,
                                                            ShoppingItemRequest pShoppingItemRequest,
                                                            AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException;

    /**
     * Prepares collection of ShoppingCartItemDataVector. Assigns contract price if exists, othewise assigns list price.
     * If product blongs to more than one category, takes the first one
     *
     * @param pSiteData            site data
     * @param pSkuNums             the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getShoppingItemsByMfgSku(SiteData pSiteData,
                                                               List pSkuNums,
                                                               ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException;
    public ShoppingCartItemDataVector getShoppingItemsByMfgSku(SiteData pSiteData,
                                                               List pSkuNums,
                                                               ShoppingItemRequest pShoppingItemRequest,
                                                               AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException;

  /**
   * Prepares collection of ShoppingCartItemDataVector. Assigns contract price if exists, othewise assigns list price.
   * If product blongs to more than one category, takes the first one
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pContractId the contract identificator or 0 if doesn't appliy
   * @param pItems the list of item is or the list of product object
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ShoppingCartItemDataVector prepareShoppingItems(String pStoreTypeCd, SiteData pSiteData, int pCatalogId,
    int pContractId, List pItems) throws RemoteException;
  public ShoppingCartItemDataVector prepareShoppingItems(String pStoreTypeCd, SiteData pSiteData, int pCatalogId,
    int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

	public List<Integer> populateMSDSInformation(ShoppingCartItemDataVector shoppingCartItems, String pUserLocale, 
			  String pCountryCode, String pStoreLocale) throws SQLException, RemoteException;

    /**
     * Prepares array of category ids for the contract
     *
     * @param pShoppingItemRequest criteria
     * @return int array sorted in increasing order
     * @throws RemoteException if an errors
     */
    public int[] getShoppingCategoryIds(ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    /**
     * Prepares array of item ids for the contract
     *
     * @param pShoppingItemRequest criteria
     * @return int array sorted in increasing order
     * @throws RemoteException if an errors
     */
    public int[] getShoppingItemIds(ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    /**
     * Picks up all catalog item manufacturers
     *
     * @param pShoppingItemRequest the specific criteria
     * @return collection of BusEntityData objects manufacturer type
     * @throws RemoteException if an errors
     */
    public BusEntityDataVector getCatalogManufacturers(ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    /******************************************************************************/
    /**
     * Picks up all catalog item categorie
     *
     * @param pShoppingItemRequest the specific criteria
     * @return collection of ItemData objects
     * @throws RemoteException if an errors
     */
    public ItemDataVector getCatalogCategories(ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

      /**
     * Picks up all catalog items, which match criteria. Returns empty collection if all filter parameters are empty.
     * Ignores filter parameter if it is empty. Applies contains ignore case match type
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pCustSku             - custormer sku number filter. Applies catalog sku number if exsits, otherwise applies item sku number
     * @param pMfgSku              - manufacturer sku number filter
     * @param pName                - item short description filter. Applies catalog short description if exsits, otherwise applies item short description
     * @param pDesc                - item long description filter
     * @param pCategory            - item lowest level category filter
     * @param pSize                - item size property filter
     * @param pMfgId               - manufacturer Id filter. Unlike other fiters it demands exact equal match type
     * @param pSortBy              - requested sorting
     * @param pGreenCertifiedFlag  item certified filter
     * @param pUPCNum              - upc num
     * @param pShoppingItemRequest -  soecific criteria
     * @return collection of item ids sorted by short description
     * @throws RemoteException if an errors
     */
    public IdVector searchShoppingItems(String pStoreTypeCd,
                                        String pCustSku,
                                        String pMfgSku,
                                        String pName,
                                        String pDesc,
                                        String pCategory,
                                        String pSize,
                                        int pMfgId,
                                        int pSortBy,
                                        boolean pGreenCertifiedFlag,
                                        String pUPCNum,
                                        ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    /**
     * Picks up all catalog items, which match criteria. Returns empty collection if all filter parameters are empty.
     * Ignores filter parameter if it is empty. Applies contains ignore case match type
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pAccountCatalogId    the account catalog identificator
     * @param pCatalogId           the shopping catalog identificator
     * @param pContractId          the contract identificator or 0 if doesn't appliy
     * @param pCustSku             - custormer sku number filter. Applies catalog sku number if exsits, otherwise applies item sku number
     * @param pMfgSku              - manufacturer sku number filter
     * @param pName                - item short description filter. Applies catalog short description if exsits, otherwise applies item short description
     * @param pDesc                - item long description filter
     * @param pCategoryId          - item lowest level category id filter
     * @param pDocType             - document type (MSDS, SPEC, DED)
     * @param pMfgId               - manufacturer Id filter. Unlike other fiters it demands exact equal match type
     * @param pSortBy              - requested sorting
     * @param pShoppingItemRequest -  soecific criteria
     * @return collection of item ids sorted by short description
     * @throws RemoteException if an errors
     */
    public IdVector searchItemDocs(String pStoreTypeCd,
                                   String pCustSku,
                                   String pMfgSku,
                                   String pName,
                                   String pDesc,
                                   int pCategoryId,
                                   String pDocType,
                                   int pMfgId,
                                   int pSortBy,
                                   ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

 /**
   * Picks up user order guides
   * @param pUserId  the user identificator
   * @param pCatalogId  the catalog identificator
   * @param pSiteId  the site identificator
   * @return collection of BusEntityData objects manufacturer type
   * @throws            RemoteException
   */
  public OrderGuideDataVector getUserOrderGuides(int pUserId, int pCatalogId, int pSiteId) throws RemoteException;

    /**
     * Picks up user order guides
     *
     * @param pUserId
     *            the user identificator
     * @param pSiteId
     *            the site identificator
     * @return collection of BusEntityData objects manufacturer type
     * @throws RemoteException
     */
    public OrderGuideDataVector getUserOrderGuides(int pUserId, int pSiteId) throws RemoteException;

  /**
   * Sorts items.
   * @param pStoreType  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pItemsId the collection of items
   * @param pSortBy determins sort order (is sort by short_dscription or sku number, takes first catalog value, and if it is null, takes item value
   * @return collection of item ids sorted by short description
   * @throws            RemoteException
   */
  public IdVector sortItems(String pStoreType, int pCatalogId, IdVector pItemIds, int pSortBy) throws RemoteException;

  /******************************************************************************/
  /**
   * Picks up template order guides
   * @param pCatalogId  the catalog identificator
   * @param pSiteId  the site identificator or 0 if does not apply
   * @return collection of BusEntityData objects manufacturer type
   * @throws            RemoteException
   */
  public OrderGuideDataVector getTemplateOrderGuides(int pCatalogId, int pSiteId) throws RemoteException;

  public ShoppingCartItemDataVector getOrderGuidesItemsAvailable
    (String pStoreTypeCd, SiteData pSiteData, int pCatalogId,
     int pContractId, boolean pContractOnly, int pOrderGuideId,
     int pOrderOfTheResult) throws RemoteException;
  public ShoppingCartItemDataVector getOrderGuidesItemsAvailable
    (String pStoreTypeCd, SiteData pSiteData, int pCatalogId,
     int pContractId, boolean pContractOnly, int pOrderGuideId,
     int pOrderOfTheResult, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  /**
   * Picks up order guide items
   * If product blongs to more than one category, takes the first one (ignores order guide category)
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pOrderGuideId  the order guide identificator
   * @param pOrder  the order items to be returned (Constants.ORDER_BY_CATEGORY, Constants.ORDER_BY_NAME, etc)
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getOrderGuideItems(String pStoreTypeCd,
                                                       SiteData pSiteData,
                                                       int pOrderGuideId,
                                                       ShoppingItemRequest pShoppingItemRequest,
                                                       int pOrder) throws RemoteException;

  public ShoppingCartItemDataVector getOrderGuideItems(String pStoreTypeCd,
                                                       SiteData pSiteData,
                                                       int pOrderGuideId,
                                                       ShoppingItemRequest pShoppingItemRequest,
                                                       int pOrder,
                                                       AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;
  public int getOrderGuideItemCount(int pOrderGuideId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

  /**
   * Picks up order guide items
   * @param pShoppingCart  the ShoppingCartData object
   * @param pCatalogId inentifys catalog, which was used creating the shopping cart
   * @param pUser user login name
   * @throws            RemoteException
   */
  public ShoppingCartData saveShoppingCart
    (ShoppingCartData pShoppingCart,
     int pCatalogId, String pUser) throws RemoteException;

  public ShoppingCartData saveShoppingCart
    (ShoppingCartData pShoppingCart,
     int pCatalogId,
     String pUser,
     ProcessOrderResultData pOrderResult,
     List pScartItemPurchased) throws RemoteException;
  public ShoppingCartData saveShoppingCart
    (ShoppingCartData pShoppingCart,
     int pCatalogId,
     String pUser,
     ProcessOrderResultData pOrderResult,
     List pScartItemPurchased, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public void createShoppingInfo(ShoppingCartData pShoppingCart,
                                 String pUser,
                                 ProcessOrderResultData pOrderResult,
                                 List pScartItemPurchased) throws RemoteException;

  public void saveModifiedOrderShoppingInfo(ShoppingCartData pShoppingCart,
                                            String pUser,
                                            ProcessOrderResultData pOrderResult) throws RemoteException;


  /**
   * Picks up order guide items
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pUser the user object
   * @param pSite the site object
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @throws            RemoteException
   */
  public ShoppingCartData getShoppingCart(String pStoreType, UserData pUser, SiteData pSite, int pCatalogId, int pContractId) throws
    RemoteException;
  public ShoppingCartData getShoppingCart(String pStoreType, UserData pUser, SiteData pSite, int pCatalogId, int pContractId, AccCategoryToCostCenterView pCategToCostCenterView) throws
    RemoteException;
  public ShoppingCartData getShoppingCart(String pStoreType, UserData pUser, String userName2, SiteData pSite, int pCatalogId, int pContractId, AccCategoryToCostCenterView pCategToCostCenterView) throws
  RemoteException;

  /**
   * Picks up order guide items
   * @param pOrderGuide  the OrderGuide object
   * @param pItems list of ShoppingCartItemData objects
   * @param pUser user login name
   * @return orderGuideId
   * @throws            RemoteException, DataNotFoundException
   */
  public int saveUserOrderGuide(OrderGuideData pOrderGuide, List pItems, String pUser) throws RemoteException,
    DataNotFoundException;

  /**
   * Picks up janitor's closet items
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return collection of ShoppingCartItemDataVector
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getJanitorCloset(String pStoreType, int pCatalogId, int pContractId,
    boolean pContractOnly, int pUserId, int pSiteId) throws RemoteException;
  public ShoppingCartItemDataVector getJanitorCloset(String pStoreType, int pCatalogId, int pContractId,
    boolean pContractOnly, int pUserId, int pSiteId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  /**
   * Picks up last order information
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return OrderData object
   * @throws            RemoteException, DataNotFoundException
   */
  public OrderData getLastOrder(int pUserId, int pSiteId) throws RemoteException, DataNotFoundException;

  /**
   * Picks up last order items
   * @param pStoreType (store type defined in RefCodeNames interface)
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return collection of ShoppingCartItemDataVector
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getLastOrderItems
    (String pStoreType, SiteData pSiteData, int pCatalogId, OrderData pLastOrder) throws RemoteException;
  public ShoppingCartItemDataVector getLastOrderItems
    (String pStoreType, SiteData pSiteData, int pCatalogId, OrderData pLastOrder, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  /**
   * Calculates freight amount. Does not apply if pContractId=0 or no active freight table found for the contact
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pAmount  purchase amount
   * @param pWeight purchase weigh
   * @return freight charge amount.
   * @throws            RemoteException
   */
  public BigDecimal getFreightAmt(int pContractId, BigDecimal pAmount, OrderHandlingView pOrder) throws RemoteException;

  /**
   * Calculates handling amount. Does not apply if pContractId=0 or no active freight table found for the contact
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pAmount  purchase amount
   * @param pWeight purchase weigh
   * @return handling charge amount.
   * @throws            RemoteException
   */
  public BigDecimal getHandlingAmt(int pContractId, BigDecimal pAmount, OrderHandlingView pOrdert) throws RemoteException;

  /**
   * Calculates freight and handling amount for the order.
   * @param pOrder ejb interfacfe object to calculate freight and handling amounts
   * @return the parameter pOrder with polulated totalFraight and totalHandiling fields
   * @throws            RemoteException
   */
  public OrderHandlingView calcTotalFreightAndHandlingAmount(OrderHandlingView pOrder) throws RemoteException;

  /**
   * Calculates service fee amount for the order.
   * @param
   * @return
   * @throws            RemoteException
   */
  public HashMap calculateServiceFee(int contractId, List cartItems, int accId) throws RemoteException;

    /**
     * Prepares collection of ShoppingCartItemDataVector. Uses Jd rules to calculate list and discount price
     *
     * @param pPriceCode           the site price code. Rules, which prices to use
     * @param pSkuNums             the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getJdShoppingItemsBySku(String pPriceCode,
                                                              List pSkuNums,
                                                              ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException;
    public ShoppingCartItemDataVector getJdShoppingItemsBySku(String pPriceCode,
                                                              List pSkuNums,
                                                              ShoppingItemRequest pShoppingItemRequest,
                                                              AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException;

    /**
     * Prepares collection of ShoppingCartItemDataVector. Uses Jd rules to calculate list and discount price
     * If product blongs to more than one category, takes the first one
     *
     * @param pPriceCode           the site price code. Rules, which prices to use
     * @param pSkuNums             the list of sku numbers
     * @param pShoppingItemRequest spec criteria
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException       if an errors
     * @throws DataNotFoundException if an errors
     */
    public ShoppingCartItemDataVector getJdShoppingItemsByMfgSku(String pPriceCode,
                                                                 List pSkuNums,
                                                                 ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException;


    /**
     * Prepares collection of ShoppingCartItemDataVector. Uses Jd rules to assign prices
   * If product blongs to more than one category, takes the first one
   * @param pPriceCode the site price code
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pContractId the contract identificator or 0 if doesn't appliy
   * @param pItems the list of item is or the list of product object
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ShoppingCartItemDataVector prepareJdShoppingItems
    (String pPriceCode, int pCatalogId,
     int pContractId, List pItems) throws RemoteException;
  public ShoppingCartItemDataVector prepareJdShoppingItems
      (String pPriceCode, int pCatalogId,
       int pContractId, List pItems, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

//  public ShoppingCartItemDataVector prepareJdShoppingItems
//    (Connection pCon, String pPriceCode, int pCatalogId, int pContractId, List pItems) throws RemoteException;

  /**
   * Picks up order guide items
   * If product blongs to more than one category, takes the first one (ignores order guide category)
   * @param pPriceCode  the site price code. Rules, which prices to use
   * @param pCatalogId  the catalog identificator
   * @param pContractId  the contract identificator
   * @param pContractOnly boolean flag, which indicates user permition to buy off contact
   * @param pOrderGuideId  the order guide identificator
   * @param pOrder  the order items to be returned (Constants.ORDER_BY_CATEGORY, Constants.ORDER_BY_NAME, etc)
   * @return collection of ShoppingCartItemData objects
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getJdOrderGuidesItems(String pPriceCode, int pCatalogId, int pContractId,
    boolean pContractOnly, int pOrderGuideId, int pOrder) throws RemoteException;

  public ShoppingCartItemDataVector getJdOrderGuidesItems(String pPriceCode, int pCatalogId, int pContractId,
    boolean pContractOnly, int pOrderGuideId, int pOrder, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;


  /**
   * Picks up last order items
   * @param pPriceCode  the site price code. Rules, which prices to use
   * @param pCatalgoId the catalog identifier to filter out extra items
   * @param pContractId the contract identifier to filter out extra items, if not 0
   * @param pUserId  the user identifier
   * @param pSiteId the site identifier
   * @return collection of ShoppingCartItemDataVector
   * @throws            RemoteException
   */
  public ShoppingCartItemDataVector getJdLastOrderItems
    (String pPriceCode, int pCatalogId, OrderData pLastOrder) throws RemoteException;
  public ShoppingCartItemDataVector getJdLastOrderItems
    (String pPriceCode, int pCatalogId, OrderData pLastOrder, AccCategoryToCostCenterView pCategToCostCenterView ) throws RemoteException;

  public java.util.List updateShoppingInfo
          (int pSiteId,
           int pOrderGuideId,
           java.util.List pShoppingInfoVector) throws RemoteException;

    /******************************************************************************/
    /**
     * Picks up order guide items
     * If product blongs to more than one category, takes the first one (ignores order guide category)
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pSiteData            the ssite data
     * @param pShoppingItemRequest the shopping criteria
     * @param pOrderGuideId        the order guide identificator
     * @param pOrder               the order items to be returned (Constants.ORDER_BY_CATEGORY, Constants.ORDER_BY_NAME, etc)
     * @return collection of ShoppingCartItemData objects
     * @throws RemoteException if an errors
     */
    public ShoppingCartItemDataVector getOrderGuidesItems(String pStoreTypeCd,
                                                          SiteData pSiteData,
                                                          int pOrderGuideId,
                                                          ShoppingItemRequest pShoppingItemRequest,
                                                          int pOrder) throws RemoteException;
    public ShoppingCartItemDataVector getOrderGuidesItems(String pStoreTypeCd,
                                                          SiteData pSiteData,
                                                          int pOrderGuideId,
                                                          ShoppingItemRequest pShoppingItemRequest,
                                                          int pOrder,
                                                          AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public ShoppingCartItemDataVector getItemControlInfo
    (String pStoreTypeCd, SiteData pSiteData) throws RemoteException;
  public ShoppingCartItemDataVector getItemControlInfo
    (String pStoreTypeCd, SiteData pSiteData, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public ShoppingCartItemDataVector getItemControlInfoForAccount
    (int pAcctId, int pCatId) throws RemoteException;

  public ShoppingCartItemDataVector getItemControlInfoForAccount
  (int pAccountId, int pCatalogId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;
  
  public AddressDataVector getUserBillTos(int pUserId, int pSiteId) throws RemoteException;

  public ShoppingInfoDataVector getOrderShoppingInfo(int pOrderId) throws RemoteException;

  /**
   * Prepares collection of ShoppingCartServiceDataVector. Assigns contract price if exists, othewise assigns list price.
   * If servbice blongs to more than one category, takes the first one
   * @param pStoreTypeCd  the store type. Rules, which sku number is used
   * @param pCatalogId  the catalog identificator
   * @param pContractId the contract identificator or 0 if doesn't appliy
   * @return collection of ShoppingCartServiceData objects
   * @throws            RemoteException Required by EJB 1.0
   */
  public ShoppingCartServiceDataVector prepareShoppingServices(String pStoreTypeCd, SiteData pSiteData,
    int pCatalogId, int pContractId, AssetData pAsset,
    ServiceDataVector pServices) throws RemoteException;


    /**
     * Picks up all order guide items, which match criteria. Returns empty collection if all filter parameters are empty.
     * Ignores filter parameter if it is empty. Applies contains ignore case match type
     *
     * @param pStoreTypeCd         the store type. Rules, which sku number is used
     * @param pOrderGuideV         the order guide identificators
     * @param pCustSku             - custormer sku number filter. Applies catalog sku number if exsits, otherwise applies item sku number
     * @param pMfgSku              - manufacturer sku number filter
     * @param pName                - item short description filter. Applies catalog short description if exsits, otherwise applies item short description
     * @param pDesc                - item long description filter
     * @param pCategory            - item lowest level category filter
     * @param pSize                - item size property filter
     * @param pMfgId               - manufacturer Id filter. Unlike other fiters it demands exact equal match type
     * @param pGreenCertifiedFlag  item certified filter
     * @param pShoppingItemRequest spec criteria
     * @return collection of item ids sorted by short description
     * @throws java.rmi.RemoteException if an errors
     */
    public IdVector searchOrderGuideItems(String pStoreTypeCd,
                                          IdVector pOrderGuideV,
                                          String pCustSku,
                                          String pMfgSku,
                                          String pName,
                                          String pDesc,
                                          String pCategory,
                                          String pSize,
                                          int pMfgId,
                                          boolean pGreenCertifiedFlag,
                                          ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    public List orderList(List pList, int pOrderBy) throws RemoteException;

    public ShoppingCartData getInvShoppingCart(String pStoreType,
                                             UserData pUser,
                                             SiteData pSite) throws RemoteException;

  public ShoppingCartData getInvShoppingCart(String pStoreType,
                                               UserData pUser,
                                               SiteData pSite,
                                               boolean pInventorySiteDataItems) throws RemoteException;
  public ShoppingCartData getInvShoppingCart(String pStoreType,
                                             UserData pUser,
                                             SiteData pSite,
                                             boolean pInventorySiteDataItems,
                                             AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public void saveInventoryCartOG(ShoppingCartData pShoppingCart) throws RemoteException;

  public ShoppingCartData getInventoryCartOG(UserData pUser, SiteData pSite, String pStoreType) throws RemoteException;
  public ShoppingCartData getInventoryCartOG(UserData pUser, SiteData pSite, String pStoreType, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public void saveInventoryCartOG(ShoppingCartData sd,
                                  ProcessOrderResultData orderRes,
                                  ShoppingCartItemDataVector entriesCollection) throws RemoteException;

  public void saveInventoryShoppingCart(ShoppingCartData mergedCart, boolean generateInvAuditEntry) throws RemoteException;
  
  public void saveInventoryShoppingCart(ShoppingCartData pShoppingCartOG,
                                        ShoppingCartData pShoppingCartIL) throws RemoteException;

  public void saveInventoryShoppingCart(ShoppingCartData pShoppingCartOG,
                                          ShoppingCartData pShoppingCartIL,
                                          boolean pUpdateFromPhysCart) throws RemoteException;

 public ShoppingCartData getInventoryCartIL(String pStoreType,
                                             UserData pUser,
                                             SiteData pSite) throws RemoteException;

 public ShoppingCartData getInventoryCartIL(String pStoreType,
                                            UserData pUser,
                                            SiteData pSite,
                                            AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

 public ShoppingCartData getInventoryCartIL(String pStoreType,
                                                   UserData pUser,
                                                   SiteData pSite,
                                                   boolean pInventorySiteDataItems) throws RemoteException;

 public ShoppingCartData getInventoryCartIL(String pStoreType,
                                                   UserData pUser,
                                                   SiteData pSite,
                                                   boolean pInventorySiteDataItems,
                                                   AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

 public void saveInventoryShoppingCart(ShoppingCartData mergedCart,
                                       ProcessOrderResultData pProcessOrderResult,
                                       ShoppingCartItemDataVector pScartItemPurchased) throws RemoteException;


 public void saveInventoryShoppingCart(ShoppingCartData mergedCart,
            ProcessOrderResultData pProcessOrderResult,
            ShoppingCartItemDataVector pScartItemPurchased,
            boolean pUpdateFromPhysCart) throws RemoteException;

 public OrderGuideDataVector getCartsForReminderAction() throws RemoteException;

 public void invalidateInvetoryShoppingHistory(int pSiteId, String pUser)
  throws RemoteException;

    public ShoppingCartItemDataVector prepareShoppingGroupItems(String storeType,
                                                                SiteData site,
                                                                int itemGroupId,
                                                                ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

   public ProductDataVector integratedSearch(String storeType, ShoppingItemRequest pShoppingItemRequest,  String searchString) throws RemoteException;
   public ProductDataVector integratedSearch(String storeType, ShoppingItemRequest pShoppingItemRequest,  String searchString, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

    public ShoppingCartItemDataVector sortShoppingCardItems(int pOrder,
    		ShoppingCartItemDataVector itemList) throws RemoteException;
  public  void updateInventoryParValues(String pUserName,  int pSiteId,
                int pCurrentInventoryPeriod, ShoppingCartItemDataVector cartItems) throws RemoteException;

    public BigDecimal getChargeAmtByCode(int pContractId,
                                         BigDecimal pAmount,
                                         OrderHandlingView pOrder,
                                         String chargeCd) throws RemoteException;

   public void saveInventoryCartIL(ShoppingCartData inventoryCart) throws RemoteException;

   public void saveInventoryCartIL(ShoppingCartData inventoryCart, boolean pUpdateFromPhysCart) throws RemoteException;

   public void saveInventoryCartIL(ShoppingCartData inventoryCart, boolean pUpdateFromPhysCart, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

   public void saveAutoDistroInvShopHistory(UserData user, int siteId,
       int currInvPeriod, ShoppingCartItemDataVector cartItems)
           throws RemoteException;
    public void saveAutoDistroInvShopHistory(UserData user, int siteId,
        int currInvPeriod, ShoppingCartItemDataVector cartItems, AccCategoryToCostCenterView pCategToCostCenterView)
            throws RemoteException;


   public PriceRuleDescView updatePriceRuleDesc(PriceRuleDescView pPriceRule, String pUserName) throws RemoteException;

   public PriceRuleDescViewVector getServiceFeeDescVector(int pBusEntityId) throws RemoteException;

   public ArrayList getAllServiceFeeCodesForStore(int pStoreId) throws RemoteException;

   /**
    * Calculates discount amount. Does not apply if pContractId=0 or no active freight table found for the contact
    * @param pContractId the contract identifier to filter out extra items, if not 0
    * @param pAmount  purchase amount
    * @param pOrder
    * @return discount amount.
    * @throws            RemoteException
    */
   public BigDecimal getDiscountAmt(int pContractId, BigDecimal pAmount, OrderHandlingView pOrder) throws RemoteException;

    public IdVector getSpecialPermssionItemIds(int pAccounCatalogId) throws RemoteException;

    public ProductDataVector getTopCatalogProducts(int pSiteId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException;
    public ProductDataVector getTopCatalogProducts(int pSiteId, ShoppingItemRequest pShoppingItemRequest,  AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException;

    public ProductDataVector getCatalogChildProducts(int pSiteId,
                                                     int pParentpCategoryId,
                                                     ShoppingItemRequest pShoppingItemRequest) throws RemoteException, DataNotFoundException;
    public ProductDataVector getCatalogChildProducts(int pSiteId,
                                                     int pParentpCategoryId,
                                                     ShoppingItemRequest pShoppingItemRequest,  AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException, DataNotFoundException;

    /* Following metod can be removed later if getCategoryChildProducts(int pStoreCatalogId,... works
    public ProductDataVector getCategoryChildProducts(int pCategoryId, IdVector pAvailableCategoryIds, ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    public ProductDataVector getCategoryChildProducts(int pCategoryId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    public ProductDataVector getCategoryChildProducts(int pSiteId, int pCategoryId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException;

    public ProductDataVector getCategoryChildProducts(int pSiteId,
                                                      int pCategoryId,
                                                      IdVector pAvailableCategoryIds,
                                                      ShoppingItemRequest pShoppingItemRequest) throws RemoteException;
    
    public ProductDataVector getCategoryChildProducts(int pSiteId,
                                                      int pCategoryId,
                                                      IdVector pAvailableCategoryIds,
                                                      ShoppingItemRequest pShoppingItemRequest,
                                                      AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;*/
    
    public ProductDataVector getCategoryChildProducts(int pStoreCatalogId,
			int pSiteId,
            int pCategoryId,            
            ShoppingItemRequest pShoppingItemRequest,
            AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;


    public OrderGuideDataVector getCustomOrderGuides(int pAccountId, int pSiteId)throws RemoteException;

    public ProductBundle getProductBundle(String pStoreType, int pSiteId, ProductDataVector pSiteProducts) throws RemoteException;

    public ProductBundle getProductBundle(String pStoreType, int pSiteId, int pCatalogId, IdVector pProductIds) throws RemoteException;

    public ProductBundle getProductBundle(String pStoreType, int pSiteId, int pCatalogId, IdVector pProductIds, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException ;

    public BigDecimal getContractItemPrice(int pStoreId, int pSiteId, int pContractId, int pCatalogId, int pItemId) throws RemoteException;

    public String getProductBundleValue(int pSiteId) throws RemoteException;

    //	Location Budget
    
    public ProductDataVector  prepareShoppingCartItems(Connection pCon, int pCatalogId, int pContractId, List pItems,
            int siteId, AccCategoryToCostCenterView pCategToCostCenterView)
     throws RemoteException;
   
    /** This method prepares the data needed to calculate total amount
    *   in shopping cart pertaining to a cost center
    *  
    * @param storeType
    * @param user
    * @param site
    * @param catalogId
    * @param contractId
    * @param accCategoryToCostCenterView
    * @return
    */
    public CostCenterCartData getProductDataVector(String storeType,UserData user, SiteData site, int catalogId, int contractId, AccCategoryToCostCenterView accCategoryToCostCenterView);

    public ShoppingCartItemDataVector setupMaxOrderQtyValues
      (SiteData pSite, ShoppingCartItemDataVector pCartItems) throws RemoteException;
    public void populateProductBundleFilter(ShoppingItemRequest pShoppingItemRequest)   throws RemoteException;   //---- STJ-6114: Performance Improvements - Optimize Pollock 

    public ProductDataVector getCatalogClwProductCollection ( int pCatalogId, IdVector pIds, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException ;

}

