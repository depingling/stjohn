package com.cleanwise.service.api.session;

/**
 * Title:        OrderProcessingServiceBean
 * Description:  Bean implementation for OrderProcessingService Stateless Session Bean
 * Purpose:      Provides order processing related services to the server-side presentation layer to perfrom checkout processing.
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

public class OrderProcessingServiceBean extends OrderProcessingServicesAPI
{
  /**
   *
   */
  public OrderProcessingServiceBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Adds the item information values into shopping cart.
   * @param pOrderId  the order identifier.
   * @param pShoppingCart  the shopping cart data.
   * @param pBusEntityId  the customer identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void finalizeOrder(int pOrderId, ShoppingCartData pShoppingCart,
                int pBusEntityId)
      throws RemoteException
  {
  }

  /**
   * determines if the payment is required.
   * @param pOrderId  the order identifier
   * @return true if the payment is required.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean isPaymentRequired(int pOrderId)
      throws RemoteException
  {
    return true;
  }

  /**
   * determines if the ref PO is required.
   * @param pOrderId  the order identifier
   * @return true if the ref PO is required.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean isRefPORequired(int pOrderId)
      throws RemoteException
  {
    return true;
  }

  /**
   * determines if the WF is required.
   * @param pOrderId  the order identifier
   * @return true if the WF is required.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean isWFRequired(int pOrderId)
      throws RemoteException
  {
    return true;
  }

  /**
   * Starts the checking out.
   * @param pShoppingCart  the shopping cart data.
   * @param pBusEntityId  the customer identifier.
   * @return OrderData
   * @throws            RemoteException Required by EJB 1.0
   */
  public OrderData startCheckOut(ShoppingCartData pShoppingCart,
                int pBusEntityId)
      throws RemoteException
  {
    return OrderData.createValue();
  }

  /**
   * determines if the payment is authorized.
   * @return true if the payment is authorized.
   * @throws            RemoteException Required by EJB 1.0
   */
  public boolean authorizePayment()
      throws RemoteException
  {
    return true;
  }

  /**
   * Processes the address info of the order.
   * @param pShipAddress  the shipping address info.
   * @param pBillSame  the indicator to say if having same billing address.
   * @param pBillAddress  the billing address info.
   * @param pOrderId  the order identifier
   * @param pBusEntityId  the customer identifier
   * @return none.
   * @throws            RemoteException Required by EJB 1.0
   */
  public void processAddressInfo(AddressData pShipAddress, String pBillSame,
                AddressData pBillAddress, int pOrderId, int pBusEntityId)
      throws RemoteException
  {
  }



  /**
   * sends the order to ERP.
   * @param pOrder  the order data.
   * @param pSourceId  the source identifier
   * @return none.
   * @throws            RemoteException Required by EJB 1.0
   */
  public void sendToERP(OrderData pOrder, int pSourceId)
      throws RemoteException
  {
  }

  /**
   * finds the locale code
   * @param void
   * @return a String code value
   * @throws            RemoteException Required by EJB 1.0
   */
  public String getLocaleCd()
      throws RemoteException
  {
    return "";
  }

  /**
   * sets the locale code
   * @param pLocaleCd  the locale code
   * @return void
   * @throws            RemoteException Required by EJB 1.0
   */
  public void setLocaleCd(String pLocaleCd)
      throws RemoteException
  {
  }

  /**
   * finds the currency code
   * @param void
   * @return a String code value
   * @throws            RemoteException Required by EJB 1.0
   */
  public String getCurrencyCd()
      throws RemoteException
  {
    return "";
  }

  /**
   * sets the currency code
   * @param pCurrencyCd  the currency code
   * @return void
   * @throws            RemoteException Required by EJB 1.0
   */
  public void setCurrencyCd(String pCurrencyCd)
      throws RemoteException
  {
  }

  /**
   * finds the currency code
   * @param void
   * @return a String code value
   * @throws            RemoteException Required by EJB 1.0
   */
  public String getBaseCurrencyCd()
      throws RemoteException
  {
    return "";
  }

  /**
   * sets the base currency code
   * @param pBaseCurrencyCd  the currency code
   * @return void
   * @throws            RemoteException Required by EJB 1.0
   */
  public void setBaseCurrencyCd(String pBaseCurrencyCd)
      throws RemoteException
  {
  }


}
