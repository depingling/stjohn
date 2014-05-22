package com.cleanwise.service.api.util;

/**
 * Title:        OrderWorkflowException
 * Description:  Application Login Failure Exception
 * Purpose:      This exception is thrown for login failures like no such
 *               user and bad password
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Durval Vieira, CleanWise, Inc.
 *
 */

/**
 * <code>OrderWorkflowException</code> is thrown if an order
 * was being attempted which would violate the budget constraints
 * for a particular site.
 */
public class OrderWorkflowException extends java.rmi.RemoteException {
    /**
     * Creates a new <code>OrderWorkflowException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public OrderWorkflowException(String msg) {
	super(msg);
    }
}
