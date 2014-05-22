package com.cleanwise.service.api.session;

import java.rmi.*;
import java.util.Date;
import com.cleanwise.service.api.value.*;

public interface Schedule extends javax.ejb.EJBObject
{
	/**
	 * 
	 * @param storeId
	 * @return all the Corporate Schedules belong to store: storeId
	 * @throws RemoteException
	 */
	public DeliveryScheduleViewVector getCorporateSchedules(int storeId)
	throws RemoteException;
	
	/**
	 * 
	 * @param storeId
	 * @param scheduleId
	 * @return Corporate schedule match store StoreId and scheduleId
	 * @throws RemoteException
	 */
	public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId, int pScheduleId)
    throws RemoteException;
	
	/**
	 * 
	 * @param storeId
	 * @param scheduleName
	 * @param dateFrom
	 * @param dateTo
	 * @param startWithFl
	 * @return return list of Corporate Schedules by search criteria
	 * @throws RemoteException
	 */
	public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId, String pScheduleName, 
			Date pDateFrom, Date pDateTo, boolean pStartWithFl)
    throws RemoteException;
	
	/**
	 * Return active schedule for site pSiteId 
	 * @param pSiteId
	 * @return
	 * @throws RemoteException
	 */
	public ScheduleData getSchedule(int pSiteId)
    throws RemoteException;
	
	public DeliveryScheduleViewVector getCorporateSchedules(int pStoreId, int pSiteId, String pScheduleName, boolean pStartWithFl)
    throws RemoteException;
	
	/**
     *  Gets a Corporate Schedule
     *
     *@param  pScheduleId          the schedule id
     *@return                      a set of ScheduleJoinView objects
     *@exception  RemoteException  if an error occurs
     */
    public ScheduleJoinView getScheduleById(int pScheduleId)
    throws RemoteException;
    
    /**
     *  Updates existing or inserts new schedule depending on scheduleId
     *  property
     *
     *@param  pSchedule            the schedule
     *@param  pScheduleDetailDV    the set of ScheduleDetail objects. 
     *@param  pUser                the user login name
     *@return                      Description of the Return Value
     *@exception  RemoteException  if an error occurs
     */
    public ScheduleJoinView saveSchedule(ScheduleData pSchedule,
            ScheduleDetailDataVector pScheduleDetailDV, String pUser)
    throws RemoteException;
    
    /**
     *  Deletes the delivery schedule
     *
     *@param  pScheduleId          the schedule id
     *@exception  RemoteException  if an error occurs
     */
    public void deleteSchedule(int pScheduleId)
    throws RemoteException;
    
    public IdVector getScheduleAccountIds(int pScheduleId, IdVector pAccountIds)
    throws RemoteException;
    
    public IdVector getScheduleSiteIds(int pScheduleId, IdVector pAccountIds)
    throws RemoteException;
    
    /**
     *  Updates accounts configrued to the distributor schedule
     *@param  pScheduleId          the schedule id
     *@param  pAcctIdToAdd         the configured accounts
     *@param  pAcctIdToDel         the unconfigured accounts
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateScheduleAccounts(int pScheduleId,
            IdVector pAcctIdToAdd, IdVector pAcctIdToDel, String pUser)
    throws RemoteException;
    
    public void updateScheduleSites(int pScheduleId,
            IdVector pAcctIdToAdd, IdVector pAcctIdToDel, String pUser)
    throws RemoteException;

	public void configureAllAccounts(int pScheduleId, String pUser)
	throws RemoteException;
	
	public void configureAllAccountSites(int pScheduleId, IdVector pAccountIds, String pUser)
	throws RemoteException;

	public Date getNextDeliveryDate(int pScheduleId)
    throws RemoteException;
	
	//STJ-5757.
	public String getCorpScheduleIntervalValue(int scheduleId) 
	throws RemoteException;
}
