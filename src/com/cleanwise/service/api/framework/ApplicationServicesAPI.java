package com.cleanwise.service.api.framework;

import java.io.OutputStream;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import oracle.sql.BLOB;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.CallerParametersStJohn;
import com.cleanwise.view.utils.Constants;


/**
 * Provides common functionality for Stateless Session Enterprise Java
 * Beans that access the database.
 *
 */
public abstract class ApplicationServicesAPI

    implements javax.ejb.SessionBean {

    private static final Logger log = Logger.getLogger(ApplicationServicesAPI.class);
    
    private javax.ejb.SessionContext sc;
    
    protected static final String OP_ORDER_OUT =   "ORD_OUT";
    InitialContext ctx = null;

    String mLogCategory = this.getClass().getName();
    Category mCat = Category.getInstance(mLogCategory);
    boolean mDebugOn = false;

    protected final String ORACLE = "Oracle";
    protected final String EDB = "EnterpriseDB";
    protected String databaseName = ORACLE;

    protected void setDebugOn()
    {
        mDebugOn = true;
    }

    protected void setDebugOff()
    {
        mDebugOn = false;
    }

    /**
     * Required by J2EE Specification, not required by Application
     */
    @Resource
    public void setSessionContext(SessionContext sc)   
    {
    	this.sc = sc;
    }
    
    
    /**
     * Uses the default datasource name to lookup a DataSource
     * and return a Connection object.
     *
     * @return          Connection object
     *
     * @throws          SQLException
     * @thorws          NamingException
     *
     * @see Connection
     * @see SQLException
     * @see NamingException
     */
    
    //new method written by SVC
    public Connection getConnection()
	throws SQLException, NamingException {
    	Connection conn = null;
    	String unit = null;
        if ("yes".equals(System.getProperty("multi.store.db"))){ // MULTIPLE DATABASE SCHEMAS
        	unit = getCurrentUnit().trim();
            //log.info("getConnection(): unit = " + unit);
            
            String multiStoreDatasources = System.getProperty("multi.store.datasources");
            if (Utility.isSet(multiStoreDatasources)) {
        	    int intMultiStoreDatasources = Integer.parseInt(multiStoreDatasources);
        	    for (int i=1; i<=intMultiStoreDatasources; i++) {
        		    //construct datasource name
        	    	String dsName;
        	    	if (i<10) {
        		        dsName = Constants.DATASOURCE_NAME_PARTIAL + "0" + i + "DS";
        	    	} else {
        	    		dsName = Constants.DATASOURCE_NAME_PARTIAL + i + "DS";
        	    	}
        		    //log.info("dsName = " + dsName);
        		    if (dsName.equals(unit)) {
        		       conn = getConnection(dsName);
                       DatabaseMetaData metaData = conn.getMetaData();
                       databaseName = metaData.getDatabaseProductName();
                       return conn;        		
        	       }
        	    }
            } else {   
            	throw new NamingException("Number of datasources is not defined. Cannot connect to the appropriate database");
            }
        } else { // ONE DATABASE SCHEMA
        	//log.info("getConnection(): DATA_SOURCE");
    	    conn = getConnection(com.cleanwise.service.api.util.JNDINames.DATA_SOURCE);
            DatabaseMetaData metaData = conn.getMetaData();
            databaseName = metaData.getDatabaseProductName();
            return conn;            
        }        
        return conn;       
    }
    
    public Connection getMainDbConnection()
    throws SQLException, NamingException    {
    	return getConnection(com.cleanwise.service.api.util.JNDINames.MAIN_DATA_SOURCE);
    }
    
    
    public Connection getConnectionNoTx()
    throws SQLException, NamingException    {
    	return getConnection(com.cleanwise.service.api.util.JNDINames.DATA_SOURCE_NO_TX);
    }

    public Connection getAnalyticConnection()
	throws SQLException, NamingException    {
	return getConnection(com.cleanwise.service.api.util.JNDINames.ANALYTIC_DATA_SOURCE);
    }

    public Connection getReportConnection()
	throws SQLException, NamingException    {
	return getConnection(com.cleanwise.service.api.util.JNDINames.REPORT_DATA_SOURCE);
    }
    public Connection getStagedInfoConnection()
	throws SQLException, NamingException    {
	return getConnection(com.cleanwise.service.api.util.JNDINames.STAGED_INFO_DATA_SOURCE);
    }

    public Connection getJDIntegrationConnection()
	throws SQLException, NamingException    {
	return getConnection(com.cleanwise.service.api.util.JNDINames.JDINTEGRATION_DATA_SOURCE);
    }
    
    public Connection getMainDSConnection()
	throws SQLException, NamingException    {
	return getConnection(com.cleanwise.service.api.util.JNDINames.MAIN_DATA_SOURCE);
    }    
    
    private Hashtable dslist = null;
    DataSource lookupDS(String pDSName) {
	if (dslist == null ) {
	    dslist = new Hashtable();
	}
	return (DataSource)dslist.get(pDSName);
    }

    private DataSource getDataSourceByName(String pDSName)
	throws  NamingException    {
	DataSource ds = lookupDS(pDSName);
	if ( null == ds ) {
	    try		{
		ds = (DataSource)ctx.lookup(pDSName);
		if ( null == ds ) {
		    String m = new String
			("class=" + this.getClass().getName() +
			 ", DS=" +   pDSName +
			 ", getDataSourceByName, failed for pDSName="
			 + pDSName );
		    log.info(m);
		    throw new NamingException(m);
		}

		dslist.put(pDSName, ds);
		if (dslist.size() > 1 ) {
		    log.info
			("class=" + this.getClass().getName() +
			 ", DS=" +   pDSName +
			 ", getDataSourceByName, added to the cache, size="
			 + dslist.size() );
		}

	    }	catch (NamingException ne)   {
		System.err.println
		    ("ApplicationServicesAPI: unable to create" +
		     "\nInitialContext, or unable to look up DS="
		     + pDSName );

		ne.printStackTrace();
		throw ne;
	    }
	}

	return ds;

    }

    public Connection getConnection(String pDSName)
	throws SQLException, NamingException
    {
    	//log.info("getConnection(String pDSName): pDSName = " + pDSName);
    	
        // we'd expect this to be null first request
        if (ctx == null)	    {
	    ctx = getInitialContext();
	}
	int retries = 0;

	do {

	    DataSource ds = getDataSourceByName(pDSName);

	    Connection con = ds.getConnection();
	    if ( ! con.isClosed() ) {
		if ( retries > 0 ) {
		    // Logging the 1st attempt is not interesting.
		    log.info
			("DS=" +   pDSName +
			 ", getConnection, success on retry="
			 + retries );
		}
		return con;
	    }

	    log.info
		("DS=" +   pDSName +
		 ", getConnection, is closed retry="
		 + retries );
	    con = null;
	    ds = null;
	    dslist.put(pDSName, null);



	} while ( retries++ < 100 );

	log.info
	    ("DS=" +   pDSName +
	     ", getConnection, failed retry="
	     + retries );

	return null;
    }


    /**
     * Utility method used to assist in transaction managemnet
     *
     * @param conn      A <code> javax.transaction.TransactionManager</code> object that was aquired by
     *                  the <code>getTransactionManager()</code> method of this class.
     * @return          TransactionManager for current thread
     *
     * @see             NamingException
     */
    public javax.transaction.TransactionManager getTransactionManager()
	throws javax.naming.NamingException
    {
	javax.naming.InitialContext ctx = getInitialContext();
	ctx = getInitialContext();

	javax.transaction.TransactionManager tm = (javax.transaction.TransactionManager)
	    ctx.lookup("java:/TransactionManager");

	return tm;

    }

    public void closeConnection(Connection conn)    {
        try {
	    if (conn != null && !conn.isClosed()){
		conn.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public APIAccess getAPIAccess()
	throws javax.naming.NamingException    {
	return APIAccess.getAPIAccess();
    }


    /**
     * Utility method used to assist in Connection managemnet
     *
     * @param conn      A <code> Connection</code> object that was aquired by
     *                  the <code>getConnection()</code> method of this class.
     * @return          none.
     *
     * @see             Connection
     * @see             SQLException
     */
    public void closeReportConnection(Connection conn) {
	closeConnection(conn);
    }

    ////////////////////////////////////////////////////////////////

    /**
     * Required by J2EE Specification, not required by Application
     */
    public InitialContext getInitialContext()
	throws NamingException    {
        return new InitialContext();
    }

    /**
     * Required by J2EE Specification, not required by Application
     */
    public void ejbActivate()     { }

    protected boolean mReloadProps = false;
    /**
     * Required by J2EE Specification, not required by Application
     */
    public void ejbPassivate() {
      mReloadProps = true;
    }

    /**
     * Required by J2EE Specification, not required by Application
     */
    public void ejbRemove() { }

    /**
     * Testing methods
     */
    public String verify() throws RemoteException {
        return "Passed";
    }

    public String verifyDB()
	throws RemoteException    {

        Connection conn = null;
        String rtn = "Failed";

        try	    {
	    conn = getConnection();

	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT SYSDATE FROM DUAL");

	    if (rs.next())
		rtn = rs.getString(1);

	    try		{
		rs.close();
		stmt.close();
	    }    catch (Exception e)		{
		e.printStackTrace();
	    }
	}        catch (Exception e)	    {
	    e.printStackTrace();

	    return rtn;
	}
	finally	    {
	    closeConnection(conn);
	}

        return rtn;
    }

    /**
     * Utility method used to log application debug messages.
     *
     * @param pLogMsg   A  pLogMsg object contains
     *                  the message being logged.
     * @return          none.
     *
     * @throws          none
     *
     */
    public void logDebug(String pLogMsg) {
        mCat.debug(pLogMsg);
    }

    /**
     *Returns wheather debbuging is enabled as defined by the logging configuration
     */
    protected boolean isLoggingDebugEnabled(){
        return mCat.isDebugEnabled();
    }

    /**
     * Utility method used to determine whether debug mode is on.
     *
     * @return          boolean
     *
     * @throws          none
     *
     */
    public boolean isDebugOn()    {
        return mDebugOn;
    }

    /**
     * Utility method to get the category of the log messages.
     *
     * @return          The category for the message being logged.
     *
     * @throws          none
     *
     */
    public String getLogCategory() {
        return mLogCategory;
    }

    /**
     * Utility method to set the category of the log messages.
     * By default the constuctor will set he mLogCategory variable
     * to this.getClass().getName().
     *
     * @param pCategory A  pCategory object contains
     *                  the category for the message being logged.
     * @return          none.
     *
     * @throws          none
     *
     */
    public void setLogCategory(String pCategory) {
        mLogCategory = pCategory;
    }

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
    public void logInfo(String pLogMsg) {
        mCat.info(pLogMsg);
    }

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
    public void logInfo(String pLogMsg, String[] pParams) {
        String lFinalLogMsg = "";
        int lPc = 0;

        for (int lRidx = 0; lRidx < pLogMsg.length(); lRidx++) {

	    if (pLogMsg.charAt(lRidx) == '?') {
		lFinalLogMsg += pParams[lPc++];
	    } else {
		lFinalLogMsg += pLogMsg.charAt(lRidx);
	    }
	}
        mCat.info(lFinalLogMsg);
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
    public void logError(String pLogMsg) {
        mCat.error(pLogMsg);
    }

    public String getAppLock(Connection pCon, String pLockName)
	throws java.sql.SQLException, RemoteException {
	if ( pLockName == null || pLockName.length() == 0 ) {
	    return "No lock specified.";
	}

	String lockValue = "none",
	    lockQ = " select clw_value from clw_property " +
	    " where short_desc = '" + pLockName + "'" +
  	    " for update";

	Statement stmt = pCon.createStatement();
	ResultSet rs = stmt.executeQuery(lockQ);

	try {
	    PropertyUtil propUtil = new PropertyUtil(pCon);
	    if ( rs == null || rs.next() == false ) {
		propUtil.saveValue(0, 0, pLockName, pLockName, "LOCKED" );
		return "UNLOCKED";
	    }
	    else {
		lockValue = rs.getString(1);
		if ( lockValue != null &&
		     !lockValue.equals("UNLOCKED") ) {
		    return lockValue;
		}
		propUtil.saveValue(0, 0, pLockName, pLockName, "UNLOCKED" );
		return "UNLOCKED";
	    }
	}
	finally {
	    if (stmt != null) {
		stmt.close();
	    }
	    if ( rs != null ) {
		rs.close();
	    }
	}
    }

    public String releaseAppLock(Connection pCon, String pLockName)
	throws java.sql.SQLException, RemoteException {
	if ( pLockName == null || pLockName.length() == 0 ) {
	    return "No lock specified.";
	}

	PropertyUtil propUtil = new PropertyUtil(pCon);
	propUtil.saveValue(0, 0, pLockName, pLockName, "UNLOCKED" );
	return "UNLOCKED";

    }

    /**
     *
     *Processes exceptions and transforms them into a remote exception suitable for throwing from an EJB.
     *
     *@param Exception the exception to process and transform.
     */
    protected RemoteException processException(Exception pException){
        logError(pException.getMessage());
        pException.printStackTrace();
        if(pException instanceof RemoteException){
            return (RemoteException) pException;
        }else{
            return new RemoteException(pException.getMessage(),pException);
        }
    }


    protected static java.util.Properties clwProperties;

    // Utility method to load the properties file.
    // This is used in constructing email messages.
    protected java.util.Properties loadClwProperties() {
        if(ApplicationServicesAPI.clwProperties != null){
            return ApplicationServicesAPI.clwProperties;
        }
        String def_fname = System.getProperty("webdeploy") + "/WEB-INF/classes/com/cleanwise/view/i18n/CleanwiseResources.properties";
        logDebug("load file: " + def_fname);

        java.util.Properties props = new java.util.Properties();

        try {

            java.io.File def = new java.io.File(def_fname);

            if (def.canRead()) {
                props.load(new java.io.FileInputStream(def));
                ApplicationServicesAPI.clwProperties = props;
            } else {
                System.err.println(" can't read: " + def_fname);
            }
        } catch (Exception e) {
            logError("loadClwProperties: " + e);
        }

        return props;
    }

    public BLOB toBlob(Connection conn, byte[] data) throws Exception {
        oracle.sql.BLOB blob = null;
        try{
            blob = createTemporaryBlob(conn);
            setByteToOracleBlob(blob, data);
            return blob;}
        catch (Exception e) {
            e.printStackTrace();
            freeTemporary(blob);
            throw new Exception(e.getMessage());
        }
    }

    private void freeTemporary(BLOB  blob) throws SQLException {
        if(blob!=null){
            blob.freeTemporary();
        }
    }

    public static BLOB createTemporaryBlob(Connection con)
      throws SQLException {
      CallableStatement cst = null;
      try {
        cst = con.prepareCall("{call dbms_lob.createTemporary(?, false, dbms_lob.SESSION)}");
        cst.registerOutParameter(1, OracleTypes.BLOB);
        cst.execute();
        return (BLOB)cst.getBlob(1);
      } finally {
        if (cst != null) { cst.close(); }
      }
    }

    protected int setByteToOracleBlob(oracle.sql.BLOB blob, byte[] data) throws Exception {

        OutputStream out = blob.setBinaryStream(1L);
        out.write(data);
        out.flush();
        out.close();

        return data.length;

    }
    
    public String getCurrentUnit() {
    	String[] callerPars = null;
    	callerPars = getCallerParameters();
        String currentUnit = null;
        if (callerPars != null && callerPars.length > 0) {
            //log.info("=====StoreUnit = " + callerPars[CallerParametersStJohn.CURRENT_UNIT]);
            currentUnit = callerPars[CallerParametersStJohn.CURRENT_UNIT];
        } else {
            log.info("===== STORE UNIT IS NULL =====");
        }
        if (currentUnit == null || "unknown".equals(currentUnit)) {
        	currentUnit = "StJohnApp"; // ???
        }
        return currentUnit;
    }
    
    private String[] getCallerParameters() {
    	if (ctx == null) {
    		try {
    	        ctx = getInitialContext();
    		} catch (NamingException ne) {
    			log.info("=====getInitialContext() produced Exception.");
    		}
    	}
    	//log.info("SessionContext from field injection: " + sc);
    	try {
            if (sc.getCallerPrincipal() != null) {    		
            	//log.info("sc.getCallerPrincipal() != null");
            	//log.info("sc.getCallerPrincipal().getName() = " + sc.getCallerPrincipal().getName());
                String[] callerPars = Utility.parseStringToArray(sc.getCallerPrincipal().getName(), ",");
                //log.info("getCallerParameters(): callerPars = " + callerPars);
                return callerPars;
            }
        } catch (Exception e) {
            log.info("=====getCallerPrincipal() is not operable.");
            //throw e;
        } 
        return null;
    }
}
