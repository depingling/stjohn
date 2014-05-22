package com.cleanwise.service.api.session;

/**
 * Title:        Currency
 * Description:  Remote Interface for Currency Stateless Session Bean
 * Purpose:      Provides access to currency methods
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.Locale;
import com.cleanwise.service.api.value.*;


public interface Currency extends javax.ejb.EJBObject
{

 

  /**
   * Get all currencies supported.
   * @return set of CurrencyData objects
   * @throws RemoteException Required by EJB 1.0
   */
  public CurrencyDataVector getAllCurrencies()
  throws RemoteException;

  /**
   * Saves currency data object
   * @return currency data object
   * @throws RemoteException Required by EJB 1.0
   */
  public CurrencyData saveCurrency(CurrencyData currency)
  throws RemoteException;
  
  /**
   * Returns the number of decimals to use for a given locale.
   * @param pLocale
   * @return
   * @throws RemoteException
   */
  public int getDecimalPlacesForLocale(String pLocale) throws RemoteException;
  
  }
