package com.cleanwise.service.api.session;

/**
 * Title:        ThruStoreErp
 * Description:  Remote Interface for ThruStoreErp Stateless Session Bean
 * Purpose:      Provides access to the methods for ThruStoreErp EJB interface
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import java.rmi.*;
import java.util.ArrayList;
import com.cleanwise.service.api.value.*;

public interface ThruStoreErp extends javax.ejb.EJBObject
{
 /**
  * Gets new orders and generates pos
  */
  //public void generatePos(String pUser)
  //throws RemoteException, Exception;
  
 /**
  * Gets list of erp po numbers for the order
  * @param pOrderId record id
  * @return  List of erp po numbers
  * @throws  RemoteException
  */
  public ArrayList getOrderErpPos(int pOrderId)
  throws RemoteException;
  
 /**
  * Gets new valid orders which have CLW_JCI erp system
  * @return the vector of order ids
  * @throws   RemoteException, LawsonException
  */

  public IdVector getNewThruStoreOrderIds()
  throws RemoteException;
  
 /**
 * Verifies po against the order, cancell wrong pos, generates new pos
 * @param pOrderId order id in CLW_ORDER_TABLE
 * @param pUser user login name
 * @throws   RemoteException
  */
  public void processNewThruStoreOrder(int pOrderId, String pUser)
  throws RemoteException;
  
 /**
 * Gets vendor invoices and generates customer invoces 
 * @param pUser the user name
 * @throws   RemoteException
  */
  public void generateCustomerInvoice(String pUser,int pInvoiceDistId) 
  throws RemoteException;
  
  

}



