package com.cleanwise.service.api.session;

/**
 * Title:        ChangeLog
 * Description:  Remote Interface for ChangeLog Stateless Session Bean
 * Purpose:      Provides access to change log methods.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;

public interface ChangeLog extends javax.ejb.EJBObject
{

  /**
   * Adds the change log information values to be used by the request.
   * @param pAddChangeLogData  the change log data.
   * @param request  the change log request data
   * @return new ChangeLogRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ChangeLogRequestData addChangeLog(ChangeLogData pAddChangeLogData, 
            ChangeLogRequestData request)
      throws RemoteException;


}
