
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        LocaleMapDataAccess
 * Description:  This class is used to build access methods to the CLW_LOCALE_MAP table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.LocaleMapData;
import com.cleanwise.service.api.value.LocaleMapDataVector;
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
 * <code>LocaleMapDataAccess</code>
 */
public class LocaleMapDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(LocaleMapDataAccess.class.getName());

    /** <code>CLW_LOCALE_MAP</code> table name */
	/* Primary key: LOCALE_MAP_ID */
	
    public static final String CLW_LOCALE_MAP = "CLW_LOCALE_MAP";
    
    /** <code>LOCALE_MAP_ID</code> LOCALE_MAP_ID column of table CLW_LOCALE_MAP */
    public static final String LOCALE_MAP_ID = "LOCALE_MAP_ID";
    /** <code>TABLE_NAME</code> TABLE_NAME column of table CLW_LOCALE_MAP */
    public static final String TABLE_NAME = "TABLE_NAME";
    /** <code>COLUMN_NAME</code> COLUMN_NAME column of table CLW_LOCALE_MAP */
    public static final String COLUMN_NAME = "COLUMN_NAME";
    /** <code>LOCALE_ID</code> LOCALE_ID column of table CLW_LOCALE_MAP */
    public static final String LOCALE_ID = "LOCALE_ID";
    /** <code>PATH</code> PATH column of table CLW_LOCALE_MAP */
    public static final String PATH = "PATH";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_LOCALE_MAP */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_LOCALE_MAP */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_LOCALE_MAP */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_LOCALE_MAP */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public LocaleMapDataAccess()
    {
    }

    /**
     * Gets a LocaleMapData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pLocaleMapId The key requested.
     * @return new LocaleMapData()
     * @throws            SQLException
     */
    public static LocaleMapData select(Connection pCon, int pLocaleMapId)
        throws SQLException, DataNotFoundException {
        LocaleMapData x=null;
        String sql="SELECT LOCALE_MAP_ID,TABLE_NAME,COLUMN_NAME,LOCALE_ID,PATH,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE_MAP WHERE LOCALE_MAP_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pLocaleMapId=" + pLocaleMapId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pLocaleMapId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=LocaleMapData.createValue();
            
            x.setLocaleMapId(rs.getInt(1));
            x.setTableName(rs.getString(2));
            x.setColumnName(rs.getString(3));
            x.setLocaleId(rs.getInt(4));
            x.setPath(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("LOCALE_MAP_ID :" + pLocaleMapId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a LocaleMapDataVector object that consists
     * of LocaleMapData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new LocaleMapDataVector()
     * @throws            SQLException
     */
    public static LocaleMapDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a LocaleMapData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_LOCALE_MAP.LOCALE_MAP_ID,CLW_LOCALE_MAP.TABLE_NAME,CLW_LOCALE_MAP.COLUMN_NAME,CLW_LOCALE_MAP.LOCALE_ID,CLW_LOCALE_MAP.PATH,CLW_LOCALE_MAP.ADD_DATE,CLW_LOCALE_MAP.ADD_BY,CLW_LOCALE_MAP.MOD_DATE,CLW_LOCALE_MAP.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated LocaleMapData Object.
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
    *@returns a populated LocaleMapData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         LocaleMapData x = LocaleMapData.createValue();
         
         x.setLocaleMapId(rs.getInt(1+offset));
         x.setTableName(rs.getString(2+offset));
         x.setColumnName(rs.getString(3+offset));
         x.setLocaleId(rs.getInt(4+offset));
         x.setPath(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the LocaleMapData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a LocaleMapDataVector object that consists
     * of LocaleMapData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new LocaleMapDataVector()
     * @throws            SQLException
     */
    public static LocaleMapDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT LOCALE_MAP_ID,TABLE_NAME,COLUMN_NAME,LOCALE_ID,PATH,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE_MAP");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_LOCALE_MAP.LOCALE_MAP_ID,CLW_LOCALE_MAP.TABLE_NAME,CLW_LOCALE_MAP.COLUMN_NAME,CLW_LOCALE_MAP.LOCALE_ID,CLW_LOCALE_MAP.PATH,CLW_LOCALE_MAP.ADD_DATE,CLW_LOCALE_MAP.ADD_BY,CLW_LOCALE_MAP.MOD_DATE,CLW_LOCALE_MAP.MOD_BY FROM CLW_LOCALE_MAP");
                where = pCriteria.getSqlClause("CLW_LOCALE_MAP");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_LOCALE_MAP.equals(otherTable)){
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
        LocaleMapDataVector v = new LocaleMapDataVector();
        while (rs.next()) {
            LocaleMapData x = LocaleMapData.createValue();
            
            x.setLocaleMapId(rs.getInt(1));
            x.setTableName(rs.getString(2));
            x.setColumnName(rs.getString(3));
            x.setLocaleId(rs.getInt(4));
            x.setPath(rs.getString(5));
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
     * Gets a LocaleMapDataVector object that consists
     * of LocaleMapData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for LocaleMapData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new LocaleMapDataVector()
     * @throws            SQLException
     */
    public static LocaleMapDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        LocaleMapDataVector v = new LocaleMapDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT LOCALE_MAP_ID,TABLE_NAME,COLUMN_NAME,LOCALE_ID,PATH,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE_MAP WHERE LOCALE_MAP_ID IN (");

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
            LocaleMapData x=null;
            while (rs.next()) {
                // build the object
                x=LocaleMapData.createValue();
                
                x.setLocaleMapId(rs.getInt(1));
                x.setTableName(rs.getString(2));
                x.setColumnName(rs.getString(3));
                x.setLocaleId(rs.getInt(4));
                x.setPath(rs.getString(5));
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
     * Gets a LocaleMapDataVector object of all
     * LocaleMapData objects in the database.
     * @param pCon An open database connection.
     * @return new LocaleMapDataVector()
     * @throws            SQLException
     */
    public static LocaleMapDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT LOCALE_MAP_ID,TABLE_NAME,COLUMN_NAME,LOCALE_ID,PATH,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE_MAP";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        LocaleMapDataVector v = new LocaleMapDataVector();
        LocaleMapData x = null;
        while (rs.next()) {
            // build the object
            x = LocaleMapData.createValue();
            
            x.setLocaleMapId(rs.getInt(1));
            x.setTableName(rs.getString(2));
            x.setColumnName(rs.getString(3));
            x.setLocaleId(rs.getInt(4));
            x.setPath(rs.getString(5));
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
     * LocaleMapData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT LOCALE_MAP_ID FROM CLW_LOCALE_MAP");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LOCALE_MAP");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LOCALE_MAP");
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
     * Inserts a LocaleMapData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleMapData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new LocaleMapData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static LocaleMapData insert(Connection pCon, LocaleMapData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_LOCALE_MAP_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_LOCALE_MAP_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setLocaleMapId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_LOCALE_MAP (LOCALE_MAP_ID,TABLE_NAME,COLUMN_NAME,LOCALE_ID,PATH,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getLocaleMapId());
        pstmt.setString(2,pData.getTableName());
        pstmt.setString(3,pData.getColumnName());
        if (pData.getLocaleId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getLocaleId());
        }

        pstmt.setString(5,pData.getPath());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   LOCALE_MAP_ID="+pData.getLocaleMapId());
            log.debug("SQL:   TABLE_NAME="+pData.getTableName());
            log.debug("SQL:   COLUMN_NAME="+pData.getColumnName());
            log.debug("SQL:   LOCALE_ID="+pData.getLocaleId());
            log.debug("SQL:   PATH="+pData.getPath());
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
        pData.setLocaleMapId(0);
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
     * Updates a LocaleMapData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleMapData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, LocaleMapData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_LOCALE_MAP SET TABLE_NAME = ?,COLUMN_NAME = ?,LOCALE_ID = ?,PATH = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE LOCALE_MAP_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getTableName());
        pstmt.setString(i++,pData.getColumnName());
        if (pData.getLocaleId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getLocaleId());
        }

        pstmt.setString(i++,pData.getPath());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getLocaleMapId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TABLE_NAME="+pData.getTableName());
            log.debug("SQL:   COLUMN_NAME="+pData.getColumnName());
            log.debug("SQL:   LOCALE_ID="+pData.getLocaleId());
            log.debug("SQL:   PATH="+pData.getPath());
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
     * Deletes a LocaleMapData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pLocaleMapId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pLocaleMapId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_LOCALE_MAP WHERE LOCALE_MAP_ID = " + pLocaleMapId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes LocaleMapData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_LOCALE_MAP");
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
     * Inserts a LocaleMapData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleMapData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, LocaleMapData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_LOCALE_MAP (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "LOCALE_MAP_ID,TABLE_NAME,COLUMN_NAME,LOCALE_ID,PATH,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getLocaleMapId());
        pstmt.setString(2+4,pData.getTableName());
        pstmt.setString(3+4,pData.getColumnName());
        if (pData.getLocaleId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getLocaleId());
        }

        pstmt.setString(5+4,pData.getPath());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a LocaleMapData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleMapData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new LocaleMapData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static LocaleMapData insert(Connection pCon, LocaleMapData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a LocaleMapData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleMapData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, LocaleMapData pData, boolean pLogFl)
        throws SQLException {
        LocaleMapData oldData = null;
        if(pLogFl) {
          int id = pData.getLocaleMapId();
          try {
          oldData = LocaleMapDataAccess.select(pCon,id);
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
     * Deletes a LocaleMapData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pLocaleMapId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pLocaleMapId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_LOCALE_MAP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_LOCALE_MAP d WHERE LOCALE_MAP_ID = " + pLocaleMapId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pLocaleMapId);
        return n;
     }

    /**
     * Deletes LocaleMapData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_LOCALE_MAP SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_LOCALE_MAP d ");
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

