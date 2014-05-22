package com.cleanwise.service.api.util;

/**
 * This is a utility class for
 *
 * Copyright:   Copyright (c) 2001
 * Company:     CleanWise, Inc.
 * @author      Tim Besser
 *
 */

import java.rmi.*;
import java.io.Serializable;
import com.cleanwise.service.api.value.IdVector;
import java.util.List;
import java.util.Iterator;

/**
 * <code>QueryCriteria</code> is a utility class used
 *
 * @author <a href="mailto:tbesser@TBESSER"></a>
 */
public class QueryCriteria implements Serializable
{
    private int field;
    private String value;
    private int flag;
    private IdVector idvector;
	  private List strings;

    /**
     * <code>SITE_NAME</code> is constant that criteria applies to a site name
     */
    public final static int SITE_NAME = 1;
    /**
     * <code>SITE_ID</code> is constant that criteria applies to a site id
     */
    public final static int SITE_ID = 2;
    /**
     * <code>ACCOUNT_NAME</code> is constant that criteria applies to an
     * account name
     */
    public final static int ACCOUNT_NAME = 3;
    /**
     * <code>ACCOUNT_ID</code> is constant that criteria applies to an
     * account id
     */
    public final static int ACCOUNT_ID = 4;
    /**
     * <code>CITY</code> is constant that criteria applies to a city
     */
    public final static int CITY = 5;
    /**
     * <code>STATE</code> is constant that criteria applies to a state
     */
    public final static int STATE = 6;
    /**
     * <code>ZIP</code> is constant that criteria applies to a zip code
     */
    public final static int ZIP = 7;

    /**
     * <code>USER_ID</code> is constant that criteria applies to a user id
     */
    public final static int USER_ID = 8;

    /**
     * <code>SITE_STATUS_CD</code> is constant that criteria applies
     * to a site status value
     */
    public final static int SITE_STATUS_CD = 9;
    /**
     * <code>COUNTY</code> is constant that criteria applies to a county
     */
    public final static int COUNTY = 10;

    public final static int COUNTRY = 11;
    public final static int SITE_ID_LIST = 20;

    public final static int STORE_ID = 23;
    public final static int CATALOG_ID = 24;

    /**
     * <code>ONLY_SITE_NAME</code> is constant that criteria applies to a site name
     */
    public final static int ONLY_SITE_NAME = 25;

    /**
     * <code>REFERENCE_NUM</code> is constant that criteria applies to a site name
     */
    public final static int REFERENCE_NUM = 26;
    
    public final static int WORKFLOW_ID = 27;

    /**
     * Creates a new <code>QueryCriteria</code> instance that represents
     * a filter pattern. Access has been deliberately left at 'default'
     * so as to discourage use by any class other than QueryCriteria
     *
     * @param field an <code>int</code> value that is one of the
     * QueryCriteria constants such as SITE_NAME, ACCOUNT_NAME, CITY, ...
     * @param value a <code>String</code> value with the pattern
     * @param flag an <code>int</code> value that is one of the QueryCriteria
     * flags such as EXACT, BEGINS, CONTAINS, ...
     */
    QueryCriteria(int field, String value, int flag) {
	this.field = field;
	this.value = value;
	this.flag = flag;
    }

    /**
     * Creates a new <code>QueryCriteria</code> instance that represents
     * a filter by ID.  Access has been deliberately left at 'default'
     * so as to discourage use by any class other than QueryCriteria
     *
     * @param field an <code>int</code> value that is one of the
     * QueryCriteria constants such as SITE_ID, ACCOUNT_ID, ...
     * @param id an <code>int</code> value that would be the site id or
     * account id, etc.
     */
    QueryCriteria(int field, int id) {
	this.field = field;
	this.value = String.valueOf(id);
	this.flag = QueryRequest.EXACT;
    }

    QueryCriteria(int field, IdVector ids) {
	this.field = field;
	this.value = null;
    this.idvector = ids;
    this.skipVarSubstitution = true;
	this.flag = QueryRequest.ID_INLIST;
    }

    QueryCriteria(int field, List values, int queryType) {
	this.field = field;
	this.value = null;
    this.strings = values;
    this.skipVarSubstitution = true;
	this.flag = queryType;
    }

    private boolean skipVarSubstitution = false;
    public boolean skipSubstitution() {
        return this.skipVarSubstitution;
    }

    QueryCriteria(int field, String pValueString) {
	this.field = field;
	this.value = pValueString;
	this.flag = QueryRequest.EXACT;
    this.skipVarSubstitution = true;
    }

    /**
     * Creates a new <code>QueryCriteria</code> instance representing an
     * order by request.  Access has been deliberately left at 'default'
     * so as to discourage use by any class other than QueryCriteria
     *
     * @param field an <code>int</code> value that is one of the
     * QueryCriteria constants such as SITE_NAME, ACCOUNT_NAME, CITY, ...
     * @param ascending a <code>boolean</code> value, true if ascending,
     * false if descending
     */
    QueryCriteria(int field, boolean ascending) {
	this.field = field;
	if (ascending) {
	    this.flag = QueryRequest.ASCENDING;
	} else {
	    this.flag = QueryRequest.DESCENDING;
	}
    }

    /**
     * <code>getFilterByType</code> returns the field name/type.  This is
     * one of the QueryCriteria constants such as SITE_NAME, SITE_ID,
     * CITY, STATE, ...
     *
     * @return an <code>int</code> value with QueryCriteria constant
     */
    public int getFilterByType() {
	return field;
    }

    /**
     * <code>getFilterByPhrase</code> generates the SQL phrase corresponding
     * to the filter.  e.g. 'B1.SHORT_DESC like 'JWP%'
     *
     * @param s a <code>String</code> with the db column name to be substituted
     * @return a <code>String</code> with the phrase
     */
    public String getFilterByPhrase(String s) {
	StringBuffer sb = new StringBuffer();

	switch (flag) {
	case QueryRequest.EXACT:
	    sb.append(s);
	    sb.append(" = ");
	    sb.append(DBAccess.toQuoted(value));
	    break;
	case QueryRequest.BEGINS:
	    sb.append(s);
	    sb.append(" LIKE '");
	    sb.append(DBAccess.escapeQuotes(value));
	    sb.append("%'");
	    break;
	case QueryRequest.CONTAINS:
	    sb.append(s);
	    sb.append(" LIKE '%");
	    sb.append(DBAccess.escapeQuotes(value));
	    sb.append("%'");
	    break;
	case QueryRequest.EXACT_IGNORE_CASE:
	    sb.append(" UPPER(");
	    sb.append(s);
	    sb.append(") = '");
	    sb.append(DBAccess.escapeQuotes(value.toUpperCase()));
	    sb.append("'");
	    break;
	case QueryRequest.BEGINS_IGNORE_CASE:
	    sb.append(" UPPER(");
	    sb.append(s);
	    sb.append(") LIKE '");
	    sb.append(DBAccess.escapeQuotes(value.toUpperCase()));
	    sb.append("%'");
	    break;
	case QueryRequest.CONTAINS_IGNORE_CASE:
	    sb.append(" UPPER(");
	    sb.append(s);
	    sb.append(") LIKE '%");
	    sb.append(DBAccess.escapeQuotes(value.toUpperCase()));
	    sb.append("%'");
	    break;
	}

	return sb.toString();
    }

    /**
     * <code>getFilterByDynamicPhrase</code> generates the dynamic SQL phrase
     * corresponding to the filter.  e.g. 'B1.SHORT_DESC like ?'. It is
     * intended for use with PreparedStatements that want to bind values
     *
     * @param s a <code>String</code> with the db column name to be substituted
     * @return a <code>String</code> with the phrase
     */
    public String getFilterByDynamicPhrase(String s) {
	StringBuffer sb = new StringBuffer();

	switch (flag) {
	case QueryRequest.EXACT:
	    sb.append(s);
	    sb.append(" = ");
	    sb.append("?");
	    break;
	case QueryRequest.BEGINS:
	    sb.append(s);
	    sb.append(" LIKE ?");
	    break;
	case QueryRequest.CONTAINS:
	    sb.append(s);
	    sb.append(" LIKE ?");
	    break;
	case QueryRequest.EXACT_IGNORE_CASE:
	    sb.append(" UPPER(");
	    sb.append(s);
	    sb.append(") = ?");
	    break;
	case QueryRequest.BEGINS_IGNORE_CASE:
	    sb.append(" UPPER(");
	    sb.append(s);
	    sb.append(") LIKE ?");
	    break;
	case QueryRequest.CONTAINS_IGNORE_CASE:
	    sb.append(" UPPER(");
	    sb.append(s);
	    sb.append(") LIKE ?");
	    break;
	case QueryRequest.ID_INLIST:
	    sb.append(s);
	    sb.append(" in (" );
        sb.append(idvector.toCommaString(idvector));
	    sb.append(" ) " );
	    break;
	case QueryRequest.STRING_INLIST:
      if(strings==null || strings.size()==0) {
  	    sb.append(" 1 = 2 " );
      } else {
  	    sb.append(s);
        boolean firstFl = true;
	      sb.append(" in (" );
        for(Iterator iter=strings.iterator(); iter.hasNext();) {
          String str = (String) iter.next();
          if(str==null) str = "";
          if(str.indexOf("'")>=0) str = str.replaceAll("'","''");
          if(firstFl) {
            sb.append("'"+str+"'");
            firstFl = false;
          } else {
            sb.append(",'"+str+"'");
          }
        }
      }
	    sb.append(" ) " );
	    break;
	}

	return sb.toString();
    }

    /**
     * <code>getFilterByValue</code> returns the value for a filter that
     * is intended to be used for the dynamic binding
     *
     * @return a <code>String</code> value
     */
    public String getFilterByValue() {
	StringBuffer sb = new StringBuffer();
	switch (flag) {
	case QueryRequest.EXACT:
	    sb.append(value);
	    break;
	case QueryRequest.EXACT_IGNORE_CASE:
	    sb.append(value.toUpperCase());
	    break;
	case QueryRequest.BEGINS:
	    sb.append(value);
	    sb.append("%");
	    break;
	case QueryRequest.BEGINS_IGNORE_CASE:
	    sb.append(value.toUpperCase());
	    sb.append("%");
	    break;
	case QueryRequest.CONTAINS:
	    sb.append("%");
	    sb.append(value);
	    sb.append("%");
	    break;
	case QueryRequest.CONTAINS_IGNORE_CASE:
	    sb.append("%");
	    sb.append(value.toUpperCase());
	    sb.append("%");
	    break;
	}

	return sb.toString();
    }

    /**
     * Describe <code>getOrderByPhrase</code> method here.
     *
     * @param s a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String getOrderByPhrase(String s) {
	StringBuffer sb = new StringBuffer();

	switch (flag) {
	case QueryRequest.ASCENDING:
	    sb.append(s);
	    sb.append(" ASC");
	    break;
	case QueryRequest.DESCENDING:
	    sb.append(s);
	    sb.append(" DESC");
	    break;
	}

	return sb.toString();
    }

    /**
     * Describe <code>toString</code> method here.
     *
     * @return a <code>String</code> value
     */
    public String toString() {
	StringBuffer sb = new StringBuffer("QueryCriteria: ");
	sb.append(field);
	sb.append(",");
	sb.append(value);
	return sb.toString();
    }
}






