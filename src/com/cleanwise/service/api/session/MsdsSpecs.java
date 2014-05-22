package com.cleanwise.service.api.session;

/**
 * Title:        ShoppingServices
 * Description:  Remote Interface for MsdsSpecs Stateless Session Bean
 * Purpose:      Provides access to the methods for msds & specs documents
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.math.BigDecimal;

public interface MsdsSpecs extends javax.ejb.EJBObject
{
  /**
   * Adds item document information to user's document closet, if it was not there
   * @param pCatalogId the catalog identifier
   * @param pUserId the user identifier
   * @param pItemIds the vector of item identifiers
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void addItemsToDocCloset(int pCatalogId, int pUserId, IdVector pItemIds, String pDocClosetType, String pUser)
      throws RemoteException;
  /**
   * Removes item document information to user's document closet, if it was not there
   * @param pCatalogId the catalog identifier
   * @param pUserId the user identifier
   * @param pItemIds the vector of item identifiers
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @param pUser the user login name
   * @throws            RemoteException Required by EJB 1.0
   */
  public void removeItemsFromDocCloset(int pCatalogId, int pUserId, IdVector pItemIds, String pDocClosetType, String pUser)
      throws RemoteException;

  /**
   * Gets items, which are in document closet
   * @param pCatalogId the catalog identifier
   * @param pUserId the user identifier
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @return item ids, which where stored in users's documdt closet
   * @throws            RemoteException Required by EJB 1.0
   */
  public IdVector getItemsDocCloset(int pCatalogId, int pUserId, String pDocClosetType)
      throws RemoteException;

  /**
   * Gets items, which were ordered during last year for the site and with have documents of the type
   * @param pSiteId the site identifier
   * @param pDocType the document type (MSDS, SPEC, DED)
   * @return item ids
   * @throws            RemoteException Required by EJB 1.0
   */
  public IdVector getItemsDocCloset(int pSiteId, String pDocClosetType)
      throws RemoteException;

}

