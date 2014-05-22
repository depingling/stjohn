package com.cleanwise.service.api.util;

/**
 * Title:        InvalidLoginException
 * Description:  Application Login Failure Exception
 * Purpose:      This exception is thrown for login failures like no such
 *               user and bad password
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Durval Vieira, CleanWise, Inc.
 *
 */

/**
 * <code>InvalidLoginException</code> is thrown if a user
 * has failed to authenticate for 1 of 2 reasons, 1) the
 * user name is not in the database, or 2) the password does
 * not match.
 */
public class InvalidLoginException extends java.lang.Exception {
    /**
     * Creates a new <code>InvalidLoginException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public InvalidLoginException(String msg) {
	super(msg);
    }
}
