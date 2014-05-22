
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AllocatedCategoryDataAccess
 * Description:  This class is used to build access methods to the CLW_ALLOCATED_CATEGORY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AllocatedCategoryData;
import com.cleanwise.service.api.value.AllocatedCategoryDataVector;
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
 * <code>AllocatedCategoryDataAccess</code>
 */
public class AllocatedCategoryDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AllocatedCategoryDataAccess.class.getName());

    /** <code>CLW_ALLOCATED_CATEGORY</code> table name */
	/* Primary key: ALLOCATED_CATEGORY_ID */
	
    public static final String CLW_ALLOCATED_CATEGORY = "CLW_ALLOCATED_CATEGORY";
    
    /** <code>ALLOCATED_CATEGORY_ID</code> ALLOCATED_CATEGORY_ID column of table CLW_ALLOCATED_CATEGORY */
    public static final String ALLOCATED_CATEGORY_ID = "ALLOCATED_CATEGORY_ID";
    /** <code>ESTIMATOR_FACILITY_ID</code> ESTIMATOR_FACILITY_ID column of table CLW_ALLOCATED_CATEGORY */
    public static final String ESTIMATOR_FACILITY_ID = "ESTIMATOR_FACILITY_ID";
    /** <code>NAME</code> NAME column of table CLW_ALLOCATED_CATEGORY */
    public static final String NAME = "NAME";
    /** <code>PERCENT</code> PERCENT column of table CLW_ALLOCATED_CATEGORY */
    public static final String PERCENT = "PERCENT";
    /** <code>SEQ_NUM</code> SEQ_NUM column of table CLW_ALLOCATED_CATEGORY */
    public static final String SEQ_NUM = "SEQ_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ALLOCATED_CATEGORY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ALLOCATED_CATEGORY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ALLOCATED_CATEGORY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ALLOCATED_CATEGORY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public AllocatedCategoryDataAccess()
    {
    }

    /**
     * Gets a AllocatedCategoryData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAllocatedCategoryId The key requested.
     * @return new AllocatedCategoryData()
     * @throws            SQLException
     */
    public static AllocatedCategoryData select(Connection pCon, int pAllocatedCategoryId)
        throws SQLException, DataNotFoundException {
        AllocatedCategoryData x=null;
        String sql="SELECT ALLOCATED_CATEGORY_ID,ESTIMATOR_FACILITY_ID,NAME,PERCENT,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ALLOCATED_CATEGORY WHERE ALLOCATED_CATEGORY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAllocatedCategoryId=" + pAllocatedCategoryId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAllocatedCategoryId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AllocatedCategoryData.createValue();
            
            x.setAllocatedCategoryId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setPercent(rs.getBigDecimal(4));
            x.setSeqNum(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ALLOCATED_CATEGORY_ID :" + pAllocatedCategoryId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AllocatedCategoryDataVector object that consists
     * of AllocatedCategoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AllocatedCategoryDataVector()
     * @throws            SQLException
     */
    public static AllocatedCategoryDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AllocatedCategoryData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ALLOCATED_CATEGORY.ALLOCATED_CATEGORY_ID,CLW_ALLOCATED_CATEGORY.ESTIMATOR_FACILITY_ID,CLW_ALLOCATED_CATEGORY.NAME,CLW_ALLOCATED_CATEGORY.PERCENT,CLW_ALLOCATED_CATEGORY.SEQ_NUM,CLW_ALLOCATED_CATEGORY.ADD_DATE,CLW_ALLOCATED_CATEGORY.ADD_BY,CLW_ALLOCATED_CATEGORY.MOD_DATE,CLW_ALLOCATED_CATEGORY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AllocatedCategoryData Object.
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
    *@returns a populated AllocatedCategoryData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AllocatedCategoryData x = AllocatedCategoryData.createValue();
         
         x.setAllocatedCategoryId(rs.getInt(1+offset));
         x.setEstimatorFacilityId(rs.getInt(2+offset));
         x.setName(rs.getString(3+offset));
         x.setPercent(rs.getBigDecimal(4+offset));
         x.setSeqNum(rs.getInt(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AllocatedCategoryData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a AllocatedCategoryDataVector object that consists
     * of AllocatedCategoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AllocatedCategoryDataVector()
     * @throws            SQLException
     */
    public static AllocatedCategoryDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ALLOCATED_CATEGORY_ID,ESTIMATOR_FACILITY_ID,NAME,PERCENT,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ALLOCATED_CATEGORY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ALLOCATED_CATEGORY.ALLOCATED_CATEGORY_ID,CLW_ALLOCATED_CATEGORY.ESTIMATOR_FACILITY_ID,CLW_ALLOCATED_CATEGORY.NAME,CLW_ALLOCATED_CATEGORY.PERCENT,CLW_ALLOCATED_CATEGORY.SEQ_NUM,CLW_ALLOCATED_CATEGORY.ADD_DATE,CLW_ALLOCATED_CATEGORY.ADD_BY,CLW_ALLOCATED_CATEGORY.MOD_DATE,CLW_ALLOCATED_CATEGORY.MOD_BY FROM CLW_ALLOCATED_CATEGORY");
                where = pCriteria.getSqlClause("CLW_ALLOCATED_CATEGORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ALLOCATED_CATEGORY.equals(otherTable)){
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
        AllocatedCategoryDataVector v = new AllocatedCategoryDataVector();
        while (rs.next()) {
            AllocatedCategoryData x = AllocatedCategoryData.createValue();
            
            x.setAllocatedCategoryId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setPercent(rs.getBigDecimal(4));
            x.setSeqNum(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a AllocatedCategoryDataVector object that consists
     * of AllocatedCategoryData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AllocatedCategoryData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AllocatedCategoryDataVector()
     * @throws            SQLException
     */
    public static AllocatedCategoryDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AllocatedCategoryDataVector v = new AllocatedCategoryDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ALLOCATED_CATEGORY_ID,ESTIMATOR_FACILITY_ID,NAME,PERCENT,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ALLOCATED_CATEGORY WHERE ALLOCATED_CATEGORY_ID IN (");

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
            AllocatedCategoryData x=null;
            while (rs.next()) {
                // build the object
                x=AllocatedCategoryData.createValue();
                
                x.setAllocatedCategoryId(rs.getInt(1));
                x.setEstimatorFacilityId(rs.getInt(2));
                x.setName(rs.getString(3));
                x.setPercent(rs.getBigDecimal(4));
                x.setSeqNum(rs.getInt(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a AllocatedCategoryDataVector object of all
     * AllocatedCategoryData objects in the database.
     * @param pCon An open database connection.
     * @return new AllocatedCategoryDataVector()
     * @throws            SQLException
     */
    public static AllocatedCategoryDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ALLOCATED_CATEGORY_ID,ESTIMATOR_FACILITY_ID,NAME,PERCENT,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ALLOCATED_CATEGORY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AllocatedCategoryDataVector v = new AllocatedCategoryDataVector();
        AllocatedCategoryData x = null;
        while (rs.next()) {
            // build the object
            x = AllocatedCategoryData.createValue();
            
            x.setAllocatedCategoryId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setPercent(rs.getBigDecimal(4));
            x.setSeqNum(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * AllocatedCategoryData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ALLOCATED_CATEGORY_ID FROM CLW_ALLOCATED_CATEGORY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ALLOCATED_CATEGORY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ALLOCATED_CATEGORY");
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
     * Inserts a AllocatedCategoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllocatedCategoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AllocatedCategoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AllocatedCategoryData insert(Connection pCon, AllocatedCategoryData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ALLOCATED_CATEGORY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ALLOCATED_CATEGORY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAllocatedCategoryId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ALLOCATED_CATEGORY (ALLOCATED_CATEGORY_ID,ESTIMATOR_FACILITY_ID,NAME,PERCENT,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAllocatedCategoryId());
        pstmt.setInt(2,pData.getEstimatorFacilityId());
        pstmt.setString(3,pData.getName());
        pstmt.setBigDecimal(4,pData.getPercent());
        pstmt.setInt(5,pData.getSeqNum());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ALLOCATED_CATEGORY_ID="+pData.getAllocatedCategoryId());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   PERCENT="+pData.getPercent());
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
        pData.setAllocatedCategoryId(0);
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
     * Updates a AllocatedCategoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AllocatedCategoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AllocatedCategoryData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ALLOCATED_CATEGORY SET ESTIMATOR_FACILITY_ID = ?,NAME = ?,PERCENT = ?,SEQ_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ALLOCATED_CATEGORY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getEstimatorFacilityId());
        pstmt.setString(i++,pData.getName());
        pstmt.setBigDecimal(i++,pData.getPercent());
        pstmt.setInt(i++,pData.getSeqNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getAllocatedCategoryId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   PERCENT="+pData.getPercent());
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
     * Deletes a AllocatedCategoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAllocatedCategoryId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAllocatedCategoryId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ALLOCATED_CATEGORY WHERE ALLOCATED_CATEGORY_ID = " + pAllocatedCategoryId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AllocatedCategoryData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ALLOCATED_CATEGORY");
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
     * Inserts a AllocatedCategoryData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllocatedCategoryData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AllocatedCategoryData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ALLOCATED_CATEGORY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ALLOCATED_CATEGORY_ID,ESTIMATOR_FACILITY_ID,NAME,PERCENT,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAllocatedCategoryId());
        pstmt.setInt(2+4,pData.getEstimatorFacilityId());
        pstmt.setString(3+4,pData.getName());
        pstmt.setBigDecimal(4+4,pData.getPercent());
        pstmt.setInt(5+4,pData.getSeqNum());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AllocatedCategoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllocatedCategoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AllocatedCategoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AllocatedCategoryData insert(Connection pCon, AllocatedCategoryData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AllocatedCategoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AllocatedCategoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AllocatedCategoryData pData, boolean pLogFl)
        throws SQLException {
        AllocatedCategoryData oldData = null;
        if(pLogFl) {
          int id = pData.getAllocatedCategoryId();
          try {
          oldData = AllocatedCategoryDataAccess.select(pCon,id);
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
     * Deletes a AllocatedCategoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAllocatedCategoryId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAllocatedCategoryId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ALLOCATED_CATEGORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ALLOCATED_CATEGORY d WHERE ALLOCATED_CATEGORY_ID = " + pAllocatedCategoryId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAllocatedCategoryId);
        return n;
     }

    /**
     * Deletes AllocatedCategoryData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ALLOCATED_CATEGORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ALLOCATED_CATEGORY d ");
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

