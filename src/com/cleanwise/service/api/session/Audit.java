package com.cleanwise.service.api.session;

/**
 * Title:        Audit
 * Description:  Remote Interface for Audit Stateless Session Bean
 * Purpose:      Provides access to the table-level audit methods (add by, add date, mod by, mod date)
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

public interface Audit extends javax.ejb.EJBObject
{

  /**
   * Adds the audit information values to be used by the request.
   * @param pAddAuditData  the audit data.
   * @param request  the audit request data
   * @return new AuditRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public AuditRequestData addAudit(AuditData pAddAuditData, 
                AuditRequestData request)
      throws RemoteException;

  /**
   * Updates the audit information values to be used by the request.
   * @param pUpdateAuditData  the audit data.
   * @param pTable  the application table name
   * @param pUserName  the user name
   * @param pNow  the Java current date (no time)
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateAudit(AuditData pUpdateAuditData, String pTable,
                String pUserName, Date pNow)
      throws RemoteException;


}
