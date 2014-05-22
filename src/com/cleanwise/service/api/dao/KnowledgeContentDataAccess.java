
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        KnowledgeContentDataAccess
 * Description:  This class is used to build access methods to the CLW_KNOWLEDGE_CONTENT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.KnowledgeContentData;
import com.cleanwise.service.api.value.KnowledgeContentDataVector;
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
 * <code>KnowledgeContentDataAccess</code>
 */
public class KnowledgeContentDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(KnowledgeContentDataAccess.class.getName());

    /** <code>CLW_KNOWLEDGE_CONTENT</code> table name */
	/* Primary key: KNOWLEDGE_CONTENT_ID */
	
    public static final String CLW_KNOWLEDGE_CONTENT = "CLW_KNOWLEDGE_CONTENT";
    
    /** <code>KNOWLEDGE_CONTENT_ID</code> KNOWLEDGE_CONTENT_ID column of table CLW_KNOWLEDGE_CONTENT */
    public static final String KNOWLEDGE_CONTENT_ID = "KNOWLEDGE_CONTENT_ID";
    /** <code>KNOWLEDGE_ID</code> KNOWLEDGE_ID column of table CLW_KNOWLEDGE_CONTENT */
    public static final String KNOWLEDGE_ID = "KNOWLEDGE_ID";
    /** <code>CONTENT_URL</code> CONTENT_URL column of table CLW_KNOWLEDGE_CONTENT */
    public static final String CONTENT_URL = "CONTENT_URL";
    /** <code>CONTENT_FORMAT</code> CONTENT_FORMAT column of table CLW_KNOWLEDGE_CONTENT */
    public static final String CONTENT_FORMAT = "CONTENT_FORMAT";
    /** <code>CONTENT_SOURCE</code> CONTENT_SOURCE column of table CLW_KNOWLEDGE_CONTENT */
    public static final String CONTENT_SOURCE = "CONTENT_SOURCE";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_KNOWLEDGE_CONTENT */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_KNOWLEDGE_CONTENT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_TIME</code> ADD_TIME column of table CLW_KNOWLEDGE_CONTENT */
    public static final String ADD_TIME = "ADD_TIME";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_KNOWLEDGE_CONTENT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_KNOWLEDGE_CONTENT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_KNOWLEDGE_CONTENT */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public KnowledgeContentDataAccess()
    {
    }

    /**
     * Gets a KnowledgeContentData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pKnowledgeContentId The key requested.
     * @return new KnowledgeContentData()
     * @throws            SQLException
     */
    public static KnowledgeContentData select(Connection pCon, int pKnowledgeContentId)
        throws SQLException, DataNotFoundException {
        KnowledgeContentData x=null;
        String sql="SELECT KNOWLEDGE_CONTENT_ID,KNOWLEDGE_ID,CONTENT_URL,CONTENT_FORMAT,CONTENT_SOURCE,LONG_DESC,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_CONTENT WHERE KNOWLEDGE_CONTENT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pKnowledgeContentId=" + pKnowledgeContentId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pKnowledgeContentId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=KnowledgeContentData.createValue();
            
            x.setKnowledgeContentId(rs.getInt(1));
            x.setKnowledgeId(rs.getInt(2));
            x.setContentUrl(rs.getString(3));
            x.setContentFormat(rs.getString(4));
            x.setContentSource(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddTime(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("KNOWLEDGE_CONTENT_ID :" + pKnowledgeContentId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a KnowledgeContentDataVector object that consists
     * of KnowledgeContentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new KnowledgeContentDataVector()
     * @throws            SQLException
     */
    public static KnowledgeContentDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a KnowledgeContentData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_KNOWLEDGE_CONTENT.KNOWLEDGE_CONTENT_ID,CLW_KNOWLEDGE_CONTENT.KNOWLEDGE_ID,CLW_KNOWLEDGE_CONTENT.CONTENT_URL,CLW_KNOWLEDGE_CONTENT.CONTENT_FORMAT,CLW_KNOWLEDGE_CONTENT.CONTENT_SOURCE,CLW_KNOWLEDGE_CONTENT.LONG_DESC,CLW_KNOWLEDGE_CONTENT.ADD_DATE,CLW_KNOWLEDGE_CONTENT.ADD_TIME,CLW_KNOWLEDGE_CONTENT.ADD_BY,CLW_KNOWLEDGE_CONTENT.MOD_DATE,CLW_KNOWLEDGE_CONTENT.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated KnowledgeContentData Object.
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
    *@returns a populated KnowledgeContentData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         KnowledgeContentData x = KnowledgeContentData.createValue();
         
         x.setKnowledgeContentId(rs.getInt(1+offset));
         x.setKnowledgeId(rs.getInt(2+offset));
         x.setContentUrl(rs.getString(3+offset));
         x.setContentFormat(rs.getString(4+offset));
         x.setContentSource(rs.getString(5+offset));
         x.setLongDesc(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddTime(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the KnowledgeContentData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a KnowledgeContentDataVector object that consists
     * of KnowledgeContentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new KnowledgeContentDataVector()
     * @throws            SQLException
     */
    public static KnowledgeContentDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT KNOWLEDGE_CONTENT_ID,KNOWLEDGE_ID,CONTENT_URL,CONTENT_FORMAT,CONTENT_SOURCE,LONG_DESC,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_CONTENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_KNOWLEDGE_CONTENT.KNOWLEDGE_CONTENT_ID,CLW_KNOWLEDGE_CONTENT.KNOWLEDGE_ID,CLW_KNOWLEDGE_CONTENT.CONTENT_URL,CLW_KNOWLEDGE_CONTENT.CONTENT_FORMAT,CLW_KNOWLEDGE_CONTENT.CONTENT_SOURCE,CLW_KNOWLEDGE_CONTENT.LONG_DESC,CLW_KNOWLEDGE_CONTENT.ADD_DATE,CLW_KNOWLEDGE_CONTENT.ADD_TIME,CLW_KNOWLEDGE_CONTENT.ADD_BY,CLW_KNOWLEDGE_CONTENT.MOD_DATE,CLW_KNOWLEDGE_CONTENT.MOD_BY FROM CLW_KNOWLEDGE_CONTENT");
                where = pCriteria.getSqlClause("CLW_KNOWLEDGE_CONTENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_KNOWLEDGE_CONTENT.equals(otherTable)){
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
        KnowledgeContentDataVector v = new KnowledgeContentDataVector();
        while (rs.next()) {
            KnowledgeContentData x = KnowledgeContentData.createValue();
            
            x.setKnowledgeContentId(rs.getInt(1));
            x.setKnowledgeId(rs.getInt(2));
            x.setContentUrl(rs.getString(3));
            x.setContentFormat(rs.getString(4));
            x.setContentSource(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddTime(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a KnowledgeContentDataVector object that consists
     * of KnowledgeContentData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for KnowledgeContentData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new KnowledgeContentDataVector()
     * @throws            SQLException
     */
    public static KnowledgeContentDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        KnowledgeContentDataVector v = new KnowledgeContentDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT KNOWLEDGE_CONTENT_ID,KNOWLEDGE_ID,CONTENT_URL,CONTENT_FORMAT,CONTENT_SOURCE,LONG_DESC,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_CONTENT WHERE KNOWLEDGE_CONTENT_ID IN (");

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
            KnowledgeContentData x=null;
            while (rs.next()) {
                // build the object
                x=KnowledgeContentData.createValue();
                
                x.setKnowledgeContentId(rs.getInt(1));
                x.setKnowledgeId(rs.getInt(2));
                x.setContentUrl(rs.getString(3));
                x.setContentFormat(rs.getString(4));
                x.setContentSource(rs.getString(5));
                x.setLongDesc(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddTime(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a KnowledgeContentDataVector object of all
     * KnowledgeContentData objects in the database.
     * @param pCon An open database connection.
     * @return new KnowledgeContentDataVector()
     * @throws            SQLException
     */
    public static KnowledgeContentDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT KNOWLEDGE_CONTENT_ID,KNOWLEDGE_ID,CONTENT_URL,CONTENT_FORMAT,CONTENT_SOURCE,LONG_DESC,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY FROM CLW_KNOWLEDGE_CONTENT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        KnowledgeContentDataVector v = new KnowledgeContentDataVector();
        KnowledgeContentData x = null;
        while (rs.next()) {
            // build the object
            x = KnowledgeContentData.createValue();
            
            x.setKnowledgeContentId(rs.getInt(1));
            x.setKnowledgeId(rs.getInt(2));
            x.setContentUrl(rs.getString(3));
            x.setContentFormat(rs.getString(4));
            x.setContentSource(rs.getString(5));
            x.setLongDesc(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddTime(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * KnowledgeContentData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT KNOWLEDGE_CONTENT_ID FROM CLW_KNOWLEDGE_CONTENT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KNOWLEDGE_CONTENT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_KNOWLEDGE_CONTENT");
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
     * Inserts a KnowledgeContentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeContentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new KnowledgeContentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KnowledgeContentData insert(Connection pCon, KnowledgeContentData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_KNOWLEDGE_CONTENT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_KNOWLEDGE_CONTENT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setKnowledgeContentId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_KNOWLEDGE_CONTENT (KNOWLEDGE_CONTENT_ID,KNOWLEDGE_ID,CONTENT_URL,CONTENT_FORMAT,CONTENT_SOURCE,LONG_DESC,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getKnowledgeContentId());
        pstmt.setInt(2,pData.getKnowledgeId());
        pstmt.setString(3,pData.getContentUrl());
        pstmt.setString(4,pData.getContentFormat());
        pstmt.setString(5,pData.getContentSource());
        pstmt.setString(6,pData.getLongDesc());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KNOWLEDGE_CONTENT_ID="+pData.getKnowledgeContentId());
            log.debug("SQL:   KNOWLEDGE_ID="+pData.getKnowledgeId());
            log.debug("SQL:   CONTENT_URL="+pData.getContentUrl());
            log.debug("SQL:   CONTENT_FORMAT="+pData.getContentFormat());
            log.debug("SQL:   CONTENT_SOURCE="+pData.getContentSource());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
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
        pData.setKnowledgeContentId(0);
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
     * Updates a KnowledgeContentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeContentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KnowledgeContentData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_KNOWLEDGE_CONTENT SET KNOWLEDGE_ID = ?,CONTENT_URL = ?,CONTENT_FORMAT = ?,CONTENT_SOURCE = ?,LONG_DESC = ?,ADD_DATE = ?,ADD_TIME = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE KNOWLEDGE_CONTENT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getKnowledgeId());
        pstmt.setString(i++,pData.getContentUrl());
        pstmt.setString(i++,pData.getContentFormat());
        pstmt.setString(i++,pData.getContentSource());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getKnowledgeContentId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   KNOWLEDGE_ID="+pData.getKnowledgeId());
            log.debug("SQL:   CONTENT_URL="+pData.getContentUrl());
            log.debug("SQL:   CONTENT_FORMAT="+pData.getContentFormat());
            log.debug("SQL:   CONTENT_SOURCE="+pData.getContentSource());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_TIME="+pData.getAddTime());
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
     * Deletes a KnowledgeContentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKnowledgeContentId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKnowledgeContentId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_KNOWLEDGE_CONTENT WHERE KNOWLEDGE_CONTENT_ID = " + pKnowledgeContentId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes KnowledgeContentData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_KNOWLEDGE_CONTENT");
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
     * Inserts a KnowledgeContentData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeContentData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, KnowledgeContentData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_KNOWLEDGE_CONTENT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "KNOWLEDGE_CONTENT_ID,KNOWLEDGE_ID,CONTENT_URL,CONTENT_FORMAT,CONTENT_SOURCE,LONG_DESC,ADD_DATE,ADD_TIME,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getKnowledgeContentId());
        pstmt.setInt(2+4,pData.getKnowledgeId());
        pstmt.setString(3+4,pData.getContentUrl());
        pstmt.setString(4+4,pData.getContentFormat());
        pstmt.setString(5+4,pData.getContentSource());
        pstmt.setString(6+4,pData.getLongDesc());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddTime()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a KnowledgeContentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeContentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new KnowledgeContentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static KnowledgeContentData insert(Connection pCon, KnowledgeContentData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a KnowledgeContentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A KnowledgeContentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, KnowledgeContentData pData, boolean pLogFl)
        throws SQLException {
        KnowledgeContentData oldData = null;
        if(pLogFl) {
          int id = pData.getKnowledgeContentId();
          try {
          oldData = KnowledgeContentDataAccess.select(pCon,id);
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
     * Deletes a KnowledgeContentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pKnowledgeContentId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pKnowledgeContentId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_KNOWLEDGE_CONTENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KNOWLEDGE_CONTENT d WHERE KNOWLEDGE_CONTENT_ID = " + pKnowledgeContentId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pKnowledgeContentId);
        return n;
     }

    /**
     * Deletes KnowledgeContentData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_KNOWLEDGE_CONTENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_KNOWLEDGE_CONTENT d ");
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

