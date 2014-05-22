
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UiDataAccess
 * Description:  This class is used to build access methods to the CLW_UI table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UiData;
import com.cleanwise.service.api.value.UiDataVector;
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
 * <code>UiDataAccess</code>
 */
public class UiDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UiDataAccess.class.getName());

    /** <code>CLW_UI</code> table name */
	/* Primary key: UI_ID */
	
    public static final String CLW_UI = "CLW_UI";
    
    /** <code>UI_ID</code> UI_ID column of table CLW_UI */
    public static final String UI_ID = "UI_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_UI */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_UI */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_UI */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_UI */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_UI */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_UI */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public UiDataAccess()
    {
    }

    /**
     * Gets a UiData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUiId The key requested.
     * @return new UiData()
     * @throws            SQLException
     */
    public static UiData select(Connection pCon, int pUiId)
        throws SQLException, DataNotFoundException {
        UiData x=null;
        String sql="SELECT UI_ID,SHORT_DESC,STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_UI WHERE UI_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUiId=" + pUiId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUiId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UiData.createValue();
            
            x.setUiId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setStatusCd(rs.getString(3));
            x.setAddBy(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("UI_ID :" + pUiId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UiDataVector object that consists
     * of UiData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UiDataVector()
     * @throws            SQLException
     */
    public static UiDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UiData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_UI.UI_ID,CLW_UI.SHORT_DESC,CLW_UI.STATUS_CD,CLW_UI.ADD_BY,CLW_UI.ADD_DATE,CLW_UI.MOD_BY,CLW_UI.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UiData Object.
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
    *@returns a populated UiData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UiData x = UiData.createValue();
         
         x.setUiId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setStatusCd(rs.getString(3+offset));
         x.setAddBy(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setModBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UiData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a UiDataVector object that consists
     * of UiData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UiDataVector()
     * @throws            SQLException
     */
    public static UiDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT UI_ID,SHORT_DESC,STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_UI");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_UI.UI_ID,CLW_UI.SHORT_DESC,CLW_UI.STATUS_CD,CLW_UI.ADD_BY,CLW_UI.ADD_DATE,CLW_UI.MOD_BY,CLW_UI.MOD_DATE FROM CLW_UI");
                where = pCriteria.getSqlClause("CLW_UI");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_UI.equals(otherTable)){
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
        UiDataVector v = new UiDataVector();
        while (rs.next()) {
            UiData x = UiData.createValue();
            
            x.setUiId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setStatusCd(rs.getString(3));
            x.setAddBy(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UiDataVector object that consists
     * of UiData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UiData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UiDataVector()
     * @throws            SQLException
     */
    public static UiDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UiDataVector v = new UiDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT UI_ID,SHORT_DESC,STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_UI WHERE UI_ID IN (");

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
            UiData x=null;
            while (rs.next()) {
                // build the object
                x=UiData.createValue();
                
                x.setUiId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setStatusCd(rs.getString(3));
                x.setAddBy(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setModBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UiDataVector object of all
     * UiData objects in the database.
     * @param pCon An open database connection.
     * @return new UiDataVector()
     * @throws            SQLException
     */
    public static UiDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT UI_ID,SHORT_DESC,STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_UI";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UiDataVector v = new UiDataVector();
        UiData x = null;
        while (rs.next()) {
            // build the object
            x = UiData.createValue();
            
            x.setUiId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setStatusCd(rs.getString(3));
            x.setAddBy(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UiData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT UI_ID FROM CLW_UI");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UI");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UI");
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
     * Inserts a UiData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UiData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UiData insert(Connection pCon, UiData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_UI_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_UI_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUiId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_UI (UI_ID,SHORT_DESC,STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUiId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getStatusCd());
        pstmt.setString(4,pData.getAddBy());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getModBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UI_ID="+pData.getUiId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setUiId(0);
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
     * Updates a UiData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UiData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UiData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_UI SET SHORT_DESC = ?,STATUS_CD = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE UI_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getUiId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
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
     * Deletes a UiData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUiId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUiId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_UI WHERE UI_ID = " + pUiId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UiData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_UI");
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
     * Inserts a UiData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UiData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_UI (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "UI_ID,SHORT_DESC,STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUiId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getStatusCd());
        pstmt.setString(4+4,pData.getAddBy());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getModBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UiData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UiData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UiData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UiData insert(Connection pCon, UiData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UiData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UiData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UiData pData, boolean pLogFl)
        throws SQLException {
        UiData oldData = null;
        if(pLogFl) {
          int id = pData.getUiId();
          try {
          oldData = UiDataAccess.select(pCon,id);
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
     * Deletes a UiData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUiId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUiId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_UI SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UI d WHERE UI_ID = " + pUiId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUiId);
        return n;
     }

    /**
     * Deletes UiData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_UI SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UI d ");
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

