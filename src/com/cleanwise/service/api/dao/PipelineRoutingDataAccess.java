
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PipelineRoutingDataAccess
 * Description:  This class is used to build access methods to the CLW_PIPELINE_ROUTING table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PipelineRoutingData;
import com.cleanwise.service.api.value.PipelineRoutingDataVector;
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
 * <code>PipelineRoutingDataAccess</code>
 */
public class PipelineRoutingDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PipelineRoutingDataAccess.class.getName());

    /** <code>CLW_PIPELINE_ROUTING</code> table name */
	/* Primary key: PIPELINE_ROUTING_ID */
	
    public static final String CLW_PIPELINE_ROUTING = "CLW_PIPELINE_ROUTING";
    
    /** <code>PIPELINE_ROUTING_ID</code> PIPELINE_ROUTING_ID column of table CLW_PIPELINE_ROUTING */
    public static final String PIPELINE_ROUTING_ID = "PIPELINE_ROUTING_ID";
    /** <code>PIPELINE_ID</code> PIPELINE_ID column of table CLW_PIPELINE_ROUTING */
    public static final String PIPELINE_ID = "PIPELINE_ID";
    /** <code>SEQUENCE_NUM</code> SEQUENCE_NUM column of table CLW_PIPELINE_ROUTING */
    public static final String SEQUENCE_NUM = "SEQUENCE_NUM";
    /** <code>PROGRAM_CD</code> PROGRAM_CD column of table CLW_PIPELINE_ROUTING */
    public static final String PROGRAM_CD = "PROGRAM_CD";
    /** <code>EXCEPTION_CD</code> EXCEPTION_CD column of table CLW_PIPELINE_ROUTING */
    public static final String EXCEPTION_CD = "EXCEPTION_CD";
    /** <code>ACTION_CD</code> ACTION_CD column of table CLW_PIPELINE_ROUTING */
    public static final String ACTION_CD = "ACTION_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PIPELINE_ROUTING */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PIPELINE_ROUTING */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PIPELINE_ROUTING */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PIPELINE_ROUTING */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public PipelineRoutingDataAccess()
    {
    }

    /**
     * Gets a PipelineRoutingData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPipelineRoutingId The key requested.
     * @return new PipelineRoutingData()
     * @throws            SQLException
     */
    public static PipelineRoutingData select(Connection pCon, int pPipelineRoutingId)
        throws SQLException, DataNotFoundException {
        PipelineRoutingData x=null;
        String sql="SELECT PIPELINE_ROUTING_ID,PIPELINE_ID,SEQUENCE_NUM,PROGRAM_CD,EXCEPTION_CD,ACTION_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PIPELINE_ROUTING WHERE PIPELINE_ROUTING_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPipelineRoutingId=" + pPipelineRoutingId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPipelineRoutingId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PipelineRoutingData.createValue();
            
            x.setPipelineRoutingId(rs.getInt(1));
            x.setPipelineId(rs.getInt(2));
            x.setSequenceNum(rs.getInt(3));
            x.setProgramCd(rs.getString(4));
            x.setExceptionCd(rs.getString(5));
            x.setActionCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PIPELINE_ROUTING_ID :" + pPipelineRoutingId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PipelineRoutingDataVector object that consists
     * of PipelineRoutingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PipelineRoutingDataVector()
     * @throws            SQLException
     */
    public static PipelineRoutingDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PipelineRoutingData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PIPELINE_ROUTING.PIPELINE_ROUTING_ID,CLW_PIPELINE_ROUTING.PIPELINE_ID,CLW_PIPELINE_ROUTING.SEQUENCE_NUM,CLW_PIPELINE_ROUTING.PROGRAM_CD,CLW_PIPELINE_ROUTING.EXCEPTION_CD,CLW_PIPELINE_ROUTING.ACTION_CD,CLW_PIPELINE_ROUTING.ADD_DATE,CLW_PIPELINE_ROUTING.ADD_BY,CLW_PIPELINE_ROUTING.MOD_DATE,CLW_PIPELINE_ROUTING.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PipelineRoutingData Object.
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
    *@returns a populated PipelineRoutingData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PipelineRoutingData x = PipelineRoutingData.createValue();
         
         x.setPipelineRoutingId(rs.getInt(1+offset));
         x.setPipelineId(rs.getInt(2+offset));
         x.setSequenceNum(rs.getInt(3+offset));
         x.setProgramCd(rs.getString(4+offset));
         x.setExceptionCd(rs.getString(5+offset));
         x.setActionCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PipelineRoutingData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a PipelineRoutingDataVector object that consists
     * of PipelineRoutingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PipelineRoutingDataVector()
     * @throws            SQLException
     */
    public static PipelineRoutingDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PIPELINE_ROUTING_ID,PIPELINE_ID,SEQUENCE_NUM,PROGRAM_CD,EXCEPTION_CD,ACTION_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PIPELINE_ROUTING");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PIPELINE_ROUTING.PIPELINE_ROUTING_ID,CLW_PIPELINE_ROUTING.PIPELINE_ID,CLW_PIPELINE_ROUTING.SEQUENCE_NUM,CLW_PIPELINE_ROUTING.PROGRAM_CD,CLW_PIPELINE_ROUTING.EXCEPTION_CD,CLW_PIPELINE_ROUTING.ACTION_CD,CLW_PIPELINE_ROUTING.ADD_DATE,CLW_PIPELINE_ROUTING.ADD_BY,CLW_PIPELINE_ROUTING.MOD_DATE,CLW_PIPELINE_ROUTING.MOD_BY FROM CLW_PIPELINE_ROUTING");
                where = pCriteria.getSqlClause("CLW_PIPELINE_ROUTING");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PIPELINE_ROUTING.equals(otherTable)){
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
        PipelineRoutingDataVector v = new PipelineRoutingDataVector();
        while (rs.next()) {
            PipelineRoutingData x = PipelineRoutingData.createValue();
            
            x.setPipelineRoutingId(rs.getInt(1));
            x.setPipelineId(rs.getInt(2));
            x.setSequenceNum(rs.getInt(3));
            x.setProgramCd(rs.getString(4));
            x.setExceptionCd(rs.getString(5));
            x.setActionCd(rs.getString(6));
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
     * Gets a PipelineRoutingDataVector object that consists
     * of PipelineRoutingData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PipelineRoutingData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PipelineRoutingDataVector()
     * @throws            SQLException
     */
    public static PipelineRoutingDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PipelineRoutingDataVector v = new PipelineRoutingDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PIPELINE_ROUTING_ID,PIPELINE_ID,SEQUENCE_NUM,PROGRAM_CD,EXCEPTION_CD,ACTION_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PIPELINE_ROUTING WHERE PIPELINE_ROUTING_ID IN (");

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
            PipelineRoutingData x=null;
            while (rs.next()) {
                // build the object
                x=PipelineRoutingData.createValue();
                
                x.setPipelineRoutingId(rs.getInt(1));
                x.setPipelineId(rs.getInt(2));
                x.setSequenceNum(rs.getInt(3));
                x.setProgramCd(rs.getString(4));
                x.setExceptionCd(rs.getString(5));
                x.setActionCd(rs.getString(6));
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
     * Gets a PipelineRoutingDataVector object of all
     * PipelineRoutingData objects in the database.
     * @param pCon An open database connection.
     * @return new PipelineRoutingDataVector()
     * @throws            SQLException
     */
    public static PipelineRoutingDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PIPELINE_ROUTING_ID,PIPELINE_ID,SEQUENCE_NUM,PROGRAM_CD,EXCEPTION_CD,ACTION_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PIPELINE_ROUTING";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PipelineRoutingDataVector v = new PipelineRoutingDataVector();
        PipelineRoutingData x = null;
        while (rs.next()) {
            // build the object
            x = PipelineRoutingData.createValue();
            
            x.setPipelineRoutingId(rs.getInt(1));
            x.setPipelineId(rs.getInt(2));
            x.setSequenceNum(rs.getInt(3));
            x.setProgramCd(rs.getString(4));
            x.setExceptionCd(rs.getString(5));
            x.setActionCd(rs.getString(6));
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
     * PipelineRoutingData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PIPELINE_ROUTING_ID FROM CLW_PIPELINE_ROUTING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PIPELINE_ROUTING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PIPELINE_ROUTING");
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
     * Inserts a PipelineRoutingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PipelineRoutingData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PipelineRoutingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PipelineRoutingData insert(Connection pCon, PipelineRoutingData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PIPELINE_ROUTING_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PIPELINE_ROUTING_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPipelineRoutingId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PIPELINE_ROUTING (PIPELINE_ROUTING_ID,PIPELINE_ID,SEQUENCE_NUM,PROGRAM_CD,EXCEPTION_CD,ACTION_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPipelineRoutingId());
        if (pData.getPipelineId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getPipelineId());
        }

        pstmt.setInt(3,pData.getSequenceNum());
        pstmt.setString(4,pData.getProgramCd());
        pstmt.setString(5,pData.getExceptionCd());
        pstmt.setString(6,pData.getActionCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PIPELINE_ROUTING_ID="+pData.getPipelineRoutingId());
            log.debug("SQL:   PIPELINE_ID="+pData.getPipelineId());
            log.debug("SQL:   SEQUENCE_NUM="+pData.getSequenceNum());
            log.debug("SQL:   PROGRAM_CD="+pData.getProgramCd());
            log.debug("SQL:   EXCEPTION_CD="+pData.getExceptionCd());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
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
        pData.setPipelineRoutingId(0);
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
     * Updates a PipelineRoutingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PipelineRoutingData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PipelineRoutingData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PIPELINE_ROUTING SET PIPELINE_ID = ?,SEQUENCE_NUM = ?,PROGRAM_CD = ?,EXCEPTION_CD = ?,ACTION_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PIPELINE_ROUTING_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getPipelineId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getPipelineId());
        }

        pstmt.setInt(i++,pData.getSequenceNum());
        pstmt.setString(i++,pData.getProgramCd());
        pstmt.setString(i++,pData.getExceptionCd());
        pstmt.setString(i++,pData.getActionCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getPipelineRoutingId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PIPELINE_ID="+pData.getPipelineId());
            log.debug("SQL:   SEQUENCE_NUM="+pData.getSequenceNum());
            log.debug("SQL:   PROGRAM_CD="+pData.getProgramCd());
            log.debug("SQL:   EXCEPTION_CD="+pData.getExceptionCd());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
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
     * Deletes a PipelineRoutingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPipelineRoutingId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPipelineRoutingId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PIPELINE_ROUTING WHERE PIPELINE_ROUTING_ID = " + pPipelineRoutingId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PipelineRoutingData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PIPELINE_ROUTING");
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
     * Inserts a PipelineRoutingData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PipelineRoutingData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PipelineRoutingData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PIPELINE_ROUTING (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PIPELINE_ROUTING_ID,PIPELINE_ID,SEQUENCE_NUM,PROGRAM_CD,EXCEPTION_CD,ACTION_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPipelineRoutingId());
        if (pData.getPipelineId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getPipelineId());
        }

        pstmt.setInt(3+4,pData.getSequenceNum());
        pstmt.setString(4+4,pData.getProgramCd());
        pstmt.setString(5+4,pData.getExceptionCd());
        pstmt.setString(6+4,pData.getActionCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PipelineRoutingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PipelineRoutingData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PipelineRoutingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PipelineRoutingData insert(Connection pCon, PipelineRoutingData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PipelineRoutingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PipelineRoutingData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PipelineRoutingData pData, boolean pLogFl)
        throws SQLException {
        PipelineRoutingData oldData = null;
        if(pLogFl) {
          int id = pData.getPipelineRoutingId();
          try {
          oldData = PipelineRoutingDataAccess.select(pCon,id);
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
     * Deletes a PipelineRoutingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPipelineRoutingId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPipelineRoutingId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PIPELINE_ROUTING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PIPELINE_ROUTING d WHERE PIPELINE_ROUTING_ID = " + pPipelineRoutingId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPipelineRoutingId);
        return n;
     }

    /**
     * Deletes PipelineRoutingData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PIPELINE_ROUTING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PIPELINE_ROUTING d ");
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

