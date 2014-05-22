
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContractItemDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTRACT_ITEM table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * <code>ContractItemDataAccess</code>
 */
public class ContractItemDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContractItemDataAccess.class.getName());

    /** <code>CLW_CONTRACT_ITEM</code> table name */
	/* Primary key: CONTRACT_ITEM_ID */
	
    public static final String CLW_CONTRACT_ITEM = "CLW_CONTRACT_ITEM";
    
    /** <code>CONTRACT_ID</code> CONTRACT_ID column of table CLW_CONTRACT_ITEM */
    public static final String CONTRACT_ID = "CONTRACT_ID";
    /** <code>CONTRACT_ITEM_ID</code> CONTRACT_ITEM_ID column of table CLW_CONTRACT_ITEM */
    public static final String CONTRACT_ITEM_ID = "CONTRACT_ITEM_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_CONTRACT_ITEM */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>AMOUNT</code> AMOUNT column of table CLW_CONTRACT_ITEM */
    public static final String AMOUNT = "AMOUNT";
    /** <code>DIST_COST</code> DIST_COST column of table CLW_CONTRACT_ITEM */
    public static final String DIST_COST = "DIST_COST";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_CONTRACT_ITEM */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_CONTRACT_ITEM */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>DISCOUNT_AMOUNT</code> DISCOUNT_AMOUNT column of table CLW_CONTRACT_ITEM */
    public static final String DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    /** <code>CURRENCY_CD</code> CURRENCY_CD column of table CLW_CONTRACT_ITEM */
    public static final String CURRENCY_CD = "CURRENCY_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CONTRACT_ITEM */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CONTRACT_ITEM */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CONTRACT_ITEM */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CONTRACT_ITEM */
    public static final String MOD_BY = "MOD_BY";
    /** <code>DIST_BASE_COST</code> DIST_BASE_COST column of table CLW_CONTRACT_ITEM */
    public static final String DIST_BASE_COST = "DIST_BASE_COST";
    /** <code>SERVICE_FEE_CODE</code> SERVICE_FEE_CODE column of table CLW_CONTRACT_ITEM */
    public static final String SERVICE_FEE_CODE = "SERVICE_FEE_CODE";

    /**
     * Constructor.
     */
    public ContractItemDataAccess()
    {
    }

    /**
     * Gets a ContractItemData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContractItemId The key requested.
     * @return new ContractItemData()
     * @throws            SQLException
     */
    public static ContractItemData select(Connection pCon, int pContractItemId)
        throws SQLException, DataNotFoundException {
        ContractItemData x=null;
        String sql="SELECT CONTRACT_ID,CONTRACT_ITEM_ID,ITEM_ID,AMOUNT,DIST_COST,EFF_DATE,EXP_DATE,DISCOUNT_AMOUNT,CURRENCY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DIST_BASE_COST,SERVICE_FEE_CODE FROM CLW_CONTRACT_ITEM WHERE CONTRACT_ITEM_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContractItemId=" + pContractItemId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContractItemId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContractItemData.createValue();
            
            x.setContractId(rs.getInt(1));
            x.setContractItemId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setAmount(rs.getBigDecimal(4));
            x.setDistCost(rs.getBigDecimal(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setDiscountAmount(rs.getBigDecimal(8));
            x.setCurrencyCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            x.setDistBaseCost(rs.getBigDecimal(14));
            x.setServiceFeeCode(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTRACT_ITEM_ID :" + pContractItemId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContractItemDataVector object that consists
     * of ContractItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContractItemDataVector()
     * @throws            SQLException
     */
    public static ContractItemDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContractItemData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTRACT_ITEM.CONTRACT_ID,CLW_CONTRACT_ITEM.CONTRACT_ITEM_ID,CLW_CONTRACT_ITEM.ITEM_ID,CLW_CONTRACT_ITEM.AMOUNT,CLW_CONTRACT_ITEM.DIST_COST,CLW_CONTRACT_ITEM.EFF_DATE,CLW_CONTRACT_ITEM.EXP_DATE,CLW_CONTRACT_ITEM.DISCOUNT_AMOUNT,CLW_CONTRACT_ITEM.CURRENCY_CD,CLW_CONTRACT_ITEM.ADD_DATE,CLW_CONTRACT_ITEM.ADD_BY,CLW_CONTRACT_ITEM.MOD_DATE,CLW_CONTRACT_ITEM.MOD_BY,CLW_CONTRACT_ITEM.DIST_BASE_COST,CLW_CONTRACT_ITEM.SERVICE_FEE_CODE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContractItemData Object.
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
    *@returns a populated ContractItemData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContractItemData x = ContractItemData.createValue();
         
         x.setContractId(rs.getInt(1+offset));
         x.setContractItemId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setAmount(rs.getBigDecimal(4+offset));
         x.setDistCost(rs.getBigDecimal(5+offset));
         x.setEffDate(rs.getDate(6+offset));
         x.setExpDate(rs.getDate(7+offset));
         x.setDiscountAmount(rs.getBigDecimal(8+offset));
         x.setCurrencyCd(rs.getString(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setAddBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setModBy(rs.getString(13+offset));
         x.setDistBaseCost(rs.getBigDecimal(14+offset));
         x.setServiceFeeCode(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContractItemData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a ContractItemDataVector object that consists
     * of ContractItemData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContractItemDataVector()
     * @throws            SQLException
     */
    public static ContractItemDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTRACT_ID,CONTRACT_ITEM_ID,ITEM_ID,AMOUNT,DIST_COST,EFF_DATE,EXP_DATE,DISCOUNT_AMOUNT,CURRENCY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DIST_BASE_COST,SERVICE_FEE_CODE FROM CLW_CONTRACT_ITEM");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTRACT_ITEM.CONTRACT_ID,CLW_CONTRACT_ITEM.CONTRACT_ITEM_ID,CLW_CONTRACT_ITEM.ITEM_ID,CLW_CONTRACT_ITEM.AMOUNT,CLW_CONTRACT_ITEM.DIST_COST,CLW_CONTRACT_ITEM.EFF_DATE,CLW_CONTRACT_ITEM.EXP_DATE,CLW_CONTRACT_ITEM.DISCOUNT_AMOUNT,CLW_CONTRACT_ITEM.CURRENCY_CD,CLW_CONTRACT_ITEM.ADD_DATE,CLW_CONTRACT_ITEM.ADD_BY,CLW_CONTRACT_ITEM.MOD_DATE,CLW_CONTRACT_ITEM.MOD_BY,CLW_CONTRACT_ITEM.DIST_BASE_COST,CLW_CONTRACT_ITEM.SERVICE_FEE_CODE FROM CLW_CONTRACT_ITEM");
                where = pCriteria.getSqlClause("CLW_CONTRACT_ITEM");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTRACT_ITEM.equals(otherTable)){
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
        ContractItemDataVector v = new ContractItemDataVector();
        while (rs.next()) {
            ContractItemData x = ContractItemData.createValue();
            
            x.setContractId(rs.getInt(1));
            x.setContractItemId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setAmount(rs.getBigDecimal(4));
            x.setDistCost(rs.getBigDecimal(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setDiscountAmount(rs.getBigDecimal(8));
            x.setCurrencyCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            x.setDistBaseCost(rs.getBigDecimal(14));
            x.setServiceFeeCode(rs.getString(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ContractItemDataVector object that consists
     * of ContractItemData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContractItemData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContractItemDataVector()
     * @throws            SQLException
     */
    public static ContractItemDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContractItemDataVector v = new ContractItemDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTRACT_ID,CONTRACT_ITEM_ID,ITEM_ID,AMOUNT,DIST_COST,EFF_DATE,EXP_DATE,DISCOUNT_AMOUNT,CURRENCY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DIST_BASE_COST,SERVICE_FEE_CODE FROM CLW_CONTRACT_ITEM WHERE CONTRACT_ITEM_ID IN (");

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
            ContractItemData x=null;
            while (rs.next()) {
                // build the object
                x=ContractItemData.createValue();
                
                x.setContractId(rs.getInt(1));
                x.setContractItemId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setAmount(rs.getBigDecimal(4));
                x.setDistCost(rs.getBigDecimal(5));
                x.setEffDate(rs.getDate(6));
                x.setExpDate(rs.getDate(7));
                x.setDiscountAmount(rs.getBigDecimal(8));
                x.setCurrencyCd(rs.getString(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setAddBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setModBy(rs.getString(13));
                x.setDistBaseCost(rs.getBigDecimal(14));
                x.setServiceFeeCode(rs.getString(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ContractItemDataVector object of all
     * ContractItemData objects in the database.
     * @param pCon An open database connection.
     * @return new ContractItemDataVector()
     * @throws            SQLException
     */
    public static ContractItemDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTRACT_ID,CONTRACT_ITEM_ID,ITEM_ID,AMOUNT,DIST_COST,EFF_DATE,EXP_DATE,DISCOUNT_AMOUNT,CURRENCY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DIST_BASE_COST,SERVICE_FEE_CODE FROM CLW_CONTRACT_ITEM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContractItemDataVector v = new ContractItemDataVector();
        ContractItemData x = null;
        while (rs.next()) {
            // build the object
            x = ContractItemData.createValue();
            
            x.setContractId(rs.getInt(1));
            x.setContractItemId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setAmount(rs.getBigDecimal(4));
            x.setDistCost(rs.getBigDecimal(5));
            x.setEffDate(rs.getDate(6));
            x.setExpDate(rs.getDate(7));
            x.setDiscountAmount(rs.getBigDecimal(8));
            x.setCurrencyCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            x.setDistBaseCost(rs.getBigDecimal(14));
            x.setServiceFeeCode(rs.getString(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ContractItemData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTRACT_ITEM_ID FROM CLW_CONTRACT_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTRACT_ITEM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTRACT_ITEM");
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
     * Inserts a ContractItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ContractItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContractItemData insert(Connection pCon, ContractItemData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTRACT_ITEM_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTRACT_ITEM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContractItemId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTRACT_ITEM (CONTRACT_ID,CONTRACT_ITEM_ID,ITEM_ID,AMOUNT,DIST_COST,EFF_DATE,EXP_DATE,DISCOUNT_AMOUNT,CURRENCY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DIST_BASE_COST,SERVICE_FEE_CODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getContractId());
        pstmt.setInt(2,pData.getContractItemId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getItemId());
        }

        pstmt.setBigDecimal(4,pData.getAmount());
        pstmt.setBigDecimal(5,pData.getDistCost());
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setBigDecimal(8,pData.getDiscountAmount());
        pstmt.setString(9,pData.getCurrencyCd());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getAddBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getModBy());
        pstmt.setBigDecimal(14,pData.getDistBaseCost());
        pstmt.setString(15,pData.getServiceFeeCode());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   CONTRACT_ITEM_ID="+pData.getContractItemId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   DIST_COST="+pData.getDistCost());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   DISCOUNT_AMOUNT="+pData.getDiscountAmount());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DIST_BASE_COST="+pData.getDistBaseCost());
            log.debug("SQL:   SERVICE_FEE_CODE="+pData.getServiceFeeCode());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setContractItemId(0);
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
     * Updates a ContractItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContractItemData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTRACT_ITEM SET CONTRACT_ID = ?,ITEM_ID = ?,AMOUNT = ?,DIST_COST = ?,EFF_DATE = ?,EXP_DATE = ?,DISCOUNT_AMOUNT = ?,CURRENCY_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,DIST_BASE_COST = ?,SERVICE_FEE_CODE = ? WHERE CONTRACT_ITEM_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getContractId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getItemId());
        }

        pstmt.setBigDecimal(i++,pData.getAmount());
        pstmt.setBigDecimal(i++,pData.getDistCost());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setBigDecimal(i++,pData.getDiscountAmount());
        pstmt.setString(i++,pData.getCurrencyCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setBigDecimal(i++,pData.getDistBaseCost());
        pstmt.setString(i++,pData.getServiceFeeCode());
        pstmt.setInt(i++,pData.getContractItemId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   DIST_COST="+pData.getDistCost());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   DISCOUNT_AMOUNT="+pData.getDiscountAmount());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DIST_BASE_COST="+pData.getDistBaseCost());
            log.debug("SQL:   SERVICE_FEE_CODE="+pData.getServiceFeeCode());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ContractItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContractItemId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContractItemId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTRACT_ITEM WHERE CONTRACT_ITEM_ID = " + pContractItemId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContractItemData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTRACT_ITEM");
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
     * Inserts a ContractItemData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContractItemData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTRACT_ITEM (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTRACT_ID,CONTRACT_ITEM_ID,ITEM_ID,AMOUNT,DIST_COST,EFF_DATE,EXP_DATE,DISCOUNT_AMOUNT,CURRENCY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DIST_BASE_COST,SERVICE_FEE_CODE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContractId());
        pstmt.setInt(2+4,pData.getContractItemId());
        if (pData.getItemId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getItemId());
        }

        pstmt.setBigDecimal(4+4,pData.getAmount());
        pstmt.setBigDecimal(5+4,pData.getDistCost());
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setBigDecimal(8+4,pData.getDiscountAmount());
        pstmt.setString(9+4,pData.getCurrencyCd());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getAddBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getModBy());
        pstmt.setBigDecimal(14+4,pData.getDistBaseCost());
        pstmt.setString(15+4,pData.getServiceFeeCode());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContractItemData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ContractItemData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContractItemData insert(Connection pCon, ContractItemData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContractItemData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractItemData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContractItemData pData, boolean pLogFl)
        throws SQLException {
        ContractItemData oldData = null;
        if(pLogFl) {
          int id = pData.getContractItemId();
          try {
          oldData = ContractItemDataAccess.select(pCon,id);
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
     * Deletes a ContractItemData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContractItemId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContractItemId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTRACT_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTRACT_ITEM d WHERE CONTRACT_ITEM_ID = " + pContractItemId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContractItemId);
        return n;
     }

    /**
     * Deletes ContractItemData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTRACT_ITEM SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTRACT_ITEM d ");
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
    
    // Location Budget
    /**
     * Calculates the amount of items in cart or pending approval
     * status pertaining to a cost center 
     * @param pContract
     * @param idVector -   List of Item Ids and Quantities.
     * @return BigDecimal 
     */
    public static BigDecimal select(Connection pCon, int pContract,IdVector idVector)
    throws SQLException{
    ContractItemData x=null;
    
    String sql="SELECT AMOUNT FROM CLW_CONTRACT_ITEM WHERE CONTRACT_ID = ? AND ITEM_ID = ?";

    if (log.isDebugEnabled()) {
        log.debug("SQL: pContractItemId=" + pContract);
        log.debug("SQL: " + sql);
    }
    ArrayList<Integer> itemIds = (ArrayList<Integer>) idVector.get(0);
    ArrayList<Integer> qtyList = (ArrayList<Integer>) idVector.get(1);
    PreparedStatement preparedStmt = pCon.prepareStatement(sql);
    ResultSet rs= null;
    BigDecimal total = new BigDecimal("0");
    for(int i=0;i<itemIds.size();i++)
    {
    	preparedStmt.setInt(1,pContract);
    	preparedStmt.setInt(2, itemIds.get(i));
     rs=preparedStmt.executeQuery();
    if (rs.next()) {
    	double amount = rs.getDouble(1);
    	BigDecimal amountBD = new BigDecimal(String.valueOf(amount));
    	int qty = qtyList.get(i);
    	BigDecimal qtyBD = new BigDecimal(String.valueOf(qty));
    	total = total.add(new BigDecimal(String.valueOf((amountBD.multiply(qtyBD)).doubleValue())));
    }    
    }
    if(rs!=null)
    rs.close();
    preparedStmt.close();

    return total;
}

}

