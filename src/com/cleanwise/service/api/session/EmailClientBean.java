package com.cleanwise.service.api.session;


import java.rmi.RemoteException;
import java.sql.Connection;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.EventEmailDataAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.utils.Constants;

/**
 *  Implementation of the EmailClient interface.  @see
 com.cleanwise.service.api.session.EmailClient.
 *
 *@author     dvieira
 *@created    October 18, 2001
 */
public class EmailClientBean extends UtilityServicesAPI {
	private static final Logger  log = Logger.getLogger(EmailClientBean.class);
	
	private static final long serialVersionUID = 4819086710838347246L;
	private static boolean _performWasSentCheck = true;
	private static boolean _performWasSentCheckInitialized = false;
	
	private boolean isPerformSentEmailCheck(Connection conn) {
		if (!EmailClientBean._performWasSentCheckInitialized) {
			try {
				EmailClientBean._performWasSentCheckInitialized = true;
				PropertyUtil propUtil = new PropertyUtil(conn);
				String value = propUtil.fetchValue(0, 0, Constants.PERFORM_EMAIL_SENT_CHECK);
				if (Constants.FALSE.equalsIgnoreCase(value)) {
					EmailClientBean._performWasSentCheck = false;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (DataNotFoundException e) {
				//don't print the stack trace here because it is ok if there is a DataNotFoundException.
			} 		
		}
		return EmailClientBean._performWasSentCheck;
	}
	
    /**
    Default constructor (empty)
     */
    public EmailClientBean() {
    }


    /**
    Default method (empty)
     *@exception  CreateException  Description of Exception
     *@exception  RemoteException  Description of Exception
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }


    /**
     *Gets the default email address for this install.
     */
    public String getDefaultEmailAddress() throws RemoteException
    {
        Connection con = null;
        try
        {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
            crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
            crit.addIsNull(EmailDataAccess.BUS_ENTITY_ID);
            crit.addIsNull(EmailDataAccess.USER_ID);
            crit.addIsNull(EmailDataAccess.CONTACT_ID);
            EmailDataVector emails = EmailDataAccess.select(con,crit);
            String defEmailAddr = Constants.EMAIL_DEFAULT_FROM_ADDRESS;
            if(emails.size() >0)
            {
            	defEmailAddr = ((EmailData)emails.get(0)).getEmailAddress();
            }
            else
            {
                defEmailAddr = ApplicationsEmailTool.getLocalEmailAddress().getAddress();
            }
	    logDebug(" EmailClient.getDefaultEmailAddress, defEmailAddr=" + defEmailAddr);
	    return defEmailAddr;
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        finally
        {
            closeConnection(con);
        }
    }
    
    /**
     *Gets the account's default email address 
     */
    public String getAcctDefaultEmailAddress(int pAcctId) throws RemoteException
    {
        Connection con = null;
        try
        {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
            crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE);
            crit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, pAcctId);
            
            EmailDataVector emails = EmailDataAccess.select(con,crit);
            String defEmailAddr = Constants.EMAIL_DEFAULT_FROM_ADDRESS;
            if(emails.size() >0){
            	defEmailAddr = ((EmailData)emails.get(0)).getEmailAddress();
            }
            else{
            	defEmailAddr = getDefaultEmailAddress();
            }
            logDebug(" EmailClient.getAcctDefaultEmailAddress, defEmailAddr=" + defEmailAddr);
            return defEmailAddr;
            
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(con);
        }
    }
    
    /**
     *Gets the account's default email address, else store's default, else default for install
     */
    public String getDefaultFromAddress(int pAcctId)throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            APIAccess factory = null;
            factory = APIAccess.getAPIAccess();
            Store storeEjb = factory.getStoreAPI();
            
            //try for account default email
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, pAcctId);
            crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
            crit.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
            crit.addIsNotNull(EmailDataAccess.EMAIL_ADDRESS);
            
            EmailDataVector emails = EmailDataAccess.select(con,crit);
            String defFromEmailAddr = Constants.EMAIL_DEFAULT_FROM_ADDRESS;
            if(emails.size()>0){
            	defFromEmailAddr = ((EmailData)emails.get(0)).getEmailAddress();

            }else{
            	//else try for store default email
            	int storeId = storeEjb.getStoreIdByAccount(pAcctId);
            	
            	crit = new DBCriteria();
            	crit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, storeId);
            	crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
            	crit.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
            	
            	emails = EmailDataAccess.select(con,crit);
            	if(emails.size()>0){
            		defFromEmailAddr = ((EmailData)emails.get(0)).getEmailAddress();

            	}else{
                    //else get default email set in database/server configs
            		defFromEmailAddr = getDefaultEmailAddress();
            	}
            	
            }
            logInfo(" EmailClient.getDefaultFromAddress, defFromEmailAddr=" + defFromEmailAddr);
        	return defFromEmailAddr;
            
        }catch (Exception e){
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(con);
        }
    }
    
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
             throws Exception, RemoteException{

        send(pToEmailAddress, pFromEmailAddress, pCcEmailAddress, pSubject,pMsg,pMsgFormat,pReferenceNum,pEmailTrackingType,
             pEmailBusEntityId, pEmailUserId, pUser, null);
    }

    /**
     *  Send the email message
     *
     *@param  pToEmailAddress
     *@param  pSubject
     *@param  pMsg
     *@param  pMsgFormat
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
    throws Exception, RemoteException {
        send(pToEmailAddress, pFromEmailAddress, null,pSubject,pMsg,pMsgFormat,0,null,
             pEmailBusEntityId, pEmailUserId, "UNKNOWN");
    }

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
            String pUser,
            FileAttach[] pAttachments)
             throws Exception, RemoteException{

           APIAccess factory = null;
           Event eventEjb = null;

           try {
               factory = APIAccess.getAPIAccess();
               eventEjb = factory.getEventAPI();
           } catch (Exception exc) {
               logError("No API access");
               throw new RemoteException("No API access: "+exc.getMessage());
           }

            try {
//              EventData eventData = new EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//              eventData = eventEjb.addEventToDB(eventData);
               EventEmailDataView eventEmailData = new EventEmailDataView();
               eventEmailData.setEventEmailId(0);
               String toAddress = (pToEmailAddress!= null && pToEmailAddress.length()>255)?
                                  pToEmailAddress.substring(0,255):pToEmailAddress;
               if ( toAddress == null || toAddress.length() == 0 ) {
                  toAddress = "none";
               }
               eventEmailData.setToAddress(toAddress);
               String ccAddress = (pCcEmailAddress!= null && pCcEmailAddress.length()>255)?
                                  pCcEmailAddress.substring(0,255):pCcEmailAddress;
               eventEmailData.setCcAddress(ccAddress);
               eventEmailData.setFromAddress(pFromEmailAddress);
               String subject = (pSubject!=null && pSubject.length()>255)?
                                pSubject.substring(0,255):pSubject;
               eventEmailData.setSubject(subject);
               //if(!(RefCodeNames.EMAIL_TRACKING_CD.USER_PASSWORD.equals(pEmailTrackingType)))
               //{
                   eventEmailData.setText(pMsg);
               //}
//               eventEmailData.setEventId(eventData.getEventId());
               eventEmailData.setEmailStatusCd(Event.STATUS_READY);
               String owner = "EmailClient";
               if (pEmailTrackingType != null && pEmailTrackingType.length() != 0) {
                 owner = pEmailTrackingType;
               }
               eventEmailData.setModBy(owner);
               eventEmailData.setAddBy(owner);

               if (pAttachments != null && pAttachments.length > 0) {
                 eventEmailData.setAttachments(pAttachments);
               }

//               eventEjb.addEventEmail(eventEmailData);
               EventData eventData = EventData.createValue();
               eventData.setStatus(Event.STATUS_READY);
               eventData.setType(Event.TYPE_EMAIL);
               eventData.setAttempt(1);
               EventEmailView eev = new EventEmailView(eventData, eventEmailData); 
               eventEjb.addEventEmail(eev, "EmailClient");
             } catch (Exception e) {
                 e.printStackTrace();
                 throw new RemoteException("Can't save email. Email address: "+pToEmailAddress, e);
            }
    }

    /**
     * Checks if a given email has already been sent to the supplied email address.
     * @param pSubject subject to check for, subject should be assumed to be unique to a particular event (i.e. have an order number in it)
     * @param pToEmailAddress The to address to check for
     * @return true if the email was sent.
     * @throws RemoteException
     */
    public boolean wasThisEmailSent(String pSubject, String pToEmailAddress)
    throws RemoteException {
    	Connection conn = null;
    	try{
	    	conn = getConnection();
	    	return wasThisEmailSent(pSubject,pToEmailAddress, conn);
        }catch (javax.naming.NamingException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
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
    throws RemoteException{
    	Connection conn = null;
    	try{
	    	conn = getConnectionNoTx();
	    	return wasThisEmailSent(pSubject,pToEmailAddress, conn);
        }catch (javax.naming.NamingException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally{
            closeConnection(conn);
        }
    }
    
    private boolean wasThisEmailSent(String pSubject, String pToEmailAddress, Connection conn)
    throws RemoteException, NamingException, java.sql.SQLException {	
    	//if the application is not to perform this check, return false
    	if (!isPerformSentEmailCheck(conn)) {
    		log.info("Not performing check as system is configured not to check");
    		return false;
    	}
    	
        

        String toAddress = (pToEmailAddress!= null && pToEmailAddress.length()>255)?
                           pToEmailAddress.substring(0,255):pToEmailAddress;
        if ( toAddress == null || toAddress.length() == 0 ) {
           toAddress = "none";
        }
        String subject = (pSubject!=null && pSubject.length()>255)?
                         pSubject.substring(0,255):pSubject;

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(EventEmailDataAccess.TO_ADDRESS, toAddress);
        dbc.addEqualTo(EventEmailDataAccess.SUBJECT, subject);
        String sql = EventEmailDataAccess.getSqlSelectIdOnly("*",dbc);
            IdVector ids = EventEmailDataAccess.selectIdOnly(conn, dbc);
        log.info("sql: "+sql+" Result: "+ids.size());
        if ( ids.size() == 0 ){
            return false;
        }
        return true;

        

    }



}

