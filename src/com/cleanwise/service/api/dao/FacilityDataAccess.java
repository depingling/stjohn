
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FacilityDataAccess
 * Description:  This class is used to build access methods to the CLW_FACILITY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FacilityData;
import com.cleanwise.service.api.value.FacilityDataVector;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>FacilityDataAccess</code>
 */
public class FacilityDataAccess implements DataAccess
{
    private static Category log = Category.getInstance(FacilityDataAccess.class.getName());

    /** <code>CLW_FACILITY</code> table name */
    public static final String CLW_FACILITY = "CLW_FACILITY";
    
    /** <code>FACILITY_ID</code> FACILITY_ID column of table CLW_FACILITY */
    public static final String FACILITY_ID = "FACILITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_FACILITY */
    public static final String USER_ID = "USER_ID";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FACILITY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FACILITY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>FACILITY_STATUS_CD</code> FACILITY_STATUS_CD column of table CLW_FACILITY */
    public static final String FACILITY_STATUS_CD = "FACILITY_STATUS_CD";
    /** <code>FACILITY_TYPE_CD</code> FACILITY_TYPE_CD column of table CLW_FACILITY */
    public static final String FACILITY_TYPE_CD = "FACILITY_TYPE_CD";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FACILITY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FACILITY */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public FacilityDataAccess()
    {
    }

    /**
     * Gets a FacilityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFacilityId The key requested.
     * @return new FacilityData()
     * @throws            SQLException
     */
    public static FacilityData select(Connection pCon, int pFacilityId)
        throws SQLException, DataNotFoundException {
        FacilityData x=null;
        String sql="SELECT FACILITY_ID,USER_ID,ADD_BY,ADD_DATE,FACILITY_STATUS_CD,FACILITY_TYPE_CD,MOD_BY,MOD_DATE FROM CLW_FACILITY WHERE FACILITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFacilityId=" + pFacilityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFacilityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FacilityData.createValue();
            
            x.setFacilityId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setFacilityStatusCd(rs.getString(5));
            x.setFacilityTypeCd(rs.getInt(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FACILITY_ID :" + pFacilityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FacilityDataVector object that consists
     * of FacilityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FacilityDataVector()
     * @throws            SQLException
     */
    public static FacilityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FacilityData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FACILITY.FACILITY_ID,CLW_FACILITY.USER_ID,CLW_FACILITY.ADD_BY,CLW_FACILITY.ADD_DATE,CLW_FACILITY.FACILITY_STATUS_CD,CLW_FACILITY.FACILITY_TYPE_CD,CLW_FACILITY.MOD_BY,CLW_FACILITY.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FacilityData Object.
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
    *@returns a populated FacilityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FacilityData x = FacilityData.createValue();
         
         x.setFacilityId(rs.getInt(1+offset));
         x.setUserId(rs.getInt(2+offset));
         x.setAddBy(rs.getString(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setFacilityStatusCd(rs.getString(5+offset));
         x.setFacilityTypeCd(rs.getInt(6+offset));
         x.setModBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FacilityData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a FacilityDataVector object that consists
     * of FacilityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FacilityDataVector()
     * @throws            SQLException
     */
    public static FacilityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FACILITY_ID,USER_ID,ADD_BY,ADD_DATE,FACILITY_STATUS_CD,FACILITY_TYPE_CD,MOD_BY,MOD_DATE FROM CLW_FACILITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FACILITY.FACILITY_ID,CLW_FACILITY.USER_ID,CLW_FACILITY.ADD_BY,CLW_FACILITY.ADD_DATE,CLW_FACILITY.FACILITY_STATUS_CD,CLW_FACILITY.FACILITY_TYPE_CD,CLW_FACILITY.MOD_BY,CLW_FACILITY.MOD_DATE FROM CLW_FACILITY");
                where = pCriteria.getSqlClause("CLW_FACILITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
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
        FacilityDataVector v = new FacilityDataVector();
        while (rs.next()) {
            FacilityData x = FacilityData.createValue();
            
            x.setFacilityId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setFacilityStatusCd(rs.getString(5));
            x.setFacilityTypeCd(rs.getInt(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FacilityDataVector object that consists
     * of FacilityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FacilityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FacilityDataVector()
     * @throws            SQLException
     */
    public static FacilityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FacilityDataVector v = new FacilityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_ID,USER_ID,ADD_BY,ADD_DATE,FACILITY_STATUS_CD,FACILITY_TYPE_CD,MOD_BY,MOD_DATE FROM CLW_FACILITY WHERE FACILITY_ID IN (");

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
            FacilityData x=null;
            while (rs.next()) {
                // build the object
                x=FacilityData.createValue();
                
                x.setFacilityId(rs.getInt(1));
                x.setUserId(rs.getInt(2));
                x.setAddBy(rs.getString(3));
                x.setAddDate(rs.getTimestamp(4));
                x.setFacilityStatusCd(rs.getString(5));
                x.setFacilityTypeCd(rs.getInt(6));
                x.setModBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FacilityDataVector object of all
     * FacilityData objects in the database.
     * @param pCon An open database connection.
     * @return new FacilityDataVector()
     * @throws            SQLException
     */
    public static FacilityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FACILITY_ID,USER_ID,ADD_BY,ADD_DATE,FACILITY_STATUS_CD,FACILITY_TYPE_CD,MOD_BY,MOD_DATE FROM CLW_FACILITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FacilityDataVector v = new FacilityDataVector();
        FacilityData x = null;
        while (rs.next()) {
            // build the object
            x = FacilityData.createValue();
            
            x.setFacilityId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setFacilityStatusCd(rs.getString(5));
            x.setFacilityTypeCd(rs.getInt(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FacilityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FACILITY_ID FROM CLW_FACILITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FACILITY");
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
     * Inserts a FacilityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new FacilityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FacilityData insert(Connection pCon, FacilityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FACILITY_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FACILITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFacilityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FACILITY (FACILITY_ID,USER_ID,ADD_BY,ADD_DATE,FACILITY_STATUS_CD,FACILITY_TYPE_CD,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFacilityId());
        pstmt.setInt(2,pData.getUserId());
        pstmt.setString(3,pData.getAddBy());
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getFacilityStatusCd());
        pstmt.setInt(6,pData.getFacilityTypeCd());
        pstmt.setString(7,pData.getModBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FACILITY_ID="+pData.getFacilityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   FACILITY_STATUS_CD="+pData.getFacilityStatusCd());
            log.debug("SQL:   FACILITY_TYPE_CD="+pData.getFacilityTypeCd());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a FacilityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FacilityData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FacilityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FACILITY SET USER_ID = ?,ADD_BY = ?,ADD_DATE = ?,FACILITY_STATUS_CD = ?,FACILITY_TYPE_CD = ?,MOD_BY = ?,MOD_DATE = ? WHERE FACILITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getFacilityStatusCd());
        pstmt.setInt(i++,pData.getFacilityTypeCd());
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getFacilityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   FACILITY_STATUS_CD="+pData.getFacilityStatusCd());
            log.debug("SQL:   FACILITY_TYPE_CD="+pData.getFacilityTypeCd());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a FacilityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFacilityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFacilityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FACILITY WHERE FACILITY_ID = " + pFacilityId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FacilityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FACILITY");
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
}

