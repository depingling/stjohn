package com.cleanwise.service.api.util;

/**
 * <code>BudgetRuleException</code> is thrown if an order
 * was being attempted which would violate the budget constraints
 * for a particular site.
 */
public class BudgetYTDRuleException extends java.rmi.RemoteException {
    /**
     * Creates a new <code>BudgetRuleException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public BudgetYTDRuleException(String msg) {
	super(msg);
    }
}
