package com.cleanwise.service.api.session;

/**
 * Title:        CatalogInformation
 * Description:  Remote Interface for CatalogInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating catalog and associated contracts.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogCategoryViewVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogFiscalPeriodViewVector;
import com.cleanwise.service.api.value.CatalogItemDescViewVector;
import com.cleanwise.service.api.value.CatalogItemViewVector;
import com.cleanwise.service.api.value.CatalogPropertyData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.CategoryToCostCenterViewVector;
import com.cleanwise.service.api.value.CostCenterAssocDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemCatalogAggrViewVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.MultiproductViewVector;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ServiceData;
import com.cleanwise.service.api.value.ServiceSearchCriteria;
import com.cleanwise.service.api.value.ServiceViewVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.UserDataVector;

public interface CatalogInformation extends javax.ejb.EJBObject
{

  /*
   * Gets the array-like catalog vector values to be used by the request.
   * @param pBusEntityId  the customer identifier
   * @return CatalogDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public CatalogDataVector getCatalogsCollectionByBusEntity(int pBusEntityId)
      throws RemoteException;
      public CatalogDataVector getCatalogsCollectionByBusEntity(int pBusEntityId
    , String pCatalogTypeCd)
    throws RemoteException;

    /**
     *  Gets the array-like catalog vector values to be used by the request.
     *
     *@param  pUserId      the customer identifier
     *@return                   CatalogDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public CatalogDataVector getCatalogsCollectionByUser(int pUserId)
        throws RemoteException;

    public IdVector getCatalogIdsCollectionByUser(int pUserId) throws
        RemoteException;

  /*
   * Gets the array-like catalog vector values to be used by the request.
   * @param pCatName  the catalog name or catalog name substring
   * @param pMatchType   Public static varaibles in CatalogInformation: EXACT_MATCH |
   * BEGINS_WITH |
   * CONTAINS |
   * EXACT_MATCH_IGNORE_CASE |
   * BEGINS_WITH_IGNORE_CASE |
   * CONTAINS_IGNORE_CASE
   * @param pBusEntityId  the customer identifier
   * @return CatalogDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public CatalogDataVector
      getCatalogsCollectionByNameAndBusEntity(String pCatName, int pMatchType,
                int pBusEntityId)
      throws RemoteException;

  /**
   * Gets catalog information values to be used by the request.
   * @param pCatalogId  the catalog identifier
   * @return CatalogData
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogData getCatalog(int pCatalogId)
      throws RemoteException, DataNotFoundException;

  public CatalogPropertyData getCatalogProperty(int pCatalogId) throws RemoteException;


  /**
   * Gets the array-like catalog association vector values to be used by the request.
   *
   * @return CatalogAssociationDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
   public CatalogDataVector getCatalogCollection()
      throws RemoteException;
   /**
   * Determins wherther exists the catalog-bus entity association
   * @param pCatalogId catalog identifier or 0 if any
   * @param pBusEntityId busness entity identifier or 0 if any
   * @param pAssocType association type (CATALOG_STORE, CATALOG_ACCOUT,..) if null any association accepted
   * @return boolean true if found
   * @throws            RemoteException
   */
   public boolean doesCatalogAssocExist(int pCatalogId, int pBusEntityId, String pAssocType)
      throws RemoteException;

   /**
   * Gets collection of CatalogAssocData elements
   * @param pCatalogId catalog identifier or 0 if any
   * @param pBusEntityId busness entity identifier or 0 if any
   * @param pAssocType association type (CATALOG_STORE, CATALOG_ACCOUT,..) if null any association accepted
   * @return boolean true if found
   * @throws            RemoteException
   */
   public CatalogAssocDataVector getCatalogAssoc(int pCatalogId, int pBusEntityId, String pAssocType)
      throws RemoteException;

   public CatalogDataVector getAccountAndShoppingCatalogsByStoreId(int pStoreId) throws
       RemoteException;

   public CatalogStructureDataVector getCatalogStructureByCategoryId(int pCategoryItemId)
       throws RemoteException;

   public CatalogStructureDataVector getCatalogStructure(int pCategoryItemId, int pCatalogId) throws RemoteException;

   public CatalogDataVector getSTORETypeCatalogsByStoreId(int pStoreId) throws
       RemoteException;

   /**
   * Gets the array-like catalog association vector values to be used by the request.
   * @param pEntitySearchCriteria the criteria
   * @return CatalogAssociationDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
   public CatalogDataVector getCatalogsByCrit(EntitySearchCriteria pEntitySearchCriteria)
      throws RemoteException;


  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @throws            RemoteException Required by EJB 1.0
   */
   public BusEntityDataVector getStoreCollection(int pCatalogId)
      throws RemoteException;

  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @throws            RemoteException Required by EJB 1.0
   */

   public BusEntityDataVector getAccountCollection(int pCatalogId)
      throws RemoteException;

    public Map<Integer, List<Integer>> getCatalogIdToAccountIdMap(Collection<Integer> pCatalogIds)
       throws RemoteException;

  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @param pNameFilter string to filter Account on shortDesc.
   * @return BusEntityDataVector object of BusEntityData Account type objects
   * @throws            RemoteException Required by EJB 1.0
   */
   public BusEntityDataVector getAccountCollection(int pCatalogId, String pNameFilter)
      throws RemoteException;

    /**
     *  Gets the Account collection for the catalog.
     *  Applies of filter for account name (contains ignore case)
     *  Picks up only BusEntityData and AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not apply
     *@param  pMatch               Match type (exect match, starts with, etc)
     *@param  pOrder               Result order (by id, by short description)
     *@return                      The AccountDataVector value
     *@exception  RemoteException  Description of Exception
     */

    public AccountDataVector getAccountCollection(int pCatalogId,
                                                  String pFilter,
                                                  int pMatch,
                                                  int pOrder)
      throws RemoteException;

     /**
     *  Gets the Account collection for the catalog. Applies of filter for
     *  account name (contains ignore case) Picks up only BusEntityData and
     *  AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not
     *      apply
     *@param  pShowInactiveFl      Filters out inactive accounts if false
     *@param  pMatch               Match type (exect match, starts with, etc)
     *@param  pOrder               Result order (by id, by short description)
     *@return                      The AccountDataVector value
     *@exception  RemoteException  Description of Exception
     */

    public AccountDataVector getAccountCollection(int pCatalogId,
                                                  String pFilter,
                                                  boolean pShowInactiveFl,
                                                  int pMatch,
                                                  int pOrder)
    throws RemoteException;


  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @throws            RemoteException Required by EJB 1.0
   */
   public BusEntityDataVector getSiteCollection(int pCatalogId)
      throws RemoteException;

     /**
     *  Gets the Site collection for the catalog. Applies of filter for site
     *  name (contains ignore case) Picks up only BusEntityData,
     *  BusEntityAssocData and shipping AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not
     *      apply
     *@param  pShowInactiveFl      Filters out inactive sites if false
     *@param  pMatch               Match type (exect match, starts with, etc)
     *@param  pOrder               Result order (by id, by short description)
     *@return                      The SiteDataVector value
     *@exception  RemoteException  Description of Exception
     */

    public SiteDataVector getSiteCollection(int pCatalogId,
                                            String pFilter,
                                            boolean pShowInactiveFl,
                                            int pMatch,
                                            int pOrder)
        throws RemoteException;


    /**
     *  Gets the site ids associated with the supplied catalogId
     *
     *@param  pCatalogId           Description of Parameter
     *@return                      The SiteIdVector value
     *@exception  RemoteException  Description of Exception
     */
    public IdVector getSiteIds(int pCatalogId)
      throws RemoteException;


    /**
     *  Gets the Site collection for the catalog.
     *  Applies of filter for site name (contains ignore case)
     *  Picks up only BusEntityData, BusEntityAssocData and shipping AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not apply
     *@param  pMatch               Match type (exect match, starts with, etc)
     *@param  pOrder               Result order (by id, by short description)
     *@return                      The SiteDataVector value
     *@exception  RemoteException  Description of Exception
     */

    public SiteDataVector getSiteCollection(int pCatalogId,
                                                  String pFilter,
                                                  int pMatch,
                                                  int pOrder)
             throws RemoteException;


  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @param pNameFilter string to filter Site on shortDesc.
   * @return BusEntityDataVector object of BusEntityData Account type objects
   * @throws            RemoteException Required by EJB 1.0
   */
   public BusEntityDataVector getSiteCollection(int pCatalogId, String pNameFilter)
      throws RemoteException;

  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @param pBusEntityTypeCd business entity type code (STORE, ACCOUNT, SITE)
   * @throws            RemoteException Required by EJB 1.0
   */
   public BusEntityDataVector getBusEntityCollection(int pCatalogId, String pBusEntityTypeCd)
      throws RemoteException;

  /**
   * Gets the array-like business entity association vector values to be used by the request.
   * @param pCatName  the catalog name
   * @param pBusEntityTypeCd business entity type code (STORE, ACCOUNT, SITE)
   * @param pNameFilter shortDesc filter
   * @param pMatch determins filter matchibg action
   * @param pOrder resunt order (could be by id or by short description)
   * @throws            RemoteException Required by EJB 1.0
   */
   public BusEntityDataVector getBusEntityCollection(int pCatalogId,
                                                     String pBusEntityTypeCd,
                                                     String pNameFilter,
                                                     int pMatch,
                                                     int pOrder)
      throws RemoteException;


   public BusEntityData getBusEntity(int pCatalogId,
                                     String pBusEntityTypeCd,
                                     int pBusEntityId)
      throws RemoteException, DataNotFoundException;

  /**
   * Gets the array-like user vector values to be used by the request.
   * @param pCatalogId  the catalog id
   * @throws            RemoteException Required by EJB 1.0
   */
  public UserDataVector getUserCollection(int pCatalogId)
      throws RemoteException;

  /*
   * Gets the array-like catalog association vector values.
   * @param pCatalogId  the catalog identifier
   * @return CatalogAssociationDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public CatalogAssocDataVector getCatalogAssociationsCollection(int pCatalogId)
      throws RemoteException;

  /**
   * Gets catalog category information values to be used by the request.
   * @param pCatalogId  the catalog identifier
   * @param pCategoryId  the catalog category identifier
   * @return CatalogCategoryData
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogCategoryData getCatalogCategory(int pCatalogId, int pCategoryId)
      throws RemoteException, DataNotFoundException;

  /**
   * Gets catalog categories, which do not have parent categories or parent products.
   * @param pCatalogId  the catalog identifier
   * @return CatalogCategoryDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogCategoryDataVector getTopCatalogCategories(int pCatalogId)
      throws RemoteException;

  /**
   * Gets product information for the item id provided.
   * @param pProductItemId  the product identifier, this is based
   * on the CLW_ITEM.ITEM_ID entry.
   * @return ProductData
   * @throws RemoteException Required by EJB 1.0,
   * DataNotFoundException
   */
  public ProductData getProduct(int pProductItemId)
      throws RemoteException, DataNotFoundException;
  public ProductData getProduct(int pProductItemId, AccCategoryToCostCenterView pCategToCostCenterView)
      throws RemoteException, DataNotFoundException;

  /**
   * Gets catalog products, which do not have parent categories or parent products.
   * @param pCatalogId  the catalog identifier
   * @return CatalogCategoryDataVector
   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException
   */
  public ProductDataVector getTopCatalogProducts(int pCatalogId)
      throws RemoteException, DataNotFoundException;

  public ProductDataVector getTopCatalogProducts(int pCatalogId, int siteId) throws
  RemoteException, DataNotFoundException ;

  public ProductDataVector getTopCatalogProducts(int pCatalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws
  RemoteException, DataNotFoundException ;

    /**
     *  Gets items, which do not have parent categories or parent
     *  products.
     *@param  pCatalogId                 the catalog identifier
     *@return                            CatalogItemViewVector
     *@throws  RemoteException           Required by EJB 1.0,
     */
    public CatalogItemViewVector getTopCatalogItems(int pCatalogId)
      throws RemoteException;

  /**
   * Gets catalog child categories for category or product
   * @param pCatalogId  the catalog identifier
   * @param pParentId parent category or product id
   * @return CatalogCategoryDataVector
   * @throws            RemoteException Required by EJB 1.0
   */

  public CatalogCategoryDataVector getCatalogChildCategories(int pCatalogId, int pParentId)
      throws RemoteException;



  /**
   * Gets catalog child products for category or product
   * @param pCatalogId  the catalog identifier
   * @param pParentId parent category or product id
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getCatalogChildProducts(int pCatalogId, int pParentId)
      throws RemoteException, DataNotFoundException;
  /**
   * Gets catalog child products for category or product
   * @param pCatalogId  the catalog identifier
   * @param pParentId parent category or product id
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getCatalogChildProducts(int pCatalogId, int pParentId, int siteId)
      throws RemoteException, DataNotFoundException;

    /**
     *  Gets catalog child items for category or product
     *
     *@param  pCatalogId                 the catalog identifier
     *@param  pParentId                  parent category or product id
     *@return                            List of CatalogNodes objects
     *@throws  RemoteException           Required by EJB 1.0
     */
    public List getCatalogNodes(int pCatalogId, CatalogCategoryDataVector pCategories, int pLevel)
      throws RemoteException;

  /**
   * Gets IdVector of all anscestor ids for the product or category
   * @param pCatalogId  the catalog identifier
   * @param pParentId parent category or product id
   * @return IdVector
   * @throws            RemoteException,DataNotFoundException
   */
  public IdVector getAllAncestors(int pCatalogId, IdVector pItemIdV)
      throws RemoteException, DataNotFoundException;

  /*
   * Gets the array-like catalog structure vector values to be used by the request.
   * @param pCatalogId  the catalog identifier
   * @return CatalogStructureDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public CatalogStructureDataVector getCatalogStructuresCollection(int pCatalogId)
      throws RemoteException;
  
  /*
   * Gets the array-like item vector values to be used by the request.
   * @param pCatalogId  the catalog identifier
   * @return ItemDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public ItemDataVector getCatalogItemCollection(int pCatalogId) 
  	throws RemoteException;
  
  /**
   * Retrieves a map of category to items for a catalog.
   * @param pSiteId
   * @param pAvailableCategoryIds
   * @param pShoppingItemRequest
   * @param pCategToCostCenterView
   * @return
   * @throws RemoteException
   */
	public Map<Integer, ProductDataVector> getCatalogCategoryToItemMap(int pSiteId, IdVector pAvailableCategoryIds,
	          ShoppingItemRequest pShoppingItemRequest, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException;

  public OrderGuideDataVector getOrderGuideCollection(int pCatalogId)
      throws RemoteException;

    /**
     *  Gets the Distributor collection for the catalog.
     *  Applies of filter for distributor name (contains ignore case)
     *  Picks up only BusEntityData and AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not apply
     *@return                      The DistributorDataVector value
     *@exception  RemoteException  Description of Exception
     */

    public DistributorDataVector getDistributorCollection(int pCatalogId, String pFilter)
             throws RemoteException;

    /**
     *  Gets the Distributor collection for the catalog.
     *  Applies of filter for distributor name (contains ignore case)
     *  Picks up only BusEntityData and AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not apply
     *@param  pMatch               Match type (exect match, starts with, etc)
     *@param  pOrder               Result order (by id, by short description)
     *@return                      The ODistributorDataVector value
     *@exception  RemoteException  Description of Exception
     */
    public DistributorDataVector getDistributorCollection(int pCatalogId,
                                                          String pFilter,
                                                          int pMatch,
                                                          int pOrder)
             throws RemoteException;

    /**
     *  Gets the Distributor collection for the catalog.
     *  Applies of filter for distributor name (contains ignore case)
     *  Picks up only BusEntityData and AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not apply
     *@param  pShowInactiveFl      Filters out inactive distributors is false
     *@param  pMatch               Match type (exect match, starts with, etc)
     *@param  pOrder               Result order (by id, by short description)
     *@return                      The ODistributorDataVector value
     *@exception  RemoteException  Description of Exception
     */
    public DistributorDataVector getDistributorCollection(int pCatalogId,
                                                          String pFilter,
                                                          boolean pShowInactiveFl,
                                                          int pMatch,
                                                          int pOrder)
             throws RemoteException;

    /**
     *  Gets the DistributorData for the catalog.
     *  Picks up only BusEntityData and AddressData objects
     *
     *@param  pCatalogId           Description of Parameter
     *@param  pFilter              Filter string. If is null or empty does not apply
     *@return                      The ODistributorData value
     *@exception  RemoteException
     *@exception  DataNotFoundException
     */
    public DistributorData getDistributor(int pCatalogId, int pDistrId)
       throws RemoteException, DataNotFoundException;


  /**
   * Gets Master catalog id
   *
   * @return Master catalog id or 0 if not found
   * @throws  RemoteException (Required by EJB 1.0) and DataNotFoundException
   */
  public int getSystemCatalogId()
    throws RemoteException;

  /**
   * Gets master for the store catalog id. System catalog can be a master for one store
   *
   * @return System catalog id or 0 if not found
   * @throws  RemoteException (Required by EJB 1.0) and DataNotFoundException
   */
  public int getStoreCatalogId(int pStoreId)
    throws RemoteException;

  /**
   * Gets superior catalog.
   *
   * @return CatalogData superior catalog or null if does not exist
   * @throws  RemoteException (Required by EJB 1.0) and DataNotFoundException
   */
  public CatalogData getSuperCatalog(int pCatalogId)
    throws RemoteException, DataNotFoundException;

  /**
   * Determine whether catalog exists
   * @param catalog id
   * @return true if exists and false if not
   * @throws  RemoteException
   */
  public boolean doesCatalogExist(int pCatalogId)
    throws RemoteException;

  /*
   * Gets a collection of product ids from clw_item table, which match to request SearcCriteria
   * @param pCriteria  collection of SearchCriteria objects
   * @return IdVector of ProductData ids
   * @throws            RemoteException Required by EJB 1.0
   */
   public IdVector searchProducts(Collection pCriteria)
     throws RemoteException;

   /*
    * Gets a collection of product ids from clw_item table, which match to request SearcCriteria
    * @param pCriteria  collection of SearchCriteria objects
    * @return IdVector of ProductData ids
    * @throws            RemoteException Required by EJB 1.0
    */
    public IdVector searchProducts(Collection pCriteria, int storeId)
      throws RemoteException;

   /*
    * Gets a collection of product ids from clw_item table, which match to request SearcCriteria
    * @param pCriteria  collection of SearchCriteria objects
    * @return IdVector of ProductData ids
    * @throws            RemoteException Required by EJB 1.0
    */
    public IdVector searchProducts(Collection pCriteria, int pStoreId, boolean catalogReqFl) throws RemoteException;
    
    public IdVector searchCatalogProducts(Collection pCriteria, int storeId, int catalogId) throws RemoteException;

    /**
     *  Gets all items of the catalog
     *
     *@param  pCatalogId           Catalog Identifier
     *@return                      Description of the Returned Value
     *@exception  RemoteException  Description of Exception
     */
    public IdVector searchCatalogProducts(int pCatalogId)
             throws RemoteException;

    /**
     *  Description of the Method
     *
     *@param  pCatalogId           Catalog Identifier
     *@param  pCriteria            Description of Parameter
     *@return                      Description of the Returned Value
     *@exception  RemoteException  Description of Exception
     */
    public IdVector searchCatalogProducts(int pCatalogId, Collection pCriteria)
             throws RemoteException;

    /**
     *  Gets items of the catalog
     *
     *@param  pCatalogId           Catalog Identifier
     *@param  pCriteria            Description of Parameter
     *@param pNoSubCatalogsFlag    Indecatates to ignore subcatalogs (if catalog has account type);
     *@return                      Description of the Returned Value
     *@exception  RemoteException  Description of Exception
     */
    public IdVector searchCatalogProducts(int pCatalogId, Collection pCriteria, boolean pNoSubCatalogsFlag)
        throws RemoteException;

    /**
   * Gets Busness entity object by its id
   * @param BusEntityId
   * @return BusEntityData - requested object
   * @trows RemoteException, DataNotFoundException
   */
   public BusEntityData getBusEntity(int pBusEntityId)
     throws RemoteException, DataNotFoundException;


  public ProductData getCatalogClwProduct(int pCatalogId, int pProductId)
    throws RemoteException, DataNotFoundException;

  public ProductData getCatalogClwProduct(int pCatalogId, int pProductId
            , int pDistId)
    throws RemoteException, DataNotFoundException;

  public ProductData getCatalogClwProduct(int pCatalogId, int pProductId
          , int pDistId, int siteId)
  throws RemoteException, DataNotFoundException;

  public ProductData getCatalogClwProduct(int pCatalogId, int pProductId
          , int pDistId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView)
  throws RemoteException, DataNotFoundException;

    /**
     *  Gets the SiteCatalog attribute of the CatalogInformation object
     *
     *@param  pSiteId              Description of Parameter
     *@return                      The SiteCatalog value
     *@exception  RemoteException  Description of Exception
     */
    public CatalogData getSiteCatalog(int pSiteId)
             throws RemoteException;

    /**
     *  Gets the CatalogsByAccountId attribute of the CatalogInformation object
     *
     *@param  pAccountId           Description of Parameter
     *@return                      The CatalogsByAccountId value
     *@exception  RemoteException  Description of Exception
     */
    public CatalogDataVector getCatalogsByAccountId(int pAccountId)
             throws RemoteException;

    /**
     *  Gets a collection of catalogs that are of type ACCOUNT and
     *  are associated with the given store.
     *
     *@param  pStoreId           store id
     *@return                    collection of account catalogs
     *@exception  RemoteException
     */
    public CatalogDataVector getAccountCatalogsByStoreId(int pStoreId)
             throws RemoteException;

    public ProductDataVector getCatalogClwProductCollection
  (int pCatalogId, IdVector pIds)
        throws RemoteException, DataNotFoundException ;

    public ProductDataVector getCatalogClwProductCollection(int pCatalogId, IdVector pItemIds, int pSiteId) throws RemoteException, DataNotFoundException;
    public ProductDataVector getCatalogClwProductCollection
      (int pCatalogId, IdVector pIds, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,  DataNotFoundException;

    public ProductDataVector getProductCollection(IdVector pIds)
        throws RemoteException, DataNotFoundException;

    public ProductDataVector getProductCollection(IdVector pIds, int siteId)
    throws RemoteException, DataNotFoundException;

    public ProductDataVector getProductCollection(IdVector pIds, int siteId, AccCategoryToCostCenterView pCategToCostCenterView )
    throws RemoteException, DataNotFoundException;

    /**
     *  Gets catalog parent category for category or product
     *
     *@param  pCatalogId        the catalog identifier
     *@param  pChildId          child category or product id
     *@return                   CatalogCategoryDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public CatalogCategoryDataVector getCatalogParentCategory(int pCatalogId, int pChildId)
        throws RemoteException;

    /**
     *  Gets all avaliable categories that belong to the supplied categories.
     *  @param pCatalogIds  the catalog identifiers
     *  @return ItemDataVector
     *  @throws            RemoteException Required by EJB 1.0
     */
    public ItemDataVector getCatalogCategories(IdVector pCatalogIds)
    throws RemoteException;

   /**
     *Adds the specified item to the specified category name in the the specified catalogs.  Optionally
     *it will add the category if it does not allready exist.
     *@param pItemId the item to add to the category
     *@param pCatalogIds the catalogs we want to add this item in to.
     *@param pCategory the category name we are adding the items to.
     *@param pCategoryCostCenter the cost center id to use if we need to add this category.  Only used
     *  when the category does not exist and the addCategoryToCatalog flag is true.
     *@param addCategoryToCatalog determines wheather the category is added if it does not exist in the catalog.
     *@param the username of the user making the change for auditing
     *@throws RemoteException if there is an error
     */
    public void addItemToCategories(
    int pItemId, IdVector pCatalogIds, String pCategory, int pCategoryCostCenter, boolean addCategoryToCatalog,
    String pUserDoingMod)
    throws RemoteException;

    /**
     *Removes the specified item from the specified category name in the the specified catalogs.  Optionally
     *it will remove the item from all of the specified categories
     *@param pItemId the item.
     *@param pCatalogIds the catalogs.
     *@param pCategory the category name we are removing this item from.
     *@param removeFromAllCategories determines wheather we are removing the item from all categories.
     *@throws RemoteException if there is an error
     */
    public void removeItemFromCategories(
    int pItemId, IdVector pCatalogIds, String pCategory, boolean removeFromAllCategories)
    throws RemoteException;

    /**
     *Gets all major categories
     *@throws RemoteException
     */
    public ItemDataVector getMajorCategories()
    throws RemoteException;

    /**
     *Gets major category mismatches
     * @param pCategory category to check
     * @param pMajorCategoryId id of major category assigned
     * @return set of CatalogCategoryView objects
     *@throws RemoteException
     */
    public CatalogCategoryViewVector
            getMismatchMajorCategories(String pCategory, int pMajorCategoryId)
    throws RemoteException;

    /**
     *  Gets store items
     *
     *@param  pCriteria            Collscton of SearchCriteria objects
     *@return  a set of ItemView objects
     *@exception  RemoteException
     */
    public ItemViewVector searchStoreItems(List pCriteria, boolean pDistInfoFl)
        throws RemoteException;

    /**
     *  Gets catalog items
     *
     *@param  pCriteria            List of SearchCriteria objects
     *@return  a set of ItemView objects
     *@exception  RemoteException
     */
    public CatalogItemDescViewVector searchCatalogItems(List pCriteria)
        throws RemoteException;

    /**
     *  Gets store product
     *@param  pStoreId store id
     *@param  pItemId item di
     *@return  catalog product data
     *@exception  RemoteException
     */
   public ProductData getStoreProduct(int pStoreId, int pItemId)
   throws RemoteException;

    /**
     *Gets all major categories for store catalog
     * @param pStoreCatalogId store catalog id
     *@throws RemoteException
     */
    public ItemDataVector getStoreMajorCategories(int pStoreCatalogId)
    throws RemoteException;

    /**
     *  Gets store categories, which do not have parent categories or parent
     *  products.
     *
     *@param  pStoreCatalogId        the catalog identifier
     *@return                   CatalogCategoryDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public CatalogCategoryDataVector getStoreCatalogCategories(int pStoreCatalogId)
        throws RemoteException;

    public CatalogCategoryDataVector getAllStoreCatalogCategories(int pStoreCatalogId)
        throws RemoteException;

    public MultiproductViewVector getStoreMultiproducts(int pStoreId, String name)  throws RemoteException ;
    public MultiproductViewVector getStoreMultiproducts(int pStoreId)
        throws RemoteException;
    public IdVector getMultiproductItems(int storeCatalogId, int pMultiproductId) throws RemoteException ;
    public IdVector getMultiproductItems(int catalogId, int pMultiproductId, IdVector categoryId) throws RemoteException ;
    public Map getMultiproductToCategoryMap(int catalogId, Set pMultiproductIds) throws RemoteException ;

    /**
   * Removes the catalog from database with it stucture and associations
   * @param pCatalogId  the catalog id.
   * @param pContractId  the contract id.
   * @throws            RemoteException
   */
  public boolean canRemoveCatalogContract (int pCatalogId, int pContractId)
  throws RemoteException;

  /**
   * Prepares product template from another store product
   * @param pDestStoreId destination store id
   * @param pSrcStoreId source store id
   * @param pProduct product to import
   * @param user user name
   * @throws            RemoteException
   */
  public ProductData importStoreItem (int pDestStoreId, int pSrcStoreId, ProductData pProduct, String user)
  throws RemoteException;

  /** Gets catalog items
   * @param pStoreCatalogId store catalog id
   * @param pCatalogId catalog id
   * @param pOrderGuideId order guide id (all catalog order guides if 0);
   * @param pItemIdV item ids (all items if null)
   * @param pManufIdV manufacturer ids (no filter if null)
   * @param pDistIdV distributor ids (no filter if null)
   * @param pItemsTypeCd items type cd (Service or Product....))
   * @param user user name
   * @throws            RemoteException
   */
  public ItemCatalogAggrViewVector getItemCatalogMgrSet(int pStoreCatalogId, int pCatalogId, int pOrderGuideId,
                                                        IdVector pItemIdV,IdVector pManufIdV, IdVector pDistIdV,
                                                        String pItemsTypeCd) throws RemoteException ;

 /**
   * Gets aggregated item data
   * @param pStroreCatalogId store catalog id
   * @param pItemId item id
   * @param pDistIds vector of distributor ids
   * @param pCatalogIds vector of catalog ids
   * @param pAccountIds vector of account ids
   * @return a set of ItemCatalogAggrViewVector objects
   * @throws            RemoteException
   */
  public ItemCatalogAggrViewVector getItemCatalogMgrSet
          (int pStoreCatalogId, int pItemId, IdVector pDistIds,
           IdVector pCatalogIds, IdVector pAccountIds)
  throws RemoteException;

  /**
   * Gets aggregated item data
   * @param pStroreCatalogId store catalog id
   * @param pCatalogIds vector of catalog ids
    * @return a subset of store catalog categories found in the catalogs
   * @throws            RemoteException
   */
  public ItemDataVector getCatalogCategories
          (int pStoreCatalogId, IdVector pCatalogIds)
  throws RemoteException;
  
  public ItemDataVector getCatalogCategories
  (int pStoreCatalogId, IdVector pCatalogIds, boolean allowMixedCategoryAndItemUnderSameParent)
  throws RemoteException;

  /**
   * Get all the CostCenters for a given account catalog
   * @param pCatalogId the Id of the CostCenter's Catalog.
   * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
   * @return a <code>CostCenterDataVector</code> with all CostCenters.
   * @exception RemoteException if an error occurs
   */
  public CostCenterDataVector getAllCostCenters(int pCatalogId, int pOrder)
  throws RemoteException;


  /**
     * Get all the CostCenterAssoces for a given account catalog
     *
     * @param pCatalogId
     *            the Id of the CostCenter's Catalog.
     * @return a <code>CostCenterAssocDataVector</code> with all
     *         CostCenterAssoces.
     * @exception RemoteException
     *                if an error occurs
     */
    public CostCenterAssocDataVector getAllCostCenterAssoces(int pCatalogId)
            throws RemoteException;


    /**
     * Update CostCenterAssoces
     *
     * @param pCostCenterAssocDV
     *            CostCenterAssoces for update.
     * @exception RemoteException
     *                if an error occurs
     */
    public void updateCostCenterAssoces(
            CostCenterAssocDataVector pCostCenterAssocDV)
            throws RemoteException;

    /**
     * Get all the CategoryName-CostCenterId pair for subcatalogs of a given account catalog
     * @param pCatalogId the Id of the CostCenter's Catalog.
     * @return a set of CategoryToCostCenterView objects
     * @exception RemoteException if an error occurs
     */
    public CategoryToCostCenterViewVector getAllCategoryToCostCenters(int pCatalogId)
     throws RemoteException;

    public CategoryToCostCenterViewVector getCatalogCategoryToCostCenters(int pCatalogId)
     throws RemoteException;

	 /**
     * Get all the CatalogId-ItemId-SkuNum-CostCenterId sets
     * for subcatalogs of a given account catalog
     * @param pCatalogId the Id of the CostCenter's Catalog.
     * @return a set of UniversalDAO.dbrow objects
     * @exception RemoteException if an error occurs
     */
    public ArrayList getAllItemToCostCenters(int pCatalogId)
    throws RemoteException;

    public ArrayList getCatalogItemToCostCenters(int pCatalogId)
    throws RemoteException;
    /**
     *Gets all the fiscal calenders for the accounts sharing the account catalog
     * @param pCatalogId - the catalog id.
     * @exception RemoteException if an error occurs
     */
    public CatalogFiscalPeriodViewVector getFiscalInfo(int pCatalogId)
    throws RemoteException;

  /**
   *Gets catalogs for the user
   * @param pUserId - the user id.
   * @param pStore Id - the store id
   * @param pFilter - catalog name pattern or catalog id
   * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
   * @exception RemoteException if an error occurs
   */
  public CatalogDataVector getUserCatalogs(int pUserId, int pStoreId,
          String pFilter, String pFilterType)
  throws RemoteException;

    /**
     * Gets catalogs for the user
     *
     * @param pUserId     - the user id.
     * @param pAccountIds - the store id
     * @param pFilter     - catalog name pattern or catalog id
     * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
     * @return user catalogs
     * @throws RemoteException if an error occurs
     */
    public CatalogDataVector getUserCatalogs(int pUserId,
                                             IdVector pAccountIds,
                                             String pFilter,
                                             String pFilterType) throws RemoteException;

  /**
   *Gets catalogs for the user
   * @param pStore Id the store id
   * @param pFilter catalog name pattern or catalog id
   * @param pFilterType can be: "id", "nameContains"(default) or "nameBegins"
   * @param pCostCenterID cost center id (if greater than 0)
   * @param pGetInactiveFl filters out inactive catalogs if false
   * @exception RemoteException if an error occurs
   */
  public CatalogDataVector getAccountCatalogs(int pStoreId,
          String pFilter, String pFilterType,
          int pCostCenterId,  boolean pGetInactiveFl)
  throws RemoteException;


    /**
     * Gets all service for store catalog
     *
     * @param pStoreCatalogId store catalog id
     * @throws RemoteException if an error occurs
     * @return  ItemDataVector
     */
    public ItemDataVector getStoreServices(int pStoreCatalogId) throws RemoteException;

    /**
      * Gets all service by criteria
      *
      * @param  criteria  ServiceSearchCriteria
      * @throws RemoteException if an error occurs
      * @return ServiceViewVector
      */
     public ServiceViewVector getServicesViewVector(ServiceSearchCriteria criteria) throws RemoteException;

     /**
     * Gets  service data by id
     *
     * @param  itemId  service indentifier
     * @throws RemoteException if an error occurs
     * @return ServiceData
     */
    public ServiceData getServiceData(int itemId) throws RemoteException ;

    /**
     * Gets  service ids  for the catalog site
     * @param pSiteId  site id
     * @param pCatalogId    catalog id
     * @param onlyForActiveCatalogs flag for the search services ids in only active catalog
     * @throws RemoteException if an error occurs
     * @throws com.cleanwise.service.api.util.DataNotFoundException   if an data not found
     * @return ids

     */
    public IdVector getServicesIdsBySiteCatalog(int pSiteId,int pCatalogId,boolean onlyForActiveCatalogs) throws RemoteException, DataNotFoundException;


    /**
     * Gets  service data by id
     *
     * @param itemId service indentifier
     * @param catalogId indentifier
     * @return ServiceData
     * @throws RemoteException if an error occurs
     */
    public ServiceData getServiceData(int itemId, int catalogId) throws RemoteException;

    /**
     * Gets catalog menu
     *
     * @param pCatalogId         the catalog identifier
     * @param pFilterCategoryIds filter
     * @param pPatternLink       pattern link (example: "../store/shop.do?action=openCategory&categoryKey={0}")
     * @return MenuItemView menu data
     * @throws RemoteException Required by EJB 1.0
     */
    public MenuItemView getCatalogMenu(int pCatalogId, IdVector pFilterCategoryIds, String pPatternLink) throws RemoteException;

    public ProductDataVector getCategoryChildProducts(int pCatalogId, int pCategoryId) throws RemoteException;

    public ProductDataVector getCategoryChildProducts(int pCatalogId, int pCategoryId, int siteId) throws RemoteException;

    public CatalogDataVector getCatalogsByCritAndIds(EntitySearchCriteria entitySearchCriteria, IdVector catalogsIdsToReturn) throws RemoteException;

    public CostCenterAssocDataVector getCatalogCostCenterAssoc(int catalogId, int costCenterId) throws RemoteException;

    public CostCenterData getCostCenterById(int costCenterId) throws RemoteException;

	public ItemDataVector getStoreCategories(int pStoreId) throws RemoteException;

    public CostCenterDataVector getCatalogCostCenters(int pCatalogId, IdVector pAvailableCostCentertIds, boolean pOrderByName) throws RemoteException;
    
    /**
     * Gets List of Catalog Structure Ids.
     * @param distributorId - Id of Distributor.
     * @return List<Integer>
     * @throws RemoteException
     */
    public List<Integer> getCatalogStructureIds(int distributorId)throws RemoteException;
    

    /**
     *  Gets the list of bus entity id that associated with 
     *  catalog pCatalogId and match bus entity type of pBusEntityTypeCd
     *
     *@param  pCatalogId        Description of Parameter
     *@param  pBusEntityTypeCd  Description of Parameter
     *@return                   The IdVector value
     */
    public IdVector getBusEntityIdCollection(int pCatalogId, String pBusEntityTypeCd) throws RemoteException;
    
    public HashMap getItemTotalPerAcctCatalog(int pUserId, String pBegDate, String pEndDate, List pOrderStatusList) throws RemoteException;
    public HashMap getCategoryItemsTotal(int pUserId, String pBegDate, String pEndDate, List pOrderStatusList, List sites, List mfgs) throws RemoteException;
    public ArrayList getLevel1Categories(int pStoreCatalogId) throws RemoteException;
    public ArrayList getNextLevelCategories(int pPrevCategoryId, int pStoreCatalogId) throws RemoteException;
    public String verifyOrderLocales(int pUserId, String pBegDate, String pEndDate, List pOrderStatusList,
    		List sites) throws RemoteException;
    public HashMap getMfgsFromAccountCatalogs(List sites, int pUserId) throws RemoteException;
    
    public List getAcctCatalogCategories(List pAccts) throws RemoteException;
    
    public List getAcctCatalogCategoriesForUser(int pUserId) throws RemoteException;
    public boolean doesCategoryContainProduct(int catalogId, int categoryId)  throws RemoteException;
    public boolean isLowestLevelCategory(int catalogId, int categoryId)  throws RemoteException;
    public CatalogCategoryDataVector getLowestStoreCatalogCategories(int pStoreCatalogId)
    throws RemoteException;
    public int resetCostCenters( String user, String tempDbUser, IdVector excludeShoppingCatalogList) throws  RemoteException ;

}
