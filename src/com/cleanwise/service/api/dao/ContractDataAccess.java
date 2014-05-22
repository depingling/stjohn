
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContractDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTRACT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
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
 * <code>ContractDataAccess</code>
 */
public class ContractDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContractDataAccess.class.getName());

    /** <code>CLW_CONTRACT</code> table name */
	/* Primary key: CONTRACT_ID */
	
    public static final String CLW_CONTRACT = "CLW_CONTRACT";
    
    /** <code>CONTRACT_ID</code> CONTRACT_ID column of table CLW_CONTRACT */
    public static final String CONTRACT_ID = "CONTRACT_ID";
    /** <code>REF_CONTRACT_NUM</code> REF_CONTRACT_NUM column of table CLW_CONTRACT */
    public static final String REF_CONTRACT_NUM = "REF_CONTRACT_NUM";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CONTRACT */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_CONTRACT */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>CATALOG_ID</code> CATALOG_ID column of table CLW_CONTRACT */
    public static final String CATALOG_ID = "CATALOG_ID";
    /** <code>CONTRACT_STATUS_CD</code> CONTRACT_STATUS_CD column of table CLW_CONTRACT */
    public static final String CONTRACT_STATUS_CD = "CONTRACT_STATUS_CD";
    /** <code>CONTRACT_TYPE_CD</code> CONTRACT_TYPE_CD column of table CLW_CONTRACT */
    public static final String CONTRACT_TYPE_CD = "CONTRACT_TYPE_CD";
    /** <code>EFF_DATE</code> EFF_DATE column of table CLW_CONTRACT */
    public static final String EFF_DATE = "EFF_DATE";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_CONTRACT */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>ACCEPTANCE_DATE</code> ACCEPTANCE_DATE column of table CLW_CONTRACT */
    public static final String ACCEPTANCE_DATE = "ACCEPTANCE_DATE";
    /** <code>QUOTE_EXP_DATE</code> QUOTE_EXP_DATE column of table CLW_CONTRACT */
    public static final String QUOTE_EXP_DATE = "QUOTE_EXP_DATE";
    /** <code>CONTRACT_ITEMS_ONLY_IND</code> CONTRACT_ITEMS_ONLY_IND column of table CLW_CONTRACT */
    public static final String CONTRACT_ITEMS_ONLY_IND = "CONTRACT_ITEMS_ONLY_IND";
    /** <code>HIDE_PRICING_IND</code> HIDE_PRICING_IND column of table CLW_CONTRACT */
    public static final String HIDE_PRICING_IND = "HIDE_PRICING_IND";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_CONTRACT */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>RANK_WEIGHT</code> RANK_WEIGHT column of table CLW_CONTRACT */
    public static final String RANK_WEIGHT = "RANK_WEIGHT";
    /** <code>FREIGHT_TABLE_ID</code> FREIGHT_TABLE_ID column of table CLW_CONTRACT */
    public static final String FREIGHT_TABLE_ID = "FREIGHT_TABLE_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CONTRACT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CONTRACT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CONTRACT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CONTRACT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>DISCOUNT_TABLE_ID</code> DISCOUNT_TABLE_ID column of table CLW_CONTRACT */
    public static final String DISCOUNT_TABLE_ID = "DISCOUNT_TABLE_ID";

    /**
     * Constructor.
     */
    public ContractDataAccess()
    {
    }

    /**
     * Gets a ContractData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContractId The key requested.
     * @return new ContractData()
     * @throws            SQLException
     */
    public static ContractData select(Connection pCon, int pContractId)
        throws SQLException, DataNotFoundException {
        ContractData x=null;
        String sql="SELECT CONTRACT_ID,REF_CONTRACT_NUM,SHORT_DESC,LONG_DESC,CATALOG_ID,CONTRACT_STATUS_CD,CONTRACT_TYPE_CD,EFF_DATE,EXP_DATE,ACCEPTANCE_DATE,QUOTE_EXP_DATE,CONTRACT_ITEMS_ONLY_IND,HIDE_PRICING_IND,LOCALE_CD,RANK_WEIGHT,FREIGHT_TABLE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISCOUNT_TABLE_ID FROM CLW_CONTRACT WHERE CONTRACT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContractId=" + pContractId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContractId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContractData.createValue();
            
            x.setContractId(rs.getInt(1));
            x.setRefContractNum(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setCatalogId(rs.getInt(5));
            x.setContractStatusCd(rs.getString(6));
            x.setContractTypeCd(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setAcceptanceDate(rs.getDate(10));
            x.setQuoteExpDate(rs.getDate(11));
            x.setContractItemsOnlyInd(rs.getBoolean(12));
            x.setHidePricingInd(rs.getBoolean(13));
            x.setLocaleCd(rs.getString(14));
            x.setRankWeight(rs.getInt(15));
            x.setFreightTableId(rs.getInt(16));
            x.setAddDate(rs.getTimestamp(17));
            x.setAddBy(rs.getString(18));
            x.setModDate(rs.getTimestamp(19));
            x.setModBy(rs.getString(20));
            x.setDiscountTableId(rs.getInt(21));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTRACT_ID :" + pContractId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContractDataVector object that consists
     * of ContractData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContractDataVector()
     * @throws            SQLException
     */
    public static ContractDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContractData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTRACT.CONTRACT_ID,CLW_CONTRACT.REF_CONTRACT_NUM,CLW_CONTRACT.SHORT_DESC,CLW_CONTRACT.LONG_DESC,CLW_CONTRACT.CATALOG_ID,CLW_CONTRACT.CONTRACT_STATUS_CD,CLW_CONTRACT.CONTRACT_TYPE_CD,CLW_CONTRACT.EFF_DATE,CLW_CONTRACT.EXP_DATE,CLW_CONTRACT.ACCEPTANCE_DATE,CLW_CONTRACT.QUOTE_EXP_DATE,CLW_CONTRACT.CONTRACT_ITEMS_ONLY_IND,CLW_CONTRACT.HIDE_PRICING_IND,CLW_CONTRACT.LOCALE_CD,CLW_CONTRACT.RANK_WEIGHT,CLW_CONTRACT.FREIGHT_TABLE_ID,CLW_CONTRACT.ADD_DATE,CLW_CONTRACT.ADD_BY,CLW_CONTRACT.MOD_DATE,CLW_CONTRACT.MOD_BY,CLW_CONTRACT.DISCOUNT_TABLE_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContractData Object.
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
    *@returns a populated ContractData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContractData x = ContractData.createValue();
         
         x.setContractId(rs.getInt(1+offset));
         x.setRefContractNum(rs.getString(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setLongDesc(rs.getString(4+offset));
         x.setCatalogId(rs.getInt(5+offset));
         x.setContractStatusCd(rs.getString(6+offset));
         x.setContractTypeCd(rs.getString(7+offset));
         x.setEffDate(rs.getDate(8+offset));
         x.setExpDate(rs.getDate(9+offset));
         x.setAcceptanceDate(rs.getDate(10+offset));
         x.setQuoteExpDate(rs.getDate(11+offset));
         x.setContractItemsOnlyInd(rs.getBoolean(12+offset));
         x.setHidePricingInd(rs.getBoolean(13+offset));
         x.setLocaleCd(rs.getString(14+offset));
         x.setRankWeight(rs.getInt(15+offset));
         x.setFreightTableId(rs.getInt(16+offset));
         x.setAddDate(rs.getTimestamp(17+offset));
         x.setAddBy(rs.getString(18+offset));
         x.setModDate(rs.getTimestamp(19+offset));
         x.setModBy(rs.getString(20+offset));
         x.setDiscountTableId(rs.getInt(21+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContractData Object represents.
    */
    public int getColumnCount(){
        return 21;
    }

    /**
     * Gets a ContractDataVector object that consists
     * of ContractData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContractDataVector()
     * @throws            SQLException
     */
    public static ContractDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTRACT_ID,REF_CONTRACT_NUM,SHORT_DESC,LONG_DESC,CATALOG_ID,CONTRACT_STATUS_CD,CONTRACT_TYPE_CD,EFF_DATE,EXP_DATE,ACCEPTANCE_DATE,QUOTE_EXP_DATE,CONTRACT_ITEMS_ONLY_IND,HIDE_PRICING_IND,LOCALE_CD,RANK_WEIGHT,FREIGHT_TABLE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISCOUNT_TABLE_ID FROM CLW_CONTRACT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTRACT.CONTRACT_ID,CLW_CONTRACT.REF_CONTRACT_NUM,CLW_CONTRACT.SHORT_DESC,CLW_CONTRACT.LONG_DESC,CLW_CONTRACT.CATALOG_ID,CLW_CONTRACT.CONTRACT_STATUS_CD,CLW_CONTRACT.CONTRACT_TYPE_CD,CLW_CONTRACT.EFF_DATE,CLW_CONTRACT.EXP_DATE,CLW_CONTRACT.ACCEPTANCE_DATE,CLW_CONTRACT.QUOTE_EXP_DATE,CLW_CONTRACT.CONTRACT_ITEMS_ONLY_IND,CLW_CONTRACT.HIDE_PRICING_IND,CLW_CONTRACT.LOCALE_CD,CLW_CONTRACT.RANK_WEIGHT,CLW_CONTRACT.FREIGHT_TABLE_ID,CLW_CONTRACT.ADD_DATE,CLW_CONTRACT.ADD_BY,CLW_CONTRACT.MOD_DATE,CLW_CONTRACT.MOD_BY,CLW_CONTRACT.DISCOUNT_TABLE_ID FROM CLW_CONTRACT");
                where = pCriteria.getSqlClause("CLW_CONTRACT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTRACT.equals(otherTable)){
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
        ContractDataVector v = new ContractDataVector();
        while (rs.next()) {
            ContractData x = ContractData.createValue();
            
            x.setContractId(rs.getInt(1));
            x.setRefContractNum(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setCatalogId(rs.getInt(5));
            x.setContractStatusCd(rs.getString(6));
            x.setContractTypeCd(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setAcceptanceDate(rs.getDate(10));
            x.setQuoteExpDate(rs.getDate(11));
            x.setContractItemsOnlyInd(rs.getBoolean(12));
            x.setHidePricingInd(rs.getBoolean(13));
            x.setLocaleCd(rs.getString(14));
            x.setRankWeight(rs.getInt(15));
            x.setFreightTableId(rs.getInt(16));
            x.setAddDate(rs.getTimestamp(17));
            x.setAddBy(rs.getString(18));
            x.setModDate(rs.getTimestamp(19));
            x.setModBy(rs.getString(20));
            x.setDiscountTableId(rs.getInt(21));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ContractDataVector object that consists
     * of ContractData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContractData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContractDataVector()
     * @throws            SQLException
     */
    public static ContractDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContractDataVector v = new ContractDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTRACT_ID,REF_CONTRACT_NUM,SHORT_DESC,LONG_DESC,CATALOG_ID,CONTRACT_STATUS_CD,CONTRACT_TYPE_CD,EFF_DATE,EXP_DATE,ACCEPTANCE_DATE,QUOTE_EXP_DATE,CONTRACT_ITEMS_ONLY_IND,HIDE_PRICING_IND,LOCALE_CD,RANK_WEIGHT,FREIGHT_TABLE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISCOUNT_TABLE_ID FROM CLW_CONTRACT WHERE CONTRACT_ID IN (");

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
            ContractData x=null;
            while (rs.next()) {
                // build the object
                x=ContractData.createValue();
                
                x.setContractId(rs.getInt(1));
                x.setRefContractNum(rs.getString(2));
                x.setShortDesc(rs.getString(3));
                x.setLongDesc(rs.getString(4));
                x.setCatalogId(rs.getInt(5));
                x.setContractStatusCd(rs.getString(6));
                x.setContractTypeCd(rs.getString(7));
                x.setEffDate(rs.getDate(8));
                x.setExpDate(rs.getDate(9));
                x.setAcceptanceDate(rs.getDate(10));
                x.setQuoteExpDate(rs.getDate(11));
                x.setContractItemsOnlyInd(rs.getBoolean(12));
                x.setHidePricingInd(rs.getBoolean(13));
                x.setLocaleCd(rs.getString(14));
                x.setRankWeight(rs.getInt(15));
                x.setFreightTableId(rs.getInt(16));
                x.setAddDate(rs.getTimestamp(17));
                x.setAddBy(rs.getString(18));
                x.setModDate(rs.getTimestamp(19));
                x.setModBy(rs.getString(20));
                x.setDiscountTableId(rs.getInt(21));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ContractDataVector object of all
     * ContractData objects in the database.
     * @param pCon An open database connection.
     * @return new ContractDataVector()
     * @throws            SQLException
     */
    public static ContractDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTRACT_ID,REF_CONTRACT_NUM,SHORT_DESC,LONG_DESC,CATALOG_ID,CONTRACT_STATUS_CD,CONTRACT_TYPE_CD,EFF_DATE,EXP_DATE,ACCEPTANCE_DATE,QUOTE_EXP_DATE,CONTRACT_ITEMS_ONLY_IND,HIDE_PRICING_IND,LOCALE_CD,RANK_WEIGHT,FREIGHT_TABLE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISCOUNT_TABLE_ID FROM CLW_CONTRACT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContractDataVector v = new ContractDataVector();
        ContractData x = null;
        while (rs.next()) {
            // build the object
            x = ContractData.createValue();
            
            x.setContractId(rs.getInt(1));
            x.setRefContractNum(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLongDesc(rs.getString(4));
            x.setCatalogId(rs.getInt(5));
            x.setContractStatusCd(rs.getString(6));
            x.setContractTypeCd(rs.getString(7));
            x.setEffDate(rs.getDate(8));
            x.setExpDate(rs.getDate(9));
            x.setAcceptanceDate(rs.getDate(10));
            x.setQuoteExpDate(rs.getDate(11));
            x.setContractItemsOnlyInd(rs.getBoolean(12));
            x.setHidePricingInd(rs.getBoolean(13));
            x.setLocaleCd(rs.getString(14));
            x.setRankWeight(rs.getInt(15));
            x.setFreightTableId(rs.getInt(16));
            x.setAddDate(rs.getTimestamp(17));
            x.setAddBy(rs.getString(18));
            x.setModDate(rs.getTimestamp(19));
            x.setModBy(rs.getString(20));
            x.setDiscountTableId(rs.getInt(21));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ContractData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTRACT_ID FROM CLW_CONTRACT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTRACT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTRACT");
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
     * Inserts a ContractData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ContractData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContractData insert(Connection pCon, ContractData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTRACT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTRACT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContractId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTRACT (CONTRACT_ID,REF_CONTRACT_NUM,SHORT_DESC,LONG_DESC,CATALOG_ID,CONTRACT_STATUS_CD,CONTRACT_TYPE_CD,EFF_DATE,EXP_DATE,ACCEPTANCE_DATE,QUOTE_EXP_DATE,CONTRACT_ITEMS_ONLY_IND,HIDE_PRICING_IND,LOCALE_CD,RANK_WEIGHT,FREIGHT_TABLE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISCOUNT_TABLE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getContractId());
        pstmt.setString(2,pData.getRefContractNum());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getLongDesc());
        pstmt.setInt(5,pData.getCatalogId());
        pstmt.setString(6,pData.getContractStatusCd());
        pstmt.setString(7,pData.getContractTypeCd());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setDate(10,DBAccess.toSQLDate(pData.getAcceptanceDate()));
        pstmt.setDate(11,DBAccess.toSQLDate(pData.getQuoteExpDate()));
        pstmt.setInt(12, pData.getContractItemsOnlyInd()?1:0);
        pstmt.setInt(13, pData.getHidePricingInd()?1:0);
        pstmt.setString(14,pData.getLocaleCd());
        pstmt.setInt(15,pData.getRankWeight());
        if (pData.getFreightTableId() == 0) {
            pstmt.setNull(16, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(16,pData.getFreightTableId());
        }

        pstmt.setTimestamp(17,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(18,pData.getAddBy());
        pstmt.setTimestamp(19,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(20,pData.getModBy());
        if (pData.getDiscountTableId() == 0) {
            pstmt.setNull(21, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(21,pData.getDiscountTableId());
        }


        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   REF_CONTRACT_NUM="+pData.getRefContractNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   CONTRACT_STATUS_CD="+pData.getContractStatusCd());
            log.debug("SQL:   CONTRACT_TYPE_CD="+pData.getContractTypeCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ACCEPTANCE_DATE="+pData.getAcceptanceDate());
            log.debug("SQL:   QUOTE_EXP_DATE="+pData.getQuoteExpDate());
            log.debug("SQL:   CONTRACT_ITEMS_ONLY_IND="+pData.getContractItemsOnlyInd());
            log.debug("SQL:   HIDE_PRICING_IND="+pData.getHidePricingInd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   RANK_WEIGHT="+pData.getRankWeight());
            log.debug("SQL:   FREIGHT_TABLE_ID="+pData.getFreightTableId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DISCOUNT_TABLE_ID="+pData.getDiscountTableId());
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
        pData.setContractId(0);
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
     * Updates a ContractData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContractData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTRACT SET REF_CONTRACT_NUM = ?,SHORT_DESC = ?,LONG_DESC = ?,CATALOG_ID = ?,CONTRACT_STATUS_CD = ?,CONTRACT_TYPE_CD = ?,EFF_DATE = ?,EXP_DATE = ?,ACCEPTANCE_DATE = ?,QUOTE_EXP_DATE = ?,CONTRACT_ITEMS_ONLY_IND = ?,HIDE_PRICING_IND = ?,LOCALE_CD = ?,RANK_WEIGHT = ?,FREIGHT_TABLE_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,DISCOUNT_TABLE_ID = ? WHERE CONTRACT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getRefContractNum());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setInt(i++,pData.getCatalogId());
        pstmt.setString(i++,pData.getContractStatusCd());
        pstmt.setString(i++,pData.getContractTypeCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getAcceptanceDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getQuoteExpDate()));
        pstmt.setInt(i++, pData.getContractItemsOnlyInd()?1:0);
        pstmt.setInt(i++, pData.getHidePricingInd()?1:0);
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setInt(i++,pData.getRankWeight());
        if (pData.getFreightTableId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getFreightTableId());
        }

        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        if (pData.getDiscountTableId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getDiscountTableId());
        }

        pstmt.setInt(i++,pData.getContractId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REF_CONTRACT_NUM="+pData.getRefContractNum());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   CATALOG_ID="+pData.getCatalogId());
            log.debug("SQL:   CONTRACT_STATUS_CD="+pData.getContractStatusCd());
            log.debug("SQL:   CONTRACT_TYPE_CD="+pData.getContractTypeCd());
            log.debug("SQL:   EFF_DATE="+pData.getEffDate());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ACCEPTANCE_DATE="+pData.getAcceptanceDate());
            log.debug("SQL:   QUOTE_EXP_DATE="+pData.getQuoteExpDate());
            log.debug("SQL:   CONTRACT_ITEMS_ONLY_IND="+pData.getContractItemsOnlyInd());
            log.debug("SQL:   HIDE_PRICING_IND="+pData.getHidePricingInd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   RANK_WEIGHT="+pData.getRankWeight());
            log.debug("SQL:   FREIGHT_TABLE_ID="+pData.getFreightTableId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   DISCOUNT_TABLE_ID="+pData.getDiscountTableId());
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
     * Deletes a ContractData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContractId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContractId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTRACT WHERE CONTRACT_ID = " + pContractId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContractData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTRACT");
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
     * Inserts a ContractData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContractData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTRACT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTRACT_ID,REF_CONTRACT_NUM,SHORT_DESC,LONG_DESC,CATALOG_ID,CONTRACT_STATUS_CD,CONTRACT_TYPE_CD,EFF_DATE,EXP_DATE,ACCEPTANCE_DATE,QUOTE_EXP_DATE,CONTRACT_ITEMS_ONLY_IND,HIDE_PRICING_IND,LOCALE_CD,RANK_WEIGHT,FREIGHT_TABLE_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,DISCOUNT_TABLE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContractId());
        pstmt.setString(2+4,pData.getRefContractNum());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getLongDesc());
        pstmt.setInt(5+4,pData.getCatalogId());
        pstmt.setString(6+4,pData.getContractStatusCd());
        pstmt.setString(7+4,pData.getContractTypeCd());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getEffDate()));
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setDate(10+4,DBAccess.toSQLDate(pData.getAcceptanceDate()));
        pstmt.setDate(11+4,DBAccess.toSQLDate(pData.getQuoteExpDate()));
        pstmt.setBoolean(12+4,pData.getContractItemsOnlyInd());
        pstmt.setBoolean(13+4,pData.getHidePricingInd());
        pstmt.setString(14+4,pData.getLocaleCd());
        pstmt.setInt(15+4,pData.getRankWeight());
        if (pData.getFreightTableId() == 0) {
            pstmt.setNull(16+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(16+4,pData.getFreightTableId());
        }

        pstmt.setTimestamp(17+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(18+4,pData.getAddBy());
        pstmt.setTimestamp(19+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(20+4,pData.getModBy());
        if (pData.getDiscountTableId() == 0) {
            pstmt.setNull(21+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(21+4,pData.getDiscountTableId());
        }



        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContractData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ContractData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContractData insert(Connection pCon, ContractData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContractData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContractData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContractData pData, boolean pLogFl)
        throws SQLException {
        ContractData oldData = null;
        if(pLogFl) {
          int id = pData.getContractId();
          try {
          oldData = ContractDataAccess.select(pCon,id);
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
     * Deletes a ContractData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContractId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContractId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTRACT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTRACT d WHERE CONTRACT_ID = " + pContractId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContractId);
        return n;
     }

    /**
     * Deletes ContractData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTRACT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTRACT d ");
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

