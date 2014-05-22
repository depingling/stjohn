package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.util.Date;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;

public interface StoreMessage extends javax.ejb.EJBObject {
    StoreMessageData getStoreMessageData(int pStoreId, int pStoreMessageId)
    throws RemoteException, DataNotFoundException;

    StoreMessageData updateStoreMessage(int pStoreId,
            StoreMessageData pStoreMessageData, String pModBy)
            throws RemoteException;
    
    StoreMessageData updateStoreMessage(int pStoreId,
            StoreMessageData pStoreMessageData, String pModBy,
            boolean pClearViewHistory) throws RemoteException;

    StoreMessageDataVector getMessagesByCriteria(int pStoreId,
            SearchCriteria pCriteria, Date pPostedDateFrom, Date pPostedDateTo,
            String pCountry, String pLanguageCd) throws RemoteException;

    StoreMessageDataVector getMessagesByCriteria(int pStoreId,
            SearchCriteria pCriteria, Date pPostedDateFrom, Date pPostedDateTo,
            String pCountry, String pLanguageCd, boolean isShowInactive) throws RemoteException;

    IdVector getConfiguratedAccounts(int pMessageId) throws RemoteException;

    void configureAccounts(int pMessageId, IdVector pNewAccountIds,
            IdVector pRemoveAccountIds, String pModBy) throws RemoteException;

    void configureAllAccounts(int pMessageId, int pStoreId, String pModBy)
            throws RemoteException;

	/**
	 * Method to retrieve the list of messages relevant for a user.  A message is relevant if:
	 *  - the message is configured to at least one of the specified business entities
	 * 	- the date posted value of the message is on or before today's date
	 *  - the end date value of the message is on or after today's date
	 *  - the message has been published
	 *  - the message is active
	 *  - the message has a language value matching the language of the specified user.
	 *  - there is no other message with the same title that more closely matches the
	 *  	language and country of the specified user. 
	 *  - if forcedRead is true, then a message must have a forced read value of true and not
	 *  	have been read by the specified user the required number of times.
	 * @param userId - the id of the user for whom the messages are being retrieved.
	 * @param busEntities - a <code>BusEntityDataVector</code> containing the business entities from
	 * 		which the messages originated.
	 * @param forcedReadOnly - a boolean to indicate if only "forced read" messages should be
	 * 		returned.
	 * @return a <code>StoreMessageDataVector</code> containing the relevant messages.
     * @throws RemoteException
	 */
    public StoreMessageViewVector getMessagesViewForUser(Integer userId, 
    		BusEntityDataVector busEntities, boolean forcedReadOnly) throws RemoteException;
    
	/**
	 * Method to retrieve a specific message for a user.  The message is returned if:
	 *  - the message is configured to at least one of the specified business entities
	 *  - if includeExpired is false,
	 *  	- the date posted value of the message is on or before today's date
	 *  	- the end date value of the message is on or after today's date
	 *  - the message has been published
	 *  - the message has a language value matching the language of the specified user.
	 * @param userId - the id of the user for whom the message is being retrieved.
	 * @param messageId - the id of the message to retrieve.
	 * @param busEntities - a <code>BusEntityDataVector</code> containing the business entities from
	 * 		which the message originated.
	 * @param includeExpired - a boolean to indicate if an expired message should be
	 * 		returned.
	 * @return a <code>StoreMessageData</code> containing the data for the message, or null if the
	 * 	conditions above are not met.
     * @throws RemoteException
	 */
   public StoreMessageView getMessageViewForUser(Integer userId, Integer messageId,
    		BusEntityDataVector busEntities, boolean includeExpired) throws RemoteException;
    
    

	/**
	 * Method to mark a message as read by a user.
	 * @param userId - the id of the user.
	 * @param messageId - the id of the message.
	 * @throws RemoteException
	 */
	public void markMessageAsReadByUser(Integer userId, Integer messageId) throws RemoteException;
	
}
