package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.CreateException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.StoreMessageAssocDataAccess;
import com.cleanwise.service.api.dao.StoreMessageDAO;
import com.cleanwise.service.api.dao.StoreMessageDataAccess;
import com.cleanwise.service.api.dao.StoreMessageDetailDataAccess;
import com.cleanwise.service.api.dao.UserMessageAssocDataAccess;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.StoreMessageAssocData;
import com.cleanwise.service.api.value.StoreMessageAssocDataVector;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
import com.cleanwise.service.api.value.StoreMessageDetailData;
import com.cleanwise.service.api.value.StoreMessageDetailDataVector;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserMessageAssocData;
import com.cleanwise.service.api.value.UserMessageAssocDataVector;

public class StoreMessageBean extends BusEntityServicesAPI {

	private static final long serialVersionUID = -7321469616091473881L;

	public void ejbCreate() throws CreateException, RemoteException {
    }

    public StoreMessageData getStoreMessageData(int pStoreId,
            int pStoreMessageId) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            StoreMessageData result = StoreMessageDataAccess.select(conn,
                    pStoreMessageId);
            if (result == null) {
                throw new DataNotFoundException("pStoreMessageId:"
                        + pStoreMessageId);
            }
            return result;
        } catch (DataNotFoundException dnfe) {
            dnfe.printStackTrace();
            throw dnfe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getStoreMessageData:" + e);
        } finally {
            closeConnection(conn);
        }
    }

    public StoreMessageData updateStoreMessage(int pStoreId,
            StoreMessageData pStoreMessageData, String pModBy)
            throws RemoteException {
        return updateStoreMessage(pStoreId, pStoreMessageData, pModBy, false);
    }

    public StoreMessageData updateStoreMessage(int pStoreId,
            StoreMessageData pStoreMessageData, String pModBy,
            boolean pClearViewHistory)
            throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            pStoreMessageData.setModBy(pModBy);
            if (pStoreMessageData.getStoreMessageId() > 0) {
                StoreMessageDataAccess.update(conn, pStoreMessageData);
                if (pClearViewHistory) {
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(
                            UserMessageAssocDataAccess.STORE_MESSAGE_ID,
                            pStoreMessageData.getStoreMessageId());
                    UserMessageAssocDataAccess.remove(conn, crit);
                }
            } else {
                pStoreMessageData.setAddBy(pModBy);
                pStoreMessageData = StoreMessageDataAccess.insert(conn,
                        pStoreMessageData);
            }
            IdVector storeIds = new IdVector();
            storeIds.add(pStoreId);
            updateMessageAssoc(conn, pStoreMessageData.getStoreMessageId(),
                    RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_STORE,
                    storeIds, null, pModBy);
            return pStoreMessageData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("updateStoreMessage:" + e);
        } finally {
            closeConnection(conn);
        }
    }

    private void updateMessageAssoc(Connection pCon, int pMessageId,
            String pMessageAssocCd, IdVector pAddBusEntityIds,
            IdVector pRemoveBusEntityIds, String pModBy) throws Exception {
        if (pRemoveBusEntityIds != null && pRemoveBusEntityIds.size() > 0) {
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(StoreMessageAssocDataAccess.STORE_MESSAGE_ID,
                    pMessageId);
            dbCrit.addEqualTo(
                    StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD,
                    pMessageAssocCd);
            dbCrit.addOneOf(StoreMessageAssocDataAccess.BUS_ENTITY_ID,
                    new ArrayList(pRemoveBusEntityIds));
            StoreMessageAssocDataAccess.remove(pCon, dbCrit);
        }
        if (pAddBusEntityIds != null && pAddBusEntityIds.size() > 0) {
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addOneOf(StoreMessageAssocDataAccess.BUS_ENTITY_ID,
                    pAddBusEntityIds);
            dbCrit.addEqualTo(StoreMessageAssocDataAccess.STORE_MESSAGE_ID,
                    pMessageId);
            dbCrit.addEqualTo(
                    StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD,
                    pMessageAssocCd);
            StoreMessageAssocDataVector dv = StoreMessageAssocDataAccess
                    .select(pCon, dbCrit);
            Map<Integer, StoreMessageAssocData> exist = new TreeMap<Integer, StoreMessageAssocData>();
            for (Object o : dv) {
                StoreMessageAssocData d = (StoreMessageAssocData) o;
                exist.put(d.getBusEntityId(), d);
            }
            for (Object busEntityId : pAddBusEntityIds) {
                StoreMessageAssocData d = exist.get(busEntityId);
                if (d == null) {
                    d = StoreMessageAssocData.createValue();
                    d.setStoreMessageAssocCd(pMessageAssocCd);
                    d.setStoreMessageId(pMessageId);
                    d.setBusEntityId((Integer) busEntityId);
                    d.setAddBy(pModBy);
                    d.setModBy(pModBy);
                    StoreMessageAssocDataAccess.insert(pCon, d);
                } else {
                    d.setModBy(pModBy);
                    StoreMessageAssocDataAccess.update(pCon, d);
                }
            }
        }
    }

    public StoreMessageDataVector getMessagesByCriteria(int pStoreId,
            SearchCriteria pCriteria, Date pPostedDateFrom, Date pPostedDateTo,
            String pCountry, String pLanguageCd) throws RemoteException {
            return getMessagesByCriteria(pStoreId, pCriteria, pPostedDateFrom,
                pPostedDateTo, pCountry, pLanguageCd, false);
    }

    public StoreMessageDataVector getMessagesByCriteria(int pStoreId,
            SearchCriteria pCriteria, Date pPostedDateFrom, Date pPostedDateTo,
            String pCountry, String pLanguageCd, boolean isShowInactive) throws RemoteException {
        StoreMessageDataVector storeMessageDV = null;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCrit = new DBCriteria();
            dbCrit
                    .addJoinTable(StoreMessageAssocDataAccess.CLW_STORE_MESSAGE_ASSOC);
            dbCrit.addJoinCondition(StoreMessageDataAccess.STORE_MESSAGE_ID,
                    StoreMessageAssocDataAccess.CLW_STORE_MESSAGE_ASSOC,
                    StoreMessageAssocDataAccess.STORE_MESSAGE_ID);
            dbCrit.addJoinTableEqualTo(
                    StoreMessageAssocDataAccess.CLW_STORE_MESSAGE_ASSOC,
                    StoreMessageAssocDataAccess.BUS_ENTITY_ID, pStoreId);
            dbCrit.addJoinTableEqualTo(
                    StoreMessageAssocDataAccess.CLW_STORE_MESSAGE_ASSOC,
                    StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD,
                    RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_STORE);
            if (pCriteria != null && Utility.isSet(pCriteria.getStringValue())) {
                if (SearchCriteria.MESSAGE_ID.equals(pCriteria.getName())
                        && SearchCriteria.EXACT_MATCH == pCriteria
                                .getOperator()) {
                    dbCrit.addEqualTo(StoreMessageDataAccess.STORE_MESSAGE_ID,
                            Utility.parseInt(pCriteria.getStringValue()));
                } else if (SearchCriteria.MESSAGE_TITLE.equals(pCriteria
                        .getName())
                        && SearchCriteria.BEGINS_WITH_IGNORE_CASE == pCriteria
                                .getOperator()) {
                    dbCrit.addBeginsWithIgnoreCase(
                            StoreMessageDataAccess.MESSAGE_TITLE, pCriteria
                                    .getStringValue());
                } else if (SearchCriteria.MESSAGE_TITLE.equals(pCriteria
                        .getName())
                        && SearchCriteria.CONTAINS_IGNORE_CASE == pCriteria
                                .getOperator()) {
                    dbCrit.addContainsIgnoreCase(
                            StoreMessageDataAccess.MESSAGE_TITLE, pCriteria
                                    .getStringValue());
                } else if (SearchCriteria.MESSAGE_TITLE.equals(pCriteria
                        .getName())
                        && SearchCriteria.EXACT_MATCH == pCriteria
                                .getOperator()) {
                    dbCrit.addEqualTo(StoreMessageDataAccess.MESSAGE_TITLE,
                            pCriteria.getStringValue());
                } else if (SearchCriteria.MESSAGE_TITLE.equals(pCriteria
                        .getName())
                        && SearchCriteria.EXACT_MATCH_IGNORE_CASE == pCriteria
                                .getOperator()) {
                    dbCrit.addEqualToIgnoreCase(
                            StoreMessageDataAccess.MESSAGE_TITLE, pCriteria
                                    .getStringValue());
                }
            }
            if (pPostedDateFrom != null) {
                dbCrit.addGreaterOrEqual(StoreMessageDataAccess.POSTED_DATE,
                        pPostedDateFrom);
            }
            if (pPostedDateTo != null) {
                dbCrit.addLessOrEqual(StoreMessageDataAccess.POSTED_DATE,
                        pPostedDateTo);
            }
            if (Utility.isSet(pCountry)) {
                dbCrit.addEqualTo(StoreMessageDataAccess.COUNTRY, pCountry);
            }
            if (Utility.isSet(pLanguageCd)) {
                dbCrit.addEqualTo(StoreMessageDataAccess.LANGUAGE_CD,
                        pLanguageCd);
            }
            if (!isShowInactive) {
                dbCrit.addEqualTo(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.STATUS_CD.ACTIVE);
            }
            storeMessageDV = StoreMessageDataAccess.select(conn, dbCrit);
            return storeMessageDV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getMessagesByCriteria:" + e);
        } finally {
            closeConnection(conn);
        }
    }

    public IdVector getConfiguratedAccounts(int pMessageId)
            throws RemoteException {
        IdVector result = new IdVector();
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(StoreMessageAssocDataAccess.STORE_MESSAGE_ID,
                    pMessageId);
            dbCrit.addEqualTo(
                    StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD,
                    RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT);
            StoreMessageAssocDataVector dv = StoreMessageAssocDataAccess
                    .select(con, dbCrit);
            for (int i = 0; dv != null && i < dv.size(); i++) {
                StoreMessageAssocData d = (StoreMessageAssocData) dv.get(i);
                result.add(d.getBusEntityId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getConfiguratedAccounts:" + e);
        } finally {
            closeConnection(con);
        }
        return result;
    }

    public void configureAccounts(int pMessageId, IdVector pNewAccountIds,
            IdVector pRemoveAccountIds, String pModBy) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            updateMessageAssoc(con, pMessageId,
                    RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT,
                    pNewAccountIds, pRemoveAccountIds, pModBy);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("configureAccounts:" + e);
        } finally {
            closeConnection(con);
        }
    }

    public void configureAllAccounts(int pMessageId, int pStoreId,
            String pModBy) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctStoreReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
                    BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, acctStoreReq);
            dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            IdVector acctIds = BusEntityDataAccess.selectIdOnly(conn,
                    BusEntityDataAccess.BUS_ENTITY_ID, dbc);
            updateMessageAssoc(conn, pMessageId,
                    RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT,
                    acctIds, null, pModBy);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("configureAllAccounts:" + e);
        } finally {
            closeConnection(conn);
        }
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
    /*
    public StoreMessageDataVector getMessagesForUser(Integer userId, 
    		BusEntityDataVector busEntities, boolean forcedReadOnly) throws RemoteException {
    	
        StoreMessageDataVector returnValue = new StoreMessageDataVector();
        Connection conn = null;
        
        try {
            conn = getConnection();
            
            //retrieve the data for the specified user (we need to know their locale in order
            //to retrieve the appropriate messages)
            UserData user = APIAccess.getAPIAccess().getUserAPI().getUser(userId);
            Locale userLocale = null;
            if (Utility.isSet(user.getPrefLocaleCd())) {
            	userLocale = Utility.parseLocaleCode(user.getPrefLocaleCd());
            }
            else {
            	userLocale = java.util.Locale.US;
            }
            
            //get the ids of the messages associated with the specified business entities
            List<Integer> busEntityIds = new ArrayList<Integer>();
            Set<String> busEntityAssocCds = new HashSet<String>();
            Iterator busEntityIterator = busEntities.iterator();
            while (busEntityIterator.hasNext()) {
            	BusEntityData busEntity = (BusEntityData)busEntityIterator.next();
            	busEntityIds.add(busEntity.getBusEntityId());
            	String busEntityTypeCd = busEntity.getBusEntityTypeCd();
            	if (RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equalsIgnoreCase(busEntityTypeCd)) {
            		busEntityAssocCds.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_STORE);
            	}
            	else if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equalsIgnoreCase(busEntityTypeCd)) {
            		busEntityAssocCds.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT);
            	}
            	else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equalsIgnoreCase(busEntityTypeCd)) {
            		busEntityAssocCds.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_SITE);
            	}
            }
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addOneOf(StoreMessageAssocDataAccess.BUS_ENTITY_ID, busEntityIds);
            dbCrit.addOneOf(StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD, new ArrayList<String>(busEntityAssocCds));
            String messageIds = StoreMessageAssocDataAccess.getSqlSelectIdOnly(StoreMessageAssocDataAccess.STORE_MESSAGE_ID, dbCrit);
            
            //get the current, published, active messages with any of the ids retrieved above that have the
            //same language as the specified user, sorted in descending order of posted date
            //and then by message title.
            dbCrit = new DBCriteria();
            //NOTE - for some reason (I believe it is due to the way date comparison criteria is
            //		 included in a query) the posted date criteria isn't working correctly. No 
            //		 messages with a posted date equal to today are returned.  So, DBCriteria
            //		 objects are not used for posted and end dates.  Instead, posted and end date
            //		 validation is done below.
            //dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.PUBLISHED, Constants.TRUE);
            //dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.MESSAGE_STATUS_CD.ACTIVE);
            dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.MESSAGE_STATUS_CD.PUBLISHED);
            dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.LANGUAGE_CD, userLocale.getLanguage());
            dbCrit.addOneOf(StoreMessageDataAccess.STORE_MESSAGE_ID, messageIds);
            dbCrit.addOrderBy(StoreMessageDataAccess.MESSAGE_TYPE, true);
            dbCrit.addOrderBy(StoreMessageDataAccess.POSTED_DATE, false);
            dbCrit.addOrderBy(StoreMessageDataAccess.ADD_DATE, false);
            dbCrit.addOrderBy(StoreMessageDataAccess.MESSAGE_TITLE, true);
            //if the caller wants only forced read messages, restrict the messages to only those
            //that are forced read.
            if (forcedReadOnly) {
                //dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.FORCED_READ, Constants.TRUE);
                dbCrit.addOneOf(StoreMessageDataAccess.MESSAGE_TYPE, Utility.getAsList(
                		RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ,
                    RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED));
            }
            StoreMessageDataVector messages = StoreMessageDataAccess.select(conn, dbCrit);
            
            //cycle through the retrieved messages, performing the following actions:
            // 1) filter out any messages with a posted date after the current 
            //	  date/time and/or an end date before the current date/time.
            // 2) store the ids of the messages in the order they were returned from the
            //	  above query, so we can return them to the caller in the proper order.
            // 3) find the version of each message with a given title that most closely matches 
            //	  the locale of the specified user.
            Date now = new Date();
       		Calendar gCalendar = Calendar.getInstance();
            Map<String, StoreMessageData> titleToMessageMap = new HashMap<String, StoreMessageData>();
            List<Integer> messageOrderList = new ArrayList<Integer>();
            Iterator messageIterator = messages.iterator();
            while (messageIterator.hasNext()) {
            	StoreMessageData message = (StoreMessageData)messageIterator.next();
                //filter out any messages that have a posted date after the current date/time
                //or an end date before the current date/time
            	Date postedDate = null;
            	if (message.getPostedDate() != null) {
            		gCalendar.setTime(message.getPostedDate());
            		gCalendar.set(Calendar.HOUR_OF_DAY, 0);
            		gCalendar.set(Calendar.MINUTE, 0);
            		gCalendar.set(Calendar.SECOND, 0);
            		postedDate = gCalendar.getTime();
            	}
            	Date endDate = null;
            	if (message.getEndDate() != null) {
            		gCalendar.setTime(message.getEndDate());
            		gCalendar.set(Calendar.HOUR_OF_DAY, 23);
            		gCalendar.set(Calendar.MINUTE, 59);
            		gCalendar.set(Calendar.SECOND, 59);
            		endDate = gCalendar.getTime();
            	}
            	if ((postedDate != null && postedDate.after(now)) || 
            			(endDate != null && endDate.before(now))) {
            		messageIterator.remove();
            	}
            	else {
                	messageOrderList.add(message.getStoreMessageId());
                	String title = message.getMessageTitle();
                	//this message most closely matches the locale of the specified user if:
                	// - this is the first message with a given title and the country value of the
                	//	 message is empty
                	// - the language and country values of the message match the locale of the user
                	String messageCountry = message.getCountry();
                	if ((titleToMessageMap.get(title) == null && !Utility.isSet(messageCountry)) ||
                			(Utility.isSet(messageCountry) && messageCountry.equalsIgnoreCase(userLocale.getCountry()))) {
                		titleToMessageMap.put(title, message);
                	}
            	}
            }
            
            //create a map of ids to messages, into which we'll put the messages we're
            //going to return
            Map<Integer, StoreMessageData> idToMessageMap = new HashMap<Integer, StoreMessageData>();
            
            //if forced read is true, put only those messages that have not been 
            //read the required number of times by the specified user into the map
            if (forcedReadOnly) {
            	messageIterator = titleToMessageMap.values().iterator();
                while (messageIterator.hasNext()) {
                	StoreMessageData message = (StoreMessageData)messageIterator.next();
                    dbCrit = new DBCriteria();
                    dbCrit.addEqualTo(UserMessageAssocDataAccess.STORE_MESSAGE_ID, message.getStoreMessageId());
                    dbCrit.addEqualTo(UserMessageAssocDataAccess.USER_ID, user.getUserId());
                    UserMessageAssocDataVector readRecords = UserMessageAssocDataAccess.select(conn, dbCrit);
                    if (readRecords.size() < message.getForcedReadCount()) {
                    	idToMessageMap.put(message.getStoreMessageId(), message);
                    }
                }
            }
            //otherwise put all the messages we've found into the map
            else {
            	messageIterator = titleToMessageMap.values().iterator();
                while (messageIterator.hasNext()) {
                	StoreMessageData message = (StoreMessageData)messageIterator.next();
                	idToMessageMap.put(message.getStoreMessageId(), message);
                }
            }
            
            //finally, add the messages we're going to return into the return list in the
            //correct order
            Iterator<Integer> idIterator = messageOrderList.iterator();
            while (idIterator.hasNext()) {
            	StoreMessageData message = idToMessageMap.get(idIterator.next());
            	if (message != null) {
            		returnValue.add(message);
            	}
            }
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
        return returnValue;
    }
    */

	/**
	 * Method to retrieve a specific message for a user.  The message is returned if:
	 *  - the message is configured to at least one of the specified business entities
	 *  - if includeExpired is false,
	 *  	- the date posted value of the message is on or before today's date
	 *  	- the end date value of the message is on or after today's date
	 *  - the message has been published
	 *  - the message is active
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
    /*
    public StoreMessageData getMessageForUser(Integer userId, Integer messageId,
    		BusEntityDataVector busEntities, boolean includeExpired) throws RemoteException {
    	
        StoreMessageData returnValue = null;
        Connection conn = null;
        
        try {
            conn = getConnection();
            
            //retrieve the data for the specified user (we need to know their locale in order
            //to validate the message)
            UserData user = APIAccess.getAPIAccess().getUserAPI().getUser(userId);
            Locale userLocale = null;
            if (Utility.isSet(user.getPrefLocaleCd())) {
            	userLocale = Utility.parseLocaleCode(user.getPrefLocaleCd());
            }
            else {
            	userLocale = java.util.Locale.US;
            }
            
            //get the specified message, ensuring it is published, active, and has the same
            //language value as the specified user.
            DBCriteria dbCrit = new DBCriteria();
            //NOTE - for some reason (I believe it is due to the way date comparison criteria is
            //		 included in a query) the posted date criteria isn't working correctly. No 
            //		 messages with a posted date equal to today are returned.  So, DBCriteria
            //		 objects are not used for posted and end dates.  Instead, posted and end date
            //		 validation is done below.
            //dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.PUBLISHED, Constants.TRUE);
            //dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.MESSAGE_STATUS_CD.ACTIVE);
            dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.MESSAGE_STATUS_CD.PUBLISHED);
            dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.LANGUAGE_CD, userLocale.getLanguage());
            dbCrit.addEqualTo(StoreMessageDataAccess.STORE_MESSAGE_ID, messageId);
            StoreMessageDataVector messages = StoreMessageDataAccess.select(conn, dbCrit);
            //if we found the message, perform additional validation
            if (messages.size() == 1) {
            	StoreMessageData message = (StoreMessageData)messages.get(0);
            	//if expired messages are not to be returned, make sure the message is still current
           		Calendar gCalendar = Calendar.getInstance();
            	Date postedDate = null;
            	if (message.getPostedDate() != null) {
            		gCalendar.setTime(message.getPostedDate());
            		gCalendar.set(Calendar.HOUR_OF_DAY, 0);
            		gCalendar.set(Calendar.MINUTE, 0);
            		gCalendar.set(Calendar.SECOND, 0);
            		postedDate = gCalendar.getTime();
            	}
            	Date endDate = null;
            	if (message.getEndDate() != null) {
            		gCalendar.setTime(message.getEndDate());
            		gCalendar.set(Calendar.HOUR_OF_DAY, 23);
            		gCalendar.set(Calendar.MINUTE, 59);
            		gCalendar.set(Calendar.SECOND, 59);
            		endDate = gCalendar.getTime();
            	}
                Date now = new Date();
            	boolean isMessageCurrent = !((postedDate != null && postedDate.after(now)) ||
            							   (endDate != null && endDate.before(now)));
            	if (includeExpired || isMessageCurrent) {
            		//make sure the message originated from one of the specified business entities
            		List busEntityIds = new ArrayList();
            		List busEntityAssocCds = new ArrayList();
            		Iterator busEntityIterator = busEntities.iterator();
                    while (busEntityIterator.hasNext()) {
                    	BusEntityData busEntity = (BusEntityData)busEntityIterator.next();
                    	busEntityIds.add(busEntity.getBusEntityId());
                    	String busEntityTypeCd = busEntity.getBusEntityTypeCd();
                    	if (RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equalsIgnoreCase(busEntityTypeCd)) {
                    		busEntityAssocCds.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_STORE);
                    	}
                    	else if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equalsIgnoreCase(busEntityTypeCd)) {
                    		busEntityAssocCds.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT);
                    	}
                    	else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equalsIgnoreCase(busEntityTypeCd)) {
                    		busEntityAssocCds.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_SITE);
                    	}
                    }
                    dbCrit = new DBCriteria();
            		dbCrit.addEqualTo(StoreMessageAssocDataAccess.STORE_MESSAGE_ID, messageId);
                    dbCrit.addOneOf(StoreMessageAssocDataAccess.BUS_ENTITY_ID, busEntityIds);
                    dbCrit.addOneOf(StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD, new ArrayList<String>(busEntityAssocCds));
                    StoreMessageAssocDataVector associations = StoreMessageAssocDataAccess.select(conn, dbCrit);
                    if (associations.size() > 0) {
                    	returnValue = message;
                    }
            	}
            }
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
        return returnValue;
    }
    */

	/**
	 * Method to mark a message as read by a user.
	 * @param userId - the id of the user.
	 * @param messageId - the id of the message.
	 * @throws DataNotFoundException, RemoteException
	 */
    public void markMessageAsReadByUser(Integer userId, Integer messageId) 
    		throws DataNotFoundException, RemoteException {

        Connection conn = null;
        
        try {
            conn = getConnection();
            
            //get the user, to make sure they are valid
            UserData user = APIAccess.getAPIAccess().getUserAPI().getUser(userId.intValue());
            if (user == null) {
            	throw new DataNotFoundException("User with id=" + userId + " not found.");
            }
            /*
            //get the specified message, to make sure it is valid
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(StoreMessageDataAccess.STORE_MESSAGE_ID, messageId);
            StoreMessageDataVector messages = StoreMessageDataAccess.select(conn, dbCrit);
            //if we didn't find the message, throw an exception
            if (messages.size() != 1) {
            	throw new DataNotFoundException("Message with id=" + messageId + " not found.");
            }
            */
            
            //create a user/message association
            UserMessageAssocData association = UserMessageAssocData.createValue();
            association.setReadDate(new Date());
            association.setStoreMessageId(messageId.intValue());
            association.setUserId(userId.intValue());
            UserMessageAssocDataAccess.insert(conn, association);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

	/**
	 * Method to retrieve the list of messages relevant for a user. A message is
	 * relevant if: - the message is configured to at least one of the specified
	 * business entities - the date posted value of the message is on or before
	 * today's date - the end date value of the message is on or after today's
	 * date - the message has been published - the message is active - the message
	 * has a language value matching the language of the specified user.
	 *  - if forcedRead is true, then a
	 * message must be 'forcedRead' or 'Acknowledgement Required' type and not have been read by the
	 * specified user the required number of times.
	 * 
	 * @param userId
	 *          - the id of the user for whom the messages are being retrieved.
	 * @param busEntities
	 *          - a <code>BusEntityDataVector</code> containing the business
	 *          entities from which the messages originated.
	 * @param forcedReadOnly
	 *          - a boolean to indicate if only 'forcedRead' or 'Acknowledgement Required' 
	 *          type messages should be returned.
	 * @return a <code>StoreMessageViewVector</code> containing the relevant
	 *         messages.
	 * @throws RemoteException
	 */
	public StoreMessageViewVector getMessagesViewForUser(Integer userId,
			BusEntityDataVector busEntities, boolean forcedReadOnly)
			throws RemoteException {

		StoreMessageViewVector returnValue = new StoreMessageViewVector();
		Connection conn = null;

		try {
			conn = getConnection();

			// retrieve the data for the specified user (we need to know their locale
			// in order
			// to retrieve the appropriate messages)
			UserData user = APIAccess.getAPIAccess().getUserAPI().getUser(userId);
			Locale userLocale = null;
			if (Utility.isSet(user.getPrefLocaleCd())) {
				userLocale = Utility.parseLocaleCode(user.getPrefLocaleCd());
			} else {
				userLocale = java.util.Locale.US;
			}

			// get the ids of the messages associated with the specified business
			// entities
			List<Integer> busEntityIds = new ArrayList<Integer>();
			Set<String> busEntityAssocCds = new HashSet<String>();
			Iterator busEntityIterator = busEntities.iterator();
			while (busEntityIterator.hasNext()) {
				BusEntityData busEntity = (BusEntityData) busEntityIterator.next();
				busEntityIds.add(busEntity.getBusEntityId());
				String busEntityTypeCd = busEntity.getBusEntityTypeCd();
				if (RefCodeNames.BUS_ENTITY_TYPE_CD.STORE
						.equalsIgnoreCase(busEntityTypeCd)) {
					busEntityAssocCds
							.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_STORE);
				} else if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT
						.equalsIgnoreCase(busEntityTypeCd)) {
					busEntityAssocCds
							.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT);
				} else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE
						.equalsIgnoreCase(busEntityTypeCd)) {
					busEntityAssocCds
							.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_SITE);
				}
			}
			DBCriteria dbCrit = new DBCriteria();
			dbCrit.addOneOf(StoreMessageAssocDataAccess.BUS_ENTITY_ID, busEntityIds);
			dbCrit.addOneOf(StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD,
					new ArrayList<String>(busEntityAssocCds));
			String messageIds = StoreMessageAssocDataAccess.getSqlSelectIdOnly(
					StoreMessageAssocDataAccess.STORE_MESSAGE_ID, dbCrit);

			// get the current, published, active messages with any of the ids
			// retrieved above that have the
			// same language as the specified user, sorted in descending order of
			// posted date
			// and then by message title.
			dbCrit = new DBCriteria();
			//MANTA-305
			dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.STORE_MESSAGE_STATUS_CD.ACTIVE);
			dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.PUBLISHED,"true");
		//	dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.LANGUAGE_CD,					userLocale.getLanguage());
			dbCrit.addOneOf(StoreMessageDataAccess.STORE_MESSAGE_ID, messageIds);
			dbCrit.addOrderBy(StoreMessageDataAccess.MESSAGE_TYPE, true);
			dbCrit.addOrderBy(StoreMessageDataAccess.DISPLAY_ORDER, true);
			dbCrit.addOrderBy(StoreMessageDataAccess.POSTED_DATE, false);
			dbCrit.addOrderBy(StoreMessageDataAccess.ADD_DATE, false);
			//dbCrit.addOrderBy(StoreMessageDataAccess.MESSAGE_TITLE, true);
			dbCrit.addOrderBy(StoreMessageDataAccess.SHORT_DESC, true);
			// if the caller wants only forced read messages, restrict the messages to
			// only those that are forced read.
			/*if (forcedReadOnly) {
				dbCrit.addOneOf(StoreMessageDataAccess.MESSAGE_TYPE, Utility.getAsList(
						RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ,
						RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED));
			}*/

			StoreMessageDAO dao = new StoreMessageDAO();
			StoreMessageViewVector allMessages = new StoreMessageViewVector();

			List messageOrderList = new ArrayList();
			Map<Integer, Map<String, StoreMessageView>> localeMap = new HashMap<Integer,  Map<String, StoreMessageView>>();
		
			String defaultKey = "default";
			String localeKey = userLocale.getLanguage()+userLocale.getCountry();
			String languageKey = userLocale.getLanguage();
			String countryKey = userLocale.getCountry();
			
			//ACKNOWLEDGEMENT and FORCED READ
			StoreMessageViewVector frMessages = dao.getMessagesForUser(conn, dbCrit, 0, 
					Utility.getAsList(RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED, RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ));
			if(frMessages!=null && frMessages.size()>0){
				allMessages.addAll(frMessages);
			}
			
			//REGULAR
			if(!forcedReadOnly){
				StoreMessageViewVector regularMessages = dao.getMessagesForUser(conn, dbCrit, 0,
						Utility.getAsList(RefCodeNames.MESSAGE_TYPE_CD.REGULAR));
				if(regularMessages!=null && regularMessages.size()>0){
					allMessages.addAll(regularMessages);
				}
			}
			
			Date now = new Date();
			Calendar gCalendar = Calendar.getInstance();
			
			Iterator messageIterator = allMessages.iterator();
			
			while (messageIterator.hasNext()) {
				StoreMessageView message = (StoreMessageView) messageIterator.next();
				// filter out any messages that have a posted date after the current
				// date/time
				// or an end date before the current date/time
				Date postedDate = null;
				if (message.getPostedDate() != null) {
					gCalendar.setTime(message.getPostedDate());
					gCalendar.set(Calendar.HOUR_OF_DAY, 0);
					gCalendar.set(Calendar.MINUTE, 0);
					gCalendar.set(Calendar.SECOND, 0);
					postedDate = gCalendar.getTime();
				}
				Date endDate = null;
				if (message.getEndDate() != null) {
					gCalendar.setTime(message.getEndDate());
					gCalendar.set(Calendar.HOUR_OF_DAY, 23);
					gCalendar.set(Calendar.MINUTE, 59);
					gCalendar.set(Calendar.SECOND, 59);
					endDate = gCalendar.getTime();
				}
				if ((postedDate != null && postedDate.after(now))
						|| (endDate != null && endDate.before(now))) {
					messageIterator.remove();
				} 
			}
			//Collections.sort(allMessages, STORE_MESSAGE_COMPARE);
			
			//Populate localeMap with all messages
			Iterator messageIterator2 = allMessages.iterator();
			while (messageIterator2.hasNext()) {
				StoreMessageView message = (StoreMessageView) messageIterator2.next();
				
				Integer messageId = new Integer(message.getStoreMessageId());
				if(!messageOrderList.contains(messageId)){
					messageOrderList.add(messageId);
				}
				String msgCountry = message.getCountry();
				String msgLanguage = message.getLanguageCd();
				
				if (localeMap.get(messageId) == null) {
					localeMap.put(messageId,new HashMap<String, StoreMessageView>());
				}
				
				if(Utility.isSet(msgLanguage) || Utility.isSet(msgCountry)){
					if ((Utility.isSet(msgLanguage) && languageKey.equals(msgLanguage))
							&& (Utility.isSet(msgCountry) && countryKey.equals(msgCountry))) {
						localeMap.get(messageId).put(localeKey, message);
						
					} else if ((Utility.isSet(msgLanguage) && languageKey.equals(msgLanguage)) 
							&& !(Utility.isSet(msgCountry))){
						localeMap.get(messageId).put(languageKey, message);
						
					} else if ((Utility.isSet(msgCountry) && countryKey.equals(msgCountry)) 
							&& !(Utility.isSet(msgLanguage))){
						localeMap.get(messageId).put(countryKey, message);
						
					} 
				}else {
					localeMap.get(messageId).put(defaultKey, message);
				}
			}
			
			//Match messages with users locale
			Map<Integer, StoreMessageView> messageToDetailMap = new HashMap<Integer, StoreMessageView>();
			
			Iterator localeMapIt = localeMap.values().iterator();
			while (localeMapIt.hasNext()) {
				Map<String, StoreMessageView> messageLocales =  (Map<String, StoreMessageView>)localeMapIt.next();
				
				StoreMessageView msg = null;
				
				if(messageLocales.get(localeKey) != null){
					msg = (StoreMessageView) messageLocales.get(localeKey);
					messageToDetailMap.put(msg.getStoreMessageId(), msg);
				}else if(messageLocales.get(languageKey) != null){
					msg = (StoreMessageView) messageLocales.get(languageKey);
					messageToDetailMap.put(msg.getStoreMessageId(), msg);
				} else if(messageLocales.get(countryKey) != null){
					msg = (StoreMessageView) messageLocales.get(countryKey);
					messageToDetailMap.put(msg.getStoreMessageId(), msg);
				} else if(messageLocales.get(defaultKey) != null){
					msg = (StoreMessageView) messageLocales.get(defaultKey);
					messageToDetailMap.put(msg.getStoreMessageId(), msg);
				} 
				
			}
			
			Map<Integer, StoreMessageView> idToMessageMap = new HashMap<Integer, StoreMessageView>();
		
			Iterator messageIterator3 = messageToDetailMap.values().iterator();
			while (messageIterator3.hasNext()) {
				StoreMessageView message = (StoreMessageView) messageIterator3.next();
				if(message.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED) || 
						message.getMessageType().equals(RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ)){
					dbCrit = new DBCriteria();
					dbCrit.addEqualTo(UserMessageAssocDataAccess.STORE_MESSAGE_ID,
							message.getStoreMessageId());
					dbCrit.addEqualTo(UserMessageAssocDataAccess.USER_ID,
							user.getUserId());
					UserMessageAssocDataVector readRecords = UserMessageAssocDataAccess.select(conn, dbCrit);
					if (readRecords.size() >= message.getForcedReadCount()) {
						if(forcedReadOnly){ 
							messageIterator3.remove();
						}else{
							//need to display Acknowledgement,Forced Read messages on dashboard even after they have been read as interstitial messages
							idToMessageMap.put(message.getStoreMessageId(), message);
						}
					}else{
						idToMessageMap.put(message.getStoreMessageId(), message);
					}
				}else{
					idToMessageMap.put(message.getStoreMessageId(), message);
				}
					
			}
			
			Iterator<Integer> idIterator = messageOrderList.iterator();
			while (idIterator.hasNext()) {
				StoreMessageView message = (StoreMessageView) idToMessageMap.get(idIterator.next());
				if (message != null) {
					returnValue.add(message);
				}
			}
			
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		} finally {
			closeConnection(conn);
		}
		return returnValue;
	}
	
	static final Comparator STORE_MESSAGE_COMPARE = new Comparator(){
		public int compare(Object o1, Object o2)
        {
			StoreMessageView obj1 = (StoreMessageView)o1;
			StoreMessageView obj2 = (StoreMessageView)o2;
			
			int c1 = Utility.compareToIgnoreCase(obj1.getMessageType(), obj2.getMessageType());
			if(c1!=0){
				return c1;
			}else{
                
				int c2 = obj1.getDisplayOrder()-obj2.getDisplayOrder();
				if(c2!=0){
					return c2;
				}else{
					int c3=0;
					Date p1 = new Date();
					Date p2 = new Date();
					if(obj1.getPostedDate()!=null){
						p1 = obj1.getPostedDate();
					}
					if(obj2.getPostedDate()!=null){
						p2 = obj2.getPostedDate();
					}
					c3 = p1.compareTo(p2);
					
					if(c3!=0){
						return c3;
					}else{
			
						int c4 = obj1.getAddDate().compareTo(obj2.getAddDate());
						if(c4!=0){
							return c4;
						}
					}
				}
			}
			return 0;
        }
	};

	/**
	 * Method to retrieve a specific message for a user. The message is returned
	 * if: - the message is configured to at least one of the specified business
	 * entities - if includeExpired is false, - the date posted value of the
	 * message is on or before today's date - the end date value of the message is
	 * on or after today's date - the message has been published - the message is
	 * active - the message has a language value matching the language of the
	 * specified user.
	 * 
	 * @param userId
	 *          - the id of the user for whom the message is being retrieved.
	 * @param messageId
	 *          - the id of the message to retrieve.
	 * @param busEntities
	 *          - a <code>BusEntityDataVector</code> containing the business
	 *          entities from which the message originated.
	 * @param includeExpired
	 *          - a boolean to indicate if an expired message should be returned.
	 * @return a <code>StoreMessageView</code> containing the data for the
	 *         message, or null if the conditions above are not met.
	 * @throws RemoteException
	 */
	public StoreMessageView getMessageViewForUser(Integer userId,
			Integer messageId, BusEntityDataVector busEntities, boolean includeExpired)
			throws RemoteException {

		StoreMessageView returnValue = null;
		Connection conn = null;

		try {
			conn = getConnection();

			// retrieve the data for the specified user (we need to know their locale
			// in order
			// to validate the message)
			UserData user = APIAccess.getAPIAccess().getUserAPI().getUser(userId);
			Locale userLocale = null;
			if (Utility.isSet(user.getPrefLocaleCd())) {
				userLocale = Utility.parseLocaleCode(user.getPrefLocaleCd());
			} else {
				userLocale = java.util.Locale.US;
			}

			// get the specified message, ensuring it is published, active, and has
			// the same
			// language value as the specified user.
			DBCriteria dbCrit = new DBCriteria();
			//MANTA-305
			dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.STORE_MESSAGE_STATUS_CD.ACTIVE);
			dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.PUBLISHED,"true");
			//dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.LANGUAGE_CD,					userLocale.getLanguage());
			dbCrit.addEqualTo(StoreMessageDataAccess.STORE_MESSAGE_ID, messageId);
		
			StoreMessageDAO dao = new StoreMessageDAO();
			String languageCd = userLocale.getLanguage();
			String countryCd = userLocale.getCountry();
			
			StoreMessageViewVector messages = dao.getMessagesForUser(conn, dbCrit,  0);
			StoreMessageView message = new StoreMessageView();
			
			if(messages != null){
				
				StoreMessageView localeMsg = null;
				StoreMessageView languageMsg = null;
				StoreMessageView defaultMsg = null;
				
				Iterator it = messages.iterator();
				while(it.hasNext()){
					StoreMessageView mess = (StoreMessageView)it.next();
					
					if( (Utility.isSet(mess.getLanguageCd()) && mess.getLanguageCd().equals(languageCd)) &&
							(Utility.isSet(mess.getCountry()) && mess.getCountry().equals(countryCd)) ){
						localeMsg = mess;
					}else if((Utility.isSet(mess.getLanguageCd()) && mess.getLanguageCd().equals(languageCd))
							&& !(Utility.isSet(mess.getCountry()))){
						languageMsg = mess;
					}else if(!Utility.isSet(mess.getLanguageCd()) && !Utility.isSet(mess.getCountry())){
						defaultMsg = mess;
					}
				}
				
				if(localeMsg!=null){
					message = localeMsg;
				}else if(languageMsg!=null){
					message = languageMsg;
				}else{
					message = defaultMsg;
				}
			
			// if we found the message, perform additional validation
			//if (messages.size() == 1) {
				//StoreMessageView message = (StoreMessageView) messages.get(0);
				// if expired messages are not to be returned, make sure the message is
				// still current
				Calendar gCalendar = Calendar.getInstance();
				Date postedDate = null;
				if (message.getPostedDate() != null) {
					gCalendar.setTime(message.getPostedDate());
					gCalendar.set(Calendar.HOUR_OF_DAY, 0);
					gCalendar.set(Calendar.MINUTE, 0);
					gCalendar.set(Calendar.SECOND, 0);
					postedDate = gCalendar.getTime();
				}
				Date endDate = null;
				if (message.getEndDate() != null) {
					gCalendar.setTime(message.getEndDate());
					gCalendar.set(Calendar.HOUR_OF_DAY, 23);
					gCalendar.set(Calendar.MINUTE, 59);
					gCalendar.set(Calendar.SECOND, 59);
					endDate = gCalendar.getTime();
				}
				Date now = new Date();
				boolean isMessageCurrent = !((postedDate != null && postedDate
						.after(now)) || (endDate != null && endDate.before(now)));
				if (includeExpired || isMessageCurrent) {
					// make sure the message originated from one of the specified business
					// entities
					List busEntityIds = new ArrayList();
					List busEntityAssocCds = new ArrayList();
					Iterator busEntityIterator = busEntities.iterator();
					while (busEntityIterator.hasNext()) {
						BusEntityData busEntity = (BusEntityData) busEntityIterator.next();
						busEntityIds.add(busEntity.getBusEntityId());
						String busEntityTypeCd = busEntity.getBusEntityTypeCd();
						if (RefCodeNames.BUS_ENTITY_TYPE_CD.STORE
								.equalsIgnoreCase(busEntityTypeCd)) {
							busEntityAssocCds
									.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_STORE);
						} else if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT
								.equalsIgnoreCase(busEntityTypeCd)) {
							busEntityAssocCds
									.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT);
						} else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE
								.equalsIgnoreCase(busEntityTypeCd)) {
							busEntityAssocCds
									.add(RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_SITE);
						}
					}
					dbCrit = new DBCriteria();
					dbCrit.addEqualTo(StoreMessageAssocDataAccess.STORE_MESSAGE_ID,
							messageId);
					dbCrit.addOneOf(StoreMessageAssocDataAccess.BUS_ENTITY_ID,
							busEntityIds);
					dbCrit.addOneOf(StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD,
							new ArrayList<String>(busEntityAssocCds));
					StoreMessageAssocDataVector associations = StoreMessageAssocDataAccess
							.select(conn, dbCrit);
					if (associations.size() > 0) {
						returnValue = message;
					}
				}
			//}
			}
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		} finally {
			closeConnection(conn);
		}
		return returnValue;
	}
	
	public StoreMessageViewVector getCustomerDefaultMessages(int pAccountId) throws RemoteException {
	    Connection conn = null;

        try {
            conn = getConnection();

    	    // get messages that associated with pAccountId
    	    DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(StoreMessageAssocDataAccess.BUS_ENTITY_ID, pAccountId);
            dbCrit.addEqualTo(StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD, RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT);
            String messageIds = StoreMessageAssocDataAccess.getSqlSelectIdOnly(
                    StoreMessageAssocDataAccess.STORE_MESSAGE_ID, dbCrit);    
            
            // get message that managed by customer only
            dbCrit = new DBCriteria();
            dbCrit.addEqualToIgnoreCase(StoreMessageDataAccess.STORE_MESSAGE_STATUS_CD, RefCodeNames.STORE_MESSAGE_STATUS_CD.ACTIVE);
            dbCrit.addEqualTo(StoreMessageDataAccess.MESSAGE_MANAGED_BY, RefCodeNames.MESSAGE_MANAGED_BY.CUSTOMER);
            dbCrit.addOneOf(StoreMessageDataAccess.STORE_MESSAGE_ID, messageIds);
            dbCrit.addOrderBy(StoreMessageDataAccess.ADD_DATE);
            
            StoreMessageDAO dao = new StoreMessageDAO();
            return dao.getMessages(conn, dbCrit, 0, null, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getMessagesByCriteria:" + e);
        } finally {
            closeConnection(conn);
        }
    }
	
	public StoreMessageDetailDataVector getStoreMessageDetails(int storeMessageId) throws RemoteException {
	    Connection conn = null;

        try {
            conn = getConnection();

            // get messages that associated with pAccountId
            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addEqualTo(StoreMessageDetailDataAccess.STORE_MESSAGE_ID, storeMessageId);
            dbCrit.addOrderBy(StoreMessageDetailDataAccess.ADD_DATE);    
            return StoreMessageDetailDataAccess.select(conn, dbCrit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getStoreMessageDetails:" + e);
        } finally {
            closeConnection(conn);
        }
	}
	
	public void updateStoreMessage(int storeId, int accountId, StoreMessageData storeMessageD,
            StoreMessageDetailDataVector messageDetailDV, String modBy) throws RemoteException {
	    Connection conn = null;

        try {
            conn = getConnection();
            storeMessageD = updateStoreMessage(storeId, storeMessageD, modBy);
            int messageId = storeMessageD.getStoreMessageId();
                    
            for (Iterator iterator = messageDetailDV.iterator(); iterator.hasNext();) {
                StoreMessageDetailData messageDetail = (StoreMessageDetailData) iterator.next();
                messageDetail.setModBy(modBy);
                                    
                if (messageDetail.getStoreMessageId() == 0){
                    messageDetail.setStoreMessageId(messageId);
                    messageDetail.setAddBy(modBy);
                    StoreMessageDetailDataAccess.insert(conn, messageDetail);
                }else{
                    StoreMessageDetailDataAccess.update(conn, messageDetail);
                }
            }
            
            IdVector pNewAccountIds = new IdVector();
            pNewAccountIds.add(accountId);
            updateMessageAssoc(conn, messageId,
                    RefCodeNames.STORE_MESSAGE_ASSOC_CD.MESSAGE_ACCOUNT,
                    pNewAccountIds, null, modBy);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("updateStoreMessage:" + e);
        } finally {
            closeConnection(conn);
        }
	}
	
	public void deleteStoreMessageDetail(int storeMessageDetailId) throws RemoteException {
	    Connection conn = null;

        try {
            conn = getConnection();
            StoreMessageDetailDataAccess.remove(conn, storeMessageDetailId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("deleteStoreMessageDetail:" + e);
        } finally {
            closeConnection(conn);
        }
	}

}
