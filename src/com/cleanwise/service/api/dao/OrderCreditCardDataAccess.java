
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderCreditCardDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_CREDIT_CARD table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderCreditCardData;
import com.cleanwise.service.api.value.OrderCreditCardDataVector;
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
 * <code>OrderCreditCardDataAccess</code>
 */
public class OrderCreditCardDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderCreditCardDataAccess.class.getName());

    /** <code>CLW_ORDER_CREDIT_CARD</code> table name */
	/* Primary key: ORDER_CREDIT_CARD_ID */
	
    public static final String CLW_ORDER_CREDIT_CARD = "CLW_ORDER_CREDIT_CARD";
    
    /** <code>ORDER_CREDIT_CARD_ID</code> ORDER_CREDIT_CARD_ID column of table CLW_ORDER_CREDIT_CARD */
    public static final String ORDER_CREDIT_CARD_ID = "ORDER_CREDIT_CARD_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_CREDIT_CARD */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ENCRYPTION_ALGORITHM</code> ENCRYPTION_ALGORITHM column of table CLW_ORDER_CREDIT_CARD */
    public static final String ENCRYPTION_ALGORITHM = "ENCRYPTION_ALGORITHM";
    /** <code>ENCRYPTION_ALIAS</code> ENCRYPTION_ALIAS column of table CLW_ORDER_CREDIT_CARD */
    public static final String ENCRYPTION_ALIAS = "ENCRYPTION_ALIAS";
    /** <code>ENCRYPTED_CREDIT_CARD_NUMBER</code> ENCRYPTED_CREDIT_CARD_NUMBER column of table CLW_ORDER_CREDIT_CARD */
    public static final String ENCRYPTED_CREDIT_CARD_NUMBER = "ENCRYPTED_CREDIT_CARD_NUMBER";
    /** <code>CREDIT_CARD_NUMBER_DISPLAY</code> CREDIT_CARD_NUMBER_DISPLAY column of table CLW_ORDER_CREDIT_CARD */
    public static final String CREDIT_CARD_NUMBER_DISPLAY = "CREDIT_CARD_NUMBER_DISPLAY";
    /** <code>CREDIT_CARD_TYPE</code> CREDIT_CARD_TYPE column of table CLW_ORDER_CREDIT_CARD */
    public static final String CREDIT_CARD_TYPE = "CREDIT_CARD_TYPE";
    /** <code>HASH_ALGORITHM</code> HASH_ALGORITHM column of table CLW_ORDER_CREDIT_CARD */
    public static final String HASH_ALGORITHM = "HASH_ALGORITHM";
    /** <code>HASHED_CREDIT_CARD_NUMBER</code> HASHED_CREDIT_CARD_NUMBER column of table CLW_ORDER_CREDIT_CARD */
    public static final String HASHED_CREDIT_CARD_NUMBER = "HASHED_CREDIT_CARD_NUMBER";
    /** <code>EXP_MONTH</code> EXP_MONTH column of table CLW_ORDER_CREDIT_CARD */
    public static final String EXP_MONTH = "EXP_MONTH";
    /** <code>EXP_YEAR</code> EXP_YEAR column of table CLW_ORDER_CREDIT_CARD */
    public static final String EXP_YEAR = "EXP_YEAR";
    /** <code>NAME</code> NAME column of table CLW_ORDER_CREDIT_CARD */
    public static final String NAME = "NAME";
    /** <code>ADDRESS1</code> ADDRESS1 column of table CLW_ORDER_CREDIT_CARD */
    public static final String ADDRESS1 = "ADDRESS1";
    /** <code>ADDRESS2</code> ADDRESS2 column of table CLW_ORDER_CREDIT_CARD */
    public static final String ADDRESS2 = "ADDRESS2";
    /** <code>ADDRESS3</code> ADDRESS3 column of table CLW_ORDER_CREDIT_CARD */
    public static final String ADDRESS3 = "ADDRESS3";
    /** <code>ADDRESS4</code> ADDRESS4 column of table CLW_ORDER_CREDIT_CARD */
    public static final String ADDRESS4 = "ADDRESS4";
    /** <code>CITY</code> CITY column of table CLW_ORDER_CREDIT_CARD */
    public static final String CITY = "CITY";
    /** <code>STATE_PROVINCE_CD</code> STATE_PROVINCE_CD column of table CLW_ORDER_CREDIT_CARD */
    public static final String STATE_PROVINCE_CD = "STATE_PROVINCE_CD";
    /** <code>COUNTRY_CD</code> COUNTRY_CD column of table CLW_ORDER_CREDIT_CARD */
    public static final String COUNTRY_CD = "COUNTRY_CD";
    /** <code>POSTAL_CODE</code> POSTAL_CODE column of table CLW_ORDER_CREDIT_CARD */
    public static final String POSTAL_CODE = "POSTAL_CODE";
    /** <code>AUTH_STATUS_CD</code> AUTH_STATUS_CD column of table CLW_ORDER_CREDIT_CARD */
    public static final String AUTH_STATUS_CD = "AUTH_STATUS_CD";
    /** <code>AUTH_ADDRESS_STATUS_CD</code> AUTH_ADDRESS_STATUS_CD column of table CLW_ORDER_CREDIT_CARD */
    public static final String AUTH_ADDRESS_STATUS_CD = "AUTH_ADDRESS_STATUS_CD";
    /** <code>CURRENCY</code> CURRENCY column of table CLW_ORDER_CREDIT_CARD */
    public static final String CURRENCY = "CURRENCY";
    /** <code>AVS_STATUS</code> AVS_STATUS column of table CLW_ORDER_CREDIT_CARD */
    public static final String AVS_STATUS = "AVS_STATUS";
    /** <code>AVS_ADDRESS</code> AVS_ADDRESS column of table CLW_ORDER_CREDIT_CARD */
    public static final String AVS_ADDRESS = "AVS_ADDRESS";
    /** <code>AVS_ZIP_CODE</code> AVS_ZIP_CODE column of table CLW_ORDER_CREDIT_CARD */
    public static final String AVS_ZIP_CODE = "AVS_ZIP_CODE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_CREDIT_CARD */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_CREDIT_CARD */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_CREDIT_CARD */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_CREDIT_CARD */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public OrderCreditCardDataAccess()
    {
    }

    /**
     * Gets a OrderCreditCardData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderCreditCardId The key requested.
     * @return new OrderCreditCardData()
     * @throws            SQLException
     */
    public static OrderCreditCardData select(Connection pCon, int pOrderCreditCardId)
        throws SQLException, DataNotFoundException {
        OrderCreditCardData x=null;
        String sql="SELECT ORDER_CREDIT_CARD_ID,ORDER_ID,ENCRYPTION_ALGORITHM,ENCRYPTION_ALIAS,ENCRYPTED_CREDIT_CARD_NUMBER,CREDIT_CARD_NUMBER_DISPLAY,CREDIT_CARD_TYPE,HASH_ALGORITHM,HASHED_CREDIT_CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,AUTH_STATUS_CD,AUTH_ADDRESS_STATUS_CD,CURRENCY,AVS_STATUS,AVS_ADDRESS,AVS_ZIP_CODE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ORDER_CREDIT_CARD WHERE ORDER_CREDIT_CARD_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderCreditCardId=" + pOrderCreditCardId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderCreditCardId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderCreditCardData.createValue();
            
            x.setOrderCreditCardId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setEncryptionAlgorithm(rs.getString(3));
            x.setEncryptionAlias(rs.getString(4));
            x.setEncryptedCreditCardNumber(rs.getString(5));
            x.setCreditCardNumberDisplay(rs.getString(6));
            x.setCreditCardType(rs.getString(7));
            x.setHashAlgorithm(rs.getString(8));
            x.setHashedCreditCardNumber(rs.getString(9));
            x.setExpMonth(rs.getInt(10));
            x.setExpYear(rs.getInt(11));
            x.setName(rs.getString(12));
            x.setAddress1(rs.getString(13));
            x.setAddress2(rs.getString(14));
            x.setAddress3(rs.getString(15));
            x.setAddress4(rs.getString(16));
            x.setCity(rs.getString(17));
            x.setStateProvinceCd(rs.getString(18));
            x.setCountryCd(rs.getString(19));
            x.setPostalCode(rs.getString(20));
            x.setAuthStatusCd(rs.getString(21));
            x.setAuthAddressStatusCd(rs.getString(22));
            x.setCurrency(rs.getString(23));
            x.setAvsStatus(rs.getString(24));
            x.setAvsAddress(rs.getString(25));
            x.setAvsZipCode(rs.getString(26));
            x.setAddBy(rs.getString(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_CREDIT_CARD_ID :" + pOrderCreditCardId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderCreditCardDataVector object that consists
     * of OrderCreditCardData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderCreditCardDataVector()
     * @throws            SQLException
     */
    public static OrderCreditCardDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderCreditCardData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_CREDIT_CARD.ORDER_CREDIT_CARD_ID,CLW_ORDER_CREDIT_CARD.ORDER_ID,CLW_ORDER_CREDIT_CARD.ENCRYPTION_ALGORITHM,CLW_ORDER_CREDIT_CARD.ENCRYPTION_ALIAS,CLW_ORDER_CREDIT_CARD.ENCRYPTED_CREDIT_CARD_NUMBER,CLW_ORDER_CREDIT_CARD.CREDIT_CARD_NUMBER_DISPLAY,CLW_ORDER_CREDIT_CARD.CREDIT_CARD_TYPE,CLW_ORDER_CREDIT_CARD.HASH_ALGORITHM,CLW_ORDER_CREDIT_CARD.HASHED_CREDIT_CARD_NUMBER,CLW_ORDER_CREDIT_CARD.EXP_MONTH,CLW_ORDER_CREDIT_CARD.EXP_YEAR,CLW_ORDER_CREDIT_CARD.NAME,CLW_ORDER_CREDIT_CARD.ADDRESS1,CLW_ORDER_CREDIT_CARD.ADDRESS2,CLW_ORDER_CREDIT_CARD.ADDRESS3,CLW_ORDER_CREDIT_CARD.ADDRESS4,CLW_ORDER_CREDIT_CARD.CITY,CLW_ORDER_CREDIT_CARD.STATE_PROVINCE_CD,CLW_ORDER_CREDIT_CARD.COUNTRY_CD,CLW_ORDER_CREDIT_CARD.POSTAL_CODE,CLW_ORDER_CREDIT_CARD.AUTH_STATUS_CD,CLW_ORDER_CREDIT_CARD.AUTH_ADDRESS_STATUS_CD,CLW_ORDER_CREDIT_CARD.CURRENCY,CLW_ORDER_CREDIT_CARD.AVS_STATUS,CLW_ORDER_CREDIT_CARD.AVS_ADDRESS,CLW_ORDER_CREDIT_CARD.AVS_ZIP_CODE,CLW_ORDER_CREDIT_CARD.ADD_BY,CLW_ORDER_CREDIT_CARD.ADD_DATE,CLW_ORDER_CREDIT_CARD.MOD_BY,CLW_ORDER_CREDIT_CARD.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderCreditCardData Object.
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
    *@returns a populated OrderCreditCardData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderCreditCardData x = OrderCreditCardData.createValue();
         
         x.setOrderCreditCardId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setEncryptionAlgorithm(rs.getString(3+offset));
         x.setEncryptionAlias(rs.getString(4+offset));
         x.setEncryptedCreditCardNumber(rs.getString(5+offset));
         x.setCreditCardNumberDisplay(rs.getString(6+offset));
         x.setCreditCardType(rs.getString(7+offset));
         x.setHashAlgorithm(rs.getString(8+offset));
         x.setHashedCreditCardNumber(rs.getString(9+offset));
         x.setExpMonth(rs.getInt(10+offset));
         x.setExpYear(rs.getInt(11+offset));
         x.setName(rs.getString(12+offset));
         x.setAddress1(rs.getString(13+offset));
         x.setAddress2(rs.getString(14+offset));
         x.setAddress3(rs.getString(15+offset));
         x.setAddress4(rs.getString(16+offset));
         x.setCity(rs.getString(17+offset));
         x.setStateProvinceCd(rs.getString(18+offset));
         x.setCountryCd(rs.getString(19+offset));
         x.setPostalCode(rs.getString(20+offset));
         x.setAuthStatusCd(rs.getString(21+offset));
         x.setAuthAddressStatusCd(rs.getString(22+offset));
         x.setCurrency(rs.getString(23+offset));
         x.setAvsStatus(rs.getString(24+offset));
         x.setAvsAddress(rs.getString(25+offset));
         x.setAvsZipCode(rs.getString(26+offset));
         x.setAddBy(rs.getString(27+offset));
         x.setAddDate(rs.getTimestamp(28+offset));
         x.setModBy(rs.getString(29+offset));
         x.setModDate(rs.getTimestamp(30+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderCreditCardData Object represents.
    */
    public int getColumnCount(){
        return 30;
    }

    /**
     * Gets a OrderCreditCardDataVector object that consists
     * of OrderCreditCardData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderCreditCardDataVector()
     * @throws            SQLException
     */
    public static OrderCreditCardDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_CREDIT_CARD_ID,ORDER_ID,ENCRYPTION_ALGORITHM,ENCRYPTION_ALIAS,ENCRYPTED_CREDIT_CARD_NUMBER,CREDIT_CARD_NUMBER_DISPLAY,CREDIT_CARD_TYPE,HASH_ALGORITHM,HASHED_CREDIT_CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,AUTH_STATUS_CD,AUTH_ADDRESS_STATUS_CD,CURRENCY,AVS_STATUS,AVS_ADDRESS,AVS_ZIP_CODE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ORDER_CREDIT_CARD");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_CREDIT_CARD.ORDER_CREDIT_CARD_ID,CLW_ORDER_CREDIT_CARD.ORDER_ID,CLW_ORDER_CREDIT_CARD.ENCRYPTION_ALGORITHM,CLW_ORDER_CREDIT_CARD.ENCRYPTION_ALIAS,CLW_ORDER_CREDIT_CARD.ENCRYPTED_CREDIT_CARD_NUMBER,CLW_ORDER_CREDIT_CARD.CREDIT_CARD_NUMBER_DISPLAY,CLW_ORDER_CREDIT_CARD.CREDIT_CARD_TYPE,CLW_ORDER_CREDIT_CARD.HASH_ALGORITHM,CLW_ORDER_CREDIT_CARD.HASHED_CREDIT_CARD_NUMBER,CLW_ORDER_CREDIT_CARD.EXP_MONTH,CLW_ORDER_CREDIT_CARD.EXP_YEAR,CLW_ORDER_CREDIT_CARD.NAME,CLW_ORDER_CREDIT_CARD.ADDRESS1,CLW_ORDER_CREDIT_CARD.ADDRESS2,CLW_ORDER_CREDIT_CARD.ADDRESS3,CLW_ORDER_CREDIT_CARD.ADDRESS4,CLW_ORDER_CREDIT_CARD.CITY,CLW_ORDER_CREDIT_CARD.STATE_PROVINCE_CD,CLW_ORDER_CREDIT_CARD.COUNTRY_CD,CLW_ORDER_CREDIT_CARD.POSTAL_CODE,CLW_ORDER_CREDIT_CARD.AUTH_STATUS_CD,CLW_ORDER_CREDIT_CARD.AUTH_ADDRESS_STATUS_CD,CLW_ORDER_CREDIT_CARD.CURRENCY,CLW_ORDER_CREDIT_CARD.AVS_STATUS,CLW_ORDER_CREDIT_CARD.AVS_ADDRESS,CLW_ORDER_CREDIT_CARD.AVS_ZIP_CODE,CLW_ORDER_CREDIT_CARD.ADD_BY,CLW_ORDER_CREDIT_CARD.ADD_DATE,CLW_ORDER_CREDIT_CARD.MOD_BY,CLW_ORDER_CREDIT_CARD.MOD_DATE FROM CLW_ORDER_CREDIT_CARD");
                where = pCriteria.getSqlClause("CLW_ORDER_CREDIT_CARD");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_CREDIT_CARD.equals(otherTable)){
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
        OrderCreditCardDataVector v = new OrderCreditCardDataVector();
        while (rs.next()) {
            OrderCreditCardData x = OrderCreditCardData.createValue();
            
            x.setOrderCreditCardId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setEncryptionAlgorithm(rs.getString(3));
            x.setEncryptionAlias(rs.getString(4));
            x.setEncryptedCreditCardNumber(rs.getString(5));
            x.setCreditCardNumberDisplay(rs.getString(6));
            x.setCreditCardType(rs.getString(7));
            x.setHashAlgorithm(rs.getString(8));
            x.setHashedCreditCardNumber(rs.getString(9));
            x.setExpMonth(rs.getInt(10));
            x.setExpYear(rs.getInt(11));
            x.setName(rs.getString(12));
            x.setAddress1(rs.getString(13));
            x.setAddress2(rs.getString(14));
            x.setAddress3(rs.getString(15));
            x.setAddress4(rs.getString(16));
            x.setCity(rs.getString(17));
            x.setStateProvinceCd(rs.getString(18));
            x.setCountryCd(rs.getString(19));
            x.setPostalCode(rs.getString(20));
            x.setAuthStatusCd(rs.getString(21));
            x.setAuthAddressStatusCd(rs.getString(22));
            x.setCurrency(rs.getString(23));
            x.setAvsStatus(rs.getString(24));
            x.setAvsAddress(rs.getString(25));
            x.setAvsZipCode(rs.getString(26));
            x.setAddBy(rs.getString(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderCreditCardDataVector object that consists
     * of OrderCreditCardData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderCreditCardData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderCreditCardDataVector()
     * @throws            SQLException
     */
    public static OrderCreditCardDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderCreditCardDataVector v = new OrderCreditCardDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_CREDIT_CARD_ID,ORDER_ID,ENCRYPTION_ALGORITHM,ENCRYPTION_ALIAS,ENCRYPTED_CREDIT_CARD_NUMBER,CREDIT_CARD_NUMBER_DISPLAY,CREDIT_CARD_TYPE,HASH_ALGORITHM,HASHED_CREDIT_CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,AUTH_STATUS_CD,AUTH_ADDRESS_STATUS_CD,CURRENCY,AVS_STATUS,AVS_ADDRESS,AVS_ZIP_CODE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ORDER_CREDIT_CARD WHERE ORDER_CREDIT_CARD_ID IN (");

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
            OrderCreditCardData x=null;
            while (rs.next()) {
                // build the object
                x=OrderCreditCardData.createValue();
                
                x.setOrderCreditCardId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setEncryptionAlgorithm(rs.getString(3));
                x.setEncryptionAlias(rs.getString(4));
                x.setEncryptedCreditCardNumber(rs.getString(5));
                x.setCreditCardNumberDisplay(rs.getString(6));
                x.setCreditCardType(rs.getString(7));
                x.setHashAlgorithm(rs.getString(8));
                x.setHashedCreditCardNumber(rs.getString(9));
                x.setExpMonth(rs.getInt(10));
                x.setExpYear(rs.getInt(11));
                x.setName(rs.getString(12));
                x.setAddress1(rs.getString(13));
                x.setAddress2(rs.getString(14));
                x.setAddress3(rs.getString(15));
                x.setAddress4(rs.getString(16));
                x.setCity(rs.getString(17));
                x.setStateProvinceCd(rs.getString(18));
                x.setCountryCd(rs.getString(19));
                x.setPostalCode(rs.getString(20));
                x.setAuthStatusCd(rs.getString(21));
                x.setAuthAddressStatusCd(rs.getString(22));
                x.setCurrency(rs.getString(23));
                x.setAvsStatus(rs.getString(24));
                x.setAvsAddress(rs.getString(25));
                x.setAvsZipCode(rs.getString(26));
                x.setAddBy(rs.getString(27));
                x.setAddDate(rs.getTimestamp(28));
                x.setModBy(rs.getString(29));
                x.setModDate(rs.getTimestamp(30));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderCreditCardDataVector object of all
     * OrderCreditCardData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderCreditCardDataVector()
     * @throws            SQLException
     */
    public static OrderCreditCardDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_CREDIT_CARD_ID,ORDER_ID,ENCRYPTION_ALGORITHM,ENCRYPTION_ALIAS,ENCRYPTED_CREDIT_CARD_NUMBER,CREDIT_CARD_NUMBER_DISPLAY,CREDIT_CARD_TYPE,HASH_ALGORITHM,HASHED_CREDIT_CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,AUTH_STATUS_CD,AUTH_ADDRESS_STATUS_CD,CURRENCY,AVS_STATUS,AVS_ADDRESS,AVS_ZIP_CODE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_ORDER_CREDIT_CARD";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderCreditCardDataVector v = new OrderCreditCardDataVector();
        OrderCreditCardData x = null;
        while (rs.next()) {
            // build the object
            x = OrderCreditCardData.createValue();
            
            x.setOrderCreditCardId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setEncryptionAlgorithm(rs.getString(3));
            x.setEncryptionAlias(rs.getString(4));
            x.setEncryptedCreditCardNumber(rs.getString(5));
            x.setCreditCardNumberDisplay(rs.getString(6));
            x.setCreditCardType(rs.getString(7));
            x.setHashAlgorithm(rs.getString(8));
            x.setHashedCreditCardNumber(rs.getString(9));
            x.setExpMonth(rs.getInt(10));
            x.setExpYear(rs.getInt(11));
            x.setName(rs.getString(12));
            x.setAddress1(rs.getString(13));
            x.setAddress2(rs.getString(14));
            x.setAddress3(rs.getString(15));
            x.setAddress4(rs.getString(16));
            x.setCity(rs.getString(17));
            x.setStateProvinceCd(rs.getString(18));
            x.setCountryCd(rs.getString(19));
            x.setPostalCode(rs.getString(20));
            x.setAuthStatusCd(rs.getString(21));
            x.setAuthAddressStatusCd(rs.getString(22));
            x.setCurrency(rs.getString(23));
            x.setAvsStatus(rs.getString(24));
            x.setAvsAddress(rs.getString(25));
            x.setAvsZipCode(rs.getString(26));
            x.setAddBy(rs.getString(27));
            x.setAddDate(rs.getTimestamp(28));
            x.setModBy(rs.getString(29));
            x.setModDate(rs.getTimestamp(30));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderCreditCardData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_CREDIT_CARD_ID FROM CLW_ORDER_CREDIT_CARD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_CREDIT_CARD");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_CREDIT_CARD");
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
     * Inserts a OrderCreditCardData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderCreditCardData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderCreditCardData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderCreditCardData insert(Connection pCon, OrderCreditCardData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_CREDIT_CARD_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_CREDIT_CARD_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderCreditCardId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_CREDIT_CARD (ORDER_CREDIT_CARD_ID,ORDER_ID,ENCRYPTION_ALGORITHM,ENCRYPTION_ALIAS,ENCRYPTED_CREDIT_CARD_NUMBER,CREDIT_CARD_NUMBER_DISPLAY,CREDIT_CARD_TYPE,HASH_ALGORITHM,HASHED_CREDIT_CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,AUTH_STATUS_CD,AUTH_ADDRESS_STATUS_CD,CURRENCY,AVS_STATUS,AVS_ADDRESS,AVS_ZIP_CODE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderCreditCardId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        pstmt.setString(3,pData.getEncryptionAlgorithm());
        pstmt.setString(4,pData.getEncryptionAlias());
        pstmt.setString(5,pData.getEncryptedCreditCardNumber());
        pstmt.setString(6,pData.getCreditCardNumberDisplay());
        pstmt.setString(7,pData.getCreditCardType());
        pstmt.setString(8,pData.getHashAlgorithm());
        pstmt.setString(9,pData.getHashedCreditCardNumber());
        pstmt.setInt(10,pData.getExpMonth());
        pstmt.setInt(11,pData.getExpYear());
        pstmt.setString(12,pData.getName());
        pstmt.setString(13,pData.getAddress1());
        pstmt.setString(14,pData.getAddress2());
        pstmt.setString(15,pData.getAddress3());
        pstmt.setString(16,pData.getAddress4());
        pstmt.setString(17,pData.getCity());
        pstmt.setString(18,pData.getStateProvinceCd());
        pstmt.setString(19,pData.getCountryCd());
        pstmt.setString(20,pData.getPostalCode());
        pstmt.setString(21,pData.getAuthStatusCd());
        pstmt.setString(22,pData.getAuthAddressStatusCd());
        pstmt.setString(23,pData.getCurrency());
        pstmt.setString(24,pData.getAvsStatus());
        pstmt.setString(25,pData.getAvsAddress());
        pstmt.setString(26,pData.getAvsZipCode());
        pstmt.setString(27,pData.getAddBy());
        pstmt.setTimestamp(28,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(29,pData.getModBy());
        pstmt.setTimestamp(30,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_CREDIT_CARD_ID="+pData.getOrderCreditCardId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ENCRYPTION_ALGORITHM="+pData.getEncryptionAlgorithm());
            log.debug("SQL:   ENCRYPTION_ALIAS="+pData.getEncryptionAlias());
            log.debug("SQL:   ENCRYPTED_CREDIT_CARD_NUMBER="+pData.getEncryptedCreditCardNumber());
            log.debug("SQL:   CREDIT_CARD_NUMBER_DISPLAY="+pData.getCreditCardNumberDisplay());
            log.debug("SQL:   CREDIT_CARD_TYPE="+pData.getCreditCardType());
            log.debug("SQL:   HASH_ALGORITHM="+pData.getHashAlgorithm());
            log.debug("SQL:   HASHED_CREDIT_CARD_NUMBER="+pData.getHashedCreditCardNumber());
            log.debug("SQL:   EXP_MONTH="+pData.getExpMonth());
            log.debug("SQL:   EXP_YEAR="+pData.getExpYear());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   ADDRESS1="+pData.getAddress1());
            log.debug("SQL:   ADDRESS2="+pData.getAddress2());
            log.debug("SQL:   ADDRESS3="+pData.getAddress3());
            log.debug("SQL:   ADDRESS4="+pData.getAddress4());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   AUTH_STATUS_CD="+pData.getAuthStatusCd());
            log.debug("SQL:   AUTH_ADDRESS_STATUS_CD="+pData.getAuthAddressStatusCd());
            log.debug("SQL:   CURRENCY="+pData.getCurrency());
            log.debug("SQL:   AVS_STATUS="+pData.getAvsStatus());
            log.debug("SQL:   AVS_ADDRESS="+pData.getAvsAddress());
            log.debug("SQL:   AVS_ZIP_CODE="+pData.getAvsZipCode());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderCreditCardId(0);
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
     * Updates a OrderCreditCardData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderCreditCardData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderCreditCardData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_CREDIT_CARD SET ORDER_ID = ?,ENCRYPTION_ALGORITHM = ?,ENCRYPTION_ALIAS = ?,ENCRYPTED_CREDIT_CARD_NUMBER = ?,CREDIT_CARD_NUMBER_DISPLAY = ?,CREDIT_CARD_TYPE = ?,HASH_ALGORITHM = ?,HASHED_CREDIT_CARD_NUMBER = ?,EXP_MONTH = ?,EXP_YEAR = ?,NAME = ?,ADDRESS1 = ?,ADDRESS2 = ?,ADDRESS3 = ?,ADDRESS4 = ?,CITY = ?,STATE_PROVINCE_CD = ?,COUNTRY_CD = ?,POSTAL_CODE = ?,AUTH_STATUS_CD = ?,AUTH_ADDRESS_STATUS_CD = ?,CURRENCY = ?,AVS_STATUS = ?,AVS_ADDRESS = ?,AVS_ZIP_CODE = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE ORDER_CREDIT_CARD_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setString(i++,pData.getEncryptionAlgorithm());
        pstmt.setString(i++,pData.getEncryptionAlias());
        pstmt.setString(i++,pData.getEncryptedCreditCardNumber());
        pstmt.setString(i++,pData.getCreditCardNumberDisplay());
        pstmt.setString(i++,pData.getCreditCardType());
        pstmt.setString(i++,pData.getHashAlgorithm());
        pstmt.setString(i++,pData.getHashedCreditCardNumber());
        pstmt.setInt(i++,pData.getExpMonth());
        pstmt.setInt(i++,pData.getExpYear());
        pstmt.setString(i++,pData.getName());
        pstmt.setString(i++,pData.getAddress1());
        pstmt.setString(i++,pData.getAddress2());
        pstmt.setString(i++,pData.getAddress3());
        pstmt.setString(i++,pData.getAddress4());
        pstmt.setString(i++,pData.getCity());
        pstmt.setString(i++,pData.getStateProvinceCd());
        pstmt.setString(i++,pData.getCountryCd());
        pstmt.setString(i++,pData.getPostalCode());
        pstmt.setString(i++,pData.getAuthStatusCd());
        pstmt.setString(i++,pData.getAuthAddressStatusCd());
        pstmt.setString(i++,pData.getCurrency());
        pstmt.setString(i++,pData.getAvsStatus());
        pstmt.setString(i++,pData.getAvsAddress());
        pstmt.setString(i++,pData.getAvsZipCode());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getOrderCreditCardId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ENCRYPTION_ALGORITHM="+pData.getEncryptionAlgorithm());
            log.debug("SQL:   ENCRYPTION_ALIAS="+pData.getEncryptionAlias());
            log.debug("SQL:   ENCRYPTED_CREDIT_CARD_NUMBER="+pData.getEncryptedCreditCardNumber());
            log.debug("SQL:   CREDIT_CARD_NUMBER_DISPLAY="+pData.getCreditCardNumberDisplay());
            log.debug("SQL:   CREDIT_CARD_TYPE="+pData.getCreditCardType());
            log.debug("SQL:   HASH_ALGORITHM="+pData.getHashAlgorithm());
            log.debug("SQL:   HASHED_CREDIT_CARD_NUMBER="+pData.getHashedCreditCardNumber());
            log.debug("SQL:   EXP_MONTH="+pData.getExpMonth());
            log.debug("SQL:   EXP_YEAR="+pData.getExpYear());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   ADDRESS1="+pData.getAddress1());
            log.debug("SQL:   ADDRESS2="+pData.getAddress2());
            log.debug("SQL:   ADDRESS3="+pData.getAddress3());
            log.debug("SQL:   ADDRESS4="+pData.getAddress4());
            log.debug("SQL:   CITY="+pData.getCity());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   AUTH_STATUS_CD="+pData.getAuthStatusCd());
            log.debug("SQL:   AUTH_ADDRESS_STATUS_CD="+pData.getAuthAddressStatusCd());
            log.debug("SQL:   CURRENCY="+pData.getCurrency());
            log.debug("SQL:   AVS_STATUS="+pData.getAvsStatus());
            log.debug("SQL:   AVS_ADDRESS="+pData.getAvsAddress());
            log.debug("SQL:   AVS_ZIP_CODE="+pData.getAvsZipCode());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderCreditCardData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderCreditCardId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderCreditCardId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_CREDIT_CARD WHERE ORDER_CREDIT_CARD_ID = " + pOrderCreditCardId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderCreditCardData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_CREDIT_CARD");
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
     * Inserts a OrderCreditCardData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderCreditCardData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderCreditCardData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_CREDIT_CARD (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_CREDIT_CARD_ID,ORDER_ID,ENCRYPTION_ALGORITHM,ENCRYPTION_ALIAS,ENCRYPTED_CREDIT_CARD_NUMBER,CREDIT_CARD_NUMBER_DISPLAY,CREDIT_CARD_TYPE,HASH_ALGORITHM,HASHED_CREDIT_CARD_NUMBER,EXP_MONTH,EXP_YEAR,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,STATE_PROVINCE_CD,COUNTRY_CD,POSTAL_CODE,AUTH_STATUS_CD,AUTH_ADDRESS_STATUS_CD,CURRENCY,AVS_STATUS,AVS_ADDRESS,AVS_ZIP_CODE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderCreditCardId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getOrderId());
        }

        pstmt.setString(3+4,pData.getEncryptionAlgorithm());
        pstmt.setString(4+4,pData.getEncryptionAlias());
        pstmt.setString(5+4,pData.getEncryptedCreditCardNumber());
        pstmt.setString(6+4,pData.getCreditCardNumberDisplay());
        pstmt.setString(7+4,pData.getCreditCardType());
        pstmt.setString(8+4,pData.getHashAlgorithm());
        pstmt.setString(9+4,pData.getHashedCreditCardNumber());
        pstmt.setInt(10+4,pData.getExpMonth());
        pstmt.setInt(11+4,pData.getExpYear());
        pstmt.setString(12+4,pData.getName());
        pstmt.setString(13+4,pData.getAddress1());
        pstmt.setString(14+4,pData.getAddress2());
        pstmt.setString(15+4,pData.getAddress3());
        pstmt.setString(16+4,pData.getAddress4());
        pstmt.setString(17+4,pData.getCity());
        pstmt.setString(18+4,pData.getStateProvinceCd());
        pstmt.setString(19+4,pData.getCountryCd());
        pstmt.setString(20+4,pData.getPostalCode());
        pstmt.setString(21+4,pData.getAuthStatusCd());
        pstmt.setString(22+4,pData.getAuthAddressStatusCd());
        pstmt.setString(23+4,pData.getCurrency());
        pstmt.setString(24+4,pData.getAvsStatus());
        pstmt.setString(25+4,pData.getAvsAddress());
        pstmt.setString(26+4,pData.getAvsZipCode());
        pstmt.setString(27+4,pData.getAddBy());
        pstmt.setTimestamp(28+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(29+4,pData.getModBy());
        pstmt.setTimestamp(30+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderCreditCardData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderCreditCardData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderCreditCardData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderCreditCardData insert(Connection pCon, OrderCreditCardData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderCreditCardData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderCreditCardData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderCreditCardData pData, boolean pLogFl)
        throws SQLException {
        OrderCreditCardData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderCreditCardId();
          try {
          oldData = OrderCreditCardDataAccess.select(pCon,id);
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
     * Deletes a OrderCreditCardData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderCreditCardId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderCreditCardId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_CREDIT_CARD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_CREDIT_CARD d WHERE ORDER_CREDIT_CARD_ID = " + pOrderCreditCardId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderCreditCardId);
        return n;
     }

    /**
     * Deletes OrderCreditCardData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_CREDIT_CARD SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_CREDIT_CARD d ");
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

