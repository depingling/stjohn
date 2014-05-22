
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        StoreMessageDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_STORE_MESSAGE_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.StoreMessageDetailData;
import com.cleanwise.service.api.value.StoreMessageDetailDataVector;
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
 * <code>StoreMessageDetailDataAccess</code>
 */
public class StoreMessageDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(StoreMessageDetailDataAccess.class.getName());

    /** <code>CLW_STORE_MESSAGE_DETAIL</code> table name */
	/* Primary key: STORE_MESSAGE_DETAIL_ID */
	
    public static final String CLW_STORE_MESSAGE_DETAIL = "CLW_STORE_MESSAGE_DETAIL";
    
    /** <code>STORE_MESSAGE_DETAIL_ID</code> STORE_MESSAGE_DETAIL_ID column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String STORE_MESSAGE_DETAIL_ID = "STORE_MESSAGE_DETAIL_ID";
    /** <code>STORE_MESSAGE_ID</code> STORE_MESSAGE_ID column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String STORE_MESSAGE_ID = "STORE_MESSAGE_ID";
    /** <code>MESSAGE_DETAIL_TYPE_CD</code> MESSAGE_DETAIL_TYPE_CD column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MESSAGE_DETAIL_TYPE_CD = "MESSAGE_DETAIL_TYPE_CD";
    /** <code>MESSAGE_TITLE</code> MESSAGE_TITLE column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MESSAGE_TITLE = "MESSAGE_TITLE";
    /** <code>LANGUAGE_CD</code> LANGUAGE_CD column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String LANGUAGE_CD = "LANGUAGE_CD";
    /** <code>COUNTRY</code> COUNTRY column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String COUNTRY = "COUNTRY";
    /** <code>MESSAGE_AUTHOR</code> MESSAGE_AUTHOR column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MESSAGE_AUTHOR = "MESSAGE_AUTHOR";
    /** <code>MESSAGE_ABSTRACT</code> MESSAGE_ABSTRACT column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MESSAGE_ABSTRACT = "MESSAGE_ABSTRACT";
    /** <code>MESSAGE_BODY</code> MESSAGE_BODY column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MESSAGE_BODY = "MESSAGE_BODY";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_STORE_MESSAGE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public StoreMessageDetailDataAccess()
    {
    }

    /**
     * Gets a StoreMessageDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pStoreMessageDetailId The key requested.
     * @return new StoreMessageDetailData()
     * @throws            SQLException
     */
    public static StoreMessageDetailData select(Connection pCon, int pStoreMessageDetailId)
        throws SQLException, DataNotFoundException {
        StoreMessageDetailData x=null;
        String sql="SELECT STORE_MESSAGE_DETAIL_ID,STORE_MESSAGE_ID,MESSAGE_DETAIL_TYPE_CD,MESSAGE_TITLE,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE_DETAIL WHERE STORE_MESSAGE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pStoreMessageDetailId=" + pStoreMessageDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pStoreMessageDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=StoreMessageDetailData.createValue();
            
            x.setStoreMessageDetailId(rs.getInt(1));
            x.setStoreMessageId(rs.getInt(2));
            x.setMessageDetailTypeCd(rs.getString(3));
            x.setMessageTitle(rs.getString(4));
            x.setLanguageCd(rs.getString(5));
            x.setCountry(rs.getString(6));
            x.setMessageAuthor(rs.getString(7));
            x.setMessageAbstract(rs.getString(8));
            x.setMessageBody(rs.getString(9));
            x.setAddBy(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("STORE_MESSAGE_DETAIL_ID :" + pStoreMessageDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a StoreMessageDetailDataVector object that consists
     * of StoreMessageDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new StoreMessageDetailDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a StoreMessageDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_STORE_MESSAGE_DETAIL.STORE_MESSAGE_DETAIL_ID,CLW_STORE_MESSAGE_DETAIL.STORE_MESSAGE_ID,CLW_STORE_MESSAGE_DETAIL.MESSAGE_DETAIL_TYPE_CD,CLW_STORE_MESSAGE_DETAIL.MESSAGE_TITLE,CLW_STORE_MESSAGE_DETAIL.LANGUAGE_CD,CLW_STORE_MESSAGE_DETAIL.COUNTRY,CLW_STORE_MESSAGE_DETAIL.MESSAGE_AUTHOR,CLW_STORE_MESSAGE_DETAIL.MESSAGE_ABSTRACT,CLW_STORE_MESSAGE_DETAIL.MESSAGE_BODY,CLW_STORE_MESSAGE_DETAIL.ADD_BY,CLW_STORE_MESSAGE_DETAIL.ADD_DATE,CLW_STORE_MESSAGE_DETAIL.MOD_BY,CLW_STORE_MESSAGE_DETAIL.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated StoreMessageDetailData Object.
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
    *@returns a populated StoreMessageDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         StoreMessageDetailData x = StoreMessageDetailData.createValue();
         
         x.setStoreMessageDetailId(rs.getInt(1+offset));
         x.setStoreMessageId(rs.getInt(2+offset));
         x.setMessageDetailTypeCd(rs.getString(3+offset));
         x.setMessageTitle(rs.getString(4+offset));
         x.setLanguageCd(rs.getString(5+offset));
         x.setCountry(rs.getString(6+offset));
         x.setMessageAuthor(rs.getString(7+offset));
         x.setMessageAbstract(rs.getString(8+offset));
         x.setMessageBody(rs.getString(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the StoreMessageDetailData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a StoreMessageDetailDataVector object that consists
     * of StoreMessageDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new StoreMessageDetailDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT STORE_MESSAGE_DETAIL_ID,STORE_MESSAGE_ID,MESSAGE_DETAIL_TYPE_CD,MESSAGE_TITLE,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_STORE_MESSAGE_DETAIL.STORE_MESSAGE_DETAIL_ID,CLW_STORE_MESSAGE_DETAIL.STORE_MESSAGE_ID,CLW_STORE_MESSAGE_DETAIL.MESSAGE_DETAIL_TYPE_CD,CLW_STORE_MESSAGE_DETAIL.MESSAGE_TITLE,CLW_STORE_MESSAGE_DETAIL.LANGUAGE_CD,CLW_STORE_MESSAGE_DETAIL.COUNTRY,CLW_STORE_MESSAGE_DETAIL.MESSAGE_AUTHOR,CLW_STORE_MESSAGE_DETAIL.MESSAGE_ABSTRACT,CLW_STORE_MESSAGE_DETAIL.MESSAGE_BODY,CLW_STORE_MESSAGE_DETAIL.ADD_BY,CLW_STORE_MESSAGE_DETAIL.ADD_DATE,CLW_STORE_MESSAGE_DETAIL.MOD_BY,CLW_STORE_MESSAGE_DETAIL.MOD_DATE FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE_DETAIL.equals(otherTable)){
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
        StoreMessageDetailDataVector v = new StoreMessageDetailDataVector();
        while (rs.next()) {
            StoreMessageDetailData x = StoreMessageDetailData.createValue();
            
            x.setStoreMessageDetailId(rs.getInt(1));
            x.setStoreMessageId(rs.getInt(2));
            x.setMessageDetailTypeCd(rs.getString(3));
            x.setMessageTitle(rs.getString(4));
            x.setLanguageCd(rs.getString(5));
            x.setCountry(rs.getString(6));
            x.setMessageAuthor(rs.getString(7));
            x.setMessageAbstract(rs.getString(8));
            x.setMessageBody(rs.getString(9));
            x.setAddBy(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a StoreMessageDetailDataVector object that consists
     * of StoreMessageDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for StoreMessageDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new StoreMessageDetailDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        StoreMessageDetailDataVector v = new StoreMessageDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT STORE_MESSAGE_DETAIL_ID,STORE_MESSAGE_ID,MESSAGE_DETAIL_TYPE_CD,MESSAGE_TITLE,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE_DETAIL WHERE STORE_MESSAGE_DETAIL_ID IN (");

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
            StoreMessageDetailData x=null;
            while (rs.next()) {
                // build the object
                x=StoreMessageDetailData.createValue();
                
                x.setStoreMessageDetailId(rs.getInt(1));
                x.setStoreMessageId(rs.getInt(2));
                x.setMessageDetailTypeCd(rs.getString(3));
                x.setMessageTitle(rs.getString(4));
                x.setLanguageCd(rs.getString(5));
                x.setCountry(rs.getString(6));
                x.setMessageAuthor(rs.getString(7));
                x.setMessageAbstract(rs.getString(8));
                x.setMessageBody(rs.getString(9));
                x.setAddBy(rs.getString(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a StoreMessageDetailDataVector object of all
     * StoreMessageDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new StoreMessageDetailDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT STORE_MESSAGE_DETAIL_ID,STORE_MESSAGE_ID,MESSAGE_DETAIL_TYPE_CD,MESSAGE_TITLE,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        StoreMessageDetailDataVector v = new StoreMessageDetailDataVector();
        StoreMessageDetailData x = null;
        while (rs.next()) {
            // build the object
            x = StoreMessageDetailData.createValue();
            
            x.setStoreMessageDetailId(rs.getInt(1));
            x.setStoreMessageId(rs.getInt(2));
            x.setMessageDetailTypeCd(rs.getString(3));
            x.setMessageTitle(rs.getString(4));
            x.setLanguageCd(rs.getString(5));
            x.setCountry(rs.getString(6));
            x.setMessageAuthor(rs.getString(7));
            x.setMessageAbstract(rs.getString(8));
            x.setMessageBody(rs.getString(9));
            x.setAddBy(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * StoreMessageDetailData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT STORE_MESSAGE_DETAIL_ID FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT STORE_MESSAGE_DETAIL_ID FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE_DETAIL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE_DETAIL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE_DETAIL");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE_DETAIL.equals(otherTable)){
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
     * Inserts a StoreMessageDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new StoreMessageDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StoreMessageDetailData insert(Connection pCon, StoreMessageDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_STORE_MESSAGE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_STORE_MESSAGE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setStoreMessageDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_STORE_MESSAGE_DETAIL (STORE_MESSAGE_DETAIL_ID,STORE_MESSAGE_ID,MESSAGE_DETAIL_TYPE_CD,MESSAGE_TITLE,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getStoreMessageDetailId());
        pstmt.setInt(2,pData.getStoreMessageId());
        pstmt.setString(3,pData.getMessageDetailTypeCd());
        pstmt.setString(4,pData.getMessageTitle());
        pstmt.setString(5,pData.getLanguageCd());
        pstmt.setString(6,pData.getCountry());
        pstmt.setString(7,pData.getMessageAuthor());
        pstmt.setString(8,pData.getMessageAbstract());
        pstmt.setString(9,pData.getMessageBody());
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getModBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_MESSAGE_DETAIL_ID="+pData.getStoreMessageDetailId());
            log.debug("SQL:   STORE_MESSAGE_ID="+pData.getStoreMessageId());
            log.debug("SQL:   MESSAGE_DETAIL_TYPE_CD="+pData.getMessageDetailTypeCd());
            log.debug("SQL:   MESSAGE_TITLE="+pData.getMessageTitle());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   COUNTRY="+pData.getCountry());
            log.debug("SQL:   MESSAGE_AUTHOR="+pData.getMessageAuthor());
            log.debug("SQL:   MESSAGE_ABSTRACT="+pData.getMessageAbstract());
            log.debug("SQL:   MESSAGE_BODY="+pData.getMessageBody());
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
        pData.setStoreMessageDetailId(0);
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
     * Updates a StoreMessageDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StoreMessageDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_STORE_MESSAGE_DETAIL SET STORE_MESSAGE_ID = ?,MESSAGE_DETAIL_TYPE_CD = ?,MESSAGE_TITLE = ?,LANGUAGE_CD = ?,COUNTRY = ?,MESSAGE_AUTHOR = ?,MESSAGE_ABSTRACT = ?,MESSAGE_BODY = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE STORE_MESSAGE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStoreMessageId());
        pstmt.setString(i++,pData.getMessageDetailTypeCd());
        pstmt.setString(i++,pData.getMessageTitle());
        pstmt.setString(i++,pData.getLanguageCd());
        pstmt.setString(i++,pData.getCountry());
        pstmt.setString(i++,pData.getMessageAuthor());
        pstmt.setString(i++,pData.getMessageAbstract());
        pstmt.setString(i++,pData.getMessageBody());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getStoreMessageDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_MESSAGE_ID="+pData.getStoreMessageId());
            log.debug("SQL:   MESSAGE_DETAIL_TYPE_CD="+pData.getMessageDetailTypeCd());
            log.debug("SQL:   MESSAGE_TITLE="+pData.getMessageTitle());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   COUNTRY="+pData.getCountry());
            log.debug("SQL:   MESSAGE_AUTHOR="+pData.getMessageAuthor());
            log.debug("SQL:   MESSAGE_ABSTRACT="+pData.getMessageAbstract());
            log.debug("SQL:   MESSAGE_BODY="+pData.getMessageBody());
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
     * Deletes a StoreMessageDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStoreMessageDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStoreMessageDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_STORE_MESSAGE_DETAIL WHERE STORE_MESSAGE_DETAIL_ID = " + pStoreMessageDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes StoreMessageDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_STORE_MESSAGE_DETAIL");
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
     * Inserts a StoreMessageDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, StoreMessageDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_STORE_MESSAGE_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "STORE_MESSAGE_DETAIL_ID,STORE_MESSAGE_ID,MESSAGE_DETAIL_TYPE_CD,MESSAGE_TITLE,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getStoreMessageDetailId());
        pstmt.setInt(2+4,pData.getStoreMessageId());
        pstmt.setString(3+4,pData.getMessageDetailTypeCd());
        pstmt.setString(4+4,pData.getMessageTitle());
        pstmt.setString(5+4,pData.getLanguageCd());
        pstmt.setString(6+4,pData.getCountry());
        pstmt.setString(7+4,pData.getMessageAuthor());
        pstmt.setString(8+4,pData.getMessageAbstract());
        pstmt.setString(9+4,pData.getMessageBody());
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getModBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a StoreMessageDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new StoreMessageDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StoreMessageDetailData insert(Connection pCon, StoreMessageDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a StoreMessageDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StoreMessageDetailData pData, boolean pLogFl)
        throws SQLException {
        StoreMessageDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getStoreMessageDetailId();
          try {
          oldData = StoreMessageDetailDataAccess.select(pCon,id);
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
     * Deletes a StoreMessageDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStoreMessageDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStoreMessageDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_STORE_MESSAGE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STORE_MESSAGE_DETAIL d WHERE STORE_MESSAGE_DETAIL_ID = " + pStoreMessageDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pStoreMessageDetailId);
        return n;
     }

    /**
     * Deletes StoreMessageDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_STORE_MESSAGE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STORE_MESSAGE_DETAIL d ");
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

