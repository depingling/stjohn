/*
 * BusEntitySearchCriteria.java
 *
 * Created on December 5, 2003, 3:50 PM
 */

package com.cleanwise.service.api.value;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  bstevens
 */
public class BusEntitySearchCriteria extends EntitySearchCriteria{
    private Map miscPropertyCrit = new HashMap();
    
    /** Holds value of property searchGroupId. */
    private String searchGroupId;

    /** Holds value of property searchTerritoryCity. */
    private String searchTerritoryCity;

    /** Holds value of property searchTerritoryState. */
    private String searchTerritoryState;

    /** Holds value of property searchTerritoryCounty. */
    private String searchTerritoryCounty;

    /** Holds value of property searchTerritoryPostalCode. */
    private String searchTerritoryPostalCode;

    /**
     * Holds value of property parentBusEntityIds.
     */
    private IdVector parentBusEntityIds;

    /**
     * Holds value of property distributorBusEntityIds.
     */
    private IdVector distributorBusEntityIds;
    
    /**
     * Holds value of property serviceProviderBusEntityIds.
     */
    private IdVector serviceProviderBusEntityIds;
    
    /**
     * Holds value of property accountBusEntityIds.
     */
    private IdVector accountBusEntityIds;

    /** Creates a new instance of BusEntitySearchCriteria */
    public BusEntitySearchCriteria() {
    }

    /** Getter for property groupId.
     * @return Value of property groupId.
     *
     */
    public String getSearchGroupId() {
        return this.searchGroupId;
    }

    /** Setter for property groupId.
     * @param groupId New value of property groupId.
     *
     */
    public void setSearchGroupId(String searchGroupId) {
        this.searchGroupId = searchGroupId;
    }

    public String getSearchTerritoryCity() {
        return this.searchTerritoryCity;
    }

    /** Getter for property searchState.
     * @return Value of property searchState.
     *
     */
    public String getSearchTerritoryState() {
        return this.searchTerritoryState;
    }

    /** Setter for property searchState.
     * @param searchState New value of property searchState.
     *
     */
    public void setSearchTerritoryState(String searchTerritoryState) {
        this.searchTerritoryState = searchTerritoryState;
    }
    public void setSearchTerritoryCity(String searchTerritoryCity) {
        this.searchTerritoryCity = searchTerritoryCity;
    }

    /** Getter for property searchCounty.
     * @return Value of property searchCounty.
     *
     */
    public String getSearchTerritoryCounty() {
        return this.searchTerritoryCounty;
    }

    /** Setter for property searchCounty.
     * @param searchCounty New value of property searchCounty.
     *
     */
    public void setSearchTerritoryCounty(String searchTerritoryCounty) {
        this.searchTerritoryCounty = searchTerritoryCounty;
    }

    /** Getter for property searchPostalCode.
     * @return Value of property searchPostalCode.
     *
     */
    public String getSearchTerritoryPostalCode() {
        return this.searchTerritoryPostalCode;
    }

    /** Setter for property searchPostalCode.
     * @param searchPostalCode New value of property searchPostalCode.
     *
     */
    public void setSearchTerritoryPostalCode(String searchTerritoryPostalCode) {
        this.searchTerritoryPostalCode = searchTerritoryPostalCode;
    }

    /**
     * Getter for property parentBusEntityIds.
     * @return Value of property parentBusEntityIds.
     */
    public IdVector getParentBusEntityIds() {

        return this.parentBusEntityIds;
    }

    /**
     * Setter for property parentBusEntityIds.
     * @param parentBusEntityIds New value of property parentBusEntityIds.
     */
    public void setParentBusEntityIds(IdVector parentBusEntityIds) {

        this.parentBusEntityIds = parentBusEntityIds;
    }

    /**
     * Getter for property distributorBusEntityIds.
     * @return Value of property distributorBusEntityIds.
     */
    public IdVector getDistributorBusEntityIds() {

        return this.distributorBusEntityIds;
    }

    /**
     * Setter for property distributorBusEntityIds.
     * @param distributorBusEntityIds New value of property distributorBusEntityIds.
     */
    public void setDistributorBusEntityIds(IdVector distributorBusEntityIds) {

        this.distributorBusEntityIds = distributorBusEntityIds;
    }
    
    /**
     * Getter for property serviceProviderBusEntityIds.
     * @return Value of property serviceProviderBusEntityIds.
     */
    public IdVector getServiceProviderBusEntityIds() {

        return this.serviceProviderBusEntityIds;
    }

    /**
     * Setter for property serviceProviderBusEntityIds.
     * @param distributorBusEntityIds New value of property serviceProviderBusEntityIds.
     */
    public void setServiceProviderBusEntityIds(IdVector serviceProviderBusEntityIds) {

        this.serviceProviderBusEntityIds = serviceProviderBusEntityIds;
    }
    
    /**
     * Getter for property accountBusEntityIds.
     * @return Value of property accountBusEntityIds.
     */
    public IdVector getAccountBusEntityIds() {

        return this.accountBusEntityIds;
    }

    /**
     * Setter for property accountBusEntityIds.
     * @param accountBusEntityIds New value of property accountBusEntityIds.
     */
    public void setAccountBusEntityIds(IdVector accountBusEntityIds) {

        this.accountBusEntityIds = accountBusEntityIds;
    }
    
     /**
     *Adds a property search criteria that will restrict the results to bus entities with this property 
     *associated directly with them.
     */
    public void addPropertyCriteria(String prop, String value){
        miscPropertyCrit.put(prop,value);
    }
    
    public Map getPropertyCriteria(){
        return this.miscPropertyCrit;
    }

    
    /**
     *Adds a user id to the search criteria object.  Repeated calls with the same parameter will only add the
     *first user id.
     */
    public void addUserId(int userId){
        if(userIds == null){
            userIds=new IdVector();
        }
        Integer id = new Integer(userId);
        if(!userIds.contains(id)){
            userIds.add(id);
        }
    }
    
    /**
     * Holds value of property userIds.
     */
    private IdVector userIds;

    /**
     * Getter for property userIds.
     * @return Value of property userIds.
     */
    public IdVector getUserIds() {

        return this.userIds;
    }

    /**
     * Setter for property userIds.
     * @param userIds New value of property userIds.
     */
    public void setUserIds(IdVector userIds) {

        this.userIds = userIds;
    }
    

    private boolean mSearchForInactive = false;
    public boolean getSearchForInactive() {
	return mSearchForInactive;
    }

    public void setSearchForInactive( boolean pFlag) {
	mSearchForInactive = pFlag;
    }
}
