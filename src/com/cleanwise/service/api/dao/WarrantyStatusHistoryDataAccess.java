
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WarrantyStatusHistoryDataAccess
 * Description:  This class is used to build access methods to the CLW_WARRANTY_STATUS_HISTORY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WarrantyStatusHistoryData;
import com.cleanwise.service.api.value.WarrantyStatusHistoryDataVector;
import com.cleanwise.service.api.framework.DataAccess;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>WarrantyStatusHistoryDataAccess</code>
 */
public class WarrantyStatusHistoryDataAccess implements DataAccess
{
    private static Category log = Category.getInstance(WarrantyStatusHistoryDataAccess.class.getName());

    /** <code>CLW_WARRANTY_STATUS_HISTORY</code> table name */
    public static final String CLW_WARRANTY_STATUS_HISTORY = "CLW_WARRANTY_STATUS_HISTORY";
    
    /** <code>WARRANTY_ID</code> WARRANTY_ID column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String WARRANTY_ID = "WARRANTY_ID";
    /** <code>WARRANTY_STATUS_HISTORY_ID</code> WARRANTY_STATUS_HISTORY_ID column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String WARRANTY_STATUS_HISTORY_ID = "WARRANTY_STATUS_HISTORY_ID";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>STATUS_DATE</code> STATUS_DATE column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String STATUS_DATE = "STATUS_DATE";
    /** <code>TYPE_CD</code> TYPE_CD column of table CLW_WARRANTY_STATUS_HISTORY */
    public static final String TYPE_CD = "TYPE_CD";

    /**
     * Constructor.
     */
    public WarrantyStatusHistoryDataAccess()
    {
    }

    /**
     * Gets a WarrantyStatusHistoryData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWarrantyStatusHistoryId The key requested.
     * @return new WarrantyStatusHistoryData()
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryData select(Connection pCon, int pWarrantyStatusHistoryId)
        throws SQLException, DataNotFoundException {
        WarrantyStatusHistoryData x=null;
        String sql="SELECT WARRANTY_ID,WARRANTY_STATUS_HISTORY_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,STATUS_CD,STATUS_DATE,TYPE_CD FROM CLW_WARRANTY_STATUS_HISTORY WHERE WARRANTY_STATUS_HISTORY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWarrantyStatusHistoryId=" + pWarrantyStatusHistoryId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWarrantyStatusHistoryId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WarrantyStatusHistoryData.createValue();
            
            x.setWarrantyId(rs.getInt(1));
            x.setWarrantyStatusHistoryId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setModBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setStatusCd(rs.getString(7));
            x.setStatusDate(rs.getDate(8));
            x.setTypeCd(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WARRANTY_STATUS_HISTORY_ID :" + pWarrantyStatusHistoryId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WarrantyStatusHistoryDataVector object that consists
     * of WarrantyStatusHistoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WarrantyStatusHistoryDataVector()
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WarrantyStatusHistoryData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WARRANTY_STATUS_HISTORY.WARRANTY_ID,CLW_WARRANTY_STATUS_HISTORY.WARRANTY_STATUS_HISTORY_ID,CLW_WARRANTY_STATUS_HISTORY.ADD_BY,CLW_WARRANTY_STATUS_HISTORY.ADD_DATE,CLW_WARRANTY_STATUS_HISTORY.MOD_BY,CLW_WARRANTY_STATUS_HISTORY.MOD_DATE,CLW_WARRANTY_STATUS_HISTORY.STATUS_CD,CLW_WARRANTY_STATUS_HISTORY.STATUS_DATE,CLW_WARRANTY_STATUS_HISTORY.TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WarrantyStatusHistoryData Object.
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
    *@returns a populated WarrantyStatusHistoryData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WarrantyStatusHistoryData x = WarrantyStatusHistoryData.createValue();
         
         x.setWarrantyId(rs.getInt(1+offset));
         x.setWarrantyStatusHistoryId(rs.getInt(2+offset));
         x.setAddBy(rs.getString(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setModBy(rs.getString(5+offset));
         x.setModDate(rs.getTimestamp(6+offset));
         x.setStatusCd(rs.getString(7+offset));
         x.setStatusDate(rs.getDate(8+offset));
         x.setTypeCd(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WarrantyStatusHistoryData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a WarrantyStatusHistoryDataVector object that consists
     * of WarrantyStatusHistoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WarrantyStatusHistoryDataVector()
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WARRANTY_ID,WARRANTY_STATUS_HISTORY_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,STATUS_CD,STATUS_DATE,TYPE_CD FROM CLW_WARRANTY_STATUS_HISTORY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WARRANTY_STATUS_HISTORY.WARRANTY_ID,CLW_WARRANTY_STATUS_HISTORY.WARRANTY_STATUS_HISTORY_ID,CLW_WARRANTY_STATUS_HISTORY.ADD_BY,CLW_WARRANTY_STATUS_HISTORY.ADD_DATE,CLW_WARRANTY_STATUS_HISTORY.MOD_BY,CLW_WARRANTY_STATUS_HISTORY.MOD_DATE,CLW_WARRANTY_STATUS_HISTORY.STATUS_CD,CLW_WARRANTY_STATUS_HISTORY.STATUS_DATE,CLW_WARRANTY_STATUS_HISTORY.TYPE_CD FROM CLW_WARRANTY_STATUS_HISTORY");
                where = pCriteria.getSqlClause("CLW_WARRANTY_STATUS_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WARRANTY_STATUS_HISTORY.equals(otherTable)){
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
        WarrantyStatusHistoryDataVector v = new WarrantyStatusHistoryDataVector();
        while (rs.next()) {
            WarrantyStatusHistoryData x = WarrantyStatusHistoryData.createValue();
            
            x.setWarrantyId(rs.getInt(1));
            x.setWarrantyStatusHistoryId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setModBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setStatusCd(rs.getString(7));
            x.setStatusDate(rs.getDate(8));
            x.setTypeCd(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WarrantyStatusHistoryDataVector object that consists
     * of WarrantyStatusHistoryData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WarrantyStatusHistoryData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WarrantyStatusHistoryDataVector()
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WarrantyStatusHistoryDataVector v = new WarrantyStatusHistoryDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WARRANTY_ID,WARRANTY_STATUS_HISTORY_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,STATUS_CD,STATUS_DATE,TYPE_CD FROM CLW_WARRANTY_STATUS_HISTORY WHERE WARRANTY_STATUS_HISTORY_ID IN (");

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
            WarrantyStatusHistoryData x=null;
            while (rs.next()) {
                // build the object
                x=WarrantyStatusHistoryData.createValue();
                
                x.setWarrantyId(rs.getInt(1));
                x.setWarrantyStatusHistoryId(rs.getInt(2));
                x.setAddBy(rs.getString(3));
                x.setAddDate(rs.getTimestamp(4));
                x.setModBy(rs.getString(5));
                x.setModDate(rs.getTimestamp(6));
                x.setStatusCd(rs.getString(7));
                x.setStatusDate(rs.getDate(8));
                x.setTypeCd(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WarrantyStatusHistoryDataVector object of all
     * WarrantyStatusHistoryData objects in the database.
     * @param pCon An open database connection.
     * @return new WarrantyStatusHistoryDataVector()
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WARRANTY_ID,WARRANTY_STATUS_HISTORY_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,STATUS_CD,STATUS_DATE,TYPE_CD FROM CLW_WARRANTY_STATUS_HISTORY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WarrantyStatusHistoryDataVector v = new WarrantyStatusHistoryDataVector();
        WarrantyStatusHistoryData x = null;
        while (rs.next()) {
            // build the object
            x = WarrantyStatusHistoryData.createValue();
            
            x.setWarrantyId(rs.getInt(1));
            x.setWarrantyStatusHistoryId(rs.getInt(2));
            x.setAddBy(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setModBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setStatusCd(rs.getString(7));
            x.setStatusDate(rs.getDate(8));
            x.setTypeCd(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WarrantyStatusHistoryData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WARRANTY_STATUS_HISTORY_ID FROM CLW_WARRANTY_STATUS_HISTORY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WARRANTY_STATUS_HISTORY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WARRANTY_STATUS_HISTORY");
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
     * Inserts a WarrantyStatusHistoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyStatusHistoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WarrantyStatusHistoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryData insert(Connection pCon, WarrantyStatusHistoryData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WARRANTY_STATUS_HISTORY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WARRANTY_STATUS_HISTORY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWarrantyStatusHistoryId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WARRANTY_STATUS_HISTORY (WARRANTY_ID,WARRANTY_STATUS_HISTORY_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,STATUS_CD,STATUS_DATE,TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWarrantyId());
        pstmt.setInt(2,pData.getWarrantyStatusHistoryId());
        pstmt.setString(3,pData.getAddBy());
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getModBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7,pData.getStatusCd());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getStatusDate()));
        pstmt.setString(9,pData.getTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   WARRANTY_STATUS_HISTORY_ID="+pData.getWarrantyStatusHistoryId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   STATUS_DATE="+pData.getStatusDate());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWarrantyStatusHistoryId(0);
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
     * Updates a WarrantyStatusHistoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyStatusHistoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WarrantyStatusHistoryData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WARRANTY_STATUS_HISTORY SET WARRANTY_ID = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,STATUS_CD = ?,STATUS_DATE = ?,TYPE_CD = ? WHERE WARRANTY_STATUS_HISTORY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWarrantyId());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getStatusDate()));
        pstmt.setString(i++,pData.getTypeCd());
        pstmt.setInt(i++,pData.getWarrantyStatusHistoryId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   STATUS_DATE="+pData.getStatusDate());
            log.debug("SQL:   TYPE_CD="+pData.getTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WarrantyStatusHistoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWarrantyStatusHistoryId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWarrantyStatusHistoryId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WARRANTY_STATUS_HISTORY WHERE WARRANTY_STATUS_HISTORY_ID = " + pWarrantyStatusHistoryId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WarrantyStatusHistoryData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WARRANTY_STATUS_HISTORY");
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
     * Inserts a WarrantyStatusHistoryData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyStatusHistoryData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WarrantyStatusHistoryData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WARRANTY_STATUS_HISTORY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WARRANTY_ID,WARRANTY_STATUS_HISTORY_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,STATUS_CD,STATUS_DATE,TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWarrantyId());
        pstmt.setInt(2+4,pData.getWarrantyStatusHistoryId());
        pstmt.setString(3+4,pData.getAddBy());
        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5+4,pData.getModBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7+4,pData.getStatusCd());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getStatusDate()));
        pstmt.setString(9+4,pData.getTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WarrantyStatusHistoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyStatusHistoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WarrantyStatusHistoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WarrantyStatusHistoryData insert(Connection pCon, WarrantyStatusHistoryData pData, boolean pLogFl)
        throws SQLException {        
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WarrantyStatusHistoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WarrantyStatusHistoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WarrantyStatusHistoryData pData, boolean pLogFl)
        throws SQLException {
        WarrantyStatusHistoryData oldData = null;
        if(pLogFl) {
          int id = pData.getWarrantyStatusHistoryId();
          try {
          oldData = WarrantyStatusHistoryDataAccess.select(pCon,id);
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
     * Deletes a WarrantyStatusHistoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWarrantyStatusHistoryId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWarrantyStatusHistoryId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";          
          String sqlLog ="INSERT INTO LCLW_WARRANTY_STATUS_HISTORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WARRANTY_STATUS_HISTORY d WHERE WARRANTY_STATUS_HISTORY_ID = " + pWarrantyStatusHistoryId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWarrantyStatusHistoryId);
        return n;
     }

    /**
     * Deletes WarrantyStatusHistoryData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WARRANTY_STATUS_HISTORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WARRANTY_STATUS_HISTORY d ");
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

