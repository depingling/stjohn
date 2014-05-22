package com.cleanwise.service.api.session;

/**
* Title:        ReportBean
 * Description:  Bean implementation for Report Session Bean
 * Purpose:      Ejb for interface with ReportOrder Ejbs
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.GenericReportDataAccess;
import com.cleanwise.service.api.dao.GroupAssocDataAccess;
import com.cleanwise.service.api.dao.GroupDataAccess;
import com.cleanwise.service.api.dao.ReportResultAssocDataAccess;
import com.cleanwise.service.api.dao.ReportResultDataAccess;
import com.cleanwise.service.api.dao.ReportResultLineDataAccess;
import com.cleanwise.service.api.dao.ReportResultParamDataAccess;
import com.cleanwise.service.api.dao.ReportScheduleDataAccess;
import com.cleanwise.service.api.dao.ReportScheduleDetailDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.ReportAPI;
import com.cleanwise.service.api.reporting.DomUniversalReport;
import com.cleanwise.service.api.reporting.GenericReport;
import com.cleanwise.service.api.reporting.GenericReportMulti;
import com.cleanwise.service.api.reporting.ReportWriterUtil;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ReportInterf;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.XSQLParser;
import com.cleanwise.service.api.value.AccountUIView;
import com.cleanwise.service.api.value.AccountUIViewVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportControlView;
import com.cleanwise.service.api.value.GenericReportControlViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportDataVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportView;
import com.cleanwise.service.api.value.GenericReportViewVector;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.HistoryData;
import com.cleanwise.service.api.value.HistoryObjectData;
import com.cleanwise.service.api.value.HistorySecurityData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemView;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.PreparedReportView;
import com.cleanwise.service.api.value.PreparedReportViewVector;
import com.cleanwise.service.api.value.ReportResultAssocData;
import com.cleanwise.service.api.value.ReportResultAssocDataVector;
import com.cleanwise.service.api.value.ReportResultData;
import com.cleanwise.service.api.value.ReportResultDataVector;
import com.cleanwise.service.api.value.ReportResultLineDataVector;
import com.cleanwise.service.api.value.ReportResultParamData;
import com.cleanwise.service.api.value.ReportResultParamDataVector;
import com.cleanwise.service.api.value.ReportSchedGroupShareView;
import com.cleanwise.service.api.value.ReportSchedGroupShareViewVector;
import com.cleanwise.service.api.value.ReportSchedUserShareView;
import com.cleanwise.service.api.value.ReportSchedUserShareViewVector;
import com.cleanwise.service.api.value.ReportScheduleData;
import com.cleanwise.service.api.value.ReportScheduleDataVector;
import com.cleanwise.service.api.value.ReportScheduleDetailData;
import com.cleanwise.service.api.value.ReportScheduleDetailDataVector;
import com.cleanwise.service.api.value.ReportScheduleJoinView;
import com.cleanwise.service.api.value.ReportScheduleView;
import com.cleanwise.service.api.value.ReportScheduleViewVector;
import com.cleanwise.service.api.value.ShipToViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;

public class ReportBean extends ReportAPI
{

	private static Category log = Category.getInstance(ReportBean.class);

	/**
     *
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.CreateException
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /** Gets All names of reports for the user
     * @param pUserId      - the user id
     * @param pUserTypeCd  - the user type code
     * @return list of reports the user eligible to request
     * @throws RemoteException
     */
    public ArrayList getAllReportNamesList(int pUserId, String pUserTypeCd)
            throws RemoteException
    {
        Connection con = null;
        ArrayList reportNameList = new ArrayList();
        DBCriteria dbc;
        String errorMess = "No error";

        try {
            //Find all groups for the user
            con = getConnection();
            dbc = new DBCriteria();
            dbc.addEqualTo(GroupAssocDataAccess.USER_ID, pUserId);
            dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
            IdVector groupIdV = GroupAssocDataAccess.selectIdOnly(con, GroupAssocDataAccess.GROUP_ID, dbc);

            //Get group for everyone
            dbc = new DBCriteria();
            LinkedList defaultGroups = new LinkedList();
            defaultGroups.add("EVERYONE");
            defaultGroups.add(pUserTypeCd);
            dbc.addOneOf(GroupDataAccess.SHORT_DESC, defaultGroups);
            IdVector everyoneIdV = GroupDataAccess.selectIdOnly(con, GroupDataAccess.GROUP_ID, dbc);
            groupIdV.addAll(everyoneIdV);

            Iterator itG = groupIdV.iterator();
            while(itG.hasNext()){
                Integer id = (Integer) itG.next();
                log("getAllReportNamesList() => " + id);
            }

            //Get all reports for groups
            dbc = new DBCriteria();
            dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, groupIdV);
            dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
            IdVector reportIdV = GroupAssocDataAccess.selectIdOnly(con,GroupAssocDataAccess.GENERIC_REPORT_ID, dbc);

            //get detail for reports
            dbc = new DBCriteria();
            dbc.addOneOf(GenericReportDataAccess.GENERIC_REPORT_ID, reportIdV);
            dbc.addOrderBy(GenericReportDataAccess.NAME);
            GenericReportDataVector reportDV = GenericReportDataAccess.select(con,dbc);

            for(int ii=0; ii<reportDV.size(); ii++) {
                GenericReportData rD = (GenericReportData) reportDV.get(ii);
                reportNameList.add(rD.getName());
            }

            return reportNameList;

        } catch (Exception exc) {
            errorMess = "Error: " + exc.getMessage();
            exc.printStackTrace();
            logError(errorMess);
            throw new RemoteException(errorMess);
        }

        finally {
            closeConnection(con);
        }
    }

    /**
     * Gets reports for the user
     *
     * @param pUserId     the user id
     * @param pUserTypeCd the user type code
     * @return list of reports the user eligible to request
     * @throws java.rmi.RemoteException
     */
    public GenericReportViewVector getReportList(int pUserId, String pUserTypeCd) throws RemoteException {
        return getReportList(pUserId, pUserTypeCd,null);
    }

    /** Gets reports for the user
     * @param pUserId the user id
     * @param pUserTypeCd the user type code
     * @param pReportNames report names<optionally>
     * @return list of reports the user eligible to request
     * @throws java.rmi.RemoteException if an errors
     */
  public GenericReportViewVector getReportList(int pUserId, String pUserTypeCd, List pReportNames) throws RemoteException {
   Connection con = null;
   GenericReportViewVector reportList = new GenericReportViewVector();
   DBCriteria dbc;
   String errorMess = "No error";
   try {
     con = getConnection();
     dbc = new DBCriteria();
     dbc.addEqualTo(GroupAssocDataAccess.USER_ID,pUserId);
     dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
        RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
     IdVector groupIdV =
       GroupAssocDataAccess.selectIdOnly(con,GroupAssocDataAccess.GROUP_ID,dbc);

     dbc = new DBCriteria();
     LinkedList defaultGroups = new LinkedList();
     defaultGroups.add("EVERYONE");
     defaultGroups.add(pUserTypeCd);
     dbc.addOneOf(GroupDataAccess.SHORT_DESC,defaultGroups);

     IdVector everyoneIdV =
       GroupDataAccess.selectIdOnly(con,GroupDataAccess.GROUP_ID,dbc);
     groupIdV.addAll(everyoneIdV);

     dbc = new DBCriteria();
     dbc.addOneOf(GroupAssocDataAccess.GROUP_ID,groupIdV);
     dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
        RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);

     IdVector reportIdV =
       GroupAssocDataAccess.selectIdOnly(con,GroupAssocDataAccess.GENERIC_REPORT_ID,dbc);

     dbc = new DBCriteria();
     dbc.addOneOf(GenericReportDataAccess.GENERIC_REPORT_ID, reportIdV);
     if(pReportNames!=null)  {
          dbc.addOneOf(GenericReportDataAccess.NAME,pReportNames);
     }
       GenericReportDataVector reportDV = GenericReportDataAccess.select(con,dbc);

     for(int ii=0; ii<reportDV.size(); ii++) {
       GenericReportData rD = (GenericReportData) reportDV.get(ii);
       if (Utility.isUserAutorizedForReport(pUserTypeCd, rD.getUserTypes())){
         GenericReportView rVw = GenericReportView.createValue();
         rVw.setGenericReportId(rD.getGenericReportId());
         rVw.setReportCategory(rD.getCategory());
         rVw.setReportName(rD.getName());
         rVw.setLongDesc(rD.getLongDesc());
         rVw.setDBName(rD.getDbName());
         if(Utility.isSet(rD.getClassname())){
      	   rVw.setReportClass(rD.getClassname());
         }
  //       rVw.setUserTypes(rD.getUserTypes());
         reportList.add(rVw);
       }
     }


     return reportList;
   } catch (Exception exc) {
     errorMess = "Error: "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
       closeConnection(con);
   }
  }
  /** Gets all generic reports
   * @return list of reports the user eligible to request
   * @throws RemoteException
   */
  public GenericReportViewVector getAllReports()
   throws RemoteException
   {
   Connection con = null;
   GenericReportViewVector reportList = new GenericReportViewVector();
   DBCriteria dbc;
   String errorMess = "No error";
   try {
     con = getConnection();
     GenericReportDataVector reportDV = GenericReportDataAccess.selectAll(con);
     for(int ii=0; ii<reportDV.size(); ii++) {
       GenericReportData rD = (GenericReportData) reportDV.get(ii);
       GenericReportView rVw = GenericReportView.createValue();
       rVw.setGenericReportId(rD.getGenericReportId());
       rVw.setReportCategory(rD.getCategory());
       rVw.setReportName(rD.getName());
       rVw.setLongDesc(rD.getLongDesc());
       rVw.setDBName(rD.getDbName());
       if(Utility.isSet(rD.getClassname())){
    	   rVw.setReportClass(rD.getClassname());
       }
       reportList.add(rVw);
     }


     return reportList;
   } catch (Exception exc) {
     errorMess = "Error: "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
       closeConnection(con);
   }
  }

   /**
   * Gets generic report object
   * @param pGenericReportId report id.
   * @return  GenericReportData object
   * @throws  RemoteException Required by EJB 1.0
   */
   public GenericReportData getGenericReport(int pGenericReportId)
   throws RemoteException
   {
   Connection con = null;
   GenericReportData report = null;
   String errorMess = "No error";
   try {
     con = getConnection();
     report = GenericReportDataAccess.select(con, pGenericReportId);
     //Can't return blob data
     report.setSqlText(null);
     report.setScriptText(null);
   } catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportControls() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     closeConnection(con);
   }
   log.info("SVC: getGenericReport() report = " + report);
  return report;
  }

   /**
   * Gets report sql and script texts
   * @param pGenericReportId report id.
   * @return  String[] object
   * @throws  RemoteException Required by EJB 1.0
   */
   public String[] getGenericReportSql(int pGenericReportId)
   throws RemoteException
   {
   Connection con = null;
   String[] sql = new String[2];
   String errorMess = "No error";
   try {
      con = getConnection();
      GenericReportData reportD =
              GenericReportDataAccess.select(con, pGenericReportId);
      String repTxt = reportD.getSqlText();
	  if(repTxt==null) repTxt = "";
      sql[0] = repTxt;
      String repScript = reportD.getScriptText();
	  if(repScript==null) repScript = "";
      sql[1] = repScript;
   } catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportControls() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     closeConnection(con);
   }
  return sql;
  }

   /**
   * Saves generic report.
   * @param GenericReportData generic report object with null Clober fields
   * @param String pSql sql text
   * @param String pScript pl/sql script
   * @param String pUser user name
    *@return int report id
   * @throws  RemoteException Required by EJB 1.0
   */
   public int saveGenericReport (GenericReportData pReport,
             String pSql, String pScript, String pUser)
   throws RemoteException
   {
   Connection con = null;
   String errorMess = "No error";
   try {
     con = getConnection();
     int reportId = pReport.getGenericReportId();
     boolean isCreate = reportId==0;
     pReport.setSqlText(null);
     pReport.setScriptText(null);
     pReport.setModBy(pUser);
     if(reportId==0) {
       pReport.setAddBy(pUser);
       pReport = GenericReportDataAccess.insert(con, pReport);
       reportId = pReport.getGenericReportId();
     } else {
       GenericReportDataAccess.update(con, pReport);
     }
     if(Utility.isSet(pSql) || Utility.isSet(pScript)) {
       if (ORACLE.equals(databaseName)) {
        String updateBlobSql =
        "update CLW_GENERIC_REPORT set SQL_TEXT=empty_clob() , SCRIPT_TEXT=empty_clob() "+
                "WHERE GENERIC_REPORT_ID = "+reportId;
        PreparedStatement pstmt1 = con.prepareStatement(updateBlobSql);
        pstmt1.executeUpdate();
        pstmt1.close();

      //Read record for update
      Statement stmn=con.createStatement();
      String sql =
        "select SQL_TEXT, SCRIPT_TEXT "+
        " from CLW_GENERIC_REPORT "+
        " where GENERIC_REPORT_ID = "+reportId+
        " for update";

      ResultSet rs1=stmn.executeQuery(sql);
      rs1.next();
      CLOB sqlClob = (CLOB) rs1.getClob(1);
      CLOB scriptClob = (CLOB) rs1.getClob(2);
      if(Utility.isSet(pSql)) {
        sqlClob.putString(1L,pSql);
      }
      if(Utility.isSet(pScript)) {
        scriptClob.putString(1L,pScript);
      }
      rs1.close();
      stmn.close();
      //
      updateBlobSql =
      "update CLW_GENERIC_REPORT set SQL_TEXT=? , SCRIPT_TEXT=? "+
              "WHERE GENERIC_REPORT_ID = "+reportId;

      pstmt1 = con.prepareStatement(updateBlobSql);
      pstmt1.setClob(1, sqlClob);
      pstmt1.setClob(2, scriptClob);
      pstmt1.executeUpdate();
      pstmt1.close();
     } else { //Postgres DB (Enterprise DB)
         String updateBlobSql =
             "update CLW_GENERIC_REPORT set SQL_TEXT=null , SCRIPT_TEXT=null "+
                     "WHERE GENERIC_REPORT_ID = "+reportId;
             PreparedStatement pstmt1 = con.prepareStatement(updateBlobSql);
             pstmt1.executeUpdate();
             pstmt1.close();

             //Read record for update
             Statement stmn=con.createStatement();
             String sql =
               "select SQL_TEXT, SCRIPT_TEXT "+
               " from CLW_GENERIC_REPORT "+
               " where GENERIC_REPORT_ID = "+reportId+
               " for update";

             ResultSet rs1=stmn.executeQuery(sql);
             rs1.next();
             String sqlString = rs1.getString(1);
             String scriptString = rs1.getString(2);
             if(Utility.isSet(pSql)) {
               sqlString = pSql.trim();
             }
             if(Utility.isSet(pScript)) {
               scriptString = pScript.trim();
             }
             rs1.close();
             stmn.close();
             //
             updateBlobSql =
             "update CLW_GENERIC_REPORT set SQL_TEXT=? , SCRIPT_TEXT=? "+
                     "WHERE GENERIC_REPORT_ID = "+reportId;

             pstmt1 = con.prepareStatement(updateBlobSql);
             pstmt1.setString(1, sqlString);
             pstmt1.setString(2, scriptString);
             pstmt1.executeUpdate();
             pstmt1.close();
     }     
   }
   recordReportChangeHistory(pReport, pSql, pScript, pUser, isCreate);
   return reportId;
 } catch (Exception exc) {
   errorMess = "Error. ReportBean.getGenericReportControls() Exception happened. "+exc.getMessage();
   exc.printStackTrace();
   logError(errorMess);
   throw new RemoteException(errorMess);
 }
 finally {
   closeConnection(con);
 }
}
   
   private void recordReportChangeHistory(GenericReportData pReport, String pSql, String pScript, String pUser, boolean isCreate) throws Exception{
	    if (RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(pReport.getGenericReportType()) ||
	    		RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL.equals(pReport.getGenericReportType())){
		    HistoryData historyData = new HistoryData();
			Date date = new Date();
			historyData.setActivityDate(date);
			historyData.setAddBy(pUser);
			historyData.setAddDate(date);
	      	historyData.setHistoryTypeCd(isCreate? RefCodeNames.HISTORY_TYPE_CD.CREATE_GENERIC_REPORT : RefCodeNames.HISTORY_TYPE_CD.MODIFY_GENERIC_REPORT);
			historyData.setModBy(pUser);
			historyData.setModDate(date);
	        historyData.setUserId(pUser);
	        
			historyData.setAttribute01(pReport.getGenericReportId() + "");
			historyData.setAttribute02(pReport.getCategory());
			historyData.setAttribute03(pReport.getName());
			historyData.setAttribute04(pReport.getParameterToken());
			historyData.setAttribute05(pReport.getReportSchemaCd());
			historyData.setAttribute06(pReport.getInterfaceTable());
			historyData.setAttribute07(pReport.getGenericReportType());
			historyData.setAttribute08(pReport.getClassname());
			historyData.setAttribute09(pReport.getSupplementaryControls());
			historyData.setAttribute10(pReport.getRuntimeEnabled());
			historyData.setAttribute11(pReport.getLongDesc());
			historyData.setAttribute12(pReport.getUserTypes());
			historyData.setAttribute13(pReport.getDbName());			
			historyData.setClob1(pSql);
			historyData.setClob2(pScript);
			
			List<HistoryObjectData> historyObjectDatas = new ArrayList<HistoryObjectData>();
			HistoryObjectData reportObject = new HistoryObjectData();
			reportObject.setObjectId(pReport.getGenericReportId());
			reportObject.setObjectTypeCd(RefCodeNames.HISTORY_OBJECT_TYPE_CD.GENERIC_REPORT);
			reportObject.setAddBy(pUser);
			reportObject.setAddDate(date);
			reportObject.setModBy(pUser);
			reportObject.setModDate(date);
			historyObjectDatas.add(reportObject);
			
			List<HistorySecurityData> historySecurityDatas = new ArrayList<HistorySecurityData>();
			
			APIAccess factory = new APIAccess();
			History historyEjb = factory.getHistoryAPI();
			historyEjb.addHistory(historyData, historyObjectDatas, historySecurityDatas);
	    }
   }

   /**
   * Deletes generic report object
   * @param pGenericReportId report id.
   * @throws  RemoteException Required by EJB 1.0
   */
   public void deleteGenericReport(int pGenericReportId)
   throws RemoteException
   {
   Connection con = null;
   GenericReportData report = null;
   String errorMess = "No error";
   try {
     con = getConnection();
     GenericReportDataAccess.remove(con, pGenericReportId);
   } catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportControls() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     closeConnection(con);
   }
  }

   /**
   * Gets list of generic report categories
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public ArrayList getGenericReportCategories()
   throws RemoteException
   {
   Connection con = null;
   ArrayList categories = new ArrayList();
   String errorMess = "No error";
   ShipToViewVector shipTos = new  ShipToViewVector();
   try {
     con = getConnection();
     String sql = "Select distinct category from clw_generic_report order by upper(category)";
     Statement stmt = con.createStatement();
     ResultSet rs = stmt.executeQuery(sql);
     while (rs.next() ) {
       String category = rs.getString("category");
       categories.add(category);
     }
     rs.close();
     stmt.close();
   }
   catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportCategories() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
       closeConnection(con);
   }
  return categories;
  }

   /**
   * Gets list of generic report names
   * @param pCategory category name. If null or empty returns all report names
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public ArrayList getGenericReportNames(String pCategory)
   throws RemoteException
   {
   Connection con = null;
   ArrayList names = new ArrayList();
   String errorMess = "No error";
   ShipToViewVector shipTos = new  ShipToViewVector();
   try {
     con = getConnection();
     String sql = "Select name from clw_generic_report";
     if(Utility.isSet(pCategory)) {
        sql += " where category = ?";
     }
     sql += " order by upper(name) ";
     PreparedStatement stmt = con.prepareStatement(sql);
     if(Utility.isSet(pCategory)) {
         stmt.setString(1, pCategory);
     }

     ResultSet rs = stmt.executeQuery();
     while (rs.next() ) {
       String name = rs.getString("name");
       names.add(name);
     }
     rs.close();
     stmt.close();
   }
   catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportNames() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
       closeConnection(con);
   }
  return names;
  }

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
   throws RemoteException
   {
   Connection con = null;
   GenericReportControlViewVector criteriaControls = new GenericReportControlViewVector();
   String errorMess = "No error";
   try {
     con = getConnection();
     DBCriteria dbc = new DBCriteria();
     if(pCategory!=null) {
        dbc.addEqualToIgnoreCase(GenericReportDataAccess.CATEGORY,pCategory);
     }
     dbc.addEqualTo(GenericReportDataAccess.NAME,pName);
     IdVector reportIdV = GenericReportDataAccess.selectIdOnly(con,dbc);
     int count = reportIdV.size();
     if(count>1 && pCategory==null) {
       pCategory = "CUSTOMER";
       dbc.addEqualToIgnoreCase(GenericReportDataAccess.CATEGORY,pCategory);
       reportIdV = GenericReportDataAccess.selectIdOnly(con,dbc);
     }
     if(count==0) {
       String mess = "No generic report found.";
       if(pCategory!=null) {
         mess +=  " Category: "+pCategory;
       }
       mess += " Report Name: "+pName;
       throw new Exception(mess);
     }
     if(count>1) {
       throw new Exception(count+" generic reports found. Category: "+pCategory+" Report Name: "+pName);
     }
     Integer reportIdI = (Integer) reportIdV.get(0);
     criteriaControls = getGenericReportControls(con, reportIdI.intValue());
   } catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportControls() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     closeConnection(con);
   }
  return criteriaControls;
  }


   /**
   * Gets list of generic report criteria control names
   * @param pGenericReportId report id.
   * @return collection of  GenericReportControlView objects
   * @throws   RemoteException Required by EJB 1.0
   */
   public GenericReportControlViewVector getGenericReportControls(int pGenericReportId)
   throws RemoteException
   {
   Connection con = null;
   GenericReportControlViewVector criteriaControls = new GenericReportControlViewVector();
   String errorMess = "No error";
   try {
     con = getConnection();
     criteriaControls = getGenericReportControls(con, pGenericReportId);
   } catch (Exception exc) {
     errorMess = "Error. ReportBean.getGenericReportControls() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     closeConnection(con);
   }
  return criteriaControls;
  }


   private GenericReportControlViewVector getGenericReportControls(Connection pCon, int pGenericReportId)
   throws Exception {
     GenericReportControlViewVector criteriaControls = new GenericReportControlViewVector();
     DBCriteria dbc = new DBCriteria();
     GenericReportData report = GenericReportDataAccess.select(pCon,pGenericReportId);
     String repTxtCl = report.getSqlText();
     String repTxt;

     if(Utility.isSet(repTxtCl)){
        repTxt = repTxtCl.substring(1,((int) repTxtCl.length()));
     }else{
        repTxt = "";
     }
     if(report.getSupplementaryControls()!= null){
         repTxt = repTxt + report.getSupplementaryControls();
     }

     String repScriptCl = report.getScriptText();
     String repScript = (!Utility.isSet(repScriptCl))?null:repScriptCl.substring(1, ((int) repScriptCl.length()));
     String token = report.getParameterToken();

     if(repTxt==null || repTxt.trim().length()==0) {
         if(RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(report.getGenericReportType())){
            throw new Exception("No report sql found.  Report id: "+pGenericReportId);
         }else{
            return criteriaControls;
         }
     }
     criteriaControls = createGenericReportControls(criteriaControls, repTxt, token, pGenericReportId);
     if(repScript!=null && repScript.trim().length()>0) {
       criteriaControls = createGenericReportControls(criteriaControls, repScript, token,  pGenericReportId);
     }
     return criteriaControls;
   }




   private GenericReportControlViewVector createGenericReportControls(GenericReportControlViewVector pControls,
        String pText, String pToken, int pGenericReportId)
  throws Exception
  {
     String token = pToken;
     if(token==null || token.trim().length()==0) {
       token = "#";
     }
     if(pText==null || pText.trim().length()==0) {
       throw new Exception("No report sql found. Report id: "+pGenericReportId);
     }
     //Prepare controls
     int ind = pText.indexOf(token);
     while (ind >=0) {
       int ind1 = pText.indexOf(token,ind+1);
       if(ind1<=ind) {
         throw new Exception("No closing token found. Report id: "+pGenericReportId+" Token: "+token);
       }
       String control = pText.substring(ind+1,ind1);
       GenericReportControlView grc = parseGenericReportControl(control);
       if(grc!=null) {
         String src = grc.getSrcString();
         String label = grc.getLabel();
         if(label==null) label = "";
         int ii=0;
           for(; ii<pControls.size(); ii++) {
            GenericReportControlView grc1 = (GenericReportControlView) pControls.get(ii);
            if(grc1.getSrcString().equals(src)) {
              break;
            }
         }
         if(ii==pControls.size()) {
           pControls.add(grc);
         }
       }
       ind = pText.indexOf(token,ind1+1);
     }
     return pControls;
  }

  private GenericReportControlView parseGenericReportControl(String pControl) throws Exception {
    GenericReportControlView grc = GenericReportControlView.createValue();
    grc.setSrcString(pControl);
   int posType = pControl.indexOf('(');
    if(posType<0) {
      grc.setName(pControl.trim());
    } else{
      grc.setName(pControl.substring(0,posType).trim());
      String params = pControl.substring(posType+1);
      int posEnd = params.lastIndexOf(')');

	  if(posEnd>0) {
	      params = params.substring(0,posEnd);
	  }
	  String[] paramsA = Utility.parseStringToArray(params,",");
	  if(paramsA.length>0) grc.setType(paramsA[0].trim());
	  if(paramsA.length>1) grc.setMandatoryFl(paramsA[1].trim());
	  if(paramsA.length>2) grc.setLabel(paramsA[2].trim());
	  if(paramsA.length>3) grc.setPriority(paramsA[3].trim());
	  if(paramsA.length>4) grc.setDefault(paramsA[4].trim());
      if(paramsA.length>5) grc.setAdditionalLabel1(paramsA[5].trim());
      if(paramsA.length>6) grc.setAdditionalLabel2(paramsA[6].trim());
      if(paramsA.length>7) grc.setAdditionalLabel3(paramsA[7].trim());
      if(paramsA.length>8) {
    	  grc.setInvisible(Utility.isTrue(paramsA[8].trim()));
    	  if (grc.getInvisible()){
    		  grc.setValue(grc.getDefault());
    	  }
    		  
      }

    }
    return grc;
  }

    /**
     * Gets list of generic report criteria control names
     *
     * @param pCategory report category
     * @param pName     report name.
     * @param pParams   report parameters
     * @return report table
     * @throws RemoteException Required by EJB 1.0
     */
    public GenericReportResultView processGenericReport(String pCategory,
                                                        String pName,
                                                        Map pParams) throws RemoteException {

        DBConnections dbcon = null;
        String errorMess;
        GenericReportResultView result;

        try {
            dbcon = new DBConnections();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(GenericReportDataAccess.CATEGORY, pCategory);
            dbc.addEqualTo(GenericReportDataAccess.NAME, pName);

            GenericReportDataVector reports = GenericReportDataAccess.select(dbcon.mainCon, dbc);

            int count = reports.size();
            if (count == 0) {
                throw new Exception("No generic report found. Category: " + pCategory + " Report Name: " + pName);
            }

            if (count > 1) {
                throw new Exception(count + " generic reports found. Category: " + pCategory + " Report Name: " + pName);
            }

            GenericReportData report = (GenericReportData) reports.get(0);
            String schemaCds = report.getReportSchemaCd();
            dbcon.mkCons(schemaCds);

            String reportType = report.getGenericReportType();

            if (!dbcon.usingExistingCon) {
                dbcon.mainCon.close();
                dbcon.mainCon = null;
            }

            if (RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(reportType)) {
                result = processSqlGenericReport(dbcon, report, pParams);
            } else if (RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL.equals(reportType)) {
                GenericReportResultViewVector v = processSqlGenericReportMulti(dbcon, report, pParams);
                result = (GenericReportResultView) v.get(0);
            } else if (RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS.equals(reportType)) {
                result = processJavaGenericReport(dbcon, report, pParams);
            } else {
                throw new RemoteException("report type is not valid: " + reportType);
            }

            setupDefaultValues(report, result);

        } catch (Exception exc) {
            errorMess = "Error. ReportBean.processGenericReport() Exception happened. " + exc.getMessage();
            exc.printStackTrace();
            logError(errorMess);
            throw new RemoteException(errorMess);
        } finally {
            if (null != dbcon) {
                dbcon.closeAll();
            }
        }

        return result;
    }

   /**
   * Gets list of generic report criteria control names
   *
   * @param pCategory report category
   * @param pName report name.
   * @param pParams report parameters
   * @return vector of report tables
   * @throws   RemoteException Required by EJB 1.0
   */
    public GenericReportResultViewVector processGenericReportMulti(String pCategory, String pName, Map pParams)
    throws RemoteException{
    	
    	try {
	    	GenericReportResultViewVector results = processGenericReportMulti(pCategory,pName,pParams,null);
	    	return results;
    	}catch (Exception exc) {
    		String errorMess = "Error. ReportBean.processGenericReportMulti() Exception happened. "+exc.getMessage();
    		exc.printStackTrace();
    		logError(errorMess);
    		throw new RemoteException(errorMess);
    	}
    }
    
   public GenericReportResultViewVector processGenericReportMulti(String pCategory, String pName, Map pParams, String pDbName)
   throws RemoteException
   {
	   log.info("processGenericReportMulti BEGIN: pParams = " + pParams
			   );
   GenericReportResultViewVector results = new GenericReportResultViewVector();
       DBConnections dbcon = null;
   String errorMess = "No error" , lastReport = "";
   try {
	   dbcon = new DBConnections();

     DBCriteria dbc = new DBCriteria();
     String category = pCategory;
     if(category!=null) {
       dbc.addEqualToIgnoreCase(GenericReportDataAccess.CATEGORY,category);
     }
     if(pDbName!=null){
    	 dbc.addEqualTo(GenericReportDataAccess.DB_NAME, pDbName);
     }else{
    	 dbc.addEqualTo(GenericReportDataAccess.NAME,pName);
     }
     GenericReportDataVector reports = GenericReportDataAccess.select
	 (dbcon.mainCon,dbc);
     int count = reports.size();
     if(category==null && count>1) {
       category = "CUSTOMER";
       dbc.addEqualToIgnoreCase(GenericReportDataAccess.CATEGORY,category);
       reports = GenericReportDataAccess.select(dbcon.mainCon,dbc);
     }
     if(count==0) {
       String mess = "No generic report found. ";
       if(category!=null) mess += "Category: ["+category+"] ";
       mess +=  "Report Name: ["+pName+"]";
       throw new Exception(mess);
     }
     if(count>1) {
       String mess = count+" generic reports found. ";
       if(category!=null) mess += "Category: ["+category+"] ";
       mess +=  "Report Name: ["+pName+"]";
       throw new Exception(mess);
     }
     GenericReportData report = (GenericReportData) reports.get(0);
     lastReport += " lastReport=" + report;
     String schemaCds = report.getReportSchemaCd();
     dbcon.mkCons(schemaCds);

     String reportType = report.getGenericReportType();
     if(!dbcon.usingExistingCon){
        dbcon.mainCon.close();
        dbcon.mainCon = null;
     }

     logDebug(" ReportBean.processGenericReportMulti reportType="
	      + reportType );

     log.info("******************SVC: ReportBean.processGenericReportMulti reportType="
   	      + reportType );

     if(RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(reportType)){
    	 log.info("******************SVC: processSqlGenericReport");
         results.add(processSqlGenericReport(dbcon, report, pParams));
     } else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL.equals(reportType)) {
    	 log.info("******************SVC: processSqlGenericReportMulti");
	     results.addAll(processSqlGenericReportMulti(dbcon, report, pParams));
     }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS.equals(reportType)){
    	 log.info("******************SVC: processJavaGenericReport");
         results.add(processJavaGenericReport(dbcon, report, pParams));
     }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS_MULTI.equals(reportType)){
    	 log.info("******************SVC: processJavaGenericReportMulti");
         results.addAll(processJavaGenericReportMulti(dbcon, report, pParams));
     }else{
         throw new RemoteException("report type is not valid: "+reportType);
     }

    if (!results.isEmpty()) {
        Iterator it = results.iterator();
        while (it.hasNext()) {
            setupDefaultValues(report, (GenericReportResultView) it.next());
        }
    }

     return results;
   }catch (Exception exc) {
     errorMess = "Error. ReportBean.processGenericReportMulti() Exception happened. "+exc.getMessage() + " WAS RUNNING " + lastReport;
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
       if ( null != dbcon ) {
	   dbcon.closeAll();
       }
   }
  }


   public Serializable checkAnalyticReport(int pGenericReportId,
            Map pParams, int pUserId, String pUserName,
            boolean pDeletePrevResultFl) throws RemoteException {
        return checkAnalyticReport(pGenericReportId, 0, pParams, pUserId,
                pUserName, pDeletePrevResultFl);
    }
   /**
     * Runs report
     *
     * @param pGenericReportId
     *            report id
     * @param pParams
     *            report parameters
     * @param pUserId
     *            user requested the report
     * @param pDeletePrevResultFl
     *            indicator to delete previous versions of the report
     * @return ReportResultData object
     * @throws RemoteException
     *             Required by EJB 1.0
     */
   public ReportResultData processAnalyticReport(
          int pGenericReportId,
          Map pParams,
          int pUserId,
          String pUserName,
          boolean pDeletePrevResultFl)
   throws RemoteException
   {
     return processAnalyticReport
          (pGenericReportId, 0, pParams, pUserId, pUserName,pDeletePrevResultFl);
   }


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
   public ReportResultData processAnalyticReport(int pGenericReportId,
            int pReportScheduleId, Map pParams, int pUserId, String pUserName,
            boolean pDeletePrevResultFl) throws RemoteException {
       return (ReportResultData) checkAnalyticReport(pGenericReportId,
                pReportScheduleId, pParams, pUserId, pUserName,
                pDeletePrevResultFl);
    }

   private Serializable checkAnalyticReport(int pGenericReportId,
            int pReportScheduleId, Map pParams, int pUserId, String pUserName,
            boolean pDeletePrevResultFl) throws RemoteException {
       GenericReportResultViewVector results = new GenericReportResultViewVector();
       String errorMess = "No error";
       ReportResultData reportResult = null;
       int reportResultId = 0;
       DBConnections dbcon = null;

       try {
	   boolean fAnalyticToo = true;
	   dbcon = new DBConnections("", fAnalyticToo);
	   GenericReportData report = GenericReportDataAccess.select
	       (dbcon.mainCon,pGenericReportId);

	   String reportType = report.getGenericReportType();
	   String schemaCds = report.getReportSchemaCd();
	   dbcon.mkCons(schemaCds);

	   if(!dbcon.usingExistingCon){
	       dbcon.mainCon.close();
	       dbcon.mainCon = null;
	   }
	   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	   Date time1 = new Date();
	   log.info("Start report "+report.getName()+". Time: "+sdf.format(time1));

	   if(
	      RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(reportType) ) {
	       results.add(processSqlGenericReport
			   (dbcon, report, pParams));
	   } else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL.equals(reportType)) {
	       results.addAll(processSqlGenericReportMulti
			      (dbcon, report, pParams));
	   }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS.equals(reportType)){
	       results.add(processJavaGenericReport(dbcon, report, pParams));
       }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS_2.equals(reportType)){
           ReportItem root = processJava2GenericReport(dbcon, report, pParams);
           return root;
	   }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS_MULTI.equals(reportType)){
               GenericReportResultViewVector res =  processJavaGenericReportMulti(dbcon, report, pParams);
               Object worker = getInstanceReport(report);
               if (worker instanceof ReportInterf && ((ReportInterf)worker).isSpecial()){
                 return res;
               }
	       results.addAll(res);
	   }else{
	       throw new RemoteException("report type is not valid: "+reportType);
	   }

      //==== Stop the report processing if page row count greater then 65000=======
           boolean error = ReportingUtils.verifyReportPageSize( results);
           if (error){
             throw new RemoteException("^clwKey^report.text.exceedMaxReportRows^clwKey^");
           }
      //======================================================================


       if (!results.isEmpty()) {
           Iterator it = results.iterator();
           while (it.hasNext()) {
               setupDefaultValues(report, (GenericReportResultView) it.next());
           }
       }

       Date time2 = new Date();
	   double dur = (double) (time2.getTime()-time1.getTime());
	   dur /= 1000;
	   log.info("Report "+report.getName()+" generated. Time: "+sdf.format(time2)+" Duration: "+dur+" sec." );


	   //Save report results
	   //ReportResult
	   reportResult = ReportResultData.createValue();
	   reportResult.setGenericReportId(pGenericReportId);
	   reportResult.setReportScheduleId(pReportScheduleId);
	   reportResult.setUserId(pUserId);
	   reportResult.setAddBy(pUserName);
	   reportResult.setReportName(report.getName());
	   reportResult.setReportCategory(report.getCategory());
	   reportResult.setReportResultStatusCd(RefCodeNames.REPORT_RESULT_STATUS_CD.GENERATED);
	   reportResult = ReportResultDataAccess.insert
	       (dbcon.analyticCon, reportResult);
	   reportResultId = reportResult.getReportResultId();

	   //ReportResultParam
	   Set paramSet = pParams.entrySet();
	   Iterator paramIter = paramSet.iterator();
	   while(paramIter.hasNext()) {
	       Map.Entry entry = (Map.Entry) paramIter.next();
	       String paramName = (String) entry.getKey();
	       String paramValue = null;
               if (entry.getValue() instanceof String ){
                 paramValue = (String) entry.getValue();
                 paramValue = (paramValue.length()>=2000) ? "Can't be saved. Parameter value is too long" : paramValue;
                 if (!Utility.isSet(paramValue))
                   continue;
                 ReportResultParamData reportResultParamD =  ReportResultParamData.createValue();
                 reportResultParamD.setReportResultId(reportResultId);
                 int brInd = paramName.indexOf('(');
                 if (brInd > 0)
                   paramName = paramName.substring(0, brInd);
                 reportResultParamD.setParamName(paramName);
                 reportResultParamD.setParamValue(paramValue);
                 ReportResultParamDataAccess.insert
                     (dbcon.analyticCon, reportResultParamD);
               }
	   }

	   //ResultLines
	   for(int ii=0; ii<results.size(); ii++) {
	       GenericReportResultView repRes = (GenericReportResultView) results.get(ii);
	       saveReportResultPage(dbcon.analyticCon, reportResultId, repRes);

	   }
	   Date time3 = new Date();
	   dur = (double) (time3.getTime()-time2.getTime());
	   dur /= 1000;
	   log.info("Report "+report.getName()+" saved. Time: "+sdf.format(time3)+" Duration: "+dur+" sec." );
	   //Remove old version of the report
	   if(pDeletePrevResultFl) {
	       DBCriteria dbc = new DBCriteria();
	       dbc.addNotEqualTo(ReportResultDataAccess.REPORT_RESULT_ID,reportResultId);
	       if(pReportScheduleId > 0) {
		   dbc.addEqualTo(ReportResultDataAccess.REPORT_SCHEDULE_ID,pReportScheduleId);
	       } else {
		   dbc.addEqualTo(ReportResultDataAccess.GENERIC_REPORT_ID,pGenericReportId);
		   dbc.addEqualTo(ReportResultDataAccess.USER_ID,pUserId);
	       }
	       IdVector rrIds = ReportResultDataAccess.
		   selectIdOnly(dbcon.analyticCon,ReportResultDataAccess.REPORT_RESULT_ID,dbc);
	       deletePreparedReports(rrIds, pUserName);
	       Date time4 = new Date();
	       dur = (double) (time4.getTime()-time3.getTime());
	       dur /= 1000;
	       log.info("Report "+report.getName()+" old version deleted. Time: "+sdf.format(time4)+" Duration: "+dur+" sec." );
	   }
	   Date time5 = new Date();
	   dur = (double) (time5.getTime()-time1.getTime());
	   dur /= 1000;
	   log.info("Report "+report.getName()+" done. Time: "+sdf.format(time5)+" Total duration: "+dur+" sec." );
	   return reportResult;
       }catch (Exception exc) {
	   errorMess = "Error. ReportBean.processGenericReportMulti() Exception happened. "+exc.getMessage();
	   exc.printStackTrace();
	   logError(errorMess);
	   throw new RemoteException(errorMess);
       }finally {
	   if ( null != dbcon ) {
	       dbcon.closeAll();
	   }
       }
   }


  private void saveReportResultPage(Connection pAnalyticCon, int pReportResultId, GenericReportResultView pPage)
  throws Exception
  {
     byte[] pageB = objectToBytes(pPage);
     String sheetName = pPage.getName();
     sheetName = sheetName.replaceAll("'", "''");
     log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR pageB.length: "+pageB.length);


     //Create record for blob (Oracle) or bytea (Postgres)
     Statement stmt = pAnalyticCon.createStatement();
     ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_RESULT_LINE_SEQ.NEXTVAL FROM DUAL");
     rs.next();
     int keyId = rs.getInt(1);
     stmt.close();
     if (ORACLE.equals(databaseName)) {
        String insertSql =
        "insert into RPT_REPORT_RESULT_LINE ( "+
        " REPORT_RESULT_LINE_ID, "+
        " REPORT_RESULT_ID, "+
        " PAGE_NAME, "+
        " REPORT_RESULT_LINE_CD, "+
        " LINE_VALUE_BLOB ) values ("+
        "  ?,?,?,?," +
       /* keyId +", "+
        pReportResultId +", "+
        "'"+sheetName+"', "+
        "'"+RefCodeNames.REPORT_RESULT_LINE_CD.REPORT_LINE+"', "+*/
        " empty_blob())";

        PreparedStatement pstmt = pAnalyticCon.prepareStatement(insertSql);
        pstmt.setInt(1, keyId);
        pstmt.setInt(2, pReportResultId);
        pstmt.setString(3, sheetName);
        pstmt.setString(4, RefCodeNames.REPORT_RESULT_LINE_CD.REPORT_LINE);
        pstmt.executeUpdate();
        pstmt.close();

        //Get Blob
        Statement stmn=pAnalyticCon.createStatement();
        String sql =
          "select LINE_VALUE_BLOB "+
          " from RPT_REPORT_RESULT_LINE "+
          " where REPORT_RESULT_LINE_ID = "+keyId+
          " for update";

        ResultSet rs1=stmn.executeQuery(sql);
        rs1.next();
        BLOB blob = (BLOB) rs1.getBlob(1);
        int len = blob.putBytes(1L,pageB);
        log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR bytes written: "+len);
        rs1.close();
        stmn.close();
        //
        String updateBlobSql =
        "update RPT_REPORT_RESULT_LINE set LINE_VALUE_BLOB=? WHERE REPORT_RESULT_LINE_ID = "+keyId;

        PreparedStatement pstmt1 = pAnalyticCon.prepareStatement(updateBlobSql);
        pstmt1.setBlob(1, blob);
        pstmt1.executeUpdate();
        pstmt1.close();
     } else { //Postgres DB (Enterprise DB)
         String insertSql =
             "insert into RPT_REPORT_RESULT_LINE ( "+
             " REPORT_RESULT_LINE_ID, "+
             " REPORT_RESULT_ID, "+
             " PAGE_NAME, "+
             " REPORT_RESULT_LINE_CD, "+
             " LINE_VALUE_BLOB ) values ("+
             "  ?,?,?,?," +
            /* keyId +", "+
             pReportResultId +", "+
             "'"+sheetName+"', "+
             "'"+RefCodeNames.REPORT_RESULT_LINE_CD.REPORT_LINE+"', "+*/
             " null)";

             PreparedStatement pstmt = pAnalyticCon.prepareStatement(insertSql);
             pstmt.setInt(1, keyId);
             pstmt.setInt(2, pReportResultId);
             pstmt.setString(3, sheetName);
             pstmt.setString(4, RefCodeNames.REPORT_RESULT_LINE_CD.REPORT_LINE);
             pstmt.executeUpdate();
             pstmt.close();

             //Get Bytea type data (Postgres)
             Statement stmn=pAnalyticCon.createStatement();
             String sql =
               "select LINE_VALUE_BLOB "+
               " from RPT_REPORT_RESULT_LINE "+
               " where REPORT_RESULT_LINE_ID = "+keyId+
               " for update";

             ResultSet rs1=stmn.executeQuery(sql);
             rs1.next();
             
             byte b[] = rs1.getBytes(1); //probably I do not need this statement at all
             log.info("saveReportResultPage(): lineValueBlob = " + b);          
             rs1.close();
             stmn.close();
             //
             String updateBlobSql =
             "update RPT_REPORT_RESULT_LINE set LINE_VALUE_BLOB=? WHERE REPORT_RESULT_LINE_ID = "+keyId;

             PreparedStatement pstmt1 = pAnalyticCon.prepareStatement(updateBlobSql);
             pstmt1.setBytes(1, pageB);
             pstmt1.executeUpdate();
             pstmt1.close(); 
     }
  }

 private byte[] objectToBytes(Object pObj)
  throws java.io.IOException,ClassNotFoundException
  {
    java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
    java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
    os.writeObject(pObj);
    os.flush();
    os.close();
    byte[] byteImage = oStream.toByteArray();
    return byteImage;

  }

 /*
  private String objectToString(Object pObj)
  throws java.io.IOException,ClassNotFoundException
  {
    java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
    java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
    os.writeObject(pObj);
    os.flush();
    os.close();
    byte[] byteImage = oStream.toByteArray();
    char[] charImage = new char[byteImage.length];
    for(int ii=0; ii<byteImage.length; ii++) {
      charImage[ii] = (char) byteImage[ii];
    }
    oStream.close();
    String objStr = new String(charImage);
    return objStr;

  }
  */
   private GenericReportResultView processJavaGenericReport
       (DBConnections dbcon,GenericReportData pReportData,Map pParams)
  throws Exception
  {
      String className = pReportData.getClassname();
      GenericReport worker = null;
      GenericReportResultView ret = null;
      try{
         Class clazz = Class.forName(className);
         worker = (GenericReport) clazz.newInstance();
         ConnectionContainer conns = new ConnectionContainer
             (dbcon.lawCon,dbcon.reportCon,dbcon.mainCon, dbcon.stagedInfoCon);
         ret = worker.process(conns, pReportData, pParams);
      }catch(ClassNotFoundException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" could not be found.");
      }catch(ClassCastException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" must implement GenericReport.");
      } catch (Exception e) {
    	  log.error(e.getMessage(), e);
          throw new RemoteException(e.getMessage(),e);
      }
      if(ret == null){
          logInfo("Java worket object returned null.");
          ret = GenericReportResultView.createValue();
      }

      return ret;
  }

    private void setupDefaultValues(GenericReportData report, GenericReportResultView ret) throws Exception {

    	//report is a raw output, in other words it has generated the end results
    	//and should not be setup
    	if(ret.getRawOutput() != null && ret.getRawOutput().length > 0){
    		return;
    	}
        if (!Utility.isSet(ret.getPaperOrientation())) {
            ret.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        }

        if (!Utility.isSet(ret.getPaperSize())) {
            ret.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        }

        GenericReportColumnViewVector header = ret.getHeader();

        int headerWidth = 0;
        int tableWidth = Utility.parseInt(ret.getWidth());
        if (header != null ){
          ArrayList<Integer> cWidthInt = new ArrayList<Integer>(header.size());
          for (int i = 0; i < header.size(); i++) {
            GenericReportColumnView column = (GenericReportColumnView) header.get(i);
            cWidthInt.add(Utility.parseInt(column.getColumnWidth()));
            headerWidth += cWidthInt.get(i);
          }

          for (int i = 0; i < ret.getColumnCount(); i++) {
            GenericReportColumnView column = (GenericReportColumnView) header.get(i);
            if (headerWidth > 0 && tableWidth > 0 && cWidthInt.get(i) > 0) {
                int width = (int) ((tableWidth * ((double) cWidthInt.get(i))) / (double) headerWidth);
                column.setColumnWidth(String.valueOf(width));
            } else if (headerWidth == 0 && cWidthInt.get(i) > 0 && tableWidth > 0) {
                throw new Exception("Column width required. Report => '" + report.getName() + "', Sheet => '" + ret.getName() + "'");
            } else {
                column.setColumnWidth(String.valueOf(ReportingUtils.DEFAULT_COLUMN_WIDTH));
            }
           }
          }
    }

    private ReportItem processJava2GenericReport(
            DBConnections dbcon, GenericReportData pReportData, Map pParams)
            throws Exception {
        String className = pReportData.getClassname();
        DomUniversalReport worker = null;
        ReportItem ret = null;
        try{
           Class clazz = Class.forName(className);
           worker = (DomUniversalReport) clazz.newInstance();
           ConnectionContainer conns = new ConnectionContainer(dbcon.lawCon,
                   dbcon.reportCon, dbcon.mainCon, dbcon.stagedInfoCon);
           ret = worker.process(conns, pReportData, pParams);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Configured class: " + className
                    + " for report category: " + pReportData.getName()
                    + " and report name: " + pReportData.getName()
                    + " could not be found.");
        } catch (ClassCastException e) {
            throw new RemoteException("Configured class: " + className
                    + " for report category: " + pReportData.getName()
                    + " and report name: " + pReportData.getName()
                    + " must implement DomUniversalReport.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        if (ret == null) {
            logInfo("Java worket object returned null.");
            ret = ReportItem.createValue("EMPTY");
        }
        return ret;
    }

   private GenericReportResultViewVector processJavaGenericReportMulti
      (DBConnections dbcon,GenericReportData pReportData,Map pParams)
  throws Exception
  {
      String className = pReportData.getClassname();
      GenericReportMulti worker = null;
      GenericReportResultViewVector ret = null;
      try{
         Class clazz = Class.forName(className);
         worker = (GenericReportMulti) clazz.newInstance();

         ConnectionContainer conns = new ConnectionContainer
             (dbcon.lawCon,dbcon.reportCon,dbcon.mainCon, dbcon.stagedInfoCon);
         ret = worker.process(conns, pReportData, pParams);
      }catch(ClassNotFoundException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" could not be found.");
      }catch(ClassCastException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" must implement GenericReport.");
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException(e.getMessage());
      }
      if(ret == null){
          logInfo("Java worker object returned null.");
          ret = new GenericReportResultViewVector();
      }

      return ret;
  }

  private GenericReportResultViewVector processSqlGenericReportMulti
      (DBConnections dbcon,
       GenericReportData report,Map pParams)
      throws Exception {

        GenericReportResultViewVector res =
            new GenericReportResultViewVector();

        // xsql
        //String repTxtCl = report.getSqlText();
        String repTxt = report.getSqlText(); //repTxtCl.substring(1,((int) repTxtCl.length()));
        String token = report.getParameterToken();
        String resultTable = report.getInterfaceTable();
        String name = report.getName();
        String category = report.getCategory();

        if(repTxt==null || repTxt.trim().length()==0) {
            throw new Exception("No report sql found 2. Category: "
                                +category+" Report Name: "+name);
        }

        //Pick sql segments
        XSQLParser xsql = new XSQLParser(repTxt);
        while (xsql.next()) {
            repTxt = xsql.getSql();
            logDebug( "xsql.getName()=" + xsql.getName()
                      + " repTxt=" + repTxt );
            log.info( "***************SVC: xsql.getName()=" + xsql.getName()
                    + " repTxt=" + repTxt );

            String repSql = createGenericReportSql(repTxt,
                                                   token,
                                                   pParams,
                                                   category,
                                                   name,
                                                   report.getReportSchemaCd());

            Statement stmt = null;
	    Connection activec = dbcon.getActiveConnection();
	    stmt = activec.createStatement();

            ResultSet rs = null;
            if(resultTable!=null && resultTable.trim().length()>0) {
                String truncateSql =  "truncate table "+resultTable;
                stmt.execute(truncateSql); //Truncate interface table
            }
            log.info("ReportBean 200 xsql: "+repSql);
            rs = stmt.executeQuery(repSql);  //Execute script
            GenericReportResultView result =
                GenericReportResultView.createValue();
            Utility.parseResultSetDataForReport(rs, result);
            result.setName(xsql.getName());
            result.setPaperSize(xsql.getPaperSize());
            result.setPaperOrientation(xsql.getPaperOrientation());
            rs.close();
            stmt.close();
            res.add(result);
        }
        // xsql

        return res;
    }


  private GenericReportResultView processSqlGenericReport
      (DBConnections dbcon,GenericReportData report,Map pParams)
  throws Exception
  {
      int conCt = dbcon.getConnectionCnt();
      if(conCt > 1){
        throw new RemoteException("For SQL Report only one schema code may be specified");
      }
      Connection con = dbcon.getActiveConnection();
      GenericReportResultView result = GenericReportResultView.createValue();
      //String repTxtCl = report.getSqlText();
      //     logDebug(" --- 53 repTxtCl="+repTxtCl);
     //String repTxt = repTxtCl.substring(1,((int) repTxtCl.length()));
     String repTxt = report.getSqlText();
           logDebug(" --- 53 repTxt="+repTxt);
     //String repScriptCl = report.getScriptText();
     String repScript = report.getScriptText();// (!Utility.isSet(repScriptCl))?null:repScriptCl.substring(1, ((int) repScriptCl.length()));
	 if(!Utility.isSet(repScript)) repScript = null;
     String token = report.getParameterToken();
     String resultTable = report.getInterfaceTable();
     String name = report.getName();
     String category = report.getCategory();

      if(repTxt==null || repTxt.trim().length()==0) {
       throw new Exception("No report sql found. Category: "+category+" Report Name: "+name);
     }
     //Prepare sql
     String repSql = createGenericReportSql(repTxt, token, pParams,category, name, report.getReportSchemaCd());
     String scriptSql = null;
     if(repScript!=null && repScript.trim().length()>0) {
        scriptSql = createGenericReportSql(repScript, token, pParams,category, name,report.getReportSchemaCd());
     }
    //Exequte report
     Statement stmt = con.createStatement();
     ResultSet rs = null;
     if(resultTable!=null && resultTable.trim().length()>0) {
       String truncateSql =  "truncate table "+resultTable;
       stmt.execute(truncateSql); //Truncate interface table
     }
     if(scriptSql!=null && scriptSql.trim().length()>0) {
       stmt.execute(scriptSql);  //Execute script
     }
     log.info("ReportBean 100 sql: "+repSql);
     rs = stmt.executeQuery(repSql);  //Execute script
     Utility.parseResultSetDataForReport(rs, result);
     rs.close();
     stmt.close();

     if (result.getColumnCount() > 8) {
        result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
     }
     return result;
  }


  private String createGenericReportSql(String pText, String pToken,
                                          Map pParams, String pCategory,
                                          String pName, String pReportSchemaCode)
  throws Exception
  {
     log.info("*************SVC: createGenericReportSql method: pReportSchemaCode = " + pReportSchemaCode);
     String token = pToken;
     if(token==null || token.trim().length()==0) {
       token = "#";
     }
     token = token.trim();

     int tokenLen = token.length();
     //Prepare sql
     String repSql = "";
     pText = ReportingUtils.replaceAccessToSitesSQL(pText,pToken,pReportSchemaCode);
     log.info("*************SVC: createGenericReportSql method: pText = " + pText);
     int ind = pText.indexOf(token);
     int ind1 = -tokenLen;

     log.info("*************SVC: createGenericReportSql method: pParams = " + pParams);

     while (ind >=0) {
       repSql += pText.substring(ind1+tokenLen, ind);
       ind1 = pText.indexOf(token,ind+1);
       if(ind1<=ind) {
         throw new Exception("No closing token found. Category: "+pCategory+" Report Name: "+pName+" Token: "+token);
       }
       String paramName = pText.substring(ind+1,ind1);
       log.info("*************SVC: createGenericReportSql method: paramName = " + paramName);
       if(pParams.containsKey(paramName)) {
         String paramValue = (String) pParams.get(paramName);
         repSql += paramValue;
       } else {
         throw new Exception("No parameter found. Category: "+pCategory+" Report Name: "+pName+" Parameter Name: "+paramName);
       }
       ind = pText.indexOf(token,ind1+1);
     }
     repSql += pText.substring(ind1+tokenLen);
     //Replace special characters with space
     char[] wrkSql = repSql.toCharArray();
     for(int ii=0; ii<wrkSql.length; ii++) {
        if(wrkSql[ii]<' ') wrkSql[ii]=' ';
     }
     repSql = new String(wrkSql);
     return repSql;
  }

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
   throws RemoteException
   {
   GenericReportResultViewVector results = new GenericReportResultViewVector();
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   ReportResultData reportResult = null;
   int reportResultId = 0;
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     GenericReportData report = GenericReportDataAccess.select(mainCon,pGenericReportId);
     String schemaCds = report.getReportSchemaCd();
     String reportType = report.getGenericReportType();
     StringTokenizer schemaCdTok = null;

     //ReportResult
     reportResult = ReportResultData.createValue();
     reportResult.setGenericReportId(pGenericReportId);
     reportResult.setReportScheduleId(pReportScheduleId);
     reportResult.setReportResultStatusCd(RefCodeNames.REPORT_RESULT_STATUS_CD.FAILED);
     reportResult.setUserId(pUserId);
     reportResult.setAddBy(pUserName);
     reportResult.setReportName(report.getName());
     reportResult.setReportCategory(report.getCategory());
     reportResult.setReportResultStatusCd(RefCodeNames.REPORT_RESULT_STATUS_CD.FAILED);
     reportResult = ReportResultDataAccess.insert(analyticCon, reportResult);
     reportResultId = reportResult.getReportResultId();

     //ReportResultParam
     Set paramSet = pParams.entrySet();
     Iterator paramIter = paramSet.iterator();
     while(paramIter.hasNext()) {
       Map.Entry entry = (Map.Entry) paramIter.next();
       String paramName = (String) entry.getKey();
       String paramValue = null;
       if (entry.getValue() != null)
    	   paramValue = entry.getValue().toString();
       if(!Utility.isSet(paramValue)) continue;
       if (paramValue.length()>2000)
    	   paramValue = (paramValue.length()>=2000) ? "Can't be saved. Parameter value is too long" : paramValue;
       
       ReportResultParamData reportResultParamD = ReportResultParamData.createValue();
       reportResultParamD.setReportResultId(reportResultId);
       reportResultParamD.setParamName(paramName);
       reportResultParamD.setParamValue(paramValue);
       ReportResultParamDataAccess.insert(analyticCon,reportResultParamD);
     }

     //Error message
     GenericReportResultView result = GenericReportResultView.createValue();
     result.setColumnCount(1);
     GenericReportColumnViewVector header = new GenericReportColumnViewVector();
     GenericReportColumnView column = GenericReportColumnView.createValue();
     column.setColumnClass("String");
     column.setColumnName("Error");
     column.setColumnType("");
     column.setColumnPrecision(0);
     column.setColumnScale(0);
     header.add(column);
     result.setHeader(header);

     ArrayList records = new ArrayList();
     if(pErrorMess!=null) {
       Iterator iter = pErrorMess.iterator();
       while(iter.hasNext()) {
         ArrayList record = new ArrayList();
         record.add(iter.next());
         records.add(record);
       }
    }
    result.setTable(records);
    results.add(result);

    for(int ii=0; ii<results.size(); ii++) {
       GenericReportResultView repRes = (GenericReportResultView) results.get(ii);
       saveReportResultPage(analyticCon, reportResultId, repRes);
    }
     return reportResult;
   }catch (Exception exc) {
     errorMess = "Error. Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }

   /**
   * Returns report result view
   *
   * @param pReportResultId
   * @return PreparedReportView obect
   * @throws   RemoteException Required by EJB 1.0
   * @throws   DataNotFoundException
   */
   public PreparedReportView getPreparedReport(int pReportResultId)
   throws RemoteException, DataNotFoundException
   {
   ReportResultDataVector reportResultDV = new ReportResultDataVector();
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     ReportResultData reportResultD =
                    ReportResultDataAccess.select(analyticCon,pReportResultId);
     PreparedReportView preparedReportVw = PreparedReportView.createValue();
     preparedReportVw.setReportResultId(pReportResultId);
     preparedReportVw.setGenericReportId(reportResultD.getGenericReportId());
     preparedReportVw.setReportCategory(reportResultD.getReportCategory());
     preparedReportVw.setReportName(reportResultD.getReportName());
     preparedReportVw.setReportDate(reportResultD.getModDate());
     preparedReportVw.setReportResultStatusCd(reportResultD.getReportResultStatusCd());
     preparedReportVw.setReadFl(false); //we do not know the user to determine the real value
     preparedReportVw.setProtectedFl(reportResultD.getProtectedFl());
     int userId = reportResultD.getUserId();
     preparedReportVw.setRequesterId(userId);

     DBCriteria dbc = new DBCriteria();
     boolean serviceUserFl = true;

     //Get parameters
     dbc = new DBCriteria();
     dbc.addEqualTo(ReportResultParamDataAccess.REPORT_RESULT_ID, pReportResultId);
     ReportResultParamDataVector reportResultParamDV =
                       ReportResultParamDataAccess.select(analyticCon,dbc);
     String params = "";
     for(int ii=0; ii<reportResultParamDV.size();ii++) {
       ReportResultParamData rrpD = (ReportResultParamData) reportResultParamDV.get(ii);
       String paramName = rrpD.getParamName();
       if("CUSTOMER".equals(paramName)) paramName = "USER";
       if(params.length()>0) params += ", ";
         params += paramName + "=";
         params += rrpD.getParamValue();
     }
     preparedReportVw.setReportParameters(params);

     //Get user
     try {
       UserData userD = UserDataAccess.select(mainCon,userId);
       preparedReportVw.setRequesterName(userD.getUserName());
     } catch(DataNotFoundException exc) {
        preparedReportVw.setRequesterName("");
     }
     return preparedReportVw;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }

   /**
   * Returns list of prepared reports for the user
   *
   * @param pFilters a set of filters (REPORT_CATEGORY,REPORT_NAME,MIN_DATE,MAX_DATE,THE_USER_REPORTS_ONLY)
   * @param pUserId user id
   * @return vector of PreparedReportView obects
   * @throws   RemoteException Required by EJB 1.0
   */
   public PreparedReportViewVector getAnalyticReportArchive(Map pFilters, int pUserId)
   throws RemoteException
   {
   PreparedReportViewVector reports = new PreparedReportViewVector();
   ReportResultDataVector reportResultDV = new ReportResultDataVector();
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     //Get Filters
     String categoryFilter = null;
     String nameFilter = null;
     Date minDateFilter = null;
     Date maxDateFilter = null;
     boolean allUserFl = true;
     if(pFilters!=null) {
       Set filterSet = pFilters.entrySet();
       Iterator filterIter = filterSet.iterator();
       while(filterIter.hasNext()) {
         Map.Entry filter = (Map.Entry) filterIter.next();
         String key = (String) filter.getKey();
         if("REPORT_CATEGORY".equals(key)) {
           try{
           categoryFilter = (String) filter.getValue();
           } catch (Exception exc) {
             exc.printStackTrace();
           }
         } else if("REPORT_NAME".equals(key)) {
           try{
           nameFilter = (String) filter.getValue();
           } catch (Exception exc) {
             exc.printStackTrace();
           }
         } else if("MIN_REPORT_DATE".equals(key)) {
           try{
           minDateFilter = (Date) filter.getValue();
           } catch (Exception exc) {
             exc.printStackTrace();
           }
         } else if("MAX_REPORT_DATE".equals(key)) {
           try {
           maxDateFilter = (Date) filter.getValue();
           } catch (Exception exc) {
             exc.printStackTrace();
           }
         } else if("THE_USER_REPORTS_ONLY".equals(key)) {
           try {
           String value = (String) filter.getValue();
           if("true".equalsIgnoreCase(value)) {
             allUserFl = false;
           }
           } catch (Exception exc) {
             exc.printStackTrace();
           }
         }

       }
     }



     UserData userD = UserDataAccess.select(mainCon,pUserId);
     String userType = userD.getUserTypeCd();
     boolean serviceUserFl = false;
       if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType))
       {
           serviceUserFl = true;
       }
       else {
           allUserFl = false;
       }

     //Customers or Cleanwise users requested only thier reports
     IdVector filterReportResultIdV = null;
     if(!allUserFl || !serviceUserFl){
       //Get user group
       DBCriteria dbc = new DBCriteria();
       dbc = new DBCriteria();
       dbc.addEqualTo(GroupAssocDataAccess.USER_ID,pUserId);
       dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                                    RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
       IdVector groupIdV =
       GroupAssocDataAccess.selectIdOnly(mainCon,GroupAssocDataAccess.GROUP_ID,dbc);
       //Get reports assigned to the groups
       dbc = new DBCriteria();
       dbc.addOneOf(ReportResultAssocDataAccess.ASSOC_REF_ID,groupIdV);
       dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                     RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP);
       filterReportResultIdV = ReportResultAssocDataAccess.selectIdOnly(analyticCon,
                               ReportResultAssocDataAccess.REPORT_RESULT_ID,dbc);
       //Get reports assigned to the user
       dbc = new DBCriteria();
       dbc.addEqualTo(ReportResultAssocDataAccess.ASSOC_REF_ID,pUserId);
       dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                     RefCodeNames.REPORT_RESULT_ASSOC_CD.USER);
      IdVector filterReportResultIdV1 = ReportResultAssocDataAccess.selectIdOnly(analyticCon,
                               ReportResultAssocDataAccess.REPORT_RESULT_ID,dbc);
      filterReportResultIdV.addAll(filterReportResultIdV1);
      //Add reports run by the user
       dbc = new DBCriteria();
       dbc.addEqualTo(ReportResultDataAccess.USER_ID,pUserId);
       IdVector filterReportResultIdV2 = ReportResultDataAccess.selectIdOnly(analyticCon,
                                    ReportResultDataAccess.REPORT_RESULT_ID,dbc);
       filterReportResultIdV.addAll(filterReportResultIdV2);
     }

     //Cleanwise users requested all reports
     IdVector genericReportIdV = null;
     if(serviceUserFl && allUserFl) {
       DBCriteria dbc = new DBCriteria();
       dbc.addEqualTo(GroupAssocDataAccess.USER_ID,pUserId);
       dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                                    RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
       IdVector groupIdV =
       GroupAssocDataAccess.selectIdOnly(mainCon,GroupAssocDataAccess.GROUP_ID,dbc);
       dbc = new DBCriteria();
       dbc.addEqualTo(GroupDataAccess.SHORT_DESC,"EVERYONE");
       IdVector everyoneIdV =
         GroupDataAccess.selectIdOnly(mainCon,GroupDataAccess.GROUP_ID,dbc);

       groupIdV.addAll(everyoneIdV);

       dbc=new DBCriteria();
       dbc.addEqualTo(GroupDataAccess.SHORT_DESC,userType);
       dbc.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,RefCodeNames.GROUP_TYPE_CD.USER);
       IdVector defaultIdV =
                 GroupDataAccess.selectIdOnly(mainCon, GroupAssocDataAccess.GROUP_ID, dbc);

       groupIdV.addAll(defaultIdV);

       dbc = new DBCriteria();
       dbc.addOneOf(GroupAssocDataAccess.GROUP_ID,groupIdV);
       dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                                 RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
       genericReportIdV =
            GroupAssocDataAccess.selectIdOnly(mainCon,GroupAssocDataAccess.GENERIC_REPORT_ID,dbc);
      }

     //Get report results now
     DBCriteria dbc = new DBCriteria();
     if(filterReportResultIdV!=null) {
       dbc.addOneOf(ReportResultDataAccess.REPORT_RESULT_ID,filterReportResultIdV);
     }
     if(genericReportIdV!=null) {
       dbc.addOneOf(ReportResultDataAccess.GENERIC_REPORT_ID,genericReportIdV);
     }

     if(categoryFilter!=null) {
        dbc.addContainsIgnoreCase(ReportResultDataAccess.REPORT_CATEGORY, categoryFilter);
     }
     if(nameFilter!=null) {
       dbc.addContainsIgnoreCase(ReportResultDataAccess.REPORT_NAME, nameFilter);
     }
     if(minDateFilter!=null) {
       dbc.addGreaterOrEqual(ReportResultDataAccess.ADD_DATE, minDateFilter);
     }
     if(maxDateFilter!=null) {
       dbc.addLessOrEqual(ReportResultDataAccess.ADD_DATE, maxDateFilter);
     }
     dbc.addOrderBy(ReportResultDataAccess.REPORT_RESULT_ID);
log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR sql: "+ ReportResultDataAccess.getSqlSelectIdOnly("*",dbc));
     reportResultDV = ReportResultDataAccess.select(analyticCon,dbc);

     //Get parameters
     IdVector reportResultIdV = new IdVector();
     for(int ii=0; ii<reportResultDV.size(); ii++) {
       ReportResultData rrD = (ReportResultData) reportResultDV.get(ii);
       reportResultIdV.add(new Integer(rrD.getReportResultId()));
     }
     dbc = new DBCriteria();
     dbc.addOneOf(ReportResultParamDataAccess.REPORT_RESULT_ID, reportResultIdV);
     dbc.addOrderBy(ReportResultParamDataAccess.REPORT_RESULT_ID);
     ReportResultParamDataVector reportResultParamDV =
                       ReportResultParamDataAccess.select(analyticCon,dbc);
     IdVector requesterIdV = new IdVector();
     for(int ii=0,jj=0; ii<reportResultDV.size(); ii++) {
       ReportResultData rrD = (ReportResultData) reportResultDV.get(ii);
       int id = rrD.getReportResultId();
       PreparedReportView prVw = PreparedReportView.createValue();
       prVw.setReportResultId(id);
       prVw.setGenericReportId(rrD.getGenericReportId());
       prVw.setReportCategory(rrD.getReportCategory());
       prVw.setReportName(rrD.getReportName());
       prVw.setReportDate(rrD.getAddDate());
       prVw.setReadFl(false);
       prVw.setReportResultStatusCd(rrD.getReportResultStatusCd());
       int requesterId = rrD.getUserId();
       prVw.setRequesterId(requesterId);
       prVw.setRequesterName("");
       prVw.setProtectedFl(rrD.getProtectedFl());
       requesterIdV.add(new Integer(requesterId));
       String params = "";
       if(serviceUserFl) {
         for(;jj<reportResultParamDV.size();jj++) {
           ReportResultParamData rrpD = (ReportResultParamData) reportResultParamDV.get(jj);
           int id1 = rrpD.getReportResultId();
           if(id1==id) {
             String paramName = rrpD.getParamName();
             if("CUSTOMER".equals(paramName)) paramName = "USER";
             if(params.length()>0) params += ", ";
             params += paramName + "=";
             params += rrpD.getParamValue();
             continue;
           }
           if(id1>id) {
             break;
           }
         }
         prVw.setReportParameters(params);
       }
       reports.add(prVw);
     }
     //Set read flag
     dbc = new DBCriteria();
     dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                 RefCodeNames.REPORT_RESULT_ASSOC_CD.USER_READ);
     dbc.addEqualTo(ReportResultAssocDataAccess.ASSOC_REF_ID,pUserId);
     dbc.addOneOf(ReportResultAssocDataAccess.REPORT_RESULT_ID,reportResultIdV);
     dbc.addOrderBy(ReportResultAssocDataAccess.REPORT_RESULT_ID);
     ReportResultAssocDataVector reportResAssocDV =
                                ReportResultAssocDataAccess.select(analyticCon,dbc);
     for(int ii=0,jj=0; ii<reports.size(); ii++) {
       PreparedReportView prVw = (PreparedReportView) reports.get(ii);
       int id = prVw.getReportResultId();
       for(;jj<reportResAssocDV.size();jj++) {
         ReportResultAssocData rraD = (ReportResultAssocData) reportResAssocDV.get(jj);
         int id1 = rraD.getReportResultId();
         if(id1==id) {
           prVw.setReadFl(true);
           jj++;
           break;
         }
         if(id1>id) {
           break;
         }
       }
     }

     dbc = new DBCriteria();
     dbc.addOneOf(UserDataAccess.USER_ID,requesterIdV);
     dbc.addOrderBy(UserDataAccess.USER_ID);
     UserDataVector userDV = UserDataAccess.select(mainCon,dbc);
     Object[] userA = userDV.toArray();
     for(int ii=0; ii<reports.size(); ii++) {
       PreparedReportView prVw = (PreparedReportView) reports.get(ii);
       int requesterId = prVw.getRequesterId();
       for(int jj=0; jj<userA.length; jj++) {
         UserData uD = (UserData) userA[jj];
         int id = uD.getUserId();
         if(id==requesterId) {
           prVw.setRequesterName(uD.getUserName());
           break;
         }
         if(id>requesterId) break;
       }
     }
     return reports;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }

  /** Gets report data of requested report
   * @param pReportResultId report result id
   * @param pFirstRecordId id of first record to return (when report is too big for one request)
   * @param pNbRecords maximum of records to be returned
   * @return set of ReportResultLineData objects
   * @throws   RemoteException Required by EJB 1.0
   */
  public ReportResultLineDataVector
      readArchiveReport(int pReportResultId, int pFirstRecordId, int pNbRecords)
  throws RemoteException
  {
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   ReportResultLineDataVector resultLines = new ReportResultLineDataVector();
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportResultLineDataAccess.REPORT_RESULT_ID,pReportResultId);
     if(pFirstRecordId>0) {
       dbc.addGreaterOrEqual(ReportResultLineDataAccess.REPORT_RESULT_LINE_ID,pFirstRecordId);
     }
     dbc.addOrderBy(ReportResultLineDataAccess.REPORT_RESULT_LINE_ID);
     if(pNbRecords>0) {
       resultLines =  ReportResultLineDataAccess.select(analyticCon,dbc,pNbRecords);
     }else{
       resultLines =  ReportResultLineDataAccess.select(analyticCon,dbc);
     }
     return resultLines;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }

  /** Gets report data of requested report
   * @param pReportResultId report result id
   * @return set of GenericReportResultView objects
   * @throws   RemoteException Required by EJB 1.0
   */
  public GenericReportResultViewVector
      readArchiveReport(int pReportResultId)
  throws RemoteException
  {
   Connection analyticCon = null;
   String errorMess = "No error";
   try {
	 if (ORACLE.equals(databaseName)) { //Oracle DB  
       analyticCon = getAnalyticConnection();
       Statement stmn=analyticCon.createStatement();
       //Get Blob 
       String sql =
       "select LINE_VALUE_BLOB "+
       " from RPT_REPORT_RESULT_LINE "+
       " where REPORT_RESULT_ID = "+pReportResultId+
       " order by REPORT_RESULT_LINE_ID";

       ResultSet rs1=stmn.executeQuery(sql);
       GenericReportResultViewVector genericReportResultVwV =
                                            new GenericReportResultViewVector();
       while(rs1.next()) {
         BLOB blob = (BLOB) rs1.getBlob(1);
         long len = blob.length();
         log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR blob length: "+blob.length());

         byte[] pageB = blob.getBytes(1L,(int) len);

         log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR bytes read: "+pageB.length);

         GenericReportResultView page = (GenericReportResultView) bytesToObject(pageB);
         List table  = page.getTable();
         if(table!=null){
    	     log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR number of records: "+table.size());
         }
         genericReportResultVwV.add(page);
       }
       rs1.close();
       stmn.close();
       return genericReportResultVwV;
     } else { //Postgres DB (Enterprise DB)
         analyticCon = getAnalyticConnection();
         Statement stmn=analyticCon.createStatement();
    	 //get bytea type data
         String sql =
             "select LINE_VALUE_BLOB "+
             " from RPT_REPORT_RESULT_LINE "+
             " where REPORT_RESULT_ID = "+pReportResultId+
             " order by REPORT_RESULT_LINE_ID";

             ResultSet rs1=stmn.executeQuery(sql);
             GenericReportResultViewVector genericReportResultVwV =
                                                  new GenericReportResultViewVector();
             while(rs1.next()) {            	              
               byte b[] = rs1.getBytes(1); //we don't use array b later...
               long len = b.length;
               log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR bytea length: "+len);

               byte[] pageB = rs1.getBytes(1); // it works correct
            	   
               log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR bytes read: "+pageB.length);

               GenericReportResultView page = (GenericReportResultView) bytesToObject(pageB);
               List table  = page.getTable();
               if(table!=null){
          	     log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRR number of records: "+table.size());
               }
               genericReportResultVwV.add(page);
             } 
             rs1.close();
             stmn.close();
             return genericReportResultVwV;
     }     
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
  }

    /**
     * Gets report result Ids of requested report
     *
     * @param pGenericReportId generic report id
     * @param pUserId          user
     * @param pStatusCd        statuf of report
     * @return ids
     * @throws RemoteException Required by EJB 1.0
     */
    public IdVector getArchiveReportIds(int pGenericReportId, int pUserId, String pStatusCd) throws RemoteException {

        Connection analyticCon = null;

        try {

            analyticCon = getAnalyticConnection();

            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(ReportResultDataAccess.GENERIC_REPORT_ID, pGenericReportId);
            dbCrit.addEqualTo(ReportResultDataAccess.USER_ID, pUserId);
            if (Utility.isSet(pStatusCd)) {
                dbCrit.addEqualTo(ReportResultDataAccess.REPORT_RESULT_STATUS_CD, RefCodeNames.REPORT_RESULT_STATUS_CD.GENERATED);
            }

            return ReportResultDataAccess.selectIdOnly(analyticCon, dbCrit);

        } catch (Exception exc) {
            exc.printStackTrace();
            logError(exc.getMessage());
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(analyticCon);
        }
    }

    public ReportResultParamDataVector getReportResultParams(int pReportResultId) throws RemoteException {

        Connection analyticCon = null;

        try {

            analyticCon = getAnalyticConnection();

            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(ReportResultParamDataAccess.REPORT_RESULT_ID, pReportResultId);

            return ReportResultParamDataAccess.select(analyticCon, dbCrit);

        } catch (Exception exc) {
            exc.printStackTrace();
            logError(exc.getMessage());
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(analyticCon);
        }
    }

    private static Object bytesToObject (byte[] pBytes) {
      Object obj = null;
      java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(pBytes);
      try {
        java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
        obj =  is.readObject();
        is.close();
        iStream.close();
      } catch(Exception exc) {
        exc.printStackTrace();
      }
      return obj;
    }

  /** Makes record in report result association table to indicate that report was read
   * @param pReportResultId report result id
   * @param pUserId user id
   * @throws   RemoteException Required by EJB 1.0
   */
  public void markReportAsRead(int pReportResultId, int pUserId)
  throws RemoteException
  {
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   ReportResultLineDataVector resultLines = new ReportResultLineDataVector();
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ID,pReportResultId);
     dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                 RefCodeNames.REPORT_RESULT_ASSOC_CD.USER_READ);
     dbc.addEqualTo(ReportResultAssocDataAccess.ASSOC_REF_ID,pUserId);
     IdVector idV = ReportResultAssocDataAccess.selectIdOnly(analyticCon,
          ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_ID,dbc);
     if(idV.size()<=0){
       ReportResultAssocData rraD = ReportResultAssocData.createValue();
       rraD.setReportResultId(pReportResultId);
       rraD.setAssocRefId(pUserId);
       rraD.setReportResultAssocCd(RefCodeNames.REPORT_RESULT_ASSOC_CD.USER_READ);
       ReportResultAssocDataAccess.insert(analyticCon,rraD);
     }
     return;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }


  /** Gets a set of group or user identifiers
   * @param pReportResultId report result id
   * @param pReportResultCd (USER or GROUP)
   * @return IdVector of identifiers
   * @throws   RemoteException Required by EJB 1.0
   */
  public IdVector getReportResultAssoc (int pReportResultId, String pReportResultAssocCd)
  throws RemoteException
  {
   Connection analyticCon = null;
   String errorMess = "No error";
   ReportResultLineDataVector resultLines = new ReportResultLineDataVector();
   try {
     analyticCon = getAnalyticConnection();
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ID,pReportResultId);
     dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                                            pReportResultAssocCd);

     IdVector idV = ReportResultAssocDataAccess.selectIdOnly(analyticCon,
          ReportResultAssocDataAccess.ASSOC_REF_ID,dbc);
     return idV;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
  }

  /** Removes and adds report result associations
   * @param pReportResultId report result id
   * @param pReportResultCd (USER or GROUP)
   * @param IdVectorAdd associations to add
   * @param IdVectorDel associations to remove
   * @param pUser userName
   * @throws   RemoteException Required by EJB 1.0
   */
  public void updateReportResultAssoc (int pReportResultId,
                                       IdVector pIdVectorAdd,
                                       IdVector pIdVectorDel,
                                       String pReportResultAssocCd,
                                       String pUser)
  throws RemoteException
  {
   Connection analyticCon = null;
   String errorMess = "No error";
   ReportResultLineDataVector resultLines = new ReportResultLineDataVector();
   try {
     analyticCon = getAnalyticConnection();
     DBCriteria dbc;
     //Delete
     if(pIdVectorDel!=null && pIdVectorDel.size()>0) {
       dbc = new DBCriteria();
       dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ID,pReportResultId);
       dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                                            pReportResultAssocCd);
       dbc.addOneOf(ReportResultAssocDataAccess.ASSOC_REF_ID,pIdVectorDel);

       ReportResultAssocDataAccess.remove(analyticCon,dbc);
     }

     //Chek existing assoc
     if(pIdVectorAdd!=null && pIdVectorAdd.size()>0) {
       dbc = new DBCriteria();
       dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ID,pReportResultId);
       dbc.addEqualTo(ReportResultAssocDataAccess.REPORT_RESULT_ASSOC_CD,
                                                            pReportResultAssocCd);
       dbc.addOneOf(ReportResultAssocDataAccess.ASSOC_REF_ID,pIdVectorAdd);
       dbc.addOrderBy(ReportResultAssocDataAccess.ASSOC_REF_ID);
       IdVector idVectorExist = ReportResultAssocDataAccess.
              selectIdOnly(analyticCon,ReportResultAssocDataAccess.ASSOC_REF_ID,dbc);
       int[] idExistA = new int[idVectorExist.size()];
       for(int ii=0; ii<idExistA.length; ii++) {
          Integer idI = (Integer) idVectorExist.get(ii);
          idExistA[ii] = idI.intValue();
       }
       for(int ii=0; ii<pIdVectorAdd.size(); ii++) {
          Integer idI = (Integer) pIdVectorAdd.get(ii);
          int id = idI.intValue();
          for(int jj=0; jj<idExistA.length; jj++) {
            if(id==idExistA[jj]) {
              pIdVectorAdd.remove(ii);
              break;
            }
            if(id<idExistA[jj]) break;
          }
       }
     }
     if(pIdVectorAdd!=null && pIdVectorAdd.size()>0) {
       for(int ii=0; ii<pIdVectorAdd.size(); ii++) {
         Integer idI = (Integer) pIdVectorAdd.get(ii);
         ReportResultAssocData rraD = ReportResultAssocData.createValue();
         rraD.setReportResultId(pReportResultId);
         rraD.setReportResultAssocCd(pReportResultAssocCd);
         rraD.setAddBy(pUser);
         rraD.setModBy(pUser);
         rraD.setAssocRefId(idI.intValue());
         ReportResultAssocDataAccess.insert(analyticCon, rraD);
       }
     }
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
  }

  /** Deletes report results from the DB
   * @param pReportResultIds set of report result ids
   * @param pUser userName
   * @throws   RemoteException Required by EJB 1.0
   */
  public void deletePreparedReports (IdVector pReportResultIds,
                                       String pUser)
  throws RemoteException
  {
   Connection analyticCon = null;
   String errorMess = "No error";
   try {

     analyticCon = getAnalyticConnection();
     DBCriteria dbc;
     if(pReportResultIds==null) return;

     dbc = new DBCriteria();
     dbc.addOneOf(ReportResultDataAccess.REPORT_RESULT_ID,pReportResultIds);
     String protectCond = "nvl("+ReportResultDataAccess.PROTECTED_FL+",'N')!='Y'";
     dbc.addCondition(protectCond);
     //dbc.addNotEqualTo(ReportResultDataAccess.PROTECTED_FL,"Y");
     IdVector reportResultIds = ReportResultDataAccess.selectIdOnly(analyticCon,
         ReportResultDataAccess.REPORT_RESULT_ID,dbc);
     log.info("ReportBean RRRRRRR reportResultIds: "+reportResultIds);
     //Delete assoc
     dbc = new DBCriteria();
     dbc.addOneOf(ReportResultAssocDataAccess.REPORT_RESULT_ID,reportResultIds);
     ReportResultAssocDataAccess.remove(analyticCon,dbc);

     //Delete parameters
     dbc = new DBCriteria();
     dbc.addOneOf(ReportResultParamDataAccess.REPORT_RESULT_ID,reportResultIds);
     ReportResultParamDataAccess.remove(analyticCon,dbc);

     //Delete report lines
     dbc = new DBCriteria();
     dbc.addOneOf(ReportResultLineDataAccess.REPORT_RESULT_ID,reportResultIds);
     ReportResultLineDataAccess.remove(analyticCon,dbc);

     //Delete report results
     dbc = new DBCriteria();
     dbc.addOneOf(ReportResultDataAccess.REPORT_RESULT_ID,reportResultIds);
     ReportResultDataAccess.remove(analyticCon,dbc);

   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
  }


  //Generate report schedule param info
  private String genrateParamInfo(ReportScheduleDetailDataVector pReportScheduleParams) {
    String paramInfo = "";
    if(pReportScheduleParams==null) {
      return paramInfo;
    }

    for(int ii=0; ii<pReportScheduleParams.size(); ii++) {
      ReportScheduleDetailData rsdD = (ReportScheduleDetailData) pReportScheduleParams.get(ii);
		if(rsdD.getReportScheduleId()==8) {
		  log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR rsdD: "+rsdD);
		}
      String value = rsdD.getDetailValue();
      if(Utility.isSet(value)) {
        String name = rsdD.getDetailName();
        if(paramInfo.length()>0) paramInfo +=  ", ";
        paramInfo += name + "="+value;
      }
    }
    return paramInfo;
  }

  //Generate report schedule info
  private String genrateScheduleInfo(ReportScheduleData pReportSchedule,
                         ReportScheduleDetailDataVector pReportScheduleDetails)
  throws Exception
  {
     String scheduleInfo = "";
     String rule = pReportSchedule.getReportScheduleRuleCd();
     int cycle = pReportSchedule.getCycle();

     //Schedule elements
     boolean exceptionFl = false;
     String deliveryDates = "";
     int datesCount = -1;
     String monthDays = "";
     String weekDays = "";
     String monthWeeks = "";
     int monthWeeksCount = 0;
     String monthWeekDay = "";
     Date currDate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     currDate = sdf.parse(sdf.format(currDate));
     for (int ii=0; ii<pReportScheduleDetails.size(); ii++) {
       ReportScheduleDetailData rsdD =
                      (ReportScheduleDetailData) pReportScheduleDetails.get(ii);
       String detailCd = rsdD.getReportScheduleDetailCd();
       if (RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
         if (RefCodeNames.REPORT_SCHEDULE_RULE_CD.DATE_LIST.equals(rule)) {
           String schDateS = rsdD.getDetailValue();
           try {
             Date schDate = sdf.parse(schDateS);
             if(!schDate.before(currDate)) {
               datesCount++;
               if(datesCount>12) continue;
               if (datesCount == 12) {
                deliveryDates += " ... ";
                continue;
               }
               if (datesCount > 0) deliveryDates += ", ";
               deliveryDates += schDateS;
             }
           } catch(Exception exc) {
             log.error("Wrond report schedule date format. Report schedule detail id: "+
                                                rsdD.getReportScheduleDetailId(), exc);
           }
         } else {
           exceptionFl = true;
         }
       } else if (RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailCd) &&
                  !(RefCodeNames.REPORT_SCHEDULE_RULE_CD.DATE_LIST.equals(rule))
       ) {
         exceptionFl = true;
       } else if (RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_DAY.equals(detailCd)) {
         if (monthDays.length() > 0) {
            monthDays += ", ";
         }
         int monthDay = 0;
         try {
            monthDay = Integer.parseInt(rsdD.getDetailValue());
         } catch (Exception exc) {
             log.error("Wrond report schedule month day format. Report schedule detail id: "+
                                                rsdD.getReportScheduleDetailId(),exc);
         }
         if (monthDay > 28) {
           monthDays += "last day";
         } else {
           monthDays += monthDay;
         }
       } else if (RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY.equals(detailCd)) {
         if (weekDays.length() > 0) {
           weekDays += ", ";
         }
         int weekDay = 0;
         try {
           weekDay = Integer.parseInt(rsdD.getDetailValue());
         } catch (Exception exc) {
             log.error("Wrond report schedule week day format. Report schedule detail id: "+
                                                rsdD.getReportScheduleDetailId(),exc);
         }
         switch (weekDay) {
           case 1: weekDays += "Sunday"; break;
           case 2: weekDays += "Monday"; break;
           case 3: weekDays += "Tuesday"; break;
           case 4: weekDays += "Wednesday"; break;
           case 5: weekDays += "Thursday"; break;
           case 6: weekDays += "Friday"; break;
           case 7: weekDays += "Saturday"; break;
           default: weekDays += "on " + weekDay + " day of the week"; break;
         }
       } else if (RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_WEEK.equals(detailCd)) {
         if (monthWeeks.length() > 0) {
           monthWeeks += ", ";
         }
         int monthWeek = 0;
         monthWeeksCount++;
         try {
           monthWeek = Integer.parseInt(rsdD.getDetailValue());
         } catch (Exception exc) {
             log.error("Wrond report schedule month week format. Report schedule detail id: "+
                                                rsdD.getReportScheduleDetailId(),exc);
         }
         switch (monthWeek) {
           case 1: monthWeeks += " first"; break;
           case 2: monthWeeks += " second"; break;
           case 3: monthWeeks += " third"; break;
           case 4: monthWeeks += " forth"; break;
           case 5: monthWeeks += " last"; break;
           default: monthWeeks += monthWeek; break;
         }
       }
     }
     //
     if (RefCodeNames.REPORT_SCHEDULE_RULE_CD.DATE_LIST.equals(rule)) {
        scheduleInfo = "On dates: " + deliveryDates;
     } else if (RefCodeNames.REPORT_SCHEDULE_RULE_CD.DAY_MONTH.equals(rule)) {
       if (cycle != 1) {
         scheduleInfo = "Each " + cycle + " month ";
       } else {
         scheduleInfo = "Each month ";
       }
       scheduleInfo += " on " + monthDays;
       if (exceptionFl) scheduleInfo += ". With exceptions";
     } else if (RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK.equals(rule)) {
       if (cycle != 1) {
         scheduleInfo = "Each " + cycle + " week ";
       } else {
         scheduleInfo = "Each week ";
       }
         scheduleInfo += " on " + weekDays;
         if (exceptionFl)scheduleInfo += ". With exceptions";
       } else if (RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK_MONTH.equals(rule)) {
         if (cycle != 1) {
           scheduleInfo = "Each " + cycle + " month ";
         } else {
           scheduleInfo = "Each month ";
         }
         scheduleInfo += " on " + monthWeeks + " week";
         if (monthWeeksCount > 1) scheduleInfo += "s";
         scheduleInfo += " on " + weekDays;
         if (exceptionFl) scheduleInfo += ". With exceptions";
       }
     return scheduleInfo;
  }
   /**
   * Returns list of report schedule view objects
   *
   * @param pFilters a set of filters (REPORT_CATEGORY,REPORT_NAME)
   * @return vector of ReportScheduleView obects
   * @throws   RemoteException Required by EJB 1.0
   */
  public ReportScheduleViewVector getReportSchedules(Map pFilters)
  throws RemoteException
  {
    return getReportSchedules(pFilters, null);
  }
  /**
   * Returns list of report schedule view objects
   *
   * @param pFilters a set of filters (REPORT_CATEGORY,REPORT_NAME)
   * @param pUser  application user data
   * @return vector of ReportScheduleView obects
   * @throws   RemoteException Required by EJB 1.0
   */

  public ReportScheduleViewVector getReportSchedules(Map pFilters, UserData pUser)
   throws RemoteException
   {
   ReportScheduleViewVector reportScheduleVwV = new ReportScheduleViewVector();
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     DBCriteria dbc = new DBCriteria();

     dbc = new DBCriteria();
     dbc.addCondition("1=1"); //in case of no filters

     //----------add filter by User
    // con = getConnection();
     dbc = new DBCriteria();
     dbc.addEqualTo(GroupAssocDataAccess.USER_ID, pUser.getUserId());
     dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);
     log.info("ReportBean RRRRRRRRR--1--RRRRRRRRRRRRRR sql: "+ GroupAssocDataAccess.getSqlSelectIdOnly("*",dbc));
     IdVector groupIdV = GroupAssocDataAccess.selectIdOnly(mainCon,GroupAssocDataAccess.GROUP_ID,dbc);

     dbc = new DBCriteria();
     LinkedList defaultGroups = new LinkedList();
     defaultGroups.add("EVERYONE");
     defaultGroups.add(pUser.getUserTypeCd());
     dbc.addOneOf(GroupDataAccess.SHORT_DESC,defaultGroups);
     log.info("ReportBean RRRRRRRRRR--2--RRRRRRRRRRRR sql: "+ GroupDataAccess.getSqlSelectIdOnly("*",dbc));
     IdVector everyoneIdV = GroupDataAccess.selectIdOnly(mainCon,GroupDataAccess.GROUP_ID,dbc);
     groupIdV.addAll(everyoneIdV);

     dbc = new DBCriteria();
     dbc.addOneOf(GroupAssocDataAccess.GROUP_ID,groupIdV);
     dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.REPORT_OF_GROUP);
     log.info("ReportBean RRRRRRRR--3--RRRRRRRRRRRRR sql: "+ GroupAssocDataAccess.getSqlSelectIdOnly("*",dbc));
     IdVector reportIdV = GroupAssocDataAccess.selectIdOnly(mainCon,GroupAssocDataAccess.GENERIC_REPORT_ID,dbc);

     dbc = new DBCriteria();
     dbc.addOneOf(GenericReportDataAccess.GENERIC_REPORT_ID, reportIdV);
     //---------- add filters by report_category or report_name
     if(pFilters!=null) {
      Set filterSet = pFilters.entrySet();
      Iterator filterIter = filterSet.iterator();
      while(filterIter.hasNext()) {
        Map.Entry filter = (Map.Entry) filterIter.next();
        String key = (String) filter.getKey();
        if("REPORT_CATEGORY".equals(key)) {
          try{
          String category = (String) filter.getValue();
          dbc.addContainsIgnoreCase(GenericReportDataAccess.CATEGORY, category);
          } catch (Exception exc) {
            exc.printStackTrace();
          }
         } else if("REPORT_NAME".equals(key)) {
           try {
           String name = (String) filter.getValue();
           dbc.addContainsIgnoreCase(GenericReportDataAccess.NAME, name);
           } catch (Exception exc) {
            exc.printStackTrace();
           }
          }
        }
      }


     dbc.addOrderBy(GenericReportDataAccess.GENERIC_REPORT_ID);
     log.info("ReportBean RRRRRRRRRR--4--RRRRRRRRRRRR sql: "+ GenericReportDataAccess.getSqlSelectIdOnly("*",dbc));
     GenericReportDataVector genericReportDV = GenericReportDataAccess.select(mainCon,dbc);

     //Get report ids
     IdVector genericReportIdV = new IdVector();
     for(int ii=0; ii<genericReportDV.size(); ii++) {
       GenericReportData grD = (GenericReportData) genericReportDV.get(ii);
       if (Utility.isUserAutorizedForReport(pUser.getUserTypeCd(), grD.getUserTypes())){
         genericReportIdV.add(new Integer(grD.getGenericReportId()));
       }
     }

     //Get report schedules
     dbc = new DBCriteria();
     dbc.addOneOf(ReportScheduleDataAccess.GENERIC_REPORT_ID, genericReportIdV);
     dbc.addOrderBy(ReportScheduleDataAccess.GENERIC_REPORT_ID);

     ReportScheduleDataVector reportScheduleDV = ReportScheduleDataAccess.select(analyticCon,dbc);

     //Create join objects
     IdVector reportScheduleIdV = new IdVector();
     for(int ii=0,jj=0; ii<reportScheduleDV.size(); ii++) {
       ReportScheduleData rsD = (ReportScheduleData) reportScheduleDV.get(ii);
       ReportScheduleView rsVw = ReportScheduleView.createValue();
       reportScheduleVwV.add(rsVw);
       int genericReportId = rsD.getGenericReportId();
       int reportScheduleId = rsD.getReportScheduleId();
       reportScheduleIdV.add(new Integer(reportScheduleId));
       rsVw.setReportScheduleId(reportScheduleId);
       rsVw.setLastRunDate(rsD.getLastRunDate());
       for(;jj<genericReportDV.size();jj++) {
         GenericReportData grD = (GenericReportData) genericReportDV.get(jj);
         int grId = grD.getGenericReportId();
         if(grId==genericReportId){
           rsVw.setGenericReportId(grD.getGenericReportId());
           rsVw.setReportCategory(grD.getCategory());
           rsVw.setReportName(grD.getName());
           rsVw.setScheduleName(rsD.getShortDesc());
           break;
         }
         if(grId>genericReportId) {
           break; //should never happen
         }
       }
     }

     //Reorder vectors by order schedule Id
     if(reportScheduleDV.size()>1) {
       Object[] reportScheduleDA = reportScheduleDV.toArray();
       Object[] reportScheduleVwA = reportScheduleVwV.toArray();
       for(int ii=0; ii<reportScheduleDA.length-1; ii++){
         boolean exitFl = true;
         for(int jj=0; jj<reportScheduleDA.length-ii-1; jj++) {
           ReportScheduleData rsD1 = (ReportScheduleData) reportScheduleDA[jj];
           ReportScheduleData rsD2 = (ReportScheduleData) reportScheduleDA[jj+1];
           int id1 = rsD1.getReportScheduleId();
           int id2 = rsD2.getReportScheduleId();
           if(id1>id2) {
             exitFl = false;
             reportScheduleDA[jj] = rsD2;
             reportScheduleDA[jj+1] = rsD1;
             Object oo = reportScheduleVwA[jj];
             reportScheduleVwA[jj] = reportScheduleVwA[jj+1];
             reportScheduleVwA[jj+1] = oo;
           }
         }
         if(exitFl) break;
       }
       //
       reportScheduleDV = new ReportScheduleDataVector();
       reportScheduleVwV = new ReportScheduleViewVector();
       for(int ii=0; ii<reportScheduleDA.length; ii++) {
         reportScheduleDV.add((ReportScheduleData) reportScheduleDA[ii]);
         reportScheduleVwV.add((ReportScheduleView)reportScheduleVwA[ii]);
       }
     }

     //Get details
     dbc = new DBCriteria();
     dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID, reportScheduleIdV);
     dbc.addOrderBy(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID);
     ReportScheduleDetailDataVector reportScheduleDetailDV =
                         ReportScheduleDetailDataAccess.select(analyticCon,dbc);

     //Create info strings
     for(int ii=0, jj=0; ii<reportScheduleVwV.size(); ii++) {
       ReportScheduleView rsVw = (ReportScheduleView) reportScheduleVwV.get(ii);
       ReportScheduleData rsD = (ReportScheduleData) reportScheduleDV.get(ii);
       int reportScheduleId = rsVw.getReportScheduleId();
       ReportScheduleDetailDataVector params = new ReportScheduleDetailDataVector();
       ReportScheduleDetailDataVector scheduleDetails = new ReportScheduleDetailDataVector();
       for(; jj<reportScheduleDetailDV.size(); jj++) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) reportScheduleDetailDV.get(jj);
         int id = rsdD.getReportScheduleId();
         if(id>reportScheduleId) {
           break;
         }
         if(id==reportScheduleId) {
           if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_PARAM.
                        equals(rsdD.getReportScheduleDetailCd())) {
             params.add(rsdD);
           } else {
             scheduleDetails.add(rsdD);
           }
         }
       } // end for
       rsVw.setReportParameters( genrateParamInfo(params));
       rsVw.setScheduleInfo(genrateScheduleInfo(rsD,scheduleDetails));
     }
     return reportScheduleVwV;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }

   /**
   * Returns report schedule view object
   *
   * @param pReportScheduleId
   * @return ReportScheduleView obect
   * @throws   RemoteException Required by EJB 1.0
   * @throws   DataNotFoundException
   */
   public ReportScheduleView getReportScheduleView(int pReportScheduleId)
   throws RemoteException, DataNotFoundException
   {
   ReportScheduleView reportScheduleVw = ReportScheduleView.createValue();
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     ReportScheduleData reportScheduleD = ReportScheduleDataAccess.select(analyticCon,pReportScheduleId);

     int genericReportId = reportScheduleD.getGenericReportId();
     GenericReportData genericReportD = GenericReportDataAccess.select(mainCon,genericReportId);


     reportScheduleVw.setReportScheduleId(pReportScheduleId);
     reportScheduleVw.setLastRunDate(reportScheduleD.getLastRunDate());
     reportScheduleVw.setGenericReportId(genericReportId);
     reportScheduleVw.setReportCategory(genericReportD.getCategory());
     reportScheduleVw.setReportName(genericReportD.getName());

     //Get details
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID, pReportScheduleId);
     ReportScheduleDetailDataVector reportScheduleDetailDV =
                         ReportScheduleDetailDataAccess.select(analyticCon,dbc);

     //Create info strings
     ReportScheduleDetailDataVector params = new ReportScheduleDetailDataVector();
     ReportScheduleDetailDataVector scheduleDetails = new ReportScheduleDetailDataVector();
     for(int ii=0; ii<reportScheduleDetailDV.size(); ii++) {
       ReportScheduleDetailData rsdD = (ReportScheduleDetailData) reportScheduleDetailDV.get(ii);
       if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_PARAM.
                                            equals(rsdD.getReportScheduleDetailCd())) {
         params.add(rsdD);
       } else {
         scheduleDetails.add(rsdD);
       }
     }
     reportScheduleVw.setReportParameters( genrateParamInfo(params));
     reportScheduleVw.setScheduleInfo(genrateScheduleInfo(reportScheduleD,scheduleDetails));
     return reportScheduleVw;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }
   /**
   * Saves report shedule
   * @param pReportScheduleJoin schedule join object
   * @param pUser user name
   * @return ReportScheduleJoinView obect
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportScheduleJoinView
       saveReportSchedule(ReportScheduleJoinView pReportScheduleJoin, String pUser)
   throws RemoteException
   {
   Connection analyticCon = null;
   String errorMess = "No error";
   try {

     ReportScheduleData reportScheduleD = pReportScheduleJoin.getSchedule();
     ReportScheduleDetailDataVector reportScheduleDetailDV =
                                       pReportScheduleJoin.getScheduleDetails();
     GenericReportControlViewVector genericReportControlVwV =
                                         pReportScheduleJoin.getReportControls();
     ReportSchedGroupShareViewVector groupVwV = pReportScheduleJoin.getGroups();
     ReportSchedUserShareViewVector userVwV = pReportScheduleJoin.getUsers();

     analyticCon = getAnalyticConnection();

     //Report Schedule
     DBCriteria dbc = new DBCriteria();
     int reportScheduleId = reportScheduleD.getReportScheduleId();
     reportScheduleD.setModBy(pUser);
     if(reportScheduleId==0) {
       reportScheduleD.setAddBy(pUser);
       reportScheduleD = ReportScheduleDataAccess.insert(analyticCon,reportScheduleD);
       reportScheduleId = reportScheduleD.getReportScheduleId();
     } else {
       ReportScheduleDataAccess.update(analyticCon,reportScheduleD);
       dbc = new DBCriteria();
       dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID,reportScheduleId);
       ReportScheduleDetailDataAccess.remove(analyticCon,dbc);
     }

     //Add ScheduleDetails
     for(int ii=0; ii<reportScheduleDetailDV.size(); ii++) {
       ReportScheduleDetailData rsdD =
                     (ReportScheduleDetailData) reportScheduleDetailDV.get(ii);
       rsdD.setReportScheduleId(reportScheduleId);
       rsdD.setAddBy(pUser);
       rsdD.setModBy(pUser);
       ReportScheduleDetailDataAccess.insert(analyticCon,rsdD);
     }

     //Add Parameters
     for(int ii=0; ii<genericReportControlVwV.size(); ii++) {
       ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
       GenericReportControlView grcVw =
                      (GenericReportControlView) genericReportControlVwV.get(ii);

       rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_PARAM);
       rsdD.setDetailName(grcVw.getName());
       rsdD.setDetailValue(grcVw.getValue());
log.info("[ReportBean] :: name=" + grcVw.getName() + "/" + grcVw.getValue());
       rsdD.setReportScheduleId(reportScheduleId);
       rsdD.setAddBy(pUser);
       rsdD.setModBy(pUser);
       ReportScheduleDetailDataAccess.insert(analyticCon,rsdD);
     }

     //Add User Assoc
     for(int ii=0; ii<userVwV.size(); ii++) {
       ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
       ReportSchedUserShareView uVw = (ReportSchedUserShareView) userVwV.get(ii);
       if(uVw.getNotifyFl()) {
         rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_USER);
       } else if (uVw.getReportOwnerFl()) {
           rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_OWNER);
       } else {
         rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_USER);
       }
       String userIdS = ""+uVw.getUserId();
       rsdD.setDetailValue(userIdS);
       rsdD.setReportScheduleId(reportScheduleId);
       rsdD.setAddBy(pUser);
       rsdD.setModBy(pUser);
       ReportScheduleDetailDataAccess.insert(analyticCon,rsdD);
     }

     //Add Group Assoc
     for(int ii=0; ii<groupVwV.size(); ii++) {
       ReportScheduleDetailData rsdD = ReportScheduleDetailData.createValue();
       ReportSchedGroupShareView gVw = (ReportSchedGroupShareView) groupVwV.get(ii);
       if(gVw.getNotifyFl()) {
         rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_GROUP);
       } else {
         rsdD.setReportScheduleDetailCd(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_GROUP);
       }
       String groupIdS = ""+gVw.getGroupId();
       rsdD.setDetailValue(groupIdS);
       rsdD.setReportScheduleId(reportScheduleId);
       rsdD.setAddBy(pUser);
       rsdD.setModBy(pUser);
       ReportScheduleDetailDataAccess.insert(analyticCon,rsdD);
     }


     return pReportScheduleJoin;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
  }

   /**
   * Gets report shedule join object
   * @param pReportScheduleId report schedule id
   * @return ReportScheduleJoinView obect
   * @throws   RemoteException Required by EJB 1.0
   */
   public ReportScheduleJoinView getReportSchedule(int pReportScheduleId)
   throws RemoteException
   {
   Connection mainCon = null;
   Connection analyticCon = null;
   String errorMess = "No error";
   try {
     mainCon = getConnection();
     analyticCon = getAnalyticConnection();
     ReportScheduleJoinView reportScheduleJoinVw =
                                           ReportScheduleJoinView.createValue();

     //Schedule
     ReportScheduleData rsD = ReportScheduleDataAccess.select(analyticCon,pReportScheduleId);
     reportScheduleJoinVw.setSchedule(rsD);

     //Generic Report
     int genericReportId = rsD.getGenericReportId();
     GenericReportData grD = GenericReportDataAccess.select(mainCon, genericReportId);
     grD.setScriptText(null); //do not needin this case
     grD.setSqlText(null); //do not need in this case
     reportScheduleJoinVw.setReport(grD);

     //Schedule Details
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID, pReportScheduleId);
     ArrayList list = new ArrayList();
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ALSO_DATE);
     //list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ELEMENT);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EXCEPT_DATE);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_DAY);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_WEEK);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EMAIL_FLAG);
     dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD, list);
     ReportScheduleDetailDataVector rsdDV =
                             ReportScheduleDetailDataAccess.select(analyticCon,dbc);
     reportScheduleJoinVw.setScheduleDetails(rsdDV);

     // Parameters
     dbc = new DBCriteria();
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID, pReportScheduleId);
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD,
                            RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_PARAM);
     ReportScheduleDetailDataVector paramDV =
                             ReportScheduleDetailDataAccess.select(analyticCon,dbc);
     Object[] paramA = paramDV.toArray();


     GenericReportControlViewVector genericReportControls =
                                       getGenericReportControls(genericReportId);

     for(int ii=0; ii<genericReportControls.size(); ii++) {
       GenericReportControlView grcVw =
                       (GenericReportControlView) genericReportControls.get(ii);
       String name = grcVw.getName();
       for(int jj=0; jj<paramA.length; jj++) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) paramA[jj];
         if(name.equals(rsdD.getDetailName())) {
           grcVw.setValue(rsdD.getDetailValue());
           break;
         }
       }
     }
     reportScheduleJoinVw.setReportControls(genericReportControls);

     //Users
     dbc = new DBCriteria();
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID, pReportScheduleId);
     ArrayList assocCdAL = new ArrayList();
     assocCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_USER);
     assocCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_USER);
     assocCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_OWNER);
     assocCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_GROUP);
     assocCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_GROUP);
     dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD, assocCdAL);
     ReportScheduleDetailDataVector assocDV =
                             ReportScheduleDetailDataAccess.select(analyticCon,dbc);

     IdVector userAssocIdV = new IdVector();
     IdVector userNotifyIdV = new IdVector();
     IdVector userReportOwnerIdV = new IdVector();
     IdVector groupAssocIdV = new IdVector();
     IdVector groupNotifyIdV = new IdVector();
     for(int ii=0; ii<assocDV.size(); ii++) {
       ReportScheduleDetailData rsdD = (ReportScheduleDetailData) assocDV.get(ii);
       String idS = rsdD.getDetailValue();
       int id = Integer.parseInt(idS);
       if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_USER.equals(rsdD.getReportScheduleDetailCd())) {
         userAssocIdV.add(new Integer(id));
       } else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_USER.equals(rsdD.getReportScheduleDetailCd())) {
         userNotifyIdV.add(new Integer(id));
       } else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_OWNER.equals(rsdD.getReportScheduleDetailCd())) {
    	 userReportOwnerIdV.add(new Integer(id));
       } else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_GROUP.equals(rsdD.getReportScheduleDetailCd())) {
         groupAssocIdV.add(new Integer(id));
       } else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_GROUP.equals(rsdD.getReportScheduleDetailCd())) {
         groupNotifyIdV.add(new Integer(id));
       }
     }
     ReportSchedUserShareViewVector userShareVwV = new ReportSchedUserShareViewVector();
     if(userAssocIdV.size()>0) {
       dbc = new DBCriteria();
       dbc.addOneOf(UserDataAccess.USER_ID,userAssocIdV);
       dbc.addOrderBy(UserDataAccess.FIRST_NAME);
       dbc.addOrderBy(UserDataAccess.LAST_NAME);
       UserDataVector userDV = UserDataAccess.select(mainCon,dbc);
       for(int ii=0; ii<userDV.size(); ii++) {
         UserData uD = (UserData) userDV.get(ii);
         ReportSchedUserShareView rsusVw = ReportSchedUserShareView.createValue();
         rsusVw.setReportScheduleId(pReportScheduleId);
         rsusVw.setUserId(uD.getUserId());
         rsusVw.setUserFirstName(uD.getFirstName());
         rsusVw.setUserLastName(uD.getLastName());
         rsusVw.setUserLoginName(uD.getUserName());
         rsusVw.setUserTypeCd(uD.getUserTypeCd());
         rsusVw.setUserStatusCd(uD.getUserStatusCd());
         rsusVw.setNotifyFl(false);
         userShareVwV.add(rsusVw);
       }
     }
     if(userNotifyIdV.size()>0) {
       dbc = new DBCriteria();
       dbc.addOneOf(UserDataAccess.USER_ID,userNotifyIdV);
       dbc.addOrderBy(UserDataAccess.FIRST_NAME);
       dbc.addOrderBy(UserDataAccess.LAST_NAME);
       UserDataVector userDV = UserDataAccess.select(mainCon,dbc);
       for(int ii=0; ii<userDV.size(); ii++) {
         UserData uD = (UserData) userDV.get(ii);
         ReportSchedUserShareView rsusVw = ReportSchedUserShareView.createValue();
         rsusVw.setReportScheduleId(pReportScheduleId);
         rsusVw.setUserId(uD.getUserId());
         rsusVw.setUserFirstName(uD.getFirstName());
         rsusVw.setUserLastName(uD.getLastName());
         rsusVw.setUserLoginName(uD.getUserName());
         rsusVw.setUserTypeCd(uD.getUserTypeCd());
         rsusVw.setUserStatusCd(uD.getUserStatusCd());
         rsusVw.setNotifyFl(true);
         userShareVwV.add(rsusVw);
       }
     }
     if(userReportOwnerIdV.size()>0) {
         dbc = new DBCriteria();
         dbc.addOneOf(UserDataAccess.USER_ID,userReportOwnerIdV);
         dbc.addOrderBy(UserDataAccess.FIRST_NAME);
         dbc.addOrderBy(UserDataAccess.LAST_NAME);
         UserDataVector userDV = UserDataAccess.select(mainCon,dbc);
         for(int ii=0; ii<userDV.size(); ii++) {
           UserData uD = (UserData) userDV.get(ii);
           ReportSchedUserShareView rsusVw = ReportSchedUserShareView.createValue();
           rsusVw.setReportScheduleId(pReportScheduleId);
           rsusVw.setUserId(uD.getUserId());
           rsusVw.setUserFirstName(uD.getFirstName());
           rsusVw.setUserLastName(uD.getLastName());
           rsusVw.setUserLoginName(uD.getUserName());
           rsusVw.setUserTypeCd(uD.getUserTypeCd());
           rsusVw.setUserStatusCd(uD.getUserStatusCd());
           rsusVw.setReportOwnerFl(true);
           userShareVwV.add(rsusVw);
         }
       }
     reportScheduleJoinVw.setUsers(userShareVwV);

     ReportSchedGroupShareViewVector groupShareVwV = new ReportSchedGroupShareViewVector();
     if(groupAssocIdV.size()>0) {
       dbc = new DBCriteria();
       dbc.addOneOf(GroupDataAccess.GROUP_ID,groupAssocIdV);
       dbc.addOrderBy(GroupDataAccess.SHORT_DESC);
       GroupDataVector groupDV = GroupDataAccess.select(mainCon,dbc);
       for(int ii=0; ii<groupDV.size(); ii++) {
         GroupData gD = (GroupData) groupDV.get(ii);
         ReportSchedGroupShareView rsgsVw = ReportSchedGroupShareView.createValue();
         rsgsVw.setReportScheduleId(pReportScheduleId);
         rsgsVw.setGroupId(gD.getGroupId());
         rsgsVw.setGroupShortDesc(gD.getShortDesc());
         rsgsVw.setGroupTypeCd(gD.getGroupTypeCd());
         rsgsVw.setGroupStatusCd(gD.getGroupStatusCd());
         rsgsVw.setNotifyFl(false);
         groupShareVwV.add(rsgsVw);
       }
     }
     if(groupNotifyIdV.size()>0) {
       dbc = new DBCriteria();
       dbc.addOneOf(GroupDataAccess.GROUP_ID,groupNotifyIdV);
       dbc.addOrderBy(GroupDataAccess.SHORT_DESC);
       GroupDataVector groupDV = GroupDataAccess.select(mainCon,dbc);
       for(int ii=0; ii<groupDV.size(); ii++) {
         GroupData gD = (GroupData) groupDV.get(ii);
         ReportSchedGroupShareView rsgsVw = ReportSchedGroupShareView.createValue();
         rsgsVw.setReportScheduleId(pReportScheduleId);
         rsgsVw.setGroupId(gD.getGroupId());
         rsgsVw.setGroupShortDesc(gD.getShortDesc());
         rsgsVw.setGroupTypeCd(gD.getGroupTypeCd());
         rsgsVw.setGroupStatusCd(gD.getGroupStatusCd());
         rsgsVw.setNotifyFl(true);
         groupShareVwV.add(rsgsVw);
       }
     }

     reportScheduleJoinVw.setGroups(groupShareVwV);

     return reportScheduleJoinVw;
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(mainCon);
     closeConnection(analyticCon);
   }
  }

   /**
   * Deletes a set of report schedules
   * @param pReportScheduleIds report schedule ids
   * @throws   RemoteException Required by EJB 1.0
   */
   public void deleteReportSchedules(IdVector pReportScheduleIds)
   throws RemoteException
   {
   Connection analyticCon = null;
   String errorMess = "No error";
   try {

     analyticCon = getAnalyticConnection();
     DBCriteria dbc = new DBCriteria();
     dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID, pReportScheduleIds);
     ReportScheduleDetailDataAccess.remove(analyticCon,dbc);

     dbc = new DBCriteria();
     dbc.addOneOf(ReportScheduleDataAccess.REPORT_SCHEDULE_ID, pReportScheduleIds);
     ReportScheduleDataAccess.remove(analyticCon,dbc);
   }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
  }


   /**
   * Runs sheduled report
   *
   * @param pReportScheduleId report schedule id
   * @param pUserId user id requested the report (0 if runs by script)
   * @param pUserName user name requested the report (null or UNKNOWN if runs by script)
   * @return ReportResultData object
   * @throws   RemoteException Required by EJB 1.0
   */
// public static final String last_month_year_begin = "last_month_year_begin";
// public static final String this_year_begin = "this_year_begin";
// public static final String last_month_end = "last_month_end";
// public static final String today = "today";
   public ReportResultData processScheduledReport(int pReportScheduleId, int pUserId, String pUserName)
   throws RemoteException
   {
     Connection analyticCon = null;
     String errorMess = "No error";
     try {
       analyticCon = getAnalyticConnection();
       ReportScheduleData rsD =
                ReportScheduleDataAccess.select(analyticCon,pReportScheduleId);
       int ownerId = getReportOwnerId (analyticCon,  pReportScheduleId );

       DBCriteria dbc = new DBCriteria();
       dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID,pReportScheduleId);
       dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD,
                     RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_PARAM);
       ReportScheduleDetailDataVector rsdDV =
                         ReportScheduleDetailDataAccess.select(analyticCon,dbc);
       Map params = new java.util.HashMap();
       for(int ii=0; ii<rsdDV.size(); ii++) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) rsdDV.get(ii);
         String name = rsdD.getDetailName();
         String value = (rsdD.getDetailValue()==null) ? "" : rsdD.getDetailValue();
         value = customizeParam(name,value, ownerId);
log.info("[ReportBean].processScheduledReport() :: name = " + name + "/" + value);
         params.put(name,value);
       }


       ReportResultData result =
                          processAnalyticReport(rsD.getGenericReportId(), pReportScheduleId,
                                                     params, pUserId, pUserName,true);
       dbc = new DBCriteria();
       dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID,pReportScheduleId);
       ArrayList assocUserCdAL = new ArrayList();
       assocUserCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_USER);
       assocUserCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_USER);
       assocUserCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_OWNER);
       dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD,assocUserCdAL);
       ReportScheduleDetailDataVector reportUserAssoc =
                         ReportScheduleDetailDataAccess.select(analyticCon,dbc);
       for(int ii=0; ii<reportUserAssoc.size(); ii++) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) reportUserAssoc.get(ii);
         String userIdS = rsdD.getDetailValue();
         int userId = 0;
         try {
           userId = Integer.parseInt(userIdS);
         } catch(Exception exc) {
            String em = "Wrong userId format: "+userIdS;
            log.error(em,exc);
            continue;
         }
         ReportResultAssocData rraD = ReportResultAssocData.createValue();
         rraD.setReportResultId(result.getReportResultId());
         rraD.setAssocRefId(userId);
         rraD.setReportResultAssocCd(RefCodeNames.REPORT_RESULT_ASSOC_CD.USER);
         rraD.setAddBy(pUserName);
         rraD.setModBy(pUserName);
         ReportResultAssocDataAccess.insert(analyticCon,rraD);
       }

       dbc = new DBCriteria();
       dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID,pReportScheduleId);
       ArrayList assocGroupCdAL = new ArrayList();
       assocGroupCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ASSOC_GROUP);
       assocGroupCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.NOTIFY_GROUP);
       assocGroupCdAL.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_OWNER);
       dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD,assocGroupCdAL);
       ReportScheduleDetailDataVector reportGroupAssoc =
                         ReportScheduleDetailDataAccess.select(analyticCon,dbc);
       for(int ii=0; ii<reportGroupAssoc.size(); ii++) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) reportGroupAssoc.get(ii);
         String groupIdS = rsdD.getDetailValue();
         int groupId = 0;
         try {
           groupId = Integer.parseInt(groupIdS);
         } catch(Exception exc) {
            String em = "Wrong groupId format: "+groupIdS;
            log.error(em,exc);
            continue;
         }
         ReportResultAssocData rraD = ReportResultAssocData.createValue();
         rraD.setReportResultId(result.getReportResultId());
         rraD.setAssocRefId(groupId);
         rraD.setReportResultAssocCd(RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP);
         rraD.setAddBy(pUserName);
         rraD.setModBy(pUserName);
         ReportResultAssocDataAccess.insert(analyticCon,rraD);
       }
       rsD.setLastRunDate(result.getModDate());
       rsD.setModBy(pUserName);
       ReportScheduleDataAccess.update(analyticCon,rsD);
       return result;
     }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
   }

   private int getReportOwnerId (Connection analyticCon, int pReportScheduleId ) throws Exception {
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID,pReportScheduleId);
     dbc.addEqualTo(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD,
                   RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.REPORT_OWNER);
     ReportScheduleDetailDataVector rsdDV = ReportScheduleDetailDataAccess.select(analyticCon,dbc);
     int ownerId = -1;
     for(int ii=0; ii<rsdDV.size(); ii++) {
       ReportScheduleDetailData rsdD = (ReportScheduleDetailData) rsdDV.get(ii);
       String value = rsdD.getDetailValue();
       ownerId = Integer.parseInt(value);
     }
     return ownerId;
   }

   private String customizeParam(String name, String value, int ownerId) throws Exception {
     String retValue = Utility.customizeDateParam(name,value);
     if ("DW_STORE_SELECT".equalsIgnoreCase(name)){
            retValue =Integer.toString(getStoreDimId(value));
     }
     if ("DW_USER_SELECT".equalsIgnoreCase(name)){
       if (ownerId >=0){
         retValue =getUserFilterForAccounts( ownerId );
       }
     }
     return retValue;
   }
   private String getMainConnName() throws  RemoteException {
    String schema = "";
    try {
      Connection connMain = getConnection();
      schema = connMain.getMetaData().getUserName();
      closeConnection(connMain);
    } catch (Exception exc) {
         exc.printStackTrace();
         throw processException(exc);
    }

    return schema;
  }

   private  String getUserFilterForAccounts(int pUserId) throws  RemoteException {
     String schema = getMainConnName();
     String filterStr =
         " and ACCOUNT_DIM_ID in ( \n" +
         " Select ACCOUNT_DIM_ID from DW_ACCOUNT_DIM where  \n" +
         "    account_ID in ( " +
         "     Select BUS_ENTITY_ID from " + schema + ".CLW_BUS_ENTITY b \n" +
         "     WHERE BUS_ENTITY_TYPE_CD  ='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT +"' \n"+
         "       AND BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE +"' \n" +
         "       AND (exists \n" +
         "       (select * from " + schema + ".CLW_USER_ASSOC ua \n" +
         "        where ua.USER_ID IN (" + pUserId + ") \n" +
         "           and b.BUS_ENTITY_ID=ua.BUS_ENTITY_ID)) \n " +
         ")) \n";
     return filterStr;
   }


   private int getStoreDimId (String storeId)  throws RemoteException {
     Connection conn = null;
     int storeDimId = -1;
     try {
       conn = getReportConnection();
       String sql =
           " select STORE_DIM_ID from DW_STORE_DIM where STORE_ID = " + storeId;

       log.info("ReportBean.getStoreDimId------------>SQL: " + sql);
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       while (rs.next()) {
         storeDimId = rs.getInt("STORE_DIM_ID");
       }
       stmt.close();
     }
     catch (Exception exc) {
       exc.printStackTrace();
       throw processException(exc);
     }
     finally {
       closeConnection(conn);
     }
     return storeDimId;
   }



/*
	private String customizeParam(String name, String value)
	throws Exception {
		if(value == null) {
			return value;
		}
		String retValue = value;
 	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String valueLC = value.toLowerCase();
		if(name.toLowerCase().indexOf("date")>=0) {
			if(value.equals(today)) {
				retValue = sdf.format(new Date());
			} else if(value.equals(last_month_year_begin)) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.add(GregorianCalendar.MONTH,-1);
				retValue = "01/01/"+gc.get(GregorianCalendar.YEAR);
			} else if(value.equals(this_year_begin)) {
				GregorianCalendar gc = new GregorianCalendar();
				retValue = "01/01/"+gc.get(GregorianCalendar.YEAR);
			} else if(value.equals(last_month_end)) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.set(GregorianCalendar.DAY_OF_MONTH,1);
				gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
				retValue =
					(gc.get(GregorianCalendar.MONTH)+1)+"/"+
					gc.get(GregorianCalendar.DAY_OF_MONTH)+"/"+
					gc.get(GregorianCalendar.YEAR);
			}
		}
		return retValue;
	}
*/

   /**
   * Gets active report ids cheduled to run on the date
   *
   * @param pDate requested date (date only - hours, minutes, etc. will be cut off).
   * Takes current date if null
   * @return Set of report schedule ids
   * @throws   RemoteException Required by EJB 1.0
   */
   public IdVector getScheduledReportIds(Date pDate)
   throws RemoteException
   {
     Connection analyticCon = null;
     IdVector scheduleIds = new IdVector();
     String errorMess = "No error";
     try {

     analyticCon = getAnalyticConnection();

     if(pDate==null) pDate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     pDate = sdf.parse(sdf.format(pDate));

     //Get active report schedules
     //For every schedule determine the date
     //1.check except dates to filter out
     //2. check also dates to accept
     //3. check scedule

     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(ReportScheduleDataAccess.REPORT_SCHEDULE_STATUS_CD,
                   RefCodeNames.REPORT_SCHEDULE_STATUS_CD.ACTIVE);
     String schReq =
     ReportScheduleDataAccess.getSqlSelectIdOnly(ReportScheduleDataAccess.REPORT_SCHEDULE_ID,dbc);
     dbc.addOrderBy(ReportScheduleDataAccess.REPORT_SCHEDULE_ID);
     ReportScheduleDataVector reportScheduleDV =
                                     ReportScheduleDataAccess.select(analyticCon, dbc);

     dbc = new DBCriteria();
       dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID,schReq);
     ArrayList list = new ArrayList();
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ALSO_DATE);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EXCEPT_DATE);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_DAY);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_WEEK);
     list.add(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY);
     dbc.addOneOf(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_DETAIL_CD, list);
     dbc.addOrderBy(ReportScheduleDetailDataAccess.REPORT_SCHEDULE_ID);
     ReportScheduleDetailDataVector rsdDV =
                             ReportScheduleDetailDataAccess.select(analyticCon,dbc);

     for (int ii=0,jj=0; ii<reportScheduleDV.size(); ii++) {
       ReportScheduleData rsD = (ReportScheduleData) reportScheduleDV.get(ii);
       int reportScheduleId = rsD.getReportScheduleId();
       LinkedList exceptDates = new LinkedList();
       LinkedList alsoDates = new LinkedList();
       LinkedList scheduleDetails = new LinkedList();
       for(; jj<rsdDV.size(); jj++) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) rsdDV.get(jj);
         int id = rsdD.getReportScheduleId();
         if(id>reportScheduleId) {
           break;
         }
         if(id==reportScheduleId) {
           String detailCd = rsdD.getReportScheduleDetailCd();
           if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailCd)) {
             exceptDates.add(rsdD);
           }else if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
             alsoDates.add(rsdD);
           }else {
             scheduleDetails.add(rsdD);
           }
         }
       }
       boolean yesFl =
         checkScheduleDate(pDate, rsD, exceptDates, alsoDates, scheduleDetails);
       if(yesFl) {
         scheduleIds.add(new Integer(reportScheduleId));
       }
     }

     return scheduleIds;

     }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
   }

   private boolean  checkScheduleDate(Date pDate, ReportScheduleData pSchedule,
                           List pExceptDates, List pAlsoDates, List pScheduleDetails)
   throws Exception
   {
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     Iterator iter = pExceptDates.iterator();
     while(iter.hasNext()) {
       ReportScheduleDetailData rsdD = (ReportScheduleDetailData) iter.next();
       String dateS = rsdD.getDetailValue();
       Date date = sdf.parse(dateS);
       if(date.equals(pDate)) {
           return false;
       }
     }
     iter = pAlsoDates.iterator();
     while(iter.hasNext()) {
       ReportScheduleDetailData rsdD = (ReportScheduleDetailData) iter.next();
       String dateS = rsdD.getDetailValue();
       Date date = sdf.parse(dateS);
       if(date.equals(pDate)) {
           return true;
       }
     }

     Date startDate = pSchedule.getAddDate();
     startDate = sdf.parse(sdf.format(startDate));
     if(pDate.before(startDate)){
       return false;
     }

     GregorianCalendar scheduleStart = new GregorianCalendar();
     scheduleStart.setTime(startDate);

     String rule = pSchedule.getReportScheduleRuleCd();
     int cycle = pSchedule.getCycle();

     GregorianCalendar currCalendar = new GregorianCalendar();
     currCalendar.setTime(pDate);

     //Weekly schedule
     if(RefCodeNames.REPORT_SCHEDULE_RULE_CD.WEEK.equals(rule)) {
       int currDayOfWeek = currCalendar.get(GregorianCalendar.DAY_OF_WEEK);
       if(cycle!=1) {
         GregorianCalendar startWeek = (GregorianCalendar) scheduleStart.clone();
         int dayOfWeek = startWeek.get(GregorianCalendar.DAY_OF_WEEK);
         startWeek.add(GregorianCalendar.DATE,-dayOfWeek+1);
         long startMills = startWeek.getTime().getTime();

         GregorianCalendar currWeek = new GregorianCalendar();
         currWeek.setTime(pDate);
         currWeek.add(GregorianCalendar.DATE,-currDayOfWeek+1);
         long currMills = currWeek.getTime().getTime();

         long diff = currMills - startMills;
        int nbWeeks = (int) (diff/(7*24*3600*1000));
        if(nbWeeks%cycle!=0) {
          return false; //this week is off
        }
      }
      iter = pScheduleDetails.iterator();
      while(iter.hasNext()) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) iter.next();
         String dayOfWeekS = rsdD.getDetailValue();
         if(!RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY.
                               equals(rsdD.getReportScheduleDetailCd())) {
            continue;
         }
         int dayOfWeek = Integer.parseInt(dayOfWeekS);
         if(dayOfWeek==currDayOfWeek) {
           return true;
         }
      }
      return false;
    }
    //Month day schedule
    if(RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH.equals(rule)) {
      if(cycle!=1) {
        int startMonth = scheduleStart.get(GregorianCalendar.MONTH);
        int startYear =  scheduleStart.get(GregorianCalendar.YEAR);
        int startYM = startYear*12+startMonth;

        int currMonth = currCalendar.get(GregorianCalendar.MONTH);
        int currYear = currCalendar.get(GregorianCalendar.YEAR);
        int currYM = currYear*12+currMonth;
        int diff = currYM - startYM;
        if(diff%cycle!=0) {
          return false;
        }
      }
      int currDayOfMonth = currCalendar.get(GregorianCalendar.DAY_OF_MONTH);
      iter = pScheduleDetails.iterator();
      while(iter.hasNext()) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) iter.next();
         String dayOfMonthS = rsdD.getDetailValue();
         if(!RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_DAY.
                               equals(rsdD.getReportScheduleDetailCd())) {
            continue;
         }
         int dayOfMonth = Integer.parseInt(dayOfMonthS);
         if(dayOfMonth==currDayOfMonth) {
           return true;
         }
         if(dayOfMonth>31) {
           GregorianCalendar wrkCal = (GregorianCalendar) currCalendar.clone();
           int currMonth = wrkCal.get(GregorianCalendar.MONTH);
           wrkCal.add(GregorianCalendar.DATE,1);
           if(currMonth!=wrkCal.get(GregorianCalendar.MONTH)) {
             return true;
           }
         }
      }
      return false;
    }

    //Month week schedule
    if(RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH.equals(rule)) {
      if(cycle!=1) {
        int startMonth = scheduleStart.get(GregorianCalendar.MONTH);
        int startYear =  scheduleStart.get(GregorianCalendar.YEAR);
        int startYM = startYear*12+startMonth;

        int currMonth = currCalendar.get(GregorianCalendar.MONTH);
        int currYear = currCalendar.get(GregorianCalendar.YEAR);
        int currYM = currYear*12+currMonth;
        int diff = currYM - startYM;
        if(diff%cycle!=0) {
          return false;
        }
      }

      int currDayOfWeek = currCalendar.get(GregorianCalendar.DAY_OF_WEEK);
      iter = pScheduleDetails.iterator();
      boolean foundFl = false;
      while(iter.hasNext()) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) iter.next();
         String dayOfMonthS = rsdD.getDetailValue();
         if(RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.WEEK_DAY.
                               equals(rsdD.getReportScheduleDetailCd())) {
           int dayOfWeek = Integer.parseInt(rsdD.getDetailValue());
           if(dayOfWeek==currDayOfWeek) {
             foundFl = true;
             break;
           }
         }
      }
      if(!foundFl) {
        return false;
      }

      GregorianCalendar wrkCalendar = (GregorianCalendar) currCalendar.clone();
      wrkCalendar.set(GregorianCalendar.DAY_OF_MONTH,1);
      int dayOfWeek = wrkCalendar.get(GregorianCalendar.DAY_OF_WEEK);
      int nextWeekShift = 7-(dayOfWeek-1);
      int currWeek = 1;
      int currDay = currCalendar.get(GregorianCalendar.DAY_OF_WEEK);
      int currMonth = currCalendar.get(GregorianCalendar.MONTH);
      boolean lastWeekFl = false;
      wrkCalendar.add(GregorianCalendar.DATE,nextWeekShift);
      for(; currWeek<6; currWeek++){
         int wrkDay = wrkCalendar.get(GregorianCalendar.DAY_OF_MONTH);
         int wrkMonth = wrkCalendar.get(GregorianCalendar.MONTH);
         if(currMonth<wrkMonth) {
           lastWeekFl = true;
           break;
         }
         if( currDay<wrkDay){
           break;
         }
         wrkCalendar.add(GregorianCalendar.DATE,7);
      }

      iter = pScheduleDetails.iterator();
      while(iter.hasNext()) {
         ReportScheduleDetailData rsdD = (ReportScheduleDetailData) iter.next();
         String dayOfMonthS = rsdD.getDetailValue();
         if(!RefCodeNames.REPORT_SCHEDULE_DETAIL_CD.MONTH_WEEK.
                               equals(rsdD.getReportScheduleDetailCd())) {
           continue;
         }
         int weekOfMonth = Integer.parseInt(rsdD.getDetailValue());
         if(weekOfMonth==currWeek) {
           return true;
         }
         if(weekOfMonth>=5 && lastWeekFl) {
           return true;
         }
       }
       return false;
     }
     return false;
   }

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
   throws RemoteException
   {
     String errorMess = "No error";
     int sentQty = 0;
     try {
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       Date modDate = pReportRes.getModDate();


       String subj = "There is a new "+pReportRes.getReportName()+
         " available. Generated date: "+sdf.format(modDate);


       APIAccess factory = APIAccess.getAPIAccess();
       User userEjb = factory.getUserAPI();
       Group groupEjb = factory.getGroupAPI();


       if(pGroupIds!=null) {
         for(int ii=0; ii<pGroupIds.size(); ii++) {
           Integer idI = (Integer)  pGroupIds.get(ii);
           int id = idI.intValue();
           GroupData gD = groupEjb.getGroupDetail(id);
           String groupStatus = gD.getGroupStatusCd();
           if(!RefCodeNames.GROUP_STATUS_CD.ACTIVE.equals(groupStatus)) {
             continue;
           }
           GroupSearchCriteriaView gsCrit = GroupSearchCriteriaView.createValue();
           gsCrit.setGroupId(id);
           UserDataVector uDV =
              groupEjb.getUsersForGroup(gsCrit,Group.NAME_EXACT,Group.ORDER_BY_ID);
           for(int jj=0; jj<uDV.size(); jj++) {
             UserData uD = (UserData) uDV.get(jj);
             if(RefCodeNames.USER_STATUS_CD.ACTIVE.equals(uD.getUserStatusCd())){
               UserInfoData uiD = userEjb.getUserContact(uD.getUserId());
               EmailData emailD = uiD.getEmailData();
               if(emailD!=null && Utility.isSet(emailD.getEmailAddress())){
                 int qty = sendEmail(emailD.getEmailAddress(),subj, subj, uD.getUserId()) ;
                 sentQty += qty;
               }
             }
           }
         }
       }
       if(pUserIds!=null) {
         for(int ii=0; ii<pUserIds.size(); ii++) {
           Integer idI = (Integer) pUserIds.get(ii);
           UserInfoData uiD = userEjb.getUserContact(idI.intValue());
           if(uiD!=null) {
             String userStatusCd = uiD.getUserData().getUserStatusCd();
             if(RefCodeNames.USER_STATUS_CD.ACTIVE.equals(userStatusCd)) {
               EmailData emailD = uiD.getEmailData();
               if(emailD!=null && Utility.isSet(emailD.getEmailAddress())){
                 int qty = sendEmail(emailD.getEmailAddress(),subj, subj, idI.intValue()) ;
                 sentQty += qty;
               }
             }
           }
         }
       }
       return sentQty;
     }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
   }
   }

   /**
   * Sends report failure notification
   *
   * @param pReportRes report result object
   * @param pErrorMessAL list of error messages
   * @throws   RemoteException Required by EJB 1.0
   */
   public void sendReportErrorNotification(ReportResultData pReportRes,
                                      ArrayList pErrorMessAL)
   throws RemoteException
   {
     String errorMess = "No error";
     int sentQty = 0;
     try {
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
       Date addDate = pReportRes.getAddDate();


       String subj = "Report failure notification. Report name: "+pReportRes.getReportName();

       String text = "Report "+pReportRes.getReportName()+
         " failed. Run date: "+sdf.format(addDate);
       for(int ii=0; ii<pErrorMessAL.size(); ii++) {
         errorMess = (String) pErrorMessAL.get(ii);
         text += "\r\n";
         text += errorMess;
       }
       APIAccess factory = APIAccess.getAPIAccess();
       EmailClient emailClient= factory.getEmailClientAPI();
       String emailAddr = emailClient.getDefaultEmailAddress();
       if(emailAddr!=null && emailAddr.trim().length()>0) {
          sendEmail(emailAddr,subj, text,0) ;
       }
     }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
   }
   }


   //-----------------------------------------------------------------------------
   private int sendEmail(String pToEmailAddress, String pSubject, String pMsg, int pUserId)
   {

        APIAccess factory = null;
        EmailClient emailClientEjb = null;
        int sentQty = 0;
        try {
            factory = APIAccess.getAPIAccess();
            emailClientEjb = factory.getEmailClientAPI();
        } catch (Exception exc) {

            String mess = "ReportBean.sendEmail. No API access";
            logError(mess);

            return sentQty;
        }

        try {
            boolean isSent = emailClientEjb.wasThisEmailSent(pSubject, pToEmailAddress);
            if (isSent == false) {
                emailClientEjb.send(pToEmailAddress,
				    emailClientEjb.getDefaultEmailAddress(),
				    pSubject, pMsg, null,0,pUserId);
                sentQty++;
            }
        } catch (Exception exc) {

            String mess = "ReportBean.sendEmail: " + exc.getMessage();
            logError(mess);

            return sentQty;
        }
        return sentQty;

    }

   /**
   * Sets report result protected flag Y - yes, N - no
   *
   * @param pReportResultId report result id
   * @param pVal value to set (Y or N)
   * @param pUser user name
   * @throws   RemoteException Required by EJB 1.0
   */
   public int setReportResultProtection(int pReportResultId, String pVal, String pUser)
   throws RemoteException
   {
     Connection analyticCon = null;
     String errorMess = "No error";
     try {
     analyticCon = getAnalyticConnection();

     ReportResultData rrD =
                     ReportResultDataAccess.select(analyticCon,pReportResultId);
     rrD.setProtectedFl(pVal);
     rrD.setModBy(pUser);
     log.info("ReportBean RRRRRRRRRRRRRRRRRRRRRRRRRRR rrD: "+rrD);
     ReportResultDataAccess.update(analyticCon,rrD);
     return rrD.getReportResultId();
     }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
     closeConnection(analyticCon);
   }
   }

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
   throws RemoteException
   {
     String errorMess = "No error";
     int sentQty = 0;
     try {
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       Date modDate = pReportRes.getModDate();


       String subj = "Report: "+pReportRes.getReportName()+
         ". Generated date: "+sdf.format(modDate);


       APIAccess factory = APIAccess.getAPIAccess();
       User userEjb = factory.getUserAPI();
       Group groupEjb = factory.getGroupAPI();
       Event eventEjb = factory.getEventAPI();
       GenericReportResultViewVector results = readArchiveReport(pReportRes.getReportResultId());
       boolean hasData = false;
       Iterator it = results.iterator();
       while (it.hasNext()) {
         GenericReportResultView result = (GenericReportResultView)it.next();
         if (result.getTable() != null && result.getTable().size() > 0) {
           hasData = true;
           break;
         }
       }
       if (pSuppressEmailFlag && !hasData) {
         return sentQty; //0
       }
	   ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	   ReportWriterUtil.writeExcelReportMulti(results,outStream);

       byte[] attachByte = outStream.toByteArray();
       FileAttach file = new FileAttach("Attachment.xls", attachByte, "xls", attachByte.length);
       FileAttach[] fileAttachA = new FileAttach[1];
	   fileAttachA[0] = file;

       if(pGroupIds!=null) {
         for(int ii=0; ii<pGroupIds.size(); ii++) {
           Integer idI = (Integer)  pGroupIds.get(ii);
           int id = idI.intValue();
           GroupData gD = groupEjb.getGroupDetail(id);
           String groupStatus = gD.getGroupStatusCd();
           if(!RefCodeNames.GROUP_STATUS_CD.ACTIVE.equals(groupStatus)) {
             continue;
           }
           GroupSearchCriteriaView gsCrit = GroupSearchCriteriaView.createValue();
           gsCrit.setGroupId(id);
           UserDataVector uDV =
              groupEjb.getUsersForGroup(gsCrit,Group.NAME_EXACT,Group.ORDER_BY_ID);
           for(int jj=0; jj<uDV.size(); jj++) {
             UserData uD = (UserData) uDV.get(jj);
             if(RefCodeNames.USER_STATUS_CD.ACTIVE.equals(uD.getUserStatusCd())){
               UserInfoData uiD = userEjb.getUserContact(uD.getUserId());
               EmailData emailD = uiD.getEmailData();
               if(emailD!=null && Utility.isSet(emailD.getEmailAddress())){
                  createEmailEvent(eventEjb, emailD.getEmailAddress(), subj, subj, fileAttachA);
				  sentQty ++;
               }
             }
           }
         }
       }
       if(pUserIds!=null) {
         for(int ii=0; ii<pUserIds.size(); ii++) {
           Integer idI = (Integer) pUserIds.get(ii);
           UserInfoData uiD = userEjb.getUserContact(idI.intValue());
           if(uiD!=null) {
             String userStatusCd = uiD.getUserData().getUserStatusCd();
             if(RefCodeNames.USER_STATUS_CD.ACTIVE.equals(userStatusCd)) {
               EmailData emailD = uiD.getEmailData();
               if(emailD!=null && Utility.isSet(emailD.getEmailAddress())){
                  createEmailEvent(eventEjb, emailD.getEmailAddress(), subj, subj, fileAttachA);
				  sentQty ++;
               }
             }
           }
         }
       }
       return sentQty;
     }catch (Exception exc) {
     errorMess = "Error. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
   }
   }

   private void createEmailEvent(Event eventEjb, String addressTo, String subj, String msg, FileAttach[] attach)
   throws Exception {
        EventData eventData = EventData.createValue();
        eventData.setStatus(Event.STATUS_READY);
        eventData.setType(Event.TYPE_EMAIL);
        eventData.setAttempt(1);

        EventEmailDataView eventEmailData = new EventEmailDataView();
        eventEmailData.setEventEmailId(0);
        eventEmailData.setToAddress(addressTo);
        eventEmailData.setFromAddress("ReportScheduler");
        eventEmailData.setSubject(subj);
        eventEmailData.setText(msg);
        eventEmailData.setAttachments(attach);
        eventEmailData.setEventId(eventData.getEventId());
        eventEmailData.setEmailStatusCd(Event.STATUS_READY);
        eventEmailData.setModBy("ReportScheduler");
        eventEmailData.setAddBy("ReportScheduler");

        EventEmailView eev = new EventEmailView(eventData, eventEmailData);
        eventEjb.addEventEmail(eev, "ReportScheduler");
   }

   private String  getListOfNamesDW (Connection con, String controlName, String idsS){

     StringBuffer nms = new StringBuffer();
     String typeDim = "";
     String tableName = "";
     if (controlName.contains("LOCATE_ACCOUNT_MULTI")) {
       typeDim = "ACCOUNT_NAME";
       tableName = "ACCOUNT";
     } else if (controlName.contains("LOCATE_SITE_MULTI")) {
       typeDim = "SITE_NAME";
       tableName = "SITE";
     } else if (controlName.contains("LOCATE_DISTRIBUTOR")) {
       typeDim = "JD_DIST_NAME";
       tableName = "DISTRIBUTOR";
     } else if (controlName.contains("LOCATE_MANUFACTURER")) {
       typeDim = "JD_MANUF_NAME";
       tableName = "MANUFACTURER";
     } else if (controlName.contains("LOCATE_ITEM")) {
       typeDim = "JD_ITEM_DESC";
       tableName = "ITEM";
     }

     if (Utility.isSet(idsS)) {
       try {
         String sql =
           " select " + typeDim + " from DW_" + tableName + "_DIM where " + tableName + "_DIM_ID \n" +
           " in (" + idsS + ") order by " + typeDim ;

         log("getListOfNamesDW()--->SQL: \n" + sql);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
           String desc = new String(rs.getString(1));
           if (nms.length() == 0) {
             nms.append(desc);
           } else {
             nms.append(":" + desc);
           }
       }
       stmt.close();
     }
     catch (SQLException exc) {
       exc.printStackTrace();
     }
   }
   return nms.toString();
 }

 private String  getListOfNames (Connection con, String controlName, String idsS ){
   String subSql = "";
   if (controlName.contains("LOCATE_ITEM")) {
     subSql = " select SHORT_DESC from CLW_ITEM where ITEM_ID \n";
   } else {
     subSql = " select SHORT_DESC from CLW_BUS_ENTITY where BUS_ENTITY_ID \n";
   }
   StringBuffer nms = new StringBuffer();
   if (Utility.isSet(idsS)) {
     try {
       String sql = subSql + " in (" + idsS + ") order by SHORT_DESC";

       log.info("getListOfNames()-------->SQL: \n" + sql);
       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       while (rs.next()) {
         String desc = new String(rs.getString(1));
         if (nms.length() == 0){
           nms.append(desc);
         } else {
           nms.append(":" + desc);
         }
       }
       stmt.close();
     }
     catch (SQLException exc) {
       exc.printStackTrace();
     }
   }
   return nms.toString();
 }


   public Object getLocateFilterByIds(String controlName, String controlValue ) throws RemoteException {
     Connection mainCon = null;
     Connection reportCon = null;
     String errorMess = "No error";
     try {
       mainCon = getConnection();
       reportCon = getReportConnection();

       String filterNames = "";
       if (controlName.startsWith("DW_")) {
         filterNames = getListOfNamesDW(reportCon, controlName, controlValue);
       }
       else {
         filterNames = getListOfNames(mainCon, controlName, controlValue);
       }
       log("getLocateFilterByIds() :: filterNames = " + filterNames);
       String[] ids = (Utility.isSet(controlValue)) ? controlValue.split(","):null;
       String[] desc = (Utility.isSet(filterNames)) ? filterNames.split(":"):null;
       if (ids != null && desc != null) {
         if (controlName.contains("LOCATE_MANUFACTURER")) {
           ManufacturerDataVector manufV = new ManufacturerDataVector();
           for (int i = 0; i < ids.length; i++) {
//             ManufacturerData manuf = ManufacturerData.createValue();
             BusEntityData be = BusEntityData.createValue();
             be.setBusEntityId(Integer.parseInt(  ids[i]));
             be.setShortDesc( desc[i]);
             ManufacturerData manuf = new ManufacturerData(be, 0, null,
                 null, null, null, null, null, null, null, null,null, null, null);
             manufV.add(manuf);
           }
           return manufV;
         } else  if (controlName.contains("LOCATE_DISTRIBUTOR")) {
           DistributorDataVector distV = new DistributorDataVector();
           for (int i = 0; i < ids.length; i++) {
             DistributorData dist = DistributorData.createValue();
             BusEntityData be = BusEntityData.createValue();
             be.setBusEntityId(Integer.parseInt(  ids[i]));
             be.setShortDesc(  desc[i]);
             dist.setBusEntity(be);
             distV.add(dist);
           }
           return distV;
         } else  if (controlName.contains("LOCATE_ITEM")) {
           ItemViewVector itemV = new ItemViewVector();
           for (int i = 0; i < ids.length; i++) {
             ItemView item = ItemView.createValue();
             item.setItemId(Integer.parseInt( ids[i]));
             item.setName(  desc[i]);
             itemV.add(item);
           }
           return itemV;
         } else if (controlName.contains("LOCATE_ACCOUNT_MULTI")) {
log ("getLocateFilterByIds() :: ids.length = " + ids.length + ", desc.length = " + desc.length);
           AccountUIViewVector accountV = new AccountUIViewVector();
           for (int i = 0; i < ids.length; i++) {
             AccountUIView account = AccountUIView.createValue();
             BusEntityData be = BusEntityData.createValue();
             log("getLocateFilterByIds() :: ids["+i+"]= " + ids[i] + ", desc["+i+"]= " + desc[i]);
             be.setBusEntityId(Integer.parseInt(  ids[i]));
             be.setShortDesc(  desc[i]);
             account.setBusEntity(be);
             accountV.add(account);
           }
           return accountV;
         } else if (controlName.contains("LOCATE_SITE_MULTI")) {
           SiteViewVector siteV = new SiteViewVector();
           for (int i = 0; i < ids.length; i++) {
             SiteView site = SiteView.createValue();
             site.setId(Integer.parseInt(  ids[i]));
             site.setName(  desc[i]);
             siteV.add(site);
           }
           return siteV;
         }
       }
     }catch (Exception exc) {
       errorMess = "Error. "+exc.getMessage();
       exc.printStackTrace();
       logError(errorMess);
       throw new RemoteException(errorMess);
     }finally {
       closeConnection(mainCon);
       closeConnection(reportCon);
     }
     return null;
   }


   private void log(String message){
        log.info("[ReportBean] :: " + message);
    }


   public Object checkInputDate(String pDate, String name, String dateFmt) throws RemoteException { // SVC

	   String errMess = "";

	   // implement some initial Date checks of the passed date
	   // check the structure of the passed date using Java regex
	   // date may have any format
	   /***
	   String p = "\\d{2}/\\d{2}/\\d{4}";
	   if(!pDate.matches(p))
	   {
		  errMess = "Date " + pDate + " is wrong. Right Date Format is: " + dateFmt;
		  return (Object) errMess;
	   }
	   ***/
       Connection con = null;


       log.info("********SVC: pDate = " + pDate);
       log.info("********SVC: name = " + name);
       log.info("********SVC: dateFmt = " + dateFmt);
       PreparedStatement stmt = null;
       ResultSet rs = null;

       try {
           //compare passed date with the Date from Oracle "dual" table
    	   try {
             con = getConnection();
    	   } catch (Exception e) {
    		   log.info("Attempt to make a DB connection failed.");
    		   errMess = "Attempt to make a DB connection failed.";
    		   return errMess;
    	   }

    	   //Example: select to_date('01/01/2000','dd/MM/yyyy') FROM dual;
           String sql="SELECT to_date('" + pDate + "','"  + dateFmt + "') FROM dual";
           log.info("*********SVC: constructed sql = " + sql);
           stmt = con.prepareStatement(sql);
           rs=stmt.executeQuery();

           rs.close();
           stmt.close();
       } catch (SQLException e){

    	     //process Oracle error message: delete "\n" and Oracle error code: ORA-xxxxx
    	     String oraInitErrorMessage = e.getMessage();

    		 String patternStr;
    		 Pattern pattern;
    		 Matcher matcher;

    		 patternStr = "ORA-\\d+:";
    	     String replacementStr = "";
    	     // Compile regular expression
    	     pattern = Pattern.compile(patternStr);
    	     // Replace all occurrences of pattern in ... and put the result in ...
    	     matcher = pattern.matcher(oraInitErrorMessage);
    	     String oraErrorMessage1 = matcher.replaceAll(replacementStr);
    	     log.info("***********SVC: oraErrorMessage1 = " + oraErrorMessage1);

    	     String oraErrorMessageToSend = oraErrorMessage1.trim();

    	     errMess = "[ " + pDate + " ] : " + oraErrorMessageToSend;
             log.info("***********SVC: mesg = " + errMess);
             return (Object) errMess;
       } finally {
           closeConnection(con);
       }
       return (Object) errMess;
   }

   public Object getInstanceReport(GenericReportData pReportData)
       throws RemoteException
   {
     String className = pReportData.getClassname();
     if (!Utility.isSet(className) ) {
       return null;
     }
     Object ret = null;
     try{
      Class clazz = Class.forName(className);
      log.info("[ReportBean].getInstanceReport GGGGGGGGGGGGGGG  clazz = " + clazz.getSimpleName());
      ret =  clazz.newInstance();
    }catch(ClassNotFoundException e){
       throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" could not be found.");
     }catch(ClassCastException e){
       throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" must implement GenericReport.");
     } catch (Exception e) {
       log.error(e.getMessage(),e);
       throw new RemoteException(e.getMessage(),e);
     }

     return ret;
   }
 }



