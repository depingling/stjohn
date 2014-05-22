package com.cleanwise.service.api.session;

/**
 * Title:        OrderNotification
 * Description:  Remote Interface for OrderNotification Stateless Session Bean
 * Purpose:      Provides notification services by sending email messages to the customer and order processing department.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;

public interface OrderNotification extends javax.ejb.EJBObject
{

  /**
   * sends the order.
   * @param pOrder  the order data.
   * @param pBusEntityId  the customer identifier
   * @return none.
   * @throws            RemoteException Required by EJB 1.0
   */
  public void sendOrder(OrderData pOrder, int pBusEntityId)
      throws RemoteException;

}
