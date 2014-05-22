
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AssetPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_ASSET_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AssetPropertyData;
import com.cleanwise.service.api.value.AssetPropertyDataVector;
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
 * <code>AssetPropertyDataAccess</code>
 */
public class AssetPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AssetPropertyDataAccess.class.getName());

    /** <code>CLW_ASSET_PROPERTY</code> table name */
	/* Primary key: ASSET_PROPERTY_ID */
	
    public static final String CLW_ASSET_PROPERTY = "CLW_ASSET_PROPERTY";
    
    /** <code>ASSET_PROPERTY_ID</code> ASSET_PROPERTY_ID column of table CLW_ASSET_PROPERTY */
    public static final String ASSET_PROPERTY_ID = "ASSET_PROPERTY_ID";
    /** <code>ASSET_ID</code> ASSET_ID column of table CLW_ASSET_PROPERTY */
    public static final String ASSET_ID = "ASSET_ID";
    /** <code>ASSET_PROPERTY_CD</code> ASSET_PROPERTY_CD column of table CLW_ASSET_PROPERTY */
    public static final String ASSET_PROPERTY_CD = "ASSET_PROPERTY_CD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_ASSET_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ASSET_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ASSET_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ASSET_PROPERTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ASSET_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public AssetPropertyDataAccess()
    {
    }

    /**
     * Gets a AssetPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAssetPropertyId The key requested.
     * @return new AssetPropertyData()
     * @throws            SQLException
     */
    public static AssetPropertyData select(Connection pCon, int pAssetPropertyId)
        throws SQLException, DataNotFoundException {
        AssetPropertyData x=null;
        String sql="SELECT ASSET_PROPERTY_ID,ASSET_ID,ASSET_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ASSET_PROPERTY WHERE ASSET_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAssetPropertyId=" + pAssetPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAssetPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AssetPropertyData.createValue();
            
            x.setAssetPropertyId(rs.getInt(1));
            x.setAssetId(rs.getInt(2));
            x.setAssetPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ASSET_PROPERTY_ID :" + pAssetPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AssetPropertyDataVector object that consists
     * of AssetPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AssetPropertyDataVector()
     * @throws            SQLException
     */
    public static AssetPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AssetPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ASSET_PROPERTY.ASSET_PROPERTY_ID,CLW_ASSET_PROPERTY.ASSET_ID,CLW_ASSET_PROPERTY.ASSET_PROPERTY_CD,CLW_ASSET_PROPERTY.CLW_VALUE,CLW_ASSET_PROPERTY.ADD_BY,CLW_ASSET_PROPERTY.ADD_DATE,CLW_ASSET_PROPERTY.MOD_BY,CLW_ASSET_PROPERTY.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AssetPropertyData Object.
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
    *@returns a populated AssetPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AssetPropertyData x = AssetPropertyData.createValue();
         
         x.setAssetPropertyId(rs.getInt(1+offset));
         x.setAssetId(rs.getInt(2+offset));
         x.setAssetPropertyCd(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AssetPropertyData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a AssetPropertyDataVector object that consists
     * of AssetPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AssetPropertyDataVector()
     * @throws            SQLException
     */
    public static AssetPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ASSET_PROPERTY_ID,ASSET_ID,ASSET_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ASSET_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ASSET_PROPERTY.ASSET_PROPERTY_ID,CLW_ASSET_PROPERTY.ASSET_ID,CLW_ASSET_PROPERTY.ASSET_PROPERTY_CD,CLW_ASSET_PROPERTY.CLW_VALUE,CLW_ASSET_PROPERTY.ADD_BY,CLW_ASSET_PROPERTY.ADD_DATE,CLW_ASSET_PROPERTY.MOD_BY,CLW_ASSET_PROPERTY.MOD_DATE FROM CLW_ASSET_PROPERTY");
                where = pCriteria.getSqlClause("CLW_ASSET_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ASSET_PROPERTY.equals(otherTable)){
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
        AssetPropertyDataVector v = new AssetPropertyDataVector();
        while (rs.next()) {
            AssetPropertyData x = AssetPropertyData.createValue();
            
            x.setAssetPropertyId(rs.getInt(1));
            x.setAssetId(rs.getInt(2));
            x.setAssetPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a AssetPropertyDataVector object that consists
     * of AssetPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AssetPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AssetPropertyDataVector()
     * @throws            SQLException
     */
    public static AssetPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AssetPropertyDataVector v = new AssetPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ASSET_PROPERTY_ID,ASSET_ID,ASSET_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ASSET_PROPERTY WHERE ASSET_PROPERTY_ID IN (");

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
            AssetPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=AssetPropertyData.createValue();
                
                x.setAssetPropertyId(rs.getInt(1));
                x.setAssetId(rs.getInt(2));
                x.setAssetPropertyCd(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setAddBy(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
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
     * Gets a AssetPropertyDataVector object of all
     * AssetPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new AssetPropertyDataVector()
     * @throws            SQLException
     */
    public static AssetPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ASSET_PROPERTY_ID,ASSET_ID,ASSET_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ASSET_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AssetPropertyDataVector v = new AssetPropertyDataVector();
        AssetPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = AssetPropertyData.createValue();
            
            x.setAssetPropertyId(rs.getInt(1));
            x.setAssetId(rs.getInt(2));
            x.setAssetPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
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
     * AssetPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ASSET_PROPERTY_ID FROM CLW_ASSET_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ASSET_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ASSET_PROPERTY");
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
     * Inserts a AssetPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AssetPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AssetPropertyData insert(Connection pCon, AssetPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ASSET_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ASSET_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAssetPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ASSET_PROPERTY (ASSET_PROPERTY_ID,ASSET_ID,ASSET_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAssetPropertyId());
        pstmt.setInt(2,pData.getAssetId());
        pstmt.setString(3,pData.getAssetPropertyCd());
        pstmt.setString(4,pData.getValue());
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getModBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ASSET_PROPERTY_ID="+pData.getAssetPropertyId());
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   ASSET_PROPERTY_CD="+pData.getAssetPropertyCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
        pData.setAssetPropertyId(0);
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
     * Updates a AssetPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AssetPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ASSET_PROPERTY SET ASSET_ID = ?,ASSET_PROPERTY_CD = ?,CLW_VALUE = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE ASSET_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getAssetId());
        pstmt.setString(i++,pData.getAssetPropertyCd());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getAssetPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ASSET_ID="+pData.getAssetId());
            log.debug("SQL:   ASSET_PROPERTY_CD="+pData.getAssetPropertyCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
     * Deletes a AssetPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAssetPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAssetPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ASSET_PROPERTY WHERE ASSET_PROPERTY_ID = " + pAssetPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AssetPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ASSET_PROPERTY");
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
     * Inserts a AssetPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AssetPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ASSET_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ASSET_PROPERTY_ID,ASSET_ID,ASSET_PROPERTY_CD,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAssetPropertyId());
        pstmt.setInt(2+4,pData.getAssetId());
        pstmt.setString(3+4,pData.getAssetPropertyCd());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getModBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AssetPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AssetPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AssetPropertyData insert(Connection pCon, AssetPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AssetPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AssetPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AssetPropertyData pData, boolean pLogFl)
        throws SQLException {
        AssetPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getAssetPropertyId();
          try {
          oldData = AssetPropertyDataAccess.select(pCon,id);
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
     * Deletes a AssetPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAssetPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAssetPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ASSET_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ASSET_PROPERTY d WHERE ASSET_PROPERTY_ID = " + pAssetPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAssetPropertyId);
        return n;
     }

    /**
     * Deletes AssetPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ASSET_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ASSET_PROPERTY d ");
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

