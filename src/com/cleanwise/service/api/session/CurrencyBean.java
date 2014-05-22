package com.cleanwise.service.api.session;

/**
 * Title:        CurrencyBean
 * Description:  Bean implementation for Currency Stateless Session Bean
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
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.process.ProcessException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.APIAccess;
import java.sql.*;

public class CurrencyBean extends UtilityServicesAPI
{
  /**
   *
   */
  public CurrencyBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Get all currencies supported.
   * @return set of CurrencyData objects
   * @throws RemoteException Required by EJB 1.0
   */
  public CurrencyDataVector getAllCurrencies()
  throws RemoteException {
    
    CurrencyDataVector currencyDV;
    Connection conn = null;
    
    try {
      conn = getConnection();
      currencyDV = CurrencyDataAccess.selectAll(conn);
      return currencyDV;
    } catch ( Exception e ) {
    	throw processException(e);
    } finally {
      try {
        if (conn != null) conn.close();
      }catch (Exception ex) {}
    }
  }

  public int getDecimalPlacesForLocale(String pLocale) throws RemoteException{
	  
	  Connection conn = null;  
	  try{
		  conn = getConnection();
		  DBCriteria crit = new DBCriteria();
		  crit.addEqualTo(CurrencyDataAccess.LOCALE, pLocale);
		  CurrencyDataVector currDV = CurrencyDataAccess.select(conn, crit);
		  if(currDV!=null && currDV.size()>0){
			  CurrencyData currD = (CurrencyData)currDV.get(0);
			  return currD.getDecimals();
		  }
		  
	  }catch ( Exception e ) {
		  throw processException(e);
	  } finally {
		  try {
			  if (conn != null) conn.close();
		  }catch (Exception ex) {}
	  }
	  return -1;
	  
  }
  
  
  
  /**
   * Saves currency data object
   * @return currency data object
   * @throws RemoteException Required by EJB 1.0
   */
  public CurrencyData saveCurrency(CurrencyData currency)
  throws RemoteException {
    
    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CurrencyDataAccess.LOCALE,currency.getLocale());
      dbc.addEqualTo(CurrencyDataAccess.GLOBAL_CODE,currency.getGlobalCode());
      CurrencyDataVector currencyDV = CurrencyDataAccess.select(conn,dbc);
      if(currencyDV.size()==0) {
        currency = CurrencyDataAccess.insert(conn,currency);
      } else {
        CurrencyData curr = (CurrencyData) currencyDV.get(0);
        currency.setCurrencyId(curr.getCurrencyId());
        currency.setAddBy(curr.getAddBy());
        currency.setAddDate(curr.getAddDate());
        CurrencyDataAccess.update(conn,currency);
        for(int ii=1; ii<currencyDV.size(); ii++) {
          curr = (CurrencyData) currencyDV.get(ii);
          CurrencyDataAccess.remove(conn,curr.getCurrencyId());
          
        }
      }
      return currency;
    } catch ( Exception e ) {
    	throw processException(e);
    } finally {
      try {
        if (conn != null) conn.close();
      }catch (Exception ex) {}
    }
  }

}
