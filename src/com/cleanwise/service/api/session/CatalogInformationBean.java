package com.cleanwise.service.api.session;

/**
 * Title:        CatalogInformationBean
 * Description:  Bean implementation for CatalogInformation
 *               Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and
 *               manipulating catalog information and their associations.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.CatalogPropertyDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.dao.CostCenterAssocDataAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.dao.CurrencyDataAccess;
import com.cleanwise.service.api.dao.EstimatorFacilityDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMappingAssocDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.dao.OrderRoutingDataAccess;
import com.cleanwise.service.api.dao.PreOrderDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.ServiceDAO;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.framework.CatalogServicesAPI;
import com.cleanwise.service.api.util.CacheManager;
import com.cleanwise.service.api.util.CatalogNode;
import com.cleanwise.service.api.util.CategoryItemMfgKey;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PropertyFieldUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogCategoryView;
import com.cleanwise.service.api.value.CatalogCategoryViewVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogFiscalPeriodView;
import com.cleanwise.service.api.value.CatalogFiscalPeriodViewVector;
import com.cleanwise.service.api.value.CatalogItemDescView;
import com.cleanwise.service.api.value.CatalogItemDescViewVector;
import com.cleanwise.service.api.value.CatalogItemView;
import com.cleanwise.service.api.value.CatalogItemViewVector;
import com.cleanwise.service.api.value.CatalogPropertyData;
import com.cleanwise.service.api.value.CatalogPropertyDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.CategoryToCostCenterView;
import com.cleanwise.service.api.value.CategoryToCostCenterViewVector;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.CostCenterAssocData;
import com.cleanwise.service.api.value.CostCenterAssocDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.CurrencyDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderDetailData;
import com.cleanwise.service.api.value.FiscalCalenderDetailDataVector;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemAssocDataVector;
import com.cleanwise.service.api.value.ItemCatalogAggrView;
import com.cleanwise.service.api.value.ItemCatalogAggrViewVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingAssocData;
import com.cleanwise.service.api.value.ItemMappingAssocDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.ItemView;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.MenuItemViewVector;
import com.cleanwise.service.api.value.MultiproductView;
import com.cleanwise.service.api.value.MultiproductViewVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.ServiceData;
import com.cleanwise.service.api.value.ServiceSearchCriteria;
import com.cleanwise.service.api.value.ServiceViewVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.UserDataVector;

/**
 *  Provides access to the methods for retrieving and manipulating catalog
 *  information and their associations.
 *
 *@author     dvieira
 *@created    September 21, 2001
 */
public class CatalogInformationBean extends CatalogServicesAPI {
	private static final Logger log = Logger.getLogger(CatalogInformationBean.class);

    private final static String DEFAULT_ALLOCATED_FREIGHT = "0";
    private final static int inClauseSize=500;
    
    private static ArrayList<String> IGNORED_CATALOG_STATUS_CDS = new ArrayList<String>();
    static {
        IGNORED_CATALOG_STATUS_CDS.add(RefCodeNames.CATALOG_STATUS_CD.INACTIVE);
        IGNORED_CATALOG_STATUS_CDS.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);
    }

    private Comparator<CatalogCategoryData> CATALOG_CATEGORY_SHORT_DESC_COMP = new Comparator<CatalogCategoryData>() {
        public int compare(CatalogCategoryData o1, CatalogCategoryData o2) {

            int sortOrder1 = o1.getSortOrder();
            String name1 = o1.getCatalogCategoryShortDesc();

            int sortOrder2 = o2.getSortOrder();
            String name2 = o2.getCatalogCategoryShortDesc();

            if (sortOrder1 != sortOrder2) {
                return  (sortOrder1 > sortOrder2) ? 1 : -1;
            } else {
               return  name1.compareToIgnoreCase(name2);
            }
        }
    };


  /**
   */
  public CatalogInformationBean() {}

  /**
   *  Gets the array-like catalog vector values to be used by the request.
   *
   *@param  pBusEntityId      the customer identifier
   *@return                   CatalogDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */

  public CatalogDataVector getCatalogsCollectionByBusEntity(int pBusEntityId) throws
    RemoteException {
    return getCatalogsCollectionByBusEntity(pBusEntityId, null);
  }

  public CatalogDataVector getCatalogsCollectionByBusEntity(int pBusEntityId, String pCatalogTypeCd) throws RemoteException {

    Connection con = null;
    try {
      con = getConnection();
      return mCacheManager.getBusEntityDAO().getCatalogsCollectionByBusEntity(con, pBusEntityId, pCatalogTypeCd);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByBusEntity() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByBusEntity() SQL Exception happened");
    } catch (Exception exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByBusEntity() Exception ");
    } finally {
      closeConnection(con);
    }
  }


  /**
   *  Gets the array-like catalog vector values to be used by the request.
   *
   *@param  pUserId      the customer identifier
   *@return                   CatalogDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */
  public CatalogDataVector getCatalogsCollectionByUser(int pUserId) throws
    RemoteException {

    Connection con = null;
    CatalogDataVector catalogDV = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
      IdVector busentityids = UserAssocDataAccess.selectIdOnly(con, UserAssocDataAccess.BUS_ENTITY_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busentityids);
      IdVector catids = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CATALOG_ID, dbc);

      catalogDV = CatalogDataAccess.select(con, catids);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByUser() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByUser() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogDV;
  }


  public IdVector getCatalogIdsCollectionByUser(int pUserId) throws
    RemoteException {

    Connection con = null;
    IdVector catids = new IdVector();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
      IdVector busentityids = UserAssocDataAccess.selectIdOnly(con, UserAssocDataAccess.BUS_ENTITY_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busentityids);
      catids = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CATALOG_ID, dbc);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByUser() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByUser() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catids;
  }


  public CatalogStructureDataVector getCatalogStructureByCategoryId(int pCategoryItemId) throws RemoteException {
	    Connection con = null;
	    CatalogStructureDataVector cSDV = null;
	    try {
	      con = getConnection();
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pCategoryItemId);

	      cSDV = CatalogStructureDataAccess.select(con, dbc);
	    } catch (NamingException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructureByCategoryId() Naming Exception happened");
	    } catch (SQLException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructureByCategoryId() SQL Exception happened");
	    } finally {
	      closeConnection(con);
	    }
	    return cSDV;
  }

  public CatalogStructureDataVector getCatalogStructure(int pCategoryItemId, int pCatalogId) throws RemoteException {
	    Connection con = null;
	    CatalogStructureDataVector cSDV = null;
	    try {
	      con = getConnection();
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pCategoryItemId);
	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

	      cSDV = CatalogStructureDataAccess.select(con, dbc);
	    } catch (NamingException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() Naming Exception happened");
	    } catch (SQLException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() SQL Exception happened");
	    } finally {
	      closeConnection(con);
	    }
	    return cSDV;
}

  /*
   * Gets the array-like catalog vector values to be used by the request.
   * @param pCatName  the catalog name or catalog name substring
   * @param pMatchType   Public static varaibles in CatalogInformation: EXACT_MATCH |
   * BEGINS_WITH |
   * CONTAINS |
   * EXACT_MATCH_IGNORE_CASE |
   * BEGINS_WITH_IGNORE_CASE |
   * CONTAINS_IGNORE_CASE
   * @param pBusEntityId  the customer identifier
   * @return CatalogDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public CatalogDataVector
    getCatalogsCollectionByNameAndBusEntity(String pCatName, int pMatchType,
                                            int pBusEntityId) throws
    RemoteException {

    Connection con = null;
    CatalogDataVector catalogDV = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
      IdVector catids = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CATALOG_ID, dbc);
      dbc = new DBCriteria();
      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      switch (pMatchType) {
      case SearchCriteria.EXACT_MATCH:
        dbc.addEqualTo(CatalogDataAccess.SHORT_DESC, pCatName);
        break;
      case SearchCriteria.BEGINS_WITH:
        dbc.addLike(CatalogDataAccess.SHORT_DESC, pCatName + "%");
        break;
      case SearchCriteria.CONTAINS:
        dbc.addLike(CatalogDataAccess.SHORT_DESC, "%" + pCatName + "%");
        break;
      case SearchCriteria.EXACT_MATCH_IGNORE_CASE:
        dbc.addEqualToIgnoreCase(CatalogDataAccess.SHORT_DESC, pCatName.toUpperCase());
        break;
      case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
        dbc.addLikeIgnoreCase(CatalogDataAccess.SHORT_DESC, pCatName.toUpperCase() + "%");
        break;
      case SearchCriteria.CONTAINS_IGNORE_CASE:
        dbc.addLikeIgnoreCase(CatalogDataAccess.SHORT_DESC, "%" + pCatName.toUpperCase() + "%");
        break;
      default:
        throw new RemoteException("Error. CatalogInformationBean.getCatalogByName(). Unknown match type: " +pMatchType);
      }
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catids);
      catalogDV = CatalogDataAccess.select(con, dbc);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByBusEntity() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogsCollectionByBusEntity() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogDV;
  }


  /**
   *  Gets the CatalogAssociationsCollection attribute of the
   *  CatalogInformationBean object
   *
   *@param  pCatalogId           Description of Parameter
   *@return                      The CatalogAssociationsCollection value
   *@exception  RemoteException  Description of Exception
   */
  public CatalogAssocDataVector getCatalogAssociationsCollection(int pCatalogId) throws
    RemoteException {
    Connection con = null;
    CatalogAssocDataVector catassocv = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      catassocv = CatalogAssocDataAccess.select(con, dbc);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogAssociationsCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogAssociationsCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catassocv;
  }

  /**
   *  Gets catalog information values to be used by the request.
   *
   *@param  pCatalogId                 the catalog identifier
   *@return                            CatalogData
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           (Required by EJB 1.0) and
   *      DataNotFoundException
   */
  public CatalogData getCatalog(int pCatalogId) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    CatalogData catalogD = null;
    try {
      con = getConnection();
      catalogD = CatalogDataAccess.select(con, pCatalogId);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalog() Naming Exception happened");
    } catch (DataNotFoundException e) {
      throw e;
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalog() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogD;
  }

  public CatalogPropertyData getCatalogProperty(int pCatalogId) throws RemoteException {
	  Connection con = null;
	  CatalogPropertyData cPD = new CatalogPropertyData();
	  try {
	    con = getConnection();
	    DBCriteria dbc = new DBCriteria();
	    dbc.addEqualTo(CatalogPropertyDataAccess.CATALOG_ID, pCatalogId);

	    CatalogPropertyDataVector catalogPropertyDV = CatalogPropertyDataAccess.select(con, dbc);
	    if (catalogPropertyDV.size() > 0) {
	    	cPD = (CatalogPropertyData) catalogPropertyDV.get(0);
	    }

	  } catch (NamingException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. CatalogInformationBean.getCatalog() Naming Exception happened");
	  } catch (SQLException exc) {
	    logError("exc.getMessage");
	    exc.printStackTrace();
	    throw new RemoteException("Error. CatalogInformationBean.getCatalog() SQL Exception happened");
	  } finally {
	    closeConnection(con);
	  }
	  return cPD;
  }

  /**
   *  Gets the array-like catalog association vector values to be used by the
   *  request.
   *
   *@return                   CatalogAssociationDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */
  public CatalogDataVector getCatalogCollection() throws RemoteException {
    Connection con = null;
    CatalogDataVector catalogDV = null;
    try {
      con = getConnection();
      catalogDV = CatalogDataAccess.selectAll(con);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogDV;
  }


  /**
   * Gets the array-like catalog association vector values to be used by the request.
   * @param pEntitySearchCriteria the criteria
   * @return CatalogAssociationDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public CatalogDataVector getCatalogsByCritAndIds(EntitySearchCriteria
                                             pEntitySearchCriteria,
                                             IdVector catalogsIdsToReturn) throws
    RemoteException {
    Connection con = null;
    CatalogDataVector catalogDV = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      if (EntitySearchCriteria.ORDER_BY_ID == pEntitySearchCriteria.getOrder()) {
        dbc.addOrderBy(CatalogDataAccess.CATALOG_ID);
      } else {
        dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      }

      if (Utility.isSet(pEntitySearchCriteria.getSearchName())) {
        switch (pEntitySearchCriteria.getSearchNameType()) {
        case EntitySearchCriteria.NAME_CONTAINS:
          dbc.addLikeIgnoreCase(CatalogDataAccess.SHORT_DESC,
                                "%" + pEntitySearchCriteria.getSearchName() +
                                "%");
          break;
        case EntitySearchCriteria.NAME_STARTS_WITH:
          dbc.addLikeIgnoreCase(CatalogDataAccess.SHORT_DESC,
                                pEntitySearchCriteria.getSearchName() + "%");
          break;
        default:
          throw new RemoteException(
            "Error. CatalogInformationBean.getCatalogByName(). Unknown match type: " +
            pEntitySearchCriteria.getSearchNameType());
        }
      }
      if (0 != pEntitySearchCriteria.getSearchIdAsInt()) {
        dbc.addEqualTo(CatalogDataAccess.CATALOG_ID,
                       pEntitySearchCriteria.getSearchIdAsInt());
      }
      if (pEntitySearchCriteria.getSearchTypeCds() != null &&
          !pEntitySearchCriteria.getSearchTypeCds().isEmpty()) {
        dbc.addOneOf(CatalogDataAccess.CATALOG_TYPE_CD, pEntitySearchCriteria.getSearchTypeCds());
      }

      List statusCdList = pEntitySearchCriteria.getSearchStatusCdList();
      if (statusCdList != null && statusCdList.size() > 0) {
        dbc.addOneOf(CatalogDataAccess.CATALOG_STATUS_CD, statusCdList);
      }
      //store idspEntitySearchCriteria
      IdVector storeIdV = pEntitySearchCriteria.getStoreBusEntityIds();
      if (storeIdV != null) { //Zero length vector means user is not allowed to see andy catalog
        DBCriteria catStoreDbc = new DBCriteria();
        catStoreDbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, storeIdV);
        catStoreDbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                               RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        String catStoreReq = CatalogAssocDataAccess.
                             getSqlSelectIdOnly(CatalogAssocDataAccess.
                                                CATALOG_ID, catStoreDbc);
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catStoreReq);
      }

      //account criteria
      IdVector accountIdV = pEntitySearchCriteria.getAccountBusEntityIds();
      if (accountIdV != null && accountIdV.size() > 0) {
        DBCriteria catAcctDbc = new DBCriteria();
        catAcctDbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, accountIdV);
        catAcctDbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                              RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        String catAcctReq = CatalogAssocDataAccess.
                            getSqlSelectIdOnly(CatalogAssocDataAccess.
                                               CATALOG_ID, catAcctDbc);
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catAcctReq);
      }

      if (catalogsIdsToReturn != null && catalogsIdsToReturn.size() > 0) {
          dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogsIdsToReturn);
      }

      catalogDV = CatalogDataAccess.select(con, dbc);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogDV;
  }


  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pCatalogId        Description of Parameter
   *@return                   The StoreCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getStoreCollection(int pCatalogId) throws
    RemoteException {
    return getBusEntityCollection(pCatalogId,
                                  RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
  }


  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pCatalogId        Description of Parameter
   *@return                   The AccountCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getAccountCollection(int pCatalogId) throws
    RemoteException {
    return getBusEntityCollection(pCatalogId, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
  }

  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pCatalogIds        Description of Parameter
   *@return                   The AccountCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public Map<Integer, List<Integer>> getCatalogIdToAccountIdMap(Collection<Integer> pCatalogIds) throws RemoteException {
	  Map<Integer, List<Integer>> returnValue = new HashMap<Integer, List<Integer>>();
	  Iterator<Integer> catalogIdIterator = pCatalogIds.iterator();
	  while (catalogIdIterator.hasNext()) {
		  returnValue.put(catalogIdIterator.next(), new ArrayList<Integer>());
	  }
	  Connection con = null;
	  ResultSet rs = null;
      Statement stmt = null;
	  try {
	      con = getConnection();
	      stmt = con.createStatement();
	      StringBuilder queryBuilder = new StringBuilder(500); 
	      queryBuilder.append("SELECT DISTINCT CA.");
	      queryBuilder.append(CatalogAssocDataAccess.CATALOG_ID);
	      queryBuilder.append(", BE.");
	      queryBuilder.append(BusEntityDataAccess.BUS_ENTITY_ID);
	      queryBuilder.append(" FROM ");
	      queryBuilder.append(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
	      queryBuilder.append(" CA, ");
	      queryBuilder.append(BusEntityDataAccess.CLW_BUS_ENTITY);
	      queryBuilder.append(" BE");
	      queryBuilder.append(" WHERE ");
	      queryBuilder.append(Utility.toSqlInClause("CA." + CatalogAssocDataAccess.CATALOG_ID, new ArrayList<Integer>(pCatalogIds)));
	      queryBuilder.append(" AND CA.");
	      queryBuilder.append(CatalogAssocDataAccess.CATALOG_ASSOC_CD);
	      queryBuilder.append(" = \'");
	      queryBuilder.append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
	      queryBuilder.append("\' AND BE.");
	      queryBuilder.append(BusEntityDataAccess.BUS_ENTITY_ID);
	      queryBuilder.append(" = CA.");
	      queryBuilder.append(CatalogAssocDataAccess.BUS_ENTITY_ID);
	      queryBuilder.append(" AND BE.");
	      queryBuilder.append(BusEntityDataAccess.BUS_ENTITY_TYPE_CD);
	      queryBuilder.append(" = \'");
	      queryBuilder.append(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
	      queryBuilder.append("\'");
	      String queryString = queryBuilder.toString();

	      rs = stmt.executeQuery(queryString);
	      while (rs.next()) {
	    	  Integer catalogId = new Integer(rs.getInt(1));
    		  returnValue.get(catalogId).add(new Integer(rs.getInt(2)));
	      }
	  } 
	  catch (Exception e) {
	      String msg = "Error. CatalogInformation.getCatalogToAccountIdMap() :" + e.getMessage();
	      logError(msg);
	      throw new RemoteException(msg);
	  } 
	  finally {
		  try {
      			if (stmt != null) {
      				stmt.close();
      			}
      			if (rs != null) {
      				rs.close();
      			}
		  } 
		  catch (Exception e) {
	    	    e.printStackTrace();
		  }
		  closeConnection(con);
	  }
	  return returnValue;
  }


  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pNameFilter       string to filter Account on shortDesc.
   *@param  pCatalogId        Description of Parameter
   *@return                   BusEntityDataVector object of BusEntityData
   *      Account type objects
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getAccountCollection(int pCatalogId,
                                                  String pNameFilter) throws
    RemoteException {
    return getBusEntityCollection(pCatalogId,
                                  RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,
                                  pNameFilter,
                                  SearchCriteria.CONTAINS_IGNORE_CASE,
                                  SearchCriteria.ORDER_BY_SHORT_DESC
      );
  }


  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pCatalogId        Description of Parameter
   *@return                   The SiteCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getSiteCollection(int pCatalogId) throws
    RemoteException {
    return getBusEntityCollection(pCatalogId, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
  }


  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pNameFilter       string to filter Site on shortDesc.
   *@param  pCatalogId        Description of Parameter
   *@return                   BusEntityDataVector object of BusEntityData
   *      Account type objects
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getSiteCollection(int pCatalogId,
                                               String pNameFilter) throws
    RemoteException {
    return getBusEntityCollection(pCatalogId,
                                  RefCodeNames.BUS_ENTITY_TYPE_CD.SITE,
                                  pNameFilter,
                                  SearchCriteria.CONTAINS_IGNORE_CASE,
                                  SearchCriteria.ORDER_BY_SHORT_DESC
      );
  }


  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pBusEntityTypeCd  business entity type code (STORE, ACCOUNT, SITE)
   *@param  pCatalogId        Description of Parameter
   *@return                   The BusEntityCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getBusEntityCollection(int pCatalogId,
    String pBusEntityTypeCd) throws RemoteException {
    return getBusEntityCollection(pCatalogId, pBusEntityTypeCd, null, 0, SearchCriteria.ORDER_BY_SHORT_DESC);
  }

  /**
   *  Gets the array-like business entity association vector values to be used
   *  by the request.
   *
   *@param  pBusEntityTypeCd  business entity type code (STORE, ACCOUNT, SITE)
   *@param  pNameFilter       shortDesc filter
   *@param  pMatch            determins filter matchibg action
   *@param  pOrder            resunt order (could be by id or by short
   *      description)
   *@param  pCatalogId        Description of Parameter
   *@return                   The BusEntityCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public BusEntityDataVector getBusEntityCollection(int pCatalogId,
    String pBusEntityTypeCd,
    String pNameFilter,
    int pMatch,
    int pOrder) throws RemoteException {
    Connection con = null;
    BusEntityDataVector busEntityDV = new BusEntityDataVector();
    try {
      con = getConnection();
      busEntityDV = getBusEntityCollection(con, pCatalogId, pBusEntityTypeCd, pNameFilter, pMatch, pOrder);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getBusEntityCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getBusEntityCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return busEntityDV;
  }

  /**
   *  Gets business entity data
   *
   *@param  pBusEntityTypeCd           business entity type code (STORE,
   *      ACCOUNT, SITE)
   *@param  pBusEntityId               business entity identifier
   *@param  pCatalogId                 Description of Parameter
   *@return                            The BusEntity value
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0
   */

  public BusEntityData getBusEntity(int pCatalogId,
                                    String pBusEntityTypeCd,
                                    int pBusEntityId) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    BusEntityData busEntityD = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      String catalogAssocReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, catalogAssocReq);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, pBusEntityTypeCd);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, pBusEntityId);

      BusEntityDataVector busEntityDV = BusEntityDataAccess.select(con, dbc);

      if (busEntityDV.size() == 0) {
        throw new DataNotFoundException("No business entity found. Id=" +
                                        pBusEntityId + " Type=" +
                                        pBusEntityTypeCd);
      }
      busEntityD = (BusEntityData) busEntityDV.get(0);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getBusEntityCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getBusEntityCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return busEntityD;
  }


  /**
   *  Gets the CatalogsForAccountSites attribute of the CatalogInformationBean
   *  object
   *
   *@param  pAccountId           Description of Parameter
   *@return                      The CatalogsForAccountSites value
   *@exception  RemoteException  Description of Exception
   */
  public CatalogDataVector getCatalogsByAccountId(int pAccountId) throws
    RemoteException {

    CatalogDataVector catv;
    Connection con = null;
    try {
      con = getConnection();
      Statement stmt = con.createStatement();
      String query = "SELECT distinct " +
                     CatalogAssocDataAccess.CATALOG_ID +
                     " FROM " + CatalogAssocDataAccess.CLW_CATALOG_ASSOC +
                     " WHERE " +
                     CatalogAssocDataAccess.CATALOG_ASSOC_CD
                     + " = \'" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT +
                     "\' and " +
                     CatalogAssocDataAccess.BUS_ENTITY_ID + " = " +
                     pAccountId + " or " +
                     CatalogAssocDataAccess.CATALOG_ASSOC_CD
                     + " = \'" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE +
                     "\' and " +
                     CatalogAssocDataAccess.BUS_ENTITY_ID +
                     " in  ( SELECT distinct " +
                     BusEntityAssocDataAccess.BUS_ENTITY1_ID +
                     " FROM " + BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC +
                     " WHERE " + BusEntityAssocDataAccess.BUS_ENTITY2_ID +
                     " = " +
                     pAccountId + " and " +
                     BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD + " = \'" +
                     RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "\' )";

      ResultSet rs = stmt.executeQuery(query);
      IdVector idVector = new IdVector();
      while (rs.next()) {
        idVector.add(new Integer(rs.getInt(1)));
      }
      if (Utility.isSet(idVector)) {
    	  DBCriteria dbc = new DBCriteria();
          dbc.addOneOf(CatalogDataAccess.CATALOG_ID, idVector);
          catv = CatalogDataAccess.select(con, dbc);
      }
      else {
    	  catv = new CatalogDataVector();
      }
    } catch (Exception e) {
      String msg =
        "Error. CatalogInformation.CatalogDataVector() :"
        + " : " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    } finally {
      closeConnection(con);
    }
    return catv;
  }


  private CatalogData pickActiveCatalog(CatalogDataVector catv,
                                        String pCatalogTypeCd) {

    for (int i = 0; null != catv && i < catv.size(); i++) {
      CatalogData cd = (CatalogData) catv.get(i);
      String catStatusCd = cd.getCatalogStatusCd(),
                           catTypeCd = cd.getCatalogTypeCd();
      if (null == catStatusCd) {
        catStatusCd = "";
      }
      if (null == catTypeCd) {
        catTypeCd = "";
      }
      if (catStatusCd.equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE)
          &&
          catTypeCd.equals(pCatalogTypeCd)
        ) {
        return cd;
      }
    }
    return null;
  }

  /**
   *  Gets the SiteCatalog attribute of the CatalogInformationBean object
   *
   *@param  pSiteId              Description of Parameter
   *@return                      The SiteCatalog value
   *@exception  RemoteException  Description of Exception
   */
  public CatalogData getSiteCatalog(int pSiteId) throws RemoteException {

    CatalogDataVector catv = getCatalogsCollectionByBusEntity(pSiteId);

    if (catv.size() > 1) {
      logError("Error. CatalogInformationBean.getSiteCatalog() :"
               + " More than one catalog tied to this site id: "
               + pSiteId + "\nCatalogs found: " + catv);
    }

    // Pick out the ACTIVE SHOPPING catalog from any possible
    // multiple associations.
    CatalogData cd = pickActiveCatalog
                     (catv, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
    if (null != cd) {
      return cd;
    }

    cd = pickActiveCatalog
         (catv, RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
    if (null != cd) {
      return cd;
    }

    // Pick out the ACTIVE ACCOUNT catalog for this site.
    cd = pickActiveCatalog(catv, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
    if (null != cd) {
      return cd;
    }

    String msg = "Error. CatalogInformationBean.getSiteCatalog() :"
                 + " No catalog tied to this site id: " + pSiteId;
    logError(msg);
    return null;
  }


  /**
   *  Gets collection of CatalogAssocData elements
   *
   *@param  pCatalogId        catalog identifier or 0 if any
   *@param  pBusEntityId      busness entity identifier or 0 if any
   *@param  pAssocType        association type (CATALOG_STORE,
   *      CATALOG_ACCOUT,..) if null any association accepted
   *@return                   boolean true if found
   *@throws  RemoteException
   */
  public CatalogAssocDataVector getCatalogAssoc(int pCatalogId,
                                                int pBusEntityId,
                                                String pAssocType) throws
    RemoteException {
    Connection con = null;
    CatalogAssocDataVector catalogAssocDV = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      if (pCatalogId > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      }
      if (pBusEntityId > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
      }
      if (pAssocType != null && pAssocType.trim().length() > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, pAssocType);
      }
      catalogAssocDV = CatalogAssocDataAccess.select(con, dbc);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.doesCatalogAssocExist() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.doesCatalogAssocExist() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogAssocDV;
  }


  /**
   *  Gets the array-like user vector values to be used by the request.
   *
   *@param  pCatalogId        the catalog id
   *@return                   The UserCollection value
   *@throws  RemoteException  Required by EJB 1.0
   */
  public UserDataVector getUserCollection(int pCatalogId) throws
    RemoteException {

    UserDataVector userDV = new UserDataVector();
    Connection con = null;

    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      IdVector beIds = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
      CatalogData catD = CatalogDataAccess.select(con, pCatalogId);
      String cattype = catD.getCatalogTypeCd();

      if (cattype.equals(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT)) {
        // Get all users associated with the Account.

        if (beIds.size() == 0) {
          return userDV;
        }
        dbc = new DBCriteria();
        dbc.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID, beIds);
        IdVector uIds = UserAssocDataAccess.selectIdOnly(con, UserAssocDataAccess.USER_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(UserDataAccess.USER_ID, uIds);
        userDV = UserDataAccess.select(con, dbc);

      }

      if (cattype.equals(RefCodeNames.CATALOG_TYPE_CD.SHOPPING)) {
        // Get all users configured to use this catalog.


        SiteDataVector sd = getSiteCollection(pCatalogId, null, 0,
                                              SearchCriteria.
                                              ORDER_BY_SHORT_DESC);

        APIAccess factory = new APIAccess();
        User usersi = factory.getUserAPI();
        IdVector siteIds = new IdVector();
        //userDV = null;
        for (int s = 0; s < sd.size(); s++) {
          SiteData site = (SiteData) sd.get(s);
          BusEntityData bed = site.getBusEntity();
          int siteId = bed.getBusEntityId();
          siteIds.add(new Integer(siteId));
        }
        userDV = usersi.getUsersCollectionByBusEntityCollection(siteIds, null);
      }

    } catch (DataNotFoundException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getUserCollection() " + "Catalog id " + pCatalogId + " not found.");
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getUserCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getUserCollection() SQL Exception happened");
    } catch (Exception exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getUserCollection() Exception happened: " + exc);
    } finally {
      closeConnection(con);
    }

    return userDV;
  }

  /**
   *  Gets catalog category information values to be used by the request.
   *
   *@param  pCatalogId                 the catalog identifier
   *@param  pCategoryId                the catalog category identifier
   *@return                            CatalogCategoryData
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0
   */
  public CatalogCategoryData getCatalogCategory(int pCatalogId, int pCategoryId) throws
    RemoteException, DataNotFoundException {
    Connection con = null;
    CatalogCategoryData catalogCategoryD = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pCategoryId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
      CatalogStructureDataVector catalogStructureDV =
        CatalogStructureDataAccess.select(con, dbc);
      if (catalogStructureDV.size() > 1) {
        throw new RemoteException("Error. CatalogInformationBean.getCatalogCategory() Too many catalog structure records for catalog-category relation. Catalog id: " +
                                  pCatalogId + " Category id: " + pCategoryId);
      }
      if (catalogStructureDV.size() == 0) {
        throw new DataNotFoundException(
          "Error. CatalogInformationBean.getCatalogCategory() Category was not found. Catalog id: " +
          pCatalogId + " Category id: " +
          pCategoryId);
      }
      ItemData itemD = ItemDataAccess.select(con, pCategoryId);
      if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {
        throw new DataNotFoundException(
          "Error. CatalogInformationBean.getCatalogCategory() Item in catalog is not category. Catalog id: " +
          pCatalogId + " Item id: " + pCategoryId);
      }
      catalogCategoryD = new CatalogCategoryData();
      catalogCategoryD.setItemData(itemD);
      ItemData majorCategory = getMajorCategory(con, pCategoryId);
      catalogCategoryD.setMajorCategory(majorCategory);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogCategory() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogCategory() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogCategoryD;
  }

  private ItemData getMajorCategory(Connection pCon, int pCategoryId) throws
    SQLException {
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pCategoryId);
    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                   RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);
    String majorAssocReq =
      ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID, dbc);

    dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID, majorAssocReq);
    dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                   RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);

    ItemDataVector itemDV = ItemDataAccess.select(pCon, dbc);
    if (itemDV.size() > 1) {
      logError("Category has more than one major category. Category id = " + pCategoryId);
      return null;
    } else if (itemDV.size() == 0) {
      logError("Category has no major category. Category id = " + pCategoryId);
      return null;
    }
    ItemData iD = (ItemData) itemDV.get(0);
    return iD;
  }

    /**
     * Gets catalog categories, which do not have parent categories or parent
     * products.
     *
     * @param pCatalogId the catalog identifier
     * @return CatalogCategoryDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public CatalogCategoryDataVector getTopCatalogCategories(int pCatalogId) throws RemoteException {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();
        try {

            con = getConnection();

            // Get the category names alphabetically.
            String sql = "select i.item_id,cs.sort_order " +
                    " from clw_item i, clw_catalog_structure cs" +
                    " where cs.catalog_id = '" + pCatalogId + "'" +
                    " and i.item_id = cs.item_id" +
                    " and cs.catalog_structure_cd = '" +
                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'" +
                    " and i.item_id not in ( select item1_id from " +
                    " clw_item_assoc " +
                    "where " + //"catalog_id = " + pCatalogId +
                    //" and"
                    "item_assoc_cd = '" +
                    RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "'" +
                    " )" +
                    " order by i.short_desc";

            logDebug("CatlogInformationBean IIIIIIIIIIIIIIIIIIII sql: " + sql);

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            HashMap<Integer, Object[]/*params*/> topCategoryMap = new HashMap<Integer, Object[]>();
            while (rs.next()) {
                Integer ti = rs.getInt(1);
                topCategoryMap.put(ti, new Object[]{rs.getInt(2)});
            }

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID, new ArrayList<Integer>(topCategoryMap.keySet()));

            ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

            int ii = itemDV.size();
            while (ii > 0) {
                ii--;
                ItemData itemD = (ItemData) itemDV.get(ii);
                if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {
                    throw new RemoteException(
                            "Error. CatalogInformationBean.getTopCatalogCategories()" +
                                    " No item_assoc - item consitency in the Catalog." +
                                    " Catalog id: " +   pCatalogId +
                                    " Item id: " + itemD.getItemId());
                }

                CatalogCategoryData ccD = new CatalogCategoryData();
                ccD.setItemData(itemD);
                if (topCategoryMap.containsKey(itemD.getItemId())) {
                    ccD.setSortOrder((Integer) topCategoryMap.get(itemD.getItemId())[0]);
                }
                catalogCategoryDV.add(ccD);
            }

            assignMajorCategory(con, catalogCategoryDV);

        } catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. CatalogInformationBean.getTopCatalogCategories() Naming Exception happened");
        } catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. CatalogInformationBean.getTopCatalogCategories() SQL Exception happened");
        } finally {
        	try {
        		if (stmt != null) {
        			stmt.close();
        		}
        		if (rs != null) {
        			rs.close();
        		}
        		closeConnection(con);
        	}
        	catch (Exception e) {
        		//nothing to do here
        	}
        }
        return catalogCategoryDV;
    }

  private void assignMajorCategory(Connection pCon,
		  CatalogCategoryDataVector pCategories) throws SQLException {
	  
	if (!Utility.isSet(pCategories)) {
		return;
	}
	
    IdVector catalogCategoryIds = new IdVector();
    Iterator<CatalogCategoryData> iter = pCategories.iterator();
    while (iter.hasNext()) {
      CatalogCategoryData ccD = iter.next();
      catalogCategoryIds.add(new Integer(ccD.getItemData().getItemId()));
    }

    DBCriteria dbc = new DBCriteria();
    dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, catalogCategoryIds);
    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                   RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);
    String majorAssocReq =
      ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID, dbc);
    dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
    ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon, dbc);
    if (itemAssocDV.isEmpty())
    	return;

    dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID, majorAssocReq);
    dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                   RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
    dbc.addOrderBy(ItemDataAccess.ITEM_ID);
    ItemDataVector majorCatDV = ItemDataAccess.select(pCon, dbc);

    iter = pCategories.iterator();
    while (iter.hasNext()) {
      CatalogCategoryData ccD = iter.next();
      int cId = ccD.getItemData().getItemId();
      Iterator iter1 = itemAssocDV.iterator();
      int majCatId = 0;
      mmm:while (iter1.hasNext()) {
        ItemAssocData iaD = (ItemAssocData) iter1.next();
        int acId = iaD.getItem1Id();
        if (acId > cId) {
          break;
        }
        if (acId == cId) {
          int amId = iaD.getItem2Id();
          Iterator iter2 = majorCatDV.iterator();
          while (iter2.hasNext()) {
            ItemData iD = (ItemData) iter2.next();
            int mId = iD.getItemId();
            if (mId > amId) {
              break mmm;
            }
            if (mId == amId) {
              if (majCatId == 0) {
                majCatId = mId;
                ccD.setMajorCategory(iD);
              } else if (majCatId != mId) {
                ccD.setMajorCategory(null);
                logError("Category has more than one major category. Category id = " + cId);
                break mmm;
              }
            }
          }
        }
      }
    }
  }

  private void populateItemInformation(Connection pCon,
		  CatalogCategoryDataVector pCategories) throws SQLException {
	  
	if (!Utility.isSet(pCategories)) {
		return;
	}
	
	Map<Integer, CatalogCategoryData> itemIdToCatalogCategoryMap = Utility.toMap(pCategories);
    IdVector itemIds = new IdVector();
    Iterator<CatalogCategoryData> catalogCategoryIterator = pCategories.iterator();
    while (catalogCategoryIterator.hasNext()) {
      itemIds.add(new Integer(catalogCategoryIterator.next().getCatalogCategoryId()));
    }

    DBCriteria dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIds);
    ItemDataVector items = ItemDataAccess.select(pCon, dbc);
    
    Iterator<ItemData> itemIterator = items.iterator();
    while (itemIterator.hasNext()) {
    	ItemData item = itemIterator.next();
    	itemIdToCatalogCategoryMap.get(item.getItemId()).setItemData(item);
    }
  }
  
  /**
   *  Gets service
   *
   *@param  pServiceId                 the service identifier
   *@return                            ServiceData
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0, DataNotFoundException
   */
  public ServiceData getService(int pServiceId) throws RemoteException, DataNotFoundException {
    Connection con = null;
    try {
      con = getConnection();
      ServiceDAO serviceDAO = mCacheManager.getServiceDAO();
      ServiceData serviceD = serviceDAO.getServiceData(con, pServiceId);
     return serviceD;
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getProduct() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getProduct() SQL Exception happened");
    } catch (Exception e) {
        throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets product
   *
   *@param  pProductId                 the product identifier
   *@return                            CatalogCategoryDataVector
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0,
   *      DataNotFoundException
   */
  public ProductData getProduct(int pProductId) throws RemoteException,
    DataNotFoundException {
      return getProduct ( pProductId, null);
  }
  public ProductData getProduct(int pProductId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    ProductData productD = null;
    try {
      con = getConnection();
      productD = readDbProductData(con, 0, pProductId);
      if (productD.hasMessages()) {
        logError("getProduct: " + productD.getMessages());
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getProduct() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getProduct() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return productD;
  }

  public ProductDataVector getProductCollection(IdVector pIds) throws
    RemoteException, DataNotFoundException {
    return getCatalogClwProductCollection(0, pIds);
  }

  public ProductDataVector getProductCollection(IdVector pIds, int siteId) throws
  RemoteException, DataNotFoundException {
  return getCatalogClwProductCollection(0, pIds, siteId, null);
  }
  public ProductDataVector getProductCollection(IdVector pIds, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws
  RemoteException, DataNotFoundException {
  return getCatalogClwProductCollection(0, pIds, siteId, pCategToCostCenterView);
  }

  public ProductDataVector getCatalogClwProductCollection
  (int pCatalogId, IdVector pIds) throws RemoteException,
  DataNotFoundException {
	  return getCatalogClwProductCollection(pCatalogId,pIds,0);
  }

  public ProductDataVector getCatalogClwProductCollection
    (int pCatalogId, IdVector pIds, int siteId) throws RemoteException,
    DataNotFoundException {
  return getCatalogClwProductCollection(pCatalogId,pIds,siteId, null);
}
  public ProductDataVector getCatalogClwProductCollection
    (int pCatalogId, IdVector pIds, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    ProductDataVector productDV = new ProductDataVector();
    try {
      con = getConnection();
      ProductDAO tpdao = new ProductDAO(con, pIds);
      if (pCatalogId > 0) {
    	  logInfo("getCatalogClwProductCollection catalog id > 0");
    	  tpdao.updateCatalogInfo(con, pCatalogId,siteId, pCategToCostCenterView);
      }
      productDV = tpdao.getResultVector();

    } catch (Exception e) {
      e.printStackTrace();
      logError("getCatalogClwProductCollection: " + e);
    } finally {
      closeConnection(con);
    }

    return productDV;
  }


  /**
   *  Gets catalog products, which do not have parent categories or parent
   *  products.
   *
   *@param  pCatalogId                 the catalog identifier
   *@return                            CatalogCategoryDataVector
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0,
   *      DataNotFoundException
   */

  public ProductDataVector getTopCatalogProducts(int pCatalogId) throws
  RemoteException, DataNotFoundException {
	  return getTopCatalogProducts(pCatalogId,0);
  }
  public ProductDataVector getTopCatalogProducts(int pCatalogId,int siteId ) throws
  RemoteException, DataNotFoundException {
          return getTopCatalogProducts(pCatalogId,siteId, null);
  }

  public ProductDataVector getTopCatalogProducts(int pCatalogId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws
    RemoteException, DataNotFoundException {
    Connection con = null;
    ProductDataVector productDV = new ProductDataVector();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      IdVector productIds = CatalogStructureDataAccess.selectIdOnly(con,
        CatalogStructureDataAccess.ITEM_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, productIds);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      Vector relTypeV = new Vector();
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);
      dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, relTypeV);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      IdVector subProductIds = ItemAssocDataAccess.selectIdOnly(con,
        ItemAssocDataAccess.ITEM1_ID, dbc);
      int subSize = subProductIds.size();

      IdVector topProductIds = null;
      if (subSize == 0) {
        topProductIds = productIds;
      } else {
        topProductIds = new IdVector();
        int ii = 0;
        int jj = 0;
        Integer prodId;
        Integer subProdId;
        for (; ii < productIds.size(); ii++) {
          prodId = (Integer) productIds.get(ii);
          if (jj == subSize) {
            topProductIds.add(prodId);
          } else {
            subProdId = (Integer) subProductIds.get(jj);
            if (prodId.equals(subProdId)) {
              jj++;
            } else if (prodId.compareTo(subProdId) < 0) {
              topProductIds.add(prodId);
            } else if (prodId.compareTo(subProdId) > 0) {
              throw new RemoteException("Error. CatalogInformationBean.getTopCatalogProducts() Program logic error error");
            }
          }
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, topProductIds);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      ProductDAO tpdao = new ProductDAO(con, itemDV);
      tpdao.updateCatalogInfo(con, pCatalogId, siteId, pCategToCostCenterView);
      productDV = tpdao.getResultVector();

    } catch (Exception exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("getTopCatalogProducts: Exception");
    } finally {
      closeConnection(con);
    }
    return productDV;
  }

  /**
   *  Gets items, which do not have parent categories or parent
   *  products.
   *@param  pCatalogId                 the catalog identifier
   *@return                            CatalogItemViewVector
   *@throws  RemoteException           Required by EJB 1.0,
   */
  public CatalogItemViewVector getTopCatalogItems(int pCatalogId) throws
    RemoteException {
    Connection con = null;
    CatalogItemViewVector itemVwV = new CatalogItemViewVector();
    try {
      con = getConnection();
      String sql =
        " select item_id from (" +
        " select cs.item_id, ia.item1_id " +
        " from CLW_ITEM_ASSOC ia, CLW_CATALOG_STRUCTURE cs" +
        " where cs.CATALOG_STRUCTURE_CD = 'CATALOG_PRODUCT'" +
        "   and cs.CATALOG_ID = " + pCatalogId +
        "   and ia.CATALOG_ID(+) = " + pCatalogId +
        "   and ia.ITEM_ASSOC_CD(+) = 'PRODUCT_PARENT_CATEGORY'" +
        "   and ia.ITEM1_ID(+) = cs.ITEM_ID" +
        " )" +
        " where item1_id is null";

      logDebug(
        "CatalogInformationBean IIIIIIIIIIIIIIIIIIIIIIIIIII sql: " + sql);

      IdVector topProductIds = new IdVector();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int itemId = rs.getInt(1);
        topProductIds.add(new Integer(itemId));
      }
      rs.close();
      stmt.close();

      if (topProductIds.size() > 0) {
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, topProductIds);
        ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
        for (int ii = 0; ii < itemDV.size(); ii++) {
          ItemData iD = (ItemData) itemDV.get(ii);
          CatalogItemView ciVw = CatalogItemView.createValue();
          int iid = iD.getItemId();
          ciVw.setItemId(iid);
          ciVw.setSkuNum(iD.getSkuNum());
          ciVw.setName(iD.getShortDesc());
          itemVwV.add(ciVw);
        }
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getTopCatalogItems() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getTopCatalogItems() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return itemVwV;
  }

  /**
   *  Gets catalog child categories for category or product
   *
   *@param  pCatalogId        the catalog identifier
   *@param  pParentId         parent category or product id
   *@return                   CatalogCategoryDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */
  public CatalogCategoryDataVector getCatalogChildCategories( int pCatalogId, int pParentId) throws RemoteException {
    Connection con = null;

    CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();
    try {
      if (con == null) {
        con = getConnection();
      }
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pParentId);
      Vector relTypeV = new Vector();
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
      dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, relTypeV);
      String subCategoryReq = ItemAssocDataAccess.getSqlSelectIdOnly(
        ItemAssocDataAccess.ITEM1_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, subCategoryReq);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      int subSize = itemDV.size();
      for (int ii = 0; ii < itemDV.size(); ii++) {
        ItemData itemD = (ItemData) itemDV.get(ii);
        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {
          throw new RemoteException(
            "Error. CatalogInformationBean.getCatalogChildCategories() No item_assoc - item consitency in the Catalog. Catalog id: " +
            pCatalogId + " Item id: " + itemD.getItemId());
        }
        CatalogCategoryData ccD = new CatalogCategoryData();
        ccD.setItemData(itemD);
        catalogCategoryDV.add(ccD);
      }

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogChildCategories()() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogChildCategories()() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogCategoryDV;
  }


  /**
   *  Gets catalog child products for category or product
   *
   *@param  pCatalogId                 the catalog identifier
   *@param  pParentId                  parent category or product id
   *@return                            ProductDataVector
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           Required by EJB 1.0
   */
  public ProductDataVector getCatalogChildProducts(int pCatalogId,
		    int pParentId) throws RemoteException, DataNotFoundException {
	  return getCatalogChildProducts(pCatalogId, pParentId,0);
  }


  public ProductDataVector getCatalogChildProducts(int pCatalogId,
    int pParentId, int siteId) throws RemoteException, DataNotFoundException {
    Connection con = null;
    ProductDataVector productDV = new ProductDataVector();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pParentId);
      Vector relTypeV = new Vector();
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);
      dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, relTypeV);
      String subProductReq = ItemAssocDataAccess.getSqlSelectIdOnly(
        ItemAssocDataAccess.ITEM1_ID, dbc);
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, subProductReq);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      int subSize = itemDV.size();

      IdVector thisCatIds = new IdVector();
      for (int ii = 0; ii < itemDV.size(); ii++) {
        ItemData itemD = (ItemData) itemDV.get(ii);
        thisCatIds.add(new Integer(itemD.getItemId()));
      }
      logInfo("CatInfoBean----pCatalogId "+pCatalogId+" thisCatIds "+thisCatIds+" siteId "+siteId);
      productDV = getCatalogClwProductCollection(pCatalogId, thisCatIds,siteId);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("getCatalogChildProducts: Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("getCatalogChildProducts: SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return productDV;
  }

  //*************************************************************************
   /**
    *  Gets catalog child items for category or product
    *
    *@param  pCatalogId                 the catalog identifier
    *@param  pParentId                  parent category or product id
    *@return                            List of CatalogNodes objects
    *@throws  RemoteException           Required by EJB 1.0
    */
   public List getCatalogNodes(int pCatalogId,
                               CatalogCategoryDataVector pCategories,
                               int pLevel) throws RemoteException {
     Connection con = null;
     LinkedList nodes = new LinkedList();
     try {
       con = getConnection();
       DBCriteria dbc = new DBCriteria();
       IdVector categoryIds = new IdVector();
       for (int ii = 0; ii < pCategories.size(); ii++) {
         CatalogCategoryData catalogCategoryD = (CatalogCategoryData)
                                                pCategories.get(ii);
         categoryIds.add(new Integer(catalogCategoryD.getItemData().getItemId()));
       }
       dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
       dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID, categoryIds);
       Vector relTypeV = new Vector();
       relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
//        relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);
       relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
//        relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT);
       dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, relTypeV);
       String subProductReq = ItemAssocDataAccess.getSqlSelectIdOnly(
         ItemAssocDataAccess.ITEM1_ID, dbc);
       ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);
       dbc = new DBCriteria();
       dbc.addOneOf(ItemDataAccess.ITEM_ID, subProductReq);
       ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
       //Create nodes
       nodes = new LinkedList();
       Object[] assoc = itemAssocDV.toArray();
       Object[] elements = itemDV.toArray();
       for (int ii = 0; ii < pCategories.size(); ii++) {
         CatalogCategoryData catalogCategoryD = (CatalogCategoryData)
                                                pCategories.get(ii);
         int categoryId = catalogCategoryD.getItemData().getItemId();
         CatalogCategoryDataVector subCategories = new
           CatalogCategoryDataVector();
         CatalogItemViewVector subItems = new CatalogItemViewVector();
         for (int jj = 0; jj < assoc.length; jj++) {
           ItemAssocData iaD = (ItemAssocData) assoc[jj];
           if (iaD.getItem2Id() == categoryId) {
             for (int kk = 0; kk < elements.length; kk++) {
               int elementId = iaD.getItem1Id();
               ItemData iD = (ItemData) elements[kk];
               if (elementId == iD.getItemId()) {
                 if (RefCodeNames.ITEM_TYPE_CD.PRODUCT.equals(iD.getItemTypeCd())) {
                   CatalogItemView ciVw = CatalogItemView.createValue();
                   ciVw.setItemId(elementId);
                   ciVw.setSkuNum(iD.getSkuNum());
                   ciVw.setName(iD.getShortDesc());
                   subItems.add(ciVw);
                 } else if (RefCodeNames.ITEM_TYPE_CD.CATEGORY.equals(iD.
                   getItemTypeCd())) {
                   CatalogCategoryData ccD = new CatalogCategoryData();
                   ccD.setTreeLevel(pLevel + 1);
                   ccD.setParentCategory(catalogCategoryD.getItemData());
                   ccD.setItemData(iD);
                   subCategories.add(ccD);
                 }
               }
             }
           }
         }
         assignMajorCategory(con, subCategories);

         //Create order
         CatalogNode node = new CatalogNode(catalogCategoryD, pLevel,
                                            subCategories, subItems);
         nodes.add(node);
       }

     } catch (NamingException exc) {
       logError("exc.getMessage");
       exc.printStackTrace();
       throw new RemoteException("Error. CatalogInformationBean.getCatalogNodes() Naming Exception happened");
     } catch (SQLException exc) {
       logError("exc.getMessage");
       exc.printStackTrace();
       throw new RemoteException("Error. CatalogInformationBean.getCatalogNodes() SQL Exception happened");
     } finally {
       closeConnection(con);
     }
     return nodes;
   }

  /**
   *  Gets IdVector of all anscestor ids for the product or category
   *
   *@param  pCatalogId                              the catalog identifier
   *@param  pItemIdV                                Description of Parameter
   *@return                                         IdVector
   *@exception  RemoteException                     Description of Exception
   *@exception  DataNotFoundException               Description of Exception
   *@throws  RemoteException,DataNotFoundException
   */
  public IdVector getAllAncestors(int pCatalogId, IdVector pItemIdV) throws
    RemoteException, DataNotFoundException {
    Connection con = null;
    IdVector idV = new IdVector();
    try {
      con = getConnection();
      DBCriteria dbc;

      Vector relTypeV = new Vector();
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT);

      IdVector wrkV = new IdVector();
      wrkV.addAll(pItemIdV);

      while (wrkV.size() > 0) {
        dbc = new DBCriteria();
        dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, relTypeV);
        dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
        dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, wrkV);
        wrkV = ItemAssocDataAccess.selectIdOnly(con,
                                                ItemAssocDataAccess.ITEM2_ID,
                                                dbc);
        for (int ii = 0; ii < wrkV.size(); ) {
          Integer newId = (Integer) wrkV.get(ii);
          int jj = 0;
          for (; jj < idV.size(); jj++) {
            if (((Integer) idV.get(jj)).equals(newId)) {
              wrkV.remove(ii);
              break;
            }
          }
          if (jj == idV.size()) {
            idV.add(newId);
            ii++;
          }
        }
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getAllAncestors() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getAllAncestors() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return idV;
  }

  
  /**
   *  Gets all avaliable categories that belong to the supplied categories.
   *  @param pCatalogIds  the catalog identifiers
   *  @return ItemDataVector
   *  @throws            RemoteException Required by EJB 1.0
   */
  public ItemDataVector getCatalogCategories(IdVector pCatalogIds) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, pCatalogIds);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
      String inSql = CatalogStructureDataAccess.getSqlSelectIdOnly(
        CatalogStructureDataAccess.ITEM_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, inSql);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
      ItemDataVector idv = ItemDataAccess.select(con, dbc);
      return idv;
    } catch (Exception exc) {
      throw processException(exc);
    } finally {
      closeConnection(con);
    }
  }

  /*
   *  Gets the array-like catalog structure vector values to be used by the request.
   *  @param pCatalogId  the catalog identifier
   *  @return CatalogStructureDataVector
   *  @throws            RemoteException Required by EJB 1.0
   */
  public CatalogStructureDataVector getCatalogStructuresCollection(int
    pCatalogId) throws RemoteException {
    CatalogStructureDataVector struc = new CatalogStructureDataVector();
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      struc = CatalogStructureDataAccess.select(con, dbc);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogCategory() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogCategory() SQL Exception happened");
    } finally {
      closeConnection(con);
    }

    return struc;
  }
  
  /*
   * Gets the array-like item vector values to be used by the request.
   * @param pCatalogId  the catalog identifier
   * @return ItemDataVector
   * @throws            RemoteException Required by EJB 1.0
   *
   */
  public ItemDataVector getCatalogItemCollection(int
		  pCatalogId) throws RemoteException {
	  ItemDataVector itemDV = new ItemDataVector();
	  Connection con = null;
	  try {		  
		  con = getConnection();
		  DBCriteria dbc = new DBCriteria();
		  dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
		  dbc.addJoinCondition(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID, 
				  	ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID);
		  JoinDataAccess.selectTableInto(new ItemDataAccess(),itemDV,con,dbc,-1);
	  } catch (NamingException exc) {
		  logError("exc.getMessage");
		  exc.printStackTrace();
		  throw new RemoteException("Error. CatalogInformationBean.getCatalogItemCollection() Naming Exception happened");
	  } catch (SQLException exc) {
		  logError("exc.getMessage");
		  exc.printStackTrace();
		  throw new RemoteException("Error. CatalogInformationBean.getCatalogItemCollection() SQL Exception happened");
	  } finally {
		  closeConnection(con);
	  }

	  return itemDV;
  }
  
  /**
   * Retrieves a map of category to items for a catalog.
   * @param pSiteId
   * @param pAvailableCategoryIds
   * @param pShoppingItemRequest
   * @param pCategToCostCenterView
   * @return
   * @throws RemoteException
   */
  	public Map<Integer, ProductDataVector> getCatalogCategoryToItemMap(int pSiteId, IdVector pAvailableCategoryIds,
          ShoppingItemRequest pShoppingItemRequest, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

  		Connection con = null;
  		Map<Integer, ProductDataVector> returnValue = new HashMap<Integer, ProductDataVector>();
		if (pAvailableCategoryIds.isEmpty())
			return returnValue;
  		try {
  			con = getConnection();
  			DBCriteria dbc = new DBCriteria();
  			
  			//get the category to item associations for the catalog
  			dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.CATALOG_ID, pShoppingItemRequest.getShoppingCatalogId());
  			dbc.addJoinTableOneOf(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM2_ID, pAvailableCategoryIds);
  			dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
  			dbc.addJoinCondition(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM1_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID);
  			dbc.addJoinCondition(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.CATALOG_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID);
  			dbc.addJoinTableOneOf(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID, ShoppingDAO.getShoppingItemIdsRequest(con, pShoppingItemRequest));
  			dbc.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
  			//dbc.addJoinTableOrderBy(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM2_ID);
  			dbc.addJoinTableOrderBy(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM1_ID);
  			ItemAssocDataVector itemAssocs = new ItemAssocDataVector();
            JoinDataAccess.selectTableInto(new ItemAssocDataAccess(), itemAssocs, con, dbc, 0);

			//---- STJ-6114: Performance Improvements - Optimize Pollock 
			//applyPollockFilter(itemAssocs, pShoppingItemRequest);
			//----
  			
  			//iterate over the results to:
  			//	1) create a temporary map of category ids to item ids, and
  			//	2) create a list of all the item ids
  			IdVector allItemIds = new IdVector();
  			Map<Integer, List<Integer>> categoryIdToItemIds = new HashMap<Integer, List<Integer>>();
  			Iterator<ItemAssocData> itemAssocIterator = itemAssocs.iterator();
  			while (itemAssocIterator.hasNext()) {
  				ItemAssocData itemAssoc = (ItemAssocData)itemAssocIterator.next();
  				Integer categoryId = new Integer(itemAssoc.getItem2Id());
  				Integer itemId = new Integer(itemAssoc.getItem1Id());
  				List<Integer> categoryItemIds = categoryIdToItemIds.get(categoryId);
  				if (!Utility.isSet(categoryItemIds)) {
  					categoryItemIds = new ArrayList<Integer>();
  					categoryIdToItemIds.put(categoryId, categoryItemIds);
  				}
  				categoryItemIds.add(itemId);
  				allItemIds.add(itemId);
  			}
  			
  			//retrieve catalog information for the items
  			ProductDAO tpdao = new ProductDAO(con, allItemIds, false);
  			tpdao.populateCatalogInfo(con, pShoppingItemRequest.getShoppingCatalogId(), pSiteId);
  			Map<Integer, ProductData> productMap = Utility.toMap(tpdao.getResultVector());
  			
  			//finally, create the map of category ids to products that we will return
  			Iterator<Integer> categoryIdIterator = categoryIdToItemIds.keySet().iterator();
  			while (categoryIdIterator.hasNext()) {
  				Integer categoryId = categoryIdIterator.next();
  				List<Integer>itemIds = categoryIdToItemIds.get(categoryId);
  				ProductDataVector productDataVector = new ProductDataVector();
  				Iterator<Integer> itemIdIterator = itemIds.iterator();
  				while (itemIdIterator.hasNext()) {
  					Integer itemId = itemIdIterator.next();
  					productDataVector.add(productMap.get(itemId));
  				}
  				returnValue.put(categoryId, productDataVector);
  			}
  			return returnValue;
  		} 
  		catch (Exception e) {
  			throw new RemoteException("Exception occurred in getCatalogCategoryToItemMap");
  		} 
  		finally {
	  		closeConnection(con);
  		}
  	}

	///////////////////
  //---- STJ-6114: Performance Improvements - Optimize Pollock 
  private void applyPollockFilter(ItemAssocDataVector pItemAssocDV, ShoppingItemRequest pShoppingItemRequest) {
		if(pItemAssocDV==null) return;
		IdVector excludeItemIds = pShoppingItemRequest.getExcProductBundleFilterIds();
		IdVector includeItemIds = pShoppingItemRequest.getIncProductBundleFilterIds();
		if(excludeItemIds==null && includeItemIds==null) {
			logInfo("YK. Pollock filters not set");
		    return;
		}
		
		logInfo("applyPollockFilter() ===> ORIGINAL: pItemAssocDV number =" + pItemAssocDV.size());
		Map<Integer, ItemAssocData> itemAssocMap = new HashMap();
		IdVector productIds = new IdVector();
		
		for(Iterator iter = pItemAssocDV.iterator(); iter.hasNext();){
			ItemAssocData iaD = (ItemAssocData) iter.next();
			itemAssocMap.put(iaD.getItem1Id(), iaD);
			productIds.add(iaD.getItem1Id());
		}
		
		if(Utility.isSet(includeItemIds)) {
			productIds.retainAll(includeItemIds);
		}
		
		if(Utility.isSet(excludeItemIds)) {
			productIds.removeAll(excludeItemIds);
		}
		
		Collections.sort(productIds);
		pItemAssocDV.clear();
		for(Iterator iter = productIds.iterator(); iter.hasNext();){
			Integer productId = (Integer) iter.next();
			pItemAssocDV.add(itemAssocMap.get(productId));
		}
		logInfo("applyPollockFilter() ===> RESULT: pItemAssocDV number =" + pItemAssocDV.size());
	}

  ////////////////////

  /**
   *  Gets the OrderGuideCollection attribute of the CatalogInformationBean
   *  object
   *
   *@param  pCatalogId           Description of Parameter
   *@return                      The OrderGuideCollection value
   *@exception  RemoteException  Description of Exception
   */
  public OrderGuideDataVector getOrderGuideCollection(int pCatalogId) throws
    RemoteException {
    Connection con = null;
    OrderGuideDataVector orderGuideDV = new OrderGuideDataVector();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
      orderGuideDV = OrderGuideDataAccess.select(con, dbc);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getOrderGuideCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getOrderGuideCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return orderGuideDV;
  }


  /**
   *  Gets the Distributor collection for the catalog. Applies of filter for
   *  distributor name (contains ignore case) Picks up only BusEntityData and
   *  AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@return                      The ODistributorDataVector value
   *@exception  RemoteException  Description of Exception
   */
  public DistributorDataVector getDistributorCollection(int pCatalogId,
    String pFilter) throws RemoteException {
    return getDistributorCollection(pCatalogId, pFilter,
                                    SearchCriteria.CONTAINS_IGNORE_CASE,
                                    SearchCriteria.ORDER_BY_SHORT_DESC);
  }


  /**
   *  Gets the Distributor collection for the catalog. Applies of filter for
   *  distributor name (contains ignore case) Picks up only BusEntityData and
   *  AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@param  pMatch               Match type (exect match, starts with, etc)
   *@param  pOrder               Result order (by id, by short description)
   *@return                      The ODistributorDataVector value
   *@exception  RemoteException  Description of Exception
   */
  public DistributorDataVector getDistributorCollection(int pCatalogId,
    String pFilter,
    int pMatch,
    int pOrder) throws RemoteException {
    DistributorDataVector distDV =  getDistributorCollection(pCatalogId, pFilter, true, pMatch, pOrder);
    return distDV;
  }

  /**
   *  Gets the Distributor collection for the catalog. Applies of filter for
   *  distributor name (contains ignore case) Picks up only BusEntityData and
   *  AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@param  pShowInactiveFl      Filters out inactive distributors if false
   *@param  pMatch               Match type (exect match, starts with, etc)
   *@param  pOrder               Result order (by id, by short description)
   *@return                      The ODistributorDataVector value
   *@exception  RemoteException  Description of Exception
   */
  public DistributorDataVector getDistributorCollection(int pCatalogId,
    String pFilter,
    boolean pShowInactiveFl,
    int pMatch,
    int pOrder) throws RemoteException {
    Connection con = null;
    DistributorDataVector distributorDV = new DistributorDataVector();
    try {
      con = getConnection();
      BusEntityDataVector busEntityDV =
        getBusEntityCollection(con,
                               pCatalogId,
                               RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR,
                               pFilter,
                               pShowInactiveFl,
                               pMatch,
                               pOrder);
      DBCriteria dbc = new DBCriteria();
      String busEntReq =
        getBusEntityCollectionRequest(pCatalogId,
                                      RefCodeNames.BUS_ENTITY_TYPE_CD.
                                      DISTRIBUTOR, pFilter,
                                      pShowInactiveFl, pMatch);
      dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID, busEntReq);
      dbc.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
      dbc.addOrderBy(AddressDataAccess.BUS_ENTITY_ID);
      AddressDataVector addressDV = AddressDataAccess.select(con, dbc);

      //Now combine these vectors into Distributr vector.
      //For catalog purposes we are interested only in Name, City and State
      for (int ii = 0; ii < busEntityDV.size(); ii++) {
        BusEntityData busEntityD = (BusEntityData) busEntityDV.get(ii);
        int id = busEntityD.getBusEntityId();
        AddressData addressD = null;
        for (int jj = 0; jj < addressDV.size(); jj++) {
          AddressData aD = (AddressData) addressDV.get(jj);
          int id1 = aD.getBusEntityId();
          if (id == id1) {
            addressD = aD;
            break;
          }
          if (id < id1) {
            break;
          }
        }
        DistributorData distributorD = new DistributorData(busEntityD, addressD);
        distributorDV.add(distributorD);
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return distributorDV;
  }


  /**
   *  Gets the DistributorData for the catalog. Picks up only BusEntityData
   *  and AddressData objects
   *
   *@param  pCatalogId                 Description of Parameter
   *@param  pDistrId                   Description of Parameter
   *@return                            The ODistributorData value
   *@exception  RemoteException
   *@exception  DataNotFoundException
   */
  public DistributorData getDistributor(int pCatalogId, int pDistrId) throws
    RemoteException, DataNotFoundException {
    Connection con = null;
    DistributorData distributorD = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
      String catalogAssocReq =
        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.
                                                  BUS_ENTITY_ID, dbc);
      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                     RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, pDistrId);
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, catalogAssocReq);
      BusEntityDataVector busEntityDV = BusEntityDataAccess.select(con, dbc);
      if (busEntityDV.size() == 0) {
        throw new DataNotFoundException("No Distributor BusEntityData found for catalog: " + pCatalogId);
      }
      if (busEntityDV.size() > 1) {
        throw new RemoteException("More than one Distributor BusEntityData found for catalog: " +
          pCatalogId);
      }
      BusEntityData busEntityD = (BusEntityData) busEntityDV.get(0);
      int distrId = busEntityD.getBusEntityId();

      dbc = new DBCriteria();
      dbc.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, distrId);
      dbc.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
      AddressDataVector addressDV = AddressDataAccess.select(con, dbc);
      if (addressDV.size() > 1) {
        throw new RemoteException("More than one primary address for distributor: " + distrId);
      }
      AddressData addressD = null;
      if (addressDV.size() == 1) {
        addressD = (AddressData) addressDV.get(0);
      }

      distributorD = new DistributorData(busEntityD, addressD);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return distributorD;
  }


  /**
   *  Gets the Account collection for the catalog. Applies of filter for
   *  account name (contains ignore case) Picks up only BusEntityData and
   *  AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@param  pMatch               Match type (exect match, starts with, etc)
   *@param  pOrder               Result order (by id, by short description)
   *@return                      The AccountDataVector value
   *@exception  RemoteException  Description of Exception
   */

  public AccountDataVector getAccountCollection(int pCatalogId,
                                                String pFilter,
                                                int pMatch,
                                                int pOrder) throws
    RemoteException {
    AccountDataVector accountDV =
      getAccountCollection(pCatalogId, pFilter, true, pMatch, pOrder);
    return accountDV;
  }

  /**
   *  Gets the Account collection for the catalog. Applies of filter for
   *  account name (contains ignore case) Picks up only BusEntityData and
   *  AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@param  pShowInactiveFl      Filters out inactive accounts if false
   *@param  pMatch               Match type (exect match, starts with, etc)
   *@param  pOrder               Result order (by id, by short description)
   *@return                      The AccountDataVector value
   *@exception  RemoteException  Description of Exception
   */

  public AccountDataVector getAccountCollection(int pCatalogId,
                                                String pFilter,
                                                boolean pShowInactiveFl,
                                                int pMatch,
                                                int pOrder) throws
    RemoteException {
    Connection con = null;
    AccountDataVector accountDV = new AccountDataVector();
    try {
      con = getConnection();
      BusEntityDataVector busEntityDV =
        getBusEntityCollection(con,
                               pCatalogId,
                               RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,
                               pFilter,
                               pShowInactiveFl,
                               pMatch,
                               pOrder);
      DBCriteria dbc = new DBCriteria();
      DBCriteria dbc2 = new DBCriteria();
      String busEntityReq =
        getBusEntityCollectionRequest(pCatalogId,
                                      RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,
                                      pFilter,
                                      pShowInactiveFl, pMatch);
      dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID, busEntityReq);
      dbc.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
      dbc.addOrderBy(AddressDataAccess.BUS_ENTITY_ID);

      dbc2.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, busEntityReq);
      dbc2.addOrderBy(PropertyDataAccess.BUS_ENTITY_ID);
      dbc2.addIsNull(PropertyDataAccess.USER_ID);

      AddressDataVector addressDV = AddressDataAccess.select(con, dbc);
      PropertyDataVector propertyDV = PropertyDataAccess.select(con, dbc2);

      //Now combine these vectors into ACCOUNT vector.
      //For catalog purposes we are interested only in Name, City and State
      for (int ii = 0; ii < busEntityDV.size(); ii++) {
        BusEntityData busEntityD = (BusEntityData) busEntityDV.get(ii);
        int id = busEntityD.getBusEntityId();
        AddressData addressD = null;
        for (int jj = 0; jj < addressDV.size(); jj++) {
          AddressData aD = (AddressData) addressDV.get(jj);
          int id1 = aD.getBusEntityId();
          if (id == id1) {
            addressD = aD;
            break;
          }
          if (id < id1) {
            break;
          }
        }
        PropertyData propertyD = null;
        for (int kk = 0; kk < propertyDV.size(); kk++) {
          PropertyData pD = (PropertyData) propertyDV.get(kk);
          int id1 = pD.getBusEntityId();
          if (id == id1) {
            propertyD = pD;
            break;
          }
          if (id < id1) {
            break;
          }
        }

        AccountData accountD = AccountData.createValue();
        accountD.setBusEntity(busEntityD);
        accountD.setPrimaryAddress(addressD);
        accountD.setAccountType(propertyD);
        accountDV.add(accountD);
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return accountDV;
  }


  /**
   *  Gets the Site collection for the catalog. Applies of filter for site
   *  name (contains ignore case) Picks up only BusEntityData,
   *  BusEntityAssocData and shipping AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@param  pMatch               Match type (exect match, starts with, etc)
   *@param  pOrder               Result order (by id, by short description)
   *@return                      The SiteDataVector value
   *@exception  RemoteException  Description of Exception
   */

  public SiteDataVector getSiteCollection(int pCatalogId,
                                          String pFilter,
                                          int pMatch,
                                          int pOrder) throws RemoteException {
    SiteDataVector siteDV =
      getSiteCollection(pCatalogId, pFilter, true, pMatch, pOrder);
    return siteDV;
  }

  /**
   *  Gets the Site collection for the catalog. Applies of filter for site
   *  name (contains ignore case) Picks up only BusEntityData,
   *  BusEntityAssocData and shipping AddressData objects
   *
   *@param  pCatalogId           Description of Parameter
   *@param  pFilter              Filter string. If is null or empty does not
   *      apply
   *@param  pShowInactiveFl      Filters out inactive sites if false
   *@param  pMatch               Match type (exect match, starts with, etc)
   *@param  pOrder               Result order (by id, by short description)
   *@return                      The SiteDataVector value
   *@exception  RemoteException  Description of Exception
   */

  public SiteDataVector getSiteCollection(int pCatalogId,
                                          String pFilter,
                                          boolean pShowInactiveFl,
                                          int pMatch,
                                          int pOrder) throws RemoteException {
    Connection con = null;
    SiteDataVector siteDV = new SiteDataVector();
    try {
      con = getConnection();
      BusEntityDataVector busEntityDV =
        getBusEntityCollection(con,
                               pCatalogId,
                               RefCodeNames.BUS_ENTITY_TYPE_CD.SITE,
                               pFilter,
                               pShowInactiveFl,
                               pMatch,
                               pOrder);

      APIAccess factory = new APIAccess();
      Site siteBean = factory.getSiteAPI();

      for (int ii = 0; ii < busEntityDV.size(); ii++) {
        BusEntityData busD = (BusEntityData)busEntityDV.get(ii);
        try {
          SiteData siteD = siteBean.getSite(busD.getBusEntityId(), 0,true);
          siteDV.add(siteD);
        } catch (DataNotFoundException nodata) {
          logError("Error. CatalogInformationBean.getSiteCollection(): " + nodata.getMessage());
        }
      }
    }

    catch (APIServiceAccessException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getSiteCollection(), APIAccess error.");
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getDistributorCollection() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return siteDV;
  }

  /**
   *  Gets the site ids associated with the supplied catalogId
   *
   *@param  pCatalogId           Description of Parameter
   *@return                      The SiteIdVector value
   *@exception  RemoteException  Description of Exception
   */
  public IdVector getSiteIds(int pCatalogId) throws RemoteException {
    Connection con = null;
    IdVector siteIds = new IdVector();
    try {
      con = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                      RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
      crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      crit.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);
      CatalogAssocDataAccess cda = new CatalogAssocDataAccess();
      String bus = "BUS_ENTITY_ID";
      siteIds = cda.selectIdOnly(con, bus, crit);

    } catch (NamingException exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getSiteIds() Naming Exception happened");
    } catch (SQLException exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getSiteIds() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return siteIds;
  }


  public ProductData s_productData
    (Connection pConn,
     int pCatalogId, int pProductId) throws RemoteException,
    DataNotFoundException {

    ProductData productD = null;
    try {
      productD = fetchProductData(pConn, pCatalogId, pProductId);
    } catch (NamingException exc) {
      exc.printStackTrace();
      throw new RemoteException("s_productData" + exc);
    } catch (SQLException exc) {
      exc.printStackTrace();
      throw new RemoteException("s_productData" + exc);
    }

    return productD;
  }

  private ProductData fetchProductData(Connection pConn,
                                       int pCatalogId, int pProductId) throws
    RemoteException, DataNotFoundException,
    NamingException, SQLException {
    return readDbProductData(pConn, pCatalogId, pProductId);
  }

  /**
   *@param  pCatalogId                 Description of Parameter
   *@param  pProductId                 Description of Parameter
   *@return                            The CatalogClwProduct value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ProductData getCatalogClwProduct
    (int pCatalogId, int pProductId) throws RemoteException,
    DataNotFoundException {
    return getCatalogClwProduct(pCatalogId, pProductId, 0, 0);
  }

  public ProductData getCatalogClwProduct
  (int pCatalogId, int pProductId, int pDistId) throws RemoteException,
  DataNotFoundException {
	  return getCatalogClwProduct(pCatalogId, pProductId, pDistId, 0);
  }

  public ProductData getCatalogClwProduct
    (int pCatalogId, int pProductId, int pDistId, int siteId) throws RemoteException,
    DataNotFoundException {
  return getCatalogClwProduct(pCatalogId, pProductId, pDistId, siteId, null);
}
  public ProductData getCatalogClwProduct
    (int pCatalogId, int pProductId, int pDistId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    ProductData productD = null;
    try {
      con = getConnection();
      productD = readDbProductData(con, pCatalogId, pProductId, pDistId, siteId, pCategToCostCenterView);
      if (productD.hasMessages()) {
        logError("getCatalogChildProducts: " + productD.getMessages());
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("getCatalogClwProduct: Naming Exception");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("getCatalogClwProduct: SQL Exception.");
    } finally {
      closeConnection(con);
    }

    return productD;
  }

  /**
   *  Gets main for all stores catalog id. There must be one and
   *  only one such catalog
   *
   *@return                   System catalog id or 0 if not found
   *@throws  RemoteException  (Required by EJB 1.0) and DataNotFoundException
   */
  public int getSystemCatalogId() throws RemoteException {
    Connection con = null;
    DBCriteria dbc;
    int systemId = 0;
    try {
      con = getConnection();
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                     RefCodeNames.CATALOG_TYPE_CD.SYSTEM);
      IdVector idv = CatalogDataAccess.selectIdOnly(con,
        CatalogDataAccess.CATALOG_ID, dbc);
      if (idv.size() > 0) {
        systemId = ((Integer) idv.get(0)).intValue();
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getSystemCatalogId() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getSystemCatalogId() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return systemId;
  }


  /**
   *  Gets store catalog for the store. System catalog can be a master for one
   *  store
   *
   *@param  pStoreId          Description of Parameter
   *@return                   System catalog id or 0 if not found
   *@throws  RemoteException  (Required by EJB 1.0) and DataNotFoundException
   */
  public int getStoreCatalogId(int pStoreId) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      return BusEntityDAO.getStoreCatalogId(con, pStoreId);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }


  /**
   *  Gets superior catalog.
   *
   *@param  pCatalogId                 Description of Parameter
   *@return                            CatalogData superior catalog or null if
   *      does not exist
   *@exception  DataNotFoundException  Description of Exception
   *@throws  RemoteException           (Required by EJB 1.0) and
   *      DataNotFoundException
   */
  public CatalogData getSuperCatalog(int pCatalogId) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    DBCriteria dbc;
    CatalogData superCatalogD = null;
    try {
      con = getConnection();
      //Return null if catalog is system catalog
      CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId);
      if (catalogD.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM)) {
        return null;
      }
      //Return system catalog if current catalog is Store Catalog
      // or Estimator Catalog
      if (catalogD.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.STORE) ||
          catalogD.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR)) {
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SYSTEM);
        CatalogDataVector superCatalogDV = CatalogDataAccess.select(con, dbc);
        if (superCatalogDV.size() == 0) {
          return null;
          //no system catalog so far
        }
        if (superCatalogDV.size() > 1) {
          throw new RemoteException("Error. CatalogInformationBean.getSuperCatalog() More than one system catalog found.");
        }
        return (CatalogData) superCatalogDV.get(0);
      }

      //Determine store catalog
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
      IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
      if (storeIdV.size() > 1) {
        throw new RemoteException("Error. CatalogInformationBean.getSuperCatalog() Catalog belongs to more than one store. CatalogId: " + pCatalogId);
      }

      CatalogData storeCatalogD = null;
      if (storeIdV.size() == 1) {
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, (Integer) storeIdV.get(0));
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        String storeCatReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatReq);
        CatalogDataVector storeCatalogDV = CatalogDataAccess.select(con, dbc);
        if (storeCatalogDV.size() > 1) {
          logError(
            "Error. CatalogInformationBean.getSuperCatalog() There are more than one store type catalog for the store. StoreId: " +
            (Integer) storeIdV.get(0));
        }

        //Return store catalog if current catalog is Account Catalog
        if (catalogD.getCatalogTypeCd().equals(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT)) {
          if (storeCatalogDV.size() == 0) {
            return null;
          } else {
            return (CatalogData) storeCatalogDV.get(0);
          }
        }

        //Determine account catalog
        //Determine account
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        String accountReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
        IdVector accountIdV = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
        if (accountIdV.size() > 0) {
          dbc = new DBCriteria();
          dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, accountReq);
          dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
          String catalogAccountReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

          dbc = new DBCriteria();
          dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAccountReq);
          dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
          dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
          CatalogDataVector accountCatalogDV = CatalogDataAccess.select(con, dbc);
          if (accountCatalogDV.size() > 1) {
            logError("Error. CatalogInformationBean.getSuperCatalog() The catalog belongs to more than one account with different account catalogs. Catalog Id: " +
                     pCatalogId);
          }
          if (accountCatalogDV.size() == 0) {
            if (storeCatalogDV.size() == 0) {
              return null;
            } else {
              return (CatalogData) storeCatalogDV.get(0);
            }
          } else {
            return (CatalogData) accountCatalogDV.get(0);
          }
        }

        //Determine site
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                       RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
        String siteReq = CatalogAssocDataAccess.getSqlSelectIdOnly(
          CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
        IdVector siteIdV = CatalogAssocDataAccess.selectIdOnly(con,
          CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
        if (siteIdV.size() > 0) {
          dbc = new DBCriteria();
          dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteReq);
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                         RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
          accountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
            BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
          accountIdV = BusEntityAssocDataAccess.selectIdOnly(con,
            BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
          if (accountIdV.size() == 0) {
            logError("Error. CatalogInformationBean.getSuperCatalog() The catalog belongs to site(s), which doesn't belog to account. Catalog Id: " +
                     pCatalogId);
          }

          dbc = new DBCriteria();
          dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, accountReq);
          dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
          String catalogAccountReq = CatalogAssocDataAccess.getSqlSelectIdOnly(
            CatalogAssocDataAccess.CATALOG_ID, dbc);

          dbc = new DBCriteria();
          dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAccountReq);
          dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                         RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
          CatalogDataVector accountCatalogDV = CatalogDataAccess.select(con,
            dbc);
          if (accountCatalogDV.size() > 1) {
            logError("Error. CatalogInformationBean.getSuperCatalog() The catalog belongs to more than one account with different account catalogs. Catalog Id: " +
                     pCatalogId);
          }
          if (accountCatalogDV.size() == 0) {
            if (storeCatalogDV.size() == 0) {
              return null;
            } else {
              return (CatalogData) storeCatalogDV.get(0);
            }
          } else {
            return (CatalogData) accountCatalogDV.get(0);
          }
        }
        superCatalogD = storeCatalogD;

      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getSuperCatalog() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getSuperCatalog() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return superCatalogD;
  }


  /**
   *  Gets Busness entity object by its id
   *
   *@param  pBusEntityId               Description of Parameter
   *@return                            BusEntityData - requested object
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   *@trows                             RemoteException, DataNotFoundException
   */
  public BusEntityData getBusEntity(int pBusEntityId) throws RemoteException,
    DataNotFoundException {
    Connection con = null;
    BusEntityData busEntityD = null;
    try {
      con = getConnection();
      busEntityD = BusEntityDataAccess.select(con, pBusEntityId);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getBusEntity() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getBusEntity() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return busEntityD;
  }


  /**
   *  Determines wherther exists the catalog-bus entity association
   *
   *@param  pCatalogId        catalog identifier or 0 if any
   *@param  pBusEntityId      busness entity identifier or 0 if any
   *@param  pAssocType        association type (CATALOG_STORE,
   *      CATALOG_ACCOUT,..) if null any association accepted
   *@return                   boolean true if found
   *@throws  RemoteException
   */
  public boolean doesCatalogAssocExist(int pCatalogId, int pBusEntityId,
                                       String pAssocType) throws
    RemoteException {
    Connection con = null;
    boolean retValue = false;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      if (pCatalogId > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
      }
      if (pBusEntityId > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
      }
      if (pAssocType != null && pAssocType.trim().length() > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, pAssocType);
      }
      if (pAssocType != null && pAssocType.trim().length() > 0) {
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, pAssocType);
      }
      IdVector idV = CatalogAssocDataAccess.selectIdOnly(con, dbc);
      if (idV.size() > 0) {
        retValue = true;
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.doesCatalogAssocExist() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.doesCatalogAssocExist() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return retValue;
  }


  /**
   *  Determine whether catalog exists
   *
   *@param  pCatalogId        Description of Parameter
   *@return                   true if exists and false if not
   *@throws  RemoteException
   */
  public boolean doesCatalogExist(int pCatalogId) throws RemoteException {
    Connection con = null;
    DBCriteria dbc;
    boolean existFlag = false;
    try {
      con = getConnection();
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, pCatalogId);
      IdVector idv = CatalogDataAccess.selectIdOnly(con,
        CatalogDataAccess.CATALOG_ID, dbc);
      if (idv.size() > 0) {
        existFlag = true;
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.doesCatalogExist() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.doesCatalogExist() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return existFlag;
  }


  /**
   *@exception  CreateException  Description of Exception
   *@exception  RemoteException  Description of Exception
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   *  Description of the Method
   *
   *@param  pCriteria            Description of Parameter
   *@return                      Description of the Returned Value
   *@exception  java.rmi.RemoteException  Description of Exception
   */
  public IdVector searchProducts(Collection pCriteria, int pStoreId, boolean catalogReqFl) throws RemoteException {
    Connection con = null;
    IdVector productIdV = new IdVector();

    DBCriteria dbcMain;
    try {
      con = getConnection();
      dbcMain = new DBCriteria();
      dbcMain.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
      String catalogReq = null;
      //Determine catalog Id
      if (catalogReqFl&&pStoreId > 0) {
        int catalogId = getCatalogId(con, pStoreId);
        if (catalogId > 0) {
          catalogReq = "" + catalogId;
        }
      }
      dbcMain = analyseSearchCriteria(dbcMain, pCriteria, catalogReq, pStoreId);
      productIdV = ItemDataAccess.selectIdOnly(con, dbcMain);
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("searchProducts, Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("searchProducts, SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return productIdV;
  }

  
  public IdVector searchCatalogProducts(Collection pCriteria, int storeId, int catalogId) throws RemoteException {
	  Connection con = null;
	  IdVector productIdV = new IdVector();
	  DBCriteria dbcMain;
	    try {
	      con = getConnection();
	      dbcMain = new DBCriteria();
	      dbcMain.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
	      String catalogReq = "" + getCatalogId(con, storeId);	      
	      
	      dbcMain = analyseSearchCriteria(dbcMain, pCriteria, catalogReq, storeId);
	      dbcMain.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
	      
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);	      
	      
	      dbcMain.addOneOf(ItemDataAccess.ITEM_ID, CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc));
	      productIdV = ItemDataAccess.selectIdOnly(con, dbcMain);
	    } catch (NamingException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("searchCatalogProducts, Naming Exception happened");
	    } catch (SQLException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("searchCatalogProducts, SQL Exception happened");
	    } finally {
	      closeConnection(con);
	    }
	    return productIdV;
  }
  private int getCatalogId(Connection con, int pStoreId) throws SQLException
  {
    //Get catalog id
    int catalogId = 0;
    String sql = "select c.catalog_id " +
                   " from clw_catalog_assoc ca, clw_catalog c" +
                   " where c.catalog_type_cd = 'STORE'" +
                   " and c.catalog_status_cd in 'ACTIVE'" +
                   " and c.catalog_id = ca.catalog_id" +
                   " and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                   " and ca.bus_entity_id = " + pStoreId;

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      int count = 0;
      while (rs.next()) {
        count++;
        catalogId = rs.getInt(1);
      }
      rs.close();
      stmt.close();
      if (count == 0) {
        String errorMess = "No catalog for store. Store id: " + pStoreId;
        throw new SQLException(errorMess);
      }
      if (count > 1) {
        String errorMess = "Multiple active catalogs for store. Store id: " +  pStoreId;
        throw new SQLException(errorMess);
      }
      return catalogId;
  }


  /**
   *  Gets a collection of product ids from clw_item table, which match to request SearcCriteria
   *  @param pCriteria  collection of SearchCriteria objects
   *  @return IdVector of ProductData ids
   *  @throws            RemoteException Required by EJB 1.0
   */
  public IdVector searchProducts(Collection pCriteria) throws RemoteException {
    return searchProducts(pCriteria, 0,true);
  }

   /**
   *  Gets a collection of product ids from clw_item table, which match to request SearcCriteria
   *  @param pCriteria  collection of SearchCriteria objects
   *  @return IdVector of ProductData ids
   *  @throws            RemoteException Required by EJB 1.0
   */
  public IdVector searchProducts(Collection pCriteria,int pStoreId) throws RemoteException {
    return searchProducts(pCriteria,pStoreId,true);
  }

  /**
   *  Gets all items of the catalog
   *
   *@param  pCatalogId           Catalog Identifier
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public IdVector searchCatalogProducts(int pCatalogId) throws RemoteException {
    return searchCatalogProducts(pCatalogId, null);
  }

  /**
   *  Gets items of the catalog
   *
   *@param  pCatalogId           Catalog Identifier
   *@param  pCriteria            Description of Parameter
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public IdVector searchCatalogProducts(int pCatalogId, Collection pCriteria) throws
    RemoteException {
    return searchCatalogProducts(pCatalogId, pCriteria, false);
  }

  /**
   *  Gets items of the catalog
   *
   *@param  pCatalogId           Catalog Identifier
   *@param  pCriteria            Description of Parameter
   *@param pNoSubCatalogsFlag    Indecatates to ignore subcatalogs (if catalog has account type);
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public IdVector searchCatalogProducts(int pCatalogId, Collection pCriteria,
                                        boolean pNoSubCatalogsFlag) throws
    RemoteException {
    Connection con = null;

    DBCriteria dbc;
    try {
      con = getConnection();
      //Determine catalog Id
      dbc = new DBCriteria();
      IdVector catalogReq = new IdVector();
      CatalogData cd = CatalogDataAccess.select(con, pCatalogId);
      if (!pNoSubCatalogsFlag &&
          cd.getCatalogTypeCd().equals
          (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT)) {
        // Search all the catalogs belonging to this account.
        String q = " select catalog_id " +
                   " from clw_catalog_assoc " +
                   " where bus_entity_id in ( " +
                   " select bus_entity1_id from clw_bus_entity_assoc " +
                   " where bus_entity2_id in ( " +
                   " select bus_entity_id from clw_catalog_assoc" +
                   " where catalog_id = " + pCatalogId +
                   " and catalog_assoc_cd = 'CATALOG_ACCOUNT')" +
                   " and bus_entity_assoc_cd = 'SITE OF ACCOUNT')" +
                   " and catalog_assoc_cd = 'CATALOG_SITE'" +
                   " union" +
                   " select " + pCatalogId + " from dual";
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, q);
      } else {
        dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, pCatalogId);
      }

      catalogReq = CatalogDataAccess.selectIdOnly(con, dbc);
      ProductDAO pdao = mCacheManager.getProductDAO();
      return pdao.allCatalogItemIds(con, pCatalogId);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogClwProduct() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getCatalogClwProduct() SQL Exception happened");
    } catch (DataNotFoundException exc) {
      exc.printStackTrace();
      throw new RemoteException("Catalog information missing.");
    } catch (Exception exc) {
      exc.printStackTrace();
      throw new RemoteException("Catalog information exception 1.");
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets the BusEntityCollection attribute of the CatalogInformationBean
   *  object
   *
   *@param  pCon                 Description of Parameter
   *@param  pCatalogId           Description of Parameter
   *@param  pBusEntityTypeCd     Description of Parameter
   *@param  pNameFilter          Description of Parameter
   *@param  pMatch               Description of Parameter
   *@param  pOrder               Description of Parameter
   *@return                      The BusEntityCollection value
   *@exception  NamingException  Description of Exception
   *@exception  SQLException     Description of Exception
   */
  private BusEntityDataVector getBusEntityCollection(Connection pCon,
    int pCatalogId,
    String pBusEntityTypeCd,
    String pNameFilter,
    int pMatch,
    int pOrder) throws NamingException, SQLException {
    BusEntityDataVector busEntityDV =
      getBusEntityCollection(pCon, pCatalogId, pBusEntityTypeCd,
                             pNameFilter, true, pMatch, pOrder);
    return busEntityDV;
  }

  /**
   *  Gets the BusEntityCollection attribute of the CatalogInformationBean
   *  object
   *
   *@param  pCon                 Description of Parameter
   *@param  pCatalogId           Description of Parameter
   *@param  pBusEntityTypeCd     Description of Parameter
   *@param  pNameFilter          Description of Parameter
   *@param  pMatch               Description of Parameter
   *@param  pOrder               Description of Parameter
   *@return                      The BusEntityCollection value
   *@exception  NamingException  Description of Exception
   *@exception  SQLException     Description of Exception
   */
  private BusEntityDataVector getBusEntityCollection(Connection pCon,
    int pCatalogId,
    String pBusEntityTypeCd,
    String pNameFilter,
    boolean pShowInactiveFl,
    int pMatch,
    int pOrder) throws NamingException, SQLException {
    BusEntityDataVector busEntityDV = new BusEntityDataVector();
    String busEntityReq =
      getBusEntityCollectionRequest(pCatalogId, pBusEntityTypeCd, pNameFilter,
                                    pShowInactiveFl, pMatch);

    DBCriteria dbc = new DBCriteria();
    dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, busEntityReq);
    switch (pOrder) {
    case SearchCriteria.ORDER_BY_ID:
      dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
      break;
    case SearchCriteria.ORDER_BY_SHORT_DESC:
      dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
      break;
    }

    // Set an absolute limit.
    int maxRows = 1000;
    busEntityDV = BusEntityDataAccess.select(pCon, dbc, maxRows);
    return busEntityDV;
  }

  /**
   *  Gets the list of bus entity id that associated with 
   *  catalog pCatalogId and match bus entity type of pBusEntityTypeCd
   *
   *@param  pCatalogId        Description of Parameter
   *@param  pBusEntityTypeCd  Description of Parameter
   *@return                   The IdVector value
   */
  public IdVector getBusEntityIdCollection(int pCatalogId, String pBusEntityTypeCd)
  throws RemoteException {
	  IdVector busEntityIds = new IdVector();
	  String busEntityReq =  getBusEntityCollectionRequest(pCatalogId, 
			  pBusEntityTypeCd, null, true, SearchCriteria.EXACT_MATCH);
	  DBCriteria dbc = new DBCriteria();
	  dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, busEntityReq);
	  dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
              RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
	  Connection conn = null;
	  try {
    	conn = getConnection();
    	busEntityIds = BusEntityDataAccess.selectIdOnly(conn, dbc);
	  } catch (Exception e) {
        String msg =
          "Error. CatalogInformation.getAccountCatalogsByStoreId() :"
          + " : " + e.getMessage();
        logError(msg);
        throw new RemoteException(msg);
      } finally {
        try {
          if (conn != null) conn.close();
        } catch (Exception ex) {}
      }
    
    return busEntityIds;
  }

  /**
   *  Gets the BusEntityCollectionRequest attribute of the
   *  CatalogInformationBean object
   *
   *@param  pCatalogId
   *@param  pBusEntityTypeCd
   *@param  pNameFilter
   *@param  pShowInactiveFl
   *@param  pMatch
   *@return
   */
  private String getBusEntityCollectionRequest(int pCatalogId,
                                               String pBusEntityTypeCd,
                                               String pNameFilter,
                                               boolean pShowInactiveFl,
                                               int pMatch) {
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
    if (RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(pBusEntityTypeCd)) {
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
    } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(pBusEntityTypeCd)) {
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
    } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(pBusEntityTypeCd)) {
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
    } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(
      pBusEntityTypeCd)) {
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
    }
    String catalogAssocReq = CatalogAssocDataAccess.getSqlSelectIdOnly(
      CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

    dbc = new DBCriteria();
    dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, catalogAssocReq);
    if (pNameFilter != null && pNameFilter.trim().length() > 0) {
      String filter = null;
      switch (pMatch) {
      case SearchCriteria.EXACT_MATCH:
        filter = pNameFilter.trim();
        dbc.addEqualTo(BusEntityDataAccess.SHORT_DESC, filter);
        break;
      case SearchCriteria.BEGINS_WITH:
        filter = pNameFilter.trim() + "%";
        dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, filter);
        break;
      case SearchCriteria.CONTAINS:
        filter = "%" + pNameFilter.trim() + "%";
        dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, filter);
        break;
      case SearchCriteria.EXACT_MATCH_IGNORE_CASE:
        filter = pNameFilter.trim().toUpperCase();
        dbc.addEqualTo(BusEntityDataAccess.SHORT_DESC, filter);
        break;
      case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
        filter = pNameFilter.trim().toUpperCase() + "%";
        dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, filter);
        break;
      case SearchCriteria.CONTAINS_IGNORE_CASE:
        filter = "%" + pNameFilter.trim().toUpperCase() + "%";
        dbc.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, filter);
        break;
      }
    }
    dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, pBusEntityTypeCd);
    if (!pShowInactiveFl) {
      dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                        RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
    }
    String busEntityReq =
      BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID,
                                             dbc);
    return busEntityReq;
  }

  /*
   *
   */
  public boolean isInt(String pInt) {
    try {
      if (pInt != null) pInt = pInt.trim();
      Integer.parseInt(pInt);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   *  Description of the Method
   *
   *@param  pDbCriteria  Description of Parameter
   *@param  pCriteria    Description of Parameter
   *@param  pCatalogReq  Description of Parameter
   *@return              Description of the Returned Value
   */
  private DBCriteria analyseSearchCriteria(DBCriteria pDbCriteria,
                                           Collection pCriteria,
                                           String pCatalogReq, int storeId) {
	    //Analise search critera
	    DBCriteria dbc = null;
	    DBCriteria dbcManuMapping = null,
	                                dbcDistrMapping = null,
	                                                  dbcDistrToCatItem = null
	      ;
	    String noDistrReq = null;

	    String catalogReq = pCatalogReq;
	    Iterator iter = pCriteria.iterator();
	    while (iter.hasNext()) {
	      SearchCriteria sc = (SearchCriteria) iter.next();
	      String value = (sc.value == null) ? null : ("" + sc.value).trim();
	      if (sc.name.equals(SearchCriteria.CLW_SKU_NUMBER)) {
	        if (isInt(value)) {
	          makeCondition(pDbCriteria, ItemDataAccess.SKU_NUM, sc.operator, value);
	        } else {
	          pDbCriteria.addCondition("1=2");
	        }

	      } else if (sc.name.equals(SearchCriteria.ITEM_ID)) {
	        if (isInt(value)) {
	          makeCondition(pDbCriteria, ItemDataAccess.ITEM_ID, sc.operator, value);
	        } else {
	          pDbCriteria.addCondition("1=2");
	        }
	      } else if (sc.name.equals(SearchCriteria.CLW_CUST_SKU_NUMBER)) {
	        String crtStr = (isInt("" + value)) ?
	                        "(SELECT item_id from clw_item where sku_num =" + value +
	                        " union " +
	                        " SELECT distinct item_id from clw_catalog_structure where customer_sku_num = '" +
	                        value + "' " +
	                        " and catalog_id in (" + pCatalogReq + "))"
	                        :
	                        "(SELECT item_id from clw_catalog_structure where customer_sku_num = '" +
	                        value + "' " +
	                        " and catalog_id in (" + pCatalogReq + "))";
	        pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, crtStr);
	      } else if (sc.name.equals(SearchCriteria.CUST_SKU_NUMBER) || sc.name.equals(SearchCriteria.STORE_SKU_NUMBER) ) {
	    	  String selCatalogIds = pCatalogReq;
	    	  if (storeId > 0) // search cust sku with in all catalogs that related to store
	    		  selCatalogIds = getCatalogIdsByStore(storeId);
	        String crtStr =
	          "(SELECT item_id from clw_catalog_structure where customer_sku_num = '" +
	          value + "' " +
	          " and catalog_id in (" + selCatalogIds + "))";
	        pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, crtStr);
	      }

	      else if (sc.name.equals(SearchCriteria.MANUFACTURER_SKU_NUMBER)) {
	        if (dbcManuMapping == null) {
	          dbcManuMapping = new DBCriteria();
	          dbcManuMapping.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	                                    RefCodeNames.ITEM_MAPPING_CD.
	                                    ITEM_MANUFACTURER);
	        }
	        makeCondition(dbcManuMapping, ItemMappingDataAccess.ITEM_NUM,
	                      sc.operator, value);

	      } else if (sc.name.equals(SearchCriteria.DISTRIBUTOR_SKU_NUMBER)) {
	        if (dbcDistrMapping == null) {
	          dbcDistrMapping = new DBCriteria();
	          dbcDistrMapping.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	                                     RefCodeNames.ITEM_MAPPING_CD.
	                                     ITEM_DISTRIBUTOR);
	        }
	        if (SearchCriteria.EXACT_MATCH == sc.operator) {
	          String cond = "trim(" + ItemMappingDataAccess.ITEM_NUM + ") = '" +
	                        value.trim() + "'";
	          dbcDistrMapping.addCondition(cond);
	        } else {
	          makeCondition(dbcDistrMapping, ItemMappingDataAccess.ITEM_NUM,
	                        sc.operator, value);
	        }

	      } else if (sc.name.equals(SearchCriteria.PRODUCT_SHORT_DESC)) {
	        makeCondition(pDbCriteria, ItemDataAccess.SHORT_DESC, sc.operator,
	                      value);

	      } else if (sc.name.equals(SearchCriteria.PRODUCT_LONG_DESC)) {
	        makeCondition(pDbCriteria, ItemDataAccess.LONG_DESC, sc.operator, value);

	      } else if (sc.name.equals(SearchCriteria.MANUFACTURER_SHORT_DESC)) {
	        dbc = new DBCriteria();
	        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
	                       RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
	        makeCondition(dbc, BusEntityDataAccess.SHORT_DESC, sc.operator, value);
	        String manuReq =
	          BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.
	                                                 BUS_ENTITY_ID, dbc);
	        if (dbcManuMapping == null) {
	          dbcManuMapping = new DBCriteria();
	          dbcManuMapping.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	                                    RefCodeNames.ITEM_MAPPING_CD.
	                                    ITEM_MANUFACTURER);
	        }
	        dbcManuMapping.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, manuReq);

	      } else if (sc.name.equals(SearchCriteria.MANUFACTURER_ID)) {
	        dbc = new DBCriteria();
	        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
	                       RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
	        makeCondition(dbc, BusEntityDataAccess.BUS_ENTITY_ID, sc.operator,
	                      value);
	        String manuReq =
	          BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.
	                                                 BUS_ENTITY_ID, dbc);
	        if (dbcManuMapping == null) {
	          dbcManuMapping = new DBCriteria();
	          dbcManuMapping.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	                                    RefCodeNames.ITEM_MAPPING_CD.
	                                    ITEM_MANUFACTURER);
	        }
	        dbcManuMapping.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, manuReq);

	      } else if (sc.name.equals(SearchCriteria.DISTRIBUTOR_ID)) {
	        int distId = -1;
	        try {
	          distId = Integer.parseInt(value);
	        } catch (Exception e) {}
	        if (catalogReq != null && catalogReq.trim().length() > 0) {
	          if (distId != 0) {
	            dbcDistrToCatItem = new DBCriteria();
	            dbcDistrToCatItem.addEqualTo(CatalogStructureDataAccess.BUS_ENTITY_ID, value);
	            dbcDistrToCatItem.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogReq);
	          } else {
	            noDistrReq =
	              "select item_id from clw_catalog_structure " +
	              " where  catalog_id in (" + catalogReq + ")" +
	              " group by item_id	having count(bus_entity_id) = 0";

	          }
	        } else {
	          if (distId != 0) {
	            if (dbcDistrMapping == null) {
	              dbcDistrMapping = new DBCriteria();
	              dbcDistrMapping.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
	                                         RefCodeNames.ITEM_MAPPING_CD.
	                                         ITEM_DISTRIBUTOR);
	            }
	            dbcDistrMapping.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,
	                                       distId);
	          } else {
	            noDistrReq =
	              " select item_id from ( " +
	              " select id.item_id, imd.item_mapping_id " +
	              " from clw_item id, clw_item_mapping imd " +
	              " where imd.item_mapping_cd(+) = 'ITEM_DISTRIBUTOR' " +
	              " and imd.item_id(+) = id.item_id " +
	              ") where item_mapping_id is null ";
	          }
	        }
	      } else if (sc.name.equals(SearchCriteria.CATALOG_CATEGORY)) {
	        dbc = new DBCriteria();
	        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
	                       RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
	        if (catalogReq != null && catalogReq.trim().length() > 0) {
	          dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogReq);
	        }
	        String catalogCategoryReq =
	          CatalogStructureDataAccess.getSqlSelectIdOnly(
	            CatalogStructureDataAccess.ITEM_ID, dbc);

	        dbc = new DBCriteria();
	        dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
	                       RefCodeNames.ITEM_TYPE_CD.CATEGORY);
	        makeCondition(dbc, ItemDataAccess.SHORT_DESC, sc.operator, value);
	        dbc.addOneOf(ItemDataAccess.ITEM_ID, catalogCategoryReq);
	        String categoryReq = ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.
	          ITEM_ID, dbc);

	        dbc = new DBCriteria();
	        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
	                       RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
	        if (catalogReq != null && catalogReq.trim().length() > 0) {
	          dbc.addOneOf(ItemAssocDataAccess.CATALOG_ID, catalogReq);
	        }
	        dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID, categoryReq);
	        String categoryAssocReq = ItemAssocDataAccess.getSqlSelectIdOnly(
	          ItemAssocDataAccess.ITEM1_ID, dbc);

	        pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, categoryAssocReq);

	      } else if (sc.name.startsWith(SearchCriteria.ITEM_META)) {
	        String itemPropName = sc.name.substring(SearchCriteria.ITEM_META.length());
	        dbc = new DBCriteria();
	        dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, itemPropName);
	        makeCondition(dbc, ItemMetaDataAccess.CLW_VALUE, sc.operator, value);
	        String itemPropReq = ItemMetaDataAccess.getSqlSelectIdOnly(
	          ItemMetaDataAccess.ITEM_ID, dbc);
	        pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, itemPropReq);
	      } else if (sc.name.equals(SearchCriteria.CONTRACT_ID)) {
	        dbc = new DBCriteria();
	        makeCondition(dbc, ContractItemDataAccess.CONTRACT_ID, sc.operator,
	                      value);
	        String itemContractReq = ContractItemDataAccess.getSqlSelectIdOnly(
	          ContractItemDataAccess.ITEM_ID, dbc);
	        pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, itemContractReq);
	      } else if (sc.name.equals(SearchCriteria.ITEM_STATUS_CD)) {
	          String itemStatusCdCond = "UPPER(i.item_status_cd)= '" +
	          value.toUpperCase().replaceAll("'", "''") + "'";
	      }
	    }
	    if (dbcManuMapping != null) {
	      String manuMappingReq = ItemMappingDataAccess.getSqlSelectIdOnly(
	        ItemMappingDataAccess.ITEM_ID, dbcManuMapping);
	      pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, manuMappingReq);
	    }
	    if (dbcDistrMapping != null) {
	      String distrMappingReq = ItemMappingDataAccess.getSqlSelectIdOnly(
	        ItemMappingDataAccess.ITEM_ID, dbcDistrMapping);
	      pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, distrMappingReq);
	    }
	    if (noDistrReq != null) {
	      pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, noDistrReq);
	    }
	    if (dbcDistrToCatItem != null) {
	      String distrCatReq = CatalogStructureDataAccess.
	                           getSqlSelectIdOnly(CatalogStructureDataAccess.
	                                              ITEM_ID,
	                                              dbcDistrToCatItem);
	      pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, distrCatReq);
	    }

	    if (catalogReq != null && catalogReq.trim().length() > 0) {
	      String catReq = "select distinct item_id from clw_catalog_structure " +
	                      "where catalog_id in (" + catalogReq + ")";
	      pDbCriteria.addOneOf(ItemDataAccess.ITEM_ID, catReq);
	    }

	    return pDbCriteria;
	  }


  /*
   *
   */
  /**
   *  Description of the Method
   *
   *@param  pDbc       Description of Parameter
   *@param  pName      Description of Parameter
   *@param  pOperator  Description of Parameter
   *@param  pValue     Description of Parameter
   */
  private void makeCondition(DBCriteria pDbc, String pName, int pOperator,
                             Object pValue) {
    String value = pValue.toString();
    switch (pOperator) {
    case SearchCriteria.EXACT_MATCH:
      pDbc.addEqualTo(pName, value);
      break;
    case SearchCriteria.BEGINS_WITH:
      pDbc.addLike(pName, value + "%");
      break;
    case SearchCriteria.CONTAINS:
      pDbc.addLike(pName, "%" + value + "%");
      break;
    case SearchCriteria.EXACT_MATCH_IGNORE_CASE:
      pDbc.addEqualToIgnoreCase(pName, value.toUpperCase());
      break;
    case SearchCriteria.BEGINS_WITH_IGNORE_CASE:
      pDbc.addLikeIgnoreCase(pName, value + "%");
      break;
    case SearchCriteria.CONTAINS_IGNORE_CASE:
      pDbc.addLikeIgnoreCase(pName, "%" + value + "%");
      break;
    }
  }


  /*
   *
   */
  /**
   *  Description of the Method
   *
   *@param  con                        Description of Parameter
   *@param  pCatalogId                 Description of Parameter
   *@param  pProductId                 Description of Parameter
   *@return                            Description of the Returned Value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   *@exception  NamingException        Description of Exception
   *@exception  SQLException           Description of Exception
   */

  private ProductData readDbProductData
    (Connection con, int pCatalogId, int pProductId) throws RemoteException,
    DataNotFoundException,
    NamingException, SQLException {
    return readDbProductData(con, pCatalogId, pProductId, 0);
  }

  private CacheManager mCacheManager = new CacheManager(this.getClass().getName());

  private ProductData readDbProductData
  (Connection con, int pCatalogId, int pProductId, int pDistId) throws
  RemoteException, DataNotFoundException,
  NamingException, SQLException {
	  return readDbProductData(con,pCatalogId, pProductId, pDistId,0);
  }

  private ProductData readDbProductData
    (Connection con, int pCatalogId, int pProductId, int pDistId, int siteId) throws
    RemoteException, DataNotFoundException,
    NamingException, SQLException {
         return readDbProductData(con,pCatalogId, pProductId, pDistId,0, null);
  }
  private ProductData readDbProductData
    (Connection con, int pCatalogId, int pProductId, int pDistId, int siteId, AccCategoryToCostCenterView pCategToCostCenterView) throws
    RemoteException, DataNotFoundException,
    NamingException, SQLException {

    logDebug("pCatalogId=" + pCatalogId +
             " pProductId=" + pProductId +
             " pDistId=" + pDistId
      );
    ProductData productD = new ProductData();

    ProductDAO pdao = mCacheManager.getProductDAO();
    pdao.lookupItem(con, pProductId);
    pdao.updateCatalogInfo(con, pCatalogId,siteId, pCategToCostCenterView);
    // get all the distributors for this item.
    pdao.updateProductDistributors(con, pProductId, pDistId);
    pdao.updateCertifiedCompanies(con, pProductId,0);
    ProductDataVector pDV = pdao.getResultVector();
    if (pDV.size() <= 0) {
      throw new DataNotFoundException("pCatalogId=" + pCatalogId +
                                      ", pProductId=" + pProductId +
                                      ", pDistId=" + pDistId);
    }

    productD = (ProductData) pDV.get(0);
    logInfo("productD????? "+productD.getCostCenterId());
    return productD;
  }

  /**
   *  Gets a collection of catalogs that are of type ACCOUNT and
   *  are associated with the given store.
   *
   *@param  pStoreId           store id
   *@return                    collection of account catalogs
   *@exception  RemoteException
   */
  public CatalogDataVector getAccountCatalogsByStoreId(int pStoreId) throws
    RemoteException {

    CatalogDataVector catv = new CatalogDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      StringBuffer buf = new StringBuffer();
      buf.append(CatalogDataAccess.CATALOG_ID);
      buf.append(" IN (SELECT ");
      buf.append(CatalogAssocDataAccess.CATALOG_ID);
      buf.append(" FROM ");
      buf.append(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
      buf.append(" WHERE ");
      buf.append(CatalogAssocDataAccess.BUS_ENTITY_ID);
      buf.append(" = ");
      buf.append(pStoreId);
      buf.append(" AND ");
      buf.append(CatalogAssocDataAccess.CATALOG_ASSOC_CD);
      buf.append(" = '");
      buf.append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
      buf.append("')");
      dbc.addCondition(buf.toString());
      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                     RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      catv = CatalogDataAccess.select(conn, dbc);
    } catch (Exception e) {
      String msg =
        "Error. CatalogInformation.getAccountCatalogsByStoreId() :"
        + " : " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return catv;
  }

  /**
   *  Gets a collection of catalogs that are of type ACCOUNT or SHOPPING and
   *  are associated with the given store.
   *
   *@param  pStoreId           store id
   *@return                    collection of account catalogs
   *@exception  RemoteException
   */
  public CatalogDataVector getAccountAndShoppingCatalogsByStoreId(int pStoreId) throws
    RemoteException {

    CatalogDataVector catv = new CatalogDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      StringBuffer buf = new StringBuffer();
      buf.append(CatalogDataAccess.CATALOG_ID);
      buf.append(" IN (SELECT ");
      buf.append(CatalogAssocDataAccess.CATALOG_ID);
      buf.append(" FROM ");
      buf.append(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
      buf.append(" WHERE ");
      buf.append(CatalogAssocDataAccess.BUS_ENTITY_ID);
      buf.append(" = ");
      buf.append(pStoreId);
      buf.append(" AND ");
      buf.append(CatalogAssocDataAccess.CATALOG_ASSOC_CD);
      buf.append(" = '");
      buf.append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
      buf.append("')");
      dbc.addCondition(buf.toString());
      ArrayList l = new ArrayList();
      l.add(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
      l.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
      dbc.addOneOf(CatalogDataAccess.CATALOG_TYPE_CD, l);

      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      catv = CatalogDataAccess.select(conn, dbc);
    } catch (Exception e) {
      String msg =
        "Error. CatalogInformation.getAccountAndShoppingCatalogsByStoreId() :"
        + " : " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return catv;
  }

  /**
   *  Gets a collection of catalogs that are of type STORE and
   *  are associated with the given store.
   *
   *@param  pStoreId           store id
   *@return                    collection of account catalogs
   *@exception  RemoteException
   */
  public CatalogDataVector getSTORETypeCatalogsByStoreId(int pStoreId) throws
    RemoteException {

    CatalogDataVector catv = new CatalogDataVector();
    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria dbc = new DBCriteria();
      StringBuffer buf = new StringBuffer();
      buf.append(CatalogDataAccess.CATALOG_ID);
      buf.append(" IN (SELECT ");
      buf.append(CatalogAssocDataAccess.CATALOG_ID);
      buf.append(" FROM ");
      buf.append(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
      buf.append(" WHERE ");
      buf.append(CatalogAssocDataAccess.BUS_ENTITY_ID);
      buf.append(" = ");
      buf.append(pStoreId);
      buf.append(" AND ");
      buf.append(CatalogAssocDataAccess.CATALOG_ASSOC_CD);
      buf.append(" = '");
      buf.append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
      buf.append("')");
      dbc.addCondition(buf.toString());
      ArrayList l = new ArrayList();
      l.add(RefCodeNames.CATALOG_TYPE_CD.STORE);
      dbc.addOneOf(CatalogDataAccess.CATALOG_TYPE_CD, l);

      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      catv = CatalogDataAccess.select(conn, dbc);
    } catch (Exception e) {
      String msg =
        "Error. CatalogInformation.getSTORETypeCatalogsByStoreId() :"
        + " : " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }
    return catv;
  }

  /**
   *  Gets catalog parent category for category or product
   *
   *@param  pCatalogId        the catalog identifier
   *@param  pChildId          child category or product id
   *@return                   CatalogCategoryDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */
  public CatalogCategoryDataVector getCatalogParentCategory(int pCatalogId,
    int pChildId) throws RemoteException {
    Connection con = null;
    CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pChildId);
      Vector relTypeV = new Vector();
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
      relTypeV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT);
      dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, relTypeV);
      String subCategoryReq = ItemAssocDataAccess.getSqlSelectIdOnly(
        ItemAssocDataAccess.ITEM2_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, subCategoryReq);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      for (int ii = 0; ii < itemDV.size(); ii++) {
        ItemData itemD = (ItemData) itemDV.get(ii);
        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {
          throw new RemoteException(
            "Error. CatalogInformationBean.getCatalogParentCategories() No item_assoc - item consitency in the Catalog. Catalog id: " +
            pCatalogId + " Item id: " + itemD.getItemId());
        }
        CatalogCategoryData ccD = new CatalogCategoryData();
        ccD.setItemData(itemD);
        catalogCategoryDV.add(ccD);
      }
      assignMajorCategory(con, catalogCategoryDV);

    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogParentCategories()() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getCatalogparentCategories()() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogCategoryDV;
  }

  /**
   *Adds the specified item to the specified category name in the the specified catalogs.  Optionally
   *it will add the category if it does not allready exist.
   *@param pItemId the item to add to the category
   *@param pCatalogIds the catalogs we want to add this item in to.
   *@param pCategory the category name we are adding the items to.
   *@param pCategoryCostCenter the cost center id to use if we need to add this category.  Only used
   *  when the category does not exist and the addCategoryToCatalog flag is true.
   *@param addCategoryToCatalog determines wheather the category is added if it does not exist in the catalog.
   *@param the username of the user making the change for auditing
   *@throws RemoteException if there is an error
   */
  public void addItemToCategories(
    int pItemId, IdVector pCatalogIds, String pCategory,
    int pCategoryCostCenter, boolean addCategoryToCatalog,
    String pUserDoingMod) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      String sql =
        "SELECT i.item_id, cs.catalog_id FROM clw_catalog_structure cs, clw_item i " +
        "WHERE cs.catalog_id IN (" + IdVector.toCommaString(pCatalogIds) +
        ") AND cs.catalog_structure_cd = 'CATALOG_CATEGORY' " +
        "AND i.item_id = cs.item_id AND i.short_desc = ?";

      logDebug("addItemToCategories " + sql);
      //crate the mapping between category ids and catalogs
      HashMap catCategoryatMap = new HashMap();
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, pCategory);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        int categoryId = rs.getInt(1);
        int catalogId = rs.getInt(2);
        catCategoryatMap.put(new Integer(catalogId), new Integer(categoryId));
      }
      rs.close();
      stmt.close();

      //loop through the mapping and find out what catalogs we don't have categories in and add them
      //if the flag is set
      if (addCategoryToCatalog) {
        Set hasCategoryCatTmp = catCategoryatMap.keySet();
        //we will be making changes to the underlying map, so get out of the map backed iterator
        ArrayList hasCategoryCat = new ArrayList();
        hasCategoryCat.addAll(hasCategoryCatTmp);
        Iterator it = pCatalogIds.iterator();
        //IdVector toAddCats = new IdVector();
        while (it.hasNext()) {
          Integer allCatId = (Integer) it.next();
          if (!hasCategoryCat.contains(allCatId)) {
            //add the category
            //by convention category records do not cross catalogs
            ItemData cat = ItemData.createValue();
            cat.setAddBy(pUserDoingMod);
            cat.setModBy(pUserDoingMod);
            cat.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.CATEGORY);
            cat.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
            cat.setShortDesc(pCategory);
            cat = ItemDataAccess.insert(con, cat);
            logDebug("Adding category: " + cat);
            //add the association
            CatalogStructureData csd = CatalogStructureData.createValue();
            csd.setAddBy(pUserDoingMod);
            csd.setModBy(pUserDoingMod);
            csd.setCatalogId(allCatId.intValue());
            csd.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.
                                      CATALOG_CATEGORY);
            csd.setCostCenterId(pCategoryCostCenter);
            csd.setItemId(cat.getItemId());
            csd.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
            csd = CatalogStructureDataAccess.insert(con, csd);
            catCategoryatMap.put(allCatId, new Integer(cat.getItemId()));
            logDebug("Adding category Mapping: " + csd);
          }
        }
      }

      //loop through the mapping and add the items to the categories if the mapping
      //does not already exist
      java.util.Collection catalogIds = catCategoryatMap.keySet();
      if (catalogIds == null || catalogIds.isEmpty()) {
        //there was nothing to do.  Most likly this means we were requested to
        //not add teh category to the catalog and for the items we are processing
        //none of them have the selected category in the catalog;
        return;
      }
      String catalogIdsStr = Utility.toCommaSting(catalogIds);
      DBCriteria inCrit = new DBCriteria();
      inCrit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                        RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      inCrit.addEqualTo(ItemDataAccess.SHORT_DESC, pCategory);
      String inSql = ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.ITEM_ID,
        inCrit);

      sql = "SELECT distinct catalog_id FROM clw_item_assoc " +
            "WHERE item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' " +
            "AND item1_id = " + pItemId + " AND item2_id IN (" + inSql +
            ") AND catalog_id IN (" + catalogIdsStr + ")";
      logDebug(sql);
      Statement stmt2 = con.createStatement();
      rs = stmt2.executeQuery(sql);
      ArrayList catsExist = new ArrayList();
      while (rs.next()) {
        int catalogId = rs.getInt(1);
        catsExist.add(new Integer(catalogId));
      }
      rs.close();
      stmt2.close();

      Iterator it = catalogIds.iterator();
      while (it.hasNext()) {
        Integer catalogId = (Integer) it.next();
        if (!catsExist.contains(catalogId)) {
          //add this item to the list
          ItemAssocData iad = ItemAssocData.createValue();
          iad.setAddBy(pUserDoingMod);
          iad.setModBy(pUserDoingMod);
          iad.setCatalogId(catalogId.intValue());
          iad.setItem1Id(pItemId);
          iad.setItem2Id(((Integer) catCategoryatMap.get(catalogId)).intValue());
          iad.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
          iad = ItemAssocDataAccess.insert(con, iad);
          logDebug("Adding item Mapping: " + iad);
        }
      }

    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }


  /**
   *Removes the specified item from the specified category name in the the specified catalogs.  Optionally
   *it will remove the item from all of the specified categories
   *@param pItemId the item.
   *@param pCatalogIds the catalogs.
   *@param pCategory the category name we are removing this item from.
   *@param removeFromAllCategories determines wheather we are removing the item from all categories.
   *@throws RemoteException if there is an error
   */
  public void removeItemFromCategories(
    int pItemId, IdVector pCatalogIds, String pCategory,
    boolean removeFromAllCategories) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItemId);
      crit.addOneOf(ItemAssocDataAccess.CATALOG_ID, pCatalogIds);
      crit.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                      RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      if (!removeFromAllCategories) {
        DBCriteria inCrit = new DBCriteria();
        inCrit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                          RefCodeNames.ITEM_TYPE_CD.CATEGORY);
        inCrit.addEqualTo(ItemDataAccess.SHORT_DESC, pCategory);
        String sql = ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.ITEM_ID,
          inCrit);
        crit.addOneOf(ItemAssocDataAccess.ITEM2_ID, sql);
      }
      ItemAssocDataAccess.remove(con, crit);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

  /**
   *Gets all major categories
   *@throws RemoteException
   */
  public ItemDataVector getMajorCategories() throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
      ItemDataVector majorCatDV = ItemDataAccess.select(con, dbc);
      return majorCatDV;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

  /**
   *Gets major category mismatches
   * @param pCategory category to check
   * @param pMajorCategoryId id of major category assigned
   * @return set of CatalogCategoryView objects
   *@throws RemoteException
   */
  public CatalogCategoryViewVector
    getMismatchMajorCategories(String pCategory, int pMajorCategoryId) throws
    RemoteException {
    Connection con = null;
    CatalogCategoryViewVector catalogCategoryVwV = new
      CatalogCategoryViewVector();
    try {
      con = getConnection();
      String sql =
        "select " +
        "  cs.catalog_id, " +
        "  cat.short_desc catalog_name, " +
        "  c.item_id category_id, " +
        "  c.short_desc category_name," +
        "  mc.item_id major_category_id," +
        "  mc.short_desc major_category_name" +
        "  from clw_item mc, clw_item c, clw_item_assoc ia," +
        "       clw_catalog_structure cs, clw_catalog cat" +
        "  where mc.item_id != ? " +
        "    and mc.item_type_cd = ? " +
        "    and c.short_desc = ? "+
        "    and c.item_type_cd = ? " +
        "    and ia.item1_id = c.item_id" +
        "    and ia.item2_id = mc.item_id" +
        "    and ia.item_assoc_cd = ? " +
        "    and cs.item_id = c.item_id" +
        "    and cat.catalog_id = cs.catalog_id" +
        "  order by major_category_name";

      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setInt(1, pMajorCategoryId);
      stmt.setString(2, RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
      stmt.setString(3, pCategory);
      stmt.setString(4, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      stmt.setString(5, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        CatalogCategoryView ccVw = CatalogCategoryView.createValue();
        int catalogId = rs.getInt("catalog_id");
        String catalogName = rs.getString("catalog_name");
        int categoryId = rs.getInt("category_id");
        String categoryName = rs.getString("category_name");
        int majorCatId = rs.getInt("major_category_id");
        String majorCatName = rs.getString("major_category_name");
        ccVw.setCatalogId(catalogId);
        ccVw.setCatalogName(catalogName);
        ccVw.setCategoryId(categoryId);
        ccVw.setCategoryName(categoryName);
        ccVw.setMajorCategoryId(majorCatId);
        ccVw.setMajorCategoryName(majorCatName);
        catalogCategoryVwV.add(ccVw);
      }
      rs.close();
      stmt.close();
      return catalogCategoryVwV;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets store items
   *
   *@param  pCriteria            List of SearchCriteria objects
   *@return  a set of ItemView objects
   *@exception  RemoteException
   */
  public ItemViewVector searchStoreItems(List pCriteria, boolean pDistInfoFl) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();

      Integer storeIdI = new Integer(0);
      String categCond = null;
      String shortDesc = null;
      String longDesc = null;
      String metaCond = null;
      String manufCond = null;
      String distCond = null;
      String storeSkuCond = null;
      String distSkuCond = null;
      String manufSkuCond = null;
      String itemIdCond = null;
      String catalogIdCond = null;
      String catalogIdCond2 = null;
      String catalogFilterCond = null;
      String itemStatusCdCond = null;
      String certifiedSql=null;
      String certifiedCond=null;
      /////////////////////
      //Create a set of filters
      for (Iterator iter = pCriteria.iterator(); iter.hasNext(); ) {
        SearchCriteria sc = (SearchCriteria) iter.next();
        String name = sc.getName();
        Object objValue = sc.getObjectValue();
        String strValue =
              (objValue instanceof String)? ((String) objValue).trim():"";

        int oper = sc.getOperator();

        if (SearchCriteria.STORE_ID.equals(name)) {
          storeIdI = (Integer) objValue;
        } else if (SearchCriteria.CATALOG_CATEGORY.equals(name)) {
          if(oper==SearchCriteria.BEGINS_WITH_IGNORE_CASE){
              categCond = "UPPER(categ.short_desc) like '" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          } else if (oper==SearchCriteria.CONTAINS_IGNORE_CASE) {
              categCond = "UPPER(categ.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          } else if(oper==SearchCriteria.BEGINS_WITH){
              categCond = "categ.short_desc like '" +
                      strValue.replaceAll("'", "''") +
                      "%'";
          } else if (oper==SearchCriteria.CONTAINS) {
              categCond = "categ.short_desc like '%" +
                      strValue.replaceAll("'", "''") +
                      "%'";
          } else {
              categCond = "UPPER(categ.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          }
        } else if (SearchCriteria.CERTIFIED.equals(name)) {
         if(strValue.equals(String.valueOf(Boolean.TRUE)))
          {
              certifiedSql = "(Select item_id,count(item_id)" +
                  " as counts from clw_item_mapping  where item_mapping_cd='"+
                    RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY+"'"+
                  " group by item_id) certcomp";
              certifiedCond ="i.item_id = certcomp.item_id and certcomp.counts>0 ";
          }
        }else if (SearchCriteria.PRODUCT_SHORT_DESC.equals(name)) {
          if(oper==SearchCriteria.BEGINS_WITH_IGNORE_CASE){
              shortDesc = "UPPER(i.short_desc) like '" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          } else if (oper==SearchCriteria.CONTAINS_IGNORE_CASE) {
              shortDesc = "UPPER(i.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          } else if(oper==SearchCriteria.BEGINS_WITH){
              shortDesc = "i.short_desc like '" +
                      strValue.replaceAll("'", "''") +
                      "%'";
          } else if (oper==SearchCriteria.CONTAINS) {
              shortDesc = "i.short_desc like '%" +
                      strValue.replaceAll("'", "''") +
                      "%'";
          } else {
              shortDesc = "UPPER(i.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          }
        } else if (SearchCriteria.PRODUCT_LONG_DESC.equals(name)) {
          longDesc = "UPPER(i.long_desc) like '%" +
                     strValue.toUpperCase().replaceAll("'", "''") +
                     "%'";
        } else if (name.startsWith(SearchCriteria.ITEM_META)) {
          metaCond = "UPPER(im.name_value)='" +
                     name.substring(SearchCriteria.ITEM_META.length()).
                     toUpperCase().replaceAll("'", "''")
                     + "' and " +
                     " UPPER(im.clw_value) like '%" +
                     strValue.toUpperCase().replaceAll("'", "''") +
                     "%'";
        } else if (SearchCriteria.MANUFACTURER_SHORT_DESC.equals(name)) {
          if(oper==SearchCriteria.BEGINS_WITH_IGNORE_CASE){
            manufCond = "UPPER(mf.short_desc) like '" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          } else if (oper==SearchCriteria.CONTAINS_IGNORE_CASE) {
            manufCond = "UPPER(mf.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          } else if(oper==SearchCriteria.BEGINS_WITH){
            manufCond = "mf.short_desc like '" +
                      strValue.replaceAll("'", "''") +
                      "%'";
          } else if (oper==SearchCriteria.CONTAINS) {
            manufCond = "mf.short_desc like '%" +
                      strValue.replaceAll("'", "''") +
                      "%'";
          } else {
            manufCond = "UPPER(mf.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
          }
        } else if (SearchCriteria.DISTRIBUTOR_SHORT_DESC.equals(name)) {
          distCond = "UPPER(dist.short_desc) like '%" +
                     strValue.toUpperCase().replaceAll("'", "''") +
                     "%'";
        } else if (SearchCriteria.STORE_SKU_NUMBER.equals(name)) {
        	storeSkuCond = "UPPER(cstr.customer_sku_num) = '" +
        	    strValue.toUpperCase().replaceAll("'", "''") +
        	    "'";
        } else if (SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(name)) {

        	if(oper==SearchCriteria.BEGINS_WITH){
        		distSkuCond = "UPPER(dim.item_num) LIKE '" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "%'";
        	} else if(oper==SearchCriteria.CONTAINS){
        		distSkuCond = "UPPER(dim.item_num) LIKE '%" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "%'";
            } else if(oper==SearchCriteria.EXACT_MATCH_IGNORE_CASE){
        		distSkuCond = "UPPER(dim.item_num) = '" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "'";
        	} else{
        		distSkuCond = "UPPER(dim.item_num) like '%" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "%'";
        	}
        } else if (SearchCriteria.MANUFACTURER_SKU_NUMBER.equals(name)) {

        	if(oper==SearchCriteria.BEGINS_WITH){
        		manufSkuCond = "UPPER(mfim.item_num) LIKE '" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "%'";
        	}else if(oper==SearchCriteria.CONTAINS){
        		manufSkuCond = "UPPER(mfim.item_num) LIKE '%" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "%'";
        	}else if(oper==SearchCriteria.EXACT_MATCH_IGNORE_CASE){
        		manufSkuCond = "UPPER(mfim.item_num) = '" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "'";
        	}else{
        		manufSkuCond = "UPPER(mfim.item_num) like '%" +
                    strValue.toUpperCase().replaceAll("'", "''") +
                    "%'";
        	}
        } else if (SearchCriteria.CUST_SKU_NUMBER.equals(name)) {
          String selCatalogIds = getCatalogIdsByStore(storeIdI.intValue());

          DBCriteria catStrDbc = new DBCriteria();

          if(oper==SearchCriteria.BEGINS_WITH){
        	  catStrDbc.addBeginsWithIgnoreCase(CatalogStructureDataAccess.
                      CUSTOMER_SKU_NUM,
                      strValue.toUpperCase());
          }else if(oper==SearchCriteria.CONTAINS){
        	  catStrDbc.addContainsIgnoreCase(CatalogStructureDataAccess.
                      CUSTOMER_SKU_NUM,
                      strValue.toUpperCase());
          }else if(oper==SearchCriteria.EXACT_MATCH_IGNORE_CASE){
        	  catStrDbc.addEqualToIgnoreCase(CatalogStructureDataAccess.
                      CUSTOMER_SKU_NUM,
                      strValue.toUpperCase());
          }else{
        	  catStrDbc.addContainsIgnoreCase(CatalogStructureDataAccess.
              CUSTOMER_SKU_NUM,
              strValue.toUpperCase());
          }
          catStrDbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, selCatalogIds);
          IdVector itemIdV = CatalogStructureDataAccess.selectIdOnly(con,
            CatalogStructureDataAccess.ITEM_ID, catStrDbc);
          if (itemIdV.size() > 1000) {
            String errorMess = "^clw^Sku criteria is to broad^clw^";
            throw new Exception(errorMess);
          }
          if (itemIdV.size() == 0) {
            itemIdCond = "1=2";
          } else {
            itemIdCond = "i.item_id in (" + IdVector.toCommaString(itemIdV) +
                         ")";
          }
        } else if (SearchCriteria.CATALOG_ID.equals(name)) {
          String catalogIdList = strValue;
          if (catalogIdList != null && catalogIdList.length() > 0) {
            catalogFilterCond =
             "i.item_id in (select cstr1.item_id from CLW_CATALOG_STRUCTURE cstr1" +
                    " where cstr1.catalog_id IN (" + catalogIdList + "))";
            //catalogFilterIdCond2 = "ia.catalog_id IN (" + catalogIdList + ")";
          }
        } else if (SearchCriteria.ITEM_STATUS_CD.equals(name)) {
          itemStatusCdCond = "UPPER(i.item_status_cd)= '" +
                             strValue.toUpperCase().replaceAll("'", "''") + "'";
        }

      }

      //Get catalog id
      int catalogId = 0;
      if (catalogIdCond == null || catalogIdCond.length() == 0) {
        String sql = "select c.catalog_id " +
                     " from clw_catalog_assoc ca, clw_catalog c" +
                     " where c.catalog_type_cd = 'STORE'" +
                     " and c.catalog_status_cd = 'ACTIVE'" +
                     " and c.catalog_id = ca.catalog_id" +
                     " and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                     " and ca.bus_entity_id = " + storeIdI;

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int count = 0;
        while (rs.next()) {
          count++;
          catalogId = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        if (count == 0) {
          String errorMess = "No catalog for store. Store id: " + storeIdI;
          throw new Exception(errorMess);
        }
        if (count > 1) {
          String errorMess = "Multiple active catalogs for store. Store id: " +
                             storeIdI;
          throw new Exception(errorMess);
        }
        catalogIdCond = "cstr.catalog_id =  " + catalogId;
        catalogIdCond2 = "ia.catalog_id(+) = " + catalogId;
      }

      //generate sql conditions
      String sql = "select distinct" +
                   " i.item_id,i.item_status_cd, " +
                   "   cstr.customer_sku_num as sku_num, " +
                   "   i.short_desc,  " +
                   "   uom.clw_value uom, " +
                   "   isize.clw_value isize, " +
                   "   pack.clw_value pack, " +
                   "   color.clw_value color, " +
                   "   categ.short_desc category, " +
                   "   categ.item_id category_id, " +
                   "   mfim.item_num manuf_sku, " +
                   "   mf.bus_entity_id manuf_id, " +
                   "   mf.short_desc manuf ";
      if (pDistInfoFl) {
        sql +=
          "   ,dim.item_num dist_sku " +
          "   ,dist.bus_entity_id dist_id " +
          "   ,dist.short_desc dist ";
      }
      sql +=
        " from " +
        "   clw_item i, " +
        "   clw_catalog_structure cstr, " +
        "   clw_item_meta uom, " +
        "   clw_item_meta color, " +
        "   clw_item_meta isize, " +
        "   clw_item_meta pack, " +
        "   clw_item_assoc ia, " +
        "   clw_item categ, " +
        "   clw_item_mapping mfim, " +
        "   clw_bus_entity mf "+
        (certifiedSql!=null?(","+certifiedSql):(""));


      if (metaCond != null) {
        sql +=
          "   ,clw_item_meta im ";
      }
      if (distSkuCond != null || distCond != null || pDistInfoFl) {
        sql +=
          "   ,clw_item_mapping dim ";
      }
      if (distCond != null || pDistInfoFl) {
        sql +=
          "   ,clw_bus_entity dist ";
      }
      sql +=
        " where i.item_id = cstr.item_id " +
        " and " + catalogIdCond +
        " and cstr.catalog_structure_cd = 'CATALOG_PRODUCT'" +
        " and i.item_id = uom.item_id(+) " +
        " and uom.name_value(+) = 'UOM'  " +
        " and i.item_id = isize.item_id(+) " +
        " and isize.name_value(+) = 'SIZE'  " +
        " and i.item_id = pack.item_id(+) " +
        " and pack.name_value(+) = 'PACK'  " +
        " and i.item_id = color.item_id(+) " +
        " and color.name_value(+) = 'COLOR' " +
        " and categ.item_id(+) = ia.item2_id " +
        " and ia.item1_id(+) = i.item_id " +
        " and ia.item_assoc_cd(+) = 'PRODUCT_PARENT_CATEGORY' " +
        " and " + catalogIdCond2 +
        " and mfim.item_id(+) = i.item_id " +
        " and mfim.item_mapping_cd(+) = 'ITEM_MANUFACTURER' " +
        " and mf.bus_entity_id(+) = mfim.bus_entity_id ";
      if (pDistInfoFl) {
        sql +=
          " and dim.item_mapping_cd(+) = 'ITEM_DISTRIBUTOR' " +
          " and dim.item_id(+) = i.item_id " +
          " and dist.bus_entity_id(+) = dim.bus_entity_id ";
      }
      if (storeSkuCond != null) {
        sql += " and " + storeSkuCond;
      }
      if (metaCond != null) {
        sql +=
          " and im.item_id = i.item_id " +
          " and " + metaCond;
      }
      if (manufSkuCond != null) {
        sql += " and " + manufSkuCond;
      }
      if (shortDesc != null) {
        sql += " and " + shortDesc;
      }
      if (longDesc != null) {
        sql += " and " + longDesc;
      }
      if (manufCond != null) {
        sql += " and " + manufCond;
      }
      if (categCond != null) {
        sql += " and " + categCond;
      }
      if (distSkuCond != null || distCond != null) {
        sql +=
          " and dim.item_mapping_cd = 'ITEM_DISTRIBUTOR' " +
          " and dim.item_id = i.item_id ";
      }
      if (distSkuCond != null) {
        sql += " and " + distSkuCond;
      }
      if (itemIdCond != null) {
        sql += " and " + itemIdCond;
      }
      if (distCond != null) {
        sql +=
          " and dist.bus_entity_id = dim.bus_entity_id " +
          " and " + distCond;
      }
      if (itemStatusCdCond != null) {
        sql += " and " + itemStatusCdCond;
      }
      if(catalogFilterCond != null) {
        sql += " and " +catalogFilterCond;
      }
      if(certifiedCond!=null)
      {
        sql+=" and "+ certifiedCond;
      }
      sql += " order by cstr.customer_sku_num ";

      logInfo("CatalogInfoBean CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC "+sql);
      logDebug(
        "CatalogInformationBena CCCCCCCCCCCCCCCCCCCCCCCCCCC sql: " + sql);

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      ItemViewVector itemVwV = new ItemViewVector();
      while (rs.next()) {
        ItemView itemVw = ItemView.createValue();
        itemVwV.add(itemVw);
        itemVw.setStoreId(storeIdI.intValue());
        itemVw.setCatalogId(catalogId);
        itemVw.setItemId(rs.getInt("item_id"));
        itemVw.setStatusCd(rs.getString("item_status_cd"));
        itemVw.setCategoryId(rs.getInt("category_id"));
        itemVw.setManufId(rs.getInt("manuf_id"));
        itemVw.setName(Utility.strNN(rs.getString("short_desc")));
        itemVw.setSku(Utility.strNN(rs.getString("sku_num")));
        itemVw.setUom(Utility.strNN(rs.getString("uom")));
        itemVw.setSize(Utility.strNN(rs.getString("isize")));
        itemVw.setPack(Utility.strNN(rs.getString("pack")));
        itemVw.setColor(Utility.strNN(rs.getString("color")));
        itemVw.setCategory(Utility.strNN(rs.getString("category")));
        itemVw.setManufName(Utility.strNN(rs.getString("manuf")));
        itemVw.setManufSku(Utility.strNN(rs.getString("manuf_sku")));
        if (pDistInfoFl) {
          itemVw.setDistId(rs.getInt("dist_id"));
          itemVw.setDistName(Utility.strNN(rs.getString("dist")));
          itemVw.setDistSku(Utility.strNN(rs.getString("dist_sku")));
        } else {
          itemVw.setDistId(0);
          itemVw.setDistName("");
          itemVw.setDistSku("");
        }
        itemVw.setSelected(false);
      }
      rs.close();
      stmt.close();
      return itemVwV;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets catalog items
   *
   *@param  pCriteria            List of SearchCriteria objects
   *@return  a set of ItemView objects
   *@exception  RemoteException
   */
  public CatalogItemDescViewVector searchCatalogItems(List pCriteria) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();

      Integer storeIdI = new Integer(0);
      Integer catalogIdI = new Integer(0);
      String categCond = null;
      String shortDesc = null;
      String longDesc = null;
      String metaCond = null;
      String manufCond = null;
      String distCond = null;
      String storeSkuCond = null;
      String distSkuCond = null;
      String manufSkuCond = null;
      String itemIdCond = null;

      /////////////////////
      //Create a set of filters
      for (Iterator iter = pCriteria.iterator(); iter.hasNext(); ) {
        SearchCriteria sc = (SearchCriteria) iter.next();
        String name = sc.getName();
        Object objValue = sc.getObjectValue();
        String strValue = "";
        if(objValue instanceof String) strValue = ((String) objValue).trim();
        if (SearchCriteria.STORE_ID.equals(name)) {
          storeIdI = (Integer) objValue;
        } else if (SearchCriteria.CATALOG_ID.equals(name)) {
          catalogIdI = (Integer) objValue;
        } else if (SearchCriteria.CATALOG_CATEGORY.equals(name)) {
          categCond = "UPPER(categ.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
        } else if (SearchCriteria.PRODUCT_SHORT_DESC.equals(name)) {
          shortDesc = "UPPER(i.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
        } else if (SearchCriteria.PRODUCT_LONG_DESC.equals(name)) {
          longDesc = "UPPER(i.long_desc) like '%" +
                     strValue.toUpperCase().replaceAll("'", "''") +
                     "%'";
        } else if (name.startsWith(SearchCriteria.ITEM_META)) {
          metaCond = "UPPER(im.name_value)='" +
                     name.substring(SearchCriteria.ITEM_META.length()).
                     toUpperCase().replaceAll("'", "''")
                     + "' and " +
                     " UPPER(im.clw_value) like '%" +
                     strValue.toUpperCase().replaceAll("'", "''") +
                     "%'";
        } else if (SearchCriteria.MANUFACTURER_SHORT_DESC.equals(name)) {
          manufCond = "UPPER(mf.short_desc) like '%" +
                      strValue.toUpperCase().replaceAll("'", "''") +
                      "%'";
        } else if (SearchCriteria.DISTRIBUTOR_SHORT_DESC.equals(name)) {
          distCond = "UPPER(dist.short_desc) like '%" +
                     strValue.toUpperCase().replaceAll("'", "''") +
                     "%'";
        } else if (SearchCriteria.STORE_SKU_NUMBER.equals(name)) {
          storeSkuCond = "UPPER(cstr.customer_sku_num) = '" +
                         strValue.toUpperCase().replaceAll("'", "''") +
                         "'";
        } else if (SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(name)) {
          distSkuCond = "UPPER(dim.item_num) like '%" +
                        strValue.toUpperCase().replaceAll("'", "''") +
                        "%'";
        } else if (SearchCriteria.MANUFACTURER_SKU_NUMBER.equals(name)) {
          manufSkuCond = "UPPER(mfim.item_num) like '%" +
                         strValue.toUpperCase().replaceAll("'", "''") +
                         "%'";
        } else if (SearchCriteria.CUST_SKU_NUMBER.equals(name)) {
        	String selCatalogIds = getCatalogIdsByStore(storeIdI.intValue());

          DBCriteria catStrDbc = new DBCriteria();
          catStrDbc.addContainsIgnoreCase(CatalogStructureDataAccess.
                                          CUSTOMER_SKU_NUM,
                                          strValue.toUpperCase());
          catStrDbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, selCatalogIds);
          IdVector itemIdV = CatalogStructureDataAccess.selectIdOnly(con,
            CatalogStructureDataAccess.ITEM_ID, catStrDbc);
          if (itemIdV.size() > 1000) {
            String errorMess = "^clw^Sku criteria is to broad^clw^";
            throw new Exception(errorMess);
          }
          if (itemIdV.size() == 0) {
            itemIdCond = "1=2";
          } else {
            itemIdCond = "i.item_id in (" + IdVector.toCommaString(itemIdV) +
                         ")";
          }
        }
      }

      //get store catalog
      String sql = "select c.catalog_id " +
                   " from clw_catalog_assoc ca, clw_catalog c" +
                   " where c.catalog_type_cd = 'STORE'" +
                   " and c.catalog_status_cd in 'ACTIVE'" +
                   " and c.catalog_id = ca.catalog_id" +
                   " and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
                   " and ca.bus_entity_id = " + storeIdI;

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      int storeCatalogId = 0;
      int count = 0;
      while (rs.next()) {
        count++;
        storeCatalogId = rs.getInt(1);
      }
      rs.close();
      stmt.close();
      if (count == 0) {
        String errorMess = "No catalog for store. Store id: " + storeIdI;
        throw new Exception(errorMess);
      }
      if (count > 1) {
        String errorMess = "Multiple active catalogs for store. Store id: " +
                           storeIdI;
        throw new Exception(errorMess);
      }

      int catalogId = catalogIdI.intValue();
      if (catalogId == 0) {
        String errorMess = "^clw^Catalog is not provided^clw^";
        throw new Exception(errorMess);
      } else { //check whether the catalog belongs to the store
        DBCriteria catalogStoreDbc = new DBCriteria();
        catalogStoreDbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, catalogId);
        catalogStoreDbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,
                                   storeIdI.intValue());
        catalogStoreDbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        IdVector catalogAssocIdV = CatalogAssocDataAccess.selectIdOnly(con,
          catalogStoreDbc);
        if (catalogAssocIdV.size() == 0) {
          String errorMess = "Catalog doesn't belont to the store. Store id: " +
                             storeIdI + " Catalog id: " + catalogId;
          throw new Exception(errorMess);
        }
      }

      //get contract id
      int contractId = 0;
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ContractDataAccess.CATALOG_ID, catalogId);
      dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                     RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
      IdVector contractIdV = ContractDataAccess.selectIdOnly(con, dbc);
      if (contractIdV.size() > 1) {
        String errorMess = "Catalog has multiple contracts. Catalog id: " +
                           catalogId;
        throw new Exception(errorMess);
      }
      if (contractIdV.size() == 1) {
        Integer contractIdI = (Integer) contractIdV.get(0);
        contractId = contractIdI.intValue();
      }

      //generate sql conditions
      sql = "select " +
            " i.item_id, " +
            "   cstr.customer_sku_num as sku_num, " +
            "   i.short_desc,  " +
            "   uom.clw_value uom, " +
            "   isize.clw_value isize, " +
            "   pack.clw_value pack, " +
            "   color.clw_value color, " +
            "   categ.short_desc category, " +
            "   categ.item_id category_id, " +
            "   mfim.item_num manuf_sku, " +
            "   mf.bus_entity_id manuf_id, " +
            "   mf.short_desc manuf, " +
            "   dim.item_num dist_sku, " +
            "   dist.bus_entity_id dist_id, " +
            "   dist.short_desc dist " +
            "   ci.amount " +
            "   ci.dist_cost" +
            "   ci.dist_base_cost" +
            " from " +
            "   clw_item i, " +
            "   clw_catalog_structure cstr, " +
            "   clw_catalog_structure cstr1, " +
            "   clw_item_meta uom, " +
            "   clw_item_meta color, " +
            "   clw_item_meta isize, " +
            "   clw_item_meta pack, " +
            "   clw_item_assoc ia, " +
            "   clw_item categ, " +
            "   clw_item_mapping mfim, " +
            "   clw_bus_entity mf, " +
            "   clw_item_mapping dim, " +
            "   clw_bus_entity dist, " +
            "   clw_contract_item ci ";
      if (metaCond != null) {
        sql +=
          "   ,clw_item_meta im ";
      }

      sql +=
        " where i.item_id = cstr.item_id " +
        " and i.item_id = cstr1.item_id " +
        " and cstr.catalog_id = " + storeCatalogId +
        " and cstr1.catalog_id = " + catalogId +
        " and cstr.catalog_structure_cd = 'CATALOG_PRODUCT'" +
        " and cstr1.catalog_structure_cd = 'CATALOG_PRODUCT'" +
        " and i.item_id = uom.item_id(+) " +
        " and uom.name_value(+) = 'UOM'  " +
        " and i.item_id = isize.item_id(+) " +
        " and isize.name_value(+) = 'SIZE'  " +
        " and i.item_id = pack.item_id(+) " +
        " and pack.name_value(+) = 'PACK'  " +
        " and i.item_id = color.item_id(+) " +
        " and color.name_value(+) = 'COLOR' " +
        " and categ.item_id(+) = ia.item2_id " +
        " and ia.item1_id(+) = i.item_id " +
        " and ia.item_assoc_cd(+) = 'PRODUCT_PARENT_CATEGORY' " +
        " and ia.catalog_id(+) =  " + catalogId +
        " and mfim.item_id(+) = i.item_id " +
        " and mfim.item_mapping_cd(+) = 'ITEM_MANUFACTURER' " +
        " and mf.bus_entity_id(+) = mfim.bus_entity_id " +
        " and dim.item_mapping_cd(+) = 'ITEM_DISTRIBUTOR' " +
        " and dim.item_id(+) = cstr1.item_id " +
        " and dim.bus_entity_id(+) = cstr1.bus_entity_id" +
        " and dist.bus_entity_id(+) = dim.bus_entity_id " +
        " and ci.contract_id(+) = " + contractId +
        " and ci.item_Id(+) = cstr1.item_id ";

      if (storeSkuCond != null) {
        sql += " and " + storeSkuCond;
      }
      if (metaCond != null) {
        sql +=
          " and im.item_id = i.item_id " +
          " and " + metaCond;
      }
      if (manufSkuCond != null) {
        sql += " and " + manufSkuCond;
      }
      if (shortDesc != null) {
        sql += " and " + shortDesc;
      }
      if (longDesc != null) {
        sql += " and " + longDesc;
      }
      if (manufCond != null) {
        sql += " and " + manufCond;
      }
      if (categCond != null) {
        sql += " and " + categCond;
      }
      if (distSkuCond != null || distCond != null) {
        sql +=
          " and dim.item_mapping_cd = 'ITEM_DISTRIBUTOR' " +
          " and dim.item_id = i.item_id ";
      }
      if (distSkuCond != null) {
        sql += " and " + distSkuCond;
      }

      if (itemIdCond != null) {
        sql += " and " + itemIdCond;
      }
      if (distCond != null) {
        sql +=
          " and dist.bus_entity_id = dim.bus_entity_id " +
          " and " + distCond;
      }

      sql += " order by cstr.customer_sku_num ";

      logDebug(
        "CatalogInformationBena CCCCCCCCCCCCCCCCCCCCCCCCCCC sql: " + sql);
      stmt = con.createStatement();
      rs = stmt.executeQuery(sql);
      CatalogItemDescViewVector itemVwV = new CatalogItemDescViewVector();
      while (rs.next()) {
        CatalogItemDescView itemVw = CatalogItemDescView.createValue();
        itemVwV.add(itemVw);
        itemVw.setStoreId(storeIdI.intValue());
        itemVw.setCatalogId(catalogId);
        itemVw.setItemId(rs.getInt("item_id"));
        itemVw.setCategoryId(rs.getInt("category_id"));
        itemVw.setManufId(rs.getInt("manuf_id"));
        itemVw.setName(Utility.strNN(rs.getString("short_desc")));
        itemVw.setSku(Utility.strNN(rs.getString("sku_num")));
        itemVw.setUom(Utility.strNN(rs.getString("uom")));
        itemVw.setSize(Utility.strNN(rs.getString("isize")));
        itemVw.setPack(Utility.strNN(rs.getString("pack")));
        itemVw.setColor(Utility.strNN(rs.getString("color")));
        itemVw.setCategory(Utility.strNN(rs.getString("category")));
        itemVw.setManufName(Utility.strNN(rs.getString("manuf")));
        itemVw.setManufSku(Utility.strNN(rs.getString("manuf_sku")));
        itemVw.setDistId(rs.getInt("dist_id"));
        itemVw.setDistName(Utility.strNN(rs.getString("dist")));
        itemVw.setDistSku(Utility.strNN(rs.getString("dist_sku")));

        BigDecimal priceBD = rs.getBigDecimal("amount");
        if (priceBD == null) priceBD = new BigDecimal(0);
        priceBD = priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
        itemVw.setCatalogPrice(priceBD);

        BigDecimal distCostBD = rs.getBigDecimal("dist_cost");
        if (distCostBD == null) distCostBD = new BigDecimal(0);
        distCostBD = distCostBD.setScale(2, BigDecimal.ROUND_HALF_UP);
        itemVw.setDistCost(distCostBD);

        BigDecimal baseCostBD = rs.getBigDecimal("dist_base_cost");
        if (baseCostBD == null) baseCostBD = new BigDecimal(0);
        baseCostBD = baseCostBD.setScale(2, BigDecimal.ROUND_HALF_UP);
        itemVw.setBaseCost(baseCostBD);

        itemVw.setSelected(false);
      }
      rs.close();
      stmt.close();
      return itemVwV;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets store product
   *@param  pStoreId store id
   *@param  pItemId item di
   *@return  catalog product data
   *@exception  RemoteException
   */
  public ProductData getStoreProduct(int pStoreId, int pItemId) throws   RemoteException{
    Connection con = null;
      try {
          con = getConnection();
          return getStoreProduct(pStoreId, pItemId, con);
      } catch (DataNotFoundException e) {
          throw new RemoteException(e.getMessage());
      } catch (NamingException exc) {
          logError("exc.getMessage");
          throw new RemoteException("Error. CatalogInformationBean.getStoreProduct() Naming Exception happened");
      } catch (SQLException exc) {
          logError("exc.getMessage");
          throw new RemoteException("Error. CatalogInformationBean.getStoreProduct() SQL Exception happened");
      } catch (Exception exc) {
          logError("exc.getMessage");
          throw new RemoteException("Error. CatalogInformationBean.getStoreProduct() "+exc.getMessage());
      } finally {
          closeConnection(con);
    }
  }

  /**
   *  Gets store product (overloaded from above)
   *@param  pStoreId store id
   *@param  pItemId item di
   *@return  catalog product data
   *@exception  RemoteException
   */
  protected ProductData getStoreProduct(int pStoreId, int pItemId,
                                        Connection con) throws Exception {
    CatalogDataVector catalogDV = getCatalogsCollectionByBusEntity(
      pStoreId, RefCodeNames.CATALOG_TYPE_CD.STORE);
    CatalogData cD = null;
    for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
      cD = (CatalogData) iter.next();
      if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD
        .getCatalogStatusCd())) {
        break;
      }
    }
    if (cD == null) {
      throw new Exception("No active catalog for the store. Store id: "
                          + pStoreId);
    }

    int catalogId = cD.getCatalogId();

    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
    dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
    IdVector itemIdV = CatalogStructureDataAccess.selectIdOnly(con,
      CatalogStructureDataAccess.ITEM_ID, dbc);
    if (itemIdV.size() == 0) {
      String errorMess = "^clw^No item found. Item id: " + pItemId
                         + "^clw^";
      throw new DataNotFoundException(errorMess);
    }

    ProductData productD = readDbProductData(con, cD.getCatalogId(), pItemId);
    parseItemDataFields(productD, pStoreId, con);
    return productD;

  }


  private void parseItemDataFields(ProductData productD, int pStoreId, Connection con) throws Exception {
      APIAccess factory = new APIAccess();
      PropertyService psvcBean = factory.getPropertyServiceAPI();
      BusEntityFieldsData fieldsData = psvcBean.fetchMasterItemFieldsData(pStoreId);
      ItemMetaDataVector aaa = productD.getProductAttributes();
      Iterator j = aaa.iterator();

      if (fieldsData != null && PropertyFieldUtil.isShowInAdmin(fieldsData)) {
        ItemMetaDataVector dataFields = new ItemMetaDataVector();
        for (int i=1; i<=15; i++) {
            if (PropertyFieldUtil.getShowInAdmin(fieldsData, i)) {
                String tagName = PropertyFieldUtil.getTag(fieldsData, i);
                ItemMetaData dataField = productD.getItemMeta(tagName);
                if (dataField != null) {
                    dataFields.add(dataField);
                    productD.removeItemMeta(tagName);
                } else {
                    dataField = ItemMetaData.createValue();
                    dataField.setNameValue(tagName);
                    dataField.setItemId(productD.getItemData().getItemId());
                    dataFields.add(dataField);
                }
            }
        }
        if (dataFields.size() > 0) {
            productD.setDataFieldProperties(dataFields);
        }
      }
  }

  /**
   * Gets store categories
   *
   * @param pStoreId
   *            store id
   * @return set of ItemData objects
   * @exception RemoteException
   */
  public ItemDataVector getStoreCategories(int pStoreId) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      CatalogDataVector catalogDV =
        getCatalogsCollectionByBusEntity(pStoreId,
                                         RefCodeNames.CATALOG_TYPE_CD.STORE);
      CatalogData cD = null;
      for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
        cD = (CatalogData) iter.next();
        if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
          break;
        }
      }
      if (cD == null) {
        throw new Exception("No active catalog for the store. Store id: " +
                            pStoreId);
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, cD.getCatalogId());
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
      IdVector categIdV = CatalogStructureDataAccess.
                          selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID,
                                       dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, categIdV);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

      ItemDataVector categDV = ItemDataAccess.select(con, dbc);

      return categDV;

    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }
  public MultiproductViewVector getStoreMultiproducts(int pStoreId)  throws RemoteException {
	  return getStoreMultiproducts(pStoreId, null);
  }

  public MultiproductViewVector getStoreMultiproducts(int pStoreId, String name)
      throws RemoteException {

      Connection con = null;
      try {
        con = getConnection();
        CatalogDataVector catalogDV =
          getCatalogsCollectionByBusEntity(pStoreId,
                                           RefCodeNames.CATALOG_TYPE_CD.STORE);
        CatalogData cD = null;
        for (Iterator iter = catalogDV.iterator(); iter.hasNext(); ) {
          cD = (CatalogData) iter.next();
          if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(cD.getCatalogStatusCd())) {
            break;
          }
        }
        if (cD == null) {
          throw new Exception("No active catalog for the store. Store id: " +
                              pStoreId);
        }

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, cD.getCatalogId());
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MULTI_PRODUCT);
        IdVector mIdV = CatalogStructureDataAccess.
                            selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID,
                                         dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, mIdV);
        if (Utility.isSet(name)){
        	dbc.addEqualTo(ItemDataAccess.SHORT_DESC, name);
        }
        dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

        ItemDataVector mpDV = ItemDataAccess.select(con, dbc);

        MultiproductViewVector mVV = new MultiproductViewVector();
        for (Iterator multiproductIter = mpDV.iterator(); multiproductIter.hasNext(); ) {
            ItemData multiproductItemData = (ItemData) multiproductIter.next();
            MultiproductView mView = new MultiproductView();
            mView.setItemData(multiproductItemData);
            mView.setMultiproductId(multiproductItemData.getItemId());
            mView.setMultiproductName(multiproductItemData.getShortDesc());
            mView.setCatalogId(cD.getCatalogId());
            mView.setCatalogName(cD.getShortDesc());
            mVV.add(mView);
        }

        return mVV;

      } catch (Exception e) {
        e.printStackTrace();
        throw new RemoteException(e.getMessage());
      } finally {
        closeConnection(con);
      }
  }

  /**
   *Gets all major categories for store catalog
   * @param pStoreCatalogId store catalog id
   *@throws RemoteException
   */
  public ItemDataVector getStoreMajorCategories(int pStoreCatalogId) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MAJOR_CATEGORY);
      IdVector mjCatIdV = CatalogStructureDataAccess.selectIdOnly(con,
        CatalogStructureDataAccess.ITEM_ID, dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
      dbc.addOneOf(ItemDataAccess.ITEM_ID, mjCatIdV);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
      logDebug(
        "CatalogInformaitonBean CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC sql: " +
        ItemDataAccess.getSqlSelectIdOnly("*", dbc));
      ItemDataVector majorCatDV = ItemDataAccess.select(con, dbc);
      return majorCatDV;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets store categories, which do not have parent categories or parent
   *  products.
   *
   *@param  pStoreCatalogId        the catalog identifier
   *@return                   CatalogCategoryDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */
  public CatalogCategoryDataVector getStoreCatalogCategories(int
    pStoreCatalogId) throws RemoteException {
    Connection con = null;
    CatalogCategoryDataVector catalogCategoryDV =
      new CatalogCategoryDataVector();
    try {
      con = getConnection();

      // Get the category names alphabetically.
      String sql = "select i.item_id " +
                   " from clw_item i, clw_catalog_structure cs" +
                   " where cs.catalog_id = '" + pStoreCatalogId + "'" +
                   " and i.item_id = cs.item_id" +
                   " and cs.catalog_structure_cd = '" +
                   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'" +
                   " and i.item_id not in ( select item1_id from " +
                   " clw_item_assoc " +
                   "where catalog_id = " + pStoreCatalogId +
                   " and item_assoc_cd = '" +
                   RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "'" +
                   " )" +
                   " order by i.short_desc";
      logDebug(
        "CatlogInformationBean IIIIIIIIIIIIIIIIIIIIIIIIIII sql: " + sql);
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      IdVector topCategoryIds = new IdVector();
      while (rs.next()) {
        Integer ti = new Integer(rs.getInt(1));
        topCategoryIds.add(ti);
      }
      stmt.close();

      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, topCategoryIds);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      int ii = itemDV.size();
      while (ii > 0) {
        ii--;
        ItemData itemD = (ItemData) itemDV.get(ii);
        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {
          throw new RemoteException(
            "No item_assoc - item consitency in the Catalog. Catalog id: " +
            pStoreCatalogId + " Item id: " + itemD.getItemId());
        }
        CatalogCategoryData ccD = new CatalogCategoryData();
        ccD.setItemData(itemD);
        catalogCategoryDV.add(ccD);
      }

      // set major categories
      IdVector catIdV = new IdVector();
      Iterator iter = catalogCategoryDV.iterator();
      while (iter.hasNext()) {
        CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
        catIdV.add(new Integer(ccD.getItemData().getItemId()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, catIdV);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);
      String majorAssocReq =
        ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID,
                                               dbc);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, majorAssocReq);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector majorCatDV = ItemDataAccess.select(con, dbc);

      iter = catalogCategoryDV.iterator();
      while (iter.hasNext()) {
        CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
        int cId = ccD.getItemData().getItemId();
        Iterator iter1 = itemAssocDV.iterator();
        int majCatId = 0;
        mmm:while (iter1.hasNext()) {
          ItemAssocData iaD = (ItemAssocData) iter1.next();
          int acId = iaD.getItem1Id();
          if (acId > cId) {
            break;
          }
          if (acId == cId) {
            int amId = iaD.getItem2Id();
            Iterator iter2 = majorCatDV.iterator();
            while (iter2.hasNext()) {
              ItemData iD = (ItemData) iter2.next();
              int mId = iD.getItemId();
              if (mId > amId) {
                break mmm;
              }
              if (mId == amId) {
                if (majCatId == 0) {
                  majCatId = mId;
                  ccD.setMajorCategory(iD);
                } else if (majCatId != mId) {
                  ccD.setMajorCategory(null);
                  logError(
                    "Category has more than one major category. Category id = " +
                    cId);
                  break mmm;
                }
              }
            }
          }
        }
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getTopCatalogCategories() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getTopCatalogCategories() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogCategoryDV;
  }

  /**
   *  Gets store categories
   *
   *@param  pStoreCatalogId        the catalog identifier
   *@return                   CatalogCategoryDataVector
   *@throws  RemoteException  Required by EJB 1.0
   */
  public CatalogCategoryDataVector getAllStoreCatalogCategories(int
    pStoreCatalogId) throws RemoteException {
    Connection con = null;
    CatalogCategoryDataVector catalogCategoryDV =
      new CatalogCategoryDataVector();
    try {
      con = getConnection();
    LinkedList topCatList = getTopCatList(con, pStoreCatalogId);


      Iterator allCatsIterator = topCatList.iterator();
      IdVector allCategoryIds = new IdVector();
      for(; allCatsIterator.hasNext(); ) {
    	  Integer[] cLE = (Integer[]) allCatsIterator.next();
    	  allCategoryIds.add(cLE[0]);
      }


      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, allCategoryIds);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      int ii = itemDV.size();
      if (ii != topCatList.size()){
    	  throw new RemoteException("Error. CatalogInformationBean.getAllStoreCatalogCategories() " +
    	  		"Duplicated item association (CATEGORY_PARENT_CATEGORY) for store catalog id " + pStoreCatalogId + " occured");
      }
      int[] levels = new int[ii];
      int iii = ii - 1;
      int categoryLevel;

      ItemDataVector itemDVOrdered = new ItemDataVector();
      for (Iterator tCLI = topCatList.iterator(); tCLI.hasNext(); iii--) {
		  Integer[] topCatListElement = (Integer[]) tCLI.next();
		  int idFromTopCatList = topCatListElement[0].intValue();
		  for(Iterator categoryItemsIterator = itemDV.iterator(); categoryItemsIterator.hasNext(); ) {
			  ItemData categoryItemFromItemDV = (ItemData) categoryItemsIterator.next();
			  int id = categoryItemFromItemDV.getItemId();
    		  if (idFromTopCatList==id) {
    			  categoryLevel = topCatListElement[1].intValue();
    			  levels[iii] = categoryLevel;
    			  itemDVOrdered.add(categoryItemFromItemDV);
    			  break;
    		  }
    	  }
      }
      
      // get lowest level category
      String selLowestLevelCategory = "select i.item_id category_id \r\n" +
      		"from clw_catalog c, clw_item i, clw_catalog_structure cs \r\n" +
      		"where c.catalog_id = " + pStoreCatalogId + " \r\n" +
      		"and cs.catalog_id = c.catalog_id \r\n" +
      		"and i.item_id = cs.item_id \r\n" +
      		"and cs.catalog_structure_cd = 'CATALOG_CATEGORY' \r\n" +
      		"and i.item_id not in (select item2_id from \r\n" +
      		"  clw_item_assoc where catalog_id = c.catalog_id \r\n" +
      		"  and item_assoc_cd = 'CATEGORY_PARENT_CATEGORY' \r\n" +
      		")";
      List<Integer> lowestLevelCategoryList = new ArrayList<Integer>();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(selLowestLevelCategory);
      while(rs.next()){
    	  lowestLevelCategoryList.add(rs.getInt(1));
      }

      ii = -1;
      int s = levels.length;
      while (ii < (s-1)) {
        ii++;
        ItemData itemD = (ItemData) itemDVOrdered.get(ii);
        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {
          throw new RemoteException(
            "No item_assoc - item consistency in the Catalog. Catalog id: " +
            pStoreCatalogId + " Item id: " + itemD.getItemId());
        }
        CatalogCategoryData ccD = new CatalogCategoryData();
        ccD.setItemData(itemD);
        ccD.setTreeLevel(levels[s - ii - 1]);
        ccD.setLowestLevel(lowestLevelCategoryList.contains(itemD.getItemId()));
        catalogCategoryDV.add(ccD);
      }

      // set major categories
      IdVector catIdV = new IdVector();
      Iterator iter = catalogCategoryDV.iterator();
      while (iter.hasNext()) {
        CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
        catIdV.add(new Integer(ccD.getItemData().getItemId()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, catIdV);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);
      String majorAssocReq =
        ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID,
                                               dbc);
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, majorAssocReq);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector majorCatDV = ItemDataAccess.select(con, dbc);

      iter = catalogCategoryDV.iterator();
      while (iter.hasNext()) {
        CatalogCategoryData ccD = (CatalogCategoryData) iter.next();
        int cId = ccD.getItemData().getItemId();
        Iterator iter1 = itemAssocDV.iterator();
        int majCatId = 0;
        mmm:while (iter1.hasNext()) {
          ItemAssocData iaD = (ItemAssocData) iter1.next();
          int acId = iaD.getItem1Id();
          if (acId > cId) {
            break;
          }
          if (acId == cId) {
            int amId = iaD.getItem2Id();
            Iterator iter2 = majorCatDV.iterator();
            while (iter2.hasNext()) {
              ItemData iD = (ItemData) iter2.next();
              int mId = iD.getItemId();
              if (mId > amId) {
                break mmm;
              }
              if (mId == amId) {
                if (majCatId == 0) {
                  majCatId = mId;
                  ccD.setMajorCategory(iD);
                } else if (majCatId != mId) {
                  ccD.setMajorCategory(null);
                  logError(
                    "Category has more than one major category. Category id = " +
                    cId);
                  break mmm;
                }
              }
            }
          }
        }
      }
    } catch (NamingException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException("Error. CatalogInformationBean.getAllStoreCatalogCategories() Naming Exception happened");
    } catch (SQLException exc) {
      logError("exc.getMessage");
      exc.printStackTrace();
      throw new RemoteException(
        "Error. CatalogInformationBean.getAllStoreCatalogCategories() SQL Exception happened");
    } finally {
      closeConnection(con);
    }
    return catalogCategoryDV;
  }

  private IdVector getTopCategoriesAssocForStore(Connection con, int pStoreCatalogId) throws SQLException {
      // Get the category names alphabetically.
      String sql = "select i.item_id \n" +
          " from clw_item i, clw_catalog_structure cs  \n" +
          " where cs.catalog_id = " + pStoreCatalogId +
          " and i.item_id = cs.item_id  \n" +
          " and cs.catalog_structure_cd = '" +
          RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "' \n" +
          " and i.item_id in \n" +
          "   ( select item2_id from clw_item_assoc \n" +
          "      where catalog_id = " + pStoreCatalogId + "  \n" +
          "        and item_assoc_cd = '" +
          RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' ) \n" +
          " and i.item_id not in \n" +
          "   ( select item1_id from clw_item_assoc \n" +
          "      where catalog_id = " + pStoreCatalogId + "  \n" +
          "        and item_assoc_cd = '" +
          RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' ) \n" +
          " order by i.item_id";
      logDebug("CatlogInformationBean IIIIIIIIIIIIIIIIIIII sql: " + sql);

      logInfo("CatlogInformationBean IIIIIIIIIIIIIIIIIIII sql: " + sql);
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      IdVector topCategoryIds = new IdVector();
      while (rs.next()) {
        Integer ti = new Integer(rs.getInt(1));
        topCategoryIds.add(ti);
      }
      stmt.close();
      return topCategoryIds;
  }

  private LinkedList   getTopCatList(Connection con, int pStoreCatalogId) throws SQLException {
      LinkedList topCatList = new LinkedList();
      IdVector topListAssoc = getTopCategoriesAssocForStore(con, pStoreCatalogId);

      String sqlTop = "select distinct  1 LEV, i.item_id PARENT, 0 CHILD, i.short_desc DESC_NAME \n" +
             " from clw_item i, clw_catalog_structure cs \n" +
               " where cs.catalog_id = " + pStoreCatalogId + "  \n" +
               " and i.item_id = cs.item_id \n" +
               " and cs.catalog_structure_cd = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "' \n" +
                   " and i.item_id not in ( select item1_id from  \n" +
                   " clw_item_assoc " +
                   "where catalog_id = " + pStoreCatalogId + "  \n" +
                   " and item_assoc_cd = '" + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' \n" +
                   " ) \n" +
                   " order by DESC_NAME";

        String sql ="select level+1 LEV, ITEM2_ID PARENT,ITEM1_ID CHILD , ITEM_ASSOC_ID from \n" +
                    "(select ITEM2_ID,ITEM1_ID, ITEM_ASSOC_ID \n" +
                    "from CLW_ITEM_ASSOC \n" +
                    "where CATALOG_ID = " + pStoreCatalogId + "  \n" +
                    "and ITEM_ASSOC_CD ='" + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "') a \n"+
                    "connect by PRIOR ITEM1_ID = ITEM2_ID  \n" +
                    "start with ITEM2_ID in (" + IdVector.toCommaString(topListAssoc) + ")" ;// +
        
    logDebug(
       "CatlogInformationBean VVVVVVVVVVVVVVVVVVVVVVVVVVVV sql: " + sqlTop);
    logInfo(
      "CatlogInformationBean VVVVVVVVVVVVVVVVVVVVVVVVVVVV sqlTop: " + sqlTop);
     logInfo(
       "CatlogInformationBean VVVVVVVVVVVVVVVVVVVVVVVVVVVV sql: " + sql);

     Statement stmtTop = con.createStatement();
     ResultSet rsTop = stmtTop.executeQuery(sqlTop);
     while (rsTop.next()) {
       Integer lev = new Integer(rsTop.getInt(1));
       Integer par = new Integer(rsTop.getInt(2));
       Integer[] p = { null, null};
       p[0] = par;
       p[1] = lev;
       topCatList.add(p);
     }
     stmtTop.close();

     HashMap map = new HashMap();
     List subCatList = new ArrayList();
     if (topListAssoc != null &&  topListAssoc.size() >0) {
       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery(sql);

       Integer topCat = (Integer)topListAssoc.get(0);
       boolean isTopCatChanged = false;
       while (rs.next()) {
         Integer lev = new Integer(rs.getInt(1));
         Integer par = new Integer(rs.getInt(2));
         Integer chil = new Integer(rs.getInt(3));
         isTopCatChanged = (topListAssoc.contains(par) && !par.equals(topCat) );
         if (isTopCatChanged ) {
           map.put(topCat, subCatList);
           subCatList = new ArrayList();
           topCat = par;
         }
         Integer[] p = { null, null};
         p[0] = chil;
         p[1] = lev;
         subCatList.add(p);
       }
       if (subCatList.size() >0 ) {
         map.put(topCat, subCatList);
       }
       stmt.close();
     }
     if (topCatList != null){
       ListIterator allCategoriesIterator = topCatList.listIterator();
       for (; allCategoriesIterator.hasNext(); ) {
         Integer[] catPair = (Integer[]) (allCategoriesIterator.next());
         List childCatList = (List) map.get(catPair[0]);
         if (childCatList != null) {
           for (Iterator cCI = childCatList.iterator(); cCI.hasNext(); ) {
             Integer[] childCategory = (Integer[]) cCI.next();
             allCategoriesIterator.add(childCategory);
           }
         }
       }
     }
     logInfo("====================topCatList.size() = " + topCatList.size());
      return topCatList;
  }

  /**
   * Removes the catalog from database with it stucture and associations
   * @param pCatalogId  the catalog id.
   * @param pContractId  the contract id.
   * @throws            RemoteException
   */
  public boolean canRemoveCatalogContract(int pCatalogId, int pContractId) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc;
      //Order guide
      ArrayList types = new ArrayList();
      types.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      types.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.DELETED);
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
      dbc.addNotOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, types);
      IdVector idV = OrderGuideDataAccess.selectIdOnly(con, dbc);
      if (idV.size() > 0)return false;

      dbc = new DBCriteria();
      dbc.addEqualTo(EstimatorFacilityDataAccess.CATALOG_ID, pCatalogId);
      idV = EstimatorFacilityDataAccess.selectIdOnly(con, dbc);
      logDebug(
        "CatalogInformationBean.java RRRRRRRRRRRRRRRRRR estimator facility: " +
        idV.size());
      if (idV.size() > 0)return false;

      //return if no contract
      if (pContractId == 0)return true;

      dbc = new DBCriteria();
      dbc.addEqualTo(OrderDataAccess.CONTRACT_ID, pContractId);
      logDebug(
        "CatalogInformationBean.java RRRRRRRRRRRRRRRRRR contract: " + idV.size());
      idV = OrderDataAccess.selectIdOnly(con, dbc);
      if (idV.size() > 0)return false;

      dbc = new DBCriteria();
      dbc.addEqualTo(OrderRoutingDataAccess.CONTRACT_ID, pContractId);
      idV = OrderRoutingDataAccess.selectIdOnly(con, dbc);
      logDebug(
        "CatalogInformationBean.java RRRRRRRRRRRRRRRRRR order routing: " +
        idV.size());
      if (idV.size() > 0)return false;

      dbc = new DBCriteria();
      dbc.addEqualTo(PreOrderDataAccess.CONTRACT_ID, pContractId);
      idV = PreOrderDataAccess.selectIdOnly(con, dbc);
      logDebug(
        "CatalogInformationBean.java RRRRRRRRRRRRRRRRRR pre order: " + idV.size());
      if (idV.size() > 0)return false;

      return true;

    } catch (Exception exc) {
      String errorMess = exc.getMessage();
      logError(errorMess);
      exc.printStackTrace();
      throw new RemoteException(errorMess);
    } finally {
      try {
        con.close();
      } catch (SQLException exc) {
        String errorMess = exc.getMessage();
        logError(errorMess);
        exc.printStackTrace();
        throw new RemoteException(errorMess);
      }
    }
  }

  /**
   * Prepares product template from another store product
   * @param pDestStoreId destination store id
   * @param pSrcStoreId source store id
   * @param pProduct product to import
   * @param user user name
   * @throws            RemoteException
   */
  public ProductData importStoreItem(int pDestStoreId, int pSrcStoreId,
                                     ProductData pProduct, String user) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc;

      //Dest Catalog
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pDestStoreId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      String storeCatalogReq = CatalogAssocDataAccess.getSqlSelectIdOnly(
        CatalogAssocDataAccess.CATALOG_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatalogReq);
      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                     RefCodeNames.CATALOG_TYPE_CD.STORE);
      CatalogDataVector destCatalogDV = CatalogDataAccess.select(con, dbc);

      if (destCatalogDV.size() == 0) {
        String errorMess =
          "No active destination store catalog found. Store id: " +
          pDestStoreId;
        throw new Exception(errorMess);
      }
      if (destCatalogDV.size() > 1) {
        String errorMess =
          "Multiple active destination store catalogs found. Store id: " +
          pDestStoreId;
        throw new Exception(errorMess);
      }

      CatalogData destCatalogD = (CatalogData) destCatalogDV.get(0);

      //Src Catalog
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pSrcStoreId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      String storeCatalogReq1 = CatalogAssocDataAccess.getSqlSelectIdOnly(
        CatalogAssocDataAccess.CATALOG_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatalogReq1);
      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                     RefCodeNames.CATALOG_TYPE_CD.STORE);
      CatalogDataVector srcCatalogDV = CatalogDataAccess.select(con, dbc);

      if (srcCatalogDV.size() == 0) {
        String errorMess = "No active source store catalog found. Store id: " +
                           pDestStoreId;
        throw new Exception(errorMess);
      }
      if (srcCatalogDV.size() > 1) {
        String errorMess =
          "Multiple active source store catalogs found. Store id: " +
          pDestStoreId;
        throw new Exception(errorMess);
      }
      CatalogData srcCatalogD = (CatalogData) destCatalogDV.get(0);

      pProduct.setProductId(0);

      String storeSkuNum = pProduct.getCatalogSkuNum();
      int skuNum = pProduct.getSkuNum();
      try {
        if (Integer.parseInt(storeSkuNum) == skuNum) {
          storeSkuNum = null;
        }
      } catch (Exception exc) {}

      CatalogStructureData csD = pProduct.getCatalogStructure();
      csD.setCatalogId(destCatalogD.getCatalogId());
      csD.setCustomerSkuNum(storeSkuNum);
      csD.setItemId(0);
      csD.setAddBy(user);
      csD.setModBy(user);
      pProduct.setCatalogStructure(csD);

      pProduct.setSkuNum(0);
      pProduct.setExpDate(null);
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      Date today = new Date();
      pProduct.setEffDate(today);
      String shortDesc = "Import >>>" + pProduct.getShortDesc();
      if (shortDesc.length() > 255) shortDesc = shortDesc.substring(0, 255);
      pProduct.setShortDesc(shortDesc);
      pProduct.setDistributorMappings(null);
      pProduct.setMappedDistributors(null);
      pProduct.setCatalogDistrMapping(null);

      //Set manufacturer
      ItemMappingData manufMappingD = pProduct.getManuMapping();
      BusEntityData manufBusEntD = pProduct.getManufacturer();
      if (manufMappingD != null && manufBusEntD != null) {
        String manufName = manufBusEntD.getShortDesc();

        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pDestStoreId);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                       RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
        String manufReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(
          BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, manufReq);
        dbc.addEqualTo(BusEntityDataAccess.SHORT_DESC, manufName);
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                       RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                       RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        BusEntityDataVector mfgBusEntDV =
          BusEntityDataAccess.select(con, dbc);

        manufMappingD.setBusEntityId(0);
        manufMappingD.setItemId(0);
        manufMappingD.setItemMappingId(0);
        manufMappingD.setAddBy(user);
        manufMappingD.setModBy(user);

        manufBusEntD.setBusEntityId(0);

        if (mfgBusEntDV.size() == 1) {
          BusEntityData manufBeD = (BusEntityData) mfgBusEntDV.get(0);
          manufMappingD.setBusEntityId(manufBeD.getBusEntityId());
          pProduct.setManuMapping(manufMappingD);
          pProduct.setManufacturer(manufBeD);
        }
      }

      //Set category
      CatalogCategoryDataVector newCategDV = new CatalogCategoryDataVector();
      CatalogCategoryDataVector categDV = pProduct.getCatalogCategories();
      if (categDV != null && categDV.size() >= 1) {
        CatalogCategoryData categD = (CatalogCategoryData) categDV.get(0);
        String categ = categD.getCatalogCategoryShortDesc();
        dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,
                       destCatalogD.getCatalogId());
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                       RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
        String categReq = CatalogStructureDataAccess.getSqlSelectIdOnly(
          CatalogStructureDataAccess.ITEM_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.ITEM_ID, categReq);
        dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                       RefCodeNames.ITEM_TYPE_CD.CATEGORY);
        dbc.addEqualTo(ItemDataAccess.SHORT_DESC, categ);

        ItemDataVector newCategItemDV =
          ItemDataAccess.select(con, dbc);
        if (newCategItemDV.size() == 1) {
          ItemData ciD = (ItemData) newCategItemDV.get(0);
          CatalogCategoryData newCategD =
            getCatalogCategory(destCatalogD.getCatalogId(), ciD.getItemId());
          newCategDV.add(newCategD);
        }
        pProduct.setCatalogCategories(newCategDV);
      }

      return pProduct;

    } catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    } finally {
      try {
        con.close();
      } catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
  }

  /**
   * Gets catalog items
   * @param pStoreCatalogId store catalog id
   * @param pCatalogId catalog id
   * @param pOrderGuideId order guide id (all catalog order guides if 0);
   * @param pItemIdV item ids (all items if null)
   * @param pManufIdV manufacturer ids (no filter if null)
   * @param pDistIdV distributor ids (no filter if null)
   * @param pItemsTypeCd items type cd (Service or Product....))
   * @param user user name
   * @throws            RemoteException
   */
  public ItemCatalogAggrViewVector getItemCatalogMgrSet
    (int pStoreCatalogId, int pCatalogId, int pOrderGuideId, IdVector pItemIdV,
     IdVector pManufIdV, IdVector pDistIdV,String pItemsTypeCd) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      ItemCatalogAggrViewVector itemCatAggrVwV = new ItemCatalogAggrViewVector();
      DBCriteria dbc;

      //Items
      CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId);

      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemsTypeCd))
      {
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);
      }else{
         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      }
      if (pItemIdV != null) {
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, pItemIdV);
      }
      if (pDistIdV != null) {
        dbc.addOneOf(CatalogStructureDataAccess.BUS_ENTITY_ID, pDistIdV);
      }
      if (pManufIdV != null) {
        DBCriteria manufDbc = new DBCriteria();
        manufDbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, pManufIdV);
        manufDbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                            RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
        String manufItemReq =
          ItemMappingDataAccess.getSqlSelectIdOnly(ItemMappingDataAccess.
          ITEM_ID, manufDbc);
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, manufItemReq);
      }
      String sss = CatalogStructureDataAccess.getSqlSelectIdOnly("*", dbc);
      logDebug("CatalogInformationBean CCCCCCCCCCCCCCCCCCCCCCC sql: " +
                         sss);
      
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      CatalogStructureDataVector wrkCatStrDV =
        CatalogStructureDataAccess.select(con, dbc);

      if (pItemIdV != null) {
        pItemIdV = sortIdVector(pItemIdV);
      } else {
        pItemIdV = new IdVector();
      }

      CatalogStructureDataVector catStrDV = new CatalogStructureDataVector();
      Integer wrkItemIdI = null;
      Iterator itemIdIter = pItemIdV.iterator();
      for (Iterator iter = wrkCatStrDV.iterator(); iter.hasNext(); ) {
        CatalogStructureData csD = (CatalogStructureData) iter.next();
        int itemId = csD.getItemId();
        while (wrkItemIdI != null || itemIdIter.hasNext()) {
          if (wrkItemIdI == null) wrkItemIdI = (Integer) itemIdIter.next();
          int iId = wrkItemIdI.intValue();
          if (iId < itemId) {
            CatalogStructureData dumCsD = CatalogStructureData.createValue();
            dumCsD.setCatalogId(pCatalogId);
            dumCsD.setItemId(iId);
            catStrDV.add(dumCsD);
            wrkItemIdI = null;
            continue;
          }
          if (iId == itemId) {
            wrkItemIdI = null;
            break;
          }
          break;
        }
        catStrDV.add(csD);
      }

      while (wrkItemIdI != null || itemIdIter.hasNext()) {
        if (wrkItemIdI == null) wrkItemIdI = (Integer) itemIdIter.next();
        int iId = wrkItemIdI.intValue();
        CatalogStructureData dumCsD = CatalogStructureData.createValue();
        dumCsD.setCatalogId(pCatalogId);
        dumCsD.setItemId(iId);
        catStrDV.add(dumCsD);
        wrkItemIdI = null;
      }

      //Order Guide
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);
      dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,
                     RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      if (pOrderGuideId > 0) {
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_ID, pOrderGuideId);
      }
      OrderGuideDataVector orderGuideDV = OrderGuideDataAccess.select(con, dbc);
      if (orderGuideDV.size() == 0) {
        OrderGuideData dummyOgD = OrderGuideData.createValue();
        dummyOgD.setShortDesc("-");
        orderGuideDV.add(dummyOgD);
      }
      IdVector orderGuideIdV = new IdVector();
      for (Iterator iter = orderGuideDV.iterator(); iter.hasNext(); ) {
        OrderGuideData ogD = (OrderGuideData) iter.next();
        orderGuideIdV.add(new Integer(ogD.getOrderGuideId()));
      }

      int prevDistId = 0;
      IdVector distIdV = new IdVector();
      for (Iterator iter = catStrDV.iterator(); iter.hasNext(); ) {
        CatalogStructureData csD = (CatalogStructureData) iter.next();
        int dId = csD.getBusEntityId();
        if (dId != prevDistId) {
          prevDistId = dId;
          distIdV.add(new Integer(dId));
        }
      }

      IdVector itemIdV = new IdVector();
      for (Iterator iter = catStrDV.iterator(); iter.hasNext(); ) {
        CatalogStructureData csD = (CatalogStructureData) iter.next();
        itemIdV.add(new Integer(csD.getItemId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIdV);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIdV);
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      CatalogStructureDataVector storeCatStrDV =
        CatalogStructureDataAccess.select(con, dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemIdV);

      if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemsTypeCd))
      {
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);
      }
      else
      {
       dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      }

      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemCategAssocDV = ItemAssocDataAccess.select(con,
        dbc);
      IdVector categIdV = new IdVector();
      int prevCategId = 0;
      for (Iterator iter = itemCategAssocDV.iterator(); iter.hasNext(); ) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if (categId != prevCategId) { //just to reduce vector size
          prevCategId = categId;
          categIdV.add(new Integer(categId));
        }
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, categIdV);
      ItemDataVector itemCategDV = ItemDataAccess.select(con, dbc);

      CatalogStructureDataVector itemMAssocDV = null;
      ItemDataVector itemMultiproductDV = null;
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIdV);

      if(RefCodeNames.ITEM_TYPE_CD.PRODUCT.equals(pItemsTypeCd))  {
          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
          dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
          itemMAssocDV = CatalogStructureDataAccess.select(con, dbc);

          IdVector mpIdV = new IdVector();
          int prevMpId = 0;
          for (Iterator iter = itemMAssocDV.iterator(); iter.hasNext(); ) {
              CatalogStructureData iaD = (CatalogStructureData) iter.next();
              int multiproductId = iaD.getItemGroupId();
              if (multiproductId != prevMpId) { //just to reduce vector size
                  prevMpId = multiproductId;
                  mpIdV.add(new Integer(multiproductId));
            }
          }

          dbc = new DBCriteria();
          dbc.addOneOf(ItemDataAccess.ITEM_ID, mpIdV);
          itemMultiproductDV = ItemDataAccess.select(con, dbc);
      }

      CatalogStructureData wrkCatStrD = null;
      for (Iterator iter = storeCatStrDV.iterator(); iter.hasNext(); ) {
        CatalogStructureData storeCatStrD = (CatalogStructureData) iter.next();

        for (Iterator iter1 = orderGuideDV.iterator(); iter1.hasNext(); ) {
          OrderGuideData ogD = (OrderGuideData) iter1.next();
          ItemCatalogAggrView icaVw = ItemCatalogAggrView.createValue();
          itemCatAggrVwV.add(icaVw);
          icaVw.setCatalogId(catalogD.getCatalogId());
          icaVw.setCatalogName(catalogD.getShortDesc());
          icaVw.setCatalogStatus(catalogD.getCatalogStatusCd());
          icaVw.setCatalogType(catalogD.getCatalogTypeCd());

          icaVw.setSkuNum(storeCatStrD.getCustomerSkuNum());
          int itemId = storeCatStrD.getItemId();
          icaVw.setItemId(itemId);
          icaVw.setMultiproductId(storeCatStrD.getItemGroupId());

          icaVw.setOrderGuideId(ogD.getOrderGuideId());
          icaVw.setOrderGuideName(ogD.getShortDesc());
        }
      }

      ItemData wrkItemD = null;
      ItemAssocData wrkItemCategAssocD = null;
      for (Iterator iter = itemCatAggrVwV.iterator(),
                           iter1 = itemDV.iterator(),
                                   iter2 = catStrDV.iterator(),
                                           iter3 = itemCategAssocDV.iterator();
        iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int itemId = icaVw.getItemId();

        while (wrkItemD != null || iter1.hasNext()) {
          if (wrkItemD == null) wrkItemD = (ItemData) iter1.next();
          int id = wrkItemD.getItemId();
          if (id < itemId) {
            wrkItemD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          icaVw.setItemName(wrkItemD.getShortDesc());
          icaVw.setItemStatusCd(wrkItemD.getItemStatusCd());
          break;
        }

        while (wrkCatStrD != null || iter2.hasNext()) {
          if (wrkCatStrD == null) wrkCatStrD = (CatalogStructureData) iter2.
                                               next();
          int id = wrkCatStrD.getItemId();
          if (id < itemId) {
            wrkCatStrD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          icaVw.setCatalogSkuNum(wrkCatStrD.getCustomerSkuNum());
          icaVw.setCatalogSkuNumInp(wrkCatStrD.getCustomerSkuNum());
          icaVw.setMultiproductId(wrkCatStrD.getItemGroupId());
          int distId = wrkCatStrD.getBusEntityId();
          if (distId > 0) {
            icaVw.setDistId(distId);
            icaVw.setDistIdInp("" + distId);
          }
          icaVw.setCostCenterId(wrkCatStrD.getCostCenterId());
          icaVw.setCostCenterIdInp("" + wrkCatStrD.getCostCenterId());

          String taxExemptFlStr = wrkCatStrD.getTaxExempt();
          boolean taxExemptFl = false;
          if (taxExemptFlStr != null) {
            taxExemptFlStr = taxExemptFlStr.trim().toUpperCase();
            if (taxExemptFlStr.startsWith("T") || taxExemptFlStr.startsWith("Y") ||
                taxExemptFlStr.equals("1")) {
              taxExemptFl = true;
            }
          }
          icaVw.setTaxExemptFl(taxExemptFl);
          icaVw.setTaxExemptFlInp(taxExemptFl);

          String specialPermissionFlStr = wrkCatStrD.getSpecialPermission();
          boolean specialPermissionFl = Utility.isTrue(specialPermissionFlStr);
          icaVw.setSpecialPermissionFl(specialPermissionFl);
          icaVw.setSpecialPermissionFlInp(specialPermissionFl);

          if (wrkCatStrD.getCatalogStructureId() > 0) {
            icaVw.setCatalogFl(true);
            icaVw.setCatalogFlInp(true);
          }
          break;
        }

        while (wrkItemCategAssocD != null || iter3.hasNext()) {
          if (wrkItemCategAssocD == null) wrkItemCategAssocD = (ItemAssocData)
            iter3.next();
          int id = wrkItemCategAssocD.getItem1Id();
          if (id < itemId) {
            wrkItemCategAssocD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          int categId = wrkItemCategAssocD.getItem2Id();
          for (Iterator iter4 = itemCategDV.iterator(); iter4.hasNext(); ) {
            ItemData itemCategD = (ItemData) iter4.next();
            if (categId == itemCategD.getItemId()) {
              icaVw.setCategoryName(Utility.getCategoryFullName(itemCategD));
              icaVw.setCategoryId(itemCategD.getItemId());
              icaVw.setCategoryIdInp("" + itemCategD.getItemId());
              break;
            }
          }
          break;
        }
      }

      // ItemMeta
      ArrayList al = new ArrayList();
      al.add("SIZE");
      al.add("PACK");
      al.add("UOM");
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemIdV);
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, al);
      dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);
      ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(con, dbc);

      ItemMetaDataVector sizeDV = new ItemMetaDataVector();
      ItemMetaDataVector uomDV = new ItemMetaDataVector();
      ItemMetaDataVector packDV = new ItemMetaDataVector();
      for (Iterator iter = itemMetaDV.iterator(); iter.hasNext(); ) {
        ItemMetaData imD = (ItemMetaData) iter.next();
        String nameValue = imD.getNameValue();
        if ("SIZE".equals(nameValue)) {
          sizeDV.add(imD);
        } else if ("PACK".equals(nameValue)) {
          packDV.add(imD);
        } else if ("UOM".equals(nameValue)) {
          uomDV.add(imD);
        }
      }
      ItemMetaData wrkSizeD = null;
      ItemMetaData wrkPackD = null;
      ItemMetaData wrkUomD = null;
      for (Iterator iter = itemCatAggrVwV.iterator(),
                           iterUom = uomDV.iterator(),
                                     iterSize = sizeDV.iterator(),
                                                iterPack = packDV.iterator();
        iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int itemId = icaVw.getItemId();

        while (wrkSizeD != null || iterSize.hasNext()) {
          if (wrkSizeD == null) wrkSizeD = (ItemMetaData) iterSize.next();
          int id = wrkSizeD.getItemId();
          if (id < itemId) {
            wrkSizeD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          icaVw.setSkuSize(wrkSizeD.getValue());
          break;
        } while (wrkPackD != null || iterPack.hasNext()) {
          if (wrkPackD == null) wrkPackD = (ItemMetaData) iterPack.next();
          int id = wrkPackD.getItemId();
          if (id < itemId) {
            wrkPackD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          icaVw.setSkuPack(wrkPackD.getValue());
          break;
        } while (wrkUomD != null || iterUom.hasNext()) {
          if (wrkUomD == null) wrkUomD = (ItemMetaData) iterUom.next();
          int id = wrkUomD.getItemId();
          if (id < itemId) {
            wrkUomD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          icaVw.setSkuUom(wrkUomD.getValue());
          break;
        }
      }

      // Manufacturer info
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      ItemMappingDataVector itemManufDV =
        ItemMappingDataAccess.select(con, dbc);
      IdVector manufIdV = new IdVector();
      int prevManufId = 0;
      ItemMappingData wrkManufMapD = null;
      for (Iterator iter = itemCatAggrVwV.iterator(),
                           iter1 = itemManufDV.iterator();
                                   iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int itemId = icaVw.getItemId();

        while (wrkManufMapD != null || iter1.hasNext()) {
          if (wrkManufMapD == null) wrkManufMapD = (ItemMappingData) iter1.next();
          int id = wrkManufMapD.getItemId();
          if (id < itemId) {
            wrkManufMapD = null;
            continue;
          }
          if (id > itemId) {
            break;
          }
          int manufId = wrkManufMapD.getBusEntityId();
          icaVw.setManufId(manufId);
          icaVw.setManufSku(wrkManufMapD.getItemNum());
          if (prevManufId != manufId) {
            prevManufId = manufId;
            manufIdV.add(new Integer(manufId));
          }
          break;
        }
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, manufIdV);
      BusEntityDataVector manufDV = BusEntityDataAccess.select(con, dbc);
      for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int manufId = icaVw.getManufId();
        for (Iterator iter1 = manufDV.iterator(); iter1.hasNext(); ) {
          BusEntityData beD = (BusEntityData) iter1.next();
          if (manufId == beD.getBusEntityId()) {
            icaVw.setManufName(beD.getShortDesc());
            break;
          }
        }
      }

      //Distributor info
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);
      dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, distIdV);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      ItemMappingDataVector itemDistDV =
        ItemMappingDataAccess.select(con, dbc);
      IdVector itemDistIdV = new IdVector();

      for (Iterator iter = itemDistDV.iterator(); iter.hasNext(); ) {
        ItemMappingData imD = (ItemMappingData) iter.next();
        itemDistIdV.add(new Integer(imD.getItemMappingId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID, itemDistIdV);
      dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,
                     RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);
      ItemMappingAssocDataVector itemMapAssocDV =
        ItemMappingAssocDataAccess.select(con, dbc);
      IdVector itemGenManufIdV = new IdVector();
      for (Iterator iter = itemMapAssocDV.iterator(); iter.hasNext(); ) {
        ItemMappingAssocData imaD = (ItemMappingAssocData) iter.next();
        itemGenManufIdV.add(new Integer(imaD.getItemMapping2Id()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, itemGenManufIdV);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      ItemMappingDataVector itemGenManufDV =
        ItemMappingDataAccess.select(con, dbc);

      IdVector genManufIdV = new IdVector();
      ItemMappingData wrkItemDistD = null;
      ItemMappingData wrkItemGenManufD = null;
      for (Iterator iter = itemCatAggrVwV.iterator(),
                           iter1 = itemDistDV.iterator(),
                                   iter2 = itemGenManufDV.iterator();
                                           iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int itemId = icaVw.getItemId();
        int distId = icaVw.getDistId();
        int itemDistMapId = 0;
        while (wrkItemDistD != null || iter1.hasNext()) {
          if (wrkItemDistD == null) wrkItemDistD = (ItemMappingData) iter1.next();
          int iId = wrkItemDistD.getItemId();
          if (iId < itemId) {
            wrkItemDistD = null;
            continue;
          }
          if (iId > itemId) {
            break;
          }
          int dId = wrkItemDistD.getBusEntityId();
          if (dId != distId) {
            wrkItemDistD = null;
            continue;
          }
          icaVw.setDistSkuNum(wrkItemDistD.getItemNum());
          icaVw.setDistSkuNumInp(wrkItemDistD.getItemNum());
          icaVw.setDistSPLFl(Utility.isTrue(wrkItemDistD.getStandardProductList()));
          icaVw.setDistSPLFlInp(Utility.isTrue(wrkItemDistD.
                                               getStandardProductList()));
          icaVw.setDistSkuPack(wrkItemDistD.getItemPack());
          icaVw.setDistSkuPackInp(wrkItemDistD.getItemPack());
          icaVw.setDistSkuUom(wrkItemDistD.getItemUom());
          icaVw.setDistSkuUomInp(wrkItemDistD.getItemUom());
          BigDecimal distConvMult = wrkItemDistD.getUomConvMultiplier();
          if (distConvMult != null) {
            distConvMult = distConvMult.setScale(2, BigDecimal.ROUND_HALF_UP);
            icaVw.setDistConversInp("" + wrkItemDistD.getUomConvMultiplier());
          } else {
            icaVw.setDistConversInp("");
          }
          icaVw.setDistConvers(distConvMult);
          itemDistMapId = wrkItemDistD.getItemMappingId();


          while (wrkItemGenManufD != null || iter2.hasNext()) {
            if (wrkItemGenManufD == null) wrkItemGenManufD = (ItemMappingData)
              iter2.next();
            int iId1 = wrkItemGenManufD.getItemId();
            if (iId1 < itemId) {
              wrkItemGenManufD = null;
              continue;
            }
            if (iId1 > itemId) {
              break;
            }
            int itemGenManufMapId = wrkItemGenManufD.getItemMappingId();
            boolean foundFl = false;
            for (Iterator iter3 = itemMapAssocDV.iterator(); iter3.hasNext(); ) {
              ItemMappingAssocData imaD = (ItemMappingAssocData) iter3.next();
              if (imaD.getItemMapping1Id() == itemDistMapId &&
                  imaD.getItemMapping2Id() == itemGenManufMapId) {
                int genManufId = wrkItemGenManufD.getBusEntityId();
                icaVw.setGenManufId(genManufId);
                genManufIdV.add(new Integer(genManufId));
                icaVw.setGenManufIdInp("" + wrkItemGenManufD.getBusEntityId());
                icaVw.setGenManufSkuNum(wrkItemGenManufD.getItemNum());
                icaVw.setGenManufSkuNumInp(wrkItemGenManufD.getItemNum());
                foundFl = true;
                break;
              }
            }
            if (foundFl) {
              break;
            } else {
              wrkItemGenManufD = null;
              continue;
            }
          }
          break;
        }
      }

      //Contract
      dbc = new DBCriteria();
      dbc.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
      dbc.addNotEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                        RefCodeNames.CONTRACT_STATUS_CD.DELETED);
      ContractDataVector contractDV = ContractDataAccess.select(con, dbc);
      if (contractDV.size() > 1) {
        String errorMess = "Multiple contracts for the catalog catalog id: " +
                           pCatalogId;
        throw new Exception(errorMess);
      }
      if (contractDV.size() == 1) {
        ContractData contractD = (ContractData) contractDV.get(0);
        dbc = new DBCriteria();
        int contractId = contractD.getContractId();
        dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contractId);
        dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
        ContractItemDataVector contractItemDV =
          ContractItemDataAccess.select(con, dbc);
        ContractItemData wrkContractItemD = null;
        // getting allowed decimal places for contract locale
        int decimalPlaces = getDecimalPlacesForContract( con, contractD);
        for (Iterator iter = itemCatAggrVwV.iterator(),
                             iter1 = contractItemDV.iterator(); iter.hasNext(); ) {
          ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
          int itemId = icaVw.getItemId();
          icaVw.setContractId(contractId);
          while (wrkContractItemD != null || iter1.hasNext()) {
            if (wrkContractItemD == null) wrkContractItemD = (ContractItemData)
              iter1.next();
            int iId = wrkContractItemD.getItemId();
            if (iId < itemId) {
              wrkContractItemD = null;
              continue;
            }
            if (iId > itemId) {
              break;
            }
            icaVw.setContractFl(true);
            icaVw.setContractFlInp(true);
            BigDecimal cost = wrkContractItemD.getDistCost();
            if (cost != null) {
              cost = cost.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
              icaVw.setCost(cost);
              icaVw.setCostInp(cost.toString());
            }

            BigDecimal price = wrkContractItemD.getAmount();
            if (price != null) {
              price = price.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
              icaVw.setPrice(price);
              icaVw.setPriceInp(price.toString());
            }

            BigDecimal baseCost = wrkContractItemD.getDistBaseCost();
            if (baseCost != null) {
              baseCost = baseCost.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
              icaVw.setBaseCost(baseCost);
              icaVw.setBaseCostInp(baseCost.toString());
            }
            icaVw.setServiceFeeCode(wrkContractItemD.getServiceFeeCode());
            icaVw.setServiceFeeCodeInp(wrkContractItemD.getServiceFeeCode());
            break;
          }
        }
      }

      // Order guide
      dbc = new DBCriteria();
      dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, orderGuideIdV);
      dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);
      OrderGuideStructureDataVector orderGuideStrDV =
        OrderGuideStructureDataAccess.select(con, dbc);

      OrderGuideStructureData wrkOGStructD = null;
      for (Iterator iter = itemCatAggrVwV.iterator(),
                           iter1 = orderGuideStrDV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int itemId = icaVw.getItemId();
        while (wrkOGStructD != null || iter1.hasNext()) {
          if (wrkOGStructD == null) wrkOGStructD = (OrderGuideStructureData)
            iter1.next();
          int iId = wrkOGStructD.getItemId();
          if (iId < itemId) {
            wrkOGStructD = null;
            continue;
          }
          if (iId > itemId) {
            break;
          }
          icaVw.setOrderGuideFl(true);
          icaVw.setOrderGuideFlInp(true);
          icaVw.setOrderGuideId(wrkOGStructD.getOrderGuideId());
          wrkOGStructD = null;
          break;
        }
      }

      //Dist and gen mfg names
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);
      BusEntityDataVector distBusEntDV = BusEntityDataAccess.select(con, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, genManufIdV);
      BusEntityDataVector gmBusEntDV = BusEntityDataAccess.select(con, dbc);
      for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int distId = icaVw.getDistId();
        if (distId > 0) {
          for (Iterator iter1 = distBusEntDV.iterator(); iter1.hasNext(); ) {
            BusEntityData beD = (BusEntityData) iter1.next();
            if (distId == beD.getBusEntityId()) {
              icaVw.setDistName(beD.getShortDesc());
              icaVw.setDistErpNum(beD.getErpNum());
              icaVw.setDistStatus(beD.getBusEntityStatusCd());
              break;
            }
          }
        }
        int gmId = icaVw.getGenManufId();
        if (gmId > 0) {
          for (Iterator iter1 = gmBusEntDV.iterator(); iter1.hasNext(); ) {
            BusEntityData beD = (BusEntityData) iter1.next();
            if (gmId == beD.getBusEntityId()) {
              icaVw.setGenManufName(beD.getShortDesc());
              break;
            }
          }
        }

      }

       multiproductLoop:
       for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
           ItemCatalogAggrView aVw = (ItemCatalogAggrView) iter.next();
           int itemIdFromAggregateView = aVw.getItemId();
           for (Iterator multiproductsIter = itemMAssocDV.iterator(); multiproductsIter.hasNext(); ) {
               CatalogStructureData multiAD = (CatalogStructureData) multiproductsIter.next();
               int mId = multiAD.getItemId();
               if (itemIdFromAggregateView == mId) {
                  aVw.setMultiproductId(multiAD.getItemGroupId());
                  for (Iterator multiproductIter = itemMultiproductDV.iterator(); multiproductIter.hasNext();) {
                      ItemData multiproductItemD = (ItemData) multiproductIter.next();
                      if (multiproductItemD.getItemId() == multiAD.getItemGroupId()) {
                          aVw.setMultiproductName(multiproductItemD.getShortDesc());
                          aVw.setMultiproductIdInp(""+multiAD.getItemGroupId());
                      }
                  }
               }
           }
       }

      return itemCatAggrVwV;
    } catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    } finally {
      try {
        con.close();
      } catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
  }

  /**
   * Gets aggregated item data
   * @param pStroreCatalogId store catalog id
   * @param pItemId item id
   * @param pDistIds vector of distributor ids
   * @param pCatalogIds vector of catalog ids
   * @param pAccountIds vector of account ids
   * @return a set of ItemCatalogAggrViewVector objects
   * @throws            RemoteException
   */
  public ItemCatalogAggrViewVector getItemCatalogMgrSet
    (int pStoreCatalogId, int pItemId, IdVector pDistIds,
     IdVector pCatalogIds, IdVector pAccountIds) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      ItemCatalogAggrViewVector itemCatAggrVwV = new ItemCatalogAggrViewVector();
      DBCriteria dbc;

      //Item
      ItemData itemD = ItemDataAccess.select(con, pItemId);
      String shortDesc = itemD.getShortDesc();
      String itemStatusCd = itemD.getItemStatusCd();

      //Item sku
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
      CatalogStructureDataVector storeCatStructDV =
        CatalogStructureDataAccess.select(con, dbc);
      if (storeCatStructDV.size() == 0) {
        String errorMess = "Item is not in store catalog. Item id: " + pItemId +
                           " Store catalog id: " + pStoreCatalogId;
        throw new Exception(errorMess);
      }

      if (storeCatStructDV.size() > 1) {
        String errorMess = "Duplication in store catalog. Item id: " + pItemId +
                           " Store catalog id: " + pStoreCatalogId;
        throw new Exception(errorMess);
      }
      CatalogStructureData storeCatStructD =
        (CatalogStructureData) storeCatStructDV.get(0);
      String skuNum = storeCatStructD.getCustomerSkuNum();

      //
      IdVector catalogIdV = pCatalogIds;
      logDebug(
        "CatalogInformationBean CCCCCCCCCCCCCCCCCCCCCCC pAccountIds: " +
        pAccountIds);
      if (pAccountIds != null && pAccountIds.size() > 0) {
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountIds);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                       RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        IdVector cIdV = CatalogAssocDataAccess.selectIdOnly(con,
          CatalogAssocDataAccess.CATALOG_ID, dbc);

        logDebug(
          "CatalogInformationBean CCCCCCCCCCCCCCCCCCCCCCC cIdV: " + cIdV);
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, cIdV);
        if (catalogIdV != null && catalogIdV.size() > 0) {
          dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIdV);
        }
        dbc.addNotEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                          RefCodeNames.CATALOG_STATUS_CD.INACTIVE);
        catalogIdV =
          CatalogDataAccess.selectIdOnly(con, CatalogDataAccess.CATALOG_ID, dbc);

      }

      //Get catalog data
      CatalogStructureDataVector catStructDV = null;
      IdVector distIdV = pDistIds;
      if (pDistIds != null && pDistIds.size() > 0) {
        dbc = new DBCriteria();
        if ((pCatalogIds != null && pCatalogIds.size() > 0) ||
            (pAccountIds != null && pAccountIds.size() > 0)) {
          dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogIdV);
        }
        dbc.addOneOf(CatalogStructureDataAccess.BUS_ENTITY_ID, distIdV);
        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
        dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);
        catStructDV = CatalogStructureDataAccess.select(con, dbc);
        catalogIdV = new IdVector();
        for (Iterator iter = catStructDV.iterator(); iter.hasNext(); ) {
          CatalogStructureData csD = (CatalogStructureData) iter.next();
          catalogIdV.add(new Integer(csD.getCatalogId()));
        }
      } else {
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogIdV);
        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
        dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);
        catStructDV = CatalogStructureDataAccess.select(con, dbc);
        distIdV = new IdVector();
        for (Iterator iter = catStructDV.iterator(); iter.hasNext(); ) {
          CatalogStructureData csD = (CatalogStructureData) iter.next();
          int distId = csD.getBusEntityId();
          if (distId > 0) {
            Integer distIdI = new Integer(distId);
            if (!distIdV.contains(distIdI)) {
              distIdV.add(distIdI);
            }
          }
        }
      }

      if (catalogIdV.size() == 0) {
        return itemCatAggrVwV;
      }

      LinkedList catalogIdsStrLL = new LinkedList();
      if (catalogIdV.size() < 1000) {
        catalogIdsStrLL.add(IdVector.toCommaString(catalogIdV));
      } else {
        int[] ids = new int[catalogIdV.size()];
        int index = 0;
        for (Iterator iter = catalogIdV.iterator(); iter.hasNext(); ) {
          Integer catIdI = (Integer) iter.next();
          ids[index++] = catIdI.intValue();
        }
        for (int ii = 0; ii < ids.length - 1; ii++) {
          boolean exitFl = true;
          for (int jj = 0; jj < ids.length - 1 - ii; jj++) {
            if (ids[jj] > ids[jj + 1]) {
              exitFl = false;
              int wrk = ids[jj];
              ids[jj] = ids[jj + 1];
              ids[jj] = wrk;
            }
          }
          if (exitFl)break;
        }
        String catalogIdsStr = null;
        for (int ii = 0; ii < ids.length; ii++) {
          if (ii % 1000 == 0) {
            if (ii > 0) {
              catalogIdsStrLL.add(catalogIdsStr);
            }
            catalogIdsStr = "" + ids[ii];
          } else {
            catalogIdsStr += "," + ids[ii];
          }
        }
        catalogIdsStrLL.add(catalogIdsStr);
      }
      LinkedList<IdVector> ogIdVLL = new LinkedList<IdVector>();
      for (Iterator iter = catalogIdsStrLL.iterator(); iter.hasNext(); ) {
        String catalogIdsStr = (String) iter.next();

        String centralSql =
          "select " +
          " c.catalog_id,  " +
          " c.short_desc catalog_name, " +
          " c.catalog_type_cd, " +
          " c.catalog_status_cd, " +
          " og.order_guide_id,  " +
          " og.short_desc og_name " +
          " from clw_catalog c, clw_order_guide og " +
          " where c.catalog_id  in (" + IdVector.toCommaString(catalogIdV) +
          ") " +
          " and og.catalog_id(+) = c.catalog_id " +
          " and og.order_guide_type_cd(+) = 'ORDER_GUIDE_TEMPLATE'  " +
          " order by catalog_id, order_guide_id";
        logDebug(
          "CatlogInformationBean IIIIIIIIIIIIIIIIIIIIIIIIIII centralSql: " +
          centralSql);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(centralSql);

        IdVector ogIdV = new IdVector();
        int prevCatalogId = 0;
        IdVector wrkOgIdV = new IdVector();

        while (rs.next()) {
          int catalogId = rs.getInt("catalog_id");
          String catalogName = rs.getString("catalog_name");
          String catalogType = rs.getString("catalog_type_cd");
          String catalogStatus = rs.getString("catalog_status_cd");
          int ogId = rs.getInt("order_guide_id");
          if(prevCatalogId != catalogId) {
              prevCatalogId = catalogId;
              if(wrkOgIdV.size()+ogIdV.size()>=1000) {
                  ogIdVLL.add(ogIdV);
                  ogIdV = wrkOgIdV;
                  wrkOgIdV = new IdVector();
              } else {
                  ogIdV.addAll(wrkOgIdV);
                  wrkOgIdV = new IdVector();
              }
          }
          wrkOgIdV.add(ogId);
          String ogName = rs.getString("og_name");
          ItemCatalogAggrView icaVw = ItemCatalogAggrView.createValue();
          itemCatAggrVwV.add(icaVw);
          icaVw.setItemId(pItemId);
          icaVw.setItemStatusCd(itemStatusCd);
          icaVw.setItemName(shortDesc);
          icaVw.setSkuNum(skuNum);
          icaVw.setCatalogId(catalogId);
          icaVw.setCatalogName(catalogName);
          icaVw.setCatalogStatus(catalogStatus);
          icaVw.setCatalogType(catalogType);
          icaVw.setOrderGuideId(ogId);
          icaVw.setOrderGuideName(ogName);
          icaVw.setCatalogFl(false);
          icaVw.setCatalogFlInp(false);
          icaVw.setContractFl(false);
          icaVw.setContractFlInp(false);
          icaVw.setOrderGuideFl(false);
          icaVw.setOrderGuideFlInp(false);
        }
        rs.close();
        stmt.close();
        if(wrkOgIdV.size()+ogIdV.size()>=1000) {
            ogIdVLL.add(ogIdV);
            ogIdV = wrkOgIdV;
        } else {
            ogIdV.addAll(wrkOgIdV);
        }
        ogIdVLL.add(ogIdV);
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.CATALOG_ID, catalogIdV);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItemId);
        if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(itemD.getItemTypeCd()))
        {
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                    RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);
        }
        else
        {
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                    RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
        }
            dbc.addOrderBy(ItemAssocDataAccess.CATALOG_ID);

      ItemAssocDataVector itemCategAssocDV = ItemAssocDataAccess.select(con,
        dbc);
      IdVector categIdV = new IdVector();
      int prevCategId = 0;
      for (Iterator iter = itemCategAssocDV.iterator(); iter.hasNext(); ) {
        ItemAssocData iaD = (ItemAssocData) iter.next();
        int categId = iaD.getItem2Id();
        if (categId != prevCategId) { //just to reduce vector size
          prevCategId = categId;
          categIdV.add(new Integer(categId));
        }
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, categIdV);
      ItemDataVector itemCategDV = ItemDataAccess.select(con, dbc);

      CatalogStructureData wrkCatStrD = null;
      ItemAssocData wrkItemCategAssocD = null;
      for (Iterator iter = itemCatAggrVwV.iterator(),
                           iter1 = catStructDV.iterator(),
                                   iter2 = itemCategAssocDV.iterator();
                                           iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int catalogId = icaVw.getCatalogId();
        //Catalog structure
        while (wrkCatStrD != null || iter1.hasNext()) {
          if (wrkCatStrD == null) wrkCatStrD = (CatalogStructureData) iter1.
                                               next();
          int cId = wrkCatStrD.getCatalogId();
          if (cId < catalogId) {
            wrkCatStrD = null;
            continue;
          }
          if (cId > catalogId) {
            break;
          }
          icaVw.setCatalogFl(true);
          icaVw.setCatalogFlInp(true);
          int distId = wrkCatStrD.getBusEntityId();
          if (distId > 0) {
            icaVw.setDistId(distId);
            icaVw.setDistIdInp("" + distId);
          }
          icaVw.setCatalogSkuNum(wrkCatStrD.getCustomerSkuNum());
          icaVw.setCatalogSkuNumInp(wrkCatStrD.getCustomerSkuNum());

          String taxExemptFlStr = wrkCatStrD.getTaxExempt();
          boolean taxExemptFl = false;
          if (taxExemptFlStr != null) {
            taxExemptFlStr = taxExemptFlStr.trim().toUpperCase();
            if (taxExemptFlStr.startsWith("T") || taxExemptFlStr.startsWith("Y") ||
                taxExemptFlStr.equals("1")) {
              taxExemptFl = true;
            }
          }
          icaVw.setTaxExemptFl(taxExemptFl);
          icaVw.setTaxExemptFlInp(taxExemptFl);

          String specialPermissionFlStr = wrkCatStrD.getSpecialPermission();
          boolean specialPermissionFl = Utility.isTrue(specialPermissionFlStr);
          icaVw.setSpecialPermissionFl(specialPermissionFl);
          icaVw.setSpecialPermissionFlInp(specialPermissionFl);

          icaVw.setCostCenterId(wrkCatStrD.getCostCenterId());
          icaVw.setCostCenterIdInp("" + wrkCatStrD.getCostCenterId());
          break;
        }

        while (wrkItemCategAssocD != null || iter2.hasNext()) {
          if (wrkItemCategAssocD == null) wrkItemCategAssocD = (ItemAssocData)
            iter2.next();
          int cId = wrkItemCategAssocD.getCatalogId();
          if (cId < catalogId) {
            wrkItemCategAssocD = null;
            continue;
          }
          if (cId > catalogId) {
            break;
          }
          int categId = wrkItemCategAssocD.getItem2Id();
          for (Iterator iter3 = itemCategDV.iterator(); iter3.hasNext(); ) {
            ItemData itemCategD = (ItemData) iter3.next();
            if (categId == itemCategD.getItemId()) {
              icaVw.setCategoryName(Utility.getCategoryFullName(itemCategD));
              icaVw.setCategoryId(itemCategD.getItemId());
              icaVw.setCategoryIdInp("" + itemCategD.getItemId());
              break;
            }
          }
          break;
        }
      }

      //Distributor info
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItemId);
      dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, distIdV);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
      ItemMappingDataVector itemDistDV =
        ItemMappingDataAccess.select(con, dbc);
      IdVector itemDistIdV = new IdVector();

      for (Iterator iter = itemDistDV.iterator(); iter.hasNext(); ) {
        ItemMappingData imD = (ItemMappingData) iter.next();
        itemDistIdV.add(new Integer(imD.getItemMappingId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID, itemDistIdV);
      dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,
                     RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);
      ItemMappingAssocDataVector itemMapAssocDV =
        ItemMappingAssocDataAccess.select(con, dbc);
      IdVector itemGenManufIdV = new IdVector();
      for (Iterator iter = itemMapAssocDV.iterator(); iter.hasNext(); ) {
        ItemMappingAssocData imaD = (ItemMappingAssocData) iter.next();
        itemGenManufIdV.add(new Integer(imaD.getItemMapping2Id()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, itemGenManufIdV);
      ItemMappingDataVector itemGenManufDV =
        ItemMappingDataAccess.select(con, dbc);

      IdVector genManufIdV = new IdVector();
      for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int distId = icaVw.getDistId();
        if (distId <= 0)continue;
        for (Iterator iter1 = itemDistDV.iterator(); iter1.hasNext(); ) {
          ItemMappingData idmD = (ItemMappingData) iter1.next();
          int dId = idmD.getBusEntityId();
          if (dId != distId) {
            continue;
          }
          icaVw.setDistSkuNum(idmD.getItemNum());
          icaVw.setDistSkuNumInp(idmD.getItemNum());
          icaVw.setDistSPLFl(Utility.isTrue(idmD.getStandardProductList()));
          icaVw.setDistSPLFlInp(Utility.isTrue(idmD.getStandardProductList()));
          icaVw.setDistSkuPack(idmD.getItemPack());
          icaVw.setDistSkuPackInp(idmD.getItemPack());
          icaVw.setDistSkuUom(idmD.getItemUom());
          icaVw.setDistSkuUomInp(idmD.getItemUom());
          BigDecimal distConvMult = idmD.getUomConvMultiplier();
          if (distConvMult != null) {
            distConvMult = distConvMult.setScale(2, BigDecimal.ROUND_HALF_UP);
            icaVw.setDistConversInp("" + idmD.getUomConvMultiplier());
          } else {
            icaVw.setDistConversInp("");
          }
          icaVw.setDistConvers(distConvMult);
          int itemDistMapId = idmD.getItemMappingId();
          mmm:
            for (Iterator iter2 = itemMapAssocDV.iterator(); iter2.hasNext(); ) {
            ItemMappingAssocData imaD = (ItemMappingAssocData) iter2.next();
            if (imaD.getItemMapping1Id() != itemDistMapId) {
              continue;
            }
            int itemGenManufMapId = imaD.getItemMapping2Id();
            for (Iterator iter3 = itemGenManufDV.iterator(); iter3.hasNext(); ) {
              ItemMappingData gmImD = (ItemMappingData) iter3.next();
              if (itemGenManufMapId != gmImD.getItemMappingId()) {
                continue;
              }
              int genManufId = gmImD.getBusEntityId();
              icaVw.setGenManufId(genManufId);
              genManufIdV.add(new Integer(genManufId));
              icaVw.setGenManufIdInp("" + genManufId);
              icaVw.setGenManufSkuNum(gmImD.getItemNum());
              icaVw.setGenManufSkuNumInp(gmImD.getItemNum());
              break mmm;
            }
          }
          break;
        }
      }

      //Contract
      String sql =
        "select catalog_id, c.contract_id, amount, dist_cost, dist_base_cost, "
        + pItemId + " item_id, contract_item_id, service_fee_code " +
        ", cr.DECIMALS " +
        " from clw_contract c, clw_contract_item ci " +
        ", clw_currency cr" +
        " where ci.item_id (+)= " + pItemId +
        "  and c.catalog_id in (" + IdVector.toCommaString(catalogIdV) + ")" +
        "  and c.contract_id = ci.contract_id (+)" +
        "  and c.contract_status_cd != '" +
        RefCodeNames.CONTRACT_STATUS_CD.DELETED + "' " +
        "  and NVL(c.LOCALE_CD, 'en_US') = cr.LOCALE " +
        "  order by catalog_id ";

      logDebug(
        "CatlogInformationBean IIIIIIIIIIIIIIIIIIIIIIIIIII sql: " + sql);
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      ItemCatalogAggrView wrkIcaVw = null;
      for (Iterator iter = itemCatAggrVwV.iterator(); rs.next(); ) {
        int catalogId = rs.getInt("catalog_id");
        int contractId = rs.getInt("contract_id");
        int contractItemId = rs.getInt("contract_item_id");
        int decimals = rs.getInt("DECIMALS");
        BigDecimal priceBD = rs.getBigDecimal("amount");
        if (priceBD != null) priceBD = priceBD.setScale(decimals,
          BigDecimal.ROUND_HALF_UP);
        BigDecimal costBD = rs.getBigDecimal("dist_cost");
        if (costBD != null) costBD = costBD.setScale(decimals,
          BigDecimal.ROUND_HALF_UP);
        BigDecimal baseCostBD = rs.getBigDecimal("dist_base_cost");
        if (baseCostBD != null) baseCostBD = baseCostBD.setScale(decimals,
          BigDecimal.ROUND_HALF_UP);
        String serviceFeeCode = rs.getString("service_fee_code");
        while (wrkIcaVw != null || iter.hasNext()) {
          if (wrkIcaVw == null) wrkIcaVw = (ItemCatalogAggrView) iter.next();
          int cId = wrkIcaVw.getCatalogId();
          if (cId < catalogId) {
            wrkIcaVw = null;
            continue;
          }
          if (cId > catalogId) {
            break;
          }
          wrkIcaVw.setContractId(contractId);
          if (contractItemId > 0) {
            wrkIcaVw.setContractFl(true);
            wrkIcaVw.setContractFlInp(true);
            wrkIcaVw.setCost(costBD);
            if (costBD != null) {
              wrkIcaVw.setCostInp(costBD.toString());
            } else {
              wrkIcaVw.setCostInp("");
            }
            wrkIcaVw.setPrice(priceBD);
            if (priceBD != null) {
              wrkIcaVw.setPriceInp(priceBD.toString());
            } else {
              wrkIcaVw.setPriceInp("");
            }
            wrkIcaVw.setBaseCost(baseCostBD);
            if (baseCostBD != null) {
              wrkIcaVw.setBaseCostInp(baseCostBD.toString());
            } else {
              wrkIcaVw.setBaseCostInp("");
            }
            wrkIcaVw.setServiceFeeCode(serviceFeeCode);
            wrkIcaVw.setServiceFeeCodeInp(serviceFeeCode);
          }

          wrkIcaVw = null;
          continue;
        }
      }
      rs.close();
      stmt.close();
      for(Iterator iterOg=ogIdVLL.iterator(); iterOg.hasNext();) {
          IdVector ogIdV = (IdVector) iterOg.next();

          //Order Guide
          sql =
            "select catalog_id, og.order_guide_id " +
            " from clw_order_guide og, clw_order_guide_structure ogs " +
            " where ogs.item_id = " + pItemId +
            "  and og.order_guide_id in (" + IdVector.toCommaString(ogIdV) + ")" +
            "  and og.order_guide_id = ogs.order_guide_id " +
            " order by catalog_id, og.order_guide_id ";

          logInfo("CatlogInformationBean IIIIIIIIIIIIIIIIIIIIIIIIIII sql: " + sql);
          stmt = con.createStatement();
          rs = stmt.executeQuery(sql);
          wrkIcaVw = null;
          for (Iterator iter = itemCatAggrVwV.iterator(); rs.next(); ) {
            int catalogId = rs.getInt("catalog_id");
            int orderGuideId = rs.getInt("order_guide_id");
            while (wrkIcaVw != null || iter.hasNext()) {
              if (wrkIcaVw == null) wrkIcaVw = (ItemCatalogAggrView) iter.next();
              int cId = wrkIcaVw.getCatalogId();
              if (cId < catalogId) {
                wrkIcaVw = null;
                continue;
              }
              if (cId > catalogId) {
                break;
              }
              int ogId = wrkIcaVw.getOrderGuideId();
              if (ogId < orderGuideId) {
                wrkIcaVw = null;
                continue;
              }
              if (ogId > orderGuideId) {
                break;
              }
              wrkIcaVw.setOrderGuideFl(true);
              wrkIcaVw.setOrderGuideFlInp(true);
              wrkIcaVw = null;
              break;
            }
          }
          rs.close();
          stmt.close();
      }

      //Dist and gen mfg names
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);
      BusEntityDataVector distBusEntDV = BusEntityDataAccess.select(con, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, genManufIdV);
      BusEntityDataVector gmBusEntDV = BusEntityDataAccess.select(con, dbc);
      for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();
        int distId = icaVw.getDistId();
        if (distId > 0) {
          for (Iterator iter1 = distBusEntDV.iterator(); iter1.hasNext(); ) {
            BusEntityData beD = (BusEntityData) iter1.next();
            if (distId == beD.getBusEntityId()) {
              icaVw.setDistName(beD.getShortDesc());
              icaVw.setDistErpNum(beD.getErpNum());
              icaVw.setDistStatus(beD.getBusEntityStatusCd());
              break;
            }
          }
        }
        int gmId = icaVw.getGenManufId();
        if (gmId > 0) {
          for (Iterator iter1 = gmBusEntDV.iterator(); iter1.hasNext(); ) {
            BusEntityData beD = (BusEntityData) iter1.next();
            if (gmId == beD.getBusEntityId()) {
              icaVw.setGenManufName(beD.getShortDesc());
              break;
            }
          }
        }

      }

      CatalogStructureDataVector itemMAssocDV = null;
      ItemDataVector itemMultiproductDV = null;
      dbc = new DBCriteria();
      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogIdV);
      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

      itemMAssocDV = CatalogStructureDataAccess.select(con, dbc);
      IdVector mpIdV = new IdVector();
      int prevMpId = 0;
      for (Iterator iter = itemMAssocDV.iterator(); iter.hasNext(); ) {
        CatalogStructureData iaD = (CatalogStructureData) iter.next();
        int multiproductId = iaD.getItemGroupId();
        if (multiproductId != prevMpId) { //just to reduce vector size
            prevMpId = multiproductId;
            mpIdV.add(new Integer(multiproductId));
        }
      }

      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, mpIdV);
      itemMultiproductDV = ItemDataAccess.select(con, dbc);

      multiproductLoop:
      for (Iterator iter = itemCatAggrVwV.iterator(); iter.hasNext(); ) {
          ItemCatalogAggrView aVw = (ItemCatalogAggrView) iter.next();
          int itemIdFromAggregateView = aVw.getItemId();
          int catalogIdFromAggregateView = aVw.getCatalogId();
          for (Iterator multiproductsIter = itemMAssocDV.iterator(); multiproductsIter.hasNext(); ) {
              CatalogStructureData multiAD = (CatalogStructureData) multiproductsIter.next();
              int mId = multiAD.getItemId();
              int cId = multiAD.getCatalogId();
              if (itemIdFromAggregateView == mId && catalogIdFromAggregateView == cId) {
                 aVw.setMultiproductId(multiAD.getItemGroupId());
                 for (Iterator multiproductIter = itemMultiproductDV.iterator(); multiproductIter.hasNext();) {
                     ItemData multiproductItemD = (ItemData) multiproductIter.next();
                     if (multiproductItemD.getItemId() == multiAD.getItemGroupId()) {
                         aVw.setMultiproductName(multiproductItemD.getShortDesc());
                         aVw.setMultiproductIdInp(""+multiAD.getItemGroupId());
                     }
                 }
              }
          }
      }

      return itemCatAggrVwV;

    } catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    } finally {
      try {
        con.close();
      } catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
  }

  public ItemDataVector getCatalogCategories
  (int pStoreCatalogId, IdVector pCatalogIds)
  throws RemoteException{
	  return getCatalogCategories(pStoreCatalogId, pCatalogIds, true);
  }
  
  /**
   * Gets aggregated item data
   * @param pStroreCatalogId store catalog id
   * @param pCatalogIds vector of catalog ids
   * @return a subset of store catalog categories found in the catalogs
   * @throws            RemoteException
   */
  public ItemDataVector getCatalogCategories
    (int pStoreCatalogId, IdVector pCatalogIds, boolean allowMixedCategoryAndItemUnderSameParent) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, pCatalogIds);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
      if (!allowMixedCategoryAndItemUnderSameParent){
    	  // exclude the non-lowest level category (parent of a category)
    	  DBCriteria dbc2 = new DBCriteria();          
    	  dbc2.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);     
    	  dbc2.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
          String selParenCategory = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID, dbc2);
          dbc.addNotOneOf(CatalogStructureDataAccess.ITEM_ID, selParenCategory);
      }
            
      IdVector categIdV = CatalogStructureDataAccess.selectIdOnly(con,
        CatalogStructureDataAccess.ITEM_ID, dbc);
      dbc = new DBCriteria();
      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, categIdV);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
      IdVector storeCategIdV = CatalogStructureDataAccess.selectIdOnly(con,
        CatalogStructureDataAccess.ITEM_ID, dbc);
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, storeCategIdV);
      dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

      ItemDataVector iDV = ItemDataAccess.select(con, dbc);
      return iDV;
    } catch (Exception exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException(exc.getMessage());
    } finally {
      try {
        con.close();
      } catch (SQLException exc) {
        logError(exc.getMessage());
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
      }
    }
  }

  /**
   * Get all the CostCenters for a given account catalog
   * @param pCatalogId the Id of the CostCenter's Catalog.
   * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
   * @return a <code>CostCenterDataVector</code> with all CostCenters.
   * @exception RemoteException if an error occurs
   */
  public CostCenterDataVector getAllCostCenters(int pCatalogId, int pOrder) throws
    RemoteException {

    CostCenterDataVector costCenterVec = new CostCenterDataVector();
    Connection conn = null;

    try {
      conn = getConnection();

      DBCriteria crit1 = new DBCriteria();
      crit1.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID, pCatalogId);
      crit1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                       RefCodeNames.COST_CENTER_ASSOC_CD.
                       COST_CENTER_ACCOUNT_CATALOG);
      IdVector costCenterIdV = CostCenterAssocDataAccess.selectIdOnly(conn,
        CostCenterAssocDataAccess.COST_CENTER_ID, crit1);

      DBCriteria crit = new DBCriteria();
      crit.addOneOf(CostCenterDataAccess.COST_CENTER_ID, costCenterIdV);

      switch (pOrder) {

      case Account.ORDER_BY_ID:
        crit.addOrderBy(CostCenterDataAccess.COST_CENTER_ID, true);

        break;

      case Account.ORDER_BY_NAME:
        crit.addOrderBy(CostCenterDataAccess.SHORT_DESC, true);

        break;

      default:
        throw new RemoteException("getAllCostCenters: Bad order specification");
      }

      costCenterVec = CostCenterDataAccess.select(conn, crit);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException("getAllCostCenters: " +
                                e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return costCenterVec;
  }

  /**
     * Get all the CostCenterAssoces for a given account catalog
     *
     * @param pCatalogId
     *            the Id of the CostCenter's Catalog.
     * @return a <code>CostCenterAssocDataVector</code> with all
     *         CostCenterAssoces.
     * @exception RemoteException
     *                if an error occurs
     */
    public CostCenterAssocDataVector getAllCostCenterAssoces(int pCatalogId)
            throws RemoteException {
        CostCenterAssocDataVector costCenterAssocDV = new CostCenterAssocDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID, pCatalogId);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD, RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            crit.addOrderBy(CostCenterAssocDataAccess.COST_CENTER_ID);
            costCenterAssocDV = CostCenterAssocDataAccess.select(conn, crit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getAllCostCenterAssoces: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return costCenterAssocDV;
    }

    /**
     * Update CostCenterAssoces
     *
     * @param pCostCenterAssocDV
     *            CostCenterAssoces for update.
     * @exception RemoteException
     *                if an error occurs
     */
    public void updateCostCenterAssoces(
            CostCenterAssocDataVector pCostCenterAssocDV)
            throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            for (int i = 0; pCostCenterAssocDV != null
                    && i < pCostCenterAssocDV.size(); i++) {
                CostCenterAssocData costCenterAssocD = (CostCenterAssocData) pCostCenterAssocDV.get(i);
                CostCenterAssocDataAccess.update(conn, costCenterAssocD);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("updateCostCenterAssoces: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


  /**
   * Get all the CategoryName-CostCenterId pair for subcatalogs of a given account catalog
   * @param pCatalogId the Id of the CostCenter's Catalog.
   * @return a set of CategoryToCostCenterView objects
   * @exception RemoteException if an error occurs
   */
  public CategoryToCostCenterViewVector getAllCategoryToCostCenters(int
    pCatalogId) throws RemoteException {

    CategoryToCostCenterViewVector ccv =
      new CategoryToCostCenterViewVector();

    Connection conn = null;
    try {

      conn = getConnection();
      IdVector catalogIdV = getSubcatalogIdsForAccountCatalog(conn, pCatalogId);
      List catalogIdVL = splitIdVector(catalogIdV);
      for (Iterator iter = catalogIdVL.iterator(); iter.hasNext(); ) {
        IdVector cIdV = (IdVector) iter.next();

        String sql2 = "select distinct trim(i.short_desc), " +
                      " cs.cost_center_id from  " +
                      " clw_catalog_structure cs, clw_catalog cat , clw_item i " +
                      " where cs.catalog_id in ( " +
                      IdVector.toCommaString(cIdV) + " ) " +
                      " and cs.catalog_structure_cd = 'CATALOG_CATEGORY' " +
                      " and cs.item_id (+) = i.item_id" +
                      " and cat.catalog_status_cd NOT IN (" +
                      Utility.toCommaSting(IGNORED_CATALOG_STATUS_CDS, '\'') +
                      ")" +
                      " and cat.catalog_id = cs.catalog_id and cat.catalog_type_cd in (" +
                      " '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'," +
                      " '" + RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING +
                      "' )" +
                      " order by 1 ";

        Statement stmt2 = conn.createStatement();
        logDebug("getAllCategoryToCostCenters sql2=" + sql2);
        ResultSet rs2 = stmt2.executeQuery(sql2);

        while (rs2.next()) {
          CategoryToCostCenterView catToCostCenter =
            new CategoryToCostCenterView
            (rs2.getString(1), rs2.getInt(2));
          ccv.add(catToCostCenter);
        }

        rs2.close();
        stmt2.close();

      }

    } catch (Exception e) {
      logError("getAllCategoryToCostCenters, error: " + e);
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return ccv;
  }
  /**
   * Get all the CategoryName-CostCenterId pair for subcatalogs of a given account catalog
   * @param pCatalogId the Id of the CostCenter's Catalog.
   * @return a set of CategoryToCostCenterView objects
   * @exception RemoteException if an error occurs
   */
  public CategoryToCostCenterViewVector getCatalogCategoryToCostCenters(int pCatalogId) 
  throws RemoteException {

    CategoryToCostCenterViewVector ccv =  new CategoryToCostCenterViewVector();
    Connection conn = null;
    try {

      conn = getConnection();

        String sql2 = "select distinct trim(i.short_desc), " +
                      " cs.cost_center_id from  " +
                      " clw_catalog_structure cs, clw_item i " +
                      " where cs.catalog_id = " + pCatalogId +
                      " and cs.catalog_structure_cd = 'CATALOG_CATEGORY' " +
                      " and cs.item_id (+) = i.item_id" +
                      " order by 1 ";

        Statement stmt2 = conn.createStatement();
        logInfo("getCatalogCategoryToCostCenters sql2=" + sql2);
        ResultSet rs2 = stmt2.executeQuery(sql2);

        while (rs2.next()) {
          CategoryToCostCenterView catToCostCenter =
            new CategoryToCostCenterView
            (rs2.getString(1), rs2.getInt(2));
          ccv.add(catToCostCenter);
        }

        rs2.close();
        stmt2.close();

    } catch (Exception e) {
      logError("getCatalogCategoryToCostCenters, error: " + e);
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return ccv;
  }


  
  
  private List splitIdVector(IdVector pIdVector) {
    LinkedList idVLL = new LinkedList();
    int size = pIdVector.size();
    if (size > 0) {
      if (size < 1000) {
        idVLL.add(pIdVector);
      } else {
        IdVector idV = new IdVector();
        int ind = 0;
        for (Iterator iter = pIdVector.iterator(); iter.hasNext(); ) {
          if (ind++ >= 998) {
            idV = new IdVector();
            idVLL.add(idV);
            ind = 0;
          }
          Integer idI = (Integer) iter.next();
          idV.add(idI);
        }
      }
    }
    return idVLL;
  }

  //
  private IdVector getSubcatalogIdsForAccountCatalog(Connection con,
    int pCatalogId) throws Exception {
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
    String sql = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
    
    dbc = new DBCriteria();
    dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, sql);
    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
    dbc.addJoinCondition(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
    dbc.addJoinTableNotOneOf(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_STATUS_CD, Utility.toCommaSting(IGNORED_CATALOG_STATUS_CDS, '\''));
    IdVector catalogIdV = JoinDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, 
    		CatalogAssocDataAccess.CATALOG_ID, dbc, 0);
    return catalogIdV;
  }


  /**
   * Get all the CatalogId-ItemId-SkuNum-CostCenterId sets
   * for subcatalogs of a given account catalog
   * @param pCatalogId the Id of the CostCenter's Catalog.
   * @return a set of UniversalDAO.dbrow objects
   * @exception RemoteException if an error occurs
   */
  public ArrayList getAllItemToCostCenters(int pCatalogId) throws
    RemoteException {
    Connection conn = null;
    ArrayList itoccv = new ArrayList();
    try {
      conn = getConnection();
      IdVector catalogIdV = getSubcatalogIdsForAccountCatalog(conn, pCatalogId);
      List catalogIdVL = splitIdVector(catalogIdV);
      for (Iterator iter = catalogIdVL.iterator(); iter.hasNext(); ) {
        IdVector cIdV = (IdVector) iter.next();

        String sql2 = " SELECT cs.catalog_id,  " +
                      " ( select sku_num from clw_item i " +
                      "    where cs.item_id = i.item_id) as sku_num,  " +
                      " ( select trim(i.short_desc) from clw_item i " +
                      "    where cs.item_id = i.item_id) as item_desc,  " +
                      " cs.cost_center_id, 'none' AS cost_center_name " +
                      " from    clw_catalog_structure cs, clw_catalog cat  " +
                      " where cs.catalog_id in ( " +
                      IdVector.toCommaString(cIdV) + " )  " +
                      " and cat.catalog_id = cs.catalog_id and cat.catalog_type_cd in (" +
                      " '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'," +
                      " '" + RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING +
                      "' )" +
                      " and cs.catalog_structure_cd = 'CATALOG_PRODUCT' " +
                      " and cat.catalog_status_cd NOT IN (" +
                      Utility.toCommaSting(IGNORED_CATALOG_STATUS_CDS, '\'') +
                      ")" +
                      " AND (cost_center_id = 0 or cost_center_id is null) "
                      + " order by 1,2 ";

        ArrayList itoccv1 = UniversalDAO.getData(conn, sql2);
        itoccv.addAll(itoccv1);
      }
    } catch (Exception e) {
      logError("getAllItemToCostCenters, error: " + e);
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
    return itoccv;
  }

  public ArrayList getCatalogItemToCostCenters(int pCatalogId) throws
    RemoteException {
    Connection conn = null;
    ArrayList itoccv = new ArrayList();
    try {
      conn = getConnection();
        String sql2 = " SELECT cs.catalog_id,  " +
                      " ( select sku_num from clw_item i " +
                      "    where cs.item_id = i.item_id) as sku_num,  " +
                      " ( select trim(i.short_desc) from clw_item i " +
                      "    where cs.item_id = i.item_id) as item_desc,  " +
                      " cs.cost_center_id, 'none' AS cost_center_name " +
                      " from    clw_catalog_structure cs  " +
                      " where cs.catalog_id = " + pCatalogId +
                      " and cs.catalog_structure_cd = 'CATALOG_PRODUCT' " +
                      " AND (cost_center_id = 0 or cost_center_id is null) "
                      + " order by 1,2 ";
logInfo("getCatalogItemToCostCenters sql2: "+sql2);
        ArrayList itoccv1 = UniversalDAO.getData(conn, sql2);
        itoccv.addAll(itoccv1);
      
    } catch (Exception e) {
      logError("getAllItemToCostCenters, error: " + e);
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
    return itoccv;
  }
  
  /**
   *Gets all the fiscal calenders for the accounts sharing the account catalog
   * @param pCatalogId - the catalog id.
   * @exception RemoteException if an error occurs
   */
  public CatalogFiscalPeriodViewVector getFiscalInfo(int pCatalogId) throws
	RemoteException {
		Connection conn = null;
		try {
			conn = getConnection();
			DBCriteria dbc = new DBCriteria();
			dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
			dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
					RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
			String accountReq =
				CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.
						BUS_ENTITY_ID, dbc);

			dbc = new DBCriteria();
			dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, accountReq);
			dbc.addNotEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
					RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
			accountReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.
					BUS_ENTITY_ID, dbc);
			dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
			BusEntityDataVector beDV =
				BusEntityDataAccess.select(conn, dbc);			

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date today = new Date();
			today = sdf.parse(sdf.format(today));
			Map <Integer, BusEntityData> accountMap = new HashMap<Integer, BusEntityData>();
			for (Iterator iter = beDV.iterator(); iter.hasNext(); ) {				
				BusEntityData beD = (BusEntityData) iter.next();
				accountMap.put(beD.getBusEntityId(), beD);
			}

			// this query will only include last fiscal calender for each account with query OVER PARTITION BY
			String sql = "SELECT A.FISCAL_CALENDER_ID,BUS_ENTITY_ID,PERIOD_CD,EFF_DATE,FISCAL_YEAR, LISTAGG(B.mmdd, ' ') WITHIN GROUP (ORDER BY B.period) AS mmdd, count(*) period_cnt FROM ( \n" +
					"SELECT A.FISCAL_CALENDER_ID,BUS_ENTITY_ID,PERIOD_CD,EFF_DATE,FISCAL_YEAR, MAX(EFF_DATE) OVER (partition by BUS_ENTITY_ID) as MaxEffDate \n" +
					"FROM CLW_FISCAL_CALENDER A \n" +
					"WHERE BUS_ENTITY_ID IN ( \n" +
					"  SELECT BUS_ENTITY_ID \n" +
					"    FROM CLW_BUS_ENTITY WHERE BUS_ENTITY_ID IN ( \n" +
					"      SELECT DISTINCT BUS_ENTITY_ID FROM CLW_CATALOG_ASSOC \n" +
					"      WHERE CATALOG_ID = " + pCatalogId + " \n" +
					"      AND CATALOG_ASSOC_CD = 'CATALOG_ACCOUNT' \n" +
					"    ) \n" +
					"    AND BUS_ENTITY_STATUS_CD <> 'INACTIVE' \n" +
					") \n" +
					"AND EFF_DATE <= SYSDATE ) A, CLW_FISCAL_CALENDER_DETAIL B \n" +
					"WHERE EFF_DATE = MaxEffDate \n" +
					"AND A.FISCAL_CALENDER_ID = B.FISCAL_CALENDER_ID \n" +
					"GROUP BY A.FISCAL_CALENDER_ID,BUS_ENTITY_ID,PERIOD_CD,EFF_DATE,FISCAL_YEAR";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
												
			HashMap calendarHM = new HashMap();
			CatalogFiscalPeriodViewVector catalogFiscalPeriodVwV = new CatalogFiscalPeriodViewVector();
			while (rs.next()){
				Date effDate = rs.getDate("EFF_DATE");
				String periodCd = rs.getString("PERIOD_CD");
				int fiscalYear = rs.getInt("FISCAL_YEAR");
				String mmdds = rs.getString("mmdd");
				int periodCnt = rs.getInt("period_cnt");
				
				String effDateS = (effDate == null) ? "" : sdf.format(effDate);
				String keyStr = effDateS + fiscalYear + periodCd + mmdds;
				
				BusEntityDataVector calendarBeDV = (BusEntityDataVector) calendarHM.get(keyStr);
				if (calendarBeDV == null) {
					FiscalCalenderData fcD = FiscalCalenderData.createValue();
					fcD.setEffDate(effDate);
					fcD.setPeriodCd(periodCd);
					fcD.setFiscalYear(fiscalYear);
					
			        FiscalCalenderDetailDataVector fcDetails = new FiscalCalenderDetailDataVector();
			        String[] mmddList = mmdds.split(" ");
			        for (int i = 0; i < periodCnt; i++){
			        	FiscalCalenderDetailData fcdD = FiscalCalenderDetailData.createValue();
			        	fcDetails.add(fcdD);
			        	fcdD.setPeriod(i+1);
			        	if (i < mmddList.length)
			        		fcdD.setMmdd(mmddList[i]);
			        }
			        
					calendarBeDV = new BusEntityDataVector();
					calendarHM.put(keyStr, calendarBeDV);
					CatalogFiscalPeriodView cfpVw = CatalogFiscalPeriodView.createValue();
					catalogFiscalPeriodVwV.add(cfpVw);
					cfpVw.setAccounts(calendarBeDV);
					cfpVw.setFiscalCalenderView(new FiscalCalenderView(fcD,fcDetails));
					cfpVw.setCatalogId(pCatalogId);
				}
				calendarBeDV.add(accountMap.get(rs.getInt("BUS_ENTITY_ID")));
			}

			return catalogFiscalPeriodVwV;
		} catch (Exception e) {
			logError("getAllItemToCostCenters, error: " + e);
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}

  private IdVector sortIdVector(IdVector pIdVector) {
    if (pIdVector == null || pIdVector.size() <= 1) {
      return pIdVector;
    }
    Integer[] array = (Integer[]) pIdVector.toArray(new Integer[0]);
    for (int ii = 0; ii < array.length - 1; ii++) {
      boolean exitFl = true;
      for (int jj = 0; jj < array.length - ii - 1; jj++) {
        Integer i1 = array[jj];
        Integer i2 = array[jj + 1];
        if (i1.intValue() > i2.intValue()) {
          array[jj] = i2;
          array[jj + 1] = i1;
          exitFl = false;
        }
      }
      if (exitFl) {
        break;
      }
    }
    IdVector retIdVector = new IdVector();
    for (int ii = 0; ii < array.length; ii++) {
      retIdVector.add(array[ii]);
    }
    return retIdVector;
  }

  /**
   *Gets catalogs for the user
   * @param pUserId - the user id.
   * @param pStore Id - the store id
   * @param pFilter - catalog name pattern or catalog id
   * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
   * @exception RemoteException if an error occurs
   */
  public CatalogDataVector getUserCatalogs(int pUserId, int pStoreId,
                                           String pFilter, String pFilterType) throws
    RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
      dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                     RefCodeNames.USER_ASSOC_CD.SITE);
      String siteUserReq =
        UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.
                                               BUS_ENTITY_ID, dbc);

      dbc = new DBCriteria();
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                     RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
      IdVector acctIdV =
        BusEntityAssocDataAccess.selectIdOnly(conn,
                                              BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                                              dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, acctIdV);
      dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteUserReq);
      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                     RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
      String siteUserStoreReq =
        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.
        BUS_ENTITY1_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, siteUserStoreReq);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

      String catalogSiteReq =
        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.
                                                  CATALOG_ID, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogSiteReq);
      if (Utility.isSet(pFilter)) {
        pFilter = pFilter.trim();
        if ("id".equalsIgnoreCase(pFilterType)) {
          int catalogId = 0;
          try {
            catalogId = Integer.parseInt(pFilter);
          } catch (Exception exc) {}
          dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, catalogId);
        } else if ("nameContains".equalsIgnoreCase(pFilterType)) {
          dbc.addContainsIgnoreCase(CatalogDataAccess.SHORT_DESC, pFilter);
        } else { //nameBegins - default
          dbc.addBeginsWithIgnoreCase(CatalogDataAccess.SHORT_DESC, pFilter);
        }
      }
      logDebug(
        "CatalogInformationBean CCCCCCCCCCCCCCCCCCCCCCCCCC sql: " +
        (CatalogDataAccess.getSqlSelectIdOnly("*", dbc)));
      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      CatalogDataVector userCatalogs = CatalogDataAccess.select(conn, dbc);
      return userCatalogs;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
  }

    /**
     * Gets catalogs for the user
     *
     * @param pUserId     - the user id.
     * @param pAccountIds - the store id
     * @param pFilter     - catalog name pattern or catalog id
     * @param pFilterType - can be: "id", "nameContains" or "nameBegins" (defalut)
     * @return user catalogs
     * @throws RemoteException if an error occurs
     */
    public CatalogDataVector getUserCatalogs(int pUserId,
                                             IdVector pAccountIds,
                                             String pFilter,
                                             String pFilterType) throws RemoteException {
        Connection conn = null;
        CatalogDataVector userCatalogs = new CatalogDataVector();

        try {

            if (pAccountIds != null && !pAccountIds.isEmpty() && pUserId > 0) {

                conn = getConnection();

                DBCriteria dbc = new DBCriteria();

                dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
                dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);

                String siteUserReq = UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);

                logDebug("getUserCatalogs() => siteUserReq: " + siteUserReq);

                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountIds);
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteUserReq);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                String siteUserStoreReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

                logDebug("getUserCatalogs() => siteUserStoreReq: " + siteUserStoreReq);

                dbc = new DBCriteria();
                dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, siteUserStoreReq);
                dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

                String catalogSiteReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

                logDebug("getUserCatalogs() => catalogSiteReq: " + catalogSiteReq);

                dbc = new DBCriteria();
                dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogSiteReq);

                if (Utility.isSet(pFilter)) {
                    pFilter = pFilter.trim();
                    if ("id".equalsIgnoreCase(pFilterType)) {
                        dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, Integer.parseInt(pFilter));
                    } else if ("nameContains".equalsIgnoreCase(pFilterType)) {
                        dbc.addContainsIgnoreCase(CatalogDataAccess.SHORT_DESC, pFilter);
                    } else { //nameBegins - default
                        dbc.addBeginsWithIgnoreCase(CatalogDataAccess.SHORT_DESC, pFilter);
                    }
                }

                logDebug("getUserCatalogs() => sql: " + (CatalogDataAccess.getSqlSelectIdOnly("*", dbc)));

                dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);

                userCatalogs = CatalogDataAccess.select(conn, dbc);
            }

            return userCatalogs;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }

    }

    /**
   *Gets catalogs for the user
   * @param pStore Id the store id
   * @param pFilter catalog name pattern or catalog id
   * @param pFilterType can be: "id", "nameContains"(default) or "nameBegins"
   * @param pCostCenterID cost center id (if greater than 0)
   * @param pGetInactiveFl filters out inactive catalogs if false
   * @exception RemoteException if an error occurs
   */
  public CatalogDataVector getAccountCatalogs(int pStoreId,
                                              String pFilter,
                                              String pFilterType,
                                              int pCostCenterId,
                                              boolean pGetInactiveFl) throws
    RemoteException {
    Connection conn = null;
    DBCriteria dbc;
    try {
      conn = getConnection();
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
      String catalogAcctReq =
        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);
      dbc = new DBCriteria();
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAcctReq);

      if (pCostCenterId > 0) {
        DBCriteria dbc1 = new DBCriteria();
        dbc1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID, pCostCenterId);
        dbc1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                        RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
        IdVector catalogCostCenterIdV =
          CostCenterAssocDataAccess.selectIdOnly(conn,
                                                 CostCenterAssocDataAccess.
                                                 CATALOG_ID, dbc1);
        dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, catalogCostCenterIdV);
      }

      if (Utility.isSet(pFilter)) {
        pFilter = pFilter.trim();
        if ("id".equalsIgnoreCase(pFilterType)) {
          int catalogId = 0;
          try {
            catalogId = Integer.parseInt(pFilter);
          } catch (Exception exc) {}
          dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, catalogId);
        } else if ("nameContains".equalsIgnoreCase(pFilterType)) {
          dbc.addContainsIgnoreCase(CatalogDataAccess.SHORT_DESC, pFilter);
        } else { //nameBegins - default
          dbc.addBeginsWithIgnoreCase(CatalogDataAccess.SHORT_DESC, pFilter);
        }
      }
      if (!pGetInactiveFl) {
        dbc.addNotEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                          RefCodeNames.CATALOG_STATUS_CD.INACTIVE);
      }

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                     RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
      logInfo("CatalogInformationBean CCCCCCCCCCCCCCCCCCCCCCCCCC sql: " +
                         (CatalogDataAccess.getSqlSelectIdOnly("*", dbc)));
      dbc.addOrderBy(CatalogDataAccess.SHORT_DESC);
      CatalogDataVector catalogs = CatalogDataAccess.select(conn, dbc);
      return catalogs;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
  }

    /**
     * Gets all service for store catalog
     *
     * @param pStoreCatalogId store catalog id
     * @throws RemoteException if an error occurs
     * @return  ItemDataVector
     */
    public ItemDataVector getStoreServices(int pStoreCatalogId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);
            IdVector catIdV = CatalogStructureDataAccess.selectIdOnly(con, CatalogStructureDataAccess.ITEM_ID, dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.SERVICE);
            dbc.addOneOf(ItemDataAccess.ITEM_ID, catIdV);
            dbc.addOrderBy(ItemDataAccess.SHORT_DESC);
            logDebug( "[CatalogInformaitonBean::getStoreServices] sql: " +
                                                    ItemDataAccess.getSqlSelectIdOnly("*", dbc));
            return  ItemDataAccess.select(con, dbc);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    /**
     * Gets  service detail info  by criteria
     *
     * @param  criteria  ServiceSearchCriteria
     * @throws RemoteException if an error occurs
     * @return ServiceViewVector
     */
    public ServiceViewVector getServicesViewVector(ServiceSearchCriteria criteria) throws RemoteException
    {
        Connection con = null;
        try {
            con = getConnection();
            ServiceDAO serviceDAO = mCacheManager.getServiceDAO();
            ServiceViewVector serviceVV = serviceDAO.getServicesViewVector(con,criteria);
            return serviceVV;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }
    /**
     * Gets  service ids  for the catalog site
     *
     * @throws RemoteException if an error occurs
     * @return ServiceViewVector
     */
    public IdVector getServicesIdsBySiteCatalog(int pSiteId,int pCatalogId,boolean onlyForActiveCatalogs) throws RemoteException, DataNotFoundException {
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria catalogDBCriteria=new DBCriteria();
            catalogDBCriteria.addEqualTo(CatalogDataAccess.CATALOG_ID,pCatalogId);
            if(onlyForActiveCatalogs)
                catalogDBCriteria.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

            IdVector catIds=  CatalogDataAccess.selectIdOnly(con,catalogDBCriteria);
            if(catIds==null ||(catIds!=null&&catIds.size()==0))
                throw new DataNotFoundException("Catalog does not  exist");
            if(catIds.size()>1) throw new Exception("Multiple catalog.Catalog id : "+pCatalogId);

            ServiceDAO serviceDAO = mCacheManager.getServiceDAO();
            IdVector serviceIds = serviceDAO.getServicesIdsBySiteCatalog(con,pSiteId,pCatalogId);
            return serviceIds;
        }
        catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }
   /**
     * Gets  service data by id
     *
     * @param  itemId  service indentifier
     * @throws RemoteException if an error occurs
     * @return ServiceData
     */
    public ServiceData getServiceData(int itemId) throws RemoteException
    {
         Connection con = null;
        try {
            con = getConnection();
            ServiceDAO serviceDAO = mCacheManager.getServiceDAO();
            ServiceData service = serviceDAO.getServiceData(con,itemId);
            return service;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    /**
     * Gets  service data by id
     *
     * @param itemId service indentifier
     * @param catalogId indentifier
     * @return ServiceData
     * @throws RemoteException if an error occurs
     */
    public ServiceData getServiceData(int itemId, int catalogId) throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            ServiceDAO serviceDAO = mCacheManager.getServiceDAO();
            ServiceData service = serviceDAO.getServiceData(con, itemId, catalogId);
            return service;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }

    public ProductDataVector getCategoryChildProducts(int pCatalogId, int pCategoryId) throws RemoteException {
    	return getCategoryChildProducts(pCatalogId, pCategoryId, 0);
    }

    public ProductDataVector getCategoryChildProducts(int pCatalogId, int pCategoryId, int siteId) throws RemoteException {

        ProductDataVector products = new ProductDataVector();

        try {
            products = getCatalogChildProducts(pCatalogId, pCategoryId, siteId);
        } catch (DataNotFoundException e) {
            logInfo(e.getMessage());
        }

        CatalogCategoryDataVector childCatalogCats = getChildCategories(pCatalogId,pCategoryId);
        if (!childCatalogCats.isEmpty()) {
            Iterator it = childCatalogCats.iterator();
            while (it.hasNext()) {
                products.addAll(getCategoryChildProducts(pCatalogId, ((CatalogCategoryData) it.next()).getCatalogCategoryId(),siteId));
            }
        }
        return products;
    }

    /**
     * Gets catalog menu
     *
     * @param pCatalogId         the catalog identifier
     * @param pFilterCategoryIds filter
     * @return MenuItemView menu data
     * @throws RemoteException Required by EJB 1.0
     */
    public MenuItemView getCatalogMenu(int pCatalogId, IdVector pFilterCategoryIds, String pPatternLink) throws RemoteException {

    	logInfo("Retrieving catalog menu...");
        MenuItemView root = new MenuItemView();
        root.setKey(MenuItemView.ATTR.ROOT);
        root.setName(MenuItemView.ATTR.ROOT);
        root.setDisplayStatus(MenuItemView.DISPLAY_STATUS.OPEN);
        long startTime = System.currentTimeMillis();
        Map<CatalogCategoryData, CatalogCategoryDataVector> catalogMap = getCatalogAsMap(pCatalogId, pFilterCategoryIds);
        createCatalogMenu(root, pPatternLink, catalogMap);
        long endTime = System.currentTimeMillis();
        logInfo("Catalog menu retrieval time: " + (endTime - startTime)/1000);
        return root;
    }
    
    /**
     * Method to retrieve a catalog as a map
     * @param catalogId
     * @param filterCategoryIds
     * @return
     */
    private Map<CatalogCategoryData, CatalogCategoryDataVector> getCatalogAsMap(int catalogId, 
    		IdVector filterCategoryIds){
    	
    	Map<CatalogCategoryData, CatalogCategoryDataVector> returnValue = 
    		new HashMap<CatalogCategoryData, CatalogCategoryDataVector>();
    	
        Connection conn = null;
    	try {
    		CatalogCategoryDataVector topCategories = getTopCatalogCategories(catalogId);
    		Utility.filter(topCategories, filterCategoryIds);
    		Iterator<CatalogCategoryData> categoryIterator = topCategories.iterator();
    		while (categoryIterator.hasNext()) {
    			returnValue.put(categoryIterator.next(), new CatalogCategoryDataVector());
    		}
        	conn = getConnection();
        	getChildCategories(conn, catalogId, topCategories, filterCategoryIds, returnValue);
        	CatalogCategoryDataVector allCategories = new CatalogCategoryDataVector();
        	allCategories.addAll(returnValue.keySet());
        	assignMajorCategory(conn, allCategories);
        	populateItemInformation(conn, allCategories);
    	}
    	catch (Exception e) {
    		processException(e);
    	}
        finally {
        	try{
            	closeConnection(conn);
            }
            catch (Exception e) {
            	//nothing to do here
            }
        }
    	return returnValue;
    }

    /**
     * Gets child categories for category
     *
     * @param pParentId parent category
     * @param pCatalogId shopping catalog id
     * @return CatalogCategoryDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    private void getChildCategories(Connection conn, int catalogId, 
    		CatalogCategoryDataVector parentCategories, IdVector filterCategoryIds,
    		Map<CatalogCategoryData, CatalogCategoryDataVector> map) throws RemoteException {

    	if (Utility.isSet(parentCategories)) {
    		Iterator<CatalogCategoryData> categoryIterator = parentCategories.iterator();
    		IdVector categoryIds = new IdVector();
    		while (categoryIterator.hasNext()) {
    			categoryIds.add(categoryIterator.next().getCatalogCategoryId());
    		}
            StringBuilder childCategoryQuery = new StringBuilder(200);
            childCategoryQuery.append("SELECT ia.ITEM2_ID parentId, ia.ITEM1_ID childId, cs.SORT_ORDER");
            childCategoryQuery.append(" FROM CLW_CATALOG_STRUCTURE cs, CLW_ITEM_ASSOC ia");
            childCategoryQuery.append(" WHERE cs.CATALOG_ID = ");
            childCategoryQuery.append(catalogId);
            childCategoryQuery.append(" AND cs.CATALOG_STRUCTURE_CD = '");
            childCategoryQuery.append(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
            childCategoryQuery.append("' AND cs.ITEM_ID = ia.ITEM1_ID");
            childCategoryQuery.append(" AND (ia.ITEM2_ID IN ");
        	//handle the fact that an in clause can contain at most 1000 items.
            int categoryIdCount = categoryIds.size();
        	boolean includeOr = false;
    	    for (int i=0; i<categoryIdCount; i+=inClauseSize) {
    	    	int endIndex = i+inClauseSize;
        		if (endIndex > categoryIdCount) {
        			endIndex=categoryIdCount;
        		}
        		if (includeOr) {
        			childCategoryQuery.append(" OR ia.ITEM2_ID IN ");
        		}
        		childCategoryQuery.append("(");
        		childCategoryQuery.append(Utility.toCommaSting(categoryIds.subList(i,endIndex)));
        		childCategoryQuery.append(")");
        		includeOr = true;
        	}
            childCategoryQuery.append(") AND ia.ITEM_ASSOC_CD = '");
            childCategoryQuery.append(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
            childCategoryQuery.append("' ORDER BY ia.ITEM2_ID, ia.ITEM1_ID");
            Statement stmt = null;
            ResultSet rs = null;
            try {
                CatalogCategoryDataVector childCategories = new CatalogCategoryDataVector();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(childCategoryQuery.toString());
            	while (rs.next()){
            		int parentId = rs.getInt(1);
            		int childId = rs.getInt(2);
            		int order = rs.getInt(3);
            		ItemData parentItem = ItemData.createValue();
            		parentItem.setItemId(parentId);
            		ItemData item = ItemData.createValue();
            		item.setItemId(childId);
            		CatalogCategoryData catalogCategory = new CatalogCategoryData();
            		catalogCategory.setItemData(item);
            		catalogCategory.setParentCategory(parentItem);
            		catalogCategory.setSortOrder(order);
                    childCategories.add(catalogCategory);
            	}
        		Utility.filter(childCategories, filterCategoryIds);
        		categoryIterator = childCategories.iterator();
        		while (categoryIterator.hasNext()) {
        			CatalogCategoryData childCategory = categoryIterator.next();
        			//put this category into the map
        			map.put(childCategory, new CatalogCategoryDataVector());
        			//put this category into the list of children for its parent
        			int parentId = childCategory.getParentCategory().getItemId();
        			Iterator<CatalogCategoryData> parentIterator = map.keySet().iterator();
        			boolean foundParent = false;
        			while (!foundParent && parentIterator.hasNext()) {
            			CatalogCategoryData parentCategory = parentIterator.next();
            			if (parentCategory.getCatalogCategoryId() == parentId) {
            				foundParent = true;
            				map.get(parentCategory).add(childCategory);
            			}
        			}
        		}
            	getChildCategories(conn, catalogId, childCategories, filterCategoryIds, map);	
            }
            catch (Exception exc) {
            	RemoteException e = processException(exc);
            	throw(e);
            }
            finally {
            	try {
                	rs.close();
                	stmt.close();
            	}
            	catch (Exception e) {
            		//nothing to do here
            	}
            }
    	}
    }
    
    private void createCatalogMenu(MenuItemView currentMenuItem, String pPatternLink,
    		Map<CatalogCategoryData, CatalogCategoryDataVector> map) {
        
    	//find the children of the currentMenu item
    	String key = currentMenuItem.getKey();
    	List<CatalogCategoryData> children = new ArrayList<CatalogCategoryData>(); 
        Iterator<CatalogCategoryData> catalogCategoryIterator = map.keySet().iterator();
        while (catalogCategoryIterator.hasNext()) {
        	CatalogCategoryData catalogCategory = catalogCategoryIterator.next();
        	//top level categories will have a null parent item and the key will be root
        	if (catalogCategory.getParentCategory() == null) {
        		if (MenuItemView.ATTR.ROOT.equals(key)) {
        			children.add(catalogCategory);
        		}
        	}
        	//otherwise the parent item id will match the key
        	else {
        		if (String.valueOf(catalogCategory.getParentCategory().getItemId()).equals(key)) {
        			children.add(catalogCategory);
        		}
        	}
        }
        //if any children were found, then for each one add it to the current menu and find
        //its children.
        if (Utility.isSet(children)) {
            Collections.sort(children, CATALOG_CATEGORY_SHORT_DESC_COMP);
        	Iterator<CatalogCategoryData> childIterator = children.iterator();
        	while (childIterator.hasNext()) {
        		CatalogCategoryData catalogCategory = childIterator.next();
        		MenuItemView menu = new MenuItemView();
        		menu.setName(catalogCategory.getCatalogCategoryShortDesc());
        		menu.setKey(String.valueOf(catalogCategory.getCatalogCategoryId()));
        		menu.setDisplayStatus(MenuItemView.DISPLAY_STATUS.CLOSE);
        		menu.setLink(MessageFormat.format(pPatternLink, menu.getKey()));
        		if (currentMenuItem.getSubItems() == null) {
        			currentMenuItem.setSubItems(new MenuItemViewVector());
        		}
        		currentMenuItem.getSubItems().add(menu);
        		createCatalogMenu(menu, pPatternLink, map);
        	}
        }
    }

    private MenuItemView getCategoryMenu(Connection con, PreparedStatement pstmt,
    		CatalogCategoryData pCatalogCat,
    		IdVector pFilterIds,
    		String pLinkPattern) throws RemoteException {

    	if (pCatalogCat == null)
    		return null;


		MenuItemView menu = new MenuItemView();

		menu.setName(pCatalogCat.getCatalogCategoryShortDesc());
		menu.setKey(String.valueOf(pCatalogCat.getCatalogCategoryId()));
		menu.setDisplayStatus(MenuItemView.DISPLAY_STATUS.CLOSE);
		menu.setLink(MessageFormat.format(pLinkPattern, menu.getKey()));

		CatalogCategoryDataVector childCattalogCats = (CatalogCategoryDataVector) Utility.filter(getChildCategories(con, pstmt, pCatalogCat.getCatalogCategoryId()), pFilterIds);

		if (!childCattalogCats.isEmpty()) {
			Collections.sort(childCattalogCats, CATALOG_CATEGORY_SHORT_DESC_COMP);
			Iterator it = childCattalogCats.iterator();
			while (it.hasNext()) {
				MenuItemView subItemMenu = getCategoryMenu(con, pstmt, (CatalogCategoryData) it.next(), pFilterIds, pLinkPattern);
				MenuItemViewVector subItems = menu.getSubItems();
				if (subItems == null) {
					subItems = new MenuItemViewVector();
					menu.setSubItems(subItems);
				}
				subItems.add(subItemMenu);
			}
		}
		return menu;
    }

    /**
     * Gets child categories for category
     *
     * @param pParentId parent category
     * @param pCatalogId shopping catalog id
     * @return CatalogCategoryDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public CatalogCategoryDataVector getChildCategories(int pCatalogId, int pParentId) throws RemoteException {

        Connection con = null;
        CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();

        try {
            con = getConnection();

            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            dbc.addJoinCondition(CatalogStructureDataAccess.ITEM_ID, ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM1_ID);

            dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC,ItemAssocDataAccess.ITEM2_ID, pParentId);
            dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC,ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

            CatalogStructureDataVector catalogStructures = CatalogStructureDataAccess.select(con, dbc);

            if (catalogStructures.isEmpty())
            	return catalogCategoryDV;
            HashMap<Integer, Object[]/*params*/> childCategoryMap = new HashMap<Integer, Object[]>();
            for (Object oCatalogStructure : catalogStructures) {
                CatalogStructureData catalogStructure = (CatalogStructureData) oCatalogStructure;
                childCategoryMap.put(catalogStructure.getItemId(), new Object[]{catalogStructure.getSortOrder()});
            }

            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID,new ArrayList<Integer>(childCategoryMap.keySet()));
            dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
            dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

            ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

            for (Object anItemDV : itemDV) {
                ItemData itemD = (ItemData) anItemDV;
                CatalogCategoryData ccD = new CatalogCategoryData();
                ccD.setItemData(itemD);
                ccD.setSortOrder((Integer) childCategoryMap.get(itemD.getItemId())[0]);
                catalogCategoryDV.add(ccD);
            }

            assignMajorCategory(con, catalogCategoryDV);

        } catch (Exception exc) {
            processException(exc);
        } finally {
            closeConnection(con);
        }

        return catalogCategoryDV;
  }

    /**
     * Gets child categories for category
     *
     * @param pParentId parent category
     * @param pCatalogId shopping catalog id
     * @return CatalogCategoryDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    private CatalogCategoryDataVector getChildCategories(Connection con, PreparedStatement pstmt, int pParentId) throws RemoteException {

        CatalogCategoryDataVector catalogCategoryDV = new CatalogCategoryDataVector();

        try {

        	pstmt.setInt(1, pParentId);
        	ResultSet rs = pstmt.executeQuery();
        	while (rs.next()){
        		int itemId = rs.getInt(1);
        		int order = rs.getInt(2);
        		ItemData itemD = ItemDataAccess.select(con, itemId);
        		CatalogCategoryData ccD = new CatalogCategoryData();
                ccD.setItemData(itemD);
                ccD.setSortOrder(order);
                catalogCategoryDV.add(ccD);
        	}
        	rs.close();

            if (catalogCategoryDV.isEmpty())
            	return catalogCategoryDV;

            assignMajorCategory(con, catalogCategoryDV);
            return catalogCategoryDV;
        } catch (Exception exc) {
            processException(exc);
            throw new RemoteException("Error. CatalogInformationBean.getChildCategories() Exception happened: " + exc);
        }
  }

    public CatalogDataVector getCatalogsByCrit(EntitySearchCriteria entitySearchCriteria)
        throws RemoteException {
        return getCatalogsByCritAndIds(entitySearchCriteria, null);
    }

    public CostCenterAssocDataVector getCatalogCostCenterAssoc(int catalogId, int costCenterId) throws RemoteException {
        CostCenterAssocDataVector costCenters = new CostCenterAssocDataVector();
        Connection connection = null;
        try {
            connection = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID, catalogId);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID, costCenterId);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD, RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            costCenters = CostCenterAssocDataAccess.select(connection, crit);
        } catch (Exception ex) {
            logInfo("[getCatalogCostCenterAssoc] An error occurred. " + ex.getMessage());
            ex.printStackTrace();
            throw new RemoteException("getCatalogCostCenterAssoc: " + ex.getMessage());
        } finally {
            closeConnection(connection);
        }
        return costCenters;
    }

    public CostCenterData getCostCenterById(int costCenterId) throws RemoteException {
        CostCenterData costCenter = null;
        Connection connection = null;
        try {
            connection = getConnection();
            costCenter = CostCenterDataAccess.select(connection, costCenterId);
        } catch (DataNotFoundException ex) {
            costCenter = null;
        } catch (Exception ex) {
            logInfo("[getCostCenterById] An error occurred. " + ex.getMessage());
            ex.printStackTrace();
            throw new RemoteException("getCostCenterById: " + ex.getMessage());
        } finally {
            closeConnection(connection);
        }
        return costCenter;
    }

    private int getDecimalPlacesForContract(Connection conn, ContractData contractD) throws Exception{
      int decimalPlaces = 0;
      String locale = contractD.getLocaleCd();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(CurrencyDataAccess.LOCALE, locale);
      CurrencyDataVector currDV = CurrencyDataAccess.select(conn, crit);
      if (currDV != null && currDV.size() > 0) {
        CurrencyData currD = (CurrencyData) currDV.get(0);
        decimalPlaces = currD.getDecimals();
      }
      return  decimalPlaces;
    }

    public CostCenterDataVector getCatalogCostCenters(int pCatalogId, IdVector pAvailableCostCentertIds, boolean pOrderByName) throws RemoteException {

        CostCenterDataVector costCenter = new CostCenterDataVector();

        Connection con = null;
        try {
            con = getConnection();
            costCenter = getCatalogCostCenters(con, pCatalogId, pAvailableCostCentertIds, pOrderByName);
        } catch (DataNotFoundException ex) {
            costCenter = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException("getCatalogCostCenters()=> ERROR: " + ex.getMessage());
        } finally {
            closeConnection(con);
        }

        return costCenter;
    }

    public CostCenterDataVector getCatalogCostCenters(Connection pCon,
                                                      int pCatalogId,
                                                      IdVector pAvailableCostCentertIds,
                                                      boolean pOrderByName) throws Exception {
        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
        crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
        crit.addGreaterThan(CatalogStructureDataAccess.COST_CENTER_ID, 0);
        if (pAvailableCostCentertIds != null) {
            crit.addOneOf(CatalogStructureDataAccess.COST_CENTER_ID, pAvailableCostCentertIds);
        }

        IdVector costCenterIds = CatalogStructureDataAccess.selectIdOnly(pCon, CatalogStructureDataAccess.COST_CENTER_ID, crit);

        crit = new DBCriteria();
        crit.addOneOf(CostCenterDataAccess.COST_CENTER_ID, costCenterIds);
        if (pOrderByName) {
            crit.addOrderBy(CostCenterDataAccess.SHORT_DESC, true);
        }

        CostCenterDataVector costCenterVec = CostCenterDataAccess.select(pCon, crit);
        for (int i = 0; null != costCenterVec && i < costCenterVec.size(); i++) {
            CostCenterData ccd = (CostCenterData) costCenterVec.get(i);
            if (null == ccd.getAllocateFreight()) {
                ccd.setAllocateFreight(DEFAULT_ALLOCATED_FREIGHT);
            }
        }

        return costCenterVec;
    }
    
    /**
     * Gets List of Catalog Structure Ids.
     * @param distributorId - Id of Distributor.
     * @return List<Integer> 
     * @throws RemoteException
     */
    public List<Integer> getCatalogStructureIds(int distributorId) throws RemoteException {
    	Connection con = null;
    	List<Integer> catalogIdList = null;
    	try{
    		con = getConnection();
    		DBCriteria dbc = new DBCriteria();
    		dbc.addEqualTo(CatalogStructureDataAccess.BUS_ENTITY_ID, distributorId);
    		
    		String beTable = BusEntityDataAccess.CLW_BUS_ENTITY; 
    		String csTable = CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE;
    		String cTable = CatalogDataAccess.CLW_CATALOG;

    		dbc.addJoinTable(beTable);
    		dbc.addJoinCondition(csTable,CatalogStructureDataAccess.BUS_ENTITY_ID,beTable,BusEntityDataAccess.BUS_ENTITY_ID);
    		dbc.addJoinTableNotEqualTo(beTable, BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
    		
    		dbc.addJoinTable(cTable);
    		dbc.addJoinCondition(csTable,CatalogStructureDataAccess.CATALOG_ID,cTable,CatalogDataAccess.CATALOG_ID); 
        	dbc.addJoinTableEqualTo(cTable, CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        	
    		catalogIdList = CatalogStructureDataAccess.getCatalogStructureIds(con, dbc);
    		
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(con!=null)
    				con.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}

    	return catalogIdList;
    }
    
    public ArrayList getLevel1Categories(int pStoreCatalogId) throws RemoteException{
    	Connection conn = null;
    	
    	ArrayList catList = new ArrayList();
    	
    	try{
    		conn = getConnection();
    		
    		//get top level categories
    		String sql = "select distinct  i.item_id category_id, i.short_desc category_name \n" +
            " from clw_item i, clw_catalog_structure cs \n" +
              " where cs.catalog_id = " + pStoreCatalogId + "  \n" +
              " and i.item_id = cs.item_id \n" +
              " and cs.catalog_structure_cd = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "' \n" +
                  " and i.item_id not in ( select item1_id from  \n" +
                  " clw_item_assoc " +
                  "where catalog_id = " + pStoreCatalogId + "  \n" +
                  " and item_assoc_cd = '" + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' \n" +
                  " ) \n" +
                  " order by category_name";
    		PreparedStatement stmt = conn.prepareStatement(sql);
     	   
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    			int catId = rs.getInt(1);
    			String catName = rs.getString(2);
    			catList.add(catId+"-"+catName);
    		}
    		
    		
    		rs.close();
    		stmt.close();
    		
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(conn!=null)
    				conn.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    	
    	return catList;
    }
    
    public ArrayList getNextLevelCategories(int pPrevCategoryId, int pStoreCatalogId) throws RemoteException{
    	Connection conn = null;
    	
    	ArrayList catList = new ArrayList();
    	
    	try{
    		conn = getConnection();
    		
    		//get top level categories
    		String sql = "select ia.item1_id, i.short_desc from clw_item_assoc ia, clw_item i " +
    				"where ia.catalog_id="+pStoreCatalogId+
    				" and ia.item2_id ="+pPrevCategoryId+
    				" and ia.item_assoc_cd = '"+RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY+"' "+
    				"and ia.item1_id = i.item_id "+
    				"order by i.short_desc";
    		
    		PreparedStatement stmt = conn.prepareStatement(sql);
     	   
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    			int catId = rs.getInt(1);
    			String catName = rs.getString(2);
    			catList.add(catId+"-"+catName);
    		}
    		
    		
    		rs.close();
    		stmt.close();
    		
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(conn!=null)
    				conn.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    	
    	return catList;
    }
    
    public String verifyOrderLocales(int pUserId, String pBegDate, String pEndDate, List pOrderStatusList,
    		List sites) throws RemoteException{
    	Connection con = null;
    	String errorMess=null;
    	
    	try{
    		con = getConnection();
    		String sql = "select o.locale_cd,count(*) "+
    					"from "+
    					"clw_order o,clw_user_assoc ua,clw_bus_entity site "+
    					"where "+
    					"o.original_order_date BETWEEN " +
    					"TO_DATE('"+pBegDate+"','MM/dd/yyyy') AND TO_DATE('"+pEndDate+"','MM/dd/yyyy') "+
    					"and ua.user_id = "+pUserId+
    					"and ua.bus_entity_id = site.bus_entity_id and ua.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.SITE+"' "+
    					"and o.site_id = site.bus_entity_id "+
    					"and o.locale_cd is not null ";
    					
    		if(sites!=null && sites.size()>0){
    			sql = sql + " and site.bus_entity_id in ("+Utility.toCommaSting(sites)+") ";
    		}
    		    		
    		sql = sql+"group by o.locale_cd";
  		
    		PreparedStatement stmt = con.prepareStatement(sql);
      	   
    		ResultSet rs = stmt.executeQuery();
    		int rCount = 0;
    		while(rs.next()){
    			rCount++;	
    		}

			if(rCount > 1){
				errorMess ="reporting.error.multipleCurrencies";
			}
    		
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(con!=null)
    				con.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    	return errorMess;
    }
    
    public HashMap getMfgsFromAccountCatalogs(List sites, int pUserId) throws RemoteException{
    	Connection con = null;
    	HashMap mfgMap = new HashMap();
    	
    	try{
    		con = getConnection();
    		
    		String sql = "select mfg.bus_entity_id, mfg.short_desc "+
	    			"from clw_catalog c,clw_catalog_assoc ca, clw_catalog_structure cs, clw_item_mapping im, clw_bus_entity mfg "+
	    			"where "+
	    			"ca.bus_entity_id in ";
	    			
    		if(sites!=null && sites.size()>0){
    			sql = sql +"(select distinct ba.bus_entity2_id from clw_bus_entity_assoc ba "+
    				"where "+
    				" ba.bus_entity1_id in ("+Utility.toCommaSting(sites)+") ";
    		}else{
    			sql = sql +"(select distinct ba.bus_entity2_id from clw_bus_entity_assoc ba,clw_user_assoc ua, clw_bus_entity site "+
				"where "+
    			" ua.user_id = "+pUserId+
    			" and ua.bus_entity_id = site.bus_entity_id "+
    			" and ua.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.SITE+"' "+
    			" and ba.bus_entity1_id = site.bus_entity_id ";
    		}
	
    		sql = sql + " and bus_entity_assoc_cd='"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"') "+
    			"and ca.catalog_assoc_cd='"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
    			"and ca.catalog_id = c.catalog_id "+
    			"and c.catalog_type_cd='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
    			"and cs.catalog_id = c.catalog_id "+
    			"and cs.item_id = im.item_id "+
    			"and im.bus_entity_id = mfg.bus_entity_id "+
    			"and mfg.bus_entity_type_cd='"+RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER+"' "+
    			"and mfg.bus_entity_status_cd='"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' "+
    			"group by mfg.bus_entity_id, mfg.short_desc";
			
    		PreparedStatement stmt = con.prepareStatement(sql);
    		
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    			int mfgId = rs.getInt(1);
    			String mfgName = rs.getString(2);
    			
    			if(!mfgMap.containsKey(mfgName)){
    				mfgMap.put(mfgName, mfgId);
    			}
    		}
					
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(con!=null)
    				con.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    	
    	return mfgMap;
    }
    
    public HashMap getCategoryItemsTotal(int pUserId, String pBegDate, String pEndDate, List pOrderStatusList,
    		List sites, List mfgs) throws RemoteException{
    	Connection con = null;
    	HashMap catItemTotalMap = new HashMap();
    	
    	try{
    		con = getConnection();
    		
    		String sql = "select ia.item2_id, i.item_id, i.short_desc, mfg.bus_entity_id, mfg.short_desc," +
    		" sum(oi.cust_contract_price * oi.total_quantity_ordered) "+
    		"from clw_order o, clw_order_item oi, clw_item i, clw_user_assoc ua, clw_bus_entity acc, clw_user_assoc ua2, clw_bus_entity site, " +
    		"clw_catalog c, clw_catalog_assoc ca, clw_item_assoc ia, clw_item_mapping im, clw_bus_entity mfg "+
    		"where "+	
    		"o.original_order_date BETWEEN " +
			"TO_DATE('"+pBegDate+"','MM/dd/yyyy') AND TO_DATE('"+pEndDate+"','MM/dd/yyyy') "+
			"and o.order_id = oi.order_id "+
			"and ua.user_id = "+pUserId+
			" and ua2.user_id = ua.user_id " +
			"and ua2.bus_entity_id = site.bus_entity_id and ua2.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.SITE+"' "+
			"and o.site_id = site.bus_entity_id ";
			
    		if(sites!=null && sites.size()>0){
    			sql = sql + " and site.bus_entity_id in ("+Utility.toCommaSting(sites)+") ";
    		}
			
			sql = sql+ " and ua.bus_entity_id = acc.bus_entity_id and ua.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.ACCOUNT+"' "+
			"and o.account_id = acc.bus_entity_id "+
			"and ca.bus_entity_id = acc.bus_entity_id "+
			"and acc.bus_entity_type_cd='"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"' "+
			"and ca.catalog_assoc_cd='"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
			"and ca.catalog_id = c.catalog_id and c.catalog_type_cd='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
			"and o.order_status_cd in ("+Utility.toCommaSting(pOrderStatusList, '\'')+") "+
    		"and i.item_id = oi.item_id "+
    		"and ia.item1_id = i.item_id "+
    		"and ia.catalog_id = c.catalog_id "+
    		"and i.item_id = im.item_id and mfg.bus_entity_id = im.bus_entity_id "+
			"and im.item_mapping_cd='"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER+"' "+
			"and mfg.bus_entity_type_cd='"+RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER+"' ";
    		
			if(mfgs!=null && mfgs.size()>0){
				sql = sql + "and mfg.bus_entity_id in ("+Utility.toCommaSting(mfgs)+") ";
			}
    		
    		sql = sql + "group by ia.item2_id, i.item_id , i.short_desc, mfg.bus_entity_id, mfg.short_desc "+
    		"order by i.item_id";
    		
    		PreparedStatement stmt = con.prepareStatement(sql);
     	   
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    			int categoryId = rs.getInt(1);
    			int itemId = rs.getInt(2);
    			String itemName = rs.getString(3);
    			int mfgId = rs.getInt(4);
    			String mfgName = rs.getString(5);
    			BigDecimal total = rs.getBigDecimal(6);
    			CategoryItemMfgKey key = new CategoryItemMfgKey();
    			key.setItemId(itemId);
    			key.setItemName(itemName);
    			key.setMfgId(mfgId);
    			key.setMfgName(mfgName);
    			
    			if(catItemTotalMap.containsKey(categoryId)){
    				
    				ArrayList itemTotals = (ArrayList)catItemTotalMap.get(categoryId);
    				Iterator it = itemTotals.iterator();
    				boolean foundF1 = false;
    				
    				while(it.hasNext()){
    					if(!foundF1){
	    					HashMap itemMap = (HashMap)it.next();
	    					if(itemMap.containsKey(key)){
	    						foundF1 = true;
	    						BigDecimal tot = ((BigDecimal)itemMap.get(key)).add(total);
	    						itemMap.put(key, tot);
	    					}
    					}
    				}
    				if(!foundF1){
    					HashMap itemMap = new HashMap();
    					itemMap.put(key, total);
    					itemTotals.add(itemMap);
    				}
    				
    			}else{
    				
    				ArrayList itemTotals = new ArrayList();
    				HashMap itemMap = new HashMap();
    				itemMap.put(key, total);
    				itemTotals.add(itemMap);
    				
    				catItemTotalMap.put(categoryId, itemTotals);
    			}
    		}
    		
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(con!=null)
    				con.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    	
    	return catItemTotalMap;
    }
    
    public HashMap getItemTotalPerAcctCatalog(int pUserId, String pBegDate, String pEndDate, List pOrderStatusList) throws RemoteException{
    	Connection con = null;
    	HashMap itemTotalMap = new HashMap();
    	
    	try{
    		con = getConnection();
    		
    		String sql = "select o.account_id, ca.catalog_id as acct_cat, i.item_id, " +
    				"sum(oi.cust_contract_price * oi.total_quantity_ordered) as item_total "+
    				"from clw_order o, clw_order_item oi, clw_item i, clw_user_assoc ua, clw_bus_entity acc, " +
    				"clw_catalog c, clw_catalog_assoc ca "+
    				"where "+
    				"NVL(o.original_order_date,o.original_order_time) BETWEEN " +
    				"TO_DATE('"+pBegDate+"','MM/dd/yyyy') AND TO_DATE('"+pEndDate+"','MM/dd/yyyy') "+
    				"and o.order_id = oi.order_id "+
    				"and ua.user_id = "+pUserId+
    				" and ua.bus_entity_id = acc.bus_entity_id and ua.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.ACCOUNT+"' "+
    				"and o.account_id = acc.bus_entity_id "+
    				"and ca.bus_entity_id = acc.bus_entity_id "+
    				"and acc.bus_entity_type_cd='"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"' "+
    				"and ca.catalog_assoc_cd='"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
    				"and ca.catalog_id = c.catalog_id and c.catalog_type_cd='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
    				"and o.order_status_cd in ("+Utility.toCommaSting(pOrderStatusList, '\'')+") "+
    				"and i.item_id = oi.item_id "+
    				"group by o.account_id, ca.catalog_id, i.item_id "+
    				"order by i.item_id ";
    		
    		PreparedStatement stmt = con.prepareStatement(sql);
    	   
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    	        int acctId = rs.getInt(1);
    	        int catalogId = rs.getInt(2);
    	        int itemId = rs.getInt(3);
    	        BigDecimal itemTotal = rs.getBigDecimal(4);
    	        
    	        if(itemTotalMap.containsKey(catalogId)){
    	        	
    	        	HashMap valueMap = (HashMap)itemTotalMap.get(catalogId);
    	        	if(valueMap.containsKey(itemId)){
    	        		BigDecimal thisTotal = ((BigDecimal)valueMap.get(itemId)).add(itemTotal);
    	        		valueMap.put(itemId, thisTotal);
    	        	}else{
    	        		valueMap.put(itemId, itemTotal);
    	        	}
    	        	
    	        }else{
    	        	
    	        	HashMap valueMap = new HashMap();
    	        	valueMap.put(itemId, itemTotal);
    	        	itemTotalMap.put(catalogId, valueMap);
    	        }
    		}
    		rs.close();
    		stmt.close();
    		
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(con!=null)
    				con.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}

    	return itemTotalMap;
    }
    private String getCatalogIdsByStore(int storeId){
    	DBCriteria catAssocDbc = new DBCriteria();
        catAssocDbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, storeId);
        catAssocDbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                               RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        String catalogIds = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, catAssocDbc);
    	return catalogIds;
    }
    
    public List getAcctCatalogCategoriesForUser(int pUserId) throws RemoteException{
    	List categories = new ArrayList();
    	  Connection conn = null;
    	  try{
    		  conn = getConnection();
    		  
    		  String sql ="select distinct cs.item_id "+
  		  		  "from clw_catalog_assoc ca, clw_catalog ct, clw_catalog_structure cs, clw_user_assoc ua "+
  		  		  "where "+ 
  		  		  "ua.user_id = "+pUserId+
  		  		  " and ua.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.ACCOUNT+"' "+
  		  		  "and ca.bus_entity_id = ua.bus_entity_id "+
  		  		  "and ca.catalog_id = ct.catalog_id "+
  		  		  "and ct.catalog_type_cd='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
  		  		  "and cs.catalog_id = ct.catalog_id "+
  		  		  "and cs.catalog_structure_cd='"+RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+"' ";
    		  
    		PreparedStatement stmt = conn.prepareStatement(sql);
   	   
	  		ResultSet rs = stmt.executeQuery();
	  		while (rs.next()) {
	    	      	int catId = rs.getInt(1);
	    	      	if(!categories.contains(catId)){
	    	      		categories.add(catId);
	    	      	}
	  		}
	  		rs.close();
	  		stmt.close();
	  		
    	  }catch (Exception exc) {
    		  logError(exc.getMessage());
    		  exc.printStackTrace();
    		  throw new RemoteException(exc.getMessage());
    	  } finally {
    		  try {
    			  if(conn!=null)
    				  conn.close();
    		  } catch (SQLException exc) {
    			  logError(exc.getMessage());
    			  exc.printStackTrace();
    			  throw new RemoteException(exc.getMessage());
    		  }
    	  }
    	  return categories;
    }
    
    public List getAcctCatalogCategories(List pAccts) throws RemoteException{
  	  
  	  List categories = new ArrayList();
  	  Connection conn = null;
  	  try{
  		  conn = getConnection();
  		  
  		  String sql ="select distinct cs.item_id "+
		  		  "from clw_catalog_assoc ca, clw_catalog ct, clw_catalog_structure cs "+
		  		  "where "+ 
		  		  "ca.bus_entity_id in ("+Utility.toCommaSting(pAccts)+") "+
		  		  "and ca.catalog_id = ct.catalog_id "+
		  		  "and ct.catalog_type_cd='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
		  		  "and cs.catalog_id = ct.catalog_id "+
		  		  "and cs.catalog_structure_cd='"+RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+"' ";
  		  
  		PreparedStatement stmt = conn.prepareStatement(sql);
 	   
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
  	      	int catId = rs.getInt(1);
  	      	if(!categories.contains(catId)){
  	      		categories.add(catId);
  	      	}
		}
		rs.close();
		stmt.close();
		
  	  }catch (Exception exc) {
  		  logError(exc.getMessage());
  		  exc.printStackTrace();
  		  throw new RemoteException(exc.getMessage());
  	  } finally {
  		  try {
  			  if(conn!=null)
  				  conn.close();
  		  } catch (SQLException exc) {
  			  logError(exc.getMessage());
  			  exc.printStackTrace();
  			  throw new RemoteException(exc.getMessage());
  		  }
  	  }
  	  return categories;
    }
    
    // check if category contains products
    public boolean doesCategoryContainProduct(int catalogId, int categoryId)  throws RemoteException {
    	Connection conn = null;
    	try{
    		conn = getConnection();
    		DBCriteria dbc = new DBCriteria();
    		dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);
    		dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, categoryId);
    		dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
    		IdVector itemIdDV = ItemAssocDataAccess.selectIdOnly(conn, ItemAssocDataAccess.ITEM1_ID, dbc);
    		return itemIdDV.size() > 0;
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(conn!=null)
    				conn.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    }
    
    public boolean isLowestLevelCategory(int catalogId, int categoryId)  throws RemoteException {
    	Connection conn = null;
    	try{
    		conn = getConnection();
    		DBCriteria dbc = new DBCriteria();
    		dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);
    		dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, categoryId);
    		Vector relTypeV = new Vector();
    		dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
    		IdVector itemIdDV = ItemAssocDataAccess.selectIdOnly(conn, ItemAssocDataAccess.ITEM1_ID, dbc);
    		return itemIdDV.size() == 0;
    	}catch (Exception exc) {
    		logError(exc.getMessage());
    		exc.printStackTrace();
    		throw new RemoteException(exc.getMessage());
    	} finally {
    		try {
    			if(conn!=null)
    				conn.close();
    		} catch (SQLException exc) {
    			logError(exc.getMessage());
    			exc.printStackTrace();
    			throw new RemoteException(exc.getMessage());
    		}
    	}
    }

    public CatalogCategoryDataVector getLowestStoreCatalogCategories(int pStoreCatalogId)
    throws RemoteException {
    	CatalogCategoryDataVector ccDV = getAllStoreCatalogCategories(pStoreCatalogId);
    	for (Iterator it = ccDV.iterator(); it.hasNext(); ){
    		CatalogCategoryData ccD = (CatalogCategoryData) it.next();
    		if (!ccD.isLowestLevel()){
    			it.remove();
    		}
    	}
    	return ccDV;
    }
    
    private static final String TBL_CATALOG_TEMP              = "CLT_APPCMD_CATALOG";
    private static final String TBL_NEW_COST_CENTERS_TEMP     = "CLT_APPCMD_NEW_COST_CENTERS";
    private static final String TBL_CURRENT_COST_CENTERS_TEMP = "CLT_APPCMD_CUR_COST_CENTERS";
    private static final String TBL_TO_UPDATE_TEMP            = "CLT_APPCMD_TO_UPDATE";
    
    private static final String TBL_CLW_CATALOG_STRUCTURE     = "CLW_CATALOG_STRUCTURE";
    private static final String TBL_CLW_CATALOG_ASSOC         = "CLW_CATALOG_ASSOC";
    private static final String TBL_CLW_CATALOG               = "CLW_CATALOG";
    private static final String TBL_CLW_ITEM_ASSOC            = "CLW_ITEM_ASSOC";
    private static final String TBL_CLW_COST_CENTER_ASSOC     = "CLW_COST_CENTER_ASSOC";
    private static final String TBL_CLW_COST_CENTER           = "CLW_COST_CENTER";
    private static final String TBL_CLW_BUS_ENTITY_ASSOC	  = "CLW_BUS_ENTITY_ASSOC";
    public int resetCostCenters( String user, String tempDbUser ,IdVector excludeShoppingCatalogList) throws  RemoteException {

        Connection connection = null;
        String tempSuffix = ""; //"_" + String.valueOf(pProcessId);
        String tempUser = tempDbUser; //System.getProperty("appcmd.tempUser");
        String dbClwNamespace = null; // may be set in the parameters of job?
        String tblCatalogTemp = Utility.getFullTableName(tempUser, TBL_CATALOG_TEMP, tempSuffix);
        String tblNewCostCentersTemp = Utility.getFullTableName(tempUser, TBL_NEW_COST_CENTERS_TEMP, tempSuffix);
        String tblCurrentCostCentersTemp = Utility.getFullTableName(tempUser, TBL_CURRENT_COST_CENTERS_TEMP, tempSuffix);
        String tblToUpdateTemp = Utility.getFullTableName(tempUser, TBL_TO_UPDATE_TEMP, tempSuffix);

        String tblClwCatalog = Utility.getFullTableName(dbClwNamespace, TBL_CLW_CATALOG);
        String tblClwCatalogAssoc = Utility.getFullTableName(dbClwNamespace, TBL_CLW_CATALOG_ASSOC);
        String tblClwCatalogStructure = Utility.getFullTableName(dbClwNamespace, TBL_CLW_CATALOG_STRUCTURE);
        String tblClwItemAssoc = Utility.getFullTableName(dbClwNamespace, TBL_CLW_ITEM_ASSOC);
        String tblClwCostCenterAssoc = Utility.getFullTableName(dbClwNamespace, TBL_CLW_COST_CENTER_ASSOC);
        String tblClwCostCenter = Utility.getFullTableName(dbClwNamespace, TBL_CLW_COST_CENTER);
        String tblClwBusEntityAssoc = Utility.getFullTableName(dbClwNamespace, TBL_CLW_BUS_ENTITY_ASSOC);
        int updated = 0;
        try {
	       connection = getConnection();
	       //////////////////////////////////////////////////////////////
	       //  Step 1. Prepare TMP_CATALOG temporal table: 
	       //          	association of Shopping catalog to Account catalog
	       /////////////////////////////////////////////////////////////
	       fillTmpCatalog(connection, tempUser, excludeShoppingCatalogList, tblCatalogTemp, tblClwCatalog, tblClwCatalogAssoc, tblClwCostCenter, tblClwCostCenterAssoc, tblClwBusEntityAssoc);
	 
	       //////////////////////////////////////////////////////////////
	       //  Step 2. Prepare TMP_NEW_COST_CENTERS temporal table: 
	       //         Cost Centers that has been assigned for categories in Account catalogs.
	       /////////////////////////////////////////////////////////////
	       fillTmpNewCostCenters(connection, tempUser, tblNewCostCentersTemp, tblClwCatalogStructure, tblCatalogTemp); 
	       //////////////////////////////////////////////////////////////
	       //  Step 3. Prepare TMP_Current_COST_CENTERS temporal table: 
	       //         Cost Centers that has been assigned for categories in Shopping catalogs and product items in Account and Shopping catalogs.
	       /////////////////////////////////////////////////////////////
	       fillTmpCurrentCostCenters(connection, tempUser, tblCurrentCostCentersTemp, tblClwCatalogStructure, tblCatalogTemp); 
	       //////////////////////////////////////////////////////////////
	       //  Step 4. Prepare TMP_TO_UPDATE temporal table: 
	       //         contains rows that should be 
	       /////////////////////////////////////////////////////////////
	       fillTmpToUpdate(connection, tempUser, tblToUpdateTemp, tblNewCostCentersTemp, tblCurrentCostCentersTemp, tblCatalogTemp, tblClwItemAssoc); 
	       //////////////////////////////////////////////////////////////
	       //  Step 5. Reset cost centers using data from TMP_TO_UPDATE temporal table
	       /////////////////////////////////////////////////////////////
	       updated = updateCostCenters(connection, user, tblToUpdateTemp, tblClwCatalogStructure); 
  
        } catch (Exception ex) {
   			ex.printStackTrace();
			throw new RemoteException(ex.getMessage(), ex);
        }
        
       return updated; 
    }
   private void execClearTbl (Connection conn , String tblName ) throws Exception {
       String sql = null;
       ResultSet resSet = null;
       PreparedStatement stmt = null;
      try {
//           sql = "DROP TABLE " + tblName + " PURGE";
           sql = "TRUNCATE TABLE " + tblName ;
           stmt = conn.prepareStatement(sql);
           stmt.executeUpdate();
           stmt.close();
       }
       catch (SQLException ex) {
           String msg = "An error occurred at clearing of table " +
           tblName + ". " + ex.getMessage()+
           "*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }

   }
   private static void createIndex (Connection connection, String tblName, String indexName, String colName) throws SQLException {

       try {
         String sql =
             "CREATE INDEX " + tblName +"_"+ indexName + " ON " +tblName + "(" + colName + ")";
         PreparedStatement stmt = connection.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
       }
       catch (SQLException ex) {
         String msg = "An error occurred at creating index on " + tblName + "(" + colName + ")" +
             ". " + ex.getMessage();
         throw new SQLException("^clw^"+msg+ "^clw^");
       }


     }
   /**
    *  Checking of existence of table in the schema.
    */
   private  boolean checkTableOnExistence(Connection connection,  String namespace, String table) throws SQLException {

       String sql = null;
       PreparedStatement stmt = null;
       ResultSet resSet = null;
       boolean isExistTable = false;

       if (namespace == null || namespace.length() == 0) {
           sql =
               "SELECT COUNT(*) TABLE_COUNT FROM USER_TABLES WHERE upper(TABLE_NAME) = ?";
           stmt = connection.prepareStatement(sql);
           stmt.setString(1, table.toUpperCase());
       }
       else {
           sql =
               "SELECT COUNT(*) FROM ALL_TABLES WHERE upper(OWNER) = ? AND upper(TABLE_NAME) = ?";
           stmt = connection.prepareStatement(sql);
           stmt.setString(1, namespace.toUpperCase());
           stmt.setString(2, table.toUpperCase());
       }
       resSet = stmt.executeQuery();
       if (resSet.next()) {
           isExistTable = (resSet.getInt(1) > 0);
       }
       resSet.close();
       stmt.close();
       return isExistTable;
   }

   private void fillTmpCatalog (Connection connection, String tempUser, IdVector excludeShoppingCatalogList, String tblName, String tblClwCatalog, String tblClwCatalogAssoc, String tblClwCostCenter, String tblClwCostCenterAssoc , String tblClwBusEntityAssoc) throws Exception {
 	   final int FLAG_READY = 0;
 	   final int FLAG_MULTY = 1;
       final int FLAG_POLLOCK = 2;
       final int FLAG_NO_SITES = 3;
       final int POLLOCK_STORE_ID = 540236;
       
  	   /// Deleting of already existing temporary table 
       String sql = null;
       ResultSet resSet = null;
       PreparedStatement stmt = null;
       boolean isExistTempTable = checkTableOnExistence(connection, null, tblName);
       if (isExistTempTable) {
       		execClearTbl(connection, tblName);
       } else {
	       /// Creation of the table 'TMP_CATALOG'
	       try {
	           sql ="create table " + tblName + " ( "+
	           	" SHOPPING_CATALOG_ID NUMBER NOT NULL, "+
	           	" ACCOUNT_CATALOG_ID NUMBER NOT NULL,  "+
	           	" MULTY_FL NUMBER  "+
	           	") " +
	           	"";
		        stmt = connection.prepareStatement(sql);
		        stmt.executeUpdate();
		        stmt.close();
		        log.info("fillTmpCatalog() ==> Table "+ tblName+ " created.");
		        /// indexes on TMP_CATALOG
		        createIndex(connection, tblName, "I1" , "SHOPPING_CATALOG_ID" ) ;
		        createIndex(connection, tblName, "I2" , "ACCOUNT_CATALOG_ID" ) ;

	       } catch (SQLException ex) {
		        String msg = "An error occurred at creation of table " +
		        	tblName + ". " + ex.getMessage()+
		             "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		    }
       } 
       
	    /// Population of temporal table: association of Shopping catalog to Account catalog
       try {
           sql = "INSERT INTO "+ tblName + " (" +
           	   " SELECT DISTINCT ca1.catalog_id scat, acccat.catalog_id acat, "+FLAG_READY+" "+
           	   "   FROM (SELECT ca.catalog_id, CA.BUS_ENTITY_ID "+
           	   "           FROM "+ tblClwCatalog +" c, "+
           	   tblClwCatalogAssoc +" ca, "+
           	   tblClwCostCenterAssoc +" cca, "+
           	   tblClwCostCenter +" cc "+
           	   "          WHERE     c.catalog_id = ca.catalog_id "+
           	   "                AND C.CATALOG_TYPE_CD = 'ACCOUNT' "+
           	   "                AND C.CATALOG_STATUS_CD = 'ACTIVE' "+
           	   "                AND ca.catalog_assoc_cd = 'CATALOG_ACCOUNT' "+
           	   "                AND CCA.CATALOG_ID = C.CATALOG_ID "+
           	   "                AND CC.COST_CENTER_ID = CCA.COST_CENTER_ID "+
           	   "                AND CC.COST_CENTER_STATUS_CD = 'ACTIVE') acccat, "+
           	   tblClwCatalogAssoc +" ca1 "+
           	   "  WHERE     1 = 1 "+
           	   "        AND acccat.bus_entity_id = CA1.BUS_ENTITY_ID "+
           	   "        AND ca1.catalog_assoc_cd = 'CATALOG_ACCOUNT')";     
           
	       log.info("fillTmpCatalog() ==> SQL: "+ sql);
	       stmt = connection.prepareStatement(sql);
           int inserted = stmt.executeUpdate();
           stmt.close();
           log.info("fillTmpCatalog() ==> Table "+ tblName+ ": Inserted "+ inserted + " rows.");
           	
       } catch  (SQLException ex) {
           String msg = "An error occurred at insertion of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }
       
       // Exclude Pollock catalogs
       try {
           sql =
        	  " UPDATE " + tblName + " " +
        	  " SET multy_fl = "+FLAG_POLLOCK +" "+
        	  " WHERE multy_fl = "+FLAG_READY +" and account_catalog_id IN (" +
        	  " SELECT CA.CATALOG_ID "+
        	  " FROM " + tblClwCatalogAssoc + " ca, " + tblClwBusEntityAssoc + " bea "+
        	  " WHERE     CA.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID "+
        	  "      AND CA.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
        	  "      AND BEA.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"' "+
        	  "      AND BEA.BUS_ENTITY2_ID = " +POLLOCK_STORE_ID +" " +
        	  ")  "+
           "" ;
	       log.info("fillTmpCatalog() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int updated = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpCatalog() ==>[Exclude Pollock] Updated "+ updated + " rows.");
       } catch  (SQLException ex) {
           String msg = "An error occurred at update of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }
       
       // Flag catalogs that do not wish to process
       try {
           sql =
        	  " UPDATE " + tblName + " " +
        	  " SET multy_fl = "+FLAG_MULTY +" "+
        	  " WHERE multy_fl = "+FLAG_READY +" and shopping_catalog_id IN (" +
        	  " 	 SELECT SHOPPING_CATALOG_ID  "+
        	  "      FROM "+tblName +" " +
        	  "      GROUP BY SHOPPING_CATALOG_ID  "+
        	  "		 HAVING COUNT ( * ) > 1)  "+
           "" ;
	       log.info("fillTmpCatalog() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int updated = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpCatalog() ==>[Flag Multy] Updated "+ updated + " rows.");
       } catch  (SQLException ex) {
           String msg = "An error occurred at update of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }
       
       // Exclude catalogs that do not have configured sites 
       try {
           sql =
        	  "UPDATE " + tblName + " t " +
        	  " SET multy_fl = "+FLAG_NO_SITES +" "+
        	  " WHERE multy_fl = "+FLAG_MULTY +
        	  " AND NOT EXISTS "+
        	  " (SELECT 1 "+
        	  "   FROM "+
        	  tblClwCatalogAssoc +" ca1, "+
        	  tblClwCatalogAssoc +" ca2, "+
        	  tblClwBusEntityAssoc +" bea "+
        	  "   WHERE 1 = 1 "+
        	  "    AND t.shopping_catalog_id = ca2.catalog_id "+
        	  "    AND t.account_catalog_id = CA1.CATALOG_ID "+
        	  "    AND CA1.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
        	  "    AND CA1.BUS_ENTITY_ID = BEA.BUS_ENTITY2_ID "+
        	  "    AND ca2.bus_entity_id = BEA.BUS_ENTITY1_ID "+
        	  "    AND BEA.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' "+
        	  "    AND ca2.catalog_assoc_cd = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+"') "+
        	  
           "" ;
	       log.info("fillTmpCatalog() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int updated = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpCatalog() ==>[Exclude without sites] Updated "+ updated + " rows.");
       } catch  (SQLException ex) {
           String msg = "An error occurred at update of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }
       
       // Reset flag to 0 for catalogs that really assigned to only 1 account catalog
       try {
           sql =
        	  "UPDATE " + tblName + " " +
        	  " SET multy_fl = "+FLAG_READY+" "+
        	  " WHERE multy_fl = "+FLAG_MULTY +
        	  "  and shopping_catalog_id IN ( " +
        	  "     SELECT SHOPPING_CATALOG_ID "+
              "     FROM (" + tblName + ") WHERE  multy_fl = "+FLAG_MULTY +
              "     GROUP BY SHOPPING_CATALOG_ID " +
              "     HAVING COUNT(*) = 1) "+        	  
           "" ;
	       log.info("fillTmpCatalog() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int updated = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpCatalog() ==>[Reset flag to 0] Updated "+ updated + " rows.");
       } catch  (SQLException ex) {
           String msg = "An error occurred at update of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }
       
       // validation of the incorrect configuration (ONE shopping catalog to MANY account catalogs) 
       // except input 'excludeShoppingCatalogList'. 
       String errMsg= "";
       try {
           sql =
        	  "SELECT * FROM " + tblName + " " +
        	  " WHERE multy_fl = "+FLAG_MULTY+" "+
        	  "  and shopping_catalog_id NOT IN ( " +
        	  (Utility.isSet(excludeShoppingCatalogList)? Utility.toCommaSting(excludeShoppingCatalogList) : "0") +
        	  ")" ;

	       log.info("fillTmpCatalog() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           resSet = stmt.executeQuery();
           
           Map<Integer, HashSet<Integer>> m = new HashMap<Integer, HashSet<Integer>>();
           while (resSet.next()){
        	   Integer shopCat = new Integer(resSet.getInt("shopping_catalog_id"));
        	   Integer accCat = new Integer(resSet.getInt("account_catalog_id"));
        	   HashSet<Integer> s = (HashSet<Integer>)m.get(shopCat);
        	   if (s == null) {
           		   s = new HashSet();
           	       m.put(shopCat, s); 
        	   }
        	   s.add(accCat);
           }

           if (Utility.isSet(m)) {
              errMsg = "There are shopping catalogs that are configured to the multiple account catalogs except specified in the parameters for this process" 
              			+ m.toString();
           }
           stmt.close();
       } catch  (SQLException ex) {
           String msg = "An error occurred at the select from " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
       }
       if (Utility.isSet(errMsg)) {
           throw new Exception ("^clw^"+errMsg+ "^clw^");
       }
      
   
   }
   
   private void fillTmpNewCostCenters (Connection connection, String tempUser, String tblName, String tblClwCatalogStructure, String tblCatalogTemp) throws Exception {
        String sql = null;
       ResultSet resSet = null;
       PreparedStatement stmt = null;
       boolean isExistTempTable = checkTableOnExistence(connection, null, tblName);
       
       /// Deleting of already existing  temporary table 
       if (isExistTempTable) {
    	   execClearTbl(connection, tblName);
       } else {
	       /// Creation of the temporary table 
	       try {
	           sql ="create table " + tblName + " ( "+
				     "CATALOG_STRUCTURE_ID NUMBER NOT NULL, "+
				     "CATALOG_ID NUMBER NOT NULL, "+
				     "CATALOG_STRUCTURE_CD VARCHAR2 (30)  NOT NULL, "+
				     "CATEGORY_ID NUMBER NOT NULL, "+
				     "COST_CENTER_ID NUMBER NOT NULL "+
				     ") ";
		        stmt = connection.prepareStatement(sql);
		        stmt.executeUpdate();
		        stmt.close();
		        log.info("fillTmpNewCostCenters ==> Table "+ tblName+ " created.");
		        /// indexes on the temporary table
		        createIndex(connection, tblName, "I1" , "CATALOG_ID" ) ;
		        createIndex(connection, tblName, "I2" , "CATEGORY_ID" ) ;
	
		    } catch (SQLException ex) {
		        String msg = "An error occurred at creation of table " +
		        	tblName + ". " + ex.getMessage()+
		             "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		    }
       } 
	    /// Population of temporary table
       try {
           sql = "INSERT INTO "+ tblName + " (" +
           "SELECT DISTINCT CS.CATALOG_STRUCTURE_ID, "+
	       "    CS.CATALOG_ID, "+
	       "    CS.CATALOG_STRUCTURE_CD, "+
	       "    CS.ITEM_ID, "+
	       "    CS.COST_CENTER_ID "+
           "FROM "+ tblClwCatalogStructure +" cs, "+ tblCatalogTemp + " tc "+
           "WHERE     CS.CATALOG_ID = tc.account_catalog_id "+
           "  AND CS.CATALOG_STRUCTURE_CD = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "' "+
		   "  AND cs.cost_center_id > 0 "+
		   ")";
           
	       log.info("fillTmpNewCostCenters() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int inserted = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpNewCostCenters ==> Table "+ tblName+ ": Inserted " + inserted + " rows.");
          	
       } catch  (SQLException ex) {
           String msg = "An error occurred at insertion of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }

   }
   
   private void fillTmpCurrentCostCenters (Connection connection, String tempUser, String tblName, String tblClwCatalogStructure, String tblCatalogTemp) throws Exception {
       String sql = null;
       ResultSet resSet = null;
       PreparedStatement stmt = null;
       boolean isExistTempTable = checkTableOnExistence(connection, null, tblName);
       
       /// Deleting of already existing temporary table 
       if (isExistTempTable) {
    	   execClearTbl(connection, tblName);
       } else {
	       /// Creation of the temporary table 
	       try {
	           sql ="create table " + tblName + " ( "+
				     "CATALOG_STRUCTURE_ID NUMBER NOT NULL, "+
				     "CATALOG_ID NUMBER NOT NULL, "+
				     "CATALOG_STRUCTURE_CD VARCHAR2 (30)  NOT NULL, "+
				     "ITEM_ID NUMBER NOT NULL, "+
				     "COST_CENTER_ID NUMBER  "+
				     ") ";
		        stmt = connection.prepareStatement(sql);
		        stmt.executeUpdate();
		        stmt.close();
		        log.info("fillTmpCurrentCostCenters ==> Table "+ tblName+ " created.");
		        /// indexes on the temporary table
		        createIndex(connection, tblName, "I1" , "CATALOG_ID" ) ;
		        createIndex(connection, tblName, "I2" , "ITEM_ID" ) ;
		        
		    } catch (SQLException ex) {
		        String msg = "An error occurred at creation of table " +
		        	tblName + ". " + ex.getMessage()+
		             "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		    }
       }
	    /// Population of temporary table
       try {
           sql = "INSERT INTO "+ tblName + "(" +
           "SELECT DISTINCT * "+
           "FROM (SELECT CS.CATALOG_STRUCTURE_ID, "+
           "             CS.CATALOG_ID, "+
           "             CS.CATALOG_STRUCTURE_CD, "+
           "             CS.ITEM_ID, "+
           "             CS.COST_CENTER_ID "+
           "        FROM "+ tblClwCatalogStructure +" cs, "+ tblCatalogTemp + " tc "+
           "       WHERE CS.CATALOG_ID = tc.shopping_catalog_id "+
           "             AND CS.CATALOG_STRUCTURE_CD = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "' "+
           "      UNION ALL "+
           "      SELECT CS.CATALOG_STRUCTURE_ID, "+
           "             CS.CATALOG_ID, "+
           "             CS.CATALOG_STRUCTURE_CD, "+
           "             CS.ITEM_ID, "+
           "             CS.COST_CENTER_ID "+
           "        FROM "+ tblClwCatalogStructure +" cs, "+ tblCatalogTemp + " tc "+
           "       WHERE CS.CATALOG_ID = tc.shopping_catalog_id "+
           "             AND CS.CATALOG_STRUCTURE_CD = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT+ "' "+
           "      UNION ALL "+
           "      SELECT CS.CATALOG_STRUCTURE_ID, "+
           "             CS.CATALOG_ID, "+
           "             CS.CATALOG_STRUCTURE_CD, "+
           "             CS.ITEM_ID, "+
           "             CS.COST_CENTER_ID "+
           "        FROM "+ tblClwCatalogStructure +" cs, "+ tblCatalogTemp + " tc "+
           "       WHERE CS.CATALOG_ID = tc.account_catalog_id "+
           "             AND CS.CATALOG_STRUCTURE_CD = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "') "+
           ")";
	       log.info("fillTmpCurrentCostCenters() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int inserted = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpCurrentCostCenters() ==> Table "+ tblName+ ": Inserted " + inserted + " rows.");
           	
       } catch  (SQLException ex) {
           String msg = "An error occurred at insertion of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }

   }
   
   private void fillTmpToUpdate (Connection connection, String tempUser, String tblName, String tblNewCostCentersTemp, String tblCurrentCostCentersTemp, String tblCatalogTemp, String tblClwItemAssoc) throws Exception {
       String sql = null;
       ResultSet resSet = null;
       PreparedStatement stmt = null;
       boolean isExistTempTable = checkTableOnExistence(connection, tempUser, tblName);
       
       /// Deleting of already existing table 
       if (isExistTempTable) {
    	   execClearTbl(connection, tblName);
       } else {
       /// Creation of the temporary table 
	       try {
	           sql ="create table " + tblName + " ( "+
	           " CATALOG_STRUCTURE_ID NUMBER NOT NULL, "+
	           " CATALOG_ID NUMBER NOT NULL, "+
	           " CATALOG_STRUCTURE_CD VARCHAR2 (30)  NOT NULL,  "+
	           " CATEGORY_ID NUMBER NOT NULL, "+
	           " ITEM_ID number NOT NULL, "+
	           " OLD_COST_CENTER_ID NUMBER, "+
	           " NEW_COST_CENTER_ID NUMBER "+
	           ") ";
		        stmt = connection.prepareStatement(sql);
		        stmt.executeUpdate();
		        stmt.close();
		        log.info("fillTmpToUpdate() ==> Table "+ tblName+ " created.");
		        /// indexes on temporary table
		        createIndex(connection, tblName, "I1" , "CATALOG_STRUCTURE_ID" ) ;
		        
		    } catch (SQLException ex) {
		        String msg = "An error occurred at creation of table " +
		        	tblName + ". " + ex.getMessage()+
		             "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		    }
       } 
	    /// add records to update cost centers for product items of Account and Shopping catalogs  
       try {
           sql = "INSERT INTO "+ tblName + " (" +
           "SELECT DISTINCT t2.catalog_structure_id, "+
           " t2.catalog_id,  t2.catalog_structure_cd, "+
           " t1.category_id, t2.item_id, "+
           " t2.cost_center_id, t1.cost_center_id "+
		   " FROM " + tblNewCostCentersTemp +" t1 "+
			"  INNER JOIN " + tblCatalogTemp +" tc ON t1.catalog_id = tc.account_catalog_id "+
			"  INNER JOIN " + tblClwItemAssoc +" ia ON t1.catalog_id = IA.CATALOG_ID AND t1.category_id = ia.item2_id, "+
			"      " + tblCurrentCostCentersTemp + " t2 "+
			"  INNER JOIN " + tblClwItemAssoc + " ia1 ON t2.catalog_id = IA1.CATALOG_ID AND t2.item_id = ia1.item1_id "+
			"WHERE 1 = 1 "+
			"  AND tc.multy_fl = 0 "+
			"  AND tc.shopping_catalog_id = t2.catalog_id "+
			"  AND t2.item_id = IA.ITEM1_ID "+
			"  AND t1.category_id = IA1.ITEM2_ID "+
			"  AND t1.cost_center_id != t2.cost_center_id "+
			"  AND t2.catalog_structure_cd = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT+ "' "+
			"  AND IA.ITEM_ASSOC_CD = '"+ RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+ "' "+
			"  AND IA1.ITEM_ASSOC_CD = '"+ RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+ "' "+
	  ")";
           
	       log.info("fillTmpToUpdate() ==> SQL1: "+ sql);
           stmt = connection.prepareStatement(sql);
           int inserted = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpToUpdate() ==> Table "+ tblName+ ": 1)Inserted "+ inserted + " rows.");
           	
       } catch  (SQLException ex) {
           String msg = "An error occurred at insertion 1 of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }
	    /// add records to update cost centers for categories of Shopping catalogs  
       try {
           sql = "INSERT INTO "+ tblName + " (" +
           "SELECT DISTINCT t2.catalog_structure_id, "+
           " t2.catalog_id,  t2.catalog_structure_cd, "+
           " t1.category_id, t2.item_id, "+
           " t2.cost_center_id, t1.cost_center_id "+
		   " FROM " + tblNewCostCentersTemp +" t1 "+
			"  INNER JOIN " + tblCatalogTemp +" tc ON t1.catalog_id = tc.account_catalog_id, "+
			"      " + tblCurrentCostCentersTemp + " t2 "+
			"WHERE 1 = 1 "+
	           " AND tc.multy_fl = 0 "+
	           " AND tc.shopping_catalog_id = t2.catalog_id "+
	           " AND t1.category_id = t2.item_id "+
	           " AND t1.cost_center_id != t2.cost_center_id "+
	           " AND t2.catalog_structure_cd = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "' "+
	  ")";
	       log.info("fillTmpToUpdate() ==> SQL2: "+ sql);
           stmt = connection.prepareStatement(sql);
           int inserted = stmt.executeUpdate();
           stmt.close();
	       log.info("fillTmpToUpdate() ==> Table "+ tblName+ ": 2)Inserted "+ inserted + " rows.");
           	
       } catch  (SQLException ex) {
           String msg = "An error occurred at insertion 2 of records into table " +
           	tblName + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }
       
 
   } 
   
   private int updateCostCenters(Connection connection, String modBy, String tblToUpdateTemp, String tblClwCatalogStructure) throws Exception {
       String sql = null;
       ResultSet resSet = null;
       PreparedStatement stmt = null;
       //checkNotSingleRow ();
       try {
           sql =
        	   "UPDATE " + tblClwCatalogStructure + " cs " +
        	   " SET (cost_center_id,mod_date,mod_by) = " +
        	   "       (SELECT new_cost_center_id, SYSDATE, '"+modBy + "' "+
        	   "          FROM " + tblToUpdateTemp+" t " +
        	   "         WHERE CS.CATALOG_STRUCTURE_ID = t.catalog_structure_id) " +
        	   "WHERE EXISTS (SELECT new_cost_center_id " +
        	   "              FROM " + tblToUpdateTemp+" t " +
        	   "             WHERE CS.CATALOG_STRUCTURE_ID = t.catalog_structure_id)" +
        	   "" ;
           
	       log.info("updateCostCenters() ==> SQL: "+ sql);
           stmt = connection.prepareStatement(sql);
           int updated = stmt.executeUpdate();
           stmt.close();
	       log.info("updateCostCenters() ==> Updated "+ updated + " rows.");
           
           return updated;	
       } catch  (SQLException ex) {
           String msg = "An error occurred  at updating cost centers in the catalog srtructure by values from the table " +
           	tblToUpdateTemp + ". " + ex.getMessage()+
           	"*** Execute the following request to get more information : " +  sql;
           throw new SQLException("^clw^"+msg+ "^clw^");
      	
       }
   }
   public IdVector getMultiproductItems(int storeCatalogId, int pMultiproductId) throws RemoteException {
	    Connection con = null;
	    IdVector itemIds = null;
	    try {
	      con = getConnection();
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_GROUP_ID, pMultiproductId);
	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
	      
	      CatalogStructureDataVector dV = CatalogStructureDataAccess.select(con, dbc);
	      if (Utility.isSet(dV) ){
	    	  Set ids = new HashSet();
	    	  for (Object obj : dV){
	    		  ids.add(((CatalogStructureData)obj).getItemId());
	    	  }
	    	  itemIds = new IdVector();
	    	  itemIds.addAll(ids);
	      }
	      
	    } catch (NamingException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() Naming Exception happened");
	    } catch (SQLException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() SQL Exception happened");
	    } finally {
	      closeConnection(con);
	    }
	    return itemIds;
   }
   public Map getMultiproductToCategoryMap(int catalogId, Set pMultiproductIds) throws RemoteException {
	    Connection con = null;
	    Map result = new HashMap();
	    Map categories = null;
	    IdVector multiproductIds = new IdVector();
	    multiproductIds.addAll(pMultiproductIds);
	    if (multiproductIds.isEmpty()){
	    	return result;
	    }
	    try {
	      con = getConnection();
		  String sql = "select cs.item_group_id as MULTIPRODUCT_ID," +
		  		" item2_id as CATEGORY_ID, \n"+
		  		" (select short_desc from clw_item where item_id = ia.item2_id) as CATEG_NAME \n" +
			 "from clw_catalog_structure cs, \n"+
			" clw_item_assoc  ia \n"+
			" where \n"+
			"cs.catalog_id ="+catalogId +"\n"+
			"and cs.catalog_id = ia.catalog_id  \n"+
			"and cs.item_id = ia.item1_id \n"+
			"and  item_group_id > 0 \n"+
			"and catalog_structure_cd = '"+RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT+"' \n"+
			"and  item_assoc_cd = '"+RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+"'  \n"+
			"group by cs.item_group_id, item2_id";
	      
	      log.info("getMultiproductToCategoryMap ===> SQL=" + sql);
	      Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);
	      while (rs.next()) {
	        int mpId = rs.getInt("MULTIPRODUCT_ID");
	        int categoryId = rs.getInt("CATEGORY_ID");
	        String categoryName = rs.getString("CATEG_NAME");
	        if (result.containsKey(mpId)){
	        	categories = (Map)result.get(mpId);
	        } else {
	        	categories = new HashMap();
	        	result.put(mpId, categories);
	        }
	        categories.put(categoryId , categoryName);
	      }
	      rs.close();
	      stmt.close();
	      
	      
	    } catch (NamingException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() Naming Exception happened");
	    } catch (SQLException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() SQL Exception happened");
	    } finally {
	      closeConnection(con);
	    }
	    log.info("getMultiproductToCategoryMap ===> result=" + result.toString());
	    return result;
  }
   public IdVector getMultiproductItems(int catalogId, int pMultiproductId, IdVector categoryIds) throws RemoteException {
	    Connection con = null;
	    IdVector result = new IdVector();
	    try {
	      con = getConnection();
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_GROUP_ID, pMultiproductId);
	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
	      dbc.addJoinCondition(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.CATALOG_ID);
	      dbc.addJoinCondition(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID, ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM1_ID);
	      dbc.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
	      if (Utility.isSet(categoryIds)){ 
	    	  dbc.addJoinTableOneOf(ItemAssocDataAccess.CLW_ITEM_ASSOC, ItemAssocDataAccess.ITEM2_ID, categoryIds);
	      }
	      CatalogStructureDataVector dV = CatalogStructureDataAccess.select(con, dbc);
	      if (Utility.isSet(dV) ){
	    	  for (Object obj : dV){
	    		  result.add(((CatalogStructureData)obj).getItemId());
	    	  }
	      }
	      
	    } catch (NamingException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() Naming Exception happened");
	    } catch (SQLException exc) {
	      logError("exc.getMessage");
	      exc.printStackTrace();
	      throw new RemoteException("Error. CatalogInformationBean.getCatalogStructure() SQL Exception happened");
	    } finally {
	      closeConnection(con);
	    }
	    log.info("getMultiproductToCategoryMap ===> result=" + result.toString());
	    return result;
	   
   }

  
}
