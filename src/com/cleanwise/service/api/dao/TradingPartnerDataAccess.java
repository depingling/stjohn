 
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        TradingPartnerDataAccess
 * Description:  This class is used to build access methods to the CLW_TRADING_PARTNER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPartnerDataVector;
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
 * <code>TradingPartnerDataAccess</code>
 */
public class TradingPartnerDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(TradingPartnerDataAccess.class.getName());

    /** <code>CLW_TRADING_PARTNER</code> table name */
	/* Primary key: TRADING_PARTNER_ID */
	
    public static final String CLW_TRADING_PARTNER = "CLW_TRADING_PARTNER";
    
    /** <code>TRADING_PARTNER_ID</code> TRADING_PARTNER_ID column of table CLW_TRADING_PARTNER */
    public static final String TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_TRADING_PARTNER */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>TRADING_TYPE_CD</code> TRADING_TYPE_CD column of table CLW_TRADING_PARTNER */
    public static final String TRADING_TYPE_CD = "TRADING_TYPE_CD";
    /** <code>TRADING_PARTNER_TYPE_CD</code> TRADING_PARTNER_TYPE_CD column of table CLW_TRADING_PARTNER */
    public static final String TRADING_PARTNER_TYPE_CD = "TRADING_PARTNER_TYPE_CD";
    /** <code>TRADING_PARTNER_STATUS_CD</code> TRADING_PARTNER_STATUS_CD column of table CLW_TRADING_PARTNER */
    public static final String TRADING_PARTNER_STATUS_CD = "TRADING_PARTNER_STATUS_CD";
    /** <code>SKU_TYPE_CD</code> SKU_TYPE_CD column of table CLW_TRADING_PARTNER */
    public static final String SKU_TYPE_CD = "SKU_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_TRADING_PARTNER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_TRADING_PARTNER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_TRADING_PARTNER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_TRADING_PARTNER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>UOM_CONVERSION_TYPE_CD</code> UOM_CONVERSION_TYPE_CD column of table CLW_TRADING_PARTNER */
    public static final String UOM_CONVERSION_TYPE_CD = "UOM_CONVERSION_TYPE_CD";
    /** <code>SITE_IDENTIFIER_TYPE_CD</code> SITE_IDENTIFIER_TYPE_CD column of table CLW_TRADING_PARTNER */
    public static final String SITE_IDENTIFIER_TYPE_CD = "SITE_IDENTIFIER_TYPE_CD";
    /** <code>VALIDATE_CONTRACT_PRICE</code> VALIDATE_CONTRACT_PRICE column of table CLW_TRADING_PARTNER */
    public static final String VALIDATE_CONTRACT_PRICE = "VALIDATE_CONTRACT_PRICE";
    /** <code>PO_NUMBER_TYPE</code> PO_NUMBER_TYPE column of table CLW_TRADING_PARTNER */
    public static final String PO_NUMBER_TYPE = "PO_NUMBER_TYPE";
    /** <code>ACCOUNT_IDENTIFIER_INBOUND</code> ACCOUNT_IDENTIFIER_INBOUND column of table CLW_TRADING_PARTNER */
    public static final String ACCOUNT_IDENTIFIER_INBOUND = "ACCOUNT_IDENTIFIER_INBOUND";

    /**
     * Constructor.
     */
    public TradingPartnerDataAccess()
    {
    }

    /**
     * Gets a TradingPartnerData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pTradingPartnerId The key requested.
     * @return new TradingPartnerData()
     * @throws            SQLException
     */
    public static TradingPartnerData select(Connection pCon, int pTradingPartnerId)
        throws SQLException, DataNotFoundException {
        TradingPartnerData x=null;
        String sql="SELECT TRADING_PARTNER_ID,SHORT_DESC,TRADING_TYPE_CD,TRADING_PARTNER_TYPE_CD,TRADING_PARTNER_STATUS_CD,SKU_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONVERSION_TYPE_CD,SITE_IDENTIFIER_TYPE_CD,VALIDATE_CONTRACT_PRICE,PO_NUMBER_TYPE,ACCOUNT_IDENTIFIER_INBOUND FROM CLW_TRADING_PARTNER WHERE TRADING_PARTNER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pTradingPartnerId=" + pTradingPartnerId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pTradingPartnerId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=TradingPartnerData.createValue();
            
            x.setTradingPartnerId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setTradingTypeCd(rs.getString(3));
            x.setTradingPartnerTypeCd(rs.getString(4));
            x.setTradingPartnerStatusCd(rs.getString(5));
            x.setSkuTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setUomConversionTypeCd(rs.getString(11));
            x.setSiteIdentifierTypeCd(rs.getString(12));
            x.setValidateContractPrice(rs.getString(13));
            x.setPoNumberType(rs.getString(14));
            x.setAccountIdentifierInbound(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("TRADING_PARTNER_ID :" + pTradingPartnerId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a TradingPartnerDataVector object that consists
     * of TradingPartnerData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new TradingPartnerDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a TradingPartnerData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_TRADING_PARTNER.TRADING_PARTNER_ID,CLW_TRADING_PARTNER.SHORT_DESC,CLW_TRADING_PARTNER.TRADING_TYPE_CD,CLW_TRADING_PARTNER.TRADING_PARTNER_TYPE_CD,CLW_TRADING_PARTNER.TRADING_PARTNER_STATUS_CD,CLW_TRADING_PARTNER.SKU_TYPE_CD,CLW_TRADING_PARTNER.ADD_DATE,CLW_TRADING_PARTNER.ADD_BY,CLW_TRADING_PARTNER.MOD_DATE,CLW_TRADING_PARTNER.MOD_BY,CLW_TRADING_PARTNER.UOM_CONVERSION_TYPE_CD,CLW_TRADING_PARTNER.SITE_IDENTIFIER_TYPE_CD,CLW_TRADING_PARTNER.VALIDATE_CONTRACT_PRICE,CLW_TRADING_PARTNER.PO_NUMBER_TYPE,CLW_TRADING_PARTNER.ACCOUNT_IDENTIFIER_INBOUND";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated TradingPartnerData Object.
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
    *@returns a populated TradingPartnerData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         TradingPartnerData x = TradingPartnerData.createValue();
         
         x.setTradingPartnerId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setTradingTypeCd(rs.getString(3+offset));
         x.setTradingPartnerTypeCd(rs.getString(4+offset));
         x.setTradingPartnerStatusCd(rs.getString(5+offset));
         x.setSkuTypeCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setUomConversionTypeCd(rs.getString(11+offset));
         x.setSiteIdentifierTypeCd(rs.getString(12+offset));
         x.setValidateContractPrice(rs.getString(13+offset));
         x.setPoNumberType(rs.getString(14+offset));
         x.setAccountIdentifierInbound(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the TradingPartnerData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a TradingPartnerDataVector object that consists
     * of TradingPartnerData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new TradingPartnerDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT TRADING_PARTNER_ID,SHORT_DESC,TRADING_TYPE_CD,TRADING_PARTNER_TYPE_CD,TRADING_PARTNER_STATUS_CD,SKU_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONVERSION_TYPE_CD,SITE_IDENTIFIER_TYPE_CD,VALIDATE_CONTRACT_PRICE,PO_NUMBER_TYPE,ACCOUNT_IDENTIFIER_INBOUND FROM CLW_TRADING_PARTNER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_TRADING_PARTNER.TRADING_PARTNER_ID,CLW_TRADING_PARTNER.SHORT_DESC,CLW_TRADING_PARTNER.TRADING_TYPE_CD,CLW_TRADING_PARTNER.TRADING_PARTNER_TYPE_CD,CLW_TRADING_PARTNER.TRADING_PARTNER_STATUS_CD,CLW_TRADING_PARTNER.SKU_TYPE_CD,CLW_TRADING_PARTNER.ADD_DATE,CLW_TRADING_PARTNER.ADD_BY,CLW_TRADING_PARTNER.MOD_DATE,CLW_TRADING_PARTNER.MOD_BY,CLW_TRADING_PARTNER.UOM_CONVERSION_TYPE_CD,CLW_TRADING_PARTNER.SITE_IDENTIFIER_TYPE_CD,CLW_TRADING_PARTNER.VALIDATE_CONTRACT_PRICE,CLW_TRADING_PARTNER.PO_NUMBER_TYPE,CLW_TRADING_PARTNER.ACCOUNT_IDENTIFIER_INBOUND FROM CLW_TRADING_PARTNER");
                where = pCriteria.getSqlClause("CLW_TRADING_PARTNER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TRADING_PARTNER.equals(otherTable)){
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
        TradingPartnerDataVector v = new TradingPartnerDataVector();
        while (rs.next()) {
            TradingPartnerData x = TradingPartnerData.createValue();
            
            x.setTradingPartnerId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setTradingTypeCd(rs.getString(3));
            x.setTradingPartnerTypeCd(rs.getString(4));
            x.setTradingPartnerStatusCd(rs.getString(5));
            x.setSkuTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setUomConversionTypeCd(rs.getString(11));
            x.setSiteIdentifierTypeCd(rs.getString(12));
            x.setValidateContractPrice(rs.getString(13));
            x.setPoNumberType(rs.getString(14));
            x.setAccountIdentifierInbound(rs.getString(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a TradingPartnerDataVector object that consists
     * of TradingPartnerData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for TradingPartnerData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new TradingPartnerDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        TradingPartnerDataVector v = new TradingPartnerDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PARTNER_ID,SHORT_DESC,TRADING_TYPE_CD,TRADING_PARTNER_TYPE_CD,TRADING_PARTNER_STATUS_CD,SKU_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONVERSION_TYPE_CD,SITE_IDENTIFIER_TYPE_CD,VALIDATE_CONTRACT_PRICE,PO_NUMBER_TYPE,ACCOUNT_IDENTIFIER_INBOUND FROM CLW_TRADING_PARTNER WHERE TRADING_PARTNER_ID IN (");

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
            TradingPartnerData x=null;
            while (rs.next()) {
                // build the object
                x=TradingPartnerData.createValue();
                
                x.setTradingPartnerId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setTradingTypeCd(rs.getString(3));
                x.setTradingPartnerTypeCd(rs.getString(4));
                x.setTradingPartnerStatusCd(rs.getString(5));
                x.setSkuTypeCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setUomConversionTypeCd(rs.getString(11));
                x.setSiteIdentifierTypeCd(rs.getString(12));
                x.setValidateContractPrice(rs.getString(13));
                x.setPoNumberType(rs.getString(14));
                x.setAccountIdentifierInbound(rs.getString(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a TradingPartnerDataVector object of all
     * TradingPartnerData objects in the database.
     * @param pCon An open database connection.
     * @return new TradingPartnerDataVector()
     * @throws            SQLException
     */
    public static TradingPartnerDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT TRADING_PARTNER_ID,SHORT_DESC,TRADING_TYPE_CD,TRADING_PARTNER_TYPE_CD,TRADING_PARTNER_STATUS_CD,SKU_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONVERSION_TYPE_CD,SITE_IDENTIFIER_TYPE_CD,VALIDATE_CONTRACT_PRICE,PO_NUMBER_TYPE,ACCOUNT_IDENTIFIER_INBOUND FROM CLW_TRADING_PARTNER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        TradingPartnerDataVector v = new TradingPartnerDataVector();
        TradingPartnerData x = null;
        while (rs.next()) {
            // build the object
            x = TradingPartnerData.createValue();
            
            x.setTradingPartnerId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setTradingTypeCd(rs.getString(3));
            x.setTradingPartnerTypeCd(rs.getString(4));
            x.setTradingPartnerStatusCd(rs.getString(5));
            x.setSkuTypeCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setUomConversionTypeCd(rs.getString(11));
            x.setSiteIdentifierTypeCd(rs.getString(12));
            x.setValidateContractPrice(rs.getString(13));
            x.setPoNumberType(rs.getString(14));
            x.setAccountIdentifierInbound(rs.getString(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * TradingPartnerData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT TRADING_PARTNER_ID FROM CLW_TRADING_PARTNER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PARTNER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TRADING_PARTNER");
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
     * Inserts a TradingPartnerData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new TradingPartnerData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingPartnerData insert(Connection pCon, TradingPartnerData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_TRADING_PARTNER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_TRADING_PARTNER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setTradingPartnerId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_TRADING_PARTNER (TRADING_PARTNER_ID,SHORT_DESC,TRADING_TYPE_CD,TRADING_PARTNER_TYPE_CD,TRADING_PARTNER_STATUS_CD,SKU_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONVERSION_TYPE_CD,SITE_IDENTIFIER_TYPE_CD,VALIDATE_CONTRACT_PRICE,PO_NUMBER_TYPE,ACCOUNT_IDENTIFIER_INBOUND) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getTradingPartnerId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getTradingTypeCd());
        pstmt.setString(4,pData.getTradingPartnerTypeCd());
        pstmt.setString(5,pData.getTradingPartnerStatusCd());
        pstmt.setString(6,pData.getSkuTypeCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());
        pstmt.setString(11,pData.getUomConversionTypeCd());
        pstmt.setString(12,pData.getSiteIdentifierTypeCd());
        pstmt.setString(13,pData.getValidateContractPrice());
        pstmt.setString(14,pData.getPoNumberType());
        pstmt.setString(15,pData.getAccountIdentifierInbound());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   TRADING_TYPE_CD="+pData.getTradingTypeCd());
            log.debug("SQL:   TRADING_PARTNER_TYPE_CD="+pData.getTradingPartnerTypeCd());
            log.debug("SQL:   TRADING_PARTNER_STATUS_CD="+pData.getTradingPartnerStatusCd());
            log.debug("SQL:   SKU_TYPE_CD="+pData.getSkuTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   UOM_CONVERSION_TYPE_CD="+pData.getUomConversionTypeCd());
            log.debug("SQL:   SITE_IDENTIFIER_TYPE_CD="+pData.getSiteIdentifierTypeCd());
            log.debug("SQL:   VALIDATE_CONTRACT_PRICE="+pData.getValidateContractPrice());
            log.debug("SQL:   PO_NUMBER_TYPE="+pData.getPoNumberType());
            log.debug("SQL:   ACCOUNT_IDENTIFIER_INBOUND="+pData.getAccountIdentifierInbound());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setTradingPartnerId(0);
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
     * Updates a TradingPartnerData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingPartnerData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_TRADING_PARTNER SET SHORT_DESC = ?,TRADING_TYPE_CD = ?,TRADING_PARTNER_TYPE_CD = ?,TRADING_PARTNER_STATUS_CD = ?,SKU_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,UOM_CONVERSION_TYPE_CD = ?,SITE_IDENTIFIER_TYPE_CD = ?,VALIDATE_CONTRACT_PRICE = ?,PO_NUMBER_TYPE = ?,ACCOUNT_IDENTIFIER_INBOUND = ? WHERE TRADING_PARTNER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getTradingTypeCd());
        pstmt.setString(i++,pData.getTradingPartnerTypeCd());
        pstmt.setString(i++,pData.getTradingPartnerStatusCd());
        pstmt.setString(i++,pData.getSkuTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getUomConversionTypeCd());
        pstmt.setString(i++,pData.getSiteIdentifierTypeCd());
        pstmt.setString(i++,pData.getValidateContractPrice());
        pstmt.setString(i++,pData.getPoNumberType());
        pstmt.setString(i++,pData.getAccountIdentifierInbound());
        pstmt.setInt(i++,pData.getTradingPartnerId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   TRADING_TYPE_CD="+pData.getTradingTypeCd());
            log.debug("SQL:   TRADING_PARTNER_TYPE_CD="+pData.getTradingPartnerTypeCd());
            log.debug("SQL:   TRADING_PARTNER_STATUS_CD="+pData.getTradingPartnerStatusCd());
            log.debug("SQL:   SKU_TYPE_CD="+pData.getSkuTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   UOM_CONVERSION_TYPE_CD="+pData.getUomConversionTypeCd());
            log.debug("SQL:   SITE_IDENTIFIER_TYPE_CD="+pData.getSiteIdentifierTypeCd());
            log.debug("SQL:   VALIDATE_CONTRACT_PRICE="+pData.getValidateContractPrice());
            log.debug("SQL:   PO_NUMBER_TYPE="+pData.getPoNumberType());
            log.debug("SQL:   ACCOUNT_IDENTIFIER_INBOUND="+pData.getAccountIdentifierInbound());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a TradingPartnerData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingPartnerId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingPartnerId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_TRADING_PARTNER WHERE TRADING_PARTNER_ID = " + pTradingPartnerId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes TradingPartnerData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_TRADING_PARTNER");
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
     * Inserts a TradingPartnerData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, TradingPartnerData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_TRADING_PARTNER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "TRADING_PARTNER_ID,SHORT_DESC,TRADING_TYPE_CD,TRADING_PARTNER_TYPE_CD,TRADING_PARTNER_STATUS_CD,SKU_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,UOM_CONVERSION_TYPE_CD,SITE_IDENTIFIER_TYPE_CD,VALIDATE_CONTRACT_PRICE,PO_NUMBER_TYPE,ACCOUNT_IDENTIFIER_INBOUND) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getTradingPartnerId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getTradingTypeCd());
        pstmt.setString(4+4,pData.getTradingPartnerTypeCd());
        pstmt.setString(5+4,pData.getTradingPartnerStatusCd());
        pstmt.setString(6+4,pData.getSkuTypeCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());
        pstmt.setString(11+4,pData.getUomConversionTypeCd());
        pstmt.setString(12+4,pData.getSiteIdentifierTypeCd());
        pstmt.setString(13+4,pData.getValidateContractPrice());
        pstmt.setString(14+4,pData.getPoNumberType());
        pstmt.setString(15+4,pData.getAccountIdentifierInbound());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a TradingPartnerData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new TradingPartnerData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TradingPartnerData insert(Connection pCon, TradingPartnerData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a TradingPartnerData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TradingPartnerData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TradingPartnerData pData, boolean pLogFl)
        throws SQLException {
        TradingPartnerData oldData = null;
        if(pLogFl) {
          int id = pData.getTradingPartnerId();
          try {
          oldData = TradingPartnerDataAccess.select(pCon,id);
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
     * Deletes a TradingPartnerData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTradingPartnerId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTradingPartnerId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_TRADING_PARTNER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PARTNER d WHERE TRADING_PARTNER_ID = " + pTradingPartnerId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pTradingPartnerId);
        return n;
     }

    /**
     * Deletes TradingPartnerData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_TRADING_PARTNER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TRADING_PARTNER d ");
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

