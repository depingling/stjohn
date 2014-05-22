/*
 * CorporateOrder.java
 *
 * Created on February 10, 2005, 11:05 AM
 */

package com.cleanwise.service.api.session;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import java.util.*;
import java.rmi.RemoteException;
import com.cleanwise.service.api.value.*;
import java.sql.Connection;
import java.text.ParseException;

/**
 *
 * @author bstevens
 * method addUpdateBusEntityAssociations() added by Sergei Cher 12/2/2008
 * 
 */
public interface CorporateOrder extends javax.ejb.EJBObject{
	public String placeCorporateOrder(CorpOrderCacheView pCorpOrderCache) 
	throws RemoteException;

	public List<DeliveryScheduleView> getCorpSchedules(Date beginDate, Date endDate,  IdVector scheduleIdV) 
	throws RemoteException, ParseException;
	
	HashMap<Integer,HashMap> getAccountCatalogSiteMap (int pScheduleId) 
	throws RemoteException;
	
	public HashMap<Integer,ProductData> getContractProducts(int pCatalogId,  
	                                    AccCategoryToCostCenterView pAccCategToCostCenterVw) 
	throws RemoteException;
	
	HashMap<Integer,Integer> getInventoryOGIds(List pSiteIdV) 
	throws RemoteException;
	
	public List<Integer> getScheduleSites(int scheduleId) 
	throws RemoteException ;

	public void updateLastProccessedDt(int scheduleId, Date schStartDate) 
	throws RemoteException;

}
