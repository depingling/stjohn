package com.cleanwise.service.api.util;

/**
 * This exception is thrown for all problems that occur in the EJB
 * Container code of the sample application. This class defines a few
 * typical error types to help the developer find the error
 * cause.
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

import java.rmi.RemoteException;
import javax.naming.NamingException; 
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.sql.SQLException;

public class GeneralFailureException extends EJBException
    implements java.io.Serializable { 

    private String cause;
    public static final String COMM_FAILURE = "RMI/IIOP problem";
    public static final String EJB_CREATION_FAILURE = "EJB creation problem";
    public static final String JNDI_PROBLEMS = "JNDI problem";
    public static final String DB_PROBLEMS = "Database problem";

    public GeneralFailureException(String cause) {
	super(cause);
	this.cause = cause;
    }

    public GeneralFailureException(Exception e) {
	super(e);
	if (e instanceof NamingException) {
	    this.cause = JNDI_PROBLEMS;
	} else if (e instanceof SQLException) {
	    this.cause = DB_PROBLEMS;
	} else if (e instanceof CreateException) {
	    this.cause = EJB_CREATION_FAILURE;
	} else if (e instanceof RemoteException) {
	    this.cause = COMM_FAILURE;
	} else {
	  this.cause = e.toString();
	}
    }

}

