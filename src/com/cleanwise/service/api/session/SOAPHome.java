package com.cleanwise.service.api.session;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface SOAPHome extends javax.ejb.EJBHome {
	
	  public SOAP create() throws CreateException, RemoteException;

}
