package com.cleanwise.service.api.session;



/**

 * Title:        CatalogBean

 * Description:  Bean implementation for Catalog Stateless Session Bean

 * Purpose:      Provides access to the methods for establishing and maintaining the catalog..

 * Copyright:    Copyright (c) 2001

 * Company:      CleanWise, Inc.

 * @author       Liang Li, CleanWise, Inc.


 */



import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.CatalogPropertyDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.dao.ContractItemSubstDataAccess;
import com.cleanwise.service.api.dao.CurrencyDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMappingAssocDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.framework.CatalogServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogAssocRequestData;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CatalogContractView;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogPropertyData;
import com.cleanwise.service.api.value.CatalogPropertyDataVector;
import com.cleanwise.service.api.value.CatalogRequestData;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.CatalogStructureRequestData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.CurrencyDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemAssocDataVector;
import com.cleanwise.service.api.value.ItemCatalogAggrView;
import com.cleanwise.service.api.value.ItemCatalogAggrViewVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingAssocData;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.MultiproductView;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderGuideDescData;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;



public class CatalogBean extends CatalogServicesAPI {

  private static final Logger log = Logger.getLogger(CatalogBean.class);

  // Used in the calculation of the automatically generated SKU - it's the

  // ITEM_ID + SKU_MINIMUM

  static public final int SKU_MINIMUM = 10000;



    private static final String className = "CatalogBean";
    private final static Object MUTEX = new Object();



  /**

   *

   */

  public CatalogBean() {}



  /**

   *

   */

  public void ejbCreate() throws CreateException, RemoteException {}



  /**

   * Adds the catalog information values to be used by the request.

   * @param pCatalog  the catalog data.

   * @param request  the catalog request data.

   * @return new CatalogRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogRequestData addCatalog(CatalogData pCatalog, int pStoreId,

                                       String user) throws RemoteException,

    DataNotFoundException, DuplicateNameException {

    CatalogRequestData catalogRD = null;

    Connection con = null;

    try {

      con = getConnection();



      // check for catalog with same name

      DBCriteria crit = new DBCriteria();

      crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

      String catStoreReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  CATALOG_ID, crit);



      crit = new DBCriteria();

      crit.addEqualTo(CatalogDataAccess.SHORT_DESC, pCatalog.getShortDesc());

      crit.addOneOf(CatalogDataAccess.CATALOG_ID, catStoreReq);

      IdVector dups = CatalogDataAccess.selectIdOnly(con, crit);

      if (dups.size() > 0) {

        throw new

          DuplicateNameException(CatalogDataAccess.SHORT_DESC);

      }



      Date date = new Date(System.currentTimeMillis());

      pCatalog.setAddBy(user);

      pCatalog.setModBy(user);



      pCatalog = CatalogDataAccess.insert(con, pCatalog);

      int catalogId = pCatalog.getCatalogId();

      catalogRD = new CatalogRequestData(catalogId);

      addCatalogAssoc(con, catalogId, pStoreId, user);

    } catch (NamingException exc) {

      logError(exc.getMessage());

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalog() Naming Exception happened");

    } catch (SQLException exc) {

      logError(exc.getMessage());

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalog() SQL Exception happened");

    } catch (DuplicateNameException de) {

      throw de;

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.addCatalog() SQL Exception happened");

      }

    }



    return catalogRD;

  }



  /**

   * Updates the catalog information values to be used by the request.

   * @param pUpdateCatalogData  the catalog data.

   * @param pCatalogId the catalog identifier.

   * @return none

   * @throws            RemoteException Required by EJB 1.0

   */

  public void updateCatalog(CatalogData pUpdateCatalogData, String user) throws

    RemoteException, DuplicateNameException {

    Connection con = null;

    try {

      con = getConnection();



      checkConsistence(con, pUpdateCatalogData);



      int catalogId = pUpdateCatalogData.getCatalogId();

      // check for another catalog id with same name

      DBCriteria crit = new DBCriteria();

      crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, catalogId);

      crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                      RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      IdVector storeIdV =

        CatalogAssocDataAccess.selectIdOnly(con,

                                            CatalogAssocDataAccess.BUS_ENTITY_ID,

                                            crit);



      crit = new DBCriteria();

      crit.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, storeIdV);

      String catStoreReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  CATALOG_ID, crit);



      crit = new DBCriteria();

      crit.addEqualTo(CatalogDataAccess.SHORT_DESC,

                      pUpdateCatalogData.getShortDesc());

      crit.addNotEqualTo(CatalogDataAccess.CATALOG_ID, catalogId);

      crit.addOneOf(CatalogDataAccess.CATALOG_ID, catStoreReq);

      IdVector dups = CatalogDataAccess.selectIdOnly(con, crit);

      if (dups.size() > 0) { // Catalog names must be unique.

        throw new

          DuplicateNameException(CatalogDataAccess.SHORT_DESC);

      }



      Date date = new Date(System.currentTimeMillis());

      String userName = pUpdateCatalogData.getModBy();

      pUpdateCatalogData.setModBy(user);

      CatalogDataAccess.update(con, pUpdateCatalogData);

      if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(pUpdateCatalogData.

        getCatalogTypeCd()) ||

          RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING.equals(

        pUpdateCatalogData.getCatalogTypeCd())) {



        crit = new DBCriteria();

        crit.addEqualTo(ContractDataAccess.CATALOG_ID, catalogId);

        ContractDataVector contractDV = ContractDataAccess.select(con, crit);

        ContractData contractD = null;

        if (contractDV.size() > 0) {

          contractD = (ContractData) contractDV.get(0);

        } else {

          contractD = ContractData.createValue();

          contractD.setCatalogId(catalogId);

          contractD.setAddBy(user);

        }



      }

    } catch (DuplicateNameException de) {

      throw de;

    } catch (Exception exc) {

      String mess = exc.getMessage();

      if (mess == null) mess = "";

      if (mess.indexOf("^clw^") < 0) {

        exc.printStackTrace();

      }

      throw new RemoteException(mess);

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.updateCatalog() SQL Exception happened");

      }

    }

  }



  private void checkConsistence(Connection con, CatalogData pCatalog) throws

    Exception {



    if (!RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(pCatalog.

      getCatalogStatusCd())) {

      return;

    }

    DBCriteria dbc;

    if (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(pCatalog.getCatalogTypeCd())) {

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalog.getCatalogId());

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

      String catAccountReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  BUS_ENTITY_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, catAccountReq);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

      dbc.addNotEqualTo(CatalogAssocDataAccess.CATALOG_ID,

                        pCatalog.getCatalogId());

      IdVector catIdV =

        CatalogAssocDataAccess.selectIdOnly(con,

                                            CatalogAssocDataAccess.CATALOG_ID,

                                            dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catIdV);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                     RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

      IdVector crossCatIdV =

        CatalogDataAccess.selectIdOnly(con, CatalogDataAccess.CATALOG_ID, dbc);

      if (crossCatIdV.size() > 0) {

        Integer cId = (Integer) crossCatIdV.get(0);

        String errorMess =

          "^clw^Found another active catalog for the same account. Catalog id: " +

          cId + "^clw^";

        throw new Exception(errorMess);

      }

    }

    if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(pCatalog.getCatalogTypeCd()) ||

        RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING.equals(pCatalog.

      getCatalogTypeCd())) {

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalog.getCatalogId());

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

      String catAccountReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  BUS_ENTITY_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, catAccountReq);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

      dbc.addNotEqualTo(CatalogAssocDataAccess.CATALOG_ID,

                        pCatalog.getCatalogId());

      IdVector catIdV =

        CatalogAssocDataAccess.selectIdOnly(con,

                                            CatalogAssocDataAccess.CATALOG_ID,

                                            dbc);



      dbc = new DBCriteria();

      LinkedList catalogTypeLL = new LinkedList();

      catalogTypeLL.add(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

      catalogTypeLL.add(RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);

      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catIdV);

      dbc.addOneOf(CatalogDataAccess.CATALOG_TYPE_CD, catalogTypeLL);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

      IdVector crossCatIdV =

        CatalogDataAccess.selectIdOnly(con, CatalogDataAccess.CATALOG_ID, dbc);

      if (crossCatIdV.size() > 0) {

        Integer cId = (Integer) crossCatIdV.get(0);

        String errorMess =

          "^clw^Found another active catalog for the same site. Catalog id: " +

          cId + "^clw^";

        throw new Exception(errorMess);

      }

    }

  }



  /**

   * Removes the catalog from database if it does not have structure or associations

   * @param pCatalogId  the catalog id.

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  public void removeCatalog(int pCatalogId, String user) throws RemoteException,

    DataNotFoundException {

    Connection con = null;

    try {

      con = getConnection();

      CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId);

      //Check whether the catalog has associations or not

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      IdVector idV = CatalogAssocDataAccess.selectIdOnly(con, dbc);

      if (idV.size() != 0) {

        throw new RemoteException(

          "Error.CatalogBean.removeCatalog(). The catalog has associations. Catalog id: " +

          pCatalogId);

      }

      //Check whether the catalog has structure or not

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      idV = CatalogStructureDataAccess.selectIdOnly(con, dbc);

      if (idV.size() != 0) {

        throw new RemoteException("Error.CatalogBean.removeCatalog(). The catalog has items or categories. Catalog id: " +

                                  pCatalogId);

      }

      //Remove catalog

      CatalogDataAccess.remove(con, pCatalogId);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalog() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalog() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalog() SQL Exception happened");

      }

    }

  }



  /**

   * Removes the catalog from database with it stucture and associations

   * @param pCatalogId  the catalog id.

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  public void removeCatalogCascade(int pCatalogId, String user) throws

    RemoteException, DataNotFoundException {

    Connection con = null;

    try {

      con = getConnection();



      //Remove catalog associations

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      CatalogAssocDataAccess.remove(con, dbc);



      //Remove major category assoc

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      String categoryReq =

        ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID,

                                               dbc);

      dbc = new DBCriteria();

      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, categoryReq);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

      ItemAssocDataAccess.remove(con, dbc);



      //Remove associations

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      ItemAssocDataAccess.remove(con, dbc);



      //Prepare to remove catalog categories

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

      IdVector categoryIdV = CatalogStructureDataAccess.selectIdOnly(con,

        CatalogStructureDataAccess.ITEM_ID, dbc);



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      dbc.addOneOf(ItemDataAccess.ITEM_ID, categoryIdV);

      DBCriteria dbc1 = dbc;



      //Remove catalog structure

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      CatalogStructureDataAccess.remove(con, dbc, true);



      //Remove catalog categories

      ItemDataAccess.remove(con, dbc1);



      //Remove catalog

      CatalogDataAccess.remove(con, pCatalogId);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogCascade() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

      }

    }

  }



  /**

   * Adds the catalog structure information values to be used by the request.

   * @param pCatalogStructure  the catalog structure data.

   * @param request  the catalog structure request data.

   * @return new CatalogStructureRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogStructureRequestData addCatalogStructure(CatalogStructureData

    pCatalogStructure,

    CatalogStructureRequestData request, String user) throws RemoteException {

    return new CatalogStructureRequestData();

  }



  /**

   * Updates the catalog structure information values to be used by the request.

   * @param pUpdateCatalogStructureData  the catalog structure data.

   * @param pCatalogId the catalog identifier.

   * @param pCatalogStructureId the catalog structure identifier.

   * @return none

   * @throws            RemoteException Required by EJB 1.0

   */

  public void updateCatalogStructure(CatalogStructureData

                                     pUpdateCatalogStructureData,

                                     int pCatalogId, int pCatalogStructureId,

                                     String user) throws RemoteException {



  }





  /**

   * Adds the catalog association information values to be used by the request.

   * @param pCatalogAssoc  the catalog association data.

   * @return new CatalogAssocRequestData()

   * @throws            RemoteException Required by EJB 1.0

   */

  public CatalogAssocRequestData addCatalogAssoc(CatalogAssocData pCatalogAssoc,

                                                 String user) throws

    RemoteException {

    CatalogAssocRequestData catalogAssocRD = null;

    Connection con = null;

    try {

      con = getConnection();

      catalogAssocRD = addCatalogAssoc(con, pCatalogAssoc, user);

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

        throw new RemoteException(

          "Error. CatalogBean.addCatalogAssoc() SQL Exception happened: " +

          exc.getMessage());

      }

    }

    return catalogAssocRD;

  }



  /*

   */

  public CatalogAssocRequestData addCatalogAssoc(Connection con,

                                                 CatalogAssocData pCatalogAssoc,

                                                 String user) throws

    RemoteException, SQLException, DataNotFoundException {

    Date date = new Date(System.currentTimeMillis());

    String userName = pCatalogAssoc.getAddBy();

    if (userName == null || userName.length() == 0) {

      pCatalogAssoc.setAddBy("unknown");

    }

    userName = pCatalogAssoc.getModBy();

    if (userName == null || userName.length() == 0) {

      pCatalogAssoc.setModBy("unknown");

    }



    String assocCd = pCatalogAssoc.getCatalogAssocCd();

    int catalogId = pCatalogAssoc.getCatalogId();

    int busEntityId = pCatalogAssoc.getBusEntityId();

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, catalogId);

    dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, busEntityId);

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, assocCd);

    CatalogAssocDataVector catalogAssocDV = CatalogAssocDataAccess.select(con,

      dbc);

    boolean insertFl = true;

    for (int ii = 0; ii < catalogAssocDV.size(); ii++) {

      CatalogAssocData caD = (CatalogAssocData) catalogAssocDV.get(ii);

      if (ii == 0) {

        insertFl = false;

        pCatalogAssoc.setCatalogAssocId(caD.getCatalogAssocId());

        pCatalogAssoc.setAddBy(caD.getAddBy());

        pCatalogAssoc.setAddDate(caD.getAddDate());

        pCatalogAssoc.setModBy(user);

        CatalogAssocDataAccess.update(con, pCatalogAssoc);

      } else {

        CatalogAssocDataAccess.remove(con, caD.getCatalogAssocId());

      }

    }



    //Check whether the account already has assigned account catalog

    if (RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT.equals(assocCd)) {

      CatalogData catalogD = CatalogDataAccess.select(con, catalogId);

      if (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogD.getCatalogTypeCd()) &&

          RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogD.

        getCatalogStatusCd())) {

        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,

                       pCatalogAssoc.getBusEntityId());

        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                       RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

        String catalogAssocReq =

          CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

          CATALOG_ID, dbc);



        dbc = new DBCriteria();

        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAssocReq);

        //dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

        //   RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                       RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);

        CatalogDataVector cDV = CatalogDataAccess.select(con, dbc);

        String errorMess = null;

        for (int ii = 0; ii < cDV.size(); ii++) {

          CatalogData cD = (CatalogData) cDV.get(ii);

          if (catalogId != cD.getCatalogId()) {

            if (errorMess == null) {

              errorMess = "" + cD.getCatalogId();

            } else {

              errorMess += "; " + cD.getCatalogId();

            }

          }

        }

        if (errorMess != null) {

          errorMess = "^clw^ The account " + busEntityId +

                      " already has catalogs assigned. Catalog id(s): " +

                      errorMess + "^clw^";

          throw new RemoteException(errorMess);



        }

      }

    }

    //Check whether the site already has assigned shopping catalog

    if (RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE.equals(assocCd)) {

      CatalogData catalogD = CatalogDataAccess.select(con, catalogId);

      if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(catalogD.

        getCatalogTypeCd())) { //&&

        //RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogD.getCatalogStatusCd())) {

        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,

                       pCatalogAssoc.getBusEntityId());

        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                       RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

        String catalogAssocReq =

          CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

          CATALOG_ID, dbc);



        dbc = new DBCriteria();

        dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAssocReq);

        //dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

        //   RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

        dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                       RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

        CatalogDataVector cDV = CatalogDataAccess.select(con, dbc);

        String errorMess = null;

        for (int ii = 0; ii < cDV.size(); ii++) {

          CatalogData cD = (CatalogData) cDV.get(ii);

          if (catalogId != cD.getCatalogId()) {

            if (errorMess == null) {

              errorMess = "" + cD.getCatalogId();

            } else {

              errorMess += "; " + cD.getCatalogId();

            }

          }

        }

        if (errorMess != null) {

          errorMess = "^clw^ The site " + busEntityId +

                      " already has catalogs assigned. Catalog id(s): " +

                      errorMess + "^clw^";

          throw new RemoteException(errorMess);



        }

      }

    }



    CatalogAssocData mCatalogAssoc = null;

    if (insertFl) {

      mCatalogAssoc = CatalogAssocDataAccess.insert(con, pCatalogAssoc);

    } else {

      mCatalogAssoc = pCatalogAssoc;

    }

    CatalogAssocRequestData catalogAssocRD = new CatalogAssocRequestData(

      mCatalogAssoc.getCatalogAssocId());

    return catalogAssocRD;

  }



  /**

   * Adds the catalog association information values to be used by the request.

   * @param pCatalogId  the catalog id.

   * @param pBusEntityId  the business entity id.

   * @param user user login id

   * @return new CatalogAssocRequestData()

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public CatalogAssocRequestData addCatalogAssoc(int pCatalogId,

                                                 int pBusEntityId, String user) throws

    RemoteException, DataNotFoundException {

    CatalogAssocRequestData catalogAssocRD = null;

    Connection con = null;

    BusEntityData busEntityD = null;

    try {

      con = getConnection();

      catalogAssocRD = addCatalogAssoc(con, pCatalogId, pBusEntityId, user);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalog() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalog() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.addCatalog() SQL Exception happened");

      }

    }

    return catalogAssocRD;

  }

   public CatalogAssocRequestData addMainDistributorAssocCd(int pCatalogId,int pDistrId, String user)

            throws DataNotFoundException, RemoteException {

        CatalogAssocRequestData catalogAssocRD = null;

        Connection con = null;

        BusEntityData busEntityD = null;

        try {

            con = getConnection();

            catalogAssocRD = addMainDistributorAssocCd(con,pCatalogId,pDistrId,user);

        } catch (NamingException exc) {

            logError("exc.getMessage");

            exc.printStackTrace();

            throw new RemoteException(

                    "Error. CatalogBean.addMainDistributorAssocCdForDistributor() Naming Exception happened");

        } catch (SQLException exc) {

            logError("exc.getMessage");

            exc.printStackTrace();

            throw new RemoteException(

                    "Error. CatalogBean.addMainDistributorAssocCdForDistributor() SQL Exception happened");

        } finally {

            try {

                con.close();

            } catch (SQLException exc) {

                logError("exc.getMessage");

                exc.printStackTrace();

                throw new RemoteException(

                        "Error. CatalogBean.addCatalog() SQL Exception happened");

            }

        }

        return catalogAssocRD;

    }

   private CatalogAssocRequestData addMainDistributorAssocCd(Connection con,

                                                  int pCatalogId,

                                                  int pDistrId, String user) throws

    SQLException, DataNotFoundException, RemoteException {



    CatalogAssocData catalogAssocD = CatalogAssocData.createValue();

    catalogAssocD.setCatalogId(pCatalogId);

    catalogAssocD.setBusEntityId(pDistrId);

    catalogAssocD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);

    catalogAssocD.setModBy(user);

    catalogAssocD.setAddBy(user);

    return addCatalogAssoc(con, catalogAssocD, user);



  }



  private CatalogAssocRequestData addCatalogAssoc(Connection con,

                                                  int pCatalogId,

                                                  int pBusEntityId, String user) throws

    SQLException, DataNotFoundException, RemoteException {

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

    dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);

    IdVector idV = CatalogAssocDataAccess.selectIdOnly(con, dbc);

    if (idV.size() > 0) {

      int id = ((Integer) idV.get(0)).intValue();

      CatalogAssocRequestData catalogAssocRD = new CatalogAssocRequestData(id);

      return catalogAssocRD;

    }

    CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId); //just to be sure that it exists

    BusEntityData busEntityD = BusEntityDataAccess.select(con, pBusEntityId);

    String assocType;

    String busEntityType = busEntityD.getBusEntityTypeCd();

    if (busEntityType.equalsIgnoreCase(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE)) {

      assocType = RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE;

    } else if (busEntityType.equalsIgnoreCase(RefCodeNames.BUS_ENTITY_TYPE_CD.

                                              ACCOUNT)) {

      assocType = RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT;

    } else if (busEntityType.equalsIgnoreCase(RefCodeNames.BUS_ENTITY_TYPE_CD.

                                              SITE)) {

      assocType = RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE;

    } else if (busEntityType.equalsIgnoreCase(RefCodeNames.BUS_ENTITY_TYPE_CD.

                                              DISTRIBUTOR)) {

      assocType = RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR;

    } else {

      throw new RemoteException(

        "Error. CatalogBean.addCatalogAssoc() Unexpected business entity type: " +

        busEntityType);

    }

    CatalogAssocData catalogAssocD = CatalogAssocData.createValue();

    catalogAssocD.setCatalogId(pCatalogId);

    catalogAssocD.setBusEntityId(pBusEntityId);

    catalogAssocD.setCatalogAssocCd(assocType);

    catalogAssocD.setModBy(user);

    catalogAssocD.setAddBy(user);



    return addCatalogAssoc(con, catalogAssocD, user);



  }



  /**

   * Removes the catalog association information values

   * @param pCatalogId  the catalog id.

   * @param pBusEntityId  the business entity id.

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogAssoc(int pCatalogId, int pBusEntityId, String user) throws

    RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);

      CatalogAssocDataAccess.remove(con, dbc);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogAssoc() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogAssoc() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogAssoc() SQL Exception happened");

      }

    }

  }

    public void removeCatalogAccountAssoc(int pCatalogId, int pAccountId, String user) throws

      RemoteException {

      Connection con = null;

      try {

        con = getConnection();

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

        dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountId);

        CatalogAssocDataAccess.remove(con, dbc);

        // remove all sites of account associated with the catalog

        IdVector sites = getSiteIds(pCatalogId, pAccountId);

        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

        dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, sites);

        CatalogAssocDataAccess.remove(con, dbc);


      } catch (NamingException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogAccountAssoc() Naming Exception happened");

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogAccountAssoc() SQL Exception happened");

      } finally {

        try {

          con.close();

        } catch (SQLException exc) {

          logError("exc.getMessage");

          exc.printStackTrace();

          throw new RemoteException(

            "Error. CatalogBean.removeCatalogAccountAssoc() SQL Exception happened");

        }

      }

    }

    private IdVector getSiteIds(int pCatalogId, int pAccountId) throws RemoteException {
        Connection con = null;
        IdVector siteIds = new IdVector();
        try {
          con = getConnection();

         // get all sites assoc with account
          DBCriteria crit = new DBCriteria();
          crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT) ;
          crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);
          IdVector accountSiteIds =
            BusEntityAssocDataAccess.selectIdOnly(con, BusEntityAssocDataAccess.BUS_ENTITY1_ID, crit);
          // get sites assoc with catalog
          crit = new DBCriteria();
          crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                          RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
          crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
          crit.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, accountSiteIds);
          crit.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);

          siteIds = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.BUS_ENTITY_ID, crit);
        } catch (NamingException exc) {
          logError(exc.getMessage());
          exc.printStackTrace();
          throw new RemoteException("Error. CatalogBean.getSiteIds() Naming Exception happened");
        } catch (SQLException exc) {
          logError(exc.getMessage());
          exc.printStackTrace();
          throw new RemoteException("Error. CatalogBean.getSiteIds() SQL Exception happened");
        } finally {
          closeConnection(con);
        }
        return siteIds;

    }


    public void removeCatalogAssoc(int pCatalogAssocId, String user) throws

    RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_ID,pCatalogAssocId);

      CatalogAssocDataAccess.remove(con, dbc);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogAssoc() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogAssoc() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogAssoc() SQL Exception happened");

      }

    }

  }



  /**

   * Removes the catalog site association information values

   * @param pCatalogId  the catalog id.

   * @param pBusEntityId  the business entity id.

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogSiteAssoc(int pCatalogId, int pBusEntityId,

                                     String user) throws RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc = new DBCriteria();

      if (0 != pCatalogId) {

        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      }

      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

      CatalogAssocDataAccess.remove(con, dbc);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogSiteAssoc() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogSiteAssoc() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogSiteAssoc() SQL Exception happened");

      }

    }

  }





  /**

   * Updates the catalog association information values to be used by the request.

   * @param pUpdateCatalogAssocData  the catalog association data.

   * @param pCatalogId the catalog identifier.

   * @param pBusEntityId the business entity identifier.

   * @param pCatalogAssocId the catalog association identifier.

   * @return none

   * @throws            RemoteException Required by EJB 1.0

   */

  public void updateCatalogAssoc(CatalogAssocData pUpdateCatalogAssocData,

                                 int pCatalogId, int pBusEntityId,

                                 int pCatalogAssocId) throws RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      pUpdateCatalogAssocData.setCatalogAssocId(pCatalogAssocId);

      pUpdateCatalogAssocData.setCatalogId(pCatalogId);

      pUpdateCatalogAssocData.setBusEntityId(pBusEntityId);

      CatalogAssocDataAccess.update(con, pUpdateCatalogAssocData);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.updateCatalogAssoc() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.updateCatalogAssoc() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.updateCatalogAssoc() SQL Exception happened");

      }

    }



  }



  /**

   * Adds the catalog category information. Two tables affected:

   * CLW_ITEM, CLW_CATALOG_STRUCTURE

   * @param pCatalogCategoryData  new CatalogCategoryData object.

   * @param pParentCategoryId, or 0 if it is top level category.

   * @throws            RemoteException Required by EJB 1.0

   */

  /*

    public void addCatalogCategory(CatalogCategoryData pCatalogCategoryData,

    int pParentCategoryId)

    throws RemoteException

    {

    Connection con=null;

    try {

    con = getConnection();

    Date date = new Date(System.currentTimeMillis());

    String userName=null;

    ItemData itemD= pCatalogCategoryData.getItemData();

    //Check parent category existance

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(ItemDataAccess.ITEM_ID,pParentCategoryId);

   dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,RefCodeNames.ITEM_TYPE_CD.CATEGORY);



    ItemData itemD = ItemDataAccess.select(con,pParentCategoryId);

    if(itemD.getItemTypeCd()!=RefCodeNames.ITEM_TYPE_CD.CATEGORY) {

    throw new RemoteException("Error. CatalogBean.addCatalogCategory() Category not found: "+pParentCategoryId);

    }



    ItemData itemD= pCatalogCategoryData.getItemData();





    ItemData itemD1 = ItemDataAccess.insert(con,pUpdateCatalogData);



    pUpdateCatalogData.setModDate(date);

    ItemDataAccess.insert(con,pUpdateCatalogData);

    }catch(DataNotFoundException exc) {

    logError("exc.getMessage");

    exc.printStackTrace();

    throw new RemoteException("Error. CatalogBean.addCatalogCategory() Category not found: "+pParentCategoryId);

    }catch(SQLException exc) {

    }catch(NamingException exc) {

    logError("exc.getMessage");

    exc.printStackTrace();

    throw new RemoteException("Error. CatalogBean.addCatalogCategory() Naming Exception happened");

    }catch(SQLException exc) {

    logError("exc.getMessage");

    exc.printStackTrace();

    throw new RemoteException("Error. CatalogBean.addCatalogCategory() SQL Exception happened");

    }finally{

    try {

    con.close();

    }catch (SQLException exc) {

    logError("exc.getMessage");

    exc.printStackTrace();

    throw new RemoteException("Error. CatalogBean.addCatalogCategory() SQL Exception happened");

    }

    }

    }

   */



  /**

   * Adds or updates product for catalog- without deleting existing item meta info

   * @param pCatalogId  the catalog id.

   * @param productD  ProductData object

   * @param user user login id

   * @return ProductData

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ProductData saveMasterCatalogProduct(int pCatalogId, ProductData productD,

          String user) throws RemoteException,DataNotFoundException {



	  Connection con = null;

	  DBCriteria dbc;

	  try {

		  con = getConnection();

		  //Check catalog existance

	      dbc = new DBCriteria();

	      dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, pCatalogId);

	      IdVector idV = CatalogDataAccess.selectIdOnly(con, dbc);

	      if (idV.size() == 0) {

	        throw new DataNotFoundException(

	          "Error. CatalogBean.saveMasterCatalogProduct(). No catalog found. Catalog id: " +

	          pCatalogId);

	      }



	      //Insert/Update Item

	      ItemData itemD = productD.getItemData();

	      if (itemD == null) {

	        throw new DataNotFoundException("Error. CatalogBean.saveMasterCatalogProduct(). No ItemData object found in the product ");

	      }

	      int skuNum = itemD.getSkuNum();

	      boolean clwSwitch = Utility.getClwSwitch();



	      itemD.setModBy(user);

	      if (itemD.getItemId() == 0) {

	    	  itemD.setAddBy(user);

	    	  itemD = ItemDataAccess.insert(con, itemD);



	    	  itemD.setSkuNum(itemD.getItemId() + SKU_MINIMUM);

	    	  ItemDataAccess.update(con, itemD);



	    	  productD.setItemData(itemD);

	      } else {

	    	  ItemDataAccess.update(con, itemD);

	      }

	      int productId = itemD.getItemId();



	      //update meta info

	      ItemMetaDataVector imDV = productD.getProductAttributes();

	      dbc = new DBCriteria();

	      dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, productId);

	      ItemMetaDataVector existingItemMeta = ItemMetaDataAccess.select(con, dbc);



	      if(existingItemMeta!=null && existingItemMeta.size()>0){

	    	  //update

	    	  for (int ii = 0; ii < imDV.size(); ii++) {

		    	  ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);



		    	  String nameVal = itemMetaD.getNameValue();

		    	  String val = itemMetaD.getValue();

		    	  boolean found = false;

		    	  ItemMetaData returnMetaD = ItemMetaData.createValue();



		    	  for(int jj=0; jj<existingItemMeta.size(); jj++){

		    		  ItemMetaData existingMetaD = (ItemMetaData)existingItemMeta.get(jj);

		    		  if(existingMetaD.getNameValue().equalsIgnoreCase(nameVal)){

		    			  found = true;

		    			  returnMetaD = existingMetaD;

		    			  break;

		    		  }

		    	  }

		    	  if(found){

		    		  if(!returnMetaD.getValue().equalsIgnoreCase(val)){

		    			  returnMetaD.setValue(itemMetaD.getValue());

		    			  returnMetaD.setModBy(user);

		    			  ItemMetaDataAccess.update(con, returnMetaD);

		    		  }

		    	  }else{

		    		  //new meta attribute

		    		  itemMetaD = ProductDAO.saveItemMetaInfo(con, productId, user,itemMetaD);

		    		  productD.setItemMeta(itemMetaD, nameVal);

		    	  }



	    	  }



	      }else{

	    	  //new item meta

	    	  for (int ii = 0; ii < imDV.size(); ii++) {

		    	  ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);



		    	  if (itemMetaD != null && itemMetaD.getValue().trim().length() > 0) {

		    		  itemMetaD = ProductDAO.saveItemMetaInfo(con, productId, user,itemMetaD);

		    		  productD.setItemMeta(itemMetaD, itemMetaD.getNameValue());

		    	  }

	    	  }

	      }



	      //Edit manufacturer mapping if was changed

	      ItemMappingData manufacturerMappingD = productD.getManuMapping();

	      if (manufacturerMappingD != null) {

	    	  int manufacturerId = manufacturerMappingD.getBusEntityId();

	    	  dbc = new DBCriteria();

	    	  dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);

	    	  dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

	    			  RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

	    	  ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,dbc);

	    	  if (itemMappingDV.size() > 1) {

	    		  throw new RemoteException("Error. CatalogBean.saveMasterCatalogProduct(). Product has more then one manufacturer. Product id: " +

	                                    productId);

	    	  }

	    	  if (itemMappingDV.size() == 0) {

	    		  manufacturerMappingD.setAddBy(user);

	    		  manufacturerMappingD.setModBy(user);

	    		  manufacturerMappingD = ItemMappingDataAccess.insert(con,manufacturerMappingD);

	    		  productD.setManuMapping(manufacturerMappingD);

	    	  }

	    	  if (itemMappingDV.size() == 1) {

	    		  ItemMappingData imD = (ItemMappingData) itemMappingDV.get(0);

	    		  if ((imD.getItemNum() == null &&

	    				  manufacturerMappingD.getItemNum() != null) ||

	    				  (imD.getItemNum() != null &&

	    						  !imD.getItemNum().equals(manufacturerMappingD.getItemNum())) ||

	    						  imD.getBusEntityId() != manufacturerMappingD.getBusEntityId()) {

	    			  imD.setItemNum(manufacturerMappingD.getItemNum());

	    			  imD.setBusEntityId(manufacturerMappingD.getBusEntityId());

	    			  imD.setModBy(user);

	    			  ItemMappingDataAccess.update(con, imD);

	    		  }

	    	  }

	      }

	      //Save distributor mapping if changed

	      ItemMappingDataVector itemMappingDV = productD.getDistributorMappings();

	      int[] save = new int[itemMappingDV.size()];

	      for (int ii = 0; ii < save.length; ii++) {

	    	  save[ii] = 2;

	      }

	      dbc = new DBCriteria();

	      dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);

	      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

	                     RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

	      ItemMappingDataVector itemMappingBaseDV = ItemMappingDataAccess.select(con, dbc);

	      IdVector removeV = new IdVector();

	      for (int ii = 0; ii < itemMappingBaseDV.size(); ii++) {

	    	  ItemMappingData itemMappingBaseD = (ItemMappingData) itemMappingBaseDV.get(ii);

	    	  int id = itemMappingBaseD.getBusEntityId();

	    	  int jj = 0;



	    	  if (null == itemMappingBaseD.getItemNum()) {

	    		  itemMappingBaseD.setItemNum("");

	    	  }

	    	  if (null == itemMappingBaseD.getItemUom()) {

	    		  itemMappingBaseD.setItemUom("");

	    	  }

	    	  if (null == itemMappingBaseD.getItemPack()) {

	    		  itemMappingBaseD.setItemPack("");

	    	  }



	    	  for (; jj < itemMappingDV.size(); jj++) {

	    		  ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);

	    		  if (itemMappingD.getBusEntityId() == id) {

	    			  if (itemMappingBaseD.getItemNum().equals(itemMappingD.getItemNum())

	    					  && itemMappingBaseD.getItemUom().equals(itemMappingD.getItemUom())

	    					  && itemMappingBaseD.getItemPack().equals(itemMappingD.getItemPack())) {

	    				  save[jj] = 0;

	    			  } else {

	    				  save[jj] = 1;

	    			  }

	    			  break;

	    		  }

	    	  }

	    	  if (jj == itemMappingDV.size()) {

	    		  removeV.add(new Integer(id));

	    	  }

	      }

	      //add/update

	      for (int ii = 0; ii < itemMappingDV.size(); ii++) {

	    	  if (save[ii] == 1) {

	    		  ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);

	    		  itemMappingD.setModBy(user);

	    		  ItemMappingDataAccess.update(con, itemMappingD);

	    	  } else if (save[ii] == 2) {

	    		  ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);

	    		  itemMappingD.setItemId(productId);

	    		  itemMappingD.setAddBy(user);

	    		  itemMappingD.setModBy(user);

	    		  ItemMappingDataAccess.insert(con, itemMappingD);

	    	  }

	      }

	      //delete

	      dbc = new DBCriteria();

	      dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);

	      dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, removeV);

	      ItemMappingDataAccess.remove(con, dbc);



	      //Catalog mapping

	      CatalogStructureData catalogStructureD = productD.getCatalogStructure();

	      if (catalogStructureD == null) {

	    	  productD.setCatalogStructure(pCatalogId);

	    	  catalogStructureD = productD.getCatalogStructure();

	      }

	      catalogStructureD.setModBy(user);

	      catalogStructureD.setItemId(productId);



	      CatalogData catD = CatalogDataAccess.select(con, pCatalogId);

	      //Specifically for store catalog

	      if (RefCodeNames.CATALOG_TYPE_CD.STORE.equals(catD.getCatalogTypeCd())) {

	    	  catalogStructureD.setCustomerSkuNum(""+productD.getItemData().getSkuNum());

	      }else{

	    	  catalogStructureD.setCustomerSkuNum(productD.getCustomerSkuNum());

	      }



	      dbc = new DBCriteria();

	      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, productId);

	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

	      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

	                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

	      IdVector idV1 = CatalogStructureDataAccess.selectIdOnly(con, dbc);

	      if (idV1.size() > 1) {

	        throw new RemoteException("Error. CatalogBean.saveMasterCatalogProduct(). Catalog and product  have duplicated relation. Catalog id: " +

	                                  pCatalogId + ". Product id: " + productId);

	      }

	      if (idV1.size() == 0) {

	    	  catalogStructureD.setAddBy(user);

	    	  catalogStructureD = CatalogStructureDataAccess.insert(con,

	    			  catalogStructureD, true);

	    	  productD.setCatalogStructure(catalogStructureD);

	      }

	      if (idV1.size() == 1) {

	        CatalogStructureData csD = CatalogStructureDataAccess.select(con,

	          ((Integer) idV1.get(0)).intValue());

	        String shortDesc = csD.getShortDesc();

	        if (shortDesc == null) shortDesc = "";

	        String shortDescNew = catalogStructureD.getShortDesc();

	        if (shortDescNew == null || shortDescNew.equals("")) {

	        	shortDescNew = shortDesc;

	        	catalogStructureD.setShortDesc(shortDesc);

	        }

	        String customerSkuNum = csD.getCustomerSkuNum();

	        if (customerSkuNum == null) customerSkuNum = "";

	        String customerSkuNumNew = catalogStructureD.getCustomerSkuNum();

	        /*if (customerSkuNumNew == null || customerSkuNumNew.equals("") ){

	        	customerSkuNumNew = customerSkuNum;

	        	catalogStructureD.setCustomerSkuNum(customerSkuNum);

	        }*/



	        if (csD.getItemId() != catalogStructureD.getItemId() ||

	            csD.getBusEntityId() != catalogStructureD.getBusEntityId() ||

	            csD.getCatalogId() != catalogStructureD.getCatalogId() ||

	            csD.getCatalogStructureCd().equals(catalogStructureD.

	                                               getCatalogStructureCd()) == false ||

	            shortDesc.equals(shortDescNew) == false ||

	            customerSkuNum.equals(customerSkuNumNew) == false) {

		          csD.setAddBy(csD.getAddBy());

		          csD.setItemId(catalogStructureD.getItemId());

		          csD.setBusEntityId(catalogStructureD.getBusEntityId());

		          csD.setCatalogStructureCd(catalogStructureD.getCatalogStructureCd());

		          csD.setShortDesc(catalogStructureD.getShortDesc());

		          csD.setCustomerSkuNum(catalogStructureD.getCustomerSkuNum());



		          CatalogStructureDataAccess.update(con, csD, true);

		          productD.setCatalogStructure(csD);

	          }

	      }

	      if (productD.getCatalogCategories() != null) {
	    	  saveProductCategory(pCatalogId, productD, user, con);
          }



	  } catch (NamingException exc) {

		  logError("exc.getMessage");

		  exc.printStackTrace();

		  throw new RemoteException(

	      "Error. CatalogBean.saveMasterCatalogProduct() Naming Exception happened");

	  } catch (SQLException exc) {

		  logError("exc.getMessage");

		  exc.printStackTrace();

		  throw new RemoteException(

		  "Error. CatalogBean.saveMasterCatalogProduct() SQL Exception happened");

	  } catch (RemoteException exc) {

		  throw exc;

	  } catch (Exception exc) {

		  logError("exc.getMessage");

		  exc.printStackTrace();

		  throw new RemoteException("Error. CatalogBean.saveMasterCatalogProduct(). " +

				  exc.getMessage());

	  } finally {

		  try {

			  con.close();

		  } catch (SQLException exc) {

			  logError("exc.getMessage");

			  exc.printStackTrace();

			  throw new RemoteException(

			  "Error. CatalogBean.saveMasterCatalogProduct() SQL Exception happened");

		  }

	  }

	  return productD;

  }

  /**

   * Adds or updates product for catalog

   * @param pCatalogId  the catalog id.

   * @param productD  ProductData object

   * @param user user login id

   * @return ProductData

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ProductData saveCatalogProduct(int pCatalogId, ProductData productD,

                                        String user) throws RemoteException,

    DataNotFoundException {

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();



      //Check catalog existance

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, pCatalogId);

      IdVector idV = CatalogDataAccess.selectIdOnly(con, dbc);

      if (idV.size() == 0) {

        throw new DataNotFoundException(

          "Error. CatalogBean.saveCatalogProduct(). No catalog found. Catalog id: " +

          pCatalogId);

      }



      //Insert/Update Item

      ItemData itemD = productD.getItemData();

      if (itemD == null) {

        throw new DataNotFoundException("Error. CatalogBean.saveCatalogProduct(). No ItemData object found in the product ");

      }

      int skuNum = itemD.getSkuNum();

      boolean clwSwitch = Utility.getClwSwitch();

      // Old logic. Commented by YK 06/06/2008

      /*

      if (!clwSwitch) {

        if (skuNum <= 0) {

          String errorMess = "Data error. Sku already exists. Sku: " + skuNum +

                             ";";

          throw new RemoteException(errorMess);

        }

        //Check wether sku already exists

        DBCriteria dbc1 = new DBCriteria();

        dbc1.addEqualTo(ItemDataAccess.SKU_NUM, skuNum);

        dbc1.addOrderBy(ItemDataAccess.ITEM_ID);

        ItemDataVector iDV = ItemDataAccess.select(con, dbc1);

        if (iDV.size() > 0) {

          for (int ii = 0; ii < iDV.size(); ii++) {

            ItemData iD = (ItemData) iDV.get(iDV.size() - 1);

            if (iD.getItemId() != itemD.getItemId()) {

              Date addDate = iD.getAddDate();

              SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

              String addDateS = sdf.format(addDate);

              String errorMess = "Data error. Sku already exists. Sku: " +

                                 skuNum + " Entered " + addDateS + " by " +

                                 iD.getAddBy() + ";";

              throw new RemoteException(errorMess);

            }

          }

        }

      }

      */

      itemD.setModBy(user);

      if (itemD.getItemId() == 0) {

        itemD.setAddBy(user);

        itemD = ItemDataAccess.insert(con, itemD);

        //if (clwSwitch) { Old logic. Commented by YK 06/06/2008

        //  if (skuNum == 0) {

            itemD.setSkuNum(itemD.getItemId() + SKU_MINIMUM);

            ItemDataAccess.update(con, itemD);

        //  }

        //}

        productD.setItemData(itemD);

      } else {

        ItemDataAccess.update(con, itemD);

      }

      int productId = itemD.getItemId();



      //Remove and insert attributes

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, productId);

      ItemMetaDataAccess.remove(con, dbc);

      ItemMetaDataVector imDV = productD.getProductAttributes();

      for (int ii = 0; ii < imDV.size(); ii++) {

        ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);



        if (itemMetaD != null && itemMetaD.getValue().trim().length() > 0) {

          itemMetaD = ProductDAO.saveItemMetaInfo(con, productId, user,

                                                  itemMetaD);

          productD.setItemMeta(itemMetaD, itemMetaD.getNameValue());

        }

      }



      //Edit manufacturer mapping if was changed

      ItemMappingData manufacturerMappingD = productD.getManuMapping();

      if (manufacturerMappingD != null) {

        int manufacturerId = manufacturerMappingD.getBusEntityId();

        dbc = new DBCriteria();

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                       RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

        ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,

          dbc);

        if (itemMappingDV.size() > 1) {

          throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). Product has more then one manufacturer. Product id: " +

                                    productId);

        }

        if (itemMappingDV.size() == 0) {

          manufacturerMappingD.setAddBy(user);

          manufacturerMappingD.setModBy(user);

          manufacturerMappingD = ItemMappingDataAccess.insert(con,

            manufacturerMappingD);

          productD.setManuMapping(manufacturerMappingD);

        }

        if (itemMappingDV.size() == 1) {

          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(0);

          if ((imD.getItemNum() == null &&

               manufacturerMappingD.getItemNum() != null) ||

              (imD.getItemNum() != null &&

               !imD.getItemNum().equals(manufacturerMappingD.getItemNum())) ||

              imD.getBusEntityId() != manufacturerMappingD.getBusEntityId()) {

            imD.setItemNum(manufacturerMappingD.getItemNum());

            imD.setBusEntityId(manufacturerMappingD.getBusEntityId());

            imD.setModBy(user);

            ItemMappingDataAccess.update(con, imD);

          }

        }

      }

      //Save distributor mapping if changed

      ItemMappingDataVector itemMappingDV = productD.getDistributorMappings();

      int[] save = new int[itemMappingDV.size()];

      for (int ii = 0; ii < save.length; ii++) {

        save[ii] = 2;

      }

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);

      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                     RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

      ItemMappingDataVector itemMappingBaseDV = ItemMappingDataAccess.select(con, dbc);

      IdVector removeV = new IdVector();

      for (int ii = 0; ii < itemMappingBaseDV.size(); ii++) {

        ItemMappingData itemMappingBaseD = (ItemMappingData) itemMappingBaseDV.get(ii);

        int id = itemMappingBaseD.getBusEntityId();

        int jj = 0;



        if (null == itemMappingBaseD.getItemNum()) {

          itemMappingBaseD.setItemNum("");

        }

        if (null == itemMappingBaseD.getItemUom()) {

          itemMappingBaseD.setItemUom("");

        }

        if (null == itemMappingBaseD.getItemPack()) {

          itemMappingBaseD.setItemPack("");

        }



        for (; jj < itemMappingDV.size(); jj++) {

          ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);

          if (itemMappingD.getBusEntityId() == id) {

            if (itemMappingBaseD.getItemNum().equals(itemMappingD.getItemNum())

                && itemMappingBaseD.getItemUom().equals(itemMappingD.getItemUom())

                && itemMappingBaseD.getItemPack().equals(itemMappingD.getItemPack())) {

              save[jj] = 0;

            } else {

              save[jj] = 1;

            }

            break;

          }

        }

        if (jj == itemMappingDV.size()) {

          removeV.add(new Integer(id));

        }

      }

      //add/update

      for (int ii = 0; ii < itemMappingDV.size(); ii++) {

        if (save[ii] == 1) {

          ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);

          itemMappingD.setModBy(user);

          ItemMappingDataAccess.update(con, itemMappingD);

        } else if (save[ii] == 2) {

          ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);

          itemMappingD.setItemId(productId);

          itemMappingD.setAddBy(user);

          itemMappingD.setModBy(user);

          ItemMappingDataAccess.insert(con, itemMappingD);

        }

      }

      //delete

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);

      dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, removeV);

      try {
        ItemMappingDataAccess.remove(con, dbc);
      }
      catch (SQLException ex) {
        log.info("[CatalogBean]--SQL EXCEPTION-->>>for :<"+productD.getShortDesc()+ "> -->" + ex.getMessage());
      }



      //Catalog mapping

      CatalogStructureData catalogStructureD = productD.getCatalogStructure();

      if (catalogStructureD == null) {

        productD.setCatalogStructure(pCatalogId);

        catalogStructureD = productD.getCatalogStructure();

      }

      catalogStructureD.setModBy(user);

      catalogStructureD.setItemId(productId);



      //catalogStructureD.setCustomerSkuNum(""+productD.getItemData().getSkuNum());

      //specifically for NSC PC loader - account and shopping catalogs only

      catalogStructureD.setCustomerSkuNum(productD.getCustomerSkuNum());



      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, productId);

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

      IdVector idV1 = CatalogStructureDataAccess.selectIdOnly(con, dbc);

      if (idV1.size() > 1) {

        throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). Catalog and product  have duplicated relation. Catalog id: " +

                                  pCatalogId + ". Product id: " + productId);

      }

      if (idV1.size() == 0) {

        catalogStructureD.setAddBy(user);

        catalogStructureD = CatalogStructureDataAccess.insert(con,

          catalogStructureD, true);

        productD.setCatalogStructure(catalogStructureD);

      }

      if (idV1.size() == 1) {

        CatalogStructureData csD = CatalogStructureDataAccess.select(con,

          ((Integer) idV1.get(0)).intValue());

        String shortDesc = csD.getShortDesc();

        if (shortDesc == null) shortDesc = "";

        String shortDescNew = catalogStructureD.getShortDesc();

        if (shortDescNew == null || shortDescNew.equals("")) {

        	shortDescNew = shortDesc;

        	catalogStructureD.setShortDesc(shortDesc);

        }

        String customerSkuNum = csD.getCustomerSkuNum();

        if (customerSkuNum == null) customerSkuNum = "";

        String customerSkuNumNew = catalogStructureD.getCustomerSkuNum();

        /*if (customerSkuNumNew == null || customerSkuNumNew.equals("") ){

        	customerSkuNumNew = customerSkuNum;

        	catalogStructureD.setCustomerSkuNum(customerSkuNum);

        }*/



        if (csD.getItemId() != catalogStructureD.getItemId() ||

            csD.getBusEntityId() != catalogStructureD.getBusEntityId() ||

            csD.getCatalogId() != catalogStructureD.getCatalogId() ||

            csD.getCatalogStructureCd().equals(catalogStructureD.

                                               getCatalogStructureCd()) == false ||

            shortDesc.equals(shortDescNew) == false ||

            customerSkuNum.equals(customerSkuNumNew) == false

          ) {

          csD.setAddBy(csD.getAddBy());

          csD.setItemId(catalogStructureD.getItemId());

          csD.setBusEntityId(catalogStructureD.getBusEntityId());

          csD.setCatalogStructureCd(catalogStructureD.getCatalogStructureCd());

          csD.setShortDesc(catalogStructureD.getShortDesc());

          csD.setCustomerSkuNum(catalogStructureD.getCustomerSkuNum());



          CatalogStructureDataAccess.update(con, csD, true);

          productD.setCatalogStructure(csD);        }

      }



      if (productD.getCatalogCategories() != null)

    	  saveProductCategory(pCatalogId, productD, user, con);



    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.saveCatalogProduct() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.saveCatalogProduct() SQL Exception happened");

    } catch (RemoteException exc) {

      throw exc;

    } catch (Exception exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). " +

                                exc.getMessage());

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.saveCatalogProduct() SQL Exception happened");

      }

    }

    return productD;

  }



  public CatalogStructureData saveCatalogStructure(CatalogStructureData csd) throws RemoteException {

	 Connection con = null;

	 try {

		  con = getConnection();

		  return CatalogStructureDataAccess.insert(con, csd);

	} catch (Exception exc) {

      exc.printStackTrace();

      throw new RemoteException(exc.getMessage(), exc);

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        exc.printStackTrace();

        throw new RemoteException(exc.getMessage(), exc);

      }

    }

  }



  /**

   * The distributor of every product in the catalog will be updated

   * to the given distributor.  The distributor must be a distributor

   * for the catalog and a distributor of the product.  If it is not,

   * the product distributor will not be changed.

   * @param pCatalogId  the catalog id.

   * @param pDistributorId the distributor id.

   * @param pOldDistId distributor to be replaced or 0 (item should not have addegned

   * distibutor) or -1 (all distributors)

   * @param pUser the login user name

   * @return items, which the distributor does not carry

   * @throws            RemoteException

   */

  public ItemDataVector updateProductDistributor(int pCatalogId,

                                                 int pDistributorId,

                                                 int pOldDistId,

                                                 String pUser) throws

    RemoteException, DataNotFoundException {

    ItemDataVector missedItemDV = null;

    Connection conn = null;

    try {

      conn = getConnection();



      // get new distributor item ids

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                     RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

      dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID,

                     pDistributorId);

      String itemDistReq = ItemMappingDataAccess.getSqlSelectIdOnly(

        ItemMappingDataAccess.ITEM_ID, dbc);



      //  get item ids should be moved

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

      if (pOldDistId == 0) {

        dbc.addIsNull(CatalogStructureDataAccess.BUS_ENTITY_ID);

      }

      if (pOldDistId > 0) {

        dbc.addEqualTo(CatalogStructureDataAccess.BUS_ENTITY_ID, pOldDistId);

      }

      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

      IdVector itemIdV = CatalogStructureDataAccess.selectIdOnly(conn,

        CatalogStructureDataAccess.ITEM_ID, dbc);



      // get catalog structure objects to move

      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemDistReq);

      CatalogStructureDataVector catalogStructureDV =

        CatalogStructureDataAccess.select(conn, dbc);

      // move to new distributor

      IdVector missedItemIdV = new IdVector();

      int jj = 0;

      for (int ii = 0; ii < catalogStructureDV.size(); ii++) {

        CatalogStructureData csD = (CatalogStructureData) catalogStructureDV.

                                   get(ii);

        int itemId = csD.getItemId();

        for (; jj < itemIdV.size(); jj++) {

          Integer iIdI = (Integer) itemIdV.get(jj);

          if (iIdI.intValue() < itemId) {

            missedItemIdV.add(iIdI);

          }

          if (iIdI.intValue() == itemId) {

            jj++;

            break;

          }

          if (iIdI.intValue() > itemId) {

            String errorMess = "Logic error. Should never happen";

          }

        }

        csD.setModBy(pUser);

        csD.setBusEntityId(pDistributorId);

        CatalogStructureDataAccess.update(conn, csD, true);

      }

      for (; jj < itemIdV.size(); jj++) {

        Integer iIdI = (Integer) itemIdV.get(jj);

        missedItemIdV.add(iIdI);

      }

      // Get items, which could not be moved

      dbc = new DBCriteria();

      dbc.addOneOf(ItemDataAccess.ITEM_ID, missedItemIdV);

      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      missedItemDV = ItemDataAccess.select(conn, dbc);

      /*

           crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

             RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

           StringBuffer buf = new StringBuffer();

           buf.append(BusEntityDataAccess.BUS_ENTITY_ID);

           buf.append(" IN (SELECT ");

           buf.append(CatalogAssocDataAccess.BUS_ENTITY_ID);

           buf.append(" FROM ");

           buf.append(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);

           buf.append(" WHERE ");

           buf.append(CatalogDataAccess.CATALOG_ID);

           buf.append(" = ");

           buf.append(pCatalogId);

           buf.append(" AND ");

           buf.append(CatalogAssocDataAccess.CATALOG_ASSOC_CD);

           buf.append(" = '");

           buf.append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);

           buf.append("')");

           crit.addCondition(buf.toString());

           IdVector distIdV = BusEntityDataAccess.selectIdOnly(conn, crit);



           // if requested distributor not a distributor of the catalog,

           // there's nothing to do

           if (!distIdV.contains(new Integer(pDistributorId))) {

        return;

           }



           // This is horrible, should be a more direct way of

           // getting items that are able to be fulfilled by

           // the specified distributor, and updating the distributor

           // For now, will do use the available methods.  This method

           // is not likely to be often used anyway.

                  APIAccess apiAccess = getAPIAccess();

           DistributorData newDistributor = null;

           CatalogInformation cati = apiAccess.getCatalogInformationAPI();

           IdVector prodIdV = cati.searchCatalogProducts(pCatalogId);

             Iterator iter = prodIdV.iterator();

             while (iter.hasNext()) {

          ProductData product =

            cati.getCatalogClwProduct

            (pCatalogId, ((Integer)iter.next()).intValue());



        BusEntityData dist = product.getCatalogDistributor();

        // if distributor needs changing...

        if (dist == null ||

            dist.getBusEntityId() != pDistributorId) {

            ItemMappingDataVector imv =

         product.getDistributorMappings();

            Iterator imIter = imv.iterator();

            boolean distOK = false;

            while (imIter.hasNext() && !distOK) {

         ItemMappingData imd = (ItemMappingData)imIter.next();

         if (imd.getBusEntityId() == pDistributorId) {

             distOK = true;

         }

            }

            // if distOK then that means this product is carried

            // by the specified distributor and we can set it's

            // distributor to the specified one.

            if (distOK) {

         // first time we'll need to get distributor data

         // (ideally the distributor bean has a call to

         // just get the bus_entity - that's all we need)

         if (newDistributor == null) {

             Distributor distBean =

          apiAccess.getDistributorAPI();

             newDistributor =

          distBean.getDistributor(pDistributorId);

         }

         product.setCatalogDistributor(newDistributor.getBusEntity());

         product = saveCatalogProduct(pCatalogId,

                 product,

                 pUser);

            }

        }

            }

       */

      return missedItemDV;

    } catch (Exception e) {

      throw new RemoteException("updateProductDistributor: " +

                                e.getMessage());

    } finally {

      try {

        if (conn != null) conn.close();

      } catch (Exception ex) {}

    }

  }



  public void updateProductDistributor

    (int pCatalogId,

     int pNewDistributorId,

     int pOldDistId,

     int pItemId,

     String pUser) throws RemoteException, DataNotFoundException {

    logDebug("0 updateProductDistributor "

             + " pCatalogId=" + pCatalogId

             + " pNewDistributorId=" + pNewDistributorId

             + "  pOldDistId=" + pOldDistId

             + " pItemId=" + pItemId

             + " pUser=" + pUser);



    Connection conn = null;

    try {

      conn = getConnection();

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);

      dbc.addEqualTo(CatalogStructureDataAccess.BUS_ENTITY_ID,

                     pOldDistId);

      CatalogStructureDataVector csv = CatalogStructureDataAccess.select

                                       (conn, dbc);



      for (int i = 0; csv != null && i < csv.size(); i++) {

        CatalogStructureData csd = (CatalogStructureData) csv.get(i);

        csd.setBusEntityId(pNewDistributorId);

        CatalogStructureDataAccess.update(conn, csd, true);

      }



    } catch (Exception e) {

      throw processException(e);

    } finally {

      closeConnection(conn);

    }



    logDebug("1 updateProductDistributor "

             + " pCatalogId=" + pCatalogId

             + " pNewDistributorId=" + pNewDistributorId

             + "  pOldDistId=" + pOldDistId

             + " pItemId=" + pItemId

             + " pUser=" + pUser);

  }



  /**

   * Adds or updates category for catalog

   * @param pCatalogId  the catalog id.

   * @param catalogCategoryD  CatalogCategoryData object

   * @param user user login id

   * @return CatalogCategoryData

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public CatalogCategoryData saveCatalogCategory(int pCatalogId,

                                                 CatalogCategoryData

                                                 catalogCategoryD, String user) throws

    RemoteException, DataNotFoundException {

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();

      String categoryLabel = catalogCategoryD.getCatalogCategoryShortDesc();

      if (categoryLabel == null || categoryLabel.trim().length() == 0) {

        throw new RemoteException(

          "Error. CatalogBean.saveCatalogCategory() Empty category name");

      }

      int catalogCategoryId = catalogCategoryD.getCatalogCategoryId();



      if (catalogCategoryId == 0) {

        //New category

        catalogCategoryD.getItemData().setAddBy(user);

        catalogCategoryD.getItemData().setModBy(user);

        ItemData iD = ItemDataAccess.insert(con, catalogCategoryD.getItemData());

        catalogCategoryD.setItemData(iD);

        catalogCategoryId = iD.getItemId();

        CatalogStructureData csD = createNewCatalogStructure(

          pCatalogId, catalogCategoryId, 0,

          RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY, getCurrentDate());

        CatalogStructureDataAccess.insert(con, csD, true);



        //Check major category

        ItemData majorCatD = catalogCategoryD.getMajorCategory();

        if (majorCatD == null) {

          throw new Exception("Category does not have major category");

        }

        int majorCatId = majorCatD.getItemId();

        String categoryName = catalogCategoryD.getItemData().getShortDesc();

        dbc = new DBCriteria();

        dbc.addEqualToIgnoreCase(ItemDataAccess.SHORT_DESC, categoryName);

        dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                       RefCodeNames.ITEM_TYPE_CD.CATEGORY);

        dbc.addNotEqualTo(ItemDataAccess.ITEM_ID, catalogCategoryId);

        String anotherCategoriesReq =

          ItemDataAccess.getSqlSelectIdOnly(ItemDataAccess.ITEM_ID, dbc);

        dbc = new DBCriteria();

        dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, anotherCategoriesReq);

        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                       RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

        String majorCatAssocReq =

          ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM2_ID,

                                                 dbc);

        dbc = new DBCriteria();

        dbc.addOneOf(ItemDataAccess.ITEM_ID, majorCatAssocReq);

        dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                       RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);

        ItemDataVector majCatDV = ItemDataAccess.select(con, dbc);

        Iterator iter = majCatDV.iterator();

        while (iter.hasNext()) {

          ItemData mjcD = (ItemData) iter.next();

          if (mjcD.getItemId() != majorCatId) {

            throw new Exception("No consitancy category " + categoryName +

                                " already has another major category: " +

                                mjcD.getShortDesc());

          }

        }

        //Set major category

        dbc = new DBCriteria();

        dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, catalogCategoryId);

        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                       RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);

        ItemAssocDataVector majorCatAssocDV = ItemAssocDataAccess.select(con,

          dbc);

        if (majorCatAssocDV.size() == 0) {

          ItemAssocData iaD = ItemAssocData.createValue();

          iaD.setItem1Id(catalogCategoryId);

          iaD.setItem2Id(majorCatId);

          iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

          iaD.setModBy(user);

          iaD.setAddBy(user);

          ItemAssocDataAccess.insert(con, iaD);

        } else {

          ItemAssocData iaD = (ItemAssocData) majorCatAssocDV.get(0);

          int majorAssocId = iaD.getItemAssocId();

          if (majorCatAssocDV.size() > 1) { //remove extra relations

            dbc = new DBCriteria();

            dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, catalogCategoryId);

            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                           RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);

            dbc.addNotEqualTo(ItemAssocDataAccess.ITEM_ASSOC_ID, majorAssocId);

            ItemAssocDataAccess.remove(con, dbc);

          }

          int mcId = iaD.getItem2Id();

          if (mcId != majorCatId) { //Update with new value

            iaD.setItem2Id(majorCatId);

            iaD.setModBy(user);

            ItemAssocDataAccess.update(con, iaD);

          }

        }



      } else {

        //Category exists

        ItemData iD = ItemDataAccess.select(con, catalogCategoryId);



        if (iD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {

          throw new RemoteException(

            "Error. CatalogBean.saveCatalogCategory() Wrong item type. Should be: " +

            RefCodeNames.ITEM_TYPE_CD.CATEGORY + " for category id: " +

            catalogCategoryId);

        }

        if (iD.getShortDesc().equals(catalogCategoryD.

                                     getCatalogCategoryShortDesc()) == false) {

          iD.setShortDesc(catalogCategoryD.getCatalogCategoryShortDesc());

          ItemDataAccess.update(con, iD);

          catalogCategoryD.setItemData(iD);

        }



        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID,

                       catalogCategoryD.getCatalogCategoryId());

        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con,

          dbc);

        if (csDV.size() > 1) {

          throw new RemoteException("Error. CatalogBean.saveCatalogCategory() Category included into calalog more than one time. Category id: " +

                                    catalogCategoryId);

        }

        if (csDV.size() == 1) {

          CatalogStructureData csD = (CatalogStructureData) csDV.get(0);

          if (csD.getCatalogId() != pCatalogId) {

            throw new RemoteException("Error. CatalogBean.saveCatalogCategory() Category associated with another catalog. Category id: " +

                                      catalogCategoryId +

                                      " Expected Catalog Id: " + pCatalogId +

                                      " Found Catalog Id: " + csD.getCatalogId());

          }

          if (csD.getCatalogStructureCd().equals(RefCodeNames.

                                                 CATALOG_STRUCTURE_CD.

                                                 CATALOG_CATEGORY) == false) {

            throw new RemoteException("Error. CatalogBean.saveCatalogCategory() Wrong catalog-category assosiation type. Category id: " +

                                      catalogCategoryId);

          }

        }

        if (csDV.size() == 0) {

          CatalogStructureData csD = createNewCatalogStructure(

            pCatalogId, catalogCategoryD.getCatalogCategoryId(), 0,

            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY, getCurrentDate());

          CatalogStructureDataAccess.insert(con, csD, true);

        }

        //update major categories in all catalogs

        ItemData majorCatD = catalogCategoryD.getMajorCategory();

        if (majorCatD == null || majorCatD.getItemId() <= 0) {

          throw new Exception("Category does not have major category");

        }

        int majorCatId = majorCatD.getItemId();

        String catName = catalogCategoryD.getCatalogCategoryShortDesc();

        dbc = new DBCriteria();

        dbc.addEqualTo(ItemDataAccess.SHORT_DESC, catName);

        dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                       RefCodeNames.ITEM_TYPE_CD.CATEGORY);

        dbc.addOrderBy(ItemDataAccess.ITEM_ID);

        IdVector catIdV = ItemDataAccess.selectIdOnly(con, dbc);

        Iterator catIter = catIdV.iterator();

        while (catIter.hasNext()) {

          Integer cIdI = (Integer) catIter.next();

          dbc = new DBCriteria();

          dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                         RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

          dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, cIdI.intValue());

          ItemAssocDataVector mjcAssocDV =

            ItemAssocDataAccess.select(con, dbc);

          if (mjcAssocDV.size() == 0) {

            ItemAssocData mjcAssocD = ItemAssocData.createValue();

            mjcAssocD.setItem1Id(cIdI.intValue());

            mjcAssocD.setItem2Id(majorCatId);

            mjcAssocD.setItemAssocCd

              (RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

            mjcAssocD.setCatalogId(0);

            mjcAssocD.setAddBy(user);

            mjcAssocD.setModBy(user);

            ItemAssocDataAccess.insert(con, mjcAssocD);

          } else {

            ItemAssocData mjcAssocD = (ItemAssocData) mjcAssocDV.get(0);

            int mjcId = mjcAssocD.getItem2Id();

            if (mjcId != majorCatId) {

              mjcAssocD.setItem2Id(majorCatId);

              ItemAssocDataAccess.update(con, mjcAssocD);

            }

            if (mjcAssocDV.size() > 0) {

              int iaId = mjcAssocD.getItemAssocId();

              dbc = new DBCriteria();

              dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                             RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

              dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, cIdI.intValue());

              dbc.addNotEqualTo(ItemAssocDataAccess.ITEM_ASSOC_ID, iaId);

              ItemAssocDataAccess.remove(con, dbc);

            }



          }

        }

      }



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

    return catalogCategoryD;

  }





  /**

   * Removes category from catalog

   * @param pCatalogId  the catalog id.

   * @param pCatalogCategoryId  the category id

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogCategory(int pCatalogId, int pCatalogCategoryId,

                                    String user) throws RemoteException {

    Connection con = null;

    DBCriteria dbc;



    try {

      con = getConnection();

      //Check whether the item is a category

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemDataAccess.ITEM_ID, pCatalogCategoryId);

      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      if (itemDV.size() > 0) {

        ItemData itemD = (ItemData) itemDV.get(0);

        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false) {

          throw new RemoteException("Error. CatalogBean.removeCatalogCategory() Item to remove is not a CATEGORY. Item id: " +

                                    pCatalogCategoryId);

        }

      }



      // Remove from CatalogStructure

      dbc = new DBCriteria();

      //dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pCatalogCategoryId);

      CatalogStructureDataAccess.remove(con, dbc, true);



      // Remove from ItemAssoc

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pCatalogCategoryId);

      ItemAssocDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pCatalogCategoryId);

      ItemAssocDataAccess.remove(con, dbc);



      //Remove from Item

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemDataAccess.ITEM_ID, pCatalogCategoryId);

      ItemDataAccess.remove(con, dbc);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogCategory() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogCategory() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogCategory() SQL Exception happened");

      }

    }

  }



  /**

   * Removes product from catalog

   * @param pCatalogId  the catalog id.

   * @param pProductId  the product id

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogProduct(int pCatalogId, int pProductId, String user) throws

    RemoteException {

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();

      //Check whether the item is a product

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemDataAccess.ITEM_ID, pProductId);

      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      if (itemDV.size() > 0) {

        ItemData itemD = (ItemData) itemDV.get(0);

        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT) == false) {

          throw new RemoteException("Error. CatalogBean.removeCatalogProduct() Item to remove is not a CATEGORY. Item id: " +

                                    pProductId);

        }

      }

      // Remove from CatalogStructure

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pProductId);

      CatalogStructureDataAccess.remove(con, dbc, true);



      // Remove from ItemAssoc

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pProductId);

      ItemAssocDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pProductId);

      ItemAssocDataAccess.remove(con, dbc);



    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogProduct() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

      }

    }

  }





  /**

   * Removes removed categories and/or items from catalog. Category shoould not have items

   * @param pCatalogId  the catalog id.

   * @param pItemIdV  vector of subtrees roots

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  public void removeCatalogSubTrees(int pCatalogId, IdVector pItemIdV,

                                    String user) throws RemoteException {

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();

      IdVector catalogIdV = new IdVector();

      removeCatalogSubTrees(con, pCatalogId, pItemIdV, user, catalogIdV);

      //Remove data from contracts

      APIAccess apiAccess = getAPIAccess();

      Contract contractEjb = apiAccess.getContractAPI();

      contractEjb.removeCatalogItemFromContract(con, catalogIdV, pItemIdV, user);

      //Remove date from order guides, shopping carts, etc

      OrderGuide orderGuideEjb = apiAccess.getOrderGuideAPI();

      orderGuideEjb.removeCatalogItemFromOrderGuide(con, catalogIdV, pItemIdV,

        user);

    } catch (Exception exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(exc.getMessage());

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

      }

    }

  }





  /**

   * Removes category-product subtrees from catalog. No structure loops assumed

   * @param pCatalogId  the catalog id.

   * @param pItemIdV  vector of subtrees roots

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */

  private void removeCatalogSubTrees(Connection pCon, int pCatalogId,

                                     IdVector pItemIdV, String user,

                                     IdVector pCatalogIdV) throws Exception {

    DBCriteria dbc;

    pCatalogIdV.add(new Integer(pCatalogId));



    //Process sub catalogs first

    IdVector catIdV = getSubCatalogs(pCon, pCatalogId, pItemIdV);

    for (Iterator iter = catIdV.iterator(); iter.hasNext(); ) {

      Integer catIdI = (Integer) iter.next();

      removeCatalogSubTrees(pCon, catIdI.intValue(), pItemIdV, user,

                            pCatalogIdV);

    }

    for (int ii = 0; ii < pItemIdV.size(); ii++) {

      IdVector itemsV = new IdVector();

      Integer itemId = (Integer) pItemIdV.get(ii);

      itemsV.add(itemId);



      //Remove parent relations

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      Vector itemRelTypesV = new Vector();

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT);

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);

      dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, itemRelTypesV);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId.intValue());

      ItemAssocDataAccess.remove(pCon, dbc);



      //Pickup childs and remove relations

      /*

         IdVector childsV = new IdVector();

         childsV.add(itemId);

         while(childsV.size()>0) {

             dbc= new DBCriteria();

             dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,pCatalogId);

             dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD,itemRelTypesV);

             dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID,childsV);

             childsV = ItemAssocDataAccess.selectIdOnly(pCon,ItemAssocDataAccess.ITEM1_ID,dbc);

             ItemAssocDataAccess.remove(pCon,dbc);

             itemsV.addAll(childsV);

         }

       */

      // Thorow error if child exists

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, itemId);

      IdVector childsV = ItemAssocDataAccess.selectIdOnly(pCon,

        ItemAssocDataAccess.ITEM1_ID, dbc);

      if (childsV.size() > 0) {

        String errorMess =

          "^clw^Can't delete category with items. Catalog Id: " +

          pCatalogId + ", category id: " + itemId + "^clw^";

        throw new Exception(errorMess);

      }



      //Remove CatalogStructure elements

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemsV);

      CatalogStructureDataAccess.remove(pCon, dbc, true);



      //Remove major category assoc

      dbc = new DBCriteria();

      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemsV);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

      ItemAssocDataAccess.remove(pCon, dbc);



      //Remove categories from Item

      dbc = new DBCriteria();

      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemsV);

      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      ItemDataAccess.remove(pCon, dbc);

    }



  }

  public IdVector getSubCatalogs(int pCatalogId, int pItemId) throws RemoteException {
	  Connection con = null;
	  IdVector itemIdV = new IdVector();
	  itemIdV.add(pItemId);

	  try {
		  con = getConnection();
		  return getSubCatalogs(con, pCatalogId, itemIdV);
	  } catch (Exception exc) {

		  logError("exc.getMessage");

		  exc.printStackTrace();

		  throw new RemoteException(exc.getMessage());

	  } finally {
		  try {
			  con.close();
		  } catch (SQLException exc) {
			  logError("exc.getMessage");
			  exc.printStackTrace();
			  throw new RemoteException(
					  "Error. CatalogBean.getSubCatalogs() SQL Exception happened");
		  }
	  }
  }

  private IdVector getSubCatalogs(Connection pCon, int pCatalogId,

                                  IdVector pItemIdV) throws Exception {

    CatalogData catalogD = CatalogDataAccess.select(pCon, pCatalogId);

    String catalogTypeCd = catalogD.getCatalogTypeCd();

    if (!RefCodeNames.CATALOG_TYPE_CD.SYSTEM.equals(catalogTypeCd) &&

        !RefCodeNames.CATALOG_TYPE_CD.STORE.equals(catalogTypeCd) &&

        !RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogTypeCd)) {

      return new IdVector();

    }

    DBCriteria dbc = new DBCriteria();

    dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, pItemIdV);

    String catItemReq =

      CatalogStructureDataAccess.

      getSqlSelectIdOnly(CatalogStructureDataAccess.CATALOG_ID, dbc);



    dbc = new DBCriteria();

    if (RefCodeNames.CATALOG_TYPE_CD.SYSTEM.equals(catalogTypeCd)) {

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                     RefCodeNames.CATALOG_TYPE_CD.STORE);



    } else if (RefCodeNames.CATALOG_TYPE_CD.STORE.equals(catalogTypeCd)) {

      DBCriteria dbcStore = new DBCriteria();

      dbcStore.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      dbcStore.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                          RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      IdVector storeIdV =

        CatalogAssocDataAccess.

        selectIdOnly(pCon, CatalogAssocDataAccess.BUS_ENTITY_ID, dbcStore);



      DBCriteria dbcAcct = new DBCriteria();

      dbcAcct.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeIdV);

      dbcAcct.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                         RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

      String acctReq =

        BusEntityAssocDataAccess.

        getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbcAcct);



      DBCriteria dbcCatAss = new DBCriteria();

      dbcCatAss.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, acctReq);

      dbcCatAss.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                           RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);



      DBCriteria dbcCatAss1 = new DBCriteria();

      dbcCatAss1.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, storeIdV);

      dbcCatAss1.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);



      DBCriteria dbcCatAssRes = new DBCriteria();

      dbcCatAssRes.addIsolatedCriterita(dbcCatAss);

      dbcCatAssRes.addOrCriteria(dbcCatAss1);



      String catalogAcctReq =

        CatalogAssocDataAccess.

        getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbcCatAssRes);



      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogAcctReq);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                     RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);



    } else if (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(catalogTypeCd)) {

      DBCriteria dbcAcct = new DBCriteria();

      dbcAcct.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      dbcAcct.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                         RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

      IdVector acctIdV =

        CatalogAssocDataAccess.

        selectIdOnly(pCon, CatalogAssocDataAccess.BUS_ENTITY_ID, dbcAcct);



      DBCriteria dbcSite = new DBCriteria();

      dbcSite.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, acctIdV);

      dbcSite.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                         RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

      String siteReq =

        BusEntityAssocDataAccess.

        getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbcSite);



      DBCriteria dbcCatAss = new DBCriteria();

      dbcCatAss.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, siteReq);

      dbcCatAss.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                           RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);



      DBCriteria dbcCatAss1 = new DBCriteria();

      dbcCatAss1.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, acctIdV);

      dbcCatAss1.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);



      DBCriteria dbcCatAssRes = new DBCriteria();

      dbcCatAssRes.addIsolatedCriterita(dbcCatAss);

      dbcCatAssRes.addOrCriteria(dbcCatAss1);



      String catalogSiteReq =

        CatalogAssocDataAccess.

        getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbcCatAssRes);

      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogSiteReq);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                     RefCodeNames.CATALOG_TYPE_CD.SHOPPING);



    } else if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(catalogTypeCd)) {

      return new IdVector();

    }

    dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catItemReq);

    dbc.addNotEqualTo(CatalogDataAccess.CATALOG_ID, pCatalogId);


    IdVector catIdV = CatalogDataAccess.selectIdOnly(pCon,

      CatalogDataAccess.CATALOG_ID, dbc);

    return catIdV;

  }





  /**

   * Removes all existing parents for the sub tree (moves to the catalog top)

   * @param pCatalogId  the catalog id.

   * @param pItemId  the subtree root item id

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */



  public void untieCatalogSubTree(int pCatalogId, int pItemId, String user) throws

    RemoteException, DataNotFoundException {

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();

      IdVector itemsV = new IdVector();

      itemsV.add(new Integer(pItemId));



      //Remove parent relations

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      Vector itemRelTypesV = new Vector();

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT);

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

      itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);

      dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, itemRelTypesV);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItemId);

      ItemAssocDataAccess.remove(con, dbc);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogProduct() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

      }

    }

  }



  /**

   * Creates a copy of exitsting Catalog

   * @param pCatalogId  the source catalog id

   * @param pShortDesc new catalog short description

   * @param pCatalogType new catalog type code

   * @param pCatalogStatus new catalog status code

   * @param pStoreId new catalog store identificator

   * @param user user login id

   * @throws            RemoteException Required by EJB 1.0

   */







  private void cloneCatalogDistributorAssoc(Connection con,

                                            int pParentCatalogId,

                                            int pCatalogId,

                                            String pUser) throws

    RemoteException, SQLException, DataNotFoundException {



    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

    IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con,

      CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);



    dbc = new DBCriteria();

    dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeIdV);

    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                   RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);

    IdVector storeDistIdV = BusEntityAssocDataAccess.selectIdOnly(con,

      BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);



    dbc = new DBCriteria();

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pParentCatalogId);

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);

    dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, storeDistIdV);

    dbc.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);

    CatalogAssocDataVector parentCatalogDistDV =

      CatalogAssocDataAccess.select(con, dbc);



    dbc = new DBCriteria();

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);

    dbc.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);

    CatalogAssocDataVector catalogDistDV =

      CatalogAssocDataAccess.select(con, dbc);

    CatalogAssocData wrkCatalogAssocD = null;

    for (Iterator iter = parentCatalogDistDV.iterator(),

                         iter1 = catalogDistDV.iterator(); iter.hasNext(); ) {

      CatalogAssocData parentCatalogAssocD = (CatalogAssocData) iter.next();

      int distrId = parentCatalogAssocD.getBusEntityId();

      while (wrkCatalogAssocD != null || iter1.hasNext()) {

        if (wrkCatalogAssocD == null) wrkCatalogAssocD = (CatalogAssocData)

          iter1.next();

        int dId = wrkCatalogAssocD.getBusEntityId();

        if (dId < distrId) {

          wrkCatalogAssocD = null;

          continue;

        }

        if (dId > distrId) {

          CatalogAssocData caD = (CatalogAssocData) parentCatalogAssocD.clone();

          caD.setCatalogAssocId(0);

          caD.setCatalogId(pCatalogId);

          caD.setAddBy(pUser);

          caD.setModBy(pUser);

          caD = CatalogAssocDataAccess.insert(con, caD);

          break;

        }

        wrkCatalogAssocD = null;

        break;

      }

    }



    return;

  }



  private void cloneContracts(Connection pConn,

                              int pParentCatalogId,

                              int pNewCatalogId) throws RemoteException,

    SQLException, DataNotFoundException {

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(ContractDataAccess.CATALOG_ID,

                   pParentCatalogId);

    IdVector contractIds = ContractDataAccess.selectIdOnly

                           (pConn, dbc);



    // Copy all the contracts from the parent.

    for (int i = 0; i < contractIds.size(); i++) {

      Integer contractId = (Integer) contractIds.get(i);

      ContractData contdata = ContractDataAccess.

                              select(pConn, contractId.intValue());

      int origcontid = contdata.getContractId();

      contdata.setContractId(0);

      String desc = "CAT_" + pNewCatalogId +

                    " CONT_" + (i + 1);

      contdata.setShortDesc(desc);

      contdata.setCatalogId(pNewCatalogId);

      contdata = ContractDataAccess.insert(pConn, contdata);

      int newcontractid = contdata.getContractId();



      // Copy the items from the source contract

      // to the new contract.

      DBCriteria dbitemsc = new DBCriteria();

      dbitemsc.addEqualTo(ContractDataAccess.CONTRACT_ID,

                          origcontid);

      ContractItemDataVector v = ContractItemDataAccess.select

                                 (pConn, dbitemsc);

      for (int i2 = 0; i2 < v.size(); i2++) {

        ContractItemData cid = (ContractItemData) v.get(i2);

        cid.setContractItemId(0);

        cid.setContractId(newcontractid);

        ContractItemDataAccess.insert(pConn, cid, true);

      }



    }



    return;

  }



  private void cloneOrderGuides(Connection pConn,

                                int pParentCatalogId,

                                int pNewCatalogId)



    throws RemoteException, SQLException, DataNotFoundException {

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID,

                   pParentCatalogId);

    dbc.addEqualTo

      (OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,

       RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);

    IdVector ogIds = OrderGuideDataAccess.selectIdOnly

                     (pConn, dbc);



    // Copy all the order guides from the parent.

    for (int i = 0; i < ogIds.size(); i++) {

      Integer ogId = (Integer) ogIds.get(i);

      OrderGuideData ogdata = OrderGuideDataAccess.

                              select(pConn, ogId.intValue());

      int origOgId = ogdata.getOrderGuideId();

      ogdata.setOrderGuideId(0);

      String desc = "CAT_" + pNewCatalogId +

                    " OG_" + (i + 1);

      ogdata.setShortDesc(desc);

      ogdata.setCatalogId(pNewCatalogId);

      ogdata = OrderGuideDataAccess.insert(pConn, ogdata);

      int newOgId = ogdata.getOrderGuideId();



      // Copy the items from the source order guide

      // to the new order guide.

      DBCriteria dbc2 = new DBCriteria();

      dbc2.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,

                      origOgId);

      OrderGuideStructureDataVector v =

        OrderGuideStructureDataAccess.select(pConn, dbc2);

      for (int i2 = 0; i2 < v.size(); i2++) {

        OrderGuideStructureData ogsd = (OrderGuideStructureData) v.get(i2);

        ogsd.setOrderGuideStructureId(0);

        ogsd.setOrderGuideId(newOgId);

        OrderGuideStructureDataAccess.insert(pConn, ogsd);

      }



    }



    return;

  }





  public CatalogData createCatalogFromCatalog

    (int pParentCatalogId,

     String pShortDesc, String pCatalogType,

     String pCatalogStatus, int pStoreId, String user) throws RemoteException,

    DataNotFoundException, DuplicateNameException {

    Connection con = null;

    DBCriteria dbc;

    CatalogData catalogD = null;



    try {

      con = getConnection();



      // check for catalog with same name

      DBCriteria crit = new DBCriteria();

      crit.addEqualTo(CatalogDataAccess.SHORT_DESC, pShortDesc);

      IdVector dups = CatalogDataAccess.selectIdOnly(con, crit);

      if (dups.size() > 0) {

        throw new

          DuplicateNameException(CatalogDataAccess.SHORT_DESC);

      }



      //Clone Catalog

      catalogD = CatalogDataAccess.select(con, pParentCatalogId);

      catalogD.setCatalogId(0);

      catalogD.setShortDesc(pShortDesc);

      catalogD.setCatalogTypeCd(pCatalogType);

      catalogD.setCatalogStatusCd(pCatalogStatus);



      catalogD.setAddBy(user);

      catalogD.setModBy(user);

      catalogD = CatalogDataAccess.insert(con, catalogD);

      int catalogId = catalogD.getCatalogId();



      //Create Store Assoc

      addCatalogAssoc(con, catalogId, pStoreId, user);



      //Clone Items (actualy only categories)

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pParentCatalogId);

      String categoryReq = CatalogStructureDataAccess.getSqlSelectIdOnly(

        CatalogStructureDataAccess.ITEM_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(ItemDataAccess.ITEM_ID, categoryReq);

      dbc.addOrderBy(ItemDataAccess.ITEM_ID);

      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      int size = itemDV.size();

      int[] srcItemId = new int[size];

      int[] resItemId = new int[size];

      IdVector categIdV = new IdVector();

      for (int ii = 0; ii < size; ii++) {

        ItemData itemD = (ItemData) itemDV.get(ii);

        srcItemId[ii] = itemD.getItemId();

        if (itemD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT)) {

          resItemId[ii] = srcItemId[ii];

        } else {

          categIdV.add(new Integer(srcItemId[ii]));

          itemD.setItemId(0);

          itemD.setAddBy(user);

          itemD.setModBy(user);

          itemD = ItemDataAccess.insert(con, itemD);

          resItemId[ii] = itemD.getItemId();

        }

      }



      //Clone CatalogStructure

      logDebug("Clone structure from catalog id=" + pParentCatalogId);

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pParentCatalogId);

      CatalogStructureDataVector catalogStructureDV =

        CatalogStructureDataAccess.select(con, dbc);

      for (int ii = 0; ii < catalogStructureDV.size(); ii++) {

        CatalogStructureData catalogStructureD = (CatalogStructureData)

                                                 catalogStructureDV.get(ii);

        int src = catalogStructureD.getItemId();

        int res = 0;

        for (int jj = 0; jj < size; jj++) {

          if (srcItemId[jj] == src) {

            res = resItemId[jj];

            break;

          }

        }

        catalogStructureD.setCatalogStructureId(0);

        catalogStructureD.setCatalogId(catalogId);

        catalogStructureD.setItemId(res);

        catalogStructureD.setAddBy(user);

        catalogStructureD.setModBy(user);

        catalogStructureD = CatalogStructureDataAccess.insert(con,

          catalogStructureD, true);

      }



      //Clone ItemAssoc

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pParentCatalogId);

      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);



      //Categories

      logDebug("Clone categories from catalog id=" + pParentCatalogId);



      for (int ii = 0; ii < itemAssocDV.size(); ii++) {

        ItemAssocData itemAssocD = (ItemAssocData) itemAssocDV.get(ii);

        itemAssocD.setItemAssocId(0);

        itemAssocD.setCatalogId(catalogId);

        int src = itemAssocD.getItem1Id();

        int res = 0;

        for (int jj = 0; jj < size; jj++) {

          if (srcItemId[jj] == src) {

            res = resItemId[jj];

            break;

          }

        }

        if (res <= 0) {

          String msg = " CatalogBean 1. item id="

                       + src + " is inconsistent in catalog id="

                       + pParentCatalogId;

          logError(msg);

          continue;

          //throw new RemoteException(msg);

        }



        itemAssocD.setItem1Id(res);



        src = itemAssocD.getItem2Id();

        res = 0;

        for (int jj = 0; jj < size; jj++) {

          if (srcItemId[jj] == src) {

            res = resItemId[jj];

            break;

          }

        }

        if (res <= 0) {

          String msg = " CatalogBean 2. item id="

                       + src + " is inconsistent in catalog id="

                       + pParentCatalogId;

          logError(msg);

          continue;

          //throw new RemoteException(msg);

        }

        itemAssocD.setItem2Id(res);

        itemAssocD.setAddBy(user);

        itemAssocD.setModBy(user);

        itemAssocD = ItemAssocDataAccess.insert(con, itemAssocD);

      }



      //Clone major category assoc

      dbc = new DBCriteria();

      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, categIdV);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);



      ItemAssocDataVector majorCatAssocDV = ItemAssocDataAccess.select(con, dbc);

      for (int ii = 0, jj = 0; ii < majorCatAssocDV.size(); ii++) {

        ItemAssocData iaD = (ItemAssocData) majorCatAssocDV.get(ii);

        int catId = iaD.getItem1Id();

        for (; jj < srcItemId.length; jj++) {

          if (catId == srcItemId[jj]) {

            iaD.setItem1Id(resItemId[jj]);

            iaD.setAddBy(user);

            iaD.setModBy(user);

            ItemAssocDataAccess.insert(con, iaD);

            jj++;

            break;

          }

          if (catId < srcItemId[jj]) {

            break;

          }

        }

      }



      // Clone the contracts.

      // cloneContracts( con, pParentCatalogId, catalogId);

      // Clone the order guide templates.

      // cloneOrderGuides( con, pParentCatalogId, catalogId);



      // Clone catalog associations.

      cloneCatalogDistributorAssoc(con, pParentCatalogId, catalogId, user);



    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogProduct() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

    } catch (DuplicateNameException de) {

      throw de;

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogProduct() SQL Exception happened");

      }

    }

    return catalogD;

  }



  /**

   * Adds catalog relation

   * @param pCatalogId  the catalog id.

   * @param pParenId  Product or Category id

   * @param pChildIdV  IdVector of Product or Category ids

   * @param user user login id

   * @return IdVector id vector of really inserted items

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public IdVector addCatalogElements(int pCatalogId, int pParentId,

                                     IdVector pChildIdV, String user) throws

    RemoteException, DataNotFoundException {

    Connection con = null;

    DBCriteria dbc;

    Date date = getCurrentDate();

    IdVector idRetV = new IdVector();



    try {

      con = getConnection();



      // get the parent struture.

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pParentId);

      CatalogStructureDataVector pcsdv = CatalogStructureDataAccess.select(con,

        dbc);

      int parentCostCenterId = 0;

      if (pcsdv.size() > 0) {

        CatalogStructureData csd = (CatalogStructureData) pcsdv.get(0);

        parentCostCenterId = csd.getCostCenterId();

      }

      for (int ii = 0; ii < pChildIdV.size(); ii++) {

        int childId = ((Integer) pChildIdV.get(ii)).intValue();

        logDebug("Catalog.addCatalogElements: pCatalogId=" + pCatalogId +

                 " pParentId=" + pParentId +

                 " childId=" + childId +

                 " parentCostCenterId=" + parentCostCenterId);



        //Check whether the structure element already exist

        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, childId);

        IdVector idV = CatalogStructureDataAccess.selectIdOnly(con, dbc);

        //Create new structure element if doesnot exist

        if (idV.size() == 0) {



          CatalogStructureData catalogStructureD =

            createNewCatalogStructure(pCatalogId, childId, parentCostCenterId,

                                      RefCodeNames.CATALOG_STRUCTURE_CD.

                                      CATALOG_PRODUCT, date);

          CatalogStructureDataAccess.insert(con, catalogStructureD, true);

          idRetV.add(new Integer(childId));



          if (pParentId != 0) {

            createCatalogTreeNode(con, pCatalogId, pParentId, childId, user);

          }

        } else {

          // Update the cost center as the parent node.

          for (int idx = 0; idx < idV.size(); idx++) {

            int csdid = ((Integer) idV.get(idx)).intValue();

            CatalogStructureData csd = CatalogStructureDataAccess.select(con,

              csdid);

            if (csd != null && csd.getCostCenterId() != parentCostCenterId) {

              csd.setCostCenterId(parentCostCenterId);

              CatalogStructureDataAccess.update(con, csd, true);

            }

          }

        }



      }



    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalogElements() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalogElements() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.addCatalogTreeNode() SQL Exception happened");

      }

    }

    return idRetV;

  }





  /**

   * Adds catalog relation

   * @param pCatalogId  the catalog id.

   * @param pParenId  Product or Category id

   * @param pChildId  Product or Category id

   * @param user user login id

   * @return ItemAssocDate

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ItemAssocData addCatalogTreeNode(int pCatalogId, int pParentId,
          int pChildId, String user) throws RemoteException, DataNotFoundException {
	  return addCatalogTreeNode(pCatalogId, pParentId, pChildId, user, true);
  }
          
          
  public ItemAssocData addCatalogTreeNode(int pCatalogId, int pParentId,
		  int pChildId, String user, boolean allowMixedCategoryAndItemUnderSameParent) throws
    RemoteException, DataNotFoundException {

    ItemAssocData itemAssocD = null;

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();

      itemAssocD = createCatalogTreeNode(con, pCatalogId, pParentId, pChildId,
    		  user, allowMixedCategoryAndItemUnderSameParent);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalogTreeNode() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.addCatalogTreeNode() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.addCatalogTreeNode() SQL Exception happened");

      }

    }

    return itemAssocD;

  }

  private ItemAssocData createCatalogTreeNode(Connection con, int pCatalogId,
          int pParentId, int pChildId,
          String user) throws
          DataNotFoundException, SQLException, RemoteException {
	  return createCatalogTreeNode(con, pCatalogId, pParentId, pChildId,
              user, true);
  }

  /*

   */

  private ItemAssocData createCatalogTreeNode(Connection con, int pCatalogId,
          int pParentId, int pChildId,
          String user, boolean allowMixedCategoryAndItemUnderSameParent) throws
    DataNotFoundException, SQLException, RemoteException {
	  synchronized (MUTEX) {

    ItemAssocData itemAssocD = null;

    CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId);

    ItemData parentD = ItemDataAccess.select(con, pParentId);

    ItemData childD = ItemDataAccess.select(con, pChildId);

    String refType = null;

    if (parentD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) &&

        childD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT)) {

      refType = RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY;

    }

    if (parentD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) &&

        childD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY)) {

      refType = RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY;
                          
      if (!allowMixedCategoryAndItemUnderSameParent){
	      // check if parent category contains child products. if do, move the products under child category
    	  DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
	      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pParentId);
	      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
	
	      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);
	      for (int i = 0; i < itemAssocDV.size(); i++){
	    	  itemAssocD = (ItemAssocData)itemAssocDV.get(i);
	    	  itemAssocD.setItem2Id(pChildId);
	    	  ItemAssocDataAccess.update(con, itemAssocD);
	      }
      }
    }

    if (parentD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT) &&

        childD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY)) {

      refType = RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT;

    }

    if (parentD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT) &&

        childD.getItemTypeCd().equals(RefCodeNames.ITEM_TYPE_CD.PRODUCT)) {

      refType = RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT;

    }

    if (refType == null) {

      throw new RemoteException("Error. CatalogBean.createCatalogTreeNode() One of catalog items has unknown type. Item1 id: " +

                                pParentId + " Item2 id: " + pChildId);

    }

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

    dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pChildId);

    dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pParentId);

    ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);

    if (itemAssocDV.size() > 1) {

      throw new RemoteException("Error. CatalogBean.createCatalogTreeNode() Two many relations between items in catalog. Catalog id: " +

                                pCatalogId + " Item1 id: " + pParentId +

                                " Item2 id: " + pChildId);

    }

    if (itemAssocDV.size() == 1) {

      itemAssocD = (ItemAssocData) itemAssocDV.get(0);

      itemAssocD.setItemAssocCd(refType);

      itemAssocD.setModBy(user);

      ItemAssocDataAccess.update(con, itemAssocD);

    }

    if (itemAssocDV.size() == 0) {



      itemAssocD = ItemAssocData.createValue();

      itemAssocD.setItem1Id(pChildId);

      itemAssocD.setItem2Id(pParentId);

      itemAssocD.setCatalogId(pCatalogId);

      itemAssocD.setItemAssocCd(refType);

      itemAssocD.setAddBy(user);

      itemAssocD.setModBy(user);



      itemAssocD = ItemAssocDataAccess.insert(con, itemAssocD);

    }



    /*

     This logic was putting all the items in catalog 1 into the

     in clause, which resulted in oracle error:

     ORA-01037: maximum cursor memory exceeded



     durval



//Check for possible loops

     Vector itemRelTypesV = new Vector();

     itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

     itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_PRODUCT);

     itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

     itemRelTypesV.add(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_PRODUCT);



     IdVector baseV = new IdVector();

     baseV.add(new Integer(pParentId));

     IdVector childV = new IdVector();

     childV.add(new Integer(pParentId));

     int tt=0;

     int childSize = 1;

     while (childSize!=0) {

         if(tt++ > 1000) {

      throw new RemoteException("Error. CatalogBean.createCatalogTreeNode() Program error. Can not finish catalog structure loop checking. Catalog id: "+pCatalogId+" Item1 id: "+pParentId+" Item2 id: "+pChildId);

         }

         dbc = new DBCriteria();

         dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD,itemRelTypesV);

         dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID,childV);

         dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,pCatalogId);

         dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

     childV=ItemAssocDataAccess.selectIdOnly(con,ItemAssocDataAccess.ITEM1_ID,dbc);

         int baseSize = baseV.size();

         childSize = childV.size();

         //Exit?

         if(childSize==0) {

      break;

         }

         IdVector newBase = new IdVector();



         for(int ii=0, jj=0; ii<baseSize || jj<childSize;) {

      if(ii<baseSize && jj==childSize) {

          newBase.add((Integer) baseV.get(ii++));

      } else if( ii==baseSize && jj<childSize) {

          newBase.add((Integer) childV.get(jj++));

      } else {

          Integer be = (Integer) baseV.get(ii);

          Integer ch = (Integer) childV.get(jj);

          if(be.equals(ch)){

       throw new RemoteException("Error. CatalogBean.createCatalogTreeNode() Program error. Catalog loop found. Catalog id: "+pCatalogId+" Item1 id: "+pParentId+" Item2 id: "+pChildId);

          } else if(be.compareTo(ch)<0) {

       newBase.add(be);

       ii++;

          } else {

       newBase.add(ch);

       jj++;

          }

      }

         }

     }

     */



    String usql = "update clw_catalog_structure cs set " +

                  "      cs.cost_center_id = ( select max(cs2.cost_center_id) " +

                  "    from clw_catalog_structure cs2 where cs2.item_id = " +

                  pParentId +

                  "   and cs2.catalog_id = " + pCatalogId + " )" +

                  " where cs.item_id = " + pChildId +

                  " and cs.catalog_id = " + pCatalogId;

    logDebug("SQL: " + usql);

    Statement stmt = con.createStatement();

    stmt.executeUpdate(usql);

    stmt.close();



    return itemAssocD;
	  }

  }



  /*

   */

  private CatalogStructureData createNewCatalogStructure

    (int pCatalogId, int pItemId, int pCostCenterId,

     String pType, Date pDate) {

    CatalogStructureData csd = CatalogStructureData.createValue();

    csd.setCatalogId(pCatalogId);

    csd.setCostCenterId(pCostCenterId);

    csd.setCatalogStructureCd(pType);

    csd.setItemId(pItemId);

    csd.setEffDate(pDate);

    csd.setStatusCd

      (RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

    return csd;

  }



  private Date getCurrentDate() {

    GregorianCalendar cal = new GregorianCalendar();

    cal.setTime(new Date(System.currentTimeMillis()));

    cal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),

                                cal.get(Calendar.DAY_OF_MONTH));

    return cal.getTime();

  }



  public void removeCategoryFromItem(int pCatalogId,

                                     int pCatalogCategoryId,

                                     int pItemId,

                                     String user) throws RemoteException {

    Connection con = null;



    try {

      con = getConnection();



      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItemId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pCatalogCategoryId);

      ItemAssocDataAccess.remove(con, dbc);



    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException("removeCategoryFromItem 1:" + exc);

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException("removeCategoryFromItem 2:" + exc);

    } finally {

      try {

        if (con != null) con.close();

      } catch (SQLException exc) {}

    }

  }

  public ProductData addProductCategory(int pCatalogId,
                                        int pCategoryId,
                                        int pItemId,
                                        String user) throws RemoteException,  DataNotFoundException {
    return addProductCategory(pCatalogId, pCategoryId, pItemId, user, null);
 }

  public ProductData addProductCategory(int pCatalogId,

                                        int pCategoryId,

                                        int pItemId,

                                        String user,
       AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException,

    DataNotFoundException {

    Connection con = null;

    DBCriteria dbc;

    ProductData productData = null;



    try {

      con = getConnection();

      createCatalogTreeNode(con, pCatalogId, pCategoryId, pItemId, user);



      APIAccess apiAccess = getAPIAccess();

      CatalogInformation cati = apiAccess.getCatalogInformationAPI();

      productData = cati.getCatalogClwProduct(pCatalogId, pItemId, 0,0,pCategToCostCenterView);

    } catch (Exception exc) {

      String msg = "addProductCategory: " + exc;

      logError(msg);

      throw new RemoteException(msg);

    } finally {

      try {

        if (con != null) con.close();

      } catch (Exception exc) {}

    }



    return productData;

  }



  public void resetCatalogAssoc(int pCatalogId,

                                int pBusEntityId,

                                String user) throws RemoteException,

    DataNotFoundException {



    Connection con = null;

    BusEntityData busEntityD = null;

    try {

      con = getConnection();



      // Remove all current catalog associations.

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID,

                     pBusEntityId);

      CatalogAssocDataAccess.remove(con, dbc);

      addCatalogAssoc(con, pCatalogId, pBusEntityId, user);

    } catch (Exception exc) {

      String msg = "resetCatalogAssoc: " + exc;

      logError(msg);

      throw new RemoteException(msg);

    } finally {

      try {

        if (con != null) con.close();

      } catch (Exception exc) {}

    }



  }



  /**

   * Gets next available sku number

   * @return max sku number plus 1

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public int getNextSkuNum() throws RemoteException {

    Connection con = null;

    int skuNum = 0;

    try {

      con = getConnection();

      String sql = "select max(SKU_NUM) max_sku_num from clw_item";

      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        skuNum = rs.getInt("max_sku_num");

        skuNum++;

      }

      rs.close();

      stmt.close();

    } catch (Exception exc) {

      exc.printStackTrace();

      String msg = "CatalogBean.getNextSkuNum:resetCatalogAssoc: " +

                   exc.getMessage();

      logError(msg);

      throw new RemoteException(msg);

    } finally {

      try {

        if (con != null) con.close();

      } catch (Exception exc) {}

    }

    return skuNum;

  }





  /**

   * Adds or updates product of store catalog

   * @param pStoreId  the store id.

   * @param pCatalogId  the catalog id.

   * @param productD  ProductData object

   * @param user user login id

   * @return ProductData

   * @throws            RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ProductData saveStoreCatalogProduct(int pStoreId, int pCatalogId,

                                             ProductData productD, String user) throws

    RemoteException {

    Connection con = null;

    DBCriteria dbc;

    try {

      con = getConnection();



      //Check catalog existance

      CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId);



      //Is catalog a store catalog

      if (!RefCodeNames.CATALOG_TYPE_CD.STORE.equals(catalogD.getCatalogTypeCd())) {

        String errorMess = "Catalog is not a store catalog. Catalog id: " +

                           pCatalogId;

        throw new Exception(errorMess);

      }



      //Get store properties

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con, dbc);

      if (storeIdV.size() == 0) {

        String errorMess = "Catalog doesn't belong to the store. Catalog id: " +

                           pCatalogId + " Store id: " + pStoreId;

        throw new Exception(errorMess);

      }



      //Get store properties

      dbc = new DBCriteria();

      dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pStoreId);

      PropertyDataVector propertyDV = PropertyDataAccess.select(con, dbc);

      boolean autoSkuFl = false;

      boolean cleanwiseFl = false;

      for (Iterator iter = propertyDV.iterator(); iter.hasNext(); ) {

        PropertyData pD = (PropertyData) iter.next();

        if (RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN.equals(pD.

          getPropertyTypeCd())) {

          String autoSkuFlagS = Utility.strNN(pD.getValue());

          if (Utility.isTrue(autoSkuFlagS)) autoSkuFl = true;

        }

      }



      //Insert/Update Item

      ItemData itemD = productD.getItemData();

      if (itemD == null) {

        throw new DataNotFoundException(

          "No ItemData object found in the product ");

      }



      int itemId = itemD.getItemId();

      boolean newItemFl = false;

      if (itemId <= 0) { //new Item

        newItemFl = true;

        //create new item

        if (!cleanwiseFl && !autoSkuFl) {

          String storeSku = productD.getCatalogSkuNum();

          if (!Utility.isSet(storeSku)) {

            String errorMess = "^clw^Empty Sku Number^clw^";

            throw new Exception(errorMess);

          }

          dbc = new DBCriteria();

          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                         RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

          dbc.addEqualTo(CatalogStructureDataAccess.CUSTOMER_SKU_NUM, storeSku);

          IdVector itemIdV = CatalogStructureDataAccess.selectIdOnly(con,

            CatalogStructureDataAccess.ITEM_ID, dbc);

          if (itemIdV.size() > 0) {

            String errorMess = "^clw^Sku Already Exists. Sku: " + storeSku +

                               "^clw^";

            throw new Exception(errorMess);

          }

          itemD.setSkuNum(0);

        }

        itemD.setAddBy(user);

        itemD.setModBy(user);

        itemD = ItemDataAccess.insert(con, itemD);

        itemId = itemD.getItemId();

        itemD.setSkuNum(itemD.getItemId() + SKU_MINIMUM);

        ItemDataAccess.update(con, itemD);

        if (cleanwiseFl || autoSkuFl) {

          productD.setCustomerSkuNum("" + itemD.getSkuNum());

        }

        productD.setItemData(itemD);

      } else {

        //update item

        if (!cleanwiseFl) {

          String storeSku = productD.getCatalogSkuNum();

          if (!Utility.isSet(storeSku)) {

            String errorMess = "^clw^Empty Sku Number^clw^";

            throw new Exception(errorMess);

          }

          dbc = new DBCriteria();

          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

          dbc.addEqualTo(CatalogStructureDataAccess.CUSTOMER_SKU_NUM, storeSku);

          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                         RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

          CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(

            con, dbc);

          for (Iterator iter = csDV.iterator(); iter.hasNext(); ) {

            CatalogStructureData csD = (CatalogStructureData) iter.next();

            if (itemId != csD.getItemId()) {

              String errorMess = "^clw^Sku Already Exists. Sku: " + storeSku +

                                 "^clw^";

              throw new Exception(errorMess);

            }

          }

        }

        itemD.setModBy(user);

        ItemDataAccess.update(con, itemD);

      }



      //Attributes
      // update a attributor if the NameValue exists and value is not same
      // add a new attributor if the NameValue not exists
      dbc = new DBCriteria();
      dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);
      ItemMetaDataVector imExistDV = ItemMetaDataAccess.select(con, dbc); 
      Map imMap = new HashMap();
      for (int ii = 0; ii < imExistDV.size(); ii++) {
    	  ItemMetaData itemMetaD = (ItemMetaData) imExistDV.get(ii);
    	  imMap.put(itemMetaD.getNameValue(), itemMetaD);
      }

      ItemMetaDataVector imDV = productD.getProductAttributes();
      for (int ii = 0; ii < imDV.size(); ii++) {
        ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);
        if (itemMetaD != null){
        	if(Utility.isSet(itemMetaD.getValue())) {       
        
        		ItemMetaData existImD = (ItemMetaData) imMap.remove(itemMetaD.getNameValue());
        		if (existImD != null){// attributor exists and update the value
        			if (!Utility.isEqual(existImD.getValue(), itemMetaD.getValue())){
        				existImD.setValue(itemMetaD.getValue());
        				existImD.setModBy(user);
        				ItemMetaDataAccess.update(con, existImD);
        				itemMetaD = existImD;
        			}        		
        		}else{
        			itemMetaD = ProductDAO.saveItemMetaInfo(con, itemId, user, itemMetaD);
        		}
        		productD.setItemMeta(itemMetaD, itemMetaD.getNameValue());
        	}else{
        		//itemMeta should be deleted
        		
        		if(imMap.containsKey(itemMetaD.getNameValue())){
        			ItemMetaData imD = (ItemMetaData)imMap.get(itemMetaD.getNameValue());
        			ItemMetaDataAccess.remove(con, imD.getItemMetaId());
        			imMap.remove(itemMetaD.getNameValue());
        		}
        		
        	}
        }
      }
      Iterator it = imMap.values().iterator();
      while (it.hasNext()){
    	  ItemMetaData existImD = (ItemMetaData) it.next(); 
          productD.setItemMeta(existImD, existImD.getNameValue());
      }

      //Edit manufacturer mapping if was changed

      ItemMappingData manufacturerMappingD = productD.getManuMapping();

      if (manufacturerMappingD != null) {

        int manufacturerId = manufacturerMappingD.getBusEntityId();

        dbc = new DBCriteria();

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemId);

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                       RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

        ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,

          dbc);

        if (itemMappingDV.size() > 1) {

          throw new Exception(

            "Product has more then one manufacturer. Product id: " + itemId);

        }

        if (itemMappingDV.size() == 0) {

          manufacturerMappingD.setAddBy(user);

          manufacturerMappingD.setModBy(user);

          manufacturerMappingD = ItemMappingDataAccess.insert(con,

            manufacturerMappingD);

          productD.setManuMapping(manufacturerMappingD);

        }

        if (itemMappingDV.size() == 1) {

          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(0);

          if ((imD.getItemNum() == null && manufacturerMappingD.getItemNum() != null) ||

              (imD.getItemNum() != null &&

               !imD.getItemNum().equals(manufacturerMappingD.getItemNum())) ||

              imD.getBusEntityId() != manufacturerMappingD.getBusEntityId()) {

            imD.setItemNum(manufacturerMappingD.getItemNum());

            imD.setBusEntityId(manufacturerMappingD.getBusEntityId());

            imD.setModBy(user);

            ItemMappingDataAccess.update(con, imD);

          }

        }

      }

      //Save distributor mapping if changed

      productD = saveDistributorMapping(productD, user, con);

      //Save Certified Company mapping if changed

      productD=saveCertifiedCompanyMapping(productD,user,con);

      //Catalog mapping

      CatalogStructureData catalogStructureD = productD.getCatalogStructure();

      if (catalogStructureD == null) {

        productD.setCatalogStructure(pCatalogId);

        catalogStructureD = productD.getCatalogStructure();

      }

      catalogStructureD.setModBy(user);

      catalogStructureD.setItemId(itemId);

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, itemId);

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

      IdVector idV1 = CatalogStructureDataAccess.selectIdOnly(con, dbc);

      if (idV1.size() > 1) {

        throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). Catalog and product  have duplicated relation. Catalog id: " +

                                  pCatalogId + ". Product id: " + itemId);

      }

      if (idV1.size() == 0) {

        catalogStructureD.setAddBy(user);

        catalogStructureD = CatalogStructureDataAccess.insert(con,

          catalogStructureD, true);

        productD.setCatalogStructure(catalogStructureD);

      }

      if (idV1.size() == 1) {

        CatalogStructureData csD = CatalogStructureDataAccess.select(con,

          ((Integer) idV1.get(0)).intValue());

        String shortDesc = csD.getShortDesc();

        if (shortDesc == null) shortDesc = "";

        String shortDescNew = catalogStructureD.getShortDesc();



        if (shortDescNew == null || shortDescNew.equals("")) {

        	shortDescNew = shortDesc;

        	catalogStructureD.setShortDesc(shortDesc);

        }

        String customerSkuNum = csD.getCustomerSkuNum();

        if (customerSkuNum == null) customerSkuNum = "";

        String customerSkuNumNew = catalogStructureD.getCustomerSkuNum();

        /*if (customerSkuNumNew == null || customerSkuNumNew.equals("") ){

        	customerSkuNumNew = customerSkuNum;

        	catalogStructureD.setCustomerSkuNum(customerSkuNum);

        }*/



        if (csD.getItemId() != catalogStructureD.getItemId() ||

            csD.getBusEntityId() != catalogStructureD.getBusEntityId() ||

            csD.getCatalogId() != catalogStructureD.getCatalogId() ||

            csD.getCatalogStructureCd().equals(catalogStructureD.

                                               getCatalogStructureCd()) == false ||

            shortDesc.equals(shortDescNew) == false ||

            customerSkuNum.equals(customerSkuNumNew) == false

          ) {

          csD.setItemId(catalogStructureD.getItemId());

          csD.setBusEntityId(catalogStructureD.getBusEntityId());

          csD.setCatalogStructureCd(catalogStructureD.getCatalogStructureCd());

          csD.setShortDesc(catalogStructureD.getShortDesc());

          csD.setCustomerSkuNum(catalogStructureD.getCustomerSkuNum());



          CatalogStructureDataAccess.update(con, csD, true);

          productD.setCatalogStructure(csD);

        }

      }

      saveProductCategory(pCatalogId, productD, user, con);

      return productD;

    } catch (Exception exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). " +

                                exc.getMessage());

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.saveCatalogProduct() SQL Exception happened");

      }

    }

  }



  /*

 * Insert/update item Associations. As per new bussiness Logic there should be only

 * one association between an Item and Category in a Catalog. So if there is more

 * than one associations exist then delete extra ones. Then update the only remaining

 * association. If there is no association exists then insert one.

 */

  private void saveProductCategory(int pCatalogId, ProductData productD,

				String user, Connection con) throws SQLException {

	DBCriteria dbc;

	int itemId = productD.getItemData().getItemId();

	//Item Category

	CatalogCategoryDataVector ccDV = productD.getCatalogCategories();

	IdVector itemAssocIdV = new IdVector();

	dbc = new DBCriteria();

	dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);

	dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

	dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

	                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

	ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);


	for (Iterator iter = itemAssocDV.iterator(); iter.hasNext(); ) {

	  ItemAssocData imD = (ItemAssocData) iter.next();

	  itemAssocIdV.add(new Integer(imD.getItemAssocId()));

	}

	CatalogCategoryData ccD = (CatalogCategoryData)ccDV.get(0);

    int categId = ccD.getCatalogCategoryId();

    ItemAssocData iaD = ItemAssocData.createValue();

    iaD.setItem1Id(itemId);

    iaD.setItem2Id(categId);

    iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

    iaD.setCatalogId(pCatalogId);

    iaD.setAddBy(user);

    iaD.setModBy(user);



	//if there is more than one itemAssoc update one of those and remove the rest.

    if(itemAssocDV.size() > 0){

	  ItemAssocData iaDOld= (ItemAssocData)itemAssocDV.get(0);

	  int itemAssocId = iaDOld.getItemAssocId();

	  iaD.setItemAssocId(itemAssocId);

	  iaD.setAddDate(iaDOld.getAddDate());

	  iaD.setAddBy(iaDOld.getAddBy());

	  ItemAssocDataAccess.update(con, iaD);

	  //remove itemAssoc except the one updated.

	  if(itemAssocDV.size() > 1){

		itemAssocIdV.remove(new Integer( itemAssocId));

		dbc = new DBCriteria();

		dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_ID, itemAssocIdV);

		ItemAssocDataAccess.remove(con, dbc);

	  }

	}if(itemAssocDV.size()  < 1){		 //if there is no itemAssoc then insert

	  ItemAssocDataAccess.insert(con, iaD);

	}

  }



    private ProductData saveCertifiedCompanyMapping(ProductData productD, String user, Connection con) throws

            Exception {

        int itemId = productD.getItemData().getItemId();

        ItemMappingDataVector itemMappingDV = productD.getCertifiedCompanies();

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemId);

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);

        ItemMappingDataVector itemMappingBaseDV = ItemMappingDataAccess.select(con, dbc);

        IdVector removeV = new IdVector();

        boolean exist;

        if (itemMappingBaseDV == null || itemMappingDV == null) return productD;

        Iterator it = itemMappingBaseDV.iterator();

        while (it.hasNext()) {

            exist = false;

            ItemMappingData itemBaseMD = (ItemMappingData) it.next();

            Iterator it2 = itemMappingDV.iterator();

            while (it2.hasNext()) {

                ItemMappingData itemMD = (ItemMappingData) it2.next();

                if (itemMD.getItemMappingId() == itemBaseMD.getItemMappingId()

                        && itemMD.getBusEntityId() == itemBaseMD.getBusEntityId()

                        && itemMD.getItemMappingCd() != null && itemBaseMD.getItemMappingCd() != null

                        && itemMD.getItemMappingCd().equals(itemBaseMD.getItemMappingCd())

                        && RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY.equals(itemMD.getItemMappingCd())

                        && itemMD.getItemId() == itemBaseMD.getItemId()) {

                    exist = true;

                    break;

                }



            }

            if (!exist) {

                removeV.add(new Integer(itemBaseMD.getItemMappingId()));

            }

        }



        it = itemMappingDV.iterator();

        while (it.hasNext()) {

            ItemMappingData itemMD = (ItemMappingData) it.next();

            if (itemMD.getItemMappingId() == 0) {



                itemMD.setItemId(itemId);

                itemMD.setAddBy(user);

                itemMD.setModBy(user);

                itemMD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);

                itemMD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

                ItemMappingDataAccess.insert(con, itemMD);

                productD.addCertCompaniesMapping(itemMD);

                productD.addMappedCertCompany(ShoppingDAO.getCerifiedCompanyBE(con, itemMD.getBusEntityId()));

            } else if (itemMD.getItemMappingId() > 0) {

                itemMD.setModBy(user);

                itemMD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY);

                ItemMappingDataAccess.update(con, itemMD);

                productD.addCertCompaniesMapping(itemMD);

                productD.addMappedCertCompany(ShoppingDAO.getCerifiedCompanyBE(con, itemMD.getBusEntityId()));

            }

        }


        //delete

        dbc = new DBCriteria();

        dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemId);

        dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, removeV);

        ItemMappingDataAccess.remove(con, dbc);

        return productD;

    }



  public ProductData saveDistributorMapping(ProductData productD, String user) throws

    RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      return saveDistributorMapping(productD, user, con);

    } catch (Exception exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException("Error. CatalogBean.saveDistributorMapping(). " +

                                exc.getMessage());

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.saveDistributorMapping() SQL Exception happened");

      }

    }

  }





  private ProductData saveDistributorMapping(ProductData productD, String user,

                                             Connection con) throws

    SQLException {

    int itemId = productD.getItemData().getItemId();

    ItemMappingDataVector itemMappingDV = productD.getDistributorMappings();

    int[] save = new int[itemMappingDV.size()];

    for (int ii = 0; ii < save.length; ii++) {

      save[ii] = 2;

    }

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemId);

    dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                   RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

    ItemMappingDataVector itemMappingBaseDV = ItemMappingDataAccess.select(

      con, dbc);

    IdVector removeV = new IdVector();

    for (int ii = 0; ii < itemMappingBaseDV.size(); ii++) {

      ItemMappingData itemMappingBaseD = (ItemMappingData) itemMappingBaseDV.

                                         get(ii);

      int id = itemMappingBaseD.getBusEntityId();

      if (null == itemMappingBaseD.getItemNum()) {

        itemMappingBaseD.setItemNum("");

      }

      if (null == itemMappingBaseD.getItemUom()) {

        itemMappingBaseD.setItemUom("");

      }

      if (null == itemMappingBaseD.getItemPack()) {

        itemMappingBaseD.setItemPack("");

      }



      int jj = 0;

      for (; jj < itemMappingDV.size(); jj++) {

        ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);

        if (itemMappingD.getBusEntityId() == id) {

          if (itemMappingBaseD.getItemNum().equals(itemMappingD.getItemNum())

              && itemMappingBaseD.getItemUom().equals(itemMappingD.getItemUom())

              &&

              itemMappingBaseD.getItemPack().equals(itemMappingD.getItemPack())) {

            save[jj] = 0;

          } else {

            save[jj] = 1;

          }

          break;

        }

      }

      if (jj == itemMappingDV.size()) {

        removeV.add(new Integer(id));

      }

    }

    //add/update

    for (int ii = 0; ii < itemMappingDV.size(); ii++) {

      if (save[ii] == 1) {

        ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);

        itemMappingD.setModBy(user);

        ItemMappingDataAccess.update(con, itemMappingD);

      } else if (save[ii] == 2) {

        ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);

        itemMappingD.setItemId(itemId);

        itemMappingD.setAddBy(user);

        itemMappingD.setModBy(user);

        ItemMappingDataAccess.insert(con, itemMappingD);

      }

    }



    //delete

    dbc = new DBCriteria();

    dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemId);

    dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, removeV);

    ItemMappingDataAccess.remove(con, dbc);

    return productD;

  }



  /**

   *Saves store category

   * @param pStoreCatalogId store catalog id

   * @param pMajorCategoryId major category id

   * @param pMajorCCategoryName category name

   * @param pUser user name

   *@throws RemoteException

   */

  public ItemData saveStoreMajorCategory(int pStoreCatalogId,

                                         int pMajorCategoryId,

                                         String pMajorCategoryName,

                                         String pUser) throws RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      if (!Utility.isSet(pMajorCategoryName)) {

        String errorMess = "^clw^Empty major category name^clw^";

        throw new Exception(errorMess);

      }

      ItemData mjCategoryD = null;

      if (pMajorCategoryId == 0) {

        mjCategoryD = createStoreMjCategory(con, pStoreCatalogId,

                                            pMajorCategoryName, pUser);

      } else {

        mjCategoryD = updateStoreMjCategory(con, pStoreCatalogId,

                                            pMajorCategoryId,

                                            pMajorCategoryName, pUser);

      }

      return mjCategoryD;

    } catch (Exception e) {

      throw processException(e);

    } finally {

      closeConnection(con);

    }

  }



  private ItemData createStoreMjCategory(Connection con, int pStoreCatalogId,

                                         String pMajorCategoryName,

                                         String pUser) throws Exception {

    ItemData mjCategory = ItemData.createValue();

    mjCategory.setShortDesc(pMajorCategoryName);

    mjCategory.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY);

    mjCategory.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

    mjCategory.setAddBy(pUser);

    mjCategory.setModBy(pUser);



    mjCategory = ItemDataAccess.insert(con, mjCategory);



    CatalogStructureData csd = CatalogStructureData.createValue();

    csd.setCatalogId(pStoreCatalogId);

    csd.setItemId(mjCategory.getItemId());

    csd.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.

                              CATALOG_MAJOR_CATEGORY);

    csd.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

    csd.setAddBy(pUser);

    csd.setModBy(pUser);



    CatalogStructureDataAccess.insert(con, csd, true);

    return mjCategory;



  }



  private ItemData updateStoreMjCategory(Connection con, int pStoreCatalogId,

                                         int pMajorCategoryId,

                                         String pMajorCategoryName,

                                         String pUser) throws Exception {

    ItemData iD = ItemDataAccess.select(con, pMajorCategoryId);

    if (!RefCodeNames.ITEM_TYPE_CD.MAJOR_CATEGORY.

        equals(iD.getItemTypeCd())) {

      String errorMess = "Item is not a major category. Item id: " +

                         pMajorCategoryId;

      throw new Exception(errorMess);

    }

    iD.setShortDesc(pMajorCategoryName);

    iD.setModBy(pUser);

    ItemDataAccess.update(con, iD);



    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);

    dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pMajorCategoryId);

    CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con,

      dbc);

    boolean foundFl = false;

    for (Iterator iter = csDV.iterator(); iter.hasNext(); ) {

      CatalogStructureData csD = (CatalogStructureData) iter.next();

      if (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MAJOR_CATEGORY.

          equals(csD.getCatalogStructureCd()) && !foundFl) {

        foundFl = true;

        continue;

      } else {

        CatalogStructureDataAccess.remove(con, csD.getCatalogStructureId(), true);

      }

    }



    if (!foundFl) {

      CatalogStructureData csD = CatalogStructureData.createValue();

      csD.setCatalogId(pStoreCatalogId);

      csD.setItemId(pMajorCategoryId);

      csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.

                                CATALOG_MAJOR_CATEGORY);

      csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

      csD.setAddBy(pUser);

      csD.setModBy(pUser);

      CatalogStructureDataAccess.insert(con, csD, true);

    }

    return iD;



  }



  /**

   *Removes major category

   * @param pStoreCatalogId store catalog id

   * @param pMajorCategoryId major category id

   *@throws RemoteException

   */

  public void deleteStoreMajorCategory(int pStoreCatalogId,

                                       int pMajorCategoryId) throws

    RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pMajorCategoryId);

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

      IdVector iaIdV = ItemAssocDataAccess.selectIdOnly(con,

        ItemAssocDataAccess.ITEM1_ID, dbc);

      if (iaIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(ItemDataAccess.ITEM_ID, iaIdV);

        dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

        ItemDataVector categDV = ItemDataAccess.select(con, dbc);

        String categListS = "";

        for (int ii = 0; ii < categDV.size(); ii++) {

          ItemData categD = (ItemData) categDV.get(ii);

          if (ii > 0) categListS += ", ";

          categListS += categD.getShortDesc();

        }

        String errorMess = "^clw^Major category has category assigned: " +

                           categListS + "^clw^";

        throw new Exception(errorMess);

      }



      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pMajorCategoryId);

      CatalogStructureDataAccess.remove(con, dbc, true);



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pMajorCategoryId);

      iaIdV = ItemAssocDataAccess.selectIdOnly(con,

                                               ItemAssocDataAccess.ITEM1_ID,

                                               dbc);

      if (iaIdV.size() == 0) {

        ItemDataAccess.remove(con, pMajorCategoryId);

      }

    } catch (Exception e) {

      throw processException(e);

    } finally {

      closeConnection(con);

    }

  }



  /**

   *Saves store category

   * @param pStoreCatalogId store catalog id

   * @param pCategoryId category id

   * @param pCategoryName category name

   * @param pMajorCategoryId major category id

   * @param pUser user name

   *@throws RemoteException

   */

    public CatalogCategoryData saveStoreCategory(int pStoreCatalogId, int pCategoryId,

        String pCategoryName, int pMajorCategoryId, String pUser) throws RemoteException {

        return saveStoreCategory(pStoreCatalogId, pCategoryId, pCategoryName, null, pMajorCategoryId, pUser);

    }



    public CatalogCategoryData saveStoreCategory(int storeCatalogId, int categoryId,

        String categoryName, String adminCategoryName, int majorCategoryId,

            String user) throws RemoteException {



        Connection con = null;

        try {

            con = getConnection();

            if (!Utility.isSet(categoryName)) {

                String errorMess = "^clw^Empty category name^clw^";

                throw new Exception(errorMess);

            }



            ItemData mjCategD = null;

            if (majorCategoryId > 0) {

                DBCriteria dbc = new DBCriteria();

                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, storeCatalogId);

                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MAJOR_CATEGORY);

                CatalogStructureDataVector mjCatalogStrDV =

                    CatalogStructureDataAccess.select(con, dbc);

                if (mjCatalogStrDV.size() == 0) {

                    String errorMess = "Major category doesn't exist. Major categ id: " +

                        majorCategoryId + " Store catalog id: " + storeCatalogId;

                    throw new Exception(errorMess);

                }

                CatalogStructureData csD = (CatalogStructureData) mjCatalogStrDV.get(0);

                mjCategD = ItemDataAccess.select(con, csD.getItemId());

            }



            CatalogCategoryData categoryD = null;

            if (categoryId == 0) {

                categoryD = createStoreCategory(con, storeCatalogId, categoryName, adminCategoryName, majorCategoryId, user);

            }

            else {

                categoryD = updateStoreCategory(con, storeCatalogId, categoryId, categoryName, adminCategoryName, majorCategoryId, user);

            }

            categoryD.setMajorCategory(mjCategD);



            return categoryD;

        }

        catch (Exception e) {

            throw processException(e);

        }

        finally {

            closeConnection(con);

        }

    }



  public MultiproductView saveStoreMultiproduct(int pStoreCatalogId,

      int pMultiproductId, String pMultiproductName, String pUser)

          throws RemoteException {



      Connection con = null;

      try {

        con = getConnection();

        if (!Utility.isSet(pMultiproductName)) {

          String errorMess = "^clw^Empty multi product name^clw^";

          throw new Exception(errorMess);

        }



//        ItemData mjCategD = null;

//        if (pMajorCategoryId > 0) {

//          DBCriteria dbc = new DBCriteria();

//          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);

//          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

//                         RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MAJOR_CATEGORY);

//          CatalogStructureDataVector mjCatalogStrDV = CatalogStructureDataAccess.

//            select(con, dbc);

//          if (mjCatalogStrDV.size() == 0) {

//            String errorMess = "Major category doesn't exist. Major categ id: " +

//                               pMajorCategoryId + " Store catalog id: " +

//                               pStoreCatalogId;

//            throw new Exception(errorMess);

//          }

//          CatalogStructureData csD = (CatalogStructureData) mjCatalogStrDV.get(0);

//          mjCategD = ItemDataAccess.select(con, csD.getItemId());

//        }



        MultiproductView multiproductView = null;

        if (pMultiproductId == 0) {

            multiproductView = createStoreMultiproduct(con, pStoreCatalogId,

                pMultiproductName, pUser);

        } else {

            multiproductView = updateStoreMultiproduct(con, pStoreCatalogId,

                pMultiproductId, pMultiproductName, pUser);

        }

//        multiproductView.setMajorCategory(mjCategD);



        return multiproductView;

      } catch (Exception e) {

        throw processException(e);

      } finally {

        closeConnection(con);

      }

  }



    private MultiproductView createStoreMultiproduct(Connection con,

        int pStoreCatalogId,

        String pMultiproductName,

        String pUser) throws

            Exception {



        MultiproductView multiproductView = new MultiproductView();



        ItemData multiproductItemD = ItemData.createValue();

        multiproductItemD.setShortDesc(pMultiproductName);

        multiproductItemD.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.ITEM_GROUP);

        multiproductItemD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

        multiproductItemD.setAddBy(pUser);

        multiproductItemD.setModBy(pUser);

        multiproductItemD = ItemDataAccess.insert(con, multiproductItemD);



        multiproductView.setItemData(multiproductItemD);



        CatalogStructureData csD = CatalogStructureData.createValue();

        csD.setCatalogId(pStoreCatalogId);

        csD.setItemId(multiproductItemD.getItemId());

        csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MULTI_PRODUCT);

        csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

        csD.setAddBy(pUser);

        csD.setModBy(pUser);

        csD = CatalogStructureDataAccess.insert(con, csD, true);

//        if (pMajorCategoryId > 0) {

//            ItemAssocData iaD = ItemAssocData.createValue();

//            iaD.setItem1Id(multiproductItemD.getItemId());

//            iaD.setItem2Id(pMajorCategoryId);

//            iaD.setCatalogId(pStoreCatalogId);

//            iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

//            iaD.setModBy(pUser);

//            iaD.setAddBy(pUser);

//            iaD = ItemAssocDataAccess.insert(con, iaD);

//        }



        return multiproductView;



    }



    private CatalogCategoryData createStoreCategory(Connection con, int pStoreCatalogId,

        String pCategoryName, int pMajorCategoryId, String pUser) throws Exception {

        return createStoreCategory(con, pStoreCatalogId, pCategoryName, null, pMajorCategoryId, pUser);

    }



    private CatalogCategoryData createStoreCategory(Connection con, int storeCatalogId,

        String categoryName, String adminCategoryName, int majorCategoryId, String user) throws Exception {



        CatalogCategoryData catalogCategoryD = new CatalogCategoryData();

        catalogCategoryD.setParentCategory(null);



        ItemData categoryItemD = ItemData.createValue();

        categoryItemD.setShortDesc(categoryName);

        categoryItemD.setLongDesc(adminCategoryName);

        categoryItemD.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.CATEGORY);

        categoryItemD.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

        categoryItemD.setAddBy(user);

        categoryItemD.setModBy(user);

        categoryItemD = ItemDataAccess.insert(con, categoryItemD);



        catalogCategoryD.setItemData(categoryItemD);



        CatalogStructureData csD = CatalogStructureData.createValue();

        csD.setCatalogId(storeCatalogId);

        csD.setItemId(categoryItemD.getItemId());

        csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

        csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

        csD.setAddBy(user);

        csD.setModBy(user);

        csD = CatalogStructureDataAccess.insert(con, csD, true);



        if (majorCategoryId > 0) {

            ItemAssocData iaD = ItemAssocData.createValue();

            iaD.setItem1Id(categoryItemD.getItemId());

            iaD.setItem2Id(majorCategoryId);

            iaD.setCatalogId(storeCatalogId);

            iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

            iaD.setModBy(user);

            iaD.setAddBy(user);

            iaD = ItemAssocDataAccess.insert(con, iaD);

        }



        return catalogCategoryD;

    }



    private CatalogCategoryData updateStoreCategory(Connection con, int pStoreCatalogId,

        int pCategoryId, String pCategoryName, int pMajorCategoryId, String pUser) throws Exception {

        return updateStoreCategory(con, pStoreCatalogId, pCategoryId, pCategoryName, null, pMajorCategoryId, pUser);

    }



    private CatalogCategoryData updateStoreCategory(Connection con, int storeCatalogId,

        int categoryId, String categoryName, String adminCategoryName, int majorCategoryId,

            String user) throws Exception {



        CatalogCategoryData catalogCategoryD = new CatalogCategoryData();

        catalogCategoryD.setParentCategory(null);



        ItemData iD = ItemDataAccess.select(con, categoryId);

        if (!RefCodeNames.ITEM_TYPE_CD.CATEGORY.equals(iD.getItemTypeCd())) {

            String errorMess = "Item is not a category. Item id: " + categoryId;

            throw new Exception(errorMess);

        }

        iD.setShortDesc(categoryName);

        iD.setLongDesc(adminCategoryName);

        iD.setModBy(user);

        ItemDataAccess.update(con, iD);

        catalogCategoryD.setItemData(iD);



        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, storeCatalogId);

        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, categoryId);

        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con, dbc);

        boolean foundFl = false;

        for (Iterator iter = csDV.iterator(); iter.hasNext(); ) {

            CatalogStructureData csD = (CatalogStructureData) iter.next();

            if (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY.equals(csD.getCatalogStructureCd()) && !foundFl) {

                foundFl = true;

                continue;

            }

            else {

                CatalogStructureDataAccess.remove(con, csD.getCatalogStructureId(), true);

            }

        }



        if (!foundFl) {

            CatalogStructureData csD = CatalogStructureData.createValue();

            csD.setCatalogId(storeCatalogId);

            csD.setItemId(categoryId);

            csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

            csD.setAddBy(user);

            csD.setModBy(user);

            CatalogStructureDataAccess.insert(con, csD, true);

        }



        dbc = new DBCriteria();

        dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, categoryId);

        dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, storeCatalogId);

        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

            RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

        if (majorCategoryId > 0) {

            ItemAssocDataVector mjAssocDV = ItemAssocDataAccess.select(con, dbc);

            if (mjAssocDV.size() > 0) {

                for (int ii = 0; ii < mjAssocDV.size(); ii++) {

                    ItemAssocData imD = (ItemAssocData) mjAssocDV.get(ii);

                    if (ii == 0) {

                        imD.setItem2Id(majorCategoryId);

                        imD.setModBy(user);

                        ItemAssocDataAccess.update(con, imD);

                    }

                    else {

                        ItemAssocDataAccess.remove(con, imD.getItemAssocId());

                    }

                }

            }

            else {

                ItemAssocData iaD = ItemAssocData.createValue();

                iaD.setItem1Id(categoryId);

                iaD.setItem2Id(majorCategoryId);

                iaD.setCatalogId(storeCatalogId);

                iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

                iaD.setModBy(user);

                iaD.setAddBy(user);

                iaD = ItemAssocDataAccess.insert(con, iaD);

            }

        }

        else {

            ItemAssocDataAccess.remove(con, dbc);

        }



        return catalogCategoryD;

    }



  public void updateItem(ItemData itemData) throws RemoteException {

      Connection con = null;

      try {

          con = getConnection();

          ItemDataAccess.update(con, itemData);

      } catch (Exception e) {

	      throw processException(e);

      } finally {

	      closeConnection(con);

	  }

  }





  private MultiproductView updateStoreMultiproduct(Connection con, int pStoreCatalogId, int pMultiproductId,

    String pMultiproductName, String pUser) throws

    Exception {



    MultiproductView multiproductView = new MultiproductView();

//    multiproductView.setParentCategory(null);



    ItemData iD = ItemDataAccess.select(con, pMultiproductId);

    if (!RefCodeNames.ITEM_TYPE_CD.ITEM_GROUP.equals(iD.getItemTypeCd())) {

      String errorMess = "Item is not a multi product. Item id: " + pMultiproductId;

      throw new Exception(errorMess);

    }

    iD.setShortDesc(pMultiproductName);

    iD.setModBy(pUser);

    ItemDataAccess.update(con, iD);

    multiproductView.setItemData(iD);



    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);

    dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pMultiproductId);

    CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(con, dbc);

    boolean foundFl = false;

    for (Iterator iter = csDV.iterator(); iter.hasNext(); ) {

      CatalogStructureData csD = (CatalogStructureData) iter.next();

      if (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MULTI_PRODUCT.

          equals(csD.getCatalogStructureCd()) && !foundFl) {

        foundFl = true;

        continue;

      } else {

        CatalogStructureDataAccess.remove(con, csD.getCatalogStructureId(), true);

      }

    }



    if (!foundFl) {

      CatalogStructureData csD = CatalogStructureData.createValue();

      csD.setCatalogId(pStoreCatalogId);

      csD.setItemId(pMultiproductId);

      csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MULTI_PRODUCT);

      csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

      csD.setAddBy(pUser);

      csD.setModBy(pUser);

      CatalogStructureDataAccess.insert(con, csD, true);

    }



//    dbc = new DBCriteria();

//    dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pMultiproductId);

//    dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);

//    dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

//                   RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

//    if (pMajorCategoryId > 0) {

//      ItemAssocDataVector mjAssocDV = ItemAssocDataAccess.select(con, dbc);

//      if (mjAssocDV.size() > 0) {

//        for (int ii = 0; ii < mjAssocDV.size(); ii++) {

//          ItemAssocData imD = (ItemAssocData) mjAssocDV.get(ii);

//          if (ii == 0) {

//            imD.setItem2Id(pMajorCategoryId);

//            imD.setModBy(pUser);

//            ItemAssocDataAccess.update(con, imD);

//          } else {

//            ItemAssocDataAccess.remove(con, imD.getItemAssocId());

//          }

//        }

//      } else {

//        ItemAssocData iaD = ItemAssocData.createValue();

//        iaD.setItem1Id(pMultiproductId);

//        iaD.setItem2Id(pMajorCategoryId);

//        iaD.setCatalogId(pStoreCatalogId);

//        iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_MAJOR_CATEGORY);

//        iaD.setModBy(pUser);

//        iaD.setAddBy(pUser);

//        iaD = ItemAssocDataAccess.insert(con, iaD);

//      }

//    } else {

//      ItemAssocDataAccess.remove(con, dbc);

//    }



    return multiproductView;

  }







  /**

   *Removed store category

   * @param pStoreCatalogId store catalog id

   * @param pCategoryId major category id

   *@throws RemoteException

   */

  public void deleteStoreCategory(int pStoreCatalogId, int pCategoryId) throws

    RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pCategoryId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                     RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

      IdVector iaIdV = ItemAssocDataAccess.selectIdOnly(con,

        ItemAssocDataAccess.ITEM1_ID, dbc);

      if (iaIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(ItemDataAccess.ITEM_ID, iaIdV);

        dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

        ItemDataVector categDV = ItemDataAccess.select(con, dbc);

        String categListS = "";

        for (int ii = 0; ii < categDV.size(); ii++) {

          ItemData categD = (ItemData) categDV.get(ii);

          if (ii > 0) categListS += ", ";

          categListS += categD.getShortDesc();

        }

//        String errorMess = "^clw^Category has subcategories assigned: " +

//                           categListS + "^clw^";

//        throw new Exception(errorMess);

      }



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pCategoryId);

      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

      iaIdV = ItemAssocDataAccess.selectIdOnly(con,

                                               ItemAssocDataAccess.ITEM1_ID,

                                               dbc);

      if (iaIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(ItemDataAccess.ITEM_ID, iaIdV);

        dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

        ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

        String itemListS = "";

        for (int ii = 0; ii < itemDV.size(); ii++) {

          ItemData itemD = (ItemData) itemDV.get(ii);

          if (ii > 0) itemListS += ", ";

          itemListS += itemD.getShortDesc();

        }

        String errorMess = "^clw^Category has products assigned: " + itemListS +

                           "^clw^";

        throw new Exception(errorMess);

      }

      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pCategoryId);

      ItemAssocDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pCategoryId);

      CatalogStructureDataAccess.remove(con, dbc, true);



      ItemDataAccess.remove(con, pCategoryId);

    } catch (Exception e) {

      throw processException(e);

    } finally {

      closeConnection(con);

    }

  }



  public void deleteStoreMultiproduct(int pStoreCatalogId, int pMultiproductId) throws

      RemoteException {



      Connection con = null;

      try {

        con = getConnection();

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pMultiproductId);

        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

            RefCodeNames.ITEM_ASSOC_CD.ITEM_GROUP_ITEM);

        IdVector iaIdV = ItemAssocDataAccess.selectIdOnly(con,

            ItemAssocDataAccess.ITEM2_ID, dbc);

        if (iaIdV.size() > 0) {

            dbc = new DBCriteria();

            dbc.addOneOf(ItemDataAccess.ITEM_ID, iaIdV);

            dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

            ItemDataVector multiproductDV = ItemDataAccess.select(con, dbc);

            String multiproductListS = "";

            for (int ii = 0; ii < multiproductDV.size(); ii++) {

                ItemData categD = (ItemData) multiproductDV.get(ii);

                if (ii > 0) multiproductListS += ", ";

                multiproductListS += categD.getShortDesc();

            }

            String errorMess = "^clw^Multiproduct has products assigned: " +

                             multiproductListS + "^clw^";

            throw new Exception(errorMess);

        }



//        dbc = new DBCriteria();

//        dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, pMultiproductId);

//        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

//            RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

//        iaIdV = ItemAssocDataAccess.selectIdOnly(con,

//                                                 ItemAssocDataAccess.ITEM1_ID,

//                                                 dbc);

//        if (iaIdV.size() > 0) {

//            dbc = new DBCriteria();

//            dbc.addOneOf(ItemDataAccess.ITEM_ID, iaIdV);

//            dbc.addOrderBy(ItemDataAccess.SHORT_DESC);

//            ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

//            String itemListS = "";

//            for (int ii = 0; ii < itemDV.size(); ii++) {

//                ItemData itemD = (ItemData) itemDV.get(ii);

//                if (ii > 0) itemListS += ", ";

//                itemListS += itemD.getShortDesc();

//            }

//            String errorMess = "^clw^Category has products assigned: " + itemListS +

//                             "^clw^";

//            throw new Exception(errorMess);

//        }

//        dbc = new DBCriteria();

//        dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pMultiproductId);

//        ItemAssocDataAccess.remove(con, dbc);



        dbc = new DBCriteria();

        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pMultiproductId);

        CatalogStructureDataAccess.remove(con, dbc, true);



        ItemDataAccess.remove(con, pMultiproductId);

      } catch (Exception e) {

        throw processException(e);

      } finally {

        closeConnection(con);

      }

  }





  /**

   * Creates new catalog-contract pair

   * @param pCatalog  catalog data.

   * @param pContract  contract data.

   * @param pCopyContractItemFl copies contract items if true and parent contract exists

   * @param pOrderGuids set of OrderGuideDescData objects

   * @param pStoreId  store id

   * @parem pParentCatalogId template catalog id

   * @param user  user login name

   * @return catalog and contract objects

   * @throws   RemoteException Required by EJB 1.0

   */

  public CatalogContractView addCatalogContract(CatalogData pCatalog,

                                                ContractData pContract,

                                                boolean pCopyContractItemFl,

                                                OrderGuideDescDataVector

                                                pOrderGuides, int pStoreId,

                                                int pParentCatalogId,

                                                String user,

                                                boolean pUpdatePriceFromLoader) throws

    RemoteException, DataNotFoundException, DuplicateNameException {

    CatalogRequestData catalogRD = null;

    Connection con = null;

    CatalogContractView catConVw = CatalogContractView.createValue();

    try {

      con = getConnection();



      // check for catalog with same name

      DBCriteria crit = new DBCriteria();

      crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

      String catStoreReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  CATALOG_ID, crit);



      crit = new DBCriteria();

      crit.addEqualToIgnoreCase(CatalogDataAccess.SHORT_DESC, pCatalog.getShortDesc());

      crit.addOneOf(CatalogDataAccess.CATALOG_ID, catStoreReq);

      IdVector dups = CatalogDataAccess.selectIdOnly(con, crit);

      if (dups.size() > 0) {

        throw new

          DuplicateNameException(CatalogDataAccess.SHORT_DESC);

      }



      //Create record in Clw_catalog table



      Date date = new Date(System.currentTimeMillis());

      pCatalog.setAddBy(user);

      pCatalog.setModBy(user);



      pCatalog = CatalogDataAccess.insert(con, pCatalog);

      catConVw.setCatalog(pCatalog);

      int catalogId = pCatalog.getCatalogId();



      //Set catalog - store association

      CatalogAssocData catAssD = CatalogAssocData.createValue();

      catAssD.setCatalogId(catalogId);

      catAssD.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      catAssD.setBusEntityId(pStoreId);

      catAssD.setAddBy(user);

      catAssD.setModBy(user);

      CatalogAssocDataAccess.insert(con, catAssD);



      //Insert Contract Data

      pContract.setCatalogId(catalogId);

      pContract.setShortDesc(pCatalog.getShortDesc());

      pContract.setContractStatusCd(pCatalog.getCatalogStatusCd());

      pContract.setAddBy(user);

      pContract.setModBy(user);

      pContract = ContractDataAccess.insert(con, pContract);

      catConVw.setContract(pContract);



      CatalogPropertyData catalogPropertyD = CatalogPropertyData.createValue();

      catalogPropertyD.setCatalogId(catalogId);

      catalogPropertyD.setShortDesc(RefCodeNames.CATALOG_PROPERTY_TYPE_CD.UPDATE_PRICE);

      catalogPropertyD.setCatalogPropertyTypeCd(RefCodeNames.CATALOG_PROPERTY_TYPE_CD.UPDATE_PRICE);

      catalogPropertyD.setAddBy(user);

      catalogPropertyD.setModBy(user);

      catalogPropertyD.setValue((pUpdatePriceFromLoader)?"true":"false");

      catalogPropertyD = CatalogPropertyDataAccess.insert(con, catalogPropertyD);



      if (pParentCatalogId > 0) {

        //create items

        copyCatalogContractItems(con,

                                 pCatalog,

                                 pContract.getContractId(),

                                 pCopyContractItemFl,

                                 pParentCatalogId,

                                 false,

                                 user);



        //copy distributors

        cloneCatalogDistributorAssoc(con, pParentCatalogId, catalogId, user);



      }



      //Create Order guide

      HashMap ogMatchHM = new HashMap();

      if (pOrderGuides != null) {

        OrderGuideDataVector ogV = new OrderGuideDataVector();

        for (Iterator iter = pOrderGuides.iterator(); iter.hasNext(); ) {

          OrderGuideDescData ogdD = (OrderGuideDescData) iter.next();

          String orderGuideName = ogdD.getOrderGuideName();

          if (!Utility.isSet(orderGuideName)) {

            continue;

          }

          OrderGuideData orderGuideD = OrderGuideData.createValue();

          orderGuideD.setShortDesc(orderGuideName);

          orderGuideD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.

                                          ORDER_GUIDE_TEMPLATE);

          orderGuideD.setCatalogId(catalogId);

          orderGuideD.setAddBy(user);

          orderGuideD.setModBy(user);

          orderGuideD = OrderGuideDataAccess.insert(con, orderGuideD);

          ogMatchHM.put(new Integer(ogdD.getOrderGuideId()),

                        new Integer(orderGuideD.getOrderGuideId()));

          ogV.add(orderGuideD);

        }

        catConVw.setOrderGuides(ogV);

      }

      if (pParentCatalogId > 0 && pCopyContractItemFl) { //Copy order guide items if copied contract items

        copyOrderGuideItems(con,

                            pCatalog,

                            ogMatchHM,

                            pContract.getContractId(),

                            pParentCatalogId,

                            user);

      }



      //Copy catalog site-account associations

      if (pParentCatalogId > 0 &&

          RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(pCatalog.

        getCatalogTypeCd())) {

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pParentCatalogId);

        IdVector assocTypes = new IdVector();

        assocTypes.add(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

        //assocTypes.add(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

        dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ASSOC_CD, assocTypes);

        CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(con, dbc);

        for (Iterator iter = caDV.iterator(); iter.hasNext(); ) {

          CatalogAssocData caD = (CatalogAssocData) iter.next();

          caD.setCatalogId(catalogId);

          caD.setAddBy(user);

          caD.setModBy(user);

          CatalogAssocDataAccess.insert(con, caD);

        }

      }



      return catConVw;

    } catch (DuplicateNameException de) {

      throw de;

    } catch (Exception exc) {

      String mess = exc.getMessage();

      if (mess == null) mess = "";

      if (mess.indexOf("^clw^") < 0) {

        logError("exc.getMessage");

        exc.printStackTrace();

      }

      throw new RemoteException(mess);

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.addCatalog() SQL Exception happened");

      }

    }

  }



  /**

   * Updates catalog-contract pair

   * @param pCatalog  catalog data.

   * @param pContract  contract data.

   * @param pOrderGuides set of OrderGuideDescData objects

   * @param pStoreId  store id

   * @param user  user login name

   * @return catalog and contract objects

   * @throws   RemoteException, DataNotFoundException, DuplicateNameException

   */

  public CatalogContractView updateCatalogContract(CatalogData pCatalog,

                                                   ContractData pContract, OrderGuideDescDataVector pOrderGuides, int pStoreId,

                                                   String user,

                                                   boolean pUpdatePriceFromLoader) throws RemoteException, DataNotFoundException,

          DuplicateNameException {



      CatalogRequestData catalogRD = null;

      Connection con = null;

      CatalogContractView catConVw = CatalogContractView.createValue();




      try {

          con = getConnection();

          int catalogId = pCatalog.getCatalogId();



          // check for catalog with same name

          DBCriteria crit = new DBCriteria();

          crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

          String catStoreReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, crit);



          crit = new DBCriteria();

          crit.addEqualToIgnoreCase(CatalogDataAccess.SHORT_DESC, pCatalog.getShortDesc());

          crit.addOneOf(CatalogDataAccess.CATALOG_ID, catStoreReq);

          crit.addNotEqualTo(CatalogDataAccess.CATALOG_ID, pCatalog.getCatalogId());



          IdVector dups = CatalogDataAccess.selectIdOnly(con, crit);

          if (dups.size() > 0) {


              throw new DuplicateNameException(CatalogDataAccess.SHORT_DESC);

          }



          if (RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(pCatalog.getCatalogStatusCd())) {



              if (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(pCatalog.getCatalogTypeCd())) {



                  //Check if exists some account with another catalog assogned

                  DBCriteria dbc = new DBCriteria();

                  dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, catalogId);

                  dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                          RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

                  String accountAssocReq = CatalogAssocDataAccess.getSqlSelectIdOnly(

                          CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);



                  dbc = new DBCriteria();

                  dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, accountAssocReq);

                  dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);



                  String accountReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc);



                  dbc = new DBCriteria();

                  dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, accountReq);

                  dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);



                  CatalogAssocDataVector caDV =

                          CatalogAssocDataAccess.select(con, dbc);



                  IdVector cIdV = new IdVector();

                  for (Iterator iter = caDV.iterator(); iter.hasNext(); ) {

                      CatalogAssocData caD = (CatalogAssocData) iter.next();

                      cIdV.add(new Integer(caD.getCatalogId()));

                  }



                  dbc = new DBCriteria();

                  dbc.addOneOf(CatalogDataAccess.CATALOG_ID, cIdV);

                  dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

                  dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);

                  dbc.addNotEqualTo(CatalogDataAccess.CATALOG_ID, catalogId);



                  IdVector crossingCatalogIdV =

                          CatalogDataAccess.selectIdOnly(con, dbc);

                  String errorMess = null;

                  for (Iterator iter = crossingCatalogIdV.iterator(); iter.hasNext(); ) {

                      int crossCatId = ((Integer) iter.next()).intValue();



                      for (Iterator iter1 = caDV.iterator(); iter1.hasNext(); ) {

                          CatalogAssocData caD = (CatalogAssocData) iter1.next();

                          if (crossCatId == caD.getCatalogId()) {

                              if (errorMess == null) {

                                  errorMess = "";

                              } else {

                                  errorMess += ", ";

                              }

                              errorMess += "Account " + caD.getBusEntityId() +

                                      " has catalog " + crossCatId;

                          }

                      }

                  }

                  if (errorMess != null) {

                      errorMess = "^clw^Some accounts assinged to " +

                              "the catalog have another active catalogs assigned: " +

                              errorMess + "^clw^";

                      throw new Exception(errorMess);

                  }



              }

              if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(pCatalog.getCatalogTypeCd())

                      || RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(pCatalog.getCatalogTypeCd())) {



                  //Check if exists some site with another catalog assogned

                  DBCriteria dbc = new DBCriteria();

                  dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, catalogId);

                  dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);

                  String siteAssocReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);



                  dbc = new DBCriteria();

                  dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, siteAssocReq);

                  dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);



                  String siteReq = BusEntityDataAccess.getSqlSelectIdOnly( BusEntityDataAccess.BUS_ENTITY_ID, dbc);



                  dbc = new DBCriteria();

                  dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, siteReq);

                  dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);



                  CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(con, dbc);



                  IdVector cIdV = new IdVector();

                  for (Iterator iter = caDV.iterator(); iter.hasNext(); ) {

                      CatalogAssocData caD = (CatalogAssocData) iter.next();

                      cIdV.add(new Integer(caD.getCatalogId()));

                  }



                  dbc = new DBCriteria();

                  dbc.addOneOf(CatalogDataAccess.CATALOG_ID, cIdV);

                  dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

                  dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

                  dbc.addNotEqualTo(CatalogDataAccess.CATALOG_ID, catalogId);



                  IdVector crossingCatalogIdV = CatalogDataAccess.selectIdOnly(con, dbc);



                  String errorMess = null;

                  for (Iterator iter = crossingCatalogIdV.iterator(); iter.hasNext(); ) {

                      int crossCatId = ((Integer) iter.next()).intValue();



                      for (Iterator iter1 = caDV.iterator(); iter1.hasNext(); ) {

                          CatalogAssocData caD = (CatalogAssocData) iter1.next();

                          if (crossCatId == caD.getCatalogId()) {

                              if (errorMess == null) {

                                  errorMess = "";

                              } else {

                                  errorMess += ", ";

                              }

                              errorMess += "Site " + caD.getBusEntityId() + " has catalog " +

                                      crossCatId;

                          }

                      }

                  }

                  if (errorMess != null) {

                      errorMess = "^clw^Some sites assinged to " +

                              "the catalog have another active catalogs assigned: " +

                              errorMess + "^clw^";

                      throw new Exception(errorMess);

                  }



              }

          }



          Date date = new Date(System.currentTimeMillis());

          pCatalog.setModBy(user);



          CatalogDataAccess.update(con, pCatalog);

          catConVw.setCatalog(CatalogDataAccess.select(con, catalogId));



          //Insert Contract Data

          if (pContract != null && pContract.getCatalogId() == catalogId &&

                  pContract.getContractId() > 0) {



              pContract.setCatalogId(catalogId);

              pContract.setShortDesc(pCatalog.getShortDesc());

              pContract.setContractStatusCd(pCatalog.getCatalogStatusCd());

              pContract.setModBy(user);

              ContractDataAccess.update(con, pContract);

              catConVw.setContract(ContractDataAccess.select(con, pContract.getContractId()));



          } else {



              DBCriteria dbc = new DBCriteria();

              dbc.addEqualTo(ContractDataAccess.CATALOG_ID, catalogId);

              dbc.addNotEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,

                      RefCodeNames.CONTRACT_STATUS_CD.DELETED);

              ContractDataVector cDV = ContractDataAccess.select(con, dbc);

              if (cDV.size() > 1) {

                  throw new Exception("Catalog has multiple contracts. Catalog id: " +

                          catalogId);

              }

              if (cDV.size() == 1) {

                  ContractData cD = (ContractData) cDV.get(0);

                  if (pContract == null) {

                      pContract = cD;

                  } else {

                      pContract.setContractId(cD.getContractId());

                      pContract.setAddBy(cD.getAddBy());

                      pContract.setAddDate(cD.getAddDate());

                  }

                  pContract.setShortDesc(pCatalog.getShortDesc());

                  pContract.setContractStatusCd(pCatalog.getCatalogStatusCd());

                  pContract.setModBy(user);

                  ContractDataAccess.update(con, pContract);

                  catConVw.setContract(ContractDataAccess.select(con,

                          pContract.getContractId()));

              } else {

                  if (pContract == null) {

                      pContract = ContractData.createValue();

                      pContract.setRefContractNum("0");

                      pContract.setFreightTableId(0);

                      pContract.setRankWeight(0);

                      pContract.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);

                      pContract.setHidePricingInd(false);

                      pContract.setContractItemsOnlyInd(false);

                      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                      Date today = sdf.parse(sdf.format(new Date()));

                      pContract.setEffDate(today);

                      pContract.setContractTypeCd("UNKNOWN");

                  }

                  pContract.setCatalogId(catalogId);

                  pContract.setShortDesc(pCatalog.getShortDesc());

                  pContract.setContractStatusCd(pCatalog.getCatalogStatusCd());

                  pContract.setModBy(user);

                  pContract.setAddBy(user);

                  pContract = ContractDataAccess.insert(con, pContract);

                  catConVw.setContract(pContract);

              }

          }



          DBCriteria dbC = new DBCriteria();

          dbC.addEqualTo(CatalogPropertyDataAccess.CATALOG_ID, catalogId);

          CatalogPropertyDataVector catalogPropertyDV = CatalogPropertyDataAccess.select(con, dbC);

          if (catalogPropertyDV.size() > 0) {

	          CatalogPropertyData catalogPropertyD = (CatalogPropertyData) catalogPropertyDV.get(0);

	          catalogPropertyD.setCatalogId(catalogId);

	          catalogPropertyD.setShortDesc(RefCodeNames.CATALOG_PROPERTY_TYPE_CD.UPDATE_PRICE);

	          catalogPropertyD.setCatalogPropertyTypeCd(RefCodeNames.CATALOG_PROPERTY_TYPE_CD.UPDATE_PRICE);

	          catalogPropertyD.setAddBy(user);

	          catalogPropertyD.setModBy(user);

	          catalogPropertyD.setValue((pUpdatePriceFromLoader)?"true":"false");

	          CatalogPropertyDataAccess.update(con, catalogPropertyD);

          } else {

              CatalogPropertyData catalogPropertyD = CatalogPropertyData.createValue();

              catalogPropertyD.setCatalogId(catalogId);

              catalogPropertyD.setShortDesc(RefCodeNames.CATALOG_PROPERTY_TYPE_CD.UPDATE_PRICE);

              catalogPropertyD.setCatalogPropertyTypeCd(RefCodeNames.CATALOG_PROPERTY_TYPE_CD.UPDATE_PRICE);

              catalogPropertyD.setAddBy(user);

              catalogPropertyD.setModBy(user);

              catalogPropertyD.setValue((pUpdatePriceFromLoader)?"true":"false");

              catalogPropertyD = CatalogPropertyDataAccess.insert(con, catalogPropertyD);

          }



          OrderGuideDataVector ogDV = new OrderGuideDataVector();

          for (Iterator iter = pOrderGuides.iterator(); iter.hasNext(); ) {



              OrderGuideDescData ogdD = (OrderGuideDescData) iter.next();

              int ogId = ogdD.getOrderGuideId();



              String ogName = ogdD.getOrderGuideName();

              if (ogId == 0 && Utility.isSet(ogName)) {

                  OrderGuideData orderGuideD = OrderGuideData.createValue();

                  orderGuideD.setShortDesc(ogName);

                  orderGuideD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);

                  orderGuideD.setCatalogId(catalogId);

                  orderGuideD.setAddBy(user);

                  orderGuideD.setModBy(user);

                  orderGuideD = OrderGuideDataAccess.insert(con, orderGuideD);

                  ogDV.add(orderGuideD);

              } else if (ogId > 0) {

                  OrderGuideData ogD = OrderGuideDataAccess.select(con, ogId);

                  if (!Utility.isSet(ogName)) {

                      ogD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.DELETED);

                      OrderGuideDataAccess.update(con, ogD);

                  } else {

                      if (!ogName.equals(ogD.getShortDesc())) {

                          ogD.setShortDesc(ogName);

                          ogD.setModBy(user);

                          OrderGuideDataAccess.update(con, ogD);

                      }

                      ogDV.add(ogD);

                  }

              }

          }

          catConVw.setOrderGuides(ogDV);



          return catConVw;

      } catch (DuplicateNameException de) {

          throw de;

      } catch (Exception exc) {

          String mess = exc.getMessage();

          if (mess == null) mess = "";

          if (mess.indexOf("^clw^") < 0) {

              logError("exc.getMessage");

              exc.printStackTrace();

          }

          throw new RemoteException(mess);

      } finally {

          try {

              con.close();

          } catch (SQLException exc) {

              logError("exc.getMessage");

              exc.printStackTrace();

              throw new RemoteException(

                      "Error. CatalogBean.addCatalog() SQL Exception happened");

          }

      }

  }



  private int getMasterCatalogId(Connection con, CatalogData pCatalog) throws

    Exception {

    DBCriteria dbc;

    int masterCatalogId = 0;



    if (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(pCatalog.getCatalogTypeCd()) ||

        RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR.equals(pCatalog.getCatalogTypeCd())) {

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalog.getCatalogId());

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      IdVector busEntityIdV = CatalogAssocDataAccess.selectIdOnly(con,

        CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

      dbc = new DBCriteria();

      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busEntityIdV);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      String storeCatalogReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  CATALOG_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatalogReq);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                     RefCodeNames.CATALOG_TYPE_CD.STORE);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

      IdVector storeCatIdV = CatalogDataAccess.selectIdOnly(con, dbc);

      if (storeCatIdV.size() > 1) {

        String mess = "^clw^The store has more than one active catalogs^clw^";

        throw new Exception(mess);

      }

      if (storeCatIdV.size() == 1) {

        masterCatalogId = ((Integer) storeCatIdV.get(0)).intValue();

      }

    }

    if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(pCatalog.getCatalogTypeCd()) ||

        RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING.equals(pCatalog.

      getCatalogTypeCd())) {

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalog.getCatalogId());

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

      IdVector busEntityIdV = CatalogAssocDataAccess.selectIdOnly(con,

        CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

      dbc = new DBCriteria();

      dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busEntityIdV);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

      String storeCatalogReq =

        CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                  CATALOG_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatalogReq);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                     RefCodeNames.CATALOG_TYPE_CD.STORE);

      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

                     RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

      IdVector acctCatIdV = CatalogDataAccess.selectIdOnly(con, dbc);

      if (acctCatIdV.size() > 1) {

        String mess =

          "^clw^Account of the catalog has more than one active catalogs^clw^";

        throw new Exception(mess);

      }

      if (acctCatIdV.size() == 1) {

        masterCatalogId = ((Integer) acctCatIdV.get(0)).intValue();

      }

    }

    if (masterCatalogId == 0) {

      String mess = "^clw^No master catalog found^clw^";

      throw new Exception(mess);

    }

    return masterCatalogId;

  }



  private void copyCatalogContractItems(Connection con,

                                        CatalogData pCatalog,

                                        int pContractId,

                                        boolean pCopyContractItemFl,

                                        int pParentCatalogId,

                                        boolean pOverwriteFl,

                                        String user) throws Exception {



    DBCriteria dbc;

    int catalogId = pCatalog.getCatalogId();

    IdVector storeCatIdV = getStoreCatalogId(con, catalogId);

    boolean copyFromStoreCatFl = false;

    if (storeCatIdV.contains(new Integer(pParentCatalogId))) copyFromStoreCatFl = true;



    dbc = new DBCriteria();

    dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, storeCatIdV);

    String storeItemIdReq =

      CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.

      ITEM_ID,

      dbc);



    dbc = new DBCriteria();

    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);

    dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

    CatalogStructureDataVector catStrDV =

      CatalogStructureDataAccess.select(con, dbc);



    LinkedList typeLL = new LinkedList();

    typeLL.add(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

    typeLL.add(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);



    dbc = new DBCriteria();

    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pParentCatalogId);

    dbc.addOneOf(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, typeLL);

    dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, storeItemIdReq);

    dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

    CatalogStructureDataVector parentCatStrDV =

      CatalogStructureDataAccess.select(con, dbc);



    CatalogStructureData wrkCatStrD = null;

    for (Iterator iter = parentCatStrDV.iterator(), iter1 = catStrDV.iterator();

      iter.hasNext(); ) {

      CatalogStructureData parentCatStrD = (CatalogStructureData) iter.next();

      int parentItemId = parentCatStrD.getItemId();

      boolean foundFl = false;

      while (wrkCatStrD != null || iter1.hasNext()) {

        if (wrkCatStrD == null) wrkCatStrD = (CatalogStructureData) iter1.next();

        int wrkItemId = wrkCatStrD.getItemId();

        if (wrkItemId < parentItemId) {

          wrkCatStrD = null;

          continue;

        }

        if (wrkItemId > parentItemId) {

          break;

        }

        if (!wrkCatStrD.getCatalogStructureCd().

            equals(parentCatStrD.getCatalogStructureCd())) {

          wrkCatStrD = null;

          continue;

        }

        foundFl = true;

        if (pOverwriteFl) {

          wrkCatStrD.setBusEntityId(parentCatStrD.getBusEntityId());

          wrkCatStrD.setStatusCd(parentCatStrD.getStatusCd());

          wrkCatStrD.setShortDesc(parentCatStrD.getShortDesc());

          if (!copyFromStoreCatFl) {

            wrkCatStrD.setCustomerSkuNum(parentCatStrD.getCustomerSkuNum());

          }

          wrkCatStrD.setCostCenterId(parentCatStrD.getCostCenterId());

          wrkCatStrD.setModBy(user);

          CatalogStructureData oldCatStrD =

            CatalogStructureDataAccess.select(con,

                                              wrkCatStrD.getCatalogStructureId());

          CatalogStructureDataAccess.update(con, wrkCatStrD, true);

          wrkCatStrD = null;

          break;

        }

      }

      if (!foundFl) {

        CatalogStructureData catStrD = (CatalogStructureData) parentCatStrD.

                                       clone();

        catStrD.setCatalogStructureId(0);

        catStrD.setCatalogId(catalogId);

        if (copyFromStoreCatFl) {

          catStrD.setCustomerSkuNum(null);

        }

        catStrD.setAddBy(user);

        catStrD.setModBy(user);

        catStrD = CatalogStructureDataAccess.insert(con, catStrD, true);

      }

    }



    IdVector parentItemIdV = new IdVector();

    for (Iterator iter = parentCatStrDV.iterator(); iter.hasNext(); ) {

      CatalogStructureData parentCatStrD = (CatalogStructureData) iter.next();

      int parentItemId = parentCatStrD.getItemId();

      if (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT.

          equals(parentCatStrD.getCatalogStructureCd())) {

        parentItemIdV.add(new Integer(parentItemId));

      } else {

        iter.remove();

      }

    }



    dbc = new DBCriteria();

    dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);

    dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, parentItemIdV);

    dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

    ItemAssocDataVector itemAssocDV =

      ItemAssocDataAccess.select(con, dbc);



    dbc = new DBCriteria();

    dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pParentCatalogId);

    dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, parentItemIdV);

    dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);

    ItemAssocDataVector parentItemAssocDV =

      ItemAssocDataAccess.select(con, dbc);



    ItemAssocData wrkItemAssocD = null;

    for (Iterator iter = parentItemAssocDV.iterator(),

      iter1 = itemAssocDV.iterator(); iter.hasNext(); ) {

      ItemAssocData parentItemAssocD = (ItemAssocData) iter.next();

      int parentItemId = parentItemAssocD.getItem1Id();

      boolean foundFl = false;

      while (wrkItemAssocD != null || iter1.hasNext()) {

        if (wrkItemAssocD == null) wrkItemAssocD = (ItemAssocData) iter1.next();

        int wrkItemId = wrkItemAssocD.getItem1Id();

        if (wrkItemId < parentItemId) {

          if (pOverwriteFl) {

            ItemAssocDataAccess.remove(con, wrkItemAssocD.getItemAssocId());

          }

          wrkItemAssocD = null;

          continue;

        }

        if (wrkItemId > parentItemId) {

          break;

        }

        if (!wrkItemAssocD.getItemAssocCd().

            equals(parentItemAssocD.getItemAssocCd())) {

          wrkItemAssocD = null;

          continue;

        }

        foundFl = true;

        if (pOverwriteFl) {

          wrkItemAssocD.setModBy(user);

          wrkItemAssocD.setItem2Id(parentItemAssocD.getItem2Id());

          ItemAssocDataAccess.update(con, wrkItemAssocD);

          wrkItemAssocD = null;

          break;

        }

      }

      if (!foundFl) {

        ItemAssocData itemAssocD = (ItemAssocData) parentItemAssocD.clone();

        itemAssocD.setItemAssocId(0);

        itemAssocD.setCatalogId(catalogId);

        itemAssocD.setModBy(user);

        itemAssocD.setAddBy(user);

        ItemAssocDataAccess.insert(con, itemAssocD);

      }

    }



    //Contract items

    if (!pCopyContractItemFl) {

      return;

    }

    dbc = new DBCriteria();

    dbc.addEqualTo(ContractDataAccess.CATALOG_ID, pParentCatalogId);

    dbc.addNotEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,

                      RefCodeNames.CONTRACT_STATUS_CD.DELETED);

    IdVector parentContractIdV =

      ContractDataAccess.selectIdOnly(con, ContractDataAccess.CONTRACT_ID, dbc);

    if (parentContractIdV.size() == 0) {

      //String errorMess = "^clw^Catalog "+pParentCatalogId+" does't have related contract^clw^";

      //throw new Exception(errorMess);

      return;

    }

    if (parentContractIdV.size() > 1) {

      String errorMess = "^clw^Catalog " + pParentCatalogId +

                         " has multilpe related contracts^clw^";

      throw new Exception(errorMess);

    }

    int parentContractId = ((Integer) parentContractIdV.get(0)).intValue();



    dbc = new DBCriteria();

    dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);

    dbc.addOneOf(ContractItemDataAccess.ITEM_ID, parentItemIdV);

    dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);

    ContractItemDataVector contractItemDV =

      ContractItemDataAccess.select(con, dbc);



    dbc = new DBCriteria();

    dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, parentContractId);

    dbc.addOneOf(ContractItemDataAccess.ITEM_ID, parentItemIdV);

    dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);

    ContractItemDataVector parentContractItemDV =

      ContractItemDataAccess.select(con, dbc);



    ContractItemData wrkContItemD = null;

    for (Iterator iter = parentContractItemDV.iterator(),

                         iter1 = contractItemDV.iterator(); iter.hasNext(); ) {

      ContractItemData parentContractItemD = (ContractItemData) iter.next();

      int parentItemId = parentContractItemD.getItemId();

      boolean foundFl = false;

      while (wrkContItemD != null || iter1.hasNext()) {

        if (wrkContItemD == null) wrkContItemD = (ContractItemData) iter1.next();

        int wrkItemId = wrkContItemD.getItemId();

        if (wrkItemId < parentItemId) {

          wrkContItemD = null;

          continue;

        }

        if (wrkItemId > parentItemId) {

          break;

        }

        foundFl = true;

        if (pOverwriteFl) {

          wrkContItemD.setAmount(parentContractItemD.getAmount());

          wrkContItemD.setDistCost(parentContractItemD.getDistCost());

          wrkContItemD.setDistBaseCost(parentContractItemD.getDistBaseCost());

          wrkContItemD.setDiscountAmount(parentContractItemD.getDiscountAmount());

          wrkContItemD.setCurrencyCd(parentContractItemD.getCurrencyCd());

          wrkContItemD.setExpDate(parentContractItemD.getExpDate());

          wrkContItemD.setEffDate(parentContractItemD.getEffDate());

          wrkContItemD.setModBy(user);

          ContractItemData oldContItemD =

            ContractItemDataAccess.select(con, wrkContItemD.getContractItemId());

          ContractItemDataAccess.update(con, wrkContItemD, true);

          wrkContItemD = null;

          break;

        }

      }

      if (!foundFl) {

        ContractItemData contItemD = (ContractItemData) parentContractItemD.

                                     clone();

        contItemD.setContractItemId(0);

        contItemD.setContractId(pContractId);

        contItemD.setModBy(user);

        contItemD.setAddBy(user);

        contItemD = ContractItemDataAccess.insert(con, contItemD, true);

      }

    }



  }



  private void copyOrderGuideItems(Connection con,

                                   CatalogData pNewCatalog,

                                   HashMap pOgMatchHM,

                                   int pContractId,

                                   int pParentCatalogId,

                                   String user) throws Exception {



    DBCriteria dbc;

    dbc = new DBCriteria();

    dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pParentCatalogId);

    dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,

                   RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);

    IdVector orderGuideIdV =

      OrderGuideDataAccess.selectIdOnly(con,

                                        OrderGuideDataAccess.ORDER_GUIDE_ID,

                                        dbc);



    if (orderGuideIdV.size() == 0) {

      return;

    }

    Integer orderGuideIdI = (Integer) orderGuideIdV.get(0);

    int orderGuideId = orderGuideIdI.intValue();



    dbc = new DBCriteria();

    dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);

    IdVector itemIdV = ContractItemDataAccess.selectIdOnly(con,

      ContractItemDataAccess.ITEM_ID, dbc);



    //Copy order guide items

    Set ogMatchSet = pOgMatchHM.entrySet();

    for (Iterator iter = ogMatchSet.iterator(); iter.hasNext(); ) {

      Map.Entry me = (Map.Entry) iter.next();

      Integer srcOgIdI = (Integer) me.getKey();

      Integer newOgIdI = (Integer) me.getValue();

      dbc = new DBCriteria();

      dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemIdV);

      dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,

                     srcOgIdI.intValue());



      OrderGuideStructureDataVector orderGuideDV =

        OrderGuideStructureDataAccess.select(con, dbc);



      for (Iterator iter1 = orderGuideDV.iterator(); iter1.hasNext(); ) {

        OrderGuideStructureData ogD = (OrderGuideStructureData) iter1.next();

        ogD.setOrderGuideStructureId(0);

        ogD.setOrderGuideId(newOgIdI.intValue());

        ogD.setAddBy(user);

        ogD.setModBy(user);

        OrderGuideStructureDataAccess.insert(con, ogD);

      }

    }



  }



  private IdVector getStoreCatalogId(Connection con, int pCatalogId) throws

    Exception {

    DBCriteria dbc = new DBCriteria();

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

    IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con,

      CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);



    dbc = new DBCriteria();

    dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, storeIdV);

    dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                   RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

    String storeCatReq =

      CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.

                                                CATALOG_ID, dbc);



    dbc = new DBCriteria();

    dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,

                   RefCodeNames.CATALOG_TYPE_CD.STORE);

    dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,

                   RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

    dbc.addOneOf(CatalogDataAccess.CATALOG_ID, storeCatReq);

    IdVector storeCatIdV = CatalogDataAccess.selectIdOnly(con,

      CatalogDataAccess.CATALOG_ID, dbc);

    return storeCatIdV;

  }



  /**

   * Removes the catalog from database with it stucture and associations

   * @param pCatalogId  the catalog id.

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  public void removeCatalogContract(int pCatalogId, String user) throws

    RemoteException, DataNotFoundException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc;



      dbc = new DBCriteria();

      dbc.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);

      IdVector contractIdV =

        ContractDataAccess.selectIdOnly(con, ContractDataAccess.CONTRACT_ID,

                                        dbc);



      //Contract

      dbc = new DBCriteria();

      dbc.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);

      ContractItemSubstDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);

      ContractItemDataAccess.remove(con, dbc, true);



      dbc = new DBCriteria();

      dbc.addOneOf(ContractDataAccess.CONTRACT_ID, contractIdV);

      ContractDataAccess.remove(con, dbc);



      //Order guide

      dbc = new DBCriteria();

      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, pCatalogId);

      IdVector ogIdV =

        OrderGuideDataAccess.selectIdOnly(con,

                                          OrderGuideDataAccess.ORDER_GUIDE_ID,

                                          dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, ogIdV);

      OrderGuideStructureDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_ID, ogIdV);

      OrderGuideDataAccess.remove(con, dbc);



      //Catalog

      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

      CatalogAssocDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);

      ItemAssocDataAccess.remove(con, dbc);



      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

      CatalogStructureDataAccess.remove(con, dbc, true);



      dbc = new DBCriteria();

      dbc.addEqualTo(CatalogPropertyDataAccess.CATALOG_ID, pCatalogId);

      CatalogPropertyDataAccess.remove(con, dbc);



      CatalogDataAccess.remove(con, pCatalogId);

    } catch (NamingException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogCascade() Naming Exception happened");

    } catch (SQLException exc) {

      logError("exc.getMessage");

      exc.printStackTrace();

      throw new RemoteException(

        "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

      }

    }

  }



  /**

   * Saves item - catalog,item - contract, item  - distributor,

   * item - distributor - genenric manufacture, item - contract data

   * @param itemAggrSet set of ItemCatalogAggrView objects

   * @param user - user login name

   * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  /* Replaced with saveItemCatalogMgrSet1

   public void saveItemCatalogMgrSet (ItemCatalogAggrViewVector itemAggrSet, String user)

   throws RemoteException

   {

    Connection con =null;

    try{

     con = getConnection();

     DBCriteria dbc;

     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

     Date today = sdf.parse(sdf.format(new Date()));

     HashMap orderGuideIdHM = new HashMap();

     HashMap templateOrderGuideIdHM = new HashMap();

     IdVector distIdV = new IdVector();

     IdVector manufIdV = new IdVector();

     LinkedList errorMessLL = new LinkedList();

     ItemCatalogAggrView[] itemCatAggrVwA =

   (ItemCatalogAggrView[]) itemAggrSet.toArray(new ItemCatalogAggrView[0]);



     int itemId = 0;

     if(itemCatAggrVwA.length>0) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[0];

       itemId = icaVw.getItemId();

     }



     if(itemCatAggrVwA.length>1) {

        for(int ii=0; ii<itemCatAggrVwA.length-1; ii++ ) {

          boolean exitFl = true;

          for(int jj=0; jj<itemCatAggrVwA.length-1-ii; jj++) {

            ItemCatalogAggrView icaVw1 = itemCatAggrVwA[jj];

            ItemCatalogAggrView icaVw2 = itemCatAggrVwA[jj+1];

            int cId1 = icaVw1.getCatalogId();

            int cId2 = icaVw2.getCatalogId();

            if(cId1>cId2) {

              itemCatAggrVwA[jj] = icaVw2;

              itemCatAggrVwA[jj+1] = icaVw1;

              exitFl = false;

            }

          }

          if(exitFl) break;

        }

     }



     IdVector catalogIdV = new IdVector();

     IdVector ogAddIdV = new IdVector();

     IdVector ogRemIdV = new IdVector();

     int prevCatalogId = -1;

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       int catalogId = icaVw.getCatalogId();

       if(catalogId!=prevCatalogId) {

         catalogIdV.add(new Integer(catalogId));

         prevCatalogId = catalogId;

       }

       int ogId = icaVw.getOrderGuideId();

       if(ogId>0 &&

         icaVw.getOrderGuideFl()!=icaVw.getOrderGuideFlInp()) {

         if(icaVw.getOrderGuideFlInp() &&

            icaVw.getCatalogFlInp() &&

            icaVw.getContractFlInp()) {

           ogAddIdV.add(new Integer(ogId));

         }

         if(!icaVw.getOrderGuideFlInp() ||

            !icaVw.getContractFlInp() ||

            !icaVw.getCatalogFlInp()) {

           ogRemIdV.add(new Integer(ogId));

         }

       }

     }



     //Get store

     dbc = new DBCriteria();

     dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, catalogIdV);

     dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

             RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

     IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con,

             CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

     if(storeIdV.size()>1) {

       throw new Exception("^clw^Catalogs belong to multiple stores^clw^");

     }

     Integer storeIdI = (Integer) storeIdV.get(0);

     int storeId = storeIdI.intValue();



     //Add to order guide

     dbc = new DBCriteria();

     dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,ogAddIdV);

     dbc.addEqualTo(OrderGuideStructureDataAccess.ITEM_ID,itemId);



     IdVector ogIdV =

             OrderGuideStructureDataAccess.selectIdOnly(con,

                     OrderGuideStructureDataAccess.ORDER_GUIDE_ID,dbc);

     for(Iterator iter = ogAddIdV.iterator(); iter.hasNext();) {

       Integer ogIdI = (Integer) iter.next();

       if(!ogIdV.contains(ogIdI)) {

         OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();

         ogsD.setOrderGuideId(ogIdI.intValue());

         ogsD.setItemId(itemId);

         ogsD.setAddBy(user);

         ogsD.setModBy(user);

         OrderGuideStructureDataAccess.insert(con,ogsD);

       }

     }



     //Remove from order guide

     dbc = new DBCriteria();

     dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,ogRemIdV);

     dbc.addEqualTo(OrderGuideStructureDataAccess.ITEM_ID,itemId);

     OrderGuideStructureDataAccess.remove(con,dbc);



     //Remove from catalog and contract

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       int catalogId = icaVw.getCatalogId();

       Integer catalogIdI = new Integer(catalogId);





       if( !icaVw.getCatalogFlInp() ||  !icaVw.getContractFlInp()) {

         //remove item form the contract

         dbc = new DBCriteria();

   dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, icaVw.getContractId());

         dbc.addEqualTo(ContractItemDataAccess.ITEM_ID,itemId);

         ContractItemDataAccess.remove(con,dbc);

       }



       if( !icaVw.getCatalogFlInp()) {

         dbc = new DBCriteria();

         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catalogId);

         dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID,itemId);

         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                 RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

         CatalogStructureDataAccess.remove(con,dbc);



         dbc = new DBCriteria();

         dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);

         dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,itemId);

         dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                 RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

         ItemAssocDataAccess.remove(con,dbc);

       }

     }



     //Add to catalog and contract

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       int catalogId = icaVw.getCatalogId();

       Integer catalogIdI = new Integer(catalogId);



       if(icaVw.getCatalogFlInp() && !icaVw.getCatalogFl()) {

         dbc = new DBCriteria();

         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catalogId);

         dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID,itemId);

         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                 RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

         IdVector csIdV = CatalogStructureDataAccess.selectIdOnly(con,dbc);

         if(csIdV.size()==0) {

           CatalogStructureData csD = CatalogStructureData.createValue();

           csD.setCatalogId(catalogId);

           csD.setItemId(itemId);

           csD.setAddBy(user);

           csD.setModBy(user);

           csD.setEffDate(today);

           csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

   csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

           csD = CatalogStructureDataAccess.insert(con,csD,true);

         } else if(csIdV.size()>1) {

           throw new Exception("^clw^System error: CB001^clw^" +

              " Multiple records in CATALOG_SRTUCTURE. Catalog id: "+catalogId+

                   " Item id: "+itemId);

         }

       }



       if( icaVw.getCatalogFlInp() &&  icaVw.getContractFlInp() && !icaVw.getContractFl()) {

         //add item to contract

         int contractId = icaVw.getContractId();

         if(contractId<=0) {

           throw new Exception("^clw^System error. " +

                   "Price not configured for the catalog. Catalog id: "+

                   catalogId+"^clw^");

         }

         dbc = new DBCriteria();

   dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, icaVw.getContractId());

         dbc.addEqualTo(ContractItemDataAccess.ITEM_ID,itemId);

         IdVector ciIdV =  ContractItemDataAccess.selectIdOnly(con,dbc);

         if(ciIdV.size()==0) {

           ContractItemData ciD = ContractItemData.createValue();

           ciD.setContractId(contractId);

           ciD.setItemId(itemId);

           ciD.setAmount(new BigDecimal(0));

           ciD.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);

           ciD.setDistBaseCost(new BigDecimal(0));

           ciD.setDistCost(new BigDecimal(0));

           ciD.setModBy(user);

           ciD.setAddBy(user);

           ciD = ContractItemDataAccess.insert(con,ciD);

         } else if(ciIdV.size()>1) {

           throw new Exception("^clw^System error: CB003^clw^" +

              " Multiple records in CONTRACT_ITEM. Contract id: "+contractId+

                   " Item id: "+itemId);

         }

       }

     }



     //Get distributors and manufacturers

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       int catalogId = icaVw.getCatalogId();

       if(icaVw.getCatalogFlInp()) {

         String distIdS = icaVw.getDistIdInp();

         if(Utility.isSet(distIdS)) {

           try {

             int distId = Integer.parseInt(distIdS);

             Integer distIdI = new Integer(distId);

             if(!distIdV.contains(distIdI)) {

               distIdV.add(distIdI);

             }

           } catch (Exception exc) {

             errorMessLL.add("Illegal distributor id: "+distIdS);

           }

           String genManufIdS = icaVw.getGenManufIdInp();

           if(Utility.isSet(genManufIdS)) {

             try {

               int genManufId = Integer.parseInt(genManufIdS);

               Integer genManufIdI = new Integer(genManufId);

               if(!manufIdV.contains(genManufIdI)) {

                 manufIdV.add(genManufIdI);

               }

             } catch (Exception exc) {

               errorMessLL.add("Illegal manufacturer id: "+genManufIdS);

             }

           }

         }

       }

     }

     if(errorMessLL.size()>0) {

       throw new Exception(makeErrorString(errorMessLL));

     }



     dbc = new DBCriteria();

     dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);

     dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

             RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);

     String storeDistReq =

        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);





     dbc = new DBCriteria();

     dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,distIdV);

     dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

             RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

     dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeDistReq);



     BusEntityDataVector distBusEntDV =

                             BusEntityDataAccess.select(con,dbc);

     if(distBusEntDV.size()!=distIdV.size()) {

       for(Iterator iter = distIdV.iterator(); iter.hasNext();) {

         Integer distIdI = (Integer)iter.next();

         int distId = distIdI.intValue();

         boolean foundFl = false;

         for(Iterator iter1 = distBusEntDV.iterator(); iter1.hasNext(); ) {

           BusEntityData beD = (BusEntityData) iter1.next();

           if(beD.getBusEntityId()==distId) {

             foundFl = true;

             break;

           }

         }

         if(!foundFl) {

           errorMessLL.add("Distributor not found. Distributor id: "+distId);

         }

       }

     }



     dbc = new DBCriteria();

     dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,manufIdV);

     dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

             RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);



     BusEntityDataVector manufBusEntDV =

                             BusEntityDataAccess.select(con,dbc);

     if(manufBusEntDV.size()!=manufIdV.size()) {

       for(Iterator iter = manufIdV.iterator(); iter.hasNext();) {

         Integer manufIdI = (Integer)iter.next();

         int manufId = manufIdI.intValue();

         boolean foundFl = false;

         for(Iterator iter1 = distBusEntDV.iterator(); iter1.hasNext(); ) {

           BusEntityData beD = (BusEntityData) iter1.next();

           if(beD.getBusEntityId()==manufId) {

             foundFl = true;

             break;

           }

         }

         if(!foundFl) {

   errorMessLL.add("Manufacturer not found. Manufacturer id: "+manufId);

         }

       }

     }

     if(errorMessLL.size()>0) {

       throw new Exception(makeErrorString(errorMessLL));

     }







     //Catalog structure

     dbc = new DBCriteria();

     dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,catalogIdV);

     dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

             RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

     dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID,itemId);

     dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);

     CatalogStructureDataVector catalogStrDV =

             CatalogStructureDataAccess.select(con,dbc);



     int ind = 0;

     for(Iterator iter=catalogStrDV.iterator(); iter.hasNext(); ) {

       CatalogStructureData csD = (CatalogStructureData)iter.next();

       int catalogId = csD.getCatalogId();

       while (ind<itemCatAggrVwA.length) {

         ItemCatalogAggrView icaVw = itemCatAggrVwA[ind];

         int cId = icaVw.getCatalogId();

         if(cId<catalogId) {

           ind++;

           continue;

         }

         if(cId>catalogId) {

           break;

         }

         String distIdS = icaVw.getDistIdInp();

         int distId = 0;

         if(Utility.isSet(distIdS)) {

           distId = Integer.parseInt(distIdS);

         }

         String catalogSku = Utility.strNN(icaVw.getCatalogSkuNumInp());

         String catalogSkuOld = Utility.strNN(csD.getCustomerSkuNum());

   if(distId!=csD.getBusEntityId() || !catalogSku.equals(catalogSkuOld)) {

           csD.setBusEntityId(distId);

           csD.setCustomerSkuNum(catalogSku);

           csD.setModBy(user);

           CatalogStructureDataAccess.update(con,csD);

         }

         ind++;

         continue;

       }

     }



     //Category

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       int catalogId = icaVw.getCatalogId();



       String newCategoryIdS = icaVw.getCategoryIdInp();

       int newCategoryId = 0;

       if(Utility.isSet(newCategoryIdS)) {

         try {

           newCategoryId = Integer.parseInt(newCategoryIdS);

         } catch(Exception exc){

            throw new Exception("^clw^System error: CB006^clw^" +

            " Illegal. Category id: "+newCategoryIdS);

         }

       }



       int oldCategoryId = icaVw.getCategoryId();

       if(newCategoryId!=oldCategoryId && icaVw.getCatalogFlInp()) {

         dbc = new DBCriteria();

         dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID,itemId);

         dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);

         dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                 RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

         ItemAssocDataVector iaDV = ItemAssocDataAccess.select(con,dbc);

         if(iaDV.size()==0 && newCategoryId !=0) {

           ItemAssocData iaD = ItemAssocData.createValue();

           iaD.setItem1Id(itemId);

           iaD.setItem2Id(newCategoryId);

   iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

           iaD.setCatalogId(catalogId);

           iaD.setAddBy(user);

           iaD.setModBy(user);

           ItemAssocDataAccess.insert(con,iaD);

         }

         if(iaDV.size()>0 && newCategoryId==0) {

           ItemAssocDataAccess.remove(con,dbc);

         }

         if(iaDV.size()>0 && newCategoryId!=0) {

           ItemAssocData iaD = (ItemAssocData) iaDV.get(0);

           iaD.setItem2Id(newCategoryId);

           iaD.setModBy(user);

           ItemAssocDataAccess.update(con,iaD);

           if(iaDV.size()>1) {

             dbc.addNotEqualTo(ItemAssocDataAccess.ITEM_ASSOC_ID,

                     iaD.getItemAssocId());

             ItemAssocDataAccess.remove(con,dbc);

           }

         }

       }

       if(newCategoryId!=oldCategoryId || !icaVw.getCatalogFlInp()) {

         dbc = new DBCriteria();

         dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID,oldCategoryId);

         dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID,catalogId);

         dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

                 RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

         IdVector iaIdV = ItemAssocDataAccess.selectIdOnly(con,dbc);

         if(iaIdV.size()==0) {

           dbc = new DBCriteria();

           dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID,oldCategoryId);

           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catalogId);

           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

           CatalogStructureDataAccess.remove(con,dbc, true);

         }

       }

       if(newCategoryId!=oldCategoryId && icaVw.getCatalogFlInp()) {

         dbc = new DBCriteria();

         dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID,newCategoryId);

         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,catalogId);

         dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                 RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

         IdVector csIdV = CatalogStructureDataAccess.selectIdOnly(con,dbc);

         if(csIdV.size()==0) {

           CatalogStructureData csD = CatalogStructureData.createValue();

           csD.setCatalogId(catalogId);

           csD.setItemId(newCategoryId);

   csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

           csD.setAddBy(user);

           csD.setModBy(user);

           CatalogStructureDataAccess.insert(con,csD,true);

         }if(csIdV.size()>1) {

            throw new Exception("^clw^System error: CB007^clw^" +

            " Multiple categories in catalog. Cataltog id: "+

                    catalogId+" Category id: "+newCategoryId);

         }

       }

     }



     //Contract

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       int catalogId = icaVw.getCatalogId();

       int contractId = icaVw.getContractId();

       if(!icaVw.getContractFlInp() || !icaVw.getCatalogFlInp()) {

         continue;

       }

       BigDecimal oldCost = Utility.bdNN(icaVw.getCost());

       BigDecimal oldPrice = Utility.bdNN(icaVw.getPrice());

       BigDecimal oldBaseCost = Utility.bdNN(icaVw.getBaseCost());



       String newCostS = icaVw.getCostInp();

       String newPriceS = icaVw.getPriceInp();

       String newBaseCostS = icaVw.getBaseCostInp();



       BigDecimal newCost = new BigDecimal(0);

       BigDecimal newPrice = new BigDecimal(0);

       BigDecimal newBaseCost = new BigDecimal(0);



       if(Utility.isSet(newCostS)) {

         try {

           double db = Double.parseDouble(newCostS);

           newCost = new BigDecimal(db);

         } catch (Exception exc) {

           errorMessLL.add("Illegal cost format. Sku num: "+icaVw.getSkuNum()+

                   " Cost: "+newCostS);

         }

       }



       if(Utility.isSet(newPriceS)) {

         try {

           double db = Double.parseDouble(newPriceS);

           newPrice = new BigDecimal(db);

         } catch (Exception exc) {

           errorMessLL.add("Illegal price format. Sku num: "+icaVw.getSkuNum()+

                   " Price: "+newPriceS);

         }

       }



       if(Utility.isSet(newBaseCostS)) {

         try {

           double db = Double.parseDouble(newBaseCostS);

           newBaseCost = new BigDecimal(db);

         } catch (Exception exc) {

   errorMessLL.add("Illegal base cost format. Sku num: "+icaVw.getSkuNum()+

                   " Cost: "+newBaseCostS);

         }

       }

       if(oldPrice.subtract(newPrice).abs().doubleValue()>0.00001 ||

          oldCost.subtract(newCost).abs().doubleValue()>0.00001 ||

          oldBaseCost.subtract(newBaseCost).abs().doubleValue()>0.00001) {

         dbc = new DBCriteria();

         dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID,contractId);

         dbc.addEqualTo(ContractItemDataAccess.ITEM_ID,itemId);

         ContractItemDataVector ciDV = ContractItemDataAccess.select(con,dbc);

         if(ciDV.size()>1) {

            throw new Exception("^clw^System error: CB008^clw^" +

            " Multiple contract item records. Contract id: "+

                    contractId+" Item id: "+itemId);

         }

         if(ciDV.size()==0) {

            throw new Exception("^clw^System error: CB009^clw^" +

            " No contract item records. Contract id: "+

                    contractId+" Item id: "+itemId);

         }

         ContractItemData ciD = (ContractItemData) ciDV.get(0);

         ciD.setAmount(newPrice);

         ciD.setDistCost(newCost);

         ciD.setDistBaseCost(newBaseCost);

         ciD.setModBy(user);

         ContractItemDataAccess.update(con,ciD,true);

       }

     }

     if(errorMessLL.size()>0) {

       throw new Exception(makeErrorString(errorMessLL));

     }



     //Dustributor

     String itemUom = "";

     String itemPack = "";

     HashMap itemDistHM = new HashMap();

     dbc = new DBCriteria();

     dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID,itemId);

     dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, distIdV);

     dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

             RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

   ItemMappingDataVector distItemMapDV = ItemMappingDataAccess.select(con,dbc);

     for(Iterator iter=distItemMapDV.iterator(); iter.hasNext();) {

       ItemMappingData imD = (ItemMappingData) iter.next();

       imD.setDirty(false);

       itemDistHM.put(new Integer(imD.getBusEntityId()),imD);

     }

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       if(!icaVw.getCatalogFlInp()) {

         continue;

       }



       String newDistIdS = icaVw.getDistIdInp();

       int newDistId = 0;

       if(Utility.isSet(newDistIdS)) {

         newDistId = Integer.parseInt(newDistIdS);

       }

       if(newDistId==0) {

         continue;

       }

       String newDistConvS = icaVw.getDistConversInp();

       BigDecimal newDistConv = new BigDecimal(1);

       if(Utility.isSet(newDistConvS)) {

         try {

   newDistConv = new BigDecimal(Double.parseDouble(newDistConvS));

         } catch (Exception exc) {

           errorMessLL.add("Illegal distibutor uom multiplier. Sku num: "+icaVw.getSkuNum()+

                   " Mutiplier: "+newDistConvS);

         }

       }

       String newDistSkuNum = Utility.strNN(icaVw.getDistSkuNumInp());

       String newDistSkuUom = Utility.strNN(icaVw.getDistSkuUomInp());

       boolean newDistSPLFl = icaVw.getDistSPLFlInp();

       String newDistSkuPack = Utility.strNN(icaVw.getDistSkuPackInp());



       BigDecimal oldDistConv = icaVw.getDistConvers();

       if(oldDistConv==null) oldDistConv = new BigDecimal(1);

       String oldDistSkuNum = Utility.strNN(icaVw.getDistSkuNum());

       String oldDistSkuUom = Utility.strNN(icaVw.getDistSkuUom());

       boolean oldDistSPLFl = icaVw.getDistSPLFl();

       String oldDistSkuPack = Utility.strNN(icaVw.getDistSkuPack());

       if(oldDistSkuNum.equals(newDistSkuNum) &&

          oldDistSkuUom.equals(newDistSkuUom) &&

          oldDistSPLFl == newDistSPLFl &&

          oldDistSkuPack.equals(newDistSkuPack)&&

          oldDistConv.subtract(newDistConv).abs().doubleValue()<0.0001) {

         continue;

       }

   ItemMappingData imD = (ItemMappingData) itemDistHM.get(new Integer(newDistId));

       if(imD==null) {

         imD = ItemMappingData.createValue();

         imD.setBusEntityId(newDistId);

         imD.setItemId(itemId);

         imD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

         imD.setItemNum(newDistSkuNum);

         if(Utility.isSet(newDistSkuPack) || Utility.isSet(newDistSkuUom)) {

           imD.setItemPack(newDistSkuPack);

           imD.setItemUom(newDistSkuUom);

           imD.setUomConvMultiplier(newDistConv);

         } else {

           if(itemPack.length()==0) {

             dbc = new DBCriteria();

             dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID,itemId);

             dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"PACK");

             ItemMetaDataVector imetaDV = ItemMetaDataAccess.select(con,dbc);

             if(imetaDV.size()>0) {

               ItemMetaData imetaD = (ItemMetaData) imetaDV.get(0);

               itemPack = Utility.strNN(imetaD.getValue());

             }

           }

           if(itemUom.length()==0) {

             dbc = new DBCriteria();

             dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID,itemId);

             dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"UOM");

             ItemMetaDataVector imetaDV = ItemMetaDataAccess.select(con,dbc);

             if(imetaDV.size()>0) {

               ItemMetaData imetaD = (ItemMetaData) imetaDV.get(0);

               itemUom = Utility.strNN(imetaD.getValue());

             }

           }

           imD.setItemPack(itemPack);

           imD.setItemUom(itemUom);

           imD.setUomConvMultiplier(new BigDecimal(1));

         }

         imD.setStandardProductList(Boolean.toString(newDistSPLFl));

         imD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.AVAILABLE);

         imD.setAddBy(user);

         imD.setModBy(user);

         imD = ItemMappingDataAccess.insert(con,imD);

         itemDistHM.put(new Integer(newDistId),imD);

       } else {

         BigDecimal prevDistConv = imD.getUomConvMultiplier();

         if(prevDistConv==null) prevDistConv = new BigDecimal(1);

         String prevDistSkuNum = Utility.strNN(imD.getItemNum());

         String prevDistSkuUom = Utility.strNN(imD.getItemUom());

         boolean prevDistSPLFl = Utility.isTrue(imD.getStandardProductList());

         String prevDistSkuPack = Utility.strNN(imD.getItemPack());

         if(!prevDistSkuNum.equals(newDistSkuNum) ||

            !prevDistSkuUom.equals(newDistSkuUom) ||

            prevDistSPLFl != newDistSPLFl   ||

            !prevDistSkuPack.equals(newDistSkuPack)||

             prevDistConv.subtract(newDistConv).abs().doubleValue()>0.0001) {

           if(imD.isDirty()) {

             String errorMess = "No distributor sku parameters consistency. Distributor id: "+newDistId;

             if(!errorMessLL.contains(errorMess)) {

               errorMessLL.add(errorMess);

             }

           } else {

             imD.setItemNum(newDistSkuNum);

             imD.setItemPack(newDistSkuPack);

             imD.setItemUom(newDistSkuUom);

             imD.setStandardProductList(Boolean.toString(newDistSPLFl));

             imD.setUomConvMultiplier(newDistConv);

             imD.setModBy(user);

           }

         }

       }

     }

     if(errorMessLL.size()>0) {

       throw new Exception(makeErrorString(errorMessLL));

     }



     Collection itemDistColl = itemDistHM.values();

     for(Iterator iter=itemDistColl.iterator(); iter.hasNext();) {

       ItemMappingData imD = (ItemMappingData) iter.next();

       if(!imD.isDirty()) {

         continue;

       }

       if(imD.getItemMappingId()==0) {

         ItemMappingDataAccess.insert(con,imD);

       } else {

         ItemMappingDataAccess.update(con,imD);

       }

     }



     //Item Generic Manufacturer

     HashMap distGenManufHM = new HashMap();

     for(int ii=0; ii<itemCatAggrVwA.length; ii++) {

       ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

       if(!icaVw.getCatalogFlInp()) {

         continue;

       }

       String newDistIdS = icaVw.getDistIdInp();

       int newDistId = (Utility.isSet(newDistIdS))?

                          Integer.parseInt(newDistIdS):0;

       if(newDistId==0) {

         continue;

       }



       String newGenManufIdS = icaVw.getGenManufIdInp();

       int newGenManufId = (Utility.isSet(newGenManufIdS))?

                          Integer.parseInt(newGenManufIdS):0;

       String newGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNumInp());



       int oldGenManufId = icaVw.getGenManufId();

       String oldGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNum());

       if(oldGenManufId==newGenManufId && oldGenManufSkuNum.equals(newGenManufSkuNum)) {

         continue;

       }



      Integer newDistIdI = new Integer(newDistId);

        ItemCatalogAggrView icaVw1 =

                (ItemCatalogAggrView) distGenManufHM.get(newDistIdI);

       if(icaVw1==null) {

         distGenManufHM.put(newDistIdI,icaVw);

       } else {

         String newGenManufIdS1 = icaVw1.getGenManufIdInp();

         int newGenManufId1 = (Utility.isSet(newGenManufIdS1))?

                          Integer.parseInt(newGenManufIdS1):0;

   String newGenManufSkuNum1 = Utility.strNN(icaVw1.getGenManufSkuNumInp());

         if(newGenManufId!=newGenManufId1) {

           String errorMess = "No genenric manufacturer consistency. Distributor id: "+newDistId;

           if(!errorMessLL.contains(errorMess)) {

             errorMessLL.add(errorMess);

           }

           continue;

         }

         if(oldGenManufSkuNum.equals(newGenManufSkuNum)) {

           continue;

         }



         String oldGenManufSkuNum1 = Utility.strNN(icaVw1.getGenManufSkuNum());

         if(oldGenManufSkuNum1.equals(newGenManufSkuNum1)) {

           distGenManufHM.put(newDistIdI,icaVw);

           continue;

         }



         if(!newGenManufSkuNum.equals(newGenManufSkuNum1)) {

           String errorMess = "No genenric manufacturer consistency. Distributor id: "+newDistId;

           if(!errorMessLL.contains(errorMess)) {

             errorMessLL.add(errorMess);

           }

         }

       }

     }



     if(errorMessLL.size()>0) {

       throw new Exception(makeErrorString(errorMessLL));

     }



     Collection itemCataAggrVwCL = distGenManufHM.values();

     for(Iterator iter=itemCataAggrVwCL.iterator();iter.hasNext();) {

       ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();

       int oldGenManufId = icaVw.getGenManufId();

       String newGenManufIdS = icaVw.getGenManufIdInp();

       int newGenManufId = (Utility.isSet(newGenManufIdS))?

                          Integer.parseInt(newGenManufIdS):0;

       int oldDistId = icaVw.getDistId();

       String newDistIdS = icaVw.getDistIdInp();

       int newDistId = (Utility.isSet(newDistIdS))?

                          Integer.parseInt(newDistIdS):0;

       if(newDistId==0) {

         continue;

       }

       String genManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNum());

       String newGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNumInp());

       if(oldGenManufId==newGenManufId && oldDistId==newDistId &&

          genManufSkuNum.equals(newGenManufSkuNum)) {

         continue;

       }

       ItemMappingData distItemMapD = (ItemMappingData) itemDistHM.get(new Integer(newDistId));



       if(newGenManufId==0) {

         dbc = new DBCriteria();

         dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID, distItemMapD.getItemMappingId());

         dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,

                 RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);

   IdVector genManufItemMapIdV = ItemMappingAssocDataAccess.selectIdOnly(con,

                 ItemMappingAssocDataAccess.ITEM_MAPPING2_ID,dbc);

         ItemMappingAssocDataAccess.remove(con,dbc);



         dbc = new DBCriteria();

   dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID,genManufItemMapIdV);

         ItemMappingDataAccess.remove(con,dbc);

       } else {

         dbc = new DBCriteria();

         dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID,

   distItemMapD.getItemMappingId());

         dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,

                 RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);

   IdVector genManufItemMapAssocIdV = ItemMappingAssocDataAccess.selectIdOnly(con,

                 ItemMappingAssocDataAccess.ITEM_MAPPING2_ID,dbc);

         if(genManufItemMapAssocIdV.size()==0) {

           ItemMappingData genManufItemMapD = ItemMappingData.createValue();

           genManufItemMapD.setBusEntityId(newGenManufId);

           genManufItemMapD.setItemId(itemId);

           genManufItemMapD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_GENERIC_MFG);

           genManufItemMapD.setItemNum(icaVw.getGenManufSkuNumInp());

           genManufItemMapD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.AVAILABLE);

           genManufItemMapD.setAddBy(user);

           genManufItemMapD.setModBy(user);

   genManufItemMapD = ItemMappingDataAccess.insert(con,genManufItemMapD);



           ItemMappingAssocData imaD = ItemMappingAssocData.createValue();

           imaD.setItemMapping1Id(distItemMapD.getItemMappingId());

           imaD.setItemMapping2Id(genManufItemMapD.getItemMappingId());

   imaD.setItemMappingAssocCd(RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);

           imaD.setAddBy(user);

           imaD.setModBy(user);

           ItemMappingAssocDataAccess.insert(con,imaD);

         } else {

           Integer imaIdI = (Integer) genManufItemMapAssocIdV.get(0);

           int imaId = imaIdI.intValue();

           ItemMappingData imD = ItemMappingDataAccess.select(con,imaId);

           int genManufId = imD.getBusEntityId();

           genManufSkuNum = Utility.strNN(imD.getItemNum());

   if(genManufId!=newGenManufId || !genManufSkuNum.equals(newGenManufSkuNum)) {

             imD.setBusEntityId(newGenManufId);

             imD.setItemNum(newGenManufSkuNum);

             imD.setModBy(user);

             ItemMappingDataAccess.update(con,imD);

           }

           if(genManufItemMapAssocIdV.size()>1) {

             dbc = new DBCriteria();

             genManufItemMapAssocIdV.remove(0);

             dbc.addOneOf(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_ID,

                                             genManufItemMapAssocIdV);

             IdVector imIdV = ItemMappingAssocDataAccess.selectIdOnly(con,

                     ItemMappingAssocDataAccess.ITEM_MAPPING2_ID,dbc);

             ItemMappingAssocDataAccess.remove(con,dbc);



             dbc = new DBCriteria();

             dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID,imIdV);

             ItemMappingDataAccess.remove(con,dbc);

           }

         }

       }

     }



     return;

    }catch(Exception exc) {

      logError(exc.getMessage());

      exc.printStackTrace();

      throw new RemoteException(exc.getMessage());

    }finally{

      try {

   con.close();

      }catch (SQLException exc) {

   logError("exc.getMessage");

   exc.printStackTrace();

   throw new RemoteException("Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

      }

    }

    }

   */

  /**

   * Saves item - catalog,item - contract, item  - distributor,

   * item - distributor - genenric manufacture, item - contract data

   * @param itemAggrSet set of ItemCatalogAggrView objects

   * @param user - user login name

   * @param pItemTypeCd - item type

   * @throws            java.rmi.RemoteException Required by EJB 1.0 and DataNotFoundException

   */

  public void saveItemCatalogMgrSet1(ItemCatalogAggrViewVector itemAggrSet,

                                     String user, String pItemTypeCd) throws RemoteException {

    Connection con = null;

    PairViewVector categCatalogToDelVwV = new PairViewVector();

    try {

      con = getConnection();

      DBCriteria dbc;

      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

      Date today = sdf.parse(sdf.format(new Date()));

      HashMap orderGuideIdHM = new HashMap();

      HashMap templateOrderGuideIdHM = new HashMap();

      IdVector distIdV = new IdVector();

      IdVector manufIdV = new IdVector();

      LinkedList errorMessLL = new LinkedList();

      ItemCatalogAggrView[] itemCatAggrVwA =

        (ItemCatalogAggrView[]) itemAggrSet.toArray(new ItemCatalogAggrView[0]);



      if (itemCatAggrVwA.length > 1) {

        for (int ii = 0; ii < itemCatAggrVwA.length - 1; ii++) {

          boolean exitFl = true;

          for (int jj = 0; jj < itemCatAggrVwA.length - 1 - ii; jj++) {

            ItemCatalogAggrView icaVw1 = itemCatAggrVwA[jj];

            ItemCatalogAggrView icaVw2 = itemCatAggrVwA[jj + 1];

            int cId1 = icaVw1.getCatalogId();

            int cId2 = icaVw2.getCatalogId();

            if (cId1 > cId2) {

              itemCatAggrVwA[jj] = icaVw2;

              itemCatAggrVwA[jj + 1] = icaVw1;

              exitFl = false;

            } else if (cId1 == cId2) {

              int iId1 = icaVw1.getItemId();

              int iId2 = icaVw2.getItemId();

              if (iId1 > iId2) {

                itemCatAggrVwA[jj] = icaVw2;

                itemCatAggrVwA[jj + 1] = icaVw1;

                exitFl = false;

              }

            }

          }

          if (exitFl)break;

        }

      }



      IdVector catalogIdV = new IdVector();

      IdVector contractIdV = new IdVector();

      IdVector itemIdV = new IdVector();

      HashSet orderGuideHS = new HashSet();

      int catalogQty = 0;

      int itemQty = 0;

      int prevCatalogId = -1;

      int prevItemId = -1;

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        int catalogId = icaVw.getCatalogId();

        int itemId = icaVw.getItemId();

        if (catalogId != prevCatalogId) {

          catalogQty++;

          catalogIdV.add(new Integer(catalogId));

          contractIdV.add(new Integer(icaVw.getContractId()));

          prevCatalogId = catalogId;



        }

        if (itemId != prevItemId) {

          itemQty++;

          itemIdV.add(new Integer(itemId));

          prevItemId = itemId;

        }

        int ogId = icaVw.getOrderGuideId();

        Integer ogIdI = new Integer(ogId);

        if (!orderGuideHS.contains(ogIdI)) orderGuideHS.add(ogIdI);

      }



      if (catalogQty > 1 && itemQty > 1) {

        String errorMess =

          "Current version doesn't support multiple catalogs together with multiple skus";

        errorMessLL.add(errorMess);

        throw new Exception(makeErrorString(errorMessLL));

      }



      //Get store

      dbc = new DBCriteria();

      dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, catalogIdV);

      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con,

        CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

      if (storeIdV.size() > 1) {

        throw new Exception("^clw^Catalogs belong to multiple stores^clw^");

      }

      Integer storeIdI = (Integer) storeIdV.get(0);

      int storeId = storeIdI.intValue();

      int storeCatId = BusEntityDAO.getStoreCatalogId(con, storeId);



      //Order gudies

      dbc = new DBCriteria();

      IdVector orderGuideIdV = new IdVector();

      for (Iterator iter = orderGuideHS.iterator(); iter.hasNext(); ) {

        orderGuideIdV.add(iter.next());

      }

      dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, orderGuideIdV);

      dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, itemIdV);

      OrderGuideStructureDataVector orderGuideStructDV =

        OrderGuideStructureDataAccess.select(con, dbc);



      HashMap itemOgHM = new HashMap();

      for (Iterator iter = orderGuideStructDV.iterator(); iter.hasNext(); ) {

        OrderGuideStructureData ogsD = (OrderGuideStructureData) iter.next();

        String key = ogsD.getItemId() + ":" + ogsD.getOrderGuideId();

        itemOgHM.put(key, ogsD);

      }



      IdVector removeOgStrIdV = new IdVector();

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        int ogId = icaVw.getOrderGuideId();

        if (icaVw.getOrderGuideFl() != icaVw.getOrderGuideFlInp()) {

          if (ogId > 0) {

            String key = icaVw.getItemId() + ":" + icaVw.getOrderGuideId();

            if (icaVw.getOrderGuideFlInp() &&

                icaVw.getCatalogFlInp() &&

                icaVw.getContractFlInp()) {

              if (!itemOgHM.containsKey(key)) {

                OrderGuideStructureData ogsD = OrderGuideStructureData.

                                               createValue();

                ogsD.setOrderGuideId(icaVw.getOrderGuideId());

                ogsD.setItemId(icaVw.getItemId());

                ogsD.setAddBy(user);

                ogsD.setModBy(user);

                ogsD = OrderGuideStructureDataAccess.insert(con, ogsD);

                itemOgHM.put(key, ogsD);

              }

            }

            if (!icaVw.getOrderGuideFlInp() ||

                !icaVw.getContractFlInp() ||

                !icaVw.getCatalogFlInp()) {

              if (itemOgHM.containsKey(key)) {

                OrderGuideStructureData ogsD = (OrderGuideStructureData)

                                               itemOgHM.get(key);

                removeOgStrIdV.add(new Integer(ogsD.getOrderGuideStructureId()));

              }

            }

          } else {

            if (RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(icaVw.

              getCatalogType())) {

              String errorMess = "No order guide for catalog: " +

                                 icaVw.getCatalogId();

              errorMessLL.add(errorMess);

            } else {

              icaVw.setOrderGuideFlInp(false);

            }

          }

        }

      }



      if (removeOgStrIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_STRUCTURE_ID,

                     removeOgStrIdV);

        OrderGuideStructureDataAccess.remove(con, dbc);

      }



      //Contracts

      dbc = new DBCriteria();

      dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);

      dbc.addOneOf(ContractItemDataAccess.ITEM_ID, itemIdV);

      ContractItemDataVector contractItemDV =

        ContractItemDataAccess.select(con, dbc);



      HashMap itemContractHM = new HashMap();

      for (Iterator iter = contractItemDV.iterator(); iter.hasNext(); ) {

        ContractItemData ciD = (ContractItemData) iter.next();

        String key = ciD.getItemId() + ":" + ciD.getContractId();

        itemContractHM.put(key, ciD);

      }



      IdVector removeContractItemIdV = new IdVector();

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        int cId = icaVw.getContractId();

        if (icaVw.getContractFl() != icaVw.getContractFlInp()) {

          if (cId > 0) {

            String key = icaVw.getItemId() + ":" + icaVw.getContractId();

            if (icaVw.getCatalogFlInp() &&

                icaVw.getContractFlInp()) {

              if (!itemContractHM.containsKey(key)) {

                ContractItemData ciD = ContractItemData.createValue();

                ciD.setContractId(icaVw.getContractId());

                ciD.setItemId(icaVw.getItemId());

                ciD.setAmount(new BigDecimal(0));

                ciD.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);

                ciD.setDistBaseCost(new BigDecimal(0));

                ciD.setDistCost(new BigDecimal(0));

                ciD.setModBy(user);

                ciD.setAddBy(user);

                ciD = ContractItemDataAccess.insert(con, ciD);

                itemContractHM.put(key, ciD);

              }

            }

            if (!icaVw.getContractFlInp() ||

                !icaVw.getCatalogFlInp()) {

              if (itemContractHM.containsKey(key)) {

                ContractItemData ciD = (ContractItemData) itemContractHM.get(

                  key);

                removeContractItemIdV.add(new Integer(ciD.getContractItemId()));

              }

            }

          } else {

            String errorMess = "Price could not be configured for catalog: " +

                               icaVw.getCatalogId();

            errorMessLL.add(errorMess);



          }

        }

      }

      if (removeContractItemIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(ContractItemDataAccess.CONTRACT_ITEM_ID,

                     removeContractItemIdV);

        ContractItemDataAccess.remove(con, dbc);

      }



      //Catalogs

      dbc = new DBCriteria();

      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogIdV);

      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIdV);

      CatalogStructureDataVector catalogItemDV =

        CatalogStructureDataAccess.select(con, dbc);



      HashMap itemCatalogHM = new HashMap();

      for (Iterator iter = catalogItemDV.iterator(); iter.hasNext(); ) {

        CatalogStructureData csD = (CatalogStructureData) iter.next();

        String key = csD.getItemId() + ":" + csD.getCatalogId();

        itemCatalogHM.put(key, csD);

      }



      dbc = new DBCriteria();

      dbc.addOneOf(ItemAssocDataAccess.CATALOG_ID, catalogIdV);

      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, itemIdV);

      ItemAssocDataVector itemAssocDV =

        ItemAssocDataAccess.select(con, dbc);



      HashMap itemAssocHM = new HashMap();

      for (Iterator iter = itemAssocDV.iterator(); iter.hasNext(); ) {

        ItemAssocData csD = (ItemAssocData) iter.next();

        String key = csD.getItem1Id() + ":" + csD.getCatalogId();

        itemAssocHM.put(key, csD);

      }



      IdVector removeCatalogStructureIdV = new IdVector();

      IdVector removeItemAssocIdV = new IdVector();

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        int cId = icaVw.getCatalogId();

        if (cId > 0 &&

            icaVw.getCatalogFl() != icaVw.getCatalogFlInp()) {

          String key = icaVw.getItemId() + ":" + icaVw.getCatalogId();

          if (icaVw.getCatalogFlInp()) {

            if (!itemCatalogHM.containsKey(key)) {

              CatalogStructureData csD = CatalogStructureData.createValue();

              csD.setCatalogId(icaVw.getCatalogId());

              csD.setItemId(icaVw.getItemId());

              csD.setTaxExempt("" + icaVw.getTaxExemptFlInp());

              if(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(icaVw.getCatalogType())){

                   csD.setSpecialPermission("" + icaVw.getSpecialPermissionFlInp());

              }

              csD.setAddBy(user);

              csD.setModBy(user);

              csD.setEffDate(today);

              csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

              if (RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd)) {

                  csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);

              } else {

                  csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

              }

              csD = CatalogStructureDataAccess.insert(con, csD, true);

              itemCatalogHM.put(key, csD);

            }

          }

          if (!icaVw.getCatalogFlInp()) {

            if (itemCatalogHM.containsKey(key)) {

              CatalogStructureData csD = (CatalogStructureData) itemCatalogHM.

                                         get(key);

              removeCatalogStructureIdV.add(new Integer(csD.

                getCatalogStructureId()));

            }

            if (itemAssocHM.containsKey(key)) {

              ItemAssocData iaD = (ItemAssocData) itemAssocHM.get(key);

              removeItemAssocIdV.add(new Integer(iaD.getItemAssocId()));

              if(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY.equals(iaD.getItemAssocCd())) {

                  PairView aa = new PairView(iaD.getItem2Id(),cId);

                  categCatalogToDelVwV.add(aa);

              }

            }

          }

        }

      }

      if (removeCatalogStructureIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(CatalogStructureDataAccess.CATALOG_STRUCTURE_ID,

                     removeCatalogStructureIdV);

        CatalogStructureDataAccess.remove(con, dbc, true);

      }

      if (removeItemAssocIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_ID, removeItemAssocIdV);

        ItemAssocDataAccess.remove(con, dbc);

      }



      //Get distributors and manufacturers

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        if (icaVw.getCatalogFlInp()) {

          String distIdS = icaVw.getDistIdInp();

          if (Utility.isSet(distIdS)) {

            try {

              int distId = Integer.parseInt(distIdS);

              Integer distIdI = new Integer(distId);

              if (!distIdV.contains(distIdI)) {

                distIdV.add(distIdI);

              }

            } catch (Exception exc) {

              errorMessLL.add("Illegal distributor id: " + distIdS);

            }

            String genManufIdS = icaVw.getGenManufIdInp();

            if (Utility.isSet(genManufIdS)) {

              try {

                int genManufId = Integer.parseInt(genManufIdS);

                Integer genManufIdI = new Integer(genManufId);

                if (!manufIdV.contains(genManufIdI)) {

                  manufIdV.add(genManufIdI);

                }

              } catch (Exception exc) {

                errorMessLL.add("Illegal manufacturer id: " + genManufIdS);

              }

            }

          }

        }

      }

      if (errorMessLL.size() > 0) {

        throw new Exception(makeErrorString(errorMessLL));

      }



      dbc = new DBCriteria();

      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeId);

      dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,

                     RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);

      String storeDistReq =

        BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.

        BUS_ENTITY1_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);

      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                     RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, storeDistReq);



      BusEntityDataVector distBusEntDV =

        BusEntityDataAccess.select(con, dbc);

      if (distBusEntDV.size() != distIdV.size()) {

        for (Iterator iter = distIdV.iterator(); iter.hasNext(); ) {

          Integer distIdI = (Integer) iter.next();

          int distId = distIdI.intValue();

          boolean foundFl = false;

          for (Iterator iter1 = distBusEntDV.iterator(); iter1.hasNext(); ) {

            BusEntityData beD = (BusEntityData) iter1.next();

            if (beD.getBusEntityId() == distId) {

              foundFl = true;

              break;

            }

          }

          if (!foundFl) {

            errorMessLL.add("Distributor not found. Distributor id: " + distId);

          }

        }

      }



      dbc = new DBCriteria();

      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, manufIdV);

      dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,

                     RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);



      BusEntityDataVector manufBusEntDV =

        BusEntityDataAccess.select(con, dbc);

      if (manufBusEntDV.size() != manufIdV.size()) {

        for (Iterator iter = manufIdV.iterator(); iter.hasNext(); ) {

          Integer manufIdI = (Integer) iter.next();

          int manufId = manufIdI.intValue();

          boolean foundFl = false;

          for (Iterator iter1 = distBusEntDV.iterator(); iter1.hasNext(); ) {

            BusEntityData beD = (BusEntityData) iter1.next();

            if (beD.getBusEntityId() == manufId) {

              foundFl = true;

              break;

            }

          }

          if (!foundFl) {

            errorMessLL.add("Manufacturer not found. Manufacturer id: " +

                            manufId);

          }

        }

      }

      if (errorMessLL.size() > 0) {

        throw new Exception(makeErrorString(errorMessLL));

      }



      //Catalog structure

      dbc = new DBCriteria();

      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogIdV);

        if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd))

        {

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);

        }

        else{

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

        }

      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemIdV);

      dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);

      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

      CatalogStructureDataVector catalogStrDV =

        CatalogStructureDataAccess.select(con, dbc);



      int ind = 0;

      for (Iterator iter = catalogStrDV.iterator(); iter.hasNext(); ) {

        CatalogStructureData csD = (CatalogStructureData) iter.next();

        int catalogId = csD.getCatalogId();

        int itemId = csD.getItemId();

        while (ind < itemCatAggrVwA.length) {

          ItemCatalogAggrView icaVw = itemCatAggrVwA[ind];

          int cId = icaVw.getCatalogId();

          int iId = icaVw.getItemId();

          if (cId < catalogId || iId < itemId) {

            ind++;

            continue;

          }

          if (cId > catalogId || iId > itemId) {

            break;

          }

          String distIdS = icaVw.getDistIdInp();

          int distId = 0;

          if (Utility.isSet(distIdS)) {

            distId = Integer.parseInt(distIdS);

          }

          String catalogSku = Utility.strNN(icaVw.getCatalogSkuNumInp());

          String prevCatalogSku = Utility.strNN(icaVw.getCatalogSkuNum());

          String catalogSkuOld = Utility.strNN(csD.getCustomerSkuNum());

          boolean taxExemptFl = icaVw.getTaxExemptFlInp();

          boolean prevTaxExemptFl = icaVw.getTaxExemptFl();

          boolean taxExemptFlOld = false;

          String taxExemptStrOld = csD.getTaxExempt();

          if (taxExemptStrOld != null) {

            taxExemptStrOld = taxExemptStrOld.trim().toUpperCase();

            if (taxExemptStrOld.startsWith("Y") ||

                taxExemptStrOld.startsWith("T") ||

                taxExemptStrOld.equals("1")) {

              taxExemptFlOld = true;

            }

          }



          boolean specialPermissionFl = icaVw.getSpecialPermissionFlInp();

          boolean prevSpecialPermissionFl = icaVw.getSpecialPermissionFl();

          boolean specialPermissionFlOld = Utility.isTrue(csD.getSpecialPermission());



          //multi product

          String newMultiproductIdS = icaVw.getMultiproductIdInp();

          int newMultiproductId = 0;

          if (Utility.isSet(newMultiproductIdS)) {

            try {

              newMultiproductId = Integer.parseInt(newMultiproductIdS);

            } catch (Exception exc) {

              throw new Exception("^clw^System error: CB006^clw^" +

                                  " Illegal. Multi product id: " + newMultiproductIdS);

            }

          }

          int oldMultiproductId = icaVw.getMultiproductId();



          if ((distId != icaVw.getDistId() && distId != csD.getBusEntityId()) ||

              (!catalogSku.equals(prevCatalogSku) &&

               !catalogSku.equals(catalogSkuOld)) ||

              (taxExemptFl != prevTaxExemptFl && taxExemptFl != taxExemptFlOld) ||

              (newMultiproductId != oldMultiproductId && icaVw.getCatalogFlInp()) ||

              (RefCodeNames.CATALOG_TYPE_CD.ACCOUNT.equals(icaVw.getCatalogType()) &&

                      (specialPermissionFl != prevSpecialPermissionFl && specialPermissionFl != specialPermissionFlOld))) {

            csD.setBusEntityId(distId);

            csD.setCustomerSkuNum(catalogSku);

            csD.setTaxExempt("" + taxExemptFl);

            csD.setSpecialPermission("" + specialPermissionFl);

            csD.setModBy(user);

            csD.setItemGroupId(newMultiproductId);

            CatalogStructureDataAccess.update(con, csD, true);

          }

          ind++;

          continue;

        }

      }



      //Category

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        int catalogId = icaVw.getCatalogId();

        int itemId = icaVw.getItemId();

        String newCategoryIdS = icaVw.getCategoryIdInp();

        int newCategoryId = 0;

        if (Utility.isSet(newCategoryIdS)) {

          try {

            newCategoryId = Integer.parseInt(newCategoryIdS);

          } catch (Exception exc) {

            throw new Exception("^clw^System error: CB006^clw^" +

                                " Illegal. Category id: " + newCategoryIdS);

          }

        }

        String newMultiproductIdS = icaVw.getMultiproductIdInp();

        int newMultiproductId = 0;

        if (Utility.isSet(newMultiproductIdS)) {

          try {

            newMultiproductId = Integer.parseInt(newMultiproductIdS);

          } catch (Exception exc) {

                throw new Exception("^clw^System error: CB006^clw^" +

                                " Illegal. Multi product id: " + newMultiproductIdS);

          }

        }



        int oldCategoryId = icaVw.getCategoryId();

        if (newCategoryId != oldCategoryId && icaVw.getCatalogFlInp()) {

          dbc = new DBCriteria();

          dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);

          dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);

          if(oldCategoryId>0) {

                PairView aa = new PairView(oldCategoryId,catalogId);

                categCatalogToDelVwV.add(aa);

          }



          //  if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd))

          //  {

          //      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);

          //  }

          //  else

          //  {

                dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

          //  }



          ItemAssocDataVector iaDV = ItemAssocDataAccess.select(con, dbc);

          if (iaDV.size() == 0 && newCategoryId != 0) {

            ItemAssocData iaD = ItemAssocData.createValue();

            iaD.setItem1Id(itemId);

            iaD.setItem2Id(newCategoryId);

            //if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd))

            //{

            //    iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);

            //}

            //  else

            //{

            iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

            //}

            iaD.setCatalogId(catalogId);

            iaD.setAddBy(user);

            iaD.setModBy(user);

            ItemAssocDataAccess.insert(con, iaD);

          }

          if (iaDV.size() > 0 && newCategoryId == 0) {

            ItemAssocDataAccess.remove(con, dbc);

          }

          if (iaDV.size() > 0 && newCategoryId != 0) {

            ItemAssocData iaD = (ItemAssocData) iaDV.get(0);

            iaD.setItem2Id(newCategoryId);

            iaD.setModBy(user);

            ItemAssocDataAccess.update(con, iaD);

            if (iaDV.size() > 1) {

              dbc.addNotEqualTo(ItemAssocDataAccess.ITEM_ASSOC_ID,

                                iaD.getItemAssocId());

              ItemAssocDataAccess.remove(con, dbc);

            }

          }

        }

        //remove categories with no chidren

        if (newCategoryId != oldCategoryId || !icaVw.getCatalogFlInp()) {

//            tryToRemoveCategoryFromCatalog(con, catalogId, oldCategoryId, pItemTypeCd);

            if (catalogId > 0 && oldCategoryId > 0) {

                PairView aa = new PairView(oldCategoryId,catalogId);

                categCatalogToDelVwV.add(aa);

            }

//          dbc = new DBCriteria();

//          dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, oldCategoryId);

//          dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);

//            if(RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd))

//            {

//                dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

//                        RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);

//            }

//            else

//            {

//            	dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,

//                        RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

//            }

//          IdVector iaIdV = ItemAssocDataAccess.selectIdOnly(con, dbc);

//          if (iaIdV.size() == 0) {

//        	  //check if there is a parent category

//        	  dbc = new DBCriteria();

//        	  dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, oldCategoryId);

//        	  dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, storeCatId);

//        	  dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

//        	  String sql = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM1_ID, dbc);

//        	  dbc = new DBCriteria();

//        	  //...and if there are items in said category

//        	  //XXX should support more than 1 level!

//        	  dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID, sql);

//        	  dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);

//        	  dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

//        	  iaIdV = ItemAssocDataAccess.selectIdOnly(con, dbc);

//        	  if (iaIdV.size() == 0) {

//	              dbc = new DBCriteria();

//	              dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, oldCategoryId);

//	              dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);

//	              dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

//	              CatalogStructureDataAccess.remove(con, dbc, true);

//        	  }

//          }

        }

        if (newCategoryId != oldCategoryId && icaVw.getCatalogFlInp()) {

          dbc = new DBCriteria();

          dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, newCategoryId);

          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);

          dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                         RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

          IdVector csIdV = CatalogStructureDataAccess.selectIdOnly(con, dbc);

          if (csIdV.size() == 0) {
        	  addCategoryWithParentCategoriesToCatalogs(Utility.toIdVector(catalogId), newCategoryId, user);
          }

          if (csIdV.size() > 1) {

            throw new Exception("^clw^System error: CB007^clw^" +

                                " Multiple categories in catalog. Cataltog id: " +

                                catalogId + " Category id: " + newCategoryId);

          }

        }

      }



      //Multi product

      if(RefCodeNames.ITEM_TYPE_CD.PRODUCT.equals("pItemTypeCd")) {

          for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

            ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

            int catalogId = icaVw.getCatalogId();

            int itemId = icaVw.getItemId();

            String newMultiproductIdS = icaVw.getMultiproductIdInp();

            int newMultiproductId = 0;

            if (Utility.isSet(newMultiproductIdS)) {

              try {

                newMultiproductId = Integer.parseInt(newMultiproductIdS);

              } catch (Exception exc) {

                throw new Exception("^clw^System error: CB006^clw^" +

                                    " Illegal. Multi product id: " + newMultiproductIdS);

              }

            }



            int oldMultiproductId = icaVw.getMultiproductId();

            if (newMultiproductId != oldMultiproductId && icaVw.getCatalogFlInp()) {

              dbc = new DBCriteria();

              dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);

              dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalogId);



              dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.ITEM_GROUP_ITEM);



              ItemAssocDataVector iaDV = ItemAssocDataAccess.select(con, dbc);

              if (iaDV.size() == 0 && newMultiproductId != 0) {

                ItemAssocData iaD = ItemAssocData.createValue();

                iaD.setItem1Id(itemId);

                iaD.setItem2Id(newMultiproductId);

                iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.ITEM_GROUP_ITEM);

                iaD.setCatalogId(catalogId);

                iaD.setAddBy(user);

                iaD.setModBy(user);

                ItemAssocDataAccess.insert(con, iaD);

              }

              if (iaDV.size() > 0 && newMultiproductId == 0) {

                ItemAssocDataAccess.remove(con, dbc);

              }

              if (iaDV.size() > 0 && newMultiproductId != 0) {

                ItemAssocData iaD = (ItemAssocData) iaDV.get(0);

                iaD.setItem2Id(newMultiproductId);

                iaD.setModBy(user);

                ItemAssocDataAccess.update(con, iaD);

                if (iaDV.size() > 1) {

                  dbc.addNotEqualTo(ItemAssocDataAccess.ITEM_ASSOC_ID,

                                    iaD.getItemAssocId());

                  ItemAssocDataAccess.remove(con, dbc);

                }

              }

            }

            //////

            if (newMultiproductId != oldMultiproductId && icaVw.getCatalogFlInp()) {

              dbc = new DBCriteria();

              dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, itemId);

              dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);

              dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                             RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

              IdVector csIdV = CatalogStructureDataAccess.selectIdOnly(con, dbc);

              if (csIdV.size() == 0 && newMultiproductId != 0) {

                CatalogStructureData csD = CatalogStructureData.createValue();

                csD.setCatalogId(catalogId);

                csD.setItemId(itemId);

                csD.setItemGroupId(newMultiproductId);

                csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

                csD.setAddBy(user);

                csD.setModBy(user);

                CatalogStructureDataAccess.insert(con, csD, true);

              }

              if (csIdV.size() > 0 && newMultiproductId == 0) {

                  ItemAssocDataAccess.remove(con, dbc);

              }

              if (csIdV.size() > 0 && newMultiproductId != 0) {

                  ItemAssocData iaD = (ItemAssocData) csIdV.get(0);

                  iaD.setItem2Id(newMultiproductId);

                  iaD.setModBy(user);

                  ItemAssocDataAccess.update(con, iaD);

                  if (csIdV.size() > 1) {

                    dbc.addNotEqualTo(ItemAssocDataAccess.ITEM_ASSOC_ID,

                                      iaD.getItemAssocId());

                    ItemAssocDataAccess.remove(con, dbc);

                  }

              }

              if (csIdV.size() > 1) {

                throw new Exception("^clw^System error: CB007^clw^" +

                                    " Multiple products in catalog. Catalog id: " +

                                    catalogId + " Product id: " + itemId);

              }

            }

          }

      }



      //Contract

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        int catalogId = icaVw.getCatalogId();

        int itemId = icaVw.getItemId();

        int contractId = icaVw.getContractId();

        if (!icaVw.getContractFlInp() || !icaVw.getCatalogFlInp()) {

          continue;

        }



        ContractData contract = ContractDataAccess.select(con, contractId);

        String catLocale = contract.getLocaleCd();

        //get number of digits after decimal point allowed for this locale



        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(CurrencyDataAccess.LOCALE, catLocale);

        CurrencyDataVector currDV = CurrencyDataAccess.select(con, crit);

        CurrencyData curr = (CurrencyData)currDV.get(0);

        int decimalPlaces = curr.getDecimals();



        BigDecimal oldCost = Utility.bdNN(icaVw.getCost());

        BigDecimal oldPrice = Utility.bdNN(icaVw.getPrice());

        BigDecimal oldBaseCost = Utility.bdNN(icaVw.getBaseCost());



        String newCostS = icaVw.getCostInp();

        String newPriceS = icaVw.getPriceInp();

        String newBaseCostS = icaVw.getBaseCostInp();



        BigDecimal newCost = new BigDecimal(0);

        BigDecimal newPrice = new BigDecimal(0);

        BigDecimal newBaseCost = new BigDecimal(0);



        if (Utility.isSet(newCostS)) {

          try {

            double db = Double.parseDouble(newCostS);

            newCost = new BigDecimal(db);

          } catch (Exception exc) {

            errorMessLL.add("Illegal cost format. Sku num: " + icaVw.getSkuNum() +

                            " Cost: " + newCostS);

          }

          newCostS = newCostS.trim(); // delete white space before or after the value of newCostS variable

          //check decimal places

          BigDecimal costBD = new BigDecimal(newCostS);

          if(costBD.scale() > decimalPlaces){

        	  String errorMess = "The cost for item "+icaVw.getSkuNum()+" has too many decimal points.  " +

        	  		"It can only have "+decimalPlaces+" decimal points for this currency";

        	  if (!errorMessLL.contains(errorMess)) {

        		  errorMessLL.add(errorMess);

        	  }

          }

        }



        if (Utility.isSet(newPriceS)) {

          try {

            double db = Double.parseDouble(newPriceS);

            newPrice = new BigDecimal(db);

          } catch (Exception exc) {

            errorMessLL.add("Illegal price format. Sku num: " + icaVw.getSkuNum() +

                            " Price: " + newPriceS);

          }



          //check decimal places

          newPriceS = newPriceS.trim(); // delete white space before or after the value of newPriceS variable

          BigDecimal priceBD = new BigDecimal(newPriceS);

          if(priceBD.scale() > decimalPlaces){

        	  String errorMess = "The price for item "+icaVw.getSkuNum()+" has too many decimal points.  " +

        	  		"It can only have "+decimalPlaces+" decimal points for this currency";

        	  if (!errorMessLL.contains(errorMess)) {

        		  errorMessLL.add(errorMess);

        	  }

          }

        }



        if (Utility.isSet(newBaseCostS)) {

          try {

            double db = Double.parseDouble(newBaseCostS);

            newBaseCost = new BigDecimal(db);

          } catch (Exception exc) {

            errorMessLL.add("Illegal base cost format. Sku num: " +

                            icaVw.getSkuNum() +

                            " Cost: " + newBaseCostS);

          }



          //check decimal places

          newBaseCostS = newBaseCostS.trim(); // delete white space before or after the value of baseCostBD variable

          BigDecimal baseCostBD = new BigDecimal(newBaseCostS);

          if(baseCostBD.scale() > decimalPlaces){

        	  String errorMess = "The base cost for item "+icaVw.getSkuNum()+" has too many decimal points.  " +

        	  		"It can only have "+decimalPlaces+" decimal points for this currency";

        	  if (!errorMessLL.contains(errorMess)) {

        		  errorMessLL.add(errorMess);

        	  }

          }

        }



        if (oldPrice.subtract(newPrice).abs().doubleValue() > 0.00001 ||

            oldCost.subtract(newCost).abs().doubleValue() > 0.00001 ||

            oldBaseCost.subtract(newBaseCost).abs().doubleValue() > 0.00001 ||

            (icaVw.getServiceFeeCodeInp() != null

                    && icaVw.getServiceFeeCodeInp().equals(icaVw.getServiceFeeCode()) == false)

            ) {

          dbc = new DBCriteria();

          dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contractId);

          dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);

          ContractItemDataVector ciDV = ContractItemDataAccess.select(con, dbc);

          if (ciDV.size() > 1) {

            throw new Exception("^clw^System error: CB008^clw^" +

                                " Multiple contract item records. Contract id: " +

                                contractId + " Item id: " + itemId);

          }

          if (ciDV.size() == 0) {

            throw new Exception("^clw^System error: CB009^clw^" +

                                " No contract item records. Contract id: " +

                                contractId + " Item id: " + itemId);

          }

          ContractItemData ciD = (ContractItemData) ciDV.get(0);

          ciD.setAmount(newPrice);

          ciD.setDistCost(newCost);

          ciD.setDistBaseCost(newBaseCost);

          ciD.setServiceFeeCode(icaVw.getServiceFeeCodeInp());

          ciD.setModBy(user);

          ContractItemDataAccess.update(con, ciD, true);

        }

      }

      if (errorMessLL.size() > 0) {

        throw new Exception(makeErrorString(errorMessLL));

      }



      //Dustributor

      String itemUom = "";

      String itemPack = "";

      HashMap itemDistHM = new HashMap();

      dbc = new DBCriteria();

      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemIdV);

      dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, distIdV);

      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                     RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

      ItemMappingDataVector distItemMapDV = ItemMappingDataAccess.select(con,

        dbc);

      for (Iterator iter = distItemMapDV.iterator(); iter.hasNext(); ) {

        ItemMappingData imD = (ItemMappingData) iter.next();

        imD.setDirty(false);

        String key = imD.getBusEntityId() + ":" + imD.getItemId();

        itemDistHM.put(key, imD);

      }

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];



        if (!icaVw.getCatalogFlInp()) {

          continue;

        }

        int itemId = icaVw.getItemId();

        String newDistIdS = icaVw.getDistIdInp();

        int newDistId = 0;

        if (Utility.isSet(newDistIdS)) {

          newDistId = Integer.parseInt(newDistIdS);

        }

        if (newDistId == 0) {

          continue;

        }

        String newDistConvS = icaVw.getDistConversInp();

        BigDecimal newDistConv = new BigDecimal(1);

        if (Utility.isSet(newDistConvS)) {

          try {

            newDistConv = new BigDecimal(Double.parseDouble(newDistConvS));

          } catch (Exception exc) {

            errorMessLL.add("Illegal distibutor uom multiplier. Sku num: " +

                            icaVw.getSkuNum() +

                            " Mutiplier: " + newDistConvS);

          }

        }

        String newDistSkuNum = Utility.strNN(icaVw.getDistSkuNumInp());

        String newDistSkuUom = Utility.strNN(icaVw.getDistSkuUomInp());

        boolean newDistSPLFl = icaVw.getDistSPLFlInp();

        String newDistSkuPack = Utility.strNN(icaVw.getDistSkuPackInp());



        BigDecimal oldDistConv = icaVw.getDistConvers();

        if (oldDistConv == null) oldDistConv = new BigDecimal(1);

        String oldDistSkuNum = Utility.strNN(icaVw.getDistSkuNum());

        String oldDistSkuUom = Utility.strNN(icaVw.getDistSkuUom());

        boolean oldDistSPLFl = icaVw.getDistSPLFl();

        String oldDistSkuPack = Utility.strNN(icaVw.getDistSkuPack());



        boolean distSkuNumFl = oldDistSkuNum.equals(newDistSkuNum);

        boolean distSkuUomFl = oldDistSkuUom.equals(newDistSkuUom);

        boolean distSPLFlFl = oldDistSPLFl == newDistSPLFl;

        boolean distSkuPackFl = oldDistSkuPack.equals(newDistSkuPack);

        boolean distConvFl = oldDistConv.subtract(newDistConv).abs().

                             doubleValue() < 0.0001;



        if(!RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd))

        {

        if (distSkuNumFl && distSkuUomFl && distSPLFlFl &&

            distSkuPackFl && distConvFl) {

          continue;

        }

        }

        String key = newDistId + ":" + itemId;

        ItemMappingData imD = (ItemMappingData) itemDistHM.get(key);

        if (imD == null) {

          imD = ItemMappingData.createValue();

          imD.setBusEntityId(newDistId);

          imD.setItemId(itemId);

          imD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

          imD.setItemNum(newDistSkuNum);

          if(!RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd)) {

              if (Utility.isSet(newDistSkuPack) || Utility.isSet(newDistSkuUom)) {

                  imD.setItemPack(newDistSkuPack);

                  imD.setItemUom(newDistSkuUom);

                  imD.setUomConvMultiplier(newDistConv);

              } else {

                  dbc = new DBCriteria();

                  dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);

                  dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, "PACK");

                  ItemMetaDataVector imetaDV = ItemMetaDataAccess.select(con, dbc);

                  if (imetaDV.size() > 0) {

                      ItemMetaData imetaD = (ItemMetaData) imetaDV.get(0);

                      itemPack = Utility.strNN(imetaD.getValue());

                  }

                  dbc = new DBCriteria();

                  dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemId);

                  dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE, "UOM");

                  imetaDV = ItemMetaDataAccess.select(con, dbc);

                  if (imetaDV.size() > 0) {

                      ItemMetaData imetaD = (ItemMetaData) imetaDV.get(0);

                      itemUom = Utility.strNN(imetaD.getValue());

                  }

                  imD.setItemPack(itemPack);

                  imD.setItemUom(itemUom);

                  imD.setUomConvMultiplier(new BigDecimal(1));

              }



              imD.setStandardProductList(Boolean.toString(newDistSPLFl));

          }

          imD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

          imD.setAddBy(user);

          imD.setModBy(user);

          imD.setDirty(true);

          imD = ItemMappingDataAccess.insert(con, imD);

          itemDistHM.put(key, imD);

        } else {

          BigDecimal prevDistConv = imD.getUomConvMultiplier();

          if (prevDistConv == null) prevDistConv = new BigDecimal(1);

          String prevDistSkuNum = Utility.strNN(imD.getItemNum());

          String prevDistSkuUom = Utility.strNN(imD.getItemUom());

          boolean prevDistSPLFl = Utility.isTrue(imD.getStandardProductList());

          String prevDistSkuPack = Utility.strNN(imD.getItemPack());

          if (!prevDistSkuNum.equals(newDistSkuNum) ||

              !prevDistSkuUom.equals(newDistSkuUom) ||

              prevDistSPLFl != newDistSPLFl ||

              !prevDistSkuPack.equals(newDistSkuPack) ||

              prevDistConv.subtract(newDistConv).abs().doubleValue() > 0.0001) {

            if (imD.isDirty()) {

              String errorMess =

                "No distributor sku parameters consistency. Distributor id: " +

                newDistId + " Sku: " + icaVw.getSkuNum();

              if (!errorMessLL.contains(errorMess)) {

                errorMessLL.add(errorMess);

              }

            } else {

              if (!distSkuNumFl) imD.setItemNum(newDistSkuNum);

              if (!distSkuPackFl) imD.setItemPack(newDistSkuPack);

              if (!distSkuUomFl) imD.setItemUom(newDistSkuUom);

              if (!distSPLFlFl) imD.setStandardProductList(Boolean.toString(

                newDistSPLFl));

              if (!distConvFl) imD.setUomConvMultiplier(newDistConv);

              imD.setModBy(user);

              ItemMappingDataAccess.update(con, imD);

            }

          }

        }

      }

      if (errorMessLL.size() > 0) {

        throw new Exception(makeErrorString(errorMessLL));

      }



      //Item Generic Manufacturer

      HashMap distGenManufHM = new HashMap();

      for (int ii = 0; ii < itemCatAggrVwA.length; ii++) {

        ItemCatalogAggrView icaVw = itemCatAggrVwA[ii];

        if (!icaVw.getCatalogFlInp()) {

          continue;

        }

        String newDistIdS = icaVw.getDistIdInp();

        int newDistId = (Utility.isSet(newDistIdS)) ?

                        Integer.parseInt(newDistIdS) : 0;

        if (newDistId == 0) {

          continue;

        }



        String newGenManufIdS = icaVw.getGenManufIdInp();

        int newGenManufId = (Utility.isSet(newGenManufIdS)) ?

                            Integer.parseInt(newGenManufIdS) : 0;

        String newGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNumInp());



        int oldGenManufId = icaVw.getGenManufId();

        String oldGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNum());

        if (oldGenManufId == newGenManufId &&

            oldGenManufSkuNum.equals(newGenManufSkuNum)) {

          continue;

        }

        int itemId = icaVw.getItemId();

        Integer newDistIdI = new Integer(newDistId);

        String key = newDistIdI + ":" + itemId;

        ItemCatalogAggrView icaVw1 =

          (ItemCatalogAggrView) distGenManufHM.get(key);

        if (icaVw1 == null) {

          distGenManufHM.put(key, icaVw);

        } else {

          String newGenManufIdS1 = icaVw1.getGenManufIdInp();

          int newGenManufId1 = (Utility.isSet(newGenManufIdS1)) ?

                               Integer.parseInt(newGenManufIdS1) : 0;

          String newGenManufSkuNum1 = Utility.strNN(icaVw1.getGenManufSkuNumInp());

          if (newGenManufId != newGenManufId1) {

            String errorMess =

              "No genenric manufacturer consistency. Distributor id: " +

              newDistId + " sku: " + icaVw.getSkuNum();

            if (!errorMessLL.contains(errorMess)) {

              errorMessLL.add(errorMess);

            }

            continue;

          }

          if (oldGenManufSkuNum.equals(newGenManufSkuNum)) {

            continue;

          }



          String oldGenManufSkuNum1 = Utility.strNN(icaVw1.getGenManufSkuNum());

          if (oldGenManufSkuNum1.equals(newGenManufSkuNum1)) {

            distGenManufHM.put(key, icaVw);

            continue;

          }



          if (!newGenManufSkuNum.equals(newGenManufSkuNum1)) {

            String errorMess =

              "No genenric manufacturer consistency. Distributor id: " +

              newDistId + " sku: " + icaVw.getSkuNum();

            if (!errorMessLL.contains(errorMess)) {

              errorMessLL.add(errorMess);

            }

          }

        }

      }



      if (errorMessLL.size() > 0) {

        throw new Exception(makeErrorString(errorMessLL));

      }



      Collection itemCataAggrVwCL = distGenManufHM.values();

      for (Iterator iter = itemCataAggrVwCL.iterator(); iter.hasNext(); ) {

        ItemCatalogAggrView icaVw = (ItemCatalogAggrView) iter.next();

        int itemId = icaVw.getItemId();

        int oldGenManufId = icaVw.getGenManufId();

        String newGenManufIdS = icaVw.getGenManufIdInp();

        int newGenManufId = (Utility.isSet(newGenManufIdS)) ?

                            Integer.parseInt(newGenManufIdS) : 0;

        int oldDistId = icaVw.getDistId();

        String newDistIdS = icaVw.getDistIdInp();

        int newDistId = (Utility.isSet(newDistIdS)) ?

                        Integer.parseInt(newDistIdS) : 0;

        if (newDistId == 0) {

          continue;

        }

        String genManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNum());

        String newGenManufSkuNum = Utility.strNN(icaVw.getGenManufSkuNumInp());

        if (oldGenManufId == newGenManufId && oldDistId == newDistId &&

            genManufSkuNum.equals(newGenManufSkuNum)) {

          continue;

        }

        String key = newDistId + ":" + itemId;

        ItemMappingData distItemMapD = (ItemMappingData) itemDistHM.get(key);



        if (newGenManufId == 0) {

          dbc = new DBCriteria();

          dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID,

                         distItemMapD.getItemMappingId());

          dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,

                         RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);

          IdVector genManufItemMapIdV = ItemMappingAssocDataAccess.selectIdOnly(

            con,

            ItemMappingAssocDataAccess.ITEM_MAPPING2_ID, dbc);

          ItemMappingAssocDataAccess.remove(con, dbc);



          dbc = new DBCriteria();

          dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID,

                       genManufItemMapIdV);

          ItemMappingDataAccess.remove(con, dbc);

        } else {

          dbc = new DBCriteria();

          dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING1_ID,

                         distItemMapD.getItemMappingId());

          dbc.addEqualTo(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_CD,

                         RefCodeNames.ITEM_MAPPING_ASSOC_CD.DIST_GENERIC_MFG);

          IdVector genManufItemMapAssocIdV = ItemMappingAssocDataAccess.

                                             selectIdOnly(con,

            ItemMappingAssocDataAccess.ITEM_MAPPING2_ID, dbc);

          if (genManufItemMapAssocIdV.size() == 0) {

            ItemMappingData genManufItemMapD = ItemMappingData.createValue();

            genManufItemMapD.setBusEntityId(newGenManufId);

            genManufItemMapD.setItemId(itemId);

            genManufItemMapD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.

                                              ITEM_GENERIC_MFG);

            genManufItemMapD.setItemNum(icaVw.getGenManufSkuNumInp());

            genManufItemMapD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            genManufItemMapD.setAddBy(user);

            genManufItemMapD.setModBy(user);

            genManufItemMapD = ItemMappingDataAccess.insert(con,

              genManufItemMapD);



            ItemMappingAssocData imaD = ItemMappingAssocData.createValue();

            imaD.setItemMapping1Id(distItemMapD.getItemMappingId());

            imaD.setItemMapping2Id(genManufItemMapD.getItemMappingId());

            imaD.setItemMappingAssocCd(RefCodeNames.ITEM_MAPPING_ASSOC_CD.

                                       DIST_GENERIC_MFG);

            imaD.setAddBy(user);

            imaD.setModBy(user);

            ItemMappingAssocDataAccess.insert(con, imaD);

          } else {

            Integer imaIdI = (Integer) genManufItemMapAssocIdV.get(0);

            int imaId = imaIdI.intValue();

            ItemMappingData imD = ItemMappingDataAccess.select(con, imaId);

            int genManufId = imD.getBusEntityId();

            genManufSkuNum = Utility.strNN(imD.getItemNum());

            if (genManufId != newGenManufId ||

                !genManufSkuNum.equals(newGenManufSkuNum)) {

              imD.setBusEntityId(newGenManufId);

              imD.setItemNum(newGenManufSkuNum);

              imD.setModBy(user);

              ItemMappingDataAccess.update(con, imD);

            }

            if (genManufItemMapAssocIdV.size() > 1) {

              dbc = new DBCriteria();

              genManufItemMapAssocIdV.remove(0);

              dbc.addOneOf(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_ID,

                           genManufItemMapAssocIdV);

              IdVector imIdV = ItemMappingAssocDataAccess.selectIdOnly(con,

                ItemMappingAssocDataAccess.ITEM_MAPPING2_ID, dbc);

              ItemMappingAssocDataAccess.remove(con, dbc);


              dbc = new DBCriteria();

              dbc.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, imIdV);

              ItemMappingDataAccess.remove(con, dbc);

            }

          }

        }

      }

    if (categCatalogToDelVwV.size()>0) {

        //Try to remove directories from catalog(s)

        int prevCategId = 0;

        int categCount = 0;

        int prevCtalalogId = 0;

        int catalogCount = 0;

        for(Iterator iter=categCatalogToDelVwV.iterator(); iter.hasNext();) {

            PairView aa = (PairView) iter.next();

            int categoryId = (Integer) aa.getObject1();

            int catalogId = (Integer) aa.getObject2();

            if(categoryId!=prevCategId) {

                categCount++;

                prevCategId = categoryId;

            }

            if(catalogId!=prevCatalogId) {

                catalogCount++;

                prevCatalogId = catalogId;

            }

        }

        boolean multiCatalogFl = true;

        if(categCount > catalogCount) multiCatalogFl = false;

        if(multiCatalogFl) {

            HashMap<Integer,HashSet> categCatalogToDelHM = new HashMap<Integer,HashSet>();

            for(Iterator iter=categCatalogToDelVwV.iterator(); iter.hasNext();) {

                PairView aa = (PairView) iter.next();

                int categoryId = (Integer) aa.getObject1();

                int catalogId = (Integer) aa.getObject2();

                HashSet<Integer> catalogHS = categCatalogToDelHM.get(categoryId);

                if(catalogHS==null) {

                    catalogHS = new HashSet<Integer>();

                    categCatalogToDelHM.put(categoryId, catalogHS);

                }

                catalogHS.add(catalogId);

            }

            Set entries = categCatalogToDelHM.entrySet();

            for(Iterator iter=entries.iterator(); iter.hasNext();) {

                Map.Entry<Integer,HashSet> es = (Map.Entry)iter.next();

                HashSet<Integer> categoryHS = new HashSet<Integer>();

                categoryHS.add((Integer) es.getKey());

                HashSet<Integer>catalogHS = (HashSet) es.getValue();

                tryToRemoveCategoryFromCatalog(con, storeCatId, catalogHS, categoryHS, pItemTypeCd);

            }

        } else {

            HashMap<Integer,HashSet> catalogCategToDelHM = new HashMap<Integer,HashSet>();

            for(Iterator iter=categCatalogToDelVwV.iterator(); iter.hasNext();) {

                PairView aa = (PairView) iter.next();

                int categoryId = (Integer) aa.getObject1();

                int catalogId = (Integer) aa.getObject2();

                HashSet<Integer> categoryHS = catalogCategToDelHM.get(catalogId);

                if(categoryHS==null) {

                    categoryHS = new HashSet<Integer>();

                    catalogCategToDelHM.put(catalogId, categoryHS);

                }

                categoryHS.add(categoryId);

            }

            Set entries = catalogCategToDelHM.entrySet();

            for(Iterator iter=entries.iterator(); iter.hasNext();) {

                Map.Entry<Integer,HashSet> es = (Map.Entry)iter.next();

                HashSet<Integer> catalogHS = new HashSet<Integer>();

                catalogHS.add((Integer) es.getKey());

                HashSet<Integer>categoryHS = (HashSet) es.getValue();

                tryToRemoveCategoryFromCatalog(con, storeCatId, catalogHS, categoryHS, pItemTypeCd);

            }

        }

    }

//tryToRemoveCategoryFromCatalog(con, catalogIds, categoryIds, pItemTypeCd);

      return;

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

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

      }

    }

  }



  private String makeErrorString(List pErrors) {

    String errorMess = "^clw^";

    for (int ii = 0; ii < pErrors.size(); ii++) {

      if (ii != 0) errorMess += "; ";

      errorMess += pErrors.get(ii);

    }

    errorMess += "^clw^";

    return errorMess;

  }



  /**

   * Addes category to clw_catalog_structure table

   * @param pCategoryId  the catalog id.

   * @param pCatalogIds  the catalog id vector.

   * @param user - user login name

   * @throws            RemoteException

   */

  public void addCategoryToCatalogs(int pCategoryId, IdVector pCatalogIds,

                                    String user) throws RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc;

      ItemData categD = ItemDataAccess.select(con, pCategoryId);

      /*String categName = categD.getShortDesc();

      String categLongDesc = categD.getLongDesc();



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemDataAccess.SHORT_DESC, categName);

      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      if (categLongDesc != null) {

        dbc.addEqualTo(ItemDataAccess.LONG_DESC, categLongDesc);

      }



      IdVector categIdV =

        ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbc);*/



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, pCatalogIds);
      dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, categD.getItemId()); 
      //dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, categIdV);

      dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);



      CatalogStructureDataVector csDV =

        CatalogStructureDataAccess.select(con, dbc);





      boolean foundFl = false;

      int catalogIdPrev = 0;

      for (Iterator iter = csDV.iterator(); iter.hasNext(); ) {

        CatalogStructureData csD = (CatalogStructureData) iter.next();

        int catalogId = csD.getCatalogId();

        if (catalogId != catalogIdPrev) {

          foundFl = false;

          catalogIdPrev = catalogId;

        }

        if (!foundFl) {

          foundFl = true;

          csD.setItemId(pCategoryId);

          csD.setModBy(user);

          CatalogStructureDataAccess.update(con, csD, true);

        } else {

          CatalogStructureDataAccess.remove(con, csD.getCatalogStructureId(), true);

        }

        pCatalogIds.remove(new Integer(catalogId));

      }



      HashSet catalogIdHS = new HashSet();

      for (Iterator iter = pCatalogIds.iterator(); iter.hasNext(); ) {

        Integer catalogIdI = (Integer) iter.next();

        if (!catalogIdHS.contains(catalogIdI)) {

          catalogIdHS.add(catalogIdI);

        }

      }



      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

      Date today = sdf.parse(sdf.format(new Date()));

      for (Iterator iter = catalogIdHS.iterator(); iter.hasNext(); ) {

        Integer catalogIdI = (Integer) iter.next();

        int catalogId = catalogIdI.intValue();

        CatalogStructureData csD = CatalogStructureData.createValue();

        csD.setCatalogId(catalogId);

        csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.

                                  CATALOG_CATEGORY);

        csD.setItemId(pCategoryId);

        csD.setEffDate(today);

        csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

        csD.setAddBy(user);

        csD.setModBy(user);

        CatalogStructureDataAccess.insert(con, csD, true);

      }



    } catch (Exception exc) {

      logError(exc.getMessage());

      exc.printStackTrace();

      throw new RemoteException(exc.getMessage());

    } finally {

      try {

        con.close();

      } catch (SQLException exc) {

        logError("exc.getMessage");

        exc.printStackTrace();

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

      }

    }

  }



  /**

   * Addes category to clw_catalog_structure table

   * @param pCategoryId  the catalog id.

   * @param pCatalogIds  the catalog id vector.

   * @param pItemTypeCd  the item type (Service or product).

   * @throws            RemoteException

   */

  public void removeCategoryFromCatalogs(int pCategoryId, IdVector pCatalogIds,String pItemTypeCd) throws

    RemoteException {

    Connection con = null;

    try {

      con = getConnection();

      DBCriteria dbc;

      String pcItemAssocCd;

      String itemTypeCd;

        if (RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(pItemTypeCd)) {

            itemTypeCd = RefCodeNames.ITEM_TYPE_CD.SERVICE;

            pcItemAssocCd = RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY;

        } else if (RefCodeNames.ITEM_TYPE_CD.PRODUCT.equals(pItemTypeCd)) {

            itemTypeCd = RefCodeNames.ITEM_TYPE_CD.PRODUCT;

            pcItemAssocCd = RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY;

        } else {

            throw new RemoteException("Can't remove category from catalog for this item type : " + pItemTypeCd);

        }

      ItemData categD = ItemDataAccess.select(con, pCategoryId);

      String categName = categD.getShortDesc();



      dbc = new DBCriteria();

      dbc.addEqualTo(ItemDataAccess.SHORT_DESC, categName);

      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);

      IdVector categIdV =

        ItemDataAccess.selectIdOnly(con, ItemDataAccess.ITEM_ID, dbc);



      dbc = new DBCriteria();

      dbc.addOneOf(ItemAssocDataAccess.ITEM2_ID, categIdV);

      dbc.addOneOf(ItemAssocDataAccess.CATALOG_ID, pCatalogIds);



      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,pcItemAssocCd);

      IdVector itemIdV = ItemAssocDataAccess.selectIdOnly(con,

        ItemAssocDataAccess.ITEM1_ID, dbc);

      if (itemIdV.size() > 0) {

        dbc = new DBCriteria();

        dbc.addOneOf(ItemDataAccess.ITEM_ID, itemIdV);

        dbc.addOrderBy(ItemDataAccess.SKU_NUM);

        ItemDataVector iDV = ItemDataAccess.select(con, dbc);

        String errorMess = "^clw^Some items belong to " + categName +

                           " category. " +

                           " Skus: ";

        for (int ii = 0; ii < iDV.size(); ii++) {

          if (ii > 9) {

            errorMess += "...";

            break;

          }

          if (ii > 0) errorMess += ", ";

          ItemData iD = (ItemData) iDV.get(ii);

          errorMess += iD.getSkuNum();

        }

        errorMess += "^clw^";

        throw new Exception(errorMess);

      }



      dbc = new DBCriteria();

      dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, pCatalogIds);

      dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, categIdV);



      CatalogStructureDataAccess.remove(con, dbc, true);



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

        throw new RemoteException(

          "Error. CatalogBean.removeCatalogCascade() SQL Exception happened");

      }

    }

  }



  public void resetCostCenters(int pCatalogId, String user) throws

    RemoteException {



    Connection con = null;

    try {

      con = getConnection();

      IdVector catalogIdV = getSubcatalogIdsForAccountCatalog(con, pCatalogId);

      List catalogIdVV = splitIdVector(catalogIdV);

      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      for (Iterator iter = catalogIdVV.iterator(); iter.hasNext(); ) {

        catalogIdV = (IdVector) iter.next();

        String q = " select distinct cs2.item_id product_item_id, "

                   + " cs.item_id category_id,  "

                   + " ia.catalog_id,  "

                   + " cs.cost_center_id category_cost_center_id "

                   + " , cs2.cost_center_id product_cost_center_id "

                   + " from clw_catalog_structure cs , clw_catalog cat , "

                   + " clw_item_assoc ia, clw_catalog_structure cs2   "

                   + " where cs.catalog_structure_cd = 'CATALOG_CATEGORY' " +

                   " and cat.catalog_id = ia.catalog_id and cat.catalog_type_cd in (" +

                   " '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'," +
                   " '" + RefCodeNames.CATALOG_TYPE_CD.ACCOUNT + "'," +
                   " '" + RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING + "' )" +

                   " and cs.cost_center_id > 0 "

                   + " and cs2.catalog_id in (  "

                   + (IdVector.toCommaString(catalogIdV))

                   + " ) and cs.item_id = ia.item2_id "

                   + " and ia.CATALOG_ID = cs2.CATALOG_ID "

                   + " and cs.catalog_id = " + pCatalogId

                   + " and (ia.item1_id = cs2.item_id or ia.item2_id = cs2.item_id) "

                   + " and cs2.cost_center_id <> cs.cost_center_id ";



        Statement stmt1 = con.createStatement();

        Statement stmt2 = con.createStatement();



        ResultSet rs1 = stmt1.executeQuery(q);

        while (rs1.next()) {

          int prodItemId = rs1.getInt("PRODUCT_ITEM_ID");

          int newCostCenterId = rs1.getInt("CATEGORY_COST_CENTER_ID");

          int catalogId = rs1.getInt("CATALOG_ID");

          Date curDate = new Date();

          String curDateS = sdf.format(curDate);

          String updsql = " update clw_catalog_structure "

                          + " set cost_center_id = " + newCostCenterId

                          + " , mod_by ='" + user + "'"

                          + " , mod_date = to_date('" + curDateS +

                          "','mm/dd/yyyy hh24:mi:ss')"

                          + " where catalog_id = " + catalogId

                          + " and item_id = " + prodItemId;


          stmt2.executeUpdate(updsql);

        }



        stmt2.close();

        stmt1.close();

      }



    } catch (Exception e) {

      e.printStackTrace();

      logError(" resetCostCenters error: " + e);

    } finally {

      closeConnection(con);

    }



    return;



  }

  public void resetAccountCostCenters(int pCatalogId, String user) throws
    RemoteException {
    Connection con = null;

    try {
		con = getConnection();
		IdVector catalogIdV = getSubcatalogIdsForAccountCatalog(con, pCatalogId);
		List catalogIdVV = splitIdVector(catalogIdV);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date curDate = new Date();
		String curDateS = sdf.format(curDate);
		
		// Clean all cost centers in subcatalogs
		for (Iterator iter = catalogIdVV.iterator(); iter.hasNext(); ) {
			catalogIdV = (IdVector) iter.next();
			curDate = new Date();
			curDateS = sdf.format(curDate);
			String cleanSql = 
				"update clw_catalog_structure  " +
				" set cost_center_id = null" +
				" , mod_by =  '" + user + "'" +
				" , mod_date = to_date('" + curDateS +
				"','mm/dd/yyyy hh24:mi:ss')" +
				" where " +
				" catalog_id in ( " + IdVector.toCommaString(catalogIdV) +
				" ) " +
				" and catalog_id != " + pCatalogId +
				" and cost_center_id is not null ";
			logInfo(" Clean the cost centers:"  + cleanSql);
			PreparedStatement stmt2 = con.prepareStatement(cleanSql);
			boolean res = stmt2.execute();
			int resCount = stmt2.getUpdateCount();
			stmt2.close();
		}
		//Update items with no cost centers
		curDate = new Date();
		curDateS = sdf.format(curDate);
		String cleanItemCCSql = 
			"update clw_catalog_structure set cost_center_id = null "+
				" , mod_by =  '" + user + "'" +
				" , mod_date = to_date('" + curDateS +
				"','mm/dd/yyyy hh24:mi:ss')" +
			" where (catalog_id, item_id) in "+
			"( " +
			" select csc.catalog_id, ia.item1_id "+
			" from clw_catalog_structure csc, clw_item_assoc ia "+
			" where csc.catalog_id = "+ pCatalogId +
			" and csc.catalog_structure_cd = 'CATALOG_CATEGORY' "+
			" and nvl(csc.cost_center_id,0)=0 "+
			" and ia.catalog_id = csc.catalog_id "+
			" and ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY'"+
			" and ia.item2_id = csc.item_id "+
			" ) "+
			" and cost_center_id is not null ";
		logInfo(" Clean the item cost centers in account catalog:"  + cleanItemCCSql);
		PreparedStatement pstmt2 = con.prepareStatement(cleanItemCCSql);
		boolean res = pstmt2.execute();
		int resCount = pstmt2.getUpdateCount();
		pstmt2.close();

		// Get catalog cost centers
		String getAccountCCSql = 
			"select distinct cost_center_id" + 
			" from clw_catalog_structure " +
			" where catalog_id = " + pCatalogId +
			" and catalog_structure_cd = 'CATALOG_CATEGORY'"+
			" and cost_center_id > 0 ";

		logInfo(" Get account cost centers:"  + getAccountCCSql);
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery(getAccountCCSql);
		IdVector ccIdV = new IdVector();
        while (rs1.next()) {
          int ccId = rs1.getInt("cost_center_id");
		  ccIdV.add(ccId);
        }
		rs1.close();
        stmt1.close();

        //Update Cost Centers
		for(Iterator iter=ccIdV.iterator(); iter.hasNext(); ) {
			Integer ccId = (Integer) iter.next();
			curDate = new Date();
			curDateS = sdf.format(curDate);
			
			String setCCSQL	=
				"update clw_catalog_structure  " +
				" set cost_center_id = " + ccId +
				" , mod_by =  '" + user + "'" +
				" , mod_date = to_date('" + curDateS +
				"','mm/dd/yyyy hh24:mi:ss')" +
				" where item_id in ( "+
				" select item_id "+
				" from clw_catalog_structure csc, clw_item_assoc ia "+
				" where csc.catalog_id = "+ pCatalogId +
				"  and csc.catalog_structure_cd = 'CATALOG_CATEGORY' "+
				"  and csc.cost_center_id = " + ccId +
				"  and ia.catalog_id = csc.catalog_id "+
				"  and ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY' "+
				"  and ia.item2_id = csc.item_id " +
				" ) "+
				" and catalog_id = "+ pCatalogId +
				" and nvl(cost_center_id,0) != " + ccId;
				logInfo(" Set account item cost centers:"  + setCCSQL);
				
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate(setCCSQL);
				stmt2.close();
        }

    } catch (Exception e) {
      e.printStackTrace();
      logError(" resetAccountCostCenters error: " + e);
    } finally {
      closeConnection(con);
    }
    return;
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

    String betab = BusEntityDataAccess.CLW_BUS_ENTITY;

    String ctab = CatalogDataAccess.CLW_CATALOG;

    String catab = CatalogAssocDataAccess.CLW_CATALOG_ASSOC;

    DBCriteria dbc = new DBCriteria();

    dbc.addJoinTableEqualTo(catab, CatalogAssocDataAccess.CATALOG_ID,

                            pCatalogId);

    dbc.addJoinTableEqualTo(catab, CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);



    dbc.addJoinCondition(catab, CatalogAssocDataAccess.CATALOG_ID, ctab,

                         CatalogDataAccess.CATALOG_ID);

    dbc.addJoinCondition(catab, CatalogAssocDataAccess.BUS_ENTITY_ID, betab,

                         BusEntityDataAccess.BUS_ENTITY_ID);

    ArrayList beGoodStatus = new ArrayList();

    beGoodStatus.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

    beGoodStatus.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);

    dbc.addJoinTableOneOf(betab, BusEntityDataAccess.BUS_ENTITY_STATUS_CD,

                          beGoodStatus);

    ArrayList catGoodStatus = new ArrayList();

    catGoodStatus.add(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

    catGoodStatus.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);

    catGoodStatus.add(RefCodeNames.CATALOG_STATUS_CD.LIVE);

    dbc.addJoinTableOneOf(ctab, CatalogDataAccess.CATALOG_STATUS_CD,

                          catGoodStatus);

    dbc.addDataAccessForJoin(new CatalogAssocDataAccess());

    String accountIdSql = JoinDataAccess.getSqlSelectIdOnly(catab,

      CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

    IdVector accountIdV = JoinDataAccess.selectIdOnly(con, catab,

      CatalogAssocDataAccess.BUS_ENTITY_ID, dbc, 1001);



    if (accountIdV.isEmpty()) {

      return new IdVector();

    }



    dbc = new DBCriteria();

    dbc.addJoinCondition(catab, CatalogAssocDataAccess.CATALOG_ID, ctab,

                         CatalogDataAccess.CATALOG_ID);

    dbc.addJoinCondition(catab, CatalogAssocDataAccess.BUS_ENTITY_ID, betab,

                         BusEntityDataAccess.BUS_ENTITY_ID);

    dbc.addJoinTableOneOf(ctab, CatalogDataAccess.CATALOG_STATUS_CD,

                          catGoodStatus);

    dbc.addJoinTableOneOf(betab, BusEntityDataAccess.BUS_ENTITY_STATUS_CD,

                          beGoodStatus);

    if (accountIdV.size() > 1000) {

      dbc.addJoinTableOneOf(catab, CatalogAssocDataAccess.BUS_ENTITY_ID,

                            accountIdSql);

    } else {

      dbc.addJoinTableOneOf(catab, CatalogAssocDataAccess.BUS_ENTITY_ID,

                            accountIdV);

    }

    dbc.addJoinTableEqualTo(catab, CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

    IdVector catalogIdV =

      JoinDataAccess.selectIdOnly(con, catab, CatalogAssocDataAccess.CATALOG_ID,

                                  dbc, 0);

    return catalogIdV;

  }



  public void setCategoryCostCenter(int pCatalogId,

                                    String pCatalogCategoryName,

                                    int pNewCostCenterId,

                                    String user) throws RemoteException {

    logDebug("setCategoryCostCenter (int pCatalogId=" + pCatalogId +

             ", String pCatalogCategoryName=" + pCatalogCategoryName +

             ", int pNewCostCenterId=" + pNewCostCenterId + ")");

    Connection conn = null;

    try {

      conn = getConnection();

      IdVector catalogIdV = getSubcatalogIdsForAccountCatalog(conn, pCatalogId);

      List catalogIdVL = splitIdVector(catalogIdV);

      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      for (Iterator iter = catalogIdVL.iterator(); iter.hasNext(); ) {

        IdVector cIdV = (IdVector) iter.next();

        Date curDate = new Date();

        String curDateS = sdf.format(curDate);



        // Update the cost center for each item found

        // under this category.

        //Statement stmt2 = conn.createStatement();

        String updSql = "update clw_catalog_structure cs2 " +

                        //" set cost_center_id = " + pNewCostCenterId +
                        " set cost_center_id = ?" +

                        //" , mod_by ='" + user + "'" +
                        " , mod_by = ? " +

                        " , mod_date = to_date('" + curDateS +

                        "','mm/dd/yyyy hh24:mi:ss')" +

                        " where " +

                        " cs2.catalog_id in ( " + IdVector.toCommaString(cIdV) +

                        " ) " +

                        " and  cs2.item_id in ( " +

                        " select distinct ia.item1_id " +

                        " from clw_item_assoc ia where " +

                        " ia.catalog_id in ( " + IdVector.toCommaString(cIdV) +

                        " ) and " +

                        " ( item2_id in (select distinct cs.item_id " +

                        " from clw_catalog_structure cs , clw_item i " +

                        " where cs.catalog_id in ( " +

                        IdVector.toCommaString(cIdV) + " ) " +

                        " and trim(i.short_desc) = trim('" + pCatalogCategoryName +  "') " +
                        //" and trim(i.short_desc) = ? " +


                        " and i.item_id = cs.item_id ) ) )  ";



        logDebug(" Update the cost center for the items:"  + updSql);

        //stmt2.executeUpdate(updSql);
        PreparedStatement stmt2 = conn.prepareStatement(updSql);
        stmt2.setInt(1, pNewCostCenterId);
        stmt2.setString(2, user);
        //stmt2.setString(3, pCatalogCategoryName);

        boolean res = stmt2.execute();
        int resCount = stmt2.getUpdateCount();
        logDebug(" Update the cost center for the items, resCount=" + resCount);
        stmt2.close();

        curDate = new Date();

        curDateS = sdf.format(curDate);

        // Update the cost center for the category.

        updSql = "update clw_catalog_structure cs2 " +

                 //" set cost_center_id = " + pNewCostCenterId +
                 " set cost_center_id = ? " +

                 //" , mod_by ='" + user + "'" +
                 " , mod_by = ? " +

                 " , mod_date = to_date('" + curDateS +

                 "','mm/dd/yyyy hh24:mi:ss')" +

                 " where " +

                 " cs2.catalog_id in ( " + IdVector.toCommaString(cIdV) + " ) " +

                 " and  cs2.item_id in ( " +

                 "   select distinct cs.item_id " +

                 "   from clw_catalog_structure cs , clw_item i " +

                 "   where cs.catalog_id in ( " + IdVector.toCommaString(cIdV) +

                 " ) " +

                 "   and trim(i.short_desc) = trim('" + pCatalogCategoryName + "') " +
                 //"   and trim(i.short_desc) = ? " +

                 "   and i.item_id = cs.item_id ) ";



        logDebug(" Update the cost center for the category:" + updSql);


        //stmt2.executeUpdate(updSql);
        //stmt2.close();

        stmt2 = conn.prepareStatement(updSql);
        stmt2.setInt(1, pNewCostCenterId);
        stmt2.setString(2, user);
        //stmt2.setString(3, pCatalogCategoryName);

        res=stmt2.execute();
        resCount = stmt2.getUpdateCount();
        logDebug("Update the cost center for the category, resCount 2=" + resCount);
        stmt2.close();


      }

    } catch (Exception e) {

      e.printStackTrace();

      logError(" setCategoryCostCenter error: " + e);

      throw new RemoteException(e.getMessage());

    } finally {

      closeConnection(conn);

    }



    return;

  }

  public void setAccountCategoryCostCenter(int pCatalogId,
                                    String pCatalogCategoryName,
                                    int pNewCostCenterId,
                                    String user) 
  throws RemoteException {

    logInfo("setCategoryCostCenter (int pCatalogId=" + pCatalogId +
             ", String pCatalogCategoryName=" + pCatalogCategoryName +
             ", int pNewCostCenterId=" + pNewCostCenterId + ")");
    Connection conn = null;
    try {
      conn = getConnection();
      IdVector catalogIdV = getSubcatalogIdsForAccountCatalog(conn, pCatalogId);
      List catalogIdVL = splitIdVector(catalogIdV);
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      Date curDate = null;
      String curDateS = null;
		
      for (Iterator iter = catalogIdVL.iterator(); iter.hasNext(); ) {
        IdVector cIdV = (IdVector) iter.next();
        curDate = new Date();
        curDateS = sdf.format(curDate);
		
        // Clean all cost centers in subcatalogs
        String cleanSql = "update clw_catalog_structure  " +
                        " set cost_center_id = null" +
                        " , mod_by = ? " +
                        " , mod_date = to_date('" + curDateS +
                        "','mm/dd/yyyy hh24:mi:ss')" +
                        " where " +
                        " catalog_id in ( " + IdVector.toCommaString(cIdV) +
                        " ) " +
						" and catalog_id != " + pCatalogId +
						" and cost_center_id is not null ";
        logInfo(" Clean the cost centers:"  + cleanSql);
        PreparedStatement stmt2 = conn.prepareStatement(cleanSql);
        stmt2.setString(1, user);
        boolean res = stmt2.execute();
        int resCount = stmt2.getUpdateCount();
        stmt2.close();
        curDate = new Date();
        curDateS = sdf.format(curDate);
	}	
    // Get category id
    String getCategIdSql = 
	"select i.item_id "+
	" from clw_item i, clw_catalog_structure cs "+
    " where cs.item_id = i.item_id "+
    " and cs.catalog_id = "+ pCatalogId +
    " and i.short_desc = '"+ pCatalogCategoryName +"'";
    
	logInfo("CatalogBean BBBBB getCategIdSql :"  + getCategIdSql);

	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(getCategIdSql);
	int categoryId = 0;
	int count = 0;
	while (rs.next()) {
		int cid = rs.getInt("item_id");
	    if(categoryId > 0 && cid != categoryId) {
			throw new Exception("Multiple catgories '"+pCatalogCategoryName+"' in catalog "+pCatalogId);
		}
		categoryId = cid;
	}
	rs.close();
	stmt.close();

	if(categoryId ==0 ) {
		throw new Exception("No category '"+pCatalogCategoryName+"' found in catalog "+pCatalogId);
	}

	
    // Update pCatalogId (account catalog)
    String updCategSql = "update clw_catalog_structure cs2 " +
                        " set cost_center_id = " + pNewCostCenterId+
                        " , mod_by =  '" + user +"'"+
                        " , mod_date = to_date('" + curDateS +"','mm/dd/yyyy hh24:mi:ss')" +
                        " where cs2.catalog_id = " + pCatalogId +
                        " and  cs2.item_id = "+categoryId;

	logInfo(" Update the cost center for category:"  + updCategSql);
    PreparedStatement stmt2 = conn.prepareStatement(updCategSql);
    boolean res = stmt2.execute();
    stmt2.close();

    curDate = new Date();
    curDateS = sdf.format(curDate);

    // Update the cost center for items.

    String updItemSql = "update clw_catalog_structure cs2 " +
					" set cost_center_id = " + pNewCostCenterId+
					" , mod_by =  '" + user +"'"+
					" , mod_date = to_date('" + curDateS +"','mm/dd/yyyy hh24:mi:ss')" +
					" where cs2.catalog_id = " + pCatalogId +
					" and  cs2.item_id in ("+
					"    select item1_id "+
					"    from clw_item_assoc  "+ 
					"    where catalog_id = " + pCatalogId +
					"    and item2_id = "+ categoryId+ 
					" ) ";

    logInfo(" Update the cost center for the items:" + updItemSql);
    stmt2 = conn.prepareStatement(updItemSql);

    res=stmt2.execute();
    stmt2.close();
    } catch (Exception e) {
      e.printStackTrace();
      logError(" setCategoryCostCenter error: " + e);
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
    return;
  }



  /**

   * Adds or updates service of store catalog

   * @param pStoreId  the store id.

   * @param pCatalogId  the catalog id.

   * @param pService  ItemData object

   * @param user user login id

   * @return ItemData

   * @throws RemoteException Required by EJB 1.0, DataNotFoundException

   */

  public ItemData updateServiceData(int pStoreId, int pCatalogId, ItemData pService, String user) throws RemoteException {

      Connection con = null;

      DBCriteria dbCriteria;

      try {

          con = getConnection();



          //Check catalog existance

          CatalogData catalogD = CatalogDataAccess.select(con, pCatalogId);

          //Is catalog a store catalog

          if (!RefCodeNames.CATALOG_TYPE_CD.STORE.equals(catalogD.getCatalogTypeCd())) {

              String errorMess = "Catalog is not a store catalog. Catalog id: " + pCatalogId;

              throw new Exception(errorMess);

          }





          dbCriteria = new DBCriteria();

          dbCriteria.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

          dbCriteria.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

          dbCriteria.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

          IdVector storeIdV = CatalogAssocDataAccess.selectIdOnly(con, dbCriteria);

          if (storeIdV.size() == 0) {

              String errorMess = "Catalog doesn't belong to the store. Catalog id: " +

                      pCatalogId + " Store id: " + pStoreId;

              throw new Exception(errorMess);

          }

          if (pService.getItemId() == 0) {



              pService.setAddBy(user);

              pService.setModBy(user);

              ItemDataAccess.insert(con, pService);

              CatalogStructureData csd = CatalogStructureData.createValue();

              csd.setCatalogId(pCatalogId);

              csd.setItemId(pService.getItemId());

              csd.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

              csd.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);

              csd.setEffDate(new java.util.Date(System.currentTimeMillis()));

              csd.setAddBy(user);

              csd.setModBy(user);

              CatalogStructureDataAccess.insert(con,csd);

          } else if (pService.getItemId() > 0) {

              //bCriteria = new DBCriteria();

              // dbCriteria.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

              //dbCriteria.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pService.getItemId());

              pService.setModBy(user);

              ItemDataAccess.update(con, pService);

          }

          return pService;

      } catch (DataNotFoundException e) {

          e.printStackTrace();

          throw new RemoteException(e.getMessage());

      } catch (Exception e) {

          e.printStackTrace();

          throw new RemoteException(e.getMessage());

      }

      finally {

          closeConnection(con);

      }

  }

    /**

     * Removes all the CatalogStructure in giveing catalog from database

     * @param pCatalogId  the catalog id.

     * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

     */

    public void removeCatalogStructure(int pCatalogId, String user) throws RemoteException {

      Connection con = null;

      try {

        con = getConnection();

        //delete structure in the catalog

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);

        CatalogStructureDataAccess.remove(con, dbc);

      } catch (Exception exc) {

        logError(exc.getMessage());

        exc.printStackTrace();

        throw new RemoteException(exc.getMessage(),exc);

      } finally {

        try {

          con.close();

        } catch (SQLException exc) {

          logError(exc.getMessage());

          exc.printStackTrace();

          throw new RemoteException(exc.getMessage(),exc);

        }

      }

    }



    /**

     * Verifies whether categroy exists and creates it if it doesn't exist

     * @param pCatalogId  the store catalog id.

     * @param pCategoryHS set of category names

     * @param pUserName user name for add_by and mod_by fields

     * @return Returns HashMap of category CatalogCategoryData objects whith category names as keys

     * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

     */

   public HashMap createOrRequestStoreCategories(int pCatalogId, HashSet pCategoryHS, String pUserName)

    throws RemoteException {

        Connection con = null;

        try {

            con = getConnection();

            HashMap categoryHM = new HashMap();



            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            String itemCatalogReq =

                    CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, dbc);

            dbc = new DBCriteria();

            dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,

                    RefCodeNames.ITEM_TYPE_CD.CATEGORY);

            dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCatalogReq);

            ItemDataVector storeCategDV = ItemDataAccess.select(con, dbc);

            CatalogInformation catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();

            CatalogCategoryDataVector categoryDV = catalogInfoEjb.getStoreCatalogCategories(pCatalogId);



            for(Iterator iter=categoryDV.iterator(); iter.hasNext();) {

                CatalogCategoryData ccD = (CatalogCategoryData) iter.next();

                String shortDesc = ccD.getItemData().getShortDesc();

                if(pCategoryHS.contains(shortDesc)) {

                    categoryHM.put(shortDesc, ccD);

                    pCategoryHS.remove(shortDesc);

                }

            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            Date today = sdf.parse(sdf.format(new Date()));

            for(Iterator iter=pCategoryHS.iterator(); iter.hasNext();) {

                String shortDesc = (String) iter.next();

                CatalogCategoryData ccD =

                       saveStoreCategory(pCatalogId, 0, shortDesc,0, pUserName);

                categoryHM.put(shortDesc, ccD);

            }

            return categoryHM;





      } catch (Exception exc) {

        logError(exc.getMessage());

        exc.printStackTrace();

        throw new RemoteException(exc.getMessage(),exc);

      } finally {

        try {

          con.close();

        } catch (SQLException exc) {

          logError(exc.getMessage());

          exc.printStackTrace();

          throw new RemoteException(exc.getMessage(),exc);

        }

      }



    }





   /**

    * Adds master catalog items to all catalogs. Does NOT update order guide

    *

    */

    public void createOrUpdateNscMasterProducts(int pStoreId, int pAcctId, int pStoreCatalogId, int pDistId,

		   ProductDataVector pProductDV, String pUserName)

   throws RemoteException {

	   Connection con = null;



       try {

           con = getConnection();

           Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();

           //find existing products

           HashSet skuNumHS = new HashSet();

           IdVector skuNums = new IdVector();

           for(Iterator iter=pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               String nscSkuNum = pD.getDistributorSku(pDistId);

               if(!skuNumHS.contains(nscSkuNum)) {

                   skuNumHS.add(nscSkuNum);

                   skuNums.add(nscSkuNum);

               }

           }



           // Check existing items

           DBCriteria dbc = new DBCriteria();

           dbc.addOneOf(ItemMappingDataAccess.ITEM_NUM, skuNums);

           dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pDistId);

           dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                   RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

           dbc.addOrderBy(ItemMappingDataAccess.ITEM_NUM);

           ItemMappingDataVector distMappingDV = ItemMappingDataAccess.select(con, dbc);



           HashMap itemsToCreate = new HashMap();

           ItemMappingDataVector itemMappingToUpdate = new ItemMappingDataVector();



           IdVector existingItemIdV = new IdVector();

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

        	   ProductData pD = (ProductData) iter.next();

               String distSku = pD.getDistributorSku(pDistId);

               boolean foundFl = false;

               for(Iterator iter1 = distMappingDV.iterator(); iter1.hasNext();) {

                   ItemMappingData imD = (ItemMappingData) iter1.next();

                   int itemId = imD.getItemId();

                   String ds = imD.getItemNum();

                   int comp = ds.compareTo(distSku);



                   if(comp==0) {

                	   foundFl = true;

                       existingItemIdV.add(new Integer(itemId));

                       pD.getItemData().setItemId(itemId);

                       if(!imD.getItemUom().equals(pD.getUom())||

                          !imD.getItemPack().equals(pD.getPack())) {

                    	   imD.setItemUom(pD.getUom());

                           imD.setItemPack(pD.getPack());

                           imD.setModBy(pUserName);

                           itemMappingToUpdate.add(imD);

                       }

                       break;

                   }

               }

               if(!foundFl) {

            	   if(!itemsToCreate.containsKey(distSku)) {

            		   itemsToCreate.put(distSku, pD);

                   }

               }

           }



           //Check manufactuere properties;

           dbc = new DBCriteria();

           dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, existingItemIdV);

           dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                   RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

           dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);



           ItemMappingDataVector manufMappigDV  =  ItemMappingDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

        	   ProductData pD = (ProductData) iter.next();

               String distSku = pD.getDistributorSku(pDistId);

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }

               int manufId = pD.getManufacturer().getBusEntityId();

               String manufSku = pD.getManufacturerSku();

               boolean foundFl = false;

               for(Iterator iter1 = manufMappigDV.iterator(); iter1.hasNext();) {

                   ItemMappingData imD = (ItemMappingData) iter1.next();

                   int id = imD.getItemId();

                   String ms = imD.getItemNum();

                   if(id > itemId) {

                       break; //not foud

                   }

                   if(id == itemId) {

                	   foundFl = true;

                	   if(!ms.equals(manufSku)||manufId != imD.getBusEntityId()) {

                           //Update the item

                           imD.setItemNum(manufSku);

                           imD.setBusEntityId(manufId);

                           imD.setModBy(pUserName);

                           itemMappingToUpdate.add(imD);

                	   }

                	   break;

                   }

               }

               if(!foundFl) {

                   ItemMappingData imD = new ItemMappingData();

                   imD.setBusEntityId(manufId);

                   imD.setItemId(itemId);

                   imD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

                   imD.setItemNum(manufSku);

                   imD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

                   imD.setAddBy(pUserName);

                   imD.setModBy(pUserName);

                   itemMappingToUpdate.add(imD);

               }

           }



           //Check UOM

           ItemMetaDataVector itemMetaToUpdate = new ItemMetaDataVector();



           dbc = new DBCriteria();

           dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);

           dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"UOM");

           dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

           ItemMetaDataVector itemUomDV  =  ItemMetaDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               String distSku = pD.getDistributorSku(pDistId);

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }

               String uom = pD.getUom();

               boolean foundFl = false;

               for(Iterator iter1 = itemUomDV.iterator(); iter1.hasNext();) {

                   ItemMetaData imD = (ItemMetaData) iter1.next();

                   int id = imD.getItemId();

                   String value = imD.getValue();

                   if(id > itemId) {

                       break; //not foud

                   }

                   if(id == itemId) {

                       foundFl = true;

                       if(!value.equals(uom)) {

                           //Update the item

                           imD.setValue(uom);

                           imD.setModBy(pUserName);

                           itemMetaToUpdate.add(imD);

                       }

                       break;

                   }

               }

               if(!foundFl) {

                   ItemMetaData imD = new ItemMetaData();

                   imD.setAddBy(pUserName);

                   imD.setItemId(itemId);

                   imD.setNameValue("UOM");

                   imD.setValue(uom);

                   imD.setModBy(pUserName);

                   itemMetaToUpdate.add(imD);

               }

           }



           //Check Pack

           dbc = new DBCriteria();

           dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);

           dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"PACK");

           dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);



           ItemMetaDataVector itemPackDV  =  ItemMetaDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               String distSku = pD.getDistributorSku(pDistId);

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }

               String pack = pD.getPack();

               boolean foundFl = false;

               for(Iterator iter1 = itemPackDV.iterator(); iter1.hasNext();) {

                   ItemMetaData imD = (ItemMetaData) iter1.next();

                   int id = imD.getItemId();

                   String value = imD.getValue();

                   if(id > itemId) {

                       break; //not foud

                   }

                   if(id == itemId) {

                       foundFl = true;

                       if(!value.equals(pack)) {

                           //Update the item

                           imD.setValue(pack);

                           imD.setModBy(pUserName);

                           itemMetaToUpdate.add(imD);

                       }

                       break;

                   }

               }

               if(!foundFl) {

                   ItemMetaData imD = new ItemMetaData();

                   imD.setAddBy(pUserName);

                   imD.setItemId(itemId);

                   imD.setNameValue("PACK");

                   imD.setValue(pack);

                   imD.setModBy(pUserName);

                   itemMetaToUpdate.add(imD);

               }

           }



           //Check UPC_NUM

           dbc = new DBCriteria();

           dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);

           dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"UPC_NUM");

           dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);



           ItemMetaDataVector itemUpcNumDV  =  ItemMetaDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               String distSku = pD.getDistributorSku(pDistId);

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }

               String upcNum = pD.getUpc();

               boolean foundFl = false;

               for(Iterator iter1 = itemUpcNumDV.iterator(); iter1.hasNext();) {

                   ItemMetaData imD = (ItemMetaData) iter1.next();

                   int id = imD.getItemId();

                   String value = imD.getValue();

                   if(id > itemId) {

                       break; //not foud

                   }

                   if(id == itemId) {

                       foundFl = true;

                       if(!value.equals(upcNum)) {

                           //Update the item

                           imD.setValue(upcNum);

                           imD.setModBy(pUserName);

                           itemMetaToUpdate.add(imD);

                       }

                       break;

                   }

               }

               if(!foundFl) {

                   ItemMetaData imD = new ItemMetaData();

                   imD.setAddBy(pUserName);

                   imD.setItemId(itemId);

                   imD.setNameValue("UPC_NUM");

                   imD.setValue(upcNum);

                   imD.setModBy(pUserName);

                   itemMetaToUpdate.add(imD);

               }

           }



           //Check short descrition

           ItemDataVector itemToUpdate = new ItemDataVector();

           dbc = new DBCriteria();

           dbc.addOneOf(ItemDataAccess.ITEM_ID, existingItemIdV);

           dbc.addOrderBy(ItemDataAccess.ITEM_ID);



           ItemDataVector itemDV  =  ItemDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               String distSku = pD.getDistributorSku(pDistId);

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }

               String shortDesc = pD.getShortDesc();

               String longDesc = pD.getLongDesc();

               boolean foundFl = false;

               for(Iterator iter1 = itemDV.iterator(); iter1.hasNext();) {

                   ItemData iD = (ItemData) iter1.next();

                   int id = iD.getItemId();

                   if(id > itemId) {

                       break; //not foud

                   }

                   if(id == itemId) {

                       pD.getItemData().setAddBy(iD.getAddBy());

                       pD.getItemData().setAddDate(iD.getAddDate());

                       int skuNum = iD.getSkuNum();

                       if(skuNum==0) {

                           skuNum = itemId + 10000;

                       }

                       pD.getItemData().setSkuNum(skuNum);



                       pD.getItemData().setModBy(pUserName);

                       foundFl = true;

                       if(!iD.getShortDesc().equals(shortDesc)){

                           //Update the item

                           iD.setShortDesc(shortDesc);

                           iD.setModBy(pUserName);

                           itemToUpdate.add(iD);

                       }

                       break;

                   }

               }

               if(!foundFl) { //should never happen

            	   //itemsToUpdate.put(distSku, pD);

               }

           }



           //Updating items on store level

           for(Iterator iter=itemMappingToUpdate.iterator(); iter.hasNext();) {

        	   ItemMappingData imD = (ItemMappingData) iter.next();

               if(imD.getItemMappingId()==0) {

                    ItemMappingDataAccess.insert(con, imD);

               } else {

                    ItemMappingDataAccess.update(con, imD);

               }

           }



           for(Iterator iter=itemMetaToUpdate.iterator(); iter.hasNext();) {

               ItemMetaData imD = (ItemMetaData) iter.next();

               if(imD.getItemMetaId()==0) {

                    ItemMetaDataAccess.insert(con, imD);

               } else {

                    ItemMetaDataAccess.update(con, imD);

               }

           }



           for(Iterator iter=itemToUpdate.iterator(); iter.hasNext();) {

               ItemData iD = (ItemData) iter.next();

               ItemDataAccess.update(con, iD);

           }


           //count new items
           HashMap newProductHM = new HashMap();
           ProductDataVector newProducts = new ProductDataVector();
           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {
               ProductData pD = (ProductData) iter.next();
               if(pD.getItemData().getItemId()!=0) {
                   continue;
               }
               String nscSkuNum = pD.getDistributorSku(pDistId);
               LinkedList prodLL = (LinkedList) newProductHM.get(nscSkuNum);
               if(prodLL==null) {
                   prodLL = new LinkedList();
                   newProductHM.put(nscSkuNum,prodLL);
               }
               prodLL.add(pD);
           }



           //update store catalog
           ProductDataVector storeCatalogToUpdate = new ProductDataVector();

           dbc = new DBCriteria();
           dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, existingItemIdV);

           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);

           dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);



           CatalogStructureDataVector catalogStructureDVS =

               CatalogStructureDataAccess.select(con, dbc);


           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

        	   ProductData pD = (ProductData)iter.next();

        	   int pItemId = pD.getItemData().getItemId();



        	   boolean foundFl = false;

        	   for(int i=0; i<catalogStructureDVS.size(); i++){

            	   CatalogStructureData csD = (CatalogStructureData)catalogStructureDVS.get(i);

            	   int itemId = csD.getItemId();



            	   if(itemId>pItemId){

            		   break;

            	   }

            	   if(itemId==pItemId){

            		   foundFl = true;

            		   break;

            	   }

               }

        	   if(!foundFl){

        		   storeCatalogToUpdate.add(pD);

        	   }



           }

           for(Iterator iter1 = storeCatalogToUpdate.iterator(); iter1.hasNext();) {

        	   ProductData pD = (ProductData) iter1.next();

        	   pD.setCatalogStructure(pStoreCatalogId);

        	   pD.getCatalogStructure().setBusEntityId(pDistId);



        	   //saveMasterCatalogProduct(pStoreCatalogId, pD, pUserName);

        	   //pD.getCatalogStructure().setCustomerSkuNum(""+pD.getItemData().getSkuNum());

        	   String custSkuNum = pD.getCustomerSkuNum();

        	   pD.getCatalogStructure().setCustomerSkuNum("");

        	   saveMasterCatalogProduct(pStoreCatalogId, pD, pUserName);

        	   pD.getCatalogStructure().setCustomerSkuNum(custSkuNum);

           }



           //update account catalog
           CatalogData acctCatalog = accountEjb.getAccountCatalog(pAcctId);
           ProductDataVector acctCatalogToUpdate = new ProductDataVector();



           dbc = new DBCriteria();

           dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, existingItemIdV);

           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, acctCatalog.getCatalogId());

           dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);



           CatalogStructureDataVector catalogStructureDV =

                   CatalogStructureDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

        	   ProductData pD = (ProductData)iter.next();

        	   int pItemId = pD.getItemData().getItemId();



        	   boolean foundFl = false;

        	   for(int i=0; i<catalogStructureDV.size(); i++){

            	   CatalogStructureData csD = (CatalogStructureData)catalogStructureDV.get(i);

            	   int itemId = csD.getItemId();



            	   if(itemId>pItemId){

            		   break;

            	   }

            	   if(itemId==pItemId){

            		   foundFl = true;

            		   break;

            	   }

               }

        	   if(!foundFl){

        		   acctCatalogToUpdate.add(pD);

        	   }



           }

           for(Iterator iter1 = acctCatalogToUpdate.iterator(); iter1.hasNext();) {

        	   ProductData pD = (ProductData) iter1.next();


        	//		   +pD.getDistributorSku(pDistId));

        	   pD.setCatalogStructure(acctCatalog.getCatalogId());

        	   pD.getCatalogStructure().setBusEntityId(pDistId);

        	   saveMasterCatalogProduct(acctCatalog.getCatalogId(), pD, pUserName);

        	   //pD.getCatalogStructure().setCustomerSkuNum(""+pD.getItemData().getSkuNum());

           }



           //update shopping catalogs
           IdVector shoppingCatalogIds = accountEjb.getAllShoppingCatalogsForAcct(pAcctId);

           HashMap<Integer,ProductDataVector> shoppingCatalogToUpdate = new HashMap<Integer,ProductDataVector>();



           dbc = new DBCriteria();

           dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, existingItemIdV);

           dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, shoppingCatalogIds);

           dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

           dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);

           CatalogStructureDataVector shoppingCatalogStructureDV =

                   CatalogStructureDataAccess.select(con, dbc);

           Map<Integer, Set<Integer>> catalogItems = new HashMap<Integer,Set<Integer>>();

           for(Iterator it = shoppingCatalogStructureDV.iterator(); it.hasNext();){

        	   CatalogStructureData csD = (CatalogStructureData)it.next();

        	   Integer catIdI = new Integer(csD.getCatalogId());

        	   Integer itemIdI = new Integer(csD.getItemId());



        	   if(!catalogItems.containsKey(catIdI)){

        		   Set items = new TreeSet();

        		   items.add(itemIdI);

        		   catalogItems.put(catIdI, items);

        	   }else{

        		   Set items = catalogItems.get(catIdI);

        		   items.add(itemIdI);

        	   }



           }



           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }



               for(int j=0; j<shoppingCatalogIds.size(); j++){

            	   Integer shoppingCatIdI = (Integer)shoppingCatalogIds.get(j);

            	   if(catalogItems.containsKey(shoppingCatIdI)){

            		   Set catItems = catalogItems.get(shoppingCatIdI);

            		   if(!catItems.contains(new Integer(itemId))){

            			   if(!shoppingCatalogToUpdate.containsKey(shoppingCatIdI)){

                        	   ProductDataVector pdv = new ProductDataVector();

                        	   pdv.add(pD);

                        	   shoppingCatalogToUpdate.put(shoppingCatIdI, pdv);

                           }else{

                        	   ProductDataVector pdv = shoppingCatalogToUpdate.get(shoppingCatIdI);

                        	   pdv.add(pD);

                           }

            		   }

            	   }else{

            		   //this catalog did not have any of the existingItems

            		   if(!shoppingCatalogToUpdate.containsKey(shoppingCatIdI)){

                    	   ProductDataVector pdv = new ProductDataVector();

                    	   pdv.add(pD);

                    	   shoppingCatalogToUpdate.put(shoppingCatIdI, pdv);

                       }else{

                    	   ProductDataVector pdv = shoppingCatalogToUpdate.get(shoppingCatIdI);

                    	   pdv.add(pD);

                       }

            	   }

               }

           }



/*           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }



               String custSkuNum = Utility.strNN(pD.getCustomerSkuNum()).trim();



               for(int j=0; j<shoppingCatalogIds.size(); j++){



            	   boolean foundFl = false;

            	   Integer shoppingCatIdI = (Integer)shoppingCatalogIds.get(j);



            	   for(Iterator iter1 = shoppingCatalogStructureDV.iterator(); iter1.hasNext();) {



            		   CatalogStructureData csD = (CatalogStructureData) iter1.next();

            		   if(csD.getCatalogId()==shoppingCatIdI.intValue()){



            			   int id = csD.getItemId();

            			   if(id == itemId) {

            				   foundFl = true;

            				   String csn = Utility.strNN(csD.getCustomerSkuNum()).trim();

            				   int dId = csD.getBusEntityId();

            				   if(!custSkuNum.equals(csn) || pDistId !=  dId) {



                            	   if(!shoppingCatalogToUpdate.containsKey(shoppingCatIdI)){

                                	   ProductDataVector pdv = new ProductDataVector();

                                	   pdv.add(pD);

                                	   shoppingCatalogToUpdate.put(shoppingCatIdI, pdv);

                                   }else{

                                	   ProductDataVector pdv = shoppingCatalogToUpdate.get(shoppingCatIdI);

                                	   pdv.add(pD);

                                   }

                               }

                               //iter1.remove();

                               break;

                           }

            		   }

            	   }

            	   if(!foundFl) {

                	   if(!shoppingCatalogToUpdate.containsKey(shoppingCatIdI)){

                		   ProductDataVector pdv = new ProductDataVector();

                		   pdv.add(pD);

                		   shoppingCatalogToUpdate.put(shoppingCatIdI, pdv);

                	   }else{

                    	   ProductDataVector pdv = shoppingCatalogToUpdate.get(shoppingCatIdI);

                    	   pdv.add(pD);

                	   }

                   }



               }



               for(Iterator iter1 = shoppingCatalogStructureDV.iterator(); iter1.hasNext();) {

            	   boolean foundFl = false;



                   CatalogStructureData csD = (CatalogStructureData) iter1.next();

                   Integer shoppingCatId = new Integer(csD.getCatalogId());

                   int id = csD.getItemId();

                   if(id > itemId) {

                       break; //not foud

                   }

                   if(id == itemId) {

                       foundFl = true;

                       String csn = Utility.strNN(csD.getCustomerSkuNum()).trim();

                       int dId = csD.getBusEntityId();

                       if(!custSkuNum.equals(csn) || pDistId !=  dId) {




                    	   if(!shoppingCatalogToUpdate.containsKey(shoppingCatId)){

                        	   ProductDataVector pdv = new ProductDataVector();

                        	   pdv.add(pD);

                        	   shoppingCatalogToUpdate.put(shoppingCatId, pdv);

                           }else{

                        	   ProductDataVector pdv = shoppingCatalogToUpdate.get(shoppingCatId);

                        	   pdv.add(pD);

                           }

                       }

                       iter1.remove();

                       break;

                   }



                   if(!foundFl) {

                	   if(!shoppingCatalogToUpdate.containsKey(shoppingCatId)){

                		   ProductDataVector pdv = new ProductDataVector();

                		   pdv.add(pD);

                		   shoppingCatalogToUpdate.put(shoppingCatId, pdv);

                	   }else{

                    	   ProductDataVector pdv = shoppingCatalogToUpdate.get(shoppingCatId);

                    	   pdv.add(pD);

                	   }

                   }

               }



           }*/


           if(shoppingCatalogToUpdate.size()==0){

        	   //these items were not present in any shopping catalog.. add all items to all catalogs

        	   for(int i=0; i<shoppingCatalogIds.size(); i++){

        		   Integer shoppingCatalogId = (Integer)shoppingCatalogIds.get(i);



        		   for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

        			   ProductData pD = (ProductData) iter.next();

        			   if(!shoppingCatalogToUpdate.containsKey(shoppingCatalogId)){

        				   ProductDataVector pDV = new ProductDataVector();

        				   pDV.add(pD);

        				   shoppingCatalogToUpdate.put(shoppingCatalogId, pDV);

        			   }else{

        				   ProductDataVector pDV = shoppingCatalogToUpdate.get(shoppingCatalogId);

        				   pDV.add(pD);

        			   }

        		   }

        	   }

           }



           for(Map.Entry<Integer, ProductDataVector> en: shoppingCatalogToUpdate.entrySet()){

        	   Integer shoppingCatId = en.getKey();

        	   ProductDataVector pDV = en.getValue();



        	   for(Iterator iter = pDV.iterator(); iter.hasNext(); ) {

        		   ProductData pD = (ProductData) iter.next();

        		   pD.setCatalogStructure(shoppingCatId);

        		   pD.getCatalogStructure().setBusEntityId(pDistId);

                   saveMasterCatalogProduct(shoppingCatId, pD, pUserName);

                   //pD.getCatalogStructure().setCustomerSkuNum(""+pD.getItemData().getSkuNum());

               }

           }





           dbc = new DBCriteria();

           dbc.addOneOf(ContractDataAccess.CATALOG_ID, shoppingCatalogIds);

           IdVector contractStatusCodes = new IdVector();

           contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

           contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);

           contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.INACTIVE);

           contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.LIVE);

           dbc.addOneOf(ContractDataAccess.CONTRACT_STATUS_CD, contractStatusCodes);



           ContractDataVector contractDV = ContractDataAccess.select(con, dbc);

           HashMap catalogContractHM = new HashMap();

           IdVector contractIdV = new IdVector();



           for(Iterator iter=contractDV.iterator(); iter.hasNext();) {

               ContractData cD = (ContractData) iter.next();

               int catalogId = cD.getCatalogId();

               Integer catalogIdI = new Integer(catalogId);

               int contractId = cD.getContractId();

               Integer contractIdI = new Integer(contractId);

               contractIdV.add(contractIdI);

               catalogContractHM.put(catalogIdI, contractIdI);

           }





           //update price

           dbc = new DBCriteria();

           dbc.addOneOf(ContractItemDataAccess.ITEM_ID, existingItemIdV);

           dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);

           dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);

           dbc.addOrderBy(ContractItemDataAccess.CONTRACT_ID);

           ContractItemDataVector contractItemDV =

                   ContractItemDataAccess.select(con, dbc);

           for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

               ProductData pD = (ProductData) iter.next();

               int itemId = pD.getItemData().getItemId();

               if(itemId<=0) {

                   continue;

               }



               for(int j=0; j<shoppingCatalogIds.size(); j++){

            	   Integer shopCatalogIdI = (Integer)shoppingCatalogIds.get(j);

            	   Integer contractIdI = (Integer)catalogContractHM.get(shopCatalogIdI);

                   int contractId = contractIdI.intValue();



                   double price = pD.getCostPrice();

                   boolean foundFl = false;



                   for(Iterator iter1 = contractItemDV.iterator(); iter1.hasNext();) {

                       ContractItemData ciD = (ContractItemData) iter1.next();

                       int id = ciD.getItemId();

                       if(id > itemId) {

                           break; //not foud

                       }

                       if(id==itemId && ciD.getContractId()==contractId) {

                           foundFl = true;

                           BigDecimal amountBD = ciD.getAmount();

                           double amountDb = (amountBD==null)? 0:amountBD.doubleValue();

                           BigDecimal distCostBD = ciD.getDistCost();

                           double distCostDb = (distCostBD==null)? 0:distCostBD.doubleValue();

                           if((price-distCostDb)>0.005 || (distCostDb-price)>0.005 ||

                              (price-amountDb)>0.005 || (amountDb-price)>0.005) {

                               BigDecimal priceBD =

                                       new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);

                               ciD.setDistCost(priceBD);

                               ciD.setAmount(priceBD);

                               ciD.setModBy(pUserName);

                               ContractItemDataAccess.update(con, ciD);

                           }

                           break;

                       }

                   }

                   if(!foundFl) {

                       ContractItemData ciD = ContractItemData.createValue();

                       BigDecimal priceBD =

                               new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);

                       ciD.setAddBy(pUserName);

                       ciD.setModBy(pUserName);

                       ciD.setAmount(priceBD);

                       ciD.setContractId(contractId);

                       //hardcoded in the contract bean, so hardcoded here

                       //cid.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);

                       ciD.setDiscountAmount(null);

                       ciD.setDistCost(priceBD);

                       ciD.setDistBaseCost(null);

                       ciD.setEffDate(null);

                       ciD.setExpDate(null);

                       ciD.setItemId(itemId);

                       ciD = ContractItemDataAccess.insert(con,ciD);

                   }

               }





           }



           Set wrkSet = newProductHM.entrySet();

           for(Iterator iter = wrkSet.iterator(); iter.hasNext();) {

               Map.Entry entry = (Map.Entry) iter.next();

               LinkedList prodLL = (LinkedList) entry.getValue();

               ProductData prodD = (ProductData) prodLL.get(0);

               //Add to system catalog

               //int shopCatalogId = prodD.getCatalogStructure().getCatalogId();

               prodD.setCatalogStructure(pStoreCatalogId);

               String custSkuNum = prodD.getCatalogStructure().getCustomerSkuNum();

               prodD.getCatalogStructure().setCustomerSkuNum("");


               prodD = saveStoreCatalogProduct(pStoreId, pStoreCatalogId, prodD, pUserName);

               //prodD.getCatalogStructure().setCatalogId(shopCatalogId);

               //prodD.getCatalogStructure().setCustomerSkuNum(""+prodD.getItemData().getSkuNum());

               prodD.getCatalogStructure().setCustomerSkuNum(custSkuNum);

               newProducts.add(prodD);

               for(int ii=1; ii<prodLL.size(); ii++) {

                   ProductData pD = (ProductData) prodLL.get(ii);

                   pD.setItemData(prodD.getItemData());

                   pD.setManuMapping(pD.getManuMapping());

                   newProducts.add(pD);

               }

           }



           //Add categories to account catalog

           dbc = new DBCriteria();
           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,acctCatalog.getCatalogId());
           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
           dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
           CatalogStructureDataVector acctCategoryDV =
                   CatalogStructureDataAccess.select(con,dbc);

           //Add New Items
           HashMap newAcctProductHM = new HashMap();
           for(Iterator iter = newProducts.iterator(); iter.hasNext();) {
               ProductData pD = (ProductData) iter.next();
               String nscSkuNum = pD.getDistributorSku(pDistId);
               Integer accountCatalogIdI = new Integer(acctCatalog.getCatalogId());
               String key = nscSkuNum+"*"+accountCatalogIdI;
               LinkedList prodLL = (LinkedList) newAcctProductHM.get(key);
               if(prodLL==null) {
                   prodLL = new LinkedList();
                   newAcctProductHM.put(key,prodLL);
               }
               prodLL.add(pD);
           }

           wrkSet = newAcctProductHM.entrySet();
           HashMap accountCatalogItemHM = new HashMap();
           wrkSet = newAcctProductHM.entrySet();

           for(Iterator iter = wrkSet.iterator(); iter.hasNext();) {
               Map.Entry entry = (Map.Entry) iter.next();
               LinkedList prodLL = (LinkedList) entry.getValue();
               ProductData prodD = (ProductData) prodLL.get(0);
               Integer accountCatalogIdI = new Integer(acctCatalog.getCatalogId());
               int accountCatalogId = accountCatalogIdI.intValue();
               prodD.getCatalogStructure().setCatalogId(accountCatalogId);
               saveMasterCatalogProduct(accountCatalogId, prodD, pUserName);
               //prodD.getCatalogStructure().setCustomerSkuNum(""+prodD.getItemData().getSkuNum());
               //category
               CatalogCategoryDataVector ccDV =  prodD.getCatalogCategories();
               if(ccDV!=null && ccDV.size()>0) {
                   CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
                   int categId = ccD.getCatalogCategoryId();
                   boolean foundFl = false;
                   for(Iterator iter1 =  acctCategoryDV.iterator(); iter1.hasNext();) {
                       CatalogStructureData csD = (CatalogStructureData) iter1.next();
                       if(categId==csD.getItemId() && accountCatalogId==csD.getCatalogId()) {
                           foundFl = true;
                           break;
                       }
                   }
                   if(!foundFl) {
                       CatalogStructureData csD = new CatalogStructureData();
                       csD.setAddBy(pUserName);
                       csD.setCatalogId(accountCatalogId);
                       csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
                       csD.setItemId(categId);
                       csD.setModBy(pUserName);
                       csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
                       acctCategoryDV.add(csD);
                   }
               }
           }

           for(Iterator iter=acctCategoryDV.iterator(); iter.hasNext();) {
               CatalogStructureData csD = (CatalogStructureData) iter.next();
               if(csD.getCatalogStructureId()!=0) {
                   continue;
               }
               CatalogStructureDataAccess.insert(con, csD);
           }



           //Add categories to shopping catalogs
            if (newProducts != null && newProducts.size() > 0) {
               IdVector newProductCategIds = new IdVector();
               for(Iterator iter = newProducts.iterator(); iter.hasNext();) {
                   ProductData pD = (ProductData) iter.next();
                   CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();
                   if(ccDV!=null && ccDV.size()>0) {
                       CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
                       newProductCategIds.add(new Integer(ccD.getCatalogCategoryId()));
                   }
               }
               dbc = new DBCriteria();
               dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,shoppingCatalogIds);
               dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                       RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
               dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, newProductCategIds);

               CatalogStructureDataVector newShoppingCategoryDV = CatalogStructureDataAccess.select(con,dbc);

                for (Iterator ii = newProducts.iterator(); ii.hasNext();) {
              	   ProductData pD = (ProductData) ii.next();
            	   CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();
            	   CatalogStructureDataVector shoppingCategoryDVtoAdd = new CatalogStructureDataVector();
        	       if(ccDV!=null && ccDV.size()>0) {
        		       CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
        		       int categId = ccD.getCatalogCategoryId();

                       for(Iterator i = shoppingCatalogIds.iterator(); i.hasNext();) {
                            int shoppingCatalog = ((Integer)i.next()).intValue();
                            boolean foundFl = false;
                            for (int j=0; j<newShoppingCategoryDV.size(); j++) {
                              CatalogStructureData scD = (CatalogStructureData)newShoppingCategoryDV.get(j);
                              if(scD.getCatalogId() == shoppingCatalog) {
        				        foundFl = true;
        				        break;
        			          }
                            }
                            if (!foundFl) {
                                   CatalogStructureData csD = new CatalogStructureData();
                                   csD.setAddBy(pUserName);
                                   csD.setCatalogId(shoppingCatalog);
                                   csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
                                   csD.setItemId(categId);
                                   csD.setModBy(pUserName);
                                   csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

                                   shoppingCategoryDVtoAdd.add(csD);
                            }

                       }
                       for (Iterator iter = shoppingCategoryDVtoAdd.iterator(); iter.hasNext();) {
                            CatalogStructureData csD = (CatalogStructureData)iter.next();
                            CatalogStructureDataAccess.insert(con, csD);
                        }
                    }
                }
            }

           HashMap<Integer,ProductDataVector> shopCatCategoriesToUpdate = new HashMap<Integer,ProductDataVector>();

           dbc = new DBCriteria();

           dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,shoppingCatalogIds);

           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

        		   RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

           dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

           CatalogStructureDataVector shoppingCategoryDV =CatalogStructureDataAccess.select(con,dbc);



           for(Iterator iter = newProducts.iterator(); iter.hasNext();) {

        	   ProductData pD = (ProductData) iter.next();



        	   CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();

        	   if(ccDV!=null && ccDV.size()>0) {

        		   CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);

        		   int categId = ccD.getCatalogCategoryId();

        		   boolean foundFl = false;

        		   Integer shoppingCatalog = 0;

        		   for(Iterator iter1 =  shoppingCategoryDV.iterator(); iter1.hasNext();) {

        			   CatalogStructureData csD = (CatalogStructureData) iter1.next();

        			   shoppingCatalog = new Integer(csD.getCatalogId());

        			   if(categId==csD.getItemId()) {

        				   foundFl = true;

        				   break;

        			   }

        		   }

        		   if(!foundFl) {

        			   CatalogStructureData csD = new CatalogStructureData();

        			   csD.setAddBy(pUserName);

        			   csD.setCatalogId(shoppingCatalog.intValue());

        			   csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

        			   csD.setItemId(categId);

        			   csD.setModBy(pUserName);

        			   csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

        			   shoppingCategoryDV.add(csD);

        		   }

        	   }

           }





           for(Iterator iter=shoppingCategoryDV.iterator(); iter.hasNext();) {

        	   CatalogStructureData csD = (CatalogStructureData) iter.next();

        	   if(csD.getCatalogStructureId()!=0) {

        		   continue;

        	   }

        	   CatalogStructureDataAccess.insert(con, csD);

           }


           //update price
           // redundent 
           /*HashMap contractItemHM = new HashMap();

           for(Iterator iter = newProducts.iterator(); iter.hasNext();) {

        	   ProductData pD = (ProductData) iter.next();

        	   //int shopCatalogId = pD.getCatalogStructure().getCatalogId();

        	   String nscSkuNum = pD.getDistributorSku(pDistId);



        	   for(int j=0; j<shoppingCatalogIds.size(); j++){

            	   Integer shopCatalogIdI = (Integer)shoppingCatalogIds.get(j);

            	   Integer contractIdI = (Integer)catalogContractHM.get(shopCatalogIdI);

                   int contractId = contractIdI.intValue();



                   HashSet skuHS = (HashSet) contractItemHM.get(contractIdI);

                   if(skuHS==null) {

                	   skuHS = new HashSet();

                	   accountCatalogItemHM.put(contractIdI,skuHS);

                   } else if(skuHS.contains(nscSkuNum)) {

                	   continue;

                   }

                   skuHS.add(nscSkuNum);

                   double price = pD.getCostPrice();

                   ContractItemData ciD = ContractItemData.createValue();

                   BigDecimal priceBD =

                	   new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);

                   ciD.setAddBy(pUserName);

                   ciD.setModBy(pUserName);

                   ciD.setAmount(priceBD);

                   ciD.setContractId(contractId);

                   //hardcoded in the contract bean, so hardcoded here

                   //cid.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);

                   ciD.setDiscountAmount(null);

                   ciD.setDistCost(priceBD);

                   ciD.setDistBaseCost(null);

                   ciD.setEffDate(null);

                   ciD.setExpDate(null);

                   ciD.setItemId(pD.getItemData().getItemId());

                   ciD = ContractItemDataAccess.insert(con,ciD);

        	   }

           }*/

           /* remove category will be tacking cared by item loader nscpc
            // pick categories to remove
            HashSet categoriesToRemove = new HashSet();
            //IdVector productCategIds = new IdVector();
            HashSet productCategIds = new HashSet();
            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {
               ProductData pD = (ProductData) iter.next();
               CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();
               if(ccDV!=null && ccDV.size()>0) {
                    CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
                    Integer cat = new Integer(ccD.getCatalogCategoryId());
                    if (!productCategIds.contains(cat)) {
                        productCategIds.add(cat);
                    }
                    //productCategIds.add(new Integer(ccD.getCatalogCategoryId()));
                }
             }
            // get all catalogs for the store

            dbc = new DBCriteria();
            dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
            dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
            String aaa = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(CatalogDataAccess.CATALOG_ID, aaa);
            dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.STATUS_CD.ACTIVE);

            IdVector allStoreAccShopCatalogIdV = CatalogDataAccess.selectIdOnly(con, CatalogDataAccess.CATALOG_ID, dbc);
            //CatalogDataVector allStoreAccShopCatalogV = getCatalogCollection(dbc);

             dbc = new DBCriteria();
             //dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, shoppingCatalogIds);
             dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, allStoreAccShopCatalogIdV);
             dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                       RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
            CatalogStructureDataVector existingShoppingCategoryDV = CatalogStructureDataAccess.select(con,dbc);
            for (Iterator iter=existingShoppingCategoryDV.iterator(); iter.hasNext();) {
                CatalogStructureData csD = (CatalogStructureData)iter.next();
                int categId = csD.getItemId();
                boolean found = false;
                for (Iterator iter1 = productCategIds.iterator(); iter1.hasNext();) {
                    int prCatId = ((Integer)iter1.next()).intValue();
                    if (prCatId == categId) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // request to remove category
                    categoriesToRemove.add(csD);
                }
            }

           IdVector accountCatalogIdV = new IdVector();
           accountCatalogIdV.add(new Integer(acctCatalog.getCatalogId()));
           removeUnusingCatalogCategory(con, categoriesToRemove, shoppingCatalogIds, accountCatalogIdV, pUserName);*/

       } catch (Exception exc) {

    	   logError(exc.getMessage());

           exc.printStackTrace();

           throw new RemoteException(exc.getMessage(),exc);

       } finally {

    	   try {

    		   con.close();

    	   } catch (SQLException exc) {

    		   logError(exc.getMessage());

    		   exc.printStackTrace();

    		   throw new RemoteException(exc.getMessage(),exc);

    	   }

       }

   }



    /**

     * Adds, updates and removes NSC items

     * @param pStoreId  NSC store id.

     * @param pStoreCatalogId  the store catalog id.

     * @param pDistrId NSC distirbutor id (only one for the store)

     * @param pProductDV set of ProductData object populated by loader

     * @param pUserName user name for add_by and mod_by fields

     * @throws            RemoteException Required by EJB 1.0 and DataNotFoundException

     */
    public void createOrUpdateNscProducts(int pStoreId,  int pStoreCatalogId, int pDistId,

                     ProductDataVector pProductDV, String pUserName)

     throws RemoteException {
       createOrUpdateNscProducts(pStoreId, pStoreCatalogId, pDistId,pProductDV, pUserName, null, true, true);
     }

    public void createOrUpdateNscProducts(int pStoreId,  int pStoreCatalogId, int pDistId,

           ProductDataVector pProductDV, String pUserName, HashMap nscItemNumHM, boolean updateItemDescrFl, boolean updateUpcFl)

    throws RemoteException {

        Connection con = null;

        try {

            con = getConnection();

            //find existing products

            HashSet skuNumHS = new HashSet();

            IdVector skuNums = new IdVector();

            for(Iterator iter=pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String nscSkuNum = pD.getDistributorSku(pDistId);

                if(!skuNumHS.contains(nscSkuNum)) {

                    skuNumHS.add(nscSkuNum);

                    skuNums.add(nscSkuNum);

                }

            }

            // Check existing items

            //HashSet itemsToUpdateHS = new HashSet();

            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(ItemMappingDataAccess.ITEM_NUM, skuNums);

            dbc.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pDistId);

            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                    RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

            dbc.addOrderBy(ItemMappingDataAccess.ITEM_NUM);

            ItemMappingDataVector distMappingDV = ItemMappingDataAccess.select(con, dbc);

            //HashMap itemsToUpdate = new HashMap();

            HashMap itemsToCreate = new HashMap();

            ItemMappingDataVector itemMappingToUpdate = new ItemMappingDataVector();

            IdVector existingItemIdV = new IdVector();

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String distSku = pD.getDistributorSku(pDistId);

                boolean foundFl = false;

                for(Iterator iter1 = distMappingDV.iterator(); iter1.hasNext();) {

                    ItemMappingData imD = (ItemMappingData) iter1.next();

                    int itemId = imD.getItemId();

                    String ds = imD.getItemNum();

                    int comp = ds.compareTo(distSku);

                    //if(comp>0) {

                    //    break; //not foud

                    //}

                    if(comp==0) {

                        foundFl = true;

                        existingItemIdV.add(new Integer(itemId));

                        pD.getItemData().setItemId(itemId);

                        if(!imD.getItemUom().equals(pD.getUom())||

                           !imD.getItemPack().equals(pD.getPack())) {

                            //Update the item-distributor mappings

                            imD.setItemUom(pD.getUom());

                            imD.setItemPack(pD.getPack());

                            imD.setModBy(pUserName);

                            itemMappingToUpdate.add(imD);

                        }

                        break;

                    }

                }

                if(!foundFl) {

                    if(!itemsToCreate.containsKey(distSku)) {

                        itemsToCreate.put(distSku, pD);

                    }

                }

            }

            //Check manufactuere proprerties;

            dbc = new DBCriteria();

            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, existingItemIdV);

            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,

                    RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);



            ItemMappingDataVector manufMappigDV  =  ItemMappingDataAccess.select(con, dbc);

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String distSku = pD.getDistributorSku(pDistId);

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                int manufId = pD.getManufacturer().getBusEntityId();

                String manufSku = pD.getManufacturerSku();

                boolean foundFl = false;

                for(Iterator iter1 = manufMappigDV.iterator(); iter1.hasNext();) {

                    ItemMappingData imD = (ItemMappingData) iter1.next();

                    int id = imD.getItemId();

                    String ms = imD.getItemNum();

                    if(id > itemId) {

                        break; //not foud

                    }

                    if(id == itemId) {

                        foundFl = true;

                        if(!ms.equals(manufSku)||

                            manufId != imD.getBusEntityId()) {

                            //Update the item

                            imD.setItemNum(manufSku);

                            imD.setBusEntityId(manufId);

                            imD.setModBy(pUserName);

                            itemMappingToUpdate.add(imD);

                        }

                        break;

                    }

                }

                if(!foundFl) {

                    ItemMappingData imD = new ItemMappingData();

                    imD.setBusEntityId(manufId);

                    imD.setItemId(itemId);

                    imD.setItemMappingCd(RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

                    imD.setItemNum(manufSku);

                    imD.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);

                    imD.setAddBy(pUserName);

                    imD.setModBy(pUserName);

                    itemMappingToUpdate.add(imD);

                }

            }


            //Check UOM

            ItemMetaDataVector itemMetaToUpdate = new ItemMetaDataVector();



            dbc = new DBCriteria();

            dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);

            dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"UOM");

            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);

            ItemMetaDataVector itemUomDV  =  ItemMetaDataAccess.select(con, dbc);

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String distSku = pD.getDistributorSku(pDistId);

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                String uom = pD.getUom();
                ItemMetaDataVector toUpdateDV = createOrUpdateItemMeta (itemUomDV,  itemId,  "UOM", uom, pUserName, true);
                itemMetaToUpdate.addAll(toUpdateDV);

            }



            //Check Pack

            dbc = new DBCriteria();

            dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);

            dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"PACK");

            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);



            ItemMetaDataVector itemPackDV  =  ItemMetaDataAccess.select(con, dbc);

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String distSku = pD.getDistributorSku(pDistId);

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                String pack = pD.getPack();
                ItemMetaDataVector toUpdateDV = createOrUpdateItemMeta (itemPackDV,  itemId,  "PACK", pack, pUserName, true);
                itemMetaToUpdate.addAll(toUpdateDV);

            }

            //Check NSC_ITEM_NUM (for Kranz)
           if (nscItemNumHM != null && nscItemNumHM.size()>0) {
             dbc = new DBCriteria();
             dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);
             dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"NSC_ITEM_NUM");
             dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
             ItemMetaDataVector itemNscItemNumDV  =  ItemMetaDataAccess.select(con, dbc);
             for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {
               ProductData pD = (ProductData) iter.next();
               String distSku = pD.getDistributorSku(pDistId);
               int itemId = pD.getItemData().getItemId();
               if(itemId<=0) {
                   continue;
               }
               String nscSku = (String)nscItemNumHM.get(distSku);
               ItemMetaDataVector toUpdateDV = createOrUpdateItemMeta (itemNscItemNumDV,  itemId,  "NSC_ITEM_NUM", nscSku, pUserName, true);
               itemMetaToUpdate.addAll(toUpdateDV);
             }
           }

            //Check UPC_NUM

            dbc = new DBCriteria();

            dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, existingItemIdV);

            dbc.addEqualTo(ItemMetaDataAccess.NAME_VALUE,"UPC_NUM");

            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);



            ItemMetaDataVector itemUpcNumDV  =  ItemMetaDataAccess.select(con, dbc);
            
            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String distSku = pD.getDistributorSku(pDistId);

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                String upcNum = pD.getUpc();
                ItemMetaDataVector toUpdateDV = createOrUpdateItemMeta (itemUpcNumDV,  itemId,  "UPC_NUM", upcNum, pUserName, updateUpcFl);
                itemMetaToUpdate.addAll(toUpdateDV);

            }



            //Check short descrition (and  long description - remved by Andy's request)

            ItemDataVector itemToUpdate = new ItemDataVector();

            dbc = new DBCriteria();

            dbc.addOneOf(ItemDataAccess.ITEM_ID, existingItemIdV);

            dbc.addOrderBy(ItemDataAccess.ITEM_ID);



            ItemDataVector itemDV  =  ItemDataAccess.select(con, dbc);

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String distSku = pD.getDistributorSku(pDistId);

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                String shortDesc = pD.getShortDesc();

                String longDesc = pD.getLongDesc();

                boolean foundFl = false;

                for(Iterator iter1 = itemDV.iterator(); iter1.hasNext();) {

                    ItemData iD = (ItemData) iter1.next();

                    int id = iD.getItemId();

                    if(id > itemId) {

                        break; //not foud

                    }

                    if(id == itemId) {

                        pD.getItemData().setAddBy(iD.getAddBy());

                        pD.getItemData().setAddDate(iD.getAddDate());

                        int skuNum = iD.getSkuNum();

                        if(skuNum==0) {

                            skuNum = itemId + 10000;

                        }

                        pD.getItemData().setSkuNum(skuNum);



                        pD.getItemData().setModBy(pUserName);

                        foundFl = true;

                        if(updateItemDescrFl && !iD.getShortDesc().equals(shortDesc)) //||

                        //   !iD.getLongDesc().equals(longDesc))

                        {

                            //Update the item

                            iD.setShortDesc(shortDesc);

                            iD.setModBy(pUserName);

                            itemToUpdate.add(iD);

                        }

                        break;

                    }

                }

                if(!foundFl) { //should never happen

                    //itemsToUpdate.put(distSku, pD);

                }

            }

            //Updating items on store level

           for(Iterator iter=itemMappingToUpdate.iterator(); iter.hasNext();) {

               ItemMappingData imD = (ItemMappingData) iter.next();

               if(imD.getItemMappingId()==0) {

                    ItemMappingDataAccess.insert(con, imD);

               } else {

                    ItemMappingDataAccess.update(con, imD);

               }

           }



           for(Iterator iter=itemMetaToUpdate.iterator(); iter.hasNext();) {

               ItemMetaData imD = (ItemMetaData) iter.next();

               if(imD.getItemMetaId()==0) {

                    ItemMetaDataAccess.insert(con, imD);

               } else {

                    ItemMetaDataAccess.update(con, imD);

               }



           }

           for(Iterator iter=itemToUpdate.iterator(); iter.hasNext();) {

               ItemData iD = (ItemData) iter.next();

               ItemDataAccess.update(con, iD);

           }

            /*

            Set entrySet = itemsToUpdate.entrySet();

            for(Iterator iter = entrySet.iterator(); iter.hasNext();) {

                Map.Entry toUpdate = (Map.Entry) iter.next();

                ProductData pD = (ProductData) toUpdate.getValue();

                CatalogStructureData productCsD = pD.getCatalogStructure();

                int shopCatalogId = productCsD.getCatalogId();

                String custSkuNum = productCsD.getCustomerSkuNum();

                int catalogDistId = productCsD.getBusEntityId();

                productCsD.setCatalogId(pStoreCatalogId);

                productCsD.setCustomerSkuNum(""+pD.getSkuNum());

                productCsD.setBusEntityId(0);





                saveStoreCatalogProduct(pStoreId, pStoreCatalogId, pD, pUserName);

                pD.getCatalogStructure().setCatalogId(shopCatalogId);

                pD.getCatalogStructure().setCustomerSkuNum(custSkuNum);

                pD.getCatalogStructure().setBusEntityId(catalogDistId);



            }

            */

            //Get all catalog ids

            IdVector shoppingCatalogIdV = new IdVector();

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int cId = pD.getCatalogStructure().getCatalogId();

                Integer cIdI = new Integer(cId);

                if(!shoppingCatalogIdV.contains(cIdI)) {

                    shoppingCatalogIdV.add(cIdI);

                }

            }

            //Get account catalogs. Assume that there couldn't be more than 1000 shopping catalog at once

            String acctCatSql =

                " SELECT ca.catalog_id shopping_catalog_id, caa.catalog_id account_catalog_id "+

                " FROM clw_catalog_assoc ca "+

                " join clw_catalog_assoc caa ON ca.bus_entity_id = caa.bus_entity_id "+

                " join clw_catalog c ON caa.catalog_id = c.catalog_id " +

                " AND c.catalog_type_cd = '"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+

                " WHERE ca.catalog_id in ("+IdVector.toCommaString(shoppingCatalogIdV)+") "+

                " AND ca.catalog_assoc_cd = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"'";

            HashMap catalogCatalogHM = new HashMap();

            IdVector accountCatalogIdV = new IdVector();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(acctCatSql);

            while (rs.next()) {

                Integer sCatIdI = new Integer(rs.getInt("shopping_catalog_id"));

                Integer aCatIdI = new Integer(rs.getInt("account_catalog_id"));

                if(!accountCatalogIdV.contains(aCatIdI)) {

                    accountCatalogIdV.add(aCatIdI);

                }

                catalogCatalogHM.put(sCatIdI, aCatIdI);

            }

            rs.close();

            stmt.close();



            //Check account catalogs

            HashMap acctCatalogToUpdate = new HashMap();



            dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, existingItemIdV);

            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, accountCatalogIdV);

            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);

            CatalogStructureDataVector catalogStructureDV =

                    CatalogStructureDataAccess.select(con, dbc);

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                //String distSku = pD.getDistributorSku(pDistId);

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                Integer shopCatalogIdI = new Integer(shopCatalogId);

                Integer accountCatalogIdI = (Integer)catalogCatalogHM.get(shopCatalogIdI);

if(accountCatalogIdI==null) {

    String errorMess = "Shopping catalog "+shopCatalogId+" doesn't have corresponding account catalog";

    throw new Exception (errorMess);

}

                int accountCatalogId = accountCatalogIdI.intValue();



                String custSkuNum = Utility.strNN(pD.getCustomerSkuNum()).trim();

                boolean foundFl = false;

                for(Iterator iter1 = catalogStructureDV.iterator(); iter1.hasNext();) {

                    CatalogStructureData csD = (CatalogStructureData) iter1.next();

                    int id = csD.getItemId();

                    if(id > itemId) {

                        break; //not foud

                    }

                    if(id == itemId && csD.getCatalogId()==accountCatalogId) {

                        foundFl = true;

                        String csn = Utility.strNN(csD.getCustomerSkuNum()).trim();

                        if(!custSkuNum.equals(csn) ) {

                            ProductDataVector pDV =

                                    (ProductDataVector) acctCatalogToUpdate.get(accountCatalogIdI);

                            if(pDV==null) {

                                pDV = new ProductDataVector();

                                acctCatalogToUpdate.put(accountCatalogIdI, pDV);

                            }

                            pDV.add(pD);

                        }

                        break;

                    }

                }

                if(!foundFl) {

                    ProductDataVector pDV =

                            (ProductDataVector) acctCatalogToUpdate.get(accountCatalogIdI);

                    if(pDV==null) {

                        pDV = new ProductDataVector();

                        acctCatalogToUpdate.put(accountCatalogIdI, pDV);

                    }

                    pDV.add(pD);

                }

            }


            for(Iterator iter = acctCatalogToUpdate.keySet().iterator(); iter.hasNext(); ) {

                Integer accountCatalogIdI = (Integer) iter.next();

                int accountCatalogId = accountCatalogIdI.intValue();

                ProductDataVector pDV =

                        (ProductDataVector) acctCatalogToUpdate.get(accountCatalogIdI);

                for(Iterator iter1 = pDV.iterator(); iter1.hasNext();) {

                    ProductData pD = (ProductData) iter1.next();

                    int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                    int catalogDistId = pD.getCatalogStructure().getBusEntityId();

                    pD.getCatalogStructure().setCatalogId(accountCatalogId);

                    pD.getCatalogStructure().setBusEntityId(0);

                    saveNscCatalogProduct(accountCatalogId, pD, pUserName,updateItemDescrFl);

                    pD.getCatalogStructure().setCatalogId(shopCatalogId);

                    pD.getCatalogStructure().setBusEntityId(catalogDistId);

                }

            }



            //Check shopping catalogs

            ProductDataVector shoppingCatalogToUpdate =

                    new ProductDataVector();

            dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, existingItemIdV);

            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, shoppingCatalogIdV);

            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);

            CatalogStructureDataVector shoppingCatalogStructureDV =

                    CatalogStructureDataAccess.select(con, dbc);

            // create map of catalogItemId to catalogStructureId. eg. key=catalogid:itemId, value=catalogStructureId

            dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, shoppingCatalogIdV);

            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            dbc.addOrderBy(CatalogStructureDataAccess.CATALOG_ID);

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

            CatalogStructureDataVector currShoppingCSDV =

                    CatalogStructureDataAccess.select(con, dbc);



            Map catalogItemRemoveMap = new HashMap();


            for(Iterator iter1 = currShoppingCSDV.iterator(); iter1.hasNext();) {

                CatalogStructureData csD = (CatalogStructureData) iter1.next();
                catalogItemRemoveMap.put(csD.getCatalogId()+":" + csD.getItemId(), new Integer(csD.getCatalogStructureId()));

            }

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                String custSkuNum = Utility.strNN(pD.getCustomerSkuNum()).trim();

                boolean foundFl = false;

                for(Iterator iter1 = shoppingCatalogStructureDV.iterator(); iter1.hasNext();) {

                    CatalogStructureData csD = (CatalogStructureData) iter1.next();

                    int id = csD.getItemId();

                    //if(id > itemId) {

                    //    break; //not foud

                    //}

                    if(id == itemId && csD.getCatalogId()==shopCatalogId) {
                        foundFl = true;
                        String csn = Utility.strNN(csD.getCustomerSkuNum()).trim();
                        int dId = csD.getBusEntityId();
                        if(!custSkuNum.equals(csn) || pDistId !=  dId) {
                                shoppingCatalogToUpdate.add(pD);
                        }
                        iter1.remove();
                        break;
                    }
                }

                if(!foundFl) {
                    shoppingCatalogToUpdate.add(pD);
                }

                // removed the item exist in the current catalog from remove list
                Integer catalogItemId = (Integer) catalogItemRemoveMap.get(shopCatalogId+":"+itemId);
                if (catalogItemId != null)
                	catalogItemRemoveMap.remove(shopCatalogId+":"+itemId);
            }

            for(Iterator iter = shoppingCatalogToUpdate.iterator(); iter.hasNext(); ) {
                ProductData pD = (ProductData) iter.next();
                int catalogId = pD.getCatalogStructure().getCatalogId();
                saveNscCatalogProduct(catalogId, pD, pUserName, updateItemDescrFl);
            }


            //remove from shopping catalog, contract and contract and order guide

            dbc = new DBCriteria();

            dbc.addOneOf(ContractDataAccess.CATALOG_ID, shoppingCatalogIdV);

            IdVector contractStatusCodes = new IdVector();

            contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

            contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.LIMITED);

            contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.INACTIVE);

            contractStatusCodes.add(RefCodeNames.CATALOG_STATUS_CD.LIVE);

            dbc.addOneOf(ContractDataAccess.CONTRACT_STATUS_CD, contractStatusCodes);



            ContractDataVector contractDV = ContractDataAccess.select(con, dbc);

            HashMap catalogContractHM = new HashMap();

            IdVector contractIdV = new IdVector();

            for(Iterator iter=contractDV.iterator(); iter.hasNext();) {

                ContractData cD = (ContractData) iter.next();

                int catalogId = cD.getCatalogId();

                Integer catalogIdI = new Integer(catalogId);

                int contractId = cD.getContractId();

                Integer contractIdI = new Integer(contractId);

                contractIdV.add(contractIdI);

                catalogContractHM.put(catalogIdI, contractIdI);

            }

            // pick categories to remove
            HashSet categoriesToRemove = new HashSet();
            IdVector productCategIds = new IdVector();
            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {
               ProductData pD = (ProductData) iter.next();
               CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();
               if(ccDV!=null && ccDV.size()>0) {
                    CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);
                    productCategIds.add(new Integer(ccD.getCatalogCategoryId()));
                }
             }
             dbc = new DBCriteria();
             dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, shoppingCatalogIdV);
             dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                       RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            CatalogStructureDataVector existingShoppingCategoryDV = CatalogStructureDataAccess.select(con,dbc);

            for (Iterator iter=existingShoppingCategoryDV.iterator(); iter.hasNext();) {
                CatalogStructureData csD = (CatalogStructureData)iter.next();
                int categId = csD.getItemId();
                boolean found = false;
                for (Iterator iter1 = productCategIds.iterator(); iter1.hasNext();) {
                    int prCatId = ((Integer)iter1.next()).intValue();
                    if (prCatId == categId) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // request to remove category
                    categoriesToRemove.add(csD);
                }
            }


            Contract contractEjb = APIAccess.getAPIAccess().getContractAPI();

            if (catalogItemRemoveMap.size() > 0){

            	for(Iterator iter=catalogItemRemoveMap.keySet().iterator(); iter.hasNext();) {

                    String key = (String) iter.next();

                    int catalogId = new Integer(key.substring(0, key.indexOf(':')));

                    int itemId = new Integer(key.substring(key.indexOf(':')+1));

                    Integer contractIdI = (Integer) catalogContractHM.get(new Integer(catalogId));

                    int catalogStructureId = ((Integer)catalogItemRemoveMap.get(key)).intValue();

                    dbc = new DBCriteria();

                    dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);

                    dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contractIdI.intValue());

                    IdVector contractItemIdV =

                            ContractItemDataAccess.selectIdOnly(con, dbc);

                    for(Iterator iter1=contractItemIdV.iterator(); iter1.hasNext();) {

                        Integer contractItemIdI = (Integer) iter1.next();

                        contractEjb.removeItem(contractItemIdI.intValue());

                    }

                    removeCatalogProduct(catalogId, itemId, pUserName);

            	}
            }

            removeUnusingCatalogCategory(con, categoriesToRemove, shoppingCatalogIdV, accountCatalogIdV, pUserName);

            //update price

            dbc = new DBCriteria();

            dbc.addOneOf(ContractItemDataAccess.ITEM_ID, existingItemIdV);

            dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);

            dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);

            dbc.addOrderBy(ContractItemDataAccess.CONTRACT_ID);

            ContractItemDataVector contractItemDV =

                    ContractItemDataAccess.select(con, dbc);



            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                Integer shopCatalogIdI = new Integer(shopCatalogId);

                Integer contractIdI = (Integer)catalogContractHM.get(shopCatalogIdI);

                int contractId = contractIdI.intValue();



                double price = pD.getCostPrice();

                boolean foundFl = false;

                for(Iterator iter1 = contractItemDV.iterator(); iter1.hasNext();) {

                    ContractItemData ciD = (ContractItemData) iter1.next();

                    int id = ciD.getItemId();

                    if(id > itemId) {

                        break; //not foud

                    }

                    if(id==itemId && ciD.getContractId()==contractId) {

                        foundFl = true;

                        BigDecimal amountBD = ciD.getAmount();

                        double amountDb = (amountBD==null)? 0:amountBD.doubleValue();

                        BigDecimal distCostBD = ciD.getDistCost();

                        double distCostDb = (distCostBD==null)? 0:distCostBD.doubleValue();

                        if((price-distCostDb)>0.005 || (distCostDb-price)>0.005 ||

                           (price-amountDb)>0.005 || (amountDb-price)>0.005) {

                            BigDecimal priceBD =

                                    new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);

                            ciD.setDistCost(priceBD);

                            ciD.setAmount(priceBD);

                            ciD.setModBy(pUserName);

                            ContractItemDataAccess.update(con, ciD);

                        }

                        break;

                    }

                }

                if(!foundFl) {

                    ContractItemData ciD = ContractItemData.createValue();

                    BigDecimal priceBD =

                            new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);

                    ciD.setAddBy(pUserName);

                    ciD.setModBy(pUserName);

                    ciD.setAmount(priceBD);

                    ciD.setContractId(contractId);

                    //hardcoded in the contract bean, so hardcoded here

                    //cid.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);

                    ciD.setDiscountAmount(null);

                    ciD.setDistCost(priceBD);

                    ciD.setDistBaseCost(null);

                    ciD.setEffDate(null);

                    ciD.setExpDate(null);

                    ciD.setItemId(itemId);

                    ciD = ContractItemDataAccess.insert(con,ciD);

                }

            }



            // Order Guide

            dbc = new DBCriteria();

            dbc.addOneOf(OrderGuideDataAccess.CATALOG_ID, shoppingCatalogIdV);

            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD,

                    RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);

            OrderGuideDataVector orderGuideDV =

                    OrderGuideDataAccess.select(con, dbc);

            HashMap catalogOrderGuideHM = new HashMap();

            IdVector orderGuideIdV = new IdVector();

            for(Iterator iter = orderGuideDV.iterator(); iter.hasNext();) {

                OrderGuideData ogD = (OrderGuideData) iter.next();

                int catalogId = ogD.getCatalogId();

                Integer catalogIdI = new Integer(catalogId);

                int orderGuideId = ogD.getOrderGuideId();

                Integer orderGuideIdI = new Integer (orderGuideId);

                catalogOrderGuideHM.put (catalogIdI, orderGuideIdI);

                orderGuideIdV.add(orderGuideIdI);

            }



            dbc = new DBCriteria();

            dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, existingItemIdV);

            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, orderGuideIdV);

            dbc.addOrderBy(OrderGuideStructureDataAccess.ITEM_ID);

            dbc.addOrderBy(OrderGuideStructureDataAccess.ORDER_GUIDE_ID);



            OrderGuideStructureDataVector orderGuideStructureDV =

                    OrderGuideStructureDataAccess.select(con, dbc);



            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int itemId = pD.getItemData().getItemId();

                if(itemId<=0) {

                    continue;

                }

                int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                Integer shopCatalogIdI = new Integer(shopCatalogId);

                Integer orderGuideIdI = (Integer)catalogOrderGuideHM.get(shopCatalogIdI);

                int orderGuideId = orderGuideIdI.intValue();



                boolean foundFl = false;

                for(Iterator iter1 = orderGuideStructureDV.iterator(); iter1.hasNext();) {

                    OrderGuideStructureData ogsD = (OrderGuideStructureData) iter1.next();

                    int id = ogsD.getItemId();

                    if(id > itemId) {

                        break; //not foud

                    }

                    if(id==itemId && ogsD.getOrderGuideId()==orderGuideId) {

                        foundFl = true;

                        if(ogsD.getQuantity()!=0) {

                            ogsD.setQuantity(0);

                            ogsD.setModBy(pUserName);

                            OrderGuideStructureDataAccess.update(con, ogsD);

                        }

                        break;

                    }

                }

                if(!foundFl) {

                    OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();

                    ogsD.setAddBy(pUserName);

                    ogsD.setModBy(pUserName);

                    ogsD.setQuantity(0);

                    ogsD.setItemId(itemId);

                    ogsD.setOrderGuideId(orderGuideId);

                    ogsD = OrderGuideStructureDataAccess.insert(con,ogsD);

                }

            }



            //Add New Items

            HashMap newProductHM = new HashMap();

            ProductDataVector newProducts = new ProductDataVector();

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                if(pD.getItemData().getItemId()!=0) {

                    continue;

                }

                String nscSkuNum = pD.getDistributorSku(pDistId);

                LinkedList prodLL = (LinkedList) newProductHM.get(nscSkuNum);

                if(prodLL==null) {

                    prodLL = new LinkedList();

                    newProductHM.put(nscSkuNum,prodLL);

                }

                prodLL.add(pD);

            }

            /*

            Set wrkSet = newProductHM.entrySet();

            for(Iterator iter = wrkSet.iterator(); iter.hasNext();) {

                Map.Entry entry = (Map.Entry) iter.next();

                LinkedList prodLL = (LinkedList) entry.getValue();

                ProductData prodD = (ProductData) prodLL.get(0);

            }

            throw new Exception ("BREAK");

            */

                //Add to system catalog



    /////////////////////////////////////////////



            Set wrkSet = newProductHM.entrySet();

            for(Iterator iter = wrkSet.iterator(); iter.hasNext();) {

                Map.Entry entry = (Map.Entry) iter.next();

                LinkedList prodLL = (LinkedList) entry.getValue();

                ProductData prodD = (ProductData) prodLL.get(0);

                //Add to system catalog

                int shopCatalogId = prodD.getCatalogStructure().getCatalogId();

                prodD.getCatalogStructure().setCatalogId(pStoreCatalogId);

                String custSkuNum = prodD.getCatalogStructure().getCustomerSkuNum();

                prodD.getCatalogStructure().setCustomerSkuNum("");

                prodD = saveStoreCatalogProduct(pStoreId, pStoreCatalogId, prodD, pUserName);

                prodD.getCatalogStructure().setCatalogId(shopCatalogId);

                prodD.getCatalogStructure().setCustomerSkuNum(custSkuNum);

                newProducts.add(prodD);

                for(int ii=1; ii<prodLL.size(); ii++) {

                    ProductData pD = (ProductData) prodLL.get(ii);

                    pD.setItemData(prodD.getItemData());

                    pD.setManuMapping(pD.getManuMapping());

                    newProducts.add(pD);

                }

            }



            //Add to account catalog

            //Add categories to an account catalog

            dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,accountCatalogIdV);

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            CatalogStructureDataVector acctCategoryDV =

                    CatalogStructureDataAccess.select(con,dbc);





            //Add New Items

            HashMap newAcctProductHM = new HashMap();

            for(Iterator iter = newProducts.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                String nscSkuNum = pD.getDistributorSku(pDistId);

                int catalogId = pD.getCatalogStructure().getCatalogId();

                Integer catalogIdI = new Integer(catalogId);

                Integer accountCatalogIdI = (Integer) catalogCatalogHM.get(catalogIdI);

                String key = nscSkuNum+"*"+accountCatalogIdI;

                LinkedList prodLL = (LinkedList) newAcctProductHM.get(key);

                if(prodLL==null) {

                    prodLL = new LinkedList();

                    newAcctProductHM.put(key,prodLL);

                }

                prodLL.add(pD);

            }

            wrkSet = newAcctProductHM.entrySet();

            HashMap accountCatalogItemHM = new HashMap();

            wrkSet = newAcctProductHM.entrySet();

            for(Iterator iter = wrkSet.iterator(); iter.hasNext();) {

                Map.Entry entry = (Map.Entry) iter.next();

                LinkedList prodLL = (LinkedList) entry.getValue();

                ProductData prodD = (ProductData) prodLL.get(0);



                int catalogId = prodD.getCatalogStructure().getCatalogId();

                //String nscSkuNum = prodD.getDistributorSku(pDistId);

                Integer catalogIdI = new Integer(catalogId);

                Integer accountCatalogIdI = (Integer) catalogCatalogHM.get(catalogIdI);

if(accountCatalogIdI==null) {

    String errorMess = "Shopping catalog "+catalogId+" doesn't have corresponding account catalog";

    throw new Exception (errorMess);

}

                int accountCatalogId = accountCatalogIdI.intValue();

                int shopCatalogId = prodD.getCatalogStructure().getCatalogId();

                prodD.getCatalogStructure().setCatalogId(accountCatalogId);

                saveNscCatalogProduct(accountCatalogId, prodD, pUserName, updateItemDescrFl);

                prodD.getCatalogStructure().setCatalogId(shopCatalogId);

            }


            //Add categories to an shopping catalogs

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int catalogId = pD.getCatalogStructure().getCatalogId();

                int acctCatalogId = (Integer) catalogCatalogHM.get(catalogId);

                CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();

                if(ccDV!=null && ccDV.size()>0) {

                    CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);

                    int categId = ccD.getCatalogCategoryId();

                    boolean foundFl = false;

                    for(Iterator iter1 =  acctCategoryDV.iterator(); iter1.hasNext();) {

                        CatalogStructureData csD = (CatalogStructureData) iter1.next();

                        if(categId==csD.getItemId() && acctCatalogId==csD.getCatalogId()) {

                            foundFl = true;

                            break;

                        }

                    }

                    if(!foundFl) {

                        CatalogStructureData csD = new CatalogStructureData();

                        csD.setAddBy(pUserName);

                        csD.setCatalogId(acctCatalogId);

                        csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

                        csD.setItemId(categId);

                        csD.setModBy(pUserName);

                        csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

                        acctCategoryDV.add(csD);

                    }

                }

            }

            for(Iterator iter=acctCategoryDV.iterator(); iter.hasNext();) {

                CatalogStructureData csD = (CatalogStructureData) iter.next();

                if(csD.getCatalogStructureId()!=0) {

                    continue;

                }

                CatalogStructureDataAccess.insert(con, csD);

            }



            //New Shopping catalog items

            for(Iterator iter = newProducts.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int catalogId = pD.getCatalogStructure().getCatalogId();

               saveNscCatalogProduct(catalogId, pD, pUserName, updateItemDescrFl);

            }


            //Add categories to an shopping catalogs

            dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,shoppingCatalogIdV);

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);

            CatalogStructureDataVector shoppingCategoryDV =

                    CatalogStructureDataAccess.select(con,dbc);

            for(Iterator iter = pProductDV.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int catalogId = pD.getCatalogStructure().getCatalogId();

                CatalogCategoryDataVector ccDV =  pD.getCatalogCategories();

                if(ccDV!=null && ccDV.size()>0) {

                    CatalogCategoryData ccD = (CatalogCategoryData) ccDV.get(0);

                    int categId = ccD.getCatalogCategoryId();

                    boolean foundFl = false;

                    for(Iterator iter1 =  shoppingCategoryDV.iterator(); iter1.hasNext();) {

                        CatalogStructureData csD = (CatalogStructureData) iter1.next();

                        if(categId==csD.getItemId() && catalogId==csD.getCatalogId()) {

                            foundFl = true;

                            break;

                        }

                    }

                    if(!foundFl) {

                        CatalogStructureData csD = new CatalogStructureData();

                        csD.setAddBy(pUserName);

                        csD.setCatalogId(catalogId);

                        csD.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

                        csD.setItemId(categId);

                        csD.setModBy(pUserName);

                        csD.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);

                        shoppingCategoryDV.add(csD);

                    }

                }

            }

            for(Iterator iter=shoppingCategoryDV.iterator(); iter.hasNext();) {

                CatalogStructureData csD = (CatalogStructureData) iter.next();

                if(csD.getCatalogStructureId()!=0) {

                    continue;

                }

                CatalogStructureDataAccess.insert(con, csD);

            }





            //update price

            HashMap contractItemHM = new HashMap();

            for(Iterator iter = newProducts.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                String nscSkuNum = pD.getDistributorSku(pDistId);

                Integer shopCatalogIdI = new Integer(shopCatalogId);

                Integer contractIdI = (Integer)catalogContractHM.get(shopCatalogIdI);

                int contractId = contractIdI.intValue();

                HashSet skuHS = (HashSet) contractItemHM.get(contractIdI);

                if(skuHS==null) {

                    skuHS = new HashSet();

                    accountCatalogItemHM.put(contractIdI,skuHS);

                } else if(skuHS.contains(nscSkuNum)) {

                    continue;

                }

                skuHS.add(nscSkuNum);

                double price = pD.getCostPrice();

                ContractItemData ciD = ContractItemData.createValue();

                BigDecimal priceBD =

                        new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);

                ciD.setAddBy(pUserName);

                ciD.setModBy(pUserName);

                ciD.setAmount(priceBD);

                ciD.setContractId(contractId);

                //hardcoded in the contract bean, so hardcoded here

                //cid.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);

                ciD.setDiscountAmount(null);

                ciD.setDistCost(priceBD);

                ciD.setDistBaseCost(null);

                ciD.setEffDate(null);

                ciD.setExpDate(null);

                ciD.setItemId(pD.getItemData().getItemId());

                ciD = ContractItemDataAccess.insert(con,ciD);



            }



            // Order Guide

            HashMap orderGuideItemHM = new HashMap();

            for(Iterator iter = newProducts.iterator(); iter.hasNext();) {

                ProductData pD = (ProductData) iter.next();

                int itemId = pD.getItemData().getItemId();

                String nscSkuNum = pD.getDistributorSku(pDistId);

                int shopCatalogId = pD.getCatalogStructure().getCatalogId();

                Integer shopCatalogIdI = new Integer(shopCatalogId);

                Integer orderGuideIdI = (Integer)catalogOrderGuideHM.get(shopCatalogIdI);

                int orderGuideId = orderGuideIdI.intValue();

                HashSet skuHS = (HashSet) orderGuideItemHM.get(orderGuideIdI);

                if(skuHS==null) {

                    skuHS = new HashSet();

                    orderGuideItemHM.put(orderGuideIdI,skuHS);

                } else if(skuHS.contains(nscSkuNum)) {

                    continue;

                }



                OrderGuideStructureData ogsD = OrderGuideStructureData.createValue();

                ogsD.setAddBy(pUserName);

                ogsD.setModBy(pUserName);

                ogsD.setQuantity(0);

                ogsD.setOrderGuideId(orderGuideId);

                ogsD.setItemId(itemId);

                ogsD = OrderGuideStructureDataAccess.insert(con,ogsD);

                skuHS.add(nscSkuNum);

            }



      } catch (Exception exc) {

        logError(exc.getMessage());

        exc.printStackTrace();

        throw new RemoteException(exc.getMessage(),exc);

      } finally {

        try {

          con.close();

        } catch (SQLException exc) {

          logError(exc.getMessage());

          exc.printStackTrace();

          throw new RemoteException(exc.getMessage(),exc);

        }

      }



    }



    private static void tryToRemoveCategoryFromCatalog(Connection con,

            int storeCatId, Set<Integer> catalogIds, Set<Integer> categoryIds,

            String pItemTypeCd) throws Exception {

        System.err.println("Will try to remove storeCatId:" + storeCatId

                + " catalogs:" + catalogIds + " and categories:" + categoryIds);

        if (catalogIds.size() == 0 && categoryIds.size() == 0) {

            return; // nothing to delete

        }

        if (catalogIds.size() > 1 && categoryIds.size() > 1) {

            throw new Exception(

                    "One of set <CatalogIds> or <CategoryIds> must have only one element!");

        }


        Set<Integer> wereRemoved = new TreeSet<Integer>();

        Set<Integer> ids = tryToRemoveCategoryFromCatalog2(con, storeCatId,

                catalogIds, categoryIds);

        wereRemoved.addAll(ids);

        ids = getCategoryParentIds(con, ids);

        if (ids.isEmpty() == false) {

            ids = tryToRemoveCategoryFromCatalog2(con, storeCatId, catalogIds,

                    ids);

            wereRemoved.addAll(ids);

            ids = getCategoryParentIds(con, ids);

            if (ids.isEmpty() == false) {

                ids = tryToRemoveCategoryFromCatalog2(con, storeCatId,

                        catalogIds, ids);

                wereRemoved.addAll(ids);

            }

        }

    }



    public static Set<Integer> tryToRemoveCategoryFromCatalog2(Connection con,

            int storeCatId, Set<Integer> catalogIds, Set<Integer> categoryIds)

            throws Exception {

        Set<Integer> ids = getCategoryIdsWOItems(con, catalogIds, categoryIds);

        if (ids == null || ids.isEmpty() == true) {

            return new HashSet<Integer>();

        }

        ids = getCategoryIdsForDelete(con, storeCatId, catalogIds, categoryIds);

        if (catalogIds.size() > 0 && ids.size() > 0) {

            DBCriteria dbc = new DBCriteria();

            dbc

                    .addOneOf(CatalogStructureDataAccess.ITEM_ID,

                            new ArrayList(ids));

            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, new ArrayList(

                    catalogIds));

            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,

                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            CatalogStructureDataAccess.remove(con, dbc, true);

        }

        return ids;

    }



    public static Set<Integer> getCategoryIdsForDelete(Connection con,

            int storeCatId, Set<Integer> catalogIds, Set<Integer> categoryIds)

            throws Exception {

        Set<Integer> ids1 = getCategoryIdsWOChildren(con, storeCatId,

                catalogIds, categoryIds);

        Set<Integer> ids2 = getCategoryIdsWithChildrenFromOtherCatalog(con,

                storeCatId, catalogIds, categoryIds);

        ids1.addAll(ids2);

        return ids1;

    }



    public static Set<Integer> getCategoryIdsWOItems(Connection con,

            Set<Integer> catalogIds, Set<Integer> categoryIds) throws Exception {

        StringBuilder inCatalogs = new StringBuilder();

        StringBuilder inCategories = new StringBuilder();

        for (Integer catalogId : catalogIds) {

            if (inCatalogs.length() > 0) {

                inCatalogs.append(',');

            }

            inCatalogs.append(catalogId);

        }

        for (Integer categoryId : categoryIds) {

            if (inCategories.length() > 0) {

                inCategories.append(',');

            }

            inCategories.append(categoryId);

        }

        StringBuilder buffer = new StringBuilder();

        buffer.append("SELECT DISTINCT item_id AS category FROM (\n");

        buffer.append("    SELECT cs.catalog_id, cs.item_id, ia.item1_id\n");

        buffer.append("    FROM clw_catalog_structure cs\n");

        buffer.append("    LEFT JOIN clw_item_assoc ia "

                + "ON cs.item_id = ia.item2_id"

                + " AND cs.catalog_id = ia.catalog_id\n");

        buffer.append("    WHERE  1=1\n");

        buffer.append("        AND cs.CATALOG_STRUCTURE_CD = '"

                + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'\n");

        buffer.append("        AND cs.catalog_id IN  (" + inCatalogs + ")\n");

        buffer.append("        AND cs.item_id IN (" + inCategories + ")\n");

        buffer.append(") WHERE item1_id IS null\n");

        String sql = buffer.toString();

        Set<Integer> result = new TreeSet<Integer>();

        Statement statement = null;

        ResultSet resultSet = null;

        try {

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int categoryId = resultSet.getInt("category");

                result.add(categoryId);

            }

        } catch (Exception e) {

            throw e;

        } finally {

            if (resultSet != null) {

                resultSet.close();

            }

            if (statement != null) {

                statement.close();

            }

        }

        return result;

    }



    public static Set<Integer> getCategoryIdsWOChildren(Connection con,

            int storeCatId, Set<Integer> catalogIds, Set<Integer> categoryIds)

            throws Exception {

        StringBuilder inCatalogs = new StringBuilder();

        StringBuilder inCategories = new StringBuilder();

        for (Integer catalogId : catalogIds) {

            if (inCatalogs.length() > 0) {

                inCatalogs.append(',');

            }

            inCatalogs.append(catalogId);

        }

        for (Integer categoryId : categoryIds) {

            if (inCategories.length() > 0) {

                inCategories.append(',');

            }

            inCategories.append(categoryId);

        }

        StringBuilder buffer = new StringBuilder();

        buffer.append("SELECT DISTINCT item_id AS category FROM (\n");

        buffer.append("    SELECT cs.catalog_structure_id, cs.catalog_id, "

                + "cs.item_id, ia.item1_id\n");

        buffer.append("    FROM clw_catalog_structure cs\n");

        buffer.append("    LEFT JOIN clw_item_assoc ia "

                + "ON cs.item_id = ia.item2_id " + "AND ia.catalog_id = "

                + storeCatId + "\n");

        buffer.append("        AND ia.item_assoc_cd = '"

                + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "'\n");

        buffer.append("    WHERE  1=1\n");

        buffer.append("        AND cs.catalog_structure_cd = '"

                + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'\n");

        buffer.append("        AND cs.catalog_id IN  (" + inCatalogs + ")\n");

        buffer.append("        AND cs.item_id IN (" + inCategories + ")\n");

        buffer.append(") WHERE item1_id IS null\n");

        String sql = buffer.toString();

        Set<Integer> result = new TreeSet<Integer>();

        Statement statement = null;

        ResultSet resultSet = null;

        try {

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int categoryId = resultSet.getInt("category");

                result.add(categoryId);

            }

        } catch (Exception e) {

            throw e;

        } finally {

            if (resultSet != null) {

                resultSet.close();

            }

            if (statement != null) {

                statement.close();

            }

        }

        return result;

    }



    public static Set<Integer> getCategoryIdsWithChildrenFromOtherCatalog(

            Connection con, int storeCatId, Set<Integer> catalogIds,

            Set<Integer> categoryIds) throws Exception {

        StringBuilder inCatalogs = new StringBuilder();

        StringBuilder inCategories = new StringBuilder();

        for (Integer catalogId : catalogIds) {

            if (inCatalogs.length() > 0) {

                inCatalogs.append(',');

            }

            inCatalogs.append(catalogId);

        }

        for (Integer categoryId : categoryIds) {

            if (inCategories.length() > 0) {

                inCategories.append(',');

            }

            inCategories.append(categoryId);

        }

        StringBuilder buffer = new StringBuilder();

        buffer.append("SELECT DISTINCT item_id AS category FROM (");

        buffer.append("SELECT catalog_structure_id, catalog_id, item_id, "

                + "Count(item1_id), Count (sub_cat_id) FROM (\n");

        buffer.append("    SELECT cs.catalog_structure_id, "

                + "cs.catalog_id, cs.item_id, "

                + "ia.item1_id, cs1.item_id sub_cat_id\n");

        buffer.append("    FROM clw_catalog_structure cs\n");

        buffer.append("    JOIN clw_item_assoc ia ON cs.item_id = ia.item2_id "

                + "AND ia.catalog_id = 4 AND ia.ITEM_ASSOC_CD = '"

                + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "'\n");

        buffer.append("    LEFT JOIN clw_catalog_structure cs1 "

                + "ON cs1.catalog_id = cs.catalog_id "

                + "AND ia.item1_id = cs1.item_id\n");

        buffer.append("    WHERE  1=1\n");

        buffer.append("        AND cs.catalog_id IN  (" + inCatalogs + ")\n");

        buffer.append("        AND cs.CATALOG_STRUCTURE_CD = '"

                + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'\n");

        buffer.append("        AND cs.item_id IN (" + inCategories + ")\n");

        buffer.append(") GROUP BY catalog_structure_id, catalog_id, item_id "

                + "HAVING Count(sub_cat_id) = 0)\n");

        String sql = buffer.toString();

        System.out

                .println("CatalogBean getCategoryIdsWithChildrenFromOtherCatalog SQL:\n"

                        + sql);

        Set<Integer> result = new TreeSet<Integer>();

        Statement statement = null;

        ResultSet resultSet = null;

        try {

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int categoryId = resultSet.getInt("category");

                result.add(categoryId);

            }

        } catch (Exception e) {

            throw e;

        } finally {

            if (resultSet != null) {

                resultSet.close();

            }

            if (statement != null) {

                statement.close();

            }

        }

        return result;

    }



    public static Set<Integer> getCategoryParentIds(Connection con,

            Set<Integer> categoryIds) throws Exception {

        if (categoryIds == null || categoryIds.isEmpty()) {

            return new HashSet<Integer>();

        }

        StringBuilder inCategories = new StringBuilder();

        for (Integer categoryId : categoryIds) {

            if (inCategories.length() > 0) {

                inCategories.append(',');

            }

            inCategories.append(categoryId);

        }

        StringBuilder buffer = new StringBuilder();

        buffer.append("SELECT item2_id AS parent\n");

        buffer.append("FROM clw_item_assoc t1\n");

        buffer.append("WHERE item_assoc_cd = '"

                + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "'\n");

        buffer.append("    AND item1_id IN (" + inCategories + ")\n");

        String sql = buffer.toString();

        Statement statement = null;

        ResultSet resultSet = null;

        Set<Integer> result = new TreeSet<Integer>();

        try {

            statement = con.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                result.add(resultSet.getInt("parent"));

            }

        } catch (Exception e) {

            throw e;

        } finally {

            if (resultSet != null) {

                resultSet.close();

            }

            if (statement != null) {

                statement.close();

            }

        }

        return result;

    }

    /**

     * Gets the catalog association information values to be used by the request.

     * @param pBusEntityIds  the BusEntityIds .

     * @param pCatalogAssocCd  the catalog association code .

     * @return new CatalogAssocDataVector()

     * @throws            RemoteException Required by EJB 1.0

     */

    public CatalogAssocDataVector getCatalogAssocCollection(IdVector pBusEntityIds,

                                                   String pCatalogAssocCd) throws

      RemoteException {

      CatalogAssocDataVector catalogAssocDV = null;

      Connection con = null;

      try {

        con = getConnection();

        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, pCatalogAssocCd);

        crit.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityIds);

        crit.addJoinTable(CatalogDataAccess.CLW_CATALOG);

        crit.addJoinTableEqualTo(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

        crit.addJoinCondition(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID, CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_ID);

        crit.addJoinTableEqualTo(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);



        crit.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);

        crit.addOrderBy(CatalogAssocDataAccess.CATALOG_ID);

        catalogAssocDV = CatalogAssocDataAccess.select(con, crit);

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

          throw new RemoteException(

            "Error. CatalogBean.getCatalogAssocCollection() SQL Exception happened: " +

            exc.getMessage());

        }

      }

      return catalogAssocDV;

    }



    /**

     * Gets the catalog information values to be used by the request.

     * @param pCrit  the DBCriteria .

     * @return new CatalogDataVector()

     * @throws            RemoteException Required by EJB 1.0

     */

    public CatalogDataVector getCatalogCollection(DBCriteria pCrit) throws

      RemoteException {

    	CatalogDataVector catalogDV = null;

      Connection con = null;

      try {

        con = getConnection();

        catalogDV = CatalogDataAccess.select(con, pCrit);

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

          throw new RemoteException(

            "Error. CatalogBean.getCatalogAssocCollection() SQL Exception happened: " +

            exc.getMessage());

        }

      }

      return catalogDV;

    }



    public int createCatalog( String pCatalogName, String pCatalogType, String pCatalogStatus, String pAddBy) throws RemoteException, DuplicateNameException{

       	CatalogData catalogD = null;

    	Connection con = null;

    	int catalogId  = 0;

    	try {

		   con = getConnection();

           catalogD = CatalogData.createValue();

	       catalogD.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

	       catalogD.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);

	       catalogD.setAddBy(pAddBy);

	       catalogD.setModBy(pAddBy);

	       catalogD.setShortDesc(pCatalogName);

	       catalogD = CatalogDataAccess.insert(con, catalogD);

	       catalogId = catalogD.getCatalogId();

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

              throw new RemoteException(

                "Error. CatalogBean.createCatalog() SQL Exception happened: " +

                exc.getMessage());

            }

        }

        return catalogId;

    }

    public void createCatalogAssoc( int pCatalogId, int pBusEntityId, String pAssocType, String pAddBy)throws

    RemoteException {

    	CatalogAssocData catalogAssocD = null;

    	Connection con = null;

    	try {

            con = getConnection();

	        catalogAssocD = new CatalogAssocData();

	        catalogAssocD.setBusEntityId(pBusEntityId);

	        catalogAssocD.setCatalogId(pCatalogId);

	        catalogAssocD.setCatalogAssocCd(pAssocType);

	        catalogAssocD.setAddBy(pAddBy);

	        catalogAssocD.setModBy(pAddBy);

	        CatalogAssocDataAccess.insert(con, catalogAssocD);

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

              throw new RemoteException(

                "Error. CatalogBean.createCatalogAssoc() SQL Exception happened: " +

                exc.getMessage());

            }

          }

        }
        private ItemMetaDataVector createOrUpdateItemMeta (ItemMetaDataVector pImDV,  int pItemId,  String pItemMetaCd, String pItemMetaValue, String pUserName, boolean updateFlag){
            ItemMetaDataVector itemMetaToUpdate = new ItemMetaDataVector();
            boolean foundFl = false;
            for (Iterator iter1 = pImDV.iterator(); iter1.hasNext(); ) {
              ItemMetaData imD = (ItemMetaData) iter1.next();
              int id = imD.getItemId();
              String value = imD.getValue();
              if (id > pItemId) {
                break; //not foud
              }
              if (id == pItemId) {
                foundFl = true;
                if (!value.equals(Utility.strNN(pItemMetaValue))) {
                  if (!updateFlag)
                	  break;
                  //Update the item
                  imD.setValue(pItemMetaValue);
                  imD.setModBy(pUserName);
                  itemMetaToUpdate.add(imD);
                }
                break;
              }
            }
            if (!foundFl && Utility.isSet(pItemMetaValue)) {
              ItemMetaData imD = new ItemMetaData();
              imD.setAddBy(pUserName);
              imD.setItemId(pItemId);
              imD.setNameValue(pItemMetaCd);
              imD.setValue(pItemMetaValue);
              imD.setModBy(pUserName);
              itemMetaToUpdate.add(imD);
            }
            return itemMetaToUpdate;
        }

        public void createCatalogContractAndOGAssociation(int pAccountId,  int pCatalogId, String pCatalogName, String pCatalogStatusCd, String pUserName ) throws RemoteException {
          Connection con = null;

          try {

              con = getConnection();

          //Order guide
          OrderGuideData ogD = OrderGuideData.createValue();
          String orderGuideName = pCatalogName;
          if (orderGuideName.length() > 30) {
            orderGuideName = orderGuideName.substring(0, 29);
          }
          ogD.setShortDesc(orderGuideName);
          ogD.setAddBy(pUserName);
          ogD.setModBy(pUserName);
          ogD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
          ogD.setCatalogId(pCatalogId);
          OrderGuideDataAccess.insert(con, ogD);

          //freight table get account freigh table
          String sql = "SELECT  con.freight_table_id \n " +
              " FROM clw_catalog_assoc ca, clw_catalog c, clw_contract con  \n " +
              " WHERE ca.bus_entity_id = " + pAccountId + " \n " +
              " AND c.catalog_id = ca.catalog_id \n " +
              " AND c.catalog_type_cd = 'ACCOUNT' \n " +
              " AND c.catalog_id = con.catalog_id \n ";

          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          int freightTableId = 0;
          while (rs.next()) {
            freightTableId = rs.getInt(1);
          }
          rs.close();
          stmt.close();

          ContractData contractD = ContractData.createValue();
          contractD.setCatalogId(pCatalogId);
          contractD.setContractItemsOnlyInd(false);
          contractD.setContractStatusCd(pCatalogStatusCd);
          contractD.setContractTypeCd("UNKNOWN");
          contractD.setEffDate(new Date());
          contractD.setFreightTableId(freightTableId);
          contractD.setHidePricingInd(false);
          contractD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
          contractD.setRankWeight(0);
          contractD.setShortDesc(pCatalogName);
          contractD.setRefContractNum("0");
          contractD.setModBy(pUserName);
          contractD.setAddBy(pUserName);
          ContractDataAccess.insert(con, contractD);
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
              throw new RemoteException(
                "Error. CatalogBean.createCatalogContractAssociation() SQL Exception happened: " +
                exc.getMessage());
            }
          }
        }

        public void updateItemMeta(int pItemId, ItemMetaDataVector imDV, String pAddBy) throws RemoteException {
          Connection con = null;
          try {
             con = getConnection();

             DBCriteria dbc = new DBCriteria();
             dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, pItemId);
             ItemMetaDataVector existingItemMeta = ItemMetaDataAccess.select(con, dbc);

             if(existingItemMeta!=null && existingItemMeta.size()>0){
               //update
               for (int ii = 0; ii < imDV.size(); ii++) {
                   ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);
                   String nameVal = itemMetaD.getNameValue();
                   String val = itemMetaD.getValue();
                   boolean found = false;
                   ItemMetaData returnMetaD = ItemMetaData.createValue();
                   for(int jj=0; jj<existingItemMeta.size(); jj++){
                       ItemMetaData existingMetaD = (ItemMetaData)existingItemMeta.get(jj);
                       if(existingMetaD.getNameValue().equalsIgnoreCase(nameVal)){
                               found = true;
                               returnMetaD = existingMetaD;
                               break;
                       }
                   }
                   if (found) {
                       returnMetaD.setValue(itemMetaD.getValue());
                       returnMetaD.setModBy(pAddBy);
                       ItemMetaDataAccess.update(con, returnMetaD);
                   }
                   else {
                     //new meta attribute
                     itemMetaD = ProductDAO.saveItemMetaInfo(con, pItemId, pAddBy,itemMetaD);
                   }
                 }
             }else{
               //new item meta
               for (int ii = 0; ii < imDV.size(); ii++) {
                 ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);
                 if (itemMetaD != null && itemMetaD.getValue().trim().length() > 0) {
                         itemMetaD = ProductDAO.saveItemMetaInfo(con, pItemId, pAddBy,itemMetaD);
                 }
               }
             }
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
                 throw new RemoteException(
                   "Error. CatalogBean.updateItemMeta() SQL Exception happened: " +
                   exc.getMessage());
               }
             }
        }

      /*  @param productD  ProductData object
        * @param user user login id
        * @return ProductData
        * @throws            RemoteException Required by EJB 1.0, DataNotFoundException
        */

       private ProductData saveNscCatalogProduct(int pCatalogId, ProductData productD,
                                             String user, boolean updateItemDescrFl) throws RemoteException,
         DataNotFoundException {
         Connection con = null;
         DBCriteria dbc;
         try {
           con = getConnection();

           //Check catalog existance

           dbc = new DBCriteria();
           dbc.addEqualTo(CatalogDataAccess.CATALOG_ID, pCatalogId);
           IdVector idV = CatalogDataAccess.selectIdOnly(con, dbc);
           if (idV.size() == 0) {
             throw new DataNotFoundException(
               "Error. CatalogBean.saveCatalogProduct(). No catalog found. Catalog id: " +
               pCatalogId);
           }

           //Insert/Update Item

           ItemData itemD = productD.getItemData();
           if (itemD == null) {
             throw new DataNotFoundException("Error. CatalogBean.saveCatalogProduct(). No ItemData object found in the product ");
           }
           int skuNum = itemD.getSkuNum();
           boolean clwSwitch = Utility.getClwSwitch();
           itemD.setModBy(user);
           if (itemD.getItemId() == 0) {
              itemD.setAddBy(user);
              itemD = ItemDataAccess.insert(con, itemD);
              itemD.setSkuNum(itemD.getItemId() + SKU_MINIMUM);
              ItemDataAccess.update(con, itemD);
              productD.setItemData(itemD);
           } else if (updateItemDescrFl){
             ItemDataAccess.update(con, itemD);
           }
           int productId = itemD.getItemId();


/*         //Remove and insert attributes

           dbc = new DBCriteria();
           dbc.addEqualTo(ItemMetaDataAccess.ITEM_ID, productId);
           ItemMetaDataAccess.remove(con, dbc);
           ItemMetaDataVector imDV = productD.getProductAttributes();
           for (int ii = 0; ii < imDV.size(); ii++) {
             ItemMetaData itemMetaD = (ItemMetaData) imDV.get(ii);
             if (itemMetaD != null && itemMetaD.getValue().trim().length() > 0) {
               itemMetaD = ProductDAO.saveItemMetaInfo(con, productId, user,itemMetaD);
               productD.setItemMeta(itemMetaD, itemMetaD.getNameValue());
             }
           }
*/
           //Edit manufacturer mapping if was changed

           ItemMappingData manufacturerMappingD = productD.getManuMapping();
           if (manufacturerMappingD != null) {
             int manufacturerId = manufacturerMappingD.getBusEntityId();
             dbc = new DBCriteria();
             dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);
             dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                            RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
             ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,dbc);
             if (itemMappingDV.size() > 1) {
               throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). Product has more then one manufacturer. Product id: " +
                                         productId);
             }
             if (itemMappingDV.size() == 0) {
               manufacturerMappingD.setAddBy(user);
               manufacturerMappingD.setModBy(user);
               manufacturerMappingD = ItemMappingDataAccess.insert(con,manufacturerMappingD);
               productD.setManuMapping(manufacturerMappingD);
             }
             if (itemMappingDV.size() == 1) {
               ItemMappingData imD = (ItemMappingData) itemMappingDV.get(0);
               if ((imD.getItemNum() == null &&
                    manufacturerMappingD.getItemNum() != null) ||
                   (imD.getItemNum() != null &&
                    !imD.getItemNum().equals(manufacturerMappingD.getItemNum())) ||
                   imD.getBusEntityId() != manufacturerMappingD.getBusEntityId()) {
                 imD.setItemNum(manufacturerMappingD.getItemNum());
                 imD.setBusEntityId(manufacturerMappingD.getBusEntityId());
                 imD.setModBy(user);
                 ItemMappingDataAccess.update(con, imD);
               }
             }
           }

           //Save distributor mapping if changed

           ItemMappingDataVector itemMappingDV = productD.getDistributorMappings();
           int[] save = new int[itemMappingDV.size()];
           for (int ii = 0; ii < save.length; ii++) {
             save[ii] = 2;
           }
           dbc = new DBCriteria();
           dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);
           dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                          RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
           ItemMappingDataVector itemMappingBaseDV = ItemMappingDataAccess.select(con, dbc);
           IdVector removeV = new IdVector();
           for (int ii = 0; ii < itemMappingBaseDV.size(); ii++) {
             ItemMappingData itemMappingBaseD = (ItemMappingData) itemMappingBaseDV.get(ii);
             int id = itemMappingBaseD.getBusEntityId();
             int jj = 0;
             if (null == itemMappingBaseD.getItemNum()) {
               itemMappingBaseD.setItemNum("");
             }
             if (null == itemMappingBaseD.getItemUom()) {
               itemMappingBaseD.setItemUom("");
             }

             if (null == itemMappingBaseD.getItemPack()) {
               itemMappingBaseD.setItemPack("");
             }
             for (; jj < itemMappingDV.size(); jj++) {
               ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(jj);
               if (itemMappingD.getBusEntityId() == id) {
                 if (itemMappingBaseD.getItemNum().equals(itemMappingD.getItemNum())
                     && itemMappingBaseD.getItemUom().equals(itemMappingD.getItemUom())
                     && itemMappingBaseD.getItemPack().equals(itemMappingD.getItemPack())) {
                   save[jj] = 0;
                 } else {
                   save[jj] = 1;
                 }
                 break;
               }
             }
             if (jj == itemMappingDV.size()) {
               removeV.add(new Integer(id));
             }
           }
           //add/update
           for (int ii = 0; ii < itemMappingDV.size(); ii++) {
             if (save[ii] == 1) {
               ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);
               itemMappingD.setModBy(user);
               ItemMappingDataAccess.update(con, itemMappingD);
             } else if (save[ii] == 2) {
               ItemMappingData itemMappingD = (ItemMappingData) itemMappingDV.get(ii);
               itemMappingD.setItemId(productId);
               itemMappingD.setAddBy(user);
               itemMappingD.setModBy(user);
               ItemMappingDataAccess.insert(con, itemMappingD);
             }
           }

           //delete
           dbc = new DBCriteria();
           dbc.addEqualTo(ItemMappingDataAccess.ITEM_ID, productId);
           dbc.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, removeV);
           try {
             ItemMappingDataAccess.remove(con, dbc);
           }
           catch (SQLException ex) {
             log.info("[CatalogBean]--SQL EXCEPTION-->>>for :<"+productD.getShortDesc()+ "> -->" + ex.getMessage());
           }

           //Catalog mapping

           CatalogStructureData catalogStructureD = productD.getCatalogStructure();
           if (catalogStructureD == null) {
             productD.setCatalogStructure(pCatalogId);
             catalogStructureD = productD.getCatalogStructure();
           }
           catalogStructureD.setModBy(user);
           catalogStructureD.setItemId(productId);

           //specifically for NSC PC loader - account and shopping catalogs only

           catalogStructureD.setCustomerSkuNum(productD.getCustomerSkuNum());
           dbc = new DBCriteria();
           dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, productId);
           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
           dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                          RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
           IdVector idV1 = CatalogStructureDataAccess.selectIdOnly(con, dbc);
           if (idV1.size() > 1) {
             throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). Catalog and product  have duplicated relation. Catalog id: " +
                                       pCatalogId + ". Product id: " + productId);
           }
           if (idV1.size() == 0) {
             catalogStructureD.setAddBy(user);
             catalogStructureD = CatalogStructureDataAccess.insert(con,catalogStructureD, true);
             productD.setCatalogStructure(catalogStructureD);
           }

           if (idV1.size() == 1) {
             CatalogStructureData csD = CatalogStructureDataAccess.select(con,
               ((Integer) idV1.get(0)).intValue());
             String shortDesc = csD.getShortDesc();
             if (shortDesc == null) shortDesc = "";
             String shortDescNew = catalogStructureD.getShortDesc();

             if (shortDescNew == null || shortDescNew.equals("")) {
                     shortDescNew = shortDesc;
                     catalogStructureD.setShortDesc(shortDesc);
             }
             String customerSkuNum = csD.getCustomerSkuNum();
             if (customerSkuNum == null) customerSkuNum = "";
             String customerSkuNumNew = catalogStructureD.getCustomerSkuNum();
             if (csD.getItemId() != catalogStructureD.getItemId() ||
                 csD.getBusEntityId() != catalogStructureD.getBusEntityId() ||
                 csD.getCatalogId() != catalogStructureD.getCatalogId() ||
                 csD.getCatalogStructureCd().equals(catalogStructureD.
                                                    getCatalogStructureCd()) == false ||
                 shortDesc.equals(shortDescNew) == false ||
                 customerSkuNum.equals(customerSkuNumNew) == false
               ) {
               csD.setAddBy(csD.getAddBy());
               csD.setItemId(catalogStructureD.getItemId());
               csD.setBusEntityId(catalogStructureD.getBusEntityId());
               csD.setCatalogStructureCd(catalogStructureD.getCatalogStructureCd());
               csD.setShortDesc(catalogStructureD.getShortDesc());
               csD.setCustomerSkuNum(catalogStructureD.getCustomerSkuNum());

               CatalogStructureDataAccess.update(con, csD, true);

               productD.setCatalogStructure(csD);
              }
           }
         if (productD.getCatalogCategories() != null)
               saveProductCategory(pCatalogId, productD, user, con);
         } catch (NamingException exc) {
           logError("exc.getMessage");
           exc.printStackTrace();
           throw new RemoteException(
             "Error. CatalogBean.saveCatalogProduct() Naming Exception happened");
         } catch (SQLException exc) {
           logError("exc.getMessage");
           exc.printStackTrace();
           throw new RemoteException(
             "Error. CatalogBean.saveCatalogProduct() SQL Exception happened");
         } catch (RemoteException exc) {
           throw exc;
         } catch (Exception exc) {
           logError("exc.getMessage");
           exc.printStackTrace();
           throw new RemoteException("Error. CatalogBean.saveCatalogProduct(). " +
                                     exc.getMessage());
         } finally {
           try {
             con.close();
           } catch (SQLException exc) {
             logError("exc.getMessage");
             exc.printStackTrace();
             throw new RemoteException(
               "Error. CatalogBean.saveCatalogProduct() SQL Exception happened");
           }
         }

         return productD;

  }



  private void removeUnusingCatalogCategory(Connection con, HashSet categoriesToRemove,
                                     IdVector shoppingCatalogIdV,  IdVector accountCatalogIdV,
         String pUserName) throws Exception {
            // remove categories if do not use in shopping catalogs
           String sql = "select * from clw_catalog_structure cs where cs.item_id in ( " +
                                    "select ia.item1_id from clw_item_assoc ia where ia.item2_id = ?)" +
                                    "and cs.catalog_id = ? ";
           PreparedStatement pstmt = con.prepareStatement(sql);

           IdVector notRemove = new IdVector();
           for (Iterator iter = categoriesToRemove.iterator(); iter.hasNext();) {
                CatalogStructureData cat = (CatalogStructureData)iter.next();
                for (int i=0; i<shoppingCatalogIdV.size(); i++) {
                   int shoppingCatalogId = ((Integer)shoppingCatalogIdV.get(i)).intValue();

                   pstmt.setInt(1, cat.getItemId());
                   pstmt.setInt(2, shoppingCatalogId);

                   ResultSet rs = pstmt.executeQuery();
                   if (rs.next()) {
                       notRemove.add(cat.getItemId());
                   }
                   rs.close();
                }
           }
           pstmt.close();

            try {
           for (Iterator iter = categoriesToRemove.iterator(); iter.hasNext();) {
            CatalogStructureData cat = (CatalogStructureData)iter.next();
            for (Iterator iter1 = notRemove.iterator(); iter1.hasNext();) {
                int i = ((Integer)iter1.next()).intValue();
                if (i == cat.getItemId()) {
                    iter.remove();
                    break;
                }
            }
           }
            } catch (Exception e) {
            }

            // check if categoriesToRemove using at other shopping catalogs of the account
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, accountCatalogIdV);
            CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
            IdVector accountIdV = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);
            Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
            notRemove = new IdVector();
            for (Iterator iter1=accountIdV.iterator(); iter1.hasNext();) {
                int accountId = ((Integer)iter1.next()).intValue();
                IdVector shoppingCatalogIds = accountEjb.getAllShoppingCatalogsForAcct(accountId);
                CatalogData accountCatalogD = accountEjb.getAccountCatalog(accountId);
                if (shoppingCatalogIds.size() <= 0) continue;

               // remove categories in account catalog if do not use in all shopping catalogs of account
               for (Iterator iter = categoriesToRemove.iterator(); iter.hasNext();) {
                    CatalogStructureData cat = (CatalogStructureData)iter.next();

                    // get item ids of category
                   dbc = new DBCriteria();
                   dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, cat.getItemId());
                   String itemAssocSql = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM1_ID, dbc);

                    // get items of category in shopping catalogs of account
                   dbc = new DBCriteria();
                   dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemAssocSql);
                   dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, shoppingCatalogIds);

                   CatalogStructureDataVector res = CatalogStructureDataAccess.select(con, dbc);

                    if (res.size() == 0) {
                        // no items with the category, we can remove category for this account
                        log.info("Removing category " + cat.getItemId() + " for account catalog " +
                            accountCatalogD.getCatalogId());
                        dbc = new DBCriteria();
                        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, accountCatalogD.getCatalogId());
                        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, cat.getItemId());
                        CatalogStructureDataAccess.remove(con, dbc, true);
                    } else {
                        notRemove.add(cat.getItemId());
                    }
               }
            }
            try {
           for (Iterator iter = categoriesToRemove.iterator(); iter.hasNext();) {
            CatalogStructureData cat = (CatalogStructureData)iter.next();
            for (Iterator iter1 = notRemove.iterator(); iter1.hasNext();) {
                int i = ((Integer)iter1.next()).intValue();
                if (i == cat.getItemId()) {
                    iter.remove();
                    break;
                }
            }
           }
            } catch (Exception e) {
            }


           // check if categoriesToRemove using at all account catalogs of the store
           for (Iterator iter = categoriesToRemove.iterator(); iter.hasNext();) {
               CatalogStructureData cat = (CatalogStructureData)iter.next();
               // get item ids of category
               dbc = new DBCriteria();
               dbc.addEqualTo(ItemAssocDataAccess.ITEM2_ID, cat.getItemId());
               String itemAssocSql = ItemAssocDataAccess.getSqlSelectIdOnly(ItemAssocDataAccess.ITEM1_ID, dbc);

               // check if there is at list one product with the category
               dbc = new DBCriteria();
               dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, itemAssocSql);
               CatalogStructureDataVector res = CatalogStructureDataAccess.select(con, dbc);

               if (res.size() == 0) {
                   // not items with the category, we can remove category from catalog structure and from item
                   log.info("Removing category " + cat.getItemId());
                   removeCatalogCategory(0, cat.getItemId(), pUserName);
               } else {
               }
           }
    }

      /*
       * Find Catalog item Distributor
       * @param catalogId
       * @param itemId
       * @return CatalogStructureData
       * @throws            RemoteException Required by EJB 1.0, DataNotFoundException
       */
       public CatalogStructureData getCatalogStructureData(int pCatalogId, int pItemId) throws RemoteException
         , DataNotFoundException {

         Connection con = null;

         CatalogStructureData csD;
         DBCriteria dbc;

         try {
              con = getConnection();

              dbc = new DBCriteria();
              dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
              dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);


              CatalogStructureDataVector csdV = CatalogStructureDataAccess.select(con,dbc);

              if (csdV.size() == 0) {
                  throw new DataNotFoundException(
                    "Error. CatalogBean.getCatalogStructureData(). No item found for Catalog id: " +
                    pCatalogId + ", item id: " + pItemId);
              }
              if (csdV.size() > 1) {
                  throw new RemoteException("Error. CatalogBean.getCatalogStructureData(). Found more than one catalog with a unique Catalog Id. Catalog id: " +
                                            pCatalogId);
              }
              csD = (CatalogStructureData) csdV.get(0);
         } catch (DataNotFoundException exc) {
        	 throw exc;
         } catch (RemoteException exc) {
        	 throw exc;
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
               throw new RemoteException(
                 "Error. CatalogBean. getCatalogStructureData(). SQL Exception happened: " +
                 exc.getMessage());
             }
         }

         return csD;

       }
       
   	   public int checkCatalogAssocExist(int pAccountId) throws RemoteException {
   		   
   	       CatalogAssocDataVector caDV = null;
   	      
           Connection con = null;
           
           DBCriteria dbc;
           
           int return_code;
           
           try {
             con = getConnection();
               
             dbc = new DBCriteria();

             dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountId);

             dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,

                          RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

             caDV = CatalogAssocDataAccess.select(con, dbc);
             
             if (caDV.size() == 0) {
            	 
            	 return_code = 1; //Account Catalog does not exist
            	 
             } else {
            	 
            	 return_code = 0; //Account Catalog exists
            	 
             }
             
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

                 throw new RemoteException(

                   "Error. CatalogBean.checkCatalogAssocExist() SQL Exception happened: " +

                   exc.getMessage());

               }

             } //finally

             return return_code;

       } //public int checkCatalogAssocExist(int pAccountId)

    public void updateProductCategory(ProductDataVector pProducts, String pUserName) throws RemoteException {
        Connection conn = null;
        logInfo("updateProductCategory()=> BEGIN");
        try {
            conn = getConnection();
            for (Object oProduct : pProducts) {
                ProductData product = (ProductData) oProduct;
                if (product.getCatalogCategories() != null) {
                    updateProductCategory(conn, product.getCatalogStructure().getCatalogId(), product, "Item Loader");
                }
            }
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(conn);
        }
        logInfo("updateProductCategory()=> END.");
    }

    private void updateProductCategory(Connection pCon, int pCatalogId, ProductData pProduct, String pUser) throws SQLException {

        DBCriteria dbc;

        if (pProduct == null || pProduct.getCatalogCategories() == null || pProduct.getCatalogCategories().isEmpty()) {
            return;
        }

        int itemId = pProduct.getItemData().getItemId();

        dbc = new DBCriteria();
        dbc.addEqualTo(ItemAssocDataAccess.ITEM1_ID, itemId);
        dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

        ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(pCon, dbc);

        List<Integer> itemAssocIds = new ArrayList<Integer>();
        for (Object anItemAssocDV : itemAssocDV) {
            ItemAssocData imD = (ItemAssocData) anItemAssocDV;
            itemAssocIds.add(imD.getItemAssocId());
        }

        int categoryId = ((CatalogCategoryData) pProduct
                .getCatalogCategories()
                .get(0))
                .getCatalogCategoryId();

        if (categoryId > 0 && itemAssocDV.size() == 1 && ((ItemAssocData) itemAssocDV.get(0)).getItem2Id() == categoryId) {
            return;
        }

        ItemAssocData iaD = ItemAssocData.createValue();
        iaD.setItem1Id(itemId);
        iaD.setItem2Id(categoryId);
        iaD.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
        iaD.setCatalogId(pCatalogId);
        iaD.setAddBy(pUser);
        iaD.setModBy(pUser);

        //if there is more than one itemAssoc update one of those and remove the rest.

        if (itemAssocDV.size() > 0) {

            ItemAssocData iaDOld = (ItemAssocData) itemAssocDV.get(0);
            int itemAssocId = iaDOld.getItemAssocId();
            iaD.setItemAssocId(itemAssocId);
            iaD.setAddDate(iaDOld.getAddDate());
            iaD.setAddBy(iaDOld.getAddBy());

            ItemAssocDataAccess.update(pCon, iaD);

            //remove itemAssoc except the one updated.
            if (itemAssocDV.size() > 1) {
                itemAssocIds.remove(itemAssocId);
                dbc = new DBCriteria();
                dbc.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_ID, itemAssocIds);
                ItemAssocDataAccess.remove(pCon, dbc);
            }

        }

        if (itemAssocDV.size() < 1) {         //if there is no itemAssoc then insert
            ItemAssocDataAccess.insert(pCon, iaD);
        }

    }
    
    public BusEntityData getStoreForCatalog(int pCatalogId) throws RemoteException {
        Connection conn = null;
        BusEntityData result = null;
        logInfo("getStoreForCatalog()=> BEGIN");
        
        try {
            conn = getConnection();
            
            String ca = CatalogAssocDataAccess.CLW_CATALOG_ASSOC;
            String be = BusEntityDataAccess.CLW_BUS_ENTITY;

            DBCriteria dbc = new DBCriteria();
            dbc.addJoinTableEqualTo(ca, CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
            dbc.addJoinTableEqualTo(ca, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

            dbc.addJoinCondition(be, BusEntityDataAccess.BUS_ENTITY_ID, ca, CatalogAssocDataAccess.BUS_ENTITY_ID);

            BusEntityDataVector storesV = BusEntityDataAccess.select(conn, dbc);

            if (Utility.isSet(storesV)) {
                result = (BusEntityData)storesV.get(0);
            }
            logInfo("getStoreForCatalog()=> END");
            return result;
        } catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            
            throw new RemoteException(exc.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    public void addCategoryWithParentCategoriesToCatalogs(IdVector pCatalogIdV,
            int pCategoryId, String pUserName) throws RemoteException {
    	try {
	    	addCategoryToCatalogs(pCategoryId, pCatalogIdV, pUserName);
	
	        // get all parents categories for categoryId
	        ProductInformation prInfEjb = getAPIAccess().getProductInformationAPI();
	        IdVector categoryIds = prInfEjb.getAllParentCategories(pCategoryId);
	
	        // add parents categories to catalog
	        for (Iterator iter = categoryIds.iterator(); iter.hasNext(); ) {
	            int catId = ((Integer)iter.next()).intValue();
	            addCategoryToCatalogs(catId, pCatalogIdV, pUserName);
	        }
    	} catch (Exception exc) {
    	      exc.printStackTrace();
    	      throw new RemoteException(exc.getMessage());
    	}
    }

}
