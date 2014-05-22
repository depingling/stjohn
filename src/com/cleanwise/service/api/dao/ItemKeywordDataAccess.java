
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ItemKeywordDataAccess
 * Description:  This class is used to build access methods to the CLW_ITEM_KEYWORD table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ItemKeywordData;
import com.cleanwise.service.api.value.ItemKeywordDataVector;
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
 * <code>ItemKeywordDataAccess</code>
 */
public class ItemKeywordDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ItemKeywordDataAccess.class.getName());

    /** <code>CLW_ITEM_KEYWORD</code> table name */
	/* Primary key: ITEM_KEYWORD_ID */
	
    public static final String CLW_ITEM_KEYWORD = "CLW_ITEM_KEYWORD";
    
    /** <code>ITEM_KEYWORD_ID</code> ITEM_KEYWORD_ID column of table CLW_ITEM_KEYWORD */
    public static final String ITEM_KEYWORD_ID = "ITEM_KEYWORD_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ITEM_KEYWORD */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>KEYWORD</code> KEYWORD column of table CLW_ITEM_KEYWORD */
    public static final String KEYWORD = "KEYWORD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ITEM_KEYWORD */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ITEM_KEYWORD */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ITEM_KEYWORD */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ITEM_KEYWORD */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ItemKeywordDataAccess()
    {
    }

    /**
     * Gets a ItemKeywordData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pItemKeywordId The key requested.
     * @return new ItemKeywordData()
     * @throws            SQLException
     */
    public static ItemKeywordData select(Connection pCon, int pItemKeywordId)
        throws SQLException, DataNotFoundException {
        ItemKeywordData x=null;
        String sql="SELECT ITEM_KEYWORD_ID,ITEM_ID,KEYWORD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_KEYWORD WHERE ITEM_KEYWORD_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pItemKeywordId=" + pItemKeywordId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pItemKeywordId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ItemKeywordData.createValue();
            
            x.setItemKeywordId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setKeyword(rs.getString(3));
            x.setAddDate(rs.getTimestamp(4));
            x.setAddBy(rs.getString(5));
            x.setModDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ITEM_KEYWORD_ID :" + pItemKeywordId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ItemKeywordDataVector object that consists
     * of ItemKeywordData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ItemKeywordDataVector()
     * @throws            SQLException
     */
    public static ItemKeywordDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ItemKeywordData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ITEM_KEYWORD.ITEM_KEYWORD_ID,CLW_ITEM_KEYWORD.ITEM_ID,CLW_ITEM_KEYWORD.KEYWORD,CLW_ITEM_KEYWORD.ADD_DATE,CLW_ITEM_KEYWORD.ADD_BY,CLW_ITEM_KEYWORD.MOD_DATE,CLW_ITEM_KEYWORD.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ItemKeywordData Object.
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
    *@returns a populated ItemKeywordData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ItemKeywordData x = ItemKeywordData.createValue();
         
         x.setItemKeywordId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setKeyword(rs.getString(3+offset));
         x.setAddDate(rs.getTimestamp(4+offset));
         x.setAddBy(rs.getString(5+offset));
         x.setModDate(rs.getTimestamp(6+offset));
         x.setModBy(rs.getString(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ItemKeywordData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a ItemKeywordDataVector object that consists
     * of ItemKeywordData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ItemKeywordDataVector()
     * @throws            SQLException
     */
    public static ItemKeywordDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ITEM_KEYWORD_ID,ITEM_ID,KEYWORD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_KEYWORD");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ITEM_KEYWORD.ITEM_KEYWORD_ID,CLW_ITEM_KEYWORD.ITEM_ID,CLW_ITEM_KEYWORD.KEYWORD,CLW_ITEM_KEYWORD.ADD_DATE,CLW_ITEM_KEYWORD.ADD_BY,CLW_ITEM_KEYWORD.MOD_DATE,CLW_ITEM_KEYWORD.MOD_BY FROM CLW_ITEM_KEYWORD");
                where = pCriteria.getSqlClause("CLW_ITEM_KEYWORD");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ITEM_KEYWORD.equals(otherTable)){
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
        ItemKeywordDataVector v = new ItemKeywordDataVector();
        while (rs.next()) {
            ItemKeywordData x = ItemKeywordData.createValue();
            
            x.setItemKeywordId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setKeyword(rs.getString(3));
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
     * Gets a ItemKeywordDataVector object that consists
     * of ItemKeywordData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ItemKeywordData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ItemKeywordDataVector()
     * @throws            SQLException
     */
    public static ItemKeywordDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ItemKeywordDataVector v = new ItemKeywordDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_KEYWORD_ID,ITEM_ID,KEYWORD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_KEYWORD WHERE ITEM_KEYWORD_ID IN (");

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
            ItemKeywordData x=null;
            while (rs.next()) {
                // build the object
                x=ItemKeywordData.createValue();
                
                x.setItemKeywordId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setKeyword(rs.getString(3));
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
     * Gets a ItemKeywordDataVector object of all
     * ItemKeywordData objects in the database.
     * @param pCon An open database connection.
     * @return new ItemKeywordDataVector()
     * @throws            SQLException
     */
    public static ItemKeywordDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ITEM_KEYWORD_ID,ITEM_ID,KEYWORD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ITEM_KEYWORD";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ItemKeywordDataVector v = new ItemKeywordDataVector();
        ItemKeywordData x = null;
        while (rs.next()) {
            // build the object
            x = ItemKeywordData.createValue();
            
            x.setItemKeywordId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setKeyword(rs.getString(3));
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
     * ItemKeywordData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ITEM_KEYWORD_ID FROM CLW_ITEM_KEYWORD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_KEYWORD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ITEM_KEYWORD");
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
     * Inserts a ItemKeywordData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemKeywordData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ItemKeywordData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemKeywordData insert(Connection pCon, ItemKeywordData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ITEM_KEYWORD_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ITEM_KEYWORD_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setItemKeywordId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ITEM_KEYWORD (ITEM_KEYWORD_ID,ITEM_ID,KEYWORD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getItemKeywordId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getItemId());
        }

        pstmt.setString(3,pData.getKeyword());
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_KEYWORD_ID="+pData.getItemKeywordId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   KEYWORD="+pData.getKeyword());
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
        pData.setItemKeywordId(0);
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
     * Updates a ItemKeywordData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemKeywordData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemKeywordData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ITEM_KEYWORD SET ITEM_ID = ?,KEYWORD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ITEM_KEYWORD_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getItemId());
        }

        pstmt.setString(i++,pData.getKeyword());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getItemKeywordId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   KEYWORD="+pData.getKeyword());
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
     * Deletes a ItemKeywordData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemKeywordId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemKeywordId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ITEM_KEYWORD WHERE ITEM_KEYWORD_ID = " + pItemKeywordId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ItemKeywordData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ITEM_KEYWORD");
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
     * Inserts a ItemKeywordData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemKeywordData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ItemKeywordData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ITEM_KEYWORD (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ITEM_KEYWORD_ID,ITEM_ID,KEYWORD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getItemKeywordId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getItemId());
        }

        pstmt.setString(3+4,pData.getKeyword());
        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(5+4,pData.getAddBy());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(7+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ItemKeywordData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemKeywordData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ItemKeywordData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ItemKeywordData insert(Connection pCon, ItemKeywordData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ItemKeywordData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ItemKeywordData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ItemKeywordData pData, boolean pLogFl)
        throws SQLException {
        ItemKeywordData oldData = null;
        if(pLogFl) {
          int id = pData.getItemKeywordId();
          try {
          oldData = ItemKeywordDataAccess.select(pCon,id);
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
     * Deletes a ItemKeywordData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pItemKeywordId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pItemKeywordId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ITEM_KEYWORD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_KEYWORD d WHERE ITEM_KEYWORD_ID = " + pItemKeywordId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pItemKeywordId);
        return n;
     }

    /**
     * Deletes ItemKeywordData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ITEM_KEYWORD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ITEM_KEYWORD d ");
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

