package com.cleanwise.service.api.session;

/**
 * Title:        TradingPartner
 * Description:  Remote Interface for TradingPartner Stateless Session Bean
 * Purpose:      Provides access to the methods for msds & specs documents
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.math.BigDecimal;

public interface TradingPartner extends javax.ejb.EJBObject
{
    /**
     * Gets trading partner object by its id.
     * @param pTradingPartnerId the trading partner id
     * @return  TradingPartnerData object
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerData getTradingPartner(int pTradingPartnerId)
	throws RemoteException, DataNotFoundException;
    
    /**
     * Gets busness enitity ids associated with the trading partner
     * @param pTradingPartnerId the trading partner id
     * @param pAssociationCd the association code (ACCOUNT or DISTRIBUTOR so far 10/07/2003)
     * @return  array of int values
     * @throws   RemoteException Required by EJB 1.0
     */
    public int[] getBusEntityIds(int pTradingPartnerId, String pAssociationCd)
	throws RemoteException, DataNotFoundException;

     /**
     * Gets trading partner object for the application if defined.
     * @return  TradingPartnerData object
     * @throws   RemoteException Required by EJB 1.0
     * @throws   DataNotFoundException if there is not one defined
     */
    public TradingPartnerData getApplicationTradingPartner()
	throws RemoteException, DataNotFoundException;

    /**
     * Gets trading partner object by busness entity id. If found more
     * than one, returns the first object
     * @param pBusEntityId the busness entity id
     * @return  TradingPartnerData object
     * @throws   RemoteException Required by EJB 1.0 adn DataNotFoundException
     */
    public TradingPartnerData getTradingPartnerByBusEntity(int pBusEntityId)
	throws RemoteException, DataNotFoundException;
    
    /**
     * Gets trading profile object by its id.
     * @param pTradingProfileId the trading profile id
     * @return  TradingProfileData object
     * @throws   RemoteException Required by EJB 1.0 and DataNotFoundException
     */
    public TradingProfileData getTradingProfile(int pTradingProfileId)
	throws RemoteException, DataNotFoundException;

    public void deleteProfile(int pTradingProfileId)
	throws RemoteException, DataNotFoundException;

    /**
     *@returns the trading profile id
     */
    public int saveTradingProfile
	(TradingPartnerData pTradingPartner, 
	 TradingProfileData pTradingProfile,
	 String pUser )
	throws RemoteException;
    
    /**
     * Gets trading profile object by trading partner id.
     * @param pTradingParntnerId the trading partner id
     * @return  TradingProfileDataVector 
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingProfileDataVector 
	getTradingProfileByPartnerId(int pTradingPartnerId) throws RemoteException;
    
    /**
     * Gets trading profile object by trading partner group sendor and group receiver id.
     * @param pSendorId the trading partner group sender id
     * @param pSendorId the trading partner group receiver id
     * @param pTestIndicator either p for production or t for test
     * @return  TradingProfileData object
     * @throws   RemoteException Required by EJB 1.0
     * @throws   DataNotFoundException if it could not find exactly one Trading Profile
     */
    public TradingProfileData getTradingProfileByGroupSenderAndReceiver(
        String pSender, String pReceiver,String pDirection, String pSetType, String pTestIndicator)
	throws RemoteException,DataNotFoundException;

    /**
     * Inserts new or updates trading partner and trading profile objects
     * @param pTradingPartner the trading partner object
     * @param pTradingPartner the trading profile object
     * @param pUser the user logon name
     * @throws   RemoteException Required by EJB 1.0
     */
    public int saveTradingPartner
	(TradingPartnerData pTradingPartner, String pUser)
	throws RemoteException;

    public int saveTradingPartnerInfo
	(TradingPartnerInfo pTradingPartner, String pUser)
	throws RemoteException;

    public TradingPartnerInfo getTradingPartnerInfo
	(int pTradingPartnerId)
	throws RemoteException, DataNotFoundException;

    /**
     * Returns the TradingPartnerDescView object which would handle the pattern supplied.
     * A file name would be one example of a pattern.  For example:
     *  jwporder_0101200.txt
     * @param    the identifier (or null) to use to limit the scope of our search
     * @param    pPattern the pattern to parse
     * @throws   DataNotFoundException if the pattern could not be parsed
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerDescView getTradingPartnerInfoByPattern(String pTradingProfileByIdentifer,String pUnParsedPattern)
    throws RemoteException, DataNotFoundException;    
    
    /**
     * Gets list of trading partner brief descriptions
     * @param pTradingPartnerType could be CUSTOMER or DISTRIBUTOR or empty
     * @param pBusEntityName a parth of ACCOUNT and/or DISTRIBUTOR short description or empty
     * @param pTradingPartnerName a part of trading partner short descripton or empty
     * @param pTradingType could be PAPER, EDI or XML or empty
     * @param pTradingStatus could be ACTIVE or INACTIVE or LIMITED or empty
     * @param pMatch could be SearchCriteria.BEGINS_WITH_IGNORE_CASE or SearchCriteria.CONTAINS_IGNORE_CASE
     * @return  TradingPartnerViewVector object
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerViewVector
	getTradingPartners(String pTradingPartnerType,
			   String pBusEntityName,
			   int pTradingPartnerId,
			   String pTradingPartnerName,
			   String pTradingType,
			   String pTradingStatus,
			   String ptraidingTypeCD,
			   int pMatch)
	throws RemoteException;

    /**
     * Deletes trading partner and its profile. It is up to database
     * to control relational integrity
     * @param pTradingPartnerId the trading partner id
     * @throws   RemoteException Required by EJB 1.0
     */
    public void deleteTradingPartner(int pTradingPartnerId)
	throws RemoteException;
    
    /**
     * <code>getNextInterchangeControlNum</code> gets the next interchange
     * control number to use.
     *
     * @param pTradingProfileId the trading profile id
     * @return an <code>int</code> value with the control number
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if a trading profile does not exist
     */
    public int getNextInterchangeControlNum(int pTradingProfileId)
	throws RemoteException, DataNotFoundException;

    /**
     * <code>getNextGroupControlNum</code> gets the next group
     * control number to use.
     *
     * @param pTradingProfileId the trading profile id
     * @return an <code>int</code> value with the control number
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if a trading profile does not exist
     */
    public int getNextGroupControlNum(int pTradingProfileId)
	throws RemoteException, DataNotFoundException;

    /**
     *Defines a single data exchange represented by a TradingProfileConfigData
     */
    public void defineDataExchange(TradingProfileConfigData pDataExchange, String pUser)
    throws RemoteException;

    public TradingProfileConfigDataVector
	fetchDataExchanges	(int pTradingPartnerId ) 
	throws RemoteException;

    public void deleteDataExchange(int pTradingProfileConfigId)
	throws RemoteException, DataNotFoundException;

    
    /**
     *Returns all of the tradingProfileMappings for the supplied id
     */
    public TradingPropertyMapDataVector getTradingPropertyMapDataCollection(int tradingProfileId)
    throws RemoteException;
    
    
    /**
     *save the supplied TradingPropertyMapDataVector (adds updates) and returns a new TradingPropertyMapDataVector with any changes.
     */
    public TradingPropertyMapDataVector updateTradingPropertyMapDataCollection
    (TradingPropertyMapDataVector tpmdv) throws RemoteException;
    
    
    /**
     * Fetches all of the associations that are associated with this trading partner.
     * This would include the overides as well as the direct applicable associations.
     * @param pTradingPartnerId the trading partner id to fetch the associations for
     * @return a list of TradingPartnerAssocData objects that are associated with this
     * 		trading partner
     */
    public TradingPartnerAssocDataVector getTradingPartnerAssocDataVectorForPartnerAll(int pTradingPartnerId)
    throws RemoteException;
    
    /**
     * Fetches the associations that are associated with this trading partner.
     * This would NOT include the overides.  Only the associations intended for
     * trading are included (if store trading partner only store associations are returned etc.).
     * @param pTradingPartnerId the trading partner id to fetch the associations for
     * @return a list of TradingPartnerAssocData objects that are associated with this
     * 		trading partner
     */
    public TradingPartnerAssocDataVector getTradingPartnerAssocDataVectorForPartnerForTrading(int pTradingPartnerId)
    throws RemoteException;

    public void deletePropertyMappingData(int pPropertyMappingId)
    throws RemoteException;

    public HashMap getMapTradingPartnerAssocIds(int tradingPartnerId)
    throws RemoteException;

    public int getPartnerId(String erpNum,int busEntityId,String entityType,boolean appTradingPartner,boolean outbound,String optSetType) 
    throws RemoteException;

    public ReportItem getReportItem(int tradingPartnerId)
            throws RemoteException;

    public void setReportItem(ReportItem reportItem, String user)
            throws RemoteException;
    
    public TradingProfileData getTradingProfileByDomainIdentifiers(
            HashMap<String,String> toDomainIds, HashMap<String,String> fromDomainIds, String pDirection, 
            String pSetType, String pTestIndicator)throws RemoteException,DataNotFoundException;
    
    public TradingProfileData getTradingProfileByGroupSenderAndReceiver(
            String pSender, String pReceiver,String pDirection, String pSetType, String pTestIndicator,
            String domain)
            throws RemoteException,DataNotFoundException;
    
    /**
     * Returns the TradingPartnerDescView object 
     * @param    tradingPartnerId the key id
     * @throws   DataNotFoundException if trading partner object not found for id tradingPartnerId
     * @throws   RemoteException Required by EJB 1.0
     */
    public TradingPartnerFullDescView getTradingPartnerInfoById(int tradingPartnerId) throws RemoteException, DataNotFoundException;
    
    public TradingPartnerFullDescView createNewTradingPartner(TradingPartnerFullDescView tpdv)
			throws RemoteException;

}


