
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        AllStoreDataAccess
 * Description:  This class is used to build access methods to the ESW_ALL_STORE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.AllStoreData;
import com.cleanwise.service.api.value.AllStoreDataVector;
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
 * <code>AllStoreDataAccess</code>
 */
public class AllStoreDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(AllStoreDataAccess.class.getName());

    /** <code>ESW_ALL_STORE</code> table name */
	/* Primary key: ALL_STORE_ID */
	
    public static final String ESW_ALL_STORE = "ESW_ALL_STORE";
    
    /** <code>ALL_STORE_ID</code> ALL_STORE_ID column of table ESW_ALL_STORE */
    public static final String ALL_STORE_ID = "ALL_STORE_ID";
    /** <code>STORE_ID</code> STORE_ID column of table ESW_ALL_STORE */
    public static final String STORE_ID = "STORE_ID";
    /** <code>STORE_NAME</code> STORE_NAME column of table ESW_ALL_STORE */
    public static final String STORE_NAME = "STORE_NAME";
    /** <code>DOMAIN</code> DOMAIN column of table ESW_ALL_STORE */
    public static final String DOMAIN = "DOMAIN";
    /** <code>DATASOURCE</code> DATASOURCE column of table ESW_ALL_STORE */
    public static final String DATASOURCE = "DATASOURCE";
    /** <code>ADD_DATE</code> ADD_DATE column of table ESW_ALL_STORE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table ESW_ALL_STORE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table ESW_ALL_STORE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table ESW_ALL_STORE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public AllStoreDataAccess()
    {
    }

    /**
     * Gets a AllStoreData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pAllStoreId The key requested.
     * @return new AllStoreData()
     * @throws            SQLException
     */
    public static AllStoreData select(Connection pCon, int pAllStoreId)
        throws SQLException, DataNotFoundException {
        AllStoreData x=null;
        String sql="SELECT ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_STORE WHERE ALL_STORE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pAllStoreId=" + pAllStoreId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pAllStoreId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=AllStoreData.createValue();
            
            x.setAllStoreId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setStoreName(rs.getString(3));
            x.setDomain(rs.getString(4));
            x.setDatasource(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ALL_STORE_ID :" + pAllStoreId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a AllStoreDataVector object that consists
     * of AllStoreData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new AllStoreDataVector()
     * @throws            SQLException
     */
    public static AllStoreDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a AllStoreData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "ESW_ALL_STORE.ALL_STORE_ID,ESW_ALL_STORE.STORE_ID,ESW_ALL_STORE.STORE_NAME,ESW_ALL_STORE.DOMAIN,ESW_ALL_STORE.DATASOURCE,ESW_ALL_STORE.ADD_DATE,ESW_ALL_STORE.ADD_BY,ESW_ALL_STORE.MOD_DATE,ESW_ALL_STORE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated AllStoreData Object.
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
    *@returns a populated AllStoreData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         AllStoreData x = AllStoreData.createValue();
         
         x.setAllStoreId(rs.getInt(1+offset));
         x.setStoreId(rs.getInt(2+offset));
         x.setStoreName(rs.getString(3+offset));
         x.setDomain(rs.getString(4+offset));
         x.setDatasource(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the AllStoreData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a AllStoreDataVector object that consists
     * of AllStoreData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new AllStoreDataVector()
     * @throws            SQLException
     */
    public static AllStoreDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT ESW_ALL_STORE.ALL_STORE_ID,ESW_ALL_STORE.STORE_ID,ESW_ALL_STORE.STORE_NAME,ESW_ALL_STORE.DOMAIN,ESW_ALL_STORE.DATASOURCE,ESW_ALL_STORE.ADD_DATE,ESW_ALL_STORE.ADD_BY,ESW_ALL_STORE.MOD_DATE,ESW_ALL_STORE.MOD_BY FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause("ESW_ALL_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_STORE.equals(otherTable)){
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
        AllStoreDataVector v = new AllStoreDataVector();
        while (rs.next()) {
            AllStoreData x = AllStoreData.createValue();
            
            x.setAllStoreId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setStoreName(rs.getString(3));
            x.setDomain(rs.getString(4));
            x.setDatasource(rs.getString(5));
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
     * Gets a AllStoreDataVector object that consists
     * of AllStoreData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for AllStoreData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new AllStoreDataVector()
     * @throws            SQLException
     */
    public static AllStoreDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        AllStoreDataVector v = new AllStoreDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_STORE WHERE ALL_STORE_ID IN (");

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
            AllStoreData x=null;
            while (rs.next()) {
                // build the object
                x=AllStoreData.createValue();
                
                x.setAllStoreId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setStoreName(rs.getString(3));
                x.setDomain(rs.getString(4));
                x.setDatasource(rs.getString(5));
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
     * Gets a AllStoreDataVector object of all
     * AllStoreData objects in the database.
     * @param pCon An open database connection.
     * @return new AllStoreDataVector()
     * @throws            SQLException
     */
    public static AllStoreDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_STORE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        AllStoreDataVector v = new AllStoreDataVector();
        AllStoreData x = null;
        while (rs.next()) {
            // build the object
            x = AllStoreData.createValue();
            
            x.setAllStoreId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setStoreName(rs.getString(3));
            x.setDomain(rs.getString(4));
            x.setDatasource(rs.getString(5));
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
     * AllStoreData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT ALL_STORE_ID FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT ALL_STORE_ID FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause("ESW_ALL_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_STORE.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause("ESW_ALL_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_STORE.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_ALL_STORE");
                where = pCriteria.getSqlClause("ESW_ALL_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_ALL_STORE.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a AllStoreData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllStoreData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new AllStoreData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AllStoreData insert(Connection pCon, AllStoreData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT ESW_ALL_STORE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT ESW_ALL_STORE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setAllStoreId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO ESW_ALL_STORE (ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getAllStoreId());
        pstmt.setInt(2,pData.getStoreId());
        pstmt.setString(3,pData.getStoreName());
        pstmt.setString(4,pData.getDomain());
        pstmt.setString(5,pData.getDatasource());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ALL_STORE_ID="+pData.getAllStoreId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   STORE_NAME="+pData.getStoreName());
            log.debug("SQL:   DOMAIN="+pData.getDomain());
            log.debug("SQL:   DATASOURCE="+pData.getDatasource());
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
        pData.setAllStoreId(0);
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
     * Updates a AllStoreData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AllStoreData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AllStoreData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE ESW_ALL_STORE SET STORE_ID = ?,STORE_NAME = ?,DOMAIN = ?,DATASOURCE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ALL_STORE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getStoreName());
        pstmt.setString(i++,pData.getDomain());
        pstmt.setString(i++,pData.getDatasource());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getAllStoreId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   STORE_NAME="+pData.getStoreName());
            log.debug("SQL:   DOMAIN="+pData.getDomain());
            log.debug("SQL:   DATASOURCE="+pData.getDatasource());
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
     * Deletes a AllStoreData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAllStoreId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAllStoreId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM ESW_ALL_STORE WHERE ALL_STORE_ID = " + pAllStoreId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes AllStoreData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM ESW_ALL_STORE");
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
     * Inserts a AllStoreData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllStoreData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, AllStoreData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LESW_ALL_STORE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getAllStoreId());
        pstmt.setInt(2+4,pData.getStoreId());
        pstmt.setString(3+4,pData.getStoreName());
        pstmt.setString(4+4,pData.getDomain());
        pstmt.setString(5+4,pData.getDatasource());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a AllStoreData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A AllStoreData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new AllStoreData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static AllStoreData insert(Connection pCon, AllStoreData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a AllStoreData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A AllStoreData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, AllStoreData pData, boolean pLogFl)
        throws SQLException {
        AllStoreData oldData = null;
        if(pLogFl) {
          int id = pData.getAllStoreId();
          try {
          oldData = AllStoreDataAccess.select(pCon,id);
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
     * Deletes a AllStoreData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pAllStoreId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pAllStoreId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LESW_ALL_STORE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM ESW_ALL_STORE d WHERE ALL_STORE_ID = " + pAllStoreId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pAllStoreId);
        return n;
     }

    /**
     * Deletes AllStoreData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LESW_ALL_STORE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM ESW_ALL_STORE d ");
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

