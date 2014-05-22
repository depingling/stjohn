  
package com.cleanwise.service.api.dao;

/**
 * Title:        BusEntityDAO
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       T Besser
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.cachecos.CacheKey;
import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.meta.AccountDataMeta;
import com.cleanwise.service.api.meta.SiteDataMeta;
import com.cleanwise.service.api.meta.StoreDataMeta;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.QueryCriteria;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.QueryRequestException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.ApplicationDomainNameData;
import com.cleanwise.service.api.value.BlanketPoNumData;
import com.cleanwise.service.api.value.BudgetViewVector;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityAssocDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.ContactData;
import com.cleanwise.service.api.value.ContactDataVector;
import com.cleanwise.service.api.value.ContactView;
import com.cleanwise.service.api.value.ContactViewVector;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.CountryPropertyDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderDataVector;
import com.cleanwise.service.api.value.FiscalCalenderDetailData;
import com.cleanwise.service.api.value.FiscalCalenderDetailDataVector;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.FiscalCalenderViewVector;
import com.cleanwise.service.api.value.FiscalPeriodView;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.FreightHandlerViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PhoneDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDeliveryScheduleView;
import com.cleanwise.service.api.value.SiteDeliveryScheduleViewVector;
import com.cleanwise.service.api.value.SitePriceListView;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;


/**
 * <code>BusEntityDAO</code>
 */
public class BusEntityDAO
{
    private static Category log = Category.getInstance(BusEntityDAO.class.getName());

    /**
     * Constructor.
     */
    public BusEntityDAO()
    {
    }

    /**
     * <code>getSiteCollection</code>  returns a vector of SiteView's
     * meeting the criteria of the QueryRequest.
     *
     * @param conn a <code>Connection</code> value that is open db connection
     * @param qr a <code>QueryRequest</code> filtering and ordering on any of:
     * SITE_NAME, SITE_ID, ACCOUNT_NAME, ACCOUNT_ID, CITY, STATE, USER_ID
     * @param qr a <code>QueryRequest</code> value
     * @return a <code>SiteViewVector</code> value
     * @exception SQLException if an error occurs
     */
    public static SiteViewVector getSiteCollection(Connection conn,QueryRequest qr)
        throws SQLException {
        SiteViewVector siteV = new SiteViewVector();

  // XXX - perhaps this should be using the constants defined in
  // BusEntityDataAccess for the table/column names.  Uglier, but would
  // give us compile-time protection in the event of schema changes
  StringBuffer sqlBuf = new StringBuffer("SELECT distinct B1.BUS_ENTITY_ID, B1.SHORT_DESC, B2.BUS_ENTITY_ID," +
          " B2.SHORT_DESC, A1.ADDRESS1, A1.CITY, A1.STATE_PROVINCE_CD, B1.BUS_ENTITY_STATUS_CD," +
          " A1.POSTAL_CODE, A1.COUNTY_CD, P1.CLW_VALUE" +
          " FROM " +
          "CLW_BUS_ENTITY B2, CLW_BUS_ENTITY_ASSOC B3," +
          "CLW_BUS_ENTITY_ASSOC B4, CLW_ADDRESS A1, CLW_BUS_ENTITY B1 " +
          "LEFT OUTER JOIN CLW_PROPERTY P1 ON P1.BUS_ENTITY_ID = B1.BUS_ENTITY_ID AND " +
          "P1.PROPERTY_TYPE_CD = '" + RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK + "' " +
          "LEFT OUTER JOIN CLW_PROPERTY KEYWORDS ON KEYWORDS.BUS_ENTITY_ID = B1.BUS_ENTITY_ID AND " +
          "KEYWORDS.SHORT_DESC IN ('" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "','"
          + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "') " +
//          "(SELECT * FROM CLW_PROPERTY WHERE PROPERTY_TYPE_CD = '"+RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK+"') P1, " +
//          "(SELECT * FROM CLW_PROPERTY WHERE SHORT_DESC IN ('"+RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"','"+RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER+"')) KEYWORDS " +
          "LEFT OUTER JOIN CLW_SITE_WORKFLOW sw ON B1.BUS_ENTITY_ID = sw.SITE_ID" +
          " WHERE "

    + " B1.BUS_ENTITY_TYPE_CD = 'SITE' AND B1.BUS_ENTITY_ID = A1.BUS_ENTITY_ID AND "
    + " A1.ADDRESS_TYPE_CD in ("
    + "'"+ RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING + "',"
    + "'"+ RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "'"
    + " ) AND "
    + " B3.BUS_ENTITY1_ID = B1.BUS_ENTITY_ID AND "
    + " B3.BUS_ENTITY2_ID = B4.BUS_ENTITY1_ID AND "
    + " B4.BUS_ENTITY1_ID = B2.BUS_ENTITY_ID AND "
    + " B4.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' AND "
    + " B3.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT'"// AND "
//    + " P1.BUS_ENTITY_ID (+) = B1.BUS_ENTITY_ID AND "
//    + " KEYWORDS.BUS_ENTITY_ID (+) = B1.BUS_ENTITY_ID"
    );

  // iterate thru all the filters, appending dynamic SQL phrases to
  // SQL above
  List filterBy = qr.getFilters();
  List substituions = new ArrayList();
  Iterator filterI = filterBy.iterator();
  while (filterI.hasNext()) {
      QueryCriteria qc = (QueryCriteria)filterI.next();
      if(!qc.skipSubstitution()){
        substituions.add(qc);
      }
      switch (qc.getFilterByType()) {
      case QueryCriteria.SITE_NAME:
          sqlBuf.append(" AND (");
          sqlBuf.append(qc.getFilterByDynamicPhrase("B1.SHORT_DESC"));
          sqlBuf.append(" OR ");
          sqlBuf.append("UPPER(KEYWORDS.CLW_VALUE) LIKE '"+qc.getFilterByValue().replaceAll("'", "''")+"'");
          //    sqlBuf.append("INSTR(NLS_UPPER(KEYWORDS.CLW_VALUE),'"+qc.getFilterByValue().replaceAll("%", "")+"') >0");
          sqlBuf.append(")");
          //add it to the substitutions again as we are adding it the query twice
          //substituions.add(qc);
    break;
      case QueryCriteria.ONLY_SITE_NAME:
    	  sqlBuf.append(" AND (");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("B1.SHORT_DESC"));
    	  sqlBuf.append(" OR ");
    	  sqlBuf.append("UPPER(B1.SHORT_DESC) LIKE '"+qc.getFilterByValue().replaceAll("'", "''")+"'");
    	  sqlBuf.append(")");
    	  break;
      case QueryCriteria.REFERENCE_NUM:
    	  sqlBuf.append(" AND (");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("KEYWORDS.CLW_VALUE"));
    	  sqlBuf.append(" OR ");
    	  sqlBuf.append("UPPER(KEYWORDS.CLW_VALUE) LIKE '"+qc.getFilterByValue().replaceAll("'", "''")+"'");
    	  sqlBuf.append(")");
    	  break;
      case QueryCriteria.SITE_STATUS_CD:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("B1.BUS_ENTITY_STATUS_CD"));
    	  //should filter by only site status here not account 
    	  /*if (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(qc.getFilterByValue())) {
    		  sqlBuf.append(" AND ");
    		  sqlBuf.append("'" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE +
    				  ("' = B2.BUS_ENTITY_STATUS_CD"));
    	  }*/
    	  break;
      case QueryCriteria.SITE_ID:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("B1.BUS_ENTITY_ID"));
    	  break;
      case QueryCriteria.WORKFLOW_ID:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("sw.WORKFLOW_ID"));
    	  break;
      case QueryCriteria.ACCOUNT_NAME:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("B2.SHORT_DESC"));
    	  break;
      case QueryCriteria.ACCOUNT_ID:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("B2.BUS_ENTITY_ID"));
    	  break;
      case QueryCriteria.CITY:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("A1.CITY"));
    	  break;
      case QueryCriteria.COUNTY:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("A1.COUNTY_CD"));
    	  break;
      case QueryCriteria.STATE:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("A1.STATE_PROVINCE_CD"));
    	  break;
      case QueryCriteria.ZIP:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("A1.POSTAL_CODE"));
    	  break;
      case QueryCriteria.COUNTRY:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("A1.COUNTRY_CD"));
    	  break;
      case QueryCriteria.USER_ID:
    	  sqlBuf.append(" AND ");
    	  // Get the sites for this user.
    	  String userSitesSql = " B1.BUS_ENTITY_ID in " +
    	  " ( select distinct ua.bus_entity_id from " +
    	  " clw_user_assoc ua where " +
    	  qc.getFilterByDynamicPhrase("UA.USER_ID")
    	  + " ) ";
    	  sqlBuf.append(userSitesSql);
    	  break;
      case QueryCriteria.SITE_ID_LIST:
    	  sqlBuf.append(" AND ");
    	  String sitesSql = " B1.BUS_ENTITY_ID in " +
    	  " ( " + qc.getFilterByValue() + " ) ";
    	  sqlBuf.append(sitesSql);
    	  break;
      case QueryCriteria.STORE_ID:
    	  sqlBuf.append(" AND ");
    	  sqlBuf.append(qc.getFilterByDynamicPhrase("B4.BUS_ENTITY2_ID"));
    	  break;
      case QueryCriteria.CATALOG_ID:
    	  String cond = " AND B1.BUS_ENTITY_ID in " +
    	  "(SELECT BUS_ENTITY_ID FROM CLW_CATALOG_ASSOC " +
    	  "WHERE CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+
    	  "' AND CATALOG_ID = ?) "; //+qc.getFilterByValue()+") ";
    	  sqlBuf.append(cond);
    	  break;
      default:
    	  // a filter that we're not supporting here
    	  throw new QueryRequestException(qc.toString());
      }
  }
  sqlBuf.append(" AND 1=1 ");
  // iterate thru the order bys, appending appropriate phrase to
  // SQL above
  ArrayList orderBy = qr.getOrderBy();
  boolean first = true;
  Iterator orderI = orderBy.iterator();
  while (orderI.hasNext()) {
      QueryCriteria qc = (QueryCriteria)orderI.next();
      if (first) {
    sqlBuf.append(" ORDER BY ");
    first = false;
      } else {
    sqlBuf.append(", ");
      }
      switch (qc.getFilterByType()) {
      case QueryCriteria.SITE_NAME:
    sqlBuf.append(qc.getOrderByPhrase("B1.SHORT_DESC"));
    break;
      case QueryCriteria.SITE_ID:
    sqlBuf.append(qc.getOrderByPhrase("B1.BUS_ENTITY_ID"));
    break;
      case QueryCriteria.ACCOUNT_NAME:
    sqlBuf.append(qc.getOrderByPhrase("B2.SHORT_DESC"));
    break;
      case QueryCriteria.ACCOUNT_ID:
    sqlBuf.append(qc.getOrderByPhrase("B2.BUS_ENTITY_ID"));
    break;
      case QueryCriteria.CITY:
    sqlBuf.append(qc.getOrderByPhrase("A1.CITY"));
    break;
      case QueryCriteria.COUNTY:
    sqlBuf.append(qc.getOrderByPhrase("A1.COUNTY_CD"));
    break;
      case QueryCriteria.STATE:
    sqlBuf.append(qc.getOrderByPhrase("A1.STATE_PROVINCE_CD"));
    break;
      case QueryCriteria.ZIP:
    sqlBuf.append(qc.getOrderByPhrase("A1.POSTAL_CODE"));
    break;
      default:
    // an order by that we're not supporting here
    throw new QueryRequestException(qc.toString());
      }
  }
log.info(sqlBuf.toString());
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sqlBuf.toString());
        }
  // showtime, prepare and execute the query
  PreparedStatement stmt = conn.prepareStatement(sqlBuf.toString());
  Iterator substituionsI = substituions.iterator();
  int i = 1;
  while (substituionsI.hasNext()) {
      QueryCriteria qc = (QueryCriteria)substituionsI.next();
            if ( ! qc.skipSubstitution() ) {
                stmt.setString(i, qc.getFilterByValue());
    log.info("BusEntityDAO, getSiteCollection i="
           + i + " qc value=" +
           qc.getFilterByValue());
                i++;
            }

  }
  ResultSet rs=stmt.executeQuery();

    if (qr.getMaxRows() == null) {
        if (qr.getResultLimit() != QueryRequest.UNLIMITED) {
            stmt.setMaxRows(qr.getResultLimit());
            stmt.setMaxRows(2000);
        }
    } else {  // maximum number of results may be limited
        if (qr.getMaxRows() != QueryRequest.UNLIMITED) {
            stmt.setMaxRows(qr.getMaxRows());
        }
    }

  HashSet foundIdsAlready = new HashSet();

  while (rs.next()) {
      int siteId = rs.getInt(1);
      Integer siteKey = new Integer(siteId);
      if(!foundIdsAlready.contains(siteKey)){
          foundIdsAlready.add(siteKey);
            // build the object
          SiteView site = SiteView.createValue();
            site.setId(siteId);
            site.setName(rs.getString(2));
            site.setAccountId(rs.getInt(3));
            site.setAccountName(rs.getString(4));
            site.setAddress(rs.getString(5));
            site.setCity(rs.getString(6));
            site.setState((rs.getString(7) != null) ? rs.getString(7) : " ");
            site.setStatus(rs.getString(8));
            site.setPostalCode(rs.getString(9));
            site.setCounty(rs.getString(10));
            String targetS = rs.getString(11);
            int target=0;
            if(targetS != null){
                try{
                    target = Integer.parseInt(targetS);
                }catch(NumberFormatException e){}
            }
            site.setTargetFacilityRank(target);
          siteV.add(site);
      }
    }

    rs.close();
    stmt.close();

  return siteV;
    }

    /**
     * <code>getNextTradingProfileControlNum</code> updates and gets the
     * next trading profile control number.
     *
     * @param conn a <code>Connection</code> value that is open db connection
     * @param pTradingProfileId the id of the trading profile
     * @param pControlField is either the interchange or group control
     * num field
     * @return an <code>int</code> with the next control number.
     * @exception SQLException if an error occurs
     * @exception DataNotFoundException if trading profile not found
     */
    public static synchronized int
    getNextTradingProfileControlNum(Connection pCon,
    		int pTradingProfileId,
    		String pControlField, boolean noTxConnection)
    throws SQLException, DataNotFoundException {
    	if (noTxConnection)
    		pCon.setAutoCommit(false);
    	StringBuffer sqlBuf =
    		new StringBuffer("UPDATE CLW_TRADING_PROFILE SET ");
    	sqlBuf.append(pControlField);
    	sqlBuf.append(" = ");
    	sqlBuf.append(pControlField);
    	sqlBuf.append(" + 1 WHERE TRADING_PROFILE_ID = ?");
    	String sql = sqlBuf.toString();

    	PreparedStatement pstmt = pCon.prepareStatement(sql);

    	pstmt.setInt(1,pTradingProfileId);

    	if (log.isDebugEnabled()) {
    		log.debug("SQL:   TRADING_PROFILE_ID="+pTradingProfileId);
    		log.debug("SQL: " + sql);
    	}

    	int n = pstmt.executeUpdate();
    	pstmt.close();

    	// trading profile does not exist, return 0
    	if (n == 0) {
    		throw new DataNotFoundException("TRADING_PROFILE_ID :" + pTradingProfileId);
    	}

    	sqlBuf = new StringBuffer("SELECT ");
    	sqlBuf.append(pControlField);
    	sqlBuf.append(" FROM CLW_TRADING_PROFILE WHERE TRADING_PROFILE_ID = ?");
    	sql = sqlBuf.toString();

    	pstmt = pCon.prepareStatement(sql);

    	pstmt.setInt(1,pTradingProfileId);

    	if (log.isDebugEnabled()) {
    		log.debug("SQL:   TRADING_PROFILE_ID="+pTradingProfileId);
    		log.debug("SQL: " + sql);
    	}

    	ResultSet rs = pstmt.executeQuery();
    	if (rs.next()) {
    		n = rs.getInt(1);
    		rs.close();
    		pstmt.close();
    		if (noTxConnection)
    			pCon.commit();
    	} else {
    		if (noTxConnection)
    			pCon.rollback();
    		throw new DataNotFoundException("TRADING_PROFILE_ID :" + pTradingProfileId);
    	}

    	return n;
    }


    /**
     *Returns the store that the supplied account belongs to
     */
    public static int getStoreForAccount(Connection pConn,
               int pAccountId)
                           throws SQLException {
        int storeid = -1;
        Statement stmt  = null;
        ResultSet rs = null;

        try {
            String query = "select max(" +
            BusEntityAssocDataAccess.BUS_ENTITY2_ID + ") " +
            " from " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " where " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = " + pAccountId + " and " +
            BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD +
            " = '" +
            RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "'";

            stmt = pConn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                storeid = rs.getInt(1);
            }
        }
        finally{
            if (null != rs ) {            rs.close(); }
            if ( null != stmt ) {            stmt.close(); }
        }

        return storeid;
    }

    /**
     * Returns the store that the supplied manufacturer belongs to
     */
    public static int getStoreForManufacturer(Connection pConn, int id) throws SQLException {
        int storeid = -1;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String query = "select max(" +
                    BusEntityAssocDataAccess.BUS_ENTITY2_ID + ") " +
                    " from " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " where " +
                    BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = " + id + " and " +
                    BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD +
                    " = '" +
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE + "'";

            stmt = pConn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                storeid = rs.getInt(1);
            }
        }
        finally {
            if (null != rs) {
                rs.close();
            }
            if (null != stmt) {
                stmt.close();
            }
        }

        return storeid;
     }

    /**
     *Returns the store that the supplied distributor belongs to
     */
    public static int getStoreForDistributor(Connection pConn,
               int pId)
                           throws SQLException {
        int storeid = -1;
        Statement stmt  = null;
        ResultSet rs = null;

        try {
            String query = "select max(" +
            BusEntityAssocDataAccess.BUS_ENTITY2_ID + ") " +
            " from " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " where " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = " + pId + " and " +
            BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD +
            " = '" +
            RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE + "'";
            stmt = pConn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                storeid = rs.getInt(1);
            }
        }
        finally{
            if (null != rs ) {            rs.close(); }
            if ( null != stmt ) {            stmt.close(); }
        }

        return storeid;
    }
    
    public static ArrayList getAccountsForSites(Connection pCon, ArrayList sites) throws SQLException {
    	
    	ArrayList accounts = new ArrayList();
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	try{
    		
    		String query = "select distinct "+BusEntityAssocDataAccess.BUS_ENTITY2_ID + " from "+
    		BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " where "+
    		BusEntityAssocDataAccess.BUS_ENTITY1_ID + " IN(" +Utility.toCommaSting(sites) +") and "+
    		BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD+ "= '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT +"'";
    		
    		stmt = pCon.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
            	accounts.add((Integer)rs.getInt(1));
            }
    	}
    	finally{
            if (null != rs ) {            rs.close(); }
            if ( null != stmt ) {            stmt.close(); }
        }
    	
    	return accounts;
    }

    public static int getAccountForSite(Connection pConn,
                                        int pSiteId)
        throws SQLException {
        int acctid = -1;
        Statement stmt  = null;
        ResultSet rs = null;

        try {
            String query = "select max(" +
            BusEntityAssocDataAccess.BUS_ENTITY2_ID + ") " +
            " from " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " where " +
            BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = " + pSiteId + " and " +
            BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD +
            " = '" +
            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'";

            stmt = pConn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                acctid = rs.getInt(1);
            }
        }
        finally{
            if (null != rs ) {rs.close(); }
            if ( null != stmt ) {stmt.close(); }
        }

        return acctid;
    }

    java.util.Hashtable mAccountsByErp = new java.util.Hashtable();
    private int mAccountsByErpReq = 0;

    public BusEntityData getAccountByErpNum(Connection pConn, String pAccountErpNum)
    throws Exception
    {
  if (mAccountsByErpReq++ > 1000) {
      mAccountsByErp = new java.util.Hashtable();
      mAccountsByErpReq = 0;
  }

        if ( mAccountsByErp.containsKey(pAccountErpNum) ) {
            return (BusEntityData)mAccountsByErp.get(pAccountErpNum);
        }

        DBCriteria  crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.ERP_NUM,
        pAccountErpNum);
        crit.addEqualTo(
        BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

        BusEntityDataVector accountV = BusEntityDataAccess.select(
        pConn, crit);

        if (null != accountV && accountV.size() > 0)
        {
             mAccountsByErp.put(pAccountErpNum,(BusEntityData)accountV.get(0));
             return (BusEntityData)accountV.get(0);
        }

        return BusEntityData.createValue();
    }

    java.util.Hashtable mAccountsById = new java.util.Hashtable();
    private int mAccountsByIdReq = 0;

    public BusEntityData getAccountById(Connection pConn, int pAccountId)
    throws Exception
    {
  if (mAccountsByIdReq++ > 1000) {
      mAccountsById = new java.util.Hashtable();
      mAccountsByIdReq = 0;
  }

        Integer iAcctId = new Integer(pAccountId);
        if ( mAccountsById.containsKey(iAcctId) ) {
      log.info(" CACHED  Account pAccountId=" + pAccountId);
            return (BusEntityData)mAccountsById.get(iAcctId);
        }

        DBCriteria  crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,
                        pAccountId);
        crit.addEqualTo(
                        BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

        BusEntityDataVector accountV = BusEntityDataAccess.select
            (pConn, crit);

        if (null != accountV && accountV.size() > 0)
        {
             mAccountsById.put(iAcctId,(BusEntityData)accountV.get(0));
       log.info(" CACHE ADDED  Account pAccountId=" + pAccountId);
             return (BusEntityData)accountV.get(0);
        }

        return BusEntityData.createValue();
    }


    public BusEntityDataVector getAccountsById(Connection pConn, IdVector pAccountIds) throws Exception
    {
    	BusEntityDataVector returnValue = new BusEntityDataVector();

        DBCriteria  crit = new DBCriteria();
        crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, pAccountIds);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

        returnValue = BusEntityDataAccess.select(pConn, crit);

        return returnValue;
    }

    java.util.Hashtable mDistributorsByErp = new java.util.Hashtable();

    public BusEntityData getDistributorByErpNum(Connection pConn, String pDistributorErpNum)
    throws SQLException
    {

        if ( mDistributorsByErp.containsKey(pDistributorErpNum) ) {
            return (BusEntityData)mDistributorsByErp.get(pDistributorErpNum);
        }

        DBCriteria  crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.ERP_NUM,
        pDistributorErpNum);
        crit.addEqualTo(
        BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

        BusEntityDataVector beV = BusEntityDataAccess.select(
        pConn, crit);

        if (null != beV && beV.size() > 0)
        {
             mDistributorsByErp.put(pDistributorErpNum,(BusEntityData)beV.get(0));
             return (BusEntityData)beV.get(0);
        }

        return BusEntityData.createValue();
    }

    public static BusEntityData getSiteByErpNums(Connection pConn,
    String pAccountErpNum, String pSiteErpNum
    )
    throws Exception
    {

        String q = "select s.bus_entity_id from clw_bus_entity s, clw_bus_entity a, " +
        " clw_bus_entity_assoc ba " +
        " where " +
        " s.erp_num = '" + pSiteErpNum + "' " +
        " and a.erp_num = '" + pAccountErpNum + "' " +
        " and a.bus_entity_id = ba.bus_entity2_id " +
        " and s.bus_entity_id = ba.bus_entity1_id ";

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, q);
        BusEntityDataVector bedv = BusEntityDataAccess.select( pConn, dbc );
        if ( bedv == null || bedv.size() == 0 ) {
            return null;
        }
        return (BusEntityData)bedv.get(0);

    }



    public static SiteData getSiteData(Connection pCon, int pSiteId ) throws Exception {

        log.debug("BusEntityDAO.getSiteData, pSiteId=" + pSiteId);
        if (pSiteId <= 0 ) {
            return null;
        }

        BusEntityData accountEntity = null, siteEntity;
        BusEntityAssocData accountAssoc = null;
        PropertyData subContractor = null;
        Integer targetFacilityRank = null;
        PropertyData taxableIndicator = null;
        PropertyData invShopping = null;
        PropertyData reBill = null;
        PropertyData invShoppingType = null;
        PropertyData inventoryShoppingHoldOrderUntilDeliveryDate = null;
        PropertyDataVector miscProperties = null;
        PropertyDataVector fieldProperties = null;
        PropertyDataVector fieldPropertiesRuntime = null;
        AddressData shippingAddress = null;
        BudgetViewVector budgets = null;
        PhoneDataVector phones;
        BlanketPoNumData po = null;
        PropertyData allowCorpSchedOrder = null;

        siteEntity = BusEntityDataAccess.select(pCon, pSiteId);

        DBCriteria lcrit = new DBCriteria();

        lcrit.addJoinCondition(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID, BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID);
        lcrit.addJoinTableEqualTo(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC, BlanketPoNumAssocDataAccess.BUS_ENTITY_ID, pSiteId);
        lcrit.addDataAccessForJoin(new BlanketPoNumDataAccess());

        List bpoRes = JoinDataAccess.select(pCon,lcrit,500);

        if(bpoRes != null && !bpoRes.isEmpty()){
            po = (BlanketPoNumData) ((List)bpoRes.get(0)).get(0);
        }

        // get account association
        DBCriteria assocCrit = new DBCriteria();
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,  pSiteId);
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,  RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

        BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(pCon, assocCrit);

        // there ought to be exactly one - if not, that's a problem
        if (assocVec.size() > 0) {
            accountAssoc = (BusEntityAssocData) assocVec.get(0);
            accountEntity = BusEntityDataAccess.select(pCon, accountAssoc.getBusEntity2Id());
        }

        // Get the account information.

        AddressDataVector addresses = getSiteAddresses(pCon, pSiteId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        if (addresses.size() > 0) {
            shippingAddress = (AddressData)addresses.get(0);
        } else {
            addresses = getSiteAddresses(pCon, pSiteId, RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
            if (addresses.size() > 0) {
                shippingAddress = (AddressData)addresses.get(0);
            }
        }
        
        log.debug("shippingAddress=" + shippingAddress);

        // get site phones
        DBCriteria phoneCrit = new DBCriteria();
        phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID, pSiteId);

        phones = PhoneDataAccess.select(pCon, phoneCrit);

        if (accountEntity != null) {
            Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountEntity.getBusEntityId());
            if (budgetYear != null) {
                budgets = BudgetDAO.getBudgetsForSite(pCon, accountEntity.getBusEntityId(), pSiteId, 0, budgetYear);
            }
        } else {
            int accountId = BusEntityDAO.getAccountForSite(pCon, pSiteId);
            Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountEntity.getBusEntityId());
            if (budgetYear != null) {
                budgets = BudgetDAO.getBudgetsForSite(pCon, accountId, pSiteId, 0, budgetYear);
            }
        }


        // get properties
        DBCriteria propCrit = new DBCriteria();
        propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSiteId);
        propCrit.addIsNull(PropertyDataAccess.USER_ID);

        PropertyDataVector props = PropertyDataAccess.select(pCon,propCrit);

        miscProperties = new PropertyDataVector();
        fieldProperties = new PropertyDataVector();
        fieldPropertiesRuntime = new PropertyDataVector();

        //set up the account iterator, which will only be accessed once
        int myAcctBus = (accountEntity!=null)?accountEntity.getBusEntityId():0;
        DBCriteria propCritAcc = new DBCriteria();
        propCritAcc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, myAcctBus);
        propCritAcc.addIsNull(PropertyDataAccess.USER_ID);

        PropertyDataVector propsAcc = PropertyDataAccess.select(pCon,propCritAcc);
        Iterator propAccIter = propsAcc.iterator();

        for (Object oProp : props) {

            PropertyData prop = (PropertyData) oProp;
            String propType = prop.getPropertyTypeCd();

            log.debug("--------------- setting site property:" + prop);

            if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.EXTRA)) {
                miscProperties.add(prop);
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING)) {
                invShopping = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE)) {
                invShoppingType = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOP_HOLD_DEL_DATE)) {
                inventoryShoppingHoldOrderUntilDeliveryDate = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER)) {
                allowCorpSchedOrder = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.SUB_CONTRACTOR)) {
                subContractor = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK)) {
                try {
                    if (prop != null && prop.getValue() != null &&
                            prop.getValue().trim().length() > 0) {
                        targetFacilityRank = new Integer(prop.getValue());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)) {

                fieldProperties.add(prop);

                /*  we only do this once because it
                 *  pertains to the Account level.
                 *  Doing it every time would give us duplicates.  */
                while (propAccIter.hasNext()) {

                    PropertyData propAcct = (PropertyData) propAccIter.next();
                    String propShortDesc = propAcct.getShortDesc();
                    String propVal = propAcct.getValue();

                    //If account level says to show a
                    //property in runtime, add it
                    if ((propShortDesc.indexOf("ShowRuntime") == 2) && (propVal.equals("true"))) {
                        fieldPropertiesRuntime.add(prop);
                        break;
                    }
                }
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR)) {
                taxableIndicator = prop;
            } else if(propType.equals(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER)) {
            	reBill = prop;
            }
            else {
                // some property we know nothing about
                miscProperties.add(prop);
            }
        }

        SiteData siteD = new SiteData( siteEntity,
               accountEntity,
               accountAssoc,
               shippingAddress,
               phones,
               budgets,
               subContractor,
               targetFacilityRank,
               taxableIndicator,
               miscProperties,
               fieldProperties,
               fieldPropertiesRuntime,
               po,
               null,
               null,
               null
               );

        if ( invShopping == null ) {
            invShopping = PropertyData.createValue();
        }
        if ( invShoppingType == null ) {
            invShoppingType = PropertyData.createValue();
        }
        if (inventoryShoppingHoldOrderUntilDeliveryDate == null) {
          inventoryShoppingHoldOrderUntilDeliveryDate = PropertyData.createValue();
        }
        
        if(reBill==null) {
        	reBill = PropertyData.createValue();
        }
        if ( allowCorpSchedOrder == null ) {
        	allowCorpSchedOrder = PropertyData.createValue();
        }
        
        siteD.setInventoryShopping(invShopping);
        siteD.setReBill(reBill);
        siteD.setInventoryShoppingType(invShoppingType);
        siteD.setInventoryShoppingHoldOrderUntilDeliveryDate(inventoryShoppingHoldOrderUntilDeliveryDate);
        siteD.setAllowCorpSchedOrder(allowCorpSchedOrder);

        // Set the contract info.
        DBCriteria conCrit = new DBCriteria();
        conCrit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,
         siteEntity.getBusEntityId() );
      conCrit.addOrderBy(CatalogAssocDataAccess.CATALOG_ID, false);
        CatalogAssocDataVector catalogAssocs =
            CatalogAssocDataAccess.select(pCon, conCrit);

        if ( catalogAssocs != null && catalogAssocs.size() > 0 ){
            CatalogAssocData cad = (CatalogAssocData)catalogAssocs.get(0);
            int catalogid = cad.getCatalogId();
            siteD.setSiteCatalogId(catalogid );
            conCrit = new DBCriteria();
            conCrit.addEqualTo(ContractDataAccess.CATALOG_ID, catalogid);
            conCrit.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                               RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            conCrit.addOrderBy(ContractDataAccess.CONTRACT_ID, false);

            ContractDataVector condv =
                ContractDataAccess.select(pCon, conCrit);
            if ( condv != null && condv.size() > 0 ) {
                siteD.setContractData( (ContractData)condv.get(0) );
            }
        }

        //set the additional data for new pricing model,
        //that allow the improved search of products
        if (siteD.isUseProductBundle()) {
            SitePriceListView priceLists = PriceListDAO.getSitePriceListView(pCon, siteD.getSiteId());
            siteD.setPriceLists(priceLists);
            if (RefCodeNames.SITE_PRODUCT_BUNDLE.ORDER_GUIDE.equals(siteD.getProductBundle())) {
                //Order Guide types that should be included are: ORDER_GUIDE_TEMPLATE, SITE_ORDER_GUIDE_TEMPLATE, CUSTOM_ORDER_GUIDE
                OrderGuideDataVector templateOrderGuides = ShoppingDAO.getTemplateOrderGuides(pCon, siteD.getSiteCatalogId(), siteD.getSiteId());
                OrderGuideDataVector customeOrderGuides = ShoppingDAO.getCustomOrderGuides(pCon, siteD.getAccountId(), siteD.getSiteId());
                siteD.setTemplateOrderGuides(templateOrderGuides);
                siteD.setCustomOrderGuides(customeOrderGuides);
            }
        }


  log.debug("BusEntityDAO.getSiteData, pSiteId=" + pSiteId +
      " found site: " + siteD.toBasicInfo() );

        return siteD;

    }

    public static SiteData getSiteData(Connection pCon,
                                       int pSiteId,
                                       String pSiteName)
        throws Exception,
           com.cleanwise.service.api.util.DataNotFoundException {

        if (pSiteId == 0 ) {
            if(pSiteName == null || pSiteName.length() == 0){
                String msg = "No Site Name";
                log.error(msg);
                throw new DataNotFoundException(msg);
            }
            BusEntityData            siteEntity = null;

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityDataAccess.SHORT_DESC,
                           pSiteName);
            BusEntityDataVector bedv = BusEntityDataAccess.select
                (pCon, dbc);
            if ( bedv == null || bedv.size() == 0) {
                String msg = "no bus entity records found for " +
                    " pSiteName=" + pSiteName;
                log.error(msg);
                throw new DataNotFoundException(msg);
            }
            siteEntity = (BusEntityData)bedv.get(0);
            pSiteId = siteEntity.getBusEntityId();
        }
        log.debug(" ...... pSiteId=" + pSiteId);

        return getSiteData(pCon, pSiteId);
    }

    public static AddressDataVector getSiteAddresses
        ( Connection pCon, int pSiteId, String pAddrCd)
        throws SQLException {

        DBCriteria addressCrit = new DBCriteria();
        addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                               pSiteId);
        addressCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                               pAddrCd);

        AddressDataVector addresses = AddressDataAccess.select
            (pCon,addressCrit);
          return addresses;
    }

    public static SiteDeliveryScheduleViewVector getDeliverySchs
        ( Connection pCon, int pAccountId, int pSiteId)
        throws SQLException {

        String be_sql =
            pSiteId > 0 ?
            "" + pSiteId :
            "select bus_entity1_id from clw_bus_entity_assoc "
            + " where bus_entity2_id = " + pAccountId;

        SiteDeliveryScheduleViewVector sdsv =
            new SiteDeliveryScheduleViewVector();
        String sql =
            " select s.bus_entity_id, s.short_desc,  "
            + " nvl(  (select p.clw_value from clw_property p "
            + "  where p.bus_entity_id =  s.bus_entity_id "
            + "  and p.short_desc = '"
            + RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE
            + "'      ), 'none')"
            + ",sa.CITY,sa.COUNTY_CD,sa.STATE_PROVINCE_CD"
            + ",  sa.POSTAL_CODE, sa.COUNTY_CD "
            + " , s.bus_entity_status_cd "
            + " from clw_bus_entity s, clw_address sa  where "
            + " s.bus_entity_id in ( " + be_sql + " ) "
            + "  and sa.bus_entity_id = s.bus_entity_id  "
            + " and sa.ADDRESS_TYPE_CD    = '"
            + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING
            + "'  order by 2";

        log.debug("SQL:" + sql);
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next()) {
            SiteDeliveryScheduleView sds =
                SiteDeliveryScheduleView.createValue();
            sds.setSiteId(rs.getInt(1));
            sds.setSiteName(rs.getString(2));
            sds.setSiteStatusCd(rs.getString("BUS_ENTITY_STATUS_CD"));
            sds.setCity(rs.getString("CITY"));
            sds.setCounty(rs.getString("COUNTY_CD"));
            sds.setState(rs.getString("STATE_PROVINCE_CD"));
            sds.setPostalCode(rs.getString("POSTAL_CODE"));
            sds.setCounty(rs.getString("COUNTY_CD"));

            // Decipher the schedule token
            StringTokenizer st = new StringTokenizer(rs.getString(3), ":");
            String stype = "", wk1st = "", wk2nd = "", wkenterv = "";
            for (int i = 0; st.hasMoreTokens(); i++ ) {
                if ( i == 0 ) stype = st.nextToken();
                if ( i == 1 ) {
                    wk1st = st.nextToken();
                    wkenterv = wk1st;
                }
                if ( i == 2 ) wk2nd = st.nextToken();
            }

            //log.debug("stype=" + stype + " wk1st=" + wk1st + " wk2nd=" + wk2nd);

            sds.setSiteScheduleType(rs.getString(3));
            if (stype.startsWith("Spe")) {
                sds.setIntervWeek(wkenterv);
            }
            else {
            if ( wk1st.startsWith("Last") ||
                 wk2nd.startsWith("Last") ) {
                sds.setLastWeekofMonth(true);
            }
            if ( wk1st.equals("1") ||
                 wk2nd.equals("1") ) {
                sds.setWeek1ofMonth(true);
            }
            if ( wk1st.equals("2") ||
                 wk2nd.equals("2") ) {
                sds.setWeek2ofMonth(true);
            }
            if ( wk1st.equals("3") ||
                 wk2nd.equals("3") ) {
                sds.setWeek3ofMonth(true);
            }
            if ( wk1st.equals("4") ||
                 wk2nd.equals("4") ) {
                sds.setWeek4ofMonth(true);
            }
            }

            sdsv.add(sds);
        }
        stmt.close();

        return sdsv;
    }


    /**
     *  Retrieves the freight handlers that match the given criteria. If no
     *  criteria is set all freight handlers will be retrieved.
     *
     *@param  pAddtlCrit           any additional criteria, may be null.
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME, 0 (no
     *      order)
     *@return                      The freightHandlers value
     *@exception  RemoteException  Description of the Exception
     *@returns                     a populated FreightHandlerViewVector with all
     *      of the freight handlers matching the supplied criteria. An empty
     *      (not null) FreightHandlerViewVector will be returned if no matching
     *      FreightHandlers were found.
     */

    public static FreightHandlerViewVector getFreightHandlerDetails(Connection conn,
            BusEntityDataVector pFreightHandlers)
            throws Exception {

        FreightHandlerViewVector v = new FreightHandlerViewVector();
        IdVector bids = new IdVector();

        try {
            Iterator iter = pFreightHandlers.iterator();
            while (iter.hasNext()) {
                FreightHandlerView fhv = FreightHandlerView.createValue();
                BusEntityData be = (BusEntityData) iter.next();
                fhv.setBusEntityData(be);
                bids.add(new Integer(be.getBusEntityId()));
                v.add(fhv);
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID, bids);
            AddressDataVector av = AddressDataAccess.select(conn, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, bids);
            PropertyDataVector pv = PropertyDataAccess.select(conn, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, bids);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.FREIGHT_HANDLER_STORE);
            BusEntityAssocDataVector storeAssoc = BusEntityAssocDataAccess.select(conn,dbc);

            Iterator i2 = v.iterator();
            while (i2.hasNext()) {
                FreightHandlerView fh = (FreightHandlerView) i2.next();
                int fhid = fh.getBusEntityData().getBusEntityId();
                for (int aidx = 0; av != null && aidx < av.size(); aidx++) {
                    AddressData ad = (AddressData) av.get(aidx);
                    int thisbid = ad.getBusEntityId();
                    if (thisbid == fhid) {
                        fh.setPrimaryAddress(ad);
                        break;
                    }
                }

                for (int pidx = 0; pv != null && pidx < pv.size(); pidx++) {
                    PropertyData pd = (PropertyData) pv.get(pidx);
                    int thisbid = pd.getBusEntityId();
                    if (thisbid == fhid) {
      String tcd = pd.getPropertyTypeCd();
      if ( null == tcd ) {
          tcd = "";
      }
      if ( tcd.equals(RefCodeNames.PROPERTY_TYPE_CD.FR_ROUTING_CD)) {
          fh.setEdiRoutingCd(pd.getValue());
      } else if ( tcd.equals(RefCodeNames.PROPERTY_TYPE_CD.FR_ON_INVOICE_CD)) {
          fh.setAcceptFreightOnInvoice(pd.getValue());
      } else {
          log.info("Unknown freight handler property: "+pd);
      }
                    }
                }

                for (int pidx = 0; storeAssoc != null && pidx < storeAssoc.size(); pidx++) {
                    BusEntityAssocData sa = (BusEntityAssocData) storeAssoc.get(pidx);
                    int thisbid = sa.getBusEntity1Id();
                    if (thisbid == fhid) {
                        fh.setStoreId(sa.getBusEntity2Id());
                        break;
                    }
                }

            }

            //initialize anything that was not set
            Iterator i3 = v.iterator();
            while (i3.hasNext()) {
                FreightHandlerView fh = (FreightHandlerView) i3.next();
                if (fh.getPrimaryAddress() == null) {
                    fh.setPrimaryAddress(AddressData.createValue());
                }

                if (fh.getEdiRoutingCd() == null) {
                    fh.setEdiRoutingCd("");
                }
            }

        } catch (Exception e) {
            throw new Exception("getFreightHandlers: " + e.getMessage());
        }

        return v;
    }

     /**
     *  Gets the FreightHandler with the specified id. If it cannot be found a
     *  DataNotFoundException is thrown.
     *
     *@param  pFreightHandlerId          an <code>int</code> value
     *@return                            a <code>FreightHandlerView</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if there is no freight handler with
     *      this id
     */
    public static FreightHandlerView getFreightHandler(Connection pCon, int pFreightHandlerId)
             throws Exception, DataNotFoundException {

        BusEntityData fh = BusEntityDataAccess.select(pCon,pFreightHandlerId);
        BusEntityDataVector scratch = new BusEntityDataVector();
        scratch.add(fh);
        FreightHandlerViewVector results = getFreightHandlerDetails(pCon, scratch);
        if (results.size() == 0) {
            throw new DataNotFoundException(Integer.toString(pFreightHandlerId));
        } else {
            return (FreightHandlerView) results.get(0);
        }
    }

    /**
     *Gets the freight handler that is used for this purchase order id if there is one.  Takes into
     *account order routing and the user slected freight logic.
     */
    public static FreightHandlerView  getFreightHandlerByPoId(Connection pCon, PurchaseOrderData pPo, int distributorId)
                throws Exception, DataNotFoundException {
        if(pPo == null){
            return null;
        }
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID, pPo.getPurchaseOrderId());
        IdVector fhids = OrderItemDataAccess.selectIdOnly(pCon,OrderItemDataAccess.FREIGHT_HANDLER_ID, crit);
        for ( int i = 0; fhids != null && i < fhids.size(); i++ ) {
           Integer fI = (Integer)fhids.get(i);
           if ( fI.intValue() > 0 ) {
            return getFreightHandler(pCon, fI.intValue());
           }
        }

        //Get any freight criteria
        if(distributorId > 0){
            OrderFreightDataVector orderFrt;

            DBCriteria orderFrtCrit = new DBCriteria();
            orderFrtCrit.addEqualTo(OrderFreightDataAccess.ORDER_ID,pPo.getOrderId());
            orderFrtCrit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID,distributorId);
            orderFrt = OrderFreightDataAccess.select(pCon,orderFrtCrit);

            Iterator it = orderFrt.iterator();
            while(it.hasNext()){
                OrderFreightData frt = (OrderFreightData) it.next();
                if(frt.getFreightHandlerId() > 0){
                    try{
                        FreightHandlerView fh =BusEntityDAO.getFreightHandler(pCon, frt.getFreightHandlerId());
                        return fh;
                    }catch(Exception e){
                        throw e;
                    }
                }
            }
        }
        return null;
    }


    /**
     *Converts the @see BusEntitySearchCriteria into a @see DBCriteria object.
     *Includes the full table names so it may be used in a join.
     *@returns the populated DBCriteria object
     *@param BusEntitySearchCriteria the criteria
     *@param String the type of the bus entity
     */
    public static DBCriteria convertToDBCriteria(BusEntitySearchCriteria pCrit,
            String pBusEntityTypeCd){
        DBCriteria dbc = null;
            dbc = new DBCriteria();
            DBCriteria dbcBusTerr = null;
            String groupId = pCrit.getSearchGroupId();
            String id = pCrit.getSearchId();
            String name = pCrit.getSearchName();
            String tCounty = pCrit.getSearchTerritoryCounty();
            String tZip = pCrit.getSearchTerritoryPostalCode();
            String
                tState = pCrit.getSearchTerritoryState(),
                tCity = pCrit.getSearchTerritoryCity();
            IdVector stores = pCrit.getStoreBusEntityIds();
            IdVector distributors = pCrit.getDistributorBusEntityIds();
            IdVector serviceProviders = pCrit.getServiceProviderBusEntityIds();
            IdVector accounts = pCrit.getAccountBusEntityIds();
            IdVector users = pCrit.getUserIds();

            IdVector parents = pCrit.getParentBusEntityIds();
            IdVector grandParents = null;
            IdVector children = null;
            Map propertyCrit = pCrit.getPropertyCriteria();

            int type = pCrit.getSearchNameType();
            int order = pCrit.getOrder();

            if(!pCrit.getSearchForInactive()) {
              dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                      RefCodeNames.DISTRIBUTOR_STATUS_CD.INACTIVE);
            }


            //deal with the store restrictions
            if(stores != null){
                if(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(pBusEntityTypeCd)){
                    dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(pBusEntityTypeCd)){
                    if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(pBusEntityTypeCd)){
                    if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER.equals(pBusEntityTypeCd)){
                    if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(pBusEntityTypeCd)){
                    if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR.equals(pBusEntityTypeCd)){
                    if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.FREIGHT_HANDLER.equals(pBusEntityTypeCd)){
                    if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(stores);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(pBusEntityTypeCd)){
                    grandParents = stores;
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT_BILLTO.equals(pBusEntityTypeCd)){
                    grandParents = stores;
                }else{
                    throw new RuntimeException("Unknonw bus entity serach type: "+pBusEntityTypeCd);
                }
            }


            //Add the filtering if any distributors were specified. Doesn't apply
            //to all bus entity types
            if(distributors != null){
                if(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(pBusEntityTypeCd)){
                    if(children == null){
                        children = new IdVector();
                    }
                    children.addAll(distributors);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(pBusEntityTypeCd)){
                    dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,distributors);
                }
            }

            //Add the filtering if any service providers were specified. Doesn't apply
            //to all bus entity types
            if(serviceProviders != null){
                if(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(pBusEntityTypeCd)){
                    if(children == null){
                        children = new IdVector();
                    }
                    children.addAll(serviceProviders);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(pBusEntityTypeCd)){
                    dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,serviceProviders);
                }
            }

            //Add the filtering if any accounts were specified. Doesn't apply
            //to all bus entity types
            if(accounts != null){
                if(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(pBusEntityTypeCd)){
                    if(children == null){
                        children = new IdVector();
                    }
                    children.addAll(accounts);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(pBusEntityTypeCd)){
                    dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,accounts);
                } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(pBusEntityTypeCd)) {
                   if(parents == null){
                        parents = new IdVector();
                    }
                    parents.addAll(accounts);
                }
            }

            if(parents != null && !parents.isEmpty()){
                DBCriteria subDbc = new DBCriteria();
                subDbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID,parents);
                dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, subDbc));
            }
            if(children != null && !children.isEmpty()){
                DBCriteria subDbc = new DBCriteria();
                subDbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,children);
                dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID, subDbc));
            }
            if(grandParents != null && !grandParents.isEmpty()){
                String subQuery = "SELECT a2.bus_entity1_id FROM clw_bus_entity_assoc a1, clw_bus_entity_assoc a2 "+
                                    "WHERE a1.bus_entity1_id = a2.bus_entity2_id AND a1.bus_entity2_id IN ("+Utility.toCommaSting(grandParents)+")";
                dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,subQuery);
            }

            if(propertyCrit != null && !propertyCrit.isEmpty()){
                Iterator it = propertyCrit.keySet().iterator();
                while(it.hasNext()){
                    String key = (String) it.next();
                    String val = (String) propertyCrit.get(key);
                    //only add the search criteria if everything is set
                    if(Utility.isSet(key) && Utility.isSet(val)){
                        DBCriteria propSubCrit = new DBCriteria();
                        val = Utility.trimLeft(val, "0");
                        propSubCrit.addEqualTo("LTRIM("+PropertyDataAccess.CLW_VALUE+",'0')", val);
                        //propSubCrit.addCondition("LTRIM("+PropertyDataAccess.CLW_VALUE+",'0')=LTRIM('"+val+"','0')");
                        //use short desc as the tye may be "EXTRA"
                        propSubCrit.addEqualTo(PropertyDataAccess.SHORT_DESC,key);
                        String propSubSql = PropertyDataAccess.getSqlSelectIdOnly(PropertyDataAccess.BUS_ENTITY_ID, propSubCrit);
                        dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,propSubSql);
                    }
                }
            }

            if(users != null && !users.isEmpty()){
                boolean doingIndirectAssociation;
                if(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(pBusEntityTypeCd)){
                    doingIndirectAssociation = true;
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(pBusEntityTypeCd) ||
                        RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(pBusEntityTypeCd) ||
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(pBusEntityTypeCd) ||
                        RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(pBusEntityTypeCd)){
                    doingIndirectAssociation = false;
                }else{
                    throw new IllegalStateException("cannot find dist associations with users and bus entity type of: "+pBusEntityTypeCd);
                }
                String userIds = IdVector.toCommaString(users);
                String bus_entity_id = BusEntityDataAccess.BUS_ENTITY_ID;
                String bus_entity1_id = BusEntityAssocDataAccess.BUS_ENTITY1_ID;
                String bus_entity2_id = BusEntityAssocDataAccess.BUS_ENTITY2_ID;
                String clw_bus_entity = BusEntityDataAccess.CLW_BUS_ENTITY;
                String user_id = UserAssocDataAccess.USER_ID;
                //direct association and associated through throwse account and site
                StringBuffer userSubSqlBuf = new StringBuffer();
                userSubSqlBuf.append("Select "+bus_entity_id+" from "+clw_bus_entity+" b ");
                userSubSqlBuf.append(" WHERE ");
                userSubSqlBuf.append(BusEntityDataAccess.BUS_ENTITY_TYPE_CD +"='"+pBusEntityTypeCd+"'");
                userSubSqlBuf.append(" AND ");
                userSubSqlBuf.append("("); //START paren id 001
                //****START of one of two or segments
                userSubSqlBuf.append("exists (select * from "+UserAssocDataAccess.CLW_USER_ASSOC+" ua ");
                userSubSqlBuf.append(" where ");
                userSubSqlBuf.append("ua."+user_id+" IN ("+userIds+") and b."+bus_entity_id+"=ua."+bus_entity_id+")");
                //****END of one of two or segments
                if(doingIndirectAssociation){
                    userSubSqlBuf.append(" OR ");
                    //****START of two of two or segments
                    userSubSqlBuf.append("exists (select * from "+UserAssocDataAccess.CLW_USER_ASSOC+" ua, "+BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC+" ba ");
                    userSubSqlBuf.append(" where ");
                    userSubSqlBuf.append("ua."+user_id+" IN ("+userIds+") and ");
                    userSubSqlBuf.append("ua."+bus_entity_id+"=ba."+bus_entity1_id+" and ");
                    userSubSqlBuf.append("ba."+bus_entity2_id+"=b."+bus_entity_id+")");
                    //****END of two of two or segments
                }
                userSubSqlBuf.append(")"); //END paren id 001
                dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,userSubSqlBuf.toString());
                //select * from clw_bus_entity b where
                //store.bus_entity_type_cd = 'STORE' and
                //(
                //exists (select * from clw_user_assoc ua where ua.user_id IN (XXX) and b.bus_entity_id = ua.bus_entity_id)
                //or
                //exists (select * from clw_user_assoc ua, clw_bus_entity_assoc ba where
                //ua.user_id IN (XXX) and ua.bus_entity_id = ba.bus_entity1_id
                //and ba.bus_entity2_id = b.bus_entity_id)
                //)
            }

            if(Utility.isSet(groupId)){
                String value = groupId;
                DBCriteria gdbc = new DBCriteria();
                gdbc.addEqualTo(GroupAssocDataAccess.GROUP_ID,value);
                gdbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
                dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,GroupAssocDataAccess.getSqlSelectIdOnly(GroupAssocDataAccess.BUS_ENTITY_ID, gdbc));
            }
            if (Utility.isSet(id)) {
                int val = 0;
                try{val = Integer.parseInt(id); }catch(Exception e){}
                dbc.addJoinTableEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID, val);
            }
            if (Utility.isSet(name)){
                if (type == BusEntitySearchCriteria.NAME_STARTS_WITH) {
                    dbc.addJoinTableBeginsWithIgnoreCase(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.SHORT_DESC, name);
                } else if (type == BusEntitySearchCriteria.EXACT_MATCH){
                  dbc.addJoinTableEqualToIgnoreCase(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.SHORT_DESC, name);
                } else {
                    dbc.addJoinTableContainsIgnoreCase(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.SHORT_DESC, name);
                }
            }
            if (Utility.isSet(tZip)) {
                if(dbcBusTerr==null) dbcBusTerr = new DBCriteria();
                dbcBusTerr.addEqualTo(BusEntityTerrDataAccess.POSTAL_CODE,tZip);
            }
            if(dbcBusTerr != null) {
              if(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(pBusEntityTypeCd)){
                  dbcBusTerr.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_TERR_CD,
                    RefCodeNames.BUS_ENTITY_TERR_CD.DIST_TERRITORY);
              }
              String terrSubquery =
                 BusEntityTerrDataAccess.getSqlSelectIdOnly(BusEntityTerrDataAccess.BUS_ENTITY_ID,
                                 dbcBusTerr);
              dbc.addJoinTableOneOf(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,terrSubquery);
            }
            dbc.addJoinTableEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_TYPE_CD,pBusEntityTypeCd);
            switch (order) {
                case BusEntitySearchCriteria.ORDER_BY_ID:
                    dbc.addJoinTableOrderBy(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID);
                    break;
                case BusEntitySearchCriteria.ORDER_BY_NAME:
                    dbc.addJoinTableOrderBy(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.SHORT_DESC);
                    break;
            }
            String sqlToPrint = BusEntityDataAccess.getSqlSelectIdOnly("*",dbc);
            log.info(sqlToPrint);
            return dbc;
    }

    /**
     *Searches for any BusEntitys based off a standard set of criteria.
     */
    public static BusEntityDataVector getBusEntityByCriteria(Connection pCon,
        BusEntitySearchCriteria pCrit, String pBusEntityTypeCd)
        throws SQLException{
            DBCriteria dbc = convertToDBCriteria(pCrit,pBusEntityTypeCd);
            BusEntityDataVector busEntityVec = new BusEntityDataVector();
            int maxRows = pCrit.getResultLimit();
            log.info("BusEntityDAO maxRows: "+maxRows);
            JoinDataAccess.selectTableInto(new BusEntityDataAccess(),busEntityVec, pCon, dbc,maxRows);
            //BusEntityDataVector busEntityVec = BusEntityDataAccess.select(pCon, dbc);
            return busEntityVec;
    }

    public FiscalCalenderData getCurrentFiscalCalender(
    Connection pCon,
    int pBusEntityId )throws SQLException{
        return getFiscalCalender(pCon,pBusEntityId,new java.util.Date());
    }

    public FiscalCalenderView getCurrentFiscalCalenderV(Connection pCon,
                                                        int pBusEntityId) throws SQLException {
        return getFiscalCalenderV(pCon, pBusEntityId, new java.util.Date());
    }

    /**
     * Gets the fiscal calender for the specified date with details.  Will initialize the fiscal date
     * if it is not set.
     *
     * @param pCon         connection
     * @param pBusEntityId the account id
     * @param pForDate     the date to get teh fiscal calender for.  May be in the past or future
     * @return the apropriate fiscal calender
     * @throws SQLException if an exceptions
     */
    public FiscalCalenderView getFiscalCalenderV(Connection pCon,
                                                 int pBusEntityId,
                                                 java.util.Date pForDate) throws SQLException {
        FiscalCalenderView fcv = null;

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID, pBusEntityId);
        dbc.addLessOrEqual(FiscalCalenderDataAccess.EFF_DATE, pForDate);
        dbc.addOrderBy(FiscalCalenderDataAccess.EFF_DATE, false);

        FiscalCalenderDataVector v = FiscalCalenderDataAccess.select(pCon, dbc);

        if (null != v && v.size() > 0) {

            FiscalCalenderData fcd = (FiscalCalenderData) v.get(0);

            int fiscalYear = fcd.getFiscalYear();
            if (fiscalYear <= 0) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(pForDate);
                fcd.setFiscalYear(cal.get(Calendar.YEAR));
            }

            dbc = new DBCriteria();
            dbc.addEqualTo(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, fcd.getFiscalCalenderId());
            dbc.addOrderBy(FiscalCalenderDetailDataAccess.PERIOD);

            FiscalCalenderDetailDataVector details = FiscalCalenderDetailDataAccess.select(pCon, dbc);

            fcv = new FiscalCalenderView(fcd, details);

        }

        return fcv;
    }

    /**
     * Gets the fiscal calenders for the specified date with details.
     *
     * @param pCon     connection
     * @param pForDate the date to get teh fiscal calender for.  May be in the past or future
     * @return the apropriate fiscal calender
     * @throws SQLException if an exceptions
     */
    public FiscalCalenderViewVector getFiscalCalenderCollections(Connection pCon,
                                                        IdVector pBusEntityIds,
                                                        java.util.Date pForDate) throws SQLException {

        FiscalCalenderViewVector fcvv = new FiscalCalenderViewVector();

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(FiscalCalenderDataAccess.BUS_ENTITY_ID, pBusEntityIds);
        dbc.addLessOrEqual(FiscalCalenderDataAccess.EFF_DATE, pForDate);
        dbc.addOrderBy(FiscalCalenderDataAccess.BUS_ENTITY_ID);
        dbc.addOrderBy(FiscalCalenderDataAccess.EFF_DATE, false);

        FiscalCalenderDataVector v = FiscalCalenderDataAccess.select(pCon, dbc);

        if (null != v && v.size() > 0) {
            Map<Integer, FiscalCalenderDetailDataVector> fiscalCalenderDataDetailMap = new HashMap<Integer, FiscalCalenderDetailDataVector>();
            IdVector fiscalCalenderIds = new IdVector();
            for (int i = 0; i < v.size(); i++) {
                FiscalCalenderData fcd = (FiscalCalenderData) v.get(i);
                fiscalCalenderIds.add(fcd.getFiscalCalenderId());
                FiscalCalenderDetailDataVector details = new FiscalCalenderDetailDataVector();
                fiscalCalenderDataDetailMap.put(fcd.getFiscalCalenderId(), details);
                fcvv.add(new FiscalCalenderView(fcd, details));
            }
            dbc = new DBCriteria();
            dbc.addOneOf(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, fiscalCalenderIds);
            dbc.addOrderBy(FiscalCalenderDetailDataAccess.PERIOD);

            FiscalCalenderDetailDataVector allDetails = FiscalCalenderDetailDataAccess.select(pCon, dbc);
            final int allDetailsSize = allDetails.size();
            for (int i = 0; allDetails != null && i < allDetailsSize; i++) {
                FiscalCalenderDetailData fcdd = (FiscalCalenderDetailData) allDetails.get(i);
                FiscalCalenderDetailDataVector details = fiscalCalenderDataDetailMap.get(fcdd.getFiscalCalenderId());
                details.add(fcdd);
            }
        }
        return fcvv;
    }
  /**
   * Gets the fiscal calender for the specified date.  Will initialize the fiscal date
   * if it is not set.
   *
   * @param pCon
   * @param pBusEntityId the account id
   * @param pForDate the date to get teh fiscal calender for.  May be in the past or future
   * @return the apropriate fiscal calender
   * @throws SQLException
   */
  public FiscalCalenderData getFiscalCalender(Connection pCon,int pBusEntityId,java.util.Date pForDate)
        throws SQLException
  {
    FiscalCalenderData fcd = null;
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,
                       pBusEntityId);
        dbc.addLessOrEqual( FiscalCalenderDataAccess.EFF_DATE ,pForDate );
        boolean ascSortOrder = false;
        dbc.addOrderBy(FiscalCalenderDataAccess.EFF_DATE, ascSortOrder);
        log.debug("\n\n" + FiscalCalenderDataAccess.getSqlSelectIdOnly("*", dbc));
        FiscalCalenderDataVector v =
            FiscalCalenderDataAccess.select(pCon, dbc);
        if ( null != v && v.size() > 0 )
        {
            fcd = (FiscalCalenderData)v.get(0);
            int fiscalYear = fcd.getFiscalYear();
            if ( fiscalYear <= 0 )
            {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(pForDate);
                fcd.setFiscalYear(cal.get(Calendar.YEAR));
            }
        }

    return fcd;
  }

  /**
   * Gets the fiscal calender for the specified year.  DOES init the fiscal year property
   */
  public FiscalCalenderData getFiscalCalenderForYear(Connection pCon,int pBusEntityId,int pYear)
    throws SQLException
  {
    FiscalCalenderData fcd = null;
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,pBusEntityId);
      String y = FiscalCalenderDataAccess.FISCAL_YEAR;
      dbc.addCondition("("+y+" is null or "+y+" = 0 or "+y+" = "+pYear+")");
      boolean ascSortOrder = false;
      dbc.addOrderBy(FiscalCalenderDataAccess.EFF_DATE, ascSortOrder);
      log.info("\n\n" + FiscalCalenderDataAccess.getSqlSelectIdOnly("*", dbc));
      FiscalCalenderDataVector v = FiscalCalenderDataAccess.select(pCon, dbc);
      if ( null != v && !v.isEmpty()){
          fcd = (FiscalCalenderData)v.get(0);
          int fiscalYear = fcd.getFiscalYear();
          fcd.setFiscalYear(pYear);
      }
    return fcd;
  }

    public FiscalCalenderView getFiscalCalenderVForYear(Connection pCon,
                                                        int pBusEntityId,
                                                        int pYear) throws SQLException {
        FiscalCalenderData fcd = getFiscalCalenderForYear(pCon, pBusEntityId, pYear);
        if (fcd != null) {
            FiscalCalenderDetailDataVector fcDetails = getFiscalCalenderDetails(pCon, fcd.getFiscalCalenderId());
            return new FiscalCalenderView(fcd, fcDetails);
        }

        return null;
    }


    public FiscalCalenderDetailDataVector getFiscalCalenderDetails(Connection pCon, int pFiscalCalenderId) throws SQLException {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, pFiscalCalenderId);
        dbc.addOrderBy(FiscalCalenderDetailDataAccess.PERIOD);

        return FiscalCalenderDetailDataAccess.select(pCon, dbc);
    }

    public FiscalCalenderView createFiscalCalender( Connection pCon,
                                                    int pBusEntityId) throws SQLException {

        FiscalCalenderData fcd = FiscalCalenderData.createValue();
        FiscalCalenderDetailDataVector defaultCalDetails = new FiscalCalenderDetailDataVector();

        fcd.setBusEntityId(pBusEntityId);
        Calendar cal = new GregorianCalendar();
        java.util.Date now = new java.util.Date();
        fcd.setEffDate(now);
        cal.setTime(now);
        fcd.setPeriodCd(RefCodeNames.BUDGET_PERIOD_CD.MONTHLY);
        fcd.setShortDesc("Default Cal");

        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 1, "1/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 2, "2/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 3, "3/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 4, "4/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 5, "5/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 6, "6/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 7, "7/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 8, "8/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 9, "9/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 10, "10/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 11, "11/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 12, "12/1", new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));
        defaultCalDetails.add(new FiscalCalenderDetailData(0, -1, 13, null, new Date(), "Admin Default Cal", new Date(), "Admin Default Cal"));

        return insertFiscalCalender(pCon, new FiscalCalenderView(fcd, defaultCalDetails));
    }


  public FiscalCalenderData setFiscalCalender(
    Connection pCon,
    FiscalCalenderData pFcd )
  {
    FiscalCalenderData fcd = null;
    return fcd;
  }

    public int getCurrentSiteInventoryPeriod(Connection pCon, int pSiteId )
        throws Exception
    {

  // return the current budget period.
  int bp = getAccountBudgetPeriod(pCon, 0, pSiteId,null);
  return bp;
    }

    public int getCurrentSiteBudgetPeriod(Connection pCon, int pSiteId )
        throws Exception
    {
        return getAccountBudgetPeriod(pCon, 0, pSiteId,null);
    }

    public int getCurrentAccountBudgetPeriod(Connection pCon, int pAccountId )
        throws Exception
    {
        return getAccountBudgetPeriod(pCon, pAccountId, 0,null);
    }

    /**
     *Gets the fiscal calender for the passed in bus entity id.  Valid bus entity id types are sites and accounts.
     */
    public FiscalPeriodView getFiscalInfo(Connection pCon, int pBusEntityId)
        throws Exception, DataNotFoundException
    {
        BusEntityData bed = BusEntityDataAccess.select(pCon, pBusEntityId);
        FiscalCalenderView fcv;
        FiscalPeriodView res = FiscalPeriodView.createValue();
        int acctid = pBusEntityId;
        if ( bed.getBusEntityTypeCd().equals
             (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT))
        {
            fcv = getCurrentFiscalCalenderV(pCon, pBusEntityId);
            res.setFiscalCalenderView(fcv);
            res.setCurrentFiscalPeriod
                (getCurrentAccountBudgetPeriod(pCon,pBusEntityId));
        }
        else
        {
            acctid = getAccountForSite(pCon, pBusEntityId);

            fcv = getCurrentFiscalCalenderV(pCon, acctid);
            res.setFiscalCalenderView(fcv);
            res.setCurrentFiscalPeriod
                (getCurrentSiteBudgetPeriod(pCon,pBusEntityId));
        }

        if ( null == res.getFiscalCalenderView()) {
            res.setFiscalCalenderView(createFiscalCalender(
                pCon, acctid )
                );
        }

        res.setBusEntityId(pBusEntityId);
        return res;
    }


    public FiscalPeriodView createFiscalInfo(Connection pCon, int pBusEntityId)
        throws SQLException, DataNotFoundException
    {
        BusEntityData bed = BusEntityDataAccess.select(pCon, pBusEntityId);
        FiscalCalenderView fcv = null;
        FiscalPeriodView res = FiscalPeriodView.createValue();
        if ( bed.getBusEntityTypeCd().equals
             (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT))
        {
            fcv = createFiscalCalender(pCon, pBusEntityId);
            res.setFiscalCalenderView(fcv);
        }
        else
        {
            int acctid = getAccountForSite(pCon, pBusEntityId);
            fcv = createFiscalCalender(pCon, acctid);
            res.setFiscalCalenderView(fcv);
        }

        res.setCurrentFiscalPeriod(1);
        res.setBusEntityId(pBusEntityId);

        log.info("createFiscalInfo, res=" + res);
        return res;
    }

    /**
     * Returns the int budget period for the supplied account.
     * @param pCon an active DB connection
     * @param pAccountId optional either the site or the account must be present
     * @param pSiteId optional either the site or the account must be present
     * @param pDate optional, if null will give the current budget period
     * @return
     * @throws java.sql.SQLException
     */
    public int getAccountBudgetPeriod(Connection pCon,
                                      int pAccountId,
                                      int pSiteId,
                                      java.util.Date pDate) throws Exception {
        try {
            DBCriteria dbc = new DBCriteria();
            if (pAccountId > 0) {
                dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID, pAccountId);
            } else {
                DBCriteria acctReqDBC = new DBCriteria();
                acctReqDBC.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pSiteId);
                acctReqDBC.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                String acctReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID, acctReqDBC);
                dbc.addOneOf(FiscalCalenderDataAccess.BUS_ENTITY_ID, acctReq);
            }

            if (pDate == null) {
                pDate = new java.util.Date();
            }

            dbc.addLessOrEqual(FiscalCalenderDataAccess.EFF_DATE, pDate);
            dbc.addOrderBy(FiscalCalenderDataAccess.EFF_DATE, false);

            FiscalCalenderDataVector fiscalCalDV = FiscalCalenderDataAccess.select(pCon, dbc);

            if (fiscalCalDV.size() == 0) {
                log.info("getAccountBudgetPeriod => no fiscal caledar found.");
                return 0; //no fiscal caledar found
            }

            FiscalCalenderData fiscalCalD = (FiscalCalenderData) fiscalCalDV.get(0);
            log.debug("getAccountBudgetPeriod => Fiscal Calendar: "+fiscalCalD);

            dbc = new DBCriteria();
            dbc.addEqualTo(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, fiscalCalD.getFiscalCalenderId());
            dbc.addOrderBy(FiscalCalenderDetailDataAccess.PERIOD);

            FiscalCalenderDetailDataVector fiscalCalDetails = FiscalCalenderDetailDataAccess.select(pCon, dbc);

            java.util.Date effDate = fiscalCalD.getEffDate();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
            String yearStr = yearSdf.format(effDate);
            int year = Integer.parseInt(yearStr);
            if (fiscalCalD.getFiscalYear() == 0) {
                yearStr = yearSdf.format(pDate);
            }

            int prevMM = 0;
            int retVal = 0;
            int i = 0;
            for (Object oFiscalDetail : fiscalCalDetails) {

                i++;

                FiscalCalenderDetailData fiscalDetail = (FiscalCalenderDetailData) oFiscalDetail;

                String mmdd = fiscalDetail.getMmdd();
                if (Utility.isSet(mmdd)) {

                    String mmS = mmdd.substring(0, mmdd.indexOf("/"));

                    int mm = Integer.parseInt(mmS);

                    if (i > 1) {

                        if (mm < prevMM) {
                            year++;
                            yearStr = "" + year;
                        }

                        String dateS = mmdd + "/" + yearStr;
                        java.util.Date date = sdf.parse(dateS);

                        if (date.compareTo(pDate) > 0) {
                            log.debug("getAccountBudgetPeriod => retVal: "+retVal);
                            return retVal;
                        } else {
                            retVal = i;
                        }
                    } else {

                        String dateS = mmdd + "/" + yearStr;
                        java.util.Date date = sdf.parse(dateS);

                        if (date.compareTo(pDate) > 0) {
                            log.debug("getAccountBudgetPeriod => Wrong calendar.");
                            return 0; // wrong calender
                        } else {
                            retVal = i;
                        }
                    }
                    prevMM = mm;
                } else {
                    if (i == 1) {
                        throw new Exception("Wrong format of periods.The first period value must be set.");
                    }
                    prevMM = 0;
                }
            }

            log.debug("getAccountBudgetPeriod => retVal: " + retVal);
            return retVal;
        }
        catch (java.text.ParseException exc) {
            exc.printStackTrace();
        }
        return 0;

    }

     /**
      *
      *Returns an IdVector of the ids that this bus entity is
      *associated with based off the association type.  Returns
      *parents i.e. everything upstream of the queried id.  So all the
      *accounts that a site belongs to.
      */
     public static IdVector getBusEntityAssoc2Ids(Connection pCon, int pBusEntityId, String pBusEntityAssocCd)
     throws java.sql.SQLException{
         DBCriteria crit = new DBCriteria();
         crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pBusEntityId);
         crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,pBusEntityAssocCd);
         return BusEntityAssocDataAccess.selectIdOnly(pCon, BusEntityAssocDataAccess.BUS_ENTITY2_ID, crit);
     }


    // Contact information
    public static ContactViewVector getBusEntityContacts(Connection conn,
              int pBusEntityId)
  throws Exception {
  return getContactsCollection( conn,pBusEntityId, 0);
    }

    public static ContactViewVector getAddressContacts(Connection conn,
                   int pAddressId)
  throws Exception {
  return getContactsCollection( conn,0, pAddressId);
    }

    private static ContactViewVector getContactsCollection(Connection conn,
                int pBusEntityId,
                int pAddressId)
  throws Exception {

  ContactViewVector contactVwV = new ContactViewVector();

  DBCriteria dbc = new DBCriteria();
  if (pBusEntityId > 0 ) {
      dbc.addEqualTo(ContactDataAccess.BUS_ENTITY_ID,pBusEntityId);
  }
  if (pAddressId > 0 ) {
      dbc.addEqualTo(ContactDataAccess.ADDRESS_ID,pAddressId);
  }

        String contactReq =
          ContactDataAccess.getSqlSelectIdOnly(ContactDataAccess.CONTACT_ID,dbc);
        dbc.addOrderBy(ContactDataAccess.CONTACT_TYPE_CD);
        ContactDataVector contactDV = ContactDataAccess.select(conn,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(PhoneDataAccess.CONTACT_ID,contactReq);
        dbc.addOrderBy(PhoneDataAccess.CONTACT_ID);
        PhoneDataVector phoneDV = PhoneDataAccess.select(conn,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(EmailDataAccess.CONTACT_ID,contactReq);
        dbc.addOrderBy(EmailDataAccess.CONTACT_ID);
        EmailDataVector emailDV = EmailDataAccess.select(conn,dbc);

        Iterator contactIter = contactDV.iterator();
        while(contactIter.hasNext()) {
          ContactData cD = (ContactData) contactIter.next();
          int contactId = cD.getContactId();
          ContactView cVw = ContactView.createValue();
          contactVwV.add(cVw);
          cVw.setBusEntityId(pBusEntityId);
          cVw.setAddressId(pAddressId);
          cVw.setContactId(contactId);
          cVw.setContactTypeCd(cD.getContactTypeCd());
          cVw.setFirstName(cD.getFirstName());
          cVw.setLastName(cD.getLastName());
          Iterator phoneIter = phoneDV.iterator();
          while(phoneIter.hasNext()) {
            PhoneData pD = (PhoneData) phoneIter.next();
            int cId = pD.getContactId();
            if(cId>contactId) break;
            if(cId == contactId) {
              String status =  pD.getPhoneStatusCd();
              if(RefCodeNames.PHONE_STATUS_CD.ACTIVE.equals(status)) {
                String type = pD.getPhoneTypeCd();
                if(RefCodeNames.PHONE_TYPE_CD.PHONE.equals(type)){
                  if(cVw.getPhone()!=null && cVw.getPhone().trim().length()>0) {
                    cVw.setPhone(cVw.getPhone()+", "+pD.getPhoneNum());
                  } else {
                    cVw.setPhone(pD.getPhoneNum());
                  }
                } else if(RefCodeNames.PHONE_TYPE_CD.MOBILE.equals(type)){
                  if(cVw.getCellPhone()!=null && cVw.getCellPhone().trim().length()>0) {
                    cVw.setCellPhone(cVw.getCellPhone()+", "+pD.getPhoneNum());
                  } else {
                    cVw.setCellPhone(pD.getPhoneNum());
                  }
                } else if(RefCodeNames.PHONE_TYPE_CD.FAX.equals(type)){
                  if(cVw.getFax() != null && cVw.getFax().trim().length()>0) {
                    cVw.setFax(cVw.getFax()+", "+pD.getPhoneNum());
                  } else {
                    cVw.setFax(pD.getPhoneNum());
                  }
                }
              }
            }
          }
          Iterator emailIter = emailDV.iterator();
          while(emailIter.hasNext()) {
            EmailData eD = (EmailData) emailIter.next();
            int cId = eD.getContactId();
            if(cId>contactId) break;
            if(cId == contactId) {
              String status =  eD.getEmailStatusCd();
              if(RefCodeNames.EMAIL_STATUS_CD.ACTIVE.equals(status)) {
                String type = eD.getEmailTypeCd();
                if(RefCodeNames.EMAIL_TYPE_CD.DEFAULT.equals(type)){
                  if(cVw.getEmail()!=null && cVw.getEmail().trim().length()>0) {
                    cVw.setEmail(cVw.getEmail()+", "+eD.getEmailAddress());
                  } else {
                    cVw.setEmail(eD.getEmailAddress());
                  }
                }
              }
            }
          }
        }


    return contactVwV;
    }

    public static ContactView updateContact(Connection conn,
              ContactView pContactView,
              String pUser)
  throws Exception {
        int contactId = pContactView.getContactId();
        ContactData contactD = null;
  DBCriteria dbc = null;

        if(contactId>0) {
          dbc = new DBCriteria();
          dbc.addEqualTo(PhoneDataAccess.CONTACT_ID,contactId);
          PhoneDataAccess.remove(conn,dbc);

          dbc = new DBCriteria();
          dbc.addEqualTo(EmailDataAccess.CONTACT_ID,contactId);
          EmailDataAccess.remove(conn,dbc);

          try {
            contactD = ContactDataAccess.select(conn,contactId);
          }catch(DataNotFoundException exc) {
            contactId = 0;
          }
          contactD.setBusEntityId(pContactView.getBusEntityId());
          contactD.setModBy(pUser);
          contactD.setFirstName(pContactView.getFirstName());
          contactD.setLastName(pContactView.getLastName());
          contactD.setContactTypeCd(pContactView.getContactTypeCd());
          ContactDataAccess.update(conn, contactD);
        }
        if(contactId==0) {
          contactD = ContactData.createValue();
    log.debug("   pContactView=" + pContactView);
    if ( pContactView.getBusEntityId() > 0 ) {
        contactD.setBusEntityId(pContactView.getBusEntityId());
    } else {
        contactD.setAddressId(pContactView.getAddressId());
    }
          contactD.setModBy(pUser);
          contactD.setAddBy(pUser);
          contactD.setFirstName(pContactView.getFirstName());
          contactD.setLastName(pContactView.getLastName());
          contactD.setContactTypeCd(pContactView.getContactTypeCd());
          contactD = ContactDataAccess.insert(conn, contactD);
          contactId = contactD.getContactId();
          pContactView.setContactId(contactId);
        }
        String phone = pContactView.getPhone();
        if(phone!=null && phone.trim().length()>0) {
          PhoneData pD = PhoneData.createValue();
          pD.setContactId(contactId);
          pD.setBusEntityId(pContactView.getBusEntityId());
          pD.setPhoneNum(phone);
          pD.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
          pD.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
          pD.setPrimaryInd(false);
          pD.setAddBy(pUser);
          pD.setModBy(pUser);
          PhoneDataAccess.insert(conn, pD);
        }

        String cellPhone = pContactView.getCellPhone();
        if(cellPhone!=null && cellPhone.trim().length()>0) {
          PhoneData pD = PhoneData.createValue();
          pD.setContactId(contactId);
          pD.setBusEntityId(pContactView.getBusEntityId());
          pD.setPhoneNum(cellPhone);
          pD.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.MOBILE);
          pD.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
          pD.setPrimaryInd(false);
          pD.setAddBy(pUser);
          pD.setModBy(pUser);
          PhoneDataAccess.insert(conn, pD);
        }

        String fax = pContactView.getFax();
        if(fax!=null && fax.trim().length()>0) {
          PhoneData pD = PhoneData.createValue();
          pD.setContactId(contactId);
          pD.setBusEntityId(pContactView.getBusEntityId());
          pD.setPhoneNum(fax);
          pD.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
          pD.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
          pD.setPrimaryInd(false);
          pD.setAddBy(pUser);
          pD.setModBy(pUser);
          PhoneDataAccess.insert(conn, pD);
        }

        String email = pContactView.getEmail();
        if(email!=null && email.trim().length()>0) {
          EmailData eD = EmailData.createValue();
          eD.setContactId(contactId);
          eD.setBusEntityId(pContactView.getBusEntityId());
          eD.setEmailAddress(email);
          eD.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
          eD.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
          eD.setPrimaryInd(false);
          eD.setAddBy(pUser);
          eD.setModBy(pUser);
          EmailDataAccess.insert(conn, eD);
        }
        return pContactView;

    }

    public static void deleteContact(Connection conn,
             int pContactId)
  throws Exception {

  DBCriteria dbc = null;
        dbc = new DBCriteria();
        dbc.addEqualTo(PhoneDataAccess.CONTACT_ID,pContactId);
        PhoneDataAccess.remove(conn,dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(EmailDataAccess.CONTACT_ID,pContactId);
        EmailDataAccess.remove(conn,dbc);

        ContactDataAccess.remove(conn,pContactId);
    }


    


    /**
     *Returns the store type of the supplied store id.
     */
    public static String getStoreTypeCd(Connection pConn, int pStoreId) throws RemoteException,DataNotFoundException{
        if(pStoreId != 0){
            PropertyUtil pru = new PropertyUtil(pConn);
            return pru.fetchValue(0, pStoreId, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
        }
        throw new DataNotFoundException("No store type code for store id 0");
    }

    /**
     *Returns the "primary" address for the store
     */
    public static AddressData getPrimaryStoreAddressData(Connection pConn, int pStoreId) throws SQLException,DataNotFoundException{
        if(pStoreId != 0){
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, pStoreId);
            AddressDataVector addrs = AddressDataAccess.select(pConn,crit);
            if(addrs.size() == 1){
                return (AddressData) addrs.get(0);
            }else if(addrs.size() > 1){
                Iterator it = addrs.iterator();
                while(it.hasNext()){
                    AddressData a = (AddressData) it.next();
                    if(RefCodeNames.ADDRESS_STATUS_CD.INACTIVE.equals(a.getAddressStatusCd())){
                        it.remove();
                    }
                }
                if(addrs.size() == 1){
                    return (AddressData) addrs.get(0);
                }
                throw new DataNotFoundException("Multiple address for store id "+pStoreId + " found ("+addrs.size()+")");
            }
        }
        throw new DataNotFoundException("No address for store id "+pStoreId);
    }

    public static boolean accountHasProp(Connection pCon,
           String pAccountSql,
           String pPropShortDesc )
       throws Exception {
  String sql = " select count(distinct short_desc) "
      + " from clw_property p "
      + " join clw_bus_entity_assoc ba on ba.bus_entity1_id = "
      + " p.bus_entity_id and ba.bus_entity2_id in ("
      + pAccountSql + ")"
      + " and short_desc = '" + pPropShortDesc + "'";
        Statement stmt  = null;
        ResultSet rs = null;
  boolean hasProp = false;
        try {
            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                if ( rs.getInt(1) > 0 ) {
        hasProp = true;
    }
            }
        }
        finally{
            if (null != rs ) {            rs.close(); }
            if ( null != stmt ) {            stmt.close(); }
        }

  log.info( "accountHasProp, sql=" + sql
          + " hasProp=" + hasProp );
  return hasProp;

    }

    public static BusEntityAssocData mkBusEntityAssoc
  (Connection pCon,
   int pBusEntity1,	 int pBusEntity2,
   String pAssocType, String pUser )
  throws Exception {

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo
    (BusEntityAssocDataAccess.BUS_ENTITY1_ID, pBusEntity1);
            crit.addEqualTo
    (BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntity2);
            crit.addEqualTo
    (BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
     pAssocType);

            BusEntityAssocDataVector v =
    BusEntityAssocDataAccess.select(pCon,crit);

      if ( v == null || v.size() == 0 ) {
    BusEntityAssocData newassoc =
        BusEntityAssocData.createValue();
    newassoc.setBusEntity1Id(pBusEntity1);
    newassoc.setBusEntity2Id(pBusEntity2);
    newassoc.setBusEntityAssocCd(pAssocType);
    newassoc.setAddBy(pUser);
    newassoc.setModBy(pUser);

    return BusEntityAssocDataAccess.insert(pCon,newassoc);
      }

      return (BusEntityAssocData)v.get(0);
    }

    public static PropertyData getAccountSpecificRights
  (Connection conn, int pUserId, int pAccountId)
  throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                       RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS
                       );
        dbc.addOrderBy(PropertyDataAccess.PROPERTY_ID, true);

        PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);
        PropertyData pd = null;
        if ( pDV != null && pDV.size() <= 0 ) {
            pd = PropertyData.createValue();
            pd.setUserId(pUserId);
            pd.setBusEntityId(pAccountId);
            pd.setPropertyTypeCd
                (RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS);
            pd.setPropertyStatusCd
                (RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            pd.setShortDesc(pd.getPropertyTypeCd());
        }
        else {
            pd = (PropertyData)pDV.get(0);
        }

        return pd;

    }


    public static PropertyDataVector getAccountSpecificRights
  (Connection conn, int pUserId, IdVector pAccountIds)
  throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
        dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, pAccountIds);
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                       RefCodeNames.PROPERTY_TYPE_CD.USER_ACCOUNT_RIGHTS
                       );
        dbc.addOrderBy(PropertyDataAccess.PROPERTY_ID, true);

        PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);
        return pDV;

    }


    public static void updateUserAccountRights(Connection conn ,
                 int pUserId,
                 int pAccountId,
                 String pRights,
                 String pUpdateUserName )
        throws Exception {

  if ( pAccountId <= 0 ) {
      // Update the base rights for the user
      // since no account was specified.
      UserData ud = UserDataAccess.select(conn,pUserId);
      ud.setUserRoleCd(pRights);
      if ( pRights.indexOf
     (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER) >= 0
     ) {
    ud.setWorkflowRoleCd
        (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
      } else {
    ud.setWorkflowRoleCd
        (RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
      }
      ud.setModBy(pUpdateUserName);
      UserDataAccess.update(conn, ud);
      return;
  }

  PropertyData pd = getAccountSpecificRights
      (conn, pUserId, pAccountId);
  pd.setValue(pRights);
  pd.setModBy(pUpdateUserName);
  if ( pd.getPropertyId() <= 0) {
      pd.setAddBy(pUpdateUserName);
      PropertyDataAccess.insert(conn, pd);
  }
  else {
      PropertyDataAccess.update(conn, pd);
  }
    }


    /**
     * Returns the catalog id for the supplied store id
     * @param con an active database connection
     * @param pStoreId the store id for which the catalog will be returned
     * @return the store catalog id or 0 if none were found
     * @throws SQLException
     */
    public static int getStoreCatalogId(Connection con, int pStoreId) throws SQLException{
      DBCriteria dbc;
      int masterId = 0;
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
        String catalogAssocReq =
            CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAssocReq);
        IdVector idv = CatalogDataAccess.selectIdOnly(con, CatalogDataAccess.CATALOG_ID, dbc);
        if (idv.size() > 0) {
            masterId = ((Integer) idv.get(0)).intValue();
        }else {
            dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SYSTEM);
            dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAssocReq);
            idv = CatalogDataAccess.selectIdOnly(con, CatalogDataAccess.CATALOG_ID, dbc);
            if (idv.size() > 0) {
                masterId = ((Integer) idv.get(0)).intValue();
            }
        }
  return masterId;
    }

    public ShoppingControlDataVector getAccountShoppingControls
            (Connection conn, int pBusEntityId, IdVector acctItems)
    throws Exception    {
        ShoppingControlDataVector scdv = new ShoppingControlDataVector();
        for ( int i = 0; null != acctItems && i < acctItems.size(); i++ ) {
            ShoppingControlData scd = ShoppingControlData.createValue();
            scd.setItemId(((Integer)acctItems.get(i)).intValue());
            scd.setAccountId(pBusEntityId);
            scdv.add(scd);
        }

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, pBusEntityId);
		dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID,0);
		dbc.addOneOf(ShoppingControlDataAccess.ITEM_ID, acctItems);

        ShoppingControlDataVector scdvInDb =
                ShoppingControlDataAccess.select(conn, dbc);

        for ( int j = 0; null != scdvInDb && j < scdvInDb.size(); j++) {
            ShoppingControlData scd = (ShoppingControlData)scdvInDb.get(j);
            int thisItemId = scd.getItemId();
            for ( int i = 0; null != scdv && i < scdv.size(); i++ ) {
                ShoppingControlData scd2 = (ShoppingControlData)scdv.get(i);
                if ( scd2.getItemId() == thisItemId ) {
                    scdv.set(i, scd);
                    break;
                }
            }
        }
        return scdv;
    }

    public CatalogDataVector getCatalogsCollectionByBusEntity(
            Connection con, int pBusEntityId, String pCatalogTypeCd)
            throws Exception {

        CatalogDataVector catalogDV = null;

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
        if ( null != pCatalogTypeCd && pCatalogTypeCd.length() > 0) {
            String sql = " ( select catalog_id from clw_catalog where "
                    + " catalog_type_cd = '" + pCatalogTypeCd + "' )";
            dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, sql);
        }
        IdVector catids = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CATALOG_ID, dbc);
        catalogDV = CatalogDataAccess.select(con, catids);

        return catalogDV;
    }

    public FiscalCalenderViewVector getFiscalCalenders(Connection pCon,int pBusEntityId)
        throws SQLException
    {
  return getFiscalCalenders(pCon,pBusEntityId,0);
    }

    public FiscalCalenderViewVector getFiscalCalenders(Connection pCon,int pBusEntityId,
                   int pFiscalYear )
        throws SQLException
    {
  return getFiscalCalenders( pCon, pBusEntityId,
           pFiscalYear, false );


    }

    public FiscalCalenderViewVector getFiscalCalenders(Connection pCon,
                                                       int pBusEntityId,
                                                       int pFiscalYear,
                                                       boolean forceZeroYear) throws SQLException {

        FiscalCalenderViewVector result = new FiscalCalenderViewVector();

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID, pBusEntityId);

        if (pFiscalYear > 0 || forceZeroYear) {
            dbc.addEqualTo(FiscalCalenderDataAccess.FISCAL_YEAR, pFiscalYear);
        }

        boolean ascSortOrder = false;
        dbc.addOrderBy(FiscalCalenderDataAccess.FISCAL_YEAR, ascSortOrder);
        dbc.addOrderBy(FiscalCalenderDataAccess.FISCAL_CALENDER_ID, ascSortOrder);

        log.info("\n\n" + FiscalCalenderDataAccess.getSqlSelectIdOnly("*", dbc));

        FiscalCalenderDataVector fiscalCalenders = FiscalCalenderDataAccess.select(pCon, dbc);

        IdVector fiscalCalenderIds = Utility.toIdVector(fiscalCalenders);
        HashMap<Integer, FiscalCalenderDetailDataVector> fiscalCalenderDetailMap = new HashMap<Integer, FiscalCalenderDetailDataVector>();
        if (!fiscalCalenderIds.isEmpty()) {

            dbc = new DBCriteria();
            dbc.addOneOf(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, fiscalCalenderIds);
            dbc.addOrderBy(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, ascSortOrder);
            dbc.addOrderBy(FiscalCalenderDetailDataAccess.PERIOD);

            FiscalCalenderDetailDataVector fiscalCalendersDetails = FiscalCalenderDetailDataAccess.select(pCon, dbc);
            Iterator it = fiscalCalendersDetails.iterator();
            while (it.hasNext()) {
                FiscalCalenderDetailData fiscalCalenderDetail = (FiscalCalenderDetailData) it.next();
                int fiscalCalId = fiscalCalenderDetail.getFiscalCalenderId();
                FiscalCalenderDetailDataVector fiscalCalenderDetails = fiscalCalenderDetailMap.get(fiscalCalId);
                if (fiscalCalenderDetails == null) {
                    fiscalCalenderDetails = new FiscalCalenderDetailDataVector();
                    fiscalCalenderDetailMap.put(fiscalCalenderDetail.getFiscalCalenderId(), fiscalCalenderDetails);
                }
                fiscalCalenderDetails.add(fiscalCalenderDetail);
            }
        }

        Iterator it = fiscalCalenders.iterator();
        while (it.hasNext()) {
            FiscalCalenderData fcd = (FiscalCalenderData) it.next();
            FiscalCalenderDetailDataVector details = fiscalCalenderDetailMap.get(fcd.getFiscalCalenderId());
            if (details == null) {
                details = new FiscalCalenderDetailDataVector();
            }
            result.add(new FiscalCalenderView(fcd, details));
        }

        return result;

    }


    public FiscalCalenderView updateFiscalCal(Connection pCon, FiscalCalenderView pInCal) throws SQLException {

        FiscalCalenderViewVector fcdv = getFiscalCalenders(pCon,
                pInCal.getFiscalCalender().getBusEntityId(),
                pInCal.getFiscalCalender().getFiscalYear(),
                true);

        if (null == fcdv || fcdv.size() == 0) {
            // no calender found
            pInCal.getFiscalCalender().setAddBy(pInCal.getFiscalCalender().getModBy());
            return insertFiscalCalender(pCon, pInCal);
        }

        FiscalCalenderView calFound = (FiscalCalenderView) fcdv.get(0);

        return updateFiscalCalender(pCon, pInCal,calFound);

    }

    private FiscalCalenderView updateFiscalCalender(Connection pCon, FiscalCalenderView pInCal, FiscalCalenderView pCalFound) throws SQLException {

        log.info("updateFiscalCalender => BEGIN");

        FiscalCalenderData calData = pInCal.getFiscalCalender();

        pCalFound.getFiscalCalender().setEffDate(calData.getEffDate());

        FiscalCalenderDataAccess.update(pCon, calData);

        Iterator existsDetailsIt;
        Iterator newDetailsIt;
        FiscalCalenderDetailDataVector existsDetails = pCalFound.getFiscalCalenderDetails();
        FiscalCalenderDetailDataVector newDetails = pInCal.getFiscalCalenderDetails();

        existsDetailsIt = existsDetails.iterator();
        while (existsDetailsIt.hasNext()) {
            FiscalCalenderDetailData existsDetailData = (FiscalCalenderDetailData) existsDetailsIt.next();
            newDetailsIt = newDetails.iterator();
            boolean found = false;
            while (newDetailsIt.hasNext()) {
                FiscalCalenderDetailData newDetailData = (FiscalCalenderDetailData) newDetailsIt.next();
                if (existsDetailData.getPeriod() == newDetailData.getPeriod()) {
                    newDetailData.setFiscalCalenderDetailId(existsDetailData.getFiscalCalenderDetailId());
                    found = true;
                    break;
                }
            }
            if (found) {
                existsDetailsIt.remove();
            }
        }

        existsDetailsIt = existsDetails.iterator();
        while (existsDetailsIt.hasNext()) {
            FiscalCalenderDetailData existsDetailData = (FiscalCalenderDetailData) existsDetailsIt.next();
            FiscalCalenderDetailDataAccess.remove(pCon, existsDetailData.getFiscalCalenderDetailId());
        }

        newDetailsIt = newDetails.iterator();
        while (newDetailsIt.hasNext()) {
            FiscalCalenderDetailData newDetailData = (FiscalCalenderDetailData) newDetailsIt.next();
            newDetailData.setFiscalCalenderId(calData.getFiscalCalenderId());
            if (newDetailData.getFiscalCalenderDetailId() > 0) {
                newDetailData.setModBy(calData.getModBy());
                FiscalCalenderDetailDataAccess.update(pCon, newDetailData);
            } else {
                newDetailData.setAddBy(calData.getAddBy());
                newDetailData.setModBy(calData.getModBy());
                newDetailData = FiscalCalenderDetailDataAccess.insert(pCon, newDetailData);
            }
        }

        pInCal.setFiscalCalender(calData);
        pInCal.setFiscalCalenderDetails(newDetails);

        log.info("updateFiscalCalender => END.pInCal: "+pInCal);

        return pInCal;

    }

    private FiscalCalenderView insertFiscalCalender(Connection pCon, FiscalCalenderView pCal) throws SQLException {

        log.info("insertFiscalCalender => BEGIN.pCal: "+pCal);

        FiscalCalenderData calData = pCal.getFiscalCalender();

        calData = FiscalCalenderDataAccess.insert(pCon, calData);
        pCal.setFiscalCalender(calData);

        Iterator it = pCal.getFiscalCalenderDetails().iterator();
        while (it.hasNext()) {
            FiscalCalenderDetailData detail = (FiscalCalenderDetailData) it.next();
            detail.setFiscalCalenderId(calData.getFiscalCalenderId());
            detail.setAddBy(calData.getAddBy());
            detail.setModBy(calData.getModBy());
            detail = FiscalCalenderDetailDataAccess.insert(pCon, detail);
        }

        log.info("insertFiscalCalender => END.pInCal: "+pCal);

        return  pCal;
    }

    /**
     * Returns a populated store data object for the supplied store id
     * @param conn an active sql connection
     * @param storeId the store id to fetch
     * @return a populated storeData object
     * @throws SQLException on any db error
     * @throws DataNotFoundException if the store id could not be found
     */
    public static StoreData getStoreData(Connection conn, int storeId)
    throws SQLException, DataNotFoundException{
      return getStoreData(conn, BusEntityDataAccess.select(conn,storeId));
    }

    /**
     * Returns a populated store data object for the supplied store bus entity data
     * @param conn an active sql connection
     * @param pBusEntity the populated busEntityData object for the store
     * @return a populated storeData object
     * @throws SQLException on any db error
     * @throws DataNotFoundException if the store id could not be found
     */
	public static StoreData getStoreData(Connection conn, BusEntityData pBusEntity) throws SQLException {
		try{
		// log.info("**********SVC: pBusEntity = " + pBusEntity);

		PropertyData prefix = null;
		// PropertyData prefixNew = PropertyData.createValue();
		PropertyData storeType = null;
		EmailData customerServiceEmail = null;
		EmailData contactUsEmail = null;
		EmailData defaultEmail = null;
		AddressData primaryAddress = null;
		PhoneData primaryPhone = null;
		PhoneData primaryFax = null;
		EmailData primaryEmail = null;
		PropertyData storePrimaryWebAddress = null;
		PropertyData storeBusinessName = null;
		PropertyData callHours = null;
		PropertyDataVector miscProperties = null;
		CountryPropertyDataVector countryProperties = null;
		String evenRowColor = null;
		String oddRowColor = null;
		boolean lDefault = false;
		String pendingOrderNotification = null;
		// get store address
		DBCriteria addressCrit = new DBCriteria();
		addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, pBusEntity.getBusEntityId());
		addressCrit.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
		AddressDataVector addressVec = AddressDataAccess.select(conn, addressCrit);
		// if more than one primary address, for now we don't care
		if (addressVec.size() > 0) {
			primaryAddress = (AddressData) addressVec.get(0);
		}

		// get related email addresses
		DBCriteria emailCrit = new DBCriteria();
		emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, pBusEntity.getBusEntityId());
		EmailDataVector emailVec = EmailDataAccess.select(conn, emailCrit);
		Iterator emailIter = emailVec.iterator();
		while (emailIter.hasNext()) {
			EmailData email = (EmailData) emailIter.next();
			String emailType = email.getEmailTypeCd();
			if (emailType.compareTo(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT) == 0) {
				primaryEmail = email;
			} else if (emailType.compareTo(RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE) == 0) {
				customerServiceEmail = email;
			} else if (emailType.compareTo(RefCodeNames.EMAIL_TYPE_CD.CONTACT_US) == 0) {
				contactUsEmail = email;
			} else if (emailType.compareTo(RefCodeNames.EMAIL_TYPE_CD.DEFAULT) == 0) {
				defaultEmail = email;
			} else {
				// ignore - unidentified email
			}
		}

		// get related phones
		DBCriteria phoneCrit = new DBCriteria();
		phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID, pBusEntity.getBusEntityId());
		phoneCrit.addEqualTo(PhoneDataAccess.PRIMARY_IND, true);
		PhoneDataVector phoneVec = PhoneDataAccess.select(conn, phoneCrit);
		Iterator phoneIter = phoneVec.iterator();
		while (phoneIter.hasNext()) {
			PhoneData phone = (PhoneData) phoneIter.next();
			String phoneType = phone.getPhoneTypeCd();
			if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.PHONE) == 0) {
				primaryPhone = phone;
			} else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.FAX) == 0) {
				primaryFax = phone;
			} else {
				// ignore - unidentified phone
			}
		}

		// get related properties
		DBCriteria propCrit = new DBCriteria();
		propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pBusEntity.getBusEntityId());
		PropertyDataVector propVec = PropertyDataAccess.select(conn, propCrit);
		miscProperties = new PropertyDataVector();
		Iterator propIter = propVec.iterator();
		while (propIter.hasNext()) {
			PropertyData prop = (PropertyData) propIter.next();
			String propType = prop.getPropertyTypeCd();
			if (propType.compareTo(RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_CODE) == 0) {
				prefix = prop;
				// } else if
				// (propType.compareTo(RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_NEW)
				// == 0) {
				// prefixNew = prop;
			} else if (propType.compareTo(RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE) == 0) {
				storeType = prop;
			} else if (propType.compareTo(RefCodeNames.PROPERTY_TYPE_CD.STORE_BUSINESS_NAME) == 0) {
				storeBusinessName = prop;
			} else if (propType.compareTo(RefCodeNames.PROPERTY_TYPE_CD.CALL_HOURS) == 0) {
				callHours = prop;
			} else if (propType.compareTo(RefCodeNames.PROPERTY_TYPE_CD.STORE_PRIMARY_WEB_ADDRESS) == 0) {
				storePrimaryWebAddress = prop;
			} else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.EVEN_ROW_COLOR)) {
				evenRowColor = prop.getValue();
			} else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.ODD_ROW_COLOR)) {
				oddRowColor = prop.getValue();
			} else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.DEFAULT)) {
				lDefault = Utility.isTrue(prop.getValue());
			} else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION)) {
				pendingOrderNotification = prop.getValue();
			} else {
				miscProperties.add(prop);
			}
		}

		// get country properties

		// log.info("********SVC: miscProperties = " + miscProperties);

		DBCriteria countryCrit = new DBCriteria();
		if(primaryAddress!= null && Utility.isSet(primaryAddress.getCountryCd()))
			countryCrit.addEqualTo(CountryDataAccess.SHORT_DESC, primaryAddress.getCountryCd());
		IdVector country = CountryDataAccess.selectIdOnly(conn, countryCrit);
		if (country != null && country.size() > 0) {
			int countryId = ((Integer) country.get(0)).intValue();
			DBCriteria countryPropCrit = new DBCriteria();
			countryPropCrit.addEqualTo(CountryPropertyDataAccess.COUNTRY_ID, countryId);
			countryProperties = CountryPropertyDataAccess.select(conn, countryPropCrit);
		}

		StoreData storeD = new StoreData(pBusEntity, prefix, storeType, customerServiceEmail, contactUsEmail, defaultEmail, primaryAddress, primaryPhone,
				primaryFax, primaryEmail, storePrimaryWebAddress, storeBusinessName, callHours, miscProperties, evenRowColor, oddRowColor, countryProperties,
				pendingOrderNotification);
		// log.info("**********SVC: storeD_1 = " + storeD);
		// storeD.setPrefixNew(prefixNew);
		// log.info("**********SVC: storeD_2 = " + storeD);

		return storeD;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new SQLException();
		}catch(Exception e){
			e.printStackTrace();
			throw new SQLException();
		}
	}


    /**
     * Creates a populated ApplicationDomainNameData object using the pDomainBusEntity as it's source
     * @param pCon a valid connection
     * @param pDomainBusEntity a populated BusEntityData.  Assumed that it is a Domain_name type
     * @return the populated ApplicationDomainNameData object
     * @throws SQLException if an error occured when communicating with the database
     */
    public static ApplicationDomainNameData getApplicationDomainNameData(Connection pCon, BusEntityData pDomainBusEntity)
    throws SQLException{

      ApplicationDomainNameData domain = ApplicationDomainNameData.createValue();
      domain.setApplicationDomainName(pDomainBusEntity);

        DBCriteria crit = new DBCriteria();
        String beTab = BusEntityDataAccess.CLW_BUS_ENTITY;
        String beaTab = BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC;
        crit.addJoinTableEqualTo(beaTab,BusEntityAssocDataAccess.BUS_ENTITY2_ID,pDomainBusEntity.getBusEntityId());
        crit.addJoinCondition(beaTab,BusEntityAssocDataAccess.BUS_ENTITY1_ID,beTab,BusEntityDataAccess.BUS_ENTITY_ID);
        crit.addJoinTableEqualTo(beaTab,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.DEFAULT_STORE_OF_DOMAIN);
        crit.addJoinTableEqualTo(beTab,BusEntityDataAccess.BUS_ENTITY_STATUS_CD,RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        crit.addJoinTableEqualTo(beTab,BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        BusEntityDataVector stores = new BusEntityDataVector();
        JoinDataAccess.selectTableInto(new BusEntityDataAccess(),stores,pCon,crit,1);
        if(stores != null && !stores.isEmpty()){
          domain.setDefaultStore((BusEntityData) stores.get(0));
        }
        crit = new DBCriteria();
        crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pDomainBusEntity.getBusEntityId());
        PropertyDataVector props = PropertyDataAccess.select(pCon,crit);
        domain.setProperties(props);
        return domain;
    }

    public static CacheKey getSiteCacheKey(int pSiteId) {
        PairViewVector pairs = new PairViewVector();
        pairs.add(new PairView(BusEntityDataAccess.BUS_ENTITY_ID, pSiteId));
        return new CacheKey("SiteData", pairs);
    }

    public static CacheKey getStoreCacheKey(int pStoreId) {
        PairViewVector pairs = new PairViewVector();
        pairs.add(new PairView(BusEntityDataAccess.BUS_ENTITY_ID, pStoreId));
        return new CacheKey("StoreData", pairs);
    }

    public static CacheKey getAccountCacheKey(int pAccountId) {
        PairViewVector pairs = new PairViewVector();
        pairs.add(new PairView(BusEntityDataAccess.BUS_ENTITY_ID, pAccountId));
        return new CacheKey("AccountData", pairs);
    }


    public static SiteData getSiteFromCache(int pSiteId) throws Exception {

        log.info("getSiteFromCache => BEGIN.");

        SiteData site = null;

        try {

            CachecosManager cacheManager = Cachecos.getCachecosManager();

            if (cacheManager != null && cacheManager.isStarted()) {
                CacheKey cacheKey;
                try {
                    cacheKey = BusEntityDAO.getSiteCacheKey(pSiteId);
                    site = (SiteData) cacheManager.get(cacheKey);
                    if (site != null) {
                        log.info("getSiteFromCache => Object found in cache with key " + cacheKey);
                    } else {
                        log.info("getSiteFromCache => Object not found in cache. Key: " + cacheKey);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (site == null) {
                log.info("getSiteFromCache => Gets object from DB.");
                site = APIAccess.getAPIAccess().getSiteAPI().getSite(pSiteId, 0);
                if (cacheManager != null && cacheManager.isStarted()) {
                    try {
                        CacheKey cacheKey = BusEntityDAO.getSiteCacheKey(pSiteId);
                        cacheManager.put(cacheKey, site, new SiteDataMeta(site));
                        log.info("getSiteFromCache => Object has been added to cache. Key: " + cacheKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            log.info("getSiteFromCache => END.");
            return site;
        } catch (DataNotFoundException exc) {
            log.info("getSiteFromCache => Site not found.SiteId: " + pSiteId);
            return null;
        }
    }


    public static AccountData getAccountFromCache(int pAccountId) throws Exception {

        log.info("getAccountFromCache => BEGIN.");
        AccountData account = null;

        try {

            CachecosManager cacheManager = Cachecos.getCachecosManager();

            if (cacheManager != null && cacheManager.isStarted()) {
                try {
                    CacheKey cacheKey = BusEntityDAO.getAccountCacheKey(pAccountId);
                    account = (AccountData) cacheManager.get(cacheKey);
                    if (account != null) {
                        log.info("getAccountFromCache => Object found in cache with key " + cacheKey);
                    } else {
                        log.info("getAccountFromCache => Object not found in cache. Key: " + cacheKey);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (account == null) {
                log.info("getAccountFromCache => Gets object from DB.");
                account = APIAccess.getAPIAccess().getAccountAPI().getAccount(pAccountId, 0);
                if (cacheManager != null && cacheManager.isStarted()) {
                    try {
                        CacheKey cacheKey = BusEntityDAO.getAccountCacheKey(pAccountId);
                        cacheManager.put(cacheKey, account, new AccountDataMeta(account));
                        log.info("getAccountFromCache => Object has been added to cache. Key: " + cacheKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            log.info("getAccountFromCache => END.");
            return account;

        } catch (DataNotFoundException exc) {
            log.info("getAccountFromCache => Account not found.AccountId: " + pAccountId);
            return null;
        }
    }


    public static StoreData getStoreFromCache(int pStoreId) throws Exception {

        log.info("getStoreFromCache => BEGIN.");
        StoreData store = null;

        try {

            CachecosManager cacheManager = Cachecos.getCachecosManager();

            if (cacheManager != null && cacheManager.isStarted()) {
                try {
                    CacheKey cacheKey = BusEntityDAO.getStoreCacheKey(pStoreId);
                    store = (StoreData) cacheManager.get(cacheKey);
                    if (store != null) {
                        log.info("getStoreFromCache => Object found in cache with key " + cacheKey);
                    } else {
                        log.info("getStoreFromCache => Object not found in cache. Key: " + cacheKey);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (store == null) {

                log.info("getStoreFromCache => Gets object from DB.");
                store = APIAccess.getAPIAccess().getStoreAPI().getStore(pStoreId);

                if (cacheManager != null && cacheManager.isStarted()) {
                    try {
                        CacheKey cacheKey = BusEntityDAO.getStoreCacheKey(pStoreId);
                        cacheManager.put(cacheKey, store, new StoreDataMeta(store));
                        log.info("getStoreFromCache => Object has been added to cache.Key: " + cacheKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            log.info("getStoreFromCache => END.");
            return store;

        } catch (DataNotFoundException e) {
            log.info("getStoreFromCache => Store not found.StoreId: " + pStoreId);
            return null;
        }

    }

    public static CatalogData getAccountCatalog(Connection pCon, int pAccountId) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountId);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

        String catalogReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogReq);
        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        dbc.addNotEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.INACTIVE);

        CatalogDataVector cdv = CatalogDataAccess.select(pCon, dbc);
        if (cdv.size() == 1) {
            return (CatalogData) cdv.get(0);
        }

        if (cdv.size() > 1) {
            throw new Exception("^clw^Multiple catalogs for account " + pAccountId + "^clw^");
        }

        return null;
    }
    
    /**
     * Gets the Site data with budgets for specified budget year.
     * @param pCon
     * @param pSiteId - SiteId
     * @param budgetYear - Budget year.
     * @return
     * @throws Exception
     */
    public static SiteData getSiteData(Connection pCon, int pSiteId, int budgetYear ) throws Exception {

        log.debug("BusEntityDAO.getSiteData, pSiteId=" + pSiteId);
        if (pSiteId <= 0 ) {
            return null;
        }

        BusEntityData accountEntity = null, siteEntity;
        BusEntityAssocData accountAssoc = null;
        PropertyData subContractor = null;
        Integer targetFacilityRank = null;
        PropertyData taxableIndicator = null;
        PropertyData invShopping = null;
        PropertyData invShoppingType = null;
        PropertyData inventoryShoppingHoldOrderUntilDeliveryDate = null;
        PropertyDataVector miscProperties = null;
        PropertyDataVector fieldProperties = null;
        PropertyDataVector fieldPropertiesRuntime = null;
        AddressData shippingAddress = null;
        BudgetViewVector budgets = null;
        PhoneDataVector phones;
        BlanketPoNumData po = null;
        PropertyData allowCorpSchedOrder = null;

        siteEntity = BusEntityDataAccess.select(pCon, pSiteId);

        DBCriteria lcrit = new DBCriteria();

        lcrit.addJoinCondition(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID, BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID);
        lcrit.addJoinTableEqualTo(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC, BlanketPoNumAssocDataAccess.BUS_ENTITY_ID, pSiteId);
        lcrit.addDataAccessForJoin(new BlanketPoNumDataAccess());

        List bpoRes = JoinDataAccess.select(pCon,lcrit,500);

        if(bpoRes != null && !bpoRes.isEmpty()){
            po = (BlanketPoNumData) ((List)bpoRes.get(0)).get(0);
        }

        // get account association
        DBCriteria assocCrit = new DBCriteria();
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,  pSiteId);
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,  RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

        BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(pCon, assocCrit);

        // there ought to be exactly one - if not, that's a problem
        if (assocVec.size() > 0) {
            accountAssoc = (BusEntityAssocData) assocVec.get(0);
            accountEntity = BusEntityDataAccess.select(pCon, accountAssoc.getBusEntity2Id());
        }

        // Get the account information.

        AddressDataVector addresses = getSiteAddresses(pCon, pSiteId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        if (addresses.size() > 0) {
            shippingAddress = (AddressData)addresses.get(0);
        } else {
            addresses = getSiteAddresses(pCon, pSiteId, RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
            if (addresses.size() > 0) {
                shippingAddress = (AddressData)addresses.get(0);
            }
        }
        
        log.debug("shippingAddress=" + shippingAddress);

        // get site phones
        DBCriteria phoneCrit = new DBCriteria();
        phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID, pSiteId);

        phones = PhoneDataAccess.select(pCon, phoneCrit);

        if (accountEntity != null) {
        	budgets = BudgetDAO.getBudgetsForSite(pCon, accountEntity.getBusEntityId(), pSiteId, 0, budgetYear);
/*            Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountEntity.getBusEntityId());
            if (budgetYear != null) {
                budgets = BudgetDAO.getBudgetsForSite(pCon, accountEntity.getBusEntityId(), pSiteId, 0, budgetYear);
            }*/
        } /*else {
            int accountId = BusEntityDAO.getAccountForSite(pCon, pSiteId);
            Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountEntity.getBusEntityId());
            if (budgetYear != null) {
                budgets = BudgetDAO.getBudgetsForSite(pCon, accountId, pSiteId, 0, budgetYear);
            }
        }*/


        // get properties
        DBCriteria propCrit = new DBCriteria();
        propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSiteId);
        propCrit.addIsNull(PropertyDataAccess.USER_ID);

        PropertyDataVector props = PropertyDataAccess.select(pCon,propCrit);

        miscProperties = new PropertyDataVector();
        fieldProperties = new PropertyDataVector();
        fieldPropertiesRuntime = new PropertyDataVector();

        //set up the account iterator, which will only be accessed once
        int myAcctBus = (accountEntity!=null)?accountEntity.getBusEntityId():0;
        DBCriteria propCritAcc = new DBCriteria();
        propCritAcc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, myAcctBus);
        propCritAcc.addIsNull(PropertyDataAccess.USER_ID);

        PropertyDataVector propsAcc = PropertyDataAccess.select(pCon,propCritAcc);
        Iterator propAccIter = propsAcc.iterator();
        Iterator propIter = props.iterator();

        for (Object oProp : props) {

            PropertyData prop = (PropertyData) oProp;
            String propType = prop.getPropertyTypeCd();

            log.debug("--------------- setting site property:" + prop);

            if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.EXTRA)) {
                miscProperties.add(prop);
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING)) {
                invShopping = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE)) {
                invShoppingType = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOP_HOLD_DEL_DATE)) {
                inventoryShoppingHoldOrderUntilDeliveryDate = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER)) {
                allowCorpSchedOrder = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.SUB_CONTRACTOR)) {
                subContractor = prop;
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK)) {
                try {
                    if (prop != null && prop.getValue() != null &&
                            prop.getValue().trim().length() > 0) {
                        targetFacilityRank = new Integer(prop.getValue());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)) {

                fieldProperties.add(prop);

                /*  we only do this once because it
                 *  pertains to the Account level.
                 *  Doing it every time would give us duplicates.  */
                while (propAccIter.hasNext()) {

                    PropertyData propAcct = (PropertyData) propAccIter.next();
                    String propShortDesc = propAcct.getShortDesc();
                    String propVal = propAcct.getValue();

                    //If account level says to show a
                    //property in runtime, add it
                    if ((propShortDesc.indexOf("ShowRuntime") == 2) && (propVal.equals("true"))) {
                        fieldPropertiesRuntime.add(prop);
                        break;
                    }
                }
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR)) {
                taxableIndicator = prop;
            } else {
                // some property we know nothing about
                miscProperties.add(prop);
            }
        }

        SiteData siteD = new SiteData( siteEntity,
               accountEntity,
               accountAssoc,
               shippingAddress,
               phones,
               budgets,
               subContractor,
               targetFacilityRank,
               taxableIndicator,
               miscProperties,
               fieldProperties,
               fieldPropertiesRuntime,
               po,
               null,
               null,
               null
               );

        if ( invShopping == null ) {
            invShopping = PropertyData.createValue();
        }
        if ( invShoppingType == null ) {
            invShoppingType = PropertyData.createValue();
        }
        if (inventoryShoppingHoldOrderUntilDeliveryDate == null) {
          inventoryShoppingHoldOrderUntilDeliveryDate = PropertyData.createValue();
        }
        if ( allowCorpSchedOrder == null ) {
        	allowCorpSchedOrder = PropertyData.createValue();
        }
        siteD.setInventoryShopping(invShopping);
        siteD.setInventoryShoppingType(invShoppingType);
        siteD.setInventoryShoppingHoldOrderUntilDeliveryDate(inventoryShoppingHoldOrderUntilDeliveryDate);
        siteD.setAllowCorpSchedOrder(allowCorpSchedOrder);
        
        // Set the contract info.
        DBCriteria conCrit = new DBCriteria();
        conCrit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,
         siteEntity.getBusEntityId() );
      conCrit.addOrderBy(CatalogAssocDataAccess.CATALOG_ID, false);
        CatalogAssocDataVector catalogAssocs =
            CatalogAssocDataAccess.select(pCon, conCrit);

        if ( catalogAssocs != null && catalogAssocs.size() > 0 ){
            CatalogAssocData cad = (CatalogAssocData)catalogAssocs.get(0);
            int catalogid = cad.getCatalogId();
            siteD.setSiteCatalogId(catalogid );
            conCrit = new DBCriteria();
            conCrit.addEqualTo(ContractDataAccess.CATALOG_ID, catalogid);
            conCrit.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                               RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            conCrit.addOrderBy(ContractDataAccess.CONTRACT_ID, false);

            ContractDataVector condv =
                ContractDataAccess.select(pCon, conCrit);
            if ( condv != null && condv.size() > 0 ) {
                siteD.setContractData( (ContractData)condv.get(0) );
            }
        }

        //set the additional data for new pricing model,
        //that allow the improved search of products
        if (siteD.isUseProductBundle()) {
            SitePriceListView priceLists = PriceListDAO.getSitePriceListView(pCon, siteD.getSiteId());
            siteD.setPriceLists(priceLists);
            if (RefCodeNames.SITE_PRODUCT_BUNDLE.ORDER_GUIDE.equals(siteD.getProductBundle())) {
                //Order Guide types that should be included are: ORDER_GUIDE_TEMPLATE, SITE_ORDER_GUIDE_TEMPLATE, CUSTOM_ORDER_GUIDE
                OrderGuideDataVector templateOrderGuides = ShoppingDAO.getTemplateOrderGuides(pCon, siteD.getSiteCatalogId(), siteD.getSiteId());
                OrderGuideDataVector customeOrderGuides = ShoppingDAO.getCustomOrderGuides(pCon, siteD.getAccountId(), siteD.getSiteId());
                siteD.setTemplateOrderGuides(templateOrderGuides);
                siteD.setCustomOrderGuides(customeOrderGuides);
            }
        }


  log.debug("BusEntityDAO.getSiteData, pSiteId=" + pSiteId +
      " found site: " + siteD.toBasicInfo() );

        return siteD;

    }
    
    public FiscalPeriodView getFiscalInfo(Connection pCon, int pBusEntityId, int budgetPeriod, int budgetYear)
    throws Exception, DataNotFoundException
	{
	    BusEntityData bed = BusEntityDataAccess.select(pCon, pBusEntityId);
	    FiscalCalenderView fcv;
	    FiscalPeriodView res = FiscalPeriodView.createValue();
	    if(budgetPeriod > 0){
        	res.setCurrentFiscalPeriod(budgetPeriod);
        }
	    
	    int acctid = pBusEntityId;
	    if ( bed.getBusEntityTypeCd().equals
	         (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT))
	    {
	        fcv = getFiscalCalenderVForYear(pCon, pBusEntityId, budgetYear);
	        res.setFiscalCalenderView(fcv);
	        if(budgetPeriod == 0){
	        	res.setCurrentFiscalPeriod
	            (getCurrentAccountBudgetPeriod(pCon,pBusEntityId));
	        }
	    }
	    else
	    {
	        acctid = getAccountForSite(pCon, pBusEntityId);
	
	        fcv = getFiscalCalenderVForYear(pCon, acctid,  budgetYear);
	        res.setFiscalCalenderView(fcv);
	        if(budgetPeriod == 0){
	        	res.setCurrentFiscalPeriod
	            (getCurrentSiteBudgetPeriod(pCon,pBusEntityId));
	        }
	        
	    }
	
	    if ( null == res.getFiscalCalenderView()) {
	        res.setFiscalCalenderView(createFiscalCalender(
	            pCon, acctid )
	            );
	    }
	
	    res.setBusEntityId(pBusEntityId);
	    return res;
	}

  
}
