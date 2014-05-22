
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
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
 * <code>OrderDataAccess</code>
 */
public class OrderDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderDataAccess.class.getName());

    /** <code>CLW_ORDER</code> table name */
	/* Primary key: ORDER_ID */
	
    public static final String CLW_ORDER = "CLW_ORDER";
    
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>EXCEPTION_IND</code> EXCEPTION_IND column of table CLW_ORDER */
    public static final String EXCEPTION_IND = "EXCEPTION_IND";
    /** <code>ORDER_NUM</code> ORDER_NUM column of table CLW_ORDER */
    public static final String ORDER_NUM = "ORDER_NUM";
    /** <code>REF_ORDER_NUM</code> REF_ORDER_NUM column of table CLW_ORDER */
    public static final String REF_ORDER_NUM = "REF_ORDER_NUM";
    /** <code>COST_CENTER_ID</code> COST_CENTER_ID column of table CLW_ORDER */
    public static final String COST_CENTER_ID = "COST_CENTER_ID";
    /** <code>COST_CENTER_NAME</code> COST_CENTER_NAME column of table CLW_ORDER */
    public static final String COST_CENTER_NAME = "COST_CENTER_NAME";
    /** <code>WORKFLOW_IND</code> WORKFLOW_IND column of table CLW_ORDER */
    public static final String WORKFLOW_IND = "WORKFLOW_IND";
    /** <code>WORKFLOW_STATUS_CD</code> WORKFLOW_STATUS_CD column of table CLW_ORDER */
    public static final String WORKFLOW_STATUS_CD = "WORKFLOW_STATUS_CD";
    /** <code>ACCOUNT_ERP_NUM</code> ACCOUNT_ERP_NUM column of table CLW_ORDER */
    public static final String ACCOUNT_ERP_NUM = "ACCOUNT_ERP_NUM";
    /** <code>SITE_ERP_NUM</code> SITE_ERP_NUM column of table CLW_ORDER */
    public static final String SITE_ERP_NUM = "SITE_ERP_NUM";
    /** <code>REQUEST_PO_NUM</code> REQUEST_PO_NUM column of table CLW_ORDER */
    public static final String REQUEST_PO_NUM = "REQUEST_PO_NUM";
    /** <code>USER_ID</code> USER_ID column of table CLW_ORDER */
    public static final String USER_ID = "USER_ID";
    /** <code>USER_FIRST_NAME</code> USER_FIRST_NAME column of table CLW_ORDER */
    public static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    /** <code>USER_LAST_NAME</code> USER_LAST_NAME column of table CLW_ORDER */
    public static final String USER_LAST_NAME = "USER_LAST_NAME";
    /** <code>ORDER_SITE_NAME</code> ORDER_SITE_NAME column of table CLW_ORDER */
    public static final String ORDER_SITE_NAME = "ORDER_SITE_NAME";
    /** <code>ORDER_CONTACT_NAME</code> ORDER_CONTACT_NAME column of table CLW_ORDER */
    public static final String ORDER_CONTACT_NAME = "ORDER_CONTACT_NAME";
    /** <code>ORDER_CONTACT_PHONE_NUM</code> ORDER_CONTACT_PHONE_NUM column of table CLW_ORDER */
    public static final String ORDER_CONTACT_PHONE_NUM = "ORDER_CONTACT_PHONE_NUM";
    /** <code>ORDER_CONTACT_EMAIL</code> ORDER_CONTACT_EMAIL column of table CLW_ORDER */
    public static final String ORDER_CONTACT_EMAIL = "ORDER_CONTACT_EMAIL";
    /** <code>ORDER_CONTACT_FAX_NUM</code> ORDER_CONTACT_FAX_NUM column of table CLW_ORDER */
    public static final String ORDER_CONTACT_FAX_NUM = "ORDER_CONTACT_FAX_NUM";
    /** <code>CONTRACT_ID</code> CONTRACT_ID column of table CLW_ORDER */
    public static final String CONTRACT_ID = "CONTRACT_ID";
    /** <code>CONTRACT_SHORT_DESC</code> CONTRACT_SHORT_DESC column of table CLW_ORDER */
    public static final String CONTRACT_SHORT_DESC = "CONTRACT_SHORT_DESC";
    /** <code>ORDER_TYPE_CD</code> ORDER_TYPE_CD column of table CLW_ORDER */
    public static final String ORDER_TYPE_CD = "ORDER_TYPE_CD";
    /** <code>ORDER_SOURCE_CD</code> ORDER_SOURCE_CD column of table CLW_ORDER */
    public static final String ORDER_SOURCE_CD = "ORDER_SOURCE_CD";
    /** <code>ORDER_STATUS_CD</code> ORDER_STATUS_CD column of table CLW_ORDER */
    public static final String ORDER_STATUS_CD = "ORDER_STATUS_CD";
    /** <code>TAX_NUM</code> TAX_NUM column of table CLW_ORDER */
    public static final String TAX_NUM = "TAX_NUM";
    /** <code>ORIGINAL_AMOUNT</code> ORIGINAL_AMOUNT column of table CLW_ORDER */
    public static final String ORIGINAL_AMOUNT = "ORIGINAL_AMOUNT";
    /** <code>TOTAL_PRICE</code> TOTAL_PRICE column of table CLW_ORDER */
    public static final String TOTAL_PRICE = "TOTAL_PRICE";
    /** <code>TOTAL_FREIGHT_COST</code> TOTAL_FREIGHT_COST column of table CLW_ORDER */
    public static final String TOTAL_FREIGHT_COST = "TOTAL_FREIGHT_COST";
    /** <code>TOTAL_MISC_COST</code> TOTAL_MISC_COST column of table CLW_ORDER */
    public static final String TOTAL_MISC_COST = "TOTAL_MISC_COST";
    /** <code>TOTAL_TAX_COST</code> TOTAL_TAX_COST column of table CLW_ORDER */
    public static final String TOTAL_TAX_COST = "TOTAL_TAX_COST";
    /** <code>TOTAL_CLEANWISE_COST</code> TOTAL_CLEANWISE_COST column of table CLW_ORDER */
    public static final String TOTAL_CLEANWISE_COST = "TOTAL_CLEANWISE_COST";
    /** <code>GROSS_WEIGHT</code> GROSS_WEIGHT column of table CLW_ORDER */
    public static final String GROSS_WEIGHT = "GROSS_WEIGHT";
    /** <code>ORIGINAL_ORDER_DATE</code> ORIGINAL_ORDER_DATE column of table CLW_ORDER */
    public static final String ORIGINAL_ORDER_DATE = "ORIGINAL_ORDER_DATE";
    /** <code>ORIGINAL_ORDER_TIME</code> ORIGINAL_ORDER_TIME column of table CLW_ORDER */
    public static final String ORIGINAL_ORDER_TIME = "ORIGINAL_ORDER_TIME";
    /** <code>REVISED_ORDER_DATE</code> REVISED_ORDER_DATE column of table CLW_ORDER */
    public static final String REVISED_ORDER_DATE = "REVISED_ORDER_DATE";
    /** <code>REVISED_ORDER_TIME</code> REVISED_ORDER_TIME column of table CLW_ORDER */
    public static final String REVISED_ORDER_TIME = "REVISED_ORDER_TIME";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_ORDER */
    public static final String COMMENTS = "COMMENTS";
    /** <code>INCOMING_TRADING_PROFILE_ID</code> INCOMING_TRADING_PROFILE_ID column of table CLW_ORDER */
    public static final String INCOMING_TRADING_PROFILE_ID = "INCOMING_TRADING_PROFILE_ID";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_ORDER */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>CURRENCY_CD</code> CURRENCY_CD column of table CLW_ORDER */
    public static final String CURRENCY_CD = "CURRENCY_CD";
    /** <code>ERP_ORDER_NUM</code> ERP_ORDER_NUM column of table CLW_ORDER */
    public static final String ERP_ORDER_NUM = "ERP_ORDER_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_ORDER */
    public static final String SITE_ID = "SITE_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_ORDER */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>STORE_ID</code> STORE_ID column of table CLW_ORDER */
    public static final String STORE_ID = "STORE_ID";
    /** <code>ERP_SYSTEM_CD</code> ERP_SYSTEM_CD column of table CLW_ORDER */
    public static final String ERP_SYSTEM_CD = "ERP_SYSTEM_CD";
    /** <code>PRE_ORDER_ID</code> PRE_ORDER_ID column of table CLW_ORDER */
    public static final String PRE_ORDER_ID = "PRE_ORDER_ID";
    /** <code>REF_ORDER_ID</code> REF_ORDER_ID column of table CLW_ORDER */
    public static final String REF_ORDER_ID = "REF_ORDER_ID";
    /** <code>TOTAL_RUSH_CHARGE</code> TOTAL_RUSH_CHARGE column of table CLW_ORDER */
    public static final String TOTAL_RUSH_CHARGE = "TOTAL_RUSH_CHARGE";
    /** <code>ORDER_BUDGET_TYPE_CD</code> ORDER_BUDGET_TYPE_CD column of table CLW_ORDER */
    public static final String ORDER_BUDGET_TYPE_CD = "ORDER_BUDGET_TYPE_CD";
    /** <code>ERP_ORDER_DATE</code> ERP_ORDER_DATE column of table CLW_ORDER */
    public static final String ERP_ORDER_DATE = "ERP_ORDER_DATE";

    /**
     * Constructor.
     */
    public OrderDataAccess()
    {
    }

    /**
     * Gets a OrderData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderId The key requested.
     * @return new OrderData()
     * @throws            SQLException
     */
    public static OrderData select(Connection pCon, int pOrderId)
        throws SQLException, DataNotFoundException {
        OrderData x=null;
        String sql="SELECT ORDER_ID,EXCEPTION_IND,ORDER_NUM,REF_ORDER_NUM,COST_CENTER_ID,COST_CENTER_NAME,WORKFLOW_IND,WORKFLOW_STATUS_CD,ACCOUNT_ERP_NUM,SITE_ERP_NUM,REQUEST_PO_NUM,USER_ID,USER_FIRST_NAME,USER_LAST_NAME,ORDER_SITE_NAME,ORDER_CONTACT_NAME,ORDER_CONTACT_PHONE_NUM,ORDER_CONTACT_EMAIL,ORDER_CONTACT_FAX_NUM,CONTRACT_ID,CONTRACT_SHORT_DESC,ORDER_TYPE_CD,ORDER_SOURCE_CD,ORDER_STATUS_CD,TAX_NUM,ORIGINAL_AMOUNT,TOTAL_PRICE,TOTAL_FREIGHT_COST,TOTAL_MISC_COST,TOTAL_TAX_COST,TOTAL_CLEANWISE_COST,GROSS_WEIGHT,ORIGINAL_ORDER_DATE,ORIGINAL_ORDER_TIME,REVISED_ORDER_DATE,REVISED_ORDER_TIME,COMMENTS,INCOMING_TRADING_PROFILE_ID,LOCALE_CD,CURRENCY_CD,ERP_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ACCOUNT_ID,STORE_ID,ERP_SYSTEM_CD,PRE_ORDER_ID,REF_ORDER_ID,TOTAL_RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,ERP_ORDER_DATE FROM CLW_ORDER WHERE ORDER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderId=" + pOrderId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderData.createValue();
            
            x.setOrderId(rs.getInt(1));
            x.setExceptionInd(rs.getString(2));
            x.setOrderNum(rs.getString(3));
            x.setRefOrderNum(rs.getString(4));
            x.setCostCenterId(rs.getInt(5));
            x.setCostCenterName(rs.getString(6));
            x.setWorkflowInd(rs.getString(7));
            x.setWorkflowStatusCd(rs.getString(8));
            x.setAccountErpNum(rs.getString(9));
            x.setSiteErpNum(rs.getString(10));
            x.setRequestPoNum(rs.getString(11));
            x.setUserId(rs.getInt(12));
            x.setUserFirstName(rs.getString(13));
            x.setUserLastName(rs.getString(14));
            x.setOrderSiteName(rs.getString(15));
            x.setOrderContactName(rs.getString(16));
            x.setOrderContactPhoneNum(rs.getString(17));
            x.setOrderContactEmail(rs.getString(18));
            x.setOrderContactFaxNum(rs.getString(19));
            x.setContractId(rs.getInt(20));
            x.setContractShortDesc(rs.getString(21));
            x.setOrderTypeCd(rs.getString(22));
            x.setOrderSourceCd(rs.getString(23));
            x.setOrderStatusCd(rs.getString(24));
            x.setTaxNum(rs.getString(25));
            x.setOriginalAmount(rs.getBigDecimal(26));
            x.setTotalPrice(rs.getBigDecimal(27));
            x.setTotalFreightCost(rs.getBigDecimal(28));
            x.setTotalMiscCost(rs.getBigDecimal(29));
            x.setTotalTaxCost(rs.getBigDecimal(30));
            x.setTotalCleanwiseCost(rs.getBigDecimal(31));
            x.setGrossWeight(rs.getBigDecimal(32));
            x.setOriginalOrderDate(rs.getDate(33));
            x.setOriginalOrderTime(rs.getTimestamp(34));
            x.setRevisedOrderDate(rs.getDate(35));
            x.setRevisedOrderTime(rs.getTimestamp(36));
            x.setComments(rs.getString(37));
            x.setIncomingTradingProfileId(rs.getInt(38));
            x.setLocaleCd(rs.getString(39));
            x.setCurrencyCd(rs.getString(40));
            x.setErpOrderNum(rs.getInt(41));
            x.setAddDate(rs.getTimestamp(42));
            x.setAddBy(rs.getString(43));
            x.setModDate(rs.getTimestamp(44));
            x.setModBy(rs.getString(45));
            x.setSiteId(rs.getInt(46));
            x.setAccountId(rs.getInt(47));
            x.setStoreId(rs.getInt(48));
            x.setErpSystemCd(rs.getString(49));
            x.setPreOrderId(rs.getInt(50));
            x.setRefOrderId(rs.getInt(51));
            x.setTotalRushCharge(rs.getBigDecimal(52));
            x.setOrderBudgetTypeCd(rs.getString(53));
            x.setErpOrderDate(rs.getDate(54));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_ID :" + pOrderId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderDataVector object that consists
     * of OrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderDataVector()
     * @throws            SQLException
     */
    public static OrderDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER.ORDER_ID,CLW_ORDER.EXCEPTION_IND,CLW_ORDER.ORDER_NUM,CLW_ORDER.REF_ORDER_NUM,CLW_ORDER.COST_CENTER_ID,CLW_ORDER.COST_CENTER_NAME,CLW_ORDER.WORKFLOW_IND,CLW_ORDER.WORKFLOW_STATUS_CD,CLW_ORDER.ACCOUNT_ERP_NUM,CLW_ORDER.SITE_ERP_NUM,CLW_ORDER.REQUEST_PO_NUM,CLW_ORDER.USER_ID,CLW_ORDER.USER_FIRST_NAME,CLW_ORDER.USER_LAST_NAME,CLW_ORDER.ORDER_SITE_NAME,CLW_ORDER.ORDER_CONTACT_NAME,CLW_ORDER.ORDER_CONTACT_PHONE_NUM,CLW_ORDER.ORDER_CONTACT_EMAIL,CLW_ORDER.ORDER_CONTACT_FAX_NUM,CLW_ORDER.CONTRACT_ID,CLW_ORDER.CONTRACT_SHORT_DESC,CLW_ORDER.ORDER_TYPE_CD,CLW_ORDER.ORDER_SOURCE_CD,CLW_ORDER.ORDER_STATUS_CD,CLW_ORDER.TAX_NUM,CLW_ORDER.ORIGINAL_AMOUNT,CLW_ORDER.TOTAL_PRICE,CLW_ORDER.TOTAL_FREIGHT_COST,CLW_ORDER.TOTAL_MISC_COST,CLW_ORDER.TOTAL_TAX_COST,CLW_ORDER.TOTAL_CLEANWISE_COST,CLW_ORDER.GROSS_WEIGHT,CLW_ORDER.ORIGINAL_ORDER_DATE,CLW_ORDER.ORIGINAL_ORDER_TIME,CLW_ORDER.REVISED_ORDER_DATE,CLW_ORDER.REVISED_ORDER_TIME,CLW_ORDER.COMMENTS,CLW_ORDER.INCOMING_TRADING_PROFILE_ID,CLW_ORDER.LOCALE_CD,CLW_ORDER.CURRENCY_CD,CLW_ORDER.ERP_ORDER_NUM,CLW_ORDER.ADD_DATE,CLW_ORDER.ADD_BY,CLW_ORDER.MOD_DATE,CLW_ORDER.MOD_BY,CLW_ORDER.SITE_ID,CLW_ORDER.ACCOUNT_ID,CLW_ORDER.STORE_ID,CLW_ORDER.ERP_SYSTEM_CD,CLW_ORDER.PRE_ORDER_ID,CLW_ORDER.REF_ORDER_ID,CLW_ORDER.TOTAL_RUSH_CHARGE,CLW_ORDER.ORDER_BUDGET_TYPE_CD,CLW_ORDER.ERP_ORDER_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderData Object.
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
    *@returns a populated OrderData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderData x = OrderData.createValue();
         
         x.setOrderId(rs.getInt(1+offset));
         x.setExceptionInd(rs.getString(2+offset));
         x.setOrderNum(rs.getString(3+offset));
         x.setRefOrderNum(rs.getString(4+offset));
         x.setCostCenterId(rs.getInt(5+offset));
         x.setCostCenterName(rs.getString(6+offset));
         x.setWorkflowInd(rs.getString(7+offset));
         x.setWorkflowStatusCd(rs.getString(8+offset));
         x.setAccountErpNum(rs.getString(9+offset));
         x.setSiteErpNum(rs.getString(10+offset));
         x.setRequestPoNum(rs.getString(11+offset));
         x.setUserId(rs.getInt(12+offset));
         x.setUserFirstName(rs.getString(13+offset));
         x.setUserLastName(rs.getString(14+offset));
         x.setOrderSiteName(rs.getString(15+offset));
         x.setOrderContactName(rs.getString(16+offset));
         x.setOrderContactPhoneNum(rs.getString(17+offset));
         x.setOrderContactEmail(rs.getString(18+offset));
         x.setOrderContactFaxNum(rs.getString(19+offset));
         x.setContractId(rs.getInt(20+offset));
         x.setContractShortDesc(rs.getString(21+offset));
         x.setOrderTypeCd(rs.getString(22+offset));
         x.setOrderSourceCd(rs.getString(23+offset));
         x.setOrderStatusCd(rs.getString(24+offset));
         x.setTaxNum(rs.getString(25+offset));
         x.setOriginalAmount(rs.getBigDecimal(26+offset));
         x.setTotalPrice(rs.getBigDecimal(27+offset));
         x.setTotalFreightCost(rs.getBigDecimal(28+offset));
         x.setTotalMiscCost(rs.getBigDecimal(29+offset));
         x.setTotalTaxCost(rs.getBigDecimal(30+offset));
         x.setTotalCleanwiseCost(rs.getBigDecimal(31+offset));
         x.setGrossWeight(rs.getBigDecimal(32+offset));
         x.setOriginalOrderDate(rs.getDate(33+offset));
         x.setOriginalOrderTime(rs.getTimestamp(34+offset));
         x.setRevisedOrderDate(rs.getDate(35+offset));
         x.setRevisedOrderTime(rs.getTimestamp(36+offset));
         x.setComments(rs.getString(37+offset));
         x.setIncomingTradingProfileId(rs.getInt(38+offset));
         x.setLocaleCd(rs.getString(39+offset));
         x.setCurrencyCd(rs.getString(40+offset));
         x.setErpOrderNum(rs.getInt(41+offset));
         x.setAddDate(rs.getTimestamp(42+offset));
         x.setAddBy(rs.getString(43+offset));
         x.setModDate(rs.getTimestamp(44+offset));
         x.setModBy(rs.getString(45+offset));
         x.setSiteId(rs.getInt(46+offset));
         x.setAccountId(rs.getInt(47+offset));
         x.setStoreId(rs.getInt(48+offset));
         x.setErpSystemCd(rs.getString(49+offset));
         x.setPreOrderId(rs.getInt(50+offset));
         x.setRefOrderId(rs.getInt(51+offset));
         x.setTotalRushCharge(rs.getBigDecimal(52+offset));
         x.setOrderBudgetTypeCd(rs.getString(53+offset));
         x.setErpOrderDate(rs.getDate(54+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderData Object represents.
    */
    public int getColumnCount(){
        return 54;
    }

    /**
     * Gets a OrderDataVector object that consists
     * of OrderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderDataVector()
     * @throws            SQLException
     */
    public static OrderDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_ID,EXCEPTION_IND,ORDER_NUM,REF_ORDER_NUM,COST_CENTER_ID,COST_CENTER_NAME,WORKFLOW_IND,WORKFLOW_STATUS_CD,ACCOUNT_ERP_NUM,SITE_ERP_NUM,REQUEST_PO_NUM,USER_ID,USER_FIRST_NAME,USER_LAST_NAME,ORDER_SITE_NAME,ORDER_CONTACT_NAME,ORDER_CONTACT_PHONE_NUM,ORDER_CONTACT_EMAIL,ORDER_CONTACT_FAX_NUM,CONTRACT_ID,CONTRACT_SHORT_DESC,ORDER_TYPE_CD,ORDER_SOURCE_CD,ORDER_STATUS_CD,TAX_NUM,ORIGINAL_AMOUNT,TOTAL_PRICE,TOTAL_FREIGHT_COST,TOTAL_MISC_COST,TOTAL_TAX_COST,TOTAL_CLEANWISE_COST,GROSS_WEIGHT,ORIGINAL_ORDER_DATE,ORIGINAL_ORDER_TIME,REVISED_ORDER_DATE,REVISED_ORDER_TIME,COMMENTS,INCOMING_TRADING_PROFILE_ID,LOCALE_CD,CURRENCY_CD,ERP_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ACCOUNT_ID,STORE_ID,ERP_SYSTEM_CD,PRE_ORDER_ID,REF_ORDER_ID,TOTAL_RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,ERP_ORDER_DATE FROM CLW_ORDER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER.ORDER_ID,CLW_ORDER.EXCEPTION_IND,CLW_ORDER.ORDER_NUM,CLW_ORDER.REF_ORDER_NUM,CLW_ORDER.COST_CENTER_ID,CLW_ORDER.COST_CENTER_NAME,CLW_ORDER.WORKFLOW_IND,CLW_ORDER.WORKFLOW_STATUS_CD,CLW_ORDER.ACCOUNT_ERP_NUM,CLW_ORDER.SITE_ERP_NUM,CLW_ORDER.REQUEST_PO_NUM,CLW_ORDER.USER_ID,CLW_ORDER.USER_FIRST_NAME,CLW_ORDER.USER_LAST_NAME,CLW_ORDER.ORDER_SITE_NAME,CLW_ORDER.ORDER_CONTACT_NAME,CLW_ORDER.ORDER_CONTACT_PHONE_NUM,CLW_ORDER.ORDER_CONTACT_EMAIL,CLW_ORDER.ORDER_CONTACT_FAX_NUM,CLW_ORDER.CONTRACT_ID,CLW_ORDER.CONTRACT_SHORT_DESC,CLW_ORDER.ORDER_TYPE_CD,CLW_ORDER.ORDER_SOURCE_CD,CLW_ORDER.ORDER_STATUS_CD,CLW_ORDER.TAX_NUM,CLW_ORDER.ORIGINAL_AMOUNT,CLW_ORDER.TOTAL_PRICE,CLW_ORDER.TOTAL_FREIGHT_COST,CLW_ORDER.TOTAL_MISC_COST,CLW_ORDER.TOTAL_TAX_COST,CLW_ORDER.TOTAL_CLEANWISE_COST,CLW_ORDER.GROSS_WEIGHT,CLW_ORDER.ORIGINAL_ORDER_DATE,CLW_ORDER.ORIGINAL_ORDER_TIME,CLW_ORDER.REVISED_ORDER_DATE,CLW_ORDER.REVISED_ORDER_TIME,CLW_ORDER.COMMENTS,CLW_ORDER.INCOMING_TRADING_PROFILE_ID,CLW_ORDER.LOCALE_CD,CLW_ORDER.CURRENCY_CD,CLW_ORDER.ERP_ORDER_NUM,CLW_ORDER.ADD_DATE,CLW_ORDER.ADD_BY,CLW_ORDER.MOD_DATE,CLW_ORDER.MOD_BY,CLW_ORDER.SITE_ID,CLW_ORDER.ACCOUNT_ID,CLW_ORDER.STORE_ID,CLW_ORDER.ERP_SYSTEM_CD,CLW_ORDER.PRE_ORDER_ID,CLW_ORDER.REF_ORDER_ID,CLW_ORDER.TOTAL_RUSH_CHARGE,CLW_ORDER.ORDER_BUDGET_TYPE_CD,CLW_ORDER.ERP_ORDER_DATE FROM CLW_ORDER");
                where = pCriteria.getSqlClause("CLW_ORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER.equals(otherTable)){
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
        OrderDataVector v = new OrderDataVector();
        while (rs.next()) {
            OrderData x = OrderData.createValue();
            
            x.setOrderId(rs.getInt(1));
            x.setExceptionInd(rs.getString(2));
            x.setOrderNum(rs.getString(3));
            x.setRefOrderNum(rs.getString(4));
            x.setCostCenterId(rs.getInt(5));
            x.setCostCenterName(rs.getString(6));
            x.setWorkflowInd(rs.getString(7));
            x.setWorkflowStatusCd(rs.getString(8));
            x.setAccountErpNum(rs.getString(9));
            x.setSiteErpNum(rs.getString(10));
            x.setRequestPoNum(rs.getString(11));
            x.setUserId(rs.getInt(12));
            x.setUserFirstName(rs.getString(13));
            x.setUserLastName(rs.getString(14));
            x.setOrderSiteName(rs.getString(15));
            x.setOrderContactName(rs.getString(16));
            x.setOrderContactPhoneNum(rs.getString(17));
            x.setOrderContactEmail(rs.getString(18));
            x.setOrderContactFaxNum(rs.getString(19));
            x.setContractId(rs.getInt(20));
            x.setContractShortDesc(rs.getString(21));
            x.setOrderTypeCd(rs.getString(22));
            x.setOrderSourceCd(rs.getString(23));
            x.setOrderStatusCd(rs.getString(24));
            x.setTaxNum(rs.getString(25));
            x.setOriginalAmount(rs.getBigDecimal(26));
            x.setTotalPrice(rs.getBigDecimal(27));
            x.setTotalFreightCost(rs.getBigDecimal(28));
            x.setTotalMiscCost(rs.getBigDecimal(29));
            x.setTotalTaxCost(rs.getBigDecimal(30));
            x.setTotalCleanwiseCost(rs.getBigDecimal(31));
            x.setGrossWeight(rs.getBigDecimal(32));
            x.setOriginalOrderDate(rs.getDate(33));
            x.setOriginalOrderTime(rs.getTimestamp(34));
            x.setRevisedOrderDate(rs.getDate(35));
            x.setRevisedOrderTime(rs.getTimestamp(36));
            x.setComments(rs.getString(37));
            x.setIncomingTradingProfileId(rs.getInt(38));
            x.setLocaleCd(rs.getString(39));
            x.setCurrencyCd(rs.getString(40));
            x.setErpOrderNum(rs.getInt(41));
            x.setAddDate(rs.getTimestamp(42));
            x.setAddBy(rs.getString(43));
            x.setModDate(rs.getTimestamp(44));
            x.setModBy(rs.getString(45));
            x.setSiteId(rs.getInt(46));
            x.setAccountId(rs.getInt(47));
            x.setStoreId(rs.getInt(48));
            x.setErpSystemCd(rs.getString(49));
            x.setPreOrderId(rs.getInt(50));
            x.setRefOrderId(rs.getInt(51));
            x.setTotalRushCharge(rs.getBigDecimal(52));
            x.setOrderBudgetTypeCd(rs.getString(53));
            x.setErpOrderDate(rs.getDate(54));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderDataVector object that consists
     * of OrderData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderDataVector()
     * @throws            SQLException
     */
    public static OrderDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderDataVector v = new OrderDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ID,EXCEPTION_IND,ORDER_NUM,REF_ORDER_NUM,COST_CENTER_ID,COST_CENTER_NAME,WORKFLOW_IND,WORKFLOW_STATUS_CD,ACCOUNT_ERP_NUM,SITE_ERP_NUM,REQUEST_PO_NUM,USER_ID,USER_FIRST_NAME,USER_LAST_NAME,ORDER_SITE_NAME,ORDER_CONTACT_NAME,ORDER_CONTACT_PHONE_NUM,ORDER_CONTACT_EMAIL,ORDER_CONTACT_FAX_NUM,CONTRACT_ID,CONTRACT_SHORT_DESC,ORDER_TYPE_CD,ORDER_SOURCE_CD,ORDER_STATUS_CD,TAX_NUM,ORIGINAL_AMOUNT,TOTAL_PRICE,TOTAL_FREIGHT_COST,TOTAL_MISC_COST,TOTAL_TAX_COST,TOTAL_CLEANWISE_COST,GROSS_WEIGHT,ORIGINAL_ORDER_DATE,ORIGINAL_ORDER_TIME,REVISED_ORDER_DATE,REVISED_ORDER_TIME,COMMENTS,INCOMING_TRADING_PROFILE_ID,LOCALE_CD,CURRENCY_CD,ERP_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ACCOUNT_ID,STORE_ID,ERP_SYSTEM_CD,PRE_ORDER_ID,REF_ORDER_ID,TOTAL_RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,ERP_ORDER_DATE FROM CLW_ORDER WHERE ORDER_ID IN (");

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
            OrderData x=null;
            while (rs.next()) {
                // build the object
                x=OrderData.createValue();
                
                x.setOrderId(rs.getInt(1));
                x.setExceptionInd(rs.getString(2));
                x.setOrderNum(rs.getString(3));
                x.setRefOrderNum(rs.getString(4));
                x.setCostCenterId(rs.getInt(5));
                x.setCostCenterName(rs.getString(6));
                x.setWorkflowInd(rs.getString(7));
                x.setWorkflowStatusCd(rs.getString(8));
                x.setAccountErpNum(rs.getString(9));
                x.setSiteErpNum(rs.getString(10));
                x.setRequestPoNum(rs.getString(11));
                x.setUserId(rs.getInt(12));
                x.setUserFirstName(rs.getString(13));
                x.setUserLastName(rs.getString(14));
                x.setOrderSiteName(rs.getString(15));
                x.setOrderContactName(rs.getString(16));
                x.setOrderContactPhoneNum(rs.getString(17));
                x.setOrderContactEmail(rs.getString(18));
                x.setOrderContactFaxNum(rs.getString(19));
                x.setContractId(rs.getInt(20));
                x.setContractShortDesc(rs.getString(21));
                x.setOrderTypeCd(rs.getString(22));
                x.setOrderSourceCd(rs.getString(23));
                x.setOrderStatusCd(rs.getString(24));
                x.setTaxNum(rs.getString(25));
                x.setOriginalAmount(rs.getBigDecimal(26));
                x.setTotalPrice(rs.getBigDecimal(27));
                x.setTotalFreightCost(rs.getBigDecimal(28));
                x.setTotalMiscCost(rs.getBigDecimal(29));
                x.setTotalTaxCost(rs.getBigDecimal(30));
                x.setTotalCleanwiseCost(rs.getBigDecimal(31));
                x.setGrossWeight(rs.getBigDecimal(32));
                x.setOriginalOrderDate(rs.getDate(33));
                x.setOriginalOrderTime(rs.getTimestamp(34));
                x.setRevisedOrderDate(rs.getDate(35));
                x.setRevisedOrderTime(rs.getTimestamp(36));
                x.setComments(rs.getString(37));
                x.setIncomingTradingProfileId(rs.getInt(38));
                x.setLocaleCd(rs.getString(39));
                x.setCurrencyCd(rs.getString(40));
                x.setErpOrderNum(rs.getInt(41));
                x.setAddDate(rs.getTimestamp(42));
                x.setAddBy(rs.getString(43));
                x.setModDate(rs.getTimestamp(44));
                x.setModBy(rs.getString(45));
                x.setSiteId(rs.getInt(46));
                x.setAccountId(rs.getInt(47));
                x.setStoreId(rs.getInt(48));
                x.setErpSystemCd(rs.getString(49));
                x.setPreOrderId(rs.getInt(50));
                x.setRefOrderId(rs.getInt(51));
                x.setTotalRushCharge(rs.getBigDecimal(52));
                x.setOrderBudgetTypeCd(rs.getString(53));
                x.setErpOrderDate(rs.getDate(54));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderDataVector object of all
     * OrderData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderDataVector()
     * @throws            SQLException
     */
    public static OrderDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_ID,EXCEPTION_IND,ORDER_NUM,REF_ORDER_NUM,COST_CENTER_ID,COST_CENTER_NAME,WORKFLOW_IND,WORKFLOW_STATUS_CD,ACCOUNT_ERP_NUM,SITE_ERP_NUM,REQUEST_PO_NUM,USER_ID,USER_FIRST_NAME,USER_LAST_NAME,ORDER_SITE_NAME,ORDER_CONTACT_NAME,ORDER_CONTACT_PHONE_NUM,ORDER_CONTACT_EMAIL,ORDER_CONTACT_FAX_NUM,CONTRACT_ID,CONTRACT_SHORT_DESC,ORDER_TYPE_CD,ORDER_SOURCE_CD,ORDER_STATUS_CD,TAX_NUM,ORIGINAL_AMOUNT,TOTAL_PRICE,TOTAL_FREIGHT_COST,TOTAL_MISC_COST,TOTAL_TAX_COST,TOTAL_CLEANWISE_COST,GROSS_WEIGHT,ORIGINAL_ORDER_DATE,ORIGINAL_ORDER_TIME,REVISED_ORDER_DATE,REVISED_ORDER_TIME,COMMENTS,INCOMING_TRADING_PROFILE_ID,LOCALE_CD,CURRENCY_CD,ERP_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ACCOUNT_ID,STORE_ID,ERP_SYSTEM_CD,PRE_ORDER_ID,REF_ORDER_ID,TOTAL_RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,ERP_ORDER_DATE FROM CLW_ORDER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderDataVector v = new OrderDataVector();
        OrderData x = null;
        while (rs.next()) {
            // build the object
            x = OrderData.createValue();
            
            x.setOrderId(rs.getInt(1));
            x.setExceptionInd(rs.getString(2));
            x.setOrderNum(rs.getString(3));
            x.setRefOrderNum(rs.getString(4));
            x.setCostCenterId(rs.getInt(5));
            x.setCostCenterName(rs.getString(6));
            x.setWorkflowInd(rs.getString(7));
            x.setWorkflowStatusCd(rs.getString(8));
            x.setAccountErpNum(rs.getString(9));
            x.setSiteErpNum(rs.getString(10));
            x.setRequestPoNum(rs.getString(11));
            x.setUserId(rs.getInt(12));
            x.setUserFirstName(rs.getString(13));
            x.setUserLastName(rs.getString(14));
            x.setOrderSiteName(rs.getString(15));
            x.setOrderContactName(rs.getString(16));
            x.setOrderContactPhoneNum(rs.getString(17));
            x.setOrderContactEmail(rs.getString(18));
            x.setOrderContactFaxNum(rs.getString(19));
            x.setContractId(rs.getInt(20));
            x.setContractShortDesc(rs.getString(21));
            x.setOrderTypeCd(rs.getString(22));
            x.setOrderSourceCd(rs.getString(23));
            x.setOrderStatusCd(rs.getString(24));
            x.setTaxNum(rs.getString(25));
            x.setOriginalAmount(rs.getBigDecimal(26));
            x.setTotalPrice(rs.getBigDecimal(27));
            x.setTotalFreightCost(rs.getBigDecimal(28));
            x.setTotalMiscCost(rs.getBigDecimal(29));
            x.setTotalTaxCost(rs.getBigDecimal(30));
            x.setTotalCleanwiseCost(rs.getBigDecimal(31));
            x.setGrossWeight(rs.getBigDecimal(32));
            x.setOriginalOrderDate(rs.getDate(33));
            x.setOriginalOrderTime(rs.getTimestamp(34));
            x.setRevisedOrderDate(rs.getDate(35));
            x.setRevisedOrderTime(rs.getTimestamp(36));
            x.setComments(rs.getString(37));
            x.setIncomingTradingProfileId(rs.getInt(38));
            x.setLocaleCd(rs.getString(39));
            x.setCurrencyCd(rs.getString(40));
            x.setErpOrderNum(rs.getInt(41));
            x.setAddDate(rs.getTimestamp(42));
            x.setAddBy(rs.getString(43));
            x.setModDate(rs.getTimestamp(44));
            x.setModBy(rs.getString(45));
            x.setSiteId(rs.getInt(46));
            x.setAccountId(rs.getInt(47));
            x.setStoreId(rs.getInt(48));
            x.setErpSystemCd(rs.getString(49));
            x.setPreOrderId(rs.getInt(50));
            x.setRefOrderId(rs.getInt(51));
            x.setTotalRushCharge(rs.getBigDecimal(52));
            x.setOrderBudgetTypeCd(rs.getString(53));
            x.setErpOrderDate(rs.getDate(54));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ID FROM CLW_ORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER");
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
     * Inserts a OrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderData insert(Connection pCon, OrderData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER (ORDER_ID,EXCEPTION_IND,ORDER_NUM,REF_ORDER_NUM,COST_CENTER_ID,COST_CENTER_NAME,WORKFLOW_IND,WORKFLOW_STATUS_CD,ACCOUNT_ERP_NUM,SITE_ERP_NUM,REQUEST_PO_NUM,USER_ID,USER_FIRST_NAME,USER_LAST_NAME,ORDER_SITE_NAME,ORDER_CONTACT_NAME,ORDER_CONTACT_PHONE_NUM,ORDER_CONTACT_EMAIL,ORDER_CONTACT_FAX_NUM,CONTRACT_ID,CONTRACT_SHORT_DESC,ORDER_TYPE_CD,ORDER_SOURCE_CD,ORDER_STATUS_CD,TAX_NUM,ORIGINAL_AMOUNT,TOTAL_PRICE,TOTAL_FREIGHT_COST,TOTAL_MISC_COST,TOTAL_TAX_COST,TOTAL_CLEANWISE_COST,GROSS_WEIGHT,ORIGINAL_ORDER_DATE,ORIGINAL_ORDER_TIME,REVISED_ORDER_DATE,REVISED_ORDER_TIME,COMMENTS,INCOMING_TRADING_PROFILE_ID,LOCALE_CD,CURRENCY_CD,ERP_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ACCOUNT_ID,STORE_ID,ERP_SYSTEM_CD,PRE_ORDER_ID,REF_ORDER_ID,TOTAL_RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,ERP_ORDER_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderId());
        pstmt.setString(2,pData.getExceptionInd());
        pstmt.setString(3,pData.getOrderNum());
        pstmt.setString(4,pData.getRefOrderNum());
        pstmt.setInt(5,pData.getCostCenterId());
        pstmt.setString(6,pData.getCostCenterName());
        pstmt.setString(7,pData.getWorkflowInd());
        pstmt.setString(8,pData.getWorkflowStatusCd());
        pstmt.setString(9,pData.getAccountErpNum());
        pstmt.setString(10,pData.getSiteErpNum());
        pstmt.setString(11,pData.getRequestPoNum());
        pstmt.setInt(12,pData.getUserId());
        pstmt.setString(13,pData.getUserFirstName());
        pstmt.setString(14,pData.getUserLastName());
        pstmt.setString(15,pData.getOrderSiteName());
        pstmt.setString(16,pData.getOrderContactName());
        pstmt.setString(17,pData.getOrderContactPhoneNum());
        pstmt.setString(18,pData.getOrderContactEmail());
        pstmt.setString(19,pData.getOrderContactFaxNum());
        if (pData.getContractId() == 0) {
            pstmt.setNull(20, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(20,pData.getContractId());
        }

        pstmt.setString(21,pData.getContractShortDesc());
        pstmt.setString(22,pData.getOrderTypeCd());
        pstmt.setString(23,pData.getOrderSourceCd());
        pstmt.setString(24,pData.getOrderStatusCd());
        pstmt.setString(25,pData.getTaxNum());
        pstmt.setBigDecimal(26,pData.getOriginalAmount());
        pstmt.setBigDecimal(27,pData.getTotalPrice());
        pstmt.setBigDecimal(28,pData.getTotalFreightCost());
        pstmt.setBigDecimal(29,pData.getTotalMiscCost());
        pstmt.setBigDecimal(30,pData.getTotalTaxCost());
        pstmt.setBigDecimal(31,pData.getTotalCleanwiseCost());
        pstmt.setBigDecimal(32,pData.getGrossWeight());
        pstmt.setDate(33,DBAccess.toSQLDate(pData.getOriginalOrderDate()));
        pstmt.setTimestamp(34,DBAccess.toSQLTimestamp(pData.getOriginalOrderTime()));
        pstmt.setDate(35,DBAccess.toSQLDate(pData.getRevisedOrderDate()));
        pstmt.setTimestamp(36,DBAccess.toSQLTimestamp(pData.getRevisedOrderTime()));
        pstmt.setString(37,pData.getComments());
        pstmt.setInt(38,pData.getIncomingTradingProfileId());
        pstmt.setString(39,pData.getLocaleCd());
        pstmt.setString(40,pData.getCurrencyCd());
        pstmt.setInt(41,pData.getErpOrderNum());
        pstmt.setTimestamp(42,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(43,pData.getAddBy());
        pstmt.setTimestamp(44,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(45,pData.getModBy());
        pstmt.setInt(46,pData.getSiteId());
        pstmt.setInt(47,pData.getAccountId());
        pstmt.setInt(48,pData.getStoreId());
        pstmt.setString(49,pData.getErpSystemCd());
        if (pData.getPreOrderId() == 0) {
            pstmt.setNull(50, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(50,pData.getPreOrderId());
        }

        pstmt.setInt(51,pData.getRefOrderId());
        pstmt.setBigDecimal(52,pData.getTotalRushCharge());
        pstmt.setString(53,pData.getOrderBudgetTypeCd());
        pstmt.setDate(54,DBAccess.toSQLDate(pData.getErpOrderDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   EXCEPTION_IND="+pData.getExceptionInd());
            log.debug("SQL:   ORDER_NUM="+pData.getOrderNum());
            log.debug("SQL:   REF_ORDER_NUM="+pData.getRefOrderNum());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   COST_CENTER_NAME="+pData.getCostCenterName());
            log.debug("SQL:   WORKFLOW_IND="+pData.getWorkflowInd());
            log.debug("SQL:   WORKFLOW_STATUS_CD="+pData.getWorkflowStatusCd());
            log.debug("SQL:   ACCOUNT_ERP_NUM="+pData.getAccountErpNum());
            log.debug("SQL:   SITE_ERP_NUM="+pData.getSiteErpNum());
            log.debug("SQL:   REQUEST_PO_NUM="+pData.getRequestPoNum());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   USER_FIRST_NAME="+pData.getUserFirstName());
            log.debug("SQL:   USER_LAST_NAME="+pData.getUserLastName());
            log.debug("SQL:   ORDER_SITE_NAME="+pData.getOrderSiteName());
            log.debug("SQL:   ORDER_CONTACT_NAME="+pData.getOrderContactName());
            log.debug("SQL:   ORDER_CONTACT_PHONE_NUM="+pData.getOrderContactPhoneNum());
            log.debug("SQL:   ORDER_CONTACT_EMAIL="+pData.getOrderContactEmail());
            log.debug("SQL:   ORDER_CONTACT_FAX_NUM="+pData.getOrderContactFaxNum());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   CONTRACT_SHORT_DESC="+pData.getContractShortDesc());
            log.debug("SQL:   ORDER_TYPE_CD="+pData.getOrderTypeCd());
            log.debug("SQL:   ORDER_SOURCE_CD="+pData.getOrderSourceCd());
            log.debug("SQL:   ORDER_STATUS_CD="+pData.getOrderStatusCd());
            log.debug("SQL:   TAX_NUM="+pData.getTaxNum());
            log.debug("SQL:   ORIGINAL_AMOUNT="+pData.getOriginalAmount());
            log.debug("SQL:   TOTAL_PRICE="+pData.getTotalPrice());
            log.debug("SQL:   TOTAL_FREIGHT_COST="+pData.getTotalFreightCost());
            log.debug("SQL:   TOTAL_MISC_COST="+pData.getTotalMiscCost());
            log.debug("SQL:   TOTAL_TAX_COST="+pData.getTotalTaxCost());
            log.debug("SQL:   TOTAL_CLEANWISE_COST="+pData.getTotalCleanwiseCost());
            log.debug("SQL:   GROSS_WEIGHT="+pData.getGrossWeight());
            log.debug("SQL:   ORIGINAL_ORDER_DATE="+pData.getOriginalOrderDate());
            log.debug("SQL:   ORIGINAL_ORDER_TIME="+pData.getOriginalOrderTime());
            log.debug("SQL:   REVISED_ORDER_DATE="+pData.getRevisedOrderDate());
            log.debug("SQL:   REVISED_ORDER_TIME="+pData.getRevisedOrderTime());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   INCOMING_TRADING_PROFILE_ID="+pData.getIncomingTradingProfileId());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL:   ERP_ORDER_NUM="+pData.getErpOrderNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   REF_ORDER_ID="+pData.getRefOrderId());
            log.debug("SQL:   TOTAL_RUSH_CHARGE="+pData.getTotalRushCharge());
            log.debug("SQL:   ORDER_BUDGET_TYPE_CD="+pData.getOrderBudgetTypeCd());
            log.debug("SQL:   ERP_ORDER_DATE="+pData.getErpOrderDate());
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
        pData.setOrderId(0);
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
     * Updates a OrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER SET EXCEPTION_IND = ?,ORDER_NUM = ?,REF_ORDER_NUM = ?,COST_CENTER_ID = ?,COST_CENTER_NAME = ?,WORKFLOW_IND = ?,WORKFLOW_STATUS_CD = ?,ACCOUNT_ERP_NUM = ?,SITE_ERP_NUM = ?,REQUEST_PO_NUM = ?,USER_ID = ?,USER_FIRST_NAME = ?,USER_LAST_NAME = ?,ORDER_SITE_NAME = ?,ORDER_CONTACT_NAME = ?,ORDER_CONTACT_PHONE_NUM = ?,ORDER_CONTACT_EMAIL = ?,ORDER_CONTACT_FAX_NUM = ?,CONTRACT_ID = ?,CONTRACT_SHORT_DESC = ?,ORDER_TYPE_CD = ?,ORDER_SOURCE_CD = ?,ORDER_STATUS_CD = ?,TAX_NUM = ?,ORIGINAL_AMOUNT = ?,TOTAL_PRICE = ?,TOTAL_FREIGHT_COST = ?,TOTAL_MISC_COST = ?,TOTAL_TAX_COST = ?,TOTAL_CLEANWISE_COST = ?,GROSS_WEIGHT = ?,ORIGINAL_ORDER_DATE = ?,ORIGINAL_ORDER_TIME = ?,REVISED_ORDER_DATE = ?,REVISED_ORDER_TIME = ?,COMMENTS = ?,INCOMING_TRADING_PROFILE_ID = ?,LOCALE_CD = ?,CURRENCY_CD = ?,ERP_ORDER_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,SITE_ID = ?,ACCOUNT_ID = ?,STORE_ID = ?,ERP_SYSTEM_CD = ?,PRE_ORDER_ID = ?,REF_ORDER_ID = ?,TOTAL_RUSH_CHARGE = ?,ORDER_BUDGET_TYPE_CD = ?,ERP_ORDER_DATE = ? WHERE ORDER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getExceptionInd());
        pstmt.setString(i++,pData.getOrderNum());
        pstmt.setString(i++,pData.getRefOrderNum());
        pstmt.setInt(i++,pData.getCostCenterId());
        pstmt.setString(i++,pData.getCostCenterName());
        pstmt.setString(i++,pData.getWorkflowInd());
        pstmt.setString(i++,pData.getWorkflowStatusCd());
        pstmt.setString(i++,pData.getAccountErpNum());
        pstmt.setString(i++,pData.getSiteErpNum());
        pstmt.setString(i++,pData.getRequestPoNum());
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getUserFirstName());
        pstmt.setString(i++,pData.getUserLastName());
        pstmt.setString(i++,pData.getOrderSiteName());
        pstmt.setString(i++,pData.getOrderContactName());
        pstmt.setString(i++,pData.getOrderContactPhoneNum());
        pstmt.setString(i++,pData.getOrderContactEmail());
        pstmt.setString(i++,pData.getOrderContactFaxNum());
        if (pData.getContractId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getContractId());
        }

        pstmt.setString(i++,pData.getContractShortDesc());
        pstmt.setString(i++,pData.getOrderTypeCd());
        pstmt.setString(i++,pData.getOrderSourceCd());
        pstmt.setString(i++,pData.getOrderStatusCd());
        pstmt.setString(i++,pData.getTaxNum());
        pstmt.setBigDecimal(i++,pData.getOriginalAmount());
        pstmt.setBigDecimal(i++,pData.getTotalPrice());
        pstmt.setBigDecimal(i++,pData.getTotalFreightCost());
        pstmt.setBigDecimal(i++,pData.getTotalMiscCost());
        pstmt.setBigDecimal(i++,pData.getTotalTaxCost());
        pstmt.setBigDecimal(i++,pData.getTotalCleanwiseCost());
        pstmt.setBigDecimal(i++,pData.getGrossWeight());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOriginalOrderDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getOriginalOrderTime()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getRevisedOrderDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getRevisedOrderTime()));
        pstmt.setString(i++,pData.getComments());
        pstmt.setInt(i++,pData.getIncomingTradingProfileId());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setString(i++,pData.getCurrencyCd());
        pstmt.setInt(i++,pData.getErpOrderNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getSiteId());
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setInt(i++,pData.getStoreId());
        pstmt.setString(i++,pData.getErpSystemCd());
        if (pData.getPreOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getPreOrderId());
        }

        pstmt.setInt(i++,pData.getRefOrderId());
        pstmt.setBigDecimal(i++,pData.getTotalRushCharge());
        pstmt.setString(i++,pData.getOrderBudgetTypeCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getErpOrderDate()));
        pstmt.setInt(i++,pData.getOrderId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EXCEPTION_IND="+pData.getExceptionInd());
            log.debug("SQL:   ORDER_NUM="+pData.getOrderNum());
            log.debug("SQL:   REF_ORDER_NUM="+pData.getRefOrderNum());
            log.debug("SQL:   COST_CENTER_ID="+pData.getCostCenterId());
            log.debug("SQL:   COST_CENTER_NAME="+pData.getCostCenterName());
            log.debug("SQL:   WORKFLOW_IND="+pData.getWorkflowInd());
            log.debug("SQL:   WORKFLOW_STATUS_CD="+pData.getWorkflowStatusCd());
            log.debug("SQL:   ACCOUNT_ERP_NUM="+pData.getAccountErpNum());
            log.debug("SQL:   SITE_ERP_NUM="+pData.getSiteErpNum());
            log.debug("SQL:   REQUEST_PO_NUM="+pData.getRequestPoNum());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   USER_FIRST_NAME="+pData.getUserFirstName());
            log.debug("SQL:   USER_LAST_NAME="+pData.getUserLastName());
            log.debug("SQL:   ORDER_SITE_NAME="+pData.getOrderSiteName());
            log.debug("SQL:   ORDER_CONTACT_NAME="+pData.getOrderContactName());
            log.debug("SQL:   ORDER_CONTACT_PHONE_NUM="+pData.getOrderContactPhoneNum());
            log.debug("SQL:   ORDER_CONTACT_EMAIL="+pData.getOrderContactEmail());
            log.debug("SQL:   ORDER_CONTACT_FAX_NUM="+pData.getOrderContactFaxNum());
            log.debug("SQL:   CONTRACT_ID="+pData.getContractId());
            log.debug("SQL:   CONTRACT_SHORT_DESC="+pData.getContractShortDesc());
            log.debug("SQL:   ORDER_TYPE_CD="+pData.getOrderTypeCd());
            log.debug("SQL:   ORDER_SOURCE_CD="+pData.getOrderSourceCd());
            log.debug("SQL:   ORDER_STATUS_CD="+pData.getOrderStatusCd());
            log.debug("SQL:   TAX_NUM="+pData.getTaxNum());
            log.debug("SQL:   ORIGINAL_AMOUNT="+pData.getOriginalAmount());
            log.debug("SQL:   TOTAL_PRICE="+pData.getTotalPrice());
            log.debug("SQL:   TOTAL_FREIGHT_COST="+pData.getTotalFreightCost());
            log.debug("SQL:   TOTAL_MISC_COST="+pData.getTotalMiscCost());
            log.debug("SQL:   TOTAL_TAX_COST="+pData.getTotalTaxCost());
            log.debug("SQL:   TOTAL_CLEANWISE_COST="+pData.getTotalCleanwiseCost());
            log.debug("SQL:   GROSS_WEIGHT="+pData.getGrossWeight());
            log.debug("SQL:   ORIGINAL_ORDER_DATE="+pData.getOriginalOrderDate());
            log.debug("SQL:   ORIGINAL_ORDER_TIME="+pData.getOriginalOrderTime());
            log.debug("SQL:   REVISED_ORDER_DATE="+pData.getRevisedOrderDate());
            log.debug("SQL:   REVISED_ORDER_TIME="+pData.getRevisedOrderTime());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   INCOMING_TRADING_PROFILE_ID="+pData.getIncomingTradingProfileId());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL:   ERP_ORDER_NUM="+pData.getErpOrderNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   STORE_ID="+pData.getStoreId());
            log.debug("SQL:   ERP_SYSTEM_CD="+pData.getErpSystemCd());
            log.debug("SQL:   PRE_ORDER_ID="+pData.getPreOrderId());
            log.debug("SQL:   REF_ORDER_ID="+pData.getRefOrderId());
            log.debug("SQL:   TOTAL_RUSH_CHARGE="+pData.getTotalRushCharge());
            log.debug("SQL:   ORDER_BUDGET_TYPE_CD="+pData.getOrderBudgetTypeCd());
            log.debug("SQL:   ERP_ORDER_DATE="+pData.getErpOrderDate());
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
     * Deletes a OrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER WHERE ORDER_ID = " + pOrderId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER");
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
     * Inserts a OrderData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_ID,EXCEPTION_IND,ORDER_NUM,REF_ORDER_NUM,COST_CENTER_ID,COST_CENTER_NAME,WORKFLOW_IND,WORKFLOW_STATUS_CD,ACCOUNT_ERP_NUM,SITE_ERP_NUM,REQUEST_PO_NUM,USER_ID,USER_FIRST_NAME,USER_LAST_NAME,ORDER_SITE_NAME,ORDER_CONTACT_NAME,ORDER_CONTACT_PHONE_NUM,ORDER_CONTACT_EMAIL,ORDER_CONTACT_FAX_NUM,CONTRACT_ID,CONTRACT_SHORT_DESC,ORDER_TYPE_CD,ORDER_SOURCE_CD,ORDER_STATUS_CD,TAX_NUM,ORIGINAL_AMOUNT,TOTAL_PRICE,TOTAL_FREIGHT_COST,TOTAL_MISC_COST,TOTAL_TAX_COST,TOTAL_CLEANWISE_COST,GROSS_WEIGHT,ORIGINAL_ORDER_DATE,ORIGINAL_ORDER_TIME,REVISED_ORDER_DATE,REVISED_ORDER_TIME,COMMENTS,INCOMING_TRADING_PROFILE_ID,LOCALE_CD,CURRENCY_CD,ERP_ORDER_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SITE_ID,ACCOUNT_ID,STORE_ID,ERP_SYSTEM_CD,PRE_ORDER_ID,REF_ORDER_ID,TOTAL_RUSH_CHARGE,ORDER_BUDGET_TYPE_CD,ERP_ORDER_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderId());
        pstmt.setString(2+4,pData.getExceptionInd());
        pstmt.setString(3+4,pData.getOrderNum());
        pstmt.setString(4+4,pData.getRefOrderNum());
        pstmt.setInt(5+4,pData.getCostCenterId());
        pstmt.setString(6+4,pData.getCostCenterName());
        pstmt.setString(7+4,pData.getWorkflowInd());
        pstmt.setString(8+4,pData.getWorkflowStatusCd());
        pstmt.setString(9+4,pData.getAccountErpNum());
        pstmt.setString(10+4,pData.getSiteErpNum());
        pstmt.setString(11+4,pData.getRequestPoNum());
        pstmt.setInt(12+4,pData.getUserId());
        pstmt.setString(13+4,pData.getUserFirstName());
        pstmt.setString(14+4,pData.getUserLastName());
        pstmt.setString(15+4,pData.getOrderSiteName());
        pstmt.setString(16+4,pData.getOrderContactName());
        pstmt.setString(17+4,pData.getOrderContactPhoneNum());
        pstmt.setString(18+4,pData.getOrderContactEmail());
        pstmt.setString(19+4,pData.getOrderContactFaxNum());
        if (pData.getContractId() == 0) {
            pstmt.setNull(20+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(20+4,pData.getContractId());
        }

        pstmt.setString(21+4,pData.getContractShortDesc());
        pstmt.setString(22+4,pData.getOrderTypeCd());
        pstmt.setString(23+4,pData.getOrderSourceCd());
        pstmt.setString(24+4,pData.getOrderStatusCd());
        pstmt.setString(25+4,pData.getTaxNum());
        pstmt.setBigDecimal(26+4,pData.getOriginalAmount());
        pstmt.setBigDecimal(27+4,pData.getTotalPrice());
        pstmt.setBigDecimal(28+4,pData.getTotalFreightCost());
        pstmt.setBigDecimal(29+4,pData.getTotalMiscCost());
        pstmt.setBigDecimal(30+4,pData.getTotalTaxCost());
        pstmt.setBigDecimal(31+4,pData.getTotalCleanwiseCost());
        pstmt.setBigDecimal(32+4,pData.getGrossWeight());
        pstmt.setDate(33+4,DBAccess.toSQLDate(pData.getOriginalOrderDate()));
        pstmt.setTimestamp(34+4,DBAccess.toSQLTimestamp(pData.getOriginalOrderTime()));
        pstmt.setDate(35+4,DBAccess.toSQLDate(pData.getRevisedOrderDate()));
        pstmt.setTimestamp(36+4,DBAccess.toSQLTimestamp(pData.getRevisedOrderTime()));
        pstmt.setString(37+4,pData.getComments());
        pstmt.setInt(38+4,pData.getIncomingTradingProfileId());
        pstmt.setString(39+4,pData.getLocaleCd());
        pstmt.setString(40+4,pData.getCurrencyCd());
        pstmt.setInt(41+4,pData.getErpOrderNum());
        pstmt.setTimestamp(42+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(43+4,pData.getAddBy());
        pstmt.setTimestamp(44+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(45+4,pData.getModBy());
        pstmt.setInt(46+4,pData.getSiteId());
        pstmt.setInt(47+4,pData.getAccountId());
        pstmt.setInt(48+4,pData.getStoreId());
        pstmt.setString(49+4,pData.getErpSystemCd());
        if (pData.getPreOrderId() == 0) {
            pstmt.setNull(50+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(50+4,pData.getPreOrderId());
        }

        pstmt.setInt(51+4,pData.getRefOrderId());
        pstmt.setBigDecimal(52+4,pData.getTotalRushCharge());
        pstmt.setString(53+4,pData.getOrderBudgetTypeCd());
        pstmt.setDate(54+4,DBAccess.toSQLDate(pData.getErpOrderDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderData insert(Connection pCon, OrderData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderData pData, boolean pLogFl)
        throws SQLException {
        OrderData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderId();
          try {
          oldData = OrderDataAccess.select(pCon,id);
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
     * Deletes a OrderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER d WHERE ORDER_ID = " + pOrderId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderId);
        return n;
     }

    /**
     * Deletes OrderData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER d ");
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

