
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CleaningScheduleDataAccess
 * Description:  This class is used to build access methods to the CLW_CLEANING_SCHEDULE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CleaningScheduleData;
import com.cleanwise.service.api.value.CleaningScheduleDataVector;
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
 * <code>CleaningScheduleDataAccess</code>
 */
public class CleaningScheduleDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CleaningScheduleDataAccess.class.getName());

    /** <code>CLW_CLEANING_SCHEDULE</code> table name */
	/* Primary key: CLEANING_SCHEDULE_ID */
	
    public static final String CLW_CLEANING_SCHEDULE = "CLW_CLEANING_SCHEDULE";
    
    /** <code>CLEANING_SCHEDULE_ID</code> CLEANING_SCHEDULE_ID column of table CLW_CLEANING_SCHEDULE */
    public static final String CLEANING_SCHEDULE_ID = "CLEANING_SCHEDULE_ID";
    /** <code>NAME</code> NAME column of table CLW_CLEANING_SCHEDULE */
    public static final String NAME = "NAME";
    /** <code>ESTIMATOR_FACILITY_ID</code> ESTIMATOR_FACILITY_ID column of table CLW_CLEANING_SCHEDULE */
    public static final String ESTIMATOR_FACILITY_ID = "ESTIMATOR_FACILITY_ID";
    /** <code>CLEANING_SCHEDULE_CD</code> CLEANING_SCHEDULE_CD column of table CLW_CLEANING_SCHEDULE */
    public static final String CLEANING_SCHEDULE_CD = "CLEANING_SCHEDULE_CD";
    /** <code>FREQUENCY</code> FREQUENCY column of table CLW_CLEANING_SCHEDULE */
    public static final String FREQUENCY = "FREQUENCY";
    /** <code>TIME_PERIOD_CD</code> TIME_PERIOD_CD column of table CLW_CLEANING_SCHEDULE */
    public static final String TIME_PERIOD_CD = "TIME_PERIOD_CD";
    /** <code>SEQ_NUM</code> SEQ_NUM column of table CLW_CLEANING_SCHEDULE */
    public static final String SEQ_NUM = "SEQ_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CLEANING_SCHEDULE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CLEANING_SCHEDULE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CLEANING_SCHEDULE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CLEANING_SCHEDULE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CleaningScheduleDataAccess()
    {
    }

    /**
     * Gets a CleaningScheduleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCleaningScheduleId The key requested.
     * @return new CleaningScheduleData()
     * @throws            SQLException
     */
    public static CleaningScheduleData select(Connection pCon, int pCleaningScheduleId)
        throws SQLException, DataNotFoundException {
        CleaningScheduleData x=null;
        String sql="SELECT CLEANING_SCHEDULE_ID,NAME,ESTIMATOR_FACILITY_ID,CLEANING_SCHEDULE_CD,FREQUENCY,TIME_PERIOD_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHEDULE WHERE CLEANING_SCHEDULE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCleaningScheduleId=" + pCleaningScheduleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCleaningScheduleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CleaningScheduleData.createValue();
            
            x.setCleaningScheduleId(rs.getInt(1));
            x.setName(rs.getString(2));
            x.setEstimatorFacilityId(rs.getInt(3));
            x.setCleaningScheduleCd(rs.getString(4));
            x.setFrequency(rs.getBigDecimal(5));
            x.setTimePeriodCd(rs.getString(6));
            x.setSeqNum(rs.getInt(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CLEANING_SCHEDULE_ID :" + pCleaningScheduleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CleaningScheduleDataVector object that consists
     * of CleaningScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CleaningScheduleDataVector()
     * @throws            SQLException
     */
    public static CleaningScheduleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CleaningScheduleData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CLEANING_SCHEDULE.CLEANING_SCHEDULE_ID,CLW_CLEANING_SCHEDULE.NAME,CLW_CLEANING_SCHEDULE.ESTIMATOR_FACILITY_ID,CLW_CLEANING_SCHEDULE.CLEANING_SCHEDULE_CD,CLW_CLEANING_SCHEDULE.FREQUENCY,CLW_CLEANING_SCHEDULE.TIME_PERIOD_CD,CLW_CLEANING_SCHEDULE.SEQ_NUM,CLW_CLEANING_SCHEDULE.ADD_DATE,CLW_CLEANING_SCHEDULE.ADD_BY,CLW_CLEANING_SCHEDULE.MOD_DATE,CLW_CLEANING_SCHEDULE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CleaningScheduleData Object.
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
    *@returns a populated CleaningScheduleData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CleaningScheduleData x = CleaningScheduleData.createValue();
         
         x.setCleaningScheduleId(rs.getInt(1+offset));
         x.setName(rs.getString(2+offset));
         x.setEstimatorFacilityId(rs.getInt(3+offset));
         x.setCleaningScheduleCd(rs.getString(4+offset));
         x.setFrequency(rs.getBigDecimal(5+offset));
         x.setTimePeriodCd(rs.getString(6+offset));
         x.setSeqNum(rs.getInt(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CleaningScheduleData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a CleaningScheduleDataVector object that consists
     * of CleaningScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CleaningScheduleDataVector()
     * @throws            SQLException
     */
    public static CleaningScheduleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CLEANING_SCHEDULE_ID,NAME,ESTIMATOR_FACILITY_ID,CLEANING_SCHEDULE_CD,FREQUENCY,TIME_PERIOD_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHEDULE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CLEANING_SCHEDULE.CLEANING_SCHEDULE_ID,CLW_CLEANING_SCHEDULE.NAME,CLW_CLEANING_SCHEDULE.ESTIMATOR_FACILITY_ID,CLW_CLEANING_SCHEDULE.CLEANING_SCHEDULE_CD,CLW_CLEANING_SCHEDULE.FREQUENCY,CLW_CLEANING_SCHEDULE.TIME_PERIOD_CD,CLW_CLEANING_SCHEDULE.SEQ_NUM,CLW_CLEANING_SCHEDULE.ADD_DATE,CLW_CLEANING_SCHEDULE.ADD_BY,CLW_CLEANING_SCHEDULE.MOD_DATE,CLW_CLEANING_SCHEDULE.MOD_BY FROM CLW_CLEANING_SCHEDULE");
                where = pCriteria.getSqlClause("CLW_CLEANING_SCHEDULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CLEANING_SCHEDULE.equals(otherTable)){
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
        CleaningScheduleDataVector v = new CleaningScheduleDataVector();
        while (rs.next()) {
            CleaningScheduleData x = CleaningScheduleData.createValue();
            
            x.setCleaningScheduleId(rs.getInt(1));
            x.setName(rs.getString(2));
            x.setEstimatorFacilityId(rs.getInt(3));
            x.setCleaningScheduleCd(rs.getString(4));
            x.setFrequency(rs.getBigDecimal(5));
            x.setTimePeriodCd(rs.getString(6));
            x.setSeqNum(rs.getInt(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CleaningScheduleDataVector object that consists
     * of CleaningScheduleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CleaningScheduleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CleaningScheduleDataVector()
     * @throws            SQLException
     */
    public static CleaningScheduleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CleaningScheduleDataVector v = new CleaningScheduleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CLEANING_SCHEDULE_ID,NAME,ESTIMATOR_FACILITY_ID,CLEANING_SCHEDULE_CD,FREQUENCY,TIME_PERIOD_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHEDULE WHERE CLEANING_SCHEDULE_ID IN (");

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
            CleaningScheduleData x=null;
            while (rs.next()) {
                // build the object
                x=CleaningScheduleData.createValue();
                
                x.setCleaningScheduleId(rs.getInt(1));
                x.setName(rs.getString(2));
                x.setEstimatorFacilityId(rs.getInt(3));
                x.setCleaningScheduleCd(rs.getString(4));
                x.setFrequency(rs.getBigDecimal(5));
                x.setTimePeriodCd(rs.getString(6));
                x.setSeqNum(rs.getInt(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CleaningScheduleDataVector object of all
     * CleaningScheduleData objects in the database.
     * @param pCon An open database connection.
     * @return new CleaningScheduleDataVector()
     * @throws            SQLException
     */
    public static CleaningScheduleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CLEANING_SCHEDULE_ID,NAME,ESTIMATOR_FACILITY_ID,CLEANING_SCHEDULE_CD,FREQUENCY,TIME_PERIOD_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_SCHEDULE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CleaningScheduleDataVector v = new CleaningScheduleDataVector();
        CleaningScheduleData x = null;
        while (rs.next()) {
            // build the object
            x = CleaningScheduleData.createValue();
            
            x.setCleaningScheduleId(rs.getInt(1));
            x.setName(rs.getString(2));
            x.setEstimatorFacilityId(rs.getInt(3));
            x.setCleaningScheduleCd(rs.getString(4));
            x.setFrequency(rs.getBigDecimal(5));
            x.setTimePeriodCd(rs.getString(6));
            x.setSeqNum(rs.getInt(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CleaningScheduleData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CLEANING_SCHEDULE_ID FROM CLW_CLEANING_SCHEDULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CLEANING_SCHEDULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CLEANING_SCHEDULE");
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
     * Inserts a CleaningScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CleaningScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CleaningScheduleData insert(Connection pCon, CleaningScheduleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CLEANING_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CLEANING_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCleaningScheduleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CLEANING_SCHEDULE (CLEANING_SCHEDULE_ID,NAME,ESTIMATOR_FACILITY_ID,CLEANING_SCHEDULE_CD,FREQUENCY,TIME_PERIOD_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCleaningScheduleId());
        pstmt.setString(2,pData.getName());
        if (pData.getEstimatorFacilityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getEstimatorFacilityId());
        }

        pstmt.setString(4,pData.getCleaningScheduleCd());
        pstmt.setBigDecimal(5,pData.getFrequency());
        pstmt.setString(6,pData.getTimePeriodCd());
        pstmt.setInt(7,pData.getSeqNum());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CLEANING_SCHEDULE_ID="+pData.getCleaningScheduleId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   CLEANING_SCHEDULE_CD="+pData.getCleaningScheduleCd());
            log.debug("SQL:   FREQUENCY="+pData.getFrequency());
            log.debug("SQL:   TIME_PERIOD_CD="+pData.getTimePeriodCd());
            log.debug("SQL:   SEQ_NUM="+pData.getSeqNum());
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
        pData.setCleaningScheduleId(0);
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
     * Updates a CleaningScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CleaningScheduleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CLEANING_SCHEDULE SET NAME = ?,ESTIMATOR_FACILITY_ID = ?,CLEANING_SCHEDULE_CD = ?,FREQUENCY = ?,TIME_PERIOD_CD = ?,SEQ_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CLEANING_SCHEDULE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getName());
        if (pData.getEstimatorFacilityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getEstimatorFacilityId());
        }

        pstmt.setString(i++,pData.getCleaningScheduleCd());
        pstmt.setBigDecimal(i++,pData.getFrequency());
        pstmt.setString(i++,pData.getTimePeriodCd());
        pstmt.setInt(i++,pData.getSeqNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCleaningScheduleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   CLEANING_SCHEDULE_CD="+pData.getCleaningScheduleCd());
            log.debug("SQL:   FREQUENCY="+pData.getFrequency());
            log.debug("SQL:   TIME_PERIOD_CD="+pData.getTimePeriodCd());
            log.debug("SQL:   SEQ_NUM="+pData.getSeqNum());
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
     * Deletes a CleaningScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCleaningScheduleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCleaningScheduleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CLEANING_SCHEDULE WHERE CLEANING_SCHEDULE_ID = " + pCleaningScheduleId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CleaningScheduleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CLEANING_SCHEDULE");
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
     * Inserts a CleaningScheduleData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningScheduleData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CleaningScheduleData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CLEANING_SCHEDULE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CLEANING_SCHEDULE_ID,NAME,ESTIMATOR_FACILITY_ID,CLEANING_SCHEDULE_CD,FREQUENCY,TIME_PERIOD_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCleaningScheduleId());
        pstmt.setString(2+4,pData.getName());
        if (pData.getEstimatorFacilityId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getEstimatorFacilityId());
        }

        pstmt.setString(4+4,pData.getCleaningScheduleCd());
        pstmt.setBigDecimal(5+4,pData.getFrequency());
        pstmt.setString(6+4,pData.getTimePeriodCd());
        pstmt.setInt(7+4,pData.getSeqNum());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CleaningScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CleaningScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CleaningScheduleData insert(Connection pCon, CleaningScheduleData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CleaningScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CleaningScheduleData pData, boolean pLogFl)
        throws SQLException {
        CleaningScheduleData oldData = null;
        if(pLogFl) {
          int id = pData.getCleaningScheduleId();
          try {
          oldData = CleaningScheduleDataAccess.select(pCon,id);
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
     * Deletes a CleaningScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCleaningScheduleId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCleaningScheduleId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CLEANING_SCHEDULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CLEANING_SCHEDULE d WHERE CLEANING_SCHEDULE_ID = " + pCleaningScheduleId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCleaningScheduleId);
        return n;
     }

    /**
     * Deletes CleaningScheduleData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CLEANING_SCHEDULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CLEANING_SCHEDULE d ");
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

