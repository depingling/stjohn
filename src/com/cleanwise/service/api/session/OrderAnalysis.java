package com.cleanwise.service.api.session;

/**
 * Title:        OrderAnalysis
 * Description:  Remote Interface for Order Analysis Stateless Session Bean
 * Purpose:      Provides access to the methods for order analysis repquests.
 *               *** THIS CODE WAS INITIALLY WRITTEN ONLY AS A TEMPORARY  ***
 *               *** SOLUTION FOR SOME SHORT TERM REPORTING NEEDS.  OTHER ***
 *               *** THAN THE getOrdersByState METHOD, IT WAS WRITTEN TO  ***
 *               *** WORK AGAINST THE OLD INTEGRATION SCHEMA              ***
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import com.cleanwise.service.api.value.OrderAnalysisView;
import com.cleanwise.service.api.value.OrderAnalysisViewVector;
import com.cleanwise.service.api.value.OrderShipAnalysisView;
import com.cleanwise.service.api.value.OrderShipAnalysisViewVector;

public interface OrderAnalysis extends javax.ejb.EJBObject
{

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
	throws RemoteException;
    
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
	throws RemoteException;
	

    /**
     * <code>getOrderAnalysis</code>
     *
     * @param account a <code>String</code> describing the account(s) to be
     * reported - one of 'jcpjci', 'jci', 'usps', 'all'; ideally this would
     * be the account id(s), but the current order status tables don't
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
	getOrderAnalysis(String report,
			 Date begin,
			 Date end,
			 String frequency,
			 java.math.BigDecimal minAmt,
			 java.math.BigDecimal maxAmt,
			 boolean inclusive)
	throws RemoteException;
	
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
	getOrderAnalysisSummary(String report,
				Date begin,
				Date end,
				java.math.BigDecimal minAmt,
				java.math.BigDecimal maxAmt,
				boolean inclusive)
	throws RemoteException;
    
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
	throws RemoteException;
}

