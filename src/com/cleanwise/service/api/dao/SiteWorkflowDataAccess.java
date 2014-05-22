
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        SiteWorkflowDataAccess
 * Description:  This class is used to build access methods to the CLW_SITE_WORKFLOW table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.SiteWorkflowData;
import com.cleanwise.service.api.value.SiteWorkflowDataVector;
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
 * <code>SiteWorkflowDataAccess</code>
 */
public class SiteWorkflowDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(SiteWorkflowDataAccess.class.getName());

    /** <code>CLW_SITE_WORKFLOW</code> table name */
	/* Primary key: SITE_WORKFLOW_ID */
	
    public static final String CLW_SITE_WORKFLOW = "CLW_SITE_WORKFLOW";
    
    /** <code>SITE_WORKFLOW_ID</code> SITE_WORKFLOW_ID column of table CLW_SITE_WORKFLOW */
    public static final String SITE_WORKFLOW_ID = "SITE_WORKFLOW_ID";
    /** <code>WORKFLOW_ID</code> WORKFLOW_ID column of table CLW_SITE_WORKFLOW */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SITE_WORKFLOW */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SITE_WORKFLOW */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SITE_WORKFLOW */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SITE_WORKFLOW */
    public static final String MOD_BY = "MOD_BY";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_SITE_WORKFLOW */
    public static final String SITE_ID = "SITE_ID";

    /**
     * Constructor.
     */
    public SiteWorkflowDataAccess()
    {
    }

    /**
     * Gets a SiteWorkflowData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pSiteWorkflowId The key requested.
     * @return new SiteWorkflowData()
     * @throws            SQLException
     */
    public static SiteWorkflowData select(Connection pCon, int pSiteWorkflowId)
        throws SQLException, DataNotFoundException {
        SiteWorkflowData x=null;
        String sql="SELECT SITE_WORKFLOW_ID,WORKFLOW_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID FROM CLW_SITE_WORKFLOW WHERE SITE_WORKFLOW_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pSiteWorkflowId=" + pSiteWorkflowId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pSiteWorkflowId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=SiteWorkflowData.createValue();
            
            x.setSiteWorkflowId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setAddDate(rs.getTimestamp(3));
            x.setAddBy(rs.getString(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setSiteId(rs.getInt(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SITE_WORKFLOW_ID :" + pSiteWorkflowId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a SiteWorkflowDataVector object that consists
     * of SiteWorkflowData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new SiteWorkflowDataVector()
     * @throws            SQLException
     */
    public static SiteWorkflowDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a SiteWorkflowData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SITE_WORKFLOW.SITE_WORKFLOW_ID,CLW_SITE_WORKFLOW.WORKFLOW_ID,CLW_SITE_WORKFLOW.ADD_DATE,CLW_SITE_WORKFLOW.ADD_BY,CLW_SITE_WORKFLOW.MOD_DATE,CLW_SITE_WORKFLOW.MOD_BY,CLW_SITE_WORKFLOW.SITE_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated SiteWorkflowData Object.
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
    *@returns a populated SiteWorkflowData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         SiteWorkflowData x = SiteWorkflowData.createValue();
         
         x.setSiteWorkflowId(rs.getInt(1+offset));
         x.setWorkflowId(rs.getInt(2+offset));
         x.setAddDate(rs.getTimestamp(3+offset));
         x.setAddBy(rs.getString(4+offset));
         x.setModDate(rs.getTimestamp(5+offset));
         x.setModBy(rs.getString(6+offset));
         x.setSiteId(rs.getInt(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the SiteWorkflowData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a SiteWorkflowDataVector object that consists
     * of SiteWorkflowData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new SiteWorkflowDataVector()
     * @throws            SQLException
     */
    public static SiteWorkflowDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SITE_WORKFLOW_ID,WORKFLOW_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID FROM CLW_SITE_WORKFLOW");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SITE_WORKFLOW.SITE_WORKFLOW_ID,CLW_SITE_WORKFLOW.WORKFLOW_ID,CLW_SITE_WORKFLOW.ADD_DATE,CLW_SITE_WORKFLOW.ADD_BY,CLW_SITE_WORKFLOW.MOD_DATE,CLW_SITE_WORKFLOW.MOD_BY,CLW_SITE_WORKFLOW.SITE_ID FROM CLW_SITE_WORKFLOW");
                where = pCriteria.getSqlClause("CLW_SITE_WORKFLOW");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SITE_WORKFLOW.equals(otherTable)){
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
        SiteWorkflowDataVector v = new SiteWorkflowDataVector();
        while (rs.next()) {
            SiteWorkflowData x = SiteWorkflowData.createValue();
            
            x.setSiteWorkflowId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setAddDate(rs.getTimestamp(3));
            x.setAddBy(rs.getString(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setSiteId(rs.getInt(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a SiteWorkflowDataVector object that consists
     * of SiteWorkflowData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for SiteWorkflowData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new SiteWorkflowDataVector()
     * @throws            SQLException
     */
    public static SiteWorkflowDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        SiteWorkflowDataVector v = new SiteWorkflowDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SITE_WORKFLOW_ID,WORKFLOW_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID FROM CLW_SITE_WORKFLOW WHERE SITE_WORKFLOW_ID IN (");

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
            SiteWorkflowData x=null;
            while (rs.next()) {
                // build the object
                x=SiteWorkflowData.createValue();
                
                x.setSiteWorkflowId(rs.getInt(1));
                x.setWorkflowId(rs.getInt(2));
                x.setAddDate(rs.getTimestamp(3));
                x.setAddBy(rs.getString(4));
                x.setModDate(rs.getTimestamp(5));
                x.setModBy(rs.getString(6));
                x.setSiteId(rs.getInt(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a SiteWorkflowDataVector object of all
     * SiteWorkflowData objects in the database.
     * @param pCon An open database connection.
     * @return new SiteWorkflowDataVector()
     * @throws            SQLException
     */
    public static SiteWorkflowDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SITE_WORKFLOW_ID,WORKFLOW_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID FROM CLW_SITE_WORKFLOW";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        SiteWorkflowDataVector v = new SiteWorkflowDataVector();
        SiteWorkflowData x = null;
        while (rs.next()) {
            // build the object
            x = SiteWorkflowData.createValue();
            
            x.setSiteWorkflowId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setAddDate(rs.getTimestamp(3));
            x.setAddBy(rs.getString(4));
            x.setModDate(rs.getTimestamp(5));
            x.setModBy(rs.getString(6));
            x.setSiteId(rs.getInt(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * SiteWorkflowData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT SITE_WORKFLOW_ID FROM CLW_SITE_WORKFLOW");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SITE_WORKFLOW");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SITE_WORKFLOW");
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
     * Inserts a SiteWorkflowData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteWorkflowData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new SiteWorkflowData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static SiteWorkflowData insert(Connection pCon, SiteWorkflowData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SITE_WORKFLOW_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SITE_WORKFLOW_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setSiteWorkflowId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SITE_WORKFLOW (SITE_WORKFLOW_ID,WORKFLOW_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getSiteWorkflowId());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getWorkflowId());
        }

        pstmt.setTimestamp(3,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(4,pData.getAddBy());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(6,pData.getModBy());
        if (pData.getSiteId() == 0) {
            pstmt.setNull(7, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(7,pData.getSiteId());
        }


        if (log.isDebugEnabled()) {
            log.debug("SQL:   SITE_WORKFLOW_ID="+pData.getSiteWorkflowId());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setSiteWorkflowId(0);
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
     * Updates a SiteWorkflowData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteWorkflowData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, SiteWorkflowData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SITE_WORKFLOW SET WORKFLOW_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,SITE_ID = ? WHERE SITE_WORKFLOW_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkflowId());
        }

        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getSiteId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getSiteId());
        }

        pstmt.setInt(i++,pData.getSiteWorkflowId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a SiteWorkflowData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pSiteWorkflowId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pSiteWorkflowId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SITE_WORKFLOW WHERE SITE_WORKFLOW_ID = " + pSiteWorkflowId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes SiteWorkflowData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SITE_WORKFLOW");
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
     * Inserts a SiteWorkflowData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteWorkflowData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, SiteWorkflowData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SITE_WORKFLOW (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SITE_WORKFLOW_ID,WORKFLOW_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getSiteWorkflowId());
        if (pData.getWorkflowId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getWorkflowId());
        }

        pstmt.setTimestamp(3+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(4+4,pData.getAddBy());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(6+4,pData.getModBy());
        if (pData.getSiteId() == 0) {
            pstmt.setNull(7+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(7+4,pData.getSiteId());
        }



        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a SiteWorkflowData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteWorkflowData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new SiteWorkflowData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static SiteWorkflowData insert(Connection pCon, SiteWorkflowData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a SiteWorkflowData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A SiteWorkflowData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, SiteWorkflowData pData, boolean pLogFl)
        throws SQLException {
        SiteWorkflowData oldData = null;
        if(pLogFl) {
          int id = pData.getSiteWorkflowId();
          try {
          oldData = SiteWorkflowDataAccess.select(pCon,id);
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
     * Deletes a SiteWorkflowData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pSiteWorkflowId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pSiteWorkflowId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SITE_WORKFLOW SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SITE_WORKFLOW d WHERE SITE_WORKFLOW_ID = " + pSiteWorkflowId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pSiteWorkflowId);
        return n;
     }

    /**
     * Deletes SiteWorkflowData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SITE_WORKFLOW SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SITE_WORKFLOW d ");
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

