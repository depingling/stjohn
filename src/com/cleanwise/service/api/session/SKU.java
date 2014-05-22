package com.cleanwise.service.api.session;

/**
 * Title:        SKU
 * Description:  Remote Interface for SKU Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving SKU information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;

public interface SKU extends javax.ejb.EJBObject
{

  /**
   * Adds the SKU information values to be used by the request.
   * @param pSKU  the sku data.
   * @param request  the sku request data.
   * @return new SKURequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKURequestData addSKU(SKUData pSKU,
                SKURequestData request)
      throws RemoteException;

  /**
   * Updates the sku information values to be used by the request.
   * @param pUpdateSKUData  the sku data.
   * @param pSKUId the sku identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateSKU(SKUData pUpdateSKUData,
                int pSKUId)
      throws RemoteException;

  /**
   * Adds the SKU meta information values to be used by the request.
   * @param pSKUMeta  the sku meta data.
   * @param request  the sku meta request data.
   * @return new SKUMetaRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUMetaRequestData addSKUMeta(SKUMetaData pSKUMeta,
                SKUMetaRequestData request)
      throws RemoteException;

  /**
   * Updates the sku meta information values to be used by the request.
   * @param pUpdateSKUMetaData  the sku meta data.
   * @param pSKUMetaId the sku meta identifier.
   * @param pNameId the name identifier.
   * @param pValueId the value identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateSKUMeta(SKUMetaData pUpdateSKUMetaData,
                int pSKUId, int pNameId, int pValueId)
      throws RemoteException;

  /**
   * Adds the meta information values to be used by the request.
   * @param pMeta  the meta data.
   * @param request  the meta request data.
   * @return new MetaRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public MetaRequestData addMeta(MetaData pMeta,
                MetaRequestData request)
      throws RemoteException;

  /**
   * Updates the meta information values to be used by the request.
   * @param pUpdateMetaData  the MetaData meta data.
   * @param pNameId the name identifier.
   * @param pValueId the value identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateMeta(MetaData pUpdateMetaData,
                int pNameId, int pValueId)
      throws RemoteException;

  /**
   * Adds the sku content information values to be used by the request.
   * @param pSKUContent  the sku content data.
   * @param request  the sku  content request data.
   * @return new SKUContentRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUContentRequestData addSKUContent(SKUContentData pSKUContent,
                SKUContentRequestData request)
      throws RemoteException;

  /**
   * Updates the SKU content information values to be used by the request.
   * @param pUpdateSKUContentData  the sku content data.
   * @param pSKUId the sku identifier.
   * @param pContentId the content identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateSKUContent(SKUContentData pUpdateSKUContentData,
                int pSKUId, int pContentId)
      throws RemoteException;

  /**
   * Adds the sku price information values to be used by the request.
   * @param pSKUPrice  the sku price data.
   * @param request  the sku  price request data.
   * @return new SKUPriceRequestData
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUPriceRequestData addSKUPrice(SKUPriceData pSKUPrice,
                SKUPriceRequestData request)
      throws RemoteException;

  /**
   * Updates the SKU price information values to be used by the request.
   * @param pUpdateSKUPriceData  the sku price data.
   * @param pProductId the product identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateSKUPrice(SKUPriceData pUpdateSKUPriceData,
                int pProductId)
      throws RemoteException;



}
