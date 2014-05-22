
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkflowDataAccess
 * Description:  This class is used to build access methods to the CLW_WORKFLOW table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.api.value.WorkflowDataVector;
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
 * <code>WorkflowDataAccess</code>
 */
public class WorkflowDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkflowDataAccess.class.getName());

    /** <code>CLW_WORKFLOW</code> table name */
	/* Primary key: WORKFLOW_ID */
	
    public static final String CLW_WORKFLOW = "CLW_WORKFLOW";
    
    /** <code>WORKFLOW_ID</code> WORKFLOW_ID column of table CLW_WORKFLOW */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_WORKFLOW */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_WORKFLOW */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>WORKFLOW_TYPE_CD</code> WORKFLOW_TYPE_CD column of table CLW_WORKFLOW */
    public static final String WORKFLOW_TYPE_CD = "WORKFLOW_TYPE_CD";
    /** <code>WORKFLOW_STATUS_CD</code> WORKFLOW_STATUS_CD column of table CLW_WORKFLOW */
    public static final String WORKFLOW_STATUS_CD = "WORKFLOW_STATUS_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_WORKFLOW */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_WORKFLOW */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_WORKFLOW */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_WORKFLOW */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_WORKFLOW */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_WORKFLOW */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public WorkflowDataAccess()
    {
    }

    /**
     * Gets a WorkflowData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkflowId The key requested.
     * @return new WorkflowData()
     * @throws            SQLException
     */
    public static WorkflowData select(Connection pCon, int pWorkflowId)
        throws SQLException, DataNotFoundException {
        WorkflowData x=null;
        String sql="SELECT WORKFLOW_ID,BUS_ENTITY_ID,SHORT_DESC,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW WHERE WORKFLOW_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkflowId=" + pWorkflowId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkflowId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkflowData.createValue();
            
            x.setWorkflowId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setWorkflowTypeCd(rs.getString(4));
            x.setWorkflowStatusCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORKFLOW_ID :" + pWorkflowId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkflowDataVector object that consists
     * of WorkflowData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkflowDataVector()
     * @throws            SQLException
     */
    public static WorkflowDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkflowData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORKFLOW.WORKFLOW_ID,CLW_WORKFLOW.BUS_ENTITY_ID,CLW_WORKFLOW.SHORT_DESC,CLW_WORKFLOW.WORKFLOW_TYPE_CD,CLW_WORKFLOW.WORKFLOW_STATUS_CD,CLW_WORKFLOW.EFF_DATE,CLW_WORKFLOW.EXP_DATE,CLW_WORKFLOW.ADD_DATE,CLW_WORKFLOW.ADD_BY,CLW_WORKFLOW.MOD_DATE,CLW_WORKFLOW.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkflowData Object.
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
    *@returns a populated WorkflowData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkflowData x = WorkflowData.createValue();
         
         x.setWorkflowId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setWorkflowTypeCd(rs.getString(4+offset));
         x.setWorkflowStatusCd(rs.getString(5+offset));
         x.setEffDate(rs.getDate(6+offset));
         x.setExpDate(rs.getDate(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkflowData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a WorkflowDataVector object that consists
     * of WorkflowData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkflowDataVector()
     * @throws            SQLException
     */
    public static WorkflowDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORKFLOW_ID,BUS_ENTITY_ID,SHORT_DESC,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORKFLOW.WORKFLOW_ID,CLW_WORKFLOW.BUS_ENTITY_ID,CLW_WORKFLOW.SHORT_DESC,CLW_WORKFLOW.WORKFLOW_TYPE_CD,CLW_WORKFLOW.WORKFLOW_STATUS_CD,CLW_WORKFLOW.EFF_DATE,CLW_WORKFLOW.EXP_DATE,CLW_WORKFLOW.ADD_DATE,CLW_WORKFLOW.ADD_BY,CLW_WORKFLOW.MOD_DATE,CLW_WORKFLOW.MOD_BY FROM CLW_WORKFLOW");
                where = pCriteria.getSqlClause("CLW_WORKFLOW");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORKFLOW.equals(otherTable)){
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
        WorkflowDataVector v = new WorkflowDataVector();
        while (rs.next()) {
            WorkflowData x = WorkflowData.createValue();
            
            x.setWorkflowId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setWorkflowTypeCd(rs.getString(4));
            x.setWorkflowStatusCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
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
     * Gets a WorkflowDataVector object that consists
     * of WorkflowData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkflowData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkflowDataVector()
     * @throws            SQLException
     */
    public static WorkflowDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkflowDataVector v = new WorkflowDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_ID,BUS_ENTITY_ID,SHORT_DESC,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW WHERE WORKFLOW_ID IN (");

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
            WorkflowData x=null;
            while (rs.next()) {
                // build the object
                x=WorkflowData.createValue();
                
                x.setWorkflowId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setShortDesc(rs.getString(3));
                x.setWorkflowTypeCd(rs.getString(4));
                x.setWorkflowStatusCd(rs.getString(5));
                x.setEffDate(rs.getDate(6));
                x.setExpDate(rs.getDate(7));
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
     * Gets a WorkflowDataVector object of all
     * WorkflowData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkflowDataVector()
     * @throws            SQLException
     */
    public static WorkflowDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORKFLOW_ID,BUS_ENTITY_ID,SHORT_DESC,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_WORKFLOW";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkflowDataVector v = new WorkflowDataVector();
        WorkflowData x = null;
        while (rs.next()) {
            // build the object
            x = WorkflowData.createValue();
            
            x.setWorkflowId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setShortDesc(rs.getString(3));
            x.setWorkflowTypeCd(rs.getString(4));
            x.setWorkflowStatusCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
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
     * WorkflowData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_ID FROM CLW_WORKFLOW");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW");
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
     * Inserts a WorkflowData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new WorkflowData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowData insert(Connection pCon, WorkflowData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORKFLOW_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORKFLOW_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkflowId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORKFLOW (WORKFLOW_ID,BUS_ENTITY_ID,SHORT_DESC,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getWorkflowId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getWorkflowTypeCd());
        pstmt.setString(5,pData.getWorkflowStatusCd());
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   WORKFLOW_TYPE_CD="+pData.getWorkflowTypeCd());
            log.debug("SQL:   WORKFLOW_STATUS_CD="+pData.getWorkflowStatusCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
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
        pData.setWorkflowId(0);
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
     * Updates a WorkflowData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORKFLOW SET BUS_ENTITY_ID = ?,SHORT_DESC = ?,WORKFLOW_TYPE_CD = ?,WORKFLOW_STATUS_CD = ?,EFF_DATE = ?,EXP_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE WORKFLOW_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getWorkflowTypeCd());
        pstmt.setString(i++,pData.getWorkflowStatusCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getWorkflowId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   WORKFLOW_TYPE_CD="+pData.getWorkflowTypeCd());
            log.debug("SQL:   WORKFLOW_STATUS_CD="+pData.getWorkflowStatusCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
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
     * Deletes a WorkflowData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORKFLOW WHERE WORKFLOW_ID = " + pWorkflowId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkflowData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORKFLOW");
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
     * Inserts a WorkflowData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkflowData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORKFLOW (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORKFLOW_ID,BUS_ENTITY_ID,SHORT_DESC,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,EFF_DATE,EXP_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkflowId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getWorkflowTypeCd());
        pstmt.setString(5+4,pData.getWorkflowStatusCd());
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkflowData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new WorkflowData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowData insert(Connection pCon, WorkflowData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkflowData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowData pData, boolean pLogFl)
        throws SQLException {
        WorkflowData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkflowId();
          try {
          oldData = WorkflowDataAccess.select(pCon,id);
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
     * Deletes a WorkflowData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORKFLOW SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW d WHERE WORKFLOW_ID = " + pWorkflowId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkflowId);
        return n;
     }

    /**
     * Deletes WorkflowData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORKFLOW SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW d ");
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

