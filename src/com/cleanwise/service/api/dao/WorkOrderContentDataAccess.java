
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkOrderContentDataAccess
 * Description:  This class is used to build access methods to the CLW_WORK_ORDER_CONTENT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkOrderContentData;
import com.cleanwise.service.api.value.WorkOrderContentDataVector;
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
 * <code>WorkOrderContentDataAccess</code>
 */
public class WorkOrderContentDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkOrderContentDataAccess.class.getName());

    /** <code>CLW_WORK_ORDER_CONTENT</code> table name */
	/* Primary key: WORK_ORDER_CONTENT_ID */
	
    public static final String CLW_WORK_ORDER_CONTENT = "CLW_WORK_ORDER_CONTENT";
    
    /** <code>WORK_ORDER_CONTENT_ID</code> WORK_ORDER_CONTENT_ID column of table CLW_WORK_ORDER_CONTENT */
    public static final String WORK_ORDER_CONTENT_ID = "WORK_ORDER_CONTENT_ID";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORK_ORDER_CONTENT */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>WORK_ORDER_ITEM_ID</code> WORK_ORDER_ITEM_ID column of table CLW_WORK_ORDER_CONTENT */
    public static final String WORK_ORDER_ITEM_ID = "WORK_ORDER_ITEM_ID";
    /** <code>CONTENT_ID</code> CONTENT_ID column of table CLW_WORK_ORDER_CONTENT */
    public static final String CONTENT_ID = "CONTENT_ID";
    /** <code>URL</code> URL column of table CLW_WORK_ORDER_CONTENT */
    public static final String URL = "URL";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORK_ORDER_CONTENT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORK_ORDER_CONTENT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORK_ORDER_CONTENT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORK_ORDER_CONTENT */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public WorkOrderContentDataAccess()
    {
    }

    /**
     * Gets a WorkOrderContentData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkOrderContentId The key requested.
     * @return new WorkOrderContentData()
     * @throws            SQLException
     */
    public static WorkOrderContentData select(Connection pCon, int pWorkOrderContentId)
        throws SQLException, DataNotFoundException {
        WorkOrderContentData x=null;
        String sql="SELECT WORK_ORDER_CONTENT_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,CONTENT_ID,URL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_CONTENT WHERE WORK_ORDER_CONTENT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkOrderContentId=" + pWorkOrderContentId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkOrderContentId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkOrderContentData.createValue();
            
            x.setWorkOrderContentId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWorkOrderItemId(rs.getInt(3));
            x.setContentId(rs.getInt(4));
            x.setUrl(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORK_ORDER_CONTENT_ID :" + pWorkOrderContentId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkOrderContentDataVector object that consists
     * of WorkOrderContentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkOrderContentDataVector()
     * @throws            SQLException
     */
    public static WorkOrderContentDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkOrderContentData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORK_ORDER_CONTENT.WORK_ORDER_CONTENT_ID,CLW_WORK_ORDER_CONTENT.WORK_ORDER_ID,CLW_WORK_ORDER_CONTENT.WORK_ORDER_ITEM_ID,CLW_WORK_ORDER_CONTENT.CONTENT_ID,CLW_WORK_ORDER_CONTENT.URL,CLW_WORK_ORDER_CONTENT.ADD_DATE,CLW_WORK_ORDER_CONTENT.ADD_BY,CLW_WORK_ORDER_CONTENT.MOD_DATE,CLW_WORK_ORDER_CONTENT.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkOrderContentData Object.
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
    *@returns a populated WorkOrderContentData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkOrderContentData x = WorkOrderContentData.createValue();
         
         x.setWorkOrderContentId(rs.getInt(1+offset));
         x.setWorkOrderId(rs.getInt(2+offset));
         x.setWorkOrderItemId(rs.getInt(3+offset));
         x.setContentId(rs.getInt(4+offset));
         x.setUrl(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkOrderContentData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a WorkOrderContentDataVector object that consists
     * of WorkOrderContentData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkOrderContentDataVector()
     * @throws            SQLException
     */
    public static WorkOrderContentDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORK_ORDER_CONTENT_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,CONTENT_ID,URL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_CONTENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORK_ORDER_CONTENT.WORK_ORDER_CONTENT_ID,CLW_WORK_ORDER_CONTENT.WORK_ORDER_ID,CLW_WORK_ORDER_CONTENT.WORK_ORDER_ITEM_ID,CLW_WORK_ORDER_CONTENT.CONTENT_ID,CLW_WORK_ORDER_CONTENT.URL,CLW_WORK_ORDER_CONTENT.ADD_DATE,CLW_WORK_ORDER_CONTENT.ADD_BY,CLW_WORK_ORDER_CONTENT.MOD_DATE,CLW_WORK_ORDER_CONTENT.MOD_BY FROM CLW_WORK_ORDER_CONTENT");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER_CONTENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER_CONTENT.equals(otherTable)){
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
        WorkOrderContentDataVector v = new WorkOrderContentDataVector();
        while (rs.next()) {
            WorkOrderContentData x = WorkOrderContentData.createValue();
            
            x.setWorkOrderContentId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWorkOrderItemId(rs.getInt(3));
            x.setContentId(rs.getInt(4));
            x.setUrl(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkOrderContentDataVector object that consists
     * of WorkOrderContentData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkOrderContentData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkOrderContentDataVector()
     * @throws            SQLException
     */
    public static WorkOrderContentDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkOrderContentDataVector v = new WorkOrderContentDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_CONTENT_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,CONTENT_ID,URL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_CONTENT WHERE WORK_ORDER_CONTENT_ID IN (");

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
            WorkOrderContentData x=null;
            while (rs.next()) {
                // build the object
                x=WorkOrderContentData.createValue();
                
                x.setWorkOrderContentId(rs.getInt(1));
                x.setWorkOrderId(rs.getInt(2));
                x.setWorkOrderItemId(rs.getInt(3));
                x.setContentId(rs.getInt(4));
                x.setUrl(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkOrderContentDataVector object of all
     * WorkOrderContentData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkOrderContentDataVector()
     * @throws            SQLException
     */
    public static WorkOrderContentDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORK_ORDER_CONTENT_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,CONTENT_ID,URL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_CONTENT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderContentDataVector v = new WorkOrderContentDataVector();
        WorkOrderContentData x = null;
        while (rs.next()) {
            // build the object
            x = WorkOrderContentData.createValue();
            
            x.setWorkOrderContentId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setWorkOrderItemId(rs.getInt(3));
            x.setContentId(rs.getInt(4));
            x.setUrl(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkOrderContentData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_CONTENT_ID FROM CLW_WORK_ORDER_CONTENT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_CONTENT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_CONTENT");
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
     * Inserts a WorkOrderContentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderContentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkOrderContentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderContentData insert(Connection pCon, WorkOrderContentData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORK_ORDER_CONTENT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_CONTENT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkOrderContentId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORK_ORDER_CONTENT (WORK_ORDER_CONTENT_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,CONTENT_ID,URL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkOrderContentId());
        pstmt.setInt(2,pData.getWorkOrderId());
        if (pData.getWorkOrderItemId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getWorkOrderItemId());
        }

        if (pData.getContentId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getContentId());
        }

        pstmt.setString(5,pData.getUrl());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_CONTENT_ID="+pData.getWorkOrderContentId());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   WORK_ORDER_ITEM_ID="+pData.getWorkOrderItemId());
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   URL="+pData.getUrl());
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
        pData.setWorkOrderContentId(0);
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
     * Updates a WorkOrderContentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderContentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderContentData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORK_ORDER_CONTENT SET WORK_ORDER_ID = ?,WORK_ORDER_ITEM_ID = ?,CONTENT_ID = ?,URL = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE WORK_ORDER_CONTENT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWorkOrderId());
        if (pData.getWorkOrderItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkOrderItemId());
        }

        if (pData.getContentId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getContentId());
        }

        pstmt.setString(i++,pData.getUrl());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkOrderContentId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   WORK_ORDER_ITEM_ID="+pData.getWorkOrderItemId());
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   URL="+pData.getUrl());
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
     * Deletes a WorkOrderContentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderContentId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderContentId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORK_ORDER_CONTENT WHERE WORK_ORDER_CONTENT_ID = " + pWorkOrderContentId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkOrderContentData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORK_ORDER_CONTENT");
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
     * Inserts a WorkOrderContentData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderContentData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkOrderContentData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORK_ORDER_CONTENT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORK_ORDER_CONTENT_ID,WORK_ORDER_ID,WORK_ORDER_ITEM_ID,CONTENT_ID,URL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkOrderContentId());
        pstmt.setInt(2+4,pData.getWorkOrderId());
        if (pData.getWorkOrderItemId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getWorkOrderItemId());
        }

        if (pData.getContentId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getContentId());
        }

        pstmt.setString(5+4,pData.getUrl());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkOrderContentData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderContentData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkOrderContentData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderContentData insert(Connection pCon, WorkOrderContentData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkOrderContentData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderContentData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderContentData pData, boolean pLogFl)
        throws SQLException {
        WorkOrderContentData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkOrderContentId();
          try {
          oldData = WorkOrderContentDataAccess.select(pCon,id);
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
     * Deletes a WorkOrderContentData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderContentId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderContentId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORK_ORDER_CONTENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_CONTENT d WHERE WORK_ORDER_CONTENT_ID = " + pWorkOrderContentId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkOrderContentId);
        return n;
     }

    /**
     * Deletes WorkOrderContentData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORK_ORDER_CONTENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_CONTENT d ");
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

