
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UploadDataAccess
 * Description:  This class is used to build access methods to the CLW_UPLOAD table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UploadData;
import com.cleanwise.service.api.value.UploadDataVector;
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
 * <code>UploadDataAccess</code>
 */
public class UploadDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UploadDataAccess.class.getName());

    /** <code>CLW_UPLOAD</code> table name */
	/* Primary key: UPLOAD_ID */
	
    public static final String CLW_UPLOAD = "CLW_UPLOAD";
    
    /** <code>UPLOAD_ID</code> UPLOAD_ID column of table CLW_UPLOAD */
    public static final String UPLOAD_ID = "UPLOAD_ID";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_UPLOAD */
    public static final String STORE_ID = "STORE_ID";
    /** <code>FILE_NAME</code> FILE_NAME column of table CLW_UPLOAD */
    public static final String FILE_NAME = "FILE_NAME";
    /** <code>FILE_TYPE</code> FILE_TYPE column of table CLW_UPLOAD */
    public static final String FILE_TYPE = "FILE_TYPE";
    /** <code>UPLOAD_STATUS_CD</code> UPLOAD_STATUS_CD column of table CLW_UPLOAD */
    public static final String UPLOAD_STATUS_CD = "UPLOAD_STATUS_CD";
    /** <code>COULUMN_QTY</code> COULUMN_QTY column of table CLW_UPLOAD */
    public static final String COULUMN_QTY = "COULUMN_QTY";
    /** <code>ROW_QTY</code> ROW_QTY column of table CLW_UPLOAD */
    public static final String ROW_QTY = "ROW_QTY";
    /** <code>NOTE</code> NOTE column of table CLW_UPLOAD */
    public static final String NOTE = "NOTE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_UPLOAD */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_UPLOAD */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_UPLOAD */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_UPLOAD */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public UploadDataAccess()
    {
    }

    /**
     * Gets a UploadData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUploadId The key requested.
     * @return new UploadData()
     * @throws            SQLException
     */
    public static UploadData select(Connection pCon, int pUploadId)
        throws SQLException, DataNotFoundException {
        UploadData x=null;
        String sql="SELECT UPLOAD_ID,STORE_ID,FILE_NAME,FILE_TYPE,UPLOAD_STATUS_CD,COULUMN_QTY,ROW_QTY,NOTE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD WHERE UPLOAD_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUploadId=" + pUploadId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUploadId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UploadData.createValue();
            
            x.setUploadId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setFileName(rs.getString(3));
            x.setFileType(rs.getString(4));
            x.setUploadStatusCd(rs.getString(5));
            x.setCoulumnQty(rs.getInt(6));
            x.setRowQty(rs.getInt(7));
            x.setNote(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("UPLOAD_ID :" + pUploadId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UploadDataVector object that consists
     * of UploadData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UploadDataVector()
     * @throws            SQLException
     */
    public static UploadDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UploadData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_UPLOAD.UPLOAD_ID,CLW_UPLOAD.STORE_ID,CLW_UPLOAD.FILE_NAME,CLW_UPLOAD.FILE_TYPE,CLW_UPLOAD.UPLOAD_STATUS_CD,CLW_UPLOAD.COULUMN_QTY,CLW_UPLOAD.ROW_QTY,CLW_UPLOAD.NOTE,CLW_UPLOAD.ADD_DATE,CLW_UPLOAD.ADD_BY,CLW_UPLOAD.MOD_DATE,CLW_UPLOAD.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UploadData Object.
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
    *@returns a populated UploadData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UploadData x = UploadData.createValue();
         
         x.setUploadId(rs.getInt(1+offset));
         x.setStoreId(rs.getInt(2+offset));
         x.setFileName(rs.getString(3+offset));
         x.setFileType(rs.getString(4+offset));
         x.setUploadStatusCd(rs.getString(5+offset));
         x.setCoulumnQty(rs.getInt(6+offset));
         x.setRowQty(rs.getInt(7+offset));
         x.setNote(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UploadData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a UploadDataVector object that consists
     * of UploadData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UploadDataVector()
     * @throws            SQLException
     */
    public static UploadDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT UPLOAD_ID,STORE_ID,FILE_NAME,FILE_TYPE,UPLOAD_STATUS_CD,COULUMN_QTY,ROW_QTY,NOTE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_UPLOAD.UPLOAD_ID,CLW_UPLOAD.STORE_ID,CLW_UPLOAD.FILE_NAME,CLW_UPLOAD.FILE_TYPE,CLW_UPLOAD.UPLOAD_STATUS_CD,CLW_UPLOAD.COULUMN_QTY,CLW_UPLOAD.ROW_QTY,CLW_UPLOAD.NOTE,CLW_UPLOAD.ADD_DATE,CLW_UPLOAD.ADD_BY,CLW_UPLOAD.MOD_DATE,CLW_UPLOAD.MOD_BY FROM CLW_UPLOAD");
                where = pCriteria.getSqlClause("CLW_UPLOAD");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_UPLOAD.equals(otherTable)){
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
        UploadDataVector v = new UploadDataVector();
        while (rs.next()) {
            UploadData x = UploadData.createValue();
            
            x.setUploadId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setFileName(rs.getString(3));
            x.setFileType(rs.getString(4));
            x.setUploadStatusCd(rs.getString(5));
            x.setCoulumnQty(rs.getInt(6));
            x.setRowQty(rs.getInt(7));
            x.setNote(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UploadDataVector object that consists
     * of UploadData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UploadData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UploadDataVector()
     * @throws            SQLException
     */
    public static UploadDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UploadDataVector v = new UploadDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT UPLOAD_ID,STORE_ID,FILE_NAME,FILE_TYPE,UPLOAD_STATUS_CD,COULUMN_QTY,ROW_QTY,NOTE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD WHERE UPLOAD_ID IN (");

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
            UploadData x=null;
            while (rs.next()) {
                // build the object
                x=UploadData.createValue();
                
                x.setUploadId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setFileName(rs.getString(3));
                x.setFileType(rs.getString(4));
                x.setUploadStatusCd(rs.getString(5));
                x.setCoulumnQty(rs.getInt(6));
                x.setRowQty(rs.getInt(7));
                x.setNote(rs.getString(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UploadDataVector object of all
     * UploadData objects in the database.
     * @param pCon An open database connection.
     * @return new UploadDataVector()
     * @throws            SQLException
     */
    public static UploadDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT UPLOAD_ID,STORE_ID,FILE_NAME,FILE_TYPE,UPLOAD_STATUS_CD,COULUMN_QTY,ROW_QTY,NOTE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UploadDataVector v = new UploadDataVector();
        UploadData x = null;
        while (rs.next()) {
            // build the object
            x = UploadData.createValue();
            
            x.setUploadId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setFileName(rs.getString(3));
            x.setFileType(rs.getString(4));
            x.setUploadStatusCd(rs.getString(5));
            x.setCoulumnQty(rs.getInt(6));
            x.setRowQty(rs.getInt(7));
            x.setNote(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UploadData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT UPLOAD_ID FROM CLW_UPLOAD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UPLOAD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UPLOAD");
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
     * Inserts a UploadData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UploadData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UploadData insert(Connection pCon, UploadData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_UPLOAD_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_UPLOAD_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUploadId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_UPLOAD (UPLOAD_ID,STORE_ID,FILE_NAME,FILE_TYPE,UPLOAD_STATUS_CD,COULUMN_QTY,ROW_QTY,NOTE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUploadId());
        pstmt.setInt(2,pData.getStoreId());
        pstmt.setString(3,pData.getFileName());
        pstmt.setString(4,pData.getFileType());
        pstmt.setString(5,pData.getUploadStatusCd());
        pstmt.setInt(6,pData.getCoulumnQty());
        pstmt.setInt(7,pData.getRowQty());
        pstmt.setString(8,pData.getNote());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UPLOAD_ID="+pData.getUploadId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   FILE_TYPE="+pData.getFileType());
            log.debug("SQL:   UPLOAD_STATUS_CD="+pData.getUploadStatusCd());
            log.debug("SQL:   COULUMN_QTY="+pData.getCoulumnQty());
            log.debug("SQL:   ROW_QTY="+pData.getRowQty());
            log.debug("SQL:   NOTE="+pData.getNote());
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
        pData.setUploadId(0);
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
     * Updates a UploadData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UploadData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_UPLOAD SET STORE_ID = ?,FILE_NAME = ?,FILE_TYPE = ?,UPLOAD_STATUS_CD = ?,COULUMN_QTY = ?,ROW_QTY = ?,NOTE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE UPLOAD_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getFileName());
        pstmt.setString(i++,pData.getFileType());
        pstmt.setString(i++,pData.getUploadStatusCd());
        pstmt.setInt(i++,pData.getCoulumnQty());
        pstmt.setInt(i++,pData.getRowQty());
        pstmt.setString(i++,pData.getNote());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getUploadId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   FILE_TYPE="+pData.getFileType());
            log.debug("SQL:   UPLOAD_STATUS_CD="+pData.getUploadStatusCd());
            log.debug("SQL:   COULUMN_QTY="+pData.getCoulumnQty());
            log.debug("SQL:   ROW_QTY="+pData.getRowQty());
            log.debug("SQL:   NOTE="+pData.getNote());
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
     * Deletes a UploadData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUploadId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUploadId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_UPLOAD WHERE UPLOAD_ID = " + pUploadId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UploadData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_UPLOAD");
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
     * Inserts a UploadData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UploadData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_UPLOAD (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "UPLOAD_ID,STORE_ID,FILE_NAME,FILE_TYPE,UPLOAD_STATUS_CD,COULUMN_QTY,ROW_QTY,NOTE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUploadId());
        pstmt.setInt(2+4,pData.getStoreId());
        pstmt.setString(3+4,pData.getFileName());
        pstmt.setString(4+4,pData.getFileType());
        pstmt.setString(5+4,pData.getUploadStatusCd());
        pstmt.setInt(6+4,pData.getCoulumnQty());
        pstmt.setInt(7+4,pData.getRowQty());
        pstmt.setString(8+4,pData.getNote());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UploadData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UploadData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UploadData insert(Connection pCon, UploadData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UploadData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UploadData pData, boolean pLogFl)
        throws SQLException {
        UploadData oldData = null;
        if(pLogFl) {
          int id = pData.getUploadId();
          try {
          oldData = UploadDataAccess.select(pCon,id);
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
     * Deletes a UploadData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUploadId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUploadId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_UPLOAD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UPLOAD d WHERE UPLOAD_ID = " + pUploadId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUploadId);
        return n;
     }

    /**
     * Deletes UploadData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_UPLOAD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UPLOAD d ");
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

