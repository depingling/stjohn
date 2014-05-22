package com.cleanwise.service.api.framework;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import java.rmi.*;

import java.sql.*;

import java.util.*;

import javax.ejb.*;


/**
 * This class will identify all Session Beans in the application, and
 * provide static database driver loading, database connection management,
 * and naming service InitialContext funtionality.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Liang Li
 *
 */
import javax.naming.*;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import org.apache.log4j.Category;


public class ReportAPI
    extends ApplicationServicesAPI
{

    protected class DBConnections {

	public Connection mainCon = null, lawCon = null,
	    stagedInfoCon = null, reportCon = null, 
	         analyticCon = null;

	public int getConnectionCnt() {
	    int conCt = 0;
	    if ( null != mainCon ) {
		conCt++;
	    }
	    if ( null != lawCon ) {
		conCt++;
	    }
	    if ( null != stagedInfoCon ) {
		conCt++;
	    }
	    if ( null != reportCon ) {
		conCt++;
	    }
	    return conCt;
	}

	public Connection getActiveConnection() {

	    if ( null != lawCon ) {
		return lawCon;
	    }
	    if ( null != stagedInfoCon ) {
		return stagedInfoCon;
	    }
	    if ( null != reportCon ) {
		return reportCon;
	    }

	    return mainCon;
	}

	public boolean usingExistingCon = false;
	public String schemaCds = "";
	public DBConnections(String pschemaCds, boolean fAnalyticToo)
	    throws Exception {
	    analyticToo = fAnalyticToo;
	    schemaCds = pschemaCds;
	    mkCons(schemaCds);
	}

	public DBConnections() 
	    throws Exception {
	    mkCons(schemaCds);
	}

	public DBConnections(String pschemaCds) 
	    throws Exception {
	    schemaCds = pschemaCds;
	    mkCons(schemaCds);
	}
	
	public void mkCons(String pschemaCds) 
	    throws Exception {
	    schemaCds = pschemaCds;

	    try {
	    if (mainCon == null ) { 
		mainCon = getConnection();
	    }

	    StringTokenizer schemaCdTok = null;
	    if(schemaCds != null){
		schemaCdTok = new StringTokenizer(schemaCds,",");
	    }
	    //if nothing was specified use existing Connection
	    if(schemaCdTok==null || schemaCdTok.countTokens() <= 1){
            
            // Handle the case where 1 schema is specified.
            if(RefCodeNames.REPORT_SCHEMA_CD.REPORT.equals(schemaCds)) {
                if ( reportCon == null ) {
                    reportCon = getReportConnection();
                }
                usingExistingCon = false;
            }
            else if(RefCodeNames.REPORT_SCHEMA_CD.STAGED_INFO.equals(schemaCds)) {
                if ( null == stagedInfoCon ) {
                    stagedInfoCon = getStagedInfoConnection();
                }
                usingExistingCon = false;
            }
            else {
                usingExistingCon = true;
            }
        }
        else{
		while(schemaCdTok.hasMoreTokens()){
		    String schemaCd = schemaCdTok.nextToken();
		    if(RefCodeNames.REPORT_SCHEMA_CD.REPORT.equals(schemaCd)) {
			if ( reportCon == null ) {
			    reportCon = getReportConnection();
			}
		    }else if(RefCodeNames.REPORT_SCHEMA_CD.STAGED_INFO.equals(schemaCd)) {
			if ( null == stagedInfoCon ) {
			    stagedInfoCon = getStagedInfoConnection();
			}
		    }else if (Utility.isSet(schemaCd)){
                // Set the main connection to true since the schema specified
                // could be MAIN or something else.
                usingExistingCon = true;
		    }
		}
	    }
        
        
	    if ( analyticToo && null == analyticCon ) {
		analyticCon = getAnalyticConnection();
	    }
	    } 
	    catch (Exception e) {
		// Failed to get 1 of the connections, try
		// to close anything that might have been
		// opened.
		closeAll();
		throw e;
	    }

	}

	private boolean analyticToo = false;
	public void closeAll() {
	    closeConnection( mainCon );
	    closeConnection( lawCon );
	    closeConnection( stagedInfoCon );
	    closeConnection( reportCon );
	    closeConnection( analyticCon );
	}
    }

}
