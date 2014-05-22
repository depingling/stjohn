
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        StoreMessageDataAccess
 * Description:  This class is used to build access methods to the CLW_STORE_MESSAGE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
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
 * <code>StoreMessageDataAccess</code>
 */
public class StoreMessageDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(StoreMessageDataAccess.class.getName());

    /** <code>CLW_STORE_MESSAGE</code> table name */
	/* Primary key: STORE_MESSAGE_ID */
	
    public static final String CLW_STORE_MESSAGE = "CLW_STORE_MESSAGE";
    
    /** <code>STORE_MESSAGE_ID</code> STORE_MESSAGE_ID column of table CLW_STORE_MESSAGE */
    public static final String STORE_MESSAGE_ID = "STORE_MESSAGE_ID";
    /** <code>MESSAGE_TITLE</code> MESSAGE_TITLE column of table CLW_STORE_MESSAGE */
    public static final String MESSAGE_TITLE = "MESSAGE_TITLE";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_STORE_MESSAGE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>MESSAGE_TYPE</code> MESSAGE_TYPE column of table CLW_STORE_MESSAGE */
    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
    /** <code>POSTED_DATE</code> POSTED_DATE column of table CLW_STORE_MESSAGE */
    public static final String POSTED_DATE = "POSTED_DATE";
    /** <code>END_DATE</code> END_DATE column of table CLW_STORE_MESSAGE */
    public static final String END_DATE = "END_DATE";
    /** <code>FORCED_READ</code> FORCED_READ column of table CLW_STORE_MESSAGE */
    public static final String FORCED_READ = "FORCED_READ";
    /** <code>HOW_MANY_TIMES</code> HOW_MANY_TIMES column of table CLW_STORE_MESSAGE */
    public static final String HOW_MANY_TIMES = "HOW_MANY_TIMES";
    /** <code>FORCED_READ_COUNT</code> FORCED_READ_COUNT column of table CLW_STORE_MESSAGE */
    public static final String FORCED_READ_COUNT = "FORCED_READ_COUNT";
    /** <code>PUBLISHED</code> PUBLISHED column of table CLW_STORE_MESSAGE */
    public static final String PUBLISHED = "PUBLISHED";
    /** <code>DISPLAY_ORDER</code> DISPLAY_ORDER column of table CLW_STORE_MESSAGE */
    public static final String DISPLAY_ORDER = "DISPLAY_ORDER";
    /** <code>LANGUAGE_CD</code> LANGUAGE_CD column of table CLW_STORE_MESSAGE */
    public static final String LANGUAGE_CD = "LANGUAGE_CD";
    /** <code>COUNTRY</code> COUNTRY column of table CLW_STORE_MESSAGE */
    public static final String COUNTRY = "COUNTRY";
    /** <code>MESSAGE_AUTHOR</code> MESSAGE_AUTHOR column of table CLW_STORE_MESSAGE */
    public static final String MESSAGE_AUTHOR = "MESSAGE_AUTHOR";
    /** <code>MESSAGE_ABSTRACT</code> MESSAGE_ABSTRACT column of table CLW_STORE_MESSAGE */
    public static final String MESSAGE_ABSTRACT = "MESSAGE_ABSTRACT";
    /** <code>MESSAGE_BODY</code> MESSAGE_BODY column of table CLW_STORE_MESSAGE */
    public static final String MESSAGE_BODY = "MESSAGE_BODY";
    /** <code>STORE_MESSAGE_STATUS_CD</code> STORE_MESSAGE_STATUS_CD column of table CLW_STORE_MESSAGE */
    public static final String STORE_MESSAGE_STATUS_CD = "STORE_MESSAGE_STATUS_CD";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_STORE_MESSAGE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_STORE_MESSAGE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_STORE_MESSAGE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_STORE_MESSAGE */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public StoreMessageDataAccess()
    {
    }

    /**
     * Gets a StoreMessageData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pStoreMessageId The key requested.
     * @return new StoreMessageData()
     * @throws            SQLException
     */
    public static StoreMessageData select(Connection pCon, int pStoreMessageId)
        throws SQLException, DataNotFoundException {
        StoreMessageData x=null;
        String sql="SELECT STORE_MESSAGE_ID,MESSAGE_TITLE,SHORT_DESC,MESSAGE_TYPE,POSTED_DATE,END_DATE,FORCED_READ,HOW_MANY_TIMES,FORCED_READ_COUNT,PUBLISHED,DISPLAY_ORDER,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,STORE_MESSAGE_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE WHERE STORE_MESSAGE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pStoreMessageId=" + pStoreMessageId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pStoreMessageId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=StoreMessageData.createValue();
            
            x.setStoreMessageId(rs.getInt(1));
            x.setMessageTitle(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setMessageType(rs.getString(4));
            x.setPostedDate(rs.getDate(5));
            x.setEndDate(rs.getDate(6));
            x.setForcedRead(rs.getString(7));
            x.setHowManyTimes(rs.getInt(8));
            x.setForcedReadCount(rs.getInt(9));
            x.setPublished(rs.getString(10));
            x.setDisplayOrder(rs.getInt(11));
            x.setLanguageCd(rs.getString(12));
            x.setCountry(rs.getString(13));
            x.setMessageAuthor(rs.getString(14));
            x.setMessageAbstract(rs.getString(15));
            x.setMessageBody(rs.getString(16));
            x.setStoreMessageStatusCd(rs.getString(17));
            x.setAddBy(rs.getString(18));
            x.setAddDate(rs.getTimestamp(19));
            x.setModBy(rs.getString(20));
            x.setModDate(rs.getTimestamp(21));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("STORE_MESSAGE_ID :" + pStoreMessageId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a StoreMessageDataVector object that consists
     * of StoreMessageData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new StoreMessageDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a StoreMessageData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_STORE_MESSAGE.STORE_MESSAGE_ID,CLW_STORE_MESSAGE.MESSAGE_TITLE,CLW_STORE_MESSAGE.SHORT_DESC,CLW_STORE_MESSAGE.MESSAGE_TYPE,CLW_STORE_MESSAGE.POSTED_DATE,CLW_STORE_MESSAGE.END_DATE,CLW_STORE_MESSAGE.FORCED_READ,CLW_STORE_MESSAGE.HOW_MANY_TIMES,CLW_STORE_MESSAGE.FORCED_READ_COUNT,CLW_STORE_MESSAGE.PUBLISHED,CLW_STORE_MESSAGE.DISPLAY_ORDER,CLW_STORE_MESSAGE.LANGUAGE_CD,CLW_STORE_MESSAGE.COUNTRY,CLW_STORE_MESSAGE.MESSAGE_AUTHOR,CLW_STORE_MESSAGE.MESSAGE_ABSTRACT,CLW_STORE_MESSAGE.MESSAGE_BODY,CLW_STORE_MESSAGE.STORE_MESSAGE_STATUS_CD,CLW_STORE_MESSAGE.ADD_BY,CLW_STORE_MESSAGE.ADD_DATE,CLW_STORE_MESSAGE.MOD_BY,CLW_STORE_MESSAGE.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated StoreMessageData Object.
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
    *@returns a populated StoreMessageData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         StoreMessageData x = StoreMessageData.createValue();
         
         x.setStoreMessageId(rs.getInt(1+offset));
         x.setMessageTitle(rs.getString(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setMessageType(rs.getString(4+offset));
         x.setPostedDate(rs.getDate(5+offset));
         x.setEndDate(rs.getDate(6+offset));
         x.setForcedRead(rs.getString(7+offset));
         x.setHowManyTimes(rs.getInt(8+offset));
         x.setForcedReadCount(rs.getInt(9+offset));
         x.setPublished(rs.getString(10+offset));
         x.setDisplayOrder(rs.getInt(11+offset));
         x.setLanguageCd(rs.getString(12+offset));
         x.setCountry(rs.getString(13+offset));
         x.setMessageAuthor(rs.getString(14+offset));
         x.setMessageAbstract(rs.getString(15+offset));
         x.setMessageBody(rs.getString(16+offset));
         x.setStoreMessageStatusCd(rs.getString(17+offset));
         x.setAddBy(rs.getString(18+offset));
         x.setAddDate(rs.getTimestamp(19+offset));
         x.setModBy(rs.getString(20+offset));
         x.setModDate(rs.getTimestamp(21+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the StoreMessageData Object represents.
    */
    public int getColumnCount(){
        return 21;
    }

    /**
     * Gets a StoreMessageDataVector object that consists
     * of StoreMessageData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new StoreMessageDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT STORE_MESSAGE_ID,MESSAGE_TITLE,SHORT_DESC,MESSAGE_TYPE,POSTED_DATE,END_DATE,FORCED_READ,HOW_MANY_TIMES,FORCED_READ_COUNT,PUBLISHED,DISPLAY_ORDER,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,STORE_MESSAGE_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_STORE_MESSAGE.STORE_MESSAGE_ID,CLW_STORE_MESSAGE.MESSAGE_TITLE,CLW_STORE_MESSAGE.SHORT_DESC,CLW_STORE_MESSAGE.MESSAGE_TYPE,CLW_STORE_MESSAGE.POSTED_DATE,CLW_STORE_MESSAGE.END_DATE,CLW_STORE_MESSAGE.FORCED_READ,CLW_STORE_MESSAGE.HOW_MANY_TIMES,CLW_STORE_MESSAGE.FORCED_READ_COUNT,CLW_STORE_MESSAGE.PUBLISHED,CLW_STORE_MESSAGE.DISPLAY_ORDER,CLW_STORE_MESSAGE.LANGUAGE_CD,CLW_STORE_MESSAGE.COUNTRY,CLW_STORE_MESSAGE.MESSAGE_AUTHOR,CLW_STORE_MESSAGE.MESSAGE_ABSTRACT,CLW_STORE_MESSAGE.MESSAGE_BODY,CLW_STORE_MESSAGE.STORE_MESSAGE_STATUS_CD,CLW_STORE_MESSAGE.ADD_BY,CLW_STORE_MESSAGE.ADD_DATE,CLW_STORE_MESSAGE.MOD_BY,CLW_STORE_MESSAGE.MOD_DATE FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE.equals(otherTable)){
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
        StoreMessageDataVector v = new StoreMessageDataVector();
        while (rs.next()) {
            StoreMessageData x = StoreMessageData.createValue();
            
            x.setStoreMessageId(rs.getInt(1));
            x.setMessageTitle(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setMessageType(rs.getString(4));
            x.setPostedDate(rs.getDate(5));
            x.setEndDate(rs.getDate(6));
            x.setForcedRead(rs.getString(7));
            x.setHowManyTimes(rs.getInt(8));
            x.setForcedReadCount(rs.getInt(9));
            x.setPublished(rs.getString(10));
            x.setDisplayOrder(rs.getInt(11));
            x.setLanguageCd(rs.getString(12));
            x.setCountry(rs.getString(13));
            x.setMessageAuthor(rs.getString(14));
            x.setMessageAbstract(rs.getString(15));
            x.setMessageBody(rs.getString(16));
            x.setStoreMessageStatusCd(rs.getString(17));
            x.setAddBy(rs.getString(18));
            x.setAddDate(rs.getTimestamp(19));
            x.setModBy(rs.getString(20));
            x.setModDate(rs.getTimestamp(21));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a StoreMessageDataVector object that consists
     * of StoreMessageData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for StoreMessageData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new StoreMessageDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        StoreMessageDataVector v = new StoreMessageDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT STORE_MESSAGE_ID,MESSAGE_TITLE,SHORT_DESC,MESSAGE_TYPE,POSTED_DATE,END_DATE,FORCED_READ,HOW_MANY_TIMES,FORCED_READ_COUNT,PUBLISHED,DISPLAY_ORDER,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,STORE_MESSAGE_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE WHERE STORE_MESSAGE_ID IN (");

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
            StoreMessageData x=null;
            while (rs.next()) {
                // build the object
                x=StoreMessageData.createValue();
                
                x.setStoreMessageId(rs.getInt(1));
                x.setMessageTitle(rs.getString(2));
                x.setShortDesc(rs.getString(3));
                x.setMessageType(rs.getString(4));
                x.setPostedDate(rs.getDate(5));
                x.setEndDate(rs.getDate(6));
                x.setForcedRead(rs.getString(7));
                x.setHowManyTimes(rs.getInt(8));
                x.setForcedReadCount(rs.getInt(9));
                x.setPublished(rs.getString(10));
                x.setDisplayOrder(rs.getInt(11));
                x.setLanguageCd(rs.getString(12));
                x.setCountry(rs.getString(13));
                x.setMessageAuthor(rs.getString(14));
                x.setMessageAbstract(rs.getString(15));
                x.setMessageBody(rs.getString(16));
                x.setStoreMessageStatusCd(rs.getString(17));
                x.setAddBy(rs.getString(18));
                x.setAddDate(rs.getTimestamp(19));
                x.setModBy(rs.getString(20));
                x.setModDate(rs.getTimestamp(21));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a StoreMessageDataVector object of all
     * StoreMessageData objects in the database.
     * @param pCon An open database connection.
     * @return new StoreMessageDataVector()
     * @throws            SQLException
     */
    public static StoreMessageDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT STORE_MESSAGE_ID,MESSAGE_TITLE,SHORT_DESC,MESSAGE_TYPE,POSTED_DATE,END_DATE,FORCED_READ,HOW_MANY_TIMES,FORCED_READ_COUNT,PUBLISHED,DISPLAY_ORDER,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,STORE_MESSAGE_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_STORE_MESSAGE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        StoreMessageDataVector v = new StoreMessageDataVector();
        StoreMessageData x = null;
        while (rs.next()) {
            // build the object
            x = StoreMessageData.createValue();
            
            x.setStoreMessageId(rs.getInt(1));
            x.setMessageTitle(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setMessageType(rs.getString(4));
            x.setPostedDate(rs.getDate(5));
            x.setEndDate(rs.getDate(6));
            x.setForcedRead(rs.getString(7));
            x.setHowManyTimes(rs.getInt(8));
            x.setForcedReadCount(rs.getInt(9));
            x.setPublished(rs.getString(10));
            x.setDisplayOrder(rs.getInt(11));
            x.setLanguageCd(rs.getString(12));
            x.setCountry(rs.getString(13));
            x.setMessageAuthor(rs.getString(14));
            x.setMessageAbstract(rs.getString(15));
            x.setMessageBody(rs.getString(16));
            x.setStoreMessageStatusCd(rs.getString(17));
            x.setAddBy(rs.getString(18));
            x.setAddDate(rs.getTimestamp(19));
            x.setModBy(rs.getString(20));
            x.setModDate(rs.getTimestamp(21));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * StoreMessageData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT STORE_MESSAGE_ID FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT STORE_MESSAGE_ID FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_STORE_MESSAGE");
                where = pCriteria.getSqlClause("CLW_STORE_MESSAGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_STORE_MESSAGE.equals(otherTable)){
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
     * Inserts a StoreMessageData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new StoreMessageData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StoreMessageData insert(Connection pCon, StoreMessageData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_STORE_MESSAGE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_STORE_MESSAGE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setStoreMessageId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_STORE_MESSAGE (STORE_MESSAGE_ID,MESSAGE_TITLE,SHORT_DESC,MESSAGE_TYPE,POSTED_DATE,END_DATE,FORCED_READ,HOW_MANY_TIMES,FORCED_READ_COUNT,PUBLISHED,DISPLAY_ORDER,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,STORE_MESSAGE_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getStoreMessageId());
        pstmt.setString(2,pData.getMessageTitle());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getMessageType());
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getPostedDate()));
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setString(7,pData.getForcedRead());
        pstmt.setInt(8,pData.getHowManyTimes());
        pstmt.setInt(9,pData.getForcedReadCount());
        pstmt.setString(10,pData.getPublished());
        pstmt.setInt(11,pData.getDisplayOrder());
        pstmt.setString(12,pData.getLanguageCd());
        pstmt.setString(13,pData.getCountry());
        pstmt.setString(14,pData.getMessageAuthor());
        pstmt.setString(15,pData.getMessageAbstract());
        pstmt.setString(16,pData.getMessageBody());
        pstmt.setString(17,pData.getStoreMessageStatusCd());
        pstmt.setString(18,pData.getAddBy());
        pstmt.setTimestamp(19,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(20,pData.getModBy());
        pstmt.setTimestamp(21,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_MESSAGE_ID="+pData.getStoreMessageId());
            log.debug("SQL:   MESSAGE_TITLE="+pData.getMessageTitle());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   MESSAGE_TYPE="+pData.getMessageType());
            log.debug("SQL:   POSTED_DATE="+pData.getPostedDate());
            log.debug("SQL:   END_DATE="+pData.getEndDate());
            log.debug("SQL:   FORCED_READ="+pData.getForcedRead());
            log.debug("SQL:   HOW_MANY_TIMES="+pData.getHowManyTimes());
            log.debug("SQL:   FORCED_READ_COUNT="+pData.getForcedReadCount());
            log.debug("SQL:   PUBLISHED="+pData.getPublished());
            log.debug("SQL:   DISPLAY_ORDER="+pData.getDisplayOrder());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   COUNTRY="+pData.getCountry());
            log.debug("SQL:   MESSAGE_AUTHOR="+pData.getMessageAuthor());
            log.debug("SQL:   MESSAGE_ABSTRACT="+pData.getMessageAbstract());
            log.debug("SQL:   MESSAGE_BODY="+pData.getMessageBody());
            log.debug("SQL:   STORE_MESSAGE_STATUS_CD="+pData.getStoreMessageStatusCd());
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
        pData.setStoreMessageId(0);
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
     * Updates a StoreMessageData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StoreMessageData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_STORE_MESSAGE SET MESSAGE_TITLE = ?,SHORT_DESC = ?,MESSAGE_TYPE = ?,POSTED_DATE = ?,END_DATE = ?,FORCED_READ = ?,HOW_MANY_TIMES = ?,FORCED_READ_COUNT = ?,PUBLISHED = ?,DISPLAY_ORDER = ?,LANGUAGE_CD = ?,COUNTRY = ?,MESSAGE_AUTHOR = ?,MESSAGE_ABSTRACT = ?,MESSAGE_BODY = ?,STORE_MESSAGE_STATUS_CD = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE STORE_MESSAGE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getMessageTitle());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getMessageType());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getPostedDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setString(i++,pData.getForcedRead());
        pstmt.setInt(i++,pData.getHowManyTimes());
        pstmt.setInt(i++,pData.getForcedReadCount());
        pstmt.setString(i++,pData.getPublished());
        pstmt.setInt(i++,pData.getDisplayOrder());
        pstmt.setString(i++,pData.getLanguageCd());
        pstmt.setString(i++,pData.getCountry());
        pstmt.setString(i++,pData.getMessageAuthor());
        pstmt.setString(i++,pData.getMessageAbstract());
        pstmt.setString(i++,pData.getMessageBody());
        pstmt.setString(i++,pData.getStoreMessageStatusCd());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getStoreMessageId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   MESSAGE_TITLE="+pData.getMessageTitle());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   MESSAGE_TYPE="+pData.getMessageType());
            log.debug("SQL:   POSTED_DATE="+pData.getPostedDate());
            log.debug("SQL:   END_DATE="+pData.getEndDate());
            log.debug("SQL:   FORCED_READ="+pData.getForcedRead());
            log.debug("SQL:   HOW_MANY_TIMES="+pData.getHowManyTimes());
            log.debug("SQL:   FORCED_READ_COUNT="+pData.getForcedReadCount());
            log.debug("SQL:   PUBLISHED="+pData.getPublished());
            log.debug("SQL:   DISPLAY_ORDER="+pData.getDisplayOrder());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   COUNTRY="+pData.getCountry());
            log.debug("SQL:   MESSAGE_AUTHOR="+pData.getMessageAuthor());
            log.debug("SQL:   MESSAGE_ABSTRACT="+pData.getMessageAbstract());
            log.debug("SQL:   MESSAGE_BODY="+pData.getMessageBody());
            log.debug("SQL:   STORE_MESSAGE_STATUS_CD="+pData.getStoreMessageStatusCd());
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
     * Deletes a StoreMessageData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStoreMessageId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStoreMessageId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_STORE_MESSAGE WHERE STORE_MESSAGE_ID = " + pStoreMessageId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes StoreMessageData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_STORE_MESSAGE");
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
     * Inserts a StoreMessageData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, StoreMessageData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_STORE_MESSAGE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "STORE_MESSAGE_ID,MESSAGE_TITLE,SHORT_DESC,MESSAGE_TYPE,POSTED_DATE,END_DATE,FORCED_READ,HOW_MANY_TIMES,FORCED_READ_COUNT,PUBLISHED,DISPLAY_ORDER,LANGUAGE_CD,COUNTRY,MESSAGE_AUTHOR,MESSAGE_ABSTRACT,MESSAGE_BODY,STORE_MESSAGE_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getStoreMessageId());
        pstmt.setString(2+4,pData.getMessageTitle());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getMessageType());
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getPostedDate()));
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setString(7+4,pData.getForcedRead());
        pstmt.setInt(8+4,pData.getHowManyTimes());
        pstmt.setInt(9+4,pData.getForcedReadCount());
        pstmt.setString(10+4,pData.getPublished());
        pstmt.setInt(11+4,pData.getDisplayOrder());
        pstmt.setString(12+4,pData.getLanguageCd());
        pstmt.setString(13+4,pData.getCountry());
        pstmt.setString(14+4,pData.getMessageAuthor());
        pstmt.setString(15+4,pData.getMessageAbstract());
        pstmt.setString(16+4,pData.getMessageBody());
        pstmt.setString(17+4,pData.getStoreMessageStatusCd());
        pstmt.setString(18+4,pData.getAddBy());
        pstmt.setTimestamp(19+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(20+4,pData.getModBy());
        pstmt.setTimestamp(21+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a StoreMessageData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new StoreMessageData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static StoreMessageData insert(Connection pCon, StoreMessageData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a StoreMessageData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A StoreMessageData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, StoreMessageData pData, boolean pLogFl)
        throws SQLException {
        StoreMessageData oldData = null;
        if(pLogFl) {
          int id = pData.getStoreMessageId();
          try {
          oldData = StoreMessageDataAccess.select(pCon,id);
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
     * Deletes a StoreMessageData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pStoreMessageId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pStoreMessageId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_STORE_MESSAGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STORE_MESSAGE d WHERE STORE_MESSAGE_ID = " + pStoreMessageId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pStoreMessageId);
        return n;
     }

    /**
     * Deletes StoreMessageData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_STORE_MESSAGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_STORE_MESSAGE d ");
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

