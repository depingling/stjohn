package com.cleanwise.service.api.session;

/**
 * Title:        Report
 * Description:  Remote Interface for Report Stateless Session Bean
 * Purpose:      Provides access to the methods for lawson Backfill EJB interface
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import com.cleanwise.service.api.util.ReportInterf;


public interface Report extends javax.ejb.EJBObject
{

    /** Gets All names of reports for the user
     * @param pUserId      - the user id
     * @param pUserTypeCd  - the user type code
     * @return list of reports the user eligible to request
     * @throws RemoteException
     */
    public ArrayList getAllReportNamesList(int pUserId, String pUserTypeCd)
            throws RemoteException;

   /** Gets reports for the user
   * @param pUserId the user id
   * @param pUserTypeCd the user type code    Active Ship To's by Account
   * @return list of reports the user eligible to request
   * @throws RemoteException
   */
  public GenericReportViewVector getReportList(int pUserId, String pUserTypeCd)
   throws RemoteException;

    /**
     * Gets reports for the user
     *
     * @param pUserId      the user id
     * @param pUserTypeCd  the user type code
     * @param pReportNames report names<optionally>
     * @return list of reports the user eligible to request
     * @throws java.rmi.RemoteException if an errors
     */
    public GenericReportViewVector getReportList(int pUserId,
                                                 String pUserTypeCd,
                                                 List pReportNames) throws RemoteException;
  /** Gets all generic reports
   * @return list of reports the user eligible to request
   * @throws RemoteException
   */
  public GenericReportViewVector getAllReports()
   throws RemoteException;

    /**
   * Gets generic report object
   * @param pGenericReportId report id.
   * @return  GenericReportData object
   * @throws  RemoteException Required by EJB 1.0
   */
   public GenericReportData getGenericReport(int pGenericReportId)
   throws RemoteException;

   /**
   * Gets report sql and script texts
   * @param pGenericReportId report id.
   * @return  String[] object
   * @throws  RemoteException Required by EJB 1.0
   */
   public String[] getGenericReportSql(int pGenericReportId)
   throws RemoteException;

   /**
   * Saves generic report.
   * @param pReport generic report object with null Clober fields
   * @param pSql pSql sql text
   * @param pScript pScript pl/sql script
   * @param pUser pUser user name
    *@return int report id
   * @throws  RemoteException Required by EJB 1.0
   */
   public int saveGenericReport (GenericReportData pReport,
             String pSql, String pScript, String pUser)
   throws RemoteException;

   /**
   * Deletes generic report object
   * @param pGenericReportId report id.
   * @throws  RemoteException Required by EJB 1.0
   */
   public void deleteGenericReport(int pGenericReportId)
   throws RemoteException;

   /**
   * Gets list of generic report categories
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public ArrayList getGenericReportCategories()
   throws RemoteException;

   /**
   * Gets list of generic report names
   * @param pCategory category name. If null or empty returns all report names
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public ArrayList getGenericReportNames(String pCategory)
   throws RemoteException;

   /**
   * Gets list of generic report criteria control names. Picks up reprort by report name
   * and category if it is not null. If category is null and more than 1 reports found
   * uses default category "CUSTOMER" to resolve multiplicity
   * @param pCategory report category.
   * @param pName report name.
   * @return collection of  GenericReportControlView objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public GenericReportControlViewVector getGenericReportControls(String pCategory, String pName)
   throws RemoteException;

   /**
   * Gets list of generic report criteria control names
   * @param pGenericReportId report id.
   * @return collection of  GenericReportControlView objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public GenericReportControlViewVector getGenericReportControls(int pGenericReportId)
   throws RemoteException;

   /**
   * Gets list of generic report criteria control names
   *
   * @param pCategory report category
   * @param pName report name.
   * @param pParams report parameters
   * @return report table
   * @throws   RemoteException Required by EJB 1.0
   */
   public GenericReportResultView processGenericReport(String pCategory, String pName, Map pParams)
   throws RemoteException;

   /**
   * Gets a list of generic report tables
   *
   * @param pCategory report category
   * @param pName report name.
   * @param pParams report parameters
   * @return vector of report tables
   * @throws   RemoteException Required by EJB 1.0
   */
   public GenericReportResultViewVector processGenericReportMulti(String pCategory, String pName, Map pParams)
   throws RemoteException;

   public GenericReportResultViewVector processGenericReportMulti(String pCategory, String pName, Map pParams, String pDbName)
   throws RemoteException;
   /**
   * Runs report
   *
   * @param pGenericReportId report id
   * @param pParams report parameters
   * @param pUserId user requested the report
   * @param pDeletePrevResultFl indicator to delete previous versions of the report
   * @return ReportResultData object
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportResultData processAnalyticReport(
          int pGenericReportId,
          Map pParams,
          int pUserId,
          String pUserName,
          boolean pDeletePrevResultFl)
   throws RemoteException ;


   /**
    * Check report GenericReport or DomUniversalReport
    *
    * @param pGenericReportId
    * @param pParams
    * @param pUserId
    * @param pUserName
    * @param pDeletePrevResultFl
    * @return
    * @throws RemoteException
    */
   public Serializable checkAnalyticReport(int pGenericReportId,
           Map pParams, int pUserId, String pUserName,
           boolean pDeletePrevResultFl) throws RemoteException;
   /**
   * Runs report
   *
   * @param pGenericReportId report id
   * @param pReportScheduleId report schedule id if scheduled report or 0
   * @param pParams report parameters
   * @param pUserId user requested the report
   * @param pDeletePrevResultFl indicator to delete previous versions of the report
   * @return ReportResultData object
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportResultData processAnalyticReport(
                   int pGenericReportId,
                   int pReportScheduleId,
                   Map pParams,
                   int pUserId,
                   String pUserName,
                   boolean pDeletePrevResultFl)
   throws RemoteException;

  /**
   * Saves failed report error message as report result
   *
   * @param pGenericReportId report id
   * @param pReportScheduleId report schedule id if scheduled report or 0
   * @param pParams report parameters
   * @param pUserId user requested the report
   * @param pErrorMess set of error messages
   * @return ReportResultData object
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportResultData saveAnalyticReportError(
                   int pGenericReportId,
                   int pReportScheduleId,
                   Map pParams,
                   int pUserId,
                   String pUserName,
                   ArrayList pErrorMess)
   throws RemoteException;

   /**
   * Returns report result view
   *
   * @param pReportResultId
   * @return PreparedReportView obect
   * @throws   RemoteException Required by EJB 1.0
   * @throws   DataNotFoundException
   */
   public PreparedReportView getPreparedReport(int pReportResultId)
   throws RemoteException, DataNotFoundException;

   /**
   * Returns list of prepared reports for the user
   *
   * @param pFilters a set of filters (REPORT_CATEGORY,REPORT_NAME,MIN_DATE,MAX_DATE,THE_USER_REPORTS_ONLY)
   * @param pUserId user id
   * @return vector of PreparedReportView obects
   * @throws   RemoteException Required by EJB 1.0
   */
   public PreparedReportViewVector getAnalyticReportArchive(Map pFilters, int pUserId)
   throws RemoteException;

  /** Gets report data of requested report
   * @param pReportResultId report result id
   * @param pFirstRecordId id of first record to return (when report is too big for one request)
   * @param pNbRecords maximum of records to be returned
   * @return set of ReportResultLineData objects
   * @throws   RemoteException Required by EJB 1.0
   */
  public ReportResultLineDataVector
      readArchiveReport(int pReportResultId, int pFirstRecordId, int pNbRecords)
  throws RemoteException;

  /** Gets report data of requested report
   * @param pReportResultId report result id
   * @return set of GenericReportResultView objects
   * @throws   RemoteException Required by EJB 1.0
   */
  public GenericReportResultViewVector
      readArchiveReport(int pReportResultId)
  throws RemoteException;


  /** Makes record in report result association table to indicate that report was read
   * @param pReportResultId report result id
   * @param pUserId user id
   * @throws   RemoteException Required by EJB 1.0
   */
  public void markReportAsRead(int pReportResultId, int pUserId)
  throws RemoteException;

  /** Gets a set of group or user identifiers
   * @param pReportResultId report result id
   * @param pReportResultAssocCd (USER or GROUP)
   * @return IdVector of identifiers
   * @throws   RemoteException Required by EJB 1.0
   */
  public IdVector getReportResultAssoc (int pReportResultId, String pReportResultAssocCd)
  throws RemoteException;

  /** Removes and adds report result associations
   * @param pReportResultId report result id
   * @param pIdVectorAdd associations to add
   * @param pIdVectorDel associations to remove
   * @param pUser userName
   * @throws   RemoteException Required by EJB 1.0
   */
  public void updateReportResultAssoc (int pReportResultId,
                                       IdVector pIdVectorAdd,
                                       IdVector pIdVectorDel,
                                       String pReportResultAssocCd,
                                       String pUser)
  throws RemoteException;

  /** Deletes report results from the DB
   * @param pReportResultIds set of report result ids
   * @param pUser userName
   * @throws   RemoteException Required by EJB 1.0
   */
  public void deletePreparedReports (IdVector pReportResultIds,
                                       String pUser)
  throws RemoteException;

   /**
   * Returns list of report schedule view objects
   *
   * @param pFilters a set of filters (REPORT_CATEGORY,REPORT_NAME)
   * @return vector of ReportScheduleView obects
   * @throws   RemoteException Required by EJB 1.0
   */

  public ReportScheduleViewVector getReportSchedules(Map pFilters)
   throws RemoteException;

  /**
  * Returns list of report schedule view objects
  *
  * @param pFilters a set of filters (REPORT_CATEGORY,REPORT_NAME)
  * @param pUser  application user data
  * @return vector of ReportScheduleView obects
  * @throws   RemoteException Required by EJB 1.0
  */

  public ReportScheduleViewVector getReportSchedules(Map pFilters, UserData pUser)
   throws RemoteException;

   /**
   * Returns report schedule view object
   *
   * @param pReportScheduleId
   * @return ReportScheduleView obect
   * @throws   RemoteException Required by EJB 1.0
   * @throws   DataNotFoundException
   */
   public ReportScheduleView getReportScheduleView(int pReportScheduleId)
   throws RemoteException, DataNotFoundException;

   /**
   * Saves report shedule
   * @param pReportScheduleJoin schedule join object
   * @param pUser user name
   * @return ReportScheduleJoinView obect
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportScheduleJoinView
       saveReportSchedule(ReportScheduleJoinView pReportScheduleJoin, String pUser)
   throws RemoteException;

   /**
   * Gets report shedule join object
   * @param pReportScheduleId report schedule id
   * @return ReportScheduleJoinView obect
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportScheduleJoinView getReportSchedule(int pReportScheduleId)
   throws RemoteException;

   /**
   * Deletes a set of report schedules
   * @param pReportScheduleIds report schedule ids
   * @throws   RemoteException Required by EJB 1.0
   */
   public void deleteReportSchedules(IdVector pReportScheduleIds)
   throws RemoteException;

   /**
   * Runs sheduled report
   *
   * @param pReportScheduleId report schedule id
   * @param pUserId user id requested the report (0 if runs by script)
   * @param pUserName user name requested the report (null or UNKNOWN if runs by script)
   * @return ReportResultData object
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportResultData processScheduledReport(int pReportScheduleId, int pUserId, String pUserName)
   throws RemoteException;

   /**
   * Gets active report ids cheduled to run on the date
   *
   * @param pDate requested date (date only - hours, minutes, etc. will be cut off).
   * Takes current date if null
   * @return Set of report schedule ids
   * @throws   RemoteException Required by EJB 1.0
   */
   public IdVector getScheduledReportIds(Date pDate)
   throws RemoteException;
   /**
   * Sends report notification to users
   *
   * @param pReportRes report result object
   * @param pUserIds set of user group ids
   * @param pUserIds set of user ids
   * @throws   RemoteException Required by EJB 1.0
   */
   public int sendReportNotification(ReportResultData pReportRes,
                                      IdVector pGroupIds,
                                      IdVector pUserIds)
   throws RemoteException;

   /**
   * Sets report result protected flag Y - yes, N - no
   *
   * @param pReportResultId report result id
   * @param pVal value to set (Y or N)
   * @param pUser user name
   * @throws   RemoteException Required by EJB 1.0
   */
   public int setReportResultProtection(int pReportResultId, String pVal, String pUser)
   throws RemoteException;

   /**
   * Sends report failure notification
   *
   * @param pReportRes report result object
   * @param pErrorMessAL list of error messages
   * @throws   RemoteException Required by EJB 1.0
   */
   public void sendReportErrorNotification(ReportResultData pReportRes,
                                      ArrayList pErrorMessAL)
   throws RemoteException;

   /**
   * Sends report results to users
   *
   * @param pReportRes report result object
   * @param pUserIds set of user group ids
   * @param pUserIds set of user ids
   * @throws   RemoteException Required by EJB 1.0
   */
   public int sendReportResult(ReportResultData pReportRes,
                                      IdVector pGroupIds,
                                      IdVector pUserIds,
                                      boolean pSuppressEmailFlag)
   throws RemoteException;

   /**
     * Gets report result Ids of requested report
     *
     * @param pGenericReportId generic report id
     * @param pUserId          user
     * @param pStatusCd        statuf of report
     * @return ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector getArchiveReportIds(int pGenericReportId, int pUserId, String pStatusCd) throws RemoteException;

    public ReportResultParamDataVector getReportResultParams(int pReportResultId) throws RemoteException;
    public Object getLocateFilterByIds(String controlName, String controlValue ) throws RemoteException ;

    public Object checkInputDate(String pDate, String name, String dateFmt) throws RemoteException ;
    public Object getInstanceReport(GenericReportData pReportData) throws RemoteException;
}


