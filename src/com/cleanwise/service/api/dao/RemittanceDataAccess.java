
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        RemittanceDataAccess
 * Description:  This class is used to build access methods to the CLW_REMITTANCE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.RemittanceData;
import com.cleanwise.service.api.value.RemittanceDataVector;
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
 * <code>RemittanceDataAccess</code>
 */
public class RemittanceDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(RemittanceDataAccess.class.getName());

    /** <code>CLW_REMITTANCE</code> table name */
	/* Primary key: REMITTANCE_ID */
	
    public static final String CLW_REMITTANCE = "CLW_REMITTANCE";
    
    /** <code>REMITTANCE_ID</code> REMITTANCE_ID column of table CLW_REMITTANCE */
    public static final String REMITTANCE_ID = "REMITTANCE_ID";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_REMITTANCE */
    public static final String STORE_ID = "STORE_ID";
    /** <code>HANDLING_CODE</code> HANDLING_CODE column of table CLW_REMITTANCE */
    public static final String HANDLING_CODE = "HANDLING_CODE";
    /** <code>TOTAL_PAYMENT_AMOUNT</code> TOTAL_PAYMENT_AMOUNT column of table CLW_REMITTANCE */
    public static final String TOTAL_PAYMENT_AMOUNT = "TOTAL_PAYMENT_AMOUNT";
    /** <code>CREDIT_DEBIT</code> CREDIT_DEBIT column of table CLW_REMITTANCE */
    public static final String CREDIT_DEBIT = "CREDIT_DEBIT";
    /** <code>PAYMENT_TYPE</code> PAYMENT_TYPE column of table CLW_REMITTANCE */
    public static final String PAYMENT_TYPE = "PAYMENT_TYPE";
    /** <code>PAYER_BANK</code> PAYER_BANK column of table CLW_REMITTANCE */
    public static final String PAYER_BANK = "PAYER_BANK";
    /** <code>PAYER_BANK_ACCOUNT</code> PAYER_BANK_ACCOUNT column of table CLW_REMITTANCE */
    public static final String PAYER_BANK_ACCOUNT = "PAYER_BANK_ACCOUNT";
    /** <code>PAYER_ID</code> PAYER_ID column of table CLW_REMITTANCE */
    public static final String PAYER_ID = "PAYER_ID";
    /** <code>PAYEE_BANK</code> PAYEE_BANK column of table CLW_REMITTANCE */
    public static final String PAYEE_BANK = "PAYEE_BANK";
    /** <code>PAYEE_BANK_ACCOUNT</code> PAYEE_BANK_ACCOUNT column of table CLW_REMITTANCE */
    public static final String PAYEE_BANK_ACCOUNT = "PAYEE_BANK_ACCOUNT";
    /** <code>PAYMENT_POST_DATE</code> PAYMENT_POST_DATE column of table CLW_REMITTANCE */
    public static final String PAYMENT_POST_DATE = "PAYMENT_POST_DATE";
    /** <code>PAYMENT_REFERENCE_NUMBER_TYPE</code> PAYMENT_REFERENCE_NUMBER_TYPE column of table CLW_REMITTANCE */
    public static final String PAYMENT_REFERENCE_NUMBER_TYPE = "PAYMENT_REFERENCE_NUMBER_TYPE";
    /** <code>PAYMENT_REFERENCE_NUMBER</code> PAYMENT_REFERENCE_NUMBER column of table CLW_REMITTANCE */
    public static final String PAYMENT_REFERENCE_NUMBER = "PAYMENT_REFERENCE_NUMBER";
    /** <code>CHECK_DATE</code> CHECK_DATE column of table CLW_REMITTANCE */
    public static final String CHECK_DATE = "CHECK_DATE";
    /** <code>PAYEE_ERP_ACCOUNT</code> PAYEE_ERP_ACCOUNT column of table CLW_REMITTANCE */
    public static final String PAYEE_ERP_ACCOUNT = "PAYEE_ERP_ACCOUNT";
    /** <code>TRANSACTION_DATE</code> TRANSACTION_DATE column of table CLW_REMITTANCE */
    public static final String TRANSACTION_DATE = "TRANSACTION_DATE";
    /** <code>TRANSACTION_REFERENCE</code> TRANSACTION_REFERENCE column of table CLW_REMITTANCE */
    public static final String TRANSACTION_REFERENCE = "TRANSACTION_REFERENCE";
    /** <code>REMITTANCE_STATUS_CD</code> REMITTANCE_STATUS_CD column of table CLW_REMITTANCE */
    public static final String REMITTANCE_STATUS_CD = "REMITTANCE_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_REMITTANCE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_REMITTANCE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_REMITTANCE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_REMITTANCE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>TRANSACTION_CD</code> TRANSACTION_CD column of table CLW_REMITTANCE */
    public static final String TRANSACTION_CD = "TRANSACTION_CD";

    /**
     * Constructor.
     */
    public RemittanceDataAccess()
    {
    }

    /**
     * Gets a RemittanceData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pRemittanceId The key requested.
     * @return new RemittanceData()
     * @throws            SQLException
     */
    public static RemittanceData select(Connection pCon, int pRemittanceId)
        throws SQLException, DataNotFoundException {
        RemittanceData x=null;
        String sql="SELECT REMITTANCE_ID,STORE_ID,HANDLING_CODE,TOTAL_PAYMENT_AMOUNT,CREDIT_DEBIT,PAYMENT_TYPE,PAYER_BANK,PAYER_BANK_ACCOUNT,PAYER_ID,PAYEE_BANK,PAYEE_BANK_ACCOUNT,PAYMENT_POST_DATE,PAYMENT_REFERENCE_NUMBER_TYPE,PAYMENT_REFERENCE_NUMBER,CHECK_DATE,PAYEE_ERP_ACCOUNT,TRANSACTION_DATE,TRANSACTION_REFERENCE,REMITTANCE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TRANSACTION_CD FROM CLW_REMITTANCE WHERE REMITTANCE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pRemittanceId=" + pRemittanceId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pRemittanceId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=RemittanceData.createValue();
            
            x.setRemittanceId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setHandlingCode(rs.getString(3));
            x.setTotalPaymentAmount(rs.getBigDecimal(4));
            x.setCreditDebit(rs.getString(5));
            x.setPaymentType(rs.getString(6));
            x.setPayerBank(rs.getString(7));
            x.setPayerBankAccount(rs.getString(8));
            x.setPayerId(rs.getString(9));
            x.setPayeeBank(rs.getString(10));
            x.setPayeeBankAccount(rs.getString(11));
            x.setPaymentPostDate(rs.getDate(12));
            x.setPaymentReferenceNumberType(rs.getString(13));
            x.setPaymentReferenceNumber(rs.getString(14));
            x.setCheckDate(rs.getDate(15));
            x.setPayeeErpAccount(rs.getString(16));
            x.setTransactionDate(rs.getDate(17));
            x.setTransactionReference(rs.getString(18));
            x.setRemittanceStatusCd(rs.getString(19));
            x.setAddDate(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            x.setTransactionCd(rs.getString(24));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("REMITTANCE_ID :" + pRemittanceId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a RemittanceDataVector object that consists
     * of RemittanceData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new RemittanceDataVector()
     * @throws            SQLException
     */
    public static RemittanceDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a RemittanceData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_REMITTANCE.REMITTANCE_ID,CLW_REMITTANCE.STORE_ID,CLW_REMITTANCE.HANDLING_CODE,CLW_REMITTANCE.TOTAL_PAYMENT_AMOUNT,CLW_REMITTANCE.CREDIT_DEBIT,CLW_REMITTANCE.PAYMENT_TYPE,CLW_REMITTANCE.PAYER_BANK,CLW_REMITTANCE.PAYER_BANK_ACCOUNT,CLW_REMITTANCE.PAYER_ID,CLW_REMITTANCE.PAYEE_BANK,CLW_REMITTANCE.PAYEE_BANK_ACCOUNT,CLW_REMITTANCE.PAYMENT_POST_DATE,CLW_REMITTANCE.PAYMENT_REFERENCE_NUMBER_TYPE,CLW_REMITTANCE.PAYMENT_REFERENCE_NUMBER,CLW_REMITTANCE.CHECK_DATE,CLW_REMITTANCE.PAYEE_ERP_ACCOUNT,CLW_REMITTANCE.TRANSACTION_DATE,CLW_REMITTANCE.TRANSACTION_REFERENCE,CLW_REMITTANCE.REMITTANCE_STATUS_CD,CLW_REMITTANCE.ADD_DATE,CLW_REMITTANCE.ADD_BY,CLW_REMITTANCE.MOD_DATE,CLW_REMITTANCE.MOD_BY,CLW_REMITTANCE.TRANSACTION_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated RemittanceData Object.
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
    *@returns a populated RemittanceData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         RemittanceData x = RemittanceData.createValue();
         
         x.setRemittanceId(rs.getInt(1+offset));
         x.setStoreId(rs.getInt(2+offset));
         x.setHandlingCode(rs.getString(3+offset));
         x.setTotalPaymentAmount(rs.getBigDecimal(4+offset));
         x.setCreditDebit(rs.getString(5+offset));
         x.setPaymentType(rs.getString(6+offset));
         x.setPayerBank(rs.getString(7+offset));
         x.setPayerBankAccount(rs.getString(8+offset));
         x.setPayerId(rs.getString(9+offset));
         x.setPayeeBank(rs.getString(10+offset));
         x.setPayeeBankAccount(rs.getString(11+offset));
         x.setPaymentPostDate(rs.getDate(12+offset));
         x.setPaymentReferenceNumberType(rs.getString(13+offset));
         x.setPaymentReferenceNumber(rs.getString(14+offset));
         x.setCheckDate(rs.getDate(15+offset));
         x.setPayeeErpAccount(rs.getString(16+offset));
         x.setTransactionDate(rs.getDate(17+offset));
         x.setTransactionReference(rs.getString(18+offset));
         x.setRemittanceStatusCd(rs.getString(19+offset));
         x.setAddDate(rs.getTimestamp(20+offset));
         x.setAddBy(rs.getString(21+offset));
         x.setModDate(rs.getTimestamp(22+offset));
         x.setModBy(rs.getString(23+offset));
         x.setTransactionCd(rs.getString(24+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the RemittanceData Object represents.
    */
    public int getColumnCount(){
        return 24;
    }

    /**
     * Gets a RemittanceDataVector object that consists
     * of RemittanceData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new RemittanceDataVector()
     * @throws            SQLException
     */
    public static RemittanceDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT REMITTANCE_ID,STORE_ID,HANDLING_CODE,TOTAL_PAYMENT_AMOUNT,CREDIT_DEBIT,PAYMENT_TYPE,PAYER_BANK,PAYER_BANK_ACCOUNT,PAYER_ID,PAYEE_BANK,PAYEE_BANK_ACCOUNT,PAYMENT_POST_DATE,PAYMENT_REFERENCE_NUMBER_TYPE,PAYMENT_REFERENCE_NUMBER,CHECK_DATE,PAYEE_ERP_ACCOUNT,TRANSACTION_DATE,TRANSACTION_REFERENCE,REMITTANCE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TRANSACTION_CD FROM CLW_REMITTANCE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_REMITTANCE.REMITTANCE_ID,CLW_REMITTANCE.STORE_ID,CLW_REMITTANCE.HANDLING_CODE,CLW_REMITTANCE.TOTAL_PAYMENT_AMOUNT,CLW_REMITTANCE.CREDIT_DEBIT,CLW_REMITTANCE.PAYMENT_TYPE,CLW_REMITTANCE.PAYER_BANK,CLW_REMITTANCE.PAYER_BANK_ACCOUNT,CLW_REMITTANCE.PAYER_ID,CLW_REMITTANCE.PAYEE_BANK,CLW_REMITTANCE.PAYEE_BANK_ACCOUNT,CLW_REMITTANCE.PAYMENT_POST_DATE,CLW_REMITTANCE.PAYMENT_REFERENCE_NUMBER_TYPE,CLW_REMITTANCE.PAYMENT_REFERENCE_NUMBER,CLW_REMITTANCE.CHECK_DATE,CLW_REMITTANCE.PAYEE_ERP_ACCOUNT,CLW_REMITTANCE.TRANSACTION_DATE,CLW_REMITTANCE.TRANSACTION_REFERENCE,CLW_REMITTANCE.REMITTANCE_STATUS_CD,CLW_REMITTANCE.ADD_DATE,CLW_REMITTANCE.ADD_BY,CLW_REMITTANCE.MOD_DATE,CLW_REMITTANCE.MOD_BY,CLW_REMITTANCE.TRANSACTION_CD FROM CLW_REMITTANCE");
                where = pCriteria.getSqlClause("CLW_REMITTANCE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_REMITTANCE.equals(otherTable)){
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
        RemittanceDataVector v = new RemittanceDataVector();
        while (rs.next()) {
            RemittanceData x = RemittanceData.createValue();
            
            x.setRemittanceId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setHandlingCode(rs.getString(3));
            x.setTotalPaymentAmount(rs.getBigDecimal(4));
            x.setCreditDebit(rs.getString(5));
            x.setPaymentType(rs.getString(6));
            x.setPayerBank(rs.getString(7));
            x.setPayerBankAccount(rs.getString(8));
            x.setPayerId(rs.getString(9));
            x.setPayeeBank(rs.getString(10));
            x.setPayeeBankAccount(rs.getString(11));
            x.setPaymentPostDate(rs.getDate(12));
            x.setPaymentReferenceNumberType(rs.getString(13));
            x.setPaymentReferenceNumber(rs.getString(14));
            x.setCheckDate(rs.getDate(15));
            x.setPayeeErpAccount(rs.getString(16));
            x.setTransactionDate(rs.getDate(17));
            x.setTransactionReference(rs.getString(18));
            x.setRemittanceStatusCd(rs.getString(19));
            x.setAddDate(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            x.setTransactionCd(rs.getString(24));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a RemittanceDataVector object that consists
     * of RemittanceData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for RemittanceData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new RemittanceDataVector()
     * @throws            SQLException
     */
    public static RemittanceDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        RemittanceDataVector v = new RemittanceDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REMITTANCE_ID,STORE_ID,HANDLING_CODE,TOTAL_PAYMENT_AMOUNT,CREDIT_DEBIT,PAYMENT_TYPE,PAYER_BANK,PAYER_BANK_ACCOUNT,PAYER_ID,PAYEE_BANK,PAYEE_BANK_ACCOUNT,PAYMENT_POST_DATE,PAYMENT_REFERENCE_NUMBER_TYPE,PAYMENT_REFERENCE_NUMBER,CHECK_DATE,PAYEE_ERP_ACCOUNT,TRANSACTION_DATE,TRANSACTION_REFERENCE,REMITTANCE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TRANSACTION_CD FROM CLW_REMITTANCE WHERE REMITTANCE_ID IN (");

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
            RemittanceData x=null;
            while (rs.next()) {
                // build the object
                x=RemittanceData.createValue();
                
                x.setRemittanceId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setHandlingCode(rs.getString(3));
                x.setTotalPaymentAmount(rs.getBigDecimal(4));
                x.setCreditDebit(rs.getString(5));
                x.setPaymentType(rs.getString(6));
                x.setPayerBank(rs.getString(7));
                x.setPayerBankAccount(rs.getString(8));
                x.setPayerId(rs.getString(9));
                x.setPayeeBank(rs.getString(10));
                x.setPayeeBankAccount(rs.getString(11));
                x.setPaymentPostDate(rs.getDate(12));
                x.setPaymentReferenceNumberType(rs.getString(13));
                x.setPaymentReferenceNumber(rs.getString(14));
                x.setCheckDate(rs.getDate(15));
                x.setPayeeErpAccount(rs.getString(16));
                x.setTransactionDate(rs.getDate(17));
                x.setTransactionReference(rs.getString(18));
                x.setRemittanceStatusCd(rs.getString(19));
                x.setAddDate(rs.getTimestamp(20));
                x.setAddBy(rs.getString(21));
                x.setModDate(rs.getTimestamp(22));
                x.setModBy(rs.getString(23));
                x.setTransactionCd(rs.getString(24));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a RemittanceDataVector object of all
     * RemittanceData objects in the database.
     * @param pCon An open database connection.
     * @return new RemittanceDataVector()
     * @throws            SQLException
     */
    public static RemittanceDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REMITTANCE_ID,STORE_ID,HANDLING_CODE,TOTAL_PAYMENT_AMOUNT,CREDIT_DEBIT,PAYMENT_TYPE,PAYER_BANK,PAYER_BANK_ACCOUNT,PAYER_ID,PAYEE_BANK,PAYEE_BANK_ACCOUNT,PAYMENT_POST_DATE,PAYMENT_REFERENCE_NUMBER_TYPE,PAYMENT_REFERENCE_NUMBER,CHECK_DATE,PAYEE_ERP_ACCOUNT,TRANSACTION_DATE,TRANSACTION_REFERENCE,REMITTANCE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TRANSACTION_CD FROM CLW_REMITTANCE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        RemittanceDataVector v = new RemittanceDataVector();
        RemittanceData x = null;
        while (rs.next()) {
            // build the object
            x = RemittanceData.createValue();
            
            x.setRemittanceId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setHandlingCode(rs.getString(3));
            x.setTotalPaymentAmount(rs.getBigDecimal(4));
            x.setCreditDebit(rs.getString(5));
            x.setPaymentType(rs.getString(6));
            x.setPayerBank(rs.getString(7));
            x.setPayerBankAccount(rs.getString(8));
            x.setPayerId(rs.getString(9));
            x.setPayeeBank(rs.getString(10));
            x.setPayeeBankAccount(rs.getString(11));
            x.setPaymentPostDate(rs.getDate(12));
            x.setPaymentReferenceNumberType(rs.getString(13));
            x.setPaymentReferenceNumber(rs.getString(14));
            x.setCheckDate(rs.getDate(15));
            x.setPayeeErpAccount(rs.getString(16));
            x.setTransactionDate(rs.getDate(17));
            x.setTransactionReference(rs.getString(18));
            x.setRemittanceStatusCd(rs.getString(19));
            x.setAddDate(rs.getTimestamp(20));
            x.setAddBy(rs.getString(21));
            x.setModDate(rs.getTimestamp(22));
            x.setModBy(rs.getString(23));
            x.setTransactionCd(rs.getString(24));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * RemittanceData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REMITTANCE_ID FROM CLW_REMITTANCE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REMITTANCE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REMITTANCE");
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
     * Inserts a RemittanceData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new RemittanceData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RemittanceData insert(Connection pCon, RemittanceData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_REMITTANCE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_REMITTANCE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setRemittanceId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_REMITTANCE (REMITTANCE_ID,STORE_ID,HANDLING_CODE,TOTAL_PAYMENT_AMOUNT,CREDIT_DEBIT,PAYMENT_TYPE,PAYER_BANK,PAYER_BANK_ACCOUNT,PAYER_ID,PAYEE_BANK,PAYEE_BANK_ACCOUNT,PAYMENT_POST_DATE,PAYMENT_REFERENCE_NUMBER_TYPE,PAYMENT_REFERENCE_NUMBER,CHECK_DATE,PAYEE_ERP_ACCOUNT,TRANSACTION_DATE,TRANSACTION_REFERENCE,REMITTANCE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TRANSACTION_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getRemittanceId());
        pstmt.setInt(2,pData.getStoreId());
        pstmt.setString(3,pData.getHandlingCode());
        pstmt.setBigDecimal(4,pData.getTotalPaymentAmount());
        pstmt.setString(5,pData.getCreditDebit());
        pstmt.setString(6,pData.getPaymentType());
        pstmt.setString(7,pData.getPayerBank());
        pstmt.setString(8,pData.getPayerBankAccount());
        pstmt.setString(9,pData.getPayerId());
        pstmt.setString(10,pData.getPayeeBank());
        pstmt.setString(11,pData.getPayeeBankAccount());
        pstmt.setDate(12,DBAccess.toSQLDate(pData.getPaymentPostDate()));
        pstmt.setString(13,pData.getPaymentReferenceNumberType());
        pstmt.setString(14,pData.getPaymentReferenceNumber());
        pstmt.setDate(15,DBAccess.toSQLDate(pData.getCheckDate()));
        pstmt.setString(16,pData.getPayeeErpAccount());
        pstmt.setDate(17,DBAccess.toSQLDate(pData.getTransactionDate()));
        pstmt.setString(18,pData.getTransactionReference());
        pstmt.setString(19,pData.getRemittanceStatusCd());
        pstmt.setTimestamp(20,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(21,pData.getAddBy());
        pstmt.setTimestamp(22,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(23,pData.getModBy());
        pstmt.setString(24,pData.getTransactionCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REMITTANCE_ID="+pData.getRemittanceId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   HANDLING_CODE="+pData.getHandlingCode());
            log.debug("SQL:   TOTAL_PAYMENT_AMOUNT="+pData.getTotalPaymentAmount());
            log.debug("SQL:   CREDIT_DEBIT="+pData.getCreditDebit());
            log.debug("SQL:   PAYMENT_TYPE="+pData.getPaymentType());
            log.debug("SQL:   PAYER_BANK="+pData.getPayerBank());
            log.debug("SQL:   PAYER_BANK_ACCOUNT="+pData.getPayerBankAccount());
            log.debug("SQL:   PAYER_ID="+pData.getPayerId());
            log.debug("SQL:   PAYEE_BANK="+pData.getPayeeBank());
            log.debug("SQL:   PAYEE_BANK_ACCOUNT="+pData.getPayeeBankAccount());
            log.debug("SQL:   PAYMENT_POST_DATE="+pData.getPaymentPostDate());
            log.debug("SQL:   PAYMENT_REFERENCE_NUMBER_TYPE="+pData.getPaymentReferenceNumberType());
            log.debug("SQL:   PAYMENT_REFERENCE_NUMBER="+pData.getPaymentReferenceNumber());
            log.debug("SQL:   CHECK_DATE="+pData.getCheckDate());
            log.debug("SQL:   PAYEE_ERP_ACCOUNT="+pData.getPayeeErpAccount());
            log.debug("SQL:   TRANSACTION_DATE="+pData.getTransactionDate());
            log.debug("SQL:   TRANSACTION_REFERENCE="+pData.getTransactionReference());
            log.debug("SQL:   REMITTANCE_STATUS_CD="+pData.getRemittanceStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   TRANSACTION_CD="+pData.getTransactionCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setRemittanceId(0);
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
     * Updates a RemittanceData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RemittanceData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_REMITTANCE SET STORE_ID = ?,HANDLING_CODE = ?,TOTAL_PAYMENT_AMOUNT = ?,CREDIT_DEBIT = ?,PAYMENT_TYPE = ?,PAYER_BANK = ?,PAYER_BANK_ACCOUNT = ?,PAYER_ID = ?,PAYEE_BANK = ?,PAYEE_BANK_ACCOUNT = ?,PAYMENT_POST_DATE = ?,PAYMENT_REFERENCE_NUMBER_TYPE = ?,PAYMENT_REFERENCE_NUMBER = ?,CHECK_DATE = ?,PAYEE_ERP_ACCOUNT = ?,TRANSACTION_DATE = ?,TRANSACTION_REFERENCE = ?,REMITTANCE_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,TRANSACTION_CD = ? WHERE REMITTANCE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getHandlingCode());
        pstmt.setBigDecimal(i++,pData.getTotalPaymentAmount());
        pstmt.setString(i++,pData.getCreditDebit());
        pstmt.setString(i++,pData.getPaymentType());
        pstmt.setString(i++,pData.getPayerBank());
        pstmt.setString(i++,pData.getPayerBankAccount());
        pstmt.setString(i++,pData.getPayerId());
        pstmt.setString(i++,pData.getPayeeBank());
        pstmt.setString(i++,pData.getPayeeBankAccount());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getPaymentPostDate()));
        pstmt.setString(i++,pData.getPaymentReferenceNumberType());
        pstmt.setString(i++,pData.getPaymentReferenceNumber());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getCheckDate()));
        pstmt.setString(i++,pData.getPayeeErpAccount());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getTransactionDate()));
        pstmt.setString(i++,pData.getTransactionReference());
        pstmt.setString(i++,pData.getRemittanceStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getTransactionCd());
        pstmt.setInt(i++,pData.getRemittanceId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   HANDLING_CODE="+pData.getHandlingCode());
            log.debug("SQL:   TOTAL_PAYMENT_AMOUNT="+pData.getTotalPaymentAmount());
            log.debug("SQL:   CREDIT_DEBIT="+pData.getCreditDebit());
            log.debug("SQL:   PAYMENT_TYPE="+pData.getPaymentType());
            log.debug("SQL:   PAYER_BANK="+pData.getPayerBank());
            log.debug("SQL:   PAYER_BANK_ACCOUNT="+pData.getPayerBankAccount());
            log.debug("SQL:   PAYER_ID="+pData.getPayerId());
            log.debug("SQL:   PAYEE_BANK="+pData.getPayeeBank());
            log.debug("SQL:   PAYEE_BANK_ACCOUNT="+pData.getPayeeBankAccount());
            log.debug("SQL:   PAYMENT_POST_DATE="+pData.getPaymentPostDate());
            log.debug("SQL:   PAYMENT_REFERENCE_NUMBER_TYPE="+pData.getPaymentReferenceNumberType());
            log.debug("SQL:   PAYMENT_REFERENCE_NUMBER="+pData.getPaymentReferenceNumber());
            log.debug("SQL:   CHECK_DATE="+pData.getCheckDate());
            log.debug("SQL:   PAYEE_ERP_ACCOUNT="+pData.getPayeeErpAccount());
            log.debug("SQL:   TRANSACTION_DATE="+pData.getTransactionDate());
            log.debug("SQL:   TRANSACTION_REFERENCE="+pData.getTransactionReference());
            log.debug("SQL:   REMITTANCE_STATUS_CD="+pData.getRemittanceStatusCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   TRANSACTION_CD="+pData.getTransactionCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a RemittanceData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRemittanceId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRemittanceId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_REMITTANCE WHERE REMITTANCE_ID = " + pRemittanceId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes RemittanceData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_REMITTANCE");
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
     * Inserts a RemittanceData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, RemittanceData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_REMITTANCE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "REMITTANCE_ID,STORE_ID,HANDLING_CODE,TOTAL_PAYMENT_AMOUNT,CREDIT_DEBIT,PAYMENT_TYPE,PAYER_BANK,PAYER_BANK_ACCOUNT,PAYER_ID,PAYEE_BANK,PAYEE_BANK_ACCOUNT,PAYMENT_POST_DATE,PAYMENT_REFERENCE_NUMBER_TYPE,PAYMENT_REFERENCE_NUMBER,CHECK_DATE,PAYEE_ERP_ACCOUNT,TRANSACTION_DATE,TRANSACTION_REFERENCE,REMITTANCE_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,TRANSACTION_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getRemittanceId());
        pstmt.setInt(2+4,pData.getStoreId());
        pstmt.setString(3+4,pData.getHandlingCode());
        pstmt.setBigDecimal(4+4,pData.getTotalPaymentAmount());
        pstmt.setString(5+4,pData.getCreditDebit());
        pstmt.setString(6+4,pData.getPaymentType());
        pstmt.setString(7+4,pData.getPayerBank());
        pstmt.setString(8+4,pData.getPayerBankAccount());
        pstmt.setString(9+4,pData.getPayerId());
        pstmt.setString(10+4,pData.getPayeeBank());
        pstmt.setString(11+4,pData.getPayeeBankAccount());
        pstmt.setDate(12+4,DBAccess.toSQLDate(pData.getPaymentPostDate()));
        pstmt.setString(13+4,pData.getPaymentReferenceNumberType());
        pstmt.setString(14+4,pData.getPaymentReferenceNumber());
        pstmt.setDate(15+4,DBAccess.toSQLDate(pData.getCheckDate()));
        pstmt.setString(16+4,pData.getPayeeErpAccount());
        pstmt.setDate(17+4,DBAccess.toSQLDate(pData.getTransactionDate()));
        pstmt.setString(18+4,pData.getTransactionReference());
        pstmt.setString(19+4,pData.getRemittanceStatusCd());
        pstmt.setTimestamp(20+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(21+4,pData.getAddBy());
        pstmt.setTimestamp(22+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(23+4,pData.getModBy());
        pstmt.setString(24+4,pData.getTransactionCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a RemittanceData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new RemittanceData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RemittanceData insert(Connection pCon, RemittanceData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a RemittanceData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RemittanceData pData, boolean pLogFl)
        throws SQLException {
        RemittanceData oldData = null;
        if(pLogFl) {
          int id = pData.getRemittanceId();
          try {
          oldData = RemittanceDataAccess.select(pCon,id);
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
     * Deletes a RemittanceData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRemittanceId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRemittanceId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_REMITTANCE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REMITTANCE d WHERE REMITTANCE_ID = " + pRemittanceId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pRemittanceId);
        return n;
     }

    /**
     * Deletes RemittanceData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_REMITTANCE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REMITTANCE d ");
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

