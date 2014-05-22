package com.cleanwise.service.api.util;


/**
 * <code>IgnoredException</code> is thrown if an exception occurred 
 * and been taken cared at time when exception occured
 */
public class IgnoredException extends java.lang.Exception {
    /**
     * Creates a new <code>BudgetRuleException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public IgnoredException(String msg) {
	super(msg);
    }
}
