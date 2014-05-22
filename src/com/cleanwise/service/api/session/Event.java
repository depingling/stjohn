package com.cleanwise.service.api.session;

/**
 * Title:        Event
 * Description:  Remote Interface for Event Stateless Session Bean
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.eventsys.EventDataVector;
import com.cleanwise.service.api.eventsys.EventData;

public interface Event extends EJBObject
{
    public static final String STATUS_READY          = "READY";
    public static final String STATUS_PENDING_REVIEW = "PENDING_REVIEW" ;
    public static final String STATUS_FAILED         = "FAILED";
    public static final String STATUS_IN_PROGRESS    = "IN_PROGRESS";
    public static final String STATUS_REJECTED       = "REJECTED";
    public static final String STATUS_PROCESSED      = "PROCESSED";
    public static final String STATUS_PROC_ERROR     = "PROC_ERROR"; // SVC
    public static final String STATUS_DELETED        = "DELETED";
    public static final String STATUS_LIMITED        = "LIMITED";
    public static final String STATUS_IGNORE         = "IGNORE";
    public static final String STATUS_SYNC_CALL      = "SYNC_CALL";
    public static final String STATUS_HOLD           = "HOLD";

    public static final String TYPE_EMAIL                = "EMAIL";
    public static final String TYPE_FTP                  = "FTP";
    public static final String TYPE_DISTRIBUTOR_DELIVERY = "DISTRIBUTOR_DELIVERY";
    public static final String TYPE_PROCESS              = "PROCESS";

    public static final String PROPERTY_PROCESS_TEMPLATE_ID = "PROCESS_TEMPLATE_ID";
    public static final String PROCESS_ACTIVE_ID            = "PROCESS_ACTIVE_ID";
    public static final String PROCESS_VARIABLE = "PROCESS_VARIABLE";
    public static final String PROCESS_SCHEMA = "PROCESS_SCHEMA";
    public static final String PROPERTY_ERROR = "ERROR";
    public static final String PROPERTY_COMMENT = "COMMENT";
    public static final String PROCESS_PRIORITY = "PROCESS_PRIORITY";
    public static final String PROCESS_SUBPROCESS_PRIORITY = "PROCESS_SUBPROCESS_PRIORITY";
    
    public static final String WATING_ON = "waitingOn";
    public static final String WATING_ON_FAIL_MEDIATOR_CLASS = "waitingOnFailMediatorClass";
    public static final String PARENT_EVENT_ID = "Priority_Override";
    public static final String PRIORITY_OVERRIDE = "Priority_Override";
    public static final String SUBPROCESS_PRIORITY = "SubProcess_Priority";
    public static final String EVENT_ID = "EVENT_ID";

    public static final int EVENT_MSG_MAX_LENGTH = 3998;

    /**
     * Return all events with by status
     * @param status Status of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String status)
    throws RemoteException;

    /**
     * Return all events with by status ant type
     * @param status Status of event
     * @param type   Type of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String status, String type)
    throws RemoteException;
    public EventDataVector getEventV(String status, String type, boolean active)
            throws RemoteException;
    public EventDataVector getEventV(DBCriteria dbc) throws RemoteException;
//    /**
//     * Add new record to clw_event
//     * @param event EventData
//     */
//    public EventData  addEventToDB(EventData event)   throws RemoteException;

    /**
     * Add new record to clw_event_property
     *
     * @param eventId     event id
     * @param shortDesc   short description
     * @param eventTypeCd type code
     * @param blobValue   value
     * @throws RemoteException if an errors
     */
    public void addProperty(int eventId,
                            String shortDesc,
                            String eventTypeCd,
                            Object blobValue,int num) throws RemoteException;

    public int updateEventData(EventData eventData)   throws RemoteException;

    public int updateEventData(EventData eventData, String comment)   throws RemoteException;

    public void runTest(int loopCount, int millis) throws RemoteException;

    public void runJob()  throws RemoteException;

    public EventData setEventStatus(EventData eventData, String status) throws RemoteException;

    public EventEmailDataViewVector getEmailsByEvent(int eventId, String statusCd) throws RemoteException;

    public EventEmailDataView addEventEmail(EventEmailDataView email) throws RemoteException;

    /**
     * This method will result in an email being sent, and a record of it in the event system.
     * send an email using the event system but do not use transactions.  That is to say if you want to send an email even
     * when the overall transaction is being rolled back use this method.  Unless this is SPECIFICALLY required 
     * you should use the @see addEventEmail method.
     * @param pEvent email view
     * @param pUser  user adding the email
     * @return event view with populated ids
     * @throws RemoteException if an errors
     */
    public EventEmailView addEventEmailNoTx(EventEmailView pEvent, String pUser) throws RemoteException;
    
    public EventEmailDataView updateEventEmailData(EventEmailDataView email) throws RemoteException;

    public EventDataVector getEventV(String status, int maxRows)   throws RemoteException;

    /**
     * Return all events with by status and set of types
     * @param status Status of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String pStatus, List pTypes, int maxRows)
            throws RemoteException;

    public EventDataVector getEventVForProcessing(String pStatus, List pTypes, int maxRows, int priority)
        throws RemoteException;

    public int getEventCount(String status, String type, boolean active) throws RemoteException;

    /**
     * Return event by ID
     * @param eventId Event ID in DB
     * @return       EventData
     */
    public EventData getEvent(int eventId) throws RemoteException;

    public int deleteEvent(EventData eventData)   throws RemoteException;

    public EventDataVector getEventV(String status, String type, boolean active, Date dateFrom, Date dateTo)
            throws RemoteException;


   public EventDataVector getEventV(String status, String type, boolean active, java.util.Date dateFrom, java.util.Date dateTo,
                                     String param_type, String param_value)
            throws RemoteException;
   public void sendOrderNotification() throws RemoteException;

   public EventPropertyDataVector getAllEventProperties() throws RemoteException;

   public EventDataVector getEventV(int eventId, String searchActive, String type, List<String> statusList,
            java.util.Date dateFrom, java.util.Date dateTo,
            java.util.Date timeFrom, java.util.Date timeTo,
            String searchParamType, String attrType, String attrFilterType, String attrFilterValue1,
            String attrFilterValue2,String searchContainsText, IdVector pEventIdList,
            String searchBlobValue, int searchBlobMaxRows) throws RemoteException;

   public EventPropertyData getEventProperty(int eventPropertyId) throws RemoteException;
   public void updateEventProperty(EventPropertyData eventPropertyData) throws RemoteException;
   public void updateEventPropertyBlob(EventPropertyData item, byte[] data) throws RemoteException;

   public int deleteEventProperty(int eventId, String eventType) throws RemoteException;
   public LoggingDataVector getLogs(int eventId) throws RemoteException;
   public EventDataVector getEventCollectionByIdList(IdVector pEventIdList)  throws RemoteException;
   public void processEvent(int eventId) throws RemoteException;

    /**
     * Return all events according to the input parameters
     *
     * @param pProcessTemplateId Template identifier of process
     * @param pEventStatusList   Status list of event
     * @param pParamName         Param name
     * @param pParamType         Param type
     * @param pParamValue        Param value
     * @return EventDataVector  events
     * @throws java.rmi.RemoteException if an errors
     */
    public EventDataVector getEventVProcessTypeOnly(int pProcessTemplateId,
                                                    List pEventStatusList,
                                                    String pParamName,
                                                    String pParamType,
                                                    String pParamValue) throws RemoteException;

    /**
     * send event to process
     *
     * @param pEvent process view
     * @param pUser  user
     * @return event view
     * @throws RemoteException if an errors
     */
    public EventProcessView addEventProcess(EventProcessView pEvent, String pUser) throws RemoteException;

    /**
     * send event to process
     *
     * @param pEvent email view
     * @param pUser  user
     * @return event view
     * @throws RemoteException if an errors
     */
    public EventEmailView addEventEmail(EventEmailView pEvent, String pUser) throws RemoteException;
    public EventEmailView addEventEmail(EventEmailView pEvent, String pUser, int parentEventId) throws RemoteException;

    public String getEventStatus(int eventId) throws RemoteException;
    public EventPropertyDataVector getEventPropertiesAsVector(int eventId) throws RemoteException ;
    public void updateEventProperty(int pEventId, String pPropertyName, Object pNewValue, String pModBy) throws RemoteException;
    /**
     * Check if a event with process id=processId and status = 'READY' or 'IN_PROGRESS' exists. Will also 
     * compare the property list (epDV) if passed in
     * @param processId
     * @return 
     */
    public boolean activeEventExist(int processTemplateId, EventPropertyDataVector epDV) 
    throws RemoteException ;

	public EventEmailDataView addEventEmail(EventEmailDataView em, int parentEventId) throws RemoteException;

	public void updateEventFromHoldToReady() throws RemoteException;
}
