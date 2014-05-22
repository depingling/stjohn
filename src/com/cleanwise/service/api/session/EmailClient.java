package com.cleanwise.service.api.session;

import java.rmi.RemoteException;

import com.cleanwise.service.api.eventsys.FileAttach;

/**
 *  Interface providing access to the JavaMail funtionality. Note that all
 *  messages sent out will have a from address as specified in the
 *  mail.properties configuration file.
 *
 *@author     dvieira
 *@created    October 18, 2001
 */
public interface EmailClient extends javax.ejb.EJBObject {

    /**
     *Gets the default email address for this install.
     */
    public String getDefaultEmailAddress() throws RemoteException;
    
    /**
     *Gets the account's default email address
     */
    public String getAcctDefaultEmailAddress(int pAcctId) throws RemoteException;
    
    /**
     *Gets the account's default email address, else store's default, else default for install
     */
    public String getDefaultFromAddress(int pAcctId)throws RemoteException;

    /**
     *  Send the email message
     *
     *@param  pToEmailAddress
     *@param  pSubject
     *@param  pMsg
     *@param  pMsgFormat    @see FMT_PLAIN_TEXT
     *@param  pEmailBusEntityId busEntityId (for failed email message)
     *@param  pEmailUserId userId  (for failed email message)
     *@throws  RemoteException  Required by EJB 1.0
     */

    public void send(String pToEmailAddress,
            String pFromEmailAddress,
            String pSubject,
            String pMsg,
            String pMsgFormat,
            int pEmailBusEntityId,
            int pEmailUserId
     )
             throws Exception, RemoteException;

    /**
     *  Send the email message
     *
     *@param  pToEmailAddress
     *@param  pFromEmailAddress
     *@param  pCcEmailAddress
     *@param  pSubject
     *@param  pMsg
     *@param  pMsgFormat
     *@param  pReferenceNum
     *@param  pEmailTrackingType
     *@param  pEmailBusEntityId busEntityId (for failed email message)
     *@param  pEmailUserId userId  (for failed email message)
     *@param  pUser
     *@throws  RemoteException  Required by EJB 1.0
     */

    public void send(String pToEmailAddress,
            String pFromEmailAddress,
            String pCcEmailAddress,
            String pSubject,
            String pMsg,
            String pMsgFormat,
            int pReferenceNum,
            String pEmailTrackingType,
            int pEmailBusEntityId,
            int pEmailUserId,
            String pUser)
             throws Exception, RemoteException;

    /**
     * Checks if a given email has already been sent to the supplied email address.
     * @param pSubject subject to check for, subject should be assumed to be unique to a particular event (i.e. have an order number in it)
     * @param pToEmailAddress The to address to check for
     * @return true if the email was sent.
     * @throws RemoteException
     */
    public boolean wasThisEmailSent(String pSubject, String pToEmailAddress)
             throws RemoteException;
    
    /**
     * Checks if a given email has already been sent to the supplied email address and do not use transactions.  That is to say if you want to send an email even
     * when the overall transaction is being rolled back use this method.  Unless this is SPECIFICALLY required 
     * you should use the @see wasThisEmailSent method.
     * @param pSubject subject to check for, subject should be assumed to be unique to a particular event (i.e. have an order number in it)
     * @param pToEmailAddress The to address to check for
     * @return true if the email was sent.
     * @throws RemoteException
     */
    public boolean wasThisEmailSentNoTx(String pSubject, String pToEmailAddress)
    throws RemoteException;
    
         /**
           *  Send the email message
           *
           *@param  pToEmailAddress
           *@param  pFromEmailAddress
           *@param  pCcEmailAddress
           *@param  pSubject
           *@param  pMsg
           *@param  pMsgFormat
           *@param  pReferenceNum
           *@param  pEmailTrackingType
           *@param  pEmailBusEntityId busEntityId (for failed email message)
           *@param  pEmailUserId userId  (for failed email message)
           *@param  pUser
           *@param  pAttachments
           *@throws  RemoteException  Required by EJB 1.0
     */
     public void send(String pToEmailAddress,
             String pFromEmailAddress,
             String pCcEmailAddress,
             String pSubject,
             String pMsg,
             String pMsgFormat,
             int pReferenceNum,
             String pEmailTrackingType,
             int pEmailBusEntityId,
             int pEmailUserId,
             String pUser,
             FileAttach[] pAttachments)
         throws Exception, RemoteException;

}

