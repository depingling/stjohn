
package com.cleanwise.service.api.session;

/**
 * Title:        DWDimBeen
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       T Besser
 */

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import java.sql.*;
import java.util.*;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.view.utils.Constants;


/**
 * <code>BusEntityDAO</code>
 */
public class DWOperationBean extends ApplicationServicesAPI
{
    /**
     * Constructor.
     */
    public DWOperationBean()
    {
    }
    /**
     *  Describe <code>ejbCreate</code> method here.
     *
     *@exception  CreateException  if an error occurs
     *@exception  RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }
    public int getStoreDimId (String storeId)  throws RemoteException {
      Connection conn = null;
      int storeDimId = -1;
      try {
        conn = getReportConnection();
        String sql =
            " select STORE_DIM_ID from DW_STORE_DIM where STORE_ID = " + storeId;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
          storeDimId = rs.getInt("STORE_DIM_ID");
      }
      stmt.close();
    }
    catch (Exception exc) {
      exc.printStackTrace();
      throw processException(exc);
    }
    finally {
      closeConnection(conn);
    }
    return storeDimId;


    }

    /**
     * <code>getDistributorByCriteria</code>  returns a vector of DistributorData's
     * meeting the criteria of the BusEntitySearchCriteria.
     *
     * @param pCrit a <code>BusEntitySearchCriteria</code> value the criteria
     * @return a <code>DistributorDataVector</code> value
     * @exception SQLException if an error occurs
     */
    public  DistributorDataVector getDistributorByCriteria(BusEntitySearchCriteria pCrit)
        throws RemoteException {

      DistributorDataVector dV = new DistributorDataVector();
      Connection conn = null;
    // STORE FILTER===========================================================//
      Integer storeId = null;
      if (pCrit.getStoreBusEntityIds() != null &&
          pCrit.getStoreBusEntityIds().size() > 0) {
        storeId = (Integer) pCrit.getStoreBusEntityIds().get(0);
      }
      else {
        throw new RemoteException("Store ID can't be null. ");
      }
      String storeFilter = "(select STORE_DIM_ID from DW_STORE_DIM \n" +
          "  where STORE_ID = " + storeId + " ) \n";

    //  NAMES FILTER=========================================================//
      String nameFilter = getSearchSqlByFilterName ("JD_DIST_NAME", pCrit.getSearchName(), pCrit.getSearchNameType());;
     //=======================================================================//
      try {
//        conn = getConnection();
        conn = getReportConnection();
        String sql =
            "select  \n " +
            " DISTRIBUTOR_DIM_ID, \n" +
            " DIST_ID, DIST_NAME, \n" +
            " JD_DIST_ID, JD_DIST_NAME, \n" +
            " JD_DIST_CITY, JD_DIST_STATE, \n" +
            " JD_DIST_ADDRESS, JD_DIST_STATUS_CD \n" +
            "from DW_DISTRIBUTOR_DIM where \n" +
            " STORE_DIM_ID = " + storeFilter + " \n" +
            nameFilter +
            " order by JD_DIST_NAME";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (pCrit.getResultLimit() != QueryRequest.UNLIMITED) {
    //          stmt.setMaxRows(2000);
        }
        while (rs.next()) {
          DistributorData dd = DistributorData.createValue();

          BusEntityData be = new BusEntityData();
          be.setBusEntityId(rs.getInt("DISTRIBUTOR_DIM_ID"));
          be.setShortDesc( (rs.getString("JD_DIST_NAME") != null) ? rs.getString("JD_DIST_NAME") : "");
          be.setBusEntityStatusCd((rs.getString("JD_DIST_STATUS_CD") != null) ? rs.getString("JD_DIST_STATUS_CD") : "");

          AddressData ad = new AddressData();
          ad.setAddress1((rs.getString("JD_DIST_ADDRESS") != null) ? rs.getString("JD_DIST_ADDRESS") : "");
          ad.setCity((rs.getString("JD_DIST_CITY") != null) ? rs.getString("JD_DIST_CITY") : "");
          ad.setStateProvinceCd((rs.getString("JD_DIST_STATE") != null) ? rs.getString("JD_DIST_STATE") : "");

          dd.setBusEntity(be);
          dd.setPrimaryAddress(ad);

          dV.add(dd);
        }
        stmt.close();
      }
      catch (Exception exc) {
        exc.printStackTrace();
        throw processException(exc);
      }
      finally {
        closeConnection(conn);
      }
      return dV;
    }
    /**
     * <code>getManufacturerByCriteria</code>  returns a vector of ManufacturerData's
     * meeting the criteria of the BusEntitySearchCriteria.
     *
     * @param pCrit a <code>BusEntitySearchCriteria</code> value the criteria
     * @return a <code>ManufacturerDataVector</code> value
     * @exception SQLException if an error occurs
     */

    public ManufacturerDataVector getManufacturerByCriteria (BusEntitySearchCriteria pCrit)
        throws RemoteException {
      Connection conn = null;
      ManufacturerDataVector mV = new ManufacturerDataVector();
      // STORE FILTER===========================================================//
      Integer storeId = null;
      if (pCrit.getStoreBusEntityIds() != null && pCrit.getStoreBusEntityIds().size() >0 ){
        storeId = (Integer)pCrit.getStoreBusEntityIds().get(0);
      } else {
        throw new RemoteException("Store ID can't be null. ");
      }
      String storeFilter = "(select STORE_DIM_ID from DW_STORE_DIM \n" +
                           "  where STORE_ID = "+storeId + " ) \n";

      //  NAMES FILTER=========================================================//
      String nameFilter = getSearchSqlByFilterName ("JD_MANUF_NAME", pCrit.getSearchName(), pCrit.getSearchNameType());
     //=======================================================================//
      try {
        //conn = getConnection();
        conn = getReportConnection();

        String sql =
            "select  \n " +
            " MANUFACTURER_DIM_ID, \n" +
            " MANUF_id, MANUF_NAME, \n" +
            " JD_MANUF_ID, JD_MANUF_NAME, \n" +
            " JD_MANUF_CITY, JD_MANUF_STATE, \n" +
            " JD_MANUF_ADDRESS, JD_MANUF_STATUS_CD \n" +
            "from DW_MANUFACTURER_DIM where \n" +
            " STORE_DIM_ID = " + storeFilter + " \n" +
            nameFilter +
            " order by JD_MANUF_NAME"  ;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (pCrit.getResultLimit() != QueryRequest.UNLIMITED) {
//          stmt.setMaxRows(2000);
        }
        while (rs.next()) {
          BusEntityData be = new BusEntityData();
          be.setBusEntityId(rs.getInt   ("MANUFACTURER_DIM_ID") );
          be.setShortDesc( (rs.getString("JD_MANUF_NAME") != null) ? rs.getString("JD_MANUF_NAME") : "");
          be.setBusEntityStatusCd((rs.getString("JD_MANUF_STATUS_CD") != null) ? rs.getString("JD_MANUF_STATUS_CD") : "");

          AddressData ad = new AddressData();
          ad.setAddress1((rs.getString("JD_MANUF_ADDRESS") != null) ? rs.getString("JD_MANUF_ADDRESS") : "");
          ad.setCity((rs.getString("JD_MANUF_CITY") != null) ? rs.getString("JD_MANUF_CITY") : "");
          ad.setStateProvinceCd((rs.getString("JD_MANUF_STATE") != null) ? rs.getString("JD_MANUF_STATE") : "");

          ManufacturerData md = new ManufacturerData(be, storeId.intValue(), ad,
              null, null, null, null, null, null, null, null,null, null, null);
          mV.add(md);
        }
        stmt.close();
      }
      catch (Exception exc) {
        exc.printStackTrace();
        throw processException(exc);
      }
      finally {
        closeConnection(conn);
      }

      return mV;
    }
    /**
      * <code>getUserSites</code>  returns a vector of SiteView's
      * meeting the criteria of the parameters.

     * @param pStoreId a <code>int</code> ID value (from Main DB) of Store selected
     * @param pUserId a <code>int</code> ID value (from Main DB) of Reporting User selected
     *               (appUser if user is not selected or
     *                0 - if user assigned to All Accounts( has property RepOA^))
     * @param pNameTempl <code>String</code> NANE filter value
     * @param nameBeginsFl <code>int</code> NANE filter type (QueryRequest.BEGINS or QueryRequest.CONTAINS)
     * @param pCity <code>String</code>  filter value for CITY
     * @param pState <code>String</code>  filter value for STATE
     * @param pAccountIdv <code>IdVector</code>  filter of AccountDimIds (null if no filter)
     * @return a <code>SiteViewVector</code> value

/** ========================================================================================  */

    public  SiteViewVector getUserSites(
        int pStoreId, int pUserId, Integer pSiteDimId, String pNameTempl, int nameBeginsFl,
        String pCity, String pState, IdVector pAccountIdv,
        boolean showInactiveFl, int pResultLimit)
        throws RemoteException {

     Connection conn = null;
     SiteViewVector siteV = new SiteViewVector();

     try {
      String storeDimFilter = "(select STORE_DIM_ID from DW_STORE_DIM \n" +
                              "  where STORE_ID = "+pStoreId + " ) \n";

      String userFilterForAccounts = ""; // if user is not RepOA^ and accounts where not selected
      if (pUserId > 0 && ( pAccountIdv == null || pAccountIdv.size() == 0 )){
        userFilterForAccounts = getUserFilterForAccounts(pUserId);
      }

       String siteNameFilter = getSearchSqlByFilterName ("SITE_NAME", pNameTempl, nameBeginsFl);
       String accountIdsFilter = (pAccountIdv != null && pAccountIdv.size() >0 ) ? "and ACCOUNT_DIM_ID in (" + convertToString (pAccountIdv) + ")" : "";
       String cityFilter = (Utility.isSet(pCity) ) ? " and UPPER(SITE_CITY) LIKE UPPER('" + pCity + "%')" : "";
       String stateFilter = (Utility.isSet(pState) ) ? " and UPPER(SITE_STATE) LIKE UPPER('" + pState + "%')" : "";

 //     conn = getConnection();
        conn = getReportConnection();

        String sql =
          "select  SITE_DIM_ID, \n" +
          " ACCOUNT_DIM_ID, ACCOUNT_ID, \n" +
          " (Select JD_ACCOUNT_NAME from DW_ACCOUNT_DIM a \n" +
          "  where a.ACCOUNT_DIM_ID = s.ACCOUNT_DIM_ID) JD_ACCOUNT_NAME, \n" +
          " SITE_ID,  \n" +
          " SITE_NAME, SITE_STREET_ADDRESS, SITE_CITY, SITE_STATE, SITE_STATUS_CD \n" +
          "from DW_SITE_DIM  s\n" +
          "where \n" +
          "   STORE_DIM_ID = " + storeDimFilter + " \n" +
          userFilterForAccounts +
          accountIdsFilter +
          siteNameFilter +
          cityFilter +
          stateFilter  +
          "order by SITE_NAME ";

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (pResultLimit != QueryRequest.UNLIMITED) {
       stmt.setMaxRows(Constants.MAX_SITES_TO_RETURN);
      }
      while (rs.next()) {
        SiteView sv = SiteView.createValue();
        sv.setId(rs.getInt("SITE_DIM_ID"));
        sv.setName(rs.getString("SITE_NAME"));
        sv.setAccountId(rs.getInt("ACCOUNT_ID"));
        sv.setAccountName(rs.getString("JD_ACCOUNT_NAME"));
        sv.setAddress(rs.getString("SITE_STREET_ADDRESS"));
        sv.setCity(rs.getString("SITE_CITY"));
        sv.setState(rs.getString("SITE_STATE"));
        sv.setStatus(rs.getString("SITE_STATUS_CD"));
        String targetS = null;//rs.getString("SITE_RANK");
        int target=0;
        if(targetS != null){
           try{
               target = Integer.parseInt(targetS);
           }catch(NumberFormatException e){}
        }
        sv.setTargetFacilityRank(target);

        siteV.add(sv);
      }
    stmt.close();

    }
    catch (Exception exc) {
      exc.printStackTrace();
      throw processException(exc);
    } finally {
      closeConnection(conn);
    }
     return siteV;
    }

    /**
      * <code>getAccountsUIByCriteria</code>  returns a vector of AccountUIView's
      * meeting the criteria of the BusEntitySearchCriteria.
      *
      * @param pCrit a <code>BusEntitySearchCriteria</code> value the criteria
      * @return a <code>AccountUIViewVector</code> value
      * @exception SQLException if an error occurs
      */


    public  AccountUIViewVector getAccountsUIByCriteria(  BusEntitySearchCriteria pCrit)
        throws  RemoteException {
      Connection conn = null;
      AccountUIViewVector acctVec = new AccountUIViewVector();
      try {
        // USER FILTER ==========================================================//
        Integer userId = null;
        String userFilterForAccounts = "";
        if (pCrit.getUserIds() != null && pCrit.getUserIds().size() > 0 ){
          userId = (Integer)pCrit.getUserIds().get(0);
          userFilterForAccounts = getUserFilterForAccounts(userId.intValue());
        }
        // STORE FILTER===========================================================//
        Integer storeId = null;
        if (pCrit.getStoreBusEntityIds() != null && pCrit.getStoreBusEntityIds().size() >0 ){
          storeId = (Integer)pCrit.getStoreBusEntityIds().get(0);
        } else {
          throw new RemoteException("Store ID can't be null. ");
        }
        String storeFilter = "(select STORE_DIM_ID from DW_STORE_DIM \n" +
                           "  where STORE_ID = "+storeId + " ) \n";

        String storeFilterForAccounts = getStoreFilterForAccounts(storeId.intValue());

        // ACCOUNT NAMES FILTER==================================================//
        String accountNameFilter = getSearchSqlByFilterName ("JD_ACCOUNT_NAME", pCrit.getSearchName(), pCrit.getSearchNameType());
        String accountIdFilter =  (Utility.isSet(pCrit.getSearchId()) ) ?
                  " and JD_ACCOUNT_ID = " + pCrit.getSearchId() : "";
        //========================================================================//
        conn = getReportConnection();
        String sql =
            "select  \n " +
            " wm_concat(ACCOUNT_DIM_ID) ACCOUNT_DIM_IDS, \n" +
            " ACCOUNT_ID,  \n" +
            " JD_ACCOUNT_NAME, JD_ACCOUNT_ID, \n" +
            " JD_ACCOUNT_CITY, JD_ACCOUNT_STATE, \n" +
            " JD_MARKET, JD_ACCOUNT_STATUS_CD \n" +
            "from DW_ACCOUNT_DIM where \n" +
            "   STORE_DIM_ID = " + storeFilter + " \n" +
/*            " and  account_ID in ( " + storeFilterForAccounts +") \n"
*/
            userFilterForAccounts +
            accountIdFilter +
            accountNameFilter +
            " group by  \n " +
            "    ACCOUNT_ID,  \n" +
            "    JD_ACCOUNT_NAME, JD_ACCOUNT_ID, \n" +
            "    JD_ACCOUNT_CITY, JD_ACCOUNT_STATE,  \n" +
            "    JD_MARKET, JD_ACCOUNT_STATUS_CD \n" +
            " order by JD_ACCOUNT_NAME " ;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (pCrit.getResultLimit() != QueryRequest.UNLIMITED) {
          stmt.setMaxRows(2000);
        }
        int rowId = 0;
        while (rs.next()) {
          AccountUIView aui = AccountUIView.createValue();
          rowId--;
          String accountDimIds = (rs.getString("ACCOUNT_DIM_IDS") != null) ? rs.getString("ACCOUNT_DIM_IDS") : "";

          BusEntityData be = new BusEntityData();
          be.setBusEntityId(rowId);
          be.setShortDesc( (rs.getString("JD_ACCOUNT_NAME") != null) ? rs.getString("JD_ACCOUNT_NAME") : "");
          be.setBusEntityStatusCd( (rs.getString("JD_ACCOUNT_STATUS_CD") != null) ? rs.getString("JD_ACCOUNT_STATUS_CD") : "");

          PropertyData pd = new PropertyData();
          pd.setValue( (rs.getString("JD_MARKET") != null) ? rs.getString("JD_MARKET") : "");

          AddressData ad = new AddressData();
          ad.setAddress1("");
          ad.setCity( (rs.getString("JD_ACCOUNT_CITY") != null) ? rs.getString("JD_ACCOUNT_CITY") : "");
          ad.setStateProvinceCd( (rs.getString("JD_ACCOUNT_STATE") != null) ? rs.getString("JD_ACCOUNT_STATE") : "");

          aui.setAccountDimIds(accountDimIds);
          aui.setBusEntity(be);
          aui.setAccountType(pd);
          aui.setPrimaryAddress(ad);
          acctVec.add(aui);
        }
        stmt.close();
      }
        catch (Exception exc) {
          exc.printStackTrace();
          throw processException(exc);
        } finally {
          closeConnection(conn);
        }
      return acctVec;
    }

     /*
    *@param  pCriteria            List of SearchCriteria objects
    *@return  a set of ItemView objects
    *@exception  RemoteException
    */
    public ItemViewVector searchStoreItems(List pCriteria, boolean pDistInfoFl) throws
     RemoteException {
     Connection con = null;
     try {
       con = getReportConnection();

       Integer storeIdI = new Integer(0);
       String categCond = "";
       String shortDescCond = "";
       String manufCond = "";
       String manufSkuCond = "";
       String distSkuCond = "";


       String storeFilter = "";
       /////////////////////
       //Create a set of filters
       for (Iterator iter = pCriteria.iterator(); iter.hasNext(); ) {
         SearchCriteria sc = (SearchCriteria) iter.next();
         String name = sc.getName();
         Object objValue = sc.getObjectValue();
         String strValue = (objValue instanceof String)? ((String) objValue).trim():"";
         if (SearchCriteria.STORE_ID.equals(name)) {
           storeIdI = (Integer) objValue;
           storeFilter = "select STORE_DIM_ID from DW_STORE_DIM \n" +
                                "  where STORE_ID = "+storeIdI + "  \n";

         } else if (SearchCriteria.CATALOG_CATEGORY.equals(name)) {
           String subStr = " like '%" +
                       strValue.toUpperCase().replaceAll("'", "''") +
                       "%' \n";
           categCond = "    and (UPPER(JD_CATEGORY1) " + subStr + " or  \n" +
                       "         UPPER(JD_CATEGORY2) " + subStr + " or  \n" +
                       "         UPPER(JD_CATEGORY3) " + subStr + ") \n" ;
         }else if (SearchCriteria.PRODUCT_SHORT_DESC.equals(name)) {
           shortDescCond = " and UPPER(JD_ITEM_DESC) like '%" +
                       strValue.toUpperCase().replaceAll("'", "''") +
                       "%' \n";
         } else if (SearchCriteria.MANUFACTURER_SHORT_DESC.equals(name)) {
           manufCond = " and UPPER(JD_MANUF_NAME) like '%" +
                       strValue.toUpperCase().replaceAll("'", "''") +
                       "%' \n";
         } else if (SearchCriteria.MANUFACTURER_SKU_NUMBER.equals(name)) {
           manufSkuCond = " and UPPER(JD_MANUF_SKU) like '%" +
                          strValue.toUpperCase().replaceAll("'", "''") +
                          "%' \n";
          } else if (SearchCriteria.DISTRIBUTOR_SKU_NUMBER.equals(name)) {
            distSkuCond = " and UPPER(JD_DIST_SKU) like '%" +
                           strValue.toUpperCase().replaceAll("'", "''") +
                           "%' \n";
          }

       }

       //Get catalog id --------------------------------------------------------//
  /*       int catalogId = 0;
         String sql = "select distinct STORE_CATALOG_ID from DW_CATEGORY_DIM \n" +
                      " where STORE_DIM_ID IN (" + storeFilter  + ")";
         log(
         "DWOperationBean ---------------------------> sql1: " + sql);

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
  */    //---------------------------------------------------------------------//
      String sql =
          "select                \n"+
          " id.ITEM_DIM_ID,      \n"+
          " JD_ITEM_DESC  ,      \n"+
          " JD_ITEM_PACK ,       \n"+
          " JD_ITEM_UOM ,        \n"+
          " JD_MANUF_SKU,        \n"+
          " JD_MANUF_NAME,        \n"+
          " JD_CATEGORY1         \n";

      String fromSql =
          " from DW_ITEM_DIM id,                               \n " +
          "      DW_CATEGORY_DIM cd,                           \n" +
          "      DW_MANUFACTURER_DIM md                        \n";
      String whereSql =
          " where id.CATEGORY_DIM_ID = cd.CATEGORY_DIM_ID     \n" +
//          "    and cd.STORE_CATALOG_ID = " + catalogId + "    \n" +
          "    and cd.STORE_DIM_ID IN (" + storeFilter  + ")  \n" +
          "    and md.MANUFACTURER_DIM_ID  = id.MANUFACTURER_DIM_ID   \n";

      //---------------------------------------------------------------------//

       if (pDistInfoFl) {
         sql +=
             " , JD_DIST_SKU,                                       \n"+
             " (select JD_DIST_NAME from DW_DISTRIBUTOR_DIM dd      \n"+
             "   where DISTRIBUTOR_DIM_ID  = did.DISTRIBUTOR_DIM_ID \n"+
             " ) JD_DIST_NAME                                       \n";

         fromSql +=
            "    ,DW_ITEM_DISTRIBUTOR did  \n" ;
         whereSql +=
            "    and id.ITEM_DIM_ID   = did.ITEM_DIM_ID (+)         \n" +
            distSkuCond;

       }
        sql +=  fromSql + whereSql ;
        sql +=  categCond + shortDescCond + manufCond + manufSkuCond;
        sql += " order by JD_MANUF_SKU ";

       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       ItemViewVector itemVwV = new ItemViewVector();
       while (rs.next()) {
         ItemView itemVw = ItemView.createValue();
         itemVwV.add(itemVw);
         itemVw.setStoreId(storeIdI.intValue());
//         itemVw.setCatalogId(catalogId);

         itemVw.setItemId(rs.getInt("ITEM_DIM_ID"));
         itemVw.setName(Utility.strNN(rs.getString("JD_ITEM_DESC")));
         itemVw.setSku(Utility.strNN(rs.getString("JD_MANUF_SKU")));
         itemVw.setUom(Utility.strNN(rs.getString("JD_ITEM_UOM")));
         itemVw.setPack(Utility.strNN(rs.getString("JD_ITEM_PACK")));
         itemVw.setCategory(Utility.strNN(rs.getString("JD_CATEGORY1")));
         itemVw.setManufName(Utility.strNN(rs.getString("JD_MANUF_NAME")));
         itemVw.setManufSku(Utility.strNN(rs.getString("JD_MANUF_SKU")));
         if (pDistInfoFl) {
           itemVw.setDistId(0);
           itemVw.setDistName(Utility.strNN(rs.getString("JD_DIST_NAME")));
           itemVw.setDistSku(Utility.strNN(rs.getString("JD_DIST_SKU")));
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

   private String convertToString (IdVector pAccountIdv){
     String stringOfIds = "";
     if ( (pAccountIdv != null && pAccountIdv.size() > 0)) {
       for (int i = 0; i < pAccountIdv.size(); i++) {
         if (stringOfIds.length() > 0) {
           stringOfIds = stringOfIds + "," + (String) pAccountIdv.get(i);
         }
         else {
           stringOfIds = (String) pAccountIdv.get(i);
         }
       }
     }
    return  stringOfIds;
   }

   private String getMainConnName() throws  RemoteException {
     String schema = "";
     try {
       Connection connMain = getConnection();
       schema = connMain.getMetaData().getUserName();
       closeConnection(connMain);
     } catch (Exception exc) {
          exc.printStackTrace();
          throw processException(exc);
     }

     return schema;
   }
/*
   public  String getUserFilterForAccounts(int pUserId) throws  RemoteException {
     String schema = getMainConnName();
     String filterStr =
       " and account_ID in ( " +
       " Select BUS_ENTITY_ID from " + schema + ".CLW_BUS_ENTITY b \n" +
       "     WHERE BUS_ENTITY_TYPE_CD  ='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT +"' \n"+
       "       AND BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE +"' \n" +
       "       AND (exists \n" +
       "       (select * from " + schema + ".CLW_USER_ASSOC ua \n" +
       "        where ua.USER_ID IN (" + pUserId + ") \n" +
       "           and b.BUS_ENTITY_ID=ua.BUS_ENTITY_ID)) \n " +
       ") \n";

     return filterStr;
   }
*/
   public  String getUserFilterForAccounts(int pUserId) throws  RemoteException {
     String schema = getMainConnName();
     String filterStr =
       " and ACCOUNT_DIM_ID in ( \n" +
       " Select ACCOUNT_DIM_ID from DW_ACCOUNT_DIM where  \n" +
       "    account_ID in ( " +
       "     Select BUS_ENTITY_ID from " + schema + ".CLW_BUS_ENTITY b \n" +
       "     WHERE BUS_ENTITY_TYPE_CD  ='" + RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT +"' \n"+
       "       AND BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE +"' \n" +
       "       AND (exists \n" +
       "       (select * from " + schema + ".CLW_USER_ASSOC ua \n" +
       "        where ua.USER_ID IN (" + pUserId + ") \n" +
       "           and b.BUS_ENTITY_ID=ua.BUS_ENTITY_ID)) \n " +
       ")) \n";

     return filterStr;
   }
   private String getStoreFilterForAccounts(int pStoreId) throws  Exception {
      String schema = getMainConnName();
      String filterStr =
       "SELECT DISTINCT BUS_ENTITY1_ID from  " + schema + ".CLW_BUS_ENTITY_ASSOC \n" +
       "  where BUS_ENTITY2_ID IN ("+pStoreId + ") \n";
      return filterStr;
   }
    private String getSearchSqlByFilterName (String filterName, String filterVal, int filterType) {
      String nameFilter = "";
      if (Utility.isSet(filterVal) ) {

        switch (filterType) {
          case QueryRequest.BEGINS:
            nameFilter = " and UPPER(" + filterName + ") LIKE UPPER('" +filterVal.replaceAll("'", "''")+ "%') \n" ;
            break;
          case QueryRequest.CONTAINS:
            nameFilter = " and UPPER(" + filterName + ") LIKE UPPER('%" +filterVal.replaceAll("'", "''")+ "%') \n" ;
            break;
        }
      }
      return nameFilter;
    }

    public List<String> getRepNameStartWith(int pStoreId, String pName, int pMaxRows) throws RemoteException {
        Connection conn = null;
        try {
            conn = getReportConnection();
            return getRepNameStartWith(conn, pStoreId, pName, pMaxRows);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    private List<String> getRepNameStartWith(Connection pConn, int pStoreId, String pName, int pMaxRows) throws Exception {

        List<String> list = new ArrayList<String>();

        String reqName = pName.toUpperCase().replaceAll("'", "''");
        String sql = "SELECT DISTINCT REP_NAME FROM DW_SALES_REP_DIM WHERE STORE_DIM_ID = ? AND UPPER(REP_NAME) LIKE '" + reqName + "%' ORDER BY REP_NAME";

        PreparedStatement pstmt = pConn.prepareStatement(sql);

        pstmt.setInt(1, pStoreId);

        pstmt.setMaxRows(pMaxRows);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }

        return list;
    }

    public List<String> getRegionNameStartWith(int pStoreId, String pName, int pMaxRows) throws RemoteException {
        Connection conn = null;
        try {
            conn = getReportConnection();
            return getRegionNameStartWith(conn, pStoreId, pName, pMaxRows);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    private List<String> getRegionNameStartWith(Connection pConn, int pStoreId, String pName, int pMaxRows) throws Exception {

        List<String> list = new ArrayList<String>();

        String reqName = pName.toUpperCase().replaceAll("'", "''");
        String sql = "SELECT DISTINCT REGION_NAME FROM DW_REGION_DIM WHERE STORE_DIM_ID = ? AND UPPER(REGION_NAME) LIKE '" + reqName + "%' ORDER BY REGION_NAME";

        PreparedStatement pstmt = pConn.prepareStatement(sql);

        pstmt.setInt(1, pStoreId);

        pstmt.setMaxRows(pMaxRows);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }

        return list;
    }


    public List<String> getCategoryNameStartWith(int pStoreId, String pName, int pMaxRows) throws RemoteException {
        Connection conn = null;
        try {
            conn = getReportConnection();
            return getCategoryNameStartWith(conn, pStoreId, pName, pMaxRows);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }


    private List<String> getCategoryNameStartWith(Connection pConn, int pStoreId, String pName, int pMaxRows) throws Exception {

        List<String> list = new ArrayList<String>();

        String reqName = pName.toUpperCase().replaceAll("'", "''");
        String sql = "SELECT DISTINCT JD_CATEGORY1 FROM DW_CATEGORY_DIM WHERE STORE_DIM_ID = ? AND UPPER(JD_CATEGORY1) LIKE '" + reqName + "%' ORDER BY JD_CATEGORY1";

        PreparedStatement pstmt = pConn.prepareStatement(sql);

        pstmt.setInt(1, pStoreId);

        pstmt.setMaxRows(pMaxRows);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString(1));
        }

        return list;
    }
}
