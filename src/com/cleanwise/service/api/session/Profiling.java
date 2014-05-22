package com.cleanwise.service.api.session;

/**
 * Title:        Profiling
 * Description:  Remote Interface for Profiling Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Profiling information. 
 * Copyright:    Copyright (c) 2003
 * Company:      Cleanwise, Inc.
 * @author       Brook Stevens, Cleanwise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

public interface Profiling extends javax.ejb.EJBObject
{
    /**
     *Retrieves a ProfileData Object  for the supplied profile id.
     *
     *@param pProfileId the profile id to fetch.
     *@param pStoreIds the list of store ids to restrict it to.
     *@throws RemoteException if an error occurs
     *@returns a ProfileData object matching for the supplied profile id or null.
     */
    public ProfileData getProfile(IdVector storeIds,int pProfileId) throws RemoteException;
    
    public ProfileData getProfileForStore(IdVector storeIds, 
        int pProfileId) throws RemoteException;
    
    /**
     *Retrieves a profile data vector of all profiles that match the specified criteria.
     *reference @see SearchCriteria for valid values of pMatch.
     *
     *@param pStoreIds the list of store ids search criteria.
     *@param String pProfileName the name search criteria
     *@param String pProfileType the profile type you wish to get
     *@param int pMatch how to match the name, if pProfileName is left unset this value is ignored
     *   @see SearchCriteria (all search types are not currently implemented, defaults to
     *   name_contains_ignore_case).
     *@returns ProfileDataVector of all the profiles which match the criteria, or an empty
     *  @see ProfileDataVector if there were no matches
     *@throws RemoteException if an error occurs.
     */
    public ProfileDataVector getProfileCollection(IdVector storeIds, String pProfileName,String pProfileType,int pMatch) 
    throws RemoteException;
    
    public ProfileDataVector getProfileCollectionForStore(IdVector storeIds, 
        String pProfileName, String pProfileType, int pMatch) throws RemoteException;
    
    /**
     *Returns a BusEntityDataVector of the busEntitys that are configured to the supplied profileId.
     *Only direct relationships are returned.
     *@param pProfileId the profile id
     *@param pBusEntityTypeCd the bus entity type to search for.
     *@param pOptionalBusEntityShortDesc if supplied return reults will be restricted to bus entities
     *  with short descriptions containing this String
     *@throws RemoteException if an error occured
     */
    public BusEntityDataVector getBusEntityCollectionForProfile(int pProfileId,String pBusEntityTypeCd,String pOptionalBusEntityShortDesc)
    throws RemoteException;
    
    /**
     *Retrieves the profiles that are availiable to the supplied BusEntityId.
     *
     *@param int pBusEntityId the business entity id. This method is smart enough to figure out all
     *  relationships with a busentityid, i.e. if you give it a site id it will give you back site
     *  relationships, account relationships, and store relationships.  Unless pDirectRelationshipsOnly
     *  equals true, in which case it will only give you the direct relationships
     *@param pDirectRelationshipsOnly indicates that only direct relationships should be returned.  That is
     *  if you pass in an account id it will give you only the relationships to the account, and not the
     *  store.
     *@returns ProfileDataVector a populated ProfileDataVector of the Profiles the supplied
     *  BusEntityId is configured for.
     *@throws RemoteException if an error occurs.
     */
    public ProfileDataVector getProfileCollectionForBusEntity
    (int pBusEntityId,boolean pDirectRelationshipsOnly) 
    throws RemoteException;
    
    /**
     *Retrieves the profile and meta data for supplied ProfileId.  The view objects returned will not
     *have the detail (results) populated.
     *
     *@param int pProfileId the profile id.
     *@throws RemoteException if an error occurs.
     */
    public ProfileView getProfileView(int pProfileId) throws RemoteException;
    
    /**
     *Retrieves questions and results for the supplied ProfileId given by the supplied BusEntityId.
     *
     *@param int pProfileId the profile id.
     *@param int pBusEntityId the business entity id.
     *@throws RemoteException if an error occurs.
     */
    public ProfileView getProfileResultsForBusEntity(int pProfileId, int pBusEntityId) 
    throws RemoteException;
    
    
    
    /**
     *Updates a profileView creating all the necessary relationships and updating the profiles themselves.
     *
     *@returns the updated ProfileView (id values set etc).
     *@throws RemoteException if an error occurs.
     */
    public ProfileView updateProfileView(ProfileView pProfileView, String pUserDoingMod,boolean pDetailDataUpdate) 
    throws RemoteException;
    
    public ProfileView updateProfileViewForStore(ProfileView pProfileView, 
        String pUserDoingMod, boolean pDetailDataUpdate, IdVector storeIds) 
            throws RemoteException;
    
    /**
     *Adds the supplied profiles BusEntity associations.
     *
     *@param pProfileId the profile id to update the associations of.
     *@param pBusEntityIds the busEntityIds to associate with the supplied profileId
     *@param pBusEntityTypeCd the busEntityType we are associating (Account, Site, etc)
     *@param pUser the user doing the update
     *@throws RemoteException if an error occurs.
     */
    public void addProfileBusEntityAssociations(int pProfileId, IdVector pBusEntityIds, String pBusEntityTypeCd, String pUser)
    throws RemoteException;
    
    /**
     *Removes the supplied profiles BusEntity associations.
     *
     *@param pProfileId the profile id to update the associations of.
     *@param pBusEntityIds the busEntityIds to associate with the supplied profileId
     *@param pBusEntityTypeCd the busEntityType we are associating (Account, Site, etc)
     *@param pUser the user doing the update
     *@throws RemoteException if an error occurs.
     */
    public void removeProfileBusEntityAssociations(int pProfileId, IdVector pBusEntityIds, String pBusEntityTypeCd, String pUser)
    throws RemoteException;
    
    /**
     *Updates the profile results.
     *
     *@throws RemoteException if an error occurs.
     */
    public void updateProfileQuestionResults() throws RemoteException;
    
    /**
     * Check correct profile's relations(associations) between profile and
     * store(s), store(s) and account(s).
     * 
     * @param pProfileId
     *            Profile ID.
     * @param pStoreIds
     *            Store IDs.
     * @param pAccountIds
     *            Account IDs.
     * @throws RemoteException
     *             if an error occurs.
     */
    public void checkProfileRelations(int pProfileId, IdVector pStoreIds,
            IdVector pAccountIds) throws RemoteException;
}
