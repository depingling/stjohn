package com.cleanwise.service.api.session;


import javax.ejb.*;
import java.rmi.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.math.BigDecimal;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;

import org.apache.log4j.Logger;


/*
 *The <code>PurchaseOrderBean</code>
 *@see com.cleanwise.service.api.session.PurchaseOrder
 * PurchaseOrderBean.java
 *
 * Created on June 3, 2002
 * @author  bstevens
 */
public class PurchaseOrderBean extends UtilityServicesAPI {
  private static final String SERVICE_TYPE_CD = "02"; //requested service type code for USPS deliver confirm
  
  private static final Logger log = Logger.getLogger(PurchaseOrderBean.class);

  /**
   *  Default <code>ejbCreate</code> method.
   *
   *@exception  CreateException  if an error occurs
   *@exception  RemoteException  if an error occurs
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /** Creates a new instance of PurchaseOrderBean */
  public PurchaseOrderBean() {
  }


  /**
   * Describe <code>addPurchaseOrder</code> method here.
   *
   * @param pPurchaseOrderData a <code>PurchaseOrderData</code> value
   * @return a <code>PurchaseOrderData</code> value
   * @exception RemoteException if an error occurs
   */
  public PurchaseOrderData addPurchaseOrder(PurchaseOrderData pPurchaseOrderData) throws RemoteException {
    return updatePurchaseOrder(pPurchaseOrderData);
  }

  /**
   * Updates the total and sub total fields from a purchase order with the data from the order
   * items table.
   *
   * @param pPurchaseOrderId the purchase order id to update
   * @exception RemoteException if an error occurs
   */
  public void updatePurchaseOrderFromOrderItems(int pPurchaseOrderId) throws RemoteException {
    if (pPurchaseOrderId == 0) {
      return;
    }
    Connection con = null;
    try {
      con = getConnection();
      //get the stjohn po to update
      PurchaseOrderData sjpo = PurchaseOrderDataAccess.select(con, pPurchaseOrderId);

      ResultSet rs;
      String sql = "SELECT SUM(total_quantity_ordered * dist_item_cost) AS LINE_TOTAL " +
                   "FROM clw_order_item WHERE purchase_order_id = " + pPurchaseOrderId;
      rs = con.createStatement().executeQuery(sql);

      //now get the po data
      if (rs == null || !rs.next()) {
        throw new RemoteException("No purchase Order Items for poId: " + pPurchaseOrderId);
      }

      BigDecimal subTot = rs.getBigDecimal("LINE_TOTAL");
      //new total is old Misc Charges plus new subTotal
      BigDecimal tot = sjpo.getPurchaseOrderTotal().subtract(sjpo.getLineItemTotal()).add(subTot);
      rs.close();
      sjpo.setPurchaseOrderTotal(tot);
      sjpo.setLineItemTotal(subTot);
      PurchaseOrderDataAccess.update(con, sjpo);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

  /**
   * Updates the PurchaseOrder information values to be used by the request.
   * @param pPurchaseOrderData  the PurchaseOrderData .
   * @return a <code>PurchaseOrderData</code> value
   * @throws            RemoteException Required by EJB 1.0
   */
  public PurchaseOrderData updatePurchaseOrder(PurchaseOrderData pPurchaseOrderData) throws RemoteException {
    logDebug("in updatePurchaseOrder method");
    Connection conn = null;
    try {
      conn = getConnection();

      logDebug("dirty flag: " + pPurchaseOrderData.isDirty());
      if (pPurchaseOrderData.isDirty()) {
        if (pPurchaseOrderData.getPurchaseOrderId() == 0) {
          logDebug("inserting new po: " + pPurchaseOrderData);
          pPurchaseOrderData = PurchaseOrderDataAccess.insert(conn, pPurchaseOrderData);
        } else {
          logDebug("updating po: " + pPurchaseOrderData);
          PurchaseOrderDataAccess.update(conn, pPurchaseOrderData);
        }
      }
      int poId = pPurchaseOrderData.getPurchaseOrderId();

    } catch (Exception e) {
      throw new RemoteException("updatePurchaseOrder: " + e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return pPurchaseOrderData;
  }

  private static final Integer USED_ORDER_TAB = new Integer(1);
  private static final Integer USED_ORDER_AUX_TAB = new Integer(2);
  private static final Integer USED_PO_TAB = new Integer(3);
  private static final Integer USED_INVOICE_D_TAB = new Integer(4);
  private static final Integer USED_ORDER_ITEM_D_TAB = new Integer(5);


    //parses out the PurchaseOrderStatusCriteriaData into a SQL statement
    private String getSqlStatementFromSearchCriteria(PurchaseOrderStatusCriteriaData searchCrit, Connection pCon) throws
            APIServiceAccessException, SQLException, javax.naming.NamingException, RemoteException {
        //this will be converted to a string to form our where clause in the end
        DBCriteria crit = new DBCriteria();

        boolean distInvoiceSearch = searchCrit.isInvoiceSearch();
        HashSet usedTables = new HashSet();

        //lets first get the orders that we are talking about based on the
        //criteria that is order specific, we will then move on to POs that
        //belong to those orders.
        if (Utility.isSet(searchCrit.getErpOrderNum())) {
            crit.addEqualTo(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ERP_ORDER_NUM, searchCrit.getErpOrderNum());
            usedTables.add(USED_ORDER_TAB);
        }
        
        //add the site criteria
        if (Utility.isSet(searchCrit.getSiteId())) {
            if (searchCrit.getSiteIdVector() == null) {
                searchCrit.setSiteIdVector(new IdVector());
            }
            searchCrit.getSiteIdVector().add(new Integer(searchCrit.getSiteId()));
        }
        if (searchCrit.getSiteIdVector() != null && !searchCrit.getSiteIdVector().isEmpty()) {
            crit.addOneOf(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.SITE_ID, searchCrit.getSiteIdVector());
            usedTables.add(USED_ORDER_TAB); //XXX could use the site name to some level if the distInvoiceSearch flag is set
        }

        //sale type code
        if (Utility.isSet(searchCrit.getSaleTypeCd())) {
            crit.addEqualTo(OrderItemDataAccess.CLW_ORDER_ITEM + "." + OrderItemDataAccess.SALE_TYPE_CD, searchCrit.getSaleTypeCd());
            usedTables.add(USED_ORDER_ITEM_D_TAB);
        }

        //add the account criteria
        if (Utility.isSet(searchCrit.getAccountId())) {
            crit.addEqualTo(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ACCOUNT_ID, searchCrit.getAccountId());
            usedTables.add(USED_ORDER_TAB); //XXX maybe should use some other crit if distInvoiceSearch flag set (maybe load addtl info with invoice)
        }
        IdVector accountIdVector = searchCrit.getAccountIdVector();
        if (accountIdVector != null && accountIdVector.size() > 0) {
            crit.addOneOf(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ACCOUNT_ID, accountIdVector);
            usedTables.add(USED_ORDER_TAB); //XXX maybe should use some other crit if distInvoiceSearch flag set (maybe load addtl info with invoice)
        }
        //add the web order number criteria
        if (Utility.isSet(searchCrit.getWebOrderConfirmationNum())) {
            crit.addEqualTo(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_NUM, searchCrit.getWebOrderConfirmationNum());
            usedTables.add(USED_ORDER_TAB);
        }
        //add customer PO number criteria
        if (Utility.isSet(searchCrit.getOrderRequestPoNum())) {
            crit.addLikeIgnoreCase(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.REQUEST_PO_NUM,
                    "%" + searchCrit.getOrderRequestPoNum() + "%");
            usedTables.add(USED_ORDER_TAB);
        }
        //add any site property conditions
        //boolean usingOrderPropertyTable = false;
        boolean usingSiteDataPropertyTable = false;
        if (Utility.isSet(searchCrit.getSiteData())) {
            //there are 2 join conditions, one for the prop type, one for the value
            crit.addEqualTo("SITE_DATA" + "." + PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
            crit.addLikeIgnoreCase("SITE_DATA" + "." + PropertyDataAccess.CLW_VALUE, searchCrit.getSiteData());
            usingSiteDataPropertyTable = true;
            usedTables.add(USED_ORDER_AUX_TAB);
        }

        boolean usingTargetFacilityTable = false;
        if (searchCrit.getTargetFacilityRank() != null) {
            //there are 2 join conditions, one for the prop type, one for the value
            crit.addEqualTo("TARGET_FACILITY" + "." + PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK);
            crit.addEqualTo("TARGET_FACILITY" + "." + PropertyDataAccess.CLW_VALUE, searchCrit.getTargetFacilityRank().toString());
            usingTargetFacilityTable = true;
            usedTables.add(USED_ORDER_AUX_TAB);
        }

        //now add any purchase order criteria we have.
        if (searchCrit.getDistributorIds() != null && searchCrit.getDistributorIds().size() > 0) {
            if (distInvoiceSearch) {
                crit.addOneOf(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.BUS_ENTITY_ID,
                        searchCrit.getDistributorIds());
                usedTables.add(USED_INVOICE_D_TAB);
            } else {
                ArrayList distErpNums = new ArrayList();
                Iterator it = searchCrit.getDistributorIds().iterator();
                while (it.hasNext()) {
                    Integer distId = (Integer) it.next();

                    //find the distributor erp number
                    BusEntityData ddata = null;
                    try {
                        ddata = BusEntityDataAccess.select(pCon, distId.intValue());
                        if (ddata.getErpNum() == null) {
                            logError("Distributor id specified has null erp number: " + distId);
                        } else {
                            distErpNums.add(ddata.getErpNum());
                            logDebug("Restricting results to dist erp number: " + ddata.getErpNum());
                            IdVector distaccountIdVector =
                                    getAccountsForDistUser(pCon, searchCrit.getUserId());

                            if (distaccountIdVector != null && distaccountIdVector.size() > 0) {
                                crit.addOneOf(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ACCOUNT_ID, distaccountIdVector);
                                usedTables.add(USED_ORDER_TAB);
                            }

                        }
                    } catch (DataNotFoundException e) {
                        logError("Distributor id specified does not exist: " + distId);
                    }
                }
                if (distErpNums.size() == 0) {
                    //nothing left to search for regardless of other crit
                    return null;
                } else if (distErpNums.size() == 1) {
                    crit.addEqualTo(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.DIST_ERP_NUM,
                            (String) distErpNums.get(0));
                } else {
                    crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.DIST_ERP_NUM, distErpNums);
                }
                usedTables.add(USED_PO_TAB);
            }
        }

        if (Utility.isSet(searchCrit.getErpPORefNum())) {
            logDebug("--2 searchCrit.getErpPORefNum()=" + searchCrit.getErpPORefNum());
            crit.addEqualTo
                    (PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ERP_PO_REF_NUM
                            , searchCrit.getErpPORefNum());
            usedTables.add(USED_PO_TAB);
        }

        if (Utility.isSet(searchCrit.getErpPONum())) {
            //do a one of as the thrustore processor preceeds everything with a "#" and the user shouldn't have to worry about
            //actually entering this value.

            if (searchCrit.getErpPONumMatchType() == PurchaseOrderStatusCriteriaData.EXACT_MATCH) {
                ArrayList opts = new ArrayList();
                opts.add(searchCrit.getErpPONum());
                opts.add("#" + searchCrit.getErpPONum());
                crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ERP_PO_NUM, opts);
                usedTables.add(USED_PO_TAB);

            } else if (searchCrit.getErpPONumMatchType() == PurchaseOrderStatusCriteriaData.BEGIN_WITH_IGNORE_CASE) {
                String sqlCond = "(" +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ERP_PO_NUM + " like '" +
                        searchCrit.getErpPONum() + "%' " +
                        "OR " +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ERP_PO_NUM + " like '#" +
                        searchCrit.getErpPONum() + "%' " +
                        ")";
                crit.addCondition(sqlCond);
                usedTables.add(USED_PO_TAB);
            } else {
                throw new RemoteException("Unknown match type");
            }
        }

        if (Utility.isSet(searchCrit.getOutboundPoNum())) {
            //do a one of as the thrustore processor preceeds everything with a "#" and the user shouldn't have to worry about
            //actually entering this value.

            if (searchCrit.getErpPONumMatchType() == PurchaseOrderStatusCriteriaData.EXACT_MATCH) 
            {
                ArrayList opts = new ArrayList();
                opts.add(searchCrit.getOutboundPoNum());
                opts.add("#" + searchCrit.getOutboundPoNum());
                crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.OUTBOUND_PO_NUM, opts);
                usedTables.add(USED_PO_TAB);

            } else if (searchCrit.getErpPONumMatchType() == PurchaseOrderStatusCriteriaData.EXACT_MATCH_AND_OTHER_COL)
            {
          
			          String sqlCond = "(( upper(" +
			          PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.OUTBOUND_PO_NUM
			          + " ) like upper('" +searchCrit.getOutboundPoNum() + "' ) " +
			          " ) OR ( upper(" +
			              
			          OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.REQUEST_PO_NUM  
			          + ") like upper('" + searchCrit.getOutboundPoNum() + "') " +
			          "))";
			          crit.addCondition(sqlCond);
			          
			          usedTables.add(USED_ORDER_TAB);
			          usedTables.add(USED_PO_TAB);
			          
            	
            }else if (searchCrit.getErpPONumMatchType() == PurchaseOrderStatusCriteriaData.BEGIN_WITH_IGNORE_CASE) {
                String sqlCond = "(" +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.OUTBOUND_PO_NUM + " like '" +
                        searchCrit.getOutboundPoNum() + "%' " +
                        "OR " +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.OUTBOUND_PO_NUM + " like '#" +
                        searchCrit.getOutboundPoNum() + "%' " +
                        ")";
                crit.addCondition(sqlCond);
                usedTables.add(USED_PO_TAB);
            } else {
                throw new RemoteException("Unknown match type");
            }
        }

        if (searchCrit.getPoDateRangeBegin() != null) {
            crit.addGreaterOrEqual(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.PO_DATE,
                    searchCrit.getPoDateRangeBegin());
            usedTables.add(USED_PO_TAB);
        }
        if (searchCrit.getPoDateRangeEnd() != null) {
            crit.addLessOrEqual(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.PO_DATE,
                    searchCrit.getPoDateRangeEnd());
            usedTables.add(USED_PO_TAB);
        }
        if (searchCrit.getPurchaseOrderStatus() != null && searchCrit.getPurchaseOrderStatus().size() > 0) {
            crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.PURCHASE_ORDER_STATUS_CD,
                    searchCrit.getPurchaseOrderStatus());
            usedTables.add(USED_PO_TAB);
        }
        if (searchCrit.getPurchOrdManifestStatus() != null && searchCrit.getPurchOrdManifestStatus().size() > 0) {
            crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.PURCH_ORD_MANIFEST_STATUS_CD,
                    searchCrit.getPurchOrdManifestStatus());
            usedTables.add(USED_PO_TAB);
        }
        if (searchCrit.isPendingManifestOnly()) {
            DBCriteria tmpCrit = new DBCriteria();
            tmpCrit.addEqualTo(PurchaseOrderDataAccess.PURCH_ORD_MANIFEST_STATUS_CD,
                    RefCodeNames.PURCH_ORD_MANIFEST_STATUS_CD.PENDING_MANIFEST);
            DBCriteria tmpCrit2 = new DBCriteria();
            tmpCrit2.addIsNull(PurchaseOrderDataAccess.PURCH_ORD_MANIFEST_STATUS_CD);
            tmpCrit.addOrCriteria(tmpCrit2);
            crit.addCondition("(" + tmpCrit.getWhereClause() + ")");
            usedTables.add(USED_PO_TAB);
        }

        //add the returns criteria
        DBCriteria returnsTmpCrit = getReturnCrit(searchCrit);
        if (returnsTmpCrit != null) {
            //create mini select statement for an 'in' subquery
            String subQry = "SELECT " + ReturnRequestDataAccess.PURCHASE_ORDER_ID + " FROM " +
                    ReturnRequestDataAccess.CLW_RETURN_REQUEST + " WHERE " + returnsTmpCrit.getSqlClause();
            crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.PURCHASE_ORDER_ID, subQry);
            usedTables.add(USED_PO_TAB);
        }

        //add the distributor invoice criteria
        if (Utility.isSet(searchCrit.getInvoiceDistNum())) {
            crit.addEqualToIgnoreCase(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.INVOICE_NUM,
                    searchCrit.getInvoiceDistNum());
            usedTables.add(USED_INVOICE_D_TAB);
        }
        if (searchCrit.getInvoiceDistDateRangeBegin() != null) {
            crit.addGreaterOrEqual(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.INVOICE_DATE,
                    searchCrit.getInvoiceDistDateRangeBegin());
            usedTables.add(USED_INVOICE_D_TAB);
        }
        if (searchCrit.getInvoiceDistDateRangeEnd() != null) {
            crit.addLessOrEqual(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.INVOICE_DATE,
                    searchCrit.getInvoiceDistDateRangeEnd());
            usedTables.add(USED_INVOICE_D_TAB);
        }

        if (searchCrit.getInvoiceDistAddDateRangeBegin() != null) {
            crit.addGreaterOrEqual(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.ADD_DATE,
                    searchCrit.getInvoiceDistAddDateRangeBegin(), true);
            usedTables.add(USED_INVOICE_D_TAB);
        }
        if (searchCrit.getInvoiceDistAddDateRangeEnd() != null) {
            crit.addLessOrEqual(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.ADD_DATE,
                    searchCrit.getInvoiceDistAddDateRangeEnd(), true);
            usedTables.add(USED_INVOICE_D_TAB);
        }

        if (searchCrit.getInvoiceDistExcludeStatusList() != null && !searchCrit.getInvoiceDistExcludeStatusList().isEmpty()) {
            crit.addNotOneOf(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.INVOICE_STATUS_CD,
                    searchCrit.getInvoiceDistExcludeStatusList());
            usedTables.add(USED_INVOICE_D_TAB);
        }

        if (searchCrit.getInvoiceDistStatusList() != null && !searchCrit.getInvoiceDistStatusList().isEmpty()) {
            crit.addOneOf(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.INVOICE_STATUS_CD,
                    searchCrit.getInvoiceDistStatusList());
            usedTables.add(USED_INVOICE_D_TAB);
        }

        if (searchCrit.getInvoiceStatus() != null && !searchCrit.getInvoiceStatus().equals("")
        		&& !searchCrit.getInvoiceStatus().equals("-Select-")) {
            crit.addEqualToIgnoreCase(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.INVOICE_STATUS_CD,
                    searchCrit.getInvoiceStatus());
            usedTables.add(USED_INVOICE_D_TAB);
        }      

        if(Utility.isSet(searchCrit.getOrderId())){
        	if(searchCrit.getOrderId().equals("null")){
        		crit.addIsNull(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.ORDER_ID);
        	}else{
        		crit.addEqualTo(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.ORDER_ID, 
        				searchCrit.getOrderId());
        	}
        //	usedTables.add(USED_INVOICE_D_TAB);
        }

   
        //finally add the order item criteria
        //The search on the due items uses the PO(Purchase Order) date range...this is
        //a little confusing as it essentially uses it for the PO target
        //ship date, and *not* the po date.
        DBCriteria itemCrit = new DBCriteria();
        boolean usingOrderItemPropertySubQuery = false;
        boolean usingPropertyTable = false;
        if (Utility.isSet(searchCrit.getItemStatus())) {
            usingOrderItemPropertySubQuery = true;
            usingPropertyTable = true;
            itemCrit.addEqualTo(OrderPropertyDataAccess.CLW_ORDER_PROPERTY + "." +
                    OrderPropertyDataAccess.CLW_VALUE, searchCrit.getItemStatus());
            itemCrit.addEqualTo(OrderPropertyDataAccess.CLW_ORDER_PROPERTY + "." +
                    OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.SHIPPING_STATUS);
            itemCrit.addEqualTo(OrderPropertyDataAccess.CLW_ORDER_PROPERTY + "." +
                    OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
                    RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        }

        boolean usingItemTable = false;
        if (searchCrit.getItemTargetShipDateBegin() != null) {
            usingItemTable = true;
            usingOrderItemPropertySubQuery = true;
            itemCrit.addGreaterOrEqual(OrderItemDataAccess.CLW_ORDER_ITEM + "." +
                    OrderItemDataAccess.TARGET_SHIP_DATE, searchCrit.getItemTargetShipDateBegin());
        }
        if (searchCrit.getItemTargetShipDateEnd() != null) {
            usingItemTable = true;
            usingOrderItemPropertySubQuery = true;
            itemCrit.addLessOrEqual(OrderItemDataAccess.CLW_ORDER_ITEM + "." +
                    OrderItemDataAccess.TARGET_SHIP_DATE, searchCrit.getItemTargetShipDateEnd());
        }
        if (searchCrit.isRoutedOnly()) {
            usingItemTable = true;
            usingOrderItemPropertySubQuery = true;
            itemCrit.addGreaterThan(OrderItemDataAccess.ORDER_ROUTING_ID, 0);
        }
        //now construct the item and item property subquery to add it to the
        //master query
        if (usingOrderItemPropertySubQuery) {
            StringBuffer sqlItmBuf = new StringBuffer("Select " +
                    OrderItemDataAccess.CLW_ORDER_ITEM + "." +
                    OrderItemDataAccess.PURCHASE_ORDER_ID +
                    " FROM " + OrderItemDataAccess.CLW_ORDER_ITEM);
            if (usingPropertyTable) {
                sqlItmBuf.append("," + OrderPropertyDataAccess.CLW_ORDER_PROPERTY +
                        " WHERE " + OrderPropertyDataAccess.CLW_ORDER_PROPERTY + "." +
                        OrderPropertyDataAccess.ORDER_ITEM_ID + "=" + OrderItemDataAccess.CLW_ORDER_ITEM + "." +
                        OrderItemDataAccess.ORDER_ITEM_ID + " AND ");
            } else {
                sqlItmBuf.append(" WHERE ");
            }
            sqlItmBuf.append(itemCrit.getSqlClause());

            crit.addOneOf(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." +
                    PurchaseOrderDataAccess.PURCHASE_ORDER_ID, sqlItmBuf.toString());
            usedTables.add(USED_PO_TAB);
        }

        //add the store criteria...this must stay last as there is some logic that determines which column to use to restrict the
        //results (clw_order.store_id or clw_invoice_dist.store_id)
        IdVector storeIdVector = searchCrit.getStoreIdVector();
        if (storeIdVector != null && storeIdVector.size() > 0) {
            if (distInvoiceSearch) {
                crit.addOneOf(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.STORE_ID, storeIdVector);
                usedTables.add(USED_INVOICE_D_TAB);
            } else {
                crit.addOneOf(OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.STORE_ID, storeIdVector);
                usedTables.add(USED_ORDER_TAB);
            }
        }

        logDebug("PO search sql clause: " + crit.getSqlClause());

        StringBuffer sqlBuf = new StringBuffer();
        //append the where clause constructed earlier
        if (distInvoiceSearch) {
            sqlBuf.append("Select distinct " + InvoiceDistDataAccess.CLW_INVOICE_DIST + "." +
                    InvoiceDistDataAccess.INVOICE_DIST_ID);
            usedTables.add(USED_INVOICE_D_TAB); //add the used invoice table, as it is implied in this search mode
            if (usedTables.size() > 1) {
                sqlBuf.append(", " + PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.PURCHASE_ORDER_ID);
            } else {
                sqlBuf.append(", 0");
            }
            sqlBuf.append(" FROM " + InvoiceDistDataAccess.CLW_INVOICE_DIST);
            Iterator it = usedTables.iterator();
            if (usedTables.size() > 1) {
                sqlBuf.append("," + OrderDataAccess.CLW_ORDER);
                sqlBuf.append("," + PurchaseOrderDataAccess.CLW_PURCHASE_ORDER);
            }
            if (usedTables.contains(USED_ORDER_ITEM_D_TAB)) {
                sqlBuf.append(", " + OrderItemDataAccess.CLW_ORDER_ITEM);
            }
            sqlBuf.append(" WHERE ");
            if (usedTables.size() > 1) {
                sqlBuf.append(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + PurchaseOrderDataAccess.ERP_PO_NUM + "(+)=" +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ERP_PO_NUM + " AND ");
                sqlBuf.append(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.STORE_ID + "(+)=" +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.STORE_ID + " AND ");
                sqlBuf.append(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ORDER_ID + "=" +
                        OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_ID + " AND ");
            }
            if (usedTables.contains(USED_ORDER_ITEM_D_TAB)) {
                sqlBuf.append(OrderDataAccess.CLW_ORDER + "." +
                        OrderDataAccess.ORDER_ID + "=" + OrderItemDataAccess.CLW_ORDER_ITEM + "." +
                        OrderItemDataAccess.ORDER_ID +
                        " AND ");
            }
        } else {
            //construct the select ids, from tables
            sqlBuf.append("Select distinct " + PurchaseOrderDataAccess.CLW_PURCHASE_ORDER +
                    "." + PurchaseOrderDataAccess.PURCHASE_ORDER_ID + "," +
                    OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_ID +
                    " FROM " + PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "," + OrderDataAccess.CLW_ORDER);
            if (usedTables.contains(USED_INVOICE_D_TAB)) {
                sqlBuf.append(", " + InvoiceDistDataAccess.CLW_INVOICE_DIST);
            }
            if (usingSiteDataPropertyTable) {
                sqlBuf.append(", " + PropertyDataAccess.CLW_PROPERTY + " SITE_DATA");
            }
            if (usedTables.contains(USED_ORDER_ITEM_D_TAB)) {
                sqlBuf.append(", " + OrderItemDataAccess.CLW_ORDER_ITEM);
            }
            if (usingTargetFacilityTable) {
                sqlBuf.append(", " + PropertyDataAccess.CLW_PROPERTY + " TARGET_FACILITY");
            }
            sqlBuf.append(" WHERE " + PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ORDER_ID + "=" +
                    OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_ID + " AND ");
            if (usingSiteDataPropertyTable) {
                sqlBuf.append(OrderDataAccess.CLW_ORDER + "." +
                        OrderDataAccess.SITE_ID + "=SITE_DATA." +
                        PropertyDataAccess.BUS_ENTITY_ID +
                        " AND ");
            }
            if (usedTables.contains(USED_ORDER_ITEM_D_TAB)) {
                sqlBuf.append(OrderDataAccess.CLW_ORDER + "." +
                        OrderDataAccess.ORDER_ID + "=" + OrderItemDataAccess.CLW_ORDER_ITEM + "." +
                        OrderItemDataAccess.ORDER_ID +
                        " AND ");
            }
            if (usingTargetFacilityTable) {
                sqlBuf.append(OrderDataAccess.CLW_ORDER + "." +
                        OrderDataAccess.SITE_ID + "=TARGET_FACILITY." +
                        PropertyDataAccess.BUS_ENTITY_ID +
                        " AND ");
            }
            if (usedTables.contains(USED_INVOICE_D_TAB)) {
                sqlBuf.append(InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + PurchaseOrderDataAccess.ERP_PO_NUM + "=" +
                        PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.ERP_PO_NUM + " AND ");
                sqlBuf.append(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER + "." + PurchaseOrderDataAccess.STORE_ID + "=" +
                        InvoiceDistDataAccess.CLW_INVOICE_DIST + "." + InvoiceDistDataAccess.STORE_ID + " AND ");
            }
        }
        sqlBuf.append(crit.getSqlClause());
        return sqlBuf.toString();
    }

  //gets the return associated criteria  from the search criteria
  private DBCriteria getReturnCrit(PurchaseOrderStatusCriteriaData searchCrit) {
    if (Utility.isSet(searchCrit.getDistributorReturnRequestNum()) || Utility.isSet(searchCrit.getReturnRequestRefNum())) {
      DBCriteria returnsCrit = new DBCriteria();
      if (Utility.isSet(searchCrit.getDistributorReturnRequestNum())) {
        returnsCrit.addEqualTo(ReturnRequestDataAccess.DISTRIBUTOR_REF_NUM, searchCrit.getDistributorReturnRequestNum());
      }
      if (Utility.isSet(searchCrit.getReturnRequestRefNum())) {
        returnsCrit.addEqualTo(ReturnRequestDataAccess.RETURN_REQUEST_REF_NUM, searchCrit.getReturnRequestRefNum());
      }
      return returnsCrit;
    }
    return null;
  }


  /**
   * Populates a PurchaseOrderStatusDescDataView from the starting point of an invoice dist id with the posibility of there
   * not being an associated purchase order.
   * @param pInvoiceDistId the invoice dist id.
   * @return a <code>PurchaseOrderStatusDescDataView</code>
   * @throws RemoteException Required by EJB 1.0
   */
  public PurchaseOrderStatusDescDataView getDistributorInvoiceDesc(int pInvoiceDistId) throws RemoteException {
    Connection con = null;
    try {
      con = this.getConnection();
      return popuateInvoicePo(pInvoiceDistId, null, con);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

    /**
     * Find pos and their associated data and returns a getPurchaseOrderStatusDescCollection of 0 or more
     * results.
     * @param searchCrit the search criteria
     * @return a <code>PurchaseOrderStatusDescDataViewVector</code>
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderStatusDescDataViewVector
    getPurchaseOrderStatusDescCollection(PurchaseOrderStatusCriteriaData searchCrit) throws RemoteException {


        Connection con = null;
        try {
            con = this.getConnection();
            return getPurchaseOrderStatusDescCollection(con, searchCrit);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }

    }

    /**
     * Find pos and their associated data and returns a getPurchaseOrderStatusDescCollection of 0 or more
     * results.
     * @param searchCrit the search criteria
     * @return a <code>PurchaseOrderStatusDescDataViewVector</code>
     * @throws            RemoteException Required by EJB 1.0
     */
    public PurchaseOrderStatusDescDataViewVector
    getPurchaseOrderStatusDescCollection(PurchaseOrderStatusCriteriaData searchCrit, boolean searchPos) throws RemoteException {


        Connection con = null;
        try {
            con = this.getConnection();
            searchCrit.setInvoiceSearch(searchPos);
            return getPurchaseOrderStatusDescCollection(con, searchCrit);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }

    }
   
    protected PurchaseOrderStatusDescDataViewVector  getPurchaseOrderStatusDescCollection(
            Connection con,
            PurchaseOrderStatusCriteriaData searchCrit
    ) throws Exception {

        PurchaseOrderStatusDescDataViewVector poStatList = new PurchaseOrderStatusDescDataViewVector();
        String sql = getSqlStatementFromSearchCriteria(searchCrit, con);

        this.logDebug("finding pos: " + sql);

        //this is the statement we will execute
        PreparedStatement stmt = con.prepareStatement(sql);
        //stmt.setMaxRows(PurchaseOrderStatusCriteriaData.MAX_SEARCH_RESULTS);

        ResultSet r = stmt.executeQuery();
        //XXX The aim of this is maintainability not preformance, it may
        //be necessary to rewrite this later, but the way this works, is
        //it makes one query to get the id relationship, and then uses the
        //dao objects to select out the data, thus makeing 2x the number of
        //sql calls than is probebly necessary.
        //The alternative is to modify the code generation to deal with joins
        //or to populate the objects here, which seems to defeate the whole
        //purpose of having auto generated dao objects.
        while (r.next()) {
            int poId = r.getInt(1);
            if (searchCrit.isInvoiceSearch()) {
                if (poId != 0) {
                    poStatList.add(popuateInvoicePo(poId, searchCrit, con));
                } else {
                    poId = r.getInt(2);
                    //this check should be unecessary
                    if (poId != 0) {
                        poStatList.add(popuatePo(poId, searchCrit, con));
                    }
                }
            } else {
                poStatList.add(popuatePo(poId, searchCrit, con));
            }
        }
        return poStatList;

    }


  private IdVector getAccountsForDistUser(Connection pCon, int pDistUserId) throws SQLException {

    DBCriteria crit = new DBCriteria();
    crit.addEqualTo(UserAssocDataAccess.USER_ID, pDistUserId);
    crit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                    RefCodeNames.USER_ASSOC_CD.ACCOUNT);

    return UserAssocDataAccess.selectIdOnly
      (pCon, UserAssocDataAccess.BUS_ENTITY_ID, crit);
  }

  /**
   *Populates a @see PurchaseOrderStatusDescDataView from the supplied invoiceDistId from the starting point of an invoice.
   *If the po cannot be found then many of the fields will be blank.
   *The optional optSearchCrit is only used for performance.  For example if the user was searching
   *for routed items only the populated return object will assume that it was routed rather than checjking the db.  If this
   *object is not avaliable then the method is capable of detemrmining this attribute on its own and null should be passed in.
   */
  private PurchaseOrderStatusDescDataView popuateInvoicePo(int invoiceDistId, 
  		PurchaseOrderStatusCriteriaData optSearchCrit,
    Connection con) throws DataNotFoundException, SQLException, RemoteException {
    InvoiceDistData invoice = InvoiceDistDataAccess.select(con, invoiceDistId);
    DBCriteria crit = new DBCriteria();
    crit.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID, invoiceDistId);
    InvoiceDistDetailDataVector invoiceDetail = InvoiceDistDetailDataAccess.select(con, crit);
    crit = new DBCriteria();
    //XXX if the purchase order id is added to the invoice dist table it should be used.
    IdVector poIdv;
    if(Utility.isSet(invoice.getErpPoNum())){
      crit.addEqualTo(PurchaseOrderDataAccess.ERP_PO_NUM, invoice.getErpPoNum());
      crit.addEqualTo(PurchaseOrderDataAccess.STORE_ID, invoice.getStoreId());
      poIdv = PurchaseOrderDataAccess.selectIdOnly(con, crit);
    }else{
      poIdv = new IdVector();
    }
    PurchaseOrderStatusDescDataView poStat;
    if (poIdv.size() == 1) {
      int poId = ((Integer) poIdv.get(0)).intValue();
      poStat = popuatePo(poId, optSearchCrit, con);
    } else {
      if (poIdv.size() > 1) {
        logError("Multiple pos found for supplied invoice data: storeid=" + invoice.getStoreId() + " and po num=" +
                 invoice.getErpPoNum());
      }
      poStat = PurchaseOrderStatusDescDataView.createValue();
      if (invoice.getBusEntityId() > 0) {
        try {
          poStat.setDistributorBusEntityData(BusEntityDataAccess.select(con, invoice.getBusEntityId()));
          if (!RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(poStat.getDistributorBusEntityData().getBusEntityTypeCd())) {
            poStat.setDistributorBusEntityData(null);
            logError("bus entity id: " + invoice.getBusEntityId() + " is not a distributor but should be (invoice dist id: " +
                     invoice.getInvoiceDistId() + ")");
          }
        } catch (DataNotFoundException e) {
          logError("dist not found for bus entity id: " + invoice.getBusEntityId());
        }
      }

      if (invoice.getAccountId() > 0) {
          try {
            poStat.setAccountBusEntityData(BusEntityDataAccess.select(con, invoice.getAccountId()));

          } catch (DataNotFoundException e) {
            logError("account not found for bus entity id: " + invoice.getAccountId());
          }
      }
    }

    crit = new DBCriteria();
    crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID, invoiceDistId);
    ArrayList oneOf = new ArrayList();
    oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_FREIGHT);
    oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_MISC_CHARGE);
    oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TAX);
    oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
    oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_DISCOUNT);
    crit.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, oneOf);
    crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
    OrderPropertyDataVector values = OrderPropertyDataAccess.select(con, crit);
    Iterator it = values.iterator();
    while (it.hasNext()) {
      OrderPropertyData prop = (OrderPropertyData) it.next();
      if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_FREIGHT.equals(prop.getOrderPropertyTypeCd())) {
        try {
          BigDecimal bd = new BigDecimal(prop.getValue());
          poStat.setVendorRequestedFreight(bd);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_MISC_CHARGE.equals(prop.getOrderPropertyTypeCd())) {
        try {
          BigDecimal bd = new BigDecimal(prop.getValue());
          poStat.setVendorRequestedMiscCharges(bd);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TAX.equals(prop.getOrderPropertyTypeCd())) {
        try {
          BigDecimal bd = new BigDecimal(prop.getValue());
          poStat.setVendorRequestedTax(bd);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED.
      		equals(prop.getOrderPropertyTypeCd())) {
        if (Utility.isTrue(prop.getValue())) {
          poStat.setInvoiceApproved(true);
          poStat.setInvoiceApprovedBy(prop.getAddBy());
          poStat.setInvoiceApprovedDate(prop.getAddDate());
        }
      }else if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_DISCOUNT.
      		equals(prop.getOrderPropertyTypeCd())) {
        try {
        	BigDecimal bd = new BigDecimal(prop.getValue());
          poStat.setVendorRequestedDiscount(bd);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } 
    }

    poStat.setInvoiceDist(invoice);
    poStat.setInvoiceDistDetailList(invoiceDetail);

    
    //add on charges
    crit = new DBCriteria();
    crit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, invoice.getOrderId());
    crit.addEqualTo(OrderAddOnChargeDataAccess.BUS_ENTITY_ID, invoice.getBusEntityId());
    OrderAddOnChargeDataVector charges = OrderAddOnChargeDataAccess.select(con, crit);
    if(charges!=null && charges.size()>0){
    	poStat.setOrderAddOnChargeDataVector(charges);
    }
    
    
    
    return poStat;
  }

  /**
   *Populates a @see PurchaseOrderStatusDescDataView from the supplied poid.
   *The optional optSearchCrit is only used for performance.  For example if the user was searching
   *for routed items only the populated return object will assume that it was routed rather than checjking the db.  If this
   *object is not avaliable then the method is capable of detemrmining this attribute on its own and null should be passed in.
   */
  private PurchaseOrderStatusDescDataView popuatePo(int poId, PurchaseOrderStatusCriteriaData optSearchCrit, Connection con) throws
    DataNotFoundException, SQLException {
    PurchaseOrderStatusDescDataView poStat = PurchaseOrderStatusDescDataView.createValue();
    if (isDebugOn()) {
      logDebug("found poId: " + poId);
    }
    PurchaseOrderData po = PurchaseOrderDataAccess.select(con, poId);
    int orderId = po.getOrderId();
    OrderData ord;
    try {
      ord = OrderDataAccess.select(con, orderId);
    } catch (DataNotFoundException e) {
      //means that there was no order associated with this po
      //this should not happen, but we can deal with it gracefully
      ord = null;
    }
    poStat.setOrderData(ord);
    poStat.setPurchaseOrderData(po);

    if (optSearchCrit != null && optSearchCrit.isRoutedOnly()) {
      //if we are only looking for routed pos in the search criteria then just set the
      //flag to true, no need to query the database.
      poStat.setRouted(Boolean.TRUE);
    } else {
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID, poId);
      OrderItemDataVector itms = OrderItemDataAccess.select(con, crit, 1);
      if (itms != null && itms.size() > 0) {
        poStat.setRouted(new Boolean(((OrderItemData) itms.get(0)).getOrderRoutingId() > 0));
      } else {
        poStat.setRouted(Boolean.FALSE);
      }
    }
    DBCriteria crit;
    if (po.getOrderId() != 0) {
      //now populate all of the relevant data using the database, not
      //EJB access.
      // ship to orderAddressData
      crit = new DBCriteria();
      OrderAddressData saddr;
      OrderAddressData baddr;
      ArrayList oneOf = new ArrayList();
      oneOf.add(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
      oneOf.add(RefCodeNames.ADDRESS_TYPE_CD.BILLING);
      crit.addOneOf(OrderAddressDataAccess.ADDRESS_TYPE_CD, oneOf);
      crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, po.getOrderId());
      OrderAddressDataVector addrs = OrderAddressDataAccess.select(con, crit);
      OrderAddressDataVector saddrs = new OrderAddressDataVector();
      OrderAddressDataVector baddrs = new OrderAddressDataVector();
      Iterator it = addrs.iterator();
      while (it.hasNext()) {
        OrderAddressData addr = (OrderAddressData) it.next();
        if (RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.equals(addr.getAddressTypeCd())) {
          saddrs.add(addr);
        } else if (RefCodeNames.ADDRESS_TYPE_CD.BILLING.equals(addr.getAddressTypeCd())) {
          baddrs.add(addr);
        }
      }
      if (saddrs.size() == 1) {
        saddr = (OrderAddressData) saddrs.get(0);
      } else if (saddrs.size() > 1) {
        throw new SQLException("multiple shipping address found for order id: " + po.getOrderId());
      } else {
        saddr = OrderAddressData.createValue();
      }

      if (baddrs.size() == 1) {
        baddr = (OrderAddressData) baddrs.get(0);
      } else if (saddrs.size() > 1) {
        throw new SQLException("multiple billing address found for order id: " + po.getOrderId());
      } else {
        baddr = OrderAddressData.createValue();
      }
      poStat.setShipToAddress(saddr);
      poStat.setBillToAddress(baddr);

      crit = new DBCriteria();
      oneOf = new ArrayList();
      oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
      oneOf.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, po.getOrderId());
      crit.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, oneOf);
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
      OrderPropertyDataVector values = OrderPropertyDataAccess.select(con, crit);
      if (values != null) {
        it = values.iterator();
        while (it.hasNext()) {
          OrderPropertyData prop = (OrderPropertyData) it.next();
          if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED.equals(prop.getOrderPropertyTypeCd())) {
            poStat.setOrderReceived(Utility.isTrue(prop.getValue()));
          } else if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER.equals(prop.getOrderPropertyTypeCd())) {
            poStat.setBillingOnlyOrder(Utility.isTrue(prop.getValue()));
          }
        }
      }
    } //end orderId > 0

    //Account Name
    BusEntityData acctbe = getAccountBusEnt(con, ord);
    poStat.setAccountBusEntityData(acctbe);
    poStat.setPoAccountName(acctbe.getShortDesc());

    //distributor name
    poStat.setDistributorBusEntityData(getDistributorBusEnt(con, po.getDistErpNum()));

    //order frieght data
    if (po.getOrderId() != 0) {
      try {
        crit = new DBCriteria();
        crit.addEqualTo(OrderFreightDataAccess.ORDER_ID, po.getOrderId());
        crit.addEqualTo(OrderFreightDataAccess.BUS_ENTITY_ID, poStat.getDistributorBusEntityData().getBusEntityId());
        OrderFreightDataVector ofdv = OrderFreightDataAccess.select(con, crit);
        poStat.setOrderFreightDataVector(ofdv);
      } catch (Exception e) {
        processException(e);
      }
    }
    
    //add on charges
    crit = new DBCriteria();
    crit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, orderId);
    crit.addEqualTo(OrderAddOnChargeDataAccess.PURCHASE_ORDER_ID, poId);
    OrderAddOnChargeDataVector charges = OrderAddOnChargeDataAccess.select(con, crit);
    if(charges!=null && charges.size()>0){
    	poStat.setOrderAddOnChargeDataVector(charges);
    }
  
    // ship via orderAddressData (if any)
    try {
      if (poStat.getDistributorBusEntityData() != null) {
        poStat.setShipVia(BusEntityDAO.getFreightHandlerByPoId(con, po, poStat.getDistributorBusEntityData().getBusEntityId()));
      } else {
        poStat.setShipVia(BusEntityDAO.getFreightHandlerByPoId(con, po, 0));
      }
    } catch (Exception e) {
      processException(e);
    }

    //returnRequest data vector
    //reuse the old criteria so if some of the search options were relevant we
    //will only pick up those returns that match the relevant criteria, not all
    //of them that belong to the po that matches the criteria.
    crit = new DBCriteria();
    if (optSearchCrit != null) {
      DBCriteria returnsTmpCrit = getReturnCrit(optSearchCrit);
      if (returnsTmpCrit != null) {
        crit.addCondition(returnsTmpCrit.getWhereClause());
      }
    }
    crit.addEqualTo(ReturnRequestDataAccess.PURCHASE_ORDER_ID, po.getPurchaseOrderId());
    ReturnRequestDataVector rrdv = ReturnRequestDataAccess.select(con, crit);
    poStat.setReturnRequestDataVector(rrdv);

    crit = new DBCriteria();
    crit.addEqualTo(ManifestItemDataAccess.PURCHASE_ORDER_ID, poStat.getPurchaseOrderData().getPurchaseOrderId());
    poStat.setManifestDataItems(ManifestItemDataAccess.select(con, crit));
    return poStat;
  }


  private BusEntityData getDistributorBusEnt(Connection pCon, String pDistErpNum) throws SQLException {
    DBCriteria crit = new DBCriteria();
    crit.addEqualTo(BusEntityDataAccess.ERP_NUM, pDistErpNum);
    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
    BusEntityDataVector distV = BusEntityDataAccess.select(pCon, crit);
    if (distV == null || distV.size() != 1) {
      return BusEntityData.createValue();
    } else {
      BusEntityData distD = (BusEntityData) distV.get(0);
      return distD;
    }
  }

  private BusEntityData getAccountBusEnt(Connection pCon, OrderData pOrder) throws SQLException {
    if (pOrder == null || pOrder.getAccountId() == 0) {
      return BusEntityData.createValue();
    }
    //Account Name
    try {
      return BusEntityDataAccess.select(pCon, pOrder.getAccountId());
    } catch (Exception e) {
      if (e instanceof SQLException) {
        throw (SQLException) e;
      } else {
        return BusEntityData.createValue();
      }
    }
  }


  /**
   * Finds pos and returns a PurchaseOrderDataVector of 0 or moreresults.
   * @param searchCrit the search criteria
   * @return a <code>PurchaseOrderDataVector</code>
   * @throws            RemoteException Required by EJB 1.0
   */
  public PurchaseOrderDataVector getPurchaseOrderCollection(
    PurchaseOrderStatusCriteriaData searchCrit) throws RemoteException {
    PurchaseOrderDataVector poList = new PurchaseOrderDataVector();
    Connection con = null;
    try {
      con = this.getConnection();

      String sql = getSqlStatementFromSearchCriteria(searchCrit, con);
      this.logDebug("finding pos: " + sql);

      //this is the statement we will execute
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setMaxRows(250);

      ResultSet r = stmt.executeQuery();
      //XXX The aim of this is maintainability not performance, it may
      //be necessary to rewrite this later, but the way this works, is
      //it makes one query to get the id relationship, and then uses the
      //dao objects to select out the data, thus makeing 2x the number of
      //sql calls than is probebly necessary.
      //The alternative is to modify the code generation to deal with joins
      //or to populate the objects here, which seems to defeate the whole
      //purpose of having auto generated dao objects.
      while (r.next()) {
        int poId = r.getInt(1);
        if (isDebugOn()) {
          logDebug("found poId: " + poId);
        }
        try {
          PurchaseOrderData po = PurchaseOrderDataAccess.select(con, poId);
          poList.add(po);
        } catch (DataNotFoundException e) {
          //this is an unexpected condition.
          break;
        }
      }
      return poList;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(con);
    }
  }

  /**
   * Finds pos and returns a RealPurchaseOrderNumViewVector of 0 or moreresults.
   * @param poNum PO number
   * @return a <code>RealPurchaseOrderNumViewVector</code>
   * @throws            RemoteException Required by EJB 1.0
   */
  public RealPurchaseOrderNumViewVector getRealPurchaseOrderNum(int storeId, String poNum)
          throws RemoteException {

        if (poNum==null) {
            poNum = "";
        }
        String sql =
            "SELECT o.order_id,  '', po.outbound_po_num AS real_value "+
            " FROM CLW_PURCHASE_ORDER po, CLW_ORDER o"+
            " WHERE outbound_po_num LIKE ? " +
            " AND o.order_id = po.order_id "+
            " AND po.po_date > sysdate - 270 "+
            " AND o.store_id = ? " +
            " ORDER BY real_value ";

        Connection conn = null;
        logInfo("Run SQL:" + sql);
        RealPurchaseOrderNumViewVector result = new RealPurchaseOrderNumViewVector();
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%"+poNum+"%");
            stmt.setInt(2, storeId);
            stmt.setMaxRows(250);
            IdVector orderIds = new IdVector();
           // String ponum=null;
            ResultSet r = stmt.executeQuery();
            Map mapOrders = new HashMap();
            while (r.next()) {
                RealPurchaseOrderNumView item = RealPurchaseOrderNumView.createValue();
                item.setOrderId(r.getInt(1));
                item.setDistrPOType(r.getString(2));
                item.setPONumValue(r.getString(3));
                orderIds.add(new Integer(item.getOrderId()));
               // ponum=item.getPONumValue();
               // result.add(item);
                mapOrders.put("" + item.getOrderId(), item);
            }
            DBCriteria cr = new DBCriteria();
            cr.addOneOf(PurchaseOrderDataAccess.ORDER_ID, orderIds);
           // cr.addContainsIgnoreCase(PurchaseOrderDataAccess.ERP_PO_NUM, ponum);

            PurchaseOrderDataVector poData = PurchaseOrderDataAccess.select(conn, cr);

            // The matching with orderId does not currently work with the hash map key
            // Will be replaced with addition of OutboundPoNum column in Purchase Order table

            for (int i = 0; poData != null && i < poData.size(); i++) {
            	PurchaseOrderData poItem = (PurchaseOrderData) poData.get(i);
            	RealPurchaseOrderNumView rPoItemSource = (RealPurchaseOrderNumView) mapOrders.get("" + poItem.getOrderId());
            	RealPurchaseOrderNumView rPoItem = RealPurchaseOrderNumView.createValue();
            	rPoItem.setDistrPOType(rPoItemSource.getDistrPOType());
            	rPoItem.setOrderId(poItem.getOrderId());
            	rPoItem.setPONumValue(poItem.getErpPoNum());
            	BusEntityData dist = getDistributorBusEnt(conn, poItem.getDistErpNum());
                // ship via orderAddressData (if any)
                if (dist != null)
                	rPoItem.setDistributorName(dist.getShortDesc());
            	rPoItem.setPurchaseOrder(poItem);
            	/*
                PurchaseOrderData poItem = (PurchaseOrderData) poData.get(i);
                RealPurchaseOrderNumView rPoItem = (RealPurchaseOrderNumView) mapOrders.get("" + poItem.getOrderId());
                if (poItem != null) {
                   rPoItem.setPurchaseOrder(poItem);
                   //rPoItem.setPONumValue(poItem.getErpPoRefNum());
                   // result.add(poItem);
                }*/
                result.add(rPoItem);
            }

        } catch (Exception sqle) {
            sqle.printStackTrace();
        } finally {
            closeConnection(conn);
        }
      return result;
  }

  /**
   * Returns a purchase order and it's associated properties and objects
   * @param purchaseOrderId the purchase order id
   * @return a <code>PurchaseOrderStatusDescDataView</code> object describing a purchase order
   * @throws            RemoteException Required by EJB 1.0
   */
  public PurchaseOrderStatusDescDataView getPurchaseOrderStatusDesc(int purchaseOrderId) throws RemoteException {

      PurchaseOrderStatusDescDataView po = PurchaseOrderStatusDescDataView.createValue();
      Connection con = null;

    try {
      con = getConnection();
      if (1 == 1) {
        return popuatePo(purchaseOrderId, null, con);
      }

      DBCriteria crit = new DBCriteria();
      PurchaseOrderData poData;
      try {
        poData = PurchaseOrderDataAccess.select(con, purchaseOrderId);
      } catch (DataNotFoundException e) {
        logError("po not found for queried id: " + purchaseOrderId);
        return po;
      }
      OrderData ordData;
      try {
        ordData = OrderDataAccess.select(con, poData.getOrderId());
      } catch (DataNotFoundException e) {
        ordData = OrderData.createValue();
        logError("No order associated with this po: " + poData.getErpPoNum());
      }
      OrderAddressData saddr = null;
      OrderAddressData baddr = null;
      BusEntityData account = null;
      String accountName = null;
      if (ordData != null) {
        //get the order address information
        crit = new DBCriteria();
        ArrayList oneOf = new ArrayList();
        oneOf.add(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        oneOf.add(RefCodeNames.ADDRESS_TYPE_CD.BILLING);
        crit.addOneOf(OrderAddressDataAccess.ADDRESS_TYPE_CD, oneOf);
        crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, ordData.getOrderId());
        OrderAddressDataVector addrs = OrderAddressDataAccess.select(con, crit);
        OrderAddressDataVector saddrs = new OrderAddressDataVector();
        OrderAddressDataVector baddrs = new OrderAddressDataVector();
        Iterator it = addrs.iterator();
        while (it.hasNext()) {
          OrderAddressData addr = (OrderAddressData) it.next();
          if (RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.equals(addr.getAddressTypeCd())) {
            saddrs.add(addr);
          } else if (RefCodeNames.ADDRESS_TYPE_CD.BILLING.equals(addr.getAddressTypeCd())) {
            baddrs.add(addr);
          }
        }
        if (saddrs.size() == 1) {
          saddr = (OrderAddressData) saddrs.get(0);
        } else if (saddrs.size() > 1) {
          throw new RemoteException("multiple shipping address found for order id: " + ordData.getOrderId());
        } else {
          saddr = OrderAddressData.createValue();
        }

        if (baddrs.size() == 1) {
          baddr = (OrderAddressData) baddrs.get(0);
        } else if (saddrs.size() > 1) {
          throw new RemoteException("multiple billing address found for order id: " + ordData.getOrderId());
        } else {
          baddr = OrderAddressData.createValue();
        }

        account = getAccountBusEnt(con, ordData);

        try {
          PropertyUtil pru = new PropertyUtil(con);
          accountName = pru.fetchValue(0, account.getBusEntityId(),
                                       RefCodeNames.PROPERTY_TYPE_CD.PURCHASE_ORDER_ACCOUNT_NAME);
        } catch (Exception e) {
          accountName = null;
        }
        if (!Utility.isSet(accountName)) {
          if (account != null) {
            accountName = account.getShortDesc();
          } else {
            accountName = "";
          }
        }
      }
      BusEntityData dist = getDistributorBusEnt(con, poData.getDistErpNum());
      // ship via orderAddressData (if any)
      if (dist != null) {
        po.setShipVia(BusEntityDAO.getFreightHandlerByPoId(con, poData, dist.getBusEntityId()));
      } else {
        po.setShipVia(BusEntityDAO.getFreightHandlerByPoId(con, poData, 0));
      }
      crit = new DBCriteria();

      crit.addEqualTo(ManifestItemDataAccess.PURCHASE_ORDER_ID, poData.getPurchaseOrderId());
      po.setManifestDataItems(ManifestItemDataAccess.select(con, crit));
      po.setPurchaseOrderData(poData);
      po.setOrderData(ordData);
      po.setShipToAddress(saddr);
      po.setBillToAddress(baddr);
      po.setAccountBusEntityData(account);
      po.setPoAccountName(accountName);
      po.setDistributorBusEntityData(dist);
      
      //add on charges
      int orderId = poData.getOrderId();
      int poId = poData.getPurchaseOrderId();
      crit = new DBCriteria();
      crit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, orderId);
      crit.addEqualTo(OrderAddOnChargeDataAccess.PURCHASE_ORDER_ID, poId);
      OrderAddOnChargeDataVector charges = OrderAddOnChargeDataAccess.select(con, crit);
      if(charges!=null && charges.size()>0){
    	  po.setOrderAddOnChargeDataVector(charges);
      }
      
      return po;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }


  /**
   *Increments and *then* Retrieves the current ReturnRequestRefNum from the property table.
   *It is important that it is done in that order or there is the danger 2 customers
   *will get the same return request number.  In this manner we first update it (thus getting
   *a lock on that row) then read in the value.s
   */
  private String generateReturnRequestRefNum(Connection conn) throws SQLException, RemoteException {
    DBCriteria crit = new DBCriteria();
    crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
    crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.RETURN_REQUEST_NUMBER);
    String sql = "Update " + PropertyDataAccess.CLW_PROPERTY + " SET " + PropertyDataAccess.CLW_VALUE
                 + "=" + PropertyDataAccess.CLW_VALUE + "+1 WHERE " + crit.getWhereClause();
    int updteCount = conn.prepareStatement(sql).executeUpdate();
    if (updteCount == 0) {
      logInfo("Creating new " + RefCodeNames.PROPERTY_TYPE_CD.RETURN_REQUEST_NUMBER + " Property");
      PropertyData pd = PropertyData.createValue();
      pd.setAddBy("generateReturnRequestRefNum");
      pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pd.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.RETURN_REQUEST_NUMBER);
      pd.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.RETURN_REQUEST_NUMBER);
      pd.setValue("1");
      PropertyDataAccess.insert(conn, pd);
      return (pd.getValue());
    }

    PropertyDataVector pdv = PropertyDataAccess.select(conn, crit);
    if (pdv.size() > 1) {
      throw new RemoteException("Multiple active properties found for property: " +
                                RefCodeNames.PROPERTY_TYPE_CD.RETURN_REQUEST_NUMBER);
    } else {
      PropertyData pd = (PropertyData) pdv.get(0);
      return pd.getValue();
    }
  }

  /**
   *Adds or updates a return request depending on if the return request id is populated.
   *@param view a populated view object containing the return data and any other associated info
   *@param userName name of user doing modification
   *@returns ReturnRequestDescDataView with whatever changes reflected (new ids etc).
   *@throws RemoteException            RemoteException Required by EJB 1.0
   */
  public ReturnRequestDescDataView addUpdateReturnRequest(ReturnRequestDescDataView view, String userName) throws
    RemoteException {
    logInfo("addUpdateReturnRequest started");
    Connection conn = null;
    try {
      conn = getConnection();
      ReturnRequestData ret = view.getReturnRequestData();
      ret.setAddBy(userName);
      ret.setModBy(userName);
      //ReturnRequestDescDetailDataViewVector retDetVec = view.getReturnRequestDescDetailDataVector();
      OrderItemDescDataVector retDetVec = view.getReturnRequestOrderItemDescDataVector();
      PurchaseOrderData po = null;
      OrderData order = null;
      if (ret.getReturnRequestId() == 0) {
        //autogenerate the return number
        ret.setReturnRequestRefNum(generateReturnRequestRefNum(conn));
        ret = ReturnRequestDataAccess.insert(conn, ret);
        view.setReturnRequestData(ret);
        po = PurchaseOrderDataAccess.select(conn, view.getReturnRequestData().getPurchaseOrderId());
        OrderPropertyData opda = OrderPropertyData.createValue();
        opda.setAddBy(userName);
        opda.setModBy(userName);
        opda.setOrderId(po.getOrderId());
        opda.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opda.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opda.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opda.setValue("Return Requested on this order, Reference Number: " +
                      view.getReturnRequestData().getReturnRequestRefNum());
        OrderPropertyDataAccess.insert(conn, opda);
      } else {
        ReturnRequestDataAccess.update(conn, ret);
      }
      //now add/update the address data
      OrderAddressData pickupAddress = view.getPickupAddress();
      pickupAddress.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.RETURN_PICKUP);
      pickupAddress.setReturnRequestId(ret.getReturnRequestId());
      if (po == null && pickupAddress.getOrderId() == 0) {
        po = PurchaseOrderDataAccess.select(conn, view.getReturnRequestData().getPurchaseOrderId());
      }
      if (pickupAddress.getOrderId() == 0) {
        pickupAddress.setOrderId(po.getOrderId());
      }
      if (pickupAddress.getOrderAddressId() == 0) {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.RETURN_PICKUP);
        crit.addEqualTo(OrderAddressDataAccess.RETURN_REQUEST_ID, ret.getReturnRequestId());
        OrderAddressDataVector addrs = OrderAddressDataAccess.select(conn, crit);
        if (addrs.size() == 0) {
          //add it
          pickupAddress = OrderAddressDataAccess.insert(conn, pickupAddress);
          view.setPickupAddress(pickupAddress);
        } else if (addrs.size() > 1) {
          throw new RemoteException("Multiple address records found for return id: " + ret.getReturnRequestId());
        } else {
          //update it
          OrderAddressData oldAddress = (OrderAddressData) addrs.get(0);
          pickupAddress.setOrderAddressId(oldAddress.getOrderAddressId());
          OrderAddressDataAccess.update(conn, pickupAddress);
        }
      } else {
        OrderAddressDataAccess.update(conn, pickupAddress);
      }
      //update the detail records
      OrderItemDescDataVector newRetDetVec = new OrderItemDescDataVector();
      for (int i = 0; i < retDetVec.size(); i++) {
        OrderItemDescData retDet = (OrderItemDescData) retDetVec.get(i);
        if (retDet.getReturnRequestDetailData().getQuantityReturned() == 0
            && retDet.getReturnRequestDetailData().getReturnRequestDetailId() == 0) {
          continue;
        }
        retDet.getReturnRequestDetailData().setReturnRequestId(ret.getReturnRequestId());
        retDet.getReturnRequestDetailData().setAddBy(userName);
        retDet.getReturnRequestDetailData().setModBy(userName);
        if (retDet.getReturnRequestDetailData().getReturnRequestDetailId() == 0) {
          retDet.setReturnRequestDetailData((ReturnRequestDetailDataAccess.insert(conn, retDet.getReturnRequestDetailData())));
          if (po == null) {
            po = PurchaseOrderDataAccess.select(conn, view.getReturnRequestData().getPurchaseOrderId());
          }
          if (order == null) {
            order = OrderDataAccess.select(conn, po.getOrderId());
          }
          OrderItemData orderItem;
          if (retDet.getOrderItem() == null) {
            orderItem = OrderItemDataAccess.select(conn, retDet.getReturnRequestDetailData().getOrderItemId());
            retDet.setOrderItem(orderItem);
          } else {
            orderItem = retDet.getOrderItem();
          }
          OrderItemActionData oiad = OrderItemActionData.createValue();
          //XXX What action should this be set to?
          oiad.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RETURNED);
          oiad.setActionDate(new java.util.Date());
          oiad.setActionTime(new java.util.Date());
          oiad.setAddBy(userName);
          //XXX customer order line number?
          oiad.setAffectedLineItem(orderItem.getErpOrderLineNum());
          oiad.setAffectedOrderNum(order.getOrderNum());
          //XXX check for sku type configuration here? customer mfg etc.
          oiad.setAffectedSku(Integer.toString(orderItem.getItemSkuNum()));
          oiad.setModBy(userName);
          oiad.setOrderId(order.getOrderId());
          oiad.setOrderItemId(orderItem.getOrderItemId());
          oiad.setQuantity(retDet.getReturnRequestDetailData().getQuantityReturned());
          OrderItemActionDataAccess.insert(conn, oiad);
          newRetDetVec.add(retDet);
        } else {
          if (retDet.getReturnRequestDetailData().getQuantityReturned() == 0) {
            ReturnRequestDetailDataAccess.remove(conn, retDet.getReturnRequestDetailData().getReturnRequestDetailId());
          } else {
            ReturnRequestDetailDataAccess.update(conn, retDet.getReturnRequestDetailData());
            newRetDetVec.add(retDet);
          }
        }
      }
      view.setReturnRequestOrderItemDescDataVector(newRetDetVec);
      return view;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Fetches the Return request out of the database.  Does not populate the OrderItemDesc data
   *fully, it only sets the returnRequestDetailData property and orderItemData property
   *of the OrderItemDesc collection of objects.
   *@returns a populated ReturnRequestDescDataView object
   *@param returnId of the return data to fetch from the database
   *@throws RemoteException            RemoteException Required by EJB 1.0
   */
  public ReturnRequestDescDataView getReturnRequestDetail(int returnId) throws RemoteException {
    logInfo("getReturnRequestDetail started");
    Connection conn = null;
    ReturnRequestDescDataView returnDesc = ReturnRequestDescDataView.createValue();
    try {
      conn = getConnection();
      //get the returnData
      returnDesc.setReturnRequestData(ReturnRequestDataAccess.select(conn, returnId));

      //Get the return requests and the order item data associated with it
      //get the detail data
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ReturnRequestDetailDataAccess.RETURN_REQUEST_ID, returnId);
      ReturnRequestDetailDataVector retDetVec = ReturnRequestDetailDataAccess.select(conn, crit);
      crit = new DBCriteria();
      crit.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID, returnDesc.getReturnRequestData().getPurchaseOrderId());
      OrderItemDataVector ordItmVec = OrderItemDataAccess.select(conn, crit);
      OrderItemDescDataVector retDetViewVec = new OrderItemDescDataVector();
      for (int i = 0, len = retDetVec.size(); i < len; i++) {
        OrderItemDescData rView = OrderItemDescData.createValue();
        ReturnRequestDetailData retDet = (ReturnRequestDetailData) retDetVec.get(i);
        rView.setReturnRequestDetailData(retDet);
        for (int j = 0, len2 = ordItmVec.size(); j < len2; j++) {
          OrderItemData oItm = (OrderItemData) ordItmVec.get(j);
          if (oItm.getOrderItemId() == retDet.getOrderItemId()) {
            rView.setOrderItem(oItm);
            break;
          }
        }
        retDetViewVec.add(rView);
      }
      returnDesc.setReturnRequestOrderItemDescDataVector(retDetViewVec);

      //get the return address
      crit = new DBCriteria();
      crit.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.RETURN_PICKUP);
      crit.addEqualTo(OrderAddressDataAccess.RETURN_REQUEST_ID, returnId);
      OrderAddressDataVector addrs = OrderAddressDataAccess.select(conn, crit);
      if (addrs.size() == 0) {
        logError("No address data for return id: " + returnId + " returning empty orderAddressData");
        returnDesc.setPickupAddress(OrderAddressData.createValue());
      } else if (addrs.size() > 1) {
        throw new RemoteException("Multiple address records found for return id: " + returnId);
      } else {
        returnDesc.setPickupAddress((OrderAddressData) addrs.get(0));
      }
      return returnDesc;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Saves all savable data from a ERPPurchaseOrderLineDescViewVector data vector.  This data can only
   *be saved if there is a valid reference within our Database, it will not modify erp data.
   *
   *@param pERPPurchaseOrderLineDescViewVector data to save
   *@param pUserName user doing modification
   *@throws RemoteException            RemoteException Required by EJB 1.0
   */
  public void saveERPPurchaseOrderLineCollection(ERPPurchaseOrderLineDescViewVector pERPPurchaseOrderLineDescViewVector,
                                                 String pUserName) throws RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      Iterator it = pERPPurchaseOrderLineDescViewVector.iterator();
      while (it.hasNext()) {
        ERPPurchaseOrderLineDescView data = (ERPPurchaseOrderLineDescView) it.next();
        if (data.getOrderItemData() != null) {
          if (Utility.isSet(data.getOpenLineStatusCd())) {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.SHORT_DESC, RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_ITEM_ID, data.getOrderItemData().getOrderItemId());
            OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(con, crit);
            if (opdv.size() == 0) {
              //insert it
              OrderPropertyData opd = OrderPropertyData.createValue();
              opd.setAddBy(pUserName);
              opd.setModBy(pUserName);
              opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
              //set it to a note so it show up in order tracker
              opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
              opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD);
              opd.setValue(data.getOpenLineStatusCd());
              opd.setOrderId(data.getOrderItemData().getOrderId());
              opd.setOrderItemId(data.getOrderItemData().getOrderItemId());
              OrderPropertyDataAccess.insert(con, opd);
            } else if (opdv.size() == 1) {
              //update it
              OrderPropertyData opd = (OrderPropertyData) opdv.get(0);
              opd.setValue(data.getOpenLineStatusCd());
              opd.setModBy(pUserName);
              OrderPropertyDataAccess.update(con, opd);
            } else {
              throw new RemoteException("Expecting 1 or 0 " +
                                        RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD +
                                        " order property data records for order item: " +
                                        data.getOrderItemData().getOrderItemId());
            }
          }
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }

  /**
   *Updates the manifest information for this po and updates purchaseorder data passed in. Will do
   *inserts and updates of both data elements, but it only deals with the manifestItemData, return address,
   *and the purchaseOrderData elements of the supplied PurchaseOrderStatusDescDataViewVector.
   *
   *@param pPurchaseOrdersWithManifests
   *@returns the supplied PurchaseOrderStatusDescDataViewVector with any changes made by the update
   */
  public PurchaseOrderStatusDescDataViewVector updateManifestPackagesAndPurchaseOrderData(
    PurchaseOrderStatusDescDataViewVector pPurchaseOrdersWithManifests) throws RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      Iterator poIt = pPurchaseOrdersWithManifests.iterator();
      while (poIt.hasNext()) {
        PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) poIt.next();
        PurchaseOrderData poD = po.getPurchaseOrderData();
        if (poD.getPurchaseOrderId() > 0) {
          PurchaseOrderDataAccess.update(conn, poD);
        } else {
          //XXX need to check if this already exists?
          po.setPurchaseOrderData(PurchaseOrderDataAccess.insert(conn, poD));
        }

        Iterator manIt = po.getManifestItems().iterator();
        while (manIt.hasNext()) {
          ManifestItemView mV = (ManifestItemView) manIt.next();
          ManifestItemData m = mV.getManifestItem();
          m.setPurchaseOrderId(po.getPurchaseOrderData().getPurchaseOrderId());
          //set the status if it is not set
          if (!Utility.isSet(m.getManifestItemStatusCd())) {
            m.setManifestItemStatusCd(RefCodeNames.MANIFEST_ITEM_STATUS_CD.INITIATED);
          }
          OrderAddressData oad = mV.getReturnAddress();
          //This satisfiys the FK constaint, as well as being useful to have
          oad.setOrderId(poD.getOrderId());
          if (m.getManifestItemId() > 0) {
            oad.setManifestItemId(m.getManifestItemId());
            ManifestItemDataAccess.update(conn, m);
            if (oad != null) {
              if (oad.getOrderAddressId() > 0) {
                OrderAddressDataAccess.update(conn, oad);
              } else {
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(OrderAddressDataAccess.MANIFEST_ITEM_ID, m.getManifestItemId());
                OrderAddressDataVector oadv = OrderAddressDataAccess.select(conn, crit);
                if (oadv.size() == 1) {
                  oad.setOrderAddressId(((OrderAddressData) oadv.get(0)).getOrderAddressId());
                  OrderAddressDataAccess.update(conn, oad);
                } else if (oadv.size() == 0) {
                  mV.setReturnAddress(OrderAddressDataAccess.insert(conn, oad));
                }
                //else don't update it
              }
            }
          } else {
            mV.setManifestItem(ManifestItemDataAccess.insert(conn, m));
            //insert the return address into the order address table
            oad.setManifestItemId(m.getManifestItemId());
            OrderAddressDataAccess.insert(conn, oad);
          }
        }
      }
      return pPurchaseOrdersWithManifests;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Assigns the package ids to the passed in manifest records.  Does not save them, or update the database
   *in any way other than to modify the manifest counter.
   *@param pPurchaseOrdersWithManifests pruchase order view vector with their Manifests populated.
   *@return the passed in PurchaseOrderStatusDescDataViewVector with the manifest package ids populated.
   */
  public PurchaseOrderStatusDescDataViewVector assignManifestPackageIds(PurchaseOrderStatusDescDataViewVector
    pPurchaseOrdersWithManifests) throws RemoteException {
    Connection conn = null;
    try {
      logDebug(" assignManifestPackageIds, size="
               + pPurchaseOrdersWithManifests.size());

      String lDunsNumber = getAPIAccess().getPropertyServiceAPI().getProperty(RefCodeNames.PROPERTY_TYPE_CD.DUNS_NUMBER);
      if (!Utility.isSet(lDunsNumber)) {
        logError("NO " + RefCodeNames.PROPERTY_TYPE_CD.DUNS_NUMBER
                 + "property found");
        throw new RemoteException("DUNS Number is not set");
      }

      logDebug(" assignManifestPackageIds, lDunsNumber="
               + lDunsNumber);

      Iterator poIt = pPurchaseOrdersWithManifests.iterator();
      while (poIt.hasNext()) {
        PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) poIt.next();
        String currStatus = po.getPurchaseOrderData().getPurchOrdManifestStatusCd();
        int manCounter = 0;
        //if this po has been manifested before get the correct package counter
        if (!(currStatus == null || currStatus.equals(RefCodeNames.PURCH_ORD_MANIFEST_STATUS_CD.PENDING_MANIFEST))) {
          logDebug(">>>>>>>>>>>>>>>>In looking for duplicate manifest entries");
          //only get the connection if we need it
          if (conn == null) {
            conn = getConnection();
          }
          DBCriteria crit = new DBCriteria();
          crit.addEqualTo(ManifestItemDataAccess.PURCHASE_ORDER_ID, po.getPurchaseOrderData().getPurchaseOrderId());
          ManifestItemDataVector exitingMans = ManifestItemDataAccess.select(conn, crit);
          if (exitingMans != null) {
            Iterator exitingMansIt = exitingMans.iterator();
            while (exitingMansIt.hasNext()) {
              ManifestItemData exitingMan = (ManifestItemData) exitingMansIt.next();
              logDebug(">>examining package id:" + exitingMan.getPackageId());
              int len = exitingMan.getPackageId().length();
              int x = Integer.parseInt(exitingMan.getPackageId().substring(len - 2, len));
              logDebug(">>" + Integer.parseInt(exitingMan.getPackageId().substring(len - 2, len)));
              if (x > manCounter) {
                manCounter = x + 1;
              }
            }
          }
          logDebug("assignManifestPackageIds manCounter >>" + manCounter);
        }

        Iterator manIt = po.getManifestItems().iterator();

        while (null != manIt && manIt.hasNext()) {

          ManifestItemView m = (ManifestItemView) manIt.next();
          String lPack = Utility.padLeft(Integer.toString(manCounter), '0', 2);
          String temPO = po.getPurchaseOrderData().getErpPoNum();
          // Strip non-numerics

          StringBuffer lPoNBuf = new StringBuffer(Utility.pickOutNumerics(temPO));

          if (lPoNBuf.length() > 6) {
            lPoNBuf.substring(0, 6);
          } else if (lPoNBuf.length() < 6) {
            lPoNBuf = new StringBuffer(Utility.padLeft(lPoNBuf.toString(), '0', 6));
          }

          logDebug("temPO=" + temPO + " FINAL lPoNBuf=" + lPoNBuf);
          lPoNBuf.append(lPack);
          m.getManifestItem().setPackageId(lPoNBuf.toString());
          lPoNBuf.insert(0, SERVICE_TYPE_CD + lDunsNumber);
          //add the check digit to it
          String lPackageId = lPoNBuf.toString();
          //check digit for USPS is calulated with the 91 for the application identifier
          int checkDigit = Utility.calculateUSPSDeliveryServiceCheckDigitFromPackageId(lPackageId);

          lPackageId = lPackageId + Integer.toString(checkDigit);
          logDebug("assignManifestPackageIds, lPackageId=" + lPackageId);
          m.getManifestItem().setPackageConfirmId(lPackageId);
          manCounter++;
          logDebug("assignManifestPackageIds 2 manCounter >>" + manCounter);
        }
      }
      logDebug("assignManifestPackageIds returning pPurchaseOrdersWithManifests.size="
               + pPurchaseOrdersWithManifests.size());

      return pPurchaseOrdersWithManifests;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Mark the currently initiated manifest item records as ready to send to freight handler for
   *the specified distribution center and distributor ids supplied.
   *@param pDistributionCenterId the distribution center id to process this request for
   *@param pDistributorIds the list of distributor ids
   */
  public void processManifestComplete(String pDistributionCenterId, IdVector pDistributorIds) throws RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, pDistributorIds);
      String busEntSql = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.ERP_NUM, crit);
      crit = new DBCriteria();
      crit.addOneOf(PurchaseOrderDataAccess.DIST_ERP_NUM, busEntSql);
      String poSql = PurchaseOrderDataAccess.getSqlSelectIdOnly(PurchaseOrderDataAccess.PURCHASE_ORDER_ID, crit);
      crit = new DBCriteria();
      crit.addOneOf(PurchaseOrderDataAccess.PURCHASE_ORDER_ID, poSql);
      crit.addEqualTo(ManifestItemDataAccess.DISTRIBUTION_CENTER_ID, pDistributionCenterId);
      crit.addEqualTo(ManifestItemDataAccess.MANIFEST_ITEM_STATUS_CD, RefCodeNames.MANIFEST_ITEM_STATUS_CD.INITIATED);

      ManifestItemDataVector mans = ManifestItemDataAccess.select(conn, crit);
      Iterator it = mans.iterator();
      while (it.hasNext()) {
        ManifestItemData man = (ManifestItemData) it.next();
        man.setManifestItemStatusCd(RefCodeNames.MANIFEST_ITEM_STATUS_CD.READY_TO_SEND);
        ManifestItemDataAccess.update(conn, man);
      }
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Returns a OrderItemDescDataVector lightly populated.  The only data that is returned is the orderItemData object and the
   *invoiceDistDetail data object.
   *@param pInvoiceDistId the distributor invoie id to take as the base for population.
   *@param pPurchaseOrderId the purchase order id to which this invoice belongs if avaliable
   *@rturns a lightly populated OrderItemDescDataVector
   */
  public OrderItemDescDataVector getDistriutorInvoiceItemDetailLightWeight(int pInvoiceDistId, int pPurchaseOrderId) throws
    RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      InvoiceDistDetailDataVector iddv;
      if (pInvoiceDistId > 0) {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID, pInvoiceDistId);
        crit.addOrderBy(InvoiceDistDetailDataAccess.ERP_PO_LINE_NUM);
        iddv = InvoiceDistDetailDataAccess.select(conn, crit);
      } else {
        iddv = new InvoiceDistDetailDataVector();
      }
      OrderItemDataVector oidv;
      if (pPurchaseOrderId > 0) {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID, pPurchaseOrderId);
        crit.addOrderBy(OrderItemDataAccess.ERP_PO_LINE_NUM);
        oidv = OrderItemDataAccess.select(conn, crit);
      } else {
        oidv = new OrderItemDataVector();
      }

      OrderItemDescDataVector retVal = new OrderItemDescDataVector();
      Iterator it = oidv.iterator();
      while (it.hasNext()) {
        OrderItemData oItm = (OrderItemData) it.next();
        OrderItemDescData desc = OrderItemDescData.createValue();
        desc.setOrderItem(oItm);
        Iterator invIt = iddv.iterator();
        while (invIt.hasNext()) {
          InvoiceDistDetailData iItm = (InvoiceDistDetailData) invIt.next();
          if (iItm.getOrderItemId() == oItm.getOrderItemId()) {
            if (desc.getWorkingInvoiceDistDetailData() != null) {
              OrderItemDescData desc2 = OrderItemDescData.createValue();
              desc2.setOrderItem(oItm);
              desc2.setWorkingInvoiceDistDetailData(iItm);
              retVal.add(desc2);
            } else {
              desc.setWorkingInvoiceDistDetailData(iItm);
            }

            invIt.remove();
          }
        }
        retVal.add(desc);
      }
      //Loop through the remaining invoice items.  These are the items that we could not find a po line for
      Iterator invIt = iddv.iterator();
      while (invIt.hasNext()) {
        InvoiceDistDetailData iItm = (InvoiceDistDetailData) invIt.next();
        OrderItemDescData desc = OrderItemDescData.createValue();
        desc.setWorkingInvoiceDistDetailData(iItm);
        retVal.add(desc);
      }
      return retVal;
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Simple method to update the data in the database based off the passed in InvoiceDistData.
   */
  public InvoiceDistData addUpdateInvoiceDistData(InvoiceDistData pInvoiceDist) throws RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      addUpdateInvoiceDistData(conn, pInvoiceDist);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
    return pInvoiceDist;
  }

  /**
   *Simple method to update the data in the database based off the passed in InvoiceDistData.
   */
  private InvoiceDistData addUpdateInvoiceDistData(Connection conn, InvoiceDistData pInvoiceDist) throws SQLException,
    RemoteException, DuplicateNameException {
    InvoiceDistData invoiceDist = pInvoiceDist;
    DBCriteria crit = new DBCriteria();
    crit.addEqualToIgnoreCase(InvoiceDistDataAccess.INVOICE_NUM, invoiceDist.getInvoiceNum());
    crit.addEqualTo(InvoiceDistDataAccess.BUS_ENTITY_ID, invoiceDist.getBusEntityId());
    ArrayList ignoreInvoiceStatus = new ArrayList();
    ignoreInvoiceStatus.add(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
    ignoreInvoiceStatus.add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
    crit.addNotOneOf(InvoiceDistDataAccess.INVOICE_STATUS_CD, ignoreInvoiceStatus);
    crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_DIST_ID, pInvoiceDist.getInvoiceDistId());
    InvoiceDistDataVector idv = InvoiceDistDataAccess.select(conn, crit);
    if (idv.size() > 0) {
      throw new DuplicateNameException("An invoice with this invoice number already exists for this vendor");
    }
    if (invoiceDist.isDirty()) {
      if (invoiceDist.getInvoiceDistId() == 0) {
        invoiceDist = InvoiceDistDataAccess.insert(conn, invoiceDist);
      } else {
        InvoiceDistDataAccess.update(conn, invoiceDist);
      }
    }
    return invoiceDist;
  }


   /**
   *Simple method to update the data in the database.
   */
   public InvoiceDistData updateInvoiceDistData(InvoiceDistData pInvoiceDist) throws RemoteException {
       Connection conn = null;
       try {
           conn = getConnection();
           if (pInvoiceDist.isDirty()) {
               if (pInvoiceDist.getInvoiceDistId() == 0) {
                   pInvoiceDist = InvoiceDistDataAccess.insert(conn, pInvoiceDist);
               } else {
                   InvoiceDistDataAccess.update(conn, pInvoiceDist);
               }
           }
       } catch (Exception e) {
           throw processException(e);
       } finally {
           closeConnection(conn);
       }
       return pInvoiceDist;
   }

    /**
   *Encapsulates the business logice of updating a distributor invoice object.
   *@Returns the invoice dist id @see InvoiceDistData.getInvoiceDistId
   */
  public int processInvoiceDistUpdate(PurchaseOrderStatusDescDataView pInvoiceDesc, String userDoingMod, boolean approve) throws
    RemoteException, DuplicateNameException {
    Connection conn = null;
    try {
      conn = getConnection();
      InvoiceDistData invoiceDist = pInvoiceDesc.getInvoiceDist();
      PurchaseOrderData po = pInvoiceDesc.getPurchaseOrderData();
      // if the dist order # is empty, enter invoice # automatically
      if (null == invoiceDist.getDistOrderNum() || invoiceDist.getDistOrderNum().trim().length() < 1) {
        invoiceDist.setDistOrderNum(invoiceDist.getInvoiceNum());
      }
      // if the dist tracking # is empty, enter invoice # automatically
      if (null == invoiceDist.getTrackingNum() || invoiceDist.getTrackingNum().trim().length() < 1) {
        invoiceDist.setTrackingNum(invoiceDist.getInvoiceNum());
      }

      //make sure referencial integrity is set
      if(pInvoiceDesc.getOrderData() != null){
    	  invoiceDist.setOrderId(pInvoiceDesc.getOrderData().getOrderId());
      }
      if(po != null){
	      BusEntityData dis = getDistributorBusEnt(conn, po.getDistErpNum());

	      invoiceDist.setBusEntityId(dis.getBusEntityId());
      }
      if (invoiceDist.getBusEntityId() == 0) {
        throw new DataNotFoundException("No dist found");
      }
      PurchaseOrderData poD = pInvoiceDesc.getPurchaseOrderData();
      if(poD != null){
    	  invoiceDist.setErpPoNum(poD.getErpPoNum());
  		invoiceDist.setErpPoRefNum(poD.getErpPoRefNum());
      }
      if( pInvoiceDesc.getOrderData() != null){
	      if (invoiceDist.getStoreId() != pInvoiceDesc.getOrderData().getStoreId() && invoiceDist.getStoreId() > 0) {
	        throw new RemoteException("Mismatched Ids");
	      }
	      invoiceDist.setStoreId(pInvoiceDesc.getOrderData().getStoreId()); //in case it was 0
      }

      //end make sure referencial integrity is set
      pInvoiceDesc.getInvoiceDist().setModBy(userDoingMod);
      //if this is a new invoice copy the fields over from the po
      if (invoiceDist.getInvoiceDistId() == 0) {
        OrderData ord = pInvoiceDesc.getOrderData();
        pInvoiceDesc.getInvoiceDist().setAddBy(userDoingMod);
        invoiceDist.setErpSystemCd(ord.getErpSystemCd());

        invoiceDist.setShipToAddress1(pInvoiceDesc.getShipToAddress().getAddress1());
        invoiceDist.setShipToAddress2(pInvoiceDesc.getShipToAddress().getAddress2());
        invoiceDist.setShipToAddress3(pInvoiceDesc.getShipToAddress().getAddress3());
        invoiceDist.setShipToAddress4(pInvoiceDesc.getShipToAddress().getAddress4());
        invoiceDist.setShipToCity(pInvoiceDesc.getShipToAddress().getCity());
        invoiceDist.setShipToCountry(pInvoiceDesc.getShipToAddress().getCountryCd());
        invoiceDist.setShipToName(pInvoiceDesc.getShipToAddress().getShortDesc());
        invoiceDist.setShipToPostalCode(pInvoiceDesc.getShipToAddress().getPostalCode());
        invoiceDist.setShipToState(pInvoiceDesc.getShipToAddress().getStateProvinceCd());
        invoiceDist.setAddBy(userDoingMod);
      }

      String origInvStatus = invoiceDist.getInvoiceStatusCd();
      //set the new invoice satus
      if (!RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED.equals(origInvStatus) &&
          !RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP.equals(origInvStatus)) {
        if (approve) {
          invoiceDist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
        } else {
          invoiceDist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
        }
      }

      invoiceDist = addUpdateInvoiceDistData(conn, invoiceDist);

      Iterator it = pInvoiceDesc.getInvoiceDistDetailList().iterator();
      while (it.hasNext()) {
        InvoiceDistDetailData invDet = (InvoiceDistDetailData) it.next();
        invDet.setModBy(userDoingMod);
        if (invDet.getInvoiceDistId() == 0) {
          invDet.setInvoiceDistId(invoiceDist.getInvoiceDistId());
        }
        invDet.setModBy(userDoingMod);
        if (invDet.getInvoiceDistDetailId() == 0) {
          invDet.setAddBy(userDoingMod);
          invDet = InvoiceDistDetailDataAccess.insert(conn, invDet);
        } else {
          InvoiceDistDetailDataAccess.update(conn, invDet);
        }
      }
      if (approve) {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID, invoiceDist.getInvoiceDistId());
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                        RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
        crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(conn, crit);
        if (opdv.size() > 0) {
          OrderPropertyData o = (OrderPropertyData) opdv.get(0);
          if (!Utility.isTrue(o.getValue())) {
            o.setValue(Boolean.TRUE.toString());
            o.setModBy(userDoingMod);
            OrderPropertyDataAccess.update(conn, o);
          }
        } else {
          OrderPropertyData o = OrderPropertyData.createValue();
          o.setAddBy(userDoingMod);
          o.setInvoiceDistId(invoiceDist.getInvoiceDistId());
          o.setOrderId(invoiceDist.getOrderId());
          o.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          o.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
          o.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
          o.setValue(Boolean.TRUE.toString());
          o.setModBy(userDoingMod);
          try {
            OrderPropertyDataAccess.insert(conn, o);
          } catch (Exception e) {
            OrderPropertyDataAccess.insert(conn, o);
          }
        }
      }
      return invoiceDist.getInvoiceDistId();
    } catch (DuplicateNameException de) {
      throw de;
    } catch (SQLException exc) {
      logError(exc.getMessage());
      exc.printStackTrace();
      throw new RemoteException("^clw^SVIL01. Oracle error. Please contact support^clw^");
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
  }


  /**
   *Moves the invoice from the current assignment to a new po
   */
  public void reAssignInvoicePo(InvoiceDistData pInvoiceDist, PurchaseOrderData pPurchaseOrder, String pUserDoingMod) throws
    RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      BusEntityData dis = getDistributorBusEnt(conn, pPurchaseOrder.getDistErpNum());
      if (dis == null || dis.getBusEntityId() == 0) {
        throw new DataNotFoundException("No dist found for erp num " + pPurchaseOrder.getDistErpNum());
      }
      OrderDAO.enterOrderProperty(conn, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                                  "PREVIOUS_PO", "Previous Po: " + pInvoiceDist.getErpPoNum(), 0, 0, 0,
                                  pInvoiceDist.getInvoiceDistId(), 0, 0, 0, pUserDoingMod);
      pInvoiceDist.setBusEntityId(dis.getBusEntityId());
      pInvoiceDist.setErpPoNum(pPurchaseOrder.getErpPoNum());
      pInvoiceDist.setErpPoRefNum(pPurchaseOrder.getErpPoRefNum());
      addUpdateInvoiceDistData(conn, pInvoiceDist);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
  }

  /**
   *Rejects an invoice from the system.  This will add any appropriate order item actions and re-set any changed quantities.  The invoice
   *remains in the system but is set to a "Rejected" status and all of the items qty are set to 0.
   *Assumes that the invoice in a state that should allow this action.
   */
  public InvoiceDistData rejectInvoice(InvoiceDistData pInvoiceDist, String pUserDoingMod) throws RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      pInvoiceDist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
      pInvoiceDist.setModBy(pUserDoingMod);
      //get the items
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID, pInvoiceDist.getInvoiceDistId());
      InvoiceDistDetailDataVector itms = InvoiceDistDetailDataAccess.select(conn, crit);
      Iterator it = itms.iterator();
      while (it.hasNext()) {
        InvoiceDistDetailData aItm = (InvoiceDistDetailData) it.next();
        if (aItm.getOrderItemId() > 0 && pInvoiceDist.getOrderId() > 0) {
          //enter new action
          java.util.Date now = new java.util.Date();
          OrderItemActionData act = OrderItemActionData.createValue();
          act.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICE_REJECTED);
          act.setActionDate(now);
          act.setActionTime(now);
          //act.setActualTransactionId()
          act.setAddBy(pUserDoingMod);
          act.setAffectedId(pInvoiceDist.getInvoiceDistId());
          act.setAffectedSku(Integer.toString(aItm.getItemSkuNum()));
          act.setAffectedTable(InvoiceDistDataAccess.CLW_INVOICE_DIST);
          act.setModBy(pUserDoingMod);
          act.setOrderId(pInvoiceDist.getOrderId());
          act.setOrderItemId(aItm.getOrderItemId());
          act.setQuantity(aItm.getDistItemQuantity());
          aItm.setDistItemQuantity(0);
          aItm.setItemQuantity(0);
          InvoiceDistDetailDataAccess.update(conn, aItm);
          OrderItemActionDataAccess.insert(conn, act);
        }
      }
      InvoiceDistDataAccess.update(conn, pInvoiceDist);
    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }
    return pInvoiceDist;
  }


    public PurchaseOrderData getOrderByErpPoNumSimple (String pStoreType,
                                                      IdVector pStoreIds,
                                                      IdVector pDistributorId,
                                                      String pErpPoNum,
                                                      IdVector pAccIds) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            ArrayList distributorErpNumList = new ArrayList();
            if (pDistributorId != null && pDistributorId.size() > 0) {
                DBCriteria subcrit = new DBCriteria();
                subcrit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, IdVector.toCommaString(pDistributorId));
                BusEntityDataVector distV = BusEntityDataAccess.select(conn, subcrit);
                if (null != distV) {
                    Iterator dit = distV.iterator();
                    while (dit.hasNext()) {
                        BusEntityData distD = (BusEntityData) dit.next();
                        if (Utility.isSet(distD.getErpNum())) {
                            distributorErpNumList.add(distD.getErpNum().toUpperCase());
                        }
                    }
                }
            }

			//escape a sql problem.
			pErpPoNum = Utility.replaceString(pErpPoNum,"'","''");
            String poNumQ = "select po.purchase_order_id from clw_purchase_order po, clw_order o " +
                            " where o.order_id > 0 and po.order_Id = o.order_id ";

            //add Acct ids
            if(pAccIds !=null && pAccIds.size() > 0 ){
            	poNumQ += " and o.account_id in (" + IdVector.toCommaString(pAccIds) + ")";
            }

            //add dist erp numbers
            if (distributorErpNumList.size() > 0) {
                poNumQ = poNumQ + " AND po." + PurchaseOrderDataAccess.DIST_ERP_NUM + " IN (" + Utility.toCommaSting(distributorErpNumList, '\'') + ")";
            }

            //add store ids
            if (null != pStoreIds && pStoreIds.size() > 0) {
                poNumQ += " and o.store_id in (" + IdVector.toCommaString(pStoreIds) + ")";
            }

			//Need to switch to use bind variables instead of SQL.  Occasioanlly we get po numbers with special charachters that crash it, and this sql is executed alot so
			//bind variables would improve performance
			//pErpPoNum = Utility.replaceString(pErpPoNum, "'","''");

            String poNumSql = poNumQ +
                    " and po."+PurchaseOrderDataAccess.OUTBOUND_PO_NUM+" in ('"+pErpPoNum+"','#"+pErpPoNum+"')";

            //System.out.flush();
            //try{Thread.currentThread().wait(100);}catch(Exception e){}
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(PurchaseOrderDataAccess.PURCHASE_ORDER_ID, poNumSql);
            dbc.addOrderBy(PurchaseOrderDataAccess.ORDER_ID);
            PurchaseOrderDataVector odv = PurchaseOrderDataAccess.select(conn, dbc);
            if(odv.size()==0) {
                poNumSql = poNumQ +
                " and ( po."+PurchaseOrderDataAccess.OUTBOUND_PO_NUM+" like '"+pErpPoNum+"/%' or" +
                "  po."+PurchaseOrderDataAccess.OUTBOUND_PO_NUM+" like '#"+pErpPoNum+"/%' )";

                //System.out.flush();
                //try{Thread.currentThread().wait(100);}catch(Exception e){}
                dbc = new DBCriteria();
                dbc.addOneOf(PurchaseOrderDataAccess.PURCHASE_ORDER_ID, poNumSql);
                dbc.addOrderBy(PurchaseOrderDataAccess.ORDER_ID);
                odv = PurchaseOrderDataAccess.select(conn, dbc);
            }
            PurchaseOrderData poToReturn = null;
            if (odv != null && odv.size() > 0) {
                for(Iterator iter=odv.iterator(); iter.hasNext();) {
                    PurchaseOrderData poD = (PurchaseOrderData) iter.next();
                    String outboundPoNum = poD.getOutboundPoNum();
                    if(outboundPoNum.startsWith("#")) outboundPoNum = outboundPoNum.substring(1);
                    String poNum = pErpPoNum;
                    if(poNum.startsWith("#")) poNum = poNum = poNum.substring(1);
                    if(poNum.equals(outboundPoNum)) {
                        poToReturn = poD;
                    }
                    int suffInd = outboundPoNum.indexOf("/");
                    if(suffInd>0) {
                        outboundPoNum = outboundPoNum.substring(0,suffInd);
                        if(poNum.equals(outboundPoNum)) {
                            poToReturn = poD;
                        }
                    }
                }
            }
            if(poToReturn !=null ) {
                return poToReturn;
            }
            return getOrderByErpPoNumSimpleOld (conn, pStoreType,pStoreIds, pDistributorId,pErpPoNum);

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private PurchaseOrderData getOrderByErpPoNumSimpleOld (Connection conn, String pStoreType,
                                                      IdVector pStoreIds,
                                                      IdVector pDistributorId,
                                                      String pErpPoNum)
    throws Exception {
            String origErpPoNum = pErpPoNum;
            if (!pErpPoNum.startsWith("#")) {
                pErpPoNum = "#" + pErpPoNum;
            }

            ArrayList distributorErpNumList = new ArrayList();
            if (pDistributorId != null && pDistributorId.size() > 0) {
                DBCriteria subcrit = new DBCriteria();
                subcrit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, IdVector.toCommaString(pDistributorId));
                BusEntityDataVector distV = BusEntityDataAccess.select(conn, subcrit);
                if (null != distV) {
                    Iterator dit = distV.iterator();
                    while (dit.hasNext()) {
                        BusEntityData distD = (BusEntityData) dit.next();
                        if (Utility.isSet(distD.getErpNum())) {
                            distributorErpNumList.add(distD.getErpNum().toUpperCase());
                        }
                    }
                }
            }

            String poNumQ = "select po.purchase_order_id from clw_purchase_order po, clw_order o " +
                            " where o.order_id > 0 and po.order_Id = o.order_id and ";

            if (pStoreType != null && !RefCodeNames.STORE_TYPE_CD.MLA.equals(pStoreType)) {
                poNumQ += " (po.ERP_PO_NUM = '" + pErpPoNum + "' or o." + OrderDataAccess.REQUEST_PO_NUM + " like '" + origErpPoNum + "%')";
            } else {
                poNumQ += " po.ERP_PO_NUM = '" + pErpPoNum + "'";
            }

            //add dist erp numbers
            if (distributorErpNumList.size() > 0) {
                poNumQ = poNumQ + " AND po." + PurchaseOrderDataAccess.DIST_ERP_NUM + " IN (" + Utility.toCommaSting(distributorErpNumList, '\'') + ")";
            }

            //add store ids
            if (null != pStoreIds && pStoreIds.size() > 0) {
                poNumQ += " and o.store_id in (" + IdVector.toCommaString(pStoreIds) + ")";
            }
            //poNumQ += ")";

            //System.out.flush();
            //try{Thread.currentThread().wait(100);}catch(Exception e){}
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(PurchaseOrderDataAccess.PURCHASE_ORDER_ID, poNumQ);
            dbc.addOrderBy(PurchaseOrderDataAccess.ORDER_ID);
            PurchaseOrderDataVector odv = PurchaseOrderDataAccess.select(conn, dbc);
            if (odv != null && odv.size() > 0) {
                return (PurchaseOrderData) odv.get(0);
            }

            if (pStoreType != null
                    && !RefCodeNames.STORE_TYPE_CD.MLA.equals(pStoreType)) {
                // search again this time asuming the erp number was set
                PurchaseOrderStatusCriteriaData pc = PurchaseOrderStatusCriteriaData.createValue();
                pc.setDistributorIds(pDistributorId);
                pc.setStoreIdVector(pStoreIds);
                pc.setOrderRequestPoNum(null);
                pc.setErpPONum(pErpPoNum);
                APIAccess factory = new APIAccess();
                PurchaseOrder poEjb = factory.getPurchaseOrderAPI();
                PurchaseOrderStatusDescDataViewVector pos = poEjb.getPurchaseOrderStatusDescCollection(pc);
                if (pos != null) {
                    // loop through and find the first one with no customer po
                    // number set
                    Iterator it2 = pos.iterator();
                    while (it2.hasNext()) {
                        PurchaseOrderStatusDescDataView ord = (PurchaseOrderStatusDescDataView) it2.next();
                        if (!Utility.isSet(ord.getOrderData().getRequestPoNum())) {
                            return ord.getPurchaseOrderData();
                        }
                    }
                }
            }
        return null;
    }

    public List getInvoiceDistDetail(int orderItemId, String erpPoNum) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit2 = new DBCriteria();

            String iddtab = InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL;
            String idtab = InvoiceDistDataAccess.CLW_INVOICE_DIST;
            crit2.addJoinCondition(iddtab, InvoiceDistDetailDataAccess.INVOICE_DIST_ID, idtab, InvoiceDistDataAccess.INVOICE_DIST_ID);
            //crit.addJoinTableEqualTo(iddtab, InvoiceDistDetailDataAccess.ITEM_SKU_NUM, pBaton.getCurrInoiceDetailData().getItemSkuNum());
            crit2.addJoinTableEqualTo(iddtab, InvoiceDistDetailDataAccess.ORDER_ITEM_ID, orderItemId);
            crit2.addJoinTableEqualTo(idtab, InvoiceDistDataAccess.ERP_PO_NUM, erpPoNum);
            crit2.addJoinTableNotEqualTo(idtab, InvoiceDistDataAccess.INVOICE_STATUS_CD, RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
            crit2.addJoinTableNotEqualTo(idtab, InvoiceDistDataAccess.INVOICE_STATUS_CD, RefCodeNames.INVOICE_STATUS_CD.REJECTED);
            crit2.addDataAccessForJoin(new InvoiceDistDataAccess());
            crit2.addDataAccessForJoin(new InvoiceDistDetailDataAccess());

            return  JoinDataAccess.select(conn, crit2);

        } catch (Exception exc) {
            String em = "Error. enterOrderProperty. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public String getNewInvoiceNumForDistInvoiceLike(String invoiceNum, int distId) throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DistributorInvoiceNumTool distrInvNumTool=new DistributorInvoiceNumTool();
            return distrInvNumTool.getNewInvoiceNumForDistInvoiceLike(conn,invoiceNum,distId);
        } catch (Exception exc) {
            String em = "Error. enterOrderProperty. " + exc.getMessage();
            throw processException(exc);
        } finally {
            closeConnection(conn);
        }
    }

    public BigDecimal getTotalAmountWhithALLOperations(OrderItemDataVector poItems, OrderData orderData) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return PipelineCalculationOperations.getTotalAmountWhithALLOperations(conn, poItems, orderData);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public PurchaseOrderData getPurchaseOrder(int orderId,String distrErpNum,boolean throwDataNotFoundExc) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getPurchaseOrder(conn,orderId,distrErpNum);
        }
        catch (DataNotFoundException e) {
            e.printStackTrace();
            if(throwDataNotFoundExc){
                throw new DataNotFoundException(e.getMessage());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    public PurchaseOrderData getPurchaseOrder(Connection conn,int orderId,String distrErpNum) throws Exception {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PurchaseOrderDataAccess.ORDER_ID,orderId);
        dbc.addEqualTo(PurchaseOrderDataAccess.DIST_ERP_NUM,distrErpNum);
        dbc.addOrderBy(PurchaseOrderDataAccess.ORDER_ID);
        PurchaseOrderDataVector poDataVector = PurchaseOrderDataAccess.select(conn, dbc);
        if(poDataVector.size()==0){
            String errorMessage = "PurchaseOrderData not found.OrderId => "+orderId+" distrErpNum => "+distrErpNum;
            throw new DataNotFoundException(errorMessage);
        }
        if(poDataVector.size()>1){
            String errorMessage = "Multiple PurchaseOrderData.OrderId => "+orderId+" distrErpNum => "+distrErpNum;
            throw new Exception(errorMessage);
        }
        return (PurchaseOrderData) poDataVector.get(0);
    }

    public String getDistributorPoType(int accountId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getDistributorPoType(conn,accountId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }

    }

    private String getDistributorPoType(Connection conn, int accountId) throws Exception {

        String defaultValue= RefCodeNames.DISTR_PO_TYPE.SYSTEM;
        DBCriteria crit=new DBCriteria();
        crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,accountId);
        crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.DISTR_PO_TYPE);
        crit.addEqualTo(PropertyDataAccess.SHORT_DESC,RefCodeNames.PROPERTY_TYPE_CD.DISTR_PO_TYPE);
        PropertyDataVector props = PropertyDataAccess.select(conn, crit);
        if(props.size()>0){
            return ((PropertyData)props.get(0)).getValue();
        }else{
            return defaultValue;
        }
    }

    public String generateOutboundPoNum(int orderId, String distrErpNum) throws RemoteException {
        return generateOutboundPoNum(orderId, distrErpNum, null, null);
    }

    public String generateOutboundPoNum(int orderId, String distrErpNum, String erpPoNum, String erpPoSuffix) throws RemoteException {
        try {

            APIAccess factory = APIAccess.getAPIAccess();

            Order orderEjb = factory.getOrderAPI();
            Distributor distEjb = factory.getDistributorAPI();
            TradingPartner tpEjb = factory.getTradingPartnerAPI();
            Store storeEjb = factory.getStoreAPI();

            logInfo("generateOutboundPoNum => begin");
            OrderData orderData = orderEjb.getOrder(orderId);

            String distrPoType = getDistributorPoType(orderData.getAccountId());

            String customerPoNum = null;
            OrderPropertyDataVector orderProps = orderEjb.getOrderPropertyCollection(orderId, RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM);
            if (orderProps.size() > 1) {
                throw new Exception("Multiple order property.ORDER_PROPERTY_TYPE_CD => " + RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM);
            } else if (orderProps.size() > 0) {
                customerPoNum = ((OrderPropertyData) orderProps.get(0)).getValue();
            }

            DistributorData distributor = distEjb.getDistributorByErpNum(distrErpNum);
            TradingPartnerData partner = tpEjb.getTradingPartnerByBusEntity(distributor.getBusEntity().getBusEntityId());
            if (erpPoNum == null) {
                PurchaseOrderData po = getPurchaseOrder(orderId, distrErpNum, true);
                erpPoNum = po.getErpPoNum();
                erpPoSuffix = Utility.getErpPoSuffix(erpPoNum);
            }

            StoreData store = storeEjb.getStore(orderData.getStoreId());

            String poOut = Utility.getOutboundPoNum(store.getStoreType().getValue(),
                    distrPoType,
                    partner.getPoNumberType(),
                    erpPoNum,
                    erpPoSuffix,
                    customerPoNum,
                    orderData.getRequestPoNum(),
                    orderData.getRefOrderNum(),
                    String.valueOf(orderData.getErpOrderNum()));
            if(!Utility.isSet(poOut)){
                String problems = Utility.analyseOutboundPONumProblem(store.getStoreType().getValue(),
                        distrPoType,
                        partner.getPoNumberType(),
                        erpPoNum,
                        erpPoSuffix,
                        customerPoNum,
                        orderData.getRequestPoNum(),
                        orderData.getRefOrderNum(),
                        String.valueOf(orderData.getErpOrderNum()));
                if(Utility.isSet(problems)) {
                    throw new Exception(problems);
                } else {
                    throw new Exception("Outbound PO Num is not set.");
                }
            }
            return poOut;
        } catch (Exception e) {
        	throw processException(e);
        }
    }

    public IdVector getOrdersWithoutOutboundPoNum(String pBegDate, String pEndDate)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            String sql =
            "SELECT po.purchase_order_id "+
            " FROM clw_purchase_order po, clw_order o "+
            " WHERE o.order_id = po.order_id  "+
            " AND o.add_date > to_date('"+pBegDate+"','mm/dd/yyyy') "+
            " AND o.add_date < (to_date('"+pEndDate+"','mm/dd/yyyy')+1) "+
            " AND po.outbound_po_num IS NULL "+
            " AND po.purchase_order_status_cd IS NOT NULL "+
            " AND po.purchase_order_status_cd != '"+RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED+"'";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            IdVector poIdV = new IdVector();
            while (rs.next()) {
                int poId = rs.getInt("purchase_order_id");
                poIdV.add(new Integer(poId));
            }
            return poIdV;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (Exception exc) {}
            }
        }
    }

  public void setOutboundPoNum(int pPurchaseOrderId, String pUserName)
  throws RemoteException
  {
    Connection conn = null;
    try {
      //Get OrderData object
      conn = getConnection();
      PurchaseOrderData poD = PurchaseOrderDataAccess.select(conn,pPurchaseOrderId);
      int orderId = poD.getOrderId();
      OrderData orderD = OrderDataAccess.select(conn,orderId);
      String erpSystemCd = orderD.getErpSystemCd();
      String statusCd = orderD.getOrderStatusCd();
      String  orderNum = orderD.getOrderNum();


      //get order items
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(OrderItemDataAccess.ORDER_ID,orderId);
      crit.addEqualTo(OrderItemDataAccess.PURCHASE_ORDER_ID,pPurchaseOrderId);
      crit.addIsNotNull(OrderItemDataAccess.ORDER_ITEM_STATUS_CD);

      OrderItemDataVector orderItemDV = OrderItemDataAccess.select(conn,crit);



      ArrayList notes = new ArrayList();

      //recalculate existing pos
      String erpPoNum = poD.getErpPoNum();
      String erpPoSuffix = Utility.getErpPoSuffix(erpPoNum);
      String distErpNum = poD.getDistErpNum();
      String poStatus = poD.getPurchaseOrderStatusCd();


      //Create new purchase orders
      if(!Utility.isSet(distErpNum)) {
          String note = "Po item does not have a distributor. Purchase order id = "+pPurchaseOrderId;
          return;
      }

      String outboundErpPoNum = generateOutboundPoNum(orderId, distErpNum,erpPoNum,erpPoSuffix);
      poD.setOutboundPoNum(outboundErpPoNum);
      poD.setModBy(pUserName);
      PurchaseOrderDataAccess.update(conn,poD);
      for(Iterator iter = orderItemDV.iterator(); iter.hasNext(); ) {
          OrderItemData oiD = (OrderItemData) iter.next();
          oiD.setOutboundPoNum(outboundErpPoNum);
          oiD.setModBy(pUserName);
          OrderItemDataAccess.update(conn,oiD);
      }

    }catch (Exception exc) {
       exc.printStackTrace();
       throw new RemoteException(exc.getMessage());
    }finally {
        closeConnection(conn);
    }
  }
  
  // Find Business Entity Id off the invoice_dist_id (SVC)
  public int getBusEntityIdOffInvoiceDistId(int pInvoiceDistId)
  throws RemoteException { 
	  
	    int storeId = 0; // initialize to 0; when returned check for 0
	    Connection conn = null;
	    try {
	      //Get InvoiceData object
	      conn = getConnection();
	      InvoiceDistData idd = InvoiceDistDataAccess.select(conn, pInvoiceDistId);
	      storeId = idd.getStoreId(); // I'm really interested in soreId here
	    } catch (Exception exc) {
	        exc.printStackTrace();
	        throw new RemoteException(exc.getMessage());
	    } finally {
	         closeConnection(conn);
	    }
	    return storeId;
  }
  
  // update Invoice Status Code (SVC)
  // params: Invoice Dist Id, Invoice Status Code
  public void updateInvoiceStatusCd(int pInvoiceDistId, String invoiceStatusCd) throws RemoteException {
	    Connection conn = null;
	    try {
	      conn = getConnection();
	      
	      //Get InvoiceData object
	      InvoiceDistData i_d_d = InvoiceDistDataAccess.select(conn, pInvoiceDistId);
	      
	      //Set invoice_status_cd and store it in the DB
	      i_d_d.setInvoiceStatusCd(invoiceStatusCd);		      
	      InvoiceDistDataAccess.update(conn, i_d_d);
	    } catch (Exception exc) {
	        exc.printStackTrace();
	        throw new RemoteException(exc.getMessage());
	    } finally {
	         closeConnection(conn);
	    }
  }
  
  // Find ERP Account Number(s) (SHORT_DESC) for a specific invoice
  // we need them for the Store(s),
  // that requires Vendor Invoice ERP Account Number
  // for usage on the Invoice Detail Screen
  public ArrayList fetchErpAccountNum(int pInvoiceDistId) throws RemoteException {
	    Connection conn = null;
	    try {
	      conn = getConnection();
	      
	      ArrayList distErpAccountNum = new ArrayList();
	      //construct SQL for DB query
	       String sql =
	    	   "select distinct cat.short_desc" +
	    	   " from clw_catalog cat, clw_CATALOG_ASSOC cata, clw_order o, clw_invoice_dist id" +
	    	   ", CLW_COST_CENTER_ASSOC cca where" +
	           " cat.catalog_type_cd = 'ACCOUNT'" +
	           " and cata.CATALOG_ID = cat.CATALOG_ID" + 
	           " and cata.BUS_ENTITY_ID = o.account_id" + 
	           " and o.order_id = id.order_id" +
	           " and cca.CATALOG_ID = cat.CATALOG_ID" + 
	           " and id.invoice_dist_id = " + pInvoiceDistId;

	           log.info("***************SVC: fetchErpAccountNum SQL = " + sql);
	           
	       PreparedStatement pstmt = conn.prepareStatement(sql);
	       ResultSet rs = pstmt.executeQuery();
	       while (rs.next()) {
	                String short_desc = rs.getString(1);
	                distErpAccountNum.add(short_desc);
	        }
	        return distErpAccountNum;
	        
	    } catch (Exception exc) {
	        exc.printStackTrace();
	        throw new RemoteException(exc.getMessage());
	    } finally {
	         closeConnection(conn);
	    }
}
      
}
