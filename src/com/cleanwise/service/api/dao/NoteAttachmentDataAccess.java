
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        NoteAttachmentDataAccess
 * Description:  This class is used to build access methods to the CLW_NOTE_ATTACHMENT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.NoteAttachmentData;
import com.cleanwise.service.api.value.NoteAttachmentDataVector;
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
 * <code>NoteAttachmentDataAccess</code>
 */
public class NoteAttachmentDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(NoteAttachmentDataAccess.class.getName());

    /** <code>CLW_NOTE_ATTACHMENT</code> table name */
	/* Primary key: NOTE_ATTACHMENT_ID */
	
    public static final String CLW_NOTE_ATTACHMENT = "CLW_NOTE_ATTACHMENT";
    
    /** <code>NOTE_ATTACHMENT_ID</code> NOTE_ATTACHMENT_ID column of table CLW_NOTE_ATTACHMENT */
    public static final String NOTE_ATTACHMENT_ID = "NOTE_ATTACHMENT_ID";
    /** <code>NOTE_ID</code> NOTE_ID column of table CLW_NOTE_ATTACHMENT */
    public static final String NOTE_ID = "NOTE_ID";
    /** <code>SERVER_NAME</code> SERVER_NAME column of table CLW_NOTE_ATTACHMENT */
    public static final String SERVER_NAME = "SERVER_NAME";
    /** <code>FILE_NAME</code> FILE_NAME column of table CLW_NOTE_ATTACHMENT */
    public static final String FILE_NAME = "FILE_NAME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_NOTE_ATTACHMENT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_NOTE_ATTACHMENT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_NOTE_ATTACHMENT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_NOTE_ATTACHMENT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>BINARY_DATA</code> BINARY_DATA column of table CLW_NOTE_ATTACHMENT */
    public static final String BINARY_DATA = "BINARY_DATA";

    /**
     * Constructor.
     */
    public NoteAttachmentDataAccess()
    {
    }

    /**
     * Gets a NoteAttachmentData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pNoteAttachmentId The key requested.
     * @return new NoteAttachmentData()
     * @throws            SQLException
     */
    public static NoteAttachmentData select(Connection pCon, int pNoteAttachmentId)
        throws SQLException, DataNotFoundException {
        NoteAttachmentData x=null;
        String sql="SELECT NOTE_ATTACHMENT_ID,NOTE_ID,SERVER_NAME,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA FROM CLW_NOTE_ATTACHMENT WHERE NOTE_ATTACHMENT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pNoteAttachmentId=" + pNoteAttachmentId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pNoteAttachmentId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=NoteAttachmentData.createValue();
            
            x.setNoteAttachmentId(rs.getInt(1));
            x.setNoteId(rs.getInt(2));
            x.setServerName(rs.getString(3));
            x.setFileName(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setBinaryData(rs.getBytes(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("NOTE_ATTACHMENT_ID :" + pNoteAttachmentId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a NoteAttachmentDataVector object that consists
     * of NoteAttachmentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new NoteAttachmentDataVector()
     * @throws            SQLException
     */
    public static NoteAttachmentDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a NoteAttachmentData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_NOTE_ATTACHMENT.NOTE_ATTACHMENT_ID,CLW_NOTE_ATTACHMENT.NOTE_ID,CLW_NOTE_ATTACHMENT.SERVER_NAME,CLW_NOTE_ATTACHMENT.FILE_NAME,CLW_NOTE_ATTACHMENT.ADD_DATE,CLW_NOTE_ATTACHMENT.ADD_BY,CLW_NOTE_ATTACHMENT.MOD_DATE,CLW_NOTE_ATTACHMENT.MOD_BY,CLW_NOTE_ATTACHMENT.BINARY_DATA";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated NoteAttachmentData Object.
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
    *@returns a populated NoteAttachmentData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         NoteAttachmentData x = NoteAttachmentData.createValue();
         
         x.setNoteAttachmentId(rs.getInt(1+offset));
         x.setNoteId(rs.getInt(2+offset));
         x.setServerName(rs.getString(3+offset));
         x.setFileName(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setBinaryData(rs.getBytes(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the NoteAttachmentData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a NoteAttachmentDataVector object that consists
     * of NoteAttachmentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new NoteAttachmentDataVector()
     * @throws            SQLException
     */
    public static NoteAttachmentDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT NOTE_ATTACHMENT_ID,NOTE_ID,SERVER_NAME,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA FROM CLW_NOTE_ATTACHMENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_NOTE_ATTACHMENT.NOTE_ATTACHMENT_ID,CLW_NOTE_ATTACHMENT.NOTE_ID,CLW_NOTE_ATTACHMENT.SERVER_NAME,CLW_NOTE_ATTACHMENT.FILE_NAME,CLW_NOTE_ATTACHMENT.ADD_DATE,CLW_NOTE_ATTACHMENT.ADD_BY,CLW_NOTE_ATTACHMENT.MOD_DATE,CLW_NOTE_ATTACHMENT.MOD_BY,CLW_NOTE_ATTACHMENT.BINARY_DATA FROM CLW_NOTE_ATTACHMENT");
                where = pCriteria.getSqlClause("CLW_NOTE_ATTACHMENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_NOTE_ATTACHMENT.equals(otherTable)){
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
        NoteAttachmentDataVector v = new NoteAttachmentDataVector();
        while (rs.next()) {
            NoteAttachmentData x = NoteAttachmentData.createValue();
            
            x.setNoteAttachmentId(rs.getInt(1));
            x.setNoteId(rs.getInt(2));
            x.setServerName(rs.getString(3));
            x.setFileName(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setBinaryData(rs.getBytes(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a NoteAttachmentDataVector object that consists
     * of NoteAttachmentData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for NoteAttachmentData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new NoteAttachmentDataVector()
     * @throws            SQLException
     */
    public static NoteAttachmentDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        NoteAttachmentDataVector v = new NoteAttachmentDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT NOTE_ATTACHMENT_ID,NOTE_ID,SERVER_NAME,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA FROM CLW_NOTE_ATTACHMENT WHERE NOTE_ATTACHMENT_ID IN (");

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
            NoteAttachmentData x=null;
            while (rs.next()) {
                // build the object
                x=NoteAttachmentData.createValue();
                
                x.setNoteAttachmentId(rs.getInt(1));
                x.setNoteId(rs.getInt(2));
                x.setServerName(rs.getString(3));
                x.setFileName(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setBinaryData(rs.getBytes(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a NoteAttachmentDataVector object of all
     * NoteAttachmentData objects in the database.
     * @param pCon An open database connection.
     * @return new NoteAttachmentDataVector()
     * @throws            SQLException
     */
    public static NoteAttachmentDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT NOTE_ATTACHMENT_ID,NOTE_ID,SERVER_NAME,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA FROM CLW_NOTE_ATTACHMENT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        NoteAttachmentDataVector v = new NoteAttachmentDataVector();
        NoteAttachmentData x = null;
        while (rs.next()) {
            // build the object
            x = NoteAttachmentData.createValue();
            
            x.setNoteAttachmentId(rs.getInt(1));
            x.setNoteId(rs.getInt(2));
            x.setServerName(rs.getString(3));
            x.setFileName(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setBinaryData(rs.getBytes(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * NoteAttachmentData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT NOTE_ATTACHMENT_ID FROM CLW_NOTE_ATTACHMENT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE_ATTACHMENT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE_ATTACHMENT");
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
     * Inserts a NoteAttachmentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteAttachmentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new NoteAttachmentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static NoteAttachmentData insert(Connection pCon, NoteAttachmentData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_NOTE_ATTACHMENT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_NOTE_ATTACHMENT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setNoteAttachmentId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_NOTE_ATTACHMENT (NOTE_ATTACHMENT_ID,NOTE_ID,SERVER_NAME,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getNoteAttachmentId());
        pstmt.setInt(2,pData.getNoteId());
        pstmt.setString(3,pData.getServerName());
        pstmt.setString(4,pData.getFileName());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(9, toBlob(pCon,pData.getBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBinaryData());
                pstmt.setBinaryStream(9, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("SQL:   NOTE_ATTACHMENT_ID="+pData.getNoteAttachmentId());
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   SERVER_NAME="+pData.getServerName());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   BINARY_DATA="+pData.getBinaryData());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setNoteAttachmentId(0);
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
     * Updates a NoteAttachmentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteAttachmentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, NoteAttachmentData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_NOTE_ATTACHMENT SET NOTE_ID = ?,SERVER_NAME = ?,FILE_NAME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,BINARY_DATA = ? WHERE NOTE_ATTACHMENT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getNoteId());
        pstmt.setString(i++,pData.getServerName());
        pstmt.setString(i++,pData.getFileName());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getBinaryData()));
        } else {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBinaryData());
                pstmt.setBinaryStream(i++, is, is.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setInt(i++,pData.getNoteAttachmentId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   SERVER_NAME="+pData.getServerName());
            log.debug("SQL:   FILE_NAME="+pData.getFileName());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   BINARY_DATA="+pData.getBinaryData());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a NoteAttachmentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pNoteAttachmentId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pNoteAttachmentId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_NOTE_ATTACHMENT WHERE NOTE_ATTACHMENT_ID = " + pNoteAttachmentId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes NoteAttachmentData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_NOTE_ATTACHMENT");
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
     * Inserts a NoteAttachmentData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteAttachmentData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, NoteAttachmentData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_NOTE_ATTACHMENT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "NOTE_ATTACHMENT_ID,NOTE_ID,SERVER_NAME,FILE_NAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BINARY_DATA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getNoteAttachmentId());
        pstmt.setInt(2+4,pData.getNoteId());
        pstmt.setString(3+4,pData.getServerName());
        pstmt.setString(4+4,pData.getFileName());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setBytes(9+4,pData.getBinaryData());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a NoteAttachmentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteAttachmentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new NoteAttachmentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static NoteAttachmentData insert(Connection pCon, NoteAttachmentData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a NoteAttachmentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteAttachmentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, NoteAttachmentData pData, boolean pLogFl)
        throws SQLException {
        NoteAttachmentData oldData = null;
        if(pLogFl) {
          int id = pData.getNoteAttachmentId();
          try {
          oldData = NoteAttachmentDataAccess.select(pCon,id);
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
     * Deletes a NoteAttachmentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pNoteAttachmentId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pNoteAttachmentId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_NOTE_ATTACHMENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_NOTE_ATTACHMENT d WHERE NOTE_ATTACHMENT_ID = " + pNoteAttachmentId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pNoteAttachmentId);
        return n;
     }

    /**
     * Deletes NoteAttachmentData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_NOTE_ATTACHMENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_NOTE_ATTACHMENT d ");
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

