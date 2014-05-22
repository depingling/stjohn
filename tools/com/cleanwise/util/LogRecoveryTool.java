package com.cleanwise.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/**
 * Recovers logs from an email server failure.  Will scann all of the log files
 * and forward them.  Currently has some hardcoded conditions of errors to ignore.
 * Otherwise will forward anything with the word error or exception etc in it.
 * @author bstevens
 */
public class LogRecoveryTool {
    private static ArrayList logDirs = new ArrayList();
    static{
        logDirs.add("/apps/ixtendx/JBoss-2.4.4_Jetty-3.1.5-1/jboss/xsuite/processed_log");
        logDirs.add("/apps/ixtendx/JBoss-2.4.4_Jetty-3.1.5-1/jboss/xsuite/reports");
        logDirs.add("/apps/ixtendx/JBoss-2.4.4_Jetty-3.1.5-1/jboss/xsuite/edi/outbound_log");
        logDirs.add("/apps/ixtendx/JBoss-2.4.4_Jetty-3.1.5-1/jboss/xsuite/edi/processed_log");
    }
    private static String fromDateString = "11/24/2006";
    private static long fromDate;
    
    
    public static void main(String[] args){
        try{
            Date fromDateDte = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            fromDateDte = sdf.parse(fromDateString);
            fromDate = fromDateDte.getTime();
            
            Iterator it = logDirs.iterator();
            while(it.hasNext()){
                processDir(new File((String) it.next()));
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static void processDir(File dir){
        System.out.println("Processing dir: "+dir.getName());
        File[] files = dir.listFiles();
        for(int i=0; i<files.length;i++){
            File aLogFile = files[i];
            processLogFile(aLogFile);
        }
    }
    
    private static void processLogFile(File log){
        System.out.println("Processing file: "+log.getAbsolutePath());
        boolean sendEmail = false;
        StringBuffer entireText = new StringBuffer("");
        try{
            if(log.isDirectory()){
                return;
            }
            long modDate = log.lastModified();
            if(modDate < fromDate){
                return;
            }
            
            BufferedReader rdr = new BufferedReader(new FileReader(log));
            String aLine = rdr.readLine();
            while(aLine!=null){
                entireText.append(aLine);
                entireText.append("\n");
                aLine = aLine.toLowerCase();
                if(aLine.indexOf("error")>=0 || aLine.indexOf("exception")>=0){
                    if(!(aLine.indexOf("user xadmin cannot log in")>=0)){
                        sendEmail = true;
                    }
                }
                aLine = rdr.readLine();
            }
        }catch(Exception e){
            sendEmail = true;
            e.printStackTrace();
        }
        if(sendEmail){
            try{
                //com.cleanwise.service.apps.ApplicationsEmailTool.sendEmail("jobreporting@cleanwise.com",log.getName(),entireText.toString());
                if(true){throw new Exception("set email!");}
            }catch(Exception e){
                System.out.println("Could not send email:");
                e.printStackTrace();
            }
        }
    }
}

