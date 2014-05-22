package com.cleanwise.service.api.util;

/**
 * This is a utility class intended to represent a search or query
 * request.
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

import com.cleanwise.service.api.value.IdVector;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.rmi.*;
import java.io.Serializable;

/**
 * <code>QueryRequest</code> is a utility class used to represent a search
 * or query request.
 *
 * @author <a href="mailto:tbesser@TBESSER"></a>
 */
public class QueryRequest implements Serializable
{
    private int resultLimit; //after some changes does not work for sites
    private Integer maxRows; // alternative of resultLimit.
    private ArrayList filterBy;
    private ArrayList orderBy;

    /**
     *  Size of returned result set is unlimited
     */
    public final static int UNLIMITED = 0;
    /**
     *  Filter using a pattern that must exactly match the string value
     */
    public final static int EXACT = 1;
    /**
     *  Filter using a pattern that must begin with the string value
     */
    public final static int BEGINS = 2;
    /**
     *  Filter using a pattern that must contain the string value
     */
    public final static int CONTAINS = 3;
    /**
     *  Filter using a pattern that must exactly match, including case, the
     *  string value
     */
    public final static int EXACT_IGNORE_CASE = 4;
    /**
     *  Filter using a pattern that must begin, including case, the
     *  string value
     */
    public final static int BEGINS_IGNORE_CASE = 5;
    /**
     *  Filter using a pattern that must contain, including case, the
     *  string value
     */
    public final static int CONTAINS_IGNORE_CASE = 6;
    /**
     * Use ascending order
     */
    final static int ASCENDING = 7;
    /**
     * Use ascending order
     */
    final static int DESCENDING = 8;
    final static int ID_INLIST = 9;
    final static int STRING_INLIST = 10;

    /**
     * Creates a new <code>QueryRequest</code> instance.
     *
     */
    public QueryRequest() {
	filterBy = new ArrayList();
	orderBy = new ArrayList();
	resultLimit = UNLIMITED;
    }

    /**
     * <code>setResultLimit</code> sets the maximum number of items (i.e.
     * rows) that a query/search may return.  Default is UNLIMITED
     *
     * @param limit <code>int</code> value of the max number
     */
    public void setResultLimit(int limit) {
	resultLimit = limit;
    }

    /**
     * <code>getResultLimit</code> gets the maximum number of items to
     * return in a query/search.
     *
     * @return an <code>int</code> value
     */
    public int getResultLimit() {
	return resultLimit;
    }

    /**
     * <code>filterBySiteName</code> sets a site name to filter the search
     *
     * @param name a <code>String</code> value with the site name
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterBySiteName(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.SITE_NAME,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByOnlySiteName</code> sets a site name to filter the search
     *
     * @param name a <code>String</code> value with the site name
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByOnlySiteName(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.ONLY_SITE_NAME,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByRefNum</code> sets a site name to filter the search
     *
     * @param name a <code>String</code> value with the site name
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByRefNum(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.REFERENCE_NUM,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterBySiteId</code> sets a site id to filter the search
     *
     * @param id an <code>int</code> value with the site id
     */
    public void filterBySiteId(int id) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.SITE_ID, id);
	filterBy.add(qc);
    }

    public void filterBySiteIdList(IdVector pSiteIds) {
	QueryCriteria qc = new QueryCriteria
        (QueryCriteria.SITE_ID_LIST, IdVector.toCommaString(pSiteIds));
	filterBy.add(qc);
    }

    /**
     * <code>filterByUserId</code> sets a user id to filter the search
     *
     * @param id an <code>int</code> value with the user id
     */
    public void filterByUserId(int id) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.USER_ID, id);
	filterBy.add(qc);
    }
    
    public void filterByWorkflowId(int id) {
    	QueryCriteria qc = new QueryCriteria(QueryCriteria.WORKFLOW_ID, id);
    	filterBy.add(qc);
    }

    /**
     * <code>filterBySiteStatusCd</code> sets a user id to filter the search
     *
     * @param id an <code>int</code> value with the user id
     */
    public void filterBySiteStatusCd(String v) {
	QueryCriteria qc = new QueryCriteria
	    (QueryCriteria.SITE_STATUS_CD, v,
	     QueryRequest.EXACT);
	filterBy.add(qc);
    }

    /**
     * <code>filterBySiteStatusCdList</code> sets a list of site status codes to filter the search
     *
     * @param v an <code>List</code> list of requested statuses
     */
    public void filterBySiteStatusCdList(List v) {
	QueryCriteria qc = new QueryCriteria
	    (QueryCriteria.SITE_STATUS_CD, v,
	     QueryRequest.STRING_INLIST);
	filterBy.add(qc);
    }

    /**
     * <code>filterByCity</code> sets a city to filter the search
     *
     * @param name a <code>String</code> value with the city
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByCity(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.CITY,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByCounty</code> sets a county to filter the search
     *
     * @param name a <code>String</code> value with the county
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByCounty(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.COUNTY,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByState</code> sets a state name to filter the search
     *
     * @param name a <code>String</code> value with the state name
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByState(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.STATE,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByCountry</code> sets a state name to filter the search
     *
     * @param name a <code>String</code> value with the country name
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByCountry(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.COUNTRY,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByZip</code> sets a zip to filter the search
     *
     * @param name a <code>String</code> value with the zip code
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByZip(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.ZIP,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByAccountName</code> sets an account name to filter the
     * search
     *
     * @param name a <code>String</code> value with the account name
     * @param flag an <code>int</code> value describing the filtering
     * This is one of EXACT, BEGINS, CONTAINS, EXACT_IGNORE_CASE,
     * BEGINS_IGNORE_CASE, CONTAINS_IGNORE_CASE
     */
    public void filterByAccountName(String name, int flag) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.ACCOUNT_NAME,
					     name,
					     flag);
	filterBy.add(qc);
    }

    /**
     * <code>filterByAccountId</code> sets an account id to filter the search
     *
     * @param id an <code>int</code> value with the account id
     */
    public void filterByAccountId(int id) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.ACCOUNT_ID, id);
	filterBy.add(qc);
    }

    public void filterByAccountIds(IdVector ids) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.ACCOUNT_ID, ids);
	filterBy.add(qc);
    }

    public void filterByStoreIds(IdVector ids) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.STORE_ID, ids);
	filterBy.add(qc);
    }

    public void filterByCatalogId(int pCatalogId) {
	QueryCriteria qc = new QueryCriteria
        (QueryCriteria.CATALOG_ID, pCatalogId);
	filterBy.add(qc);
    }
    /**
     * <code>orderBySiteName</code> mandates that query results should be
     * ordered by site name.
     *
     * @param asc a <code>boolean</code> value, if true then ascending order,
     * false then descending
     */
    public void orderBySiteName(boolean asc) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.SITE_NAME, asc);
	orderBy.add(qc);
    }

    /**
     * <code>orderBySiteId</code> mandates that query results should be
     * ordered by site id.
     *
     * @param asc a <code>boolean</code> value, if true then ascending order,
     * false then descending
     */
    public void orderBySiteId(boolean asc) {
	QueryCriteria qc = new QueryCriteria(QueryCriteria.SITE_ID, asc);
	orderBy.add(qc);
    }

    /**
     * <code>getFilters</code> returns a list of QueryCriteria with the
     * query request filters.
     *
     * @return an <code>ArrayList</code> value with QueryCriteria objects
     * representing the filters to be applied
     */
    public ArrayList getFilters() {
	return filterBy;
    }

    /**
     * <code>getOrderBy</code> returns a list of QueryCriteria with the
     * query request ordering instructions.
     *
     * @return an <code>ArrayList</code> value with QueryCriteria objects
     * representing the ordering to be applied
     */
    public ArrayList getOrderBy() {
	return orderBy;
    }

    public void setMaxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }

    public Integer getMaxRows() {
        return maxRows;
    }
}
