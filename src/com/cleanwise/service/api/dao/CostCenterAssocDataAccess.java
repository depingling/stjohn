
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CostCenterAssocDataAccess
 * Description:  This class is used to build access methods to the CLW_COST_CENTER_ASSOC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CostCenterAssocData;
import com.cleanwise.service.api.value.CostCenterAssocDataVector;
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
 * <code>CostCenterAssocDataAccess</code>
 */
public class CostCenterAssocDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CostCenterAssocDataAccess.class.getName());

    /** <code>CLW_COST_CENTER_ASSOC</code> table name */
	/* Primary key: COST_CENTER_ASSOC_ID */
	
    public static final String CLW_COST_CENTER_ASSOC = "CLW_COST_CENTER_ASSOC";
    
    /** <code>COST_CENTER_ASSOC_ID</code> COST_CENTER_ASSOC_ID column of table CLW_COST_CENTER_ASSOC */
    public static final String COST_CENTER_ASSOC_ID = "COST_CENTER_ASSOC_ID";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_COST_CENTER_ASSOC */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_COST_CENTER_ASSOC */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>COST_CENTER_ASSOC_CD</code> COST_CENTER_ASSOC_CD column of table CLW_COST_CENTER_ASSOC */
    public static final String COST_CENTER_ASSOC_CD = "COST_CENTER_ASSOC_CD";
    /** <code>BUDGET_THRESHOLD</code> BUDGET_THRESHOLD column of table CLW_COST_CENTER_ASSOC */
    public static final String BUDGET_THRESHOLD = "BUDGET_THRESHOLD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_COST_CENTER_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_COST_CENTER_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_COST_CENTER_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_COST_CENTER_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CostCenterAssocDataAccess()
    {
    }

    /**
     * Gets a CostCenterAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCostCenterAssocId The key requested.
     * @return new CostCenterAssocData()
     * @throws            SQLException
     */
    public static CostCenterAssocData select(Connection pCon, int pCostCenterAssocId)
        throws SQLException, DataNotFoundException {
        CostCenterAssocData x=null;
        String sql="SELECT COST_CENTER_ASSOC_ID,COST_CENTER_ID,CATALOG_ID,COST_CENTER_ASSOC_CD,BUDGET_THRESHOLD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_ASSOC WHERE COST_CENTER_ASSOC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCostCenterAssocId=" + pCostCenterAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCostCenterAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CostCenterAssocData.createValue();
            
            x.setCostCenterAssocId(rs.getInt(1));
            x.setCostCenterId(rs.getInt(2));
            x.setCatalogId(rs.getInt(3));
            x.setCostCenterAssocCd(rs.getString(4));
            x.setBudgetThreshold(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("COST_CENTER_ASSOC_ID :" + pCostCenterAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CostCenterAssocDataVector object that consists
     * of CostCenterAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CostCenterAssocDataVector()
     * @throws            SQLException
     */
    public static CostCenterAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CostCenterAssocData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_COST_CENTER_ASSOC.COST_CENTER_ASSOC_ID,CLW_COST_CENTER_ASSOC.COST_CENTER_ID,CLW_COST_CENTER_ASSOC.CATALOG_ID,CLW_COST_CENTER_ASSOC.COST_CENTER_ASSOC_CD,CLW_COST_CENTER_ASSOC.BUDGET_THRESHOLD,CLW_COST_CENTER_ASSOC.ADD_DATE,CLW_COST_CENTER_ASSOC.ADD_BY,CLW_COST_CENTER_ASSOC.MOD_DATE,CLW_COST_CENTER_ASSOC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CostCenterAssocData Object.
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
    *@returns a populated CostCenterAssocData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CostCenterAssocData x = CostCenterAssocData.createValue();
         
         x.setCostCenterAssocId(rs.getInt(1+offset));
         x.setCostCenterId(rs.getInt(2+offset));
         x.setCatalogId(rs.getInt(3+offset));
         x.setCostCenterAssocCd(rs.getString(4+offset));
         x.setBudgetThreshold(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CostCenterAssocData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a CostCenterAssocDataVector object that consists
     * of CostCenterAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CostCenterAssocDataVector()
     * @throws            SQLException
     */
    public static CostCenterAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT COST_CENTER_ASSOC_ID,COST_CENTER_ID,CATALOG_ID,COST_CENTER_ASSOC_CD,BUDGET_THRESHOLD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_ASSOC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_COST_CENTER_ASSOC.COST_CENTER_ASSOC_ID,CLW_COST_CENTER_ASSOC.COST_CENTER_ID,CLW_COST_CENTER_ASSOC.CATALOG_ID,CLW_COST_CENTER_ASSOC.COST_CENTER_ASSOC_CD,CLW_COST_CENTER_ASSOC.BUDGET_THRESHOLD,CLW_COST_CENTER_ASSOC.ADD_DATE,CLW_COST_CENTER_ASSOC.ADD_BY,CLW_COST_CENTER_ASSOC.MOD_DATE,CLW_COST_CENTER_ASSOC.MOD_BY FROM CLW_COST_CENTER_ASSOC");
                where = pCriteria.getSqlClause("CLW_COST_CENTER_ASSOC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COST_CENTER_ASSOC.equals(otherTable)){
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
        CostCenterAssocDataVector v = new CostCenterAssocDataVector();
        while (rs.next()) {
            CostCenterAssocData x = CostCenterAssocData.createValue();
            
            x.setCostCenterAssocId(rs.getInt(1));
            x.setCostCenterId(rs.getInt(2));
            x.setCatalogId(rs.getInt(3));
            x.setCostCenterAssocCd(rs.getString(4));
            x.setBudgetThreshold(rs.getString(5));
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
     * Gets a CostCenterAssocDataVector object that consists
     * of CostCenterAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CostCenterAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CostCenterAssocDataVector()
     * @throws            SQLException
     */
    public static CostCenterAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CostCenterAssocDataVector v = new CostCenterAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_ASSOC_ID,COST_CENTER_ID,CATALOG_ID,COST_CENTER_ASSOC_CD,BUDGET_THRESHOLD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_ASSOC WHERE COST_CENTER_ASSOC_ID IN (");

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
            CostCenterAssocData x=null;
            while (rs.next()) {
                // build the object
                x=CostCenterAssocData.createValue();
                
                x.setCostCenterAssocId(rs.getInt(1));
                x.setCostCenterId(rs.getInt(2));
                x.setCatalogId(rs.getInt(3));
                x.setCostCenterAssocCd(rs.getString(4));
                x.setBudgetThreshold(rs.getString(5));
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
     * Gets a CostCenterAssocDataVector object of all
     * CostCenterAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new CostCenterAssocDataVector()
     * @throws            SQLException
     */
    public static CostCenterAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT COST_CENTER_ASSOC_ID,COST_CENTER_ID,CATALOG_ID,COST_CENTER_ASSOC_CD,BUDGET_THRESHOLD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COST_CENTER_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CostCenterAssocDataVector v = new CostCenterAssocDataVector();
        CostCenterAssocData x = null;
        while (rs.next()) {
            // build the object
            x = CostCenterAssocData.createValue();
            
            x.setCostCenterAssocId(rs.getInt(1));
            x.setCostCenterId(rs.getInt(2));
            x.setCatalogId(rs.getInt(3));
            x.setCostCenterAssocCd(rs.getString(4));
            x.setBudgetThreshold(rs.getString(5));
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
     * CostCenterAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT COST_CENTER_ASSOC_ID FROM CLW_COST_CENTER_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COST_CENTER_ASSOC");
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
     * Inserts a CostCenterAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CostCenterAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterAssocData insert(Connection pCon, CostCenterAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_COST_CENTER_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_COST_CENTER_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCostCenterAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_COST_CENTER_ASSOC (COST_CENTER_ASSOC_ID,COST_CENTER_ID,CATALOG_ID,COST_CENTER_ASSOC_CD,BUDGET_THRESHOLD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCostCenterAssocId());
        pstmt.setInt(2,pData.getCostCenterId());
        pstmt.setInt(3,pData.getCatalogId());
        pstmt.setString(4,pData.getCostCenterAssocCd());
        pstmt.setString(5,pData.getBudgetThreshold());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COST_CENTER_ASSOC_ID="+pData.getCostCenterAssocId());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   COST_CENTER_ASSOC_CD="+pData.getCostCenterAssocCd());
            log.debug("SQL:   BUDGET_THRESHOLD="+pData.getBudgetThreshold());
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
        pData.setCostCenterAssocId(0);
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
     * Updates a CostCenterAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_COST_CENTER_ASSOC SET COST_CENTER_ID = ?,CATALOG_ID = ?,COST_CENTER_ASSOC_CD = ?,BUDGET_THRESHOLD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE COST_CENTER_ASSOC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCostCenterId());
        pstmt.setInt(i++,pData.getCatalogId());
        pstmt.setString(i++,pData.getCostCenterAssocCd());
        pstmt.setString(i++,pData.getBudgetThreshold());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCostCenterAssocId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   COST_CENTER_ASSOC_CD="+pData.getCostCenterAssocCd());
            log.debug("SQL:   BUDGET_THRESHOLD="+pData.getBudgetThreshold());
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
     * Deletes a CostCenterAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterAssocId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_COST_CENTER_ASSOC WHERE COST_CENTER_ASSOC_ID = " + pCostCenterAssocId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CostCenterAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_COST_CENTER_ASSOC");
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
     * Inserts a CostCenterAssocData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterAssocData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CostCenterAssocData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_COST_CENTER_ASSOC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "COST_CENTER_ASSOC_ID,COST_CENTER_ID,CATALOG_ID,COST_CENTER_ASSOC_CD,BUDGET_THRESHOLD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCostCenterAssocId());
        pstmt.setInt(2+4,pData.getCostCenterId());
        pstmt.setInt(3+4,pData.getCatalogId());
        pstmt.setString(4+4,pData.getCostCenterAssocCd());
        pstmt.setString(5+4,pData.getBudgetThreshold());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CostCenterAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CostCenterAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CostCenterAssocData insert(Connection pCon, CostCenterAssocData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CostCenterAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CostCenterAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CostCenterAssocData pData, boolean pLogFl)
        throws SQLException {
        CostCenterAssocData oldData = null;
        if(pLogFl) {
          int id = pData.getCostCenterAssocId();
          try {
          oldData = CostCenterAssocDataAccess.select(pCon,id);
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
     * Deletes a CostCenterAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCostCenterAssocId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCostCenterAssocId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_COST_CENTER_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER_ASSOC d WHERE COST_CENTER_ASSOC_ID = " + pCostCenterAssocId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCostCenterAssocId);
        return n;
     }

    /**
     * Deletes CostCenterAssocData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_COST_CENTER_ASSOC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COST_CENTER_ASSOC d ");
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

