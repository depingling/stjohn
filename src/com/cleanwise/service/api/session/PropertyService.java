package com.cleanwise.service.api.session;

import java.util.ArrayList;
import javax.ejb.*;
import java.rmi.*;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Properties;

import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

/**
 * The <code>PropertyService</code> is a general purpose interface to
 *  store and retrieve name value pairs from the database. The
 *  Property service makes a disctinction between general name value
 *  pairs, user specific name value pairs, and business entity name
 *  value pairs. Each group is distinguished by it own namespace. The
 *  namespace is not exposed to the client. The client need only know
 *  whether it is dealing with user, business entity, or general
 *  properties.
 *
 * @author     durval
 * @created    November 7, 2001
 */
public interface PropertyService extends javax.ejb.EJBObject {

    /**
     *  General application properties. <code>setProperty</code>
     *  method, Adds the property specified, or perform an update if
     *  it is already exists.
     *
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setProperty(String pName, String pValue)
             throws RemoteException;

    /**
     *  Set a general application property. <code>setProperty</code>
     *  method, adds the property specified, or performs an update if
     *  the property already exists.
     *
     *@param  pName                a <code>String</code> identifier
     *@param  pValue               a <code>String</code> value
     *@param  pLocaleCd            a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setProperty(String pName, String pValue, String pLocaleCd)
	throws RemoteException;

    /**
     *  General application properties. <code>getProperty</code>
     *  search for the property in memory. If the property is not
     *  found in memory, look in the database.
     *
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public String getProperty(String pName)
             throws RemoteException, DataNotFoundException;


    /**
     *  General application
     *  properties. <code>getPropertyCollection</code> search for the
     *  property collection in memory. If the Properties instance is
     *  not found in memory, create it from entries in the databse.
     *
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getPropertyCollection()
             throws RemoteException, DataNotFoundException;


    /**
     *  User specific properties. <code>setUserProperty</code> method,
     *  Adds the user specific property specified, or perform an
     *  update if it is already exists.
     *
     *@param  pUserId              an <code>int</code> value
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setUserProperty(int pUserId, String pName, String pValue)
             throws RemoteException;
    /**
     *  Set a user specific property. <code>setUserProperty</code>
     *  method, adds the user property specified, or performs an
     *  update if it is already exists.
     *
     *@param  pUserId              an <code>int</code> value
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@param  pLocaleCd               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setUserProperty(int pUserId, String pName, String pValue, String pLocaleCd)
             throws RemoteException;


    /**
     *  User specific
     *  properties. <code>setUserPropertyCollection</code> method,
     *  Adds the properties specified, or perform an update if they
     *  already exist.
     *
     *@param  pUserId              an <code>int</code> value
     *@param  pUserProps           a <code>Properties</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setUserPropertyCollection(int pUserId, Properties pUserProps)
             throws RemoteException;


    /**
     *  User specific properties. <code>getUserProperty</code> search
     *  for the user specific property in memory. If the property is
     *  not found in memory, look in the database.
     *
     *@param  pUserId                    an <code>int</code> value
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public String getUserProperty(int pUserId, String pName)
             throws RemoteException, DataNotFoundException;


    /**
     *  User specific
     *  properties. <code>getUserPropertyCollection</code> search for
     *  the user specific property collection in memory. If the
     *  Properties instance is not found in memory, create it from
     *  entries in the databse.
     *
     *@param  pUserId                    an <code>int</code> value
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getUserPropertyCollection(int pUserId)
             throws RemoteException, DataNotFoundException;


    /**
     *  Business entity specific
     *  properties. <code>setBusEntityProperty</code> method, Adds the
     *  business entity specific property specified, or perform an
     *  update if it is already exists.
     *
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setBusEntityProperty(int pBusEntityId,
            String pName,
            String pValue)
             throws RemoteException;

    /**
     *  Set a business entity specific property. <code>setBusEntityProperty
     *  </code>method, adds the business entity property specified, or performs
     *  an update if it is already exists.
     *
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@param  pLocaleCd               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setBusEntityProperty(int pBusEntityId,
            String pName, String pValue, String pLocaleCd)
	throws RemoteException ;

    public void setBusEntityProperty(int pBusEntityId, String pName,
			String pValue, String pLocaleCd, int userId) throws RemoteException;

    /**
	 * Business entity specific properties.
	 * <code>setBusEntityPropertyCollection</code> method, Adds the properties
	 * specified, or perform an update if they already exist.
	 *
	 * @param pBusEntityId
	 *            an <code>int</code> value
	 * @param pBusEntityProps
	 *            a <code>Properties</code> value
	 * @exception RemoteException
	 *                if an error occurs
	 */
    public void setBusEntityPropertyCollection(int pBusEntityId,
            Properties pBusEntityProps)
             throws RemoteException;


    /**
     *  Business entity specific
     *  properties. <code>getBusEntityProperty</code> search for the
     *  business entity specific property in memory. If the property
     *  is not found in memory, look in the database.
     *
     *@param  pBusEntityId               an <code>int</code> value
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public String getBusEntityProperty(int pBusEntityId, String pName)
             throws RemoteException, DataNotFoundException;


    /**
     *  Get business entity specific properties. <code>getBusEntityProperty
     *  </code>search for the business entity specific property in memory. If
     *  the property is not found in memory, look in the database.  This version does
     *  not throw a DataNotFoundException, but instead returns null, otherwise it
     *  is identical to getBusEntityProperty(pBusEntityId,pName)
     *
     *@param  pBusEntityId               an <code>int</code> value
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     */
    public String checkBusEntityProperty(int pBusEntityId, String pName)
             throws RemoteException;

    /**
     *  Business entity specific
     *  properties. <code>getBusEntityPropertyCollection</code> search
     *  for the business entity specific property collection in
     *  memory.  If the Properties instance is not found in memory,
     *  create it from entries in the databse.
     *
     *@param  pBusEntityId               an <code>int</code> value
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getBusEntityPropertyCollection(int pBusEntityId)
             throws RemoteException, DataNotFoundException;

    /**
     *  Get business entity specific properties. <code>
     *  getBusEntityPropertyCollection</code> search for the business entity
     *  specific property collection.
     *
     *@param  pBusEntityId               an <code>int</code> value
     *@param  pLocaleCd                  an Locale Code
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getBusEntityPropertyCollection(int pBusEntityId, String pLocaleCd)
	throws RemoteException, DataNotFoundException;

    /**
     *
     * @param pBusEntityId
     * @param pLocaleCd
     * @param userId
     * @return
     * @throws RemoteException
     * @throws DataNotFoundException
     */
    public Properties getBusEntityPropertyCollection(int pBusEntityId, String pLocaleCd,
    			int userId) throws RemoteException, DataNotFoundException;

    /**
     *  Description of the Method
     *
     *@param  pBusEntityId         Description of Parameter
     *@return                      Description of the Returned Value
     *@exception  RemoteException  Description of Exception
     */
    public UIConfigData fetchUIConfigData(int pBusEntityId)
             throws RemoteException;
     /**
     * Describe <code>fetchUIConfigData</code> method here.
     *
     * @param  pBusEntityId         Description of Parameter
     * @param  pLocaleCd            Description of Parameter
     * @return                      Description of the Returned Value
     * @exception  RemoteException  Description of Exception
     * @see  com.cleanwise.service.api.session.PropertyService
     */
    public UIConfigData fetchUIConfigData(int pBusEntityId, String pLocaleCd)
	throws RemoteException;


    /**
     *
     * @param pBusEntityId
     * @param pLocaleCd
     * @param userId
     * @return
     * @throws RemoteException
     */
    public UIConfigData fetchUIConfigData(int pBusEntityId, String pLocaleCd,
    		int userId)	throws RemoteException;

    public UIConfigData getStoreUIConfigData(int pBusEntityId, String pLocaleCd)
        throws RemoteException ;

    /**
     *  Update the properties for UI configuration for the store or
     *  account specified by the business entity.
     *
     *@param  pBusEntityId         Description of Parameter
     *@param  pUIConfigData        Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    public void updateUIConfig
            (int pBusEntityId, UIConfigData pUIConfigData)
             throws RemoteException;

    public void updateUIConfig(int pBusEntityId, UIConfigData pUIConfigData,
			int userId) throws RemoteException;

    /**
	 * Remove the properties for UI configuration for the store or account
	 * specified by the business entity.
	 *
	 * @param pBusEntityId
	 *            an <code>int</code> value
	 * @exception RemoteException
	 *                if an error occurs
	 */
    public void removeUIConfig
            (int pBusEntityId)
             throws RemoteException;

    /**
	 * Remove the properties for UI configuration for the store or account
	 * specified by the business entity and user.

     * @param pBusEntityId
     * @param userId
     * @throws RemoteException
     */
    public void removeUIConfig(int pBusEntityId, int userId)
			throws RemoteException;

    /**
	 * Fetches the site properties for the supplied account id
	 *
	 * @param pBusEntityId
	 *            Account id
	 * @return the populated BusEntityFieldsData
	 */
    public BusEntityFieldsData fetchSiteFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException ;

    /**
     *  Fetches the account properties for the supplied store id
     *
     *@param  pBusEntityId               Store id
     *@return                            the populated BusEntityFieldsData
     */
    public BusEntityFieldsData fetchAccountFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException;

    /**
     *  Fetches the account properties for the supplied store id
     *
     *@param  pBusEntityId               Store id
     *@return                            the populated BusEntityFieldsData
     */
    public BusEntityFieldsData fetchMasterItemFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException;


    /**
     *  Updates the supplied site fields.
     *
     *@param  pBusEntityId         Account id
     *@param  pSiteFieldsData      data to update
     */
    public void updateSiteFieldsData
            (int pBusEntityId, BusEntityFieldsData pSiteFieldsData)
             throws RemoteException;


    /**
     *  Updates the supplied account fields.
     *
     *@param  pBusEntityId         Store id
     *@param  pBusEntityFieldsData      data to update
     */
    public void updateAccountFieldsData (int pBusEntityId,
				      BusEntityFieldsData pBusEntityFieldsData)
	throws RemoteException;

    /**
     *  Updates the supplied account fields.
     *
     *@param  pBusEntityId         Store id
     *@param  pMasterItemFieldsData      data to update
     */
    public void updateMasterItemFieldsData (int pBusEntityId,
				      BusEntityFieldsData pMasterItemFieldsData)
	throws RemoteException;


    /**
     *Gets the message resources for this bus entity id, gets all locales.  This means that
     *it is up to the caller to correctly determine between two message names/keys
     */
    public MessageResourceDataVector getMessageResources(int busEntityId)
    throws RemoteException;

    /**
     *Gets the message resources for this bus entity id, with this locale
     */
    public MessageResourceDataVector getMessageResources(int busEntityId,String localKey)
    throws RemoteException;

    /**
     *Gets the message resources for this bus entity id, with this locale, and includes
     *nulls that are set, this is good for config so that the auditing of the message
     *resource table is not lost.
     */
    public MessageResourceDataVector getMessageResourcesWithNulls(int busEntityId,String localKey)
    throws RemoteException;

    /**
     *Saves the message resources
     */
    public void saveMessageResources(MessageResourceDataVector pMessageResourceDataVector,String pUser)
    throws RemoteException;

    /**
     *  Fetchs properties from clw_property table
     *
     *@param  pBusEntId                  business entity identifier (any business entity if null)
     *@param  pUserId                    user identifier (any user if null)
     *@param  pPropertyType              property type (all types if null)
     *@return                            set of PropertyData objects
     *@exception  RemoteException
     *@exception  DataNotFoundException
     */
    public PropertyDataVector getProperties(Integer pUserId, Integer pBusEntId, String pPropertyType)
        throws RemoteException;
    
    public PropertyDataVector getPropertiesForLocale(UserData pUser, Integer pBusEntId, String pPropertyType)
    throws RemoteException;

      /**
     * Gets the message resources for this bus entity id with this locale where key is locale_name
     *
     * @param busEntityId       business entity identifier
     * @param key               name identifier
     * @param localeKey         locale key
     * @param reqType           Request type."contains" or "equal".default value is "equal"
     * @return                  set of MessageResourceData objects
     * @throws RemoteException
     */
     public  MessageResourceDataVector getMessageResourcesByKey(int busEntityId,String key,String localeKey,String reqType)
     throws RemoteException;

     /**
     * Gets the message resources for the bus entity id with this locale for message key prefix
     *
     * @param busEntityId       business entity identifier
     * @param localeKey         locale key
     * @return                  HashMap of MessageResourceData objects wiht message name as a key
     * @throws RemoteException
     */
     public HashMap getMessageResources(int busEntityId, String localeKey, String keyPrefix)
            throws RemoteException;

     /**
     * Gets the BusEntityIds for this bus entity id with this locale where key is locale_name
     *
     * NOTE DO NOT USE THIS METHOD UNLESS DOING YOUR OWN SECURITY CODING
     * TO MAKE SUER YOU WILL NOT CROSS STORES!!!!!!!!!
     *
     * @param propertyName      Name of the propetry to search on
     * @param propertyValue     Value to search for
     * @return                  ArrayList of founded BusEntityIds
     * @throws RemoteException, DataNotFoundException
     */
     public ArrayList getBusEntityVector(String propertyName, String propertyValue)
     throws RemoteException, DataNotFoundException;

     /**
      * Gets the BusEntityIds for specified property value and days ago
      *
      * @param propertyName      Name of the propetry to search on
      * @param propertyValue     Value to search for
      * @param days				 Number of days ago
      * @return                  ArrayList of founded BusEntityIds
      * @throws RemoteException, DataNotFoundException
      */
     public ArrayList getBusEntityVector(String propertyName, String propertyValue, int days)
     throws RemoteException;

     /**
      * Gets all users with specified property
      * @param propertyName		Name of the propetry to search on
      * @param propertyValue	Value to search for
      * @return					ArrayList of users
      * @throws RemoteException
      * @throws DataNotFoundException
      */
     public ArrayList getUsers(String propertyName, String propertyValue)
 	 throws RemoteException, DataNotFoundException;

     public void update(PropertyData propertyData) throws RemoteException;
     public void delete(int propertyId) throws RemoteException;
     public PropertyData getProperty(int propertyId) throws RemoteException;
     /**
      *  Fetches the checkout properties for the supplied account id
      *
      *@param  pBusEntityId               Account id
      *@return                            the populated BusEntityFieldsData
      */
     public BusEntityFieldsData fetchCheckoutFieldsData(int pBusEntityId)
     throws RemoteException, DataNotFoundException;
     
     /**
      *  Updates the supplied checkout fields.
      *
      *@param  pBusEntityId         Store id
      *@param  pBusEntityFieldsData      data to update
      */
     public void updateCheckoutFieldsData (int pBusEntityId,
 				      BusEntityFieldsData pBusEntityFieldsData)
 	throws RemoteException;

     public PropertyDataVector getProperties(IdVector pBusEntityIds,
             String pShortDesc) throws RemoteException;
     
     public PropertyData getAccessToken(String accessToken) throws RemoteException;
}
