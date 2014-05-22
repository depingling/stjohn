/*
 * CustomerRequestPoNumber.java
 *
 * Created on February 10, 2005, 11:05 AM
 */

package com.cleanwise.service.api.session;

import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import com.cleanwise.service.api.value.BlanketPoNumDescData;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import java.rmi.RemoteException;

/**
 *
 * @author bstevens
 * method addUpdateBusEntityAssociations() added by Sergei Cher 12/2/2008
 * 
 */
public interface CustomerRequestPoNumber extends javax.ejb.EJBObject{
    
    /**
     *Searches the existing blanket request po object with the supplied criteria
     *@param SearchCriteria the search criteria to use
     *@returns BlanketPoNumDataVector populated with the results, or empty if nothing
     *  was found (not null).
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector search(EntitySearchCriteria crit) throws RemoteException;

    /**
     *Searches the existing blanket request po object with the supplied criteria and supplied filter
     *@param SearchCriteria the search criteria to use
     *@returns BlanketPoNumDataVector populated with the results, or empty if nothing
     *  was found (not null).
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector searchWithFilters(EntitySearchCriteria crit, boolean showInactiveFl) throws RemoteException;
    
    /**
     *Returns all the BlanketPoNumData records
     *@returns BlanketPoNumDataVector populated with the results, or empty if nothing
     *  was found (not null).
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector viewAll()throws RemoteException;
    
    /**
     *Returns a fully populated BlanketPoNumDescData object that is identified by 
     *the supplied id.
     *@param int the id of the BlanketPoNum
     *@returns a populated BlanketPoNumDescData object
     *@throws RemoteExcetpion if an error occurs
     *@throws DataNotFoundException if the supplied id does not identify a BlanketPoNum
     */
    public BlanketPoNumDescData getDetail(int BlanketPoNumId)throws RemoteException,DataNotFoundException;
    
    
    /**
     *Adds or updates the BlanketPoNumDescData as necessary.  Updates all of the aux
     *data that is contained in this object.  Returns the BlanketPoNumDescData with 
     *any values that were modified.
     *@param the BlanketPoNumDescData to be added/updated
     *@param String the user doing the action
     *@returns the BlanketPoNumDescData that may or may not have been modified
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDescData addUpdate(BlanketPoNumDescData pDetail, String pUserName)throws RemoteException;
    
    
    
    /**
     *Searches the account associations
     *@param the populated BusEntitySearchCriteria to use in the search
     *@param the pBlanketPoNumId to have associations linked to
     *@returns a populated BusEntityDataVector, or an empty one if nothing was found. not null.
     *@throws RemoteExcetpion if an error occurs
     */
    public BusEntityDataVector searchAccountAssociations(BusEntitySearchCriteria pCrit, int pBlanketPoNumId) throws RemoteException;
    
    /**
     *Searches the site associations
     *@param the populated BusEntitySearchCriteria to use in the search
     *@param the pBlanketPoNumId to have associations linked to
     *@returns a populated BusEntityDataVector, or an empty one if nothing was found. not null.
     *@throws RemoteExcetpion if an error occurs
     */
    public BusEntityDataVector searchSiteAssociations(BusEntitySearchCriteria pCrit, int pBlanketPoNumId) throws RemoteException;
    
    /**
     *Searches the store associations
     *@param the populated BusEntitySearchCriteria to use in the search
     *@param the pBlanketPoNumId to have associations linked to
     *@returns a populated BusEntityDataVector, or an empty one if nothing was found. not null.
     *@throws RemoteExcetpion if an error occurs
     */
    public BusEntityDataVector searchStoreAssociations(BusEntitySearchCriteria pCrit, int pBlanketPoNumId) throws RemoteException;
    
    /**
     *Adds/updates associations between the supplied pBlanketPoNumId and the supplied pBusEntityIds
     *@param the pBlanketPoNumId to associate the bus entity ids to
     *@param the pBusEntityIds to associate to the pBlanketPoNumId
     *@throws RemoteExcetpion if an error occurs
     */
    public void addStoreBusEntityAssociations(int pBlanketPoNumId, IdVector pBusEntityIds, String pUserDoingMod)throws RemoteException;
    
    /**
     *Adds associations between the supplied pBlanketPoNumId and the supplied pBusEntityIds
     *@param the pBlanketPoNumId to associate the bus entity ids to
     *@param the pBusEntityIds to associate to the pBlanketPoNumId
     *@throws RemoteExcetpion if an error occurs
     */
    public void addBusEntityAssociations(int pBlanketPoNumId, IdVector pBusEntityIds, String pUserDoingMod)throws RemoteException;
    /**
     *removes associations between the supplied pBlanketPoNumId and the supplied pBusEntityIds
     *@param the pBlanketPoNumId to de-associate the bus entity ids to
     *@param the pBusEntityIds to de-associate to the pBlanketPoNumId
     *@throws RemoteExcetpion if an error occurs
     */
    public void removeBusEntityAssociations(int pBlanketPoNumId, IdVector pBusEntityIds)throws RemoteException;
    
    /**
     *Retrieves all the blanket po number for the supplied bus entity.
     *@param the business entity id to find direct associations to
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector getBlanketPosForBusEntity(int pBusEntityId) throws RemoteException;
}
