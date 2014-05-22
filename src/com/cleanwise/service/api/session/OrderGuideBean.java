package com.cleanwise.service.api.session;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;

import java.math.BigDecimal;

import java.rmi.*;

import java.sql.*;

import java.util.*;
import java.util.Date;

import javax.ejb.*;


/**
 *  Implementation of the OrderGuide interface.
 *
 *@author     dvieira
 *@created    August 14, 2001
 *@see        com.cleanwise.service.api.session.OrderGuide
 */
public class OrderGuideBean extends ShoppingServicesAPI {

  /**
   *  Default constructor.
   */
  public OrderGuideBean() {
  }

  /**
   *  Gets the OrderGuideInfo value for the id specified.
   *
   *@param  pOrderGuideId
   *@return
   *@exception  RemoteException
   *@exception  DataNotFoundException
   *@see                               OrderGuideInfoData
   */
  public OrderGuideInfoData getOrderGuideInfo(int pOrderGuideId) throws RemoteException,
    DataNotFoundException {

    OrderGuideData ogd = getOrderGuide(pOrderGuideId);
    OrderGuideItemDescDataVector ogsdv = getItems(ogd);
    OrderGuideInfoData ret = new OrderGuideInfoData(ogd, ogsdv);
    
    return ret;
  }
  
  /**
   *  Gets the OrderGuideInfo value for the id specified.
   *
   *@param  pOrderGuideId
   *@return
   *@exception  RemoteException
   *@exception  DataNotFoundException
   *@see                               OrderGuideInfoData
   */
  public OrderGuideInfoData getOrderGuideInfoWithEstimatedTotal(int pOrderGuideId, int pContractId) 
  throws RemoteException,DataNotFoundException {

	  Connection conn = null;
	  OrderGuideInfoData ogInfo = null;
	  try {
	      conn = getConnection();
	      ogInfo = getOrderGuideInfo(pOrderGuideId);  
	      OrderGuideStructureDataVector items = getOrderGuideItems(pOrderGuideId);
	      List itemIds = new ArrayList();
	      for(int i=0; i<items.size(); i++){
	    	  int itemId = ((OrderGuideStructureData)items.get(i)).getItemId();
	    	  itemIds.add(itemId);
	      }
	      
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
	      dbc.addOneOf(ContractItemDataAccess.ITEM_ID, itemIds);
	      ContractItemDataVector contractItems = ContractItemDataAccess.select(conn, dbc);
	      
	      BigDecimal total = new BigDecimal(0);
	      for(int ii=0; ii<items.size(); ii++){
	    		OrderGuideStructureData ogItem = (OrderGuideStructureData)items.get(ii);
	    		
	    		for(int jj=0; jj<contractItems.size(); jj++){
	    			ContractItemData cItem = (ContractItemData)contractItems.get(jj);
	    			
	    			if(cItem.getItemId() == ogItem.getItemId()){
	    				BigDecimal price = cItem.getAmount().multiply(new BigDecimal(ogItem.getQuantity()));
	    				if(price!=null && price.compareTo(new BigDecimal(0))>0){
	    					total = total.add(price);
	    				}
	    			}
	    		}
	      }
	      ogInfo.setEstimatedTotal(total);
	      
	  }catch (Exception e) {
		  throw new RemoteException("getOrderGuideInfoWithEstimatedTotal: " + e.getMessage());
	  } finally {
		  try {
			  if (conn != null) {
				  conn.close();
			  }
		  } catch (Exception ex) {
		  }
	  }
	  
    return ogInfo;
  }
  
  /**
   *  Gets the OrderGuide for the id specified.
   *
   *@param  pOrderGuideId
   *@return                            The OrderGuide value
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideData getOrderGuide(int pOrderGuideId) throws RemoteException, DataNotFoundException {

    Connection conn = null;
    OrderGuideData ogd = null;

    try {
      conn = getConnection();
      ogd = OrderGuideDataAccess.select(conn, pOrderGuideId);
    } catch (Exception e) {
      throw new RemoteException("getOrderGuide: " + e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return ogd;
  }

    public OrderGuideData getOrderGuide(int pUserId,
                                        int pSiteId,
                                        String ogType) throws RemoteException {

        Connection conn = null;
        OrderGuideData ogd = null;

        try {
            conn = getConnection();
            return getOrderGuide(conn, pUserId, pSiteId, ogType);
        } catch (Exception e) {
            throw new RemoteException("getOrderGuide: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
}

    public OrderGuideData getOrderGuide(Connection con,
                                               int pUserId,
                                               int pSiteId,
                                               String ogType) throws SQLException {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, ogType);
        dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);

        if (pUserId > 0) {
            dbc.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
        }

        dbc.addOrderBy(OrderGuideDataAccess.ORDER_GUIDE_ID, false);

        OrderGuideDataVector ogdv = OrderGuideDataAccess.select(con, dbc);
        if (null != ogdv && ogdv.size() > 0) {
            return (OrderGuideData) ogdv.get(0);
        }
        return null;
    }

  /**
   *  Fetch the order guide description information for the id specified.
   *
   *@param  pOrderGuideId              an <code>int</code> value
   *@return                            an <code>OrderGuideDescData</code>
   *      value
   *@exception  RemoteException        if an error occurs
   *@exception  DataNotFoundException  if an error occurs
   */
  public OrderGuideDescData getOrderGuideDesc(int pOrderGuideId) throws RemoteException,
    DataNotFoundException {

    Connection conn = null;
    OrderGuideData ogd = null;

    try {
      conn = getConnection();
      ogd = OrderGuideDataAccess.select(conn, pOrderGuideId);
    } catch (Exception e) {
      throw new RemoteException("getOrderGuide: " + e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return fillOrderGuideDescData(ogd);
  }

  /**
   *  Gets the OrderGuideDataVector for the user id specified.
   *
   *@param  pUserId
   *@param  pTypeId              the order guide type
   *@return                      OrderGuideDataVector
   *@exception  RemoteException
   */
  public OrderGuideDescDataVector getCollectionByUser(int pUserId,
    int pTypeId) throws RemoteException {

    OrderGuideDataVector ogv = getCollection(pTypeId, "UserId", pUserId);

    return fillOrderGuideDescVector(ogv);
  }

    /**
     * Gets the OrderGuideDescDataVector
     *
     * @param pUserId     user id
     * @param pStoreId    store id
     * @param pFilter     - order guide name pattern or order guide id
     * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
     * @return OrderGuideDataVector
     * @throws RemoteException if error
     */
    public OrderGuideDescDataVector getCollectionByUser(int pUserId,
                                                        int pStoreId,
                                                        String pFilter,
                                                        String pFilterType) throws RemoteException {

        return getCollectionByUser(pUserId, pStoreId, null, pFilter, pFilterType);

  }


    public OrderGuideDescDataVector getCollectionByUser(Integer pUserId,
                                                        Integer pStoreId,
                                                        IdVector pAccountIds,
                                                        String pFilter,
                                                        String pFilterType) throws RemoteException {

        Connection conn = null;
        OrderGuideDescDataVector result = new OrderGuideDescDataVector();

        try {

            conn = getConnection();

            if (pUserId != null) {

                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
                dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);

                String siteUserReq = UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);

                IdVector accountIds = new IdVector();
                if (pStoreId != null) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
                    if (pAccountIds != null) {
                        dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pAccountIds);
                    }
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

                    accountIds = BusEntityAssocDataAccess.selectIdOnly(conn, BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                } else {
                    if (pAccountIds != null) {
                        accountIds = pAccountIds;
                    }
                }

                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountIds);
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteUserReq);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                String siteUserStoreReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, siteUserStoreReq);
                dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

                String catalogSiteReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(OrderGuideDataAccess.CATALOG_ID, catalogSiteReq);
                if (Utility.isSet(pFilter)) {
                    pFilter = pFilter.trim();
                    if ("id".equalsIgnoreCase(pFilterType)) {
                        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_ID, Integer.parseInt(pFilter));
                    } else if ("nameContains".equalsIgnoreCase(pFilterType)) {
                        dbc.addContainsIgnoreCase(OrderGuideDataAccess.SHORT_DESC, pFilter);
                    } else { //nameBegins - default
                        dbc.addBeginsWithIgnoreCase(OrderGuideDataAccess.SHORT_DESC, pFilter);
                    }
                }

                List<String> ogTypeCds = new ArrayList<String>();
                ogTypeCds.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
                ogTypeCds.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);

                dbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, ogTypeCds);
                dbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);

                OrderGuideDataVector orderGuideDV = OrderGuideDataAccess.select(conn, dbc);

                for (Iterator iter = orderGuideDV.iterator(); iter.hasNext();) {
                    OrderGuideData ogD = (OrderGuideData) iter.next();
                    int userId = ogD.getUserId();
                    if (userId > 0 && userId != pUserId) {
                        iter.remove();
                    }
                }

                logInfo("getCollectionByUser => sql: " + (OrderGuideDataAccess.getSqlSelectIdOnly("*", dbc)));

                result = fillOrderGuideDescVector(orderGuideDV);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
   *  Gets the order guides associated with this catalog id.
   *
   *@param  pCatalogId
   *@param  pTypeId
   *@return                      OrderGuideDataVector
   *@exception  RemoteException
   */
  public OrderGuideDescDataVector getCollectionByCatalog(int pCatalogId,
    int pTypeId) throws RemoteException {

    OrderGuideDataVector ogv = getCollection(pTypeId, "CatalogId",
                                             pCatalogId);

    return fillOrderGuideDescVector(ogv);
  }
  


  /**
   *  Describe <code>getCollectionByType</code> method here.
   *
   *@param  pOrderGuideType      a <code>String</code> value
   *@return                      an <code>OrderGuideDescDataVector</code>
   *      value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector getCollectionByType(String pOrderGuideType) throws RemoteException {

    OrderGuideDataVector ogv = getCollection(pOrderGuideType, null, 0);

    return fillOrderGuideDescVector(ogv);
  }

  /**
   *  Gets a collection of order guides.
   *
   *@param  pMatchType
   *@param  pName                Name fragment to match on.
   *@param  pType                Order guide type id
   *@param  pBusEntityId         Business entity associated with the order
   *      guide(s).
   *@return                      OrderGuideDataVector
   *@exception  RemoteException
   *@see                         OrderGuide
   */
  public OrderGuideDescDataVector getCollectionByName(String pName,
    int pMatchType,
    int pBusEntityId,
    int pType) throws RemoteException {

    Connection conn = null;
    OrderGuideDataVector ogdv;

    try {
      conn = getConnection();

      DBCriteria crit = new DBCriteria();
      crit = addShortDescCriterion(crit, pMatchType, pName);
      crit = addTypeCriterion(crit, pType);

      if (pBusEntityId > 0) {
        crit.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID,
                        pBusEntityId);
      }

      crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
      ogdv = OrderGuideDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getOrderGuideByName: " +
                                e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return fillOrderGuideDescVector(ogdv);
  }

  /**
   *  Gets the CatalogItems attribute of the OrderGuideBean object
   *
   *@param  pOrderGuideId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData getCatalogItems(int pOrderGuideId) throws RemoteException,
    DataNotFoundException {

    OrderGuideData ogd = getOrderGuide(pOrderGuideId);
    OrderGuideItemDescDataVector ov = getItemsToAdd(ogd.getCatalogId(),
      pOrderGuideId);

    return new OrderGuideInfoData(ogd, ov);
  }

  /**
   *  Gets the CollectionBySite attribute of the OrderGuideBean object
   *
   *@param  pSiteId              Description of Parameter
   *@param  pTypeId              Order guide type id
   *@return                      The CollectionBySite value
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideDescDataVector getCollectionBySite(int pSiteId,
    int pTypeId) throws RemoteException {

    OrderGuideDescDataVector ogdv = new OrderGuideDescDataVector();

    // Get the users for this site.
    APIAccess apiAccess = null;
    //User userBean = null;
    //OrderGuide ogBean = null;
    CatalogInformation cati = null;

    try {
      apiAccess = getAPIAccess();
      //userBean = apiAccess.getUserAPI();
      //ogBean = apiAccess.getOrderGuideAPI();
      cati = apiAccess.getCatalogInformationAPI();

      CatalogData sitecat = cati.getSiteCatalog(pSiteId);
      int catalogId = sitecat.getCatalogId();
      ogdv = getCollectionByCatalog(catalogId, pTypeId);

      /* UserDataVector udv = userBean.getUsersCollectionByBusEntity
                (pSiteId, null);
                // Get the order guides for the users.
                for (int i = 0; i < udv.size(); i++) {
                UserData ud = (UserData) udv.get(i);
                OrderGuideDescDataVector uogv =
                getCollectionByUser(ud.getUserId());
                for (int uogi = 0; uogi < uogv.size(); uogi++) {
                ogdv.add(uogv.get(uogi));
                }
                }*/
    } catch (Exception e) {
      throw processException(e);
    }

    return ogdv;
  }
  public OrderGuideDescDataVector getCollectionBySiteUser(int pSiteId, int pUserId, 
		    int pTypeId) throws RemoteException {

		    // Get the users for this site.
		    APIAccess apiAccess = null;
		    CatalogInformation cati = null;
		    OrderGuideDataVector ogv = new OrderGuideDataVector();
		    try {
		      apiAccess = getAPIAccess();
		      cati = apiAccess.getCatalogInformationAPI();

		      CatalogData sitecat = cati.getSiteCatalog(pSiteId);
		      int catalogId = sitecat.getCatalogId();
		      
		      DBCriteria crit = new DBCriteria();
		      if (catalogId > 0 ){
		    	  crit.addEqualTo(OrderGuideDataAccess.CATALOG_ID, catalogId );
		      }
		      switch (pTypeId) {
		      case (OrderGuide.TYPE_BUYER):
		    	crit.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);  
		        crit.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
		      	break;
		      case (OrderGuide.TYPE_TEMPLATE):
		        LinkedList templatetypes = new LinkedList();
		        templatetypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
		        templatetypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE);
		        crit.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, templatetypes);
		    	DBCriteria isolatedCriteria = new DBCriteria();
		    	DBCriteria or = new DBCriteria();
		    	isolatedCriteria.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pSiteId);
		    	or.addIsNull(OrderGuideDataAccess.BUS_ENTITY_ID);
		    	isolatedCriteria.addOrCriteria(or);
		    	crit.addIsolatedCriterita(isolatedCriteria);
		        break;
		      }  
		      
		      if (pUserId > 0) {
		    	  DBCriteria isolatedCriteria = new DBCriteria();
		    	  DBCriteria or = new DBCriteria();
		          isolatedCriteria.addEqualTo(OrderGuideDataAccess.USER_ID, pUserId);
		          or.addIsNull(OrderGuideDataAccess.USER_ID);
		          isolatedCriteria.addOrCriteria(or);
		    	  crit.addIsolatedCriterita(isolatedCriteria);
		      }	 

		      crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
		      ogv = getCollection(crit);

		    } catch (Exception e) {
		      throw processException(e);
		    }

		    return fillOrderGuideDescVector(ogv);
		  }

  /**
   *  Fetch the order guides for an account.
   *
   *@param  pAccountId           an <code>int</code> value
   *@param  pTypeId              Order guid type id
   *@return                      an <code>OrderGuideDescDataVector</code>
   *      value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector getCollectionByAccount(int pAccountId,
    int pTypeId) throws RemoteException {

    OrderGuideDataVector orderGuides = new OrderGuideDataVector();
    Connection conn = null;

    try {
      conn = getConnection();

      APIAccess apiAccess = getAPIAccess();
      CatalogInformation catalogAPI = apiAccess.getCatalogInformationAPI();
      CatalogDataVector catalogs = catalogAPI.getCatalogsCollectionByBusEntity(
        pAccountId);

      // Get the order guides for the catalogs.
      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogs.iterator();

      while (catalogI.hasNext()) {

        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      if (catalogIds.size() > 0) {

        DBCriteria crit = new DBCriteria();
        crit = addTypeCriterion(crit, pTypeId);
        crit.addOneOf(OrderGuideDataAccess.CATALOG_ID, catalogIds);
        crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
        orderGuides = OrderGuideDataAccess.select(conn, crit);
      }
    } catch (Exception e) {
      throw new RemoteException("getCollectionByAccount: " +
                                e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return fillOrderGuideDescVector(orderGuides);
  }

  /**
   *  Fetch the order guides matching the specified name for an account
   *
   *@param  pName                a String value with order guide name or
   *      pattern
   *@param  pAccountId           an <code>int</code> value
   *@param  pMatch               one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
   *      EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
   *      CONTAINS_IGNORE_CASE.
   *@param  pTypeId              Order guid type id
   *@return                      an <code>OrderGuideDescDataVector</code>
   *      value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideDescDataVector getCollectionByNameAndAccount(String pName,
    int pAccountId,
    int pMatch,
    int pTypeId) throws RemoteException {

    OrderGuideDataVector orderGuides = new OrderGuideDataVector();
    Connection conn = null;

    try {
      conn = getConnection();

      APIAccess apiAccess = getAPIAccess();
      CatalogInformation catalogAPI = apiAccess.getCatalogInformationAPI();
      CatalogDataVector catalogs = catalogAPI.getCatalogsCollectionByBusEntity(
        pAccountId);

      // Get the order guides for the catalogs.
      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogs.iterator();

      while (catalogI.hasNext()) {

        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      if (catalogIds.size() > 0) {

        DBCriteria crit = new DBCriteria();
        crit.addOneOf(OrderGuideDataAccess.CATALOG_ID, catalogIds);
        crit = addTypeCriterion(crit, pTypeId);
        crit = addShortDescCriterion(crit, pMatch, pName);
        crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
        orderGuides = OrderGuideDataAccess.select(conn, crit);
      }
    } catch (Exception e) {
      throw new RemoteException("getCollectionByAccount: " +
                                e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return fillOrderGuideDescVector(orderGuides);
  }

  /**
   *  Gets the vector or order guide templates available to a particular
   *  user.
   *
   *@param  pUserId
   *@return                      OrderGuideDescDataVector
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideDescDataVector getTemplateCollectionByUser(int pUserId) throws RemoteException {

    OrderGuideDataVector ogv = new OrderGuideDataVector();
    Connection conn = null;

    try {
      conn = getConnection();

      Statement stmt = conn.createStatement();
      String query = "SELECT DISTINCT " +
                     OrderGuideDataAccess.ORDER_GUIDE_ID + " FROM " +
                     OrderGuideDataAccess.CLW_ORDER_GUIDE + " WHERE " +
                     OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD +
                     " =  \'" +
                     RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE +
                     "'" + " AND " + OrderGuideDataAccess.CATALOG_ID +
                     " IN " + " (" + " SELECT DISTINCT " +
                     CatalogAssocDataAccess.CATALOG_ID + " FROM " +
                     CatalogAssocDataAccess.CLW_CATALOG_ASSOC +
                     " WHERE " + CatalogAssocDataAccess.BUS_ENTITY_ID +
                     " IN " + " (" + " SELECT " +
                     UserAssocDataAccess.BUS_ENTITY_ID + " FROM " +
                     UserAssocDataAccess.CLW_USER_ASSOC + " WHERE " +
                     UserAssocDataAccess.USER_ASSOC_CD + " = '" +
                     RefCodeNames.USER_ASSOC_CD.SITE + "'" + " AND " +
                     UserAssocDataAccess.USER_ID + " = " + pUserId +
                     ")" + " AND " +
                     CatalogAssocDataAccess.CATALOG_ASSOC_CD + " = " +
                     "'" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE +
                     "'" + ")";
      ResultSet rs = stmt.executeQuery(query);
      IdVector oids = new IdVector();

      while (rs.next()) {

        int id = rs.getInt(1);
        oids.add(new Integer(id));
      }

      DBCriteria crit = new DBCriteria();
      crit.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_ID, oids);
      ogv = OrderGuideDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getTemplateCollectionByUser: " +
                                e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return fillOrderGuideDescVector(ogv);
  }

  /**
   *  Description of the Method
   *
   *@param  pOrderGuideStructureId     Description of Parameter
   *@param  pNewQty                    Description of Parameter
   *@param  pModUserName               Description of Parameter
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public void updateQuantity(int pOrderGuideStructureId, int pNewQty,
                             String pModUserName) throws RemoteException, DataNotFoundException {

    Connection conn = null;

    try {
      conn = getConnection();

      OrderGuideStructureData o = null;

      try {
        o = OrderGuideStructureDataAccess.select(conn,
                                                 pOrderGuideStructureId);
      } catch (Exception e) {
        logError("updateQuantity: " + e.getMessage());
      }

      if (o != null) {
        o.setQuantity(pNewQty);
        o.setModBy(pModUserName);
        OrderGuideStructureDataAccess.update(conn, o);
      }
    } catch (Exception e) {
      throw new RemoteException("updateQuantity: " + e.getMessage());
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
   *  Description of the Method
   *
   *@param  pOrderGuideStructureId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeItem(int pOrderGuideStructureId) throws RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();
      OrderGuideStructureDataAccess.remove(conn, pOrderGuideStructureId);
    } catch (Exception e) {
      throw new RemoteException("removeItem: " + e.getMessage());
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
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(int pCatalogId, int pItemId) throws RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();
      removeCatalogItemFromOrderGuide(conn, pCatalogId, pItemId);
    } catch (Exception e) {
      throw new RemoteException("removeCatalogItemFromOrderGuide: " +
                                e.getMessage());
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
   *  Description of the Method
   *
   *@param pCon DB connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(Connection pCon, int pCatalogId, int pItemId) throws RemoteException,
    Exception {

    DBCriteria crit = new DBCriteria();
    IdVector orderGuideIdV = new IdVector();
    crit.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
    // crit.addEqualTo (OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
    orderGuideIdV = OrderGuideDataAccess.selectIdOnly
                    (pCon, OrderGuideDataAccess.ORDER_GUIDE_ID, crit);

    if (null != orderGuideIdV && 0 < orderGuideIdV.size()) {
      crit = new DBCriteria();
      crit.addEqualTo(OrderGuideStructureDataAccess.ITEM_ID, pItemId);
      crit.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,
                    orderGuideIdV);
      OrderGuideStructureDataAccess.remove(pCon, crit);
    }

    return;
  }

  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(int pCatalogId,
                                              IdVector pItemIdV) throws RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();
      removeCatalogItemFromOrderGuide(conn, pCatalogId, pItemIdV);

    } catch (Exception e) {
      throw new RemoteException("removeCatalogItemFromOrderGuide(1): " +
                                e.getMessage());
    } finally {

      closeConnection(conn);
    }

    return;
  }

  /**
   *  Description of the Method
   *
   *@parm pCon DB connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(
    Connection pCon, int pCatalogId, IdVector pItemIdV) throws RemoteException, Exception {

    Statement stmt = pCon.createStatement();
    for (int idx = 0; idx < pItemIdV.size(); idx++) {
      Integer itemid = (Integer) pItemIdV.get(idx);
      String delsql = "delete from " +
                      OrderGuideStructureDataAccess.CLW_ORDER_GUIDE_STRUCTURE +
                      " os where  os.item_id = " + itemid.intValue() +
                      " and os.order_guide_id in ( " +
                      "   select distinct og.order_guide_id from " +
                      OrderGuideDataAccess.CLW_ORDER_GUIDE + " og " +
                      "   where og.catalog_id = " + pCatalogId + " ) ";
      logDebug("SQL: " + delsql);
      stmt.executeUpdate(delsql);
    }
    stmt.close();

    return;
  }

  /**
   *  Removes items from order guides and shopping carts
   *
   *@parm pCon DB connection
   *@param  pCatalogIdV Vector of catalog ids
   *@param  pItemIdV  Vectoir of item ids
   *@param pUser User login name
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromOrderGuide(
    Connection pCon, IdVector pCatalogIdV, IdVector pItemIdV, String pUser) throws RemoteException, Exception {
    DBCriteria dbc = new DBCriteria();
    dbc.addOneOf(OrderGuideDataAccess.CATALOG_ID, pCatalogIdV);
    LinkedList ogExclTypes = new LinkedList();
    ogExclTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.DED_CLOSET);
    ogExclTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.MSDS_CLOSET);
    ogExclTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SPEC_CLOSET);
    dbc.addNotOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, ogExclTypes);
    String ogRequest =
      OrderGuideDataAccess.getSqlSelectIdOnly(OrderGuideDataAccess.ORDER_GUIDE_ID, dbc);

    dbc = new DBCriteria();
    dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, ogRequest);
    dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, pItemIdV);
    OrderGuideStructureDataVector ogsDV =
      OrderGuideStructureDataAccess.select(pCon, dbc);
    for (Iterator iter = ogsDV.iterator(); iter.hasNext(); ) {
      OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();
      OrderGuideStructDelData ogsdD = OrderGuideStructDelData.createValue();
      ogsdD.setOrderGuideId(ogsD.getOrderGuideId());
      ogsdD.setItemId(ogsD.getItemId());
      ogsdD.setCategoryItemId(ogsD.getCategoryItemId());
      ogsdD.setQuantity(ogsD.getQuantity());
      ogsdD.setAddBy(pUser);
      ogsdD.setModBy(pUser);
      OrderGuideStructDelDataAccess.insert(pCon, ogsdD);
    }
    OrderGuideStructureDataAccess.remove(pCon, dbc);
    return;
  }

  /**
   *  Create and order guide from a catalog. The order guide will then contain
   *  all the items in the catalog with a quantity of zero.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The
   *      catalog id and the add by values must be set.
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromCatalog(OrderGuideData pOrderGuideData) throws RemoteException,
    DataNotFoundException {

    OrderGuideInfoData ogid = OrderGuideInfoData.createValue();

    // Get the items in the catalog.
    IdVector itemids;

    try {

      APIAccess apiAccess = getAPIAccess();
      CatalogInformation cati = apiAccess.getCatalogInformationAPI();

      // Get all the items.
      itemids = cati.searchCatalogProducts(pOrderGuideData.getCatalogId());
    } catch (Exception e) {

      String msg = "createFromCatalog: " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    }

    ogid.setOrderGuideData(addOrderGuide(pOrderGuideData));

    int logid = ogid.getOrderGuideData().getOrderGuideId();

    // Add the items.
    OrderGuideItemDescDataVector ov = new OrderGuideItemDescDataVector();

    for (int i = 0; i < itemids.size(); i++) {

      Integer itemid = (Integer) itemids.get(i);
      OrderGuideItemDescData ogsdata = addItem(logid, itemid.intValue(),
                                               0);
      ov.add(ogsdata);
    }

    ogid.setOrderGuideItems(ov);

    return ogid;
  }

  /**
   *  Create and order guide from a contract. The order guide will then
   *  contain all the items in the contract with a quantity of zero.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The
   *      catalog id and the add by values must be set.
   *@param  pContractId
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromContract(OrderGuideData pOrderGuideData,
                                               int pContractId) throws RemoteException,
    DataNotFoundException {

    OrderGuideInfoData ogid = OrderGuideInfoData.createValue();
    ContractData condata = null;

    // Get the items in the contract.
    ContractItemDataVector conitems;

    try {

      APIAccess apiAccess = getAPIAccess();
      Contract conti = apiAccess.getContractAPI();

      // Get all the items.
      conitems = conti.getItems(pContractId);
      condata = conti.getContract(pContractId);
    } catch (Exception e) {

      String msg = "createFromContract: " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    }

    pOrderGuideData.setCatalogId(condata.getCatalogId());
    ogid.setOrderGuideData(addOrderGuide(pOrderGuideData));

    int logid = ogid.getOrderGuideData().getOrderGuideId();

    // Add the items.
    OrderGuideItemDescDataVector ov = new OrderGuideItemDescDataVector();

    for (int i = 0; i < conitems.size(); i++) {

      ContractItemData item = (ContractItemData) conitems.get(i);
      OrderGuideItemDescData ogsdata = addItem(logid, item.getItemId(),
                                               0);
      ov.add(ogsdata);
    }

    ogid.setOrderGuideItems(ov);

    return ogid;
  }

  /**
   *  Create an order guide from another order guide. The order guide created
   *  will then contain all the items in the originating order guide.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The order
   *      guide id must be set to the original order guide and the add by must
   *      be set to the username of the user requesting the new order guide.
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromOrderGuide(OrderGuideData pOrderGuideData) throws RemoteException,
    DataNotFoundException {
    return createFromOrderGuide(pOrderGuideData, "UNKNOWN");
  }

  /**
   *  Create an order guide from another order guide. The order guide created
   *  will then contain all the items in the originating order guide.
   *
   *@param  pOrderGuideData            OrderGuideData value object. The order
   *      guide id must be set to the original order guide and the add by must
   *      be set to the username of the user requesting the new order guide.
   * @param pAdmin name of the user who requested the action
   *@return                            OrderGuideInfoData
   *@exception  RemoteException
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideInfoData createFromOrderGuide(OrderGuideData pOrderGuideData, String pAdmin) throws RemoteException,
    DataNotFoundException {

    OrderGuideInfoData ogid = OrderGuideInfoData.createValue();

    // Get the old order guide.
    OrderGuideInfoData oldog = getOrderGuideInfo(pOrderGuideData.getOrderGuideId());

    // Get the items in the old order guide.
    OrderGuideItemDescDataVector ov = oldog.getOrderGuideItems();
    pOrderGuideData.setCatalogId(oldog.getOrderGuideData().getCatalogId());
    pOrderGuideData.setAddBy(pAdmin);
    pOrderGuideData.setModBy(pAdmin);
    ogid.setOrderGuideData(addOrderGuide(pOrderGuideData));

    int logid = ogid.getOrderGuideData().getOrderGuideId();

    // Add the items.
    ov = addItems(logid, ov, pAdmin);
    ogid.setOrderGuideItems(ov);

    return ogid;
  }

  /**
   *  Standard EJB method. This one is empty.
   *
   *@exception  CreateException  Description of Exception
   *@exception  RemoteException  Description of Exception
   */
  public void ejbCreate() throws CreateException, RemoteException {
  }

  /**
   *  Adds an OrderGuide entry.
   *
   *@param  pOrderGuide          The OrderGuide to be created.
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideData addOrderGuide(OrderGuideData pOrderGuide) throws RemoteException {

    // Set the id to 0 to ensure that this order guide
    // is added and not updated.
    pOrderGuide.setOrderGuideId(0);

    return updateOrderGuide(pOrderGuide);
  }

  /**
   *  Update the information describbing an order guide.
   *
   *@param  pOrderGuide          an <code>OrderGuideData</code> value
   *@return                      an <code>OrderGuideData</code> value
   *@exception  RemoteException  if an error occurs
   */
  public OrderGuideData updateOrderGuide(OrderGuideData pOrderGuide) throws RemoteException {

    String dataErrors = validateOrderGuideData(pOrderGuide);

    if (dataErrors != null) {
      throw new RemoteException(this.getClass().getName() + " : " +
                                dataErrors);
    }

    Connection conn = null;

    try {
      conn = getConnection();

      if (pOrderGuide.getOrderGuideId() == 0) {
        OrderGuideDataAccess.insert(conn, pOrderGuide);
      } else {
        OrderGuideDataAccess.update(conn, pOrderGuide);
      }
    } catch (Exception e) {
      throw new RemoteException("updateOrderGuide: " + e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return pOrderGuide;
  }

  /**
   *  Delete an order guide from the db.
   *
   *@param  pOrderGuideId
   *@exception  RemoteException
   */
  public void removeOrderGuide(int pOrderGuideId) throws RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();

      OrderGuideData ogd = OrderGuideDataAccess.select(conn,
        pOrderGuideId);
      ogd.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.DELETED);
      OrderGuideDataAccess.update(conn, ogd);
    } catch (Exception e) {
      throw new RemoteException("removeOrderGuide: " + e.getMessage());
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
   *  Add an item to the order guide.
   *
   *@param  pOrderGuideId              an <code>int</code> value identifying
   *      the order guide db entry.
   *@param  pItemIds    set of item ids
   *@param  pItems   set of item quantities corresponging to item ids
   *@return      an <code>OrderGuideItemDescData</code>
   *      value
   *@exception  RemoteException        if an error occurs
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideItemDescDataVector addItems(int pOrderGuideId,
                                               OrderGuideItemDescDataVector pItems, String pAdmin) throws RemoteException,
    DataNotFoundException {

    Connection conn = null;
    try {
      conn = getConnection();
      IdVector itemIds = new IdVector();
      for (int ii = 0; ii < pItems.size(); ii++) {
        OrderGuideItemDescData ogidD = (OrderGuideItemDescData) pItems.get(ii);
        int itemId = ogidD.getItemId();
        itemIds.add(new Integer(itemId));
      }
      //Get already assinged items
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,
                     pOrderGuideId);
      dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemIds);
      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);
      OrderGuideStructureDataVector ogsDV = OrderGuideStructureDataAccess.select(conn, dbc);

      for (int ii = 0; ii < pItems.size(); ii++) {
        OrderGuideItemDescData ogidD = (OrderGuideItemDescData) pItems.get(ii);
        int itemId = ogidD.getItemId();
        itemIds.add(new Integer(itemId));
        int jj = 0;
        for (; jj < ogsDV.size(); jj++) {
          OrderGuideStructureData ogsD = (OrderGuideStructureData) ogsDV.get(jj);
          int existItemId = ogsD.getItemId();
          if (existItemId == itemId) {
            ogsD.setQuantity(ogidD.getQuantity());
            ogsD.setModBy(pAdmin);
            OrderGuideStructureDataAccess.update(conn, ogsD);
            ogidD.setOrderGuideStructureId(ogsD.getOrderGuideStructureId());
            break;
          }
        }
        if (jj >= ogsDV.size()) {
          OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();
          ogsD.setOrderGuideId(pOrderGuideId);
          ogsD.setItemId(itemId);
          ogsD.setQuantity(ogidD.getQuantity());
          ogsD.setAddBy(pAdmin);
          ogsD.setModBy(pAdmin);
          ogsD = OrderGuideStructureDataAccess.insert(conn, ogsD);
          ogidD.setOrderGuideStructureId(ogsD.getOrderGuideStructureId());
        }
      }
    } catch (Exception e) {
      throw new RemoteException("addItem: " + e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }
    return pItems;
  }

  /**
   *  Add an item to the order guide.
   *
   *@param  pOrderGuideId              an <code>int</code> value identifying
   *      the order guide db entry.
   *@param  pItemId                    an <code>int</code> value
   *@param  pItemQty                   an <code>int</code> value, defines the
   *      number of items in the order guide entry.
   *@return                            an <code>OrderGuideItemDescData</code>
   *      value
   *@exception  RemoteException        if an error occurs
   *@exception  DataNotFoundException  Description of Exception
   */
  public OrderGuideItemDescData addItem(int pOrderGuideId, int pItemId,
                                        int pItemQty) throws RemoteException,
    DataNotFoundException {
  return addItem( pOrderGuideId,  pItemId,  pItemQty, null);
}
  public OrderGuideItemDescData addItem(int pOrderGuideId, int pItemId,
                                        int pItemQty, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,
    DataNotFoundException {

    Connection conn = null;
    OrderGuideStructureData o;

    try {
      conn = getConnection();

      // Check to see if the item is already there.
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,
                      pOrderGuideId);
      crit.addEqualTo(OrderGuideStructureDataAccess.ITEM_ID, pItemId);

      OrderGuideStructureDataVector ov = OrderGuideStructureDataAccess.select(
        conn, crit);

      if (ov.size() > 0) {
        o = (OrderGuideStructureData) ov.get(0);
      } else {
        o = OrderGuideStructureData.createValue();
        o.setOrderGuideId(pOrderGuideId);
        o.setItemId(pItemId);
        o.setQuantity(pItemQty);
        o = OrderGuideStructureDataAccess.insert(conn, o);
      }
      // Need the catalog id to get product data.
      OrderGuideData ogd = getOrderGuide(pOrderGuideId);
      OrderGuideItemDescData n = fillProductDesc
                                 (conn, ogd.getCatalogId(), pItemId, pCategToCostCenterView);
      n.setOrderGuideStructureId(o.getOrderGuideStructureId());
      n.setOrderGuideId(o.getOrderGuideId());
      n.setItemId(o.getItemId());
      n.setQuantity(o.getQuantity());

      return n;
    } catch (Exception e) {
      throw new RemoteException("addItem: " + e.getMessage());
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
   *  Helper function to get the items attached to an order guide.
   *
   *@param  pOrderGuideData
   *@return                            OrderGuideItemDescDataVector
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  private OrderGuideItemDescDataVector getItems(OrderGuideData pOrderGuideData) throws RemoteException,
    DataNotFoundException {

    Connection conn = null;
    OrderGuideItemDescDataVector ov = getOrderGuideItemsDesc(pOrderGuideData.getCatalogId(),
      pOrderGuideData.getOrderGuideId());

    return ov;
  }

  /**
   *  Get all the order guides
   *
   *@param  pSearchType
   *@param  pId
   *@return                      a <code>OrderGuideDataVector</code> with all
   *      the order guides found.
   *@exception  RemoteException  if an error occurs
   */
  private OrderGuideDataVector getCollection(String pOrderGuideType,
                                             String pIdType, int pId) throws RemoteException {

    Connection conn = null;
    OrderGuideDataVector ogdv = new OrderGuideDataVector();

    try {
      conn = getConnection();

      DBCriteria crit = new DBCriteria();

      if (pOrderGuideType != null) {
        crit.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
                        pOrderGuideType);
      }

      if (pIdType == null) {
        crit.addGreaterOrEqual(OrderGuideDataAccess.ORDER_GUIDE_ID, 0);
      } else {

        if (pIdType.equals("CatalogId")) {
          crit.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pId);
        } else if (pIdType.equals("UserId")) {
          crit.addEqualTo(OrderGuideDataAccess.USER_ID, pId);
        }
      }

      crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
      ogdv = OrderGuideDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getCollection: " + e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return ogdv;
  }

  /**
   *  Get all the order guides
   *
   *@param  pSearchType
   *@param  pId
   *@return                      a <code>OrderGuideDataVector</code> with all
   *      the order guides found.
   *@exception  RemoteException  if an error occurs
   */
   
  private OrderGuideDataVector getCollection(int pOrderGuideTypeId,
                                             String pIdType, int pId) throws RemoteException {

    Connection conn = null;
    OrderGuideDataVector ogdv = new OrderGuideDataVector();

    try {
      conn = getConnection();

      DBCriteria crit = new DBCriteria();
      crit = addTypeCriterion(crit, pOrderGuideTypeId);
 
      if (pIdType == null) {
        crit.addGreaterOrEqual(OrderGuideDataAccess.ORDER_GUIDE_ID, 0);
      } else {

        if (pIdType.equals("CatalogId")) {
          crit.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pId);
        } else if (pIdType.equals("UserId")) {
          crit.addEqualTo(OrderGuideDataAccess.USER_ID, pId);
        }
      }

      crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
      ogdv = OrderGuideDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getCollection: " + e.getMessage());
    } finally {

      try {

        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return ogdv;
  }

  private OrderGuideDataVector getCollection(DBCriteria crit) throws RemoteException {

	Connection conn = null;
	OrderGuideDataVector ogdv = new OrderGuideDataVector();
	
	try {
		conn = getConnection();
		ogdv = OrderGuideDataAccess.select(conn, crit);
	} catch (Exception e) {
		throw new RemoteException("getCollection: " + e.getMessage());
	} finally {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
	}
}

return ogdv;
}

  /**
   *  Description of the Method
   *
   *@param  pOrderGuideData  Description of Parameter
   *@return                  Description of the Returned Value
   */
  private OrderGuideDescData fillOrderGuideDescData(OrderGuideData pOrderGuideData) {

    OrderGuideDescData ogDesc = OrderGuideDescData.createValue();
    CatalogInformation cati = null;

    try {
      ogDesc.setOrderGuideId(pOrderGuideData.getOrderGuideId());
      ogDesc.setOrderGuideName(pOrderGuideData.getShortDesc());
      ogDesc.setOrderGuideTypeCd(pOrderGuideData.getOrderGuideTypeCd());
      ogDesc.setCatalogId(pOrderGuideData.getCatalogId());

      APIAccess apiAccess = getAPIAccess();
      cati = apiAccess.getCatalogInformationAPI();

      CatalogData catd = cati.getCatalog(pOrderGuideData.getCatalogId());
      ogDesc.setCatalogName(catd.getShortDesc());
      ogDesc.setStatus(catd.getCatalogStatusCd());
    } catch (Exception e) {

      String msg = "fillOrderGuideDescData: " + e.getMessage();
      logError(msg);
    }

    return ogDesc;
  }

  /**
   *  Description of the Method
   *
   *@param  pOgv  Description of Parameter
   *@return       Description of the Returned Value
   */
  private OrderGuideDescDataVector fillOrderGuideDescVector(OrderGuideDataVector pOgv) {

    OrderGuideDescDataVector ogDescv = new OrderGuideDescDataVector();

    for (int i = 0; i < pOgv.size(); i++) {

      OrderGuideData ogd = (OrderGuideData) pOgv.get(i);
      OrderGuideDescData ogDesc = fillOrderGuideDescData(ogd);
      ogDescv.add(ogDesc);
    }

    return ogDescv;
  }

  /**
   *  Sets the ProductDesc attributes for the given catalog item.
   *
   *@param  pItemId  The new ProductDesc value
   *@param  pCatId   Description of Parameter
   *@return          Description of the Returned Value
   */
  private OrderGuideItemDescData fillProductDesc
    (Connection conn, int pCatId, int pItemId, AccCategoryToCostCenterView pCategToCostCenterView) {

    try {

      ProductDAO pdao = new ProductDAO(conn, pItemId);
      pdao.updateCatalogInfo(conn, pCatId, pCategToCostCenterView);
      OrderGuideItemDescData n = OrderGuideItemDescData.createValue();
      ProductDataVector pdv = pdao.getResultVector();
      if (pdv == null || pdv.size() == 0) {
        logError("no product info available for, " +
                 " pItemId=" + pItemId +
                 " pCatId=" + pCatId
          );
        return null;
      }

      ProductData p = (ProductData) pdv.get(0);
      n.setCwSKU(String.valueOf(p.getSkuNum()));
      n.setShortDesc(p.getShortDesc());
      n.setPackDesc(p.getPack());
      n.setSizeDesc(p.getSize());
      n.setUomDesc(p.getUom());
      n.setColorDesc(p.getColor());
      n.setManufacturerCd(p.getManufacturer().getShortDesc());
      n.setManufacturerSKU(p.getManufacturerSku());

      CatalogCategoryDataVector catdv = p.getCatalogCategories();
      String categories = "";

      for (int i1 = 0; i1 < catdv.size(); i1++) {

        CatalogCategoryData catdata = (CatalogCategoryData) catdv.get(
          i1);
        String thiscat = catdata.getCatalogCategoryShortDesc() +
                         " ";
        categories += thiscat;
      }

      n.setCategoryDesc(categories);
      n.setPrice(new java.math.BigDecimal(p.getListPrice()));

      return n;
    } catch (Exception e) {

      String msg = "fillProductDesc: " + e.getMessage();
      logError(msg);
    }

    return null;
  }

    public OrderGuideStructureDataVector getOrderGuideStructure(int pOrderGuideId) throws RemoteException,
	    DataNotFoundException {
	    OrderGuideStructureDataVector v = new OrderGuideStructureDataVector();
		Connection con = null;
		try {
		    con = getConnection();
		      //Pick up order guide items
		    DBCriteria dbc = new DBCriteria();
		    dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideId);
		    dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);
		    v = OrderGuideStructureDataAccess.select(con, dbc);
		} catch (Exception e) {
		    logError(" OrderGuide error: " + e);
		    e.printStackTrace();
		    throw new RemoteException("getOrderGuideStructure: " + e);
		} finally {
		    closeConnection(con);
		}
	    return v;
    }


  /**
   *  Gets order guide item descriptions
   *
   *@param  pCatalogId              the catalog identifier
   *@param  pOrderGuideId           the order guide identifier
   *@return                         collection of OrderGuideItemDescData objects
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideItemDescDataVector getOrderGuideItemsDesc(int pCatalogId,
    int pOrderGuideId) throws RemoteException,
    DataNotFoundException {

    OrderGuideItemDescDataVector v = new OrderGuideItemDescDataVector();
    Connection con = null;

    try {
      con = getConnection();

      //Pick up order guide items
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,
                     pOrderGuideId);

      String ogItemReq = OrderGuideStructureDataAccess.getSqlSelectIdOnly(
        OrderGuideStructureDataAccess.ITEM_ID,
        dbc);
      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

      OrderGuideStructureDataVector ogItemDV = OrderGuideStructureDataAccess.select(
        con, dbc);

      // get the items from the catalog
      // make sure all items in the contract are in the catalog
      // if not, remove it from list and don't show it
      DBCriteria dbc2 = new DBCriteria();
      dbc2.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      dbc2.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

      CatalogStructureDataVector catItemDV = CatalogStructureDataAccess.select(
        con, dbc2);

      for (int i = 0; i < ogItemDV.size(); i++) {

        OrderGuideStructureData og = (OrderGuideStructureData) ogItemDV.get(
          i);
        boolean found = false;

        for (int j = 0; j < catItemDV.size() && found == false; j++) {

          CatalogStructureData cat = (CatalogStructureData) catItemDV.get(
            j);

          if (cat.getItemId() == og.getItemId()) {
            found = true;
          }
        }

        if (!found) {
          logError("  Order Guide item: " + og +
                   "not in catalog id: " + pCatalogId);
          ogItemDV.remove(i);
        }
      }

      //Pick up items information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, ogItemReq);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      //Pick up item meta information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, ogItemReq);

      LinkedList itemPropNames = new LinkedList();
      itemPropNames.add("UOM");
      itemPropNames.add("PACK");
      itemPropNames.add("COLOR");
      itemPropNames.add("SIZE");
      itemPropNames.add("LIST_PRICE");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);

      //Pick up item manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, ogItemReq);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

      ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(
        con, dbc);
      IdVector itemMfgIds = new IdVector();

      for (int ii = 0; ii < itemMappingDV.size(); ii++) {

        ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
        itemMfgIds.add(new Integer(imD.getBusEntityId()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);

      BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con,
        dbc);

      //Pick up item categories
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, ogItemReq);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,
        dbc);
      IdVector itemCategoryIds = new IdVector();

      for (int ii = 0; ii < itemAssocDV.size(); ii++) {

        ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(ii);
        itemCategoryIds.add(new Integer(iaD.getItem2Id()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      ItemDataVector categories = ItemDataAccess.select(con, dbc);

      //Combine altogether
      for (int ii = 0; ii < ogItemDV.size(); ii++) {

        int jj = 0;
        int ff = 0;
        int mm = 0;
        int uu = 0;
        int cc = 0;
        OrderGuideStructureData ogsD = (OrderGuideStructureData) ogItemDV.get(
          ii);
        int itemId = ogsD.getItemId();
        OrderGuideItemDescData ogidD = OrderGuideItemDescData.createValue();
        ogidD.setOrderGuideId(pOrderGuideId);
        ogidD.setOrderGuideStructureId(ogsD.getOrderGuideStructureId());
        ogidD.setQuantity(ogsD.getQuantity());
        ogidD.setItemId(ogsD.getItemId());

        //Item data
        while (jj < itemDV.size()) {

          ItemData iD = (ItemData) itemDV.get(jj);

          if (iD.getItemId() == itemId) {
            ogidD.setCwSKU("" + iD.getSkuNum());
            ogidD.setShortDesc(iD.getShortDesc());

            break;
          }

          jj++;
        }

        //Meta data
        while (uu < itemPropDV.size()) {

          ItemMetaData imD = (ItemMetaData) itemPropDV.get(uu);

          if (imD.getItemId() == itemId) {

            if ("UOM".equals(imD.getNameValue())) {
              ogidD.setUomDesc(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              ogidD.setSizeDesc(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              ogidD.setPackDesc(imD.getValue());
            } else if ("COLOR".equals(imD.getNameValue())) {
              ogidD.setColorDesc(imD.getValue());
            } else if ("LIST_PRICE".equals(imD.getNameValue())) {

              String priceS = imD.getValue();
              BigDecimal price = new BigDecimal(0);

              try {
                price = new BigDecimal(priceS);
              } catch (Exception exc) {
              }

              ogidD.setPrice(price);
            }
          }

          uu++;
        }

        //Manufacturer data
        for (; ff < itemMappingDV.size(); ff++) {

          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(
            ff);

          if (imD.getItemId() == itemId) {

            int beId = imD.getBusEntityId();
            ogidD.setManufacturerSKU(imD.getItemNum());

            for (int bb = 0; bb < itemMfgDV.size(); bb++) {

              BusEntityData beD = (BusEntityData) itemMfgDV.get(
                bb);

              if (beD.getBusEntityId() == beId) {
                ogidD.setManufacturerCd(beD.getShortDesc());

                break;
              }
            }

            break;
          }
        }

        //Categories
        String categoryString = "";

        for (; cc < itemAssocDV.size(); cc++) {

          ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(cc);

          if (iaD.getItem1Id() == itemId) {

            int catId = iaD.getItem2Id();

            for (int rr = 0; rr < categories.size(); rr++) {

              ItemData iD = (ItemData) categories.get(rr);

              if (catId == iD.getItemId()) {
                categoryString += iD.getShortDesc() + " ";

                break;
              }
            }
          }
        }

        ogidD.setCategoryDesc(categoryString);

        //add to vector
        v.add(ogidD);
      }
    } catch (Exception e) {
      logError(" OrderGuide error: " + e);
      e.printStackTrace();
      throw new RemoteException("getContractItemsDesc: " + e);
    } finally {
      closeConnection(con);
    }

    return v;
  }

  /**
   *  Gets order guide item descriptions for all catalog items
   *
   *@param  pCatalogId              the catalog identifier
   *@param  pOrderGuideId           the order guide identifier
   *@return                         collection of OrderGuideItemDescData objects
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideItemDescDataVector getEstimatorItems(int pCatalogId,
    int pOrderGuideId) throws RemoteException, DataNotFoundException {
    OrderGuideItemDescDataVector orderGuiderItemDescDV = new OrderGuideItemDescDataVector();
    Connection con = null;

    try {
      con = getConnection();

      //Pick up order guide items
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,
                     pOrderGuideId);

      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

      OrderGuideStructureDataVector ogItemDV = OrderGuideStructureDataAccess.select(
        con, dbc);

      //Pick up contract items
      dbc = new DBCriteria();
      dbc.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                     RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
      IdVector contractIdV = ContractDataAccess.selectIdOnly(con,
        ContractDataAccess.CONTRACT_ID, dbc);
      ContractItemDataVector contractItemDV = null;
      if (contractIdV.size() == 0) {
        contractItemDV = new ContractItemDataVector();
      } else {
        Integer contractIdI = (Integer) contractIdV.get(0);
        dbc = new DBCriteria();
        dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contractIdI);
        dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
        contractItemDV = ContractItemDataAccess.select(con, dbc);
      }

      // get the items from the catalog
      // make sure all items in the contract are in the catalog
      // if not, remove it from list and don't show it
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      String csItemReq = CatalogStructureDataAccess.getSqlSelectIdOnly(
        CatalogStructureDataAccess.ITEM_ID,
        dbc);
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

      CatalogStructureDataVector catItemDV = CatalogStructureDataAccess.select(
        con, dbc);

      //Create order guide items desc
      OrderGuideStructureData wrkOgsD = null;
      ContractItemData wrkCiD = null;
      for (Iterator iter = catItemDV.iterator(),
                           iter1 = ogItemDV.iterator(),
                                   iter2 = contractItemDV.iterator();
                                           iter.hasNext(); ) {
        CatalogStructureData csD = (CatalogStructureData) iter.next();
        OrderGuideItemDescData ogidD = OrderGuideItemDescData.createValue();
        orderGuiderItemDescDV.add(ogidD);
        int itemId = csD.getItemId();
        ogidD.setItemId(itemId);
        ogidD.setQuantity(0);
        while (wrkOgsD != null || iter1.hasNext()) {
          if (wrkOgsD == null) wrkOgsD = (OrderGuideStructureData) iter1.next();
          if (itemId == wrkOgsD.getItemId()) {
            ogidD.setOrderGuideId(pOrderGuideId);
            ogidD.setOrderGuideStructureId(wrkOgsD.getOrderGuideStructureId());
            ogidD.setQuantity(wrkOgsD.getQuantity());
            wrkOgsD = null;
            break;
          }
          if (itemId > wrkOgsD.getItemId()) { //Is in order guide but doesn't exist in the catalog
            wrkOgsD = null;
            continue;
          }
          break;
        } while (wrkCiD != null || iter2.hasNext()) {
          if (wrkCiD == null) wrkCiD = (ContractItemData) iter2.next();
          if (itemId == wrkCiD.getItemId()) {
            ogidD.setPrice(wrkCiD.getAmount());
            wrkCiD = null;
            break;
          }
          if (itemId > wrkCiD.getItemId()) { //Is in contract but doesn't exist in the catalog
            wrkCiD = null;
            continue;
          }
          break;
        }
      }

      //Pick up items information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, csItemReq);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      //Pick up item meta information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, csItemReq);

      LinkedList itemPropNames = new LinkedList();
      itemPropNames.add("UOM");
      itemPropNames.add("PACK");
      itemPropNames.add("COLOR");
      itemPropNames.add("SIZE");
      itemPropNames.add("LIST_PRICE");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);

      //Pick up item manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, csItemReq);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

      ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(
        con, dbc);
      IdVector itemMfgIds = new IdVector();

      for (int ii = 0; ii < itemMappingDV.size(); ii++) {

        ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
        itemMfgIds.add(new Integer(imD.getBusEntityId()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);

      BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con,
        dbc);

      //Pick up item categories
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, csItemReq);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,
        dbc);
      IdVector itemCategoryIds = new IdVector();

      for (int ii = 0; ii < itemAssocDV.size(); ii++) {

        ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(ii);
        itemCategoryIds.add(new Integer(iaD.getItem2Id()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      ItemDataVector categories = ItemDataAccess.select(con, dbc);

      //Combine altogether
      for (int ii = 0; ii < orderGuiderItemDescDV.size(); ii++) {
        OrderGuideItemDescData ogidD =
          (OrderGuideItemDescData) orderGuiderItemDescDV.get(ii);

        int jj = 0;
        int ff = 0;
        int mm = 0;
        int uu = 0;
        int cc = 0;

        int itemId = ogidD.getItemId();

        //Item data
        while (jj < itemDV.size()) {

          ItemData iD = (ItemData) itemDV.get(jj);

          if (iD.getItemId() == itemId) {
            ogidD.setCwSKU("" + iD.getSkuNum());
            ogidD.setShortDesc(iD.getShortDesc());

            break;
          }

          jj++;
        }

        //Meta data
        while (uu < itemPropDV.size()) {

          ItemMetaData imD = (ItemMetaData) itemPropDV.get(uu);

          if (imD.getItemId() == itemId) {

            if ("UOM".equals(imD.getNameValue())) {
              ogidD.setUomDesc(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              ogidD.setSizeDesc(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              ogidD.setPackDesc(imD.getValue());
            } else if ("COLOR".equals(imD.getNameValue())) {
              ogidD.setColorDesc(imD.getValue());
            } else if ("LIST_PRICE".equals(imD.getNameValue())) {

              String priceS = imD.getValue();
              BigDecimal price = new BigDecimal(0);

              try {
                price = new BigDecimal(priceS);
              } catch (Exception exc) {
              }

              //ogidD.setPrice(price);
            }
          }

          uu++;
        }

        //Manufacturer data
        for (; ff < itemMappingDV.size(); ff++) {

          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(
            ff);

          if (imD.getItemId() == itemId) {

            int beId = imD.getBusEntityId();
            ogidD.setManufacturerSKU(imD.getItemNum());

            for (int bb = 0; bb < itemMfgDV.size(); bb++) {

              BusEntityData beD = (BusEntityData) itemMfgDV.get(
                bb);

              if (beD.getBusEntityId() == beId) {
                ogidD.setManufacturerCd(beD.getShortDesc());

                break;
              }
            }

            break;
          }
        }

        //Categories
        String categoryString = "";

        for (; cc < itemAssocDV.size(); cc++) {

          ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(cc);

          if (iaD.getItem1Id() == itemId) {

            int catId = iaD.getItem2Id();

            for (int rr = 0; rr < categories.size(); rr++) {

              ItemData iD = (ItemData) categories.get(rr);

              if (catId == iD.getItemId()) {
                categoryString += iD.getShortDesc() + " ";

                break;
              }
            }
          }
        }

        ogidD.setCategoryDesc(categoryString);

      }
    } catch (Exception e) {
      logError(" OrderGuide error: " + e);
      e.printStackTrace();
      throw new RemoteException("getContractItemsDesc: " + e);
    } finally {
      closeConnection(con);
    }

    return orderGuiderItemDescDV;
  }

  /**
   *  Gets item descriptions, which do not belong to the order guide
   *
   *@param  pCatalogId              the catalog identifier
   *@param  pOrderGuideId           the order guide identifier
   *@return                         collection of OrderGuideItemDescData objects
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public OrderGuideItemDescDataVector getItemsToAdd(int pCatalogId,
    int pOrderGuideId) throws RemoteException,
    DataNotFoundException {

    OrderGuideItemDescDataVector v = new OrderGuideItemDescDataVector();
    Connection con = null;

    try {
      con = getConnection();

      //Pick up order guide items
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,
                     pOrderGuideId);
      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

      IdVector ogItemIds = OrderGuideStructureDataAccess.selectIdOnly(
        con,
        OrderGuideStructureDataAccess.ITEM_ID,
        dbc);

      //Pick up items from the catalog
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

      IdVector catItemIds = CatalogStructureDataAccess.selectIdOnly(con,
        CatalogStructureDataAccess.ITEM_ID,
        dbc);

      //Get of items from order guide
      IdVector itemIds = new IdVector();
      int ogSize = ogItemIds.size();

      for (int ii = 0, jj = 0; ii < catItemIds.size(); ii++) {

        Integer catItemIdI = (Integer) catItemIds.get(ii);
        int catItemId = catItemIdI.intValue();

        if (jj == ogSize) {
          itemIds.add(catItemIdI);
        }

        while (jj < ogSize) {

          Integer ogItemIdI = (Integer) ogItemIds.get(jj);
          int ogItemId = ogItemIdI.intValue();

          if (ogItemId == catItemId) {
            jj++;

            break;
          } else if (ogItemId < catItemId) { //Impossible situation. System error
            throw new RemoteException("Order guide has items, which catalog does not. Catalgod id: " +
                                      pCatalogId +
                                      " Order guide id: " +
                                      pOrderGuideId +
                                      " Item id: " + ogItemId);
          } else {
            itemIds.add(catItemIdI);

            break;
          }
        }
      }

      //Pick up items information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIds);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      //Pick up item meta information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemIds);

      LinkedList itemPropNames = new LinkedList();
      itemPropNames.add("UOM");
      itemPropNames.add("PACK");
      itemPropNames.add("COLOR");
      itemPropNames.add("SIZE");
      itemPropNames.add("LIST_PRICE");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);

      //Pick up item manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIds);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

      ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(
        con, dbc);
      IdVector itemMfgIds = new IdVector();

      for (int ii = 0; ii < itemMappingDV.size(); ii++) {

        ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
        itemMfgIds.add(new Integer(imD.getBusEntityId()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);

      BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con,
        dbc);

      //Pick up item categories
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemIds);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con,
        dbc);
      IdVector itemCategoryIds = new IdVector();

      for (int ii = 0; ii < itemAssocDV.size(); ii++) {

        ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(ii);
        itemCategoryIds.add(new Integer(iaD.getItem2Id()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      ItemDataVector categories = ItemDataAccess.select(con, dbc);

      //Combine altogether
      for (int ii = 0, ff = 0, mm = 0, uu = 0, cc = 0;
        ii < itemDV.size();
        ii++) {

        OrderGuideItemDescData ogidD = OrderGuideItemDescData.createValue();
        ItemData iD = (ItemData) itemDV.get(ii);
        int itemId = iD.getItemId();
        ogidD.setOrderGuideId(pOrderGuideId);
        ogidD.setQuantity(0);
        ogidD.setItemId(itemId);
        ogidD.setCwSKU("" + iD.getSkuNum());
        ogidD.setShortDesc(iD.getShortDesc());

        //Meta data
        while (uu < itemPropDV.size()) {

          ItemMetaData imD = (ItemMetaData) itemPropDV.get(uu);

          if (imD.getItemId() == itemId) {
            uu++;

            if ("UOM".equals(imD.getNameValue())) {
              ogidD.setUomDesc(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              ogidD.setSizeDesc(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              ogidD.setPackDesc(imD.getValue());
            } else if ("COLOR".equals(imD.getNameValue())) {
              ogidD.setColorDesc(imD.getValue());
            } else if ("LIST_PRICE".equals(imD.getNameValue())) {

              String priceS = imD.getValue();
              BigDecimal price = new BigDecimal(0);

              try {
                price = new BigDecimal(priceS);
              } catch (Exception exc) {
              }

              ogidD.setPrice(price);
            }

            continue;
          }

          if (imD.getItemId() > itemId) {

            break;
          }
        }

        //Manufacturer data
        for (; ff < itemMappingDV.size(); ff++) {

          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(
            ff);

          if (imD.getItemId() > itemId) {

            break;
          }

          if (imD.getItemId() == itemId) {

            int beId = imD.getBusEntityId();
            ogidD.setManufacturerSKU(imD.getItemNum());

            for (int bb = 0; bb < itemMfgDV.size(); bb++) {

              BusEntityData beD = (BusEntityData) itemMfgDV.get(
                bb);

              if (beD.getBusEntityId() == beId) {
                ogidD.setManufacturerCd(beD.getShortDesc());

                break;
              }
            }
          }
        }

        //Categories
        String categoryString = "";

        for (; cc < itemAssocDV.size(); cc++) {

          ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(cc);

          if (iaD.getItem1Id() > itemId) {

            break;
          }

          if (iaD.getItem1Id() == itemId) {

            int catId = iaD.getItem2Id();

            for (int rr = 0; rr < categories.size(); rr++) {

              ItemData iiD = (ItemData) categories.get(rr);

              if (catId == iiD.getItemId()) {
                categoryString += iiD.getShortDesc() + " ";

                break;
              }
            }
          }
        }

        ogidD.setCategoryDesc(categoryString);

        //add to vector
        v.add(ogidD);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException("getContractItemsDesc: " + e);
    } finally {

      try {

        if (con != null) {
          con.close();
        }
      } catch (Exception e) {
      }
    }

    return v;
  }

  /**
   *  Check the fields in the object specified for validity.
   *
   *@param  pOrderGuide
   *@return              String describbing the error.
   */
  private String validateOrderGuideData(OrderGuideData pOrderGuide) {

    // Verify the data required.
    if (pOrderGuide.getShortDesc().length() == 0) {

      return new String("data validation error, OrderGuide.getShortDesc().length() == 0 returned true");
    }

    if (pOrderGuide.getCatalogId() <= 0) {

      return new String("data validation error, OrderGuide.getCatalogId()  returned a value <= 0");
    }

    return null;
  }

  /**
   * @param pCrit the DBCriteria object
   * @param pType the OrderGuide type
   * @param pName the name value to search for
   * @return the DBCriteria object with the new criteria added
   * @exception IllegalArgumentException
   */
  private DBCriteria addTypeCriterion(DBCriteria pCrit, int pTypeId) throws IllegalArgumentException {

    if (pCrit == null)
      pCrit = new DBCriteria();

    switch (pTypeId) {

    case (OrderGuide.TYPE_ALL):

      // Nothing to do for this case.
      break;

    case (OrderGuide.TYPE_BUYER):
      pCrit.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
                       RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);

      break;

    case (OrderGuide.TYPE_TEMPLATE):
      LinkedList templatetypes = new LinkedList();
      templatetypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      templatetypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE);
      pCrit.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, templatetypes);

      break;

    case (OrderGuide.TYPE_ADMIN_RELATED):
      LinkedList admtypes = new LinkedList();
      admtypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
      admtypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      pCrit.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, admtypes);

      break;

    case (OrderGuide.TYPE_BUYER_AND_TEMPLATE):

      LinkedList list = new LinkedList();
      list.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
      list.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      pCrit.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, list);

      break;

    case (OrderGuide.TYPE_BUYER_AND_SITE):

      LinkedList sitelist = new LinkedList();
      sitelist.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
      sitelist.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE);
      pCrit.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, sitelist);

      break;

    default:
      throw new IllegalArgumentException("OrderGuideBean.addTypeCriterion: " +
                                         "Unknown type id: " +
                                         pTypeId);
    }

    return pCrit;
  }

  /**
   * @param pCrit the DBCriteria object
   * @param pMatchType the OrderGuide match type (for a name comparison)
   * @param pName the name value to search for
   * @return the DBCriteria object with the new criteria added
   * @exception IllegalArgumentException
   */
  private DBCriteria addShortDescCriterion(DBCriteria pCrit, int pMatchType,
                                           String pName) throws IllegalArgumentException {

    if (pCrit == null)
      pCrit = new DBCriteria();

    switch (pMatchType) {

    case OrderGuide.EXACT_MATCH:
      pCrit.addEqualTo(OrderGuideDataAccess.SHORT_DESC, pName);

      break;

    case OrderGuide.EXACT_MATCH_IGNORE_CASE:
      pCrit.addEqualToIgnoreCase(OrderGuideDataAccess.SHORT_DESC,
                                 pName);

      break;

    case OrderGuide.BEGINS_WITH:
      pCrit.addBeginsWith(OrderGuideDataAccess.SHORT_DESC, pName);

      break;

    case OrderGuide.BEGINS_WITH_IGNORE_CASE:
      pCrit.addBeginsWithIgnoreCase(OrderGuideDataAccess.SHORT_DESC,
                                    pName);

      break;

    case OrderGuide.CONTAINS:
      pCrit.addContains(OrderGuideDataAccess.SHORT_DESC, pName);

      break;

    case OrderGuide.CONTAINS_IGNORE_CASE:
      pCrit.addContainsIgnoreCase(OrderGuideDataAccess.SHORT_DESC,
                                  pName);

      break;

    default:
      throw new IllegalArgumentException("OrderGuide.addShortDescCriterion:" +
                                         " Bad match specification" +
                                         pMatchType);
    }

    return pCrit;
  }

  public OrderGuideDescDataVector getCollectionByCatalogName(String pName,
    int pMatchType,
    int pType) throws RemoteException {

    Connection conn = null;
    OrderGuideDataVector ogdv;

    try {
      conn = getConnection();

      APIAccess apiAccess = getAPIAccess();
      CatalogInformation catalogAPI = apiAccess.getCatalogInformationAPI();
      int catMatchType = EntitySearchCriteria.NAME_CONTAINS;

      if (pMatchType == OrderGuide.BEGINS_WITH) {
        catMatchType = EntitySearchCriteria.NAME_STARTS_WITH;
      }
      EntitySearchCriteria eCrit = new EntitySearchCriteria();
      eCrit.setSearchNameType(catMatchType);
      eCrit.setSearchName(pName);
      CatalogDataVector catalogs = catalogAPI.getCatalogsByCrit(eCrit);

      // Get the order guides for the catalogs.
      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogs.iterator();

      while (catalogI.hasNext()) {

        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      DBCriteria crit = new DBCriteria();

      if (catalogIds.size() > 0) {
        crit.addOneOf(OrderGuideDataAccess.CATALOG_ID, catalogIds);
      }

      crit = addTypeCriterion(crit, pType);
      crit.addOrderBy(OrderGuideDataAccess.ORDER_GUIDE_ID);
      ogdv = OrderGuideDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getOrderGuideByCatalogName: " +
                                e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return fillOrderGuideDescVector(ogdv);
  }

  /**
   * Sends emails when items deleted from shopping carts and/or personal order guides
   * @exception RemoteException
   */
  public void sendItemDeleteEmail() throws RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      APIAccess apiAccess = getAPIAccess();
      EmailClient emailEjb = apiAccess.getEmailClientAPI();
      String defEmail = emailEjb.getDefaultEmailAddress();
      String ls = System.getProperty("line.separator");

      DBCriteria dbc = new DBCriteria();
      dbc.addIsNull(OrderGuideStructDelDataAccess.COMMENTS);

      String ogReq = OrderGuideStructDelDataAccess.
                     getSqlSelectIdOnly(OrderGuideStructDelDataAccess.ORDER_GUIDE_ID, dbc);

      String itemReq = OrderGuideStructDelDataAccess.
                       getSqlSelectIdOnly(OrderGuideStructDelDataAccess.ITEM_ID, dbc);

      dbc.addOrderBy(OrderGuideStructDelDataAccess.ORDER_GUIDE_ID);
      dbc.addOrderBy(OrderGuideStructDelDataAccess.ITEM_ID);
      OrderGuideStructDelDataVector ogsdDV =
        OrderGuideStructDelDataAccess.select(conn, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_ID, ogReq);
      dbc.addOrderBy(OrderGuideDataAccess.ORDER_GUIDE_ID);
      OrderGuideDataVector ogDV = OrderGuideDataAccess.select(conn, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemReq);
      ItemDataVector iDV = ItemDataAccess.select(conn, dbc);
      HashMap itemHM = new HashMap();
      for (Iterator iter = iDV.iterator(); iter.hasNext(); ) {
        ItemData imD = (ItemData) iter.next();
        itemHM.put(new Integer(imD.getItemId()), imD);
      }

      OrderGuideStructDelData ogsdD = null;
      OrderGuideStructDelDataVector ogItems =
        new OrderGuideStructDelDataVector();
      for (Iterator iter = ogDV.iterator(), iter1 = ogsdDV.iterator();
        iter.hasNext(); ) {
        OrderGuideData ogD = (OrderGuideData) iter.next();
        int orderGuideId = ogD.getOrderGuideId();
        String ogType = ogD.getOrderGuideTypeCd();
        boolean emailFl = false;
        boolean shoppingCartFl = false;
        if (RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE.equals(ogType)) {
          emailFl = true;
        }
        if (RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART.equals(ogType)) {
          emailFl = true;
          shoppingCartFl = true;
        }
        BusEntityData siteD = null;
        UserData userD = null;
        if (emailFl) {
          if (ogD.getBusEntityId() <= 0 || ogD.getUserId() <= 0) {
            emailFl = false;
          } else {
            try {
              siteD = BusEntityDataAccess.select(conn, ogD.getBusEntityId());
              userD = UserDataAccess.select(conn, ogD.getUserId());
              if (!RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE
                  .equals(siteD.getBusEntityStatusCd()) ||
                  !RefCodeNames.USER_STATUS_CD.ACTIVE
                  .equals(userD.getUserStatusCd())) {
                emailFl = false;
              }
              /*
                             if(!RefCodeNames.USER_TYPE_CD.MSB.equals(userD.getUserTypeCd()) &&
                 !RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userD.getUserTypeCd())) {
                emailFl = false;
                             }
               */
            } catch (DataNotFoundException exc) {
              emailFl = false;
            }
          }
        }

        while (true) {
          if (ogsdD == null) {
            if (iter1.hasNext()) {
              ogsdD = (OrderGuideStructDelData) iter1.next();
            } else {
              break;
            }
          }
          int ogId = ogsdD.getOrderGuideId();
          if (ogId < orderGuideId) {
            ogsdD.setComments("No order guide fould");
            OrderGuideStructDelDataAccess.update(conn, ogsdD);
            ogsdD = null;
          } else if (ogId == orderGuideId) {
            if (emailFl) {
              ogItems.add(ogsdD);
            } else {
              ogsdD.setComments("No email necessary");
              OrderGuideStructDelDataAccess.update(conn, ogsdD);
            }
            ogsdD = null;
          } else { //ogId>orderGuideId
            break;
          }
        }
        if (emailFl && ogItems.size() > 0) {
          //Send email
          //Generate text
          String emailText = "This Email is to inform you that";
          if (ogItems.size() == 1) {
            OrderGuideStructDelData emailOgsdD =
              (OrderGuideStructDelData) ogItems.get(0);
            ItemData iD = (ItemData) itemHM.get(new Integer(emailOgsdD.getItemId()));
            emailText += " an item " + iD.getShortDesc() + " (sku #" + iD.getSkuNum() + ")";
          } else {
            emailText += " " + ogItems.size() + " items";
          }
          emailText += " in your";
          if (shoppingCartFl) {
            emailText += " shopping cart";
          } else {
            emailText += " order guide " + ogD.getShortDesc();
          }
          emailText += " for site " + siteD.getShortDesc();
          if (ogItems.size() == 1) {
            emailText += " is";
          } else {
            emailText += " are";
          }
          emailText += " no longer available. " +
            "Please call our Customer Resource Center 800-236-7079 " +
            "for a product replacement.";
          if (ogItems.size() > 1) {
            emailText += ls + ls + "Removed items:";
            int count = 0;
            for (Iterator iter3 = ogItems.iterator(); iter3.hasNext(); ) {
              OrderGuideStructDelData emailOgsdD =
                (OrderGuideStructDelData) iter3.next();

              count++;
              ItemData iD = (ItemData) itemHM.get(new Integer(emailOgsdD.getItemId()));
              emailText += ls + count + ". " + iD.getShortDesc() + " (sku #" + iD.getSkuNum() + ")";
            }
          }
          emailText += ls + ls + ls + "Cleanwise, the leading outsource provider" +
            " for Janitorial products and services." + ls +
            "If you have any questions, please reply to this Email," +
            " or call us at 800-236-7097 from 8:00 AM to 5:00" +
            " PM EST to speak to our Customer Resource Center.";

          //Subject
          String subject = "";
          if (shoppingCartFl) {
            subject += "Shopping Cart";
          } else {
            subject += "Order Guide";
          }
          subject += " Change. Location: " + siteD.getShortDesc();

          //find address
          LinkedList emailAddrLL = new LinkedList();
          dbc = new DBCriteria();
          dbc.addEqualTo(EmailDataAccess.USER_ID, userD.getUserId());
          dbc.addOrderBy(EmailDataAccess.PRIMARY_IND);
          EmailDataVector emDV = EmailDataAccess.select(conn, dbc);
          String comment = "Sent to customer: ";
          if (emDV.size() > 0) {
            EmailData emD = (EmailData) emDV.get(0);
            String emailAddress = emD.getEmailAddress();
            if (emailAddress != null && emailAddress.indexOf('@') > 0)
              emailAddrLL.add(emailAddress);
          }
          if (emailAddrLL.size() == 0) {
            //Pickup dbm user
            comment = "Sent to Cleanwise: ";
            LinkedList dbmUsers = new LinkedList();
            for (Iterator iter3 = ogItems.iterator(); iter3.hasNext(); ) {
              OrderGuideStructDelData emailOgsdD =
                (OrderGuideStructDelData) iter3.next();
              dbmUsers.add(emailOgsdD.getAddBy());
            }
            dbc = new DBCriteria();
            dbc.addOneOf(UserDataAccess.USER_NAME, dbmUsers);
            IdVector dbmUserIdV =
              UserDataAccess.selectIdOnly(conn, UserDataAccess.USER_ID, dbc);
            for (Iterator iter3 = dbmUserIdV.iterator(); iter3.hasNext(); ) {
              Integer dbmUserIdI = (Integer) iter3.next();
              dbc = new DBCriteria();
              dbc.addEqualTo(EmailDataAccess.USER_ID, dbmUserIdI.intValue());
              dbc.addOrderBy(EmailDataAccess.PRIMARY_IND);
              emDV = EmailDataAccess.select(conn, dbc);
              if (emDV.size() > 0) {
                EmailData emD = (EmailData) emDV.get(0);
                String emailAddress = emD.getEmailAddress();
                emailAddrLL.add(emailAddress);
              }
            }
            if (emailAddrLL.size() == 0) {
              emailAddrLL.add(defEmail);
            }
            String emailText1 = "User " +
                                userD.getFirstName() + " " + userD.getLastName() +
                                " (" + userD.getUserId() + ") doesn't have eMail address." +
                                ls + "Message to send: " +
                                ls + ls + "Subject: " +
                                ls + subject +
                                ls + "Message:" +
                                ls + emailText;

            subject = "User eMail Failure";
            emailText = emailText1;

          }
          String emailAddresses = "";
          for (Iterator iter3 = emailAddrLL.iterator(); iter3.hasNext(); ) {
            String emailAddr = (String) iter3.next();
            if (emailAddresses.length() > 0) emailAddresses += "; ";
            emailAddresses += emailAddr;
            emailEjb.send(emailAddr,
                          emailEjb.getDefaultEmailAddress(),
                          subject, emailText, null, 0, userD.getUserId());
          }
          comment += emailAddresses;
          if (comment.length() > 255) comment = comment.substring(0, 255);
          for (Iterator iter3 = ogItems.iterator(); iter3.hasNext(); ) {
            OrderGuideStructDelData emailOgsdD =
              (OrderGuideStructDelData) iter3.next();
            emailOgsdD.setComments(comment);
            OrderGuideStructDelDataAccess.update(conn, emailOgsdD);
          }

        }
        ogItems = new OrderGuideStructDelDataVector();
      }

    } catch (Exception e) {
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return;

  }


  public OrderGuideDescDataVector getCollectionByBusEntityId(int pBusEntityId, int pTypeId) throws RemoteException
  {
      OrderGuideDataVector ogdv = new OrderGuideDataVector();
      APIAccess apiAccess = null;
      Connection conn = null;
      try {
        conn = getConnection();
        DBCriteria crit = new DBCriteria();
        crit = addTypeCriterion(crit, pTypeId);
        crit.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pBusEntityId);
        crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
        ogdv = OrderGuideDataAccess.select(conn, crit);
      } catch (Exception e) {
        throw new RemoteException("getCollectionBySiteId: " + e.getMessage());
      } finally {
        try {
          if (conn != null) {
            conn.close();
          }
        } catch (Exception ex) {
        }
      }
      return fillOrderGuideDescVector(ogdv);
    }

    public OrderGuideStructureDataVector updateOgStructureCollection(OrderGuideStructureDataVector collection,
                                                                     String pUser) throws RemoteException {
        Connection conn = null;
        logInfo("updateOgStructureCollection => begin ");
        OrderGuideStructureDataVector updatedCollection = new OrderGuideStructureDataVector();
        try {

            conn = getConnection();

            if (collection != null && collection.size() > 0) {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    OrderGuideStructureData ogStructureData = (OrderGuideStructureData) it.next();

                    if (ogStructureData.getOrderGuideStructureId() > 0) {
                        logInfo("updateOgStructureCollection => update: "+ogStructureData.getItemId());
                        if (pUser != null) {
                            ogStructureData.setModBy(pUser);
                        }
                        OrderGuideStructureDataAccess.update(conn, ogStructureData);

                    } else {
                        logInfo("updateOgStructureCollection => insert: "+ogStructureData.getItemId());
                        if (pUser != null) {
                            ogStructureData.setModBy(pUser);
                            ogStructureData.setAddBy(pUser);
                        }
                        ogStructureData = OrderGuideStructureDataAccess.insert(conn, ogStructureData);
                    }

                  updatedCollection.add(ogStructureData);
                }
            }
        } catch (Exception e) {
            throw new RemoteException("getCollectionBySiteId: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        logInfo("updateOgStructureCollection => end");

        return updatedCollection;
    }

    public OrderGuideStructureDataVector getAvailableOrderGuideItems(int pOrderGuideId, ShoppingItemRequest pShoppingItemRequest) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAvailableOrderGuideItems(conn,pOrderGuideId, pShoppingItemRequest);
        } catch (Exception e) {
            throw new RemoteException("getAvailableOrderGuideItems: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public OrderGuideStructureDataVector getAvailableOrderGuideItems(Connection pCon,
                                                                     int pOrderGuideId,
                                                                     ShoppingItemRequest pShoppingItemRequest) throws SQLException {

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideId);
        dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, ShoppingDAO.getShoppingItemIdsRequest(pCon, pShoppingItemRequest));
        return OrderGuideStructureDataAccess.select(pCon, dbc);

    }

    /**
     * create contract if a contract not exists for a giving catalog
     * otherwise update the contract
     * @param pCatalogId the catalogId
     * @throws            RemoteException Required by EJB 1.0
     */
    public void updateOrderGuideByCatalogAndBusEntity(int pCatalogId, int pBusEntityId, String pUser) throws RemoteException {
 	   // Get the items in the catalog.
 	   IdVector itemids;
 	   CatalogData catalogD;
 	   try {
 		   APIAccess apiAccess = getAPIAccess();
 		   CatalogInformation cati = apiAccess.getCatalogInformationAPI();
 		   catalogD = cati.getCatalog(pCatalogId);
 		   // Get all the items.
 		   itemids = cati.searchCatalogProducts(pCatalogId);
 	   } catch (Exception e) {
 		   String msg = "updateContractByCatalog: " + e.getMessage();
 		   logError(msg);
 		   throw new RemoteException(msg);
 	   }

 	   Connection conn = null;

 	   try {
 		   conn = getConnection();
 		   DBCriteria crit = new DBCriteria();
 		   crit = addTypeCriterion(crit, OrderGuide.TYPE_ALL);
 		   crit.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
 		   crit.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pBusEntityId);
 		   crit.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
 		   OrderGuideDataVector ogdv = OrderGuideDataAccess.select(conn, crit);
 		   if (ogdv.size() > 0){
 			   OrderGuideData templateOrderG = null;
 			   IdVector idVector = new IdVector();
 			   for (int j = 0; j < ogdv.size(); j++) {
 				   OrderGuideData ogdD = (OrderGuideData) ogdv.get(j);
 				   idVector.add(new Integer(ogdD.getOrderGuideId()));
 				   if (j == 0 || RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE.equals(ogdD.getOrderGuideTypeCd()))
 					   templateOrderG = ogdD;
 			   }

 			   // remove order guide item that not in the catalog
 			   crit = new DBCriteria();
 			   crit.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, idVector);
 			   crit.addNotOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemids);
 			   OrderGuideStructureDataAccess.remove(conn, crit);

 			   // select order guide items that exists in template order guide and catalog
 			   crit = new DBCriteria();
 			   crit.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, templateOrderG.getOrderGuideId());
 			   crit.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemids);
 			   OrderGuideStructureDataVector ogsdv = OrderGuideStructureDataAccess.select(conn, crit);

 			   // get order item that not already exists in order guide
 			   for (int i = 0; i < ogsdv.size(); i++) {
 				   OrderGuideStructureData ogsd = (OrderGuideStructureData)ogsdv.get(i);
 				   Integer itemId = new Integer(ogsd.getItemId());
 				   if (itemids.contains(itemId)){
 					   itemids.remove(itemId);
 				   }
 			   }

 			   // create new order guide item
 			   for (int i = 0; i < itemids.size(); i++) {
 				   OrderGuideStructureData newOrderGuideItem = OrderGuideStructureData.createValue();
 				   newOrderGuideItem.setOrderGuideId(templateOrderG.getOrderGuideId());
 				   newOrderGuideItem.setItemId(((Integer)itemids.get(i)).intValue());
 				   newOrderGuideItem.setAddBy(pUser);
 				   newOrderGuideItem.setModBy(pUser);
 				   OrderGuideStructureDataAccess.insert(conn, newOrderGuideItem);
 			   }
 		   }else{// no order guide
 			   OrderGuideData ogdD = OrderGuideData.createValue();
 			   ogdD.setShortDesc(catalogD.getShortDesc());
 			   ogdD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
 			   ogdD.setCatalogId(pCatalogId);
 			   ogdD.setBusEntityId(pBusEntityId);
 			   ogdD.setAddBy(pUser);
 			   ogdD.setModBy(pUser);
 			   createFromCatalog(ogdD);
 		   }
 	   } catch (Exception e) {
 		   e.printStackTrace();
 		   throw new RemoteException("updateOrderGuideByCatalogAndBusEntity: " + e.getMessage());
 	   } finally {
 		   closeConnection(conn);
 	   }
    }

    public OrderGuideDescDataVector getCollectionByStore(IdVector storeIds, String filterValue,
        String filterType, List orderGuideTypes) throws RemoteException {
        if (storeIds == null) {
            return new OrderGuideDescDataVector();
        }
        if (storeIds.size() == 0) {
            return new OrderGuideDescDataVector();
        }

        OrderGuideDescDataVector resOrderGuideDescVector = new OrderGuideDescDataVector();
        Connection connection = null;

        try {
          connection = getConnection();

          DBCriteria orderGuideDbc = new DBCriteria();
          DBCriteria catalogDbc = new DBCriteria();
          String catalogSqlSelect = null;
          OrderGuideDataVector orderGuideVector = null;

          // Building of SQL-request to get array of id's for catalogs
          catalogDbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, storeIds);
          catalogDbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
          catalogSqlSelect = CatalogAssocDataAccess.getSqlSelectIdOnly(
            CatalogAssocDataAccess.CATALOG_ID, catalogDbc);

          // Criterion addition regarding catalogues
          orderGuideDbc.addOneOf(OrderGuideDataAccess.CATALOG_ID, catalogSqlSelect);

          // Criterion addition regarding user's filter
          if (Utility.isSet(filterValue)) {
              if (Utility.isSet(filterType)) {
                  filterValue = filterValue.trim();
                  filterType = filterType.trim();
                  if (LocatePropertyNames.ID_SEARCH_TYPE.equalsIgnoreCase(filterType)) {
                      int orderGuideId = 0;
                      try {
                          orderGuideId = Integer.parseInt(filterValue);
                      }
                      catch (Exception exc) {
                      }
                      orderGuideDbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_ID, orderGuideId);
                  }
                  else if (LocatePropertyNames.NAME_CONTAINS_SEARCH_TYPE.equalsIgnoreCase(filterType)) {
                      orderGuideDbc.addContainsIgnoreCase(OrderGuideDataAccess.SHORT_DESC, filterValue);
                  }
                  else {
                      orderGuideDbc.addBeginsWithIgnoreCase(OrderGuideDataAccess.SHORT_DESC, filterValue);
                  }
              }
          }

          // Criterion addition regarding types of the order-guides
          if (orderGuideTypes != null) {
              orderGuideDbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, orderGuideTypes);
          }

          //
          orderGuideDbc.addOrderBy(OrderGuideDataAccess.SHORT_DESC);
          orderGuideVector = OrderGuideDataAccess.select(connection, orderGuideDbc);

          resOrderGuideDescVector = fillOrderGuideDescVector(orderGuideVector);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }

        return resOrderGuideDescVector;
    }

    public int[] removeOrderGuides(List pOrderGuides) throws RemoteException {

        Connection conn = null;

        try {

            int[] numberOfRowsDeleted = new int[2];

            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_ID, pOrderGuides);
            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);

            String orderGuideRequest = OrderGuideDataAccess.getSqlSelectIdOnly(OrderGuideDataAccess.ORDER_GUIDE_ID, dbc);

            DBCriteria idbc = new DBCriteria();
            idbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, orderGuideRequest);

            int itemsDeleted = OrderGuideStructureDataAccess.remove(conn, idbc);
            int orderGuidesDeleted = OrderGuideDataAccess.remove(conn, dbc);

            numberOfRowsDeleted[0] = orderGuidesDeleted;
            numberOfRowsDeleted[1] = itemsDeleted;

            return  numberOfRowsDeleted;

        }  catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public int removeOrderGuideStructureItems(List pOrderGuideStructureIds) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_STRUCTURE_ID, pOrderGuideStructureIds);

            return OrderGuideStructureDataAccess.remove(conn, dbc);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public List<OrderGuideStructureData> updateItems(Integer pOrderGuideId, List<OrderGuideStructureData> pItems, String pUser) throws RemoteException {

        Connection conn = null;

        try {

            conn = getConnection();

            for (OrderGuideStructureData item : pItems) {
                item.setOrderGuideId(pOrderGuideId);
                if (item.getOrderGuideStructureId() > 0) {
                    item.setModBy(pUser);
                    OrderGuideStructureDataAccess.update(conn, item);
                } else {
                    item.setAddBy(pUser);
                    item.setModBy(pUser);
                    item = OrderGuideStructureDataAccess.insert(conn, item);
                }
            }

            return pItems;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public OrderGuideData updateOrderGuideData(OrderGuideData pOrderGuide) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            if (pOrderGuide.getOrderGuideId() == 0) {
               pOrderGuide =  OrderGuideDataAccess.insert(conn, pOrderGuide);
            } else {
                OrderGuideDataAccess.update(conn, pOrderGuide);
            }
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return pOrderGuide;
    }
    //shopping list

    /**
     * Deletes items from the order guide
     *@param  pItemIdV - list of item ids that are needed to be removed from order guide 
     *@param orderGuideId - order guide id from which passed in items will be removed 
     *@exception  RemoteException     
     */
    public void removeItemFromOrderGuide(IdVector pItemIdV,int orderGuideId) throws RemoteException,Exception {
        Connection conn = null;
        Statement stmt = null;
        try{
        conn = getConnection();
        stmt = conn.createStatement();
        for (int idx = 0; idx < pItemIdV.size(); idx++) {
          Integer itemid = (Integer) pItemIdV.get(idx);
          String delsql = "delete from " +
                          OrderGuideStructureDataAccess.CLW_ORDER_GUIDE_STRUCTURE +
                          " ogs where  ogs.item_id = " + itemid +
                          " and ogs.order_guide_id = "+orderGuideId;
          logDebug("SQL: " + delsql);
          stmt.executeUpdate(delsql);
        }
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
      	  stmt.close();
            closeConnection(conn);
        }
      }
      /**
       * gets items for a specific order guide
       *@param orderGuideId - order guide id for which items will be retrieved 
       *@exception  RemoteException     
       */
      public OrderGuideStructureDataVector getOrderGuideItems(
              int pOrderGuideId) throws RemoteException {
      	DBCriteria dbc = new DBCriteria();
      	Connection conn = null;
      	OrderGuideStructureDataVector ogsdv = null; 
      	try{
          conn = getConnection();
          dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideId);
          ogsdv = OrderGuideStructureDataAccess.select(conn, dbc);
      	}
      	catch (Exception e) {
              throw new RemoteException(e.getMessage());
          } finally {
              closeConnection(conn);
          }
      	return ogsdv;
      }
      
      public OrderGuideStructureDataVector getOrderGuideItems(IdVector pOrderGuideIds) throws RemoteException {
      	DBCriteria dbc = new DBCriteria();
      	Connection conn = null;
      	OrderGuideStructureDataVector ogsdv = null; 
      	try{
          conn = getConnection();
          dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideIds);
          ogsdv = OrderGuideStructureDataAccess.select(conn, dbc);
      	}
      	catch (Exception e) {
              throw new RemoteException(e.getMessage());
          } finally {
              closeConnection(conn);
          }
      	return ogsdv;
      }
      
      public OrderScheduleDataVector findOrderGuideSchedules(int orderGuideId, int siteId) throws RemoteException {
        OrderScheduleDataVector osdv = null;
          
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderScheduleDataAccess.ORDER_GUIDE_ID, orderGuideId);
        dbc.addEqualTo(OrderScheduleDataAccess.RECORD_STATUS_CD, RefCodeNames.RECORD_STATUS_CD.VALID);

        if (siteId > 0) {
            dbc.addEqualTo(OrderScheduleDataAccess.BUS_ENTITY_ID, siteId);
        }
        
        Connection conn = null;
        try {
            conn = getConnection();
            osdv = OrderScheduleDataAccess.select(conn, dbc);
        } catch (Exception e) {
            throw new RemoteException("findOrderGuideSchedules() " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
		}
            } catch (Exception ex) {}
	}

        return osdv;
    }
}
