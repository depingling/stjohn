package com.cleanwise.service.apps;
/*
 * SQLRunner.java
 *
 * Created on April 23, 2005, 9:48 PM
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 * Runs a set of sql commands, will rollback on an error based off the command line args
 * @author bstevens
 */
public class SQLRunner extends ClientServicesAPI{
    private static final Logger log = Logger.getLogger(SQLRunner.class);
    
    public static void printUsage(){
        log.info("Usage: -Dconf=v -DSQLFile=v -DignoreError=(true|false)");
    }
    
    public static void main (String[] args){
        SQLRunner me = new SQLRunner();
        me.exec();
    }
    
    private SQLRunner(){}
    
    private void exec(){
        Connection con;
        try{
            con = getConnection();
            con.setAutoCommit(false);
        }catch(Exception e){
            printUsage();
            log.info("Could not create a connection to the database");
            e.printStackTrace();
            return;
        }
        
        boolean ignoreError = false;
        String ignoreErrorStr = System.getProperty("ignoreError");
        if(ignoreErrorStr != null && ("true".equalsIgnoreCase(ignoreErrorStr) || "yes".equalsIgnoreCase(ignoreErrorStr))){
            ignoreError = true;
        }
        
        //read in the file
        String sqlFile = System.getProperty("SQLFile");
        if(sqlFile == null){
            printUsage();
            return;
        }
        
        StringBuffer sqlText = new StringBuffer();
        try{
            BufferedReader rdr = new BufferedReader(new FileReader(sqlFile));
            String aLine = rdr.readLine();
            while(aLine != null){
                //minimal comment recognition
                if(!aLine.trim().startsWith("--")){
                    sqlText.append(" ");
                    sqlText.append(aLine);
                }
                aLine = rdr.readLine();
            }
        }catch(Exception e){
            log.info("Problem reading in file:");
            e.printStackTrace();
        }
        
        //run the sql
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
            Date now = new Date();
            log.info("Started with file: "+sqlFile+" "+sdf.format(now));
            StringTokenizer tok = new StringTokenizer(sqlText.toString(),";");
            while(tok.hasMoreTokens()){
                String sql = tok.nextToken();
                try{
                    log.info("Executing: "+sql);
                    //if a commit was specified in the file commit the current progress
                    if(sql.equalsIgnoreCase("commit")){
                        con.commit();
                    }else{
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(sql);
                        stmt.close();
                    }
                    log.info("Success");
                    log.info("");
                }catch(SQLException e){
                    log.info("Failed");
                    log.info("A SQL Error Occured: "+e.getMessage());
                    if(!ignoreError){
                        log.info("Executing Roll Back");
                        con.rollback();
                        return;
                    }
                }
            }
            log.info("Executing Commit");
            con.commit();
            log.info("Done with file: "+sqlFile+" "+sdf.format(now));
        }catch(Exception e){
            e.printStackTrace();
            try{
                log.info("Executing Roll Back");
                con.rollback();
            }catch(Exception e2){
                log.info("--------------------ERROR ROLLING BACK TRANSACTION-------------------------");
                e2.printStackTrace();
            }
        }finally{
            try{
                con.close();
            }catch(Exception e){
                log.info("Caught exception closing connection: ");
                e.printStackTrace();
            }
        }
    }
    
}
