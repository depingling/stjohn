package com.cleanwise.service.api.session;

/**
 * Title:        OrderAnalysisBean
 * Description:  Bean implementation for Order Analysis Stateless Session Bean
 * Purpose:      Provides access to the methods for order analysis requests.
 *               *** THIS IS INTENDED TO ONLY BE A TEMPORARY HACK ***
 *               ***                THROWAWAY CODE!!!!            ***
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import javax.naming.NamingException;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBAccess;
import java.sql.Connection;
import java.sql.SQLException;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import org.apache.log4j.Category;

public class OrderAnalysisBean extends ShoppingServicesAPI
{
    private static Category log = Category.getInstance(OrderAnalysisBean.class.getName());

    /**
     *
     */
    public OrderAnalysisBean() {}
    
    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * <code>getOrdersByState</code>
     *
     * @param account a <code>int</code> Account ID, if 0 then all accounts
     * @param begin a <code>Date</code> used for the start date
     * @param end a <code>Date</code> used for the end date
     * @param minAmt a <code>java.math.BigDecimal</code> minimum order amount
     * @param maxAmt a <code>java.math.BigDecimal</code> maximum order amount
     * @param inclusive a <code>boolean</code> if true, include orders in range
     * @return an <code>OrderAnalysisViewVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderAnalysisViewVector 
	getOrdersByState(int accountId,
			 Date begin,
			 Date end,
			 java.math.BigDecimal minAmt,
			 java.math.BigDecimal maxAmt,
			 boolean inclusive)
	throws RemoteException
    {
	OrderAnalysisViewVector oav = new OrderAnalysisViewVector();
	Connection conn = null;

    	try {
	    conn = getConnection();

	    StringBuffer sqlBuf = 
	    new StringBuffer("SELECT COUNT(*),SUM(");
       	    sqlBuf.append(OrderDataAccess.TOTAL_PRICE);
       	    sqlBuf.append("),");
       	    sqlBuf.append(OrderAddressDataAccess.STATE_PROVINCE_CD);
       	    sqlBuf.append(" FROM ");
       	    sqlBuf.append(OrderDataAccess.CLW_ORDER);
       	    sqlBuf.append(" o,");
       	    sqlBuf.append(OrderAddressDataAccess.CLW_ORDER_ADDRESS);
       	    sqlBuf.append(" a where o.");
       	    sqlBuf.append(OrderDataAccess.ORDER_ID);
       	    sqlBuf.append(" = a.");
       	    sqlBuf.append(OrderAddressDataAccess.ORDER_ID);
       	    sqlBuf.append(" AND a.");
       	    sqlBuf.append(OrderAddressDataAccess.ADDRESS_TYPE_CD);
       	    sqlBuf.append(" = '");
       	    sqlBuf.append(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
       	    sqlBuf.append("' GROUP BY a.");
       	    sqlBuf.append(OrderAddressDataAccess.STATE_PROVINCE_CD);
	    String sql = sqlBuf.toString();

	    if (log.isDebugEnabled()) {
		log.debug("SQL: " + sql);
	    }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
		OrderAnalysisView oa = OrderAnalysisView.createValue();

		oa.setTotalOrders(rs.getInt(1));
		oa.setTotalOrderAmt(rs.getBigDecimal(2));
		oa.setState(rs.getString(3));
		oa.setBeginDate(begin);
		oa.setEndDate(end);
		oav.add(oa);
	    }
	    rs.close();
	    stmt.close();

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new 
		RemoteException("getOrdersByState: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return oav;
    }

    /**
     * <code>getOrdersByType</code>
     *
     * @param account a <code>int</code> Account ID, if 0 then all accounts
     * @param begin a <code>Date</code> used for the start date
     * @param end a <code>Date</code> used for the end date
     * @return an <code>OrderAnalysisViewVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderAnalysisViewVector 
	getOrdersByType(int accountId,
			 Date begin,
			 Date end)
	throws RemoteException
    {
	OrderAnalysisViewVector oav = new OrderAnalysisViewVector();
	Connection conn = null;
    	try {
	    conn = getConnection();
            StringBuffer sqlBuf = new StringBuffer("select " + OrderDataAccess.ORDER_SOURCE_CD + 
                                                   " as \"Order Type\", Count(" + OrderDataAccess.ORDER_ID + 
                                                   ") as \"Order Count\" from " + OrderDataAccess.CLW_ORDER + 
                                                   ", " + BusEntityDataAccess.CLW_BUS_ENTITY + " ");
                                                 
	    DBCriteria crit = new DBCriteria();
            crit.addOrderBy(OrderDataAccess.ORDER_SOURCE_CD);
            crit.addGreaterOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE,
				   begin);
            crit.addLessOrEqual(OrderDataAccess.ORIGINAL_ORDER_DATE,
				   end);
            if (accountId > 0) {
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,
                                   accountId);  
            }
            sqlBuf.append(" where ");  
            sqlBuf.append(crit.getWhereClause());        
            sqlBuf.append(" AND " + BusEntityDataAccess.BUS_ENTITY_TYPE_CD+ " = 'ACCOUNT' and " + 
                            OrderDataAccess.ACCOUNT_ERP_NUM +" = " + BusEntityDataAccess.ERP_NUM + 
                            " group by " + OrderDataAccess.ORDER_SOURCE_CD + " ");
            sqlBuf.append(crit.getOrderByClause());
	    String sql = sqlBuf.toString();


	    if (log.isDebugEnabled()) {
		log.debug("SQL: " + sql);
	    }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
		OrderAnalysisView oa = OrderAnalysisView.createValue();
                oa.setAccountId(accountId);
		oa.setOrderType(rs.getString(1));
                oa.setCountOrderType(rs.getInt(2));
		oa.setBeginDate(begin);
		oa.setEndDate(end);
		oav.add(oa);
	    }
	    rs.close();
	    stmt.close();

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new 
		RemoteException("getOrdersByType: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return oav;
    }
    
    
    /**
     * <code>getOrderAnalysis</code>
     *
     * @param account a <code>String</code> describing the account(s) to be
     * reported - one of 'jcpjci', 'jci', 'jcp', 'usps', 'all'; ideally this 
     * would be the account id(s), but the current order status tables don't
     * have this information
     * @param begin a <code>Date</code> used for the start date
     * @param end a <code>Date</code> used for the end date
     * @param frequency a <code>String</code> used for the periods
     * (one of DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY)
     * @param minAmt a <code>java.math.BigDecimal</code> minimum order amount
     * @param maxAmt a <code>java.math.BigDecimal</code> maximum order amount
     * @param inclusive a <code>boolean</code> if true, include orders in range
     * @return an <code>OrderAnalysisViewVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderAnalysisViewVector 
	getOrderAnalysis(String account,
			 Date begin,
			 Date end,
			 String frequency,
			 java.math.BigDecimal minAmt,
			 java.math.BigDecimal maxAmt,
			 boolean inclusive)
	throws RemoteException
    {
	OrderAnalysisViewVector oav = new OrderAnalysisViewVector();

	// XXX - This was written to run against the old integration
	//       data and generate some reports needed in the shortterm

        /*
	Connection conn = null;

    	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

	    ArrayList accts = new ArrayList();
	    if (account.equals("jcpjci")) {
		accts.add(new String("10051"));
		accts.add(new String("10053"));
	    } else if (account.equals("jci")) {
		accts.add(new String("10051"));
	    } else if (account.equals("jcp")) {
		accts.add(new String("10053"));
	    } else if (account.equals("usps")) {
		accts.add(new String("10052"));
	    } else {
		// no accts will be taken as 'all' accounts
	    }

	    boolean noMaxAmt = true;
	    if (maxAmt.compareTo(new java.math.BigDecimal(0)) > 0) {
		noMaxAmt = false;
	    }

	    // get list of backorders
	    if (accts.size() > 0) {
		crit.addOneOf(OrderStatusDataAccess.CUSTOMER_ACCT, accts);
	    }
	    crit.addGreaterOrEqual(OrderStatusDataAccess.DATE_ORDERED,
            			   begin);
	    crit.addCondition("order_status_id in (select o.order_status_id from clw_order_status o,clw_erp_po po, clw_order_item_status oi, clw_order_item_detail d where o.order_status_id = po.order_status_id and oi.erp_po_id = po.erp_po_id and d.order_item_status_id = oi.order_item_status_id and d.action = 2)");
	    IdVector backorders = 
		OrderStatusDataAccess.selectIdOnly(conn, crit);

	    crit = new DBCriteria();
	    if (accts.size() > 0) {
		crit.addOneOf(OrderStatusDataAccess.CUSTOMER_ACCT, accts);
	    }
	    crit.addGreaterOrEqual(OrderStatusDataAccess.DATE_ORDERED,
				   begin);
	    crit.addOrderBy(OrderStatusDataAccess.ERP_SHIPTO_INDEX);
	    crit.addOrderBy(OrderStatusDataAccess.CUSTOMER_ACCT);
	    crit.addOrderBy(OrderStatusDataAccess.DATE_ORDERED);
	    OrderStatusDataVector osv = 
		OrderStatusDataAccess.select(conn, crit);

	    ArrayList intervals = new ArrayList();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(begin);
	    if (frequency.equals("DAILY")) {
		cal.add(Calendar.DATE, 1);
	    } else if (frequency.equals("MONTHLY")) {
		cal.add(Calendar.MONTH, 1);
	    } else if (frequency.equals("QUARTERLY")) {
		cal.add(Calendar.MONTH, 3);
	    } else if (frequency.equals("YEARLY")) {
		cal.add(Calendar.MONTH, 12);
	    } else { // if (frequency.equals("WEEKLY"))
		cal.add(Calendar.DATE, 7);
	    }
	    Date date1 = cal.getTime();
	    intervals.add(begin);
	    while (!date1.after(end)) {
		intervals.add(date1);
		if (frequency.equals("DAILY")) {
		    cal.add(Calendar.DATE, 1);
		} else if (frequency.equals("MONTHLY")) {
		    cal.add(Calendar.MONTH, 1);
		} else if (frequency.equals("QUARTERLY")) {
		    cal.add(Calendar.MONTH, 3);
		} else if (frequency.equals("YEARLY")) {
		    cal.add(Calendar.MONTH, 12);
		} else { 
		    // default is "WEEKLY"
		    cal.add(Calendar.DATE, 7);
	        }
		date1 = cal.getTime();
	    }
	    cal.setTime(end);
	    cal.add(Calendar.DATE, 1);
	    intervals.add(cal.getTime());

	    boolean first = true;

	    OrderAnalysisViewVector orderAV = null;
	    OrderAnalysisView orderA = null;
	    IdVector orderStatusIds = null;
	    int totalShipTime = 0;
	    int totalShipTimeBackorder = 0;
	    int interval = 1;

	    Iterator iter = osv.iterator();
	    while (iter.hasNext()) {
		OrderStatusData orderS = (OrderStatusData)iter.next();
  		int accountId = Integer.parseInt(orderS.getCustomerAcct());
		int siteId = orderS.getErpShiptoIndex();

		if (first || 
		    accountId != orderA.getAccountId() ||
		    siteId != orderA.getSiteId()) {
		    if (!first) {
			for (int i=0; i<intervals.size()-1; i++) {
			    oav.add(orderAV.get(i));
			}
		    } else {
			first = false;
		    }
		    
		    orderAV = new OrderAnalysisViewVector();

		    for (int i=0; i<intervals.size()-1; i++) {
			orderA = OrderAnalysisView.createValue();
			if (accountId == 10051) {
			    orderA.setAccountName("JCI");
			} else if (accountId == 10053) {
			    orderA.setAccountName("JC Penney");
			} else if (accountId == 10052) {
			    orderA.setAccountName("USPS");
			} else {
			    // doesn't always have something useful, but better
			    // than nothing
			    orderA.setAccountName(orderS.getLocationName());
			}

			orderA.setAccountId(accountId);
			orderA.setSiteId(siteId);

			orderA.setTotalOrders(0);
			orderA.setTotalOnlineOrders(0);
			orderA.setTotalBackorderOrders(0);
			orderA.setTotalFreightChargeOrders(0);
			orderA.setTotalOrderAmt(new java.math.BigDecimal(0));
			orderA.setTotalOrderAmtCategory1(new java.math.BigDecimal(0));
			orderA.setTotalOrderAmtCategory2(new java.math.BigDecimal(0));
			orderA.setTotalOrderAmtCategory3(new java.math.BigDecimal(0));
			orderA.setTotalOrderAmtCategory4(new java.math.BigDecimal(0));
			orderA.setTotalOrderFreightAmt(new java.math.BigDecimal(0));
			orderA.setHighDaysOrderShipNoBackorder(0);
			orderA.setLowDaysOrderShipNoBackorder(0);
			orderA.setAvgDaysOrderShipNoBackorder(0);
			orderA.setHighDaysOrderShipBackorder(0);
			orderA.setLowDaysOrderShipBackorder(0);
			orderA.setAvgDaysOrderShipBackorder(0);
			orderA.setBeginDate((Date)intervals.get(i));
			cal.setTime((Date)intervals.get(i+1));
			cal.add(Calendar.DATE, -1);
			orderA.setEndDate(cal.getTime());
			orderA.setState(orderS.getState());
			orderA.setPostalCode(orderS.getZip());
			orderAV.add(orderA);
		    }
		    orderStatusIds = new IdVector();
		    totalShipTime = 0;
		    totalShipTimeBackorder = 0;
		    interval = 1;
		    orderA = (OrderAnalysisView)orderAV.get(0);
		}

		Date dateOrdered = orderS.getDateOrdered();
		if (!dateOrdered.before((Date)intervals.get(interval))) {
		    for (int i=interval+1; i<intervals.size(); i++) {
			if (dateOrdered.before((Date)intervals.get(i))) {
			    interval = i;
			    break;
			}
		    }
		    orderStatusIds = new IdVector();
		    totalShipTime = 0;
		    totalShipTimeBackorder = 0;
		    orderA = (OrderAnalysisView)orderAV.get(interval-1);
		}

		StringBuffer sb = new StringBuffer("erp_po_id in (select erp_po_id from clw_erp_po where order_status_id = ");
		sb.append(orderS.getOrderStatusId());
		sb.append(")");
		DBCriteria crit1 = new DBCriteria();
		crit1.addCondition(sb.toString());
		OrderItemStatusDataVector oisv =
		    OrderItemStatusDataAccess.select(conn, crit1);

		Iterator oisI = oisv.iterator();

		java.math.BigDecimal total = new java.math.BigDecimal(0);
		java.math.BigDecimal total1 = new java.math.BigDecimal(0);
		java.math.BigDecimal total2 = new java.math.BigDecimal(0);
		java.math.BigDecimal total3 = new java.math.BigDecimal(0);
		java.math.BigDecimal total4 = new java.math.BigDecimal(0);
		IdVector orderItemIds = new IdVector();
		Date dateShipped = dateOrdered;
		StringBuffer pname = new StringBuffer();
		while (oisI.hasNext()) {
		    OrderItemStatusData ois = 
			(OrderItemStatusData)oisI.next();
		    orderItemIds.add(new Integer(ois.getOrderItemStatusId()));
		    java.math.BigDecimal price = ois.getPrice();
		    if (price == null) {
			// what does it mean if price is null???
			price = new java.math.BigDecimal(0);
		    }

		    String cat = ois.getTopLevelCatName();
		    if (cat.equals("Chemical")) {
			total1 = total1.add(price);
		    } else if (cat.equals("Paper")) {
			total2 = total2.add(price);
		    } else if (cat.equals("Liners")) {
			total3 = total3.add(price);
		    } else {
			total4 = total4.add(price);
		    }
		    total = total.add(price);
		    pname.append(",");
		    pname.append(ois.getProductName());
		    pname.append("(");
		    pname.append(price);
		    pname.append("(");
		}

		// check if order should be excluded based on total
		if (inclusive) {
		    // order total is less than min or greater than max
		    if (total.compareTo(minAmt) < 0 || 
			(!noMaxAmt && total.compareTo(maxAmt) > 0)) {
//  				       + pname);
			continue;
		    }
		} else {
		    // order total is greater/equal to min and
		    // less/equal to max
		    if (total.compareTo(minAmt) >= 0 && 
			(noMaxAmt || total.compareTo(maxAmt) <= 0)) {
//  				       + pname);
			continue;
		    }
		}

		// Hack - exclude initial orders
//  		if ((total.compareTo(new java.math.BigDecimal("71.72")) == 0) 
//  		    ||
//  		    (total.compareTo(new java.math.BigDecimal("143.44")) == 0) 
//  		    ||
//  		    (total.compareTo(new java.math.BigDecimal("199.40")) == 0))
//  		    {
//  //  				       + pname);
//  		    continue;
//  		}

		// get the latest ship date
		if (orderItemIds.size() > 0) {
		    crit = new DBCriteria();
		    crit.addOneOf(ShippingHistoryDataAccess.ORDER_ITEM_ID,
				  orderItemIds);
		    crit.addOrderBy(ShippingHistoryDataAccess.SHIP_DATE, false);
		    ShippingHistoryDataVector shipHistoryV =
			ShippingHistoryDataAccess.select(conn, crit);
		    if (shipHistoryV.size() > 0) {
			ShippingHistoryData shipHistory = 
			    (ShippingHistoryData)shipHistoryV.get(0);
			Date d = shipHistory.getShipDate();
			if (d.after(dateShipped)) {
			    dateShipped = d;
			}
		    }
		}

		orderA.setTotalOrders(orderA.getTotalOrders()+1);
		if (orderS.getOrderType().equals("web")) {
		    orderA.setTotalOnlineOrders(orderA.getTotalOnlineOrders()+1);
		}

		boolean backorder = 
		    backorders.contains(new Integer(orderS.getOrderStatusId()));
		cal.setTime(dateOrdered);
		int day1 = cal.get(Calendar.DAY_OF_YEAR) + 
		    (cal.get(Calendar.YEAR) - 2001) * 365;
		cal.setTime(dateShipped);
		int day2 = cal.get(Calendar.DAY_OF_YEAR) +
		    (cal.get(Calendar.YEAR) - 2001) * 365;
		int shipDays = day2 - day1;

		if (backorder) {
		    orderA.setTotalBackorderOrders(orderA.getTotalBackorderOrders()+1);
		    int high = orderA.getHighDaysOrderShipBackorder();
		    if (high < shipDays) {
			orderA.setHighDaysOrderShipBackorder(shipDays);
		    }
		    int low = orderA.getLowDaysOrderShipBackorder();
		    if (low == 0 || low > shipDays) {
			orderA.setLowDaysOrderShipBackorder(shipDays);
		    }
		    totalShipTimeBackorder += shipDays;
		    orderA.setAvgDaysOrderShipBackorder((double)totalShipTimeBackorder / orderA.getTotalBackorderOrders());
		} else {
		    int high = orderA.getHighDaysOrderShipNoBackorder();
		    if (high < shipDays) {
			orderA.setHighDaysOrderShipNoBackorder(shipDays);
		    }
		    int low = orderA.getLowDaysOrderShipNoBackorder();
		    if (low == 0 || low > shipDays) {
			orderA.setLowDaysOrderShipNoBackorder(shipDays);
		    }
		    totalShipTime += shipDays;
		    orderA.setAvgDaysOrderShipNoBackorder((double)totalShipTime / (orderA.getTotalOrders() - orderA.getTotalBackorderOrders()));
		}
		
		total1 = orderA.getTotalOrderAmtCategory1().add(total1);
		orderA.setTotalOrderAmtCategory1(total1);
		total2 = orderA.getTotalOrderAmtCategory2().add(total2);
		orderA.setTotalOrderAmtCategory2(total2);
		total3 = orderA.getTotalOrderAmtCategory3().add(total3);
		orderA.setTotalOrderAmtCategory3(total3);
		total4 = orderA.getTotalOrderAmtCategory4().add(total4);
		orderA.setTotalOrderAmtCategory4(total4);
		total = orderA.getTotalOrderAmt().add(total);
		orderA.setTotalOrderAmt(total);

		java.math.BigDecimal freight = orderS.getFreight();
		if (freight == null) {
		    freight = new java.math.BigDecimal(0);
		}
		if (freight.compareTo(new java.math.BigDecimal(0)) == 1) {
		    orderA.setTotalFreightChargeOrders(orderA.getTotalFreightChargeOrders()+1);
		}
		freight = orderA.getTotalOrderFreightAmt().add(freight);
		orderA.setTotalOrderFreightAmt(freight);
	    }
	    if (!first) {
		for (int i=0; i<intervals.size()-1; i++) {
		    oav.add(orderAV.get(i));
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new 
		RemoteException("getOrderAnalysis: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

         */
	return oav;
    }
	
    /**
     * <code>getOrderAnalysisSummary</code>
     *
     * @param account a <code>String</code> describing the account(s) to be
     * reported - one of 'jcpjci', 'jci', 'usps', 'all'; ideally this would
     * be the account id(s), but the current order status tables don't
     * have this information
     * @param begin a <code>Date</code> used for the start date
     * @param end a <code>Date</code> used for the end date
     * @param minAmt a <code>java.math.BigDecimal</code> minimum order amount
     * @param maxAmt a <code>java.math.BigDecimal</code> maximum order amount
     * @param inclusive a <code>boolean</code> if true, include orders in range
     * @return an <code>OrderAnalysisView</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderAnalysisView
	getOrderAnalysisSummary(String account,
				Date begin,
				Date end,
				java.math.BigDecimal minAmt,
				java.math.BigDecimal maxAmt,
				boolean inclusive)
	throws RemoteException {

	OrderAnalysisView orderA = OrderAnalysisView.createValue();

	Connection conn = null;

	// XXX - This was written to run against the old integration
	//       data and generate some reports needed in the shortterm

        /*
    	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

	    ArrayList accts = new ArrayList();
	    if (account.equals("jcpjci")) {
		accts.add(new String("10051"));
		accts.add(new String("10053"));
	    } else if (account.equals("jci")) {
		accts.add(new String("10051"));
	    } else if (account.equals("jcp")) {
		accts.add(new String("10053"));
	    } else if (account.equals("usps")) {
		accts.add(new String("10052"));
	    } else {
		// no accts will be taken as 'all' accounts
	    }

	    boolean noMaxAmt = true;
	    if (maxAmt.compareTo(new java.math.BigDecimal(0)) > 0) {
		noMaxAmt = false;
	    }

	    crit = new DBCriteria();
	    if (accts.size() > 0) {
		crit.addOneOf(OrderStatusDataAccess.CUSTOMER_ACCT, accts);
	    }
	    crit.addGreaterOrEqual(OrderStatusDataAccess.DATE_ORDERED,
				   begin);
	    crit.addOrderBy(OrderStatusDataAccess.ERP_SHIPTO_INDEX);
	    crit.addOrderBy(OrderStatusDataAccess.CUSTOMER_ACCT);
	    crit.addOrderBy(OrderStatusDataAccess.DATE_ORDERED);
	    OrderStatusDataVector osv = 
		OrderStatusDataAccess.select(conn, crit);

	    orderA.setTotalOrders(0);
	    orderA.setTotalOnlineOrders(0);
	    orderA.setTotalBackorderOrders(0);
	    orderA.setTotalFreightChargeOrders(0);
	    orderA.setTotalOrderAmt(new java.math.BigDecimal(0));
	    orderA.setTotalOrderAmtCategory1(new java.math.BigDecimal(0));
	    orderA.setTotalOrderAmtCategory2(new java.math.BigDecimal(0));
	    orderA.setTotalOrderAmtCategory3(new java.math.BigDecimal(0));
	    orderA.setTotalOrderAmtCategory4(new java.math.BigDecimal(0));
	    orderA.setTotalOrderFreightAmt(new java.math.BigDecimal(0));
	    orderA.setHighDaysOrderShipNoBackorder(0);
	    orderA.setLowDaysOrderShipNoBackorder(0);
	    orderA.setAvgDaysOrderShipNoBackorder(0);
	    orderA.setHighDaysOrderShipBackorder(0);
	    orderA.setLowDaysOrderShipBackorder(0);
	    orderA.setAvgDaysOrderShipBackorder(0);
	    orderA.setBeginDate(begin);
	    orderA.setEndDate(end);

	    java.math.BigDecimal total = new java.math.BigDecimal(0);
	    java.math.BigDecimal total1 = new java.math.BigDecimal(0);
	    java.math.BigDecimal total2 = new java.math.BigDecimal(0);
	    java.math.BigDecimal total3 = new java.math.BigDecimal(0);
	    java.math.BigDecimal total4 = new java.math.BigDecimal(0);

	    Iterator iter = osv.iterator();
	    while (iter.hasNext()) {
		OrderStatusData orderS = (OrderStatusData)iter.next();
  		int accountId = Integer.parseInt(orderS.getCustomerAcct());
		int siteId = orderS.getErpShiptoIndex();

		StringBuffer sb = new StringBuffer("erp_po_id in (select erp_po_id from clw_erp_po where order_status_id = ");
		sb.append(orderS.getOrderStatusId());
		sb.append(")");
		DBCriteria crit1 = new DBCriteria();
		crit1.addCondition(sb.toString());
		OrderItemStatusDataVector oisv =
		    OrderItemStatusDataAccess.select(conn, crit1);

		Iterator oisI = oisv.iterator();

		StringBuffer pname = new StringBuffer();
		while (oisI.hasNext()) {
		    OrderItemStatusData ois = 
			(OrderItemStatusData)oisI.next();
		    java.math.BigDecimal price = ois.getPrice();
		    if (price == null) {
			// what does it mean if price is null???
			price = new java.math.BigDecimal(0);
		    }

		    String cat = ois.getTopLevelCatName();
		    if (cat.equals("Chemical")) {
			total1 = total1.add(price);
		    } else if (cat.equals("Paper")) {
			total2 = total2.add(price);
		    } else if (cat.equals("Liners")) {
			total3 = total3.add(price);
		    } else {
			total4 = total4.add(price);
		    }
		    total = total.add(price);
		}

		// check if order should be excluded based on total
		if (inclusive) {
		    // order total is less than min or greater than max
		    if (total.compareTo(minAmt) < 0 || 
			(!noMaxAmt && total.compareTo(maxAmt) > 0)) {
			continue;
		    }
		} else {
		    // order total is greater/equal to min and
		    // less/equal to max
		    if (total.compareTo(minAmt) >= 0 && 
			(noMaxAmt || total.compareTo(maxAmt) <= 0)) {
			continue;
		    }
		}

		orderA.setTotalOrders(orderA.getTotalOrders()+1);
		if (orderS.getOrderType().equals("web")) {
		    orderA.setTotalOnlineOrders(orderA.getTotalOnlineOrders()+1);
		}

		java.math.BigDecimal freight = orderS.getFreight();
		if (freight == null) {
		    freight = new java.math.BigDecimal(0);
		}
		if (freight.compareTo(new java.math.BigDecimal(0)) == 1) {
		    orderA.setTotalFreightChargeOrders(orderA.getTotalFreightChargeOrders()+1);
		}
		freight = orderA.getTotalOrderFreightAmt().add(freight);
		orderA.setTotalOrderFreightAmt(freight);
	    }

	    orderA.setTotalOrderAmtCategory1(total1);
	    orderA.setTotalOrderAmtCategory2(total2);
	    orderA.setTotalOrderAmtCategory3(total3);
	    orderA.setTotalOrderAmtCategory4(total4);
	    orderA.setTotalOrderAmt(total);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new 
		RemoteException("getOrderAnalysis: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
         */

	return orderA;
    }
	
    /**
     * <code>getOrderShipAnalysis</code>
     *
     * @param account a <code>String</code> describing the account(s) to be
     * reported - one of 'jcpjci', 'jci', 'jcp', 'usps', 'all'; ideally this 
     * would be the account id(s), but the current order status tables don't
     * have this information
     * @param begin a <code>Date</code> used for the start date
     * @param end a <code>Date</code> used for the end date
     * @return an <code>OrderShipAnalysisViewVector</code> value
     * @exception RemoteException if an error occurs
     */
    public OrderShipAnalysisViewVector 
	getOrderShipAnalysis(String account, Date begin, Date end)
	throws RemoteException {

	OrderShipAnalysisViewVector oav = new OrderShipAnalysisViewVector();

	// XXX - This was written to run against the old integration
	//       data and generate some reports needed in the shortterm

        /*
	Connection conn = null;

    	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();

	    ArrayList accts = new ArrayList();
	    if (account.equals("jcpjci")) {
		accts.add(new String("10051"));
		accts.add(new String("10053"));
	    } else if (account.equals("jci")) {
		accts.add(new String("10051"));
	    } else if (account.equals("jcp")) {
		accts.add(new String("10053"));
	    } else if (account.equals("usps")) {
		accts.add(new String("10052"));
	    } else {
		// no accts will be taken as 'all' accounts
	    }

	    // get list of backorders
	    if (accts.size() > 0) {
		crit.addOneOf(OrderStatusDataAccess.CUSTOMER_ACCT, accts);
	    }
	    crit.addGreaterOrEqual(OrderStatusDataAccess.DATE_ORDERED,
				   begin);
	    crit.addLessOrEqual(OrderStatusDataAccess.DATE_ORDERED,
				end);
	    crit.addCondition("order_status_id in (select o.order_status_id from clw_order_status o,clw_erp_po po, clw_order_item_status oi, clw_order_item_detail d where o.order_status_id = po.order_status_id and oi.erp_po_id = po.erp_po_id and d.order_item_status_id = oi.order_item_status_id and d.action = 2)");
	    IdVector backorders = 
		OrderStatusDataAccess.selectIdOnly(conn, crit);

	    crit = new DBCriteria();
	    if (accts.size() > 0) {
		crit.addOneOf(OrderStatusDataAccess.CUSTOMER_ACCT, accts);
	    }
	    crit.addGreaterOrEqual(OrderStatusDataAccess.DATE_ORDERED,
				   begin);
	    crit.addLessOrEqual(OrderStatusDataAccess.DATE_ORDERED,
				end);
	    crit.addOrderBy(OrderStatusDataAccess.DATE_ORDERED);
	    crit.addOrderBy(OrderStatusDataAccess.ERP_SHIPTO_INDEX);
	    crit.addOrderBy(OrderStatusDataAccess.CUSTOMER_ACCT);
	    OrderStatusDataVector osv = 
		OrderStatusDataAccess.select(conn, crit);

	    Iterator iter = osv.iterator();
	    while (iter.hasNext()) {
		OrderStatusData orderS = (OrderStatusData)iter.next();
		OrderShipAnalysisView orderA = 
		    OrderShipAnalysisView.createValue();

		int accountId = Integer.parseInt(orderS.getCustomerAcct());
		orderA.setAccountId(accountId);
		if (accountId == 10051) {
		    orderA.setAccountName("JCI");
		} else if (accountId == 10053) {
		    orderA.setAccountName("JC Penney");
		} else if (accountId == 10052) {
		    orderA.setAccountName("USPS");
		} else {
		    // doesn't always have something useful, but better
		    // than nothing
		    orderA.setAccountName(orderS.getLocationName());
		}

		orderA.setOrderStatusId(orderS.getOrderStatusId());
		orderA.setSiteId(orderS.getErpShiptoIndex());
		orderA.setState(orderS.getState());
		orderA.setPostalCode(orderS.getZip());
		orderA.setOrderDate(orderS.getDateOrdered());
		orderA.setBackorder(backorders.contains(new Integer(orderS.getOrderStatusId())));

		Date dateOrdered = orderS.getDateOrdered();

		DBCriteria erpCrit = new DBCriteria();
		erpCrit.addEqualTo(ErpPoDataAccess.ORDER_STATUS_ID, 
				   orderS.getOrderStatusId());
		ErpPoDataVector erpPoV = 
		    ErpPoDataAccess.select(conn, erpCrit);
		IdVector ids = new IdVector();
		Iterator erpPoI = erpPoV.iterator();
		while (erpPoI.hasNext()) {
		    ErpPoData erpPo = (ErpPoData)erpPoI.next();
		    ids.add(new Integer(erpPo.getErpPoId()));
		    String vendor = erpPo.getVendorId();
		    if (vendor != null) {
			orderA.setDistributorId(Integer.parseInt(vendor.trim()));
		    }
		}

		// throw out everything but Lagasse
		if (orderA.getDistributorId() != 1127) continue;
		orderA.setDistributorName("Lagasse");

		DBCriteria crit1 = new DBCriteria();
		crit1.addOneOf(OrderItemStatusDataAccess.ERP_PO_ID, ids);
		OrderItemStatusDataVector oisv =
		    OrderItemStatusDataAccess.select(conn, crit1);

		int nItems = 0;
		ids = new IdVector();

		Iterator oisI = oisv.iterator();
		while (oisI.hasNext()) {
		    OrderItemStatusData ois = 
			(OrderItemStatusData)oisI.next();
		    ids.add(new Integer(ois.getOrderItemStatusId()));
		    nItems += ois.getQuantityOrdered();
		}
		orderA.setNumItemsOrdered(nItems);

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOrdered);
		cal.add(Calendar.DATE, 3);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY) {
		    cal.add(Calendar.DATE, 2);
		} else if (dayOfWeek == Calendar.SUNDAY) {
		    cal.add(Calendar.DATE, 1);
		}
		Date twoBusDays = cal.getTime();

		if (ids.size() > 0) {
		    crit = new DBCriteria();
		    crit.addOneOf(ShippingHistoryDataAccess.ORDER_ITEM_ID,
				  ids);
		    crit.addOrderBy(ShippingHistoryDataAccess.SHIP_DATE);
		    ShippingHistoryDataVector shipHistoryV =
			ShippingHistoryDataAccess.select(conn, crit);
		    Iterator shipHistoryI = shipHistoryV.iterator();
		    Date shipDate = orderA.getShipDate();
		    int qtyShipped = 0;
		    int qtyShipped1 = 0;
		    int qtyShipped2 = 0;
		    while (shipHistoryI.hasNext()) {
			ShippingHistoryData shipHistory = 
			    (ShippingHistoryData)shipHistoryI.next();
			Date dt = shipHistory.getShipDate();
			int qty = shipHistory.getQuantity();
			if (shipDate == null) {
			    shipDate = dt;
			    qtyShipped1 = qty;
			} else {
			    if (dt != null) {
				if (shipDate.compareTo(dt) == 0) {
				    qtyShipped1 += qty;
				}
			    }
			}
			if (dt != null) {
			    if (dt.before(twoBusDays)) {
				qtyShipped2 += qty;
			    }
			}
			qtyShipped += qty;
		    }
		    orderA.setShipDate(shipDate);
		    // % of items shipped
		    orderA.setStat1((double)qtyShipped/nItems);
		    // % of items shipped on 'ship date'
		    orderA.setStat2((double)qtyShipped1/nItems);
		    // % of items shipped within 2 b-days of order date
		    orderA.setStat3((double)qtyShipped2/nItems);
		}

		oav.add(orderA);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new 
		RemoteException("getOrderAnalysis: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
         */
        
	return oav;
    }
	
}
