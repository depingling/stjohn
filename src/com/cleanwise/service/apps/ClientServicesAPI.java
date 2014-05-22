package com.cleanwise.service.apps;

import com.cleanwise.service.api.APIAccess;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
/**
 * This class is for client side service. e.g. error logging
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 *
 */
import javax.sql.DataSource;


/**
 * Provides common functionality for Stateless Session Enterprise Java
 * Beans that access the database.
 *
 */
public abstract class ClientServicesAPI
{
    private static final Logger log = Logger.getLogger(ClientServicesAPI.class);
    String mClassName = this.getClass().getName();
    Properties installation;
    /**
     * Utility method used to log application information messages.
     *
     * @param pLogMsg   A  pLogMsg object contains
     *                  the message being logged.
     * @return          none.
     *
     * @throws          none
     *
     */

    /**
     * Utility method used to log application information messages.
     *
     * @param pLogMsg   A  pLogMsg object contains
     *                  the message being logged with ?(s) for areas
     *                  where strings are to be replaced.
     * @param pParams   A  pParams array containing
     *                  values to replace the ?(s) found in pLogMsg.
     * @return          none.
     *
     * @throws          none
     *
     */

    public void logInfo(String pLogMsg, String [] pParams ) {
	    String lFinalLogMsg = "";
	    int lPc = 0;
	    for ( int lRidx = 0; lRidx < pLogMsg.length(); lRidx++ ) {
	      if ( pLogMsg.charAt(lRidx) == '?' ) {
		      lFinalLogMsg += pParams[lPc++];
	      } else {
		      lFinalLogMsg += pLogMsg.charAt(lRidx);
	      }
      }
      log.info("[Info, " + mClassName + "]    " + lFinalLogMsg);
    }


    /**
     * Utility method used to log application error messages.
     *
     * @param pLogMsg   A  pLogMsg object contains
     *                  the message being logged.
     * @return          none.
     *
     * @throws          none
     *
     */


    /**
     *Filters any output that is not an actual error so that it does not get picked up by any of the 
     *OS filters
     */
    public String filterOutput(String s){
        if(s == null){
            return s;
        }
        s = s.replaceAll("error","");
        s = s.replaceAll("Exception","");
        s = s.replaceAll("exception","");
        s = s.replaceAll("Error","");
        
        return s;
    }
    
    /**
     *Returns an initialized APIAccess object.  Note a side affect of this method is that anything in the 
     *configuration properties file is loaded into the system properties.
     */
    protected APIAccess getAPIAccess() throws IOException, NamingException{
        Properties props = getConfiguration();
        return getAPIAccess(props);
    }
    
    /**
     *Returns an initialized APIAccess object.  Note a side affect of this method is that anything in the 
     *configuration properties file is loaded into the system properties.
     */
    protected static APIAccess getAPIAccess(Properties props) throws IOException, NamingException{
        Enumeration en = props.propertyNames();
        while(en.hasMoreElements()){
            String key = (String) en.nextElement();
            System.setProperty(key,props.getProperty(key));
        }
        return new APIAccess();
    }
    
    /**
     *Returns the configuration as defined by the system "conf" property (which should define a location of a
     *property file, or by default it will simply try to load the file name "installation.properties".
     */
    protected Properties getConfiguration() throws IOException {
        if(installation == null){
            installation = loadConfiguration();
        }    
        return installation;
    }
    
    /**
     *Returns the configuration as defined by the system "conf" property (which should define a location of a
     *property file, or by default it will simply try to load the file name "installation.properties".
     */
    protected static Properties loadConfiguration() throws IOException {
        Properties installation = new Properties();
        String conf = System.getProperty("conf");
        if(conf == null){
            installation.load(new FileInputStream("installation.properties"));
        }else{
            installation.load(new FileInputStream(conf));
        }
        return installation;
    }

    /**
     * Creates a database connection based off the configuration setup in the instalation.properties file.
     */
    protected Connection getConnection() throws SQLException {
	    try		{
	    	InitialContext ctx = new InitialContext();
	    	DataSource ds = (DataSource)ctx.lookup(com.cleanwise.service.api.util.JNDINames.DATA_SOURCE);
	    	//ds.getConnection().setAutoCommit(false);
	    	return ds.getConnection();
	    }	catch (Exception ne)   {
	    	log.info(ne.getMessage());
			log.info("Could not get managed connection through JNDI.  Trying to create a direct connection.");
	    }

        String url;
        String user;
        String pass;
        String dbDriver;
        url = System.getProperty("dbUrl");
        user = System.getProperty("dbUser");
        pass = System.getProperty("dbPassword");
        dbDriver = System.getProperty("dbDriver");
        try{
            getConfiguration();
            if(url == null){
                url = installation.getProperty("dbUrl");
            }
            if(user == null){
                user = installation.getProperty("dbUser");
            }
            if(pass == null){
                pass = installation.getProperty("dbPassword");
            }
            if(dbDriver == null){
            	dbDriver = installation.getProperty("dbDriver");
            }
        }catch (Exception e){}
        
        log.info("dbDriver = " + dbDriver);
        
        try{
            //Class.forName("oracle.jdbc.driver.OracleDriver");
        	Class.forName(dbDriver);
        }catch (ClassNotFoundException e){
        	//throw new SQLException("Could not load oracle driver! (class oracle.jdbc.driver.OracleDriver not found) " + e.getMessage());
        	throw new SQLException("Could not load " +dbDriver+ " driver! (class " +dbDriver+ " not found) " + e.getMessage());
        }
        log.info("Getting db connection:");
        log.info(url+"@"+user+":******");
        Connection connection = DriverManager.getConnection(url, user, pass);
        connection.setAutoCommit(false);
        return connection;
    }
    
    /**
     * Creates a database connection based off the configuration setup in the instalation.properties file.
     */
    protected Connection getReportConnection() throws SQLException {
	    try		{
	    	InitialContext ctx = new InitialContext();
	    	DataSource ds = (DataSource)ctx.lookup(com.cleanwise.service.api.util.JNDINames.REPORT_DATA_SOURCE);
	    	//ds.getConnection().setAutoCommit(false);
	    	return ds.getConnection();
	    }	catch (Exception ne)   {
	    	log.info(ne.getMessage());
			log.info("Could not get managed connection through JNDI.  Trying to create a direct connection.");
	    }

        String url;
        String user;
        String pass;
        url = System.getProperty("reportUrl");
        user = System.getProperty("reportUser");
        pass = System.getProperty("reportPassword");
        try{
            getConfiguration();
            if(url == null){
                url = installation.getProperty("reportUrl");
            }
            if(user == null){
                user = installation.getProperty("reportUser");
            }
            if(pass == null){
                pass = installation.getProperty("reportPassword");
            }
        }catch (Exception e){}
        
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException e){
            throw new SQLException("Could not load oracle driver! (class oracle.jdbc.driver.OracleDriver not found) " + e.getMessage());
        }
        log.info("Getting db connection:");
        log.info(url+"@"+user+":******");
        Connection connection = DriverManager.getConnection(url, user, pass);
        connection.setAutoCommit(false);
        return connection;
    }
    
    /**
     * Close a database connection.  Catches error for convenience as there may not be anything left to do.
     * @param conn
     */
    protected void closeConnection(Connection conn){
    	try {
    	    if (conn != null && !conn.isClosed()){
    		conn.close();
    	    }
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    
    /**
     *Gets the date range inclusive of the weekend for processing.  That is if you ask this method for the date range
     *on wednesday 1/1/2005 it give you 2/10/2005 - 2/10/2005; However if you ask it for the date range on Monday morning
     *2/10/2005 it gives you a date range of 2/7/2005 to 2/10/2005.  For a request of Friday evening on 2/10/2005 you will
     *get back a date range of 2/10/2005 to 2/2005.
     */
    protected static Date[] getDateRangeForTodayPlusWeekend(Date pBaseDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(pBaseDate);
        int forwardModifier = 0;
        int backModifier = 0;
        if(cal.get(Calendar.HOUR_OF_DAY) < 10){
            //we are running in the morning
            if(Calendar.MONDAY == cal.get(Calendar.DAY_OF_WEEK)){
                backModifier = 2;
            }
        }else{
            //we are running at night
            if(Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK)){
                forwardModifier = 2;
            }
        }
        
        
        cal.add(Calendar.DAY_OF_YEAR, -1 * backModifier);
        Date begDate = cal.getTime();
        cal.setTime(pBaseDate);
        cal.add(Calendar.DAY_OF_YEAR, forwardModifier);
        Date endDate = cal.getTime();
        Date[] retVal = new Date[2];
        retVal[0] = begDate;
        retVal[1] = endDate;
        return retVal;
    }

}
