
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemMetaDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_META table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>ItemMetaDataAccess</code>
 */
public class ItemMetaDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemMetaDataAccess.class.getName());

    /** <code>CLW_ITEM_META</code> table name */
	/* Primary key: ITEM_META_ID */
	
    public static final String CLW_ITEM_META = "CLW_ITEM_META";
    
    /** <code>ITEM_META_ID</code> ITEM_META_ID column of table CLW_ITEM_META */
    public static final String ITEM_META_ID = "ITEM_META_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ITEM_META */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>VALUE_ID</code> VALUE_ID column of table CLW_ITEM_META */
    public static final String VALUE_ID = "VALUE_ID";
    /** <code>NAME_VALUE</code> NAME_VALUE column of table CLW_ITEM_META */
    public static final String NAME_VALUE = "NAME_VALUE";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_ITEM_META */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_META */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_META */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_META */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_META */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ItemMetaDataAccess()
    {
    }

    /**
     * Gets a ItemMetaData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemMetaId The key requested.
     * @return new ItemMetaData()
     * @throws            SQLException
     */
    public static ItemMetaData select(Connection pCon, int pItemMetaId)
        throws SQLException, DataNotFoundException {
        ItemMetaData x=null;
        String sql="SELECT ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_META WHERE ITEM_META_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemMetaId=" + pItemMetaId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemMetaId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemMetaData.createValue();
            
            x.setItemMetaId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setValueId(rs.getInt(3));
            x.setNameValue(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_META_ID :" + pItemMetaId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemMetaDataVector object that consists
     * of ItemMetaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemMetaDataVector()
     * @throws            SQLException
     */
    public static ItemMetaDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemMetaData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_META.ITEM_META_ID,CLW_ITEM_META.ITEM_ID,CLW_ITEM_META.VALUE_ID,CLW_ITEM_META.NAME_VALUE,CLW_ITEM_META.CLW_VALUE,CLW_ITEM_META.ADD_DATE,CLW_ITEM_META.ADD_BY,CLW_ITEM_META.MOD_DATE,CLW_ITEM_META.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemMetaData Object.
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
    *@returns a populated ItemMetaData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemMetaData x = ItemMetaData.createValue();
         
         x.setItemMetaId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setValueId(rs.getInt(3+offset));
         x.setNameValue(rs.getString(4+offset));
         x.setValue(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemMetaData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a ItemMetaDataVector object that consists
     * of ItemMetaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemMetaDataVector()
     * @throws            SQLException
     */
    public static ItemMetaDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_META");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_META.ITEM_META_ID,CLW_ITEM_META.ITEM_ID,CLW_ITEM_META.VALUE_ID,CLW_ITEM_META.NAME_VALUE,CLW_ITEM_META.CLW_VALUE,CLW_ITEM_META.ADD_DATE,CLW_ITEM_META.ADD_BY,CLW_ITEM_META.MOD_DATE,CLW_ITEM_META.MOD_BY FROM CLW_ITEM_META");
                where = pCriteria.getSqlClause("CLW_ITEM_META");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM_META.equals(otherTable)){
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
        ItemMetaDataVector v = new ItemMetaDataVector();
        while (rs.next()) {
            ItemMetaData x = ItemMetaData.createValue();
            
            x.setItemMetaId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setValueId(rs.getInt(3));
            x.setNameValue(rs.getString(4));
            x.setValue(rs.getString(5));
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
     * Gets a ItemMetaDataVector object that consists
     * of ItemMetaData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemMetaData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemMetaDataVector()
     * @throws            SQLException
     */
    public static ItemMetaDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemMetaDataVector v = new ItemMetaDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_META WHERE ITEM_META_ID IN (");

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
            ItemMetaData x=null;
            while (rs.next()) {
                // build the object
                x=ItemMetaData.createValue();
                
                x.setItemMetaId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setValueId(rs.getInt(3));
                x.setNameValue(rs.getString(4));
                x.setValue(rs.getString(5));
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
     * Gets a ItemMetaDataVector object of all
     * ItemMetaData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemMetaDataVector()
     * @throws            SQLException
     */
    public static ItemMetaDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_META";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemMetaDataVector v = new ItemMetaDataVector();
        ItemMetaData x = null;
        while (rs.next()) {
            // build the object
            x = ItemMetaData.createValue();
            
            x.setItemMetaId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setValueId(rs.getInt(3));
            x.setNameValue(rs.getString(4));
            x.setValue(rs.getString(5));
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
     * ItemMetaData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_META_ID FROM CLW_ITEM_META");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_META");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_META");
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
     * Inserts a ItemMetaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMetaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemMetaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemMetaData insert(Connection pCon, ItemMetaData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_META_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_META_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemMetaId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_META (ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemMetaId());
        pstmt.setInt(2,pData.getItemId());
        pstmt.setInt(3,pData.getValueId());
        pstmt.setString(4,pData.getNameValue());
        pstmt.setString(5,pData.getValue());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_META_ID="+pData.getItemMetaId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   VALUE_ID="+pData.getValueId());
            log.debug("SQL:   NAME_VALUE="+pData.getNameValue());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setItemMetaId(0);
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
     * Updates a ItemMetaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMetaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemMetaData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_META SET ITEM_ID = ?,VALUE_ID = ?,NAME_VALUE = ?,CLW_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ITEM_META_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getValueId());
        pstmt.setString(i++,pData.getNameValue());
        pstmt.setString(i++,pData.getValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getItemMetaId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   VALUE_ID="+pData.getValueId());
            log.debug("SQL:   NAME_VALUE="+pData.getNameValue());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();
        
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ItemMetaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemMetaId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemMetaId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_META WHERE ITEM_META_ID = " + pItemMetaId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemMetaData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_META");
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
     * Inserts a ItemMetaData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMetaData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemMetaData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM_META (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_META_ID,ITEM_ID,VALUE_ID,NAME_VALUE,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemMetaId());
        pstmt.setInt(2+4,pData.getItemId());
        pstmt.setInt(3+4,pData.getValueId());
        pstmt.setString(4+4,pData.getNameValue());
        pstmt.setString(5+4,pData.getValue());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemMetaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMetaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemMetaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemMetaData insert(Connection pCon, ItemMetaData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemMetaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemMetaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemMetaData pData, boolean pLogFl)
        throws SQLException {
        ItemMetaData oldData = null;
        if(pLogFl) {
          int id = pData.getItemMetaId();
          try {
          oldData = ItemMetaDataAccess.select(pCon,id);
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
     * Deletes a ItemMetaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemMetaId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemMetaId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM_META SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_META d WHERE ITEM_META_ID = " + pItemMetaId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemMetaId);
        return n;
     }

    /**
     * Deletes ItemMetaData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM_META SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_META d ");
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

