
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportUserActivityDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_USER_ACTIVITY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportUserActivityData;
import com.cleanwise.service.api.value.ReportUserActivityDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>ReportUserActivityDataAccess</code>
 */
public class ReportUserActivityDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ReportUserActivityDataAccess.class.getName());

    /** <code>RPT_REPORT_USER_ACTIVITY</code> table name */
	/* Primary key: REPORT_USER_ACTIVITY_ID */
	
    public static final String RPT_REPORT_USER_ACTIVITY = "RPT_REPORT_USER_ACTIVITY";
    
    /** <code>REPORT_USER_ACTIVITY_ID</code> REPORT_USER_ACTIVITY_ID column of table RPT_REPORT_USER_ACTIVITY */
    public static final String REPORT_USER_ACTIVITY_ID = "REPORT_USER_ACTIVITY_ID";
    /** <code>STORE_ID</code> STORE_ID column of table RPT_REPORT_USER_ACTIVITY */
    public static final String STORE_ID = "STORE_ID";
    /** <code>STORE_NAME</code> STORE_NAME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String STORE_NAME = "STORE_NAME";
    /** <code>SESSION_ID</code> SESSION_ID column of table RPT_REPORT_USER_ACTIVITY */
    public static final String SESSION_ID = "SESSION_ID";
    /** <code>USER_NAME</code> USER_NAME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String USER_NAME = "USER_NAME";
    /** <code>ACTION_CLASS</code> ACTION_CLASS column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ACTION_CLASS = "ACTION_CLASS";
    /** <code>ACTION</code> ACTION column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ACTION = "ACTION";
    /** <code>HTTP_START_TIME</code> HTTP_START_TIME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String HTTP_START_TIME = "HTTP_START_TIME";
    /** <code>ACTION_START_TIME</code> ACTION_START_TIME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ACTION_START_TIME = "ACTION_START_TIME";
    /** <code>ACTION_END_TIME</code> ACTION_END_TIME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ACTION_END_TIME = "ACTION_END_TIME";
    /** <code>HTTP_END_TIME</code> HTTP_END_TIME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String HTTP_END_TIME = "HTTP_END_TIME";
    /** <code>ACTION_RESULT</code> ACTION_RESULT column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ACTION_RESULT = "ACTION_RESULT";
    /** <code>HTTP_RESULT</code> HTTP_RESULT column of table RPT_REPORT_USER_ACTIVITY */
    public static final String HTTP_RESULT = "HTTP_RESULT";
    /** <code>ACTION_DURATION</code> ACTION_DURATION column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ACTION_DURATION = "ACTION_DURATION";
    /** <code>HTTP_DURATION</code> HTTP_DURATION column of table RPT_REPORT_USER_ACTIVITY */
    public static final String HTTP_DURATION = "HTTP_DURATION";
    /** <code>REFERER</code> REFERER column of table RPT_REPORT_USER_ACTIVITY */
    public static final String REFERER = "REFERER";
    /** <code>PARAMS</code> PARAMS column of table RPT_REPORT_USER_ACTIVITY */
    public static final String PARAMS = "PARAMS";
    /** <code>FINISH_FILE</code> FINISH_FILE column of table RPT_REPORT_USER_ACTIVITY */
    public static final String FINISH_FILE = "FINISH_FILE";
    /** <code>SERVER_NAME</code> SERVER_NAME column of table RPT_REPORT_USER_ACTIVITY */
    public static final String SERVER_NAME = "SERVER_NAME";
    /** <code>REQUEST_ID</code> REQUEST_ID column of table RPT_REPORT_USER_ACTIVITY */
    public static final String REQUEST_ID = "REQUEST_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table RPT_REPORT_USER_ACTIVITY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table RPT_REPORT_USER_ACTIVITY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table RPT_REPORT_USER_ACTIVITY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ReportUserActivityDataAccess()
    {
    }

    /**
     * Gets a ReportUserActivityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pReportUserActivityId The key requested.
     * @return new ReportUserActivityData()
     * @throws            SQLException
     */
    public static ReportUserActivityData select(Connection pCon, int pReportUserActivityId)
        throws SQLException, DataNotFoundException {
        ReportUserActivityData x=null;
        String sql="SELECT REPORT_USER_ACTIVITY_ID,STORE_ID,STORE_NAME,SESSION_ID,USER_NAME,ACTION_CLASS,ACTION,HTTP_START_TIME,ACTION_START_TIME,ACTION_END_TIME,HTTP_END_TIME,ACTION_RESULT,HTTP_RESULT,ACTION_DURATION,HTTP_DURATION,REFERER,PARAMS,FINISH_FILE,SERVER_NAME,REQUEST_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_USER_ACTIVITY WHERE REPORT_USER_ACTIVITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportUserActivityId=" + pReportUserActivityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportUserActivityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportUserActivityData.createValue();
            
            x.setReportUserActivityId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setStoreName(rs.getString(3));
            x.setSessionId(rs.getString(4));
            x.setUserName(rs.getString(5));
            x.setActionClass(rs.getString(6));
            x.setAction(rs.getString(7));
            x.setHttpStartTime(rs.getTimestamp(8));
            x.setActionStartTime(rs.getTimestamp(9));
            x.setActionEndTime(rs.getTimestamp(10));
            x.setHttpEndTime(rs.getTimestamp(11));
            x.setActionResult(rs.getString(12));
            x.setHttpResult(rs.getString(13));
            x.setActionDuration(rs.getBigDecimal(14));
            x.setHttpDuration(rs.getBigDecimal(15));
            x.setReferer(rs.getString(16));
            x.setParams(rs.getString(17));
            x.setFinishFile(rs.getString(18));
            x.setServerName(rs.getString(19));
            x.setRequestId(rs.getString(20));
            x.setAddDate(rs.getTimestamp(21));
            x.setAddBy(rs.getString(22));
            x.setModDate(rs.getTimestamp(23));
            x.setModBy(rs.getString(24));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("REPORT_USER_ACTIVITY_ID :" + pReportUserActivityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportUserActivityDataVector object that consists
     * of ReportUserActivityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportUserActivityDataVector()
     * @throws            SQLException
     */
    public static ReportUserActivityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ReportUserActivityData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "RPT_REPORT_USER_ACTIVITY.REPORT_USER_ACTIVITY_ID,RPT_REPORT_USER_ACTIVITY.STORE_ID,RPT_REPORT_USER_ACTIVITY.STORE_NAME,RPT_REPORT_USER_ACTIVITY.SESSION_ID,RPT_REPORT_USER_ACTIVITY.USER_NAME,RPT_REPORT_USER_ACTIVITY.ACTION_CLASS,RPT_REPORT_USER_ACTIVITY.ACTION,RPT_REPORT_USER_ACTIVITY.HTTP_START_TIME,RPT_REPORT_USER_ACTIVITY.ACTION_START_TIME,RPT_REPORT_USER_ACTIVITY.ACTION_END_TIME,RPT_REPORT_USER_ACTIVITY.HTTP_END_TIME,RPT_REPORT_USER_ACTIVITY.ACTION_RESULT,RPT_REPORT_USER_ACTIVITY.HTTP_RESULT,RPT_REPORT_USER_ACTIVITY.ACTION_DURATION,RPT_REPORT_USER_ACTIVITY.HTTP_DURATION,RPT_REPORT_USER_ACTIVITY.REFERER,RPT_REPORT_USER_ACTIVITY.PARAMS,RPT_REPORT_USER_ACTIVITY.FINISH_FILE,RPT_REPORT_USER_ACTIVITY.SERVER_NAME,RPT_REPORT_USER_ACTIVITY.REQUEST_ID,RPT_REPORT_USER_ACTIVITY.ADD_DATE,RPT_REPORT_USER_ACTIVITY.ADD_BY,RPT_REPORT_USER_ACTIVITY.MOD_DATE,RPT_REPORT_USER_ACTIVITY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ReportUserActivityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs) throws SQLException{
         return parseResultSet(rs,0);
    }

    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated ReportUserActivityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ReportUserActivityData x = ReportUserActivityData.createValue();
         
         x.setReportUserActivityId(rs.getInt(1+offset));
         x.setStoreId(rs.getInt(2+offset));
         x.setStoreName(rs.getString(3+offset));
         x.setSessionId(rs.getString(4+offset));
         x.setUserName(rs.getString(5+offset));
         x.setActionClass(rs.getString(6+offset));
         x.setAction(rs.getString(7+offset));
         x.setHttpStartTime(rs.getTimestamp(8+offset));
         x.setActionStartTime(rs.getTimestamp(9+offset));
         x.setActionEndTime(rs.getTimestamp(10+offset));
         x.setHttpEndTime(rs.getTimestamp(11+offset));
         x.setActionResult(rs.getString(12+offset));
         x.setHttpResult(rs.getString(13+offset));
         x.setActionDuration(rs.getBigDecimal(14+offset));
         x.setHttpDuration(rs.getBigDecimal(15+offset));
         x.setReferer(rs.getString(16+offset));
         x.setParams(rs.getString(17+offset));
         x.setFinishFile(rs.getString(18+offset));
         x.setServerName(rs.getString(19+offset));
         x.setRequestId(rs.getString(20+offset));
         x.setAddDate(rs.getTimestamp(21+offset));
         x.setAddBy(rs.getString(22+offset));
         x.setModDate(rs.getTimestamp(23+offset));
         x.setModBy(rs.getString(24+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ReportUserActivityData Object represents.
    */
    public int getColumnCount(){
        return 24;
    }

    /**
     * Gets a ReportUserActivityDataVector object that consists
     * of ReportUserActivityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportUserActivityDataVector()
     * @throws            SQLException
     */
    public static ReportUserActivityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT REPORT_USER_ACTIVITY_ID,STORE_ID,STORE_NAME,SESSION_ID,USER_NAME,ACTION_CLASS,ACTION,HTTP_START_TIME,ACTION_START_TIME,ACTION_END_TIME,HTTP_END_TIME,ACTION_RESULT,HTTP_RESULT,ACTION_DURATION,HTTP_DURATION,REFERER,PARAMS,FINISH_FILE,SERVER_NAME,REQUEST_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT RPT_REPORT_USER_ACTIVITY.REPORT_USER_ACTIVITY_ID,RPT_REPORT_USER_ACTIVITY.STORE_ID,RPT_REPORT_USER_ACTIVITY.STORE_NAME,RPT_REPORT_USER_ACTIVITY.SESSION_ID,RPT_REPORT_USER_ACTIVITY.USER_NAME,RPT_REPORT_USER_ACTIVITY.ACTION_CLASS,RPT_REPORT_USER_ACTIVITY.ACTION,RPT_REPORT_USER_ACTIVITY.HTTP_START_TIME,RPT_REPORT_USER_ACTIVITY.ACTION_START_TIME,RPT_REPORT_USER_ACTIVITY.ACTION_END_TIME,RPT_REPORT_USER_ACTIVITY.HTTP_END_TIME,RPT_REPORT_USER_ACTIVITY.ACTION_RESULT,RPT_REPORT_USER_ACTIVITY.HTTP_RESULT,RPT_REPORT_USER_ACTIVITY.ACTION_DURATION,RPT_REPORT_USER_ACTIVITY.HTTP_DURATION,RPT_REPORT_USER_ACTIVITY.REFERER,RPT_REPORT_USER_ACTIVITY.PARAMS,RPT_REPORT_USER_ACTIVITY.FINISH_FILE,RPT_REPORT_USER_ACTIVITY.SERVER_NAME,RPT_REPORT_USER_ACTIVITY.REQUEST_ID,RPT_REPORT_USER_ACTIVITY.ADD_DATE,RPT_REPORT_USER_ACTIVITY.ADD_BY,RPT_REPORT_USER_ACTIVITY.MOD_DATE,RPT_REPORT_USER_ACTIVITY.MOD_BY FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause("RPT_REPORT_USER_ACTIVITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!RPT_REPORT_USER_ACTIVITY.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        ReportUserActivityDataVector v = new ReportUserActivityDataVector();
        while (rs.next()) {
            ReportUserActivityData x = ReportUserActivityData.createValue();
            
            x.setReportUserActivityId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setStoreName(rs.getString(3));
            x.setSessionId(rs.getString(4));
            x.setUserName(rs.getString(5));
            x.setActionClass(rs.getString(6));
            x.setAction(rs.getString(7));
            x.setHttpStartTime(rs.getTimestamp(8));
            x.setActionStartTime(rs.getTimestamp(9));
            x.setActionEndTime(rs.getTimestamp(10));
            x.setHttpEndTime(rs.getTimestamp(11));
            x.setActionResult(rs.getString(12));
            x.setHttpResult(rs.getString(13));
            x.setActionDuration(rs.getBigDecimal(14));
            x.setHttpDuration(rs.getBigDecimal(15));
            x.setReferer(rs.getString(16));
            x.setParams(rs.getString(17));
            x.setFinishFile(rs.getString(18));
            x.setServerName(rs.getString(19));
            x.setRequestId(rs.getString(20));
            x.setAddDate(rs.getTimestamp(21));
            x.setAddBy(rs.getString(22));
            x.setModDate(rs.getTimestamp(23));
            x.setModBy(rs.getString(24));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportUserActivityDataVector object that consists
     * of ReportUserActivityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportUserActivityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportUserActivityDataVector()
     * @throws            SQLException
     */
    public static ReportUserActivityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportUserActivityDataVector v = new ReportUserActivityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_USER_ACTIVITY_ID,STORE_ID,STORE_NAME,SESSION_ID,USER_NAME,ACTION_CLASS,ACTION,HTTP_START_TIME,ACTION_START_TIME,ACTION_END_TIME,HTTP_END_TIME,ACTION_RESULT,HTTP_RESULT,ACTION_DURATION,HTTP_DURATION,REFERER,PARAMS,FINISH_FILE,SERVER_NAME,REQUEST_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_USER_ACTIVITY WHERE REPORT_USER_ACTIVITY_ID IN (");

        if ( pIdVector.size() > 0 ) {
            sqlBuf.append(pIdVector.get(0).toString());
            int vecsize = pIdVector.size();
            for ( int idx = 1; idx < vecsize; idx++ ) {
                sqlBuf.append("," + pIdVector.get(idx).toString());
            }
            sqlBuf.append(")");


            String sql = sqlBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("SQL: " + sql);
            }

            Statement stmt = pCon.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            ReportUserActivityData x=null;
            while (rs.next()) {
                // build the object
                x=ReportUserActivityData.createValue();
                
                x.setReportUserActivityId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setStoreName(rs.getString(3));
                x.setSessionId(rs.getString(4));
                x.setUserName(rs.getString(5));
                x.setActionClass(rs.getString(6));
                x.setAction(rs.getString(7));
                x.setHttpStartTime(rs.getTimestamp(8));
                x.setActionStartTime(rs.getTimestamp(9));
                x.setActionEndTime(rs.getTimestamp(10));
                x.setHttpEndTime(rs.getTimestamp(11));
                x.setActionResult(rs.getString(12));
                x.setHttpResult(rs.getString(13));
                x.setActionDuration(rs.getBigDecimal(14));
                x.setHttpDuration(rs.getBigDecimal(15));
                x.setReferer(rs.getString(16));
                x.setParams(rs.getString(17));
                x.setFinishFile(rs.getString(18));
                x.setServerName(rs.getString(19));
                x.setRequestId(rs.getString(20));
                x.setAddDate(rs.getTimestamp(21));
                x.setAddBy(rs.getString(22));
                x.setModDate(rs.getTimestamp(23));
                x.setModBy(rs.getString(24));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportUserActivityDataVector object of all
     * ReportUserActivityData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportUserActivityDataVector()
     * @throws            SQLException
     */
    public static ReportUserActivityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_USER_ACTIVITY_ID,STORE_ID,STORE_NAME,SESSION_ID,USER_NAME,ACTION_CLASS,ACTION,HTTP_START_TIME,ACTION_START_TIME,ACTION_END_TIME,HTTP_END_TIME,ACTION_RESULT,HTTP_RESULT,ACTION_DURATION,HTTP_DURATION,REFERER,PARAMS,FINISH_FILE,SERVER_NAME,REQUEST_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_USER_ACTIVITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportUserActivityDataVector v = new ReportUserActivityDataVector();
        ReportUserActivityData x = null;
        while (rs.next()) {
            // build the object
            x = ReportUserActivityData.createValue();
            
            x.setReportUserActivityId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setStoreName(rs.getString(3));
            x.setSessionId(rs.getString(4));
            x.setUserName(rs.getString(5));
            x.setActionClass(rs.getString(6));
            x.setAction(rs.getString(7));
            x.setHttpStartTime(rs.getTimestamp(8));
            x.setActionStartTime(rs.getTimestamp(9));
            x.setActionEndTime(rs.getTimestamp(10));
            x.setHttpEndTime(rs.getTimestamp(11));
            x.setActionResult(rs.getString(12));
            x.setHttpResult(rs.getString(13));
            x.setActionDuration(rs.getBigDecimal(14));
            x.setHttpDuration(rs.getBigDecimal(15));
            x.setReferer(rs.getString(16));
            x.setParams(rs.getString(17));
            x.setFinishFile(rs.getString(18));
            x.setServerName(rs.getString(19));
            x.setRequestId(rs.getString(20));
            x.setAddDate(rs.getTimestamp(21));
            x.setAddBy(rs.getString(22));
            x.setModDate(rs.getTimestamp(23));
            x.setModBy(rs.getString(24));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportUserActivityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT REPORT_USER_ACTIVITY_ID FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT REPORT_USER_ACTIVITY_ID FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause("RPT_REPORT_USER_ACTIVITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!RPT_REPORT_USER_ACTIVITY.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of requested
     * objects in the database.
     * @param pCon An open database connection.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, String pIdName, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause("RPT_REPORT_USER_ACTIVITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!RPT_REPORT_USER_ACTIVITY.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }


    /**
     * Gets an sql statement to request ids of
     * objects in the database.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return String
     */
    public static String getSqlSelectIdOnly(String pIdName, DBCriteria pCriteria)
    {
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_USER_ACTIVITY");
                where = pCriteria.getSqlClause("RPT_REPORT_USER_ACTIVITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!RPT_REPORT_USER_ACTIVITY.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }


        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a ReportUserActivityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportUserActivityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ReportUserActivityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportUserActivityData insert(Connection pCon, ReportUserActivityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT RPT_REPORT_USER_ACTIVITY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_USER_ACTIVITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportUserActivityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_USER_ACTIVITY (REPORT_USER_ACTIVITY_ID,STORE_ID,STORE_NAME,SESSION_ID,USER_NAME,ACTION_CLASS,ACTION,HTTP_START_TIME,ACTION_START_TIME,ACTION_END_TIME,HTTP_END_TIME,ACTION_RESULT,HTTP_RESULT,ACTION_DURATION,HTTP_DURATION,REFERER,PARAMS,FINISH_FILE,SERVER_NAME,REQUEST_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getReportUserActivityId());
        pstmt.setInt(2,pData.getStoreId());
        pstmt.setString(3,pData.getStoreName());
        pstmt.setString(4,pData.getSessionId());
        pstmt.setString(5,pData.getUserName());
        pstmt.setString(6,pData.getActionClass());
        pstmt.setString(7,pData.getAction());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getHttpStartTime()));
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getActionStartTime()));
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getActionEndTime()));
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getHttpEndTime()));
        pstmt.setString(12,pData.getActionResult());
        pstmt.setString(13,pData.getHttpResult());
        pstmt.setBigDecimal(14,pData.getActionDuration());
        pstmt.setBigDecimal(15,pData.getHttpDuration());
        pstmt.setString(16,pData.getReferer());
        pstmt.setString(17,pData.getParams());
        pstmt.setString(18,pData.getFinishFile());
        pstmt.setString(19,pData.getServerName());
        pstmt.setString(20,pData.getRequestId());
        pstmt.setTimestamp(21,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(22,pData.getAddBy());
        pstmt.setTimestamp(23,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(24,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_USER_ACTIVITY_ID="+pData.getReportUserActivityId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   STORE_NAME="+pData.getStoreName());
            log.debug("SQL:   SESSION_ID="+pData.getSessionId());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   ACTION_CLASS="+pData.getActionClass());
            log.debug("SQL:   ACTION="+pData.getAction());
            log.debug("SQL:   HTTP_START_TIME="+pData.getHttpStartTime());
            log.debug("SQL:   ACTION_START_TIME="+pData.getActionStartTime());
            log.debug("SQL:   ACTION_END_TIME="+pData.getActionEndTime());
            log.debug("SQL:   HTTP_END_TIME="+pData.getHttpEndTime());
            log.debug("SQL:   ACTION_RESULT="+pData.getActionResult());
            log.debug("SQL:   HTTP_RESULT="+pData.getHttpResult());
            log.debug("SQL:   ACTION_DURATION="+pData.getActionDuration());
            log.debug("SQL:   HTTP_DURATION="+pData.getHttpDuration());
            log.debug("SQL:   REFERER="+pData.getReferer());
            log.debug("SQL:   PARAMS="+pData.getParams());
            log.debug("SQL:   FINISH_FILE="+pData.getFinishFile());
            log.debug("SQL:   SERVER_NAME="+pData.getServerName());
            log.debug("SQL:   REQUEST_ID="+pData.getRequestId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setReportUserActivityId(0);
        exceptionMessage=e.getMessage();
        }
        finally{
        pstmt.close();
        }

        if(exceptionMessage!=null) {
                 throw new SQLException(exceptionMessage);
        }

        return pData;
    }

    /**
     * Updates a ReportUserActivityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportUserActivityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportUserActivityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_USER_ACTIVITY SET STORE_ID = ?,STORE_NAME = ?,SESSION_ID = ?,USER_NAME = ?,ACTION_CLASS = ?,ACTION = ?,HTTP_START_TIME = ?,ACTION_START_TIME = ?,ACTION_END_TIME = ?,HTTP_END_TIME = ?,ACTION_RESULT = ?,HTTP_RESULT = ?,ACTION_DURATION = ?,HTTP_DURATION = ?,REFERER = ?,PARAMS = ?,FINISH_FILE = ?,SERVER_NAME = ?,REQUEST_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE REPORT_USER_ACTIVITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getStoreName());
        pstmt.setString(i++,pData.getSessionId());
        pstmt.setString(i++,pData.getUserName());
        pstmt.setString(i++,pData.getActionClass());
        pstmt.setString(i++,pData.getAction());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getHttpStartTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getActionStartTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getActionEndTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getHttpEndTime()));
        pstmt.setString(i++,pData.getActionResult());
        pstmt.setString(i++,pData.getHttpResult());
        pstmt.setBigDecimal(i++,pData.getActionDuration());
        pstmt.setBigDecimal(i++,pData.getHttpDuration());
        pstmt.setString(i++,pData.getReferer());
        pstmt.setString(i++,pData.getParams());
        pstmt.setString(i++,pData.getFinishFile());
        pstmt.setString(i++,pData.getServerName());
        pstmt.setString(i++,pData.getRequestId());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getReportUserActivityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   STORE_NAME="+pData.getStoreName());
            log.debug("SQL:   SESSION_ID="+pData.getSessionId());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   ACTION_CLASS="+pData.getActionClass());
            log.debug("SQL:   ACTION="+pData.getAction());
            log.debug("SQL:   HTTP_START_TIME="+pData.getHttpStartTime());
            log.debug("SQL:   ACTION_START_TIME="+pData.getActionStartTime());
            log.debug("SQL:   ACTION_END_TIME="+pData.getActionEndTime());
            log.debug("SQL:   HTTP_END_TIME="+pData.getHttpEndTime());
            log.debug("SQL:   ACTION_RESULT="+pData.getActionResult());
            log.debug("SQL:   HTTP_RESULT="+pData.getHttpResult());
            log.debug("SQL:   ACTION_DURATION="+pData.getActionDuration());
            log.debug("SQL:   HTTP_DURATION="+pData.getHttpDuration());
            log.debug("SQL:   REFERER="+pData.getReferer());
            log.debug("SQL:   PARAMS="+pData.getParams());
            log.debug("SQL:   FINISH_FILE="+pData.getFinishFile());
            log.debug("SQL:   SERVER_NAME="+pData.getServerName());
            log.debug("SQL:   REQUEST_ID="+pData.getRequestId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ReportUserActivityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportUserActivityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportUserActivityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_USER_ACTIVITY WHERE REPORT_USER_ACTIVITY_ID = " + pReportUserActivityId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportUserActivityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_USER_ACTIVITY");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
    }

    /**
     * Inserts a ReportUserActivityData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportUserActivityData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ReportUserActivityData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LRPT_REPORT_USER_ACTIVITY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "REPORT_USER_ACTIVITY_ID,STORE_ID,STORE_NAME,SESSION_ID,USER_NAME,ACTION_CLASS,ACTION,HTTP_START_TIME,ACTION_START_TIME,ACTION_END_TIME,HTTP_END_TIME,ACTION_RESULT,HTTP_RESULT,ACTION_DURATION,HTTP_DURATION,REFERER,PARAMS,FINISH_FILE,SERVER_NAME,REQUEST_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getReportUserActivityId());
        pstmt.setInt(2+4,pData.getStoreId());
        pstmt.setString(3+4,pData.getStoreName());
        pstmt.setString(4+4,pData.getSessionId());
        pstmt.setString(5+4,pData.getUserName());
        pstmt.setString(6+4,pData.getActionClass());
        pstmt.setString(7+4,pData.getAction());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getHttpStartTime()));
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getActionStartTime()));
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getActionEndTime()));
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getHttpEndTime()));
        pstmt.setString(12+4,pData.getActionResult());
        pstmt.setString(13+4,pData.getHttpResult());
        pstmt.setBigDecimal(14+4,pData.getActionDuration());
        pstmt.setBigDecimal(15+4,pData.getHttpDuration());
        pstmt.setString(16+4,pData.getReferer());
        pstmt.setString(17+4,pData.getParams());
        pstmt.setString(18+4,pData.getFinishFile());
        pstmt.setString(19+4,pData.getServerName());
        pstmt.setString(20+4,pData.getRequestId());
        pstmt.setTimestamp(21+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(22+4,pData.getAddBy());
        pstmt.setTimestamp(23+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(24+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ReportUserActivityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportUserActivityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ReportUserActivityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportUserActivityData insert(Connection pCon, ReportUserActivityData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ReportUserActivityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportUserActivityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportUserActivityData pData, boolean pLogFl)
        throws SQLException {
        ReportUserActivityData oldData = null;
        if(pLogFl) {
          int id = pData.getReportUserActivityId();
          try {
          oldData = ReportUserActivityDataAccess.select(pCon,id);
          } catch(DataNotFoundException exc) {}
        }
        int n = update(pCon,pData);
        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, oldData, millis, "U", "O");
          insertLog(pCon, pData, millis, "U", "N");
        }
        return n;
    }

    /**
     * Deletes a ReportUserActivityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportUserActivityId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportUserActivityId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LRPT_REPORT_USER_ACTIVITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM RPT_REPORT_USER_ACTIVITY d WHERE REPORT_USER_ACTIVITY_ID = " + pReportUserActivityId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pReportUserActivityId);
        return n;
     }

    /**
     * Deletes ReportUserActivityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria, boolean pLogFl)
        throws SQLException {
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          StringBuffer sqlBuf =
             new StringBuffer("INSERT INTO LRPT_REPORT_USER_ACTIVITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM RPT_REPORT_USER_ACTIVITY d ");
          String where = pCriteria.getSqlClause();
          sqlBuf.append(" WHERE ");
          sqlBuf.append(where);

          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlBuf.toString());
          stmt.close();
        }
        int n = remove(pCon,pCriteria);
        return n;
    }
///////////////////////////////////////////////
}

