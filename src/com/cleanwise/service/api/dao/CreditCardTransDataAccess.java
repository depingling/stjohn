
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CreditCardTransDataAccess
 * Description:  This class is used to build access methods to the CLW_CREDIT_CARD_TRANS table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CreditCardTransData;
import com.cleanwise.service.api.value.CreditCardTransDataVector;
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
 * <code>CreditCardTransDataAccess</code>
 */
public class CreditCardTransDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CreditCardTransDataAccess.class.getName());

    /** <code>CLW_CREDIT_CARD_TRANS</code> table name */
	/* Primary key: CREDIT_CARD_TRANS_ID */
	
    public static final String CLW_CREDIT_CARD_TRANS = "CLW_CREDIT_CARD_TRANS";
    
    /** <code>CREDIT_CARD_TRANS_ID</code> CREDIT_CARD_TRANS_ID column of table CLW_CREDIT_CARD_TRANS */
    public static final String CREDIT_CARD_TRANS_ID = "CREDIT_CARD_TRANS_ID";
    /** <code>ORDER_CREDIT_CARD_ID</code> ORDER_CREDIT_CARD_ID column of table CLW_CREDIT_CARD_TRANS */
    public static final String ORDER_CREDIT_CARD_ID = "ORDER_CREDIT_CARD_ID";
    /** <code>INVOICE_CUST_ID</code> INVOICE_CUST_ID column of table CLW_CREDIT_CARD_TRANS */
    public static final String INVOICE_CUST_ID = "INVOICE_CUST_ID";
    /** <code>TRANSACTION_TYPE_CD</code> TRANSACTION_TYPE_CD column of table CLW_CREDIT_CARD_TRANS */
    public static final String TRANSACTION_TYPE_CD = "TRANSACTION_TYPE_CD";
    /** <code>AMOUNT</code> AMOUNT column of table CLW_CREDIT_CARD_TRANS */
    public static final String AMOUNT = "AMOUNT";
    /** <code>TRANSACTION_REFERENCE</code> TRANSACTION_REFERENCE column of table CLW_CREDIT_CARD_TRANS */
    public static final String TRANSACTION_REFERENCE = "TRANSACTION_REFERENCE";
    /** <code>AUTH_CODE</code> AUTH_CODE column of table CLW_CREDIT_CARD_TRANS */
    public static final String AUTH_CODE = "AUTH_CODE";
    /** <code>PAYMETRIC_RESPONSE_CODE</code> PAYMETRIC_RESPONSE_CODE column of table CLW_CREDIT_CARD_TRANS */
    public static final String PAYMETRIC_RESPONSE_CODE = "PAYMETRIC_RESPONSE_CODE";
    /** <code>PAYMETRIC_AUTH_DATE</code> PAYMETRIC_AUTH_DATE column of table CLW_CREDIT_CARD_TRANS */
    public static final String PAYMETRIC_AUTH_DATE = "PAYMETRIC_AUTH_DATE";
    /** <code>PAYMETRIC_AUTH_TIME</code> PAYMETRIC_AUTH_TIME column of table CLW_CREDIT_CARD_TRANS */
    public static final String PAYMETRIC_AUTH_TIME = "PAYMETRIC_AUTH_TIME";
    /** <code>PAYMETRIC_TRANSACTION_ID</code> PAYMETRIC_TRANSACTION_ID column of table CLW_CREDIT_CARD_TRANS */
    public static final String PAYMETRIC_TRANSACTION_ID = "PAYMETRIC_TRANSACTION_ID";
    /** <code>PAYMETRIC_AUTH_MESSAGE</code> PAYMETRIC_AUTH_MESSAGE column of table CLW_CREDIT_CARD_TRANS */
    public static final String PAYMETRIC_AUTH_MESSAGE = "PAYMETRIC_AUTH_MESSAGE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CREDIT_CARD_TRANS */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CREDIT_CARD_TRANS */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CREDIT_CARD_TRANS */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CREDIT_CARD_TRANS */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public CreditCardTransDataAccess()
    {
    }

    /**
     * Gets a CreditCardTransData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCreditCardTransId The key requested.
     * @return new CreditCardTransData()
     * @throws            SQLException
     */
    public static CreditCardTransData select(Connection pCon, int pCreditCardTransId)
        throws SQLException, DataNotFoundException {
        CreditCardTransData x=null;
        String sql="SELECT CREDIT_CARD_TRANS_ID,ORDER_CREDIT_CARD_ID,INVOICE_CUST_ID,TRANSACTION_TYPE_CD,AMOUNT,TRANSACTION_REFERENCE,AUTH_CODE,PAYMETRIC_RESPONSE_CODE,PAYMETRIC_AUTH_DATE,PAYMETRIC_AUTH_TIME,PAYMETRIC_TRANSACTION_ID,PAYMETRIC_AUTH_MESSAGE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_CREDIT_CARD_TRANS WHERE CREDIT_CARD_TRANS_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCreditCardTransId=" + pCreditCardTransId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCreditCardTransId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CreditCardTransData.createValue();
            
            x.setCreditCardTransId(rs.getInt(1));
            x.setOrderCreditCardId(rs.getInt(2));
            x.setInvoiceCustId(rs.getInt(3));
            x.setTransactionTypeCd(rs.getString(4));
            x.setAmount(rs.getBigDecimal(5));
            x.setTransactionReference(rs.getString(6));
            x.setAuthCode(rs.getString(7));
            x.setPaymetricResponseCode(rs.getString(8));
            x.setPaymetricAuthDate(rs.getDate(9));
            x.setPaymetricAuthTime(rs.getTimestamp(10));
            x.setPaymetricTransactionId(rs.getString(11));
            x.setPaymetricAuthMessage(rs.getString(12));
            x.setAddBy(rs.getString(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CREDIT_CARD_TRANS_ID :" + pCreditCardTransId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CreditCardTransDataVector object that consists
     * of CreditCardTransData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CreditCardTransDataVector()
     * @throws            SQLException
     */
    public static CreditCardTransDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CreditCardTransData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CREDIT_CARD_TRANS.CREDIT_CARD_TRANS_ID,CLW_CREDIT_CARD_TRANS.ORDER_CREDIT_CARD_ID,CLW_CREDIT_CARD_TRANS.INVOICE_CUST_ID,CLW_CREDIT_CARD_TRANS.TRANSACTION_TYPE_CD,CLW_CREDIT_CARD_TRANS.AMOUNT,CLW_CREDIT_CARD_TRANS.TRANSACTION_REFERENCE,CLW_CREDIT_CARD_TRANS.AUTH_CODE,CLW_CREDIT_CARD_TRANS.PAYMETRIC_RESPONSE_CODE,CLW_CREDIT_CARD_TRANS.PAYMETRIC_AUTH_DATE,CLW_CREDIT_CARD_TRANS.PAYMETRIC_AUTH_TIME,CLW_CREDIT_CARD_TRANS.PAYMETRIC_TRANSACTION_ID,CLW_CREDIT_CARD_TRANS.PAYMETRIC_AUTH_MESSAGE,CLW_CREDIT_CARD_TRANS.ADD_BY,CLW_CREDIT_CARD_TRANS.ADD_DATE,CLW_CREDIT_CARD_TRANS.MOD_BY,CLW_CREDIT_CARD_TRANS.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CreditCardTransData Object.
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
    *@returns a populated CreditCardTransData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CreditCardTransData x = CreditCardTransData.createValue();
         
         x.setCreditCardTransId(rs.getInt(1+offset));
         x.setOrderCreditCardId(rs.getInt(2+offset));
         x.setInvoiceCustId(rs.getInt(3+offset));
         x.setTransactionTypeCd(rs.getString(4+offset));
         x.setAmount(rs.getBigDecimal(5+offset));
         x.setTransactionReference(rs.getString(6+offset));
         x.setAuthCode(rs.getString(7+offset));
         x.setPaymetricResponseCode(rs.getString(8+offset));
         x.setPaymetricAuthDate(rs.getDate(9+offset));
         x.setPaymetricAuthTime(rs.getTimestamp(10+offset));
         x.setPaymetricTransactionId(rs.getString(11+offset));
         x.setPaymetricAuthMessage(rs.getString(12+offset));
         x.setAddBy(rs.getString(13+offset));
         x.setAddDate(rs.getTimestamp(14+offset));
         x.setModBy(rs.getString(15+offset));
         x.setModDate(rs.getTimestamp(16+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CreditCardTransData Object represents.
    */
    public int getColumnCount(){
        return 16;
    }

    /**
     * Gets a CreditCardTransDataVector object that consists
     * of CreditCardTransData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CreditCardTransDataVector()
     * @throws            SQLException
     */
    public static CreditCardTransDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CREDIT_CARD_TRANS_ID,ORDER_CREDIT_CARD_ID,INVOICE_CUST_ID,TRANSACTION_TYPE_CD,AMOUNT,TRANSACTION_REFERENCE,AUTH_CODE,PAYMETRIC_RESPONSE_CODE,PAYMETRIC_AUTH_DATE,PAYMETRIC_AUTH_TIME,PAYMETRIC_TRANSACTION_ID,PAYMETRIC_AUTH_MESSAGE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_CREDIT_CARD_TRANS");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CREDIT_CARD_TRANS.CREDIT_CARD_TRANS_ID,CLW_CREDIT_CARD_TRANS.ORDER_CREDIT_CARD_ID,CLW_CREDIT_CARD_TRANS.INVOICE_CUST_ID,CLW_CREDIT_CARD_TRANS.TRANSACTION_TYPE_CD,CLW_CREDIT_CARD_TRANS.AMOUNT,CLW_CREDIT_CARD_TRANS.TRANSACTION_REFERENCE,CLW_CREDIT_CARD_TRANS.AUTH_CODE,CLW_CREDIT_CARD_TRANS.PAYMETRIC_RESPONSE_CODE,CLW_CREDIT_CARD_TRANS.PAYMETRIC_AUTH_DATE,CLW_CREDIT_CARD_TRANS.PAYMETRIC_AUTH_TIME,CLW_CREDIT_CARD_TRANS.PAYMETRIC_TRANSACTION_ID,CLW_CREDIT_CARD_TRANS.PAYMETRIC_AUTH_MESSAGE,CLW_CREDIT_CARD_TRANS.ADD_BY,CLW_CREDIT_CARD_TRANS.ADD_DATE,CLW_CREDIT_CARD_TRANS.MOD_BY,CLW_CREDIT_CARD_TRANS.MOD_DATE FROM CLW_CREDIT_CARD_TRANS");
                where = pCriteria.getSqlClause("CLW_CREDIT_CARD_TRANS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CREDIT_CARD_TRANS.equals(otherTable)){
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
        CreditCardTransDataVector v = new CreditCardTransDataVector();
        while (rs.next()) {
            CreditCardTransData x = CreditCardTransData.createValue();
            
            x.setCreditCardTransId(rs.getInt(1));
            x.setOrderCreditCardId(rs.getInt(2));
            x.setInvoiceCustId(rs.getInt(3));
            x.setTransactionTypeCd(rs.getString(4));
            x.setAmount(rs.getBigDecimal(5));
            x.setTransactionReference(rs.getString(6));
            x.setAuthCode(rs.getString(7));
            x.setPaymetricResponseCode(rs.getString(8));
            x.setPaymetricAuthDate(rs.getDate(9));
            x.setPaymetricAuthTime(rs.getTimestamp(10));
            x.setPaymetricTransactionId(rs.getString(11));
            x.setPaymetricAuthMessage(rs.getString(12));
            x.setAddBy(rs.getString(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CreditCardTransDataVector object that consists
     * of CreditCardTransData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CreditCardTransData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CreditCardTransDataVector()
     * @throws            SQLException
     */
    public static CreditCardTransDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CreditCardTransDataVector v = new CreditCardTransDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CREDIT_CARD_TRANS_ID,ORDER_CREDIT_CARD_ID,INVOICE_CUST_ID,TRANSACTION_TYPE_CD,AMOUNT,TRANSACTION_REFERENCE,AUTH_CODE,PAYMETRIC_RESPONSE_CODE,PAYMETRIC_AUTH_DATE,PAYMETRIC_AUTH_TIME,PAYMETRIC_TRANSACTION_ID,PAYMETRIC_AUTH_MESSAGE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_CREDIT_CARD_TRANS WHERE CREDIT_CARD_TRANS_ID IN (");

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
            CreditCardTransData x=null;
            while (rs.next()) {
                // build the object
                x=CreditCardTransData.createValue();
                
                x.setCreditCardTransId(rs.getInt(1));
                x.setOrderCreditCardId(rs.getInt(2));
                x.setInvoiceCustId(rs.getInt(3));
                x.setTransactionTypeCd(rs.getString(4));
                x.setAmount(rs.getBigDecimal(5));
                x.setTransactionReference(rs.getString(6));
                x.setAuthCode(rs.getString(7));
                x.setPaymetricResponseCode(rs.getString(8));
                x.setPaymetricAuthDate(rs.getDate(9));
                x.setPaymetricAuthTime(rs.getTimestamp(10));
                x.setPaymetricTransactionId(rs.getString(11));
                x.setPaymetricAuthMessage(rs.getString(12));
                x.setAddBy(rs.getString(13));
                x.setAddDate(rs.getTimestamp(14));
                x.setModBy(rs.getString(15));
                x.setModDate(rs.getTimestamp(16));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CreditCardTransDataVector object of all
     * CreditCardTransData objects in the database.
     * @param pCon An open database connection.
     * @return new CreditCardTransDataVector()
     * @throws            SQLException
     */
    public static CreditCardTransDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CREDIT_CARD_TRANS_ID,ORDER_CREDIT_CARD_ID,INVOICE_CUST_ID,TRANSACTION_TYPE_CD,AMOUNT,TRANSACTION_REFERENCE,AUTH_CODE,PAYMETRIC_RESPONSE_CODE,PAYMETRIC_AUTH_DATE,PAYMETRIC_AUTH_TIME,PAYMETRIC_TRANSACTION_ID,PAYMETRIC_AUTH_MESSAGE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_CREDIT_CARD_TRANS";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CreditCardTransDataVector v = new CreditCardTransDataVector();
        CreditCardTransData x = null;
        while (rs.next()) {
            // build the object
            x = CreditCardTransData.createValue();
            
            x.setCreditCardTransId(rs.getInt(1));
            x.setOrderCreditCardId(rs.getInt(2));
            x.setInvoiceCustId(rs.getInt(3));
            x.setTransactionTypeCd(rs.getString(4));
            x.setAmount(rs.getBigDecimal(5));
            x.setTransactionReference(rs.getString(6));
            x.setAuthCode(rs.getString(7));
            x.setPaymetricResponseCode(rs.getString(8));
            x.setPaymetricAuthDate(rs.getDate(9));
            x.setPaymetricAuthTime(rs.getTimestamp(10));
            x.setPaymetricTransactionId(rs.getString(11));
            x.setPaymetricAuthMessage(rs.getString(12));
            x.setAddBy(rs.getString(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setModBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CreditCardTransData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CREDIT_CARD_TRANS_ID FROM CLW_CREDIT_CARD_TRANS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CREDIT_CARD_TRANS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CREDIT_CARD_TRANS");
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
     * Inserts a CreditCardTransData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CreditCardTransData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CreditCardTransData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CreditCardTransData insert(Connection pCon, CreditCardTransData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CREDIT_CARD_TRANS_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CREDIT_CARD_TRANS_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCreditCardTransId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CREDIT_CARD_TRANS (CREDIT_CARD_TRANS_ID,ORDER_CREDIT_CARD_ID,INVOICE_CUST_ID,TRANSACTION_TYPE_CD,AMOUNT,TRANSACTION_REFERENCE,AUTH_CODE,PAYMETRIC_RESPONSE_CODE,PAYMETRIC_AUTH_DATE,PAYMETRIC_AUTH_TIME,PAYMETRIC_TRANSACTION_ID,PAYMETRIC_AUTH_MESSAGE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCreditCardTransId());
        pstmt.setInt(2,pData.getOrderCreditCardId());
        pstmt.setInt(3,pData.getInvoiceCustId());
        pstmt.setString(4,pData.getTransactionTypeCd());
        pstmt.setBigDecimal(5,pData.getAmount());
        pstmt.setString(6,pData.getTransactionReference());
        pstmt.setString(7,pData.getAuthCode());
        pstmt.setString(8,pData.getPaymetricResponseCode());
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getPaymetricAuthDate()));
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getPaymetricAuthTime()));
        pstmt.setString(11,pData.getPaymetricTransactionId());
        pstmt.setString(12,pData.getPaymetricAuthMessage());
        pstmt.setString(13,pData.getAddBy());
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15,pData.getModBy());
        pstmt.setTimestamp(16,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CREDIT_CARD_TRANS_ID="+pData.getCreditCardTransId());
            log.debug("SQL:   ORDER_CREDIT_CARD_ID="+pData.getOrderCreditCardId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   TRANSACTION_TYPE_CD="+pData.getTransactionTypeCd());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   TRANSACTION_REFERENCE="+pData.getTransactionReference());
            log.debug("SQL:   AUTH_CODE="+pData.getAuthCode());
            log.debug("SQL:   PAYMETRIC_RESPONSE_CODE="+pData.getPaymetricResponseCode());
            log.debug("SQL:   PAYMETRIC_AUTH_DATE="+pData.getPaymetricAuthDate());
            log.debug("SQL:   PAYMETRIC_AUTH_TIME="+pData.getPaymetricAuthTime());
            log.debug("SQL:   PAYMETRIC_TRANSACTION_ID="+pData.getPaymetricTransactionId());
            log.debug("SQL:   PAYMETRIC_AUTH_MESSAGE="+pData.getPaymetricAuthMessage());
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
        pData.setCreditCardTransId(0);
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
     * Updates a CreditCardTransData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CreditCardTransData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CreditCardTransData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CREDIT_CARD_TRANS SET ORDER_CREDIT_CARD_ID = ?,INVOICE_CUST_ID = ?,TRANSACTION_TYPE_CD = ?,AMOUNT = ?,TRANSACTION_REFERENCE = ?,AUTH_CODE = ?,PAYMETRIC_RESPONSE_CODE = ?,PAYMETRIC_AUTH_DATE = ?,PAYMETRIC_AUTH_TIME = ?,PAYMETRIC_TRANSACTION_ID = ?,PAYMETRIC_AUTH_MESSAGE = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE CREDIT_CARD_TRANS_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderCreditCardId());
        pstmt.setInt(i++,pData.getInvoiceCustId());
        pstmt.setString(i++,pData.getTransactionTypeCd());
        pstmt.setBigDecimal(i++,pData.getAmount());
        pstmt.setString(i++,pData.getTransactionReference());
        pstmt.setString(i++,pData.getAuthCode());
        pstmt.setString(i++,pData.getPaymetricResponseCode());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getPaymetricAuthDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getPaymetricAuthTime()));
        pstmt.setString(i++,pData.getPaymetricTransactionId());
        pstmt.setString(i++,pData.getPaymetricAuthMessage());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getCreditCardTransId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_CREDIT_CARD_ID="+pData.getOrderCreditCardId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   TRANSACTION_TYPE_CD="+pData.getTransactionTypeCd());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   TRANSACTION_REFERENCE="+pData.getTransactionReference());
            log.debug("SQL:   AUTH_CODE="+pData.getAuthCode());
            log.debug("SQL:   PAYMETRIC_RESPONSE_CODE="+pData.getPaymetricResponseCode());
            log.debug("SQL:   PAYMETRIC_AUTH_DATE="+pData.getPaymetricAuthDate());
            log.debug("SQL:   PAYMETRIC_AUTH_TIME="+pData.getPaymetricAuthTime());
            log.debug("SQL:   PAYMETRIC_TRANSACTION_ID="+pData.getPaymetricTransactionId());
            log.debug("SQL:   PAYMETRIC_AUTH_MESSAGE="+pData.getPaymetricAuthMessage());
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
     * Deletes a CreditCardTransData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCreditCardTransId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCreditCardTransId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CREDIT_CARD_TRANS WHERE CREDIT_CARD_TRANS_ID = " + pCreditCardTransId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CreditCardTransData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CREDIT_CARD_TRANS");
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
     * Inserts a CreditCardTransData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CreditCardTransData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CreditCardTransData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CREDIT_CARD_TRANS (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CREDIT_CARD_TRANS_ID,ORDER_CREDIT_CARD_ID,INVOICE_CUST_ID,TRANSACTION_TYPE_CD,AMOUNT,TRANSACTION_REFERENCE,AUTH_CODE,PAYMETRIC_RESPONSE_CODE,PAYMETRIC_AUTH_DATE,PAYMETRIC_AUTH_TIME,PAYMETRIC_TRANSACTION_ID,PAYMETRIC_AUTH_MESSAGE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCreditCardTransId());
        pstmt.setInt(2+4,pData.getOrderCreditCardId());
        pstmt.setInt(3+4,pData.getInvoiceCustId());
        pstmt.setString(4+4,pData.getTransactionTypeCd());
        pstmt.setBigDecimal(5+4,pData.getAmount());
        pstmt.setString(6+4,pData.getTransactionReference());
        pstmt.setString(7+4,pData.getAuthCode());
        pstmt.setString(8+4,pData.getPaymetricResponseCode());
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getPaymetricAuthDate()));
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getPaymetricAuthTime()));
        pstmt.setString(11+4,pData.getPaymetricTransactionId());
        pstmt.setString(12+4,pData.getPaymetricAuthMessage());
        pstmt.setString(13+4,pData.getAddBy());
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15+4,pData.getModBy());
        pstmt.setTimestamp(16+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CreditCardTransData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CreditCardTransData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CreditCardTransData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CreditCardTransData insert(Connection pCon, CreditCardTransData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CreditCardTransData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CreditCardTransData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CreditCardTransData pData, boolean pLogFl)
        throws SQLException {
        CreditCardTransData oldData = null;
        if(pLogFl) {
          int id = pData.getCreditCardTransId();
          try {
          oldData = CreditCardTransDataAccess.select(pCon,id);
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
     * Deletes a CreditCardTransData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCreditCardTransId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCreditCardTransId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CREDIT_CARD_TRANS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CREDIT_CARD_TRANS d WHERE CREDIT_CARD_TRANS_ID = " + pCreditCardTransId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCreditCardTransId);
        return n;
     }

    /**
     * Deletes CreditCardTransData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CREDIT_CARD_TRANS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CREDIT_CARD_TRANS d ");
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

