/**
 * Title: MessageService 
 * Description: This class provides functionality for business entity messages (interstitial, etc).
 */
package com.cleanwise.service.apps;

import java.rmi.RemoteException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;

public class MessageService {
	
	/**
	 * Method to retrieve the list of messages relevant for a user.  A message is relevant if:
	 *  - the message is configured to at least one of the accounts to which the specified 
	 *  	user belongs.
	 * 	- the date posted value of the message is on or before today's date
	 *  - the end date value of the message is on or after today's date
	 *  - the message has been published
	 *  - the message is active
	 *  - the message has a language value matching the language of the specified user.
	 *  - there is no other message with the same title that more closely matches the
	 *  	language and country of the specified user.
	 * @param userId - the id of the user for whom the messages are being retrieved.
	 * @return a <code>StoreMessageDataVector</code> containing the relevant messages.
	 */
	public static StoreMessageViewVector getMessagesForUser(Integer userId) throws Exception {
		return getMessagesForUser(userId, false);
	}
	
	/**
	 * Method to retrieve the list of messages relevant for a user.  A message is relevant if:
	 *  - the message is configured to at least one of the accounts to which the specified 
	 *  	user belongs.
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
	 * @param forcedReadOnly - a boolean to indicate if only "forced read" messages should be
	 * 		returned.
	 * @return a <code>StoreMessageDataVector</code> containing the relevant messages.
	 */
	public static StoreMessageViewVector getMessagesForUser(Integer userId, 			boolean forcedReadOnly) throws Exception {
        BusEntityDataVector userAccounts = APIAccess.getAPIAccess().getUserAPI().getBusEntityCollection(userId, 
        		RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
		return getMessagesForUser(userId, userAccounts, forcedReadOnly);
	}
	
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
	public static StoreMessageViewVector getMessagesForUser(Integer userId, 
			BusEntityDataVector busEntities, boolean forcedReadOnly) throws Exception {
		StoreMessageViewVector returnValue = APIAccess.getAPIAccess().getStoreMessageAPI().getMessagesViewForUser(userId, busEntities, forcedReadOnly);
		return returnValue;
	}
	
	/**
	 * Method to retrieve a message for a user.
	 * @param userId - the id of the user for which the message is being retrieved.
	 * @param messageId - the id of the message being retrieved.
	 * @param includeExpired - a boolean to indicate if the message should be retrieved even if it
	 * 	is expired
	 * @return a <code>StoreMessageView</code> containing the message or null if the message
	 * is invalid (not belonging to a business entity associated with the specified user, not 
	 * published, not active, or not having a language value matching the language of the specified user) 
	 * or expired if includeExpired is false.
	 */
	public static StoreMessageView getMessageViewForUser(Integer userId, Integer messageId,
			boolean includeExpired) throws Exception {
        BusEntityDataVector userAccounts = APIAccess.getAPIAccess().getUserAPI().getBusEntityCollection(userId, 
        		RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
		return getMessageViewForUser(userId, messageId, userAccounts, includeExpired);
	}
	
	/**
	 * Method to retrieve a message for a user.
	 * @param userId - the id of the user for which the message is being retrieved.
	 * @param messageId - the id of the message being retrieved.
	 * @param busEntities - a <code>BusEntityDataVector</code> containing the business entities from
	 * 		which the message must have originated.
	 * @param includeExpired - a boolean that indicates if the message should be returned even if it
	 * 	is expired.
	 * @return a <code>StoreMessageView</code> containing the message or null if the message
	 * is invalid (not belonging to one of the specified business entities, not published, not active, 
	 * or not having a language value matching the language of the specified user) 
	 * or expired if includeExpired is false.
	 */
	public static StoreMessageView getMessageViewForUser(Integer userId, Integer messageId, 
			BusEntityDataVector busEntities, boolean includeExpired) throws Exception {
		StoreMessageView returnValue = APIAccess.getAPIAccess().getStoreMessageAPI().getMessageViewForUser(userId, messageId, busEntities, includeExpired);
		return returnValue;
	}
	
	/**
	 * Method to mark a message as read by a user.
	 * @param userId - the id of the user.
	 * @param messageId - the id of the message.
	 */
	public static void markMessageAsReadByUser(Integer userId, Integer messageId) throws Exception {
		APIAccess.getAPIAccess().getStoreMessageAPI().markMessageAsReadByUser(userId, messageId);
	}

}
