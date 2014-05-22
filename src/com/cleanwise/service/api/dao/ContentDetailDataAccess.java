
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContentDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTENT_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContentDetailData;
import com.cleanwise.service.api.value.ContentDetailDataVector;
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
 * <code>ContentDetailDataAccess</code>
 */
public class ContentDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContentDetailDataAccess.class.getName());

    /** <code>CLW_CONTENT_DETAIL</code> table name */
	/* Primary key: CONTENT_DETAIL_ID */
	
    public static final String CLW_CONTENT_DETAIL = "CLW_CONTENT_DETAIL";
    
    /** <code>CONTENT_ID</code> CONTENT_ID column of table CLW_CONTENT_DETAIL */
    public static final String CONTENT_ID = "CONTENT_ID";
    /** <code>CONTENT_DETAIL_ID</code> CONTENT_DETAIL_ID column of table CLW_CONTENT_DETAIL */
    public static final String CONTENT_DETAIL_ID = "CONTENT_DETAIL_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CONTENT_DETAIL */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>SEQUENCE_NUM</code> SEQUENCE_NUM column of table CLW_CONTENT_DETAIL */
    public static final String SEQUENCE_NUM = "SEQUENCE_NUM";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_CONTENT_DETAIL */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>CONTENT_DETAIL_TYPE_CD</code> CONTENT_DETAIL_TYPE_CD column of table CLW_CONTENT_DETAIL */
    public static final String CONTENT_DETAIL_TYPE_CD = "CONTENT_DETAIL_TYPE_CD";
    /** <code>CONTENT_DETAIL_STATUS_CD</code> CONTENT_DETAIL_STATUS_CD column of table CLW_CONTENT_DETAIL */
    public static final String CONTENT_DETAIL_STATUS_CD = "CONTENT_DETAIL_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CONTENT_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CONTENT_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CONTENT_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CONTENT_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ContentDetailDataAccess()
    {
    }

    /**
     * Gets a ContentDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContentDetailId The key requested.
     * @return new ContentDetailData()
     * @throws            SQLException
     */
    public static ContentDetailData select(Connection pCon, int pContentDetailId)
        throws SQLException, DataNotFoundException {
        ContentDetailData x=null;
        String sql="SELECT CONTENT_ID,CONTENT_DETAIL_ID,SHORT_DESC,SEQUENCE_NUM,CLW_VALUE,CONTENT_DETAIL_TYPE_CD,CONTENT_DETAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTENT_DETAIL WHERE CONTENT_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContentDetailId=" + pContentDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContentDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContentDetailData.createValue();
            
            x.setContentId(rs.getInt(1));
            x.setContentDetailId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setSequenceNum(rs.getInt(4));
            x.setValue(rs.getString(5));
            x.setContentDetailTypeCd(rs.getString(6));
            x.setContentDetailStatusCd(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTENT_DETAIL_ID :" + pContentDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContentDetailDataVector object that consists
     * of ContentDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContentDetailDataVector()
     * @throws            SQLException
     */
    public static ContentDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContentDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTENT_DETAIL.CONTENT_ID,CLW_CONTENT_DETAIL.CONTENT_DETAIL_ID,CLW_CONTENT_DETAIL.SHORT_DESC,CLW_CONTENT_DETAIL.SEQUENCE_NUM,CLW_CONTENT_DETAIL.CLW_VALUE,CLW_CONTENT_DETAIL.CONTENT_DETAIL_TYPE_CD,CLW_CONTENT_DETAIL.CONTENT_DETAIL_STATUS_CD,CLW_CONTENT_DETAIL.ADD_DATE,CLW_CONTENT_DETAIL.ADD_BY,CLW_CONTENT_DETAIL.MOD_DATE,CLW_CONTENT_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContentDetailData Object.
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
    *@returns a populated ContentDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContentDetailData x = ContentDetailData.createValue();
         
         x.setContentId(rs.getInt(1+offset));
         x.setContentDetailId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setSequenceNum(rs.getInt(4+offset));
         x.setValue(rs.getString(5+offset));
         x.setContentDetailTypeCd(rs.getString(6+offset));
         x.setContentDetailStatusCd(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContentDetailData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a ContentDetailDataVector object that consists
     * of ContentDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContentDetailDataVector()
     * @throws            SQLException
     */
    public static ContentDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTENT_ID,CONTENT_DETAIL_ID,SHORT_DESC,SEQUENCE_NUM,CLW_VALUE,CONTENT_DETAIL_TYPE_CD,CONTENT_DETAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTENT_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTENT_DETAIL.CONTENT_ID,CLW_CONTENT_DETAIL.CONTENT_DETAIL_ID,CLW_CONTENT_DETAIL.SHORT_DESC,CLW_CONTENT_DETAIL.SEQUENCE_NUM,CLW_CONTENT_DETAIL.CLW_VALUE,CLW_CONTENT_DETAIL.CONTENT_DETAIL_TYPE_CD,CLW_CONTENT_DETAIL.CONTENT_DETAIL_STATUS_CD,CLW_CONTENT_DETAIL.ADD_DATE,CLW_CONTENT_DETAIL.ADD_BY,CLW_CONTENT_DETAIL.MOD_DATE,CLW_CONTENT_DETAIL.MOD_BY FROM CLW_CONTENT_DETAIL");
                where = pCriteria.getSqlClause("CLW_CONTENT_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTENT_DETAIL.equals(otherTable)){
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
        ContentDetailDataVector v = new ContentDetailDataVector();
        while (rs.next()) {
            ContentDetailData x = ContentDetailData.createValue();
            
            x.setContentId(rs.getInt(1));
            x.setContentDetailId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setSequenceNum(rs.getInt(4));
            x.setValue(rs.getString(5));
            x.setContentDetailTypeCd(rs.getString(6));
            x.setContentDetailStatusCd(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
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
     * Gets a ContentDetailDataVector object that consists
     * of ContentDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContentDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContentDetailDataVector()
     * @throws            SQLException
     */
    public static ContentDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContentDetailDataVector v = new ContentDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTENT_ID,CONTENT_DETAIL_ID,SHORT_DESC,SEQUENCE_NUM,CLW_VALUE,CONTENT_DETAIL_TYPE_CD,CONTENT_DETAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTENT_DETAIL WHERE CONTENT_DETAIL_ID IN (");

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
            ContentDetailData x=null;
            while (rs.next()) {
                // build the object
                x=ContentDetailData.createValue();
                
                x.setContentId(rs.getInt(1));
                x.setContentDetailId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setSequenceNum(rs.getInt(4));
                x.setValue(rs.getString(5));
                x.setContentDetailTypeCd(rs.getString(6));
                x.setContentDetailStatusCd(rs.getString(7));
                x.setAddDate(rs.getTimestamp(8));
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
     * Gets a ContentDetailDataVector object of all
     * ContentDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new ContentDetailDataVector()
     * @throws            SQLException
     */
    public static ContentDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTENT_ID,CONTENT_DETAIL_ID,SHORT_DESC,SEQUENCE_NUM,CLW_VALUE,CONTENT_DETAIL_TYPE_CD,CONTENT_DETAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CONTENT_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContentDetailDataVector v = new ContentDetailDataVector();
        ContentDetailData x = null;
        while (rs.next()) {
            // build the object
            x = ContentDetailData.createValue();
            
            x.setContentId(rs.getInt(1));
            x.setContentDetailId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setSequenceNum(rs.getInt(4));
            x.setValue(rs.getString(5));
            x.setContentDetailTypeCd(rs.getString(6));
            x.setContentDetailStatusCd(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
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
     * ContentDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTENT_DETAIL_ID FROM CLW_CONTENT_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT_DETAIL");
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
     * Inserts a ContentDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ContentDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContentDetailData insert(Connection pCon, ContentDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTENT_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTENT_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContentDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTENT_DETAIL (CONTENT_ID,CONTENT_DETAIL_ID,SHORT_DESC,SEQUENCE_NUM,CLW_VALUE,CONTENT_DETAIL_TYPE_CD,CONTENT_DETAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getContentId());
        pstmt.setInt(2,pData.getContentDetailId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setInt(4,pData.getSequenceNum());
        pstmt.setString(5,pData.getValue());
        pstmt.setString(6,pData.getContentDetailTypeCd());
        pstmt.setString(7,pData.getContentDetailStatusCd());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   CONTENT_DETAIL_ID="+pData.getContentDetailId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   SEQUENCE_NUM="+pData.getSequenceNum());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   CONTENT_DETAIL_TYPE_CD="+pData.getContentDetailTypeCd());
            log.debug("SQL:   CONTENT_DETAIL_STATUS_CD="+pData.getContentDetailStatusCd());
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
        pData.setContentDetailId(0);
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
     * Updates a ContentDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContentDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTENT_DETAIL SET CONTENT_ID = ?,SHORT_DESC = ?,SEQUENCE_NUM = ?,CLW_VALUE = ?,CONTENT_DETAIL_TYPE_CD = ?,CONTENT_DETAIL_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CONTENT_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getContentId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setInt(i++,pData.getSequenceNum());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getContentDetailTypeCd());
        pstmt.setString(i++,pData.getContentDetailStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getContentDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   SEQUENCE_NUM="+pData.getSequenceNum());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   CONTENT_DETAIL_TYPE_CD="+pData.getContentDetailTypeCd());
            log.debug("SQL:   CONTENT_DETAIL_STATUS_CD="+pData.getContentDetailStatusCd());
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
     * Deletes a ContentDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContentDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContentDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTENT_DETAIL WHERE CONTENT_DETAIL_ID = " + pContentDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContentDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTENT_DETAIL");
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
     * Inserts a ContentDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContentDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTENT_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTENT_ID,CONTENT_DETAIL_ID,SHORT_DESC,SEQUENCE_NUM,CLW_VALUE,CONTENT_DETAIL_TYPE_CD,CONTENT_DETAIL_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContentId());
        pstmt.setInt(2+4,pData.getContentDetailId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setInt(4+4,pData.getSequenceNum());
        pstmt.setString(5+4,pData.getValue());
        pstmt.setString(6+4,pData.getContentDetailTypeCd());
        pstmt.setString(7+4,pData.getContentDetailStatusCd());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContentDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ContentDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContentDetailData insert(Connection pCon, ContentDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContentDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContentDetailData pData, boolean pLogFl)
        throws SQLException {
        ContentDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getContentDetailId();
          try {
          oldData = ContentDetailDataAccess.select(pCon,id);
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
     * Deletes a ContentDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContentDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContentDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTENT_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTENT_DETAIL d WHERE CONTENT_DETAIL_ID = " + pContentDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContentDetailId);
        return n;
     }

    /**
     * Deletes ContentDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTENT_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTENT_DETAIL d ");
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

