package com.cleanwise.service.api.session;

/**
 * Title:        ReportOrderBean
 * Description:  Bean implementation for ReportOrder Session Bean
 * Purpose:      Ejb for interface with ReportOrder Ejbs
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import javax.naming.NamingException;
import com.cleanwise.service.api.util.*;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.reporting.GenericReport;
import com.cleanwise.service.api.reporting.GenericReportMulti;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.text.DateFormat;
import java.math.BigDecimal;
import javax.naming.Context;

import javax.naming.*;

import org.apache.log4j.Category;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import java.io.IOException;


public class ReportOrderBean extends ReportAPI
{
  /**
   *
   */
 public void ejbCreate() throws CreateException, RemoteException {}


   /**
   * Gets list of generic report categories
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   private ArrayList getGenericReportCategories()
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
     errorMess = "Error. ReportOrderBean.getGenericReportCategories() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     try {
       if(con!=null) con.close();
     }
     catch (Exception exc) {
       throw new RemoteException(errorMess);
     }
   }
  return categories;
  }

   /**
   * Gets list of generic report names
   * @param pCategory category name. If null or empty returns all report names
   * @return collection of  String objects
   * @throws   RemoteException Required by EJB 1.0
   */
   private ArrayList getGenericReportNames(String pCategory)
   throws RemoteException
   {
   Connection con = null;
   ArrayList names = new ArrayList();
   String errorMess = "No error";
   ShipToViewVector shipTos = new  ShipToViewVector();
   try {
     con = getConnection();
     String sql = "Select name from clw_generic_report";
     if(pCategory!=null && pCategory.trim().length()>0) {
        sql += " where category = '"+pCategory+"'";
     }
     sql += " order by upper(name) ";
     Statement stmt = con.createStatement();
     ResultSet rs = stmt.executeQuery(sql);
     while (rs.next() ) {
       String name = rs.getString("name");
       names.add(name);
     }
     rs.close();
     stmt.close();
   }
   catch (Exception exc) {
     errorMess = "Error. ReportOrderBean.getGenericReportNames() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     try {
       if(con!=null) con.close();
     }
     catch (Exception exc) {
       throw new RemoteException(errorMess);
     }
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
   private GenericReportControlViewVector getGenericReportControls(String pCategory, String pName)
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
     GenericReportDataVector reports = GenericReportDataAccess.select(con,dbc);
     int count = reports.size();
     if(count>1 && pCategory==null) {
       pCategory = "CUSTOMER";
       dbc.addEqualToIgnoreCase(GenericReportDataAccess.CATEGORY,pCategory);
       reports = GenericReportDataAccess.select(con,dbc);
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
     GenericReportData report = (GenericReportData) reports.get(0);
     String repTxtCl = report.getSqlText();
     String repTxt;
     //if(RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(report.getGenericReportType())){
         if(repTxtCl != null){
            repTxt = repTxtCl.substring(1,((int) repTxtCl.length()));
         }else{
             repTxt = "";
         }
     //}else{
     if(report.getSupplementaryControls()!= null){
         repTxt = repTxt + report.getSupplementaryControls();
     }
     //}

     String repScriptCl = report.getScriptText();
     String repScript = (repScriptCl==null)?null:repScriptCl.substring(1, ((int) repScriptCl.length()));
     String token = report.getParameterToken();

     if(repTxt==null || repTxt.trim().length()==0) {
         if(RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(report.getGenericReportType())){
            throw new Exception("No report sql found. Category: "+pCategory+" Report Name: "+pName);
         }else{
            return criteriaControls;
         }
     }
     criteriaControls = createGenericReportControls(criteriaControls, repTxt, token, pCategory, pName);
     if(repScript!=null && repScript.trim().length()>0) {
       criteriaControls = createGenericReportControls(criteriaControls, repScript, token, pCategory, pName);
     }
   }
   catch (Exception exc) {
     errorMess = "Error. ReportOrderBean.getGenericReportControls() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }
   finally {
     closeConnection(con);
   }
  return criteriaControls;
  }

  private GenericReportControlViewVector createGenericReportControls(GenericReportControlViewVector pControls,
        String pText, String pToken, String pCategory, String pName)
  throws Exception
  {
     String token = pToken;
     if(token==null || token.trim().length()==0) {
       token = "#";
     }
     if(pText==null || pText.trim().length()==0) {
       throw new Exception("No report sql found. Category: "+pCategory+" Report Name: "+pName);
     }
     //Prepare controls
     int ind = pText.indexOf(token);
     while (ind >=0) {
       int ind1 = pText.indexOf(token,ind+1);
       if(ind1<=ind) {
         throw new Exception("No closing token found. Category: "+pCategory+" Report Name: "+pName+" Token: "+token);
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

  private GenericReportControlView parseGenericReportControl(String pControl) {
    GenericReportControlView grc = GenericReportControlView.createValue();
    grc.setSrcString(pControl);
    int posType = pControl.indexOf('(');
    if(posType<0) {
    grc.setName(pControl.trim());
    } else{
      grc.setName(pControl.substring(0,posType).trim());
      int posEnd = pControl.indexOf(')',posType+1);
      if(posEnd<0) posEnd = pControl.length();
      int posMndFl = pControl.indexOf(',',posType+1);
      if(posMndFl<0) {
        grc.setType(pControl.substring(posType+1,posEnd).trim());
      } else {
        grc.setType(pControl.substring(posType+1,posMndFl).trim());
        int posLabel = pControl.indexOf(',',posMndFl+1);
        if(posLabel<0) {
          grc.setMandatoryFl(pControl.substring(posMndFl+1,posEnd).trim());
        } else {
          grc.setMandatoryFl(pControl.substring(posMndFl+1,posLabel).trim());
          int posPr = pControl.indexOf(',',posLabel+1);
          if(posPr<0) {
            grc.setLabel(pControl.substring(posLabel+1,posEnd).trim());
          } else {
            grc.setLabel(pControl.substring(posLabel+1,posPr).trim());
            int posDefault = pControl.indexOf(',',posPr);
            if(posDefault<0) {
              grc.setPriority(pControl.substring(posPr+1,posEnd).trim());
            } else {
              grc.setPriority(pControl.substring(posPr+1,posDefault).trim());
              //Default is the last parameter
              grc.setDefault(pControl.substring(posDefault,posEnd).trim());
            }
          }
        }
      }
    }
    return grc;
  }


   /**
   * Gets list of generic report criteria control names
   *
   * @param pCategory report category
   * @param pName report name.
   * @param pParams report parameters
   * @return report table
   * @throws   RemoteException Required by EJB 1.0
   */
   private GenericReportResultView processGenericReport(String pCategory, String pName, Map pParams)
   throws RemoteException
   {
       DBConnections dbcon = null;

   String errorMess = "No error";
   try {
	   dbcon = new DBConnections();

     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(GenericReportDataAccess.CATEGORY,pCategory);
     dbc.addEqualTo(GenericReportDataAccess.NAME,pName);
     GenericReportDataVector reports = GenericReportDataAccess.select
	 (dbcon.mainCon,dbc);
     int count = reports.size();
     if(count==0) {
       throw new Exception("No generic report found. Category: "+pCategory+" Report Name: "+pName);
     }
     if(count>1) {
       throw new Exception(count+" generic reports found. Category: "+pCategory+" Report Name: "+pName);
     }
     GenericReportData report = (GenericReportData) reports.get(0);
     String schemaCds = report.getReportSchemaCd();
     String reportType = report.getGenericReportType();
	   dbcon.mkCons(schemaCds);

     if(!dbcon.usingExistingCon){
        dbcon.mainCon.close();
        dbcon.mainCon = null;
     }

     if(RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(reportType)){
         return processSqlGenericReport(dbcon, report, pParams);
     } else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL.equals(reportType)
	       ){
	 GenericReportResultViewVector v = processSqlGenericReportMulti
	     (dbcon, report, pParams);
	 return (GenericReportResultView)v.get(0);

     }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS.equals(reportType)){
         return processJavaGenericReport(dbcon, report, pParams);
     }else{
         throw new RemoteException("report type is not valid: "+reportType);
     }
   }catch (Exception exc) {
     errorMess = "Error. ReportOrderBean.processGenericReport() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
       if ( null != dbcon ) {
	   dbcon.closeAll();
       }
   }
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
   private GenericReportResultViewVector processGenericReportMulti(String pCategory, String pName, Map pParams)
   throws RemoteException
   {
   GenericReportResultViewVector results = new GenericReportResultViewVector();

       DBConnections dbcon = null;
   String errorMess = "No error";
   try {
	   dbcon = new DBConnections();

     DBCriteria dbc = new DBCriteria();
     String category = pCategory;
     if(category!=null) {
       dbc.addEqualToIgnoreCase(GenericReportDataAccess.CATEGORY,category);
     }
     dbc.addEqualTo(GenericReportDataAccess.NAME,pName);
     GenericReportDataVector reports = GenericReportDataAccess.select(dbcon.mainCon,dbc);
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
     String schemaCds = report.getReportSchemaCd();
	   dbcon.mkCons(schemaCds);
     String reportType = report.getGenericReportType();
     if(!dbcon.usingExistingCon){
        dbcon.mainCon.close();
        dbcon.mainCon = null;
     }

     if(RefCodeNames.GENERIC_REPORT_TYPE_CD.SQL.equals(reportType)
        ){
         results.add(processSqlGenericReport(dbcon, report, pParams));
     } else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.XSQL.equals(reportType)) {
	 results.addAll(processSqlGenericReportMulti
			(dbcon, report, pParams));
     }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS.equals(reportType)){
         results.add(processJavaGenericReport(dbcon, report, pParams));
     }else if(RefCodeNames.GENERIC_REPORT_TYPE_CD.JAVA_CLASS_MULTI.equals(reportType)){
         results.addAll(processJavaGenericReportMulti(dbcon, report, pParams));
     }else{
         throw new RemoteException("report type is not valid: "+reportType);
     }
     return results;
   }catch (Exception exc) {
     errorMess = "Error. ReportOrderBean.processGenericReportMulti() Exception happened. "+exc.getMessage();
     exc.printStackTrace();
     logError(errorMess);
     throw new RemoteException(errorMess);
   }finally {
	   if ( null != dbcon ) {
	       dbcon.closeAll();
	   }
   }
  }

   private GenericReportResultView processJavaGenericReport
(DBConnections dbcon,GenericReportData pReportData,Map pParams)
  throws Exception
  {
      String className = pReportData.getClassname();
      GenericReport worker = null;
      try{
         Class clazz = Class.forName(className);
         worker = (GenericReport) clazz.newInstance();
      }catch(ClassNotFoundException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" could not be found.");
      }catch(ClassCastException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" must implement GenericReport.");
      }
      ConnectionContainer conns = new ConnectionContainer(dbcon.lawCon,dbcon.reportCon,dbcon.mainCon, dbcon.stagedInfoCon);
      GenericReportResultView ret = worker.process(conns, pReportData, pParams);
      if(ret == null){
          logInfo("Java worket object returned null.");
          ret = GenericReportResultView.createValue();
      }
      return ret;
  }

  private GenericReportResultViewVector processJavaGenericReportMulti
      (DBConnections dbcon,GenericReportData pReportData,Map pParams)
  throws Exception
  {
      String className = pReportData.getClassname();
      GenericReportMulti worker = null;
      try{
         Class clazz = Class.forName(className);
         worker = (GenericReportMulti) clazz.newInstance();
      }catch(ClassNotFoundException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" could not be found.");
      }catch(ClassCastException e){
          throw new RemoteException("Configured class: "+className+" for report category: "+pReportData.getName()+" and report name: " + pReportData.getName()+" must implement GenericReport.");
      }
      ConnectionContainer conns = new ConnectionContainer(dbcon.lawCon,dbcon.reportCon,dbcon.mainCon, dbcon.stagedInfoCon);
      GenericReportResultViewVector ret = worker.process(conns, pReportData, pParams);
      if(ret == null){
          logInfo("Java worket object returned null.");
          ret = new GenericReportResultViewVector();
      }
      return ret;
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
      String repTxtCl = report.getSqlText();
     String repTxt = repTxtCl.substring(1,((int) repTxtCl.length()));
     String repScriptCl = report.getScriptText();
     String repScript = (repScriptCl==null)?null:repScriptCl.substring(1, ((int) repScriptCl.length()));
     String token = report.getParameterToken();
     String resultTable = report.getInterfaceTable();
     String name = report.getName();
     String category = report.getCategory();

      if(repTxt==null || repTxt.trim().length()==0) {
       throw new Exception("No report sql found. Category: "+category+" Report Name: "+name);
     }
     //Prepare sql
     String repSql = createGenericReportSql(repTxt, token, pParams,category, name);
     String scriptSql = null;
     if(repScript!=null && repScript.trim().length()>0) {
        scriptSql = createGenericReportSql(repScript, token, pParams,category, name);
     }
    //Exequte reprort
     Statement stmt = con.createStatement();
     ResultSet rs = null;
     if(resultTable!=null && resultTable.trim().length()>0) {
       String truncateSql =  "truncate table "+resultTable;
       stmt.execute(truncateSql); //Truncate interface table
     }
     if(scriptSql!=null && scriptSql.trim().length()>0) {
       stmt.execute(scriptSql);  //Execute script
     }
     rs = stmt.executeQuery(repSql);  //Execute script
     Utility.parseResultSetDataForReport(rs, result);
     rs.close();
     stmt.close();
     return result;
  }

  private String createGenericReportSql(String pText, String pToken, Map pParams, String pCategory, String pName)
  throws Exception
  {
     String token = pToken;
     if(token==null || token.trim().length()==0) {
       token = "#";
     }
     token = token.trim();
     int tokenLen = token.length();

     //Prepare sql
     String repSql = "";

     // Search for the standard token in the SQL.
     String accessSQLToken = token + "ACCESS_TO_SITES" + token;
     logDebug("looking for special token:"+accessSQLToken);
     int ind = pText.indexOf(accessSQLToken);
     logDebug("1 repSql=" + repSql);
     if ( ind >= 0 ) {
       repSql += pText.substring(ind, ind+accessSQLToken.length());
     }
     logDebug("2 repSql=" + repSql);

     ind = pText.indexOf(token);
     int ind1 = -tokenLen;
     while (ind >=0) {
       repSql += pText.substring(ind1+tokenLen, ind);
       ind1 = pText.indexOf(token,ind+1);
       if(ind1<=ind) {
         throw new Exception("No closing token found. Category: "+pCategory+" Report Name: "+pName+" Token: "+token);
       }
       String paramName = pText.substring(ind+1,ind1);
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

     logDebug("final report sql, repSql=" + repSql);

     return repSql;
  }

  private GenericReportResultViewVector processSqlGenericReportMulti
      (DBConnections dbcon,
       GenericReportData report,Map pParams)
      throws Exception {

        GenericReportResultViewVector res =
            new GenericReportResultViewVector();

        // xsql
        String repTxtCl = report.getSqlText();
        String repTxt = repTxtCl.substring(1,((int) repTxtCl.length()));
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

            String repSql = createGenericReportSql(repTxt,
                                                   token,
                                                   pParams,
                                                   category,
                                                   name);

            Statement stmt = null;
	    Connection activec = dbcon.getActiveConnection();
	    stmt = activec.createStatement();

            ResultSet rs = null;
            if(resultTable!=null && resultTable.trim().length()>0) {
                String truncateSql =  "truncate table "+resultTable;
                stmt.execute(truncateSql); //Truncate interface table
            }
            rs = stmt.executeQuery(repSql);  //Execute script
            GenericReportResultView result =
                GenericReportResultView.createValue();
            Utility.parseResultSetDataForReport(rs, result);
            result.setName(xsql.getName());
            rs.close();
            stmt.close();
            res.add(result);
        }
        // xsql

        return res;
    }

}



