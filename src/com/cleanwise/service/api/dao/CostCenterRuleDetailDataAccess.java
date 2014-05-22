
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CostCenterRuleDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_COST_CENTER_RULE_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CostCenterRuleDetailData;
import com.cleanwise.service.api.value.CostCenterRuleDetailDataVector;
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
 * <code>CostCenterRuleDetailDataAccess</code>
 */
public class CostCenterRuleDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CostCenterRuleDetailDataAccess.class.getName());

    /** <code>CLW_COST_CENTER_RULE_DETAIL</code> table name */
	/* Primary key: COST_CENTER_RULE_DETAIL_ID */
	
    public static final String CLW_COST_CENTER_RULE_DETAIL = "CLW_COST_CENTER_RULE_DETAIL";
    
    /** <code>COST_CENTER_RULE_DETAIL_ID</code> COST_CENTER_RULE_DETAIL_ID column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String COST_CENTER_RULE_DETAIL_ID = "COST_CENTER_RULE_DETAIL_ID";
    /** <code>COST_CENTER_RULE_ID</code> COST_CENTER_RULE_ID column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String COST_CENTER_RULE_ID = "COST_CENTER_RULE_ID";
    /** <code>PARAM_NAME</code> PARAM_NAME column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String PARAM_NAME = "PARAM_NAME";
    /** <code>PARAM_VALUE</code> PARAM_VALUE column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String PARAM_VALUE = "PARAM_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_COST_CENTER_RULE_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CostCenterRuleDetailDataAccess()
    {
    }

    /**
     * Gets a CostCenterRuleDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCostCenterRuleDetailId The key requested.
     * @return new CostCenterRuleDetailData()
     * @throws            SQLException
     */
    public static CostCenterRuleDetailData select(Connection pCon, int pCostCenterRuleDetailId)
        throws SQLException, DataNotFoundException {
        CostCenterRuleDetailData x=null;
        String sql="SELECT COST_CENTER_RULE_DETAIL_ID,COST_CENTER_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE_DETAIL WHERE COST_CENTER_RULE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCostCenterRuleDetailId=" + pCostCenterRuleDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCostCenterRuleDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CostCenterRuleDetailData.createValue();
            
            x.setCostCenterRuleDetailId(rs.getInt(1));
            x.setCostCenterRuleId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("COST_CENTER_RULE_DETAIL_ID :" + pCostCenterRuleDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CostCenterRuleDetailDataVector object that consists
     * of CostCenterRuleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CostCenterRuleDetailDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CostCenterRuleDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_COST_CENTER_RULE_DETAIL.COST_CENTER_RULE_DETAIL_ID,CLW_COST_CENTER_RULE_DETAIL.COST_CENTER_RULE_ID,CLW_COST_CENTER_RULE_DETAIL.PARAM_NAME,CLW_COST_CENTER_RULE_DETAIL.PARAM_VALUE,CLW_COST_CENTER_RULE_DETAIL.ADD_DATE,CLW_COST_CENTER_RULE_DETAIL.ADD_BY,CLW_COST_CENTER_RULE_DETAIL.MOD_DATE,CLW_COST_CENTER_RULE_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CostCenterRuleDetailData Object.
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
    *@returns a populated CostCenterRuleDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CostCenterRuleDetailData x = CostCenterRuleDetailData.createValue();
         
         x.setCostCenterRuleDetailId(rs.getInt(1+offset));
         x.setCostCenterRuleId(rs.getInt(2+offset));
         x.setParamName(rs.getString(3+offset));
         x.setParamValue(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CostCenterRuleDetailData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a CostCenterRuleDetailDataVector object that consists
     * of CostCenterRuleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CostCenterRuleDetailDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT COST_CENTER_RULE_DETAIL_ID,COST_CENTER_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_COST_CENTER_RULE_DETAIL.COST_CENTER_RULE_DETAIL_ID,CLW_COST_CENTER_RULE_DETAIL.COST_CENTER_RULE_ID,CLW_COST_CENTER_RULE_DETAIL.PARAM_NAME,CLW_COST_CENTER_RULE_DETAIL.PARAM_VALUE,CLW_COST_CENTER_RULE_DETAIL.ADD_DATE,CLW_COST_CENTER_RULE_DETAIL.ADD_BY,CLW_COST_CENTER_RULE_DETAIL.MOD_DATE,CLW_COST_CENTER_RULE_DETAIL.MOD_BY FROM CLW_COST_CENTER_RULE_DETAIL");
                where = pCriteria.getSqlClause("CLW_COST_CENTER_RULE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COST_CENTER_RULE_DETAIL.equals(otherTable)){
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
        CostCenterRuleDetailDataVector v = new CostCenterRuleDetailDataVector();
        while (rs.next()) {
            CostCenterRuleDetailData x = CostCenterRuleDetailData.createValue();
            
            x.setCostCenterRuleDetailId(rs.getInt(1));
            x.setCostCenterRuleId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CostCenterRuleDetailDataVector object that consists
     * of CostCenterRuleDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CostCenterRuleDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CostCenterRuleDetailDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CostCenterRuleDetailDataVector v = new CostCenterRuleDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_RULE_DETAIL_ID,COST_CENTER_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE_DETAIL WHERE COST_CENTER_RULE_DETAIL_ID IN (");

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
            CostCenterRuleDetailData x=null;
            while (rs.next()) {
                // build the object
                x=CostCenterRuleDetailData.createValue();
                
                x.setCostCenterRuleDetailId(rs.getInt(1));
                x.setCostCenterRuleId(rs.getInt(2));
                x.setParamName(rs.getString(3));
                x.setParamValue(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CostCenterRuleDetailDataVector object of all
     * CostCenterRuleDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new CostCenterRuleDetailDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT COST_CENTER_RULE_DETAIL_ID,COST_CENTER_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CostCenterRuleDetailDataVector v = new CostCenterRuleDetailDataVector();
        CostCenterRuleDetailData x = null;
        while (rs.next()) {
            // build the object
            x = CostCenterRuleDetailData.createValue();
            
            x.setCostCenterRuleDetailId(rs.getInt(1));
            x.setCostCenterRuleId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CostCenterRuleDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_RULE_DETAIL_ID FROM CLW_COST_CENTER_RULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER_RULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER_RULE_DETAIL");
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
     * Inserts a CostCenterRuleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CostCenterRuleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterRuleDetailData insert(Connection pCon, CostCenterRuleDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_COST_CENTER_RULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_COST_CENTER_RULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCostCenterRuleDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_COST_CENTER_RULE_DETAIL (COST_CENTER_RULE_DETAIL_ID,COST_CENTER_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCostCenterRuleDetailId());
        pstmt.setInt(2,pData.getCostCenterRuleId());
        pstmt.setString(3,pData.getParamName());
        pstmt.setString(4,pData.getParamValue());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COST_CENTER_RULE_DETAIL_ID="+pData.getCostCenterRuleDetailId());
            log.debug("SQL:   COST_CENTER_RULE_ID="+pData.getCostCenterRuleId());
            log.debug("SQL:   PARAM_NAME="+pData.getParamName());
            log.debug("SQL:   PARAM_VALUE="+pData.getParamValue());
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
        pData.setCostCenterRuleDetailId(0);
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
     * Updates a CostCenterRuleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterRuleDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_COST_CENTER_RULE_DETAIL SET COST_CENTER_RULE_ID = ?,PARAM_NAME = ?,PARAM_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE COST_CENTER_RULE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCostCenterRuleId());
        pstmt.setString(i++,pData.getParamName());
        pstmt.setString(i++,pData.getParamValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCostCenterRuleDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COST_CENTER_RULE_ID="+pData.getCostCenterRuleId());
            log.debug("SQL:   PARAM_NAME="+pData.getParamName());
            log.debug("SQL:   PARAM_VALUE="+pData.getParamValue());
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
     * Deletes a CostCenterRuleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterRuleDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterRuleDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_COST_CENTER_RULE_DETAIL WHERE COST_CENTER_RULE_DETAIL_ID = " + pCostCenterRuleDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CostCenterRuleDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_COST_CENTER_RULE_DETAIL");
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
     * Inserts a CostCenterRuleDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CostCenterRuleDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_COST_CENTER_RULE_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "COST_CENTER_RULE_DETAIL_ID,COST_CENTER_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCostCenterRuleDetailId());
        pstmt.setInt(2+4,pData.getCostCenterRuleId());
        pstmt.setString(3+4,pData.getParamName());
        pstmt.setString(4+4,pData.getParamValue());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CostCenterRuleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CostCenterRuleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterRuleDetailData insert(Connection pCon, CostCenterRuleDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CostCenterRuleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterRuleDetailData pData, boolean pLogFl)
        throws SQLException {
        CostCenterRuleDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getCostCenterRuleDetailId();
          try {
          oldData = CostCenterRuleDetailDataAccess.select(pCon,id);
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
     * Deletes a CostCenterRuleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterRuleDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterRuleDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_COST_CENTER_RULE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER_RULE_DETAIL d WHERE COST_CENTER_RULE_DETAIL_ID = " + pCostCenterRuleDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCostCenterRuleDetailId);
        return n;
     }

    /**
     * Deletes CostCenterRuleDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_COST_CENTER_RULE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER_RULE_DETAIL d ");
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

