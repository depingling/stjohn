
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CostCenterRuleDataAccess
 * Description:  This class is used to build access methods to the CLW_COST_CENTER_RULE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CostCenterRuleData;
import com.cleanwise.service.api.value.CostCenterRuleDataVector;
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
 * <code>CostCenterRuleDataAccess</code>
 */
public class CostCenterRuleDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CostCenterRuleDataAccess.class.getName());

    /** <code>CLW_COST_CENTER_RULE</code> table name */
	/* Primary key: COST_CENTER_RULE_ID */
	
    public static final String CLW_COST_CENTER_RULE = "CLW_COST_CENTER_RULE";
    
    /** <code>COST_CENTER_RULE_ID</code> COST_CENTER_RULE_ID column of table CLW_COST_CENTER_RULE */
    public static final String COST_CENTER_RULE_ID = "COST_CENTER_RULE_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_COST_CENTER_RULE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_COST_CENTER_RULE */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_COST_CENTER_RULE */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>MATCH_TYPE_CD</code> MATCH_TYPE_CD column of table CLW_COST_CENTER_RULE */
    public static final String MATCH_TYPE_CD = "MATCH_TYPE_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_COST_CENTER_RULE */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_COST_CENTER_RULE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_COST_CENTER_RULE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_COST_CENTER_RULE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_COST_CENTER_RULE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CostCenterRuleDataAccess()
    {
    }

    /**
     * Gets a CostCenterRuleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCostCenterRuleId The key requested.
     * @return new CostCenterRuleData()
     * @throws            SQLException
     */
    public static CostCenterRuleData select(Connection pCon, int pCostCenterRuleId)
        throws SQLException, DataNotFoundException {
        CostCenterRuleData x=null;
        String sql="SELECT COST_CENTER_RULE_ID,BUS_ENTITY_ID,COST_CENTER_ID,ITEM_ID,MATCH_TYPE_CD,EFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE WHERE COST_CENTER_RULE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCostCenterRuleId=" + pCostCenterRuleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCostCenterRuleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CostCenterRuleData.createValue();
            
            x.setCostCenterRuleId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setCostCenterId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setMatchTypeCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("COST_CENTER_RULE_ID :" + pCostCenterRuleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CostCenterRuleDataVector object that consists
     * of CostCenterRuleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CostCenterRuleDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CostCenterRuleData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_COST_CENTER_RULE.COST_CENTER_RULE_ID,CLW_COST_CENTER_RULE.BUS_ENTITY_ID,CLW_COST_CENTER_RULE.COST_CENTER_ID,CLW_COST_CENTER_RULE.ITEM_ID,CLW_COST_CENTER_RULE.MATCH_TYPE_CD,CLW_COST_CENTER_RULE.EFF_DATE,CLW_COST_CENTER_RULE.ADD_DATE,CLW_COST_CENTER_RULE.ADD_BY,CLW_COST_CENTER_RULE.MOD_DATE,CLW_COST_CENTER_RULE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CostCenterRuleData Object.
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
    *@returns a populated CostCenterRuleData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CostCenterRuleData x = CostCenterRuleData.createValue();
         
         x.setCostCenterRuleId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setCostCenterId(rs.getInt(3+offset));
         x.setItemId(rs.getInt(4+offset));
         x.setMatchTypeCd(rs.getString(5+offset));
         x.setEffDate(rs.getDate(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CostCenterRuleData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a CostCenterRuleDataVector object that consists
     * of CostCenterRuleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CostCenterRuleDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT COST_CENTER_RULE_ID,BUS_ENTITY_ID,COST_CENTER_ID,ITEM_ID,MATCH_TYPE_CD,EFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_COST_CENTER_RULE.COST_CENTER_RULE_ID,CLW_COST_CENTER_RULE.BUS_ENTITY_ID,CLW_COST_CENTER_RULE.COST_CENTER_ID,CLW_COST_CENTER_RULE.ITEM_ID,CLW_COST_CENTER_RULE.MATCH_TYPE_CD,CLW_COST_CENTER_RULE.EFF_DATE,CLW_COST_CENTER_RULE.ADD_DATE,CLW_COST_CENTER_RULE.ADD_BY,CLW_COST_CENTER_RULE.MOD_DATE,CLW_COST_CENTER_RULE.MOD_BY FROM CLW_COST_CENTER_RULE");
                where = pCriteria.getSqlClause("CLW_COST_CENTER_RULE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COST_CENTER_RULE.equals(otherTable)){
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
        CostCenterRuleDataVector v = new CostCenterRuleDataVector();
        while (rs.next()) {
            CostCenterRuleData x = CostCenterRuleData.createValue();
            
            x.setCostCenterRuleId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setCostCenterId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setMatchTypeCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
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
     * Gets a CostCenterRuleDataVector object that consists
     * of CostCenterRuleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CostCenterRuleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CostCenterRuleDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CostCenterRuleDataVector v = new CostCenterRuleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_RULE_ID,BUS_ENTITY_ID,COST_CENTER_ID,ITEM_ID,MATCH_TYPE_CD,EFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE WHERE COST_CENTER_RULE_ID IN (");

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
            CostCenterRuleData x=null;
            while (rs.next()) {
                // build the object
                x=CostCenterRuleData.createValue();
                
                x.setCostCenterRuleId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setCostCenterId(rs.getInt(3));
                x.setItemId(rs.getInt(4));
                x.setMatchTypeCd(rs.getString(5));
                x.setEffDate(rs.getDate(6));
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
     * Gets a CostCenterRuleDataVector object of all
     * CostCenterRuleData objects in the database.
     * @param pCon An open database connection.
     * @return new CostCenterRuleDataVector()
     * @throws            SQLException
     */
    public static CostCenterRuleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT COST_CENTER_RULE_ID,BUS_ENTITY_ID,COST_CENTER_ID,ITEM_ID,MATCH_TYPE_CD,EFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_RULE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CostCenterRuleDataVector v = new CostCenterRuleDataVector();
        CostCenterRuleData x = null;
        while (rs.next()) {
            // build the object
            x = CostCenterRuleData.createValue();
            
            x.setCostCenterRuleId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setCostCenterId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setMatchTypeCd(rs.getString(5));
            x.setEffDate(rs.getDate(6));
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
     * CostCenterRuleData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_RULE_ID FROM CLW_COST_CENTER_RULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER_RULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER_RULE");
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
     * Inserts a CostCenterRuleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CostCenterRuleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterRuleData insert(Connection pCon, CostCenterRuleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_COST_CENTER_RULE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_COST_CENTER_RULE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCostCenterRuleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_COST_CENTER_RULE (COST_CENTER_RULE_ID,BUS_ENTITY_ID,COST_CENTER_ID,ITEM_ID,MATCH_TYPE_CD,EFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCostCenterRuleId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getCostCenterId());
        pstmt.setInt(4,pData.getItemId());
        pstmt.setString(5,pData.getMatchTypeCd());
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COST_CENTER_RULE_ID="+pData.getCostCenterRuleId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   MATCH_TYPE_CD="+pData.getMatchTypeCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
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
        pData.setCostCenterRuleId(0);
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
     * Updates a CostCenterRuleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterRuleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_COST_CENTER_RULE SET BUS_ENTITY_ID = ?,COST_CENTER_ID = ?,ITEM_ID = ?,MATCH_TYPE_CD = ?,EFF_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE COST_CENTER_RULE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getCostCenterId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getMatchTypeCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCostCenterRuleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   MATCH_TYPE_CD="+pData.getMatchTypeCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
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
     * Deletes a CostCenterRuleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterRuleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterRuleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_COST_CENTER_RULE WHERE COST_CENTER_RULE_ID = " + pCostCenterRuleId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CostCenterRuleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_COST_CENTER_RULE");
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
     * Inserts a CostCenterRuleData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CostCenterRuleData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_COST_CENTER_RULE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "COST_CENTER_RULE_ID,BUS_ENTITY_ID,COST_CENTER_ID,ITEM_ID,MATCH_TYPE_CD,EFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCostCenterRuleId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getCostCenterId());
        pstmt.setInt(4+4,pData.getItemId());
        pstmt.setString(5+4,pData.getMatchTypeCd());
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CostCenterRuleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CostCenterRuleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterRuleData insert(Connection pCon, CostCenterRuleData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CostCenterRuleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterRuleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterRuleData pData, boolean pLogFl)
        throws SQLException {
        CostCenterRuleData oldData = null;
        if(pLogFl) {
          int id = pData.getCostCenterRuleId();
          try {
          oldData = CostCenterRuleDataAccess.select(pCon,id);
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
     * Deletes a CostCenterRuleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterRuleId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterRuleId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_COST_CENTER_RULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER_RULE d WHERE COST_CENTER_RULE_ID = " + pCostCenterRuleId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCostCenterRuleId);
        return n;
     }

    /**
     * Deletes CostCenterRuleData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_COST_CENTER_RULE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER_RULE d ");
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

