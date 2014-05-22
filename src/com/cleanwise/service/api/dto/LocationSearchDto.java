/**
 * Title: LocationSearchDto 
 * Description: This is a data transfer object holding location search criteria and results.
 */

package com.cleanwise.service.api.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.cleanwise.service.api.value.SiteDataVector;

public class LocationSearchDto implements Serializable {
	
	private static final long serialVersionUID = 4987193648587235473L;
	private String _userId;
	private String _keyword;
	private String _city;
	private String _state;
	private String _postalCode;
	private SiteDataVector _matchingLocations;
	private String _mostRecentlyVisitedSiteId;
	private String _sortField;
	private String _sortOrder;
	private boolean _searchInactive;
	
    /**
     * Create a completely empty new LocationSearchDto
     */
    public LocationSearchDto() {
    	super();
    	setUserId(StringUtils.EMPTY);
    	setKeyword(StringUtils.EMPTY);
    	setState(StringUtils.EMPTY);
    	setCity(StringUtils.EMPTY);
    	setPostalCode(StringUtils.EMPTY);
    	setMatchingLocations(null);
    	setMostRecentlyVisitedSiteId(StringUtils.EMPTY);
    	setSortField(StringUtils.EMPTY);
    	setSortOrder(StringUtils.EMPTY);
    }
    
    /**
     * Create a completely new LocationSearchDto initialized with a user id.
     * @param  userId  a <code>String</code> containing the user id of the user for whom locations
     * 	are being searched.
     */
    public LocationSearchDto(String userId) {
    	this();
    	setUserId(userId);
    }

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return _userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		_userId = userId;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return _keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return _city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		_city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return _state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		_state = state;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return _postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}

	/**
	 * @return the matching locations
	 */
	public SiteDataVector getMatchingLocations() {
		//to prevent the caller from having to check for null, return an empty vector if
		//the results are null.
		if (_matchingLocations == null) {
			_matchingLocations = new SiteDataVector();
		}
		return _matchingLocations;
	}

	/**
	 * @param matchingLocations the matchingLocations to set
	 */
	public void setMatchingLocations(SiteDataVector matchingLocations) {
		_matchingLocations = matchingLocations;
	}
	
	/**
	 * @return the mostRecentlyVisitedSiteId
	 */
	public String getMostRecentlyVisitedSiteId() {
		return _mostRecentlyVisitedSiteId;
	}
	
	/**
	 * @param mostRecentlyVisitedSiteId the mostRecentlyVisitedSiteId to set
	 */
	public void setMostRecentlyVisitedSiteId(String mostRecentlyVisitedSiteId) {
		_mostRecentlyVisitedSiteId = mostRecentlyVisitedSiteId;
	}
	
	/**
	 * @return the sortField
	 */
	public String getSortField() {
		return _sortField;
	}
	
	/**
	 * @param sortField the sortField to set
	 */
	public void setSortField(String sortField) {
		_sortField = sortField;
	}
	
	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return _sortOrder;
	}
	
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		_sortOrder = sortOrder;
	}

    public boolean isSearchInactive() {
        return _searchInactive;
    }

    public void setSearchInactive(boolean searchInactive) {
        this._searchInactive = searchInactive;
    }
}
