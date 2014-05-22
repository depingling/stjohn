
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BudgetDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_BUDGET_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
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
 * <code>BudgetDetailDataAccess</code>
 */
public class BudgetDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BudgetDetailDataAccess.class.getName());

    /** <code>CLW_BUDGET_DETAIL</code> table name */
	/* Primary key: BUDGET_DETAIL_ID */
	
    public static final String CLW_BUDGET_DETAIL = "CLW_BUDGET_DETAIL";
    
    /** <code>BUDGET_DETAIL_ID</code> BUDGET_DETAIL_ID column of table CLW_BUDGET_DETAIL */
    public static final String BUDGET_DETAIL_ID = "BUDGET_DETAIL_ID";
    /** <code>BUDGET_ID</code> BUDGET_ID column of table CLW_BUDGET_DETAIL */
    public static final String BUDGET_ID = "BUDGET_ID";
    /** <code>PERIOD</code> PERIOD column of table CLW_BUDGET_DETAIL */
    public static final String PERIOD = "PERIOD";
    /** <code>AMOUNT</code> AMOUNT column of table CLW_BUDGET_DETAIL */
    public static final String AMOUNT = "AMOUNT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BUDGET_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BUDGET_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BUDGET_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BUDGET_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public BudgetDetailDataAccess()
    {
    }

    /**
     * Gets a BudgetDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBudgetDetailId The key requested.
     * @return new BudgetDetailData()
     * @throws            SQLException
     */
    public static BudgetDetailData select(Connection pCon, int pBudgetDetailId)
        throws SQLException, DataNotFoundException {
        BudgetDetailData x=null;
        String sql="SELECT BUDGET_DETAIL_ID,BUDGET_ID,PERIOD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUDGET_DETAIL WHERE BUDGET_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBudgetDetailId=" + pBudgetDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBudgetDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BudgetDetailData.createValue();
            
            x.setBudgetDetailId(rs.getInt(1));
            x.setBudgetId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setAmount(rs.getBigDecimal(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BUDGET_DETAIL_ID :" + pBudgetDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BudgetDetailDataVector object that consists
     * of BudgetDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BudgetDetailDataVector()
     * @throws            SQLException
     */
    public static BudgetDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BudgetDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BUDGET_DETAIL.BUDGET_DETAIL_ID,CLW_BUDGET_DETAIL.BUDGET_ID,CLW_BUDGET_DETAIL.PERIOD,CLW_BUDGET_DETAIL.AMOUNT,CLW_BUDGET_DETAIL.ADD_DATE,CLW_BUDGET_DETAIL.ADD_BY,CLW_BUDGET_DETAIL.MOD_DATE,CLW_BUDGET_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BudgetDetailData Object.
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
    *@returns a populated BudgetDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BudgetDetailData x = BudgetDetailData.createValue();
         
         x.setBudgetDetailId(rs.getInt(1+offset));
         x.setBudgetId(rs.getInt(2+offset));
         x.setPeriod(rs.getInt(3+offset));
         x.setAmount(rs.getBigDecimal(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BudgetDetailData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a BudgetDetailDataVector object that consists
     * of BudgetDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BudgetDetailDataVector()
     * @throws            SQLException
     */
    public static BudgetDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BUDGET_DETAIL_ID,BUDGET_ID,PERIOD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUDGET_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BUDGET_DETAIL.BUDGET_DETAIL_ID,CLW_BUDGET_DETAIL.BUDGET_ID,CLW_BUDGET_DETAIL.PERIOD,CLW_BUDGET_DETAIL.AMOUNT,CLW_BUDGET_DETAIL.ADD_DATE,CLW_BUDGET_DETAIL.ADD_BY,CLW_BUDGET_DETAIL.MOD_DATE,CLW_BUDGET_DETAIL.MOD_BY FROM CLW_BUDGET_DETAIL");
                where = pCriteria.getSqlClause("CLW_BUDGET_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BUDGET_DETAIL.equals(otherTable)){
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
        BudgetDetailDataVector v = new BudgetDetailDataVector();
        while (rs.next()) {
            BudgetDetailData x = BudgetDetailData.createValue();
            
            x.setBudgetDetailId(rs.getInt(1));
            x.setBudgetId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setAmount(rs.getBigDecimal(4));
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
     * Gets a BudgetDetailDataVector object that consists
     * of BudgetDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BudgetDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BudgetDetailDataVector()
     * @throws            SQLException
     */
    public static BudgetDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BudgetDetailDataVector v = new BudgetDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BUDGET_DETAIL_ID,BUDGET_ID,PERIOD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUDGET_DETAIL WHERE BUDGET_DETAIL_ID IN (");

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
            BudgetDetailData x=null;
            while (rs.next()) {
                // build the object
                x=BudgetDetailData.createValue();
                
                x.setBudgetDetailId(rs.getInt(1));
                x.setBudgetId(rs.getInt(2));
                x.setPeriod(rs.getInt(3));
                x.setAmount(rs.getBigDecimal(4));
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
     * Gets a BudgetDetailDataVector object of all
     * BudgetDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new BudgetDetailDataVector()
     * @throws            SQLException
     */
    public static BudgetDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BUDGET_DETAIL_ID,BUDGET_ID,PERIOD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_BUDGET_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BudgetDetailDataVector v = new BudgetDetailDataVector();
        BudgetDetailData x = null;
        while (rs.next()) {
            // build the object
            x = BudgetDetailData.createValue();
            
            x.setBudgetDetailId(rs.getInt(1));
            x.setBudgetId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setAmount(rs.getBigDecimal(4));
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
     * BudgetDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BUDGET_DETAIL_ID FROM CLW_BUDGET_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUDGET_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BUDGET_DETAIL");
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
     * Inserts a BudgetDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BudgetDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BudgetDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BudgetDetailData insert(Connection pCon, BudgetDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BUDGET_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BUDGET_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBudgetDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BUDGET_DETAIL (BUDGET_DETAIL_ID,BUDGET_ID,PERIOD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBudgetDetailId());
        pstmt.setInt(2,pData.getBudgetId());
        pstmt.setInt(3,pData.getPeriod());
        pstmt.setBigDecimal(4,pData.getAmount());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUDGET_DETAIL_ID="+pData.getBudgetDetailId());
            log.debug("SQL:   BUDGET_ID="+pData.getBudgetId());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
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
        pData.setBudgetDetailId(0);
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
     * Updates a BudgetDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BudgetDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BudgetDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BUDGET_DETAIL SET BUDGET_ID = ?,PERIOD = ?,AMOUNT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE BUDGET_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBudgetId());
        pstmt.setInt(i++,pData.getPeriod());
        pstmt.setBigDecimal(i++,pData.getAmount());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBudgetDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUDGET_ID="+pData.getBudgetId());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
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
     * Deletes a BudgetDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBudgetDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBudgetDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BUDGET_DETAIL WHERE BUDGET_DETAIL_ID = " + pBudgetDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BudgetDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BUDGET_DETAIL");
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
     * Inserts a BudgetDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BudgetDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BudgetDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BUDGET_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BUDGET_DETAIL_ID,BUDGET_ID,PERIOD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBudgetDetailId());
        pstmt.setInt(2+4,pData.getBudgetId());
        pstmt.setInt(3+4,pData.getPeriod());
        pstmt.setBigDecimal(4+4,pData.getAmount());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BudgetDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BudgetDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BudgetDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BudgetDetailData insert(Connection pCon, BudgetDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BudgetDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BudgetDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BudgetDetailData pData, boolean pLogFl)
        throws SQLException {
        BudgetDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getBudgetDetailId();
          try {
          oldData = BudgetDetailDataAccess.select(pCon,id);
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
     * Deletes a BudgetDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBudgetDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBudgetDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BUDGET_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUDGET_DETAIL d WHERE BUDGET_DETAIL_ID = " + pBudgetDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBudgetDetailId);
        return n;
     }

    /**
     * Deletes BudgetDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BUDGET_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BUDGET_DETAIL d ");
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

