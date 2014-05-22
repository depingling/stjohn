package com.cleanwise.service.api.session;

/**
 * Title:        EventBean
 * Description:  Bean implementation for Event Stateless Session Bean
 * Purpose:      Provides event driven data processing
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Evgeny Vlasov, Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;

import oracle.sql.BLOB;

import com.cleanwise.service.api.dao.EventDataAccess;
import com.cleanwise.service.api.dao.EventEmailDataAccess;
import com.cleanwise.service.api.dao.EventPropertyDataAccess;
import com.cleanwise.service.api.dao.LoggingDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.ProcessDataAccess;
import com.cleanwise.service.api.dao.TaskPropertyDataAccess;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import com.cleanwise.service.api.eventsys.EventData;
import com.cleanwise.service.api.eventsys.EventDataVector;
import com.cleanwise.service.api.eventsys.EventHandler;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.plugin.FailMediator;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ObjectUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.EventEmailDataVector;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailDataViewVector;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LoggingData;
import com.cleanwise.service.api.value.LoggingDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderInfoDataView;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.TaskPropertyData;
import com.cleanwise.service.api.value.TaskPropertyDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserInfoDataVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;

import org.apache.log4j.Logger;

public class EventBean extends UtilityServicesAPI
{
    private static final String className = "EventBean";
    private static final Logger log = Logger.getLogger(EventBean.class);
    private BlobStorageAccess bsa = new BlobStorageAccess();
    

    /**
     *
     */
    public EventBean() {}

    /**
     *
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.CreateException
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    public int getEventCount(String status, String type, boolean active)
            throws RemoteException {

        int count = 0;
        Connection conn = null;

        if (status == null) {
            status = "";
        }

        if (type == null) {
            type = "";
        }
        try {
            conn = getConnection();
            String query = "";
            PreparedStatement sttm = null;

            if (!status.equals("") && !type.equals("")) {
                query =
                        "SELECT count(event_id) " +
                                "  FROM clw_event\n" +
                                " WHERE status = ? AND TYPE = ? ";
                if(active){
                    query = query + " AND attempt > 0";
                } else{
                    query = query + " AND attempt=0 ";
                }

                sttm = conn.prepareStatement(query);
                sttm.setString(1, status);
                sttm.setString(2, type);

            } else if (!status.equals("") && type.equals("")) {
                query =
                        "SELECT count(event_id) " +
                                "  FROM clw_event\n" +
                                " WHERE status = ? ";
                if(active){
                    query = query + " AND attempt > 0";
                }else{
                    query = query + " AND attempt=0 ";
                }

                sttm = conn.prepareStatement(query);
                sttm.setString(1, status);

            } else if (status.equals("") && !type.equals("")) {
                query =
                        "SELECT count(event_id) " +
                                "  FROM clw_event\n" +
                                " WHERE TYPE = ? ";
                if(active){
                    query = query + " AND attempt > 0";
                }else{
                    query = query + " AND attempt=0 ";
                }

                sttm = conn.prepareStatement(query);
                sttm.setString(1, type);

            } else if (status.equals("") && type.equals("")) {
                query =
                        "SELECT count(event_id) " +
                                "  FROM clw_event\n";
                if(active){
                    query = query + " WHERE  attempt > 0";
                } else{
                    query = query + " WHERE  attempt=0 ";
                }

                sttm = conn.prepareStatement(query);
            }

            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            sttm.close();


        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

        return count;
    }


    /**
     * Return event by ID
     * @param eventId Event ID in DB
     * @return       EventData
     */
    public EventData getEvent(int eventId) throws RemoteException
    {

        EventData eventData = null;
        Connection conn = null;

        try {
            conn = getConnection();
            String query =
                    "SELECT clw_event.event_id, clw_event.status, CLW_PROCESS.PROCESS_NAME, clw_event.cd, clw_event.add_date, clw_event.mod_date,\n" +
                            "  clw_event.mod_by, clw_event.attempt, clw_event.process_time, clw_event.type\n" +
                            "  FROM clw_event \n" +
                            "    LEFT OUTER JOIN CLW_EVENT_PROPERTY CLW_EVENT_PROPERTY_2\n" +
                            "      ON CLW_EVENT.EVENT_ID = CLW_EVENT_PROPERTY_2.EVENT_ID\n" +
                            "      AND CLW_EVENT_PROPERTY_2.TYPE = 'PROCESS_TEMPLATE_ID'\n" +
                            "      AND CLW_EVENT_PROPERTY_2.SHORT_DESC = 'process_id'\n" +
                            "    LEFT OUTER JOIN CLW_PROCESS ON CLW_PROCESS.PROCESS_ID = CLW_EVENT_PROPERTY_2.NUMBER_VAL\n" +
                            " WHERE clw_event.event_id = ?";

            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setInt(1, eventId);

            ResultSet rs = sttm.executeQuery();
            while(rs.next()){
                eventData = new EventData();
                eventData.setEventId(rs.getInt(1));
                eventData.setStatus(rs.getString(2));
                eventData.setProcessName(rs.getString(3));
                eventData.setCd(rs.getString(4));
                eventData.setAddDate(rs.getTimestamp(5));
                eventData.setModDate(rs.getTimestamp(6));
                eventData.setAttempt(rs.getInt(8));
                eventData.setProcessTime(rs.getTimestamp(9));                
                eventData.setType(rs.getString(10));
            }
            rs.close();
            sttm.close();

            if(eventData!=null){
                eventData.setProperties(getEventProperties(conn, eventData.getEventId()));

                //init StackTrace if it exist
                EventPropertyDataVector eventPropertyDataVector = getEventPropertiesAsVector(eventData.getEventId());

                if(eventPropertyDataVector!=null){
                    Iterator it = eventPropertyDataVector.iterator();
                    EventPropertyData stackTrace = null;
                    while(it.hasNext()){
                        EventPropertyData eventPropertyData = (EventPropertyData) it.next();
                        if("StackTrace".equals(eventPropertyData.getShortDesc())){
                            eventData.setStackTrace(eventPropertyData.getStringVal());
                            stackTrace = eventPropertyData;
                        }
                    }
                    if(stackTrace!=null){
                        eventPropertyDataVector.remove(stackTrace);
                    }
                }

                eventData.setEventPropertyDataVector(eventPropertyDataVector);

                //if it is email - init email property
                if(Event.TYPE_EMAIL.equals(eventData.getType())){
                    eventData.setEventEmailDataViewVector(getEmailsByEvent(eventData.getEventId()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

        return eventData;
    }

    /**
     * Return eventPropertyDataVector by ID
     * @param eventId Event ID in DB
     * @return EventPropertyDataVector
     * @throws java.rmi.RemoteException
     */
    public EventPropertyDataVector getEventPropertiesAsVector(int eventId) throws RemoteException
    {

        EventPropertyDataVector eventPropertyDV = null;
        Connection conn = null;

        try {

            conn = getConnection();
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(EventPropertyDataAccess.EVENT_ID, eventId);
            eventPropertyDV = EventPropertyDataAccess.select(conn, dbCrit);
            // STJ-6037
            if (Utility.isSet(eventPropertyDV)) {
                EventPropertyData eventProperty;
                byte[] blob;
                for (int i = 0; i < eventPropertyDV.size(); i++) {
                    eventProperty = (EventPropertyData) eventPropertyDV.get(i);
                    
                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(eventProperty.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(eventProperty.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(eventProperty.getStorageTypeCd())) {
                        if (Utility.isSet(eventProperty.getBlobValueSystemRef())) {
                            blob = bsa.readBlob(eventProperty.getStorageTypeCd(),
                                                eventProperty.getBinaryDataServer(),
                                                eventProperty.getBlobValueSystemRef());
                            eventProperty.setBlobValue(blob);
                        }
                    } /*else {
                        if (eventProperty.getBlobValue() != null) {
                            throw new Exception("ERROR. Blob has been read from DB. EventID: " + eventProperty.getEventId() +
                                      " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                        }
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

        return eventPropertyDV;
    }
    /**
     * Return all events with by status
     * @param status Status of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String status, int maxRows)
            throws RemoteException
    {

        EventDataVector eventDataVector = new EventDataVector();
        Connection conn = null;

        try {

            conn = getConnection();
            String query =
                    "SELECT event_id, status, type, cd, add_date, mod_date,\n" +
                            "  mod_by, attempt" +
                            "  FROM clw_event\n" +
                            " WHERE status = ? AND attempt > 0";

            PreparedStatement sttm = conn.prepareStatement(query);
            if (maxRows > 0) {
                sttm.setMaxRows(maxRows);
            }
            sttm.setString(1, status);

            ResultSet rs = sttm.executeQuery();
            while(rs.next()){
                EventData eventData = new EventData();
                eventData.setEventId(rs.getInt(1));
                eventData.setStatus(rs.getString(2));
                eventData.setType(rs.getString(3));
                eventData.setCd(rs.getString(4));
                eventData.setAddDate(rs.getDate(5));
                eventData.setModDate(rs.getDate(6));
                eventData.setAttempt(rs.getInt(8));
                eventDataVector.add(eventData);
            }
            rs.close();
            sttm.close();

            Iterator it=eventDataVector.iterator();
            while(it.hasNext())
            {
                EventData ed= (EventData) it.next();
                ed.setProperties(getEventProperties(conn,ed.getEventId()));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

        return eventDataVector;
    }

    public EventDataVector getEventV(String status) throws RemoteException {
        return getEventV(status, 0);
    }


    public EventDataVector getEventV(String status, String type)
            throws RemoteException
    {
        return getEventV(status, type, true);
    }

    /**
     * Return all events with by status ant type
     * @param status Status of event
     * @param type   Type of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String status, String type, boolean active)
            throws RemoteException
    {
        return getEventV(status, type, active, null, null);
    }

    /**
     * Return all events with by status ant type
     * @param status Status of event
     * @param type   Type of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String status, String type, boolean active, java.util.Date dateFrom, java.util.Date dateTo,
                                     String param_type, String param_value)
            throws RemoteException
    {
        EventDataVector eventDataVector = new EventDataVector();
        com.cleanwise.service.api.value.EventDataVector evDV = new com.cleanwise.service.api.value.EventDataVector();
        Connection conn=null;

        if (status == null) {
            status = "";
        }

        if (type == null) {
            type = "";
        }

        try {
            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            if(!status.equals("")){
                dbc.addEqualTo(EventDataAccess.STATUS, status);
            }
            if(!type.equals("")){
                dbc.addEqualTo(EventDataAccess.TYPE, type);
            }
            if(dateFrom!=null){
                dbc.addGreaterOrEqual(EventDataAccess.ADD_BY, dateFrom);
            }
            if(dateTo!=null){
                dbc.addLessOrEqual(EventDataAccess.ADD_BY, dateTo);
            }
            if(active){
                dbc.addGreaterThan(EventDataAccess.ATTEMPT, 0);
            }else{
                dbc.addEqualTo(EventDataAccess.ATTEMPT, 0);
            }

            if(("NUMBER_VALUE".equals(param_type))||("STRING_VALUE".equals(param_type))){
                dbc.addJoinTable(EventPropertyDataAccess.CLW_EVENT_PROPERTY);
                dbc.addJoinCondition(EventDataAccess.EVENT_ID, EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.EVENT_ID);
            }
            if(("EMAIL_TO".equals(param_type))||("EMAIL_THEMA".equals(param_type))||("EMAIL_TEXT".equals(param_type))){
                dbc.addJoinTable(EventEmailDataAccess.CLW_EVENT_EMAIL);
                dbc.addJoinCondition(EventDataAccess.EVENT_ID, EventEmailDataAccess.CLW_EVENT_EMAIL, EventEmailDataAccess.EVENT_ID);
            }
            if("STRING_VALUE".equals(param_type)){
                dbc.addJoinTableLikeIgnoreCase(EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.STRING_VAL, param_value);
            }
            if("NUMBER_VALUE".equals(param_type)){
                dbc.addJoinTableEqualTo(EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.NUMBER_VAL, Integer.parseInt(param_value));
            }
            if("EMAIL_TO".equals(param_type)){
                dbc.addJoinTableLikeIgnoreCase(EventEmailDataAccess.CLW_EVENT_EMAIL, EventEmailDataAccess.TO_ADDRESS, param_value);
            }
            if("EMAIL_THEMA".equals(param_type)){
                dbc.addJoinTableLikeIgnoreCase(EventEmailDataAccess.CLW_EVENT_EMAIL, EventEmailDataAccess.SUBJECT, param_value);
            }
            if("EMAIL_TEXT".equals(param_type)){
                dbc.addJoinTableLikeIgnoreCase(EventEmailDataAccess.CLW_EVENT_EMAIL, EventEmailDataAccess.TEXT, param_value);
            }
            dbc.addOrderBy(EventDataAccess.MOD_DATE, false);

            evDV = EventDataAccess.select(conn, dbc, 200);

            Iterator it = evDV.iterator();
            while(it.hasNext()){
                com.cleanwise.service.api.value.EventData evD = (com.cleanwise.service.api.value.EventData) it.next();
                EventData eventData = new EventData();

                eventData.setEventId(evD.getEventId());
                eventData.setStatus(evD.getStatus());
                eventData.setType(evD.getType());
                eventData.setCd(evD.getCd());
                eventData.setAddDate(evD.getAddDate());
                eventData.setModDate(evD.getModDate());
                eventData.setAttempt(evD.getAttempt());

                eventDataVector.add(eventData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return eventDataVector;
    }

    /**
     * Return all events according to the input parameters
     *
     * @param pProcessTemplateId  Template identifier of process
     * @param pEventStatusList Status list of event
     * @param pParamName       Param name
     * @param pParamType       Param type
     * @param pParamValue      Param value
     * @return EventDataVector  events
     * @throws java.rmi.RemoteException if an errors
     */
    public EventDataVector getEventVProcessTypeOnly(int pProcessTemplateId,
                                                    List pEventStatusList,
                                                    String pParamName,
                                                    String pParamType,
                                                    String pParamValue) throws RemoteException {

        EventDataVector eventDataVector = new EventDataVector();
        com.cleanwise.service.api.value.EventDataVector evDV;
        Connection conn = null;

        if (Utility.isSet(pParamName)) {

            try {
                conn = getConnection();

                DBCriteria dbc = new DBCriteria();

                if (pEventStatusList != null && !pEventStatusList.isEmpty()) {
                    if (pEventStatusList.size() > 1) {
                        dbc.addOneOf(EventDataAccess.STATUS, pEventStatusList);
                    } else {
                        String status = (String) pEventStatusList.get(0);
                        if (Utility.isSet(status)) {
                            dbc.addEqualTo(EventDataAccess.STATUS, status);
                        }
                    }
                }

                dbc.addEqualTo(EventDataAccess.TYPE, Event.TYPE_PROCESS);

                if (pProcessTemplateId > 0) {
                    DBCriteria isolatedCriterita = new DBCriteria();
                    dbc.addJoinTable(EventPropertyDataAccess.CLW_EVENT_PROPERTY + " P_TEMPLATE");
                    isolatedCriterita.addEqualTo("P_TEMPLATE." + EventPropertyDataAccess.TYPE, Event.PROPERTY_PROCESS_TEMPLATE_ID);
                    isolatedCriterita.addEqualTo("P_TEMPLATE." + EventPropertyDataAccess.NUMBER_VAL, pProcessTemplateId);
                    isolatedCriterita.addCondition("P_TEMPLATE." + EventPropertyDataAccess.EVENT_ID + "=" + EventDataAccess.CLW_EVENT + "." + EventDataAccess.EVENT_ID);
                    dbc.addIsolatedCriterita(isolatedCriterita);
                }

                dbc.addJoinTable(EventPropertyDataAccess.CLW_EVENT_PROPERTY);
                dbc.addJoinCondition(EventDataAccess.EVENT_ID, EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.EVENT_ID);
                dbc.addJoinTableEqualTo(EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.TYPE, Event.PROCESS_VARIABLE);
                if (Utility.isSet(pParamName)) {
                    dbc.addJoinTableLikeIgnoreCase(EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.SHORT_DESC, pParamName);
                }

                if ("STRING_VALUE".equals(pParamType)) {
                    dbc.addJoinTableLikeIgnoreCase(EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.STRING_VAL, pParamValue);
                } else if ("NUMBER_VALUE".equals(pParamType)) {
                    dbc.addJoinTableEqualTo(EventPropertyDataAccess.CLW_EVENT_PROPERTY, EventPropertyDataAccess.NUMBER_VAL, Integer.parseInt(pParamValue));
                } else {
                   DBCriteria isolCrit  = new DBCriteria();
                   isolCrit.addCondition("DBMS_LOB.INSTR(" + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "." + EventPropertyDataAccess.BLOB_VALUE + ", UTL_RAW.CAST_TO_RAW('" + Utility.strNN(pParamValue) + "')) > 0");
                   dbc.addIsolatedCriterita(isolCrit);
                }

                dbc.addOrderBy(EventDataAccess.MOD_DATE, false);

                evDV = EventDataAccess.select(conn, dbc);

                Iterator it = evDV.iterator();
                while (it.hasNext()) {
                    com.cleanwise.service.api.value.EventData evD = (com.cleanwise.service.api.value.EventData) it.next();
                    EventData eventData = new EventData();

                    eventData.setEventId(evD.getEventId());
                    eventData.setStatus(evD.getStatus());
                    eventData.setType(evD.getType());
                    eventData.setCd(evD.getCd());
                    eventData.setAddDate(evD.getAddDate());
                    eventData.setModDate(evD.getModDate());
                    eventData.setAttempt(evD.getAttempt());

                    eventDataVector.add(eventData);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            } finally {
                closeConnection(conn);
            }
        }
        return eventDataVector;
    }


    /**
     * Return all events with by status ant type
     * @param status Status of event
     * @param type   Type of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String status, String type, boolean active, java.util.Date dateFrom, java.util.Date dateTo)
            throws RemoteException
    {

        EventDataVector eventDataVector = new EventDataVector();
        Connection conn=null;

        if (status == null) {
            status = "";
        }

        if (type == null) {
            type = "";
        }

        try {

            conn = getConnection();
            String query = "";
            PreparedStatement sttm = null;

            if (!status.equals("") && !type.equals("")) {
                query =
                        "SELECT event_id, status, type, cd, add_date, mod_date,\n" +
                                "       mod_by, attempt" +
                                "  FROM clw_event\n" +
                                " WHERE status = ? AND TYPE = ? ";
                if(active){
                    query = query + " AND attempt > 0 ";
                }else{
                    query = query + " AND attempt=0 ";
                }

                if((dateFrom!=null)&&(dateTo!=null)){
                    query = query + " AND ADD_DATE between ? and ?";
                }

                query = query + " order by add_date desc, type, status, event_id";

                sttm = conn.prepareStatement(query);
                sttm.setString(1, status);
                sttm.setString(2, type);

                if((dateFrom!=null)&&(dateTo!=null)){
                    sttm.setTimestamp(3, DBAccess.toSQLTimestamp(dateFrom));
                    sttm.setTimestamp(4, DBAccess.toSQLTimestamp(dateTo));
                }

            } else if (!status.equals("") && type.equals("")) {
                query =
                        "SELECT event_id, status, type, cd, add_date, mod_date,\n" +
                                "       mod_by, attempt" +
                                "  FROM clw_event\n" +
                                " WHERE status = ? ";
                if(active){
                    query = query + " AND attempt > 0";
                }else{
                    query = query + " AND attempt=0 ";
                }

                if((dateFrom!=null)&&(dateTo!=null)){
                    query = query + " AND ADD_DATE between ? and ?";
                }

                query = query + " order by add_date desc, type, status, event_id";

                sttm = conn.prepareStatement(query);
                sttm.setString(1, status);

                if((dateFrom!=null)&&(dateTo!=null)){
                    sttm.setTimestamp(2, DBAccess.toSQLTimestamp(dateFrom));
                    sttm.setTimestamp(3, DBAccess.toSQLTimestamp(dateTo));
                }

            } else if (status.equals("") && !type.equals("")) {
                query =
                        "SELECT event_id, status, type, cd, add_date, mod_date,\n" +
                                "       mod_by, attempt" +
                                "  FROM clw_event\n" +
                                " WHERE TYPE = ? ";
                if(active){
                    query = query + " AND attempt > 0";
                }else{
                    query = query + " AND attempt=0 ";
                }

                if((dateFrom!=null)&&(dateTo!=null)){
                    query = query + " AND ADD_DATE between ? and ?";
                }

                query = query + " order by add_date desc, type, status, event_id";

                sttm = conn.prepareStatement(query);
                sttm.setString(1, type);

                if((dateFrom!=null)&&(dateTo!=null)){
                    sttm.setTimestamp(2, DBAccess.toSQLTimestamp(dateFrom));
                    sttm.setTimestamp(3, DBAccess.toSQLTimestamp(dateTo));
                }

            } else if (status.equals("") && type.equals("")) {
                query =
                        "SELECT event_id, status, type, cd, add_date, mod_date,\n" +
                                "       mod_by, attempt" +
                                "  FROM clw_event\n";
                if(active){
                    query = query + " WHERE  attempt > 0";
                }else{
                    query = query + " WHERE  attempt = 0 ";
                }

                if((dateFrom!=null)&&(dateTo!=null)){
                    query = query + " AND ADD_DATE between ? and ?";
                }

                query = query + " order by add_date desc, type, status, event_id ";

                sttm = conn.prepareStatement(query);

                if((dateFrom!=null)&&(dateTo!=null)){
                    sttm.setTimestamp(1, DBAccess.toSQLTimestamp(dateFrom));
                    sttm.setTimestamp(2, DBAccess.toSQLTimestamp(dateTo));
                }
            }

            sttm.setMaxRows(200);

            ResultSet rs = sttm.executeQuery();
            while(rs.next()){
                EventData eventData = new EventData();

                eventData.setEventId(rs.getInt(1));
                eventData.setStatus(rs.getString(2));
                eventData.setType(rs.getString(3));
                eventData.setCd(rs.getString(4));
                eventData.setAddDate(rs.getDate(5));
                eventData.setModDate(rs.getDate(6));
                eventData.setAttempt(rs.getInt(8));

                eventDataVector.add(eventData);
            }

            rs.close();
            sttm.close();

            Iterator it = eventDataVector.iterator();
            while(it.hasNext())
            {
                EventData ed = (EventData) it.next();
                ed.setProperties(getEventProperties(conn, ed.getEventId()));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return eventDataVector;
    }

    /**
     * Return all events with by status and set of types
     * @param status Status of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(String pStatus, List pTypes, int maxRows)
            throws RemoteException
    {

        EventDataVector eventDataVector = new EventDataVector();
        Connection conn = null;

        try {
            conn = getConnection();
            if(pTypes == null) {
                return eventDataVector;
            }
            boolean emailFl = false;
            ArrayList typeAL = new ArrayList();
            for(Iterator iter=pTypes.iterator(); iter.hasNext();) {
                String type = (String) iter.next();
                if("EMAIL".equals(type)) {
                    emailFl = true;
                    continue;
                }
                typeAL.add(type);
            }
            String processSql = null;
            if(!typeAL.isEmpty()) {
                char[] typesA = new char[typeAL.size()*2-1];
                typesA[0] = '?';
                for(int ii=1; ii<typeAL.size(); ii++) {
                    typesA[ii*2-1] = ',';
                    typesA[ii*2] = '?';
                }
                processSql =
                    " SELECT distinct e.event_id, e.status, e.type, e.cd, e.add_date, " +
                    " e.mod_date,  e.mod_by, e.attempt, p.process_name "+
                    " FROM "+
                    " clw_event e " +
                    " join clw_event_property ep " +
                    "    ON e.event_id = ep.event_id " +
                    "    AND ep.TYPE = 'PROCESS_TEMPLATE_ID' " +
                    " join clw_process p " +
                    "    ON ep.NUMBER_VAL = p.process_id " +
                    "    AND p.process_name IN ("+String.valueOf(typesA)+") " +
                    " WHERE e.TYPE  = 'PROCESS' " +
                    " AND e.status = '"+pStatus+"'" ;
            }
            String emailSql = null;
            if(emailFl) {
                emailSql =
                    " SELECT distinct e.event_id, e.status, e.type, e.cd, e.add_date, " +
                    " e.mod_date,  e.mod_by, e.attempt, e.type as process_name "+
                    " FROM clw_event e  WHERE TYPE = 'EMAIL' "+
                    " AND e.status = '"+pStatus+"'";

            }
            PreparedStatement pstmt = null;
            String sql = null;
            IdVector paramV = new IdVector();
            if(emailSql!=null && processSql!=null) {
                sql = "select * from ("+
                        processSql +
                      " union all "+
                        emailSql +
                      ") order by add_date"
                      ;
                pstmt = conn.prepareStatement(sql);
                int count = 1;
                for(Iterator iter = typeAL.iterator(); iter.hasNext();) {
                    String param = (String) iter.next();
                    pstmt.setString(count++, param);
                    paramV.add(param);
                }
                //pstmt.setString(count++, pStatus);//processes
                //paramV.add(pStatus);
                //pstmt.setString(count++, pStatus);//email
                //paramV.add(pStatus);

            } else if (processSql!=null) {
                sql = processSql + " order by e.add_date ";
                int count = 1;
                pstmt = conn.prepareStatement(sql);
                for(Iterator iter = typeAL.iterator(); iter.hasNext();) {
                    String param = (String) iter.next();
                    pstmt.setString(count++, param);
                    paramV.add(param);
                }
                //pstmt.setString(count++, pStatus);//processes
                //paramV.add(pStatus);
            } else if (emailSql!=null) {
                sql = emailSql + " order by e.add_date ";
                int count = 1;
                pstmt = conn.prepareStatement(sql);
                //pstmt.setString(count++, pStatus);//email
                //paramV.add(pStatus);
            } else { //never should happen
                return eventDataVector;
            }


            if (maxRows > 0) {
                pstmt.setMaxRows(maxRows);
            }
            String logStr = "getEventV() => Query for: "+sql+
                    "\n\r Params: "+IdVector.toCommaString(paramV);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                EventData eventData = new EventData();
                eventData.setEventId(rs.getInt(1));
                eventData.setStatus(rs.getString(2));
                eventData.setType(rs.getString(3));
                eventData.setCd(rs.getString(4));
                eventData.setAddDate(rs.getDate(5));
                eventData.setModDate(rs.getDate(6));
                eventData.setAttempt(rs.getInt(8));
                eventData.setProcess(rs.getString(9));
                eventDataVector.add(eventData);
            }
            rs.close();
            pstmt.close();

            if(eventDataVector.isEmpty()) {
                return eventDataVector;
            }

            IdVector eventIds = new IdVector();
            for(Iterator iter=eventDataVector.iterator();iter.hasNext(); ) {
                EventData ed = (EventData) iter.next();
                eventIds.add(new Integer(ed.getEventId()));
            }
            HashMap eventPropHM = getEventProperties(conn, eventIds);
            for(Iterator iter=eventDataVector.iterator();iter.hasNext(); ) {
                EventData ed = (EventData) iter.next();
                HashMap wrkHM = (HashMap) eventPropHM.get(new Integer(ed.getEventId()));
                ed.setProperties(wrkHM);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

        return eventDataVector;
    }


    /**
     * Return events with by status and set of types sorted by priority
     * @return       EventDataVector
     */
    public EventDataVector getEventVForProcessing(String pStatus, List pTypes, int maxRows, int priority)
                throws RemoteException
        {

            EventDataVector eventDataVector = new EventDataVector();
            Connection conn = null;

            try {
                conn = getConnection();
                if(pTypes == null) {
                    return eventDataVector;
                }
                boolean emailFl = false;
                ArrayList typeAL = new ArrayList();
                for(Iterator iter=pTypes.iterator(); iter.hasNext();) {
                    String type = (String) iter.next();
                    if("EMAIL".equals(type)) {
                        if (priority <= 50) {
                            emailFl = true;
                        }
                        continue;
                    }
                    typeAL.add(type);
                }
                String processSql = null;
                if(!typeAL.isEmpty()) {
                    char[] typesA = new char[typeAL.size()*2-1];
                    typesA[0] = '?';
                    for(int ii=1; ii<typeAL.size(); ii++) {
                        typesA[ii*2-1] = ',';
                        typesA[ii*2] = '?';
                    }
                    processSql =
                        " SELECT distinct e.event_id, e.status, e.type, e.cd, e.add_date, " +
                        " e.mod_date,  e.mod_by, e.attempt, p.process_name, e.event_priority "+
                        " , NVL(ep1.NUMBER_VAL, 0) " +
                        " FROM "+
                        " clw_event e " +
                        " join clw_event_property ep " +
                        "    ON e.event_id = ep.event_id " +
                        "    AND ep.TYPE = 'PROCESS_TEMPLATE_ID' " +
                        " join clw_process p " +
                        "    ON ep.NUMBER_VAL = p.process_id " +
                        "    AND p.process_name IN ("+String.valueOf(typesA)+") " +
                        " LEFT JOIN clw_event_property ep1 " +
                        "    ON e.event_id = ep1.event_id" +
                        "    AND ep1.Short_desc = '"+Event.WATING_ON+"' " +

                        " WHERE e.TYPE  = 'PROCESS' " +
                        " AND e.status = '"+pStatus+"' AND e.event_priority > " + String.valueOf(priority) ;
                }
                String emailSql = null;
                if(emailFl) {
                    emailSql =
                        " SELECT distinct e.event_id, e.status, e.type, e.cd, e.add_date, " +
                        " e.mod_date,  e.mod_by, e.attempt, e.type as process_name, e.event_priority "+
                        " , 0 NUMBER_VAL " +
                        " FROM clw_event e  WHERE TYPE = 'EMAIL' "+
                        " AND e.status = '"+pStatus+"'";

                }
                PreparedStatement pstmt = null;
                String sql = null;
                IdVector paramV = new IdVector();
                if(emailSql!=null && processSql!=null) {
                    sql = "select * from ("+
                            processSql +
                          " union all "+
                            emailSql +
                          ") order by event_priority DESC, add_date ASC"
                          ;
                    pstmt = conn.prepareStatement(sql);
                    int count = 1;
                    for(Iterator iter = typeAL.iterator(); iter.hasNext();) {
                        String param = (String) iter.next();
                        pstmt.setString(count++, param);
                        paramV.add(param);
                    }
                    //pstmt.setString(count++, pStatus);//processes
                    //paramV.add(pStatus);
                    //pstmt.setString(count++, pStatus);//email
                    //paramV.add(pStatus);

                } else if (processSql!=null) {
                    sql = processSql + " order by e.event_priority DESC, e.add_date ASC ";
                    int count = 1;
                    pstmt = conn.prepareStatement(sql);
                    for(Iterator iter = typeAL.iterator(); iter.hasNext();) {
                        String param = (String) iter.next();
                        pstmt.setString(count++, param);
                        paramV.add(param);
                    }
                    //pstmt.setString(count++, pStatus);//processes
                    //paramV.add(pStatus);
                } else if (emailSql!=null) {
                    sql = emailSql + " order by e.add_date ASC ";
                    int count = 1;
                    pstmt = conn.prepareStatement(sql);
                    //pstmt.setString(count++, pStatus);//email
                    //paramV.add(pStatus);
                } else { //never should happen
                    return eventDataVector;
                }


                if (maxRows > 0) {
                    pstmt.setMaxRows(maxRows);
                }
                String logStr = "getEventV() => Query for: "+sql+
                        "\n\r Params: "+IdVector.toCommaString(paramV);

                HashMap waitingOnHM = new HashMap();
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()){
                    EventData eventData = new EventData();
                    eventData.setEventId(rs.getInt(1));
                    eventData.setStatus(rs.getString(2));
                    eventData.setType(rs.getString(3));
                    eventData.setCd(rs.getString(4));
                    eventData.setAddDate(rs.getDate(5));
                    eventData.setModDate(rs.getDate(6));
                    eventData.setAttempt(rs.getInt(8));
                    eventData.setProcess(rs.getString(9));
                    eventData.setPriority(rs.getInt(10));
                    waitingOnHM.put(new Integer(rs.getInt(1)), new Integer(rs.getInt(11)));
                    eventDataVector.add(eventData);
                }
                rs.close();
                pstmt.close();

                if(eventDataVector.isEmpty()) {
                    return eventDataVector;
                }

                IdVector eventIds = new IdVector();
                for(Iterator iter=eventDataVector.iterator();iter.hasNext(); ) {
                    EventData ed = (EventData) iter.next();
                    int waitingOnEventId = ((Integer)waitingOnHM.get(new Integer(ed.getEventId()))).intValue();
                    if (isNotWaitingOnEvent(ed, waitingOnEventId, conn)){
                      eventIds.add(new Integer(ed.getEventId()));
                    }
                }
                if(eventIds.isEmpty()) {
                    return new EventDataVector();
                }

                IdVector updEventIds = new IdVector();
                updEventIds = getEventVWithUpdateStatus(conn, eventIds, pStatus, Event.STATUS_IN_PROGRESS);

                EventDataVector updEventDataVector = new EventDataVector();
                for(Iterator iter=eventDataVector.iterator();iter.hasNext(); ) {
                    EventData ed = (EventData) iter.next();
                    if (updEventIds.contains(ed.getEventId())) {
                      updEventDataVector.add(ed);
                    }
                }

                HashMap eventPropHM = getEventProperties(conn, updEventIds);
                for(Iterator iter=updEventDataVector.iterator();iter.hasNext(); ) {
                    EventData ed = (EventData) iter.next();
                    HashMap wrkHM = (HashMap) eventPropHM.get(new Integer(ed.getEventId()));
                    ed.setProperties(wrkHM);
                }
                eventDataVector = updEventDataVector;

            } catch (Exception e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
            finally {
                closeConnection(conn);
            }

            return eventDataVector;
        }

        private IdVector getEventVWithUpdateStatus(Connection conn,
                                                   IdVector eventIds,
                                                   String oldStatus,
                                                   String newStatus) throws SQLException {

            //HashMap prop = new HashMap();

        	log.info(".getEventVWithUpdateStatus(): oldStatus="+oldStatus+" newStatus="+newStatus+" eventIds="+eventIds);
            
        	String query = "SELECT event_id" +
                    " FROM clw_event " +
                    " WHERE status = ? " +
                   ( (Utility.isSet(eventIds)) ? " AND event_id IN (" + IdVector.toCommaString(eventIds) + ")" : "") +
                    " order by event_id " +
                    " FOR UPDATE NOWAIT ";
        	
        	log.info(".getEventVWithUpdateStatus(): query="+query);
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, oldStatus);
            ResultSet rs = sttm.executeQuery();
            IdVector ids = new IdVector();
            while (rs.next()) {
                int eventId = rs.getInt(1);
                ids.add(eventId);
            }
            rs.close();
            sttm.close();
            log.info(".getEventVWithUpdateStatus(): IdVector.toCommaString(ids)="+ ((Utility.isSet(eventIds)) ? IdVector.toCommaString(ids) : ""));
            query = "UPDATE clw_event" +
                    " SET status = ? " +
                    " WHERE " +
                    ( (Utility.isSet(ids)) ? " event_id IN (" + IdVector.toCommaString(ids) + ")" : "") ;
                   // "event_id IN (" + IdVector.toCommaString(ids) + ") ";
            sttm = conn.prepareStatement(query);
            sttm.setString(1, newStatus);
            //rs = sttm.executeQuery();   //old statement, commented by SVC - it failed with Postgres DB
            int n = sttm.executeUpdate(); //new statement, put here by SVC
            log.info(".getEventVWithUpdateStatus(): Number of updated events = " + n);
            log.info(".getEventVWithUpdateStatus(): ids = " + ids);
            return ids;
        }



    private HashMap getEventProperties(int eventId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getEventProperties(conn, eventId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private HashMap getEventProperties(Connection conn, int eventId) throws SQLException {

        HashMap prop = new HashMap();

        String query = "SELECT short_desc, num, type, blob_value, string_val, number_val, date_val, var_class," +
                               " blob_value_system_ref, binary_data_server, storage_type_cd" +
                " FROM clw_event_property\n" +
                " WHERE event_id = " + eventId + " order by type,num";

        Statement sttm = conn.createStatement();
        ResultSet rs = sttm.executeQuery(query);

        while (rs.next()) {

            String shortDesc = rs.getString(1);
            int num = rs.getInt(2);
            String type = rs.getString(3); 

            byte[] blobValueBytes = null;
            Object blobObject = null;
            if (ORACLE.equals(databaseName)) {
                BLOB blob = (BLOB) rs.getBlob(4); //Oracle Database ONLY
                if (blob != null) {
                    blobObject = bytesToObject(blob.getBytes(1L, (int) blob.length()));
                }
            } else {
                blobValueBytes = rs.getBytes(4); //Enterprise DB (Postgres DB)
                if (blobValueBytes != null) {
                    blobObject = bytesToObject(blobValueBytes);
                }
            }
            String str_val = rs.getString(5);
            int num_val = rs.getInt(6);
            java.util.Date date = rs.getTimestamp(7);
            String var_class = rs.getString(8);
            
            // STJ-6037
            String blob_value_system_ref = rs.getString(9);
            String binary_data_server = rs.getString(10);
            String storage_type_cd = rs.getString(11);

            if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(storage_type_cd) ||
				BlobStorageAccess.STORAGE_NFS.equals(storage_type_cd) ||
                BlobStorageAccess.STORAGE_FTP.equals(storage_type_cd)) {
                if (Utility.isSet(blob_value_system_ref)) {
                    blobValueBytes = bsa.readBlob(storage_type_cd,
                                                  binary_data_server,
                                                  blob_value_system_ref);
                    if (blobValueBytes != null) {
                        blobObject = bytesToObject(blobValueBytes);
                    }
                }
            } /*else {
                if (blobObject != null) {
                    log.error("ERROR. Blob has been read from DB. EventID: " + eventId +
                                      " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                }
            }*/

            if (!prop.containsKey(type)) {
                PairViewVector propVal = new PairViewVector();
                if (RefCodeNames.PROCESS_VAR_CLASS.STRING.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, str_val));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.INTEGER.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, new Integer(num_val)));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.DATE.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, date));
                } else {
                       /* STJ-6037
                	if (ORACLE.equals(databaseName)) {
                       propVal.add(new PairView(shortDesc, bytesToObject(blob.getBytes(1L, (int) blob.length())))); //Orcale DB
                	} else { //Postgres
                		propVal.add(new PairView(shortDesc, bytesToObject(blobValueBytes))); //new stmt for Postgres
                	}
		       */
                    propVal.add(new PairView(shortDesc, blobObject));
                }
                prop.put(type, propVal);
            } else {
                PairViewVector propVal = (PairViewVector) prop.get(type);
                if (RefCodeNames.PROCESS_VAR_CLASS.STRING.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, str_val));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.INTEGER.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, new Integer(num_val)));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.DATE.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, date));
                } else {
                    /* STJ-6037
                	if (ORACLE.equals(databaseName)) {
                       propVal.add(new PairView(shortDesc, bytesToObject(blob.getBytes(1L, (int) blob.length()))));
                	} else {
                	   propVal.add(new PairView(shortDesc, bytesToObject(blobValueBytes)));
                	}
                    */
                    propVal.add(new PairView(shortDesc, blobObject));
                }
            }
        }

        rs.close();
        sttm.close();

        return prop;
    }

    private HashMap getEventProperties(Connection conn, IdVector eventIds) throws SQLException {

        //HashMap prop = new HashMap();

        String query = "SELECT event_id, short_desc, num, type, blob_value, string_val, number_val, date_val, var_class," +
                               " blob_value_system_ref, binary_data_server, storage_type_cd" +
                " FROM clw_event_property\n" +
                " WHERE event_id IN (" + IdVector.toCommaString(eventIds) + ") order by event_id, type,num";

        Statement sttm = conn.createStatement();
        ResultSet rs = sttm.executeQuery(query);
        int prevEventId = 0;
        HashMap eventHM = new HashMap();
        HashMap wrkHM = null;
        while (rs.next()) {
            int eventId = rs.getInt(1);
            if(prevEventId!=eventId) {
                prevEventId = eventId;
                wrkHM = new HashMap();
                eventHM.put(new Integer(eventId), wrkHM);
            }

            String shortDesc = rs.getString(2);
            int num = rs.getInt(3);
            String type = rs.getString(4);

            // STJ-6037
            //BLOB blob = null;
            //byte[] blobValueBytes = null;
            //if (ORACLE.equals(databaseName)) {
            //   blob = (BLOB) rs.getBlob(5); //Oracle DB
            //} else {
            //   blobValueBytes = rs.getBytes(5);  //Enterprise DB (Postgres DB)
            //}
            
            byte[] blobValueBytes = null;
            Object blobObject = null;
            if (ORACLE.equals(databaseName)) {
                BLOB blob = (BLOB) rs.getBlob(5); //Oracle Database ONLY
                if (blob != null) {
                    blobObject = bytesToObject(blob.getBytes(1L, (int) blob.length()));
                }
            } else {
                blobValueBytes = rs.getBytes(5); //Enterprise DB (Postgres DB)
                if (blobValueBytes != null) {
                    blobObject = bytesToObject(blobValueBytes);
                }
            }

            String str_val = rs.getString(6);            
            int num_val = rs.getInt(7);
            java.util.Date date = rs.getTimestamp(8);
            String var_class = rs.getString(9);
            String blob_value_system_ref = rs.getString(10);
            String binary_data_server = rs.getString(11);
            String storage_type_cd = rs.getString(12);
            
            // STJ-6037
            if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(storage_type_cd) ||
				BlobStorageAccess.STORAGE_NFS.equals(storage_type_cd) ||
                BlobStorageAccess.STORAGE_FTP.equals(storage_type_cd)) {
                if (Utility.isSet(blob_value_system_ref)) {
                    blobValueBytes = bsa.readBlob(storage_type_cd,
                                                  binary_data_server,
                                                  blob_value_system_ref);
                    if (blobValueBytes != null) {
                        blobObject = bytesToObject(blobValueBytes);
                    }
                }
            } /*else {
                if (blobObject != null) {
                    log.error("ERROR. Blob has been read from DB. EventID: " + eventId +
                                      " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                }
            }*/
           //---------------------------------------------------------------
            if (!wrkHM.containsKey(type)) {
                PairViewVector propVal = new PairViewVector();
                if (RefCodeNames.PROCESS_VAR_CLASS.STRING.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, str_val));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.INTEGER.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, new Integer(num_val)));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.DATE.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, date));
                } else {
                    //propVal.add(new PairView(shortDesc, bytesToObject(blob.getBytes(1L, (int) blob.length()))));
                    propVal.add(new PairView(shortDesc, blobObject));
                }
                wrkHM.put(type, propVal);
            } else {
                PairViewVector propVal = (PairViewVector) wrkHM.get(type);
                if (RefCodeNames.PROCESS_VAR_CLASS.STRING.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, str_val));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.INTEGER.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, new Integer(num_val)));
                } else if (RefCodeNames.PROCESS_VAR_CLASS.DATE.equals(var_class)) {
                    propVal.add(new PairView(shortDesc, date));
                } else {
                    //if (ORACLE.equals(databaseName)) {
                    //   propVal.add(new PairView(shortDesc, bytesToObject(blob.getBytes(1L, (int) blob.length()))));
                    //} else {
                    //   propVal.add(new PairView(shortDesc, bytesToObject(blobValueBytes)));
                    //}
                    propVal.add(new PairView(shortDesc, blobObject));
                }
            }
        }

        rs.close();
        sttm.close();

        return eventHM;
    }

    /**
     * send event to process
     *
     * @param pEvent process view
     * @param pUser  user
     * @return event view
     * @throws RemoteException if an errors
     */
    public EventProcessView addEventProcess(EventProcessView pEvent, String pUser) throws RemoteException {
        Connection conn = null;
        try {
		    log.info("addEventProcess. Event: "+pEvent.getEventData());
		    
            conn = getConnection();
            return addEventProcess(conn, pEvent, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public EventProcessView addEventProcess(Connection pCon, EventProcessView pEvent, String pUser) throws Exception {

        com.cleanwise.service.api.value.EventData eventData = pEvent.getEventData();
        EventPropertyDataVector properties = pEvent.getProperties();

        if (Utility.isSet(pUser)) {
            eventData.setAddBy(pUser);
            eventData.setModBy(pUser);
        }
        
        int eventPriority = 0;
        if (pEvent.getParentEventId() > 0){
        	EventPropertyDataVector epDV = getEventPropertiesAsVector(pEvent.getParentEventId());
        	EventPropertyData evD = getEventPropertyData(epDV, Event.PROCESS_SUBPROCESS_PRIORITY);
            if (evD != null){
            	eventPriority = evD.getNumberVal();
            }
            if (eventPriority > 0){
            	pEvent.getProperties().add(Utility.createEventPropertyData(Event.SUBPROCESS_PRIORITY,
        				Event.PROCESS_SUBPROCESS_PRIORITY, eventPriority,
        				1));
            }
        }else{
	        EventPropertyData epD = getEventPropertyData(properties, Event.PROCESS_PRIORITY);
	        if (epD != null){
	        	eventPriority = epD.getNumberVal();
	        }
        }
        if (eventPriority == 0){
        	EventPropertyData epD = getEventPropertyData(properties, Event.PROPERTY_PROCESS_TEMPLATE_ID);
        	if (epD != null){
        		ProcessData pD = ProcessDataAccess.select(pCon, epD.getNumberVal());
        		eventPriority = pD.getProcessPriority();
        	}
        }
        eventData.setEventPriority(eventPriority);

        eventData = EventDataAccess.insert(pCon, eventData);

        if (properties != null && !properties.isEmpty()) {
            for (int i = 0; i < properties.size(); i++) {
                EventPropertyData property = (EventPropertyData) properties.get(i);
                property.setEventId(eventData.getEventId());
                if (Utility.isSet(pUser)) {
                    property.setAddBy(pUser);
                    property.setModBy(pUser);
                }
                //STJ-6037
				if("parameters".equals(property.getShortDesc())) {
				   log.info("Saving Event Property with blob. Event id: "+property.getEventId());
				   log.info("Saving Event Property with blob. Blob existst: "+(property.getBlobValue() != null));
				}
                if (property.getBlobValue() != null) {
                    String blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                            BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                            eventData.getEventId());
				    log.info("Saving Event Property with blob. blobFileName: "+blobFileName);
                    boolean isOK = bsa.storeBlob(property.getBlobValue(), blobFileName);
				    log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
                    if (isOK && bsa.getStoredToStorageType() != null &&
                    	bsa.getStoredToHostName() != null	) { // blob has been stored
                        property.setBlobValueSystemRef(blobFileName);
                        property.setStorageTypeCd(bsa.getStoredToStorageType());
                        property.setBinaryDataServer(bsa.getStoredToHostName());

                        property.setBlobValue(null);
                    } else {
                        log.error("ERROR. Blob will be written into DB. EventID: " + property.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                        property.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                        property.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                    }
                }
                property = EventPropertyDataAccess.insert(pCon, property);
                properties.set(i, property);
            }
        }

        pEvent.setEventData(eventData);
        pEvent.setProperties(properties);

        return pEvent;
    }
    
    private EventPropertyData getEventPropertyData(EventPropertyDataVector properties, String propertyType){
    	if (properties != null && !properties.isEmpty()) {
            for (int i = 0; i < properties.size(); i++) {
                EventPropertyData property = (EventPropertyData) properties.get(i);
                if (property.getType().equals(propertyType))
                	return property;
            }
        }
    	return null;
    }

    /**
     * send event to process
     *
     * @param pEvent email view
     * @param pUser  user
     * @return event view
     * @throws RemoteException if an errors
     */
    public EventEmailView addEventEmail(EventEmailView pEvent, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return addEventEmail(conn, pEvent, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    public EventEmailView addEventEmail(EventEmailView pEvent, String pUser, int parentEventId) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return addEventEmail(conn, pEvent, pUser, parentEventId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
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
    public EventEmailView addEventEmailNoTx(EventEmailView pEvent, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnectionNoTx();
            return addEventEmail(conn, pEvent, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private EventEmailView addEventEmail(Connection pCon, EventEmailView pEvent, String pUser) throws Exception {
    	return addEventEmail(pCon, pEvent, pUser, 0);
    }
    private EventEmailView addEventEmail(Connection pCon, EventEmailView pEvent, String pUser, int parentEventId) throws Exception {

        com.cleanwise.service.api.value.EventData eventData = pEvent.getEventData();
        EventEmailData property = pEvent.getEmailProperty();
        byte[] attachments = null;
        byte[] longText = null;
        if(property instanceof EventEmailDataView) {
            FileAttach[] files = ((EventEmailDataView)property).getFileAttachments();
            if (files!= null && files.length>0) {
               attachments = objectToBytes(files);
               property.setAttachments(attachments);
            }
        }
        if (Utility.isSet(pUser)) {
            eventData.setAddBy(pUser);
            eventData.setModBy(pUser);
        }

        int eventPriority = 50;
        if (parentEventId > 0){
        	EventPropertyDataVector epDV = getEventPropertiesAsVector(parentEventId);
        	EventPropertyData evD = getEventPropertyData(epDV, Event.PROCESS_SUBPROCESS_PRIORITY);
            if (evD != null){
            	eventPriority = evD.getNumberVal();
            }            
        }
        eventData.setEventPriority(eventPriority);
        eventData = EventDataAccess.insert(pCon, eventData);

        if (property != null) {
log.info("Email TEXT : "+	property.getText());	
            property.setEventId(eventData.getEventId());
            if (Utility.isSet(pUser)) {
                property.setAddBy(pUser);
                property.setModBy(pUser);
            }
            if (property.getText() != null
                    && property.getText().getBytes("UTF-8").length > 4000) {
            	//STJ-3673
                longText = property.getText().getBytes("UTF-8");
                property.setLongText(longText);
                property.setText("");
            }
            if (attachments != null || longText != null) {
                if (attachments != null) {
                    String blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                            BlobStorageAccess.FIELD_ATTACHMENTS,
                                                                            eventData.getEventId());
                    boolean isOK = bsa.storeBlob(attachments, blobFileName);
				    log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

                    if (isOK && bsa.getStoredToStorageType() != null &&
                        bsa.getStoredToHostName() != null) { // blob has been stored
                        property.setAttachmentsSystemRef(blobFileName);
                        property.setStorageTypeCd(bsa.getStoredToStorageType());
                        property.setBinaryDataServer(bsa.getStoredToHostName());

                        property.setAttachments(null);
                    } else {
                        log.error("ERROR. Blob will be written into DB. EventID: " + property.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_ATTACHMENTS + ")");
                        property.setAttachmentsSystemRef(null);
                        property.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                        property.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                        property.setAttachments(attachments);
                    }
                }
                if (longText != null) {
                    String blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                            BlobStorageAccess.FIELD_LONG_TEXT,
                                                                            eventData.getEventId());
                    boolean isOK = bsa.storeBlob(longText, blobFileName);
				    log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

                    if (isOK && bsa.getStoredToStorageType() != null &&
                        bsa.getStoredToHostName() != null) { // blob has been stored
                        property.setLongTextSystemRef(blobFileName);
                        property.setStorageTypeCd(bsa.getStoredToStorageType());
                        property.setBinaryDataServer(bsa.getStoredToHostName());

                        property.setLongText(null);
                    } else {
                        log.error("ERROR. Blob will be written into DB. EventID: " + property.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_LONG_TEXT + ")");
                        property.setLongTextSystemRef(null);
                        property.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                        property.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                        property.setLongText(longText);
                    }
                }
            }
            
            property = EventEmailDataAccess.insert(pCon, property);
        }

        pEvent.setEventData(eventData);
        pEvent.setEmailProperty(property);

        return pEvent;
    }

//    /**
//     * Add new record to clw_event table
//     * @param event EventData
//     */
//    public EventData addEventToDB(EventData event) throws RemoteException
//    {
//        Connection conn = null;
//
//        try {
//            conn = getConnection();
//            return addEventToDB(conn,event);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RemoteException(e.getMessage());
//        } finally {
//            closeConnection(conn);
//        }
//    }


    /**
     * Add new record to clw_event table
     * @param Connection valid database connection
     * @param event EventData
     */
    private EventData addEventToDB(Connection conn,EventData event) throws SQLException{
		String query =
	            "INSERT INTO clw_event\n" +
	                    "            (event_id, status, TYPE, cd, add_date, mod_date, mod_by, attempt\n" +
	                    "            )\n" +
	                    "     VALUES (?, ?, ?, ?, sysdate, sysdate, null, ?\n" +
	                    "            )";
	    Statement stmt = conn.createStatement();

	    ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_SEQ.NEXTVAL FROM DUAL");
	    rs.next();
	    int keyId = rs.getInt(1);
	    rs.close();
	    stmt.close();

	    PreparedStatement sttm = conn.prepareStatement(query);
	    sttm.setInt   (1, keyId);
	    sttm.setString(2, event.getStatus());
	    sttm.setString(3, event.getType());
	    sttm.setString(4, event.getCd());
	    sttm.setInt   (5, event.getAttempt());
	    sttm.executeUpdate();
	    sttm.close();

	    event.setEventId(keyId);

	    return event;
    }

    public void addProperty(int eventId,
                            String shortDesc,
                            String eventTypeCd,
                            Object value, int num) throws RemoteException {
        Connection conn = null;
        log.info("addProperty() method: eventId="+eventId+" shortDesc="+shortDesc+" eventTypeCd="+eventTypeCd+" value="+value+" num="+num);
        try {
            //Create record for blob
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_PROPERTY_SEQ.NEXTVAL FROM DUAL");
            rs.next();
            int keyId = rs.getInt(1);
            rs.close();
            stmt.close();

            String varField = null;

            if (value instanceof String) {
                varField = EventPropertyDataAccess.STRING_VAL;
            } else if (value instanceof Integer) {
                varField = EventPropertyDataAccess.NUMBER_VAL;
            } else if (value instanceof Date) {
                varField = EventPropertyDataAccess.DATE_VAL;
            } else {
                varField = EventPropertyDataAccess.BLOB_VALUE;
            }

            log.info("addProperty() method: varField = " + varField);
            
            String insertSql =
                    " insert into CLW_EVENT_PROPERTY ( " +
                            " EVENT_PROPERTY_ID," +
                            " EVENT_ID," +
                            " SHORT_DESC," +
                            " TYPE," +
                            varField +","+
                            " NUM ," +
                            " VAR_CLASS,"+
                            " ADD_BY," +
                            " ADD_DATE," +
                            " MOD_BY," +
                            " MOD_DATE " +
                            " ) values  (?,?,?,?,?,?,?,?,?,?,?) ";


            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, keyId);
            pstmt.setInt(2, eventId);
            pstmt.setString(3, shortDesc);
            pstmt.setString(4, eventTypeCd);

            if (EventPropertyDataAccess.STRING_VAL.equals(varField)) {
                pstmt.setString(5, (String) value);
            } else if (EventPropertyDataAccess.NUMBER_VAL.equals(varField)) {
                pstmt.setInt(5, ((Integer) value).intValue());
            } else if (EventPropertyDataAccess.DATE_VAL.equals(varField)) {
                pstmt.setTimestamp(5, DBAccess.toSQLTimestamp((java.util.Date) value));
            } else {
                if (ORACLE.equals(databaseName)) {
                    pstmt.setBlob(5, (BLOB) null);
                    //pstmt.setBlob(5, BLOB.empty_lob());
                } else if (EDB.equals(databaseName)) {
                    pstmt.setBytes(5, null);
                } else {
                  throw new Exception("Unknown database");
                }
            }

            pstmt.setInt(6, num);
            pstmt.setString(7,value.getClass().getName());
            pstmt.setString(8, className);
            pstmt.setTimestamp(9, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
            pstmt.setString(10, className);
            pstmt.setTimestamp(11, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));

            pstmt.executeUpdate();
            pstmt.close();

            if (EventPropertyDataAccess.BLOB_VALUE.equals(varField)) {
                updatePropertyBlobValue(conn, keyId, objectToBytes(value));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public EventEmailDataView addEventEmail(EventEmailDataView email) throws RemoteException {
        return addEventEmail(email, 0);
    }

    public EventEmailDataView addEventEmail(EventEmailDataView em, int parentEventId) throws RemoteException {
    	Connection conn = null;
        try {
            conn = getConnection();
            return updateEventEmail(conn, em, parentEventId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException();
        } finally {
            closeConnection(conn);
        }
    }
    
    private EventEmailDataView updateEventEmail(Connection conn, EventEmailDataView emailView) throws Exception {
    	return updateEventEmail(conn, emailView, 0);
    }
    
    private EventEmailDataView updateEventEmail(Connection conn, EventEmailDataView emailView, int parentEventId) throws Exception {

        if (emailView != null) {
            if (emailView.getEventEmailId() > 0) {
                updateEmailAttachments(conn, emailView.getEventEmailId(), emailView);
            } else {
                emailView = insertEventEmail(conn, emailView, parentEventId);
            }
        }

        return emailView;
    }

    /**
     * Add Event Email to DB
     * @param conn  Current connection to DB
     * @param emailView Data of an event email
     * @return
     * @throws Exception
     */
    private EventEmailDataView insertEventEmail(Connection conn, EventEmailDataView emailView, int parentEventId) throws Exception {

        if((emailView==null)||(conn==null)){
            return null;
        }

        if(emailView.getEventId() == 0){
//        	EventData eventData = new EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//        	eventData = addEventToDB(conn,eventData);
//        	emailView.setEventId(eventData.getEventId());
            com.cleanwise.service.api.value.EventData eventData = Utility.createEventDataForEmail();
            EventEmailView eev = new EventEmailView(eventData, emailView);
            addEventEmail(eev, emailView.getModBy(), parentEventId);
            return emailView;
        }

        if((emailView.getToAddress()==null)||(emailView.getToAddress().trim().equals(""))){
            return null;
        }

        if(emailView.getText()==null){
            emailView.setText("");
        }
        byte[] messBytes = null;
        byte[] attachBytes = null;
        if(emailView.getText().getBytes("UTF-8").length > 4000){
          messBytes = emailView.getText().getBytes();
          emailView.setText("");
        }
        if (emailView.getFileAttachments() != null) {
           attachBytes = objectToBytes(emailView.getFileAttachments());
        }

/*
        if(emailView.getText().length()>3998){
            emailView.setText(emailView.getText().substring(0, 3900)+"\n\r ... (Truncated) ...");
            //return null;
        }
*/
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_EMAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        int keyId = rs.getInt(1);
        rs.close();
        stmt.close();

        // STJ-6037
        String insertSql =
                " insert into CLW_EVENT_EMAIL ( " +
                        " EVENT_EMAIL_ID," +
                        " EVENT_ID," +
                        " TO_ADDRESS," +
                        " CC_ADDRESS," +
                        " SUBJECT," +
                        " TEXT ," +
                        " IMPORTANCE ," +
                        " EMAIL_STATUS_CD ," +
                        //" ATTACHMENTS ," +
                        " ADD_BY," +
                        " ADD_DATE," +
                        " MOD_BY," +
                        " MOD_DATE," +
                        " FROM_ADDRESS," +
                        //" LONG_TEXT " +
                        //" ) values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                        " ) values  (?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        PreparedStatement pstmt = conn.prepareStatement(insertSql);

        pstmt.setInt      (1,  keyId);
        pstmt.setInt      (2,  emailView.getEventId());
        pstmt.setString   (3,  emailView.getToAddress());
        pstmt.setString   (4,  emailView.getCcAddress());

        pstmt.setString   (5,  emailView.getSubject());
        pstmt.setString   (6,  emailView.getText());
        pstmt.setString   (7,  emailView.getImportance());
        pstmt.setString   (8,  emailView.getEmailStatusCd());
        /*
        if (ORACLE.equals(databaseName)) {
            pstmt.setBlob(9, BLOB.empty_lob());
        } else if (EDB.equals(databaseName)) {
            pstmt.setBytes(9, null);
        } else {
          throw new Exception("Unknown database");
        }
        */
        pstmt.setString   (9, className);
        pstmt.setTimestamp(10, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
        pstmt.setString   (11, className);
        pstmt.setTimestamp(12, DBAccess.toSQLTimestamp(new Date(System.currentTimeMillis())));
        pstmt.setString   (13,  emailView.getFromAddress());
        /*
        if (ORACLE.equals(databaseName)) {
            pstmt.setBlob(15, BLOB.empty_lob());
        } else if (EDB.equals(databaseName)) {
            pstmt.setBytes(15, null);
        } else {
          throw new Exception("Unknown database");
        }
        */
        pstmt.executeUpdate();
        pstmt.close();

        emailView.setEventEmailId(keyId);

        if ((attachBytes != null && attachBytes.length > 0) ||
            (messBytes != null && messBytes.length > 0)) {
            updateEmailBlobs(conn, keyId, messBytes, attachBytes);
        }
/*
        if (emailView.getFileAttachments() != null) {
            updateEmailAttachments(conn, keyId, objectToBytes(emailView.getFileAttachments()));
        }
*/
        return emailView;
    }

	
    private boolean updateEmailBlobs (Connection conn, int keyId, byte[] messBytes, byte[] attachBytes) throws Exception {

         Statement stmt = conn.createStatement();
         /*
         String sql = "select ATTACHMENTS, LONG_TEXT from CLW_EVENT_EMAIL " +
                 " where EVENT_EMAIL_ID = " + keyId +
                 " for update ";
         */
         
         // STJ-6037
         String sql = "select EVENT_ID, " + 
                             "BINARY_DATA_SERVER, " +
                             "ATTACHMENTS_SYSTEM_REF, " +
                             "LONG_TEXT_SYSTEM_REF, " +
                             "STORAGE_TYPE_CD, " +
                             "ATTACHMENTS, " +
                             "LONG_TEXT " +
                      "from CLW_EVENT_EMAIL where EVENT_EMAIL_ID = " + keyId + 
                      " for update";
         ResultSet rs = stmt.executeQuery(sql);
         rs.next();
         
         int eventId = rs.getInt(1);
         String binaryDataServer = rs.getString(2);
         String attachmentsSystemRef = rs.getString(3);
         String longTextSystemRef = rs.getString(4);
         String storageTypeCd = rs.getString(5);
         
         BLOB blobA = null;
         BLOB blobL = null;
         
         if (attachBytes != null && attachBytes.length > 0) {
             if (ORACLE.equals(databaseName)) {
                 blobA = (BLOB) rs.getBlob(6);
 //                setByteToOracleBlob(blobA, attachBytes);
             }
         }
         if (messBytes != null && messBytes.length > 0) {
             if (ORACLE.equals(databaseName)) {
                 blobL = (BLOB) rs.getBlob(7);
 //                setByteToOracleBlob(blobL, messBytes);
             }
         }
         
         rs.close();
         stmt.close();
        
        boolean writeAttachments = false;
        boolean writeLongText = false;
        if ((attachBytes != null && attachBytes.length > 0) ||
            (messBytes != null && messBytes.length > 0)) {
           
           if (attachBytes != null && attachBytes.length > 0) {
               if (!Utility.isSet(attachmentsSystemRef)) {
                    attachmentsSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                             BlobStorageAccess.FIELD_ATTACHMENTS,
                                                                             eventId);
               }
               boolean isOK = bsa.storeBlob(attachBytes, attachmentsSystemRef);
			   log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

               if (isOK && bsa.getStoredToStorageType() != null &&
                   bsa.getStoredToHostName() != null) { // blob has been stored
                   storageTypeCd = bsa.getStoredToStorageType();
                   binaryDataServer = bsa.getStoredToHostName();
               } else {
                    log.error("ERROR. Blob will be written into DB. EventID: " + eventId + 
                              " (" + BlobStorageAccess.FIELD_ATTACHMENTS + ")");
                    storageTypeCd = BlobStorageAccess.STORAGE_DB;
                    binaryDataServer = BlobStorageAccess.getCurrentHost();
					attachmentsSystemRef = null;
                    writeAttachments = true;
               }
           }
           if (messBytes != null && messBytes.length > 0) {
                if (!Utility.isSet(longTextSystemRef)) {
                    longTextSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                          BlobStorageAccess.FIELD_LONG_TEXT,
                                                                          eventId);
                }
                boolean isOK = bsa.storeBlob(messBytes, longTextSystemRef);
			    log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

                if (isOK && bsa.getStoredToStorageType() != null &&
                    bsa.getStoredToHostName() != null) { // blob has been stored
                    storageTypeCd = bsa.getStoredToStorageType();
                    binaryDataServer = bsa.getStoredToHostName();
                } else {
                    log.error("ERROR. Blob will be written into DB. EventID: " + eventId + 
                              " (" + BlobStorageAccess.FIELD_LONG_TEXT + ")");
                    storageTypeCd = BlobStorageAccess.STORAGE_DB;
                    binaryDataServer = BlobStorageAccess.getCurrentHost();
					longTextSystemRef = null;
                    writeLongText = true;
                }
           }
        }
		// Init Oracle Blobs if necessary 
		if(ORACLE.equals(databaseName)) {
			if(writeAttachments) {
				if (blobA == null) {
					blobA = createTemporaryBlob(conn);
				}
                setByteToOracleBlob(blobA, attachBytes);
			}
			if(writeLongText) {
				if (blobL == null) {
					blobL = createTemporaryBlob(conn);
				}
                setByteToOracleBlob(blobL, messBytes);
			}
		 }
        
         //String updateBlobSql = "update CLW_EVENT_EMAIL set ATTACHMENTS=?, LONG_TEXT=?  where EVENT_EMAIL_ID = " + keyId;
         
         String updateBlobSql = "update CLW_EVENT_EMAIL " +
                                "set BINARY_DATA_SERVER=?, " +
                                "ATTACHMENTS_SYSTEM_REF=?, " +
                                "LONG_TEXT_SYSTEM_REF=?, " +
                                "STORAGE_TYPE_CD=?, " +
                                "MOD_BY=?, " +
                                "MOD_DATE=?" +
                                (writeAttachments ? ", ATTACHMENTS=?" : "") +
                                (writeLongText ? ", LONG_TEXT=?" : "") +
                                " where EVENT_EMAIL_ID = " + keyId;
         
         PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
         
         
         
         java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

         pstmt.setString(1, binaryDataServer);
         pstmt.setString(2, attachmentsSystemRef);
         pstmt.setString(3, longTextSystemRef);
         pstmt.setString(4, storageTypeCd);
         pstmt.setString(5, className);
         pstmt.setTimestamp(6, DBAccess.toSQLTimestamp(currDate));         
        
         if (writeAttachments) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(7, blobA);
            } else if (EDB.equals(databaseName)) {
                pstmt.setBytes(7, attachBytes);
            } else {    
                throw new Exception("Unknown database");
            }
         }
         if (writeLongText) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(8, blobL);
            } else if (EDB.equals(databaseName)) {  
                ByteArrayInputStream isA = new ByteArrayInputStream(messBytes);
                pstmt.setBinaryStream(8, isA, isA.available());
                pstmt.setBytes(8, messBytes);
            } else {
                throw new Exception("Unknown database");
            }
         }
         
         pstmt.executeUpdate();
         pstmt.close();

         return true;
    }

    private boolean updateEmailAttachments(Connection conn, int keyId, byte[] bytes) throws Exception {

        Statement stmt = conn.createStatement();
        
        /*
        String sql = "select ATTACHMENTS from CLW_EVENT_EMAIL " +
                " where EVENT_EMAIL_ID = " + keyId +
                " for update ";
        */
        
        // STJ-6037
        String sql = "select EVENT_ID, " +
                            "BINARY_DATA_SERVER, " +
                            "ATTACHMENTS_SYSTEM_REF, " +
                            "STORAGE_TYPE_CD, " +
                            "ATTACHMENTS " +
                     "from CLW_EVENT_EMAIL where EVENT_EMAIL_ID = " + keyId +
                     " for update";

        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        
        int eventId = rs.getInt(1);
        String binaryDataServer = rs.getString(2);
        String attachmentsSystemRef = rs.getString(3);
        String storageTypeCd = rs.getString(4);

        BLOB blob = null;
        if (bytes != null && bytes.length > 0) {
            if (ORACLE.equals(databaseName)) {
                blob = (BLOB) rs.getBlob(5);
//                setByteToOracleBlob(blob, bytes);
            }
        }

        rs.close();
        stmt.close();
        
        boolean writeAttachments = false;
        if (!Utility.isSet(attachmentsSystemRef)) {
             attachmentsSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                      BlobStorageAccess.FIELD_ATTACHMENTS,
                                                                      eventId);
        }
        boolean isOK = bsa.storeBlob(bytes, attachmentsSystemRef);
	    log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

        if (isOK && bsa.getStoredToStorageType() != null &&
            bsa.getStoredToHostName() != null) { // blob has been stored
             storageTypeCd = bsa.getStoredToStorageType();
             binaryDataServer = bsa.getStoredToHostName();
        } else {
            log.error("ERROR. Blob will be written into DB. EventID: " + eventId + 
                              " (" + BlobStorageAccess.FIELD_ATTACHMENTS + ")");
            storageTypeCd = BlobStorageAccess.STORAGE_DB;
            binaryDataServer = BlobStorageAccess.getCurrentHost();
            writeAttachments = true;
        }

        //String updateBlobSql = "update CLW_EVENT_EMAIL set ATTACHMENTS=?  where EVENT_EMAIL_ID = " + keyId;
		if(writeAttachments && ORACLE.equals(databaseName)) {
			if(blob == null) {
				blob = createTemporaryBlob(conn);
			}
            setByteToOracleBlob(blob, bytes);
		}
		
        
        String updateBlobSql = "update CLW_EVENT_EMAIL " +
                               "set BINARY_DATA_SERVER=?, " +
                               "ATTACHMENTS_SYSTEM_REF=?, " +
                               "STORAGE_TYPE_CD=?, " +
                               "MOD_BY=?, " +
                               "MOD_DATE=? " +
                               (writeAttachments ? ", ATTACHMENTS=?" : "") +
                               " where EVENT_EMAIL_ID = " + keyId;

        PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
        
        //if (ORACLE.equals(databaseName)) {
        //    pstmt.setBlob(1, blob);
        //} else if (EDB.equals(databaseName)) {
        	/***
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            pstmt.setBinaryStream(1, is, is.available());
            ***/
        //	pstmt.setBytes(1,bytes);
        //} else {
        //  throw new Exception("Unknown database");
        //}
        
        java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

        pstmt.setString(1, binaryDataServer);
        pstmt.setString(2, attachmentsSystemRef);
        pstmt.setString(3, storageTypeCd);
        pstmt.setString(4, className);
        pstmt.setTimestamp(5, DBAccess.toSQLTimestamp(currDate));
        
        if (writeAttachments) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(6, blob);
            } else if (EDB.equals(databaseName)) {   
                //ByteArrayInputStream isA = new ByteArrayInputStream(bytes);
                //pstmt.setBinaryStream(6, isA, isA.available());
                pstmt.setBytes(6, bytes);
            } else {
                throw new Exception("Unknown database");
            }
        }
        
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    private int updateEmailAttachments(Connection conn, int eventEmailId, EventEmailDataView emailView) throws Exception {

        Statement stmt = conn.createStatement();
        /*
        String sql = "select ATTACHMENTS, LONG_TEXT from CLW_EVENT_EMAIL " +
                " where EVENT_EMAIL_ID = " + eventEmailId +
                " for update ";

        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        BLOB blobA = null;
        BLOB blobT = null;
        if (ORACLE.equals(databaseName)) {
            blobA = (BLOB) rs.getBlob(1);
            if(emailView.getFileAttachments()!=null){
                if (blobA == null) {
                    blobA = createTemporaryBlob(conn);
                }
                setByteToOracleBlob(blobA, objectToBytes(emailView.getFileAttachments()));
            }
            blobT = (BLOB) rs.getBlob(2);
            //STJ-3674
            if(emailView.getText()!=null && emailView.getText().getBytes("UTF-8").length > 4000){
              
                byte[] messBytes = emailView.getText().getBytes();
               //STJ-3784
                setByteToOracleBlob(blobT, messBytes);
                emailView.setText("");
            }
        }
        rs.close();
        stmt.close();
        */

        // STJ-6037
        String sql = "select EVENT_ID, " +
                            "BINARY_DATA_SERVER, " +
                            "ATTACHMENTS_SYSTEM_REF, " +
                            "LONG_TEXT_SYSTEM_REF, " +
                            "STORAGE_TYPE_CD, " +
                            "ATTACHMENTS, " +
                            "LONG_TEXT " +
                     "from CLW_EVENT_EMAIL where EVENT_EMAIL_ID = " + eventEmailId +
                     " for update";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        
        int eventId = rs.getInt(1);
        String binaryDataServer = rs.getString(2);
        String attachmentsSystemRef = rs.getString(3);
        String longTextSystemRef = rs.getString(4);
        String storageTypeCd = rs.getString(5);
        
        BLOB blobA = null;
        BLOB blobL = null;

        if (emailView.getFileAttachments() != null) {
            if (ORACLE.equals(databaseName)) {
                blobA = (BLOB) rs.getBlob(6);
				/*
                if(emailView.getFileAttachments()!=null){
                    if (blobA == null) {
                        blobA = createTemporaryBlob(conn);
                    }
                    setByteToOracleBlob(blobA, objectToBytes(emailView.getFileAttachments()));
                }
				*/
            }
        }
        if (emailView.getText() != null && emailView.getText().getBytes("UTF-8").length > 4000) {
            if (ORACLE.equals(databaseName)) {
                blobL = (BLOB) rs.getBlob(7);
				/*
				if (blobL == null) {
					blobL = createTemporaryBlob(conn);
				}
                setByteToOracleBlob(blobL, emailView.getText().getBytes());
                emailView.setText("");
				*/
            }
        }
        
        boolean writeAttachments = false;
        boolean writeLongText = false;
        if (emailView.getFileAttachments() != null ||
           (emailView.getText() != null && emailView.getText().getBytes("UTF-8").length > 4000)) {
           
           if (emailView.getFileAttachments() != null) {
               if (!Utility.isSet(attachmentsSystemRef)) {
                    attachmentsSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                             BlobStorageAccess.FIELD_ATTACHMENTS,
                                                                             eventId);
               }
               boolean isOK = bsa.storeBlob(objectToBytes(emailView.getFileAttachments()), attachmentsSystemRef);
			   log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

               if (isOK && bsa.getStoredToStorageType() != null &&
                   bsa.getStoredToHostName() != null) { // blob has been stored
                    storageTypeCd = bsa.getStoredToStorageType();
                    binaryDataServer = bsa.getStoredToHostName();
               } else {
                   log.error("ERROR. Blob will be written into DB. EventID: " + eventId + 
                              " (" + BlobStorageAccess.FIELD_ATTACHMENTS + ")");
                    storageTypeCd = BlobStorageAccess.STORAGE_DB;
                    binaryDataServer = BlobStorageAccess.getCurrentHost();
                    writeAttachments = true;
               }
           }
           if (emailView.getText() != null && emailView.getText().getBytes("UTF-8").length > 4000) {
                if (!Utility.isSet(longTextSystemRef)) {
                    longTextSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                          BlobStorageAccess.FIELD_LONG_TEXT,
                                                                          eventId);
               }
                boolean isOK = bsa.storeBlob(emailView.getText().getBytes(), longTextSystemRef);
	           log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());

               if (isOK && bsa.getStoredToStorageType() != null &&
                   bsa.getStoredToHostName() != null) { // blob has been stored
                    storageTypeCd = bsa.getStoredToStorageType();
                    binaryDataServer = bsa.getStoredToHostName();
                    emailView.setText("");
               } else {
                   log.error("ERROR. Blob will be written into DB. EventID: " + eventId + 
                              " (" + BlobStorageAccess.FIELD_LONG_TEXT + ")");
                    storageTypeCd = BlobStorageAccess.STORAGE_DB;
                    binaryDataServer = BlobStorageAccess.getCurrentHost();
                    writeLongText = true;
               }
           }
        }

        //Init Blobs if necessary
		if (ORACLE.equals(databaseName)) {
			if(writeAttachments) {
				if (blobA == null) {
					blobA = createTemporaryBlob(conn);
				}
                setByteToOracleBlob(blobA, objectToBytes(emailView.getFileAttachments()));
            }
			if(writeLongText) {
				if (blobL == null) {
					blobL = createTemporaryBlob(conn);
				}
                setByteToOracleBlob(blobL, emailView.getText().getBytes());
			}
        }
		if(writeLongText) {
			emailView.setText("");
		}
        
        sql = "UPDATE CLW_EVENT_EMAIL SET EVENT_ID=?," +
                " TO_ADDRESS=?," +
                " CC_ADDRESS=?," +
                " SUBJECT=?," +
                " TEXT=?," +
                " IMPORTANCE=?," +
                " EMAIL_STATUS_CD=?," +
                " MOD_BY=?," +
                " MOD_DATE=?," +
                " FROM_ADDRESS=?," +
                " BINARY_DATA_SERVER=?," +
                " ATTACHMENTS_SYSTEM_REF=?," +
                " LONG_TEXT_SYSTEM_REF=?," +
                " STORAGE_TYPE_CD=?" +
                (writeAttachments ? ", ATTACHMENTS=?" : "") +
                (writeLongText ? ", LONG_TEXT=?" : "") +
                " where EVENT_EMAIL_ID = " + eventEmailId;

        PreparedStatement pstmt = conn.prepareStatement(sql);

        java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

        pstmt.setInt(1, emailView.getEventId());
        pstmt.setString(2, emailView.getToAddress());
        pstmt.setString(3, emailView.getCcAddress());
        pstmt.setString(4, emailView.getSubject());
        pstmt.setString(5, emailView.getText());
        pstmt.setString(6, emailView.getImportance());
        pstmt.setString(7, emailView.getEmailStatusCd());
        
        /*
        if (ORACLE.equals(databaseName)) {
            pstmt.setBlob(8, blobA);
        } else if (EDB.equals(databaseName)) {
            ByteArrayInputStream is = new ByteArrayInputStream(objectToBytes(emailView.getFileAttachments()));
            pstmt.setBinaryStream(8, is, is.available());
        } else {
          throw new Exception("Unknown database");
        }
        */
        
        pstmt.setString(8, className);
        pstmt.setTimestamp(9, DBAccess.toSQLTimestamp(currDate));
        pstmt.setString(10, emailView.getFromAddress());
        pstmt.setString(11, binaryDataServer);
        pstmt.setString(12, attachmentsSystemRef);
        pstmt.setString(13, longTextSystemRef);
        pstmt.setString(14, storageTypeCd);
        
        if (writeAttachments) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(15, blobA);
            } else if (EDB.equals(databaseName)) {
                //ByteArrayInputStream isA = new ByteArrayInputStream(objectToBytes(emailView.getFileAttachments()));
                //pstmt.setBinaryStream(15, isA, isA.available());
                pstmt.setBytes(15, objectToBytes(emailView.getFileAttachments()));
            } else {
                throw new Exception("Unknown database");
            }
        }
        
        if (writeLongText) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(16, blobL);
            } else if (EDB.equals(databaseName)) {
                //ByteArrayInputStream isA = new ByteArrayInputStream(emailView.getText().getBytes());
                //pstmt.setBinaryStream(16, isA, isA.available());
                pstmt.setBytes(16, emailView.getText().getBytes());
            } else {
                throw new Exception("Unknown database");
            }
        }

        /*
        pstmt.setBlob(12, blobT);
        if (ORACLE.equals(databaseName)) {
            pstmt.setBlob(12, blobT);
        } else if (EDB.equals(databaseName)) {
            emailView.setText("");
            byte[] messBytes = emailView.getText().getBytes();
            ByteArrayInputStream is = new ByteArrayInputStream(messBytes);
            pstmt.setBinaryStream(12, is, is.available());
        } else {
          throw new Exception("Unknown database");
        }
        */

        int n = pstmt.executeUpdate();
        pstmt.close();

        return n;
    }

    private boolean updatePropertyBlobValue(Connection conn, int eventPropertyId, byte[] data) throws Exception {

        Statement stmt = conn.createStatement();
        // STJ-6037
        /*
        String sql = "select BLOB_VALUE from CLW_EVENT_PROPERTY " +
                " where EVENT_PROPERTY_ID = " + eventPropertyId +
                " for update ";
        */

        // STJ-6037
        String sql = "select EVENT_ID, " +
                            "BINARY_DATA_SERVER, " +
                            "BLOB_VALUE_SYSTEM_REF, " +
                            "STORAGE_TYPE_CD, " +
                            "BLOB_VALUE " +
                     "from CLW_EVENT_PROPERTY where EVENT_PROPERTY_ID = " + eventPropertyId +
                     " for update";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        
        int eventId = rs.getInt(1);
        String binaryDataServer = rs.getString(2);
        String blobValueSystemRef = rs.getString(3);
        String storageTypeCd = rs.getString(4);
        
        BLOB blob = null;
        if (data != null && data.length > 0) {
            if (ORACLE.equals(databaseName)) {
                blob = (BLOB) rs.getBlob(5);
                //setByteToOracleBlob(blob, data);
            }
        }

        rs.close();
        stmt.close();
        
        boolean writeBlobValue = false;
        if (!Utility.isSet(blobValueSystemRef)) {
            blobValueSystemRef = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                   BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                   eventId);
        }
        boolean isOK = bsa.storeBlob(data, blobValueSystemRef);
        log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
       
        if (isOK && bsa.getStoredToStorageType() != null &&
        	bsa.getStoredToHostName() != null	) { // blob has been stored
             storageTypeCd = bsa.getStoredToStorageType();
             binaryDataServer = bsa.getStoredToHostName();
        } else {
            log.error("ERROR. Blob will be written into DB. EventID: " + eventId + 
                              " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
            storageTypeCd = BlobStorageAccess.STORAGE_DB;
            binaryDataServer = BlobStorageAccess.getCurrentHost();
			blobValueSystemRef = null;
            writeBlobValue = true;
        }
        
        //String updateBlobSql = "update CLW_EVENT_PROPERTY set BLOB_VALUE=? where EVENT_PROPERTY_ID = " + eventPropertyId;
        if(writeBlobValue && ORACLE.equals(databaseName)) {
			if(blob == null) {
				blob = createTemporaryBlob(conn);
			}
			setByteToOracleBlob(blob, data);
		}
        String updateBlobSql = "update CLW_EVENT_PROPERTY " +
                               "set BINARY_DATA_SERVER=?, " +
                               "BLOB_VALUE_SYSTEM_REF=?, " + 
                               "STORAGE_TYPE_CD=?, " +
                               "MOD_BY=?, " +
                               "MOD_DATE=? " +
                               (writeBlobValue ? ", BLOB_VALUE=?" : "") +
                               " where EVENT_PROPERTY_ID = " + eventPropertyId;

        PreparedStatement pstmt = conn.prepareStatement(updateBlobSql);
        
        /*
        if (ORACLE.equals(databaseName)) {
            pstmt.setBlob(1, blob);
        } else if (EDB.equals(databaseName)) {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            pstmt.setBinaryStream(1, is, is.available());
        } else {
          throw new Exception("Unknown database");
        }
        */

        java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

        pstmt.setString(1, binaryDataServer);
        pstmt.setString(2, blobValueSystemRef);
        pstmt.setString(3, storageTypeCd);
        pstmt.setString(4, className);
        pstmt.setTimestamp(5, DBAccess.toSQLTimestamp(currDate));

        if (writeBlobValue) {
            if (ORACLE.equals(databaseName)) {
                pstmt.setBlob(6, blob);
            } else if (EDB.equals(databaseName)) {
               //ByteArrayInputStream isA = new ByteArrayInputStream(data);
               //pstmt.setBinaryStream(6, isA, isA.available());
               pstmt.setBytes(6, data);
            } else {
                throw new Exception("Unknown database");
            }
        }

        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    public int updateEventData(EventData eventData)   throws RemoteException {
        return updateEventData(eventData, null);
    }

    public int updateEventData(EventData eventData, String comment)   throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            return updateEventData(conn,eventData, comment);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    private int updateEventData(Connection conn, EventData eventData,
                String comment) throws SQLException, RemoteException {

        String sql = "UPDATE CLW_EVENT SET ATTEMPT = ?, CD = ?, STATUS = ?, TYPE = ?, MOD_BY = ?, MOD_DATE = ?, PROCESS_TIME = ? WHERE EVENT_ID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        java.util.Date currDate = new java.util.Date(System.currentTimeMillis());

        pstmt.setInt      (1, eventData.getAttempt());
        pstmt.setString   (2, eventData.getCd());
        pstmt.setString   (3, eventData.getStatus());
        pstmt.setString   (4, eventData.getType());
        pstmt.setString   (5, className);
        pstmt.setTimestamp(6, DBAccess.toSQLTimestamp(currDate));
        pstmt.setTimestamp(7, DBAccess.toSQLTimestamp(eventData.getProcessTime()));
        pstmt.setInt      (8, eventData.getEventId());        

        int n = pstmt.executeUpdate();
        pstmt.close();
        EventPropertyData eventPropertyComment = null;
        if((eventData.getEventPropertyDataVector()!=null)&&(eventData.getEventPropertyDataVector().size()>0)){

            Iterator it = eventData.getEventPropertyDataVector().iterator();
            
            while(it.hasNext()){

                EventPropertyData eventPropertyData = (EventPropertyData) it.next();
                if (Event.PROPERTY_COMMENT.equals(eventPropertyData.getType())) {
                    eventPropertyComment = eventPropertyData;
                } else {
                    if (eventPropertyData.getBlobValue() != null) {
                        String blobFileName = eventPropertyData.getBlobValueSystemRef();
                        if (!Utility.isSet(blobFileName)) {
                            blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                             BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                             eventData.getEventId());
                        }
                        boolean isOK = bsa.storeBlob(eventPropertyData.getBlobValue(), blobFileName);
         	           log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
                      if (isOK && bsa.getStoredToStorageType() != null  &&
                            bsa.getStoredToHostName() != null) { // blob has been stored
                            eventPropertyData.setBlobValueSystemRef(blobFileName);
                            eventPropertyData.setStorageTypeCd(bsa.getStoredToStorageType());
                            eventPropertyData.setBinaryDataServer(bsa.getStoredToHostName());
                            eventPropertyData.setBlobValue(null);
                        } else {
                            log.error("ERROR. Blob will be written into DB. EventID: " + eventPropertyData.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                            eventPropertyData.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                            eventPropertyData.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                        }
                    }
                
                }
                
                if(eventPropertyData.getEventPropertyId()>0){
                    EventPropertyDataAccess.update(conn, eventPropertyData);

                }else{
                    Statement stmt = conn.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_SEQ.NEXTVAL FROM DUAL");
                    rs.next();
                    int keyId = rs.getInt(1);
                    rs.close();
                    stmt.close();

                    eventPropertyData.setEventPropertyId(keyId);
                    eventPropertyData.setEventId(eventData.getEventId());

                    EventPropertyDataAccess.insert(conn, eventPropertyData);
                }

            }
        }
        if (comment != null) {
            if (eventPropertyComment == null) {
                eventPropertyComment = EventPropertyData.createValue();
                eventPropertyComment.setType(Event.PROPERTY_COMMENT);
                eventPropertyComment.setEventId(eventData.getEventId());
                eventPropertyComment.setShortDesc(Event.PROPERTY_COMMENT);
                eventPropertyComment.setVarClass("java.lang.String");
                eventPropertyComment.setNum(1);
                eventPropertyComment.setAddBy(eventData.getModBy());
            }
            eventPropertyComment.setModBy(eventData.getModBy());
            try {
                byte[] blob = ObjectUtil.objectToBytes(comment);
                String blobFileName = eventPropertyComment.getBlobValueSystemRef();
                if (!Utility.isSet(blobFileName)) {
                    blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                        BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                        eventData.getEventId());
                }
                boolean isOK = bsa.storeBlob(blob, blobFileName);
 	           log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
                if (isOK && bsa.getStoredToStorageType() != null &&
                   	bsa.getStoredToHostName() != null) { // blob has been stored
                    eventPropertyComment.setBlobValueSystemRef(blobFileName);
                    eventPropertyComment.setStorageTypeCd(bsa.getStoredToStorageType());
                    eventPropertyComment.setBinaryDataServer(bsa.getStoredToHostName());
                    eventPropertyComment.setBlobValue(null);
                } else {
                    log.error("ERROR. Blob will be written into DB. EventID: " + eventPropertyComment.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                    eventPropertyComment.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                    eventPropertyComment.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                    eventPropertyComment.setBlobValue(blob);
                }
            } catch (IOException e) {
                throw new RemoteException("Can't convert object to byte array!", e);
            }
            if (eventPropertyComment.getEventPropertyId() > 0) {
                EventPropertyDataAccess.update(conn, eventPropertyComment);
            } else {
                EventPropertyDataAccess.insert(conn, eventPropertyComment);
            }
        }
        EventEmailDataViewVector emailViewVector = eventData.getEventEmailDataViewVector();

        if(emailViewVector!=null){
            Iterator it2 = emailViewVector.iterator();
            while(it2.hasNext()){
                EventEmailDataView emailView = (EventEmailDataView) it2.next();
                try {
                    emailView.setEmailStatusCd(eventData.getStatus());
                    updateEventEmail(conn, emailView);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RemoteException("Error while update Email data", e);
                }
            }
        }


        return n;
    }

    protected int setByteToOracleBlob(BLOB blob, byte[] data) throws Exception {

        OutputStream out = blob.setBinaryStream(0);
        out.write(data);
        out.flush();
        out.close();

        return data.length;

    }

    protected byte[] objectToBytes(Object pObj) throws java.io.IOException {
        java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
        os.writeObject(pObj);
        os.flush();
        os.close();
        return oStream.toByteArray();

    }

    protected Object bytesToObject(byte[] pBytes) {
        Object obj = null;
        java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(pBytes);
        try {
            java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
            obj = is.readObject();
            is.close();
            iStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return obj;
    }

    public void runTest(int loopCount, int millis) throws RemoteException {
        try {
            int i=1;
            while(loopCount>0)         {
                EventHandler.start();
                Thread.sleep(millis);
                loopCount--;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runJob() throws RemoteException {
        try {
            EventHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processEvent(int eventId) throws RemoteException {
        try {
            EventHandler.processEvent(eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEventStatus(int eventId) throws RemoteException {
        Connection conn = null;
        try {
           conn = getConnection();
           com.cleanwise.service.api.value.EventData eventData = EventDataAccess.select(conn, eventId);
           return (eventData != null) ? eventData.getStatus() : "";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }
    public EventData setEventStatus(EventData eventData, String status) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            if (eventData != null && eventData.getEventId() > 0) {
                eventData.setStatus(status);
                int n = updateEventData(conn, eventData, null);
            }
            return eventData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public EventEmailDataViewVector getEmailsByEvent(int eventId) throws RemoteException {
        return getEmailsByEvent(eventId, null);
    }

    public EventEmailDataViewVector getEmailsByEvent(int eventId, String statusCd) throws RemoteException {

        Connection conn = null;
        DBCriteria dbCriteria;
        EventEmailDataViewVector emailViewVector=new EventEmailDataViewVector();

        try {
            conn = getConnection();
            if (eventId > 0) {
                dbCriteria = new DBCriteria();
                dbCriteria.addEqualTo(EventEmailDataAccess.EVENT_ID, eventId);

                if((statusCd!=null)&&(!"".equals(statusCd))){
                    dbCriteria.addEqualTo(EventEmailDataAccess.EMAIL_STATUS_CD, statusCd);
                }

                EventEmailDataVector emailCollection = EventEmailDataAccess.select(conn, dbCriteria);
                Iterator it = emailCollection.iterator();
                while(it.hasNext())
                {
                    EventEmailData email = (EventEmailData) it.next();
                    EventEmailDataView emailView= new EventEmailDataView();

                    FileAttach[] files = new FileAttach[0];
                    //STJ-6037
                    //if(email.getAttachments()!=null){
                    //    files = (new ApplicationsEmailTool()).getFilesFromBytes(email.getAttachments());
                    //}
                    byte[] blob = null;
                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(email.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(email.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(email.getStorageTypeCd())) {
                        if (Utility.isSet(email.getAttachmentsSystemRef())) {
                        	blob = bsa.readBlob(email.getStorageTypeCd(),
                                            email.getBinaryDataServer(),
                                            email.getAttachmentsSystemRef());
                        	if (blob == null){
                        		throw new Exception("ERROR reading Attachment from " + email.getStorageTypeCd());
                        	}
                        }
                    } else {
                        blob = email.getAttachments();
						/*
                        if (blob != null) {
                            log.error("ERROR. Blob has been read from DB. EventID: " + eventId +
                                      " (" + BlobStorageAccess.FIELD_ATTACHMENTS + ")");
                        }*/
                    }

                    if (blob != null) {
                        files = (new ApplicationsEmailTool()).getFilesFromBytes(blob);
                    }

                    emailView.setEventEmailId(email.getEventEmailId());
                    emailView.setCcAddress(email.getCcAddress());
                    emailView.setToAddress(email.getToAddress());
                    emailView.setFromAddress(email.getFromAddress());

                    if (email.getText()!=null && email.getText().length() > 0){
                      emailView.setText(email.getText());
                    }

                    //STJ-6037
                    // else if (email.getLongText() != null) {
                      
                    
                    //  long len = email.getLongText().length;
                    //  byte[] byteMess = email.getLongText();
                    //STJ-3673
                    //  String longText = new String(byteMess,"UTF-8");
                    //  emailView.setText(longText);
                      //bug # 4494 : Modified to make sure emails should be sent with body text. 
                    //  emailView.setLongText(byteMess);
                    // }

                    blob = null;
                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(email.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(email.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(email.getStorageTypeCd())) {
                        if (Utility.isSet(email.getLongTextSystemRef())) {
                            blob = bsa.readBlob(email.getStorageTypeCd(),
                                                email.getBinaryDataServer(),
                                                email.getLongTextSystemRef());
                        	if (blob == null){
                        		throw new Exception("ERROR reading Long Text from " + email.getStorageTypeCd());
                        	}
                        }
                    } else {
                        blob = email.getLongText();
						/*
                        if (blob != null) {
                            log.error("ERROR. Blob has been read from DB. EventID: " + eventId +
                                      " (" + BlobStorageAccess.FIELD_LONG_TEXT + ")");
                        }*/
                    }
                    if (blob != null) {
                        String longText = new String(blob,"UTF-8");
						if(Utility.isSet(longText)) {
							emailView.setText(longText);
							emailView.setLongText(blob);
						}
                    }

                    emailView.setEventId(email.getEventId());
                    emailView.setImportance(email.getImportance());
                    emailView.setSubject(email.getSubject());
                    emailView.setModBy(email.getModBy());
                    emailView.setModDate(email.getModDate());
                    emailView.setAddBy(email.getAddBy());
                    emailView.setAddDate(email.getAddDate());
                    emailView.setAttachments(files);
                    emailViewVector.add(emailView);
                }
            }
            return emailViewVector;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public EventEmailDataView updateEventEmailData(EventEmailDataView email) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            if (email != null && email.getEventEmailId() > 0) {
                return updateEventEmail(conn, email);
            }
            return email;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


    public int deleteEvent(EventData eventData)   throws RemoteException
    {
        Connection conn = null;

        try {
            conn = getConnection();

            EventDataAccess.remove(conn, eventData.getEventId());

            if(Event.TYPE_EMAIL.equals(eventData.getType())){
                EventEmailDataAccess.remove(conn, eventData.getEventId());
            }

            return EventDataAccess.remove(conn, eventData.getEventId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

    public int deleteEventProperty(int eventId, String eventType) throws RemoteException  {
        Connection conn = null;

        try {
            conn = getConnection();
            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addEqualTo(EventPropertyDataAccess.EVENT_ID, eventId);
            dbCriteria.addEqualTo(EventPropertyDataAccess.TYPE, eventType);

            return EventPropertyDataAccess.remove(conn, dbCriteria);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }

    }

    public void sendOrderNotification() throws RemoteException {
        Connection conn = null;
        File[] attachments;
        File tmp = null;
        try {
            conn = getConnection();
            tmp = File.createTempFile("Attachment", ".txt");
            //tmp = new File("/Temp/Attachment.txt");

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderDataAccess.ACCOUNT_ID,176650);
            dbc.addEqualTo(OrderDataAccess.ADD_BY,"inv_order");
            dbc.addEqualTo(OrderDataAccess.ORDER_STATUS_CD,RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
            OrderDataVector oDV = OrderDataAccess.select(conn,dbc);
            com.cleanwise.service.api.APIAccess factory = new com.cleanwise.service.api.APIAccess();
            //com.cleanwise.service.api.session.Event eventEjb = factory.getEventAPI();
            com.cleanwise.service.api.session.User userEjb = factory.getUserAPI();
            com.cleanwise.service.api.session.Order orderEjb = factory.getOrderAPI();
            int ii=0;
            for(Iterator iter=oDV.iterator(); iter.hasNext();) {
                OrderData orderData = (OrderData) iter.next();
                UserInfoDataVector contactUsers=
                        userEjb.getUserInfoDataCollection(orderData.getSiteId(),
                                                    orderData.getAccountId(),
                                                   UserInfoData.USER_GETS_EMAIL_ORDER_DETAIL_APPROVED);
                if (!contactUsers.isEmpty()) {
                    ii++;
                    //if(ii>20) break;

                    String subject = "Order notification. Order #"+orderData.getOrderNum()+" ("+orderData.getOrderSiteName()+")";
                    String emailContacts= com.cleanwise.service.apps.ApplicationsEmailTool.getEmailContacts(contactUsers);
                    if(com.cleanwise.service.api.util.Utility.isSet(emailContacts)){

                        com.cleanwise.service.api.process.operations.OrderNotificationGenerator fileGenerator =
                                new com.cleanwise.service.api.process.operations.OrderNotificationGenerator();
                        int orderId = orderData.getOrderId();
                        String message =  fileGenerator.genTXT(orderEjb.getOrderInfoData(orderId));
                        if(message.length()>3988) {
                            String footer = message.substring(message.length()-1000);
                            int pp = footer.indexOf("\n\r");
                            if(pp>0) {
                                footer = footer.substring(pp);
                            }
                            String header = message.substring(0,2500);
                            pp = header.lastIndexOf("\n\r");
                            if(pp>0) {
                                header = header.substring(0,pp);
                            }
                            message = header+"\n\r\n\r ....... (Truncated) ......\n\r\n\r"+footer;
                        }
                        //message = getMessage(orderData.getOrderId());
                        OrderInfoDataView orderInfoData = orderEjb.getOrderInfoData(orderData.getOrderId());

                        fileGenerator.generate(orderInfoData, tmp);
                        attachments = new File[]{tmp};
                        FileAttach[] fileAttach;

                        if(attachments!=null){
                            fileAttach = (new ApplicationsEmailTool()).fromFilesToAttachs(attachments);
                        } else{
                            fileAttach = new FileAttach[0];
                        }

//                        EventData eventData = new EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//                        eventData = addEventToDB(eventData);

                        EventEmailDataView eventEmailData = new EventEmailDataView();
                        eventEmailData.setEventEmailId(0);
                        eventEmailData.setToAddress(emailContacts);
                        eventEmailData.setSubject(subject);
                        eventEmailData.setText(message);
                        eventEmailData.setAttachments(fileAttach);

//                        eventEmailData.setEventId(eventData.getEventId());
                        eventEmailData.setEmailStatusCd(Event.STATUS_READY);
                        eventEmailData.setModBy("OrderNotification");
                        eventEmailData.setAddBy("OrderNotification");
//                        addEventEmail(eventEmailData);
                        com.cleanwise.service.api.value.EventData eventData = Utility.createEventDataForEmail();
                        EventEmailView eev = new EventEmailView(eventData, eventEmailData);
                        addEventEmail(eev, "OrderNotification");
                    } else {
                        OrderPropertyData note = OrderPropertyData.createValue();
                        note.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                        note.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                        note.setShortDesc("Pipeline Warning");
                        note.setValue("User has no contact email");
                        note.setOrderId(orderData.getOrderId());
                        note.setModBy("OrderNotification");
                        note.setAddBy("OrderNotification");
                        orderEjb.addNote(note);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public EventPropertyDataVector getAllEventProperties()
            throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            // STJ-6037
            //return EventPropertyDataAccess.selectAll(conn);
            EventPropertyDataVector eventPropertyDV = EventPropertyDataAccess.selectAll(conn);
            if (Utility.isSet(eventPropertyDV)) {
                EventPropertyData eventProperty;
                byte[] blob;
                for (int i = 0; i < eventPropertyDV.size(); i++) {
                    eventProperty = (EventPropertyData) eventPropertyDV.get(i);
                    
                    if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(eventProperty.getStorageTypeCd()) ||
						BlobStorageAccess.STORAGE_NFS.equals(eventProperty.getStorageTypeCd()) ||
                        BlobStorageAccess.STORAGE_FTP.equals(eventProperty.getStorageTypeCd())) {
                        if (Utility.isSet(eventProperty.getBlobValueSystemRef())) {
                            blob = bsa.readBlob(eventProperty.getStorageTypeCd(),
                                                eventProperty.getBinaryDataServer(),
                                                eventProperty.getBlobValueSystemRef());
                            eventProperty.setBlobValue(blob);
                        }
                    } /* else {
                        if (eventProperty.getBlobValue() != null) {
                            throw new Exception("ERROR. Blob has been read from DB. EventID: " + eventProperty.getEventId() +
                                      " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                        }
                    } */
                }
            }
            return eventPropertyDV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public EventDataVector getEventCollectionByIdList(IdVector pEventIdList)  throws RemoteException {
        EventDataVector eventV = new EventDataVector();
        Connection conn = null;
        com.cleanwise.service.api.value.EventDataVector eventDV = null;
        try {
          conn = getConnection();
          DBCriteria crit = new DBCriteria();
          crit.addOneOf(EventDataAccess.EVENT_ID, pEventIdList);
          crit.addOrderBy(EventDataAccess.EVENT_ID);
          eventDV = EventDataAccess.select(conn, crit);
          if (eventDV != null) {
            for (int i = 0; i < eventDV.size(); i++) {
              com.cleanwise.service.api.value.EventData eventD = (com.cleanwise.service.api.value.EventData)eventDV.get(i);
              EventData event = new EventData();
              event.setEventId(eventD.getEventId());
              event.setStatus(eventD.getStatus());
              event.setType(eventD.getType());
              event.setCd(eventD.getCd());
              event.setAddDate(eventD.getAddDate());
              event.setModDate(eventD.getModDate());
              event.setAttempt(eventD.getAttempt());
              eventV.add(event);
            }
          }
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException(e.getMessage());
      }
      finally {
          closeConnection(conn);
      }
        return eventV;
    }

    public EventDataVector getEventV(
            int eventId, String searchActive, String type, List<String> statusList,
            java.util.Date dateFrom, java.util.Date dateTo,
            java.util.Date timeFrom, java.util.Date timeTo,
            String searchParamType, String attrType,
            String attrFilterType, String attrFilterValue1,
            String attrFilterValue2, String searchContainsText, IdVector pEventIdList,
            String searchBlobText, int searchBlobTextMaxRows) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            EventDataVector result = new EventDataVector();
            if (eventId > 0) {
                EventData event = getEvent(eventId);
                if (event != null) {
                    if (searchParamType != null && searchParamType.length() > 0) {
                        String addProperty = "";
                        result.setUseAdditional(true);
                        if (!Event.TYPE_EMAIL.equals(type)) {
                            if (event.getEventPropertyDataVector() != null) {
                                DBCriteria crit = new DBCriteria();
                                crit.addEqualTo(TaskPropertyDataAccess.TASK_PROPERTY_ID,
                                                Utility.parseInt(searchParamType));
                                TaskPropertyData taskProp = (TaskPropertyData)TaskPropertyDataAccess.select(conn, crit).get(0);

                                if (taskProp != null) {
                                    String taskPropVarName = taskProp.getVarName();
                                    String taskPropVarType = taskProp.getVarType();
                                    if (taskPropVarName != null && taskPropVarType != null) {
                                        Iterator it = event.getEventPropertyDataVector().iterator();
                                        EventPropertyData eProp;
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                                        while (it.hasNext()) {
                                            eProp = (EventPropertyData)it.next();
                                            if (taskPropVarName.equals(eProp.getShortDesc()) &&
                                                taskPropVarType.equals(eProp.getVarClass())) {
                                                if ("java.lang.String".equals(eProp.getVarClass())) {
                                                    addProperty = eProp.getStringVal();
                                                } else if ("java.lang.Integer".equals(eProp.getVarClass())){
                                                    addProperty = String.valueOf(eProp.getNumberVal());
                                                } else if ("java.util.Date".equals(eProp.getVarClass())) {
                                                    addProperty = dateFormat.format(eProp.getDateVal());
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            DBCriteria crit = new DBCriteria();
                                crit.addEqualTo(EventEmailDataAccess.EVENT_ID, eventId);
                            EventEmailData eEmail = (EventEmailData)EventEmailDataAccess.select(conn, crit).get(0);

                            if (eEmail != null) {
                                if ("EMAIL_TEXT".equals(searchParamType)) {
                                    addProperty = eEmail.getText();
                                } else if ("EMAIL_TO".equals(searchParamType)) {
                                    addProperty = eEmail.getToAddress();
                                } else if ("EMAIL_FROM".equals(searchParamType)) {
                                    addProperty = eEmail.getFromAddress();
                                } else if ("EMAIL_SUBJECT".equals(searchParamType)) {
                                    addProperty = eEmail.getSubject();
                                }
                            }
                        }
                        event.setAdditinalProperty(addProperty);
                    }

                    if (Utility.isSet(searchBlobText)) {
                    	if (blobTextFound(conn, event.getEventId(), searchBlobText)) {
                            result.add(event);
                        }
                    } else {
                    	result.add(event);
                    }
                }
            } else {
                DBCriteria cr = new DBCriteria();
                if (searchActive != null && searchActive.length() > 0) {
                    if (searchActive.equals("1")) {
                        cr.addGreaterThan(EventDataAccess.CLW_EVENT + "."
                                + EventDataAccess.ATTEMPT, 0);
                    } else {
                        cr.addEqualTo(EventDataAccess.CLW_EVENT + "." + EventDataAccess.ATTEMPT, 0);
                    }
                }
                if (Utility.isSet(statusList)) {
                    cr.addOneOf(EventDataAccess.CLW_EVENT + "." + EventDataAccess.STATUS, statusList);
                }
                if (dateFrom != null) {
                    if (timeFrom != null) {
                        dateFrom = Utility.combineDateAndTime(dateFrom, timeFrom);
                        cr.addGreaterOrEqual(EventDataAccess.CLW_EVENT + "." + EventDataAccess.MOD_DATE, dateFrom, true);
                    } else {
                        cr.addGreaterOrEqual(EventDataAccess.CLW_EVENT + "." + EventDataAccess.MOD_DATE, dateFrom);
                    }
                }
                if (dateTo != null) {
                    if (timeTo != null) {
                        dateTo = Utility.combineDateAndTime(dateTo, timeTo);
                        cr.addLessOrEqual(EventDataAccess.CLW_EVENT + "." + EventDataAccess.MOD_DATE, dateTo, true);
                    } else {
                        dateTo = Utility.addDays(dateTo, 1);
                        cr.addLessOrEqual(EventDataAccess.CLW_EVENT + "." + EventDataAccess.MOD_DATE, dateTo);
                    }
                }
                if(Utility.isSet(searchContainsText)){
                        result.setUseAdditional(true);
                        cr.addCondition("CONTAINS("+EventPropertyDataAccess.CLW_EVENT_PROPERTY + "." + EventPropertyDataAccess.BLOB_VALUE+", '%"+searchContainsText+"%', 1) > 0");
                }
                String addColumn = null;
                String addJoin = null;
                if (type != null && type.length() > 0) {
                    if (type.equals(Event.TYPE_EMAIL) == true) {
                        addColumn = prepareWhereEmail(cr,
                                                      searchParamType,
                                                      attrType,
                                                      attrFilterType,
                                                      attrFilterValue1,
                                                      attrFilterValue2);
                    } else if (searchParamType != null && searchParamType.length() > 0) {
                        DBCriteria cr2 = new DBCriteria();
                        cr2.addEqualTo(TaskPropertyDataAccess.TASK_PROPERTY_ID,
                                Utility.parseInt(searchParamType));
                        TaskPropertyDataVector pdv = TaskPropertyDataAccess.select(conn, cr2);
                        if (pdv != null && pdv.size() > 0) {
                            final String table = EventPropertyDataAccess.CLW_EVENT_PROPERTY;
                            if ("string".equals(attrType)) {
                                addColumn = table + "." + EventPropertyDataAccess.STRING_VAL;
                            } else if ("integer".equals(attrType)) {
                                addColumn = table + "." + EventPropertyDataAccess.NUMBER_VAL;
                            } else if ("date".equals(attrType)) {
                                addColumn = table + "." + EventPropertyDataAccess.DATE_VAL;
                            }
                            addJoin = prepareWhere(cr,
                                                  (TaskPropertyData) pdv.get(0),
                                                   attrType,
                                                   attrFilterType,
                                                   attrFilterValue1,
                                                   attrFilterValue2);
                        }
                    }
                }
                StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.EVENT_ID + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.ADD_BY + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.ADD_DATE + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.ATTEMPT + ","
                        + EventDataAccess.CLW_EVENT + "." + EventDataAccess.CD
                        + "," + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.MOD_BY + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.MOD_DATE + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.STATUS + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.PROCESS_TIME + ","
                        + ProcessDataAccess.CLW_PROCESS + "."
                        + ProcessDataAccess.PROCESS_NAME + ","
                        + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.TYPE);
                if (addColumn != null) {
                    sqlBuf.append("," + addColumn);
                    result.setUseAdditional(true);
                }
                sqlBuf.append(" FROM " + EventDataAccess.CLW_EVENT);
                sqlBuf.append(" LEFT OUTER JOIN "
                        + EventPropertyDataAccess.CLW_EVENT_PROPERTY + " "
                        + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "_2");
                sqlBuf.append(" ON " + EventDataAccess.CLW_EVENT + "."
                        + EventDataAccess.EVENT_ID + " = "
                        + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "_2."
                        + EventPropertyDataAccess.EVENT_ID + " AND "
                        + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "_2."
                        + EventPropertyDataAccess.TYPE + " = '"
                        + Event.PROPERTY_PROCESS_TEMPLATE_ID + "' AND "
                        + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "_2."
                        + EventPropertyDataAccess.SHORT_DESC
                        + " = 'process_id'");
                sqlBuf.append(" LEFT OUTER JOIN "
                        + ProcessDataAccess.CLW_PROCESS);
                sqlBuf.append(" ON " + ProcessDataAccess.CLW_PROCESS + "."
                        + ProcessDataAccess.PROCESS_ID + " = "
                        + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "_2."
                        + EventPropertyDataAccess.NUMBER_VAL + " ");
                String where = null;
                if (result.isUseAdditional() || (type != null && type.length() > 0)) {
                    if (Event.TYPE_EMAIL.equals(type)) {
                        sqlBuf.append(","
                                + EventEmailDataAccess.CLW_EVENT_EMAIL);
                        cr.addJoinCondition(EventDataAccess.CLW_EVENT + "."
                                + EventDataAccess.EVENT_ID,
                                EventEmailDataAccess.CLW_EVENT_EMAIL,
                                EventEmailDataAccess.EVENT_ID);
                        cr.addEqualTo(EventDataAccess.CLW_EVENT + "."
                                + EventDataAccess.TYPE, Event.TYPE_EMAIL);
                    } else if (result.isUseAdditional() == true) {
                        if ((Utility.isSet(attrFilterValue1) && ("string".equals(attrType) || "integer".equals(attrType))) ||
                            (Utility.isSet(attrFilterValue1) || Utility.isSet(attrFilterValue2)) && "date".equals(attrType)) {
                                sqlBuf.append("," + EventPropertyDataAccess.CLW_EVENT_PROPERTY);

                                cr.addJoinCondition(EventDataAccess.CLW_EVENT + "."
                                        + EventDataAccess.EVENT_ID,
                                        EventPropertyDataAccess.CLW_EVENT_PROPERTY,
                                        EventPropertyDataAccess.EVENT_ID);
                        } else {
                            if (addJoin != null) {
                                sqlBuf.append(addJoin);
                            }
                        }
                        cr.addEqualTo(EventDataAccess.CLW_EVENT + "."
                                    + EventDataAccess.TYPE, Event.TYPE_PROCESS);
                    }
                    int processId = Utility.parseInt(type);
                    if (processId > 0) {
                        where = EventDataAccess.CLW_EVENT + "."
                                + EventDataAccess.EVENT_ID
                                + " IN (SELECT DISTINCT T1."
                                + EventPropertyDataAccess.EVENT_ID + " FROM "
                                + EventPropertyDataAccess.CLW_EVENT_PROPERTY
                                + " T1 WHERE T1."
                                + EventPropertyDataAccess.SHORT_DESC
                                + " = 'process_id' AND "
                                + EventPropertyDataAccess.TYPE + " = '"
                                + Event.PROPERTY_PROCESS_TEMPLATE_ID + "' AND "
                                + EventPropertyDataAccess.NUMBER_VAL + " = "
                                + processId + ")";
                    }
                }
                String sqlClause = cr.getSqlClause();
                boolean isUseSqlClause = false;
                if (sqlClause != null && sqlClause.length() > 0) {
                    sqlBuf.append(" WHERE " + sqlClause);
                    isUseSqlClause = true;
                }
                boolean isUseWhere = false;
                if (where != null && where.length() > 0) {
                    if (isUseSqlClause == true) {
                        sqlBuf.append( " AND (" + where + ")");
                    } else {
                        sqlBuf.append( " WHERE " + where);
                    }
                isUseWhere = true;
                }
                // ids from fulltext search ---------------
                if (pEventIdList != null && pEventIdList.size() > 0) {
                  String sqlIds = EventDataAccess.CLW_EVENT + "." + EventDataAccess.EVENT_ID +
                                  " IN (" + IdVector.toCommaString(pEventIdList) + ")";
                  if (sqlIds != null && sqlIds.length() > 0) {
                    if (isUseSqlClause || isUseWhere) {
                      sqlBuf.append(" AND (" + sqlIds + ")");
                    }
                    else {
                      sqlBuf.append(" WHERE " + sqlIds);
                    }
                  }
                }
                
                if (Utility.isSet(searchBlobText)) {
                	String sqlStr = sqlBuf.toString();
                	sqlStr = "SELECT COUNT(*)" + sqlStr.substring(sqlStr.indexOf(" FROM " + EventDataAccess.CLW_EVENT));
                	Statement stmt = conn.createStatement();
            		ResultSet rs = stmt.executeQuery(sqlStr);
                    if (rs.next()) {
                    	int count = rs.getInt(1);
                    	if (count > searchBlobTextMaxRows){
                    		throw new RemoteException(count + " events have been searched for blob text. Maximum " + searchBlobTextMaxRows + " events are allowed. Please redefine your search criteria for effective search");
                    	}
                    }
                }
                
                // STJ-6037
                /*
                	sqlBuf.append(" AND EXISTS (SELECT EVENT_PROPERTY_ID FROM CLW_EVENT_PROPERTY " +
                			"WHERE CLW_EVENT.EVENT_ID = CLW_EVENT_PROPERTY.EVENT_ID " +
                			"AND BLOB_VALUE IS NOT NULL " +
                			"AND dbms_lob.INSTR(BLOB_VALUE, utl_raw.cast_to_raw('" + searchBlobText + "'),1,1) > 0)");
                }
                */

                sqlBuf.append(" ORDER BY " + EventDataAccess.CLW_EVENT + "." + EventDataAccess.EVENT_ID);
                //------------------------------------------
                Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlBuf.toString());
                
                boolean searchForBlobText = Utility.isSet(searchBlobText);
                while (rs.next()) {
                    EventData item = new EventData();
                    item.setEventId(rs.getInt(1));
                    item.setAddBy(rs.getString(2));
                    item.setAddDate(rs.getTimestamp(3));
                    item.setAttempt(rs.getInt(4));
                    item.setCd(rs.getString(5));
                    item.setModBy(rs.getString(6));
                    item.setModDate(rs.getTimestamp(7));
                    item.setStatus(rs.getString(8));
                    item.setProcessTime(rs.getTimestamp(9));
                    item.setProcessName(rs.getString(10));
                    item.setType(rs.getString(11));
                    
                    if (addColumn != null) {
                        item.setAdditinalProperty(rs.getString(12));
                    }
                    if (searchForBlobText) {
                        if (blobTextFound(conn, item.getEventId(), searchBlobText)) {
                            result.add(item);
                        }
                    } else {
                        result.add(item);
                    }
                }
                rs.close();
                stmt.close();
            }

            return result;
        } catch (Exception e) {
        	if (e instanceof RemoteException)
        		throw (RemoteException)e;
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    private boolean blobTextFound(Connection conn, int eventId, String searchBlobText) throws Exception {

        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(EventPropertyDataAccess.EVENT_ID, eventId);
        // STJ-6037
        //cr.addIsNotNull(EventPropertyDataAccess.BLOB_VALUE);

        EventPropertyDataVector eventProps = EventPropertyDataAccess.select(conn, cr);
        
        if (Utility.isSet(eventProps)) {
            searchBlobText = searchBlobText.toUpperCase();

            EventPropertyData eventProperty;
            byte blob[] = null;
            for (int i = 0; i < eventProps.size(); i++) {
                eventProperty = (EventPropertyData) eventProps.get(i);
                
                if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(eventProperty.getStorageTypeCd()) ||
					BlobStorageAccess.STORAGE_NFS.equals(eventProperty.getStorageTypeCd()) ||
                    BlobStorageAccess.STORAGE_FTP.equals(eventProperty.getStorageTypeCd())) {
                    if (Utility.isSet(eventProperty.getBlobValueSystemRef())) {
                        blob = bsa.readBlob(eventProperty.getStorageTypeCd(),
                                            eventProperty.getBinaryDataServer(),
                                            eventProperty.getBlobValueSystemRef());
                        eventProperty.setBlobValue(blob);
                    }
                } else {
                    blob = eventProperty.getBlobValue();
					/*
                    if (blob != null) {
                        log.error("ERROR. Blob has been read from DB. EventID: " + eventProperty.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                    }*/
                }
                
                if (blob != null) {
                    String buffer = new String(blob).toUpperCase();
                    if (buffer.indexOf(searchBlobText) > -1) {
                        return true;
                    }
                }
                //----------------------------------------------------------
                //byte[] data = eventProperty.getBlobValue();
            }
        }
        return false;
    }

    /**
     * Return all events with by status
     * @param status Status of event
     * @return       EventDataVector
     */
    public EventDataVector getEventV(DBCriteria dbc)
            throws RemoteException
    {

        EventDataVector eventDataVector = new EventDataVector();
        Connection conn = null;

        try {
            conn = getConnection();
            String query =
                    "SELECT event_id, status, type, cd, add_date, mod_date,\n" +
                            "  mod_by, attempt" +
                            "  FROM clw_event\n" +
                            " WHERE " + dbc.getWhereClause();

            Statement sttm = conn.createStatement();

            ResultSet rs = sttm.executeQuery(query);
            while(rs.next()){
                EventData eventData = new EventData();
                eventData.setEventId(rs.getInt(1));
                eventData.setStatus(rs.getString(2));
                eventData.setType(rs.getString(3));
                eventData.setCd(rs.getString(4));
                eventData.setAddDate(rs.getDate(5));
                eventData.setModDate(rs.getDate(6));
                eventData.setAttempt(rs.getInt(8));
                eventDataVector.add(eventData);
            }
            rs.close();
            sttm.close();

            Iterator it=eventDataVector.iterator();
            while(it.hasNext())
            {
                EventData ed= (EventData) it.next();
                ed.setProperties(getEventProperties(conn,ed.getEventId()));

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
        
        for (int i = 0; i < eventDataVector.size(); i++) {
            log.info("getEventV() method: eventData = " + eventDataVector.get(0));
        }
        
        return eventDataVector;
    }

    private static String prepareWhere(DBCriteria cr,
                                       TaskPropertyData property,
                                       String attrType,
                                       String attrFilterType,
                                       String attrFilterValue1,
                                       String attrFilterValue2) {
        final String table = EventPropertyDataAccess.CLW_EVENT_PROPERTY;
        String column = null;
        String addJoin = null;
                                    
        if ((Utility.isSet(attrFilterValue1) && ("string".equals(attrType) || "integer".equals(attrType))) ||
            (Utility.isSet(attrFilterValue1) || Utility.isSet(attrFilterValue2)) && "date".equals(attrType)) {
            
            cr.addEqualTo(table + "." + EventPropertyDataAccess.SHORT_DESC, property.getVarName());
            cr.addEqualTo(table + "." + EventPropertyDataAccess.VAR_CLASS, property.getVarType());
            cr.addEqualTo(table + "." + EventPropertyDataAccess.TYPE, Event.PROCESS_VARIABLE);
            
            if ("string".equals(attrType)) {
                column = table + "." + EventPropertyDataAccess.STRING_VAL;
                if (("" + SearchCriteria.BEGINS_WITH_IGNORE_CASE).equals(attrFilterType)) {
                    cr.addLikeIgnoreCase(column, attrFilterValue1 + "%");
                } else {
                    cr.addLikeIgnoreCase(column, "%" + attrFilterValue1 + "%");
                }
            } else if ("integer".equals(attrType)) {
                column = table + "." + EventPropertyDataAccess.NUMBER_VAL;
                int value = Utility.parseInt(attrFilterValue1);
                if (">=".equals(attrFilterType)) {
                    cr.addGreaterOrEqual(column, value);
                } else if (">".equals(attrFilterType)) {
                    cr.addGreaterThan(column, value);
                } else if ("=".equals(attrFilterType)) {
                    cr.addEqualTo(column, value);
                } else if ("<".equals(attrFilterType)) {
                    cr.addLessThan(column, value);
                } else if ("<=".equals(attrFilterType)) {
                    cr.addLessOrEqual(column, value);
                }
            } else if ("date".equals(attrType) == true) {
                column = table + "." + EventPropertyDataAccess.DATE_VAL;
                Date from = Utility.parseDate(attrFilterValue1);
                Date to = Utility.parseDate(attrFilterValue2);
                if (from != null) {
                    cr.addGreaterOrEqual(column, from);
                }
                if (to != null) {
                    cr.addLessOrEqual(column, to);
                }
                // STJ-6018
                //if (to == null && from == null) {
                //    cr.addIsNotNull(column);
                //}
                //return column;
            }
        } else {
            StringBuilder sqlBuf = new StringBuilder();

            sqlBuf.append(" LEFT OUTER JOIN " + EventPropertyDataAccess.CLW_EVENT_PROPERTY);
            sqlBuf.append(" ON " + EventDataAccess.CLW_EVENT + "."
            + EventDataAccess.EVENT_ID + " = "
            + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "."
            + EventPropertyDataAccess.EVENT_ID + " AND "

            + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "."
            + EventPropertyDataAccess.SHORT_DESC + " = '"
            + property.getVarName() + "' AND "

            + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "."
            + EventPropertyDataAccess.VAR_CLASS + " = '"
            + property.getVarType() + "' AND "

            + EventPropertyDataAccess.CLW_EVENT_PROPERTY + "."
            + EventPropertyDataAccess.TYPE + " = '"
            + Event.PROCESS_VARIABLE + "'");

            addJoin = sqlBuf.toString();
        }
        return addJoin;
    }

    private static String prepareWhereEmail(DBCriteria cr,
            String searchParamType, String attrType, String attrFilterType,
            String attrFilterValue1, String attrFilterValue2) {
        String table = EventEmailDataAccess.CLW_EVENT_EMAIL;
        String column = null;
        if ("EMAIL_TEXT".equals(searchParamType)) {
            column = EventEmailDataAccess.TEXT;
        } else if ("EMAIL_TO".equals(searchParamType)) {
            column = EventEmailDataAccess.TO_ADDRESS;
        } else if ("EMAIL_FROM".equals(searchParamType)) {
            column = EventEmailDataAccess.FROM_ADDRESS;
        } else if ("EMAIL_SUBJECT".equals(searchParamType)) {
            column = EventEmailDataAccess.SUBJECT;
        }
        if (column != null) {
            if (("" + SearchCriteria.BEGINS_WITH_IGNORE_CASE)
                    .equals(attrFilterType)) {
                cr.addLikeIgnoreCase(table + "." + column, attrFilterValue1
                        + "%");
            } else {
                cr.addLikeIgnoreCase(table + "." + column, "%"
                        + attrFilterValue1 + "%");
            }
            return table + "." + column;
        } else {
            return null;
        }
    }

    public EventPropertyData getEventProperty(int eventPropertyId) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            EventPropertyData item = EventPropertyDataAccess.select(conn, eventPropertyId);
            
            // STJ-6037
            if (BlobStorageAccess.STORAGE_FILESYSTEM.equals(item.getStorageTypeCd()) ||
				BlobStorageAccess.STORAGE_NFS.equals(item.getStorageTypeCd()) ||
                BlobStorageAccess.STORAGE_FTP.equals(item.getStorageTypeCd())) {
                if (Utility.isSet(item.getBlobValueSystemRef())) {
                    byte[] blob = bsa.readBlob(item.getStorageTypeCd(),
                                               item.getBinaryDataServer(),
                                               item.getBlobValueSystemRef());
                    item.setBlobValue(blob);
                }
            } /*else {
                if (item.getBlobValue() != null) {
                    log.error("ERROR. Blob has been read from DB. EventID: " + item.getEventId() +
                              " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                }
            }*/
            
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void updateEventProperty(EventPropertyData eventPropertyData) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            // STJ-6037
            if (eventPropertyData.getBlobValue() != null) {
                String blobFileName = eventPropertyData.getBlobValueSystemRef();
                if (!Utility.isSet(blobFileName)) {
                    blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                     BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                     eventPropertyData.getEventId());
                }
                boolean isOK = bsa.storeBlob(eventPropertyData.getBlobValue(), blobFileName);
 	           log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
                if (isOK && bsa.getStoredToStorageType() != null &&
                    bsa.getStoredToHostName() != null) { // blob has been stored
                    eventPropertyData.setBlobValueSystemRef(blobFileName);
                    eventPropertyData.setStorageTypeCd(bsa.getStoredToStorageType());
                    eventPropertyData.setBinaryDataServer(bsa.getStoredToHostName());

                    eventPropertyData.setBlobValue(null);
                } else {
                    log.error("ERROR. Blob will be written into DB. EventID: " + eventPropertyData.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                    eventPropertyData.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                    eventPropertyData.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
               }
            }
            EventPropertyDataAccess.update(conn, eventPropertyData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public void updateEventPropertyBlob(EventPropertyData item, byte data[]) throws RemoteException {
        Connection conn = null;
        if (item == null) {
            return;
        }
        try {
            conn = getConnection();
            
            // STJ-6037
            String blobFileName = item.getBlobValueSystemRef();
            if (!Utility.isSet(blobFileName)) {
                blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_PROPERTY,
                                                                 BlobStorageAccess.FIELD_BLOB_VALUE,
                                                                 item.getEventId());
            }
            boolean isOK = bsa.storeBlob(data, blobFileName);
	        log.info("Saving Event Property with blob. bsa.getStoredToStorageType(): "+bsa.getStoredToStorageType() +", bsa.getStoredToHostName()=" + bsa.getStoredToHostName());
           if (isOK && bsa.getStoredToStorageType() != null &&
                bsa.getStoredToHostName() != null) { // blob has been stored
                item.setBlobValueSystemRef(blobFileName);
                item.setStorageTypeCd(bsa.getStoredToStorageType());
                item.setBinaryDataServer(bsa.getStoredToHostName());
                
                item.setBlobValue(null);    
            } else {
                log.error("ERROR. Blob will be written into DB. EventID: " + item.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_BLOB_VALUE + ")");
                item.setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                item.setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                item.setBlobValue(data);
            }
            EventPropertyDataAccess.update(conn, item);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public LoggingDataVector getLogs(int eventId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(LoggingDataAccess.PARENT_ID, eventId);
            cr.addOrderBy(LoggingDataAccess.LOGGING_ID);
            return LoggingDataAccess.select(conn, cr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    private void loggingMessage(EventData eD, String message) throws RemoteException {
      LoggingData logD= LoggingData.createValue();
      logD.setParentId(eD.getEventId());

      logD.setMessage(message);
      logD.setPrio("INFO");
      logD.setCategory(this.getClass().getName());
      logD.setIprio(eD.getPriority());
      logD.setAddBy("logger");
      logD.setAddDate(new Date());
//      logD.setProcessId(9999);
      Connection conn = null;
      try {
        conn = getConnection();
        LoggingDataAccess.insert(conn, logD);
      }
      catch (Exception e) {
        e.printStackTrace();
        throw new RemoteException(e.getMessage());
      }
      finally {
        closeConnection(conn);
      }
 }

    private boolean isNotWaitingOnEvent(EventData eD, int waitingOnEventId, Connection conn) throws Exception {
      //String propType = "PROCESS_VARIABLE";
      //String propSortDesc = "waitingOn";
      boolean b = true;
      String waitingOnEventStatus = "";
      if (waitingOnEventId > 0 ){
        waitingOnEventStatus = getEventStatus(waitingOnEventId);

        String waitFailMediatorClass = null;
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(EventPropertyDataAccess.EVENT_ID, eD.getEventId());
        crit.addEqualTo(EventPropertyDataAccess.SHORT_DESC, Event.WATING_ON_FAIL_MEDIATOR_CLASS);
        EventPropertyDataVector epvd = EventPropertyDataAccess.select(conn,crit);
        if(epvd.size() >= 1){
        	EventPropertyData epd = (EventPropertyData) epvd.get(0);
    		waitFailMediatorClass = epd.getStringVal();
        }
        
        log.info("waitFailMediatorClass = "+waitFailMediatorClass);
        if(Utility.isSet(waitFailMediatorClass)){
        	b = (Event.STATUS_PROCESSED.equals(waitingOnEventStatus) || Event.STATUS_REJECTED.equals(waitingOnEventStatus));
        	log.info("EVENT BEAN waitFailMediatorClass found");
        	try{
	        	Class c = Class.forName(waitFailMediatorClass);
	        	FailMediator fm = (FailMediator) c.newInstance();
	        	if(fm.shouldFail(eD, waitingOnEventId,waitingOnEventStatus, conn)){
	        		b = false;
	        		String message = "This event has been rejected because the event "+waitingOnEventId+" that this is prior is determined to cause this event to fail by meditor class: "+waitFailMediatorClass;
		  	        eD.setAttempt(0);
		  	        setEventStatus(eD, Event.STATUS_REJECTED);
		  	        loggingMessage(eD, message);
		  	        loggingMessage(eD, "Reason from fail mediator: "+fm.getReasonForFail());
	        	}
        	}catch(Exception e){
        		  b = false;
				  //StringWriter sw = new StringWriter();
				  //e.printStackTrace(new PrintWriter(sw));
				  //String stacktrace = sw.toString();
				  log.error(e.getMessage(),e);
				  String message = "This event has been rejected because a problem with the fail mediator class("+waitFailMediatorClass+")";
				  eD.setAttempt(0);
				  setEventStatus(eD, Event.STATUS_REJECTED);
				  loggingMessage(eD, message);
        	}
        }else{
	        if (Event.STATUS_REJECTED.equals(waitingOnEventStatus)) {
		      String message = "This event has been rejected because the event "+waitingOnEventId+" that this is waiting to is REJECTED";
	          eD.setAttempt(0);
	          setEventStatus(eD, Event.STATUS_REJECTED);
	          loggingMessage(eD, message);
	        }
	        b = (Event.STATUS_PROCESSED.equals(waitingOnEventStatus));
        }
        
      }
      return b;
    }

    public void updateEventProperty(int pEventId, String pPropertyName, Object pNewValue, String pModBy) throws RemoteException{
         Connection conn = null;
         try {
             conn = getConnection();
             DBCriteria dbc = new DBCriteria();
             dbc.addEqualTo(EventPropertyDataAccess.EVENT_ID, pEventId);
             dbc.addEqualTo(EventPropertyDataAccess.SHORT_DESC, pPropertyName);
             String whereSql = EventPropertyDataAccess.getSqlSelectIdOnly(EventPropertyDataAccess.EVENT_PROPERTY_ID, dbc);
             updateEventPropertyValue(conn, whereSql,  pNewValue,  pModBy);
         } catch (Exception e) {
             e.printStackTrace();
             throw new RemoteException(e.getMessage());
         } finally {
             closeConnection(conn);
         }
     }

     private int updateEventPropertyValue(Connection pCon, String pWhereSql, Object pPropertyValue, String pModBy)
        throws SQLException {

        int n = 0;
        String sql = "UPDATE CLW_EVENT_PROPERTY SET MOD_BY = ?,MOD_DATE = ?,STRING_VAL = ?,NUMBER_VAL = ? WHERE EVENT_PROPERTY_ID in (" + pWhereSql + ")";
        PreparedStatement pstmt = pCon.prepareStatement(sql);

        int numVal = 0;
        String strVal = "" ;
        if (pPropertyValue != null && pPropertyValue instanceof String){
          strVal = (String)pPropertyValue;
        } else if (pPropertyValue != null && pPropertyValue instanceof Integer){
          numVal = ((Integer)pPropertyValue).intValue();
        }
        Date modDate = new java.util.Date(System.currentTimeMillis());

        int i = 1;

        pstmt.setString(i++,pModBy);
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(modDate));
        pstmt.setString(i++,strVal);
        pstmt.setInt(i++,numVal);

        n = pstmt.executeUpdate();
        pstmt.close();

       return n;
    }
     
     /**
      * Check if a event with process id=processId and status = 'READY' or 'IN_PROGRESS' exists. Will also 
      * compare the property list (epDV) if passed in
      * @param processId
      * @return 
      */
     public boolean activeEventExist(int processTemplateId, EventPropertyDataVector epDV) 
 	throws RemoteException {
 		Connection conn = null;
 		ArrayList<String> statusList = new ArrayList<String>();
 		statusList.add(Event.STATUS_READY);
 		statusList.add(Event.STATUS_IN_PROGRESS);

 		try {
 			conn = getConnection();
 			DBCriteria dbc = new DBCriteria();
 			dbc.addEqualTo(EventDataAccess.TYPE, Event.TYPE_PROCESS);
 			dbc.addOneOf(EventDataAccess.STATUS, statusList);

 			DBCriteria isolatedCriterita = new DBCriteria();
 			dbc.addJoinTable(EventPropertyDataAccess.CLW_EVENT_PROPERTY + " P_TEMPLATE");
 			isolatedCriterita.addEqualTo("P_TEMPLATE." + EventPropertyDataAccess.TYPE, Event.PROPERTY_PROCESS_TEMPLATE_ID);
 			isolatedCriterita.addEqualTo("P_TEMPLATE." + EventPropertyDataAccess.NUMBER_VAL, processTemplateId);
 			isolatedCriterita.addCondition("P_TEMPLATE." + EventPropertyDataAccess.EVENT_ID + "=" + EventDataAccess.CLW_EVENT + "." + EventDataAccess.EVENT_ID);
 			dbc.addIsolatedCriterita(isolatedCriterita);

 			com.cleanwise.service.api.value.EventDataVector events = EventDataAccess.select(conn, dbc);
 			if (epDV == null || epDV.size() == 0)
					return events.size()>0;
	  	    log.info("activeEventExist() ===> Number of events ="+ ((events!= null) ? events.size():"0"));
 			for (int i = 0; i < events.size(); i++){
 				int eventId = ((com.cleanwise.service.api.value.EventData)events.get(i)).getEventId();
 				dbc = new DBCriteria();
 				ArrayList<String> typeList = new ArrayList<String>();
 				dbc.addEqualTo(EventPropertyDataAccess.EVENT_ID, eventId);
 				dbc.addEqualTo(EventPropertyDataAccess.TYPE, Event.PROCESS_VARIABLE);
 				EventPropertyDataVector epList = EventPropertyDataAccess.select(conn, dbc); 				
 				boolean match=true;
 				for (int j = 0; j < epDV.size() && match; j++){
 					EventPropertyData ep = (EventPropertyData)epDV.get(j);
 					if (ep.getShortDesc().equals("process_id"))
 							continue;
 					for (Object o1 : epList){
 						EventPropertyData ep1 = (EventPropertyData)o1;
 						if (ep.getShortDesc().equals(ep1.getShortDesc())){
 							String varType = ep.getVarClass();                			 
 							if("java.lang.Integer".equals(varType)) {
 								if (ep.getNumberVal()!=ep1.getNumberVal()){
 									match = false;
 								}         							
 							} else if("java.lang.String".equals(varType)) {
 								if (!Utility.isEqual(ep.getStringVal(),ep1.getStringVal())){
 									match = false;
 								}         							
 							} else if("java.util.HashMap".equals(varType)){
 								match = matchParametersMap(ep, ep1);
 							}
 							break;
 						}
 					}
 				}
 				if (match)
 					return true;
 			}             
 		} catch (Exception e) {
 			e.printStackTrace();
 			throw new RemoteException(e.getMessage());
 		} finally {
 			closeConnection(conn);
 		}
 		return false;
 	}
     
    private boolean  matchParametersMap(EventPropertyData ep, EventPropertyData ep1) throws Exception {
    	HashMap parameters = (HashMap)ObjectUtil.bytesToObject(ep.getBlobValue());
   	    log.info("matchParametersMap() ===> parameters ="+ ((parameters!= null) ? parameters.toString():"NULL"));
    	HashMap parameters1 =(HashMap)getBlobObject (ep1.getStorageTypeCd(), ep1.getBinaryDataServer(), ep1.getBlobValueSystemRef());
   	    log.info("matchParametersMap() ===> parameters1 ="+ ((parameters1!=null) ? parameters1.toString() : "NULL"));
    	boolean match = true;
   	    if (Utility.isSet(parameters) && !Utility.isSet(parameters1) ||
   	    	Utility.isSet(parameters1) && !Utility.isSet(parameters)){
   	    	match= false;
   	    } else if (Utility.isSet(parameters) && Utility.isSet(parameters1) ) {
   	    	if (parameters.size()!=parameters1.size()) {
   	    		match = false;
   	    	} else {
   	  	    	match = true;
   	   	    	Iterator it = parameters.keySet().iterator();
   	   	    	while (it.hasNext()){
   	   	    		String paramName = (String)it.next();
   	   	    		if (parameters1.containsKey(paramName)){
   	   	   	    		match &= Utility.isEqual((String)parameters.get(paramName),(String)parameters1.get(paramName));
   	   	    		} else {
   	   	    			match =  false;
   	   	    			break;
   	   	    		}
   	   	    	}  	    	    	
   	    	}
   	    }
   	    log.info("matchParametersMap() ===> match ="+ match);
     	return match;
    }
    
    private Object getBlobObject ( String storageTypeCd, String binaryDataServer,String blobValueSystemRef ) throws Exception {
    	Object blobObject = null;
    	if (Utility.isSet(blobValueSystemRef)) {
            byte[] blobValueBytes = bsa.readBlob(storageTypeCd, binaryDataServer, blobValueSystemRef);
            if (blobValueBytes != null) {
                blobObject = bytesToObject(blobValueBytes);
            }
        }
   	    return blobObject;
    }
    
    public IdVector updateEventFromHoldToReady() throws RemoteException {
    	log.info(".updateEventFromHoldToReady()");
        
    	String query = "SELECT event_id" +
                " FROM clw_event" +
                " WHERE status = ? AND process_time < SYSDATE";
    	Connection conn = null;

        try {
            conn = getConnection();
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.setString(1, RefCodeNames.EVENT_STATUS_CD.STATUS_HOLD);
            ResultSet rs = sttm.executeQuery();
	        IdVector eventIds = new IdVector();
	        while (rs.next()) {
	            int eventId = rs.getInt(1);
	            eventIds.add(eventId);
	        }
	        rs.close();
	        sttm.close();
	        IdVector updEventIds = new IdVector();
	        if (!eventIds.isEmpty()){		        
	        	updEventIds = getEventVWithUpdateStatus(conn, eventIds, Event.STATUS_HOLD, Event.STATUS_READY);	        
	        }
	        return updEventIds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

}
