package com.cleanwise.view.utils;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.avalara.avatax.services.tax.ArrayOfMessage;
import com.avalara.avatax.services.tax.Message;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.ShoppingServicesBean.ServiceFeeDetail;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.PhysicalInventoryPeriodArray;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.TaxUtilAvalara.AvalaraTaxEntry;
import com.cleanwise.service.api.util.TaxUtilAvalara.AvalaraTaxItem;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.ConsolidatedCartView;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingCartServiceData;
import com.cleanwise.service.api.value.ShoppingCartServiceDataVector;
import com.cleanwise.service.api.value.ShoppingItemRequest;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDeliveryData;
import com.cleanwise.service.api.value.SiteDeliveryDataVector;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TaxQuery;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.ShoppingForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;

//packages needed for using Avatax software from Avalara: End

public final class ShopTool {
	static final Category log = Category.getInstance(ShopTool.class);
    public static final BigDecimal ZERO = new BigDecimal(0);

    public static int getCurrentSiteId
        (HttpServletRequest request) {
        SiteData sd = getCurrentSite(request);
        if ( null == sd ) return 0;
        return sd.getBusEntity().getBusEntityId();
    }

    public static SiteData getCurrentSite
        (HttpServletRequest request) {
        CleanwiseUser u = getCurrentUser(request);
        if ( null == u || u.getSite() == null ) {
            return null;
        }

        return u.getSite();
    }

        public static String getRushOrderCharge(HttpServletRequest request) {

            if (isInventoryShoppingOn(request) == false) {
                log.debug("ShopTool.getRushOrderCharge " +
                " isInventoryShoppingOn = false " );
                return "";
            }

            CleanwiseUser u = getCurrentUser(request);
            if ( null == u || u.getUserAccount() == null ) {
                log.debug("ShopTool.getRushOrderCharge " +
                " getUserAccount = null " );
                return "";
            }
            String s = u.getUserAccount().getRushOrderCharge();
            log.debug("ShopTool.getRushOrderCharge " +
                " ret = (" + s + ")" +
                " \n\nacctinfo=" + u.getUserAccount() );
            return s;
    }

    public static boolean isInventoryShoppingOn(HttpServletRequest request) {
        CleanwiseUser u = getCurrentUser(request);
        if ( null == u || u.getSite() == null ) {
            return false;
        }
        return  u.getSite().hasInventoryShoppingOn();
    }

    public static int getInventoryParValue
        (ArrayList pInvItems, int pItemId) {
        for ( int i = 0; pInvItems != null && i < pInvItems.size(); i++ ) {
            SiteInventoryInfoView siiv =
                (SiteInventoryInfoView)pInvItems.get(i);
            if ( siiv.getItemId() == pItemId) {
                return siiv.getParValue();
            }
        }
        return 0;
    }

    public static java.util.ArrayList getInventoryItems(HttpServletRequest request) {
        CleanwiseUser u = getCurrentUser(request);
        if ( null == u || u.getSite() == null
        || u.getSite().getSiteInventory() == null ) {
            return new ArrayList();
        }
        return  u.getSite().getSiteInventory();
    }

    public static void setInventoryItems(HttpServletRequest request,
    ArrayList v ) {
        CleanwiseUser u = getCurrentUser(request);
        if ( null == u || u.getSite() == null ) {
            return;
        }
        u.getSite().setSiteInventory(v);

        // Override the data in the current shopping cart.
        getCurrentShoppingCart(request).setInventoryOrderQty(v);
        getCurrentShoppingCart(request).setSite(u.getSite());
    }

    public static SiteInventoryInfoView getInventoryItem
        (HttpServletRequest request, int pItemId) {

        java.util.ArrayList v = getInventoryItems(request);
        for ( int i = 0; v != null && i < v.size(); i++ ) {
            SiteInventoryInfoView invitem = (SiteInventoryInfoView)v.get(i);
            if ( invitem.getItemId() == pItemId ) {
                return invitem;
            }
        }
        return SiteInventoryInfoView.createValue();
    }

        public static boolean showScheduledDelivery(HttpServletRequest request) {

            CleanwiseUser u = getCurrentUser(request);
            if ( null == u || u.getUserAccount() == null ) {
                return false;
            }
            AccountData accuntD = u.getUserAccount();
            String v = accuntD.getPropertyValue
                (RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
            //log.debug("AccountData.showScheduledDelivery =" + v);
            if(v!=null && v.length()>0 &&
               "T".equalsIgnoreCase(v.substring(0,1))){
                SiteData siteData = u.getSite();
                if(siteData.getInventoryShopping() != null && "on".equalsIgnoreCase(siteData.getInventoryShopping().getValue())){
                    return true;
                }
            }
            String showDistDeliveryDateS =
              accuntD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE);
            if(Utility.isTrue(showDistDeliveryDateS,false)) {
              return true;
            }
            return false;
    }

    public static ShoppingCartData getCurrentShoppingCart
        (HttpServletRequest request) {
        return getCurrentShoppingCart(request.getSession());
    }

    public static ShoppingCartData getCurrentShoppingCart
        (HttpSession session) {

         ShoppingCartData scd = (ShoppingCartData)
            session.getAttribute
            (Constants.SHOPPING_CART);

         if ( scd == null)        {
             log.debug(" -- ShopTool, reset cart. ---");
             scd = new ShoppingCartData();
             scd.setUser(getCurrentUser(session).getUser());
         }
         return scd;
    }
    public static void setCurrentShoppingCart
        (HttpSession session, ShoppingCartData pCart) {

        session.setAttribute
            (Constants.SHOPPING_CART, pCart);
    }

    public static CleanwiseUser getCurrentUser
        (HttpServletRequest request) {
        return getCurrentUser(request.getSession());
    }

    public static CleanwiseUser getCurrentUser
        (HttpSession session) {
        CleanwiseUser u = (CleanwiseUser)
            session.getAttribute(Constants.APP_USER);
        return u;
    }

    public static ActionErrors updateShoppingCartQty(HttpServletRequest request, List pCartLines) {

        log.info("updateShoppingCartQty()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        if (pCartLines == null || pCartLines.size() == 0) {
            log.info("updateShoppingCartQty()=> no items");
            return ae;
        }

        ShoppingCartData sd = getCurrentShoppingCart(request.getSession());
        for (Object oCartLine : pCartLines) {
            ShoppingCartItemData n = (ShoppingCartItemData) oCartLine;
            log.debug("updateShoppingCartQty()=> item: " + n.getActualSkuNum() + ", qty: " + n.getQuantity());
            sd.updateItemQuantity(n);
        }

        log.info("updateShoppingCartQty()=> END.");

        return ae;
    }

    public static ActionErrors calcInputQuantities(HttpServletRequest request, java.util.List pCartLines) {

        log.info("calcInputQuantities()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        if (pCartLines == null || pCartLines.size() == 0) {
            log.info("updateShoppingCartQty()=> no items");
            return ae;
        }

        List<String> msgList = new ArrayList<String>();
        for (Object oCartLine : pCartLines) {
            ShoppingCartItemData n = (ShoppingCartItemData) oCartLine;
            String qtyStr = n.getQuantityString();
            log.debug("updateShoppingCartQty()=> item: " + n.getActualSkuNum() + ", qtyStr: " + qtyStr);
            try {
                n.setQuantity(Integer.parseInt(qtyStr));
            } catch (NumberFormatException exc) {
                msgList.add(n.getActualSkuNum());
            }
        }

        if (msgList.size() > 0 && !msgList.isEmpty()) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(msgList);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        log.info("calcInputQuantities()=> END.");

        return ae;
    }

    public static void initShoppingForm(HttpSession pSession)
    {
        ShoppingCartForm scf = (ShoppingCartForm)
            pSession.getAttribute(Constants.SHOPPING_CART_FORM);
        if ( null != scf )
        {
            ShoppingCartData shoppingCartD =
                getCurrentShoppingCart(pSession);
            scf.setShoppingCart(shoppingCartD);
            scf.setCartItems(shoppingCartD.getItems());
            scf.setCartServices(shoppingCartD.getServices());
        }
    }


     public static boolean isResaleItemsAllowed(HttpServletRequest request) {
            CleanwiseUser u = getCurrentUser(request);
            if ( null == u || u.getUserAccount() == null ) {
                return false;
            }
            return u.getUserAccount().isAuthorizedForResale();
    }

    public static boolean isOrcaAccess(HttpSession pSession) {
        if (pSession != null) {
            Object orcaAccess = pSession.getAttribute(Constants.ORCA_ACCESS);
            return orcaAccess != null && Utility.isTrue(String.valueOf(orcaAccess));
        } else {
            return false;
        }
    }

    /** Force the status strings to be one of the known allowed values.  There are certain status codes that are hidden from the shopper
     * These would be filtered out and replaced.  Addtionally there are status codes that are not "real".  That is to say an order will
     * display as "Shipped" when all items have been shipped.  This is not a status that is stored in the database.
     * @param the OrderJoinData to get the status from.
     */
    public static String xlateStatus(OrderJoinData orderJoin) {
    	return xlateStatus(orderJoin.getOrder().getOrderStatusCd(), false, orderJoin.isReceived(), false);
    }


    /** Force the status strings to be one of the known allowed values.  There are certain status codes that are hidden from the shopper
     * These would be filtered out and replaced.  Addtionally there are status codes that are not "real".  That is to say an order will
     * display as "Shipped" when all items have been shipped.  This is not a status that is stored in the database.
     * This version of the method will additionally localize the returned order status code.
     * @param the OrderJoinData to get the status from.
     * @param the HttpServletRequest making the request.  This is used to perform the localization.
     */
    public static String xlateStatus(OrderJoinData orderJoin, HttpServletRequest request) {
    	String status = xlateStatus(orderJoin.getOrder().getOrderStatusCd(), false, orderJoin.isReceived(), false);
    	return i18nStatus(status, request);
    }

    /** Force the status strings to be one of the known allowed values.  There are certain status codes that are hidden from the shopper
     * These would be filtered out and replaced.  Addtionally there are status codes that are not "real".  That is to say an order will
     * display as "Shipped" when all items have been shipped.  This is not a status that is stored in the database.
     * @param the OrderStatusDescData to get the status from.
     */
    public static String xlateStatus(OrderStatusDescData orderStatus) {
        return xlateStatus(orderStatus.getOrderDetail().getOrderStatusCd(), orderStatus.isShipped(), orderStatus.isReceived(), orderStatus.isAckOnHold());
    }


    /** Force the status strings to be one of the known allowed values.  There are certain status codes that are hidden from the shopper
     * These would be filtered out and replaced.  Addtionally there are status codes that are not "real".  That is to say an order will
     * display as "Shipped" when all items have been shipped.  This is not a status that is stored in the database.
     * This version of the method will additionally localize the returned order status code.
     * @param the OrderStatusDescData to get the status from.
     * @param the HttpServletRequest making the request.  This is used to perform the localization.
     */
    public static String xlateStatus(OrderStatusDescData orderStatus, HttpServletRequest request,String prefix) {
        String status = xlateStatus(orderStatus.getOrderDetail().getOrderStatusCd(), orderStatus.isShipped(), orderStatus.isReceived(), orderStatus.isAckOnHold());

        return i18nStatus(prefix,status, request);
    }


    /** Force the status strings to be one of the known allowed values.  There are certain status codes that are hidden from the shopper
     * These would be filtered out and replaced.  Addtionally there are status codes that are not "real".  That is to say an order will
     * display as "Shipped" when all items have been shipped.  This is not a status that is stored in the database.
     * This version of the method will additionally localize the returned order status code.
     * @param the OrderStatusDescData to get the status from.
     * @param the HttpServletRequest making the request.  This is used to perform the localization.
     */
    public static String xlateStatus(OrderStatusDescData orderStatus, HttpServletRequest request) {
        return xlateStatus(orderStatus,request,null);
    }

    /**
     * Actually does the work of translating the scrubbed order status code.
     * @param status the scrubbed order status code
     * @param request used for EJB access
     * @return the translated status code
     */
    public static String i18nStatus(String status, HttpServletRequest request){
        return i18nStatus(null,status,request);
    }
    /**
     * Actually does the work of translating the scrubbed order status code.
     * @param status the scrubbed order status code
     * @param prefix prefix
     * @param request used for EJB access
     * @return the translated status code
     */
    public static String i18nStatus(String prefix, String status, HttpServletRequest request) {
        log.debug("i18nStatus.for status: " + status);
        try {
            RefCdDataVector orderStatusCodes = CachedViewDataManager.getRefCdDataVector("ORDER_STATUS_CD");
            if (orderStatusCodes != null) {
                log.debug("got orderStatusCodes.size=" + orderStatusCodes.size());
            } else {
                log.debug("got orderStatusCodes but was null");
            }
            if (status != null && orderStatusCodes != null) {
                log.debug("status and orderStatusCodes not null, continuing...");
                Iterator it = orderStatusCodes.iterator();
                while (it.hasNext()) {
                    RefCdData rc = (RefCdData) it.next();
                    log.debug("looping through RefCdData: " + rc.getValue());
                    log.debug("checking if [" + rc.getValue() + "]=[" + status + "]");
                    if (rc.getValue() != null && rc.getValue().equals(status)) {
                        String i18nStatus;
                        String messCd = "refcode." + rc.getRefCd() + "." + rc.getShortDesc();
                        log.debug("prefix =>" + prefix);
                        if (Utility.isSet(prefix)) {
                            i18nStatus = ClwI18nUtil.getMessageOrNull(request, prefix + "." + messCd);
                            if (!Utility.isSet(i18nStatus)) {
                                i18nStatus = ClwI18nUtil.getMessage(request, messCd, null);
                            }
                        } else {
                            i18nStatus = ClwI18nUtil.getMessage(request, messCd, null);
                        }
                        log.debug("Translated status [" + status + "] to [" + i18nStatus + "]");
                        if (Utility.isSet(i18nStatus)) {
                            log.debug("i18nStatus was not null, returning: " + i18nStatus);
                            return i18nStatus;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.fatal("i18nStatus. Error during i18n", e);
        }
        return status;
    }

    /**
     * Actually does the work of scrubbing the order status code.
     * @param status the order status code
     * @param boolean wheather the order is entierly shipped
     * @param boolean received the order was recieved against
     * @return the translated status code
     */
    private static String xlateStatus(String status, boolean shipped, boolean received, boolean onhold){
        if (status.equals(RefCodeNames.ORDER_STATUS_CD.CANCELLED) ||
      status.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED) ||
      status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
            status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE) ||
            status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
            status.equals(RefCodeNames.ORDER_STATUS_CD.INVOICED)
      ) {
      return status;
  }
        if(status.equals(RefCodeNames.ORDER_STATUS_CD.RECEIVED) ||
           status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW) ||
           status.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW)) {
                return RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING;
        }
        if(status.equals(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED)) {
                return RefCodeNames.ORDER_STATUS_CD.CANCELLED;
        }
        if(shipped){
            return RefCodeNames.ORDER_STATUS_CD.SHIPPED;
        }
        if(received){
        	return RefCodeNames.ORDER_STATUS_CD.SHIPMENT_RECEIVED;
        }if(onhold){
        	return RefCodeNames.ORDER_STATUS_CD.ON_HOLD;
        }
        return RefCodeNames.ORDER_STATUS_CD.ORDERED;
    }

    public static List rxlateOrderStatusXpedx(String status) {

        ArrayList list = new ArrayList();

        if (RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING.equals(status)) {

            list.add(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW);
            list.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
            list.add(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM);
            list.add(RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS);
            list.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);

            return list;

        } else if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(status)) {

            list.add(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED);
            list.add(RefCodeNames.ORDER_STATUS_CD.CANCELLED);

            return list;

        } else if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(status)) {

            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            list.add(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);

            return list;
        } else if (RefCodeNames.ORDER_STATUS_CD.SHIPPED.equals(status)) {

            list.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
            list.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);

            return list;
        }



        list.add(status);

        return list;
    }

    public static String doBasicAddressCheck(AddressData pReqAddr, boolean isStateProvinceRequired) {

  String msg = "";
  if ( null == pReqAddr ) {
      //msg += "Address is missing ";
      msg = "shop.checkout.error.addressMissing";

  }
  else if ( pReqAddr.getAddress1() == null ||
       pReqAddr.getAddress1().trim().length() == 0 ){
      //msg += "Address1 is missing ";
      msg = "shop.checkout.error.address1Missing";
  }
  else if ( pReqAddr.getPostalCode() == null ||
      pReqAddr.getPostalCode().trim().length() < 5 ) {
      //msg += "ZIP code error ";
      msg = "shop.checkout.error.zip";
  }
  else if ( isStateProvinceRequired &&
            (pReqAddr.getStateProvinceCd() == null ||
            pReqAddr.getStateProvinceCd().trim().length()<2) ) {
      //msg += "State value error ";
      msg = "shop.checkout.error.state";
  }
  else if ( pReqAddr.getCity() == null ||
      pReqAddr.getCity().trim().length() == 0 ) {
      //msg += "City value error ";
      msg = "shop.checkout.error.city";
  }

  if (msg.length() == 0 ) {
      log.debug(" no address error for pReqAddr="
             + pReqAddr);
      return null;
  }

  log.debug(" address error for pReqAddr="
         + pReqAddr
         + " msg=" + msg
         );
  return msg;
    }

    public static boolean isAnOCISession(HttpSession pSession) {
  if ( pSession.getAttribute(Constants.OCI_HOOK_URL) != null ) {
      return true;
  }
  return false;
    }

    public static boolean canSaveShoppingCart(HttpSession pSession) {
  return ! isAnOCISession(pSession);
    }

    public static String getCheckoutFormUrl(HttpServletRequest request) {
  return getCheckoutFormUrl(request.getSession());
    }
    public static String getCheckoutFormTarget(HttpServletRequest request) {
  String target = (String)
      request.getSession().getAttribute(Constants.OCI_TARGET);
  if ( null == target ) {
      target="#";
  }
  return target;

    }

    public static String getCheckoutFormUrl(HttpSession pSession) {
  if ( null == pSession.getAttribute(Constants.OCI_HOOK_URL) ) {
      return null;
  }
  return (String)pSession.getAttribute(Constants.OCI_HOOK_URL);
    }


    /**
     *Returns the sku nmber for display in the runtime based off the users store type for a given order item
     */
    public static String getRuntimeSku(OrderItemData pOrderItemData,HttpServletRequest request){
        return getRuntimeSku(pOrderItemData,request.getSession());
    }

    /**
     *Returns the sku nmber for display in the runtime based off the users store type for a given order item
     */
    public static String getRuntimeSku(OrderItemJoinData pOrderItemJoinData,HttpServletRequest request){
        return getRuntimeSku(pOrderItemJoinData.getOrderItem(),request.getSession());
    }

    /**
     *Returns the sku nmber for display in the runtime based off the users store type for a given order item
     */
    public static String getRuntimeSku(OrderItemData pOrderItemData,HttpSession pSession){
        CleanwiseUser appUser = (CleanwiseUser) pSession.getAttribute(Constants.APP_USER);
        String storeType = null;
        if(appUser.getUserStore().getStoreType() != null){
            storeType = appUser.getUserStore().getStoreType().getValue();
        }

        return Utility.getActualSkuNumber(storeType, pOrderItemData);
    }

    /**
     *Figures out what the total budgeted amount for this cart is.  Includes freight if applicable and tax depending on the information
     *available.
     */
    public static BigDecimal getCartBudgetTotal(ActionForm pForm, HttpServletRequest request)
    throws APIServiceAccessException, RemoteException{
    	log.info("AAAA: Before invokation of getCartBudgetTotal() method => pForm = " + pForm);
        return getCartBudgetTotal(0,pForm, request);
    }


    /**
    *Figures out what the total budgeted amount for this cart is. Includes freight if applicable and tax depending on the information
    *available.
    */
    public static BigDecimal getCartBudgetTotal(int pOptionalCostCenterId, ActionForm pForm, HttpServletRequest request)
    throws APIServiceAccessException, RemoteException{

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData site = appUser.getSite();
        BigDecimal subTotal = null;
        BigDecimal tax = null;
        BigDecimal freight = null;
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Order orderEjb = factory.getOrderAPI();
        Site  siteEjb  = factory.getSiteAPI();

        Distributor distributorEjb = factory.getDistributorAPI();
        Catalog catalogEjb = factory.getCatalogAPI();

        CostCenterData costCenterData = null;
        if(pOptionalCostCenterId != 0){
            CostCenterDataVector ccdv = getAllCostCenters(request);
            Iterator it = ccdv.iterator();
            while(it.hasNext()){
                CostCenterData aCc = (CostCenterData) it.next();
                if(aCc.getCostCenterId() == pOptionalCostCenterId){
                    costCenterData = aCc;
                }
            }
        }


        if(pForm instanceof ShoppingCartForm){
            ShoppingCartForm form =(ShoppingCartForm) pForm;

            subTotal = ((ShoppingCartForm)pForm).getCartItemsAmt(pOptionalCostCenterId,true,request);

        }else if(pForm instanceof CheckoutForm){
            CheckoutForm sForm = (CheckoutForm) pForm;
            subTotal = sForm.getCartItemsAmt(pOptionalCostCenterId,true,request);
            freight = Utility.addAmt(sForm.getFreightAmt(),sForm.getHandlingAmt());
        }else{
            throw new RuntimeException("Unknown form class.");
        }

        if (subTotal.doubleValue() <= 0.000001 
        	&& costCenterData != null && !RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenterData.getCostCenterTaxType())
        	) {
        	return ZERO;
        }	
        //get sales tax information
        if(pForm instanceof ShoppingForm){
            ShoppingForm sForm = (ShoppingForm) pForm;
            if(costCenterData == null) {

            	log.info("BBBB: getCartBudgetTotal() method => sForm = " + sForm);
            	log.info("CCCC: getCartBudgetTotal() method =>  sForm.getItems() = " + sForm.getItems());

            	tax = getSalesTaxAvalara(orderEjb,siteEjb,distributorEjb,appUser.getUserStore(),appUser.getUserAccount(),appUser.getSite(), sForm.getItems(), catalogEjb);
            } else if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenterData.getCostCenterTaxType())){
                ShoppingCartItemDataVector cartItems = sForm.getItems();
                ShoppingCartItemDataVector cartCostCenterItems = new ShoppingCartItemDataVector();
                if(cartItems!=null) {
                    for(Iterator iter = cartItems.iterator(); iter.hasNext();) {
                        ShoppingCartItemData item = (ShoppingCartItemData) iter.next();
                        if(item.getProduct().getCostCenterId()==pOptionalCostCenterId) {
                            cartCostCenterItems.add(item);
                        }
                    }
                }
                tax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, appUser.getUserStore(),appUser.getUserAccount(),appUser.getSite(), cartCostCenterItems, catalogEjb);
            }
        }

        if(subTotal == null){subTotal = ZERO;}
        if(tax == null){tax = ZERO;}
        if(freight == null){freight = ZERO;}


        //if this is a master tax cost center go through and find a complete subtotal for tax
        if(costCenterData != null && RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenterData.getCostCenterTaxType())){
            if(pForm instanceof ShoppingForm){
                ShoppingForm sForm = (ShoppingForm) pForm;
                tax = getSalesTaxAvalara(orderEjb,siteEjb,distributorEjb,appUser.getUserStore(),appUser.getUserAccount(),appUser.getSite(), sForm.getItems(), catalogEjb);
                log.debug("tax call 2: "+tax);
            }
        }

        BigDecimal budgetedTotal;
        if(costCenterData == null ||
                RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenterData.getCostCenterTaxType()) ||
                RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenterData.getCostCenterTaxType())){
            budgetedTotal = Utility.addAmt(subTotal,tax);
        }else{
            budgetedTotal = subTotal;
        }

        //only add freight if site is configured to a cost center that will collect this amount
        if(costCenterData == null && site.isAllocateFreightToBudget()){
            budgetedTotal = Utility.addAmt(budgetedTotal,freight);
        }
        if(costCenterData != null && Utility.isTrue(costCenterData.getAllocateFreight())){
            budgetedTotal = Utility.addAmt(budgetedTotal,freight);
        }
        return budgetedTotal;
    }


    public static void saveBudgetSessionInfoForCurrentDiscretionaryCart(HttpServletRequest request)
    throws APIServiceAccessException, RemoteException{
    	HttpSession session = request.getSession();
    	ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData site = appUser.getSite();
        CostCenterDataVector ccdv = getAllCostCenters(request);
        Iterator it = ccdv.iterator();
        while(it.hasNext()){
            CostCenterData aCc = (CostCenterData) it.next();
            if(!site.isBudgetUnlimited(aCc.getCostCenterId())) {//STJ - 4828
	            BigDecimal amt = getActualCartBudgetTotal(aCc.getCostCenterId(),request);
	            BudgetSpendView bsv = BudgetSpendView.createValue();
	            bsv.setAmountSpent(amt);
	            bsv.setCostCenterId(aCc.getCostCenterId());
	            site.addSessionBudgetSpendViewVector(bsv);
            }
        }
    }

    public static BigDecimal getActualCartBudgetTotal(int pOptionalCostCenterId, HttpServletRequest request)
    throws APIServiceAccessException, RemoteException{

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData site = appUser.getSite();
        BigDecimal subTotal = null;
        BigDecimal tax = null;
        BigDecimal freight = null;
        BigDecimal handlingAmt = null;
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Order orderEjb = factory.getOrderAPI();
        Site  siteEjb  = factory.getSiteAPI();

        Distributor distributorEjb = factory.getDistributorAPI();
        Catalog catalogEjb = factory.getCatalogAPI();

        ShoppingServices shopEjb = factory.getShoppingServicesAPI();
        CostCenterData costCenterData = null;
        if(pOptionalCostCenterId != 0){
            CostCenterDataVector ccdv = getAllCostCenters(request);
            Iterator it = ccdv.iterator();
            while(it.hasNext()){
                CostCenterData aCc = (CostCenterData) it.next();
                if(aCc.getCostCenterId() == pOptionalCostCenterId){
                    costCenterData = aCc;
                }
            }
        }

        //get actual cart
        ShoppingCartData shoppingCart =
                (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
        if(hasDiscretionaryCartAccessOpen(request) || shoppingCart == null) {
            shoppingCart =
                 (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
        }

        ShoppingCartItemDataVector cartItems  = shoppingCart.getItems();
////////////////////
 // private double getItemsAmt(Boolean pOptTaxable, int pOptionalCostCenterId, boolean budgetedOnly,HttpServletRequest request) {
        boolean budgetedOnly = true;
        double cartAmtDb = 0;
        CostCenterDataVector ccdv;
        if(request == null){
        	ccdv = new CostCenterDataVector();
        }else{
        	ccdv = getAllCostCenters(request);
        }

        for(int ii=0; ii<cartItems.size(); ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
            if(!sciD.getDuplicateFlag()) {
                if(true){
                    if(pOptionalCostCenterId == 0 || pOptionalCostCenterId == sciD.getProduct().getCostCenterId()){

                    	if(!budgetedOnly ||  sciD.getProduct().getCostCenterId() != 0){
                    			if(!budgetedOnly){
                    				cartAmtDb += sciD.getAmount();
                    				continue;
                    			}
                    			int ccid = sciD. getProduct().getCostCenterId();

                    			for(int i=0; i<ccdv.size();i++){
                    				CostCenterData ccd = (CostCenterData)ccdv.get(i);
                    				if(ccd.getCostCenterId() == ccid ){
                    					if(!Utility.isTrue(ccd.getNoBudget())){
                    						cartAmtDb += sciD.getAmount();
                    					}
                    				}
                    			}

                        }

                    }
                    }
                }
            }

////////////////////


        if (cartItems.size() == 0)
        	return new BigDecimal(0);

        subTotal = new BigDecimal(cartAmtDb);

        String addServiceFee = appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        BigDecimal serviceFee = new BigDecimal(0);
        AccountData acctD = appUser.getUserAccount();
        int accountId=acctD.getAccountId();
        Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
        int contractId = 0;
        if (contractIdI != null) {
          contractId = contractIdI.intValue();
        }
        ActionErrors ae = new ActionErrors();
        ShoppingServices shoppingServEjb = null;
        try {
          shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
          ae.add("error", new ActionError("error.systemError", "No shopping services API Access"));

        }
      //add service fee if applicable
        if(addServiceFee.equals("true") && contractId > 0){
        	try {
        		HashMap serviceFeeDetMap = shoppingServEjb.calculateServiceFee(contractId,cartItems,accountId);

        		if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){

        			//Calculate total service fee
        			for (int s = 0;s < cartItems.size();s++) {
        				ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(s);
        				int qty = sData.getQuantity();
        				Integer item = new Integer(sData.getItemId());
        				int prodCCid = sData.getProduct().getCostCenterId();
        				ServiceFeeDetail finalDet = (ServiceFeeDetail)serviceFeeDetMap.get(item);

        				if(finalDet!=null){
        					if(pOptionalCostCenterId>0 && pOptionalCostCenterId != prodCCid){
        						continue;
        					}
        					BigDecimal amt = finalDet.getAmount();
        					BigDecimal totAmt = amt.multiply(new BigDecimal(qty));
        					serviceFee = serviceFee.add(totAmt);
        				}
        			}

        		}

        		if(serviceFee!=null && serviceFee.compareTo(new BigDecimal(0))>0){
        			subTotal = subTotal.add(serviceFee);
        		}
        	}catch (RemoteException exc) {
        		ae.add("error", new ActionError("error.systemError", exc.getMessage()));
        		exc.printStackTrace();

        	}
        }

        //handling
        boolean addFreightAndHandlingAmt = (costCenterData == null && site.isAllocateFreightToBudget())
        		|| (costCenterData != null && Utility.isTrue(costCenterData.getAllocateFreight()));

        if (addFreightAndHandlingAmt){
        	OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

	        if(cartItems!=null && cartItems.size() > 0){
	        	for (int ii = 0; ii < cartItems.size(); ii++) {
		        	ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
		        	OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
		        	frItem.setItemId(cartItem.getProduct().getProductId());
		        	BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
		        	priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
		        	frItem.setPrice(priceBD);
		        	frItem.setQty(cartItem.getQuantity());
		        	BigDecimal weight = null;
		        	String weightS = cartItem.getProduct().getShipWeight();
		        	try {
		        		weight = new BigDecimal(weightS);
		        	} catch (Exception exc) {}
		        	frItem.setWeight(weight);
		        	frItems.add(frItem);
	        	}
	        	OrderHandlingView frOrder = OrderHandlingView.createValue();
		        frOrder.setTotalHandling(new BigDecimal(0));
		        frOrder.setTotalFreight(new BigDecimal(0));
		        frOrder.setContractId(contractId);
		        frOrder.setAccountId(accountId);
		        frOrder.setSiteId(site.getBusEntity().getBusEntityId());
		        frOrder.setAmount(subTotal);
		        frOrder.setWeight(new BigDecimal(0));
		        frOrder.setItems(frItems);

		        frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);

		        if(frOrder!=null){
		        	handlingAmt = frOrder.getTotalHandling();
		        	freight = frOrder.getTotalFreight();
		        }
	        }
        }

        //get sales tax information
        if(costCenterData == null) {
            tax = getSalesTaxAvalara(orderEjb,siteEjb,distributorEjb,appUser.getUserStore(),appUser.getUserAccount(),appUser.getSite(), cartItems, catalogEjb);
        } else if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenterData.getCostCenterTaxType())){
            ShoppingCartItemDataVector cartCostCenterItems = new ShoppingCartItemDataVector();
            if(cartItems!=null) {
                for(Iterator iter = cartItems.iterator(); iter.hasNext();) {
                    ShoppingCartItemData item = (ShoppingCartItemData) iter.next();
                    if(item.getProduct().getCostCenterId()==pOptionalCostCenterId) {
                        cartCostCenterItems.add(item);
                    }
                }
            }
            tax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, appUser.getUserStore(),appUser.getUserAccount(),appUser.getSite(), cartCostCenterItems, catalogEjb);
        }

        if(subTotal == null){subTotal = ZERO;}
        if(tax == null){tax = ZERO;}
        if(freight == null){freight = ZERO;}
        if(handlingAmt == null){handlingAmt = ZERO;}


        //if this is a master tax cost center go through and find a complete sub total for tax
        if(costCenterData != null && RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenterData.getCostCenterTaxType())){
             tax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, appUser.getUserStore(),appUser.getUserAccount(),appUser.getSite(), cartItems, catalogEjb);
        }

        BigDecimal budgetedTotal;
        if(costCenterData == null ||
                RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenterData.getCostCenterTaxType()) ||
                RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenterData.getCostCenterTaxType())){
            budgetedTotal = Utility.addAmt(subTotal,tax);
        }else{
            budgetedTotal = subTotal;
        }

        //only add freight if site is configured to a cost center that will collect this amount
        if (addFreightAndHandlingAmt){
            budgetedTotal = Utility.addAmt(budgetedTotal,freight);
            budgetedTotal = Utility.addAmt(budgetedTotal,handlingAmt);
        }

        return budgetedTotal;
    }

    /**
     * To calculate total amount for budget.
     * A freight and handling are used at this calculation.
     * A total tax for budget is calculated by items cost centers.
     */
    public static BigDecimal getCartBudgetTotalWithAllCharges(HttpServletRequest request) throws APIServiceAccessException, RemoteException {
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData site = appUser.getSite();
        BigDecimal subTotal = null;
        BigDecimal tax = null;
        BigDecimal freight = null;
        BigDecimal handlingAmt = null;
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Order orderEjb = factory.getOrderAPI();
        Site siteEjb = factory.getSiteAPI();

        Distributor distributorEjb = factory.getDistributorAPI();
        Catalog catalogEjb = factory.getCatalogAPI();

        ShoppingServices shopEjb = factory.getShoppingServicesAPI();
        CostCenterData costCenterData = null;

        //get actual cart
        ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
        if (hasDiscretionaryCartAccessOpen(request) || shoppingCart == null) {
            shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
        }

        ShoppingCartItemDataVector cartItems = shoppingCart.getItems();
        if (cartItems.size() == 0) {
            return new BigDecimal(0);
        }

        CostCenterDataVector ccdv = getAllCostCenters(request);
        HashMap<Integer, ShoppingCartItemDataVector> costCenterItemMap = new HashMap<Integer, ShoppingCartItemDataVector>();
        CostCenterData masterCostCenterData = null;
        double cartAmtDb = 0;

        for (int ii = 0; ii < cartItems.size(); ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData) cartItems.get(ii);
            if (!sciD.getDuplicateFlag()) {
                if (sciD.getProduct().getCostCenterId() != 0) {
                    int ccid = sciD.getProduct().getCostCenterId();
                    for (int i = 0; i < ccdv.size(); i++) {
                        CostCenterData ccd = (CostCenterData)ccdv.get(i);
                        if (ccd.getCostCenterId() == ccid ) {
                            if (!Utility.isTrue(ccd.getNoBudget())) {
                                cartAmtDb += sciD.getAmount();
                                /// Try to add item into 'costCenterItemMap'
                                if (masterCostCenterData == null) {
                                    if (RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(ccd.getCostCenterTaxType())) {
                                        masterCostCenterData = ccd;
                                    } else if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(ccd.getCostCenterTaxType())) {
                                        Integer costCenterIdObj = new Integer(ccid);
                                        if (costCenterItemMap.containsKey(costCenterIdObj)) {
                                            ShoppingCartItemDataVector costCenterItems =
                                                (ShoppingCartItemDataVector)costCenterItemMap.get(costCenterIdObj);
                                            costCenterItems.add(sciD);
                                        } else {
                                            ShoppingCartItemDataVector costCenterItems = new ShoppingCartItemDataVector();
                                            costCenterItems.add(sciD);
                                            costCenterItemMap.put(costCenterIdObj, costCenterItems);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (masterCostCenterData != null) {
            costCenterItemMap.clear();
            Integer costCenterIdObj = new Integer(masterCostCenterData.getCostCenterId());
            costCenterItemMap.put(costCenterIdObj, cartItems);
        }

        subTotal = new BigDecimal(cartAmtDb);

        String addServiceFee = appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        BigDecimal serviceFee = new BigDecimal(0);
        AccountData acctD = appUser.getUserAccount();
        int accountId = acctD.getAccountId();
        Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
        int contractId = 0;
        if (contractIdI != null) {
            contractId = contractIdI.intValue();
        }
        ActionErrors ae = new ActionErrors();
        ShoppingServices shoppingServEjb = null;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping services API Access"));
        }

        //add service fee if applicable
        if (addServiceFee.equals("true") && contractId > 0) {
            try {
                HashMap serviceFeeDetMap = shoppingServEjb.calculateServiceFee(contractId, cartItems, accountId);
                if (serviceFeeDetMap != null && serviceFeeDetMap.size() > 0) {
                    //Calculate total service fee
                    for (int s = 0; s < cartItems.size(); s++) {
                        ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(s);
                        int qty = sData.getQuantity();
                        Integer item = new Integer(sData.getItemId());
                        int prodCCid = sData.getProduct().getCostCenterId();
                        ServiceFeeDetail finalDet = (ServiceFeeDetail)serviceFeeDetMap.get(item);

                        if (finalDet != null) {
                            BigDecimal amt = finalDet.getAmount();
                            BigDecimal totAmt = amt.multiply(new BigDecimal(qty));
                            serviceFee = serviceFee.add(totAmt);
                        }
                    }
                }

                if (serviceFee != null && serviceFee.compareTo(new BigDecimal(0)) > 0) {
                    subTotal = subTotal.add(serviceFee);
                }
            } catch (RemoteException exc) {
                ae.add("error", new ActionError("error.systemError", exc.getMessage()));
                exc.printStackTrace();
            }
        }

        //handling
        boolean addFreightAndHandlingAmt = site.isAllocateFreightToBudget();

        if (addFreightAndHandlingAmt) {
            OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

            if (cartItems != null && cartItems.size() > 0) {
                for (int ii = 0; ii < cartItems.size(); ii++) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
                    OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
                    frItem.setItemId(cartItem.getProduct().getProductId());
                    BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
                    priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
                    frItem.setPrice(priceBD);
                    frItem.setQty(cartItem.getQuantity());
                    BigDecimal weight = null;
                    String weightS = cartItem.getProduct().getShipWeight();
                    try {
                        weight = new BigDecimal(weightS);
                    } catch (Exception exc) {
                    }
                    frItem.setWeight(weight);
                    frItems.add(frItem);
                }
                OrderHandlingView frOrder = OrderHandlingView.createValue();
                frOrder.setTotalHandling(new BigDecimal(0));
                frOrder.setTotalFreight(new BigDecimal(0));
                frOrder.setContractId(contractId);
                frOrder.setAccountId(accountId);
                frOrder.setSiteId(site.getBusEntity().getBusEntityId());
                frOrder.setAmount(subTotal);
                frOrder.setWeight(new BigDecimal(0));
                frOrder.setItems(frItems);

                frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);

                if (frOrder != null){
                    handlingAmt = frOrder.getTotalHandling();
                    freight = frOrder.getTotalFreight();
                }
            }
        }

        /// get sales tax information
        if (tax == null) {
            tax = ZERO;
        }
        {
            Iterator it = costCenterItemMap.keySet().iterator();
            while (it.hasNext()) {
                Integer costCenterIdObj = (Integer)it.next();
                ShoppingCartItemDataVector costCenterItems = (ShoppingCartItemDataVector)costCenterItemMap.get(costCenterIdObj);
                BigDecimal costCenterTax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, appUser.getUserStore(), appUser.getUserAccount(), appUser.getSite(), costCenterItems, catalogEjb);
                tax = Utility.addAmt(tax, costCenterTax);
            }
        }

        if (subTotal == null) {
            subTotal = ZERO;
        }
        if (freight == null) {
            freight = ZERO;
        }
        if (handlingAmt == null) {
            handlingAmt = ZERO;
        }

        BigDecimal budgetedTotal = Utility.addAmt(subTotal, tax);

        //only add freight if site is configured to a cost center that will collect this amount
        if (addFreightAndHandlingAmt) {
            budgetedTotal = Utility.addAmt(budgetedTotal, freight);
            budgetedTotal = Utility.addAmt(budgetedTotal, handlingAmt);
        }

        return budgetedTotal;
    } // getCartBudgetTotalWithAllCharges

    // method getSalesTax() below was not changed for Avatax on purpose:
    // this method is not used (invoked)
    private static BigDecimal getSalesTax(Order pOrderEjb, CleanwiseUser appUser,ShoppingForm sForm, BigDecimal taxableTotal){
        int siteId = appUser.getSite().getSiteId();

        BigDecimal tax = sForm.getCacheCalculatedSalesTaxAmt(siteId,taxableTotal);
        if(tax != null){
            tax = sForm.getCacheCalculatedSalesTaxAmt(siteId,taxableTotal);
            log.debug("getCacheCalculatedSalesTaxAmt: "+tax);
        }else{
            try{
                TaxQuery tq = new TaxQuery(taxableTotal, appUser.getSite(), appUser.getUserAccount(), appUser.getUserStore());
                tax = pOrderEjb.calculateTax(tq); //old code

                sForm.setCacheCalculatedSalesTaxAmt(siteId,tax, taxableTotal);
            }catch (Exception e){
                //for now just print, after this is more mature may do something more meaningful
                e.printStackTrace();
            }
        }
        log.debug("About to return tax of: "+tax);
        return tax;
    }

    /**
     *Returns all of the cost centers for the current shopping account.  This data is then cached in the session.
     *Returns null if no cost centers could be found.
     */
    public static CostCenterDataVector getAllCostCenters(HttpServletRequest request){
        HttpSession session = request.getSession();
        return getAllCostCenters(session);
    }

    public static CostCenterDataVector getAllCostCenters(HttpSession session) {
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int accountId = appUser.getUserAccount().getBusEntity().getBusEntityId();
        return getAllCostCentersForAccount(session, appUser, accountId);
    }

    public static CostCenterDataVector getAllCostCentersForAccount(
                        HttpSession session, CleanwiseUser appUser, int accountId) {
        try {
            String key = "acctCostCenters::"+accountId;
            Map cache = appUser.getCache();
            if (!cache.containsKey(key) ){
                APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
                CostCenterDataVector ccdv = factory.getAccountAPI().getAllCostCenters(accountId, Account.ORDER_BY_NAME);
                cache.put(key, ccdv);
            }
            return (CostCenterDataVector) cache.get(key);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static CostCenterDataVector getSiteCostCenters(HttpServletRequest request) {

        try {

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            SiteData site = ShopTool.getCurrentSite(request);
            if (site == null) {
                log.info("getSiteCostCenters()=> ERROR: Could not get cost centers data. Site is null.");
                return null;
            }

            CostCenterDataVector allCostCenters = getAllCostCenters(session);
            if (allCostCenters == null || allCostCenters.isEmpty()) {
                log.info("getSiteCostCenters()=> ERROR: No available cost centers for site. SiteID: " + site.getSiteId());
                return null;
            }

            Map<String, Object> cache = appUser.getCache();
            String key = "siteCostCenters::" + site.getSiteId();

            if (!cache.containsKey(key)) {

                APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
                CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

                IdVector availableCostCenterIds = Utility.toIdVector(allCostCenters);

                CostCenterDataVector ccdv = catInfEjb.getCatalogCostCenters(site.getSiteCatalogId(), availableCostCenterIds, true);

                cache.put(key, ccdv);
            }

            return (CostCenterDataVector) cache.get(key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static BigDecimal getAllocatedBudgetFor(HttpServletRequest request, CostCenterDataVector pCostCenters) {

        BigDecimal allocatedBudgetAmount = null;
        try {

            SiteData site = ShopTool.getCurrentSite(request);
            if (site == null) {
                log.info("getAllocatedBudgetFor()=> ERROR: Site is null.");
                return null;
            }

            if (pCostCenters != null) {
                for (Object oCostCenter : pCostCenters) {
                    CostCenterData costCenter = (CostCenterData) oCostCenter;
                    allocatedBudgetAmount = Utility.addAmt(allocatedBudgetAmount, site.getBudgetAllocated(costCenter.getCostCenterId()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allocatedBudgetAmount;
    }

    public static String getShowDistInventoryCode (HttpServletRequest request) {
        HttpSession session = request.getSession();
        CleanwiseUser user = (CleanwiseUser)
            session.getAttribute(Constants.APP_USER);
        AccountData accountD = user.getUserAccount();
        String showDistInventory =
            accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY);
        return showDistInventory;
    }

    public static int getDistInventory (HttpServletRequest request, String distSkuNum,
        int distId) {
        HttpSession session = request.getSession();
        CleanwiseUser user = (CleanwiseUser)
            session.getAttribute(Constants.APP_USER);
        SiteData siteD = user.getSite();
        PropertyData propLocCode = siteD.getMiscProp("Location Code");
        String locationCode = "";
        if(propLocCode != null && propLocCode.getValue()!=null) {
            locationCode = propLocCode.getValue();
        }
        DistributorData distributorData;
        try {
      distributorData = SessionTool.getDistributorData(request, distId);
    } catch (Exception e) {
      throw new RuntimeException("Cann't get DistributorData for ID:"
          + distId);
    }
    int distInv = Utility.getDistInventory(locationCode, distSkuNum,
        distributorData);
    return distInv;
    }

    public static boolean isModernInventoryShopping(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return  isModernInventoryShopping(session);
    }

    public static boolean isModernInventoryShopping(HttpSession session) {
        CleanwiseUser u = getCurrentUser(session);
        if ( null == u || u.getSite() == null ) {
            return false;
        }
        log.debug("isModernInvShop "+u.getSite().hasModernInventoryShopping());
        return  u.getSite().hasModernInventoryShopping();
    }

    public static String getOGListUI(HttpServletRequest request, String defaultVal) {
        CleanwiseUser u = getCurrentUser(request);
        if (null == u || u.getSite() == null || u.getUserAccount() == null) {
            return defaultVal;
        }
        AccountData account = u.getUserAccount();
        String propVal = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI);
        if (propVal == null || propVal.trim().length() == 0) {
            return defaultVal;
        }
        return propVal;
    }

    public static String getShopUIType(HttpServletRequest request, String defaultVal) {
        CleanwiseUser u = getCurrentUser(request);
        if (null == u || u.getSite() == null || u.getUserAccount() == null) {
            return defaultVal;
        }
        AccountData account = u.getUserAccount();
        String propVal = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE);
        if (propVal == null || propVal.trim().length() == 0) {
            return defaultVal;
        }
        return propVal;
    }

    public static boolean isModernInventoryCartAvailable(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        return isModernInventoryCartAvailable(session);
    }

    public static boolean isModernInventoryCartAvailable(HttpSession session) {

        CleanwiseUser u = getCurrentUser(session);
        if (null == u || u.getSite() == null) {
            return false;
        }
	    boolean isPhysicalCartAvailable = isPhysicalCartAvailable(session);
		boolean isUsedPhysicalInventoryAlgorithm = isUsedPhysicalInventoryAlgorithm(u);
        boolean usePhysicalInventory = isPhysicalCartAvailable && isUsedPhysicalInventoryAlgorithm;
        if (usePhysicalInventory) {
            if (isPhysicalCartPeriodAvailable(u.getSite().getPhysicalInventoryPeriods())) {
                return u.getSite().isModernInventoryCartAvailableWithoutDatesChecking();
            }
        }
        return u.getSite().isModernInventoryCartAvailable();
    }

    public static boolean isUsedPhysicalInventoryAlgorithm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        return isUsedPhysicalInventoryAlgorithm(session);
    }

    /**
     * Checks if the physical inventory is complaint.  A complaint physical inventory
     * is one where all of the on hand values for all items in the cart are set.
     * @param request
     * @return
     * @throws Exception 
     */
    public static boolean isPhysicalCartCompliant(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        
        CleanwiseUser appUser = getCurrentUser(request);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
        IdVector specialItemIds = shoppingServEjb.getSpecialPermssionItemIds(appUser.getUserAccount().getAccountCatalog().getCatalogId());
        ShoppingCartForm cartForm = (ShoppingCartForm) session.getAttribute("INVENTORY_SHOPPING_CART_FORM");
        ShoppingCartData shoppingCartD = cartForm.getShoppingCart();
        ShoppingCartItemDataVector items = shoppingCartD.getInventoryItemsOnly();
        Iterator<ShoppingCartItemData> it = (Iterator<ShoppingCartItemData>) items.iterator();
        while(it.hasNext()){
        	ShoppingCartItemData item = it.next();
        	if(!Utility.isSet(item.getInventoryQtyOnHandString()) && !specialItemIds.contains(item.getItemId())){
        		return false;
        	}
        }
        return true;
    }

    public static boolean isPhysicalCartAvailable(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        return isPhysicalCartAvailable(session);
    }

    private static boolean isUsedPhysicalInventoryAlgorithm(CleanwiseUser appUser) {
        if (appUser == null) {
            return false;
        }
        if (appUser.getUserAccount() == null) {
            return false;
        }
        boolean usePhysicalInventory = false;
        String usePhysicalInventoryString =
            appUser.getUserAccount().getPropertyValue(
                RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
        if (usePhysicalInventoryString != null) {
            usePhysicalInventory =
                Boolean.parseBoolean(usePhysicalInventoryString);
        }
        if (!usePhysicalInventory) {
            return false;
        }
        /*if (appUser.getSite() == null) {
            return false;
        }
        if (appUser.getSite().getPhysicalInventoryPeriods() == null) {
            return false;
        }
        if (appUser.getSite().getPhysicalInventoryPeriods().size() == 0) {
            return false;
        }*/
        return true;
    }

    public static PhysicalInventoryPeriod getCurrentPhysicalPeriod(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        if (session == null) {
            return null;
        }
        CleanwiseUser u = getCurrentUser(session);
    	ArrayList<PhysicalInventoryPeriod> periods = u.getSite().getPhysicalInventoryPeriods();
        if (periods == null) {
            return null;
        }
        if (periods.size() == 0) {
            return null;
        }
        PhysicalInventoryPeriodArray periodsHelper = new PhysicalInventoryPeriodArray();
        periodsHelper.setPeriods(periods);
        Date currentDate = Constants.getCurrentDate();
        GregorianCalendar templateCalendar = new GregorianCalendar();
        templateCalendar.setTime(currentDate);
        templateCalendar.add(GregorianCalendar.MONTH, -1);
        templateCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        GregorianCalendar currentCalendar = new GregorianCalendar();
        currentCalendar.setTime(currentDate);

        return periodsHelper.getCurrentPhysicalInventoryPeriod(templateCalendar, currentCalendar);
    }


    private static boolean isPhysicalCartPeriodAvailable(ArrayList<PhysicalInventoryPeriod> periods) {
        if (periods == null) {
            return false;
        }
        if (periods.size() == 0) {
            return false;
        }
        PhysicalInventoryPeriodArray periodsHelper = new PhysicalInventoryPeriodArray();
        periodsHelper.setPeriods(periods);
        Date currentDate = Constants.getCurrentDate();
        GregorianCalendar templateCalendar = new GregorianCalendar();
        templateCalendar.setTime(currentDate);
        templateCalendar.add(GregorianCalendar.MONTH, -1);
        templateCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
        GregorianCalendar currentCalendar = new GregorianCalendar();
        currentCalendar.setTime(currentDate);
        return periodsHelper.containsDate(templateCalendar, currentCalendar);
    }

    public static boolean isUsedPhysicalInventoryAlgorithm(HttpSession session) {
        CleanwiseUser appUser =
            (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            return false;
        }
        return isUsedPhysicalInventoryAlgorithm(appUser);
    }

    public static boolean isPhysicalCartAvailable(HttpSession session) {
        CleanwiseUser appUser =
            (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            return false;
        }
        if (!isUsedPhysicalInventoryAlgorithm(appUser)) {
            return false;
        }
        return isPhysicalCartPeriodAvailable(appUser.getSite().getPhysicalInventoryPeriods()) ;
    }

   /*
    * Retrurns true if in scheduled cart and not in physical inventory cart
    */
   public static boolean isScheduledCart(HttpServletRequest request, HttpSession session){
	   if(isPhysicalInventoryCart(request)){
		   return false;
	   }
		return ((ShopTool.isModernInventoryShopping(request)
                && !ShopTool.hasDiscretionaryCartAccessOpen(request)
                && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null
                ) );
	}

   /*
    * Returns true if in Physical inventory period.
    */
   public static boolean isPhysicalInventoryCart(HttpServletRequest request){
	   boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
	   boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);

	   return (isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable);
   }

/*
 * Returns true if not in physical inventory period and not in scheduled cart.
 */
	public static boolean isRegularCart(HttpServletRequest request, HttpSession session){
		return (!(isPhysicalInventoryCart(request) || isScheduledCart(request, session)));
	}

    public static ActionErrors reloadInvShoppingCart(ShoppingServices shoppingEjb,
                                                      HttpSession pSession,
                                                      ShoppingCartData shoppingCartD,
                                                      SiteData site,
                                                      UserData user,
                                                      String storeTypeProperty) {
        log.info("reloadInvShoppingCart.");
        ActionErrors ae = new ActionErrors();
        try {
            if (ShopTool.canSaveShoppingCart(pSession)
                    && ShopTool.isModernInventoryCartAvailable(pSession)) {

                log.debug("ShopTool::reloadInvShoppingCart => getInvShoppingCart");
                pSession.setAttribute(Constants.INVENTORY_SHOPPING_CART, new ShoppingCartData());
                shoppingCartD = shoppingEjb.getInvShoppingCart(storeTypeProperty, user, site,false,
                    SessionTool.getCategoryToCostCenterView(pSession, site.getSiteId()));
                pSession.setAttribute(Constants.INVENTORY_SHOPPING_CART, shoppingCartD);
                log.debug("ShopTool::reloadInvShoppingCart => ok");


            } else {
                pSession.setAttribute(Constants.INVENTORY_SHOPPING_CART, null);
            }
        } catch (Exception exc) {
//        } catch (RemoteException exc) {
            ae.add("error", new ActionError("error.systemError",
                    "Failed to get the inventory shopping cart data"));
            return ae;
        }
        log.debug("ShopTool::reloadInvShoppingCart => end");
        return ae;
    }

    public static ShoppingCartData getCurrentInventoryCart(HttpSession session) {
        {
            ShoppingCartData cart = null;
            if (isModernInventoryCartAvailable(session)) {
                cart = (ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
                if (cart == null) {
                    log.debug("ShopTool: getCurrentInventoryCart reset inv cart.");
                    cart = new ShoppingCartData();
                    cart.setUser(getCurrentUser(session).getUser());
                }
            }
            return cart;
        }
    }

    // method getSalesTax() below was not changed for Avatax on purpose:
    // this method is not used (invoked)
    public static BigDecimal getSalesTax(Order orderEjb,
                                         Site siteEjb,
                                         StoreData store,
                                         AccountData account,
                                         SiteData site,
                                         ShoppingForm pForm,HttpServletRequest request) {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        Distributor distributorEjb = null;
        try {
            distributorEjb = factory.getDistributorAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
        }

        Catalog catalogEjb = null;
        try {
            catalogEjb = factory.getCatalogAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
        }

        double itemsAmt = pForm.getItemsAmt(request);
        BigDecimal itemsAmtBD = new BigDecimal(itemsAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal tax = pForm.getCacheCalculatedSalesTaxAmt(site.getSiteId(), itemsAmtBD);
        if (tax != null) {
            log.debug("getCacheCalculatedSalesTaxAmt: " + tax);
        } else {
            try {
                log.debug("calculateSalesTaxAmt");
                tax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, store, account, site, pForm.getItems(), catalogEjb);
                pForm.setCacheCalculatedSalesTaxAmt(site.getSiteId(), tax, itemsAmtBD);
            } catch (Exception e) {
                //for now just print, after this is more mature may do something more meaningful
                e.printStackTrace();
            }
        }
        log.debug("About to return tax of: " + tax);
        return tax;

    }

    // old method to calculate Sales Tax (was not changed);
    // new method  to calculate Sales Tax is called getSalesTaxAvalara()
    public static BigDecimal getSalesTax(Order orderEjb,
                                         Site siteEjb,
                                         StoreData store,
                                         AccountData account,
                                         SiteData site,
                                         ShoppingCartItemDataVector items
                                         ) throws RemoteException {

        BigDecimal taxTotal = ZERO;
        if (items != null) {

            int storeId = store != null ? store.getStoreId() : 0;
            int accountId = account != null ? account.getAccountId() : 0;
            int siteId = site != null ? site.getSiteId() : 0;

            boolean taxableOrder = orderEjb.isTaxableOrder(storeId, accountId, siteId);
            Iterator it = items.iterator();
            BigDecimal taxRate = siteEjb.getTaxRate(siteId);
            taxRate = taxRate == null ? new BigDecimal(0) : taxRate;
            while (it.hasNext()) {
                ShoppingCartItemData item = (ShoppingCartItemData) it.next();
                if (taxableOrder && item.isTaxable() && !item.getReSaleItem()) {
                    BigDecimal taxitem = new BigDecimal(item.getAmount()).multiply(taxRate);
                    taxTotal = Utility.addAmt(taxitem.setScale(2, BigDecimal.ROUND_HALF_UP), taxTotal);
                }
            }
        }

        return taxTotal;
    }

// Old code Avalara Tax
    /*** SVC: new method for AvaTax ***/
//    public static BigDecimal getSalesTaxAvalara(Order orderEjb,
//    //public static ArrayList getSalesTaxAvalara(Order orderEjb,
//                                                Site siteEjb,
//                                                Distributor distributorEjb,
//                                                StoreData store,
//                                                AccountData account,
//                                                SiteData site,
//                                                ShoppingCartItemDataVector items,
//                                                Catalog catalogEjb) throws RemoteException {
//	try {
//
//    	AddressData sad; // shipping address
//        BigDecimal taxTotal = ZERO;
//        boolean avatax_success = true;
//        ArrayList returnList = new ArrayList();
//
//        if (items != null) {
//
//            int storeId = store != null ? store.getStoreId() : 0;
//            int accountId = account != null ? account.getAccountId() : 0;
//            int siteId = site != null ? site.getSiteId() : 0;
//
//            boolean taxableOrder = orderEjb.isTaxableOrder(storeId, accountId, siteId);
//            Iterator it = items.iterator();
//
//            sad = siteEjb.getShipToAddress(siteId); // Shipping address for the site
//
//            while (it.hasNext()) {
//                   ShoppingCartItemData item = (ShoppingCartItemData) it.next();
//                   if (taxableOrder && item.isTaxable() && !item.getReSaleItem()) {
//
//                	   //log.info("SVC: item = " + item);
//                	   //log.info("SVC: item.getProduct() = " + item.getProduct());
//                	   //log.info("SVC: item.getProduct().getCatalogStructure() = " + item.getProduct().getCatalogStructure());
//                	   int distributorId = item.getProduct().getCatalogStructure().getBusEntityId(); // item Distributor (inside one Catalog)
//                	   //log.info("SVC: Item = " + item.getItemDesc() + " Distributor = " + distributorId);
//                	   if (distributorId == 0) { //Object CatalogStructureData does not have info. about the Distributor
//                		   // find the item's Distributor on the basis of the known ItemId and catalogId
//                		   log.info("SVC: DistributorID is 0");
//                		   CatalogStructureData csd = null;
//                		   try {
//                		       csd = catalogEjb.getCatalogStructureData(item.getProduct().getCatalogStructure().getCatalogId(), item.getItemId());
//                       	   } catch (DataNotFoundException e) {
//                               e.printStackTrace();
//                    	   }
//                       	   distributorId = csd.getBusEntityId();
//                	   }
//
//                	   AddressData oad = new AddressData();
//
//                	   //find the origin address for the item's Distributor
//                       if (distributorId != 0) {
//                    	   DistributorData dd = null;
//                    	   try {
//                    	       dd = distributorEjb.getDistributor(distributorId);
//                    	   } catch (DataNotFoundException e) {
//                               e.printStackTrace();
//                    	   }
//                    	   oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)
//                       } else { // item Distributor is NOT defined/found in the Cleanwise Database
//                    	   // find the Site address, to which the catalog is attached
//                    	   log.info("Distributor for the item " + item.getItemDesc() + " is NOT found in the Cleanwise Database. Will use Site Address as Origin Address for sales tax calculations.");
//                    	   oad = site.getSiteAddress(); //site Address from DB table CLW_ADDRESS
//                       }
//
//                       // find Shipping (Destination) Address for the item
//                       BigDecimal salesTax = ZERO;
//                       //log.info("Before instantiating the class TaxUtilAvalara.");
//                       TaxUtilAvalara taxutilavalara = new TaxUtilAvalara();
//                       //log.info("After instantiating the class TaxUtilAvalara.");
//                       BigDecimal itemAmount = new BigDecimal(item.getAmount());
//                       itemAmount = itemAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
//                       //GetTaxResult getTaxResult = taxutilavalara.calculateAvatax(oad, sad, new BigDecimal(item.getAmount()));
//                       GetTaxResult getTaxResult = taxutilavalara.calculateAvatax(oad, sad, itemAmount);
//                       if (getTaxResult.getResultCode() == SeverityLevel.Success)
//                       {
//                           //log.info("DocCode: " + getTaxRequest.getDocCode());
//                           // DocId is generated by AvaTax
//                           log.info("DocId: " + getTaxResult.getDocId());
//                           log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
//                           log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
//                           BigDecimal taxitem = getTaxResult.getTotalTax();
//                           taxTotal = Utility.addAmt(taxitem.setScale(2, BigDecimal.ROUND_HALF_UP), taxTotal);
//                           log.info("AvaTax calculated tax successfully!!!");
//                       }
//                       else
//                       {
//                           avatax_success = false;
//                    	   printMessages(getTaxResult.getMessages());
//                           log.info("AvaTax tax calculation failed!!!");
//
//                       }
//                   }
//            }
//        }
//
//        returnList.add(taxTotal);
//        returnList.add(avatax_success);
//        //return returnList;
//        return taxTotal;
//
//		}catch(DataNotFoundException exc) {
//			throw new RemoteException(exc.getMessage(), exc);
//		}
//	}
// End of Old code Avalara Tax


// New code Avalara Tax. YR
        public static BigDecimal getSalesTaxAvalara(Order orderEjb,
                                                    Site siteEjb,
                                                    Distributor distributorEjb,
                                                    StoreData store,
                                                    AccountData account,
                                                    SiteData site,
                                                    ShoppingCartItemDataVector sciDV,
                                                    Catalog catalogEjb) throws RemoteException {

            BigDecimal taxTotal = ZERO;
            HashMap<String,AvalaraTaxEntry> addressItemsMap = new HashMap<String,AvalaraTaxEntry>();

            try {

            AddressData sad; // shipping address
            if (sciDV != null) {
                int storeId = store != null ? store.getStoreId() : 0;
                int accountId = account != null ? account.getAccountId() : 0;
                int siteId = site != null ? site.getSiteId() : 0;
                boolean taxableOrder = orderEjb.isTaxableOrder(storeId, accountId, siteId);
                Iterator it = sciDV.iterator();
                sad = siteEjb.getShipToAddress(siteId); // Shipping address for the site
                Map<Integer, AddressData> distAddressMap = new HashMap<Integer, AddressData>();
                while (it.hasNext()) {
                       ShoppingCartItemData scid = (ShoppingCartItemData) it.next();
                       if (taxableOrder && scid.isTaxable() && !scid.getReSaleItem()) {
                               int distributorId = scid.getProduct().getCatalogStructure().getBusEntityId(); // item Distributor (inside one Catalog)
                               if (distributorId == 0) { //Object CatalogStructureData does not have info. about the Distributor
                                   // find the item's Distributor on the basis of the known ItemId and catalogId
                                   log.info("SVC: DistributorID is 0");
                                   CatalogStructureData csd = null;
                                   try {
                                       csd = catalogEjb.getCatalogStructureData(scid.getProduct().getCatalogStructure().getCatalogId(),
                                                                                scid.getItemId());
                                   } catch (DataNotFoundException e) {
                                       e.printStackTrace();
                                   }
                                   distributorId = csd.getBusEntityId();
                               }
                               AddressData oad = new AddressData();

                               //find the origin address for the item's Distributor
                               if (distributorId != 0) {
                            	   oad = distAddressMap.get(distributorId);
                            	   if (oad == null){
	                                   DistributorData dd = null;
	                                   try {
	                                       dd = distributorEjb.getDistributor(distributorId);
	                                   } catch (DataNotFoundException e) {
	                                       e.printStackTrace();
	                                   }
	                                   oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)
	                                   distAddressMap.put(distributorId, oad);
                            	   }
                               } else { // item Distributor is NOT defined/found in the Cleanwise Database
                                   // find the Site address, to which the catalog is attached
                                   log.info("Distributor for the item " + scid.getItemDesc() + " is NOT found in the Cleanwise Database. Will use Site Address as Origin Address for sales tax calculations.");
                                   oad = site.getSiteAddress(); //site Address from DB table CLW_ADDRESS
                               }

                               if (!Utility.isSetForDisplay(oad.getAddress1()) &&
                                   !Utility.isSetForDisplay(oad.getAddress2()) &&
                                   !Utility.isSetForDisplay(oad.getAddress3()) &&
                                   !Utility.isSetForDisplay(oad.getAddress4()) &&
                                   !Utility.isSetForDisplay(oad.getPostalCode()) ) {
                                   oad = sad;
                               }
                               BigDecimal itemAmount = new BigDecimal(scid.getAmount());
                               itemAmount = itemAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                               AvalaraTaxItem item = new AvalaraTaxItem(Integer.toString(scid.getItemId()),
                                                                        scid.getActualSkuNum(),
                                                                        itemAmount,
                                                                        scid.getQuantity(),
                                                                        scid.getItemDesc());
                               String addressStr = TaxUtilAvalara.addressToString(oad);
                               if (addressItemsMap.get(addressStr) == null) {
                                   AvalaraTaxEntry entry = new AvalaraTaxEntry();
                                   ArrayList<AvalaraTaxItem> items = new ArrayList<AvalaraTaxItem>();
                                   items.add(item);
                                   entry.setItems(items);
                                   entry.setOriginAddress(oad);
                                   entry.setShippingAddress(sad);
                                   addressItemsMap.put(addressStr, entry);
                               } else {
                                   addressItemsMap.get(addressStr).getItems().add(item);
                               }
                       }
                }
            }

        }catch(DataNotFoundException exc) {
            throw new RemoteException(exc.getMessage(), exc);
        }

        if (!addressItemsMap.isEmpty()) {
            TaxUtilAvalara.calculateAvalaraTax(addressItemsMap);
            taxTotal = Utility.addAmt(taxTotal,
                                      TaxUtilAvalara.postCalculateAvalaraTax(addressItemsMap, null));
        }

        return taxTotal;

    }
// End of New code Avalara Tax. YR


    protected static void printMessages(ArrayOfMessage messages)
    {
        for (int ii = 0; ii < messages.size(); ii++)
        {
            Message message = messages.getMessage(ii);
            log.info(message.getSeverity().toString() + " " + ii + ": " + message.getSummary());
        }

    }

    // method getSalesTax() below was not changed for Avatax on purpose:
    // this method is currently not used (invoked), because it deals with Service, NOT Product Item
    public static BigDecimal getSalesTax(Order orderEjb,
            Site siteEjb,
            StoreData store,
            AccountData account,
            SiteData site,
            ShoppingCartServiceDataVector services) throws RemoteException {

       BigDecimal taxTotal = ZERO;
       if (services != null) {

           int storeId = store != null ? store.getStoreId() : 0;
           int accountId = account != null ? account.getAccountId() : 0;
           int siteId = site != null ? site.getSiteId() : 0;

           boolean taxableOrder = orderEjb.isTaxableOrder(storeId, accountId, siteId);
           Iterator it = services.iterator();
           BigDecimal taxRate = siteEjb.getTaxRate(siteId);
           taxRate = taxRate == null ? new BigDecimal(0) : taxRate;
           while (it.hasNext()) {
               ShoppingCartServiceData service = (ShoppingCartServiceData) it.next();
               if (taxableOrder && service.isTaxable()) {
                   BigDecimal taxService = new BigDecimal(service.getAmount()).multiply(taxRate);
                   taxTotal = Utility.addAmt(taxService.setScale(2, BigDecimal.ROUND_HALF_UP), taxTotal);
               }
           }
       }
       return taxTotal;
    }

    public static boolean hasInventoryCartAccessOpen(HttpServletRequest request) {
        CleanwiseUser u = getCurrentUser(request);
        if (null == u ||  u.getSite() == null) {
            return false;
        }
        boolean isInventoryCartAccessOpen = false;
	    boolean isPhysicalCartAvailable = isPhysicalCartAvailable(request);
        if (isPhysicalCartAvailable) {
            isInventoryCartAccessOpen = true;
        } else {
            isInventoryCartAccessOpen =
                u.getSite().hasInventoryCartAccessOpen();
        }
        return isInventoryCartAccessOpen;
    }

    public static boolean hasDiscretionaryCartAccessOpen(HttpServletRequest request) {
        return hasDiscretionaryCartAccessOpen(request.getSession());
    }

    public static boolean hasDiscretionaryCartAccessOpen(HttpSession session) {
        CleanwiseUser u = getCurrentUser(session);
        if (null == u || u.getSite() == null) {
            return false;
        } else if(cartContainsModifiedOrder(session)){
            return true;
        } else{
		    boolean isPhysicalCartAvailable = isPhysicalCartAvailable(session);
			boolean isUsedPhysicalInventoryAlgorithm = isUsedPhysicalInventoryAlgorithm(u);
            boolean usePhysicalInventory = isPhysicalCartAvailable && isUsedPhysicalInventoryAlgorithm;

            if (usePhysicalInventory) {
                return !isPhysicalCartPeriodAvailable(u.getSite().getPhysicalInventoryPeriods());
            } else {
                log.debug("hasDiscCartAccess "+u.getSite().hasDiscretionaryCartAccessOpen());
                return u.getSite().hasDiscretionaryCartAccessOpen();
            }
        }
    }

    public static boolean cartContainsModifiedOrder(HttpSession session){
        ShoppingCartData shoppingCart = getCurrentShoppingCart(session);
        return shoppingCart != null && shoppingCart.getPrevOrderData() != null;
    }

    public static boolean cartContainsWarnings(HttpServletRequest request) {

        java.util.List warnings = null;
        java.util.List iwarnings = null;

        String shoppingCartName = (String) request.getParameter("shoppingCartName");
        if (shoppingCartName == null) {
            shoppingCartName = Constants.SHOPPING_CART;
        }

        ShoppingCartData v_shoppingCart = (ShoppingCartData) request.getSession().getAttribute(shoppingCartName);
        if (v_shoppingCart != null) {
            warnings = v_shoppingCart.getWarningMessages();
            iwarnings = v_shoppingCart.getItemMessages();
        }

        return ((warnings != null && !warnings.isEmpty()) ||
                (iwarnings != null && !iwarnings.isEmpty()));
    }

    public static boolean requestContainsErrors(HttpServletRequest request) {
        ActionErrors ae = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
        return ae != null && !ae.isEmpty();
    }

    /**
     * Adds items to the order guide list
     *
     * @param currOgItems order guid list
     * @param cartItems   cart items
     * @return result
     */
    public static ShoppingCartItemDataVector addItemsToOgList(ShoppingCartItemDataVector currOgItems, List cartItems) {

        ShoppingCartItemDataVector result = new ShoppingCartItemDataVector();

        HashMap ogMap = Utility.toMapByItemId(currOgItems);

        Iterator cartItemIt = cartItems.iterator();
        while (cartItemIt.hasNext()) {

            ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItemIt.next();
            Integer key = new Integer(cartItem.getItemId());

            if (!cartItem.getProduct().isItemGroup()) {
                if (cartItem.getQuantity() > 0) {

                    if (ogMap.containsKey(key)) {
                        ShoppingCartItemData ogItem = (ShoppingCartItemData) ogMap.get(key);
                        ogItem.setQuantity(ogItem.getQuantity() + cartItem.getQuantity());
                        ogMap.put(key, ogItem);
                    } else {
                        ogMap.put(key, cartItem);
                    }

                }
            }
        }

        result.addAll(ogMap.values());

        return result;

    }

    public static boolean isShoppingCartConsolidated(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ShoppingCartData shoppingCartData =
            (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
        if (shoppingCartData == null) {
            return false;
        }
        if (shoppingCartData instanceof ConsolidatedCartView) {
            return true;
        }
        return false;
    }

/*
    public static BigDecimal calculateBudgetThreshold(BigDecimal budget, BigDecimal threshold) {
        if (budget == null) {
            return budget;
        }
        if (threshold == null) {
            return budget;
        }
        BigDecimal percentage = new BigDecimal(0);
        percentage = percentage.add(budget);
        percentage = percentage.multiply(threshold);
        percentage = percentage.divide(new BigDecimal(100));

        BigDecimal budgetThreshold = new BigDecimal(0);
        budgetThreshold = budgetThreshold.add(budget);
        budgetThreshold = budgetThreshold.add(percentage);
        return budgetThreshold;
    }

    private static BudgetInfo getBudgetInfoForCostCenter(int catalogId, int contractId, int costCenterId,
        CleanwiseUser appUser, ShoppingServices shoppingServicesEjb, Order orderEjb, Site siteEjb,
        CatalogInformation catalogInformationEjb, ShoppingCartItemDataVector cartItems)
        throws APIServiceAccessException, RemoteException {

        BudgetInfo budgetInfo = new BudgetInfo();

        if (cartItems == null) {
            return budgetInfo;
        }
        if (costCenterId == 0) {
            return budgetInfo;
        }
        CostCenterData costCenterData = catalogInformationEjb.getCostCenterById(costCenterId);
        if (costCenterData == null) {
            return budgetInfo;
        }

        SiteData siteData = appUser.getSite();
        BigDecimal tax = null;
        BigDecimal freight = null;
        BigDecimal handlingAmt = null;
        ///
        budgetInfo.setCategoryName(costCenterData.getShortDesc());

        /// Total sum
        double cartTotal = 0;
        if (!Utility.isTrue(costCenterData.getNoBudget())) {
            for (int i = 0; i < cartItems.size(); ++i) {
                ShoppingCartItemData cartItem = (ShoppingCartItemData)cartItems.get(i);
                if (!cartItem.getDuplicateFlag()) {
                    if (cartItem.getProduct().getCostCenterId() == costCenterId) {
                        cartTotal += cartItem.getAmount();
                    }
                }
            }
        }
        budgetInfo.setCartTotal(new BigDecimal(cartTotal));
        /// add service fee if applicable
        String addServiceFee = appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        BigDecimal serviceFee = new BigDecimal(0);
        AccountData accountData = appUser.getUserAccount();
        int accountId = accountData.getAccountId();
        if (addServiceFee.equals("true") && contractId > 0) {
            HashMap serviceFeeDetMap = shoppingServicesEjb.calculateServiceFee(contractId, cartItems, accountId);
            if (serviceFeeDetMap != null && serviceFeeDetMap.size() > 0) {

                //Calculate total service fee
                for (int i = 0; i < cartItems.size(); ++i) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData)cartItems.get(i);
                    int qty = cartItem.getQuantity();
                    Integer item = new Integer(cartItem.getItemId());
                    int prodCCid = cartItem.getProduct().getCostCenterId();
                    ServiceFeeDetail finalDet = (ServiceFeeDetail)serviceFeeDetMap.get(item);

                    if (finalDet != null) {
                        if (cartItem.getProduct().getCostCenterId() == costCenterId) {
                            BigDecimal amt = finalDet.getAmount();
                            BigDecimal totAmt = amt.multiply(new BigDecimal(qty));
                            serviceFee = serviceFee.add(totAmt);
                        }
                    }
                }
            }
            if (serviceFee != null && serviceFee.compareTo(new BigDecimal(0)) > 0) {
                budgetInfo.setCartTotal(budgetInfo.getCartTotal().add(serviceFee));
            }
        }
        /// handling
        boolean addFreightAndHandlingAmt = Utility.isTrue(costCenterData.getAllocateFreight());
        if (addFreightAndHandlingAmt) {
            OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();
            if (cartItems != null && cartItems.size() > 0) {
                for (int i = 0; i < cartItems.size(); ++i) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(i);
                    OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
                    frItem.setItemId(cartItem.getProduct().getProductId());
                    BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
                    priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
                    frItem.setPrice(priceBD);
                    frItem.setQty(cartItem.getQuantity());
                    BigDecimal weight = null;
                    String weightS = cartItem.getProduct().getShipWeight();
                    try {
                        weight = new BigDecimal(weightS);
                    } catch (Exception ex) {
                    }
                    frItem.setWeight(weight);
                    frItems.add(frItem);
                }
                OrderHandlingView frOrder = OrderHandlingView.createValue();
                frOrder.setTotalHandling(new BigDecimal(0));
                frOrder.setTotalFreight(new BigDecimal(0));
                frOrder.setContractId(contractId);
                frOrder.setAccountId(accountId);
                frOrder.setSiteId(siteData.getBusEntity().getBusEntityId());
                frOrder.setAmount(budgetInfo.getCartTotal());
                frOrder.setWeight(new BigDecimal(0));
                frOrder.setItems(frItems);

                frOrder = shoppingServicesEjb.calcTotalFreightAndHandlingAmount(frOrder);

                if (frOrder != null) {
                    handlingAmt = frOrder.getTotalHandling();
                    freight = frOrder.getTotalFreight();
                    if (handlingAmt != null) {
                        budgetInfo.setCartTotal(budgetInfo.getCartTotal().add(handlingAmt));
                    }
                    if (freight != null) {
                        budgetInfo.setCartTotal(budgetInfo.getCartTotal().add(freight));
                    }
                }
            }
        }
        /// get sales tax information
        if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenterData.getCostCenterTaxType())) {
            tax = getSalesTax(orderEjb, siteEjb, appUser.getUserStore(), appUser.getUserAccount(), appUser.getSite(), cartItems);
            if (tax != null) {
                budgetInfo.setCartTotal(budgetInfo.getCartTotal().add(tax));
            }
        }
        ///
        budgetInfo.setAllocated(siteData.getBudgetAllocated(costCenterId));
        budgetInfo.setSpent(siteData.getBudgetSpent(costCenterId));
        CostCenterAssocDataVector costCentersAssoc = catalogInformationEjb.getCatalogCostCenterAssoc(catalogId, costCenterId);
        if (costCentersAssoc != null && costCentersAssoc.size() > 0) {
            CostCenterAssocData costCenterAssoc = (CostCenterAssocData)costCentersAssoc.get(0);
            String budgetThreshold = costCenterAssoc.getBudgetThreshold();
            budgetInfo.setThreshold(Utility.parseBigDecimal(budgetThreshold));
        }

        return budgetInfo;
    }

    public static HashMap<Integer, BudgetInfo> getBudgetsForCostCenters(HttpSession session,
        int catalogId, ShoppingCartItemDataVector cartItems) throws APIServiceAccessException, RemoteException {
        HashMap<Integer, BudgetInfo> budgetsForCostCenters = new HashMap<Integer, BudgetInfo>();

        if (cartItems == null) {
            return budgetsForCostCenters;
        }
        if (cartItems.size() == 0) {
            return budgetsForCostCenters;
        }

        Integer contractIdObj = (Integer) session.getAttribute(Constants.CONTRACT_ID);
        int contractId = 0;
        if (contractIdObj != null) {
            contractId = contractIdObj.intValue();
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData site = appUser.getSite();
        BigDecimal subTotal = null;
        BigDecimal tax = null;
        BigDecimal freight = null;
        BigDecimal handlingAmt = null;
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Order orderEjb = factory.getOrderAPI();
        Site siteEjb = factory.getSiteAPI();
        CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
        ShoppingServices shoppingServicesEjb = factory.getShoppingServicesAPI();
        CostCenterData costCenterData = null;

        //CostCenterDataVector costCenters = catalogInformationEjb.getAllCostCenters(catalogId, Account.ORDER_BY_ID);
        CostCenterDataVector costCenters = getAllCostCenters(session);
        HashMap<Integer, ShoppingCartItemDataVector> costCenterItemMap = new HashMap<Integer, ShoppingCartItemDataVector>();
        CostCenterData masterCostCenterData = null;

        if (costCenters == null) {
            return budgetsForCostCenters;
        }

        for (int i = 0; i < cartItems.size(); ++i) {
            ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(i);
            if (!cartItem.getDuplicateFlag()) {
                if (cartItem.getProduct().getCostCenterId() != 0) {
                    int costCenterId = cartItem.getProduct().getCostCenterId();
                    for (int j = 0; j < costCenters.size(); ++j) {
                        CostCenterData costCenter = (CostCenterData)costCenters.get(j);
                        if (costCenter.getCostCenterId() == costCenterId) {
                            if (!Utility.isTrue(costCenter.getNoBudget())) {
                                /// Try to add item into 'costCenterItemMap'
                                if (masterCostCenterData == null) {

                                    if (RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenter.getCostCenterTaxType())) {
                                        masterCostCenterData = costCenter;
                                    } else if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenter.getCostCenterTaxType())) {
                                        Integer costCenterIdObj = new Integer(costCenterId);
                                        if (costCenterItemMap.containsKey(costCenterIdObj)) {
                                            ShoppingCartItemDataVector costCenterItems = (ShoppingCartItemDataVector)costCenterItemMap.get(costCenterIdObj);
                                            costCenterItems.add(cartItem);
                                        } else {
                                            ShoppingCartItemDataVector costCenterItems = new ShoppingCartItemDataVector();
                                            costCenterItems.add(cartItem);
                                            costCenterItemMap.put(costCenterIdObj, costCenterItems);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (masterCostCenterData != null) {
            costCenterItemMap.clear();
            Integer costCenterIdObj = new Integer(masterCostCenterData.getCostCenterId());
            costCenterItemMap.put(costCenterIdObj, cartItems);
            Account accEjb = factory.getAccountAPI();
            CatalogData accCatalog = accEjb.getAccountCatalog(appUser.getUserAccount().getAccountId());
            catalogId = accCatalog.getCatalogId();
        }

        {
            Iterator it = costCenterItemMap.keySet().iterator();
            while (it.hasNext()) {
                Integer costCenterIdObj = (Integer)it.next();
                ShoppingCartItemDataVector costCenterItems = (ShoppingCartItemDataVector)costCenterItemMap.get(costCenterIdObj);
                BudgetInfo budgetInfo = getBudgetInfoForCostCenter(catalogId, contractId, costCenterIdObj.intValue(),
                    appUser, shoppingServicesEjb, orderEjb, siteEjb, catalogInformationEjb, costCenterItems);
                budgetsForCostCenters.put(costCenterIdObj, budgetInfo);
            }
        }

        return budgetsForCostCenters;
    }

    public static boolean getUseBudgetThreshold(HttpSession session, StoreData store) throws APIServiceAccessException, RemoteException {
        if (store == null) {
            return false;
        }
        boolean useBudgetThreshold = false;
        try {
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            PropertyService propertyEjb = factory.getPropertyServiceAPI();
            String value = propertyEjb.getBusEntityProperty(store.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
            if (value != null) {
                if (value.equalsIgnoreCase("true")) {
                    useBudgetThreshold = true;
                }
            }
        } catch (DataNotFoundException ex) {
            useBudgetThreshold = false;
        }
        return useBudgetThreshold;
    }

    public static ActionErrors validateBudgetThreshold(HttpServletRequest request,
        StoreData store, int catalogId, ShoppingCartItemDataVector cartItems) {

        ActionErrors ae = new ActionErrors();
        boolean useBudgetThreshold = false;
        try {
            useBudgetThreshold = getUseBudgetThreshold(request.getSession(), store);
        } catch (APIServiceAccessException ex) {
            ae.add("error", new ActionError("error.simpleGenericError", ex.getMessage()));
            return ae;
        } catch (RemoteException ex) {
            ae.add("error", new ActionError("error.simpleGenericError", ex.getMessage()));
            return ae;
        }

        if (!useBudgetThreshold) {
            return ae;
        }

        ///
        HashMap<Integer, BudgetInfo> budgetsForCostCenters = new HashMap<Integer, BudgetInfo>();
        try {
            budgetsForCostCenters = ShopTool.getBudgetsForCostCenters(request.getSession(), catalogId, cartItems);
        } catch (APIServiceAccessException ex) {
            ae.add("error", new ActionError("error.simpleGenericError", ex.getMessage()));
            return ae;
        } catch (RemoteException ex) {
            ae.add("error", new ActionError("error.simpleGenericError", ex.getMessage()));
            return ae;
        }

        ///
        if (budgetsForCostCenters == null) {
            return ae;
        }
        boolean firstRecord = true;
        StringBuilder buff = new StringBuilder();
        Iterator it = budgetsForCostCenters.keySet().iterator();
        while (it.hasNext()) {
            Integer costCenterIdObj = (Integer)it.next();
            BudgetInfo budgetInfo = (BudgetInfo)budgetsForCostCenters.get(costCenterIdObj);
            if (budgetInfo.getAllocated() == null) {
                continue;
            }
            if (budgetInfo.getCartTotal() == null) {
                continue;
            }
            if (budgetInfo.getThreshold() == null) {
                continue;
            }
            BigDecimal budgetTotal = new BigDecimal(budgetInfo.getAllocated().doubleValue());
            if (budgetInfo.getSpent() != null) {
                budgetTotal = budgetTotal.subtract(budgetInfo.getSpent());
            }
            BigDecimal budgetThreshold = ShopTool.calculateBudgetThreshold(budgetTotal, budgetInfo.getThreshold());
            if (budgetThreshold.doubleValue() < budgetInfo.getCartTotal().doubleValue()) {
                String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.costCenterExceedsBudget", new String[]{budgetInfo.getCategoryName()});
                if (firstRecord) {
                    firstRecord = false;
                } else {
                    buff.append(" ");
                }
                buff.append(errorMess);
            }
        }
        if (buff.length() > 0) {
            ae.add("error", new ActionError("error.simpleGenericError", buff.toString()));
        }
        ///
        return ae;
    }

    public static ShoppingCartItemDataVector copySCardItems(ShoppingCartData cartD) {
        ShoppingCartItemDataVector newItems = new ShoppingCartItemDataVector();
        ShoppingCartItemDataVector oldItems = cartD.getItems();
        Iterator i = oldItems.iterator();
        while (i.hasNext()) {
            ShoppingCartItemData oldItem = (ShoppingCartItemData)i.next();
            ShoppingCartItemData newItem = oldItem.copy();
            newItems.add(newItem);
        }
        return newItems;
    }
*/

    private static ActionErrors validateBudgetThreshold(HttpServletRequest request,
                                                        ShoppingCartData pShoppingCart,
                                                        ShoppingCartItemDataVector pItemsToAdd,
                                                        String pThrValidateRequest) {

        log.info("validateBudgetThreshold()=> BEGIN");

        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();

            CleanwiseUser appUser = getCurrentUser(request);

            StoreData store = appUser.getUserStore();
            AccountData account = appUser.getUserAccount();
            SiteData site = appUser.getSite();
            UserData user = appUser.getUser();

            String budgetThrType = account.getBudgetThresholdType();

            log.info("validateBudgetThreshold()=> isAllowBudgetThreshold: " + store.isAllowBudgetThreshold());
            log.info("validateBudgetThreshold()=> budgetThrType: " + budgetThrType);
            log.info("validateBudgetThreshold()=> workflowRoleCd: " + user.getWorkflowRoleCd());

            if (store.isAllowBudgetThreshold()) {

                if (!RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equalsIgnoreCase(user.getWorkflowRoleCd())) {

                    if (BudgetUtil.isUsedAccountBudgetThreshold(store.isAllowBudgetThreshold(), budgetThrType)
                            || BudgetUtil.isUsedSiteBudgetThreshold(store.isAllowBudgetThreshold(), budgetThrType)) {

                        ShoppingCartItemDataVector items = getValidateThresholdItems(pShoppingCart, pItemsToAdd);

                        log.info("validateBudgetThreshold()=> pThrValidateRequest : " + pThrValidateRequest);
                        CostCenterDataVector costCenters;
                        if (Constants.THRESHOLD_VALIDATE_COST_CENTER_REQUEST.NEW_ITEMS.equals(pThrValidateRequest)) {
                            costCenters = getCostCentersForItems(session, pItemsToAdd);
                        } else if (Constants.THRESHOLD_VALIDATE_COST_CENTER_REQUEST.SHOPPING_CART_ITEMS.equals(pThrValidateRequest)) {
                            costCenters = getCostCentersForItems(session, items);
                        } else if (Constants.THRESHOLD_VALIDATE_COST_CENTER_REQUEST.REQUEST_ITEMS.equals(pThrValidateRequest)) {
                            costCenters = getCostCentersForItems(session, items);
                        } else {
                            costCenters = getAllCostCenters(session);
                        }

                        log.info("validateBudgetThreshold()=> Cost Centers size: " + costCenters.size());

                        List<CostCenterData> ecc = validateBudgetThreshold(store, account, site, costCenters, items);
                        if (!ecc.isEmpty()) {
                            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.costCenterExceedsBudget", null);
                            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                        }
                    }
                }
            }

        } catch (Exception e) {
            String err = "An error occurred when validate threshold rule.";
            ae.add("error", new ActionError("error.systemError", err));
            log.error(err, e);
            return ae;
        }

       log.info("validateBudgetThreshold()=> END.");
       return ae;

    }

    private static CostCenterDataVector getCostCentersForItems(HttpSession pSession, ShoppingCartItemDataVector pItems) {

        log.info("getCostCentersForItems()=> BEGIN");

        CostCenterDataVector result = new CostCenterDataVector();
        HashSet<Integer> resCostCentersSet = new HashSet<Integer>();

        if (pItems != null && !pItems.isEmpty()) {

            CostCenterDataVector allCostCenters = getAllCostCenters(pSession);
            HashMap map = Utility.toMap(allCostCenters);
            log.info("getCostCentersForItems()=> available cost centers: " + map.keySet());
            for (Object oItem : pItems) {
                ShoppingCartItemData item = (ShoppingCartItemData) oItem;
                int ccId = item.getProduct().getCostCenterId();
                if (!resCostCentersSet.contains(ccId) && map.containsKey(ccId)) {
                    if (pItems.getCostCenterItemsOnly(ccId).getItemsCost() > 0) {
                        result.add(map.get(ccId));
                        resCostCentersSet.add(ccId);
                    }
                }
            }

        }

        log.info("getCostCentersForItems()=> result cost centers:" + resCostCentersSet);
        log.info("getCostCentersForItems()=> END.");

        return result;

    }

    private static List<CostCenterData> validateBudgetThreshold(StoreData pStore,
                                                                AccountData pAccount,
                                                                SiteData pSite,
                                                                CostCenterDataVector pCostCenters,
                                                                ShoppingCartItemDataVector pItems) throws Exception {

        ArrayList<CostCenterData> exceededCostCenters = new ArrayList<CostCenterData>();

        if (pCostCenters != null && !pCostCenters.isEmpty()) {

            for (Object oCostCenter : pCostCenters) {

                CostCenterData costCenter = (CostCenterData) oCostCenter;

                boolean isExcBudgetThr = validateBudgetThreshold(pStore, pAccount, pSite, costCenter, pItems);
                if (isExcBudgetThr) {
                    exceededCostCenters.add(costCenter);
                }

            }

        }

        return exceededCostCenters;

    }

    private static boolean validateBudgetThreshold(StoreData pStore,
                                                   AccountData pAccount,
                                                   SiteData pSite,
                                                   CostCenterData pCostCenter,
                                                   ShoppingCartItemDataVector pItems) throws Exception {

        if (pCostCenter != null) {

            int costCenterId = pCostCenter.getCostCenterId();
            String costCenterName = pCostCenter.getShortDesc();

            log.info("validateBudgetThreshold()=> Validate Cost Center ID: " + costCenterId + ", Name: " + costCenterName);
            
            if(pSite.isBudgetUnlimited(costCenterId))
                return false;

            BigDecimal estCartAmount = getEstCostCenterAmount(pStore,
                    pAccount,
                    pSite,
                    pCostCenter,
                    pItems);

            BigDecimal allocated = pSite.getBudgetAllocated(costCenterId);
            BigDecimal spent     = pSite.getBudgetSpent(costCenterId);
            Integer thresholdPct = pSite.getBudgetThresholdPercent(costCenterId);

            log.info("validateBudgetThreshold()=> allocated    : " + allocated.doubleValue());
            log.info("validateBudgetThreshold()=> spent        : " + spent.doubleValue());
            log.info("validateBudgetThreshold()=> estCartAmount: " + estCartAmount.doubleValue());
            log.info("validateBudgetThreshold()=> thresholdPct : " + thresholdPct);

            if (thresholdPct != null) {

                BigDecimal budgetThreshold = BudgetUtil.calculateBudgetThreshold(allocated, thresholdPct);

                budgetThreshold = Utility.bdNN(budgetThreshold).setScale(BigDecimal.ROUND_HALF_UP, 2);

                BigDecimal spentWithCart = Utility.addAmt(spent, estCartAmount);
                spentWithCart = Utility.bdNN(spentWithCart).setScale(BigDecimal.ROUND_HALF_UP, 2);

                log.info("validateBudgetThreshold()=> Budget Threshold      : " + budgetThreshold);
                log.info("validateBudgetThreshold()=> Estimated Spent Amount: " + spentWithCart);

                if (budgetThreshold.doubleValue() < spentWithCart.doubleValue()) {
                    log.info("The Budget has exceeded threshold for '" + pCostCenter.getShortDesc() + "' cost center. " +
                            "Threshold:" + budgetThreshold + ", Estimated Spent Amount: " + spentWithCart);
                    return true;
                }
            }
        }

        return false;

    }

    private static ShoppingCartItemDataVector getValidateThresholdItems(ShoppingCartData pShoppingCart,
                                                                        ShoppingCartItemDataVector pItemsToAdd) {
        ShoppingCartItemDataVector items = new ShoppingCartItemDataVector();
        if (pShoppingCart != null) {
            items = pShoppingCart.getItems().copy();
            if (pItemsToAdd != null) {
                for (Object oNewShoppingCartItem : pItemsToAdd) {
                    pShoppingCart.addItem(items, (ShoppingCartItemData) oNewShoppingCartItem);
                }
            }
        } else if (pItemsToAdd != null) {
            items.addAll(pItemsToAdd);
        }

        return items;
    }

    public static BigDecimal getEstCostCenterAmount(StoreData pStore,
                                                    AccountData pAccount,
                                                    SiteData pSite,
                                                    CostCenterData pCostCenter,
                                                    ShoppingCartItemDataVector pItems) throws Exception {


        log.info("getEstCostCenterAmount()=> BEGIN");

        if (pCostCenter == null || pItems == null) {
            log.info("getEstCostCenterAmount()=> Invalid Data. Return.");
            return null;
        }

        log.info("getEstCostCenterAmount()=> CostCenterID:"+pCostCenter.getCostCenterId()+", pItems size: "+pItems.size());

        if (pItems.isEmpty()) {
            return ZERO;
        }

        APIAccess factory = APIAccess.getAPIAccess();

        Site siteEjb   = factory.getSiteAPI();
        Order orderEjb = factory.getOrderAPI();

        Distributor distributorEjb = factory.getDistributorAPI();
        Catalog catalogEjb = factory.getCatalogAPI();

        ContractData contract = pSite.getContractData();
        ShoppingCartItemDataVector ccItemsOnly = pItems.getCostCenterItemsOnly(pCostCenter.getCostCenterId());

        log.info("getEstCostCenterAmount()=> ccItemsOnly size: "+ccItemsOnly.size());

        BigDecimal tax        = null;
        BigDecimal freight    = null;
        BigDecimal handling   = null;
        BigDecimal serviceFee = null;

        String addServiceFee = pAccount.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        if (Utility.isTrue(addServiceFee)) {
            serviceFee = calculateServiceFeeAmt(pAccount.getAccountId(),
                    contract.getContractId(),
                    pCostCenter,
                    pItems);
        }

       if (Utility.isTrue(pCostCenter.getAllocateFreight())) {

            OrderHandlingView frOrder = getFreightOrder(pAccount.getAccountId(),
                    pSite.getSiteId(),
                    contract.getContractId(),
                    pItems);

            handling = frOrder.getTotalHandling();
            freight  = frOrder.getTotalFreight();

        }

        //get sales tax information
        if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(pCostCenter.getCostCenterTaxType())) {
            tax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, pStore, pAccount, pSite, ccItemsOnly, catalogEjb);
        } else if (RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(pCostCenter.getCostCenterTaxType())) {
            //if this is a master tax cost center go through and find a complete subtotal for tax
            tax = getSalesTaxAvalara(orderEjb, siteEjb, distributorEjb, pStore, pAccount, pSite, pItems, catalogEjb);
        }


        freight    = Utility.bdNN(freight);
        handling   = Utility.bdNN(handling);
        tax        = Utility.bdNN(tax);
        serviceFee = Utility.bdNN(serviceFee);

        BigDecimal total = new BigDecimal(ccItemsOnly.getItemsCost());

        log.info("getEstCostCenterAmount()=> allocateFreight  : "+pCostCenter.getAllocateFreight());
        log.info("getEstCostCenterAmount()=> addServiceFee    : "+addServiceFee);
        log.info("getEstCostCenterAmount()=> CostCenterTaxType: "+pCostCenter.getCostCenterTaxType());
        log.info("getEstCostCenterAmount()=> serviceFee       : "+serviceFee.doubleValue());
        log.info("getEstCostCenterAmount()=> freight          : "+freight.doubleValue()+", handling: "+handling.doubleValue());
        log.info("getEstCostCenterAmount()=> itemsCost        : "+total.doubleValue());

        total = total.add(freight);
        total = total.add(handling);
        total = total.add(tax);
        total = total.add(serviceFee);

        log.info("getEstCostCenterAmount()=> END.Total: "+total.doubleValue());

        return total;

    }

    private static OrderHandlingView getFreightOrder(int pAccountId,
                                              int pSiteId,
                                              int pContractId,
                                              ShoppingCartItemDataVector pShoppingCartItems) throws Exception {

        APIAccess factory = APIAccess.getAPIAccess();

        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

        OrderHandlingView frOrder = OrderHandlingView.createValue();

        frOrder.setTotalHandling(ZERO);
        frOrder.setTotalFreight(ZERO);
        frOrder.setContractId(pContractId);
        frOrder.setAccountId(pAccountId);
        frOrder.setSiteId(pSiteId);
        frOrder.setAmount(ZERO);
        frOrder.setWeight(ZERO);
        frOrder.setItems(pShoppingCartItems.getFreightItems());

        return shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);

    }


    private static BigDecimal calculateServiceFeeAmt(int pAccountId,
                                              int pContractId,
                                              CostCenterData pCostCenter,
                                              ShoppingCartItemDataVector pShoppingCartItems) throws Exception {

        BigDecimal serviceFee = ZERO;

        APIAccess factory = APIAccess.getAPIAccess();
        ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
        if (pShoppingCartItems != null) {

            HashMap serviceFeeItemMap = shoppingServEjb.calculateServiceFee(pContractId, pShoppingCartItems, pAccountId);

            if (serviceFeeItemMap != null && !serviceFeeItemMap.isEmpty()) {

                //Calculate total service fee
                for (Object oShoppingCartItem : pShoppingCartItems) {
                    ShoppingCartItemData item = (ShoppingCartItemData) oShoppingCartItem;
                    ServiceFeeDetail itemServiceFeeInf = (ServiceFeeDetail) serviceFeeItemMap.get(item.getItemId());
                    if (itemServiceFeeInf != null) {
                        if (pCostCenter.getCostCenterId() == item.getProduct().getCostCenterId()) {
                            BigDecimal amt = itemServiceFeeInf.getAmount();
                            BigDecimal totAmt = Utility.bdNN(amt).multiply(new BigDecimal(item.getQuantity()));
                            serviceFee = Utility.addAmt(serviceFee, totAmt);
                        }
                    }
                }

            }
        }
        return serviceFee;
    }

    public static ActionErrors validateBudgetThreshold(HttpServletRequest request, ShoppingCartItemDataVector pItems) {
        return validateBudgetThreshold(request, null, pItems, Constants.THRESHOLD_VALIDATE_COST_CENTER_REQUEST.REQUEST_ITEMS);
    }

    public static ActionErrors validateBudgetThreshold(HttpServletRequest request, ShoppingCartData pCart) {
        return validateBudgetThreshold(request, pCart, null, Constants.THRESHOLD_VALIDATE_COST_CENTER_REQUEST.SHOPPING_CART_ITEMS);
    }

    public static ValidateActionMessage addItemsToCart(HttpServletRequest request,
                                                       ShoppingCartData pShoppingCart,
                                                       ShoppingCartItemDataVector pItemsToAdd) {

        log.info("addItemsToCart()=> BEGIN");

        ValidateActionMessage am = validateSpecialPermission(request, pItemsToAdd, false);
        if (am.hasErrors()) {
            return am;
        }

        ActionErrors ae = validateBudgetThreshold(request,
                pShoppingCart,
                pItemsToAdd,
                Constants.THRESHOLD_VALIDATE_COST_CENTER_REQUEST.NEW_ITEMS);

        if (!ae.isEmpty()) {
            am.reset();
            am.addActionErrors(ae);
            return am;
        }

        if (pItemsToAdd != null && pShoppingCart != null) {
            for (Object oItemToAdd : pItemsToAdd) {
                ShoppingCartItemData item = (ShoppingCartItemData) oItemToAdd;
                pShoppingCart.addItem(item);
            }
        }

        log.info("addItemsToCart()=> END.");

        return am;

    }

    public static ValidateActionMessage validateSpecialPermission(HttpServletRequest request, List pItems) {
        return validateSpecialPermission(request, pItems, true);
    }

    private static ValidateActionMessage validateSpecialPermission(HttpServletRequest request, List pItems, boolean pErrorFlag) {

        log.info("validateSpecialPermission()=> BEGIN");

        ValidateActionMessage vActionMessages = new ValidateActionMessage();
        try {

            CleanwiseUser appUser = getCurrentUser(request);
            AccountData account = appUser.getUserAccount();

            if (pItems != null && !pItems.isEmpty()) {
                if (appUser.isSpecialPermissionRequired() && !appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.SPECIAL_PERMISSION_ITEMS)) {

                    ShoppingServices shoppingServEjb = APIAccess.getAPIAccess().getShoppingServicesAPI();
                    IdVector itemIds = shoppingServEjb.getSpecialPermssionItemIds(account.getAccountCatalog().getCatalogId());

                    Iterator it = pItems.iterator();
                    while (it.hasNext()) {
                        Object oItem = it.next();
                        if (oItem instanceof ShoppingCartItemData) {
                            ShoppingCartItemData dhoppingItem = (ShoppingCartItemData) oItem;
                            if (itemIds.contains(dhoppingItem.getItemId())) {
                                if (pErrorFlag) {
                                    String mess = ClwI18nUtil.getMessage(request, "shop.errors.specPermItemCheckResult", new String[]{dhoppingItem.getActualSkuNum()});
                                    vActionMessages.putError(mess);
                                } else {
                                    String mess = ClwI18nUtil.getMessage(request, "shop.warning.specPermItemCheckResult", new String[]{dhoppingItem.getActualSkuNum()});
                                    it.remove();
                                    vActionMessages.putWarning(mess);
                                }
                            }
                        } else {
                            String mess = "Method does not support item objects of type: " + oItem.getClass().getName();
                            throw new Exception(mess);
                        }
                    }
                }
            }

        } catch (Exception e) {
            String err = "An error occurred when validate special items.Error: " + e.getMessage();
            log.error(err, e);
            vActionMessages.putSystemError(err);
            return vActionMessages;
        }

        log.info("validateSpecialPermission()=> END.");

        return vActionMessages;

    }


    public static ShoppingItemRequest createShoppingItemRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return createShoppingItemRequest(session);
    }

    /**
     * set ShoppingItemRequest into session for performance
     * @param session
     * @return
     */
    public static ShoppingItemRequest createShoppingItemRequest(HttpSession session) {
        CleanwiseUser appUser = getCurrentUser(session);
        SiteData site = appUser.getSite();
        String itemReqKey = "ShoppingItemRequest"+":"+site.getSiteId();
        ShoppingItemRequest shoppingItemRequest = (ShoppingItemRequest) session.getAttribute(itemReqKey);
        if (shoppingItemRequest != null)
        	return shoppingItemRequest;

        Integer catalogId = Utility.intNN((Integer) session.getAttribute(Constants.CATALOG_ID));
        Integer contractId = Utility.intNN((Integer) session.getAttribute(Constants.CONTRACT_ID));
        int accountCatalogId = appUser.getUserAccount().getAccountCatalogId();
        Integer priceList1Id = site.getPriceListRank1Id();
        Integer priceList2Id = site.getPriceListRank2Id();
        IdVector proprietaryListIds =  site.getProprietaryPriceListIds();
 
        shoppingItemRequest = new ShoppingItemRequest(appUser.getSite().getSiteId(),
                accountCatalogId,
                catalogId,
                contractId,
                priceList1Id,
                priceList2Id,
                proprietaryListIds,
                site.getAvailableTemplateOrderGuideIds(),
                appUser.isUserOnContract(),
                appUser.isSpecialPermissionRequired(),
                appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.SPECIAL_PERMISSION_ITEMS),
                appUser.getSite().getProductBundle());
        //---- STJ-6114: Performance Improvements - Optimize Pollock 
        try {
        	APIAccess.getAPIAccess().getShoppingServicesAPI().populateProductBundleFilter(shoppingItemRequest);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        //---------
        session.setAttribute(itemReqKey, shoppingItemRequest);
        return shoppingItemRequest;

    }


    public static boolean doesAnyAccountSupportsBudgets(HttpServletRequest request){
  		 try{
			 CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			 BusEntityDataVector accounts = user.getUserAccountsCollection();
			 APIAccess factory = APIAccess.getAPIAccess();
			 PropertyService propertyEjb = factory.getPropertyServiceAPI();
             Iterator i = accounts.iterator();
			 while (i.hasNext())  {
                 BusEntityData account = (BusEntityData)i.next();
                 int accountId = account.getBusEntityId();
                 //STJ-5383
                 //New UI - Budgets - Does not display the Budget Reporting tab when some of the locations supports budget
                 try {
	                 String value =
	                    propertyEjb.getBusEntityProperty(accountId, RefCodeNames.PROPERTY_TYPE_CD.SUPPORTS_BUDGET);
	                 if (Utility.isTrue(value)) {
				        return true;
	                 }
                 } catch(DataNotFoundException exception) {
                	 //We are here because an account's SUPPORTS-BUDGET property is not set to true,so nothing to do here.
                	 //Just continue the loop and check if other accounts support budgets. 
                 }
             }
           } catch (Exception e) {
               return false;
           }
           return false;
    }

    /**
	  * Method to find whether current account has fiscal calendar and site has budgets set.
	 */
	 public static boolean doesAccountSiteSupportsBudgets(HttpServletRequest request){
  		 try{
			 CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			 AccountDataVector accounts = new AccountDataVector();
			 accounts.add(user.getUserAccount());
			 return doesSiteSupportsBudgets(request, accounts);
           } catch (Exception e) {
               return false;
           }
     }


	 public static boolean doesAnySiteSupportsBudgets(HttpServletRequest request){

  		 try{
			 CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			 APIAccess factory = APIAccess.getAPIAccess();
			 Account accBean = factory.getAccountAPI();
			 BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
			 besc.setStoreBusEntityIds(user.getUserStoreAsIdVector());
			 IdVector userIds = new IdVector();
			 userIds.add(new Integer(user.getUserId()));
			 besc.setUserIds(userIds);
			 AccountDataVector accounts = accBean.getAccountsByCriteria(besc);

			 return doesSiteSupportsBudgets(request, accounts);
           } catch (Exception e) {
               return false;
           }
   }


	 public static boolean doesSiteSupportsBudgets(HttpServletRequest request, AccountDataVector accounts){

		 try{
			 CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			 APIAccess factory = APIAccess.getAPIAccess();
			 Account accBean = factory.getAccountAPI();
             Iterator i = accounts.iterator();
			 while (i.hasNext())  {
                 AccountData account = (AccountData)i.next();
                 int accountId = account.getAccountId();
                 if (account.isSupportsBudget()) {
                     SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
                     Date today = new Date();
                     today = sdf.parse(sdf.format(today));

                     //Here we find if there is Fiscal Year "ALL" for account.
                     FiscalCalenderData fiscalCalenderData = accBean.getFiscalCalenderForYear(accountId, 0);
                    //Here we retrieve latest Fiscal calendar for account.
                     FiscalCalenderView fiscalCalendarView = accBean.getCurrentFiscalCalenderV(accountId);
                     if(fiscalCalendarView != null) {
                         FiscalCalendarInfo fiscalCalendarInfo = new FiscalCalendarInfo(fiscalCalendarView);
						 int numOfPeriods = fiscalCalendarInfo.getNumberOfBudgetPeriods();
						 log.info("numOfPeriods: "+numOfPeriods);
                         HashMap budgetPeriodDates = fiscalCalendarInfo.getBudgetPeriodsAsHashMap();
						 /*
                         int numOfPeriods = fiscalCalendarView.getFiscalCalenderDetails().size();
                         if(numOfPeriods >12) {
                             numOfPeriods = 12;
                         }
                         String lastPeriodDateStr = ((FiscalCalendarInfo.BudgetPeriodInfo)budgetPeriodDates.get(numOfPeriods)).getEndDateMmdd()+"/"+fiscalCalendarView.getFiscalCalender().getFiscalYear();
                         Date lastPeriodDate = sdf.parse(lastPeriodDateStr);
						 */
                         Date endOfLastPeriod = ((FiscalCalendarInfo.BudgetPeriodInfo)budgetPeriodDates.get(numOfPeriods)).getEndDate();
						 log.info("endOfLastPeriod: "+endOfLastPeriod);
                         if(endOfLastPeriod.compareTo(today) >= 0 || fiscalCalenderData != null) {
                             CostCenterDataVector ccdv = ShopTool.getAllCostCentersForAccount(request.getSession(), user, accountId);
                             for(int j = 0; ccdv != null && j < ccdv.size();j++){ //check if site has budgets set.
                                 if(((CostCenterData)ccdv.get(j)).getNoBudget().equalsIgnoreCase(Constants.FALSE) &&
                                         ((CostCenterData)ccdv.get(j)).getCostCenterStatusCd().equals(RefCodeNames.COST_CENTER_STATUS_CD.ACTIVE)){
                                     return true;
                                 }
                             }
                         }
                     }
                  }
             }
			 return false;
		 }
		 catch(Exception e) {
			 return false;
		 }
	 }
	 /**
	  * Method to find whether current order's account supports budgets and to find unspent amount
	 */
	 public static HashMap getUnspentAmount(HttpServletRequest request,int siteId,int accountId){
		 BigDecimal unspentAmount = new BigDecimal(0);
		 HashMap orderBudgetInfo = new HashMap();
		 try{
			 CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			 AccountDataVector accounts = new AccountDataVector();
			 SiteData siteData = APIAccess.getAPIAccess().getSiteAPI().getSite(siteId,accountId);
			 AccountData accountData = APIAccess.getAPIAccess().getAccountAPI().getAccount(accountId, user.getUserStore().getStoreId());
			 accounts.add(accountData);
			 boolean siteHasBudgets = doesSiteSupportsBudgets(request, accounts);
			 if(siteHasBudgets){
				 unspentAmount = siteData.getTotalBudgetRemaining();
				 orderBudgetInfo.put(siteId,true);
				 orderBudgetInfo.put("unspentAmount",unspentAmount);
			 }
			 else{
				 orderBudgetInfo.put(siteId,false);
				 //orderBudgetInfo.put("unspentAmount",unspentAmount);
			 }
			 return orderBudgetInfo;
		 }
		 catch(Exception e){
			 return orderBudgetInfo;
		 }
	 }
	 
	 /**
	  * Builds View Cart HTML and return it as a String 
	  */
	 public static String buildViewCartHTML(HttpServletRequest request) {
		 
 	    HttpSession session = request.getSession(false);
 	    if(session==null) {
 	    	session = request.getSession(true);
 	    }
 	    
 	    CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 	    SiteData location = user.getSite();
 	    
	    SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
	    StringBuilder corporateOrderLink = new StringBuilder(50);
	    corporateOrderLink.append("shopping.do?");
	    corporateOrderLink.append(Constants.PARAMETER_OPERATION);
	    corporateOrderLink.append("=");
	    corporateOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER);
	    
	    StringBuilder physicalCartLink = new StringBuilder(50);
        physicalCartLink.append("shopping.do?");
        physicalCartLink.append(Constants.PARAMETER_OPERATION);
        physicalCartLink.append("=");
        physicalCartLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_PHYSICAL_CART);
		    
	    StringBuilder viewCartItemLink = new StringBuilder(50);
	    viewCartItemLink.append("shopping.do?");
	    viewCartItemLink.append(Constants.PARAMETER_OPERATION);
	    viewCartItemLink.append("=");
	    viewCartItemLink.append(Constants.PARAMETER_OPERATION_VALUE_ITEMS);
		    
	    StringBuilder viewCartLink = new StringBuilder(50);
	    viewCartLink.append("shopping.do?");
	    viewCartLink.append(Constants.PARAMETER_OPERATION);
	    viewCartLink.append("=");
	    viewCartLink.append(Constants.PARAMETER_OPERATION_VALUE_VIEW_CART);
		    
	    StringBuilder checkOutLink = new StringBuilder(50);
	    checkOutLink.append("checkout.do?");
	    checkOutLink.append(Constants.PARAMETER_OPERATION);
	    checkOutLink.append("=");
	    checkOutLink.append(Constants.PARAMETER_OPERATION_VALUE_CHECK_OUT);
		    
	    StringBuilder viewCartHTML = new StringBuilder(1000);
	    //STJ-5021 - only show the cart if the user is authorized for shopping
 		boolean showCorporateOrder = Utility.getSessionDataUtil(request).isConfiguredForCorporateOrders();;
 	    boolean isCorporateOrderOpen = ShopTool.hasInventoryCartAccessOpen(request); 	    
 	    boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
        if (showCorporateOrder && (isPhysicalCartAvailable || isCorporateOrderOpen)) {
			ShoppingCartData cart = (ShoppingCartData)session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
			int contractId = 0;
			
			if(location != null){
			    contractId = location.getContractData().getContractId();
			}

			//View Cart Left button
			viewCartHTML.append("<a class=\"btnLeft\" href=\"");
			if (isPhysicalCartAvailable){
			    viewCartHTML.append(physicalCartLink.toString());
			    viewCartHTML.append("\"> ");
	            viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"shoppingCart.text.physicalCart"));	            
			} else {
			    viewCartHTML.append(corporateOrderLink.toString());
			    viewCartHTML.append("\"> ");
	            viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"header.label.orders.corporateOrder"));	            
			}
			viewCartHTML.append(" ");
			viewCartHTML.append("(");
			viewCartHTML.append(cart.getItemsQty());
			viewCartHTML.append(")");
			viewCartHTML.append("</a> ");
			
			//View cart right button i.e. Price Button
			viewCartHTML.append("<a class=\"btnRight\" href=\"#\"> ");
			int userId = user.getUserId();
			String formattedPrice = ClwI18nUtil.formatAmount(request, cart.getItemsCost(), contractId, 0, userId);
			viewCartHTML.append(formattedPrice);
			viewCartHTML.append("</a> ");
			
			viewCartHTML.append("<div class=\"expand\"> ");
			viewCartHTML.append("<div class=\"tableWrapper\"> ");
			viewCartHTML.append("<table> ");
			viewCartHTML.append("<thead> ");
			viewCartHTML.append("<tr> ");
			viewCartHTML.append("<th> ");
			viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"shoppingCart.text.productName"));
			viewCartHTML.append("</th> ");
			viewCartHTML.append("<th class=\"right\"> ");
			viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"shoppingItems.text.qty"));
			viewCartHTML.append("</th> ");
			viewCartHTML.append("</tr> ");
			viewCartHTML.append("</thead> ");
			viewCartHTML.append("<tbody> ");
			
			int itemCount = cart.getItemsQty();
			for (int index=0; index <itemCount; index++) {
				ShoppingCartItemData item = cart.getItem(index);
				StringBuilder itemLink = new StringBuilder(viewCartItemLink.toString());
				if (itemLink.indexOf("?") > 0) {
					itemLink.append("&");
				}
				else {
					itemLink.append("?");
				}
				itemLink.append("itemId=");
				itemLink.append(item.getItemId());
				
				viewCartHTML.append("<tr> ");
				viewCartHTML.append("<td> ");
				String actualSkuNum = Utility.encodeForHTML(item.getActualSkuNum());
				viewCartHTML.append((Utility.isSet(actualSkuNum)) ? actualSkuNum : "&nbsp;");
				//viewCartHTML.append(Utility.encodeForHTML(Utility.isSet(item.getActualSkuNum()) ? item.getActualSkuNum() : "&nbsp;"));
				viewCartHTML.append("<br/> ");
				
				viewCartHTML.append("<a href=\"");
				viewCartHTML.append(itemLink.toString());
				viewCartHTML.append("\"> ");
				viewCartHTML.append(Utility.encodeForHTML(item.getItemDesc()));
				viewCartHTML.append("</a> ");
				
				viewCartHTML.append("</td> ");
				viewCartHTML.append("<td class=\"right\"> ");
				
				String qty = item.getQuantityString();
				if(!Utility.isSet(qty)){
				
					if (location.isAnInventoryAutoOrderItem(item.getProduct().getProductId())) {									
						BigDecimal autoOrderFactor = item.getAutoOrderFactor();
						BigDecimal parVal = new BigDecimal(item.getInventoryParValue());
						
						double qtyDb =  (parVal.doubleValue() * autoOrderFactor.doubleValue());
						int qtyInt = (int) (qtyDb + 0.500001); //rounding to upper integer
						qty = Integer.toString(qtyInt);
					}else{
						qty="0";
					}
				}
				
				viewCartHTML.append(qty);
				viewCartHTML.append("</td> ");
				viewCartHTML.append("</tr> ");
			}
					
			viewCartHTML.append("</tbody> ");
			viewCartHTML.append("</table> ");
			viewCartHTML.append("</div> ");
			viewCartHTML.append("<div class=\"checkoutNav\"> ");
             			
			//Close button
			viewCartHTML.append("<a class=\"closeBtn\" href=\"");
			viewCartHTML.append("#");
			viewCartHTML.append("\"> ");
			viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"global.action.label.close"));
			viewCartHTML.append("</a> ");
			
			viewCartHTML.append("</div> ");
			viewCartHTML.append("</div> ");
			
		}else{
			
				ShoppingCartData cart = (ShoppingCartData)session.getAttribute(Constants.SHOPPING_CART);
				String cartTotal = null;
				//only show the cart value if the user has the "show price" property
				if (user.getShowPrice()) {
					cartTotal = ClwI18nUtil.getPriceShopping(request, new Double(cart.getItemsCost()), 
						cart.getLocaleCd(), null, " ");
				}
				else {
					cartTotal = "&nbsp;";
				}
				
				//View Cart Left button
				viewCartHTML.append("<a class=\"btnLeft\" href=\"");
				viewCartHTML.append(viewCartLink.toString());
				viewCartHTML.append("\"> ");
				viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"header.label.viewCart"));
				viewCartHTML.append(" ");
				viewCartHTML.append("(");
				viewCartHTML.append(cart.getItemsQty());
				viewCartHTML.append(")");
				viewCartHTML.append("</a> ");
				
				//View cart right button i.e. Price Button
				viewCartHTML.append("<a class=\"btnRight\" href=\"#\"> ");
				viewCartHTML.append(cartTotal);
				viewCartHTML.append("</a> ");
             		
				viewCartHTML.append("<div class=\"expand\"> ");
				viewCartHTML.append("<div class=\"tableWrapper\"> ");
				viewCartHTML.append("<table> ");
				viewCartHTML.append("<thead> ");
				viewCartHTML.append("<tr> ");
				viewCartHTML.append("<th> ");
				viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"shoppingCart.text.productName"));
				viewCartHTML.append("</th> ");
				viewCartHTML.append("<th class=\"right\"> ");
				viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"shoppingItems.text.qty"));
				viewCartHTML.append("</th> ");
				viewCartHTML.append("</tr> ");
				viewCartHTML.append("</thead> ");
				viewCartHTML.append("<tbody> ");
				
				int itemCount = cart.getItemsQty();
				for (int index=0; index <itemCount; index++) {
					ShoppingCartItemData item = cart.getItem(index);
					StringBuilder itemLink = new StringBuilder(viewCartItemLink.toString());
					if (itemLink.indexOf("?") > 0) {
						itemLink.append("&");
					}
					else {
						itemLink.append("?");
					}
					itemLink.append("itemId=");
					itemLink.append(item.getItemId());
								
					viewCartHTML.append("<tr> ");
					viewCartHTML.append("<td> ");
					String actualSkuNum = Utility.encodeForHTML(item.getActualSkuNum());
					viewCartHTML.append((Utility.isSet(actualSkuNum)) ? actualSkuNum : "&nbsp;");
					//viewCartHTML.append(Utility.encodeForHTML(item.getActualSkuNum()));
					viewCartHTML.append("<br/> ");
									
					viewCartHTML.append("<a href=\"");
					viewCartHTML.append(itemLink.toString());
					viewCartHTML.append("\"> ");
					viewCartHTML.append(Utility.encodeForHTML(item.getItemDesc()));
					viewCartHTML.append("</a> ");
					
					viewCartHTML.append("</td> ");
					viewCartHTML.append("<td class=\"right\"> ");
					viewCartHTML.append(item.getQuantityString());
					viewCartHTML.append("</td> ");
					viewCartHTML.append("</tr> ");
				}
				viewCartHTML.append("</tbody> ");
				viewCartHTML.append("</table> ");
				viewCartHTML.append("</div> ");
				viewCartHTML.append("<div class=\"checkoutNav\"> ");
				
				viewCartHTML.append("<form name=\"esw.CheckOutEswForm\" action=\"");
				viewCartHTML.append(checkOutLink.toString());
				viewCartHTML.append("\" id=\"viewCartCheckOutFormId\" ");
				viewCartHTML.append(" > ");
				viewCartHTML.append("</form> ");
				
				viewCartHTML.append("<a class=\"blueBtn\" href=\"");
				viewCartHTML.append("javascript:submitCheckOut('shoppingFormId','operationId','checkOut')");
				viewCartHTML.append("\"> ");
				viewCartHTML.append("<span> ");
				viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"global.action.label.checkout"));
				viewCartHTML.append("</span> ");
				viewCartHTML.append("</a> ");
				
				viewCartHTML.append("<a class=\"closeBtn\" href=\"");
				viewCartHTML.append("#");
				viewCartHTML.append("\"> ");
				viewCartHTML.append(ClwMessageResourcesImpl.getMessage(request,"global.action.label.close"));
				viewCartHTML.append("</a> ");
				viewCartHTML.append("</div> ");
				viewCartHTML.append("</div> ");
			} //end not inv shopping on
		return viewCartHTML.toString();
	 }
	 
	 /*
	  * Builds HTML String for Classic UI link.
	  */
	 public static String buildClassicUIHTML(HttpServletRequest request) {
		 StringBuilder classicUIHTML = new StringBuilder(150);
		 
		 StringBuilder uiSelectionLink = new StringBuilder(50);
	     uiSelectionLink.append("changeUI.do?");
	     uiSelectionLink.append(Constants.PARAMETER_OPERATION);
	     uiSelectionLink.append("=");
	     uiSelectionLink.append(Constants.PARAMETER_OPERATION_VALUE_USE_CLASSIC_UI);
		    
		 classicUIHTML.append("<p class=\"utilityNav\"> ");
		 classicUIHTML.append("<a href=\"");
		 classicUIHTML.append(uiSelectionLink.toString());
		 classicUIHTML.append("\"> ");
		 classicUIHTML.append(ClwMessageResourcesImpl.getMessage(request,"header.label.useClassicUserInterface"));
		 classicUIHTML.append("</a> ");
		 classicUIHTML.append("</p> ");
		 
		 return classicUIHTML.toString();
	 }
	 
	 /**
	  * Method to determine the user interface the current user should be using
	  * @param request
	  * @return
	  */
	 public static String getUserInterface(HttpServletRequest request) {
		 //assume the old UI as the default
		 String returnValue = Constants.PORTAL_CLASSIC;
		 
		 //determine the type of user currently logged in.  If we cannot determine this information then
		 //don't proceed
		 String userType = null;
	     CleanwiseUser appUser = ShopTool.getCurrentUser(request);
	     if (appUser != null) {
	    	 userType = appUser.getUser().getUserTypeCd();
	     }
	     if (!Utility.isSet(userType)) {
	    	 return returnValue;
	     }
	     
	     //determine what UI value is specified at the store level
	     String storeUIPreference = Utility.getAlternatePortal(appUser.getUserStore());
	     if(!Utility.isSet(storeUIPreference)){
	    	 storeUIPreference =  Utility.getAlternatePortal(appUser.getUserAccount());
	     }
	     //if this is a mobile app situation, then the user should be using the new UI only if the 
	     //store they belong to has specified the new UI and the user is a multi-site buyer.
	     String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
	     if (Constants.TRUE.equalsIgnoreCase(mobileClient)) {
	    	 if (Constants.PORTAL_ESW.equals(storeUIPreference) && RefCodeNames.USER_TYPE_CD.MSB.equals(userType)) {
	    		 returnValue = Constants.PORTAL_ESW;
	    	 }
	     }
	     //if this is not a mobile app situation, then there are a couple of things we need to examine to
	     //determine which UI the user should be using
	     else {
	         //STJ-4642 - if the user is authorized to specify a UI preference and they have made such
	         //a specification, respect that
	         String userUIPreference = null;
	         boolean isAuthorizedForFunction = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS);
	         if (isAuthorizedForFunction) {
	        	 try {
	        		 APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);        
	        		 userUIPreference = factory.getPropertyServiceAPI().getUserProperty(appUser.getUserId(), 
		    	    			RefCodeNames.PROPERTY_TYPE_CD.USER_UI_PREFERENCE);
	        	 }
		         catch (DataNotFoundException dnfe) {
		            	//nothing to do here - no user ui preference has been set
		         }
		         catch (Exception e) {
		    	 	log.error("Caught exception trying to determine user UI preference.");
		         }
	         }
	         
	         //if the user has specified a preference for a UI, return that value
	         if (Utility.isSet(userUIPreference)) {
	        	 returnValue = userUIPreference;
	         }
	         //otherwise, see if there is a preference specified at the store level.  If so and it is applicable
	         //to this user return it.
	         else {
	        	if (Constants.PORTAL_ESW.equals(storeUIPreference)) {
	            	//For now, only multi-site buyers and mobile users can use the new UI.
	                if (RefCodeNames.USER_TYPE_CD.MSB.equals(userType)) {
	                	returnValue = storeUIPreference;
	                }
	        	}
	         }
	     }
		 return returnValue;
	 }
	 
	 /*
	  * Build Site delivery HTML
	  */
	 public static String buildSiteDeliveryHTML(HttpServletRequest request) {
		 StringBuilder siteDeliveryHTML = new StringBuilder(250);
		 String cutoffTimeS = "";
		 String cutoffDateS = "";
		 String nextDeliveryDateS = "";
		 
		 String noBreakSpace = "&nbsp;";
		 String space = " ";
		  
		 CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		 SimpleDateFormat sdfTime = new SimpleDateFormat(Constants.SIMPLE_TIME_PATTERN);
		 APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
		 
		 try{
			 Site siteBean = factory.getSiteAPI();
			 SiteDeliveryData nextDeliveryData = null;
			 int siteId = 0;
			 if (appUser.getSite() != null) {
                 siteId = appUser.getSite().getSiteId();
             }
			 SiteDeliveryDataVector dataV = siteBean.getNextSiteDeliveryData(siteId);

			 Date dt = new Date();

			 if (dataV != null && dataV.size() == 1){
				 nextDeliveryData  = (SiteDeliveryData)(dataV.get(0));
			 } else if (dataV.size() == 2) {
				 Date d1 = ((SiteDeliveryData)(dataV.get(0))).getCutoffSystemTime();
				 Date d2 = ((SiteDeliveryData)(dataV.get(1))).getCutoffSystemTime();
				 nextDeliveryData =( d1.before(d2) && dt.before(d1) ) ? (SiteDeliveryData)(dataV.get(0)) : (SiteDeliveryData)(dataV.get(1));
			 }

			 if (nextDeliveryData != null) {

				 Date cutoffTime = nextDeliveryData.getCutoffSiteTime() != null ? nextDeliveryData.getCutoffSiteTime()
						 : nextDeliveryData.getCutoffSystemTime();

				 cutoffTimeS = sdfTime.format(cutoffTime);
				 cutoffDateS = ClwI18nUtil.formatDate(request, cutoffTime, DateFormat.FULL);

				 if (cutoffDateS != null && cutoffTimeS != null) {
					 cutoffDateS = cutoffDateS.replaceAll(space, noBreakSpace);
					 cutoffTimeS = cutoffTimeS.replaceAll(space, noBreakSpace);
				 }

				 if (nextDeliveryData.getDeliveryDate() != null) {
					 nextDeliveryDateS = ClwI18nUtil.formatDate(request, nextDeliveryData.getDeliveryDate(),
							 DateFormat.FULL
					 );
				 }
			 }
		 }catch (Exception exc) {
			 exc.printStackTrace();
		 }
		 
		 siteDeliveryHTML.append("<p class=\"utilityNav\"> ");
		 
		 if(Utility.isSet(cutoffDateS)){
			 siteDeliveryHTML.append("<b>"+ClwMessageResourcesImpl.getMessage(request,"shop.header.text.cutOffDate")+"</b>");
			 siteDeliveryHTML.append("&nbsp;");
			 siteDeliveryHTML.append(cutoffDateS);
			 
			 if(Utility.isSet(cutoffTimeS)){
				 siteDeliveryHTML.append("&nbsp;");
				 siteDeliveryHTML.append(cutoffTimeS);
			 }
		 }
		 
		 if(Utility.isSet(nextDeliveryDateS)){
			 siteDeliveryHTML.append("<br>");
			 siteDeliveryHTML.append("<b>"+ClwMessageResourcesImpl.getMessage(request,"shop.header.text.deliveryDate")+"</b>");
			 siteDeliveryHTML.append("&nbsp;");
			 siteDeliveryHTML.append(nextDeliveryDateS);
		 }
		 
		 siteDeliveryHTML.append("</p> ");
		 return siteDeliveryHTML.toString();
	 }

	 
	 /*
	  * Builds Corporate Order HTML.
	  */
	 public static String buildCorporateOrderHTML(HttpServletRequest request) {
		StringBuilder corporateOrderHTML = new StringBuilder(250);
		 
		boolean showCorporateOrder = Utility.getSessionDataUtil(request).isConfiguredForCorporateOrders();
		boolean isCorporateOrderOpen = ShopTool.hasInventoryCartAccessOpen(request);
		if (showCorporateOrder){
			if(isCorporateOrderOpen) {
				String releaseDate = Utility.getSessionDataUtil(request).getCorporateOrderReleaseDate();
				if(Utility.isSet(releaseDate)){
					corporateOrderHTML.append("<p class=\"utilityNav\"> ");
					corporateOrderHTML.append(ClwMessageResourcesImpl.getMessage(request,"header.label.corporateOrderRelease"));
					corporateOrderHTML.append(":&nbsp;");
					corporateOrderHTML.append(releaseDate);
					corporateOrderHTML.append("</p> ");
					
				}
			} else {
				String openDate = Utility.getSessionDataUtil(request).getCorporateOrderOpenDate();
				if(Utility.isSet(openDate)){
					corporateOrderHTML.append("<p class=\"utilityNav\"> ");
					corporateOrderHTML.append(ClwMessageResourcesImpl.getMessage(request,"header.label.corporateOrderOpen"));
					corporateOrderHTML.append(":&nbsp;");
					corporateOrderHTML.append(openDate);
					corporateOrderHTML.append("</p> ");
				}
			}
		}
		return corporateOrderHTML.toString();
	 }
	 
	 public static String getCustomContentURL(HttpServletRequest request){
		 
		 String val = null;
		 try{
			 CleanwiseUser applUser = getCurrentUser(request);
			 APIAccess factory = new APIAccess();
			 
			 PropertyService prop = factory.getPropertyServiceAPI();
		    	
			 if(applUser.getUserAccount()!=null){
		    		
				 int accountId = applUser.getUserAccount().getAccountId();
				 try{
					 val =prop.getBusEntityProperty(accountId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOM_CONTENT_URL);
				 }catch (DataNotFoundException dnfe) {
	    	        	//log.info("Content Management Properties not set for account:"+accountId);
				 }
			 }
			 
			 if(!Utility.isSet(val)){ 
				 int storeId = applUser.getUserStore().getStoreId();
				 try{
					 val =prop.getBusEntityProperty(storeId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOM_CONTENT_URL);
				 }catch (DataNotFoundException dnfe) {
	    	        	//log.info("Content Management Properties not set for account:"+accountId);
				 }
			 }
			 
			 //If not set, get default from properties file
			 if(!Utility.isSet(val)){
				 val = System.getProperty("cms.customContentURL");
			 }
		 }
		 catch(Exception e) {
			 return null;
		 }
		 return val;
	 }
	 
	 /**
	     * This method is to retrieve the username to login to Joomla!
	     * If the current user is in the editor list for account, use it. Else return viewer user for account.
	     * If editor/viewer are not set up at account, get store editor/viewer.
	     * @param request
	     * @return username
	     */
	 public static String getCustomContentUser(HttpServletRequest request){
		 String val = null;
		 try{
			 CleanwiseUser applUser = getCurrentUser(request);
			 APIAccess factory = new APIAccess();
			 
			 PropertyService prop = factory.getPropertyServiceAPI();
			 
			 if(applUser.getUserAccount()!=null){
		    		
				 int accountId = applUser.getUserAccount().getAccountId();
				 try{
					 String editorList =prop.getBusEntityProperty(accountId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOM_CONTENT_EDITOR);
					 if(Utility.isSet(editorList)){
						 String[] editors = Utility.parseStringToArray(editorList, ",");
						 for(int i=0; i<editors.length; i++){
							 if(editors[i].equals(applUser.getUserName())){
								 val = applUser.getUserName();
								 break;
							 }
						 }
 		    		
					 }
					 if(!Utility.isSet(val)){
						 val = prop.getBusEntityProperty(accountId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOM_CONTENT_VIEWER);
					 }
				 }catch (DataNotFoundException dnfe) {
					 log.info("Content Management Properties not set for account:"+accountId);
				 }
			 }
			 
			 if(!Utility.isSet(val)){ 
				 int storeId = applUser.getUserStore().getStoreId();
				 //Property is not setup at account level, try store
				 try{
					 String editorList =prop.getBusEntityProperty(storeId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOM_CONTENT_EDITOR);
 		    		
					 if(Utility.isSet(editorList)){
						 String[] editors = Utility.parseStringToArray(editorList, ",");
						 for(int i=0; i<editors.length; i++){
							 if(editors[i].equals(applUser.getUserName())){
								 val = applUser.getUserName();
								 break;
							 }
						 }
					 }
					 if(!Utility.isSet(val)){
						 val = prop.getBusEntityProperty(storeId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOM_CONTENT_VIEWER);
					 }
				 }catch (DataNotFoundException dnfe) {
					 log.info("Content Management Properties not set for store:"+storeId);
				 }
			 }
			 
			 if(!Utility.isSet(val)){
				 val = System.getProperty("cms.cutomContentViewer");
			 }
		 }
		 catch(Exception e) {
			 return null;
		 }
		 return val;
			 
	 }
	 
	 public static BigDecimal getBudgetExceededAmountWithOrder(HttpServletRequest request, OrderData orderD)
	 throws RemoteException {
		 BigDecimal budgetExceededAmount = ZERO;
		 CleanwiseUser applUser = getCurrentUser(request);
		 try {
			 APIAccess factory = new APIAccess();
			 SiteData siteD = applUser.getSite();
			 if (siteD.getSiteId() != orderD.getSiteId()){
				 siteD = factory.getSiteAPI().getSite(orderD.getSiteId(), orderD.getAccountId());
			 }
			 BigDecimal orderBudget = factory.getOrderAPI().getTotalBudgetForOrder(orderD);
			 budgetExceededAmount = siteD.getTotalBudgetAllocated().subtract(siteD.getTotalBudgetSpent());
			 budgetExceededAmount = budgetExceededAmount.subtract(orderBudget).negate();			 
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 return budgetExceededAmount;
	 }
}
