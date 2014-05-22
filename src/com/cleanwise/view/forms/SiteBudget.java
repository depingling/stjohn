package com.cleanwise.view.forms;

/**
 * Title:        SiteBudget
 * Description:  Bean for combined site and budget information
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import com.cleanwise.service.api.wrapper.SiteBudgetWrapper;

import java.math.BigDecimal;

public class SiteBudget extends SiteBudgetWrapper {

    private String id;
    private String name;
    private String city;
    private String state;

    private BigDecimal mSiteBudgetRemaining;
    private BigDecimal mRemainingBudgetForSite;
    
    

    private int pCurrentBudgetPeriod;    
    public void setCurrentBudgetPeriod(int pCurrentBudgetPeriod) {
        this.pCurrentBudgetPeriod = pCurrentBudgetPeriod;
    }
    public int getCurrentBudgetPeriod() {
        return pCurrentBudgetPeriod;
    }

    private int pCurrentBudgetYear;    
    public void setCurrentBudgetYear(int pCurrentBudgetYear) {
        this.pCurrentBudgetYear = pCurrentBudgetYear;
    }
    public int getCurrentBudgetYear() {
        return pCurrentBudgetYear;
    }

    public SiteBudget() {}

    
    /**
     * Get the value of SiteBudgetRemaining.
     * @return value of SiteBudgetRemaining.
     */
    public BigDecimal getSiteBudgetRemaining() {
	if ( null == mSiteBudgetRemaining ) {
	    mSiteBudgetRemaining = new BigDecimal(0);
	}
	return mSiteBudgetRemaining;
    }
    
    /**
     * Get the site budget remaining value as it is.
     * @return value of SiteBudgetRemaining.
     */
    public BigDecimal getRemainingBudgetForSite() {
		return mRemainingBudgetForSite;
	}
    
    /**
     * Set the value of SiteBudgetRemaining.
     * @param v  Value to assign to SiteBudgetRemaining.
     */
    public void setSiteBudgetRemaining(BigDecimal  v) {
    	this.mSiteBudgetRemaining = v;
    	this.mRemainingBudgetForSite = v;
    }
    
    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public String getId() {
	return this.id;
    }

    /**
     * Sets the value of id
     *
     * @param pId Value to assign to this.siteId
     */
    public void setId(String pId){
	this.id = pId;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName() {
	return this.name;
    }

    /**
     * Sets the value of name
     *
     * @param pName Value to assign to this.siteName
     */
    public void setName(String pName){
	this.name = pName;
    }

    /**
     * Gets the value of city
     *
     * @return the value of city
     */
    public String getCity() {
	return this.city;
    }

    /**
     * Sets the value of city
     *
     * @param pCity Value to assign to this.siteCity
     */
    public void setCity(String pCity){
	this.city = pCity;
    }

    /**
     * Gets the value of state
     *
     * @return the value of state
     */
    public String getState() {
	return this.state;
    }

    /**
     * Sets the value of state
     *
     * @param pState Value to assign to this.siteState
     */
    public void setState(String pState){
	this.state = pState;
    }

    
    
}
