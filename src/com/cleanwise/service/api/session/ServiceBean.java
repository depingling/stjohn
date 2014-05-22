package com.cleanwise.service.api.session;

import javax.ejb.*;
import javax.naming.NamingException;

import java.sql.*;
import java.rmi.*;
import java.util.Date;
import java.util.Iterator;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.ServiceDAO;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;

public class ServiceBean extends BusEntityServicesAPI
{
    /**
     * Creates a new <code>ServiceProviderBean</code> instance.
     *
     */
    public ServiceBean() {}

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Describe <code>getServiceProviderDetails</code> method here.
     *
     * @param pBusEntity a <code>BusEntityData</code> value
     * @return a <code>ServiceProviderData</code> value
     * @exception RemoteException if an error occurs
     */
    private ServiceProviderData getServiceProviderDetails(BusEntityData pBusEntity)
	throws RemoteException {

	AddressData primaryAddress = null;
	PhoneData primaryPhone = null;
	PhoneData primaryFax = null;
	EmailData primaryEmail = null;
	PropertyData serviceProvider = null;
        String[] serviceProviderType = null;
//	PropertyData businessClass = null;
//	PropertyData womanOwnedBusiness = null;
//	PropertyData minorityOwnedBusiness = null;
//	PropertyData jWOD = null;
//	PropertyData otherBusiness = null;
//	PropertyDataVector specializations = null;
//	PropertyDataVector miscProperties = null;
        int storeId = 0;
        
	Connection conn = null;

	try {
	    conn = getConnection();

	    // get service provider address
	    DBCriteria addressCrit = new DBCriteria();
	    addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
				   pBusEntity.getBusEntityId());
	    addressCrit.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
	    AddressDataVector addressVec =
		AddressDataAccess.select(conn, addressCrit);
	    // if more than one primary address, for now we don't care
	    if (addressVec.size() > 0) {
		primaryAddress = (AddressData)addressVec.get(0);
	    }

	    // get related email addresses
	    DBCriteria emailCrit = new DBCriteria();
	    emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,
				   pBusEntity.getBusEntityId());
	    emailCrit.addEqualTo(EmailDataAccess.PRIMARY_IND, true);
	    EmailDataVector emailVec =
		EmailDataAccess.select(conn, emailCrit);
	    // if more than one primary email address, for now we don't care
	    if (emailVec.size() > 0) {
		primaryEmail = (EmailData)emailVec.get(0);
	    }

	    // get related phones
	    DBCriteria phoneCrit = new DBCriteria();
	    phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID,
				   pBusEntity.getBusEntityId());
	    phoneCrit.addEqualTo(PhoneDataAccess.PRIMARY_IND, true);
	    PhoneDataVector phoneVec =
		PhoneDataAccess.select(conn, phoneCrit);
	    Iterator phoneIter = phoneVec.iterator();
	    while (phoneIter.hasNext()) {
		PhoneData phone = (PhoneData)phoneIter.next();
		String phoneType = phone.getPhoneTypeCd();
		if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.PHONE) == 0) {
		    primaryPhone = phone;
		} else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.FAX) == 0) {
		    primaryFax = phone;
		} else {
		    // ignore - unidentified phone
		}
	    }

            IdVector storeIds = getBusEntityAssoc2Ids(
            	conn, pBusEntity.getBusEntityId(), RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE
            );
            if(storeIds != null && !storeIds.isEmpty()){
                storeId = ((Integer) storeIds.get(0)).intValue();
            }
            
	    // get properties
	    DBCriteria propCrit = new DBCriteria();
	    propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
				pBusEntity.getBusEntityId());
	    PropertyDataVector props = 
		PropertyDataAccess.select(conn, propCrit);
	    Iterator propIter = props.iterator();
//	    specializations = new PropertyDataVector();
//	    miscProperties = new PropertyDataVector();
	    while (propIter.hasNext()) {
		PropertyData prop = (PropertyData)propIter.next();
		String propTypeCd = prop.getPropertyTypeCd();
		if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.SERVICE_PROVIDER_CD)) {
			serviceProvider = prop;
		}
//		if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.MANUFACTURER_SPECIALIZATION)) {
//		    specializations.add(prop);
//		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.EXTRA)) {
//		    miscProperties.add(prop);
//		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.BUSINESS_CLASS)) {
//		    businessClass = prop;
//		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.JWOD)) {
//		    womanOwnedBusiness = prop;
//		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.MINORITY_OWNED_BUSINESS)) {
//		    minorityOwnedBusiness = prop;
//		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.OTHER_BUSINESS)) {
//		    jWOD = prop;
//		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.WOMAN_OWNED_BUSINESS)) {
//		    otherBusiness = prop;
//		} else {
//		    // unknown property type - ignore
//		}
	    }
            
            // get Service Provider Type
	    DBCriteria srvProviderTypeCrit = new DBCriteria();
	    srvProviderTypeCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                                                        pBusEntity.getBusEntityId());
            srvProviderTypeCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                                                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_TO_TYPE_ASSOC);
	    BusEntityAssocDataVector srvProviderTypeDV = 
                    BusEntityAssocDataAccess.select(conn, srvProviderTypeCrit);
            
            int size = srvProviderTypeDV.size();
            serviceProviderType = new String[size];
            for(int i = 0; i < size; i++) {
                serviceProviderType[i] = Integer.toString(((BusEntityAssocData)srvProviderTypeDV.get(i)).getBusEntity2Id());
            }
	} catch (Exception e) {
	    throw new RemoteException("getServiceProviderDetails: " + e.toString());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return new ServiceProviderData(pBusEntity, 
                                    storeId,
				    primaryAddress, 
				    primaryPhone, 
				    primaryFax, 
				    primaryEmail,
				    serviceProvider,
                                    serviceProviderType);
    }

    /**
     * Describe <code>getServiceProvider</code> method here.
     *
     * @param pServiceProviderId an <code>int</code> value
     * @return a <code>ServiceProviderData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public ServiceProviderData getServiceProvider(int pServiceProviderId)
	throws RemoteException, DataNotFoundException {


	Connection conn = null;
	try {
	    conn = getConnection();

	    return getServiceProvider(conn, pServiceProviderId);
	    
	} catch (DataNotFoundException e) {
	    throw e;
	} catch (Exception e) {
	    throw new RemoteException("getBusEntity: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

    }

	public ServiceProviderData getServiceProvider(Connection conn,
			int pServiceProviderId) throws SQLException, DataNotFoundException,
			RemoteException {
		BusEntityData busEntity = 
		BusEntityDataAccess.select(conn, pServiceProviderId);
	    if (busEntity.getBusEntityTypeCd().compareTo(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER) != 0) {
		throw new DataNotFoundException("Bus Entity not service provider");
	    }
	    return getServiceProviderDetails(busEntity);
	}

    /**
     * Get all service providers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the service provider
     * @return a <code>ServiceProviderDataVector</code> of matching service providers
     * @exception RemoteException if an error occurs
     */
    public ServiceProviderDataVector getServiceProviderByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
	ServiceProviderDataVector serviceProviderVec = new ServiceProviderDataVector();
	Connection conn = null;
	try {
	    conn = getConnection();

	    BusEntityDataVector busEntityVec =
                BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
	    Iterator iter = busEntityVec.iterator();
	    while (iter.hasNext()) {
		serviceProviderVec.add(getServiceProviderDetails((BusEntityData)iter.next()));
	    }
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
	return serviceProviderVec;
    }
    
    /**
     * Get all service providers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the service provider
     * @return a <code>BusEntityDataVector</code> of matching service providers
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getServiceProviderBusEntitiesByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
	Connection conn = null;
	try {
	    conn = getConnection();
	    return BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);   
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
    }

    /**
     * Get all the service providers.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>ServiceProviderDataVector</code> with all service providers.
     * @exception RemoteException if an error occurs
     */
    public ServiceProviderDataVector getAllServiceProviders(int pOrder)
	throws RemoteException {

	ServiceProviderDataVector serviceProviderVec = new ServiceProviderDataVector ();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
			    RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
	    switch (pOrder) {
	    case Service.ORDER_BY_ID:
		crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
		break;
	    case Service.ORDER_BY_NAME:
		crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
		break;
	    default:
		throw new RemoteException("getServiceProviderByName: Bad order specification");
	    }
	    BusEntityDataVector busEntityVec = 
		BusEntityDataAccess.select(conn, crit);

	    Iterator iter = busEntityVec.iterator();
	    while (iter.hasNext()) {
		serviceProviderVec.add(getServiceProviderDetails((BusEntityData)iter.next()));
	    }
	} catch (Exception e) {
	    throw new RemoteException("getAllServiceProviders: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return serviceProviderVec;
    }

    /**
     *  Gets bus_entity account object sites assigned to the service provider
     *
     *@param  pSvcProviderId      the  service provider id
     *@param pStoreId   the store id
     *@param pGetInactiveFl filters out inactive accounts if false
     *@return a set of BusEnityData objects
     *@exception  RemoteException        Description of Exception
     */
    public IdVector getSvcProviderAccountIds(int pSvcProviderId, int pStoreId, boolean pGetInactiveFl)
        throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pSvcProviderId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_ACCOUNT);
            String acctSPIdReq =
            	BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, acctSPIdReq);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctSPStoreReq =
               BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, acctSPStoreReq);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            if(!pGetInactiveFl) {
              dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            }
            IdVector acctIdV =
                    BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID, dbc);
            return acctIdV;
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. ServiceBean.getSvcProviderAccountIds. "
                + exc.getMessage());
        } finally {
            closeConnection(conn);
        }

    }
    
    /**
     *  Gets sites assigned to the service provider
     *
     *@param  pSvcProviderId    the  service provider id
     *@param pStoreId           the store id
     *@param pGetInactiveFl filters out inactive sites if false
     *@return a vector of site Ids of the given Service Provider
     *@exception  RemoteException        Description of Exception
     */
    public IdVector getSvcProviderSiteIds(int pSvcProviderId, int pStoreId, boolean pGetInactiveFl)
        throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            
            String sql = "SELECT bea1.bus_entity2_id FROM clw_bus_entity_assoc bea1 \n\r" +
                         " JOIN clw_bus_entity_assoc bea2 \n\r" +
                         " ON bea2.bus_entity1_id = bea1.bus_entity2_id \n\r" +
                         " AND bea2.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n\r" +
                         " JOIN clw_bus_entity_assoc bea3 \n\r" +
                         " ON bea3.bus_entity1_id = bea2.bus_entity2_id \n\r" +
                         " AND bea3.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "'\n\r" +
                         " AND bea3.bus_entity2_id = " + pStoreId + "\n\r";
                         if (!pGetInactiveFl) {
                             sql += " JOIN clw_bus_entity be \n\r" +
                                    " ON be.bus_entity_id = bea2.bus_entity1_id \n\r" +
                                    " AND be.bus_entity_status_cd <> '" + RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE + "'\n\r";
                         }
                          
                    sql += " WHERE bea1.bus_entity1_id = " + pSvcProviderId + "\n\r" +
                           " AND bea1.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE + "'";
                    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            IdVector sitesIdV = new IdVector();
            while (rs.next()) {
                sitesIdV.add(Integer.valueOf(rs.getInt(1)));
            }

            return sitesIdV;
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("Error. ServiceBean.getSvcProviderSiteIds. "
                + exc.getMessage());
        } finally {
            closeConnection(conn);
        }

    }
    
    /**
     * Describe <code>addServiceProvider</code> method here.
     *
     * @param pServiceProviderData a <code>ServiceProviderData</code> value
     * @return a <code>ServiceProviderData</code> value
     * @exception RemoteException if an error occurs
     */
    public ServiceProviderData addServiceProvider(ServiceProviderData pServiceProviderData)
	throws RemoteException
    {
	return updateServiceProvider(pServiceProviderData);
    }

    /**
     * Updates the service provider information values to be used by the request.
     * @param pServiceProviderData  the ServiceProviderData service provider data.
     * @return a <code>ServiceProviderData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public ServiceProviderData updateServiceProvider(ServiceProviderData pServiceProviderData)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    BusEntityData busEntity = pServiceProviderData.getBusEntity();
	    if (busEntity.isDirty()) {
		if (busEntity.getBusEntityId() == 0) {
		    busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
		    BusEntityDataAccess.insert(conn, busEntity);
		} else {
		    BusEntityDataAccess.update(conn, busEntity);
		}
	    }
	    int serviceProviderId = pServiceProviderData.getBusEntity().getBusEntityId();
            
            saveBusEntAssociation(true,serviceProviderId,pServiceProviderData.getStoreId(),RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE,conn);
            
            int serviceProviderTypeId = 0;
            int size = pServiceProviderData.getServiceProviderTypeId().length;
            if (size > 0) {
                saveBusEntAssociation(  true,
                                        serviceProviderId,
                                        Integer.parseInt(pServiceProviderData.getServiceProviderTypeId()[0]),
                                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_TO_TYPE_ASSOC,
                                        conn);
            }
            for(int i = 1;  i < size; i++ ) {
                serviceProviderTypeId = Integer.parseInt(pServiceProviderData.getServiceProviderTypeId()[i]);
                saveBusEntAssociation(false, serviceProviderId, serviceProviderTypeId, RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_TO_TYPE_ASSOC, conn);
            }
            
            EmailData primaryEmail = pServiceProviderData.getPrimaryEmail();
	    if (primaryEmail.isDirty()) {
		if (primaryEmail.getEmailId() == 0) {
		    primaryEmail.setBusEntityId(serviceProviderId);
		    primaryEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
		    primaryEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
		    primaryEmail.setPrimaryInd(true);
		    EmailDataAccess.insert(conn, primaryEmail);
		} else {
		    EmailDataAccess.update(conn, primaryEmail);
		}
	    }

	    PhoneData primaryPhone = pServiceProviderData.getPrimaryPhone();
	    if (primaryPhone.isDirty()) {
		if (primaryPhone.getPhoneId() == 0) {
		    primaryPhone.setBusEntityId(serviceProviderId);
		    primaryPhone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
		    primaryPhone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
		    primaryPhone.setPrimaryInd(true);
		    PhoneDataAccess.insert(conn, primaryPhone);
		} else {
		    PhoneDataAccess.update(conn, primaryPhone);
		}
	    }

	    PhoneData primaryFax = pServiceProviderData.getPrimaryFax();
	    if (primaryFax.isDirty()) {
		if (primaryFax.getPhoneId() == 0) {
		    primaryFax.setBusEntityId(serviceProviderId);
		    primaryFax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
		    primaryFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
		    primaryFax.setPrimaryInd(true);
		    PhoneDataAccess.insert(conn, primaryFax);
		} else {
		    PhoneDataAccess.update(conn, primaryFax);
		}
	    }

	    AddressData primaryAddress = pServiceProviderData.getPrimaryAddress();
	    if (primaryAddress.isDirty()) {	    
		if (primaryAddress.getAddressId() == 0) {
		    primaryAddress.setBusEntityId(serviceProviderId);
		    primaryAddress.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
		    primaryAddress.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
		    primaryAddress.setPrimaryInd(true);
		    AddressDataAccess.insert(conn, primaryAddress);
		} else {
		    AddressDataAccess.update(conn, primaryAddress);
		}
	    }

//	    PropertyData prop = pServiceProviderData.getBusinessClass();
//	    if (prop.isDirty()) {
//		prop.setShortDesc("Business Class");
//		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.BUSINESS_CLASS);
//		prop.setBusEntityId(serviceProviderId);
//		if (prop.getPropertyId() == 0) {
//		    PropertyDataAccess.insert(conn, prop);
//		} else {
//		    PropertyDataAccess.update(conn, prop);
//		}
//	    }

//	    PropertyData prop = pServiceProviderData.getServiceProvider();
//	    if (prop.isDirty()) {
//		    prop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SERVICE_PROVIDER_CD);
//		    prop.setValue(pServiceProviderData.getServiceProvider().getValue());
//		    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		    prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SERVICE_PROVIDER_CD);
//		    prop.setBusEntityId(serviceProviderId);
//		    if (prop.getPropertyId() == 0) {
//		        PropertyDataAccess.insert(conn, prop);
//		    } else {
//		        PropertyDataAccess.update(conn, prop);
//		    }
//	    }	    
	    
//	    prop = pServiceProviderData.getWomanOwnedBusiness();
//	    if (prop.isDirty()) {
//		prop.setShortDesc("Woman Owned Business;");
//		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.WOMAN_OWNED_BUSINESS);
//		prop.setBusEntityId(serviceProviderId);
//		if (prop.getPropertyId() == 0) {
//		    PropertyDataAccess.insert(conn, prop);
//		} else {
//		    PropertyDataAccess.update(conn, prop);
//		}
//	    }
//
//	    prop = pServiceProviderData.getMinorityOwnedBusiness();
//	    if (prop.isDirty()) {
//		prop.setShortDesc("Minority Owned Business;");
//		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.MINORITY_OWNED_BUSINESS);
//		prop.setBusEntityId(serviceProviderId);
//		if (prop.getPropertyId() == 0) {
//		    PropertyDataAccess.insert(conn, prop);
//		} else {
//		    PropertyDataAccess.update(conn, prop);
//		}
//	    }
//
//	    prop = pServiceProviderData.getJWOD();
//	    if (prop.isDirty()) {
//		prop.setShortDesc("JWOD");
//		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.JWOD);
//		prop.setBusEntityId(serviceProviderId);
//		if (prop.getPropertyId() == 0) {
//		    PropertyDataAccess.insert(conn, prop);
//		} else {
//		    PropertyDataAccess.update(conn, prop);
//		}
//	    }
//
//	    prop = pServiceProviderData.getOtherBusiness();
//	    if (prop.isDirty()) {
//		prop.setShortDesc("Other Business");
//		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.OTHER_BUSINESS);
//		prop.setBusEntityId(serviceProviderId);
//		if (prop.getPropertyId() == 0) {
//		    PropertyDataAccess.insert(conn, prop);
//		} else {
//		    PropertyDataAccess.update(conn, prop);
//		}
//	    }
//
//	    PropertyDataVector specProperties = 
//		pServiceProviderData.getSpecializations();
//	    Iterator propIter = specProperties.iterator();
//	    while (propIter.hasNext()) {
//		prop = (PropertyData)propIter.next();
//		if (prop.isDirty()) {
//		    if (prop.getPropertyId() == 0) {
//			prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.MANUFACTURER_SPECIALIZATION);
//			if (prop.getPropertyStatusCd().compareTo("") == 0) {
//			    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//			}
//			prop.setBusEntityId(serviceProviderId);
//			PropertyDataAccess.insert(conn, prop);
//		    } else {
//			PropertyDataAccess.update(conn, prop);
//		    }
//		}
//	    }
//
//	    PropertyDataVector miscProperties = 
//		pServiceProviderData.getMiscProperties();
//	    propIter = miscProperties.iterator();
//	    while (propIter.hasNext()) {
//		prop = (PropertyData)propIter.next();
//		if (prop.isDirty()) {
//		    if (prop.getPropertyId() == 0) {
//			prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
//			if (prop.getPropertyStatusCd().compareTo("") == 0) {
//			    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//			}
//			prop.setBusEntityId(serviceProviderId);
//			PropertyDataAccess.insert(conn, prop);
//		    } else {
//			PropertyDataAccess.update(conn, prop);
//		    }
//		}
//	    }

	} catch (Exception e) {
	    throw new RemoteException("updateServiceProvider: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pServiceProviderData;
    }

    /**
     * <code>removeServiceProvider</code> may be used to remove an 'unused' service provider.
     * An unused service provider is a service provider with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a service provider that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pServiceProviderData a <code>ServiceProviderData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeServiceProvider(ServiceProviderData pServiceProviderData)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();
	    
	    int serviceProviderId = pServiceProviderData.getBusEntity().getBusEntityId();

	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, serviceProviderId);
            DBCriteria aCrit = new DBCriteria();
            aCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, serviceProviderId);
            
	    PropertyDataAccess.remove(conn, crit);
	    PhoneDataAccess.remove(conn, crit);
	    EmailDataAccess.remove(conn, crit);
	    AddressDataAccess.remove(conn, crit);
            BusEntityAssocDataAccess.remove(conn, aCrit);
	    BusEntityDataAccess.remove(conn, serviceProviderId);
	} catch (Exception e) {
	    throw new RemoteException("removeServiceProvider: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    }

    /**
     * Gets the ServiceProvider information values to be used by the request.
     * @param pServiceProviderId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return ServiceProviderData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if ServiceProvider with pServiceProviderId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public ServiceProviderData getServiceProviderForStore(int pServiceProviderId, IdVector pStoreIds, boolean pInactiveFl)
                                                                 throws RemoteException, DataNotFoundException {

        if (pStoreIds!=null&&pStoreIds.size()>0&&pServiceProviderId > 0) {
            BusEntitySearchCriteria pCrit = new BusEntitySearchCriteria();
            pCrit.setStoreBusEntityIds(pStoreIds);
            pCrit.setSearchId(pServiceProviderId);
            pCrit.setSearchForInactive(pInactiveFl);
            ServiceProviderDataVector sProvDV = getServiceProviderByCriteria(pCrit);
            if (sProvDV != null && sProvDV.size() > 0) {
                if (sProvDV.size() == 1) {
                    return (ServiceProviderData) sProvDV.get(0);
                } else {
                    throw new RemoteException("Multiple ServiceProviders for store id : " + pStoreIds);
                }
            }
        }
        throw new DataNotFoundException("ServiceProvider is not found");
    }

    /**
     *Associates specified service provider with all accounts of the store
     *@param pSvcProviderId         the service provider id
     *@param pStoreId   the store id
     *@param userName  the admininstrator name
     */
    public void configureAllAccounts(int pSvcProviderId, int pStoreId, String userName)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            String acctStoreReq =
               BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, acctStoreReq);
            dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            IdVector acctIds = BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID, dbc);
            ServiceDAO.addBusEntityAssociations(conn, pSvcProviderId, acctIds,
                    RefCodeNames.USER_ASSOC_CD.ACCOUNT);
        }catch (Exception e) {
            throw processException(e);
		}finally {
		    closeConnection(conn);
		}
    }

    public BusEntityAssocData addAssoc(int pSvcProvId, int pBusEntityId, String pType)
    throws RemoteException, DataNotFoundException {
        BusEntityAssocData busEntityAssocRD = null;
        Connection con = null;
        BusEntityData busEntityD = null;
        try {
            con = getConnection();
            busEntityAssocRD = addAssoc(con, pSvcProvId, pBusEntityId, pType);
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ServiceBean.addAssoc() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ServiceBean.addAssoc() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return busEntityAssocRD;
    }    

    /**
     *  Adds a feature to the UserAssoc attribute of the UserBean object
     *
     *@param  con                        The feature to be added to the
     *      UserAssoc attribute
     *@param  pSPId                    The feature to be added to the
     *      UserAssoc attribute
     *@param  pBusEntityId               The feature to be added to the
     *      UserAssoc attribute
     *@param  pType                      The feature to be added to the
     *      UserAssoc attribute
     *@return                            Description of the Returned Value
     *@exception  SQLException           Description of Exception
     *@exception  DataNotFoundException  Description of Exception
     *@exception  RemoteException        Description of Exception
     */
    public BusEntityAssocData addAssoc(Connection con, int pSPId,
                                      int pBusEntityId, String pType)
    throws SQLException, DataNotFoundException, RemoteException {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pSPId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, pType);
        logDebug("add service provider assoc: "
                 + " pSPId=" + pSPId
                 + " pBusEntityId=" + pBusEntityId
                 + " pType=" + pType
                 );
        BusEntityAssocDataVector serviceProvAssocV = BusEntityAssocDataAccess.select(con, dbc);
        if (serviceProvAssocV.size() > 0) {
        	BusEntityAssocData serviceProvAssocD = (BusEntityAssocData) serviceProvAssocV.get(0);
            logDebug("found service provider assoc: " + serviceProvAssocD);
            return serviceProvAssocD;
        }

        ///UserData userD = UserDataAccess.select(con, pSPId);
        //just to be sure that it exists
        BusEntityData busEntityD = BusEntityDataAccess.select(con, pBusEntityId);
//        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
//        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
//                RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
//        String acctStoreReq =
//           BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

//        dbc = new DBCriteria();
//        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, acctStoreReq);
//        dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
//                RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
//        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
//                RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
//        IdVector acctIds = BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID, dbc);

        IdVector acctIds = new IdVector();
        acctIds.add(new Integer(pBusEntityId));
        ServiceDAO.addBusEntityAssociations(con, pSPId, acctIds, pType);

//        String assocType = pType;
//        String busEntityType = busEntityD.getBusEntityTypeCd();
//
//        UserAssocData userAssocD = UserAssocData.createValue();
//        userAssocD.setUserId(pSPId);
//        userAssocD.setBusEntityId( pBusEntityId);
//        userAssocD.setUserAssocCd(assocType);

        return null;  //addAssoc(con, userAssocD);
    }    

    /**
     *  Adds a feature to the UserAssoc attribute of the UserBean object
     *
     *@param  con                  The feature to be added to the UserAssoc
     *      attribute
     *@param  pUserAssoc           The feature to be added to the UserAssoc
     *      attribute
     *@return                      Description of the Returned Value
     *@exception  RemoteException  Description of Exception
     *@exception  SQLException     Description of Exception
     */
    public UserAssocData addAssoc(Connection con, UserAssocData pUserAssoc)
    throws RemoteException, SQLException {
        Date date = new Date(System.currentTimeMillis());
        pUserAssoc.setAddBy("-1-");
        pUserAssoc.setAddDate(date);
        pUserAssoc.setModBy("-1-");
        pUserAssoc.setModDate(date);

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(UserAssocDataAccess.USER_ID,pUserAssoc.getUserId());
        crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,pUserAssoc.getUserAssocCd());
        crit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,pUserAssoc.getBusEntityId());

        UserAssocDataVector udv = UserAssocDataAccess.select(con,crit);
        if(udv.isEmpty()){
        	UserAssocData mUserAssoc = UserAssocDataAccess.insert(con, pUserAssoc);
        	return mUserAssoc;
        }else{
        	return (UserAssocData) udv.get(0);
        }
    }
    
    public void removeAssoc(int pServiceProviderId, int pBusEntityId)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();

            // The following condition takes care of removing
            // direct bus entity relations to this user
            // as well as any bus entities related to this
            // bus entity.
            String allbusentities = " select bus_entity1_id from "
            + " clw_bus_entity_assoc where"
            + " bus_entity2_id = " + pBusEntityId
            + " union select " + pBusEntityId + " from dual ";

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pServiceProviderId);
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, allbusentities);
            BusEntityAssocDataAccess.remove(con, dbc);
        }
        catch (Exception e) {
            throw new RemoteException("Error. ServiceBean.removeAssoc() Exception happened");
        } finally {
            closeConnection(con);
        }
    }
    
    /**
     * Gets the Service Providers by type
     * @param pStoreId a store identifier
     * @param pProviderCode provider type (returns all provider if null)
     * @param pInactiveFl returns only active proviedes if false 
     * @return set of ServiceProviderData objects
     * @exception RemoteException Required by EJB 1.0
     */
    public ServiceProviderDataVector getServiceProvidersForStore
            (int pStoreId, String pProviderCode, boolean pInactiveFl)
    throws RemoteException {
    Connection conn = null;    
    try {
        conn = getConnection();
    
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, 
                RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
        if(!pInactiveFl) {
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, 
                    RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        }
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pStoreId);
        dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, 
                RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE);
        String storeAssocReq = 
                BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc1);        
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeAssocReq);

        if(pProviderCode!=null) { 
            dbc1 = new DBCriteria();
                dbc1.addEqualTo(BusEntityDataAccess.SHORT_DESC, pProviderCode);
                String providerTypeReq = 
                        BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc1);
                
                dbc1 = new DBCriteria();
                dbc1.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, providerTypeReq);
                String serviceProviderReq = 
                        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc1);
                
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, serviceProviderReq);
        }
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
        BusEntityDataVector beDV  = BusEntityDataAccess.select(conn,dbc);
        ServiceProviderDataVector spDV = new ServiceProviderDataVector();
        for(Iterator iter=beDV.iterator(); iter.hasNext();) {
            spDV.add(getServiceProviderDetails((BusEntityData)iter.next()));
	}

        return spDV;
    } catch (Exception e) {
        e.printStackTrace();
        throw new RemoteException(e.getMessage());
    } finally {
        try {if (conn != null) conn.close();} catch (Exception ex) {}
    }
    }
    
    /**
     * Gets Service Providers by type
     * @param pStoreId a account identifier
     * @param pProviderCode provider type (returns all provider if null)
     * @param pInactiveFl returns only active provieers if false 
     * @return set of ServiceProviderData objects
     * @exception RemoteException Required by EJB 1.0
     */
    public ServiceProviderDataVector getServiceProvidersForAccount
            (int pAccountId, String pProviderCode, boolean pInactiveFl)
                                               throws RemoteException {
        Connection conn = null;    
        try {
            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, 
                    RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
            if(!pInactiveFl) {
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, 
                        RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            }
            DBCriteria dbc1 = new DBCriteria();
            dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pAccountId);
            dbc1.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, 
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_ACCOUNT);
            String storeAssocReq = 
                    BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc1);        
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeAssocReq);
            
            if(Utility.isSet(pProviderCode)) {
                dbc1 = new DBCriteria();
                dbc1.addEqualTo(BusEntityDataAccess.SHORT_DESC, pProviderCode);
                String providerTypeReq = 
                        BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc1);
                
                dbc1 = new DBCriteria();
                dbc1.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, providerTypeReq);
                String serviceProviderReq = 
                        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc1);
                
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, serviceProviderReq);
            }
            dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
            BusEntityDataVector beDV  = BusEntityDataAccess.select(conn,dbc);
            ServiceProviderDataVector spDV = new ServiceProviderDataVector();
            for(Iterator iter=beDV.iterator(); iter.hasNext();) {
                spDV.add(getServiceProviderDetails((BusEntityData)iter.next()));
            }

            return spDV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
    }
    
    /**
     * Returns Service Providers associated with given User
     * @param pUserId a user identifier
     * @return set of ServiceProviderData objects
     * @exception RemoteException Required by EJB 1.0
     */
    public ServiceProviderDataVector getServiceProvidersForUser(int pUserId)
            throws RemoteException {
        Connection conn = null;    
        try {
            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SERVICE_PROVIDER);
            
            String serviceProviderAssocReq = 
                    UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID, serviceProviderAssocReq);
            
            BusEntityDataVector beDV = BusEntityDataAccess.select(conn, dbc);             
            
            ServiceProviderDataVector spDV = new ServiceProviderDataVector();
            for(Iterator iter=beDV.iterator(); iter.hasNext();) {
                spDV.add(getServiceProviderDetails((BusEntityData)iter.next()));
            }
            
            return spDV;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
}
