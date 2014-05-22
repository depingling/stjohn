package com.cleanwise.service.api.session;

import java.math.BigDecimal;
import javax.ejb.*;
import javax.naming.NamingException;
import java.sql.*;
import java.rmi.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;

import org.apache.log4j.Logger;


/**
 *  <code>Distributor</code> stateless session bean.
 *
 *@author     <a href="mailto:tbesser@cleanwise.com"</a>
 *@created    August 28, 2001
 */
public class DistributorBean extends BusEntityServicesAPI {
    private static final Logger log = Logger.getLogger(DistributorBean.class);
    private static final BigDecimal ONE = new BigDecimal(1);
    private static String className = "DistributorBean";

    /**
     *  Creates a new <code>DistributorBean</code> instance.
     */
    public DistributorBean() { }

    /**
     *  Gets BusEntityData object for the distributor
     *
     *@param  pDistributorId             an <code>int</code> value
     *@return                            a <code>BusEntityData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public BusEntityData getDistributorBusEntity(int pDistributorId)
             throws RemoteException, DataNotFoundException {

        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityData distBeD = BusEntityDataAccess.select(conn, pDistributorId);
            return distBeD;
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }

    /**
     *  Describe <code>getDistributor</code> method here.
     *
     *@param  pDistributorId             an <code>int</code> value
     *@return                            a <code>DistributorData</code> value
     *@exception  RemoteException        if an error occurs
     *@exception  DataNotFoundException  if an error occurs
     */
    public DistributorData getDistributor(int pDistributorId)
             throws RemoteException, DataNotFoundException {

        DistributorData distributor = null;

        Connection conn = null;
        try {
            conn = getConnection();
            distributor = getDistributor(pDistributorId,conn);
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return distributor;
    }

    /**
     * Gets the distributor information values to be used by the request.
     * @param pDistributorId an <code>int</code> value
     * @param pStoreIds an <code>IdVector</code> value
     * @param pInactiveFl an <code>boolean</code> value
     * @return DistributorData
     * @exception RemoteException Required by EJB 1.0
     * DataNotFoundException if distributor with pDistributorId
     * doesn't exist
     * @exception DataNotFoundException if an error occurs
     */
    public DistributorData getDistributorForStore(int pDistributorId, IdVector pStoreIds, boolean pInactiveFl)
            throws RemoteException, DataNotFoundException {

        if (pStoreIds!=null&& pStoreIds.size()>0&& pDistributorId > 0) {
            BusEntitySearchCriteria pCrit = new BusEntitySearchCriteria();
            IdVector distributorIds = new IdVector();
            distributorIds.add(new Integer(pDistributorId));
            pCrit.setStoreBusEntityIds(pStoreIds);
            pCrit.setDistributorBusEntityIds(distributorIds);
            pCrit.setSearchForInactive(pInactiveFl);
            DistributorDataVector distributorDV = getDistributorByCriteria(pCrit);
            if (distributorDV != null && distributorDV.size() > 0) {
                if (distributorDV.size() == 1) {
                    return (DistributorData) distributorDV.get(0);
                } else {
                    throw new RemoteException("Multiple distributors for store id : " + pStoreIds);
                }
            }
        }
        throw new DataNotFoundException("Disributor is not found");
    }


    DistributorData getDistributor(int pDistributorId,Connection conn)
    throws Exception{
        BusEntityData busEntity =
        BusEntityDataAccess.select(conn, pDistributorId);
        if (busEntity.getBusEntityTypeCd().compareTo(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR) != 0) {
            throw new DataNotFoundException("Bus Entity not distributor");
        }
        return getDistributorDetails(busEntity);
    }

    /**
     * Get all distributor ids for a given store
     */
    public IdVector getDistributorIdsForStore(int pStoreId)
        throws RemoteException,DataNotFoundException {
      return getDistributorIdsForStore( pStoreId, null);
    }

    public IdVector getDistributorIdsForStore(int pStoreId, String pDistrStatus)
    throws RemoteException,DataNotFoundException {

    	IdVector distributorIds = new IdVector();
    	if (pStoreId > 0) {
    		Connection conn = null;
            try {
                conn = getConnection();

    		DBCriteria crit = new DBCriteria();
    		crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pStoreId);
    		crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
    		//distributorIds.add(BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit));
                if (Utility.isSet(pDistrStatus) ){
                    DBCriteria dbc = new DBCriteria();
                    dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, pDistrStatus);
                    String subSql = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc);
                    crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, subSql);
                }
    		distributorIds = BusEntityAssocDataAccess.selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);

    		//return distributorIds;

            } catch (Exception e) {
                throw processException(e);
            } finally {
                closeConnection(conn);
            }
    	}
    	return distributorIds;

    }

    /**
     *  Get all distributors that match the given name. The arguments specify
     *  whether the name is interpreted as a pattern or exact match.
     *
     *@param  pName                a <code>String</code> value with distributor
     *      name or pattern
     *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH,
     *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>DistributorDataVector</code> of
     *      matching distributors
     *@exception  RemoteException  if an error occurs
     */
    public DistributorDataVector getDistributorByName(String pName,
            int pMatch,
            int pOrder)
             throws RemoteException {

        DistributorDataVector distributorVec = new DistributorDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            switch (pMatch) {
                case Distributor.EXACT_MATCH:
                    crit.addEqualTo(BusEntityDataAccess.SHORT_DESC, pName);
                    break;
                case Distributor.EXACT_MATCH_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                            pName);
                    break;
                case Distributor.BEGINS_WITH:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC, pName + "%");
                    break;
                case Distributor.BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                            pName + "%");
                    break;
                case Distributor.CONTAINS:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC, "%" + pName + "%");
                    break;
                case Distributor.CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                            "%" + pName + "%");
                    break;
                default:
                    throw new RemoteException("getDistributorByName: Bad match specification");
            }
            switch (pOrder) {
                case Distributor.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
                    break;
                case Distributor.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
                    break;
                default:
                    throw new RemoteException("getDistributorByName: Bad order specification");
            }
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            BusEntityDataVector busEntityVec =
                    BusEntityDataAccess.select(conn, crit);

            Iterator iter = busEntityVec.iterator();
            while (iter.hasNext()) {
                distributorVec.add(getDistributorDetails((BusEntityData) iter.next()));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return distributorVec;
    }
   //******************************************************************************
    /**
     *Gets a list of DistributorData objects based off the supplied search criteria object
     *@param BusEntitySearchCriteria the search criteria
     *@return                      a set of DistributorData objects
     *@exception  RemoteException  if an error occurs
     */
    public DistributorDataVector getDistributorByCriteria(BusEntitySearchCriteria pCrit)
             throws RemoteException {
        DistributorDataVector distributorVec = new DistributorDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDataVector busEntityVec =
                BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            Iterator iter = busEntityVec.iterator();
            while (iter.hasNext()) {
                distributorVec.add(getDistributorDetails((BusEntityData) iter.next()));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return distributorVec;
    }


    /**
     *Gets a list of BusEntityDataVector objects based off the supplied search criteria object
     *@param BusEntitySearchCriteria the search criteria
     *@return                      a set of DistributorData objects
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getDistributrBusEntitiesByCriteria(BusEntitySearchCriteria pCrit)throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }

//******************************************************************************

    /**
     *  Get all the distributors.
     *
     *@param  pOrder               one of ORDER_BY_ID, ORDER_BY_NAME
     *@return                      a <code>DistributorDataVector</code> with all
     *      distributors.
     *@exception  RemoteException  if an error occurs
     */
    public DistributorDataVector getAllDistributors(int pOrder)
             throws RemoteException {

        DistributorDataVector distributorVec = new DistributorDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            switch (pOrder) {
                case Distributor.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);
                    break;
                case Distributor.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);
                    break;
                default:
                    throw new RemoteException("getAllDistributors: Bad order specification");
            }
            BusEntityDataVector busEntityVec =
                    BusEntityDataAccess.select(conn, crit);

            Iterator iter = busEntityVec.iterator();
            while (iter.hasNext()) {
                distributorVec.add(getDistributorDetails((BusEntityData) iter.next()));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return distributorVec;
    }



    /**
     *  Describe <code>getDistributorByErpNum</code> method here.
     *
     *@param  pErpNum              an <code>int</code> value
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public DistributorData getDistributorByErpNum(String pErpNum)
             throws RemoteException {

        DistributorData distributor = null;

        Connection conn = null;
        try {
            conn = getConnection();
            distributor = getDistributorByErpNum(pErpNum,conn);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return distributor;
    }


    DistributorData getDistributorByErpNum(String pErpNum,Connection conn) throws Exception{
        if (pErpNum == null || pErpNum.length() == 0) {
            throw new RemoteException("getDistributorByErpNum: " +
                    "pErpNum is missing (" + pErpNum + ")");
        }
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.ERP_NUM, pErpNum);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
        BusEntityDataVector busEntityV = new BusEntityDataVector();
        busEntityV =
                BusEntityDataAccess.select(conn, crit);
        if (null != busEntityV && 0 < busEntityV.size()) {
            BusEntityData busEntity = (BusEntityData) busEntityV.get(0);
            return getDistributorDetails(busEntity);
        }
        return null;
    }

    /**
     *  Describe <code>getDistributorCollectionByIdList</code> method here.
     *
     *@param  pBusEntityIdList     Description of the Parameter
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public DistributorDataVector getDistributorCollectionByIdList(IdVector pBusEntityIdList)
             throws RemoteException {

        DistributorDataVector distributorV = new DistributorDataVector();

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, pBusEntityIdList);
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            BusEntityDataVector busEntityV = new BusEntityDataVector();
            busEntityV =
                    BusEntityDataAccess.select(conn, crit);
            if (null != busEntityV && 0 < busEntityV.size()) {
                for (int i = 0; i < busEntityV.size(); i++) {
                    BusEntityData busEntity = (BusEntityData) busEntityV.get(i);
                    DistributorData distributor = getDistributorDetails(busEntity);
                    distributorV.add(distributor);
                }
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return distributorV;
    }



    /**
     *  Describe <code>ejbCreate</code> method here.
     *
     *@exception  CreateException  if an error occurs
     *@exception  RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }


    /**
     *  Describe <code>addDistributor</code> method here.
     *
     *@param  pDistributorData     a <code>DistributorData</code> value
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public DistributorData addDistributor(DistributorData pDistributorData)
             throws RemoteException {
        return updateDistributor(pDistributorData);
    }


    /**
     *  Updates the distributor information values to be used by the request.
     *
     *@param  pDistributorData  the DistributorData distributor data.
     *@return                   a <code>DistributorData</code> value
     *@throws  RemoteException  Required by EJB 1.0
     */
    public DistributorData updateDistributor(DistributorData pDistributorData)
             throws RemoteException {
        Connection conn = null;

        try {
            conn = getConnection();

            BusEntityData busEntity = pDistributorData.getBusEntity();
            if (busEntity.isDirty()) {
                if (busEntity.getBusEntityId() == 0) {
                    busEntity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                    BusEntityDataAccess.insert(conn, busEntity);
                } else {
                    BusEntityDataAccess.update(conn, busEntity);
                }
            }
            int distributorId = pDistributorData.getBusEntity().getBusEntityId();

            int storeId = pDistributorData.getStoreId();

            saveBusEntAssociation(true, distributorId,storeId,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE,conn);

            saveStoreCatalogAssoc(conn, distributorId, storeId,
                    pDistributorData.getBusEntity().getModBy());

            EmailData primaryEmail = pDistributorData.getPrimaryEmail();
            if (primaryEmail.isDirty()) {
                if (primaryEmail.getEmailId() == 0) {
                    primaryEmail.setBusEntityId(distributorId);
                    primaryEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
                    primaryEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                    primaryEmail.setPrimaryInd(true);
                    EmailDataAccess.insert(conn, primaryEmail);
                } else {
                    EmailDataAccess.update(conn, primaryEmail);
                }
            }
            EmailData rejectedInvEmail = pDistributorData.getRejectedInvEmail();
            if (rejectedInvEmail != null) {
               if (rejectedInvEmail.isDirty()) {
                   if (rejectedInvEmail.getEmailId() == 0) {
                       rejectedInvEmail.setBusEntityId(distributorId);
                       rejectedInvEmail.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.REJECTED_INVOICE);
                       rejectedInvEmail.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                       EmailDataAccess.insert(conn, rejectedInvEmail);
                   } else {
                       EmailDataAccess.update(conn, rejectedInvEmail);
                   }
               }
            }
            PhoneData primaryPhone = pDistributorData.getPrimaryPhone();
            if (primaryPhone.isDirty()) {
                if (primaryPhone.getPhoneId() == 0) {
                    primaryPhone.setBusEntityId(distributorId);
                    primaryPhone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    primaryPhone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
                    primaryPhone.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, primaryPhone);
                } else {
                    PhoneDataAccess.update(conn, primaryPhone);
                }
            }

            PhoneData primaryFax = pDistributorData.getPrimaryFax();
            if (primaryFax.isDirty()) {
                if (primaryFax.getPhoneId() == 0) {
                    primaryFax.setBusEntityId(distributorId);
                    primaryFax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    primaryFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
                    primaryFax.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, primaryFax);
                } else {
                    PhoneDataAccess.update(conn, primaryFax);
                }
            }

            PhoneData poFax = pDistributorData.getPoFax();
            if (poFax.isDirty()) {
                if (poFax.getPhoneId() == 0) {
                    poFax.setBusEntityId(distributorId);
                    poFax.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    poFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.POFAX);
                    poFax.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, poFax);
                } else {
                    PhoneDataAccess.update(conn, poFax);
                }
            }

            AddressData primaryAddress = pDistributorData.getPrimaryAddress();
            if (primaryAddress.isDirty()) {
                if (primaryAddress.getAddressId() == 0) {
                    primaryAddress.setBusEntityId(distributorId);
                    primaryAddress.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                    primaryAddress.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
                    primaryAddress.setPrimaryInd(true);
                    AddressDataAccess.insert(conn, primaryAddress);
                } else {
                    AddressDataAccess.update(conn, primaryAddress);
                }
            }

            AddressData billingAddress = pDistributorData.getBillingAddress();
            if (billingAddress!=null && billingAddress.isDirty()) {
               String address1 = billingAddress.getAddress1();
               if(address1==null) address1="";
                if (billingAddress.getAddressId() == 0 && address1.trim().length()>0) {
                  billingAddress.setBusEntityId(distributorId);
                  billingAddress.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                  billingAddress.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.BILLING);
                  billingAddress.setPrimaryInd(false);
                  AddressDataAccess.insert(conn, billingAddress);
                } else if (billingAddress.getAddressId() > 0 && address1.trim().length()==0) {
                  AddressDataAccess.remove(conn, billingAddress.getAddressId());
                  pDistributorData.setBillingAddress(AddressData.createValue());
                } else if (billingAddress.getAddressId()==0 && address1.trim().length()==0) {
                  pDistributorData.setBillingAddress(AddressData.createValue());
                } else {
                  AddressDataAccess.update(conn, billingAddress);
                }
            }

            PropertyData prop = pDistributorData.getDistributorTypeProp();
            if (prop.isDirty()) {
                if (prop.getPropertyId() == 0) {
                    prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
                    if (prop.getPropertyStatusCd().compareTo("") == 0) {
                        prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                        prop.setShortDesc(RefCodeNames.DISTRIBUTOR_TYPE_LABEL);
                    }
                    prop.setBusEntityId(distributorId);
                    PropertyDataAccess.insert(conn, prop);
                } else {
                    PropertyDataAccess.update(conn, prop);
                }
            }

            PropertyUtil propUtil = new PropertyUtil(conn);
            int distId = pDistributorData.getBusEntity().getBusEntityId();
      propUtil.saveCollection(pDistributorData.getPropertyCollection());


        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return pDistributorData;
    }

    public void saveStoreCatalogAssoc(Connection pConn, int pDistId, int pStoreId, String user)
     throws Exception
     {
        CatalogData storeCatalog = getStoreCatalog(pConn,pStoreId);
        int storeCatalogId = storeCatalog.getCatalogId();

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                RefCodeNames.CATALOG_TYPE_CD.STORE);
        String storeCatalogReq =
           CatalogDataAccess.getSqlSelectIdOnly(CatalogDataAccess.CATALOG_ID, crit);

        crit = new DBCriteria();
        crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,pDistId);
        crit.addOneOf(CatalogAssocDataAccess.CATALOG_ID,storeCatalogReq);
        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                      RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);

        CatalogAssocDataVector assocV = CatalogAssocDataAccess.select(pConn,crit);
        boolean flag = false;
        for(Iterator iter=assocV.iterator(); iter.hasNext();) {
          CatalogAssocData caD = (CatalogAssocData) iter.next();
          int catId = caD.getCatalogId();
          if(!flag) {
            if(catId!=storeCatalogId) {
              caD.setCatalogId(storeCatalogId);
              caD.setModBy(user);
              CatalogAssocDataAccess.update(pConn,caD);
              flag = true;
            }
          } else {
            CatalogAssocDataAccess.remove(pConn,caD.getCatalogAssocId());
          }
        }
        if(!flag) {
          CatalogAssocData caD = CatalogAssocData.createValue();
          caD.setCatalogId(storeCatalogId);
          caD.setBusEntityId(pDistId);
          caD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
          caD.setAddBy(user);
          caD.setModBy(user);
          CatalogAssocDataAccess.insert(pConn,caD);
        }
     }

  private CatalogData getStoreCatalog(Connection pConn, int pStoreId)
  throws Exception {
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,pStoreId);
    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                        RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

    String storeCatalogReq = CatalogAssocDataAccess.getSqlSelectIdOnly(
            CatalogAssocDataAccess.CATALOG_ID,dbc);

    dbc = new DBCriteria();
    dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatalogReq);
    dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
       RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
    dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
       RefCodeNames.CATALOG_TYPE_CD.STORE);
    CatalogDataVector catalogDV = CatalogDataAccess.select(pConn,dbc);

    if(catalogDV.size()==0) {
      String errorMess = "No active store catalog found. Store id: "+pStoreId;
      throw new Exception (errorMess);
    }
    if(catalogDV.size()>1) {
      String errorMess = "Multiple active store catalogs found. Store id: "+pStoreId;
      throw new Exception (errorMess);
    }
    CatalogData catalogD = (CatalogData) catalogDV.get(0);
    return catalogD;
  }

    /**
     *  <code>removeDistributor</code> may be used to remove an 'unused'
     *  distributor. An unused distributor is a distributor with no database
     *  references other than the default primary address, phone numbers, email
     *  addresses and properties. Attempting to remove a distributor that is
     *  used will result in a failure initially reported as a SQLException and
     *  consequently caught and rethrown as a RemoteException.
     *
     *@param  pDistributorData     a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void removeDistributor(DistributorData pDistributorData)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();

            int distributorId = pDistributorData.getBusEntity().getBusEntityId();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, distributorId);
            DBCriteria aCrit = new DBCriteria();
            aCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, distributorId);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
               RefCodeNames.CATALOG_TYPE_CD.STORE);
            IdVector storeCatalogIdV =
                CatalogDataAccess.selectIdOnly(conn,CatalogDataAccess.CATALOG_ID, dbc);

            DBCriteria catalogCrit = new DBCriteria();
            catalogCrit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, distributorId);
            catalogCrit.addNotOneOf(CatalogAssocDataAccess.CATALOG_ID,storeCatalogIdV);
            IdVector catalogAssocIdV =
               CatalogAssocDataAccess.selectIdOnly(conn,
                              CatalogAssocDataAccess.CATALOG_ID,catalogCrit);
            if(catalogAssocIdV.size()>0) {
              throw new Exception("Distributor assigned to cataogs. Can't delete");
            }


            PropertyDataAccess.remove(conn, crit);
            PhoneDataAccess.remove(conn, crit);
            EmailDataAccess.remove(conn, crit);
            AddressDataAccess.remove(conn, crit);
            CatalogAssocDataAccess.remove(conn,crit);
            BusEntityAssocDataAccess.remove(conn, aCrit);
            BusEntityDataAccess.remove(conn, distributorId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Method for conversion from BusEntityDataVector to DistributorDataVector
     * @param BusEntityDataVector - collection of BusEntitys
     * @return DistributorDataVector
     * @throws RemoteException
     */
    public DistributorDataVector getDistributorsByBusEntityCollection(BusEntityDataVector busEntitys) throws RemoteException {

        DistributorDataVector distributorDataVector = new DistributorDataVector();

        for (int i = 0; i < busEntitys.size(); i++) {
            BusEntityData busEntity = (BusEntityData) busEntitys.get(i);
            DistributorData distributor = getDistributorDetails(busEntity);
            distributorDataVector.add(distributor);
        }

        return distributorDataVector;
    }

    /**
     *  Describe <code>getDistributorDetails</code> method here.
     *
     *@param  pBusEntity           a <code>BusEntityData</code> value
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    private DistributorData getDistributorDetails(BusEntityData pBusEntity)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getDistributorDetails(conn,pBusEntity);
        } catch (Exception e) {
            throw new RemoteException("getDistributorDetails: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     *Populates a DistributorData object bsed off the passed in busEntityData object.  the passed in object must have
     *a valid id for retrieving the relational data.
     *@throws SQLException, RemoteException
     */
    protected DistributorData getDistributorDetails(Connection conn, BusEntityData pBusEntity)throws SQLException, RemoteException{
            AddressData primaryAddress = null;
            PhoneData primaryPhone = null;
            PhoneData primaryFax = null;
            PhoneData poFax = null;
            EmailData primaryEmail = null;
            PropertyData distTypeProp = null;
            AddressData billingAddress = null;
            int storeId = 0;
            PropertyDataVector lPropv = null;
            EmailData rejectedInvEmail = null;
            // get distributor address
            DBCriteria addressCrit = new DBCriteria();
            addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                    pBusEntity.getBusEntityId());
            addressCrit.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
            AddressDataVector addressVec =
                    AddressDataAccess.select(conn, addressCrit);
            // if more than one primary address, for now we don't care
            if (addressVec.size() > 0) {
                primaryAddress = (AddressData) addressVec.get(0);
            }

            IdVector stores = getBusEntityAssoc2Ids(conn,pBusEntity.getBusEntityId(),
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
            if(stores.size() == 1){
                storeId = ((Integer)stores.get(0)).intValue();
            }else if(stores.size() > 1){
                throw new RemoteException("Multiple store association for distributor id "+pBusEntity.getBusEntityId());
            }

            // get billing address
            DBCriteria billAddressCrit = new DBCriteria();
            billAddressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                    pBusEntity.getBusEntityId());
            billAddressCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                                          RefCodeNames.ADDRESS_TYPE_CD.BILLING);
            AddressDataVector billingAddressVec =
                    AddressDataAccess.select(conn, billAddressCrit);

            // if more than one billing address,  we don't care
            if (billingAddressVec.size() > 0) {
              billingAddress = (AddressData) billingAddressVec.get(0);
            } else {
              billingAddress = AddressData.createValue();
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
                primaryEmail = (EmailData) emailVec.get(0);
            }

            // get related phones
            DBCriteria phoneCrit = new DBCriteria();
            phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID,
                    pBusEntity.getBusEntityId());
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
                } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.POFAX) == 0) {
                    poFax = phone;
                } else {
                    // ignore - unidentified phone
                }
            }

            // get the type property
            DBCriteria propCrit = new DBCriteria();
            propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                    pBusEntity.getBusEntityId());
            propCrit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
            propCrit.addEqualTo(PropertyDataAccess.SHORT_DESC,
                    RefCodeNames.DISTRIBUTOR_TYPE_LABEL);
      lPropv = PropertyDataAccess.select(conn, propCrit);
            if (lPropv.size() > 1) {
                logError("DistributorData.getDistributorDetails: multiple properties found: "
                         + lPropv.toString());
            } else if (lPropv.size() == 1) {
                distTypeProp = (PropertyData) lPropv.get(0);
            } else {
                distTypeProp = PropertyData.createValue();
            }

            //get any miscellanious properties
            propCrit = new DBCriteria();
            propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                                pBusEntity.getBusEntityId());

            lPropv = PropertyDataAccess.select(conn, propCrit);
//            this.logDebug("found: " + lPropv.size() +  " properties for distributor");

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(FreightTableDataAccess.FREIGHT_TABLE_STATUS_CD,RefCodeNames.FREIGHT_TABLE_STATUS_CD.ACTIVE);
            crit.addEqualTo(FreightTableDataAccess.DISTRIBUTOR_ID,pBusEntity.getBusEntityId());
            FreightTableDataVector fts = FreightTableDataAccess.select(conn,crit);
            FreightTableDescData freightTableDesc;
            if(!fts.isEmpty()){
              freightTableDesc = populateFreightTableDescData(conn, (FreightTableData)fts.get(0));
            }else{
              freightTableDesc = new FreightTableDescData();
            }

            DistributorData distData = new DistributorData(pBusEntity,
                primaryAddress,
                primaryPhone,
                primaryFax,
                poFax,
                primaryEmail,
                distTypeProp,
                lPropv,
                billingAddress,
                storeId,
                freightTableDesc);
            
            emailCrit = new DBCriteria();
            emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,
                    pBusEntity.getBusEntityId());
            emailCrit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,
            		RefCodeNames.EMAIL_TYPE_CD.REJECTED_INVOICE);
            EmailDataVector rejectedInvEmailVec =
                    EmailDataAccess.select(conn, emailCrit);
            if (rejectedInvEmailVec.size() > 0) {
            	rejectedInvEmail = (EmailData) rejectedInvEmailVec.get(0);
          }else{
          	rejectedInvEmail = EmailData.createValue();
          }
           
            distData.setRejectedInvEmail(rejectedInvEmail);
            

            return distData;
        }

    /**
     * Populates a FreightTableDescData based off the supplied FreightTableData.  It is assumed
     * that this object exists in the database and has a valid id.
     * @param con
     * @param freightTable
     * @return
     */
    private FreightTableDescData populateFreightTableDescData(Connection con, FreightTableData freightTable)
    throws SQLException{
      FreightTableDescData desc = new FreightTableDescData();
      desc.setFreightTable(freightTable);
      DBCriteria crit = new DBCriteria();
      crit.addIsNull(FreightTableCriteriaDataAccess.CHARGE_CD);
      crit.addEqualTo(FreightTableCriteriaDataAccess.FREIGHT_TABLE_ID, freightTable.getFreightTableId());
      FreightTableCriteriaDataVector dv = FreightTableCriteriaDataAccess.select(con,crit);
      desc.setFreightTableCriteria(dv);
      return desc;
    }


    private void addPossiblyNullCriteria(DBCriteria pCrit, String pAttrName, String pValue){
        if(pValue == null || pValue.length() == 0){
            pCrit.addIsNull(pAttrName);
        }else{
            pCrit.addEqualTo(pAttrName,pValue);
        }
    }

    /**
     *  Describe <code>addShipFromAddress</code> method here.
     *
     *@param  pShipFromAddr        an <code>AddressData</code> value
     *@return                      a <code>boolean</code> value
     *@exception  RemoteException  if an error occurs
     */
    public boolean addShipFromAddress(AddressData pShipFromAddr)
             throws RemoteException {

        Connection conn = null;
        AddressDataVector adv = null;

        try {
            conn = getConnection();
            if(!Utility.isSet(pShipFromAddr.getStateProvinceCd())){
                pShipFromAddr.setStateProvinceCd("-");
            }
            if(!Utility.isSet(pShipFromAddr.getCountryCd())){
                pShipFromAddr.setCountryCd("-");
            }
            pShipFromAddr.setAddressTypeCd
                    (RefCodeNames.ADDRESS_TYPE_CD.DIST_SHIP_FROM);
            pShipFromAddr.setAddressStatusCd
                    (RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                    pShipFromAddr.getBusEntityId());
            addPossiblyNullCriteria(dbc,AddressDataAccess.NAME1,pShipFromAddr.getName1());
            addPossiblyNullCriteria(dbc,AddressDataAccess.CITY,pShipFromAddr.getCity());
            addPossiblyNullCriteria(dbc,AddressDataAccess.COUNTRY_CD,pShipFromAddr.getCountryCd());
            addPossiblyNullCriteria(dbc,AddressDataAccess.POSTAL_CODE,pShipFromAddr.getPostalCode());

            dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                    pShipFromAddr.getAddressTypeCd());

            adv = AddressDataAccess.select(conn, dbc);
            if (adv.size() == 0) {
                AddressDataAccess.insert(conn, pShipFromAddr);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        if (null != adv && adv.size() > 0) {
            // This is a duplicate address.
            return false;
        }
        return true;
    }


    /**
     *  Describe <code>getShipFromAddressCollection</code> method here.
     *
     *@param  pDistributorId            an <code>int</code> value
     *@param  pOptionalAddressStatusCd  a <code>String</code> value
     *@return                           a <code>DistShipFromAddressViewVector</code>
     *      value
     *@exception  RemoteException       if an error occurs
     */
    public DistShipFromAddressViewVector getShipFromAddressCollection
            (int pDistributorId, String pOptionalAddressStatusCd)
             throws RemoteException {
        DistShipFromAddressViewVector addrs = new
                DistShipFromAddressViewVector();

        Connection conn = null;

        try {
            conn = getConnection();

            String selectSql = "select " +
                    "be.bus_entity_id, be.short_desc, sf.address_id, " +
                    "sf.name1, sf.address1, sf.address2, sf.address3, " +
                    " sf.city, sf.state_province_cd, " +
                    " sf.postal_code, sf.country_cd, sf.address_status_cd " +
                    "from clw_bus_entity be, clw_address sf " +
                    " where  be.bus_entity_id = sf.bus_entity_id " +
                    " and be.bus_entity_id = '" + pDistributorId + "' " +
                    " and " + AddressDataAccess.ADDRESS_TYPE_CD + " = '" +
                    RefCodeNames.ADDRESS_TYPE_CD.DIST_SHIP_FROM + "' ";

            if (null != pOptionalAddressStatusCd) {
                selectSql += " and " +
                        AddressDataAccess.ADDRESS_STATUS_CD + " = '" +
                        pOptionalAddressStatusCd + "' ";
            }

            Statement stmt = conn.createStatement();
            stmt.setMaxRows(100);
            ResultSet rs = stmt.executeQuery(selectSql);

            int rid = 0;
            while (rs.next()) {
                rid++;

                DistShipFromAddressView shipd =
                        DistShipFromAddressView.createValue();
                int col = 0;
                col++;
                shipd.setDistributorId(rs.getInt(col));

                col++;
                shipd.setDistName(rs.getString(col));
                col++;
                shipd.setShipFromAddressId(rs.getInt(col));
                col++;
                shipd.setShipFromName(rs.getString(col));
                col++;
                shipd.setShipFromAddress1(rs.getString(col));
                col++;
                shipd.setShipFromAddress2(rs.getString(col));
                col++;
                shipd.setShipFromAddress3(rs.getString(col));
                col++;
                shipd.setShipFromCity(rs.getString(col));
                col++;
                shipd.setShipFromState(rs.getString(col));
                col++;
                shipd.setShipFromPostalCode(rs.getString(col));
                col++;
                shipd.setShipFromCountryCd(rs.getString(col));
                col++;
                shipd.setShipFromStatus(rs.getString(col));

                addrs.add(shipd);
            }

            stmt.close();
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return addrs;
    }


    /**
     *  Describe <code>removeShipFrom</code> method here.
     *
     *@param  pShipFromAddrId      an <code>int</code> value
     *@exception  RemoteException  if an error occurs
     */
    public void removeShipFrom(int pShipFromAddrId)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            AddressDataAccess.remove(conn, pShipFromAddrId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }


    /**
     *  Describe <code>updateShipFromAddress</code> method here.
     *
     *@param  pShipFromAddr        an <code>AddressData</code> value
     *@return                      a <code>boolean</code> value
     *@exception  RemoteException  if an error occurs
     */
    public boolean updateShipFromAddress(AddressData pShipFromAddr)
             throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            AddressData taddr = AddressDataAccess.select
                    (conn, pShipFromAddr.getAddressId());
            taddr.setModBy(pShipFromAddr.getModBy());
            taddr.setName1(pShipFromAddr.getName1());
            taddr.setAddress1(pShipFromAddr.getAddress1());
            taddr.setAddress2(pShipFromAddr.getAddress2());
            taddr.setAddress3(pShipFromAddr.getAddress3());
            taddr.setCity(pShipFromAddr.getCity());
            taddr.setStateProvinceCd(pShipFromAddr.getStateProvinceCd());
            taddr.setPostalCode(pShipFromAddr.getPostalCode());
            taddr.setCountryCd(pShipFromAddr.getCountryCd());
            taddr.setAddressStatusCd(pShipFromAddr.getAddressStatusCd());

            AddressDataAccess.update(conn, taddr);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return true;
    }


    /**
     *  Description of the Method
     *
     *@param  pSearchType          Description of the Parameter
     *@param  pSearchVal           Description of the Parameter
     *@return                      Description of the Return Value
     *@exception  RemoteException  Description of the Exception
     */
    public DistShipFromAddressViewVector locateShipFrom
            (String pSearchType, String pSearchVal)
             throws RemoteException {
        return locateShipFrom(pSearchType, pSearchVal, null);
    }


    /**
     *  Description of the Method
     *
     *@param  pSearchType          Description of the Parameter
     *@param  pSearchVal           Description of the Parameter
     *@param  pDistId              Description of the Parameter
     *@return                      Description of the Return Value
     *@exception  RemoteException  Description of the Exception
     */
    public DistShipFromAddressViewVector locateShipFrom
            (String pSearchType, String pSearchVal, Integer pDistId)
             throws RemoteException {

        DistShipFromAddressViewVector addrs = new
                DistShipFromAddressViewVector();

        String selectSql = "select " +
                "be.bus_entity_id, be.short_desc, sf.address_id, " +
                "sf.name1, sf.address1, sf.address2, sf.address3, " +
                " sf.city, sf.state_province_cd, " +
                " sf.postal_code, sf.country_cd, sf.address_status_cd " +
                "from clw_bus_entity be, clw_address sf " +
                " where  be.bus_entity_id = sf.bus_entity_id " +
                " and " +
                AddressDataAccess.ADDRESS_TYPE_CD + " = '" +
                RefCodeNames.ADDRESS_TYPE_CD.DIST_SHIP_FROM + "' ";

        if (pDistId != null && pDistId.intValue() != 0) {
            selectSql += " and be.bus_entity_id = " + pDistId;
        }

        if (pSearchType.equals("id")) {
            String addrId = pSearchVal.trim();
            if (addrId.length() == 0) {
                String msg = "locateShipFrom, invalid id: " + pSearchVal;
                logError(msg);
                throw new RemoteException(msg);
            }
            selectSql += " and sf.address_id = '" + addrId + "' ";
        } else if (pSearchType.equals("nameBegins") || pSearchType.equals("nameContains")) {
            selectSql += " and UPPER(sf.name1) like  UPPER(?) ";
        }

        selectSql += " order by sf.name1 ";
      //  logDebug("sql: " + selectSql);
        Connection conn = null;

        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(selectSql);
            if (pSearchType.equals("nameBegins")) {
                stmt.setString(1, pSearchVal + "%");
            } else if ( pSearchType.equals("nameContains")) {
                stmt.setString(1, "%" + pSearchVal + "%");
            }
            ResultSet rs = stmt.executeQuery();

            int rid = 0;
            while (rs.next()) {
                rid++;

                DistShipFromAddressView shipd =
                        DistShipFromAddressView.createValue();
                int col = 0;
                col++;
                shipd.setDistributorId(rs.getInt(col));

                col++;
                shipd.setDistName(rs.getString(col));
                col++;
                shipd.setShipFromAddressId(rs.getInt(col));
                col++;
                shipd.setShipFromName(rs.getString(col));
                col++;
                shipd.setShipFromAddress1(rs.getString(col));
                col++;
                shipd.setShipFromAddress2(rs.getString(col));
                col++;
                shipd.setShipFromAddress3(rs.getString(col));
                col++;
                shipd.setShipFromCity(rs.getString(col));
                col++;
                shipd.setShipFromState(rs.getString(col));
                col++;
                shipd.setShipFromPostalCode(rs.getString(col));
                col++;
                shipd.setShipFromCountryCd(rs.getString(col));
                col++;
                shipd.setShipFromStatus(rs.getString(col));

                addrs.add(shipd);
            }

            stmt.close();
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return addrs;
    }


    /**
     *  Describe <code>getDistributorCounties</code> method here.
     *
     *@param  pDistId              distributor identifier
     *@param  pConditions          set of coditions. Keys: state, county,
     *      postalCode
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityTerrViewVector getDistributorCounties(int pDistId, Hashtable pConditions)
             throws RemoteException {
        BusEntityTerrViewVector counties = new BusEntityTerrViewVector();
        Connection conn = null;
        try {
            conn = getConnection();
            //Prepare postal code condition if exists
            String sql =
                    "select " +
                    "county_cd, " +
                    "state_province_cd, " +
                    "min(state_province_nam) state_province_nam, " +
                    "country_cd, " +
                    "count(pc.postal_code) post_qty,  " +
                    "count(bus_entity_id) dist_post_qty,  " +
                    "min(bet.bus_entity_terr_freight_cd) bus_entity_terr_freight_cd" +
                    " from clw_city_postal_code pc, clw_bus_entity_terr bet  " +
                    " where bet.bus_entity_id(+) =  " + pDistId +
                    "  and bet.postal_code(+) = pc.postal_code ";
            if (pConditions.containsKey("county")) {
                //String cn = (String) pConditions.get("county");
                //sql += " and upper(county_cd) like '" + cn.toUpperCase() + "%' ";
                sql += " and upper(county_cd) like upper(?) ";
            }
            if (pConditions.containsKey("state")) {
                //String cn = (String) pConditions.get("state");
                //sql += " and upper(state_province_cd) = '" + cn.toUpperCase() + "' ";
                sql += " and upper(state_province_cd) = upper(?) ";
            }
            if (pConditions.containsKey("city")) {
                //String cn = (String) pConditions.get("city");
                //sql += " and upper(city) like '" + cn.toUpperCase() + "%' ";
                sql += " and upper(city) like upper(?) ";
            }
            if (pConditions.containsKey("postalCode")) {
              String cn = (String) pConditions.get("postalCode");
              String postalSql =
                "select distinct trim(COUNTRY_CD)||trim(STATE_PROVINCE_CD)||trim(COUNTY_CD) as code "+
                " from CLW_CITY_POSTAL_CODE  where POSTAL_CODE like ? ";
               PreparedStatement postalStmt = conn.prepareStatement(postalSql);
               postalStmt.setString(1, cn + "%");
               ResultSet postalRS = postalStmt.executeQuery(postalSql);
               String sss = "'###'";
               int count =0;
               while (postalRS.next()) {
                 count ++;
                 if(count>=1000) break;
                 String code = postalRS.getString("code");
                 sss += ",'"+ code+"'";
               }
               postalRS.close();
               postalStmt.close();
                sql += " and pc.COUNTRY_CD||pc.STATE_PROVINCE_CD||pc.COUNTY_CD in "+
                   "("+sss+")";
            }
            sql += " group by county_cd, state_province_cd, country_cd";
            if (pConditions.containsKey("servicedOnly")) {
                sql += " having count(bus_entity_id)>0 ";
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            int i=1;
            if (pConditions.containsKey("county")) {
                String cn = (String) pConditions.get("county") + "%";
                stmt.setString(i++, cn);
            }
            if (pConditions.containsKey("state")) {
                String cn = (String) pConditions.get("state");
                stmt.setString(i++, cn);
            }
            if (pConditions.containsKey("city")) {
                String cn = (String) pConditions.get("city") + "%";
                stmt.setString(i++, cn);
            }

            ResultSet countyRS = stmt.executeQuery();
            while (countyRS.next()) {
                String countyVal = countyRS.getString("county_cd");
                String stateCdVal = countyRS.getString("state_province_cd");
                String stateNamVal = countyRS.getString("state_province_nam");
                String country = countyRS.getString("country_cd");
                int postQty = countyRS.getInt("post_qty");
                int distPostQty = countyRS.getInt("dist_post_qty");
                BusEntityTerrView betV = BusEntityTerrView.createValue();
                betV.setCountyCd(countyVal);
                betV.setStateProvinceCd(stateCdVal);
                betV.setStateProvinceName(stateNamVal);
                betV.setCountryCd(country);
                betV.setBusEntityId(pDistId);
                betV.setCheckedFl(distPostQty > 0 ? true : false);
                betV.setNoModifiyFl((distPostQty != postQty && distPostQty > 0) ? true : false);
                betV.setBusEntityTerrFreightCd(countyRS.getString("bus_entity_terr_freight_cd"));
                betV.setCity("");
                counties.add(betV);
            }
            stmt.close();

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return counties;
    }


    /**
     *  Describe <code>getDistributorZipCodes</code> method here.
     *
     *@param  pDistId              distributor identifier
     *@param  pConditions          set of coditions. Keys: state, county,
     *      postalCode
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityTerrViewVector getDistributorZipCodes(int pDistId, Hashtable pConditions)
             throws RemoteException {
        BusEntityTerrViewVector zipCodes = new BusEntityTerrViewVector();
        Connection conn = null;
        try {
            conn = getConnection();
            String sql =
                    "select distinct 0 as postal_code_id, " +
                    "city, " +
                    "bus_entity_id, " +
                    "pc.postal_code, " +
                    "county_cd, " +
                    "state_province_cd, " +
                    "state_province_nam, " +
                    "country_cd, " +
                    "bet.bus_entity_terr_freight_cd " +
                    " from clw_city_postal_code pc, clw_bus_entity_terr bet  " +
                    " where bet.bus_entity_id(+) =  " + pDistId +
                    "  and bet.postal_code(+) = pc.postal_code ";
            if (pConditions.containsKey("postalCode")) {
                String cn = (String) pConditions.get("postalCode");
                //sql += " and upper(pc.postal_code) like '" + cn.toUpperCase() + "%' ";
                sql += " and upper(pc.postal_code) like upper(?) ";
            }
            if (pConditions.containsKey("county")) {
                String cn = (String) pConditions.get("county");
                //sql += " and upper(pc.county_cd) like '" + cn.toUpperCase() + "%' ";
                sql += " and upper(pc.county_cd) like upper(?) ";
            }
            if (pConditions.containsKey("city")) {
                String cn = (String) pConditions.get("city");
                //sql += " and upper(pc.city) like '" + cn.toUpperCase() + "%' ";
                sql += " and upper(pc.city) like upper(?) ";
            }
            if (pConditions.containsKey("state")) {
                String cn = (String) pConditions.get("state");
                //sql += " and upper(pc.state_province_cd) = '" + cn.toUpperCase() + "' ";
                sql += " and upper(pc.state_province_cd) = upper(?) ";
            }
            if (pConditions.containsKey("servicedOnly")) {
                sql += " and bet.bus_entity_id >0 ";
            }
            sql += "  order by pc.county_cd, pc.postal_code ";

            PreparedStatement stmt = conn.prepareStatement(sql);
            int i = 1;
            if (pConditions.containsKey("postalCode")) {
                String cn = (String) pConditions.get("postalCode") + "%";
                stmt.setString(i++, cn);
            }
            if (pConditions.containsKey("county")) {
                String cn = (String) pConditions.get("county") + "%";
                stmt.setString(i++, cn);
            }
            if (pConditions.containsKey("city")) {
                String cn = (String) pConditions.get("city") + "%";
                stmt.setString(i++, cn);
            }
            if (pConditions.containsKey("state")) {
                String cn = (String) pConditions.get("state");
                stmt.setString(i++, cn);
            }

            ResultSet pcRS = stmt.executeQuery();
            while (pcRS.next()) {
                int postalCodeId = pcRS.getInt("postal_code_id");
                int busEntityId = pcRS.getInt("bus_entity_id");
                String postalCodeVal = pcRS.getString("postal_code");
                String countyVal = pcRS.getString("county_cd");
                String stateCdVal = pcRS.getString("state_province_cd");
                String stateNamVal = pcRS.getString("state_province_nam");
                String country = pcRS.getString("country_cd");
                String city = pcRS.getString("city");
                BusEntityTerrView betV = BusEntityTerrView.createValue();
                betV.setPostalCodeId(postalCodeId);
                betV.setPostalCode(postalCodeVal);
                betV.setCountyCd(countyVal);
                betV.setStateProvinceCd(stateCdVal);
                betV.setStateProvinceName(stateNamVal);
                betV.setCountryCd(country);
                betV.setBusEntityId(pDistId);
                betV.setCheckedFl(busEntityId > 0 ? true : false);
                betV.setNoModifiyFl(false);
                betV.setBusEntityTerrFreightCd(pcRS.getString("bus_entity_terr_freight_cd"));
                betV.setCity(city);
                zipCodes.add(betV);
            }
            stmt.close();

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return zipCodes;
    }

    /**
     *  Describe <code>getSubDistributorForSite(</code> method here.
     *
     *@param  pSiteId              site identifier
     *@return                      a <code>DistributorData</code> value
     *@exception  RemoteException  if an error occurs
     */

     public DistributorData getSubDistributorForSite(int pSiteId)
              throws RemoteException {
         DistributorData distr = DistributorData.createValue();
         Connection conn = null;
         try {
             conn = getConnection();
             String sql =
                 "select " +
                   "CLW_BUS_ENTITY.SHORT_DESC as SHORT_DESC, " +
                   "CITY, STATE_PROVINCE_CD, COUNTRY_CD,"+
                   "PHONE_AREA_CODE, PHONE_NUM "+
                   "from CLW_BUS_ENTITY, CLW_BUS_ENTITY_ASSOC, CLW_PHONE, CLW_ADDRESS, CLW_PROPERTY " +
                   "where CLW_PROPERTY.CLW_VALUE = '" + RefCodeNames.DISTRIBUTOR_TYPE_CD.SUB_DISTRIBUTOR + "' and " +
                   "CLW_BUS_ENTITY.BUS_ENTITY_ID= CLW_PROPERTY.BUS_ENTITY_ID and " +
                   "CLW_BUS_ENTITY.BUS_ENTITY_ID= CLW_PHONE.BUS_ENTITY_ID and " +
                          "CLW_BUS_ENTITY.BUS_ENTITY_ID= CLW_ADDRESS.BUS_ENTITY_ID and " +
                          "CLW_BUS_ENTITY.BUS_ENTITY_ID=CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID and " +
                          "CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID = " + pSiteId;

             Statement stmt = conn.createStatement();
             ResultSet pcRS = stmt.executeQuery(sql);
             while (pcRS.next()) {

                 BusEntityData busEntity = BusEntityData.createValue();
                 AddressData address = AddressData.createValue();
                 PhoneData phone = PhoneData.createValue();

                 busEntity.setShortDesc(pcRS.getString("SHORT_DESC"));
                 address.setCity(pcRS.getString("CITY"));
                 address.setCountryCd(pcRS.getString("COUNTRY_CD"));
                 address.setStateProvinceCd(pcRS.getString("STATE_PROVINCE_CD"));
                 phone.setPhoneAreaCode(pcRS.getString("PHONE_AREA_CODE"));
                 phone.setPhoneNum(pcRS.getString("PHONE_NUM"));

                 distr.setBusEntity(busEntity);
                 distr.setPrimaryAddress(address);
                 distr.setPrimaryPhone(phone);
             }
             stmt.close();

         } catch (Exception e) {
             throw processException(e);
         } finally {
             closeConnection(conn);
         }
         return distr;
    }

    /**
     *  Gets Net Supply Distributor Business Entity
     *
     *@param  pStore              store identifier
     *@return    Set of BusEntityData objects (if correct must be one object only)
     *@exception  RemoteException  if an error occurs
     */

     public BusEntityDataVector getNscStoreDistributor(int pStoreId)
              throws RemoteException {
         DistributorData distr = DistributorData.createValue();
         Connection conn = null;
         try {
             conn = getConnection();
             String sqlCond =
                "SELECT bea.bus_entity1_id FROM "+
                "clw_bus_entity_assoc bea join clw_property p " +
                " ON bea.bus_entity1_id = p.bus_entity_id "+
                " WHERE bea.bus_entity2_id = "+ pStoreId+
                " AND  p.CLW_VALUE != '"+RefCodeNames.DISTRIBUTOR_TYPE_CD.SUB_DISTRIBUTOR+"'"+
                " AND  p.SHORT_DESC = '"+RefCodeNames.DISTRIBUTOR_TYPE_LABEL+"'"+
                " AND  bea.bus_entity_assoc_cd = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE+"'";
             DBCriteria dbc = new DBCriteria();
             dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                     RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
             dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, sqlCond);

             logInfo("Distr select: "+BusEntityDataAccess.getSqlSelectIdOnly("*", dbc));
             BusEntityDataVector busEntityDV = BusEntityDataAccess.select(conn, dbc);
             return busEntityDV;

         } catch (Exception e) {
             throw processException(e);
         } finally {
             closeConnection(conn);
         }
    }

     /**
     *  Describe <code>updateDistributorCounties</code> method here.
     *
     *@param  pCounties            set of BusEntityTerrView objects
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateDistributorCounties(BusEntityTerrViewVector pCounties, String pUser)
             throws RemoteException {
       /* Connection conn = null;
        DBCriteria dbc = null;
        try {
            conn = getConnection();
            ArrayList countiesToExclude = new ArrayList();
            for (int ii = 0; ii < pCounties.size(); ii++) {
                BusEntityTerrView betV = (BusEntityTerrView) pCounties.get(ii);
                int distId = betV.getBusEntityId();
                dbc = new DBCriteria();
                dbc.addEqualTo(CityPostalCodeDataAccess.COUNTY_CD, betV.getCountyCd());
                dbc.addEqualTo(CityPostalCodeDataAccess.COUNTRY_CD, betV.getCountryCd());
                dbc.addEqualTo(CityPostalCodeDataAccess.STATE_PROVINCE_CD, betV.getStateProvinceCd());
                dbc.addOrderBy(CityPostalCodeDataAccess.POSTAL_CODE);
                String countyZipReq =
                  "select distinct POSTAL_CODE from CLW_CITY_POSTAL_CODE "+
                  " where 1=1 "+
                  " and COUNTY_CD = '"+betV.getCountyCd()+"'"+
                  " and COUNTRY_CD = '"+betV.getCountryCd()+"'"+
                  " and STATE_PROVINCE_CD = '"+betV.getStateProvinceCd()+"'";
                        //PostalCodeDataAccess.getSqlSelectIdOnly(PostalCodeDataAccess.POSTAL_CODE_ID, dbc);
                if (!betV.getCheckedFl()) {
                    //Remove
                    dbc = new DBCriteria();
                    dbc.addOneOf(BusEntityTerrDataAccess.POSTAL_CODE, countyZipReq);
                    dbc.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_ID, distId);
                    BusEntityTerrDataAccess.remove(conn, dbc);
                } else {
                    //Add
                    CityPostalCodeDataVector pcDV = CityPostalCodeDataAccess.select(conn, dbc);
                    dbc = new DBCriteria();
                    dbc.addOneOf(BusEntityTerrDataAccess.POSTAL_CODE, countyZipReq);
                    dbc.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_ID, distId);
                    BusEntityTerrDataVector betDV = BusEntityTerrDataAccess.select(conn, dbc);
                    String postalCodePrev = "";
                    for (int kk = 0; kk < pcDV.size(); kk++) {
                        CityPostalCodeData pcD = (CityPostalCodeData) pcDV.get(kk);
                        String postalCode = pcD.getPostalCode();
                        if(postalCode.equals(postalCodePrev)) continue;
                        postalCodePrev = postalCode;
                        int jj = 0;
                        for (; jj < betDV.size(); jj++) {
                            BusEntityTerrData betD = (BusEntityTerrData) betDV.get(jj);
                            if (postalCode.equals(betD.getPostalCode())) {
                                //update
                                String currFrtCd = (betD.getBusEntityTerrFreightCd() == null)?"":betD.getBusEntityTerrFreightCd();
                                String newFrtCd = (betV.getBusEntityTerrFreightCd() == null)?"":betV.getBusEntityTerrFreightCd();
                                if(!currFrtCd.equals(newFrtCd)){
                                    betD.setModBy(pUser);
                                    betD.setBusEntityTerrFreightCd(betV.getBusEntityTerrFreightCd());
                                    BusEntityTerrDataAccess.update(conn, betD);
                                }
                                break;
                            }
                        }
                        if (jj == betDV.size()) {
                            BusEntityTerrData betD = BusEntityTerrData.createValue();
                            //betD.setPostalCodeId(postalCodeId);
                            betD.setPostalCode(postalCode);
                            betD.setBusEntityId(distId);
                            betD.setBusEntityTerrCd(RefCodeNames.BUS_ENTITY_TERR_CD.DIST_TERRITORY);
                            betD.setBusEntityTerrFreightCd(betV.getBusEntityTerrFreightCd());
                            betD.setAddBy(pUser);
                            betD.setModBy(pUser);
                            BusEntityTerrDataAccess.insert(conn, betD);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }*/
        return;
    }


    /**
     *  Describe <code>updateDistributorZipCodes</code> method here.
     *
     *@param  pUser                the user login name
     *@param  pZipCodes            Description of the Parameter
     *@exception  RemoteException  if an error occurs
     */
    public void updateDistributorZipCodes(BusEntityTerrViewVector pZipCodes, String pUser)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
            conn = getConnection();
            ArrayList toExclude = new ArrayList();
            ArrayList toAdd = new ArrayList();
            int distId = 0;
            for (int ii = 0; ii < pZipCodes.size(); ii++) {
                BusEntityTerrView betV = (BusEntityTerrView) pZipCodes.get(ii);
                if (ii == 0) {
                    distId = betV.getBusEntityId();
                }
                String postalCode = betV.getPostalCode();
                if (!betV.getCheckedFl()) {
                    //Remove
                    toExclude.add(postalCode);
                } else {
                    toAdd.add(postalCode);
                }
            }
            //Remove
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_ID, distId);
            dbc.addOneOf(BusEntityTerrDataAccess.POSTAL_CODE, toExclude);
            BusEntityTerrDataAccess.remove(conn, dbc);
            //Add
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_ID, distId);
            dbc.addOneOf(BusEntityTerrDataAccess.POSTAL_CODE, toAdd);
            dbc.addOrderBy(BusEntityTerrDataAccess.POSTAL_CODE);
            BusEntityTerrDataVector betDV = BusEntityTerrDataAccess.select(conn, dbc);
            HashSet processedZips = new HashSet();
            for (int ii = 0; ii < pZipCodes.size(); ii++) {
                BusEntityTerrView betV = (BusEntityTerrView) pZipCodes.get(ii);
                String postalCode = betV.getPostalCode();
                if(processedZips.contains(postalCode)){
                    break;
                }
                processedZips.add(postalCode);
                boolean addEntry = true;
                if (betV.getCheckedFl()) {
                    String postalCode1 = "";
                    for (int jj = 0; jj < betDV.size(); jj++) {
                        BusEntityTerrData betD = (BusEntityTerrData) betDV.get(jj);
                        postalCode1 = betD.getPostalCode();
                        if (postalCode1.equals(postalCode)) {
                            addEntry = false;
                            String currFrtCd = (betD.getBusEntityTerrFreightCd() == null)?"":betD.getBusEntityTerrFreightCd();
                            String newFrtCd = (betV.getBusEntityTerrFreightCd() == null)?"":betV.getBusEntityTerrFreightCd();
                            //update
                            if(!currFrtCd.equals(newFrtCd)){
                                betD.setModBy(pUser);
                                betD.setBusEntityTerrFreightCd(betV.getBusEntityTerrFreightCd());
                                BusEntityTerrDataAccess.update(conn, betD);
                            }
                        }
                    }
                    if (addEntry) {
                        BusEntityTerrData betDInsert = BusEntityTerrData.createValue();
                        betDInsert.setPostalCode(postalCode);
                        betDInsert.setBusEntityId(distId);
                        betDInsert.setBusEntityTerrCd(RefCodeNames.BUS_ENTITY_TERR_CD.DIST_TERRITORY);
                        betDInsert.setBusEntityTerrFreightCd(betV.getBusEntityTerrFreightCd());
                        betDInsert.setAddBy(pUser);
                        betDInsert.setModBy(pUser);
                        BusEntityTerrDataAccess.insert(conn, betDInsert);
                    }
                }
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return;
    }


    /**
     *  Get all FreightHandlers that match the given criteria.
     *
     *@param  pBusEntitySearchCriteria the search criteria to use
     *@return a <code>FreightHandlerViewVector</code> of matching FreightHandlers
     *@exception  RemoteException  if an error occurs
     */
    public FreightHandlerView getFreightHandlerById
  (int pFhid ) throws RemoteException {

  BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
  besc.setSearchId(pFhid);
  FreightHandlerViewVector v = getFreightHandlersByCriteria(besc);
  if ( null == v || v.size() == 0 ) {
      return null;
  }
  return (FreightHandlerView)v.get(0);
    }

    public FreightHandlerViewVector getFreightHandlersByCriteria(
            BusEntitySearchCriteria pBusEntitySearchCriteria)throws RemoteException {

        FreightHandlerViewVector v = null;
        Connection con = null;
        try {
            con = getConnection();
            BusEntityDataVector fhbe = BusEntityDAO.getBusEntityByCriteria(con, pBusEntitySearchCriteria, RefCodeNames.BUS_ENTITY_TYPE_CD.FREIGHT_HANDLER);
            v = BusEntityDAO.getFreightHandlerDetails(con,fhbe);
        }catch (Exception e) {
            processException(e);
        }
        finally {
            closeConnection(con);
        }
        return v;
    }









    /**
     *  Description of the Method
     *
     *@param  pFreightHandler      Description of the Parameter
     *@return                      Description of the Return Value
     *@exception  RemoteException  Description of the Exception
     */
    public FreightHandlerView saveFreightHandler(FreightHandlerView pFreightHandler)
             throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            if (pFreightHandler.getBusEntityData().getBusEntityId() > 0) {
                BusEntityDataAccess.update(conn, pFreightHandler.getBusEntityData());
            } else {
                pFreightHandler.setBusEntityData
                        (BusEntityDataAccess.insert(conn, pFreightHandler.getBusEntityData()));
            }

            pFreightHandler.getPrimaryAddress().setBusEntityId(pFreightHandler.getBusEntityData().getBusEntityId());
            if (pFreightHandler.getPrimaryAddress().getAddressId() > 0) {
                AddressDataAccess.update(conn, pFreightHandler.getPrimaryAddress());
            } else {
                pFreightHandler.setPrimaryAddress
                        (AddressDataAccess.insert(conn, pFreightHandler.getPrimaryAddress())
                        );
            }

      int fhid = pFreightHandler.getBusEntityData().getBusEntityId();
      PropertyUtil propUtil = new PropertyUtil(conn);
      propUtil.saveValue(0, fhid,
             RefCodeNames.PROPERTY_TYPE_CD.FR_ROUTING_CD,
             RefCodeNames.PROPERTY_TYPE_CD.FR_ROUTING_CD,
             pFreightHandler.getEdiRoutingCd() );
      propUtil.saveValue(0, fhid,
             RefCodeNames.PROPERTY_TYPE_CD.FR_ON_INVOICE_CD,
             RefCodeNames.PROPERTY_TYPE_CD.FR_ON_INVOICE_CD,
             pFreightHandler.getAcceptFreightOnInvoice() );
      propUtil = null;

            saveBusEntAssociation(true, pFreightHandler.getBusEntityData().getBusEntityId(),pFreightHandler.getStoreId(),RefCodeNames.BUS_ENTITY_ASSOC_CD.FREIGHT_HANDLER_STORE,conn);
        } catch (Exception e) {
            throw new RemoteException("saveFreightHandler: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return pFreightHandler;
    }


    /**
     *  Gets a list of delivery schedule view objects
     *
     *@param  pFilter              set of criteria. Accepts keys: -
     *      "STATE_PROVINCE_CD", "COUNTY_CD", "POSTAL_CD", "SHORT_DESC",
     *      "ID",'SITE_ID","DISTRIBUTOR"
     *@param  pStartWithFl         applies two search types: start with ignore
     *      case (true) and contains ignore case (false)
     *@param  pDistributorId       Description of the Parameter
     *@return                      a set of DeliveryScheduleView objects
     *@exception  RemoteException  if an error occurs
     */
    public DeliveryScheduleViewVector getDeliverySchedules(int pDistributorId, Hashtable pFilter, boolean pStartWithFl)
             throws RemoteException {
        DeliveryScheduleViewVector scheduleList = new DeliveryScheduleViewVector();
        if (pFilter == null) {
            pFilter = new Hashtable();
        }
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = null;
            dbc = new DBCriteria();
            DBCriteria dbcTerr = null;
            DBCriteria dbcDetail = null;
            IdVector schedulesAccount = new IdVector();
            IdVector schedulesNoAccount = new IdVector();
            if (pDistributorId > 0) {
                dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, pDistributorId);
            } else if (pFilter.containsKey("DISTRIBUTOR")) {
                String value = ((String) pFilter.get("DISTRIBUTOR"));
                DBCriteria dbcDist = new DBCriteria();
                if (pStartWithFl) {
                    dbcDist.addBeginsWithIgnoreCase(BusEntityDataAccess.SHORT_DESC, value);
                } else {
                    dbcDist.addContainsIgnoreCase(BusEntityDataAccess.SHORT_DESC, value);
                }
                dbcDist.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                dbcDist.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                        RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                String distReq =
                        BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbcDist);
                dbc.addOneOf(ScheduleDataAccess.BUS_ENTITY_ID, distReq);
            }

            dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD,
                    RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY);
            java.util.Enumeration enume = pFilter.keys();
            while (enume.hasMoreElements()) {
                String key = (String) enume.nextElement();
                String value = (String) pFilter.get(key);
                if ("COUNTY_CD".equals(key)) {
                    if (dbcTerr == null) {
                        dbcTerr = new DBCriteria();
                    }
                    //if (pStartWithFl) {
                    //    dbcTerr.addBeginsWithIgnoreCase(CityPostalCodeDataAccess.COUNTY_CD, value.trim());
                   // } else {
                   //     dbcTerr.addContainsIgnoreCase(CityPostalCodeDataAccess.COUNTY_CD, value.trim());
                   // }
                } else if ("STATE_PROVINCE_CD".equals(key)) {
                    if (dbcTerr == null) {
                        dbcTerr = new DBCriteria();
                    }
                   // if (pStartWithFl) {
                   //     dbcTerr.addBeginsWithIgnoreCase(CityPostalCodeDataAccess.STATE_PROVINCE_CD, value.trim());
                   // } else {
                   //     dbcTerr.addContainsIgnoreCase(CityPostalCodeDataAccess.STATE_PROVINCE_CD, value.trim());
                   // }
                } else if ("CITY".equals(key)) {
                    if (dbcTerr == null) {
                        dbcTerr = new DBCriteria();
                    }
                   // if (pStartWithFl) {
                   //     dbcTerr.addBeginsWithIgnoreCase(CityPostalCodeDataAccess.CITY, value.trim());
                   // } else {
                   //     dbcTerr.addContainsIgnoreCase(CityPostalCodeDataAccess.CITY, value.trim());
                   // }
                } else if ("POSTAL_CD".equals(key)) {
                    if (dbcDetail == null) {
                        dbcDetail = new DBCriteria();
                        dbcDetail.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                                RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                    }
                    if (pStartWithFl) {
                        dbcDetail.addBeginsWithIgnoreCase(ScheduleDetailDataAccess.VALUE, value.trim());
                    } else {
                        dbcDetail.addContainsIgnoreCase(ScheduleDetailDataAccess.VALUE, value.trim());
                    }
                } else if ("SHORT_DESC".equals(key)) {
                    if (pStartWithFl) {
                        dbc.addBeginsWithIgnoreCase(ScheduleDataAccess.SHORT_DESC, value.trim());
                    } else {
                        dbc.addContainsIgnoreCase(ScheduleDataAccess.SHORT_DESC, value.trim());
                    }
                } else if ("ID".equals(key)) {
                    dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_ID, value.trim());

                } else if ("SITE_ID".equals(key)) {
                    DBCriteria dbcSiteSched = new DBCriteria();
                    String siteIdS = value.trim();
                    int siteId = -1;
                    try {
                        siteId = Integer.parseInt(siteIdS);
                    } catch (Exception exc) {}
                    DBCriteria dbcSiteAddr = new DBCriteria();

                    dbcSiteAddr.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, siteId);
                    dbcSiteAddr.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                            RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
                    dbcSiteAddr.addEqualTo(AddressDataAccess.ADDRESS_STATUS_CD,
                            RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                    AddressDataVector aDV = AddressDataAccess.select(conn, dbcSiteAddr);
                    if (aDV.size() == 1) {
                        AddressData aD = (AddressData) aDV.get(0);
                        String zipCode = aD.getPostalCode();
                        if (zipCode != null) {
                            zipCode = zipCode.trim();

                            if((aD.getCountryCd()!=null)&&(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(aD.getCountryCd()))){
                                if (zipCode.length() > 5) {
                                    zipCode = zipCode.substring(0, 5);
                                }
                            }

                            dbcSiteSched.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                                        RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                            dbcSiteSched.addEqualTo(ScheduleDetailDataAccess.VALUE, zipCode);
                        }
                    }

                    CatalogDataVector cdv = new CatalogDataVector();

                    CatalogInformation catEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();

                	// Get Catalog id
                    cdv = catEjb.getCatalogsCollectionByBusEntity(siteId,"SHOPPING");

                	// Parse the vector and search for ACTIVE main distributor


                	if(cdv.isEmpty()){
                		return scheduleList;

                	}
                	else{
                		Iterator it = cdv.iterator();
                		while(it.hasNext()){
                			CatalogData cdata = (CatalogData)it.next();
                			int cat_id = cdata.getCatalogId();

                			// Get Main distributor for catalog
                			int dist_id = getMajorDistforCatalog(cat_id);

                			dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, dist_id);
                			String dbcReq =
                				ScheduleDataAccess.getSqlSelectIdOnly(ScheduleDataAccess.SCHEDULE_ID, dbc);

                			dbc.addOneOf(ScheduleDataAccess.SCHEDULE_ID,dbcReq);
                		}
                	}

                	// Get account_id for the site
                	Account accEjb = APIAccess.getAPIAccess().getAccountAPI();

                	int accountId = accEjb.getAccountIdForSite(siteId);

                	//		" ************** Account ID ************** "+account_id);

                	// Get schedules
                	String dbcDetReq =
                		ScheduleDataAccess.getSqlSelectIdOnly(ScheduleDataAccess.SCHEDULE_ID, dbc);


                	dbcSiteSched.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, dbcDetReq);

                	// Get all schedules
                	    IdVector scheduleIds =
                                ScheduleDetailDataAccess.selectIdOnly(conn, ScheduleDetailDataAccess.SCHEDULE_ID,dbcSiteSched);
                        IdVector scheduleAcctIds = new IdVector();
                        IdVector scheduleWrongAcctIds = new IdVector();
                        dbcSiteSched = new DBCriteria();
                        dbcSiteSched.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleIds);
                        dbcSiteSched.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                                RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
                        ScheduleDetailDataVector sdDV =
                                ScheduleDetailDataAccess.select(conn,dbcSiteSched);
                        for(Iterator iter=sdDV.iterator(); iter.hasNext();) {
                		ScheduleDetailData sdData = (ScheduleDetailData)iter.next();
                                String acctIdS = sdData.getValue();
                                int scheduleId = sdData.getScheduleId();
                                try {
                                    int acctId = Integer.parseInt(acctIdS);
                                    if(acctId!=accountId) {
                                        scheduleWrongAcctIds.add(new Integer(scheduleId));
                                    } else {
                                        scheduleAcctIds.add(new Integer(scheduleId));
                                    }
                                } catch (Exception exc) {
                                    scheduleWrongAcctIds.add(new Integer(scheduleId));
                                }
                        }

                        IdVector acctScheduleIds = new IdVector();
                	// Iterate through all schedules to get those matching with account_id
                        if(!scheduleAcctIds.isEmpty()) {
                            scheduleIds = scheduleAcctIds;
                        } else {
                            for(Iterator iter=scheduleWrongAcctIds.iterator(); iter.hasNext();) {
                                Integer schIdI = (Integer) iter.next();
                                scheduleIds.contains(schIdI);
                                scheduleIds.remove(schIdI);
                            }
                        }
                        if(dbcDetail==null) {
                            dbcDetail = new DBCriteria();
                        }
                        dbcDetail.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID,scheduleIds);
                }
            }
           // if (dbcTerr != null) {
           //     String terrReq =
           //             CityPostalCodeDataAccess.getSqlSelectIdOnly(CityPostalCodeDataAccess.POSTAL_CODE, dbcTerr);
           //     if (dbcDetail == null) {
           //         dbcDetail = new DBCriteria();
           //         dbcDetail.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
           //                 RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
           //     }
           //     dbcDetail.addOneOf(ScheduleDetailDataAccess.VALUE, terrReq);
           // }
            if (dbcDetail != null) {

            	String detailReq =
                        ScheduleDetailDataAccess.getSqlSelectIdOnly(ScheduleDetailDataAccess.SCHEDULE_ID, dbcDetail);
                dbc.addOneOf(ScheduleDataAccess.SCHEDULE_ID, detailReq);
            }
            dbc.addOrderBy(ScheduleDataAccess.SCHEDULE_ID);

            ScheduleDataVector scheduleDV = ScheduleDataAccess.select(conn, dbc);
            IdVector scheduleIdV = new IdVector();
            IdVector distIdV = new IdVector();
            for (int ii = 0; ii < scheduleDV.size(); ii++) {
                ScheduleData sD = (ScheduleData) scheduleDV.get(ii);
                scheduleIdV.add(new Integer(sD.getScheduleId()));
                distIdV.add(new Integer(sD.getBusEntityId()));
            }
            //Details
            ArrayList detailTypes = new ArrayList();
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);

            dbc = new DBCriteria();
            dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleIdV);
            dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
            dbc.addOrderBy(ScheduleDetailDataAccess.SCHEDULE_ID);

            ScheduleDetailDataVector scheduleDetailDV = ScheduleDetailDataAccess.select(conn, dbc);
            //Distr info
            dbc = new DBCriteria();
            if (pDistributorId == 0) {
                dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);
            } else {
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, pDistributorId);
            }
            BusEntityDataVector distrDV = BusEntityDataAccess.select(conn, dbc);

            //Join everything
            for (int ii = 0, jj = 0; ii < scheduleDV.size(); ii++) {
                ScheduleData sD = (ScheduleData) scheduleDV.get(ii);
                //logDebug("sD=" + sD);
                DeliveryScheduleView dsVv = DeliveryScheduleView.createValue();
                int scheduleId = sD.getScheduleId();
                dsVv.setScheduleId(scheduleId);
                int distId = sD.getBusEntityId();
                dsVv.setBusEntityId(distId);
                dsVv.setScheduleName(sD.getShortDesc());
                dsVv.setScheduleStatus(sD.getScheduleStatusCd());
                //logDebug("distrDV.size()=" + distrDV.size());
                for (int kk = 0; kk < distrDV.size(); kk++) {
                    BusEntityData beD = (BusEntityData) distrDV.get(kk);
                    if (beD.getBusEntityId() == distId) {
                        dsVv.setBusEntityShortDesc(beD.getShortDesc());
                        dsVv.setBusEntityErpNum(beD.getErpNum());
                        break;
                    }
                }
                String info = "";
                String rule = sD.getScheduleRuleCd();
                int cycle = sD.getCycle();

                //Schedule elements
                boolean exceptionFl = false;
                String deliveryDates = "";
                int deliveryDatesCount = -1;
                String monthDays = "";
                String weekDays = "";
                String monthWeeks = "";
                int monthWeeksCount = 0;
                String monthWeekDay = "";
                String cutoffDay = "";
                String cutoffTime = "";
                //logDebug( "scheduleDetailDV.size()=" + scheduleDetailDV.size());
                for (; jj < scheduleDetailDV.size(); jj++) {
                    ScheduleDetailData sdD = (ScheduleDetailData) scheduleDetailDV.get(jj);
                    logDebug("sdD=" + sdD);
                    if (sdD.getScheduleId() != scheduleId) {
                        //logDebug("done with scheduleId=" + scheduleId);
                        break;
                    }
                    String detailCd = sdD.getScheduleDetailCd();
                    if (RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd)) {
                        if (RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST.equals(rule)) {
                            deliveryDatesCount++;
                            if (deliveryDatesCount == 12) {
                                deliveryDates += " ... ";
                                continue;
                            }
                            if (deliveryDatesCount > 0) {
                                deliveryDates += ", ";
                            }
                            deliveryDates += sdD.getValue();
                        } else {
                            exceptionFl = true;
                        }
                    } else if (RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE.equals(detailCd) &&
                            !(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE.equals(detailCd))
                            ) {
                        exceptionFl = true;
                    } else if (RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY.equals(detailCd)) {
                        if (monthDays.length() > 0) {
                            monthDays += ", ";
                        }
                        int monthDay = 0;
                        try {
                            monthDay = Integer.parseInt(sdD.getValue());
                        } catch (Exception exc) {
                        }
                        if (monthDay > 28) {
                            monthDays += "last day";
                        } else {
                            monthDays += monthDay;
                        }
                    } else if (RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY.equals(detailCd)) {
                        if (weekDays.length() > 0) {
                            weekDays += ", ";
                        }
                        int weekDay = 0;
                        try {
                            weekDay = Integer.parseInt(sdD.getValue());
                        } catch (Exception exc) {
                        }
                        switch (weekDay) {
                            case 1:
                                weekDays += "Sunday";
                                break;
                            case 2:
                                weekDays += "Monday";
                                break;
                            case 3:
                                weekDays += "Tuesday";
                                break;
                            case 4:
                                weekDays += "Wednesday";
                                break;
                            case 5:
                                weekDays += "Thursday";
                                break;
                            case 6:
                                weekDays += "Friday";
                                break;
                            case 7:
                                weekDays += "Saturday";
                                break;
                            default:
                                weekDays += "on " + weekDay + " day of the week";
                                break;
                        }
                    } else if (RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK.equals(detailCd)) {
                        if (monthWeeks.length() > 0) {
                            monthWeeks += ", ";
                        }
                        int monthWeek = 0;
                        monthWeeksCount++;
                        try {
                            monthWeek = Integer.parseInt(sdD.getValue());
                        } catch (Exception exc) {
                        }
                        switch (monthWeek) {
                            case 1:
                                monthWeeks += " first";
                                break;
                            case 2:
                                monthWeeks += " second";
                                break;
                            case 3:
                                monthWeeks += " third";
                                break;
                            case 4:
                                monthWeeks += " forth";
                                break;
                            case 5:
                                monthWeeks += " last";
                                break;
                            default:
                                monthWeeks += monthWeek;
                                break;
                        }
                    } else if (RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY.equals(detailCd)) {
                        cutoffDay = sdD.getValue();
                    } else if (RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME.equals(detailCd)) {
                        cutoffTime = sdD.getValue();
                    }
                }
                if (cutoffDay != null && cutoffTime != null) {
                    dsVv.setCutoffInfo(cutoffDay + " days. At " + cutoffTime);
                }
                //
                if (RefCodeNames.SCHEDULE_RULE_CD.DATE_LIST.equals(rule)) {
                    info = "On dates: " + deliveryDates;
                } else if (RefCodeNames.SCHEDULE_RULE_CD.DAY_MONTH.equals(rule)) {
                    if (cycle != 1) {
                        info = "Each " + cycle + " month ";
                    } else {
                        info = "Each month ";
                    }
                    info += " on " + monthDays;
                    if (exceptionFl) {
                        info += ". With exceptions";
                    }
                } else if (RefCodeNames.SCHEDULE_RULE_CD.WEEK.equals(rule)) {
                    if (cycle != 1) {
                        info = "Each " + cycle + " week ";
                    } else {
                        info = "Each week ";
                    }
                    info += " on " + weekDays;
                    if (exceptionFl) {
                        info += ". With exceptions";
                    }
                } else if (RefCodeNames.SCHEDULE_RULE_CD.WEEK_MONTH.equals(rule)) {
                    if (cycle != 1) {
                        info = "Each " + cycle + " month ";
                    } else {
                        info = "Each month ";
                    }
                    info += " on " + monthWeeks + " week";
                    if (monthWeeksCount > 1) {
                        info += "s";
                    }
                    info += " on " + weekDays;
                    if (exceptionFl) {
                        info += ". With exceptions";
                    }
                }

                dsVv.setScheduleInfo(info);
                //logDebug("getNextDeliveryDate(scheduleId="+scheduleId);
                dsVv.setNextDelivery(getNextDeliveryDate(0, scheduleId));
                //logDebug(" done getNextDeliveryDate(scheduleId="+scheduleId);
                scheduleList.add(dsVv);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getDeliverySchedules: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return scheduleList;
    }


    //------------------------------------------------------------------------------------
    /**
     *  Gets a delivery schedule
     *
     *@param  pScheduleId          the schedule id
     *@return                      a set of ScheduleJoinView objects
     *@exception  RemoteException  if an error occurs
     */
    public ScheduleJoinView getDeliveryScheduleById(int pScheduleId)throws RemoteException {
    	return getDeliveryScheduleById(pScheduleId, true);
    }


    /**
     *  Gets a delivery schedule
     *@param  pScheduleId the schedule id
     *@param  zipCdFl if false wont return rows where schedule_detail_cd = "ZIP_CODE"
     *@return a set of ScheduleJoinView objects
     *@exception  RemoteException        if an error occurs
     */
    public ScheduleJoinView getDeliveryScheduleById(int pScheduleId, boolean zipCdFl)
             throws RemoteException {
        ScheduleJoinView scheduleJoin = ScheduleJoinView.createValue();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = null;
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_ID, pScheduleId);
            ScheduleDataVector scheduleDV = ScheduleDataAccess.select(conn, dbc);
            if (scheduleDV.size() == 0) {
                throw new Exception("No schedule found. Schedule id: " + pScheduleId);
            }
            scheduleJoin.setSchedule((ScheduleData) scheduleDV.get(0));

            ArrayList detailTypes = new ArrayList();
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
            if(zipCdFl){
            	detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
            }
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE);

            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
            dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
            ScheduleDetailDataVector scheduleDetailDV =
                    ScheduleDetailDataAccess.select(conn, dbc);

            scheduleJoin.setScheduleDetail(scheduleDetailDV);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getDeliveryScheduleById: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return scheduleJoin;
    }


    //------------------------------------------------------------------------------------

    /**
     *  Deletes the delivery schedule
     *
     *@param  pScheduleId          the schedule id
     *@exception  RemoteException  if an error occurs
     */
    public void deleteDeliverySchedule(int pScheduleId)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = null;
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
            ScheduleDetailDataAccess.remove(conn, dbc);

            ScheduleDataAccess.remove(conn, pScheduleId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("deleteDeliverySchedule: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return;
    }


    //----------------------------------------------------------------------------------------------------

    /**
     *  Updates existing or inserts new schedule depending on scheduleId
     *  property
     *
     *@param  pSchedule            the schedule
     *@param  pScheduleDetailDV    the set of ScheduleDetail objects. It will
     *      skip all zip code confure properties
     *@param  pUser                the user login name
     *@return                      Description of the Return Value
     *@exception  RemoteException  if an error occurs
     */
    public ScheduleJoinView saveDeliverySchedule(ScheduleData pSchedule,
            ScheduleDetailDataVector pScheduleDetailDV,
            String pUser)
             throws RemoteException {
        ScheduleJoinView scheduleRet = ScheduleJoinView.createValue();
        DBCriteria dbc = null;
        Connection conn = null;
        try {
            conn = getConnection();
            int scheduleId = pSchedule.getScheduleId();
            if (scheduleId != 0) {
                //Update schedule
                ScheduleData schedule = ScheduleDataAccess.select(conn, scheduleId);
                schedule.setBusEntityId(pSchedule.getBusEntityId());
                schedule.setShortDesc(pSchedule.getShortDesc());
                schedule.setCycle(pSchedule.getCycle());
                schedule.setScheduleTypeCd(pSchedule.getScheduleTypeCd());
                schedule.setScheduleStatusCd(pSchedule.getScheduleStatusCd());
                schedule.setScheduleRuleCd(pSchedule.getScheduleRuleCd());
                schedule.setEffDate(pSchedule.getEffDate());
                schedule.setExpDate(pSchedule.getExpDate());
                schedule.setModBy(pUser);
                ScheduleDataAccess.update(conn, schedule);

                //Delete
                ArrayList detailTypes = new ArrayList();
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.ALSO_DATE);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.EXCEPT_DATE);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_DAY);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.MONTH_WEEK);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.WEEK_DAY);
                detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.INV_CART_ACCESS_INTERVAL);
                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
                dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
                ScheduleDetailDataVector scheduleDetDV =
                        ScheduleDetailDataAccess.select(conn, dbc);

                for (int ii = 0; ii < pScheduleDetailDV.size(); ii++) {
                    ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(ii);
                    nSdD.setScheduleDetailId(0);
                }
                for (int ii = 0; ii < scheduleDetDV.size(); ii++) {
                    ScheduleDetailData sdD = (ScheduleDetailData) scheduleDetDV.get(ii);
                    int detailId = sdD.getScheduleDetailId();
                    String type = sdD.getScheduleDetailCd();
                    String val = sdD.getValue();
                    boolean foundFl = false;
                    for (int jj = 0; jj < pScheduleDetailDV.size(); jj++) {
                        ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(jj);
                        if (nSdD.getScheduleDetailId() != 0) {
                            continue;
                        }
                        String nType = nSdD.getScheduleDetailCd();
                        String nVal = nSdD.getValue();
                        if (isPhysicalInventoryPeriodScheduleType(nType)) {
                            continue;
                        }
                        if (nType.equals(type)) {
                            if (nVal.equals(val)) {
                                foundFl = true;
                                nSdD.setScheduleDetailId(detailId);
                                break;
                            }
                            sdD.setValue(nVal);
                            sdD.setModBy(pUser);
                            nSdD.setScheduleDetailId(detailId);
                            ScheduleDetailDataAccess.update(conn, sdD);
                            foundFl = true;
                            break;
                        }
                    }
                    if (!foundFl) {
                        ScheduleDetailDataAccess.remove(conn, detailId);
                    }
                }
                //Save the rest details
                for (int ii = 0; ii < pScheduleDetailDV.size(); ii++) {
                    ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(ii);
                    int detailId = nSdD.getScheduleDetailId();
                    if (detailId != 0) {
                        continue;
                    }
                    if (isPhysicalInventoryPeriodScheduleType(nSdD.getScheduleDetailCd())) {
                        continue;
                    }
                    nSdD.setScheduleId(scheduleId);
                    nSdD.setAddBy(pUser);
                    nSdD.setModBy(pUser);
                    ScheduleDetailDataAccess.insert(conn, nSdD);
                }
                ///
                savePhysicalInventoryPeriods(conn, pSchedule, pScheduleDetailDV, pUser);
            } else {
                //Schedule does not exist
                pSchedule.setAddBy(pUser);
                pSchedule.setModBy(pUser);
                pSchedule = ScheduleDataAccess.insert(conn, pSchedule);
                scheduleId = pSchedule.getScheduleId();
                for (int ii = 0; ii < pScheduleDetailDV.size(); ii++) {
                    ScheduleDetailData nSdD = (ScheduleDetailData) pScheduleDetailDV.get(ii);
                    int detailId = nSdD.getScheduleDetailId();
                    nSdD.setScheduleId(scheduleId);
                    nSdD.setAddBy(pUser);
                    nSdD.setModBy(pUser);
                    ScheduleDetailDataAccess.insert(conn, nSdD);
                }
            }
            //Retreive data
            ScheduleData sD = ScheduleDataAccess.select(conn, scheduleId);
            scheduleRet.setSchedule(sD);
            dbc = new DBCriteria();
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleId);
            ScheduleDetailDataVector sdDV = ScheduleDetailDataAccess.select(conn, dbc);
            scheduleRet.setScheduleDetail(sdDV);
            //Invalidate cached site schedules
            IdVector siteIdV =
                    getDeliveryScheduleSiteIds(conn,sD.getBusEntityId(), sD.getScheduleId());
            if(siteIdV.size()>0) {
                dbc = new DBCriteria();
                ArrayList al = new ArrayList();
                al.add(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE);
                al.add(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME);
                al.add(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_DELIVERY_DATE);
                dbc.addOneOf(PropertyDataAccess.PROPERTY_TYPE_CD,al);
                dbc.addOneOf(PropertyDataAccess.BUS_ENTITY_ID,siteIdV);
                PropertyDataAccess.remove(conn,dbc);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("saveDeiverySchedule: " + e.getMessage());
        } finally {
            if (conn != null) {
                closeConnection(conn);
            }
        }

        return scheduleRet;
    }

    private static boolean isPhysicalInventoryPeriodScheduleType(String type) {
        if (type == null) {
            return false;
        }
        if (type.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE) ||
            type.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE) ||
            type.equals(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE)) {
            return true;
        }
        return false;
    }

    private void loadPhysicalPeriodsFromScheduleDetailDataVector(
        PhysicalInventoryPeriodArray periods,
        ScheduleDetailDataVector scheduleDetails) {
        if (periods == null || scheduleDetails == null) {
            return;
        }
        ScheduleDetailData detailData = null;
        String detailType = null;
        String detailValue = null;
        periods.startLoadingItems();
        for (int i = 0; i < scheduleDetails.size(); ++i) {
            detailData = (ScheduleDetailData) scheduleDetails.get(i);
            detailType = detailData.getScheduleDetailCd();
            detailValue = detailData.getValue();
            if (isPhysicalInventoryPeriodScheduleType(detailType)) {
                periods.addItem(detailType, detailValue);
            }
        }
        boolean isOk = periods.finishLoadingItems();
    }

    private void savePhysicalInventoryPeriods(Connection connection, ScheduleData scheduleData,
        ScheduleDetailDataVector scheduleDetails, String userName) throws RemoteException {
        try {
            PhysicalInventoryPeriodArray periods = new PhysicalInventoryPeriodArray();
            PhysicalInventoryPeriodArray periodsFromDb = new PhysicalInventoryPeriodArray();
            ScheduleDetailData detailData = null;

            ///
            ArrayList detailTypes = new ArrayList();
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_START_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_END_DATE);
            detailTypes.add(RefCodeNames.SCHEDULE_DETAIL_CD.PHYSICAL_INV_FINAL_DATE);
            DBCriteria criteria = new DBCriteria();
            criteria.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleData.getScheduleId());
            criteria.addOneOf(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, detailTypes);
            ScheduleDetailDataVector scheduleDetailsFromDb = ScheduleDetailDataAccess.select(connection, criteria);

            ///
            loadPhysicalPeriodsFromScheduleDetailDataVector(periods, scheduleDetails);
            loadPhysicalPeriodsFromScheduleDetailDataVector(periodsFromDb, scheduleDetailsFromDb);

            ///
            if (!PhysicalInventoryPeriodArray.isEquals(periods, periodsFromDb)) {
                ///
                String detailType = null;
                for (int i = 0; i < scheduleDetailsFromDb.size(); ++i) {
                    detailData = (ScheduleDetailData) scheduleDetailsFromDb.get(i);
                    detailType = detailData.getScheduleDetailCd();
                    if (isPhysicalInventoryPeriodScheduleType(detailType)) {
                        ScheduleDetailDataAccess.remove(connection, detailData.getScheduleDetailId());
                    }
                }
                ///
                for (int i = 0; i < scheduleDetails.size(); ++i) {
                    detailData = (ScheduleDetailData) scheduleDetails.get(i);
                    detailType = detailData.getScheduleDetailCd();
                    if (isPhysicalInventoryPeriodScheduleType(detailType)) {
                        detailData.setScheduleId(scheduleData.getScheduleId());
                        detailData.setAddBy(userName);
                        detailData.setModBy(userName);
                        ScheduleDetailDataAccess.insert(connection, detailData);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException("savePhysicalInventoryPeriods: " + ex.getMessage());
        }
    }

   private IdVector  getDeliveryScheduleSiteIds(Connection conn, int pDistributorId, int pScheduleId)
   throws Exception {

        DBCriteria dbc = null;
        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,pDistributorId);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
        IdVector catalogIdV =
                CatalogAssocDataAccess.selectIdOnly(conn,CatalogAssocDataAccess.CATALOG_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catalogIdV);
        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        catalogIdV = CatalogDataAccess.selectIdOnly(conn,CatalogDataAccess.CATALOG_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID,catalogIdV);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
        IdVector siteIdV =
                CatalogAssocDataAccess.selectIdOnly(conn,CatalogAssocDataAccess.BUS_ENTITY_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID,siteIdV);
        dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        AddressDataVector addressDV = AddressDataAccess.select(conn,dbc);

        HashMap addressHM = new HashMap();
        for(Iterator iter=addressDV.iterator(); iter.hasNext();) {
            AddressData aD = (AddressData) iter.next();
            String postalCode = aD.getPostalCode();
            if(!Utility.isSet(postalCode)) {
                continue;
            }
            String country = aD.getCountryCd();
            String key = null;
            if(!Utility.isSet(country) || RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(country.trim())) {
                if(postalCode.length()>5) postalCode = postalCode.substring(0,5);
                key = postalCode.trim()+"^"+RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;
            } else {
                key = postalCode.trim()+"^"+country.trim();
            }
            ArrayList al = (ArrayList) addressHM.get(key);
            if(al==null) {
                al = new ArrayList();
            }
            al.add(aD);
            addressHM.put(key,al);
        }

        dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
        ScheduleDetailDataVector scheduleDetailDV =
                ScheduleDetailDataAccess.select(conn,dbc);
        IdVector resSiteIdV = new IdVector();

        for(Iterator iter = scheduleDetailDV.iterator(); iter.hasNext();) {
            ScheduleDetailData sdD = (ScheduleDetailData) iter.next();
            String postalCode = sdD.getValue();
            if(postalCode==null) {
                continue;
            }
            String country = sdD.getCountryCd();
            if(!Utility.isSet(country)) {
                country = RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;
            }
            String key = postalCode.trim()+"^"+country.trim();
            ArrayList al = (ArrayList) addressHM.get(key);
            if(al!=null) {
                for(Iterator iter1=al.iterator(); iter1.hasNext();) {
                    AddressData aD = (AddressData) iter1.next();
                    resSiteIdV.add(new Integer(aD.getBusEntityId()));
                }
            }
        }

        return resSiteIdV;
    }


    /**
     *  Describe <code>getDeliveryScheduleCounties</code> method here.
     *
     *@param  pScheduleId          the schedule identifier
     *@param  pFilter              a set of criteria. Keys: "STATE_PROVINCE_CD",
     *      "COUNTY_CD", "POSTAL_CD"
     *@param  pStartWithFl         indicates two search types: start with ignore
     *      case (true)
     *@param  pConfOnlyFl          flag to return configured counties only
     *@return                      a set of BusEntityTerrView objects
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityTerrViewVector
            getDeliveryScheduleCounties(int pScheduleId,
            Hashtable pFilter,
            boolean pStartWithFl,
            boolean pConfOnlyFl)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityTerrViewVector counties = null;
            if (pConfOnlyFl && pFilter.isEmpty()) {
                counties = getAllDeliveryScheduleCounties(conn, pScheduleId);
            } else {
                counties =
                        getDeliveryScheduleCounties(conn, pScheduleId,
                        pFilter, pStartWithFl, pConfOnlyFl);
            }
            return counties;
        } catch (Exception e) {
            throw new RemoteException("getDeliveryScheduleCounties: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }


    //------------------------------------------------------------------------------------------

    /**
     *  Gets the deliveryScheduleCounties attribute of the DistributorBean
     *  object
     *
     *@param  pConn          Description of the Parameter
     *@param  pScheduleId    Description of the Parameter
     *@param  pFilter        Description of the Parameter
     *@param  pStartWithFl   Description of the Parameter
     *@param  pConfOnlyFl    Description of the Parameter
     *@return                The deliveryScheduleCounties value
     *@exception  Exception  Description of the Exception
     */
    private BusEntityTerrViewVector
            getDeliveryScheduleCounties(Connection pConn,
            int pScheduleId,
            Hashtable pFilter,
            boolean pStartWithFl,
            boolean pConfOnlyFl)
             throws Exception {
        BusEntityTerrViewVector counties = new BusEntityTerrViewVector();
        String prefWc = (pStartWithFl) ? "" : "%";
        String countyCrit = "";
        if (pFilter.containsKey("COUNTY_CD")) {
            String ss = (String) pFilter.get("COUNTY_CD");
            //countyCrit = " and upper(pc.county_cd) like '" + prefWc + ss.trim().toUpperCase() + "%' ";
            countyCrit = " and upper(pc.county_cd) like upper(?) ";
        }
        String stateCrit = "";
        if (pFilter.containsKey("STATE_PROVINCE_CD")) {
            String ss = (String) pFilter.get("STATE_PROVINCE_CD");
            //stateCrit = " and upper(pc.state_province_cd) = '" + ss.trim().toUpperCase() + "' ";
            stateCrit = " and upper(pc.state_province_cd) = upper(?) ";
        }
        String cityCrit = "";
        if (pFilter.containsKey("CITY")) {
            String ss = (String) pFilter.get("CITY");
            //cityCrit = " and upper(pc.city) like '" + ss.trim().toUpperCase() + "%' ";
            cityCrit = " and upper(pc.city) like upper(?) ";
        }
        String zipCrit = "";
        if (pFilter.containsKey("POSTAL_CD")) {
            String ss = (String) pFilter.get("POSTAL_CD");
            ss = ss.trim();
            if (ss.length() < 3) {
                String mess = "Zip code should have at least 3 characters";
                throw new Exception(mess);
            }
            String zipSql =
                    "select distinct pc1.county_cd||'^'||pc1.state_province_cd as ccss" +
                    "  from clw_city_postal_code pc1" +
                    "  where upper(pc1.postal_code) like upper(?) "; // + ss.toUpperCase() + "%' ";
            PreparedStatement stmt = pConn.prepareStatement(zipSql);
            stmt.setString(1, ss.toUpperCase() + "%");
            ResultSet countyRS = stmt.executeQuery();
            String ccssList = "";
            while (countyRS.next()) {
                String ccss = countyRS.getString("ccss");
                if (ccssList.length() > 0) {
                    ccssList += ",";
                }
                ccssList += "'" + ccss + "'";
            }
            stmt.close();
            if (ccssList.length() > 0) {
                zipCrit = "  and pc.county_cd||'^'||pc.state_province_cd in " +
                        "(" + ccssList + ")";
            }
        }

        String sql =
                "select city, country_cd, state_province_cd, county_cd, " +
                " min(state_province_nam) state_province_nam, " +
                " count(*) post_qty, count(schedule_id) sched_post_qty " +
                " from ( " +
                " select distinct pc.city, pc.country_cd, pc.state_province_cd, pc.county_cd, " +
                " pc.state_province_nam, pc.postal_code, schedule_id " +
                " from  clw_city_postal_code pc, clw_schedule_detail sd " +
                " where pc.postal_code = sd.value(+) " +
                " and sd.schedule_id(+) = " + pScheduleId + " " +
                " and sd.schedule_detail_cd(+) = 'ZIP_CODE' " +
                countyCrit +
                stateCrit +
                zipCrit +
                cityCrit +
                " ) group by city, country_cd, state_province_cd, county_cd ";
        if (pConfOnlyFl) {
            sql += " having count(schedule_id) >0 ";
        }

        PreparedStatement stmt = pConn.prepareStatement(sql);
        int i = 1;
        if (pFilter.containsKey("COUNTY_CD")) {
            String ss = prefWc + ((String) pFilter.get("COUNTY_CD")).trim() + "%";
            stmt.setString(i++, ss);
        }
        if (pFilter.containsKey("STATE_PROVINCE_CD")) {
            String ss = ((String) pFilter.get("STATE_PROVINCE_CD")).trim();
            stmt.setString(i++, ss);
        }
        if (pFilter.containsKey("CITY")) {
            String ss = prefWc + ((String) pFilter.get("CITY")).trim() + "%";
            stmt.setString(i++, ss);
        }
        
        ResultSet countyRS = stmt.executeQuery();
        while (countyRS.next()) {
            String countyVal = countyRS.getString("county_cd");
            String stateCdVal = countyRS.getString("state_province_cd");
            String stateNamVal = countyRS.getString("state_province_nam");
            String country = countyRS.getString("country_cd");
            String city = countyRS.getString("city");
            int postQty = countyRS.getInt("post_qty");
            int schedPostQty = countyRS.getInt("sched_post_qty");
            BusEntityTerrView betV = BusEntityTerrView.createValue();
            betV.setCountyCd(countyVal);
            betV.setStateProvinceCd(stateCdVal);
            betV.setStateProvinceName(stateNamVal);
            betV.setCountryCd(country);
            betV.setCheckedFl(schedPostQty > 0 ? true : false);
            betV.setNoModifiyFl((schedPostQty != postQty && schedPostQty > 0) ? true : false);
            betV.setCity(city);
            counties.add(betV);
        }
        stmt.close();
        return counties;
    }


    //-----------------------------------------------------------------------------
    /**
     *  Gets the allDeliveryScheduleCounties attribute of the DistributorBean
     *  object
     *
     *@param  pConn          Description of the Parameter
     *@param  pScheduleId    Description of the Parameter
     *@return                The allDeliveryScheduleCounties value
     *@exception  Exception  Description of the Exception
     */
    private BusEntityTerrViewVector
            getAllDeliveryScheduleCounties(Connection pConn, int pScheduleId)
             throws Exception {
        BusEntityTerrViewVector counties = new BusEntityTerrViewVector();
        String sql =
                "select " +
                " city, pc.country_cd, pc.state_province_cd,  " +
                " min(pc.state_province_nam) as state_province_nam, " +
                " pc.county_cd, " +
                " count(*) as post_qty, count(sd.value) as sched_post_qty " +
                " from " +
                " clw_city_postal_code pc, " +
                " clw_schedule_detail sd, " +
                " ( " +
                "   select distinct state_province_cd, county_cd " +
                "     from clw_city_postal_code, clw_schedule_detail " +
                "  where schedule_id = " + pScheduleId +
                "    and postal_code = value " +
                "    and schedule_detail_cd = 'ZIP_CODE' " +
                ") counties " +
                " where pc.state_province_cd = counties.state_province_cd " +
                "   and pc.county_cd = counties.county_cd " +
                "   and pc.postal_code = sd.value(+) " +
                "   and sd.schedule_id(+) = " + pScheduleId +
                "   and sd.schedule_detail_cd(+) = 'ZIP_CODE' " +
                " group by city, pc.country_cd, pc.state_province_cd, pc.county_cd";

     //   logDebug("DistributorBean getAllDeliveryScheduleCounties sql: " + sql);
        Statement stmt = pConn.createStatement();
        ResultSet countyRS = stmt.executeQuery(sql);
        while (countyRS.next()) {
            String countyVal = countyRS.getString("county_cd");
            String stateCdVal = countyRS.getString("state_province_cd");
            String stateNamVal = countyRS.getString("state_province_nam");
            String country = countyRS.getString("country_cd");
            String city = countyRS.getString("city");
            int postQty = countyRS.getInt("post_qty");
            int schedPostQty = countyRS.getInt("sched_post_qty");
            BusEntityTerrView betV = BusEntityTerrView.createValue();
            betV.setCountyCd(countyVal);
            betV.setStateProvinceCd(stateCdVal);
            betV.setStateProvinceName(stateNamVal);
            betV.setCountryCd(country);
            betV.setCheckedFl(schedPostQty > 0 ? true : false);
            betV.setNoModifiyFl((schedPostQty != postQty && schedPostQty > 0) ? true : false);
            betV.setCity(city);
            counties.add(betV);
        }
        stmt.close();
        return counties;
    }

    /**
     * Return all codes for ScheduleId
     * @param pScheduleId
     * @param countryCd
     * @param value
     * @return
     * @throws RemoteException
     */
    public BusEntityTerrViewVector getDeliveryScheduleZipCodes(
            int pScheduleId,
            String countryCd,
            String value)
            throws RemoteException {

        BusEntityTerrViewVector busEntityTerrViewVector = new BusEntityTerrViewVector();
        Connection conn = null;

        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
            if((countryCd!=null)&&(!countryCd.trim().equals(""))){
                if(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(countryCd)){
                    List list = new ArrayList();
                    list.add(countryCd);
                    list.add("");

                    dbc.addOneOfIgnoreCase(ScheduleDetailDataAccess.COUNTRY_CD, list);
                }else{
                    dbc.addLike(ScheduleDetailDataAccess.COUNTRY_CD, countryCd);
                }
            }

            if((value!=null)&&(!value.trim().equals(""))){
                dbc.addContainsIgnoreCase(ScheduleDetailDataAccess.VALUE, value);
            }
            dbc.addLike(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
            dbc.addOrderBy(ScheduleDetailDataAccess.VALUE);
            ScheduleDetailDataVector scheduleDetailDataVector = ScheduleDetailDataAccess.select(conn, dbc, 1000);

            Iterator it = scheduleDetailDataVector.iterator();
            while(it.hasNext()){
                ScheduleDetailData scheduleDetailData = (ScheduleDetailData) it.next();
                BusEntityTerrView busEntityTerrView = BusEntityTerrView.createValue();

                busEntityTerrView.setCountryCd(scheduleDetailData.getCountryCd()==null?
                        RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES
                        :scheduleDetailData.getCountryCd());
                busEntityTerrView.setPostalCode(scheduleDetailData.getValue());
                busEntityTerrView.setPostalCodeId(scheduleDetailData.getScheduleDetailId());
                busEntityTerrView.setCheckedFl(true);
                busEntityTerrViewVector.add(busEntityTerrView);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return busEntityTerrViewVector;
    }

    //------------------------------------------------------------------------------------
    /**
     *  Describe <code>getDeliveryScheduleZipCodes</code> method here.
     *
     *@param  pScheduleId          the schedule identifier
     *@param  pFilter              a set of criteria. Keys: "STATE_PROVINCE_CD",
     *      "COUNTY_CD", "POSTAL_CD"
     *@param  pStartWithFl         indicates two search types: start with ignore
     *      case (true)
     *@param  pConfOnlyFl          flag to return configured counties only
     *@return                      a set of BusEntityTerrView objects
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityTerrViewVector
            getDeliveryScheduleZipCodes(int pScheduleId,
            Hashtable pFilter,
            boolean pStartWithFl,
            boolean pConfOnlyFl)
             throws RemoteException {
        BusEntityTerrViewVector zipCodes = new BusEntityTerrViewVector();
        Connection conn = null;
        try {
            conn = getConnection();

            String prefWc = (pStartWithFl) ? "" : "%";
            String countyCrit = "";
            if (pFilter.containsKey("COUNTY_CD")) {
                //String ss = (String) pFilter.get("COUNTY_CD");
                //countyCrit = " and upper(pc.county_cd) like '" + prefWc + ss.trim().toUpperCase() + "%' ";
                countyCrit = " and upper(pc.county_cd) like upper(?) ";
            }
            String stateCrit = "";
            if (pFilter.containsKey("STATE_PROVINCE_CD")) {
                //String ss = (String) pFilter.get("STATE_PROVINCE_CD");
                //stateCrit = " and upper(pc.state_province_cd) = '" + ss.trim().toUpperCase() + "' ";
                stateCrit = " and upper(pc.state_province_cd) = upper(?) ";
            }
            String cityCrit = "";
            if (pFilter.containsKey("CITY")) {
                //String ss = (String) pFilter.get("CITY");
                //cityCrit = " and upper(pc.city) like '" + ss.trim().toUpperCase() + "%' ";
                cityCrit = " and upper(pc.city) like upper(?) ";
            }
            String zipCrit = "";
            if (pFilter.containsKey("POSTAL_CD")) {
                zipCrit = " and upper(pc.postal_code) like upper(?) ";
            }
            String outerJoin = (pConfOnlyFl) ? "" : "(+)";
            String sql =
                    "select distinct city, pc.country_cd, pc.state_province_cd, pc.state_province_nam, " +
                    " pc.county_cd, " +
                    " pc.postal_code,  nvl(sd.schedule_id,0) schedule_id  " +
                    " from " +
                    " clw_city_postal_code pc, clw_schedule_detail sd " +
                    " where pc.postal_code = sd.value" + outerJoin +
                    " and sd.schedule_id" + outerJoin + " = " + pScheduleId +
                    " and sd.schedule_detail_cd" + outerJoin + " = 'ZIP_CODE' " +
                    countyCrit +
                    stateCrit +
                    zipCrit +
                    cityCrit;

            PreparedStatement stmt = conn.prepareStatement(sql);
            int i = 1;
            if (pFilter.containsKey("COUNTY_CD")) {
                String ss = prefWc + ((String) pFilter.get("COUNTY_CD")).trim() + "%";
                stmt.setString(i++, ss);
            }
            if (pFilter.containsKey("STATE_PROVINCE_CD")) {
                String ss = ((String) pFilter.get("STATE_PROVINCE_CD")).trim();
                stmt.setString(i++, ss);
            }
            if (pFilter.containsKey("CITY")) {
                String ss = prefWc + ((String) pFilter.get("CITY")).trim() + "%";
                stmt.setString(i++, ss);
            }
            if (pFilter.containsKey("POSTAL_CD")) {
                String ss = (String) pFilter.get("POSTAL_CD");
                ss = ss.trim();
                if (ss.length() < 3) {
                    String mess = "Zip code should have at least 3 characters";
                    throw new Exception(mess);
                }
                ss = ss + "%";
                stmt.setString(i++, ss);
            }

            ResultSet pcRS = stmt.executeQuery();
            while (pcRS.next()) {
                String postalCodeVal = pcRS.getString("postal_code");
                String countyVal = pcRS.getString("county_cd");
                String stateCdVal = pcRS.getString("state_province_cd");
                String stateNamVal = pcRS.getString("state_province_nam");
                String country = pcRS.getString("country_cd");
                String city = pcRS.getString("city");
                int scheduleId = pcRS.getInt("schedule_id");
                BusEntityTerrView betV = BusEntityTerrView.createValue();
                betV.setPostalCode(postalCodeVal);
                betV.setCountyCd(countyVal);
                betV.setStateProvinceCd(stateCdVal);
                betV.setStateProvinceName(stateNamVal);
                betV.setCountryCd(country);
                betV.setCheckedFl(scheduleId > 0 ? true : false);
                betV.setNoModifiyFl(false);
                betV.setCity(city);
                zipCodes.add(betV);
            }
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getDeliveryScheduleZipCodes: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return zipCodes;
    }


    //---------------------------------------------------------------------------

    /**
     *  Describe <code>updateDeliveryScheduleCounties</code> method here.
     *
     *@param  pScheduleId          the schedule id
     *@param  pCounties            set of BusEntityTerrView objects
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateDeliveryScheduleCounties(int pScheduleId, BusEntityTerrViewVector pCounties, String pUser)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
            conn = getConnection();

            String countyAddList = "";
            String countyDelList = "";
            for (int ii = 0; ii < pCounties.size(); ii++) {
                BusEntityTerrView betV = (BusEntityTerrView) pCounties.get(ii);
                String county = betV.getCountyCd();
                String state = betV.getStateProvinceCd();
                String city = betV.getCity();
                String ss = city + "^" + county + "^" + state;
                boolean noModifyFl = betV.getNoModifiyFl();
                boolean checkedFl = betV.getCheckedFl();
                if (!noModifyFl) {
                    if (checkedFl) {
                        if (countyAddList.length() > 0) {
                            countyAddList += ",";
                        }
                        countyAddList += "'" + ss + "'";
                    } else {
                        if (countyDelList.length() > 0) {
                            countyDelList += ",";
                        }
                        countyDelList += "'" + ss + "'";
                    }
                }
            }
            if (countyDelList.length() > 0) {
                String sql = "select distinct postal_code " +
                        " from clw_city_postal_code " +
                        " where city||'^'||county_cd||'^'||state_province_cd in (" + countyDelList + ")";
                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                        RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                dbc.addCondition(ScheduleDetailDataAccess.VALUE + " in (" + sql + ")");
                logDebug("DistributorBean DDDDDDDDDDDDDDDDDDDDDD del sql: " + ScheduleDetailDataAccess.getSqlSelectIdOnly("*", dbc));
                ScheduleDetailDataAccess.remove(conn, dbc);
            }

            if (countyAddList.length() > 0) {
                String sql = "select distinct postal_code " +
                        " from clw_city_postal_code " +
                        " where city||'^'||county_cd||'^'||state_province_cd in (" + countyAddList + ")";
                logDebug("DistributorBean DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD add1 sql: " + sql);
                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                        RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                dbc.addCondition(ScheduleDetailDataAccess.VALUE + " in (" + sql + ")");
                logDebug("DistributorBean DDDDDDDDDDDDDDDDDDDDDD add2 sql: " + ScheduleDetailDataAccess.getSqlSelectIdOnly("*", dbc));
                ScheduleDetailDataVector sdDV = ScheduleDetailDataAccess.select(conn, dbc);
                Statement stmt = conn.createStatement();
                ResultSet pcRS = stmt.executeQuery(sql);
                ArrayList addZipAL = new ArrayList();
                while (pcRS.next()) {
                    String postalCodeVal = pcRS.getString("postal_code");
                    boolean foundFl = false;
                    for (int ii = 0; ii < sdDV.size(); ii++) {
                        ScheduleDetailData sdD = (ScheduleDetailData) sdDV.get(ii);
                        String zip = sdD.getValue();
                        if (postalCodeVal.equals(zip)) {
                            foundFl = true;
                            break;
                        }
                    }
                    if (!foundFl) {
                        addZipAL.add(postalCodeVal);
                    }
                }
                for (int ii = 0; ii < addZipAL.size(); ii++) {
                    String zip = (String) addZipAL.get(ii);
                    ScheduleDetailData sdD = ScheduleDetailData.createValue();
                    sdD.setScheduleId(pScheduleId);
                    sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                    sdD.setValue(zip);
                    sdD.setAddBy(pUser);
                    sdD.setModBy(pUser);
                    ScheduleDetailDataAccess.insert(conn, sdD);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("updateDeliveryScheduleCounties: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return;
    }

    public int addZipCode(int pScheduleId, ScheduleDetailData scheduleDetailData, String pUser) throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;

        try {
            conn = getConnection();

            scheduleDetailData.setScheduleId(pScheduleId);
            scheduleDetailData.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
            scheduleDetailData.setAddBy(pUser);
            scheduleDetailData.setModBy(pUser);

            if((getDeliveryScheduleZipCodes(pScheduleId, scheduleDetailData.getCountryCd(), scheduleDetailData.getValue()).size()>0)){
                return 0;
            }

            ScheduleDetailDataAccess.insert(conn, scheduleDetailData);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("addZipCode(): " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

    }

    //---------------------------------------------------------------------------

    /**
     *  Describe <code>updateDeliveryScheduleZipCodes</code> method here.
     *
     *@param  pScheduleId          the schedule id
     *@param  pZipCodes            set of BusEntityTerrView objects
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateDeliveryScheduleZipCodes(int pScheduleId, BusEntityTerrViewVector pZipCodes, String pUser)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
            conn = getConnection();

            ArrayList zipAddAL = new ArrayList();
            ArrayList zipDelAL = new ArrayList();

            for (int ii = 0; ii < pZipCodes.size(); ii++) {
                BusEntityTerrView betV = (BusEntityTerrView) pZipCodes.get(ii);
                String zip = betV.getPostalCode();
                boolean checkedFl = betV.getCheckedFl();
                if (checkedFl) {
                    zipAddAL.add(zip);
                } else {
                    zipDelAL.add(zip);
                }
            }

            if (zipDelAL.size() > 0) {
                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                        RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                dbc.addOneOf(ScheduleDetailDataAccess.VALUE, zipDelAL);
                logDebug("DistributorBean DDDDDDDDDDDDDDDDDDDDDD del sql: " + ScheduleDetailDataAccess.getSqlSelectIdOnly("*", dbc));
                ScheduleDetailDataAccess.remove(conn, dbc);
            }

            if (zipAddAL.size() > 0) {

                dbc = new DBCriteria();
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
                dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD, RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                dbc.addOneOf(ScheduleDetailDataAccess.VALUE, zipAddAL);
                logDebug("DistributorBean DDDDDDDDDDDDDDDDDDDDDD add2 sql: " + ScheduleDetailDataAccess.getSqlSelectIdOnly("*", dbc));
                ScheduleDetailDataVector sdDV = ScheduleDetailDataAccess.select(conn, dbc);

                for (int ii = 0; ii < sdDV.size(); ii++) {
                    ScheduleDetailData sdD = (ScheduleDetailData) sdDV.get(ii);
                    String zip = sdD.getValue();

                    for (int jj = 0; jj < zipAddAL.size(); jj++) {
                        String confZip = (String) zipAddAL.get(jj);
                        if (confZip.equals(zip)) {
                            zipAddAL.remove(jj);
                            break;
                        }
                    }
                }

                for (int ii = 0; ii < zipAddAL.size(); ii++) {
                    String zip = (String) zipAddAL.get(ii);
                    ScheduleDetailData sdD = ScheduleDetailData.createValue();
                    sdD.setScheduleId(pScheduleId);
                    sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
                    sdD.setValue(zip);
                    sdD.setAddBy(pUser);
                    sdD.setModBy(pUser);
                    sdD.setCountryCd(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
                    ScheduleDetailDataAccess.insert(conn, sdD);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("updateDeliveryScheduleZipCodes: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return;
    }

    /**
     *  Gets configured schedule account ids
     *
     *@param  pScheduleId          the schedule id
     *@param  pAccountIds list of account id to filter or null
     *@returns List of account ids configured to the schedule
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getDeliveryScheduleAccountIds(int pScheduleId, IdVector pAccountIds)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
          conn = getConnection();
          dbc = new DBCriteria();
          dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
          dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
              RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
          if(pAccountIds!=null) {
            LinkedList ll = new LinkedList();
            for(Iterator iter=pAccountIds.iterator(); iter.hasNext();) {
              Integer idI = (Integer) iter.next();
              ll.add(idI.toString());
            }
            dbc = new DBCriteria();
            dbc.addOneOf(ScheduleDetailDataAccess.VALUE,ll);
          }
          ScheduleDetailDataVector
                  sdDV = ScheduleDetailDataAccess.select(conn,dbc);
          IdVector configAcctIds = new IdVector();
          for(Iterator iter = sdDV.iterator(); iter.hasNext();) {
            ScheduleDetailData sdD = (ScheduleDetailData) iter.next();
            String acctIdStr = sdD.getValue();
            try {
                configAcctIds.add(new Integer(acctIdStr));
            } catch (Exception exc) {
              log.info("DistributorBean. ERROR. Invalid value of accountId: "+acctIdStr);
            }
          }
          return configAcctIds;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    //---------------------------------------------------------------------------

    /**
     *  Updates accounts configrued to the distributor schedule
     *@param  pScheduleId          the schedule id
     *@param  pAcctIdToAdd         the configured accounts
     *@param  pAcctIdToDel         the unconfigured accounts
     *@param  pUser                the user login name
     *@exception  RemoteException  if an error occurs
     */
    public void updateDeliveryScheduleAccounts(int pScheduleId,
            IdVector pAcctIdToAdd, IdVector pAcctIdToDel, String pUser)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
          conn = getConnection();
          //Remove
          LinkedList ll = new LinkedList();
          if(pAcctIdToDel!=null && pAcctIdToDel.size()>0) {
            for(Iterator iter=pAcctIdToDel.iterator(); iter.hasNext();) {
              Integer idI = (Integer) iter.next();
              ll.add(idI.toString());
            }
            dbc = new DBCriteria();
            dbc.addOneOf(ScheduleDetailDataAccess.VALUE,ll);
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                    RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
            ScheduleDetailDataAccess.remove(conn,dbc);
          }
          //Pick existing
          if(pAcctIdToAdd!=null && pAcctIdToAdd.size()>0) {
            ll = new LinkedList();
            for(Iterator iter=pAcctIdToAdd.iterator(); iter.hasNext();) {
              Integer idI = (Integer) iter.next();
              ll.add(idI.toString());
            }
            dbc = new DBCriteria();
            dbc.addOneOf(ScheduleDetailDataAccess.VALUE,ll);
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                    RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
            dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID,pScheduleId);
            ScheduleDetailDataVector existingAccts =
              ScheduleDetailDataAccess.select(conn,dbc);
            IdVector acctIdExists = new IdVector();
            for(Iterator iter = existingAccts.iterator(); iter.hasNext();) {
              ScheduleDetailData sdD = (ScheduleDetailData) iter.next();
              String acctIdStr = sdD.getValue();
              try {
                acctIdExists.add(new Integer(acctIdStr));
              } catch (Exception exc) {
                log.info("DistributorBean. ERROR. Invalid value of accountId: "+acctIdStr);
              }
            }
            //Add
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,ll);
            if (acctIdExists.size()>0)
            	dbc.addNotOneOf(BusEntityDataAccess.BUS_ENTITY_ID,acctIdExists);
            IdVector newAcctIdV =
               BusEntityDataAccess.selectIdOnly(conn,dbc);
            for(Iterator iter = newAcctIdV.iterator(); iter.hasNext();) {
              Integer idI = (Integer) iter.next();
              ScheduleDetailData sdD = ScheduleDetailData.createValue();
              sdD.setScheduleId(pScheduleId);
              sdD.setScheduleDetailCd(RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
              sdD.setValue(idI.toString());
              sdD.setAddBy(pUser);
              sdD.setModBy(pUser);
              ScheduleDetailDataAccess.insert(conn,sdD);
            }
          }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
        return;
    }

    //---------------------------------------------------------------------------
    /**
     *  Calculates next delivery date for the schedule
     *
     *@param pSiteId the site identifier (0 is acceptable)
     *@param  pScheduleId  of the Schedule identifier
     *@return                      Date object
     *@exception  RemoteException  if an error occurs
     */
    public Date getNextDeliveryDate(int pSiteId,int pScheduleId)
             throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            Date currDate = new Date();

            Date deliveryDate = getDeliveryDate(conn, pSiteId, pScheduleId, currDate, currDate);
            return deliveryDate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getDeliveryDate: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }


    //---------------------------------------------------------------------------
    /**
     *  Calculates delivery date for the order.
     *
     *@param  pDistId              the distributor id. If 0 pDistErpNum would be
     *      used
     *@param  pDistErpNum          the distributor erp number
     *@param  pSiteId              the site identifier (if 0 would not chech site schedule)
     *@param  pOrderDate           the date of the order
     *@param  pOrderTime           the time of the order
     *@param  pZipCode             site zip code
     *@return                      Date object
     *@exception  RemoteException  if an error occurs
     */
    public Date getDeliveryDate(int pDistId, String pDistErpNum, int pSiteId, Date pOrderDate, Date pOrderTime, String pZipCode)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
            conn = getConnection();
            if (pDistId == 0 && pDistErpNum != null) {
                dbc = new DBCriteria();
                dbc.addEqualTo(BusEntityDataAccess.ERP_NUM, pDistErpNum.trim());
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                        RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                BusEntityDataVector beDV = BusEntityDataAccess.select(conn, dbc);
                if (beDV.size() == 0) {
                    String errorMess = "Error. DistributorBean.getDeliveryDate. No active distributor with erp: " + pDistErpNum;
                    logError(errorMess);
                    return null;
                }
                if (beDV.size() > 1) {
                    String errorMess = "Error. DistributorBean.getDeliveryDate. More than one active distributor with erp: " + pDistErpNum;
                    logError(errorMess);
                    return null;
                }
                BusEntityData beD = (BusEntityData) beDV.get(0);
                pDistId = beD.getBusEntityId();
            }
            if (pDistId == 0) {
                String errorMess = "Error. DistributorBean.getDeliveryDate. No distributor information provided. Dist id: " +
                        pDistId + " Dist erp num: " + pDistErpNum;
                logError(errorMess);
                return null;
            }

            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pSiteId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            IdVector acctIdV =
               BusEntityAssocDataAccess.selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);
            int acctId = 0;
            if(acctIdV.size()>0) {
              acctId = ((Integer) acctIdV.get(0)).intValue();
            }
            ScheduleData schedule =
                    getSchedule(conn, pDistId, pZipCode, acctId,pOrderDate);
            Date deliveryDate = null;
            if(schedule!=null) {
              deliveryDate = getDeliveryDate(conn, pSiteId, schedule.getScheduleId(),
                               pOrderDate, pOrderTime);
            }
            return deliveryDate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getDeliveryDate: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }
    }



    /**
     */
    private Date getDeliveryDate(Connection pConn, int pSiteId, int pScheduleId,
            Date pOrderDate, Date pOrderTime)
             throws Exception {
        GregorianCalendar orderCal =
                calculateNextOrderDate(pConn, pScheduleId, pOrderDate, pOrderTime, pSiteId );

  if ( null == orderCal ) return null;

        return orderCal.getTime();
    }

    private ScheduleData getSchedule(Connection pCon,
            int pDistId, String pZipCode, int pAcctId,
            Date pOrderDate)
             throws Exception {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
        dbc.addEqualTo(ScheduleDetailDataAccess.VALUE, pZipCode);
        dbc.addOrderBy(ScheduleDetailDataAccess.SCHEDULE_ID);
        IdVector zipCodeSchedIdV =
           ScheduleDetailDataAccess.selectIdOnly(pCon,ScheduleDetailDataAccess.SCHEDULE_ID,dbc);

        IdVector acctSchedIdV = null;
        if(pAcctId>0) {
          dbc = new DBCriteria();
          dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
          dbc.addEqualTo(ScheduleDetailDataAccess.VALUE, ""+pAcctId);
          dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, zipCodeSchedIdV);
          acctSchedIdV =
             ScheduleDetailDataAccess.selectIdOnly(pCon,ScheduleDetailDataAccess.SCHEDULE_ID,dbc);
        } else {
          acctSchedIdV = new IdVector();
        }
        ScheduleDataVector scheduleDV = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String orderDateS =
             (pOrderDate!=null)? sdf.format(pOrderDate):sdf.format(new Date());
        ScheduleData selectedScheduleD = null;
        if(acctSchedIdV.size()>0) {
          try {
            selectedScheduleD = getSchedule(pCon,pDistId,orderDateS,acctSchedIdV);
          } catch (MultipleDataException exc) {
            String mess = "Found: multiple schedules for the distributor. Distributor id: " + pDistId +
                    ", date: " + pOrderDate + ", zip code: " + pZipCode;
            logError(mess);
            return null;
          }
        }
        if(selectedScheduleD == null) {
          dbc = new DBCriteria();
          dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID,zipCodeSchedIdV);
          dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                  RefCodeNames.SCHEDULE_DETAIL_CD.ACCOUNT_ID);
          dbc.addOrderBy(ScheduleDetailDataAccess.SCHEDULE_ID);
          IdVector acctAssignSchedIdV =
             ScheduleDetailDataAccess.selectIdOnly(pCon,ScheduleDetailDataAccess.SCHEDULE_ID,dbc);
          Integer wrkSchIdI = null;
          for(Iterator iter=zipCodeSchedIdV.iterator(),
                       iter1=acctAssignSchedIdV.iterator(); iter.hasNext();) {
            Integer schIdI = (Integer) iter.next();
            while(iter1.hasNext() || wrkSchIdI!=null) {
              if(wrkSchIdI==null) wrkSchIdI = (Integer) iter1.next();
              if(schIdI.equals(wrkSchIdI)) {
                wrkSchIdI = null;
                iter.remove();
              }
              break;
            }
          }
          try {
            selectedScheduleD = getSchedule(pCon,pDistId,orderDateS,zipCodeSchedIdV);
          } catch (MultipleDataException exc) {
            String mess = "Found: multiple schedules for the distributor. Distributor id: " + pDistId +
                    ", date: " + pOrderDate + ", zip code: " + pZipCode;
            logError(mess);
            return null;
          }
        }
        return selectedScheduleD;
    }

    private ScheduleData getSchedule(Connection pCon, int pDistId, String pDateS, IdVector pScheduleIds)
    throws Exception
    {
      DBCriteria  dbc = new DBCriteria();
      dbc.addEqualTo(ScheduleDataAccess.BUS_ENTITY_ID, pDistId);
      dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD,
                  RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY);
      String effDateCond =
        ScheduleDataAccess.EFF_DATE + "<= to_date('" + pDateS + "','mm/dd/yyyy')";
      dbc.addCondition(effDateCond);

      String expDateCond =
            "nvl("+ScheduleDataAccess.EXP_DATE + ",to_date('1/1/3000','dd/mm/yyyy'))>"+
            "to_date('" + pDateS + "','mm/dd/yyyy')";
      dbc.addCondition(expDateCond);
      dbc.addOneOf(ScheduleDataAccess.SCHEDULE_ID,pScheduleIds);
      ScheduleDataVector scheduleDV = ScheduleDataAccess.select(pCon,dbc);

      Date currDate = new Date();
      ScheduleData selectedScheduleD = null;
      for(Iterator iter = scheduleDV.iterator(); iter.hasNext();) {
        ScheduleData sD = (ScheduleData) iter.next();
        Date effDate = sD.getEffDate();
        Date expDate = sD.getExpDate();
        String status = sD.getScheduleStatusCd();
        if (!RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE.equals(status) &&
          !currDate.before(effDate) &&
           (expDate == null || currDate.before(expDate))) {
            continue;
            //for current scheduel schedule status should be ACTIVE, not true for future or old ones
        }
        if(selectedScheduleD==null) {
          selectedScheduleD = sD;
        } else {
          throw new MultipleDataException("Multiple schedules found");
        }
      }
      return selectedScheduleD;
    }


    /**
     *  Calculates cutoff and delivery dates for site-distributor
     *
     *@param  pSiteId     The site identifier
     *@param  pDistId     The distributor identifier
     *@param  pZipCode     The Site Zip Code
     *@param  pOrderDate  Order day and time
     *@return    Cutoff and Delivery days pair
     *@exception  RemoteException  Description of the Exception
     */
    public ScheduleOrderDates calculateNextOrderDates
            (int pSiteId, int pDistId, String pZipCode, Date pOrderDate)
             throws RemoteException {
        Connection conn = null;
        DBCriteria dbc = null;
        try {
            conn = getConnection();
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pSiteId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            IdVector acctIdV =
               BusEntityAssocDataAccess.selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);
            int acctId = 0;
            if(acctIdV.size()>0) {
              acctId = ((Integer) acctIdV.get(0)).intValue();
            }

            ScheduleData schd =
               getSchedule(conn, pDistId, pZipCode, acctId, pOrderDate);
            if (null == schd) {
                return null;
            }

            ScheduleProc scheduleProc =
                          createScheduleProc (conn, pSiteId, schd.getScheduleId());
            ScheduleOrderDates sod =
             scheduleProc.getFirstOrderDeliveryDates(pOrderDate, pOrderDate, false);
            return sod;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    /**
     */
    public GregorianCalendar calculateNextOrderDate
            (Connection pConn, int pScheduleId,
            Date pOrderDate, Date pOrderTime, int pSiteId)
             throws Exception {

        ScheduleProc scheduleProc = createScheduleProc (pConn, pSiteId,pScheduleId);
        GregorianCalendar deliveryCal = scheduleProc.getOrderDeliveryDate(pOrderDate,pOrderTime);
        return deliveryCal;
    }
    //------------------------------------------------------------------------------------------
    private ScheduleProc createScheduleProc (Connection pConn, int pSiteId, int pScheduleId)
    throws Exception
    {
      //get distributor schedule
      ScheduleJoinView   sjVw = getDeliveryScheduleById(pScheduleId);
      //get site schedule
      DBCriteria dbc;
        SiteDeliveryScheduleView sds = null;
        if(pSiteId!=0) {
          String siteSchedule = null;
          dbc = new DBCriteria();
          dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,pSiteId);
          dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                               RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE);
          PropertyDataVector propertyDV = PropertyDataAccess.select(pConn,dbc);
          if(propertyDV.size()==1) {
            PropertyData pD = (PropertyData) propertyDV.get(0);
            siteSchedule = pD.getValue();
          }
          if(siteSchedule!=null && siteSchedule.trim().length()>0) {
            StringTokenizer st = new StringTokenizer(siteSchedule, ":");
            sds = SiteDeliveryScheduleView.createValue();
            for (int tt = 0; st.hasMoreTokens(); tt++ ) {
              String str =   st.nextToken();
              if ( tt == 0 ) {
                sds.setSiteScheduleType(str);
              } else if ( str.startsWith("Last")) {
                sds.setLastWeekofMonth(true);
              } else if(str.equals("1")) {
                sds.setWeek1ofMonth(true);
              } else if(str.equals("2")) {
                sds.setWeek2ofMonth(true);
              } else if(str.equals("3")) {
                sds.setWeek3ofMonth(true);
              } else if(str.equals("4")) {
                sds.setWeek4ofMonth(true);
              }
            }
          }
        }
        ScheduleProc scheduleProc = new ScheduleProc(sjVw,sds);
        scheduleProc.initSchedule();
        return scheduleProc;
    }

    /**
     *  Description of the Method
     *
     *@param  pValues  Description of the Parameter
     *@return          Description of the Return Value
     */
    private int[] makeOrderedArray(ArrayList pValues) {
        int[] list = new int[pValues.size()];
        for (int ii = 0; ii < list.length; ii++) {
            Integer valI = (Integer) pValues.get(ii);
            list[ii] = valI.intValue();
        }
        for (int ii = 0; ii < list.length - 1; ii++) {
            boolean retFl = true;
            for (int jj = 0; jj < list.length - ii - 1; jj++) {
                int val1 = list[jj];
                int val2 = list[jj + 1];
                if (val1 > val2) {
                    list[jj] = val2;
                    list[jj + 1] = val1;
                    retFl = false;
                }
            }
            if (retFl) {
                break;
            }
        }
        return list;
    }


    /**
     *  Gets the dateInList attribute of the DistributorBean object
     *
     *@param  pDate      Description of the Parameter
     *@param  pDateList  Description of the Parameter
     *@return            The dateInList value
     */
    private boolean isDateInList(Date pDate, ArrayList pDateList) {
        boolean ret = false;
        for (int ii = 0; ii < pDateList.size(); ii++) {
            Date dd = (Date) pDateList.get(ii);
            if (dd.equals(pDate)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    // pZip (5 digit zip code).
    public boolean isFreightFreeZone(int pDistId, String pZip)
        throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            logDebug("isFreightFreeZone: int pDistId=" + pDistId +
                     ", String pZip=" + pZip );
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_ID,
                           pDistId);
            dbc.addEqualTo(BusEntityTerrDataAccess.POSTAL_CODE,
                           pZip);
            dbc.addEqualTo(BusEntityTerrDataAccess.BUS_ENTITY_TERR_CD,
                           RefCodeNames.BUS_ENTITY_TERR_CD.DIST_TERRITORY);
            BusEntityTerrDataVector btdv =
                BusEntityTerrDataAccess.select(con,dbc);
            boolean ret =  DistributorInvoiceFreightTool.isFreightFreeArea(btdv);
            logDebug("isFreightFreeZone: int pDistId=" + pDistId +
                     ", String pZip=" + pZip +
                     " returning=" + ret);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return false;
    }

    public OrderRoutingData getPrefferedFreightHandler(int pDistId, String pZip)
        throws RemoteException {

        Connection con = null;
        OrderRoutingData ord = null;
        int fhid = 0;
        try {
            con = getConnection();
            String sz = pZip.substring(0,3);
            logDebug("getPrefferedFreightHandler: int pDistId=" + pDistId +
                     ", String pZip=" + pZip + " sz=" + sz);
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(OrderRoutingDataAccess.DISTRIBUTOR_ID,
                           pDistId);
            dbc.addEqualTo(OrderRoutingDataAccess.ACCOUNT_ID, 0);
            dbc.addEqualTo(OrderRoutingDataAccess.ZIP, sz);
            OrderRoutingDataVector ordv =
                OrderRoutingDataAccess.select(con,dbc);
            for ( int i = 0; fhid == 0 && ordv != null && i < ordv.size(); i++ ) {
                ord = (OrderRoutingData)ordv.get(i);
                logDebug("getPrefferedFreightHandler: ord=" + ord );
                if ( ord.getAccountId() == 0 ) {
                    fhid = ord.getFreightHandlerId();
                }
            }

            logDebug("getPrefferedFreightHandler: int pDistId=" + pDistId +
                     ", String pZip=" + pZip +
                     " freight handler id=" + fhid);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return ord;
    }

    //-------------------------------------------------------------------------------------
    /*
    private ArrayList addNationalHolidays(ArrayList pHolidays, Date pStartDate, Date pEndDate) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(pStartDate);
      int year = cal.get(Calendar.YEAR);
      GregorianCalendar holidayCal = new GregorianCalendar(year,Calendar.JANUARY,1);
      while(!holidayCal.getTime().after(pEndDate)){
        //January 1
        Date holidayDate = holidayCal.getTime();
        if(holidayDate.after(pEndDate)) break;
        if(!isDateInList(holidayDate,pHolidays))pHolidays.add(holidayDate);

        //July 4
        holidayCal.set(Calendar.MONTH,Calendar.JULY);
        holidayCal.set(Calendar.DATE,4);
        holidayDate = holidayCal.getTime();
        if(holidayDate.after(pEndDate)) break;
        if(!isDateInList(holidayDate,pHolidays))pHolidays.add(holidayDate);

        //December 25
        holidayCal.set(Calendar.MONTH,Calendar.DECEMBER);
        holidayCal.set(Calendar.DATE,25);
        holidayDate = holidayCal.getTime();
        if(holidayDate.after(pEndDate)) break;
        if(!isDateInList(holidayDate,pHolidays))pHolidays.add(holidayDate);

        //Set January 1 next year
        year++;
        holidayCal = new GregorianCalendar(year,Calendar.JANUARY,1);

      }
      return pHolidays;
    }
     */
    //------------------------------------------------------------------------------------
    /**
     *  Gets items for the distributor
     *
     *@param  pDistId       the distributor identifier
     *@param  pItemIds      the set of item ids
     *@return   a set of DistItemView objects
     *@exception  RemoteException  if an error occurs
     */
    public DistItemViewVector  getDistItems(int pDistId, IdVector pItemIds)
    throws RemoteException {
       DistItemViewVector distItemVwV = new DistItemViewVector();
        Connection conn = null;
        try {
          conn = getConnection();
          if(pItemIds==null || pItemIds.size()==0) return distItemVwV;
          ArrayList idCritVector = new ArrayList();
          String crit = "";
          for(int ii=0; ii<pItemIds.size();ii++) {
            if(ii!=0 && ii%1000==0) {
              idCritVector.add(crit);
              crit = new String("");
            }
            if(crit.length()>0) crit += ",";
            crit += pItemIds.get(ii);
          }
          idCritVector.add(crit);

          String sqlTempl =
          "select "+
          " i.item_id as item_id, "+
          " i.sku_num as sku_num, "+
          " i.short_desc as short_desc, "+
          " uom.clw_value as uom, "+
          " pack.clw_value as pack, "+
          " asize.clw_value as asize, "+
          " m_map.item_mapping_id as mfg_item_mapping_id, "+
          " m_map.bus_entity_id as mfg_id, "+
          " m_map.item_num as mfg_item_sku, "+
          " mfg.short_desc as mfg_name, "+
          " d_map.item_mapping_id as dist_item_mapping_id, "+
          " d_map.bus_entity_id as dist_id, "+
          " d_map.item_num as dist_item_sku, "+
          " d_map.item_uom as dist_item_uom, "+
          " d_map.item_pack as dist_item_pack, "+
          " d_map.uom_conv_multiplier as dist_uom_conv_multiplier, "+
          " dist.short_desc as dist_name, "+
          " ima.item_mapping_assoc_id as item_mapping_assoc_id, "+
          " ima.item_mapping1_id as item_mapping1_id, "+
          " ima.item_mapping2_id as item_mapping2_id, "+
          " m1_map.item_mapping_id as mfg1_item_mapping_id, "+
          " m1_map.bus_entity_id as mfg1_id, "+
          " m1_map.item_num as mfg1_item_sku, "+
          " mfg1.short_desc as mfg1_name "+
          " from  "+
          " clw_item i, "+
          " clw_item_meta uom, "+
          " clw_item_meta pack, "+
          " clw_item_meta asize, "+
          " clw_item_mapping m_map, "+
          " clw_bus_entity mfg, "+
          " clw_item_mapping d_map, "+
          " clw_bus_entity dist, "+
          " clw_item_mapping_assoc ima, "+
          " clw_item_mapping m1_map, "+
          " clw_bus_entity mfg1 "+
          " where 1=1 "+
          "  and i.item_id = uom.item_id(+) "+
          "  and uom.name_value(+) = 'UOM' "+
          "  and i.item_id = pack.item_id(+) "+
          "  and pack.name_value(+) = 'PACK' "+
          "  and i.item_id = asize.item_id(+) "+
          "  and asize.name_value(+) = 'SIZE' "+
          "  and i.item_id = m_map.item_id(+) "+
          "  and m_map.item_mapping_cd(+) = 'ITEM_MANUFACTURER' "+
          "  and m_map.bus_entity_id = mfg.bus_entity_id(+) "+
          "  and i.item_id = d_map.item_id(+)  "+
          "  and d_map.item_mapping_cd(+) = 'ITEM_DISTRIBUTOR' "+
          "  and d_map.bus_entity_id(+) = "+pDistId+
          "  and dist.bus_entity_id(+) = "+pDistId+
          "  and d_map.item_mapping_id = ima.item_mapping1_id(+) "+
          "  and ima.item_mapping_assoc_cd(+) = '"+
                   RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG+"' "+
          "  and ima.item_mapping2_id = m1_map.item_mapping_id(+) "+
          "  and m1_map.bus_entity_id = mfg1.bus_entity_id(+) "+
          "  and i.item_id in ";
         for(int ii=0; ii<idCritVector.size(); ii++) {
            String sqlWrk = sqlTempl + "("+idCritVector.get(ii)+")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlWrk);
            while (rs.next()) {
              DistItemView diVw = DistItemView.createValue();
              diVw.setItemId(rs.getInt("item_id"));
              diVw.setItemMfgMappingId(rs.getInt("mfg_item_mapping_id"));
              diVw.setItemDistMappingId(rs.getInt("dist_item_mapping_id"));
              diVw.setItemMfg1MappingId(rs.getInt("mfg1_item_mapping_id"));
              diVw.setItemMappingAssocId(rs.getInt("item_mapping_assoc_id"));
              diVw.setSku(rs.getInt("sku_num"));
              String shortDesc = rs.getString("short_desc");
              if(shortDesc==null) shortDesc = "";
              diVw.setName(shortDesc);
              String size = rs.getString("asize");
              if(size==null) size = "";
              diVw.setSize(size);
              String pack = rs.getString("pack");
              if(pack==null) pack = "";
              diVw.setPack(pack);
              String uom = rs.getString("uom");
              if(uom==null) uom = "";
              diVw.setUom(uom);
              diVw.setMfgId(rs.getInt("mfg_id"));
              String mfgName = rs.getString("mfg_name");
              if(mfgName==null) mfgName = "";
              diVw.setMfgName(mfgName);
              String mfgItemSku = rs.getString("mfg_item_sku");
              if(mfgItemSku==null) mfgItemSku = "";
              diVw.setMfgItemSku(mfgItemSku);
              diVw.setDistId(rs.getInt("dist_id"));
              String distName = rs.getString("dist_name");
              if(distName==null) distName = "";
              diVw.setDistName(distName);
              String distItemSku = rs.getString("dist_item_sku");
              if(distItemSku==null) distItemSku = "";
              diVw.setDistItemSku(distItemSku);
              String distItemPack = rs.getString("dist_item_pack");
              if(distItemPack==null) distItemPack = "";
              diVw.setDistItemPack(distItemPack);
              String distItemUom = rs.getString("dist_item_uom");
              if(distItemUom==null) distItemUom = "";
              diVw.setDistItemUom(distItemUom);
              BigDecimal distUomConvMultiplier = rs.getBigDecimal("dist_uom_conv_multiplier");
              if(distUomConvMultiplier==null) distUomConvMultiplier = ONE;
              diVw.setDistUomConvMultiplier(distUomConvMultiplier);
              diVw.setMfg1Id(rs.getInt("mfg1_id"));
              String mfg1Name = rs.getString("mfg1_name");
              if(mfg1Name==null) mfg1Name = "";
              diVw.setMfg1Name(mfg1Name);
              String mfg1ItemSku = rs.getString("mfg1_item_sku");
              if(mfg1ItemSku==null) mfg1ItemSku = "";
              diVw.setMfg1ItemSku(mfg1ItemSku);
              distItemVwV.add(diVw);
            }
            rs.close();
            stmt.close();
         }


        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return distItemVwV;
    }

    //--------------------------------------------------------------------------
    /**
     *  Saves generic manufacturer item information. Creates new or updates existing
     *  Corrects duplicated relations
     *@param  pDistItem   the  distributor item data
     *@param  pUser      the user login name
     *@return   input DistItemView object with ids set
     *@exception  RemoteException
     */
    public DistItemView updateDitstItemMfgInfo(DistItemView pDistItem, String pUser)
    throws RemoteException
    {
      Connection conn = null;
      try {
        conn = getConnection();
        if(pDistItem.getMfg1Id()<=0) return pDistItem;
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID,pDistItem.getItemId());
        dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,pDistItem.getDistId());
        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
          RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
        ItemMappingDataVector itemMappingDV =ItemMappingDataAccess.select(conn,dbc);
        if(itemMappingDV.size()==0) {
          String errorMess = "Distributor does not have item. Distributor id =  "
                +pDistItem.getDistId()+" Item id = "+pDistItem.getItemId();
          throw new Exception(errorMess);
        }
        if(itemMappingDV.size()>1) {
          String errorMess = "Database error. More than one record for "+
            "distributor-item relation. Distributor id =  "
                +pDistItem.getDistId()+" Item id = "+pDistItem.getItemId();
          throw new Exception(errorMess);
        }
        ItemMappingData distItemMappingD = (ItemMappingData) itemMappingDV.get(0);
        itemMappingDV = null;
        pDistItem.setItemDistMappingId(distItemMappingD.getItemMappingId());

        ItemMappingData imD = null;
        int size = 0;
        try {
           imD = ItemMappingDataAccess.select(conn,pDistItem.getItemMfg1MappingId());
           size = 1;
        } catch(DataNotFoundException exc) {}
        if(size==0) {
          imD = ItemMappingData.createValue();
          imD.setItemId(pDistItem.getItemId());
          imD.setBusEntityId(pDistItem.getMfg1Id());
          imD.setItemNum(pDistItem.getMfg1ItemSku());
          imD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_GENERIC_MFG);
          imD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
          imD.setAddBy(pUser);
          imD.setModBy(pUser);
          imD = ItemMappingDataAccess.insert(conn, imD);
        } else {
          imD.setBusEntityId(pDistItem.getMfg1Id());
          imD.setItemNum(pDistItem.getMfg1ItemSku());
          imD.setModBy(pUser);
          ItemMappingDataAccess.update(conn, imD);
        }
        pDistItem.setItemMfg1MappingId(imD.getItemMappingId());

        dbc = new DBCriteria();
        dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID,
                                      distItemMappingD.getItemMappingId());
        dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING2_ID,imD.getItemMappingId());
        dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,
                            RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);
        ItemMappingAssocDataVector itemMappingAssocDV =
                            ItemMappingAssocDataAccess.select(conn,dbc);
        size = itemMappingAssocDV.size();
        if(size>1) {
          ItemMappingAssocDataAccess.remove(conn,dbc);
          size=0;
        }
        ItemMappingAssocData imaD = null;
        if(size==0) {
          imaD = ItemMappingAssocData.createValue();
          imaD.setItemMapping1Id(pDistItem.getItemDistMappingId());
          imaD.setItemMapping2Id(pDistItem.getItemMfg1MappingId());
          imaD.setItemMappingAssocCd(RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);
          imaD.setAddBy(pUser);
          imaD.setModBy(pUser);
          imaD = ItemMappingAssocDataAccess.insert(conn,imaD);
        } else {
          imaD =  (ItemMappingAssocData) itemMappingAssocDV.get(0);
          imaD.setItemMapping1Id(pDistItem.getItemDistMappingId());
          imaD.setItemMapping2Id(pDistItem.getItemMfg1MappingId());
          imaD.setModBy(pUser);
          ItemMappingAssocDataAccess.update(conn,imaD);
        }
        pDistItem.setItemMappingAssocId(imaD.getItemMappingAssocId());

      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("updateDitstItemMfgInfo: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
      return pDistItem;
    }
    //--------------------------------------------------------------------------
    /**
     *  Removes generic manufacturer item information. Creates new or updates existing
     *@param  pDistItem   the  distributor item data
     *@exception  RemoteException
     */
    public void removeDitstItemMfgInfo(DistItemView pDistItem)
    throws RemoteException
    {
      Connection conn = null;
      try {
        conn = getConnection();

        DBCriteria dbc = new DBCriteria();
        ItemMappingAssocDataAccess.remove(conn,pDistItem.getItemMappingAssocId());
        ItemMappingDataAccess.remove(conn,pDistItem.getItemMfg1MappingId());

      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("updateDitstItemMfgInfo: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
      return;
    }

    //--------------------------------------------------------------------------
    /**
     *  Gets distributor contacts
     *@param  pDistId   the  distributor id
     *@return set of ContactView objects
     *@exception  RemoteException
     */
    public ContactViewVector getDistributorContacts (int pDistId)
    throws RemoteException
    {
      Connection conn = null;
      try {
        conn = getConnection();
        return BusEntityDAO.getBusEntityContacts(conn,pDistId);
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
    closeConnection(conn);
      }
    }

    /**
     * Udpates or inserts distributor contact inforemation
     *@param  pContactView   contact data
     *@param  pUser   user login name
     *@return ContactView object
     *@exception  RemoteException
     */
    public ContactView updateDistributorContact (ContactView pContactView, String pUser)
    throws RemoteException
    {
      Connection conn = null;
      try {
        conn = getConnection();
  return BusEntityDAO.updateContact(conn, pContactView, pUser);

      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
    closeConnection(conn);
      }
    }

    /**
     * Deletes distributor contact inforemation
     *@param  pContactId   contact id
     *@exception  RemoteException
     */
    public void deleteDistributorContact (int pContactId)
    throws RemoteException
    {
      Connection conn = null;
      try {
        conn = getConnection();
  BusEntityDAO.deleteContact (conn,pContactId) ;

      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
    closeConnection(conn);
      }
    }

    /**
     * Gets active groups for the distributor
     *@param  pDistId   distributor id
     *@return a vector of GroupData objects
     *@exception  RemoteException
     */
    public GroupDataVector getDistributorGroups (int pDistId)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        dbc = new DBCriteria();
        dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                RefCodeNames.GROUP_ASSOC_CD.BUS_ENTITY_OF_GROUP);
        dbc.addEqualTo(GroupAssocDataAccess.BUS_ENTITY_ID, pDistId);
        String groupReq =
          GroupAssocDataAccess.getSqlSelectIdOnly(GroupDataAccess.GROUP_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(GroupDataAccess.GROUP_ID, groupReq);
        dbc.addEqualTo(GroupDataAccess.GROUP_STATUS_CD,
                                     RefCodeNames.GROUP_STATUS_CD.ACTIVE);
        dbc.addEqualTo(GroupDataAccess.GROUP_TYPE_CD,
                                      RefCodeNames.GROUP_TYPE_CD.DISTRIBUTOR);
        dbc.addOrderBy(GroupDataAccess.SHORT_DESC);

        GroupDataVector groupDV = GroupDataAccess.select(conn,dbc);

        return groupDV;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    /**
     * Gets primary manufacturers for the distibutor
     *@param  pDistId   distributor id
     *@return a vector of BusEntityData objects
     *@exception  RemoteException
     */
    public BusEntityDataVector getPrimaryManufacturers (int pDistId)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_MANUFACTURER);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pDistId);
        String mfgReq =  BusEntityAssocDataAccess.
            getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, mfgReq);
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);

        BusEntityDataVector mfgDV = BusEntityDataAccess.select(conn,dbc);

        return mfgDV;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    /**
     * Adds primary manufacturer for the distibutor
     *@param  pDistId   distributor id
     *@param  pMfgId   manufacturer id
     *@param pUser the user login name
     *@exception  RemoteException
     */
    public void addPrimaryManufacturer (int pDistId, int pMfgId, String pUser)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_MANUFACTURER);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pDistId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pMfgId);
        IdVector assocIds =  BusEntityAssocDataAccess.
            selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID,dbc);
        if(assocIds.size()==1) {
          //Already exists
          return;
        }
        if(assocIds.size()>1) {
          //Only one association allowed
          assocIds.remove(0);
          dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_ID,assocIds);
          BusEntityAssocDataAccess.remove(conn,dbc);
          return;
        }
        //Add assciation
        BusEntityAssocData beaD = BusEntityAssocData.createValue();
        beaD.setBusEntity1Id(pDistId);
        beaD.setBusEntity2Id(pMfgId);
        beaD.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_MANUFACTURER);
        beaD.setAddBy(pUser);
        beaD.setModBy(pUser);
        beaD = BusEntityAssocDataAccess.insert(conn,beaD);
        return;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }
    /**
     * Removes primary manufacturer for the distibutor
     *@param  pDistId   distributor id
     *@param  pMfgId   manufacturer id
     *@exception  RemoteException
     */
    public void deletePrimaryManufacturer (int pDistId, int pMfgId)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_MANUFACTURER);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pDistId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pMfgId);
        BusEntityAssocDataAccess.remove(conn,dbc);
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }
    /**
     * Gets distributor related addresses
     *@param  pDistId   distributor id
     *@return a vector of AddressData objects
     *@exception  RemoteException
     */
    public AddressDataVector getAddresses (int pDistId,String pAddressTypeCd)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        dbc = new DBCriteria();
        dbc.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,pDistId);
        dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, pAddressTypeCd);
        AddressDataVector addrDV = AddressDataAccess.select(conn,dbc);
        return addrDV;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    /**
     * Adds or Updates distributor related address
     *@param  pAddress address to save
     *@param pUser the user login name
     *@return address
     *@exception  RemoteException
     */
    public AddressData saveAddress (AddressData pAddress, String pUser)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        int addressId = pAddress.getAddressId();
        if(addressId>0) {
          pAddress.setModBy(pUser);
          AddressDataAccess.update(conn,pAddress);
        } else {
          pAddress.setAddBy(pUser);
          pAddress.setModBy(pUser);
          pAddress = AddressDataAccess.insert(conn,pAddress);
        }
          return pAddress;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    /**
     * Removes distributor related address
     *@exception  RemoteException
     */
    public void deleteAddress (int pAddressId)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        AddressDataAccess.remove(conn,pAddressId);
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    /**
     * Gets a list of states served by the distributor
     * @param pDistId  distributor id
     * @exception  RemoteException
     */
    public ArrayList getServedStates (int pDistId)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        ArrayList servedStates = new ArrayList();
        conn = getConnection();
        String sql =
         "select distinct state_province_nam,state_province_cd  "+
         " from clw_city_postal_code cpc, "+
         " ( select distinct postal_code "+
         "   from clw_bus_entity_terr where bus_entity_id = "+pDistId+
         " and bus_entity_terr_cd='DIST_TERRITORY' ) bet "+
         " where cpc.postal_code = bet.postal_code "+
         " order by state_province_nam ";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String state = rs.getString(1)+" ("+rs.getString(2)+")";
          servedStates.add(state);
        }
        rs.close();
        stmt.close();
        return servedStates;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    /**
     * Gets a list of accounts served by the distributor
     * @param pDistId  distributor id
     * @exception  RemoteException
     */
    public BusEntityDataVector getServedAccounts (int pDistId)
    throws RemoteException
    {
      Connection conn = null;
      DBCriteria dbc = null;
      try {
        conn = getConnection();
        ArrayList servedStates = new ArrayList();
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.BUS_ENTITY_ID,pDistId);
        IdVector catalogIds = CatalogStructureDataAccess.selectIdOnly
                               (conn,CatalogStructureDataAccess.CATALOG_ID,dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                                         RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catalogIds);
        catalogIds = CatalogDataAccess.selectIdOnly(conn,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID,catalogIds);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
        String siteReq =
          CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.BUS_ENTITY_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,siteReq);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                                  RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        IdVector accountIds =
           BusEntityAssocDataAccess.selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, accountIds);
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);

        BusEntityDataVector accounts= BusEntityDataAccess.select(conn,dbc);
        return accounts;
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error: " + e.getMessage());
      } finally {
          try {
              if (conn != null) {
                  conn.close();
              }
        } catch (Exception ex) {}
      }
    }

    public ArrayList getBranchesCollection( int pDistId)
    throws RemoteException {
  AddressDataVector branchAddresses =
      getAddresses (pDistId,RefCodeNames.ADDRESS_TYPE_CD.BRANCH);

  ArrayList distBranches = new ArrayList();
  Iterator it = branchAddresses.iterator();
  Connection conn = null;
  try {
      conn = getConnection();
      while ( it.hasNext() ) {
    AddressData addr = (AddressData)it.next();
    DistBranchData distBranchData = new DistBranchData(addr);
    distBranchData.setContactsCollection
        (BusEntityDAO.getAddressContacts
         (conn,addr.getAddressId()) );
    distBranches.add(distBranchData);
      }
  } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException("Error: " + e.getMessage());
  }
  finally {
      closeConnection(conn);
  }

  return  distBranches;
    }


    public String getDistRuntimeDisplayName(int pDistId) throws RemoteException {
        Connection conn = null;
        String runtimeDisplayName = null;
        PropertyDataVector lPropv;
        try {
            conn = getConnection();

            // get the runtimeDisplayName property
            DBCriteria propCrit = new DBCriteria();
            propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                    pDistId);
            propCrit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.RUNTIME_DISPLAY_NAME);
      lPropv = PropertyDataAccess.select(conn, propCrit);
            if (lPropv.size() > 1) {
                logError("DistributorBean.getDistRuntimeDisplayName: multiple properties found: "
                         + lPropv.toString());
            } else if (lPropv.size() == 1) {
                runtimeDisplayName = ((PropertyData) lPropv.get(0)).getValue();
            } else {
                runtimeDisplayName = null;
            }

            return runtimeDisplayName;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }

   /**
   * Gets a distributor shedule for zip code and account active for provided date
   * @param pDistId  distributor id
   * @param pZipCode zip code
   * @param pAcctId account id
   * @param pDate date when schedule should be active
   * @return ScheduleData object or null if not found or multiple schedules found
   * @exception  RemoteException
   */
   public ScheduleData getScheduleForZipCode(int pDistId, String pZipCode, int pAcctId,
            Date pDate)
    throws RemoteException {
        Connection conn = null;
        try {
          conn = getConnection();
          ScheduleData
                  scheduleD = getSchedule(conn,pDistId, pZipCode, pAcctId,pDate);
          return scheduleD;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }
    // Get the major distributor for the catalog.
    public int getMajorDistforCatalog(int catId) throws RemoteException {

        DBCriteria dbc;
        Connection conn = null;
        try {
            conn = getConnection();
            dbc = new DBCriteria();
            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, catId);
            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);

            IdVector distIdV =
                    CatalogAssocDataAccess.selectIdOnly(conn, CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

            int distId = 0;
            if (distIdV.size() == 1) {
                Integer distIdI = (Integer) distIdV.get(0);
                distId = distIdI.intValue();
            }
            if (distId == 0) {
                String q = "select cs.bus_entity_id, count(cs.item_id) qty " +
                        " from clw_catalog_structure cs " +
                        " where catalog_id = " + catId +
                        " group by cs.bus_entity_id " +
                        " order by qty desc ";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(q);
                if (rs.next()) {
                    distId = rs.getInt(1);
                }
                rs.close();
                stmt.close();
            }
            return distId;
        } catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
    }

    public IdVector getSchedules(Date pDate)
     throws RemoteException {
         Connection conn = null;
         try {
           conn = getConnection();
           SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
           String pDateS =
                (pDate!=null)? sdf.format(pDate):sdf.format(new Date());
           DBCriteria  dbc = new DBCriteria();
           dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD,RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY);
           dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_STATUS_CD,RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE);
           String effDateCond = ScheduleDataAccess.EFF_DATE + "<= to_date('" + pDateS + "','mm/dd/yyyy')";
           dbc.addCondition(effDateCond);
           String expDateCond =
                     "nvl("+ScheduleDataAccess.EXP_DATE + ",to_date('1/1/3000','dd/mm/yyyy'))>"+
                     "to_date('" + pDateS + "','mm/dd/yyyy')";
           dbc.addCondition(expDateCond);
           IdVector scheduleDV = ScheduleDataAccess.selectIdOnly(conn,ScheduleDataAccess.SCHEDULE_ID,dbc);
           return scheduleDV;
         } catch (Exception e) {
             throw processException(e);
         } finally {
             closeConnection(conn);
         }

     }

     public IdVector  getSiteIdsForSchedule(int pDistributorId, int pScheduleId)
         throws RemoteException {
       Connection conn = null;

       try {
         conn = getConnection();
         DBCriteria dbc = null;
         dbc = new DBCriteria();

         dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,pDistributorId);
         dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                  RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
         IdVector catalogIdV =
                  CatalogAssocDataAccess.selectIdOnly(conn,CatalogAssocDataAccess.CATALOG_ID,dbc);

         dbc = new DBCriteria();
         dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catalogIdV);
         dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                  RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
         catalogIdV = CatalogDataAccess.selectIdOnly(conn,CatalogDataAccess.CATALOG_ID,dbc);

         dbc = new DBCriteria();
         dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID,catalogIdV);
         dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                  RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
         IdVector siteIdV =
                  CatalogAssocDataAccess.selectIdOnly(conn,CatalogAssocDataAccess.BUS_ENTITY_ID,dbc);

         dbc = new DBCriteria();
         dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID,siteIdV);
         dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                  RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
         AddressDataVector addressDV = AddressDataAccess.select(conn,dbc);

         HashMap addressHM = new HashMap();
         for(Iterator iter=addressDV.iterator(); iter.hasNext();) {
           AddressData aD = (AddressData) iter.next();
           String postalCode = aD.getPostalCode();
           if(!Utility.isSet(postalCode)) {
             continue;
           }
           String country = aD.getCountryCd();
           String key = null;
           if(!Utility.isSet(country) || RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(country.trim())) {
             if(postalCode.length()>5) postalCode = postalCode.substring(0,5);
             key = postalCode.trim()+"^"+RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;
           } else {
             key = postalCode.trim()+"^"+country.trim();
           }
           ArrayList al = (ArrayList) addressHM.get(key);
           if(al==null) {
             al = new ArrayList();
           }
           al.add(aD);
           addressHM.put(key,al);
         }

         dbc = new DBCriteria();
         dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_ID, pScheduleId);
         dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                        RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
         ScheduleDetailDataVector scheduleDetailDV =
                  ScheduleDetailDataAccess.select(conn,dbc);
         IdVector resSiteIdV = new IdVector();

         for(Iterator iter = scheduleDetailDV.iterator(); iter.hasNext();) {
           ScheduleDetailData sdD = (ScheduleDetailData) iter.next();
           String postalCode = sdD.getValue();
           if(postalCode==null) {
             continue;
           }
           String country = sdD.getCountryCd();
           if(!Utility.isSet(country)) {
             country = RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;
           }
           String key = postalCode.trim()+"^"+country.trim();
           ArrayList al = (ArrayList) addressHM.get(key);
           if(al!=null) {
             for(Iterator iter1=al.iterator(); iter1.hasNext();) {
               AddressData aD = (AddressData) iter1.next();
               resSiteIdV.add(new Integer(aD.getBusEntityId()));
             }
           }
         }
         return resSiteIdV;
       } catch (Exception e) {
         throw processException(e);
       } finally {
         closeConnection(conn);
       }

     }

     public OrderAddOnChargeDataVector getOrderAddOnCharges(int pDistId, int pOrderId)
     throws RemoteException {
    	 Connection conn = null;
    	 OrderAddOnChargeDataVector addOnCharges = new OrderAddOnChargeDataVector();
         try {
           conn = getConnection();
           DBCriteria crit = new DBCriteria();
           crit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, pOrderId);
           crit.addEqualTo(OrderAddOnChargeDataAccess.BUS_ENTITY_ID, pDistId);

           addOnCharges = OrderAddOnChargeDataAccess.select(conn, crit);

         } catch (Exception e) {
        	 throw processException(e);
         } finally {
        	 closeConnection(conn);
         }
         return addOnCharges;
     }

     public  BusEntityData getDistributorForOrderItem(int pOrderId, int pStoreId, String pErpNum)
     throws RemoteException {
    	 
    	 BusEntityDataVector businessEntityDV = null;
    	 BusEntityData businessEntity = null;
    		 
         Connection conn = null;
         try {
             conn = getConnection();
             businessEntityDV = getDistributorForOrderItem(pOrderId, pStoreId, pErpNum, conn);
             if(businessEntityDV!=null && businessEntityDV.size()>0)
             {
             businessEntity = (BusEntityData) businessEntityDV.get(0);
             }
         } catch (Exception e) {
             throw processException(e);
         } finally {
             closeConnection(conn);
         }

         return businessEntity;    	 
     }
     
     public BusEntityDataVector getDistributorForOrderItem(int pOrderId, int pStoreId, String pErpNum, Connection conn) throws RemoteException {
         
    	 BusEntityDataVector beDV = null;
    	 logInfo("getDistributorForOrderItem() method: pErpNum = " + pErpNum);
    	 
         try {
        	 /*** Wrong SQL below: Distributor Erp Num is not yet in the clw_order_item table;
        	      it is only in the orderItem object ***/
             /***
             String sqlCond =
        	 "select bea.bus_entity1_id" + 
        	 " select be.bus_entity_id" + 
        	 " from clw_bus_entity be, clw_bus_entity_assoc bea, clw_order o, clw_order_item oi" +
        	 " where oi.order_id = " + pOrderId + 
        	 " and oi.dist_erp_num = be.erp_num" +
        	 " and o.order_id = oi.order_id" +
        	 " and be.bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR+"'" +
        	 " and be.bus_entity_status_cd = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"'" +
        	 " and be.bus_entity_id = bea.bus_entity1_id" +
        	 " and be.erp_num = oi.dist_erp_num" +
        	 " and trim(be.erp_num) = trim('"+pErpNum+"'" + ")" + 
        	 " and bea.bus_entity2_id = " + pStoreId; 
             ***/

             String sqlCond =             
            	 " select be.bus_entity_id" + /* Distributor Id */
            	 " from clw_bus_entity be, clw_bus_entity_assoc bea" +
            	 " where be.bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR+"'" +
            	 " and be.bus_entity_status_cd = '"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"'" +
            	 " and be.bus_entity_id = bea.bus_entity1_id" +
            	 " and trim(be.erp_num) = trim('"+pErpNum+"'" + ")" + /* Distributor's erpNum */
            	 " and bea.bus_entity2_id = " + pStoreId; /* Store Id */

             DBCriteria dbc = new DBCriteria();
             dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, sqlCond);

             logInfo("Distr select: "+BusEntityDataAccess.getSqlSelectIdOnly("*", dbc));
             beDV = BusEntityDataAccess.select(conn, dbc);
             logInfo("beDV = " + beDV);
         } catch (Exception e) {
        	 throw processException(e);
         } finally {
        	 closeConnection(conn);
         }
          
         return beDV;          
     }
     

     public EmailData getRejectedInvEmail(int distributorId) throws RemoteException {
    	 EmailData rejectedInvEmailData = null;
       DBCriteria emailCrit = new DBCriteria();
       emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,distributorId);
       emailCrit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,
       		RefCodeNames.EMAIL_TYPE_CD.REJECTED_INVOICE);
       
       Connection conn = null;
       try {
           conn = getConnection();
           EmailDataVector rejectedInvEmailVec =
             EmailDataAccess.select(conn, emailCrit);
           if (rejectedInvEmailVec.size() > 0) {
          		rejectedInvEmailData = (EmailData) rejectedInvEmailVec.get(0);
          }
       } catch (Exception e) {
           throw processException(e);
       } finally {
           closeConnection(conn);
       }
       return rejectedInvEmailData;
     }

}
