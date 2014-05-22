
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CleaningSchedFilterDataAccess
 * Description:  This class is used to build access methods to the CLW_CLEANING_SCHED_FILTER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CleaningSchedFilterData;
import com.cleanwise.service.api.value.CleaningSchedFilterDataVector;
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
 * <code>CleaningSchedFilterDataAccess</code>
 */
public class CleaningSchedFilterDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CleaningSchedFilterDataAccess.class.getName());

    /** <code>CLW_CLEANING_SCHED_FILTER</code> table name */
	/* Primary key: CLEANING_SCHED_FILTER_ID */
	
    public static final String CLW_CLEANING_SCHED_FILTER = "CLW_CLEANING_SCHED_FILTER";
    
    /** <code>CLEANING_SCHED_FILTER_ID</code> CLEANING_SCHED_FILTER_ID column of table CLW_CLEANING_SCHED_FILTER */
    public static final String CLEANING_SCHED_FILTER_ID = "CLEANING_SCHED_FILTER_ID";
    /** <code>CLEANING_SCHEDULE_ID</code> CLEANING_SCHEDULE_ID column of table CLW_CLEANING_SCHED_FILTER */
    public static final String CLEANING_SCHEDULE_ID = "CLEANING_SCHEDULE_ID";
    /** <code>PROPERTY_NAME</code> PROPERTY_NAME column of table CLW_CLEANING_SCHED_FILTER */
    public static final String PROPERTY_NAME = "PROPERTY_NAME";
    /** <code>FILTER_OPERATOR_CD</code> FILTER_OPERATOR_CD column of table CLW_CLEANING_SCHED_FILTER */
    public static final String FILTER_OPERATOR_CD = "FILTER_OPERATOR_CD";
    /** <code>PROPERTY_VALUE</code> PROPERTY_VALUE column of table CLW_CLEANING_SCHED_FILTER */
    public static final String PROPERTY_VALUE = "PROPERTY_VALUE";
    /** <code>FILTER_GROUP_NUM</code> FILTER_GROUP_NUM column of table CLW_CLEANING_SCHED_FILTER */
    public static final String FILTER_GROUP_NUM = "FILTER_GROUP_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CLEANING_SCHED_FILTER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CLEANING_SCHED_FILTER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CLEANING_SCHED_FILTER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CLEANING_SCHED_FILTER */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CleaningSchedFilterDataAccess()
    {
    }

    /**
     * Gets a CleaningSchedFilterData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCleaningSchedFilterId The key requested.
     * @return new CleaningSchedFilterData()
     * @throws            SQLException
     */
    public static CleaningSchedFilterData select(Connection pCon, int pCleaningSchedFilterId)
        throws SQLException, DataNotFoundException {
        CleaningSchedFilterData x=null;
        String sql="SELECT CLEANING_SCHED_FILTER_ID,CLEANING_SCHEDULE_ID,PROPERTY_NAME,FILTER_OPERATOR_CD,PROPERTY_VALUE,FILTER_GROUP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHED_FILTER WHERE CLEANING_SCHED_FILTER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCleaningSchedFilterId=" + pCleaningSchedFilterId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCleaningSchedFilterId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CleaningSchedFilterData.createValue();
            
            x.setCleaningSchedFilterId(rs.getInt(1));
            x.setCleaningScheduleId(rs.getInt(2));
            x.setPropertyName(rs.getString(3));
            x.setFilterOperatorCd(rs.getString(4));
            x.setPropertyValue(rs.getString(5));
            x.setFilterGroupNum(rs.getInt(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CLEANING_SCHED_FILTER_ID :" + pCleaningSchedFilterId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CleaningSchedFilterDataVector object that consists
     * of CleaningSchedFilterData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CleaningSchedFilterDataVector()
     * @throws            SQLException
     */
    public static CleaningSchedFilterDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CleaningSchedFilterData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CLEANING_SCHED_FILTER.CLEANING_SCHED_FILTER_ID,CLW_CLEANING_SCHED_FILTER.CLEANING_SCHEDULE_ID,CLW_CLEANING_SCHED_FILTER.PROPERTY_NAME,CLW_CLEANING_SCHED_FILTER.FILTER_OPERATOR_CD,CLW_CLEANING_SCHED_FILTER.PROPERTY_VALUE,CLW_CLEANING_SCHED_FILTER.FILTER_GROUP_NUM,CLW_CLEANING_SCHED_FILTER.ADD_DATE,CLW_CLEANING_SCHED_FILTER.ADD_BY,CLW_CLEANING_SCHED_FILTER.MOD_DATE,CLW_CLEANING_SCHED_FILTER.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CleaningSchedFilterData Object.
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
    *@returns a populated CleaningSchedFilterData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CleaningSchedFilterData x = CleaningSchedFilterData.createValue();
         
         x.setCleaningSchedFilterId(rs.getInt(1+offset));
         x.setCleaningScheduleId(rs.getInt(2+offset));
         x.setPropertyName(rs.getString(3+offset));
         x.setFilterOperatorCd(rs.getString(4+offset));
         x.setPropertyValue(rs.getString(5+offset));
         x.setFilterGroupNum(rs.getInt(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CleaningSchedFilterData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a CleaningSchedFilterDataVector object that consists
     * of CleaningSchedFilterData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CleaningSchedFilterDataVector()
     * @throws            SQLException
     */
    public static CleaningSchedFilterDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CLEANING_SCHED_FILTER_ID,CLEANING_SCHEDULE_ID,PROPERTY_NAME,FILTER_OPERATOR_CD,PROPERTY_VALUE,FILTER_GROUP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHED_FILTER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CLEANING_SCHED_FILTER.CLEANING_SCHED_FILTER_ID,CLW_CLEANING_SCHED_FILTER.CLEANING_SCHEDULE_ID,CLW_CLEANING_SCHED_FILTER.PROPERTY_NAME,CLW_CLEANING_SCHED_FILTER.FILTER_OPERATOR_CD,CLW_CLEANING_SCHED_FILTER.PROPERTY_VALUE,CLW_CLEANING_SCHED_FILTER.FILTER_GROUP_NUM,CLW_CLEANING_SCHED_FILTER.ADD_DATE,CLW_CLEANING_SCHED_FILTER.ADD_BY,CLW_CLEANING_SCHED_FILTER.MOD_DATE,CLW_CLEANING_SCHED_FILTER.MOD_BY FROM CLW_CLEANING_SCHED_FILTER");
                where = pCriteria.getSqlClause("CLW_CLEANING_SCHED_FILTER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CLEANING_SCHED_FILTER.equals(otherTable)){
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
        CleaningSchedFilterDataVector v = new CleaningSchedFilterDataVector();
        while (rs.next()) {
            CleaningSchedFilterData x = CleaningSchedFilterData.createValue();
            
            x.setCleaningSchedFilterId(rs.getInt(1));
            x.setCleaningScheduleId(rs.getInt(2));
            x.setPropertyName(rs.getString(3));
            x.setFilterOperatorCd(rs.getString(4));
            x.setPropertyValue(rs.getString(5));
            x.setFilterGroupNum(rs.getInt(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CleaningSchedFilterDataVector object that consists
     * of CleaningSchedFilterData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CleaningSchedFilterData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CleaningSchedFilterDataVector()
     * @throws            SQLException
     */
    public static CleaningSchedFilterDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CleaningSchedFilterDataVector v = new CleaningSchedFilterDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CLEANING_SCHED_FILTER_ID,CLEANING_SCHEDULE_ID,PROPERTY_NAME,FILTER_OPERATOR_CD,PROPERTY_VALUE,FILTER_GROUP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHED_FILTER WHERE CLEANING_SCHED_FILTER_ID IN (");

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
            CleaningSchedFilterData x=null;
            while (rs.next()) {
                // build the object
                x=CleaningSchedFilterData.createValue();
                
                x.setCleaningSchedFilterId(rs.getInt(1));
                x.setCleaningScheduleId(rs.getInt(2));
                x.setPropertyName(rs.getString(3));
                x.setFilterOperatorCd(rs.getString(4));
                x.setPropertyValue(rs.getString(5));
                x.setFilterGroupNum(rs.getInt(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CleaningSchedFilterDataVector object of all
     * CleaningSchedFilterData objects in the database.
     * @param pCon An open database connection.
     * @return new CleaningSchedFilterDataVector()
     * @throws            SQLException
     */
    public static CleaningSchedFilterDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CLEANING_SCHED_FILTER_ID,CLEANING_SCHEDULE_ID,PROPERTY_NAME,FILTER_OPERATOR_CD,PROPERTY_VALUE,FILTER_GROUP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHED_FILTER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CleaningSchedFilterDataVector v = new CleaningSchedFilterDataVector();
        CleaningSchedFilterData x = null;
        while (rs.next()) {
            // build the object
            x = CleaningSchedFilterData.createValue();
            
            x.setCleaningSchedFilterId(rs.getInt(1));
            x.setCleaningScheduleId(rs.getInt(2));
            x.setPropertyName(rs.getString(3));
            x.setFilterOperatorCd(rs.getString(4));
            x.setPropertyValue(rs.getString(5));
            x.setFilterGroupNum(rs.getInt(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CleaningSchedFilterData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CLEANING_SCHED_FILTER_ID FROM CLW_CLEANING_SCHED_FILTER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CLEANING_SCHED_FILTER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CLEANING_SCHED_FILTER");
        String where = pCriteria.getSqlClause();
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
     * Inserts a CleaningSchedFilterData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningSchedFilterData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CleaningSchedFilterData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CleaningSchedFilterData insert(Connection pCon, CleaningSchedFilterData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CLEANING_SCHED_FILTER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CLEANING_SCHED_FILTER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCleaningSchedFilterId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CLEANING_SCHED_FILTER (CLEANING_SCHED_FILTER_ID,CLEANING_SCHEDULE_ID,PROPERTY_NAME,FILTER_OPERATOR_CD,PROPERTY_VALUE,FILTER_GROUP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCleaningSchedFilterId());
        pstmt.setInt(2,pData.getCleaningScheduleId());
        pstmt.setString(3,pData.getPropertyName());
        pstmt.setString(4,pData.getFilterOperatorCd());
        pstmt.setString(5,pData.getPropertyValue());
        pstmt.setInt(6,pData.getFilterGroupNum());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CLEANING_SCHED_FILTER_ID="+pData.getCleaningSchedFilterId());
            log.debug("SQL:   CLEANING_SCHEDULE_ID="+pData.getCleaningScheduleId());
            log.debug("SQL:   PROPERTY_NAME="+pData.getPropertyName());
            log.debug("SQL:   FILTER_OPERATOR_CD="+pData.getFilterOperatorCd());
            log.debug("SQL:   PROPERTY_VALUE="+pData.getPropertyValue());
            log.debug("SQL:   FILTER_GROUP_NUM="+pData.getFilterGroupNum());
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
        pData.setCleaningSchedFilterId(0);
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
     * Updates a CleaningSchedFilterData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningSchedFilterData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CleaningSchedFilterData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CLEANING_SCHED_FILTER SET CLEANING_SCHEDULE_ID = ?,PROPERTY_NAME = ?,FILTER_OPERATOR_CD = ?,PROPERTY_VALUE = ?,FILTER_GROUP_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CLEANING_SCHED_FILTER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCleaningScheduleId());
        pstmt.setString(i++,pData.getPropertyName());
        pstmt.setString(i++,pData.getFilterOperatorCd());
        pstmt.setString(i++,pData.getPropertyValue());
        pstmt.setInt(i++,pData.getFilterGroupNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCleaningSchedFilterId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CLEANING_SCHEDULE_ID="+pData.getCleaningScheduleId());
            log.debug("SQL:   PROPERTY_NAME="+pData.getPropertyName());
            log.debug("SQL:   FILTER_OPERATOR_CD="+pData.getFilterOperatorCd());
            log.debug("SQL:   PROPERTY_VALUE="+pData.getPropertyValue());
            log.debug("SQL:   FILTER_GROUP_NUM="+pData.getFilterGroupNum());
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
     * Deletes a CleaningSchedFilterData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCleaningSchedFilterId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCleaningSchedFilterId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CLEANING_SCHED_FILTER WHERE CLEANING_SCHED_FILTER_ID = " + pCleaningSchedFilterId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CleaningSchedFilterData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CLEANING_SCHED_FILTER");
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
     * Inserts a CleaningSchedFilterData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningSchedFilterData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CleaningSchedFilterData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CLEANING_SCHED_FILTER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CLEANING_SCHED_FILTER_ID,CLEANING_SCHEDULE_ID,PROPERTY_NAME,FILTER_OPERATOR_CD,PROPERTY_VALUE,FILTER_GROUP_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCleaningSchedFilterId());
        pstmt.setInt(2+4,pData.getCleaningScheduleId());
        pstmt.setString(3+4,pData.getPropertyName());
        pstmt.setString(4+4,pData.getFilterOperatorCd());
        pstmt.setString(5+4,pData.getPropertyValue());
        pstmt.setInt(6+4,pData.getFilterGroupNum());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CleaningSchedFilterData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningSchedFilterData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CleaningSchedFilterData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CleaningSchedFilterData insert(Connection pCon, CleaningSchedFilterData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CleaningSchedFilterData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningSchedFilterData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CleaningSchedFilterData pData, boolean pLogFl)
        throws SQLException {
        CleaningSchedFilterData oldData = null;
        if(pLogFl) {
          int id = pData.getCleaningSchedFilterId();
          try {
          oldData = CleaningSchedFilterDataAccess.select(pCon,id);
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
     * Deletes a CleaningSchedFilterData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCleaningSchedFilterId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCleaningSchedFilterId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CLEANING_SCHED_FILTER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CLEANING_SCHED_FILTER d WHERE CLEANING_SCHED_FILTER_ID = " + pCleaningSchedFilterId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCleaningSchedFilterId);
        return n;
     }

    /**
     * Deletes CleaningSchedFilterData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CLEANING_SCHED_FILTER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CLEANING_SCHED_FILTER d ");
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

