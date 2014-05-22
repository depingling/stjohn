
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PriceListDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_PRICE_LIST_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PriceListDetailData;
import com.cleanwise.service.api.value.PriceListDetailDataVector;
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
 * <code>PriceListDetailDataAccess</code>
 */
public class PriceListDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PriceListDetailDataAccess.class.getName());

    /** <code>CLW_PRICE_LIST_DETAIL</code> table name */
	/* Primary key: PRICE_LIST_DETAIL_ID */
	
    public static final String CLW_PRICE_LIST_DETAIL = "CLW_PRICE_LIST_DETAIL";
    
    /** <code>PRICE_LIST_DETAIL_ID</code> PRICE_LIST_DETAIL_ID column of table CLW_PRICE_LIST_DETAIL */
    public static final String PRICE_LIST_DETAIL_ID = "PRICE_LIST_DETAIL_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_PRICE_LIST_DETAIL */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>PRICE_LIST_ID</code> PRICE_LIST_ID column of table CLW_PRICE_LIST_DETAIL */
    public static final String PRICE_LIST_ID = "PRICE_LIST_ID";
    /** <code>PRICE</code> PRICE column of table CLW_PRICE_LIST_DETAIL */
    public static final String PRICE = "PRICE";
    /** <code>CUSTOMER_SKU_NUM</code> CUSTOMER_SKU_NUM column of table CLW_PRICE_LIST_DETAIL */
    public static final String CUSTOMER_SKU_NUM = "CUSTOMER_SKU_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRICE_LIST_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRICE_LIST_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRICE_LIST_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRICE_LIST_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public PriceListDetailDataAccess()
    {
    }

    /**
     * Gets a PriceListDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPriceListDetailId The key requested.
     * @return new PriceListDetailData()
     * @throws            SQLException
     */
    public static PriceListDetailData select(Connection pCon, int pPriceListDetailId)
        throws SQLException, DataNotFoundException {
        PriceListDetailData x=null;
        String sql="SELECT PRICE_LIST_DETAIL_ID,ITEM_ID,PRICE_LIST_ID,PRICE,CUSTOMER_SKU_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_DETAIL WHERE PRICE_LIST_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPriceListDetailId=" + pPriceListDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPriceListDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PriceListDetailData.createValue();
            
            x.setPriceListDetailId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setPriceListId(rs.getInt(3));
            x.setPrice(rs.getBigDecimal(4));
            x.setCustomerSkuNum(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRICE_LIST_DETAIL_ID :" + pPriceListDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PriceListDetailDataVector object that consists
     * of PriceListDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PriceListDetailDataVector()
     * @throws            SQLException
     */
    public static PriceListDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PriceListDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRICE_LIST_DETAIL.PRICE_LIST_DETAIL_ID,CLW_PRICE_LIST_DETAIL.ITEM_ID,CLW_PRICE_LIST_DETAIL.PRICE_LIST_ID,CLW_PRICE_LIST_DETAIL.PRICE,CLW_PRICE_LIST_DETAIL.CUSTOMER_SKU_NUM,CLW_PRICE_LIST_DETAIL.ADD_DATE,CLW_PRICE_LIST_DETAIL.ADD_BY,CLW_PRICE_LIST_DETAIL.MOD_DATE,CLW_PRICE_LIST_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PriceListDetailData Object.
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
    *@returns a populated PriceListDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PriceListDetailData x = PriceListDetailData.createValue();
         
         x.setPriceListDetailId(rs.getInt(1+offset));
         x.setItemId(rs.getInt(2+offset));
         x.setPriceListId(rs.getInt(3+offset));
         x.setPrice(rs.getBigDecimal(4+offset));
         x.setCustomerSkuNum(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PriceListDetailData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a PriceListDetailDataVector object that consists
     * of PriceListDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PriceListDetailDataVector()
     * @throws            SQLException
     */
    public static PriceListDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRICE_LIST_DETAIL_ID,ITEM_ID,PRICE_LIST_ID,PRICE,CUSTOMER_SKU_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRICE_LIST_DETAIL.PRICE_LIST_DETAIL_ID,CLW_PRICE_LIST_DETAIL.ITEM_ID,CLW_PRICE_LIST_DETAIL.PRICE_LIST_ID,CLW_PRICE_LIST_DETAIL.PRICE,CLW_PRICE_LIST_DETAIL.CUSTOMER_SKU_NUM,CLW_PRICE_LIST_DETAIL.ADD_DATE,CLW_PRICE_LIST_DETAIL.ADD_BY,CLW_PRICE_LIST_DETAIL.MOD_DATE,CLW_PRICE_LIST_DETAIL.MOD_BY FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_DETAIL.equals(otherTable)){
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
        PriceListDetailDataVector v = new PriceListDetailDataVector();
        while (rs.next()) {
            PriceListDetailData x = PriceListDetailData.createValue();
            
            x.setPriceListDetailId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setPriceListId(rs.getInt(3));
            x.setPrice(rs.getBigDecimal(4));
            x.setCustomerSkuNum(rs.getString(5));
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
     * Gets a PriceListDetailDataVector object that consists
     * of PriceListDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PriceListDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PriceListDetailDataVector()
     * @throws            SQLException
     */
    public static PriceListDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PriceListDetailDataVector v = new PriceListDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRICE_LIST_DETAIL_ID,ITEM_ID,PRICE_LIST_ID,PRICE,CUSTOMER_SKU_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_DETAIL WHERE PRICE_LIST_DETAIL_ID IN (");

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
            PriceListDetailData x=null;
            while (rs.next()) {
                // build the object
                x=PriceListDetailData.createValue();
                
                x.setPriceListDetailId(rs.getInt(1));
                x.setItemId(rs.getInt(2));
                x.setPriceListId(rs.getInt(3));
                x.setPrice(rs.getBigDecimal(4));
                x.setCustomerSkuNum(rs.getString(5));
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
     * Gets a PriceListDetailDataVector object of all
     * PriceListDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new PriceListDetailDataVector()
     * @throws            SQLException
     */
    public static PriceListDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRICE_LIST_DETAIL_ID,ITEM_ID,PRICE_LIST_ID,PRICE,CUSTOMER_SKU_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PRICE_LIST_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PriceListDetailDataVector v = new PriceListDetailDataVector();
        PriceListDetailData x = null;
        while (rs.next()) {
            // build the object
            x = PriceListDetailData.createValue();
            
            x.setPriceListDetailId(rs.getInt(1));
            x.setItemId(rs.getInt(2));
            x.setPriceListId(rs.getInt(3));
            x.setPrice(rs.getBigDecimal(4));
            x.setCustomerSkuNum(rs.getString(5));
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
     * PriceListDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT PRICE_LIST_DETAIL_ID FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT PRICE_LIST_DETAIL_ID FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_DETAIL.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_DETAIL.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRICE_LIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_PRICE_LIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRICE_LIST_DETAIL.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a PriceListDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PriceListDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceListDetailData insert(Connection pCon, PriceListDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRICE_LIST_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRICE_LIST_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPriceListDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRICE_LIST_DETAIL (PRICE_LIST_DETAIL_ID,ITEM_ID,PRICE_LIST_ID,PRICE,CUSTOMER_SKU_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPriceListDetailId());
        pstmt.setInt(2,pData.getItemId());
        pstmt.setInt(3,pData.getPriceListId());
        pstmt.setBigDecimal(4,pData.getPrice());
        pstmt.setString(5,pData.getCustomerSkuNum());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRICE_LIST_DETAIL_ID="+pData.getPriceListDetailId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   PRICE_LIST_ID="+pData.getPriceListId());
            log.debug("SQL:   PRICE="+pData.getPrice());
            log.debug("SQL:   CUSTOMER_SKU_NUM="+pData.getCustomerSkuNum());
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
        pData.setPriceListDetailId(0);
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
     * Updates a PriceListDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceListDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRICE_LIST_DETAIL SET ITEM_ID = ?,PRICE_LIST_ID = ?,PRICE = ?,CUSTOMER_SKU_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PRICE_LIST_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getPriceListId());
        pstmt.setBigDecimal(i++,pData.getPrice());
        pstmt.setString(i++,pData.getCustomerSkuNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getPriceListDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   PRICE_LIST_ID="+pData.getPriceListId());
            log.debug("SQL:   PRICE="+pData.getPrice());
            log.debug("SQL:   CUSTOMER_SKU_NUM="+pData.getCustomerSkuNum());
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
     * Deletes a PriceListDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceListDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceListDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRICE_LIST_DETAIL WHERE PRICE_LIST_DETAIL_ID = " + pPriceListDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PriceListDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRICE_LIST_DETAIL");
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
     * Inserts a PriceListDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PriceListDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRICE_LIST_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRICE_LIST_DETAIL_ID,ITEM_ID,PRICE_LIST_ID,PRICE,CUSTOMER_SKU_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPriceListDetailId());
        pstmt.setInt(2+4,pData.getItemId());
        pstmt.setInt(3+4,pData.getPriceListId());
        pstmt.setBigDecimal(4+4,pData.getPrice());
        pstmt.setString(5+4,pData.getCustomerSkuNum());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PriceListDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PriceListDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PriceListDetailData insert(Connection pCon, PriceListDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PriceListDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PriceListDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PriceListDetailData pData, boolean pLogFl)
        throws SQLException {
        PriceListDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getPriceListDetailId();
          try {
          oldData = PriceListDetailDataAccess.select(pCon,id);
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
     * Deletes a PriceListDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPriceListDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPriceListDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRICE_LIST_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_LIST_DETAIL d WHERE PRICE_LIST_DETAIL_ID = " + pPriceListDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPriceListDetailId);
        return n;
     }

    /**
     * Deletes PriceListDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRICE_LIST_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRICE_LIST_DETAIL d ");
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

