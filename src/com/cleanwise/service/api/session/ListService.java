package com.cleanwise.service.api.session;

/**
 * Title:        ListService
 * Description:  Remote Interface for ListService Stateless Session Bean
 * Purpose:      Provide reference code lookup functionality.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;

/**
 * Provide reference code lookup functionality.  
 *
 * @author durval
 */
public interface ListService extends javax.ejb.EJBObject
{
    /**
     *  Flag indicating that returned vector of stores should be ordered
     *  by ids
     */
    public final static int ORDER_BY_ID = 10000;
    /**
     *  Flag indicating that returned vector of stores should be ordered
     *  by names
     */
    public final static int ORDER_BY_NAME = 10001;

    /**
     * Gets the ref codes vector for the given code.  Default ordering
     * is by ID.
     * @param pRefCd  the application reference code
     * @return RefCdDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public RefCdDataVector getRefCodesCollection(String pRefCd)
	throws RemoteException;

    /**
     * Gets the ref codes vector for the given code.
     * @param pRefCd  the application reference code
     * @param pOrderFlag one of ORDER_BY_ID, ORDER_BY_NAME
     * @return RefCdDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public RefCdDataVector getRefCodesCollection(String pRefCd, int pOrderFlag)
	throws RemoteException;
}




