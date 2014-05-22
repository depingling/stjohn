
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkOrderAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_WORK_ORDER_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkOrderAssocData;
import com.cleanwise.service.api.value.WorkOrderAssocDataVector;
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
 * <code>WorkOrderAssocDataAccess</code>
 */
public class WorkOrderAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkOrderAssocDataAccess.class.getName());

    /** <code>CLW_WORK_ORDER_ASSOC</code> table name */
	/* Primary key: WORK_ORDER_ASSOC_ID */
	
    public static final String CLW_WORK_ORDER_ASSOC = "CLW_WORK_ORDER_ASSOC";
    
    /** <code>WORK_ORDER_ASSOC_ID</code> WORK_ORDER_ASSOC_ID column of table CLW_WORK_ORDER_ASSOC */
    public static final String WORK_ORDER_ASSOC_ID = "WORK_ORDER_ASSOC_ID";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORK_ORDER_ASSOC */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_WORK_ORDER_ASSOC */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>WORK_ORDER_ASSOC_CD</code> WORK_ORDER_ASSOC_CD column of table CLW_WORK_ORDER_ASSOC */
    public static final String WORK_ORDER_ASSOC_CD = "WORK_ORDER_ASSOC_CD";
    /** <code>WORK_ORDER_ASSOC_STATUS_CD</code> WORK_ORDER_ASSOC_STATUS_CD column of table CLW_WORK_ORDER_ASSOC */
    public static final String WORK_ORDER_ASSOC_STATUS_CD = "WORK_ORDER_ASSOC_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORK_ORDER_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORK_ORDER_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORK_ORDER_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORK_ORDER_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public WorkOrderAssocDataAccess()
    {
    }

    /**
     * Gets a WorkOrderAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkOrderAssocId The key requested.
     * @return new WorkOrderAssocData()
     * @throws            SQLException
     */
    public static WorkOrderAssocData select(Connection pCon, int pWorkOrderAssocId)
        throws SQLException, DataNotFoundException {
        WorkOrderAssocData x=null;
        String sql="SELECT WORK_ORDER_ASSOC_ID,WORK_ORDER_ID,BUS_ENTITY_ID,WORK_ORDER_ASSOC_CD,WORK_ORDER_ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_ASSOC WHERE WORK_ORDER_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkOrderAssocId=" + pWorkOrderAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkOrderAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkOrderAssocData.createValue();
            
            x.setWorkOrderAssocId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setWorkOrderAssocCd(rs.getString(4));
            x.setWorkOrderAssocStatusCd(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORK_ORDER_ASSOC_ID :" + pWorkOrderAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkOrderAssocDataVector object that consists
     * of WorkOrderAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkOrderAssocDataVector()
     * @throws            SQLException
     */
    public static WorkOrderAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkOrderAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORK_ORDER_ASSOC.WORK_ORDER_ASSOC_ID,CLW_WORK_ORDER_ASSOC.WORK_ORDER_ID,CLW_WORK_ORDER_ASSOC.BUS_ENTITY_ID,CLW_WORK_ORDER_ASSOC.WORK_ORDER_ASSOC_CD,CLW_WORK_ORDER_ASSOC.WORK_ORDER_ASSOC_STATUS_CD,CLW_WORK_ORDER_ASSOC.ADD_DATE,CLW_WORK_ORDER_ASSOC.ADD_BY,CLW_WORK_ORDER_ASSOC.MOD_DATE,CLW_WORK_ORDER_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkOrderAssocData Object.
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
    *@returns a populated WorkOrderAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkOrderAssocData x = WorkOrderAssocData.createValue();
         
         x.setWorkOrderAssocId(rs.getInt(1+offset));
         x.setWorkOrderId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setWorkOrderAssocCd(rs.getString(4+offset));
         x.setWorkOrderAssocStatusCd(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkOrderAssocData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a WorkOrderAssocDataVector object that consists
     * of WorkOrderAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkOrderAssocDataVector()
     * @throws            SQLException
     */
    public static WorkOrderAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORK_ORDER_ASSOC_ID,WORK_ORDER_ID,BUS_ENTITY_ID,WORK_ORDER_ASSOC_CD,WORK_ORDER_ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORK_ORDER_ASSOC.WORK_ORDER_ASSOC_ID,CLW_WORK_ORDER_ASSOC.WORK_ORDER_ID,CLW_WORK_ORDER_ASSOC.BUS_ENTITY_ID,CLW_WORK_ORDER_ASSOC.WORK_ORDER_ASSOC_CD,CLW_WORK_ORDER_ASSOC.WORK_ORDER_ASSOC_STATUS_CD,CLW_WORK_ORDER_ASSOC.ADD_DATE,CLW_WORK_ORDER_ASSOC.ADD_BY,CLW_WORK_ORDER_ASSOC.MOD_DATE,CLW_WORK_ORDER_ASSOC.MOD_BY FROM CLW_WORK_ORDER_ASSOC");
                where = pCriteria.getSqlClause("CLW_WORK_ORDER_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORK_ORDER_ASSOC.equals(otherTable)){
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
        WorkOrderAssocDataVector v = new WorkOrderAssocDataVector();
        while (rs.next()) {
            WorkOrderAssocData x = WorkOrderAssocData.createValue();
            
            x.setWorkOrderAssocId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setWorkOrderAssocCd(rs.getString(4));
            x.setWorkOrderAssocStatusCd(rs.getString(5));
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
     * Gets a WorkOrderAssocDataVector object that consists
     * of WorkOrderAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkOrderAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkOrderAssocDataVector()
     * @throws            SQLException
     */
    public static WorkOrderAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkOrderAssocDataVector v = new WorkOrderAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_ASSOC_ID,WORK_ORDER_ID,BUS_ENTITY_ID,WORK_ORDER_ASSOC_CD,WORK_ORDER_ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_ASSOC WHERE WORK_ORDER_ASSOC_ID IN (");

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
            WorkOrderAssocData x=null;
            while (rs.next()) {
                // build the object
                x=WorkOrderAssocData.createValue();
                
                x.setWorkOrderAssocId(rs.getInt(1));
                x.setWorkOrderId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setWorkOrderAssocCd(rs.getString(4));
                x.setWorkOrderAssocStatusCd(rs.getString(5));
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
     * Gets a WorkOrderAssocDataVector object of all
     * WorkOrderAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkOrderAssocDataVector()
     * @throws            SQLException
     */
    public static WorkOrderAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORK_ORDER_ASSOC_ID,WORK_ORDER_ID,BUS_ENTITY_ID,WORK_ORDER_ASSOC_CD,WORK_ORDER_ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORK_ORDER_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkOrderAssocDataVector v = new WorkOrderAssocDataVector();
        WorkOrderAssocData x = null;
        while (rs.next()) {
            // build the object
            x = WorkOrderAssocData.createValue();
            
            x.setWorkOrderAssocId(rs.getInt(1));
            x.setWorkOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setWorkOrderAssocCd(rs.getString(4));
            x.setWorkOrderAssocStatusCd(rs.getString(5));
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
     * WorkOrderAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORK_ORDER_ASSOC_ID FROM CLW_WORK_ORDER_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORK_ORDER_ASSOC");
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
     * Inserts a WorkOrderAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkOrderAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderAssocData insert(Connection pCon, WorkOrderAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORK_ORDER_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORK_ORDER_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkOrderAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORK_ORDER_ASSOC (WORK_ORDER_ASSOC_ID,WORK_ORDER_ID,BUS_ENTITY_ID,WORK_ORDER_ASSOC_CD,WORK_ORDER_ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkOrderAssocId());
        pstmt.setInt(2,pData.getWorkOrderId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setString(4,pData.getWorkOrderAssocCd());
        pstmt.setString(5,pData.getWorkOrderAssocStatusCd());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ASSOC_ID="+pData.getWorkOrderAssocId());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   WORK_ORDER_ASSOC_CD="+pData.getWorkOrderAssocCd());
            log.debug("SQL:   WORK_ORDER_ASSOC_STATUS_CD="+pData.getWorkOrderAssocStatusCd());
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
        pData.setWorkOrderAssocId(0);
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
     * Updates a WorkOrderAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORK_ORDER_ASSOC SET WORK_ORDER_ID = ?,BUS_ENTITY_ID = ?,WORK_ORDER_ASSOC_CD = ?,WORK_ORDER_ASSOC_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE WORK_ORDER_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWorkOrderId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getWorkOrderAssocCd());
        pstmt.setString(i++,pData.getWorkOrderAssocStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkOrderAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   WORK_ORDER_ASSOC_CD="+pData.getWorkOrderAssocCd());
            log.debug("SQL:   WORK_ORDER_ASSOC_STATUS_CD="+pData.getWorkOrderAssocStatusCd());
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
     * Deletes a WorkOrderAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORK_ORDER_ASSOC WHERE WORK_ORDER_ASSOC_ID = " + pWorkOrderAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkOrderAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORK_ORDER_ASSOC");
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
     * Inserts a WorkOrderAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkOrderAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORK_ORDER_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORK_ORDER_ASSOC_ID,WORK_ORDER_ID,BUS_ENTITY_ID,WORK_ORDER_ASSOC_CD,WORK_ORDER_ASSOC_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkOrderAssocId());
        pstmt.setInt(2+4,pData.getWorkOrderId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setString(4+4,pData.getWorkOrderAssocCd());
        pstmt.setString(5+4,pData.getWorkOrderAssocStatusCd());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkOrderAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkOrderAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkOrderAssocData insert(Connection pCon, WorkOrderAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkOrderAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkOrderAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkOrderAssocData pData, boolean pLogFl)
        throws SQLException {
        WorkOrderAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkOrderAssocId();
          try {
          oldData = WorkOrderAssocDataAccess.select(pCon,id);
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
     * Deletes a WorkOrderAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkOrderAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkOrderAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORK_ORDER_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_ASSOC d WHERE WORK_ORDER_ASSOC_ID = " + pWorkOrderAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkOrderAssocId);
        return n;
     }

    /**
     * Deletes WorkOrderAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORK_ORDER_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORK_ORDER_ASSOC d ");
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

