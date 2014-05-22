
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InvoiceDistDataAccess
 * Description:  This class is used to build access methods to the CLW_INVOICE_DIST table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
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
 * <code>InvoiceDistDataAccess</code>
 */
public class InvoiceDistDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InvoiceDistDataAccess.class.getName());

    /** <code>CLW_INVOICE_DIST</code> table name */
	/* Primary key: INVOICE_DIST_ID */
	
    public static final String CLW_INVOICE_DIST = "CLW_INVOICE_DIST";
    
    /** <code>INVOICE_DIST_ID</code> INVOICE_DIST_ID column of table CLW_INVOICE_DIST */
    public static final String INVOICE_DIST_ID = "INVOICE_DIST_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_INVOICE_DIST */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_INVOICE_DIST */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ERP_PO_NUM</code> ERP_PO_NUM column of table CLW_INVOICE_DIST */
    public static final String ERP_PO_NUM = "ERP_PO_NUM";
    /** <code>DIST_ORDER_NUM</code> DIST_ORDER_NUM column of table CLW_INVOICE_DIST */
    public static final String DIST_ORDER_NUM = "DIST_ORDER_NUM";
    /** <code>INVOICE_NUM</code> INVOICE_NUM column of table CLW_INVOICE_DIST */
    public static final String INVOICE_NUM = "INVOICE_NUM";
    /** <code>INVOICE_DATE</code> INVOICE_DATE column of table CLW_INVOICE_DIST */
    public static final String INVOICE_DATE = "INVOICE_DATE";
    /** <code>INVOICE_STATUS_CD</code> INVOICE_STATUS_CD column of table CLW_INVOICE_DIST */
    public static final String INVOICE_STATUS_CD = "INVOICE_STATUS_CD";
    /** <code>DIST_SHIPMENT_NUM</code> DIST_SHIPMENT_NUM column of table CLW_INVOICE_DIST */
    public static final String DIST_SHIPMENT_NUM = "DIST_SHIPMENT_NUM";
    /** <code>TRACKING_TYPE</code> TRACKING_TYPE column of table CLW_INVOICE_DIST */
    public static final String TRACKING_TYPE = "TRACKING_TYPE";
    /** <code>TRACKING_NUM</code> TRACKING_NUM column of table CLW_INVOICE_DIST */
    public static final String TRACKING_NUM = "TRACKING_NUM";
    /** <code>CARRIER</code> CARRIER column of table CLW_INVOICE_DIST */
    public static final String CARRIER = "CARRIER";
    /** <code>SCAC</code> SCAC column of table CLW_INVOICE_DIST */
    public static final String SCAC = "SCAC";
    /** <code>SHIP_TO_NAME</code> SHIP_TO_NAME column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_NAME = "SHIP_TO_NAME";
    /** <code>SHIP_TO_ADDRESS_1</code> SHIP_TO_ADDRESS_1 column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_ADDRESS_1 = "SHIP_TO_ADDRESS_1";
    /** <code>SHIP_TO_ADDRESS_2</code> SHIP_TO_ADDRESS_2 column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_ADDRESS_2 = "SHIP_TO_ADDRESS_2";
    /** <code>SHIP_TO_ADDRESS_3</code> SHIP_TO_ADDRESS_3 column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_ADDRESS_3 = "SHIP_TO_ADDRESS_3";
    /** <code>SHIP_TO_ADDRESS_4</code> SHIP_TO_ADDRESS_4 column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_ADDRESS_4 = "SHIP_TO_ADDRESS_4";
    /** <code>SHIP_TO_CITY</code> SHIP_TO_CITY column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_CITY = "SHIP_TO_CITY";
    /** <code>SHIP_TO_STATE</code> SHIP_TO_STATE column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_STATE = "SHIP_TO_STATE";
    /** <code>SHIP_TO_POSTAL_CODE</code> SHIP_TO_POSTAL_CODE column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_POSTAL_CODE = "SHIP_TO_POSTAL_CODE";
    /** <code>SHIP_TO_COUNTRY</code> SHIP_TO_COUNTRY column of table CLW_INVOICE_DIST */
    public static final String SHIP_TO_COUNTRY = "SHIP_TO_COUNTRY";
    /** <code>SHIP_FROM_NAME</code> SHIP_FROM_NAME column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_NAME = "SHIP_FROM_NAME";
    /** <code>SHIP_FROM_ADDRESS_1</code> SHIP_FROM_ADDRESS_1 column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_ADDRESS_1 = "SHIP_FROM_ADDRESS_1";
    /** <code>SHIP_FROM_ADDRESS_2</code> SHIP_FROM_ADDRESS_2 column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_ADDRESS_2 = "SHIP_FROM_ADDRESS_2";
    /** <code>SHIP_FROM_ADDRESS_3</code> SHIP_FROM_ADDRESS_3 column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_ADDRESS_3 = "SHIP_FROM_ADDRESS_3";
    /** <code>SHIP_FROM_ADDRESS_4</code> SHIP_FROM_ADDRESS_4 column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_ADDRESS_4 = "SHIP_FROM_ADDRESS_4";
    /** <code>SHIP_FROM_CITY</code> SHIP_FROM_CITY column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_CITY = "SHIP_FROM_CITY";
    /** <code>SHIP_FROM_STATE</code> SHIP_FROM_STATE column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_STATE = "SHIP_FROM_STATE";
    /** <code>SHIP_FROM_POSTAL_CODE</code> SHIP_FROM_POSTAL_CODE column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_POSTAL_CODE = "SHIP_FROM_POSTAL_CODE";
    /** <code>SHIP_FROM_COUNTRY</code> SHIP_FROM_COUNTRY column of table CLW_INVOICE_DIST */
    public static final String SHIP_FROM_COUNTRY = "SHIP_FROM_COUNTRY";
    /** <code>SUB_TOTAL</code> SUB_TOTAL column of table CLW_INVOICE_DIST */
    public static final String SUB_TOTAL = "SUB_TOTAL";
    /** <code>FREIGHT</code> FREIGHT column of table CLW_INVOICE_DIST */
    public static final String FREIGHT = "FREIGHT";
    /** <code>SALES_TAX</code> SALES_TAX column of table CLW_INVOICE_DIST */
    public static final String SALES_TAX = "SALES_TAX";
    /** <code>DISCOUNTS</code> DISCOUNTS column of table CLW_INVOICE_DIST */
    public static final String DISCOUNTS = "DISCOUNTS";
    /** <code>MISC_CHARGES</code> MISC_CHARGES column of table CLW_INVOICE_DIST */
    public static final String MISC_CHARGES = "MISC_CHARGES";
    /** <code>CREDITS</code> CREDITS column of table CLW_INVOICE_DIST */
    public static final String CREDITS = "CREDITS";
    /** <code>BATCH_NUMBER</code> BATCH_NUMBER column of table CLW_INVOICE_DIST */
    public static final String BATCH_NUMBER = "BATCH_NUMBER";
    /** <code>BATCH_DATE</code> BATCH_DATE column of table CLW_INVOICE_DIST */
    public static final String BATCH_DATE = "BATCH_DATE";
    /** <code>BATCH_TIME</code> BATCH_TIME column of table CLW_INVOICE_DIST */
    public static final String BATCH_TIME = "BATCH_TIME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVOICE_DIST */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVOICE_DIST */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVOICE_DIST */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVOICE_DIST */
    public static final String MOD_BY = "MOD_BY";
    /** <code>INVOICE_DIST_SOURCE_CD</code> INVOICE_DIST_SOURCE_CD column of table CLW_INVOICE_DIST */
    public static final String INVOICE_DIST_SOURCE_CD = "INVOICE_DIST_SOURCE_CD";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_INVOICE_DIST */
    public static final String STORE_ID = "STORE_ID";
    /** <code>ERP_SYSTEM_CD</code> ERP_SYSTEM_CD column of table CLW_INVOICE_DIST */
    public static final String ERP_SYSTEM_CD = "ERP_SYSTEM_CD";
    /** <code>REMIT_TO</code> REMIT_TO column of table CLW_INVOICE_DIST */
    public static final String REMIT_TO = "REMIT_TO";
    /** <code>ERP_PO_REF_NUM</code> ERP_PO_REF_NUM column of table CLW_INVOICE_DIST */
    public static final String ERP_PO_REF_NUM = "ERP_PO_REF_NUM";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_INVOICE_DIST */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_INVOICE_DIST */
    public static final String SITE_ID = "SITE_ID";

    /**
     * Constructor.
     */
    public InvoiceDistDataAccess()
    {
    }

    /**
     * Gets a InvoiceDistData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInvoiceDistId The key requested.
     * @return new InvoiceDistData()
     * @throws            SQLException
     */
    public static InvoiceDistData select(Connection pCon, int pInvoiceDistId)
        throws SQLException, DataNotFoundException {
        InvoiceDistData x=null;
        String sql="SELECT INVOICE_DIST_ID,BUS_ENTITY_ID,ORDER_ID,ERP_PO_NUM,DIST_ORDER_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,DIST_SHIPMENT_NUM,TRACKING_TYPE,TRACKING_NUM,CARRIER,SCAC,SHIP_TO_NAME,SHIP_TO_ADDRESS_1,SHIP_TO_ADDRESS_2,SHIP_TO_ADDRESS_3,SHIP_TO_ADDRESS_4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY,SHIP_FROM_NAME,SHIP_FROM_ADDRESS_1,SHIP_FROM_ADDRESS_2,SHIP_FROM_ADDRESS_3,SHIP_FROM_ADDRESS_4,SHIP_FROM_CITY,SHIP_FROM_STATE,SHIP_FROM_POSTAL_CODE,SHIP_FROM_COUNTRY,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,INVOICE_DIST_SOURCE_CD,STORE_ID,ERP_SYSTEM_CD,REMIT_TO,ERP_PO_REF_NUM,ACCOUNT_ID,SITE_ID FROM CLW_INVOICE_DIST WHERE INVOICE_DIST_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInvoiceDistId=" + pInvoiceDistId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInvoiceDistId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InvoiceDistData.createValue();
            
            x.setInvoiceDistId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setOrderId(rs.getInt(3));
            x.setErpPoNum(rs.getString(4));
            x.setDistOrderNum(rs.getString(5));
            x.setInvoiceNum(rs.getString(6));
            x.setInvoiceDate(rs.getDate(7));
            x.setInvoiceStatusCd(rs.getString(8));
            x.setDistShipmentNum(rs.getString(9));
            x.setTrackingType(rs.getString(10));
            x.setTrackingNum(rs.getString(11));
            x.setCarrier(rs.getString(12));
            x.setScac(rs.getString(13));
            x.setShipToName(rs.getString(14));
            x.setShipToAddress1(rs.getString(15));
            x.setShipToAddress2(rs.getString(16));
            x.setShipToAddress3(rs.getString(17));
            x.setShipToAddress4(rs.getString(18));
            x.setShipToCity(rs.getString(19));
            x.setShipToState(rs.getString(20));
            x.setShipToPostalCode(rs.getString(21));
            x.setShipToCountry(rs.getString(22));
            x.setShipFromName(rs.getString(23));
            x.setShipFromAddress1(rs.getString(24));
            x.setShipFromAddress2(rs.getString(25));
            x.setShipFromAddress3(rs.getString(26));
            x.setShipFromAddress4(rs.getString(27));
            x.setShipFromCity(rs.getString(28));
            x.setShipFromState(rs.getString(29));
            x.setShipFromPostalCode(rs.getString(30));
            x.setShipFromCountry(rs.getString(31));
            x.setSubTotal(rs.getBigDecimal(32));
            x.setFreight(rs.getBigDecimal(33));
            x.setSalesTax(rs.getBigDecimal(34));
            x.setDiscounts(rs.getBigDecimal(35));
            x.setMiscCharges(rs.getBigDecimal(36));
            x.setCredits(rs.getBigDecimal(37));
            x.setBatchNumber(rs.getInt(38));
            x.setBatchDate(rs.getDate(39));
            x.setBatchTime(rs.getTimestamp(40));
            x.setAddDate(rs.getTimestamp(41));
            x.setAddBy(rs.getString(42));
            x.setModDate(rs.getTimestamp(43));
            x.setModBy(rs.getString(44));
            x.setInvoiceDistSourceCd(rs.getString(45));
            x.setStoreId(rs.getInt(46));
            x.setErpSystemCd(rs.getString(47));
            x.setRemitTo(rs.getString(48));
            x.setErpPoRefNum(rs.getString(49));
            x.setAccountId(rs.getInt(50));
            x.setSiteId(rs.getInt(51));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVOICE_DIST_ID :" + pInvoiceDistId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InvoiceDistDataVector object that consists
     * of InvoiceDistData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InvoiceDistDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InvoiceDistData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVOICE_DIST.INVOICE_DIST_ID,CLW_INVOICE_DIST.BUS_ENTITY_ID,CLW_INVOICE_DIST.ORDER_ID,CLW_INVOICE_DIST.ERP_PO_NUM,CLW_INVOICE_DIST.DIST_ORDER_NUM,CLW_INVOICE_DIST.INVOICE_NUM,CLW_INVOICE_DIST.INVOICE_DATE,CLW_INVOICE_DIST.INVOICE_STATUS_CD,CLW_INVOICE_DIST.DIST_SHIPMENT_NUM,CLW_INVOICE_DIST.TRACKING_TYPE,CLW_INVOICE_DIST.TRACKING_NUM,CLW_INVOICE_DIST.CARRIER,CLW_INVOICE_DIST.SCAC,CLW_INVOICE_DIST.SHIP_TO_NAME,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_1,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_2,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_3,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_4,CLW_INVOICE_DIST.SHIP_TO_CITY,CLW_INVOICE_DIST.SHIP_TO_STATE,CLW_INVOICE_DIST.SHIP_TO_POSTAL_CODE,CLW_INVOICE_DIST.SHIP_TO_COUNTRY,CLW_INVOICE_DIST.SHIP_FROM_NAME,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_1,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_2,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_3,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_4,CLW_INVOICE_DIST.SHIP_FROM_CITY,CLW_INVOICE_DIST.SHIP_FROM_STATE,CLW_INVOICE_DIST.SHIP_FROM_POSTAL_CODE,CLW_INVOICE_DIST.SHIP_FROM_COUNTRY,CLW_INVOICE_DIST.SUB_TOTAL,CLW_INVOICE_DIST.FREIGHT,CLW_INVOICE_DIST.SALES_TAX,CLW_INVOICE_DIST.DISCOUNTS,CLW_INVOICE_DIST.MISC_CHARGES,CLW_INVOICE_DIST.CREDITS,CLW_INVOICE_DIST.BATCH_NUMBER,CLW_INVOICE_DIST.BATCH_DATE,CLW_INVOICE_DIST.BATCH_TIME,CLW_INVOICE_DIST.ADD_DATE,CLW_INVOICE_DIST.ADD_BY,CLW_INVOICE_DIST.MOD_DATE,CLW_INVOICE_DIST.MOD_BY,CLW_INVOICE_DIST.INVOICE_DIST_SOURCE_CD,CLW_INVOICE_DIST.STORE_ID,CLW_INVOICE_DIST.ERP_SYSTEM_CD,CLW_INVOICE_DIST.REMIT_TO,CLW_INVOICE_DIST.ERP_PO_REF_NUM,CLW_INVOICE_DIST.ACCOUNT_ID,CLW_INVOICE_DIST.SITE_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InvoiceDistData Object.
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
    *@returns a populated InvoiceDistData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InvoiceDistData x = InvoiceDistData.createValue();
         
         x.setInvoiceDistId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setOrderId(rs.getInt(3+offset));
         x.setErpPoNum(rs.getString(4+offset));
         x.setDistOrderNum(rs.getString(5+offset));
         x.setInvoiceNum(rs.getString(6+offset));
         x.setInvoiceDate(rs.getDate(7+offset));
         x.setInvoiceStatusCd(rs.getString(8+offset));
         x.setDistShipmentNum(rs.getString(9+offset));
         x.setTrackingType(rs.getString(10+offset));
         x.setTrackingNum(rs.getString(11+offset));
         x.setCarrier(rs.getString(12+offset));
         x.setScac(rs.getString(13+offset));
         x.setShipToName(rs.getString(14+offset));
         x.setShipToAddress1(rs.getString(15+offset));
         x.setShipToAddress2(rs.getString(16+offset));
         x.setShipToAddress3(rs.getString(17+offset));
         x.setShipToAddress4(rs.getString(18+offset));
         x.setShipToCity(rs.getString(19+offset));
         x.setShipToState(rs.getString(20+offset));
         x.setShipToPostalCode(rs.getString(21+offset));
         x.setShipToCountry(rs.getString(22+offset));
         x.setShipFromName(rs.getString(23+offset));
         x.setShipFromAddress1(rs.getString(24+offset));
         x.setShipFromAddress2(rs.getString(25+offset));
         x.setShipFromAddress3(rs.getString(26+offset));
         x.setShipFromAddress4(rs.getString(27+offset));
         x.setShipFromCity(rs.getString(28+offset));
         x.setShipFromState(rs.getString(29+offset));
         x.setShipFromPostalCode(rs.getString(30+offset));
         x.setShipFromCountry(rs.getString(31+offset));
         x.setSubTotal(rs.getBigDecimal(32+offset));
         x.setFreight(rs.getBigDecimal(33+offset));
         x.setSalesTax(rs.getBigDecimal(34+offset));
         x.setDiscounts(rs.getBigDecimal(35+offset));
         x.setMiscCharges(rs.getBigDecimal(36+offset));
         x.setCredits(rs.getBigDecimal(37+offset));
         x.setBatchNumber(rs.getInt(38+offset));
         x.setBatchDate(rs.getDate(39+offset));
         x.setBatchTime(rs.getTimestamp(40+offset));
         x.setAddDate(rs.getTimestamp(41+offset));
         x.setAddBy(rs.getString(42+offset));
         x.setModDate(rs.getTimestamp(43+offset));
         x.setModBy(rs.getString(44+offset));
         x.setInvoiceDistSourceCd(rs.getString(45+offset));
         x.setStoreId(rs.getInt(46+offset));
         x.setErpSystemCd(rs.getString(47+offset));
         x.setRemitTo(rs.getString(48+offset));
         x.setErpPoRefNum(rs.getString(49+offset));
         x.setAccountId(rs.getInt(50+offset));
         x.setSiteId(rs.getInt(51+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InvoiceDistData Object represents.
    */
    public int getColumnCount(){
        return 51;
    }

    /**
     * Gets a InvoiceDistDataVector object that consists
     * of InvoiceDistData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InvoiceDistDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVOICE_DIST_ID,BUS_ENTITY_ID,ORDER_ID,ERP_PO_NUM,DIST_ORDER_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,DIST_SHIPMENT_NUM,TRACKING_TYPE,TRACKING_NUM,CARRIER,SCAC,SHIP_TO_NAME,SHIP_TO_ADDRESS_1,SHIP_TO_ADDRESS_2,SHIP_TO_ADDRESS_3,SHIP_TO_ADDRESS_4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY,SHIP_FROM_NAME,SHIP_FROM_ADDRESS_1,SHIP_FROM_ADDRESS_2,SHIP_FROM_ADDRESS_3,SHIP_FROM_ADDRESS_4,SHIP_FROM_CITY,SHIP_FROM_STATE,SHIP_FROM_POSTAL_CODE,SHIP_FROM_COUNTRY,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,INVOICE_DIST_SOURCE_CD,STORE_ID,ERP_SYSTEM_CD,REMIT_TO,ERP_PO_REF_NUM,ACCOUNT_ID,SITE_ID FROM CLW_INVOICE_DIST");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVOICE_DIST.INVOICE_DIST_ID,CLW_INVOICE_DIST.BUS_ENTITY_ID,CLW_INVOICE_DIST.ORDER_ID,CLW_INVOICE_DIST.ERP_PO_NUM,CLW_INVOICE_DIST.DIST_ORDER_NUM,CLW_INVOICE_DIST.INVOICE_NUM,CLW_INVOICE_DIST.INVOICE_DATE,CLW_INVOICE_DIST.INVOICE_STATUS_CD,CLW_INVOICE_DIST.DIST_SHIPMENT_NUM,CLW_INVOICE_DIST.TRACKING_TYPE,CLW_INVOICE_DIST.TRACKING_NUM,CLW_INVOICE_DIST.CARRIER,CLW_INVOICE_DIST.SCAC,CLW_INVOICE_DIST.SHIP_TO_NAME,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_1,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_2,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_3,CLW_INVOICE_DIST.SHIP_TO_ADDRESS_4,CLW_INVOICE_DIST.SHIP_TO_CITY,CLW_INVOICE_DIST.SHIP_TO_STATE,CLW_INVOICE_DIST.SHIP_TO_POSTAL_CODE,CLW_INVOICE_DIST.SHIP_TO_COUNTRY,CLW_INVOICE_DIST.SHIP_FROM_NAME,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_1,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_2,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_3,CLW_INVOICE_DIST.SHIP_FROM_ADDRESS_4,CLW_INVOICE_DIST.SHIP_FROM_CITY,CLW_INVOICE_DIST.SHIP_FROM_STATE,CLW_INVOICE_DIST.SHIP_FROM_POSTAL_CODE,CLW_INVOICE_DIST.SHIP_FROM_COUNTRY,CLW_INVOICE_DIST.SUB_TOTAL,CLW_INVOICE_DIST.FREIGHT,CLW_INVOICE_DIST.SALES_TAX,CLW_INVOICE_DIST.DISCOUNTS,CLW_INVOICE_DIST.MISC_CHARGES,CLW_INVOICE_DIST.CREDITS,CLW_INVOICE_DIST.BATCH_NUMBER,CLW_INVOICE_DIST.BATCH_DATE,CLW_INVOICE_DIST.BATCH_TIME,CLW_INVOICE_DIST.ADD_DATE,CLW_INVOICE_DIST.ADD_BY,CLW_INVOICE_DIST.MOD_DATE,CLW_INVOICE_DIST.MOD_BY,CLW_INVOICE_DIST.INVOICE_DIST_SOURCE_CD,CLW_INVOICE_DIST.STORE_ID,CLW_INVOICE_DIST.ERP_SYSTEM_CD,CLW_INVOICE_DIST.REMIT_TO,CLW_INVOICE_DIST.ERP_PO_REF_NUM,CLW_INVOICE_DIST.ACCOUNT_ID,CLW_INVOICE_DIST.SITE_ID FROM CLW_INVOICE_DIST");
                where = pCriteria.getSqlClause("CLW_INVOICE_DIST");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_DIST.equals(otherTable)){
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
        InvoiceDistDataVector v = new InvoiceDistDataVector();
        while (rs.next()) {
            InvoiceDistData x = InvoiceDistData.createValue();
            
            x.setInvoiceDistId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setOrderId(rs.getInt(3));
            x.setErpPoNum(rs.getString(4));
            x.setDistOrderNum(rs.getString(5));
            x.setInvoiceNum(rs.getString(6));
            x.setInvoiceDate(rs.getDate(7));
            x.setInvoiceStatusCd(rs.getString(8));
            x.setDistShipmentNum(rs.getString(9));
            x.setTrackingType(rs.getString(10));
            x.setTrackingNum(rs.getString(11));
            x.setCarrier(rs.getString(12));
            x.setScac(rs.getString(13));
            x.setShipToName(rs.getString(14));
            x.setShipToAddress1(rs.getString(15));
            x.setShipToAddress2(rs.getString(16));
            x.setShipToAddress3(rs.getString(17));
            x.setShipToAddress4(rs.getString(18));
            x.setShipToCity(rs.getString(19));
            x.setShipToState(rs.getString(20));
            x.setShipToPostalCode(rs.getString(21));
            x.setShipToCountry(rs.getString(22));
            x.setShipFromName(rs.getString(23));
            x.setShipFromAddress1(rs.getString(24));
            x.setShipFromAddress2(rs.getString(25));
            x.setShipFromAddress3(rs.getString(26));
            x.setShipFromAddress4(rs.getString(27));
            x.setShipFromCity(rs.getString(28));
            x.setShipFromState(rs.getString(29));
            x.setShipFromPostalCode(rs.getString(30));
            x.setShipFromCountry(rs.getString(31));
            x.setSubTotal(rs.getBigDecimal(32));
            x.setFreight(rs.getBigDecimal(33));
            x.setSalesTax(rs.getBigDecimal(34));
            x.setDiscounts(rs.getBigDecimal(35));
            x.setMiscCharges(rs.getBigDecimal(36));
            x.setCredits(rs.getBigDecimal(37));
            x.setBatchNumber(rs.getInt(38));
            x.setBatchDate(rs.getDate(39));
            x.setBatchTime(rs.getTimestamp(40));
            x.setAddDate(rs.getTimestamp(41));
            x.setAddBy(rs.getString(42));
            x.setModDate(rs.getTimestamp(43));
            x.setModBy(rs.getString(44));
            x.setInvoiceDistSourceCd(rs.getString(45));
            x.setStoreId(rs.getInt(46));
            x.setErpSystemCd(rs.getString(47));
            x.setRemitTo(rs.getString(48));
            x.setErpPoRefNum(rs.getString(49));
            x.setAccountId(rs.getInt(50));
            x.setSiteId(rs.getInt(51));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InvoiceDistDataVector object that consists
     * of InvoiceDistData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InvoiceDistData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InvoiceDistDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InvoiceDistDataVector v = new InvoiceDistDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_DIST_ID,BUS_ENTITY_ID,ORDER_ID,ERP_PO_NUM,DIST_ORDER_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,DIST_SHIPMENT_NUM,TRACKING_TYPE,TRACKING_NUM,CARRIER,SCAC,SHIP_TO_NAME,SHIP_TO_ADDRESS_1,SHIP_TO_ADDRESS_2,SHIP_TO_ADDRESS_3,SHIP_TO_ADDRESS_4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY,SHIP_FROM_NAME,SHIP_FROM_ADDRESS_1,SHIP_FROM_ADDRESS_2,SHIP_FROM_ADDRESS_3,SHIP_FROM_ADDRESS_4,SHIP_FROM_CITY,SHIP_FROM_STATE,SHIP_FROM_POSTAL_CODE,SHIP_FROM_COUNTRY,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,INVOICE_DIST_SOURCE_CD,STORE_ID,ERP_SYSTEM_CD,REMIT_TO,ERP_PO_REF_NUM,ACCOUNT_ID,SITE_ID FROM CLW_INVOICE_DIST WHERE INVOICE_DIST_ID IN (");

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
            InvoiceDistData x=null;
            while (rs.next()) {
                // build the object
                x=InvoiceDistData.createValue();
                
                x.setInvoiceDistId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setOrderId(rs.getInt(3));
                x.setErpPoNum(rs.getString(4));
                x.setDistOrderNum(rs.getString(5));
                x.setInvoiceNum(rs.getString(6));
                x.setInvoiceDate(rs.getDate(7));
                x.setInvoiceStatusCd(rs.getString(8));
                x.setDistShipmentNum(rs.getString(9));
                x.setTrackingType(rs.getString(10));
                x.setTrackingNum(rs.getString(11));
                x.setCarrier(rs.getString(12));
                x.setScac(rs.getString(13));
                x.setShipToName(rs.getString(14));
                x.setShipToAddress1(rs.getString(15));
                x.setShipToAddress2(rs.getString(16));
                x.setShipToAddress3(rs.getString(17));
                x.setShipToAddress4(rs.getString(18));
                x.setShipToCity(rs.getString(19));
                x.setShipToState(rs.getString(20));
                x.setShipToPostalCode(rs.getString(21));
                x.setShipToCountry(rs.getString(22));
                x.setShipFromName(rs.getString(23));
                x.setShipFromAddress1(rs.getString(24));
                x.setShipFromAddress2(rs.getString(25));
                x.setShipFromAddress3(rs.getString(26));
                x.setShipFromAddress4(rs.getString(27));
                x.setShipFromCity(rs.getString(28));
                x.setShipFromState(rs.getString(29));
                x.setShipFromPostalCode(rs.getString(30));
                x.setShipFromCountry(rs.getString(31));
                x.setSubTotal(rs.getBigDecimal(32));
                x.setFreight(rs.getBigDecimal(33));
                x.setSalesTax(rs.getBigDecimal(34));
                x.setDiscounts(rs.getBigDecimal(35));
                x.setMiscCharges(rs.getBigDecimal(36));
                x.setCredits(rs.getBigDecimal(37));
                x.setBatchNumber(rs.getInt(38));
                x.setBatchDate(rs.getDate(39));
                x.setBatchTime(rs.getTimestamp(40));
                x.setAddDate(rs.getTimestamp(41));
                x.setAddBy(rs.getString(42));
                x.setModDate(rs.getTimestamp(43));
                x.setModBy(rs.getString(44));
                x.setInvoiceDistSourceCd(rs.getString(45));
                x.setStoreId(rs.getInt(46));
                x.setErpSystemCd(rs.getString(47));
                x.setRemitTo(rs.getString(48));
                x.setErpPoRefNum(rs.getString(49));
                x.setAccountId(rs.getInt(50));
                x.setSiteId(rs.getInt(51));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InvoiceDistDataVector object of all
     * InvoiceDistData objects in the database.
     * @param pCon An open database connection.
     * @return new InvoiceDistDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVOICE_DIST_ID,BUS_ENTITY_ID,ORDER_ID,ERP_PO_NUM,DIST_ORDER_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,DIST_SHIPMENT_NUM,TRACKING_TYPE,TRACKING_NUM,CARRIER,SCAC,SHIP_TO_NAME,SHIP_TO_ADDRESS_1,SHIP_TO_ADDRESS_2,SHIP_TO_ADDRESS_3,SHIP_TO_ADDRESS_4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY,SHIP_FROM_NAME,SHIP_FROM_ADDRESS_1,SHIP_FROM_ADDRESS_2,SHIP_FROM_ADDRESS_3,SHIP_FROM_ADDRESS_4,SHIP_FROM_CITY,SHIP_FROM_STATE,SHIP_FROM_POSTAL_CODE,SHIP_FROM_COUNTRY,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,INVOICE_DIST_SOURCE_CD,STORE_ID,ERP_SYSTEM_CD,REMIT_TO,ERP_PO_REF_NUM,ACCOUNT_ID,SITE_ID FROM CLW_INVOICE_DIST";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InvoiceDistDataVector v = new InvoiceDistDataVector();
        InvoiceDistData x = null;
        while (rs.next()) {
            // build the object
            x = InvoiceDistData.createValue();
            
            x.setInvoiceDistId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setOrderId(rs.getInt(3));
            x.setErpPoNum(rs.getString(4));
            x.setDistOrderNum(rs.getString(5));
            x.setInvoiceNum(rs.getString(6));
            x.setInvoiceDate(rs.getDate(7));
            x.setInvoiceStatusCd(rs.getString(8));
            x.setDistShipmentNum(rs.getString(9));
            x.setTrackingType(rs.getString(10));
            x.setTrackingNum(rs.getString(11));
            x.setCarrier(rs.getString(12));
            x.setScac(rs.getString(13));
            x.setShipToName(rs.getString(14));
            x.setShipToAddress1(rs.getString(15));
            x.setShipToAddress2(rs.getString(16));
            x.setShipToAddress3(rs.getString(17));
            x.setShipToAddress4(rs.getString(18));
            x.setShipToCity(rs.getString(19));
            x.setShipToState(rs.getString(20));
            x.setShipToPostalCode(rs.getString(21));
            x.setShipToCountry(rs.getString(22));
            x.setShipFromName(rs.getString(23));
            x.setShipFromAddress1(rs.getString(24));
            x.setShipFromAddress2(rs.getString(25));
            x.setShipFromAddress3(rs.getString(26));
            x.setShipFromAddress4(rs.getString(27));
            x.setShipFromCity(rs.getString(28));
            x.setShipFromState(rs.getString(29));
            x.setShipFromPostalCode(rs.getString(30));
            x.setShipFromCountry(rs.getString(31));
            x.setSubTotal(rs.getBigDecimal(32));
            x.setFreight(rs.getBigDecimal(33));
            x.setSalesTax(rs.getBigDecimal(34));
            x.setDiscounts(rs.getBigDecimal(35));
            x.setMiscCharges(rs.getBigDecimal(36));
            x.setCredits(rs.getBigDecimal(37));
            x.setBatchNumber(rs.getInt(38));
            x.setBatchDate(rs.getDate(39));
            x.setBatchTime(rs.getTimestamp(40));
            x.setAddDate(rs.getTimestamp(41));
            x.setAddBy(rs.getString(42));
            x.setModDate(rs.getTimestamp(43));
            x.setModBy(rs.getString(44));
            x.setInvoiceDistSourceCd(rs.getString(45));
            x.setStoreId(rs.getInt(46));
            x.setErpSystemCd(rs.getString(47));
            x.setRemitTo(rs.getString(48));
            x.setErpPoRefNum(rs.getString(49));
            x.setAccountId(rs.getInt(50));
            x.setSiteId(rs.getInt(51));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InvoiceDistData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_DIST_ID FROM CLW_INVOICE_DIST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_DIST");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_DIST");
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
     * Inserts a InvoiceDistData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InvoiceDistData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceDistData insert(Connection pCon, InvoiceDistData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVOICE_DIST_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVOICE_DIST_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInvoiceDistId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVOICE_DIST (INVOICE_DIST_ID,BUS_ENTITY_ID,ORDER_ID,ERP_PO_NUM,DIST_ORDER_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,DIST_SHIPMENT_NUM,TRACKING_TYPE,TRACKING_NUM,CARRIER,SCAC,SHIP_TO_NAME,SHIP_TO_ADDRESS_1,SHIP_TO_ADDRESS_2,SHIP_TO_ADDRESS_3,SHIP_TO_ADDRESS_4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY,SHIP_FROM_NAME,SHIP_FROM_ADDRESS_1,SHIP_FROM_ADDRESS_2,SHIP_FROM_ADDRESS_3,SHIP_FROM_ADDRESS_4,SHIP_FROM_CITY,SHIP_FROM_STATE,SHIP_FROM_POSTAL_CODE,SHIP_FROM_COUNTRY,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,INVOICE_DIST_SOURCE_CD,STORE_ID,ERP_SYSTEM_CD,REMIT_TO,ERP_PO_REF_NUM,ACCOUNT_ID,SITE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInvoiceDistId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getBusEntityId());
        }

        if (pData.getOrderId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getOrderId());
        }

        pstmt.setString(4,pData.getErpPoNum());
        pstmt.setString(5,pData.getDistOrderNum());
        pstmt.setString(6,pData.getInvoiceNum());
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(8,pData.getInvoiceStatusCd());
        pstmt.setString(9,pData.getDistShipmentNum());
        pstmt.setString(10,pData.getTrackingType());
        pstmt.setString(11,pData.getTrackingNum());
        pstmt.setString(12,pData.getCarrier());
        pstmt.setString(13,pData.getScac());
        pstmt.setString(14,pData.getShipToName());
        pstmt.setString(15,pData.getShipToAddress1());
        pstmt.setString(16,pData.getShipToAddress2());
        pstmt.setString(17,pData.getShipToAddress3());
        pstmt.setString(18,pData.getShipToAddress4());
        pstmt.setString(19,pData.getShipToCity());
        pstmt.setString(20,pData.getShipToState());
        pstmt.setString(21,pData.getShipToPostalCode());
        pstmt.setString(22,pData.getShipToCountry());
        pstmt.setString(23,pData.getShipFromName());
        pstmt.setString(24,pData.getShipFromAddress1());
        pstmt.setString(25,pData.getShipFromAddress2());
        pstmt.setString(26,pData.getShipFromAddress3());
        pstmt.setString(27,pData.getShipFromAddress4());
        pstmt.setString(28,pData.getShipFromCity());
        pstmt.setString(29,pData.getShipFromState());
        pstmt.setString(30,pData.getShipFromPostalCode());
        pstmt.setString(31,pData.getShipFromCountry());
        pstmt.setBigDecimal(32,pData.getSubTotal());
        pstmt.setBigDecimal(33,pData.getFreight());
        pstmt.setBigDecimal(34,pData.getSalesTax());
        pstmt.setBigDecimal(35,pData.getDiscounts());
        pstmt.setBigDecimal(36,pData.getMiscCharges());
        pstmt.setBigDecimal(37,pData.getCredits());
        pstmt.setInt(38,pData.getBatchNumber());
        pstmt.setDate(39,DBAccess.toSQLDate(pData.getBatchDate()));
        pstmt.setTimestamp(40,DBAccess.toSQLTimestamp(pData.getBatchTime()));
        pstmt.setTimestamp(41,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(42,pData.getAddBy());
        pstmt.setTimestamp(43,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(44,pData.getModBy());
        pstmt.setString(45,pData.getInvoiceDistSourceCd());
        pstmt.setInt(46,pData.getStoreId());
        pstmt.setString(47,pData.getErpSystemCd());
        pstmt.setString(48,pData.getRemitTo());
        pstmt.setString(49,pData.getErpPoRefNum());
        pstmt.setInt(50,pData.getAccountId());
        pstmt.setInt(51,pData.getSiteId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   DIST_ORDER_NUM="+pData.getDistOrderNum());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   INVOICE_STATUS_CD="+pData.getInvoiceStatusCd());
            log.debug("SQL:   DIST_SHIPMENT_NUM="+pData.getDistShipmentNum());
            log.debug("SQL:   TRACKING_TYPE="+pData.getTrackingType());
            log.debug("SQL:   TRACKING_NUM="+pData.getTrackingNum());
            log.debug("SQL:   CARRIER="+pData.getCarrier());
            log.debug("SQL:   SCAC="+pData.getScac());
            log.debug("SQL:   SHIP_TO_NAME="+pData.getShipToName());
            log.debug("SQL:   SHIP_TO_ADDRESS_1="+pData.getShipToAddress1());
            log.debug("SQL:   SHIP_TO_ADDRESS_2="+pData.getShipToAddress2());
            log.debug("SQL:   SHIP_TO_ADDRESS_3="+pData.getShipToAddress3());
            log.debug("SQL:   SHIP_TO_ADDRESS_4="+pData.getShipToAddress4());
            log.debug("SQL:   SHIP_TO_CITY="+pData.getShipToCity());
            log.debug("SQL:   SHIP_TO_STATE="+pData.getShipToState());
            log.debug("SQL:   SHIP_TO_POSTAL_CODE="+pData.getShipToPostalCode());
            log.debug("SQL:   SHIP_TO_COUNTRY="+pData.getShipToCountry());
            log.debug("SQL:   SHIP_FROM_NAME="+pData.getShipFromName());
            log.debug("SQL:   SHIP_FROM_ADDRESS_1="+pData.getShipFromAddress1());
            log.debug("SQL:   SHIP_FROM_ADDRESS_2="+pData.getShipFromAddress2());
            log.debug("SQL:   SHIP_FROM_ADDRESS_3="+pData.getShipFromAddress3());
            log.debug("SQL:   SHIP_FROM_ADDRESS_4="+pData.getShipFromAddress4());
            log.debug("SQL:   SHIP_FROM_CITY="+pData.getShipFromCity());
            log.debug("SQL:   SHIP_FROM_STATE="+pData.getShipFromState());
            log.debug("SQL:   SHIP_FROM_POSTAL_CODE="+pData.getShipFromPostalCode());
            log.debug("SQL:   SHIP_FROM_COUNTRY="+pData.getShipFromCountry());
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
            log.debug("SQL:   INVOICE_DIST_SOURCE_CD="+pData.getInvoiceDistSourceCd());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   REMIT_TO="+pData.getRemitTo());
            log.debug("SQL:   ERP_PO_REF_NUM="+pData.getErpPoRefNum());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInvoiceDistId(0);
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
     * Updates a InvoiceDistData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceDistData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVOICE_DIST SET BUS_ENTITY_ID = ?,ORDER_ID = ?,ERP_PO_NUM = ?,DIST_ORDER_NUM = ?,INVOICE_NUM = ?,INVOICE_DATE = ?,INVOICE_STATUS_CD = ?,DIST_SHIPMENT_NUM = ?,TRACKING_TYPE = ?,TRACKING_NUM = ?,CARRIER = ?,SCAC = ?,SHIP_TO_NAME = ?,SHIP_TO_ADDRESS_1 = ?,SHIP_TO_ADDRESS_2 = ?,SHIP_TO_ADDRESS_3 = ?,SHIP_TO_ADDRESS_4 = ?,SHIP_TO_CITY = ?,SHIP_TO_STATE = ?,SHIP_TO_POSTAL_CODE = ?,SHIP_TO_COUNTRY = ?,SHIP_FROM_NAME = ?,SHIP_FROM_ADDRESS_1 = ?,SHIP_FROM_ADDRESS_2 = ?,SHIP_FROM_ADDRESS_3 = ?,SHIP_FROM_ADDRESS_4 = ?,SHIP_FROM_CITY = ?,SHIP_FROM_STATE = ?,SHIP_FROM_POSTAL_CODE = ?,SHIP_FROM_COUNTRY = ?,SUB_TOTAL = ?,FREIGHT = ?,SALES_TAX = ?,DISCOUNTS = ?,MISC_CHARGES = ?,CREDITS = ?,BATCH_NUMBER = ?,BATCH_DATE = ?,BATCH_TIME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,INVOICE_DIST_SOURCE_CD = ?,STORE_ID = ?,ERP_SYSTEM_CD = ?,REMIT_TO = ?,ERP_PO_REF_NUM = ?,ACCOUNT_ID = ?,SITE_ID = ? WHERE INVOICE_DIST_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        pstmt.setString(i++,pData.getErpPoNum());
        pstmt.setString(i++,pData.getDistOrderNum());
        pstmt.setString(i++,pData.getInvoiceNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(i++,pData.getInvoiceStatusCd());
        pstmt.setString(i++,pData.getDistShipmentNum());
        pstmt.setString(i++,pData.getTrackingType());
        pstmt.setString(i++,pData.getTrackingNum());
        pstmt.setString(i++,pData.getCarrier());
        pstmt.setString(i++,pData.getScac());
        pstmt.setString(i++,pData.getShipToName());
        pstmt.setString(i++,pData.getShipToAddress1());
        pstmt.setString(i++,pData.getShipToAddress2());
        pstmt.setString(i++,pData.getShipToAddress3());
        pstmt.setString(i++,pData.getShipToAddress4());
        pstmt.setString(i++,pData.getShipToCity());
        pstmt.setString(i++,pData.getShipToState());
        pstmt.setString(i++,pData.getShipToPostalCode());
        pstmt.setString(i++,pData.getShipToCountry());
        pstmt.setString(i++,pData.getShipFromName());
        pstmt.setString(i++,pData.getShipFromAddress1());
        pstmt.setString(i++,pData.getShipFromAddress2());
        pstmt.setString(i++,pData.getShipFromAddress3());
        pstmt.setString(i++,pData.getShipFromAddress4());
        pstmt.setString(i++,pData.getShipFromCity());
        pstmt.setString(i++,pData.getShipFromState());
        pstmt.setString(i++,pData.getShipFromPostalCode());
        pstmt.setString(i++,pData.getShipFromCountry());
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
        pstmt.setString(i++,pData.getInvoiceDistSourceCd());
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getErpSystemCd());
        pstmt.setString(i++,pData.getRemitTo());
        pstmt.setString(i++,pData.getErpPoRefNum());
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setInt(i++,pData.getSiteId());
        pstmt.setInt(i++,pData.getInvoiceDistId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   DIST_ORDER_NUM="+pData.getDistOrderNum());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   INVOICE_STATUS_CD="+pData.getInvoiceStatusCd());
            log.debug("SQL:   DIST_SHIPMENT_NUM="+pData.getDistShipmentNum());
            log.debug("SQL:   TRACKING_TYPE="+pData.getTrackingType());
            log.debug("SQL:   TRACKING_NUM="+pData.getTrackingNum());
            log.debug("SQL:   CARRIER="+pData.getCarrier());
            log.debug("SQL:   SCAC="+pData.getScac());
            log.debug("SQL:   SHIP_TO_NAME="+pData.getShipToName());
            log.debug("SQL:   SHIP_TO_ADDRESS_1="+pData.getShipToAddress1());
            log.debug("SQL:   SHIP_TO_ADDRESS_2="+pData.getShipToAddress2());
            log.debug("SQL:   SHIP_TO_ADDRESS_3="+pData.getShipToAddress3());
            log.debug("SQL:   SHIP_TO_ADDRESS_4="+pData.getShipToAddress4());
            log.debug("SQL:   SHIP_TO_CITY="+pData.getShipToCity());
            log.debug("SQL:   SHIP_TO_STATE="+pData.getShipToState());
            log.debug("SQL:   SHIP_TO_POSTAL_CODE="+pData.getShipToPostalCode());
            log.debug("SQL:   SHIP_TO_COUNTRY="+pData.getShipToCountry());
            log.debug("SQL:   SHIP_FROM_NAME="+pData.getShipFromName());
            log.debug("SQL:   SHIP_FROM_ADDRESS_1="+pData.getShipFromAddress1());
            log.debug("SQL:   SHIP_FROM_ADDRESS_2="+pData.getShipFromAddress2());
            log.debug("SQL:   SHIP_FROM_ADDRESS_3="+pData.getShipFromAddress3());
            log.debug("SQL:   SHIP_FROM_ADDRESS_4="+pData.getShipFromAddress4());
            log.debug("SQL:   SHIP_FROM_CITY="+pData.getShipFromCity());
            log.debug("SQL:   SHIP_FROM_STATE="+pData.getShipFromState());
            log.debug("SQL:   SHIP_FROM_POSTAL_CODE="+pData.getShipFromPostalCode());
            log.debug("SQL:   SHIP_FROM_COUNTRY="+pData.getShipFromCountry());
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
            log.debug("SQL:   INVOICE_DIST_SOURCE_CD="+pData.getInvoiceDistSourceCd());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   REMIT_TO="+pData.getRemitTo());
            log.debug("SQL:   ERP_PO_REF_NUM="+pData.getErpPoRefNum());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InvoiceDistData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceDistId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceDistId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVOICE_DIST WHERE INVOICE_DIST_ID = " + pInvoiceDistId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InvoiceDistData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVOICE_DIST");
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
     * Inserts a InvoiceDistData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InvoiceDistData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVOICE_DIST (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVOICE_DIST_ID,BUS_ENTITY_ID,ORDER_ID,ERP_PO_NUM,DIST_ORDER_NUM,INVOICE_NUM,INVOICE_DATE,INVOICE_STATUS_CD,DIST_SHIPMENT_NUM,TRACKING_TYPE,TRACKING_NUM,CARRIER,SCAC,SHIP_TO_NAME,SHIP_TO_ADDRESS_1,SHIP_TO_ADDRESS_2,SHIP_TO_ADDRESS_3,SHIP_TO_ADDRESS_4,SHIP_TO_CITY,SHIP_TO_STATE,SHIP_TO_POSTAL_CODE,SHIP_TO_COUNTRY,SHIP_FROM_NAME,SHIP_FROM_ADDRESS_1,SHIP_FROM_ADDRESS_2,SHIP_FROM_ADDRESS_3,SHIP_FROM_ADDRESS_4,SHIP_FROM_CITY,SHIP_FROM_STATE,SHIP_FROM_POSTAL_CODE,SHIP_FROM_COUNTRY,SUB_TOTAL,FREIGHT,SALES_TAX,DISCOUNTS,MISC_CHARGES,CREDITS,BATCH_NUMBER,BATCH_DATE,BATCH_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,INVOICE_DIST_SOURCE_CD,STORE_ID,ERP_SYSTEM_CD,REMIT_TO,ERP_PO_REF_NUM,ACCOUNT_ID,SITE_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInvoiceDistId());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getBusEntityId());
        }

        if (pData.getOrderId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getOrderId());
        }

        pstmt.setString(4+4,pData.getErpPoNum());
        pstmt.setString(5+4,pData.getDistOrderNum());
        pstmt.setString(6+4,pData.getInvoiceNum());
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(8+4,pData.getInvoiceStatusCd());
        pstmt.setString(9+4,pData.getDistShipmentNum());
        pstmt.setString(10+4,pData.getTrackingType());
        pstmt.setString(11+4,pData.getTrackingNum());
        pstmt.setString(12+4,pData.getCarrier());
        pstmt.setString(13+4,pData.getScac());
        pstmt.setString(14+4,pData.getShipToName());
        pstmt.setString(15+4,pData.getShipToAddress1());
        pstmt.setString(16+4,pData.getShipToAddress2());
        pstmt.setString(17+4,pData.getShipToAddress3());
        pstmt.setString(18+4,pData.getShipToAddress4());
        pstmt.setString(19+4,pData.getShipToCity());
        pstmt.setString(20+4,pData.getShipToState());
        pstmt.setString(21+4,pData.getShipToPostalCode());
        pstmt.setString(22+4,pData.getShipToCountry());
        pstmt.setString(23+4,pData.getShipFromName());
        pstmt.setString(24+4,pData.getShipFromAddress1());
        pstmt.setString(25+4,pData.getShipFromAddress2());
        pstmt.setString(26+4,pData.getShipFromAddress3());
        pstmt.setString(27+4,pData.getShipFromAddress4());
        pstmt.setString(28+4,pData.getShipFromCity());
        pstmt.setString(29+4,pData.getShipFromState());
        pstmt.setString(30+4,pData.getShipFromPostalCode());
        pstmt.setString(31+4,pData.getShipFromCountry());
        pstmt.setBigDecimal(32+4,pData.getSubTotal());
        pstmt.setBigDecimal(33+4,pData.getFreight());
        pstmt.setBigDecimal(34+4,pData.getSalesTax());
        pstmt.setBigDecimal(35+4,pData.getDiscounts());
        pstmt.setBigDecimal(36+4,pData.getMiscCharges());
        pstmt.setBigDecimal(37+4,pData.getCredits());
        pstmt.setInt(38+4,pData.getBatchNumber());
        pstmt.setDate(39+4,DBAccess.toSQLDate(pData.getBatchDate()));
        pstmt.setTimestamp(40+4,DBAccess.toSQLTimestamp(pData.getBatchTime()));
        pstmt.setTimestamp(41+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(42+4,pData.getAddBy());
        pstmt.setTimestamp(43+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(44+4,pData.getModBy());
        pstmt.setString(45+4,pData.getInvoiceDistSourceCd());
        pstmt.setInt(46+4,pData.getStoreId());
        pstmt.setString(47+4,pData.getErpSystemCd());
        pstmt.setString(48+4,pData.getRemitTo());
        pstmt.setString(49+4,pData.getErpPoRefNum());
        pstmt.setInt(50+4,pData.getAccountId());
        pstmt.setInt(51+4,pData.getSiteId());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InvoiceDistData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InvoiceDistData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceDistData insert(Connection pCon, InvoiceDistData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InvoiceDistData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceDistData pData, boolean pLogFl)
        throws SQLException {
        InvoiceDistData oldData = null;
        if(pLogFl) {
          int id = pData.getInvoiceDistId();
          try {
          oldData = InvoiceDistDataAccess.select(pCon,id);
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
     * Deletes a InvoiceDistData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceDistId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceDistId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVOICE_DIST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_DIST d WHERE INVOICE_DIST_ID = " + pInvoiceDistId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInvoiceDistId);
        return n;
     }

    /**
     * Deletes InvoiceDistData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVOICE_DIST SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_DIST d ");
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

