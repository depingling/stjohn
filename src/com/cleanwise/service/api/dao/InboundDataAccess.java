
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InboundDataAccess
 * Description:  This class is used to build access methods to the CLW_INBOUND table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.value.InboundDataVector;
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
 * <code>InboundDataAccess</code>
 */
public class InboundDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InboundDataAccess.class.getName());

    /** <code>CLW_INBOUND</code> table name */
	/* Primary key: INBOUND_ID */
	
    public static final String CLW_INBOUND = "CLW_INBOUND";
    
    /** <code>INBOUND_ID</code> INBOUND_ID column of table CLW_INBOUND */
    public static final String INBOUND_ID = "INBOUND_ID";
    /** <code>FILE_NAME</code> FILE_NAME column of table CLW_INBOUND */
    public static final String FILE_NAME = "FILE_NAME";
    /** <code>PARTNER_KEY</code> PARTNER_KEY column of table CLW_INBOUND */
    public static final String PARTNER_KEY = "PARTNER_KEY";
    /** <code>URL</code> URL column of table CLW_INBOUND */
    public static final String URL = "URL";
    /** <code>ENCRYPT_BINARY_DATA</code> ENCRYPT_BINARY_DATA column of table CLW_INBOUND */
    public static final String ENCRYPT_BINARY_DATA = "ENCRYPT_BINARY_DATA";
    /** <code>DECRYPT_BINARY_DATA</code> DECRYPT_BINARY_DATA column of table CLW_INBOUND */
    public static final String DECRYPT_BINARY_DATA = "DECRYPT_BINARY_DATA";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INBOUND */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INBOUND */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INBOUND */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INBOUND */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public InboundDataAccess()
    {
    }

    /**
     * Gets a InboundData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInboundId The key requested.
     * @return new InboundData()
     * @throws            SQLException
     */
    public static InboundData select(Connection pCon, int pInboundId)
        throws SQLException, DataNotFoundException {
        InboundData x=null;
        String sql="SELECT INBOUND_ID,FILE_NAME,PARTNER_KEY,URL,ENCRYPT_BINARY_DATA,DECRYPT_BINARY_DATA,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INBOUND WHERE INBOUND_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInboundId=" + pInboundId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInboundId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InboundData.createValue();
            
            x.setInboundId(rs.getInt(1));
            x.setFileName(rs.getString(2));
            x.setPartnerKey(rs.getString(3));
            x.setUrl(rs.getString(4));
            x.setEncryptBinaryData(rs.getBytes(5));
            x.setDecryptBinaryData(rs.getBytes(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INBOUND_ID :" + pInboundId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InboundDataVector object that consists
     * of InboundData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InboundDataVector()
     * @throws            SQLException
     */
    public static InboundDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InboundData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INBOUND.INBOUND_ID,CLW_INBOUND.FILE_NAME,CLW_INBOUND.PARTNER_KEY,CLW_INBOUND.URL,CLW_INBOUND.ENCRYPT_BINARY_DATA,CLW_INBOUND.DECRYPT_BINARY_DATA,CLW_INBOUND.ADD_DATE,CLW_INBOUND.ADD_BY,CLW_INBOUND.MOD_DATE,CLW_INBOUND.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InboundData Object.
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
    *@returns a populated InboundData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InboundData x = InboundData.createValue();
         
         x.setInboundId(rs.getInt(1+offset));
         x.setFileName(rs.getString(2+offset));
         x.setPartnerKey(rs.getString(3+offset));
         x.setUrl(rs.getString(4+offset));
         x.setEncryptBinaryData(rs.getBytes(5+offset));
         x.setDecryptBinaryData(rs.getBytes(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InboundData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a InboundDataVector object that consists
     * of InboundData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InboundDataVector()
     * @throws            SQLException
     */
    public static InboundDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INBOUND_ID,FILE_NAME,PARTNER_KEY,URL,ENCRYPT_BINARY_DATA,DECRYPT_BINARY_DATA,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INBOUND");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INBOUND.INBOUND_ID,CLW_INBOUND.FILE_NAME,CLW_INBOUND.PARTNER_KEY,CLW_INBOUND.URL,CLW_INBOUND.ENCRYPT_BINARY_DATA,CLW_INBOUND.DECRYPT_BINARY_DATA,CLW_INBOUND.ADD_DATE,CLW_INBOUND.ADD_BY,CLW_INBOUND.MOD_DATE,CLW_INBOUND.MOD_BY FROM CLW_INBOUND");
                where = pCriteria.getSqlClause("CLW_INBOUND");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INBOUND.equals(otherTable)){
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
        InboundDataVector v = new InboundDataVector();
        while (rs.next()) {
            InboundData x = InboundData.createValue();
            
            x.setInboundId(rs.getInt(1));
            x.setFileName(rs.getString(2));
            x.setPartnerKey(rs.getString(3));
            x.setUrl(rs.getString(4));
            x.setEncryptBinaryData(rs.getBytes(5));
            x.setDecryptBinaryData(rs.getBytes(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InboundDataVector object that consists
     * of InboundData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InboundData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InboundDataVector()
     * @throws            SQLException
     */
    public static InboundDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InboundDataVector v = new InboundDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INBOUND_ID,FILE_NAME,PARTNER_KEY,URL,ENCRYPT_BINARY_DATA,DECRYPT_BINARY_DATA,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INBOUND WHERE INBOUND_ID IN (");

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
            InboundData x=null;
            while (rs.next()) {
                // build the object
                x=InboundData.createValue();
                
                x.setInboundId(rs.getInt(1));
                x.setFileName(rs.getString(2));
                x.setPartnerKey(rs.getString(3));
                x.setUrl(rs.getString(4));
                x.setEncryptBinaryData(rs.getBytes(5));
                x.setDecryptBinaryData(rs.getBytes(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InboundDataVector object of all
     * InboundData objects in the database.
     * @param pCon An open database connection.
     * @return new InboundDataVector()
     * @throws            SQLException
     */
    public static InboundDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INBOUND_ID,FILE_NAME,PARTNER_KEY,URL,ENCRYPT_BINARY_DATA,DECRYPT_BINARY_DATA,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INBOUND";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InboundDataVector v = new InboundDataVector();
        InboundData x = null;
        while (rs.next()) {
            // build the object
            x = InboundData.createValue();
            
            x.setInboundId(rs.getInt(1));
            x.setFileName(rs.getString(2));
            x.setPartnerKey(rs.getString(3));
            x.setUrl(rs.getString(4));
            x.setEncryptBinaryData(rs.getBytes(5));
            x.setDecryptBinaryData(rs.getBytes(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InboundData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INBOUND_ID FROM CLW_INBOUND");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INBOUND");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INBOUND");
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
     * Inserts a InboundData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InboundData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InboundData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InboundData insert(Connection pCon, InboundData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INBOUND_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INBOUND_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInboundId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INBOUND (INBOUND_ID,FILE_NAME,PARTNER_KEY,URL,ENCRYPT_BINARY_DATA,DECRYPT_BINARY_DATA,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInboundId());
        pstmt.setString(2,pData.getFileName());
        pstmt.setString(3,pData.getPartnerKey());
        pstmt.setString(4,pData.getUrl());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(5, toBlob(pCon,pData.getEncryptBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getEncryptBinaryData());
                pstmt.setBinaryStream(5, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(6, toBlob(pCon,pData.getDecryptBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getDecryptBinaryData());
                pstmt.setBinaryStream(6, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INBOUND_ID="+pData.getInboundId());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   PARTNER_KEY="+pData.getPartnerKey());
            log.debug("SQL:   URL="+pData.getUrl());
            log.debug("SQL:   ENCRYPT_BINARY_DATA="+pData.getEncryptBinaryData());
            log.debug("SQL:   DECRYPT_BINARY_DATA="+pData.getDecryptBinaryData());
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
        pData.setInboundId(0);
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
     * Updates a InboundData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InboundData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InboundData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INBOUND SET FILE_NAME = ?,PARTNER_KEY = ?,URL = ?,ENCRYPT_BINARY_DATA = ?,DECRYPT_BINARY_DATA = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE INBOUND_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getFileName());
        pstmt.setString(i++,pData.getPartnerKey());
        pstmt.setString(i++,pData.getUrl());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getEncryptBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getEncryptBinaryData());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getDecryptBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getDecryptBinaryData());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getInboundId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   PARTNER_KEY="+pData.getPartnerKey());
            log.debug("SQL:   URL="+pData.getUrl());
            log.debug("SQL:   ENCRYPT_BINARY_DATA="+pData.getEncryptBinaryData());
            log.debug("SQL:   DECRYPT_BINARY_DATA="+pData.getDecryptBinaryData());
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
     * Deletes a InboundData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInboundId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInboundId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INBOUND WHERE INBOUND_ID = " + pInboundId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InboundData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INBOUND");
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
     * Inserts a InboundData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InboundData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InboundData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INBOUND (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INBOUND_ID,FILE_NAME,PARTNER_KEY,URL,ENCRYPT_BINARY_DATA,DECRYPT_BINARY_DATA,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInboundId());
        pstmt.setString(2+4,pData.getFileName());
        pstmt.setString(3+4,pData.getPartnerKey());
        pstmt.setString(4+4,pData.getUrl());
        pstmt.setBytes(5+4,pData.getEncryptBinaryData());
        pstmt.setBytes(6+4,pData.getDecryptBinaryData());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InboundData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InboundData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InboundData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InboundData insert(Connection pCon, InboundData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InboundData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InboundData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InboundData pData, boolean pLogFl)
        throws SQLException {
        InboundData oldData = null;
        if(pLogFl) {
          int id = pData.getInboundId();
          try {
          oldData = InboundDataAccess.select(pCon,id);
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
     * Deletes a InboundData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInboundId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInboundId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INBOUND SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INBOUND d WHERE INBOUND_ID = " + pInboundId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInboundId);
        return n;
     }

    /**
     * Deletes InboundData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INBOUND SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INBOUND d ");
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

