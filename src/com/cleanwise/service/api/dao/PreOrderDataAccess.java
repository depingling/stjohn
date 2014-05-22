
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PreOrderDataAccess
 * Description:  This class is used to build access methods to the CLW_PRE_ORDER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PreOrderData;
import com.cleanwise.service.api.value.PreOrderDataVector;
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
 * <code>PreOrderDataAccess</code>
 */
public class PreOrderDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PreOrderDataAccess.class.getName());

    /** <code>CLW_PRE_ORDER</code> table name */
	/* Primary key: PRE_ORDER_ID */
	
    public static final String CLW_PRE_ORDER = "CLW_PRE_ORDER";
    
    /** <code>PRE_ORDER_ID</code> PRE_ORDER_ID column of table CLW_PRE_ORDER */
    public static final String PRE_ORDER_ID = "PRE_ORDER_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_PRE_ORDER */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_PRE_ORDER */
    public static final String SITE_ID = "SITE_ID";
    /** <code>SITE_NAME</code> SITE_NAME column of table CLW_PRE_ORDER */
    public static final String SITE_NAME = "SITE_NAME";
    /** <code>CONTRACT_ID</code> CONTRACT_ID column of table CLW_PRE_ORDER */
    public static final String CONTRACT_ID = "CONTRACT_ID";
    /** <code>INCOMING_PROFILE_ID</code> INCOMING_PROFILE_ID column of table CLW_PRE_ORDER */
    public static final String INCOMING_PROFILE_ID = "INCOMING_PROFILE_ID";
    /** <code>TRADING_PARTNER_ID</code> TRADING_PARTNER_ID column of table CLW_PRE_ORDER */
    public static final String TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_PRE_ORDER */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_PRE_ORDER */
    public static final String USER_ID = "USER_ID";
    /** <code>USER_NAME</code> USER_NAME column of table CLW_PRE_ORDER */
    public static final String USER_NAME = "USER_NAME";
    /** <code>SKU_TYPE_CD</code> SKU_TYPE_CD column of table CLW_PRE_ORDER */
    public static final String SKU_TYPE_CD = "SKU_TYPE_CD";
    /** <code>BILLING_ORDER</code> BILLING_ORDER column of table CLW_PRE_ORDER */
    public static final String BILLING_ORDER = "BILLING_ORDER";
    /** <code>BYPASS_ORDER_ROUTING</code> BYPASS_ORDER_ROUTING column of table CLW_PRE_ORDER */
    public static final String BYPASS_ORDER_ROUTING = "BYPASS_ORDER_ROUTING";
    /** <code>BYPASS_PRE_CAPTURE_PIPELINE</code> BYPASS_PRE_CAPTURE_PIPELINE column of table CLW_PRE_ORDER */
    public static final String BYPASS_PRE_CAPTURE_PIPELINE = "BYPASS_PRE_CAPTURE_PIPELINE";
    /** <code>FREE_FORMAT_ADDRESS</code> FREE_FORMAT_ADDRESS column of table CLW_PRE_ORDER */
    public static final String FREE_FORMAT_ADDRESS = "FREE_FORMAT_ADDRESS";
    /** <code>CUSTOMER_COMMENTS</code> CUSTOMER_COMMENTS column of table CLW_PRE_ORDER */
    public static final String CUSTOMER_COMMENTS = "CUSTOMER_COMMENTS";
    /** <code>CUSTOMER_ORDER_DATE</code> CUSTOMER_ORDER_DATE column of table CLW_PRE_ORDER */
    public static final String CUSTOMER_ORDER_DATE = "CUSTOMER_ORDER_DATE";
    /** <code>CUSTOMER_PO_NUMBER</code> CUSTOMER_PO_NUMBER column of table CLW_PRE_ORDER */
    public static final String CUSTOMER_PO_NUMBER = "CUSTOMER_PO_NUMBER";
    /** <code>FREIGHT_CHARGE</code> FREIGHT_CHARGE column of table CLW_PRE_ORDER */
    public static final String FREIGHT_CHARGE = "FREIGHT_CHARGE";
    /** <code>HANDLING_CHARGE</code> HANDLING_CHARGE column of table CLW_PRE_ORDER */
    public static final String HANDLING_CHARGE = "HANDLING_CHARGE";
    /** <code>HOLD_UNTIL_DATE</code> HOLD_UNTIL_DATE column of table CLW_PRE_ORDER */
    public static final String HOLD_UNTIL_DATE = "HOLD_UNTIL_DATE";
    /** <code>ORDER_CONTACT_NAME</code> ORDER_CONTACT_NAME column of table CLW_PRE_ORDER */
    public static final String ORDER_CONTACT_NAME = "ORDER_CONTACT_NAME";
    /** <code>ORDER_EMAIL</code> ORDER_EMAIL column of table CLW_PRE_ORDER */
    public static final String ORDER_EMAIL = "ORDER_EMAIL";
    /** <code>ORDER_FAX_NUMBER</code> ORDER_FAX_NUMBER column of table CLW_PRE_ORDER */
    public static final String ORDER_FAX_NUMBER = "ORDER_FAX_NUMBER";
    /** <code>ORDER_NOTE</code> ORDER_NOTE column of table CLW_PRE_ORDER */
    public static final String ORDER_NOTE = "ORDER_NOTE";
    /** <code>ORDER_REF_NUMBER</code> ORDER_REF_NUMBER column of table CLW_PRE_ORDER */
    public static final String ORDER_REF_NUMBER = "ORDER_REF_NUMBER";
    /** <code>ORDER_REQUESTED_SHIP_DATE</code> ORDER_REQUESTED_SHIP_DATE column of table CLW_PRE_ORDER */
    public static final String ORDER_REQUESTED_SHIP_DATE = "ORDER_REQUESTED_SHIP_DATE";
    /** <code>ORDER_SOURCE_CD</code> ORDER_SOURCE_CD column of table CLW_PRE_ORDER */
    public static final String ORDER_SOURCE_CD = "ORDER_SOURCE_CD";
    /** <code>ORDER_TELEPHONE_NUMBER</code> ORDER_TELEPHONE_NUMBER column of table CLW_PRE_ORDER */
    public static final String ORDER_TELEPHONE_NUMBER = "ORDER_TELEPHONE_NUMBER";
    /** <code>PAYMENT_TYPE</code> PAYMENT_TYPE column of table CLW_PRE_ORDER */
    public static final String PAYMENT_TYPE = "PAYMENT_TYPE";
    /** <code>ORDER_STATUS_CD_OVERIDE</code> ORDER_STATUS_CD_OVERIDE column of table CLW_PRE_ORDER */
    public static final String ORDER_STATUS_CD_OVERIDE = "ORDER_STATUS_CD_OVERIDE";
    /** <code>ACCOUNT_ERP_NUMBER_OVERIDE</code> ACCOUNT_ERP_NUMBER_OVERIDE column of table CLW_PRE_ORDER */
    public static final String ACCOUNT_ERP_NUMBER_OVERIDE = "ACCOUNT_ERP_NUMBER_OVERIDE";
    /** <code>CUSTOMER_BILLING_UNIT</code> CUSTOMER_BILLING_UNIT column of table CLW_PRE_ORDER */
    public static final String CUSTOMER_BILLING_UNIT = "CUSTOMER_BILLING_UNIT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PRE_ORDER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PRE_ORDER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PRE_ORDER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PRE_ORDER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>REF_ORDER_ID</code> REF_ORDER_ID column of table CLW_PRE_ORDER */
    public static final String REF_ORDER_ID = "REF_ORDER_ID";
    /** <code>ORDER_TYPE_CD</code> ORDER_TYPE_CD column of table CLW_PRE_ORDER */
    public static final String ORDER_TYPE_CD = "ORDER_TYPE_CD";
    /** <code>WORKFLOW_IND</code> WORKFLOW_IND column of table CLW_PRE_ORDER */
    public static final String WORKFLOW_IND = "WORKFLOW_IND";
    /** <code>USER_NAME_KEY</code> USER_NAME_KEY column of table CLW_PRE_ORDER */
    public static final String USER_NAME_KEY = "USER_NAME_KEY";
    /** <code>RUSH_CHARGE</code> RUSH_CHARGE column of table CLW_PRE_ORDER */
    public static final String RUSH_CHARGE = "RUSH_CHARGE";
    /** <code>ORDER_BUDGET_TYPE_CD</code> ORDER_BUDGET_TYPE_CD column of table CLW_PRE_ORDER */
    public static final String ORDER_BUDGET_TYPE_CD = "ORDER_BUDGET_TYPE_CD";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_PRE_ORDER */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>CURRENCY_CD</code> CURRENCY_CD column of table CLW_PRE_ORDER */
    public static final String CURRENCY_CD = "CURRENCY_CD";

    /**
     * Constructor.
     */
    public PreOrderDataAccess()
    {
    }

    /**
     * Gets a PreOrderData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPreOrderId The key requested.
     * @return new PreOrderData()
     * @throws            SQLException
     */
    public static PreOrderData select(Connection pCon, int pPreOrderId)
        throws SQLException, DataNotFoundException {
        PreOrderData x=null;
        String sql="SELECT PRE_ORDER_ID,ACCOUNT_ID,SITE_ID,SITE_NAME,CONTRACT_ID,INCOMING_PROFILE_ID,TRADING_PARTNER_ID,COST_CENTER_ID,USER_ID,USER_NAME,SKU_TYPE_CD,BILLING_ORDER,BYPASS_ORDER_ROUTING,BYPASS_PRE_CAPTURE_PIPELINE,FREE_FORMAT_ADDRESS,CUSTOMER_COMMENTS,CUSTOMER_ORDER_DATE,CUSTOMER_PO_NUMBER,FREIGHT_CHARGE,HANDLING_CHARGE,HOLD_UNTIL_DATE,ORDER_CONTACT_NAME,ORDER_EMAIL,ORDER_FAX_NUMBER,ORDER_NOTE,ORDER_REF_NUMBER,ORDER_REQUESTED_SHIP_DATE,ORDER_SOURCE_CD,ORDER_TELEPHONE_NUMBER,PAYMENT_TYPE,ORDER_STATUS_CD_OVERIDE,ACCOUNT_ERP_NUMBER_OVERIDE,CUSTOMER_BILLING_UNIT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REF_ORDER_ID,ORDER_TYPE_CD,WORKFLOW_IND,USER_NAME_KEY,RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,LOCALE_CD,CURRENCY_CD FROM CLW_PRE_ORDER WHERE PRE_ORDER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPreOrderId=" + pPreOrderId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPreOrderId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PreOrderData.createValue();
            
            x.setPreOrderId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setSiteId(rs.getInt(3));
            x.setSiteName(rs.getString(4));
            x.setContractId(rs.getInt(5));
            x.setIncomingProfileId(rs.getInt(6));
            x.setTradingPartnerId(rs.getInt(7));
            x.setCostCenterId(rs.getInt(8));
            x.setUserId(rs.getInt(9));
            x.setUserName(rs.getString(10));
            x.setSkuTypeCd(rs.getString(11));
            x.setBillingOrder(rs.getString(12));
            x.setBypassOrderRouting(rs.getString(13));
            x.setBypassPreCapturePipeline(rs.getString(14));
            x.setFreeFormatAddress(rs.getString(15));
            x.setCustomerComments(rs.getString(16));
            x.setCustomerOrderDate(rs.getString(17));
            x.setCustomerPoNumber(rs.getString(18));
            x.setFreightCharge(rs.getBigDecimal(19));
            x.setHandlingCharge(rs.getBigDecimal(20));
            x.setHoldUntilDate(rs.getDate(21));
            x.setOrderContactName(rs.getString(22));
            x.setOrderEmail(rs.getString(23));
            x.setOrderFaxNumber(rs.getString(24));
            x.setOrderNote(rs.getString(25));
            x.setOrderRefNumber(rs.getString(26));
            x.setOrderRequestedShipDate(rs.getDate(27));
            x.setOrderSourceCd(rs.getString(28));
            x.setOrderTelephoneNumber(rs.getString(29));
            x.setPaymentType(rs.getString(30));
            x.setOrderStatusCdOveride(rs.getString(31));
            x.setAccountErpNumberOveride(rs.getString(32));
            x.setCustomerBillingUnit(rs.getString(33));
            x.setAddDate(rs.getTimestamp(34));
            x.setAddBy(rs.getString(35));
            x.setModDate(rs.getTimestamp(36));
            x.setModBy(rs.getString(37));
            x.setRefOrderId(rs.getInt(38));
            x.setOrderTypeCd(rs.getString(39));
            x.setWorkflowInd(rs.getString(40));
            x.setUserNameKey(rs.getString(41));
            x.setRushCharge(rs.getBigDecimal(42));
            x.setOrderBudgetTypeCd(rs.getString(43));
            x.setLocaleCd(rs.getString(44));
            x.setCurrencyCd(rs.getString(45));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PRE_ORDER_ID :" + pPreOrderId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PreOrderDataVector object that consists
     * of PreOrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PreOrderDataVector()
     * @throws            SQLException
     */
    public static PreOrderDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PreOrderData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PRE_ORDER.PRE_ORDER_ID,CLW_PRE_ORDER.ACCOUNT_ID,CLW_PRE_ORDER.SITE_ID,CLW_PRE_ORDER.SITE_NAME,CLW_PRE_ORDER.CONTRACT_ID,CLW_PRE_ORDER.INCOMING_PROFILE_ID,CLW_PRE_ORDER.TRADING_PARTNER_ID,CLW_PRE_ORDER.COST_CENTER_ID,CLW_PRE_ORDER.USER_ID,CLW_PRE_ORDER.USER_NAME,CLW_PRE_ORDER.SKU_TYPE_CD,CLW_PRE_ORDER.BILLING_ORDER,CLW_PRE_ORDER.BYPASS_ORDER_ROUTING,CLW_PRE_ORDER.BYPASS_PRE_CAPTURE_PIPELINE,CLW_PRE_ORDER.FREE_FORMAT_ADDRESS,CLW_PRE_ORDER.CUSTOMER_COMMENTS,CLW_PRE_ORDER.CUSTOMER_ORDER_DATE,CLW_PRE_ORDER.CUSTOMER_PO_NUMBER,CLW_PRE_ORDER.FREIGHT_CHARGE,CLW_PRE_ORDER.HANDLING_CHARGE,CLW_PRE_ORDER.HOLD_UNTIL_DATE,CLW_PRE_ORDER.ORDER_CONTACT_NAME,CLW_PRE_ORDER.ORDER_EMAIL,CLW_PRE_ORDER.ORDER_FAX_NUMBER,CLW_PRE_ORDER.ORDER_NOTE,CLW_PRE_ORDER.ORDER_REF_NUMBER,CLW_PRE_ORDER.ORDER_REQUESTED_SHIP_DATE,CLW_PRE_ORDER.ORDER_SOURCE_CD,CLW_PRE_ORDER.ORDER_TELEPHONE_NUMBER,CLW_PRE_ORDER.PAYMENT_TYPE,CLW_PRE_ORDER.ORDER_STATUS_CD_OVERIDE,CLW_PRE_ORDER.ACCOUNT_ERP_NUMBER_OVERIDE,CLW_PRE_ORDER.CUSTOMER_BILLING_UNIT,CLW_PRE_ORDER.ADD_DATE,CLW_PRE_ORDER.ADD_BY,CLW_PRE_ORDER.MOD_DATE,CLW_PRE_ORDER.MOD_BY,CLW_PRE_ORDER.REF_ORDER_ID,CLW_PRE_ORDER.ORDER_TYPE_CD,CLW_PRE_ORDER.WORKFLOW_IND,CLW_PRE_ORDER.USER_NAME_KEY,CLW_PRE_ORDER.RUSH_CHARGE,CLW_PRE_ORDER.ORDER_BUDGET_TYPE_CD,CLW_PRE_ORDER.LOCALE_CD,CLW_PRE_ORDER.CURRENCY_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PreOrderData Object.
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
    *@returns a populated PreOrderData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PreOrderData x = PreOrderData.createValue();
         
         x.setPreOrderId(rs.getInt(1+offset));
         x.setAccountId(rs.getInt(2+offset));
         x.setSiteId(rs.getInt(3+offset));
         x.setSiteName(rs.getString(4+offset));
         x.setContractId(rs.getInt(5+offset));
         x.setIncomingProfileId(rs.getInt(6+offset));
         x.setTradingPartnerId(rs.getInt(7+offset));
         x.setCostCenterId(rs.getInt(8+offset));
         x.setUserId(rs.getInt(9+offset));
         x.setUserName(rs.getString(10+offset));
         x.setSkuTypeCd(rs.getString(11+offset));
         x.setBillingOrder(rs.getString(12+offset));
         x.setBypassOrderRouting(rs.getString(13+offset));
         x.setBypassPreCapturePipeline(rs.getString(14+offset));
         x.setFreeFormatAddress(rs.getString(15+offset));
         x.setCustomerComments(rs.getString(16+offset));
         x.setCustomerOrderDate(rs.getString(17+offset));
         x.setCustomerPoNumber(rs.getString(18+offset));
         x.setFreightCharge(rs.getBigDecimal(19+offset));
         x.setHandlingCharge(rs.getBigDecimal(20+offset));
         x.setHoldUntilDate(rs.getDate(21+offset));
         x.setOrderContactName(rs.getString(22+offset));
         x.setOrderEmail(rs.getString(23+offset));
         x.setOrderFaxNumber(rs.getString(24+offset));
         x.setOrderNote(rs.getString(25+offset));
         x.setOrderRefNumber(rs.getString(26+offset));
         x.setOrderRequestedShipDate(rs.getDate(27+offset));
         x.setOrderSourceCd(rs.getString(28+offset));
         x.setOrderTelephoneNumber(rs.getString(29+offset));
         x.setPaymentType(rs.getString(30+offset));
         x.setOrderStatusCdOveride(rs.getString(31+offset));
         x.setAccountErpNumberOveride(rs.getString(32+offset));
         x.setCustomerBillingUnit(rs.getString(33+offset));
         x.setAddDate(rs.getTimestamp(34+offset));
         x.setAddBy(rs.getString(35+offset));
         x.setModDate(rs.getTimestamp(36+offset));
         x.setModBy(rs.getString(37+offset));
         x.setRefOrderId(rs.getInt(38+offset));
         x.setOrderTypeCd(rs.getString(39+offset));
         x.setWorkflowInd(rs.getString(40+offset));
         x.setUserNameKey(rs.getString(41+offset));
         x.setRushCharge(rs.getBigDecimal(42+offset));
         x.setOrderBudgetTypeCd(rs.getString(43+offset));
         x.setLocaleCd(rs.getString(44+offset));
         x.setCurrencyCd(rs.getString(45+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PreOrderData Object represents.
    */
    public int getColumnCount(){
        return 45;
    }

    /**
     * Gets a PreOrderDataVector object that consists
     * of PreOrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PreOrderDataVector()
     * @throws            SQLException
     */
    public static PreOrderDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PRE_ORDER_ID,ACCOUNT_ID,SITE_ID,SITE_NAME,CONTRACT_ID,INCOMING_PROFILE_ID,TRADING_PARTNER_ID,COST_CENTER_ID,USER_ID,USER_NAME,SKU_TYPE_CD,BILLING_ORDER,BYPASS_ORDER_ROUTING,BYPASS_PRE_CAPTURE_PIPELINE,FREE_FORMAT_ADDRESS,CUSTOMER_COMMENTS,CUSTOMER_ORDER_DATE,CUSTOMER_PO_NUMBER,FREIGHT_CHARGE,HANDLING_CHARGE,HOLD_UNTIL_DATE,ORDER_CONTACT_NAME,ORDER_EMAIL,ORDER_FAX_NUMBER,ORDER_NOTE,ORDER_REF_NUMBER,ORDER_REQUESTED_SHIP_DATE,ORDER_SOURCE_CD,ORDER_TELEPHONE_NUMBER,PAYMENT_TYPE,ORDER_STATUS_CD_OVERIDE,ACCOUNT_ERP_NUMBER_OVERIDE,CUSTOMER_BILLING_UNIT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REF_ORDER_ID,ORDER_TYPE_CD,WORKFLOW_IND,USER_NAME_KEY,RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,LOCALE_CD,CURRENCY_CD FROM CLW_PRE_ORDER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PRE_ORDER.PRE_ORDER_ID,CLW_PRE_ORDER.ACCOUNT_ID,CLW_PRE_ORDER.SITE_ID,CLW_PRE_ORDER.SITE_NAME,CLW_PRE_ORDER.CONTRACT_ID,CLW_PRE_ORDER.INCOMING_PROFILE_ID,CLW_PRE_ORDER.TRADING_PARTNER_ID,CLW_PRE_ORDER.COST_CENTER_ID,CLW_PRE_ORDER.USER_ID,CLW_PRE_ORDER.USER_NAME,CLW_PRE_ORDER.SKU_TYPE_CD,CLW_PRE_ORDER.BILLING_ORDER,CLW_PRE_ORDER.BYPASS_ORDER_ROUTING,CLW_PRE_ORDER.BYPASS_PRE_CAPTURE_PIPELINE,CLW_PRE_ORDER.FREE_FORMAT_ADDRESS,CLW_PRE_ORDER.CUSTOMER_COMMENTS,CLW_PRE_ORDER.CUSTOMER_ORDER_DATE,CLW_PRE_ORDER.CUSTOMER_PO_NUMBER,CLW_PRE_ORDER.FREIGHT_CHARGE,CLW_PRE_ORDER.HANDLING_CHARGE,CLW_PRE_ORDER.HOLD_UNTIL_DATE,CLW_PRE_ORDER.ORDER_CONTACT_NAME,CLW_PRE_ORDER.ORDER_EMAIL,CLW_PRE_ORDER.ORDER_FAX_NUMBER,CLW_PRE_ORDER.ORDER_NOTE,CLW_PRE_ORDER.ORDER_REF_NUMBER,CLW_PRE_ORDER.ORDER_REQUESTED_SHIP_DATE,CLW_PRE_ORDER.ORDER_SOURCE_CD,CLW_PRE_ORDER.ORDER_TELEPHONE_NUMBER,CLW_PRE_ORDER.PAYMENT_TYPE,CLW_PRE_ORDER.ORDER_STATUS_CD_OVERIDE,CLW_PRE_ORDER.ACCOUNT_ERP_NUMBER_OVERIDE,CLW_PRE_ORDER.CUSTOMER_BILLING_UNIT,CLW_PRE_ORDER.ADD_DATE,CLW_PRE_ORDER.ADD_BY,CLW_PRE_ORDER.MOD_DATE,CLW_PRE_ORDER.MOD_BY,CLW_PRE_ORDER.REF_ORDER_ID,CLW_PRE_ORDER.ORDER_TYPE_CD,CLW_PRE_ORDER.WORKFLOW_IND,CLW_PRE_ORDER.USER_NAME_KEY,CLW_PRE_ORDER.RUSH_CHARGE,CLW_PRE_ORDER.ORDER_BUDGET_TYPE_CD,CLW_PRE_ORDER.LOCALE_CD,CLW_PRE_ORDER.CURRENCY_CD FROM CLW_PRE_ORDER");
                where = pCriteria.getSqlClause("CLW_PRE_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PRE_ORDER.equals(otherTable)){
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
        PreOrderDataVector v = new PreOrderDataVector();
        while (rs.next()) {
            PreOrderData x = PreOrderData.createValue();
            
            x.setPreOrderId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setSiteId(rs.getInt(3));
            x.setSiteName(rs.getString(4));
            x.setContractId(rs.getInt(5));
            x.setIncomingProfileId(rs.getInt(6));
            x.setTradingPartnerId(rs.getInt(7));
            x.setCostCenterId(rs.getInt(8));
            x.setUserId(rs.getInt(9));
            x.setUserName(rs.getString(10));
            x.setSkuTypeCd(rs.getString(11));
            x.setBillingOrder(rs.getString(12));
            x.setBypassOrderRouting(rs.getString(13));
            x.setBypassPreCapturePipeline(rs.getString(14));
            x.setFreeFormatAddress(rs.getString(15));
            x.setCustomerComments(rs.getString(16));
            x.setCustomerOrderDate(rs.getString(17));
            x.setCustomerPoNumber(rs.getString(18));
            x.setFreightCharge(rs.getBigDecimal(19));
            x.setHandlingCharge(rs.getBigDecimal(20));
            x.setHoldUntilDate(rs.getDate(21));
            x.setOrderContactName(rs.getString(22));
            x.setOrderEmail(rs.getString(23));
            x.setOrderFaxNumber(rs.getString(24));
            x.setOrderNote(rs.getString(25));
            x.setOrderRefNumber(rs.getString(26));
            x.setOrderRequestedShipDate(rs.getDate(27));
            x.setOrderSourceCd(rs.getString(28));
            x.setOrderTelephoneNumber(rs.getString(29));
            x.setPaymentType(rs.getString(30));
            x.setOrderStatusCdOveride(rs.getString(31));
            x.setAccountErpNumberOveride(rs.getString(32));
            x.setCustomerBillingUnit(rs.getString(33));
            x.setAddDate(rs.getTimestamp(34));
            x.setAddBy(rs.getString(35));
            x.setModDate(rs.getTimestamp(36));
            x.setModBy(rs.getString(37));
            x.setRefOrderId(rs.getInt(38));
            x.setOrderTypeCd(rs.getString(39));
            x.setWorkflowInd(rs.getString(40));
            x.setUserNameKey(rs.getString(41));
            x.setRushCharge(rs.getBigDecimal(42));
            x.setOrderBudgetTypeCd(rs.getString(43));
            x.setLocaleCd(rs.getString(44));
            x.setCurrencyCd(rs.getString(45));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a PreOrderDataVector object that consists
     * of PreOrderData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PreOrderData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PreOrderDataVector()
     * @throws            SQLException
     */
    public static PreOrderDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PreOrderDataVector v = new PreOrderDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_ID,ACCOUNT_ID,SITE_ID,SITE_NAME,CONTRACT_ID,INCOMING_PROFILE_ID,TRADING_PARTNER_ID,COST_CENTER_ID,USER_ID,USER_NAME,SKU_TYPE_CD,BILLING_ORDER,BYPASS_ORDER_ROUTING,BYPASS_PRE_CAPTURE_PIPELINE,FREE_FORMAT_ADDRESS,CUSTOMER_COMMENTS,CUSTOMER_ORDER_DATE,CUSTOMER_PO_NUMBER,FREIGHT_CHARGE,HANDLING_CHARGE,HOLD_UNTIL_DATE,ORDER_CONTACT_NAME,ORDER_EMAIL,ORDER_FAX_NUMBER,ORDER_NOTE,ORDER_REF_NUMBER,ORDER_REQUESTED_SHIP_DATE,ORDER_SOURCE_CD,ORDER_TELEPHONE_NUMBER,PAYMENT_TYPE,ORDER_STATUS_CD_OVERIDE,ACCOUNT_ERP_NUMBER_OVERIDE,CUSTOMER_BILLING_UNIT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REF_ORDER_ID,ORDER_TYPE_CD,WORKFLOW_IND,USER_NAME_KEY,RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,LOCALE_CD,CURRENCY_CD FROM CLW_PRE_ORDER WHERE PRE_ORDER_ID IN (");

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
            PreOrderData x=null;
            while (rs.next()) {
                // build the object
                x=PreOrderData.createValue();
                
                x.setPreOrderId(rs.getInt(1));
                x.setAccountId(rs.getInt(2));
                x.setSiteId(rs.getInt(3));
                x.setSiteName(rs.getString(4));
                x.setContractId(rs.getInt(5));
                x.setIncomingProfileId(rs.getInt(6));
                x.setTradingPartnerId(rs.getInt(7));
                x.setCostCenterId(rs.getInt(8));
                x.setUserId(rs.getInt(9));
                x.setUserName(rs.getString(10));
                x.setSkuTypeCd(rs.getString(11));
                x.setBillingOrder(rs.getString(12));
                x.setBypassOrderRouting(rs.getString(13));
                x.setBypassPreCapturePipeline(rs.getString(14));
                x.setFreeFormatAddress(rs.getString(15));
                x.setCustomerComments(rs.getString(16));
                x.setCustomerOrderDate(rs.getString(17));
                x.setCustomerPoNumber(rs.getString(18));
                x.setFreightCharge(rs.getBigDecimal(19));
                x.setHandlingCharge(rs.getBigDecimal(20));
                x.setHoldUntilDate(rs.getDate(21));
                x.setOrderContactName(rs.getString(22));
                x.setOrderEmail(rs.getString(23));
                x.setOrderFaxNumber(rs.getString(24));
                x.setOrderNote(rs.getString(25));
                x.setOrderRefNumber(rs.getString(26));
                x.setOrderRequestedShipDate(rs.getDate(27));
                x.setOrderSourceCd(rs.getString(28));
                x.setOrderTelephoneNumber(rs.getString(29));
                x.setPaymentType(rs.getString(30));
                x.setOrderStatusCdOveride(rs.getString(31));
                x.setAccountErpNumberOveride(rs.getString(32));
                x.setCustomerBillingUnit(rs.getString(33));
                x.setAddDate(rs.getTimestamp(34));
                x.setAddBy(rs.getString(35));
                x.setModDate(rs.getTimestamp(36));
                x.setModBy(rs.getString(37));
                x.setRefOrderId(rs.getInt(38));
                x.setOrderTypeCd(rs.getString(39));
                x.setWorkflowInd(rs.getString(40));
                x.setUserNameKey(rs.getString(41));
                x.setRushCharge(rs.getBigDecimal(42));
                x.setOrderBudgetTypeCd(rs.getString(43));
                x.setLocaleCd(rs.getString(44));
                x.setCurrencyCd(rs.getString(45));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a PreOrderDataVector object of all
     * PreOrderData objects in the database.
     * @param pCon An open database connection.
     * @return new PreOrderDataVector()
     * @throws            SQLException
     */
    public static PreOrderDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PRE_ORDER_ID,ACCOUNT_ID,SITE_ID,SITE_NAME,CONTRACT_ID,INCOMING_PROFILE_ID,TRADING_PARTNER_ID,COST_CENTER_ID,USER_ID,USER_NAME,SKU_TYPE_CD,BILLING_ORDER,BYPASS_ORDER_ROUTING,BYPASS_PRE_CAPTURE_PIPELINE,FREE_FORMAT_ADDRESS,CUSTOMER_COMMENTS,CUSTOMER_ORDER_DATE,CUSTOMER_PO_NUMBER,FREIGHT_CHARGE,HANDLING_CHARGE,HOLD_UNTIL_DATE,ORDER_CONTACT_NAME,ORDER_EMAIL,ORDER_FAX_NUMBER,ORDER_NOTE,ORDER_REF_NUMBER,ORDER_REQUESTED_SHIP_DATE,ORDER_SOURCE_CD,ORDER_TELEPHONE_NUMBER,PAYMENT_TYPE,ORDER_STATUS_CD_OVERIDE,ACCOUNT_ERP_NUMBER_OVERIDE,CUSTOMER_BILLING_UNIT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REF_ORDER_ID,ORDER_TYPE_CD,WORKFLOW_IND,USER_NAME_KEY,RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,LOCALE_CD,CURRENCY_CD FROM CLW_PRE_ORDER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PreOrderDataVector v = new PreOrderDataVector();
        PreOrderData x = null;
        while (rs.next()) {
            // build the object
            x = PreOrderData.createValue();
            
            x.setPreOrderId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setSiteId(rs.getInt(3));
            x.setSiteName(rs.getString(4));
            x.setContractId(rs.getInt(5));
            x.setIncomingProfileId(rs.getInt(6));
            x.setTradingPartnerId(rs.getInt(7));
            x.setCostCenterId(rs.getInt(8));
            x.setUserId(rs.getInt(9));
            x.setUserName(rs.getString(10));
            x.setSkuTypeCd(rs.getString(11));
            x.setBillingOrder(rs.getString(12));
            x.setBypassOrderRouting(rs.getString(13));
            x.setBypassPreCapturePipeline(rs.getString(14));
            x.setFreeFormatAddress(rs.getString(15));
            x.setCustomerComments(rs.getString(16));
            x.setCustomerOrderDate(rs.getString(17));
            x.setCustomerPoNumber(rs.getString(18));
            x.setFreightCharge(rs.getBigDecimal(19));
            x.setHandlingCharge(rs.getBigDecimal(20));
            x.setHoldUntilDate(rs.getDate(21));
            x.setOrderContactName(rs.getString(22));
            x.setOrderEmail(rs.getString(23));
            x.setOrderFaxNumber(rs.getString(24));
            x.setOrderNote(rs.getString(25));
            x.setOrderRefNumber(rs.getString(26));
            x.setOrderRequestedShipDate(rs.getDate(27));
            x.setOrderSourceCd(rs.getString(28));
            x.setOrderTelephoneNumber(rs.getString(29));
            x.setPaymentType(rs.getString(30));
            x.setOrderStatusCdOveride(rs.getString(31));
            x.setAccountErpNumberOveride(rs.getString(32));
            x.setCustomerBillingUnit(rs.getString(33));
            x.setAddDate(rs.getTimestamp(34));
            x.setAddBy(rs.getString(35));
            x.setModDate(rs.getTimestamp(36));
            x.setModBy(rs.getString(37));
            x.setRefOrderId(rs.getInt(38));
            x.setOrderTypeCd(rs.getString(39));
            x.setWorkflowInd(rs.getString(40));
            x.setUserNameKey(rs.getString(41));
            x.setRushCharge(rs.getBigDecimal(42));
            x.setOrderBudgetTypeCd(rs.getString(43));
            x.setLocaleCd(rs.getString(44));
            x.setCurrencyCd(rs.getString(45));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * PreOrderData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT PRE_ORDER_ID FROM CLW_PRE_ORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PRE_ORDER");
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
     * Inserts a PreOrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PreOrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderData insert(Connection pCon, PreOrderData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PRE_ORDER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PRE_ORDER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPreOrderId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PRE_ORDER (PRE_ORDER_ID,ACCOUNT_ID,SITE_ID,SITE_NAME,CONTRACT_ID,INCOMING_PROFILE_ID,TRADING_PARTNER_ID,COST_CENTER_ID,USER_ID,USER_NAME,SKU_TYPE_CD,BILLING_ORDER,BYPASS_ORDER_ROUTING,BYPASS_PRE_CAPTURE_PIPELINE,FREE_FORMAT_ADDRESS,CUSTOMER_COMMENTS,CUSTOMER_ORDER_DATE,CUSTOMER_PO_NUMBER,FREIGHT_CHARGE,HANDLING_CHARGE,HOLD_UNTIL_DATE,ORDER_CONTACT_NAME,ORDER_EMAIL,ORDER_FAX_NUMBER,ORDER_NOTE,ORDER_REF_NUMBER,ORDER_REQUESTED_SHIP_DATE,ORDER_SOURCE_CD,ORDER_TELEPHONE_NUMBER,PAYMENT_TYPE,ORDER_STATUS_CD_OVERIDE,ACCOUNT_ERP_NUMBER_OVERIDE,CUSTOMER_BILLING_UNIT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REF_ORDER_ID,ORDER_TYPE_CD,WORKFLOW_IND,USER_NAME_KEY,RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,LOCALE_CD,CURRENCY_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPreOrderId());
        pstmt.setInt(2,pData.getAccountId());
        pstmt.setInt(3,pData.getSiteId());
        pstmt.setString(4,pData.getSiteName());
        pstmt.setInt(5,pData.getContractId());
        pstmt.setInt(6,pData.getIncomingProfileId());
        pstmt.setInt(7,pData.getTradingPartnerId());
        pstmt.setInt(8,pData.getCostCenterId());
        pstmt.setInt(9,pData.getUserId());
        pstmt.setString(10,pData.getUserName());
        pstmt.setString(11,pData.getSkuTypeCd());
        pstmt.setString(12,pData.getBillingOrder());
        pstmt.setString(13,pData.getBypassOrderRouting());
        pstmt.setString(14,pData.getBypassPreCapturePipeline());
        pstmt.setString(15,pData.getFreeFormatAddress());
        pstmt.setString(16,pData.getCustomerComments());
        pstmt.setString(17,pData.getCustomerOrderDate());
        pstmt.setString(18,pData.getCustomerPoNumber());
        pstmt.setBigDecimal(19,pData.getFreightCharge());
        pstmt.setBigDecimal(20,pData.getHandlingCharge());
        pstmt.setDate(21,DBAccess.toSQLDate(pData.getHoldUntilDate()));
        pstmt.setString(22,pData.getOrderContactName());
        pstmt.setString(23,pData.getOrderEmail());
        pstmt.setString(24,pData.getOrderFaxNumber());
        pstmt.setString(25,pData.getOrderNote());
        pstmt.setString(26,pData.getOrderRefNumber());
        pstmt.setDate(27,DBAccess.toSQLDate(pData.getOrderRequestedShipDate()));
        pstmt.setString(28,pData.getOrderSourceCd());
        pstmt.setString(29,pData.getOrderTelephoneNumber());
        pstmt.setString(30,pData.getPaymentType());
        pstmt.setString(31,pData.getOrderStatusCdOveride());
        pstmt.setString(32,pData.getAccountErpNumberOveride());
        pstmt.setString(33,pData.getCustomerBillingUnit());
        pstmt.setTimestamp(34,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(35,pData.getAddBy());
        pstmt.setTimestamp(36,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(37,pData.getModBy());
        pstmt.setInt(38,pData.getRefOrderId());
        pstmt.setString(39,pData.getOrderTypeCd());
        pstmt.setString(40,pData.getWorkflowInd());
        pstmt.setString(41,pData.getUserNameKey());
        pstmt.setBigDecimal(42,pData.getRushCharge());
        pstmt.setString(43,pData.getOrderBudgetTypeCd());
        pstmt.setString(44,pData.getLocaleCd());
        pstmt.setString(45,pData.getCurrencyCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   SITE_NAME="+pData.getSiteName());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   INCOMING_PROFILE_ID="+pData.getIncomingProfileId());
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   SKU_TYPE_CD="+pData.getSkuTypeCd());
            log.debug("SQL:   BILLING_ORDER="+pData.getBillingOrder());
            log.debug("SQL:   BYPASS_ORDER_ROUTING="+pData.getBypassOrderRouting());
            log.debug("SQL:   BYPASS_PRE_CAPTURE_PIPELINE="+pData.getBypassPreCapturePipeline());
            log.debug("SQL:   FREE_FORMAT_ADDRESS="+pData.getFreeFormatAddress());
            log.debug("SQL:   CUSTOMER_COMMENTS="+pData.getCustomerComments());
            log.debug("SQL:   CUSTOMER_ORDER_DATE="+pData.getCustomerOrderDate());
            log.debug("SQL:   CUSTOMER_PO_NUMBER="+pData.getCustomerPoNumber());
            log.debug("SQL:   FREIGHT_CHARGE="+pData.getFreightCharge());
            log.debug("SQL:   HANDLING_CHARGE="+pData.getHandlingCharge());
            log.debug("SQL:   HOLD_UNTIL_DATE="+pData.getHoldUntilDate());
            log.debug("SQL:   ORDER_CONTACT_NAME="+pData.getOrderContactName());
            log.debug("SQL:   ORDER_EMAIL="+pData.getOrderEmail());
            log.debug("SQL:   ORDER_FAX_NUMBER="+pData.getOrderFaxNumber());
            log.debug("SQL:   ORDER_NOTE="+pData.getOrderNote());
            log.debug("SQL:   ORDER_REF_NUMBER="+pData.getOrderRefNumber());
            log.debug("SQL:   ORDER_REQUESTED_SHIP_DATE="+pData.getOrderRequestedShipDate());
            log.debug("SQL:   ORDER_SOURCE_CD="+pData.getOrderSourceCd());
            log.debug("SQL:   ORDER_TELEPHONE_NUMBER="+pData.getOrderTelephoneNumber());
            log.debug("SQL:   PAYMENT_TYPE="+pData.getPaymentType());
            log.debug("SQL:   ORDER_STATUS_CD_OVERIDE="+pData.getOrderStatusCdOveride());
            log.debug("SQL:   ACCOUNT_ERP_NUMBER_OVERIDE="+pData.getAccountErpNumberOveride());
            log.debug("SQL:   CUSTOMER_BILLING_UNIT="+pData.getCustomerBillingUnit());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   REF_ORDER_ID="+pData.getRefOrderId());
            log.debug("SQL:   ORDER_TYPE_CD="+pData.getOrderTypeCd());
            log.debug("SQL:   WORKFLOW_IND="+pData.getWorkflowInd());
            log.debug("SQL:   USER_NAME_KEY="+pData.getUserNameKey());
            log.debug("SQL:   RUSH_CHARGE="+pData.getRushCharge());
            log.debug("SQL:   ORDER_BUDGET_TYPE_CD="+pData.getOrderBudgetTypeCd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setPreOrderId(0);
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
     * Updates a PreOrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PRE_ORDER SET ACCOUNT_ID = ?,SITE_ID = ?,SITE_NAME = ?,CONTRACT_ID = ?,INCOMING_PROFILE_ID = ?,TRADING_PARTNER_ID = ?,COST_CENTER_ID = ?,USER_ID = ?,USER_NAME = ?,SKU_TYPE_CD = ?,BILLING_ORDER = ?,BYPASS_ORDER_ROUTING = ?,BYPASS_PRE_CAPTURE_PIPELINE = ?,FREE_FORMAT_ADDRESS = ?,CUSTOMER_COMMENTS = ?,CUSTOMER_ORDER_DATE = ?,CUSTOMER_PO_NUMBER = ?,FREIGHT_CHARGE = ?,HANDLING_CHARGE = ?,HOLD_UNTIL_DATE = ?,ORDER_CONTACT_NAME = ?,ORDER_EMAIL = ?,ORDER_FAX_NUMBER = ?,ORDER_NOTE = ?,ORDER_REF_NUMBER = ?,ORDER_REQUESTED_SHIP_DATE = ?,ORDER_SOURCE_CD = ?,ORDER_TELEPHONE_NUMBER = ?,PAYMENT_TYPE = ?,ORDER_STATUS_CD_OVERIDE = ?,ACCOUNT_ERP_NUMBER_OVERIDE = ?,CUSTOMER_BILLING_UNIT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,REF_ORDER_ID = ?,ORDER_TYPE_CD = ?,WORKFLOW_IND = ?,USER_NAME_KEY = ?,RUSH_CHARGE = ?,ORDER_BUDGET_TYPE_CD = ?,LOCALE_CD = ?,CURRENCY_CD = ? WHERE PRE_ORDER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setInt(i++,pData.getSiteId());
        pstmt.setString(i++,pData.getSiteName());
        pstmt.setInt(i++,pData.getContractId());
        pstmt.setInt(i++,pData.getIncomingProfileId());
        pstmt.setInt(i++,pData.getTradingPartnerId());
        pstmt.setInt(i++,pData.getCostCenterId());
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getUserName());
        pstmt.setString(i++,pData.getSkuTypeCd());
        pstmt.setString(i++,pData.getBillingOrder());
        pstmt.setString(i++,pData.getBypassOrderRouting());
        pstmt.setString(i++,pData.getBypassPreCapturePipeline());
        pstmt.setString(i++,pData.getFreeFormatAddress());
        pstmt.setString(i++,pData.getCustomerComments());
        pstmt.setString(i++,pData.getCustomerOrderDate());
        pstmt.setString(i++,pData.getCustomerPoNumber());
        pstmt.setBigDecimal(i++,pData.getFreightCharge());
        pstmt.setBigDecimal(i++,pData.getHandlingCharge());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getHoldUntilDate()));
        pstmt.setString(i++,pData.getOrderContactName());
        pstmt.setString(i++,pData.getOrderEmail());
        pstmt.setString(i++,pData.getOrderFaxNumber());
        pstmt.setString(i++,pData.getOrderNote());
        pstmt.setString(i++,pData.getOrderRefNumber());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderRequestedShipDate()));
        pstmt.setString(i++,pData.getOrderSourceCd());
        pstmt.setString(i++,pData.getOrderTelephoneNumber());
        pstmt.setString(i++,pData.getPaymentType());
        pstmt.setString(i++,pData.getOrderStatusCdOveride());
        pstmt.setString(i++,pData.getAccountErpNumberOveride());
        pstmt.setString(i++,pData.getCustomerBillingUnit());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getRefOrderId());
        pstmt.setString(i++,pData.getOrderTypeCd());
        pstmt.setString(i++,pData.getWorkflowInd());
        pstmt.setString(i++,pData.getUserNameKey());
        pstmt.setBigDecimal(i++,pData.getRushCharge());
        pstmt.setString(i++,pData.getOrderBudgetTypeCd());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setString(i++,pData.getCurrencyCd());
        pstmt.setInt(i++,pData.getPreOrderId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   SITE_NAME="+pData.getSiteName());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   INCOMING_PROFILE_ID="+pData.getIncomingProfileId());
            log.debug("SQL:   TRADING_PARTNER_ID="+pData.getTradingPartnerId());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   USER_NAME="+pData.getUserName());
            log.debug("SQL:   SKU_TYPE_CD="+pData.getSkuTypeCd());
            log.debug("SQL:   BILLING_ORDER="+pData.getBillingOrder());
            log.debug("SQL:   BYPASS_ORDER_ROUTING="+pData.getBypassOrderRouting());
            log.debug("SQL:   BYPASS_PRE_CAPTURE_PIPELINE="+pData.getBypassPreCapturePipeline());
            log.debug("SQL:   FREE_FORMAT_ADDRESS="+pData.getFreeFormatAddress());
            log.debug("SQL:   CUSTOMER_COMMENTS="+pData.getCustomerComments());
            log.debug("SQL:   CUSTOMER_ORDER_DATE="+pData.getCustomerOrderDate());
            log.debug("SQL:   CUSTOMER_PO_NUMBER="+pData.getCustomerPoNumber());
            log.debug("SQL:   FREIGHT_CHARGE="+pData.getFreightCharge());
            log.debug("SQL:   HANDLING_CHARGE="+pData.getHandlingCharge());
            log.debug("SQL:   HOLD_UNTIL_DATE="+pData.getHoldUntilDate());
            log.debug("SQL:   ORDER_CONTACT_NAME="+pData.getOrderContactName());
            log.debug("SQL:   ORDER_EMAIL="+pData.getOrderEmail());
            log.debug("SQL:   ORDER_FAX_NUMBER="+pData.getOrderFaxNumber());
            log.debug("SQL:   ORDER_NOTE="+pData.getOrderNote());
            log.debug("SQL:   ORDER_REF_NUMBER="+pData.getOrderRefNumber());
            log.debug("SQL:   ORDER_REQUESTED_SHIP_DATE="+pData.getOrderRequestedShipDate());
            log.debug("SQL:   ORDER_SOURCE_CD="+pData.getOrderSourceCd());
            log.debug("SQL:   ORDER_TELEPHONE_NUMBER="+pData.getOrderTelephoneNumber());
            log.debug("SQL:   PAYMENT_TYPE="+pData.getPaymentType());
            log.debug("SQL:   ORDER_STATUS_CD_OVERIDE="+pData.getOrderStatusCdOveride());
            log.debug("SQL:   ACCOUNT_ERP_NUMBER_OVERIDE="+pData.getAccountErpNumberOveride());
            log.debug("SQL:   CUSTOMER_BILLING_UNIT="+pData.getCustomerBillingUnit());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   REF_ORDER_ID="+pData.getRefOrderId());
            log.debug("SQL:   ORDER_TYPE_CD="+pData.getOrderTypeCd());
            log.debug("SQL:   WORKFLOW_IND="+pData.getWorkflowInd());
            log.debug("SQL:   USER_NAME_KEY="+pData.getUserNameKey());
            log.debug("SQL:   RUSH_CHARGE="+pData.getRushCharge());
            log.debug("SQL:   ORDER_BUDGET_TYPE_CD="+pData.getOrderBudgetTypeCd());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a PreOrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PRE_ORDER WHERE PRE_ORDER_ID = " + pPreOrderId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PreOrderData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PRE_ORDER");
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
     * Inserts a PreOrderData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PreOrderData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PRE_ORDER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PRE_ORDER_ID,ACCOUNT_ID,SITE_ID,SITE_NAME,CONTRACT_ID,INCOMING_PROFILE_ID,TRADING_PARTNER_ID,COST_CENTER_ID,USER_ID,USER_NAME,SKU_TYPE_CD,BILLING_ORDER,BYPASS_ORDER_ROUTING,BYPASS_PRE_CAPTURE_PIPELINE,FREE_FORMAT_ADDRESS,CUSTOMER_COMMENTS,CUSTOMER_ORDER_DATE,CUSTOMER_PO_NUMBER,FREIGHT_CHARGE,HANDLING_CHARGE,HOLD_UNTIL_DATE,ORDER_CONTACT_NAME,ORDER_EMAIL,ORDER_FAX_NUMBER,ORDER_NOTE,ORDER_REF_NUMBER,ORDER_REQUESTED_SHIP_DATE,ORDER_SOURCE_CD,ORDER_TELEPHONE_NUMBER,PAYMENT_TYPE,ORDER_STATUS_CD_OVERIDE,ACCOUNT_ERP_NUMBER_OVERIDE,CUSTOMER_BILLING_UNIT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,REF_ORDER_ID,ORDER_TYPE_CD,WORKFLOW_IND,USER_NAME_KEY,RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,LOCALE_CD,CURRENCY_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPreOrderId());
        pstmt.setInt(2+4,pData.getAccountId());
        pstmt.setInt(3+4,pData.getSiteId());
        pstmt.setString(4+4,pData.getSiteName());
        pstmt.setInt(5+4,pData.getContractId());
        pstmt.setInt(6+4,pData.getIncomingProfileId());
        pstmt.setInt(7+4,pData.getTradingPartnerId());
        pstmt.setInt(8+4,pData.getCostCenterId());
        pstmt.setInt(9+4,pData.getUserId());
        pstmt.setString(10+4,pData.getUserName());
        pstmt.setString(11+4,pData.getSkuTypeCd());
        pstmt.setString(12+4,pData.getBillingOrder());
        pstmt.setString(13+4,pData.getBypassOrderRouting());
        pstmt.setString(14+4,pData.getBypassPreCapturePipeline());
        pstmt.setString(15+4,pData.getFreeFormatAddress());
        pstmt.setString(16+4,pData.getCustomerComments());
        pstmt.setString(17+4,pData.getCustomerOrderDate());
        pstmt.setString(18+4,pData.getCustomerPoNumber());
        pstmt.setBigDecimal(19+4,pData.getFreightCharge());
        pstmt.setBigDecimal(20+4,pData.getHandlingCharge());
        pstmt.setDate(21+4,DBAccess.toSQLDate(pData.getHoldUntilDate()));
        pstmt.setString(22+4,pData.getOrderContactName());
        pstmt.setString(23+4,pData.getOrderEmail());
        pstmt.setString(24+4,pData.getOrderFaxNumber());
        pstmt.setString(25+4,pData.getOrderNote());
        pstmt.setString(26+4,pData.getOrderRefNumber());
        pstmt.setDate(27+4,DBAccess.toSQLDate(pData.getOrderRequestedShipDate()));
        pstmt.setString(28+4,pData.getOrderSourceCd());
        pstmt.setString(29+4,pData.getOrderTelephoneNumber());
        pstmt.setString(30+4,pData.getPaymentType());
        pstmt.setString(31+4,pData.getOrderStatusCdOveride());
        pstmt.setString(32+4,pData.getAccountErpNumberOveride());
        pstmt.setString(33+4,pData.getCustomerBillingUnit());
        pstmt.setTimestamp(34+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(35+4,pData.getAddBy());
        pstmt.setTimestamp(36+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(37+4,pData.getModBy());
        pstmt.setInt(38+4,pData.getRefOrderId());
        pstmt.setString(39+4,pData.getOrderTypeCd());
        pstmt.setString(40+4,pData.getWorkflowInd());
        pstmt.setString(41+4,pData.getUserNameKey());
        pstmt.setBigDecimal(42+4,pData.getRushCharge());
        pstmt.setString(43+4,pData.getOrderBudgetTypeCd());
        pstmt.setString(44+4,pData.getLocaleCd());
        pstmt.setString(45+4,pData.getCurrencyCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PreOrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PreOrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PreOrderData insert(Connection pCon, PreOrderData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PreOrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PreOrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PreOrderData pData, boolean pLogFl)
        throws SQLException {
        PreOrderData oldData = null;
        if(pLogFl) {
          int id = pData.getPreOrderId();
          try {
          oldData = PreOrderDataAccess.select(pCon,id);
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
     * Deletes a PreOrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPreOrderId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPreOrderId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PRE_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER d WHERE PRE_ORDER_ID = " + pPreOrderId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPreOrderId);
        return n;
     }

    /**
     * Deletes PreOrderData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PRE_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PRE_ORDER d ");
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

