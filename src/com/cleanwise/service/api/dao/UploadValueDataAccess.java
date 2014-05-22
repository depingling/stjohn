
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UploadValueDataAccess
 * Description:  This class is used to build access methods to the CLW_UPLOAD_VALUE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UploadValueData;
import com.cleanwise.service.api.value.UploadValueDataVector;
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
 * <code>UploadValueDataAccess</code>
 */
public class UploadValueDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UploadValueDataAccess.class.getName());

    /** <code>CLW_UPLOAD_VALUE</code> table name */
	/* Primary key: UPLOAD_VALUE_ID */
	
    public static final String CLW_UPLOAD_VALUE = "CLW_UPLOAD_VALUE";
    
    /** <code>UPLOAD_ID</code> UPLOAD_ID column of table CLW_UPLOAD_VALUE */
    public static final String UPLOAD_ID = "UPLOAD_ID";
    /** <code>UPLOAD_VALUE_ID</code> UPLOAD_VALUE_ID column of table CLW_UPLOAD_VALUE */
    public static final String UPLOAD_VALUE_ID = "UPLOAD_VALUE_ID";
    /** <code>COLUMN_NUM</code> COLUMN_NUM column of table CLW_UPLOAD_VALUE */
    public static final String COLUMN_NUM = "COLUMN_NUM";
    /** <code>COLUMN_NUM_ORIG</code> COLUMN_NUM_ORIG column of table CLW_UPLOAD_VALUE */
    public static final String COLUMN_NUM_ORIG = "COLUMN_NUM_ORIG";
    /** <code>ROW_NUM</code> ROW_NUM column of table CLW_UPLOAD_VALUE */
    public static final String ROW_NUM = "ROW_NUM";
    /** <code>ROW_NUM_ORIG</code> ROW_NUM_ORIG column of table CLW_UPLOAD_VALUE */
    public static final String ROW_NUM_ORIG = "ROW_NUM_ORIG";
    /** <code>UPLOAD_VALUE</code> UPLOAD_VALUE column of table CLW_UPLOAD_VALUE */
    public static final String UPLOAD_VALUE = "UPLOAD_VALUE";
    /** <code>UPLOAD_VALUE_ORIG</code> UPLOAD_VALUE_ORIG column of table CLW_UPLOAD_VALUE */
    public static final String UPLOAD_VALUE_ORIG = "UPLOAD_VALUE_ORIG";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_UPLOAD_VALUE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_UPLOAD_VALUE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_UPLOAD_VALUE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_UPLOAD_VALUE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public UploadValueDataAccess()
    {
    }

    /**
     * Gets a UploadValueData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUploadValueId The key requested.
     * @return new UploadValueData()
     * @throws            SQLException
     */
    public static UploadValueData select(Connection pCon, int pUploadValueId)
        throws SQLException, DataNotFoundException {
        UploadValueData x=null;
        String sql="SELECT UPLOAD_ID,UPLOAD_VALUE_ID,COLUMN_NUM,COLUMN_NUM_ORIG,ROW_NUM,ROW_NUM_ORIG,UPLOAD_VALUE,UPLOAD_VALUE_ORIG,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD_VALUE WHERE UPLOAD_VALUE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUploadValueId=" + pUploadValueId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUploadValueId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UploadValueData.createValue();
            
            x.setUploadId(rs.getInt(1));
            x.setUploadValueId(rs.getInt(2));
            x.setColumnNum(rs.getInt(3));
            x.setColumnNumOrig(rs.getInt(4));
            x.setRowNum(rs.getInt(5));
            x.setRowNumOrig(rs.getInt(6));
            x.setUploadValue(rs.getString(7));
            x.setUploadValueOrig(rs.getString(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("UPLOAD_VALUE_ID :" + pUploadValueId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UploadValueDataVector object that consists
     * of UploadValueData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UploadValueDataVector()
     * @throws            SQLException
     */
    public static UploadValueDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UploadValueData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_UPLOAD_VALUE.UPLOAD_ID,CLW_UPLOAD_VALUE.UPLOAD_VALUE_ID,CLW_UPLOAD_VALUE.COLUMN_NUM,CLW_UPLOAD_VALUE.COLUMN_NUM_ORIG,CLW_UPLOAD_VALUE.ROW_NUM,CLW_UPLOAD_VALUE.ROW_NUM_ORIG,CLW_UPLOAD_VALUE.UPLOAD_VALUE,CLW_UPLOAD_VALUE.UPLOAD_VALUE_ORIG,CLW_UPLOAD_VALUE.ADD_DATE,CLW_UPLOAD_VALUE.ADD_BY,CLW_UPLOAD_VALUE.MOD_DATE,CLW_UPLOAD_VALUE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UploadValueData Object.
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
    *@returns a populated UploadValueData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UploadValueData x = UploadValueData.createValue();
         
         x.setUploadId(rs.getInt(1+offset));
         x.setUploadValueId(rs.getInt(2+offset));
         x.setColumnNum(rs.getInt(3+offset));
         x.setColumnNumOrig(rs.getInt(4+offset));
         x.setRowNum(rs.getInt(5+offset));
         x.setRowNumOrig(rs.getInt(6+offset));
         x.setUploadValue(rs.getString(7+offset));
         x.setUploadValueOrig(rs.getString(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UploadValueData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a UploadValueDataVector object that consists
     * of UploadValueData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UploadValueDataVector()
     * @throws            SQLException
     */
    public static UploadValueDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT UPLOAD_ID,UPLOAD_VALUE_ID,COLUMN_NUM,COLUMN_NUM_ORIG,ROW_NUM,ROW_NUM_ORIG,UPLOAD_VALUE,UPLOAD_VALUE_ORIG,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD_VALUE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_UPLOAD_VALUE.UPLOAD_ID,CLW_UPLOAD_VALUE.UPLOAD_VALUE_ID,CLW_UPLOAD_VALUE.COLUMN_NUM,CLW_UPLOAD_VALUE.COLUMN_NUM_ORIG,CLW_UPLOAD_VALUE.ROW_NUM,CLW_UPLOAD_VALUE.ROW_NUM_ORIG,CLW_UPLOAD_VALUE.UPLOAD_VALUE,CLW_UPLOAD_VALUE.UPLOAD_VALUE_ORIG,CLW_UPLOAD_VALUE.ADD_DATE,CLW_UPLOAD_VALUE.ADD_BY,CLW_UPLOAD_VALUE.MOD_DATE,CLW_UPLOAD_VALUE.MOD_BY FROM CLW_UPLOAD_VALUE");
                where = pCriteria.getSqlClause("CLW_UPLOAD_VALUE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_UPLOAD_VALUE.equals(otherTable)){
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
        UploadValueDataVector v = new UploadValueDataVector();
        while (rs.next()) {
            UploadValueData x = UploadValueData.createValue();
            
            x.setUploadId(rs.getInt(1));
            x.setUploadValueId(rs.getInt(2));
            x.setColumnNum(rs.getInt(3));
            x.setColumnNumOrig(rs.getInt(4));
            x.setRowNum(rs.getInt(5));
            x.setRowNumOrig(rs.getInt(6));
            x.setUploadValue(rs.getString(7));
            x.setUploadValueOrig(rs.getString(8));
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
     * Gets a UploadValueDataVector object that consists
     * of UploadValueData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UploadValueData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UploadValueDataVector()
     * @throws            SQLException
     */
    public static UploadValueDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UploadValueDataVector v = new UploadValueDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT UPLOAD_ID,UPLOAD_VALUE_ID,COLUMN_NUM,COLUMN_NUM_ORIG,ROW_NUM,ROW_NUM_ORIG,UPLOAD_VALUE,UPLOAD_VALUE_ORIG,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD_VALUE WHERE UPLOAD_VALUE_ID IN (");

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
            UploadValueData x=null;
            while (rs.next()) {
                // build the object
                x=UploadValueData.createValue();
                
                x.setUploadId(rs.getInt(1));
                x.setUploadValueId(rs.getInt(2));
                x.setColumnNum(rs.getInt(3));
                x.setColumnNumOrig(rs.getInt(4));
                x.setRowNum(rs.getInt(5));
                x.setRowNumOrig(rs.getInt(6));
                x.setUploadValue(rs.getString(7));
                x.setUploadValueOrig(rs.getString(8));
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
     * Gets a UploadValueDataVector object of all
     * UploadValueData objects in the database.
     * @param pCon An open database connection.
     * @return new UploadValueDataVector()
     * @throws            SQLException
     */
    public static UploadValueDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT UPLOAD_ID,UPLOAD_VALUE_ID,COLUMN_NUM,COLUMN_NUM_ORIG,ROW_NUM,ROW_NUM_ORIG,UPLOAD_VALUE,UPLOAD_VALUE_ORIG,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_UPLOAD_VALUE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UploadValueDataVector v = new UploadValueDataVector();
        UploadValueData x = null;
        while (rs.next()) {
            // build the object
            x = UploadValueData.createValue();
            
            x.setUploadId(rs.getInt(1));
            x.setUploadValueId(rs.getInt(2));
            x.setColumnNum(rs.getInt(3));
            x.setColumnNumOrig(rs.getInt(4));
            x.setRowNum(rs.getInt(5));
            x.setRowNumOrig(rs.getInt(6));
            x.setUploadValue(rs.getString(7));
            x.setUploadValueOrig(rs.getString(8));
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
     * UploadValueData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT UPLOAD_VALUE_ID FROM CLW_UPLOAD_VALUE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UPLOAD_VALUE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_UPLOAD_VALUE");
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
     * Inserts a UploadValueData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadValueData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UploadValueData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UploadValueData insert(Connection pCon, UploadValueData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_UPLOAD_VALUE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_UPLOAD_VALUE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUploadValueId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_UPLOAD_VALUE (UPLOAD_ID,UPLOAD_VALUE_ID,COLUMN_NUM,COLUMN_NUM_ORIG,ROW_NUM,ROW_NUM_ORIG,UPLOAD_VALUE,UPLOAD_VALUE_ORIG,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUploadId());
        pstmt.setInt(2,pData.getUploadValueId());
        pstmt.setInt(3,pData.getColumnNum());
        pstmt.setInt(4,pData.getColumnNumOrig());
        pstmt.setInt(5,pData.getRowNum());
        pstmt.setInt(6,pData.getRowNumOrig());
        pstmt.setString(7,pData.getUploadValue());
        pstmt.setString(8,pData.getUploadValueOrig());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UPLOAD_ID="+pData.getUploadId());
            log.debug("SQL:   UPLOAD_VALUE_ID="+pData.getUploadValueId());
            log.debug("SQL:   COLUMN_NUM="+pData.getColumnNum());
            log.debug("SQL:   COLUMN_NUM_ORIG="+pData.getColumnNumOrig());
            log.debug("SQL:   ROW_NUM="+pData.getRowNum());
            log.debug("SQL:   ROW_NUM_ORIG="+pData.getRowNumOrig());
            log.debug("SQL:   UPLOAD_VALUE="+pData.getUploadValue());
            log.debug("SQL:   UPLOAD_VALUE_ORIG="+pData.getUploadValueOrig());
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
        pData.setUploadValueId(0);
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
     * Updates a UploadValueData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadValueData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UploadValueData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_UPLOAD_VALUE SET UPLOAD_ID = ?,COLUMN_NUM = ?,COLUMN_NUM_ORIG = ?,ROW_NUM = ?,ROW_NUM_ORIG = ?,UPLOAD_VALUE = ?,UPLOAD_VALUE_ORIG = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE UPLOAD_VALUE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getUploadId());
        pstmt.setInt(i++,pData.getColumnNum());
        pstmt.setInt(i++,pData.getColumnNumOrig());
        pstmt.setInt(i++,pData.getRowNum());
        pstmt.setInt(i++,pData.getRowNumOrig());
        pstmt.setString(i++,pData.getUploadValue());
        pstmt.setString(i++,pData.getUploadValueOrig());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getUploadValueId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   UPLOAD_ID="+pData.getUploadId());
            log.debug("SQL:   COLUMN_NUM="+pData.getColumnNum());
            log.debug("SQL:   COLUMN_NUM_ORIG="+pData.getColumnNumOrig());
            log.debug("SQL:   ROW_NUM="+pData.getRowNum());
            log.debug("SQL:   ROW_NUM_ORIG="+pData.getRowNumOrig());
            log.debug("SQL:   UPLOAD_VALUE="+pData.getUploadValue());
            log.debug("SQL:   UPLOAD_VALUE_ORIG="+pData.getUploadValueOrig());
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
     * Deletes a UploadValueData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUploadValueId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUploadValueId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_UPLOAD_VALUE WHERE UPLOAD_VALUE_ID = " + pUploadValueId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UploadValueData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_UPLOAD_VALUE");
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
     * Inserts a UploadValueData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadValueData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UploadValueData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_UPLOAD_VALUE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "UPLOAD_ID,UPLOAD_VALUE_ID,COLUMN_NUM,COLUMN_NUM_ORIG,ROW_NUM,ROW_NUM_ORIG,UPLOAD_VALUE,UPLOAD_VALUE_ORIG,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUploadId());
        pstmt.setInt(2+4,pData.getUploadValueId());
        pstmt.setInt(3+4,pData.getColumnNum());
        pstmt.setInt(4+4,pData.getColumnNumOrig());
        pstmt.setInt(5+4,pData.getRowNum());
        pstmt.setInt(6+4,pData.getRowNumOrig());
        pstmt.setString(7+4,pData.getUploadValue());
        pstmt.setString(8+4,pData.getUploadValueOrig());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UploadValueData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadValueData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UploadValueData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UploadValueData insert(Connection pCon, UploadValueData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UploadValueData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UploadValueData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UploadValueData pData, boolean pLogFl)
        throws SQLException {
        UploadValueData oldData = null;
        if(pLogFl) {
          int id = pData.getUploadValueId();
          try {
          oldData = UploadValueDataAccess.select(pCon,id);
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
     * Deletes a UploadValueData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUploadValueId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUploadValueId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_UPLOAD_VALUE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UPLOAD_VALUE d WHERE UPLOAD_VALUE_ID = " + pUploadValueId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUploadValueId);
        return n;
     }

    /**
     * Deletes UploadValueData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_UPLOAD_VALUE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_UPLOAD_VALUE d ");
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

