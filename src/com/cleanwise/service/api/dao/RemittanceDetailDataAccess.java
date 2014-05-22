
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        RemittanceDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_REMITTANCE_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.RemittanceDetailData;
import com.cleanwise.service.api.value.RemittanceDetailDataVector;
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
 * <code>RemittanceDetailDataAccess</code>
 */
public class RemittanceDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(RemittanceDetailDataAccess.class.getName());

    /** <code>CLW_REMITTANCE_DETAIL</code> table name */
	/* Primary key: REMITTANCE_DETAIL_ID */
	
    public static final String CLW_REMITTANCE_DETAIL = "CLW_REMITTANCE_DETAIL";
    
    /** <code>REMITTANCE_DETAIL_ID</code> REMITTANCE_DETAIL_ID column of table CLW_REMITTANCE_DETAIL */
    public static final String REMITTANCE_DETAIL_ID = "REMITTANCE_DETAIL_ID";
    /** <code>SITE_REFERENCE</code> SITE_REFERENCE column of table CLW_REMITTANCE_DETAIL */
    public static final String SITE_REFERENCE = "SITE_REFERENCE";
    /** <code>INVOICE_NUMBER</code> INVOICE_NUMBER column of table CLW_REMITTANCE_DETAIL */
    public static final String INVOICE_NUMBER = "INVOICE_NUMBER";
    /** <code>INVOICE_TYPE</code> INVOICE_TYPE column of table CLW_REMITTANCE_DETAIL */
    public static final String INVOICE_TYPE = "INVOICE_TYPE";
    /** <code>DISCOUNT_AMOUNT</code> DISCOUNT_AMOUNT column of table CLW_REMITTANCE_DETAIL */
    public static final String DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    /** <code>NET_AMOUNT</code> NET_AMOUNT column of table CLW_REMITTANCE_DETAIL */
    public static final String NET_AMOUNT = "NET_AMOUNT";
    /** <code>ORIG_INVOICE_AMOUNT</code> ORIG_INVOICE_AMOUNT column of table CLW_REMITTANCE_DETAIL */
    public static final String ORIG_INVOICE_AMOUNT = "ORIG_INVOICE_AMOUNT";
    /** <code>CUSTOMER_PO_NUMBER</code> CUSTOMER_PO_NUMBER column of table CLW_REMITTANCE_DETAIL */
    public static final String CUSTOMER_PO_NUMBER = "CUSTOMER_PO_NUMBER";
    /** <code>REMITTANCE_ID</code> REMITTANCE_ID column of table CLW_REMITTANCE_DETAIL */
    public static final String REMITTANCE_ID = "REMITTANCE_ID";
    /** <code>CUSTOMER_SUPPLIER_NUMBER</code> CUSTOMER_SUPPLIER_NUMBER column of table CLW_REMITTANCE_DETAIL */
    public static final String CUSTOMER_SUPPLIER_NUMBER = "CUSTOMER_SUPPLIER_NUMBER";
    /** <code>REMITTANCE_DETAIL_STATUS_CD</code> REMITTANCE_DETAIL_STATUS_CD column of table CLW_REMITTANCE_DETAIL */
    public static final String REMITTANCE_DETAIL_STATUS_CD = "REMITTANCE_DETAIL_STATUS_CD";
    /** <code>ERP_REFERENCE_NUMBER</code> ERP_REFERENCE_NUMBER column of table CLW_REMITTANCE_DETAIL */
    public static final String ERP_REFERENCE_NUMBER = "ERP_REFERENCE_NUMBER";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_REMITTANCE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_REMITTANCE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_REMITTANCE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_REMITTANCE_DETAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PAYMENT_TYPE_CD</code> PAYMENT_TYPE_CD column of table CLW_REMITTANCE_DETAIL */
    public static final String PAYMENT_TYPE_CD = "PAYMENT_TYPE_CD";
    /** <code>TRANSACTION_CD</code> TRANSACTION_CD column of table CLW_REMITTANCE_DETAIL */
    public static final String TRANSACTION_CD = "TRANSACTION_CD";
    /** <code>REFERENCE_INVOICE_NUMBER</code> REFERENCE_INVOICE_NUMBER column of table CLW_REMITTANCE_DETAIL */
    public static final String REFERENCE_INVOICE_NUMBER = "REFERENCE_INVOICE_NUMBER";

    /**
     * Constructor.
     */
    public RemittanceDetailDataAccess()
    {
    }

    /**
     * Gets a RemittanceDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pRemittanceDetailId The key requested.
     * @return new RemittanceDetailData()
     * @throws            SQLException
     */
    public static RemittanceDetailData select(Connection pCon, int pRemittanceDetailId)
        throws SQLException, DataNotFoundException {
        RemittanceDetailData x=null;
        String sql="SELECT REMITTANCE_DETAIL_ID,SITE_REFERENCE,INVOICE_NUMBER,INVOICE_TYPE,DISCOUNT_AMOUNT,NET_AMOUNT,ORIG_INVOICE_AMOUNT,CUSTOMER_PO_NUMBER,REMITTANCE_ID,CUSTOMER_SUPPLIER_NUMBER,REMITTANCE_DETAIL_STATUS_CD,ERP_REFERENCE_NUMBER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TYPE_CD,TRANSACTION_CD,REFERENCE_INVOICE_NUMBER FROM CLW_REMITTANCE_DETAIL WHERE REMITTANCE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pRemittanceDetailId=" + pRemittanceDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pRemittanceDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=RemittanceDetailData.createValue();
            
            x.setRemittanceDetailId(rs.getInt(1));
            x.setSiteReference(rs.getString(2));
            x.setInvoiceNumber(rs.getString(3));
            x.setInvoiceType(rs.getString(4));
            x.setDiscountAmount(rs.getBigDecimal(5));
            x.setNetAmount(rs.getBigDecimal(6));
            x.setOrigInvoiceAmount(rs.getBigDecimal(7));
            x.setCustomerPoNumber(rs.getString(8));
            x.setRemittanceId(rs.getInt(9));
            x.setCustomerSupplierNumber(rs.getString(10));
            x.setRemittanceDetailStatusCd(rs.getString(11));
            x.setErpReferenceNumber(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setPaymentTypeCd(rs.getString(17));
            x.setTransactionCd(rs.getString(18));
            x.setReferenceInvoiceNumber(rs.getString(19));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("REMITTANCE_DETAIL_ID :" + pRemittanceDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a RemittanceDetailDataVector object that consists
     * of RemittanceDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new RemittanceDetailDataVector()
     * @throws            SQLException
     */
    public static RemittanceDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a RemittanceDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_REMITTANCE_DETAIL.REMITTANCE_DETAIL_ID,CLW_REMITTANCE_DETAIL.SITE_REFERENCE,CLW_REMITTANCE_DETAIL.INVOICE_NUMBER,CLW_REMITTANCE_DETAIL.INVOICE_TYPE,CLW_REMITTANCE_DETAIL.DISCOUNT_AMOUNT,CLW_REMITTANCE_DETAIL.NET_AMOUNT,CLW_REMITTANCE_DETAIL.ORIG_INVOICE_AMOUNT,CLW_REMITTANCE_DETAIL.CUSTOMER_PO_NUMBER,CLW_REMITTANCE_DETAIL.REMITTANCE_ID,CLW_REMITTANCE_DETAIL.CUSTOMER_SUPPLIER_NUMBER,CLW_REMITTANCE_DETAIL.REMITTANCE_DETAIL_STATUS_CD,CLW_REMITTANCE_DETAIL.ERP_REFERENCE_NUMBER,CLW_REMITTANCE_DETAIL.ADD_DATE,CLW_REMITTANCE_DETAIL.ADD_BY,CLW_REMITTANCE_DETAIL.MOD_DATE,CLW_REMITTANCE_DETAIL.MOD_BY,CLW_REMITTANCE_DETAIL.PAYMENT_TYPE_CD,CLW_REMITTANCE_DETAIL.TRANSACTION_CD,CLW_REMITTANCE_DETAIL.REFERENCE_INVOICE_NUMBER";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated RemittanceDetailData Object.
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
    *@returns a populated RemittanceDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         RemittanceDetailData x = RemittanceDetailData.createValue();
         
         x.setRemittanceDetailId(rs.getInt(1+offset));
         x.setSiteReference(rs.getString(2+offset));
         x.setInvoiceNumber(rs.getString(3+offset));
         x.setInvoiceType(rs.getString(4+offset));
         x.setDiscountAmount(rs.getBigDecimal(5+offset));
         x.setNetAmount(rs.getBigDecimal(6+offset));
         x.setOrigInvoiceAmount(rs.getBigDecimal(7+offset));
         x.setCustomerPoNumber(rs.getString(8+offset));
         x.setRemittanceId(rs.getInt(9+offset));
         x.setCustomerSupplierNumber(rs.getString(10+offset));
         x.setRemittanceDetailStatusCd(rs.getString(11+offset));
         x.setErpReferenceNumber(rs.getString(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setPaymentTypeCd(rs.getString(17+offset));
         x.setTransactionCd(rs.getString(18+offset));
         x.setReferenceInvoiceNumber(rs.getString(19+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the RemittanceDetailData Object represents.
    */
    public int getColumnCount(){
        return 19;
    }

    /**
     * Gets a RemittanceDetailDataVector object that consists
     * of RemittanceDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new RemittanceDetailDataVector()
     * @throws            SQLException
     */
    public static RemittanceDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT REMITTANCE_DETAIL_ID,SITE_REFERENCE,INVOICE_NUMBER,INVOICE_TYPE,DISCOUNT_AMOUNT,NET_AMOUNT,ORIG_INVOICE_AMOUNT,CUSTOMER_PO_NUMBER,REMITTANCE_ID,CUSTOMER_SUPPLIER_NUMBER,REMITTANCE_DETAIL_STATUS_CD,ERP_REFERENCE_NUMBER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TYPE_CD,TRANSACTION_CD,REFERENCE_INVOICE_NUMBER FROM CLW_REMITTANCE_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_REMITTANCE_DETAIL.REMITTANCE_DETAIL_ID,CLW_REMITTANCE_DETAIL.SITE_REFERENCE,CLW_REMITTANCE_DETAIL.INVOICE_NUMBER,CLW_REMITTANCE_DETAIL.INVOICE_TYPE,CLW_REMITTANCE_DETAIL.DISCOUNT_AMOUNT,CLW_REMITTANCE_DETAIL.NET_AMOUNT,CLW_REMITTANCE_DETAIL.ORIG_INVOICE_AMOUNT,CLW_REMITTANCE_DETAIL.CUSTOMER_PO_NUMBER,CLW_REMITTANCE_DETAIL.REMITTANCE_ID,CLW_REMITTANCE_DETAIL.CUSTOMER_SUPPLIER_NUMBER,CLW_REMITTANCE_DETAIL.REMITTANCE_DETAIL_STATUS_CD,CLW_REMITTANCE_DETAIL.ERP_REFERENCE_NUMBER,CLW_REMITTANCE_DETAIL.ADD_DATE,CLW_REMITTANCE_DETAIL.ADD_BY,CLW_REMITTANCE_DETAIL.MOD_DATE,CLW_REMITTANCE_DETAIL.MOD_BY,CLW_REMITTANCE_DETAIL.PAYMENT_TYPE_CD,CLW_REMITTANCE_DETAIL.TRANSACTION_CD,CLW_REMITTANCE_DETAIL.REFERENCE_INVOICE_NUMBER FROM CLW_REMITTANCE_DETAIL");
                where = pCriteria.getSqlClause("CLW_REMITTANCE_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_REMITTANCE_DETAIL.equals(otherTable)){
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
        RemittanceDetailDataVector v = new RemittanceDetailDataVector();
        while (rs.next()) {
            RemittanceDetailData x = RemittanceDetailData.createValue();
            
            x.setRemittanceDetailId(rs.getInt(1));
            x.setSiteReference(rs.getString(2));
            x.setInvoiceNumber(rs.getString(3));
            x.setInvoiceType(rs.getString(4));
            x.setDiscountAmount(rs.getBigDecimal(5));
            x.setNetAmount(rs.getBigDecimal(6));
            x.setOrigInvoiceAmount(rs.getBigDecimal(7));
            x.setCustomerPoNumber(rs.getString(8));
            x.setRemittanceId(rs.getInt(9));
            x.setCustomerSupplierNumber(rs.getString(10));
            x.setRemittanceDetailStatusCd(rs.getString(11));
            x.setErpReferenceNumber(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setPaymentTypeCd(rs.getString(17));
            x.setTransactionCd(rs.getString(18));
            x.setReferenceInvoiceNumber(rs.getString(19));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a RemittanceDetailDataVector object that consists
     * of RemittanceDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for RemittanceDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new RemittanceDetailDataVector()
     * @throws            SQLException
     */
    public static RemittanceDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        RemittanceDetailDataVector v = new RemittanceDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REMITTANCE_DETAIL_ID,SITE_REFERENCE,INVOICE_NUMBER,INVOICE_TYPE,DISCOUNT_AMOUNT,NET_AMOUNT,ORIG_INVOICE_AMOUNT,CUSTOMER_PO_NUMBER,REMITTANCE_ID,CUSTOMER_SUPPLIER_NUMBER,REMITTANCE_DETAIL_STATUS_CD,ERP_REFERENCE_NUMBER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TYPE_CD,TRANSACTION_CD,REFERENCE_INVOICE_NUMBER FROM CLW_REMITTANCE_DETAIL WHERE REMITTANCE_DETAIL_ID IN (");

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
            RemittanceDetailData x=null;
            while (rs.next()) {
                // build the object
                x=RemittanceDetailData.createValue();
                
                x.setRemittanceDetailId(rs.getInt(1));
                x.setSiteReference(rs.getString(2));
                x.setInvoiceNumber(rs.getString(3));
                x.setInvoiceType(rs.getString(4));
                x.setDiscountAmount(rs.getBigDecimal(5));
                x.setNetAmount(rs.getBigDecimal(6));
                x.setOrigInvoiceAmount(rs.getBigDecimal(7));
                x.setCustomerPoNumber(rs.getString(8));
                x.setRemittanceId(rs.getInt(9));
                x.setCustomerSupplierNumber(rs.getString(10));
                x.setRemittanceDetailStatusCd(rs.getString(11));
                x.setErpReferenceNumber(rs.getString(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setPaymentTypeCd(rs.getString(17));
                x.setTransactionCd(rs.getString(18));
                x.setReferenceInvoiceNumber(rs.getString(19));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a RemittanceDetailDataVector object of all
     * RemittanceDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new RemittanceDetailDataVector()
     * @throws            SQLException
     */
    public static RemittanceDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REMITTANCE_DETAIL_ID,SITE_REFERENCE,INVOICE_NUMBER,INVOICE_TYPE,DISCOUNT_AMOUNT,NET_AMOUNT,ORIG_INVOICE_AMOUNT,CUSTOMER_PO_NUMBER,REMITTANCE_ID,CUSTOMER_SUPPLIER_NUMBER,REMITTANCE_DETAIL_STATUS_CD,ERP_REFERENCE_NUMBER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TYPE_CD,TRANSACTION_CD,REFERENCE_INVOICE_NUMBER FROM CLW_REMITTANCE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        RemittanceDetailDataVector v = new RemittanceDetailDataVector();
        RemittanceDetailData x = null;
        while (rs.next()) {
            // build the object
            x = RemittanceDetailData.createValue();
            
            x.setRemittanceDetailId(rs.getInt(1));
            x.setSiteReference(rs.getString(2));
            x.setInvoiceNumber(rs.getString(3));
            x.setInvoiceType(rs.getString(4));
            x.setDiscountAmount(rs.getBigDecimal(5));
            x.setNetAmount(rs.getBigDecimal(6));
            x.setOrigInvoiceAmount(rs.getBigDecimal(7));
            x.setCustomerPoNumber(rs.getString(8));
            x.setRemittanceId(rs.getInt(9));
            x.setCustomerSupplierNumber(rs.getString(10));
            x.setRemittanceDetailStatusCd(rs.getString(11));
            x.setErpReferenceNumber(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setPaymentTypeCd(rs.getString(17));
            x.setTransactionCd(rs.getString(18));
            x.setReferenceInvoiceNumber(rs.getString(19));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * RemittanceDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REMITTANCE_DETAIL_ID FROM CLW_REMITTANCE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REMITTANCE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REMITTANCE_DETAIL");
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
     * Inserts a RemittanceDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new RemittanceDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RemittanceDetailData insert(Connection pCon, RemittanceDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_REMITTANCE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_REMITTANCE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setRemittanceDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_REMITTANCE_DETAIL (REMITTANCE_DETAIL_ID,SITE_REFERENCE,INVOICE_NUMBER,INVOICE_TYPE,DISCOUNT_AMOUNT,NET_AMOUNT,ORIG_INVOICE_AMOUNT,CUSTOMER_PO_NUMBER,REMITTANCE_ID,CUSTOMER_SUPPLIER_NUMBER,REMITTANCE_DETAIL_STATUS_CD,ERP_REFERENCE_NUMBER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TYPE_CD,TRANSACTION_CD,REFERENCE_INVOICE_NUMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getRemittanceDetailId());
        pstmt.setString(2,pData.getSiteReference());
        pstmt.setString(3,pData.getInvoiceNumber());
        pstmt.setString(4,pData.getInvoiceType());
        pstmt.setBigDecimal(5,pData.getDiscountAmount());
        pstmt.setBigDecimal(6,pData.getNetAmount());
        pstmt.setBigDecimal(7,pData.getOrigInvoiceAmount());
        pstmt.setString(8,pData.getCustomerPoNumber());
        pstmt.setInt(9,pData.getRemittanceId());
        pstmt.setString(10,pData.getCustomerSupplierNumber());
        pstmt.setString(11,pData.getRemittanceDetailStatusCd());
        pstmt.setString(12,pData.getErpReferenceNumber());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setString(17,pData.getPaymentTypeCd());
        pstmt.setString(18,pData.getTransactionCd());
        pstmt.setString(19,pData.getReferenceInvoiceNumber());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REMITTANCE_DETAIL_ID="+pData.getRemittanceDetailId());
            log.debug("SQL:   SITE_REFERENCE="+pData.getSiteReference());
            log.debug("SQL:   INVOICE_NUMBER="+pData.getInvoiceNumber());
            log.debug("SQL:   INVOICE_TYPE="+pData.getInvoiceType());
            log.debug("SQL:   DISCOUNT_AMOUNT="+pData.getDiscountAmount());
            log.debug("SQL:   NET_AMOUNT="+pData.getNetAmount());
            log.debug("SQL:   ORIG_INVOICE_AMOUNT="+pData.getOrigInvoiceAmount());
            log.debug("SQL:   CUSTOMER_PO_NUMBER="+pData.getCustomerPoNumber());
            log.debug("SQL:   REMITTANCE_ID="+pData.getRemittanceId());
            log.debug("SQL:   CUSTOMER_SUPPLIER_NUMBER="+pData.getCustomerSupplierNumber());
            log.debug("SQL:   REMITTANCE_DETAIL_STATUS_CD="+pData.getRemittanceDetailStatusCd());
            log.debug("SQL:   ERP_REFERENCE_NUMBER="+pData.getErpReferenceNumber());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PAYMENT_TYPE_CD="+pData.getPaymentTypeCd());
            log.debug("SQL:   TRANSACTION_CD="+pData.getTransactionCd());
            log.debug("SQL:   REFERENCE_INVOICE_NUMBER="+pData.getReferenceInvoiceNumber());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setRemittanceDetailId(0);
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
     * Updates a RemittanceDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RemittanceDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_REMITTANCE_DETAIL SET SITE_REFERENCE = ?,INVOICE_NUMBER = ?,INVOICE_TYPE = ?,DISCOUNT_AMOUNT = ?,NET_AMOUNT = ?,ORIG_INVOICE_AMOUNT = ?,CUSTOMER_PO_NUMBER = ?,REMITTANCE_ID = ?,CUSTOMER_SUPPLIER_NUMBER = ?,REMITTANCE_DETAIL_STATUS_CD = ?,ERP_REFERENCE_NUMBER = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PAYMENT_TYPE_CD = ?,TRANSACTION_CD = ?,REFERENCE_INVOICE_NUMBER = ? WHERE REMITTANCE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getSiteReference());
        pstmt.setString(i++,pData.getInvoiceNumber());
        pstmt.setString(i++,pData.getInvoiceType());
        pstmt.setBigDecimal(i++,pData.getDiscountAmount());
        pstmt.setBigDecimal(i++,pData.getNetAmount());
        pstmt.setBigDecimal(i++,pData.getOrigInvoiceAmount());
        pstmt.setString(i++,pData.getCustomerPoNumber());
        pstmt.setInt(i++,pData.getRemittanceId());
        pstmt.setString(i++,pData.getCustomerSupplierNumber());
        pstmt.setString(i++,pData.getRemittanceDetailStatusCd());
        pstmt.setString(i++,pData.getErpReferenceNumber());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getPaymentTypeCd());
        pstmt.setString(i++,pData.getTransactionCd());
        pstmt.setString(i++,pData.getReferenceInvoiceNumber());
        pstmt.setInt(i++,pData.getRemittanceDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SITE_REFERENCE="+pData.getSiteReference());
            log.debug("SQL:   INVOICE_NUMBER="+pData.getInvoiceNumber());
            log.debug("SQL:   INVOICE_TYPE="+pData.getInvoiceType());
            log.debug("SQL:   DISCOUNT_AMOUNT="+pData.getDiscountAmount());
            log.debug("SQL:   NET_AMOUNT="+pData.getNetAmount());
            log.debug("SQL:   ORIG_INVOICE_AMOUNT="+pData.getOrigInvoiceAmount());
            log.debug("SQL:   CUSTOMER_PO_NUMBER="+pData.getCustomerPoNumber());
            log.debug("SQL:   REMITTANCE_ID="+pData.getRemittanceId());
            log.debug("SQL:   CUSTOMER_SUPPLIER_NUMBER="+pData.getCustomerSupplierNumber());
            log.debug("SQL:   REMITTANCE_DETAIL_STATUS_CD="+pData.getRemittanceDetailStatusCd());
            log.debug("SQL:   ERP_REFERENCE_NUMBER="+pData.getErpReferenceNumber());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PAYMENT_TYPE_CD="+pData.getPaymentTypeCd());
            log.debug("SQL:   TRANSACTION_CD="+pData.getTransactionCd());
            log.debug("SQL:   REFERENCE_INVOICE_NUMBER="+pData.getReferenceInvoiceNumber());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a RemittanceDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRemittanceDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRemittanceDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_REMITTANCE_DETAIL WHERE REMITTANCE_DETAIL_ID = " + pRemittanceDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes RemittanceDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_REMITTANCE_DETAIL");
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
     * Inserts a RemittanceDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, RemittanceDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_REMITTANCE_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "REMITTANCE_DETAIL_ID,SITE_REFERENCE,INVOICE_NUMBER,INVOICE_TYPE,DISCOUNT_AMOUNT,NET_AMOUNT,ORIG_INVOICE_AMOUNT,CUSTOMER_PO_NUMBER,REMITTANCE_ID,CUSTOMER_SUPPLIER_NUMBER,REMITTANCE_DETAIL_STATUS_CD,ERP_REFERENCE_NUMBER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TYPE_CD,TRANSACTION_CD,REFERENCE_INVOICE_NUMBER) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getRemittanceDetailId());
        pstmt.setString(2+4,pData.getSiteReference());
        pstmt.setString(3+4,pData.getInvoiceNumber());
        pstmt.setString(4+4,pData.getInvoiceType());
        pstmt.setBigDecimal(5+4,pData.getDiscountAmount());
        pstmt.setBigDecimal(6+4,pData.getNetAmount());
        pstmt.setBigDecimal(7+4,pData.getOrigInvoiceAmount());
        pstmt.setString(8+4,pData.getCustomerPoNumber());
        pstmt.setInt(9+4,pData.getRemittanceId());
        pstmt.setString(10+4,pData.getCustomerSupplierNumber());
        pstmt.setString(11+4,pData.getRemittanceDetailStatusCd());
        pstmt.setString(12+4,pData.getErpReferenceNumber());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setString(17+4,pData.getPaymentTypeCd());
        pstmt.setString(18+4,pData.getTransactionCd());
        pstmt.setString(19+4,pData.getReferenceInvoiceNumber());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a RemittanceDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new RemittanceDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static RemittanceDetailData insert(Connection pCon, RemittanceDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a RemittanceDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A RemittanceDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, RemittanceDetailData pData, boolean pLogFl)
        throws SQLException {
        RemittanceDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getRemittanceDetailId();
          try {
          oldData = RemittanceDetailDataAccess.select(pCon,id);
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
     * Deletes a RemittanceDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pRemittanceDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pRemittanceDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_REMITTANCE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REMITTANCE_DETAIL d WHERE REMITTANCE_DETAIL_ID = " + pRemittanceDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pRemittanceDetailId);
        return n;
     }

    /**
     * Deletes RemittanceDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_REMITTANCE_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REMITTANCE_DETAIL d ");
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

