/*
 * OrderRouting.java
 *
 * Created on September 22, 2003, 1:06 PM
 */

package com.cleanwise.service.api.pipeline;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.ShoppingDAO;
import com.cleanwise.service.api.session.DistributorBean;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderRoutingData;
import com.cleanwise.service.api.value.OrderRoutingDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.SiteData;

/**
 *
 * @author  bstevens
 */
public class OrderRouting implements OrderPipeline, PostOrderCapturePipeline {
  private static final Logger log = Logger.getLogger(OrderRouting.class);
  OrderPipelineBaton mBaton;
  Connection con;
  APIAccess factory;

  /**
   *  Routes the order and order items through the pipline.
   *
   *@param  pOrderData        the order data to route.
   *@param  pOrderItems       the order items belonging to the order.
   *@param  pAccountId        the account id. If 0 the pipeline will attempt
   *      to retrieve the account based off the account erp number of the
   *      order.
   *@return                   a message String
   *@throws  RemoteException  Required by EJB 1.0
   */
  private String routeOrderItems(OrderData pOrderData,
                                 OrderItemDataVector pOrderItems,
                                 int pAccountId,
                                 AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {

    int maxNumberOfItems = 10;

    String resp = " Pipeline.routeOrderItems";
    log.debug(resp);

    // Get the product information for the items destined for
    // each distributor involved.
    Hashtable distv = new Hashtable();
    BusEntityDAO busDAO = new BusEntityDAO();

    try {
      PropertyService propBean = factory.getPropertyServiceAPI();
      String maxwv = propBean.checkBusEntityProperty
                     (pAccountId, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_ORDER_MAX_WEIGHT);
      String maxcs = propBean.checkBusEntityProperty
                     (pAccountId, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_ORDER_MAX_CUBE_SIZE);

      if (null == maxwv) {
        maxwv = "0";
      }
      if (null == maxcs) {
        maxcs = "0";
      }
      BigDecimal maxItemWeight = new BigDecimal(maxwv);
      BigDecimal maxItemCubicSize = new BigDecimal(maxcs);

      if (pAccountId == 0) {
        try {
          BusEntityData accte = busDAO.getAccountByErpNum
                                (con, pOrderData.getAccountErpNum());
          pAccountId = accte.getBusEntityId();
        } catch (DataNotFoundException e) {
          throw new RemoteException(e.getMessage());
        }
      }
      // Check to see if this order is for a targeted site,
      // if it is, then do not reroute items.
      // This property is defined in the UI per account.
      // Currently, USPS is the only account which
      // defines targeted sites.
      SiteData sd = busDAO.getSiteData
                    (con, pOrderData.getSiteId());
      if (sd == null) {
        resp += "\n Items not re-routed." +
          " No site information available." +
          "  Site name=" +
          pOrderData.getOrderSiteName() +
          " pOrderData.getSiteId=" +
          pOrderData.getSiteId();

        log.error(resp);

        return resp;
      }

      if (!sd.isAllowOrderRouting()) {
        // facility marked to not allow routing.
        //Do not re-route the items, regardless of order size.
        resp += "\n Items not re-routed." + "  Site name=" + pOrderData.getOrderSiteName() +
          " is marked to not allow order routing.";
        log.debug(resp);
        return resp;
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,
                     pOrderData.getOrderId());

      OrderMetaDataVector omdv = OrderMetaDataAccess.select(con, dbc);
      for (int j = 0; omdv != null && j < omdv.size(); j++) {
        OrderMetaData omd = (OrderMetaData) omdv.get(j);
        if (omd.getName() != null &&
            omd.getName().equals(Order.BYPASS_ORDER_ROUTING) &&
            omd.getValue() != null &&
            omd.getValue().equals("TRUE")) {
	        // This order was placed requesting that
	        // no routing be performed.
	        for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {
	          OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
	          oid.setFreightHandlerId(0);
	          oid.setOrderRoutingId(0);
	          OrderItemDataAccess.update(con, oid);
	        }
	        resp += "\n Items not re-routed." +
	          " This order is marked for no routing.";
	        log.debug(resp);
	        return resp;
        }
      }

      for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {

        OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
        String distnum = oid.getDistErpNum();

        if (distv.containsKey(distnum) == false) {
          distv.put(distnum, distnum);
        }
      }

      // For each distributor found.
      Enumeration e = distv.keys();
      NEXT_DIST:while (e.hasMoreElements()) {

        // Pick out the items for this distributor.
        String thisDistNum = (String) e.nextElement();
        IdVector thisDistItems = new IdVector();
        OrderItemDataVector thisDistOrderItems = new OrderItemDataVector();

        for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {

          OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
          String distnum = oid.getDistErpNum();

          if (thisDistNum.equals(distnum)) {
            thisDistItems.add(new Integer(oid.getItemId()));
            thisDistOrderItems.add(oid);
          }
        }

        // Is this group of items >= the minim order
        // for this distributor?
        // continue if yes
        if (allowOrderToDist(pOrderData, pOrderItems, thisDistNum)) {
          log.info
            ("failed allowOrderToDist check"
             + " thisDistNum=" + thisDistNum
             + " orderid=" + pOrderData.getOrderId());
          continue NEXT_DIST;
        }

        // Check to see if this is a freight free zone for the
        // distributor.
        if (isThisOrderFreightFree(con, pOrderData, thisDistNum)) {
          continue NEXT_DIST;
        }

        // This is a small order.
        // The order is destined to a zip code
        // which charges freight.
        // See if the current distributor can ship there
        // using a freight handler.
        OrderRoutingData sord = shipperForSmallOrder
                                (con, pOrderData, thisDistNum);
        if (sord != null) {
          changeShipperForItems(pOrderData, pOrderItems,
                                thisDistNum, sord);
          continue NEXT_DIST;
        }

        // The only way to get here is that we have a small
        // order, which the current distributor does not
        // want to handle.
        ProductDAO pdao = new ProductDAO(con, thisDistItems);
        ProductDataVector pdv = pdao.getResultVector();

        // Is there an entry in the order_routing table
        // for the 1st 3 digits of the zip code?
        // continue if no
        OrderRoutingData frd = getNewOrderDistributor(pOrderData, pAccountId);

        //If the resulting order route is configured for
        //skipping routing for this zipcode: which we know
        //because the distributor and contract id are 0 or if
        //there is no routing set up for the given zipcode
        //return
        if (frd == null || frd.getDistributorId() == 0
            || frd.getContractId() == 0) {
          if (frd == null) {
            log.info("Not Routing Order, Nothing Configured For Order: " + pOrderData.getOrderNum());
          } else {
            log.info("Not Routing Order, Found Hard Skip For Zip: " + frd.getZip());
          }
          return null;
        } else {
          log.info("routing order (id): " + pOrderData.getOrderId() + " using route: " + frd);
        }

        boolean usePrimaryDist = true;

        // Any item a Hazmat material?
        boolean
          orderHasHazmat = hasHazmat(pdv);

        // Any item weight more than configured value?
        boolean
          orderHasHeavyItem = hasHeavyItem(pdv, maxItemWeight);

        // Any item larger than configured value?
        boolean
          orderHasOversizeItem = hasOversizeItem(pdv, maxItemCubicSize);

        boolean
          orderHasTooManyUOMs = hasTooManyUOMs(thisDistOrderItems, maxNumberOfItems);

        log.debug("PipelineBean, thisDistNum =" + thisDistNum +
                 "\n\t ==== orderHasHazmat=" + orderHasHazmat +
                 "\n\t ==== orderHasHeavyItem=" + orderHasHeavyItem +
                 "\n\t ==== orderHasOversizeItem=" + orderHasOversizeItem +
                 "\n\t ==== orderHasTooManyUOMs=" + orderHasTooManyUOMs
                 + " thisDistNum=" + thisDistNum
                 + " orderid=" + pOrderData.getOrderId());

        if (orderHasHazmat || orderHasHeavyItem
            || orderHasOversizeItem || orderHasTooManyUOMs) {
          usePrimaryDist = false;
        } else {
          usePrimaryDist = checkAllItemsForRouting(pOrderData, pOrderItems, thisDistNum, frd);
        }
        // Route the items to the distributor and the
        // freight handler configured for this zip code.
        // Done with the for loop.
        resp += "\n re-routing items, dist erp num = " + thisDistNum +
          " new routing data: " + frd.toString() +
          " \n usePrimaryDist=" + usePrimaryDist;
        changeDistForItems(pOrderData, pOrderItems, thisDistNum, frd,
                           usePrimaryDist,
                           orderHasHazmat,
                           orderHasHeavyItem,
                           orderHasOversizeItem,
                           pCategToCostCenterView);
        log.debug("routeOrderItems, resp=" + resp);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error("routeOrderItems, error: " + e);

      return e.getMessage();
    }

    return resp;
  }

  /**
   *  Gets the newOrderDistributor attribute of the PipelineBean object
   *
   *@param  pOrderData  Description of the Parameter
   *@param  pAccountId  Description of the Parameter
   *@return             The newOrderDistributor value
   */
  private OrderRoutingData getNewOrderDistributor(OrderData pOrderData, int pAccountId) {
    log.debug("getNewOrderDistributor");

    // Get the zip code from the ship to address for the order.

    try {
      String zip = getOrderShipToZip(con, pOrderData);
      OrderRoutingData ORD = getOrderRoutingDataForPostalCode(zip, pAccountId, con);
      log.debug("getNewOrderDistributor, ORD=" + ORD);
      return ORD;
    } catch (Exception e) {
      e.printStackTrace();
      log.error("getNewOrderDistributor, error: " + e);
    }
    return null;
  }

  private String getOrderShipToZip(Connection pCon, OrderData pOrderData) throws Exception {
    String addrTypeCds = "'" +
                         RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING +
                         "', '" +
                         RefCodeNames.ADDRESS_TYPE_CD.SHIPPING +
                         "'";
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderData.getOrderId());
    dbc.addOneOf(OrderAddressDataAccess.ADDRESS_TYPE_CD, addrTypeCds);
    dbc.addOrderBy(OrderAddressDataAccess.ADDRESS_TYPE_CD);

    OrderAddressDataVector oadv = OrderAddressDataAccess.select(pCon, dbc);

    if (oadv == null) {
      return null;
    }

    OrderAddressData oad = (OrderAddressData) oadv.get(0);
    return oad.getPostalCode();
  }

  private String kUserTag = "Account Order Routing";


  /**
   *  Description of the Method
   *
   *@param  pOrderData    Description of the Parameter
   *@param  pOrderItems   Description of the Parameter
   *@param  pDistErpNum   Description of the Parameter
   *@param  pRoutingData  Description of the Parameter
   *@return               Description of the Return Value
   */
  private boolean checkAllItemsForRouting(OrderData pOrderData,
                                          OrderItemDataVector pOrderItems,
                                          String pDistErpNum,
                                          OrderRoutingData pRoutingData
    ) {

    log.debug("checkAllItemsForRouting");

    try {

      Statement stmt = con.createStatement();
      DistributorBean distSvc = new DistributorBean();
      int newDistId = 0;
      int newContractId = 0;

      newDistId = pRoutingData.getDistributorId();
      newContractId = pRoutingData.getContractId();

      DistributorData d = distSvc.getDistributor(newDistId);

      for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {

        OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
        log.debug("re-routing oid: " + oid);

        String distnum = oid.getDistErpNum();

        String lognote = "";

        if (pDistErpNum.equals(distnum)) {

          int thisItemId = oid.getItemId();
          ProductDAO.DistItemInfo distInfo = ProductDAO.getDistInfo
                                             (con, thisItemId,
                                              d.getBusEntity().getBusEntityId(),
                                              newContractId);

          if (distInfo.mSku == null) {
            lognote = "\nOrder item info:\n" + oid.toString();
            lognote += "\n No Distributor SKU defined";
            // At least one item is not handled
            log.info("checkAllItemsForRouting: " + lognote +
                    " returning FALSE");
            return false;
          }

          if (distInfo.mUOM == null) {
            lognote = "\nOrder item info:\n" + oid.toString();
            lognote += "\n No Distributor UOM defined";
            // At least one item is not handled
            log.info("checkAllItemsForRouting: " + lognote +
                    " returning FALSE");
            return false;
          }

          if (distInfo.mDistCost == null) {
            lognote = "\nOrder item info:\n" + oid.toString();
            lognote += "\n No Distributor cost in contract id=" +
              newContractId;
            // At least one item is not handled in the
            // freight handler contract.
            log.info("checkAllItemsForRouting: " + lognote +
                    " returning FALSE");
            return false;
          }
        }
      }
    } catch (Exception e) {
      log.error("changeDistForItems, error: " + e);
    }

    // All items are on contract.
    log.debug("checkAllItemsForRouting: " +
             " returning TRUE");
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  pOrderData         Description of the Parameter
   *@param  pOrderItems        Description of the Parameter
   *@param  pDistErpNum        Description of the Parameter
   *@param  pRoutingData       Description of the Parameter
   *@param  usePrimaryDist     Description of the Parameter
   *@param  orderHasHazmat     Description of the Parameter
   *@param  orderHasHeavyItem  Description of the Parameter
   *@param  orderHasOversizeItem  Description of the Parameter
   *@return                    Description of the Return Value
   */
  boolean mOrderRoutingFailed = false;

  private boolean changeDistForItems(OrderData pOrderData,
                                     OrderItemDataVector pOrderItems,
                                     String pDistErpNum,
                                     OrderRoutingData pRoutingData,
                                     boolean usePrimaryDist,
                                     boolean orderHasHazmat,
                                     boolean orderHasHeavyItem,
                                     boolean orderHasOversizeItem,
                                     AccCategoryToCostCenterView pCategToCostCenterView) {

    log.debug("changeDistForItems");

    try {
      UpdateDistributorItemInfo updateDistributorItemInfoImpl = new UpdateDistributorItemInfo();
      DistributorBean distSvc = new DistributorBean();
      int newDistId = 0;
      int newContractId = 0;
      int fhid = -1;

      if (orderHasHazmat
          || orderHasHeavyItem
          || orderHasOversizeItem
        ) {
        // 1 or more items cannot be handled
        // by parcel direct.
        fhid = pRoutingData.getLtlFreightHandlerId();
        newDistId = pRoutingData.getFinalDistributorId();
        newContractId = pRoutingData.getFinalContractId();
      } else if (usePrimaryDist) {
        // all items are ok and they are all
        // on the primary contract.
        fhid = pRoutingData.getFreightHandlerId();
        newDistId = pRoutingData.getDistributorId();
        newContractId = pRoutingData.getContractId();
      } else {
        // all items are ok, but 1 or more items
        // not found in the primary contract.
        fhid = pRoutingData.getFinalFreightHandlerId();
        newDistId = pRoutingData.getFinalDistributorId();
        newContractId = pRoutingData.getFinalContractId();
      }

      DistributorData d = distSvc.getDistributor(newDistId);
      log.debug("=== NEW dist id=" + newDistId + " freight handler id=" + fhid);

      // Assume routing will succeed.
      mOrderRoutingFailed = false;

      for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {

        OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
        log.debug("re-routing oid: " + oid);
        OrderItemData oldoid = OrderItemData.createValue();
        oldoid.setDistErpNum(oid.getDistErpNum());
        oldoid.setDistItemUom(oid.getDistItemUom());
        oldoid.setDistItemPack(oid.getDistItemPack());
        oldoid.setDistItemCost(oid.getDistItemCost());

        String distnum = oid.getDistErpNum();
        log.debug(" === distnum=" + distnum +
                 " \npDistErpNum=" + pDistErpNum);

        String lognote;
        String note = "{ CW ItemId=" + oid.getItemId() +
                      ", ItemSKU=" + oid.getItemSkuNum() + " } ";

        if (pDistErpNum.equals(distnum)) {
          lognote = "\nOrder item info:\n" + oid.toString();

          int thisItemId = oid.getItemId();

            ProductDAO.DistItemInfo distInfo;
            String productBundleValue = ShoppingDAO.getProductBundleValue(con, pOrderData.getSiteId());

            if (Utility.isSet(productBundleValue)) {

               PropertyUtil pu = new PropertyUtil(con);
                String storeType = pu.fetchValueIgnoreMissing(0, pOrderData.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);

                ContractData contract = APIAccess.getAPIAccess().getContractAPI().getContract(newContractId);

                ProductDAO productDAO = new ProductDAO(con, thisItemId);
                productDAO.updateCatalogInfo(con, contract.getCatalogId(), pOrderData.getSiteId(), pCategToCostCenterView);

                distInfo = ProductDAO.getDistInfo(con,
                        storeType,
                        pOrderData.getSiteId(),
                        newContractId,
                        (ProductData) productDAO.getResultVector().get(0));

               oid.setFreightHandlerId(fhid);
               oid.setOrderRoutingId(pRoutingData.getOrderRoutingId());

               updateDistributorItemInfoImpl.process(con,
                        pOrderData.getStoreId(),
                        pOrderData.getSiteId(),
                        oid,
                        null,
                        newContractId,
                        d.getBusEntity().getBusEntityId(),
                        mBaton,
                        true);

            } else {

                distInfo =
                        ProductDAO.getDistInfo(
                                con, thisItemId,
                                d.getBusEntity().getBusEntityId(),
                                newContractId);

                // Update the erp number, and distributor
                // data for the items to be re-routed.
                //oid.setDistErpNum(d.getBusEntity().getErpNum());
                //oid.setDistItemShortDesc("Reroute from:" + pDistErpNum + " to:" + d.getBusEntity().getErpNum());
                oid.setFreightHandlerId(fhid);
                oid.setOrderRoutingId(pRoutingData.getOrderRoutingId());
                //oid.setDistItemSkuNum(distInfo.mSku);
                //oid.setDistItemUom(distInfo.mUOM);
                //oid.setDistItemPack(distInfo.mPack);
                //oid.setDistItemCost(distInfo.mDistCost);
                updateDistributorItemInfoImpl.process(oid, distInfo.mContractId, d.getBusEntity().getBusEntityId(), mBaton, con);
            }

          OrderItemDataAccess.update(con, oid);

          note += "\n Modified distributor info, " +
            "\n  { ERP num, old=" + oldoid.getDistErpNum() +
            " new=" + d.getBusEntity().getErpNum() +
            " }\n  { UOM, old=" + oldoid.getDistItemUom() +
            " new=" + distInfo.mUOM +
            " }\n  { Pack, old=" + oldoid.getDistItemPack() +
            " new=" + distInfo.mPack +
            " }\n  { Cost, old=" + oldoid.getDistItemCost() +
            " new=" + distInfo.mDistCost +
            " }\n  { Contract Id, old=" +
            pOrderData.getContractId() +
            " new=" + distInfo.mContractId + " } ";

          if (!distInfo.isInfoComplete()) {
            // Routing for this order has failed.
            note += distInfo.getErrorMsg();
            mOrderRoutingFailed = true;
          }

          if (fhid > 0) {
            // Get the freight info.
            BusEntityData fh = BusEntityDataAccess.select(con, fhid);
            note += "\n Using freight handler: " + fh.getShortDesc();

          }

          if (orderHasHazmat) {
            note += "\n Order has at least 1 HAZMAT item.";
          }
          if (orderHasHeavyItem) {
            note += "\n Order has at least 1 HEAVY item.";
          }
          if (orderHasOversizeItem) {
            note += "\n Order has at least 1 OVERSIZE item.";
          }

          OrderDAO.addOrderItemNote(con, oid.getOrderId(),
                                    oid.getOrderItemId(), note, kUserTag);
          lognote += "\n" + note;
          log.debug(lognote);

        }
      }

    } catch (Exception e) {
      log.error("changeDistForItems, error: " + e);
    }

    return true;
  }


  /**
   *  loops through looking for an exact match for this zip, i.e. for zip
   *  02174 looks for: 02174 0217 021 02 0 thus allowing for arbitary
   *  precision in the routing table.
   *
   *@param  pPostalCode                Description of the Parameter
   *@param  pAccountId                 Description of the Parameter
   *@param  pCon                       Description of the Parameter
   *@return                            The orderRoutingDataForPostalCode value
   *@exception  java.sql.SQLException  Description of the Exception
   */
  public OrderRoutingData getOrderRoutingDataForPostalCode(String pPostalCode, int pAccountId, Connection pCon) throws java.
    sql.SQLException {
    int i = pPostalCode.length();
    OrderRoutingData ORD = null;
    while (pPostalCode.length() > 1 && ORD == null) {
      pPostalCode = pPostalCode.substring(0, i);
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(com.cleanwise.service.api.dao.OrderRoutingDataAccess.ZIP, pPostalCode);
      dbc.addEqualTo(com.cleanwise.service.api.dao.OrderRoutingDataAccess.ACCOUNT_ID, pAccountId);
      OrderRoutingDataVector ordv =
        com.cleanwise.service.api.dao.OrderRoutingDataAccess.select(pCon, dbc);
      if (ordv != null && ordv.size() > 0) {
        ORD = (OrderRoutingData) ordv.get(0);
      }
      i--;
    }
    return ORD;
  }


  private OrderRoutingData shipperForSmallOrder(Connection pCon,
                                                OrderData pOrderData,
                                                String pDistErpNum) {

    try {

      DistributorBean distSvc = new DistributorBean();
      DistributorData d = distSvc.getDistributorByErpNum(pDistErpNum);
      String zip = getOrderShipToZip(pCon, pOrderData);
      int dist_id = d.getBusEntity().getBusEntityId();
      OrderRoutingData ord = distSvc.getPrefferedFreightHandler
                             (dist_id, zip);
      return ord;
    } catch (Exception e) {
      log.error("shipperForSmallOrder, error: " + e);
    }

    log.debug("shipperForSmallOrder, return null");

    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  pdv  Description of the Parameter
   *@return      Description of the Return Value
   */
  private boolean hasHazmat(ProductDataVector pdv) {

    for (int idx = 0; idx < pdv.size(); idx++) {

      ProductData pd = (ProductData) pdv.get(idx);

      if (pd.isHazmat()) {

        return true;
      }
    }

    return false;
  }


  /**
   *  Description of the Method
   *
   *@param  pdv   Description of the Parameter
   *@param  pMax  Description of the Parameter
   *@return       Description of the Return Value
   */
  private boolean hasHeavyItem(ProductDataVector pdv, BigDecimal pMax) {

    for (int idx = 0; idx < pdv.size(); idx++) {

      ProductData pd = (ProductData) pdv.get(idx);

      try {

        if (null == pd.getShipWeight() ||
            pd.getShipWeight().length() == 0) {

          continue;
        }

        BigDecimal itemweight = new BigDecimal(pd.getShipWeight());

        if (itemweight.compareTo(pMax) > 0) {

          return true;
        }
      } catch (Exception e) {
        log.error("hasHeavyItem, " +
                 "error getting item weight for pd=" + pd +
                 " \n error: " + e);
      }
    }

    return false;
  }


  /**
   *  Description of the Method
   *
   *@param  pdv   Description of the Parameter
   *@param  pMax  Description of the Parameter
   *@return       Description of the Return Value
   */
  private boolean hasOversizeItem(ProductDataVector pdv, BigDecimal pMax) {

    log.debug("--+-- pdv.size()=" + pdv.size() + " pMax=" + pMax);
    for (int idx = 0; idx < pdv.size(); idx++) {
      ProductData pd = (ProductData) pdv.get(idx);
      try {

        log.debug("--+-- pdv.size()=" + pdv.size() + " pMax=" + pMax +
                 " pd.getCubeSize() =" + pd.getCubeSize());
        if (null == pd.getCubeSize() ||
            pd.getCubeSize().length() == 0) {
          continue;
        }
        BigDecimal cubs = new BigDecimal(pd.getCubeSize());
        log.debug(" Max cube config=" + pMax +
                 " PD.cube=" + cubs);
        if (cubs.compareTo(pMax) > 0) {
          return true;
        }
      } catch (Exception e) {
        log.error("hasOversizeItem, " + "error for pd=" + pd + " \n error: " + e);
      }
    }
    return false;
  }


  private boolean hasTooManyUOMs(OrderItemDataVector oidv, int maxNumberOfItems) {
    int numberOfUoms = 0;
    Iterator it = oidv.iterator();
    while (it.hasNext()) {
      OrderItemData oi = (OrderItemData) it.next();
      numberOfUoms += oi.getTotalQuantityOrdered();
    }
    if (numberOfUoms > maxNumberOfItems) {
      return true;
    }
    return false;
  }

  /**
   *  Description of the Method
   *
   *@param  pOrderData   Description of the Parameter
   *@param  pOrderItems  Description of the Parameter
   *@param  pDistErpNum  Description of the Parameter
   *@return              Description of the Return Value
   */
  private boolean allowOrderToDist(OrderData pOrderData,
                                   OrderItemDataVector pOrderItems,
                                   String pDistErpNum) {

    BigDecimal sumItems = new BigDecimal(0);

    // Add up the line items for this distributor.
    for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {

      OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
      String distnum = oid.getDistErpNum();

      if (pDistErpNum.equals(distnum)) {

        BigDecimal qty = new BigDecimal(String.valueOf(oid.getTotalQuantityOrdered()));
        BigDecimal distcost = oid.getDistItemCost();
        sumItems = sumItems.add(distcost.multiply(qty));
      }
    }

    try {

      DistributorBean distSvc = new DistributorBean();
      DistributorData d = distSvc.getDistributorByErpNum(pDistErpNum);
      BigDecimal distMinOrderVal = d.getMinimumOrderAmount();
      log.debug("allowOrderToDist, distMinOrderVal=" + distMinOrderVal +
               " sumItems=" + sumItems);

      if (distMinOrderVal == null || distMinOrderVal.compareTo(sumItems) <= 0) {
        log.debug("allowOrderToDist, return true");

        return true;
      }
    } catch (Exception e) {
      log.error("allowOrderToDist, error: " + e);
    }

    log.debug("allowOrderToDist, return false");

    return false;
  }

  private boolean isThisOrderFreightFree(Connection pCon,
                                         OrderData pOrderData,
                                         String pDistErpNum) {

    try {

      DistributorBean distSvc = new DistributorBean();
      DistributorData d = distSvc.getDistributorByErpNum(pDistErpNum);
      String zip = getOrderShipToZip(pCon, pOrderData);
      int dist_id = d.getBusEntity().getBusEntityId();
      boolean ret = distSvc.isFreightFreeZone(dist_id, zip);
      log.debug("isThisOrderFreightFree, dist_id=" + dist_id +
               " zip=" + zip + " ret=" + ret);

      return ret;
    } catch (Exception e) {
      log.error("isThisOrderFreightFree, error: " + e);
    }

    log.debug("isThisOrderFreightFree, return false");

    return false;
  }


  private boolean changeShipperForItems(OrderData pOrderData,
                                        OrderItemDataVector pOrderItems,
                                        String pDistErpNum,
                                        OrderRoutingData pRoutingData) {

    log.debug("changeShipperForItems");

    try {

      DistributorBean distSvc = new DistributorBean();
      int fhid = pRoutingData.getFreightHandlerId(),
                 orid = pRoutingData.getOrderRoutingId();

      log.debug("=== NEW freight handler id=" + fhid);
      if (fhid <= 0) {
        log.debug("===== no freight handler defined fhid=" + fhid);
        return false;
      }
      for (int distidx = 0; distidx < pOrderItems.size(); distidx++) {

        OrderItemData oid = (OrderItemData) pOrderItems.get(distidx);
        log.debug("reset shipper oid: " + oid);

        String distnum = oid.getDistErpNum();
        log.debug(" === distnum=" + distnum +
                 " \npDistErpNum=" + pDistErpNum);

        String lognote;
        String note = "{ CW ItemId=" + oid.getItemId() +
                      ", ItemSKU=" + oid.getItemSkuNum() + " } ";

        if (pDistErpNum.equals(distnum)) {
          lognote = "\nOrder item info:\n" + oid.toString();

          // Update the erp number, and distributor
          // data for the items to be re-routed.
          oid.setDistItemShortDesc("Use freight handler id=" + fhid);
          oid.setOrderRoutingId(pRoutingData.getOrderRoutingId());
          oid.setFreightHandlerId(pRoutingData.getFreightHandlerId());

          OrderItemDataAccess.update(con, oid);
          note += "\n Modified distributor shipper info. ";
          if (fhid > 0) {
            // Get the freight info.
            BusEntityData fh = BusEntityDataAccess.select(con, fhid);
            note += "\n Using freight handler: " + fh.getShortDesc();

          }

          OrderDAO.addOrderItemNote(con, oid.getOrderId(),
                                    oid.getOrderItemId(), note,
                                    kUserTag);
          lognote += "\n" + note;
          log.debug(lognote);

        }
      }

    } catch (Exception e) {
      log.error("changeShipperForItems, error: " + e);
    }

    return true;
  }

  public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                    OrderPipelineActor pActor,
                                    Connection pCon,
                                    APIAccess pFactory) throws PipelineException {
    mBaton = pBaton;
    OrderData orderD = pBaton.getOrderData();
    OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
    AccCategoryToCostCenterView categToCostCenterView= pBaton.getCategToCCView();
    process(orderD, orderItemDV, orderD.getAccountId(), pCon, pFactory, categToCostCenterView);

    try {
      orderD = OrderDataAccess.select(pCon, orderD.getOrderId());

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, orderD.getOrderId());
      dbc.addOrderBy(OrderItemDataAccess.ORDER_ITEM_ID);
      orderItemDV = OrderItemDataAccess.select(pCon, dbc);

      pBaton.setOrderData(orderD);
      pBaton.setOrderItemDataVector(orderItemDV);
      if (mOrderRoutingFailed) {
        pBaton.setWhatNext(OrderPipelineBaton.STOP);
        String finalOrderStatus =
          RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW;
        pBaton.setOrderStatus(finalOrderStatus);

        OrderDAO.updateOrderStatusCd(pCon, orderD.getOrderId(),
                                     finalOrderStatus);
        pBaton.getOrderData().setOrderStatusCd(finalOrderStatus);

        pBaton.addError(pCon, "Order Routing Failed",
                        RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,
                        0, 0,
                        "pipeline.message.noDistributorInfo");
        log.debug("mOrderRoutingFailed=" + mOrderRoutingFailed + " pBaton=" + pBaton);
      } else {
        pBaton.setOrderStatus(orderD.getOrderStatusCd());

        if (pBaton.hasErrors()) {
          pBaton.setWhatNext(OrderPipelineBaton.STOP);
        } else {
          pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        }
      }

      return pBaton;

    } catch (Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
  }

  public void process(OrderData pOrderData,
                      OrderItemDataVector pOrderItemDataVector,
                      int pAccountId,
                      java.sql.Connection pCon,
                      APIAccess pFactory,
                      AccCategoryToCostCenterView pCategToCostCenterView) throws com.cleanwise.service.api.util.PipelineException {
    try {
      con = pCon;
      factory = pFactory;
      routeOrderItems(pOrderData, pOrderItemDataVector, pAccountId, pCategToCostCenterView);
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new PipelineException(e.getMessage());
    }
  }

}
