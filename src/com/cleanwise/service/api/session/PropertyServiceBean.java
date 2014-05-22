package com.cleanwise.service.api.session;

import java.util.ArrayList;
import javax.ejb.*;
import java.rmi.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.Date;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.PropertyFieldUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;

import java.util.Iterator;
import java.util.Set;

/**
 * The <code>PropertyServiceBean</code> implements the
 * @see        com.cleanwise.service.api.session.PropertyService
 *      interface.
 *
 * @author     durval
 * @created    August 29, 2001
 */
public class PropertyServiceBean extends UtilityServicesAPI {

    /**
     *  Creates a new <code>PropertyServiceBean</code> instance.
     */
    public PropertyServiceBean() { }


    /**
     *  Set a general application property. <code>setProperty</code>
     *  method, adds the property specified, or performs an update if
     *  the property already exists.
     *
     *@param  pName                a <code>String</code> identifier
     *@param  pValue               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setProperty(String pName, String pValue)
	throws RemoteException {
        setProperty(pName, pValue, null);
    }

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
	throws RemoteException {
        saveProperty(0, 0, pName, pValue, pLocaleCd);
    }


    /**
     *  Set a user specific property. <code>setUserProperty</code>
     *  method, adds the user property specified, or performs an
     *  update if it is already exists.
     *
     *@param  pUserId              an <code>int</code> value
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setUserProperty(int pUserId, String pName, String pValue)
	throws RemoteException {
        setUserProperty(pUserId, pName, pValue, null);
    }

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
	throws RemoteException {
        saveProperty(pUserId, 0, pName, pValue, pLocaleCd);
    }

    /**
     *  Set user specific
     *  properties. <code>setUserPropertyCollection</code> method,
     *  adds the properties specified, or performs an update if they
     *  already exist.
     *
     *@param  pUserId              an <code>int</code> value
     *@param  pUserProps           a <code>Properties</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setUserPropertyCollection(int pUserId, Properties pUserProps)
	throws RemoteException {
	try {
	    saveProps(pUserId, 0, pUserProps);
	}
	catch (Exception e) {
	    String m = "setUserPropertyCollection: Error, " + e;
	    logError(m);
	    throw new RemoteException(m);
	}
    }


    /**
     *  Set a business entity specific property. <code>setBusEntityProperty
     *  </code>method, adds the business entity property specified, or performs
     *  an update if it is already exists.
     *
     *@param  pBusEntityId         an <code>int</code> value
     *@param  pName                a <code>String</code> value
     *@param  pValue               a <code>String</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void setBusEntityProperty(int pBusEntityId,
				     String pName,
				     String pValue)
	throws RemoteException {
        setBusEntityProperty(pBusEntityId, pName, pValue, null);
    }

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
	throws RemoteException {
    	setBusEntityProperty(pBusEntityId, pName, pValue, pLocaleCd, 0);
    }

    public void setBusEntityProperty(int pBusEntityId, String pName,
			String pValue, String pLocaleCd, int userId) throws RemoteException {
		saveProperty(userId, pBusEntityId, pName, pValue, pLocaleCd);
	}

    /**
	 * Set business entity specific properties. <code>
     *  setBusEntityPropertyCollection</code>
	 * method, adds the properties specified, or performs an update if they
	 * already exist.
	 *
	 * @param pBusEntityId
	 *            an <code>int</code> value
	 * @param pProps
	 *            a <code>Properties</code> value
	 * @exception RemoteException
	 *                if an error occurs
	 */
    public void setBusEntityPropertyCollection(int pBusEntityId,
					       Properties pProps)
	throws RemoteException {
	try {
	    saveProps(0, pBusEntityId, pProps);
	}
	catch (Exception e) {
	    String m = "setBusEntityPropertyCollection: Error, " + e;
	    logError(m);
	    throw new RemoteException(m);
	}

    }


    /**
     *  Get all the general application properties. <code>getPropertyCollection
     *  </code>get the property collection in memory.
     *
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getPropertyCollection()
	throws RemoteException, DataNotFoundException {
        return fetchCollection(0, 0, null);
    }


    /**
     *  Get a general application property. <code>getProperty</code> .
     *
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public String getProperty(String pName)
	throws RemoteException, DataNotFoundException {
        return fetchValue(0, 0, pName);
    }


    /**
     *  Get a user specific propertiy. <code>getUserProperty</code> search for
     *  the user specific property in memory. If the property is not found in
     *  memory, look in the database.
     *
     *@param  pUserId                    an <code>int</code> value
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public String getUserProperty(int pUserId, String pName)
	throws RemoteException, DataNotFoundException {
        return fetchValue(pUserId, 0, pName);
    }


    /**
     *  Get user specific properties. <code>getUserPropertyCollection</code>
     *  search for the user specific property collection in memory. If the
     *  Properties instance is not found in memory, create it from entries in
     *  the database.
     *
     *@param  pUserId                    an <code>int</code> value
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getUserPropertyCollection(int pUserId)
	throws RemoteException, DataNotFoundException {
        return fetchCollection(pUserId, 0, null);
    }


    /**
     *  Get business entity specific properties. <code>getBusEntityProperty
     *  </code>search for the business entity specific property in memory. If
     *  the property is not found in memory, look in the database.
     *
     *@param  pBusEntityId               an <code>int</code> value
     *@param  pName                      a <code>String</code> value
     *@return                            a <code>String</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public String getBusEntityProperty(int pBusEntityId, String pName)
	throws RemoteException, DataNotFoundException {
        return fetchValue(0, pBusEntityId, pName);
    }

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
	throws RemoteException {
        String v = null;
	try {
	    v = fetchValue(0, pBusEntityId, pName);
	}
	catch (Exception e) {
	    return null;
	}
	return v;
    }



    /**
     *  Get business entity specific properties. <code>
     *  getBusEntityPropertyCollection</code> search for the business entity
     *  specific property collection.
     *
     *@param  pBusEntityId               an <code>int</code> value
     *@return                            a <code>Properties</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public Properties getBusEntityPropertyCollection(int pBusEntityId)
	throws RemoteException, DataNotFoundException {
        return getBusEntityPropertyCollection(pBusEntityId,null);
    }

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
	throws RemoteException, DataNotFoundException {
        return getBusEntityPropertyCollection(pBusEntityId, pLocaleCd, 0);
    }

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
    		int userId)
		throws RemoteException, DataNotFoundException {
        return fetchCollection(userId, pBusEntityId, pLocaleCd);
    }
    /**
     *  Default <code>ejbCreate</code> method.
     *
     *@exception  CreateException  if an error occurs
     *@exception  RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException { }


    /**
     * Describe <code>fetchUIConfigData</code> method here.
     *
     * @param  pBusEntityId         Description of Parameter
     * @return                      Description of the Returned Value
     * @exception  RemoteException  Description of Exception
     * @see  com.cleanwise.service.api.session.PropertyService
     */
    public UIConfigData fetchUIConfigData(int pBusEntityId)
	throws RemoteException {
      return fetchUIConfigData(pBusEntityId, null);
    }

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
	throws RemoteException {
    	return fetchUIConfigData(pBusEntityId, pLocaleCd, 0);
    }

    /**
     *
     * @param pBusEntityId
     * @param pLocaleCd
     * @param userId
     * @return
     * @throws RemoteException
     */
    public UIConfigData fetchUIConfigData(int pBusEntityId, String pLocaleCd,
    			int userId) throws RemoteException {
        UIConfigData uiConfigData = new UIConfigData();
        try {
            Properties props = null;
            props = getBusEntityPropertyCollection(pBusEntityId, pLocaleCd, 0);
            try {
            	Properties userProps = getBusEntityPropertyCollection(pBusEntityId,
						(userId > 0) ? null : pLocaleCd, userId);
		Enumeration propNamesEnum = userProps.propertyNames();
                while(propNamesEnum.hasMoreElements()) {
                    String propName = (String) propNamesEnum.nextElement();
                    String propValue = userProps.getProperty(propName);
                    props.put(propName,propValue);
                }
            } catch (DataNotFoundException dnfe) {
            	//props = getBusEntityPropertyCollection(pBusEntityId, pLocaleCd, 0);
            }
            Enumeration en = props.propertyNames();
            String tname;
            String tval;
            while (en.hasMoreElements()) {
                tname = (String) en.nextElement();
                tval = (String) props.get(tname);
                uiConfigData.populate(tname, tval);
            }
        }
        catch (Exception e) {
            throw new RemoteException
		("Account.fetchUIConfigData, error:" + e);
        }

        return uiConfigData;
    }

    public UIConfigData getStoreUIConfigData(int pBusEntityId, String pLocaleCd) throws RemoteException {
          UIConfigData uiConfigData = new UIConfigData();
          try {
              Properties props = null;
              props = getBusEntityPropertyCollection(pBusEntityId, pLocaleCd, 0);
              Enumeration en = props.propertyNames();
              String tname;
              String tval;
              while (en.hasMoreElements()) {
                  tname = (String) en.nextElement();
                  tval = (String) props.get(tname);
                  uiConfigData.populate(tname, tval);
              }
          }
          catch (Exception e) {
              throw new RemoteException
                  ("Account.fetchUIConfigData, error:" + e);
          }

          return uiConfigData;
      }


    /**
     * Describe <code>updateUIConfig</code> method here.
     *
     * @param  pBusEntityId         Description of Parameter
     * @param  pUIConfigData        Description of Parameter
     * @exception  RemoteException  Description of Exception
     * @see com.cleanwise.service.api.session.PropertyService
     */
    public void updateUIConfig(int pBusEntityId, UIConfigData pUIConfigData)
			throws RemoteException {
    	updateUIConfig(pBusEntityId, pUIConfigData, 0);
	}
    public void updateUIConfig(int pBusEntityId, UIConfigData pUIConfigData,
			int userId) throws RemoteException {
        String localeCd = pUIConfigData.getLocaleCd();
        setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getTipsMsgPropName(),
			     pUIConfigData.getTipsMsg(),localeCd, userId);

        setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getMainMsgPropName(),
			     pUIConfigData.getMainMsg(),localeCd, userId);

        setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getContactUsMsgPropName(),
			     pUIConfigData.getContactUsMsg(),localeCd, userId);

        setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getToolbarStylePropName(),
			     pUIConfigData.getToolbarStyle(),localeCd, userId);

        if (pUIConfigData.getLogo1().length() > 0) {
            setBusEntityProperty(pBusEntityId,
				 pUIConfigData.getLogo1PropName(),
				 pUIConfigData.getLogo1(),localeCd, userId);
        }

        if (pUIConfigData.getLogo2().length() > 0) {
            setBusEntityProperty(pBusEntityId,
				 pUIConfigData.getLogo2PropName(),
				 pUIConfigData.getLogo2(),localeCd, userId);
        }

        setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getStyleSheetPropName(),
			     pUIConfigData.getStyleSheet(),localeCd, userId);

        setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getFooterMsgPropName(),
			     pUIConfigData.getFooterMsg(),localeCd, userId);

       setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getPageTitlePropName(),
			     pUIConfigData.getPageTitle(),localeCd, userId);

       setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getHomePageButtonLabelName(),
			     pUIConfigData.getHomePageButtonLabel(),localeCd, userId);

       setBusEntityProperty(pBusEntityId,
			     pUIConfigData.getCustomerServiceAliasName(),
			     pUIConfigData.getCustomerServiceAlias(),localeCd, userId);


        return;
    }


    /**
     *  Description of the Method
     *
     *@param  pId                  Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    public void removeUIConfig(int pId)
	throws RemoteException {
    	removeUIConfig(pId, 0);
    }

    public void removeUIConfig(int pId, int userId) throws RemoteException {
        UIConfigData lUIConfigData = new UIConfigData();
        try {
            removeProperty(userId, pId, lUIConfigData.getTipsMsgPropName());
            removeProperty(userId, pId, lUIConfigData.getMainMsgPropName());
            removeProperty(userId, pId, lUIConfigData.getContactUsMsgPropName());
            removeProperty(userId, pId, lUIConfigData.getToolbarStylePropName());
            removeProperty(userId, pId, lUIConfigData.getLogo1PropName());
            removeProperty(userId, pId, lUIConfigData.getLogo2PropName());
            removeProperty(userId, pId, lUIConfigData.getStyleSheetPropName());
            removeProperty(userId, pId, lUIConfigData.getFooterMsgPropName());
            removeProperty(userId, pId, lUIConfigData.getPageTitlePropName());
            removeProperty(userId, pId, lUIConfigData.getHomePageButtonLabelName());
            removeProperty(userId, pId, lUIConfigData.getCustomerServiceAliasName());
        }catch (Exception e) {
            logError("removeUIConfig: error:" + e.getMessage());
        }


        return;
    }


    /**
     *  Fetches the site properties for the supplied account id
     *
     *@param  pBusEntityId               Account id
     *@return                            the populated BusEntityFieldsData
     */
    public BusEntityFieldsData fetchSiteFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException {
        return fetchBusEntityFieldsData(pBusEntityId,RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
    }

    /**
     *  Fetches the account properties for the supplied store id
     *
     *@param  pBusEntityId               Store id
     *@return                            the populated BusEntityFieldsData
     */
    public BusEntityFieldsData fetchAccountFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException {
        return fetchBusEntityFieldsData(pBusEntityId,RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
    }

    /**
     *  Fetches the account properties for the supplied store id
     *
     *@param  pBusEntityId               Store id
     *@return                            the populated BusEntityFieldsData
     */
    public BusEntityFieldsData fetchMasterItemFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException {
        return fetchBusEntityFieldsData(pBusEntityId,RefCodeNames.PROPERTY_TYPE_CD.MASTER_ITEM_FIELD_CD);
    }

    /**
     *  Fetches the checkout properties for the supplied account id
     *
     *@param  pBusEntityId               Account id
     *@return                            the populated BusEntityFieldsData
     */
    public BusEntityFieldsData fetchCheckoutFieldsData(int pBusEntityId)
    throws RemoteException, DataNotFoundException {
        return fetchBusEntityFieldsData(pBusEntityId,RefCodeNames.PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD);
    }
    
    private BusEntityFieldsData fetchBusEntityFieldsData(int pBusEntityId, String type)
	throws RemoteException, DataNotFoundException {

	BusEntityFieldsData sfd = null;

	Connection conn = null;
	try {
            conn = getConnection();
	    PropertyFieldUtil acctProps =
		new PropertyFieldUtil(conn,type);
	    sfd = acctProps.fetchBusEntityFieldsData(pBusEntityId);
	} catch (Exception e) {
	    String m = "fetchBusEntityFieldsData: Error, " + e;
	    logError(m);
	    throw new RemoteException(m);
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return sfd;
    }


    /**
     *  Updates the supplied site fields.
     *
     *@param  pBusEntityId         Account id
     *@param  pSiteFieldsData      data to update
     */
    public void updateSiteFieldsData (int pBusEntityId,
				      BusEntityFieldsData pSiteFieldsData)
	throws RemoteException {
            updateBusEntityFieldsData(pBusEntityId, pSiteFieldsData,RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
    }

    /**
     *  Updates the supplied account fields.
     *
     *@param  pBusEntityId         Store id
     *@param  pBusEntityFieldsData      data to update
     */
    public void updateAccountFieldsData (int pBusEntityId,
				      BusEntityFieldsData pBusEntityFieldsData)
	throws RemoteException {
            updateBusEntityFieldsData(pBusEntityId, pBusEntityFieldsData,RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
    }

    /**
     *  Updates the supplied master item fields.
     *
     *@param  pBusEntityId         Store id
     *@param  pMasterItemFieldsData      data to update
     */
    public void updateMasterItemFieldsData (int pBusEntityId,
				      BusEntityFieldsData pMasterItemFieldsData)
	throws RemoteException {
            updateBusEntityFieldsData(pBusEntityId, pMasterItemFieldsData,RefCodeNames.PROPERTY_TYPE_CD.MASTER_ITEM_FIELD_CD);
    }

    /**
     *  Updates the supplied checkout fields.
     *
     *@param  pBusEntityId         account id
     *@param  pBusEntityFieldsData      data to update
     */
    public void updateCheckoutFieldsData (int pBusEntityId,
				      BusEntityFieldsData pBusEntityFieldsData)
	throws RemoteException {
            updateBusEntityFieldsData(pBusEntityId, pBusEntityFieldsData,RefCodeNames.PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD);
    }


    private void updateBusEntityFieldsData (int pBusEntityId,
				      BusEntityFieldsData pBusEntityFieldsData,
                                      String pType)
	throws RemoteException {
	Connection conn = null;
	try {
            conn = getConnection();
	    PropertyFieldUtil acctProps = new PropertyFieldUtil(conn,pType);
	    acctProps.updateSiteFieldsData(pBusEntityId,pBusEntityFieldsData);
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}

    }


    /**
     *  Save this name value pair to the database. The root qualifier is
     *  optional.
     *
     *@param  pUserId              Description of Parameter
     *@param  pBusEntId            Description of Parameter
     *@param  pName                Description of Parameter
     *@param  pValue               Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    private void saveProperty(int pUserId, int pBusEntId,
			      String pName, String pValue, String pLocaleCd)
	throws RemoteException {

	Connection conn = null;
        try {
            conn = getConnection();
            PropertyUtil propUtil = new PropertyUtil(conn);
	    propUtil.saveValue(pUserId, pBusEntId, pName, pName, pValue,pLocaleCd);
        }
        catch (Exception e) {
            String msg = "saveProperty error: " + e;
            logError(msg);
            throw new RemoteException(msg);
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }


    /**
     *  Helper method to save a collection of properties.
     *
     *@param  pUserId              Description of Parameter
     *@param  pBusEntId            Description of Parameter
     *@param  pProps               Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    private void saveProps(int pUserId, int pBusEntId, Properties pProps)
	throws Exception {

        Connection conn = null;
        try{
          Enumeration en = pProps.propertyNames();
          String tname;
          String tval;
          conn = getConnection();
          PropertyUtil propUtil = new PropertyUtil(conn);
          while (en.hasMoreElements()) {
              tname = (String) en.nextElement();
              tval = (String) pProps.get(tname);
	      propUtil.saveValue(pUserId, pBusEntId, tname, tname, tval);
          }
        }finally{
          if (conn != null){
            conn.close();
          }
        }
    }


    /**
     *  Description of the Method
     *
     *@param  pUserId                    Description of Parameter
     *@param  pBusEntId                  Description of Parameter
     *@param  pName                      Description of Parameter
     *@exception  DataNotFoundException  Description of Exception
     *@exception  Exception              Description of Exception
     */
    private void removeProperty(int pUserId, int pBusEntId, String pName)
	throws DataNotFoundException, Exception {

	Connection conn = null;
        try {
            conn = getConnection();
	    PropertyUtil propUtil = new PropertyUtil(conn);
            propUtil.removeProperty(pUserId, pBusEntId, pName);
        }
        catch (Exception e) {
            throw new RemoteException("removeProperty: error" + e);
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }


    /**
     *  Helper method to fetch a value for a property.
     *
     *@param  pUserId                    Description of Parameter
     *@param  pBusEntId                  Description of Parameter
     *@param  pName                      Description of Parameter
     *@return                            Description of the Returned Value
     *@exception  RemoteException        Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     */
    private String fetchValue(int pUserId, int pBusEntId, String pName)
	throws RemoteException, DataNotFoundException {

	String v = null;

	Connection conn = null;
        try {
            conn = getConnection();
	    logDebug( "fetchValue pUserId=" + pUserId
		      + " pBusEntId=" + pBusEntId + " pName=" + pName );
	    PropertyUtil propUtil = new PropertyUtil(conn);
            v = propUtil.fetchValue(pUserId, pBusEntId, pName);
        }
        catch (DataNotFoundException e) {
            throw new DataNotFoundException
		("fetchValue: no such property");
	}
        catch (Exception e) {
            throw new RemoteException
		("fetchValue: error" + e.getMessage());
	} finally {
	    logDebug( "fetchValue CLOSE CONN pUserId=" + pUserId
		      + " pBusEntId=" + pBusEntId + " pName=" + pName );
	    closeConnection(conn);
        }

	logDebug( "fetchValue pUserId=" + pUserId
		  + " pBusEntId=" + pBusEntId + " pName=" + pName
		  + " returning v=" + v );
	return v;
    }


    /**
     *  Helper method to fetch a Properties object for a user or business
     *  entiry.
     *
     *@param  pUserId                    Description of Parameter
     *@param  pBusEntId                  Description of Parameter
     *@param  pLocaleCd                  Description of Parameter
     *@return                            Description of the Returned Value
     *@exception  RemoteException        Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     */
    private Properties fetchCollection(int pUserId, int pBusEntId, String pLocaleCd)
        throws DataNotFoundException, RemoteException {

        Properties props = null;

        Connection conn = null;
        try {
            conn = getConnection();
            PropertyUtil propUtil = new PropertyUtil(conn);
    	      props = propUtil.fetchProperties(pUserId, pBusEntId, null, pLocaleCd);
        }
        catch (DataNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RemoteException("fetchCollection: error" + e);
        } finally {
            closeConnection(conn);
        }
        return props;
    }

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
        throws RemoteException {

        Connection conn = null;
        try {
          conn = getConnection();
          // Fetch the properties.
          PropertyDataVector pdv = null;

          DBCriteria crit = new DBCriteria();
          if(pUserId!=null) {
            int userId = pUserId.intValue();
            if (userId > 0) {
              crit.addEqualTo(PropertyDataAccess.USER_ID, userId);
            } else {
              crit.addIsNull(PropertyDataAccess.USER_ID);
            }
          }
          if(pBusEntId!=null) {
            int busEntId = pBusEntId.intValue();
            if (busEntId > 0) {
              crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, busEntId);
            } else {
              crit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
            }
          }
          if (pPropertyType != null && pPropertyType.trim().length() > 0) {
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, pPropertyType);
          }
          pdv = PropertyDataAccess.select(conn, crit);
          return pdv;
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     *  Fetchs properties from clw_property table
     *
     *@param  pBusEntId                  business entity identifier (any business entity if null)
     *@param  pUser                    user  (any user if null)
     *@param  pPropertyType              property type (all types if null)
     *@return                            set of PropertyData objects
     *@exception  RemoteException
     *@exception  DataNotFoundException
     */
    public PropertyDataVector getPropertiesForLocale(UserData pUser, Integer pBusEntId, String pPropertyType, boolean getDefault)
        throws RemoteException {

        Connection conn = null;
        try {
          conn = getConnection();
          // Fetch the properties.
          PropertyDataVector pdv = null;
          
          DBCriteria crit = new DBCriteria();
          if(pUser!=null) {
        	 if(!getDefault){	
        	  if((!pUser.getPrefLocaleCd().equalsIgnoreCase(RefCodeNames.LOCALE_CD.EN_US)) 
        			  || (pPropertyType.equals(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC))){
        		  String userLocale = pUser.getPrefLocaleCd(); 
        		  crit.addEqualTo(PropertyDataAccess.LOCALE_CD, userLocale);
        	  }else{
        		  crit.addIsNull(PropertyDataAccess.LOCALE_CD);
        	  }
        	 }else{
        		 crit.addIsNull(PropertyDataAccess.LOCALE_CD);
        	 }
        	  
          }
          
          if(pBusEntId!=null) {
            int busEntId = pBusEntId.intValue();
            if (busEntId > 0) {
              crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, busEntId);
            } else {
              crit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
            }
          }
          if (pPropertyType != null && pPropertyType.trim().length() > 0) {
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, pPropertyType);
          }
          pdv = PropertyDataAccess.select(conn, crit);
          return pdv;
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    public PropertyDataVector getPropertiesForLocale(UserData pUser, Integer pBusEntId, String pPropertyType)
        throws RemoteException {

    	PropertyDataVector pdv = null;
    	pdv = getPropertiesForLocale(pUser, pBusEntId, pPropertyType, false);
    	// if no CONTACT_US_TOPIC returned for user locale, then get the default CONTACT_US_TOPIC.
    	if((pPropertyType.equals(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC)) && ((pdv == null) || (pdv.size() <1))){
    		pdv = getPropertiesForLocale(pUser, pBusEntId, pPropertyType, true);
        }
    	return pdv;
    }
    
    /**
     *Gets the message resources for this bus entity id, gets all locales.  This means that
     *it is up to the caller to correctly determine between two message names/keys
     */
    public MessageResourceDataVector getMessageResources(int busEntityId)
    throws RemoteException{
        return getMessageResourcesWorker(busEntityId, (String) null, false);
    }

    /**
     *Gets the message resources for this bus entity id, with this locale
     */
    public MessageResourceDataVector getMessageResources(int busEntityId,String localKey)
    throws RemoteException{
        return getMessageResourcesWorker(busEntityId, localKey, false);
    }

    /**
     *Gets the message resources for this bus entity id, with this locale, and includes
     *nulls that are set, this is good for config so that the auditing of the message
     *resource table is not lost.
     */
    public MessageResourceDataVector getMessageResourcesWithNulls(int busEntityId,String localKey)
    throws RemoteException{
        return getMessageResourcesWorker(busEntityId, localKey, true);
    }

    /**
     *Gets the message resources
     */
    private MessageResourceDataVector getMessageResourcesWorker(int busEntityId,String localKey, boolean withNulls)
    throws RemoteException{

    	/*if(true){
			return new MessageResourceDataVector();
		}*/
        Connection con = null;
        try{
            con = getConnection();
            MessageResourceDataVector messageResourceDV = null;
            if(busEntityId != 0){
              DBCriteria crit = new DBCriteria();
              DBCriteria critDef = new DBCriteria();
              if(Utility.isSet(localKey)){
                crit.addEqualTo(MessageResourceDataAccess.LOCALE,localKey);
                critDef.addEqualTo(MessageResourceDataAccess.LOCALE,localKey);
              } else {
                crit.addIsNull(MessageResourceDataAccess.LOCALE);
                critDef.addIsNull(MessageResourceDataAccess.LOCALE);
              }
              crit.addEqualTo(MessageResourceDataAccess.BUS_ENTITY_ID,busEntityId);
              crit.addOrderBy(MessageResourceDataAccess.NAME);
              messageResourceDV = MessageResourceDataAccess.select(con,crit);
              critDef.addIsNull(MessageResourceDataAccess.BUS_ENTITY_ID);
              critDef.addOrderBy(MessageResourceDataAccess.NAME);
              MessageResourceDataVector mrDefDV =
                      MessageResourceDataAccess.select(con,critDef);
              MessageResourceDataVector mrDV = new MessageResourceDataVector();
              MessageResourceData wrkMessResD = null;
            for(Iterator iter=mrDefDV.iterator(),
                      iter1=messageResourceDV.iterator(); iter.hasNext();) {
                MessageResourceData mrDefD = (MessageResourceData) iter.next();
                String nameDef = mrDefD.getName();
                boolean foundFl = false;
                while(wrkMessResD!=null || iter1.hasNext()) {
                  if(wrkMessResD==null) wrkMessResD = (MessageResourceData) iter1.next();
                  int comp = nameDef.compareTo(wrkMessResD.getName());
                  if(comp==0) {
                    wrkMessResD = null;
                    foundFl = true;
                    break;
                  } else if(comp>0) {
                    wrkMessResD = null;
                    continue;
                  } else {
                    break;
                  }
                }
                if(!foundFl)  mrDV.add(mrDefD);
              }
              messageResourceDV.addAll(mrDV);
            }

            // no business entity specific messages were found
            // try again for the locale.  What if a business entity
            // only wants to cusomize some properties?  durval 2006-9-1
            if ( null == messageResourceDV || messageResourceDV.size() == 0 ) {
              DBCriteria crit = new DBCriteria();
              if(Utility.isSet(localKey)){
                crit.addEqualTo(MessageResourceDataAccess.LOCALE,localKey);
              }
              crit.addIsNull(MessageResourceDataAccess.BUS_ENTITY_ID);
              messageResourceDV = MessageResourceDataAccess.select(con,crit);
            }
            return messageResourceDV;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    public  MessageResourceDataVector getMessageResourcesByKey(int busEntityId,String key,String localeKey,String reqType)
     throws RemoteException{
        Connection con = null;
        try {
                con = getConnection();
                MessageResourceDataVector messageResourceDV = null;

                if((reqType==null)||(reqType!=null&&reqType.trim().length()==0))
                reqType="equal";

               DBCriteria crit = new DBCriteria();
               if(Utility.isSet(localeKey)){
                  crit.addEqualTo(MessageResourceDataAccess.LOCALE,localeKey);
                }
               else {
                 crit.addIsNull(MessageResourceDataAccess.LOCALE);
               }

              if(key!=null)
              {
              if(key.trim().length()>0){

              if(reqType.equals("contains"))
              crit.addContains(MessageResourceDataAccess.NAME,key);
              else if(reqType.equals("equal"))  crit.addEqualTo(MessageResourceDataAccess.NAME,key);

              }
              else if(key.trim().length()==0) crit.addIsNull(MessageResourceDataAccess.NAME);
              }

              if(busEntityId==0) crit.addIsNull(MessageResourceDataAccess.BUS_ENTITY_ID);
              else crit.addEqualTo(MessageResourceDataAccess.BUS_ENTITY_ID,busEntityId);

              return     MessageResourceDataAccess.select(con,crit);

            }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }


    }

    public HashMap getMessageResources(int busEntityId, String localeKey, String keyPrefix)
            throws RemoteException {
        Connection con = null;
        HashMap messageHM = new HashMap();
        try {
            con = getConnection();
            IdVector locales = new IdVector();
            locales.add("en");
            locales.add("en_US");
            String lang = "";
            if(localeKey!=null) {
                localeKey = localeKey.trim();
                locales.add(localeKey);
                if(localeKey.length()>2) {
                    locales.add(localeKey.substring(0, 2));
                }
            }

            DBCriteria crit = new DBCriteria();
            crit.addBeginsWith(MessageResourceDataAccess.NAME, keyPrefix);
            crit.addOneOf(MessageResourceDataAccess.LOCALE, locales);
            String storeCond = "("+ MessageResourceDataAccess.BUS_ENTITY_ID+
                    " is NULL OR "+MessageResourceDataAccess.BUS_ENTITY_ID+" = "+busEntityId+")";
            crit.addCondition(storeCond);
            MessageResourceDataVector messageResourceDV =
                    MessageResourceDataAccess.select(con, crit);
            for(Iterator iter=messageResourceDV.iterator(); iter.hasNext();) {
                MessageResourceData mrD = (MessageResourceData) iter.next();
                String key = mrD.getName();
                MessageResourceData wrkMrD = (MessageResourceData) messageHM.get(key);
                if(wrkMrD==null) {
                    messageHM.put(key, mrD);
                } else {
                    String wrkLc = wrkMrD.getLocale();
                    String lc = mrD.getLocale();
                    if(lc.equals(localeKey) && !lc.equals(wrkLc)) {
                        messageHM.put(key, mrD);
                    } else if("en".equals(wrkLc)) {
                        if("en_US".equals(lc) || lang.equals(lc) || localeKey.equals(lc)) {
                            messageHM.put(key, mrD);
                        }
                    } else if("en_US".equals(wrkLc)) {
                        if(lang.equals(lc) || localeKey.equals(lc)) {
                            messageHM.put(key, mrD);
                        }
                    } else if(lang.equals(wrkLc)) {
                        if(localeKey.equals(lc)) {
                            messageHM.put(key, mrD);
                        }
                    } else if(lc.equals(wrkLc) && mrD.getBusEntityId()>0) {
                        messageHM.put(key, mrD);
                    }
                }
            }
            return messageHM;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }


    }


    /**
     *Saves the message resources
     */
    public void saveMessageResources(MessageResourceDataVector pMessageResourceDataVector,String pUser)
            throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            Iterator it = pMessageResourceDataVector.iterator();
            while(it.hasNext()){
                MessageResourceData res = (MessageResourceData) it.next();
                if(res.getMessageResourceId() == 0){
                    MessageResourceDataVector controlReq =
                            getMessageResourcesByKey(res.getBusEntityId(), res.getName(), res.getLocale(), null);
                    if(controlReq!=null)  {
                        if(controlReq.size()==0)
                        {
                            res.setAddBy(pUser);
                            MessageResourceDataAccess.insert(con,res);
                        }
                        else
                        {
                            if(res.isDirty()){

                                res.setMessageResourceId(((MessageResourceData)controlReq.get(0)).getMessageResourceId());
                                res.setModBy(pUser);
                                MessageResourceDataAccess.update(con,res);
                            }
                        }
                    }
                }else{
                    if(res.isDirty()){
                        res.setModBy(pUser);
                        MessageResourceDataAccess.update(con,res);
                    }
                }
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /** NOTE DO NOT USE THIS METHOD UNLESS DOING YOUR OWN SECURITY CODING
    * TO MAKE SUER YOU WILL NOT CROSS STORES!!!!!!!!!
    */
    public ArrayList getBusEntityVector(String propertyName, String propertyValue)
        throws RemoteException, DataNotFoundException {

        Connection con = null;
        PropertyDataVector pdv = null;
        ArrayList beIds = new ArrayList();
            try {
                con = getConnection();

                DBCriteria crit = new DBCriteria();
                if(propertyName != null && propertyName.trim().length() > 0) {
                    crit.addEqualTo(PropertyDataAccess.SHORT_DESC, propertyName.trim().toUpperCase());
                    crit.addEqualTo(PropertyDataAccess.CLW_VALUE, propertyValue.trim());
                }
                pdv = PropertyDataAccess.select(con, crit);
                if (pdv.size() == 0) {
                    throw new DataNotFoundException("getBusEntityVector() no data returned. " +
                                                    "propertyName: (" + propertyName + ") " +
                                                    "propertyValue: (" + propertyValue + ")");
                }
                for (Iterator iter = pdv.iterator(); iter.hasNext();) {
                    PropertyData pD = (PropertyData) iter.next();

                    beIds.add(new Integer(pD.getBusEntityId()));
                }
            } catch (DataNotFoundException dex){
                throw new DataNotFoundException(dex.getMessage());
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeConnection(con);
            }
        return beIds;
    }

    /*
     * Returns a list of busEntityIds with specified property name and value,
     * specified number of days ago
     */
    public ArrayList getBusEntityVector(String propertyName, String propertyValue, int days)
    throws RemoteException {

    	Connection con = null;
        PropertyDataVector pdv = null;
        ArrayList beIds = new ArrayList();

            try {
                con = getConnection();
                Date current = new Date();

                if(days>0){
                	Utility.addDays(current, -days);
                }

                DBCriteria crit = new DBCriteria();
                if(propertyName != null && propertyName.trim().length() > 0) {
                    crit.addEqualTo(PropertyDataAccess.SHORT_DESC, propertyName.trim().toUpperCase());
                    crit.addEqualTo(PropertyDataAccess.CLW_VALUE, propertyValue.trim());
                    crit.addGreaterThan(PropertyDataAccess.MOD_DATE, current);
                }
                pdv = PropertyDataAccess.select(con, crit);
                if (pdv.size() == 0) {
                	return null;
                    /*throw new DataNotFoundException("getBusEntityVector() no data returned. " +
                                                    "propertyName: (" + propertyName + ") " +
                                                    "propertyValue: (" + propertyValue + ")");*/
                }
                for (Iterator iter = pdv.iterator(); iter.hasNext();) {
                    PropertyData pD = (PropertyData) iter.next();

                    beIds.add(new Integer(pD.getBusEntityId()));
                }
            /*} catch (DataNotFoundException dex){
                throw new DataNotFoundException(dex.getMessage());*/
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeConnection(con);
            }
        return beIds;

    }

    /*
     * Gets all users with specified property value
     */
    public ArrayList getUsers(String propertyName, String propertyValue)
    	throws RemoteException, DataNotFoundException {

    	Connection con = null;
        PropertyDataVector pdv = null;
        ArrayList userIds = new ArrayList();
            try {
                con = getConnection();

                DBCriteria crit = new DBCriteria();
                if(propertyName != null && propertyName.trim().length() > 0) {
                    crit.addEqualTo(PropertyDataAccess.SHORT_DESC, propertyName.trim().toUpperCase());
                    crit.addEqualTo(PropertyDataAccess.CLW_VALUE, propertyValue.trim());
                }
                pdv = PropertyDataAccess.select(con, crit);
                if (pdv.size() == 0) {
                    throw new DataNotFoundException("getUsers() no data returned. " +
                                                    "propertyName: (" + propertyName + ") " +
                                                    "propertyValue: (" + propertyValue + ")");
                }
                for (Iterator iter = pdv.iterator(); iter.hasNext();) {
                    PropertyData pD = (PropertyData) iter.next();

                    userIds.add(new Integer(pD.getUserId()));
                }
            } catch (DataNotFoundException dex){
                throw new DataNotFoundException(dex.getMessage());
            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeConnection(con);
            }
        return userIds;

    }

    public void update(PropertyData propertyData) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            if (propertyData.getPropertyId() > 0) {
                PropertyDataAccess.update(con, propertyData);
            } else {
                PropertyDataAccess.insert(con, propertyData);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    public void delete(int propertyId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            PropertyDataAccess.remove(con, propertyId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    public PropertyData getProperty(int propertyId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            PropertyData propertyData = PropertyDataAccess.select(con,
                    propertyId);
            return propertyData;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    public PropertyDataVector getProperties(IdVector busEntityIds,
            String shortDesc) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            if (busEntityIds != null && busEntityIds.size() > 0) {
                crit.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, busEntityIds);
            }
            crit.addEqualTo(PropertyDataAccess.SHORT_DESC, shortDesc);
            PropertyDataVector result = PropertyDataAccess.select(con, crit);
            return result;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }
    
    public PropertyData getAccessToken(String accessToken) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            PropertyData propertyData = null;
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.CLW_VALUE,accessToken);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.ACCESS_TOKEN);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            PropertyDataVector properties = PropertyDataAccess.select(con, crit);
            if (properties.size() > 0) {
            	propertyData = (PropertyData)properties.get(0);
            }
            return propertyData;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }
}
