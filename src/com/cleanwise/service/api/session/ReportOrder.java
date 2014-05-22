package com.cleanwise.service.api.session;

/**
 * Title:        ReportOrder
 * Description:  Remote Interface for Lawson Backfill Stateless Session Bean
 * Purpose:      Provides access to the methods for lawson Backfill EJB interface
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.math.BigDecimal;

public interface ReportOrder extends javax.ejb.EJBObject
{
 /**
   * Gets list of generic report categories
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   //public ArrayList getGenericReportCategories() 
   //throws RemoteException;

   /**
   * Gets list of generic report names
   * @param pCategory category name. If null or empty returns all report names
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   //public ArrayList getGenericReportNames(String pCategory) 
   //throws RemoteException;
 
   /**
   * Gets list of generic report criteria control names. Picks up reprort by report name
   * and category if it is not null. If category is null and more than 1 reports found
   * uses default category "CUSTOMER" to resolve multiplicity
   * @param pCategory report category. 
   * @param pName report name.
   * @return collection of  GenericReportControlView objects
   * @throws   RemoteException Required by EJB 1.0
   */
   //public GenericReportControlViewVector getGenericReportControls(String pCategory, String pName) 
   //throws RemoteException;

   /**
   * Gets list of generic report criteria control names
   *
   * @param pCategory report category
   * @param pName report name.
   * @param pParams report parameters
   * @return report table
   * @throws   RemoteException Required by EJB 1.0
   */
   //public GenericReportResultView processGenericReport(String pCategory, String pName, Map pParams) 
   //throws RemoteException;

   /**
   * Gets a list of generic report tables 
   *
   * @param pCategory report category
   * @param pName report name.
   * @param pParams report parameters
   * @return vector of report tables
   * @throws   RemoteException Required by EJB 1.0
   */
   //public GenericReportResultViewVector processGenericReportMulti(String pCategory, String pName, Map pParams) 
   //throws RemoteException;

}



