package com.cleanwise.service.api.session;

/**
 * Title:        StoreBean
 * Description:  Bean implementation for Store Stateless Session Bean
 * Purpose:      Provides access to the services for managing the store information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.AssetDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.SequenceUtilDAO;
import com.cleanwise.service.api.dao.StagedAssetAssocDataAccess;
import com.cleanwise.service.api.dao.StagedAssetDataAccess;
import com.cleanwise.service.api.dao.StagedItemAssocDataAccess;
import com.cleanwise.service.api.dao.StagedItemDataAccess;
import com.cleanwise.service.api.dao.StoreProfileDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dto.StoreProfileDto;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.synchronizer.CategorySynchronizer;
import com.cleanwise.service.api.util.synchronizer.MasterAssetLoader;
import com.cleanwise.service.api.util.synchronizer.MasterItemLoader;
import com.cleanwise.service.api.util.synchronizer.MasterLoader;
import com.cleanwise.service.api.util.synchronizer.ParentChildStoresSynchronizer;
import com.cleanwise.service.api.util.synchronizer.PhysicalAssetLoader;
import com.cleanwise.service.api.util.synchronizer.StoreItemSynchronizer;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AssetData;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityAssocDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.DomainData;
import com.cleanwise.service.api.value.DomainDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.StoreDataVector;
import com.cleanwise.service.api.value.StoreProfileData;
import com.cleanwise.service.api.value.StoreProfileDataVector;


/**
 * <code>Store</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public class StoreBean extends BusEntityServicesAPI
{
    private static final Logger log = Logger.getLogger(StoreBean.class);
    
    private static final String STORE_PREFIX_PROP = "STORE_PREFIX";

    /**
     * Creates a new <code>StoreBean</code> instance.
     *
     */
    public StoreBean() {}

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /**
     * Describe <code>getStoreDetails</code> method here.
     *
     * @param pBusEntity a <code>BusEntityData</code> value
     * @return a <code>StoreData</code> value
     * @exception RemoteException if an error occurs
     */
    private StoreData getStoreDetails(BusEntityData pBusEntity)
	throws RemoteException {
		Connection conn = null;
		try {
		    conn = getConnection();
		    
		    //log.info("**********SVC: pBusEntity = " + pBusEntity);
		    
		    return BusEntityDAO.getStoreData(conn,pBusEntity);
		} catch (Exception e) {
		    throw new RemoteException("getStoreDetails: " + e.getMessage());
		} finally {
		    try {if (conn != null) conn.close();} catch (Exception ex) {}
		}
    }

    /**
     * Describe <code>getStore</code> method here.
     *
     * @param pStoreId an <code>int</code> value
     * @return a <code>StoreData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public StoreData getStore(int pStoreId)
	throws RemoteException, DataNotFoundException {

	StoreData store = null;

	Connection conn = null;
	try {
	    conn = getConnection();
	    BusEntityData busEntity = 
		BusEntityDataAccess.select(conn, pStoreId);
	    
	    if (busEntity.getBusEntityTypeCd().compareTo(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE) != 0) {
		throw new DataNotFoundException("Bus Entity not store");
	    }
	    store = getStoreDetails(busEntity);
	} catch (DataNotFoundException e) {
	    throw e;            
	} catch (Exception e) {
		e.printStackTrace();
	    throw new RemoteException("getBusEntity: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return store;
    }

    

    /**
     *  Get all the stores.
     *
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>StoreDataVector</code> with all
     *      stores.
     *@exception  RemoteException  if an error occurs
     */
    public StoreDataVector getAllStores(int pOrder) throws RemoteException {
        StoreDataVector storeVec = new StoreDataVector ();
	Connection conn = null;
	try {
	    conn = getConnection();
            BusEntityDataVector busEntityVec = getAllStoresBusEntityData(pOrder, conn);
            Iterator iter = busEntityVec.iterator();
	    while (iter.hasNext()) {
		storeVec.add(getStoreDetails((BusEntityData)iter.next()));
	    }
	}  catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}

	return storeVec;
    }
   
    public DomainDataVector getAllDomains(int pOrder)
    throws RemoteException {
    	DomainDataVector domainVec = new DomainDataVector();
    	DomainData domData;
        Connection conn = null;
        
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();

            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME);
            
            switch (pOrder) {
                
                case Account.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
                    
                    break;
                    
                case Account.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
                    
                    break;
                    
                default:
                    throw new RemoteException("getAllDomains: Bad order specification");
            }
            
            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,
                    crit);
            Iterator iter = busEntityVec.iterator();
            
            while (iter.hasNext()) {
            	domData = getDomainDetails((BusEntityData)iter.next());
            	
            	domainVec.add(domData);
            }
        } catch (Exception e) {
            throw new RemoteException("getAllAccounts: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        
        return domainVec;
    }

    public DomainData getDomain(int busEntId) throws RemoteException {
    	DomainData domData = DomainData.createValue();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, busEntId);
            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn, crit);
            Iterator iter = busEntityVec.iterator();
            while (iter.hasNext()) {
            	BusEntityData bED = (BusEntityData)iter.next();
            	domData.setBusEntity(bED);
            	domData.getBusEntity().setAddBy(bED.getAddBy());
            	domData.getBusEntity().setAddDate(bED.getAddDate());            	
            }
        } catch (Exception e) {
            throw new RemoteException("getDomain: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return domData;
    }    

    public PropertyData getSSLName(int busEntId) throws RemoteException {
    	PropertyData SSLData = PropertyData.createValue();
    	PropertyData pD = null;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME);            
            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, busEntId);
            PropertyDataVector propertyVec = PropertyDataAccess.select(conn, crit);
            Iterator iter = propertyVec.iterator();
            while (iter.hasNext()) {
            	pD = (PropertyData)iter.next();
//            	SSLData.setBusEntity(pD);
//            	SSLData.getBusEntity().setAddBy(pD.getAddBy());
//            	SSLData.getBusEntity().setAddDate(pD.getAddDate());            	
            }
        } catch (Exception e) {
            throw new RemoteException("getSSLName: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return pD;
    }
    
    DomainData getDomainDetails(BusEntityData pBusEntity, Connection conn) 
	throws Exception{

        PropertyData SSLName = null;
        
        PropertyDataVector miscProperties = null;
        List runtimeDisplayOrderItemActionTypes = new ArrayList();
        List fieldProperties = null;
        PropertyDataVector fieldPropertiesRuntime = null;
        PropertyData accountType = null;
        PropertyData orderMinimum = null;
        PropertyData creditLimit = null;
        PropertyData creditRating = null;
        PropertyData crcShop = null;
        PropertyData comments = null, ognote = null, skutag = null;
        PropertyData authForResale = null;
        PropertyData resaleErpAccount = null;

        BusEntityAssocData storeAssoc = null;
        BusEntityAssocData defaultStoreAssoc = null;
        
        ArrayList orderManagerEmail = new ArrayList();
        String ediShipToPrefix = null;
        BigDecimal targetMargin = null;
        String customerSystemApprovalCd = null;
        boolean makeShipToBillTo = false;
        boolean customerRequestPoAllowed = true; //default to true
        boolean taxableIndicator = false;
        String freightChargeType = null;
        String purchaseOrderAccountName = null;
        String budgetTypeCd = null;
        String distrPOType = null;
        
        String defaultStoreName = "";
        
        
        // get store association
        DBCriteria assocCrit = new DBCriteria();
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                pBusEntity.getBusEntityId());
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                RefCodeNames.BUS_ENTITY_ASSOC_CD.DEFAULT_STORE_OF_DOMAIN);
        
        BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(
                conn, assocCrit);
        
        // there ought to be exactly one - if not, that's a problem
        int defaultStoreId = 0;
        if (assocVec.size() > 0) {
            defaultStoreAssoc = (BusEntityAssocData)assocVec.get(0);
            defaultStoreId = defaultStoreAssoc.getBusEntity1Id();
        }
        
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, defaultStoreId);
        BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn, crit);
        Iterator iter = busEntityVec.iterator();

        while (iter.hasNext()) {
        	BusEntityData storeData = (BusEntityData)iter.next();
        	defaultStoreName = storeData.getShortDesc();
        	
        }
        
        
        // get properties
        DBCriteria propCrit = new DBCriteria();
        propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        
        PropertyDataVector props = PropertyDataAccess.select(conn,
                propCrit);
//        miscProperties = new PropertyDataVector();
//        fieldProperties = new PropertyDataVector();
//        fieldPropertiesRuntime = new PropertyDataVector();
        
        
//        DBCriteria propCritAcc = new DBCriteria();
//        propCritAcc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
//        PropertyDataVector propsStore = PropertyDataAccess.select(conn,propCritAcc);
//        Iterator storePropIt = propsStore.iterator();
//        PropertyDataVector fieldPropertiesUnordered = new PropertyDataVector();
        Iterator propIter = props.iterator();
//        int howToCleanTopicId = 0;
//	Note noteEjb = getAPIAccess().getNoteAPI();

//	PropertyDataVector topicDV = 
//	    noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);
//	for(Iterator iter=topicDV.iterator(); iter.hasNext();) {
//	    PropertyData pD = (PropertyData) iter.next();
//	    if("How to Clean".equalsIgnoreCase(pD.getValue())) {
//		howToCleanTopicId = pD.getPropertyId();
//		break;
//	    }
//	}

        while (propIter.hasNext()) {
            PropertyData prop = (PropertyData)propIter.next();
            String propType = prop.getPropertyTypeCd();
            if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME) == 0) {
            	SSLName = prop;
            } 
        }

        DomainData dD = new DomainData
                (pBusEntity, SSLName, storeAssoc, defaultStoreAssoc, defaultStoreName);
        
        return dD;
    }
    
    
    public DomainData getDomainDetails(BusEntityData pBusEntity) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getDomainDetails(pBusEntity, conn);
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public BusEntityDataVector getStoresCollectionByBusEntity(int pBusEntityId) throws RemoteException {
        Connection conn = null;
        // will not return null vector unless exception thrown
        BusEntityDataVector sDV = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_OF_DOMAIN);
            IdVector storeIds = BusEntityAssocDataAccess.selectIdOnly(conn, BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeIds);
            sDV = BusEntityDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getStoresCollectionByBusEntity error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        
        return sDV;    	
    }

    public BusEntityDataVector getDefaultStoreByBusEntity(int pBusEntityId) throws RemoteException {
        Connection conn = null;
        // will not return null vector unless exception thrown
        BusEntityDataVector dSDV = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.DEFAULT_STORE_OF_DOMAIN);
            IdVector storeIds = BusEntityAssocDataAccess.selectIdOnly(conn, BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeIds);
            dSDV = BusEntityDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getDefaultStoreByBusEntity error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        
        return dSDV;    	
    }    
        
    
    public BusEntityDataVector getStoresCollectionByBusEntity(
    String pName,
    int pMatch,
    int pOrder)
    throws RemoteException {
        IdVector busEntityIds = new IdVector();
//        busEntityIds.add(new Integer(pBusEntityId));
        return getStoresCollectionByBusEntityCollection(pName, pMatch, pOrder);
    }    
    

    public BusEntityDataVector getStoresCollectionByBusEntityCollection(
    String pName,
    int pMatch,
    int pOrder)
    throws RemoteException {
        Connection conn = null;
        // will not return null vector unless exception thrown
        BusEntityDataVector sDV = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

            switch (pMatch) {
                case User.NAME_BEGINS_WITH:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC, pName + "%");
                    break;
                case User.NAME_BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                    pName + "%");
                    break;
                case User.NAME_CONTAINS:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC,
                    "%" + pName + "%");
                    break;
                case User.NAME_CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                    "%" + pName + "%");
                    break;
                case User.NAME_EXACT:
                    crit.addEqualTo(BusEntityDataAccess.SHORT_DESC,
                    pName);
                    break;
                case User.NAME_EXACT_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                    pName);
                    break;
                default:
                    throw new RemoteException("bad match");
            }
            
            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }
            sDV = BusEntityDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getStoresCollectionByBusEntityCollection error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        return sDV;
    }
    
    public BusEntityDataVector getStoresCollectionByBusEntity(int pBusEntityId,
    	    String pName,
    	    int pMatch,
    	    int pOrder		
    ) throws RemoteException {
        Connection conn = null;
        // will not return null vector unless exception thrown
        BusEntityDataVector sDV = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.STORE_OF_DOMAIN);
            IdVector storeIds = BusEntityAssocDataAccess.selectIdOnly(conn, BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeIds);
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            switch (pMatch) {
                case User.NAME_BEGINS_WITH:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC, pName + "%");
                    break;
                case User.NAME_BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                    pName + "%");
                    break;
                case User.NAME_CONTAINS:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC,
                    "%" + pName + "%");
                    break;
                case User.NAME_CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                    "%" + pName + "%");
                    break;
                case User.NAME_EXACT:
                    crit.addEqualTo(BusEntityDataAccess.SHORT_DESC,
                    pName);
                    break;
                case User.NAME_EXACT_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                    pName);
                    break;
                default:
                    throw new RemoteException("bad match");
            }
            switch (pOrder) {
                case User.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
                    break;
                case User.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
                    break;
                default:
                    throw new RemoteException("Bad order specification");
            }
            sDV = BusEntityDataAccess.select(conn, crit);
        } catch (Exception e) {
            throw new RemoteException("getStoresCollectionByBusEntity error: "
            + e.getMessage());
        } finally {
            try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        
        return sDV;    	
    } 
    
    /**
     *  Get all the stores.
     *
     *@param  pOrder       one of ORDER_BY_ID, ORDER_BY_NAME
     *@param conn          an open @see Connection object
     *@return              a <code>BusEntityDataVector</code> with all stores.
     *@exception  RemoteException  if an error occurs
     */
    private BusEntityDataVector getAllStoresBusEntityData(int pOrder, Connection conn) throws Exception{
        DBCriteria crit = new DBCriteria();
        switch (pOrder) {
        case Store.ORDER_BY_ID:
            crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
            break;
        case Store.ORDER_BY_NAME:
            crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
            break;
        default:
            throw new RemoteException("getAllStores: Bad order specification");
        }
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

        return BusEntityDataAccess.select(conn, crit);
    }


    /**
     *  Get all domains.
     *
     *@param  pOrder       one of ORDER_BY_ID, ORDER_BY_NAME
     *@param conn          an open @see Connection object
     *@return              a <code>BusEntityDataVector</code> with all stores.
     *@exception  RemoteException  if an error occurs
     */
    private BusEntityDataVector getAllDomainsBusEntityData(int pOrder, Connection conn) throws Exception{
        DBCriteria crit = new DBCriteria();
        switch (pOrder) {
        case Store.ORDER_BY_ID:
            crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
            break;
        case Store.ORDER_BY_NAME:
            crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
            break;
        default:
            throw new RemoteException("getAllDomains: Bad order specification");
        }
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME);

        return BusEntityDataAccess.select(conn, crit);
    }    
    
    /**
     *  Get all the stores.
     *
     *@param  pOrder       one of ORDER_BY_ID, ORDER_BY_NAME
     *@return              a <code>BusEntityDataVector</code> with all stores.
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getAllStoresBusEntityData(int pOrder) throws RemoteException{
        Connection conn = null;
	try {
	    conn = getConnection();
            return getAllStoresBusEntityData(pOrder, conn);
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
    }

    /**
     *  Get all domains.
     *
     *@param  pOrder       one of ORDER_BY_ID, ORDER_BY_NAME
     *@return              a <code>BusEntityDataVector</code> with all stores.
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getAllDomainsBusEntityData(int pOrder) throws RemoteException{
        Connection conn = null;
	try {
	    conn = getConnection();
            return getAllDomainsBusEntityData(pOrder, conn);
	} catch (Exception e) {
	    throw processException(e);
	} finally {
	    closeConnection(conn);
	}
    }    
    
    /**
     * Describe <code>addStore</code> method here.
     *
     * @param pStoreData a <code>StoreData</code> value
     * @return a <code>StoreData</code> value
     * @exception RemoteException if an error occurs
     */
    public StoreData addStore(StoreData pStoreData)
	throws RemoteException
    {
	return updateStore(pStoreData);
    }

    /**
     * Updates the store information values to be used by the request.
     * @param pStoreData  the StoreData store data.
     * @return a <code>StoreData</code> value
     * @throws            RemoteException Required by EJB 1.0
     */
    public StoreData updateStore(StoreData pStoreData)
	throws RemoteException
    {
	Connection conn = null;

	try {
	    conn = getConnection();

	    BusEntityData busEntity = pStoreData.getBusEntity();
	    if (busEntity.isDirty()) {
		if (busEntity.getBusEntityId() == 0) {
		    busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
		    BusEntityDataAccess.insert(conn, busEntity);
		} else {
		    BusEntityDataAccess.update(conn, busEntity);
		}
	    }
	    int storeId = pStoreData.getBusEntity().getBusEntityId();

	    PropertyData prefix = pStoreData.getPrefix();
	    if (prefix.isDirty()) {
		if (prefix.getPropertyId() == 0) {
		    prefix.setShortDesc(STORE_PREFIX_PROP);
		    prefix.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		    prefix.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_CODE);
		    prefix.setBusEntityId(storeId);
		    PropertyDataAccess.insert(conn, prefix);
		} else { 
		    PropertyDataAccess.update(conn, prefix);
		}
	    }

//	    PropertyData prefixNew = pStoreData.getPrefixNew();
//	    if (prefixNew.isDirty()) {
//		if (prefixNew.getPropertyId() == 0) {
//		    prefixNew.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_NEW);
//		    prefixNew.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		    prefixNew.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_NEW);
//		    prefixNew.setBusEntityId(storeId);
//		    PropertyDataAccess.insert(conn, prefixNew);
//		} else { 
//		    PropertyDataAccess.update(conn, prefixNew);
//		}
//	    }
            
            PropertyData storeBusName = pStoreData.getStoreBusinessName();
	    if (storeBusName.isDirty()) {
		if (storeBusName.getPropertyId() == 0) {
		    storeBusName.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.STORE_BUSINESS_NAME);
		    storeBusName.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		    storeBusName.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_BUSINESS_NAME);
		    storeBusName.setBusEntityId(storeId);
		    PropertyDataAccess.insert(conn, storeBusName);
		} else { 
		    PropertyDataAccess.update(conn, storeBusName);
		}
	    }
            
            PropertyData storePriWebAddr = pStoreData.getStorePrimaryWebAddress();
	    if (storePriWebAddr.isDirty()) {
		if (storePriWebAddr.getPropertyId() == 0) {
		    storePriWebAddr.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.STORE_PRIMARY_WEB_ADDRESS);
		    storePriWebAddr.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		    storePriWebAddr.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_PRIMARY_WEB_ADDRESS);
		    storePriWebAddr.setBusEntityId(storeId);
		    PropertyDataAccess.insert(conn, storePriWebAddr);
		} else { 
		    PropertyDataAccess.update(conn, storePriWebAddr);
		}
	    }

	    EmailData customerServiceEmail = 
		pStoreData.getCustomerServiceEmail();
	    if (customerServiceEmail.isDirty()) {
		if (customerServiceEmail.getEmailId() == 0) {
		    customerServiceEmail.setBusEntityId(storeId);
		    customerServiceEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE);
		    customerServiceEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
		    customerServiceEmail.setPrimaryInd(false);
		    EmailDataAccess.insert(conn, customerServiceEmail);
		} else {
		    EmailDataAccess.update(conn, customerServiceEmail);
		}
	    }

	    EmailData contactUsEmail = pStoreData.getContactUsEmail();
	    if (contactUsEmail.isDirty()) {
		if (contactUsEmail.getEmailId() == 0) {
		    contactUsEmail.setBusEntityId(storeId);
		    contactUsEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.CONTACT_US);
		    contactUsEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
		    contactUsEmail.setPrimaryInd(false);
		    EmailDataAccess.insert(conn, pStoreData.getContactUsEmail());
		} else {
		    EmailDataAccess.update(conn, pStoreData.getContactUsEmail());
		}
	    }
	    
	    EmailData defaultEmail = pStoreData.getDefaultEmail();
	    if (defaultEmail.isDirty()) {
		if (defaultEmail.getEmailId() == 0) {
		    defaultEmail.setBusEntityId(storeId);
		    defaultEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
		    defaultEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
		    defaultEmail.setPrimaryInd(false);
		    EmailDataAccess.insert(conn, defaultEmail);
		} else {
		    EmailDataAccess.update(conn, defaultEmail);
		}
	    }

	    EmailData primaryEmail = pStoreData.getPrimaryEmail();
	    if (primaryEmail.isDirty()) {
		if (primaryEmail.getEmailId() == 0) {
		    primaryEmail.setBusEntityId(storeId);
		    primaryEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
		    primaryEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
		    primaryEmail.setPrimaryInd(true);
		    EmailDataAccess.insert(conn, primaryEmail);
		} else {
		    EmailDataAccess.update(conn, primaryEmail);
		}
	    }

	    PhoneData primaryPhone = pStoreData.getPrimaryPhone();
	    if (primaryPhone.isDirty()) {
		if (primaryPhone.getPhoneId() == 0) {
		    primaryPhone.setBusEntityId(storeId);
		    primaryPhone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
		    primaryPhone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
		    primaryPhone.setPrimaryInd(true);
		    PhoneDataAccess.insert(conn, primaryPhone);
		} else {
		    PhoneDataAccess.update(conn, primaryPhone);
		}
	    }

	    PhoneData primaryFax = pStoreData.getPrimaryFax();
	    if (primaryFax.isDirty()) {
		if (primaryFax.getPhoneId() == 0) {
		    primaryFax.setBusEntityId(storeId);
		    primaryFax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
		    primaryFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
		    primaryFax.setPrimaryInd(true);
		    PhoneDataAccess.insert(conn, primaryFax);
		} else {
		    PhoneDataAccess.update(conn, primaryFax);
		}
	    }

	    AddressData primaryAddress = pStoreData.getPrimaryAddress();
	    if (primaryAddress.isDirty()) {	    
		if (primaryAddress.getAddressId() == 0) {
		    primaryAddress.setBusEntityId(storeId);
		    primaryAddress.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
		    primaryAddress.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
		    primaryAddress.setPrimaryInd(true);
		    AddressDataAccess.insert(conn, primaryAddress);
		} else {
		    AddressDataAccess.update(conn, primaryAddress);
		}
	    }

	    PropertyData storeType = pStoreData.getStoreType();
	    if (storeType.isDirty()) {
		storeType.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
		storeType.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		storeType.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
		storeType.setBusEntityId(storeId);
		if (storeType.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, storeType);
		} else {
		    PropertyDataAccess.update(conn, storeType);
		}
	    }
            
            
            PropertyData callHours = pStoreData.getCallHours();
	    if (callHours.isDirty()) {
                String type = RefCodeNames.PROPERTY_TYPE_CD.CALL_HOURS;
                callHours.setShortDesc(type);
		callHours.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
		callHours.setPropertyTypeCd(type);
		callHours.setBusEntityId(storeId);
		if (callHours.getPropertyId() == 0) {
		    PropertyDataAccess.insert(conn, callHours);
		} else {
		    PropertyDataAccess.update(conn, callHours);
		}
	    }
            
            PropertyUtil pru = new PropertyUtil(conn);
            pru.saveValue(0, storeId, RefCodeNames.PROPERTY_TYPE_CD.ODD_ROW_COLOR, RefCodeNames.PROPERTY_TYPE_CD.ODD_ROW_COLOR, pStoreData.getOddRowColor());
            pru.saveValue(0, storeId, RefCodeNames.PROPERTY_TYPE_CD.EVEN_ROW_COLOR, RefCodeNames.PROPERTY_TYPE_CD.EVEN_ROW_COLOR, pStoreData.getEvenRowColor());
            pru.saveValue(0, storeId, RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION, RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION, pStoreData.getPendingOrderNotification());
	    PropertyDataVector miscProperties = 
		pStoreData.getMiscProperties();
	    
	    //log.info("**********SVC: miscProperties = " + miscProperties);
	    
	    Iterator propIter = miscProperties.iterator();	    
	    while (propIter.hasNext()) {
	      PropertyData prop = (PropertyData)propIter.next();
	      //log.info("**********SVC: prop = " + prop);
              if (prop.getPropertyId() == 0) {
        	    //log.info("**********SVC: propertyId = 0"); 
                String propStatus = prop.getPropertyStatusCd();
                if(propStatus==null || propStatus.trim().length()==0) {
		           prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                }
                String propType = prop.getPropertyTypeCd();
                if(propType==null || propType.trim().length()==0) {  
                  prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
                };
                prop.setBusEntityId(storeId);
                PropertyDataAccess.insert(conn, prop);
	          } else {
	        	//log.info("**********SVC: propertyId != 0");  
		        PropertyDataAccess.update(conn, prop);
	      }
	    }
	    SequenceUtilDAO.checkAndCreateIfNeed(conn, storeId);
	} catch (Exception e) {
	    throw new RemoteException("updateStore: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return pStoreData;
    }

    public DomainData updateDomain(DomainData pDomainData)
    	throws RemoteException {
    	
		Connection conn = null;
	
		try {
		    conn = getConnection();
		    BusEntityData busEntity = pDomainData.getBusEntity();
	//	    if (busEntity.isDirty()) {
		    int domainId = pDomainData.getBusEntity().getBusEntityId();
			if (domainId == 0) {
	//			busEntity.setAddBy()
	//			busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME);
			    busEntity = BusEntityDataAccess.insert(conn, busEntity);

//	            DBCriteria crit = new DBCriteria();
//	            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,
//                        domainId);			    
//			    BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,
//	                    crit);
//	            Iterator iter = busEntityVec.iterator();
//	            while (iter.hasNext()) {
//	            	busEntity = (BusEntityData)iter.next();
//	            }

//	            appDomainData.setApplicationDomainName(BusEntityData.createValue());
//	            appDomainData.getApplicationDomainName().setShortDesc(request.getServerName());
//	            appDomainData.setSslDomainName(request.getServerName());
//	            appDomainData.setDefaultDomain(true);
//	            appDomainData.setDefaultStore(scratchDomainData.getDefaultStore());            
	            
//	            while (iter.hasNext()) {
//	            	domData = getDomainDetails((BusEntityData)iter.next());
//	            	
//	            	domainVec.add(domData);
//	            }			
			
			} else {
			    BusEntityDataAccess.update(conn, busEntity);
			}
			pDomainData.setBusEntity(busEntity);
	//	    }
//		    int domainId = pDomainData.getBusEntity().getBusEntityId();
	        PropertyData SSLName = pDomainData.getSSLName();
	        SSLName.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME);
	        SSLName.setPropertyStatusCd(busEntity.getBusEntityStatusCd());
            SSLName.setBusEntityId(busEntity.getBusEntityId());
            SSLName.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SSL_DOMAIN_NAME);
            SSLName.setAddBy(busEntity.getAddBy());
            SSLName.setModBy(busEntity.getModBy());
//            SSLName.setPropertyId(domainId);
	        
			if (domainId == 0) {
			    PropertyDataAccess.insert(conn, SSLName);				
			} else {
			    PropertyDataAccess.update(conn, SSLName);				
			}
			

//	        if (sid == 0) {
//	            busEntData.setAddBy(appUser.getUserName());
//	            busEntData.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.DOMAIN_NAME); 
//	            busEntData.setLocaleCd("unk");                
//	            busEntData.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
//	        }
//	
//	        busEntData.setBusEntityId(Integer.parseInt(sForm.getId()));
	
//	        busEntData.setLongDesc(sForm.getLongDescription());
//	        busEntData.setModBy(appUser.getUserName());
//	        busEntData.setShortDesc(sForm.getName());
	        

//	    PropertyData prefix = pStoreData.getPrefix();
//	    if (prefix.isDirty()) {
//		if (prefix.getPropertyId() == 0) {
//		    prefix.setShortDesc(STORE_PREFIX_PROP);
//		    prefix.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		    prefix.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_PREFIX_CODE);
//		    prefix.setBusEntityId(storeId);
//		    PropertyDataAccess.insert(conn, prefix);
//		} else { 
//		    PropertyDataAccess.update(conn, prefix);
//		}
//	    }
//           PropertyData storeBusName = pStoreData.getStoreBusinessName();
//		if (storeBusName.getPropertyId() == 0) {
//		    storeBusName.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.STORE_BUSINESS_NAME);
//		    storeBusName.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//		    storeBusName.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STORE_BUSINESS_NAME);
//		    storeBusName.setBusEntityId(storeId);
//		    PropertyDataAccess.insert(conn, storeBusName);
//		} else { 
//		    PropertyDataAccess.update(conn, storeBusName);
//
//	    } 

    	} catch (Exception e) {
    	    throw new RemoteException("updateDomain: "+e.getMessage());
    	} finally {
    	    try {if (conn != null) conn.close();} catch (Exception ex) {}
    	}
		return pDomainData;
    }
    /**
     * <code>removeStore</code> may be used to remove an 'unused' store.
     * An unused store is a store with no database references other than
     * the default primary address, phone numbers, email addresses and
     * properties.  Attempting to remove a store that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pStoreData a <code>StoreData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeStore(StoreData pStoreData)
	throws RemoteException
    {
	Connection conn = null;
	try {
	    conn = getConnection();
	    
	    int storeId = pStoreData.getBusEntity().getBusEntityId();

	    DBCriteria crit = new DBCriteria();
	    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, storeId);

	    PropertyDataAccess.remove(conn, crit);
	    PhoneDataAccess.remove(conn, crit);
	    EmailDataAccess.remove(conn, crit);
	    AddressDataAccess.remove(conn, crit);
	    BusEntityDataAccess.remove(conn, storeId);
	} catch (Exception e) {
	    throw new RemoteException("removeStore: "+e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}
    }

    /**
     * gets store identifier
     *
     * @param accountId account identifier
     * @return store identifier
     * @throws RemoteException if an errors
     */
    public int getStoreIdByAccount(int accountId) throws RemoteException,DataNotFoundException {
        Connection conn = null;
        try {

            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, accountId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

            IdVector storeIdV = BusEntityAssocDataAccess.selectIdOnly(conn,
                    BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);

            if (storeIdV.size() == 0) {
                throw new DataNotFoundException("No store found for account. Account Id: " + accountId);
            }

            if (storeIdV.size() > 1) {
                throw new Exception("More than one store found for account. Accont Id: "
                        + accountId);
            }
            Integer storeIdI = (Integer) storeIdV.get(0);
            return storeIdI.intValue();
        }
        catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public BusEntityData getStoreBusEntityByAccount(int pAccountId) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();

            BusEntitySearchCriteria pCrit = new BusEntitySearchCriteria();
            pCrit.setAccountBusEntityIds(Utility.toIdVector(pAccountId));

            BusEntityDataVector stores = BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            if (stores.size() == 0) {
                throw new DataNotFoundException("No store found for account. Account Id: " + pAccountId);
            }

            if (stores.size() > 1) {
                throw new Exception("More than one store found for account. Accont Id: " + pAccountId);
            }

            return (BusEntityData) stores.get(0);
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     *Gets a list of StoreData objects based off the supplied search criteria object
     *@param pCrit the search criteria
     *@return                      a set of StoreData objects
     *@exception  RemoteException  if an error occurs
     */
    public StoreDataVector getStoresByCriteria(BusEntitySearchCriteria pCrit)
             throws RemoteException {
        StoreDataVector storeVec = new StoreDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDataVector busEntityVec = 
                BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            Iterator iter = busEntityVec.iterator();
            while (iter.hasNext()) {
                storeVec.add(getStoreDetails((BusEntityData) iter.next()));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return storeVec;
    }

    /**
     * Gets a list of Store type BusEntityData objects based off the supplied search criteria object
     *
     * @param pCrit the search criteria
     * @return a set of StoretData objects
     * @throws RemoteException if an error occurs
     */
    public BusEntityDataVector getStoresBusEntByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public IdVector getStoreIdsByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDataVector stores = BusEntityDAO.getBusEntityByCriteria(conn, pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            return Utility.toIdVector(stores);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * gets linked store-item id pairs for a managed item
     * @param parentItemId
     * @param userId
     * @param userTypeCd
     * @return PairViewVector
     * @throws RemoteException
     */
 public PairViewVector getLinkStoreItemPairIdsByParentItem(int parentItemId,int userId,String userTypeCd) throws RemoteException {
        Connection con=null;
        PairViewVector pairVV=new PairViewVector();
        try {

            if(Utility.isSet(userTypeCd)){
                con=getConnection();
                String sql=null;
                if(userTypeCd.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR))
                {
                    sql=new String("select i.item_id, ca.bus_entity_id " +
                            "  from " +
                            "   clw_item_assoc ia, " +
                            "   clw_item i," +
                            "   clw_catalog_structure cs," +
                            "   clw_catalog c," +
                            "   clw_catalog_assoc ca" +
                            "  where cs.catalog_id = c.catalog_id" +
                            "  and c.catalog_type_cd = 'STORE'" +
                            "  and ca.catalog_id = c.catalog_id" +
                            "  and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                            "  and i.item_id = ia.item1_id" +
                            "  and i.item_status_cd = 'ACTIVE'" +
                            "  and i.item_id = cs.item_id" +
                            "  and ia.item_assoc_cd = 'MANAGED_ITEM_PARENT'" +
                            "  and ia.item2_id = "+parentItemId);

                }
                else
                {
				    DBCriteria dbc = new DBCriteria();
					dbc.addEqualTo(UserAssocDataAccess.USER_ID,userId);
                    dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                              RefCodeNames.USER_ASSOC_CD.STORE);
                    IdVector storeIdV = 
						UserAssocDataAccess.selectIdOnly(con, UserAssocDataAccess.BUS_ENTITY_ID,dbc);
					if(storeIdV.size()==0) storeIdV.add(-1);
				
                    sql=new String("select i.item_id, ca.bus_entity_id " +
                            " from " +
                            "   clw_item_assoc ia, " +
                            "   clw_item i," +
                            "   clw_catalog_structure cs," +
                            "   clw_catalog c," +
                            "   clw_catalog_assoc ca" +
                            "  where ca.bus_entity_id in (" +IdVector.toCommaString(storeIdV)+")"+
                            "  and cs.catalog_id = c.catalog_id" +
                            "  and c.catalog_type_cd = 'STORE'" +
                            "  and ca.catalog_id = c.catalog_id" +
                            "  and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                            "  and i.item_id = ia.item1_id" +
                            "  and i.item_status_cd = 'ACTIVE'" +
                            "  and i.item_id = cs.item_id" +
                            "  and ia.item_assoc_cd = 'MANAGED_ITEM_PARENT'" +
                            "  and ia.item2_id = "+parentItemId);

                }
                logInfo("Item sql: "+sql);
                Statement stmt = con.createStatement();
                ResultSet rs=stmt.executeQuery(sql);
                while(rs.next()){
                    int itemId=rs.getInt(1);
                    int storeId=rs.getInt(2);
                    pairVV.add(new PairView(new Integer(storeId),new Integer(itemId)));
                }
            }
        } catch (SQLException e) {
            logError("e.getMessage");
            e.printStackTrace();
            throw new RemoteException(
                    "Error. StoreBean.getLinkStoreItemPairIdsByParentItem() SQL Exception happened");
        } catch (NamingException e) {
            logError("e.getMessage");
            e.printStackTrace();
            throw new RemoteException(
                    "Error. StoreBean.getLinkStoreItemPairIdsByParentItem() Naming Exception happened");
        } finally {
            closeConnection(con);
        }
        return pairVV;

    }

    public PairViewVector getLinkStoreItemPairIdsByParentItemBetweenStores(
        int parentItemId, int userId,String userTypeCd) throws RemoteException {
        Connection con = null;
        PairViewVector pairVV = new PairViewVector();
        try {
            if(Utility.isSet(userTypeCd)) {
                con = getConnection();
                String sql = null;
                if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)) {
                    sql = new String(
                         "select i.item_id, ca.bus_entity_id " +
                         "  from " +
                         "   clw_item_assoc ia, " +
                         "   clw_item i," +
                         "   clw_catalog_structure cs," +
                         "   clw_catalog c," +
                         "   clw_catalog_assoc ca" +
                         "  where cs.catalog_id = c.catalog_id" +
                         "  and c.catalog_type_cd = 'STORE'" +
                         "  and ca.catalog_id = c.catalog_id" +
                         "  and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                         "  and i.item_id = ia.item2_id" +
                         "  and i.item_status_cd = 'ACTIVE'" +
                         "  and i.item_id = cs.item_id" +
                         "  and ia.item_assoc_cd = 'CROSS_STORE_ITEM_LINK'" +
                         "  and ia.item1_id = "+parentItemId);

                }
                else {
                    sql = new String(
                         "select i.item_id, ca.bus_entity_id " +
                         " from " +
                         "   clw_item_assoc ia, " +
                         "   clw_item i," +
                         "   clw_catalog_structure cs," +
                         "   clw_catalog c," +
                         "   clw_catalog_assoc ca," +
                         "   clw_user_assoc ua" +
                         "  where ua.user_assoc_cd = 'STORE'" +
                         "  and ua.user_id = "+userId+"" +
                         "  and ua.bus_entity_id = ca.bus_entity_id " +
                         "  and cs.catalog_id = c.catalog_id" +
                         "  and c.catalog_type_cd = 'STORE'" +
                         "  and ca.catalog_id = c.catalog_id" +
                         "  and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                         "  and i.item_id = ia.item2_id" +
                         "  and i.item_status_cd = 'ACTIVE'" +
                         "  and i.item_id = cs.item_id" +
                         "  and ia.item_assoc_cd = 'CROSS_STORE_ITEM_LINK'" +
                         "  and ia.item1_id = "+parentItemId);

                }

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int itemId = rs.getInt(1);
                    int storeId = rs.getInt(2);
                    pairVV.add(new PairView(new Integer(storeId), new Integer(itemId)));
                }
            }
        } 
        catch (SQLException e) {
            logError("e.getMessage");
            e.printStackTrace();
            throw new RemoteException("Error. StoreBean.getLinkStoreItemPairIdsByParentItemBetweenStores() SQL Exception happened");
        } 
        catch (NamingException e) {
            logError("e.getMessage");
            e.printStackTrace();
            throw new RemoteException("Error. StoreBean.getLinkStoreItemPairIdsByParentItemBetweenStores() Naming Exception happened");
        } 
        finally {
            closeConnection(con);
        }
        return pairVV;
    }

    /**
     * Seperates the store ids into their associatied types:
     * input: 1,2,3,4
     * return: DISTIBUTOR: 1,2
     * MLA: 3
     * MANUFACTURER 4
     *
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *         when one of the passed in stores does not hae a type configured
     * @throws java.rmi.RemoteException if there was a problem retrieving
     *         the store types from the passed in ids
     */
    public Map seperateStoresByType(IdVector pStoreIds) throws DataNotFoundException, RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            HashMap theMap = new HashMap();
            Iterator it = pStoreIds.iterator();
            PropertyUtil pru = new PropertyUtil(conn);
            while (it.hasNext()) {
                Integer id = ((Integer) it.next());
                String key = pru.fetchValue(0, id.intValue(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
                IdVector sepStoreIds;
                if (theMap.containsKey(key)) {
                    sepStoreIds = (IdVector) theMap.get(key);
                } else {
                    sepStoreIds = new IdVector();
                    theMap.put(key, sepStoreIds);
                }
                sepStoreIds.add(id);
            }
            return theMap;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

    }
    /**
     *  Adds a feature to the UserAssoc attribute of the UserBean object
     *
     *@param  con                        The feature to be added to the
     *      UserAssoc attribute
     *@param  pStoreId                    The feature to be added to the
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
    public BusEntityAssocData addStoreAssoc(Connection con, int pStoreId,
                                      int pBusEntityId, String pType, String adderName)
    throws SQLException, DataNotFoundException, RemoteException {
        DBCriteria dbc = new DBCriteria();
//        dbc.addEqualTo(StoreAssocDataAccess.USER_ID, pStoreId);
//        dbc.addEqualTo(StoreAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pStoreId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, pType);
//        logDebug("add user assoc: "
//                 + " pStoreId=" + pStoreId
//                 + " pBusEntityId=" + pBusEntityId
//                 + " pType=" + pType
//                 );
        BusEntityAssocDataVector storeAssocV = BusEntityAssocDataAccess.select(con, dbc);
        if (storeAssocV.size() > 0) {
        	BusEntityAssocData storeAssocD = (BusEntityAssocData) storeAssocV.get(0);
            logDebug("found store assoc: " + storeAssocD);
            return storeAssocD;
        }
        
        BusEntityData storeD = BusEntityDataAccess.select(con, pStoreId);
        //just to be sure that it exists
        BusEntityData domainD = BusEntityDataAccess.select(con, pBusEntityId);
        //String assocType = RefCodeNames.USER_ASSOC_CD.SITE;
        String assocType = pType;
        String busEntityType = domainD.getBusEntityTypeCd();
        
        BusEntityAssocData storeAssocD = BusEntityAssocData.createValue();
        storeAssocD.setBusEntity1Id(pStoreId);
        storeAssocD.setBusEntity2Id(pBusEntityId);
        storeAssocD.setBusEntityAssocCd(assocType);
        storeAssocD.setAddBy(adderName);
        storeAssocD.setModBy(adderName);
        
        return addStoreAssoc(con, storeAssocD);
    }

//    public BusEntityAssocData addDefaultStoreAssoc(Connection con, int pStoreId,
//            int pBusEntityId, String pType)
//    			throws SQLException, DataNotFoundException, RemoteException {
//    	DBCriteria dbc = new DBCriteria();
////dbc.addEqualTo(StoreAssocDataAccess.USER_ID, pStoreId);
////dbc.addEqualTo(StoreAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
//    	dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pStoreId);
//    	dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
////logDebug("add user assoc: "
////+ " pStoreId=" + pStoreId
////+ " pBusEntityId=" + pBusEntityId
////+ " pType=" + pType
////);
//    	BusEntityAssocDataVector storeAssocV = BusEntityAssocDataAccess.select(con, dbc);
//    	if (storeAssocV.size() > 0) {
//    		BusEntityAssocData storeAssocD = (BusEntityAssocData) storeAssocV.get(0);
//    		logDebug("found default store assoc: " + storeAssocD);
//    		return storeAssocD;
//    	}
//
//    	BusEntityData storeD = BusEntityDataAccess.select(con, pStoreId);
////just to be sure that it exists
//    	BusEntityData domainD = BusEntityDataAccess.select(con, pBusEntityId);
////String assocType = RefCodeNames.USER_ASSOC_CD.SITE;
//    	String assocType = pType;
//    	String busEntityType = domainD.getBusEntityTypeCd();
//
//    	BusEntityAssocData storeAssocD = BusEntityAssocData.createValue();
//    	storeAssocD.setBusEntity1Id(pStoreId);
//    	storeAssocD.setBusEntity2Id(pBusEntityId);
//    	storeAssocD.setBusEntityAssocCd(assocType);
//    	return addStoreAssoc(con, storeAssocD);
//}
    
    
    public BusEntityAssocData addStoreAssoc(int pStoreId, int pBusEntityId, String pType, String adderName)
    throws RemoteException, DataNotFoundException {
    	BusEntityAssocData storeAssocRD = null;
        Connection con = null;
        BusEntityData busEntityD = null;
        try {
            con = getConnection();
            storeAssocRD = addStoreAssoc(con, pStoreId, pBusEntityId, pType, adderName);
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. StoreBean.addStoreAssoc() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. StoreBean.addStoreAssoc() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return storeAssocRD;
    }    
    
    public BusEntityAssocData addStoreAssoc(Connection con, BusEntityAssocData pStoreAssoc)
    throws RemoteException, SQLException {
        Date date = new Date(System.currentTimeMillis());
        pStoreAssoc.setAddBy(pStoreAssoc.getAddBy());
        pStoreAssoc.setAddDate(date);
        pStoreAssoc.setModBy(pStoreAssoc.getModBy());
        pStoreAssoc.setModDate(date);
        
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pStoreAssoc.getBusEntity1Id());
        crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, pStoreAssoc.getBusEntityAssocCd());
        crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreAssoc.getBusEntity2Id());
        
        BusEntityAssocDataVector storeAssocDV = BusEntityAssocDataAccess.select(con,crit);
        if(storeAssocDV.isEmpty()){
        	BusEntityAssocData mStoreAssoc = BusEntityAssocDataAccess.insert(con, pStoreAssoc);
        	return mStoreAssoc;
        }else{
        	return (BusEntityAssocData) storeAssocDV.get(0);
        }
        
    }
    
    /**
     *  Removes the user association information values
     *
     *@param  pStoreId           the catalog id.
     *@param  pBusEntityId      the business entity id.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public void removeStoreAssoc(int pStoreId, int pBusEntityId, String pType)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            
            // The following condition takes care of removing 
            // direct bus entity relations to this store
            // as well as any bus entities related to this
            // bus entity.  
            String allbusentities = " select bus_entity1_id from "
            + " clw_bus_entity_assoc where"
            + " bus_entity2_id = " + pBusEntityId
            + " union select " + pBusEntityId + " from dual ";

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pStoreId);
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, allbusentities);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, pType);
            BusEntityAssocDataAccess.remove(con, dbc);
        }
        catch (Exception e) {
            throw new RemoteException("Error. StoreBean.removeStoreAssoc() Exception happened");
        } finally {
            closeConnection(con);
        }
    }


    public int updateStoreAssoc(int pStoreId, int pBusEntityId, String pType)
    throws SQLException, DataNotFoundException, RemoteException {
        
        BusEntityAssocData storeAssocRes = null;
        Connection con = null;
        int result = 0;
        
        try{
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pStoreId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pBusEntityId);
            BusEntityAssocDataVector storeAssocV = BusEntityAssocDataAccess.select(con, dbc);
            
            if(storeAssocV.size() == 0){
                //the associated account may have changed so we'll get it by type
                DBCriteria dbc2 = new DBCriteria();
                dbc2.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pStoreId);
                dbc2.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID, pType);
                storeAssocV = BusEntityAssocDataAccess.select(con, dbc2);
            }
            BusEntityAssocData storeAssoc = (BusEntityAssocData) storeAssocV.get(0);
            
            if (storeAssocV.size() > 1 || storeAssocV.size() == 0) {
                return 0;
            }
            
            String assocType = pType;
            Date date = new Date(System.currentTimeMillis());
            storeAssoc.setBusEntity2Id(pBusEntityId);
            storeAssoc.setBusEntityAssocCd(assocType);
            storeAssoc.setModDate(date);
            
            result = BusEntityAssocDataAccess.update(con, storeAssoc);
            
        }catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. StoreBean.updateStoreAssoc() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. StoreBean.updateStoreAssoc() SQL Exception happened");
        } finally {
            try {if (con != null) con.close();} catch (Exception ex) {}
        }
        return result;
    }


    public IdVector getStoreIdsByType(String pStoreType) throws RemoteException {
        Connection conn = null;
        try {

            conn = getConnection();
            return getStoreIdsByType(conn, pStoreType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    private IdVector getStoreIdsByType(Connection conn, String pStoreType) throws SQLException {

        DBCriteria dbCrit = new DBCriteria();

        dbCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        dbCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

        dbCrit.addJoinTableEqualTo(PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        dbCrit.addJoinTableEqualTo(PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
        dbCrit.addJoinTableEqualTo(PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.CLW_VALUE, pStoreType);
        dbCrit.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID, PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.BUS_ENTITY_ID);

        return Utility.toIdVector(BusEntityDataAccess.select(conn, dbCrit));

    }
    
    public String getDefaultEmailForStoreUser(int pUserId) throws RemoteException {
    	Connection conn = null;
    	String defaultEmail = null;
        try {
        	
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.STORE);
            IdVector stores = UserAssocDataAccess.selectIdOnly(conn, UserAssocDataAccess.BUS_ENTITY_ID, crit);
        
            if(stores!=null){
            	int storeId = ((Integer)stores.get(0)).intValue();
            	
            	crit = new DBCriteria();
            	crit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, storeId);
            	crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
            	
            	EmailDataVector eDV = EmailDataAccess.select(conn, crit);
            	if(eDV!=null && eDV.size()>0){
            		EmailData eD = (EmailData)eDV.get(0);
            		defaultEmail = eD.getEmailAddress();
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
        return defaultEmail;
    }
    
    public BusEntityDataVector getChildStores(int storeId) throws RemoteException {
        BusEntityDataVector childStores = new BusEntityDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            crit.addJoinTable(PropertyDataAccess.CLW_PROPERTY + " PROPS");

            DBCriteria isolCrit = new DBCriteria();
            isolCrit.addCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID + "=" + "PROPS." + PropertyDataAccess.BUS_ENTITY_ID);
            isolCrit.addEqualTo("PROPS." + PropertyDataAccess.SHORT_DESC,  RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
            isolCrit.addEqualTo("PROPS." + PropertyDataAccess.PROPERTY_STATUS_CD,  RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            isolCrit.addEqualTo("PROPS." + PropertyDataAccess.CLW_VALUE,  storeId+"");

            crit.addIsolatedCriterita(isolCrit);

            childStores = BusEntityDataAccess.select(conn, crit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.toString());
        } finally {
            closeConnection(conn);
        } 
        return childStores;
    }
	public void loadMasterAsset(int staged_assetId, int assetId, ICleanwiseUser appUser)  throws RemoteException {
//      String userName = user.getUser().getUserName();
        String userName = "MasterAssetLoader";

    	Connection connection = null;
        try {
        	MasterLoader masterAssetLoader = 
        		new MasterAssetLoader(staged_assetId, assetId, userName);
        	connection = getConnection();
			masterAssetLoader.load(connection);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw new RemoteException("loadMasterAsset: " + e.getMessage());
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
		
	}
	

	public void loadMasterAsset(int staged_assetId, ICleanwiseUser appUser)  throws RemoteException {
//      String userName = user.getUser().getUserName();
        String userName = "MasterAssetLoader";

    	Connection connection = null;
        try {
        	MasterLoader masterAssetLoader = 
        		new MasterAssetLoader(staged_assetId, userName);
        	connection = getConnection();
			masterAssetLoader.load(connection);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw new RemoteException("loadMasterAsset: " + e.getMessage());
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
		
	}
    public void loadMasterAsset(String loadingTableName, ICleanwiseUser user)  throws RemoteException {
    	
        boolean stageUnmatched = Boolean.parseBoolean(Utility
				.getPropertyValue(user.getUserStore()
						.getMiscProperties(),
						RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED));
        loadMasterAsset(loadingTableName, stageUnmatched, user);
    }

    public void loadMasterAsset(String loadingTableName, boolean stageUnmatched, ICleanwiseUser user)  throws RemoteException {
//        String userName = user.getUser().getUserName();
        String userName = "MasterAssetLoader";
    	Connection connection = null;
        try {
        	MasterLoader masterAssetLoader = 
        		new MasterAssetLoader(loadingTableName, stageUnmatched, userName);
        	connection = getConnection();
			masterAssetLoader.load(connection);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw new RemoteException("loadMasterAsset: " + e.getMessage(), e);
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
        
    }

    public void loadMasterAsset(String loadingTableName, int storeId, String userName)  
	throws RemoteException {
    	Connection conn = null;
        try {
			conn = getConnection();

			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
			dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED);
			PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);
			boolean stageUnmatched = false;
			if(pDV.size()>0) {
				PropertyData pD = (PropertyData) pDV.get(0);
				String val = pD.getValue();
				if(Utility.isSet(val)) {
					stageUnmatched = Utility.isTrue(val);
				}
			}
        	MasterLoader masterAssetLoader =
        		new MasterAssetLoader(loadingTableName, stageUnmatched, userName);
			masterAssetLoader.load(conn);
        }
        catch(Exception e)
        {
        	throw new RemoteException("loadMasterAsset: " + e.getMessage(),e);
        }
		finally{
			if(conn!=null) closeConnection(conn);
		}
    }

    public void splitAssetItemTable(String loadingTableName)  
	throws RemoteException {
    	Connection conn = null;
        try {
		conn = getConnection();
		loadingTableName = loadingTableName.trim();
		String itemSql = 
           "CREATE TABLE " + loadingTableName + "I AS \n\r"+
           " SELECT * FROM " + loadingTableName + " \n\r"+  
           "  WHERE Lower(Trim(ASSET)) != 'true' \n\r";
        logInfo("StoreBean. Create item loading table sql: "+ itemSql); 
		Statement stmt = conn.createStatement();
        stmt.execute(itemSql);
		
		   
		String assetSql = 
           "CREATE TABLE " + loadingTableName + "A AS \n\r"+
           " SELECT * FROM " + loadingTableName + " \n\r"+  
           "  WHERE Lower(Trim(ASSET)) = 'true' \n\r";

	    logInfo("StoreBean. Create asset loading table sql: "+ assetSql); 
        stmt.execute(assetSql);
        stmt.close();

		}
        catch(Exception e)
        {
        	throw new RemoteException("loadMasterAsset: " + e.getMessage(),e);
        }
		finally{
			if(conn!=null) closeConnection(conn);
		}
    }

    public void loadMasterItem(String loadingTableName, ICleanwiseUser user)  throws RemoteException {
    	
        boolean stageUnmatched = Boolean.parseBoolean(Utility
				.getPropertyValue(user.getUserStore()
						.getMiscProperties(),
						RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED));
//        String userName = user.getUser().getUserName();
        String userName = "MasterAssetLoader";
    	Connection connection = null;
        try {
        	MasterLoader masterItemLoader = 
        		new MasterItemLoader(loadingTableName, stageUnmatched, userName);
        	connection = getConnection();
			masterItemLoader.load(connection);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw new RemoteException("loadMasterAsset: " + e.getMessage(), e);
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
        
    }
    
    public void loadMasterItem(String loadingTableName, int storeId, String userName)  throws RemoteException {
    	
        userName = "MasterItemLoader";
    	Connection conn = null;
        try {

			conn = getConnection();
			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
			dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED);
			PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);
			boolean stageUnmatched = false;
			if(pDV.size()>0) {
				PropertyData pD = (PropertyData) pDV.get(0);
				String val = pD.getValue();
				if(Utility.isSet(val)) {
					stageUnmatched = Utility.isTrue(val);
				}
			}
        	MasterLoader masterItemLoader = 
        		new MasterItemLoader(loadingTableName, stageUnmatched, userName);
			masterItemLoader.load(conn);
        }
        catch(Exception e)
        {
        	throw new RemoteException("loadMasterItem: " + e.getMessage(),e);
        }
        finally {
        	try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
        
    }

    public void loadPhysicalAsset(String loadingTableName, ICleanwiseUser user)  
	throws RemoteException {
       String userName = "PhysicalAssetLoader";
	   loadPhysicalAsset(loadingTableName, 0, userName);  
	}
    
	public void loadPhysicalAsset(String loadingTableName, int storeId, String userName)  
                                                                    throws RemoteException {
    	Connection connection = null;
        try {
        	PhysicalAssetLoader physicalAssetLoader = 
        		new PhysicalAssetLoader(loadingTableName, userName);
        	connection = getConnection();
                physicalAssetLoader.load(connection);
        } catch(Exception e) {
        	e.printStackTrace();
                throw new RemoteException("loadPhysicalAsset: " + e.getMessage());
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
    }
    
    public void synchronizeChildStore(int childStoreId, ICleanwiseUser user)  throws RemoteException {
    	
    	int parentStoreId = 0;
    	try {
			PropertyServiceBean propertyBean = new PropertyServiceBean();
			String parentStoreIdStr = propertyBean.getBusEntityProperty(childStoreId, RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
			parentStoreId = Integer.parseInt(parentStoreIdStr);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RemoteException("synchronizeChildStore: " + e.getMessage());
    	}

		synchronizeParentChildStores(parentStoreId, childStoreId, 0, user);
		synchronizeParentChildStoreItems(parentStoreId, childStoreId, 0, user);
		
    }
        
    public void synchronizeMasterAsset(int parentStoreId, int masterAssetId, ICleanwiseUser user)  throws RemoteException {
    	
		synchronizeParentChildStores(parentStoreId, 0, masterAssetId, user);
    }

	private void synchronizeParentChildStores(int parentStoreId, int childStoreId, int assetId, ICleanwiseUser user)
			throws RemoteException {
		Connection connection = null;
		try {
//			final String userName = user.getUser().getUserName();
			final String userName = "Parent Store";
			connection = this.getConnection();
			AssetData assetData = null; 
			if(assetId > 0) {
			     assetData = AssetDataAccess.select(connection, assetId);
			} 
			
			if(assetData != null && assetData.getAssetTypeCd().equals(RefCodeNames.ASSET_TYPE_CD.CATEGORY))
			{
				CategorySynchronizer synchronizer =
					new CategorySynchronizer(parentStoreId, assetId, userName);
				synchronizer.synchronize(connection);
			}
			else 
			{
				ParentChildStoresSynchronizer synchronizer =
					new ParentChildStoresSynchronizer(parentStoreId, childStoreId, assetId, userName);
				synchronizer.synchronize(connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		    throw new RemoteException("synchronizeChildStore: " + e.getMessage());
		} finally {
		    try {if (connection != null) connection.close();} catch (Exception ex) {}
		}
	}
	
	public void synchronizeParentChildStoreItems(int parentStoreId, int childStoreId, int parentItemId, ICleanwiseUser user)
			throws RemoteException {
		Connection conn = null;
		try {
            String userName = "Parent Store";
			StoreItemSynchronizer synchronizer =
				new StoreItemSynchronizer(parentStoreId, childStoreId, parentItemId, userName);
			conn = getConnection();
			synchronizer.synchronizeCategories(conn);
			synchronizer.synchronize(conn);
		} catch (Exception e) {
			e.printStackTrace();
		    throw new RemoteException("synchronizeChildStore: " + e.getMessage());
		} finally {
            closeConnection(conn);
		}
	}
	public void synchronizeParentChildStoreCateg(int parentStoreId, int childStoreId, ICleanwiseUser user)
			throws RemoteException {
		Connection conn = null;
		try {
            String userName = "Parent Store";
			StoreItemSynchronizer synchronizer =
				new StoreItemSynchronizer(parentStoreId, childStoreId, 0, userName);
			conn = getConnection();
			synchronizer.synchronizeCategories(conn);
		} catch (Exception e) {
			e.printStackTrace();
		    throw new RemoteException("synchronizeChildStore: " + e.getMessage());
		} finally {
            closeConnection(conn);
		}

	}


    public void loadMasterAssetFromStaged(ICleanwiseUser user)  throws RemoteException {
        loadMasterAsset("clw_staged_asset", false, user);
    	Connection connection = null;
        try
        {
        	connection = getConnection();
        	StagedAssetAssocDataAccess.remove(connection, new DBCriteria());
        	StagedAssetDataAccess.remove(connection, new DBCriteria());
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RemoteException("loadMasterAssetFromStaged, " + e.getClass().getName());
        }
    }
    public void loadMasterItemFromStaged(ICleanwiseUser user)  throws RemoteException {
        loadMasterItem("clw_staged_item", false, user);
    	Connection connection = null;
        try
        {
        	connection = getConnection();
        	StagedItemAssocDataAccess.remove(connection, new DBCriteria());
        	StagedItemDataAccess.remove(connection, new DBCriteria());
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RemoteException("loadMasterItemFromStaged, " + e.getClass().getName());
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
    }
    
	public void loadMasterItem(int staged_itemId, int itemId, ICleanwiseUser appUser)  throws RemoteException {
//      String userName = user.getUser().getUserName();
        String userName = "MasterItemLoader";

    	Connection connection = null;
        try {
        	MasterLoader masterItemLoader = 
        		new MasterItemLoader(staged_itemId, itemId, userName);
        	connection = getConnection();
			masterItemLoader.load(connection);
        }
        catch(Exception e)
        {
        	throw new RemoteException("loadMasterItem: " + e.getMessage());
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
		
	}

	public void loadMasterItem(int staged_itemId, ICleanwiseUser appUser)  throws RemoteException {
//      String userName = user.getUser().getUserName();
        String userName = "MasterItemLoader";

    	Connection connection = null;
        try {
        	MasterLoader masterAssetLoader = 
        		new MasterItemLoader(staged_itemId, userName);
        	connection = getConnection();
			masterAssetLoader.load(connection);
        }
        catch(Exception e)
        {
        	throw new RemoteException("loadMasterItem: " + e.getMessage());
        }
        finally {
        	try {if (connection != null) connection.close();} catch (Exception ex) {}
        }
		
	}
    public void loadMasterItem(String loadingTableName, boolean stageUnmatched, ICleanwiseUser user)  throws RemoteException {
//      String userName = user.getUser().getUserName();
      String userName = "MasterItemLoader";
  	Connection connection = null;
      try {
      	MasterLoader masterItemLoader = 
      		new MasterItemLoader(loadingTableName, stageUnmatched, userName);
      	connection = getConnection();
			masterItemLoader.load(connection);
      }
      catch(Exception e)
      {
      	throw new RemoteException("loadMasterItem: " + e.getMessage());
      }
      finally {
      	try {if (connection != null) connection.close();} catch (Exception ex) {}
      }
      
  }
  
  public List checkForChildDuplItems(int pParentStoreId)
  throws RemoteException {
	return checkForChildDuplItems(pParentStoreId, 0, null, null);
  }
  
  public List checkForChildDuplItems(int pParentStoreId, int pParentManufId, String pManufSku, String pUom)
  throws RemoteException 
  {
	Connection conn = null;
    try {
		conn = getConnection();
		List duplLL = new LinkedList();
		String sql = 
           "SELECT st.short_desc STORE, manuf.short_desc manuf,  \n\r"+
           " manuf_sku, uom, sku_nums, main_manuf_id  \n\r"+
           " FROM ( \n\r"+
           " SELECT bb.main_manuf_id, store_id, im.item_num manuf_sku, uom.clw_value uom, \n\r"+
           " wm_concat(i.sku_Num) sku_nums   \n\r"+
           " FROM ( \n\r"+
           " SELECT aa.main_store_id, aa.main_manuf_id,  \n\r"+
           " pch.bus_entity_id store_id, chm.bus_entity_id manuf_id \n\r"+
           " FROM ( \n\r"+
           " SELECT p.property_id, bea.bus_entity2_id main_store_id,  \n\r"+
		   "       be.bus_entity_id main_manuf_id,     \n\r"+
           " REPLACE(Chr(10)|| \n\r"+
           " Lower(be.short_desc||Chr(10)|| \n\r"+
           " Translate( p.clw_value, Chr(10)||Chr(13)||',;',  \n\r"+
           "  Chr(10)||Chr(10)||Chr(10)||Chr(10))),' ','')||Chr(10) manuf_list \n\r"+
           " FROM clw_bus_entity be, clw_bus_entity_assoc bea, clw_property p \n\r"+
           " WHERE 1=1 \n\r"+
           " AND bea.bus_entity2_id = ? \n\r"+
           " AND bea.BUS_ENTITY_ASSOC_CD = 'MANUFACTURER OF STORE' \n\r"+
           " AND bea.bus_entity1_id = be.bus_entity_id \n\r"+
           " AND be.bus_entity_id = p.bus_entity_id (+) \n\r"+
           " AND p.SHORT_DESC(+) = 'OTHER_NAMES' \n\r"+
           " AND p.PROPERTY_TYPE_CD(+) = 'OTHER_NAMES' \n\r"+
           " ) aa, \n\r"+
           " clw_property pch, clw_bus_entity chm, clw_bus_entity_assoc chbea \n\r"+
           " WHERE 1=1 \n\r"+
           " AND pch.clw_value = To_Char(aa.main_store_id) \n\r"+
           " AND chm.bus_entity_type_cd = 'MANUFACTURER'    \n\r"+
           " AND InStr(aa.manuf_list, \n\r"+
           "      Chr(10)||Lower(REPLACE(chm.short_desc,' ',''))||Chr(10) \n\r"+
           "     ) > 0 \n\r"+
           " AND chbea.bus_entity1_id = chm.bus_entity_id \n\r"+
           " AND chbea.bus_entity2_id = pch.bus_entity_id \n\r"+
           " ) bb, clw_item_mapping im, clw_item_meta uom, clw_item i \n\r"+
           " WHERE 1=1  \n\r"+
           "   AND im.bus_entity_id = manuf_id \n\r"+
           "   AND im.ITEM_MAPPING_CD = 'ITEM_MANUFACTURER' \n\r"+
           "   AND uom.item_id = im.item_id \n\r"+
           "   AND uom.name_value = 'UOM' \n\r"+
           "   AND i.item_id = im.item_id \n\r"+
  //---
		   ((pParentManufId>0)? ("AND main_manuf_id = ? \n\r"):"")+
           ((Utility.isSet(pUom))? ("AND uom.clw_value = ? \n\r"):"")+
           ((Utility.isSet(pManufSku))? ("AND im.item_num = ? \n\r"):"")+
  //--
           " GROUP BY bb.main_manuf_id, store_id, im.item_num, uom.clw_value \n\r"+
           " HAVING Count(*) > 1 \n\r"+
           " ) cc, clw_bus_entity manuf, clw_bus_entity st \n\r"+
           " WHERE cc.store_id = st.bus_entity_id \n\r"+
           "   AND cc.main_manuf_id = manuf.bus_entity_id \n\r";
           
           PreparedStatement stmt = conn.prepareStatement(sql);
           int i=1;
           stmt.setInt(i++, pParentStoreId);
           if ((pParentManufId>0)) {
               stmt.setInt(i++, pParentManufId);
           }
           if (Utility.isSet(pUom)) {
              stmt.setString(i++, pUom);
           }
           if (Utility.isSet(pManufSku)) {
               stmt.setString(i++, pManufSku);
           }
           ResultSet rs=stmt.executeQuery();
           while(rs.next()){
		      HashMap hm = new HashMap();
			  duplLL.add(hm);
			  String store = rs.getString("STORE");
			  String parentManuf = rs.getString("manuf");
		      String manufSku = rs.getString("manuf_sku");
			  String uom = rs.getString("uom");
			  String skuList = rs.getString("sku_nums");
			  hm.put("Child Store",store);
			  hm.put("Parent Manuf", parentManuf);
			  hm.put("Manuf Sku", manufSku);
			  hm.put("UOM", uom);
			  hm.put("Sku Numbers",skuList);			  
           }
		   rs.close();
		   stmt.close();
		   return duplLL;
 
        }
        catch(Exception e)
        {
			e.printStackTrace();
        	throw new RemoteException(e.getMessage(), e);
        }
        finally {
        	try {if (conn != null) conn.close();} catch (Exception ex) {}
        }
	}


    public String getStoreType(int pStoreId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            return BusEntityDAO.getStoreTypeCd(con, pStoreId);
        } catch (DataNotFoundException exc) {
            return null;
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
    }

    public StoreProfileDataVector getStoreProfile(int pStoreId) throws RemoteException {
    	
    	StoreProfileDataVector profileDV = new StoreProfileDataVector();
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(StoreProfileDataAccess.STORE_ID, pStoreId);
            
            profileDV = StoreProfileDataAccess.select(con, crit);
            
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
        
        return profileDV;
    }
    
    public StoreProfileData getStoreProfile(int pStoreId, String pShortDesc) throws RemoteException{
    	
    	Connection con = null;
    	try{
    		con = getConnection();
    		DBCriteria crit = new DBCriteria();
    		crit.addEqualTo(StoreProfileDataAccess.STORE_ID, pStoreId);
    		crit.addEqualTo(StoreProfileDataAccess.SHORT_DESC, pShortDesc);
    		
    		StoreProfileDataVector spDV = StoreProfileDataAccess.select(con, crit);
    		if(spDV != null && spDV.size()>0){
    			return (StoreProfileData)spDV.get(0);
    		}
    		
    	} catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
        
        return null;
    }
    
    public void updateStoreProfile(String pUser,int pStoreId, StoreProfileDto pStoreProfile,
    		List allLanguages) throws RemoteException {
    	
    	
    	Connection con = null;
        try {
            con = getConnection();
            StoreProfileData spd;
            
            //Profile Name
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.PROFILE_NAME);
            
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isProfileNameDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isProfileNameEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.PROFILE_NAME);
                spd.setDisplay(String.valueOf(pStoreProfile.isProfileNameDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isProfileNameEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
      	  	
            //Language
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.LANGUAGE);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isLanguageDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isLanguageEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.LANGUAGE);
                spd.setDisplay(String.valueOf(pStoreProfile.isLanguageDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isLanguageEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Country
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.COUNTRY);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isCountryDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isCountryEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.COUNTRY);
                spd.setDisplay(String.valueOf(pStoreProfile.isCountryDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isCountryEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }          
            
            //Addr
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.CONTACT_ADDRESS);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isContactAddressDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isContactAddressEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.CONTACT_ADDRESS);
                spd.setDisplay(String.valueOf(pStoreProfile.isContactAddressDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isContactAddressEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Phone
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.PHONE);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isPhoneDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isPhoneEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.PHONE);
                spd.setDisplay(String.valueOf(pStoreProfile.isPhoneDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isPhoneEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Mobile
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.MOBILE);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isMobileDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isMobileEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.MOBILE);
                spd.setDisplay(String.valueOf(pStoreProfile.isMobileDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isMobileEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Fax
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.FAX);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isFaxDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isFaxEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.FAX);
                spd.setDisplay(String.valueOf(pStoreProfile.isFaxDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isFaxEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Email
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.EMAIL);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isEmailDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isEmailEdit()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.EMAIL);
                spd.setDisplay(String.valueOf(pStoreProfile.isEmailDisplay()));
          	  	spd.setEdit(String.valueOf(pStoreProfile.isEmailEdit()));
          	  	spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Change Password
            spd = getStoreProfile(pStoreId,RefCodeNames.STORE_PROFILE_FIELD.CHANGE_PASSWORD);
            if(spd!=null){        	
            	spd.setDisplay(String.valueOf(pStoreProfile.isChangePassword()));
          	  	spd.setModBy(pUser);
        	  	spd.setModDate(new Date());
        	  	StoreProfileDataAccess.update(con, spd);
        	  	
            }else{
            	spd = new StoreProfileData();
            	spd.setStoreId(pStoreId);
                spd.setShortDesc(RefCodeNames.STORE_PROFILE_FIELD.CHANGE_PASSWORD);
                spd.setDisplay(String.valueOf(pStoreProfile.isChangePassword()));
                spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.FIELD_OPTION);
          	  	spd.setModBy(pUser);
          	  	spd.setModDate(new Date());
          	  	StoreProfileDataAccess.insert(con, spd);
          	  	
            }
            
            //Language list
            
            String[] storeLanguages = pStoreProfile.getStoreLanguages();
            boolean languageSelected = false;
            for(int ii=0; ii<storeLanguages.length; ii++){
            	String lang = (String)storeLanguages[ii];
            	languageSelected = languageSelected || (lang != null && lang.trim().length() > 0);
            	spd = getStoreProfile(pStoreId, lang);
            	
            	if(spd!=null){
            		spd.setDisplay(String.valueOf(true));
            		spd.setModBy(pUser);
            	  	spd.setModDate(new Date());
            	  	StoreProfileDataAccess.update(con, spd);
            	  	
            	}else{
            		spd = new StoreProfileData();
                	spd.setStoreId(pStoreId);
                    spd.setShortDesc(lang);
                    spd.setDisplay(String.valueOf(true));
                    spd.setOptionTypeCd(RefCodeNames.STORE_PROFILE_TYPE_CD.LANGUAGE_OPTION);
              	  	spd.setModBy(pUser);
              	  	spd.setModDate(new Date());
              	  	StoreProfileDataAccess.insert(con, spd);
            	}
            	
            	allLanguages.remove(lang);
            }
            
            if(allLanguages!=null && allLanguages.size()>0){
            	DBCriteria crit = new DBCriteria();
            	crit.addEqualTo(StoreProfileDataAccess.STORE_ID, pStoreId);
            	crit.addOneOf(StoreProfileDataAccess.SHORT_DESC, allLanguages);
            	StoreProfileDataAccess.remove(con, crit);
            }
            
            //STJ-5778
            //if a language was selected, remove any rows that exist that indicate a value of "none"
            if (languageSelected) {
            	DBCriteria crit = new DBCriteria();
            	crit.addEqualTo(StoreProfileDataAccess.STORE_ID, pStoreId);
            	crit.addEqualTo(StoreProfileDataAccess.OPTION_TYPE_CD, RefCodeNames.STORE_PROFILE_TYPE_CD.LANGUAGE_OPTION);
            	crit.addIsNull(StoreProfileDataAccess.SHORT_DESC);
            	StoreProfileDataAccess.remove(con, crit);
            }
            
        } catch (Exception exc) {
            throw processException(exc);
        } finally {
            closeConnection(con);
        }
 
    }
   
}



