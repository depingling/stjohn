package com.cleanwise.service.api.util;

/**
 * Title:        DuplicateNameException
 * Description:  Application Failure Exception
 * Purpose:      This exception is thrown when an attempt is made to
 *               add an entity with a non-unique name
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       T Besser, CleanWise, Inc.
 *
 */

/**
 * <code>DuplicateNameException</code> is thrown the entity being
 * added has a name which already exists.
 */
public class DuplicateNameException extends java.lang.Exception {
    /**
     * Creates a new <code>DuplicateNameException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public DuplicateNameException(String msg) {
	super(msg);
    }
}
