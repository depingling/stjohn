package com.cleanwise.service.api.util;

/**
 * Title:        QueryRequestException
 * Description:  Query Request Exception
 * Purpose:      This exception is thrown when a query request asks
 *               a supporting method to do something it does not support
 *               e.g. search for a user filtering on manufacturer
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       T. Besser, CleanWise, Inc.
 *
 */

import javax.ejb.EJBException;

/**
 * <code>QueryRequestException</code>
 */
public class QueryRequestException extends EJBException {
    /**
     * Creates a new <code>QueryRequestException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public QueryRequestException(String msg) {
	super(msg);
    }
}
