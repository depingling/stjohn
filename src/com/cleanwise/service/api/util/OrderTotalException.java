package com.cleanwise.service.api.util;

/**
 * Title:        OrderTotalException
 * Description:  Application Login Failure Exception
 * Purpose:      This exception is thrown for login failures like no such
 *               user and bad password
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Durval Vieira, CleanWise, Inc.
 *
 */

/**
 * <code>OrderTotalException</code> is thrown if an order
 * was being attempted which would violate the budget constraints
 * for a particular site.
 */
public class OrderTotalException extends java.rmi.RemoteException {
    /**
     * Creates a new <code>OrderTotalException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public OrderTotalException(String msg) {
	super(msg);
    }
}
