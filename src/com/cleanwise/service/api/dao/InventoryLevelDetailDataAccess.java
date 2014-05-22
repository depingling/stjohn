
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InventoryLevelDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_INVENTORY_LEVEL_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InventoryLevelDetailData;
import com.cleanwise.service.api.value.InventoryLevelDetailDataVector;
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
 * <code>InventoryLevelDetailDataAccess</code>
 */
public class InventoryLevelDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InventoryLevelDetailDataAccess.class.getName());

    /** <code>CLW_INVENTORY_LEVEL_DETAIL</code> table name */
	/* Primary key: INVENTORY_LEVEL_DETAIL_ID */
	
    public static final String CLW_INVENTORY_LEVEL_DETAIL = "CLW_INVENTORY_LEVEL_DETAIL";
    
    /** <code>INVENTORY_LEVEL_DETAIL_ID</code> INVENTORY_LEVEL_DETAIL_ID column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String INVENTORY_LEVEL_DETAIL_ID = "INVENTORY_LEVEL_DETAIL_ID";
    /** <code>INVENTORY_LEVEL_ID</code> INVENTORY_LEVEL_ID column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String INVENTORY_LEVEL_ID = "INVENTORY_LEVEL_ID";
    /** <code>PERIOD</code> PERIOD column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String PERIOD = "PERIOD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVENTORY_LEVEL_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public InventoryLevelDetailDataAccess()
    {
    }

    /**
     * Gets a InventoryLevelDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInventoryLevelDetailId The key requested.
     * @return new InventoryLevelDetailData()
     * @throws            SQLException
     */
    public static InventoryLevelDetailData select(Connection pCon, int pInventoryLevelDetailId)
        throws SQLException, DataNotFoundException {
        InventoryLevelDetailData x=null;
        String sql="SELECT INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID,PERIOD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVENTORY_LEVEL_DETAIL WHERE INVENTORY_LEVEL_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInventoryLevelDetailId=" + pInventoryLevelDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInventoryLevelDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InventoryLevelDetailData.createValue();
            
            x.setInventoryLevelDetailId(rs.getInt(1));
            x.setInventoryLevelId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setValue(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVENTORY_LEVEL_DETAIL_ID :" + pInventoryLevelDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InventoryLevelDetailDataVector object that consists
     * of InventoryLevelDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InventoryLevelDetailDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InventoryLevelDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVENTORY_LEVEL_DETAIL.INVENTORY_LEVEL_DETAIL_ID,CLW_INVENTORY_LEVEL_DETAIL.INVENTORY_LEVEL_ID,CLW_INVENTORY_LEVEL_DETAIL.PERIOD,CLW_INVENTORY_LEVEL_DETAIL.CLW_VALUE,CLW_INVENTORY_LEVEL_DETAIL.ADD_DATE,CLW_INVENTORY_LEVEL_DETAIL.ADD_BY,CLW_INVENTORY_LEVEL_DETAIL.MOD_DATE,CLW_INVENTORY_LEVEL_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InventoryLevelDetailData Object.
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
    *@returns a populated InventoryLevelDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InventoryLevelDetailData x = InventoryLevelDetailData.createValue();
         
         x.setInventoryLevelDetailId(rs.getInt(1+offset));
         x.setInventoryLevelId(rs.getInt(2+offset));
         x.setPeriod(rs.getInt(3+offset));
         x.setValue(rs.getInt(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InventoryLevelDetailData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a InventoryLevelDetailDataVector object that consists
     * of InventoryLevelDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InventoryLevelDetailDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID,PERIOD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVENTORY_LEVEL_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVENTORY_LEVEL_DETAIL.INVENTORY_LEVEL_DETAIL_ID,CLW_INVENTORY_LEVEL_DETAIL.INVENTORY_LEVEL_ID,CLW_INVENTORY_LEVEL_DETAIL.PERIOD,CLW_INVENTORY_LEVEL_DETAIL.CLW_VALUE,CLW_INVENTORY_LEVEL_DETAIL.ADD_DATE,CLW_INVENTORY_LEVEL_DETAIL.ADD_BY,CLW_INVENTORY_LEVEL_DETAIL.MOD_DATE,CLW_INVENTORY_LEVEL_DETAIL.MOD_BY FROM CLW_INVENTORY_LEVEL_DETAIL");
                where = pCriteria.getSqlClause("CLW_INVENTORY_LEVEL_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVENTORY_LEVEL_DETAIL.equals(otherTable)){
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
        InventoryLevelDetailDataVector v = new InventoryLevelDetailDataVector();
        while (rs.next()) {
            InventoryLevelDetailData x = InventoryLevelDetailData.createValue();
            
            x.setInventoryLevelDetailId(rs.getInt(1));
            x.setInventoryLevelId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setValue(rs.getInt(4));
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
     * Gets a InventoryLevelDetailDataVector object that consists
     * of InventoryLevelDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InventoryLevelDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InventoryLevelDetailDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InventoryLevelDetailDataVector v = new InventoryLevelDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID,PERIOD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVENTORY_LEVEL_DETAIL WHERE INVENTORY_LEVEL_DETAIL_ID IN (");

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
            InventoryLevelDetailData x=null;
            while (rs.next()) {
                // build the object
                x=InventoryLevelDetailData.createValue();
                
                x.setInventoryLevelDetailId(rs.getInt(1));
                x.setInventoryLevelId(rs.getInt(2));
                x.setPeriod(rs.getInt(3));
                x.setValue(rs.getInt(4));
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
     * Gets a InventoryLevelDetailDataVector object of all
     * InventoryLevelDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new InventoryLevelDetailDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID,PERIOD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVENTORY_LEVEL_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InventoryLevelDetailDataVector v = new InventoryLevelDetailDataVector();
        InventoryLevelDetailData x = null;
        while (rs.next()) {
            // build the object
            x = InventoryLevelDetailData.createValue();
            
            x.setInventoryLevelDetailId(rs.getInt(1));
            x.setInventoryLevelId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setValue(rs.getInt(4));
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
     * InventoryLevelDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_LEVEL_DETAIL_ID FROM CLW_INVENTORY_LEVEL_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_LEVEL_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_LEVEL_DETAIL");
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
     * Inserts a InventoryLevelDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InventoryLevelDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryLevelDetailData insert(Connection pCon, InventoryLevelDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVENTORY_LEVEL_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVENTORY_LEVEL_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInventoryLevelDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVENTORY_LEVEL_DETAIL (INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID,PERIOD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInventoryLevelDetailId());
        pstmt.setInt(2,pData.getInventoryLevelId());
        pstmt.setInt(3,pData.getPeriod());
        pstmt.setInt(4,pData.getValue());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVENTORY_LEVEL_DETAIL_ID="+pData.getInventoryLevelDetailId());
            log.debug("SQL:   INVENTORY_LEVEL_ID="+pData.getInventoryLevelId());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
        pData.setInventoryLevelDetailId(0);
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
     * Updates a InventoryLevelDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryLevelDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVENTORY_LEVEL_DETAIL SET INVENTORY_LEVEL_ID = ?,PERIOD = ?,CLW_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE INVENTORY_LEVEL_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getInventoryLevelId());
        pstmt.setInt(i++,pData.getPeriod());
        pstmt.setInt(i++,pData.getValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getInventoryLevelDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVENTORY_LEVEL_ID="+pData.getInventoryLevelId());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
     * Deletes a InventoryLevelDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryLevelDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryLevelDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVENTORY_LEVEL_DETAIL WHERE INVENTORY_LEVEL_DETAIL_ID = " + pInventoryLevelDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InventoryLevelDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVENTORY_LEVEL_DETAIL");
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
     * Inserts a InventoryLevelDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InventoryLevelDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVENTORY_LEVEL_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVENTORY_LEVEL_DETAIL_ID,INVENTORY_LEVEL_ID,PERIOD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInventoryLevelDetailId());
        pstmt.setInt(2+4,pData.getInventoryLevelId());
        pstmt.setInt(3+4,pData.getPeriod());
        pstmt.setInt(4+4,pData.getValue());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InventoryLevelDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InventoryLevelDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryLevelDetailData insert(Connection pCon, InventoryLevelDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InventoryLevelDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryLevelDetailData pData, boolean pLogFl)
        throws SQLException {
        InventoryLevelDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getInventoryLevelDetailId();
          try {
          oldData = InventoryLevelDetailDataAccess.select(pCon,id);
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
     * Deletes a InventoryLevelDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryLevelDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryLevelDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVENTORY_LEVEL_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_LEVEL_DETAIL d WHERE INVENTORY_LEVEL_DETAIL_ID = " + pInventoryLevelDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInventoryLevelDetailId);
        return n;
     }

    /**
     * Deletes InventoryLevelDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVENTORY_LEVEL_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_LEVEL_DETAIL d ");
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

