package com.cleanwise.service.api.session;

/**
 * Title:        Country
 * Description:  Remote Interface for Country Stateless Session Bean
 * Purpose:      Provides access to currency methods
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * @author       Veronika Denega
 *
 */

import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.IdVector;

import java.rmi.RemoteException;
import java.util.List;


public interface Country extends javax.ejb.EJBObject
{

  /**
   * Adds the Country information values to be used by the request.
   * @param pCountry  the Country data.
   * @param request  the Country request data.
   * @return new CountryRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
//  public CountryRequestData addCountry(CountryData pCountry,
//                CountryRequestData request)
//      throws RemoteException;

  /**
   * Updates the Country information values to be used by the request.
   * @param pUpdateCountryData  the Country data.
   * @param pCountryId the Country identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateCountry(CountryData pUpdateCountryData,
                int pCountryId)
      throws RemoteException;

  /**
   * Get all currencies supported.
   * @return set of CountryData objects
   * @throws RemoteException Required by EJB 1.0
   */
  public CountryDataVector getAllCountries()
  throws RemoteException;

  /**
   * Saves Country data object
   * @return Country data object
   * @throws RemoteException Required by EJB 1.0
   */
  public CountryData saveCountry(CountryData Country)
  throws RemoteException;


public CountryPropertyData getCountryPropertyData(int countryId, String propertyCd)
throws RemoteException;

public CountryData getCountryByShortDesc(String pShortDesc) throws RemoteException;

/**
 * Get all countries description supported for the sites this user has access to.
 *
 * @param userId  the user id
 * @return set of CountryData objects
 * @throws RemoteException Required by EJB 1.0
 */
public List getSiteCountriesForUserDesc(int userId) throws RemoteException ;

    /**
     * Special method for the data storing named as newxpedx has been created
     * it gets data of  sites states and countries
     * for loading to the dynamic list.
     *
     * @param pUserId user id
     * @return Object[]{HashMap[country,states],HashMap[states,country]}
     * @throws RemoteException if an exception
     */
    public Object[] getCountryAndValidStateLinks(int pUserId) throws RemoteException;
    public CountryDataVector getCountriesByPropertyData( String propertyCd, String propertyValue) throws RemoteException ;
    
    /**
     * Gets country data by country code.
     * @param pCountryCode a <code>String</code> value
     * @return countryData a <code>CountryData</code> value
     * @throws RemoteException - if an error occurs 
     */
    public CountryData getCountryByCountryCode(String pCountryCode) throws RemoteException;
    
    //STJ-4689
    /**
     * Gets country's address last line formatter.
     * @param pCountryCode a <code>String</code> value
     * @return addressLastLineFormatter a <code>String</code> value
     * @throws RemoteException - if an error occurs 
     */
    public String getAdddressFormatByShortDesc(String pCountryShortDesc) throws RemoteException;

}

