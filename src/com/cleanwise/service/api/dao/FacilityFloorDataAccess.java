
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FacilityFloorDataAccess
 * Description:  This class is used to build access methods to the CLW_FACILITY_FLOOR table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FacilityFloorData;
import com.cleanwise.service.api.value.FacilityFloorDataVector;
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
 * <code>FacilityFloorDataAccess</code>
 */
public class FacilityFloorDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(FacilityFloorDataAccess.class.getName());

    /** <code>CLW_FACILITY_FLOOR</code> table name */
	/* Primary key: FACILITY_FLOOR_ID */
	
    public static final String CLW_FACILITY_FLOOR = "CLW_FACILITY_FLOOR";
    
    /** <code>FACILITY_FLOOR_ID</code> FACILITY_FLOOR_ID column of table CLW_FACILITY_FLOOR */
    public static final String FACILITY_FLOOR_ID = "FACILITY_FLOOR_ID";
    /** <code>ESTIMATOR_FACILITY_ID</code> ESTIMATOR_FACILITY_ID column of table CLW_FACILITY_FLOOR */
    public static final String ESTIMATOR_FACILITY_ID = "ESTIMATOR_FACILITY_ID";
    /** <code>FLOOR_TYPE_CD</code> FLOOR_TYPE_CD column of table CLW_FACILITY_FLOOR */
    public static final String FLOOR_TYPE_CD = "FLOOR_TYPE_CD";
    /** <code>CLEANABLE_PERCENT</code> CLEANABLE_PERCENT column of table CLW_FACILITY_FLOOR */
    public static final String CLEANABLE_PERCENT = "CLEANABLE_PERCENT";
    /** <code>CLEANABLE_PERCENT_HT</code> CLEANABLE_PERCENT_HT column of table CLW_FACILITY_FLOOR */
    public static final String CLEANABLE_PERCENT_HT = "CLEANABLE_PERCENT_HT";
    /** <code>CLEANABLE_PERCENT_MT</code> CLEANABLE_PERCENT_MT column of table CLW_FACILITY_FLOOR */
    public static final String CLEANABLE_PERCENT_MT = "CLEANABLE_PERCENT_MT";
    /** <code>CLEANABLE_PERCENT_LT</code> CLEANABLE_PERCENT_LT column of table CLW_FACILITY_FLOOR */
    public static final String CLEANABLE_PERCENT_LT = "CLEANABLE_PERCENT_LT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FACILITY_FLOOR */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FACILITY_FLOOR */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FACILITY_FLOOR */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FACILITY_FLOOR */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public FacilityFloorDataAccess()
    {
    }

    /**
     * Gets a FacilityFloorData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFacilityFloorId The key requested.
     * @return new FacilityFloorData()
     * @throws            SQLException
     */
    public static FacilityFloorData select(Connection pCon, int pFacilityFloorId)
        throws SQLException, DataNotFoundException {
        FacilityFloorData x=null;
        String sql="SELECT FACILITY_FLOOR_ID,ESTIMATOR_FACILITY_ID,FLOOR_TYPE_CD,CLEANABLE_PERCENT,CLEANABLE_PERCENT_HT,CLEANABLE_PERCENT_MT,CLEANABLE_PERCENT_LT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_FLOOR WHERE FACILITY_FLOOR_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFacilityFloorId=" + pFacilityFloorId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFacilityFloorId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FacilityFloorData.createValue();
            
            x.setFacilityFloorId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setFloorTypeCd(rs.getString(3));
            x.setCleanablePercent(rs.getBigDecimal(4));
            x.setCleanablePercentHt(rs.getBigDecimal(5));
            x.setCleanablePercentMt(rs.getBigDecimal(6));
            x.setCleanablePercentLt(rs.getBigDecimal(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FACILITY_FLOOR_ID :" + pFacilityFloorId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FacilityFloorDataVector object that consists
     * of FacilityFloorData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FacilityFloorDataVector()
     * @throws            SQLException
     */
    public static FacilityFloorDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FacilityFloorData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FACILITY_FLOOR.FACILITY_FLOOR_ID,CLW_FACILITY_FLOOR.ESTIMATOR_FACILITY_ID,CLW_FACILITY_FLOOR.FLOOR_TYPE_CD,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT_HT,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT_MT,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT_LT,CLW_FACILITY_FLOOR.ADD_DATE,CLW_FACILITY_FLOOR.ADD_BY,CLW_FACILITY_FLOOR.MOD_DATE,CLW_FACILITY_FLOOR.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FacilityFloorData Object.
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
    *@returns a populated FacilityFloorData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FacilityFloorData x = FacilityFloorData.createValue();
         
         x.setFacilityFloorId(rs.getInt(1+offset));
         x.setEstimatorFacilityId(rs.getInt(2+offset));
         x.setFloorTypeCd(rs.getString(3+offset));
         x.setCleanablePercent(rs.getBigDecimal(4+offset));
         x.setCleanablePercentHt(rs.getBigDecimal(5+offset));
         x.setCleanablePercentMt(rs.getBigDecimal(6+offset));
         x.setCleanablePercentLt(rs.getBigDecimal(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FacilityFloorData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a FacilityFloorDataVector object that consists
     * of FacilityFloorData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FacilityFloorDataVector()
     * @throws            SQLException
     */
    public static FacilityFloorDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FACILITY_FLOOR_ID,ESTIMATOR_FACILITY_ID,FLOOR_TYPE_CD,CLEANABLE_PERCENT,CLEANABLE_PERCENT_HT,CLEANABLE_PERCENT_MT,CLEANABLE_PERCENT_LT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_FLOOR");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FACILITY_FLOOR.FACILITY_FLOOR_ID,CLW_FACILITY_FLOOR.ESTIMATOR_FACILITY_ID,CLW_FACILITY_FLOOR.FLOOR_TYPE_CD,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT_HT,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT_MT,CLW_FACILITY_FLOOR.CLEANABLE_PERCENT_LT,CLW_FACILITY_FLOOR.ADD_DATE,CLW_FACILITY_FLOOR.ADD_BY,CLW_FACILITY_FLOOR.MOD_DATE,CLW_FACILITY_FLOOR.MOD_BY FROM CLW_FACILITY_FLOOR");
                where = pCriteria.getSqlClause("CLW_FACILITY_FLOOR");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FACILITY_FLOOR.equals(otherTable)){
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
        FacilityFloorDataVector v = new FacilityFloorDataVector();
        while (rs.next()) {
            FacilityFloorData x = FacilityFloorData.createValue();
            
            x.setFacilityFloorId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setFloorTypeCd(rs.getString(3));
            x.setCleanablePercent(rs.getBigDecimal(4));
            x.setCleanablePercentHt(rs.getBigDecimal(5));
            x.setCleanablePercentMt(rs.getBigDecimal(6));
            x.setCleanablePercentLt(rs.getBigDecimal(7));
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
     * Gets a FacilityFloorDataVector object that consists
     * of FacilityFloorData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FacilityFloorData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FacilityFloorDataVector()
     * @throws            SQLException
     */
    public static FacilityFloorDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FacilityFloorDataVector v = new FacilityFloorDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_FLOOR_ID,ESTIMATOR_FACILITY_ID,FLOOR_TYPE_CD,CLEANABLE_PERCENT,CLEANABLE_PERCENT_HT,CLEANABLE_PERCENT_MT,CLEANABLE_PERCENT_LT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_FLOOR WHERE FACILITY_FLOOR_ID IN (");

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
            FacilityFloorData x=null;
            while (rs.next()) {
                // build the object
                x=FacilityFloorData.createValue();
                
                x.setFacilityFloorId(rs.getInt(1));
                x.setEstimatorFacilityId(rs.getInt(2));
                x.setFloorTypeCd(rs.getString(3));
                x.setCleanablePercent(rs.getBigDecimal(4));
                x.setCleanablePercentHt(rs.getBigDecimal(5));
                x.setCleanablePercentMt(rs.getBigDecimal(6));
                x.setCleanablePercentLt(rs.getBigDecimal(7));
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
     * Gets a FacilityFloorDataVector object of all
     * FacilityFloorData objects in the database.
     * @param pCon An open database connection.
     * @return new FacilityFloorDataVector()
     * @throws            SQLException
     */
    public static FacilityFloorDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FACILITY_FLOOR_ID,ESTIMATOR_FACILITY_ID,FLOOR_TYPE_CD,CLEANABLE_PERCENT,CLEANABLE_PERCENT_HT,CLEANABLE_PERCENT_MT,CLEANABLE_PERCENT_LT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FACILITY_FLOOR";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FacilityFloorDataVector v = new FacilityFloorDataVector();
        FacilityFloorData x = null;
        while (rs.next()) {
            // build the object
            x = FacilityFloorData.createValue();
            
            x.setFacilityFloorId(rs.getInt(1));
            x.setEstimatorFacilityId(rs.getInt(2));
            x.setFloorTypeCd(rs.getString(3));
            x.setCleanablePercent(rs.getBigDecimal(4));
            x.setCleanablePercentHt(rs.getBigDecimal(5));
            x.setCleanablePercentMt(rs.getBigDecimal(6));
            x.setCleanablePercentLt(rs.getBigDecimal(7));
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
     * FacilityFloorData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_FLOOR_ID FROM CLW_FACILITY_FLOOR");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_FLOOR");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY_FLOOR");
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
     * Inserts a FacilityFloorData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityFloorData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new FacilityFloorData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityFloorData insert(Connection pCon, FacilityFloorData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FACILITY_FLOOR_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FACILITY_FLOOR_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFacilityFloorId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FACILITY_FLOOR (FACILITY_FLOOR_ID,ESTIMATOR_FACILITY_ID,FLOOR_TYPE_CD,CLEANABLE_PERCENT,CLEANABLE_PERCENT_HT,CLEANABLE_PERCENT_MT,CLEANABLE_PERCENT_LT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFacilityFloorId());
        pstmt.setInt(2,pData.getEstimatorFacilityId());
        pstmt.setString(3,pData.getFloorTypeCd());
        pstmt.setBigDecimal(4,pData.getCleanablePercent());
        pstmt.setBigDecimal(5,pData.getCleanablePercentHt());
        pstmt.setBigDecimal(6,pData.getCleanablePercentMt());
        pstmt.setBigDecimal(7,pData.getCleanablePercentLt());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FACILITY_FLOOR_ID="+pData.getFacilityFloorId());
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   FLOOR_TYPE_CD="+pData.getFloorTypeCd());
            log.debug("SQL:   CLEANABLE_PERCENT="+pData.getCleanablePercent());
            log.debug("SQL:   CLEANABLE_PERCENT_HT="+pData.getCleanablePercentHt());
            log.debug("SQL:   CLEANABLE_PERCENT_MT="+pData.getCleanablePercentMt());
            log.debug("SQL:   CLEANABLE_PERCENT_LT="+pData.getCleanablePercentLt());
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
        pData.setFacilityFloorId(0);
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
     * Updates a FacilityFloorData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityFloorData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityFloorData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FACILITY_FLOOR SET ESTIMATOR_FACILITY_ID = ?,FLOOR_TYPE_CD = ?,CLEANABLE_PERCENT = ?,CLEANABLE_PERCENT_HT = ?,CLEANABLE_PERCENT_MT = ?,CLEANABLE_PERCENT_LT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE FACILITY_FLOOR_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getEstimatorFacilityId());
        pstmt.setString(i++,pData.getFloorTypeCd());
        pstmt.setBigDecimal(i++,pData.getCleanablePercent());
        pstmt.setBigDecimal(i++,pData.getCleanablePercentHt());
        pstmt.setBigDecimal(i++,pData.getCleanablePercentMt());
        pstmt.setBigDecimal(i++,pData.getCleanablePercentLt());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getFacilityFloorId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ESTIMATOR_FACILITY_ID="+pData.getEstimatorFacilityId());
            log.debug("SQL:   FLOOR_TYPE_CD="+pData.getFloorTypeCd());
            log.debug("SQL:   CLEANABLE_PERCENT="+pData.getCleanablePercent());
            log.debug("SQL:   CLEANABLE_PERCENT_HT="+pData.getCleanablePercentHt());
            log.debug("SQL:   CLEANABLE_PERCENT_MT="+pData.getCleanablePercentMt());
            log.debug("SQL:   CLEANABLE_PERCENT_LT="+pData.getCleanablePercentLt());
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
     * Deletes a FacilityFloorData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityFloorId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityFloorId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FACILITY_FLOOR WHERE FACILITY_FLOOR_ID = " + pFacilityFloorId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FacilityFloorData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FACILITY_FLOOR");
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
     * Inserts a FacilityFloorData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityFloorData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, FacilityFloorData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FACILITY_FLOOR (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FACILITY_FLOOR_ID,ESTIMATOR_FACILITY_ID,FLOOR_TYPE_CD,CLEANABLE_PERCENT,CLEANABLE_PERCENT_HT,CLEANABLE_PERCENT_MT,CLEANABLE_PERCENT_LT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFacilityFloorId());
        pstmt.setInt(2+4,pData.getEstimatorFacilityId());
        pstmt.setString(3+4,pData.getFloorTypeCd());
        pstmt.setBigDecimal(4+4,pData.getCleanablePercent());
        pstmt.setBigDecimal(5+4,pData.getCleanablePercentHt());
        pstmt.setBigDecimal(6+4,pData.getCleanablePercentMt());
        pstmt.setBigDecimal(7+4,pData.getCleanablePercentLt());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a FacilityFloorData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityFloorData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new FacilityFloorData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityFloorData insert(Connection pCon, FacilityFloorData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a FacilityFloorData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityFloorData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityFloorData pData, boolean pLogFl)
        throws SQLException {
        FacilityFloorData oldData = null;
        if(pLogFl) {
          int id = pData.getFacilityFloorId();
          try {
          oldData = FacilityFloorDataAccess.select(pCon,id);
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
     * Deletes a FacilityFloorData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityFloorId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityFloorId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FACILITY_FLOOR SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FACILITY_FLOOR d WHERE FACILITY_FLOOR_ID = " + pFacilityFloorId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFacilityFloorId);
        return n;
     }

    /**
     * Deletes FacilityFloorData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FACILITY_FLOOR SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FACILITY_FLOOR d ");
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

