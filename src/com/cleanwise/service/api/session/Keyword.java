package com.cleanwise.service.api.session;

/**
 * Title:        Keyword
 * Description:  Remote Interface for Keyword Stateless Session Bean
 * Purpose:      Provides access to keyword add and retrieval of information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;
import com.cleanwise.service.api.value.*;

public interface Keyword extends javax.ejb.EJBObject
{

  /**
   * Gets the keywords for the domain and locale. Adds default domain data for the locale
   * @param pDomainName domain name
   * @param pLocale locale
   * @return Hashtable
   * @throws            RemoteException Required by EJB 1.0
   *//*
   public Hashtable getKeys(String pDomainName, String pLocale) 
   throws RemoteException;
*/
   /**
   * Gets the keyword vector information values to be used by the request.
   * @param pKeyword the keyword.
   * @return KeywordDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordDataVector getKeywordValuesCollection(String pKeyword)
      throws RemoteException;

  /**
   * Gets the keyword vector information values to be used by the request.
   * @param pKeyword the keyword.
   * @return KeywordDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordDataVector getKeywordValuesCollectionByType(String pKeyword)
      throws RemoteException;

  /**
   * Gets the keyword information values to be used by the request.
   * @param pKeyword the keyword.
   * @return KeywordData
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordData getKeywordValue(String pKeyword)
      throws RemoteException;

  /**
   * Adds the keyword information values to be used by the request.
   * @param pKeyword  the keyword data.
   * @param request  the keyword request data.
   * @return new KeywordRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordRequestData addKeyword(KeywordData pKeyword,
                KeywordRequestData request)
      throws RemoteException;

  /**
   * Updates the keyword information values to be used by the request.
   * @param pUpdateKeywordData  the keyword data.
   * @param pKeyword the keyword.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateKeyword(KeywordData pUpdateKeywordData,
                String pKeyword)
      throws RemoteException;

  /**
   * Removes all messages for found store-locale pairs and adds provided messages if name is not empty
   * object with empty name could be used to remove all messages for the store-locale
   * @param pMessages set of MessageResourceData objects
   * @throws            RemoteException Required by EJB 1.0
   */
   public void loadMessages(MessageResourceDataVector pMessages) 
   throws RemoteException;


}
