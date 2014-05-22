
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InvoiceCustDataAccess
 * Description:  This class is used to build access methods to the CLW_INVOICE_CUST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InvoiceCustData;
import com.cleanwise.service.api.value.InvoiceCustDataVector;
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
 * <code>InvoiceCustDataAccess</code>
 */
public class InvoiceCustDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InvoiceCustDataAccess.class.getName());

    /** <code>CLW_INVOICE_CUST</code> table name */
	/* Primary key: INVOICE_CUST_ID */
	
    public static final String CLW_INVOICE_CUST = "CLW_INVOICE_CUST";
    
    /** <code>INVOICE_CUST_ID</code> INVOICE_CUST_ID column of table CLW_INVOICE_CUST */
    public static final String INVOICE_CUST_ID = "INVOICE_CUST_ID";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_INVOICE_CUST */
    public static final String STORE_ID = "STORE_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_INVOICE_CUST */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_INVOICE_CUST */
    public static final String SITE_ID = "SITE_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_INVOICE_CUST */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ERP_PO_NUM</code> ERP_PO_NUM column of table CLW_INVOICE_CUST */
    public static final String ERP_PO_NUM = "ERP_PO_NUM";
    /** <code>INVOICE_NUM</code> INVOICE_NUM column of table CLW_INVOICE_CUST */
    public static final String INVOICE_NUM = "INVOICE_NUM";
    /** <code>INVOICE_DATE</code> INVOICE_DATE column of table CLW_INVOICE_CUST */
    public static final String INVOICE_DATE = "INVOICE_DATE";
    /** <code>INVOICE_STATUS_CD</code> INVOICE_STATUS_CD column of table CLW_INVOICE_CUST */
    public static final String INVOICE_STATUS_CD = "INVOICE_STATUS_CD";
    /** <code>BILL_TO_NAME</code> BILL_TO_NAME column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_NAME = "BILL_TO_NAME";
    /** <code>BILL_TO_ADDRESS_1</code> BILL_TO_ADDRESS_1 column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_ADDRESS_1 = "BILL_TO_ADDRESS_1";
    /** <code>BILL_TO_ADDRESS_2</code> BILL_TO_ADDRESS_2 column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_ADDRESS_2 = "BILL_TO_ADDRESS_2";
    /** <code>BILL_TO_ADDRESS_3</code> BILL_TO_ADDRESS_3 column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_ADDRESS_3 = "BILL_TO_ADDRESS_3";
    /** <code>BILL_TO_ADDRESS_4</code> BILL_TO_ADDRESS_4 column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_ADDRESS_4 = "BILL_TO_ADDRESS_4";
    /** <code>BILL_TO_CITY</code> BILL_TO_CITY column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_CITY = "BILL_TO_CITY";
    /** <code>BILL_TO_STATE</code> BILL_TO_STATE column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_STATE = "BILL_TO_STATE";
    /** <code>BILL_TO_POSTAL_CODE</code> BILL_TO_POSTAL_CODE column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_POSTAL_CODE = "BILL_TO_POSTAL_CODE";
    /** <code>BILL_TO_COUNTRY</code> BILL_TO_COUNTRY column of table CLW_INVOICE_CUST */
    public static final String BILL_TO_COUNTRY = "BILL_TO_COUNTRY";
    /** <code>SHIPPING_NAME</code> SHIPPING_NAME column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_NAME = "SHIPPING_NAME";
    /** <code>SHIPPING_ADDRESS_1</code> SHIPPING_ADDRESS_1 column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_ADDRESS_1 = "SHIPPING_ADDRESS_1";
    /** <code>SHIPPING_ADDRESS_2</code> SHIPPING_ADDRESS_2 column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_ADDRESS_2 = "SHIPPING_ADDRESS_2";
    /** <code>SHIPPING_ADDRESS_3</code> SHIPPING_ADDRESS_3 column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_ADDRESS_3 = "SHIPPING_ADDRESS_3";
    /** <code>SHIPPING_ADDRESS_4</code> SHIPPING_ADDRESS_4 column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_ADDRESS_4 = "SHIPPING_ADDRESS_4";
    /** <code>SHIPPING_CITY</code> SHIPPING_CITY column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_CITY = "SHIPPING_CITY";
    /** <code>SHIPPING_STATE</code> SHIPPING_STATE column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_STATE = "SHIPPING_STATE";
    /** <code>SHIPPING_POSTAL_CODE</code> SHIPPING_POSTAL_CODE column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_POSTAL_CODE = "SHIPPING_POSTAL_CODE";
    /** <code>SHIPPING_COUNTRY</code> SHIPPING_COUNTRY column of table CLW_INVOICE_CUST */
    public static final String SHIPPING_COUNTRY = "SHIPPING_COUNTRY";
    /** <code>NET_DUE</code> NET_DUE column of table CLW_INVOICE_CUST */
    public static final String NET_DUE = "NET_DUE";
    /** <code>SUB_TOTAL</code> SUB_TOTAL column of table CLW_INVOICE_CUST */
    public static final String SUB_TOTAL = "SUB_TOTAL";
    /** <code>FREIGHT</code> FREIGHT column of table CLW_INVOICE_CUST */
    public static final String FREIGHT = "FREIGHT";
    /** <code>SALES_TAX</code> SALES_TAX column of table CLW_INVOICE_CUST */
    public static final String SALES_TAX = "SALES_TAX";
    /** <code>DISCOUNTS</code> DISCOUNTS column of table CLW_INVOICE_CUST */
    public static final String DISCOUNTS = "DISCOUNTS";
    /** <code>MISC_CHARGES</code> MISC_CHARGES column of table CLW_INVOICE_CUST */
    public static final String MISC_CHARGES = "MISC_CHARGES";
    /** <code>CREDITS</code> CREDITS column of table CLW_INVOICE_CUST */
    public static final String CREDITS = "CREDITS";
    /** <code>BATCH_NUMBER</code> BATCH_NUMBER column of table CLW_INVOICE_CUST */
    public static final String BATCH_NUMBER = "BATCH_NUMBER";
    /** <code>BATCH_DATE</code> BATCH_DATE column of table CLW_INVOICE_CUST */
    public static final String BATCH_DATE = "BATCH_DATE";
    /** <code>BATCH_TIME</code> BATCH_TIME column of table CLW_INVOICE_CUST */
    public static final String BATCH_TIME = "BATCH_TIME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVOICE_CUST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVOICE_CUST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVOICE_CUST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVOICE_CUST */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PAYMENT_TERMS_CD</code> PAYMENT_TERMS_CD column of table CLW_INVOICE_CUST */
    public static final String PAYMENT_TERMS_CD = "PAYMENT_TERMS_CD";
    /** <code>CIT_STATUS_CD</code> CIT_STATUS_CD column of table CLW_INVOICE_CUST */
    public static final String CIT_STATUS_CD = "CIT_STATUS_CD";
    /** <code>CIT_ASSIGNMENT_NUMBER</code> CIT_ASSIGNMENT_NUMBER column of table CLW_INVOICE_CUST */
    public static final String CIT_ASSIGNMENT_NUMBER = "CIT_ASSIGNMENT_NUMBER";
    /** <code>CIT_TRANSACTION_DATE</code> CIT_TRANSACTION_DATE column of table CLW_INVOICE_CUST */
    public static final String CIT_TRANSACTION_DATE = "CIT_TRANSACTION_DATE";
    /** <code>ORIGINAL_INVOICE_NUM</code> ORIGINAL_INVOICE_NUM column of table CLW_INVOICE_CUST */
    public static final String ORIGINAL_INVOICE_NUM = "ORIGINAL_INVOICE_NUM";
    /** <code>INVOICE_TYPE</code> INVOICE_TYPE column of table CLW_INVOICE_CUST */
    public static final String INVOICE_TYPE = "INVOICE_TYPE";
    /** <code>ERP_SYSTEM_CD</code> ERP_SYSTEM_CD column of table CLW_INVOICE_CUST */
    public static final String ERP_SYSTEM_CD = "ERP_SYSTEM_CD";
    /** <code>FUEL_SURCHARGE</code> FUEL_SURCHARGE column of table CLW_INVOICE_CUST */
    public static final String FUEL_SURCHARGE = "FUEL_SURCHARGE";

    /**
     * Constructor.
     */
    public InvoiceCustDataAccess()
    {
    }

    /**
     * Gets a InvoiceCustData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInvoiceCustId The key requested.
     * @return new InvoiceCustData()
     * @throws            SQLException
     */
    public static InvoiceCustData select(Connection pCon, int pInvoiceCustId)
        throws SQLException, DataNotFoundException {
        InvoiceCustData x=null;
        String sql="SELECT INVOICE_CUST_ID,STORE_ID,ACCOUNT_ID,SITE_ID,ORDER_ID,ERP_PO_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,SHIPPING_NAME,SHIPPING_ADDRESS_1,SHIPPING_ADDRESS_2,SHIPPING_ADDRESS_3,SHIPPING_ADDRESS_4,SHIPPING_CITY,SHIPPING_STATE,SHIPPING_POSTAL_CODE,SHIPPING_COUNTRY,NET_DUE,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TERMS_CD,CIT_STATUS_CD,CIT_ASSIGNMENT_NUMBER,CIT_TRANSACTION_DATE,ORIGINAL_INVOICE_NUM,INVOICE_TYPE,ERP_SYSTEM_CD,FUEL_SURCHARGE FROM CLW_INVOICE_CUST WHERE INVOICE_CUST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInvoiceCustId=" + pInvoiceCustId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInvoiceCustId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InvoiceCustData.createValue();
            
            x.setInvoiceCustId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setAccountId(rs.getInt(3));
            x.setSiteId(rs.getInt(4));
            x.setOrderId(rs.getInt(5));
            x.setErpPoNum(rs.getString(6));
            x.setInvoiceNum(rs.getString(7));
            x.setInvoiceDate(rs.getDate(8));
            x.setInvoiceStatusCd(rs.getString(9));
            x.setBillToName(rs.getString(10));
            x.setBillToAddress1(rs.getString(11));
            x.setBillToAddress2(rs.getString(12));
            x.setBillToAddress3(rs.getString(13));
            x.setBillToAddress4(rs.getString(14));
            x.setBillToCity(rs.getString(15));
            x.setBillToState(rs.getString(16));
            x.setBillToPostalCode(rs.getString(17));
            x.setBillToCountry(rs.getString(18));
            x.setShippingName(rs.getString(19));
            x.setShippingAddress1(rs.getString(20));
            x.setShippingAddress2(rs.getString(21));
            x.setShippingAddress3(rs.getString(22));
            x.setShippingAddress4(rs.getString(23));
            x.setShippingCity(rs.getString(24));
            x.setShippingState(rs.getString(25));
            x.setShippingPostalCode(rs.getString(26));
            x.setShippingCountry(rs.getString(27));
            x.setNetDue(rs.getBigDecimal(28));
            x.setSubTotal(rs.getBigDecimal(29));
            x.setFreight(rs.getBigDecimal(30));
            x.setSalesTax(rs.getBigDecimal(31));
            x.setDiscounts(rs.getBigDecimal(32));
            x.setMiscCharges(rs.getBigDecimal(33));
            x.setCredits(rs.getBigDecimal(34));
            x.setBatchNumber(rs.getInt(35));
            x.setBatchDate(rs.getDate(36));
            x.setBatchTime(rs.getTimestamp(37));
            x.setAddDate(rs.getTimestamp(38));
            x.setAddBy(rs.getString(39));
            x.setModDate(rs.getTimestamp(40));
            x.setModBy(rs.getString(41));
            x.setPaymentTermsCd(rs.getString(42));
            x.setCitStatusCd(rs.getString(43));
            x.setCitAssignmentNumber(rs.getInt(44));
            x.setCitTransactionDate(rs.getDate(45));
            x.setOriginalInvoiceNum(rs.getString(46));
            x.setInvoiceType(rs.getString(47));
            x.setErpSystemCd(rs.getString(48));
            x.setFuelSurcharge(rs.getBigDecimal(49));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVOICE_CUST_ID :" + pInvoiceCustId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InvoiceCustDataVector object that consists
     * of InvoiceCustData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InvoiceCustDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InvoiceCustData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVOICE_CUST.INVOICE_CUST_ID,CLW_INVOICE_CUST.STORE_ID,CLW_INVOICE_CUST.ACCOUNT_ID,CLW_INVOICE_CUST.SITE_ID,CLW_INVOICE_CUST.ORDER_ID,CLW_INVOICE_CUST.ERP_PO_NUM,CLW_INVOICE_CUST.INVOICE_NUM,CLW_INVOICE_CUST.INVOICE_DATE,CLW_INVOICE_CUST.INVOICE_STATUS_CD,CLW_INVOICE_CUST.BILL_TO_NAME,CLW_INVOICE_CUST.BILL_TO_ADDRESS_1,CLW_INVOICE_CUST.BILL_TO_ADDRESS_2,CLW_INVOICE_CUST.BILL_TO_ADDRESS_3,CLW_INVOICE_CUST.BILL_TO_ADDRESS_4,CLW_INVOICE_CUST.BILL_TO_CITY,CLW_INVOICE_CUST.BILL_TO_STATE,CLW_INVOICE_CUST.BILL_TO_POSTAL_CODE,CLW_INVOICE_CUST.BILL_TO_COUNTRY,CLW_INVOICE_CUST.SHIPPING_NAME,CLW_INVOICE_CUST.SHIPPING_ADDRESS_1,CLW_INVOICE_CUST.SHIPPING_ADDRESS_2,CLW_INVOICE_CUST.SHIPPING_ADDRESS_3,CLW_INVOICE_CUST.SHIPPING_ADDRESS_4,CLW_INVOICE_CUST.SHIPPING_CITY,CLW_INVOICE_CUST.SHIPPING_STATE,CLW_INVOICE_CUST.SHIPPING_POSTAL_CODE,CLW_INVOICE_CUST.SHIPPING_COUNTRY,CLW_INVOICE_CUST.NET_DUE,CLW_INVOICE_CUST.SUB_TOTAL,CLW_INVOICE_CUST.FREIGHT,CLW_INVOICE_CUST.SALES_TAX,CLW_INVOICE_CUST.DISCOUNTS,CLW_INVOICE_CUST.MISC_CHARGES,CLW_INVOICE_CUST.CREDITS,CLW_INVOICE_CUST.BATCH_NUMBER,CLW_INVOICE_CUST.BATCH_DATE,CLW_INVOICE_CUST.BATCH_TIME,CLW_INVOICE_CUST.ADD_DATE,CLW_INVOICE_CUST.ADD_BY,CLW_INVOICE_CUST.MOD_DATE,CLW_INVOICE_CUST.MOD_BY,CLW_INVOICE_CUST.PAYMENT_TERMS_CD,CLW_INVOICE_CUST.CIT_STATUS_CD,CLW_INVOICE_CUST.CIT_ASSIGNMENT_NUMBER,CLW_INVOICE_CUST.CIT_TRANSACTION_DATE,CLW_INVOICE_CUST.ORIGINAL_INVOICE_NUM,CLW_INVOICE_CUST.INVOICE_TYPE,CLW_INVOICE_CUST.ERP_SYSTEM_CD,CLW_INVOICE_CUST.FUEL_SURCHARGE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InvoiceCustData Object.
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
    *@returns a populated InvoiceCustData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InvoiceCustData x = InvoiceCustData.createValue();
         
         x.setInvoiceCustId(rs.getInt(1+offset));
         x.setStoreId(rs.getInt(2+offset));
         x.setAccountId(rs.getInt(3+offset));
         x.setSiteId(rs.getInt(4+offset));
         x.setOrderId(rs.getInt(5+offset));
         x.setErpPoNum(rs.getString(6+offset));
         x.setInvoiceNum(rs.getString(7+offset));
         x.setInvoiceDate(rs.getDate(8+offset));
         x.setInvoiceStatusCd(rs.getString(9+offset));
         x.setBillToName(rs.getString(10+offset));
         x.setBillToAddress1(rs.getString(11+offset));
         x.setBillToAddress2(rs.getString(12+offset));
         x.setBillToAddress3(rs.getString(13+offset));
         x.setBillToAddress4(rs.getString(14+offset));
         x.setBillToCity(rs.getString(15+offset));
         x.setBillToState(rs.getString(16+offset));
         x.setBillToPostalCode(rs.getString(17+offset));
         x.setBillToCountry(rs.getString(18+offset));
         x.setShippingName(rs.getString(19+offset));
         x.setShippingAddress1(rs.getString(20+offset));
         x.setShippingAddress2(rs.getString(21+offset));
         x.setShippingAddress3(rs.getString(22+offset));
         x.setShippingAddress4(rs.getString(23+offset));
         x.setShippingCity(rs.getString(24+offset));
         x.setShippingState(rs.getString(25+offset));
         x.setShippingPostalCode(rs.getString(26+offset));
         x.setShippingCountry(rs.getString(27+offset));
         x.setNetDue(rs.getBigDecimal(28+offset));
         x.setSubTotal(rs.getBigDecimal(29+offset));
         x.setFreight(rs.getBigDecimal(30+offset));
         x.setSalesTax(rs.getBigDecimal(31+offset));
         x.setDiscounts(rs.getBigDecimal(32+offset));
         x.setMiscCharges(rs.getBigDecimal(33+offset));
         x.setCredits(rs.getBigDecimal(34+offset));
         x.setBatchNumber(rs.getInt(35+offset));
         x.setBatchDate(rs.getDate(36+offset));
         x.setBatchTime(rs.getTimestamp(37+offset));
         x.setAddDate(rs.getTimestamp(38+offset));
         x.setAddBy(rs.getString(39+offset));
         x.setModDate(rs.getTimestamp(40+offset));
         x.setModBy(rs.getString(41+offset));
         x.setPaymentTermsCd(rs.getString(42+offset));
         x.setCitStatusCd(rs.getString(43+offset));
         x.setCitAssignmentNumber(rs.getInt(44+offset));
         x.setCitTransactionDate(rs.getDate(45+offset));
         x.setOriginalInvoiceNum(rs.getString(46+offset));
         x.setInvoiceType(rs.getString(47+offset));
         x.setErpSystemCd(rs.getString(48+offset));
         x.setFuelSurcharge(rs.getBigDecimal(49+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InvoiceCustData Object represents.
    */
    public int getColumnCount(){
        return 49;
    }

    /**
     * Gets a InvoiceCustDataVector object that consists
     * of InvoiceCustData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InvoiceCustDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVOICE_CUST_ID,STORE_ID,ACCOUNT_ID,SITE_ID,ORDER_ID,ERP_PO_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,SHIPPING_NAME,SHIPPING_ADDRESS_1,SHIPPING_ADDRESS_2,SHIPPING_ADDRESS_3,SHIPPING_ADDRESS_4,SHIPPING_CITY,SHIPPING_STATE,SHIPPING_POSTAL_CODE,SHIPPING_COUNTRY,NET_DUE,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TERMS_CD,CIT_STATUS_CD,CIT_ASSIGNMENT_NUMBER,CIT_TRANSACTION_DATE,ORIGINAL_INVOICE_NUM,INVOICE_TYPE,ERP_SYSTEM_CD,FUEL_SURCHARGE FROM CLW_INVOICE_CUST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVOICE_CUST.INVOICE_CUST_ID,CLW_INVOICE_CUST.STORE_ID,CLW_INVOICE_CUST.ACCOUNT_ID,CLW_INVOICE_CUST.SITE_ID,CLW_INVOICE_CUST.ORDER_ID,CLW_INVOICE_CUST.ERP_PO_NUM,CLW_INVOICE_CUST.INVOICE_NUM,CLW_INVOICE_CUST.INVOICE_DATE,CLW_INVOICE_CUST.INVOICE_STATUS_CD,CLW_INVOICE_CUST.BILL_TO_NAME,CLW_INVOICE_CUST.BILL_TO_ADDRESS_1,CLW_INVOICE_CUST.BILL_TO_ADDRESS_2,CLW_INVOICE_CUST.BILL_TO_ADDRESS_3,CLW_INVOICE_CUST.BILL_TO_ADDRESS_4,CLW_INVOICE_CUST.BILL_TO_CITY,CLW_INVOICE_CUST.BILL_TO_STATE,CLW_INVOICE_CUST.BILL_TO_POSTAL_CODE,CLW_INVOICE_CUST.BILL_TO_COUNTRY,CLW_INVOICE_CUST.SHIPPING_NAME,CLW_INVOICE_CUST.SHIPPING_ADDRESS_1,CLW_INVOICE_CUST.SHIPPING_ADDRESS_2,CLW_INVOICE_CUST.SHIPPING_ADDRESS_3,CLW_INVOICE_CUST.SHIPPING_ADDRESS_4,CLW_INVOICE_CUST.SHIPPING_CITY,CLW_INVOICE_CUST.SHIPPING_STATE,CLW_INVOICE_CUST.SHIPPING_POSTAL_CODE,CLW_INVOICE_CUST.SHIPPING_COUNTRY,CLW_INVOICE_CUST.NET_DUE,CLW_INVOICE_CUST.SUB_TOTAL,CLW_INVOICE_CUST.FREIGHT,CLW_INVOICE_CUST.SALES_TAX,CLW_INVOICE_CUST.DISCOUNTS,CLW_INVOICE_CUST.MISC_CHARGES,CLW_INVOICE_CUST.CREDITS,CLW_INVOICE_CUST.BATCH_NUMBER,CLW_INVOICE_CUST.BATCH_DATE,CLW_INVOICE_CUST.BATCH_TIME,CLW_INVOICE_CUST.ADD_DATE,CLW_INVOICE_CUST.ADD_BY,CLW_INVOICE_CUST.MOD_DATE,CLW_INVOICE_CUST.MOD_BY,CLW_INVOICE_CUST.PAYMENT_TERMS_CD,CLW_INVOICE_CUST.CIT_STATUS_CD,CLW_INVOICE_CUST.CIT_ASSIGNMENT_NUMBER,CLW_INVOICE_CUST.CIT_TRANSACTION_DATE,CLW_INVOICE_CUST.ORIGINAL_INVOICE_NUM,CLW_INVOICE_CUST.INVOICE_TYPE,CLW_INVOICE_CUST.ERP_SYSTEM_CD,CLW_INVOICE_CUST.FUEL_SURCHARGE FROM CLW_INVOICE_CUST");
                where = pCriteria.getSqlClause("CLW_INVOICE_CUST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_CUST.equals(otherTable)){
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
        InvoiceCustDataVector v = new InvoiceCustDataVector();
        while (rs.next()) {
            InvoiceCustData x = InvoiceCustData.createValue();
            
            x.setInvoiceCustId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setAccountId(rs.getInt(3));
            x.setSiteId(rs.getInt(4));
            x.setOrderId(rs.getInt(5));
            x.setErpPoNum(rs.getString(6));
            x.setInvoiceNum(rs.getString(7));
            x.setInvoiceDate(rs.getDate(8));
            x.setInvoiceStatusCd(rs.getString(9));
            x.setBillToName(rs.getString(10));
            x.setBillToAddress1(rs.getString(11));
            x.setBillToAddress2(rs.getString(12));
            x.setBillToAddress3(rs.getString(13));
            x.setBillToAddress4(rs.getString(14));
            x.setBillToCity(rs.getString(15));
            x.setBillToState(rs.getString(16));
            x.setBillToPostalCode(rs.getString(17));
            x.setBillToCountry(rs.getString(18));
            x.setShippingName(rs.getString(19));
            x.setShippingAddress1(rs.getString(20));
            x.setShippingAddress2(rs.getString(21));
            x.setShippingAddress3(rs.getString(22));
            x.setShippingAddress4(rs.getString(23));
            x.setShippingCity(rs.getString(24));
            x.setShippingState(rs.getString(25));
            x.setShippingPostalCode(rs.getString(26));
            x.setShippingCountry(rs.getString(27));
            x.setNetDue(rs.getBigDecimal(28));
            x.setSubTotal(rs.getBigDecimal(29));
            x.setFreight(rs.getBigDecimal(30));
            x.setSalesTax(rs.getBigDecimal(31));
            x.setDiscounts(rs.getBigDecimal(32));
            x.setMiscCharges(rs.getBigDecimal(33));
            x.setCredits(rs.getBigDecimal(34));
            x.setBatchNumber(rs.getInt(35));
            x.setBatchDate(rs.getDate(36));
            x.setBatchTime(rs.getTimestamp(37));
            x.setAddDate(rs.getTimestamp(38));
            x.setAddBy(rs.getString(39));
            x.setModDate(rs.getTimestamp(40));
            x.setModBy(rs.getString(41));
            x.setPaymentTermsCd(rs.getString(42));
            x.setCitStatusCd(rs.getString(43));
            x.setCitAssignmentNumber(rs.getInt(44));
            x.setCitTransactionDate(rs.getDate(45));
            x.setOriginalInvoiceNum(rs.getString(46));
            x.setInvoiceType(rs.getString(47));
            x.setErpSystemCd(rs.getString(48));
            x.setFuelSurcharge(rs.getBigDecimal(49));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InvoiceCustDataVector object that consists
     * of InvoiceCustData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InvoiceCustData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InvoiceCustDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InvoiceCustDataVector v = new InvoiceCustDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_CUST_ID,STORE_ID,ACCOUNT_ID,SITE_ID,ORDER_ID,ERP_PO_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,SHIPPING_NAME,SHIPPING_ADDRESS_1,SHIPPING_ADDRESS_2,SHIPPING_ADDRESS_3,SHIPPING_ADDRESS_4,SHIPPING_CITY,SHIPPING_STATE,SHIPPING_POSTAL_CODE,SHIPPING_COUNTRY,NET_DUE,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TERMS_CD,CIT_STATUS_CD,CIT_ASSIGNMENT_NUMBER,CIT_TRANSACTION_DATE,ORIGINAL_INVOICE_NUM,INVOICE_TYPE,ERP_SYSTEM_CD,FUEL_SURCHARGE FROM CLW_INVOICE_CUST WHERE INVOICE_CUST_ID IN (");

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
            InvoiceCustData x=null;
            while (rs.next()) {
                // build the object
                x=InvoiceCustData.createValue();
                
                x.setInvoiceCustId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setAccountId(rs.getInt(3));
                x.setSiteId(rs.getInt(4));
                x.setOrderId(rs.getInt(5));
                x.setErpPoNum(rs.getString(6));
                x.setInvoiceNum(rs.getString(7));
                x.setInvoiceDate(rs.getDate(8));
                x.setInvoiceStatusCd(rs.getString(9));
                x.setBillToName(rs.getString(10));
                x.setBillToAddress1(rs.getString(11));
                x.setBillToAddress2(rs.getString(12));
                x.setBillToAddress3(rs.getString(13));
                x.setBillToAddress4(rs.getString(14));
                x.setBillToCity(rs.getString(15));
                x.setBillToState(rs.getString(16));
                x.setBillToPostalCode(rs.getString(17));
                x.setBillToCountry(rs.getString(18));
                x.setShippingName(rs.getString(19));
                x.setShippingAddress1(rs.getString(20));
                x.setShippingAddress2(rs.getString(21));
                x.setShippingAddress3(rs.getString(22));
                x.setShippingAddress4(rs.getString(23));
                x.setShippingCity(rs.getString(24));
                x.setShippingState(rs.getString(25));
                x.setShippingPostalCode(rs.getString(26));
                x.setShippingCountry(rs.getString(27));
                x.setNetDue(rs.getBigDecimal(28));
                x.setSubTotal(rs.getBigDecimal(29));
                x.setFreight(rs.getBigDecimal(30));
                x.setSalesTax(rs.getBigDecimal(31));
                x.setDiscounts(rs.getBigDecimal(32));
                x.setMiscCharges(rs.getBigDecimal(33));
                x.setCredits(rs.getBigDecimal(34));
                x.setBatchNumber(rs.getInt(35));
                x.setBatchDate(rs.getDate(36));
                x.setBatchTime(rs.getTimestamp(37));
                x.setAddDate(rs.getTimestamp(38));
                x.setAddBy(rs.getString(39));
                x.setModDate(rs.getTimestamp(40));
                x.setModBy(rs.getString(41));
                x.setPaymentTermsCd(rs.getString(42));
                x.setCitStatusCd(rs.getString(43));
                x.setCitAssignmentNumber(rs.getInt(44));
                x.setCitTransactionDate(rs.getDate(45));
                x.setOriginalInvoiceNum(rs.getString(46));
                x.setInvoiceType(rs.getString(47));
                x.setErpSystemCd(rs.getString(48));
                x.setFuelSurcharge(rs.getBigDecimal(49));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InvoiceCustDataVector object of all
     * InvoiceCustData objects in the database.
     * @param pCon An open database connection.
     * @return new InvoiceCustDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVOICE_CUST_ID,STORE_ID,ACCOUNT_ID,SITE_ID,ORDER_ID,ERP_PO_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,SHIPPING_NAME,SHIPPING_ADDRESS_1,SHIPPING_ADDRESS_2,SHIPPING_ADDRESS_3,SHIPPING_ADDRESS_4,SHIPPING_CITY,SHIPPING_STATE,SHIPPING_POSTAL_CODE,SHIPPING_COUNTRY,NET_DUE,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TERMS_CD,CIT_STATUS_CD,CIT_ASSIGNMENT_NUMBER,CIT_TRANSACTION_DATE,ORIGINAL_INVOICE_NUM,INVOICE_TYPE,ERP_SYSTEM_CD,FUEL_SURCHARGE FROM CLW_INVOICE_CUST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InvoiceCustDataVector v = new InvoiceCustDataVector();
        InvoiceCustData x = null;
        while (rs.next()) {
            // build the object
            x = InvoiceCustData.createValue();
            
            x.setInvoiceCustId(rs.getInt(1));
            x.setStoreId(rs.getInt(2));
            x.setAccountId(rs.getInt(3));
            x.setSiteId(rs.getInt(4));
            x.setOrderId(rs.getInt(5));
            x.setErpPoNum(rs.getString(6));
            x.setInvoiceNum(rs.getString(7));
            x.setInvoiceDate(rs.getDate(8));
            x.setInvoiceStatusCd(rs.getString(9));
            x.setBillToName(rs.getString(10));
            x.setBillToAddress1(rs.getString(11));
            x.setBillToAddress2(rs.getString(12));
            x.setBillToAddress3(rs.getString(13));
            x.setBillToAddress4(rs.getString(14));
            x.setBillToCity(rs.getString(15));
            x.setBillToState(rs.getString(16));
            x.setBillToPostalCode(rs.getString(17));
            x.setBillToCountry(rs.getString(18));
            x.setShippingName(rs.getString(19));
            x.setShippingAddress1(rs.getString(20));
            x.setShippingAddress2(rs.getString(21));
            x.setShippingAddress3(rs.getString(22));
            x.setShippingAddress4(rs.getString(23));
            x.setShippingCity(rs.getString(24));
            x.setShippingState(rs.getString(25));
            x.setShippingPostalCode(rs.getString(26));
            x.setShippingCountry(rs.getString(27));
            x.setNetDue(rs.getBigDecimal(28));
            x.setSubTotal(rs.getBigDecimal(29));
            x.setFreight(rs.getBigDecimal(30));
            x.setSalesTax(rs.getBigDecimal(31));
            x.setDiscounts(rs.getBigDecimal(32));
            x.setMiscCharges(rs.getBigDecimal(33));
            x.setCredits(rs.getBigDecimal(34));
            x.setBatchNumber(rs.getInt(35));
            x.setBatchDate(rs.getDate(36));
            x.setBatchTime(rs.getTimestamp(37));
            x.setAddDate(rs.getTimestamp(38));
            x.setAddBy(rs.getString(39));
            x.setModDate(rs.getTimestamp(40));
            x.setModBy(rs.getString(41));
            x.setPaymentTermsCd(rs.getString(42));
            x.setCitStatusCd(rs.getString(43));
            x.setCitAssignmentNumber(rs.getInt(44));
            x.setCitTransactionDate(rs.getDate(45));
            x.setOriginalInvoiceNum(rs.getString(46));
            x.setInvoiceType(rs.getString(47));
            x.setErpSystemCd(rs.getString(48));
            x.setFuelSurcharge(rs.getBigDecimal(49));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InvoiceCustData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_CUST_ID FROM CLW_INVOICE_CUST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_CUST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_CUST");
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
     * Inserts a InvoiceCustData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InvoiceCustData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceCustData insert(Connection pCon, InvoiceCustData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVOICE_CUST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVOICE_CUST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInvoiceCustId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVOICE_CUST (INVOICE_CUST_ID,STORE_ID,ACCOUNT_ID,SITE_ID,ORDER_ID,ERP_PO_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,SHIPPING_NAME,SHIPPING_ADDRESS_1,SHIPPING_ADDRESS_2,SHIPPING_ADDRESS_3,SHIPPING_ADDRESS_4,SHIPPING_CITY,SHIPPING_STATE,SHIPPING_POSTAL_CODE,SHIPPING_COUNTRY,NET_DUE,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TERMS_CD,CIT_STATUS_CD,CIT_ASSIGNMENT_NUMBER,CIT_TRANSACTION_DATE,ORIGINAL_INVOICE_NUM,INVOICE_TYPE,ERP_SYSTEM_CD,FUEL_SURCHARGE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInvoiceCustId());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getStoreId());
        }

        if (pData.getAccountId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getAccountId());
        }

        if (pData.getSiteId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getSiteId());
        }

        if (pData.getOrderId() == 0) {
            pstmt.setNull(5, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5,pData.getOrderId());
        }

        pstmt.setString(6,pData.getErpPoNum());
        pstmt.setString(7,pData.getInvoiceNum());
        pstmt.setDate(8,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(9,pData.getInvoiceStatusCd());
        pstmt.setString(10,pData.getBillToName());
        pstmt.setString(11,pData.getBillToAddress1());
        pstmt.setString(12,pData.getBillToAddress2());
        pstmt.setString(13,pData.getBillToAddress3());
        pstmt.setString(14,pData.getBillToAddress4());
        pstmt.setString(15,pData.getBillToCity());
        pstmt.setString(16,pData.getBillToState());
        pstmt.setString(17,pData.getBillToPostalCode());
        pstmt.setString(18,pData.getBillToCountry());
        pstmt.setString(19,pData.getShippingName());
        pstmt.setString(20,pData.getShippingAddress1());
        pstmt.setString(21,pData.getShippingAddress2());
        pstmt.setString(22,pData.getShippingAddress3());
        pstmt.setString(23,pData.getShippingAddress4());
        pstmt.setString(24,pData.getShippingCity());
        pstmt.setString(25,pData.getShippingState());
        pstmt.setString(26,pData.getShippingPostalCode());
        pstmt.setString(27,pData.getShippingCountry());
        pstmt.setBigDecimal(28,pData.getNetDue());
        pstmt.setBigDecimal(29,pData.getSubTotal());
        pstmt.setBigDecimal(30,pData.getFreight());
        pstmt.setBigDecimal(31,pData.getSalesTax());
        pstmt.setBigDecimal(32,pData.getDiscounts());
        pstmt.setBigDecimal(33,pData.getMiscCharges());
        pstmt.setBigDecimal(34,pData.getCredits());
        pstmt.setInt(35,pData.getBatchNumber());
        pstmt.setDate(36,DBAccess.toSQLDate(pData.getBatchDate()));
        pstmt.setTimestamp(37,DBAccess.toSQLTimestamp(pData.getBatchTime()));
        pstmt.setTimestamp(38,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(39,pData.getAddBy());
        pstmt.setTimestamp(40,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(41,pData.getModBy());
        pstmt.setString(42,pData.getPaymentTermsCd());
        pstmt.setString(43,pData.getCitStatusCd());
        pstmt.setInt(44,pData.getCitAssignmentNumber());
        pstmt.setDate(45,DBAccess.toSQLDate(pData.getCitTransactionDate()));
        pstmt.setString(46,pData.getOriginalInvoiceNum());
        pstmt.setString(47,pData.getInvoiceType());
        pstmt.setString(48,pData.getErpSystemCd());
        pstmt.setBigDecimal(49,pData.getFuelSurcharge());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   INVOICE_STATUS_CD="+pData.getInvoiceStatusCd());
            log.debug("SQL:   BILL_TO_NAME="+pData.getBillToName());
            log.debug("SQL:   BILL_TO_ADDRESS_1="+pData.getBillToAddress1());
            log.debug("SQL:   BILL_TO_ADDRESS_2="+pData.getBillToAddress2());
            log.debug("SQL:   BILL_TO_ADDRESS_3="+pData.getBillToAddress3());
            log.debug("SQL:   BILL_TO_ADDRESS_4="+pData.getBillToAddress4());
            log.debug("SQL:   BILL_TO_CITY="+pData.getBillToCity());
            log.debug("SQL:   BILL_TO_STATE="+pData.getBillToState());
            log.debug("SQL:   BILL_TO_POSTAL_CODE="+pData.getBillToPostalCode());
            log.debug("SQL:   BILL_TO_COUNTRY="+pData.getBillToCountry());
            log.debug("SQL:   SHIPPING_NAME="+pData.getShippingName());
            log.debug("SQL:   SHIPPING_ADDRESS_1="+pData.getShippingAddress1());
            log.debug("SQL:   SHIPPING_ADDRESS_2="+pData.getShippingAddress2());
            log.debug("SQL:   SHIPPING_ADDRESS_3="+pData.getShippingAddress3());
            log.debug("SQL:   SHIPPING_ADDRESS_4="+pData.getShippingAddress4());
            log.debug("SQL:   SHIPPING_CITY="+pData.getShippingCity());
            log.debug("SQL:   SHIPPING_STATE="+pData.getShippingState());
            log.debug("SQL:   SHIPPING_POSTAL_CODE="+pData.getShippingPostalCode());
            log.debug("SQL:   SHIPPING_COUNTRY="+pData.getShippingCountry());
            log.debug("SQL:   NET_DUE="+pData.getNetDue());
            log.debug("SQL:   SUB_TOTAL="+pData.getSubTotal());
            log.debug("SQL:   FREIGHT="+pData.getFreight());
            log.debug("SQL:   SALES_TAX="+pData.getSalesTax());
            log.debug("SQL:   DISCOUNTS="+pData.getDiscounts());
            log.debug("SQL:   MISC_CHARGES="+pData.getMiscCharges());
            log.debug("SQL:   CREDITS="+pData.getCredits());
            log.debug("SQL:   BATCH_NUMBER="+pData.getBatchNumber());
            log.debug("SQL:   BATCH_DATE="+pData.getBatchDate());
            log.debug("SQL:   BATCH_TIME="+pData.getBatchTime());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PAYMENT_TERMS_CD="+pData.getPaymentTermsCd());
            log.debug("SQL:   CIT_STATUS_CD="+pData.getCitStatusCd());
            log.debug("SQL:   CIT_ASSIGNMENT_NUMBER="+pData.getCitAssignmentNumber());
            log.debug("SQL:   CIT_TRANSACTION_DATE="+pData.getCitTransactionDate());
            log.debug("SQL:   ORIGINAL_INVOICE_NUM="+pData.getOriginalInvoiceNum());
            log.debug("SQL:   INVOICE_TYPE="+pData.getInvoiceType());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   FUEL_SURCHARGE="+pData.getFuelSurcharge());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInvoiceCustId(0);
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
     * Updates a InvoiceCustData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceCustData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVOICE_CUST SET STORE_ID = ?,ACCOUNT_ID = ?,SITE_ID = ?,ORDER_ID = ?,ERP_PO_NUM = ?,INVOICE_NUM = ?,INVOICE_DATE = ?,INVOICE_STATUS_CD = ?,BILL_TO_NAME = ?,BILL_TO_ADDRESS_1 = ?,BILL_TO_ADDRESS_2 = ?,BILL_TO_ADDRESS_3 = ?,BILL_TO_ADDRESS_4 = ?,BILL_TO_CITY = ?,BILL_TO_STATE = ?,BILL_TO_POSTAL_CODE = ?,BILL_TO_COUNTRY = ?,SHIPPING_NAME = ?,SHIPPING_ADDRESS_1 = ?,SHIPPING_ADDRESS_2 = ?,SHIPPING_ADDRESS_3 = ?,SHIPPING_ADDRESS_4 = ?,SHIPPING_CITY = ?,SHIPPING_STATE = ?,SHIPPING_POSTAL_CODE = ?,SHIPPING_COUNTRY = ?,NET_DUE = ?,SUB_TOTAL = ?,FREIGHT = ?,SALES_TAX = ?,DISCOUNTS = ?,MISC_CHARGES = ?,CREDITS = ?,BATCH_NUMBER = ?,BATCH_DATE = ?,BATCH_TIME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PAYMENT_TERMS_CD = ?,CIT_STATUS_CD = ?,CIT_ASSIGNMENT_NUMBER = ?,CIT_TRANSACTION_DATE = ?,ORIGINAL_INVOICE_NUM = ?,INVOICE_TYPE = ?,ERP_SYSTEM_CD = ?,FUEL_SURCHARGE = ? WHERE INVOICE_CUST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getStoreId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getStoreId());
        }

        if (pData.getAccountId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getAccountId());
        }

        if (pData.getSiteId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getSiteId());
        }

        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setString(i++,pData.getErpPoNum());
        pstmt.setString(i++,pData.getInvoiceNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(i++,pData.getInvoiceStatusCd());
        pstmt.setString(i++,pData.getBillToName());
        pstmt.setString(i++,pData.getBillToAddress1());
        pstmt.setString(i++,pData.getBillToAddress2());
        pstmt.setString(i++,pData.getBillToAddress3());
        pstmt.setString(i++,pData.getBillToAddress4());
        pstmt.setString(i++,pData.getBillToCity());
        pstmt.setString(i++,pData.getBillToState());
        pstmt.setString(i++,pData.getBillToPostalCode());
        pstmt.setString(i++,pData.getBillToCountry());
        pstmt.setString(i++,pData.getShippingName());
        pstmt.setString(i++,pData.getShippingAddress1());
        pstmt.setString(i++,pData.getShippingAddress2());
        pstmt.setString(i++,pData.getShippingAddress3());
        pstmt.setString(i++,pData.getShippingAddress4());
        pstmt.setString(i++,pData.getShippingCity());
        pstmt.setString(i++,pData.getShippingState());
        pstmt.setString(i++,pData.getShippingPostalCode());
        pstmt.setString(i++,pData.getShippingCountry());
        pstmt.setBigDecimal(i++,pData.getNetDue());
        pstmt.setBigDecimal(i++,pData.getSubTotal());
        pstmt.setBigDecimal(i++,pData.getFreight());
        pstmt.setBigDecimal(i++,pData.getSalesTax());
        pstmt.setBigDecimal(i++,pData.getDiscounts());
        pstmt.setBigDecimal(i++,pData.getMiscCharges());
        pstmt.setBigDecimal(i++,pData.getCredits());
        pstmt.setInt(i++,pData.getBatchNumber());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getBatchDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getBatchTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getPaymentTermsCd());
        pstmt.setString(i++,pData.getCitStatusCd());
        pstmt.setInt(i++,pData.getCitAssignmentNumber());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getCitTransactionDate()));
        pstmt.setString(i++,pData.getOriginalInvoiceNum());
        pstmt.setString(i++,pData.getInvoiceType());
        pstmt.setString(i++,pData.getErpSystemCd());
        pstmt.setBigDecimal(i++,pData.getFuelSurcharge());
        pstmt.setInt(i++,pData.getInvoiceCustId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   INVOICE_STATUS_CD="+pData.getInvoiceStatusCd());
            log.debug("SQL:   BILL_TO_NAME="+pData.getBillToName());
            log.debug("SQL:   BILL_TO_ADDRESS_1="+pData.getBillToAddress1());
            log.debug("SQL:   BILL_TO_ADDRESS_2="+pData.getBillToAddress2());
            log.debug("SQL:   BILL_TO_ADDRESS_3="+pData.getBillToAddress3());
            log.debug("SQL:   BILL_TO_ADDRESS_4="+pData.getBillToAddress4());
            log.debug("SQL:   BILL_TO_CITY="+pData.getBillToCity());
            log.debug("SQL:   BILL_TO_STATE="+pData.getBillToState());
            log.debug("SQL:   BILL_TO_POSTAL_CODE="+pData.getBillToPostalCode());
            log.debug("SQL:   BILL_TO_COUNTRY="+pData.getBillToCountry());
            log.debug("SQL:   SHIPPING_NAME="+pData.getShippingName());
            log.debug("SQL:   SHIPPING_ADDRESS_1="+pData.getShippingAddress1());
            log.debug("SQL:   SHIPPING_ADDRESS_2="+pData.getShippingAddress2());
            log.debug("SQL:   SHIPPING_ADDRESS_3="+pData.getShippingAddress3());
            log.debug("SQL:   SHIPPING_ADDRESS_4="+pData.getShippingAddress4());
            log.debug("SQL:   SHIPPING_CITY="+pData.getShippingCity());
            log.debug("SQL:   SHIPPING_STATE="+pData.getShippingState());
            log.debug("SQL:   SHIPPING_POSTAL_CODE="+pData.getShippingPostalCode());
            log.debug("SQL:   SHIPPING_COUNTRY="+pData.getShippingCountry());
            log.debug("SQL:   NET_DUE="+pData.getNetDue());
            log.debug("SQL:   SUB_TOTAL="+pData.getSubTotal());
            log.debug("SQL:   FREIGHT="+pData.getFreight());
            log.debug("SQL:   SALES_TAX="+pData.getSalesTax());
            log.debug("SQL:   DISCOUNTS="+pData.getDiscounts());
            log.debug("SQL:   MISC_CHARGES="+pData.getMiscCharges());
            log.debug("SQL:   CREDITS="+pData.getCredits());
            log.debug("SQL:   BATCH_NUMBER="+pData.getBatchNumber());
            log.debug("SQL:   BATCH_DATE="+pData.getBatchDate());
            log.debug("SQL:   BATCH_TIME="+pData.getBatchTime());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PAYMENT_TERMS_CD="+pData.getPaymentTermsCd());
            log.debug("SQL:   CIT_STATUS_CD="+pData.getCitStatusCd());
            log.debug("SQL:   CIT_ASSIGNMENT_NUMBER="+pData.getCitAssignmentNumber());
            log.debug("SQL:   CIT_TRANSACTION_DATE="+pData.getCitTransactionDate());
            log.debug("SQL:   ORIGINAL_INVOICE_NUM="+pData.getOriginalInvoiceNum());
            log.debug("SQL:   INVOICE_TYPE="+pData.getInvoiceType());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   FUEL_SURCHARGE="+pData.getFuelSurcharge());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InvoiceCustData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceCustId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceCustId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVOICE_CUST WHERE INVOICE_CUST_ID = " + pInvoiceCustId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InvoiceCustData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVOICE_CUST");
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
     * Inserts a InvoiceCustData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InvoiceCustData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVOICE_CUST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVOICE_CUST_ID,STORE_ID,ACCOUNT_ID,SITE_ID,ORDER_ID,ERP_PO_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,SHIPPING_NAME,SHIPPING_ADDRESS_1,SHIPPING_ADDRESS_2,SHIPPING_ADDRESS_3,SHIPPING_ADDRESS_4,SHIPPING_CITY,SHIPPING_STATE,SHIPPING_POSTAL_CODE,SHIPPING_COUNTRY,NET_DUE,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PAYMENT_TERMS_CD,CIT_STATUS_CD,CIT_ASSIGNMENT_NUMBER,CIT_TRANSACTION_DATE,ORIGINAL_INVOICE_NUM,INVOICE_TYPE,ERP_SYSTEM_CD,FUEL_SURCHARGE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInvoiceCustId());
        if (pData.getStoreId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getStoreId());
        }

        if (pData.getAccountId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getAccountId());
        }

        if (pData.getSiteId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getSiteId());
        }

        if (pData.getOrderId() == 0) {
            pstmt.setNull(5+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(5+4,pData.getOrderId());
        }

        pstmt.setString(6+4,pData.getErpPoNum());
        pstmt.setString(7+4,pData.getInvoiceNum());
        pstmt.setDate(8+4,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(9+4,pData.getInvoiceStatusCd());
        pstmt.setString(10+4,pData.getBillToName());
        pstmt.setString(11+4,pData.getBillToAddress1());
        pstmt.setString(12+4,pData.getBillToAddress2());
        pstmt.setString(13+4,pData.getBillToAddress3());
        pstmt.setString(14+4,pData.getBillToAddress4());
        pstmt.setString(15+4,pData.getBillToCity());
        pstmt.setString(16+4,pData.getBillToState());
        pstmt.setString(17+4,pData.getBillToPostalCode());
        pstmt.setString(18+4,pData.getBillToCountry());
        pstmt.setString(19+4,pData.getShippingName());
        pstmt.setString(20+4,pData.getShippingAddress1());
        pstmt.setString(21+4,pData.getShippingAddress2());
        pstmt.setString(22+4,pData.getShippingAddress3());
        pstmt.setString(23+4,pData.getShippingAddress4());
        pstmt.setString(24+4,pData.getShippingCity());
        pstmt.setString(25+4,pData.getShippingState());
        pstmt.setString(26+4,pData.getShippingPostalCode());
        pstmt.setString(27+4,pData.getShippingCountry());
        pstmt.setBigDecimal(28+4,pData.getNetDue());
        pstmt.setBigDecimal(29+4,pData.getSubTotal());
        pstmt.setBigDecimal(30+4,pData.getFreight());
        pstmt.setBigDecimal(31+4,pData.getSalesTax());
        pstmt.setBigDecimal(32+4,pData.getDiscounts());
        pstmt.setBigDecimal(33+4,pData.getMiscCharges());
        pstmt.setBigDecimal(34+4,pData.getCredits());
        pstmt.setInt(35+4,pData.getBatchNumber());
        pstmt.setDate(36+4,DBAccess.toSQLDate(pData.getBatchDate()));
        pstmt.setTimestamp(37+4,DBAccess.toSQLTimestamp(pData.getBatchTime()));
        pstmt.setTimestamp(38+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(39+4,pData.getAddBy());
        pstmt.setTimestamp(40+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(41+4,pData.getModBy());
        pstmt.setString(42+4,pData.getPaymentTermsCd());
        pstmt.setString(43+4,pData.getCitStatusCd());
        pstmt.setInt(44+4,pData.getCitAssignmentNumber());
        pstmt.setDate(45+4,DBAccess.toSQLDate(pData.getCitTransactionDate()));
        pstmt.setString(46+4,pData.getOriginalInvoiceNum());
        pstmt.setString(47+4,pData.getInvoiceType());
        pstmt.setString(48+4,pData.getErpSystemCd());
        pstmt.setBigDecimal(49+4,pData.getFuelSurcharge());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InvoiceCustData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InvoiceCustData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceCustData insert(Connection pCon, InvoiceCustData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InvoiceCustData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceCustData pData, boolean pLogFl)
        throws SQLException {
        InvoiceCustData oldData = null;
        if(pLogFl) {
          int id = pData.getInvoiceCustId();
          try {
          oldData = InvoiceCustDataAccess.select(pCon,id);
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
     * Deletes a InvoiceCustData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceCustId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceCustId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVOICE_CUST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_CUST d WHERE INVOICE_CUST_ID = " + pInvoiceCustId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInvoiceCustId);
        return n;
     }

    /**
     * Deletes InvoiceCustData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVOICE_CUST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_CUST d ");
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

