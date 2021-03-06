
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        LanguageDataAccess
 * Description:  This class is used to build access methods to the CLW_LANGUAGE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.LanguageData;
import com.cleanwise.service.api.value.LanguageDataVector;
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
 * <code>LanguageDataAccess</code>
 */
public class LanguageDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(LanguageDataAccess.class.getName());

    /** <code>CLW_LANGUAGE</code> table name */
	/* Primary key: LANGUAGE_ID */
	
    public static final String CLW_LANGUAGE = "CLW_LANGUAGE";
    
    /** <code>LANGUAGE_ID</code> LANGUAGE_ID column of table CLW_LANGUAGE */
    public static final String LANGUAGE_ID = "LANGUAGE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_LANGUAGE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>UI_NAME</code> UI_NAME column of table CLW_LANGUAGE */
    public static final String UI_NAME = "UI_NAME";
    /** <code>LANGUAGE_CODE</code> LANGUAGE_CODE column of table CLW_LANGUAGE */
    public static final String LANGUAGE_CODE = "LANGUAGE_CODE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_LANGUAGE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_LANGUAGE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_LANGUAGE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_LANGUAGE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>SUPPORTED</code> SUPPORTED column of table CLW_LANGUAGE */
    public static final String SUPPORTED = "SUPPORTED";
    /** <code>TRANSLATED_NAME</code> TRANSLATED_NAME column of table CLW_LANGUAGE */
    public static final String TRANSLATED_NAME = "TRANSLATED_NAME";

    /**
     * Constructor.
     */
    public LanguageDataAccess()
    {
    }

    /**
     * Gets a LanguageData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pLanguageId The key requested.
     * @return new LanguageData()
     * @throws            SQLException
     */
    public static LanguageData select(Connection pCon, int pLanguageId)
        throws SQLException, DataNotFoundException {
        LanguageData x=null;
        String sql="SELECT LANGUAGE_ID,SHORT_DESC,UI_NAME,LANGUAGE_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SUPPORTED,TRANSLATED_NAME FROM CLW_LANGUAGE WHERE LANGUAGE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pLanguageId=" + pLanguageId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pLanguageId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=LanguageData.createValue();
            
            x.setLanguageId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setUiName(rs.getString(3));
            x.setLanguageCode(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setSupported(rs.getString(9));
            x.setTranslatedName(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("LANGUAGE_ID :" + pLanguageId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a LanguageDataVector object that consists
     * of LanguageData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new LanguageDataVector()
     * @throws            SQLException
     */
    public static LanguageDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a LanguageData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_LANGUAGE.LANGUAGE_ID,CLW_LANGUAGE.SHORT_DESC,CLW_LANGUAGE.UI_NAME,CLW_LANGUAGE.LANGUAGE_CODE,CLW_LANGUAGE.ADD_DATE,CLW_LANGUAGE.ADD_BY,CLW_LANGUAGE.MOD_DATE,CLW_LANGUAGE.MOD_BY,CLW_LANGUAGE.SUPPORTED,CLW_LANGUAGE.TRANSLATED_NAME";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated LanguageData Object.
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
    *@returns a populated LanguageData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         LanguageData x = LanguageData.createValue();
         
         x.setLanguageId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setUiName(rs.getString(3+offset));
         x.setLanguageCode(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setSupported(rs.getString(9+offset));
         x.setTranslatedName(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the LanguageData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a LanguageDataVector object that consists
     * of LanguageData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new LanguageDataVector()
     * @throws            SQLException
     */
    public static LanguageDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT LANGUAGE_ID,SHORT_DESC,UI_NAME,LANGUAGE_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SUPPORTED,TRANSLATED_NAME FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_LANGUAGE.LANGUAGE_ID,CLW_LANGUAGE.SHORT_DESC,CLW_LANGUAGE.UI_NAME,CLW_LANGUAGE.LANGUAGE_CODE,CLW_LANGUAGE.ADD_DATE,CLW_LANGUAGE.ADD_BY,CLW_LANGUAGE.MOD_DATE,CLW_LANGUAGE.MOD_BY,CLW_LANGUAGE.SUPPORTED,CLW_LANGUAGE.TRANSLATED_NAME FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause("CLW_LANGUAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_LANGUAGE.equals(otherTable)){
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
        LanguageDataVector v = new LanguageDataVector();
        while (rs.next()) {
            LanguageData x = LanguageData.createValue();
            
            x.setLanguageId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setUiName(rs.getString(3));
            x.setLanguageCode(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setSupported(rs.getString(9));
            x.setTranslatedName(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a LanguageDataVector object that consists
     * of LanguageData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for LanguageData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new LanguageDataVector()
     * @throws            SQLException
     */
    public static LanguageDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        LanguageDataVector v = new LanguageDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT LANGUAGE_ID,SHORT_DESC,UI_NAME,LANGUAGE_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SUPPORTED,TRANSLATED_NAME FROM CLW_LANGUAGE WHERE LANGUAGE_ID IN (");

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
            LanguageData x=null;
            while (rs.next()) {
                // build the object
                x=LanguageData.createValue();
                
                x.setLanguageId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setUiName(rs.getString(3));
                x.setLanguageCode(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setSupported(rs.getString(9));
                x.setTranslatedName(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a LanguageDataVector object of all
     * LanguageData objects in the database.
     * @param pCon An open database connection.
     * @return new LanguageDataVector()
     * @throws            SQLException
     */
    public static LanguageDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT LANGUAGE_ID,SHORT_DESC,UI_NAME,LANGUAGE_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SUPPORTED,TRANSLATED_NAME FROM CLW_LANGUAGE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        LanguageDataVector v = new LanguageDataVector();
        LanguageData x = null;
        while (rs.next()) {
            // build the object
            x = LanguageData.createValue();
            
            x.setLanguageId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setUiName(rs.getString(3));
            x.setLanguageCode(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setSupported(rs.getString(9));
            x.setTranslatedName(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * LanguageData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT LANGUAGE_ID FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT LANGUAGE_ID FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause("CLW_LANGUAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_LANGUAGE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause("CLW_LANGUAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_LANGUAGE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LANGUAGE");
                where = pCriteria.getSqlClause("CLW_LANGUAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_LANGUAGE.equals(otherTable)){
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
     * Inserts a LanguageData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LanguageData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new LanguageData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static LanguageData insert(Connection pCon, LanguageData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_LANGUAGE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_LANGUAGE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setLanguageId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_LANGUAGE (LANGUAGE_ID,SHORT_DESC,UI_NAME,LANGUAGE_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SUPPORTED,TRANSLATED_NAME) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getLanguageId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getUiName());
        pstmt.setString(4,pData.getLanguageCode());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setString(9,pData.getSupported());
        pstmt.setString(10,pData.getTranslatedName());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   LANGUAGE_ID="+pData.getLanguageId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   UI_NAME="+pData.getUiName());
            log.debug("SQL:   LANGUAGE_CODE="+pData.getLanguageCode());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SUPPORTED="+pData.getSupported());
            log.debug("SQL:   TRANSLATED_NAME="+pData.getTranslatedName());
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
        pData.setLanguageId(0);
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
     * Updates a LanguageData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A LanguageData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, LanguageData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_LANGUAGE SET SHORT_DESC = ?,UI_NAME = ?,LANGUAGE_CODE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,SUPPORTED = ?,TRANSLATED_NAME = ? WHERE LANGUAGE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getUiName());
        pstmt.setString(i++,pData.getLanguageCode());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getSupported());
        pstmt.setString(i++,pData.getTranslatedName());
        pstmt.setInt(i++,pData.getLanguageId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   UI_NAME="+pData.getUiName());
            log.debug("SQL:   LANGUAGE_CODE="+pData.getLanguageCode());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SUPPORTED="+pData.getSupported());
            log.debug("SQL:   TRANSLATED_NAME="+pData.getTranslatedName());
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
     * Deletes a LanguageData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pLanguageId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pLanguageId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_LANGUAGE WHERE LANGUAGE_ID = " + pLanguageId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes LanguageData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_LANGUAGE");
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
     * Inserts a LanguageData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LanguageData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, LanguageData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_LANGUAGE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "LANGUAGE_ID,SHORT_DESC,UI_NAME,LANGUAGE_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SUPPORTED,TRANSLATED_NAME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getLanguageId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getUiName());
        pstmt.setString(4+4,pData.getLanguageCode());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setString(9+4,pData.getSupported());
        pstmt.setString(10+4,pData.getTranslatedName());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a LanguageData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LanguageData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new LanguageData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static LanguageData insert(Connection pCon, LanguageData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a LanguageData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A LanguageData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, LanguageData pData, boolean pLogFl)
        throws SQLException {
        LanguageData oldData = null;
        if(pLogFl) {
          int id = pData.getLanguageId();
          try {
          oldData = LanguageDataAccess.select(pCon,id);
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
     * Deletes a LanguageData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pLanguageId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pLanguageId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_LANGUAGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_LANGUAGE d WHERE LANGUAGE_ID = " + pLanguageId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pLanguageId);
        return n;
     }

    /**
     * Deletes LanguageData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_LANGUAGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_LANGUAGE d ");
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

