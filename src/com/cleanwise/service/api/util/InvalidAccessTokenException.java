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
 * <code>InvalidAccessTokenException</code> is thrown if a user
 * has attempted to authenticate via an access token but the access token is
 * invalid for any reason (i.e. expired, etc.).
 */
public class InvalidAccessTokenException extends java.lang.Exception {
    /**
     * Creates a new <code>InvalidLoginException</code> instance.
     *
     * @param msg a <code>String</code> value with the exception message.
     */
    public InvalidAccessTokenException(String msg) {
	super(msg);
    }
}
