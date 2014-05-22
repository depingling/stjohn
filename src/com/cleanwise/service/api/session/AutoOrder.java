package com.cleanwise.service.api.session;

/**
 * Title:        AutoOrder
 * Description:  Remote Interface for AutoOrder Stateless Session Bean
 * Purpose:      Provides access to the methods for msds & specs documents
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import javax.servlet.http.HttpServletRequest;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.math.BigDecimal;

public interface AutoOrder extends javax.ejb.EJBObject
{
    /**
     * Gets the Order schedule
     * @param pOrderScheduleId  the order schedule id
     * @return an OrderScheduleJoin object
     * @throws   RemoteException Required by EJB 1.0, DataNotFoundException
     */
    public OrderScheduleJoin getOrderSchedule(int pOrderScheduleId)
            throws RemoteException, DataNotFoundException;

    /**
     * Gets the list of Order schedules
     * @param pBusEntityId  the site id
     * @param pUserId the user id
     * @return a list of OrderScheduleView objects
     * @throws   RemoteException Required by EJB 1.0
     */
    public OrderScheduleViewVector getOrderSchedules(int pBusEntityId, int pUserId)
            throws RemoteException;

    /**
     * Gets vector of order schedules
     * @param pAccountId the account identifier
     * @param pSiteId the site identifier
     * @param pSiteName part of the site short description
     * @param pOrderGuideName part of the order guide name
     * @param pOrderScheduleType type of the scheduled aciont (RefCodeNames.ORDER_SCHEDULE_CD.PLACE_ORDER, RefCodeNames.ORDER_SCHEDULE_CD.NOTIFY  or nothing)
     * @param pOrderScheduleRuleType type of schedule rule (RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK, RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH, RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK_MONTH RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST or nothing)
     * @param pDateFor the schedule date, when shceduled action should be performed
     * @param pMatch string matching operaition (SearchCriteria.BEGINS_WITH_IGNORE_CASE or SearchCriteria.CONTAINS_IGNORE_CASE)
     * @throws   RemoteException Required by EJB 1.0
     */
    public OrderScheduleViewVector getOrderSchedules(int pAccountId, int pSiteId, String pSiteName, String pOrderGuideName, String pOrderScheduleType, String pOrderScheduleRuleType, Date pDateFor, int pMatch)
            throws RemoteException;

    /**
     * Gets vector of order schedules to be placed or to remaind the user to place the order
     * @param pDateFor the schedule date, for which
     * @throws   RemoteException Required by EJB 1.0
     */
    public IdVector getOrderSchedules(Date pDateFor)
            throws RemoteException;
 /**
  * Gets vector of order schedules to be placed or to remaind the user to place the order
  * @param pDateFor the schedule date, for which
   *@param pAccountIds list of accounts to process or ignore 
   * (depending on pForOrExceptFl), if null would process all accounts
   *@param pForOrExceptFl equals true or false 
   * (to return schedules for accounts or to return all schedules except accounts provided)
   * @throws   RemoteException Required by EJB 1.0
  */
  public IdVector getOrderSchedules(Date pDateFor, IdVector pAccountIds, boolean pForOrExceptFl)
  throws RemoteException;

    /**
     * Saves to database the order schedule
     * @param pOrderSchedule the schedule data
     * @param pUser the user logon name
     * @return the order schedule identifier
     * @throws   RemoteException Required by EJB 1.0
     */
    public int saveOrderSchedule(OrderScheduleJoin pOrderSchedule, String pUser)
            throws RemoteException;

    /**
     * Deletes to database the order schedule
     * @param pOrderScheduleId the order schedule id
     * @param pUser the user logon name
     * @throws   RemoteException Required by EJB 1.0
     */
    public void deleteOrderSchedule(int pOrderScheduleId, String pUser)
            throws RemoteException;

    /**
     * Processes order for order schedule without date checking. Logs result to clw_order_batch_log table
     * @param pOrderScheduleId the order schedule id
     * @param pDateFor the processing date
     * @param pUser the user logon name
     * @return eMail message to the customer
     * @throws   RemoteException Required by EJB 1.0
     */
    public String placeAutoOrder(int pOrderScheduleId, Date pDateFor, String pUser)
            throws RemoteException;

    /**
     * Sends email about successfully placed order
     * @param  pOrderScheduleId order schedule id
     * @param  pEmailMessage message to be sent
     * @param pUser the user logon name
     * @throws   RemoteException Required by EJB 1.0
     */
    public void sendOrderNotification(int pOrderScheduleId,
                                      String pEmailMessage,
                                      String pUser)
            throws RemoteException;

    /**
     * Gets order log records for the date
     * @param pDateFor the processing date
     * @return List of the log records
     * @throws   RemoteException Required by EJB 1.0
     */
    public OrderBatchLogDataVector getOrderBatchLog(Date pDateFor)
            throws RemoteException;
/*
  public String placeAutoOrdersForToday(Date pDateFor, String pUser)
  throws RemoteException;
 */

    /**
     * Saves error message into CLW_ORDER_BACTCH_LOG table
     * @param pOrderScheduleId the order schedule id
     * @param pDateFor the processing date
     * @param pUser the user logon name
     * @throws   RemoteException Required by EJB 1.0
     */
    public void logErrorRecord(int pOrderScheduleId, String pMess, Date pDateFor, String pUser)
            throws RemoteException;

    /**
     * Gets the list of Order schedules
     * @param pUserId the user id
     * @param bDate  Begin Date
     * @param eDate  End Date
     * @return a list of OrderScheduleView objects
     * @throws   RemoteException Required by EJB 1.0
     */
    public OrderScheduleViewVector getOrderSchedules(int pUserId, Date bDate, Date eDate)
            throws RemoteException;


    /**
     * Gets the list of Order schedules
     *
     * @param siteIds  the site ids
     * @param pDateFor the schedule date
     * @return a ids list
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector getWorkOrderScheduleIds(IdVector siteIds, Date pDateFor) throws RemoteException ;


    /**
     * Processes order for  work order schedule without date checking.
     * Logs result to clw_order_batch_log table
     * @param pOrderScheduleId the order schedule id
     * @param pDateFor the processing date
     * @param pUser the user logon name
     * @return eMail message to the customer
     * @throws RemoteException Required by EJB 1.0
     */
    public String placeAutoWorkOrder(int pOrderScheduleId, Date pDateFor, String pUser) throws RemoteException;

    /**
     * Gets the list of Work Order schedules
     *
     * @param pBusEntityId the site id
     * @param workOrderIds optional work order ids
     * @return a list of OrderScheduleDataobjects
     * @throws RemoteException Required by EJB 1.0
     */
    public OrderScheduleDataVector getWorkOrderSchedules(int pBusEntityId, IdVector workOrderIds) throws RemoteException;

    /**
     * Gets the Work Order schedule
     *
     * @param pOrderScheduleId the order schedule id
     * @return an OrderScheduleJoin object
     * @throws RemoteException Required by EJB 1.0
     * @throws com.cleanwise.service.api.util.DataNotFoundException if schedule not found
     */
    public OrderScheduleJoin getWorkOrderSchedule(int pOrderScheduleId) throws RemoteException, DataNotFoundException;
    
    public ArrayList getOrderSchedulesList(int pSiteId, Date pBeginDate, Date pEndDate, String pScheduleCd)
    throws RemoteException, DataNotFoundException;
    
}


