
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AssetWarrantyDataAccess
 * Description:  This class is used to build access methods to the CLW_ASSET_WARRANTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AssetWarrantyData;
import com.cleanwise.service.api.value.AssetWarrantyDataVector;
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
 * <code>AssetWarrantyDataAccess</code>
 */
public class AssetWarrantyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AssetWarrantyDataAccess.class.getName());

    /** <code>CLW_ASSET_WARRANTY</code> table name */
	/* Primary key: ASSET_WARRANTY_ID */
	
    public static final String CLW_ASSET_WARRANTY = "CLW_ASSET_WARRANTY";
    
    /** <code>ASSET_WARRANTY_ID</code> ASSET_WARRANTY_ID column of table CLW_ASSET_WARRANTY */
    public static final String ASSET_WARRANTY_ID = "ASSET_WARRANTY_ID";
    /** <code>ASSET_ID</code> ASSET_ID column of table CLW_ASSET_WARRANTY */
    public static final String ASSET_ID = "ASSET_ID";
    /** <code>WARRANTY_ID</code> WARRANTY_ID column of table CLW_ASSET_WARRANTY */
    public static final String WARRANTY_ID = "WARRANTY_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ASSET_WARRANTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ASSET_WARRANTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ASSET_WARRANTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ASSET_WARRANTY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public AssetWarrantyDataAccess()
    {
    }

    /**
     * Gets a AssetWarrantyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAssetWarrantyId The key requested.
     * @return new AssetWarrantyData()
     * @throws            SQLException
     */
    public static AssetWarrantyData select(Connection pCon, int pAssetWarrantyId)
        throws SQLException, DataNotFoundException {
        AssetWarrantyData x=null;
        String sql="SELECT ASSET_WARRANTY_ID,ASSET_ID,WARRANTY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ASSET_WARRANTY WHERE ASSET_WARRANTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAssetWarrantyId=" + pAssetWarrantyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAssetWarrantyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AssetWarrantyData.createValue();
            
            x.setAssetWarrantyId(rs.getInt(1));
            x.setAssetId(rs.getInt(2));
            x.setWarrantyId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ASSET_WARRANTY_ID :" + pAssetWarrantyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AssetWarrantyDataVector object that consists
     * of AssetWarrantyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AssetWarrantyDataVector()
     * @throws            SQLException
     */
    public static AssetWarrantyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AssetWarrantyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ASSET_WARRANTY.ASSET_WARRANTY_ID,CLW_ASSET_WARRANTY.ASSET_ID,CLW_ASSET_WARRANTY.WARRANTY_ID,CLW_ASSET_WARRANTY.ADD_DATE,CLW_ASSET_WARRANTY.ADD_BY,CLW_ASSET_WARRANTY.MOD_DATE,CLW_ASSET_WARRANTY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AssetWarrantyData Object.
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
    *@returns a populated AssetWarrantyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AssetWarrantyData x = AssetWarrantyData.createValue();
         
         x.setAssetWarrantyId(rs.getInt(1+offset));
         x.setAssetId(rs.getInt(2+offset));
         x.setWarrantyId(rs.getInt(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setModDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AssetWarrantyData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a AssetWarrantyDataVector object that consists
     * of AssetWarrantyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AssetWarrantyDataVector()
     * @throws            SQLException
     */
    public static AssetWarrantyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ASSET_WARRANTY_ID,ASSET_ID,WARRANTY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ASSET_WARRANTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ASSET_WARRANTY.ASSET_WARRANTY_ID,CLW_ASSET_WARRANTY.ASSET_ID,CLW_ASSET_WARRANTY.WARRANTY_ID,CLW_ASSET_WARRANTY.ADD_DATE,CLW_ASSET_WARRANTY.ADD_BY,CLW_ASSET_WARRANTY.MOD_DATE,CLW_ASSET_WARRANTY.MOD_BY FROM CLW_ASSET_WARRANTY");
                where = pCriteria.getSqlClause("CLW_ASSET_WARRANTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ASSET_WARRANTY.equals(otherTable)){
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
        AssetWarrantyDataVector v = new AssetWarrantyDataVector();
        while (rs.next()) {
            AssetWarrantyData x = AssetWarrantyData.createValue();
            
            x.setAssetWarrantyId(rs.getInt(1));
            x.setAssetId(rs.getInt(2));
            x.setWarrantyId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a AssetWarrantyDataVector object that consists
     * of AssetWarrantyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AssetWarrantyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AssetWarrantyDataVector()
     * @throws            SQLException
     */
    public static AssetWarrantyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AssetWarrantyDataVector v = new AssetWarrantyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ASSET_WARRANTY_ID,ASSET_ID,WARRANTY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ASSET_WARRANTY WHERE ASSET_WARRANTY_ID IN (");

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
            AssetWarrantyData x=null;
            while (rs.next()) {
                // build the object
                x=AssetWarrantyData.createValue();
                
                x.setAssetWarrantyId(rs.getInt(1));
                x.setAssetId(rs.getInt(2));
                x.setWarrantyId(rs.getInt(3));
                x.setAddDate(rs.getTimestamp(4));
                x.setAddBy(rs.getString(5));
                x.setModDate(rs.getTimestamp(6));
                x.setModBy(rs.getString(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a AssetWarrantyDataVector object of all
     * AssetWarrantyData objects in the database.
     * @param pCon An open database connection.
     * @return new AssetWarrantyDataVector()
     * @throws            SQLException
     */
    public static AssetWarrantyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ASSET_WARRANTY_ID,ASSET_ID,WARRANTY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ASSET_WARRANTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AssetWarrantyDataVector v = new AssetWarrantyDataVector();
        AssetWarrantyData x = null;
        while (rs.next()) {
            // build the object
            x = AssetWarrantyData.createValue();
            
            x.setAssetWarrantyId(rs.getInt(1));
            x.setAssetId(rs.getInt(2));
            x.setWarrantyId(rs.getInt(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * AssetWarrantyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ASSET_WARRANTY_ID FROM CLW_ASSET_WARRANTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ASSET_WARRANTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ASSET_WARRANTY");
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
     * Inserts a AssetWarrantyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetWarrantyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AssetWarrantyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AssetWarrantyData insert(Connection pCon, AssetWarrantyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ASSET_WARRANTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ASSET_WARRANTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAssetWarrantyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ASSET_WARRANTY (ASSET_WARRANTY_ID,ASSET_ID,WARRANTY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAssetWarrantyId());
        pstmt.setInt(2,pData.getAssetId());
        pstmt.setInt(3,pData.getWarrantyId());
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ASSET_WARRANTY_ID="+pData.getAssetWarrantyId());
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
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
        pData.setAssetWarrantyId(0);
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
     * Updates a AssetWarrantyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetWarrantyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AssetWarrantyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ASSET_WARRANTY SET ASSET_ID = ?,WARRANTY_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ASSET_WARRANTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getAssetId());
        pstmt.setInt(i++,pData.getWarrantyId());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getAssetWarrantyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   WARRANTY_ID="+pData.getWarrantyId());
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
     * Deletes a AssetWarrantyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAssetWarrantyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAssetWarrantyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ASSET_WARRANTY WHERE ASSET_WARRANTY_ID = " + pAssetWarrantyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AssetWarrantyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ASSET_WARRANTY");
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
     * Inserts a AssetWarrantyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetWarrantyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AssetWarrantyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ASSET_WARRANTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ASSET_WARRANTY_ID,ASSET_ID,WARRANTY_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAssetWarrantyId());
        pstmt.setInt(2+4,pData.getAssetId());
        pstmt.setInt(3+4,pData.getWarrantyId());
        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AssetWarrantyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetWarrantyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AssetWarrantyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AssetWarrantyData insert(Connection pCon, AssetWarrantyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AssetWarrantyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetWarrantyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AssetWarrantyData pData, boolean pLogFl)
        throws SQLException {
        AssetWarrantyData oldData = null;
        if(pLogFl) {
          int id = pData.getAssetWarrantyId();
          try {
          oldData = AssetWarrantyDataAccess.select(pCon,id);
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
     * Deletes a AssetWarrantyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAssetWarrantyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAssetWarrantyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ASSET_WARRANTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ASSET_WARRANTY d WHERE ASSET_WARRANTY_ID = " + pAssetWarrantyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAssetWarrantyId);
        return n;
     }

    /**
     * Deletes AssetWarrantyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ASSET_WARRANTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ASSET_WARRANTY d ");
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

