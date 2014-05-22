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
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.APIAccess;


/**
 * Provide reference code lookup functionality.  
 *
 * @author durval
 */
public class ListServiceBean extends UtilityServicesAPI
{
    /**
     * Creates a new <code>ListServiceBean</code> instance.
     *
     */
    public ListServiceBean() {}

    /**
     * Default <code>ejbCreate</code> method.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Gets the ref codes vector for the given code.  Default ordering
     * is by ID.
     * @param pRefCd  the application reference code
     * @return RefCdDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public RefCdDataVector getRefCodesCollection(String pRefCd)
	throws RemoteException {

	return getRefCodesCollection(pRefCd, ListService.ORDER_BY_ID);
    }

    /**
     * Gets ref codes vector information values to be used by the request.
     * @param pRefCd  the application reference code
     * @param pOrderFlag one of ORDER_BY_ID, ORDER_BY_NAME
     * @return RefCodeDataVector
     * @throws            RemoteException Required by EJB 1.0
     */
    public RefCdDataVector getRefCodesCollection(String pRefCd, int pOrderFlag)
	throws RemoteException
    {
	Connection lConn = null;
	RefCdDataVector lRes = null;
	try {
	    lConn = getConnection();
	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(RefCdDataAccess.REF_CD, pRefCd);
	    switch (pOrderFlag) {
	    case ListService.ORDER_BY_ID:
		crit.addOrderBy(RefCdDataAccess.REF_CD_ID, true);
		break;
	    case ListService.ORDER_BY_NAME:
		crit.addOrderBy(RefCdDataAccess.SHORT_DESC, true);
		break;
	    default:
		throw new RemoteException("getRefCodesCollection: Bad order specification");
	    }
	    lRes = RefCdDataAccess.select(lConn, crit);
	} catch (Exception e) {
	    throw new RemoteException("getByRefCodesCollection error: " 
				      + e.getMessage());
	} finally {
	    closeConnection(lConn);
	}
    
	if (lRes.size() == 0) {
	    throw new RemoteException("getRefCodesCollection error: no data for reference code " + pRefCd);
	};

	return lRes;
    }

}






