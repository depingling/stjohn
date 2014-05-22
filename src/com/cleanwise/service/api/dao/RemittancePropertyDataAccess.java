
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        RemittancePropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_REMITTANCE_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.RemittancePropertyData;
import com.cleanwise.service.api.value.RemittancePropertyDataVector;
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
 * <code>RemittancePropertyDataAccess</code>
 */
public class RemittancePropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(RemittancePropertyDataAccess.class.getName());

    /** <code>CLW_REMITTANCE_PROPERTY</code> table name */
	/* Primary key: REMITTANCE_PROPERTY_ID */
	
    public static final String CLW_REMITTANCE_PROPERTY = "CLW_REMITTANCE_PROPERTY";
    
    /** <code>REMITTANCE_PROPERTY_ID</code> REMITTANCE_PROPERTY_ID column of table CLW_REMITTANCE_PROPERTY */
    public static final String REMITTANCE_PROPERTY_ID = "REMITTANCE_PROPERTY_ID";
    /** <code>REMITTANCE_DETAIL_ID</code> REMITTANCE_DETAIL_ID column of table CLW_REMITTANCE_PROPERTY */
    public static final String REMITTANCE_DETAIL_ID = "REMITTANCE_DETAIL_ID";
    /** <code>REMITTANCE_ID</code> REMITTANCE_ID column of table CLW_REMITTANCE_PROPERTY */
    public static final String REMITTANCE_ID = "REMITTANCE_ID";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_REMITTANCE_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>REMITTANCE_PROPERTY_STATUS_CD</code> REMITTANCE_PROPERTY_STATUS_CD column of table CLW_REMITTANCE_PROPERTY */
    public static final String REMITTANCE_PROPERTY_STATUS_CD = "REMITTANCE_PROPERTY_STATUS_CD";
    /** <code>REMITTANCE_PROPERTY_TYPE_CD</code> REMITTANCE_PROPERTY_TYPE_CD column of table CLW_REMITTANCE_PROPERTY */
    public static final String REMITTANCE_PROPERTY_TYPE_CD = "REMITTANCE_PROPERTY_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_REMITTANCE_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_REMITTANCE_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_REMITTANCE_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_REMITTANCE_PROPERTY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public RemittancePropertyDataAccess()
    {
    }

    /**
     * Gets a RemittancePropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pRemittancePropertyId The key requested.
     * @return new RemittancePropertyData()
     * @throws            SQLException
     */
    public static RemittancePropertyData select(Connection pCon, int pRemittancePropertyId)
        throws SQLException, DataNotFoundException {
        RemittancePropertyData x=null;
        String sql="SELECT REMITTANCE_PROPERTY_ID,REMITTANCE_DETAIL_ID,REMITTANCE_ID,CLW_VALUE,REMITTANCE_PROPERTY_STATUS_CD,REMITTANCE_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REMITTANCE_PROPERTY WHERE REMITTANCE_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pRemittancePropertyId=" + pRemittancePropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pRemittancePropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=RemittancePropertyData.createValue();
            
            x.setRemittancePropertyId(rs.getInt(1));
            x.setRemittanceDetailId(rs.getInt(2));
            x.setRemittanceId(rs.getInt(3));
            x.setValue(rs.getString(4));
            x.setRemittancePropertyStatusCd(rs.getString(5));
            x.setRemittancePropertyTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("REMITTANCE_PROPERTY_ID :" + pRemittancePropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a RemittancePropertyDataVector object that consists
     * of RemittancePropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new RemittancePropertyDataVector()
     * @throws            SQLException
     */
    public static RemittancePropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a RemittancePropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_REMITTANCE_PROPERTY.REMITTANCE_PROPERTY_ID,CLW_REMITTANCE_PROPERTY.REMITTANCE_DETAIL_ID,CLW_REMITTANCE_PROPERTY.REMITTANCE_ID,CLW_REMITTANCE_PROPERTY.CLW_VALUE,CLW_REMITTANCE_PROPERTY.REMITTANCE_PROPERTY_STATUS_CD,CLW_REMITTANCE_PROPERTY.REMITTANCE_PROPERTY_TYPE_CD,CLW_REMITTANCE_PROPERTY.ADD_DATE,CLW_REMITTANCE_PROPERTY.ADD_BY,CLW_REMITTANCE_PROPERTY.MOD_DATE,CLW_REMITTANCE_PROPERTY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated RemittancePropertyData Object.
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
    *@returns a populated RemittancePropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         RemittancePropertyData x = RemittancePropertyData.createValue();
         
         x.setRemittancePropertyId(rs.getInt(1+offset));
         x.setRemittanceDetailId(rs.getInt(2+offset));
         x.setRemittanceId(rs.getInt(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setRemittancePropertyStatusCd(rs.getString(5+offset));
         x.setRemittancePropertyTypeCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the RemittancePropertyData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a RemittancePropertyDataVector object that consists
     * of RemittancePropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new RemittancePropertyDataVector()
     * @throws            SQLException
     */
    public static RemittancePropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT REMITTANCE_PROPERTY_ID,REMITTANCE_DETAIL_ID,REMITTANCE_ID,CLW_VALUE,REMITTANCE_PROPERTY_STATUS_CD,REMITTANCE_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REMITTANCE_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_REMITTANCE_PROPERTY.REMITTANCE_PROPERTY_ID,CLW_REMITTANCE_PROPERTY.REMITTANCE_DETAIL_ID,CLW_REMITTANCE_PROPERTY.REMITTANCE_ID,CLW_REMITTANCE_PROPERTY.CLW_VALUE,CLW_REMITTANCE_PROPERTY.REMITTANCE_PROPERTY_STATUS_CD,CLW_REMITTANCE_PROPERTY.REMITTANCE_PROPERTY_TYPE_CD,CLW_REMITTANCE_PROPERTY.ADD_DATE,CLW_REMITTANCE_PROPERTY.ADD_BY,CLW_REMITTANCE_PROPERTY.MOD_DATE,CLW_REMITTANCE_PROPERTY.MOD_BY FROM CLW_REMITTANCE_PROPERTY");
                where = pCriteria.getSqlClause("CLW_REMITTANCE_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_REMITTANCE_PROPERTY.equals(otherTable)){
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
        RemittancePropertyDataVector v = new RemittancePropertyDataVector();
        while (rs.next()) {
            RemittancePropertyData x = RemittancePropertyData.createValue();
            
            x.setRemittancePropertyId(rs.getInt(1));
            x.setRemittanceDetailId(rs.getInt(2));
            x.setRemittanceId(rs.getInt(3));
            x.setValue(rs.getString(4));
            x.setRemittancePropertyStatusCd(rs.getString(5));
            x.setRemittancePropertyTypeCd(rs.getString(6));
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
     * Gets a RemittancePropertyDataVector object that consists
     * of RemittancePropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for RemittancePropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new RemittancePropertyDataVector()
     * @throws            SQLException
     */
    public static RemittancePropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        RemittancePropertyDataVector v = new RemittancePropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REMITTANCE_PROPERTY_ID,REMITTANCE_DETAIL_ID,REMITTANCE_ID,CLW_VALUE,REMITTANCE_PROPERTY_STATUS_CD,REMITTANCE_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REMITTANCE_PROPERTY WHERE REMITTANCE_PROPERTY_ID IN (");

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
            RemittancePropertyData x=null;
            while (rs.next()) {
                // build the object
                x=RemittancePropertyData.createValue();
                
                x.setRemittancePropertyId(rs.getInt(1));
                x.setRemittanceDetailId(rs.getInt(2));
                x.setRemittanceId(rs.getInt(3));
                x.setValue(rs.getString(4));
                x.setRemittancePropertyStatusCd(rs.getString(5));
                x.setRemittancePropertyTypeCd(rs.getString(6));
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
     * Gets a RemittancePropertyDataVector object of all
     * RemittancePropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new RemittancePropertyDataVector()
     * @throws            SQLException
     */
    public static RemittancePropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REMITTANCE_PROPERTY_ID,REMITTANCE_DETAIL_ID,REMITTANCE_ID,CLW_VALUE,REMITTANCE_PROPERTY_STATUS_CD,REMITTANCE_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_REMITTANCE_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        RemittancePropertyDataVector v = new RemittancePropertyDataVector();
        RemittancePropertyData x = null;
        while (rs.next()) {
            // build the object
            x = RemittancePropertyData.createValue();
            
            x.setRemittancePropertyId(rs.getInt(1));
            x.setRemittanceDetailId(rs.getInt(2));
            x.setRemittanceId(rs.getInt(3));
            x.setValue(rs.getString(4));
            x.setRemittancePropertyStatusCd(rs.getString(5));
            x.setRemittancePropertyTypeCd(rs.getString(6));
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
     * RemittancePropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REMITTANCE_PROPERTY_ID FROM CLW_REMITTANCE_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REMITTANCE_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REMITTANCE_PROPERTY");
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
     * Inserts a RemittancePropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittancePropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new RemittancePropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RemittancePropertyData insert(Connection pCon, RemittancePropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_REMITTANCE_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_REMITTANCE_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setRemittancePropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_REMITTANCE_PROPERTY (REMITTANCE_PROPERTY_ID,REMITTANCE_DETAIL_ID,REMITTANCE_ID,CLW_VALUE,REMITTANCE_PROPERTY_STATUS_CD,REMITTANCE_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getRemittancePropertyId());
        pstmt.setInt(2,pData.getRemittanceDetailId());
        pstmt.setInt(3,pData.getRemittanceId());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getRemittancePropertyStatusCd());
        pstmt.setString(6,pData.getRemittancePropertyTypeCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REMITTANCE_PROPERTY_ID="+pData.getRemittancePropertyId());
            log.debug("SQL:   REMITTANCE_DETAIL_ID="+pData.getRemittanceDetailId());
            log.debug("SQL:   REMITTANCE_ID="+pData.getRemittanceId());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   REMITTANCE_PROPERTY_STATUS_CD="+pData.getRemittancePropertyStatusCd());
            log.debug("SQL:   REMITTANCE_PROPERTY_TYPE_CD="+pData.getRemittancePropertyTypeCd());
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
        pData.setRemittancePropertyId(0);
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
     * Updates a RemittancePropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittancePropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RemittancePropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_REMITTANCE_PROPERTY SET REMITTANCE_DETAIL_ID = ?,REMITTANCE_ID = ?,CLW_VALUE = ?,REMITTANCE_PROPERTY_STATUS_CD = ?,REMITTANCE_PROPERTY_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE REMITTANCE_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getRemittanceDetailId());
        pstmt.setInt(i++,pData.getRemittanceId());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getRemittancePropertyStatusCd());
        pstmt.setString(i++,pData.getRemittancePropertyTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getRemittancePropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REMITTANCE_DETAIL_ID="+pData.getRemittanceDetailId());
            log.debug("SQL:   REMITTANCE_ID="+pData.getRemittanceId());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   REMITTANCE_PROPERTY_STATUS_CD="+pData.getRemittancePropertyStatusCd());
            log.debug("SQL:   REMITTANCE_PROPERTY_TYPE_CD="+pData.getRemittancePropertyTypeCd());
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
     * Deletes a RemittancePropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRemittancePropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRemittancePropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_REMITTANCE_PROPERTY WHERE REMITTANCE_PROPERTY_ID = " + pRemittancePropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes RemittancePropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_REMITTANCE_PROPERTY");
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
     * Inserts a RemittancePropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittancePropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, RemittancePropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_REMITTANCE_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "REMITTANCE_PROPERTY_ID,REMITTANCE_DETAIL_ID,REMITTANCE_ID,CLW_VALUE,REMITTANCE_PROPERTY_STATUS_CD,REMITTANCE_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getRemittancePropertyId());
        pstmt.setInt(2+4,pData.getRemittanceDetailId());
        pstmt.setInt(3+4,pData.getRemittanceId());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getRemittancePropertyStatusCd());
        pstmt.setString(6+4,pData.getRemittancePropertyTypeCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a RemittancePropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittancePropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new RemittancePropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RemittancePropertyData insert(Connection pCon, RemittancePropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a RemittancePropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittancePropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RemittancePropertyData pData, boolean pLogFl)
        throws SQLException {
        RemittancePropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getRemittancePropertyId();
          try {
          oldData = RemittancePropertyDataAccess.select(pCon,id);
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
     * Deletes a RemittancePropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRemittancePropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRemittancePropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_REMITTANCE_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REMITTANCE_PROPERTY d WHERE REMITTANCE_PROPERTY_ID = " + pRemittancePropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pRemittancePropertyId);
        return n;
     }

    /**
     * Deletes RemittancePropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_REMITTANCE_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REMITTANCE_PROPERTY d ");
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

