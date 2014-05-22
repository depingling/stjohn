package com.cleanwise.service.api.session;

/**
 * Title:        CWLocale
 *        - Locale is the class name of java.util.Locale, we can't use it.
 * Description:  Remote Interface for Locale Stateless Session Bean
 * Purpose:      Provides access to locale methods.
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
import com.cleanwise.service.api.util.DataNotFoundException;

/**
 * The <code>CWLocale</code> interface provide the capabilities needed
 * to get the collection of locales supported of a single locale object.
 *
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public interface CWLocale extends javax.ejb.EJBObject
{

  /**
   * Get all Locales supported.
   * @return new LocaleDataVector()
   * @exception            RemoteException Required by EJB 1.0
   */
  public LocaleDataVector getAllLocalesCollection()
      throws RemoteException;

  /**
   * Returns the locale data for the name.
   * @param pLocaleName a <code>String</code> value
   * @return LocaleData
   * @exception RemoteException Required by EJB 1.0
   * @exception DataNotFoundException if the locale is not found
   */
  public LocaleData getLocale(String pLocaleName)
      throws RemoteException, DataNotFoundException;

}
