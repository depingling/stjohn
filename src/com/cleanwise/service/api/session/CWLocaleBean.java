package com.cleanwise.service.api.session;

/**
 * Title:        CWLocaleBean
 * Description:  Bean implementation for CWLocale Stateless Session Bean
 * Purpose:      Provides access to locale methods.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.sql.*;
import java.rmi.*;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.APIAccess;

/**
 * The <code>CWLocaleBean</code> implements
 * the <code>CWLocale</code> interface.
 *
 * @author durval
 * @see com.cleanwise.service.api.session.CWLocale
 */
public class CWLocaleBean extends UtilityServicesAPI
{
  /**
   * Creates a new <code>CWLocaleBean</code> instance.
   *
   */
  public CWLocaleBean() {}

  /**
   * Default <code>ejbCreate</code> method.
   *
   * @exception CreateException if an error occurs
   * @exception RemoteException if an error occurs
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Get all Locales supported.
   * @return new LocaleDataVector()
   * @throws RemoteException Required by EJB 1.0
   */
  public LocaleDataVector getAllLocalesCollection()
      throws RemoteException {

      LocaleDataVector rtn;
      Connection conn = null;

      try {
	  conn = getConnection();
	  rtn = LocaleDataAccess.selectAll(conn);
      } catch ( Exception e ) {
	  String msg = "getLocaleCollection error: " + e.getMessage();
	  logError(msg);
	  throw new RemoteException(msg);
      } finally {
	  try {if (conn != null) conn.close();} catch (Exception ex) {}
      }

      return rtn;
  }

  /**
   * Returns the locale data for the name.
   * @param pLocaleName a <code>String</code> value
   * @return LocaleData
   * @exception RemoteException Required by EJB 1.0
   * @exception DataNotFoundException the locale entry was not found
   */
  public LocaleData getLocale(String pLocaleName)
      throws RemoteException, DataNotFoundException {

      LocaleData ld = null;

      StringTokenizer st = new StringTokenizer(pLocaleName, "_");
      // Get the language part of the token string.
      String lang = st.nextToken();
      // Get the country code (cc) part of the token string.
      String cc = st.nextToken();

      Connection conn = null;

      try {
	  conn = getConnection();
	  DBCriteria crit = new DBCriteria();
	  crit.addEqualTo(LocaleDataAccess.LANGUAGE_CD, lang); 
	  crit.addEqualTo(LocaleDataAccess.COUNTRY_CD, cc); 
	  LocaleDataVector ldv = LocaleDataAccess.select(conn, crit);
	  if (ldv.size() > 0) {
	      ld = (LocaleData)ldv.get(0);
	  }
      } catch ( Exception e ) {
	  String msg = this.getClass().getName() +
	      ".getLocale error: " + e.getMessage();
	  logError(msg);
	  throw new RemoteException(msg);
      } finally {
	  try {if (conn != null) conn.close();} catch (Exception ex) {}
      }

      if (ld == null) {
	  throw new 
	      DataNotFoundException(this.getClass().getName() + 
				    ".getLocale error: no entry found for " +
				    pLocaleName );
      }
	  
      return ld;
  }
}





