package com.cleanwise.service.api.session;


import javax.ejb.*;
import java.rmi.*;

/**
 *  Home interface for the EmaiClient interface.
 @see com.cleanwise.service.api.session.EmailClient
 *
 *@author     dvieira
 *@created    October 18, 2001
 */
public interface EmailClientHome extends javax.ejb.EJBHome {
    /**
     *  Simple create method used to get a reference to the EmailClient Bean
     *  remote reference.
     *
     *@return                   The remote reference to be used to execute the
     *      methods associated with the EmailClientBean.
     *@throws  CreateException
     *@throws  RemoteException
     *@see                      EmailClient
     *@see                      CreateException
     *@see                      RemoteException
     */
    public EmailClient create() throws CreateException,
            RemoteException;

}

