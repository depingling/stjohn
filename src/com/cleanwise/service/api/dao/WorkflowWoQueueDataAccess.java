
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkflowWoQueueDataAccess
 * Description:  This class is used to build access methods to the CLW_WORKFLOW_WO_QUEUE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkflowWoQueueData;
import com.cleanwise.service.api.value.WorkflowWoQueueDataVector;
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
 * <code>WorkflowWoQueueDataAccess</code>
 */
public class WorkflowWoQueueDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkflowWoQueueDataAccess.class.getName());

    /** <code>CLW_WORKFLOW_WO_QUEUE</code> table name */
	/* Primary key: WORKFLOW_WO_QUEUE_ID */
	
    public static final String CLW_WORKFLOW_WO_QUEUE = "CLW_WORKFLOW_WO_QUEUE";
    
    /** <code>WORKFLOW_WO_QUEUE_ID</code> WORKFLOW_WO_QUEUE_ID column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String WORKFLOW_WO_QUEUE_ID = "WORKFLOW_WO_QUEUE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>WORK_ORDER_ID</code> WORK_ORDER_ID column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String WORK_ORDER_ID = "WORK_ORDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>WORKFLOW_ROLE_CD</code> WORKFLOW_ROLE_CD column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String WORKFLOW_ROLE_CD = "WORKFLOW_ROLE_CD";
    /** <code>WORKFLOW_ID</code> WORKFLOW_ID column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    /** <code>WORKFLOW_RULE_ID</code> WORKFLOW_RULE_ID column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String WORKFLOW_RULE_ID = "WORKFLOW_RULE_ID";
    /** <code>ACTION_DAYS</code> ACTION_DAYS column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String ACTION_DAYS = "ACTION_DAYS";
    /** <code>ACTION_TYPE</code> ACTION_TYPE column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String ACTION_TYPE = "ACTION_TYPE";
    /** <code>STATUS_CD</code> STATUS_CD column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String STATUS_CD = "STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORKFLOW_WO_QUEUE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public WorkflowWoQueueDataAccess()
    {
    }

    /**
     * Gets a WorkflowWoQueueData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkflowWoQueueId The key requested.
     * @return new WorkflowWoQueueData()
     * @throws            SQLException
     */
    public static WorkflowWoQueueData select(Connection pCon, int pWorkflowWoQueueId)
        throws SQLException, DataNotFoundException {
        WorkflowWoQueueData x=null;
        String sql="SELECT WORKFLOW_WO_QUEUE_ID,SHORT_DESC,WORK_ORDER_ID,BUS_ENTITY_ID,WORKFLOW_ROLE_CD,WORKFLOW_ID,WORKFLOW_RULE_ID,ACTION_DAYS,ACTION_TYPE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_WO_QUEUE WHERE WORKFLOW_WO_QUEUE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkflowWoQueueId=" + pWorkflowWoQueueId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkflowWoQueueId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkflowWoQueueData.createValue();
            
            x.setWorkflowWoQueueId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setWorkOrderId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setWorkflowRoleCd(rs.getString(5));
            x.setWorkflowId(rs.getInt(6));
            x.setWorkflowRuleId(rs.getInt(7));
            x.setActionDays(rs.getInt(8));
            x.setActionType(rs.getString(9));
            x.setStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORKFLOW_WO_QUEUE_ID :" + pWorkflowWoQueueId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkflowWoQueueDataVector object that consists
     * of WorkflowWoQueueData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkflowWoQueueDataVector()
     * @throws            SQLException
     */
    public static WorkflowWoQueueDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkflowWoQueueData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORKFLOW_WO_QUEUE.WORKFLOW_WO_QUEUE_ID,CLW_WORKFLOW_WO_QUEUE.SHORT_DESC,CLW_WORKFLOW_WO_QUEUE.WORK_ORDER_ID,CLW_WORKFLOW_WO_QUEUE.BUS_ENTITY_ID,CLW_WORKFLOW_WO_QUEUE.WORKFLOW_ROLE_CD,CLW_WORKFLOW_WO_QUEUE.WORKFLOW_ID,CLW_WORKFLOW_WO_QUEUE.WORKFLOW_RULE_ID,CLW_WORKFLOW_WO_QUEUE.ACTION_DAYS,CLW_WORKFLOW_WO_QUEUE.ACTION_TYPE,CLW_WORKFLOW_WO_QUEUE.STATUS_CD,CLW_WORKFLOW_WO_QUEUE.ADD_DATE,CLW_WORKFLOW_WO_QUEUE.ADD_BY,CLW_WORKFLOW_WO_QUEUE.MOD_DATE,CLW_WORKFLOW_WO_QUEUE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkflowWoQueueData Object.
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
    *@returns a populated WorkflowWoQueueData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkflowWoQueueData x = WorkflowWoQueueData.createValue();
         
         x.setWorkflowWoQueueId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setWorkOrderId(rs.getInt(3+offset));
         x.setBusEntityId(rs.getInt(4+offset));
         x.setWorkflowRoleCd(rs.getString(5+offset));
         x.setWorkflowId(rs.getInt(6+offset));
         x.setWorkflowRuleId(rs.getInt(7+offset));
         x.setActionDays(rs.getInt(8+offset));
         x.setActionType(rs.getString(9+offset));
         x.setStatusCd(rs.getString(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkflowWoQueueData Object represents.
    */
    public int getColumnCount(){
        return 14;
    }

    /**
     * Gets a WorkflowWoQueueDataVector object that consists
     * of WorkflowWoQueueData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkflowWoQueueDataVector()
     * @throws            SQLException
     */
    public static WorkflowWoQueueDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORKFLOW_WO_QUEUE_ID,SHORT_DESC,WORK_ORDER_ID,BUS_ENTITY_ID,WORKFLOW_ROLE_CD,WORKFLOW_ID,WORKFLOW_RULE_ID,ACTION_DAYS,ACTION_TYPE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_WO_QUEUE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORKFLOW_WO_QUEUE.WORKFLOW_WO_QUEUE_ID,CLW_WORKFLOW_WO_QUEUE.SHORT_DESC,CLW_WORKFLOW_WO_QUEUE.WORK_ORDER_ID,CLW_WORKFLOW_WO_QUEUE.BUS_ENTITY_ID,CLW_WORKFLOW_WO_QUEUE.WORKFLOW_ROLE_CD,CLW_WORKFLOW_WO_QUEUE.WORKFLOW_ID,CLW_WORKFLOW_WO_QUEUE.WORKFLOW_RULE_ID,CLW_WORKFLOW_WO_QUEUE.ACTION_DAYS,CLW_WORKFLOW_WO_QUEUE.ACTION_TYPE,CLW_WORKFLOW_WO_QUEUE.STATUS_CD,CLW_WORKFLOW_WO_QUEUE.ADD_DATE,CLW_WORKFLOW_WO_QUEUE.ADD_BY,CLW_WORKFLOW_WO_QUEUE.MOD_DATE,CLW_WORKFLOW_WO_QUEUE.MOD_BY FROM CLW_WORKFLOW_WO_QUEUE");
                where = pCriteria.getSqlClause("CLW_WORKFLOW_WO_QUEUE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORKFLOW_WO_QUEUE.equals(otherTable)){
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
        WorkflowWoQueueDataVector v = new WorkflowWoQueueDataVector();
        while (rs.next()) {
            WorkflowWoQueueData x = WorkflowWoQueueData.createValue();
            
            x.setWorkflowWoQueueId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setWorkOrderId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setWorkflowRoleCd(rs.getString(5));
            x.setWorkflowId(rs.getInt(6));
            x.setWorkflowRuleId(rs.getInt(7));
            x.setActionDays(rs.getInt(8));
            x.setActionType(rs.getString(9));
            x.setStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkflowWoQueueDataVector object that consists
     * of WorkflowWoQueueData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkflowWoQueueData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkflowWoQueueDataVector()
     * @throws            SQLException
     */
    public static WorkflowWoQueueDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkflowWoQueueDataVector v = new WorkflowWoQueueDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_WO_QUEUE_ID,SHORT_DESC,WORK_ORDER_ID,BUS_ENTITY_ID,WORKFLOW_ROLE_CD,WORKFLOW_ID,WORKFLOW_RULE_ID,ACTION_DAYS,ACTION_TYPE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_WO_QUEUE WHERE WORKFLOW_WO_QUEUE_ID IN (");

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
            WorkflowWoQueueData x=null;
            while (rs.next()) {
                // build the object
                x=WorkflowWoQueueData.createValue();
                
                x.setWorkflowWoQueueId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setWorkOrderId(rs.getInt(3));
                x.setBusEntityId(rs.getInt(4));
                x.setWorkflowRoleCd(rs.getString(5));
                x.setWorkflowId(rs.getInt(6));
                x.setWorkflowRuleId(rs.getInt(7));
                x.setActionDays(rs.getInt(8));
                x.setActionType(rs.getString(9));
                x.setStatusCd(rs.getString(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkflowWoQueueDataVector object of all
     * WorkflowWoQueueData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkflowWoQueueDataVector()
     * @throws            SQLException
     */
    public static WorkflowWoQueueDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORKFLOW_WO_QUEUE_ID,SHORT_DESC,WORK_ORDER_ID,BUS_ENTITY_ID,WORKFLOW_ROLE_CD,WORKFLOW_ID,WORKFLOW_RULE_ID,ACTION_DAYS,ACTION_TYPE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_WO_QUEUE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkflowWoQueueDataVector v = new WorkflowWoQueueDataVector();
        WorkflowWoQueueData x = null;
        while (rs.next()) {
            // build the object
            x = WorkflowWoQueueData.createValue();
            
            x.setWorkflowWoQueueId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setWorkOrderId(rs.getInt(3));
            x.setBusEntityId(rs.getInt(4));
            x.setWorkflowRoleCd(rs.getString(5));
            x.setWorkflowId(rs.getInt(6));
            x.setWorkflowRuleId(rs.getInt(7));
            x.setActionDays(rs.getInt(8));
            x.setActionType(rs.getString(9));
            x.setStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkflowWoQueueData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_WO_QUEUE_ID FROM CLW_WORKFLOW_WO_QUEUE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_WO_QUEUE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_WO_QUEUE");
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
     * Inserts a WorkflowWoQueueData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowWoQueueData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkflowWoQueueData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowWoQueueData insert(Connection pCon, WorkflowWoQueueData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORKFLOW_WO_QUEUE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORKFLOW_WO_QUEUE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkflowWoQueueId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORKFLOW_WO_QUEUE (WORKFLOW_WO_QUEUE_ID,SHORT_DESC,WORK_ORDER_ID,BUS_ENTITY_ID,WORKFLOW_ROLE_CD,WORKFLOW_ID,WORKFLOW_RULE_ID,ACTION_DAYS,ACTION_TYPE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkflowWoQueueId());
        pstmt.setString(2,pData.getShortDesc());
        if (pData.getWorkOrderId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getWorkOrderId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getBusEntityId());
        }

        pstmt.setString(5,pData.getWorkflowRoleCd());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(6, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(6,pData.getWorkflowId());
        }

        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(7, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(7,pData.getWorkflowRuleId());
        }

        pstmt.setInt(8,pData.getActionDays());
        pstmt.setString(9,pData.getActionType());
        pstmt.setString(10,pData.getStatusCd());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_WO_QUEUE_ID="+pData.getWorkflowWoQueueId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   WORKFLOW_ROLE_CD="+pData.getWorkflowRoleCd());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   ACTION_DAYS="+pData.getActionDays());
            log.debug("SQL:   ACTION_TYPE="+pData.getActionType());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
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
        pData.setWorkflowWoQueueId(0);
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
     * Updates a WorkflowWoQueueData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowWoQueueData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowWoQueueData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORKFLOW_WO_QUEUE SET SHORT_DESC = ?,WORK_ORDER_ID = ?,BUS_ENTITY_ID = ?,WORKFLOW_ROLE_CD = ?,WORKFLOW_ID = ?,WORKFLOW_RULE_ID = ?,ACTION_DAYS = ?,ACTION_TYPE = ?,STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE WORKFLOW_WO_QUEUE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        if (pData.getWorkOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkOrderId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getWorkflowRoleCd());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkflowId());
        }

        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkflowRuleId());
        }

        pstmt.setInt(i++,pData.getActionDays());
        pstmt.setString(i++,pData.getActionType());
        pstmt.setString(i++,pData.getStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkflowWoQueueId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   WORK_ORDER_ID="+pData.getWorkOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   WORKFLOW_ROLE_CD="+pData.getWorkflowRoleCd());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   ACTION_DAYS="+pData.getActionDays());
            log.debug("SQL:   ACTION_TYPE="+pData.getActionType());
            log.debug("SQL:   STATUS_CD="+pData.getStatusCd());
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
     * Deletes a WorkflowWoQueueData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowWoQueueId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowWoQueueId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORKFLOW_WO_QUEUE WHERE WORKFLOW_WO_QUEUE_ID = " + pWorkflowWoQueueId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkflowWoQueueData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORKFLOW_WO_QUEUE");
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
     * Inserts a WorkflowWoQueueData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowWoQueueData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkflowWoQueueData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORKFLOW_WO_QUEUE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORKFLOW_WO_QUEUE_ID,SHORT_DESC,WORK_ORDER_ID,BUS_ENTITY_ID,WORKFLOW_ROLE_CD,WORKFLOW_ID,WORKFLOW_RULE_ID,ACTION_DAYS,ACTION_TYPE,STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkflowWoQueueId());
        pstmt.setString(2+4,pData.getShortDesc());
        if (pData.getWorkOrderId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getWorkOrderId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getBusEntityId());
        }

        pstmt.setString(5+4,pData.getWorkflowRoleCd());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(6+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(6+4,pData.getWorkflowId());
        }

        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(7+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(7+4,pData.getWorkflowRuleId());
        }

        pstmt.setInt(8+4,pData.getActionDays());
        pstmt.setString(9+4,pData.getActionType());
        pstmt.setString(10+4,pData.getStatusCd());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkflowWoQueueData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowWoQueueData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkflowWoQueueData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowWoQueueData insert(Connection pCon, WorkflowWoQueueData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkflowWoQueueData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowWoQueueData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowWoQueueData pData, boolean pLogFl)
        throws SQLException {
        WorkflowWoQueueData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkflowWoQueueId();
          try {
          oldData = WorkflowWoQueueDataAccess.select(pCon,id);
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
     * Deletes a WorkflowWoQueueData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowWoQueueId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowWoQueueId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORKFLOW_WO_QUEUE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_WO_QUEUE d WHERE WORKFLOW_WO_QUEUE_ID = " + pWorkflowWoQueueId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkflowWoQueueId);
        return n;
     }

    /**
     * Deletes WorkflowWoQueueData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORKFLOW_WO_QUEUE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_WO_QUEUE d ");
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

