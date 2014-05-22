
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkflowRuleDataAccess
 * Description:  This class is used to build access methods to the CLW_WORKFLOW_RULE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
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
 * <code>WorkflowRuleDataAccess</code>
 */
public class WorkflowRuleDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkflowRuleDataAccess.class.getName());

    /** <code>CLW_WORKFLOW_RULE</code> table name */
	/* Primary key: WORKFLOW_RULE_ID */
	
    public static final String CLW_WORKFLOW_RULE = "CLW_WORKFLOW_RULE";
    
    /** <code>WORKFLOW_RULE_ID</code> WORKFLOW_RULE_ID column of table CLW_WORKFLOW_RULE */
    public static final String WORKFLOW_RULE_ID = "WORKFLOW_RULE_ID";
    /** <code>RULE_SEQ</code> RULE_SEQ column of table CLW_WORKFLOW_RULE */
    public static final String RULE_SEQ = "RULE_SEQ";
    /** <code>WORKFLOW_ID</code> WORKFLOW_ID column of table CLW_WORKFLOW_RULE */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    /** <code>RULE_TYPE_CD</code> RULE_TYPE_CD column of table CLW_WORKFLOW_RULE */
    public static final String RULE_TYPE_CD = "RULE_TYPE_CD";
    /** <code>RULE_EXP</code> RULE_EXP column of table CLW_WORKFLOW_RULE */
    public static final String RULE_EXP = "RULE_EXP";
    /** <code>RULE_EXP_VALUE</code> RULE_EXP_VALUE column of table CLW_WORKFLOW_RULE */
    public static final String RULE_EXP_VALUE = "RULE_EXP_VALUE";
    /** <code>RULE_ACTION</code> RULE_ACTION column of table CLW_WORKFLOW_RULE */
    public static final String RULE_ACTION = "RULE_ACTION";
    /** <code>NEXT_ACTION_CD</code> NEXT_ACTION_CD column of table CLW_WORKFLOW_RULE */
    public static final String NEXT_ACTION_CD = "NEXT_ACTION_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORKFLOW_RULE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>WORKFLOW_RULE_STATUS_CD</code> WORKFLOW_RULE_STATUS_CD column of table CLW_WORKFLOW_RULE */
    public static final String WORKFLOW_RULE_STATUS_CD = "WORKFLOW_RULE_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORKFLOW_RULE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORKFLOW_RULE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORKFLOW_RULE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORKFLOW_RULE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>APPROVER_GROUP_ID</code> APPROVER_GROUP_ID column of table CLW_WORKFLOW_RULE */
    public static final String APPROVER_GROUP_ID = "APPROVER_GROUP_ID";
    /** <code>EMAIL_GROUP_ID</code> EMAIL_GROUP_ID column of table CLW_WORKFLOW_RULE */
    public static final String EMAIL_GROUP_ID = "EMAIL_GROUP_ID";
    /** <code>RULE_GROUP</code> RULE_GROUP column of table CLW_WORKFLOW_RULE */
    public static final String RULE_GROUP = "RULE_GROUP";
    /** <code>WARNING_MESSAGE</code> WARNING_MESSAGE column of table CLW_WORKFLOW_RULE */
    public static final String WARNING_MESSAGE = "WARNING_MESSAGE";

    /**
     * Constructor.
     */
    public WorkflowRuleDataAccess()
    {
    }

    /**
     * Gets a WorkflowRuleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkflowRuleId The key requested.
     * @return new WorkflowRuleData()
     * @throws            SQLException
     */
    public static WorkflowRuleData select(Connection pCon, int pWorkflowRuleId)
        throws SQLException, DataNotFoundException {
        WorkflowRuleData x=null;
        String sql="SELECT WORKFLOW_RULE_ID,RULE_SEQ,WORKFLOW_ID,RULE_TYPE_CD,RULE_EXP,RULE_EXP_VALUE,RULE_ACTION,NEXT_ACTION_CD,SHORT_DESC,WORKFLOW_RULE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,APPROVER_GROUP_ID,EMAIL_GROUP_ID,RULE_GROUP,WARNING_MESSAGE FROM CLW_WORKFLOW_RULE WHERE WORKFLOW_RULE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkflowRuleId=" + pWorkflowRuleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkflowRuleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkflowRuleData.createValue();
            
            x.setWorkflowRuleId(rs.getInt(1));
            x.setRuleSeq(rs.getInt(2));
            x.setWorkflowId(rs.getInt(3));
            x.setRuleTypeCd(rs.getString(4));
            x.setRuleExp(rs.getString(5));
            x.setRuleExpValue(rs.getString(6));
            x.setRuleAction(rs.getString(7));
            x.setNextActionCd(rs.getString(8));
            x.setShortDesc(rs.getString(9));
            x.setWorkflowRuleStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setApproverGroupId(rs.getInt(15));
            x.setEmailGroupId(rs.getInt(16));
            x.setRuleGroup(rs.getString(17));
            x.setWarningMessage(rs.getString(18));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORKFLOW_RULE_ID :" + pWorkflowRuleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkflowRuleDataVector object that consists
     * of WorkflowRuleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkflowRuleDataVector()
     * @throws            SQLException
     */
    public static WorkflowRuleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkflowRuleData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORKFLOW_RULE.WORKFLOW_RULE_ID,CLW_WORKFLOW_RULE.RULE_SEQ,CLW_WORKFLOW_RULE.WORKFLOW_ID,CLW_WORKFLOW_RULE.RULE_TYPE_CD,CLW_WORKFLOW_RULE.RULE_EXP,CLW_WORKFLOW_RULE.RULE_EXP_VALUE,CLW_WORKFLOW_RULE.RULE_ACTION,CLW_WORKFLOW_RULE.NEXT_ACTION_CD,CLW_WORKFLOW_RULE.SHORT_DESC,CLW_WORKFLOW_RULE.WORKFLOW_RULE_STATUS_CD,CLW_WORKFLOW_RULE.ADD_DATE,CLW_WORKFLOW_RULE.ADD_BY,CLW_WORKFLOW_RULE.MOD_DATE,CLW_WORKFLOW_RULE.MOD_BY,CLW_WORKFLOW_RULE.APPROVER_GROUP_ID,CLW_WORKFLOW_RULE.EMAIL_GROUP_ID,CLW_WORKFLOW_RULE.RULE_GROUP,CLW_WORKFLOW_RULE.WARNING_MESSAGE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkflowRuleData Object.
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
    *@returns a populated WorkflowRuleData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkflowRuleData x = WorkflowRuleData.createValue();
         
         x.setWorkflowRuleId(rs.getInt(1+offset));
         x.setRuleSeq(rs.getInt(2+offset));
         x.setWorkflowId(rs.getInt(3+offset));
         x.setRuleTypeCd(rs.getString(4+offset));
         x.setRuleExp(rs.getString(5+offset));
         x.setRuleExpValue(rs.getString(6+offset));
         x.setRuleAction(rs.getString(7+offset));
         x.setNextActionCd(rs.getString(8+offset));
         x.setShortDesc(rs.getString(9+offset));
         x.setWorkflowRuleStatusCd(rs.getString(10+offset));
         x.setAddDate(rs.getTimestamp(11+offset));
         x.setAddBy(rs.getString(12+offset));
         x.setModDate(rs.getTimestamp(13+offset));
         x.setModBy(rs.getString(14+offset));
         x.setApproverGroupId(rs.getInt(15+offset));
         x.setEmailGroupId(rs.getInt(16+offset));
         x.setRuleGroup(rs.getString(17+offset));
         x.setWarningMessage(rs.getString(18+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkflowRuleData Object represents.
    */
    public int getColumnCount(){
        return 18;
    }

    /**
     * Gets a WorkflowRuleDataVector object that consists
     * of WorkflowRuleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkflowRuleDataVector()
     * @throws            SQLException
     */
    public static WorkflowRuleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORKFLOW_RULE_ID,RULE_SEQ,WORKFLOW_ID,RULE_TYPE_CD,RULE_EXP,RULE_EXP_VALUE,RULE_ACTION,NEXT_ACTION_CD,SHORT_DESC,WORKFLOW_RULE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,APPROVER_GROUP_ID,EMAIL_GROUP_ID,RULE_GROUP,WARNING_MESSAGE FROM CLW_WORKFLOW_RULE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORKFLOW_RULE.WORKFLOW_RULE_ID,CLW_WORKFLOW_RULE.RULE_SEQ,CLW_WORKFLOW_RULE.WORKFLOW_ID,CLW_WORKFLOW_RULE.RULE_TYPE_CD,CLW_WORKFLOW_RULE.RULE_EXP,CLW_WORKFLOW_RULE.RULE_EXP_VALUE,CLW_WORKFLOW_RULE.RULE_ACTION,CLW_WORKFLOW_RULE.NEXT_ACTION_CD,CLW_WORKFLOW_RULE.SHORT_DESC,CLW_WORKFLOW_RULE.WORKFLOW_RULE_STATUS_CD,CLW_WORKFLOW_RULE.ADD_DATE,CLW_WORKFLOW_RULE.ADD_BY,CLW_WORKFLOW_RULE.MOD_DATE,CLW_WORKFLOW_RULE.MOD_BY,CLW_WORKFLOW_RULE.APPROVER_GROUP_ID,CLW_WORKFLOW_RULE.EMAIL_GROUP_ID,CLW_WORKFLOW_RULE.RULE_GROUP,CLW_WORKFLOW_RULE.WARNING_MESSAGE FROM CLW_WORKFLOW_RULE");
                where = pCriteria.getSqlClause("CLW_WORKFLOW_RULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORKFLOW_RULE.equals(otherTable)){
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
        WorkflowRuleDataVector v = new WorkflowRuleDataVector();
        while (rs.next()) {
            WorkflowRuleData x = WorkflowRuleData.createValue();
            
            x.setWorkflowRuleId(rs.getInt(1));
            x.setRuleSeq(rs.getInt(2));
            x.setWorkflowId(rs.getInt(3));
            x.setRuleTypeCd(rs.getString(4));
            x.setRuleExp(rs.getString(5));
            x.setRuleExpValue(rs.getString(6));
            x.setRuleAction(rs.getString(7));
            x.setNextActionCd(rs.getString(8));
            x.setShortDesc(rs.getString(9));
            x.setWorkflowRuleStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setApproverGroupId(rs.getInt(15));
            x.setEmailGroupId(rs.getInt(16));
            x.setRuleGroup(rs.getString(17));
            x.setWarningMessage(rs.getString(18));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkflowRuleDataVector object that consists
     * of WorkflowRuleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkflowRuleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkflowRuleDataVector()
     * @throws            SQLException
     */
    public static WorkflowRuleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkflowRuleDataVector v = new WorkflowRuleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_RULE_ID,RULE_SEQ,WORKFLOW_ID,RULE_TYPE_CD,RULE_EXP,RULE_EXP_VALUE,RULE_ACTION,NEXT_ACTION_CD,SHORT_DESC,WORKFLOW_RULE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,APPROVER_GROUP_ID,EMAIL_GROUP_ID,RULE_GROUP,WARNING_MESSAGE FROM CLW_WORKFLOW_RULE WHERE WORKFLOW_RULE_ID IN (");

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
            WorkflowRuleData x=null;
            while (rs.next()) {
                // build the object
                x=WorkflowRuleData.createValue();
                
                x.setWorkflowRuleId(rs.getInt(1));
                x.setRuleSeq(rs.getInt(2));
                x.setWorkflowId(rs.getInt(3));
                x.setRuleTypeCd(rs.getString(4));
                x.setRuleExp(rs.getString(5));
                x.setRuleExpValue(rs.getString(6));
                x.setRuleAction(rs.getString(7));
                x.setNextActionCd(rs.getString(8));
                x.setShortDesc(rs.getString(9));
                x.setWorkflowRuleStatusCd(rs.getString(10));
                x.setAddDate(rs.getTimestamp(11));
                x.setAddBy(rs.getString(12));
                x.setModDate(rs.getTimestamp(13));
                x.setModBy(rs.getString(14));
                x.setApproverGroupId(rs.getInt(15));
                x.setEmailGroupId(rs.getInt(16));
                x.setRuleGroup(rs.getString(17));
                x.setWarningMessage(rs.getString(18));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkflowRuleDataVector object of all
     * WorkflowRuleData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkflowRuleDataVector()
     * @throws            SQLException
     */
    public static WorkflowRuleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORKFLOW_RULE_ID,RULE_SEQ,WORKFLOW_ID,RULE_TYPE_CD,RULE_EXP,RULE_EXP_VALUE,RULE_ACTION,NEXT_ACTION_CD,SHORT_DESC,WORKFLOW_RULE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,APPROVER_GROUP_ID,EMAIL_GROUP_ID,RULE_GROUP,WARNING_MESSAGE FROM CLW_WORKFLOW_RULE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkflowRuleDataVector v = new WorkflowRuleDataVector();
        WorkflowRuleData x = null;
        while (rs.next()) {
            // build the object
            x = WorkflowRuleData.createValue();
            
            x.setWorkflowRuleId(rs.getInt(1));
            x.setRuleSeq(rs.getInt(2));
            x.setWorkflowId(rs.getInt(3));
            x.setRuleTypeCd(rs.getString(4));
            x.setRuleExp(rs.getString(5));
            x.setRuleExpValue(rs.getString(6));
            x.setRuleAction(rs.getString(7));
            x.setNextActionCd(rs.getString(8));
            x.setShortDesc(rs.getString(9));
            x.setWorkflowRuleStatusCd(rs.getString(10));
            x.setAddDate(rs.getTimestamp(11));
            x.setAddBy(rs.getString(12));
            x.setModDate(rs.getTimestamp(13));
            x.setModBy(rs.getString(14));
            x.setApproverGroupId(rs.getInt(15));
            x.setEmailGroupId(rs.getInt(16));
            x.setRuleGroup(rs.getString(17));
            x.setWarningMessage(rs.getString(18));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkflowRuleData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_RULE_ID FROM CLW_WORKFLOW_RULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_RULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_RULE");
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
     * Inserts a WorkflowRuleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowRuleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkflowRuleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowRuleData insert(Connection pCon, WorkflowRuleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORKFLOW_RULE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORKFLOW_RULE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkflowRuleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORKFLOW_RULE (WORKFLOW_RULE_ID,RULE_SEQ,WORKFLOW_ID,RULE_TYPE_CD,RULE_EXP,RULE_EXP_VALUE,RULE_ACTION,NEXT_ACTION_CD,SHORT_DESC,WORKFLOW_RULE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,APPROVER_GROUP_ID,EMAIL_GROUP_ID,RULE_GROUP,WARNING_MESSAGE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkflowRuleId());
        pstmt.setInt(2,pData.getRuleSeq());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getWorkflowId());
        }

        pstmt.setString(4,pData.getRuleTypeCd());
        pstmt.setString(5,pData.getRuleExp());
        pstmt.setString(6,pData.getRuleExpValue());
        pstmt.setString(7,pData.getRuleAction());
        pstmt.setString(8,pData.getNextActionCd());
        pstmt.setString(9,pData.getShortDesc());
        pstmt.setString(10,pData.getWorkflowRuleStatusCd());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12,pData.getAddBy());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14,pData.getModBy());
        if (pData.getApproverGroupId() == 0) {
            pstmt.setNull(15, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(15,pData.getApproverGroupId());
        }

        if (pData.getEmailGroupId() == 0) {
            pstmt.setNull(16, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(16,pData.getEmailGroupId());
        }

        pstmt.setString(17,pData.getRuleGroup());
        pstmt.setString(18,pData.getWarningMessage());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   RULE_SEQ="+pData.getRuleSeq());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   RULE_TYPE_CD="+pData.getRuleTypeCd());
            log.debug("SQL:   RULE_EXP="+pData.getRuleExp());
            log.debug("SQL:   RULE_EXP_VALUE="+pData.getRuleExpValue());
            log.debug("SQL:   RULE_ACTION="+pData.getRuleAction());
            log.debug("SQL:   NEXT_ACTION_CD="+pData.getNextActionCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   WORKFLOW_RULE_STATUS_CD="+pData.getWorkflowRuleStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   APPROVER_GROUP_ID="+pData.getApproverGroupId());
            log.debug("SQL:   EMAIL_GROUP_ID="+pData.getEmailGroupId());
            log.debug("SQL:   RULE_GROUP="+pData.getRuleGroup());
            log.debug("SQL:   WARNING_MESSAGE="+pData.getWarningMessage());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWorkflowRuleId(0);
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
     * Updates a WorkflowRuleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowRuleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowRuleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORKFLOW_RULE SET RULE_SEQ = ?,WORKFLOW_ID = ?,RULE_TYPE_CD = ?,RULE_EXP = ?,RULE_EXP_VALUE = ?,RULE_ACTION = ?,NEXT_ACTION_CD = ?,SHORT_DESC = ?,WORKFLOW_RULE_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,APPROVER_GROUP_ID = ?,EMAIL_GROUP_ID = ?,RULE_GROUP = ?,WARNING_MESSAGE = ? WHERE WORKFLOW_RULE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getRuleSeq());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkflowId());
        }

        pstmt.setString(i++,pData.getRuleTypeCd());
        pstmt.setString(i++,pData.getRuleExp());
        pstmt.setString(i++,pData.getRuleExpValue());
        pstmt.setString(i++,pData.getRuleAction());
        pstmt.setString(i++,pData.getNextActionCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getWorkflowRuleStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getApproverGroupId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getApproverGroupId());
        }

        if (pData.getEmailGroupId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getEmailGroupId());
        }

        pstmt.setString(i++,pData.getRuleGroup());
        pstmt.setString(i++,pData.getWarningMessage());
        pstmt.setInt(i++,pData.getWorkflowRuleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   RULE_SEQ="+pData.getRuleSeq());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   RULE_TYPE_CD="+pData.getRuleTypeCd());
            log.debug("SQL:   RULE_EXP="+pData.getRuleExp());
            log.debug("SQL:   RULE_EXP_VALUE="+pData.getRuleExpValue());
            log.debug("SQL:   RULE_ACTION="+pData.getRuleAction());
            log.debug("SQL:   NEXT_ACTION_CD="+pData.getNextActionCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   WORKFLOW_RULE_STATUS_CD="+pData.getWorkflowRuleStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   APPROVER_GROUP_ID="+pData.getApproverGroupId());
            log.debug("SQL:   EMAIL_GROUP_ID="+pData.getEmailGroupId());
            log.debug("SQL:   RULE_GROUP="+pData.getRuleGroup());
            log.debug("SQL:   WARNING_MESSAGE="+pData.getWarningMessage());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WorkflowRuleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowRuleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowRuleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORKFLOW_RULE WHERE WORKFLOW_RULE_ID = " + pWorkflowRuleId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkflowRuleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORKFLOW_RULE");
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
     * Inserts a WorkflowRuleData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowRuleData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkflowRuleData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORKFLOW_RULE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORKFLOW_RULE_ID,RULE_SEQ,WORKFLOW_ID,RULE_TYPE_CD,RULE_EXP,RULE_EXP_VALUE,RULE_ACTION,NEXT_ACTION_CD,SHORT_DESC,WORKFLOW_RULE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,APPROVER_GROUP_ID,EMAIL_GROUP_ID,RULE_GROUP,WARNING_MESSAGE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkflowRuleId());
        pstmt.setInt(2+4,pData.getRuleSeq());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getWorkflowId());
        }

        pstmt.setString(4+4,pData.getRuleTypeCd());
        pstmt.setString(5+4,pData.getRuleExp());
        pstmt.setString(6+4,pData.getRuleExpValue());
        pstmt.setString(7+4,pData.getRuleAction());
        pstmt.setString(8+4,pData.getNextActionCd());
        pstmt.setString(9+4,pData.getShortDesc());
        pstmt.setString(10+4,pData.getWorkflowRuleStatusCd());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(12+4,pData.getAddBy());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(14+4,pData.getModBy());
        if (pData.getApproverGroupId() == 0) {
            pstmt.setNull(15+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(15+4,pData.getApproverGroupId());
        }

        if (pData.getEmailGroupId() == 0) {
            pstmt.setNull(16+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(16+4,pData.getEmailGroupId());
        }

        pstmt.setString(17+4,pData.getRuleGroup());
        pstmt.setString(18+4,pData.getWarningMessage());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkflowRuleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowRuleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkflowRuleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowRuleData insert(Connection pCon, WorkflowRuleData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkflowRuleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowRuleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowRuleData pData, boolean pLogFl)
        throws SQLException {
        WorkflowRuleData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkflowRuleId();
          try {
          oldData = WorkflowRuleDataAccess.select(pCon,id);
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
     * Deletes a WorkflowRuleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowRuleId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowRuleId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORKFLOW_RULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_RULE d WHERE WORKFLOW_RULE_ID = " + pWorkflowRuleId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkflowRuleId);
        return n;
     }

    /**
     * Deletes WorkflowRuleData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORKFLOW_RULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_RULE d ");
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

