
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkflowAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_WORKFLOW_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkflowAssocData;
import com.cleanwise.service.api.value.WorkflowAssocDataVector;
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
 * <code>WorkflowAssocDataAccess</code>
 */
public class WorkflowAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkflowAssocDataAccess.class.getName());

    /** <code>CLW_WORKFLOW_ASSOC</code> table name */
	/* Primary key: WORKFLOW_ASSOC_ID */
	
    public static final String CLW_WORKFLOW_ASSOC = "CLW_WORKFLOW_ASSOC";
    
    /** <code>WORKFLOW_ASSOC_ID</code> WORKFLOW_ASSOC_ID column of table CLW_WORKFLOW_ASSOC */
    public static final String WORKFLOW_ASSOC_ID = "WORKFLOW_ASSOC_ID";
    /** <code>WORKFLOW_ID</code> WORKFLOW_ID column of table CLW_WORKFLOW_ASSOC */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    /** <code>WORKFLOW_RULE_ID</code> WORKFLOW_RULE_ID column of table CLW_WORKFLOW_ASSOC */
    public static final String WORKFLOW_RULE_ID = "WORKFLOW_RULE_ID";
    /** <code>WORKFLOW_ASSOC_CD</code> WORKFLOW_ASSOC_CD column of table CLW_WORKFLOW_ASSOC */
    public static final String WORKFLOW_ASSOC_CD = "WORKFLOW_ASSOC_CD";
    /** <code>GROUP_ID</code> GROUP_ID column of table CLW_WORKFLOW_ASSOC */
    public static final String GROUP_ID = "GROUP_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_WORKFLOW_ASSOC */
    public static final String USER_ID = "USER_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORKFLOW_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORKFLOW_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORKFLOW_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORKFLOW_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public WorkflowAssocDataAccess()
    {
    }

    /**
     * Gets a WorkflowAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkflowAssocId The key requested.
     * @return new WorkflowAssocData()
     * @throws            SQLException
     */
    public static WorkflowAssocData select(Connection pCon, int pWorkflowAssocId)
        throws SQLException, DataNotFoundException {
        WorkflowAssocData x=null;
        String sql="SELECT WORKFLOW_ASSOC_ID,WORKFLOW_ID,WORKFLOW_RULE_ID,WORKFLOW_ASSOC_CD,GROUP_ID,USER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_ASSOC WHERE WORKFLOW_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkflowAssocId=" + pWorkflowAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkflowAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkflowAssocData.createValue();
            
            x.setWorkflowAssocId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setWorkflowRuleId(rs.getInt(3));
            x.setWorkflowAssocCd(rs.getString(4));
            x.setGroupId(rs.getInt(5));
            x.setUserId(rs.getInt(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORKFLOW_ASSOC_ID :" + pWorkflowAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkflowAssocDataVector object that consists
     * of WorkflowAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkflowAssocDataVector()
     * @throws            SQLException
     */
    public static WorkflowAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkflowAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORKFLOW_ASSOC.WORKFLOW_ASSOC_ID,CLW_WORKFLOW_ASSOC.WORKFLOW_ID,CLW_WORKFLOW_ASSOC.WORKFLOW_RULE_ID,CLW_WORKFLOW_ASSOC.WORKFLOW_ASSOC_CD,CLW_WORKFLOW_ASSOC.GROUP_ID,CLW_WORKFLOW_ASSOC.USER_ID,CLW_WORKFLOW_ASSOC.ADD_DATE,CLW_WORKFLOW_ASSOC.ADD_BY,CLW_WORKFLOW_ASSOC.MOD_DATE,CLW_WORKFLOW_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkflowAssocData Object.
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
    *@returns a populated WorkflowAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkflowAssocData x = WorkflowAssocData.createValue();
         
         x.setWorkflowAssocId(rs.getInt(1+offset));
         x.setWorkflowId(rs.getInt(2+offset));
         x.setWorkflowRuleId(rs.getInt(3+offset));
         x.setWorkflowAssocCd(rs.getString(4+offset));
         x.setGroupId(rs.getInt(5+offset));
         x.setUserId(rs.getInt(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkflowAssocData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a WorkflowAssocDataVector object that consists
     * of WorkflowAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkflowAssocDataVector()
     * @throws            SQLException
     */
    public static WorkflowAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORKFLOW_ASSOC_ID,WORKFLOW_ID,WORKFLOW_RULE_ID,WORKFLOW_ASSOC_CD,GROUP_ID,USER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORKFLOW_ASSOC.WORKFLOW_ASSOC_ID,CLW_WORKFLOW_ASSOC.WORKFLOW_ID,CLW_WORKFLOW_ASSOC.WORKFLOW_RULE_ID,CLW_WORKFLOW_ASSOC.WORKFLOW_ASSOC_CD,CLW_WORKFLOW_ASSOC.GROUP_ID,CLW_WORKFLOW_ASSOC.USER_ID,CLW_WORKFLOW_ASSOC.ADD_DATE,CLW_WORKFLOW_ASSOC.ADD_BY,CLW_WORKFLOW_ASSOC.MOD_DATE,CLW_WORKFLOW_ASSOC.MOD_BY FROM CLW_WORKFLOW_ASSOC");
                where = pCriteria.getSqlClause("CLW_WORKFLOW_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORKFLOW_ASSOC.equals(otherTable)){
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
        WorkflowAssocDataVector v = new WorkflowAssocDataVector();
        while (rs.next()) {
            WorkflowAssocData x = WorkflowAssocData.createValue();
            
            x.setWorkflowAssocId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setWorkflowRuleId(rs.getInt(3));
            x.setWorkflowAssocCd(rs.getString(4));
            x.setGroupId(rs.getInt(5));
            x.setUserId(rs.getInt(6));
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
     * Gets a WorkflowAssocDataVector object that consists
     * of WorkflowAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkflowAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkflowAssocDataVector()
     * @throws            SQLException
     */
    public static WorkflowAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkflowAssocDataVector v = new WorkflowAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_ASSOC_ID,WORKFLOW_ID,WORKFLOW_RULE_ID,WORKFLOW_ASSOC_CD,GROUP_ID,USER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_ASSOC WHERE WORKFLOW_ASSOC_ID IN (");

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
            WorkflowAssocData x=null;
            while (rs.next()) {
                // build the object
                x=WorkflowAssocData.createValue();
                
                x.setWorkflowAssocId(rs.getInt(1));
                x.setWorkflowId(rs.getInt(2));
                x.setWorkflowRuleId(rs.getInt(3));
                x.setWorkflowAssocCd(rs.getString(4));
                x.setGroupId(rs.getInt(5));
                x.setUserId(rs.getInt(6));
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
     * Gets a WorkflowAssocDataVector object of all
     * WorkflowAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkflowAssocDataVector()
     * @throws            SQLException
     */
    public static WorkflowAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORKFLOW_ASSOC_ID,WORKFLOW_ID,WORKFLOW_RULE_ID,WORKFLOW_ASSOC_CD,GROUP_ID,USER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkflowAssocDataVector v = new WorkflowAssocDataVector();
        WorkflowAssocData x = null;
        while (rs.next()) {
            // build the object
            x = WorkflowAssocData.createValue();
            
            x.setWorkflowAssocId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setWorkflowRuleId(rs.getInt(3));
            x.setWorkflowAssocCd(rs.getString(4));
            x.setGroupId(rs.getInt(5));
            x.setUserId(rs.getInt(6));
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
     * WorkflowAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_ASSOC_ID FROM CLW_WORKFLOW_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_ASSOC");
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
     * Inserts a WorkflowAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkflowAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowAssocData insert(Connection pCon, WorkflowAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORKFLOW_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORKFLOW_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkflowAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORKFLOW_ASSOC (WORKFLOW_ASSOC_ID,WORKFLOW_ID,WORKFLOW_RULE_ID,WORKFLOW_ASSOC_CD,GROUP_ID,USER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkflowAssocId());
        pstmt.setInt(2,pData.getWorkflowId());
        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getWorkflowRuleId());
        }

        pstmt.setString(4,pData.getWorkflowAssocCd());
        if (pData.getGroupId() == 0) {
            pstmt.setNull(5, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5,pData.getGroupId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(6, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(6,pData.getUserId());
        }

        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_ASSOC_ID="+pData.getWorkflowAssocId());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   WORKFLOW_ASSOC_CD="+pData.getWorkflowAssocCd());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
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
        pData.setWorkflowAssocId(0);
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
     * Updates a WorkflowAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORKFLOW_ASSOC SET WORKFLOW_ID = ?,WORKFLOW_RULE_ID = ?,WORKFLOW_ASSOC_CD = ?,GROUP_ID = ?,USER_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE WORKFLOW_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getWorkflowId());
        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkflowRuleId());
        }

        pstmt.setString(i++,pData.getWorkflowAssocCd());
        if (pData.getGroupId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getGroupId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getUserId());
        }

        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkflowAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   WORKFLOW_ASSOC_CD="+pData.getWorkflowAssocCd());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
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
     * Deletes a WorkflowAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORKFLOW_ASSOC WHERE WORKFLOW_ASSOC_ID = " + pWorkflowAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkflowAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORKFLOW_ASSOC");
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
     * Inserts a WorkflowAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkflowAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORKFLOW_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORKFLOW_ASSOC_ID,WORKFLOW_ID,WORKFLOW_RULE_ID,WORKFLOW_ASSOC_CD,GROUP_ID,USER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkflowAssocId());
        pstmt.setInt(2+4,pData.getWorkflowId());
        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getWorkflowRuleId());
        }

        pstmt.setString(4+4,pData.getWorkflowAssocCd());
        if (pData.getGroupId() == 0) {
            pstmt.setNull(5+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5+4,pData.getGroupId());
        }

        if (pData.getUserId() == 0) {
            pstmt.setNull(6+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(6+4,pData.getUserId());
        }

        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkflowAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkflowAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowAssocData insert(Connection pCon, WorkflowAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkflowAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowAssocData pData, boolean pLogFl)
        throws SQLException {
        WorkflowAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkflowAssocId();
          try {
          oldData = WorkflowAssocDataAccess.select(pCon,id);
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
     * Deletes a WorkflowAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORKFLOW_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_ASSOC d WHERE WORKFLOW_ASSOC_ID = " + pWorkflowAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkflowAssocId);
        return n;
     }

    /**
     * Deletes WorkflowAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORKFLOW_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_ASSOC d ");
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

