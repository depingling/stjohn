
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        NoteTextDataAccess
 * Description:  This class is used to build access methods to the CLW_NOTE_TEXT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.NoteTextData;
import com.cleanwise.service.api.value.NoteTextDataVector;
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
 * <code>NoteTextDataAccess</code>
 */
public class NoteTextDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(NoteTextDataAccess.class.getName());

    /** <code>CLW_NOTE_TEXT</code> table name */
	/* Primary key: NOTE_TEXT_ID */
	
    public static final String CLW_NOTE_TEXT = "CLW_NOTE_TEXT";
    
    /** <code>NOTE_TEXT_ID</code> NOTE_TEXT_ID column of table CLW_NOTE_TEXT */
    public static final String NOTE_TEXT_ID = "NOTE_TEXT_ID";
    /** <code>USER_FIRST_NAME</code> USER_FIRST_NAME column of table CLW_NOTE_TEXT */
    public static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    /** <code>USER_LAST_NAME</code> USER_LAST_NAME column of table CLW_NOTE_TEXT */
    public static final String USER_LAST_NAME = "USER_LAST_NAME";
    /** <code>NOTE_ID</code> NOTE_ID column of table CLW_NOTE_TEXT */
    public static final String NOTE_ID = "NOTE_ID";
    /** <code>SEQ_NUM</code> SEQ_NUM column of table CLW_NOTE_TEXT */
    public static final String SEQ_NUM = "SEQ_NUM";
    /** <code>PAGE_NUM</code> PAGE_NUM column of table CLW_NOTE_TEXT */
    public static final String PAGE_NUM = "PAGE_NUM";
    /** <code>NOTE_TEXT</code> NOTE_TEXT column of table CLW_NOTE_TEXT */
    public static final String NOTE_TEXT = "NOTE_TEXT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_NOTE_TEXT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_NOTE_TEXT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_NOTE_TEXT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_NOTE_TEXT */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public NoteTextDataAccess()
    {
    }

    /**
     * Gets a NoteTextData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pNoteTextId The key requested.
     * @return new NoteTextData()
     * @throws            SQLException
     */
    public static NoteTextData select(Connection pCon, int pNoteTextId)
        throws SQLException, DataNotFoundException {
        NoteTextData x=null;
        String sql="SELECT NOTE_TEXT_ID,USER_FIRST_NAME,USER_LAST_NAME,NOTE_ID,SEQ_NUM,PAGE_NUM,NOTE_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_NOTE_TEXT WHERE NOTE_TEXT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pNoteTextId=" + pNoteTextId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pNoteTextId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=NoteTextData.createValue();
            
            x.setNoteTextId(rs.getInt(1));
            x.setUserFirstName(rs.getString(2));
            x.setUserLastName(rs.getString(3));
            x.setNoteId(rs.getInt(4));
            x.setSeqNum(rs.getInt(5));
            x.setPageNum(rs.getInt(6));
            x.setNoteText(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("NOTE_TEXT_ID :" + pNoteTextId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a NoteTextDataVector object that consists
     * of NoteTextData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new NoteTextDataVector()
     * @throws            SQLException
     */
    public static NoteTextDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a NoteTextData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_NOTE_TEXT.NOTE_TEXT_ID,CLW_NOTE_TEXT.USER_FIRST_NAME,CLW_NOTE_TEXT.USER_LAST_NAME,CLW_NOTE_TEXT.NOTE_ID,CLW_NOTE_TEXT.SEQ_NUM,CLW_NOTE_TEXT.PAGE_NUM,CLW_NOTE_TEXT.NOTE_TEXT,CLW_NOTE_TEXT.ADD_DATE,CLW_NOTE_TEXT.ADD_BY,CLW_NOTE_TEXT.MOD_DATE,CLW_NOTE_TEXT.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated NoteTextData Object.
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
    *@returns a populated NoteTextData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         NoteTextData x = NoteTextData.createValue();
         
         x.setNoteTextId(rs.getInt(1+offset));
         x.setUserFirstName(rs.getString(2+offset));
         x.setUserLastName(rs.getString(3+offset));
         x.setNoteId(rs.getInt(4+offset));
         x.setSeqNum(rs.getInt(5+offset));
         x.setPageNum(rs.getInt(6+offset));
         x.setNoteText(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the NoteTextData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a NoteTextDataVector object that consists
     * of NoteTextData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new NoteTextDataVector()
     * @throws            SQLException
     */
    public static NoteTextDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT NOTE_TEXT_ID,USER_FIRST_NAME,USER_LAST_NAME,NOTE_ID,SEQ_NUM,PAGE_NUM,NOTE_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_NOTE_TEXT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_NOTE_TEXT.NOTE_TEXT_ID,CLW_NOTE_TEXT.USER_FIRST_NAME,CLW_NOTE_TEXT.USER_LAST_NAME,CLW_NOTE_TEXT.NOTE_ID,CLW_NOTE_TEXT.SEQ_NUM,CLW_NOTE_TEXT.PAGE_NUM,CLW_NOTE_TEXT.NOTE_TEXT,CLW_NOTE_TEXT.ADD_DATE,CLW_NOTE_TEXT.ADD_BY,CLW_NOTE_TEXT.MOD_DATE,CLW_NOTE_TEXT.MOD_BY FROM CLW_NOTE_TEXT");
                where = pCriteria.getSqlClause("CLW_NOTE_TEXT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_NOTE_TEXT.equals(otherTable)){
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
        NoteTextDataVector v = new NoteTextDataVector();
        while (rs.next()) {
            NoteTextData x = NoteTextData.createValue();
            
            x.setNoteTextId(rs.getInt(1));
            x.setUserFirstName(rs.getString(2));
            x.setUserLastName(rs.getString(3));
            x.setNoteId(rs.getInt(4));
            x.setSeqNum(rs.getInt(5));
            x.setPageNum(rs.getInt(6));
            x.setNoteText(rs.getString(7));
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
     * Gets a NoteTextDataVector object that consists
     * of NoteTextData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for NoteTextData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new NoteTextDataVector()
     * @throws            SQLException
     */
    public static NoteTextDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        NoteTextDataVector v = new NoteTextDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT NOTE_TEXT_ID,USER_FIRST_NAME,USER_LAST_NAME,NOTE_ID,SEQ_NUM,PAGE_NUM,NOTE_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_NOTE_TEXT WHERE NOTE_TEXT_ID IN (");

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
            NoteTextData x=null;
            while (rs.next()) {
                // build the object
                x=NoteTextData.createValue();
                
                x.setNoteTextId(rs.getInt(1));
                x.setUserFirstName(rs.getString(2));
                x.setUserLastName(rs.getString(3));
                x.setNoteId(rs.getInt(4));
                x.setSeqNum(rs.getInt(5));
                x.setPageNum(rs.getInt(6));
                x.setNoteText(rs.getString(7));
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
     * Gets a NoteTextDataVector object of all
     * NoteTextData objects in the database.
     * @param pCon An open database connection.
     * @return new NoteTextDataVector()
     * @throws            SQLException
     */
    public static NoteTextDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT NOTE_TEXT_ID,USER_FIRST_NAME,USER_LAST_NAME,NOTE_ID,SEQ_NUM,PAGE_NUM,NOTE_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_NOTE_TEXT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        NoteTextDataVector v = new NoteTextDataVector();
        NoteTextData x = null;
        while (rs.next()) {
            // build the object
            x = NoteTextData.createValue();
            
            x.setNoteTextId(rs.getInt(1));
            x.setUserFirstName(rs.getString(2));
            x.setUserLastName(rs.getString(3));
            x.setNoteId(rs.getInt(4));
            x.setSeqNum(rs.getInt(5));
            x.setPageNum(rs.getInt(6));
            x.setNoteText(rs.getString(7));
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
     * NoteTextData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT NOTE_TEXT_ID FROM CLW_NOTE_TEXT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE_TEXT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_NOTE_TEXT");
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
     * Inserts a NoteTextData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteTextData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new NoteTextData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static NoteTextData insert(Connection pCon, NoteTextData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_NOTE_TEXT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_NOTE_TEXT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setNoteTextId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_NOTE_TEXT (NOTE_TEXT_ID,USER_FIRST_NAME,USER_LAST_NAME,NOTE_ID,SEQ_NUM,PAGE_NUM,NOTE_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getNoteTextId());
        pstmt.setString(2,pData.getUserFirstName());
        pstmt.setString(3,pData.getUserLastName());
        pstmt.setInt(4,pData.getNoteId());
        pstmt.setInt(5,pData.getSeqNum());
        pstmt.setInt(6,pData.getPageNum());
        pstmt.setString(7,pData.getNoteText());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   NOTE_TEXT_ID="+pData.getNoteTextId());
            log.debug("SQL:   USER_FIRST_NAME="+pData.getUserFirstName());
            log.debug("SQL:   USER_LAST_NAME="+pData.getUserLastName());
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   SEQ_NUM="+pData.getSeqNum());
            log.debug("SQL:   PAGE_NUM="+pData.getPageNum());
            log.debug("SQL:   NOTE_TEXT="+pData.getNoteText());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
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
        pData.setNoteTextId(0);
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
     * Updates a NoteTextData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteTextData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, NoteTextData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_NOTE_TEXT SET USER_FIRST_NAME = ?,USER_LAST_NAME = ?,NOTE_ID = ?,SEQ_NUM = ?,PAGE_NUM = ?,NOTE_TEXT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE NOTE_TEXT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getUserFirstName());
        pstmt.setString(i++,pData.getUserLastName());
        pstmt.setInt(i++,pData.getNoteId());
        pstmt.setInt(i++,pData.getSeqNum());
        pstmt.setInt(i++,pData.getPageNum());
        pstmt.setString(i++,pData.getNoteText());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getNoteTextId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_FIRST_NAME="+pData.getUserFirstName());
            log.debug("SQL:   USER_LAST_NAME="+pData.getUserLastName());
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   SEQ_NUM="+pData.getSeqNum());
            log.debug("SQL:   PAGE_NUM="+pData.getPageNum());
            log.debug("SQL:   NOTE_TEXT="+pData.getNoteText());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
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
     * Deletes a NoteTextData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pNoteTextId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pNoteTextId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_NOTE_TEXT WHERE NOTE_TEXT_ID = " + pNoteTextId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes NoteTextData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_NOTE_TEXT");
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
     * Inserts a NoteTextData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteTextData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, NoteTextData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_NOTE_TEXT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "NOTE_TEXT_ID,USER_FIRST_NAME,USER_LAST_NAME,NOTE_ID,SEQ_NUM,PAGE_NUM,NOTE_TEXT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getNoteTextId());
        pstmt.setString(2+4,pData.getUserFirstName());
        pstmt.setString(3+4,pData.getUserLastName());
        pstmt.setInt(4+4,pData.getNoteId());
        pstmt.setInt(5+4,pData.getSeqNum());
        pstmt.setInt(6+4,pData.getPageNum());
        pstmt.setString(7+4,pData.getNoteText());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a NoteTextData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteTextData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new NoteTextData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static NoteTextData insert(Connection pCon, NoteTextData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a NoteTextData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A NoteTextData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, NoteTextData pData, boolean pLogFl)
        throws SQLException {
        NoteTextData oldData = null;
        if(pLogFl) {
          int id = pData.getNoteTextId();
          try {
          oldData = NoteTextDataAccess.select(pCon,id);
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
     * Deletes a NoteTextData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pNoteTextId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pNoteTextId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_NOTE_TEXT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_NOTE_TEXT d WHERE NOTE_TEXT_ID = " + pNoteTextId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pNoteTextId);
        return n;
     }

    /**
     * Deletes NoteTextData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_NOTE_TEXT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_NOTE_TEXT d ");
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

