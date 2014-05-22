package com.cleanwise.service.api.util;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Properties;

import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;

/**
 *  The <code>PropertyUtil</code> implements the <code>
 *
 *
 *
 *
 *@author     durval
 *@created    August 29, 2001
 *@see        com.cleanwise.service.api.session.PropertyService </code>
 *      interface.
 *@see        com.cleanwise.service.api.session.PropertyService
 */
public class PropertyUtil {

    Connection mDbConn;

    /**
     *  Creates a new <code>PropertyUtil</code> instance.
     *
     *@param  pDbConn  Description of Parameter
     */
    public PropertyUtil(Connection pDbConn) {
        mDbConn = pDbConn;
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
        return fetchProperties(0, 0, null);
    }


    /**
     *  Get a general application property. <code>getProperty</code> search for
     *  the property in memory. If the property is not found in memory, look in
     *  the database.
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
     *  Helper method to save a property.
     *
     *@param  pUserId              Description of Parameter
     *@param  pBusEntId            Description of Parameter
     *@param  pName                Description of Parameter
     *@param  pVal                 Description of Parameter
     *@param  pType                Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    public void saveValue(int pUserId, int pBusEntId,
            String pType, String pName, String pVal)
             throws RemoteException {
        saveValue(pUserId, pBusEntId, pType, pName, pVal, null);
    }

    /**
     *  Helper method to save a property.
     *
     *@param  pUserId              Description of Parameter
     *@param  pBusEntId            Description of Parameter
     *@param  pName                Description of Parameter
     *@param  pVal                 Description of Parameter
     *@param  pType                Description of Parameter
     *@param  pLocaleCd            Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    public void saveValue(int pUserId, int pBusEntId,
            String pType, String pName, String pVal, String pLocaleCd)
             throws RemoteException {
        dbSaveValue(pUserId, pBusEntId, pType, pName, pVal, pLocaleCd);
    }

    public void saveDateValue(int pUserId, int pBusEntId,
                              String pType, String pName,
                              java.util.Date pDateVal)
             throws RemoteException {
       saveDateValue(pUserId, pBusEntId, pType, pName, pDateVal, null);      
    }

    public void saveDateValue(int pUserId, int pBusEntId,
                              String pType, String pName,
                              java.util.Date pDateVal, String pLocaleCd)
             throws RemoteException {

        DateFormat df = DateFormat.getDateInstance();

        dbSaveValue(pUserId, pBusEntId, pType, pName,
                    df.format(pDateVal), pLocaleCd );
    }

    public void saveDateValue(int pUserId, int pBusEntId,
                              String pType, String pName,
                              java.util.Date pDateVal, String pattern, String pLocaleCd) throws RemoteException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        dbSaveValue(pUserId, pBusEntId, pType, pName, sdf.format(pDateVal), pLocaleCd);
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
    public void removeProperty
            (int pUserId, int pBusEntId, String pName)
             throws DataNotFoundException, Exception {

        PropertyData lp = null;

        try {
            DBCriteria lcrit = new DBCriteria();

            lcrit.addEqualTo(PropertyDataAccess.SHORT_DESC, pName);

            if (pUserId > 0) {
                lcrit.addEqualTo(PropertyDataAccess.USER_ID,
                        pUserId);
            }
            else {
                lcrit.addIsNull(PropertyDataAccess.USER_ID);
            }

            if (pBusEntId > 0) {
                lcrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                        pBusEntId);
            }
            else {
                lcrit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
            }

            try {
                PropertyDataAccess.remove(mDbConn, lcrit);
            }
            catch (SQLException se) {
                throw new Exception("SQL ex " + se.getMessage());
            }
        }
        catch (Exception e) {
            String msg = this.getClass().getName() +
                    ".removeProperty error: " + e.getMessage();
            throw new Exception(msg);
        }
    }


    /**
     *  Helper method to get a collection of properties.
     *
     *@param  pUserId                    Description of Parameter
     *@param  pBusEntId                  Description of Parameter
     *@param  pType                      Description of Parameter
     *@return                            Description of the Returned Value
     *@exception  DataNotFoundException  Description of Exception
     */
    public Properties fetchProperties(int pUserId, int pBusEntId, String pType)
             throws DataNotFoundException {
      return fetchProperties(pUserId, pBusEntId, pType, null);
    }

    /**
     *  Helper method to get a collection of properties.
     *
     *@param  pUserId                    Description of Parameter
     *@param  pBusEntId                  Description of Parameter
     *@param  pType                      Description of Parameter
     *@param  pLocaleCd                  Description of Parameter
     *@return                            Description of the Returned Value
     *@exception  DataNotFoundException  Description of Exception
     */
    public Properties fetchProperties(int pUserId, int pBusEntId, String pType, String pLocaleCd)
             throws DataNotFoundException {

        // Fetch the properties.
        try {
            DBCriteria crit = new DBCriteria();
            DBCriteria localeCrit = new DBCriteria();
            if (pUserId > 0) {
                crit.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
                localeCrit.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
            }
            else {
                crit.addIsNull(PropertyDataAccess.USER_ID);
                localeCrit.addIsNull(PropertyDataAccess.USER_ID);
            }
            if (pBusEntId > 0) {
                crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pBusEntId);
                localeCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pBusEntId);
            }
            else {
                crit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
                localeCrit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
            }
            if (pType != null && pType.length() > 0) {
                crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, pType);
                localeCrit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, pType);
            }
            crit.addIsNull(PropertyDataAccess.LOCALE_CD);
            localeCrit.addEqualTo(PropertyDataAccess.LOCALE_CD,pLocaleCd);


          Properties op = new Properties();
          PropertyData lpd;
          if(pLocaleCd!=null) {
            PropertyDataVector localePdv = PropertyDataAccess.select(mDbConn, localeCrit);
            for(Iterator iter=localePdv.iterator(); iter.hasNext();) {
              lpd = (PropertyData) iter.next();
              String tname = lpd.getShortDesc();
              if (tname == null) {
                  continue;  // A null name is not allowed.
              }
              String tval = lpd.getValue();
             // if (tval == null) {
             //     tval = "";
             //}
             if (tval != null)
                op.put(tname, tval);              
            }            
          }
          PropertyDataVector pdv = PropertyDataAccess.select(mDbConn, crit);            
          for (Iterator iter = pdv.iterator(); iter.hasNext();) {
            lpd = (PropertyData) iter.next();
            String tname = lpd.getShortDesc();
            if (tname == null) {
                continue;  // A null name is not allowed.
            }
            String tval = lpd.getValue();
            if (tval == null) {
                tval = "";
            }
            if(!op.containsKey(tname)) {
              op.put(tname, tval);
            }
          }

          if (op.isEmpty()) {
              throw new DataNotFoundException("fetchProperties:" +
                      " user " + pUserId +
                      " business entity " + pBusEntId);
          }

          return op;
        } catch (Exception exc) {
            //exc.printStackTrace();
            throw new DataNotFoundException("fetchProperties:" +
                    " user " + pUserId +
                    " business entity " + pBusEntId);
        }
    }

    /**
     *  Helper method to fetch a value for a property.
     *
     *@param  pUserId                    
     *@param  pBusEntId                  
     *@param  pName                      The property type @see RefCodeNames.PROPERTY_TYPE_CD
     *@return                            the property value
     *@exception  RemoteException        Description of Exception
     *@exception  DataNotFoundException  Thrown when this property was not found
     */
    public String fetchValue(int pUserId, int pBusEntId, String pName)
             throws RemoteException, DataNotFoundException {
       return fetchValue(pUserId, pBusEntId, pName, true);
    }

    /**
     *  Helper method to fetch a value for a property.
     *
     *@param  pUserId                    
     *@param  pBusEntId                  
     *@param  pName                      The property type @see RefCodeNames.PROPERTY_TYPE_CD
     *@param  pThrowDataNotFound         if true a @see DataNotFound exception will 
                                         be generated if the property was not found.
     *@return                            the property value
     *@exception  RemoteException        Description of Exception
     *@exception  DataNotFoundException  Thrown when this property was not found
     */
    private String fetchValue(int pUserId, int pBusEntId, String pName, boolean pThrowDataNotFound)
             throws RemoteException, DataNotFoundException {
        try {
            return dbGetProperty(pUserId, pBusEntId, pName, pThrowDataNotFound);
        }
        catch (DataNotFoundException e) {
            throw new DataNotFoundException(" PropertyUtil.fetchValue: " +
					    " property not found: " +
					    e.getMessage());
        }
        catch (Exception e) {
            throw new RemoteException(" PropertyUtil.fetchValue: error " +
                    e.getMessage());
        }
    }
    
    /**
     * Helper method to fetch a value for a property.  identical to @see fetchValue
     * but does not throw an exception when the value is null.
     *
     *@param  pUserId                    
     *@param  pBusEntId                  
     *@param  pName                      The property type @see RefCodeNames.PROPERTY_TYPE_CD
     *@exception  RemoteException        Description of Exception
     */
    public String fetchValueIgnoreMissing(int pUserId, int pBusEntId, String pName)
    throws RemoteException{
        try{
            return fetchValue(pUserId, pBusEntId, pName,false);
        }catch (DataNotFoundException e) {
            throw new RemoteException("Unexpected DataNotFoundException occured in fetchValueIgnoreMissing");
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
     *@param  pType                Description of Parameter
     *@exception  RemoteException  Description of Exception
     */
    private void dbSaveValue(PropertyData pD)
             throws RemoteException {
	dbSaveValue(pD.getUserId(), pD.getBusEntityId(),
		    pD.getPropertyTypeCd(), pD.getShortDesc(), 
		    pD.getValue(), pD.getLocaleCd());
    }
    private void dbSaveValue(int pUserId, int pBusEntId,
            String pType, String pName, String pValue,String pLocaleCd)
             throws RemoteException {

        try {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.SHORT_DESC, pName);
            if (pType != null && pType.length() > 0) {
                crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, pType);
            }
            else {
                // Since no type was specified, use the name.
                pType = pName;
            }

            if (pUserId > 0) {
                crit.addEqualTo(PropertyDataAccess.USER_ID, pUserId);
            }
            else {
                crit.addIsNull(PropertyDataAccess.USER_ID);
            }
            if (pBusEntId > 0) {
                crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pBusEntId);
            }
            else {
                crit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
            }
            if(Utility.isSet(pLocaleCd)) {
               crit.addEqualTo(PropertyDataAccess.LOCALE_CD, pLocaleCd);
            } else {
               pLocaleCd = null;
               crit.addIsNull(PropertyDataAccess.LOCALE_CD);
            }

            PropertyDataVector pdv = null;
            try {
                pdv = PropertyDataAccess.select(mDbConn, crit);
            }
            catch (Exception e) {
            }

            if (pdv==null || pdv.size() == 0) {
                // No db entry was found.
                PropertyData pd = toPropertyData
		    (pUserId, pBusEntId,pType,  pName,  pValue, pLocaleCd);
                // Insert the new record.
		        PropertyDataAccess.insert(mDbConn, pd);
            }
            else {
                PropertyData pd = (PropertyData) pdv.get(0);
                pd.setValue(pValue);

		// let the dao layer set the mod date
                pd.setModDate(null);
				PropertyDataAccess.update(mDbConn, pd);
			}
        }
        catch (Exception e) {
            String msg = "dbSaveValue error: " + e.getMessage();
            throw new RemoteException(msg);
        }
    }


    /**
     *  Helper method to retrieve a property.
     *
     *@param  pUserId                    Description of Parameter
     *@param  pBusEntId                  Description of Parameter
     *@param  pName                      Description of Parameter
     *@return                            Description of the Returned Value
     *@exception  DataNotFoundException  Description of Exception
     *@exception  Exception              Description of Exception
     */
    private String dbGetProperty(int pUserId, int pBusEntId, String pName, boolean throwDataNotFound)
             throws DataNotFoundException, Exception {

	DBCriteria lcrit = new DBCriteria();
        try {
            lcrit.addEqualTo(PropertyDataAccess.SHORT_DESC, pName);

            if (pUserId > 0) {
                lcrit.addEqualTo(PropertyDataAccess.USER_ID,
                        pUserId);
            }
            else {
                lcrit.addIsNull(PropertyDataAccess.USER_ID);
            }

            if (pBusEntId > 0) {
                lcrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                        pBusEntId);
            }
            else {
                lcrit.addIsNull(PropertyDataAccess.BUS_ENTITY_ID);
            }
            if (mMaxModAge > 0) 
            {
                lcrit.addCondition( " mod_date > sysdate - " + mMaxModAge );
            }
            
        }
        catch (Exception e) {
            String msg = this.getClass().getName() +
		".dbGetProperty error: " + e.getMessage();
            throw new Exception(msg);
        }

	PropertyDataVector pdv = null;
	try {
	    pdv = PropertyDataAccess.select(mDbConn, lcrit);

	    if (null != pdv && pdv.size() > 0) {
		String v = ((PropertyData) pdv.get(0)).getValue();
		if ( null == v ) {
		    return "";
		}
		return v;
	    }
	}
	catch (SQLException se) {
	    throw new Exception("SQL ex " + se.getMessage());
	}

	if (null == pdv || pdv.size() == 0) {
            if(throwDataNotFound){
                throw new DataNotFoundException
                    (" dbGetProperty property=" +
                     pName +" user id=" + pUserId +
                     " business entity id=" + pBusEntId);
            }
	}

	return null;
    }

    int mMaxModAge = 0;
    public java.util.Date getAsDate( int pUserId,
                                     int pBusEntId,
                                     String pName, int pOptionalAge)
        throws RemoteException, DataNotFoundException {
        try {
            mMaxModAge = pOptionalAge;
            String t = dbGetProperty(pUserId, pBusEntId, pName, true);
            mMaxModAge = 0;
            if ( t == null || t.length() == 0 )
            {
                return null;
            }
            DateFormat df = DateFormat.getDateInstance();
            return df.parse(t);
        }
        catch (DataNotFoundException e) {
            throw new DataNotFoundException(" PropertyUtil.getAsDate: " +
					    " property not found: " +
					    e.getMessage());
        }
        catch (Exception e) {
            throw new RemoteException(" PropertyUtil.getAsDate: error " +
                    e.getMessage());
        }
    }

    public java.util.Date getAsDate(int pUserId,
                                    int pBusEntId,
                                    String pName,
                                    String pattern,
                                    int pOptionalAge) throws RemoteException, DataNotFoundException {
        try {
            mMaxModAge = pOptionalAge;
            String t = dbGetProperty(pUserId, pBusEntId, pName, true);
            mMaxModAge = 0;
            if (t == null || t.length() == 0) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(t);
        }
        catch (DataNotFoundException e) {
            String errMess="PropertyUtil.getAsDate: property not found: " + e.getMessage();
            throw new DataNotFoundException(errMess);
        }
        catch (Exception e) {
            String errMess="PropertyUtil.getAsDate: error " + e.getMessage();
            throw new RemoteException(errMess);
        }
    }

    public void saveCollection(PropertyDataVector v)
	throws RemoteException {
	for ( int i = 0; null != v && i < v.size(); i++ ) {
	    dbSaveValue((PropertyData)v.get(i));
	}
    }

    public static PropertyData toPropertyData(int pUserId, int pBusEntId,
            String pType, String pName, String pValue)
    {
      return toPropertyData(pUserId, pBusEntId, pType, pName, pValue, null);
    }
    public static PropertyData toPropertyData(int pUserId, int pBusEntId,
            String pType, String pName, String pValue, String pLocaleCd)
    {

	PropertyData pd = PropertyData.createValue();
	pd.setValue(pValue);
	pd.setUserId(pUserId);
	pd.setBusEntityId(pBusEntId);
	pd.setShortDesc(pName);
	String s = "-";
	pd.setPropertyStatusCd(s);
	pd.setPropertyTypeCd(pType);
  pd.setLocaleCd(pLocaleCd);
	pd.setAddDate(new java.util.Date());
	pd.setAddBy(s);
	pd.setModDate(new java.util.Date());
	pd.setModBy(s);

	return pd;
    }
    
    public static String getPropertyValue(PropertyDataVector properties, String pType)
    {
    	for (int ii = 0; ii < properties.size(); ii++) {
            PropertyData pD = (PropertyData) properties.get(ii);
            String propType = pD.getPropertyTypeCd();
            if (pType.equals(propType)) {
            	return pD.getValue();
            }
        }
    	return null;
    }

}


