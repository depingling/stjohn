/*
 * SearchCriteria.java
 *
 * Created on March 30, 2005, 11:23 AM
 */

package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.Utility;
import java.util.List;

/**
 * A basic search criteria that serves as the basis for many of the more specialized search
 * criteria classes.
 * @author bstevens
 */
public class EntitySearchCriteria extends ValueObject{
    public static final int NAME_STARTS_WITH = 10004;
    public static final int NAME_CONTAINS = 10005;
    public static final int EXACT_MATCH = 10010;

    public static final int ORDER_BY_NAME = 10007;
    public static final int ORDER_BY_ID = 10006;
    
    
    /** Holds value of property searchId. */
    private String searchId;

    /** Holds value of property searchName. */
    private String searchName;

    /** Holds value of property searchNameType. */
    private int searchNameType;

    /** Holds value of property searchStatusCdList. */
    private List searchStatusCdList;

    /** Holds value of property order. */
    private int order;
    
    /**
     * Holds value of property storeBusEntityIds.
     */
    private IdVector storeBusEntityIds;

    /**
     * Holds value of property accountBusEntityIds.
     */
    private IdVector accountBusEntityIds;

    /**
     * Holds value of property searchTypeCds.
     */
    private List searchTypeCds;
    
    /**
     * returns the searchId property formated as an int
     * @throws NumberFormatException if the property is not numeric
     */
    public int getSearchIdAsInt() throws NumberFormatException{
        if(Utility.isSet(searchId)){
            return Integer.parseInt(searchId);
        }
        return 0;
    }
    
    /** Getter for property searchId.
     * @return Value of property searchId.
     *
     */
    public String getSearchId() {
        return this.searchId;
    }

    /** Setter for property searchId.
     * @param searchId New value of property searchId.
     *
     */
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
    public void setSearchId(int searchId) {
        this.searchId = String.valueOf(searchId);
    }

    /** Getter for property searchName.
     * @return Value of property searchName.
     *
     */
    public String getSearchName() {
        return this.searchName;
    }

    /** Setter for property searchName.
     * @param searchName New value of property searchName.
     *
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /** Getter for property searchNameType.
     * @return Value of property searchNameType.
     *
     */
    public int getSearchNameType() {
        return this.searchNameType;
    }

    /** Setter for property searchNameType.
     * @param searchNameType New value of property searchNameType.
     *
     */
    public void setSearchNameType(int searchNameType) {
        this.searchNameType = searchNameType;
    }

    
    
    /** Getter for property searchStatusCdList.
     * @return Value of property searchStatusCdList.
     *
     */
    public List getSearchStatusCdList() {
        return this.searchStatusCdList;
    }

    /** Setter for property searchStatusCdList.
     * @param searchStatusCdList New value of property searchStatusCdList.
     *
     */
    public void setSearchStatusCdList(List searchStatusCdList) {
        this.searchStatusCdList = searchStatusCdList;
    }

    
    /** Getter for property order.
     * @return Value of property order.
     *
     */
    public int getOrder() {
        return this.order;
    }

    /** Setter for property order.
     * @param order New value of property order.
     *
     */
    public void setOrder(int order) {
        this.order = order;
    }
    
    /**
     * Getter for property storeBusEntityIds.
     * @return Value of property storeBusEntityIds.
     */
    public IdVector getStoreBusEntityIds() {

        return this.storeBusEntityIds;
    }

    /**
     * Setter for property storeBusEntityIds.
     * @param storeBusEntityIds New value of property storeBusEntityIds.
     */
    public void setStoreBusEntityIds(IdVector storeBusEntityIds) {

        this.storeBusEntityIds = storeBusEntityIds;
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
     * Getter for property searchTypeCds.
     * @return Value of property searchTypeCds.
     */
    public List getSearchTypeCds() {

        return this.searchTypeCds;
    }

    /**
     * Setter for property searchTypeCds.
     * @param searchTypeCds New value of property searchTypeCds.
     */
    public void setSearchTypeCds(List searchTypeCds) {

        this.searchTypeCds = searchTypeCds;
    }
    
    /** Creates a new instance of SearchCriteria */
    public EntitySearchCriteria() {
    }
    
    
    public final static int UNLIMITED = 0;
    private int resultLimit= UNLIMITED;
    
	public void setResultLimit(int limit) {
		resultLimit = limit;
	}

	public int getResultLimit() {
		return resultLimit;
	}
}
