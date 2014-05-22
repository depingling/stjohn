package com.cleanwise.service.api.util;

/**
 * Title:        BudgetRuleException
 * Description:  Application Login Failure Exception
 * Purpose:      This exception is thrown for login failures like no such
 *               user and bad password
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Durval Vieira, CleanWise, Inc.
 *
 */

/**
 * <code>BudgetRuleException</code> is thrown if an order
 * was being attempted which would violate the budget constraints
 * for a particular site.
 */
public class BudgetRuleException extends java.rmi.RemoteException {
    /**
     * Creates a new <code>BudgetRuleException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public BudgetRuleException(String msg) {
	super(msg);
    }
}
