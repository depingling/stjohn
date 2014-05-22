
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BaseCostDataAccess
 * Description:  This class is used to build access methods to the CLW_BASE_COST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BaseCostData;
import com.cleanwise.service.api.value.BaseCostDataVector;
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
 * <code>BaseCostDataAccess</code>
 */
public class BaseCostDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BaseCostDataAccess.class.getName());

    /** <code>CLW_BASE_COST</code> table name */
	/* Primary key: BASE_COST_ID */
	
    public static final String CLW_BASE_COST = "CLW_BASE_COST";
    
    /** <code>BASE_COST_ID</code> BASE_COST_ID column of table CLW_BASE_COST */
    public static final String BASE_COST_ID = "BASE_COST_ID";
    /** <code>GROUP_ID</code> GROUP_ID column of table CLW_BASE_COST */
    public static final String GROUP_ID = "GROUP_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_BASE_COST */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>BASE_COST</code> BASE_COST column of table CLW_BASE_COST */
    public static final String BASE_COST = "BASE_COST";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_BASE_COST */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_BASE_COST */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>REV_DATE</code> REV_DATE column of table CLW_BASE_COST */
    public static final String REV_DATE = "REV_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BASE_COST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BASE_COST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BASE_COST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BASE_COST */
    public static final String MOD_BY = "MOD_BY";
    /** <code>DISTRIBUTOR_ID</code> DISTRIBUTOR_ID column of table CLW_BASE_COST */
    public static final String DISTRIBUTOR_ID = "DISTRIBUTOR_ID";

    /**
     * Constructor.
     */
    public BaseCostDataAccess()
    {
    }

    /**
     * Gets a BaseCostData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBaseCostId The key requested.
     * @return new BaseCostData()
     * @throws            SQLException
     */
    public static BaseCostData select(Connection pCon, int pBaseCostId)
        throws SQLException, DataNotFoundException {
        BaseCostData x=null;
        String sql="SELECT BASE_COST_ID,GROUP_ID,ITEM_ID,BASE_COST,EFF_DATE,EXP_DATE,REV_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID FROM CLW_BASE_COST WHERE BASE_COST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBaseCostId=" + pBaseCostId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBaseCostId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BaseCostData.createValue();
            
            x.setBaseCostId(rs.getInt(1));
            x.setGroupId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setBaseCost(rs.getBigDecimal(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setRevDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setDistributorId(rs.getInt(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BASE_COST_ID :" + pBaseCostId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BaseCostDataVector object that consists
     * of BaseCostData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BaseCostDataVector()
     * @throws            SQLException
     */
    public static BaseCostDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BaseCostData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BASE_COST.BASE_COST_ID,CLW_BASE_COST.GROUP_ID,CLW_BASE_COST.ITEM_ID,CLW_BASE_COST.BASE_COST,CLW_BASE_COST.EFF_DATE,CLW_BASE_COST.EXP_DATE,CLW_BASE_COST.REV_DATE,CLW_BASE_COST.ADD_DATE,CLW_BASE_COST.ADD_BY,CLW_BASE_COST.MOD_DATE,CLW_BASE_COST.MOD_BY,CLW_BASE_COST.DISTRIBUTOR_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BaseCostData Object.
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
    *@returns a populated BaseCostData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BaseCostData x = BaseCostData.createValue();
         
         x.setBaseCostId(rs.getInt(1+offset));
         x.setGroupId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setBaseCost(rs.getBigDecimal(4+offset));
         x.setEffDate(rs.getDate(5+offset));
         x.setExpDate(rs.getDate(6+offset));
         x.setRevDate(rs.getDate(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         x.setDistributorId(rs.getInt(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BaseCostData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a BaseCostDataVector object that consists
     * of BaseCostData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BaseCostDataVector()
     * @throws            SQLException
     */
    public static BaseCostDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BASE_COST_ID,GROUP_ID,ITEM_ID,BASE_COST,EFF_DATE,EXP_DATE,REV_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID FROM CLW_BASE_COST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BASE_COST.BASE_COST_ID,CLW_BASE_COST.GROUP_ID,CLW_BASE_COST.ITEM_ID,CLW_BASE_COST.BASE_COST,CLW_BASE_COST.EFF_DATE,CLW_BASE_COST.EXP_DATE,CLW_BASE_COST.REV_DATE,CLW_BASE_COST.ADD_DATE,CLW_BASE_COST.ADD_BY,CLW_BASE_COST.MOD_DATE,CLW_BASE_COST.MOD_BY,CLW_BASE_COST.DISTRIBUTOR_ID FROM CLW_BASE_COST");
                where = pCriteria.getSqlClause("CLW_BASE_COST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BASE_COST.equals(otherTable)){
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
        BaseCostDataVector v = new BaseCostDataVector();
        while (rs.next()) {
            BaseCostData x = BaseCostData.createValue();
            
            x.setBaseCostId(rs.getInt(1));
            x.setGroupId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setBaseCost(rs.getBigDecimal(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setRevDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setDistributorId(rs.getInt(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BaseCostDataVector object that consists
     * of BaseCostData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BaseCostData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BaseCostDataVector()
     * @throws            SQLException
     */
    public static BaseCostDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BaseCostDataVector v = new BaseCostDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BASE_COST_ID,GROUP_ID,ITEM_ID,BASE_COST,EFF_DATE,EXP_DATE,REV_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID FROM CLW_BASE_COST WHERE BASE_COST_ID IN (");

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
            BaseCostData x=null;
            while (rs.next()) {
                // build the object
                x=BaseCostData.createValue();
                
                x.setBaseCostId(rs.getInt(1));
                x.setGroupId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setBaseCost(rs.getBigDecimal(4));
                x.setEffDate(rs.getDate(5));
                x.setExpDate(rs.getDate(6));
                x.setRevDate(rs.getDate(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                x.setDistributorId(rs.getInt(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BaseCostDataVector object of all
     * BaseCostData objects in the database.
     * @param pCon An open database connection.
     * @return new BaseCostDataVector()
     * @throws            SQLException
     */
    public static BaseCostDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BASE_COST_ID,GROUP_ID,ITEM_ID,BASE_COST,EFF_DATE,EXP_DATE,REV_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID FROM CLW_BASE_COST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BaseCostDataVector v = new BaseCostDataVector();
        BaseCostData x = null;
        while (rs.next()) {
            // build the object
            x = BaseCostData.createValue();
            
            x.setBaseCostId(rs.getInt(1));
            x.setGroupId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setBaseCost(rs.getBigDecimal(4));
            x.setEffDate(rs.getDate(5));
            x.setExpDate(rs.getDate(6));
            x.setRevDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setDistributorId(rs.getInt(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BaseCostData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BASE_COST_ID FROM CLW_BASE_COST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BASE_COST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BASE_COST");
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
     * Inserts a BaseCostData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BaseCostData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BaseCostData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BaseCostData insert(Connection pCon, BaseCostData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BASE_COST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BASE_COST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBaseCostId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BASE_COST (BASE_COST_ID,GROUP_ID,ITEM_ID,BASE_COST,EFF_DATE,EXP_DATE,REV_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBaseCostId());
        pstmt.setInt(2,pData.getGroupId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setBigDecimal(4,pData.getBaseCost());
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getRevDate()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());
        pstmt.setInt(12,pData.getDistributorId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BASE_COST_ID="+pData.getBaseCostId());
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   BASE_COST="+pData.getBaseCost());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   REV_DATE="+pData.getRevDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setBaseCostId(0);
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
     * Updates a BaseCostData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BaseCostData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BaseCostData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BASE_COST SET GROUP_ID = ?,ITEM_ID = ?,BASE_COST = ?,EFF_DATE = ?,EXP_DATE = ?,REV_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,DISTRIBUTOR_ID = ? WHERE BASE_COST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getGroupId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setBigDecimal(i++,pData.getBaseCost());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getRevDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getDistributorId());
        pstmt.setInt(i++,pData.getBaseCostId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GROUP_ID="+pData.getGroupId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   BASE_COST="+pData.getBaseCost());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   REV_DATE="+pData.getRevDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DISTRIBUTOR_ID="+pData.getDistributorId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a BaseCostData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBaseCostId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBaseCostId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BASE_COST WHERE BASE_COST_ID = " + pBaseCostId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BaseCostData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BASE_COST");
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
     * Inserts a BaseCostData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BaseCostData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BaseCostData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BASE_COST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BASE_COST_ID,GROUP_ID,ITEM_ID,BASE_COST,EFF_DATE,EXP_DATE,REV_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISTRIBUTOR_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBaseCostId());
        pstmt.setInt(2+4,pData.getGroupId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setBigDecimal(4+4,pData.getBaseCost());
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getRevDate()));
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());
        pstmt.setInt(12+4,pData.getDistributorId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BaseCostData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BaseCostData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BaseCostData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BaseCostData insert(Connection pCon, BaseCostData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BaseCostData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BaseCostData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BaseCostData pData, boolean pLogFl)
        throws SQLException {
        BaseCostData oldData = null;
        if(pLogFl) {
          int id = pData.getBaseCostId();
          try {
          oldData = BaseCostDataAccess.select(pCon,id);
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
     * Deletes a BaseCostData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBaseCostId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBaseCostId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BASE_COST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BASE_COST d WHERE BASE_COST_ID = " + pBaseCostId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBaseCostId);
        return n;
     }

    /**
     * Deletes BaseCostData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BASE_COST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BASE_COST d ");
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

