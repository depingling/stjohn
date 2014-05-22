package com.cleanwise.service.api.util;

/**
 * Title:        MultipleDataException
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

/**
 * <code>MultipleDataException</code> is thrown if more than one result found 
 * and only one suppose to be
 */
public class MultipleDataException extends java.lang.Exception {
    /**
     * Creates a new <code>MultipleDataException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public MultipleDataException(String msg) {
	super(msg);
    }
}
