package com.cleanwise.service.api.session;

/**
 * Title:        Contractor
 * Description:  Remote Interface for Contractor Stateless Session Bean
 * Purpose:      Provides access to the services for managing the Contractor information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 */

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Remote interface for the <code>Contractor</code> stateless session bean.
 *
 * */
public interface Contractor extends javax.ejb.EJBObject
{
    public BuildingServicesContractorView getContractorForStore(int pContractorId, IdVector pStoreIds, boolean pInactiveFl)
    throws RemoteException, DataNotFoundException;
    
    public List listAll(IdVector storeIds, String buildingSvcContractor,
			String fieldValue, String fieldSearchType, boolean showInactiveFl ) throws RemoteException;
	
	public int updateInfo(Hashtable pReqTable) throws RemoteException;
	
	public ArrayList getSitesFor(Hashtable pReqTable) throws RemoteException;
}
