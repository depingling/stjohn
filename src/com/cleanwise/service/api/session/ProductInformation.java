package com.cleanwise.service.api.session;

/**
 * Title:        ProductInformation
 * Description:  Remote Interface for ProductInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating product information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.util.DataNotFoundException;

public interface ProductInformation extends javax.ejb.EJBObject
{
  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException;

  /**
   * Gets the array-like product vector values to be used by the request.
   * @param pCatalogId  the catalog identifier
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByCatalog(int pCatalogId)
      throws RemoteException;
  /*
   * Gets the array-like product vector values to be used by the request
   * (search by product code).
   * @param pProductCd  the product code
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
  public ProductDataVector getProductsCollectionByProductCode(String pProductCd)
      throws RemoteException;
   */
  /**
   * Gets the array-like product vector values to be used by the request
   * (search by product Id).
   * @param pProductId  the product identifier
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByProductId(int pProductId)
      throws RemoteException;

  /*
   * Gets the array-like product vector values to be used by the request
   * (Search by product type).
   * @param pProductTypeCd  the product type code
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
  public ProductDataVector getProductsCollectionByType(String pProductTypeCd)
      throws RemoteException;
   */
  /**
   * Gets the array-like product vector values to be used by the request.
   * (Search by product UPC).
   * @param pProductUPC  the product UPC value
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByUPC(String pProductUPC)
      throws RemoteException;

  /*
   * Gets the array-like product vector values to be used by the request.
   * (Search by product name).
   * @param pProductShortDesc  the product name or short description
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
  public ProductDataVector getProductsCollectionByProductShortDesc(String pProductShortDesc)
      throws RemoteException;
  */
  /**
   * Gets the array-like product vector values to be used by the request.
   * (Search by category).
   * @param pCategoryId  the category identifier
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByCategory(int pCategoryId)
      throws RemoteException;

  /**
   * Gets the array-like product vector values to be used by the request.
   * (Search by keyword).
   * @param pKeyword  the keyword
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByKeyword(String pKeyword)
      throws RemoteException;

  /**
   * Gets the array-like product vector values to be used by the request.
   * (Search by product meta).
   * @param pNameId the product meta name identifier
   * @param pValueId the product meta value identifier
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByMeta(String pName, String pValue)
      throws RemoteException;

  /**
   * Gets the array-like product vector values to be used by the request.
   * (Search by business entity identifier, e.g.manufacturer, distributor).
   * @param pBusEntityId  the business entity identifier
   * @return ProductDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductDataVector getProductsCollectionByBusEntity(int pBusEntityId)
      throws RemoteException;

  /**
   * Gets product information values to be used by the request.
   * @param pProductId  the product identifier
   * @return ProductData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductData getProduct(int pProductId)
      throws RemoteException;

  /**
   * Gets the Product content vector values to be used by the request.
   * @param pProductId  the product identifier
   * @return ProductContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContentDataVector getProductContentCollection(int pProductId)
      throws RemoteException;

  /*
   * Gets product content information values to be used by the request.
   * @param pProductId  the product identifier
   * @param pContentId  the content identifier
   * @return ProductContentData
   * @throws            RemoteException Required by EJB 1.0
   *
  public ProductContentData getProductContentDetail(int pProductId, int pContentId)
      throws RemoteException;
  */
  /**
   * Gets product sku information values to be used by the request.
   * @param pProductId  the product identifier
   * @return ProductContentData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductSKUData getProductSKU(int pProductId)
      throws RemoteException;

  /**
   * Gets product price information values to be used by the request.
   * @param pProductId  the product identifier
   * @param pPriceId  the price identifier
   * @return ProductContentData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductPriceData getProductPrice(int pProductId, int pPriceId)
      throws RemoteException;

  /**
   * Gets product information values to be used by the request.
   * @param pProductId  the product identifier
   * @return ProductContentData
   * @throws            RemoteException Required by EJB 1.0
   */
  public ProductData getProductInfo(int pProductId)
      throws RemoteException;

  /**
   * Gets category information values to be used by the request.
   * @param pCategoryId  the category identifier
   * @return CatalogCategoryData
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogCategoryData getCatalogCategory(int pCategoryId)
      throws RemoteException, DataNotFoundException;
  /**
   * Gets sub categoris.
   * @param pCategoryId  the category identifier
   * @return CatalogCategoryDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogCategoryDataVector getSubCategories(int pCategoryId)
      throws RemoteException;
  /**
   * Gets top level catalog category.
   * @param pCatalogId  the catalog identifier
   * @return CatalogCategoryData
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogCategoryData getTopProductCategory(int pCatalogId)
      throws RemoteException, DataNotFoundException;

   /**
     * Gets the array-like item id vector values to be used by the request.
     * (Search by managed item parent).
     * @param pParentItemId  the managed item parent identifier
     * @return IdVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public IdVector getItemIdCollectionByManagedParentItem(int pParentItemId)
        throws RemoteException;

    /**
     * Gets the array-like item id vector values to be used by the request.
     * (Search by managed item child).
     * @param pChildItemId  the managed item child identifier
     * @return IdVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public IdVector getItemIdCollectionByManagedChildItem(int pChildItemId)
        throws RemoteException;
    
    public IdVector getItemIdCollectionByManagedItemBetweenStores(int itemId)
        throws RemoteException;
    
    /**
     * remove item assoc for linked items of managed item.
     * (Search by managed item parent).
     * @param pParentItemId  the managed item parent identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void removeManagedItemLinksByManagedParentItem(int pParentItemId)
        throws RemoteException;

    /**
     * remove item assoc for linked items of managed item.
     * (Search by managed item child).
     * @param pParentItemId  the managed item child identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void removeManagedItemLinksByManagedChildItem(int pChildItemId)
        throws RemoteException;

    public void removeItemsLinkBetweenStores(int itemId, int linkedItemId)
    	throws RemoteException;

    public void removeItemsLinkBetweenStores(int itemId)
		throws RemoteException;

    /**
     * add item assoc for linked item of managed item.
     * (Search by managed item parent).
     * @param pParentItemId  the managed item parent identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void addManagedItemLinkByManagedParentItem(int pItemId, int pParentItemId, String user)
        throws RemoteException;

    /**
     * add item assoc for linked item of managed item.
     * (Search by managed item child).
     * @param pParentItemId  the managed item child identifier
     * @throws            RemoteException Required by EJB 1.0
     */
    public void addManagedItemLinkByManagedChildItem(int pItemId, int pChildItemId, String user)
        throws RemoteException;

    public void addItemsLinkBetweenStores(int itemId, int linkedItemId, int linkedCatalogId, String user)
        throws RemoteException;

    /**
     * Gets a list of BusEntityData objects based off the supplied search criteria
     * object
     *
     * @param pCrit  Description of the Parameter
     * @param typeCd type cd
     * @return bus_entity collection
     * @throws RemoteException if an error occurs
     */
    public BusEntityDataVector getBusEntityByCriteria(BusEntitySearchCriteria pCrit, String typeCd) throws RemoteException;

    /**
     * Gets all parent categoris.
     * @param   pCategoryId  the category identifier
     * @return  IdVector
     * @throws  RemoteException Required by EJB 1.0
     */
    public IdVector getAllParentCategories(int pCategoryId) throws RemoteException;
    
    public ProductDataVector getProductsCollectionByItemIds(IdVector itemIds)
    throws RemoteException;

}
