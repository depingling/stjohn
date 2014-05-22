package com.cleanwise.service.api.session;

/**
 * Title:        CountryBean
 * Description:  Bean implementation for Country Stateless Session Bean
 * Purpose:      Provides access to country methods
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * @author       Veronika Denega
 *
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.CreateException;

import com.cleanwise.service.api.dao.CountryDataAccess;
import com.cleanwise.service.api.dao.CountryPropertyDataAccess;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.CountryPropertyDataVector;

public class CountryBean extends UtilityServicesAPI {
  /**
   *
   */
  public CountryBean() {}

  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}


  /**
   * Adds the Country information values to be used by the request.
   * @param pCountry  the Country data.
   * @param request  the Country request data.
   * @return new CountryRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  // public CountryRequestData addCountry(CountryData pCountry,
  //               CountryRequestData request)
  //     throws RemoteException
  // {
  //   return new CountryRequestData();
  // }

  /**
   * Updates the Country information values to be used by the request.
   * @param pUpdateCountryData  the Country data.
   * @param pCountryId the Country identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateCountry(CountryData pUpdateCountryData,
                            int pCountryId) throws RemoteException {

  }

    /**
     * Get all currencies supported.
     * @return set of CountryData objects
     * @throws RemoteException Required by EJB 1.0
     */
    public CountryDataVector getAllCountries() throws RemoteException {

        CountryDataVector countryDV;
        Connection conn = null;

        try {

            conn = getConnection();

            DBCriteria dbC = new DBCriteria();
            dbC.addLessOrEqual("rownum", 1000);
           // dbC.addOrderBy(CountryDataAccess.COUNTRY_CODE);

            dbC.addOrderBy(CountryDataAccess.SHORT_DESC);
            countryDV = CountryDataAccess.select(conn, dbC);
            return countryDV;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * Get all countries description supported for the sites this user has access to.
     *
     * @param userId  the user id
     * @return set of CountryData objects
     * @throws RemoteException Required by EJB 1.0
     */
    public List getSiteCountriesForUserDesc(int userId) throws RemoteException {

        Connection conn = null;
        ArrayList list = new ArrayList();

        try {

            if (userId > 0) {

                conn = getConnection();

                String sql = "SELECT min(c.country_id), c.ui_name, c.short_desc" +
                        " FROM clw_address a JOIN clw_country c ON a.country_cd = c.short_desc" +
                        " WHERE  a.bus_entity_id IN (select bus_entity_id from clw_user_assoc where user_assoc_cd = 'SITE' and user_id = "+userId+")" +
                        " GROUP BY c.ui_name, c.short_desc";

                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    list.add(rs.getString(3));
                }

                rs.close();
                stmt.close();
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
  /**
   * Saves Country data object
   * @return Country data object
   * @throws RemoteException Required by EJB 1.0
   */
  public CountryData saveCountry(CountryData country) throws RemoteException {

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CountryDataAccess.COUNTRY_CODE, country.getCountryCode());
      CountryDataVector countryDV = CountryDataAccess.select(conn, dbc);
      if (countryDV.size() == 0) {
        country = CountryDataAccess.insert(conn, country);
      } else {
        CountryData curr = (CountryData) countryDV.get(0);
        country.setCountryId(curr.getCountryId());
        country.setAddBy(curr.getAddBy());
        country.setAddDate(curr.getAddDate());
        CountryDataAccess.update(conn, country);
        for (int ii = 1; ii < countryDV.size(); ii++) {
          curr = (CountryData) countryDV.get(ii);
          CountryDataAccess.remove(conn, curr.getCountryId());
        }
      }
      return country;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }
  }

  public CountryPropertyData getCountryPropertyData(int countryId, String propertyCd) throws RemoteException {
    Connection conn = null;
    CountryPropertyData res = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CountryPropertyDataAccess.COUNTRY_ID, countryId);
      dbc.addEqualTo(CountryPropertyDataAccess.COUNTRY_PROPERTY_CD, propertyCd);
      CountryPropertyDataVector cPropertyV = CountryPropertyDataAccess.select(conn, dbc);
      if (cPropertyV.size() > 0) {
        res = (CountryPropertyData) cPropertyV.get(0);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }
    return res;
  }
  public CountryDataVector getCountriesByPropertyData( String propertyCd, String propertyValue) throws RemoteException {
    Connection conn = null;
    CountryDataVector cV = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CountryPropertyDataAccess.COUNTRY_PROPERTY_CD, propertyCd);
      dbc.addEqualToIgnoreCase(CountryPropertyDataAccess.CLW_VALUE, propertyValue);
      String subSql = CountryPropertyDataAccess.getSqlSelectIdOnly(CountryPropertyDataAccess.COUNTRY_ID, dbc);
      dbc = new DBCriteria();
      dbc.addOneOf(CountryDataAccess.COUNTRY_ID, subSql );
      cV = CountryDataAccess.select(conn, dbc);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }
    return cV;
  }

  public CountryData getCountryByShortDesc(String pShortDesc) throws RemoteException {

          CountryDataVector countryDV;
          Connection conn = null;

          try {

              conn = getConnection();

              DBCriteria dbC = new DBCriteria();
              dbC.addContainsIgnoreCase(CountryDataAccess.SHORT_DESC, pShortDesc);
              countryDV = CountryDataAccess.select(conn, dbC);
              if (countryDV.size() > 0) {
                return (CountryData)countryDV.get(0);
              } else {
                  return null;
              }

          } catch (Exception e) {
              e.printStackTrace();
              throw new RemoteException(e.getMessage());
          } finally {
              try {
                  if (conn != null) conn.close();
              } catch (Exception ex) {

              }
          }
      }

    /**
     * Special method for the data storing named as newxpedx has been created
     * it gets data of  sites states and countries
     * for loading to the dynamic list.
     *
     * @param pUserId user id
     * @return Object[]{HashMap[country,states],HashMap[states,country]}
     * @throws RemoteException if an exception
     */
    public Object[] getCountryAndValidStateLinks(int pUserId) throws RemoteException {

        Connection conn = null;
        HashMap<String, HashSet<String>> countryLinkMap = new HashMap<String, HashSet<String>>();
        HashMap<String, HashSet<String>> stateLinkMap = new HashMap<String, HashSet<String>>();
        Object[] result = new Object[2];

        try {

            if (pUserId > 0) {

                conn = getConnection();

                String sql = "SELECT A.COUNTRY_CD,A.STATE_PROVINCE_CD FROM CLW_ADDRESS A\n" +
                        "WHERE A.BUS_ENTITY_ID IN (SELECT BE.BUS_ENTITY_ID FROM CLW_USER_ASSOC UA,CLW_BUS_ENTITY BE\n" +
                        " WHERE BE.BUS_ENTITY_ID=UA.BUS_ENTITY_ID\n" +
                        " AND BE.BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "'\n" +
                        " AND UA.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.SITE + "' \n" +
                        " AND UA.USER_ID = " + pUserId + ")\n" +
                        " AND  A.ADDRESS_TYPE_CD IN ('" + RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING + "','" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "')\n" +
                        " GROUP BY A.COUNTRY_CD,A.STATE_PROVINCE_CD";


                Statement stmt = conn.createStatement();
                logDebug("getCountryAndValidStateLinks sql:" + sql);

                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {

                    String country = rs.getString(1);
                    String state = rs.getString(2);

                    if (countryLinkMap.containsKey(country)) {
                        HashSet<String> stateSet = countryLinkMap.get(country);
                        if (isValidState(state)) {
                            stateSet.add(state);
                        }
                        countryLinkMap.put(country, stateSet);
                    } else {
                        HashSet<String> stateSet = new HashSet<String>();
                        if (isValidState(state)) {
                            stateSet.add(state);
                        }
                        countryLinkMap.put(country, stateSet);
                    }

                    if (isValidState(state)) {
                        if (stateLinkMap.containsKey(state)) {
                            HashSet<String> countrySet = stateLinkMap.get(state);
                            countrySet.add(country);
                            stateLinkMap.put(state, countrySet);
                        } else {
                            HashSet<String> countrySet = new HashSet<String>();
                            countrySet.add(country);
                            stateLinkMap.put(state, countrySet);
                        }
                    }

                }

                rs.close();
                stmt.close();
            }

            result[0] = countryLinkMap;
            result[1] = stateLinkMap;

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public boolean isValidState(String state) {
        return Utility.isSet(state) && (!(state.contentEquals("--")) || !(state.contentEquals(".")));
    }
    
    /**
     * Gets country data by country code.
     * @param pCountryCode a <code>String</code> value
     * @return countryData a <code>CountryData</code> value
     * @throws RemoteException - if an error occurs 
     */
    public CountryData getCountryByCountryCode(String pCountryCode) throws RemoteException {
    	
    	CountryData countryData = null;
        CountryDataVector countryDV;
        Connection conn = null;
        try {
            conn = getConnection();

            DBCriteria dbC = new DBCriteria();
            dbC.addEqualTo(CountryDataAccess.COUNTRY_CODE, pCountryCode);
            countryDV = CountryDataAccess.select(conn, dbC);
            
            if (countryDV.size() > 0) {
            	countryData = (CountryData)countryDV.get(0);
            } 

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return countryData;
    }
    
    //STJ-4689
    public String getAdddressFormatByShortDesc(String pCountryShortDesc) throws RemoteException {
    	String addressFormat = null;
        CountryDataVector countryDV;
        CountryData countryData = null;
        Connection conn = null;
        try {
            conn = getConnection();

            DBCriteria dbC = new DBCriteria();
            dbC.addEqualTo(CountryDataAccess.SHORT_DESC, pCountryShortDesc);
            countryDV = CountryDataAccess.select(conn, dbC);
            
            if (countryDV.size() > 0) {
            	countryData = (CountryData)countryDV.get(0);
            	addressFormat = countryData.getAddressFormat();
            } 

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return addressFormat;

    }
}
