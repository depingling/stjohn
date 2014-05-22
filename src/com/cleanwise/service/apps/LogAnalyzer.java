package com.cleanwise.service.apps;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;

import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.view.utils.ReportWritter;

import org.apache.log4j.Logger;

/**
 * This application picks up customerinvoces and matches them against DB
 *
 * @author YKupershmidt
 */
public class LogAnalyzer 
{
     private static final Logger log = Logger.getLogger(LogAnalyzer.class);
     Report reportEjb = null;
    

    /**
     * Describe <code>setUp</code> method here.
     *Any added EJBs should also be added to the inbound and outbound translators
     *constructor so they can be used outside of the java main environment.
     */
    public void setUp() throws Exception {
        
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        
        Properties props = new Properties();
        props.load(new FileInputStream(propFileName) );
        
        InitialContext jndiContext = new InitialContext(props);
        /*
        Object ref;
        ref = jndiContext.lookup(JNDINames.REPORT_EJBHOME);
        
        ReportHome reportHome = (ReportHome)
        PortableRemoteObject.narrow(ref, ReportHome.class);
        
        reportEjb = reportHome.create();
         */
    }

    String[] template1 = {
      "Class: <",
      "> Action: <",
      "> Params: {",
      "} Started at: <",
      "> Session id : <",
      "> User: <",
      ">@@@@"
    };
    String[] template2 = {
      "Class: <",
      "> Action: <",
      "> Params: {",
      "} Started at: <",
      "> Ended at: <",
      "> Result: <",
      "> Duration: <",
      "> Session id : <",
      "> User: <",
      ">@@@@"
    };
    String[] template1http = {
      "Referer: <",
      "> Started at: <",
      "> Session id : <",
      "> User: <",
      ">@@##@@"
    };
    String[] template2http = {
      "Referer: <",
      "> Started at: <",
      "> Ended at: <",
      "> Result: <",
      "> Duration: <",
      "> Session id : <",
      "> User: <",
      ">@@##@@"
    };

    public void analyzeLog(String pFileName, Date pStartDate)
    throws Exception
    {
      String template = "[INFO,Default] @@@@";
      String templateHttp = "[INFO,Default] @@##@@";
      ArrayList openedActions = new ArrayList();
      ArrayList noUser = new ArrayList();
      ArrayList allActions = new ArrayList();
      FileReader fr = null;
      try {
        fr = new FileReader(pFileName);
      } catch (FileNotFoundException exc){
        String mess = "File does not exist: "+pFileName;
        throw new Exception(mess);
      }
      BufferedReader bufR = new BufferedReader(fr);
      int posType = template.length();
      int posTypeHttp = templateHttp.length();
      int recno=0;
      int maxParamsLen = 4000;
      
      for(String str = bufR.readLine(); str!=null; str = bufR.readLine()) {
         int pos = str.indexOf(templateHttp);
         if(pos>=0) {
           pos += posTypeHttp;
           int strLen = str.length();
           if(strLen<=pos) continue;
           char cc = str.charAt(pos);
           if(cc=='S'){
             UserAction ua = parseStartHttpMess(str, pStartDate);
             if(ua==null) continue;
             openedActions.add(ua);
             allActions.add(ua);
             if(ua.userName==null ||
                ua.userName.trim().length()==0 || 
                ua.userName.trim().equals("null")) {
               noUser.add(ua);
             } else {
               for(int jj=0; jj<noUser.size(); jj++) {
                  UserAction uaNoUser = (UserAction) noUser.get(jj);
                  if(ua.sessionId.equals(uaNoUser.sessionId)) {
                     uaNoUser.userName = ua.userName;
                     noUser.remove(jj);
                     break;
                  }
               }
             }
             
           } else if (cc=='E'){
             UserAction ua = parseEndHttpMess(str, pStartDate);
             if(ua==null) continue;
             String sessionId = ua.sessionId;
             String startDate = ua.httpStartDate;
             for(int jj=openedActions.size()-1; jj>=0; jj--){
               UserAction uaOpen = (UserAction) openedActions.get(jj);
               if(sessionId.equals(uaOpen.sessionId) &&
                  startDate.equals(uaOpen.httpStartDate)) {
                 uaOpen.httpDuration = ua.httpDuration;
                 uaOpen.httpEndDate = ua.httpEndDate;
                 uaOpen.httpResult = ua.httpResult;
                 openedActions.remove(jj);
                 break;
               }
             }
            
           }
           continue;
         }
         pos = str.indexOf(template);
         if(pos>=0) {
           pos += posType;
           int strLen = str.length();
           if(strLen<=pos) continue;
           char cc = str.charAt(pos);
           if(cc=='S'){
             UserAction ua = parseStartContMess(str, pStartDate);
             if(ua==null) continue;
/*
             if(ua.userName!=null ||
                ua.userName.trim().length()>0 || 
                !ua.userName.trim().equals("null")) {
               noUser.add(ua);
               for(int jj=0; jj<noUser.size(); jj++) {
                  UserAction uaNoUser = (UserAction) noUser.get(jj);
                  if(ua.sessionId.equals(uaNoUser.sessionId)) {
                     uaNoUser.userName = ua.userName;
                     noUser.remove(jj);
                     break;
                  }
               }
             }
 */
             String sessionId = ua.sessionId;
             for(int jj=openedActions.size()-1; jj>=0; jj--){
               UserAction uaOpen = (UserAction) openedActions.get(jj);
               if(sessionId.equals(uaOpen.sessionId)) {
                 uaOpen.action = ua.action;
                 uaOpen.className = ua.className;
                 uaOpen.params = ua.params;
                 uaOpen.startDate = ua.startDate;
                 uaOpen.userName = ua.userName;
                 break;
               }
             }
           } else if(cc=='E'){
             UserAction ua = parseEndContMess(str, pStartDate);
             if(ua==null) continue;
             String sessionId = ua.sessionId;
             String startDate = ua.startDate;
             for(int jj=0; jj<openedActions.size();jj++){
               UserAction uaOpen = (UserAction) openedActions.get(jj);
               if(sessionId.equals(uaOpen.sessionId) &&
                  startDate.equals(uaOpen.startDate)) {
                 uaOpen.endDate = ua.endDate;
                 uaOpen.duration = ua.duration;
                 uaOpen.result = ua.result;
                 break;
               }
             }
           }
         }
      }
      for(int ii=0; ii<allActions.size(); ii++) {
        if(ii%100==0) {
          UserAction ua = (UserAction)allActions.get(ii);
        }
      }
      log.info("############ Total: "+allActions.size());
      log.info("############ Opened: "+openedActions.size());
      log.info("############ noUser: "+noUser.size());
      
      //Output 
      FileOutputStream fos = new FileOutputStream("aaa.xls");
       GenericReportResultViewVector resultV = new GenericReportResultViewVector();
       GenericReportResultView actionPage = GenericReportResultView.createValue();
       actionPage.setTable(new ArrayList());
       for(int ii=0; ii<allActions.size(); ii++) {
          ArrayList row = new ArrayList();
          UserAction ua = (UserAction) allActions.get(ii);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Session Id",0,255,"VARCHAR2"));
          row.add(ua.sessionId);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","User Name",0,255,"VARCHAR2"));
          row.add(ua.userName);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action Class",0,255,"VARCHAR2"));
          row.add(ua.className);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action",0,255,"VARCHAR2"));
          row.add(ua.action);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Start Date",0,255,"VARCHAR2"));
          row.add(ua.httpStartDate);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Content Start Date",0,255,"VARCHAR2"));
          row.add(ua.startDate);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Content End Date",0,255,"VARCHAR2"));
          row.add(ua.endDate);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","End Date",0,255,"VARCHAR2"));
          row.add(ua.httpEndDate);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Content Result",0,255,"VARCHAR2"));
          row.add(ua.result);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Result",0,255,"VARCHAR2"));
          row.add(ua.httpResult);
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BidDecimal","Content Dur",5,20,"NUMBER"));
          BigDecimal durBD = null;
          try {
            durBD = new BigDecimal(ua.duration);

          } catch (Exception exc){}
          row.add(durBD);
//        header.add(ReportingUtils.createGenericReportColumnView("java.math.BidDecimal","Total Dur",5,20,"NUMBER"));
          BigDecimal tDurBD = null;
          try {
            tDurBD = new BigDecimal(ua.httpDuration);
          } catch (Exception exc){}
          row.add(tDurBD);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Referer",500,pParamLength,"VARCHAR2"));
          String referer = ua.referer;
          if(referer!=null && referer.length()>500) referer = referer.substring(0,500);          
          row.add(referer);
//        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Params",0,pParamLength,"VARCHAR2"));
          String params = ua.params;
          if(params!=null && params.length()>maxParamsLen) params = 
                                               params.substring(0,maxParamsLen);
          row.add(params);
          actionPage.getTable().add(row);
        }
        actionPage.setHeader(getHeader(maxParamsLen));
        actionPage.setColumnCount(getHeader(maxParamsLen).size());
        actionPage.setName("Actions");
        resultV.add(actionPage);
        ReportWritter.writeExcelReportMulti(resultV, fos); 
        fos.flush();
      
    }

    private UserAction parseStartHttpMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      UserAction ua = new UserAction();
      for(int ii=0; ii<template1http.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template1http[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
         switch (ii) {

//1  Referer: <http://localhost:8080/cleanwise/adminportal/distNoteMgr.do> 
//2   Started at: <04/30/2004 11:35:20:3520> 
//3   Session id : <1jg2ca0ek> 
//4   User: <yuriy>
          case 1:
             ua.referer = val;
             break;
          case 2:
             ua.httpStartDate = val;
             break;
          case 3:
             ua.sessionId = val;
             break;
          case 4:
             ua.userName = val;
             break;
         }
         pos += template1http[ii].length();
         posPrev = pos;
      }
      return ua;
    }
    
    private UserAction parseEndHttpMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      UserAction ua = new UserAction();
      for(int ii=0; ii<template2http.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template2http[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
         switch (ii) {

// 1 Referer: <http://localhost:8080/cleanwise/adminportal/distNoteMgr.do?action=loadTopic&topicId=321295> 
// 2 Started at: <04/30/2004 11:35:24:3524> 
// 3 Ended at: <04/30/2004 11:36:21:3621> 
// 4 Result: <OK> 
// 5 Duration: <56.942> 
// 6 Session id : <1jg2ca0ek> 
// 7 User: <yuriy>
          case 1:
             ua.referer = val;
             break;
          case 2:
             ua.httpStartDate = val;
             break;
          case 3:
             ua.httpEndDate = val;
             break;
          case 4:
             ua.httpResult = val;
             break;
          case 5:
             ua.httpDuration = val;
             break;
          case 6:
             ua.sessionId = val;
             break;
          case 7:
             ua.userName = val;
             break;
         }
         pos += template2http[ii].length();
         posPrev = pos;
      }
      return ua;
    }

    private UserAction parseStartContMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      UserAction ua = new UserAction();
      for(int ii=0; ii<template1.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template1[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
         switch (ii) {
          case 1:
             ua.className = val;
             break;
          case 2:
             ua.action = val;
             break;
          case 3:
             ua.params = val;
             break;
          case 4:
             ua.startDate = val;
             SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
             try{
               Date actionDate = sdf1.parse(ua.startDate);
               if(actionDate.before(pStartDate)) {
                 return null;
               }
             }catch(Exception exc){}
             break;
          case 5:
             ua.sessionId = val;
             break;
          case 6:   
             ua.userName = val;
         }
         pos += template1[ii].length();
         posPrev = pos;
      }
      return ua;
    }
    
    private UserAction parseEndContMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      UserAction ua = new UserAction();
      for(int ii=0; ii<template2.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template2[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
/*
1Class: <DistNoteMgrAction> 
2Action: <loadTopic> 
3Params: { topicId: <321295> action: <loadTopic>} 
4Started at: <04/30/2004 11:35:20:3520> 
5Ended at: <04/30/2004 11:35:21:3521> 
6Result: <OK> 
7Duration: <0.23> 
8Session id : <1jg2ca0ek> 
9User: <yuriy>@@@@
 */
         switch (ii) {
          case 1:
             ua.className = val;
             break;
          case 2:
             ua.action = val;
             break;
          case 3:
             ua.params = val;
             break;
          case 4:
             ua.startDate = val;
             SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
             try{
               Date actionDate = sdf1.parse(ua.startDate);
               if(actionDate.before(pStartDate)) {
                 return null;
               }
             }catch(Exception exc){}
             break;
          case 5:
             ua.endDate = val;
             break;
          case 6:
             ua.result = val;
             break;
          case 7:
             ua.duration = val;
             break;
          case 8:
             ua.sessionId = val;
             break;
          case 9:   
             ua.userName = val;
         }
         pos += template2[ii].length();
         posPrev = pos;
      }
      return ua;
    }

    private String[] initStringArray(String[] pSA){
      for(int ii=0; ii<pSA.length; ii++) {
        pSA[ii] = null;
      }
      return pSA;
    }

    private GenericReportColumnViewVector getHeader(int pParamLength) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Session Id",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","User Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action Class",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Action",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Start Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Content Start Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Content End Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","End Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Content Result",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Result",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BidDecimal","Content Dur",5,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BidDecimal","Total Dur",5,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Referer",500,pParamLength,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Params",0,pParamLength,"VARCHAR2"));
        return header;
    }
    ////////////////////////////////////////////////////////////////////////
    public class UserAction {
       String sessionId;
       String userName;
       String httpStartDate;
       String httpEndDate;
       String httpDuration;
       String httpResult;
       String referer;
       String className;
       String action;
       String startDate;
       String endDate;
       String result;
       String params;
       String duration;
       
       public String toString() {
         String ss = 
         "sessionId: "+sessionId +
         ", userName: "+userName +
         ", httpStartDate: "+httpStartDate +
         ", httpEndDate: "+httpEndDate +
         ", httpDuration: "+httpDuration +
         ", httpResult: "+httpResult +
         ", referer: "+referer +
         ", className: "+className +
         ", action: "+action +
         ", startDate: "+startDate +
         ", endDate: "+endDate +
         ", result: "+result +
         ", duration: "+duration;
         return ss;
       }
       
    }
    //=========================================================================
    public static void main(String arg[]) throws Exception {
        String sDateS = System.getProperty("sdate");
        String lFileName = System.getProperty("ifile");
        String mess = "The command line: java ScheduleReportRunner -Dsdate=<schedule date> -Difile=<console log file> ";
        log.info(mess);
        mess = "Parameters:";
        log.info(mess);
        mess = "sdate. Use format: mm/dd/yyyy. Optional, by default analzses the whole file. ";
        log.info(mess);
        mess = "ifile. Optional, default value = console.log";
        log.info(mess);
String ffnn = ".";
File ffN = new File(ffnn);
log.info("WWWWWWWWWWWWWWWW :"+ffN.getName() );
log.info("WWWWWWWWWWWWWWWW :"+ffN.getPath());
log.info("WWWWWWWWWWWWWWWW :"+ffN.getAbsolutePath());
log.info("WWWWWWWWWWWWWWWW :"+ffN.isDirectory());
        LogAnalyzer la = new LogAnalyzer();      
        la.setUp();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date sDate = null;
        boolean dateFl = false;
        if(sDateS!=null && sDateS.trim().length()!=0) {
          try {
          sDate = sdf.parse(sDateS);
          dateFl = true;
          } catch(Exception exc) {
            String errorMess = "Wrong schedule date format: "+sDateS;
            log.info(errorMess);
            return;
          }
        } else {
          sDate = sdf.parse("01/01/1900");            
        }
        
        if(lFileName==null || lFileName.trim().length()==0) lFileName="console.log";
        mess = "Analyze log file "+lFileName;
        if(dateFl) {
          mess += " from "+sdf.format(sDate);
        } else {
          mess += " from the beginnig";
        }
        log.info(mess);
        la.analyzeLog(lFileName,sDate);
    }
    
}
