package com.cleanwise.service.api.session;

/**
 * Title:        SKUInformationBean
 * Description:  Bean implementation for SKUInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating SKU information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;

public class SKUInformationBean extends BusEntityServicesAPI
{
  /**
   *
   */
  public SKUInformationBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Gets the SKU vector values to be used by the request
   * @param pCatalogId  the catalog identifier
   * @param pProductId  the product identifier
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollection(int pCatalogId, int pProductId)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets the SKU vector values to be used by the request
   * @param pProductId  the product identifier
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollectionByProduct(int pProductId)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets the SKU vector values to be used by the request
   * @param pContractId  the contract identifier
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollectionByContract(int pContractId)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets the SKU vector values to be used by the request
   * @param pSKUShortDesc  the sku name or short description
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollectionByShortDesc(String pSKUShortDesc)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets the SKU vector values to be used by the request
   * @param pSizeShortDesc  the sku size short description
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollectionBySize(String pSizeShortDesc)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets the SKU vector values to be used by the request
   * @param pPackCd  the sku pack code
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollectionByPack(String pPackCd)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets the SKU vector values to be used by the request
   * @param pNameId  the sku meta name identifier
   * @param pNameId  the sku meta value identifier
   * @return SKUDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUDataVector getSKUsCollectionByMeta(int pNameId, int pValueId)
      throws RemoteException
  {
    return new SKUDataVector();
  }

  /**
   * Gets SKU information values to be used by the request.
   * @param pSKUId  the sku identifier
   * @return SKUData
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUData getSKU(int pSKUId)
      throws RemoteException
  {
    return new SKUData();
  }

  /**
   * Gets the SKU content vector values to be used by the request.
   * @param pSKUId  the sku identifier
   * @return SKUContentDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUContentDataVector getSKUContentCollection(int pSKUId)
      throws RemoteException
  {
    return new SKUContentDataVector();
  }

  /**
   * Gets sku content information values to be used by the request.
   * @param pSKUId  the sku identifier
   * @param pContentId  the content identifier
   * @return SKUContentData
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUContentData getSKUContentDetail(int pSKUId, int pContentId)
      throws RemoteException
  {
    return new SKUContentData();
  }

  /**
   * Gets sku price information values to be used by the request.
   * @param pSKUId  the sku identifier
   * @param pPriceId  the price identifier
   * @return SKUPriceData
   * @throws            RemoteException Required by EJB 1.0
   */
  public SKUPriceData getSKUPrice(int pSKUId, int pPriceId)
      throws RemoteException
  {
    return new SKUPriceData();
  }


}
