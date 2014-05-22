
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PriceRuleDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_PRICE_RULE_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PriceRuleDetailData;
import com.cleanwise.service.api.value.PriceRuleDetailDataVector;
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
 * <code>PriceRuleDetailDataAccess</code>
 */
public class PriceRuleDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PriceRuleDetailDataAccess.class.getName());

    /** <code>CLW_PRICE_RULE_DETAIL</code> table name */
	/* Primary key: PRICE_RULE_DETAIL_ID */
	
    public static final String CLW_PRICE_RULE_DETAIL = "CLW_PRICE_RULE_DETAIL";
    
    /** <code>PRICE_RULE_DETAIL_ID</code> PRICE_RULE_DETAIL_ID column of table CLW_PRICE_RULE_DETAIL */
    public static final String PRICE_RULE_DETAIL_ID = "PRICE_RULE_DETAIL_ID";
    /** <code>PRICE_RULE_ID</code> PRICE_RULE_ID column of table CLW_PRICE_RULE_DETAIL */
    public static final String PRICE_RULE_ID = "PRICE_RULE_ID";
    /** <code>PARAM_NAME</code> PARAM_NAME column of table CLW_PRICE_RULE_DETAIL */
    public static final String PARAM_NAME = "PARAM_NAME";
    /** <code>PARAM_VALUE</code> PARAM_VALUE column of table CLW_PRICE_RULE_DETAIL */
    public static final String PARAM_VALUE = "PARAM_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRICE_RULE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRICE_RULE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRICE_RULE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRICE_RULE_DETAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_PRICE_RULE_DETAIL */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";

    /**
     * Constructor.
     */
    public PriceRuleDetailDataAccess()
    {
    }

    /**
     * Gets a PriceRuleDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPriceRuleDetailId The key requested.
     * @return new PriceRuleDetailData()
     * @throws            SQLException
     */
    public static PriceRuleDetailData select(Connection pCon, int pPriceRuleDetailId)
        throws SQLException, DataNotFoundException {
        PriceRuleDetailData x=null;
        String sql="SELECT PRICE_RULE_DETAIL_ID,PRICE_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BUS_ENTITY_ID FROM CLW_PRICE_RULE_DETAIL WHERE PRICE_RULE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPriceRuleDetailId=" + pPriceRuleDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPriceRuleDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PriceRuleDetailData.createValue();
            
            x.setPriceRuleDetailId(rs.getInt(1));
            x.setPriceRuleId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setBusEntityId(rs.getInt(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRICE_RULE_DETAIL_ID :" + pPriceRuleDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PriceRuleDetailDataVector object that consists
     * of PriceRuleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PriceRuleDetailDataVector()
     * @throws            SQLException
     */
    public static PriceRuleDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PriceRuleDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRICE_RULE_DETAIL.PRICE_RULE_DETAIL_ID,CLW_PRICE_RULE_DETAIL.PRICE_RULE_ID,CLW_PRICE_RULE_DETAIL.PARAM_NAME,CLW_PRICE_RULE_DETAIL.PARAM_VALUE,CLW_PRICE_RULE_DETAIL.ADD_DATE,CLW_PRICE_RULE_DETAIL.ADD_BY,CLW_PRICE_RULE_DETAIL.MOD_DATE,CLW_PRICE_RULE_DETAIL.MOD_BY,CLW_PRICE_RULE_DETAIL.BUS_ENTITY_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PriceRuleDetailData Object.
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
    *@returns a populated PriceRuleDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PriceRuleDetailData x = PriceRuleDetailData.createValue();
         
         x.setPriceRuleDetailId(rs.getInt(1+offset));
         x.setPriceRuleId(rs.getInt(2+offset));
         x.setParamName(rs.getString(3+offset));
         x.setParamValue(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setBusEntityId(rs.getInt(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PriceRuleDetailData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a PriceRuleDetailDataVector object that consists
     * of PriceRuleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PriceRuleDetailDataVector()
     * @throws            SQLException
     */
    public static PriceRuleDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRICE_RULE_DETAIL_ID,PRICE_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BUS_ENTITY_ID FROM CLW_PRICE_RULE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRICE_RULE_DETAIL.PRICE_RULE_DETAIL_ID,CLW_PRICE_RULE_DETAIL.PRICE_RULE_ID,CLW_PRICE_RULE_DETAIL.PARAM_NAME,CLW_PRICE_RULE_DETAIL.PARAM_VALUE,CLW_PRICE_RULE_DETAIL.ADD_DATE,CLW_PRICE_RULE_DETAIL.ADD_BY,CLW_PRICE_RULE_DETAIL.MOD_DATE,CLW_PRICE_RULE_DETAIL.MOD_BY,CLW_PRICE_RULE_DETAIL.BUS_ENTITY_ID FROM CLW_PRICE_RULE_DETAIL");
                where = pCriteria.getSqlClause("CLW_PRICE_RULE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_RULE_DETAIL.equals(otherTable)){
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
        PriceRuleDetailDataVector v = new PriceRuleDetailDataVector();
        while (rs.next()) {
            PriceRuleDetailData x = PriceRuleDetailData.createValue();
            
            x.setPriceRuleDetailId(rs.getInt(1));
            x.setPriceRuleId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setBusEntityId(rs.getInt(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PriceRuleDetailDataVector object that consists
     * of PriceRuleDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PriceRuleDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PriceRuleDetailDataVector()
     * @throws            SQLException
     */
    public static PriceRuleDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PriceRuleDetailDataVector v = new PriceRuleDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRICE_RULE_DETAIL_ID,PRICE_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BUS_ENTITY_ID FROM CLW_PRICE_RULE_DETAIL WHERE PRICE_RULE_DETAIL_ID IN (");

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
            PriceRuleDetailData x=null;
            while (rs.next()) {
                // build the object
                x=PriceRuleDetailData.createValue();
                
                x.setPriceRuleDetailId(rs.getInt(1));
                x.setPriceRuleId(rs.getInt(2));
                x.setParamName(rs.getString(3));
                x.setParamValue(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setBusEntityId(rs.getInt(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PriceRuleDetailDataVector object of all
     * PriceRuleDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new PriceRuleDetailDataVector()
     * @throws            SQLException
     */
    public static PriceRuleDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRICE_RULE_DETAIL_ID,PRICE_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BUS_ENTITY_ID FROM CLW_PRICE_RULE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PriceRuleDetailDataVector v = new PriceRuleDetailDataVector();
        PriceRuleDetailData x = null;
        while (rs.next()) {
            // build the object
            x = PriceRuleDetailData.createValue();
            
            x.setPriceRuleDetailId(rs.getInt(1));
            x.setPriceRuleId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setBusEntityId(rs.getInt(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PriceRuleDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRICE_RULE_DETAIL_ID FROM CLW_PRICE_RULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_RULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_RULE_DETAIL");
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
     * Inserts a PriceRuleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceRuleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PriceRuleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceRuleDetailData insert(Connection pCon, PriceRuleDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRICE_RULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRICE_RULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPriceRuleDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRICE_RULE_DETAIL (PRICE_RULE_DETAIL_ID,PRICE_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BUS_ENTITY_ID) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPriceRuleDetailId());
        pstmt.setInt(2,pData.getPriceRuleId());
        pstmt.setString(3,pData.getParamName());
        pstmt.setString(4,pData.getParamValue());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setInt(9,pData.getBusEntityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRICE_RULE_DETAIL_ID="+pData.getPriceRuleDetailId());
            log.debug("SQL:   PRICE_RULE_ID="+pData.getPriceRuleId());
            log.debug("SQL:   PARAM_NAME="+pData.getParamName());
            log.debug("SQL:   PARAM_VALUE="+pData.getParamValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPriceRuleDetailId(0);
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
     * Updates a PriceRuleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceRuleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceRuleDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRICE_RULE_DETAIL SET PRICE_RULE_ID = ?,PARAM_NAME = ?,PARAM_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,BUS_ENTITY_ID = ? WHERE PRICE_RULE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getPriceRuleId());
        pstmt.setString(i++,pData.getParamName());
        pstmt.setString(i++,pData.getParamValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getPriceRuleDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRICE_RULE_ID="+pData.getPriceRuleId());
            log.debug("SQL:   PARAM_NAME="+pData.getParamName());
            log.debug("SQL:   PARAM_VALUE="+pData.getParamValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PriceRuleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceRuleDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceRuleDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRICE_RULE_DETAIL WHERE PRICE_RULE_DETAIL_ID = " + pPriceRuleDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PriceRuleDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRICE_RULE_DETAIL");
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
     * Inserts a PriceRuleDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceRuleDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PriceRuleDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRICE_RULE_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRICE_RULE_DETAIL_ID,PRICE_RULE_ID,PARAM_NAME,PARAM_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,BUS_ENTITY_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPriceRuleDetailId());
        pstmt.setInt(2+4,pData.getPriceRuleId());
        pstmt.setString(3+4,pData.getParamName());
        pstmt.setString(4+4,pData.getParamValue());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setInt(9+4,pData.getBusEntityId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PriceRuleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceRuleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PriceRuleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceRuleDetailData insert(Connection pCon, PriceRuleDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PriceRuleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceRuleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceRuleDetailData pData, boolean pLogFl)
        throws SQLException {
        PriceRuleDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getPriceRuleDetailId();
          try {
          oldData = PriceRuleDetailDataAccess.select(pCon,id);
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
     * Deletes a PriceRuleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceRuleDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceRuleDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRICE_RULE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_RULE_DETAIL d WHERE PRICE_RULE_DETAIL_ID = " + pPriceRuleDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPriceRuleDetailId);
        return n;
     }

    /**
     * Deletes PriceRuleDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRICE_RULE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_RULE_DETAIL d ");
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

