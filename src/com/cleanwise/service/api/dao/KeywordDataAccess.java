
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        KeywordDataAccess
 * Description:  This class is used to build access methods to the CLW_KEYWORD table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.KeywordData;
import com.cleanwise.service.api.value.KeywordDataVector;
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
 * <code>KeywordDataAccess</code>
 */
public class KeywordDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(KeywordDataAccess.class.getName());

    /** <code>CLW_KEYWORD</code> table name */
	/* Primary key: KEYWORD_ID */
	
    public static final String CLW_KEYWORD = "CLW_KEYWORD";
    
    /** <code>KEYWORD_ID</code> KEYWORD_ID column of table CLW_KEYWORD */
    public static final String KEYWORD_ID = "KEYWORD_ID";
    /** <code>KEYWORD</code> KEYWORD column of table CLW_KEYWORD */
    public static final String KEYWORD = "KEYWORD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_KEYWORD */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>GENERATED_IND</code> GENERATED_IND column of table CLW_KEYWORD */
    public static final String GENERATED_IND = "GENERATED_IND";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_KEYWORD */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_KEYWORD */
    public static final String MOD_BY = "MOD_BY";
    /** <code>LOCALE</code> LOCALE column of table CLW_KEYWORD */
    public static final String LOCALE = "LOCALE";

    /**
     * Constructor.
     */
    public KeywordDataAccess()
    {
    }

    /**
     * Gets a KeywordData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pKeywordId The key requested.
     * @return new KeywordData()
     * @throws            SQLException
     */
    public static KeywordData select(Connection pCon, int pKeywordId)
        throws SQLException, DataNotFoundException {
        KeywordData x=null;
        String sql="SELECT KEYWORD_ID,KEYWORD,CLW_VALUE,GENERATED_IND,MOD_DATE,MOD_BY,LOCALE FROM CLW_KEYWORD WHERE KEYWORD_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pKeywordId=" + pKeywordId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pKeywordId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=KeywordData.createValue();
            
            x.setKeywordId(rs.getInt(1));
            x.setKeyword(rs.getString(2));
            x.setValue(rs.getString(3));
            x.setGeneratedInd(rs.getBoolean(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setLocale(rs.getString(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("KEYWORD_ID :" + pKeywordId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a KeywordDataVector object that consists
     * of KeywordData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new KeywordDataVector()
     * @throws            SQLException
     */
    public static KeywordDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a KeywordData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_KEYWORD.KEYWORD_ID,CLW_KEYWORD.KEYWORD,CLW_KEYWORD.CLW_VALUE,CLW_KEYWORD.GENERATED_IND,CLW_KEYWORD.MOD_DATE,CLW_KEYWORD.MOD_BY,CLW_KEYWORD.LOCALE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated KeywordData Object.
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
    *@returns a populated KeywordData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         KeywordData x = KeywordData.createValue();
         
         x.setKeywordId(rs.getInt(1+offset));
         x.setKeyword(rs.getString(2+offset));
         x.setValue(rs.getString(3+offset));
         x.setGeneratedInd(rs.getBoolean(4+offset));
         x.setModDate(rs.getTimestamp(5+offset));
         x.setModBy(rs.getString(6+offset));
         x.setLocale(rs.getString(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the KeywordData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a KeywordDataVector object that consists
     * of KeywordData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new KeywordDataVector()
     * @throws            SQLException
     */
    public static KeywordDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT KEYWORD_ID,KEYWORD,CLW_VALUE,GENERATED_IND,MOD_DATE,MOD_BY,LOCALE FROM CLW_KEYWORD");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_KEYWORD.KEYWORD_ID,CLW_KEYWORD.KEYWORD,CLW_KEYWORD.CLW_VALUE,CLW_KEYWORD.GENERATED_IND,CLW_KEYWORD.MOD_DATE,CLW_KEYWORD.MOD_BY,CLW_KEYWORD.LOCALE FROM CLW_KEYWORD");
                where = pCriteria.getSqlClause("CLW_KEYWORD");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_KEYWORD.equals(otherTable)){
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
        KeywordDataVector v = new KeywordDataVector();
        while (rs.next()) {
            KeywordData x = KeywordData.createValue();
            
            x.setKeywordId(rs.getInt(1));
            x.setKeyword(rs.getString(2));
            x.setValue(rs.getString(3));
            x.setGeneratedInd(rs.getBoolean(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setLocale(rs.getString(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a KeywordDataVector object that consists
     * of KeywordData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for KeywordData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new KeywordDataVector()
     * @throws            SQLException
     */
    public static KeywordDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        KeywordDataVector v = new KeywordDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT KEYWORD_ID,KEYWORD,CLW_VALUE,GENERATED_IND,MOD_DATE,MOD_BY,LOCALE FROM CLW_KEYWORD WHERE KEYWORD_ID IN (");

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
            KeywordData x=null;
            while (rs.next()) {
                // build the object
                x=KeywordData.createValue();
                
                x.setKeywordId(rs.getInt(1));
                x.setKeyword(rs.getString(2));
                x.setValue(rs.getString(3));
                x.setGeneratedInd(rs.getBoolean(4));
                x.setModDate(rs.getTimestamp(5));
                x.setModBy(rs.getString(6));
                x.setLocale(rs.getString(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a KeywordDataVector object of all
     * KeywordData objects in the database.
     * @param pCon An open database connection.
     * @return new KeywordDataVector()
     * @throws            SQLException
     */
    public static KeywordDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT KEYWORD_ID,KEYWORD,CLW_VALUE,GENERATED_IND,MOD_DATE,MOD_BY,LOCALE FROM CLW_KEYWORD";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        KeywordDataVector v = new KeywordDataVector();
        KeywordData x = null;
        while (rs.next()) {
            // build the object
            x = KeywordData.createValue();
            
            x.setKeywordId(rs.getInt(1));
            x.setKeyword(rs.getString(2));
            x.setValue(rs.getString(3));
            x.setGeneratedInd(rs.getBoolean(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setLocale(rs.getString(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * KeywordData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT KEYWORD_ID FROM CLW_KEYWORD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KEYWORD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KEYWORD");
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
     * Inserts a KeywordData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KeywordData object to insert.
     * @return new KeywordData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KeywordData insert(Connection pCon, KeywordData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_KEYWORD_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_KEYWORD_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setKeywordId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_KEYWORD (KEYWORD_ID,KEYWORD,CLW_VALUE,GENERATED_IND,MOD_DATE,MOD_BY,LOCALE) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getKeywordId());
        pstmt.setString(2,pData.getKeyword());
        pstmt.setString(3,pData.getValue());
        pstmt.setInt(4, pData.getGeneratedInd()?1:0);
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(6,pData.getModBy());
        pstmt.setString(7,pData.getLocale());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KEYWORD_ID="+pData.getKeywordId());
            log.debug("SQL:   KEYWORD="+pData.getKeyword());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   GENERATED_IND="+pData.getGeneratedInd());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE="+pData.getLocale());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setKeywordId(0);
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
     * Updates a KeywordData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KeywordData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KeywordData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_KEYWORD SET KEYWORD = ?,CLW_VALUE = ?,GENERATED_IND = ?,MOD_DATE = ?,MOD_BY = ?,LOCALE = ? WHERE KEYWORD_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getKeyword());
        pstmt.setString(i++,pData.getValue());
        pstmt.setInt(i++, pData.getGeneratedInd()?1:0);
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getLocale());
        pstmt.setInt(i++,pData.getKeywordId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KEYWORD="+pData.getKeyword());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   GENERATED_IND="+pData.getGeneratedInd());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE="+pData.getLocale());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a KeywordData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKeywordId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKeywordId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_KEYWORD WHERE KEYWORD_ID = " + pKeywordId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes KeywordData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_KEYWORD");
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
     * Inserts a KeywordData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KeywordData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, KeywordData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_KEYWORD (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "KEYWORD_ID,KEYWORD,CLW_VALUE,GENERATED_IND,MOD_DATE,MOD_BY,LOCALE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getKeywordId());
        pstmt.setString(2+4,pData.getKeyword());
        pstmt.setString(3+4,pData.getValue());
        pstmt.setBoolean(4+4,pData.getGeneratedInd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(6+4,pData.getModBy());
        pstmt.setString(7+4,pData.getLocale());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a KeywordData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KeywordData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new KeywordData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KeywordData insert(Connection pCon, KeywordData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a KeywordData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KeywordData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KeywordData pData, boolean pLogFl)
        throws SQLException {
        KeywordData oldData = null;
        if(pLogFl) {
          int id = pData.getKeywordId();
          try {
          oldData = KeywordDataAccess.select(pCon,id);
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
     * Deletes a KeywordData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKeywordId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKeywordId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_KEYWORD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KEYWORD d WHERE KEYWORD_ID = " + pKeywordId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pKeywordId);
        return n;
     }

    /**
     * Deletes KeywordData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_KEYWORD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KEYWORD d ");
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

