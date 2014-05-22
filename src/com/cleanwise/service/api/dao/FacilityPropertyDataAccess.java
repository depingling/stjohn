
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FacilityPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_FACILITY_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FacilityPropertyData;
import com.cleanwise.service.api.value.FacilityPropertyDataVector;
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
 * <code>FacilityPropertyDataAccess</code>
 */
public class FacilityPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(FacilityPropertyDataAccess.class.getName());

    /** <code>CLW_FACILITY_PROPERTY</code> table name */
	/* Primary key: FACILITY_PROPERTY_ID */
	
    public static final String CLW_FACILITY_PROPERTY = "CLW_FACILITY_PROPERTY";
    
    /** <code>FACILITY_PROPERTY_ID</code> FACILITY_PROPERTY_ID column of table CLW_FACILITY_PROPERTY */
    public static final String FACILITY_PROPERTY_ID = "FACILITY_PROPERTY_ID";
    /** <code>ESTIMATOR_FACILITY_ID</code> ESTIMATOR_FACILITY_ID column of table CLW_FACILITY_PROPERTY */
    public static final String ESTIMATOR_FACILITY_ID = "ESTIMATOR_FACILITY_ID";
    /** <code>PROPERTY_NAME</code> PROPERTY_NAME column of table CLW_FACILITY_PROPERTY */
    public static final String PROPERTY_NAME = "PROPERTY_NAME";
    /** <code>PROPERTY_VALUE</code> PROPERTY_VALUE column of table CLW_FACILITY_PROPERTY */
    public static final String PROPERTY_VALUE = "PROPERTY_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FACILITY_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FACILITY_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FACILITY_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FACILITY_PROPERTY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public FacilityPropertyDataAccess()
    {
    }

    /**
     * Gets a FacilityPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFacilityPropertyId The key requested.
     * @return new FacilityPropertyData()
     * @throws            SQLException
     */
    public static FacilityPropertyData select(Connection pCon, int pFacilityPropertyId)
        throws SQLException, DataNotFoundException {
        FacilityPropertyData x=null;
        String sql="SELECT FACILITY_PROPERTY_ID,ESTIMATOR_FACILITY_ID,PROPERTY_NAME,PROPERTY_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_PROPERTY WHERE FACILITY_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFacilityPropertyId=" + pFacilityPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFacilityPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FacilityPropertyData.createValue();
            
            x.setFacilityPropertyId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setPropertyName(rs.getString(3));
            x.setPropertyValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FACILITY_PROPERTY_ID :" + pFacilityPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FacilityPropertyDataVector object that consists
     * of FacilityPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FacilityPropertyDataVector()
     * @throws            SQLException
     */
    public static FacilityPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FacilityPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FACILITY_PROPERTY.FACILITY_PROPERTY_ID,CLW_FACILITY_PROPERTY.ESTIMATOR_FACILITY_ID,CLW_FACILITY_PROPERTY.PROPERTY_NAME,CLW_FACILITY_PROPERTY.PROPERTY_VALUE,CLW_FACILITY_PROPERTY.ADD_DATE,CLW_FACILITY_PROPERTY.ADD_BY,CLW_FACILITY_PROPERTY.MOD_DATE,CLW_FACILITY_PROPERTY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FacilityPropertyData Object.
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
    *@returns a populated FacilityPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FacilityPropertyData x = FacilityPropertyData.createValue();
         
         x.setFacilityPropertyId(rs.getInt(1+offset));
         x.setEstimatorFacilityId(rs.getInt(2+offset));
         x.setPropertyName(rs.getString(3+offset));
         x.setPropertyValue(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FacilityPropertyData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a FacilityPropertyDataVector object that consists
     * of FacilityPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FacilityPropertyDataVector()
     * @throws            SQLException
     */
    public static FacilityPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FACILITY_PROPERTY_ID,ESTIMATOR_FACILITY_ID,PROPERTY_NAME,PROPERTY_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FACILITY_PROPERTY.FACILITY_PROPERTY_ID,CLW_FACILITY_PROPERTY.ESTIMATOR_FACILITY_ID,CLW_FACILITY_PROPERTY.PROPERTY_NAME,CLW_FACILITY_PROPERTY.PROPERTY_VALUE,CLW_FACILITY_PROPERTY.ADD_DATE,CLW_FACILITY_PROPERTY.ADD_BY,CLW_FACILITY_PROPERTY.MOD_DATE,CLW_FACILITY_PROPERTY.MOD_BY FROM CLW_FACILITY_PROPERTY");
                where = pCriteria.getSqlClause("CLW_FACILITY_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FACILITY_PROPERTY.equals(otherTable)){
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
        FacilityPropertyDataVector v = new FacilityPropertyDataVector();
        while (rs.next()) {
            FacilityPropertyData x = FacilityPropertyData.createValue();
            
            x.setFacilityPropertyId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setPropertyName(rs.getString(3));
            x.setPropertyValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FacilityPropertyDataVector object that consists
     * of FacilityPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FacilityPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FacilityPropertyDataVector()
     * @throws            SQLException
     */
    public static FacilityPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FacilityPropertyDataVector v = new FacilityPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_PROPERTY_ID,ESTIMATOR_FACILITY_ID,PROPERTY_NAME,PROPERTY_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_PROPERTY WHERE FACILITY_PROPERTY_ID IN (");

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
            FacilityPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=FacilityPropertyData.createValue();
                
                x.setFacilityPropertyId(rs.getInt(1));
                x.setEstimatorFacilityId(rs.getInt(2));
                x.setPropertyName(rs.getString(3));
                x.setPropertyValue(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FacilityPropertyDataVector object of all
     * FacilityPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new FacilityPropertyDataVector()
     * @throws            SQLException
     */
    public static FacilityPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FACILITY_PROPERTY_ID,ESTIMATOR_FACILITY_ID,PROPERTY_NAME,PROPERTY_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FacilityPropertyDataVector v = new FacilityPropertyDataVector();
        FacilityPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = FacilityPropertyData.createValue();
            
            x.setFacilityPropertyId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setPropertyName(rs.getString(3));
            x.setPropertyValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FacilityPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_PROPERTY_ID FROM CLW_FACILITY_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_PROPERTY");
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
     * Inserts a FacilityPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new FacilityPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityPropertyData insert(Connection pCon, FacilityPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FACILITY_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FACILITY_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFacilityPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FACILITY_PROPERTY (FACILITY_PROPERTY_ID,ESTIMATOR_FACILITY_ID,PROPERTY_NAME,PROPERTY_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFacilityPropertyId());
        pstmt.setInt(2,pData.getEstimatorFacilityId());
        pstmt.setString(3,pData.getPropertyName());
        pstmt.setString(4,pData.getPropertyValue());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FACILITY_PROPERTY_ID="+pData.getFacilityPropertyId());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   PROPERTY_NAME="+pData.getPropertyName());
            log.debug("SQL:   PROPERTY_VALUE="+pData.getPropertyValue());
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
        pData.setFacilityPropertyId(0);
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
     * Updates a FacilityPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FACILITY_PROPERTY SET ESTIMATOR_FACILITY_ID = ?,PROPERTY_NAME = ?,PROPERTY_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE FACILITY_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getEstimatorFacilityId());
        pstmt.setString(i++,pData.getPropertyName());
        pstmt.setString(i++,pData.getPropertyValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getFacilityPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   PROPERTY_NAME="+pData.getPropertyName());
            log.debug("SQL:   PROPERTY_VALUE="+pData.getPropertyValue());
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
     * Deletes a FacilityPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FACILITY_PROPERTY WHERE FACILITY_PROPERTY_ID = " + pFacilityPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FacilityPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FACILITY_PROPERTY");
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
     * Inserts a FacilityPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, FacilityPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FACILITY_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FACILITY_PROPERTY_ID,ESTIMATOR_FACILITY_ID,PROPERTY_NAME,PROPERTY_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFacilityPropertyId());
        pstmt.setInt(2+4,pData.getEstimatorFacilityId());
        pstmt.setString(3+4,pData.getPropertyName());
        pstmt.setString(4+4,pData.getPropertyValue());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a FacilityPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new FacilityPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityPropertyData insert(Connection pCon, FacilityPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a FacilityPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityPropertyData pData, boolean pLogFl)
        throws SQLException {
        FacilityPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getFacilityPropertyId();
          try {
          oldData = FacilityPropertyDataAccess.select(pCon,id);
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
     * Deletes a FacilityPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FACILITY_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FACILITY_PROPERTY d WHERE FACILITY_PROPERTY_ID = " + pFacilityPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFacilityPropertyId);
        return n;
     }

    /**
     * Deletes FacilityPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FACILITY_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FACILITY_PROPERTY d ");
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

