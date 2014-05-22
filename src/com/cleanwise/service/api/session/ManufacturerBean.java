package com.cleanwise.service.api.session;

/**
 * Title:        ManufacturerBean
 * Description:  Bean implementation for Manufacturer Stateless Session Bean
 * Purpose:      Provides access to the services for managing the manufacturer information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Iterator;

/**
 * <code>Manufacturer</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public class ManufacturerBean extends BusEntityServicesAPI
{
    /**
     * Creates a new <code>ManufacturerBean</code> instance.
     *
     */
    public ManufacturerBean() {}

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Describe <code>getManufacturerDetails</code> method here.
     *
     * @param pBusEntity a <code>BusEntityData</code> value
     * @return a <code>ManufacturerData</code> value
     * @exception RemoteException if an error occurs
     */
    private ManufacturerData getManufacturerDetails(BusEntityData pBusEntity)
	throws RemoteException {

	AddressData primaryAddress = null;
	PhoneData primaryPhone = null;
	PhoneData primaryFax = null;
	EmailData primaryEmail = null;
	PropertyData businessClass = null;
	PropertyData womanOwnedBusiness = null;
	PropertyData minorityOwnedBusiness = null;
	PropertyData jWOD = null;
	PropertyData otherBusiness = null;
	PropertyDataVector specializations = null;
	PropertyDataVector miscProperties = null;
	PropertyData otherNames = null;
	
        int storeId = 0;
        
	Connection conn = null;

	try {
	    conn = getConnection();

	    // get manufacturer address
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

            IdVector storeIds = getBusEntityAssoc2Ids(conn, pBusEntity.getBusEntityId(), RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
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
	    specializations = new PropertyDataVector();
	    miscProperties = new PropertyDataVector();
	    while (propIter.hasNext()) {
		PropertyData prop = (PropertyData)propIter.next();
		String propTypeCd = prop.getPropertyTypeCd();
		if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.MANUFACTURER_SPECIALIZATION)) {
		    specializations.add(prop);
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.EXTRA)) {
		    miscProperties.add(prop);
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES)) {
			otherNames = prop;
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.BUSINESS_CLASS)) {
		    businessClass = prop;
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.JWOD)) {
		    womanOwnedBusiness = prop;
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.MINORITY_OWNED_BUSINESS)) {
		    minorityOwnedBusiness = prop;
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.OTHER_BUSINESS)) {
		    jWOD = prop;
		} else if (propTypeCd.equals(RefCodeNames.PROPERTY_TYPE_CD.WOMAN_OWNED_BUSINESS)) {
		    otherBusiness = prop;
		} else {
		    // unknown property type - ignore
		}
	    }

	} catch (Exception e) {
	    throw new RemoteException("getManufacturerDetails: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return new ManufacturerData(pBusEntity, 
                                    storeId,
				    primaryAddress, 
				    primaryPhone, 
				    primaryFax, 
				    primaryEmail,
				    businessClass,
				    womanOwnedBusiness,
				    minorityOwnedBusiness,
				    jWOD,
				    otherBusiness,
				    specializations,
				    miscProperties, otherNames);
    }

    /**
     * Describe <code>getManufacturer</code> method here.
     *
     * @param pManufacturerId an <code>int</code> value
     * @return a <code>ManufacturerData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public ManufacturerData getManufacturer(int pManufacturerId)
	throws RemoteException, DataNotFoundException {

	ManufacturerData manufacturer = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    BusEntityData busEntity = 
		BusEntityDataAccess.select(conn, pManufacturerId);
	    if (busEntity.getBusEntityTypeCd().compareTo(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER) != 0) {
		throw new DataNotFoundException("Bus Entity not manufacturer");
	    }
	    manufacturer = getManufacturerDetails(busEntity);
	} catch (DataNotFoundException e) {
	    throw e;
	} catch (Exception e) {
	    throw new RemoteException("getBusEntity: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return manufacturer;
    }

    /**
     * Get all manufacturers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the manufacturer
     * @return a <code>ManufacturerDataVector</code> of matching manufacturers
     * @exception RemoteException if an error occurs
     */
    public ManufacturerDataVector getManufacturerByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
	ManufacturerDataVector manufacturerVec = new ManufacturerDataVector();
	Connection conn = null;
	try {
	    conn = getConnection();

	    BusEntityDataVector busEntityVec =
                BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
	    Iterator iter = busEntityVec.iterator();
	    while (iter.hasNext()) {
		manufacturerVec.add(getManufacturerDetails((BusEntityData)iter.next()));
	    }
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
	return manufacturerVec;
    }
    
    /**
     * Get all manufacturers that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the manufacturer
     * @return a <code>BusEntityDataVector</code> of matching manufacturers
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getManufacturerBusEntitiesByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
	Connection conn = null;
	try {
	    conn = getConnection();
	    return BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);   
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
    }

    /**
     * Get all the manufacturers.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>ManufacturerDataVector</code> with all manufacturers.
     * @exception RemoteException if an error occurs
     */
    public ManufacturerDataVector getAllManufacturers(int pOrder)
	throws RemoteException {

	ManufacturerDataVector manufacturerVec = new ManufacturerDataVector ();

	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
			    RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
	    switch (pOrder) {
	    case Manufacturer.ORDER_BY_ID:
		crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
		break;
	    case Manufacturer.ORDER_BY_NAME:
		crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
		break;
	    default:
		throw new RemoteException("getManufacturerByName: Bad order specification");
	    }
	    BusEntityDataVector busEntityVec = 
		BusEntityDataAccess.select(conn, crit);

	    Iterator iter = busEntityVec.iterator();
	    while (iter.hasNext()) {
		manufacturerVec.add(getManufacturerDetails((BusEntityData)iter.next()));
	    }
	} catch (Exception e) {
	    throw new RemoteException("getAllManufacturers: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return manufacturerVec;
    }

    /**
     * Describe <code>addManufacturer</code> method here.
     *
     * @param pManufacturerData a <code>ManufacturerData</code> value
     * @return a <code>ManufacturerData</code> value
     * @exception RemoteException if an error occurs
     */
    public ManufacturerData addManufacturer(ManufacturerData pManufacturerData)
	throws RemoteException
    {
	return updateManufacturer(pManufacturerData);
    }

    /**
     * Updates the manufacturer information values to be used by the request.
     * @param pManufacturerData  the ManufacturerData manufacturer data.
     * @return a <code>ManufacturerData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public ManufacturerData updateManufacturer(ManufacturerData pManufacturerData)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    BusEntityData busEntity = pManufacturerData.getBusEntity();
	    if (busEntity.isDirty()) {
		if (busEntity.getBusEntityId() == 0) {
		    busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
		    BusEntityDataAccess.insert(conn, busEntity);
		} else {
		    BusEntityDataAccess.update(conn, busEntity);
		}
	    }
	    int manufacturerId = pManufacturerData.getBusEntity().getBusEntityId();
            
            saveBusEntAssociation(true,manufacturerId,pManufacturerData.getStoreId(),RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE,conn);

	    EmailData primaryEmail = pManufacturerData.getPrimaryEmail();
	    if (primaryEmail.isDirty()) {
		if (primaryEmail.getEmailId() == 0) {
		    primaryEmail.setBusEntityId(manufacturerId);
		    primaryEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
		    primaryEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
		    primaryEmail.setPrimaryInd(true);
		    EmailDataAccess.insert(conn, primaryEmail);
		} else {
		    EmailDataAccess.update(conn, primaryEmail);
		}
	    }

	    PhoneData primaryPhone = pManufacturerData.getPrimaryPhone();
	    if (primaryPhone.isDirty()) {
		if (primaryPhone.getPhoneId() == 0) {
		    primaryPhone.setBusEntityId(manufacturerId);
		    primaryPhone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
		    primaryPhone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
		    primaryPhone.setPrimaryInd(true);
		    PhoneDataAccess.insert(conn, primaryPhone);
		} else {
		    PhoneDataAccess.update(conn, primaryPhone);
		}
	    }

	    PhoneData primaryFax = pManufacturerData.getPrimaryFax();
	    if (primaryFax.isDirty()) {
		if (primaryFax.getPhoneId() == 0) {
		    primaryFax.setBusEntityId(manufacturerId);
		    primaryFax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
		    primaryFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
		    primaryFax.setPrimaryInd(true);
		    PhoneDataAccess.insert(conn, primaryFax);
		} else {
		    PhoneDataAccess.update(conn, primaryFax);
		}
	    }

	    AddressData primaryAddress = pManufacturerData.getPrimaryAddress();
	    if (primaryAddress.isDirty()) {	    
		if (primaryAddress.getAddressId() == 0) {
		    primaryAddress.setBusEntityId(manufacturerId);
		    primaryAddress.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
		    primaryAddress.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
		    primaryAddress.setPrimaryInd(true);
		    AddressDataAccess.insert(conn, primaryAddress);
		} else {
		    AddressDataAccess.update(conn, primaryAddress);
		}
	    }

	    PropertyData prop = pManufacturerData.getBusinessClass();
	    if (prop.isDirty()) {
		prop.setShortDesc("Business Class");
		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.BUSINESS_CLASS);
		prop.setBusEntityId(manufacturerId);
		if (prop.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, prop);
		} else {
		    PropertyDataAccess.update(conn, prop);
		}
	    }

	    prop = pManufacturerData.getWomanOwnedBusiness();
	    if (prop.isDirty()) {
		prop.setShortDesc("Woman Owned Business;");
		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.WOMAN_OWNED_BUSINESS);
		prop.setBusEntityId(manufacturerId);
		if (prop.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, prop);
		} else {
		    PropertyDataAccess.update(conn, prop);
		}
	    }

	    prop = pManufacturerData.getMinorityOwnedBusiness();
	    if (prop.isDirty()) {
		prop.setShortDesc("Minority Owned Business;");
		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.MINORITY_OWNED_BUSINESS);
		prop.setBusEntityId(manufacturerId);
		if (prop.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, prop);
		} else {
		    PropertyDataAccess.update(conn, prop);
		}
	    }

	    prop = pManufacturerData.getJWOD();
	    if (prop.isDirty()) {
		prop.setShortDesc("JWOD");
		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.JWOD);
		prop.setBusEntityId(manufacturerId);
		if (prop.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, prop);
		} else {
		    PropertyDataAccess.update(conn, prop);
		}
	    }

	    prop = pManufacturerData.getOtherBusiness();
	    if (prop.isDirty()) {
		prop.setShortDesc("Other Business");
		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.OTHER_BUSINESS);
		prop.setBusEntityId(manufacturerId);
		if (prop.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, prop);
		} else {
		    PropertyDataAccess.update(conn, prop);
		}
	    }

	    PropertyDataVector specProperties = 
		pManufacturerData.getSpecializations();
	    Iterator propIter = specProperties.iterator();
	    while (propIter.hasNext()) {
		prop = (PropertyData)propIter.next();
		if (prop.isDirty()) {
		    if (prop.getPropertyId() == 0) {
			prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.MANUFACTURER_SPECIALIZATION);
			if (prop.getPropertyStatusCd().compareTo("") == 0) {
			    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			}
			prop.setBusEntityId(manufacturerId);
			PropertyDataAccess.insert(conn, prop);
		    } else {
			PropertyDataAccess.update(conn, prop);
		    }
		}
	    }

	    PropertyDataVector miscProperties = 
		pManufacturerData.getMiscProperties();
	    propIter = miscProperties.iterator();
	    while (propIter.hasNext()) {
		prop = (PropertyData)propIter.next();
		if (prop.isDirty()) {
		    if (prop.getPropertyId() == 0) {
			prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
			if (prop.getPropertyStatusCd().compareTo("") == 0) {
			    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			}
			prop.setBusEntityId(manufacturerId);
			PropertyDataAccess.insert(conn, prop);
		    } else {
			PropertyDataAccess.update(conn, prop);
		    }
		}
	    }
	    
	    prop = pManufacturerData.getOtherNames();
	    if (prop.isDirty()) {
		prop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
		prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.OTHER_NAMES);
		prop.setBusEntityId(manufacturerId);
		if (prop.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, prop);
		} else {
		    PropertyDataAccess.update(conn, prop);
		}
	    }

	} catch (Exception e) {
	    throw new RemoteException("updateManufacturer: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pManufacturerData;
    }

    /**
     * <code>removeManufacturer</code> may be used to remove an 'unused' manufacturer.
     * An unused manufacturer is a manufacturer with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a manufacturer that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pManufacturerData a <code>ManufacturerData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeManufacturer(ManufacturerData pManufacturerData)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();
	    
	    int manufacturerId = pManufacturerData.getBusEntity().getBusEntityId();

	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, manufacturerId);
            DBCriteria aCrit = new DBCriteria();
            aCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, manufacturerId);
            
	    PropertyDataAccess.remove(conn, crit);
	    PhoneDataAccess.remove(conn, crit);
	    EmailDataAccess.remove(conn, crit);
	    AddressDataAccess.remove(conn, crit);
            BusEntityAssocDataAccess.remove(conn, aCrit);
	    BusEntityDataAccess.remove(conn, manufacturerId);
	} catch (Exception e) {
	    throw new RemoteException("removeManufacturer: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    }

    /**
     * Gets the Manufacturer information values to be used by the request.
     * @param pManufacturerId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return ManufacturerData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if Manufacturer with pManufacturerId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public ManufacturerData getManufacturerForStore(int pManufacturerId, IdVector pStoreIds, boolean pInactiveFl)
                                                                 throws RemoteException, DataNotFoundException {

        if (pStoreIds!=null&&pStoreIds.size()>0&&pManufacturerId > 0) {
            BusEntitySearchCriteria pCrit = new BusEntitySearchCriteria();
            pCrit.setStoreBusEntityIds(pStoreIds);
            pCrit.setSearchId(pManufacturerId);
            pCrit.setSearchForInactive(pInactiveFl);
            ManufacturerDataVector manufacturerDV = getManufacturerByCriteria(pCrit);
            if (manufacturerDV != null && manufacturerDV.size() > 0) {
                if (manufacturerDV.size() == 1) {
                    return (ManufacturerData) manufacturerDV.get(0);
                } else {
                    throw new RemoteException("Multiple Manufacturers for store id : " + pStoreIds);
                }
            }
        }
        throw new DataNotFoundException("Manufacturer is not found");
    }

    public BusEntityDataVector getManufacturerByUserId(String userId) throws RemoteException {

        BusEntityDataVector bedv = null;
        Connection conn = null;

        try {
            conn = getConnection();
/*
            String q =
                    "SELECT manuf.bus_entity_id\n" +
                            "  FROM (SELECT DISTINCT manuf.bus_entity_id, manuf.short_desc\n" +
                            "                   FROM clw_catalog_assoc ca,\n" +
                            "                        clw_catalog cat,\n" +
                            "                        clw_catalog_structure cs,\n" +
                            "                        clw_item_mapping im,\n" +
                            "                        clw_bus_entity manuf,\n" +
                            "                        clw_bus_entity site,\n" +
                            "                        clw_user_assoc ua\n" +
                            "                  WHERE ua.user_assoc_cd = 'SITE'\n" +
                            "                    AND ua.user_id =" + userId + "\n" +
                            "                    AND site.bus_entity_status_cd <> 'INACTIVE'\n" +
                            "                    AND site.bus_entity_id = ua.bus_entity_id\n" +
                            "                    AND site.bus_entity_id = ca.bus_entity_id\n" +
                            "                    AND ca.catalog_assoc_cd = 'CATALOG_SITE'\n" +
                            "                    AND cat.catalog_id = ca.catalog_id\n" +
                            "                    AND cat.catalog_type_cd = 'SHOPPING'\n" +
                            "                    AND cat.catalog_status_cd <> 'INACTIVE'\n" +
                            "                    AND cat.catalog_id = cs.catalog_id\n" +
                            "                    AND cs.catalog_structure_cd = 'CATALOG_PRODUCT'\n" +
                            "                    AND cs.item_id = im.item_id\n" +
                            "                    AND im.item_mapping_cd = 'ITEM_MANUFACTURER'\n" +
                            "                    AND im.bus_entity_id = manuf.bus_entity_id\n" +
                            "               ORDER BY manuf.short_desc) manuf";
*/
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, userId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, 
                    RefCodeNames.USER_ASSOC_CD.SITE);
            String userSiteReq = 
                    UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, userSiteReq);
            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, 
                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
            String siteCatalogReq =
                    CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogDataAccess.CATALOG_ID, siteCatalogReq);
            dbc.addNotEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.INACTIVE);

            IdVector catalogIdV = 
                    CatalogDataAccess.selectIdOnly(conn, CatalogDataAccess.CATALOG_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,catalogIdV);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            IdVector itemIdV = 
                    CatalogStructureDataAccess.selectIdOnly(conn, CatalogStructureDataAccess.ITEM_ID,dbc);
                    
            dbc = new DBCriteria();
            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, 
                    RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            
            String itemManufReq = 
                    ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.BUS_ENTITY_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemManufReq);
//            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, q);
            dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
            bedv = BusEntityDataAccess.select( conn, dbc );
            
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return bedv;
    }

    public PairViewVector getEnterpriseAssoc(int pEnterpriseStoreId, int pManagedStoreId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getEnterpriseAssoc(conn, pEnterpriseStoreId, pManagedStoreId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private PairViewVector getEnterpriseAssoc(Connection conn, int pEnterpriseStoreId, int pManagedStoreId) throws Exception {

        String sql = " SELECT  BUS_ENTITY1_ID,BUS_ENTITY2_ID,BUS_ENTITY_ASSOC_ID,ADD_BY,ADD_DATE,BUS_ENTITY_ASSOC_CD,MOD_BY,MOD_DATE FROM CLW_BUS_ENTITY_ASSOC, \n" +
                " (SELECT CLW_BUS_ENTITY.BUS_ENTITY_ID FROM CLW_BUS_ENTITY_ASSOC,CLW_BUS_ENTITY \n" +
                " WHERE CLW_BUS_ENTITY.BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "'  \n" +
                " AND CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER + "'  \n" +
                " AND CLW_BUS_ENTITY.BUS_ENTITY_ID = CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID \n" +
                " AND CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE + "'\n" +
                " AND CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID=" + pEnterpriseStoreId + ") EM, \n" +
                " (SELECT CLW_BUS_ENTITY.BUS_ENTITY_ID FROM CLW_BUS_ENTITY_ASSOC,CLW_BUS_ENTITY \n" +
                " WHERE CLW_BUS_ENTITY.BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "'  \n" +
                " AND CLW_BUS_ENTITY.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER + "'  \n" +
                " AND CLW_BUS_ENTITY.BUS_ENTITY_ID = CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID \n" +
                " AND CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE + "'\n" +
                " AND CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID=" + pManagedStoreId + ") MM \n" +
                "   WHERE CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ENTERPRISE_MANUF_ASSOC + "'\n" +
                "    AND CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID = MM.BUS_ENTITY_ID\n" +
                "     AND CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID = EM.BUS_ENTITY_ID";

        PairViewVector result = new PairViewVector();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            result.add(new PairView(new Integer(rs.getInt(1)), new Integer(rs.getInt(2))));
        }

        rs.close();
        stmt.close();


        return result;
    }

    public void addEnterpriseManufAssoc(PairViewVector pMfgIdPairs, String pUser) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            addEnterpriseManufAssoc(conn, pMfgIdPairs, pUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private void addEnterpriseManufAssoc(Connection conn, PairViewVector pMfgIdPairs, String pUser) throws SQLException {
        if (pMfgIdPairs != null && !pMfgIdPairs.isEmpty()) {
            Iterator it = pMfgIdPairs.iterator();
            while (it.hasNext()) {

                PairView pair = (PairView) it.next();

                BusEntityAssocData bea = BusEntityAssocData.createValue();

                bea.setBusEntity1Id(((Integer) pair.getObject1()).intValue());
                bea.setBusEntity2Id(((Integer) pair.getObject2()).intValue());
                bea.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.ENTERPRISE_MANUF_ASSOC);
                bea.setModBy(pUser);
                bea.setAddBy(pUser);

                BusEntityAssocDataAccess.insert(conn, bea);
            }
        }
    }

    public void removeEnterpriseManufAssoc(PairViewVector pMfgIdPairs) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            removeEnterpriseManufAssoc(conn, pMfgIdPairs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private void removeEnterpriseManufAssoc(Connection conn, PairViewVector pMfgIdPairs) throws SQLException {
        if (pMfgIdPairs != null && !pMfgIdPairs.isEmpty()) {
            Iterator it = pMfgIdPairs.iterator();
            while (it.hasNext()) {
                PairView pair = (PairView) it.next();
                DBCriteria dbCrit = new DBCriteria();
                dbCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, ((Integer) pair.getObject1()).intValue());
                dbCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, ((Integer) pair.getObject2()).intValue());

                BusEntityAssocDataAccess.remove(conn, dbCrit);
            }
        }
    }
    
    /**
     * Get all manufacturer busentitys that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the manufacturer
     * @return a <code>BusEntityDataVector</code> of matching manufacturer bus entity
     * @exception RemoteException if an error occurs
     */
    public BusEntityDataVector getManufBusEntityByCriteria(BusEntitySearchCriteria pCrit)
	throws RemoteException {
    	BusEntityDataVector busEntityVec = new BusEntityDataVector();
		Connection conn = null;
		try {
		    conn = getConnection();
	
		    busEntityVec =
	                BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
		    
		} catch (Exception e) {
		    throw processException(e);
		} finally {
		    closeConnection(conn);
		}
		return busEntityVec;
    }
    
    public BusEntityDataVector getManufacturerByName(String manufName, int storeId) throws RemoteException {
        BusEntityDataVector manufs = new BusEntityDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            
            crit.addEqualTo(BusEntityDataAccess.SHORT_DESC, manufName);
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            
            if (storeId > 0) {
                crit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " BEA");
                DBCriteria isolCrit = new DBCriteria();
                
                isolCrit.addCondition("BEA." + BusEntityAssocDataAccess.BUS_ENTITY1_ID + " = " + BusEntityDataAccess.BUS_ENTITY_ID);
                isolCrit.addEqualTo("BEA." + BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeId);
                isolCrit.addEqualTo("BEA." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
                
                crit.addIsolatedCriterita(isolCrit);
            }
            manufs = BusEntityDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return manufs;
    }
    
    public PropertyDataVector getManufacturerProps(int manufacturerId) throws RemoteException {
        PropertyDataVector props = new PropertyDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            
            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, manufacturerId);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            
            props = PropertyDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return props;
    }
    
    public BusEntityDataVector getManufacturerByUserInAccountCatalogs(int pUserId)throws RemoteException {
    	BusEntityDataVector manufacturerVec = new BusEntityDataVector();
	Connection conn = null;
	try {
	    conn = getConnection();
	    DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
        dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
        String userAcctReq = 
                UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);
        
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, userAcctReq);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        String acctCatalogReq =
                CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);
        
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, acctCatalogReq);
        dbc.addNotEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.INACTIVE);
               
        IdVector catalogIdV = 
                CatalogDataAccess.selectIdOnly(conn, CatalogDataAccess.CATALOG_ID, dbc);
        
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,catalogIdV);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        IdVector itemIdV = 
                CatalogStructureDataAccess.selectIdOnly(conn, CatalogStructureDataAccess.ITEM_ID,dbc);
                
        dbc = new DBCriteria();
        dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, 
                RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
        
        String itemManufReq = 
                ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.BUS_ENTITY_ID, dbc);
        
        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemManufReq);
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
        manufacturerVec = BusEntityDataAccess.select( conn, dbc );
        
	    
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
	return manufacturerVec;
    }
    
}



