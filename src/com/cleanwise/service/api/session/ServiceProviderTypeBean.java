package com.cleanwise.service.api.session;

/**
 * Title:        ServiceProviderTypeBean
 * Description:  Bean implementation for ServiceProviderType Stateless Session Bean
 * Purpose:      Provides access to the services for managing the ServiceProviderType information, properties and relationships.
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * @author       alexxey
 */

import javax.ejb.*;
import java.sql.*;
import java.rmi.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import java.util.Iterator;
import java.util.TreeMap;
import javax.naming.NamingException;

/**
 * <code>ServiceProviderType</code> stateless session bean.
 *
 */
public class ServiceProviderTypeBean extends BusEntityServicesAPI
{
    /**
     * Creates a new <code>ServiceProviderTypeBean</code> instance.
     *
     */
    public ServiceProviderTypeBean() {}

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Describe <code>getManufacturer</code> method here.
     *
     * @param pServiceProviderTypeId an <code>int</code> value
     * @return a <code>ServiceProviderTypeData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public BusEntityData getServiceProviderType(int pServiceProviderTypeId)
	throws RemoteException, DataNotFoundException {

	BusEntityData serviceProviderType = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    serviceProviderType = BusEntityDataAccess.select(conn, pServiceProviderTypeId);
	    if (serviceProviderType.getBusEntityTypeCd().compareTo(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER_TYPE) != 0) {
		throw new DataNotFoundException("Bus Entity is not ServiceProviderType");
	    }
	} catch (DataNotFoundException e) {
	    throw e;
	} catch (Exception e) {
	    throw new RemoteException("getBusEntity: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return serviceProviderType;
    }

    /**
     * Get all serviceProviderTypes that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the serviceProviderType
     * @return a <code>sBusEntityDataVector</code> of matching serviceProviderTypes
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getServiceProviderTypeByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
	BusEntityDataVector serviceProviderTypeDV = new BusEntityDataVector();
	Connection conn = null;
	try {
	    conn = getConnection();

	    serviceProviderTypeDV = BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER_TYPE);
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
	return serviceProviderTypeDV;
    }

    /**
     * Get all the ServiceProviderTypes.
     * @param pOrder one of BusEntityDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.SHORT_DESC
     * @return a <code>BusEntityDataVector</code> with all ServiceProviderTypes.
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getAllServiceProviderTypes(String pOrder)
	throws RemoteException {

	BusEntityDataVector ServiceProviderTypeDV = new BusEntityDataVector();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
			    RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER_TYPE);
            crit.addOrderBy(pOrder, true);
	    ServiceProviderTypeDV = BusEntityDataAccess.select(conn, crit);
	} catch (Exception e) {
	    throw new RemoteException("getServiceProviderTypes: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return ServiceProviderTypeDV;
    }
    
    /**
     * Get all the ServiceProviderTypes.
     * @param pOrder one of BusEntityDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.SHORT_DESC
     * @return a <code>BusEntityDataVector</code> with all ServiceProviderTypes.
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getUserServiceProviderTypes(int userId, int storeId)
	throws RemoteException {

        /*
          JOIN clw_user_assoc ua
               ON ua.user_id = 54257 AND
                  ua.user_assoc_cd = 'SERVICE_PROVIDER'
          JOIN clw_bus_entity_assoc bea
               ON ua.bus_entity_id = bea.bus_entity1_id AND
                  bea.bus_entity_assoc_cd = 'SERVICE PROVIDER TO TYPE ASSOC' AND 
                  be.bus_entity_id = bea.bus_entity2_id
          JOIN clw_bus_entity_assoc bea_store
               ON bea_store.bus_entity1_id = bea.bus_entity2_id AND
                  bea_store.bus_entity_assoc_cd = 'STORE SERVICE PROVIDER TYPE' AND
                  bea_store.bus_entity2_id = 1;
         
         */

	BusEntityDataVector ServiceProviderTypeDV = new BusEntityDataVector();

	Connection conn = null;
        try {
            if (storeId > 0 && userId > 0) {
                conn = getConnection();
                DBCriteria crit = new DBCriteria();
                DBCriteria isolCrit = new DBCriteria();
                
                crit.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);
                crit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA_TYPE");
                crit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA_STORE");

                isolCrit.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ID, userId);
                isolCrit.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ASSOC_CD,
                                    RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER);
                
                isolCrit.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID + " = " +
                                    "BEA_TYPE." + BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                isolCrit.addEqualTo("BEA_TYPE." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_TO_TYPE_ASSOC);
                
                isolCrit.addCondition("BEA_STORE." + BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = " +
                                    "BEA_TYPE." +  BusEntityAssocDataAccess.BUS_ENTITY2_ID);
                isolCrit.addEqualTo("BEA_STORE." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                                    RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_SERVICE_PROVIDER_TYPE);
                isolCrit.addEqualTo("BEA_STORE." + BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeId);

                crit.addIsolatedCriterita(isolCrit);

                crit.addCondition(BusEntityDataAccess.BUS_ENTITY_ID + " = " +
			    "BEA_TYPE." +  BusEntityAssocDataAccess.BUS_ENTITY2_ID);
                crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
                
                BusEntityDataVector types = BusEntityDataAccess.select(conn, crit);
                
                TreeMap map = new TreeMap();
                for (int i = 0; i < types.size(); i++) {
                    map.put(((BusEntityData) types.get(i)).getShortDesc(), types.get(i));
                }
                Iterator it = map.values().iterator();
                while (it.hasNext()) {
                    ServiceProviderTypeDV.add(it.next());
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("getServiceProviderTypesForStore: " + e.toString());
        } catch (NamingException e) {
            throw new RemoteException("getServiceProviderTypesForStore: " + e.toString());
        } finally {
            closeConnection(conn);
            return ServiceProviderTypeDV;
        }
    }
    
    /**
     * Describe <code>addServiceProviderType</code> method here.
     *
     * @param pServiceProviderTypeD a <code>BusEntityData</code> value
     * @return a <code>BusEntityData</code> value
     * @exception RemoteException if an error occurs
     */
    public BusEntityData addServiceProviderType(BusEntityData pServiceProviderTypeD, int storeId)
	throws RemoteException
    {
	return updateServiceProviderType(pServiceProviderTypeD, storeId);
    }

    /**
     * Updates the ServiceProviderType information values to be used by the request.
     * @param pServiceProviderTypeD  the BusEntityData ServiceProviderType data.
     * @param storeId the Store the Service Provider Type is intended for.
     * @return a <code>BusEntityData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public BusEntityData updateServiceProviderType(BusEntityData pServiceProviderTypeD, int storeId)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    if (pServiceProviderTypeD.isDirty()) {
		if (pServiceProviderTypeD.getBusEntityId() == 0) {
		    pServiceProviderTypeD.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER_TYPE);
		    BusEntityDataAccess.insert(conn, pServiceProviderTypeD);
		} else {
		    BusEntityDataAccess.update(conn, pServiceProviderTypeD);
		}
	    }
	    int serviceProviderTypeId = pServiceProviderTypeD.getBusEntityId();
            
            saveBusEntAssociation(true, serviceProviderTypeId, storeId, RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_SERVICE_PROVIDER_TYPE, conn);
            
	} catch (Exception e) {
	    throw new RemoteException("updateServiceProviderType: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pServiceProviderTypeD;
    }

    /**
     * <code>removeServiceProviderType</code> may be used to remove an 'unused' ServiceProviderType.
     * An unused ServiceProviderType is a ServiceProviderType with no database references.
     * Attempting to remove a ServiceProviderType that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pManufacturerData a <code>ManufacturerData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeServiceProviderType(int serviceProviderTypeId)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();

	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, serviceProviderTypeId);
            DBCriteria aCrit = new DBCriteria();
            aCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, serviceProviderTypeId);
            
            BusEntityAssocDataAccess.remove(conn, aCrit);
	    BusEntityDataAccess.remove(conn, serviceProviderTypeId);
	} catch (Exception e) {
	    throw new RemoteException("removeServiceProviderType: " + e.getMessage());
	} finally {
	    closeConnection(conn);
	}
    }

    /**
     * Gets the serviceProviderType information values to be used by the request.
     * @param storeId an <code>int</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return BusEntityDataVector
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if store with storeId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public BusEntityDataVector getServiceProviderTypesForStore(int storeId, boolean pInactiveFl)
        throws RemoteException, DataNotFoundException {
        BusEntityDataVector serviceProviderTypesDV = new BusEntityDataVector();
                
        if (storeId > 0) {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_SERVICE_PROVIDER_TYPE);
            
            String serviceProviderTypesReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);
          
          crit = new DBCriteria();
          if (pInactiveFl) {
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
          } else {
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
          }
          crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, serviceProviderTypesReq);
          crit.addOrderBy(BusEntityDataAccess.SHORT_DESC);
          
          Connection conn = null;
            try {
	    
                conn = getConnection();
                serviceProviderTypesDV = BusEntityDataAccess.select(conn, crit);
                
                return serviceProviderTypesDV;
            } catch (SQLException e) {
                throw new RemoteException("getServiceProviderTypesForStore: " + e.toString());
            } catch (NamingException e) {
                throw new RemoteException("getServiceProviderTypesForStore: " + e.toString());
            } finally {
                closeConnection(conn);
            } 
        }
        throw new DataNotFoundException("Store is not found");
    }

}



