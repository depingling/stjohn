
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
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
 * <code>OrderPropertyDataAccess</code>
 */
public class OrderPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderPropertyDataAccess.class.getName());

    /** <code>CLW_ORDER_PROPERTY</code> table name */
	/* Primary key: ORDER_PROPERTY_ID */
	
    public static final String CLW_ORDER_PROPERTY = "CLW_ORDER_PROPERTY";
    
    /** <code>ORDER_PROPERTY_ID</code> ORDER_PROPERTY_ID column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_PROPERTY_ID = "ORDER_PROPERTY_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>INVOICE_DIST_ID</code> INVOICE_DIST_ID column of table CLW_ORDER_PROPERTY */
    public static final String INVOICE_DIST_ID = "INVOICE_DIST_ID";
    /** <code>INVOICE_DIST_DETAIL_ID</code> INVOICE_DIST_DETAIL_ID column of table CLW_ORDER_PROPERTY */
    public static final String INVOICE_DIST_DETAIL_ID = "INVOICE_DIST_DETAIL_ID";
    /** <code>INVOICE_CUST_ID</code> INVOICE_CUST_ID column of table CLW_ORDER_PROPERTY */
    public static final String INVOICE_CUST_ID = "INVOICE_CUST_ID";
    /** <code>INVOICE_CUST_DETAIL_ID</code> INVOICE_CUST_DETAIL_ID column of table CLW_ORDER_PROPERTY */
    public static final String INVOICE_CUST_DETAIL_ID = "INVOICE_CUST_DETAIL_ID";
    /** <code>ORDER_ADDRESS_ID</code> ORDER_ADDRESS_ID column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_ADDRESS_ID = "ORDER_ADDRESS_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ORDER_PROPERTY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_ORDER_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ORDER_PROPERTY_STATUS_CD</code> ORDER_PROPERTY_STATUS_CD column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_PROPERTY_STATUS_CD = "ORDER_PROPERTY_STATUS_CD";
    /** <code>ORDER_PROPERTY_TYPE_CD</code> ORDER_PROPERTY_TYPE_CD column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_PROPERTY_TYPE_CD = "ORDER_PROPERTY_TYPE_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_PROPERTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ORDER_STATUS_CD</code> ORDER_STATUS_CD column of table CLW_ORDER_PROPERTY */
    public static final String ORDER_STATUS_CD = "ORDER_STATUS_CD";
    /** <code>WORKFLOW_RULE_ID</code> WORKFLOW_RULE_ID column of table CLW_ORDER_PROPERTY */
    public static final String WORKFLOW_RULE_ID = "WORKFLOW_RULE_ID";
    /** <code>APPROVE_DATE</code> APPROVE_DATE column of table CLW_ORDER_PROPERTY */
    public static final String APPROVE_DATE = "APPROVE_DATE";
    /** <code>APPROVE_USER_ID</code> APPROVE_USER_ID column of table CLW_ORDER_PROPERTY */
    public static final String APPROVE_USER_ID = "APPROVE_USER_ID";
    /** <code>MESSAGE_KEY</code> MESSAGE_KEY column of table CLW_ORDER_PROPERTY */
    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    /** <code>ARG0</code> ARG0 column of table CLW_ORDER_PROPERTY */
    public static final String ARG0 = "ARG0";
    /** <code>ARG0_TYPE_CD</code> ARG0_TYPE_CD column of table CLW_ORDER_PROPERTY */
    public static final String ARG0_TYPE_CD = "ARG0_TYPE_CD";
    /** <code>ARG1</code> ARG1 column of table CLW_ORDER_PROPERTY */
    public static final String ARG1 = "ARG1";
    /** <code>ARG1_TYPE_CD</code> ARG1_TYPE_CD column of table CLW_ORDER_PROPERTY */
    public static final String ARG1_TYPE_CD = "ARG1_TYPE_CD";
    /** <code>ARG2</code> ARG2 column of table CLW_ORDER_PROPERTY */
    public static final String ARG2 = "ARG2";
    /** <code>ARG2_TYPE_CD</code> ARG2_TYPE_CD column of table CLW_ORDER_PROPERTY */
    public static final String ARG2_TYPE_CD = "ARG2_TYPE_CD";
    /** <code>ARG3</code> ARG3 column of table CLW_ORDER_PROPERTY */
    public static final String ARG3 = "ARG3";
    /** <code>ARG3_TYPE_CD</code> ARG3_TYPE_CD column of table CLW_ORDER_PROPERTY */
    public static final String ARG3_TYPE_CD = "ARG3_TYPE_CD";
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_ORDER_PROPERTY */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";

    /**
     * Constructor.
     */
    public OrderPropertyDataAccess()
    {
    }

    /**
     * Gets a OrderPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderPropertyId The key requested.
     * @return new OrderPropertyData()
     * @throws            SQLException
     */
    public static OrderPropertyData select(Connection pCon, int pOrderPropertyId)
        throws SQLException, DataNotFoundException {
        OrderPropertyData x=null;
        String sql="SELECT ORDER_PROPERTY_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ADDRESS_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_STATUS_CD,WORKFLOW_RULE_ID,APPROVE_DATE,APPROVE_USER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,PURCHASE_ORDER_ID FROM CLW_ORDER_PROPERTY WHERE ORDER_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderPropertyId=" + pOrderPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderPropertyData.createValue();
            
            x.setOrderPropertyId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setInvoiceDistId(rs.getInt(4));
            x.setInvoiceDistDetailId(rs.getInt(5));
            x.setInvoiceCustId(rs.getInt(6));
            x.setInvoiceCustDetailId(rs.getInt(7));
            x.setOrderAddressId(rs.getInt(8));
            x.setShortDesc(rs.getString(9));
            x.setValue(rs.getString(10));
            x.setOrderPropertyStatusCd(rs.getString(11));
            x.setOrderPropertyTypeCd(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setOrderStatusCd(rs.getString(17));
            x.setWorkflowRuleId(rs.getInt(18));
            x.setApproveDate(rs.getDate(19));
            x.setApproveUserId(rs.getInt(20));
            x.setMessageKey(rs.getString(21));
            x.setArg0(rs.getString(22));
            x.setArg0TypeCd(rs.getString(23));
            x.setArg1(rs.getString(24));
            x.setArg1TypeCd(rs.getString(25));
            x.setArg2(rs.getString(26));
            x.setArg2TypeCd(rs.getString(27));
            x.setArg3(rs.getString(28));
            x.setArg3TypeCd(rs.getString(29));
            x.setPurchaseOrderId(rs.getInt(30));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_PROPERTY_ID :" + pOrderPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderPropertyDataVector object that consists
     * of OrderPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderPropertyDataVector()
     * @throws            SQLException
     */
    public static OrderPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_PROPERTY.ORDER_PROPERTY_ID,CLW_ORDER_PROPERTY.ORDER_ID,CLW_ORDER_PROPERTY.ORDER_ITEM_ID,CLW_ORDER_PROPERTY.INVOICE_DIST_ID,CLW_ORDER_PROPERTY.INVOICE_DIST_DETAIL_ID,CLW_ORDER_PROPERTY.INVOICE_CUST_ID,CLW_ORDER_PROPERTY.INVOICE_CUST_DETAIL_ID,CLW_ORDER_PROPERTY.ORDER_ADDRESS_ID,CLW_ORDER_PROPERTY.SHORT_DESC,CLW_ORDER_PROPERTY.CLW_VALUE,CLW_ORDER_PROPERTY.ORDER_PROPERTY_STATUS_CD,CLW_ORDER_PROPERTY.ORDER_PROPERTY_TYPE_CD,CLW_ORDER_PROPERTY.ADD_DATE,CLW_ORDER_PROPERTY.ADD_BY,CLW_ORDER_PROPERTY.MOD_DATE,CLW_ORDER_PROPERTY.MOD_BY,CLW_ORDER_PROPERTY.ORDER_STATUS_CD,CLW_ORDER_PROPERTY.WORKFLOW_RULE_ID,CLW_ORDER_PROPERTY.APPROVE_DATE,CLW_ORDER_PROPERTY.APPROVE_USER_ID,CLW_ORDER_PROPERTY.MESSAGE_KEY,CLW_ORDER_PROPERTY.ARG0,CLW_ORDER_PROPERTY.ARG0_TYPE_CD,CLW_ORDER_PROPERTY.ARG1,CLW_ORDER_PROPERTY.ARG1_TYPE_CD,CLW_ORDER_PROPERTY.ARG2,CLW_ORDER_PROPERTY.ARG2_TYPE_CD,CLW_ORDER_PROPERTY.ARG3,CLW_ORDER_PROPERTY.ARG3_TYPE_CD,CLW_ORDER_PROPERTY.PURCHASE_ORDER_ID";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderPropertyData Object.
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
    *@returns a populated OrderPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderPropertyData x = OrderPropertyData.createValue();
         
         x.setOrderPropertyId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setOrderItemId(rs.getInt(3+offset));
         x.setInvoiceDistId(rs.getInt(4+offset));
         x.setInvoiceDistDetailId(rs.getInt(5+offset));
         x.setInvoiceCustId(rs.getInt(6+offset));
         x.setInvoiceCustDetailId(rs.getInt(7+offset));
         x.setOrderAddressId(rs.getInt(8+offset));
         x.setShortDesc(rs.getString(9+offset));
         x.setValue(rs.getString(10+offset));
         x.setOrderPropertyStatusCd(rs.getString(11+offset));
         x.setOrderPropertyTypeCd(rs.getString(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setOrderStatusCd(rs.getString(17+offset));
         x.setWorkflowRuleId(rs.getInt(18+offset));
         x.setApproveDate(rs.getDate(19+offset));
         x.setApproveUserId(rs.getInt(20+offset));
         x.setMessageKey(rs.getString(21+offset));
         x.setArg0(rs.getString(22+offset));
         x.setArg0TypeCd(rs.getString(23+offset));
         x.setArg1(rs.getString(24+offset));
         x.setArg1TypeCd(rs.getString(25+offset));
         x.setArg2(rs.getString(26+offset));
         x.setArg2TypeCd(rs.getString(27+offset));
         x.setArg3(rs.getString(28+offset));
         x.setArg3TypeCd(rs.getString(29+offset));
         x.setPurchaseOrderId(rs.getInt(30+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderPropertyData Object represents.
    */
    public int getColumnCount(){
        return 30;
    }

    /**
     * Gets a OrderPropertyDataVector object that consists
     * of OrderPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderPropertyDataVector()
     * @throws            SQLException
     */
    public static OrderPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_PROPERTY_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ADDRESS_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_STATUS_CD,WORKFLOW_RULE_ID,APPROVE_DATE,APPROVE_USER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,PURCHASE_ORDER_ID FROM CLW_ORDER_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_PROPERTY.ORDER_PROPERTY_ID,CLW_ORDER_PROPERTY.ORDER_ID,CLW_ORDER_PROPERTY.ORDER_ITEM_ID,CLW_ORDER_PROPERTY.INVOICE_DIST_ID,CLW_ORDER_PROPERTY.INVOICE_DIST_DETAIL_ID,CLW_ORDER_PROPERTY.INVOICE_CUST_ID,CLW_ORDER_PROPERTY.INVOICE_CUST_DETAIL_ID,CLW_ORDER_PROPERTY.ORDER_ADDRESS_ID,CLW_ORDER_PROPERTY.SHORT_DESC,CLW_ORDER_PROPERTY.CLW_VALUE,CLW_ORDER_PROPERTY.ORDER_PROPERTY_STATUS_CD,CLW_ORDER_PROPERTY.ORDER_PROPERTY_TYPE_CD,CLW_ORDER_PROPERTY.ADD_DATE,CLW_ORDER_PROPERTY.ADD_BY,CLW_ORDER_PROPERTY.MOD_DATE,CLW_ORDER_PROPERTY.MOD_BY,CLW_ORDER_PROPERTY.ORDER_STATUS_CD,CLW_ORDER_PROPERTY.WORKFLOW_RULE_ID,CLW_ORDER_PROPERTY.APPROVE_DATE,CLW_ORDER_PROPERTY.APPROVE_USER_ID,CLW_ORDER_PROPERTY.MESSAGE_KEY,CLW_ORDER_PROPERTY.ARG0,CLW_ORDER_PROPERTY.ARG0_TYPE_CD,CLW_ORDER_PROPERTY.ARG1,CLW_ORDER_PROPERTY.ARG1_TYPE_CD,CLW_ORDER_PROPERTY.ARG2,CLW_ORDER_PROPERTY.ARG2_TYPE_CD,CLW_ORDER_PROPERTY.ARG3,CLW_ORDER_PROPERTY.ARG3_TYPE_CD,CLW_ORDER_PROPERTY.PURCHASE_ORDER_ID FROM CLW_ORDER_PROPERTY");
                where = pCriteria.getSqlClause("CLW_ORDER_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_PROPERTY.equals(otherTable)){
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
        OrderPropertyDataVector v = new OrderPropertyDataVector();
        while (rs.next()) {
            OrderPropertyData x = OrderPropertyData.createValue();
            
            x.setOrderPropertyId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setInvoiceDistId(rs.getInt(4));
            x.setInvoiceDistDetailId(rs.getInt(5));
            x.setInvoiceCustId(rs.getInt(6));
            x.setInvoiceCustDetailId(rs.getInt(7));
            x.setOrderAddressId(rs.getInt(8));
            x.setShortDesc(rs.getString(9));
            x.setValue(rs.getString(10));
            x.setOrderPropertyStatusCd(rs.getString(11));
            x.setOrderPropertyTypeCd(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setOrderStatusCd(rs.getString(17));
            x.setWorkflowRuleId(rs.getInt(18));
            x.setApproveDate(rs.getDate(19));
            x.setApproveUserId(rs.getInt(20));
            x.setMessageKey(rs.getString(21));
            x.setArg0(rs.getString(22));
            x.setArg0TypeCd(rs.getString(23));
            x.setArg1(rs.getString(24));
            x.setArg1TypeCd(rs.getString(25));
            x.setArg2(rs.getString(26));
            x.setArg2TypeCd(rs.getString(27));
            x.setArg3(rs.getString(28));
            x.setArg3TypeCd(rs.getString(29));
            x.setPurchaseOrderId(rs.getInt(30));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderPropertyDataVector object that consists
     * of OrderPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderPropertyDataVector()
     * @throws            SQLException
     */
    public static OrderPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderPropertyDataVector v = new OrderPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_PROPERTY_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ADDRESS_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_STATUS_CD,WORKFLOW_RULE_ID,APPROVE_DATE,APPROVE_USER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,PURCHASE_ORDER_ID FROM CLW_ORDER_PROPERTY WHERE ORDER_PROPERTY_ID IN (");

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
            OrderPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=OrderPropertyData.createValue();
                
                x.setOrderPropertyId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setOrderItemId(rs.getInt(3));
                x.setInvoiceDistId(rs.getInt(4));
                x.setInvoiceDistDetailId(rs.getInt(5));
                x.setInvoiceCustId(rs.getInt(6));
                x.setInvoiceCustDetailId(rs.getInt(7));
                x.setOrderAddressId(rs.getInt(8));
                x.setShortDesc(rs.getString(9));
                x.setValue(rs.getString(10));
                x.setOrderPropertyStatusCd(rs.getString(11));
                x.setOrderPropertyTypeCd(rs.getString(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setOrderStatusCd(rs.getString(17));
                x.setWorkflowRuleId(rs.getInt(18));
                x.setApproveDate(rs.getDate(19));
                x.setApproveUserId(rs.getInt(20));
                x.setMessageKey(rs.getString(21));
                x.setArg0(rs.getString(22));
                x.setArg0TypeCd(rs.getString(23));
                x.setArg1(rs.getString(24));
                x.setArg1TypeCd(rs.getString(25));
                x.setArg2(rs.getString(26));
                x.setArg2TypeCd(rs.getString(27));
                x.setArg3(rs.getString(28));
                x.setArg3TypeCd(rs.getString(29));
                x.setPurchaseOrderId(rs.getInt(30));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderPropertyDataVector object of all
     * OrderPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderPropertyDataVector()
     * @throws            SQLException
     */
    public static OrderPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_PROPERTY_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ADDRESS_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_STATUS_CD,WORKFLOW_RULE_ID,APPROVE_DATE,APPROVE_USER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,PURCHASE_ORDER_ID FROM CLW_ORDER_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderPropertyDataVector v = new OrderPropertyDataVector();
        OrderPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = OrderPropertyData.createValue();
            
            x.setOrderPropertyId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setInvoiceDistId(rs.getInt(4));
            x.setInvoiceDistDetailId(rs.getInt(5));
            x.setInvoiceCustId(rs.getInt(6));
            x.setInvoiceCustDetailId(rs.getInt(7));
            x.setOrderAddressId(rs.getInt(8));
            x.setShortDesc(rs.getString(9));
            x.setValue(rs.getString(10));
            x.setOrderPropertyStatusCd(rs.getString(11));
            x.setOrderPropertyTypeCd(rs.getString(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setOrderStatusCd(rs.getString(17));
            x.setWorkflowRuleId(rs.getInt(18));
            x.setApproveDate(rs.getDate(19));
            x.setApproveUserId(rs.getInt(20));
            x.setMessageKey(rs.getString(21));
            x.setArg0(rs.getString(22));
            x.setArg0TypeCd(rs.getString(23));
            x.setArg1(rs.getString(24));
            x.setArg1TypeCd(rs.getString(25));
            x.setArg2(rs.getString(26));
            x.setArg2TypeCd(rs.getString(27));
            x.setArg3(rs.getString(28));
            x.setArg3TypeCd(rs.getString(29));
            x.setPurchaseOrderId(rs.getInt(30));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_PROPERTY_ID FROM CLW_ORDER_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_PROPERTY");
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
     * Inserts a OrderPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderPropertyData insert(Connection pCon, OrderPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_PROPERTY (ORDER_PROPERTY_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ADDRESS_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_STATUS_CD,WORKFLOW_RULE_ID,APPROVE_DATE,APPROVE_USER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,PURCHASE_ORDER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderPropertyId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        if (pData.getOrderItemId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getOrderItemId());
        }

        if (pData.getInvoiceDistId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getInvoiceDistId());
        }

        pstmt.setInt(5,pData.getInvoiceDistDetailId());
        pstmt.setInt(6,pData.getInvoiceCustId());
        pstmt.setInt(7,pData.getInvoiceCustDetailId());
        pstmt.setInt(8,pData.getOrderAddressId());
        pstmt.setString(9,pData.getShortDesc());
        pstmt.setString(10,pData.getValue());
        pstmt.setString(11,pData.getOrderPropertyStatusCd());
        pstmt.setString(12,pData.getOrderPropertyTypeCd());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setString(17,pData.getOrderStatusCd());
        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(18, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(18,pData.getWorkflowRuleId());
        }

        pstmt.setDate(19,DBAccess.toSQLDate(pData.getApproveDate()));
        if (pData.getApproveUserId() == 0) {
            pstmt.setNull(20, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(20,pData.getApproveUserId());
        }

        pstmt.setString(21,pData.getMessageKey());
        pstmt.setString(22,pData.getArg0());
        pstmt.setString(23,pData.getArg0TypeCd());
        pstmt.setString(24,pData.getArg1());
        pstmt.setString(25,pData.getArg1TypeCd());
        pstmt.setString(26,pData.getArg2());
        pstmt.setString(27,pData.getArg2TypeCd());
        pstmt.setString(28,pData.getArg3());
        pstmt.setString(29,pData.getArg3TypeCd());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(30, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(30,pData.getPurchaseOrderId());
        }


        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_PROPERTY_ID="+pData.getOrderPropertyId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   INVOICE_DIST_DETAIL_ID="+pData.getInvoiceDistDetailId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   INVOICE_CUST_DETAIL_ID="+pData.getInvoiceCustDetailId());
            log.debug("SQL:   ORDER_ADDRESS_ID="+pData.getOrderAddressId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ORDER_PROPERTY_STATUS_CD="+pData.getOrderPropertyStatusCd());
            log.debug("SQL:   ORDER_PROPERTY_TYPE_CD="+pData.getOrderPropertyTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_STATUS_CD="+pData.getOrderStatusCd());
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   APPROVE_DATE="+pData.getApproveDate());
            log.debug("SQL:   APPROVE_USER_ID="+pData.getApproveUserId());
            log.debug("SQL:   MESSAGE_KEY="+pData.getMessageKey());
            log.debug("SQL:   ARG0="+pData.getArg0());
            log.debug("SQL:   ARG0_TYPE_CD="+pData.getArg0TypeCd());
            log.debug("SQL:   ARG1="+pData.getArg1());
            log.debug("SQL:   ARG1_TYPE_CD="+pData.getArg1TypeCd());
            log.debug("SQL:   ARG2="+pData.getArg2());
            log.debug("SQL:   ARG2_TYPE_CD="+pData.getArg2TypeCd());
            log.debug("SQL:   ARG3="+pData.getArg3());
            log.debug("SQL:   ARG3_TYPE_CD="+pData.getArg3TypeCd());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderPropertyId(0);
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
     * Updates a OrderPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_PROPERTY SET ORDER_ID = ?,ORDER_ITEM_ID = ?,INVOICE_DIST_ID = ?,INVOICE_DIST_DETAIL_ID = ?,INVOICE_CUST_ID = ?,INVOICE_CUST_DETAIL_ID = ?,ORDER_ADDRESS_ID = ?,SHORT_DESC = ?,CLW_VALUE = ?,ORDER_PROPERTY_STATUS_CD = ?,ORDER_PROPERTY_TYPE_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ORDER_STATUS_CD = ?,WORKFLOW_RULE_ID = ?,APPROVE_DATE = ?,APPROVE_USER_ID = ?,MESSAGE_KEY = ?,ARG0 = ?,ARG0_TYPE_CD = ?,ARG1 = ?,ARG1_TYPE_CD = ?,ARG2 = ?,ARG2_TYPE_CD = ?,ARG3 = ?,ARG3_TYPE_CD = ?,PURCHASE_ORDER_ID = ? WHERE ORDER_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        if (pData.getOrderItemId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderItemId());
        }

        if (pData.getInvoiceDistId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getInvoiceDistId());
        }

        pstmt.setInt(i++,pData.getInvoiceDistDetailId());
        pstmt.setInt(i++,pData.getInvoiceCustId());
        pstmt.setInt(i++,pData.getInvoiceCustDetailId());
        pstmt.setInt(i++,pData.getOrderAddressId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getOrderPropertyStatusCd());
        pstmt.setString(i++,pData.getOrderPropertyTypeCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getOrderStatusCd());
        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getWorkflowRuleId());
        }

        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getApproveDate()));
        if (pData.getApproveUserId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getApproveUserId());
        }

        pstmt.setString(i++,pData.getMessageKey());
        pstmt.setString(i++,pData.getArg0());
        pstmt.setString(i++,pData.getArg0TypeCd());
        pstmt.setString(i++,pData.getArg1());
        pstmt.setString(i++,pData.getArg1TypeCd());
        pstmt.setString(i++,pData.getArg2());
        pstmt.setString(i++,pData.getArg2TypeCd());
        pstmt.setString(i++,pData.getArg3());
        pstmt.setString(i++,pData.getArg3TypeCd());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getPurchaseOrderId());
        }

        pstmt.setInt(i++,pData.getOrderPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   INVOICE_DIST_DETAIL_ID="+pData.getInvoiceDistDetailId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   INVOICE_CUST_DETAIL_ID="+pData.getInvoiceCustDetailId());
            log.debug("SQL:   ORDER_ADDRESS_ID="+pData.getOrderAddressId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ORDER_PROPERTY_STATUS_CD="+pData.getOrderPropertyStatusCd());
            log.debug("SQL:   ORDER_PROPERTY_TYPE_CD="+pData.getOrderPropertyTypeCd());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_STATUS_CD="+pData.getOrderStatusCd());
            log.debug("SQL:   WORKFLOW_RULE_ID="+pData.getWorkflowRuleId());
            log.debug("SQL:   APPROVE_DATE="+pData.getApproveDate());
            log.debug("SQL:   APPROVE_USER_ID="+pData.getApproveUserId());
            log.debug("SQL:   MESSAGE_KEY="+pData.getMessageKey());
            log.debug("SQL:   ARG0="+pData.getArg0());
            log.debug("SQL:   ARG0_TYPE_CD="+pData.getArg0TypeCd());
            log.debug("SQL:   ARG1="+pData.getArg1());
            log.debug("SQL:   ARG1_TYPE_CD="+pData.getArg1TypeCd());
            log.debug("SQL:   ARG2="+pData.getArg2());
            log.debug("SQL:   ARG2_TYPE_CD="+pData.getArg2TypeCd());
            log.debug("SQL:   ARG3="+pData.getArg3());
            log.debug("SQL:   ARG3_TYPE_CD="+pData.getArg3TypeCd());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_PROPERTY WHERE ORDER_PROPERTY_ID = " + pOrderPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_PROPERTY");
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
     * Inserts a OrderPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_PROPERTY_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_DIST_ID,INVOICE_DIST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ADDRESS_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_STATUS_CD,WORKFLOW_RULE_ID,APPROVE_DATE,APPROVE_USER_ID,MESSAGE_KEY,ARG0,ARG0_TYPE_CD,ARG1,ARG1_TYPE_CD,ARG2,ARG2_TYPE_CD,ARG3,ARG3_TYPE_CD,PURCHASE_ORDER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderPropertyId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getOrderId());
        }

        if (pData.getOrderItemId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getOrderItemId());
        }

        if (pData.getInvoiceDistId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getInvoiceDistId());
        }

        pstmt.setInt(5+4,pData.getInvoiceDistDetailId());
        pstmt.setInt(6+4,pData.getInvoiceCustId());
        pstmt.setInt(7+4,pData.getInvoiceCustDetailId());
        pstmt.setInt(8+4,pData.getOrderAddressId());
        pstmt.setString(9+4,pData.getShortDesc());
        pstmt.setString(10+4,pData.getValue());
        pstmt.setString(11+4,pData.getOrderPropertyStatusCd());
        pstmt.setString(12+4,pData.getOrderPropertyTypeCd());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setString(17+4,pData.getOrderStatusCd());
        if (pData.getWorkflowRuleId() == 0) {
            pstmt.setNull(18+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(18+4,pData.getWorkflowRuleId());
        }

        pstmt.setDate(19+4,DBAccess.toSQLDate(pData.getApproveDate()));
        if (pData.getApproveUserId() == 0) {
            pstmt.setNull(20+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(20+4,pData.getApproveUserId());
        }

        pstmt.setString(21+4,pData.getMessageKey());
        pstmt.setString(22+4,pData.getArg0());
        pstmt.setString(23+4,pData.getArg0TypeCd());
        pstmt.setString(24+4,pData.getArg1());
        pstmt.setString(25+4,pData.getArg1TypeCd());
        pstmt.setString(26+4,pData.getArg2());
        pstmt.setString(27+4,pData.getArg2TypeCd());
        pstmt.setString(28+4,pData.getArg3());
        pstmt.setString(29+4,pData.getArg3TypeCd());
        if (pData.getPurchaseOrderId() == 0) {
            pstmt.setNull(30+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(30+4,pData.getPurchaseOrderId());
        }



        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderPropertyData insert(Connection pCon, OrderPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderPropertyData pData, boolean pLogFl)
        throws SQLException {
        OrderPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderPropertyId();
          try {
          oldData = OrderPropertyDataAccess.select(pCon,id);
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
     * Deletes a OrderPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_PROPERTY d WHERE ORDER_PROPERTY_ID = " + pOrderPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderPropertyId);
        return n;
     }

    /**
     * Deletes OrderPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_PROPERTY d ");
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

