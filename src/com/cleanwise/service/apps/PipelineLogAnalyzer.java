package com.cleanwise.service.apps;


import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.InitialContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import com.cleanwise.view.utils.ReportWritter;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.reporting.ReportingUtils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 *
 * @author YKupershmidt
 */
public class PipelineLogAnalyzer
{
     private static final Logger log = Logger.getLogger(PipelineLogAnalyzer.class);
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
      "Pipeline: <", //WEB_PRE_ORDER_CAPTURE
      "> Started at: <", //05/03/2004 12:11:117
      ">@@PP@@"
    };
    String[] template2 = {
      "Pipeline: <", //WEB_PRE_ORDER_CAPTURE
      "> Started at: <", //05/03/2004 12:11:117
      "> Ended at: <", //05/03/2004 12:11:118
      "> Duration: <", //1.255
      ">@@PP@@"
    };

    String[] template2Step = {
     ".",
      ": ", 
      " sec"
    };

    public void analyzeLog(String pFileName, Date pStartDate)
    throws Exception
    {
      SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ms");
      ArrayList stepList = new ArrayList();
      String template = "[INFO,Default] @@PP@@";
      String templateStep = "[INFO,Default] OrderPipelineActor ======= End ";
      HashMap currSteps = new HashMap();
      Date collisionDate = null;
      ArrayList openedPipelines = new ArrayList();
      ArrayList allPipelines = new ArrayList();
      FileReader fr = null;
      try {
        fr = new FileReader(pFileName);
      } catch (FileNotFoundException exc){
        String mess = "File does not exist: "+pFileName;
        throw new Exception(mess);
      }
      BufferedReader bufR = new BufferedReader(fr);
      int posType = template.length();
      int posStep = templateStep.length();
      int recno=0;
      for(String str = bufR.readLine(); str!=null; str = bufR.readLine()) {
         recno++;
         int pos = str.indexOf(template);
         if(pos>=0) {
           pos += posType;
           int strLen = str.length();
           if(strLen<=pos) continue;
           char cc = str.charAt(pos);
           if(cc=='S'){
             Pipeline pl = parseStartPipelineMess(str, pStartDate);
             if(pl==null) continue;
             //openedPipelines.clear();
             openedPipelines.add(pl);
             allPipelines.add(pl);
             
           } else if (cc=='E'){
             Pipeline pl = parseEndPipelineMess(str, pStartDate);
             if(pl==null) continue;
             String startDate = pl.startDate;
             for(int jj=openedPipelines.size()-1; jj>=0; jj--){
               Pipeline plOpen = (Pipeline) openedPipelines.get(jj);
               if(startDate.equals(plOpen.startDate)) {
                 plOpen.duration = pl.duration;
                 plOpen.endDate = pl.endDate;
                 plOpen.steps = currSteps;
                 currSteps = new HashMap();
                 openedPipelines.clear();
                 break;
               }
             }
            
           }
           continue;
         }
         pos = str.indexOf(templateStep);
         if(pos>=0) {
           pos += posStep;
           int strLen = str.length();
           if(strLen<=pos) continue;
           if(openedPipelines.size()==0) {
             continue;
           }
           if(openedPipelines.size()>1) {
             Pipeline pl = (Pipeline) openedPipelines.get(0);
             String dddS = pl.startDate;
             try {
               Date ddd = sdf1.parse(dddS);
               if(collisionDate==null || collisionDate.before(ddd)) {
                 collisionDate = ddd;               
               }
             } catch (Exception exc){
             }
           }
           PipelineStep pls = parseEndStepMess(str, pStartDate);  
           if(openedPipelines.size()==0){
             currSteps = new HashMap();
             continue;
           }
           if(!stepList.contains(pls.stepName)) {
             stepList.add(pls.stepName);
           }
           currSteps.put(pls.stepName, pls);
         }
      }

      //Output 
      FileOutputStream fos = new FileOutputStream("aaa.xls");
       GenericReportResultViewVector resultV = new GenericReportResultViewVector();
       GenericReportResultView actionPage = GenericReportResultView.createValue();
       actionPage.setTable(new ArrayList());
       for(int ii=0; ii<allPipelines.size(); ii++) {
          Pipeline pl = (Pipeline) allPipelines.get(ii);
          BigDecimal durBD = null;
          try {
            durBD = new BigDecimal(pl.duration);
          } catch (Exception exc){}
          if(durBD==null){
            continue;
          }
          ArrayList row = new ArrayList();
          row.add(pl.type);          
          row.add(pl.startDate);
          row.add(pl.endDate);
          row.add(durBD);
          HashMap stepHM = pl.steps;
          for(Iterator iter = stepList.iterator(); iter.hasNext(); ) {
            String ss = (String) iter.next();
            PipelineStep pls = (PipelineStep) stepHM.get(ss);
            if(ss!=null){
              BigDecimal stepDurBD = null;
              try {
                stepDurBD = new BigDecimal(pls.stepDuration);
              } catch (Exception exc){}
              row.add(stepDurBD);
              
            } else {
              row.add(null);
            }
          }

          actionPage.getTable().add(row);
        }
        actionPage.setHeader(getHeader(stepList));
        actionPage.setColumnCount(getHeader(stepList).size());
        actionPage.setName("Actions");
        resultV.add(actionPage);
        ReportWritter.writeExcelReportMulti(resultV, fos); 
        fos.flush();
      
    }

    private Pipeline parseStartPipelineMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      Pipeline pl = new Pipeline();
      pl.steps = new HashMap();
      for(int ii=0; ii<template1.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template1[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
         switch (ii) {
//1 Pipeline: <WEB_PRE_ORDER_CAPTURE
//2 Started at: <05/03/2004 12:25:2541>
          case 1:
             //if(!"POST_ORDER_CAPTURE".equals(val)){
             if(!"WEB_PRE_ORDER_CAPTURE".equals(val)){
               return null;
             }
             pl.type = val;
             break;
          case 2:
             pl.startDate = val;
             break;
         }
         pos += template1[ii].length();
         posPrev = pos;
      }
      return pl;
    }
    
    private Pipeline parseEndPipelineMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      Pipeline pl = new Pipeline();
      for(int ii=0; ii<template2.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template2[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
         switch (ii) {


//1 Pipeline: <WEB_PRE_ORDER_CAPTURE> 
//2 Started at: <05/03/2004 12:25:2541> 
//3 Ended at: <05/03/2004 12:25:2543> 
//4 Duration: <1.589>
          case 1:
             pl.type = val;
             break;
          case 2:
             pl.startDate = val;
             break;
          case 3:
             pl.endDate = val;
             break;
          case 4:
             pl.duration = val;
             break;
         }
         pos += template2[ii].length();
         posPrev = pos;
      }
      return pl;
    }

    private PipelineStep parseEndStepMess(String str, Date pStartDate) 
    {
      int pos = 0;
      int strLen = str.length();
      char cc = str.charAt(pos);
      int posPrev = 0;
      PipelineStep pls = new PipelineStep();
      for(int ii=0; ii<template2Step.length; ii++){
         if(pos>=str.length()) break;
         pos = str.indexOf(template2Step[ii],pos);
         if(pos<0) return null;
         String val = str.substring(posPrev,pos);
         if("null".equals(val)) val=null;
         switch (ii) {
          case 1:
             pls.stepName = val;
             break;
          case 2:
             pls.stepDuration = val;
             break;
         }
         pos += template2Step[ii].length();
         posPrev = pos;
      }
      return pls;
    }
    

    private GenericReportColumnViewVector getHeader(ArrayList pSteps) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pipeline Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Start Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","End Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BidDecimal","Total Dur",5,20,"NUMBER"));
        for(int ii=0; ii<pSteps.size(); ii++){
          String ss = (String) pSteps.get(ii);
          header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",ss,0,255,"VARCHAR2"));
        }
        return header;
    }
    ////////////////////////////////////////////////////////////////////////
    public class Pipeline {
       String type;
       String startDate;
       String endDate;
       String duration;
       HashMap steps;
      public String toString() {
       String ss = 
         "type: " + type +
         ", startDate: " + startDate +
         ", endDate: " + endDate +
         ", duration: " + duration +
         ", steps: " + steps;
         return ss;
       }
    }
    public class PipelineStep {
      String stepName;
      String stepDuration;

      public String toString() {
         String ss = 
         "stepName: "+stepName +
         ", stepDuration: "+stepDuration;
         return ss;
       }
       
    }
    //=========================================================================
    public static void main(String arg[]) throws Exception {
        String sDateS = System.getProperty("sdate");
        String lFileName = System.getProperty("ifile");
        log.info("======================= Pipeline Log Analyzer ==================");
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
        PipelineLogAnalyzer la = new PipelineLogAnalyzer();
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
